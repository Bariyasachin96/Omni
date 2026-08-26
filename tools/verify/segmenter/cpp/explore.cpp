#include <string>
#include <cstring>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stdint.h>
#include <mutex>
#include <iostream>
#include "core.inc"

static const char* TYPE[] = {"0-neutral","1-latin","2-nonlatin","3-number","4-punct","5-emoji"};
static void run(const char* label, const std::string& text,
                int numMode, int puncMode, int emojiMode,
                const char* latin, const char* nonLatin,
                bool punctInFlow, bool smartNum, int groupSize,
                const char* neutralDefault, int neutralType, bool dual){
    ReadingModes m;
    m.numberMode=numMode; m.numberSpecific="NUMSPEC";
    m.punctuationMode=puncMode; m.punctuationSpecific="PUNCSPEC";
    m.emojiMode=emojiMode; m.emojiSpecific="EMOJISPEC";
    m.punctuationInFlow=punctInFlow; m.smartNumber=smartNum; m.smartNumberGroupSize=groupSize;
    std::vector<std::string> sentences{text};
    auto chunks = buildMixChunks(sentences, latin, nonLatin, m, neutralDefault, neutralType, false, dual);
    printf("\n%s\n  in : %s\n", label, text.c_str());
    for(size_t i=0;i<chunks.size();i++){
        const char* t = (chunks[i].type>=0 && chunks[i].type<=5) ? TYPE[chunks[i].type] : "?";
        printf("  [%zu] type=%-11s kind=%d lang=%-10s text='%s'\n", i, t, chunks[i].kind,
               chunks[i].lang.c_str(), chunks[i].text.c_str());
    }
}
int main(){
    const char* HI = "\xE0\xA4\xA8\xE0\xA4\xAE\xE0\xA4\xB8\xE0\xA5\x8D\xE0\xA4\xA4\xE0\xA5\x87";     // नमस्ते
    const char* DANDA = "\xE0\xA5\xA4";                                                              // ।
    const char* EMOJI = "\xF0\x9F\x98\x80";                                                          // 😀
    setLanguageHintsForTest("en,hi");
    printf("======== defaults: number/punct/emoji mode = 0 (Auto language), punct in flow ========\n");
    run("Latin only",              "Hello world",                    0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("non-Latin only",          HI,                               0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("Latin + non-Latin",       std::string("Hello ")+HI,         0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("with a number",           std::string("Hello 12345 ")+HI,   0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("with punctuation",        "Hello, world!",                  0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("with emoji",              std::string("Hello ")+EMOJI+" world", 0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("Hindi + danda + Latin",   std::string(HI)+DANDA+" Hello",   0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("number ALONE",            "12345",                          0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("punctuation ALONE",       "!!!",                            0,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("emoji ALONE",             EMOJI,                            0,0,0,"eng","hin",true,false,1,"eng",1,false);
    printf("\n======== mode ints = 3 (Specific language) ========\n");
    run("number specific",         std::string("Hello 12345 ")+HI,   3,3,3,"eng","hin",true,false,1,"eng",1,false);
    run("punct specific",          "Hello, world!",                  3,3,3,"eng","hin",true,false,1,"eng",1,false);
    run("emoji specific",          std::string("Hi ")+EMOJI,         3,3,3,"eng","hin",true,false,1,"eng",1,false);
    printf("\n======== mode ints = 1 (Primary) and 2 (Secondary) ========\n");
    run("number -> Primary",       std::string("Hello 12345 ")+HI,   1,0,0,"eng","hin",true,false,1,"eng",1,false);
    run("number -> Secondary",     std::string("Hello 12345 ")+HI,   2,0,0,"eng","hin",true,false,1,"eng",1,false);
    printf("\n======== punctuation NOT in flow (mode != 0) ========\n");
    run("punct apart",             "Hello, world!",                  0,1,0,"eng","hin",false,false,1,"eng",1,false);
    printf("\n======== dual mode ========\n");
    run("dual: Latin + Hindi",     std::string("Hello ")+HI,         0,0,0,"eng","hin",true,false,1,"eng",1,true);
    run("dual: with number",       std::string("Hello 12345 ")+HI,   0,0,0,"eng","hin",true,false,1,"eng",1,true);
    printf("\n======== smart number reading, group sizes ========\n");
    run("phone, group 1",          "Call 9876543210 now",            0,0,0,"eng","hin",true,true,1,"eng",1,false);
    run("phone, group 2",          "Call 9876543210 now",            0,0,0,"eng","hin",true,true,2,"eng",1,false);
    run("phone, group 3",          "Call 9876543210 now",            0,0,0,"eng","hin",true,true,3,"eng",1,false);
    run("clock time",              "Meeting at 10:30 today",         0,0,0,"eng","hin",true,true,1,"eng",1,false);
    printf("\n======== fancy Unicode ========\n");
    run("maths bold",              "\xF0\x9D\x90\x87\xF0\x9D\x90\x9E\xF0\x9D\x90\xA5\xF0\x9D\x90\xA5\xF0\x9D\x90\xA8 world", 0,0,0,"eng","hin",true,false,1,"eng",1,false);
    return 0;
}
