#!/usr/bin/env python3
"""langid_model.h: the language-identification model, as C++ data.

The model is Google's MediaPipe Language Detector (tools/langid/
language_detector.tflite, Apache License 2.0, Learn2Compress team, Google
Research). It is the model inside ML Kit's language identification
(com.google.mlkit:language-id 17.0.6): the same 11 operations in the same
order, all 29 tensors and every one of their 309,852 weight bytes identical,
the same 111 labels in the same order (checked 2026-10-20). ML Kit's file only
adds the label list as a tensor and two extra outputs.

The app runs it itself, in the native core, with no TensorFlow Lite runtime:
the graph is fixed and small (langid.h). This script turns the .tflite into
the arrays langid.h reads, and copies the rune tables (isalpharune /
tolowerrune) of MediaPipe's tokenizer out of tools/langid/mediapipe/
runetypebody.h, so nothing in the header is typed by hand.

    gen_model.py [--check]     --check: fail if the header differs

Reading the .tflite needs no package: a FlatBuffer is little-endian offsets,
and only the fields used here are read.
"""
import hashlib
import io
import os
import re
import struct
import sys
import zipfile

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(HERE))
MODEL = os.path.join(HERE, "language_detector.tflite")
RUNETYPES = os.path.join(HERE, "mediapipe", "runetypebody.h")
OUT = os.path.join(ROOT, "app", "src", "main", "cpp", "langid_model.h")
MODEL_SHA256 = "7db4f23dfe1ad8966b050b419a865da451143fd43eb6b606a256aadeeb1e5417"

buf = open(MODEL, "rb").read()
digest = hashlib.sha256(buf).hexdigest()
if digest != MODEL_SHA256:
    sys.exit("language_detector.tflite is not the reviewed model (sha256 %s)" % digest)


# ---- the few FlatBuffer reads the TFLite schema needs ----------------------
def u16(p): return struct.unpack_from("<H", buf, p)[0]
def i32(p): return struct.unpack_from("<i", buf, p)[0]
def u32(p): return struct.unpack_from("<I", buf, p)[0]


def field(table, slot):
    """Position of field `slot` of the table at `table`, or None if absent."""
    vtable = table - i32(table)
    entry = 4 + 2 * slot
    if entry >= u16(vtable):
        return None
    off = u16(vtable + entry)
    return table + off if off else None


def ref(p): return p + u32(p)


def vector(table, slot):
    p = field(table, slot)
    if p is None:
        return 0, 0
    p = ref(p)
    return p + 4, u32(p)


def tables(table, slot):
    start, n = vector(table, slot)
    return [ref(start + 4 * k) for k in range(n)]


def string(table, slot):
    start, n = vector(table, slot)
    return buf[start:start + n].decode()


def scalar(table, slot, fmt, default):
    p = field(table, slot)
    return default if p is None else struct.unpack_from("<" + fmt, buf, p)[0]


model = u32(0)
buffers = tables(model, 4)                       # Model.buffers
graph = tables(model, 2)[0]                      # Model.subgraphs[0]
opcodes = tables(model, 1)                       # Model.operator_codes

TYPES = {0: ("f", 4, "float"), 3: ("B", 1, "uint8_t"), 9: ("b", 1, "int8_t")}
tensors = {}
for t in tables(graph, 0):                       # SubGraph.tensors
    start, n = vector(t, 0)                      # Tensor.shape
    shape = [i32(start + 4 * k) for k in range(n)]
    kind = scalar(t, 1, "B", 0)                  # Tensor.type
    data_start, data_len = vector(buffers[scalar(t, 2, "I", 0)], 0)  # Buffer.data
    scale = None
    q = field(t, 4)                              # Tensor.quantization
    if q is not None:
        s_start, s_n = vector(ref(q), 2)         # QuantizationParameters.scale
        if s_n:
            scale = [struct.unpack_from("<f", buf, s_start + 4 * k)[0] for k in range(s_n)]
    tensors[string(t, 3)] = (shape, kind, data_start, data_len, scale)

# The graph must be the one langid.h computes, wired the way it computes it:
# NGramHash, UNPACK into the four n-gram lengths, one KmeansEmbeddingLookup
# each, CONCATENATION in n-gram order, three FULLY_CONNECTED, SOFTMAX -- and
# with the options langid.h assumes.
def ints(table, slot):
    start, n = vector(table, slot)
    return [i32(start + 4 * k) for k in range(n)]


