// Both detectors on every case of cases.tsv.
//
//   detect cases.tsv sets.txt raw.tsv pipeline.tsv
//
// raw.tsv -- each detector alone, as the core calls it for a span but with no
// hint: CLD2's ExtDetectLanguageSummary (plain text, flags 0x4000) and the
// model's top label (langid.h):
//   label TAB class TAB cld2 TAB top TAB p(top)
// CLD2's codes are written as the model's labels where they name the same
// language (tl -> fil, jw -> jv, zh-Hant -> zh, nn -> no, sr-ME -> sr).
//
// pipeline.tsv -- the VOICE each would choose, for every set of sets.txt and
// every case in a language of that set:
//   CLD2  the core's span path as it stands (emitScriptSpan): the per-script
//         hint (the one enabled language of that script, if exactly one), the
//         summary if enabled, else the first enabled of CLD2's top three that
//         covers 1%+, else the script's fallback (the first enabled language of
//         kScriptLangPairs for it)
//   model the enabled language of the span's script with the highest
//         probability; the same fallback where none of them is a label
//   set TAB label TAB class TAB split TAB cld2 TAB model TAB fallback TAB
//   cld2 summary:reliable:lang3/percent3 x3 TAB model's candidates lang=p,... TAB
//   blend (langblend.h, exactly what the core decides) TAB text
// -- everything a way of combining the two needs, so combine.py can try them
// without running either detector again.
#include <cstdio>
#include <cstring>
#include <set>
#include <sstream>
#include <string>
#include <vector>
#include "compact_lang_det.h"
#include "encodings.h"
#include "lang_script.h"
#include "langid.h"
#include "langblend.h"

static std::string cld2Label(CLD2::Language lang) {
    const char* code = CLD2::LanguageCode(lang);
    std::string c = code ? code : "un";
    if (c == "tl") return "fil";
    if (c == "jw") return "jv";
    if (c == "zh-Hant") return "zh";
    if (c == "nn") return "no";
    if (c == "sr-ME") return "sr";
    if (c == "un" || c.compare(0, 3, "xx-") == 0) return "unknown";
    return c;
}

// The core's kScriptLangPairs, in its order (as codes).
static const struct { int slot; const char* code; } kPairs[] = {
    {3, "ar"}, {3, "fa"}, {3, "ur"}, {3, "ps"}, {3, "sd"},
    {2, "ru"}, {2, "uk"}, {2, "bg"}, {2, "sr"}, {2, "mk"}, {2, "be"}, {2, "mn"}, {2, "tg"}, {2, "kk"}, {2, "ky"},
    {4, "hi"}, {4, "mr"}, {4, "ne"}, {4, "sa"},
    {6, "bn"}, {6, "as"},
    {5, "zh"}, {5, "zh-Hant"}, {5, "ja"},
    {1, "en"}, {1, "es"}, {1, "fr"}, {1, "pt"}, {1, "de"}, {1, "it"}, {1, "vi"}, {1, "id"}, {1, "tr"},
    {1, "nl"}, {1, "pl"}, {1, "ro"}, {1, "cs"}, {1, "hu"}, {1, "sv"}, {1, "da"}, {1, "no"}, {1, "fi"},
    {1, "sk"}, {1, "hr"}, {1, "ms"}, {1, "tl"}, {1, "sw"}, {1, "af"},
};

// The model's labels by script (ML Kit's support table): slot 1 is the rest.
static int labelSlot(const std::string& l) {
    static const char* const kCyrl[] = {"be", "bg", "kk", "ky", "mk", "mn", "ru", "sr", "tg", "uk"};
    static const char* const kArab[] = {"ar", "fa", "ps", "sd", "ur"};
    static const char* const kDeva[] = {"hi", "mr", "ne"};
    static const char* const kOther[] = {"am", "el", "gu", "hy", "iw", "ka", "km", "kn", "ko", "lo", "ml", "my",
                                         "pa", "si", "ta", "te", "th", "yi", "unknown"};
    for (const char* c : kCyrl) if (l == c) return 2;
    for (const char* c : kArab) if (l == c) return 3;
    for (const char* c : kDeva) if (l == c) return 4;
    if (l == "zh" || l == "ja") return 5;
    if (l == "bn") return 6;
    for (const char* c : kOther) if (l == c) return 0;
    return 1;
}

