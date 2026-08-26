#include <cstdio>
#include <string>
inline int utf8ToCodepoint(const unsigned char* utf8, int& len) {
    if (utf8[0] < 0x80)               { len=1; return utf8[0]; }
    if ((utf8[0]&0xE0)==0xC0)         { len=2; return ((utf8[0]&0x1F)<<6)|(utf8[1]&0x3F); }
    if ((utf8[0]&0xF0)==0xE0)         { len=3; return ((utf8[0]&0x0F)<<12)|((utf8[1]&0x3F)<<6)|(utf8[2]&0x3F); }
    if ((utf8[0]&0xF8)==0xF0)         { len=4; return ((utf8[0]&0x07)<<18)|((utf8[1]&0x3F)<<12)|((utf8[2]&0x3F)<<6)|(utf8[3]&0x3F); }
    len=1; return utf8[0];
}
// ---------------------------------------------------------------------------
// AutoTTS 5.7.7.26 clsCLD2.a(int) / clsCLD2.b(String): decorated Latin letters
// are folded back to plain ASCII before any language detection runs. Without
// this a whole sentence written in maths-bold or script Unicode -- which is
// ordinary on social media -- comes back UNKNOWN and falls through to the
// preferred language.
//
// normalizeFancyCodepoint is a mechanical transcription of a(int). It was
// verified rather than eyeballed: a(int) was extracted, compiled with javac,
// swept over all 1,114,112 code points, and the 1,062 mappings it produces were
// diffed against the same sweep of this function. Identical.
static int normalizeFancyCodepoint(int n3) {
        if (n3 >= 119808 && n3 <= 120483) {
            if ((n3 = (n3 - 119808) % 52) >= 26) return n3 + 71;
            return n3 + 65;
        }
        if (n3 == 120484) {
            return 105;
        }
        if (n3 == 120485) {
            return 106;
        }
        if (n3 >= 120782 && n3 <= 120831) {
            return (n3 - 120782) % 10 + 48;
        }
        if (n3 >= 65296 && n3 <= 65305) {
            return n3 - 65248;
        }
        if (n3 >= 65313 && n3 <= 65338) {
            return n3 - 65248;
        }
        if (n3 >= 65345 && n3 <= 65370) {
            return n3 - 65248;
        }
        if (n3 == 8450) return 67;
        if (n3 == 8469) return 78;
        if (n3 == 8484 || n3 == 8488) return 90;
        if (n3 == 8492) return 66;
        if (n3 == 8493) return 67;
        if (n3 == 8499) return 77;
        if (n3 == 8500) return 111;
        switch (n3) {
            default: {
                switch (n3) {
                    default: {
                        switch (n3) {
                            default: {
                                switch (n3) {
                                    default: {
                                        if (n3 >= 9398 && n3 <= 9423) {
                                            return n3 - 9333;
                                        }
                                        if (n3 >= 9424 && n3 <= 9449) {
                                            return n3 - 9327;
                                        }
                                        if (n3 == 9450) {
                                            return 48;
                                        }
                                        if (n3 >= 9312 && n3 <= 9320) {
                                            return n3 - 9263;
                                        }
                                        if (n3 >= 9372 && n3 <= 9397) {
                                            return n3 - 9275;
                                        }
                                        if (n3 >= 127312 && n3 <= 127337) {
                                            return n3 - 127247;
                                        }
                                        if (n3 >= 127344 && n3 <= 127369) {
                                            return n3 - 127279;
                                        }
                                        if (n3 >= 127280 && n3 <= 127305) {
                                            return n3 - 127215;
                                        }
                                        switch (n3) {
                                            default: {
                                                if (n3 == 688) return 104;
                                                if (n3 == 7491) return 97;
                                                if (n3 == 7501) return 103;
                                                if (n3 == 7506) return 111;
                                                if (n3 == 7515) return 118;
                                                if (n3 == 7580) return 99;
                                                if (n3 == 7584) return 102;
                                                if (n3 == 7611) return 122;
                                                if (n3 == 8305) return 105;
                                                if (n3 == 8319) return 110;
                                                if (n3 == 690) return 106;
                                                if (n3 == 691) return 114;
                                                if (n3 == 695) return 119;
                                                if (n3 == 696) return 121;
                                                if (n3 == 7503) return 107;
                                                if (n3 == 7504) return 109;
                                                switch (n3) {
                                                    default: {
                                                        switch (n3) {
                                                            default: {
                                                                switch (n3) {
                                                                    default: {
                                                                        if (n3 == 8304) {
                                                                            return 48;
                                                                        }
                                                                        if (n3 == 185) {
                                                                            return 49;
                                                                        }
                                                                        if (n3 == 178) {
                                                                            return 50;
                                                                        }
                                                                        if (n3 == 179) {
                                                                            return 51;
                                                                        }
                                                                        if (n3 >= 8308 && n3 <= 8313) {
                                                                            return n3 - 8256;
                                                                        }
                                                                        if (n3 >= 8320 && n3 <= 8329) {
                                                                            return n3 - 8272;
                                                                        }
                                                                        switch (n3) {
                                                                            default: {
                                                                                return n3;
                                                                            }
                                                                            case 8348: {
                                                                                return 116;
                                                                            }
                                                                            case 8347: {
                                                                                return 115;
                                                                            }
                                                                            case 8346: {
                                                                                return 112;
                                                                            }
                                                                            case 8345: {
                                                                                return 110;
                                                                            }
                                                                            case 8344: {
                                                                                return 109;
                                                                            }
                                                                            case 8343: {
                                                                                return 108;
                                                                            }
                                                                            case 8342: {
                                                                                return 107;
                                                                            }
                                                                            case 8341: {
                                                                                return 104;
                                                                            }
                                                                            case 8339: {
                                                                                return 120;
                                                                            }
                                                                            case 8338: {
                                                                                return 111;
                                                                            }
                                                                            case 8337: {
                                                                                return 101;
                                                                            }
                                                                            case 8336: 
                                                                                break;
                                                                        }
                                                                        return 97;
                                                                    }
                                                                    case 7512: {
                                                                        return 117;
                                                                    }
                                                                    case 7511: {
                                                                        return 116;
                                                                    }
                                                                    case 7510: 
                                                                        break;
                                                                }
                                                                return 112;
                                                            }
                                                            case 7497: {
                                                                return 101;
                                                            }
                                                            case 7496: {
                                                                return 100;
                                                            }
                                                            case 7495: 
                                                                break;
                                                        }
                                                        return 98;
                                                    }
                                                    case 739: {
                                                        return 120;
                                                    }
                                                    case 738: {
                                                        return 115;
                                                    }
                                                    case 737: 
                                                        break;
                                                }
                                                return 108;
                                            }
                                            case 42801: {
                                                return 83;
                                            }
                                            case 42800: {
                                                return 70;
                                            }
                                            case 7458: {
                                                return 90;
                                            }
                                            case 7457: {
                                                return 87;
                                            }
                                            case 7456: {
                                                return 86;
                                            }
                                            case 7452: {
                                                return 85;
                                            }
                                            case 7451: {
                                                return 84;
                                            }
                                            case 7448: {
                                                return 80;
                                            }
                                            case 7439: {
                                                return 79;
                                            }
                                            case 7437: {
                                                return 77;
                                            }
                                            case 7435: {
                                                return 75;
                                            }
                                            case 7434: {
                                                return 74;
                                            }
                                            case 7431: {
                                                return 69;
                                            }
                                            case 7429: {
                                                return 68;
                                            }
                                            case 7428: {
                                                return 67;
                                            }
                                            case 7424: {
                                                return 65;
                                            }
                                            case 671: {
                                                return 76;
                                            }
                                            case 668: {
                                                return 72;
                                            }
                                            case 665: {
                                                return 66;
                                            }
                                            case 655: {
                                                return 89;
                                            }
                                            case 640: {
                                                return 82;
                                            }
                                            case 628: {
                                                return 78;
                                            }
                                            case 618: {
                                                return 73;
                                            }
                                            case 610: 
                                                break;
                                        }
                                        return 71;
                                    }
                                    case 8497: {
                                        return 70;
                                    }
                                    case 8496: {
                                        return 69;
                                    }
                                    case 8495: 
                                        break;
                                }
                                return 101;
                            }
                            case 8475: 
                            case 8476: 
                            case 8477: {
                                return 82;
                            }
                            case 8474: {
                                return 81;
                            }
                            case 8473: 
                                break;
                        }
                        return 80;
                    }
                    case 8467: {
                        return 108;
                    }
                    case 8466: {
                        return 76;
                    }
                    case 8464: 
                    case 8465: 
                        break;
                }
                return 73;
            }
            case 8462: {
                return 104;
            }
            case 8459: 
            case 8460: 
            case 8461: {
                return 72;
            }
            case 8458: 
                break;
        }
        return 103;
    }

