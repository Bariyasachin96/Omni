// ==========================================================================
//  THE TWO DETECTORS TOGETHER       owner, 2026-10-20: "Mujhe accuracy 100%
//  hi chahie ... aap kaise bhi karo"
//
//  CLD2 and the model ML Kit runs (langid.h) are each better at different
//  pairs of languages -- measured by tools/verify/langsuite on 5,538 texts
//  neither was tuned on: CLD2 at Hindi/Marathi/Nepali, Urdu/Persian and
//  Assamese (which the model has no label for), the model at Spanish/French/
//  Italian/Portuguese, Russian/Ukrainian/Bulgarian and Chinese. So where a
//  span's script is shared by two or more ENABLED languages -- the only place
//  a detector chooses between voices at all -- both are asked and weighed:
//
//    1. a letter one language of the pair writes and the other never does
//       decides alone: Assamese ৰ ৱ, Bengali র, Marathi ळ, Urdu ٹ ڈ ڑ ں ے,
//       Ukrainian ї є ґ, Serbian ђ ћ џ, Japanese kana; and for Chinese and
//       Japanese, which national character sets the text's Han fits;
//    2. the word-and-ending model (langnb.h, learned from Wikipedia, Tatoeba,
//       Common Voice and Android's own UI strings) decides alone when its best
//       language leads the next by e^10 to 1 or more;
//    3. a language the model ML Kit runs has no label for (Assamese, Sanskrit,
//       Bihari ...) can only come from CLD2, so CLD2's answer stands when it
//       names one;
//    4. otherwise each enabled language L of the script scores
//           a ln p_model(L) + (1 - a) ln q_cld2(L) + c nb(L)
//       where q_cld2 is CLD2's top three (each one's percent of the text),
//       plus k for every candidate and b for CLD2's own answer, normalised,
//       and nb is the word-and-ending model's score. The highest wins. a, k,
//       b and c are per script (kBlend), chosen on text NOT used to measure
//       the result, with a right answer turned wrong counted three times a
//       wrong one put right.
//
//  Then CONTEXT (blendFinal): a span that proved nothing (steps 3-4: "ब्लूटूथ",
//  "प्रस्तावना" are the same in Hindi and Marathi) takes the language of the
//  last span of its script that did prove one, unless the word-and-ending
//  model puts its own pick 6 nats above it.
//
//  Measured (tools/verify/langsuite/run.sh) on 35,212 held-out texts in sets
//  where languages share a script -- Android's own UI strings, Tatoeba, the
//  UDHR, everyday phrases: CLD2 alone 92.3%, steps 1-4 96.1%, and with the
//  context, reading text in one language, 99.79%. What is left is mostly
//  English names inside other languages' UI ("Google Talk", "IMEI") and
//  Indonesian/Malay, which are nearly one language.
//
//  With fewer than two enabled languages in the script nothing here runs and
//  CLD2's path decides exactly as before -- the owner's own set {hi, en, gu}
//  is such a set.
// ==========================================================================
#pragma once
#include <algorithm>
#include <cmath>
#include <mutex>
#include <cstring>
#include <string>
#include <vector>
#include "compact_lang_det.h"
#include "langhan.h"
#include "langnb.h"
#include "langid.h"

// The model's labels by script slot (ML Kit's own support table): 2 Cyrillic,
// 3 Arabic, 4 Devanagari, 5 Han and kana, 6 Bengali; 1 for the Latin-script
// rest; 0 for a script the core decides without a detector.
static inline int blendLabelSlot(const char* l) {
    static const char* const kCyrl[] = {"be", "bg", "kk", "ky", "mk", "mn", "ru", "sr", "tg", "uk"};
    static const char* const kArab[] = {"ar", "fa", "ps", "sd", "ur"};
    static const char* const kDeva[] = {"hi", "mr", "ne"};
    static const char* const kNone[] = {"am", "el", "gu", "hy", "iw", "ka", "km", "kn", "ko", "lo", "ml", "my",
                                        "pa", "si", "ta", "te", "th", "yi", "unknown"};
    for (const char* c : kCyrl) if (!strcmp(l, c)) return 2;
    for (const char* c : kArab) if (!strcmp(l, c)) return 3;
    for (const char* c : kDeva) if (!strcmp(l, c)) return 4;
    if (!strcmp(l, "zh") || !strcmp(l, "ja")) return 5;
    if (!strcmp(l, "bn")) return 6;
    for (const char* c : kNone) if (!strcmp(l, c)) return 0;
    return strchr(l, '-') ? 0 : 1;   // "hi-Latn" and the rest are never an enabled code
}

