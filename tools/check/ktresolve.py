"""Check every call to OUR OWN top-level functions against its signature.

A regex cannot resolve arbitrary Kotlin identifiers -- implicit receivers inside
apply{}/run{}, inherited Activity members and named arguments all look like bare
names, so a general resolver is 90% false positives and gets ignored. This does
one narrow job soundly instead, and it is exactly the bug that once reached CI:

    buildModesTabView(container.context, prefs) { testTts }

Adding a trailing `settingsOnlyForMode: String?` parameter silently rebound that
trailing lambda to the NEW last parameter instead of testTtsProvider. Nothing
short of a type-checker saw it. Here, arity and named-argument checking do.

Reports, per call site:
  * a named argument the function does not declare
  * more arguments than the function accepts
  * a required (no-default) parameter left unsupplied
"""
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import ktscan

import evpaths
SRC = evpaths.kotlin_dir()
FUN_RE = re.compile(r'^(?:@\w+\s+)*(?:(?:private|internal|public)\s+)?fun\s+(\w+)\s*\(', re.M)


def split_top(text, sep=',', generics=False):
    """Split on separators that are not nested in (), <>, [] or {}.

    The '>' of a function type's '->' is NOT a closing bracket; counting it as
    one drives the depth negative and mis-splits every later parameter. That is
    a false-positive factory -- '(Int) -> Unit' looked like an unclosed group.
    """
    out, depth, buf, prev = [], 0, '', ''
    for ch in text:
        if ch in '([{' or (generics and ch == '<'):
            depth += 1
        elif ch in ')]}':
            depth -= 1
        elif generics and ch == '>' and prev != '-':
            depth -= 1
        if ch == sep and depth == 0:
            out.append(buf); buf = ''
        else:
            buf += ch
        prev = ch
    if buf.strip():
        out.append(buf)
    return [s.strip() for s in out if s.strip()]


def balanced(text, start):
    """start is the index of '('; return (inner, index after the ')')."""
    depth = 0
    for i in range(start, len(text)):
        if text[i] in '([{':
            depth += 1
        elif text[i] in ')]}':
            depth -= 1
            if depth == 0:
                return text[start + 1:i], i + 1
    return None, len(text)


def parse_params(sig):
    """[(name, has_default)] for a parameter list, ignoring the receiver."""
    params = []
    for part in split_top(sig, generics=True):
        m = re.match(r'(?:@\w+\s+)*(?:vararg\s+)?(\w+)\s*:', part)
        if not m:
            continue
        params.append((m.group(1), '=' in part.split(':', 1)[1]))
    return params


def main():
    files = [f for f in sorted(os.listdir(SRC)) if f.endswith('.kt')]
    codes = {f: ktscan.code_only(open(os.path.join(SRC, f), encoding='utf-8').read())
             for f in files}

    # 1. collect our own top-level function signatures (module-wide)
    sigs = {}
    for f in files:
        code = codes[f]
        for m in FUN_RE.finditer(code):
            if code[:m.start()].count('{') - code[:m.start()].count('}') > 0:
                continue                      # nested in a class/object -- skip
            inner, _ = balanced(code, m.end() - 1)
            if inner is None:
                continue
            sigs.setdefault(m.group(1), parse_params(inner))

    # 2. check every call site of those functions
    problems = []
    for f in files:
        code = codes[f]
        for name, params in sigs.items():
            for m in re.finditer(r'(?<![\w.])' + re.escape(name) + r'\s*\(', code):
                if re.match(r'\s*fun\b', code[max(0, m.start() - 40):m.start()][::-1][::-1][-40:] or ''):
                    pass
                before = code[:m.start()].rstrip()
                if before.endswith('fun'):
                    continue                  # this is the declaration itself
                inner, after = balanced(code, m.end() - 1)
                if inner is None:
                    continue
                args = split_top(inner)
                trailing = 1 if re.match(r'\s*\{', code[after:]) else 0
                names = [p[0] for p in params]
                named = [a.split('=')[0].strip() for a in args if re.match(r'^\w+\s*=[^=]', a)]
                positional = len(args) - len(named)
                line = code[:m.start()].count('\n') + 1
                for n in named:
                    if n not in names:
                        problems.append((f, line, name, 'no parameter named %r' % n))
                if positional + len(named) + trailing > len(params):
                    problems.append((f, line, name, 'passes %d args, takes %d'
                                     % (positional + len(named) + trailing, len(params))))
                supplied = set(names[:positional]) | set(named)
                if trailing and len(params):
                    supplied.add(names[-1])
                for pname, has_default in params:
                    if pname not in supplied and not has_default:
                        problems.append((f, line, name, 'required parameter %r not supplied' % pname))
    print('signature mismatches at call sites:', len(problems))
    for p in problems:
        print('   %-26s %4d  %-24s %s' % p)
    return 1 if problems else 0


if __name__ == '__main__':
    sys.exit(main())
