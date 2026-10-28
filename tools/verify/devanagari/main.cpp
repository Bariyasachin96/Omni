// WHICH LANGUAGE A SHARED-SCRIPT SPAN REALLY GETS, asked of the REAL detector.
//
// The owner reported it precisely (2026-09-11): with Hindi, Gujarati and
// Marathi all enabled, MARATHI text is spoken by the HINDI voice, whichever
// order the languages are listed in -- and Gujarati is always right. They asked
// for the general case, not just Marathi: "yah maine sirf Marathi ka example
// diya hai ... aur bhi languages mein honge."
//
// So this links the REAL tts_engine_core.cpp and a genuine JNIEnv, pushes a
// real enabled set through setLanguageHints/setDetectSets exactly as
// pushLanguageSets does, and asks nativeFoldAndDetect what each span gets. It
// also asks ML Kit (Google's own library, run here by tools/mlkit, as the core
// asks it on a phone) DIRECTLY and prints its top three beside the answer --
// the way to tell "ML Kit was wrong" from "the choice among the enabled
// languages dropped a right answer". (Until 2026-10-21 that column was CLD2's
// summary and top three; until 2026-10-28 the model ML Kit runs, our copy.)
//
// Gujarati, Tamil, Telugu and the rest are decided by their Unicode SCRIPT and
// can never take part in this; only the scripts several languages share can --
// Devanagari, Cyrillic, Arabic, Bengali and CJK -- so those are what it covers.
#include <jni.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>
#include <algorithm>
#include "mlkit.h"

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv*, jclass, jstring, jboolean);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jstring, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean, jboolean);
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
    jobjectArray got = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, js, JNI_FALSE);
    if (!got) return "<null>";
    jsize n = env->GetArrayLength(got);
    // triples: lang, isLatin, text -- take the language of the LONGEST span
    std::string best; size_t bestLen = 0;
    for (jsize i = 1; i + 2 < n; i += 3) {
        std::string lang = str((jstring)env->GetObjectArrayElement(got, i));
        std::string txt  = str((jstring)env->GetObjectArrayElement(got, i + 2));
        if (txt.size() >= bestLen) { bestLen = txt.size(); best = lang; }
    }
    return best.empty() ? "<none>" : best;
}

// ML Kit, asked DIRECTLY: its three most likely languages, so the ranking
// behind the answer is visible rather than inferred.
static std::string modelDetail(const std::string& text){
    char tags[112][16];
    float p[112];
    const int n = evmlkit_identify(text.c_str(), text.size(), tags, p, 112);
    if (n <= 0) return "ML Kit: no answer";
    std::vector<int> order;
    for (int k = 0; k < n; k++) order.push_back(k);
    std::sort(order.begin(), order.end(), [&](int x, int y){ return p[x] > p[y]; });
    std::string out = "ML Kit top3=[";
    for (int k = 0; k < 3 && k < n; k++) {
        char one[48];
        snprintf(one, sizeof one, "%s%s %.0f%%", k ? ", " : "", tags[order[k]], 100 * p[order[k]]);
        out += one;
    }
    return out + "]";
}

// THE REAL SEQUENCE, not a shortcut. The app never asks the detector about the
// whole utterance in mix or multilingual mode: processDirect (buildMixChunks)
// cuts it into segments FIRST, and only then is each segment detected. A short
// segment is a different question from a whole sentence, which is exactly how
// the CLD3 reports of 2026-09-02 went wrong twice, so this runs both.
static void mixChunks(const std::string& text, const char* latin, const char* nonLatin,
                      std::vector<std::string>& out){
    out.clear();
    jobjectArray js = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, env->NewStringUTF(text.c_str()),
        env->NewStringUTF(latin), env->NewStringUTF(nonLatin), env->NewStringUTF("mix"),
        0, env->NewStringUTF(""), 0, env->NewStringUTF(""), 0, env->NewStringUTF(""),
        JNI_TRUE, JNI_FALSE, 1, env->NewStringUTF(latin), 1, JNI_FALSE, JNI_FALSE, JNI_FALSE);
    if (!js) return;
    // records: a field count, then type / kind / lang / text -- we want the text
    jsize total = env->GetArrayLength(js);
    for (jsize at = 0; at < total;) {
        int n = atoi(str((jstring)env->GetObjectArrayElement(js, at)).c_str());
        if (n >= 4) {
            std::string t = str((jstring)env->GetObjectArrayElement(js, at + 4));
            if (!t.empty()) out.push_back(t);
        }
        at += 1 + n;
    }
}

