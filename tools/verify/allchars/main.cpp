// EVERY CHARACTER THROUGH THE REAL DETECTOR (owner, 2026-10-21: "ek character
// bhi nahin chhutna chahie ... koi bhi Bhasha ka ... Chahe Koi Bhi Lipi Ho").
//
// Reads cases.py's cases.tsv and sends each text through the core's REAL JNI
// entry points (around tools/pgo/fakejni.h, ART string behaviour, no JVM), as
// the service calls them, under the enabled set of the "@set" line above it:
//   m  mix       processDirect(..., detect) -> each record's detected spans
//   a  auto      detectWindowOrSpans -> the window answer, else the spans
//                clsCLD2.f would weigh (the longest wins)
//   d  dual      processDirect(dual) -> the chunk types (1 Latin, 2 not)
// and prints  id<TAB>mix=..<TAB>rec=<record texts><TAB>auto=..<TAB>dual=..
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
static std::string P = "eng", Q = "guj";
static jstring J(const std::string& s) { return env->NewStringUTF(s.c_str()); }
static std::string T(jobject s) { return fakejni::text((jstring)s); }
static jobjectArray arr(const std::vector<std::string>& v) {
    jobjectArray a = env->NewObjectArray((jsize)v.size(), env->FindClass("java/lang/String"), nullptr);
    for (size_t i = 0; i < v.size(); i++) env->SetObjectArrayElement(a, (jsize)i, J(v[i]));
    return a;
}
static jobjectArray direct(const std::string& text, bool dual) {
    return Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, J(text), J(P), J(Q), J(dual ? "dual" : "mix"),
        0, J(P), 0, J(Q), 0, J(Q), JNI_TRUE, JNI_FALSE, 1, J("hin"), 2, JNI_FALSE,
        dual ? JNI_FALSE : JNI_TRUE, JNI_FALSE);
}
static void mix(const std::string& text, std::string& spans, std::string& recs) {
    jobjectArray r = direct(text, false);
    jsize n = r ? env->GetArrayLength(r) : 0;
    for (jsize at = 0; at < n;) {
        int k = atoi(T(env->GetObjectArrayElement(r, at)).c_str());
        if (k >= 4) { if (!recs.empty()) recs += '\x1f'; recs += T(env->GetObjectArrayElement(r, at + 4)); }
        if (k >= 5) for (int s = 6; s + 2 <= k; s += 3) {
            if (!spans.empty()) spans += ',';
            spans += T(env->GetObjectArrayElement(r, at + s)) + "/" + (T(env->GetObjectArrayElement(r, at + s + 1)) == "1" ? "L" : "N");
        }
        else if (k >= 4) { if (!spans.empty()) spans += ','; spans += "type" + T(env->GetObjectArrayElement(r, at + 1)) + ":" + T(env->GetObjectArrayElement(r, at + 3)); }
        at += 1 + k;
    }
    if (spans.empty()) spans = "-";
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
    return out.empty() ? "-" : out;
}
static std::string autoAnswer(const std::string& text) {
    jobjectArray r = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
        env, nullptr, J(text), J(P), J(Q), JNI_FALSE, JNI_FALSE, JNI_FALSE);
    jsize n = r ? env->GetArrayLength(r) : 0;
    if (n == 0) return "-";
    std::string head = T(env->GetObjectArrayElement(r, 0));
    head = head.substr(0, head.find('\x01'));
    if (n < 5) return head;
    std::string fold = T(env->GetObjectArrayElement(r, 1));
    std::map<std::string, size_t> total; std::vector<std::string> order;
    for (jsize i = 2; i + 2 <= n - 1; i += 3) {
        std::string key = T(env->GetObjectArrayElement(r, i)) + "/" + (T(env->GetObjectArrayElement(r, i + 1)) == "1" ? "L" : "N");
        std::string t = T(env->GetObjectArrayElement(r, i + 2)); if (t.empty()) t = fold;
        if (!total.count(key)) order.push_back(key);
        total[key] += fakejni::fromUtf8(t.c_str()).size();
    }
    std::string best; size_t bestLen = 0;
    for (auto& k : order) if (total[k] > bestLen) { bestLen = total[k]; best = k; }
    return head + ">" + best;
}

int main(int argc, char** argv) {
    if (argc != 2) { fprintf(stderr, "usage: main <cases.tsv>\n"); return 2; }
    env = fakejni::env();
    std::ifstream in(argv[1]); std::string line;
    while (std::getline(in, line)) {
        if (line.empty()) continue;
        std::vector<std::string> f; std::stringstream ss(line); std::string x;
        while (std::getline(ss, x, '\t')) f.push_back(x);
        if (f[0] == "@set") {
            std::vector<std::string> iso2, iso3; std::stringstream ps(f[2]); std::string p;
            while (std::getline(ps, p, ',')) { size_t c = p.find(':'); iso2.push_back(p.substr(0, c)); iso3.push_back(p.substr(c + 1)); }
            P = f[3]; Q = f[4];
            Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(iso2));
            Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(iso2));
            fakejni::reset();
            continue;
        }
        if (f.size() < 3) continue;
        const std::string& modes = f[1]; const std::string& text = f[2];
        std::string spans = "", recs = "", a = "", d = "";
        if (modes.find('m') != std::string::npos) mix(text, spans, recs);
        if (modes.find('a') != std::string::npos) a = autoAnswer(text);
        if (modes.find('d') != std::string::npos) d = dualTypes(text);
        printf("%s\t%s\t%s\t%s\t%s\n", f[0].c_str(), spans.c_str(), recs.c_str(), a.c_str(), d.c_str());
        fakejni::reset();
    }
    return 0;
}
