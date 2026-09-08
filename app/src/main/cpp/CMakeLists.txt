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
add_library(easyvoice_core SHARED
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
target_include_directories(easyvoice_core PRIVATE ${CLD2_DIR}/../public ${CLD2_DIR})
find_library(log-lib log)
target_link_libraries(easyvoice_core ${log-lib})
target_link_options(easyvoice_core PRIVATE "-Wl,--gc-sections")
