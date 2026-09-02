
#include <jni.h>
#include <string>
#include <cstring>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stdint.h>
#include <mutex>
#include "compact_lang_det.h"
#include "encodings.h"
#include "compact_lang_det_impl.h"
#include "nnet_language_identifier.h"
JNIEXPORT jint JNI_OnLoad(JavaVM*, void*) {
    return JNI_VERSION_1_6;
}

// ==========================================================================
//  UTF-8 AND UTF-16 PRIMITIVES
//  Decoding, encoding, and the UTF-16 length helpers that exist so the 24- and
//  48-character context windows count the same units Java counts.
// ==========================================================================
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

// ==========================================================================
//  UNICODE NORMALISER          AutoTTS clsCLD2.a(int) / clsCLD2.b(String)
//  PROVEN: tools/verify/normalizer/run.sh -- 1,062 mappings over every code
//  point. If this is ever touched, re-run that. Do not hand-check it.
// ==========================================================================
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

// ==========================================================================
//  EMOJI
// ==========================================================================
inline bool isEmoji(int codePoint) {
    return codePoint==0x00A9||codePoint==0x00AE||codePoint==0x203C||codePoint==0x2049||codePoint==0x2122||codePoint==0x2139||
           (codePoint>=0x2194&&codePoint<=0x2199)||(codePoint>=0x21A9&&codePoint<=0x21AA)||
           codePoint==0x231A||codePoint==0x231B||codePoint==0x2328||codePoint==0x23CF||(codePoint>=0x23E9&&codePoint<=0x23F3)||(codePoint>=0x23F8&&codePoint<=0x23FA)||
           codePoint==0x24C2||codePoint==0x25AA||codePoint==0x25AB||codePoint==0x25B6||codePoint==0x25C0||(codePoint>=0x25FB&&codePoint<=0x25FE)||
           (codePoint>=0x2600&&codePoint<=0x26FF)||(codePoint>=0x2700&&codePoint<=0x27BF)||codePoint==0x2934||codePoint==0x2935||
           (codePoint>=0x2B05&&codePoint<=0x2B07)||codePoint==0x2B1B||codePoint==0x2B1C||codePoint==0x2B50||codePoint==0x2B55||
           codePoint==0x3030||codePoint==0x303D||codePoint==0x3297||codePoint==0x3299||codePoint==0x20E3||codePoint==0xFE0F||
           codePoint==0x1F004||codePoint==0x1F0CF||
           codePoint==0x1F170||codePoint==0x1F171||codePoint==0x1F17E||codePoint==0x1F17F||codePoint==0x1F18E||(codePoint>=0x1F191&&codePoint<=0x1F19A)||
           (codePoint>=0x1F1E6&&codePoint<=0x1F1FF)||
           codePoint==0x1F201||codePoint==0x1F202||codePoint==0x1F21A||codePoint==0x1F22F||(codePoint>=0x1F232&&codePoint<=0x1F23A)||codePoint==0x1F250||codePoint==0x1F251||
           (codePoint>=0x1F300&&codePoint<=0x1F5FF)||(codePoint>=0x1F600&&codePoint<=0x1F64F)||(codePoint>=0x1F680&&codePoint<=0x1F6FF)||(codePoint>=0x1F900&&codePoint<=0x1F9FF);
}

// ==========================================================================
//  SHARED STATE AND FORWARD DECLARATIONS
// ==========================================================================
struct ChunkResult { std::string lang; std::string text; int type; int kind; };
static std::mutex detectSetMutex;
static std::unordered_set<std::string> detectOkIso3Set;
static std::unordered_set<std::string> enabledLangSet;
// activeSmartNumberKeywords calls this 1,288 lines before it is defined.
static std::string currentLanguageHints();

// ==========================================================================
//  SMART NUMBER READING        AutoTTS d0.i / d0.a / d0.e / d0.p / d0.m / d0.r / d0.s
//  The keyword table is cached for the life of the process and never rebuilt,
//  exactly as AutoTTS caches it in d0.j. That is deliberate; see INVARIANTS.
// ==========================================================================
static const char* const smartNumberKeywords[][20] = {
    {"*", "otp", "pin", "sms", "imei", "cvv", "tel", "fax", "hotline", "sim", "whatsapp", "zalo", "viber", "telegram", nullptr},
    {"en", "phone", "mobile", "cell", "call", "dial", "code", "extension", "ext", "passcode", "verification", "account", "contact", nullptr},
    {"vi", "điện thoại", "sđt", "gọi", "di động", "mã", "liên hệ", "tổng đài", "tài khoản", "cccd", "cmnd", nullptr},
    {"hi", "फ़ोन", "फोन", "मोबाइल", "कॉल", "कोड", "खाता", "संपर्क", "डायल", nullptr},
    {"ur", "فون", "موبائل", "کال", "نمبر", "کوڈ", "رابطہ", "اکاؤنٹ", nullptr},
    {"fa", "تلفن", "موبایل", "تماس", "شماره", "کد", "همراه", "حساب", nullptr},
    {"ar", "هاتف", "جوال", "اتصل", "اتصال", "رقم", "رمز", "كود", "حساب", "موبايل", nullptr},
    {"ru", "телефон", "моб", "мобильный", "звони", "позвони", "код", "номер", "счёт", "счет", "добавочный", nullptr},
    {"uk", "телефон", "моб", "дзвони", "зателефонуй", "код", "номер", "рахунок", nullptr},
    {"de", "telefon", "handy", "anruf", "anrufen", "durchwahl", "code", "konto", "mobil", "rufnummer", "vorwahl", nullptr},
    {"fr", "téléphone", "tél", "portable", "appel", "appelez", "code", "poste", "compte", "mobile", "numéro", nullptr},
    {"es", "teléfono", "móvil", "celular", "llama", "llamar", "código", "cuenta", "extensión", "número", nullptr},
    {"pt", "telefone", "celular", "telemóvel", "ligar", "ligue", "código", "conta", "ramal", "número", nullptr},
    {"it", "telefono", "cellulare", "chiama", "chiamare", "codice", "conto", "interno", "numero", nullptr},
    {"tr", "telefon", "cep", "ara", "arayın", "kod", "dahili", "hesap", "numara", nullptr},
    {"in", "telepon", "ponsel", "hp", "hubungi", "kode", "rekening", "nomor", "seluler", nullptr},
    {"id", "telepon", "ponsel", "hp", "hubungi", "kode", "rekening", "nomor", "seluler", nullptr},
    {"ms", "telefon", "bimbit", "hubungi", "kod", "akaun", "nombor", "talian", nullptr},
    {"jv", "telpon", "telpun", "nomer", "kode", "hubungi", nullptr},
    {"th", "โทรศัพท์", "โทร", "มือถือ", "รหัส", "เบอร์", "บัญชี", "ติดต่อ", "สายด่วน", nullptr},
    {"pl", "telefon", "komórka", "zadzwoń", "kod", "numer", "konto", "wewnętrzny", nullptr},
    {"ne", "फोन", "मोबाइल", "कल", "नम्बर", "कोड", "खाता", "सम्पर्क", nullptr},
    {"af", "telefoon", "foon", "selfoon", "skakel", "kode", "rekening", "nommer", nullptr},
    {"ja", "電話", "携帯", "コード", "番号", "内線", "口座", "連絡", "ダイヤル", nullptr},
    {"ko", "전화", "휴대폰", "핸드폰", "코드", "번호", "내선", "계좌", "연락", "문자", nullptr},
    {"zh", "电话", "電話", "手机", "手機", "号码", "號碼", "代码", "代碼", "验证码", "驗證碼", "分机", "分機", "账号", "帳號", "拨打", "撥打", "热线", "熱線", nullptr},
    {"nl", "telefoon", "bel", "bellen", "code", "mobiel", "nummer", "rekening", "toestel", nullptr},
    {"sv", "telefon", "ring", "mobil", "kod", "nummer", "konto", "anknytning", nullptr},
    {"da", "telefon", "ring", "mobil", "kode", "nummer", "konto", nullptr},
    {"no", "telefon", "ring", "mobil", "kode", "nummer", "konto", nullptr},
    {"fi", "puhelin", "soita", "kännykkä", "koodi", "numero", "tili", nullptr},
    {"cs", "telefon", "zavolej", "mobil", "kód", "číslo", "účet", "linka", nullptr},
    {"sk", "telefón", "zavolaj", "mobil", "kód", "číslo", "účet", "linka", nullptr},
    {"hu", "telefon", "hívj", "mobil", "kód", "szám", "számla", "mellék", nullptr},
    {"ro", "telefon", "sună", "mobil", "cod", "număr", "cont", "interior", nullptr},
    {"bg", "телефон", "обади", "мобилен", "код", "номер", "сметка", nullptr},
    {"el", "τηλέφωνο", "τηλ", "κινητό", "κωδικός", "αριθμός", "λογαριασμός", nullptr},
    {"iw", "טלפון", "נייד", "חייג", "קוד", "מספר", "חשבון", "שלוחה", nullptr},
    {"he", "טלפון", "נייד", "חייג", "קוד", "מספר", "חשבון", "שלוחה", nullptr},
    {"bn", "ফোন", "মোবাইল", "কল", "নম্বর", "কোড", "হিসাব", "যোগাযোগ", nullptr},
    {"ta", "தொலைபேசி", "கைபேசி", "அழை", "எண்", "குறியீடு", "கணக்கு", nullptr},
    {"te", "ఫోన్", "మొబైల్", "కాల్", "నంబర్", "కోడ్", "ఖాతా", nullptr},
    {"kn", "ಫೋನ್", "ಮೊಬೈಲ್", "ಕರೆ", "ಸಂಖ್ಯೆ", "ಕೋಡ್", "ಖಾತೆ", nullptr},
    {"ml", "ഫോൺ", "മൊബൈൽ", "വിളിക്കുക", "നമ്പർ", "കോഡ്", "അക്കൗണ്ട്", nullptr},
    {"mr", "फोन", "मोबाईल", "कॉल", "क्रमांक", "कोड", "खाते", "संपर्क", nullptr},
    {"gu", "ફોન", "મોબાઇલ", "કૉલ", "નંબર", "કોડ", "ખાતું", nullptr},
    {"pa", "ਫੋਨ", "ਮੋਬਾਈਲ", "ਕਾਲ", "ਨੰਬਰ", "ਕੋਡ", "ਖਾਤਾ", nullptr},
    {"si", "දුරකථන", "ජංගම", "අමතන්න", "අංකය", "කේතය", "ගිණුම", nullptr},
    {"km", "ទូរស័ព្ទ", "លេខ", "កូដ", "ទំនាក់ទំនង", "គណនី", nullptr},
    {"my", "ဖုန်း", "မိုဘိုင်း", "နံပါတ်", "ကုဒ်", "အကောင့်", "ဆက်သွယ်", nullptr},
    {"lo", "ໂທລະສັບ", "ເບີ", "ລະຫັດ", "ບັນຊີ", "ຕິດຕໍ່", nullptr},
    {"sw", "simu", "piga", "namba", "nambari", "msimbo", "akaunti", nullptr},
    {"tl", "telepono", "tawag", "tumawag", "numero", nullptr},
};
static const int smartNumberKeywordRows = 53;
static std::mutex smartNumberMutex;
static std::vector<std::string> smartNumberActive;
static bool smartNumberCacheValid = false;
static std::vector<std::string> activeSmartNumberKeywords(){
    std::string hints = currentLanguageHints();
    std::lock_guard<std::mutex> lock(smartNumberMutex);
    if(smartNumberCacheValid) return smartNumberActive;
    std::unordered_set<std::string> enabled;
    size_t from = 0;
    while(from <= hints.size()){
        size_t at = hints.find(',', from);
        std::string one = hints.substr(from, at == std::string::npos ? std::string::npos : at - from);
        if(!one.empty()) enabled.insert(one);
        if(at == std::string::npos) break;
        from = at + 1;
    }
    std::vector<std::string> collected;
    for(int row = 0; row < smartNumberKeywordRows; row++){
        std::string tag = smartNumberKeywords[row][0];
        if(tag != "*" && enabled.find(tag) == enabled.end()) continue;
        for(int col = 1; smartNumberKeywords[row][col]; col++) collected.push_back(smartNumberKeywords[row][col]);
    }
    smartNumberActive = collected;
    smartNumberCacheValid = true;
    return collected;
}
static bool isNonLetterInsideLetterBlock(int codePoint){
    if(codePoint >= 0x0300 && codePoint <= 0x036F) return true;
    if(codePoint == 0x0374 || codePoint == 0x0375 || codePoint == 0x037E || codePoint == 0x0384 ||
       codePoint == 0x0385 || codePoint == 0x0387) return true;
    if(codePoint >= 0x0483 && codePoint <= 0x0489) return true;
    if(codePoint >= 0x0591 && codePoint <= 0x05C7) return true;
    if(codePoint >= 0x0610 && codePoint <= 0x061A) return true;
    if(codePoint >= 0x064B && codePoint <= 0x065F) return true;
    if(codePoint >= 0x0660 && codePoint <= 0x0669) return true;
    if(codePoint == 0x0670) return true;
    if(codePoint >= 0x06D6 && codePoint <= 0x06ED) return true;
    if(codePoint >= 0x06F0 && codePoint <= 0x06F9) return true;
    if(codePoint >= 0x0900 && codePoint <= 0x0903) return true;
    if(codePoint >= 0x093A && codePoint <= 0x094F) return true;
    if(codePoint >= 0x0951 && codePoint <= 0x0957) return true;
    if(codePoint >= 0x0962 && codePoint <= 0x096F) return true;
    if(codePoint >= 0x0981 && codePoint <= 0x0983) return true;
    if(codePoint >= 0x09BC && codePoint <= 0x09CD) return true;
    if(codePoint >= 0x09E2 && codePoint <= 0x09EF) return true;
    if(codePoint >= 0x0A01 && codePoint <= 0x0A03) return true;
    if(codePoint >= 0x0A3C && codePoint <= 0x0A4D) return true;
    if(codePoint >= 0x0A66 && codePoint <= 0x0A71) return true;
    if(codePoint >= 0x0A81 && codePoint <= 0x0A83) return true;
    if(codePoint >= 0x0ABC && codePoint <= 0x0ACD) return true;
    if(codePoint >= 0x0AE2 && codePoint <= 0x0AEF) return true;
    if(codePoint >= 0x0B01 && codePoint <= 0x0B03) return true;
    if(codePoint >= 0x0B3C && codePoint <= 0x0B4D) return true;
    if(codePoint >= 0x0B62 && codePoint <= 0x0B6F) return true;
    if(codePoint >= 0x0B82 && codePoint <= 0x0B82) return true;
    if(codePoint >= 0x0BBE && codePoint <= 0x0BCD) return true;
    if(codePoint >= 0x0BE6 && codePoint <= 0x0BEF) return true;
    if(codePoint >= 0x0C00 && codePoint <= 0x0C04) return true;
    if(codePoint >= 0x0C3E && codePoint <= 0x0C56) return true;
    if(codePoint >= 0x0C66 && codePoint <= 0x0C6F) return true;
    if(codePoint >= 0x0C81 && codePoint <= 0x0C83) return true;
    if(codePoint >= 0x0CBC && codePoint <= 0x0CD6) return true;
    if(codePoint >= 0x0CE6 && codePoint <= 0x0CEF) return true;
    if(codePoint >= 0x0D00 && codePoint <= 0x0D03) return true;
    if(codePoint >= 0x0D3B && codePoint <= 0x0D4D) return true;
    if(codePoint >= 0x0D66 && codePoint <= 0x0D6F) return true;
    if(codePoint >= 0x0D81 && codePoint <= 0x0D83) return true;
    if(codePoint >= 0x0DCA && codePoint <= 0x0DDF) return true;
    if(codePoint >= 0x0DE6 && codePoint <= 0x0DEF) return true;
    if(codePoint >= 0x0E31 && codePoint <= 0x0E3A) return true;
    if(codePoint >= 0x0E47 && codePoint <= 0x0E4E) return true;
    if(codePoint >= 0x0E50 && codePoint <= 0x0E5B) return true;
    if(codePoint >= 0x0EB1 && codePoint <= 0x0EBC) return true;
    if(codePoint >= 0x0EC8 && codePoint <= 0x0ED9) return true;
    if(codePoint >= 0x102B && codePoint <= 0x103E) return true;
    if(codePoint >= 0x1040 && codePoint <= 0x104F) return true;
    if(codePoint >= 0x1056 && codePoint <= 0x1059) return true;
    if(codePoint >= 0x17B4 && codePoint <= 0x17D3) return true;
    return false;
}
static bool isLetterCodepoint(int codePoint){
    if(isNonLetterInsideLetterBlock(codePoint)) return false;
    if(codePoint == 0x00AA || codePoint == 0x00B5 || codePoint == 0x00BA) return true;
    return (codePoint>=0x41&&codePoint<=0x5A)||(codePoint>=0x61&&codePoint<=0x7A)||
           (codePoint>=0xC0&&codePoint<=0xD6)||(codePoint>=0xD8&&codePoint<=0xF6)||(codePoint>=0xF8&&codePoint<=0x24F)||
           (codePoint>=0x370&&codePoint<=0x3FF)||(codePoint>=0x400&&codePoint<=0x52F)||
           (codePoint>=0x531&&codePoint<=0x58F)||(codePoint>=0x5D0&&codePoint<=0x5EA)||
           (codePoint>=0x620&&codePoint<=0x64A)||(codePoint>=0x66E&&codePoint<=0x6D3)||
           (codePoint>=0x710&&codePoint<=0x74F)||(codePoint>=0x900&&codePoint<=0x97F)||
           (codePoint>=0x980&&codePoint<=0x9FF)||(codePoint>=0xA00&&codePoint<=0xA7F)||
           (codePoint>=0xA80&&codePoint<=0xAFF)||(codePoint>=0xB00&&codePoint<=0xB7F)||
           (codePoint>=0xB80&&codePoint<=0xBFF)||(codePoint>=0xC00&&codePoint<=0xC7F)||
           (codePoint>=0xC80&&codePoint<=0xCFF)||(codePoint>=0xD00&&codePoint<=0xD7F)||
           (codePoint>=0xD80&&codePoint<=0xDFF)||(codePoint>=0xE01&&codePoint<=0xE5B)||
           (codePoint>=0xE81&&codePoint<=0xEDF)||(codePoint>=0x1000&&codePoint<=0x109F)||
           (codePoint>=0x1780&&codePoint<=0x17D3)||(codePoint>=0x1E00&&codePoint<=0x1EFF)||
           (codePoint>=0x2C60&&codePoint<=0x2C7F)||(codePoint>=0x2E80&&codePoint<=0x9FFF)||
           (codePoint>=0xA720&&codePoint<=0xA7FF)||(codePoint>=0xAC00&&codePoint<=0xD7AF)||
           (codePoint>=0xF900&&codePoint<=0xFAFF);
}
static bool keywordMatchesAnywhere(const std::string& keyword){
    int cpLen = 0;
    int firstCp = utf8ToCodepoint((const unsigned char*)keyword.c_str(), cpLen);
    if(firstCp>=0x0E00 && firstCp<=0x0EFF) return true;
    if(firstCp>=0x1000 && firstCp<=0x109F) return true;
    if(firstCp>=0x1780 && firstCp<=0x17FF) return true;
    if(firstCp>=0x2E80 && firstCp<=0x9FFF) return true;
    if(firstCp>=0xAC00 && firstCp<=0xD7AF) return true;
    if(firstCp>=0xF900 && firstCp<=0xFAFF) return true;
    return false;
}
static int codepointBefore(const std::string& text, size_t at){
    if(at == 0) return -1;
    size_t back = at - 1;
    while(back > 0 && ((unsigned char)text[back] & 0xC0) == 0x80) back--;
    int cpLen = 0;
    return utf8ToCodepoint((const unsigned char*)text.c_str() + back, cpLen);
}
static bool contextHasKeyword(const std::string& text, const std::vector<std::string>& keywords){
    if(text.empty()) return false;
    for(const std::string& keyword : keywords){
        size_t at = text.find(keyword);
        while(at != std::string::npos){
            if(keywordMatchesAnywhere(keyword)) return true;
            int before = codepointBefore(text, at);
            bool openBefore = (before < 0) || !isLetterCodepoint(before);
            size_t after = at + keyword.size();
            bool openAfter = true;
            if(after < text.size()){
                int cpLen = 0;
                openAfter = !isLetterCodepoint(utf8ToCodepoint((const unsigned char*)text.c_str() + after, cpLen));
            }
            if(openBefore && openAfter) return true;
            at = text.find(keyword, at + 1);
        }
    }
    return false;
}
static std::string lowerCaseRoot(const std::string& text){
    std::string out;
    out.reserve(text.size());
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        int lowered = codePoint;
        if(codePoint>=0x41 && codePoint<=0x5A) lowered = codePoint + 0x20;
        else if(codePoint>=0xC0 && codePoint<=0xDE && codePoint!=0xD7) lowered = codePoint + 0x20;
        else if(codePoint>=0x391 && codePoint<=0x3A9 && codePoint!=0x3A2) lowered = codePoint + 0x20;
        else if(codePoint>=0x410 && codePoint<=0x42F) lowered = codePoint + 0x20;
        else if(codePoint>=0x400 && codePoint<=0x40F) lowered = codePoint + 0x50;
        if(lowered < 0x80) out += (char)lowered;
        else if(lowered < 0x800){ out += (char)(0xC0|(lowered>>6)); out += (char)(0x80|(lowered&0x3F)); }
        else if(lowered < 0x10000){ out += (char)(0xE0|(lowered>>12)); out += (char)(0x80|((lowered>>6)&0x3F)); out += (char)(0x80|(lowered&0x3F)); }
        else { out += (char)(0xF0|(lowered>>18)); out += (char)(0x80|((lowered>>12)&0x3F)); out += (char)(0x80|((lowered>>6)&0x3F)); out += (char)(0x80|(lowered&0x3F)); }
        at += cpLen;
    }
    return out;
}
static bool hasEnoughDigits(const std::string& text){
    int digits = 0;
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        if(codePoint>='0' && codePoint<='9') digits++;
        else if(codePoint==',' || codePoint=='.' || codePoint==':' || codePoint=='%' || codePoint=='$') return false;
        else if(codePoint>=0x20A0 && codePoint<=0x20CF) return false;
        at += cpLen;
    }
    return digits >= 4;
}
// AutoTTS 5.7.7.26 c3.d0.b, used with Matcher.matches():
//     Pattern.compile("[0-9]{1,2}:[0-9]{2}(:[0-9]{2})?")
// matches() means the WHOLE string, and the pattern is compiled with no flags
// -- unlike every other pattern in that class, which pass 64 (UNICODE_CASE) --
// so [0-9] is ASCII digits only.
//
// Written as a scan rather than a regex, and the greedy-with-backtracking case
// is why the leading run is counted rather than capped at two: for "123:45" the
// regex tries "12" then "1" for [0-9]{1,2}, fails to find ':' either way, and
// rejects. Counting all leading digits and requiring 1 or 2 rejects it too.
static bool looksLikeClockTime(const std::string& text){
    size_t at = 0;
    size_t digits = 0;
    while(at < text.size() && text[at] >= '0' && text[at] <= '9'){ at++; digits++; }
    if(digits < 1 || digits > 2) return false;
    if(at >= text.size() || text[at] != ':') return false;
    at++;
    digits = 0;
    while(at < text.size() && text[at] >= '0' && text[at] <= '9'){ at++; digits++; }
    if(digits != 2) return false;
    if(at == text.size()) return true;
    if(text[at] != ':') return false;
    at++;
    digits = 0;
    while(at < text.size() && text[at] >= '0' && text[at] <= '9'){ at++; digits++; }
    return digits == 2 && at == text.size();
}
static bool isPhoneShaped(const std::string& raw){
    size_t begin = 0, finish = raw.size();
    while(begin < finish && (unsigned char)raw[begin] <= 0x20) begin++;
    while(finish > begin && (unsigned char)raw[finish-1] <= 0x20) finish--;
    std::string text = raw.substr(begin, finish - begin);
    if(text.empty()) return false;
    // Straight after the empty check, exactly where 5.7.7.26 puts it.
    if(looksLikeClockTime(text)) return false;
    int digits = 0, run = 0, longestRun = 0;
    bool sawComma = false, sawDot = false, sawCurrency = false;
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        if(codePoint>='0' && codePoint<='9'){ digits++; run++; if(run>longestRun) longestRun = run; }
        else if(codePoint==','){ run = 0; sawComma = true; }
        else if(codePoint=='.'){ run = 0; sawDot = true; }
        else if(codePoint=='%' || codePoint=='$' || (codePoint>=0x20A0 && codePoint<=0x20CF)){ run = 0; sawCurrency = true; }
        else run = 0;
        at += cpLen;
    }
    if(digits == 0) return false;
    if(sawCurrency) return false;
    if((text.rfind("+", 0) == 0 || text.rfind("00", 0) == 0) && digits >= 8) return true;
    std::string body = text;
    if(body[0] == '+' || body[0] == '-') body = body.substr(1);
    if(!body.empty() && body[0] == '0' && digits >= 5) return true;
    if(!sawComma && !sawDot){
        if(longestRun >= 7) return true;
        std::vector<std::string> parts;
        std::string current;
        for(char ch : body){
            if(ch==' ' || ch=='\t' || ch=='\n' || ch=='\r' || ch=='-'){ if(!current.empty()){ parts.push_back(current); current.clear(); } }
            else current += ch;
        }
        if(!current.empty()) parts.push_back(current);
        if((int)parts.size() >= 3 && digits >= 7){
            bool grouped = true;
            for(const std::string& part : parts){
                if(part.size() < 2 || part.size() > 4){ grouped = false; break; }
                for(char ch : part) if(ch < '0' || ch > '9'){ grouped = false; break; }
                if(!grouped) break;
            }
            if(grouped) return true;
        }
    }
    return false;
}
// Byte-wise rather than char-wise, which is safe here: the only characters
// that advance the run are ASCII digits, and every byte of a multi-byte UTF-8
// character takes the else branch, where resetting the run to 0 repeatedly is
// the same as resetting it once.
static std::string respaceDigits(const std::string& text, int groupSize){
    int size = groupSize > 1 ? groupSize : 1;      // Math.max(1, AutoTtsService.f0)
    std::string out;
    out.reserve(text.size() * 2);
    int run = 0;
    for(char ch : text){
        if(ch>='0' && ch<='9'){
            if(run > 0 && run % size == 0) out += ' ';
            run++;
        } else {
            run = 0;
        }
        out += ch;
    }
    return out;
}
static bool cpIsNumericCore(int codePoint);
static bool cpIsAsciiPunct(int codePoint);
static bool cpIsPosixPunct(int codePoint);
static bool cpIsGeneralPunct(int codePoint);
static bool cpIsPosixSpace(int codePoint);
static int emojiRunLengthAt(const std::string& text, int offset);
static int wholeSegmentKind(const std::string& text){
    bool allNumber = true, allPunct = true, allEmoji = true, sawDigit = false, sawAny = false;
    bool emojiRun = false;
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        sawAny = true;
        bool posixSpace = cpIsPosixSpace(codePoint);
        bool asciiPunct = cpIsAsciiPunct(codePoint);
        bool generalPunct = cpIsGeneralPunct(codePoint);
        bool currencyish = cpIsNumericCore(codePoint) && !(codePoint >= '0' && codePoint <= '9');
        if(codePoint >= '0' && codePoint <= '9') sawDigit = true;
        else if(!(cpIsPosixPunct(codePoint) || generalPunct || posixSpace || currencyish)) allNumber = false;
        if(!(asciiPunct || generalPunct || posixSpace)) allPunct = false;
        if(allEmoji){
            int emojiLen = emojiRunLengthAt(text, at);
            if(emojiLen <= 0) allEmoji = false;
            else { emojiRun = true; if(emojiLen > cpLen){ at += emojiLen; continue; } }
        }
        at += cpLen;
    }
    if(!sawAny) return 0;
    if(allNumber && sawDigit) return 3;
    if(allPunct) return 4;
    if(allEmoji) return 5;
    return 0;
}

