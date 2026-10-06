// Quick character read, every code point (owner, 2026-10-06). The REAL core is
// included whole. For each one-UTF-16-unit character c:
//   Latin / Common / Inherited  -> foldAndSpansFlat(c, quick) is the fold alone
//                                  (no spans: clsCLD2.e's "un", as before)
//   any other script            -> foldAndSpansFlat(c, quick) == foldAndSpansFlat(c, no quick)
//                                  (a matra, halant, akshar is detected like text)
// and the Kotlin side's decision (UnicodeTables.kt, read from the file) is the
// same set as the C++ table, so both halves stop on exactly the same characters.
#include "tts_engine_core.cpp"
#include <cstdio>
#include <fstream>
#include <sstream>
#include <regex>
int main(int argc, char** argv){
    std::vector<std::string> en = {"en","hi","gu","mr","bn","ta","th"};
    { std::string j; for(size_t i=0;i<en.size();i++){ if(i) j+=","; j+=en[i]; }
      auto st = std::make_shared<HintState>(); st->list=j; st->codes=en; rebuildScriptLanguageTables(*st); g_hintState=st; }
    // the Kotlin table
    std::ifstream f(argv[1]); std::stringstream ss; ss << f.rdbuf(); std::string kt = ss.str();
    std::string body = kt.substr(kt.find("intArrayOf("));
    body = body.substr(0, body.find(")"));
    std::vector<long> nums; std::regex hex("0x([0-9A-F]+)");
    for(std::sregex_iterator it(body.begin(), body.end(), hex), e; it!=e; ++it) nums.push_back(std::stol((*it)[1], nullptr, 16));
    auto ktLci = [&](int c){ for(size_t i=0;i+1<nums.size();i+=2) if(c>=nums[i] && c<=nums[i+1]) return true; return false; };
    long checked=0, stopped=0, detected=0, bad=0, tableDiff=0;
    for(int c=0x20; c<0x10000; c++){
        if(c>=0xD800 && c<=0xDFFF) continue;
        std::string t; appendCodepointUtf8(t, c);
        if(ktLci(c) != uniIn(kUniLatinCommonInherited, c)) tableDiff++;
        // the fold first decides what the core looks at (a fancy letter folds to ASCII)
        std::string folded = needsFancyFold(t) ? normalizeFancyText(t) : t;
        auto q = foldAndSpansFlat(t, true);
        auto nq = foldAndSpansFlat(t, false);
        checked++;
        int units = 0; for(unsigned char b : folded){ if((b&0xC0)==0x80) continue; units += b>=0xF0 ? 2 : 1; }
        bool expectStop = units == 1 && uniIn(kUniLatinCommonInherited, firstCodepointOf(folded));
        bool ok;
        if(expectStop){ stopped++; ok = q.size()==1 && q[0]==folded; }
        else { detected++; ok = q == nq; }
        if(!ok){ bad++; if(bad<5) printf("DIFF U+%04X\n", c); }
    }
    printf("%ld single characters: %ld stop at quick read, %ld detected; %ld wrong; Kotlin vs C++ table: %ld differ\n",
           checked, stopped, detected, bad, tableDiff);
    return (bad || tableDiff) ? 1 : 0;
}
