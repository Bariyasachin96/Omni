// OLD CORE vs NEW CORE, through the real JNI entry points (owner, 2026-10-01:
// "pahle test kariye"). Built twice: with -DOLD against the core of the commit
// before the change (processDirect took a direct ByteBuffer and answered a
// packed string; auto/Google mode crossed twice, detectLanguageFull then
// nativeFoldAndDetect), and without it against the working tree (processDirect
// takes the String and answers an array; detectWindowOrSpans does both steps).
// Each build prints one line per case with the raw answers, escaped; Check.kt
// then runs the Kotlin of each version over its own build's answers and
// compares what the service would speak.
#include <jni.h>
#include <cstdio>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <cstring>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv*, jclass, jstring, jboolean);
#ifdef OLD
JNIEXPORT jstring JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jobject, jint, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean, jboolean);
JNIEXPORT jstring JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectLanguageFull(
    JNIEnv*, jclass, jstring, jstring, jstring, jboolean, jboolean);
#else
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jstring, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean, jboolean);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
    JNIEnv*, jclass, jstring, jstring, jstring, jboolean, jboolean, jboolean);
#endif
}

static JNIEnv* env = nullptr;
static jstring U(const std::u16string& u){ return env->NewString((const jchar*)u.data(), (jsize)u.size()); }
static std::u16string A(const char* s){ std::u16string u; while (*s) u += char16_t((unsigned char)*s++); return u; }
static jstring J(const char* s){ return U(A(s)); }
// UTF-16 units of a Java string, out as one escaped field: printable ASCII as
// itself, everything else (and '\' and tab) as \uXXXX per unit.
static std::string E(jstring s){
    if (!s) return "\\N";
    jsize n = env->GetStringLength(s);
    std::vector<jchar> c((size_t)n + 1);
    env->GetStringRegion(s, 0, n, c.data());
    std::string o; char b[8];
    for (jsize i = 0; i < n; i++) {
        jchar x = c[(size_t)i];
        if (x >= 0x20 && x < 0x7F && x != '\\') o += (char)x;
        else { snprintf(b, sizeof b, "\\u%04X", (unsigned)x); o += b; }
    }
    return o;
}
static std::string EA(jobjectArray a){
    if (!a) return "\\N";
    std::string o; jsize n = env->GetArrayLength(a);
    for (jsize i = 0; i < n; i++) {
        jstring s = (jstring)env->GetObjectArrayElement(a, i);
        if (i) o += '\t';
        o += E(s); env->DeleteLocalRef(s);
    }
    return o;
}
static jobjectArray arr(const std::vector<std::string>& v){
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i].c_str()));
    return a;
}
// A corpus line: UTF-8, with \\ \t \n \0 and \uXXXX (one UTF-16 unit, so a lone
// surrogate can be written) as escapes.
static std::u16string unesc(const std::string& s){
    std::u16string u;
    for (size_t i = 0; i < s.size();) {
        if (s[i] == '\\' && i + 1 < s.size()) {
            char n = s[i + 1];
            if (n == 'u' && i + 5 < s.size()) { u += char16_t(strtol(s.substr(i + 2, 4).c_str(), nullptr, 16)); i += 6; continue; }
            u += n == 't' ? u'\t' : n == 'n' ? u'\n' : n == '0' ? u'\0' : char16_t((unsigned char)n); i += 2; continue;
        }
        unsigned char c = s[i]; unsigned cp; int n;
        if (c < 0x80) { cp = c; n = 1; } else if ((c >> 5) == 6) { cp = c & 31; n = 2; }
        else if ((c >> 4) == 14) { cp = c & 15; n = 3; } else { cp = c & 7; n = 4; }
        for (int k = 1; k < n && i + k < s.size(); k++) cp = (cp << 6) | (s[i + k] & 63);
        i += n;
        if (cp >= 0x10000) { cp -= 0x10000; u += char16_t(0xD800 + (cp >> 10)); u += char16_t(0xDC00 + (cp & 1023)); }
        else u += char16_t(cp);
    }
    return u;
}