// ==========================================================================
//  CODE-POINT PREDICATES
//  Note two pairs that look mergeable and are not: cpIsAsciiPunct EXCLUDES
//  backslash while isLatinPunctuation includes it, and cpIsJavaSpace and
//  isJavaWhitespace are separate ports of separate AutoTTS sources.
// ==========================================================================
static bool cpIsNumericCore(int codePoint){
    return (codePoint >= '0' && codePoint <= '9') || codePoint == 0x00B0 || codePoint == 0x00D7 ||
           codePoint == 0x00F7 || (codePoint >= 0x20A0 && codePoint <= 0x20CF);
}
static bool cpIsAsciiPunct(int codePoint){
    return (codePoint >= 0x21 && codePoint <= 0x2F) || (codePoint >= 0x3A && codePoint <= 0x40) ||
           codePoint == 0x5B || (codePoint >= 0x5D && codePoint <= 0x60) || (codePoint >= 0x7B && codePoint <= 0x7E);
}
static bool cpIsPosixPunct(int codePoint){ return cpIsAsciiPunct(codePoint) || codePoint == 0x5C; }
static bool cpIsGeneralPunct(int codePoint){ return codePoint >= 0x2000 && codePoint <= 0x206F; }
static bool cpIsPosixSpace(int codePoint){
    return codePoint == 0x20 || (codePoint >= 0x09 && codePoint <= 0x0D);
}
static bool cpIsPunctRun(int codePoint){
    return cpIsGeneralPunct(codePoint) || cpIsAsciiPunct(codePoint) || cpIsPosixSpace(codePoint);
}
static bool cpIsJavaSpace(int codePoint){
    return (codePoint >= 0x09 && codePoint <= 0x0D) || (codePoint >= 0x1C && codePoint <= 0x1F) ||
           codePoint == 0x20 || codePoint == 0x1680 ||
           (codePoint >= 0x2000 && codePoint <= 0x200A && codePoint != 0x2007) ||
           codePoint == 0x205F || codePoint == 0x3000 || codePoint == 0x2028 || codePoint == 0x2029;
}
static bool cpIsEmojiRunChar(int codePoint){ return isEmoji(codePoint) || codePoint == 0x200D; }
static int emojiRunLengthAt(const std::string& text, int offset){
    int len = (int)text.size();
    if(offset >= len) return 0;
    int cpLen = 0;
    int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + offset, cpLen);
    if(codePoint == 0x23 || codePoint == 0x2A || (codePoint >= 0x30 && codePoint <= 0x39)){
        int at = offset + cpLen;
        if(at < len){
            int nextLen = 0;
            int nextCp = utf8ToCodepoint((const unsigned char*)text.c_str() + at, nextLen);
            if(nextCp == 0xFE0F){ at += nextLen; if(at >= len) return 0; nextCp = utf8ToCodepoint((const unsigned char*)text.c_str() + at, nextLen); }
            if(nextCp == 0x20E3) return at + nextLen - offset;
        }
        return 0;
    }
    if(cpIsEmojiRunChar(codePoint)) return cpLen;
    return 0;
}
static bool cpIsLetterOrDigitForNumberEdge(int codePoint){
    return (codePoint >= '0' && codePoint <= '9') || isLetterCodepoint(codePoint);
}
static int codepointAtOffset(const std::string& text, int offset, int& cpLen){
    return utf8ToCodepoint((const unsigned char*)text.c_str() + offset, cpLen);
}
static int codepointBeforeOffset(const std::string& text, int offset){
    if(offset <= 0) return -1;
    int back = offset - 1;
    while(back > 0 && ((unsigned char)text[back] & 0xC0) == 0x80) back--;
    int cpLen = 0;
    return codepointAtOffset(text, back, cpLen);
}
static int utf16LengthOf(const std::string& text){
    int units = 0, at = 0, len = (int)text.size();
    while(at < len){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        units += (codePoint >= 0x10000) ? 2 : 1;
        at += cpLen;
    }
    return units;
}
static std::string lastUtf16Units(const std::string& text, int want){
    int at = (int)text.size(), units = 0;
    while(at > 0 && units < want){
        int back = at - 1;
        while(back > 0 && ((unsigned char)text[back] & 0xC0) == 0x80) back--;
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + back, cpLen);
        int cost = (codePoint >= 0x10000) ? 2 : 1;
        if(units + cost > want) break;
        units += cost;
        at = back;
    }
    return text.substr(at);
}
static std::string firstUtf16Units(const std::string& text, int want){
    int at = 0, units = 0, len = (int)text.size();
    while(at < len && units < want){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        int cost = (codePoint >= 0x10000) ? 2 : 1;
        if(units + cost > want) break;
        units += cost;
        at += cpLen;
    }
    return text.substr(0, at);
}

