// Android's libc, as far as ML Kit's language-id library uses it, on glibc --
// so that its x86_64 build (liblanguage_id_l2c_jni.so) runs on a desktop, and
// the harnesses ask ML Kit itself what the app asks it on a phone (host.c).
//
// Each import of that library carries bionic's symbol version LIBC, so each
// is defined here under that version, as a NON-default one (name@LIBC): a
// default one (@@) would also capture this file's own calls to glibc, and
// every forwarder would jump to itself. Where bionic's ABI is glibc's, a tail
// jump; where it is not, a translation:
//   - FILE*: bionic's stdin/stdout/stderr are __sF[0..2], 152 bytes each
//   - sigset_t is one long on LP64 bionic, 128 bytes in glibc
//   - sysconf names are numbered differently
//   - long double is binary128 on x86_64 Android, x87 extended in glibc
//   - errno is __errno(), strerror_r the POSIX one
//   - a static PTHREAD_RECURSIVE_MUTEX_INITIALIZER_NP puts the type into the
//     first word, which glibc reads as "locked"
#define _GNU_SOURCE
#include <errno.h>
#include <locale.h>
#include <pthread.h>
#include <signal.h>
#include <stdarg.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <wchar.h>
extern int __xpg_strerror_r(int, char*, size_t);

#define SAME(name) __asm__(".globl bionic_" #name "\n.type bionic_" #name ",@function\nbionic_" #name ":\n\tjmp " #name "@PLT\n.symver bionic_" #name "," #name "@LIBC\n");
SAME(__ctype_get_mb_cur_max) SAME(__cxa_atexit) SAME(__cxa_finalize) SAME(__stack_chk_fail) SAME(_exit) SAME(abort)
SAME(bsearch) SAME(calloc) SAME(clock_gettime) SAME(close) SAME(closelog) SAME(dl_iterate_phdr) SAME(dlclose) SAME(dlopen)
SAME(dlsym) SAME(exp) SAME(expf) SAME(free) SAME(freelocale) SAME(frexp) SAME(getenv) SAME(iswlower_l) SAME(ldexp)
SAME(log2f) SAME(madvise) SAME(malloc) SAME(mbrlen) SAME(mbrtowc) SAME(mbsnrtowcs) SAME(mbsrtowcs) SAME(mbtowc)
SAME(memchr) SAME(memcmp) SAME(memcpy) SAME(memmove) SAME(memset) SAME(mmap) SAME(munmap) SAME(nanosleep) SAME(newlocale)
SAME(open) SAME(openlog) SAME(posix_memalign) SAME(prctl) SAME(pthread_cond_broadcast) SAME(pthread_cond_destroy)
SAME(pthread_cond_init) SAME(pthread_cond_signal) SAME(pthread_cond_wait) SAME(pthread_create) SAME(pthread_getschedparam)
SAME(pthread_getspecific) SAME(pthread_join) SAME(pthread_key_create) SAME(pthread_key_delete) SAME(pthread_once)
SAME(pthread_rwlock_rdlock) SAME(pthread_rwlock_unlock) SAME(pthread_rwlock_wrlock) SAME(pthread_self)
SAME(pthread_setspecific) SAME(qsort) SAME(read) SAME(realloc) SAME(sched_yield) SAME(snprintf) SAME(sscanf) SAME(strcmp)
SAME(strerror) SAME(strftime_l) SAME(strlen) SAME(strncmp) SAME(strncpy) SAME(strtod) SAME(strtof) SAME(strtol)
SAME(strtoll_l) SAME(strtoull_l) SAME(syscall) SAME(syslog) SAME(uselocale) SAME(vasprintf) SAME(vsnprintf) SAME(vsscanf)
SAME(wcrtomb) SAME(wcslen) SAME(wcsnrtombs) SAME(wmemchr) SAME(write)