// ART behaviour for the three modified-UTF-8 calls (see autottslog/main.cpp):
// a supplementary character is real 4-byte UTF-8, U+0000 is C0 80.
static std::string toArt(const jchar* c, jsize n){
    std::string o;
    for (jsize i = 0; i < n; i++) {
        unsigned cp = c[i];
        if (cp >= 0xD800 && cp < 0xDC00 && i + 1 < n && c[i + 1] >= 0xDC00 && c[i + 1] < 0xE000) { cp = 0x10000 + ((cp - 0xD800) << 10) + (c[++i] - 0xDC00); }
        if (cp == 0) { o += (char)0xC0; o += (char)0x80; }
        else if (cp < 0x80) o += char(cp);
        else if (cp < 0x800) { o += char(0xC0 | cp >> 6); o += char(0x80 | (cp & 63)); }
        else if (cp < 0x10000) { o += char(0xE0 | cp >> 12); o += char(0x80 | ((cp >> 6) & 63)); o += char(0x80 | (cp & 63)); }
        else { o += char(0xF0 | cp >> 18); o += char(0x80 | ((cp >> 12) & 63)); o += char(0x80 | ((cp >> 6) & 63)); o += char(0x80 | (cp & 63)); }
    }
    return o;
}
static std::u16string fromUtf8(const char* s){
    std::string b(s); std::u16string u;
    for (size_t i = 0; i < b.size();) {
        unsigned char c = b[i]; unsigned cp; int n;
        if (c < 0x80) { cp = c; n = 1; } else if ((c >> 5) == 6) { cp = c & 31; n = 2; }
        else if ((c >> 4) == 14) { cp = c & 15; n = 3; } else { cp = c & 7; n = 4; }
        for (int k = 1; k < n && i + k < b.size(); k++) cp = (cp << 6) | (b[i + k] & 63);
        i += n;
        if (cp >= 0x10000) { cp -= 0x10000; u += char16_t(0xD800 + (cp >> 10)); u += char16_t(0xDC00 + (cp & 1023)); }
        else u += char16_t(cp);
    }
    return u;
}
static JNINativeInterface_ artFns;
static const JNINativeInterface_* realFns;
static jstring JNICALL artNewStringUTF(JNIEnv* e, const char* c){ std::u16string u = fromUtf8(c ? c : ""); return realFns->NewString(e, (const jchar*)u.data(), (jsize)u.size()); }
static std::string artOf(JNIEnv* e, jstring s){
    jsize n = realFns->GetStringLength(e, s);
    std::vector<jchar> c((size_t)n + 1);
    realFns->GetStringRegion(e, s, 0, n, c.data());
    return toArt(c.data(), n);
}
static jsize JNICALL artUTFLength(JNIEnv* e, jstring s){ return (jsize)artOf(e, s).size(); }
static void JNICALL artUTFRegion(JNIEnv* e, jstring s, jsize start, jsize len, char* buf){
    jsize n = realFns->GetStringLength(e, s);
    std::vector<jchar> c((size_t)n + 1);
    realFns->GetStringRegion(e, s, 0, n, c.data());
    std::string o = toArt(c.data() + start, std::min(len, n - start));
    memcpy(buf, o.data(), o.size()); buf[o.size()] = 0;
}

