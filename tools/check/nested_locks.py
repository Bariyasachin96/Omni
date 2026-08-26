#!/usr/bin/env python3
"""INVARIANTS #3: never hold LangStore.languages while taking another lock.

A grep cannot answer this. The correct shape and the deadlocking shape look
almost identical a few lines apart -- the difference is whether the second lock
is INSIDE the first block or after it:

    // correct: read the guard under the list monitor, reload outside it
    val missing = synchronized(LangStore.languages) {
        !languagesLoaded || LangStore.languages.isEmpty()
    }
    if (!missing) return
    synchronized(this) { LangStore.loadLanguages(applicationContext) }

    // deadlock: languages -> this, while onLoadLanguage takes this -> languages
    synchronized(LangStore.languages) {
        synchronized(this) { LangStore.loadLanguages(applicationContext) }
    }

onSynthesizeText runs on the synthesis thread and reaches the first;
onLoadLanguage is a TextToSpeechService override that Android also calls on
binder threads, and it takes the service monitor and then `languages` inside
localeFor / engineFor / variantFor. Two threads taking the same pair in opposite
orders is a textbook ABBA deadlock, and when it lands speech simply stops with
nothing in the log. It has happened once.

So this tracks brace depth from the opening of each
`synchronized(LangStore.languages) {` to its matching close, and reports any
other `synchronized(...)` inside it. Braces in comments and string literals are
ignored, or a `"{"` in a message would throw the count off.
"""
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import evpaths

OPEN = re.compile(r'synchronized\s*\(\s*LangStore\.languages\s*\)\s*\{')
ANY = re.compile(r'synchronized\s*\(([^)]*)\)')


def strip_noise(line):
    """Blank out // comments and "..." literals, so their braces do not count."""
    out, index, in_string = [], 0, False
    while index < len(line):
        char = line[index]
        if in_string:
            if char == '\\':
                index += 2
                continue
            if char == '"':
                in_string = False
            index += 1
            continue
        if char == '"':
            in_string = True
            index += 1
            continue
        if char == '/' and index + 1 < len(line) and line[index + 1] == '/':
            break
        out.append(char)
        index += 1
    return ''.join(out)


def check(path):
    lines = open(path, encoding='utf-8').read().split('\n')
    clean = [strip_noise(line) for line in lines]
    problems = []
    for start, line in enumerate(clean):
        if not OPEN.search(line):
            continue
        depth = 0
        opened = False
        for at in range(start, len(clean)):
            depth += clean[at].count('{') - clean[at].count('}')
            if '{' in clean[at]:
                opened = True
            if at > start:
                for match in ANY.finditer(clean[at]):
                    if 'LangStore.languages' in match.group(1):
                        continue
                    problems.append((at + 1, lines[at].strip(), start + 1))
            if opened and depth <= 0:
                break
    return problems


def main():
    root = evpaths.kotlin_dir()
    total = 0
    for name in sorted(os.listdir(root)):
        if not name.endswith('.kt'):
            continue
        for line_no, text, owner in check(os.path.join(root, name)):
            print('   %s:%d  inside the synchronized(LangStore.languages) opened '
                  'at line %d:\n      %s' % (name, line_no, owner, text))
            total += 1
    if total:
        print('nested locks inside the language-list monitor: %d' % total)
    return 1 if total else 0


if __name__ == '__main__':
    sys.exit(main())