struct Case { const char* want; const char* text; };
static int failures = 0, total = 0;

static void run(const char* title, const std::vector<Lang>& set,
                const std::vector<Case>& cases){
    printf("\n== %s ==\n   enabled:", title);
    for (auto& l : set) printf(" %s", l.iso2);
    printf("\n");
    enable(set);
    for (auto& c : cases) {
        std::string got = appAnswer(c.text);
        bool ok = got == c.want;
        total++; if (!ok) failures++;
        printf("   %-4s want %-3s got %-4s  %-3s  %s\n", ok ? "ok" : "MISS",
               c.want, got.c_str(), "", modelDetail(c.text).c_str());
        printf("        %.*s\n", 72, c.text);
    }
}

int main(){
    // ML Kit as the core's language identifier (tools/mlkit), as on a phone
    if (!installMlKit()) { printf("ML Kit is not installed (EV_MLKIT_MODEL)\n"); return 2; }
    JavaVM* vm = nullptr;
    JavaVMInitArgs a{}; a.version = JNI_VERSION_1_6; a.nOptions = 0;
    if (JNI_CreateJavaVM(&vm, (void**)&env, &a) != JNI_OK) { printf("no JVM\n"); return 2; }

    // ---- DEVANAGARI: Hindi vs Marathi, the reported case --------------------
    run("Devanagari, the owner's set", {{"en","eng"},{"gu","guj"},{"hi","hin"},{"mr","mar"}}, {
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
    run("Devanagari, only Marathi enabled", {{"en","eng"},{"mr","mar"}}, {
        {"mr", "ही बैठक आयोजित करण्याचा उद्देश काय आहे"},
        {"mr", "नवीन चॅट"},
    });

    // ---- CYRILLIC: Russian vs Ukrainian ------------------------------------
    run("Cyrillic, Russian + Ukrainian", {{"en","eng"},{"ru","rus"},{"uk","ukr"}}, {
        {"uk", "Це зустріч організована для обговорення важливих питань"},
        {"ru", "Это встреча организована для обсуждения важных вопросов"},
    });

    // ---- ARABIC: Arabic vs Urdu vs Persian ---------------------------------
    run("Arabic script, Arabic + Urdu + Persian", {{"en","eng"},{"ar","ara"},{"ur","urd"},{"fa","fas"}}, {
        {"ur", "یہ میٹنگ اہم امور پر تبادلہ خیال کے لیے منعقد کی گئی ہے"},
        {"ar", "هذا الاجتماع منعقد لمناقشة القضايا المهمة"},
        {"fa", "این جلسه برای بحث در مورد مسائل مهم برگزار شده است"},
    });

    // ---- EVERY SCRIPT: one sentence per language (owner, 2026-10-07:
    //      "sabhi lipiyon ka sahi detection") ---------------------------------
    run("Script-fixed languages, all enabled together",
        {{"en","eng"},{"el","ell"},{"hy","hye"},{"iw","heb"},{"ka","kat"},{"pa","pan"},{"gu","guj"},
         {"or","ori"},{"ta","tam"},{"te","tel"},{"kn","kan"},{"ml","mal"},{"si","sin"},{"th","tha"},
         {"lo","lao"},{"bo","bod"},{"my","mya"},{"km","khm"},{"am","amh"},{"ko","kor"}}, {
        {"el", "Αυτή η συνάντηση οργανώθηκε για να συζητηθούν σημαντικά θέματα"},
        {"hy", "Այս հանդիպումը կազմակերպվել է կարևոր հարցեր քննարկելու համար"},
        {"iw", "הפגישה הזאת אורגנה כדי לדון בנושאים חשובים"},
        {"ka", "ეს შეხვედრა მოეწყო მნიშვნელოვანი საკითხების განსახილველად"},
        {"pa", "ਇਹ ਮੀਟਿੰਗ ਮਹੱਤਵਪੂਰਨ ਮੁੱਦਿਆਂ ਤੇ ਚਰਚਾ ਕਰਨ ਲਈ ਕੀਤੀ ਗਈ ਹੈ"},
        {"gu", "આ બેઠક મહત્વના મુદ્દાઓ પર ચર્ચા કરવા માટે યોજવામાં આવી છે"},
        {"or", "ଏହି ବୈଠକ ଗୁରୁତ୍ୱପୂର୍ଣ୍ଣ ବିଷୟ ଉପରେ ଆଲୋଚନା ପାଇଁ ଆୟୋଜିତ ହୋଇଛି"},
        {"ta", "இந்த கூட்டம் முக்கியமான விஷயங்களைப் பற்றி விவாதிக்க ஏற்பாடு செய்யப்பட்டது"},
        {"te", "ఈ సమావేశం ముఖ్యమైన విషయాలను చర్చించడానికి ఏర్పాటు చేయబడింది"},
        {"kn", "ಈ ಸಭೆಯನ್ನು ಪ್ರಮುಖ ವಿಷಯಗಳನ್ನು ಚರ್ಚಿಸಲು ಆಯೋಜಿಸಲಾಗಿದೆ"},
        {"ml", "ഈ യോഗം പ്രധാനപ്പെട്ട വിഷയങ്ങൾ ചർച്ച ചെയ്യാനാണ് സംഘടിപ്പിച്ചത്"},
        {"si", "මෙම රැස්වීම වැදගත් කරුණු සාකච්ඡා කිරීම සඳහා සංවිධානය කරන ලදී"},
        {"th", "การประชุมครั้งนี้จัดขึ้นเพื่อหารือเรื่องสำคัญ"},
        {"lo", "ກອງປະຊຸມນີ້ຈັດຂຶ້ນເພື່ອປຶກສາຫາລືບັນຫາສຳຄັນ"},
        {"bo", "ཚོགས་འདུ་འདི་གནད་དོན་གལ་ཆེན་གྲོས་བསྡུར་བྱེད་ཆེད་ཚོགས་པ་ཡིན།"},
        {"my", "ဒီအစည်းအဝေးကို အရေးကြီးတဲ့ကိစ္စတွေ ဆွေးနွေးဖို့ စီစဉ်ခဲ့တာပါ"},
        {"km", "កិច្ចប្រជុំនេះត្រូវបានរៀបចំឡើងដើម្បីពិភាក្សាអំពីបញ្ហាសំខាន់ៗ"},
        {"am", "ይህ ስብሰባ አስፈላጊ ጉዳዮችን ለመወያየት ተዘጋጅቷል"},
        {"ko", "이 회의는 중요한 문제를 논의하기 위해 마련되었습니다"},
    });
    run("Bengali script, Bengali + Assamese", {{"en","eng"},{"bn","ben"},{"as","asm"}}, {
        {"bn", "এই সভাটি গুরুত্বপূর্ণ বিষয় নিয়ে আলোচনার জন্য আয়োজন করা হয়েছে"},
        {"as", "এই সভাখন গুৰুত্বপূৰ্ণ বিষয়সমূহ আলোচনা কৰিবলৈ আয়োজন কৰা হৈছে"},
    });
    run("CJK, Chinese + Japanese", {{"en","eng"},{"zh","zho"},{"ja","jpn"}}, {
        {"zh", "这次会议是为了讨论重要问题而组织的"},
        {"ja", "この会議は重要な問題を話し合うために開かれました"},
    });
    run("Latin, thirteen languages enabled together",
        {{"en","eng"},{"fr","fra"},{"de","deu"},{"es","spa"},{"pt","por"},{"it","ita"},{"nl","nld"},
         {"tr","tur"},{"vi","vie"},{"id","ind"},{"pl","pol"},{"sv","swe"},{"sw","swa"}}, {
        {"en", "This meeting was organised to discuss important issues"},
        {"fr", "Cette réunion a été organisée pour discuter de questions importantes"},
        {"de", "Dieses Treffen wurde organisiert, um wichtige Fragen zu besprechen"},
        {"es", "Esta reunión fue organizada para discutir temas importantes"},
        {"pt", "Esta reunião foi organizada para discutir questões importantes"},
        {"it", "Questa riunione è stata organizzata per discutere questioni importanti"},
        {"nl", "Deze vergadering werd georganiseerd om belangrijke kwesties te bespreken"},
        {"tr", "Bu toplantı önemli konuları görüşmek için düzenlendi"},
        {"vi", "Cuộc họp này được tổ chức để thảo luận những vấn đề quan trọng"},
        {"id", "Pertemuan ini diadakan untuk membahas masalah-masalah penting"},
        {"pl", "To spotkanie zostało zorganizowane w celu omówienia ważnych spraw"},
        {"sv", "Det här mötet anordnades för att diskutera viktiga frågor"},
        {"sw", "Mkutano huu uliandaliwa ili kujadili masuala muhimu"},
    });
    run("Cyrillic, four languages", {{"en","eng"},{"ru","rus"},{"uk","ukr"},{"bg","bul"},{"sr","srp"}}, {
        {"ru", "Эта встреча была организована для обсуждения важных вопросов"},
        {"uk", "Ця зустріч була організована для обговорення важливих питань"},
        {"bg", "Тази среща беше организирана, за да се обсъдят важни въпроси"},
        {"sr", "Овај састанак је организован да би се разговарало о важним питањима"},
    });
    run("Devanagari, Hindi + Marathi + Nepali", {{"en","eng"},{"hi","hin"},{"mr","mar"},{"ne","nep"}}, {
        {"hi", "यह बैठक महत्वपूर्ण मुद्दों पर चर्चा करने के लिए आयोजित की गई है"},
        {"mr", "ही बैठक महत्त्वाच्या मुद्द्यांवर चर्चा करण्यासाठी आयोजित केली आहे"},
        {"ne", "यो बैठक महत्त्वपूर्ण विषयहरूमा छलफल गर्न आयोजना गरिएको हो"},
    });
    run("Scripts written by one language each", {{"en","eng"},{"dv","div"},{"chr","chr"}}, {
        {"dv", "މި ބައްދަލުވުމަކީ މުހިންމު ކަންކަމާ މެދު މަޝްވަރާކުރުމަށް ބޭއްވި ބައްދަލުވުމެކެވެ"},
        {"chr", "ᏣᎳᎩ ᎦᏬᏂᎯᏍᏗ ᎠᏂᏴᏫ ᎤᏂᏬᏂᎯᏍᏗ"},
        {"syr", "ܗܢܐ ܟܢܘܫܝܐ ܐܬܛܝܒ ܠܡܡܠܠܘ ܥܠ ܨܒܘܬܐ ܚܫܝܚܬܐ"},
    });
    // A living script no detector knows (2026-10-16): decided by the
    // script, in CLDR's likely language -- Santali, whatever is enabled -- and
    // NOT latin, so without a Santali voice the non-Latin preferred one reads it.
    // It was "xx-Olck" before, which no voice is ever set up for.
    run("A script no detector knows (Ol Chiki)", {{"en","eng"},{"hi","hin"}}, {
        {"sat", "ᱱᱤᱭᱟᱹ ᱫᱚ ᱢᱤᱫ ᱡᱟᱹᱨᱩᱲ ᱠᱟᱹᱢᱤ ᱠᱟᱱᱟ"},
    });
    // The same sentences INSIDE Latin text: each must come out as its own span.
    {
        enable({{"en","eng"},{"dv","div"},{"chr","chr"},{"syr","syr"}});
        const char* mixed[] = {
            "Hello friends މި ބައްދަލުވުމަކީ މުހިންމު ކަންކަމާ މެދު މަޝްވަރާކުރުމަށް",
            "Read this ᏣᎳᎩ ᎦᏬᏂᎯᏍᏗ ᎠᏂᏴᏫ ᎤᏂᏬᏂᎯᏍᏗ please",
        };
        printf("\n== other scripts inside Latin text (spans) ==\n");
        for (const char* t : mixed) {
            jobjectArray got = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(
                env, nullptr, env->NewStringUTF(t), JNI_FALSE);
            jsize n = env->GetArrayLength(got);
            for (jsize i = 1; i + 2 < n; i += 3)
                printf("   %-4s latin=%s  %s\n", str((jstring)env->GetObjectArrayElement(got, i)).c_str(),
                       str((jstring)env->GetObjectArrayElement(got, i + 1)).c_str(),
                       str((jstring)env->GetObjectArrayElement(got, i + 2)).c_str());
        }
    }

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
