// Two functions changed shape on 2026-10-01 for speed; this proves they answer
// the same, over EVERY input that matters, against the old bodies copied in.
//   isLetterCodepoint -- ASCII answered first, the exception list from U+0300:
//                        all 1,114,112 code points.
//   splitByPunct      -- a punctuation run's type is worked out while it is
//                        scanned instead of by segmentTypeOf: every run of one
//                        and two code points from the run alphabet.
#include "tts_engine_core.cpp"
#include <cstdio>
static bool oldIsLetterCodepoint(int codePoint){
    if(isNonLetterInsideLetterBlock(codePoint)) return false;
    if(codePoint == 0x00AA || codePoint == 0x00B5 || codePoint == 0x00BA) return true;
    return (codePoint>=0x41&&codePoint<=0x5A)||(codePoint>=0x61&&codePoint<=0x7A)||
           (codePoint>=0xC0&&codePoint<=0xD6)||(codePoint>=0xD8&&codePoint<=0xF6)||(codePoint>=0xF8&&codePoint<=0x24F)||
           (codePoint>=0x370&&codePoint<=0x3FF)||(codePoint>=0x400&&codePoint<=0x52F)||
           (codePoint>=0x531&&codePoint<=0x58F)||(codePoint>=0x5D0&&codePoint<=0x5EA)||
           (codePoint>=0x620&&codePoint<=0x64A)||(codePoint>=0x66E&&codePoint<=0x6D3)||
           (codePoint>=0x710&&codePoint<=0x74F)||(codePoint>=0x900&&codePoint<=0x97F)||
           (codePoint>=0x980&&codePoint<=0x9FF)||(codePoint>=0xA00&&codePoint<=0xA7F)||
           (codePoint>=0xA80&&codePoint<=0xAFF)||(codePoint>=0xB00&&codePoint<=0xB7F)||
           (codePoint>=0xB80&&codePoint<=0xBFF)||(codePoint>=0xC00&&codePoint<=0xC7F)||
           (codePoint>=0xC80&&codePoint<=0xCFF)||(codePoint>=0xD00&&codePoint<=0xD7F)||
           (codePoint>=0xD80&&codePoint<=0xDFF)||(codePoint>=0xE01&&codePoint<=0xE5B)||
           (codePoint>=0xE81&&codePoint<=0xEDF)||(codePoint>=0x1000&&codePoint<=0x109F)||
           (codePoint>=0x1780&&codePoint<=0x17D3)||(codePoint>=0x1E00&&codePoint<=0x1EFF)||
           (codePoint>=0x2C60&&codePoint<=0x2C7F)||(codePoint>=0x2E80&&codePoint<=0x9FFF)||
           (codePoint>=0xA720&&codePoint<=0xA7FF)||(codePoint>=0xAC00&&codePoint<=0xD7AF)||
           (codePoint>=0xF900&&codePoint<=0xFAFF);
}

int main(){
    long bad = 0;
    for (int cp = 0; cp <= 0x10FFFF; cp++)
        if (oldIsLetterCodepoint(cp) != isLetterCodepoint(cp)) { if (++bad <= 5) printf("isLetter U+%04X\n", cp); }
    printf("isLetterCodepoint: %ld of 1,114,112 differ\n", bad);
    std::vector<int> alphabet;
    for (int cp = 0; cp <= 0x10FFFF; cp++) if (cp < 0xD800 || cp > 0xDFFF) if (cpIsPunctRun(cp)) alphabet.push_back(cp);
    long runs = 0, badRuns = 0;
    auto check = [&](const std::string& text){
        std::vector<TextSegment> got; splitByPunct(text, got);
        runs++;
        for (auto& seg : got) if (seg.type != segmentTypeOf(seg.text)) { if (++badRuns <= 5) printf("run typed %d, segmentTypeOf %d\n", seg.type, segmentTypeOf(seg.text)); }
    };
    for (int a : alphabet) {
        std::string one; appendCodepointUtf8(one, a); check(one); check("x" + one + "y");
        for (int b : alphabet) { std::string two = one; appendCodepointUtf8(two, b); check(two); }
    }
    printf("splitByPunct: %zu run code points, %ld texts, %ld pieces typed differently\n", alphabet.size(), runs, badRuns);
    return (bad || badRuns) ? 1 : 0;
}