// ==========================================================================
//  SEGMENT TYPING AND SPLITTING    AutoTTS d0.j / d0.b / d0.c / d0.d
//  Types are 0 whitespace, 1 Latin, 2 non-Latin, 3 number, 4 punctuation,
//  5 emoji -- and the type is what ultimately picks the voice.
// ==========================================================================
static int segmentTypeOf(const std::string& text){
    bool allSpace = true, allPunct = true, allNumber = true, allEmoji = true, sawDigit = false, sawAny = false;
    int at = 0, len = (int)text.size();
    while(at < len){
        int cpLen = 0;
        int codePoint = codepointAtOffset(text, at, cpLen);
        sawAny = true;
        if(!cpIsJavaSpace(codePoint)) allSpace = false;
        if(!cpIsPunctRun(codePoint)) allPunct = false;
        if(codePoint >= '0' && codePoint <= '9') sawDigit = true;
        else if(!(cpIsNumericCore(codePoint) || cpIsGeneralPunct(codePoint) || cpIsPosixPunct(codePoint) || cpIsPosixSpace(codePoint))) allNumber = false;
        if(allEmoji){
            int emojiLen = emojiRunLengthAt(text, at);
            if(emojiLen <= 0) allEmoji = false;
            else if(emojiLen > cpLen){ at += emojiLen; continue; }
        }
        at += cpLen;
    }
    if(!sawAny) return 1;
    if(allSpace) return 0;
    if(allPunct) return 4;
    if(allNumber && sawDigit) return 3;
    if(allEmoji) return 5;
    return 1;
}
struct TextSegment { std::string text; int type; std::string lang; };
static void splitByPunct(const std::string& text, std::vector<TextSegment>& out){
    int at = 0, len = (int)text.size(), prev = 0;
    while(at < len){
        int cpLen = 0;
        int codePoint = codepointAtOffset(text, at, cpLen);
        if(!cpIsPunctRun(codePoint)){ at += cpLen; continue; }
        int runStart = at;
        while(at < len){
            int nextLen = 0;
            int nextCp = codepointAtOffset(text, at, nextLen);
            if(!cpIsPunctRun(nextCp)) break;
            at += nextLen;
        }
        if(runStart != prev){
            std::string piece = text.substr(prev, runStart - prev);
            out.push_back({piece, segmentTypeOf(piece), ""});
        }
        std::string run = text.substr(runStart, at - runStart);
        out.push_back({run, segmentTypeOf(run), ""});
        prev = at;
    }
    if(prev < len){
        std::string piece = text.substr(prev);
        out.push_back({piece, segmentTypeOf(piece), ""});
    }
}
static bool matchNumberRun(const std::string& text, int start, int& matchEnd){
    int len = (int)text.size();
    int before = codepointBeforeOffset(text, start);
    if(before >= 0 && cpIsLetterOrDigitForNumberEdge(before)) return false;
    int at = start, cpLen = 0;
    int codePoint = codepointAtOffset(text, at, cpLen);
    if(codePoint == '+' || codePoint == '-'){
        at += cpLen;
        if(at >= len) return false;
        codePoint = codepointAtOffset(text, at, cpLen);
    }
    if(!cpIsNumericCore(codePoint)) return false;
    at += cpLen;
    int coreEnd = at, bestEnd = -1, scan = at;
    while(scan < len){
        int scanLen = 0;
        int scanCp = codepointAtOffset(text, scan, scanLen);
        if(!(cpIsNumericCore(scanCp) || cpIsPosixSpace(scanCp) || cpIsAsciiPunct(scanCp))) break;
        scan += scanLen;
        if(cpIsNumericCore(scanCp)) bestEnd = scan;
    }
    int end = bestEnd > 0 ? bestEnd : coreEnd;
    if(end < len){
        int afterLen = 0;
        int afterCp = codepointAtOffset(text, end, afterLen);
        if(cpIsLetterOrDigitForNumberEdge(afterCp)) return false;
    }
    matchEnd = end;
    return true;
}
static void splitByNumber(const std::string& text, std::vector<TextSegment>& out){
    int at = 0, len = (int)text.size(), prev = 0;
    while(at < len){
        int matchEnd = 0;
        if(matchNumberRun(text, at, matchEnd)){
            if(at != prev) splitByPunct(text.substr(prev, at - prev), out);
            out.push_back({text.substr(at, matchEnd - at), 3, ""});
            prev = at = matchEnd;
            continue;
        }
        int cpLen = 0;
        codepointAtOffset(text, at, cpLen);
        at += cpLen;
    }
    if(prev < len) splitByPunct(text.substr(prev), out);
}
static void splitByEmoji(const std::string& text, std::vector<TextSegment>& out){
    int at = 0, len = (int)text.size(), prev = 0;
    while(at < len){
        int cpLen = 0;
        int startLen = emojiRunLengthAt(text, at);
        if(startLen <= 0){ codepointAtOffset(text, at, cpLen); at += cpLen; continue; }
        int runStart = at;
        while(at < len){
            int nextLen = emojiRunLengthAt(text, at);
            if(nextLen <= 0) break;
            at += nextLen;
        }
        if(runStart != prev) out.push_back({text.substr(prev, runStart - prev), 2, ""});
        out.push_back({text.substr(runStart, at - runStart), 5, ""});
        prev = at;
    }
    if(prev < len) out.push_back({text.substr(prev), 2, ""});
}
struct ReadingModes {
    int numberMode = 0; std::string numberSpecific;
    int punctuationMode = 0; std::string punctuationSpecific;
    int emojiMode = 0; std::string emojiSpecific;
    bool punctuationInFlow = true;
    bool smartNumber = false;
    int smartNumberGroupSize = 1;                  // AutoTtsService.f0
};

// ==========================================================================
//  THE SEGMENTER               AutoTTS c3.d0.t
//  The heart of the app: one utterance in, typed chunks out.
//  PROVEN: tools/verify/segmenter/run.sh -- 163,296 cases against AutoTTS.
// ==========================================================================
// ==========================================================================
//  DETECT CONTEXT -- EasyVoice only, and it exists for the CLD3 arm alone.
//
//  CLD3 is a small neural net and it is unreliable on short fragments; CLD2's
//  n-gram tables are not. Measured on the owner's own sentence (2026-09-02),
//  the Hindi tail "वही असली चैनल होता है" with {en,gu,hi,mr} enabled:
//
//      the 56-byte chunk alone                    -> mr  p=0.712
//      that chunk WITH the utterance's Devanagari -> hi  p=1.0000
//      the same, doubled                          -> hi  p=1.0000
//
//  The model is not broken; it is being asked with too little text. The app
//  makes that unavoidable on its own: buildMixChunks splits an utterance into
//  per-script chunks first, so "जहाँ", "और", "दोनों मिलें" and the tail arrive at
//  the detector as four separate 3-to-56-byte fragments even though they are one
//  Devanagari sentence.
//
//  So the whole normalised utterance is kept here, and the CLD3 arm detects a
//  SHORT span against that utterance's same-script text instead of the span
//  alone. CLD2's arm is untouched -- it does not need this and it must stay
//  byte-for-byte AutoTTS.
//
//  It lives HERE, immediately above buildMixChunks, rather than beside the
//  language-hint tables where the rest of the detector state sits. That is
//  deliberate: tools/verify/make_core_inc.py slices the core "--until
//  buildMixChunks" for the segmenter harness, so anything the chunk builder
//  calls has to be defined above it or that harness will not link.
// ==========================================================================
static std::mutex detectContextMutex;
static std::string detectContextText;

static void setDetectContext(const std::string& text){
    std::lock_guard<std::mutex> lock(detectContextMutex);
    detectContextText = text;
}
static std::string currentDetectContext(){
    std::lock_guard<std::mutex> lock(detectContextMutex);
    return detectContextText;
}

static std::vector<ChunkResult> buildMixChunks(const std::vector<std::string>& sentences, const std::string& latinFallback, const std::string& nonLatinFallback, const ReadingModes& modes, const std::string& neutralDefault, int neutralType, bool disableAdvancedDetection, bool isDual = false) {
    std::vector<ChunkResult> result;
    std::string fullText;
    for (auto& sentence : sentences) { if (!sentence.empty()) fullText += sentence + " "; }
    if (fullText.empty()) return result;
    {
        {
            size_t textBegin = 0, textEnd = fullText.size();
            while (textBegin < textEnd && (unsigned char)fullText[textBegin] <= 0x20) ++textBegin;
            while (textEnd > textBegin && (unsigned char)fullText[textEnd - 1] <= 0x20) --textEnd;
            if (textBegin == textEnd) return result;
            fullText = fullText.substr(textBegin, textEnd - textBegin);
        }
        auto isUnicodeWs = [](int codePoint) -> bool {
            return (codePoint >= 0x09 && codePoint <= 0x0D) || codePoint == 0x20 ||
                   codePoint == 0x00A0 || codePoint == 0x2007 || codePoint == 0x202F || codePoint == 0xFEFF;
        };
        std::string collapsed; collapsed.reserve(fullText.size());
        bool prevWs = false;
        int collapsePos = 0, collapseLen = (int)fullText.size();
        while (collapsePos < collapseLen) {
            int cpLen = 0;
            int codePoint = utf8ToCodepoint((const unsigned char*)&fullText[collapsePos], cpLen);
            if (cpLen <= 0) cpLen = 1;
            if (isUnicodeWs(codePoint)) { if (!prevWs) collapsed += ' '; prevWs = true; }
            else { collapsed.append(fullText, collapsePos, (size_t)cpLen); prevWs = false; }
            collapsePos += cpLen;
        }
        fullText = std::move(collapsed);
    }
    // c3.d0.t: d0.q(clsCLD2.b(text.replaceAll(...).replaceAll(...))). The
    // whitespace collapse above is the two replaceAll calls, the bidi strip
    // below is q(), and this is clsCLD2.b sitting between them.
    fullText = normalizeFancyText(fullText);
    if (fullText.empty()) return result;
    {
        std::string clean;
        int bytePos = 0, byteLen = (int)fullText.size();
        while (bytePos < byteLen) {
            int cpLen = 0;
            int codePoint = utf8ToCodepoint((const unsigned char*)&fullText[bytePos], cpLen);
            if (codePoint == 0x061C || codePoint == 0x200E || codePoint == 0x200F ||
                (codePoint >= 0x202A && codePoint <= 0x202E)) { bytePos += cpLen; continue; }
            clean += fullText.substr(bytePos, cpLen);
            bytePos += cpLen;
        }
        fullText = clean;
    }
    // The text every chunk is cut from, kept for the CLD3 arm of the span site.
    // Set HERE rather than in the JNI wrapper so it is the NORMALISED text --
    // the same bytes the chunks are substrings of, which is what makes the
    // staleness guard at the span site a simple substring test.
    setDetectContext(fullText);
    if (fullText.empty()) return result;
    std::vector<TextSegment> segs;
    {
        std::string buf;
        int bufKind = -1;
        auto flushBuf = [&]() {
            if (buf.empty()) return;
            if (bufKind == 1) splitByEmoji(buf, segs);
            else splitByNumber(buf, segs);
            buf.clear(); bufKind = -1;
        };
        int len = (int)fullText.size(), i = 0;
        while (i < len) {
            int cpLen = 0;
            int codePoint = utf8ToCodepoint((const unsigned char*)&fullText[i], cpLen);
            bool latRange = (codePoint <= 0x024F) ||
                            (codePoint >= 0x1E00 && codePoint <= 0x1EFF) ||
                            (codePoint >= 0x2000 && codePoint <= 0x206F) ||
                            (codePoint >= 0x20A0 && codePoint <= 0x20CF) ||
                            (codePoint >= 0x2C60 && codePoint <= 0x2C7F) ||
                            (codePoint >= 0xA720 && codePoint <= 0xA7FF);
            int kind = latRange ? 0 : 1;
            if (bufKind == -1 || bufKind == kind) { buf += fullText.substr(i, cpLen); bufKind = kind; }
            else { flushBuf(); buf = fullText.substr(i, cpLen); bufKind = kind; }
            i += cpLen;
        }
        flushBuf();
    }
    {
        std::vector<TextSegment> mergedSegs;
        int segIdx = 0, segCount = (int)segs.size();
        while (segIdx < segCount) {
            std::string runText = segs[segIdx].text;
            int runType = segs[segIdx].type;
            int runEnd = segIdx + 1;
            while (runEnd < segCount && segs[runEnd].type != -1 &&
                   (segs[runEnd].type == runType || segs[runEnd].type == 0)) {
                runText += segs[runEnd].text;
                runEnd++;
            }
            mergedSegs.push_back({runText, runType, ""});
            segIdx = runEnd;
        }
        segs = std::move(mergedSegs);
    }
    if (segs.empty()) return result;
    (void)disableAdvancedDetection;
    if (modes.smartNumber) {
        std::vector<std::string> keywords = activeSmartNumberKeywords();
        for (int segPos = 0; segPos < (int)segs.size(); segPos++) {
            if (segs[segPos].type != 3) continue;
            const std::string& numberText = segs[segPos].text;
            bool respace = isPhoneShaped(numberText);
            if (!respace && !keywords.empty() && hasEnoughDigits(numberText)) {
                std::string before;
                for (int backIdx = segPos - 1; backIdx >= 0 && utf16LengthOf(before) < 48; backIdx--)
                    before.insert(0, segs[backIdx].text);
                if (utf16LengthOf(before) > 48) before = lastUtf16Units(before, 48);
                std::string after;
                for (int nextIdx = segPos + 1; nextIdx < (int)segs.size() && utf16LengthOf(after) < 24; nextIdx++)
                    after += segs[nextIdx].text;
                if (utf16LengthOf(after) > 24) after = firstUtf16Units(after, 24);
                respace = contextHasKeyword(lowerCaseRoot(before), keywords) ||
                          contextHasKeyword(lowerCaseRoot(after), keywords);
            }
            if (respace) segs[segPos].text = respaceDigits(numberText, modes.smartNumberGroupSize);
        }
    }
    std::vector<int> originalTypes;
    originalTypes.reserve(segs.size());
    for (auto& seg : segs) originalTypes.push_back(seg.type);
    auto isTypedRun = [](int type) { return type != 3 && type != 4 && type != 5; };
    // c3.d0.i(nArray, n3): the nearest type at or before n3-1 that is not 3, 4
    // or 5 -- and it RETURNS that type even when it is 0, rather than reading
    // past it. Type 0 is all-whitespace and survives the first merge only at
    // index 0, which happens when the text began with U+00A0, U+2007, U+202F or
    // U+FEFF: trim() strips nothing above U+0020, so the leading character is
    // still there when the whitespace collapse turns it into a space.
    auto typeBefore = [&](int segPos) {
        for (int prevIdx = segPos - 1; prevIdx >= 0; prevIdx--) {
            int type = originalTypes[prevIdx];
            if (type != 3 && type != 4 && type != 5) return type;
        }
        return 0;
    };
    // c3.d0.h(nArray): the same scan, forwards from the start of the array.
    auto typeAnywhere = [&]() {
        for (int scanIdx = 0; scanIdx < (int)originalTypes.size(); scanIdx++) {
            int type = originalTypes[scanIdx];
            if (type != 3 && type != 4 && type != 5) return type;
        }
        return 0;
    };
    // c3.d0.k(nArray, n3, n7): backwards, then forwards, then the neutral type.
    // A 0 from either scan counts as "not found", so a whitespace neighbour
    // sends the segment to the neutral type instead of to the next real run.
    auto surroundingType = [&](int segPos) {
        int found = typeBefore(segPos);
        if (found == 0) found = typeAnywhere();
        if (found == 0) return neutralType;
        return found;
    };
    for (int segPos = 0; segPos < (int)segs.size(); segPos++) {
        int segType = originalTypes[segPos];
        if (isTypedRun(segType)) continue;
        int typeMode = (segType == 3) ? modes.numberMode
                     : (segType == 4) ? modes.punctuationMode : modes.emojiMode;
        if (typeMode == 3) continue;
        segs[segPos].type = (typeMode == 0) ? surroundingType(segPos) : typeMode;
    }
    for (auto& seg : segs) {
        seg.lang = (seg.type == 1) ? latinFallback
                 : (seg.type == 2) ? nonLatinFallback
                 : (seg.type == 3) ? modes.numberSpecific
                 : (seg.type == 4) ? modes.punctuationSpecific
                 : (seg.type == 5) ? modes.emojiSpecific : neutralDefault;
    }
    bool punctuationApart = (modes.punctuationMode != 0 && !modes.punctuationInFlow);
    int mergePos = 0, segTotal = (int)segs.size();
    while (mergePos < segTotal) {
        std::string runText = segs[mergePos].text;
        int runType = segs[mergePos].type;
        std::string runLang = segs[mergePos].lang.empty() ? latinFallback : segs[mergePos].lang;
        if (punctuationApart && originalTypes[mergePos] == 4) {
            result.push_back({runLang, runText, runType, wholeSegmentKind(runText)});
            mergePos++;
            continue;
        }
        mergePos++;
        while (mergePos < segTotal && segs[mergePos].type == runType &&
               !(punctuationApart && originalTypes[mergePos] == 4)) {
            runText += segs[mergePos].text;
            mergePos++;
        }
        result.push_back({runLang, runText, runType, wholeSegmentKind(runText)});
    }
    return result;
}
extern "C" JNIEXPORT jstring JNICALL

