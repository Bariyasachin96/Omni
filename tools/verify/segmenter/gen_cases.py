# -*- coding: utf-8 -*-
import sys, itertools
HI   = "नमस्ते"          # नमस्ते
HI2  = "दुनिया"          # दुनिया
DANDA= "।"
AR   = "مرحبا"                # مرحبا
ZH   = "你好"                                   # 你好
RU   = "привет"          # привет
EM   = "\U0001F600"
EM2  = "❤️"
FLAG = "\U0001F1EE\U0001F1F3"
BOLD = "\U0001D407\U0001D41E\U0001D425\U0001D425\U0001D428"   # 𝐇𝐞𝐥𝐥𝐨
FW   = "Ｔｅｓｔ"                       # Ｔｅｓｔ
CIRC = "①②ⓐ"                             # ①②ⓐ
SUP  = "¹²³"
NBSP = " "

TEXTS = [
 "Hello world",
 HI,
 "Hello " + HI,
 HI + " Hello",
 "Hello 12345 " + HI,
 "Hello, world!",
 "Hello " + EM + " world",
 HI + DANDA + " Hello",
 "12345",
 "!!!",
 EM,
 EM2,
 FLAG,
 "...",
 "   ",
 "1",
 "1.5",
 "3,000",
 "$50",
 "50%",
 "10:30",
 "Call 9876543210 now",
 "Call +91 98765 43210 now",
 "phone 987 654 3210",
 HI + " 9876543210 " + HI2,
 "Total: 1,234.56 USD",
 "abc 123 " + HI + " 456 " + AR,
 ZH + " hello " + RU,
 "Hello!" + EM + HI + "?" + ZH,
 BOLD + " world",
 FW + " " + HI,
 CIRC + " test",
 SUP + " test",
 NBSP + "Hello",
 NBSP + "123 Hello",
 NBSP + "!!! " + HI,
 "a" + NBSP + "b",
 "  Hello   world  ",
 "Hello\\tworld",
 HI + ", " + HI2 + "! " + EM,
 "OTP is 483920 for login",
 HI + " OTP 483920",
 "no digits here",
 "-500",
 "+15551234567",
 "007 123 4567",
 "2 + 2 = 4",
 "#1 @home",
 "e.g. i.e.",
 "Hello " + HI + " 42 " + EM + " end",
 HI + EM + "123" + "abc",
 "123" + HI,
 HI + "123",
 EM + "123",
 "123" + EM,
 "!!!" + HI,
 HI + "!!!",
 "A" + DANDA + "B",
 ZH + "123" + ZH,
 AR + " 12345 " + AR,
 "1234567",
 "12345678",
 "1 2 3 4 5 6 7 8",
 "98 76 54 32",
]

FEFF = "\ufeff"
FIGSP= "\u2007"
NNBSP= "\u202f"
TEXTS += [
 FEFF + "123 Hello",
 FIGSP + "123 " + HI,
 NNBSP + "!!! Hello",
 NBSP + "123",
 NBSP + "!!!",
 NBSP + EM,
 NBSP + EM + " Hello",
 NBSP + "123 " + HI,
 NBSP + "!!!" + HI,
 NBSP + "12:30 Hello",
 NBSP + "123 456 Hello",
 NBSP + HI + " 123",
 NBSP + "!!! 123 " + EM + " Hello",
 FEFF + EM + "123" + HI,
 NBSP + "Hello 123",
 NBSP + "123" + EM + "!!!",
]

