// Script audit probe (owner, 2026-10-06): the REAL core, included whole, plus
// the Kotlin decisions that follow it (runsFromFlat, languageForDetectedRun,
// detectWindowThenAggregate, aggregateOf) transcribed in C++.
// stdin, one case per line, tab separated:
//   id  hintsIso2(comma)  okIso3(comma)  latinIso3  nonLatinIso3  autoIso3  quick(0/1)  text(hex code points, space sep)
// argv[1]: Kotlin IsoCodes.toIso3 answers, "code=iso3" per line (a code not listed answers null)
// stdout: id  mix=<iso3 per run, | separated, each "iso3:chunkCps">  auto=<iso3>  chunks=<n>
#include "tts_engine_core.cpp"
#include <cstdio>
#include <iostream>
#include <sstream>
#include <map>
#include <fstream>
static std::vector<std::string> split(const std::string& s, char d){ std::vector<std::string> o; std::string c; std::stringstream ss(s); while(std::getline(ss,c,d)) o.push_back(c); return o; }
static std::vector<jchar> u16(const std::string& s){ std::vector<jchar> o; int at=0; while(at<(int)s.size()){int l; int cp=utf8ToCodepoint((const unsigned char*)s.data()+at,l); if(l<=0)l=1; at+=l; if(cp>=0x10000){cp-=0x10000;o.push_back(0xD800+(cp>>10));o.push_back(0xDC00+(cp&0x3FF));} else o.push_back((jchar)cp);} return o; }
static int u16len(const std::string& s){ return (int)u16(s).size(); }
static std::string hexOf(const std::string& s){ std::string o; int at=0; char b[16]; while(at<(int)s.size()){int l; int cp=utf8ToCodepoint((const unsigned char*)s.data()+at,l); if(l<=0)l=1; at+=l; snprintf(b,sizeof b,"%s%X",o.empty()?"":" ",cp); o+=b;} return o; }
int main(int argc, char** argv){
    std::string line; std::string lastEnabled;
    std::unordered_set<std::string> okIso3;
    std::unordered_map<std::string,std::string> kt;
    { std::ifstream in(argv[1]); std::string l; while(std::getline(in,l)){ auto e=l.find('='); if(e==std::string::npos) continue; kt[l.substr(0,e)]=l.substr(e+1); if(e==2) g_iso2to3[l.substr(0,e)]=l.substr(e+1); } }
    while(std::getline(std::cin, line)){
        auto f = split(line, '\t'); if(f.size() < 8) continue;
        std::string id=f[0], latin=f[3], nonLatin=f[4], autoL=f[5]; bool quick = f[6]=="1";
        if(f[1]+f[2] != lastEnabled){
            lastEnabled = f[1]+f[2];
            std::vector<std::string> en = split(f[1], ',');
            okIso3.clear();
            for(auto& e : split(f[2], ',')) okIso3.insert(e);
            std::string j; for(size_t i=0;i<en.size();i++){ if(i) j+=","; j+=en[i]; }
            auto st=std::make_shared<HintState>(); st->list=j; st->codes=en; rebuildScriptLanguageTables(*st); g_hintState=st;
            detectOkIso3Set = std::make_shared<const std::unordered_set<std::string>>(okIso3);
            enabledLangSet = std::make_shared<const std::unordered_set<std::string>>(std::unordered_set<std::string>(en.begin(), en.end()));
        }
        std::string text; for(auto& h : split(f[7], ' ')) if(!h.empty()) appendCodepointUtf8(text, (int)strtol(h.c_str(), nullptr, 16));
        // Kotlin IsoCodes.toIso3: a known code -> its iso3, else null (3-letter codes kept only if known)
        auto ktIso3 = [&](const std::string& c) -> std::string {
            std::string lc = c; for(char& ch : lc) if(ch>='A'&&ch<='Z') ch+=32;
            lc = lc.substr(0, lc.find_first_of("-_"));
            auto it = kt.find(lc); return it == kt.end() ? std::string() : it->second;
        };
        auto resolve = [&](const std::string& lang, bool isLatin) -> std::string {
            std::string byScript = isLatin ? latin : nonLatin;
            std::string r = ktIso3(lang); if(r.empty()) r = byScript;
            if(!okIso3.count(r)) r = byScript;
            return r;
        };
        // ---- mix mode
        ReadingModes modes;
        auto chunks = buildMixChunks(std::vector<std::string>{text}, latin, nonLatin, modes, latin, 1, false);
        std::string mix;
        for(auto& c : chunks){
            if(!(c.type>=0 && c.type<=2) || c.text.empty()){ mix += (mix.empty()?"":"|") + std::string("T") + std::to_string(c.type) + ":" + hexOf(c.text); continue; }
            auto fl = foldAndSpansFlat(c.text, quick, true, false);
            const std::string& folded = fl[0];
            if(quick && u16len(folded)==1 && uniIn(kUniLatinCommonInherited, firstCodepointOf(folded))){
                mix += (mix.empty()?"":"|") + resolve("un", true) + ":" + hexOf(c.text); continue;
            }
            for(size_t k=1;k+2<fl.size();k+=3) mix += (mix.empty()?"":"|") + resolve(fl[k], fl[k+1]=="1") + ":" + hexOf(fl[k+2]);
        }
        // ---- auto mode (detectWindowThenAggregate + the auto branch)
        std::string det;
        auto units = u16(text);
        auto aggregate = [&](const std::vector<std::string>& fl) -> std::string {
            if(quick && u16len(fl[0])==1 && uniIn(kUniLatinCommonInherited, firstCodepointOf(fl[0]))) return "un";
            int size = (int)fl.size()-1; if(size < 3) return "un"; if(size == 3) return fl[1];
            std::map<std::string,int> tot; for(size_t k=1;k+2<fl.size();k+=3) tot[fl[k]+"|"+fl[k+1]] += u16len(fl[k+2]);
            std::string bestAny, bestReal; int a=-1,r=-1; for(auto& kv:tot){ if(kv.second>a){a=kv.second;bestAny=kv.first;} if(kv.first.rfind("un|",0)==0) continue; if(kv.second>r){r=kv.second;bestReal=kv.first;} }
            std::string w = !bestReal.empty()?bestReal:bestAny; return w.substr(0,w.find('|'));
        };
        if(text.empty()) det = "un";
        else if(units.size()==1 && quick && uniIn(kUniLatinCommonInherited, firstCodepointOf(text))) det = aggregate(foldAndSpansFlat(text, quick));
        else {
            std::string w = detectWindowed(units.data(), (int)units.size(), latin, nonLatin, false, nullptr);
            std::string wl = w; for(char& ch: wl) ch = tolower(ch);
            det = wl != "unknown" ? w : aggregate(foldAndSpansFlat(text, quick));
        }
        if(det.size() > 2) det = det.substr(0,2);
        std::string autoR = ktIso3(det); if(autoR.empty()) autoR = autoL; if(!okIso3.count(autoR)) autoR = autoL;
        printf("%s\tmix=%s\tauto=%s(cld:%s)\tchunks=%zu\n", id.c_str(), mix.c_str(), autoR.c_str(), det.c_str(), chunks.size());
    }
}
