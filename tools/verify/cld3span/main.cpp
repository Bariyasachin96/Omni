// Regression harness for the CLD3 span site, run against the REAL native core.
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

    printf("\n");
    if(failures == 0) printf("ALL CLD3 SPAN CASES PASS\n");
    else              printf("%d CASE(S) FAILED\n", failures);
    vm->DestroyJavaVM();
    return failures ? 1 : 0;
}
