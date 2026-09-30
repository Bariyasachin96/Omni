// Replays a real AutoTTS log through OUR native core and compares, chunk by
// chunk, what AutoTTS spoke with what Easy Voice would speak.
//
// extract.py turns the log into cases: the utterance text, then each
// `speak N:` chunk AutoTTS logged with the language it loaded for it. Here the
// same text goes down Easy Voice's real MIXED path -- processDirect
// (buildMixChunks), then normalizeFancy + nativeGetLanguages on every chunk of
// type 0/1/2, then languageForDetectedRun's rule -- and the two lists are
// compared. A screen reader often cuts an utterance off, so AutoTTS's list can
// be a PREFIX of the full split; only that prefix is compared.
//
// Settings are the ones the log itself shows: mixed mode (Latin eng, non-Latin
// guj), `updateLanguage2LetterCodes [hi, en, gu]` for the enabled set, and
// smart number reading at group size 2 ("83201" is spoken "83 20 1"). Every
// other detected language has no engine and falls back by script.
#include <jni.h>
#include <cstdio>
#include <algorithm>
#include <cstring>
#include <fstream>
#include <string>
#include <vector>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeGetLanguages(JNIEnv*, jclass, jstring);
JNIEXPORT jstring JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_normalizeFancy(JNIEnv*, jclass, jstring);
JNIEXPORT jstring JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jobject, jint, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean);
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
// decodeChunkText's inverse of packField: \x1D escapes \x1D, \x1E, \x1F
static std::string decodeChunk(const std::string& s){
    std::string o;
    for (size_t i = 0; i < s.size(); i++) {
        if (s[i] == '\x1D' && i + 1 < s.size()) { o += s[++i]; } else o += s[i];
    }
    return o;
}
struct Chunk { std::string lang, text; };

// ART, unlike HotSpot, reads and writes a supplementary character (every emoji)
// as a real 4-byte UTF-8 sequence in NewStringUTF / GetStringUTF*. The core is
// written for ART, so under HotSpot every emoji came back mangled and the span
// cut short. These three stand in for ART's behaviour.
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

static std::string resolve(const std::string& lang, bool latin){
    // languageForDetectedRun with only eng and guj configured
    std::string byScript = latin ? "eng" : "guj";
    if (lang == "en") return "eng";
    if (lang == "gu") return "guj";
    if (lang == "hi") return "hin";
    return byScript;
}

static bool smart = true; static int group = 2;   // what the log shows: "83201" -> "83 20 1"
static std::vector<Chunk> ours(const std::string& text){
    std::vector<Chunk> out;
    jobject buf = env->NewDirectByteBuffer((void*)text.data(), (jlong)text.size());
    std::string packed = S(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, buf, (jint)text.size(), J("eng"), J("guj"), J("mix"),
        0, J(""), 0, J(""), 0, J(""), JNI_TRUE, smart ? JNI_TRUE : JNI_FALSE, group, J("eng"), 1, JNI_FALSE));
    size_t at = 0;
    while (at < packed.size()) {
        size_t rec = packed.find('\x1E', at);
        std::string one = packed.substr(at, rec == std::string::npos ? std::string::npos : rec - at);
        at = rec == std::string::npos ? packed.size() : rec + 1;
        size_t f1 = one.find('\x1F'), f2 = one.find('\x1F', f1 + 1), f3 = one.find('\x1F', f2 + 1);
        if (f1 == std::string::npos || f2 == std::string::npos || f3 == std::string::npos) continue;
        int type = atoi(one.substr(0, f1).c_str());
        std::string t = decodeChunk(one.substr(f3 + 1));
        if (type >= 3) { out.push_back({one.substr(f2 + 1, f3 - f2 - 1), t}); continue; }
        std::string folded = S(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_normalizeFancy(env, nullptr, J(t)));
        jobjectArray got = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeGetLanguages(env, nullptr, J(folded));
        jsize n = got ? env->GetArrayLength(got) : 0;
        for (jsize i = 0; i + 2 < n; i += 3) {
            std::string l = S((jstring)env->GetObjectArrayElement(got, i));
            bool latin = S((jstring)env->GetObjectArrayElement(got, i + 1)) == "1";
            out.push_back({resolve(l, latin), S((jstring)env->GetObjectArrayElement(got, i + 2))});
        }
    }
    return out;
}

int main(int argc, char** argv){
    JavaVM* vm; JavaVMInitArgs a{}; a.version = JNI_VERSION_1_8;
    JNI_CreateJavaVM(&vm, (void**)&env, &a);
    artFns = *env->functions;
    artFns.NewStringUTF = artNewStringUTF;
    artFns.GetStringUTFLength = artUTFLength;
    artFns.GetStringUTFRegion = artUTFRegion;
    env->functions = &artFns;
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr({"hin","eng","guj"}), arr({"hi","en","gu"}));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr({"hi","en","gu"}));
    std::ifstream in(argv[1]);
    std::string line, text; std::vector<Chunk> want;
    int cases = 0, bad = 0, chunks = 0;
    auto check = [&](){
        if (text.empty() && want.empty()) return;
        cases++; chunks += (int)want.size();
        std::vector<Chunk> got = ours(text);
        bool ok = got.size() >= want.size();
        for (size_t i = 0; ok && i < want.size(); i++)
            ok = got[i].lang == want[i].lang && got[i].text == want[i].text;
        if (!ok) {
            bad++;
            printf("DIFF: %s\n", text.c_str());
            for (auto& c : want) printf("   AutoTTS %s |%s|\n", c.lang.c_str(), c.text.c_str());
            for (auto& c : got)  printf("   ours    %s |%s|\n", c.lang.c_str(), c.text.c_str());
        }
    };
    while (std::getline(in, line)) {
        if (line.rfind("U\t", 0) == 0) { check(); text = unesc(line.substr(2)); want.clear(); }
        else if (line.rfind("E\t", 0) == 0) {
            size_t t2 = line.find('\t', 2);
            want.push_back({line.substr(2, t2 - 2), unesc(line.substr(t2 + 1))});
        }
    }
    check();
    printf("\n%d utterances, %d chunks AutoTTS spoke: %d differ\n", cases, chunks, bad);
    return bad ? 1 : 0;
}
