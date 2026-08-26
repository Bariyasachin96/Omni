"""Parse every generated XML resource and resolve every @color/@drawable/@style
/@string reference.

Cheap, and it catches what a Kotlin checker never sees: a malformed resource, or
a reference left dangling by a deleted file. It has already caught two real
things -- a "--" inside an XML comment (illegal in XML) and, historically,
drawables referenced after their block was removed.
"""
import os
import re
import sys
import xml.etree.ElementTree as ET

# Material Components / AppCompat theme attributes. The app depends on neither
# library any more, so a reference to one of these fails resource linking.
LIBRARY_ATTRS_WE_NO_LONGER_HAVE = set("""
colorPrimary colorPrimaryDark colorPrimaryContainer colorOnPrimary colorOnPrimaryContainer
colorSecondary colorOnSecondary colorSecondaryContainer colorOnSecondaryContainer
colorSurface colorOnSurface colorSurfaceVariant colorOnSurfaceVariant
colorOutline colorOutlineVariant colorError colorOnError
colorAccent colorControlNormal colorControlActivated colorControlHighlight
windowActionBar windowNoTitle actionBarSize selectableItemBackground
textAppearanceBody1 textAppearanceBody2 textAppearanceHeadline6
""".split())

import evpaths
RES = evpaths.main_dir()


def main():
    problems = 0
    for root, _, files in os.walk(RES):
        for name in files:
            if not name.endswith('.xml'):
                continue
            path = os.path.join(root, name)
            try:
                ET.parse(path)
            except Exception as exc:
                print('   MALFORMED %-40s %s' % (path.replace(RES + '/', ''), exc))
                problems += 1
    if problems:
        print('XML PROBLEMS:', problems)
        return 1

    have = {'color': set(), 'drawable': set(), 'style': set(), 'string': set()}
    for name in os.listdir(RES + '/res/values'):
        for el in ET.parse(RES + '/res/values/' + name).getroot():
            if el.tag in ('color', 'string', 'style'):
                have[el.tag].add(el.get('name'))
    for name in os.listdir(RES + '/res/drawable'):
        have['drawable'].add(name.rsplit('.', 1)[0])

    missing = set()
    for root, _, files in os.walk(RES):
        for name in files:
            if not name.endswith('.xml'):
                continue
            text = open(os.path.join(root, name), encoding='utf-8').read()
            for kind, ref in re.findall(r'@(color|drawable|style|string)/([A-Za-z0-9_.]+)', text):
                if ref not in have[kind]:
                    missing.add((name, kind, ref))
            # Theme attribute references. "?colorControlNormal" is valid shorthand
            # for "?attr/colorControlNormal", so matching only "?attr/" misses it
            # -- which is exactly how two drawables kept an AppCompat attribute
            # after that library was removed, and only CI noticed:
            #   error: resource attr/colorControlNormal not found
            # Anything not prefixed android: comes from a library or our own
            # attrs.xml, and we have neither.
            for ref in re.findall(r'\?(?:attr/)?([A-Za-z_][A-Za-z0-9_]*)', text):
                if ref not in LIBRARY_ATTRS_WE_NO_LONGER_HAVE:
                    continue
                missing.add((name, 'attr', ref))
    for name, kind, ref in sorted(missing):
        prefix = '?' if kind == 'attr' else '@' + kind + '/'
        print('   MISSING %s%s referenced by %s' % (prefix, ref if kind == 'attr' else ref, name))
    print('XML PROBLEMS:', len(missing))
    return 1 if missing else 0


if __name__ == '__main__':
    sys.exit(main())
