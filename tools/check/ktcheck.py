"""Structural sanity check on every generated Kotlin file.

Cheap checks that run in a second and catch the damage a bad string-splice does
to build.yml long before kotlinc (2 minutes) or CI (4 minutes) would.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import ktscan

import evpaths
SRC = evpaths.kotlin_dir()
PAIRS = {'{': '}', '(': ')', '[': ']'}
CLOSERS = {v: k for k, v in PAIRS.items()}


def check(name, raw):
    problems = []
    code = ktscan.code_only(raw)

    # 1. an odd number of quotes means a literal was cut in half by an edit
    if raw.count('"') % 2 or raw.count('"""') % 2:
        problems.append('unbalanced quotes')

    # 2. brackets must balance, and must close in the right order
    stack = []
    for line_no, line in enumerate(code.split('\n'), 1):
        for ch in line:
            if ch in PAIRS:
                stack.append((ch, line_no))
            elif ch in CLOSERS:
                if not stack:
                    problems.append('line %d: stray %r' % (line_no, ch))
                elif stack[-1][0] != CLOSERS[ch]:
                    problems.append('line %d: %r closes %r opened on line %d'
                                    % (line_no, ch, stack[-1][0], stack[-1][1]))
                    stack.pop()
                else:
                    stack.pop()
    for ch, line_no in stack:
        problems.append('line %d: %r never closed' % (line_no, ch))

    # 3. the package line must survive every splice
    if not raw.startswith('package com.tts.easyvoice\n'):
        problems.append('missing or misplaced package declaration')

    # 4. an empty or near-empty file means a block was swallowed
    if len(raw.strip()) < 40:
        problems.append('file is suspiciously short (%d bytes)' % len(raw.strip()))
    return problems


def main():
    total = 0
    for name in sorted(os.listdir(SRC)):
        if not name.endswith('.kt'):
            continue
        raw = open(os.path.join(SRC, name), encoding='utf-8').read()
        for p in check(name, raw):
            print('   %-26s %s' % (name, p))
            total += 1
    print('PROBLEMS:', total)
    return 1 if total else 0


if __name__ == '__main__':
    sys.exit(main())
