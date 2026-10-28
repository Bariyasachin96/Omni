// Android's liblog, for ML Kit's library: warnings and errors to stderr; its
// informational lines ("Initialized TensorFlow Lite runtime") are dropped, so a
// harness's own output stays its own.
#include <stdarg.h>
#include <stdio.h>
enum { kWarn = 5 };   // ANDROID_LOG_WARN
int __android_log_write(int prio, const char* tag, const char* text) {
    return prio < kWarn ? 0 : fprintf(stderr, "log %d %s: %s\n", prio, tag, text);
}
int __android_log_vprint(int prio, const char* tag, const char* fmt, va_list ap) {
    if (prio < kWarn) return 0;
    fprintf(stderr, "log %d %s: ", prio, tag);
    int r = vfprintf(stderr, fmt, ap);
    fputc('\n', stderr);
    return r;
}
