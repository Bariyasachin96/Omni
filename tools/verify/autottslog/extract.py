#!/usr/bin/env python3
"""Turn an AutoTTS log into test cases: every utterance's text, and the chunks
AutoTTS actually spoke for it (language + text), in order.

    U<TAB>text
    E<TAB>lang<TAB>chunk text        one per `speak N:` line AutoTTS logged

A chunk list may be SHORTER than the full split when the screen reader cut the
utterance off, so the runner compares only the prefix AutoTTS reached."""
import re, sys
line_re = re.compile(r'^\d{4}-\d\d-\d\d \d\d:\d\d:\d\d\.\d+ \[.\] AutoTTS: ?(.*)$')
def esc(s): return s.replace('\\', '\\\\').replace('\t', '\\t').replace('\n', '\\n')
cur, lang, out, seen = None, None, [], set()
def flush():
    if cur is None: return
    text, chunks = cur
    if not chunks: return
    key = (text, tuple(chunks))
    if key in seen: return
    seen.add(key)
    out.append('U\t' + esc(text))
    for l, t in chunks: out.append('E\t' + l + '\t' + esc(t))
for raw in open(sys.argv[1], encoding='utf-8', errors='replace'):
    raw = raw.rstrip('\n')
    m = line_re.match(raw)
    if not m: continue
    msg = m.group(1)
    if msg.startswith('Speak: '):
        flush()
        body = msg[len('Speak: '):]
        body = re.sub(r' id: \S+$', '', body)
        cur, lang = (body, []), None
    elif cur is not None and msg.startswith('loadLanguage '):
        lang = msg.split()[1] if len(msg.split()) > 1 else ''
    elif cur is not None and re.match(r'^speak \d+: ', msg):
        cur[1].append((lang or '?', re.sub(r'^speak \d+: ', '', msg)))
    elif msg.startswith('onSynthesizeText ended'):
        pass
flush()
sys.stdout.write('\n'.join(out) + '\n')
