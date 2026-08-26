#!/usr/bin/env bash
# Check that every language tag CLD3's model can emit maps through our IsoCodes.
#
#   tools/verify/isocodes/run.sh
#
# CLD3's model has 109 language tags (counted from task_context_params.cc's
# kLanguageNames). Every one of them has to survive IsoCodes.toIso3, because the
# Kotlin side converts a detected tag to iso3 before looking up an engine. One
# did not: "und", CLD3's own word for undetermined, where CLD2 says "un". It
# counted as a real language and could win the aggregate detector, which is
# exactly what clsCLD2.f's two-candidate scan exists to prevent. It is folded at
# the cld3DetectRaw boundary now.
#
# java/Iso.java is a Java copy of IsoCodes.kt built the same way -- from
# Locale.getISOLanguages() plus the terminological and no-iso2 tables.
#
# Needs: javac. No network.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-isocodes
rm -rf "$WORK"; mkdir -p "$WORK"
cp "$HERE"/java/*.java "$WORK/"
(cd "$WORK" && javac -encoding UTF-8 -nowarn *.java 2>&1 | grep -vE "^(Note:|Picked up)" || true)
java -cp "$WORK" Iso 2>/dev/null | grep -v "^Picked up"
