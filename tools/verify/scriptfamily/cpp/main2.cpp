#include <string>
#include <cstring>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stdint.h>
#include <mutex>
#include <iostream>
#include "core2.inc"
int main(){
    std::string outBuf; outBuf.reserve(1<<22);
    char hex[16];
    for(int cp=0; cp<=0x10FFFF; cp++){
        LangFamily f = familyForCp(cp);
        snprintf(hex,sizeof hex,"%x",cp);
        outBuf += hex; outBuf += ' ';
        outBuf += (f.primary ? f.primary : "null");
        outBuf += '|';
        int cap=(int)(f.members.size()/0.75)+1; if(cap<16) cap=16;
        bool first=true;
        for(const auto& m : javaHashSetOrder(f.members, cap)){ if(!first) outBuf += ','; outBuf += m; first=false; }
        outBuf += '\n';
        if(outBuf.size() > (1<<21)){ fwrite(outBuf.data(),1,outBuf.size(),stdout); outBuf.clear(); }
    }
    fwrite(outBuf.data(),1,outBuf.size(),stdout);
    return 0;
}