int main(int argc, char** argv) {
    if (argc != 5) { fprintf(stderr, "usage: detect cases.tsv sets.txt raw.tsv pipeline.tsv\n"); return 2; }
    std::vector<std::vector<std::string>> sets;
    {
        FILE* f = fopen(argv[2], "r");
        char line[512];
        while (f && fgets(line, sizeof line, f)) {
            if (line[0] == '#' || line[0] == '\n') continue;
            std::istringstream words(line);
            std::vector<std::string> set;
            std::string w;
            while (words >> w) set.push_back(w);
            if (!set.empty()) sets.push_back(set);
        }
        if (f) fclose(f);
    }
    FILE* in = fopen(argv[1], "rb");
    FILE* raw = fopen(argv[3], "w");
    FILE* pipe = fopen(argv[4], "w");
    if (!in || !raw || !pipe) { fprintf(stderr, "cannot open\n"); return 2; }
    static char line[1 << 16];
    long n = 0;
    while (fgets(line, sizeof line, in)) {
        line[strcspn(line, "\n")] = 0;
        std::vector<std::string> f;
        std::istringstream cols(line);
        std::string c;
        while (std::getline(cols, c, '\t')) f.push_back(c);
        if (f.size() != 5) continue;
        const std::string& label = f[0];
        const int slot = atoi(f[1].c_str());
        const std::string& cls = f[2];
        const std::string& split = f[3];
        const std::string& text = f[4];

        CLD2::Language lang3[3]; int percent3[3]; double score3[3];
        int textBytes = 0; bool reliable = false;
        CLD2::CLDHints none = {nullptr, nullptr, CLD2::UNKNOWN_ENCODING, CLD2::UNKNOWN_LANGUAGE};
        CLD2::Language summary = CLD2::ExtDetectLanguageSummary(text.data(), (int)text.size(), true, &none, 0x4000,
                                                               lang3, percent3, score3, nullptr, &textBytes, &reliable);
        float p[EV_LID_CLASSES];
        lidClassify(text.data(), text.size(), p);
        int top = 0;
        for (int k = 1; k < EV_LID_CLASSES; k++) if (p[k] > p[top]) top = k;
        fprintf(raw, "%s\t%s\t%s\t%s\t%.4f\n", label.c_str(), cls.c_str(), cld2Label(summary).c_str(), kLidLabels[top], p[top]);

        for (size_t s = 0; s < sets.size() && slot >= 1; s++) {
            const std::vector<std::string>& set = sets[s];
            bool in = false;
            for (const std::string& l : set) if (l == label) in = true;
            if (!in) continue;
            auto enabled = [&](const std::string& code) {
                for (const std::string& l : set) if (l == code) return true;
                return false;
            };
            int matched = 0;
            std::string hintCode, fallback;
            for (auto& pr : kPairs) {
                if (pr.slot != slot || !enabled(pr.code)) continue;
                matched++;
                hintCode = pr.code;
                if (fallback.empty()) fallback = pr.code;
            }
            CLD2::Language hint = matched == 1 ? CLD2::GetLanguageFromName(hintCode.c_str()) : CLD2::UNKNOWN_LANGUAGE;
            CLD2::CLDHints hints = {nullptr, nullptr, CLD2::UNKNOWN_ENCODING, hint};
            CLD2::Language got = CLD2::ExtDetectLanguageSummary(text.data(), (int)text.size(), true, &hints, 0x4000,
                                                               lang3, percent3, score3, nullptr, &textBytes, &reliable);
            std::string cld = CLD2::LanguageCode(got) ? CLD2::LanguageCode(got) : "un";
            if (!enabled(cld)) {
                bool picked = false;
                for (int rank = 0; rank < 3 && !picked; rank++) {
                    if (lang3[rank] == CLD2::UNKNOWN_LANGUAGE || percent3[rank] < 1) continue;
                    const char* other = CLD2::LanguageCode(lang3[rank]);
                    if (other && enabled(other)) { cld = other; picked = true; }
                }
                if (!picked && !fallback.empty()) cld = fallback;
            }
            std::string model, cands;
            float best = -1;
            for (int k = 0; k < EV_LID_CLASSES; k++) {
                if (labelSlot(kLidLabels[k]) != slot || !enabled(kLidLabels[k])) continue;
                char one[64];
                snprintf(one, sizeof one, "%s%s=%.6f", cands.empty() ? "" : ",", kLidLabels[k], p[k]);
                cands += one;
                if (p[k] > best) { best = p[k]; model = kLidLabels[k]; }
            }
            if (model.empty()) model = fallback.empty() ? "un" : fallback;
            std::string detail = std::string(CLD2::LanguageCode(got) ? CLD2::LanguageCode(got) : "un") + ":" + (reliable ? "1" : "0");
            for (int rank = 0; rank < 3; rank++) {
                char one[48];
                snprintf(one, sizeof one, ":%s/%d", CLD2::LanguageCode(lang3[rank]) ? CLD2::LanguageCode(lang3[rank]) : "un", percent3[rank]);
                detail += one;
            }
            std::string name;
            for (const std::string& l : set) name += (name.empty() ? "" : "+") + l;
            const BlendResult br = blendDetectors(slot, text.data(), (int)text.size(), set, cld, lang3, percent3);
            std::string blend = br.lang.empty() ? cld : br.lang;
            // what blendFinal answers after each possible context (each enabled
            // language of the script as the last sure one), for combine.py to check
            std::string ctxmap;
            for (const std::string& c : br.slotLangs) {
                gBlendContext[slot] = c;
                ctxmap += (ctxmap.empty() ? "" : ",") + c + ":" + blendFinal(slot, br, cld);
            }
            gBlendContext[slot].clear();
            fprintf(pipe, "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", name.c_str(), label.c_str(), cls.c_str(), split.c_str(),
                    cld.c_str(), model.c_str(), fallback.c_str(), detail.c_str(), cands.c_str(), blend.c_str(), text.c_str(),
                    ctxmap.c_str());
        }
        n++;
    }
    fclose(raw);
    fclose(pipe);
    printf("detect: %ld cases\n", n);
    return 0;
}