// A CLD2 or enabled-language code spelled the way the model labels it.
static inline std::string blendCanonical(const std::string& c) {
    if (c == "tl") return "fil";
    if (c == "jw") return "jv";
    if (c == "he") return "iw";
    if (c == "ji") return "yi";
    if (c == "in") return "id";
    if (c == "nb" || c == "nn") return "no";
    if (c == "zh-Hant") return "zh";
    if (c == "sr-ME") return "sr";
    return c;
}

// The model's label index for a code, or -1.
static inline int blendLabelIndex(const std::string& code) {
    const std::string c = blendCanonical(code);
    for (int k = 1; k < EV_LID_CLASSES; k++) if (c == kLidLabels[k]) return k;
    return -1;
}

// The languages of CLD2's kScriptLangPairs the model has no label for, by slot.
static inline int blendCldOnlySlot(const std::string& code) {
    if (code == "sa") return 4;
    if (code == "as") return 6;
    return 0;
}

// Does the UTF-8 text hold any code point in [lo, hi] or in `list`?
static inline bool blendHas(const char* s, int n, const int* list, int count, int lo = -1, int hi = -1) {
    for (int i = 0; i < n;) {
        int32_t r;
        int used = lidDecode((const unsigned char*)s + i, (size_t)(n - i), &r);
        if (used <= 0) break;
        i += used;
        if (r >= lo && r <= hi) return true;
        for (int k = 0; k < count; k++) if (r == list[k]) return true;
    }
    return false;
}

// ---- the word-and-ending model (langnb.h; tools/langid/train_nb.py) ----
// A text's features are its words (letters and marks of the model's blocks,
// lowercased) and each word's last one, two and three letters; each feature
// counts once. A language's score is the sum of its stored round(8 ln p) over
// the features the table holds.
static inline bool nbIsWord(int32_t c) {
    int lo = 0, hi = kNbWordRangeCount - 1;
    while (lo <= hi) {
        int mid = (lo + hi) >> 1;
        if ((uint32_t)c < kNbWordRanges[mid][0]) hi = mid - 1;
        else if ((uint32_t)c > kNbWordRanges[mid][1]) lo = mid + 1;
        else return true;
    }
    return false;
}
static inline int32_t nbLower(int32_t c) {
    int lo = 0, hi = kNbLowerCount - 1;
    while (lo <= hi) {
        int mid = (lo + hi) >> 1;
        if ((uint32_t)c < kNbLower[mid][0]) hi = mid - 1;
        else if ((uint32_t)c > kNbLower[mid][0]) lo = mid + 1;
        else return (int32_t)kNbLower[mid][1];
    }
    return c;
}
static inline void nbUtf8(std::string& s, int32_t c) {
    if (c < 0x80) s += (char)c;
    else if (c < 0x800) { s += (char)(0xC0 | (c >> 6)); s += (char)(0x80 | (c & 0x3F)); }
    else if (c < 0x10000) { s += (char)(0xE0 | (c >> 12)); s += (char)(0x80 | ((c >> 6) & 0x3F)); s += (char)(0x80 | (c & 0x3F)); }
    else { s += (char)(0xF0 | (c >> 18)); s += (char)(0x80 | ((c >> 12) & 0x3F)); s += (char)(0x80 | ((c >> 6) & 0x3F)); s += (char)(0x80 | (c & 0x3F)); }
}
static inline uint32_t nbFnv(const char* p, size_t n, uint32_t h = 0x811C9DC5u) {
    for (size_t i = 0; i < n; i++) h = (h ^ (uint8_t)p[i]) * 0x01000193u;
    return h;
}
static inline void nbFeatures(const char* text, int bytes, std::vector<uint32_t>& out) {
    std::string word;
    std::vector<size_t> starts;   // where each letter of the word starts
    auto flush = [&]() {
        if (word.empty()) return;
        out.push_back(nbFnv(word.data(), word.size(), nbFnv("w", 1)));
        const int n = (int)starts.size();
        for (int k = 1; k <= 3; k++) {
            if (n <= k) break;
            const char tag = (char)('0' + k);
            const size_t at = starts[n - k];
            out.push_back(nbFnv(word.data() + at, word.size() - at, nbFnv(&tag, 1)));
        }
        word.clear();
        starts.clear();
    };
    for (int i = 0; i < bytes;) {
        int32_t r;
        int used = lidDecode((const unsigned char*)text + i, (size_t)(bytes - i), &r);
        if (used <= 0) break;
        i += used;
        if (nbIsWord(r)) {
            starts.push_back(word.size());
            nbUtf8(word, nbLower(r));
        } else {
            flush();
        }
    }
    flush();
    std::sort(out.begin(), out.end());
    out.erase(std::unique(out.begin(), out.end()), out.end());
}
// The model's scores for `cands` (codes as enabled); false when the script has
// no model or one of them is a language it was not taught.
static inline bool nbScore(int slot, const char* text, int bytes, const std::vector<std::string>& cands,
                           std::vector<int>& score) {
    NbModel m;
    if (!nbModel(slot, &m)) return false;
    std::vector<int> idx;
    for (const std::string& c : cands) {
        const std::string k = blendCanonical(c);
        int at = -1;
        for (int i = 0; i < m.nLangs; i++) if (k == m.langs[i]) at = i;
        if (at < 0) return false;
        idx.push_back(at);
    }
    std::vector<uint32_t> f;
    nbFeatures(text, bytes, f);
    score.assign(cands.size(), 0);
    for (uint32_t h : f) {
        const uint32_t* p = std::lower_bound(m.hash, m.hash + m.n, h);
        if (p == m.hash + m.n || *p != h) continue;
        const int8_t* v = m.logp + (size_t)(p - m.hash) * m.nLangs;
        for (size_t j = 0; j < idx.size(); j++) score[j] += v[idx[j]];
    }
    return true;
}
// How far ahead the best language must score to be believed (in round(8 ln p)
// units: 80 = 10 nats, the odds e^10 to 1), per script slot.
static const int kNbMargin[7] = {0, 80, 80, 80, 80, 0, 80};

