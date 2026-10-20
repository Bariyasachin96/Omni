// Android's liblog, for ML Kit's library: to stderr.
#include <stdarg.h>
#include <stdio.h>
int __android_log_write(int prio, const char* tag, const char* text) { return fprintf(stderr, "log %d %s: %s\n", prio, tag, text); }
int __android_log_vprint(int prio, const char* tag, const char* fmt, va_list ap) {
    fprintf(stderr, "log %d %s: ", prio, tag);
    int r = vfprintf(stderr, fmt, ap);
    fputc('\n', stderr);
    return r;
}
