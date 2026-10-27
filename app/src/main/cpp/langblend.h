// ==========================================================================
//  CHOOSING BETWEEN THE ENABLED LANGUAGES OF ONE SCRIPT
//  owner, 2026-10-20: "Mujhe accuracy 100% hi chahie ... aap kaise bhi karo";
//  2026-10-21: CLD2 removed -- "jahan per CLD2 kam karega vahan per ab ML Kit
//  kam karega". The model ML Kit runs (langid.h) is now the only statistical
//  detector; CLD2, its tables and its answers are gone from the app.
//
//  Where a span's script is shared by two or more ENABLED languages -- the
//  only place a detector chooses between voices at all -- in order:
//
//    0. the letters of each orthography, word by word (Cyrillic, Arabic,
//       Bengali; orthoDecide below, owner 2026-10-21): a language the text's
//       letters it does not write cost kOrthoM or more than they cost the best
//       is ruled out ("كتاب" with Arabic kaf is not Urdu), one left is sure,
//       and everything below -- the context too -- only chooses between those
//       left. A span holding two such languages has already been cut into one
//       piece per language (orthoSegment, tts_engine_core.cpp emitScriptSpan);
//    1. a letter one language of the pair writes and the other never does
//       decides alone: Assamese ৰ ৱ, Bengali র, Marathi ळ, Urdu ٹ ڈ ڑ ں ے,
//       Ukrainian ї є ґ, Serbian ђ ћ џ, Japanese kana; and for Chinese and
//       Japanese, which national character sets the text's Han fits;
//    2. the word-and-ending model (langnb.h, learned from Wikipedia, Tatoeba,
//       Common Voice and Android's own UI strings) decides alone when its best
//       language leads the next by e^10 to 1 or more;
//    3. otherwise each enabled language L of the script scores
//           ln p_model(L) + c nb(L)
//       (nb: the word-and-ending model's score in nats; c per script, kBlendC,
//       chosen on text NOT used to measure the result). Where one of them is a
//       language the model has no label for (Assamese, Sanskrit), the
//       word-and-ending model alone decides, else the model between those it
//       knows.
//
//  Then CONTEXT (blendFinal): a span that proved nothing (step 3: "ब्लूटूथ",
//  "प्रस्तावना" are the same in Hindi and Marathi) takes the language of the
//  last span of its script that did prove one -- or, before the first span of
//  its utterance that does, that span's (tts_engine_core.cpp nameSpanTexts) --
//  unless the word-and-ending model puts its own pick 8 nats above it, or the
//  span holds a letter its own answer writes and that language practically
//  never does (keheh after Arabic text, ৰ after Bengali: langnb.h kNbRare, at
//  most 1 in 2,000 texts).
//
//  With fewer than two enabled languages in the script nothing here runs: the
//  span is that one language (tts_engine_core.cpp) -- the owner's own set
//  {hi, en, gu} is such a set.
// ==========================================================================
#pragma once
#include <algorithm>
#include <cmath>
#include <mutex>
#include <cstring>
#include <string>
#include <vector>
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

// An enabled-language code spelled the way the model labels it.
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

// The languages of kScriptLangPairs (tts_engine_core.cpp) the model has no
// label for, by slot: their script is known, the model cannot name them.
static inline int blendUnlabelledSlot(const std::string& code) {
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
        int lo = 0, hi = m.n;                       // the first feature whose hash is not below h
        while (lo < hi) {
            const int mid = lo + (hi - lo) / 2;
            if (nbHashAt(m.hash, mid) < h) lo = mid + 1; else hi = mid;
        }
        if (lo == m.n || nbHashAt(m.hash, lo) != h) continue;
        const size_t at = (size_t)lo * m.nLangs;
        for (size_t j = 0; j < idx.size(); j++) score[j] += nbLogpAt(m.logp, at + idx[j]);
    }
    return true;
}
// How far ahead the best language must score to be believed (in round(8 ln p)
// units: 80 = 10 nats, the odds e^10 to 1), per script slot.
static const int kNbMargin[7] = {0, 80, 80, 80, 80, 0, 80};