static void appendCodepointUtf8(std::string& out, int cp){
    if(cp < 0x80){ out += (char)cp; }
    else if(cp < 0x800){ out += (char)(0xC0|(cp>>6)); out += (char)(0x80|(cp&0x3F)); }
    else if(cp < 0x10000){ out += (char)(0xE0|(cp>>12)); out += (char)(0x80|((cp>>6)&0x3F)); out += (char)(0x80|(cp&0x3F)); }
    else { out += (char)(0xF0|(cp>>18)); out += (char)(0x80|((cp>>12)&0x3F)); out += (char)(0x80|((cp>>6)&0x3F)); out += (char)(0x80|(cp&0x3F)); }
}

// clsCLD2.b's scan, moved from UTF-16 chars to code points. b() tests
// n3 == 55349 and n3 == 55356, which are the lead surrogates 0xD835 and 0xD83C,
// so on code points those become the first two ranges here. Trail surrogates
// are 0xDC00-0xDFFF and match none of the others, so the two forms agree.
static bool isFancyTrigger(int cp){
    return (cp >= 0x1D400 && cp <= 0x1D7FF)      // 55349, maths alphanumerics
        || (cp >= 0x1F000 && cp <= 0x1F3FF)      // 55356, squared / regional
        || (cp >= 0x2102  && cp <= 0x2134)       // 8450..8500
        || (cp >= 0xFF10  && cp <= 0xFF5A)       // 65296..65370
        || (cp >= 0x2460  && cp <= 0x24EA)       // 9312..9450
        || (cp >= 0x1D00  && cp <= 0x1DBB)       // 7424..7611
        || (cp >= 0x262   && cp <= 0x2E3)        // 610..739
        || (cp >= 0x2070  && cp <= 0x209C)       // 8304..8348
        || cp == 0xB9 || cp == 0xB2 || cp == 0xB3          // 185, 178, 179
        || cp == 0xA730 || cp == 0xA731;                   // 42800, 42801
}

