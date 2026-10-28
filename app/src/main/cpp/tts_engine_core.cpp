
#include <jni.h>
#include <string>
#include <cstring>
#include <cstdio>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <stdint.h>
#include <mutex>
#include <memory>
#include <algorithm>
#include <limits>
#include <iterator>
#include <atomic>
#include <array>
#include <cmath>
#include "unicode_tables.h"   // generated from the Unicode Character Database
// java.lang.String, looked up ONCE. Android's JNI tips: "cache the IDs in
// JNI_OnLoad" -- FindClass is a class-loader lookup, and nativeGetLanguages
// ran it on every call. Held as a global ref, because a local one dies with the
// call. A function taking JNIEnv* rather than a bare global, so the desktop
// harnesses' slicer (which drops anything that takes a JNIEnv*) drops it too.
// The first caller fills it; JNI_OnLoad is that caller on a device.
static jclass stringClassRef(JNIEnv* env) {
    static std::mutex lock;
    static jclass cached = nullptr;
    std::lock_guard<std::mutex> guard(lock);
    if (!cached) {
        jclass local = env->FindClass("java/lang/String");
        if (!local) return nullptr;                 // exception pending
        cached = static_cast<jclass>(env->NewGlobalRef(local));
        env->DeleteLocalRef(local);
    }
    return cached;
}
// The entry points, bound once at load (owner, 2026-10-07): ART otherwise
// looks each Java_* symbol up with dlsym the first time it is called. Defined
// at the end of this file; registerNatives below is too.
static void registerNatives(JNIEnv* env);
// ML Kit's language identification, reached through Kotlin (owner, 2026-10-28):
// defined at the end of this file too.
static void installLanguageIdentifier(JavaVM* vm, JNIEnv* env);
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void*) {
    JNIEnv* env = nullptr;
    if (vm && vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) == JNI_OK && env) {
        if (!stringClassRef(env)) env->ExceptionClear();
        registerNatives(env);
        installLanguageIdentifier(vm, env);
    }
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

// clsCLD2.a's fold first; where it leaves a code point alone, the styling
// compatibility forms of the current Unicode (kUniFold: font, circled, wide,
// super/subscript, squared, small, narrow forms of one ASCII letter or digit --
// e.g. the outlined digits U+1CCF0.., the modifier capitals U+1D2C..) fold to
// that letter too (owner, 2026-10-06: "latest Unicode"). Every target is ASCII,
// which neither table touches, so folding twice still changes nothing.
static int foldCodepoint(int cp){
    int folded = normalizeFancyCodepoint(cp);
    if(folded != cp) return folded;
    return uniValue(kUniFold, cp, cp);
}

// Does the text hold anything the fold would change? Split out (owner,
// 2026-10-01) so a caller can keep its own string when the answer is no,
// instead of getting a copy of it back from normalizeFancyText.
static bool needsFancyFold(const std::string& text){
    for(int at = 0; at < (int)text.size(); ){
        int cpLen = 0;
        int cp = utf8ToCodepoint((const unsigned char*)text.data() + at, cpLen);
        if(cpLen <= 0) cpLen = 1;
        if(isFancyTrigger(cp) || uniValue(kUniFold, cp, -1) >= 0) return true;
        at += cpLen;
    }
    return false;
}
static std::string normalizeFancyText(const std::string& text){
    // The fast path matters: b() returns the ORIGINAL string when there is
    // nothing to fold, so ordinary text is never rebuilt.
    if(!needsFancyFold(text)) return text;
    std::string out;
    out.reserve(text.size());
    for(int at = 0; at < (int)text.size(); ){
        int cpLen = 0;
        int cp = utf8ToCodepoint((const unsigned char*)text.data() + at, cpLen);
        if(cpLen <= 0) cpLen = 1;
        appendCodepointUtf8(out, foldCodepoint(cp));
        at += cpLen;
    }
    return out;
}
// ---------------------------------------------------------------------------

// ==========================================================================
//  EMOJI
// ==========================================================================
// Extended_Pictographic + Emoji_Component of the current Unicode (kUniEmoji,
// generated), minus # * 0-9 and ZWJ (owner, 2026-10-06). This was a hand list
// from Unicode 11-era data: it missed every emoji since (🟠 U+1F7E0, 🫠 U+1FAE0,
// the 1FA70-1FAFF block ...) and the tag characters, so a tag flag was cut in two.
inline bool isEmoji(int codePoint) {
    return uniIn(kUniEmoji, codePoint);
}
// libcld2 getLanguageSpans' script of one code point (languageSpansFlat's classifyScript).
static int spanScriptSlot(int codePoint){
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
    // Everything above ASCII from the current Unicode (kUniSpanSlot,
    // generated; owner 2026-10-06). libcld2's hand-made block test stopped at
    // Unicode ~6: CJK Extensions G-J, Cyrillic Extended-C/D, Arabic
    // Extended-B/C, Georgian Mtavruli, Myanmar Extended-A/B, Ethiopic
    // Extended ... all read as "no script". A code point takes its Script's
    // slot; a Common/Inherited one the single slot its Script_Extensions
    // name, else none -- which is why the danda (U+0964/5, a dozen Indic
    // scripts) stays neutral exactly as 5.7.7.26 made it by hand.
    return uniValue(kUniSpanSlot, codePoint, -1);
}