// Step 3's weight on the word-and-ending model, per script slot, chosen on half
// of the suite's held-out text (tools/verify/langsuite/combine.py prints it).
static const float kBlendC[7] = {
    0.0f,
    1.00f,   // 1 Latin
    1.50f,   // 2 Cyrillic
    0.75f,   // 3 Arabic
    8.00f,   // 4 Devanagari
    0.00f,   // 5 Han and kana (no word-and-ending model)
    0.00f,   // 6 Bengali (Assamese has no model label: the word-and-ending model decides)
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

// The rare letters (langnb.h kNbRare, learned like the letter table with a
// looser bound, owner 2026-10-21: "Urdu aur Arabic ke liye kya karta hai"):
// bit (a * 8 + b) when some letter of the text is one cands[a] writes in at
// least 1 of 200 of its texts and cands[b] in at most 1 of 2,000. They decide
// nothing alone; blendFinal does not give such a span the context b. Keheh
// (ک) is in 9 of 10 Urdu texts and 1 of 4,300 Arabic ones, so an Urdu word
// read after Arabic text stays Urdu.
static inline uint64_t nbRareVeto(int slot, const char* text, int bytes, const std::vector<std::string>& cands) {
    NbModel m;
    if (cands.size() < 2 || cands.size() > 8 || !nbModel(slot, &m) || m.nRare == 0) return 0;
    int idx[8];
    for (size_t i = 0; i < cands.size(); i++) {
        const std::string k = blendCanonical(cands[i]);
        idx[i] = -1;
        for (int j = 0; j < m.nLangs; j++) if (k == m.langs[j]) idx[i] = j;
    }
    uint64_t seen = 0;
    for (int i = 0; i < bytes;) {
        int32_t r;
        int used = lidDecode((const unsigned char*)text + i, (size_t)(bytes - i), &r);
        if (used <= 0) break;
        i += used;
        int lo = 0, hi = m.nRare - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if ((uint64_t)r < m.rare[mid][0]) hi = mid - 1;
            else if ((uint64_t)r > m.rare[mid][0]) lo = mid + 1;
            else { seen |= m.rare[mid][1]; break; }
        }
    }
    uint64_t veto = 0;
    for (size_t a = 0; a < cands.size(); a++)
        for (size_t b = 0; b < cands.size(); b++)
            if (a != b && idx[a] >= 0 && idx[b] >= 0 && ((seen >> (idx[a] * 8 + idx[b])) & 1))
                veto |= 1ULL << (a * 8 + b);
    return veto;
}

// ---- THE LETTERS OF EACH ORTHOGRAPHY, WORD BY WORD ----
// Owner, 2026-10-21: "Arabic language Arabic voice ke pass bhi Jana chahie ...
// jo bhasha ke liye Jo voice Chuna Hai vah bhasha ke liye Vahi voice ... aisa
// Nahin Ki Ek Hi voice do Bhasha Pade." Where languages share a script, some
// letters belong to one orthography: Urdu writes ٹ ڈ ڑ ں ھ ہ ے, Persian and
// Urdu ک ی where Arabic writes ك ي, Arabic ة أ إ, Pashto ټ ځ څ ډ ړ ږ ښ ګ ۍ ې,
// Ukrainian і ї є, Russian ы э ё, Serbian ђ ј љ њ ћ, Bulgarian ъ, Assamese ৰ ৱ.
// langnb.h kOrtho<slot> holds each such letter with the languages that write it
// and, per language, round(8 ln r) for r the share of its words holding it --
// where the language does not write it, ONE share for all such languages: the
// worst any careless or older spelling shows in any one source (Persian typed
// with Arabic ي ك, Assamese with Bengali র). A word holding such a letter costs
// each language that does not write it that share against the best writer's.
// Letters a language does not write rule it out; letters it lacks never do (a
// short Pashto phrase need not hold a Pashto letter).
// (tools/langid/train_nb.py learn_ortho / ortho_*, the reference this mirrors.)
//   orthoDecide   step 0 of blendDetectors: a language kOrthoM or more below
//                 the best is ruled out ("كتاب" with Arabic kaf after Urdu text
//                 is Arabic: the context can no longer make it Urdu);
//   orthoSegment  where a span holding two such languages is cut so that each
//                 piece reaches its own voice (emitScriptSpan).
// FLORES-200, never learned from, through the real core (tools/verify/
// mixedscript): no text in one language is newly read in two, and two
// sentences of two languages in one text are read 99.5-100% right word for
// word (before: 54.7-64.8%).
struct OrthoWord { int start, end; std::vector<int32_t> cps; };   // byte offsets; the letters, lowercased