#ifdef OLD
// What the old Kotlin did: text.toByteArray(Charsets.UTF_8) into a direct buffer.
static std::string javaBytes(jstring s){
    jclass sc = env->FindClass("java/lang/String");
    static jmethodID gb = env->GetMethodID(sc, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray b = (jbyteArray)env->CallObjectMethod(s, gb, J("UTF-8"));
    jsize n = env->GetArrayLength(b);
    std::string o((size_t)n, '\0');
    if (n) env->GetByteArrayRegion(b, 0, n, (jbyte*)&o[0]);
    env->DeleteLocalRef(b); env->DeleteLocalRef(sc);
    return o;
}
#endif

int main(int argc, char** argv){
    JavaVM* vm; JavaVMInitArgs a{}; a.version = JNI_VERSION_1_8;
    JNI_CreateJavaVM(&vm, (void**)&env, &a);
    realFns = env->functions;
    artFns = *env->functions;
    artFns.NewStringUTF = artNewStringUTF;
    artFns.GetStringUTFLength = artUTFLength;
    artFns.GetStringUTFRegion = artUTFRegion;
    env->functions = &artFns;
    std::vector<std::u16string> texts;
    { std::ifstream in(argv[1]); std::string line; while (std::getline(in, line)) texts.push_back(unesc(line)); }
    const std::vector<std::vector<std::string>> sets2 = {
        {"hi","en","gu"}, {"en","hi","mr"}, {"en","ru","uk"}, {"en","ar","ur","fa"},
        {"zh","ja","ko","th"}, {}, {"en"}, {"en","hi","ar","zh","ru","gu","ta","bn"} };
    const int modes[][3] = {{0,0,0},{1,1,1},{2,2,2},{3,3,3},{0,3,1},{2,0,3}};
    long id = 0;
    for (size_t si = 0; si < sets2.size(); si++) {
        auto& set = sets2[si];
        std::vector<std::string> iso3;
        for (auto& c : set) iso3.push_back(c == "hi" ? "hin" : c == "en" ? "eng" : c == "gu" ? "guj" : c == "mr" ? "mar" : c);
        Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(set));
        Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(set));
        for (size_t ti = 0; ti < texts.size(); ti++) {
            env->PushLocalFrame(1024);
            jstring text = U(texts[ti]);
            printf("T\t%s\n", E(text).c_str());
#ifdef OLD
            std::string bytes = javaBytes(text);
            jobject buf = env->NewDirectByteBuffer((void*)bytes.data(), (jlong)bytes.size());
#endif
            // mix / multilingual (detect on) and dual (detect off)
            for (int dual = 0; dual < 2; dual++) for (auto& m : modes) for (int quick = 0; quick < 2; quick++) for (int sm = 0; sm < 2; sm++) {
                if (dual && quick) continue;
                env->PushLocalFrame(64);
#ifdef OLD
                jstring r = (bytes.empty() ? env->NewStringUTF("") :
                    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
                    env, nullptr, buf, (jint)bytes.size(), J("eng"), J("hin"), J(dual ? "dual" : "mix"),
                    m[0], J("eng"), m[1], J("hin"), m[2], J("guj"), JNI_TRUE, sm ? JNI_TRUE : JNI_FALSE, 2, J("eng"), 1, JNI_FALSE,
                    dual ? JNI_FALSE : JNI_TRUE, quick ? JNI_TRUE : JNI_FALSE));
                printf("%s\t%ld\t%d\t%s\n", dual ? "Q" : "P", id, quick, E(r).c_str());
#else
                jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
                    env, nullptr, text, J("eng"), J("hin"), J(dual ? "dual" : "mix"),
                    m[0], J("eng"), m[1], J("hin"), m[2], J("guj"), JNI_TRUE, sm ? JNI_TRUE : JNI_FALSE, 2, J("eng"), 1, JNI_FALSE,
                    dual ? JNI_FALSE : JNI_TRUE, quick ? JNI_TRUE : JNI_FALSE);
                printf("%s\t%ld\t%d\t%s\n", dual ? "Q" : "P", id, quick, EA(r).c_str());
#endif
                id++;
                env->PopLocalFrame(nullptr);
            }
            // auto / Google: windowed detect, and the aggregate's input
            for (int dis = 0; dis < 2; dis++) for (int quick = 0; quick < 2; quick++) {
                env->PushLocalFrame(64);
#ifdef OLD
                jstring full = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectLanguageFull(
                    env, nullptr, text, J("eng"), J("hin"), dis ? JNI_TRUE : JNI_FALSE, JNI_TRUE);
                jobjectArray fold = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, text, quick ? JNI_TRUE : JNI_FALSE);
                printf("A\t%ld\t%d\t%s\t\\X\t%s\n", id, quick, E(full).c_str(), EA(fold).c_str());
#else
                jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
                    env, nullptr, text, J("eng"), J("hin"), dis ? JNI_TRUE : JNI_FALSE, JNI_TRUE, quick ? JNI_TRUE : JNI_FALSE);
                jobjectArray fold = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, text, quick ? JNI_TRUE : JNI_FALSE);
                printf("A\t%ld\t%d\t%s\t\\X\t%s\n", id, quick, EA(r).c_str(), EA(fold).c_str());
#endif
                id++;
                env->PopLocalFrame(nullptr);
            }
            env->PopLocalFrame(nullptr);
        }
    }
    return 0;
}