static std::string normalizeFancyText(const std::string& text){
    bool found = false;
    for(int at = 0; at < (int)text.size(); ){
        int cpLen = 0;
        int cp = utf8ToCodepoint((const unsigned char*)text.data() + at, cpLen);
        if(cpLen <= 0) cpLen = 1;
        if(isFancyTrigger(cp)){ found = true; break; }
        at += cpLen;
    }
    // The fast path matters: b() returns the ORIGINAL string when there is
    // nothing to fold, so ordinary text is never rebuilt.
    if(!found) return text;
    std::string out;
    out.reserve(text.size());
    for(int at = 0; at < (int)text.size(); ){
        int cpLen = 0;
        int cp = utf8ToCodepoint((const unsigned char*)text.data() + at, cpLen);
        if(cpLen <= 0) cpLen = 1;
        appendCodepointUtf8(out, normalizeFancyCodepoint(cp));
        at += cpLen;
    }
    return out;
}
// ---------------------------------------------------------------------------


int main(){
  const char* cases[] = {
    "\xF0\x9D\x90\x87\xF0\x9D\x90\x9E\xF0\x9D\x90\xA5\xF0\x9D\x90\xA5\xF0\x9D\x90\xA8",
    "\xF0\x9D\x93\x97\xF0\x9D\x93\xB2",
    "\xEF\xBC\xB4\xEF\xBD\x85\xEF\xBD\x93\xEF\xBD\x94",
    "\xE2\x84\x82\xE2\x84\x95\xE2\x84\xA4",
    "\xE2\x91\xA0\xE2\x91\xA1\xE2\x93\x90",
    "plain english text",
    "\xE0\xA4\xA8\xE0\xA4\xAE\xE0\xA4\xB8\xE0\xA5\x8D\xE0\xA4\xA4\xE0\xA5\x87\xE0\xA5\xA4",
    "\xC2\xB9\xC2\xB2\xC2\xB3"
  };
  const char* names[] = {"maths bold Hello","script Hi","fullwidth Test","blackboard CNZ","circled 12a","plain ascii","Hindi + danda","superscript 123"};
  for(int i=0;i<8;i++){
    std::string out = normalizeFancyText(cases[i]);
    printf("%-18s %-34s -> %s%s\n", names[i], cases[i], out.c_str(), out==cases[i]?"   (unchanged)":"");
  }
  printf("\ndanda U+0964 fancy-trigger? %s\n", isFancyTrigger(0x964)?"yes":"no");
  return 0;
}
