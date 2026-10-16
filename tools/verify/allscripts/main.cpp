// EVERY UNICODE SCRIPT THROUGH THE REAL DETECTOR, IN EVERY MODE (owner,
// 2026-10-16: "Unicode script mein kuchh chijen aisi hogi jo detect nahin hoti
// ... sabhi mode mein ... latest Unicode ... jo available na ho vah provide").
//
// One sample per script (texts.py, from the UCD the app is generated from) is
// sent through the core's REAL JNI entry points, around tools/pgo/fakejni.h (ART
// string behaviour, no JVM), exactly as the service calls them:
//   mix/multilingual  processDirect(..., detect) -> each record's detected spans
//   auto/google       detectWindowOrSpans -> the window answer, else the spans
//                     clsCLD2.f would weigh (the longest wins)
//   dual              processDirect(dual) -> the chunk type (1 Latin, 2 not)
// under the owner's set {hi, en, gu}, and once more with the script's own
// language enabled too.
//
//   main <scripts.tsv> <script-lang.tsv>   one line per script
#include "fakejni.h"
#include <fstream>
#include <map>
#include <sstream>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jstring, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean, jboolean);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
    JNIEnv*, jclass, jstring, jstring, jstring, jboolean, jboolean, jboolean);
}

static JNIEnv* env;
static jstring J(const std::string& s) { return env->NewStringUTF(s.c_str()); }
static std::string T(jobject s) { return fakejni::text((jstring)s); }
static jobjectArray arr(const std::vector<std::string>& v) {
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i]));
    return a;
}
// iso2 for the hints, iso3 for the detect sets -- pushLanguageSets' own split.
static void useSet(const std::vector<std::pair<std::string, std::string>>& set) {
    std::vector<std::string> iso2, iso3;
    for (auto& p : set) { iso2.push_back(p.first); iso3.push_back(p.second); }
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(iso2));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(iso2));
}
static jobjectArray direct(const std::string& text, bool dual) {
    return Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, J(text), J("eng"), J("hin"), J(dual ? "dual" : "mix"),
        0, J("eng"), 0, J("hin"), 0, J("guj"), JNI_TRUE, JNI_FALSE, 1, J("hin"), 2, JNI_FALSE,
        dual ? JNI_FALSE : JNI_TRUE, JNI_FALSE);
}
// "lang/L" or "lang/N" per span, joined by ','; "" span text means the whole record.
static std::string mixAnswer(const std::string& text) {
    jobjectArray r = direct(text, false);
    std::string out;
    jsize n = r ? env->GetArrayLength(r) : 0;
    for (jsize at = 0; at < n;) {
        int k = atoi(T(env->GetObjectArrayElement(r, at)).c_str());
        if (k >= 5) for (int s = 6; s + 2 <= k; s += 3) {
            if (!out.empty()) out += ',';
            out += T(env->GetObjectArrayElement(r, at + s)) + "/" + (T(env->GetObjectArrayElement(r, at + s + 1)) == "1" ? "L" : "N");
        }
        else if (k >= 4) { if (!out.empty()) out += ','; out += "type" + T(env->GetObjectArrayElement(r, at + 1)) + ":" + T(env->GetObjectArrayElement(r, at + 3)); }
        at += 1 + k;
    }
    return out.empty() ? "-" : out;
}
static std::string dualTypes(const std::string& text) {
    jobjectArray r = direct(text, true);
    std::string out;
    jsize n = r ? env->GetArrayLength(r) : 0;
    for (jsize at = 0; at < n;) {
        int k = atoi(T(env->GetObjectArrayElement(r, at)).c_str());
        if (k >= 1) out += T(env->GetObjectArrayElement(r, at + 1));
        at += 1 + k;
    }
    return out;
}
static std::string autoAnswer(const std::string& text) {
    jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
        env, nullptr, J(text), J("eng"), J("hin"), JNI_FALSE, JNI_FALSE, JNI_FALSE);
    jsize n = r ? env->GetArrayLength(r) : 0;
    if (n == 0) return "\\N";
    std::string head = T(env->GetObjectArrayElement(r, 0));
    head = head.substr(0, head.find('\x01'));
    if (n < 5) return head;
    // clsCLD2.f: total UTF-16 length per lang|latin, the biggest wins (ties: first seen)
    std::string fold = T(env->GetObjectArrayElement(r, 1));
    std::map<std::string, size_t> total; std::vector<std::string> order;
    for (jsize i = 2; i + 2 < n + 0 && i + 2 <= n - 1; i += 3) {
        std::string key = T(env->GetObjectArrayElement(r, i)) + "/" + (T(env->GetObjectArrayElement(r, i + 1)) == "1" ? "L" : "N");
        std::string t = T(env->GetObjectArrayElement(r, i + 2)); if (t.empty()) t = fold;
        if (!total.count(key)) order.push_back(key);
        total[key] += fakejni::fromUtf8(t.c_str()).size();
    }
    std::string best; size_t bestLen = 0;
    for (auto& k : order) if (total[k] > bestLen) { bestLen = total[k]; best = k; }
    return head + " -> " + best;
}

int main(int argc, char** argv) {
    if (argc != 3) { fprintf(stderr, "usage: main <scripts.tsv> <script-lang.tsv>\n"); return 2; }
    env = fakejni::env();
    std::map<std::string, std::pair<std::string, std::string>> own;   // script -> (code, iso3)
    { std::ifstream in(argv[2]); std::string l; while (std::getline(in, l)) {
        std::stringstream ss(l); std::string s, c, i3; std::getline(ss, s, '\t'); std::getline(ss, c, '\t'); std::getline(ss, i3, '\t');
        if (!s.empty()) own[s] = {c, i3.empty() ? c : i3}; } }
    const std::vector<std::pair<std::string, std::string>> owner = {{"hi", "hin"}, {"en", "eng"}, {"gu", "guj"}};
    std::ifstream in(argv[1]); std::string line;
    while (std::getline(in, line)) {
        size_t tab = line.find('\t'); if (tab == std::string::npos) continue;
        std::string script = line.substr(0, tab), text = line.substr(tab + 1);
        useSet(owner);
        std::string m1 = mixAnswer(text), a1 = autoAnswer(text), d1 = dualTypes(text);
        std::string m2 = "-", a2 = "-";
        auto it = own.find(script);
        if (it != own.end() && !it->second.first.empty()) {
            auto set = owner; set.push_back(it->second);
            useSet(set);
            m2 = mixAnswer(text); a2 = autoAnswer(text);
        }
        printf("%s\t%s\tmix=%s\tauto=%s\tdual=%s\t| +own: mix=%s\tauto=%s\n", script.c_str(),
               it != own.end() ? it->second.first.c_str() : "?", m1.c_str(), a1.c_str(), d1.c_str(), m2.c_str(), a2.c_str());
        fakejni::reset();
    }
    return 0;
}