// The words as nbFeatures makes them, with their offsets.
static inline void orthoWords(const char* text, int bytes, std::vector<OrthoWord>& out) {
    out.clear();
    OrthoWord cur{-1, -1, {}};
    int i = 0;
    while (i < bytes) {
        int32_t r;
        int used = lidDecode((const unsigned char*)text + i, (size_t)(bytes - i), &r);
        if (used <= 0) break;
        if (nbIsWord(r)) {
            if (cur.start < 0) cur.start = i;
            cur.cps.push_back(nbLower(r));
        } else if (cur.start >= 0) {
            cur.end = i;
            out.push_back(cur);
            cur.start = -1;
            cur.cps.clear();
        }
        i += used;
    }
    if (cur.start >= 0) {
        cur.end = i;
        out.push_back(cur);
    }
}

// The slot's letter table and each of `langs` as an index into it; false when
// the slot has none or one of them is a language it was not taught.
static inline bool orthoModel(int slot, const std::vector<std::string>& langs, NbModel& m, std::vector<int>& idx) {
    if (langs.size() < 2 || langs.size() > 8 || !nbModel(slot, &m) || m.nOrtho == 0) return false;
    idx.clear();
    for (const std::string& c : langs) {
        const std::string k = blendCanonical(c);
        int at = -1;
        for (int i = 0; i < m.nLangs; i++) if (k == m.langs[i]) at = i;
        if (at < 0) return false;
        idx.push_back(at);
    }
    return true;
}
static inline bool orthoHolds(const OrthoWord& w, uint32_t cp) {
    for (int32_t c : w.cps) if ((uint32_t)c == cp) return true;
    return false;
}

// What a word costs each language in letters that language does not write
// itself (each: its stored share against the best writer's). False when that
// is the same for every language (the word says nothing).
static inline bool orthoWordCost(const NbModel& m, const std::vector<int>& idx, const OrthoWord& w,
                                 std::vector<long long>& c) {
    c.assign(idx.size(), 0);
    for (int l = 0; l < m.nOrtho; l++) {
        const OrthoLetter& e = m.ortho[l];
        if (!orthoHolds(w, e.cp)) continue;
        int top = -128;
        for (int i = 0; i < m.nLangs; i++) top = std::max(top, (int)e.share[i]);
        for (size_t j = 0; j < idx.size(); j++)
            if (!((e.native >> idx[j]) & 1)) c[j] += e.share[idx[j]] - top;
    }
    long long lo = c[0], hi = c[0];
    for (long long x : c) { lo = std::min(lo, x); hi = std::max(hi, x); }
    return lo < hi;
}

