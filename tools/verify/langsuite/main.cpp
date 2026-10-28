// The core's choice of voice for labelled text, with ML Kit as its language
// identifier (tools/mlkit: Google's own library, as on a phone). For every set of
// sets.txt and every case in a language of that set, the text goes through the
// real JNI entry point mix mode detects with (nativeFoldAndDetect, fakejni) and
// the answer is the language its spans give the most bytes of the case's own
// script.
//
//   main cases.tsv sets.txt isopairs.tsv > choices.tsv
//   choices.tsv: set TAB label TAB class TAB split TAB slot-sharers TAB chosen TAB text
// (slot-sharers: how many languages of the set write the case's script.)
#include "fakejni.h"
#include "mlkit.h"
#include <fstream>
#include <map>
#include <sstream>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setIsoMap(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv*, jclass, jstring, jboolean);
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
// The script slot each language of the sets writes (the core's languageSlot,
// for the languages sets.txt names).
static int slotOf(const std::string& c) {
    static const std::map<std::string, int> kSlot = {
        {"ru", 2}, {"uk", 2}, {"bg", 2}, {"sr", 2}, {"mk", 2}, {"be", 2}, {"kk", 2},
        {"ar", 3}, {"fa", 3}, {"ur", 3}, {"ps", 3}, {"sd", 3},
        {"hi", 4}, {"mr", 4}, {"ne", 4}, {"sa", 4}, {"zh", 5}, {"ja", 5}, {"bn", 6}, {"as", 6}, {"gu", 12}};
    auto it = kSlot.find(c);
    return it == kSlot.end() ? 1 : it->second;
}
static std::string chosen(const std::string& text, int slot) {
    jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, J(text), JNI_FALSE);
    jsize n = r ? env->GetArrayLength(r) : 0;
    if (n < 4) return "-";
    std::string fold = T(env->GetObjectArrayElement(r, 0));
    std::map<std::string, size_t> bytes;
    std::vector<std::string> order;
    for (jsize i = 1; i + 2 < n; i += 3) {
        std::string lang = T(env->GetObjectArrayElement(r, i));
        std::string t = T(env->GetObjectArrayElement(r, i + 2));
        if (t.empty()) t = fold;
        if (slotOf(lang) != slot) continue;
        if (!bytes.count(lang)) order.push_back(lang);
        bytes[lang] += t.size();
    }
    std::string best = "-";
    size_t most = 0;
    for (auto& l : order) if (bytes[l] > most) { most = bytes[l]; best = l; }
    return best;
}

int main(int argc, char** argv) {
    if (argc != 4) { fprintf(stderr, "usage: main <cases.tsv> <sets.txt> <isopairs.tsv>\n"); return 2; }
    env = fakejni::env();
    if (!installMlKit()) { fprintf(stderr, "ML Kit is not installed (EV_MLKIT_MODEL)\n"); return 2; }
    { std::ifstream in(argv[3]); std::string l; std::vector<std::string> a, b;
      while (std::getline(in, l)) { size_t t = l.find('\t'); if (t == std::string::npos) continue;
          a.push_back(l.substr(0, t)); b.push_back(l.substr(t + 1)); iso3Of[a.back()] = b.back(); }
      if (a.empty()) { fprintf(stderr, "no ISO pairs\n"); return 2; }
      Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setIsoMap(env, nullptr, arr(a), arr(b));
      fakejni::reset(); }
    std::vector<std::vector<std::string>> sets;
    { std::ifstream in(argv[2]); std::string l;
      while (std::getline(in, l)) { if (l.empty() || l[0] == '#') continue;
          std::stringstream ss(l); std::vector<std::string> s; std::string w; while (ss >> w) s.push_back(w);
          if (!s.empty()) sets.push_back(s); } }
    struct Case { std::string label, cls, split, text; int slot; };
    std::vector<Case> cases;
    { std::ifstream in(argv[1]); std::string l;
      while (std::getline(in, l)) { std::vector<std::string> f; std::stringstream ss(l); std::string c;
          while (std::getline(ss, c, '\t')) f.push_back(c);
          if (f.size() == 5) cases.push_back({f[0], f[2], f[3], f[4], atoi(f[1].c_str())}); } }
    for (auto& set : sets) {
        useSet(set);
        std::string name; for (auto& c : set) name += (name.empty() ? "" : "+") + c;
        for (auto& c : cases) {
            bool in = false; for (auto& x : set) in |= x == c.label;
            if (!in || c.slot < 1) continue;
            int sharers = 0; for (auto& x : set) sharers += slotOf(x) == c.slot;
            printf("%s\t%s\t%s\t%s\t%d\t%s\t%s\n", name.c_str(), c.label.c_str(), c.cls.c_str(), c.split.c_str(),
                   sharers, chosen(c.text, c.slot).c_str(), c.text.c_str());
            fakejni::reset();
        }
    }
    return 0;
}
