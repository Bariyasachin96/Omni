import sys, re
def method_containing(path, needle):
    s=open(path,encoding='utf-8').read()
    pos=s.index(needle)
    # walk back to the enclosing method header at 4-space indent
    starts=[m.start() for m in re.finditer(r'\n    (?:public|private|protected|final|static)[^\n;=]*\)\s*\{', s) if m.start()<pos]
    i=starts[-1]+1
    d=0;k=s.index('{',i)
    while True:
        if s[k]=='{':d+=1
        elif s[k]=='}':
            d-=1
            if d==0:break
        k+=1
    return s[i:k+1]
print(method_containing(sys.argv[1], sys.argv[2]))