WS = ["\u001c","\u001d","\u001e","\u001f","\u0085","\u1680","\u2000","\u2028","\u2029","\u205f","\u3000","\u200b","\u00ad"]
EXTRA = [
 "\u2764\ufe0f\u200d\U0001f525 hello",
 "\U0001f469\U0001f3fd hello",
 "1\ufe0f\u20e3 2\ufe0f\u20e3",
 "\u0966\u0967\u0968 " + HI,
 "\u0660\u0661\u0662 " + AR,
 "\uff11\uff12\uff13 Hello",
 "\u20ac100 and \u00a350",
 "\u00d7 \u00f7 \u00b0 test",
 "caf\u00e9 na\u00efve r\u00e9sum\u00e9",
 "\u1e9e\u1ef2 latin ext",
 "\u2c60\u2c61 \ua720\ua721",
 "Hello\u200bworld",
 "\u061c\u200e\u200fHello " + AR,
 "\u202aHello\u202c " + HI,
 HI + " " + ZH + " " + RU + " " + AR + " hello 123 " + EM,
 "a1b2c3",
 "12.34.56",
 "1,2,3",
 "....,,,,",
 "(123) 456-7890",
 "call me on 0123456789 please",
 HI + " 12 " + HI2 + " 34 " + HI,
 EM + EM + EM,
 "!" * 20,
 "9" * 20,
 "The year 2024 was good",
 "Version 1.2.3 released",
 "-1 -2 -3",
 "+1 +2 +3",
 "100%",
 "\u20b9500",
]
for w in WS:
    EXTRA.append("abc" + w + "def")
    EXTRA.append(w + "123 Hello")
    EXTRA.append("123" + w + HI)
    EXTRA.append(HI + w + "!!!")
TEXTS += EXTRA

MODEINTS = [(0,0,0),(1,0,0),(2,0,0),(3,0,0),(0,1,0),(0,2,0),(0,3,0),(0,0,1),(0,0,2),(0,0,3),
            (1,1,1),(2,2,2),(3,3,3),(1,2,3),(3,2,1),(0,1,2)]
FLAGS = [(1,0,1),(0,0,1),(1,1,1),(1,1,2),(1,1,3),(0,1,2)]
MODES = [1,4,5]
NEUT  = [("hin","hin","eng"),("hin","hin","hin"),("","","eng")]
HINTS = ["en,hi","", "en,hi,ar,zh,ru"]

KW = [
 "\u7535\u8bdd 12345678",
 "\u624b\u673a\uff1a13800138000",
 "\u9a8c\u8bc1\u7801 483920 \u8bf7\u52ff\u6cc4\u9732",
 "\u96fb\u8a71\u756a\u53f7 0312345678",
 "\u96fb\u8a71 03-1234-5678",
 "\uc804\ud654 01012345678",
 "\ubc88\ud638 010-1234-5678",
 "\u0e42\u0e17\u0e23\u0e28\u0e31\u0e1e\u0e17\u0eec 0812345678",
 "\u0e40\u0e1a\u0e2d\u0e23\u0e4c 0812345678",
 "phone 12345678",
 "phones 12345678",
 "telephone 12345678",
 "xphone 12345678",
 "PHONE 12345678",
 "12345678 phone",
 "otp 483920",
 "pin 4839",
 "code 4839 here",
 "\u0928\u0902\u092c\u0930 9876543210",
 "\u092e\u094b\u092c\u093e\u0907\u0932 9876543210",
 "\u0641\u0648\u0646 12345678",
 "\u0442\u0435\u043b\u0435\u0444\u043e\u043d 12345678",
 "1234 phone 5678",
 "a" * 60 + " phone 12345678",
 "phone " + "b" * 60 + " 12345678",
 "12345678 " + "c" * 30 + " phone",
]
TEXTS += KW
HINTS += ["zh,ja,ko,th", "zh", "th,ko", "hi,ur,fa,ru"]

out=[]
for text in TEXTS:
    for mi,(nm,pm,em) in enumerate(MODEINTS):
        for fi,(inflow,smart,grp) in enumerate(FLAGS):
            for mode in MODES:
                for ni,(dual,mixnl,dev) in enumerate(NEUT):
                    hints = HINTS[(mi+fi+ni) % len(HINTS)]
                    out.append("\t".join([str(mode),str(nm),str(pm),str(em),str(inflow),str(smart),str(grp),dual,mixnl,dev,hints,text]))
sys.stdout.write("\n".join(out)+"\n")
