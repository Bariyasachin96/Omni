#!/usr/bin/env python3
"""Every language code the native core can answer for a span or a window, read
out of its sources (2026-10-21, when the model ML Kit runs replaced CLD2):

    the model's labels (langid_model.h kLidLabels) less "unknown" and the
      romanised xx-Latn ones, which the core never answers;
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
    out = set(re.findall(r'"([^"]+)"', block(os.path.join(CPP, 'langid_model.h'), 'kLidLabels[')))
    out = {c for c in out if c != 'unknown' and not c.endswith('-Latn')}
    core = os.path.join(CPP, 'tts_engine_core.cpp')
    for start in ('SCRIPT_FIXED_LANG[19] = {', 'kExtraSlotLangs[7] = {', 'kScriptLangPairs[48] = {'):
        out |= set(re.findall(r'"([^"]+)"', block(core, start)))
    out |= set(re.findall(r'"([^"]+)"', block(os.path.join(CPP, 'unicode_tables.h'), 'kUniFixedSlotLang[')))
    out.add('un')
    return sorted(out)


if __name__ == '__main__':
    sys.stdout.write('\n'.join(codes()) + '\n')
