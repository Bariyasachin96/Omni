// THE NATIVE CORE'S PGO DRIVER (owner, 2026-10-07: "5 ... complete test karke").
// Built by tools/pgo/run.sh with the NDK's own clang as a static x86_64-android
// executable, around a fake JNIEnv (fakejni.h), so it calls the core's REAL JNI
// entry points exactly as the service does.
//
//   driver train <corpus>   the training run: what a profile is made from
//   driver dump  <corpus>   every answer, one per line -- a PGO build and a plain
//                           build must print the same bytes (the corebytes cross
//                           product: 8 enabled sets x mix/dual x 8 mode triples x
//                           quick x smart off/1/2/3 x flow x neutral, plus auto)
//   driver time  <corpus>   microseconds per pass of real use, median of 25
#include "fakejni.h"
#include <chrono>
#include <fstream>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv*, jclass, jstring, jboolean);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jstring, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean, jboolean);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
    JNIEnv*, jclass, jstring, jstring, jstring, jboolean, jboolean, jboolean);
}

static JNIEnv* env;
static jstring U(const std::u16string& u) { return env->NewString((const jchar*)u.data(), (jsize)u.size()); }
static jstring J(const char* s) { return env->NewStringUTF(s); }
static jobjectArray arr(const std::vector<std::string>& v) {
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i].c_str()));
    return a;
}
static std::string EA(jobjectArray a) {
    if (!a) return "\\N";
    std::string o; jsize n = env->GetArrayLength(a);
    for (jsize i = 0; i < n; i++) { if (i) o += '\t'; o += fakejni::text((jstring)env->GetObjectArrayElement(a, i)); }
    return o;
}
// A corpus line: UTF-8 with \\ \t \n \0 and \uXXXX escapes (corebytes' format).
static std::u16string unesc(const std::string& s) {
    std::u16string u;
    for (size_t i = 0; i < s.size();) {
        if (s[i] == '\\' && i + 1 < s.size()) {
            char n = s[i + 1];
            if (n == 'u' && i + 5 < s.size()) { u += char16_t(strtol(s.substr(i + 2, 4).c_str(), nullptr, 16)); i += 6; continue; }
            u += n == 't' ? u'\t' : n == 'n' ? u'\n' : n == '0' ? u'\0' : char16_t((unsigned char)n); i += 2; continue;
        }
        size_t j = i + 1; while (j < s.size() && ((unsigned char)s[j] & 0xC0) == 0x80) j++;
        u += fakejni::fromUtf8(s.substr(i, j - i).c_str()); i = j;
    }
    return u;
}

static const std::vector<std::vector<std::string>> SETS = {
    {"hi","en","gu"}, {"en","hi","mr"}, {"en","ru","uk"}, {"en","ar","ur","fa"},
    {"zh","ja","ko","th"}, {}, {"en"}, {"en","hi","ar","zh","ru","gu","ta","bn"} };
static const int MODES[][3] = {{0,0,0},{1,1,1},{2,2,2},{3,3,3},{0,3,1},{2,0,3},{3,0,2},{1,2,0}};

static void useSet(const std::vector<std::string>& set) {
    std::vector<std::string> iso3;
    for (auto& c : set) iso3.push_back(c == "hi" ? "hin" : c == "en" ? "eng" : c == "gu" ? "guj" : c == "mr" ? "mar" : c);
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(set));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(set));
}
static jobjectArray direct(jstring text, bool dual, const int* m, bool quick, int sm, bool flow, int nt) {
    return Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, text, J("eng"), J("hin"), J(dual ? "dual" : "mix"),
        m[0], J("eng"), m[1], J("hin"), m[2], J("guj"), flow ? JNI_TRUE : JNI_FALSE,
        sm ? JNI_TRUE : JNI_FALSE, sm ? sm : 1, J(nt == 1 ? "eng" : "hin"), nt, JNI_FALSE,
        dual ? JNI_FALSE : JNI_TRUE, quick ? JNI_TRUE : JNI_FALSE);
}
static jobjectArray autoMode(jstring text, bool dis, bool quick) {
    return Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
        env, nullptr, text, J("eng"), J("hin"), dis ? JNI_TRUE : JNI_FALSE, JNI_TRUE, quick ? JNI_TRUE : JNI_FALSE);
}
static jobjectArray fold(jstring text, bool quick) {
    return Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, text, quick ? JNI_TRUE : JNI_FALSE);
}

