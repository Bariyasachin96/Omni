// Proves nativeFoldAndDetect(text) == normalizeFancy(text) + nativeGetLanguages(folded),
// element for element, for every text and every word of every text, with the
// quick-character flag off and on, under ART's string behaviour (real 4-byte
// UTF-8 for an emoji). Helpers are copied from tools/verify/autottslog/main.cpp.
#include <jni.h>
#include <algorithm>
#include <cstdio>
#include <cstring>
#include <fstream>
#include <string>
#include <vector>
extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeGetLanguages(JNIEnv*, jclass, jstring);
JNIEXPORT jstring JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_normalizeFancy(JNIEnv*, jclass, jstring);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv*, jclass, jstring, jboolean);
jint JNI_OnLoad(JavaVM*, void*);
}
static JNIEnv* env = nullptr;
// Real UTF-8 <-> Java strings. NewStringUTF/GetStringUTFChars speak MODIFIED
// UTF-8, which mangles an emoji; the app hands the core real Java Strings, so
// the harness must too.
static jstring J(const std::string& s){
    std::u16string u;
    for (size_t i = 0; i < s.size();) {
        unsigned char c = s[i]; unsigned cp; int n;
        if (c < 0x80) { cp = c; n = 1; } else if ((c >> 5) == 6) { cp = c & 31; n = 2; }
        else if ((c >> 4) == 14) { cp = c & 15; n = 3; } else { cp = c & 7; n = 4; }
        for (int k = 1; k < n && i + k < s.size(); k++) cp = (cp << 6) | (s[i + k] & 63);
        i += n;
        if (cp >= 0x10000) { cp -= 0x10000; u += char16_t(0xD800 + (cp >> 10)); u += char16_t(0xDC00 + (cp & 1023)); }
        else u += char16_t(cp);
    }
    return env->NewString((const jchar*)u.data(), (jsize)u.size());
}
static std::string S(jstring s){
    if (!s) return "";
    jsize n = env->GetStringLength(s);
    const jchar* c = env->GetStringChars(s, nullptr);
    std::string o;
    for (jsize i = 0; i < n; i++) {
        unsigned cp = c[i];
        if (cp >= 0xD800 && cp < 0xDC00 && i + 1 < n) { cp = 0x10000 + ((cp - 0xD800) << 10) + (c[++i] - 0xDC00); }
        if (cp < 0x80) o += char(cp);
        else if (cp < 0x800) { o += char(0xC0 | cp >> 6); o += char(0x80 | (cp & 63)); }
        else if (cp < 0x10000) { o += char(0xE0 | cp >> 12); o += char(0x80 | ((cp >> 6) & 63)); o += char(0x80 | (cp & 63)); }
        else { o += char(0xF0 | cp >> 18); o += char(0x80 | ((cp >> 12) & 63)); o += char(0x80 | ((cp >> 6) & 63)); o += char(0x80 | (cp & 63)); }
    }
    env->ReleaseStringChars(s, c);
    return o;
}
static jobjectArray arr(const std::vector<std::string>& v){
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i]));
    return a;
}
static std::string unesc(const std::string& s){
    std::string o;
    for (size_t i = 0; i < s.size(); i++) {
        if (s[i] == '\\' && i + 1 < s.size()) {
            char n = s[++i];
            o += n == 't' ? '\t' : n == 'n' ? '\n' : n;
        } else o += s[i];
    }
    return o;
}
static JNINativeInterface_ artFns;
static jstring JNICALL artNewStringUTF(JNIEnv*, const char* c){ return J(c ? c : ""); }
static jsize JNICALL artUTFLength(JNIEnv*, jstring s){ return (jsize)S(s).size(); }
static void JNICALL artUTFRegion(JNIEnv*, jstring s, jsize start, jsize len, char* buf){
    // start/len are UTF-16 units; turn exactly that slice into UTF-8
    jsize n = env->GetStringLength(s);
    const jchar* c = env->GetStringChars(s, nullptr);
    jstring part = env->NewString(c + start, std::min(len, n - start));
    env->ReleaseStringChars(s, c);
    std::string o = S(part);
    memcpy(buf, o.data(), o.size()); buf[o.size()] = 0;
}


static std::vector<std::string> V(jobjectArray a){
    std::vector<std::string> v;
    for (jsize i = 0; i < env->GetArrayLength(a); i++) v.push_back(S((jstring)env->GetObjectArrayElement(a, i)));
    return v;
}
static int checked = 0, bad = 0;
static void check(const std::string& text){
    for (int quick = 0; quick < 2; quick++) {
        std::string folded = S(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_normalizeFancy(env, nullptr, J(text)));
        std::vector<std::string> want{folded};
        if (!(quick && env->GetStringLength(J(folded)) == 1)) {
            std::vector<std::string> spans = V(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeGetLanguages(env, nullptr, J(folded)));
            want.insert(want.end(), spans.begin(), spans.end());
        }
        std::vector<std::string> got = V(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, J(text), (jboolean)quick));
        checked++;
        if (got != want) {
            bad++;
            printf("DIFF (quick=%d): %s\n", quick, text.c_str());
            for (auto& w : want) printf("   two calls |%s|\n", w.c_str());
            for (auto& g : got)  printf("   one call  |%s|\n", g.c_str());
        }
    }
}
int main(int argc, char** argv){
    JavaVM* vm; JavaVMInitArgs a{}; a.version = JNI_VERSION_1_8;
    JNI_CreateJavaVM(&vm, (void**)&env, &a);
    JNI_OnLoad(vm, nullptr);
    artFns = *env->functions;
    artFns.NewStringUTF = artNewStringUTF;
    artFns.GetStringUTFLength = artUTFLength;
    artFns.GetStringUTFRegion = artUTFRegion;
    env->functions = &artFns;
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr({"hin","eng","guj"}), arr({"hi","en","gu"}));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr({"hi","en","gu"}));
    for (int f = 1; f < argc; f++) {
        std::ifstream in(argv[f]); std::string line;
        while (std::getline(in, line)) {
            if (line.empty() || line.rfind("# ", 0) == 0) continue;
            std::string text = unesc(line);
            check(text);
            size_t p = 0;
            while (p < text.size()) {
                size_t q = text.find(' ', p);
                if (q == std::string::npos) q = text.size();
                if (q > p) check(text.substr(p, q - p));
                p = q + 1;
            }
        }
    }
    check("");
    printf("%d checks: %d differ\n", checked, bad);
    return bad ? 1 : 0;
}
