// A JNIEnv WITHOUT A JVM, so the real JNI entry points of the native core can
// run inside a static Android (bionic) executable built by the NDK's own clang.
// That is what lets tools/pgo train and measure the EXACT compiler that ships
// the app: the NDK has no host profile runtime, but a static x86_64-android
// binary runs on a Linux x86_64 kernel as it is.
//
// Only what the core and the driver call is implemented; every other slot of
// the function table aborts, so a new JNI call in the core fails loudly here
// instead of reading garbage. Strings behave like ART's: GetStringUTF* give a
// supplementary character as real 4-byte UTF-8 and U+0000 as C0 80, and
// NewStringUTF reads that form back (see tools/verify/corebytes/main.cpp).
#pragma once
#include <jni.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <string>
#include <vector>
#include <algorithm>
#include <type_traits>

namespace fakejni {

struct Obj { bool global = false; bool isArray = false; std::u16string s; std::vector<Obj*> a; };
static std::vector<Obj*> locals;

static Obj* make() { Obj* o = new Obj(); locals.push_back(o); return o; }
// Frees every local made since the last reset; global refs survive.
static void reset() {
    for (Obj* o : locals) if (!o->global) delete o;
    locals.clear();
}

static std::string toArt(const jchar* c, jsize n) {
    std::string o;
    for (jsize i = 0; i < n; i++) {
        unsigned cp = c[i];
        if (cp >= 0xD800 && cp < 0xDC00 && i + 1 < n && c[i + 1] >= 0xDC00 && c[i + 1] < 0xE000) cp = 0x10000 + ((cp - 0xD800) << 10) + (c[++i] - 0xDC00);
        if (cp == 0) { o += (char)0xC0; o += (char)0x80; }
        else if (cp < 0x80) o += char(cp);
        else if (cp < 0x800) { o += char(0xC0 | cp >> 6); o += char(0x80 | (cp & 63)); }
        else if (cp < 0x10000) { o += char(0xE0 | cp >> 12); o += char(0x80 | ((cp >> 6) & 63)); o += char(0x80 | (cp & 63)); }
        else { o += char(0xF0 | cp >> 18); o += char(0x80 | ((cp >> 12) & 63)); o += char(0x80 | ((cp >> 6) & 63)); o += char(0x80 | (cp & 63)); }
    }
    return o;
}
static std::u16string fromUtf8(const char* s) {
    std::string b(s ? s : ""); std::u16string u;
    for (size_t i = 0; i < b.size();) {
        unsigned char c = b[i]; unsigned cp; int n;
        if (c < 0x80) { cp = c; n = 1; } else if ((c >> 5) == 6) { cp = c & 31; n = 2; }
        else if ((c >> 4) == 14) { cp = c & 15; n = 3; } else { cp = c & 7; n = 4; }
        for (int k = 1; k < n && i + k < b.size(); k++) cp = (cp << 6) | (b[i + k] & 63);
        i += n;
        if (cp >= 0x10000) { cp -= 0x10000; u += char16_t(0xD800 + (cp >> 10)); u += char16_t(0xDC00 + (cp & 1023)); }
        else u += char16_t(cp);
    }
    return u;
}

static Obj* O(jobject o) { return reinterpret_cast<Obj*>(o); }
static jstring JNICALL NewString(JNIEnv*, const jchar* c, jsize n) { Obj* o = make(); o->s.assign((const char16_t*)c, (size_t)n); return (jstring)o; }
static jstring JNICALL NewStringUTF(JNIEnv*, const char* c) { Obj* o = make(); o->s = fromUtf8(c); return (jstring)o; }
static jsize JNICALL GetStringLength(JNIEnv*, jstring s) { return (jsize)O(s)->s.size(); }
static void JNICALL GetStringRegion(JNIEnv*, jstring s, jsize st, jsize len, jchar* buf) { memcpy(buf, O(s)->s.data() + st, (size_t)len * 2); }
static jsize JNICALL GetStringUTFLength(JNIEnv*, jstring s) { auto& u = O(s)->s; return (jsize)toArt((const jchar*)u.data(), (jsize)u.size()).size(); }
static void JNICALL GetStringUTFRegion(JNIEnv*, jstring s, jsize st, jsize len, char* buf) {
    auto& u = O(s)->s;
    std::string o = toArt((const jchar*)u.data() + st, std::min(len, (jsize)u.size() - st));
    memcpy(buf, o.data(), o.size()); buf[o.size()] = 0;
}
static jobjectArray JNICALL NewObjectArray(JNIEnv*, jsize n, jclass, jobject init) { Obj* o = make(); o->isArray = true; o->a.assign((size_t)n, O(init)); return (jobjectArray)o; }
static void JNICALL SetObjectArrayElement(JNIEnv*, jobjectArray a, jsize i, jobject v) { O(a)->a[(size_t)i] = O(v); }
static jobject JNICALL GetObjectArrayElement(JNIEnv*, jobjectArray a, jsize i) { return (jobject)O(a)->a[(size_t)i]; }
static jsize JNICALL GetArrayLength(JNIEnv*, jarray a) { return (jsize)O(a)->a.size(); }
static void JNICALL DeleteLocalRef(JNIEnv*, jobject) {}
static jobject JNICALL NewGlobalRef(JNIEnv*, jobject o) { if (o) O(o)->global = true; return o; }
static void JNICALL DeleteGlobalRef(JNIEnv*, jobject) {}
static Obj stringClass;
static jclass JNICALL FindClass(JNIEnv*, const char*) { stringClass.global = true; return (jclass)&stringClass; }
static jboolean JNICALL ExceptionCheck(JNIEnv*) { return JNI_FALSE; }
static void JNICALL ExceptionClear(JNIEnv*) {}
static void trap() { fprintf(stderr, "fakejni: a JNI function the fake does not implement was called\n"); abort(); }

// The NDK's jni.h and the JDK's name these structs differently (_JNIEnv /
// JNINativeInterface vs JNIEnv_ / JNINativeInterface_); JNIEnv is the struct in
// both, so the ASan host build in run.sh compiles this same file.
using Fns = std::remove_const_t<std::remove_pointer_t<decltype(JNIEnv::functions)>>;
static Fns fns;
static JNIEnv envObj;
static JNIEnv* env() {
    static bool ready = false;
    if (!ready) {
        void** slot = reinterpret_cast<void**>(&fns);
        for (size_t i = 0; i < sizeof(fns) / sizeof(void*); i++) slot[i] = reinterpret_cast<void*>(&trap);
        fns.NewString = NewString; fns.NewStringUTF = NewStringUTF;
        fns.GetStringLength = GetStringLength; fns.GetStringRegion = GetStringRegion;
        fns.GetStringUTFLength = GetStringUTFLength; fns.GetStringUTFRegion = GetStringUTFRegion;
        fns.NewObjectArray = NewObjectArray; fns.SetObjectArrayElement = SetObjectArrayElement;
        fns.GetObjectArrayElement = GetObjectArrayElement; fns.GetArrayLength = GetArrayLength;
        fns.DeleteLocalRef = DeleteLocalRef; fns.NewGlobalRef = NewGlobalRef; fns.DeleteGlobalRef = DeleteGlobalRef;
        fns.FindClass = FindClass; fns.ExceptionCheck = ExceptionCheck; fns.ExceptionClear = ExceptionClear;
        envObj.functions = &fns;
        ready = true;
    }
    return &envObj;
}
static std::string text(jstring s) { if (!s) return "\\N"; auto& u = O(s)->s; return toArt((const jchar*)u.data(), (jsize)u.size()); }

}  // namespace fakejni