struct BlendWeights { float a, k, b, c; };
// Chosen per script slot on half of the suite's held-out text, counting a right
// answer turned wrong as three times a wrong one put right
// (tools/verify/langsuite/combine.py prints them).
static const BlendWeights kBlend[7] = {
    {0.0f, 0.0f, 0.0f, 0.0f},
    {0.3f, 0.20f, 2.0f, 0.50f},   // 1 Latin
    {0.4f, 0.01f, 0.5f, 0.50f},   // 2 Cyrillic
    {0.4f, 0.20f, 0.5f, 0.20f},   // 3 Arabic
    {0.3f, 0.20f, 2.0f, 1.00f},   // 4 Devanagari
    {0.6f, 0.05f, 1.0f, 0.00f},   // 5 Han and kana
    {0.1f, 0.01f, 0.0f, 0.00f},   // 6 Bengali (one model language: the blend cannot move it)
};

// The letters one enabled language of the script writes and every other enabled
// one practically never does (langnb.h's letter table, learned from the same
// text as the word-and-ending model): the one language they point to, or "".
static inline std::string nbLetterRule(int slot, const char* text, int bytes, const std::vector<std::string>& cands) {
    NbModel m;
    if (cands.size() < 2 || cands.size() > 8 || !nbModel(slot, &m) || m.nLetters == 0) return "";
    int idx[8];
    for (size_t i = 0; i < cands.size(); i++) {
        const std::string k = blendCanonical(cands[i]);
        idx[i] = -1;
        for (int j = 0; j < m.nLangs; j++) if (k == m.langs[j]) idx[i] = j;
        if (idx[i] < 0) return "";
    }
    uint32_t won = 0;
    for (int i = 0; i < bytes;) {
        int32_t r;
        int used = lidDecode((const unsigned char*)text + i, (size_t)(bytes - i), &r);
        if (used <= 0) break;
        i += used;
        int lo = 0, hi = m.nLetters - 1;
        uint64_t mask = 0;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if ((uint64_t)r < m.letters[mid][0]) hi = mid - 1;
            else if ((uint64_t)r > m.letters[mid][0]) lo = mid + 1;
            else { mask = m.letters[mid][1]; break; }
        }
        if (!mask) continue;
        for (size_t a = 0; a < cands.size(); a++) {
            bool all = true;
            for (size_t b = 0; b < cands.size() && all; b++)
                if (b != a && !((mask >> (idx[a] * 8 + idx[b])) & 1)) all = false;
            if (all) won |= 1u << a;
        }
    }
    if (won == 0 || (won & (won - 1))) return "";
    for (size_t a = 0; a < cands.size(); a++) if ((won >> a) & 1) return cands[a];
    return "";
}

