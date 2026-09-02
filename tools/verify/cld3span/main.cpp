// Regression harness for the SPAN SITE -- nativeGetLanguages -- run against the
// REAL native core. It started life covering only the CLD3 arm, which is where
// its directory name comes from; it now also covers the CLD2 answer filter and
// the script-0 case, both read back off the arm64 of getLanguageSpans.
//
// Not a sliced copy: this links tts_engine_core.cpp itself, together with CLD2,
// CLD3 and protobuf, starts a JVM so there is a genuine JNIEnv, and calls the
// exported entry points the app calls -- setLanguageHints, setDetectSets and
// nativeGetLanguages. What it asserts is therefore what the device does.
//
// It exists because of one bug, reported from a device log on 2026-08-27. With
// CLD3 enabled the Latin name "MEET Choudhary " was spoken by the Hindi voice;
// with CLD2 it was read in English. The chunking was identical in both runs --
// only the language of the first span differed. Cause: CLD3 answered "hi" with
// is_reliable = 0 and probability 0.495, its own top-3 loop correctly rejected
// that candidate, and the fallthrough returned it anyway because the span site
// passed nullptr for reliableOut and never looked. Both arms of
// detectWindowLang already answer UNKNOWN when the detector is unsure; this
// site did not.
#include <jni.h>
#include <cstdio>
#include <cstring>
#include <string>
#include <vector>

extern "C" {
JNIEXPORT void JNICALL Java_com_tts_easyvoice_NativeEngine_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_tts_easyvoice_NativeEngine_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_tts_easyvoice_NativeEngine_nativeGetLanguages(JNIEnv*, jclass, jstring, jboolean);
JNIEXPORT jstring JNICALL Java_com_tts_easyvoice_NativeEngine_processDirect(
    JNIEnv*, jclass, jobject, jint, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean, jboolean);
}

static JNIEnv* env = nullptr;

static jobjectArray toArray(const std::vector<std::string>& items){
    jclass stringClass = env->FindClass("java/lang/String");
    jobjectArray out = env->NewObjectArray((jsize)items.size(), stringClass, nullptr);
    for(size_t at = 0; at < items.size(); at++)
        env->SetObjectArrayElement(out, (jsize)at, env->NewStringUTF(items[at].c_str()));
    return out;
}

static std::string fromJString(jstring s){
    const char* chars = env->GetStringUTFChars(s, nullptr);
    std::string out(chars ? chars : "");
    if(chars) env->ReleaseStringUTFChars(s, chars);
    return out;
}

// nativeGetLanguages returns a flat array of triples: language, isLatin, text.
struct Span { std::string lang, latin, text; };

static std::vector<Span> spansOf(const std::string& text, bool useCld3){
    jobjectArray raw = Java_com_tts_easyvoice_NativeEngine_nativeGetLanguages(
        env, nullptr, env->NewStringUTF(text.c_str()), useCld3 ? JNI_TRUE : JNI_FALSE);
    std::vector<Span> out;
    if(!raw) return out;
    const jsize n = env->GetArrayLength(raw);
    for(jsize at = 0; at + 2 < n; at += 3){
        Span one;
        one.lang  = fromJString((jstring)env->GetObjectArrayElement(raw, at));
        one.latin = fromJString((jstring)env->GetObjectArrayElement(raw, at + 1));
        one.text  = fromJString((jstring)env->GetObjectArrayElement(raw, at + 2));
        out.push_back(one);
    }
    return out;
}

static int failures = 0;

// A case names the span it cares about by index, so a change in how the rest of
// the text splits does not silently turn the assertion into a no-op: the span
// count is checked too.
static void expect(const char* label, const std::string& text, bool useCld3,
                   size_t spanIndex, size_t spanCount, const std::string& wantLang){
    const std::vector<Span> spans = spansOf(text, useCld3);
    const char* detector = useCld3 ? "CLD3" : "CLD2";
    if(spans.size() != spanCount){
        printf("  FAIL  %-46s %s: expected %zu spans, got %zu\n",
               label, detector, spanCount, spans.size());
        for(size_t at = 0; at < spans.size(); at++)
            printf("          span %zu  lang=%-8s latin=%s  \"%.48s\"\n",
                   at, spans[at].lang.c_str(), spans[at].latin.c_str(), spans[at].text.c_str());
        failures++;
        return;
    }
    const std::string got = spans[spanIndex].lang;
    if(got != wantLang){
        printf("  FAIL  %-46s %s: span %zu is \"%s\", expected \"%s\"   text=\"%.40s\"\n",
               label, detector, spanIndex, got.c_str(), wantLang.c_str(), spans[spanIndex].text.c_str());
        failures++;
        return;
    }
    printf("  ok    %-46s %s: span %zu = %s\n", label, detector, spanIndex, got.c_str());
}

