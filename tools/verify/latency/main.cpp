// How long the app spends BEFORE it can speak the first word.
//
// The device logs the owner has sent all top out at a 404-character utterance,
// where start-to-first-speak is 8-12 ms. The report is about a text of 25 to 30
// paragraphs, which no log covers, so this measures that case against the REAL
// native core rather than guessing at it: the same tts_engine_core.cpp the app
// loads, linked with CLD2, with a genuine JNIEnv.
//
// What it times is the work that must finish before speakChunk(true) runs:
// buildMixChunks (through processDirect) and then one detection per chunk, which
// is what the mix and multilingual branches do in Kotlin.
#include <jni.h>
#include <chrono>
#include <cstdio>
#include <string>
#include <vector>

extern "C" {
JNIEXPORT void JNICALL Java_com_tts_easyvoice_EasyVoiceTtsService_setLanguageHints(JNIEnv*, jclass, jobjectArray);
JNIEXPORT void JNICALL Java_com_tts_easyvoice_EasyVoiceTtsService_setDetectSets(JNIEnv*, jclass, jobjectArray, jobjectArray);
JNIEXPORT jobjectArray JNICALL Java_com_tts_easyvoice_EasyVoiceTtsService_nativeGetLanguages(JNIEnv*, jclass, jstring);
JNIEXPORT jstring JNICALL Java_com_tts_easyvoice_EasyVoiceTtsService_normalizeFancy(JNIEnv*, jclass, jstring);
JNIEXPORT jstring JNICALL Java_com_tts_easyvoice_EasyVoiceTtsService_processDirect(
    JNIEnv*, jclass, jobject, jint, jstring, jstring, jstring, jint, jstring, jint, jstring,
    jint, jstring, jboolean, jboolean, jint, jstring, jint, jboolean);
JNIEXPORT jstring JNICALL Java_com_tts_easyvoice_EasyVoiceTtsService_detectLanguageFull(
    JNIEnv*, jclass, jstring, jstring, jstring, jboolean, jboolean);
}

static JNIEnv* env = nullptr;
static double nowMs(){
    using namespace std::chrono;
    return duration<double, std::milli>(steady_clock::now().time_since_epoch()).count();
}
static jobjectArray arr(const std::vector<std::string>& v){
    jclass sc = env->FindClass("java/lang/String");
    jobjectArray a = env->NewObjectArray((jsize)v.size(), sc, nullptr);
    for (size_t i = 0; i < v.size(); i++)
        env->SetObjectArrayElement(a, (jsize)i, env->NewStringUTF(v[i].c_str()));
    return a;
}

// One paragraph each of Gujarati, Hindi and Marathi with a Latin name in it --
// the shape the owner described.
static const char* GU =
    "\xE0\xAA\x86 \xE0\xAA\xAE\xE0\xAB\x80\xE0\xAA\x9F\xE0\xAA\xBF\xE0\xAA\x82\xE0\xAA\x97\xE0\xAA\xA8\xE0\xAB\x8B "
    "\xE0\xAA\x89\xE0\xAA\xA6\xE0\xAB\x8D\xE0\xAA\xA6\xE0\xAB\x87\xE0\xAA\xB6\xE0\xAB\x8D\xE0\xAA\xAF Rinkesh Patel "
    "\xE0\xAA\xB8\xE0\xAA\xBE\xE0\xAA\xA5\xE0\xAB\x87 BRTS \xE0\xAA\xB8\xE0\xAA\x82\xE0\xAA\xAC\xE0\xAA\x82\xE0\xAA\xA7\xE0\xAA\xBF\xE0\xAA\xA4 "
    "\xE0\xAA\xAE\xE0\xAB\x81\xE0\xAA\xA6\xE0\xAB\x8D\xE0\xAA\xA6\xE0\xAA\xBE\xE0\xAA\x93 \xE0\xAA\x9A\xE0\xAA\xB0\xE0\xAB\x8D\xE0\xAA\x9A\xE0\xAA\xB5\xE0\xAA\xBE\xE0\xAA\xA8\xE0\xAB\x8B \xE0\xAA\x9B\xE0\xAB\x87. ";
