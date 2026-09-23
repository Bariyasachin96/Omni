// WHICH LANGUAGE A SHARED-SCRIPT SPAN REALLY GETS, asked of the REAL detector.
//
// The owner reported it precisely (2026-09-11): with Hindi, Gujarati and
// Marathi all enabled, MARATHI text is spoken by the HINDI voice, whichever
// order the languages are listed in -- and Gujarati is always right. They asked
// for the general case, not just Marathi: "yah maine sirf Marathi ka example
// diya hai ... aur bhi languages mein honge."
//
// So this links the REAL tts_engine_core.cpp with CLD2 and a genuine JNIEnv,
// pushes a real enabled set through setLanguageHints/setDetectSets exactly as
// pushLanguageSets does, and asks nativeGetLanguages what each span gets. It
// also calls CLD2 DIRECTLY with the same hints so the summary, the top three
// and their percentages are printed beside the answer -- which is the only way
// to tell "the detector was wrong" from "our filter dropped a right answer".
//
// Gujarati, Tamil, Telugu and the rest are decided by their Unicode SCRIPT and
// can never take part in this; only the scripts CLD2 is actually asked about
// can -- Devanagari, Cyrillic, Arabic, Bengali and CJK -- so those are what it
// covers.
#include <jni.h>
#include <cstdio>
#include <cstring>
#include <string>
#include <vector>
#include "cld2_src/public/compact_lang_det.h"
#include "cld2_src/public/encodings.h"

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeGetLanguages(JNIEnv*, jclass, jstring);
JNIEXPORT jstring JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jobject, jint, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean);
}

static JNIEnv* env = nullptr;
static jobjectArray arr(const std::vector<std::string>& v){
    jclass sc = env->FindClass("java/lang/String");
    jobjectArray a = env->NewObjectArray((jsize)v.size(), sc, nullptr);
    for (size_t i = 0; i < v.size(); i++)
        env->SetObjectArrayElement(a, (jsize)i, env->NewStringUTF(v[i].c_str()));
    return a;
}
static std::string str(jstring s){
    const char* c = env->GetStringUTFChars(s, nullptr);
    std::string out = c ? c : "";
    if (c) env->ReleaseStringUTFChars(s, c);
    return out;
}

// The app's own push, so the hint tables are built exactly as they are on the
// phone: iso2 codes for the hints, iso3 for the detect sets.
struct Lang { const char* iso2; const char* iso3; };
static void enable(const std::vector<Lang>& set){
    std::vector<std::string> iso2, iso3;
    for (auto& l : set) { iso2.push_back(l.iso2); iso3.push_back(l.iso3); }
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(iso2));
    Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(iso2));
}

// What the app answers for the WHOLE text: one span per script run, so a
// single-language sentence gives one span and that span's language is the voice.
static std::string appAnswer(const std::string& text){
    jstring js = env->NewStringUTF(text.c_str());
    jobjectArray got = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeGetLanguages(env, nullptr, js);
    if (!got) return "<null>";
    jsize n = env->GetArrayLength(got);
    // triples: lang, isLatin, text -- take the language of the LONGEST span
    std::string best; size_t bestLen = 0;
    for (jsize i = 0; i + 2 < n; i += 3) {
        std::string lang = str((jstring)env->GetObjectArrayElement(got, i));
        std::string txt  = str((jstring)env->GetObjectArrayElement(got, i + 2));
        if (txt.size() >= bestLen) { bestLen = txt.size(); best = lang; }
    }
    return best.empty() ? "<none>" : best;
}

// CLD2 asked DIRECTLY, with the same per-script hint the core would pass, so
// the ranking behind the answer is visible rather than inferred.
static std::string cldDetail(const std::string& text, CLD2::Language scriptHint){
    CLD2::CLDHints hints = {nullptr, nullptr, CLD2::UNKNOWN_ENCODING, scriptHint};
    CLD2::Language l3[3]; int p3[3]; double s3[3];
    int bytes = 0; bool reliable = false;
    CLD2::Language summary = CLD2::ExtDetectLanguageSummary(
        text.c_str(), (int)text.size(), true, &hints, 0x4000, l3, p3, s3, nullptr, &bytes, &reliable);
    char buf[256];
    const char* sc = CLD2::LanguageCode(summary);
    snprintf(buf, sizeof buf, "summary=%-8s rel=%d  top3=[%s %d%%, %s %d%%, %s %d%%]",
             sc ? sc : "?", reliable ? 1 : 0,
             CLD2::LanguageCode(l3[0]) ? CLD2::LanguageCode(l3[0]) : "?", p3[0],
             CLD2::LanguageCode(l3[1]) ? CLD2::LanguageCode(l3[1]) : "?", p3[1],
             CLD2::LanguageCode(l3[2]) ? CLD2::LanguageCode(l3[2]) : "?", p3[2]);
    return buf;
}