// What blendDetectors decided for a span of script slot `slot`: the enabled
// code ("" keeps CLD2's path answer), whether the text itself proved it (a
// letter, the Han rules, or the word-and-ending model past its margin), and,
// for the context (blendFinal), the script's enabled languages and the
// word-and-ending model's score for each (empty when it cannot score them).
struct BlendResult {
    std::string lang;
    bool sure = false;
    std::vector<std::string> slotLangs;
    std::vector<int> nb;
};

// `cld2Answer` is CLD2's path answer; lang3 / percent3 are the same CLD2
// call's top three. Depends on nothing but its arguments, so it can be cached.
static inline BlendResult blendDetectors(int slot, const char* text, int bytes,
                                         const std::vector<std::string>& enabled,
                                         const std::string& cld2Answer,
                                         const CLD2::Language lang3[3], const int percent3[3]) {
    BlendResult res;
    if (slot < 1 || slot > 6 || bytes <= 0) return res;
    std::vector<std::string> cands;   // enabled codes of this script the model ML Kit runs has a label for
    std::vector<int> labels;
    for (const std::string& code : enabled) {
        int label = blendLabelIndex(code);
        const bool here = (label > 0 && blendLabelSlot(kLidLabels[label]) == slot) ||
                          (label < 0 && blendCldOnlySlot(code) == slot);
        if (!here) continue;
        bool dup = false;
        for (const std::string& x : res.slotLangs) dup |= blendCanonical(x) == blendCanonical(code);
        if (dup) continue;
        res.slotLangs.push_back(code);
        if (label > 0) {
            cands.push_back(code);
            labels.push_back(label);
        }
    }
    if (res.slotLangs.size() < 2) return res;
    auto enabledCode = [&](const char* want) -> std::string {
        for (const std::string& code : enabled) if (blendCanonical(code) == want || code == want) return code;
        return "";
    };
    auto sure = [&](const std::string& lang) { res.lang = lang; res.sure = true; return res; };

    // 1. letters
    if (slot == 2 || slot == 3 || slot == 6) {
        const std::string won = nbLetterRule(slot, text, bytes, res.slotLangs);
        if (!won.empty()) return sure(won);
    } else if (slot == 5) {
        // Han and kana. Kana (and the iteration mark 々) is written only in
        // Japanese. A Han character outside JIS X 0208 is one everyday
        // Japanese does not use. Seven or more Han characters with no kana
        // among them are Chinese: Japanese running text always has kana, only
        // its short compounds have none. And a short text holding both a
        // character Simplified Chinese does not write (outside GB 2312) and
        // one Traditional Chinese does not (outside CNS 11643 planes 1-2) is
        // neither standard: it is Japanese, which takes from both (電話番号:
        // 電 traditional, 号 simplified). (langhan.h, Unihan's IRG sources.)
        std::string ja = enabledCode("ja"), zh = enabledCode("zh");
        int han = 0;
        bool kana = false, notJ = false, allS = true, allT = true;
        for (int i = 0; i < bytes;) {
            int32_t r;
            int used = lidDecode((const unsigned char*)text + i, (size_t)(bytes - i), &r);
            if (used <= 0) break;
            i += used;
            if ((r >= 0x3041 && r <= 0x309F) || (r >= 0x30A0 && r <= 0x30FF && r != 0x30FB) ||
                (r >= 0x31F0 && r <= 0x31FF) || (r >= 0xFF66 && r <= 0xFF9F) || r == 0x3005) {
                kana = true;
            } else if ((r >= 0x4E00 && r <= 0x9FFF) || (r >= 0x3400 && r <= 0x4DBF) ||
                       (r >= 0xF900 && r <= 0xFAFF) || (r >= 0x20000 && r <= 0x3FFFF)) {
                han++;
                if (!hanIn(kHanJapanese, r)) notJ = true;
                if (!hanIn(kHanSimplified, r)) allS = false;
                if (!hanIn(kHanTraditional, r)) allT = false;
            }
        }
        if (!ja.empty() && kana) return sure(ja);
        if (!zh.empty() && han > 0 && notJ) return sure(zh);
        if (!zh.empty() && han >= 7) return sure(zh);
        if (!ja.empty() && han > 0 && !allS && !allT) return sure(ja);
    }

    // 2. the word-and-ending model, when it is sure
    if (nbScore(slot, text, bytes, res.slotLangs, res.nb)) {
        size_t best = 0;
        for (size_t i = 1; i < res.nb.size(); i++) if (res.nb[i] > res.nb[best]) best = i;
        int second = -1000000000;
        for (size_t i = 0; i < res.nb.size(); i++) if (i != best && res.nb[i] > second) second = res.nb[i];
        if (kNbMargin[slot] > 0 && res.nb[best] - second >= kNbMargin[slot]) return sure(res.slotLangs[best]);
    } else {
        res.nb.clear();
    }

    // 3. only CLD2 can name it
    if (blendLabelIndex(cld2Answer) < 0) return res;
    if (cands.empty()) return res;

    // 4. all three, weighed
    float p[EV_LID_CLASSES];
    if (!lidClassify(text, (size_t)bytes, p)) return res;
    const BlendWeights w = kBlend[slot];
    std::vector<double> q(cands.size(), w.k);
    for (int rank = 0; rank < 3; rank++) {
        const char* code = CLD2::LanguageCode(lang3[rank]);
        if (!code || percent3[rank] <= 0) continue;
        int label = blendLabelIndex(code);
        for (size_t c = 0; c < cands.size(); c++) if (labels[c] == label) q[c] += percent3[rank] / 100.0;
    }
    const int answer = blendLabelIndex(cld2Answer);
    for (size_t c = 0; c < cands.size(); c++) if (labels[c] == answer) q[c] += w.b;
    double z = 0;
    for (double v : q) z += v;
    std::vector<int> nb;
    if (cands.size() < 2 || !nbScore(slot, text, bytes, cands, nb)) nb.assign(cands.size(), 0);
    size_t best = 0;
    double bestScore = -1e300;
    for (size_t c = 0; c < cands.size(); c++) {
        const double score = w.a * std::log(p[labels[c]] + 1e-6) + (1 - w.a) * std::log(q[c] / z + 1e-6) +
                             w.c * (nb[c] / 8.0);
        if (score > bestScore) { bestScore = score; best = c; }
    }
    res.lang = cands[best];
    return res;
}