// The letters' verdict over `langs`: keep[j], the summed costs, and the best
// (first highest). False when no word's letters tell them apart. They rule out
// only when some word's letters allow the best alone (a Pashto letter, an
// Arabic ة, "كتاب" against Urdu): then a language they cost kOrthoM or more
// than the best is ruled out, unless the word-and-ending model puts it
// kNbMargin above that best, or -- for a span's own decision (`detectors`) and
// every language one the model ML Kit runs knows -- the model's top and the
// word-and-ending model's best both name it: then the text is that language
// spelled with another's letters (Persian typed with Arabic ي ك). A best no word attests alone wins only because it writes everyone's
// letters -- Pashto writes both ي and ی, so a Persian sentence quoting Arabic
// would "be" Pashto -- and nothing is ruled out.
static inline bool orthoDecide(int slot, const char* text, int bytes, const std::vector<std::string>& langs,
                               std::vector<char>& keep, std::vector<long long>& total, int& best,
                               bool detectors = false) {
    NbModel m;
    std::vector<int> idx;
    if (!orthoModel(slot, langs, m, idx)) return false;
    std::vector<OrthoWord> words;
    orthoWords(text, bytes, words);
    total.assign(langs.size(), 0);
    std::vector<long long> s;
    std::vector<char> alone(langs.size(), 0);   // some word's letters allow this language alone
    bool evidence = false;
    for (const OrthoWord& w : words) {
        if (!orthoWordCost(m, idx, w, s)) continue;
        evidence = true;
        int clean = 0, only = -1;   // languages this word costs nothing
        for (size_t j = 0; j < langs.size(); j++) {
            total[j] += s[j];
            if (s[j] == 0) { clean++; only = (int)j; }
        }
        if (clean == 1) alone[only] = 1;
    }
    if (!evidence) return false;
    best = 0;
    for (size_t j = 1; j < langs.size(); j++) if (total[j] > total[best]) best = (int)j;
    keep.assign(langs.size(), 1);
    if (!alone[best]) return true;
    bool out = false;
    for (size_t j = 0; j < langs.size(); j++)
        if (total[best] - total[j] >= kOrthoM[slot]) { keep[j] = 0; out = true; }
    std::vector<int> nb;
    if (!out || !nbScore(slot, text, bytes, langs, nb)) return true;
    int nbBest = 0;
    for (size_t j = 1; j < langs.size(); j++) if (nb[j] > nb[nbBest]) nbBest = (int)j;
    int modelTop = -2;   // not asked yet
    for (size_t j = 0; j < langs.size(); j++) {
        if (keep[j]) continue;
        if (nb[j] - nb[best] >= kNbMargin[slot]) { keep[j] = 1; continue; }
        if (!detectors || (int)j != nbBest) continue;
        if (modelTop == -2) {
            modelTop = -1;
            std::vector<int> labels;
            for (const std::string& c : langs) {
                const int label = blendLabelIndex(c);
                if (label <= 0 || blendLabelSlot(kLidLabels[label]) != slot) { labels.clear(); break; }
                labels.push_back(label);
            }
            float p[EV_LID_CLASSES];
            if (!labels.empty() && lidClassify(text, (size_t)bytes, p)) {
                modelTop = 0;
                for (size_t c = 1; c < labels.size(); c++) if (p[labels[c]] > p[labels[modelTop]]) modelTop = (int)c;
            }
        }
        if (modelTop == (int)j) keep[j] = 1;
    }
    return true;
}

// What a sentence surely is, for cutting a text: the letters' one language,
// else the word-and-ending model's best of those they leave, kNbMargin ahead,
// in a sentence of kOrthoMinWords words or more; -1 when neither (it then goes
// with the text around it).
static inline int orthoSure(int slot, const char* text, int bytes, const std::vector<std::string>& langs, int words) {
    std::vector<char> keep;
    std::vector<long long> total;
    int best = 0;
    std::vector<std::string> left;
    std::vector<int> at;
    const bool said = orthoDecide(slot, text, bytes, langs, keep, total, best);
    for (size_t j = 0; j < langs.size(); j++)
        if (!said || keep[j]) { left.push_back(langs[j]); at.push_back((int)j); }
    if (left.size() == 1) return at[0];
    std::vector<int> sc;
    if (left.size() < 2 || words < kOrthoMinWords || !nbScore(slot, text, bytes, left, sc)) return -1;
    size_t top = 0;
    for (size_t j = 1; j < sc.size(); j++) if (sc[j] > sc[top]) top = j;
    int second = -1000000000;
    for (size_t j = 0; j < sc.size(); j++) if (j != top && sc[j] > second) second = sc[j];
    return sc[top] - second >= kNbMargin[slot] ? at[top] : -1;
}

// The best path through per-word scores, a change of language costing `cost`;
// a tie keeps the language, then takes the lower index.
static inline std::vector<int> orthoViterbi(const std::vector<std::vector<long long>>& ems, long long cost, int n) {
    std::vector<long long> score(ems[0]);
    std::vector<std::vector<int>> back;
    for (size_t t = 1; t < ems.size(); t++) {
        std::vector<long long> next((size_t)n);
        std::vector<int> from((size_t)n);
        for (int j = 0; j < n; j++) {
            int bj = j;
            long long bs = score[j];
            for (int i = 0; i < n; i++)
                if (i != j && score[i] - cost > bs) { bj = i; bs = score[i] - cost; }
            next[j] = bs + ems[t][j];
            from[j] = bj;
        }
        score.swap(next);
        back.push_back(from);
    }
    int j = 0;
    for (int x = 1; x < n; x++) if (score[x] > score[j]) j = x;
    std::vector<int> labels(ems.size());
    labels.back() = j;
    for (size_t t = ems.size() - 1; t > 0; t--) {
        j = back[t - 1][j];
        labels[t - 1] = j;
    }
    return labels;
}

