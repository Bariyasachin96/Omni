#!/usr/bin/env bash
# Every shell fragment in .github/workflows/build.yml, parsed by the shell that
# will actually run it.
#
#   tools/check/workflow-shell.sh
#
# WHY THIS EXISTS. Build 868 and 869 both went red on one line:
#
#     /usr/bin/sh -c set -o pipefail
#     /usr/bin/sh: 1: set: Illegal option -o pipefail
#
# The fragment was valid BASH and had been tested as bash. What was not tested
# is that `reactivecircus/android-emulator-runner` runs its `script:` input with
# /usr/bin/sh -- dash -- so a bashism in there is a two-run outage that no local
# test would have caught. This checks each fragment against ITS OWN shell:
#
#     run:      -> bash -n     (GitHub runs `run:` with bash on Linux)
#     script:   -> dash -n     (the emulator action runs it with sh)
#
# It is a SYNTAX check, so it costs milliseconds and cannot execute anything.
set -u
ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
cd "$ROOT"
WF=.github/workflows/build.yml
WORK=$(mktemp -d)
trap 'rm -rf "$WORK"' EXIT

echo "workflow shell syntax ($WF)"
[ -f "$WF" ] || { echo "  no workflow file -- nothing to check"; exit 0; }

python3 - "$WF" "$WORK" <<'PY'
import sys, os, yaml
wf, work = sys.argv[1], sys.argv[2]
doc = yaml.safe_load(open(wf))
n = 0
for jobname, job in (doc.get('jobs') or {}).items():
    for i, step in enumerate(job.get('steps') or []):
        name = step.get('name', f'step {i}')
        if 'run' in step:
            kind, body = 'bash', step['run']
        elif isinstance(step.get('with'), dict) and 'script' in step['with']:
            kind, body = 'dash', step['with']['script']
        else:
            continue
        n += 1
        path = os.path.join(work, f'{n:02d}.{kind}.sh')
        open(path, 'w').write(body)
        open(path + '.label', 'w').write(f'{jobname} / {name}')
print(f'  {n} shell fragments extracted')
PY

# A SYNTAX CHECK ALONE IS NOT ENOUGH, and finding that out is the reason this
# script has a second half. `dash -n` PARSES every one of these happily:
#
#     set -o pipefail        <- the exact line that broke builds 868 and 869
#     if [[ 1 == 1 ]]; then
#     f() { local n=0; }
#
# They are syntactically fine and fail at RUNTIME ("Illegal option -o pipefail",
# "[[: not found"). So a checker built only on `dash -n` would have reported ok
# for precisely the bug it was written to prevent. It was written that way
# first, and tested, and it said ok.
#
# Hence a bashism blacklist for the fragments that run under sh. `local` is NOT
# on it: dash implements it, and Ubuntu's /bin/sh IS dash.
BASHISMS='set -o pipefail|set -o posix|\[\[|^\s*function [A-Za-z_]|<<<|\$\(\(.*\*\*|=\(\)|source |\$\{[A-Za-z_][A-Za-z0-9_]*\^\^|\$\{[A-Za-z_][A-Za-z0-9_]*,,|echo -e|&>'

fail=0
for f in "$WORK"/*.bash.sh "$WORK"/*.dash.sh; do
  [ -e "$f" ] || continue
  label=$(cat "$f.label")
  case "$f" in
    *.bash.sh) sh_bin=bash ;;
    *.dash.sh) sh_bin=$(command -v dash || echo sh) ;;
  esac
  bad=""
  "$sh_bin" -n "$f" 2>"$f.err" || bad="syntax"
  if [ "$bad" = "" ] && [ "${f##*.}" != "" ]; then
    case "$f" in
      *.dash.sh)
        hits=$(grep -nE "$BASHISMS" "$f" || true)
        if [ -n "$hits" ]; then bad="bashism"; printf '%s\n' "$hits" > "$f.err"; fi
        ;;
    esac
  fi
  if [ -z "$bad" ]; then
    printf '  ok    %-16s %s\n' "[$sh_bin]" "$label"
  else
    printf '  FAIL  %-16s %s  (%s)\n' "[$sh_bin]" "$label" "$bad"
    sed 's/^/          /' "$f.err"
    fail=1
  fi
done

[ "$fail" = 0 ] && echo "EVERY FRAGMENT PARSES IN ITS OWN SHELL" || echo "A WORKFLOW SHELL FRAGMENT DOES NOT PARSE"
exit $fail