// Script 0 -- a run in which nothing was classified -- settles both fields
// itself, so both have to be asserted.
static void expectSpan(const char* label, const std::string& text, bool useCld3,
                       size_t spanIndex, size_t spanCount,
                       const std::string& wantLang, const char* wantLatin){
    const std::vector<Span> spans = spansOf(text, useCld3);
    const char* detector = useCld3 ? "CLD3" : "CLD2";
    if(spans.size() != spanCount){
        printf("  FAIL  %-46s %s: expected %zu spans, got %zu\n",
               label, detector, spanCount, spans.size());
        failures++;
        return;
    }
    if(spans[spanIndex].lang != wantLang || spans[spanIndex].latin != wantLatin){
        printf("  FAIL  %-46s %s: span %zu is %s/latin=%s, expected %s/latin=%s\n",
               label, detector, spanIndex, spans[spanIndex].lang.c_str(),
               spans[spanIndex].latin.c_str(), wantLang.c_str(), wantLatin);
        failures++;
        return;
    }
    printf("  ok    %-46s %s: span %zu = %s latin=%s\n",
           label, detector, spanIndex, spans[spanIndex].lang.c_str(), spans[spanIndex].latin.c_str());
}

static void enable(const std::vector<std::string>& codes){
    Java_com_tts_easyvoice_NativeEngine_setLanguageHints(env, nullptr, toArray(codes));
    // The iso3 set only has to be non-empty and parallel; the span site reads
    // the hint codes, not this one.
    Java_com_tts_easyvoice_NativeEngine_setDetectSets(env, nullptr, toArray(codes), toArray(codes));
}

