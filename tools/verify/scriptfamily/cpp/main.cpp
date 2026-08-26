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
int main(int argc, char** argv){
    std::unordered_set<std::string> enabled;
    if(argc > 1 && argv[1][0]){
        std::string s(argv[1]); size_t p=0;
        for(;;){ size_t q=s.find(',',p); std::string one = (q==std::string::npos)?s.substr(p):s.substr(p,q-p);
                 if(!one.empty()) enabled.insert(one); if(q==std::string::npos) break; p=q+1; }
    }
    std::string outBuf; outBuf.reserve(1<<22);
    char hex[16];
    for(int cp=0; cp<=0x10FFFF; cp++){
        std::string res = enabled.empty() ? std::string("") : familyLangForCpFiltered(cp, enabled);
        snprintf(hex,sizeof hex,"%x",cp);
        outBuf += hex; outBuf += ' '; outBuf += res; outBuf += '\n';
        if(outBuf.size() > (1<<21)){ fwrite(outBuf.data(),1,outBuf.size(),stdout); outBuf.clear(); }
    }
    fwrite(outBuf.data(),1,outBuf.size(),stdout);
    return 0;
}