// THE REAL SEQUENCE, not a shortcut. The app never asks the detector about the
// whole utterance in mix or multilingual mode: processDirect (buildMixChunks)
// cuts it into segments FIRST, and only then is each segment detected. A short
// segment is a different question from a whole sentence, which is exactly how
// the CLD3 reports of 2026-09-02 went wrong twice, so this runs both.
static void mixChunks(const std::string& text, const char* latin, const char* nonLatin,
                      std::vector<std::string>& out){
    out.clear();
    jobject buf = env->NewDirectByteBuffer((void*)text.data(), (jlong)text.size());
    jstring js = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, buf, (jint)text.size(),
        env->NewStringUTF(latin), env->NewStringUTF(nonLatin), env->NewStringUTF("mix"),
        0, env->NewStringUTF(""), 0, env->NewStringUTF(""), 0, env->NewStringUTF(""),
        JNI_TRUE, JNI_FALSE, 1, env->NewStringUTF(latin), 1, JNI_FALSE);
    if (!js) return;
    std::string packed = str(js);
    size_t at = 0;
    while (at < packed.size()) {
        size_t rec = packed.find('\x1E', at);
        std::string one = packed.substr(at, rec == std::string::npos ? std::string::npos : rec - at);
        at = (rec == std::string::npos) ? packed.size() : rec + 1;
        // type \x1F kind \x1F lang \x1F text -- we want the text
        size_t f1 = one.find('\x1F'); if (f1 == std::string::npos) continue;
        size_t f2 = one.find('\x1F', f1 + 1); if (f2 == std::string::npos) continue;
        size_t f3 = one.find('\x1F', f2 + 1); if (f3 == std::string::npos) continue;
        std::string t = one.substr(f3 + 1);
        if (!t.empty()) out.push_back(t);
    }
}

struct Case { const char* want; const char* text; };
static int failures = 0, total = 0;

static void run(const char* title, const std::vector<Lang>& set,
                CLD2::Language scriptHintWhenAlone, const std::vector<Case>& cases){
    printf("\n== %s ==\n   enabled:", title);
    for (auto& l : set) printf(" %s", l.iso2);
    printf("\n");
    enable(set);
    for (auto& c : cases) {
        std::string got = appAnswer(c.text);
        bool ok = got == c.want;
        total++; if (!ok) failures++;
        printf("   %-4s want %-3s got %-4s  %-3s  %s\n", ok ? "ok" : "MISS",
               c.want, got.c_str(), "", cldDetail(c.text, scriptHintWhenAlone).c_str());
        printf("        %.*s\n", 72, c.text);
    }
}

int main(){
    JavaVM* vm = nullptr;
    JavaVMInitArgs a{}; a.version = JNI_VERSION_1_6; a.nOptions = 0;
    if (JNI_CreateJavaVM(&vm, (void**)&env, &a) != JNI_OK) { printf("no JVM\n"); return 2; }

    // ---- DEVANAGARI: Hindi vs Marathi, the reported case --------------------
    run("Devanagari, the owner's set", {{"en","eng"},{"gu","guj"},{"hi","hin"},{"mr","mar"}},
        CLD2::UNKNOWN_LANGUAGE, {
        {"mr", "ही बैठक आयोजित करण्याचा उद्देश काय आहे"},
        {"mr", "मला मराठीत वाचायला खूप आवडते"},
        {"mr", "माझे नाव सचिन आहे आणि मी पुण्यात राहतो"},
        {"mr", "तुम्ही कसे आहात"},
        {"mr", "नवीन चॅट"},
        {"hi", "यह बैठक का उद्देश्य क्या है"},
        {"hi", "मुझे हिंदी में पढ़ना बहुत पसंद है"},
        {"hi", "मेरा नाम सचिन है और मैं पुणे में रहता हूँ"},
        {"hi", "आप कैसे हैं"},
        {"hi", "नई चैट"},
    });

    // ---- the same text with ONLY Marathi enabled ----------------------------
    run("Devanagari, only Marathi enabled", {{"en","eng"},{"mr","mar"}},
        CLD2::MARATHI, {
        {"mr", "ही बैठक आयोजित करण्याचा उद्देश काय आहे"},
        {"mr", "नवीन चॅट"},
    });

    // ---- CYRILLIC: Russian vs Ukrainian ------------------------------------
    run("Cyrillic, Russian + Ukrainian", {{"en","eng"},{"ru","rus"},{"uk","ukr"}},
        CLD2::UNKNOWN_LANGUAGE, {
        {"uk", "Це зустріч організована для обговорення важливих питань"},
        {"ru", "Это встреча организована для обсуждения важных вопросов"},
    });

    // ---- ARABIC: Arabic vs Urdu vs Persian ---------------------------------
    run("Arabic script, Arabic + Urdu + Persian", {{"en","eng"},{"ar","ara"},{"ur","urd"},{"fa","fas"}},
        CLD2::UNKNOWN_LANGUAGE, {
        {"ur", "یہ میٹنگ اہم امور پر تبادلہ خیال کے لیے منعقد کی گئی ہے"},
        {"ar", "هذا الاجتماع منعقد لمناقشة القضايا المهمة"},
        {"fa", "این جلسه برای بحث در مورد مسائل مهم برگزار شده است"},
    });

    // ---- THE REAL PATH: segment first, then detect each segment -------------
    printf("\n== the REAL mix path: processDirect, then detect each chunk ==\n");
    printf("   enabled: en gu hi mr   (non-Latin preferred = hin, Latin = eng)\n");
    enable({{"en","eng"},{"gu","guj"},{"hi","hin"},{"mr","mar"}});
    const char* real[] = {
        "ही बैठक आयोजित करण्याचा उद्देश काय आहे",
        "माझे नाव Sachin Bariya आहे आणि मी पुण्यात राहतो",
        "नवीन चॅट",
        "मायक्रोफोन",
        "साइडबार बंद करा",
        "यह बैठक का उद्देश्य क्या है",
        "मेरा नाम Sachin Bariya है और मैं पुणे में रहता हूँ",
    };
    for (const char* t : real) {
        std::vector<std::string> chunks;
        mixChunks(t, "eng", "hin", chunks);
        printf("   %.*s\n", 64, t);
        for (auto& c : chunks)
            printf("        chunk -> %-4s   %.*s\n", appAnswer(c).c_str(), 52, c.c_str());
    }

    printf("\n%d of %d whole-text cases got the language the user chose\n", total - failures, total);
    return 0;
}
