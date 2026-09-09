#!/usr/bin/env python3
"""Resolve the app's compile classpath from Google Maven + Maven Central.

Why this exists: kotlinc can only type-check a call site whose types it can
resolve. Without the androidx / Compose / Material3 jars on the classpath it
resolves NONE of them, reports over a thousand unresolved references, and
silently checks nothing at every Compose call site -- which is how an
@ExperimentalMaterial3Api opt-in once reached CI and broke build 827.

It is a small Maven resolver rather than a hard-coded list because the list is
not knowable by hand: `implementation("androidx.compose.material3:material3")`
has no version at all (the BOM supplies it) and pulls in a tree of its own.

Scope: compile and runtime, non-optional, minus anything in <exclusions>.
Version conflicts are settled by HIGHEST wins. Maven itself uses nearest-wins,
so this can differ -- but for a type-check classpath "the newer API surface"
is the safe direction, and the exact resolved version is CI's job, not ours.
"""
import re, sys, zipfile, urllib.request, xml.etree.ElementTree as ET
from pathlib import Path

M2 = "{http://maven.apache.org/POM/4.0.0}"
REPOS = ["https://dl.google.com/dl/android/maven2",
         "https://repo1.maven.org/maven2"]

CACHE = Path(__file__).resolve().parent / ".cache" / "m2"
# g:a:v -> <packaging>. A Compose artifact like androidx.compose.ui:ui is
# packaging=pom: a shim whose only job is to depend on ui-android, which is
# where the classes are. It has no aar and no jar and that is not a failure.
PACKAGING = {}
POMS  = CACHE / "pom"
LIBS  = CACHE / "lib"


def fetch(url):
    req = urllib.request.Request(url, headers={"User-Agent": "easyvoice-bootstrap"})
    with urllib.request.urlopen(req, timeout=120) as r:
        return r.read()


def coord_path(g, a, v, ext):
    return f"{g.replace('.', '/')}/{a}/{v}/{a}-{v}.{ext}"


def get(g, a, v, ext, dest_dir):
    """Download <g:a:v>.<ext> from the first repo that has it. Cached on disk."""
    dest = dest_dir / f"{g}--{a}--{v}.{ext}"
    if dest.exists():
        return dest
    rel = coord_path(g, a, v, ext)
    for repo in REPOS:
        try:
            data = fetch(f"{repo}/{rel}")
        except Exception:
            continue
        dest_dir.mkdir(parents=True, exist_ok=True)
        dest.write_bytes(data)
        return dest
    return None


def text(node):
    return (node.text or "").strip() if node is not None else ""


def norm_version(v):
    """Maven version ranges. androidx poms pin with the HARD-REQUIREMENT form
    `[1.12.0]`, which means exactly that version -- and reading it literally is
    what dropped compose runtime, ui-graphics, ui-text and ui-unit off the
    classpath on the first run of this script. `[a,b)` takes the lower bound.
    "unspecified" is what a pom writes when it has no version to give; it is not
    a version and must fall through to the managed one."""
    v = v.strip()
    if not v or v == "unspecified":
        return ""
    if v.startswith(("[", "(")):
        inner = v.strip("[]()")
        parts = [x.strip() for x in inner.split(",")]
        for x in parts:
            if x:
                return x
        return ""
    return v


def subst(s, props):
    """Expand ${...} against the pom's own properties. Two passes is enough for
    the androidx poms, which never nest deeper than that."""
    for _ in range(2):
        def rep(m):
            return props.get(m.group(1), m.group(0))
        s2 = re.sub(r"\$\{([^}]+)\}", rep, s)
        if s2 == s:
            break
        s = s2
    return s


def read_pom(g, a, v, seen_parents=()):
    """Return (properties, managed{ga:version}, deps[list]) for one pom,
    with its parent chain folded in."""
    p = get(g, a, v, "pom", POMS)
    if p is None:
        return {}, {}, []
    try:
        root = ET.fromstring(p.read_bytes())
    except ET.ParseError:
        return {}, {}, []

    props, managed, deps = {}, {}, []
    PACKAGING[f"{g}:{a}:{v}"] = text(root.find(f"{M2}packaging")) or "jar"

    par = root.find(f"{M2}parent")
    if par is not None:
        pg, pa, pv = (text(par.find(f"{M2}groupId")),
                      text(par.find(f"{M2}artifactId")),
                      text(par.find(f"{M2}version")))
        key = f"{pg}:{pa}:{pv}"
        if pg and pa and pv and key not in seen_parents:
            props, managed, deps = read_pom(pg, pa, pv, seen_parents + (key,))

    props.update({"project.version": v, "project.groupId": g,
                  "pom.version": v, "version": v})
    pn = root.find(f"{M2}properties")
    if pn is not None:
        for c in pn:
            props[c.tag.replace(M2, "")] = text(c)

    def parse_dep(d):
        dg = subst(text(d.find(f"{M2}groupId")), props)
        da = subst(text(d.find(f"{M2}artifactId")), props)
        dv = norm_version(subst(text(d.find(f"{M2}version")), props))
        sc = text(d.find(f"{M2}scope")) or "compile"
        op = text(d.find(f"{M2}optional")) == "true"
        ty = text(d.find(f"{M2}type")) or "jar"
        ex = set()
        en = d.find(f"{M2}exclusions")
        if en is not None:
            for e in en.findall(f"{M2}exclusion"):
                ex.add(f"{text(e.find(f'{M2}groupId'))}:{text(e.find(f'{M2}artifactId'))}")
        return dict(g=dg, a=da, v=dv, scope=sc, optional=op, type=ty, excl=ex)

    dm = root.find(f"{M2}dependencyManagement")
    if dm is not None:
        for d in dm.findall(f".//{M2}dependency"):
            x = parse_dep(d)
            if x["scope"] == "import" and x["type"] == "pom":
                _, im, _ = read_pom(x["g"], x["a"], x["v"], seen_parents)
                managed.update(im)
            elif x["v"]:
                managed[f"{x['g']}:{x['a']}"] = x["v"]

    dn = root.find(f"{M2}dependencies")
    if dn is not None:
        deps = deps + [parse_dep(d) for d in dn.findall(f"{M2}dependency")]

    return props, managed, deps


