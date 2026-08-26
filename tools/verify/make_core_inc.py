#!/usr/bin/env python3
"""Slice the native core into something a desktop harness can compile.

app/src/main/cpp/tts_engine_core.cpp is an Android JNI translation unit: it
includes <jni.h>, CLD2 and CLD3, and half of it is Java_… entry points. A
verification harness wants none of that -- it wants the pure functions, compiled
by an ordinary g++, so their output can be diffed against AutoTTS's own Java.

So this takes the file, cuts it at a named function, drops the JNI and detector
plumbing, and appends the few stubs the remainder needs. The result is a .inc the
harness #includes.

WHAT IS DROPPED, and why it is safe for the two proofs that use this:
  * #include <jni.h> and the CLD2/CLD3 headers -- nothing before the cut point
    calls into either library. The segmenter never detects (buildMixChunks
    segments and merges; detection happens back in Kotlin), and the script
    family is table lookups.
  * JNI_OnLoad and every extern "C" JNIEXPORT function -- entry points only.
  * the forward declaration of cld3DetectRaw, which would need CLD3's headers.

WHAT IS ADDED:
  * a jchar typedef, because a couple of UTF-16 helpers use it;
  * currentLanguageHints(), which really lives in the JNI half. The stub also
    invalidates the smart-number keyword cache, because AutoTTS caches its
    keyword set for the life of the process (c3.d0.j is assigned in exactly two
    places and nothing clears it) and the Java harness resets it per case -- the
    two must reset together or they are not comparing the same thing.

Usage:
    make_core_inc.py --until buildMixChunks          --out core.inc
    make_core_inc.py --until scriptLangForCpFiltered --out core2.inc

--until names the LAST function to keep. Everything from the start of the file
through the end of that function is kept.
"""
import argparse
import os
import sys

DEFAULT_SOURCE = 'app/src/main/cpp/tts_engine_core.cpp'

DROP_INCLUDES = (
    '#include <jni.h>',
    '#include "compact_lang_det.h"',
    '#include "encodings.h"',
    '#include "compact_lang_det_impl.h"',
    '#include "nnet_language_identifier.h"',
)

HINTS_STUB = """
// ---- harness-only stubs; see tools/verify/make_core_inc.py ----------------
static std::string g_testHints;
static std::string currentLanguageHints(){ return g_testHints; }
"""

CACHE_STUB = """static void setLanguageHintsForTest(const std::string& s){
    g_testHints = s;
    // AutoTTS caches d0.a()'s keyword set in d0.j for the life of the process
    // and never rebuilds it; the Java harness resets d0.j per case, so this
    // cache has to reset with it or the two are not comparing the same thing.
    smartNumberCacheValid = false;
}
"""


def cut_point(lines, name):
    """Index one past the closing brace of the definition of `name`."""
    start = None
    for index, line in enumerate(lines):
        stripped = line.lstrip()
        if name + '(' not in line:
            continue
        if not (stripped.startswith('static ') or stripped.startswith('inline ')
                or stripped.startswith('std::') or stripped.startswith('auto ')):
            continue
        if line.rstrip().endswith(';'):
            continue          # a forward declaration, not the definition
        start = index
        break
    if start is None:
        raise SystemExit('make_core_inc: no definition of %s found' % name)
    depth = 0
    seen_brace = False
    for index in range(start, len(lines)):
        depth += lines[index].count('{') - lines[index].count('}')
        if '{' in lines[index]:
            seen_brace = True
        if seen_brace and depth <= 0:
            return index + 1
    raise SystemExit('make_core_inc: %s never closes' % name)


def strip(lines):
    out = []
    index = 0
    while index < len(lines):
        line = lines[index]
        if line.strip() in DROP_INCLUDES:
            index += 1
            continue
        if line.startswith('JNIEXPORT jint JNI_OnLoad') or line.startswith('extern "C" JNIEXPORT'):
            depth = 0
            seen_brace = False
            while index < len(lines):
                depth += lines[index].count('{') - lines[index].count('}')
                if '{' in lines[index]:
                    seen_brace = True
                index += 1
                if seen_brace and depth <= 0:
                    break
            continue
        if line.startswith('static std::string cld3DetectRaw(') and line.rstrip().endswith(';'):
            index += 1
            continue
        out.append(line)
        index += 1
    return out


def main():
    parser = argparse.ArgumentParser(description=__doc__,
                                     formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument('--source', default=DEFAULT_SOURCE)
    parser.add_argument('--until', required=True,
                        help='name of the last function to keep')
    parser.add_argument('--out', required=True)
    args = parser.parse_args()

    if not os.path.exists(args.source):
        raise SystemExit('make_core_inc: %s not found -- run from the repository '
                         'root' % args.source)

    lines = open(args.source, encoding='utf-8').read().split('\n')
    kept = strip(lines[:cut_point(lines, args.until)])
    text = '\n'.join(kept)
    # Only stub what the slice actually needs. A short slice -- the normaliser,
    # say -- contains neither the hint accessor nor the keyword cache.
    stubs = ''
    if 'currentLanguageHints' in text:
        stubs += HINTS_STUB
        if 'smartNumberCacheValid' in text:
            stubs += CACHE_STUB
    body = 'typedef unsigned short jchar;\n' + text + stubs
    with open(args.out, 'w', encoding='utf-8') as out:
        out.write(body)
    print('%s: %d lines, cut after %s' % (args.out, body.count('\n'), args.until))


if __name__ == '__main__':
    sys.exit(main())
