#!/usr/bin/env bash
# The text tools/langid/train_nb.py learns from, fetched into
# tools/.cache/langtrain (never committed; only the learned table is):
#   wiki/<lang>.parquet  Wikipedia (wikimedia/wikipedia 20231101 on Hugging
#                        Face, the first file of each language), CC BY-SA 4.0
#   tatoeba/<iso3>.tsv   Tatoeba's sentences, up to 300,000 per language,
#                        CC BY 2.0 FR
#   cv/<locale>.txt      Common Voice's sentence collection, CC0
#   aosp/<locale>-<part>.xml
#                        Android's own UI strings OTHER than the three files the
#                        suite tests on (Settings, SystemUI, framework core):
#                        SettingsLib, PackageInstaller, the lock screen, Contacts,
#                        Calendar, Email, the media and download providers ...
#                        (AOSP, Apache License 2.0) -- the text a screen reader
#                        reads all day, which Wikipedia hardly holds
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
mkdir -p "$OUT/aosp"
RAW=https://raw.githubusercontent.com/aosp-mirror
BASE=platform_frameworks_base/main/packages
PARTS="settingslib:$BASE/SettingsLib/res printspooler:$BASE/PrintSpooler/res shell:$BASE/Shell/res
  vpndialogs:$BASE/VpnDialogs/res packageinstaller:$BASE/PackageInstaller/res companion:$BASE/CompanionDeviceManager/res
  keyguard:$BASE/SystemUI/res-keyguard sysuiproduct:$BASE/SystemUI/res-product carrierdefault:$BASE/CarrierDefaultApp/res
  soundpicker:$BASE/SoundPicker/res inputdevices:$BASE/InputDevices/res credentialmanager:$BASE/CredentialManager/res
  backupconfirm:$BASE/BackupRestoreConfirmation/res dsu:$BASE/DynamicSystemInstallationService/res
  externalstorage:$BASE/ExternalStorageProvider/res settingsprovider:$BASE/SettingsProvider/res
  contacts:platform_packages_apps_contacts/main/res calendar:platform_packages_apps_calendar/main/res
  email:platform_packages_apps_email/main/res stk:platform_packages_apps_stk/main/res
  soundrecorder:platform_packages_apps_soundrecorder/main/res
  downloadprovider:platform_packages_providers_downloadprovider/main/res
  mediaprovider:platform_packages_providers_mediaprovider/main/res
  contactsprovider:platform_packages_providers_contactsprovider/main/res
  calendarprovider:platform_packages_providers_calendarprovider/main/res"
# AOSP has no Pashto; "in" is its Indonesian
for loc in "" es fr de pt-rBR pt-rPT it in ms ru uk bg sr ar fa ur hi mr ne bn as; do
  for part in $PARTS; do
    get "$RAW/${part#*:}/values${loc:+-$loc}/strings.xml" "$OUT/aosp/${loc:-en}-${part%%:*}.xml" 2>/dev/null
  done
done
if [ ! -d "$OUT/flores/flores200_dataset" ]; then
  get https://dl.fbaipublicfiles.com/nllb/flores200_dataset.tar.gz "$OUT/flores/flores.tar.gz"
  [ -s "$OUT/flores/flores.tar.gz" ] && tar -xzf "$OUT/flores/flores.tar.gz" -C "$OUT/flores" && rm -f "$OUT/flores/flores.tar.gz"
fi
echo "langtrain: $(ls "$OUT/wiki" | wc -l) wiki, $(ls "$OUT/tatoeba" | wc -l) tatoeba, $(ls "$OUT/cv" | wc -l) cv, $(ls "$OUT/aosp" | wc -l) aosp"
