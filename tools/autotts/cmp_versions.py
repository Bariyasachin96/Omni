"""Structural comparison of two AutoTTS decompiles.

Local names, block labels and the one-letter static shift all change between
builds without anything actually changing, so a raw diff is useless. This
strips every identifier down to a placeholder, keeps only keywords, operators,
numbers and string literals, and reports what is left.
"""
import os, re, sys, difflib

KW = set('if else for while switch case break continue return synchronized try catch finally new int long float double boolean char void this null true false instanceof do throw static final public private protected class abstract extends implements'.split())

def skel(path):
    s = open(path, encoding='utf-8', errors='replace').read()
    s = re.sub(r'/\*(?:.|\n)*?\*/', '', s)
    s = re.sub(r'//[^\n]*', '', s)
    lits = sorted(re.findall(r'"(?:[^"\\]|\\.)*"', s))
    s = re.sub(r'"(?:[^"\\]|\\.)*"', 'S', s)
    out = []
    for line in s.split('\n'):
        line = line.strip()
        if not line:
            continue
        line = re.sub(r'\b[A-Za-z_$][A-Za-z0-9_.$]*\b',
                      lambda m: m.group(0) if m.group(0) in KW else 'X', line)
        line = re.sub(r'\b\d+\b', 'N', line)
        out.append(re.sub(r'\s+', '', line))
    return out, lits

def compare(a, b, label):
    sa, la = skel(a)
    sb, lb = skel(b)
    same_struct = (sa == sb)
    lit_add = [x for x in lb if lb.count(x) > la.count(x)]
    lit_del = [x for x in la if la.count(x) > lb.count(x)]
    lit_add = sorted(set(lit_add)); lit_del = sorted(set(lit_del))
    if same_struct and not lit_add and not lit_del:
        return None
    d = list(difflib.unified_diff(sa, sb, lineterm='', n=0))
    hunks = sum(1 for l in d if l.startswith('@@'))
    return (label, len(sa), len(sb), hunks, lit_add, lit_del)

OLD, NEW = sys.argv[1], sys.argv[2]
rows = []
for root, _, files in os.walk(OLD):
    for fn in files:
        if not fn.endswith('.java'):
            continue
        rel = os.path.relpath(os.path.join(root, fn), OLD)
        nb = os.path.join(NEW, rel)
        if not os.path.exists(nb):
            rows.append((rel, 'REMOVED', '', 0, [], [])); continue
        r = compare(os.path.join(root, fn), nb, rel)
        if r: rows.append(r)
for root, _, files in os.walk(NEW):
    for fn in files:
        if fn.endswith('.java'):
            rel = os.path.relpath(os.path.join(root, fn), NEW)
            if not os.path.exists(os.path.join(OLD, rel)):
                rows.append((rel, '', 'ADDED', 0, [], []))
print('files with a REAL structural or literal difference:', len(rows))
for rel, na, nb, hunks, add, dele in sorted(rows):
    print(f'\n### {rel}   lines {na} -> {nb}   hunks {hunks}')
    for x in add[:8]: print('    + literal', x[:90])
    for x in dele[:8]: print('    - literal', x[:90])
