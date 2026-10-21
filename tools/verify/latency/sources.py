#!/usr/bin/env python3
"""Print the native source list, read from CMakeLists.txt rather than globbed.

    sources.py app/src/main/cpp            the .cc/.cpp files, one per line
    sources.py --includes <dir>            the -I flags those files need

Since 2026-10-21 the app's own list is tts_engine_core.cpp alone (CLD2 left the
app). A commit from before that also lists CLD2's files through ${CLD2_DIR};
for such a commit -- the "old" side of corebytes, langsuite and friends -- the
variable points at the clone tools/verify/clone-cld2.sh makes in
tools/.cache/cld2, and --includes names its public/ and internal/ headers.

Reading add_library()'s real list instead of a glob matters for those old
commits: CLD2's internal/ carries offline tools with their own main(),
alternate data tables that redefine the same symbols and debug_empty.cc beside
debug.cc, and a glob picked them all up the first time this was written.
"""
import os
import re
import sys

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..', '..'))
CLD2 = os.path.join(ROOT, 'tools', '.cache', 'cld2')


def sources(cpp_dir):
    cpp_dir = os.path.abspath(cpp_dir)
    text = open(os.path.join(cpp_dir, 'CMakeLists.txt'), encoding='utf-8').read()
    body = text[text.index('add_library('):]
    body = body[:body.index(')')]
    variables = {
        'CMAKE_CURRENT_SOURCE_DIR': cpp_dir,
        'CLD2_DIR': os.path.join(CLD2, 'internal'),
    }
    out = []
    for token in body.split():
        if token in ('add_library(', 'easyvoice', 'easyvoice_core', 'SHARED'):
            continue
        path = re.sub(r'\$\{(\w+)\}',
                      lambda m: variables.get(m.group(1), m.group(0)), token)
        if not os.path.isabs(path):
            path = os.path.join(cpp_dir, path)
        if path.endswith(('.cc', '.cpp')):
            out.append(path)
    return out


def uses_cld2(cpp_dir):
    return any(p.startswith(CLD2 + os.sep) for p in sources(cpp_dir))


def main():
    args = sys.argv[1:]
    includes = bool(args) and args[0] == '--includes'
    if includes:
        args = args[1:]
    if len(args) != 1:
        print(__doc__.strip())
        return 2
    if uses_cld2(args[0]) and not os.path.isdir(os.path.join(CLD2, 'internal')):
        sys.stderr.write('this commit links CLD2: run tools/verify/clone-cld2.sh first\n')
        return 2
    if includes:
        if uses_cld2(args[0]):
            print('-I%s -I%s' % (os.path.join(CLD2, 'public'), os.path.join(CLD2, 'internal')))
        return 0
    for path in sources(args[0]):
        print(path)
    return 0


if __name__ == '__main__':
    sys.exit(main())
