cmake_minimum_required(VERSION 3.18.1)
project("easyvoice_native")
# CLD2 is the only detector. CLD3, its generated protobufs and the protobuf
# runtime they needed were removed on 2026-09-02 at the owner's instruction --
# see CLAUDE.md, "CLD3 IS GONE". Do not add them back without that decision
# being reversed in writing.
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
    ${CLD2_DIR}/cld_generated_cjk_delta_bi_4.cc
    ${CLD2_DIR}/generated_distinct_bi_0.cc
    ${CLD2_DIR}/cld2_generated_quadchrome_16.cc
    ${CLD2_DIR}/cld2_generated_deltaoctachrome.cc
    ${CLD2_DIR}/cld2_generated_distinctoctachrome.cc
    ${CLD2_DIR}/cld_generated_score_quad_octa_2.cc
)
target_include_directories(easyvoice_core PRIVATE ${CLD2_DIR}/../public ${CLD2_DIR})
find_library(log-lib log)
target_link_libraries(easyvoice_core ${log-lib})
target_link_options(easyvoice_core PRIVATE "-Wl,--gc-sections")
