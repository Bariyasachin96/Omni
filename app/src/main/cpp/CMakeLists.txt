cmake_minimum_required(VERSION 3.18.1)
project("easyvoice_native")
# NO MODEL IN THE NATIVE CORE (owner, 2026-10-28: "koi model rakhna hi nahin ...
# ML Kit use karenge har jagah ... aur Unicode use karenge"). The core reads a
# text's script from the Unicode tables (unicode_tables.h) and, where the text
# has to name a language, asks Google's ML Kit language identification -- the
# Gradle library com.google.mlkit:language-id, called from Kotlin
# (EasyVoiceTtsService.mlkitIdentify) through JNI. CLD2 (removed 2026-10-21),
# CLD3 (2026-09-02), and the word-and-ending model, letter rules and the copy of
# ML Kit's network that sat here until 2026-10-28 are all gone; do not add any
# of them back without that decision being reversed in writing
# (tools/check/invariants.sh fails the build on CLD2, CLD3 or a model header).
add_library(easyvoice SHARED
    tts_engine_core.cpp
)
# PROFILE-GUIDED OPTIMIZATION (owner, 2026-10-07). pgo/easyvoice.profdata is
# trained by tools/pgo/run.sh with this NDK's own clang, on the core's real JNI
# entry points over the corebytes corpus, weighted toward the mix mode a screen
# reader actually uses. Front-end (source-based) profiles are keyed by function
# name and control-flow hash, so one profile serves arm64-v8a and armeabi-v7a;
# run.sh compiles both with -Werror=profile-instr-out-of-date to prove it.
# Measured (run.sh, x86_64-android, callgrind): the core's own instructions
# -8.9% (buildMixChunks -13%; measured 2026-10-07, while CLD2 was still here);
# every answer byte-identical to the plain build (tools/pgo/run.sh re-proves it
# on every --update). After ANY change to tts_engine_core.cpp,
# run tools/pgo/run.sh --update (check-all fails on a stale profile).
set(EV_PGO_PROFILE ${CMAKE_CURRENT_SOURCE_DIR}/pgo/easyvoice.profdata)
target_compile_options(easyvoice PRIVATE "-fprofile-instr-use=${EV_PGO_PROFILE}")
target_link_options(easyvoice PRIVATE "-fprofile-instr-use=${EV_PGO_PROFILE}")
get_target_property(EV_SOURCES easyvoice SOURCES)
set_source_files_properties(${EV_SOURCES} PROPERTIES OBJECT_DEPENDS ${EV_PGO_PROFILE})
find_library(log-lib log)
target_link_libraries(easyvoice ${log-lib})
# --gc-sections is the LINKER half of -ffunction-sections/-fdata-sections in
# build.gradle.kts. Without it those two compile flags do nothing at all.
#
# --strip-all drops .symtab and .strtab. The seven JNI entry points live in
# .dynsym, which a shared library must keep to be loadable, so they survive --
# verified with `nm -D --defined-only`, all seven still exported after stripping.
#
# WHAT THE LIBRARY IS MADE OF (2026-10-28): the Unicode tables and the core's
# own code -- no detector tables at all. ML Kit's own native library and model
# (liblanguage_id_l2c_jni.so, tflite_langid.tflite.jpg) come from its AAR
# through Gradle and are packaged beside this one. Before: CLD2's 6.1 MB of
# tables (until 2026-10-21), then the model ML Kit runs (0.33 MB) and the
# word-and-ending model (16.32 MB of tables at the end, 2026-10-27).
#
# --gc-sections pairs with the -ffunction-sections/-fdata-sections in
# build.gradle.kts; --strip-all removes .symtab and .strtab only, and every
# Java_ entry point lives in .dynsym, which a shared library must keep.
#
# --icf=safe folds identical functions whose address is never taken, so a
# function-pointer compare cannot change meaning -- that is why it is `safe`
# and not `all`, which measured only 448 bytes better and does take addresses.
# -flto=thin must appear at link as well as at compile; see the measurement
# table beside cppFlags in build.gradle.kts.
#
# THE EXPORT LIST AND PACKED RELOCATIONS (owner, 2026-10-01). The note that
# used to sit here said --pack-dyn-relocs and --exclude-libs,ALL "measured zero".
# That was measured on a HOST link, and the shipped APK said otherwise: build
# 945's arm64 libeasyvoice.so exported 698 dynamic symbols -- almost all of the
# static libc++ the NDK links in -- and carried 3,485 relocations as plain
# 24-byte RELA. An exported symbol is a root --gc-sections must keep, so all
# that libc++ code stayed. exports.map now exports ONLY Java_* and JNI_OnLoad
# (the JNI names the VM resolves; nothing uses RegisterNatives), and
# --pack-dyn-relocs=android packs the relocations (API 23+, minSdk is 24).
# Measured on the real NDK 30 build, arm64:
#     exported symbols       698 -> 7
#     .text            171,904 -> 122,612     .eh_frame 23,940 -> 8,536
#     .so raw        6,667,704 -> 6,450,712   gzipped 4,821,154 -> 4,772,860
# .rodata was unchanged, which was the point: nothing the detector read moved.
# tools/check/native-exports.sh guards the count.
# Still not added: --hash-style=gnu, --as-needed, -z nostart-stop-gc.
#
# 16 KB PAGES ARE STATED, NOT INHERITED (owner, 2026-09-23). NDK r28 and later
# already align every PT_LOAD segment to 16 KB, and the shipped arm64 library was
# measured at p_align 16384 -- but that was the NDK's default doing it. Google's
# own page (developer.android.com/guide/practices/page-sizes) gives these two
# flags as the way to make it explicit, so a future NDK or toolchain change
# cannot quietly drop the app back to 4 KB and into compatibility mode on a
# 16 KB device. On the 32-bit ABI they cost nothing but a little padding.
# tools/check/native-pagesize.sh verifies the built APK.
target_link_options(easyvoice PRIVATE
    "-Wl,--gc-sections" "-Wl,--strip-all" "-Wl,--icf=safe" "-flto=thin"
    "-Wl,-z,max-page-size=16384" "-Wl,-z,common-page-size=16384"
    "-Wl,--version-script=${CMAKE_CURRENT_SOURCE_DIR}/exports.map"
    "-Wl,--exclude-libs,ALL" "-Wl,--pack-dyn-relocs=android")
set_target_properties(easyvoice PROPERTIES
    LINK_DEPENDS "${CMAKE_CURRENT_SOURCE_DIR}/exports.map")