static const char* HI =
    "\xE0\xA4\xAF\xE0\xA4\xB9 \xE0\xA4\xAC\xE0\xA5\x88\xE0\xA4\xA0\xE0\xA4\x95 \xE0\xA4\x95\xE0\xA4\xBE "
    "\xE0\xA4\x89\xE0\xA4\xA6\xE0\xA5\x8D\xE0\xA4\xA6\xE0\xA5\x87\xE0\xA4\xB6\xE0\xA5\x8D\xE0\xA4\xAF Meet Choudhary "
    "\xE0\xA4\x95\xE0\xA5\x87 \xE0\xA4\xB8\xE0\xA4\xBE\xE0\xA4\xA5 \xE0\xA4\xB9\xE0\xA4\xBE\xE0\xA4\xB2 \xE0\xA4\x95\xE0\xA5\x87 "
    "\xE0\xA4\xAE\xE0\xA5\x81\xE0\xA4\xA6\xE0\xA5\x8D\xE0\xA4\xA6\xE0\xA5\x8B\xE0\xA4\x82 \xE0\xA4\xAA\xE0\xA4\xB0 \xE0\xA4\x9A\xE0\xA4\xB0\xE0\xA5\x8D\xE0\xA4\x9A\xE0\xA4\xBE \xE0\xA4\x95\xE0\xA4\xB0\xE0\xA4\xA8\xE0\xA4\xBE \xE0\xA4\xB9\xE0\xA5\x88. ";
static const char* MR =
    "\xE0\xA4\xB9\xE0\xA5\x80 \xE0\xA4\xAC\xE0\xA5\x88\xE0\xA4\xA0\xE0\xA4\x95 \xE0\xA4\x86\xE0\xA4\xAF\xE0\xA5\x8B\xE0\xA4\x9C\xE0\xA4\xBF\xE0\xA4\xA4 "
    "\xE0\xA4\x95\xE0\xA4\xB0\xE0\xA4\xA3\xE0\xA5\x8D\xE0\xA4\xAF\xE0\xA4\xBE\xE0\xA4\x9A\xE0\xA4\xBE \xE0\xA4\x89\xE0\xA4\xA6\xE0\xA5\x8D\xE0\xA4\xA6\xE0\xA5\x87\xE0\xA4\xB6 "
    "Sachin Bariya \xE0\xA4\xAF\xE0\xA4\xBE\xE0\xA4\x82\xE0\xA4\xA8\xE0\xA5\x80 \xE0\xA4\xB8\xE0\xA4\xBE\xE0\xA4\x82\xE0\xA4\x97\xE0\xA4\xBF\xE0\xA4\xA4\xE0\xA4\xB2\xE0\xA5\x87\xE0\xA4\xB2\xE0\xA5\x8D\xE0\xA4\xAF\xE0\xA4\xBE "
    "\xE0\xA4\xAE\xE0\xA5\x81\xE0\xA4\xA6\xE0\xA5\x8D\xE0\xA4\xA6\xE0\xA5\x8D\xE0\xA4\xAF\xE0\xA4\xBE\xE0\xA4\x82\xE0\xA4\xB5\xE0\xA4\xB0 \xE0\xA4\x86\xE0\xA4\xB9\xE0\xA5\x87. ";

static std::string buildText(int paragraphs){
    std::string t;
    for (int i = 0; i < paragraphs; i++) {
        t += (i % 3 == 0) ? GU : (i % 3 == 1) ? HI : MR;
        t += "\n\n";
    }
    return t;
}

