// Sweep every Unicode code point through OUR normaliser and print the ones that
// change, as "<from hex> <to hex>". The Java side prints the same, so diff is
// the whole test.
//
// core.inc is sliced from the live app/src/main/cpp/tts_engine_core.cpp by
// tools/verify/make_core_inc.py, so this always tests the current code rather
// than a copy that went stale.
#include <string>
#include <cstring>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stdint.h>
#include <mutex>
#include "core.inc"

int main(){
    std::string out;
    char line[32];
    for (int cp = 0; cp <= 0x10FFFF; cp++) {
        int mapped = normalizeFancyCodepoint(cp);
        if (mapped == cp) continue;
        snprintf(line, sizeof line, "%x %x\n", cp, mapped);
        out += line;
        if (out.size() > (1u << 20)) { fwrite(out.data(), 1, out.size(), stdout); out.clear(); }
    }
    fwrite(out.data(), 1, out.size(), stdout);
    return 0;
}
