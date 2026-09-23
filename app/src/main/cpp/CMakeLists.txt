cmake_minimum_required(VERSION 3.18.1)
project("easyvoice_native")
# CLD2 is the only detector. CLD3, its generated protobufs and the protobuf
# runtime they needed were removed on 2026-09-02 at the owner's instruction --
# see CLAUDE.md, "CLD3 IS GONE". Do not add them back without that decision
# being reversed in writing.
# THE FULL CLD2 TABLES, NOT THE COMPACT ONES (owner, 2026-09-08).
#
# CLD2 ships two matched table sets and its own repo builds each with its own
# script -- internal/compile.sh for the compact one and internal/compile_full.sh
# for the full one. The five files below that carry a "0122" or "_32" suffix are
# compile_full.sh's set; the ones they replaced (quadchrome_16, deltaoctachrome,
# distinctoctachrome, score_quad_octa_2, cjk_delta_bi_4) were compile.sh's.
# They must be swapped as a SET -- the score table and the CJK delta table are
# built to match their quad table, so mixing them is not a supported build.
#
# Nothing else changes, and that is verified rather than hoped: both quad tables
# define exactly the same two public symbols, CLD2::kQuad_obj and
# CLD2::kQuad_obj2 (`nm -C --defined-only`), and differ only in the internal
# names of the arrays behind them. So this is a file-list change and no C++ in
# this project moves.
#
# WHAT IT BUYS, from CLD2's OWN evaluation files rather than a claim --
# docs/evaluate_cld2_small_20140122.txt and docs/evaluate_cld2_large_20140122.txt,
# same corpus, same date: the compact set scores 82 languages and the full set
# 183. The extra hundred are the ones the QUADGRAM scorer needs a bigger table
# to name -- Frisian, Breton, Corsican, Luxembourgish, Occitan, Maori, Samoan,
# Xhosa, Shona, Wolof, Kurdish, Pashto, Sindhi, Tajik, Kazakh, Uzbek, Turkmen,
# Uighur, Tatar, Bashkir, Mongolian, Sanskrit and the rest. Accuracy on the
# languages both sets already had is equal or better (Bihari's F-measure goes
# 0.7769 -> 0.8541; Arabic and Bengali reach 1.0000).
#
# It does NOT change the script-defined languages. Gujarati, Tamil, Telugu,
# Kannada, Malayalam, Punjabi, Oriya, Sinhala, Khmer, Lao, Georgian, Armenian
# and the rest are decided by their Unicode script, never by the quad table --
# CLD2's README says so and our own SCRIPT_FIXED_LANG ladder is the same rule.
# They worked before this change and are untouched by it. (They are absent from
# the small evaluation file only because that run did not cover them, which is
# what makes that file's 82 a count of quadgram-scored languages rather than of
# what the build can detect.)
#
# WHAT IT COSTS, measured by compiling both sets with these exact flags and
# reading the object sizes, not estimated from the source:
#
#     compact tables    951,416 bytes   0.91 MB per ABI
#     full tables     6,103,648 bytes   5.82 MB per ABI
#     delta          +5,152,232 bytes  +4.91 MB per ABI
#
# and the APK ships armeabi-v7a AND arm64-v8a, so that is about +9.8 MB
# uncompressed. This is the one change in the project that makes the download
# substantially bigger, and it is a deliberate owner decision that reverses the
# earlier "keep the smallest table" note. To go back, swap the five names below
# for compile.sh's and nothing else.

