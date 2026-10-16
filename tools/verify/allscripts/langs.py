#!/usr/bin/env python3
"""Each Unicode script's language and whether it is in use today, from the CLDR
files the generator reads (tools/unicode/cldr) -- read here on their own.

    langs.py <ucd dir> <cldr dir>  ->  script<TAB>language<TAB>usage
"""
import os, re, sys
ucd, cldr = sys.argv[1:3]
code = {}
for line in open(os.path.join(ucd, "PropertyValueAliases.txt"), encoding="utf-8"):
    f = [x.strip() for x in line.split("#")[0].split(";")]
    if f[0] == "sc" and len(f) > 2: code[f[2]] = f[1]
usage = {}
for line in open(os.path.join(cldr, "scriptMetadata.txt"), encoding="utf-8"):
    f = [x.strip() for x in line.split("#")[0].split(";")]
    if len(f) > 5: usage[f[0]] = f[5]
likely = dict(re.findall(r'from="und_([A-Z][a-z]{3})" to="([a-z]{2,3})_',
                         open(os.path.join(cldr, "likelySubtags-scripts.txt"), encoding="utf-8").read()))
for name in sorted(code):
    c = code[name]
    print("%s\t%s\t%s" % (name, likely.get(c, ""), usage.get(c, "")))