int main(int argc, char** argv) {
    if (argc != 3) { fprintf(stderr, "usage: driver train|dump|time <corpus>\n"); return 2; }
    env = fakejni::env();
    std::string mode = argv[1];
    std::vector<std::u16string> texts;
    { std::ifstream in(argv[2]); std::string line; while (std::getline(in, line)) texts.push_back(unesc(line)); }

    if (mode == "dump") {
        long id = 0;
        for (auto& set : SETS) {
            useSet(set);
            for (auto& t : texts) {
                for (int dual = 0; dual < 2; dual++) for (auto& m : MODES) for (int quick = 0; quick < 2; quick++)
                for (int sm = 0; sm < 4; sm++) for (int flow = 0; flow < 2; flow++) for (int nt = 1; nt <= 2; nt++) {
                    if (dual && quick) continue;
                    jstring text = U(t);
                    printf("%ld\t%s\n", id++, EA(direct(text, dual, m, quick, sm, flow, nt)).c_str());
                    fakejni::reset();
                }
                for (int dis = 0; dis < 2; dis++) for (int quick = 0; quick < 2; quick++) {
                    jstring text = U(t);
                    printf("%ld\t%s\t\\X\t%s\n", id++, EA(autoMode(text, dis, quick)).c_str(), EA(fold(text, quick)).c_str());
                    fakejni::reset();
                }
            }
        }
        return 0;
    }
    if (mode == "train") {
        // Weighted toward what a screen reader actually sends: the mix mode with
        // its defaults (every mode int 0, smart numbers off, punctuation in
        // flow) under the owner's own set {hi, en, gu}, with the detector answer
        // cache emptied each round so the detector itself is counted. Every
        // other combination runs once, so no path is left without counts.
        for (int round = 0; round < 4; round++) {
            useSet(SETS[0]);
            for (auto& t : texts) {
                jstring text = U(t);
                direct(text, false, MODES[0], false, 0, true, 2);
                autoMode(text, false, false);
                fakejni::reset();
            }
        }
        for (auto& set : SETS) {
            useSet(set);
            for (auto& t : texts) {
                for (int dual = 0; dual < 2; dual++) for (auto& m : MODES) {
                    jstring text = U(t);
                    direct(text, dual, m, false, 0, true, 2);
                    fakejni::reset();
                }
                jstring text = U(t);
                direct(text, false, MODES[0], true, 2, false, 1);
                autoMode(text, true, true);
                fold(text, false);
                fakejni::reset();
            }
        }
        return 0;
    }
    if (mode == "time") {
        std::vector<double> cold, warm;
        for (int rep = 0; rep < 27; rep++) {
            useSet(SETS[0]);   // a new hint state empties the detection cache
            for (int pass = 0; pass < 2; pass++) {
                auto t0 = std::chrono::steady_clock::now();
                for (auto& t : texts) {
                    jstring text = U(t);
                    direct(text, false, MODES[0], false, 0, true, 2);
                    fakejni::reset();
                }
                double us = std::chrono::duration<double, std::micro>(std::chrono::steady_clock::now() - t0).count();
                if (rep >= 2) (pass ? warm : cold).push_back(us);   // two warm-up reps
            }
        }
        std::sort(cold.begin(), cold.end()); std::sort(warm.begin(), warm.end());
        printf("%.0f %.0f\n", cold[cold.size() / 2], warm[warm.size() / 2]);
        return 0;
    }
    fprintf(stderr, "unknown mode %s\n", argv[1]);
    return 2;
}