// Where a span (well-formed UTF-8) is cut so that each piece is one language of
// `langs`: byte offsets, each the start of a word, ascending; none when nothing
// is to be cut. Pass 1: sentences (cut at kOrthoSent) each named by orthoSure;
// one named another language than the one before starts a new piece. Pass 2:
// inside each piece, words scored by the letters their languages do not write
// (orthoWordCost), best path with a change costing kOrthoPw. Each cut lands
// where the word-and-ending model puts the words between the two languages'
// evidence best -- at a sentence end in pass 1, at a kOrthoStrong mark when
// there is one in pass 2 -- and on a tie the later.
static inline std::vector<int> orthoSegment(int slot, const char* text, int bytes, const std::vector<std::string>& langs) {
    std::vector<int> cuts;
    NbModel m;
    std::vector<int> idx;
    if (!orthoModel(slot, langs, m, idx)) return cuts;
    std::vector<OrthoWord> toks;
    orthoWords(text, bytes, toks);
    const int n = (int)toks.size();
    if (n < 2) return cuts;
    const int nSent = (int)(sizeof kOrthoSent / sizeof kOrthoSent[0]);
    const int nStrong = (int)(sizeof kOrthoStrong / sizeof kOrthoStrong[0]);
    auto between = [&](int g, const uint32_t* marks, int count) {
        for (int i = toks[g - 1].end; i < toks[g].start;) {
            int32_t r;
            int used = lidDecode((const unsigned char*)text + i, (size_t)(toks[g].start - i), &r);
            if (used <= 0) break;
            for (int k = 0; k < count; k++) if ((uint32_t)r == marks[k]) return true;
            i += used;
        }
        return false;
    };
    // the word k's own score for B less A, summed from g to b - 1: the cut is the
    // option that gives B the most
    auto cut = [&](int a, int b, const std::string& A, const std::string& B, const std::vector<int>& options) {
        const std::vector<std::string> ab = {A, B};
        std::vector<long long> tail((size_t)(b - a + 1), 0);   // tail[k - a] = sum over [k, b)
        for (int k = b - 1; k > a; k--) {
            std::vector<int> sc;
            long long d = 0;
            if (nbScore(slot, text + toks[k].start, toks[k].end - toks[k].start, ab, sc)) d = sc[1] - sc[0];
            tail[k - a] = tail[k + 1 - a] + d;
        }
        int bestG = b;
        long long bestV = 0;
        bool have = false;
        for (int g : options) {
            const long long v = tail[g - a];
            if (!have || v > bestV || (v == bestV && g > bestG)) { bestG = g; bestV = v; have = true; }
        }
        return bestG;
    };
    std::vector<std::pair<int, int>> runs;
    int cur = -1, start = 0, last = -1, x = 0;
    for (int k = 1; k <= n; k++) {
        if (k < n && !between(k, kOrthoSent, nSent)) continue;
        const int lab = orthoSure(slot, text + toks[x].start, toks[k - 1].end - toks[x].start, langs, k - x);
        if (lab >= 0) {
            if (cur < 0) {
                cur = lab;
            } else if (lab != cur) {
                std::vector<int> options;
                for (int g = last + 1; g <= x; g++) if (between(g, kOrthoSent, nSent)) options.push_back(g);
                const int g = cut(last, x, langs[cur], langs[lab], options);
                runs.push_back({start, g});
                start = g;
                cur = lab;
            }
            last = k - 1;
        }
        x = k;
    }
    runs.push_back({start, n});
    std::vector<int> at;
    std::vector<long long> c;
    for (size_t ri = 0; ri < runs.size(); ri++) {
        if (ri) at.push_back(runs[ri].first);
        std::vector<int> ev;
        std::vector<std::vector<long long>> ems;
        for (int k = runs[ri].first; k < runs[ri].second; k++)
            if (orthoWordCost(m, idx, toks[k], c)) { ev.push_back(k); ems.push_back(c); }
        if (ev.size() < 2) continue;
        const std::vector<int> lab = orthoViterbi(ems, kOrthoPw[slot], (int)langs.size());
        for (size_t t = 1; t < ev.size(); t++) {
            if (lab[t] == lab[t - 1]) continue;
            const int a = ev[t - 1], b = ev[t];
            std::vector<int> options;
            for (int g = a + 1; g <= b; g++) if (between(g, kOrthoStrong, nStrong)) options.push_back(g);
            if (options.empty()) for (int g = a + 1; g <= b; g++) options.push_back(g);
            at.push_back(cut(a, b, langs[lab[t - 1]], langs[lab[t]], options));
        }
    }
    std::sort(at.begin(), at.end());
    at.erase(std::unique(at.begin(), at.end()), at.end());
    for (int g : at) cuts.push_back(toks[g].start);
    return cuts;
}