names = [string(t, 3) for t in tables(graph, 0)]
ops = []
for o in tables(graph, 3):                       # SubGraph.operators
    code = opcodes[scalar(o, 0, "I", 0)]         # Operator.opcode_index
    kind = (string(code, 1) if field(code, 1) is not None else
            max(scalar(code, 0, "b", 0), scalar(code, 3, "i", 0)))
    p = field(o, 4)                              # Operator.builtin_options
    ops.append((kind, [names[i] for i in ints(o, 1)], [names[i] for i in ints(o, 2)],
                ref(p) if p is not None else None))
EXPECTED = ["NGramHash", 88] + ["KmeansEmbeddingLookup"] * 4 + [2, 9, 9, 9, 25]
if [op[0] for op in ops] != EXPECTED:
    sys.exit("unexpected graph: %r" % [op[0] for op in ops])
KM = "pod/head_0_ngram_%d/kmeans_compression/"
FC = [("pod/batch_norm/batch_norm_core/mul_1", "pod/Relu;pod/batch_norm/batch_norm_core/add_1", 160, 96, 1),
      ("pod/batch_norm_1/batch_norm_core/mul_1", "pod/Relu_1;pod/batch_norm_1/batch_norm_core/add_1", 200, 160, 1),
      ("pod/batch_norm_2/batch_norm_core/mul_1", "pod/batch_norm_2/batch_norm_core/add_11", 111, 200, 0)]
wired = (ops[0][1] == ["Placeholder"] and ops[1][1] == ops[0][2] and len(ops[1][2]) == 4
         and all(ops[2 + k][1] == [ops[1][2][k], KM % (k + 1) + "c_matrix", KM % (k + 1) + "b_matrix"]
                 for k in range(4))
         and ops[6][1] == [ops[2 + k][2][0] for k in range(4)]
         and all(ops[7 + k][1] == [ops[6 + k][2][0], FC[k][0], FC[k][1]] for k in range(3))
         and ops[10][1] == ops[9][2] and [names[i] for i in ints(graph, 2)] == ops[10][2])
opt = lambda k, slot, fmt, default: scalar(ops[k][3], slot, fmt, default)
options = (opt(1, 0, "i", 0) == 4 and opt(1, 1, "i", 0) == 1                 # UnpackOptions num, axis
           and opt(6, 0, "i", 0) in (1, -1) and opt(6, 1, "b", 0) == 0       # Concatenation axis, no activation
           and all(opt(7 + k, 0, "b", 0) == FC[k][4]                         # RELU, RELU, none
                   and opt(7 + k, 1, "b", 0) == 0 and opt(7 + k, 2, "B", 0) == 0
                   and opt(7 + k, 3, "B", 0) == 1 for k in range(3))         # asymmetric_quantize_inputs
           and opt(10, 0, "f", 0.0) == 1.0)                                  # Softmax beta
if not (wired and options):
    sys.exit("the graph's wiring or options are not what langid.h computes")


def array(name, shape, kind):
    t = tensors[name]
    if t[0] != shape or t[1] != kind:
        sys.exit("%s is %r type %d, expected %r type %d" % (name, t[0], t[1], shape, kind))
    fmt, width, _ = TYPES[kind]
    count = t[3] // width
    return list(struct.unpack_from("<%d%s" % (count, fmt), buf, t[2])), t[4]


def flt(x):
    # Exact: every float32 is a double, and a hex literal names it bit for bit.
    if x == 0:
        return "0.0f" if struct.pack("<f", x) == b"\0\0\0\0" else "-0.0f"
    return float.hex(x) + "f"


def emit(ctype, name, values, per_line):
    out = ["static const %s %s[%d] = {" % (ctype, name, len(values))]
    for k in range(0, len(values), per_line):
        out.append("    " + ",".join(flt(v) if ctype == "float" else str(v) for v in values[k:k + per_line]) + ",")
    out.append("};")
    return "\n".join(out) + "\n"


