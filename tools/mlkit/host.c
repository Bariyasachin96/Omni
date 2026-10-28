// GOOGLE'S ML KIT ON A DESKTOP, for the harnesses: the x86_64 build of
// liblanguage_id_l2c_jni.so (com.google.mlkit:language-id 17.0.6) and the model
// it bundles, on glibc through bionic.c and a fake JNI -- so a harness asks ML
// Kit itself what the app asks it on a phone, and no model of ours is needed
// anywhere (owner, 2026-10-28: "koi model rakhna hi nahin ... ML Kit use
// karenge har jagah").
//
//   int evmlkit_load(const char* model);        tflite_langid.tflite.jpg
//   int evmlkit_identify(const char* utf8, size_t len,
//                        char (*tags)[16], float* confidences, int max);
//
// evmlkit_identify has the signature of the core's language identifier
// (tts_engine_core.cpp, EvLidFn), so a harness hands it to
// evSetLanguageIdentifier. It answers exactly what the app's path answers --
// the core's UTF-8 made a Java String (NewStringUTF, as ART reads it),
// EasyVoiceTtsService.mlkitIdentify -> LanguageIdentifier.identifyPossibleLanguages
// at its default options, whose Java (javap of language-id-common 16.1.0:
// LanguageIdentifierImpl, and ThickLanguageIdentifier in language-id 17.0.6)
// keeps the first 200 UTF-16 units of the text (String.substring), passes
// String.getBytes(UTF_8) and the threshold 0.01 to
// nativeIdentifyPossibleLanguages -- and each confidence rounded to millionths
// as Kotlin sends it to the core.
//
// Every JNI function starts as a trap that names its slot (traps.h, which
// build.sh writes), so a call this file does not fake stops the run instead of
// jumping through a null pointer. Not thread-safe by itself: one call at a
// time (a mutex below).
#include <dlfcn.h>
#include <jni.h>
#include <math.h>
#include <pthread.h>
#include <stdarg.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static void trapped(int slot) { fprintf(stderr, "ML Kit called JNI function slot %d, which is not faked\n", slot); abort(); }
#include "traps.h"

// Java objects: one pool, emptied before every text.
enum { CLASS = 1, BYTES, BUFFER, STRING, ARRAY, OBJECT };
typedef struct Obj { int kind; char str[64]; void* data; long len; struct Obj** elems; struct Obj* tag; float conf; } Obj;
static Obj pool[4096];
static struct Obj* elemPool[4096];
static int used, usedElems;
static Obj* obj(int kind) {
    if (used == (int)(sizeof pool / sizeof *pool)) { fprintf(stderr, "object pool full\n"); abort(); }
    Obj* o = &pool[used++];
    memset(o, 0, sizeof *o);
    o->kind = kind;
    return o;
}