int main(){
    JavaVMInitArgs a; a.version = JNI_VERSION_1_8; a.nOptions = 0; a.options = nullptr; a.ignoreUnrecognized = JNI_TRUE;
    JavaVM* vm = nullptr;
    if (JNI_CreateJavaVM(&vm, (void**)&env, &a) != JNI_OK) { printf("no JVM\n"); return 2; }

    const std::vector<std::string> iso2 = {"en","gu","hi","mr"};
    const std::vector<std::string> iso3 = {"eng","guj","hin","mar"};
    Java_com_tts_easyvoice_EasyVoiceTtsService_setLanguageHints(env, nullptr, arr(iso2));
    Java_com_tts_easyvoice_EasyVoiceTtsService_setDetectSets(env, nullptr, arr(iso3), arr(iso2));

    printf("%-12s %8s %8s %10s %10s %12s %10s\n",
           "paragraphs", "chars", "chunks", "segment", "detect", "fold+detect", "TOTAL");
    for (int p : {1, 2, 3, 5, 10, 20, 30, 60}) {
        const std::string text = buildText(p);

        // 1. segmentation, exactly as the mix branch calls it
        jobject buf = env->NewDirectByteBuffer((void*)text.data(), (jlong)text.size());
        const double s0 = nowMs();
        jstring packed = Java_com_tts_easyvoice_EasyVoiceTtsService_processDirect(
            env, nullptr, buf, (jint)text.size(),
            env->NewStringUTF("eng"), env->NewStringUTF("guj"), env->NewStringUTF("mix"),
            0, env->NewStringUTF("eng"), 0, env->NewStringUTF("eng"), 0, env->NewStringUTF("eng"),
            JNI_TRUE, JNI_FALSE, 1, env->NewStringUTF("eng"), 1, JNI_TRUE);
        const double segMs = nowMs() - s0;

        const char* pc = env->GetStringUTFChars(packed, nullptr);
        std::string packedStr(pc ? pc : "");
        env->ReleaseStringUTFChars(packed, pc);

        // split records on 0x1E, take field 3 (the text) of each
        std::vector<std::string> chunkTexts;
        size_t at = 0;
        while (at < packedStr.size()) {
            size_t end = packedStr.find('\x1E', at);
            if (end == std::string::npos) end = packedStr.size();
            std::string rec = packedStr.substr(at, end - at);
            at = end + 1;
            int field = 0; size_t fs = 0;
            for (size_t i = 0; i <= rec.size(); i++) {
                if (i == rec.size() || rec[i] == '\x1F') {
                    if (field == 3) chunkTexts.push_back(rec.substr(fs, i - fs));
                    field++; fs = i + 1;
                }
            }
            if (field > 3 && chunkTexts.empty()) {}
        }

        // 2. one detection per chunk, which is what detectLanguageRuns does.
        // There is one detector now: CLD3 was removed on 2026-09-02 and this
        // used to carry a second column for it.
        // detectLanguageRuns calls normalizeFancy AND THEN nativeGetLanguages for
        // every chunk, so both are timed -- measuring only the detector hid a
        // whole second JNI round trip per chunk.
        auto detectAll = [&](bool withFold) -> double {
            const double t0 = nowMs();
            for (const std::string& c : chunkTexts) {
                if (c.empty()) continue;
                jstring js = env->NewStringUTF(c.c_str());
                if (withFold) {
                    jstring folded = Java_com_tts_easyvoice_EasyVoiceTtsService_normalizeFancy(env, nullptr, js);
                    env->DeleteLocalRef(js);
                    js = folded;
                }
                jobjectArray got = Java_com_tts_easyvoice_EasyVoiceTtsService_nativeGetLanguages(env, nullptr, js);
                (void)got;
                env->DeleteLocalRef(js);
            }
            return nowMs() - t0;
        };
        const double detOnly = detectAll(false);
        const double detMs   = detectAll(true);

        printf("%-12d %8zu %8zu %10.2f %10.2f %12.2f %10.2f\n",
               p, text.size(), chunkTexts.size(), segMs, detOnly, detMs, segMs + detMs);
    }
    vm->DestroyJavaVM();
    return 0;
}
