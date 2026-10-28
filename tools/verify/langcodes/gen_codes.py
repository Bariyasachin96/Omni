#!/usr/bin/env python3
"""Every language code the native core can answer for a span or a window, read
out of its sources (2026-10-28, when Google's ML Kit library became the
language identifier and every model of ours left the core):

    the languages ML Kit names that the core takes (languageSlot's lists, which
      are ML Kit's own support table by script, plus zh, ja, bn, as and yi --
      its romanised xx-Latn tags never reach an answer);
    the script-decided languages (SCRIPT_FIXED_LANG, kExtraSlotLangs and
      unicode_tables.h kUniFixedSlotLang);
    the per-script fallbacks (kScriptLangPairs);
    "un".

An enabled language the core answers is one of the codes the app pushed, so
it is in IsoCodes already. run.sh fails if codes.txt has fallen behind this.
"""
import os
import re
import sys

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..', '..'))
CPP = os.path.join(ROOT, 'app', 'src', 'main', 'cpp')


def block(path, start):
    s = open(path, encoding='utf-8').read()
    i = s.index(start)
    return s[i:s.index('};', i)]


def codes():
    core = os.path.join(CPP, 'tts_engine_core.cpp')
    out = set()
    for start in ('kLatin[] = {', 'kCyrillic[] = {', 'kArabic[] = {', 'kDevanagari[] = {',
                  'SCRIPT_FIXED_LANG[19] = {', 'kExtraSlotLangs[7] = {', 'kScriptLangPairs[48] = {'):
        out |= set(re.findall(r'"([^"]+)"', block(core, start)))
    out |= {'zh', 'ja', 'bn', 'as', 'yi'}
    out |= set(re.findall(r'"([^"]+)"', block(os.path.join(CPP, 'unicode_tables.h'), 'kUniFixedSlotLang[')))
    out.add('un')
    return sorted(out)


if __name__ == '__main__':
    sys.stdout.write('\n'.join(codes()) + '\n')