static jclass JNICALL FindClass(JNIEnv* e, const char* name) {
    (void)e; Obj* o = obj(CLASS); snprintf(o->str, sizeof o->str, "%s", name); return (jclass)o;
}
static jmethodID JNICALL GetMethodID(JNIEnv* e, jclass c, const char* n, const char* s) {
    (void)e; (void)c; (void)n; (void)s; static int id; return (jmethodID)&id;
}
// new IdentifiedLanguage(String languageTag, float confidence)
static jobject JNICALL NewObjectV(JNIEnv* e, jclass c, jmethodID m, va_list ap) {
    (void)e; (void)c; (void)m;
    Obj* o = obj(OBJECT);
    o->tag = (Obj*)va_arg(ap, jobject);
    o->conf = (float)va_arg(ap, double);   // a float, promoted through the varargs
    return (jobject)o;
}
static jobject JNICALL NewObject(JNIEnv* e, jclass c, jmethodID m, ...) {
    va_list ap; va_start(ap, m); jobject r = NewObjectV(e, c, m, ap); va_end(ap); return r;
}
static jobject JNICALL NewObjectA(JNIEnv* e, jclass c, jmethodID m, const jvalue* v) {
    (void)e; (void)c; (void)m; Obj* o = obj(OBJECT); o->tag = (Obj*)v[0].l; o->conf = v[1].f; return (jobject)o;
}
static jstring JNICALL NewStringUTF(JNIEnv* e, const char* s) {
    (void)e; Obj* o = obj(STRING); snprintf(o->str, sizeof o->str, "%s", s); return (jstring)o;
}
static jobjectArray JNICALL NewObjectArray(JNIEnv* e, jsize n, jclass c, jobject init) {
    (void)e; (void)c;
    if (usedElems + n > (int)(sizeof elemPool / sizeof *elemPool)) { fprintf(stderr, "array pool full\n"); abort(); }
    Obj* o = obj(ARRAY);
    o->len = n;
    o->elems = &elemPool[usedElems];
    usedElems += n;
    for (int i = 0; i < n; i++) o->elems[i] = (Obj*)init;
    return (jobjectArray)o;
}
static void JNICALL SetObjectArrayElement(JNIEnv* e, jobjectArray a, jsize i, jobject v) { (void)e; ((Obj*)a)->elems[i] = (Obj*)v; }
static jobject JNICALL GetObjectArrayElement(JNIEnv* e, jobjectArray a, jsize i) { (void)e; return (jobject)((Obj*)a)->elems[i]; }
static jsize JNICALL GetArrayLength(JNIEnv* e, jarray a) { (void)e; return (jsize)((Obj*)a)->len; }
static jbyte* JNICALL GetByteArrayElements(JNIEnv* e, jbyteArray a, jboolean* copy) {
    (void)e; if (copy) *copy = JNI_FALSE; return (jbyte*)((Obj*)a)->data;
}
static void JNICALL ReleaseByteArrayElements(JNIEnv* e, jbyteArray a, jbyte* p, jint mode) { (void)e; (void)a; (void)p; (void)mode; }
static void JNICALL GetByteArrayRegion(JNIEnv* e, jbyteArray a, jsize at, jsize n, jbyte* out) {
    (void)e; memcpy(out, (char*)((Obj*)a)->data + at, (size_t)n);
}
static void* JNICALL GetPrimitiveArrayCritical(JNIEnv* e, jarray a, jboolean* copy) {
    (void)e; if (copy) *copy = JNI_FALSE; return ((Obj*)a)->data;
}
static void JNICALL ReleasePrimitiveArrayCritical(JNIEnv* e, jarray a, void* p, jint mode) { (void)e; (void)a; (void)p; (void)mode; }
static void* JNICALL GetDirectBufferAddress(JNIEnv* e, jobject b) { (void)e; return ((Obj*)b)->data; }
static jlong JNICALL GetDirectBufferCapacity(JNIEnv* e, jobject b) { (void)e; return ((Obj*)b)->len; }
static void JNICALL DeleteLocalRef(JNIEnv* e, jobject o) { (void)e; (void)o; }
static jobject JNICALL NewGlobalRef(JNIEnv* e, jobject o) { (void)e; return o; }
static void JNICALL DeleteGlobalRef(JNIEnv* e, jobject o) { (void)e; (void)o; }
static jboolean JNICALL ExceptionCheck(JNIEnv* e) { (void)e; return JNI_FALSE; }
static jthrowable JNICALL ExceptionOccurred(JNIEnv* e) { (void)e; return NULL; }
static jint JNICALL ThrowNew(JNIEnv* e, jclass c, const char* msg) {
    (void)e; fprintf(stderr, "ML Kit threw %s: %s\n", ((Obj*)c)->str, msg); exit(1);
}
static jint JNICALL EnsureLocalCapacity(JNIEnv* e, jint n) { (void)e; (void)n; return 0; }
static jint JNICALL PushLocalFrame(JNIEnv* e, jint n) { (void)e; (void)n; return 0; }
static jobject JNICALL PopLocalFrame(JNIEnv* e, jobject r) { (void)e; return r; }

static JNIEnv* theEnv;
static jint JNICALL VmGetEnv(JavaVM* vm, void** env, jint version) { (void)vm; (void)version; *env = theEnv; return JNI_OK; }
static jint JNICALL VmAttach(JavaVM* vm, void** env, void* args) { (void)vm; (void)args; *env = theEnv; return JNI_OK; }
static jint JNICALL VmDetach(JavaVM* vm) { (void)vm; return JNI_OK; }
static struct JNIInvokeInterface_ vmFns = {NULL, NULL, NULL, NULL, VmAttach, VmDetach, VmGetEnv, VmAttach};
static const struct JNIInvokeInterface_* vmTable = &vmFns;
static jint JNICALL GetJavaVM(JNIEnv* e, JavaVM** vm) { (void)e; *vm = (JavaVM*)&vmTable; return JNI_OK; }