// ==========================================================================
//  JNI: processDirect
//  Packs the chunk list as  type US kind US lang US text, with records
//  joined by RS -- US is U+001F, RS is U+001E. Both, and U+001D which is
//  the escape, are escaped inside the text: they can occur in it, and an
//  unescaped U+001E once cost the rest of the utterance.
// ==========================================================================
Java_com_tts_easyvoice_NativeEngine_processDirect(
    JNIEnv* env, jobject, jobject directBuffer, jint length,
    jstring jLat, jstring jNonLat, jstring jMode,
    jint jNumberMode, jstring jNumberSpecific,
    jint jPunctuationMode, jstring jPunctuationSpecific,
    jint jEmojiMode, jstring jEmojiSpecific, jboolean jPunctuationInFlow, jboolean jSmartNumber,
    jint jSmartNumberGroupSize,
    jstring jNeutralDefault, jint jNeutralType, jboolean jDisableAdvancedDetection, jboolean jUseCld3)
{
    if(!directBuffer||length<=0) return env->NewStringUTF("");
    void* buf=env->GetDirectBufferAddress(directBuffer);
    if(!buf) return env->NewStringUTF("");
    std::string rawInput((const char*)buf,(size_t)length);
    const char* latC=env->GetStringUTFChars(jLat,nullptr); std::string latinFallback(latC); env->ReleaseStringUTFChars(jLat,latC);
    const char* nonLatC=env->GetStringUTFChars(jNonLat,nullptr); std::string nonLatinFallback(nonLatC); env->ReleaseStringUTFChars(jNonLat,nonLatC);
    const char* modeC=env->GetStringUTFChars(jMode,nullptr); std::string mode(modeC); env->ReleaseStringUTFChars(jMode,modeC);
    ReadingModes modes;
    modes.numberMode = (int)jNumberMode;
    modes.punctuationMode = (int)jPunctuationMode;
    modes.emojiMode = (int)jEmojiMode;
    modes.punctuationInFlow = (jPunctuationInFlow == JNI_TRUE);
    modes.smartNumber = (jSmartNumber == JNI_TRUE);
    modes.smartNumberGroupSize = (int)jSmartNumberGroupSize;
    const char* numSpecC=env->GetStringUTFChars(jNumberSpecific,nullptr); modes.numberSpecific=numSpecC; env->ReleaseStringUTFChars(jNumberSpecific,numSpecC);
    const char* puncSpecC=env->GetStringUTFChars(jPunctuationSpecific,nullptr); modes.punctuationSpecific=puncSpecC; env->ReleaseStringUTFChars(jPunctuationSpecific,puncSpecC);
    const char* emojiSpecC=env->GetStringUTFChars(jEmojiSpecific,nullptr); modes.emojiSpecific=emojiSpecC; env->ReleaseStringUTFChars(jEmojiSpecific,emojiSpecC);
    const char* ndC=env->GetStringUTFChars(jNeutralDefault,nullptr); std::string neutralDefault(ndC); env->ReleaseStringUTFChars(jNeutralDefault,ndC);
    int neutralType = (int)jNeutralType;
    bool disableAdvancedDetection = (jDisableAdvancedDetection == JNI_TRUE);
    (void)jUseCld3;
    std::vector<std::string> sentences;
    size_t start=0, end=rawInput.find('\0');
    while(end!=std::string::npos){ sentences.push_back(rawInput.substr(start,end-start)); start=end+1; end=rawInput.find('\0',start); }
    if(start<rawInput.size()) sentences.push_back(rawInput.substr(start));
    std::vector<std::string> processed; processed.reserve(sentences.size());
    for(auto& sentence: sentences){ processed.push_back(sentence); }
    // The chunk list is packed as  type \x1F kind \x1F lang \x1F text, with
    // records joined by \x1E. Those two characters can occur in the text
    // itself: they are Character.isWhitespace, but AutoTTS's collapse is
    // replaceAll("\\s+", " ") and Java's \\s is only [ \\t\\n\\x0B\\f\\r], so
    // a U+001E survives the collapse there and here alike. Unescaped, it split
    // one record into two and the Kotlin dropped the half with three fields --
    // everything after it went unspoken. U+001D is the escape and is escaped
    // too; the language and the two integers can never contain any of them.
    auto packField = [](const std::string& in){
        std::string out;
        out.reserve(in.size());
        for (size_t at = 0; at < in.size(); at++) {
            char ch = in[at];
            if (ch == '\x1D') { out += "\x1D""0"; }
            else if (ch == '\x1E') { out += "\x1D""1"; }
            else if (ch == '\x1F') { out += "\x1D""2"; }
            else out += ch;
        }
        return out;
    };
    std::string result;
    if (mode == "mix") {
        auto chunks = buildMixChunks(processed, latinFallback, nonLatinFallback, modes, neutralDefault, neutralType, disableAdvancedDetection);
        for(size_t i=0; i<chunks.size(); i++){ result += std::to_string(chunks[i].type) + "\x1F" + std::to_string(chunks[i].kind) + "\x1F" + chunks[i].lang + "\x1F" + packField(chunks[i].text); if(i+1 < chunks.size()) result += "\x1E"; }
    } else if (mode == "dual") {
        auto chunks = buildMixChunks(processed, latinFallback, nonLatinFallback, modes, neutralDefault, neutralType, disableAdvancedDetection, true);
        for(size_t i=0; i<chunks.size(); i++){ result += std::to_string(chunks[i].type) + "\x1F" + std::to_string(chunks[i].kind) + "\x1F" + chunks[i].lang + "\x1F" + packField(chunks[i].text); if(i+1 < chunks.size()) result += "\x1E"; }
    }
    return env->NewStringUTF(result.c_str());
}

// ==========================================================================
//  ISO CODES AND JAVA COLLECTION-ORDER EMULATION
//  javaHashSetOrder is load-bearing: the family fallback takes the first
//  enabled member IN HashSet ORDER, so any other container is a different
//  voice. It models ANDROID's HashSet, which is not JDK 19+'s.
// ==========================================================================
static std::unordered_map<std::string,std::string> g_iso2to3;
static std::string toIso3(const std::string& lang){
    std::string normalised = lang;
    size_t cut = normalised.find_first_of("-_");
    if (cut != std::string::npos) normalised = normalised.substr(0, cut);
    for (char& ch : normalised) if (ch >= 'A' && ch <= 'Z') ch = (char)(ch - 'A' + 'a');
    std::string iso3 = normalised;
    if (normalised.size()==2){ auto found=g_iso2to3.find(normalised); if(found!=g_iso2to3.end() && !found->second.empty()) iso3=found->second; }
    if (iso3=="cmn"||iso3=="lzh"||iso3=="gan"||iso3=="hak") return "zho";
    return iso3;
}
static bool isJavaWhitespace(int codePoint){
    return (codePoint>=0x09&&codePoint<=0x0D)||(codePoint>=0x1C&&codePoint<=0x1F)||codePoint==0x20||codePoint==0x1680||
           (codePoint>=0x2000&&codePoint<=0x200A&&codePoint!=0x2007)||codePoint==0x205F||codePoint==0x3000||codePoint==0x2028||codePoint==0x2029;
}
static bool isLatinPunctuation(int codePoint){ return (codePoint>=33&&codePoint<=47)||(codePoint>=58&&codePoint<=64)||(codePoint>=91&&codePoint<=96)||(codePoint>=123&&codePoint<=126); }
static int firstValidCodePointU16(const jchar* units, int len){
    int j=0;
    while(j<len){
        jchar unit=units[j];
        if(!isJavaWhitespace((int)unit) && !(unit>='0'&&unit<='9') && !isLatinPunctuation((int)unit)){
            if(unit>=0xD800&&unit<=0xDBFF&&j+1<len&&units[j+1]>=0xDC00&&units[j+1]<=0xDFFF)
                return 0x10000+((((int)unit)-0xD800)<<10)+(((int)units[j+1])-0xDC00);
            return (int)unit;
        }
        j++;
    }
    return -1;
}
static std::string utf16to8(const jchar* units, int len){
    std::string out; out.reserve((size_t)len*3);
    int j=0;
    while(j<len){
        int codePoint=(int)units[j];
        if(codePoint>=0xD800&&codePoint<=0xDBFF&&j+1<len&&units[j+1]>=0xDC00&&units[j+1]<=0xDFFF){ codePoint=0x10000+((codePoint-0xD800)<<10)+(((int)units[j+1])-0xDC00); j+=2; } else j++;
        // Modified UTF-8, because the string this stands in for reaches
        // AutoTTS's detector through GetStringUTFChars: U+0000 is C0 80 there,
        // never a bare NUL byte.
        if(codePoint==0){ out+=(char)0xC0; out+=(char)0x80; }
        else if(codePoint<0x80) out+=(char)codePoint;
        else if(codePoint<0x800){ out+=(char)(0xC0|(codePoint>>6)); out+=(char)(0x80|(codePoint&0x3F)); }
        else if(codePoint<0x10000){ out+=(char)(0xE0|(codePoint>>12)); out+=(char)(0x80|((codePoint>>6)&0x3F)); out+=(char)(0x80|(codePoint&0x3F)); }
        else { out+=(char)(0xF0|(codePoint>>18)); out+=(char)(0x80|((codePoint>>12)&0x3F)); out+=(char)(0x80|((codePoint>>6)&0x3F)); out+=(char)(0x80|(codePoint&0x3F)); }
    }
    return out;
}
static int javaStringHash(const std::string& text){ uint32_t hash=0; for(unsigned char ch: text) hash=hash*31u+(uint32_t)ch; return (int)hash; }
static uint32_t javaHashSpread(int hash){ uint32_t spreadBits=(uint32_t)hash; return spreadBits ^ (spreadBits>>16); }
static int javaTableSizeFor(int wanted){ if(wanted<=1) return 1; int size=wanted-1; size|=size>>1;size|=size>>2;size|=size>>4;size|=size>>8;size|=size>>16; return size+1; }
static std::vector<std::string> javaHashSetOrder(const std::vector<std::string>& inserted, int initialCapacity){
    int capacity=0, threshold=(initialCapacity>=0?javaTableSizeFor(initialCapacity):0), size=0;
    std::vector<std::vector<std::string>> buckets; std::unordered_set<std::string> keys;
    auto resize=[&](){ int newCap,newThreshold; if(capacity>0){newCap=capacity*2;newThreshold=threshold*2;} else if(threshold>0){newCap=threshold;newThreshold=(int)(newCap*0.75);} else {newCap=16;newThreshold=12;}
        std::vector<std::vector<std::string>> newBuckets(newCap); for(auto&bucket:buckets)for(auto&key:bucket) newBuckets[javaHashSpread(javaStringHash(key))&(newCap-1)].push_back(key); buckets.swap(newBuckets); capacity=newCap; threshold=newThreshold; };
    for(auto& key: inserted){ if(keys.count(key)) continue; if(buckets.empty()) resize(); buckets[javaHashSpread(javaStringHash(key))&(capacity-1)].push_back(key); keys.insert(key); size++; if(size>threshold) resize(); }
    std::vector<std::string> out; for(auto&bucket:buckets)for(auto&key:bucket) out.push_back(key); return out;
}
static std::string javaFamilyFallback(const std::string& primary, const std::vector<std::string>& fallbacks, const std::unordered_set<std::string>& enabled){
    std::vector<std::string> full; full.push_back(primary); for(auto& fallback: fallbacks) full.push_back(fallback);
    int initialCapacity=(int)(full.size()/0.75)+1; if(initialCapacity<16) initialCapacity=16;
    std::vector<std::string> famOrder = javaHashSetOrder(full, initialCapacity);
    std::vector<std::string> enabledInOrder; for(auto& code2: famOrder) if(enabled.count(code2)) enabledInOrder.push_back(code2);
    std::vector<std::string> ordered = javaHashSetOrder(enabledInOrder, -1);
    for(auto& code2: ordered) if(enabled.count(code2)) return code2;
    return "";
}

