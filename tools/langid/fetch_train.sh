#!/usr/bin/env bash
# The text tools/langid/train_nb.py learns from, fetched into
# tools/.cache/langtrain (never committed; only the learned table is):
#   wiki/<lang>.parquet  Wikipedia (wikimedia/wikipedia 20231101 on Hugging
#                        Face, the first file of each language), CC BY-SA 4.0
#   tatoeba/<iso3>.tsv   Tatoeba's sentences, up to 300,000 per language,
#                        CC BY 2.0 FR
#   cv/<locale>.txt      Common Voice's sentence collection, CC0
#   flores/              FLORES-200 dev and devtest, CC BY-SA 4.0 -- read only as
#                        TEST text, never learned from
# Already present files are kept.
set -uo pipefail
ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
OUT="$ROOT/tools/.cache/langtrain"
mkdir -p "$OUT/wiki" "$OUT/tatoeba" "$OUT/cv" "$OUT/flores"
get() {
  [ -s "$2" ] && return 0
  curl -sSfL --retry 3 --max-time 900 -o "$2.part" "$1" && mv "$2.part" "$2" || { rm -f "$2.part"; echo "skipped: $1"; }
}
HF=https://huggingface.co/datasets/wikimedia/wikipedia/resolve/main/20231101
for L in hi mr ne bn as ur fa ar ps uk bg sr id ms; do
  get "$HF.$L/train-00000-of-$(curl -sSL "https://huggingface.co/api/datasets/wikimedia/wikipedia/tree/main/20231101.$L" \
      | python3 -c 'import json,sys; print(json.load(sys.stdin)[0]["path"].split("-of-")[1])')" "$OUT/wiki/$L.parquet"
done
for iso in hin mar npi ben asm urd pes ara pus rus ukr bul srp eng spa fra deu por ita ind zsm; do
  f="$OUT/tatoeba/$iso.tsv"
  [ -s "$f" ] && continue
  curl -sSfL --max-time 900 "https://downloads.tatoeba.org/exports/per_language/$iso/${iso}_sentences.tsv.bz2" \
    | bzcat 2>/dev/null | head -n 300000 > "$f.part"
  [ -s "$f.part" ] && mv "$f.part" "$f" || { rm -f "$f.part"; echo "skipped: tatoeba $iso"; }
done
CV=https://raw.githubusercontent.com/common-voice/common-voice/main/server/data
for L in hi mr ne-NP bn as ur fa ar ps ru uk bg sr en es fr de pt it id ms; do
  get "$CV/$L/sentence-collector.txt" "$OUT/cv/$L.txt"
done
if [ ! -d "$OUT/flores/flores200_dataset" ]; then
  get https://dl.fbaipublicfiles.com/nllb/flores200_dataset.tar.gz "$OUT/flores/flores.tar.gz"
  [ -s "$OUT/flores/flores.tar.gz" ] && tar -xzf "$OUT/flores/flores.tar.gz" -C "$OUT/flores" && rm -f "$OUT/flores/flores.tar.gz"
fi
echo "langtrain: $(ls "$OUT/wiki" | wc -l) wiki, $(ls "$OUT/tatoeba" | wc -l) tatoeba, $(ls "$OUT/cv" | wc -l) cv"
