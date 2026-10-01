// Is the fold a no-op on text it already folded? buildMixChunks folds the whole
// utterance, and every chunk processDirect detects is a piece of that folded
// text cut at code-point boundaries (plus ASCII spaces respaceDigits puts
// between digits). The fold maps code point by code point, so folding a chunk
// again changes nothing exactly when f(f(c)) == f(c) for every code point c --
// checked here over all 1,114,112, through the real normalizeFancyText.
#include "tts_engine_core.cpp"
#include <cstdio>
int main(){
    long bad = 0, folded = 0;
    for (int cp = 0; cp <= 0x10FFFF; cp++) {
        if (cp >= 0xD800 && cp <= 0xDFFF) continue;   // never in valid UTF-8
        std::string one; appendCodepointUtf8(one, cp);
        std::string once = normalizeFancyText(one);
        std::string twice = normalizeFancyText(once);
        if (once != one) folded++;
        if (twice != once) { if (++bad <= 10) printf("U+%04X folds again\n", cp); }
    }
    // and the respaced digits, the only bytes not cut from the folded text
    if (normalizeFancyText(" 0123456789") != " 0123456789") { bad++; printf("digits/space fold\n"); }
    printf("%ld code points change under the fold; folding them again changes %ld\n", folded, bad);
    return bad ? 1 : 0;
}