// ---- CONTEXT ----
// A text that proves nothing ("ब्लूटूथ", "प्रस्तावना", "تمام": the same in two
// enabled languages) takes the language of the last text of its script that
// did prove one -- the screen or the message being read -- unless the
// word-and-ending model puts its own pick at least kCtxMargin (6 nats, 400 to
// 1) above that language. One per script slot, shared by every caller.
static const int kCtxMargin = 48;
static std::mutex gBlendContextMutex;
static std::string gBlendContext[7];

// The span's language from a (possibly cached) BlendResult and CLD2's path
// answer; a sure result becomes the script's context.
static inline std::string blendFinal(int slot, const BlendResult& r, const std::string& cld2Answer) {
    const std::string got = r.lang.empty() ? cld2Answer : r.lang;
    if (slot < 1 || slot > 6 || r.slotLangs.size() < 2) return got;
    std::lock_guard<std::mutex> lock(gBlendContextMutex);
    if (r.sure) {
        gBlendContext[slot] = r.lang;
        return r.lang;
    }
    const std::string& ctx = gBlendContext[slot];
    int ci = -1, gi = -1;
    for (size_t i = 0; i < r.slotLangs.size(); i++) {
        if (r.slotLangs[i] == ctx) ci = (int)i;
        if (r.slotLangs[i] == got) gi = (int)i;
    }
    if (ctx.empty() || ci < 0 || ctx == got) return got;
    if (r.nb.empty() || gi < 0) return ctx;
    return r.nb[gi] - r.nb[ci] >= kCtxMargin ? got : ctx;
}
