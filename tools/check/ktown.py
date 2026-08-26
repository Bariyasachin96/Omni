"""Flag kotlinc 'unresolved reference: X' where X is declared in OUR OWN module.

Google Maven is blocked by the proxy, so every androidx / Compose / material
symbol is unresolved locally -- ~1000 lines of expected noise, and a REAL bug
reads exactly the same:

    VoiceRows.kt: unresolved reference: LangEntry     <- real (LangEntry is ours)
    VoiceScreen.kt: unresolved reference: Modifier    <- noise (androidx)

A name we declare ourselves can NEVER be legitimately unresolved, so those lines
are always actionable however large the surrounding noise is.

usage: ktown.py <tree-dir> <kotlinc-errors-file>
"""
import os
import re
import sys

DECL_RES = [
    re.compile(r'^(?:@\w+\s+)*(?:\w+\s+)*(?:class|object|interface)\s+([A-Za-z_]\w*)', re.M),
    re.compile(r'^typealias\s+([A-Za-z_]\w*)', re.M),
    re.compile(r'^(?:@\w+\s+)*(?:\w+\s+)*fun\s+(?:<[^>]*>\s*)?([A-Za-z_]\w*)\s*\(', re.M),
    re.compile(r'^(?:@\w+\s+)*(?:\w+\s+)*va[lr]\s+([A-Za-z_]\w*)\b', re.M),
]


def module_declarations(src_dir):
    names = set()
    for entry in sorted(os.listdir(src_dir)):
        if not entry.endswith('.kt'):
            continue
        text = open(os.path.join(src_dir, entry), encoding='utf-8').read()
        for pattern in DECL_RES:
            names |= set(pattern.findall(text))
    return names


def main():
    tree, errfile = sys.argv[1], sys.argv[2]
    src = os.path.join(tree, 'app/src/main/java/com/tts/easyvoice')
    ours = module_declarations(src)
    hits = []
    for line in open(errfile, encoding='utf-8'):
        match = re.search(r'unresolved reference: (\w+)', line)
        if match and match.group(1) in ours:
            hits.append((match.group(1), line.strip().replace('file://', '')))
    print('unresolved references to OUR OWN declarations:', len(hits))
    for name, line in hits:
        print('   %-22s %s' % (name, line[line.rfind('/com/tts/easyvoice/') + 19:]))
    return 1 if hits else 0


if __name__ == '__main__':
    sys.exit(main())
