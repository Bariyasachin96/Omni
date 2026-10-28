// A harness's side of tools/mlkit: install Google's ML Kit (host.c, run on
// this desktop) as the core's language identifier, as JNI_OnLoad installs the
// Kotlin one on a phone. Needs the environment tools/mlkit/build.sh sets up;
// without EV_MLKIT_MODEL, or with a core that has no evSetLanguageIdentifier
// (one from before 2026-10-28, which ran its own model), it does nothing.
#pragma once
#include <cstddef>
#include <cstdio>
#include <cstdlib>
extern "C" int evmlkit_load(const char* model);
extern "C" int evmlkit_identify(const char* utf8, size_t len, char (*tags)[16], float* confidences, int max);
typedef int (*EvLidFnHost)(const char* utf8, size_t len, char (*tags)[16], float* confidences, int max);
extern "C" void evSetLanguageIdentifier(EvLidFnHost) __attribute__((weak));
// true when ML Kit is installed
static inline bool installMlKit() {
    const char* model = getenv("EV_MLKIT_MODEL");
    if (!model || !*model || !evSetLanguageIdentifier) return false;
    if (!evmlkit_load(model)) { fprintf(stderr, "ML Kit could not be loaded\n"); exit(1); }
    evSetLanguageIdentifier(evmlkit_identify);
    return true;
}