#define OWN(name) __asm__(".symver bionic_" #name "," #name "@LIBC");
char bionic___sF[3][152];
OWN(__sF)
static FILE* file(void* f) {
    return f == bionic___sF[0] ? stdin : f == bionic___sF[1] ? stdout : f == bionic___sF[2] ? stderr : (FILE*)f;
}
int bionic_fflush(void* f) { return fflush(f ? file(f) : NULL); } OWN(fflush)
int bionic_vfprintf(void* f, const char* fmt, va_list ap) { return vfprintf(file(f), fmt, ap); } OWN(vfprintf)
int bionic_fprintf(void* f, const char* fmt, ...) {
    va_list ap;
    va_start(ap, fmt);
    int r = vfprintf(file(f), fmt, ap);
    va_end(ap);
    return r;
} OWN(fprintf)
int bionic_fputc(int c, void* f) { return fputc(c, file(f)); } OWN(fputc)
wint_t bionic_fputwc(wchar_t c, void* f) { return fputwc(c, file(f)); } OWN(fputwc)
size_t bionic_fwrite(const void* p, size_t s, size_t n, void* f) { return fwrite(p, s, n, file(f)); } OWN(fwrite)
int bionic_getc(void* f) { return getc(file(f)); } OWN(getc)
wint_t bionic_getwc(void* f) { return getwc(file(f)); } OWN(getwc)
int bionic_ungetc(int c, void* f) { return ungetc(c, file(f)); } OWN(ungetc)
wint_t bionic_ungetwc(wint_t c, void* f) { return ungetwc(c, file(f)); } OWN(ungetwc)

int* bionic___errno(void) { return &errno; } OWN(__errno)
int bionic_strerror_r(int e, char* buf, size_t n) { return __xpg_strerror_r(e, buf, n); } OWN(strerror_r)
__float128 bionic_strtold_l(const char* s, char** end, locale_t l) { return (__float128)strtold_l(s, end, l); } OWN(strtold_l)

long bionic_sysconf(int name) {
    switch (name) {
        case 0x06: return sysconf(_SC_CLK_TCK);
        case 0x27: case 0x28: return sysconf(_SC_PAGESIZE);
        case 0x60: return sysconf(_SC_NPROCESSORS_CONF);
        case 0x61: return sysconf(_SC_NPROCESSORS_ONLN);
        case 0x62: return sysconf(_SC_PHYS_PAGES);
    }
    fprintf(stderr, "bionic shim: sysconf(0x%x) not translated\n", name);
    return -1;
} OWN(sysconf)

int bionic_sigfillset(uint64_t* set) { *set = ~0ULL; return 0; } OWN(sigfillset)
int bionic_pthread_sigmask(int how, const uint64_t* set, uint64_t* old) {
    sigset_t s, o;
    sigemptyset(&s);
    for (int i = 1; set && i <= 64; i++) if (*set & (1ULL << (i - 1))) sigaddset(&s, i);
    int r = pthread_sigmask(how, set ? &s : NULL, &o);
    if (old) { *old = 0; for (int i = 1; i <= 64; i++) if (sigismember(&o, i) == 1) *old |= 1ULL << (i - 1); }
    return r;
} OWN(pthread_sigmask)

static void mutexFromBionic(pthread_mutex_t* mutex) {
    int32_t* m = (int32_t*)mutex;
    if (m[0] != 0x4000 && m[0] != 0x8000) return;   // recursive, error-checking
    for (int i = 1; i < 10; i++) if (m[i]) return;
    pthread_mutexattr_t a;
    pthread_mutexattr_init(&a);
    pthread_mutexattr_settype(&a, m[0] == 0x4000 ? PTHREAD_MUTEX_RECURSIVE : PTHREAD_MUTEX_ERRORCHECK);
    pthread_mutex_init(mutex, &a);
    pthread_mutexattr_destroy(&a);
}
int bionic_pthread_mutex_init(pthread_mutex_t* m, const void* attr) {
    if (attr) { fprintf(stderr, "bionic shim: pthread_mutex_init with attributes\n"); abort(); }
    return pthread_mutex_init(m, NULL);
} OWN(pthread_mutex_init)
int bionic_pthread_mutex_lock(pthread_mutex_t* m) { mutexFromBionic(m); return pthread_mutex_lock(m); } OWN(pthread_mutex_lock)
int bionic_pthread_mutex_unlock(pthread_mutex_t* m) { mutexFromBionic(m); return pthread_mutex_unlock(m); } OWN(pthread_mutex_unlock)
int bionic_pthread_mutex_destroy(pthread_mutex_t* m) { mutexFromBionic(m); return pthread_mutex_destroy(m); } OWN(pthread_mutex_destroy)

int bionic___system_property_get(const char* name, char* value) { (void)name; value[0] = 0; return 0; } OWN(__system_property_get)
void bionic_android_set_abort_message(const char* msg) { fprintf(stderr, "abort message: %s\n", msg); } OWN(android_set_abort_message)
