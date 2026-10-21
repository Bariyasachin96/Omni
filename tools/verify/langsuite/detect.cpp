// The detector on every case of cases.tsv.
//
//   detect cases.tsv sets.txt raw.tsv pipeline.tsv
//
// raw.tsv -- the model ML Kit runs (langid.h) alone, its top label among all
// its languages:
//   label TAB class TAB top TAB p(top)
//
// pipeline.tsv -- the VOICE the core chooses, for every set of sets.txt and
// every case in a language of that set:
//   model     the enabled language of the span's script with the highest
//             probability; the script's fallback (the first enabled language
//             of kScriptLangPairs for it) where none of them is a label
//   blend     langblend.h's blendDetectors, exactly what the core decides for
//             a span of a script two or more enabled languages share
//   set TAB label TAB class TAB split TAB model TAB fallback TAB
//   model's candidates lang=p,... TAB blend TAB text TAB ctxmap
// -- ctxmap: what blendFinal answers after each possible context (each enabled
// language of the script as the last sure one), for combine.py to check.
// (Until 2026-10-21 CLD2's answers were columns here too; CLD2 is gone.)
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <set>
#include <sstream>
#include <string>
#include <vector>
#include "langid.h"
#include "langblend.h"

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

        float p[EV_LID_CLASSES];
        lidClassify(text.data(), text.size(), p);
        int top = 0;
        for (int k = 1; k < EV_LID_CLASSES; k++) if (p[k] > p[top]) top = k;
        fprintf(raw, "%s\t%s\t%s\t%.4f\n", label.c_str(), cls.c_str(), kLidLabels[top], p[top]);

        for (size_t s = 0; s < sets.size() && slot >= 1; s++) {
            const std::vector<std::string>& set = sets[s];
            bool in = false;
            for (const std::string& l : set) if (l == label) in = true;
            if (!in) continue;
            auto enabled = [&](const std::string& code) {
                for (const std::string& l : set) if (l == code) return true;
                return false;
            };
            std::string fallback;
            for (auto& pr : kPairs)
                if (pr.slot == slot && enabled(pr.code) && fallback.empty()) fallback = pr.code;
            std::string model, cands;
            float best = -1;
            for (int k = 0; k < EV_LID_CLASSES; k++) {
                if (blendLabelSlot(kLidLabels[k]) != slot || !enabled(kLidLabels[k])) continue;
                char one[64];
                snprintf(one, sizeof one, "%s%s=%.6f", cands.empty() ? "" : ",", kLidLabels[k], p[k]);
                cands += one;
                if (p[k] > best) { best = p[k]; model = kLidLabels[k]; }
            }
            if (model.empty()) model = fallback.empty() ? "un" : fallback;
            std::string name;
            for (const std::string& l : set) name += (name.empty() ? "" : "+") + l;
            const BlendResult br = blendDetectors(slot, text.data(), (int)text.size(), set);
            const std::string blend = br.lang.empty() ? model : br.lang;
            std::string ctxmap;
            for (const std::string& ctx : br.slotLangs) {
                gBlendContext[slot] = ctx;
                ctxmap += (ctxmap.empty() ? "" : ",") + ctx + ":" + blendFinal(slot, br, model);
            }
            gBlendContext[slot].clear();
            fprintf(pipe, "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", name.c_str(), label.c_str(), cls.c_str(),
                    split.c_str(), model.c_str(), fallback.c_str(), cands.c_str(), blend.c_str(), text.c_str(),
                    ctxmap.c_str());
        }
        n++;
    }
    fclose(raw);
    fclose(pipe);
    printf("detect: %ld cases\n", n);
    return 0;
}
