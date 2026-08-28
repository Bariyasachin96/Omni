#include <string>
#include <cstring>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stdint.h>
#include <mutex>
#include <iostream>
#include <sstream>
#include "core.inc"

static std::string esc(const std::string& s){
    std::string out;
    int i=0,n=(int)s.size();
    while(i<n){
        int len=0; int cp=utf8ToCodepoint((const unsigned char*)s.c_str()+i,len);
        if(cp=='\n') out += "\\n";
        else if(cp<0x20 || cp==0x7f){ char b[16]; snprintf(b,sizeof b,"\\u%04x",cp); out+=b; }
        else out.append(s,i,len);
        i+=len;
    }
    return out;
}
int main(){
    std::string line;
    while(std::getline(std::cin,line)){
        if(line.empty()) continue;
        std::vector<std::string> f;
        {
            size_t p=0;
            for(;;){ size_t q=line.find('\t',p); if(q==std::string::npos){ f.push_back(line.substr(p)); break;} f.push_back(line.substr(p,q-p)); p=q+1; }
        }
        int mode      = atoi(f[0].c_str());
        ReadingModes m;
        m.numberMode      = atoi(f[1].c_str());
        m.punctuationMode = atoi(f[2].c_str());
        m.emojiMode       = atoi(f[3].c_str());
        m.punctuationInFlow = f[4]=="1";
        m.smartNumber       = f[5]=="1";
        m.smartNumberGroupSize = atoi(f[6].c_str());
        std::string dualLang = f[7], mixNonLat = f[8], deviceIso3 = f[9];
        setLanguageHintsForTest(f[10]);
        // Columns 12 and 13 are OPTIONAL and EasyVoice-only: keepTimeMarker and
        // the CLDR day-period marker list. Absent means off, so the generated
        // 12-column battery -- the 163,296 cases that prove parity with AutoTTS
        // -- runs with the flag clear and is completely unaffected by it.
        if (f.size() > 12) m.keepTimeMarker = f[12]=="1";
        if (f.size() > 13) m.dayPeriodMarkers = f[13];
        std::string text = f[11];
        { std::string t; for(size_t i=0;i<text.size();i++){ if(text[i]=='\\'&&i+1<text.size()&&text[i+1]=='t'){t+='\t';i++;} else t+=text[i]; } text=t; }
        // d0.t: n7 = 2 when (mode 1 and device==dualLang) or (mode 4/5 and device==mixNonLatin)
        int neutralType = 1;
        if (mode==1) { if (deviceIso3==dualLang) neutralType = 2; }
        else if (mode==4 || mode==5) { if (deviceIso3==mixNonLat) neutralType = 2; }
        m.numberSpecific="N"; m.punctuationSpecific="P"; m.emojiSpecific="E";
        std::vector<std::string> sentences{text};
        auto chunks = buildMixChunks(sentences, "LAT", "NONLAT", m, "ND", neutralType, false, mode==1);
        std::string out;
        for(size_t i=0;i<chunks.size();i++){
            if(i) out += " | ";
            out += std::to_string(chunks[i].type) + ":'" + esc(chunks[i].text) + "'";
        }
        printf("%s\n", out.c_str());
    }
    return 0;
}