set(CLD2_DIR ${CMAKE_CURRENT_SOURCE_DIR}/cld2_src/internal)
add_library(easyvoice SHARED
    tts_engine_core.cpp
    ${CLD2_DIR}/cldutil.cc
    ${CLD2_DIR}/cldutil_shared.cc
    ${CLD2_DIR}/compact_lang_det.cc
    ${CLD2_DIR}/compact_lang_det_hint_code.cc
    ${CLD2_DIR}/compact_lang_det_impl.cc
    ${CLD2_DIR}/debug.cc
    ${CLD2_DIR}/fixunicodevalue.cc
    ${CLD2_DIR}/generated_entities.cc
    ${CLD2_DIR}/generated_language.cc
    ${CLD2_DIR}/generated_ulscript.cc
    ${CLD2_DIR}/getonescriptspan.cc
    ${CLD2_DIR}/lang_script.cc
    ${CLD2_DIR}/offsetmap.cc
    ${CLD2_DIR}/scoreonescriptspan.cc
    ${CLD2_DIR}/tote.cc
    ${CLD2_DIR}/utf8statetable.cc
    ${CLD2_DIR}/cld_generated_cjk_uni_prop_80.cc
    ${CLD2_DIR}/cld2_generated_cjk_compatible.cc
    ${CLD2_DIR}/cld_generated_cjk_delta_bi_32.cc
    ${CLD2_DIR}/generated_distinct_bi_0.cc
    ${CLD2_DIR}/cld2_generated_quad0122.cc
    ${CLD2_DIR}/cld2_generated_deltaocta0122.cc
    ${CLD2_DIR}/cld2_generated_distinctocta0122.cc
    ${CLD2_DIR}/cld_generated_score_quad_octa_0122.cc
)
target_include_directories(easyvoice PRIVATE ${CLD2_DIR}/../public ${CLD2_DIR})
find_library(log-lib log)
target_link_libraries(easyvoice ${log-lib})
# --gc-sections is the LINKER half of -ffunction-sections/-fdata-sections in
# build.gradle.kts. Without it those two compile flags do nothing at all.
#
# --strip-all drops .symtab and .strtab. The seven JNI entry points live in
# .dynsym, which a shared library must keep to be loadable, so they survive --
# verified with `nm -D --defined-only`, all seven still exported after stripping.
#
# THERE IS NO NATIVE R8 HERE, AND THAT IS MEASURED (2026-09-09).
# The owner asked for a native equivalent of R8's shrinking. R8 works by removing
# unreachable CODE, and this library is not code:
#
#     .rodata (CLD2's tables)   6,253,216 bytes    95%
#     .text   (all our code)      110,533 bytes   1.7%
#
# Every one of those tables is reached from the detector, so there is nothing to
# garbage-collect -- and --gc-sections, the one flag that IS R8-shaped, has been
# on all along. Every remaining flag was then built and weighed; that pass is
# finished and its table lives beside cppFlags in build.gradle.kts. The total it
# can save is about 75 KB raw and 26 KB of actual download per ABI, against a
# 6.4 MB library. Do not chase them again.
#
# AND COMPRESSION CANNOT SAVE THIS EITHER, WHICH IS THE ANSWER TO "CLD2 KA
# OPTIMISATION" (owner, 2026-09-09). Measured on the real table bytes of the
# full set rather than argued:
#
#     raw                                   6,165,933
#     deflate -9   <- what the APK does     4,653,696     -25%
#     xz -9e                                4,106,444     -33%
#
# A quadgram table is a HASH table, so its bytes are close to random and the
# APK's own deflate already takes most of what there is. Storing the tables
# pre-compressed and inflating them at load would buy 547,252 bytes per ABI and
# cost 6 MB of dirty private RAM plus decompression on the path to the first
# word. Refused on the measurement, not on taste.
#
# THE SIZE IS THE DUPLICATE, NOT THE TABLE. The APK ships this library once per
# ABI, so every one of those bytes is paid twice. That is what
# `splits { abi }` in build.gradle.kts removes, and it removes nothing else --
# see the comment there, including why CLD2's own CLD2_DYNAMIC_MODE solves the
# same duplicate and lands on a worse number.
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
# NOT added, and each for a measured reason: --pack-dyn-relocs is ALREADY
# supplied by clang's own driver below API 28 (Linux.cpp:288) and minSdk is 24,
# so passing it changes nothing; --exclude-libs,ALL, --hash-style=gnu,
# --as-needed and -z nostart-stop-gc each measured zero or noise.
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
    "-Wl,-z,max-page-size=16384" "-Wl,-z,common-page-size=16384")
