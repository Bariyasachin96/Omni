#!/usr/bin/env bash
# Test text for the suite beyond the UDHR, fetched into tools/.cache/langdata
# (never committed; it is only ever read as test data):
#   aosp/<locale>-<file>.xml  Android's own UI strings -- Settings, SystemUI and
#                             the framework -- the kind of text a screen reader
#                             reads all day (AOSP, Apache License 2.0)
#   tatoeba/<iso3>.tsv        the first sentences Tatoeba has in each language,
#                             everyday sentences (CC BY 2.0 FR)
#   cldr/<locale>.xml         CLDR 48.2's locale data: the exemplar characters
#                             each language is written with (Unicode License)
#   Unihan_Variants.txt       which Han characters are simplified or
#                             traditional forms of which (Unicode License)
# Already present files are kept. A failed download is reported and skipped.
set -uo pipefail
ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)
OUT="$ROOT/tools/.cache/langdata"
mkdir -p "$OUT/aosp" "$OUT/tatoeba" "$OUT/cldr"
get() {  # url dest
  [ -s "$2" ] && return 0
  curl -sSfL --retry 3 --max-time 120 -o "$2.part" "$1" && mv "$2.part" "$2" || { rm -f "$2.part"; echo "skipped: $1"; }
}
RAW=https://raw.githubusercontent.com/aosp-mirror
LOCALES="ja zh-rCN zh-rTW zh-rHK hi mr ne bn as ar fa ur ru uk bg sr mk be kk ky mn
  es fr it pt-rBR pt-rPT de in ms nl ca ro pl cs sk tr vi tl sv da nb fi hu hr sl bs sq et lv lt is gl eu af sw zu uz az"
for loc in "" $LOCALES; do
  d=${loc:+-$loc}; name=${loc:-en}
  get "$RAW/platform_packages_apps_settings/main/res/values$d/strings.xml" "$OUT/aosp/$name-settings.xml"
  get "$RAW/platform_frameworks_base/main/packages/SystemUI/res/values$d/strings.xml" "$OUT/aosp/$name-systemui.xml"
  get "$RAW/platform_frameworks_base/main/core/res/res/values$d/strings.xml" "$OUT/aosp/$name-core.xml"
done
for iso in jpn cmn yue hin mar npi ben asm urd pes ara pus rus ukr bul srp mkd bel kaz spa fra ita por deu ind zsm eng; do
  f="$OUT/tatoeba/$iso.tsv"
  [ -s "$f" ] && continue
  # the first 4000 sentences only: the stream is cut there, the rest never downloaded
  curl -sSfL --max-time 300 "https://downloads.tatoeba.org/exports/per_language/$iso/${iso}_sentences.tsv.bz2" \
    | bzcat 2>/dev/null | head -n 4000 > "$f.part"
  [ -s "$f.part" ] && mv "$f.part" "$f" || { rm -f "$f.part"; echo "skipped: tatoeba $iso"; }
done
for loc in ja zh zh_Hant yue yue_Hans hi mr ne bn as ar fa ur ps sd ru uk bg sr mk be kk ky mn tg en es fr it pt de id ms; do
  get "https://raw.githubusercontent.com/unicode-org/cldr/release-48-2/common/main/$loc.xml" "$OUT/cldr/$loc.xml"
done
if [ ! -s "$OUT/Unihan_Variants.txt" ]; then
  get https://www.unicode.org/Public/18.0.0/ucd/Unihan.zip "$OUT/Unihan.zip"
  [ -s "$OUT/Unihan.zip" ] && unzip -o -q "$OUT/Unihan.zip" Unihan_Variants.txt Unihan_IRGSources.txt -d "$OUT" && rm -f "$OUT/Unihan.zip"
fi
echo "langdata: $(ls "$OUT/aosp" | wc -l) AOSP files, $(ls "$OUT/tatoeba" | wc -l) Tatoeba, $(ls "$OUT/cldr" | wc -l) CLDR"