// ==========================================================================
//  SCRIPT FAMILIES             AutoTTS com/vnspeak/autotts/a.java
//  PROVEN: tools/verify/scriptfamily/run.sh -- 15 language sets over every
//  code point, iteration order included.
//  familyLangForCpFiltered and familyForCp are near-identical ladders and must
//  stay separate: the filtered one is GATED by which families are enabled, and
//  the gates change which branch a code point falls through to.
// ==========================================================================
static const char* FAMILY_LATIN[] = {"en","es","fr","de","it","pt","nl","sv","no","da","fi","pl","cs","sk","hu","ro","tr","id","ms","vi","tl","sw","hr","sr","sl","et","lv","lt","is","ga","cy","sq","mt"};
static const char* FAMILY_CYRILLIC[]   = {"ru","uk","be","bg","mk","sr","bs","hr","kk","ky","tg","mn","uz","tt","ba","cv","sah","ce","av","ab"};
static const char* FAMILY_ARABIC[]  = {"ar","fa","ur","ps","sd","ku","ckb","az","ks","ug","ha"};
static const char* FAMILY_PERSIAN[]    = {"fa","ur","ps","ku","ckb","sd","az","ks","ug"};
static const char* FAMILY_URDU[]    = {"ur","ps","sd","pa"};
static const char* FAMILY_DEVANAGARI[]  = {"hi","mr","ne","sa","kok","mai","bh","new","awa","bho","raj","hne"};
static const char* FAMILY_ETHIOPIC[]   = {"am","ti","om","aa","so","sid","wal","gez"};
static const char* FAMILY_CJK[]   = {"zh","ja","ko","vi"};
static const char* FAMILY_DIGIT[] = {"en","es","fr","de","it","pt","ru","ja","zh","ko","ar","hi"};
template <size_t N>
static bool anyLangEnabled(const std::unordered_set<std::string>& enabled, const char* const (&arr)[N]){
    for(size_t i=0;i<N;i++) if(enabled.count(arr[i])) return true;
    return false;
}
static std::string pickFamilyLang(const std::string& primary, const std::vector<std::string>& rest,
                          const std::unordered_set<std::string>& enabled){
    if(enabled.count(primary)) return primary;
    return javaFamilyFallback(primary, rest, enabled);
}
template <size_t N>
static std::string pickFamilyLangArr(const char* const (&arr)[N], const std::unordered_set<std::string>& enabled){
    std::vector<std::string> rest; for(size_t i=1;i<N;i++) rest.push_back(arr[i]);
    return pickFamilyLang(arr[0], rest, enabled);
}
static std::string familyLangForCpFiltered(int codePoint, const std::unordered_set<std::string>& enabled){
    bool hasLatin  = anyLangEnabled(enabled, FAMILY_LATIN);
    bool hasCyrillic = anyLangEnabled(enabled, FAMILY_CYRILLIC);
    bool hasArabic = anyLangEnabled(enabled, FAMILY_ARABIC);
    bool hasThaana = enabled.count("dv") > 0;
    bool hasDevanagari = anyLangEnabled(enabled, FAMILY_DEVANAGARI);
    bool hasCjk = anyLangEnabled(enabled, FAMILY_CJK);
    bool hasGreek = enabled.count("el") || enabled.count("grc") || enabled.count("pnt");
    bool hasBengali = enabled.count("bn") || enabled.count("as") || enabled.count("mni");
    bool hasThai = enabled.count("th") || enabled.count("nod") || enabled.count("sou") || enabled.count("tts");
    bool hasHangul = enabled.count("ko") > 0;
    bool hasKana = enabled.count("ja") || enabled.count("ain");
    bool hasHebrew = enabled.count("he") || enabled.count("yi") || enabled.count("lad");
    bool hasArmenian = enabled.count("hy") > 0;
    bool hasGeorgian = enabled.count("ka") || enabled.count("xmf") || enabled.count("lzz");
    bool hasEthiopic = anyLangEnabled(enabled, FAMILY_ETHIOPIC);
    bool hasSouthIndic = enabled.count("pa") || enabled.count("gu") || enabled.count("or") || enabled.count("ta") ||
                enabled.count("te") || enabled.count("kn") || enabled.count("ml") || enabled.count("si") || enabled.count("tcy");
    bool hasSeAsian = enabled.count("lo") || enabled.count("hnx") || enabled.count("bo") || enabled.count("dz") ||
                enabled.count("my") || enabled.count("shn") || enabled.count("kar") || enabled.count("mnw") || enabled.count("km");
    bool hasMongolian = enabled.count("mn") || enabled.count("mnc") || enabled.count("xal");
    if(codePoint>=48 && codePoint<=57) return pickFamilyLangArr(FAMILY_DIGIT, enabled);
    if((codePoint>=32&&codePoint<=47)||(codePoint>=58&&codePoint<=64)||(codePoint>=91&&codePoint<=96)||(codePoint>=123&&codePoint<=126)||(codePoint>=8192&&codePoint<=8303)) return "";
    if(hasLatin){
        if(codePoint==192||codePoint==194||codePoint==199||codePoint==200||codePoint==201||codePoint==202||codePoint==203||codePoint==206||codePoint==207||codePoint==212||codePoint==217||
           codePoint==219||codePoint==224||codePoint==226||codePoint==231||codePoint==232||codePoint==233||codePoint==234||codePoint==235||codePoint==238||codePoint==239||codePoint==244||
           codePoint==249||codePoint==251||codePoint==338||codePoint==339) return pickFamilyLang("fr", {"ca","oc"}, enabled);
        if(codePoint==193||codePoint==205||codePoint==209||codePoint==211||codePoint==218||codePoint==225||codePoint==237||codePoint==241||codePoint==243||codePoint==250)
            return pickFamilyLang("es", {"pt","gl","ca"}, enabled);
        if(codePoint==196||codePoint==214||codePoint==220||codePoint==228||codePoint==246||codePoint==252||codePoint==223) return pickFamilyLang("de", {"lb"}, enabled);
        if(codePoint==197||codePoint==198||codePoint==216||codePoint==229||codePoint==230||codePoint==248) return pickFamilyLang("no", {"da","sv","is","fo"}, enabled);
        if(codePoint==260||codePoint==261||codePoint==262||codePoint==263||codePoint==280||codePoint==281||codePoint==321||codePoint==322||codePoint==323||codePoint==324||codePoint==346||
           codePoint==347||codePoint==377||codePoint==378||codePoint==379||codePoint==380) return pickFamilyLang("pl", {}, enabled);
        if(codePoint==268||codePoint==269||codePoint==270||codePoint==271||codePoint==282||codePoint==283||codePoint==327||codePoint==328||codePoint==344||codePoint==345||codePoint==352||
           codePoint==353||codePoint==356||codePoint==357||codePoint==366||codePoint==367||codePoint==381||codePoint==382) return pickFamilyLang("cs", {"sk"}, enabled);
        if((codePoint>=7840&&codePoint<=7929)||codePoint==272||codePoint==273||codePoint==416||codePoint==417||codePoint==431||codePoint==432) return pickFamilyLang("vi", {}, enabled);
        if((codePoint>=65&&codePoint<=90)||(codePoint>=97&&codePoint<=122)) return pickFamilyLangArr(FAMILY_LATIN, enabled);
        if((codePoint>=192&&codePoint<=255)||(codePoint>=256&&codePoint<=591)) return pickFamilyLangArr(FAMILY_LATIN, enabled);
    }
    if(hasGreek && ((codePoint>=880&&codePoint<=1023)||(codePoint>=7936&&codePoint<=8191))) return pickFamilyLang("el", {"grc","pnt"}, enabled);
    if(hasCyrillic && ((codePoint>=1024&&codePoint<=1279)||(codePoint>=1280&&codePoint<=1327))){
        if(codePoint==1028||codePoint==1108||codePoint==1030||codePoint==1110||codePoint==1031||codePoint==1111||codePoint==1168||codePoint==1169) return pickFamilyLang("uk", {}, enabled);
        if(codePoint==1026||codePoint==1106||codePoint==1032||codePoint==1112||codePoint==1033||codePoint==1113||codePoint==1034||codePoint==1114||codePoint==1035||codePoint==1115||
           codePoint==1039||codePoint==1119) return pickFamilyLang("sr", {"bs","hr"}, enabled);
        if(codePoint==1066||codePoint==1098) return pickFamilyLang("bg", {"mk"}, enabled);
        if(codePoint==1186||codePoint==1187||codePoint==1170||codePoint==1171||codePoint==1200||codePoint==1201||codePoint==1198||codePoint==1199||codePoint==1240||codePoint==1241)
            return pickFamilyLang("kk", {"ky"}, enabled);
        if(codePoint==1025||codePoint==1105||codePoint==1069||codePoint==1101||codePoint==1067||codePoint==1099) return pickFamilyLang("ru", {"be"}, enabled);
        return pickFamilyLangArr(FAMILY_CYRILLIC, enabled);
    }
    if(hasArmenian && ((codePoint>=1328&&codePoint<=1423)||(codePoint>=64256&&codePoint<=64279))) return pickFamilyLang("hy", {}, enabled);
    if(hasHebrew && ((codePoint>=1424&&codePoint<=1535)||(codePoint>=64285&&codePoint<=64335))) return pickFamilyLang("he", {"yi","lad"}, enabled);
    if(hasArabic && ((codePoint>=1536&&codePoint<=1791)||(codePoint>=1872&&codePoint<=1919)||(codePoint>=2208&&codePoint<=2303)||(codePoint>=64336&&codePoint<=65023)||(codePoint>=65136&&codePoint<=65279))){
        if(codePoint==1657||codePoint==1672||codePoint==1681||codePoint==1729||codePoint==1746) return pickFamilyLang("ur", {"pa"}, enabled);
        if(codePoint==1665||codePoint==1669||codePoint==1673||codePoint==1683||codePoint==1686||codePoint==1690||codePoint==1724||codePoint==1744) return pickFamilyLang("ps", {}, enabled);
        if(codePoint>=1658&&codePoint<=1661) return pickFamilyLang("sd", {}, enabled);
        if(codePoint==1742||codePoint==1685||codePoint==1717||codePoint==1734) return pickFamilyLang("ku", {"ckb"}, enabled);
        if(codePoint==1722||codePoint==1726) return pickFamilyLangArr(FAMILY_URDU, enabled);
        if(codePoint==1662||codePoint==1670||codePoint==1688||codePoint==1711) return pickFamilyLangArr(FAMILY_PERSIAN, enabled);
        return pickFamilyLang("ar", {}, enabled);
    }
    if(hasThaana && codePoint>=1920 && codePoint<=1983) return "dv";
    if(hasDevanagari && ((codePoint>=2304&&codePoint<=2431)||(codePoint>=43232&&codePoint<=43263))){
        if(codePoint!=2355) return pickFamilyLangArr(FAMILY_DEVANAGARI, enabled);
        return pickFamilyLang("mr", {"kok"}, enabled);
    }
    if(hasBengali && codePoint>=2432 && codePoint<=2559){
        if(codePoint==2544||codePoint==2545) return pickFamilyLang("as", {}, enabled);
        return pickFamilyLang("bn", {"as","mni"}, enabled);
    }
    if(hasSouthIndic){
        if(codePoint>=2560&&codePoint<=2687) return pickFamilyLang("pa", {}, enabled);
        if(codePoint>=2688&&codePoint<=2815) return pickFamilyLang("gu", {}, enabled);
        if(codePoint>=2816&&codePoint<=2943) return pickFamilyLang("or", {}, enabled);
        if(codePoint>=2944&&codePoint<=3071) return pickFamilyLang("ta", {"kn"}, enabled);
        if(codePoint>=3072&&codePoint<=3199) return pickFamilyLang("te", {}, enabled);
        if(codePoint>=3200&&codePoint<=3327) return pickFamilyLang("kn", {"tcy"}, enabled);
        if(codePoint>=3328&&codePoint<=3455) return pickFamilyLang("ml", {}, enabled);
        if(codePoint>=3456&&codePoint<=3583) return pickFamilyLang("si", {}, enabled);
    }
    if(hasThai && codePoint>=3584 && codePoint<=3711) return pickFamilyLang("th", {"nod","sou","tts"}, enabled);
    if(hasSeAsian){
        if(codePoint>=3712&&codePoint<=3839) return pickFamilyLang("lo", {"hnx"}, enabled);
        if(codePoint>=3840&&codePoint<=4095) return pickFamilyLang("bo", {"dz"}, enabled);
        if((codePoint>=4096&&codePoint<=4255)||(codePoint>=43616&&codePoint<=43647)) return pickFamilyLang("my", {"shn","kar","mnw"}, enabled);
        if((codePoint>=6016&&codePoint<=6143)||(codePoint>=6624&&codePoint<=6655)) return pickFamilyLang("km", {}, enabled);
    }
    if(hasGeorgian && ((codePoint>=4256&&codePoint<=4351)||(codePoint>=11520&&codePoint<=11567))) return pickFamilyLang("ka", {"xmf","lzz"}, enabled);
    if(hasHangul && ((codePoint>=4352&&codePoint<=4607)||(codePoint>=12592&&codePoint<=12687)||(codePoint>=43360&&codePoint<=43391)||(codePoint>=44032&&codePoint<=55215)||(codePoint>=55216&&codePoint<=55295)))
        return pickFamilyLang("ko", {}, enabled);
    if(hasEthiopic && ((codePoint>=4608&&codePoint<=4991)||(codePoint>=4992&&codePoint<=5023)||(codePoint>=11648&&codePoint<=11743))) return pickFamilyLangArr(FAMILY_ETHIOPIC, enabled);
    if(hasMongolian && ((codePoint>=6144&&codePoint<=6319)||(codePoint>=71264&&codePoint<=71295))) return pickFamilyLang("mn", {"mnc","xal"}, enabled);
    if(hasCjk && codePoint>=12288 && codePoint<=12351) return pickFamilyLangArr(FAMILY_CJK, enabled);
    if(hasKana){
        if((codePoint>=12352&&codePoint<=12447)||(codePoint>=110592&&codePoint<=110847)||(codePoint>=110848&&codePoint<=110895)) return pickFamilyLang("ja", {}, enabled);
        if((codePoint>=12448&&codePoint<=12543)||(codePoint>=12784&&codePoint<=12799)||(codePoint>=110896&&codePoint<=110959)) return pickFamilyLang("ja", {"ain"}, enabled);
    }
    if(hasCjk && ((codePoint>=19968&&codePoint<=40959)||(codePoint>=13312&&codePoint<=19903)||(codePoint>=131072&&codePoint<=173791)||(codePoint>=173824&&codePoint<=177983)||
               (codePoint>=177984&&codePoint<=178207)||(codePoint>=178208&&codePoint<=183983)||(codePoint>=183984&&codePoint<=191471)||
               (codePoint>=196608&&codePoint<=201551)||(codePoint>=201552&&codePoint<=205743)))
        return pickFamilyLangArr(FAMILY_CJK, enabled);
    return "";
}
struct LangFamily { const char* primary; std::vector<std::string> members; };
static LangFamily familyForCp(int codePoint){
    auto makeFamily=[&](const char* primaryCode, std::initializer_list<const char*> rest){
        LangFamily family; family.primary=primaryCode; family.members.push_back(primaryCode);
        for(auto member: rest) family.members.push_back(member); return family; };
    auto famFromArray=[&](const char* const* arr, size_t count){
        LangFamily family; family.primary=arr[0];
        for(size_t i=0;i<count;i++) family.members.push_back(arr[i]); return family; };
    if(codePoint>=48 && codePoint<=57) return famFromArray(FAMILY_DIGIT, sizeof(FAMILY_DIGIT)/sizeof(FAMILY_DIGIT[0]));
    if((codePoint>=32&&codePoint<=47)||(codePoint>=58&&codePoint<=64)||(codePoint>=91&&codePoint<=96)||(codePoint>=123&&codePoint<=126)||(codePoint>=8192&&codePoint<=8303))
        return makeFamily("zz", {});
    if(codePoint==192||codePoint==194||codePoint==199||codePoint==200||codePoint==201||codePoint==202||codePoint==203||codePoint==206||codePoint==207||codePoint==212||codePoint==217||
       codePoint==219||codePoint==224||codePoint==226||codePoint==231||codePoint==232||codePoint==233||codePoint==234||codePoint==235||codePoint==238||codePoint==239||codePoint==244||
       codePoint==249||codePoint==251||codePoint==338||codePoint==339) return makeFamily("fr", {"ca","oc"});
    if(codePoint==193||codePoint==205||codePoint==209||codePoint==211||codePoint==218||codePoint==225||codePoint==237||codePoint==241||codePoint==243||codePoint==250)
        return makeFamily("es", {"pt","gl","ca"});
    if(codePoint==196||codePoint==214||codePoint==220||codePoint==228||codePoint==246||codePoint==252||codePoint==223) return makeFamily("de", {"lb"});
    if(codePoint==197||codePoint==198||codePoint==216||codePoint==229||codePoint==230||codePoint==248) return makeFamily("no", {"da","sv","is","fo"});
    if(codePoint==260||codePoint==261||codePoint==262||codePoint==263||codePoint==280||codePoint==281||codePoint==321||codePoint==322||codePoint==323||codePoint==324||codePoint==346||
       codePoint==347||codePoint==377||codePoint==378||codePoint==379||codePoint==380) return makeFamily("pl", {});
    if(codePoint==268||codePoint==269||codePoint==270||codePoint==271||codePoint==282||codePoint==283||codePoint==327||codePoint==328||codePoint==344||codePoint==345||codePoint==352||
       codePoint==353||codePoint==356||codePoint==357||codePoint==366||codePoint==367||codePoint==381||codePoint==382) return makeFamily("cs", {"sk"});
    if((codePoint>=7840&&codePoint<=7929)||codePoint==272||codePoint==273||codePoint==416||codePoint==417||codePoint==431||codePoint==432) return makeFamily("vi", {});
    if((codePoint>=65&&codePoint<=90)||(codePoint>=97&&codePoint<=122)) return famFromArray(FAMILY_LATIN, sizeof(FAMILY_LATIN)/sizeof(FAMILY_LATIN[0]));
    if((codePoint>=192&&codePoint<=255)||(codePoint>=256&&codePoint<=591)) return famFromArray(FAMILY_LATIN, sizeof(FAMILY_LATIN)/sizeof(FAMILY_LATIN[0]));
    if((codePoint>=880&&codePoint<=1023)||(codePoint>=7936&&codePoint<=8191)) return makeFamily("el", {"grc","pnt"});
    if((codePoint>=1024&&codePoint<=1279)||(codePoint>=1280&&codePoint<=1327)){
        if(codePoint==1028||codePoint==1108||codePoint==1030||codePoint==1110||codePoint==1031||codePoint==1111||codePoint==1168||codePoint==1169) return makeFamily("uk", {});
        if(codePoint==1026||codePoint==1106||codePoint==1032||codePoint==1112||codePoint==1033||codePoint==1113||codePoint==1034||codePoint==1114||codePoint==1035||codePoint==1115||
           codePoint==1039||codePoint==1119) return makeFamily("sr", {"bs","hr"});
        if(codePoint==1066||codePoint==1098) return makeFamily("bg", {"mk"});
        if(codePoint==1186||codePoint==1187||codePoint==1170||codePoint==1171||codePoint==1200||codePoint==1201||codePoint==1198||codePoint==1199||codePoint==1240||codePoint==1241)
            return makeFamily("kk", {"ky"});
        if(codePoint==1025||codePoint==1105||codePoint==1069||codePoint==1101||codePoint==1067||codePoint==1099) return makeFamily("ru", {"be"});
        return famFromArray(FAMILY_CYRILLIC, sizeof(FAMILY_CYRILLIC)/sizeof(FAMILY_CYRILLIC[0]));
    }
    if((codePoint>=1328&&codePoint<=1423)||(codePoint>=64256&&codePoint<=64279)) return makeFamily("hy", {});
    if((codePoint>=1424&&codePoint<=1535)||(codePoint>=64285&&codePoint<=64335)) return makeFamily("he", {"yi","lad"});
    if((codePoint>=1536&&codePoint<=1791)||(codePoint>=1872&&codePoint<=1919)||(codePoint>=2208&&codePoint<=2303)||(codePoint>=64336&&codePoint<=65023)||(codePoint>=65136&&codePoint<=65279)){
        if(codePoint==1657||codePoint==1672||codePoint==1681||codePoint==1729||codePoint==1746) return makeFamily("ur", {"pa"});
        if(codePoint==1665||codePoint==1669||codePoint==1673||codePoint==1683||codePoint==1686||codePoint==1690||codePoint==1724||codePoint==1744) return makeFamily("ps", {});
        if(codePoint>=1658&&codePoint<=1661) return makeFamily("sd", {});
        if(codePoint==1742||codePoint==1685||codePoint==1717||codePoint==1734) return makeFamily("ku", {"ckb"});
        if(codePoint==1722||codePoint==1726) return famFromArray(FAMILY_URDU, sizeof(FAMILY_URDU)/sizeof(FAMILY_URDU[0]));
        if(codePoint==1662||codePoint==1670||codePoint==1688||codePoint==1711) return famFromArray(FAMILY_PERSIAN, sizeof(FAMILY_PERSIAN)/sizeof(FAMILY_PERSIAN[0]));
        return makeFamily("ar", {});
    }
    if(codePoint>=1920&&codePoint<=1983) return makeFamily("dv", {});
    if((codePoint>=2304&&codePoint<=2431)||(codePoint>=43232&&codePoint<=43263)){
        if(codePoint==2355) return makeFamily("mr", {"kok"});
        return famFromArray(FAMILY_DEVANAGARI, sizeof(FAMILY_DEVANAGARI)/sizeof(FAMILY_DEVANAGARI[0]));
    }
    if(codePoint>=2432&&codePoint<=2559){
        if(codePoint==2544||codePoint==2545) return makeFamily("as", {});
        return makeFamily("bn", {"as","mni"});
    }
    if(codePoint>=2560&&codePoint<=2687) return makeFamily("pa", {});
    if(codePoint>=2688&&codePoint<=2815) return makeFamily("gu", {});
    if(codePoint>=2816&&codePoint<=2943) return makeFamily("or", {});
    if(codePoint>=2944&&codePoint<=3071) return makeFamily("ta", {"kn"});
    if(codePoint>=3072&&codePoint<=3199) return makeFamily("te", {});
    if(codePoint>=3200&&codePoint<=3327) return makeFamily("kn", {"tcy"});
    if(codePoint>=3328&&codePoint<=3455) return makeFamily("ml", {});
    if(codePoint>=3456&&codePoint<=3583) return makeFamily("si", {});
    if(codePoint>=3584&&codePoint<=3711) return makeFamily("th", {"nod","sou","tts"});
    if(codePoint>=3712&&codePoint<=3839) return makeFamily("lo", {"hnx"});
    if(codePoint>=3840&&codePoint<=4095) return makeFamily("bo", {"dz"});
    if((codePoint>=4096&&codePoint<=4255)||(codePoint>=43616&&codePoint<=43647)) return makeFamily("my", {"shn","kar","mnw"});
    if((codePoint>=4256&&codePoint<=4351)||(codePoint>=11520&&codePoint<=11567)) return makeFamily("ka", {"xmf","lzz"});
    if((codePoint>=4352&&codePoint<=4607)||(codePoint>=12592&&codePoint<=12687)||(codePoint>=43360&&codePoint<=43391)||(codePoint>=44032&&codePoint<=55215)||(codePoint>=55216&&codePoint<=55295))
        return makeFamily("ko", {});
    if((codePoint>=4608&&codePoint<=4991)||(codePoint>=4992&&codePoint<=5023)||(codePoint>=11648&&codePoint<=11743))
        return famFromArray(FAMILY_ETHIOPIC, sizeof(FAMILY_ETHIOPIC)/sizeof(FAMILY_ETHIOPIC[0]));
    if((codePoint>=6016&&codePoint<=6143)||(codePoint>=6624&&codePoint<=6655)) return makeFamily("km", {});
    if((codePoint>=6144&&codePoint<=6319)||(codePoint>=71264&&codePoint<=71295)) return makeFamily("mn", {"mnc","xal"});
    if(codePoint>=12288&&codePoint<=12351) return famFromArray(FAMILY_CJK, sizeof(FAMILY_CJK)/sizeof(FAMILY_CJK[0]));
    if((codePoint>=12352&&codePoint<=12447)||(codePoint>=110592&&codePoint<=110847)||(codePoint>=110848&&codePoint<=110895)) return makeFamily("ja", {});
    if((codePoint>=12448&&codePoint<=12543)||(codePoint>=12784&&codePoint<=12799)||(codePoint>=110896&&codePoint<=110959)) return makeFamily("ja", {"ain"});
    if((codePoint>=19968&&codePoint<=40959)||(codePoint>=13312&&codePoint<=19903)||(codePoint>=131072&&codePoint<=173791)||(codePoint>=173824&&codePoint<=177983)||
       (codePoint>=177984&&codePoint<=178207)||(codePoint>=178208&&codePoint<=183983)||(codePoint>=183984&&codePoint<=191471)||
       (codePoint>=196608&&codePoint<=201551)||(codePoint>=201552&&codePoint<=205743)) return famFromArray(FAMILY_CJK, sizeof(FAMILY_CJK)/sizeof(FAMILY_CJK[0]));
    if((codePoint>=126976&&codePoint<=127023)||(codePoint>=127744&&codePoint<=129535)||(codePoint>=129648&&codePoint<=129791)||(codePoint>=9728&&codePoint<=9983)||(codePoint>=9984&&codePoint<=10175))
        return makeFamily("zz", {});
    if((codePoint>=119808&&codePoint<=120831)||(codePoint>=8448&&codePoint<=8527)||(codePoint>=8704&&codePoint<=8959)) return makeFamily("zz", {});
    if(codePoint>=65536&&codePoint<=65663) return makeFamily("grc", {});
    if(codePoint>=66304&&codePoint<=66351) return makeFamily("la", {});
    if(codePoint>=66352&&codePoint<=66383) return makeFamily("got", {});
    if(codePoint>=66560&&codePoint<=66639) return makeFamily("en", {});
    if(codePoint>=67584&&codePoint<=67647) return makeFamily("grc", {});
    if(codePoint>=68096&&codePoint<=68191) return makeFamily("sa", {});
    if((codePoint>=77824&&codePoint<=78895)||(codePoint>=78896&&codePoint<=78943)) return makeFamily("egy", {});
    if((codePoint>=73728&&codePoint<=74751)||(codePoint>=74752&&codePoint<=74879)) return makeFamily("akk", {});
    return makeFamily("un", {});
}
static std::string familyLangForCp(int codePoint, const std::unordered_set<std::string>& okIso3){
    LangFamily family = familyForCp(codePoint);
    auto isRoutable=[&](const std::string& langCode){ return okIso3.count(toIso3(langCode)) > 0; };
    if(family.primary && isRoutable(family.primary)) return family.primary;
    int initialCapacity=(int)(family.members.size()/0.75)+1; if(initialCapacity<16) initialCapacity=16;
    for(const auto& member : javaHashSetOrder(family.members, initialCapacity)) if(isRoutable(member)) return member;
    return "";
}
static std::string scriptLangForCpFiltered(int codePoint, const std::unordered_set<std::string>& enabled,
                                       const std::unordered_set<std::string>& okIso3, std::string* logBuf){
    char hex[16]; snprintf(hex,sizeof(hex),"%x",codePoint);
    std::string res = enabled.empty() ? familyLangForCp(codePoint, okIso3) : familyLangForCpFiltered(codePoint, enabled);
    if(logBuf) *logBuf += std::string("[SCRIPT] a.e cp=U+")+hex+" → "+(res.empty()?"null":res)+"\n";
    return res;
}

