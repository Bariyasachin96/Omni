// The real core on text of two languages of one script: mix mode's own path
// (processDirect with detection, as the service calls it, around
// tools/pgo/fakejni.h) under an enabled set. The core is compiled INTO this
// file so that the context (langblend.h gBlendContext) can be cleared between
// texts that are not read one after another.
//   probe < commands
//     SET <TAB> iso2,... <TAB> iso3,...   the enabled languages (pushLanguageSets)
//     RESET                              forget the last sure language of every script
//     TEXT <TAB> id <TAB> text           -> id, then <TAB> lang 0x1F text per span
#include "fakejni.h"
#include "tts_engine_core.cpp"
#include <iostream>
#include <sstream>

static JNIEnv* env;
static jstring J(const std::string& s) { return env->NewStringUTF(s.c_str()); }
static std::string T(jobject s) { return fakejni::text((jstring)s); }
static jobjectArray arr(const std::vector<std::string>& v) {
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i]));
    return a;
}
static std::vector<std::string> split(const std::string& s, char c) {
    std::vector<std::string> out;
    std::stringstream in(s);
    std::string x;
    while (std::getline(in, x, c)) out.push_back(x);
    return out;
}

int main() {
    env = fakejni::env();
    std::string line, nonLatin = "eng";
    while (std::getline(std::cin, line)) {
        std::vector<std::string> f = split(line, '\t');
        if (f.empty()) continue;
        if (f[0] == "SET" && f.size() == 3) {
            std::vector<std::string> iso2 = split(f[1], ','), iso3 = split(f[2], ',');
            Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(iso2));
            Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(iso2));
            nonLatin = "eng";
            for (const std::string& c : iso3) if (c != "eng") { nonLatin = c; break; }
            fakejni::reset();
        } else if (f[0] == "RESET") {
            std::lock_guard<std::mutex> lock(gBlendContextMutex);
            for (std::string& c : gBlendContext) c.clear();
        } else if (f[0] == "TEXT" && f.size() >= 3) {
            std::string text = f[2];
            for (size_t i = 0; i < text.size(); i++)
                if (text[i] == '\\' && i + 1 < text.size() && text[i + 1] == 'n') text.replace(i, 2, "\n");
            jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
                env, nullptr, J(text), J("eng"), J(nonLatin), J("mix"), 0, J("eng"), 0, J(nonLatin), 0, J(nonLatin),
                JNI_TRUE, JNI_FALSE, 1, J(nonLatin), 2, JNI_FALSE, JNI_TRUE, JNI_FALSE);
            printf("%s", f[1].c_str());
            jsize n = r ? env->GetArrayLength(r) : 0;
            for (jsize at = 0; at < n;) {
                const int k = atoi(T(env->GetObjectArrayElement(r, at)).c_str());
                if (k >= 5) {
                    const std::string rec = T(env->GetObjectArrayElement(r, at + 4));
                    for (int s = 6; s + 2 <= k; s += 3) {
                        std::string t = T(env->GetObjectArrayElement(r, at + s + 2));
                        if (t.empty()) t = rec;
                        printf("\t%s\x1F%s", T(env->GetObjectArrayElement(r, at + s)).c_str(), t.c_str());
                    }
                } else if (k >= 4) {
                    printf("\ttype%s:%s\x1F%s", T(env->GetObjectArrayElement(r, at + 1)).c_str(),
                           T(env->GetObjectArrayElement(r, at + 3)).c_str(), T(env->GetObjectArrayElement(r, at + 4)).c_str());
                }
                at += 1 + k;
            }
            printf("\n");
            fflush(stdout);
            fakejni::reset();
        }
    }
    return 0;
}