int main(){
    JavaVMInitArgs vmArgs;
    vmArgs.version = JNI_VERSION_1_8;
    vmArgs.nOptions = 0;
    vmArgs.options = nullptr;
    vmArgs.ignoreUnrecognized = JNI_TRUE;
    JavaVM* vm = nullptr;
    if(JNI_CreateJavaVM(&vm, (void**)&env, &vmArgs) != JNI_OK){
        printf("could not start a JVM\n");
        return 2;
    }

    // The reporter's configuration: English on a dedicated engine, Gujarati and
    // Hindi on Google. Only "en" is a Latin-script language here, so the
    // per-script hint for Latin is unambiguous and the per-script fallback for
    // Latin is English -- which is exactly what CLD2 used to get right and the
    // CLD3 arm got wrong.
    const std::vector<std::string> enabled = {"en", "gu", "hi"};
    Java_com_tts_easyvoice_NativeEngine_setLanguageHints(env, nullptr, toArray(enabled));
    const std::vector<std::string> iso3 = {"eng", "guj", "hin"};
    Java_com_tts_easyvoice_NativeEngine_setDetectSets(env, nullptr, toArray(iso3), toArray(enabled));

    // The exact utterance from the log (id 419 under CLD2, id 438 under CLD3).
    const std::string reported =
        "MEET Choudhary \xE0\xA4\xA6\xE0\xA4\xBE\xE0\xA4\xA6\xE0\xA4\xBE "
        "\xE0\xA4\x95\xE0\xA4\xB9\xE0\xA4\xAF\xE0\xA4\xBE "
        "\xE0\xA4\x95\xE0\xA4\xB0\xE0\xA4\xA6\xE0\xA4\xBE "
        "\xE0\xA4\xA5\xE0\xA4\xBE Received at 11:33 AM";

    printf("the reported utterance, both detectors\n");
    expect("Latin name span is English", reported, false, 0, 3, "en");
    expect("Latin name span is English", reported, true,  0, 3, "en");
    expect("Devanagari span is Hindi",   reported, false, 1, 3, "hi");
    expect("Devanagari span is Hindi",   reported, true,  1, 3, "hi");
    expect("trailing Latin span is English", reported, false, 2, 3, "en");
    expect("trailing Latin span is English", reported, true,  2, 3, "en");

    // Short Latin strings the probe showed CLD3 answering with a non-Latin
    // language, reliably or not. None may reach a non-Latin voice: either the
    // answer is an enabled Latin language, or the per-script fallback gives en.
    printf("\nshort Latin strings CLD3 is unsure about\n");
    const char* shortLatin[] = {
        "MEET Choudhary ", "Meet Choudhary", "Rahul Sharma", "Priya Patel",
        "Amit Kumar", "Ramesh bhai", "Save", "Easy Voice", "Settings",
        "WhatsApp", "POCO Launcher", "Telegram,Unlocked", "OK", "Play", nullptr };
    for(int at = 0; shortLatin[at]; at++)
        expect(shortLatin[at], shortLatin[at], true, 0, 1, "en");

    // A language of the WRONG SCRIPT must not win a Latin span just because the
    // user has it enabled. CLD2 is steered away from that by its per-script
    // hint, which goes into the detector; the CLD3 arm can only reject the
    // answer afterwards, which is what scriptOfLanguageCode is for. Measured
    // over 536 Latin strings from the reporter's log, before that rejection
    // existed: 19 spans went to Serbian with {en, sr} and 4 to Japanese with
    // {en, ja}. These are the specific strings behind those counts.
    printf("\nwrong-script languages enabled alongside English\n");
    enable({"en", "sr"});
    expect("sr enabled: log file name", "easy_voice.log.1", true, 0, 1, "en");
    expect("sr enabled: log share title", "Easy Voice Log", true, 0, 1, "en");
    expect("sr enabled: app name", "Easy Voice", true, 0, 1, "en");
    enable({"en", "ja"});
    expect("ja enabled: a bare verb", "load", true, 0, 1, "en");
    expect("ja enabled: a button label", "Save", true, 0, 1, "en");
    enable({"en", "ja", "sr", "ru", "zh"});
    expect("five scripts enabled: app name", "Easy Voice", true, 0, 1, "en");
    expect("five scripts enabled: a button label", "Save", true, 0, 1, "en");

    // The other direction: rejecting a wrong-script answer must not start
    // rejecting RIGHT-script ones. Each of these is a script CLD2 is asked
    // about, with its own language enabled.
    printf("\nnon-Latin text still reaches its own language\n");
    enable({"en", "hi"});
    expect("Devanagari with hi enabled",
           "\xE0\xA4\xB9\xE0\xA4\xBF\xE0\xA4\x82\xE0\xA4\xA6\xE0\xA5\x80 "
           "\xE0\xA4\x95\xE0\xA4\xB5\xE0\xA4\xBF\xE0\xA4\xA4\xE0\xA4\xBE\xE0\xA4\x8F\xE0\xA4\x81 "
           "\xE0\xA4\x8F\xE0\xA4\xB5\xE0\xA4\x82 "
           "\xE0\xA4\x95\xE0\xA4\xB9\xE0\xA4\xBE\xE0\xA4\xA8\xE0\xA4\xBF\xE0\xA4\xAF\xE0\xA4\xBE\xE0\xA4\x81",
           true, 0, 1, "hi");
    enable({"en", "ru"});
    expect("Cyrillic with ru enabled",
           "\xD0\x9F\xD1\x80\xD0\xB8\xD0\xB2\xD0\xB5\xD1\x82 \xD0\xBA\xD0\xB0\xD0\xBA "
           "\xD0\xB4\xD0\xB5\xD0\xBB\xD0\xB0 \xD1\x83 \xD1\x82\xD0\xB5\xD0\xB1\xD1\x8F "
           "\xD1\x81\xD0\xB5\xD0\xB3\xD0\xBE\xD0\xB4\xD0\xBD\xD1\x8F",
           true, 0, 1, "ru");
    enable({"en", "ja"});
    expect("Japanese with ja enabled",
           "\xE3\x81\x93\xE3\x82\x8C\xE3\x81\xAF\xE6\x97\xA5\xE6\x9C\xAC\xE8\xAA\x9E\xE3\x81\xA7"
           "\xE6\x9B\xB8\xE3\x81\x8B\xE3\x82\x8C\xE3\x81\x9F\xE6\x96\x87\xE3\x81\xA7\xE3\x81\x99",
           true, 0, 1, "ja");

    // Script 0: a run of digits, ASCII punctuation, spaces or emoji, in which
    // the classifier never set a script. getLanguageSpans gives it a case of
    // its own at 0x653ef0 -- "un" and latin = TRUE -- and then, if any hints
    // are set, replaces the language with scriptLanguageFallback[1], the LATIN
    // fallback, read from [x8, #0x564]. The flag stays true throughout, which
    // is why AutoTTS reads a bare number in the PREFERRED LATIN language.
    // Both detectors take this path: it never reaches one.
    printf("\nscript 0 -- nothing classified\n");
    enable({"en", "gu", "hi"});
    expectSpan("bare number, en enabled",        "123",      false, 0, 1, "en", "1");
    expectSpan("bare number, en enabled",        "123",      true,  0, 1, "en", "1");
    expectSpan("bare punctuation, en enabled",   "!?!",      false, 0, 1, "en", "1");
    expectSpan("a clock time, en enabled",       "7:45",     false, 0, 1, "en", "1");
    enable({"gu", "hi"});
    expectSpan("bare number, no Latin language", "123",      false, 0, 1, "un", "1");
    expectSpan("bare number, no Latin language", "123",      true,  0, 1, "un", "1");
    enable({"de", "en"});
    // The fallback is the first pair in TABLE order that is enabled, not the
    // first in the caller's list: English precedes German among the 48.
    expectSpan("bare number, de and en enabled", "123",      false, 0, 1, "en", "1");
    enable({"de", "ru"});
    expectSpan("bare number, de is the only Latin one", "123", false, 0, 1, "de", "1");


    // The CLD2 answer filter walks the SUMMARY first and then language3[0],
    // [1] and [2] -- three unrolled blocks at 0x654190, 0x6542ac and 0x654380,
    // reading language3[0..2] from x29-0x14/-0x10/-0xc and percent3[0..2] from
    // x29-0x20/-0x1c/-0x18. Rank 0 is not a repeat of the summary above it:
    // CalcSummaryLang returns language3[active_slot[1]] when it decides the top
    // answer is English or FIGS boilerplate, and UNKNOWN_LANGUAGE when the top
    // language covers less than 26 percent of the text. In both cases the
    // summary is a language the user has not enabled while language3[0] is one
    // they have, and skipping rank 0 sent the span to the per-script fallback
    // instead -- a different voice for the same text.
    //
    // These four cases were found by building the harness both ways and
    // diffing; each one answers differently with the loop starting at 1.
    printf("\nthe summary and language3[0] disagree\n");
    // French, then a Dutch tail big enough for the FIGS-boilerplate rule
    // (percent3[1] >= 20 and at least 15 bytes), so the summary becomes Dutch.
    const std::string frWithDutchTail =
        "Le temps est tres beau aujourd hui et le soleil brille toute la journee sur la ville entiere. "
        "Nous allons nous promener dans le parc avec nos amis et nos enfants pendant tout le week end. "
        "Het weer is vandaag erg mooi en de zon schijnt de hele dag door over de hele stad heen.";
    const std::string deWithDutchTail =
        "Das Wetter ist heute sehr schoen und die Sonne scheint den ganzen Tag ueber die ganze Stadt. "
        "Wir gehen mit unseren Freunden und Kindern das ganze Wochenende im Park spazieren gehen. "
        "Het weer is vandaag erg mooi en de zon schijnt de hele dag door over de hele stad heen.";
    // Five Latin languages in near-equal shares, so no single one reaches the
    // 26 percent the summary needs and it comes back UNKNOWN.
    const std::string fiveLatin =
        "Het weer is vandaag erg mooi en de zon schijnt de hele dag door over de hele stad. "
        "Das Wetter ist heute sehr schoen und die Sonne scheint den ganzen Tag lang. "
        "Le temps est tres beau aujourd hui et le soleil brille toute la journee. "
        "El tiempo es muy bueno hoy y el sol brilla durante todo el dia entero. "
        "Il tempo e molto bello oggi e il sole splende per tutta la giornata.";
    // Spanish precedes French among the 48 pairs, so the per-script fallback is
    // Spanish and taking it instead of language3[0] is audible.
    enable({"es", "fr"});
    expect("French text, es+fr enabled",  frWithDutchTail, false, 0, 1, "fr");
    enable({"de", "fr"});
    expect("German text, de+fr enabled",  deWithDutchTail, false, 0, 1, "de");
    enable({"de", "es"});
    expect("German text, de+es enabled",  deWithDutchTail, false, 0, 1, "de");
    expect("five-language text, de+es enabled", fiveLatin, false, 0, 1, "es");

    printf("\n");
    // ---- Hindi vs Marathi, from the 2026-09-02 device log (id 614) ----------
    // The owner reads Hindi with Marathi ALSO enabled, and the tail of a Hindi
    // sentence was spoken by the Marathi voice under CLD3 while CLD2 read it in
    // Hindi. It is not a plumbing bug: peeking at CLD3's full 109-language
    // softmax for this chunk gives mr p=0.99991 and hi p=0.00006. The model is
    // certain, and wrong. There is no tie to break and no threshold to tune, so
    // the CLD3 answer is left alone -- see CLAUDE.md.
    //
    // What DOES change it is the enabled list, and that is the owner's lever:
    // with Marathi unticked, "mr" is no longer hinted, isHinted rejects it and
    // the per-script fallback resolves Devanagari to Hindi (kScriptLangPairs
    // lists {4, HINDI} before {4, MARATHI}). Both directions are asserted so a
    // future change to either the filter or the fallback is caught.
    {
        const std::string hindiTail =
            "\u0935\u0939\u0940 \u0905\u0938\u0932\u0940 \u091a\u0948\u0928\u0932 "
            "\u0939\u094b\u0924\u093e \u0939\u0948!";
        enable({"en", "gu", "hi", "mr"});
        expectSpan("Hindi tail, CLD2, mr also enabled",  hindiTail, false, 0, 1, "hi", "0");
        expectSpan("Hindi tail, CLD3, mr also enabled",  hindiTail, true,  0, 1, "mr", "0");
        enable({"en", "gu", "hi"});
        expectSpan("Hindi tail, CLD3, mr NOT enabled",   hindiTail, true,  0, 1, "hi", "0");
        expectSpan("Hindi tail, CLD2, mr NOT enabled",   hindiTail, false, 0, 1, "hi", "0");

        // The two detectors AGREE on every other realistic Indic case, and these
        // assert it. They exist because of a rejected idea: CLD3 is built with
        // min_num_bytes = 0 while its own default is 140, so raising it looked
        // like a principled fix -- short chunks would return "und" and the
        // per-script fallback would resolve Devanagari to Hindi. Measured, and
        // it is a net LOSS: at min = 60 the 56-byte Hindi tail is fixed but
        // 41-byte Marathi ALSO becomes "und" and reads as Hindi, and both
        // detectors get that one right today. One sentence gained, a whole
        // language's short phrases lost. Do not raise min_num_bytes.
        enable({"en", "gu", "hi", "mr"});
        const std::string shortMarathi =
            "\u092e\u0932\u093e \u092e\u0930\u093e\u0920\u0940 \u0906\u0935\u0921\u0924\u0947";
        const std::string shortHindi =
            "\u092e\u0941\u091d\u0947 \u0939\u093f\u0928\u094d\u0926\u0940 \u092a\u0938\u0902\u0926 \u0939\u0948";
        expectSpan("short Marathi, CLD2, mr enabled",    shortMarathi, false, 0, 1, "mr", "0");
        expectSpan("short Marathi, CLD3, mr enabled",    shortMarathi, true,  0, 1, "mr", "0");
        expectSpan("short Hindi, CLD2, mr enabled",      shortHindi,   false, 0, 1, "hi", "0");
        expectSpan("short Hindi, CLD3, mr enabled",      shortHindi,   true,  0, 1, "hi", "0");

        // ---- AND NOW THE FIX, exercised the way the app exercises it -------
        // Everything above calls nativeGetLanguages directly, so no detect
        // context exists and the span is judged alone -- which is why the CLD3
        // row still reads "mr". The app does not do that: processDirect runs
        // first and stores the whole normalised utterance, and the per-chunk
        // detection that follows can then widen a short span to the utterance's
        // text in its own script. These four cases run that real sequence.
        const std::string utterance =
            "\u091c\u0939\u093e\u0901 Quality \u0914\u0930 Quantity "
            "\u0926\u094b\u0928\u094b\u0902 \u092e\u093f\u0932\u0947\u0902 - "
            "\u0935\u0939\u0940 \u0905\u0938\u0932\u0940 \u091a\u0948\u0928\u0932 "
            "\u0939\u094b\u0924\u093e \u0939\u0948!";
        auto runProcessDirect = [&](const std::string& t){
            jobject buf = env->NewDirectByteBuffer((void*)t.data(), (jlong)t.size());
            Java_com_tts_easyvoice_NativeEngine_processDirect(
                env, nullptr, buf, (jint)t.size(),
                env->NewStringUTF("eng"), env->NewStringUTF("hin"), env->NewStringUTF("mix"),
                0, env->NewStringUTF("eng"), 0, env->NewStringUTF("eng"), 0, env->NewStringUTF("eng"),
                JNI_TRUE, JNI_FALSE, 1, env->NewStringUTF("eng"), 1, JNI_TRUE, JNI_FALSE);
        };

        enable({"en", "gu", "hi", "mr"});
        runProcessDirect(utterance);
        expectSpan("THE BUG: Hindi tail after processDirect, CLD3", hindiTail, true,  0, 1, "hi", "0");
        expectSpan("Hindi tail after processDirect, CLD2",          hindiTail, false, 0, 1, "hi", "0");

        // A genuinely Marathi utterance must still come out Marathi -- the
        // widened evidence is Marathi too, so the answer does not move.
        const std::string marathiUtterance =
            "\u0924\u094b\u091a \u0916\u0930\u093e \u091a\u0945\u0928\u0932 \u0905\u0938\u0924\u094b, "
            "\u092e\u0932\u093e \u092e\u0930\u093e\u0920\u0940 \u092d\u093e\u0937\u093e "
            "\u0916\u0942\u092a \u0906\u0935\u0921\u0924\u0947.";
        runProcessDirect(marathiUtterance);
        expectSpan("Marathi still Marathi after widening, CLD3", shortMarathi, true,  0, 1, "mr", "0");
        expectSpan("Marathi still Marathi after widening, CLD2", shortMarathi, false, 0, 1, "mr", "0");

        // ---- THE SECOND REPORT (device log, 2026-09-02) ---------------
        // Same symptom, different cause, and the widening above cannot touch
        // it: this utterance's Devanagari chunk IS all the Devanagari there is,
        // so there is nothing wider to detect against.
        //
        //     किसी दूसरी भाषा में हो रही किसी दूसरी भाषा बातचीत Text box Compose message
        //
        // CLD2 read the Devanagari chunk as Hindi; CLD3 sent it to the MARATHI
        // voice. The model is not at fault -- handed the raw bytes it answers
        // hi p=0.9926. What it was handed was not those bytes: CLD3 runs
        // CheapSqueezeInplace on every text it is given, and "किसी दूसरी भाषा"
        // occurs twice here, so 131 bytes were cut to 75 --
        // "किसी दूसरी भाषा भाषा बातचीत" -- and on the wreckage the answer is mr.
        // CLD2 squeezes a span only above 2048 bytes. The gate is now applied
        // in cld3FindLanguageGated / cld3TopNGated; these cases hold it.
        {
            const std::string repeatedHindi =
                "किसी दूसरी भाषा "
                "में हो रही किसी "
                "दूसरी भाषा बातचीत ";
            const std::string repeatedUtterance = repeatedHindi + "Text box Compose message";
            enable({"en", "gu", "hi", "mr"});
            expectSpan("repeated Hindi, CLD2", repeatedHindi, false, 0, 1, "hi", "0");
            expectSpan("repeated Hindi, CLD3", repeatedHindi, true,  0, 1, "hi", "0");
            runProcessDirect(repeatedUtterance);
            expectSpan("repeated Hindi after processDirect, CLD3", repeatedHindi, true,  0, 1, "hi", "0");
            expectSpan("repeated Hindi after processDirect, CLD2", repeatedHindi, false, 0, 1, "hi", "0");

            // The same defect on Latin text, found while measuring the fix: the
            // phrase doubled is squeezed to something CLD3 calls JAPANESE with
            // p=0.784, which clears its own reliability bar. Gated, it answers
            // en unreliably and the per-script fallback resolves the Latin span
            // to the Latin language -- which is where it belonged all along.
            const std::string doubledLatin =
                "Text box Compose message Text box Compose message";
            expectSpan("doubled Latin phrase, CLD3", doubledLatin, true,  0, 1, "en", "1");
            expectSpan("doubled Latin phrase, CLD2", doubledLatin, false, 0, 1, "en", "1");
        }
    }

    if(failures == 0) printf("ALL CLD3 SPAN CASES PASS\n");
    else              printf("%d CASE(S) FAILED\n", failures);
    vm->DestroyJavaVM();
    return failures ? 1 : 0;
}
