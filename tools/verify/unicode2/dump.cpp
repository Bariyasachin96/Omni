// See run.sh. Asks the REAL functions, sliced out of tts_engine_core.cpp.
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
static bool oldJavaWhitespace(int c){
    return (c>=0x09&&c<=0x0D)||(c>=0x1C&&c<=0x1F)||c==0x20||c==0x1680||
           (c>=0x2000&&c<=0x200A&&c!=0x2007)||c==0x205F||c==0x3000||c==0x2028||c==0x2029;
}
static void hexOf(const std::string& s){
    size_t at = 0; bool first = true;
    while(at < s.size()){ int len = 0; int cp = utf8ToCodepoint((const unsigned char*)s.c_str() + at, len); printf(first ? "%X" : " %X", cp); first = false; at += len; }
}
int main(int argc, char** argv){
    // per code point: cp ws oldws digit currency nospace lower...
    for(int cp = 0; cp < 0x110000; cp++){
        if(cp >= 0xD800 && cp <= 0xDFFF) continue;
        std::string one; appendCodepointUtf8(one, cp);
        printf("C\t%X\t%d\t%d\t%d\t%d\t%d\t", cp, isJavaWhitespace(cp) ? 1 : 0, oldJavaWhitespace(cp) ? 1 : 0,
               decimalDigitValue(cp), isCurrencySymbol(cp) ? 1 : 0, keywordMatchesAnywhere(one) ? 1 : 0);
        hexOf(lowerCaseRoot(one)); printf("\n");
    }
    // strings on stdin (one per line, \u-free UTF-8): their lowercase
    char buf[4096];
    while(fgets(buf, sizeof buf, stdin)){
        std::string s(buf); while(!s.empty() && (s.back() == '\n' || s.back() == '\r')) s.pop_back();
        printf("S\t"); hexOf(s); printf("\t"); hexOf(lowerCaseRoot(s)); printf("\n");
    }
}