// ==========================================================================
//  DETECTORS                   AutoTTS clsCLD2.d / libcld2.so getLanguage 0x6523c8
//  cld3DetectRaw takes useHints because the two call sites are hinted
//  differently: the span site hints and filters, the window site does neither
//  and lets clsCLD2.d decide. See docs/AUTOTTS_MAP.md.
// ==========================================================================
static std::string baseLanguageTag(const std::string& code){
    size_t cut = code.find_first_of("-_");
    std::string base = (cut == std::string::npos) ? code : code.substr(0, cut);
    for(char& ch : base) if(ch >= 'A' && ch <= 'Z') ch = (char)(ch - 'A' + 'a');
    return base;
}
// CLD3's model emits exactly six romanised tags; counted from
// task_context_params.cc's kLanguageNames, which holds 109 entries in total.
static bool isRomanisedTag(const std::string& code){
    return code=="bg-Latn" || code=="el-Latn" || code=="hi-Latn"
        || code=="ja-Latn" || code=="ru-Latn" || code=="zh-Latn";
}
// CLD2 says "un" for undetermined; CLD3 says "und"
// (NNetLanguageIdentifier::kUnknown[] = "und"). Nothing else in the app knows
// about "und" -- it is the one CLD3 tag out of 109 that IsoCodes cannot map --
// and the aggregate detector filters unknown with startsWith("un|"), which
// "und|1" does not match. So an undetermined span used to count as a real
// language under CLD3 and could win the aggregate, which is the exact outcome
// clsCLD2.f's two-candidate scan exists to prevent. Folding it to CLD3's own
// empty-result convention makes both callers fall back the way they already do
// for CLD2.
static bool isCld3Unknown(const std::string& code){
    return code == "und";
}
// useHints mirrors whether CLD2 is hinted at the call site: getLanguageSpans
// hands CLD2 a per-script language hint and then filters what comes back
// against the enabled list, while getLanguage hands it nothing at all and lets
// clsCLD2.d's own n.n() test and script-family fallback deal with the answer.
// Filtering here at the window site would take that decision away from the
// caller and make the two detectors disagree.
static std::string cld3DetectRaw(const std::string& utf8Text, bool* reliableOut, bool useHints){
    static std::mutex cld3Mutex;
    static chrome_lang_id::NNetLanguageIdentifier* cld3Identifier = nullptr;
    std::lock_guard<std::mutex> cld3Lock(cld3Mutex);
    if(!cld3Identifier) cld3Identifier = new chrome_lang_id::NNetLanguageIdentifier(0, 1024);
    // Built only when it is going to be read. `hinted` is used in exactly one
    // place -- the `useHints && !hinted.empty()` test below -- which
    // short-circuits when useHints is false, so at the WINDOW site every one of
    // these was a comma-split plus an unordered_set of up to 64 std::strings
    // that nothing ever looked at. That site is called once per 64-character
    // window of the utterance, so on a long text in auto mode it was the whole
    // list rebuilt hundreds of times to be thrown away.
    // This changes no answer: the guard already made the set unreachable when
    // useHints is false.
    std::unordered_set<std::string> hinted;
    if(useHints){
        const std::string hintList = currentLanguageHints();
        size_t hintStart = 0;
        while(hintStart < hintList.size()){
            size_t hintEnd = hintList.find(',', hintStart);
            if(hintEnd == std::string::npos) hintEnd = hintList.size();
            if(hintEnd > hintStart) hinted.insert(hintList.substr(hintStart, hintEnd - hintStart));
            hintStart = hintEnd + 1;
        }
    }
    if(useHints && !hinted.empty()){
        const std::vector<chrome_lang_id::NNetLanguageIdentifier::Result> ranked =
            cld3Identifier->FindTopNMostFreqLangs(utf8Text, 3);
        for(const auto& candidate : ranked){
            if(!candidate.is_reliable) continue;
            if(isRomanisedTag(candidate.language)) continue;
            if(hinted.find(baseLanguageTag(candidate.language)) == hinted.end()) continue;
            if(reliableOut) *reliableOut = true;
            return candidate.language;
        }
    }
    const chrome_lang_id::NNetLanguageIdentifier::Result result = cld3Identifier->FindLanguage(utf8Text);
    if(isRomanisedTag(result.language) || isCld3Unknown(result.language)){
        if(reliableOut) *reliableOut = false;
        return "";
    }
    if(reliableOut) *reliableOut = result.is_reliable;
    return result.language;
}
static std::string detectWindowLang(const std::string& utf8Text, bool useCld3){
    if(useCld3) {
        bool cld3Reliable = false;
        std::string cld3Raw = cld3DetectRaw(utf8Text, &cld3Reliable, false);
        if(!cld3Reliable) return "UNKNOWN";
        return cld3Raw.empty() ? "UNKNOWN" : cld3Raw;
    }
    // getLanguage (0x6523c8), which is what clsCLD2.d calls per 64-character
    // window. It passes no content-language hint, an EMPTY tld hint rather than
    // a null one, encoding_hint 0 (ISO_8859_1, not UNKNOWN_ENCODING), and
    // is_plain_text from CLD2::FLAGS_plain -- a flag nothing in the app ever
    // sets, so false.
    CLD2::CLDHints hints = {nullptr, "", 0, CLD2::UNKNOWN_LANGUAGE};
    CLD2::Language lang3[3]; int percent3[3]; double score3[3];
    int textBytes=0; bool reliable=false;
    CLD2::DetectLanguageSummaryV2(utf8Text.c_str(), (int)utf8Text.size(), false, &hints,
                                  true, 0x4000, CLD2::UNKNOWN_LANGUAGE,
                                  lang3, percent3, score3, nullptr, &textBytes, &reliable);
    if(!reliable) return "UNKNOWN";
    const char* code = CLD2::LanguageCode(lang3[0]);
    return code ? std::string(code) : "UNKNOWN";
}

// ==========================================================================
//  LANGUAGE HINTS              libcld2.so setLanguageHints 0x653588
//  setLanguageHints does more than remember the list: it derives two per-script
//  tables that steer CLD2. The hint survives only where exactly one enabled
//  language uses that script; the fallback is the first one that does.
// ==========================================================================
static std::mutex languageHintMutex;
static std::string languageHintList;
// AutoTTS's native setLanguageHints (0x653588) keeps the codes lowercased and
// at most 64 of them, and then derives two per-script tables that
// getLanguageSpans steers CLD2 with. Both are 40 entries -- the script codes it
// dispatches on stop at 39 -- and both start as UNKNOWN_LANGUAGE.
static const int kScriptHintSlots = 40;
static std::vector<std::string> languageHintCodes;
static int scriptLanguageHint[kScriptHintSlots];
static int scriptLanguageFallback[kScriptHintSlots];
// The static table at 0x62f924: 48 {script, candidate language} pairs, in file
// order, because the order is what decides which candidate becomes the
// fallback. Only the six scripts CLD2 is ever asked about appear.
static const struct { int script; CLD2::Language lang; } kScriptLangPairs[48] = {
    { 3, CLD2::ARABIC},   { 3, CLD2::PERSIAN},    { 3, CLD2::URDU},
    { 3, CLD2::PASHTO},   { 3, CLD2::SINDHI},
    { 2, CLD2::RUSSIAN},  { 2, CLD2::UKRAINIAN},  { 2, CLD2::BULGARIAN},
    { 2, CLD2::SERBIAN},  { 2, CLD2::MACEDONIAN}, { 2, CLD2::BELARUSIAN},
    { 2, CLD2::MONGOLIAN},{ 2, CLD2::TAJIK},      { 2, CLD2::KAZAKH},
    { 2, CLD2::KYRGYZ},
    { 4, CLD2::HINDI},    { 4, CLD2::MARATHI},    { 4, CLD2::NEPALI},
    { 4, CLD2::SANSKRIT},
    { 6, CLD2::BENGALI},  { 6, CLD2::ASSAMESE},
    { 5, CLD2::CHINESE},  { 5, CLD2::CHINESE_T},  { 5, CLD2::JAPANESE},
    { 1, CLD2::ENGLISH},  { 1, CLD2::SPANISH},    { 1, CLD2::FRENCH},
    { 1, CLD2::PORTUGUESE},{ 1, CLD2::GERMAN},    { 1, CLD2::ITALIAN},
    { 1, CLD2::VIETNAMESE},{ 1, CLD2::INDONESIAN},{ 1, CLD2::TURKISH},
    { 1, CLD2::DUTCH},    { 1, CLD2::POLISH},     { 1, CLD2::ROMANIAN},
    { 1, CLD2::CZECH},    { 1, CLD2::HUNGARIAN},  { 1, CLD2::SWEDISH},
    { 1, CLD2::DANISH},   { 1, CLD2::NORWEGIAN},  { 1, CLD2::FINNISH},
    { 1, CLD2::SLOVAK},   { 1, CLD2::CROATIAN},   { 1, CLD2::MALAY},
    { 1, CLD2::TAGALOG},  { 1, CLD2::SWAHILI},    { 1, CLD2::AFRIKAANS}
};
// Caller holds languageHintMutex. This is the second half of setLanguageHints.
static void rebuildScriptLanguageTables(){
    for(int slot=0; slot<kScriptHintSlots; slot++){
        scriptLanguageHint[slot] = CLD2::UNKNOWN_LANGUAGE;
        scriptLanguageFallback[slot] = CLD2::UNKNOWN_LANGUAGE;
    }
    if(languageHintCodes.empty()) return;
    int matched[kScriptHintSlots];
    for(int slot=0; slot<kScriptHintSlots; slot++) matched[slot] = 0;
    for(int pair=0; pair<48; pair++){
        const char* code = CLD2::LanguageCode(kScriptLangPairs[pair].lang);
        if(!code) continue;
        bool hinted = false;
        for(size_t at=0; at<languageHintCodes.size(); at++){
            if(languageHintCodes[at] == code){ hinted = true; break; }
        }
        if(!hinted) continue;
        int slot = kScriptLangPairs[pair].script;
        if(slot < 0 || slot >= kScriptHintSlots) continue;
        scriptLanguageHint[slot] = (int)kScriptLangPairs[pair].lang;
        matched[slot]++;
        if(scriptLanguageFallback[slot] == CLD2::UNKNOWN_LANGUAGE)
            scriptLanguageFallback[slot] = (int)kScriptLangPairs[pair].lang;
    }
    // The language hint is kept only where exactly one enabled language uses
    // that script; anywhere else it goes back to UNKNOWN.
    for(int slot=0; slot<kScriptHintSlots; slot++)
        if(matched[slot] != 1) scriptLanguageHint[slot] = CLD2::UNKNOWN_LANGUAGE;
}
static std::string currentLanguageHints(){
    std::lock_guard<std::mutex> lock(languageHintMutex);
    return languageHintList;
}
static std::vector<std::string> currentLanguageHintCodes(){
    std::lock_guard<std::mutex> lock(languageHintMutex);
    return languageHintCodes;
}
// Which of the six scripts a language belongs to, per the same 48 pairs, or -1
// when the table does not mention it.
//
// This exists for the CLD3 arm of the span site only. CLD2 is steered by
// scriptLanguageHint, which goes INTO the detector, so it rarely names a
// language of the wrong script for a span; CLD3 has no hints API and can only
// be filtered afterwards, against a flat enabled-language list that knows
// nothing about script. That is how a reliable "sr" or "ja" could win a Latin
// span -- see INVARIANTS #16.
//
// The table is NOT a complete script classification: Latin lists 24 languages
// and has no Catalan or Basque, and CJK has no Korean. So "absent" must mean
// "no evidence", never "wrong script" -- answering -1 here and having the caller
// accept the language is the whole point. Only a language the table places under
// a DIFFERENT script is a mismatch we can prove.
static int scriptOfLanguageCode(const std::string& code){
    if(code.empty()) return -1;
    for(int pair=0; pair<48; pair++){
        const char* listed = CLD2::LanguageCode(kScriptLangPairs[pair].lang);
        if(listed && code == listed) return kScriptLangPairs[pair].script;
    }
    return -1;
}
static int currentScriptLanguageHint(int script){
    std::lock_guard<std::mutex> lock(languageHintMutex);
    if(script < 0 || script >= kScriptHintSlots) return CLD2::UNKNOWN_LANGUAGE;
    return scriptLanguageHint[script];
}
static int currentScriptLanguageFallback(int script){
    std::lock_guard<std::mutex> lock(languageHintMutex);
    if(script < 0 || script >= kScriptHintSlots) return CLD2::UNKNOWN_LANGUAGE;
    return scriptLanguageFallback[script];
}
extern "C" JNIEXPORT void JNICALL

