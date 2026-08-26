cmake_minimum_required(VERSION 3.18.1)
project("easyvoice_native")
set(CLD2_DIR ${CMAKE_CURRENT_SOURCE_DIR}/cld2_src/internal)
set(CLD3_DIR ${CMAKE_CURRENT_SOURCE_DIR}/cld3_src/src)
set(CLD3_GEN ${CMAKE_CURRENT_SOURCE_DIR}/cld3_gen)
set(protobuf_BUILD_TESTS OFF CACHE BOOL "" FORCE)
set(protobuf_BUILD_PROTOC_BINARIES OFF CACHE BOOL "" FORCE)
set(protobuf_INSTALL OFF CACHE BOOL "" FORCE)
set(protobuf_WITH_ZLIB OFF CACHE BOOL "" FORCE)
set(BUILD_SHARED_LIBS OFF CACHE BOOL "" FORCE)
set(protobuf_BUILD_SHARED_LIBS OFF CACHE BOOL "" FORCE)
add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/protobuf_src/cmake protobuf_build)
file(GLOB CLD3_SCRIPT_SPAN ${CLD3_DIR}/script_span/*.cc)
list(FILTER CLD3_SCRIPT_SPAN EXCLUDE REGEX "test")
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
    ${CLD3_GEN}/cld_3/protos/feature_extractor.pb.cc
    ${CLD3_GEN}/cld_3/protos/sentence.pb.cc
    ${CLD3_GEN}/cld_3/protos/task_spec.pb.cc
    ${CLD3_DIR}/base.cc
    ${CLD3_DIR}/embedding_feature_extractor.cc
    ${CLD3_DIR}/embedding_network.cc
    ${CLD3_DIR}/feature_extractor.cc
    ${CLD3_DIR}/feature_types.cc
    ${CLD3_DIR}/fml_parser.cc
    ${CLD3_DIR}/language_identifier_features.cc
    ${CLD3_DIR}/lang_id_nn_params.cc
    ${CLD3_DIR}/nnet_language_identifier.cc
    ${CLD3_DIR}/registry.cc
    ${CLD3_DIR}/relevant_script_feature.cc
    ${CLD3_DIR}/sentence_features.cc
    ${CLD3_DIR}/task_context.cc
    ${CLD3_DIR}/task_context_params.cc
    ${CLD3_DIR}/unicodetext.cc
    ${CLD3_DIR}/utils.cc
    ${CLD3_DIR}/workspace.cc
    ${CLD3_SCRIPT_SPAN}
)
target_include_directories(easyvoice_core PRIVATE ${CLD2_DIR}/../public ${CLD2_DIR} ${CLD3_DIR} ${CLD3_GEN})
find_library(log-lib log)
target_link_libraries(easyvoice_core libprotobuf-lite ${log-lib})
target_link_options(easyvoice_core PRIVATE "-Wl,--gc-sections")
