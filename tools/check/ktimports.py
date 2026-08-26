import sys, os, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import evpaths
SRC = evpaths.kotlin_dir()

BUILTINS = set('''
String Int Long Float Double Boolean Char Byte Short Unit Any Nothing Array List MutableList
Set MutableSet Map MutableMap Pair Triple Comparable Comparator Iterable Iterator Sequence
Exception Throwable RuntimeException IllegalStateException IllegalArgumentException
InterruptedException IndexOutOfBoundsException NumberFormatException UnsupportedOperationException
ArrayList HashMap HashSet LinkedHashMap LinkedHashSet Object Runnable Thread Regex CharSequence
Number Enum Lazy Result System Character Charsets Math StringBuilder ByteArray IntArray
FloatArray ShortArray LongArray DoubleArray BooleanArray CharArray Integer Locale Collections
JvmStatic JvmField Volatile Synchronized Suppress Deprecated SuppressLint JvmOverloads Throws
OptIn Composable
'''.split())

DECL_RE = re.compile(r'\b(?:class|object|interface|enum\s+class|annotation\s+class)\s+([A-Z]\w*)')
CONST_RE = re.compile(r'\b(?:val|var)\s+([A-Z]\w*)\b')
FUN_RE = re.compile(r'\bfun\s+(?:<[^>]*>\s*)?([A-Z]\w*)\s*\(')

# Receiver-scope members: called on a scope, never imported. Verified against the
# material3 API file for the version we build against, not assumed --
#   1.4.0 : class ExposedDropdownMenuBoxScope { ... public final void
#           ExposedDropdownMenu(...) }        -> MEMBER, must NOT be imported
#   main  : class ExposedDropdownMenuKt { public static void
#           ExposedDropdownMenu(scope, ...) } -> extension, needs an import
# Revisit this line whenever the Compose BOM moves.
COMPOSE_SCOPE_MEMBERS = set("""
ExposedDropdownMenu
""".split())

ANDROID_INHERITED = set('''
AUDIO_SERVICE START_STICKY START_NOT_STICKY START_REDELIVER_INTENT NOTIFICATION_SERVICE
BIND_AUTO_CREATE MODE_PRIVATE RESULT_OK RESULT_CANCELED CONTEXT_IGNORE_SECURITY
TELEPHONY_SERVICE POWER_SERVICE ACTIVITY_SERVICE
'''.split())

# Names that are RECEIVER-SCOPE members, never importable on their own. Importing
# one compiles to an unresolved reference, which no missing-import check could
# ever see -- so flag the import itself.
SCOPE_ONLY_IMPORTS = set("""
weight align alignByBaseline matchParentSize menuAnchor animateItemPlacement
""".split())


def code_only(text):
    """Strip comments and string literals so only real code is scanned."""
    text = re.sub(r'"""(?:.|\n)*?"""', '""', text)
    text = re.sub(r'(?<!\\)"(?:[^"\\\n]|\\.)*"', '""', text)
    text = re.sub(r'//[^\n]*', '', text)
    text = re.sub(r'/\*(?:.|\n)*?\*/', '', text)
    return text


def bad_scope_imports(text):
    out = []
    for m in re.finditer(r'^import\s+([\w.]+)\s*$', text, re.M):
        leaf = m.group(1).rsplit('.', 1)[-1]
        if leaf in SCOPE_ONLY_IMPORTS:
            out.append((m.group(1), text[:m.start()].count(chr(10)) + 1))
    return out


def declared_types(text):
    return set(DECL_RE.findall(text))


def main():
    files = [f for f in sorted(os.listdir(SRC)) if f.endswith('.kt')]
    module_types = set()
    sources = {}
    for f in files:
        text = open(os.path.join(SRC, f), encoding='utf-8').read()
        sources[f] = text
        module_types |= declared_types(text)
        module_types |= set(re.findall(r'^typealias\s+([A-Z]\w*)', text, re.M))
        module_types |= set(FUN_RE.findall(text))

    problems = []
    for f in files:
        for imp, ln in bad_scope_imports(sources[f]):
            problems.append((f, ln, imp, 'SCOPE-ONLY import: ' + imp))
    for f in files:
        code = code_only(sources[f])
        imported = {m.rsplit('.', 1)[-1]
                    for m in re.findall(r'^import\s+([\w.]+)', code, re.M)}
        local = declared_types(code) | set(CONST_RE.findall(code)) | set(FUN_RE.findall(code))
        lines = sources[f].split('\n')
        for m in re.finditer(r'(?<![\w.$"@])([A-Z]\w*)\b', code):
            name = m.group(1)
            if (name in imported or name in local or name in module_types
                    or name in BUILTINS or name in ANDROID_INHERITED
                    or name in COMPOSE_SCOPE_MEMBERS or name == 'R'):
                continue
            line = code[:m.start()].count('\n') + 1
            problems.append((f, line, name, lines[line - 1].strip()[:90]))

    seen, uniq = set(), []
    for p in problems:
        if (p[0], p[2]) in seen:
            continue
        seen.add((p[0], p[2]))
        uniq.append(p)
    print('unimported capitalised names:', len(uniq))
    for x in uniq:
        print('   %-24s %4d  %-26s %s' % x)
    return 1 if uniq else 0


if __name__ == '__main__':
    sys.exit(main())