typedef jobjectArray (JNICALL *Identify)(JNIEnv*, jobject, jlong, jbyteArray, jfloat);
static struct JNINativeInterface_ fns;
static const struct JNINativeInterface_* table;
static Identify identify;
static jlong handle;
static Obj thiz;
static pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

int evmlkit_load(const char* model) {
    void** slots = (void**)&fns;
    for (size_t i = 0; i < sizeof fns / sizeof(void*); i++) slots[i] = (void*)kTraps[i];
    fns.FindClass = FindClass; fns.GetMethodID = GetMethodID; fns.NewObject = NewObject; fns.NewObjectV = NewObjectV;
    fns.NewObjectA = NewObjectA; fns.NewStringUTF = NewStringUTF; fns.NewObjectArray = NewObjectArray;
    fns.SetObjectArrayElement = SetObjectArrayElement; fns.GetObjectArrayElement = GetObjectArrayElement;
    fns.GetArrayLength = GetArrayLength; fns.GetByteArrayElements = GetByteArrayElements;
    fns.ReleaseByteArrayElements = ReleaseByteArrayElements; fns.GetByteArrayRegion = GetByteArrayRegion;
    fns.GetPrimitiveArrayCritical = GetPrimitiveArrayCritical; fns.ReleasePrimitiveArrayCritical = ReleasePrimitiveArrayCritical;
    fns.GetDirectBufferAddress = GetDirectBufferAddress; fns.GetDirectBufferCapacity = GetDirectBufferCapacity;
    fns.DeleteLocalRef = DeleteLocalRef; fns.NewGlobalRef = NewGlobalRef; fns.DeleteGlobalRef = DeleteGlobalRef;
    fns.ExceptionCheck = ExceptionCheck; fns.ExceptionOccurred = ExceptionOccurred; fns.ThrowNew = ThrowNew;
    fns.GetJavaVM = GetJavaVM;
    fns.EnsureLocalCapacity = EnsureLocalCapacity; fns.PushLocalFrame = PushLocalFrame; fns.PopLocalFrame = PopLocalFrame;
    table = &fns;
    theEnv = (JNIEnv*)&table;

    void* lib = dlopen("liblanguage_id_l2c_jni.so", RTLD_NOW);
    if (!lib) { fprintf(stderr, "evmlkit: dlopen: %s\n", dlerror()); return 0; }
    typedef jlong (JNICALL *Init)(JNIEnv*, jobject, jobject, jlong);
    Init init = (Init)dlsym(lib, "Java_com_google_mlkit_nl_languageid_bundled_internal_ThickLanguageIdentifier_nativeInitFromBuffer");
    identify = (Identify)dlsym(lib, "Java_com_google_mlkit_nl_languageid_bundled_internal_ThickLanguageIdentifier_nativeIdentifyPossibleLanguages");
    if (!init || !identify) { fprintf(stderr, "evmlkit: entry points missing\n"); return 0; }

    FILE* f = fopen(model, "rb");
    if (!f) { fprintf(stderr, "evmlkit: cannot read %s\n", model); return 0; }
    fseek(f, 0, SEEK_END);
    long size = ftell(f);
    fseek(f, 0, SEEK_SET);
    char* bytes = malloc((size_t)size);
    if (!bytes || fread(bytes, 1, (size_t)size, f) != (size_t)size) { fclose(f); fprintf(stderr, "evmlkit: cannot read %s\n", model); return 0; }
    fclose(f);
    static Obj buffer;
    buffer.kind = BUFFER;
    buffer.data = bytes;   // ThickLanguageIdentifier maps it; it lives as long as the process here
    buffer.len = size;
    used = usedElems = 0;
    handle = init(theEnv, (jobject)&thiz, (jobject)&buffer, size);
    if (!handle) { fprintf(stderr, "evmlkit: nativeInitFromBuffer failed\n"); return 0; }
    return 1;
}