// The enabled codes of a script slot, one per language, in the enabled order;
// with `cands` and `labels`, those the model ML Kit runs has a label for.
static inline void blendSlotLangs(int slot, const std::vector<std::string>& enabled, std::vector<std::string>& slotLangs,
                                  std::vector<std::string>* cands = nullptr, std::vector<int>* labels = nullptr) {
    for (const std::string& code : enabled) {
        const int label = blendLabelIndex(code);
        const bool here = (label > 0 && blendLabelSlot(kLidLabels[label]) == slot) ||
                          (label < 0 && blendUnlabelledSlot(code) == slot);
        if (!here) continue;
        bool dup = false;
        for (const std::string& x : slotLangs) dup |= blendCanonical(x) == blendCanonical(code);
        if (dup) continue;
        slotLangs.push_back(code);
        if (label > 0 && cands) {
            cands->push_back(code);
            labels->push_back(label);
        }
    }
}

// What blendDetectors decided for a span of script slot `slot`: the enabled
// code, whether the text itself proved it (a letter, the Han rules, or the
// word-and-ending model past its margin), and, for the context (blendFinal),
// the script's enabled languages, the word-and-ending model's score for each
// (empty when it cannot score them) and the rare-letter veto over them
// (nbRareVeto). `lang` is empty only when fewer than two enabled languages
// write the script (then nothing here ran).
struct BlendResult {
    std::string lang;
    bool sure = false;
    std::vector<std::string> slotLangs;
    std::vector<int> nb;
    uint64_t veto = 0;
};

// Depends on nothing but its arguments, so it can be cached.
static inline BlendResult blendDetectors(int slot, const char* text, int bytes,
                                         const std::vector<std::string>& enabled) {
    BlendResult res;
    if (slot < 1 || slot > 6 || bytes <= 0) return res;
    blendSlotLangs(slot, enabled, res.slotLangs);
    if (res.slotLangs.size() < 2) return res;
    auto enabledCode = [&](const char* want) -> std::string {
        for (const std::string& code : enabled) if (blendCanonical(code) == want || code == want) return code;
        return "";
    };
    auto sure = [&](const std::string& lang) { res.lang = lang; res.sure = true; return res; };

    // 0. the letters of each orthography, word by word: one language left is
    // sure; otherwise everything below (and the context) only chooses between
    // the languages they leave
    if (slot == 2 || slot == 3 || slot == 6) {
        std::vector<char> keep;
        std::vector<long long> total;
        int best = 0;
        if (orthoDecide(slot, text, bytes, res.slotLangs, keep, total, best, true)) {
            std::vector<std::string> left;
            for (size_t j = 0; j < res.slotLangs.size(); j++) if (keep[j]) left.push_back(res.slotLangs[j]);
            if (left.size() == 1) return sure(left[0]);
            res.slotLangs = left;
        }
    }

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
    if (slot == 2 || slot == 3 || slot == 6) res.veto = nbRareVeto(slot, text, bytes, res.slotLangs);

    // 3. the model ML Kit runs and the word-and-ending model, weighed; where
    // the model has no label for one of them (Assamese, Sanskrit) the
    // word-and-ending model alone, else the model between those it knows
    std::vector<int> labels;
    bool allLabelled = true;
    for (const std::string& c : res.slotLangs) {
        const int label = blendLabelIndex(c);
        labels.push_back(label);
        if (label <= 0) allLabelled = false;
    }
    float p[EV_LID_CLASSES];
    const bool haveP = lidClassify(text, (size_t)bytes, p);
    size_t best = 0;
    if (allLabelled && haveP) {
        double bestScore = -1e300;
        for (size_t c = 0; c < res.slotLangs.size(); c++) {
            const double score = std::log(p[labels[c]] + 1e-6) + kBlendC[slot] * (res.nb.empty() ? 0.0 : res.nb[c] / 8.0);
            if (score > bestScore) { bestScore = score; best = c; }
        }
    } else if (!res.nb.empty()) {
        for (size_t c = 1; c < res.nb.size(); c++) if (res.nb[c] > res.nb[best]) best = c;
    } else if (haveP) {
        float bestP = -1.0f;
        for (size_t c = 0; c < res.slotLangs.size(); c++)
            if (labels[c] > 0 && p[labels[c]] > bestP) { bestP = p[labels[c]]; best = c; }
    }
    res.lang = res.slotLangs[best];
    return res;
}