def resolve(roots, bom):
    """Walk the graph breadth-first. Returns {g:a -> version}."""
    _, managed, _ = read_pom(*bom.split(":"))
    print(f"  BOM {bom}: {len(managed)} managed versions", file=sys.stderr)

    chosen, queue, visited = {}, list(roots), set()

    def newer(x, y):
        def key(s):
            return [int(t) if t.isdigit() else t
                    for t in re.split(r"[.\-]", s)]
        try:
            return key(x) > key(y)
        except TypeError:
            return x > y

    while queue:
        g, a, v, excl = queue.pop(0)
        ga = f"{g}:{a}"
        if not v:
            v = managed.get(ga, "")
        if not v:
            print(f"  ! no version for {ga}", file=sys.stderr)
            continue
        v = norm_version(v) or v
        if ga in chosen and not newer(v, chosen[ga]):
            pass
        else:
            chosen[ga] = v
        if (ga, v) in visited:
            continue
        visited.add((ga, v))

        _, m2, deps = read_pom(g, a, v)
        local = dict(managed)
        local.update(m2)
        for d in deps:
            if d["optional"] or d["scope"] not in ("compile", "runtime"):
                continue
            if d["type"] not in ("jar", "aar", ""):
                continue
            dga = f"{d['g']}:{d['a']}"
            if dga in excl or f"{d['g']}:*" in excl or "*:*" in excl:
                continue
            dv = d["v"] or local.get(dga, "")
            queue.append((d["g"], d["a"], dv, excl | d["excl"]))

    return chosen


def download_classes(chosen):
    """Fetch each artifact and return the list of jars to put on the classpath.
    An .aar is a zip; the code is classes.jar inside it."""
    LIBS.mkdir(parents=True, exist_ok=True)
    jars, missing = [], []
    for ga, v in sorted(chosen.items()):
        g, a = ga.split(":")
        out = LIBS / f"{g}--{a}--{v}.jar"
        if out.exists():
            jars.append(out)
            continue
        f = get(g, a, v, "aar", LIBS)
        if f is not None:
            try:
                with zipfile.ZipFile(f) as z:
                    out.write_bytes(z.read("classes.jar"))
                f.unlink()
                jars.append(out)
                continue
            except (KeyError, zipfile.BadZipFile):
                f.unlink(missing_ok=True)
        f = get(g, a, v, "jar", LIBS)
        if f is not None:
            f.rename(out)
            jars.append(out)
            continue
        if PACKAGING.get(f"{g}:{a}:{v}") == "pom":
            continue
        missing.append(f"{ga}:{v}")
    return jars, missing


def parse_build_gradle(path):
    """Read the roots and the BOM out of app/build.gradle.kts itself, so this
    can never drift from what the app actually declares. A hand-kept list here
    would go stale the first time a dependency is added and say nothing."""
    src = path.read_text()
    # Strip // comments so a coordinate quoted inside a comment is not read as
    # a dependency -- build.gradle.kts has several.
    src = re.sub(r"//[^\n]*", "", src)
    bom = None
    roots = []
    for m in re.finditer(r'(implementation|api)\s*\(\s*(platform\s*\(\s*)?"([^"]+)"', src):
        coord = m.group(3)
        if m.group(2):
            bom = coord
            continue
        parts = coord.split(":")
        if len(parts) == 2:
            roots.append((parts[0], parts[1], "", set()))
        elif len(parts) == 3:
            roots.append((parts[0], parts[1], parts[2], set()))
    return bom, roots


def main():
    gradle = Path(__file__).resolve().parent.parent / "app" / "build.gradle.kts"
    bom, roots = parse_build_gradle(gradle)
    if bom is None:
        print("  ! no platform(...) BOM found in build.gradle.kts", file=sys.stderr)
        sys.exit(1)
    print(f"  roots    {len(roots)} declared in build.gradle.kts", file=sys.stderr)
    chosen = resolve(roots, bom)
    print(f"  resolved {len(chosen)} artifacts", file=sys.stderr)
    jars, missing = download_classes(chosen)
    print(f"  fetched  {len(jars)} class jars", file=sys.stderr)
    for m in missing:
        print(f"  ! no aar/jar for {m}", file=sys.stderr)
    if not jars:
        sys.exit(1)
    out = Path(sys.argv[1])
    out.write_text("\n".join(str(j) for j in jars) + "\n")


if __name__ == "__main__":
    main()
