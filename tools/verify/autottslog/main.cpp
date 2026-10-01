// Replays a real AutoTTS log through OUR native core and compares, chunk by
// chunk, what AutoTTS spoke with what Easy Voice would speak.
//
// extract.py turns the log into cases: the utterance text, then each
// `speak N:` chunk AutoTTS logged with the language it loaded for it. Here the
// same text goes down Easy Voice's real MIXED path -- processDirect
// (buildMixChunks), then nativeFoldAndDetect on every chunk of
// type 0/1/2, then languageForDetectedRun's rule -- and the two lists are
// compared. A screen reader often cuts an utterance off, so AutoTTS's list can
// be a PREFIX of the full split; only that prefix is compared.
//
// Settings are the ones the log itself shows: mixed mode (Latin eng, non-Latin
// guj), `updateLanguage2LetterCodes [hi, en, gu]` for the enabled set, and
// smart number reading at group size 2 ("83201" is spoken "83 20 1"). Every
// other detected language has no engine and falls back by script.
#include <jni.h>
#include <cstdint>
#include <cstdio>
#include <algorithm>
#include <cstring>
#include <fstream>
#include <string>
#include <vector>

extern "C" {
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv*, jclass, jstring, jboolean);
JNIEXPORT jobjectArray JNICALL Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jstring, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean, jboolean);
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
            o += n == 't' ? '\t' : n == 'n' ? '\n' : n == '0' ? '\0' : n;
        } else o += s[i];
    }
    return o;
}
// processDirect's answer: per record, a field count, then the fields
// (type, kind, lang, text and, for a detected chunk, its fold and spans).
static std::vector<std::vector<std::string>> records(jobjectArray a){
    std::vector<std::vector<std::string>> out;
    jsize total = a ? env->GetArrayLength(a) : 0;
    for (jsize at = 0; at < total;) {
        int n = atoi(S((jstring)env->GetObjectArrayElement(a, at)).c_str());
        if (n < 0 || at + 1 + n > total) break;
        std::vector<std::string> f;
        for (int k = 1; k <= n; k++) f.push_back(S((jstring)env->GetObjectArrayElement(a, at + k)));
        out.push_back(f);
        at += 1 + n;
    }
    return out;
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
    auto recs = records(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, J(text), J("eng"), J("guj"), J("mix"),
        0, J(""), 0, J(""), 0, J(""), JNI_TRUE, smart ? JNI_TRUE : JNI_FALSE, group, J("eng"), 1, JNI_FALSE, JNI_FALSE, JNI_FALSE));
    for (auto& one : recs) {
        if (one.size() < 4) continue;
        int type = atoi(one[0].c_str());
        const std::string& t = one[3];
        if (type >= 3) { out.push_back({one[2], t}); continue; }
        jobjectArray got = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, J(t), JNI_FALSE);
        jsize n = got ? env->GetArrayLength(got) : 0;
        for (jsize i = 1; i + 2 < n; i += 3) {
            std::string l = S((jstring)env->GetObjectArrayElement(got, i));
            bool latin = S((jstring)env->GetObjectArrayElement(got, i + 1)) == "1";
            out.push_back({resolve(l, latin), S((jstring)env->GetObjectArrayElement(got, i + 2))});
        }
    }
    return out;
}