// ---- CONTEXT ----
// A text that proves nothing ("ब्लूटूथ", "प्रस्तावना", "تمام": the same in two
// enabled languages) takes the language of the last text of its script that
// did prove one -- the screen or the message being read -- unless the
// word-and-ending model puts its own pick at least kCtxMargin (9 nats, 8,100 to
// 1) above that language, or the span holds a letter its own answer writes
// and that language practically never does (BlendResult::veto). One per
// script slot, shared by every caller.
// 8 nats since 2026-10-22 (was 6): the model learned from Android's UI text
// is surer of itself ("Data" is a word of Indonesian and Italian UI as much as
// of English) and overruled a right context more often. Measured on the
// suite's held-out half, a stream whose last sure language is the text's own /
// another with probability 0.1 / 0.3 / 0.5: 6 nats 99.76 / 98.81 / 97.07 /
// 95.22%; 8 nats 99.85 / 98.72 / 96.55 / 94.31 -- above the earlier model at 6
// nats (99.78 / 98.63 / 96.45 / 94.16) at every one; 10 nats 99.90 / 98.55 /
// 95.98 / 93.38.
// 9 nats since 2026-10-27 (was 8): the model now holds EVERY feature it
// learned (langnb.h), so one word's endings ("1a", "2ta", "3ata" beside "wdata")
// add up to more nats than before. Same measure, every-feature model: 8 nats
// 99.82 / 98.82 / 96.92 / 94.98; 9 nats 99.85 / 98.76 / 96.68 / 94.56 -- the
// smallest margin at which it is at or above the 2026-10-22 model at 8 nats at
// every one. Auto mode over the whole suite (tools/verify/automode) at 9 nats:
// en+id+ms 99.54 -> 99.32%, Android UI 99.83 -> 99.80, the rest equal or 0.03.
static const int kCtxMargin = 72;
static std::mutex gBlendContextMutex;
static std::string gBlendContext[7];

// The span's language given the context ctx ("" for none): ctx unless the
// span proved its own (r.sure, the caller's test), the veto or the
// word-and-ending model's margin keep its own answer.
static inline std::string blendWithContext(const BlendResult& r, const std::string& got, const std::string& ctx) {
    int ci = -1, gi = -1;
    for (size_t i = 0; i < r.slotLangs.size(); i++) {
        if (r.slotLangs[i] == ctx) ci = (int)i;
        if (r.slotLangs[i] == got) gi = (int)i;
    }
    if (ctx.empty() || ci < 0 || ctx == got) return got;
    if (gi >= 0 && ((r.veto >> (gi * 8 + ci)) & 1)) return got;
    if (r.nb.empty() || gi < 0) return ctx;
    return r.nb[gi] - r.nb[ci] >= kCtxMargin ? got : ctx;
}

// The span's language from a (possibly cached) BlendResult and the answer the
// span had without it; a sure result becomes the script's context.
static inline std::string blendFinal(int slot, const BlendResult& r, const std::string& plainAnswer) {
    const std::string got = r.lang.empty() ? plainAnswer : r.lang;
    if (slot < 1 || slot > 6 || r.slotLangs.size() < 2) return got;
    std::lock_guard<std::mutex> lock(gBlendContextMutex);
    if (r.sure) {
        gBlendContext[slot] = r.lang;
        return r.lang;
    }
    return blendWithContext(r, got, gBlendContext[slot]);
}
