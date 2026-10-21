// Every code point through the core's Unicode predicates, one line each:
//   cp  fold  emoji  letter  slot  latinbuf  bidi  family  scxmask  attach
// slot = spanScriptSlot (the span detector's classifyScript), latinbuf =
// isLatinBufferCp (buildMixChunks), bidi = the table d0.q's strip reads,
// scxmask = the slots a neutral code point's Script_Extensions name (the span
// walk's all-neutral run), attach = kUniAttach (the segmenter's buffer rule).
#include <string>
#include <vector>
#include <unordered_set>
#include <unordered_map>
#include <cstdio>
#include <cstdint>
#include <mutex>
#include <memory>
#include <algorithm>
#include "core.inc"
int main(){
  for(int cp=0; cp<0x110000; cp++){
    if(cp>=0xD800&&cp<=0xDFFF) continue;
    int slot = spanScriptSlot(cp);
    bool lat = isLatinBufferCp(cp);
    printf("%X\t%X\t%d\t%d\t%d\t%d\t%d\t%s\t%llX\t%d\n", cp, foldCodepoint(cp), isEmoji(cp)?1:0, isLetterCodepoint(cp)?1:0,
           slot, lat?1:0, uniIn(kUniBidiControl, cp)?1:0, familyForCp(cp).primary,
           (unsigned long long)uniMask(kUniScxMask, cp), (int)uniValue(kUniAttach, cp, -1));
  }
}
