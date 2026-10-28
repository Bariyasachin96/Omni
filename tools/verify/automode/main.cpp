// AUTO MODE ON LABELLED TEXT: the language auto and Google mode give a whole
// utterance, through the core's REAL JNI entry point (detectWindowOrSpans,
// around tools/pgo/fakejni.h, no JVM) exactly as the service calls it: the
// window answer, and when that is unknown the spans clsCLD2.f weighs (the
// longest wins) -- for every case of the language suite (cases.tsv) under
// every enabled set that holds its language, and under the owner's {hi,en,gu}.
// Built twice by run.sh, around an earlier core and the working tree's, so the
// two can be compared text by text.
//
//   main <cases.tsv> <sets.txt> <isopairs.tsv>   ->  set TAB label TAB class TAB split TAB answer
#include "fakejni.h"
#include "mlkit.h"
#include <fstream>
#include <map>
#include <sstream>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setIsoMap(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
    JNIEnv*, jclass, jstring, jstring, jstring, jboolean, jboolean, jboolean);
}

static JNIEnv* env;
static std::map<std::string, std::string> iso3Of;
static jstring J(const std::string& s) { return env->NewStringUTF(s.c_str()); }
static std::string T(jobject s) { return fakejni::text((jstring)s); }
static jobjectArray arr(const std::vector<std::string>& v) {
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i]));
    return a;
}
static std::string iso3(const std::string& c) { auto it = iso3Of.find(c); return it == iso3Of.end() ? c : it->second; }
static void useSet(const std::vector<std::string>& set) {
    std::vector<std::string> i3;
    for (auto& c : set) i3.push_back(iso3(c));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(i3), arr(set));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(set));
}
static std::string autoAnswer(const std::string& text) {
    jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
        env, nullptr, J(text), J("eng"), J("hin"), JNI_FALSE, JNI_FALSE, JNI_FALSE);
    jsize n = r ? env->GetArrayLength(r) : 0;
    if (n == 0) return "-";
    std::string head = T(env->GetObjectArrayElement(r, 0));
    head = head.substr(0, head.find('\x01'));
    if (n < 5) return head;
    // clsCLD2.f: total UTF-16 length per lang|latin, the biggest wins (ties: first seen)
    std::string fold = T(env->GetObjectArrayElement(r, 1));
    std::map<std::string, size_t> total; std::vector<std::string> order;
    for (jsize i = 2; i + 2 <= n - 1; i += 3) {
        std::string key = T(env->GetObjectArrayElement(r, i));
        std::string t = T(env->GetObjectArrayElement(r, i + 2)); if (t.empty()) t = fold;
        if (!total.count(key)) order.push_back(key);
        total[key] += fakejni::fromUtf8(t.c_str()).size();
    }
    std::string best; size_t bestLen = 0;
    for (auto& k : order) if (total[k] > bestLen) { bestLen = total[k]; best = k; }
    return best.empty() ? "-" : best;
}

int main(int argc, char** argv) {
    // ML Kit as the language identifier, for a core that asks one (tools/mlkit)
    installMlKit();
    if (argc != 4) { fprintf(stderr, "usage: main <cases.tsv> <sets.txt> <isopairs.tsv>\n"); return 2; }
    env = fakejni::env();
    { std::ifstream in(argv[3]); std::string l; std::vector<std::string> a, b;
      while (std::getline(in, l)) { size_t t = l.find('\t'); if (t == std::string::npos) continue;
          a.push_back(l.substr(0, t)); b.push_back(l.substr(t + 1)); iso3Of[a.back()] = b.back(); }
      if (a.empty()) { fprintf(stderr, "no ISO pairs\n"); return 2; }
      Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setIsoMap(env, nullptr, arr(a), arr(b));
      fakejni::reset(); }
    std::vector<std::vector<std::string>> sets = {{"hi", "en", "gu"}};
    { std::ifstream in(argv[2]); std::string l;
      while (std::getline(in, l)) { if (l.empty() || l[0] == '#') continue;
          std::stringstream ss(l); std::vector<std::string> s; std::string w; while (ss >> w) s.push_back(w);
          if (!s.empty()) sets.push_back(s); } }
    struct Case { std::string label, cls, split, text; };
    std::vector<Case> cases;
    { std::ifstream in(argv[1]); std::string l;
      while (std::getline(in, l)) { std::vector<std::string> f; std::stringstream ss(l); std::string c;
          while (std::getline(ss, c, '\t')) f.push_back(c);
          if (f.size() == 5) cases.push_back({f[0], f[2], f[3], f[4]}); } }
    for (auto& set : sets) {
        useSet(set);
        std::string name; for (auto& c : set) name += (name.empty() ? "" : "+") + c;
        for (auto& c : cases) {
            bool in = false; for (auto& x : set) in |= x == c.label;
            if (!in) continue;
            printf("%s\t%s\t%s\t%s\t%s\n", name.c_str(), c.label.c_str(), c.cls.c_str(), c.split.c_str(), autoAnswer(c.text).c_str());
            fakejni::reset();
        }
    }
    return 0;
}
