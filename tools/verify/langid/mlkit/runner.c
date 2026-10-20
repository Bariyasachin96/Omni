// ML Kit's own language identification, as the oracle for langid.h: the x86_64
// build of liblanguage_id_l2c_jni.so (com.google.mlkit:language-id 17.0.6) on
// glibc, through bionic.c and a fake JNI.
//
//   runner model.tflite corpus.txt out.f32 labels.txt
//
// The calls are ThickLanguageIdentifier's (javap of the AAR's classes.jar):
// nativeInitFromBuffer(the model, mapped; its length), then per line
// nativeIdentifyPossibleLanguages(handle, String.getBytes(UTF_8), threshold)
// with threshold 0, so that every label comes back. Its confidences go out as
// float32 in the order of labels.txt.
//
// Every JNI function starts as a trap that names its slot (traps.h, which
// run.sh writes), so a call this file does not fake stops the run instead of
// jumping through a null pointer.
#include <dlfcn.h>
#include <jni.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static void trapped(int slot) { fprintf(stderr, "ML Kit called JNI function slot %d, which is not faked\n", slot); abort(); }
#include "traps.h"

// Java objects: one pool, emptied after every text.
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

static char* slurp(const char* path, long* n) {
    FILE* f = fopen(path, "rb");
    if (!f) { fprintf(stderr, "cannot read %s\n", path); exit(2); }
    fseek(f, 0, SEEK_END);
    *n = ftell(f);
    fseek(f, 0, SEEK_SET);
    char* all = malloc((size_t)*n + 1);
    if (fread(all, 1, (size_t)*n, f) != (size_t)*n) { fprintf(stderr, "cannot read %s\n", path); exit(2); }
    fclose(f);
    all[*n] = 0;
    return all;
}

int main(int argc, char** argv) {
    if (argc != 5) { fprintf(stderr, "usage: runner model.tflite corpus.txt out.f32 labels.txt\n"); return 2; }
    struct JNINativeInterface_ fns;
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
    const struct JNINativeInterface_* table = &fns;
    JNIEnv* env = (JNIEnv*)&table;
    theEnv = env;

    void* lib = dlopen("liblanguage_id_l2c_jni.so", RTLD_NOW);
    if (!lib) { fprintf(stderr, "dlopen: %s\n", dlerror()); return 1; }
    typedef jlong (JNICALL *Init)(JNIEnv*, jobject, jobject, jlong);
    typedef jobjectArray (JNICALL *Identify)(JNIEnv*, jobject, jlong, jbyteArray, jfloat);
    Init init = (Init)dlsym(lib, "Java_com_google_mlkit_nl_languageid_bundled_internal_ThickLanguageIdentifier_nativeInitFromBuffer");
    Identify identify = (Identify)dlsym(lib, "Java_com_google_mlkit_nl_languageid_bundled_internal_ThickLanguageIdentifier_nativeIdentifyPossibleLanguages");
    if (!init || !identify) { fprintf(stderr, "entry points missing\n"); return 1; }

    long size;
    static Obj model, thiz;
    model.kind = BUFFER;
    model.data = slurp(argv[1], &size);
    model.len = size;
    jlong handle = init(env, (jobject)&thiz, (jobject)&model, size);
    if (!handle) { fprintf(stderr, "nativeInitFromBuffer failed\n"); return 1; }

    long n;
    char* labelText = slurp(argv[4], &n);
    char* labels[256];
    int nlabels = 0;
    for (char* tok = strtok(labelText, "\n"); tok && nlabels < 256; tok = strtok(NULL, "\n")) labels[nlabels++] = tok;

    char* all = slurp(argv[2], &n);
    FILE* out = fopen(argv[3], "wb");
    float row[256];
    long texts = 0;
    for (long at = 0; at < n;) {
        char* nl = memchr(all + at, '\n', (size_t)(n - at));
        long end = nl ? nl - all : n;
        for (int k = 0; k < nlabels; k++) row[k] = -1;
        if (end > at) {
            used = usedElems = 0;
            Obj* text = obj(BYTES);
            text->data = all + at;
            text->len = end - at;
            Obj* result = (Obj*)identify(env, (jobject)&thiz, handle, (jbyteArray)text, 0.0f);
            for (long i = 0; result && i < result->len; i++) {
                int k = 0;
                while (k < nlabels && strcmp(labels[k], result->elems[i]->tag->str)) k++;
                if (k == nlabels) { fprintf(stderr, "ML Kit answered a label the model does not have: %s\n", result->elems[i]->tag->str); return 1; }
                row[k] = result->elems[i]->conf;
            }
        }
        fwrite(row, sizeof(float), (size_t)nlabels, out);
        texts++;
        at = end + 1;
    }
    fclose(out);
    printf("ML Kit: %ld texts through its own library\n", texts);
    return 0;
}