VOCAB = [5500, 5500, 13000, 13000]
h = []
h.append("// GENERATED by tools/langid/gen_model.py from tools/langid/language_detector.tflite\n"
         "// (MediaPipe Language Detector, Apache License 2.0, sha256 %s...) and the rune\n"
         "// tables of tools/langid/mediapipe/runetypebody.h (MediaPipe, Apache License 2.0,\n"
         "// forked from Rob Pike and Ken Thompson's libutf). The same model ML Kit's language\n"
         "// identification runs. Do not edit; regenerate.\n" % MODEL_SHA256[:16])
h.append("#pragma once\n#include <cstdint>\n\n")

labels = zipfile.ZipFile(io.BytesIO(buf)).read("labels.txt").decode().split("\n")
labels = [x for x in labels if x]
if len(labels) != 111 or labels[0] != "unknown":
    sys.exit("unexpected labels.txt")
h.append("#define EV_LID_CLASSES %d\n" % len(labels))
h.append("static const char* const kLidLabels[EV_LID_CLASSES] = {\n")
for k in range(0, len(labels), 12):
    h.append("    " + ", ".join('"%s"' % x for x in labels[k:k + 12]) + ",\n")
h.append("};\n\n")

# NGramHash's custom options, read with the official flexbuffers reader when
# the model was reviewed: {seed: 10911, ngram_lengths: [1, 2, 3, 4],
# vocab_sizes: [5500, 5500, 13000, 13000], max_splits: 128}, lowercase_input
# absent (= true). The sha256 above pins them; the vocab sizes are also the
# encoding tables' row counts, checked below.
h.append("static const uint64_t kLidSeed = 10911;\nstatic const int kLidMaxSplits = 128;\n"
         "static const int kLidNgramLength[4] = {1, 2, 3, 4};\n"
         "static const int kLidVocab[4] = {%s};\n\n" % ", ".join(str(v) for v in VOCAB))

for k in range(4):
    enc, _ = array(KM % (k + 1) + "c_matrix", [VOCAB[k] + 1, 6], 3)
    code, _ = array(KM % (k + 1) + "b_matrix", [256, 4], 0)
    h.append("// ngram %d: encoding table [%d][6] (centroid per block) and codebook [256][4]\n" % (k + 1, VOCAB[k] + 1))
    h.append(emit("uint8_t", "kLidEnc%d" % k, enc, 48))
    h.append(emit("float", "kLidCodebook%d" % k, code, 8))

for k, (w, b, rows, cols, _) in enumerate(FC):
    weights, scale = array(w, [rows, cols], 9)
    bias, _ = array(b, [rows], 0)
    if scale is None or len(scale) != 1:
        sys.exit("%s: expected one per-tensor scale" % w)
    h.append("// fully connected %d: int8 weights [%d][%d] (one scale), float bias\n" % (k + 1, rows, cols))
    h.append("static const float kLidScale%d = %s;\n" % (k, flt(scale[0])))
    h.append(emit("int8_t", "kLidWeight%d" % k, weights, 48))
    h.append(emit("float", "kLidBias%d" % k, bias, 8))

# The rune tables, verbatim from MediaPipe's runetypebody.h.
src = open(RUNETYPES, encoding="utf-8").read()
for upstream, ours in (("__isalphar", "kLidAlphaRanges"), ("__isalphas", "kLidAlphaSingles"),
                       ("__tolowerr", "kLidLowerRanges"), ("__tolowerp", "kLidLowerPairs"),
                       ("__tolowers", "kLidLowerSingles")):
    m = re.search(r"static Rune %s\[\] = \{(.*?)\};" % upstream, src, re.S)
    values = [int(v, 0) for v in re.findall(r"0x[0-9a-fA-F]+|\d+", m.group(1))]
    h.append("// %s of runetypebody.h\n" % upstream)
    h.append(emit("int32_t", ours, values, 12))

text = "".join(h)
if "--check" in sys.argv:
    old = open(OUT, encoding="utf-8").read() if os.path.exists(OUT) else None
    if old != text:
        sys.exit("out of date: " + OUT)
elif not os.path.exists(OUT) or open(OUT, encoding="utf-8").read() != text:
    open(OUT, "w", encoding="utf-8").write(text)
print("langid model: %d labels, %d bytes of tables, header %d bytes"
      % (len(labels), sum(t[3] for t in tensors.values()), len(text)))
