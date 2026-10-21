// langblend.h's orthoSegment on every line of a TSV (slot, enabled languages, text):
// the byte offsets where a new piece starts, comma-separated ("-" for none).
//   seg < cases.tsv
#include <cstdio>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>
#include "langblend.h"

int main() {
    std::string line;
    while (std::getline(std::cin, line)) {
        std::stringstream in(line);
        std::string slot, langs, text;
        std::getline(in, slot, '\t');
        std::getline(in, langs, '\t');
        std::getline(in, text);
        for (size_t i = 0; i < text.size(); i++)
            if (text[i] == '\\' && i + 1 < text.size() && text[i + 1] == 'n') text.replace(i, 2, "\n");
        std::vector<std::string> set;
        std::stringstream ls(langs);
        std::string l;
        while (std::getline(ls, l, ',')) set.push_back(l);
        std::vector<std::string> slotLangs;   // as emitScriptSpan takes them from the hints
        blendSlotLangs(atoi(slot.c_str()), set, slotLangs);
        const std::vector<int> cuts = orthoSegment(atoi(slot.c_str()), text.data(), (int)text.size(), slotLangs);
        std::string out;
        for (int c : cuts) out += (out.empty() ? "" : ",") + std::to_string(c);
        printf("%s\n", out.empty() ? "-" : out.c_str());
    }
    return 0;
}
