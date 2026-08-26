import sys, difflib
sys.path.insert(0,'.')
from skelmod import skel
a,_=skel(sys.argv[1]); b,_=skel(sys.argv[2])
for l in difflib.unified_diff(a,b,lineterm='',n=1): print(l)
