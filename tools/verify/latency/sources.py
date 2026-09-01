#!/usr/bin/env python3
"""Print the native source list, read from CMakeLists.txt rather than globbed.

    sources.py app/src/main/cpp

Both CLD2's internal/ and CLD3's src/ carry files the app deliberately does not
build: offline tools that have their own main(), alternate data tables that
redefine the same symbols, debug_empty.cc alongside debug.cc. A glob picks all
of those up and the link dies on duplicate definitions -- which is exactly what
happened the first time this harness was written.

Reading add_library()'s real list instead means the harness compiles what CI
compiles, and cannot drift out of step with it.
"""
import os
import re
import sys


def sources(cpp_dir):
    # Absolute, so that a ${CLD2_DIR} expansion is already absolute and the
    # join below cannot glue the tree onto itself.
    cpp_dir = os.path.abspath(cpp_dir)
    text = open(os.path.join(cpp_dir, 'CMakeLists.txt'), encoding='utf-8').read()
    body = text[text.index('add_library('):]
    body = body[:body.index(')')]
    variables = {
        'CMAKE_CURRENT_SOURCE_DIR': cpp_dir,
        'CLD2_DIR': os.path.join(cpp_dir, 'cld2_src', 'internal'),
        'CLD3_DIR': os.path.join(cpp_dir, 'cld3_src', 'src'),
        'CLD3_GEN': os.path.join(cpp_dir, 'cld3_gen'),
    }
    out = []
    for token in body.split():
        if token in ('add_library(', 'easyvoice_core', 'SHARED'):
            continue
        # CMake builds this one with file(GLOB ...) and then filters out tests.
        if token == '${CLD3_SCRIPT_SPAN}':
            span = os.path.join(cpp_dir, 'cld3_src', 'src', 'script_span')
            out += sorted(os.path.join(span, name) for name in os.listdir(span)
                          if name.endswith('.cc') and 'test' not in name)
            continue
        path = re.sub(r'\$\{(\w+)\}',
                      lambda m: variables.get(m.group(1), m.group(0)), token)
        if not os.path.isabs(path):
            path = os.path.join(cpp_dir, path)
        if path.endswith(('.cc', '.cpp')):
            out.append(path)
    return out


def main():
    if len(sys.argv) != 2:
        print(__doc__.strip())
        return 2
    for path in sources(sys.argv[1]):
        print(path)
    return 0


if __name__ == '__main__':
    sys.exit(main())