// ---- STRESS MODE (owner, 2026-09-30: "bahut sari alag-alag emojis aur
// alag-alag text ... test karke"). One text per line of a corpus, run down the
// same real mixed path, and three things must hold for every one of them:
//   1. NOTHING IS LOST OR INVENTED: the chunks, with all whitespace taken out,
//      are exactly the input after the core's own folding (normalizeFancy) and
//      bidi-control strip, with all whitespace taken out. Smart-number spacing
//      only adds spaces, so it passes; a dropped emoji, a doubled digit or a
//      mangled byte does not.
//   2. NO EMOJI IS CUT IN TWO: no chunk starts with a joiner, a variation
//      selector, a skin tone, a keycap mark or a tag character, none ends with
//      a ZWJ, and no flag's two regional indicators land in different chunks.
//      A cut sequence is spoken as two broken pieces by two engines.
//   3. every chunk is valid UTF-8 and has a language.
static std::vector<uint32_t> cps(const std::string& s){
    std::vector<uint32_t> o; size_t i = 0;
    while (i < s.size()) {
        unsigned char c = s[i]; uint32_t cp; int n;
        if (c < 0x80) { cp = c; n = 1; } else if ((c >> 5) == 6) { cp = c & 31; n = 2; }
        else if ((c >> 4) == 14) { cp = c & 15; n = 3; } else if ((c >> 3) == 30) { cp = c & 7; n = 4; }
        else return {0xFFFFFFFF};
        if (i + n > s.size()) return {0xFFFFFFFF};
        for (int k = 1; k < n; k++) { unsigned char d = s[i + k]; if ((d >> 6) != 2) return {0xFFFFFFFF}; cp = (cp << 6) | (d & 63); }
        o.push_back(cp); i += n;
    }
    return o;
}
static bool isSpace(uint32_t c){
    return c == 0x20 || (c >= 9 && c <= 13) || (c >= 0x1C && c <= 0x1F) || c == 0x85 || c == 0xA0
        || c == 0x1680 || (c >= 0x2000 && c <= 0x200A) || c == 0x2028 || c == 0x2029 || c == 0x202F
        || c == 0x205F || c == 0x3000 || c == 0xFEFF;
}
static bool isBidi(uint32_t c){
    return c == 0x200E || c == 0x200F || (c >= 0x202A && c <= 0x202E) || (c >= 0x2066 && c <= 0x2069) || c == 0x061C;
}
static bool continuesEmoji(uint32_t c){
    return c == 0x200D || c == 0xFE0F || c == 0xFE0E || (c >= 0x1F3FB && c <= 0x1F3FF) || c == 0x20E3
        || (c >= 0xE0020 && c <= 0xE007F);
}
static bool isRI(uint32_t c){ return c >= 0x1F1E6 && c <= 0x1F1FF; }
static int stress(const char* path){
    std::ifstream in(path); std::string line; int n = 0, bad = 0, chunks = 0;
    while (std::getline(in, line)) {
        if (line.empty() || line.rfind("# ", 0) == 0) continue;
        std::string text = unesc(line); n++;
        std::vector<Chunk> got = ours(text);
        chunks += (int)got.size();
        std::vector<std::string> why;
        std::string folded = S((jstring)env->GetObjectArrayElement(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, J(text), JNI_FALSE), 0));
        std::vector<uint32_t> want, have;
        for (uint32_t c : cps(folded)) if (!isSpace(c) && !isBidi(c)) want.push_back(c);
        int riRun = 0;
        for (size_t i = 0; i < got.size(); i++) {
            std::vector<uint32_t> c = cps(got[i].text);
            if (!c.empty() && c[0] == 0xFFFFFFFF) { why.push_back("chunk " + std::to_string(i) + " is not valid UTF-8"); continue; }
            if (got[i].lang.size() != 3) why.push_back("chunk " + std::to_string(i) + " has language '" + got[i].lang + "'");
            std::vector<uint32_t> ink; for (uint32_t x : c) if (!isSpace(x)) ink.push_back(x);
            if (i > 0 && !ink.empty() && continuesEmoji(ink[0]))
                why.push_back("chunk " + std::to_string(i) + " starts inside an emoji sequence");
            if (i > 0 && !ink.empty() && isRI(ink[0]) && riRun % 2 == 1)
                why.push_back("a flag is split before chunk " + std::to_string(i));
            if (!ink.empty() && ink.back() == 0x200D && i + 1 < got.size())
                why.push_back("chunk " + std::to_string(i) + " ends on a zero-width joiner");
            for (uint32_t x : ink) { have.push_back(x); riRun = isRI(x) ? riRun + 1 : 0; }
            if (!c.empty() && isSpace(c.back())) riRun = 0;
        }
        if (want != have) why.push_back("the chunks do not add up to the text (something lost, added or changed)");
        if (!why.empty()) {
            bad++;
            printf("BAD: %s\n", text.c_str());
            for (auto& w : why) printf("   %s\n", w.c_str());
            for (auto& c : got) printf("   %s |%s|\n", c.lang.c_str(), c.text.c_str());
        }
    }
    printf("\n%d texts, %d chunks: %d wrong\n", n, chunks, bad);
    return bad ? 1 : 0;
}


