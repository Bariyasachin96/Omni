"""Where the source lives, for every checker in this directory.

Until 2026-08-26 the source did not exist as files at all -- it was embedded in
ci/generate.py and written out into a scratch directory called out/, which is
why the checkers used to hard-code that path. The source is checked in now, so
they resolve the repository root instead.

Override with EV_ROOT, or pass a root as the first argument to any checker, if
you want to point one at a tree somewhere else (an old commit exported with
`git archive`, for instance).
"""
import os
import sys

KOTLIN_PACKAGE = os.path.join('app', 'src', 'main', 'java', 'com', 'sachinbaria', 'easyvoice')
ANDROID_TEST_PACKAGE = os.path.join('app', 'src', 'androidTest', 'java', 'com', 'sachinbaria', 'easyvoice')
MAIN = os.path.join('app', 'src', 'main')
CPP = os.path.join('app', 'src', 'main', 'cpp')


def repo_root():
    override = os.environ.get('EV_ROOT')
    if override:
        return os.path.abspath(override)
    for arg in sys.argv[1:]:
        if not arg.startswith('-') and os.path.isdir(os.path.join(arg, 'app')):
            return os.path.abspath(arg)
    here = os.path.dirname(os.path.abspath(__file__))
    while True:
        if os.path.isdir(os.path.join(here, '.git')) or os.path.isdir(os.path.join(here, 'app')):
            return here
        parent = os.path.dirname(here)
        if parent == here:
            raise SystemExit('evpaths: no repository root above %s -- set EV_ROOT'
                             % os.path.dirname(os.path.abspath(__file__)))
        here = parent


def kotlin_dir():
    return os.path.join(repo_root(), KOTLIN_PACKAGE)


def android_test_dir():
    """The instrumented tests.

    These compile ONLY in CI, on the emulator job, so a brace left open or a
    missing import there costs a thirteen-minute run to discover. ktcheck reads
    them for that reason -- it is a structure check and needs no classpath.
    Returns None when the directory is absent, so a checker can skip it rather
    than fail on a tree that has no tests.
    """
    path = os.path.join(repo_root(), ANDROID_TEST_PACKAGE)
    return path if os.path.isdir(path) else None


def main_dir():
    return os.path.join(repo_root(), MAIN)


def cpp_dir():
    return os.path.join(repo_root(), CPP)