// ==========================================================================
//  JNI: the remaining entry points
// ==========================================================================
Java_com_tts_easyvoice_NativeEngine_setLanguageHints(JNIEnv* env, jclass, jobjectArray jLangs){
    std::vector<std::string> codes;
    if(jLangs){
        jsize count = env->GetArrayLength(jLangs);
        for(jsize i=0;i<count && codes.size()<64;i++){
            jstring jLang = (jstring)env->GetObjectArrayElement(jLangs, i);
            if(!jLang) continue;
            const char* langC = env->GetStringUTFChars(jLang, nullptr);
            if(langC){
                // 0x6535f4: `sub x8, x0, #0x8 / cmn x8, #0x7 / b.lo <skip>`,
                // i.e. a code is kept only when 1 <= strlen <= 7 -- it has to
                // fit an 8-byte slot with its NUL. A longer one is SKIPPED, not
                // truncated. Then each byte is copied with A-Z folded to
                // lower case, and the loop stops once 64 have been kept
                // (0x65373c: `cmp w8, #0x40 / b.lt <continue>`).
                std::string one(langC);
                if(one.size() >= 1 && one.size() <= 7){
                    for(size_t at=0; at<one.size(); at++)
                        if(one[at] >= 'A' && one[at] <= 'Z') one[at] = (char)(one[at] | 0x20);
                    codes.push_back(one);
                }
                env->ReleaseStringUTFChars(jLang, langC);
            }
            env->DeleteLocalRef(jLang);
        }
    }
    // The comma list is what the CLD3 arm filters its candidates by, so it is
    // built from the SAME accepted codes rather than from the raw array: the
    // two detectors have to be steered by one list, or the switch changes more
    // than which detector runs.
    std::string joined;
    for(size_t at=0; at<codes.size(); at++){ if(at) joined += ","; joined += codes[at]; }
    std::lock_guard<std::mutex> lock(languageHintMutex);
    languageHintList = joined;
    languageHintCodes = codes;
    rebuildScriptLanguageTables();
}
static std::unordered_set<std::string> jStringArrayToSet(JNIEnv* env, jobjectArray arr){
    std::unordered_set<std::string> out;
    if(!arr) return out;
    jsize count = env->GetArrayLength(arr);
    for(jsize i=0;i<count;i++){
        jstring elem=(jstring)env->GetObjectArrayElement(arr,i);
        if(!elem) continue;
        const char* chars=env->GetStringUTFChars(elem,nullptr);
        out.insert(std::string(chars));
        env->ReleaseStringUTFChars(elem,chars);
        env->DeleteLocalRef(elem);
    }
    return out;
}
extern "C" JNIEXPORT void JNICALL
Java_com_tts_easyvoice_NativeEngine_setIsoMap(JNIEnv* env, jclass, jobjectArray jIso2, jobjectArray jIso3){
    if(!jIso2||!jIso3) return;
    jsize iso2Count=env->GetArrayLength(jIso2); jsize iso3Count=env->GetArrayLength(jIso3);
    if(iso3Count<iso2Count) iso2Count=iso3Count;
    for(jsize i=0;i<iso2Count;i++){
        jstring jIso2Element=(jstring)env->GetObjectArrayElement(jIso2,i);
        jstring jIso3Element=(jstring)env->GetObjectArrayElement(jIso3,i);
        if(jIso2Element&&jIso3Element){
            const char* iso2Chars=env->GetStringUTFChars(jIso2Element,nullptr);
            const char* iso3Chars=env->GetStringUTFChars(jIso3Element,nullptr);
            g_iso2to3[std::string(iso2Chars)]=std::string(iso3Chars);
            env->ReleaseStringUTFChars(jIso2Element,iso2Chars); env->ReleaseStringUTFChars(jIso3Element,iso3Chars);
        }
        if(jIso2Element) env->DeleteLocalRef(jIso2Element); if(jIso3Element) env->DeleteLocalRef(jIso3Element);
    }
}
extern "C" JNIEXPORT void JNICALL
Java_com_tts_easyvoice_NativeEngine_setDetectSets(JNIEnv* env, jclass, jobjectArray jDetectOk, jobjectArray jEnabled){
    std::unordered_set<std::string> ok = jStringArrayToSet(env, jDetectOk);
    std::unordered_set<std::string> enabled = jStringArrayToSet(env, jEnabled);
    std::lock_guard<std::mutex> lock(detectSetMutex);
    detectOkIso3Set = std::move(ok);
    enabledLangSet = std::move(enabled);
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_tts_easyvoice_NativeEngine_detectLanguageFull(
    JNIEnv* env, jclass, jstring jText, jstring jLat, jstring jNonLat,
    jboolean jDisableAdv, jboolean jWantLog, jboolean jUseCld3)
{
    bool useCld3 = (jUseCld3==JNI_TRUE);
    if(!jText) return env->NewStringUTF("UNKNOWN\x01");
    const jchar* chars = env->GetStringChars(jText, nullptr);
    int textLen = (int)env->GetStringLength(jText);
    const char* latC = jLat?env->GetStringUTFChars(jLat,nullptr):nullptr; std::string latinFallback(latC?latC:""); if(latC) env->ReleaseStringUTFChars(jLat,latC);
    const char* nlC = jNonLat?env->GetStringUTFChars(jNonLat,nullptr):nullptr; std::string nonLatinFallback(nlC?nlC:""); if(nlC) env->ReleaseStringUTFChars(jNonLat,nlC);
    bool disableAdvanced = (jDisableAdv==JNI_TRUE);
    std::unordered_set<std::string> okIso3, enabled;
    { std::lock_guard<std::mutex> lock(detectSetMutex); okIso3 = detectOkIso3Set; enabled = enabledLangSet; }
    std::string log; std::string* logBuf = (jWantLog==JNI_TRUE) ? &log : nullptr;
    std::string result = "UNKNOWN"; bool done=false;
    int winStart=0;
    while(winStart<textLen && !done){
        int windowEndRaw=winStart+64; int windowEnd = windowEndRaw<textLen?windowEndRaw:textLen;
        std::string windowUtf8 = utf16to8(chars+winStart, windowEnd-winStart);
        if(logBuf){
            int snippetLen = (windowEnd-winStart)<30?(windowEnd-winStart):30;
            char nbuf[32]; snprintf(nbuf,sizeof(nbuf),"%d",windowEnd-winStart);
            *logBuf += std::string(useCld3?"[CLD3] cld3DetectWindow windowLen=":"[CLD2] cld2DetectWindow windowLen=")+nbuf+" latFall="+latinFallback+" nonLatFall="+nonLatinFallback+" isDisableAdv="+(disableAdvanced?"true":"false")+" snip='"+utf16to8(chars+winStart,snippetLen)+"'\n";
        }
        std::string detectedLang = detectWindowLang(windowUtf8, useCld3);
        if(logBuf) *logBuf += std::string(useCld3?"[CLD3] cld3DetectWindow rawOut='":"[CLD2] cld2DetectWindow rawOut='")+(detectedLang.empty()?"UNKNOWN":detectedLang)+"|"+windowUtf8+"' → result="+(detectedLang.empty()?"null":detectedLang)+"\n";
        if(!detectedLang.empty() && detectedLang!="UNKNOWN"){
            if(disableAdvanced){ result=detectedLang; done=true; break; }
            if(okIso3.count(toIso3(detectedLang))){ result=detectedLang; done=true; break; }
            int windowFirstCp = firstValidCodePointU16(chars+winStart, windowEnd-winStart);
            if(windowFirstCp!=-1){
                std::string scriptLang = scriptLangForCpFiltered(windowFirstCp, enabled, okIso3, logBuf);
                if(!scriptLang.empty()){ result=scriptLang; done=true; break; }
            }
        }
        winStart=windowEndRaw;
    }
    if(!done && !disableAdvanced){
        int firstCp = firstValidCodePointU16(chars, textLen);
        if(firstCp!=-1){
            std::string scriptLang = scriptLangForCpFiltered(firstCp, enabled, okIso3, logBuf);
            if(!scriptLang.empty()) result=scriptLang;
        }
    }
    env->ReleaseStringChars(jText, chars);
    std::string out = result + "\x01" + log;
    return env->NewStringUTF(out.c_str());
}
// clsCLD2.b, for the Kotlin side. clsCLD2.e and the new clsCLD2.f both call
// it before anything else, and e()'s quick-character test measures the
// NORMALISED string, so the fold has to be visible there rather than hidden
// inside nativeGetLanguages.
extern "C" JNIEXPORT jstring JNICALL
Java_com_tts_easyvoice_NativeEngine_normalizeFancy(JNIEnv* env, jclass, jstring jText){
    if(!jText) return env->NewStringUTF("");
    const char* chars = env->GetStringUTFChars(jText, nullptr);
    std::string text(chars ? chars : "");
    if(chars) env->ReleaseStringUTFChars(jText, chars);
    return env->NewStringUTF(normalizeFancyText(text).c_str());
}
extern "C" JNIEXPORT jobjectArray JNICALL

// ==========================================================================
//  JNI: nativeGetLanguages     libcld2.so getLanguageSpans 0x65392c
//  Script 0 -- a run with nothing classified: digits, punctuation, spaces,
//  emoji -- answers "un" with latin = TRUE and never reaches a detector. That
//  is why a bare number is read in the LATIN preferred language.
// ==========================================================================
Java_com_tts_easyvoice_NativeEngine_nativeGetLanguages(JNIEnv* env, jclass, jstring jText, jboolean jUseCld3){
    jclass stringClass=env->FindClass("java/lang/String");
    if(!jText) return env->NewObjectArray(0, stringClass, nullptr);
    const char* textChars=env->GetStringUTFChars(jText,nullptr); std::string text(textChars?textChars:""); env->ReleaseStringUTFChars(jText,textChars);
    const bool spanUseCld3 = (jUseCld3 == JNI_TRUE);
    struct ScriptSpan { int offset; int bytes; std::string lang; bool latin; };
    std::vector<ScriptSpan> spans;
    const int kMaxSpans = 128;
    auto classifyScript = [](int codePoint) -> int {
        // 0x653ae8: `and w8, w8, #0x5f / sub w8, w8, #0x5b / cmn w8, #0x1a /
        // b.lo 0x653b88`. b.lo is taken when the folded byte is OUTSIDE
        // [0x41, 0x5A], and 0x653b88 is `mov w27, w3` -- keep the current
        // script. A letter falls through to 0x653b78, `mov w27, #1` = Latin.
        // So a letter means Latin and anything else means "no information",
        // exactly like the ASCII fast path in the caller. This branch is
        // reachable only through an OVERLONG UTF-8 sequence, which is not
        // hypothetical: JNI hands out modified UTF-8, where U+0000 is C0 80.
        if (codePoint < 0x80) {
            int folded = codePoint & 0x5F;
            return (folded >= 0x41 && folded <= 0x5A) ? 1 : -1;
        }
        if (codePoint < 0xC0) return -1;
        if (codePoint < 0x2B0) return 1;
        if ((unsigned)(codePoint - 0x370) < 0x90)  return 7;
        if ((unsigned)(codePoint - 0x400) < 0x130) return 2;
        if ((unsigned)(codePoint - 0x530) < 0x60)  return 8;
        if ((unsigned)(codePoint - 0x590) < 0x70)  return 9;
        if ((unsigned)(codePoint - 0x8A0) < 0x60)  return 3;
        if ((codePoint & 0x1FFF00) == 0x600) return 3;
        if ((unsigned)(codePoint - 0x750) < 0x30)  return 3;
        // 5.7.7.26: `and w10, w8, #0x1ffffe / cmp w10, #0x964 / b.eq` in
        // getLanguageSpans, branching to the same `mov w27, w3` that a code
        // point below 0xC0 jumps to. The mask makes one compare cover U+0964
        // and U+0965, the danda and double danda, and the target is the neutral
        // path -- so a danda no longer counts as Devanagari.
        if ((codePoint & 0x1FFFFE) == 0x964) return -1;
        unsigned indicBlock = ((unsigned)(codePoint - 0x900)) >> 7;
        if (indicBlock <= 11) {
            static const int kIndic[12] = {4,6,11,12,13,14,15,16,17,18,19,20};
            return kIndic[indicBlock];
        }
        if ((codePoint & 0x1FFF00) == 0xF00)  return 21;
        if ((unsigned)(codePoint - 0x1000) < 0xA0)  return 22;
        if ((unsigned)(codePoint - 0x10A0) < 0x60)  return 10;
        if ((codePoint & 0x1FFF00) == 0x1100) return 25;
        if ((unsigned)(codePoint - 0x1200) < 0x180) return 24;
        if ((codePoint & 0x1FFF80) == 0x1780) return 23;
        if ((codePoint & 0x1FFF00) == 0x1E00) return 1;
        if ((codePoint & 0x1FFFE0) == 0x2C60) return 1;
        if ((unsigned)(codePoint - 0x2E80) < 0x160) return 5;
        if ((unsigned)(codePoint - 0x3040) < 0xC0)  return 5;
        if ((unsigned)(codePoint - 0x3130) < 0x60)  return 25;
        if ((codePoint >> 4) == 0x31F)        return 5;
        if ((((unsigned)(codePoint - 0x3400)) >> 10) < 0x1B) return 5;
        if ((unsigned)(codePoint - 0xA720) < 0xE0)  return 1;
        if ((codePoint & 0x1FFFE0) == 0xA960) return 25;
        if ((((unsigned)(codePoint - 0xAC00)) >> 10) < 0xB) return 25;
        if ((unsigned)(codePoint - 0xF900) < 0x200) return 5;
        if ((unsigned)(codePoint - 0xFB50) < 0x2B0) return 3;
        if ((unsigned)(codePoint - 0xFE70) < 0x90)  return 3;
        if ((unsigned)(codePoint - 0xFF66) < 0x38)  return 5;
        if ((((unsigned)(codePoint - 0x20000)) >> 5) < 0x7D1) return 5;
        return -1;
    };

    // The utterance's text in THIS span's script, for the CLD3 arm only.
    //
    // Returns "" -- meaning "no usable context, detect the span alone" -- when
    // the stored context does not contain the span. That is the staleness
    // guard: the context is written by buildMixChunks, and nativeGetLanguages
    // is also reached from the auto/Google aggregate detector, which never runs
    // the chunk builder. A context left over from an earlier utterance cannot
    // contain this span, so it is refused rather than used.
    //
    // ASCII letters are matched directly because the scanner classifies them in
    // its own fast path (`& 0x5F`, then A..Z) and never asks classifyScript;
    // everything else goes through classifyScript, which is the same ladder the
    // span boundaries were drawn with. Spaces are kept so words stay separated
    // and n-grams do not run together; digits and punctuation are dropped
    // because they carry no language signal.
    auto sameScriptContextText = [&](int wantScript, const std::string& span) -> std::string {
        const std::string context = currentDetectContext();
        if (context.empty()) return std::string();
        if (context.find(span) == std::string::npos) return std::string();
        std::string out;
        out.reserve(context.size());
        size_t at = 0;
        bool lastWasSpace = true;
        while (at < context.size()) {
            int cpLen = 0;
            const int codePoint = utf8ToCodepoint((const unsigned char*)context.c_str() + at, cpLen);
            if (cpLen <= 0) break;
            bool keep = false;
            if (codePoint < 0x80) {
                const int folded = codePoint & 0x5F;
                keep = (wantScript == 1 && folded >= 0x41 && folded <= 0x5A);
            } else {
                keep = (classifyScript(codePoint) == wantScript);
            }
            if (keep) { out.append(context, at, (size_t)cpLen); lastWasSpace = false; }
            else if (!lastWasSpace) { out += ' '; lastWasSpace = true; }
            at += (size_t)cpLen;
        }
        return out;
    };

    static const char* SCRIPT_FIXED_LANG[19] = {
        "el",
        "hy",
        "iw",
        "ka",
        "pa",
        "gu",
        "or",
        "ta",
        "te",
        "kn",
        "ml",
        "si",
        "th",
        "lo",
        "bo",
        "my",
        "km",
        "am",
        "ko"
    };
    auto emitScriptSpan = [&](int start, int len, int script) {
        if (len < 1) return;
        if (start < 0 || start + len > (int)text.size()) return;
        std::string lang;
        // getLanguageSpans dispatches on the script through a 26-entry jump
        // table (0x62f8f0; `cmp w3, #0x19 / b.hi` sends anything above 25 to
        // CLD2), and script 0 -- nothing classified, i.e. a run made only of
        // digits, ASCII punctuation, spaces or emoji -- has a case of its own:
        //
        //     653ef0: mov  w20, #0x1        ; latin = TRUE
        //     653ef8: adrp x19, <"un">      ; language = "un"
        //     653f08: b.lt 0x6544c0         ; one past the cset below
        //     6544b8: cmp  w3, #0x1
        //     6544bc: cset w20, eq          ; latin = (script == 1)
        //
        // so it never reaches CLD2 and never reaches that cset. It matters
        // because the caller resolves such a span with `run.b ? P : Q`: AutoTTS
        // speaks a bare number, a bare punctuation run or a bare emoji with the
        // LATIN preferred language. Detecting it and leaving latin false sent it
        // to the NON-Latin one instead -- a different voice for every standalone
        // number a screen reader announces.
        //
        // Script 0 can only be the single final span of a text in which nothing
        // was classified: the two mid-text emits fire on `curScript >= 2` and on
        // `curScript != 0 && stringClass != curScript`, and neither can pass 0.
        // The case still branches to 0x6544c0, which IS the merge, cap and push
        // code below, so only the language and the flag are settled early.
        bool latin = (script == 1);
        unsigned scriptIdx = (unsigned)(script - 7);
        if (script == 0) {
            // ...but "un" is only where case 0 STARTS. It then consults the
            // LATIN fallback, hard-coded to slot 1 of the same table the CLD2
            // path uses:
            //
            //     653f00: ldr  w8, [x8, #0x2b4]   ; hint count
            //     653f04: cmp  w8, #0x1
            //     653f08: b.lt 0x6544c0           ; no hints -> keep "un"
            //     653f10: ldr  w0, [x8, #0x564]   ; scriptLanguageFallback[1]
            //     653f14: cmp  w0, #0x1a          ; UNKNOWN_LANGUAGE
            //     653f18: b.eq 0x6544c0           ; none -> keep "un"
            //     653f34: bl   CLD2::LanguageCode
            //     653f50: mov  x19, x0            ; language = that code
            //
            // so a run of nothing but digits, punctuation, spaces or emoji is
            // named after the first enabled Latin language rather than left
            // undetermined -- and latin stays true either way. It matters
            // because the caller resolves an unnamed span with the PREFERRED
            // Latin language while a named one goes through c3.e.c and the
            // engine check first.
            lang = "un";
            latin = true;
            if(!currentLanguageHintCodes().empty()){
                const int latinFallback = currentScriptLanguageFallback(1);
                if(latinFallback != CLD2::UNKNOWN_LANGUAGE){
                    const char* latinFallbackCode = CLD2::LanguageCode((CLD2::Language)latinFallback);
                    if(latinFallbackCode) lang = latinFallbackCode;
                }
            }
        } else if (scriptIdx < 19) {
            lang = SCRIPT_FIXED_LANG[scriptIdx];
        } else {
            int detectBytes = len;
            if (detectBytes > 1024) {
                detectBytes = 1024;
                while (detectBytes > 0 && ((unsigned char)text[start + detectBytes] & 0xC0) == 0x80) detectBytes--;
            }
            // The hint list and the per-script tables belong to BOTH detectors.
            // CLD3 cannot be given a CLD2 Language as a hint, but the filter --
            // keep an answer only if the user has that language enabled, and
            // otherwise fall back to the one language this script implies -- is
            // the part that decides the voice, so both arms run it.
            const std::vector<std::string> hintCodes = currentLanguageHintCodes();
            auto isHinted = [&](const std::string& candidate) -> bool {
                if(candidate.empty()) return false;
                for(size_t at=0; at<hintCodes.size(); at++)
                    if(hintCodes[at] == candidate) return true;
                return false;
            };
            auto scriptFallbackCode = [&]() -> std::string {
                const int fallback = currentScriptLanguageFallback(script);
                if(fallback == CLD2::UNKNOWN_LANGUAGE) return "";
                const char* fallbackCode = CLD2::LanguageCode((CLD2::Language)fallback);
                return fallbackCode ? std::string(fallbackCode) : std::string();
            };
            if (spanUseCld3) {
                // cld3DetectRaw already keeps the first reliable candidate of
                // its own top 3 that the hints allow, which is CLD2's first
                // three tests. The fourth -- the per-script fallback -- has no
                // CLD3 counterpart, so it is applied here; without it the two
                // detectors answer differently for the same text, and the
                // Kotlin's engine check then sends the run to P or Q instead.
                //
                // The reliability flag MUST be asked for and honoured. Both
                // arms of detectWindowLang answer "UNKNOWN" when the detector
                // is not sure, and this span site is the one place that used to
                // pass nullptr and drop the answer on the floor. What that cost
                // is on record: for "MEET Choudhary " CLD3's top-3 loop rejects
                // its own candidate (hi, is_reliable = 0, p = 0.495), falls
                // through to FindLanguage(), and returned that same unreliable
                // "hi" anyway -- so a Latin name was spoken by the Hindi voice
                // while CLD2, steered by the per-script hint, read it in
                // English. Unreliable now becomes "un", which is not in the
                // hint list, so the per-script fallback below resolves the span
                // to the one language its script implies.
                //
                // The answer must also belong to THIS span's script. CLD2 gets
                // that for free: its per-script hint is fed into the detector,
                // so for a Latin span with one enabled Latin language it is
                // told what to expect. The CLD3 arm can only filter afterwards,
                // and the enabled list it filters against is flat -- so a
                // reliable "sr" or "ja" would otherwise win a Latin span merely
                // because the user has Serbian or Japanese enabled. Measured
                // over 536 Latin strings from the reporter's own log: 19 such
                // spans with {en, sr} and 4 with {en, ja}.
                //
                // scriptOfLanguageCode answers -1 for a language the table does
                // not list, and -1 is ACCEPTED. The table names 24 Latin
                // languages and no Catalan, and no Korean at all, so treating
                // "absent" as "wrong" would break far more than it fixed. Only
                // a language the table places under a different script is
                // rejected, and then the per-script fallback below resolves the
                // span the way CLD2 would have.
                //
                // THE SPAN IS WIDENED BEFORE IT IS DETECTED (owner request,
                // 2026-09-02). CLD3 is a neural net and it is unreliable on a
                // short fragment, which is exactly what this site hands it:
                // buildMixChunks has already cut the utterance into per-script
                // chunks, so one Devanagari sentence arrives as several 3-to-56
                // byte pieces. Measured on the reported sentence with
                // {en,gu,hi,mr} enabled:
                //
                //     the 56-byte tail alone                    -> mr p=0.712
                //     the same tail with the utterance's
                //     Devanagari in front of it                 -> hi p=1.0000
                //
                // Same model, same enabled set, same span -- only the amount of
                // text changed. So a span shorter than CLD3's OWN documented
                // minimum is detected against the utterance's text in this
                // span's script. 140 is not a number of ours:
                // NNetLanguageIdentifier::kMinNumBytesToConsider is 140, and we
                // construct the identifier with 0 precisely so that short spans
                // still get an answer rather than "und".
                //
                // The span itself is still what gets the answer -- only the
                // evidence is wider. CLD2's arm below is untouched: it does not
                // need this, and it has to stay byte-for-byte AutoTTS.
                bool cld3Reliable = false;
                std::string cld3Text(text, start, detectBytes);
                if ((int)cld3Text.size() < chrome_lang_id::NNetLanguageIdentifier::kMinNumBytesToConsider) {
                    const std::string wider = sameScriptContextText(script, cld3Text);
                    if (wider.size() > cld3Text.size()) cld3Text = wider;
                }
                std::string cld3Lang = cld3DetectRaw(cld3Text, &cld3Reliable, true);
                const int cld3Script = scriptOfLanguageCode(baseLanguageTag(cld3Lang));
                const bool wrongScript = (cld3Script >= 0 && cld3Script != script);
                lang = (cld3Lang.empty() || !cld3Reliable || wrongScript) ? "un" : cld3Lang;
                if(!hintCodes.empty() && !isHinted(baseLanguageTag(lang))){
                    const std::string fallbackCode = scriptFallbackCode();
                    if(!fallbackCode.empty()) lang = fallbackCode;
                }
            } else {
                // AutoTTS passes NO content-language hint here. What it passes
                // is the per-script language hint setLanguageHints derived, and
                // it then filters the answer against the hint list -- see
                // 0x65400c (the two NULL pointers), 0x654030 (the table read)
                // and 0x654098 onwards (the filter).
                const int scriptHint = currentScriptLanguageHint(script);
                CLD2::CLDHints hints = {nullptr, nullptr, CLD2::UNKNOWN_ENCODING,
                                        hintCodes.empty() ? CLD2::UNKNOWN_LANGUAGE
                                                          : (CLD2::Language)scriptHint};
                CLD2::Language lang3[3]; int percent3[3]; double score3[3];
                int textBytes = 0; bool reliable = false;
                CLD2::Language cldLang = CLD2::ExtDetectLanguageSummary(text.c_str() + start, detectBytes, true, &hints, 0x4000,
                                           lang3, percent3, score3, nullptr, &textBytes, &reliable);
                const char* code = CLD2::LanguageCode(cldLang);
                lang = code ? std::string(code) : "un";
                if(!hintCodes.empty() && !isHinted(lang)){
                    bool picked = false;
                    // lang3[0], [1] then [2] -- the compiler unrolled the loop
                    // into three identical blocks at 0x654190, 0x6542ac and
                    // 0x654380, reading language3[0..2] from x29-0x14/-0x10/-0xc
                    // and percent3[0..2] from x29-0x20/-0x1c/-0x18. Rank 0 is
                    // NOT a repeat of the summary above it: CalcSummaryLang
                    // returns language3[active_slot[1]] when it decides the top
                    // answer is English or FIGS boilerplate, and
                    // UNKNOWN_LANGUAGE when the top language covers too little
                    // of the text -- so the summary and language3[0] genuinely
                    // differ, and skipping rank 0 sent those spans to the
                    // per-script fallback instead of the language CLD2 ranked
                    // first. Each rank counts only when it is a real language
                    // and covers at least one percent of the text.
                    for(int rank=0; rank<3 && !picked; rank++){
                        if(lang3[rank] == CLD2::UNKNOWN_LANGUAGE) continue;
                        if(percent3[rank] < 1) continue;
                        const char* other = CLD2::LanguageCode(lang3[rank]);
                        if(!other) continue;
                        if(!isHinted(other)) continue;
                        lang = other;
                        picked = true;
                    }
                    if(!picked){
                        const std::string fallbackCode = scriptFallbackCode();
                        if(!fallbackCode.empty()) lang = fallbackCode;
                    }
                }
            }
        }
        if (!spans.empty()
            && spans.back().offset + spans.back().bytes == start
            && spans.back().latin == latin
            && spans.back().lang == lang) {
            spans.back().bytes += len;
            return;
        }
        if ((int)spans.size() >= kMaxSpans) {
            if (!spans.empty()) spans.back().bytes = (start + len) - spans.back().offset;
            return;
        }
        ScriptSpan newSpan;
        newSpan.offset = start; newSpan.bytes = len;
        newSpan.lang = lang;
        newSpan.latin = latin;
        spans.push_back(newSpan);
    };
    {
        int textLen = (int)text.size();
        int spanStart = 0, curScript = 0, i = 0;
        while (i < textLen) {
            signed char signedByte = (signed char)text[i];
            if (signedByte >= 0) {
                int folded = ((int)(unsigned char)text[i]) & 0x5F;
                if (folded < 0x41 || folded > 0x5A) { i++; continue; }
                if (curScript >= 2) { emitScriptSpan(spanStart, i - spanStart, curScript); spanStart = i; }
                curScript = 1;
                i++;
                continue;
            }
            int codePoint, cpLen;
            const unsigned char* bytes = (const unsigned char*)text.data() + i;
            int availableBytes = textLen - i;
            if (availableBytes >= 2 && (bytes[0] & 0xE0) == 0xC0 && (bytes[1] & 0xC0) == 0x80) {
                codePoint = ((bytes[0] & 0x1F) << 6) | (bytes[1] & 0x3F); cpLen = 2;
            } else if (availableBytes >= 3 && (bytes[0] & 0xF0) == 0xE0 && (bytes[1] & 0xC0) == 0x80 && (bytes[2] & 0xC0) == 0x80) {
                codePoint = ((bytes[0] & 0x0F) << 12) | ((bytes[1] & 0x3F) << 6) | (bytes[2] & 0x3F); cpLen = 3;
            } else if (availableBytes >= 4 && (bytes[0] & 0xF8) == 0xF0 && (bytes[1] & 0xC0) == 0x80 && (bytes[2] & 0xC0) == 0x80 && (bytes[3] & 0xC0) == 0x80) {
                codePoint = ((bytes[0] & 0x07) << 18) | ((bytes[1] & 0x3F) << 12) | ((bytes[2] & 0x3F) << 6) | (bytes[3] & 0x3F); cpLen = 4;
            } else {
                codePoint = 0xFFFD; cpLen = 1;
            }
            int stringClass = classifyScript(codePoint);
            if (stringClass < 0) stringClass = curScript;
            else if (curScript != 0 && stringClass != curScript) { emitScriptSpan(spanStart, i - spanStart, curScript); spanStart = i; }
            i += cpLen;
            curScript = stringClass;
        }
        emitScriptSpan(spanStart, textLen - spanStart, curScript);
    }
    std::vector<std::string> flat;
    const int textBytesTotal = (int)text.size();
    for(size_t scriptIdx=0; scriptIdx<spans.size(); scriptIdx++){
        const ScriptSpan& span = spans[scriptIdx];
        // nativeGetLanguages CLAMPS rather than dropping (0x652f8c): a negative
        // offset or one past the end is skipped, and an overlong span is cut to
        // what is left of the text. Neither can happen -- the loop only emits
        // ranges inside it -- but this is the shape.
        if(span.offset < 0) continue;
        if(textBytesTotal <= span.offset) continue;
        int spanBytes = span.bytes;
        if(span.offset + spanBytes > textBytesTotal) spanBytes = textBytesTotal - span.offset;
        if(spanBytes < 1) continue;
        flat.push_back(span.lang);
        flat.push_back(span.latin ? "1" : "0");
        flat.push_back(text.substr(span.offset, (size_t)spanBytes));
    }
    jobjectArray arr=env->NewObjectArray((jsize)flat.size(), stringClass, nullptr);
    for(jsize i=0;i<(jsize)flat.size();i++) env->SetObjectArrayElement(arr,i,env->NewStringUTF(flat[i].c_str()));
    return arr;
}