// ---- ONE-CALL MODE (owner, 2026-10-01: "pahle test kariye"). processDirect
// with detect on carries each type-0/1/2 chunk's fold and spans in its own
// record. That must be EXACTLY what the old two-step path produced: the same
// processDirect with detect off, then nativeFoldAndDetect on each such chunk.
// Both run in this one binary through the same core, over a corpus of texts x
// enabled-language sets x quick-character on/off x smart numbers on/off x the
// number/punctuation/emoji mode ints, and every record is compared field for
// field -- the four base fields too, so turning detect on cannot move a chunk.
static std::vector<std::vector<std::string>> recsOf(const std::string& text, int nm, int pm, int em, bool smartOn, bool quick, bool detect){
    return records(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
        env, nullptr, J(text), J("eng"), J("hin"), J("mix"),
        nm, J("eng"), pm, J("hin"), em, J("guj"), JNI_TRUE, smartOn ? JNI_TRUE : JNI_FALSE, 2, J("eng"), 1, JNI_FALSE,
        detect ? JNI_TRUE : JNI_FALSE, quick ? JNI_TRUE : JNI_FALSE));
}
static int onecall(const char* path){
    std::vector<std::string> texts;
    { std::ifstream in(path); std::string line; while (std::getline(in, line)) texts.push_back(unesc(line)); }
    const std::vector<std::vector<std::string>> sets2 = {
        {"hi","en","gu"}, {"en","hi","mr"}, {"en","ru","uk"}, {"en","ar","ur","fa"},
        {"zh","ja","ko","th"}, {}, {"en"}, {"en","hi","ar","zh","ru","gu","ta","bn"} };
    const int modes[][3] = {{0,0,0},{1,1,1},{2,2,2},{3,3,3},{0,3,1},{2,0,3}};
    long records = 0, detected = 0, bad = 0;
    for (auto& set : sets2) {
        std::vector<std::string> iso3;
        for (auto& c : set) iso3.push_back(c == "hi" ? "hin" : c == "en" ? "eng" : c == "gu" ? "guj" : c == "mr" ? "mar" : c);
        Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(set));
        Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(set));
        for (auto& text : texts) for (auto& m : modes) for (int quick = 0; quick < 2; quick++) for (int sm = 0; sm < 2; sm++) {
            env->PushLocalFrame(512);
            auto oldR = recsOf(text, m[0], m[1], m[2], sm, quick, false);
            auto newR = recsOf(text, m[0], m[1], m[2], sm, quick, true);
            std::string why;
            if (oldR.size() != newR.size()) why = "record count " + std::to_string(oldR.size()) + " vs " + std::to_string(newR.size());
            for (size_t i = 0; why.empty() && i < oldR.size(); i++) {
                records++;
                const std::vector<std::string>& o = oldR[i]; const std::vector<std::string>& n = newR[i];
                if (o.size() != 4) { why = "old record has " + std::to_string(o.size()) + " fields"; break; }
                if (n.size() < 4) { why = "new record has " + std::to_string(n.size()) + " fields"; break; }
                for (int k = 0; k < 4 && why.empty(); k++) if (o[k] != n[k]) why = "record " + std::to_string(i) + " field " + std::to_string(k) + " moved";
                if (!why.empty()) break;
                int type = atoi(o[0].c_str());
                std::string t = o[3];
                std::vector<std::string> want;
                if (type >= 0 && type <= 2 && !t.empty()) {
                    detected++;
                    jobjectArray got = Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(env, nullptr, J(t), quick ? JNI_TRUE : JNI_FALSE);
                    jsize cnt = got ? env->GetArrayLength(got) : 0;
                    for (jsize j = 0; j < cnt; j++) want.push_back(S((jstring)env->GetObjectArrayElement(got, j)));
                    env->DeleteLocalRef(got);
                }
                std::vector<std::string> have;
                for (size_t k = 4; k < n.size(); k++) have.push_back(n[k]);
                if (want != have) why = "record " + std::to_string(i) + ": spans differ (" + std::to_string(want.size()) + " vs " + std::to_string(have.size()) + " fields)";
            }
            if (!why.empty()) {
                if (++bad <= 10) printf("DIFF [%s] quick=%d smart=%d modes=%d%d%d: %s\n   %s\n",
                    set.empty() ? "-" : set[0].c_str(), quick, sm, m[0], m[1], m[2], why.c_str(), text.c_str());
            }
            env->PopLocalFrame(nullptr);
        }
    }
    printf("\n%zu texts, %ld records, %ld detected chunks compared: %ld differ\n", texts.size(), records, detected, bad);
    return bad ? 1 : 0;
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
    if (argc > 2 && std::string(argv[1]) == "--stress") return stress(argv[2]);
    if (argc > 2 && std::string(argv[1]) == "--onecall") return onecall(argv[2]);
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
