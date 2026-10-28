#!/usr/bin/env python3
"""The letters each language writes, from CLDR (owner, 2026-10-28: "cldr ka
properli jo data hai Unicode ka ... sab Kuchh complete karo").

Writes tools/unicode/cldr/exemplars.txt: the <exemplarCharacters> of every
language gen_tables.py's SHARED_LANGS names (the scripts two or more languages
share), main, auxiliary and index, as CLDR writes them (XML entities decoded).
gen_tables.py reads that file; this script is only for refreshing it.

    D=$(mktemp -d); for l in <SHARED_LANGS codes>; do
      curl -sSfo $D/$l.xml https://raw.githubusercontent.com/unicode-org/cldr/release-48-2/common/main/$l.xml
    done; extract_exemplars.py $D
"""
import html, os, re, sys

HERE = os.path.dirname(os.path.abspath(__file__))
TAG = "release-48-2"


def main():
    src = sys.argv[1]
    out = ["# CLDR %s, common/main/<lang>.xml: <exemplarCharacters> main, auxiliary and index of" % TAG,
           "# every language gen_tables.py's SHARED_LANGS names, as CLDR writes them (XML entities",
           "# decoded). Written by extract_exemplars.py; gen_tables.py reads it.",
           "# lang<TAB>type<TAB>UnicodeSet"]
    for name in sorted(os.listdir(src)):
        if not name.endswith(".xml"): continue
        lang = name[:-4]
        text = open(os.path.join(src, name), encoding="utf-8").read()
        for m in re.finditer(r'<exemplarCharacters(?: type="([^"]+)")?[^>]*>([^<]*)</exemplarCharacters>', text):
            kind = m.group(1) or "main"
            if kind not in ("main", "auxiliary", "index") or "↑↑↑" in m.group(2): continue
            out.append("%s\t%s\t%s" % (lang, kind, html.unescape(m.group(2)).replace("\t", " ")))
    open(os.path.join(HERE, "exemplars.txt"), "w", encoding="utf-8").write("\n".join(out) + "\n")


if __name__ == "__main__":
    main()