// ==========================================================================
//  SHARED STATE AND FORWARD DECLARATIONS
// ==========================================================================
// No "kind" any more (owner, 2026-10-01): a second pass over every chunk used
// to work out whether it was all number / punctuation / emoji, and nothing read
// the answer -- the Kotlin branches on the TYPE d0.t resolved (parts[0]) and
// never on parts[1]. The field stays in each record as "0" so the record
// layout does not change.
struct ChunkResult { std::string lang; std::string text; int type; };
// The two sets are held as IMMUTABLE shared snapshots (owner, 2026-10-01):
// detectWindowed used to COPY both hash sets under the mutex on every call --
// every utterance in auto and Google mode. Now setDetectSets swaps in new
// pointers and a reader takes two refcounted pointers; nobody ever changes a
// set in place, so a snapshot read outside the mutex is exactly the copy it
// used to make.
typedef std::shared_ptr<const std::unordered_set<std::string>> LangSetPtr;
static std::mutex detectSetMutex;
static LangSetPtr detectOkIso3Set = std::make_shared<const std::unordered_set<std::string>>();
static LangSetPtr enabledLangSet = std::make_shared<const std::unordered_set<std::string>>();
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
// The cache is checked FIRST (owner, 2026-10-01): this ran for every number in
// every utterance with smart numbers on, and each call joined the hint list
// under its mutex and copied the whole keyword vector out, although the answer
// never changes once built. The table is written once, under the mutex, and
// never again, so a reference to it stays valid. The hint list is still read
// outside smartNumberMutex, so no lock is ever taken while holding it.
static const std::vector<std::string>& activeSmartNumberKeywords(){
    {
        std::lock_guard<std::mutex> lock(smartNumberMutex);
        if(smartNumberCacheValid) return smartNumberActive;
    }
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
    smartNumberActive = std::move(collected);
    smartNumberCacheValid = true;
    return smartNumberActive;
}
// Java's Character.isLetter (d0.p asks it): General_Category Lu/Ll/Lt/Lm/Lo
// of the current Unicode (kUniLetter, generated; owner 2026-10-06). The old
// block list called IPA, Latin Extended-E, most scripts after Unicode 5 and
// whole blocks of unassigned code points the wrong thing.
static bool isLetterCodepoint(int codePoint){
    if(codePoint < 0x80) return (codePoint>=0x41&&codePoint<=0x5A)||(codePoint>=0x61&&codePoint<=0x7A);
    return uniIn(kUniLetter, codePoint);
}
// ---------------------------------------------------------------------------
// FROM THE UNICODE DATA, NOT TYPED BY HAND (owner, 2026-10-07: "jo old hai vahan
// per sab kuchh update kar do"). Each one reads a table tools/unicode/gen_tables.py
// writes from UCD 18; tools/verify/unicode2/run.sh checks them against Java.
//
// A decimal digit is General_Category Nd -- Java's Character.isDigit: ASCII 0-9
// and the Devanagari, Gujarati, Bengali, Arabic-Indic, Thai... digits alike.
// AutoTTS's d0 used the regex [0-9], so a "१२३" was read as letters of the
// script around it; it is a number now, in every mode (a DEPARTURE).
static int decimalDigitValue(int cp){
    if(cp >= '0' && cp <= '9') return cp - '0';
    if(cp < 0x80) return -1;
    size_t a = 0, b = sizeof(kUniDigit) / sizeof(kUniDigit[0]);
    while(a < b){
        size_t m = (a + b) / 2;
        if((uint32_t)cp < kUniDigit[m].lo) b = m;
        else if((uint32_t)cp > kUniDigit[m].hi) a = m + 1;
        else return kUniDigit[m].v + (cp - (int)kUniDigit[m].lo);
    }
    return -1;
}
static inline bool isDecimalDigit(int cp){ return decimalDigitValue(cp) >= 0; }
// A currency symbol: AutoTTS's Currency Symbols block (U+20A0-20CF) plus every
// General_Category Sc outside ASCII (the cent, pound, yen, rial... signs). '$'
// is named on its own where it is meant.
static inline bool isCurrencySymbol(int cp){ return (cp >= 0x20A0 && cp <= 0x20CF) || uniIn(kUniCurrency, cp); }
// Java's Character.isWhitespace (Zs Zl Zp minus the no-break spaces, plus the
// controls Java names). One function now; it was typed out twice.
static inline bool isJavaWhitespace(int cp){ return uniIn(kUniJavaSpace, cp); }
// Reads one code point at `at`; *digit gets its value or -1.
static inline int digitAt(const std::string& text, size_t at, int& cpLen){
    return decimalDigitValue(utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen));
}
static bool keywordMatchesAnywhere(const std::string& keyword){
    int cpLen = 0;
    int firstCp = utf8ToCodepoint((const unsigned char*)keyword.c_str(), cpLen);
    // AutoTTS d0.p's blocks first (unchanged), then every letter of a script
    // written without spaces, from Line_Break (CJK Ext B-J, Tai Tham, Tai Viet...).
    if(uniIn(kUniNoSpace, firstCp)) return true;
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
        if(at == std::string::npos) continue;
        // Decided by the keyword's first code point alone, so once per keyword.
        if(keywordMatchesAnywhere(keyword)) return true;
        while(at != std::string::npos){
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
// String.toLowerCase(Locale.ROOT), which is what AutoTTS's d0 calls: the simple
// Lowercase_Mapping of every code point, SpecialCasing's one unconditional
// mapping (U+0130 -> i + U+0307), and Final_Sigma (a capital sigma that ends a
// word lowers to the final form). It covered Latin-1, Greek and Cyrillic by hand.
static_assert(EV_SPECIAL_LOWER_COUNT == 1, "SpecialCasing gained an unconditional lowercase mapping -- add it here");
static std::string lowerCaseRoot(const std::string& text){
    std::vector<int> cps;
    cps.reserve(text.size());
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        cps.push_back(utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen));
        at += cpLen;
    }
    std::string out;
    out.reserve(text.size() + 4);
    for(size_t k = 0; k < cps.size(); k++){
        int cp = cps[k];
        if(cp == 0x130){ out += 'i'; appendCodepointUtf8(out, 0x307); continue; }
        if(cp == 0x3A3){
            // Final_Sigma: a cased letter before (skipping case-ignorables), and
            // no cased letter after (skipping case-ignorables).
            bool before = false;
            for(size_t b = k; b > 0; b--){
                int c = cps[b - 1];
                if(uniIn(kUniCaseIgnorable, c)) continue;
                before = uniIn(kUniCased, c); break;
            }
            bool after = false;
            for(size_t f = k + 1; f < cps.size(); f++){
                int c = cps[f];
                if(uniIn(kUniCaseIgnorable, c)) continue;
                after = uniIn(kUniCased, c); break;
            }
            appendCodepointUtf8(out, (before && !after) ? 0x3C2 : 0x3C3);
            continue;
        }
        int lowered = cp;
        if(cp >= 'A' && cp <= 'Z') lowered = cp + 0x20;
        else if(cp >= 0x80) lowered = cp + uniValue(kUniLowerDelta, cp, 0);
        appendCodepointUtf8(out, lowered);
    }
    return out;
}
static bool hasEnoughDigits(const std::string& text){
    int digits = 0;
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + at, cpLen);
        if(isDecimalDigit(codePoint)) digits++;
        else if(codePoint==',' || codePoint=='.' || codePoint==':' || codePoint=='%' || codePoint=='$') return false;
        else if(isCurrencySymbol(codePoint)) return false;
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
    int cpLen = 0;
    auto digitsFrom = [&](size_t& pos){ size_t n = 0; while(pos < text.size() && digitAt(text, pos, cpLen) >= 0){ pos += cpLen; n++; } return n; };
    size_t digits = digitsFrom(at);
    if(digits < 1 || digits > 2) return false;
    if(at >= text.size() || text[at] != ':') return false;
    at++;
    if(digitsFrom(at) != 2) return false;
    if(at == text.size()) return true;
    if(text[at] != ':') return false;
    at++;
    return digitsFrom(at) == 2 && at == text.size();
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
        if(isDecimalDigit(codePoint)){ digits++; run++; if(run>longestRun) longestRun = run; }
        else if(codePoint==','){ run = 0; sawComma = true; }
        else if(codePoint=='.'){ run = 0; sawDot = true; }
        else if(codePoint=='%' || codePoint=='$' || isCurrencySymbol(codePoint)){ run = 0; sawCurrency = true; }
        else run = 0;
        at += cpLen;
    }
    if(digits == 0) return false;
    if(sawCurrency) return false;
    if((text.rfind("+", 0) == 0 || text.rfind("00", 0) == 0) && digits >= 8) return true;
    std::string body = text;
    if(body[0] == '+' || body[0] == '-') body = body.substr(1);
    { int firstLen = 0; if(!body.empty() && digitAt(body, 0, firstLen) == 0 && digits >= 5) return true; }
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
                size_t partCps = 0;
                for(size_t p = 0; p < part.size(); ){ int len = 0; utf8ToCodepoint((const unsigned char*)part.c_str() + p, len); p += len; partCps++; }
                if(partCps < 2 || partCps > 4){ grouped = false; break; }
                for(size_t p = 0; p < part.size(); ){ int len = 0; if(digitAt(part, p, len) < 0){ grouped = false; break; } p += len; }
                if(!grouped) break;
            }
            if(grouped) return true;
        }
    }
    return false;
}
// Code point by code point: a digit run is any decimal digits (Nd), so a
// Devanagari or Gujarati number is grouped like an ASCII one.
static std::string respaceDigits(const std::string& text, int groupSize){
    int size = groupSize > 1 ? groupSize : 1;      // Math.max(1, AutoTtsService.f0)
    std::string out;
    out.reserve(text.size() * 2);
    int run = 0;
    size_t at = 0;
    while(at < text.size()){
        int cpLen = 0;
        if(digitAt(text, at, cpLen) >= 0){
            if(run > 0 && run % size == 0) out += ' ';
            run++;
        } else {
            run = 0;
        }
        out.append(text, at, cpLen);
        at += cpLen;
    }
    return out;
}
// ==========================================================================
//  CODE-POINT PREDICATES
//  Note two pairs that look mergeable and are not: cpIsAsciiPunct EXCLUDES
//  backslash while isLatinPunctuation includes it, and cpIsJavaSpace and
//  isJavaWhitespace both read kUniJavaSpace now (one table, two names).
// ==========================================================================
static bool cpIsNumericCore(int codePoint){
    return isDecimalDigit(codePoint) || codePoint == 0x00B0 || codePoint == 0x00D7 ||
           codePoint == 0x00F7 || isCurrencySymbol(codePoint);
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
static bool cpIsJavaSpace(int codePoint){ return isJavaWhitespace(codePoint); }
static bool cpIsEmojiRunChar(int codePoint){ return isEmoji(codePoint) || codePoint == 0x200D; }
// The caller has usually decoded the code point at `offset` already; this
// takes it rather than decoding it again (owner, 2026-10-01).
static int emojiRunLengthFrom(const std::string& text, int offset, int codePoint, int cpLen){
    int len = (int)text.size();
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
static int emojiRunLengthAt(const std::string& text, int offset){
    if(offset >= (int)text.size()) return 0;
    int cpLen = 0;
    int codePoint = utf8ToCodepoint((const unsigned char*)text.c_str() + offset, cpLen);
    return emojiRunLengthFrom(text, offset, codePoint, cpLen);
}
static bool cpIsLetterOrDigitForNumberEdge(int codePoint){
    return isDecimalDigit(codePoint) || isLetterCodepoint(codePoint);
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
        if(isDecimalDigit(codePoint)) sawDigit = true;
        else if(!(cpIsNumericCore(codePoint) || cpIsGeneralPunct(codePoint) || cpIsPosixPunct(codePoint) || cpIsPosixSpace(codePoint))) allNumber = false;
        if(allEmoji){
            int emojiLen = emojiRunLengthFrom(text, at, codePoint, cpLen);
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
        // Every code point of the run passes cpIsPunctRun, so segmentTypeOf
        // would answer 0 if all are Java whitespace and 4 otherwise: worked out
        // here instead of reading the run again (owner, 2026-10-01).
        bool runAllSpace = true;
        while(at < len){
            int nextLen = 0;
            int nextCp = codepointAtOffset(text, at, nextLen);
            if(!cpIsPunctRun(nextCp)) break;
            if(!cpIsJavaSpace(nextCp)) runAllSpace = false;
            at += nextLen;
        }
        if(runStart != prev){
            std::string piece = text.substr(prev, runStart - prev);
            int pieceType = segmentTypeOf(piece);
            out.push_back({std::move(piece), pieceType, ""});
        }
        std::string run = text.substr(runStart, at - runStart);
        int runType = runAllSpace ? 0 : 4;
        out.push_back({std::move(run), runType, ""});
        prev = at;
    }
    if(prev < len){
        std::string piece = text.substr(prev);
        int pieceType = segmentTypeOf(piece);
        out.push_back({std::move(piece), pieceType, ""});
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
        int codePoint = codepointAtOffset(text, at, cpLen);
        int startLen = emojiRunLengthFrom(text, at, codePoint, cpLen);
        if(startLen <= 0){ at += cpLen; continue; }
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
// c3.w's Latin pattern -- Basic Latin to Latin Extended-B, Latin Extended
// Additional, General Punctuation, Currency Symbols, Latin Extended-C/D -- and,
// added to it, every Script=Latin code point of the current Unicode (kUniLatin;
// owner 2026-10-06): IPA, Latin Extended-E/F/G, the Latin modifier letters and
// the fullwidth Latin letters are Latin text too. Only ADDS to the old pattern.
static bool isLatinBufferCp(int codePoint){
    return (codePoint <= 0x024F) ||
           (codePoint >= 0x1E00 && codePoint <= 0x1EFF) ||
           (codePoint >= 0x2000 && codePoint <= 0x206F) ||
           (codePoint >= 0x20A0 && codePoint <= 0x20CF) ||
           (codePoint >= 0x2C60 && codePoint <= 0x2C7F) ||
           (codePoint >= 0xA720 && codePoint <= 0xA7FF) ||
           uniIn(kUniLatin, codePoint);
}
static std::vector<ChunkResult> buildMixChunks(const std::vector<std::string>& sentences, const std::string& latinFallback, const std::string& nonLatinFallback, const ReadingModes& modes, const std::string& neutralDefault, int neutralType, bool disableAdvancedDetection, bool isDual = false) {
    std::vector<ChunkResult> result;
    std::string fullText;
    for (auto& sentence : sentences) { if (!sentence.empty()) { fullText += sentence; fullText += ' '; } }
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
        // A run of non-space code points is copied in ONE append when it ends
        // (owner, 2026-10-01); the bytes are the ones the per-code-point
        // append copied, in the same order.
        std::string collapsed; collapsed.reserve(fullText.size());
        bool prevWs = false;
        int collapsePos = 0, collapseLen = (int)fullText.size(), keepFrom = -1;
        while (collapsePos < collapseLen) {
            int cpLen = 0;
            int codePoint = utf8ToCodepoint((const unsigned char*)&fullText[collapsePos], cpLen);
            if (cpLen <= 0) cpLen = 1;
            if (isUnicodeWs(codePoint)) {
                if (keepFrom >= 0) { collapsed.append(fullText, (size_t)keepFrom, (size_t)(collapsePos - keepFrom)); keepFrom = -1; }
                if (!prevWs) collapsed += ' ';
                prevWs = true;
            } else {
                if (keepFrom < 0) keepFrom = collapsePos;
                prevWs = false;
            }
            collapsePos += cpLen;
        }
        if (keepFrom >= 0) collapsed.append(fullText, (size_t)keepFrom, (size_t)(collapseLen - keepFrom));
        fullText = std::move(collapsed);
    }
    // c3.d0.t: d0.q(clsCLD2.b(text.replaceAll(...).replaceAll(...))). The
    // whitespace collapse above is the two replaceAll calls, the bidi strip
    // below is q(), and this is clsCLD2.b sitting between them.
    if (needsFancyFold(fullText)) fullText = normalizeFancyText(fullText);
    if (fullText.empty()) return result;
    {
        // The bidi controls are cut out and everything between them is copied
        // in ONE append per stretch (owner, 2026-10-01). Most texts carry
        // none, and then nothing is copied at all.
        std::string clean;
        bool cut = false;
        int bytePos = 0, byteLen = (int)fullText.size(), keepFrom = 0;
        while (bytePos < byteLen) {
            int cpLen = 0;
            int codePoint = utf8ToCodepoint((const unsigned char*)&fullText[bytePos], cpLen);
            // Bidi_Control of the current Unicode (kUniBidiControl): the
            // marks and embeddings d0.q strips, plus the isolates U+2066-2069
            // (Unicode 6.3) it predates (owner, 2026-10-06).
            if (uniIn(kUniBidiControl, codePoint)) {
                if (!cut) { clean.reserve(fullText.size()); cut = true; }
                clean.append(fullText, (size_t)keepFrom, (size_t)(bytePos - keepFrom));
                bytePos += cpLen;
                keepFrom = bytePos;
                continue;
            }
            bytePos += cpLen;
        }
        if (cut) {
            if (keepFrom < byteLen) clean.append(fullText, (size_t)keepFrom, (size_t)(byteLen - keepFrom));
            fullText = std::move(clean);
        }
    }
    if (fullText.empty()) return result;
    std::vector<TextSegment> segs;
    segs.reserve(fullText.size() / 8 + 4);
    {
        // The Latin / non-Latin buffer is always one contiguous stretch of
        // fullText, so it is cut out once when it ends instead of being built
        // a code point at a time (owner, 2026-10-01).
        int bufStart = 0, bufKind = -1;
        auto flushBuf = [&](int bufEnd) {
            if (bufKind == -1 || bufEnd <= bufStart) return;
            std::string buf = fullText.substr((size_t)bufStart, (size_t)(bufEnd - bufStart));
            if (bufKind == 1) splitByEmoji(buf, segs);
            else splitByNumber(buf, segs);
        };
        int len = (int)fullText.size(), i = 0;
        while (i < len) {
            int cpLen = 0;
            int codePoint = utf8ToCodepoint((const unsigned char*)&fullText[i], cpLen);
            bool latRange = isLatinBufferCp(codePoint);
            int kind = latRange ? 0 : 1;
            // A CHARACTER THAT BELONGS TO THE ONE BEFORE IT (owner, 2026-10-21:
            // "ek character bhi nahin chhutna chahie"): a combining mark
            // (Script=Inherited, UAX #24) or a Common modifier letter, modifier
            // symbol or mark stays in that character's buffer. c3.w's blocks
            // know none of them, so "café" typed with a combining acute was cut
            // into "cafe" and a lone mark, Uzbek "Oʻzbekiston" into three
            // pieces, and a decomposed Vietnamese word apart -- each piece
            // spoken on its own, the dual voice reading the mark. With nothing
            // before it, it takes the buffer of the first character after it
            // that is not one; alone, kUniAttach's own (DELIBERATE DEPARTURE).
            if (kind == 1) {
                int alone = uniValue(kUniAttach, codePoint, -1);
                if (alone >= 0) {
                    if (bufKind != -1) kind = bufKind;
                    else {
                        kind = alone;
                        for (int ahead = i + cpLen; ahead < len;) {
                            int aheadLen = 0;
                            int aheadCp = utf8ToCodepoint((const unsigned char*)&fullText[ahead], aheadLen);
                            bool aheadLatin = isLatinBufferCp(aheadCp);
                            if (aheadLatin || uniValue(kUniAttach, aheadCp, -1) < 0) { kind = aheadLatin ? 0 : 1; break; }
                            ahead += aheadLen;
                        }
                    }
                }
            }
            if (bufKind != -1 && bufKind != kind) { flushBuf(i); bufStart = i; }
            bufKind = kind;
            i += cpLen;
        }
        flushBuf(len);
    }
    {
        std::vector<TextSegment> mergedSegs;
        mergedSegs.reserve(segs.size());
        int segIdx = 0, segCount = (int)segs.size();
        while (segIdx < segCount) {
            std::string runText = std::move(segs[segIdx].text);
            int runType = segs[segIdx].type;
            int runEnd = segIdx + 1;
            while (runEnd < segCount && segs[runEnd].type != -1 &&
                   (segs[runEnd].type == runType || segs[runEnd].type == 0)) {
                runText += segs[runEnd].text;
                runEnd++;
            }
            mergedSegs.push_back({std::move(runText), runType, ""});
            segIdx = runEnd;
        }
        segs = std::move(mergedSegs);
    }
    if (segs.empty()) return result;
    (void)disableAdvancedDetection;
    if (modes.smartNumber) {
        const std::vector<std::string>& keywords = activeSmartNumberKeywords();
        for (int segPos = 0; segPos < (int)segs.size(); segPos++) {
            if (segs[segPos].type != 3) continue;
            const std::string& numberText = segs[segPos].text;
            bool respace = isPhoneShaped(numberText);
            if (!respace && !keywords.empty() && hasEnoughDigits(numberText)) {
                // Every segment is whole code points, so the UTF-16 length of
                // the joined context is the sum of the pieces' lengths: each
                // piece is measured once and the context joined once, instead
                // of re-measuring (and front-inserting into) the growing
                // string on every step (owner, 2026-10-01).
                int beforeUnits = 0, firstBack = segPos;
                while (firstBack > 0 && beforeUnits < 48) { firstBack--; beforeUnits += utf16LengthOf(segs[firstBack].text); }
                std::string before;
                for (int backIdx = firstBack; backIdx < segPos; backIdx++) before += segs[backIdx].text;
                if (beforeUnits > 48) before = lastUtf16Units(before, 48);
                int afterUnits = 0, nextIdx = segPos + 1;
                std::string after;
                for (; nextIdx < (int)segs.size() && afterUnits < 24; nextIdx++) {
                    after += segs[nextIdx].text;
                    afterUnits += utf16LengthOf(segs[nextIdx].text);
                }
                if (afterUnits > 24) after = firstUtf16Units(after, 24);
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
    result.reserve(segs.size());
    while (mergePos < segTotal) {
        std::string runText = std::move(segs[mergePos].text);
        int runType = segs[mergePos].type;
        std::string runLang = segs[mergePos].lang.empty() ? latinFallback : segs[mergePos].lang;
        if (punctuationApart && originalTypes[mergePos] == 4) {
            result.push_back({std::move(runLang), std::move(runText), runType});
            mergePos++;
            continue;
        }
        mergePos++;
        while (mergePos < segTotal && segs[mergePos].type == runType &&
               !(punctuationApart && originalTypes[mergePos] == 4)) {
            runText += segs[mergePos].text;
            mergePos++;
        }
        result.push_back({std::move(runLang), std::move(runText), runType});
    }
    return result;
}

// ==========================================================================
//  READING A jstring, THE ONE WAY (2026-09-10)
//
//  GetStringUTFChars ALLOCATES, and it answers nullptr when it cannot -- with
//  an OutOfMemoryError left PENDING in the JNIEnv. Eleven sites in this file
//  used the result without looking: `std::string(chars)` on a null pointer is
//  undefined behaviour, which on a phone means SIGSEGV and the whole process
//  gone, taking the voice with it. Several other sites in the same file DID
//  check (setLanguageHints, normalizeFancy, detectWindowOrSpans' two
//  fallbacks), so this was our own inconsistency rather than a decision.
//
//  It matters most where it is hottest: processDirect reads SEVEN strings and
//  runs once per utterance in mix and multilingual mode, and setDetectSets
//  reads the whole enabled-language array once per utterance too.
//
//  AND THE KOTLIN SIDE WAS ALREADY WRITTEN FOR THIS. Every call into the core
//  is wrapped in `try { ... } catch (_: Throwable) {}` or falls back to
//  "UNKNOWN" -- so a native failure was always meant to be recoverable. A
//  segfault is the one failure that contract cannot survive, and returning
//  nullptr with the pending exception intact is what turns it back into the
//  Throwable those catches are waiting for.
//
//  A null jstring is NOT a failure: it is an empty string, which is what every
//  guarded site here already treated it as.
// ==========================================================================
// std::string -> jstring WITHOUT losing anything after a U+0000 (2026-09-30).
// NewStringUTF reads a C string, so a raw 0x00 byte ends it: everything after
// it was silently dropped. Two ways one gets here: the Kotlin side fills
// processDirect's buffer with REAL UTF-8, where U+0000 is one 0x00 byte, and
// the fold rebuilds text through appendCodepointUtf8, which writes 0x00 too.
// JNI's modified UTF-8 spells U+0000 as C0 80, so that is what it becomes; the
// Java string then holds the same U+0000 the caller sent. A function taking
// JNIEnv*, so the desktop slicer drops it with the entry points.
static jstring newJavaString(JNIEnv* env, const std::string& s){
    if(s.find('\0') == std::string::npos) return env->NewStringUTF(s.c_str());
    std::string m;
    m.reserve(s.size() + 8);
    for(char c : s){
        if(c == '\0'){ m += (char)0xC0; m += (char)0x80; }
        else m += c;
    }
    return env->NewStringUTF(m.c_str());
}
static bool jstringToStd(JNIEnv* env, jstring value, std::string& out){
    out.clear();
    if(!value) return true;
    // GetStringUTFRegion, not GetStringUTFChars (2026-09-23). Android's JNI
    // tips recommend it for exactly this: it copies into OUR buffer, so the VM
    // allocates nothing, there is nothing to release, and the OOM-null answer
    // the old call could give is gone. The bytes are the same modified UTF-8
    // (U+0000 as C0 80, which the span code relies on).
    jsize units = env->GetStringLength(value);
    jsize bytes = env->GetStringUTFLength(value);
    if(env->ExceptionCheck()) return false;
    out.resize((size_t)bytes + 1);
    env->GetStringUTFRegion(value, 0, units, &out[0]);
    if(env->ExceptionCheck()) { out.clear(); return false; }
    out.resize((size_t)bytes);
    return true;
}

// Defined beside nativeFoldAndDetect, which shares it; processDirect uses it
// to carry each chunk's spans in the same crossing.
static std::vector<std::string> foldAndSpansFlat(const std::string& text, bool singleStops, bool alreadyFolded = false, bool forRecord = false);
struct ChunkResult;
static std::vector<std::vector<std::string>> chunksSpansFlat(const std::vector<ChunkResult>& chunks, bool singleStops);
static jobjectArray toJavaStringArray(JNIEnv* env, jclass stringClass, const std::vector<std::string>& flat);
// String.getBytes(UTF_8), byte for byte (owner, 2026-10-01): the Kotlin side
// used to encode the text, copy it into a direct ByteBuffer and hand THAT over
// -- two copies and two allocations per span. It hands the String now, and this
// is the same encoder: real UTF-8, U+0000 as one 0x00, a supplementary
// character as four bytes, and a surrogate with no partner as '?', which is
// what the JDK and Android's libcore write for malformed input.
static std::string javaUtf8Bytes(const jchar* units, int len){
    std::string out; out.reserve((size_t)len*3);
    int j=0;
    while(j<len){
        int c=(int)units[j];
        if(c>=0xD800&&c<=0xDBFF){
            if(j+1<len&&units[j+1]>=0xDC00&&units[j+1]<=0xDFFF){
                c=0x10000+((c-0xD800)<<10)+(((int)units[j+1])-0xDC00); j+=2;
                out+=(char)(0xF0|(c>>18)); out+=(char)(0x80|((c>>12)&0x3F)); out+=(char)(0x80|((c>>6)&0x3F)); out+=(char)(0x80|(c&0x3F));
                continue;
            }
            out+='?'; j++; continue;
        }
        if(c>=0xDC00&&c<=0xDFFF){ out+='?'; j++; continue; }
        j++;
        if(c<0x80) out+=(char)c;
        else if(c<0x800){ out+=(char)(0xC0|(c>>6)); out+=(char)(0x80|(c&0x3F)); }
        else { out+=(char)(0xE0|(c>>12)); out+=(char)(0x80|((c>>6)&0x3F)); out+=(char)(0x80|(c&0x3F)); }
    }
    return out;
}
// ==========================================================================
//  JNI: processDirect
//  Answers the chunk list as a String array (owner, 2026-10-01): for each
//  chunk, the number of fields that follow, then type, kind, lang, text and,
//  for a detected chunk, its fold and spans. Each field is its own element,
//  so nothing has to be escaped or split: the old packed string needed
//  U+001D/U+001E/U+001F escaped inside the text, and an unescaped U+001E once
//  cost the rest of the utterance.
// ==========================================================================
extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv* env, jclass, jstring jText,
    jstring jLat, jstring jNonLat, jstring jMode,
    jint jNumberMode, jstring jNumberSpecific,
    jint jPunctuationMode, jstring jPunctuationSpecific,
    jint jEmojiMode, jstring jEmojiSpecific, jboolean jPunctuationInFlow, jboolean jSmartNumber,
    jint jSmartNumberGroupSize,
    jstring jNeutralDefault, jint jNeutralType, jboolean jDisableAdvancedDetection,
    jboolean jDetect, jboolean jSingleStops)
{
    jclass stringClass = stringClassRef(env);
    if(!stringClass) return nullptr;
    std::vector<std::string> flat;
    int textLen = jText ? (int)env->GetStringLength(jText) : 0;
    if(textLen<=0) return toJavaStringArray(env, stringClass, flat);
    std::vector<jchar> units((size_t)textLen);
    env->GetStringRegion(jText, 0, textLen, units.data());
    if(env->ExceptionCheck()) return nullptr;
    std::string rawInput = javaUtf8Bytes(units.data(), textLen);
    std::string latinFallback, nonLatinFallback, mode;
    if(!jstringToStd(env,jLat,latinFallback)) return nullptr;
    if(!jstringToStd(env,jNonLat,nonLatinFallback)) return nullptr;
    if(!jstringToStd(env,jMode,mode)) return nullptr;
    ReadingModes modes;
    modes.numberMode = (int)jNumberMode;
    modes.punctuationMode = (int)jPunctuationMode;
    modes.emojiMode = (int)jEmojiMode;
    modes.punctuationInFlow = (jPunctuationInFlow == JNI_TRUE);
    modes.smartNumber = (jSmartNumber == JNI_TRUE);
    modes.smartNumberGroupSize = (int)jSmartNumberGroupSize;
    if(!jstringToStd(env,jNumberSpecific,modes.numberSpecific)) return nullptr;
    if(!jstringToStd(env,jPunctuationSpecific,modes.punctuationSpecific)) return nullptr;
    if(!jstringToStd(env,jEmojiSpecific,modes.emojiSpecific)) return nullptr;
    std::string neutralDefault;
    if(!jstringToStd(env,jNeutralDefault,neutralDefault)) return nullptr;
    int neutralType = (int)jNeutralType;
    bool disableAdvancedDetection = (jDisableAdvancedDetection == JNI_TRUE);
    // ONE sentence, the whole buffer (2026-10-01). It used to be split on 0x00,
    // a vestige: the Kotlin side fills this buffer with ONE span's real UTF-8 and
    // never puts a separator in it, so the only 0x00 that can arrive is a U+0000
    // in the text itself -- and buildMixChunks joins sentences with " ", so that
    // character became a space and was gone. AutoTTS's d0.t keeps U+0000 (Java's
    // \\s does not match it); buildMixChunks fed it whole agrees with d0.t on 192
    // U+0000 cases (segmenter harness, dual/mixed/multilingual x 4 mode ints).
    // Moved, not copied: rawInput is not read again (owner, 2026-10-06).
    std::vector<std::string> processed;
    processed.push_back(std::move(rawInput));
    bool detect = (jDetect == JNI_TRUE);
    bool singleStops = (jSingleStops == JNI_TRUE);
    // Every piece is MOVED into the answer (owner, 2026-10-06): the chunk and
    // its spans are not read again once the record is written.
    auto addRecord = [&flat](ChunkResult& chunk, std::vector<std::string>* extra){
        size_t n = 4 + (extra ? extra->size() : 0);
        flat.push_back(std::to_string(n));
        flat.push_back(std::to_string(chunk.type));
        flat.push_back("0");                       // the retired kind field
        flat.push_back(std::move(chunk.lang));
        flat.push_back(std::move(chunk.text));
        if (extra) flat.insert(flat.end(), std::make_move_iterator(extra->begin()), std::make_move_iterator(extra->end()));
    };
    if (mode == "mix") {
        auto chunks = buildMixChunks(processed, latinFallback, nonLatinFallback, modes, neutralDefault, neutralType, disableAdvancedDetection);
        // ONE CROSSING FOR THE WHOLE SPAN (owner, 2026-10-01). A chunk of type
        // 0, 1 or 2 is the one the caller hands to clsCLD2.e, so its fold and
        // its language spans ride in the same record -- exactly what
        // nativeFoldAndDetect answers for it, field for field: the folded text,
        // then lang / latin / text triples. An empty chunk carries nothing,
        // because the caller answers it before detecting. The chunks' spans are
        // named together (chunksSpansFlat): the chunks are one utterance, and a
        // script's runs in one sentence of it are one text to the detector.
        std::vector<std::vector<std::string>> chunkSpans;
        if (detect) chunkSpans = chunksSpansFlat(chunks, singleStops);
        for(size_t i=0; i<chunks.size(); i++){
            if (detect && chunks[i].type >= 0 && chunks[i].type <= 2 && !chunks[i].text.empty()) addRecord(chunks[i], &chunkSpans[i]);
            else addRecord(chunks[i], nullptr);
        }
    } else if (mode == "dual") {
        auto chunks = buildMixChunks(processed, latinFallback, nonLatinFallback, modes, neutralDefault, neutralType, disableAdvancedDetection, true);
        for(size_t i=0; i<chunks.size(); i++) addRecord(chunks[i], nullptr);
    }
    return toJavaStringArray(env, stringClass, flat);
}

// ==========================================================================
//  ISO CODES AND JAVA COLLECTION-ORDER EMULATION
//  javaHashSetOrder is load-bearing: the family fallback takes the first
//  enabled member IN HashSet ORDER, so any other container is a different
//  voice. It models ANDROID's HashSet, which is not JDK 19+'s.
// ==========================================================================
static std::unordered_map<std::string,std::string> g_iso2to3;
static std::string toIso3(const std::string& lang){
    // One copy: the part before any '-' / '_' (owner, 2026-10-01).
    std::string normalised(lang, 0, lang.find_first_of("-_"));
    for (char& ch : normalised) if (ch >= 'A' && ch <= 'Z') ch = (char)(ch - 'A' + 'a');
    std::string iso3 = normalised;
    if (normalised.size()==2){ auto found=g_iso2to3.find(normalised); if(found!=g_iso2to3.end() && !found->second.empty()) iso3=found->second; }
    if (iso3=="cmn"||iso3=="lzh"||iso3=="gan"||iso3=="hak") return "zho";
    return iso3;
}
static bool isLatinPunctuation(int codePoint){ return (codePoint>=33&&codePoint<=47)||(codePoint>=58&&codePoint<=64)||(codePoint>=91&&codePoint<=96)||(codePoint>=123&&codePoint<=126); }
static int firstValidCodePointU16(const jchar* units, int len){
    int j=0;
    while(j<len){
        jchar unit=units[j];
        if(!isJavaWhitespace((int)unit) && !isDecimalDigit((int)unit) && !isLatinPunctuation((int)unit)){
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
    auto hasLatin = [&]() -> bool { return anyLangEnabled(enabled, FAMILY_LATIN); };
    auto hasCyrillic = [&]() -> bool { return anyLangEnabled(enabled, FAMILY_CYRILLIC); };
    auto hasArabic = [&]() -> bool { return anyLangEnabled(enabled, FAMILY_ARABIC); };
    auto hasThaana = [&]() -> bool { return enabled.count("dv") > 0; };
    auto hasDevanagari = [&]() -> bool { return anyLangEnabled(enabled, FAMILY_DEVANAGARI); };
    auto hasCjk = [&]() -> bool { return anyLangEnabled(enabled, FAMILY_CJK); };
    auto hasGreek = [&]() -> bool { return enabled.count("el") || enabled.count("grc") || enabled.count("pnt"); };
    auto hasBengali = [&]() -> bool { return enabled.count("bn") || enabled.count("as") || enabled.count("mni"); };
    auto hasThai = [&]() -> bool { return enabled.count("th") || enabled.count("nod") || enabled.count("sou") || enabled.count("tts"); };
    auto hasHangul = [&]() -> bool { return enabled.count("ko") > 0; };
    auto hasKana = [&]() -> bool { return enabled.count("ja") || enabled.count("ain"); };
    auto hasHebrew = [&]() -> bool { return enabled.count("he") || enabled.count("yi") || enabled.count("lad"); };
    auto hasArmenian = [&]() -> bool { return enabled.count("hy") > 0; };
    auto hasGeorgian = [&]() -> bool { return enabled.count("ka") || enabled.count("xmf") || enabled.count("lzz"); };
    auto hasEthiopic = [&]() -> bool { return anyLangEnabled(enabled, FAMILY_ETHIOPIC); };
    auto hasSouthIndic = [&]() -> bool { return enabled.count("pa") || enabled.count("gu") || enabled.count("or") || enabled.count("ta") ||
                enabled.count("te") || enabled.count("kn") || enabled.count("ml") || enabled.count("si") || enabled.count("tcy"); };
    auto hasSeAsian = [&]() -> bool { return enabled.count("lo") || enabled.count("hnx") || enabled.count("bo") || enabled.count("dz") ||
                enabled.count("my") || enabled.count("shn") || enabled.count("kar") || enabled.count("mnw") || enabled.count("km"); };
    auto hasMongolian = [&]() -> bool { return enabled.count("mn") || enabled.count("mnc") || enabled.count("xal"); };
    if(codePoint>=48 && codePoint<=57) return pickFamilyLangArr(FAMILY_DIGIT, enabled);
    if((codePoint>=32&&codePoint<=47)||(codePoint>=58&&codePoint<=64)||(codePoint>=91&&codePoint<=96)||(codePoint>=123&&codePoint<=126)||(codePoint>=8192&&codePoint<=8303)) return "";
    // The 18 gates are asked LAZILY (owner, 2026-10-01): each was a handful of
    // hash lookups, all 18 computed on every call although a code point reaches
    // one or two of them. Every test below now checks the code point's range
    // FIRST and the gate second; both are pure, so swapping the && only skips
    // work. The Latin block's ranges are exactly 65-90, 97-122, 192-591 and
    // 7840-7929 (every code point listed inside it falls in those), so outside
    // them the block could never return and the gate is not asked.
    // PROVEN: tools/verify/scriptfamily/run.sh, every code point x 15 sets.
    if(((codePoint>=65&&codePoint<=90)||(codePoint>=97&&codePoint<=122)||(codePoint>=192&&codePoint<=591)||(codePoint>=7840&&codePoint<=7929)) && hasLatin()){
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
    if(((codePoint>=880&&codePoint<=1023)||(codePoint>=7936&&codePoint<=8191)) && hasGreek()) return pickFamilyLang("el", {"grc","pnt"}, enabled);
    if(((codePoint>=1024&&codePoint<=1279)||(codePoint>=1280&&codePoint<=1327)) && hasCyrillic()){
        if(codePoint==1028||codePoint==1108||codePoint==1030||codePoint==1110||codePoint==1031||codePoint==1111||codePoint==1168||codePoint==1169) return pickFamilyLang("uk", {}, enabled);
        if(codePoint==1026||codePoint==1106||codePoint==1032||codePoint==1112||codePoint==1033||codePoint==1113||codePoint==1034||codePoint==1114||codePoint==1035||codePoint==1115||
           codePoint==1039||codePoint==1119) return pickFamilyLang("sr", {"bs","hr"}, enabled);
        if(codePoint==1066||codePoint==1098) return pickFamilyLang("bg", {"mk"}, enabled);
        if(codePoint==1186||codePoint==1187||codePoint==1170||codePoint==1171||codePoint==1200||codePoint==1201||codePoint==1198||codePoint==1199||codePoint==1240||codePoint==1241)
            return pickFamilyLang("kk", {"ky"}, enabled);
        if(codePoint==1025||codePoint==1105||codePoint==1069||codePoint==1101||codePoint==1067||codePoint==1099) return pickFamilyLang("ru", {"be"}, enabled);
        return pickFamilyLangArr(FAMILY_CYRILLIC, enabled);
    }
    if(((codePoint>=1328&&codePoint<=1423)||(codePoint>=64256&&codePoint<=64279)) && hasArmenian()) return pickFamilyLang("hy", {}, enabled);
    if(((codePoint>=1424&&codePoint<=1535)||(codePoint>=64285&&codePoint<=64335)) && hasHebrew()) return pickFamilyLang("he", {"yi","lad"}, enabled);
    if(((codePoint>=1536&&codePoint<=1791)||(codePoint>=1872&&codePoint<=1919)||(codePoint>=2208&&codePoint<=2303)||(codePoint>=64336&&codePoint<=65023)||(codePoint>=65136&&codePoint<=65279)) && hasArabic()){
        if(codePoint==1657||codePoint==1672||codePoint==1681||codePoint==1729||codePoint==1746) return pickFamilyLang("ur", {"pa"}, enabled);
        if(codePoint==1665||codePoint==1669||codePoint==1673||codePoint==1683||codePoint==1686||codePoint==1690||codePoint==1724||codePoint==1744) return pickFamilyLang("ps", {}, enabled);
        if(codePoint>=1658&&codePoint<=1661) return pickFamilyLang("sd", {}, enabled);
        if(codePoint==1742||codePoint==1685||codePoint==1717||codePoint==1734) return pickFamilyLang("ku", {"ckb"}, enabled);
        if(codePoint==1722||codePoint==1726) return pickFamilyLangArr(FAMILY_URDU, enabled);
        if(codePoint==1662||codePoint==1670||codePoint==1688||codePoint==1711) return pickFamilyLangArr(FAMILY_PERSIAN, enabled);
        return pickFamilyLang("ar", {}, enabled);
    }
    if(codePoint>=1920 && codePoint<=1983 && hasThaana()) return "dv";
    if(((codePoint>=2304&&codePoint<=2431)||(codePoint>=43232&&codePoint<=43263)) && hasDevanagari()){
        if(codePoint!=2355) return pickFamilyLangArr(FAMILY_DEVANAGARI, enabled);
        return pickFamilyLang("mr", {"kok"}, enabled);
    }
    if(codePoint>=2432 && codePoint<=2559 && hasBengali()){
        if(codePoint==2544||codePoint==2545) return pickFamilyLang("as", {}, enabled);
        return pickFamilyLang("bn", {"as","mni"}, enabled);
    }
    if(codePoint>=2560 && codePoint<=3583 && hasSouthIndic()){
        if(codePoint>=2560&&codePoint<=2687) return pickFamilyLang("pa", {}, enabled);
        if(codePoint>=2688&&codePoint<=2815) return pickFamilyLang("gu", {}, enabled);
        if(codePoint>=2816&&codePoint<=2943) return pickFamilyLang("or", {}, enabled);
        if(codePoint>=2944&&codePoint<=3071) return pickFamilyLang("ta", {"kn"}, enabled);
        if(codePoint>=3072&&codePoint<=3199) return pickFamilyLang("te", {}, enabled);
        if(codePoint>=3200&&codePoint<=3327) return pickFamilyLang("kn", {"tcy"}, enabled);
        if(codePoint>=3328&&codePoint<=3455) return pickFamilyLang("ml", {}, enabled);
        if(codePoint>=3456&&codePoint<=3583) return pickFamilyLang("si", {}, enabled);
    }
    if(codePoint>=3584 && codePoint<=3711 && hasThai()) return pickFamilyLang("th", {"nod","sou","tts"}, enabled);
    if(((codePoint>=3712&&codePoint<=4255)||(codePoint>=43616&&codePoint<=43647)||(codePoint>=6016&&codePoint<=6143)||(codePoint>=6624&&codePoint<=6655)) && hasSeAsian()){
        if(codePoint>=3712&&codePoint<=3839) return pickFamilyLang("lo", {"hnx"}, enabled);
        if(codePoint>=3840&&codePoint<=4095) return pickFamilyLang("bo", {"dz"}, enabled);
        if((codePoint>=4096&&codePoint<=4255)||(codePoint>=43616&&codePoint<=43647)) return pickFamilyLang("my", {"shn","kar","mnw"}, enabled);
        if((codePoint>=6016&&codePoint<=6143)||(codePoint>=6624&&codePoint<=6655)) return pickFamilyLang("km", {}, enabled);
    }
    if(((codePoint>=4256&&codePoint<=4351)||(codePoint>=11520&&codePoint<=11567)) && hasGeorgian()) return pickFamilyLang("ka", {"xmf","lzz"}, enabled);
    if(((codePoint>=4352&&codePoint<=4607)||(codePoint>=12592&&codePoint<=12687)||(codePoint>=43360&&codePoint<=43391)||(codePoint>=44032&&codePoint<=55215)||(codePoint>=55216&&codePoint<=55295)) && hasHangul())
        return pickFamilyLang("ko", {}, enabled);
    if(((codePoint>=4608&&codePoint<=4991)||(codePoint>=4992&&codePoint<=5023)||(codePoint>=11648&&codePoint<=11743)) && hasEthiopic()) return pickFamilyLangArr(FAMILY_ETHIOPIC, enabled);
    if(((codePoint>=6144&&codePoint<=6319)||(codePoint>=71264&&codePoint<=71295)) && hasMongolian()) return pickFamilyLang("mn", {"mnc","xal"}, enabled);
    if(codePoint>=12288 && codePoint<=12351 && hasCjk()) return pickFamilyLangArr(FAMILY_CJK, enabled);
    if(((codePoint>=12352&&codePoint<=12543)||(codePoint>=12784&&codePoint<=12799)||(codePoint>=110592&&codePoint<=110959)) && hasKana()){
        if((codePoint>=12352&&codePoint<=12447)||(codePoint>=110592&&codePoint<=110847)||(codePoint>=110848&&codePoint<=110895)) return pickFamilyLang("ja", {}, enabled);
        if((codePoint>=12448&&codePoint<=12543)||(codePoint>=12784&&codePoint<=12799)||(codePoint>=110896&&codePoint<=110959)) return pickFamilyLang("ja", {"ain"}, enabled);
    }
    if(((codePoint>=19968&&codePoint<=40959)||(codePoint>=13312&&codePoint<=19903)||(codePoint>=131072&&codePoint<=173791)||(codePoint>=173824&&codePoint<=177983)||
               (codePoint>=177984&&codePoint<=178207)||(codePoint>=178208&&codePoint<=183983)||(codePoint>=183984&&codePoint<=191471)||
               (codePoint>=196608&&codePoint<=201551)||(codePoint>=201552&&codePoint<=205743)) && hasCjk())
        return pickFamilyLangArr(FAMILY_CJK, enabled);
    // A letter, mark or digit none of a.java's block ranges reached: its Script
    // in the current Unicode picks the same family a.java gives that script
    // (owner, 2026-10-06). a.java's blocks are Unicode ~6 -- Greek Extended was
    // there, Cyrillic Extended-C, Latin Extended-E, Kana Supplement, Hangul
    // Jamo Extended-B, CJK Extensions I/J and the rest were not.
    switch (uniValue(kUniFamilyScript, codePoint, 0)) {
        case 1: return hasLatin() ? pickFamilyLangArr(FAMILY_LATIN, enabled) : "";
        case 2: return hasGreek() ? pickFamilyLang("el", {"grc","pnt"}, enabled) : "";
        case 3: return hasCyrillic() ? pickFamilyLangArr(FAMILY_CYRILLIC, enabled) : "";
        case 4: return hasArmenian() ? pickFamilyLang("hy", {}, enabled) : "";
        case 5: return hasHebrew() ? pickFamilyLang("he", {"yi","lad"}, enabled) : "";
        case 6: return hasArabic() ? pickFamilyLang("ar", {}, enabled) : "";
        case 7: return hasThaana() ? "dv" : "";
        case 8: return hasDevanagari() ? pickFamilyLangArr(FAMILY_DEVANAGARI, enabled) : "";
        case 9: return hasBengali() ? pickFamilyLang("bn", {"as","mni"}, enabled) : "";
        case 10: return hasSouthIndic() ? pickFamilyLang("pa", {}, enabled) : "";
        case 11: return hasSouthIndic() ? pickFamilyLang("gu", {}, enabled) : "";
        case 12: return hasSouthIndic() ? pickFamilyLang("or", {}, enabled) : "";
        case 13: return hasSouthIndic() ? pickFamilyLang("ta", {"kn"}, enabled) : "";
        case 14: return hasSouthIndic() ? pickFamilyLang("te", {}, enabled) : "";
        case 15: return hasSouthIndic() ? pickFamilyLang("kn", {"tcy"}, enabled) : "";
        case 16: return hasSouthIndic() ? pickFamilyLang("ml", {}, enabled) : "";
        case 17: return hasSouthIndic() ? pickFamilyLang("si", {}, enabled) : "";
        case 18: return hasThai() ? pickFamilyLang("th", {"nod","sou","tts"}, enabled) : "";
        case 19: return hasSeAsian() ? pickFamilyLang("lo", {"hnx"}, enabled) : "";
        case 20: return hasSeAsian() ? pickFamilyLang("bo", {"dz"}, enabled) : "";
        case 21: return hasSeAsian() ? pickFamilyLang("my", {"shn","kar","mnw"}, enabled) : "";
        case 22: return hasGeorgian() ? pickFamilyLang("ka", {"xmf","lzz"}, enabled) : "";
        case 23: return hasHangul() ? pickFamilyLang("ko", {}, enabled) : "";
        case 24: return hasEthiopic() ? pickFamilyLangArr(FAMILY_ETHIOPIC, enabled) : "";
        case 25: return hasSeAsian() ? pickFamilyLang("km", {}, enabled) : "";
        case 26: return hasMongolian() ? pickFamilyLang("mn", {"mnc","xal"}, enabled) : "";
        case 27: return hasKana() ? pickFamilyLang("ja", {}, enabled) : "";
        case 28: return hasKana() ? pickFamilyLang("ja", {"ain"}, enabled) : "";
        case 29: return hasCjk() ? pickFamilyLangArr(FAMILY_CJK, enabled) : "";
        default: return "";
    }
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
    // The same Script fallback as familyLangForCpFiltered's (owner, 2026-10-06).
    switch (uniValue(kUniFamilyScript, codePoint, 0)) {
        case 1: return famFromArray(FAMILY_LATIN, sizeof(FAMILY_LATIN)/sizeof(FAMILY_LATIN[0]));
        case 2: return makeFamily("el", {"grc","pnt"});
        case 3: return famFromArray(FAMILY_CYRILLIC, sizeof(FAMILY_CYRILLIC)/sizeof(FAMILY_CYRILLIC[0]));
        case 4: return makeFamily("hy", {});
        case 5: return makeFamily("he", {"yi","lad"});
        case 6: return makeFamily("ar", {});
        case 7: return makeFamily("dv", {});
        case 8: return famFromArray(FAMILY_DEVANAGARI, sizeof(FAMILY_DEVANAGARI)/sizeof(FAMILY_DEVANAGARI[0]));
        case 9: return makeFamily("bn", {"as","mni"});
        case 10: return makeFamily("pa", {});
        case 11: return makeFamily("gu", {});
        case 12: return makeFamily("or", {});
        case 13: return makeFamily("ta", {"kn"});
        case 14: return makeFamily("te", {});
        case 15: return makeFamily("kn", {"tcy"});
        case 16: return makeFamily("ml", {});
        case 17: return makeFamily("si", {});
        case 18: return makeFamily("th", {"nod","sou","tts"});
        case 19: return makeFamily("lo", {"hnx"});
        case 20: return makeFamily("bo", {"dz"});
        case 21: return makeFamily("my", {"shn","kar","mnw"});
        case 22: return makeFamily("ka", {"xmf","lzz"});
        case 23: return makeFamily("ko", {});
        case 24: return famFromArray(FAMILY_ETHIOPIC, sizeof(FAMILY_ETHIOPIC)/sizeof(FAMILY_ETHIOPIC[0]));
        case 25: return makeFamily("km", {});
        case 26: return makeFamily("mn", {"mnc","xal"});
        case 27: return makeFamily("ja", {});
        case 28: return makeFamily("ja", {"ain"});
        case 29: return famFromArray(FAMILY_CJK, sizeof(FAMILY_CJK)/sizeof(FAMILY_CJK[0]));
        default: return makeFamily("un", {});
    }
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
    std::string res = enabled.empty() ? familyLangForCp(codePoint, okIso3) : familyLangForCpFiltered(codePoint, enabled);
    // The hex is formatted only when there is a log to put it in.
    if(logBuf){ char hex[16]; snprintf(hex,sizeof(hex),"%x",codePoint);
        *logBuf += std::string("[SCRIPT] a.e cp=U+")+hex+" → "+(res.empty()?"null":res)+"\n"; }
    return res;
}

// The language each script slot 7..25 is read in (libcld2's getLanguageSpans
// jump table, 0x62f8f0): a span of one of these scripts is that language,
// decided by the script alone.
static const char* const SCRIPT_FIXED_LANG[19] = {
    "el", "hy", "iw", "ka", "pa", "gu", "or", "ta", "te", "kn",
    "ml", "si", "th", "lo", "bo", "my", "km", "am", "ko"
};
// ==========================================================================
//  LANGUAGE IDENTIFICATION IS GOOGLE'S ML KIT     AutoTTS clsCLD2.d / getLanguage
//  owner, 2026-10-28: "koi model rakhna hi nahin ... ML Kit use karenge har
//  jagah ... aur Unicode use karenge". The core keeps no model of its own: not
//  CLD2 (gone 2026-10-21), not CLD3 (2026-09-02), and since 2026-10-28 no
//  word-and-ending model, no letter rules and no copy of ML Kit's network. A
//  text's SCRIPT is read from the Unicode tables (unicode_tables.h); where the
//  text itself has to name a LANGUAGE -- a span of a script two or more
//  enabled languages write, a span of a shared script no enabled language
//  writes, and every window of auto and Google mode -- the core asks Google's
//  ML Kit language identification (com.google.mlkit:language-id, its bundled
//  model) through EasyVoiceTtsService.mlkitIdentify in Kotlin, which
//  JNI_OnLoad installs (end of this file). A desktop harness installs its own
//  with evSetLanguageIdentifier (tools/mlkit runs ML Kit's own library).
// ==========================================================================
// The identifier: the text in UTF-8 in; out, ML Kit's languages, each one's
// tag (as ML Kit spells it: "hi", "iw", "fil", "zh-Latn" ...) and confidence
// (0..1) -- every language it gives 0.01 or more, its identifyPossibleLanguages
// at its default options. Answers how many it wrote, at most `max`; 0 when
// there was no answer.
typedef int (*EvLidFn)(const char* utf8, size_t len, char (*tags)[16], float* confidences, int max);
static std::atomic<EvLidFn> g_languageIdentifier{nullptr};
extern "C" void evSetLanguageIdentifier(EvLidFn fn){
    g_languageIdentifier.store(fn, std::memory_order_release);
}
// ML Kit's answer for one text. It knows 111 languages, and no more than 100
// of them can each have 0.01.
struct LidAnswer {
    static constexpr int kMax = 112;
    int n = 0;
    char tags[kMax][16];
    float confidences[kMax];
};
static bool identifyLanguages(const char* utf8, size_t len, LidAnswer& answer){
    answer.n = 0;
    EvLidFn fn = g_languageIdentifier.load(std::memory_order_acquire);
    if(!fn || len == 0) return false;
    const int n = fn(utf8, len, answer.tags, answer.confidences, LidAnswer::kMax);
    answer.n = n < 0 ? 0 : std::min(n, (int)LidAnswer::kMax);
    for(int k = 0; k < answer.n; k++) answer.tags[k][15] = '\0';
    return answer.n > 0;
}

// One code point of UTF-8 (MediaPipe's utf_charntorune, which ML Kit's own
// tokenizer reads text with): the bytes it takes, *r set; 0xFFFD and 1 for a
// byte that starts no valid sequence; 0 when the sequence runs past n.
static int utf8Decode(const unsigned char* s, size_t n, int32_t* r){
    *r = 0xFFFD;
    if (n == 0) return 0;
    int c = s[0];
    if (c < 0x80) { *r = c; return 1; }
    if (n <= 1) return 0;
    int c1 = s[1] ^ 0x80;
    if (c1 & 0xC0) return 1;
    if (c < 0xE0) {
        if (c < 0xC0) return 1;
        int32_t l = ((c << 6) | c1) & 0x7FF;
        if (l <= 0x7F) return 1;
        *r = l;
        return 2;
    }
    if (n <= 2) return 0;
    int c2 = s[2] ^ 0x80;
    if (c2 & 0xC0) return 1;
    if (c < 0xF0) {
        int32_t l = ((((c << 6) | c1) << 6) | c2) & 0xFFFF;
        if (l <= 0x7FF) return 1;
        *r = l;
        return 3;
    }
    if (n <= 3) return 0;
    int c3 = s[3] ^ 0x80;
    if (c3 & 0xC0) return 1;
    if (c < 0xF8) {
        int32_t l = ((((((c << 6) | c1) << 6) | c2) << 6) | c3) & 0x1FFFFF;
        if (l <= 0xFFFF || l > 0x10FFFF) return 1;
        *r = l;
        return 4;
    }
    return 1;
}

// A language code -- an enabled one, or ML Kit's tag -- spelled one way. ML Kit
// says "iw", "fil", "jv", "no", "id", "yi" where a voice may say "he", "tl",
// "jw", "nb" or "nn", "in", "ji"; "zh-Hant" is Chinese and "sr-ME" Serbian.
static std::string canonicalLanguage(const std::string& c){
    if(c == "he") return "iw";
    if(c == "tl") return "fil";
    if(c == "jw") return "jv";
    if(c == "nb" || c == "nn") return "no";
    if(c == "in") return "id";
    if(c == "ji") return "yi";
    if(c == "zh-Hant") return "zh";
    if(c == "sr-ME") return "sr";
    return c;
}
// The script slot (spanScriptSlot's numbering) a language writes, by its
// canonical code. 1..6, the scripts two languages can share -- Latin, Cyrillic,
// Arabic, Devanagari, Han and kana, Bengali -- by kUniSharedLangs (gen_tables.py
// SHARED_LANGS: ML Kit's own list of the 111 languages it identifies, plus
// Sanskrit and Assamese, which it does not name); 7..25 a script one language is
// read in (SCRIPT_FIXED_LANG; Yiddish writes Hebrew); -1 for anything else: a
// romanised tag ("hi-Latn"), "und", a language ML Kit does not know.
static int languageSlot(const std::string& code){
    for(const UniSharedLang& x : kUniSharedLangs) if(code == x.code) return x.slot;
    for(int i = 0; i < 19; i++) if(code == SCRIPT_FIXED_LANG[i]) return 7 + i;
    if(code == "yi") return 9;   // Yiddish: the Hebrew script
    return -1;
}
// The enabled codes that write script slot `slot`, one per language, in the
// order they were enabled.
static void slotLanguages(int slot, const std::vector<std::string>& enabled, std::vector<std::string>& out){
    out.clear();
    for(const std::string& code : enabled){
        const std::string c = canonicalLanguage(code);
        if(languageSlot(c) != slot) continue;
        bool seen = false;
        for(const std::string& x : out) seen |= canonicalLanguage(x) == c;
        if(!seen) out.push_back(code);
    }
}
// Of `langs`, the one ML Kit gives the most; "" when it names none of them.
// On a tie the first.
static std::string mostLikely(const LidAnswer& answer, const std::vector<std::string>& langs){
    std::string best;
    float bestConfidence = -1.0f;
    for(const std::string& code : langs){
        const std::string c = canonicalLanguage(code);
        for(int k = 0; k < answer.n; k++){
            if(canonicalLanguage(answer.tags[k]) != c) continue;
            if(answer.confidences[k] > bestConfidence){ bestConfidence = answer.confidences[k]; best = code; }
            break;
        }
    }
    return best;
}
// ML Kit's most likely language among those that write script slot `slot`, as
// ML Kit spells it; "" when it names none.
static std::string mostLikelyInSlot(const LidAnswer& answer, int slot){
    int best = -1;
    for(int k = 0; k < answer.n; k++){
        if(languageSlot(canonicalLanguage(answer.tags[k])) != slot) continue;
        if(best < 0 || answer.confidences[k] > answer.confidences[best]) best = k;
    }
    return best < 0 ? std::string() : std::string(answer.tags[best]);
}

// ==========================================================================
//  DETECTION CACHE (owner, 2026-10-01)
//  A screen reader says the same short labels again and again ("Settings",
//  "Back", a contact's name). ML Kit keeps no state between calls, so for the
//  same bytes it gives the same answer; the span answer also depends on the
//  script and on the hint state, so a span entry is keyed by script + bytes and
//  the whole span table is dropped when the hint state changes (the pointer is
//  kept, so it cannot be reused). One cache per thread: no lock, and nothing
//  shared. At most kDetectCacheMax entries each, cleared when full.
// ==========================================================================
struct DetectCache {
    std::shared_ptr<const void> spanHints;                      // the HintState the span entries belong to
    std::unordered_map<std::string, std::string> spans;         // script byte + span bytes -> language
    std::shared_ptr<const void> windowsEnabled;                 // the enabled set the window entries belong to
    std::unordered_map<std::string, std::string> windows;       // window bytes -> detectWindowLang's answer
    std::unordered_map<std::string, std::vector<float>> shared; // script byte + bytes -> sharedAnswer's confidences
};
static const size_t kDetectCacheMax = 256;
static DetectCache& detectCache(){ static thread_local DetectCache cache; return cache; }
// The span and shared entries belong to one HintState; a new one drops them.
static void syncSpanCache(DetectCache& cache, const std::shared_ptr<const void>& hints){
    if(cache.spanHints != hints){ cache.spans.clear(); cache.shared.clear(); cache.spanHints = hints; }
}
// ML Kit's identifyLanguage answers a language only when it gives it at least
// this confidence, "und" otherwise -- its documented default threshold
// (LanguageIdentificationOptions). The window detector keeps the same rule.
static const float kMlKitThreshold = 0.5f;
static std::string detectWindowLangUncached(const std::string& utf8Text, const std::unordered_set<std::string>& enabled);
// The answer depends on the enabled set too, since ML Kit chooses between the
// enabled languages of one script, so a new set drops the window entries, as a
// new HintState drops the span ones.
static std::string detectWindowLang(const std::string& utf8Text,
                                    const std::shared_ptr<const std::unordered_set<std::string>>& enabled){
    DetectCache& cache = detectCache();
    if(cache.windowsEnabled != enabled){ cache.windows.clear(); cache.windowsEnabled = enabled; }
    auto hit = cache.windows.find(utf8Text);
    if(hit == cache.windows.end()){
        std::string answer = detectWindowLangUncached(utf8Text, *enabled);
        if(cache.windows.size() >= kDetectCacheMax) cache.windows.clear();
        hit = cache.windows.emplace(utf8Text, std::move(answer)).first;
    }
    return hit->second;
}
// The letters each language writes (below, "THE LETTERS EACH LANGUAGE WRITES").
struct LetterLang { int bit; bool open; bool named; };
static std::vector<LetterLang> letterLangs(int s, const std::vector<std::string>& langs);
static uint64_t allOf(size_t n);
static uint64_t lettersAllow(int s, const char* bytes, size_t len, const std::vector<LetterLang>& info);
static uint64_t lettersHeard(uint64_t allowed, const std::vector<LetterLang>& info, const std::vector<float>& p);
static size_t bestAllowed(uint64_t allowed, const std::vector<float>& p, size_t prefer);
static std::string detectWindowLangUncached(const std::string& utf8Text, const std::unordered_set<std::string>& enabled){
    // What clsCLD2.d asks per 64-character window (AutoTTS: CLD2's getLanguage,
    // 0x6523c8): one language for the window, or none when the detector is not
    // sure of it -- here ML Kit's most likely language, at ML Kit's own
    // threshold.
    //
    // ONLY A LANGUAGE THAT WRITES THE WINDOW'S LETTERS (2026-10-21). CLD2 scored
    // a text per script, so its answer was always a language of the text's own
    // letters. ML Kit weighs all its languages at once, and on a letter it
    // barely knows it names one of another script with confidence: "en" for a
    // lone Greek beta, "hi" for a rare Han character, Khmer for "😂😂😂" (no
    // letter at all). So the answer is ML Kit's most likely language among
    // those that write a script the window's letters are in (Unicode's own
    // letters, General_Category L), and a window without one letter answers
    // UNKNOWN, as CLD2 did -- the script fallback after it then names the
    // language. A romanised tag (hi-Latn, ar-Latn ...) is never taken: Latin
    // text stays with the Latin-script languages, as in the span path.
    uint64_t scripts = 0;
    {
        const unsigned char* s = (const unsigned char*)utf8Text.data();
        size_t i = 0, n = utf8Text.size();
        while(i < n){
            int32_t r;
            const int used = utf8Decode(s + i, n - i, &r);
            if(used == 0) break;
            i += (size_t)used;
            if(!isLetterCodepoint((int)r)) continue;
            const int slot = spanScriptSlot((int)r);
            if(slot >= 1 && slot < 64) scripts |= (uint64_t)1 << slot;
        }
        if(!scripts) return "UNKNOWN";
    }
    LidAnswer answer;
    if(!identifyLanguages(utf8Text.data(), utf8Text.size(), answer)) return "UNKNOWN";
    int top = -1;
    for(int k = 0; k < answer.n; k++){
        const int slot = languageSlot(canonicalLanguage(answer.tags[k]));
        if(slot < 1 || !((scripts >> slot) & 1)) continue;
        if(top < 0 || answer.confidences[k] > answer.confidences[top]) top = k;
    }
    if(top < 0) return "UNKNOWN";
    // Sorted, so the same code is chosen whatever order the set iterates in.
    std::vector<std::string> codes(enabled.begin(), enabled.end());
    std::sort(codes.begin(), codes.end());
    // Where two or more enabled languages write the script of that answer, the
    // window is the one of them ML Kit gives the most, whatever its confidence:
    // between close languages (Indonesian and Malay, Spanish and Portuguese,
    // Hindi and Marathi) ML Kit is split under 0.5, and an UNKNOWN there handed
    // the window to the script's first enabled language, right or not. When ML
    // Kit names none of them, UNKNOWN.
    const std::string topTag = canonicalLanguage(answer.tags[top]);
    const int slot = languageSlot(topTag);
    if(slot >= 1 && slot <= 6){
        std::vector<std::string> langs;
        slotLanguages(slot, codes, langs);
        if(langs.size() >= 2){
            // Only a language the window's letters allow (lettersAllow, heard
            // against ML Kit as a word's are): Urdu letters in it, an Urdu window.
            const std::vector<LetterLang> info = letterLangs(slot, langs);
            std::vector<float> p(langs.size(), 0.0f);
            for(size_t l = 0; l < langs.size(); l++){
                const std::string c = canonicalLanguage(langs[l]);
                for(int k = 0; k < answer.n; k++)
                    if(canonicalLanguage(answer.tags[k]) == c){ p[l] = answer.confidences[k]; break; }
            }
            const uint64_t allowed = lettersHeard(lettersAllow(slot, utf8Text.data(), utf8Text.size(), info), info, p);
            if(allowed != allOf(langs.size())){
                const size_t best = bestAllowed(allowed, p, langs.size());
                // Two or more allowed and ML Kit gives none of them anything
                // ("Мы арабы": Kyrgyz, Belarusian): the spans decide, word by
                // word, as when it names none of `langs`.
                if((allowed & (allowed - 1)) != 0 && p[best] <= 0.0f) return "UNKNOWN";
                return langs[best];
            }
            const std::string chosen = mostLikely(answer, langs);
            return chosen.empty() ? std::string("UNKNOWN") : chosen;
        }
    }
    if(answer.confidences[top] < kMlKitThreshold) return "UNKNOWN";
    // The enabled code that names this language, so its voice is found: ML Kit
    // says "no", "iw", "id", "fil" where a voice may say "nb", "he", "in", "tl".
    for(const std::string& code : codes) if(canonicalLanguage(code) == topTag) return code;
    return answer.tags[top];
}

// ==========================================================================
//  LANGUAGE HINTS              libcld2.so setLanguageHints 0x653588
//  setLanguageHints does more than remember the list: it derives, per script,
//  the first enabled language that writes it (AutoTTS's scriptLanguageFallback
//  table) -- what a run of only neutral characters and a few scripts' spans are
//  read in. (AutoTTS's other table, the hint CLD2 was steered with, went with
//  CLD2 on 2026-10-21.)
// ==========================================================================
static std::mutex languageHintMutex;
// AutoTTS's native setLanguageHints (0x653588) keeps the codes lowercased and
// at most 64 of them. The script codes the span walk dispatches on stop at 39,
// so the table has 40 entries.
static const int kScriptHintSlots = 40;
// ONE IMMUTABLE SNAPSHOT (owner, 2026-10-01). setLanguageHints builds a new
// HintState and swaps the pointer; nothing changes a HintState after that, so
// a reader takes the mutex once, keeps a refcounted pointer, and reads freely.
struct HintState {
    std::string list;                         // the comma list, = languageHintList
    std::vector<std::string> codes;           // = languageHintCodes
    const char* fallback[kScriptHintSlots];   // = scriptLanguageFallback; nullptr where none
};
typedef std::shared_ptr<const HintState> HintStatePtr;
static HintStatePtr emptyHintState(){
    std::shared_ptr<HintState> state = std::make_shared<HintState>();
    for(int slot=0; slot<kScriptHintSlots; slot++) state->fallback[slot] = nullptr;
    return state;
}
static HintStatePtr g_hintState = emptyHintState();
static HintStatePtr currentHintState(){
    std::lock_guard<std::mutex> lock(languageHintMutex);
    return g_hintState;
}
// The static table at 0x62f924: 48 {script, candidate language} pairs, in file
// order, because the order is what decides which candidate becomes the
// fallback. Only the six scripts a detector is ever asked about appear. (The
// codes CLD2 named its languages by: zh-Hant, tl, no.)
static const struct { int script; const char* code; } kScriptLangPairs[48] = {
    { 3, "ar"},  { 3, "fa"},  { 3, "ur"},  { 3, "ps"},  { 3, "sd"},
    { 2, "ru"},  { 2, "uk"},  { 2, "bg"},  { 2, "sr"},  { 2, "mk"},
    { 2, "be"},  { 2, "mn"},  { 2, "tg"},  { 2, "kk"},  { 2, "ky"},
    { 4, "hi"},  { 4, "mr"},  { 4, "ne"},  { 4, "sa"},
    { 6, "bn"},  { 6, "as"},
    { 5, "zh"},  { 5, "zh-Hant"}, { 5, "ja"},
    { 1, "en"},  { 1, "es"},  { 1, "fr"},  { 1, "pt"},  { 1, "de"},
    { 1, "it"},  { 1, "vi"},  { 1, "id"},  { 1, "tr"},  { 1, "nl"},
    { 1, "pl"},  { 1, "ro"},  { 1, "cs"},  { 1, "hu"},  { 1, "sv"},
    { 1, "da"},  { 1, "no"},  { 1, "fi"},  { 1, "sk"},  { 1, "hr"},
    { 1, "ms"},  { 1, "tl"},  { 1, "sw"},  { 1, "af"}
};
// THE SEVEN SCRIPTS GEN_TABLES' SLOT_EXTRA NAMES (owner, 2026-10-21: "ek
// character bhi nahin chhutna chahie ... Chahe Koi Bhi Lipi Ho"): Syriac,
// Thaana, Cherokee, Canadian Aboriginal, Mongolian, Tagalog and Limbu, each
// written by one language. A span of one of them is that language (as the
// fixed slots 7..25 are theirs), and the fallback table below holds it where it
// is enabled.
static const struct { int script; const char* code; } kExtraSlotLangs[7] = {
    {26, "syr"}, {27, "dv"}, {28, "chr"}, {29, "iu"}, {30, "mn"}, {31, "tl"}, {32, "lif"}};
// This is the second half of setLanguageHints, run on a HintState nobody else
// can see yet: for each script, the first enabled language of the tables above.
static void rebuildScriptLanguageTables(HintState& state){
    const std::vector<std::string>& languageHintCodes = state.codes;
    for(int slot=0; slot<kScriptHintSlots; slot++) state.fallback[slot] = nullptr;
    if(languageHintCodes.empty()) return;
    auto hinted = [&](const char* code){
        return std::find(languageHintCodes.begin(), languageHintCodes.end(), code) != languageHintCodes.end();
    };
    for(int pair=0; pair<48; pair++){
        if(!hinted(kScriptLangPairs[pair].code)) continue;
        const int slot = kScriptLangPairs[pair].script;
        if(!state.fallback[slot]) state.fallback[slot] = kScriptLangPairs[pair].code;
    }
    for(int pair=0; pair<7; pair++){
        if(!hinted(kExtraSlotLangs[pair].code)) continue;
        const int slot = kExtraSlotLangs[pair].script;
        if(!state.fallback[slot]) state.fallback[slot] = kExtraSlotLangs[pair].code;
    }
}
static std::string currentLanguageHints(){
    return currentHintState()->list;
}
static const char* scriptLanguageFallbackOf(const HintState& state, int script){
    if(script < 0 || script >= kScriptHintSlots) return nullptr;
    return state.fallback[script];
}

// ==========================================================================
//  JNI: the remaining entry points
// ==========================================================================
extern "C" JNIEXPORT void JNICALL
Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv* env, jclass, jobjectArray jLangs){
    std::vector<std::string> codes;
    if(jLangs){
        jsize count = env->GetArrayLength(jLangs);
        for(jsize i=0;i<count && codes.size()<64;i++){
            jstring jLang = (jstring)env->GetObjectArrayElement(jLangs, i);
            if(!jLang) continue;
            std::string langC;
            if(!jstringToStd(env, jLang, langC)){ env->DeleteLocalRef(jLang); return; }   // exception pending
            {
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
            }
            env->DeleteLocalRef(jLang);
        }
    }
    // The comma list is what the hint tables are derived from, so it is
    // built from the SAME accepted codes rather than from the raw array: the
    // two detectors have to be steered by one list, or the switch changes more
    // than which detector runs.
    std::string joined;
    for(size_t at=0; at<codes.size(); at++){ if(at) joined += ","; joined += codes[at]; }
    std::shared_ptr<HintState> state = std::make_shared<HintState>();
    state->list = joined;
    state->codes = codes;
    rebuildScriptLanguageTables(*state);
    std::lock_guard<std::mutex> lock(languageHintMutex);
    g_hintState = std::move(state);
}
// Answers false on an allocation failure rather than inserting a std::string
// built from a null pointer -- see jstringToStd. setDetectSets runs this over
// the whole enabled-language array once per utterance, so it is the hottest of
// the array readers.
static bool jStringArrayToSet(JNIEnv* env, jobjectArray arr, std::unordered_set<std::string>& out){
    out.clear();
    if(!arr) return true;
    jsize count = env->GetArrayLength(arr);
    for(jsize i=0;i<count;i++){
        jstring elem=(jstring)env->GetObjectArrayElement(arr,i);
        if(!elem) continue;
        std::string one;
        if(!jstringToStd(env, elem, one)){ env->DeleteLocalRef(elem); return false; }
        out.insert(one);
        env->DeleteLocalRef(elem);
    }
    return true;
}
extern "C" JNIEXPORT void JNICALL
Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setIsoMap(JNIEnv* env, jclass, jobjectArray jIso2, jobjectArray jIso3){
    if(!jIso2||!jIso3) return;
    jsize iso2Count=env->GetArrayLength(jIso2); jsize iso3Count=env->GetArrayLength(jIso3);
    if(iso3Count<iso2Count) iso2Count=iso3Count;
    for(jsize i=0;i<iso2Count;i++){
        jstring jIso2Element=(jstring)env->GetObjectArrayElement(jIso2,i);
        jstring jIso3Element=(jstring)env->GetObjectArrayElement(jIso3,i);
        if(jIso2Element&&jIso3Element){
            std::string iso2Text, iso3Text;
            if(!jstringToStd(env,jIso2Element,iso2Text) || !jstringToStd(env,jIso3Element,iso3Text)){
                env->DeleteLocalRef(jIso2Element); env->DeleteLocalRef(jIso3Element);
                return;                                    // OOM pending
            }
            g_iso2to3[iso2Text]=iso3Text;
        }
        if(jIso2Element) env->DeleteLocalRef(jIso2Element); if(jIso3Element) env->DeleteLocalRef(jIso3Element);
    }
}
extern "C" JNIEXPORT void JNICALL
Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv* env, jclass, jobjectArray jDetectOk, jobjectArray jEnabled){
    std::unordered_set<std::string> ok, enabled;
    // Both or neither: a half-read pair would leave the detector filtering
    // against a set that does not match the language list it came from.
    if(!jStringArrayToSet(env, jDetectOk, ok)) return;
    if(!jStringArrayToSet(env, jEnabled, enabled)) return;
    LangSetPtr okPtr = std::make_shared<const std::unordered_set<std::string>>(std::move(ok));
    LangSetPtr enabledPtr = std::make_shared<const std::unordered_set<std::string>>(std::move(enabled));
    std::lock_guard<std::mutex> lock(detectSetMutex);
    detectOkIso3Set = std::move(okPtr);
    enabledLangSet = std::move(enabledPtr);
}
// clsCLD2.d's windowed detect over UTF-16 text, with no JNI in it: 64-unit
// windows, the first reliable answer that is an enabled language (or any
// answer with advanced detection disabled), else the script family of the
// window's first letter, else the whole text's. Answers the language, or
// "UNKNOWN". logBuf, when set, collects the [DETECT] lines.
static std::string detectWindowed(const jchar* chars, int textLen,
        const std::string& latinFallback, const std::string& nonLatinFallback,
        bool disableAdvanced, std::string* logBuf){
    LangSetPtr okPtr, enabledPtr;
    { std::lock_guard<std::mutex> lock(detectSetMutex); okPtr = detectOkIso3Set; enabledPtr = enabledLangSet; }
    const std::unordered_set<std::string>& okIso3 = *okPtr;
    const std::unordered_set<std::string>& enabled = *enabledPtr;
    std::string result = "UNKNOWN"; bool done=false;
    int winStart=0;
    while(winStart<textLen && !done){
        int windowEndRaw=winStart+64; int windowEnd = windowEndRaw<textLen?windowEndRaw:textLen;
        std::string windowUtf8 = utf16to8(chars+winStart, windowEnd-winStart);
        if(logBuf){
            int snippetLen = (windowEnd-winStart)<30?(windowEnd-winStart):30;
            char nbuf[32]; snprintf(nbuf,sizeof(nbuf),"%d",windowEnd-winStart);
            *logBuf += std::string("[DETECT] detectWindow windowLen=")+nbuf+" latFall="+latinFallback+" nonLatFall="+nonLatinFallback+" isDisableAdv="+(disableAdvanced?"true":"false")+" snip='"+utf16to8(chars+winStart,snippetLen)+"'\n";
        }
        std::string detectedLang = detectWindowLang(windowUtf8, enabledPtr);
        if(logBuf) *logBuf += std::string("[DETECT] detectWindow rawOut='")+(detectedLang.empty()?"UNKNOWN":detectedLang)+"|"+windowUtf8+"' → result="+(detectedLang.empty()?"null":detectedLang)+"\n";
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
    return result;
}
static bool isUnknownWord(const std::string& s){
    static const char* w = "unknown";
    if(s.size() != 7) return false;
    for(size_t k=0;k<7;k++){ char c=s[k]; if(c>='A'&&c<='Z') c=(char)(c-'A'+'a'); if(c!=w[k]) return false; }
    return true;
}
// ==========================================================================
//  JNI: detectWindowOrSpans    clsCLD2.d, then clsCLD2.f's input, ONE crossing
//  (owner, 2026-10-01). Auto and Google mode ask the windowed detector first
//  and, only when it answers unknown, the aggregate detector -- which needs the
//  fold and the spans of the same text. Element 0 is the windowed answer, a
//  \x01, then the log lines; when that answer is unknown, the fold and the
//  lang / latin / text triples follow, exactly what nativeFoldAndDetect answers
//  for the same text. Any other answer is a one-element array.
// ==========================================================================
extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans(
    JNIEnv* env, jclass, jstring jText, jstring jLat, jstring jNonLat,
    jboolean jDisableAdv, jboolean jWantLog, jboolean jSingleStops)
{
    jclass stringClass = stringClassRef(env);
    if(!stringClass) return nullptr;
    std::string latinFallback, nonLatinFallback;
    if(!jstringToStd(env, jLat, latinFallback) || !jstringToStd(env, jNonLat, nonLatinFallback)) return nullptr;
    std::string log; std::string* logBuf = (jWantLog==JNI_TRUE) ? &log : nullptr;
    std::string result = "UNKNOWN";
    std::vector<jchar> units(1);
    int textLen = 0;
    if(jText){
        textLen = (int)env->GetStringLength(jText);
        // GetStringRegion into OUR buffer: nothing to release, no OOM-null.
        units.resize((size_t)textLen + 1);
        if(textLen > 0) env->GetStringRegion(jText, 0, textLen, units.data());
        if(env->ExceptionCheck()) return nullptr;
        result = detectWindowed(units.data(), textLen, latinFallback, nonLatinFallback, jDisableAdv==JNI_TRUE, logBuf);
    }
    std::vector<std::string> flat;
    flat.push_back(result + "\x01" + log);
    if(isUnknownWord(result)){
        // The fold reads the text the way nativeFoldAndDetect does, so the two
        // carriers cannot disagree. It is made from the UTF-16 units already
        // read (owner, 2026-10-01) rather than read from Java a second time:
        // utf16to8 writes exactly ART's modified UTF-8 -- a pair as 4 bytes, a
        // lone surrogate as 3, U+0000 as C0 80 -- which is what
        // GetStringUTFRegion (jstringToStd) answers.
        std::string text = utf16to8(units.data(), textLen);
        std::vector<std::string> spans = foldAndSpansFlat(text, jSingleStops == JNI_TRUE);
        flat.insert(flat.end(), std::make_move_iterator(spans.begin()), std::make_move_iterator(spans.end()));
    }
    return toJavaStringArray(env, stringClass, flat);
}
// ==========================================================================
//  JNI: nativeFoldAndDetect    libcld2.so getLanguageSpans 0x65392c
//  Script 0 -- a run with nothing classified: digits, punctuation, spaces,
//  emoji -- answers "un" with latin = TRUE and never reaches a detector. That
//  is why a bare number is read in the LATIN preferred language.
//
//  Three steps, with no JNI in any: scanScriptRuns cuts a text into runs of one
//  script (getLanguageSpans' walk), nameSpanTexts gives every run its language
//  (pieces of it, where it holds two), spanTriples merges and flattens them the
//  way nativeGetLanguages carries them.
// ==========================================================================
// A run of one script slot inside a text, before it is named.
struct ScriptRun { int offset; int bytes; int script; };
// A text whose runs are named: `named` false for one that only lies between
// others (a number or punctuation chunk of processDirect), read for its
// sentence ends; `pieces` per run, where each piece of it ends and its language.
struct SpanText {
    const std::string* text = nullptr;
    bool named = false;
    std::vector<ScriptRun> runs;
    std::vector<std::vector<std::pair<int, std::string>>> pieces;
};

// The walk of getLanguageSpans over one text: the runs of one script slot, in
// order. A neutral character (a digit, punctuation, a space) stays with the run
// it is in.
static void scanScriptRuns(const std::string& text, const HintState& hints, std::vector<ScriptRun>& runs){
    const std::vector<std::string>& hintCodes = hints.codes;
    auto classifyScript = [](int codePoint) -> int { return spanScriptSlot(codePoint); };
    auto emitScriptSpan = [&](int start, int len, int script) {
        if (len < 1) return;
        if (start < 0 || start + len > (int)text.size()) return;
        runs.push_back({start, len, script});
    };
    // The lowest slot in `slots` (bit 1 << slot) that an enabled language
    // writes: a detector slot (1-6, 26-32) with a fallback language, a fixed
    // slot whose language is enabled; 0 when none.
    auto hintedCode = [&](const char* code) -> bool {
        for (const std::string& h : hintCodes) {
            if (h == code) return true;
            if ((h == "he" && strcmp(code, "iw") == 0) || (h == "iw" && strcmp(code, "he") == 0)) return true;
        }
        return false;
    };
    auto enabledNeutralSlot = [&](uint64_t slots) -> int {
        if (hintCodes.empty()) return 0;
        for (int slot = 1; slot < 64; slot++) {
            if (!((slots >> slot) & 1)) continue;
            bool enabled = false;
            if (slot <= 6 || (slot >= 26 && slot <= 32))
                enabled = scriptLanguageFallbackOf(hints, slot) != nullptr;
            else if (slot >= 7 && slot <= 25)
                enabled = hintedCode(SCRIPT_FIXED_LANG[slot - 7]);
            else if (slot >= EV_FIXED_SLOT_FIRST && slot < EV_FIXED_SLOT_FIRST + EV_FIXED_SLOT_COUNT)
                enabled = hintedCode(kUniFixedSlotLang[slot - EV_FIXED_SLOT_FIRST]);
            if (enabled) return slot;
        }
        return 0;
    };
    {
        int textLen = (int)text.size();
        int spanStart = 0, curScript = 0, i = 0;
        uint64_t neutralSlots = 0;
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
            if (stringClass < 0) { stringClass = curScript; neutralSlots |= uniMask(kUniScxMask, codePoint); }
            else if (curScript != 0 && stringClass != curScript) { emitScriptSpan(spanStart, i - spanStart, curScript); spanStart = i; }
            i += cpLen;
            curScript = stringClass;
        }
        // A TEXT OF NOTHING BUT NEUTRAL CHARACTERS (owner, 2026-10-21: "ek
        // character bhi nahin chhutna chahie"). Script 0 takes the LATIN
        // language (case 0 above, AutoTTS's rule) -- right for digits, ASCII
        // punctuation and emoji, which name no script, but a lone danda, a
        // Vedic sign, a North Indic fraction, a CJK full stop or bracket, an
        // Arabic comma or a lone harakat went to the English voice too. Those
        // name the scripts that use them (Script_Extensions, kUniScxMask): such
        // a text is read as the lowest of those scripts an enabled language
        // writes, and only when none is does case 0 stand.
        if (curScript == 0 && neutralSlots != 0) curScript = enabledNeutralSlot(neutralSlots);
        emitScriptSpan(spanStart, textLen - spanStart, curScript);
    }
}

// What a span of a shared script slot (1..6) is read in, from these bytes:
//   - one enabled language writes the script: that one, and ML Kit is not
//     asked (what AutoTTS's per-script hint and fallback made of CLD2's
//     answer). The owner's own {hi, en, gu} is only ever this;
//   - two or more: the one of them ML Kit gives the most; when ML Kit names
//     none of them, the script's first enabled language (AutoTTS's
//     scriptLanguageFallback, which its CLD2 path took when CLD2's answer was
//     not a language the user enabled);
//   - none: ML Kit's most likely language of the script, which no voice of the
//     user's reads ("un" when it names none).
// Cached by script + bytes; the cache is dropped when the hints change.
static std::string nameRange(const char* bytes, int len, int script, const HintStatePtr& hintState){
    int detectBytes = len;
    if (detectBytes > 1024) {
        detectBytes = 1024;
        while (detectBytes > 0 && ((unsigned char)bytes[detectBytes] & 0xC0) == 0x80) detectBytes--;
    }
    DetectCache& cache = detectCache();
    syncSpanCache(cache, hintState);
    std::string cacheKey;
    cacheKey.reserve((size_t)detectBytes + 1);
    cacheKey += (char)script;
    cacheKey.append(bytes, (size_t)detectBytes);
    auto cached = cache.spans.find(cacheKey);
    if(cached == cache.spans.end()) {
        std::vector<std::string> langs;
        slotLanguages(script, hintState->codes, langs);
        std::string lang;
        if(langs.size() == 1){
            lang = langs[0];
        } else {
            LidAnswer answer;
            const bool answered = identifyLanguages(bytes, (size_t)detectBytes, answer);
            if(langs.empty()){
                if(answered) lang = mostLikelyInSlot(answer, script);
                if(lang.empty()) lang = "un";
            } else {
                if(answered) lang = mostLikely(answer, langs);
                if(lang.empty()){
                    const char* fallback = scriptLanguageFallbackOf(*hintState, script);
                    lang = fallback ? fallback : langs[0];
                }
            }
        }
        if(cache.spans.size() >= kDetectCacheMax) cache.spans.clear();
        cached = cache.spans.emplace(std::move(cacheKey), std::move(lang)).first;
    }
    return cached->second;
}

// A run named on its own: every script but one two or more enabled languages
// share.
static std::string nameRunAlone(const std::string& text, const ScriptRun& run, const HintStatePtr& hintState){
    const int script = run.script;
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
    // code (spanTriples here), so only the language and the flag are settled
    // early.
    unsigned scriptIdx = (unsigned)(script - 7);
    if (script == 0) {
        // ...but "un" is only where case 0 STARTS. It then consults the
        // LATIN fallback, hard-coded to slot 1 of the same table the
        // detection path uses:
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
        std::string lang = "un";
        if(!hintState->codes.empty()){
            const char* latinFallbackCode = scriptLanguageFallbackOf(*hintState, 1);
            if(latinFallbackCode) lang = latinFallbackCode;
        }
        return lang;
    }
    if (scriptIdx < 19) return SCRIPT_FIXED_LANG[scriptIdx];
    if (script >= EV_FIXED_SLOT_FIRST && script < EV_FIXED_SLOT_FIRST + EV_FIXED_SLOT_COUNT) {
        // A living script no slot above names (owner, 2026-10-16): CLD2 had no
        // model for Ol Chiki, Meetei Mayek, Javanese, Tifinagh ... and answered
        // "un" or "xx-Olck", so the span never reached that language's voice.
        // Decided by the script, like SCRIPT_FIXED_LANG; the language is CLDR's
        // likely one (unicode_tables.h, generated).
        return kUniFixedSlotLang[script - EV_FIXED_SLOT_FIRST];
    }
    if (script >= 1 && script <= 6) return nameRange(text.c_str() + run.offset, run.bytes, script, hintState);
    // Syriac, Thaana, Cherokee, Canadian Aboriginal, Mongolian, Tagalog, Limbu:
    // each written by one language, which reads it.
    if (script >= 26 && script <= 32) return kExtraSlotLangs[script - 26].code;
    // A script no language of today writes (gen_tables.py SLOT_OTHER: the
    // historic ones), so no voice reads it: "un", the non-Latin preferred voice.
    return "un";
}

// The marks that end a sentence: newline ! . ; ?, the Arabic semicolon, question
// mark and full stop (؛ ؟ ۔), the Devanagari danda and double danda (। ॥), the
// ideographic full stop and the fullwidth ! and ? (。！？).
static const uint32_t kSentenceEnd[] = {0xA, 0x21, 0x2E, 0x3B, 0x3F, 0x61B, 0x61F, 0x6D4, 0x964, 0x965,
                                        0x3002, 0xFF01, 0xFF1F};
static bool isSentenceEnd(uint32_t cp){
    for (uint32_t mark : kSentenceEnd) if (cp == mark) return true;
    return false;
}
// Whether a sentence ends between run `a` of text `ta` and run `b` of text `tb`
// (tb >= ta, b after a): a mark of kSentenceEnd in the text between them, the
// texts that lie between included.
static bool sentenceEndBetween(const std::vector<SpanText>& texts, size_t ta, size_t a, size_t tb, size_t b){
    auto scan = [](const std::string& t, size_t from, size_t to) {
        for (size_t i = from; i < to;) {
            int32_t r;
            int used = utf8Decode((const unsigned char*)t.data() + i, to - i, &r);
            if (used <= 0) break;
            if (isSentenceEnd((uint32_t)r)) return true;
            i += (size_t)used;
        }
        return false;
    };
    const ScriptRun& ra = texts[ta].runs[a];
    const ScriptRun& rb = texts[tb].runs[b];
    if (ta == tb) return scan(*texts[ta].text, (size_t)(ra.offset + ra.bytes), (size_t)rb.offset);
    if (scan(*texts[ta].text, (size_t)(ra.offset + ra.bytes), texts[ta].text->size())) return true;
    for (size_t t = ta + 1; t < tb; t++) if (texts[t].text && scan(*texts[t].text, 0, texts[t].text->size())) return true;
    return scan(*texts[tb].text, 0, (size_t)rb.offset);
}

// A word of a run: its bytes, whether a sentence end stands before its first
// letter or after its last, and whether it holds a letter at all.
struct RunWord { int start; int end; bool endBefore; bool endAfter; bool letters; };
// The words of bytes [from, to) of `text`: cut at whitespace, and after a
// sentence end that a letter follows with no space between ("ہے۔یہ").
static void runWords(const std::string& text, int from, int to, std::vector<RunWord>& out){
    out.clear();
    RunWord cur{-1, -1, false, false, false};
    auto close = [&](int at) {
        if (cur.start >= 0) { cur.end = at; out.push_back(cur); }
        cur = RunWord{-1, -1, false, false, false};
    };
    for (int pos = from; pos < to;) {
        int32_t cp;
        int used = utf8Decode((const unsigned char*)text.data() + pos, (size_t)(to - pos), &cp);
        if (used <= 0) used = 1;
        if (isJavaWhitespace(cp)) { close(pos); pos += used; continue; }
        const bool letter = isLetterCodepoint(cp);
        if (letter && cur.start >= 0 && cur.endAfter) close(pos);
        if (cur.start < 0) cur.start = pos;
        if (isSentenceEnd((uint32_t)cp)) {
            if (!cur.letters) cur.endBefore = true;
            cur.endAfter = true;
        } else if (letter || isDecimalDigit(cp)) {
            cur.endAfter = false;
            cur.letters = cur.letters || letter;
        }
        pos += used;
    }
    close(to);
}

// ML Kit's answer for ONE WORD, kept per thread (a screen reader says the same
// words again and again): the languages it names, at most kWordLangsMax of
// them, by canonicalLanguage.
struct WordLang { char tag[8]; float p; };
static const size_t kWordLangsMax = 8;
static const size_t kWordAnswersMax = 2048;
static std::unordered_map<std::string, std::vector<WordLang>>& wordAnswers(){
    static thread_local std::unordered_map<std::string, std::vector<WordLang>> cache;
    return cache;
}
// The letters (Unicode's General_Category L) of bytes [0, len).
static int countLetters(const char* bytes, size_t len){
    int letters = 0;
    for (size_t i = 0; i < len;) {
        int32_t cp;
        int used = utf8Decode((const unsigned char*)bytes + i, len - i, &cp);
        if (used <= 0) break;
        if (isLetterCodepoint((int)cp)) letters++;
        i += (size_t)used;
    }
    return letters;
}
// The index of the largest p and of the next one; p.size() where there is none
// (a language ML Kit does not name has p 0).
static void rankOf(const std::vector<float>& p, size_t& top, size_t& second){
    const size_t none = p.size();
    top = second = none;
    for (size_t l = 0; l < p.size(); l++) {
        if (p[l] <= 0.0f) continue;
        if (top == none || p[l] > p[top]) { second = top; top = l; }
        else if (second == none || p[l] > p[second]) second = l;
    }
}
// How far ML Kit puts confidence `a` ahead of `b`, in nats. Below kWordFloor a
// confidence counts as kWordFloor: ML Kit names no language under 0.01, so one
// it leaves out is unlikely, not impossible.
static const float kWordFloor = 0.005f;
static float leadOf(float a, float b){ return std::log(std::max(a, kWordFloor) / std::max(b, kWordFloor)); }
// ML Kit's answer for one word (cached), restricted to `langs`: p[l] is the
// confidence it gives langs[l], 0 where it does not name it.
static void wordAnswer(const char* bytes, size_t len, const std::vector<std::string>& langs, std::vector<float>& p){
    auto& cache = wordAnswers();
    std::string key(bytes, len);
    auto found = cache.find(key);
    if (found == cache.end()) {
        if (cache.size() >= kWordAnswersMax) cache.clear();
        std::vector<WordLang> answer;
        LidAnswer lid;
        if (identifyLanguages(bytes, len, lid)) {
            std::vector<int> order;
            for (int k = 0; k < lid.n; k++) order.push_back(k);
            std::sort(order.begin(), order.end(), [&](int x, int y){ return lid.confidences[x] > lid.confidences[y]; });
            for (int k : order) {
                if (answer.size() >= kWordLangsMax) break;
                const std::string c = canonicalLanguage(lid.tags[k]);
                if (c.size() >= sizeof(WordLang::tag)) continue;
                WordLang one;
                memcpy(one.tag, c.c_str(), c.size() + 1);
                one.p = lid.confidences[k];
                answer.push_back(one);
            }
        }
        found = cache.emplace(std::move(key), std::move(answer)).first;
    }
    p.assign(langs.size(), 0.0f);
    for (size_t l = 0; l < langs.size(); l++) {
        const std::string c = canonicalLanguage(langs[l]);
        for (const WordLang& one : found->second) if (c == one.tag) { p[l] = one.p; break; }
    }
}
// How strongly one word speaks for each of `langs`: kWordWeight x ln p. A word
// ML Kit names none of `langs` for (or has no answer for) says nothing: every
// language gets the same.
static const float kWordWeight = 0.5f;
static void wordEvidence(const char* bytes, size_t len, const std::vector<std::string>& langs, std::vector<float>& out){
    std::vector<float> p;
    wordAnswer(bytes, len, langs, p);
    out.assign(langs.size(), 0.0f);
    size_t top, second;
    rankOf(p, top, second);
    if (top == langs.size()) return;
    for (size_t l = 0; l < langs.size(); l++) out[l] = kWordWeight * std::log(std::max(p[l], kWordFloor));
}
// A word ML Kit is all but certain of: kWordSureLetters letters or more and
// kWordSureP (0.99) for one of `langs` -- so under 0.01 for every other. Its
// index in `langs`, or langs.size(). Such a word is read in its own language
// even inside a sentence of another (nameSharedScript). Measured over the
// suite's held-out texts: 2,021 Urdu and Arabic words at that certainty, none
// of them wrong; over all eight sets 48 of 10,664, most of them English words
// inside French, German or Italian UI strings, which an English voice does read
// better.
static const float kWordSureP = 0.99f;
static const int kWordSureLetters = 2;
static size_t certainWord(const char* bytes, size_t len, const std::vector<std::string>& langs){
    std::vector<float> p;
    wordAnswer(bytes, len, langs, p);
    size_t top, second;
    rankOf(p, top, second);
    if (top < langs.size() && p[top] >= kWordSureP && countLetters(bytes, len) >= kWordSureLetters) return top;
    return langs.size();
}

// THE LETTERS EACH LANGUAGE WRITES (owner, 2026-10-28: "cldr ka properli jo data
// hai Unicode ka ... sab Kuchh complete karo aur ekadam accurate result
// chahie"). A letter one enabled language writes and another never does settles
// a word between them: "ٹ" is Urdu's and never Arabic's, "إ" Arabic's and never
// Urdu's, "ї" Ukrainian's and never Russian's, kana Japanese and never
// Chinese. Which language writes which letter is CLDR's (kUniLetterLangs<slot>,
// gen_tables.py). For each of `langs` (the enabled languages of shared slot s):
// its bit there (-1 when CLDR has no letters for it), whether no letter rules it
// out (`open`: CLDR has no locale for it, or no auxiliary letters -- it does not
// say which letters the language never writes; a Malay name with é is still
// Malay), and whether ML Kit names it at all.
static std::vector<LetterLang> letterLangs(int s, const std::vector<std::string>& langs){
    std::vector<LetterLang> info(langs.size(), LetterLang{-1, true, true});
    for (size_t l = 0; l < langs.size(); l++) {
        const std::string c = canonicalLanguage(langs[l]);
        for (const UniSharedLang& x : kUniSharedLangs)
            if (x.slot == s && c == x.code) { info[l] = LetterLang{x.bit, x.open, x.mlkit}; break; }
    }
    return info;
}
// Every language of `langs` (the first 64), as bits: bit l is langs[l].
static uint64_t allOf(size_t n){ return n >= 64 ? ~0ULL : ((1ULL << n) - 1); }
// Which of `langs` write every letter of bytes [0, len), as bits. A letter none
// of them writes, or a character that is not a letter of the script, rules
// nothing out; an open language is never ruled out; 0 when two letters rule
// each other out (two languages in one).
static uint64_t lettersAllow(int s, const char* bytes, size_t len, const std::vector<LetterLang>& info){
    const size_t L = std::min<size_t>(info.size(), 64);
    uint64_t allowed = allOf(L), open = 0;
    for (size_t l = 0; l < L; l++) if (info[l].open) open |= 1ULL << l;
    for (size_t i = 0; i < len;) {
        int32_t cp;
        const int used = utf8Decode((const unsigned char*)bytes + i, len - i, &cp);
        if (used <= 0) break;
        i += (size_t)used;
        const uint64_t writers = uniLetterLangs(s, (int)cp);
        if (!writers) continue;
        uint64_t claim = 0;
        for (size_t l = 0; l < L; l++) if (info[l].bit >= 0 && ((writers >> info[l].bit) & 1)) claim |= 1ULL << l;
        if (claim) allowed &= claim | open;
    }
    return allowed;
}
// What one word's letters allow (lettersAllow), or every language when they rule
// out none -- or when ML Kit gives a language they rule out kLetterVetoP or more
// while they allow one it names: CLDR lists the letters a language usually
// writes, and a word ML Kit is that sure of is not ruled out by one it seldom
// writes (the eyelash ra in Nepali, "नपुऱ्याउनुहोस्", ML Kit ne 0.979, was such a
// word until gen_tables.py's CLDR_MISSES gave Nepali that letter). Measured over
// the suite's held-out words (114,811, 9 sets): 8,857 words whose letters allow
// ONE enabled language, one of them wrong -- that Nepali word; ML Kit disputed
// none of the right ones at 0.9 or more.
static const float kLetterVetoP = 0.95f;
// Whether ML Kit can dispute `allowed` at all: it allows a language ML Kit names
// (ML Kit has no Assamese or Sanskrit, so it gives such text another language).
static bool disputable(uint64_t allowed, const std::vector<LetterLang>& info){
    for (size_t l = 0; l < info.size() && l < 64; l++) if (((allowed >> l) & 1) && info[l].named) return true;
    return false;
}
// `allowed`, heard against ML Kit's confidences `p` in `langs`.
static uint64_t lettersHeard(uint64_t allowed, const std::vector<LetterLang>& info, const std::vector<float>& p){
    const uint64_t all = allOf(p.size());
    if (allowed == 0 || allowed == all) return all;
    if (!disputable(allowed, info)) return allowed;
    size_t top, second;
    rankOf(p, top, second);
    if (top < p.size() && top < 64 && !((allowed >> top) & 1) && p[top] >= kLetterVetoP) return all;
    return allowed;
}
static uint64_t wordLetters(int s, const char* bytes, size_t len, const std::vector<std::string>& langs,
                            const std::vector<LetterLang>& info){
    const uint64_t allowed = lettersAllow(s, bytes, len, info);
    if (allowed == 0 || allowed == allOf(langs.size())) return allOf(langs.size());
    if (!disputable(allowed, info)) return allowed;
    std::vector<float> p;
    wordAnswer(bytes, len, langs, p);
    return lettersHeard(allowed, info, p);
}
// Of the languages `allowed` (bits), the one ML Kit's `p` gives the most; when
// it gives none of them anything, `prefer` (the language the words' path chose)
// if allowed, else the first of them in `langs`. `allowed` is never 0 here.
static size_t bestAllowed(uint64_t allowed, const std::vector<float>& p, size_t prefer){
    size_t best = p.size();
    for (size_t l = 0; l < p.size() && l < 64; l++) {
        if (!((allowed >> l) & 1)) continue;
        if (best == p.size() || p[l] > p[best]) best = l;
    }
    if (best < p.size() && p[best] <= 0.0f && prefer < p.size() && prefer < 64 && ((allowed >> prefer) & 1))
        return prefer;
    return best;
}

// ML Kit's confidence in each of `langs` for bytes [0, len) of script slot `s`,
// kept with the span answers (dropped when the hints change, and the hints
// decide `langs`).
static void sharedAnswer(const char* bytes, size_t len, int s, const std::vector<std::string>& langs,
                         const HintStatePtr& hintState, std::vector<float>& p){
    DetectCache& cache = detectCache();
    syncSpanCache(cache, hintState);
    std::string key;
    key.reserve(len + 1);
    key += (char)s;
    key.append(bytes, len);
    auto hit = cache.shared.find(key);
    if (hit == cache.shared.end()) {
        std::vector<float> fresh(langs.size(), 0.0f);
        LidAnswer answer;
        if (identifyLanguages(bytes, len, answer)) {
            for (size_t l = 0; l < langs.size(); l++) {
                const std::string c = canonicalLanguage(langs[l]);
                for (int k = 0; k < answer.n; k++)
                    if (canonicalLanguage(answer.tags[k]) == c) { fresh[l] = answer.confidences[k]; break; }
            }
        }
        if (cache.shared.size() >= kDetectCacheMax) cache.shared.clear();
        hit = cache.shared.emplace(std::move(key), std::move(fresh)).first;
    }
    p = hit->second;
}

// THE LANGUAGE A SCRIPT WAS LAST READ IN (owner, 2026-10-28: "jo bhi language Ke
// Jo Bhi characters ho ... Jo TTS set kara hai use TTS ke pass hi Jana chahie Ek
// single character bhi"). A letter, or a short word, does not say which
// language it is in: "ب" is Urdu's and Arabic's alike, and ML Kit names it
// Chinese (0.885); "ل" it names Arabic (0.920), in Urdu text too. A screen
// reader reads a text and then, character by character or word by word, the
// same text -- so a text ML Kit is not sure of is read in the language its
// script was last read in (a text of two words or more, or one ML Kit is sure
// of). One per script for the whole process; a new language list starts
// afresh. Measured (the suite's held-out texts read one after another, 1,200
// per set, the language changing after P of them; right: whole texts / their
// words read one by one / their first letters one by one): all 9 sets, P 0
// 93.3 / 76.2 / 44.7% -> 98.9 / 98.9 / 99.1, P 0.1 91.1 / 70.3 / 45.6 -> 95.3 /
// 95.1 / 95.0, P 0.5 91.1 / 68.9 / 44.6 -> 88.7 / 94.3 / 90.0; Urdu with
// Arabic, P 0 99.7 / 97.5 / 95.2 -> 100 / 100 / 100, P 0.5 99.0 / 89.7 / 68.5
// -> 97.4 / 99.7 / 99.0. The price: a short text right after the language
// changed (a one-word Arabic label after Urdu) is read in the one before.
struct ScriptContext { std::mutex mutex; std::shared_ptr<const void> hints; std::string lang[7]; };
static ScriptContext& scriptContext(){ static ScriptContext context; return context; }
static std::string contextLanguage(int s, const HintStatePtr& hintState){
    ScriptContext& c = scriptContext();
    std::lock_guard<std::mutex> lock(c.mutex);
    if (c.hints != hintState) { c.hints = hintState; for (std::string& l : c.lang) l.clear(); }
    return (s >= 0 && s < 7) ? c.lang[s] : std::string();
}
static void rememberContext(int s, const std::string& lang, const HintStatePtr& hintState){
    ScriptContext& c = scriptContext();
    std::lock_guard<std::mutex> lock(c.mutex);
    if (c.hints != hintState) { c.hints = hintState; for (std::string& l : c.lang) l.clear(); }
    if (s >= 0 && s < 7) c.lang[s] = lang;
}
// A text ML Kit is sure of: kSureLetters letters or more, and its likeliest
// language given kSureP or more and kSureLead (e^4, 55 times) ahead of the next.
// Over the suite's held-out texts (9 sets, 16,233) ML Kit is sure of 69.5% and
// right in 99.27% of those -- right in 90.06% of all. A text it is not sure of
// keeps ML Kit's own answer over the context only when it has kSureLetters
// letters and that answer leads the context's language by kContextLead.
static const int kSureLetters = 3;
static const float kSureP = 0.9f;
static const float kSureLead = 4.0f;
static const float kContextLead = 3.0f;
static const float kWordLeanP = 0.95f;

// WHERE THE LANGUAGE CHANGES INSIDE A SHARED SCRIPT (owner, 2026-10-28: "ml kit
// per raho ... Urdu ke liye alag TTS ... Arabic ke liye bhi alag"). ML Kit names
// one language for the WHOLE of any text it is given: an Urdu sentence and an
// Arabic one together came back "ur 0.985" with Arabic not even named, so a
// message holding both was read by one voice. So every word of the script is
// asked on its own, and the words choose where the language changes: a Viterbi
// path over them, each word adding its wordEvidence, and a change costing
// kSwitchCost (4) between two words -- a lone word, or two at the very end,
// never moves a sentence's voice -- or kSentenceSwitchCost (0.5) after a
// sentence end. Each
// stretch that path finds is then named by ML Kit on the stretch's WHOLE text
// (its best language, restricted to `langs`), and two neighbouring stretches
// stay apart only when ML Kit confirms both (kKeepMargin, below). A text in one
// language is one stretch, named by one ML Kit call, as before -- and when ML
// Kit is not sure of it, the script's context (above) names it.
static const float kSwitchCost = 4.0f;
static const float kSentenceSwitchCost = 0.5f;
static const float kKeepMargin = 2.3f;
// A language a word's letters rule out (wordLetters) costs the path kLetterCost
// at that word -- more than two changes (2 x kSwitchCost), so a word whose
// letters only one language writes is read in it even inside another's
// sentence, and a stretch is never named a language its letters rule out.
static const float kLetterCost = 9.0f;
static void nameSharedScript(std::vector<SpanText>& texts, int s, const std::vector<std::string>& langs,
                             const HintStatePtr& hintState){
    struct Word { size_t t; size_t r; RunWord w; bool sentence; };
    std::vector<Word> words;
    std::vector<RunWord> inRun;
    bool havePrev = false;
    size_t prevT = 0, prevR = 0;
    for (size_t t = 0; t < texts.size(); t++) {
        if (!texts[t].named) continue;
        for (size_t r = 0; r < texts[t].runs.size(); r++) {
            const ScriptRun& run = texts[t].runs[r];
            if (run.script != s) continue;
            runWords(*texts[t].text, run.offset, run.offset + run.bytes, inRun);
            const bool between = havePrev && sentenceEndBetween(texts, prevT, prevR, t, r);
            for (size_t k = 0; k < inRun.size(); k++) {
                bool sentence = !words.empty() && (words.back().w.endAfter || inRun[k].endBefore);
                if (k == 0) sentence = sentence || between;
                words.push_back({t, r, inRun[k], sentence});
            }
            havePrev = true;
            prevT = t;
            prevR = r;
        }
    }
    const size_t W = words.size(), L = langs.size();
    // What each word's letters allow (wordLetters; every language for a word
    // whose letters say nothing).
    const std::vector<LetterLang> info = letterLangs(s, langs);
    const uint64_t everyLang = allOf(L);
    std::vector<uint64_t> letters(W, everyLang);
    for (size_t i = 0; i < W; i++) {
        if (!words[i].w.letters) continue;
        const std::string& text = *texts[words[i].t].text;
        letters[i] = wordLetters(s, text.data() + words[i].w.start, (size_t)(words[i].w.end - words[i].w.start),
                                 langs, info);
    }
    // The path: one language per word.
    std::vector<int> label(W, 0);
    size_t lettered = 0;
    for (const Word& w : words) lettered += w.w.letters ? 1 : 0;
    if (lettered >= 2 && L >= 2) {
        std::vector<std::vector<float>> evidence(W);
        for (size_t i = 0; i < W; i++) {
            if (!words[i].w.letters) { evidence[i].assign(L, 0.0f); continue; }
            const std::string& text = *texts[words[i].t].text;
            wordEvidence(text.data() + words[i].w.start, (size_t)(words[i].w.end - words[i].w.start), langs, evidence[i]);
            for (size_t l = 0; l < L && l < 64; l++) if (!((letters[i] >> l) & 1)) evidence[i][l] -= kLetterCost;
        }
        std::vector<float> score(evidence[0]), next(L);
        std::vector<std::vector<int>> from(W, std::vector<int>(L, 0));
        for (size_t i = 1; i < W; i++) {
            size_t first = 0, second = 1;
            if (score[second] > score[first]) std::swap(first, second);
            for (size_t l = 2; l < L; l++) {
                if (score[l] > score[first]) { second = first; first = l; }
                else if (score[l] > score[second]) second = l;
            }
            const float cost = words[i].sentence ? kSentenceSwitchCost : kSwitchCost;
            for (size_t l = 0; l < L; l++) {
                const size_t other = l == first ? second : first;
                const float move = score[other] - cost;
                if (move > score[l]) { next[l] = move + evidence[i][l]; from[i][l] = (int)other; }
                else { next[l] = score[l] + evidence[i][l]; from[i][l] = (int)l; }
            }
            score.swap(next);
        }
        int at = 0;
        for (size_t l = 1; l < L; l++) if (score[l] > score[(size_t)at]) at = (int)l;
        for (size_t i = W; i-- > 0;) { label[i] = at; at = from[i][(size_t)at]; }
    }
    // The stretches of one path language, each named by ML Kit on its whole text.
    struct Stretch { size_t first; size_t last; std::string lang; std::vector<float> p; int letters; };
    std::vector<Stretch> stretches;
    for (size_t first = 0; first < W;) {
        size_t last = first;
        while (last + 1 < W && label[last + 1] == label[first]) last++;
        stretches.push_back({first, last, langs[(size_t)label[first]], std::vector<float>(L, 0.0f), 0});
        first = last + 1;
    }
    auto sameRun = [&](size_t x, size_t y) { return words[x].t == words[y].t && words[x].r == words[y].r; };
    // The languages every word of a stretch's letters allow (0: its words' letters
    // rule each other out), and a language's index in `langs`.
    auto lettersOf = [&](const Stretch& st) {
        uint64_t a = everyLang;
        for (size_t i = st.first; i <= st.last; i++) a &= letters[i];
        return a;
    };
    auto indexOf = [&](const std::string& lang) {
        for (size_t l = 0; l < L; l++) if (langs[l] == lang) return l;
        return L;
    };
    auto allows = [](uint64_t allowed, size_t l) { return l < 64 && ((allowed >> l) & 1); };
    // The stretch's text: each run's part of it, a whole run with the spaces and
    // marks around its words, joined by a space between runs.
    auto stretchText = [&](const Stretch& st) {
        std::string joined;
        for (size_t i = st.first; i <= st.last;) {
            size_t end = i;
            while (end < st.last && sameRun(end + 1, i)) end++;
            const ScriptRun& run = texts[words[i].t].runs[words[i].r];
            const int from = (i == 0 || !sameRun(i - 1, i)) ? run.offset : words[i].w.start;
            const int to = (end + 1 >= W || !sameRun(end + 1, i)) ? run.offset + run.bytes : words[end].w.end;
            if (!joined.empty()) joined += ' ';
            joined.append(*texts[words[i].t].text, (size_t)from, (size_t)(to - from));
            i = end + 1;
        }
        return joined;
    };
    // ML Kit on a stretch's whole text (its first 1024 bytes): how likely it
    // makes each of `langs`, and the stretch's language, the likeliest -- the
    // one it had when ML Kit names none of them.
    auto askStretch = [&](Stretch& st) {
        const size_t path = indexOf(st.lang);
        const std::string joined = stretchText(st);
        size_t ask = joined.size();
        if (ask > 1024) {
            ask = 1024;
            while (ask > 0 && ((unsigned char)joined[ask] & 0xC0) == 0x80) ask--;
        }
        sharedAnswer(joined.data(), ask, s, langs, hintState, st.p);
        st.letters = countLetters(joined.data(), joined.size());
        size_t top, second;
        rankOf(st.p, top, second);
        if (top < L) st.lang = langs[top];
        // Only a language every word's letters allow (lettersOf).
        const uint64_t allowed = lettersOf(st);
        if (allowed && allowed != everyLang && !allows(allowed, indexOf(st.lang)))
            st.lang = langs[bestAllowed(allowed, st.p, path)];
    };
    for (Stretch& st : stretches) askStretch(st);
    // Two neighbouring stretches stay apart only when ML Kit, on each one's whole
    // text, makes its own language kKeepMargin (e^2.3, ten times) more likely
    // than the other's. A clause of one language that a few words made look like
    // another ("Всеки човек," in Bulgarian, "ज्या अर्थी," in Marathi) fails that
    // and is joined to its neighbour, and the two are named again together -- ML
    // Kit decides, not the words. Each join leaves one stretch fewer, so this
    // ends.
    auto margin = [&](const Stretch& st, const std::string& other) {
        float own = 0.0f, theirs = 0.0f;
        for (size_t l = 0; l < L; l++) {
            if (langs[l] == st.lang) own = st.p[l];
            if (langs[l] == other) theirs = st.p[l];
        }
        return leadOf(own, theirs);
    };
    for (size_t k = 0; k + 1 < stretches.size();) {
        Stretch& a = stretches[k];
        Stretch& b = stretches[k + 1];
        // A stretch's letters that rule the other's language out confirm it too.
        auto confirmed = [&](const Stretch& st, const std::string& other) {
            return !allows(lettersOf(st), indexOf(other)) || margin(st, other) >= kKeepMargin;
        };
        const bool apart = a.lang != b.lang && confirmed(a, b.lang) && confirmed(b, a.lang);
        if (apart) { k++; continue; }
        const std::string longer = (a.last - a.first >= b.last - b.first) ? a.lang : b.lang;
        a.last = b.last;
        stretches.erase(stretches.begin() + (std::ptrdiff_t)(k + 1));
        stretches[k].lang = longer;
        askStretch(stretches[k]);
        if (k > 0) k--;
    }
    // Sure, by ML Kit's own answer on the stretch's text: kSureLetters, kSureP,
    // kSureLead -- and a single word only at kWordSureP (0.99): ML Kit is right
    // in 98.9% of the Urdu and Arabic words it gives 0.9, and in all of those it
    // gives 0.99.
    auto wordsOf = [&](const Stretch& st) {
        size_t n = 0;
        for (size_t i = st.first; i <= st.last; i++) n += words[i].w.letters ? 1 : 0;
        return n;
    };
    // And sure when its letters allow its language alone: "ٹ" is Urdu, read one
    // letter at a time or not.
    auto sure = [&](const Stretch& st) {
        const uint64_t own = lettersOf(st);
        if (own && (own & (own - 1)) == 0 && allows(own, indexOf(st.lang))) return true;
        size_t top, second;
        rankOf(st.p, top, second);
        if (top == L) return false;
        if (wordsOf(st) <= 1) return st.letters >= kWordSureLetters && st.p[top] >= kWordSureP;
        return st.letters >= kSureLetters && st.p[top] >= kSureP
               && leadOf(st.p[top], second < L ? st.p[second] : 0.0f) >= kSureLead;
    };
    if (stretches.size() == 1 && !sure(stretches[0])) {
        // Not sure: the language the script was last read in, unless ML Kit
        // puts its own answer kContextLead ahead of it -- on two words or more,
        // or on one word it gives kWordLeanP; with no context, ML Kit's answer,
        // and when it names none of `langs`, the script's first enabled
        // language (AutoTTS's scriptLanguageFallback).
        // Neither when the stretch's letters rule it out.
        Stretch& st = stretches[0];
        size_t top, second;
        rankOf(st.p, top, second);
        const uint64_t own = lettersOf(st) ? lettersOf(st) : everyLang;
        const std::string context = contextLanguage(s, hintState);
        size_t at = L;
        for (size_t l = 0; l < L; l++) if (langs[l] == context && allows(own, l)) at = l;
        if (at < L) {
            const bool ownLean = top < L && top != at && st.letters >= kSureLetters
                                 && (wordsOf(st) >= 2 || st.p[top] >= kWordLeanP)
                                 && leadOf(st.p[top], st.p[at]) >= kContextLead;
            if (!ownLean) st.lang = langs[at];
        } else if (top == L) {
            const char* fallback = scriptLanguageFallbackOf(*hintState, s);
            st.lang = (fallback && allows(own, indexOf(fallback))) ? fallback : langs[bestAllowed(own, st.p, indexOf(st.lang))];
        }
    }
    // What the script was last read in: the last stretch of two words or more,
    // or one ML Kit is sure of. (A letter or a lone word read on its own does
    // not move it: that is how a text is read again, a piece at a time.)
    for (size_t k = stretches.size(); k-- > 0;)
        if (wordsOf(stretches[k]) >= 2 || sure(stretches[k])) { rememberContext(s, stretches[k].lang, hintState); break; }
    std::vector<std::string> wordLang(W);
    for (const Stretch& st : stretches)
        for (size_t i = st.first; i <= st.last; i++) wordLang[i] = st.lang;
    // A word ML Kit is all but certain of (certainWord), or whose letters only
    // one language writes, is read in its own language, whatever its sentence
    // is read in -- the sentence keeps the language it was named in above. A
    // word's letters outrank ML Kit.
    if (L >= 2) {
        for (size_t i = 0; i < W; i++) {
            if (!words[i].w.letters) continue;
            const uint64_t own = letters[i];
            if (own != everyLang && (own & (own - 1)) == 0) { wordLang[i] = langs[(size_t)__builtin_ctzll(own)]; continue; }
            const std::string& text = *texts[words[i].t].text;
            const char* bytes = text.data() + words[i].w.start;
            const size_t len = (size_t)(words[i].w.end - words[i].w.start);
            const size_t c = certainWord(bytes, len, langs);
            if (c < L && allows(own, c)) { wordLang[i] = langs[c]; continue; }
            if (!allows(own, indexOf(wordLang[i]))) {
                std::vector<float> p;
                wordAnswer(bytes, len, langs, p);
                wordLang[i] = langs[bestAllowed(own, p, (size_t)label[i])];
            }
        }
    }
    // Pieces: a word ends where the next word of its run starts, the run's last
    // word at the run's end.
    for (size_t i = 0; i < W; i++) {
        const Word& w = words[i];
        const ScriptRun& run = texts[w.t].runs[w.r];
        const bool lastOfRun = i + 1 >= W || words[i + 1].t != w.t || words[i + 1].r != w.r;
        const int end = lastOfRun ? run.offset + run.bytes : words[i + 1].w.start;
        texts[w.t].pieces[w.r].push_back({end, wordLang[i]});
    }
    // A run with no word in it (none can have: a run starts at a letter) still
    // gets its language.
    for (SpanText& t : texts) {
        if (!t.named) continue;
        for (size_t r = 0; r < t.runs.size(); r++)
            if (t.runs[r].script == s && t.pieces[r].empty())
                t.pieces[r].push_back({t.runs[r].offset + t.runs[r].bytes, W ? wordLang[0] : langs[0]});
    }
}

// Every run is named. A script one enabled language writes (or none, or a script
// one language is read in) is named run by run (nameRunAlone); a script two or
// more enabled languages write is named word by word (nameSharedScript), over
// every text of the utterance together -- processDirect's chunks are one
// utterance -- so a word of another script standing between two of its runs
// does not leave either alone to be guessed.
static void nameSpanTexts(std::vector<SpanText>& texts, const HintStatePtr& hintState){
    std::vector<std::string> slotLangs[7];
    for (int s = 1; s <= 6; s++) slotLanguages(s, hintState->codes, slotLangs[s]);
    for (SpanText& t : texts) {
        if (!t.named) continue;
        t.pieces.assign(t.runs.size(), {});
        for (size_t r = 0; r < t.runs.size(); r++) {
            const ScriptRun& run = t.runs[r];
            if (run.script >= 1 && run.script <= 6 && slotLangs[run.script].size() >= 2) continue;
            t.pieces[r].push_back({run.offset + run.bytes, nameRunAlone(*t.text, run, hintState)});
        }
    }
    for (int s = 1; s <= 6; s++)
        if (slotLangs[s].size() >= 2) nameSharedScript(texts, s, slotLangs[s], hintState);
}

// The named runs of one text as nativeGetLanguages carries them: lang, latin
// flag, text triples, flattened; a piece that follows one of the same language
// and flag is merged into it, and past kMaxSpans the last span stretches.
static std::vector<std::string> spanTriples(SpanText& t, bool blankWhole){
    struct ScriptSpan { int offset; int bytes; std::string lang; bool latin; };
    std::vector<ScriptSpan> spans;
    const int kMaxSpans = 128;
    auto pushSpan = [&](int start, int len, const std::string& lang, bool latin) {
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
    for (size_t r = 0; r < t.runs.size(); r++) {
        const ScriptRun& run = t.runs[r];
        const bool latin = run.script == 1 || run.script == 0;
        int at = run.offset;
        for (const auto& piece : t.pieces[r]) {
            if (piece.first > at) pushSpan(at, piece.first - at, piece.second, latin);
            at = piece.first;
        }
    }
    const std::string& text = *t.text;
    std::vector<std::string> flat;
    flat.reserve(spans.size() * 3);
    const int textBytesTotal = (int)text.size();
    for(size_t scriptIdx=0; scriptIdx<spans.size(); scriptIdx++){
        ScriptSpan& span = spans[scriptIdx];
        // nativeGetLanguages CLAMPS rather than dropping (0x652f8c): a negative
        // offset or one past the end is skipped, and an overlong span is cut to
        // what is left of the text. Neither can happen -- the walk only emits
        // ranges inside it -- but this is the shape.
        if(span.offset < 0) continue;
        if(textBytesTotal <= span.offset) continue;
        int spanBytes = span.bytes;
        if(span.offset + spanBytes > textBytesTotal) spanBytes = textBytesTotal - span.offset;
        if(spanBytes < 1) continue;
        flat.push_back(std::move(span.lang));
        flat.push_back(span.latin ? "1" : "0");
        // blankWhole: a span that IS the whole text goes as "" -- the same
        // string processDirect used to build here and then clear.
        if (blankWhole && span.offset == 0 && spanBytes == textBytesTotal) flat.emplace_back();
        else flat.push_back(text.substr(span.offset, (size_t)spanBytes));
    }
    return flat;
}

// The spans of one text: lang, latin flag, text triples, flattened, exactly as
// nativeFoldAndDetect's array carries them.
static std::vector<std::string> languageSpansFlat(const std::string& text, bool blankWhole = false){
    const HintStatePtr hintState = currentHintState();
    std::vector<SpanText> texts(1);
    texts[0].text = &text;
    texts[0].named = true;
    scanScriptRuns(text, *hintState, texts[0].runs);
    nameSpanTexts(texts, hintState);
    return spanTriples(texts[0], blankWhole);
}
// The fold, then the spans of the folded text: element 0 is the folded text,
// lang / latin / text triples follow. With `singleStops` and a fold that is a
// single UTF-16 unit the spans are not computed, because the caller answers
// that case without them. Shared by nativeFoldAndDetect and processDirect.
//
// `alreadyFolded` (owner, 2026-10-01): processDirect's chunks are pieces of the
// text buildMixChunks has ALREADY folded, cut at code-point boundaries, plus the
// ASCII spaces respaceDigits adds. The fold maps code point by code point and
// f(f(c)) == f(c) for all 1,114,112 code points (corebytes/foldtwice.cpp), so
// folding such a chunk again cannot change it; it only cost a scan and a copy.
static std::vector<std::string> foldAndSpansFlat(const std::string& text, bool singleStops, bool alreadyFolded, bool forRecord){
    // Text with nothing to fold is used as it is, not copied (owner, 2026-10-01).
    std::string foldedCopy;
    bool fold = !alreadyFolded && needsFancyFold(text);
    if(fold) foldedCopy = normalizeFancyText(text);
    const std::string& folded = fold ? foldedCopy : text;
    // UTF-16 length of the folded text: one unit below U+10000, two above.
    // Only "is it exactly one" matters here.
    int units = 0;
    for(size_t k=0; k<folded.size() && units<2; k++){
        unsigned char b=(unsigned char)folded[k];
        if((b & 0xC0) == 0x80) continue;
        units += (b >= 0xF0) ? 2 : 1;
    }
    std::vector<std::string> flat;
    // forRecord (processDirect): the fold slot is "" -- the record already
    // carries the chunk's text, which IS the fold there (alreadyFolded).
    if(forRecord) flat.emplace_back(); else flat.push_back(folded);
    if(!(singleStops && units == 1)){
        std::vector<std::string> spans = languageSpansFlat(folded, forRecord);
        flat.insert(flat.end(), std::make_move_iterator(spans.begin()), std::make_move_iterator(spans.end()));
    }
    return flat;
}
// processDirect's records: for each chunk of type 0, 1 or 2 that is not empty,
// what foldAndSpansFlat(chunk, singleStops, true, true) answers for it -- the
// fold slot as "" (the record carries the chunk's text, which is the fold:
// buildMixChunks folded it), then the triples, a span covering the whole chunk
// as "" -- and nothing for any other chunk. Unlike foldAndSpansFlat one chunk
// at a time, the chunks' runs are named together (nameSpanTexts), the chunks
// in between read for their sentence ends.
static std::vector<std::vector<std::string>> chunksSpansFlat(const std::vector<ChunkResult>& chunks, bool singleStops){
    std::vector<std::vector<std::string>> out(chunks.size());
    const HintStatePtr hintState = currentHintState();
    std::vector<SpanText> texts(chunks.size());
    for (size_t i = 0; i < chunks.size(); i++) {
        texts[i].text = &chunks[i].text;
        if (chunks[i].type < 0 || chunks[i].type > 2 || chunks[i].text.empty()) continue;
        out[i].emplace_back();
        // The UTF-16 length: with singleStops a single unit is answered
        // without spans (foldAndSpansFlat).
        const std::string& t = chunks[i].text;
        int units = 0;
        for (size_t k = 0; k < t.size() && units < 2; k++) {
            unsigned char b = (unsigned char)t[k];
            if ((b & 0xC0) == 0x80) continue;
            units += (b >= 0xF0) ? 2 : 1;
        }
        if (singleStops && units == 1) continue;
        texts[i].named = true;
        scanScriptRuns(t, *hintState, texts[i].runs);
    }
    nameSpanTexts(texts, hintState);
    for (size_t i = 0; i < chunks.size(); i++) {
        if (!texts[i].named) continue;
        std::vector<std::string> triples = spanTriples(texts[i], true);
        out[i].insert(out[i].end(), std::make_move_iterator(triples.begin()), std::make_move_iterator(triples.end()));
    }
    return out;
}
// Every element is checked and released (2026-09-30). NewStringUTF can fail
// with an OutOfMemoryError pending, and JNI forbids the next call
// (SetObjectArrayElement) while one is; CheckJNI aborts the process on it.
// And each jstring is a LOCAL reference that lives until the native method
// returns -- up to 385 of them here (the folded text + 128 spans x 3) -- so it
// is dropped as soon as the array holds it.
// ONE Java string per distinct short value (owner, 2026-10-06). An answer
// repeats the same few short strings over and over -- every record's field
// count, its type, the retired "0", the "" fold slot, each span's "0"/"1"
// latin flag and its language code -- and each used to be its own
// NewStringUTF. Java strings are immutable, so one jstring can fill any
// number of array slots; the elements read back are equal() to what they were.
// Only strings of at most kShareMax bytes are shared, and at most kShareSlots
// of them, so the local references held until the end stay few.
// THE VALUES EVERY ANSWER REPEATS ARE MADE ONCE PER PROCESS (owner, 2026-10-07,
// item 4). A record's field count, its type, the retired "0", the "" fold slot
// and each span's "0"/"1" latin flag are the same handful of strings in every
// answer of every utterance; the per-call sharing above still made each one
// fresh on every call. A decimal 0..kConstMax-1 or "" is a GLOBAL ref now,
// created the first time it is needed and never again (a global ref is valid
// on every thread, and two threads racing make one each and keep one).
// Strings are immutable, so the slots read back exactly as before.
static jstring constantJavaString(JNIEnv* env, const std::string& value){
    constexpr int kConstMax = 400;   // 4 + 1 + 3 x 128 spans, the largest count
    static std::atomic<jstring> slots[kConstMax + 1];
    int index;
    if(value.empty()) index = kConstMax;
    else {
        if(value.size() > 3 || (value.size() > 1 && value[0] == '0')) return nullptr;
        index = 0;
        for(char c : value){ if(c < '0' || c > '9') return nullptr; index = index * 10 + (c - '0'); }
        if(index >= kConstMax) return nullptr;
    }
    jstring have = slots[index].load(std::memory_order_acquire);
    if(have) return have;
    jstring local = env->NewStringUTF(value.c_str());
    if(!local) return nullptr;                                   // exception pending
    jstring global = static_cast<jstring>(env->NewGlobalRef(local));
    env->DeleteLocalRef(local);
    if(!global) return nullptr;
    jstring expected = nullptr;
    if(!slots[index].compare_exchange_strong(expected, global, std::memory_order_acq_rel)){
        env->DeleteGlobalRef(global);
        return expected;
    }
    return global;
}
static jobjectArray toJavaStringArray(JNIEnv* env, jclass stringClass, const std::vector<std::string>& flat){
    jobjectArray arr=env->NewObjectArray((jsize)flat.size(), stringClass, nullptr);
    if(!arr) return nullptr;                                  // OOM pending
    constexpr size_t kShareMax = 8;
    constexpr int kShareSlots = 32;
    const std::string* sharedKey[kShareSlots];
    jstring sharedRef[kShareSlots];
    int shared = 0;
    auto release = [&](){ for(int k=0;k<shared;k++) env->DeleteLocalRef(sharedRef[k]); };
    for(jsize i=0;i<(jsize)flat.size();i++){
        const std::string& value = flat[(size_t)i];
        jstring element = constantJavaString(env, value);
        if(!element && env->ExceptionCheck()){ release(); env->DeleteLocalRef(arr); return nullptr; }
        bool keep = element != nullptr;                          // a global ref is never deleted here
        if(!element && value.size() <= kShareMax){
            for(int k=0;k<shared;k++) if(*sharedKey[k] == value){ element = sharedRef[k]; keep = true; break; }
        }
        if(!element){
            element = newJavaString(env, value);
            if(!element){ release(); env->DeleteLocalRef(arr); return nullptr; }   // exception pending
            if(value.size() <= kShareMax && shared < kShareSlots){
                sharedKey[shared] = &value; sharedRef[shared] = element; shared++; keep = true;
            }
        }
        env->SetObjectArrayElement(arr, i, element);
        if(!keep) env->DeleteLocalRef(element);
        if(env->ExceptionCheck()){ release(); env->DeleteLocalRef(arr); return nullptr; }
    }
    release();
    return arr;
}
extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect(JNIEnv* env, jclass, jstring jText, jboolean singleStops){
    jclass stringClass = stringClassRef(env);
    if(!stringClass) return nullptr;
    std::string text;
    if(jText && !jstringToStd(env, jText, text)) return nullptr;
    return toJavaStringArray(env, stringClass, foldAndSpansFlat(text, singleStops == JNI_TRUE));
}

// ==========================================================================
//  RegisterNatives: every entry point bound at load, ONE AT A TIME, so a
//  signature that ever drifted from the Kotlin declaration costs only that
//  method its binding (ART then finds it by its exported name, as before) and
//  never the load. The Java_* names stay exported for exactly that reason.
//  tools/verify/registernatives/run.sh checks every signature against the
//  compiled class.
// ==========================================================================
static void registerNatives(JNIEnv* env){
    jclass cls = env->FindClass("com/sachinbaria/easyvoice/EasyVoiceTtsService");
    if (!cls) { env->ExceptionClear(); return; }
    static const JNINativeMethod methods[] = {
        {const_cast<char*>("processDirect"),
         const_cast<char*>("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;ILjava/lang/String;ILjava/lang/String;ZZILjava/lang/String;IZZZ)[Ljava/lang/String;"),
         reinterpret_cast<void*>(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_processDirect)},
        {const_cast<char*>("nativeFoldAndDetect"), const_cast<char*>("(Ljava/lang/String;Z)[Ljava/lang/String;"),
         reinterpret_cast<void*>(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_nativeFoldAndDetect)},
        {const_cast<char*>("setIsoMap"), const_cast<char*>("([Ljava/lang/String;[Ljava/lang/String;)V"),
         reinterpret_cast<void*>(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setIsoMap)},
        {const_cast<char*>("setLanguageHints"), const_cast<char*>("([Ljava/lang/String;)V"),
         reinterpret_cast<void*>(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setLanguageHints)},
        {const_cast<char*>("setDetectSets"), const_cast<char*>("([Ljava/lang/String;[Ljava/lang/String;)V"),
         reinterpret_cast<void*>(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_setDetectSets)},
        {const_cast<char*>("detectWindowOrSpans"),
         const_cast<char*>("(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZ)[Ljava/lang/String;"),
         reinterpret_cast<void*>(Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_detectWindowOrSpans)},
    };
    for (const JNINativeMethod& m : methods) {
        if (env->RegisterNatives(cls, &m, 1) != JNI_OK) env->ExceptionClear();
    }
    env->DeleteLocalRef(cls);
}

// ==========================================================================
//  ML KIT, ASKED FROM HERE (owner, 2026-10-28: "ML Kit use karenge har jagah").
//  JNI_OnLoad finds EasyVoiceTtsService.mlkitIdentify (Kotlin; kept by
//  proguard-rules.pro, since only this file calls it) and installs jniIdentify
//  as the language identifier. It runs on the thread that is detecting -- one
//  that called into this library from Java, so it is attached -- and hands the
//  text to Kotlin, which asks ML Kit and answers its languages as lines of
//  "tag confidence-in-millionths" (integers, so no locale can read them).
// ==========================================================================
static JavaVM* g_javaVm = nullptr;
static jclass g_serviceClass = nullptr;
static jmethodID g_mlkitIdentify = nullptr;
static int jniIdentify(const char* utf8, size_t len, char (*tags)[16], float* confidences, int max){
    JNIEnv* env = nullptr;
    if(!g_javaVm || g_javaVm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK || !env) return 0;
    jstring text = newJavaString(env, std::string(utf8, len));
    if(!text){ env->ExceptionClear(); return 0; }
    jstring reply = static_cast<jstring>(env->CallStaticObjectMethod(g_serviceClass, g_mlkitIdentify, text));
    env->DeleteLocalRef(text);
    if(env->ExceptionCheck()){
        env->ExceptionClear();
        if(reply) env->DeleteLocalRef(reply);
        return 0;
    }
    std::string lines;
    const bool read = jstringToStd(env, reply, lines);
    if(reply) env->DeleteLocalRef(reply);
    if(!read){ env->ExceptionClear(); return 0; }
    int n = 0;
    for(size_t at = 0; at < lines.size() && n < max;){
        size_t end = lines.find('\n', at);
        if(end == std::string::npos) end = lines.size();
        const size_t space = lines.find(' ', at);
        if(space != std::string::npos && space > at && space < end && space - at < 16 && space + 1 < end){
            long millionths = 0;
            bool digits = true;
            for(size_t i = space + 1; i < end && digits; i++){
                const char c = lines[i];
                if(c < '0' || c > '9') digits = false;
                else if(millionths < 1000000) millionths = millionths * 10 + (c - '0');
            }
            if(digits){
                memcpy(tags[n], lines.data() + at, space - at);
                tags[n][space - at] = '\0';
                confidences[n] = (float)std::min(millionths, 1000000L) / 1000000.0f;
                n++;
            }
        }
        at = end + 1;
    }
    return n;
}
static void installLanguageIdentifier(JavaVM* vm, JNIEnv* env){
    jclass cls = env->FindClass("com/sachinbaria/easyvoice/EasyVoiceTtsService");
    if(!cls){ env->ExceptionClear(); return; }
    jmethodID method = env->GetStaticMethodID(cls, "mlkitIdentify", "(Ljava/lang/String;)Ljava/lang/String;");
    if(!method){ env->ExceptionClear(); env->DeleteLocalRef(cls); return; }
    jclass global = static_cast<jclass>(env->NewGlobalRef(cls));
    env->DeleteLocalRef(cls);
    if(!global){ env->ExceptionClear(); return; }
    g_serviceClass = global;
    g_mlkitIdentify = method;
    g_javaVm = vm;
    evSetLanguageIdentifier(jniIdentify);
}