// The core's UTF-8 as the Java String NewStringUTF makes of it on ART (a 4-byte
// sequence is a surrogate pair, C0 80 is U+0000, an encoded surrogate stays a
// lone one), its first 200 UTF-16 units, as String.getBytes(UTF_8) writes them
// (an unpaired surrogate as '?').
static size_t javaFirst200(const char* utf8, size_t len, char* out) {
    uint16_t units[204];
    int n = 0;
    for (size_t i = 0; i < len && n < 200;) {
        const unsigned char c = (unsigned char)utf8[i];
        uint32_t cp; size_t k;
        if (c < 0x80) { cp = c; k = 1; }
        else if ((c & 0xE0) == 0xC0 && i + 1 < len) { cp = ((c & 0x1Fu) << 6) | (utf8[i + 1] & 0x3F); k = 2; }
        else if ((c & 0xF0) == 0xE0 && i + 2 < len) { cp = ((c & 0x0Fu) << 12) | ((utf8[i + 1] & 0x3Fu) << 6) | (utf8[i + 2] & 0x3F); k = 3; }
        else if ((c & 0xF8) == 0xF0 && i + 3 < len) {
            cp = ((c & 0x07u) << 18) | ((utf8[i + 1] & 0x3Fu) << 12) | ((utf8[i + 2] & 0x3Fu) << 6) | (utf8[i + 3] & 0x3F); k = 4;
        } else { cp = 0xFFFD; k = 1; }
        i += k;
        if (cp >= 0x10000) { units[n++] = (uint16_t)(0xD800 + ((cp - 0x10000) >> 10)); units[n++] = (uint16_t)(0xDC00 + ((cp - 0x10000) & 0x3FF)); }
        else units[n++] = (uint16_t)cp;
    }
    if (n > 200) n = 200;
    size_t o = 0;
    for (int i = 0; i < n; i++) {
        uint32_t cp = units[i];
        if (cp >= 0xD800 && cp < 0xDC00 && i + 1 < n && units[i + 1] >= 0xDC00 && units[i + 1] < 0xE000) {
            cp = 0x10000 + ((cp - 0xD800) << 10) + (units[++i] - 0xDC00u);
        } else if (cp >= 0xD800 && cp < 0xE000) {
            out[o++] = '?';
            continue;
        }
        if (cp < 0x80) out[o++] = (char)cp;
        else if (cp < 0x800) { out[o++] = (char)(0xC0 | cp >> 6); out[o++] = (char)(0x80 | (cp & 63)); }
        else if (cp < 0x10000) { out[o++] = (char)(0xE0 | cp >> 12); out[o++] = (char)(0x80 | ((cp >> 6) & 63)); out[o++] = (char)(0x80 | (cp & 63)); }
        else { out[o++] = (char)(0xF0 | cp >> 18); out[o++] = (char)(0x80 | ((cp >> 12) & 63)); out[o++] = (char)(0x80 | ((cp >> 6) & 63)); out[o++] = (char)(0x80 | (cp & 63)); }
    }
    return o;
}

int evmlkit_identify(const char* utf8, size_t len, char (*tags)[16], float* confidences, int max) {
    if (!identify) return 0;
    char bytes[200 * 4];
    pthread_mutex_lock(&lock);
    used = usedElems = 0;
    Obj* text = obj(BYTES);
    text->len = (long)javaFirst200(utf8, len, bytes);
    text->data = bytes;
    Obj* result = (Obj*)identify(theEnv, (jobject)&thiz, handle, (jbyteArray)text, 0.01f);
    int n = 0;
    for (long i = 0; result && i < result->len && n < max; i++) {
        size_t t = strlen(result->elems[i]->tag->str);
        if (t > 15) t = 15;
        memcpy(tags[n], result->elems[i]->tag->str, t);
        tags[n][t] = '\0';
        // Kotlin: (confidence * 1_000_000f).roundToInt(), then the core / 1e6f
        const float scaled = result->elems[i]->conf * 1000000.0f;
        const long millionths = (long)floor((double)scaled + 0.5);
        confidences[n] = (float)(millionths > 1000000 ? 1000000 : millionths) / 1000000.0f;
        n++;
    }
    pthread_mutex_unlock(&lock);
    return n;
}
