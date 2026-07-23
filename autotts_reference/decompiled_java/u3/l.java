/*
 * Decompiled with CFR 0.152.
 */
package u3;

import e3.i;
import r3.a;
import r3.c;
import r3.e;
import u3.b;
import u3.k;

public abstract class l
extends k {
    public static final int e(CharSequence charSequence) {
        o3.k.e(charSequence, "<this>");
        return charSequence.length() - 1;
    }

    public static final int f(CharSequence charSequence, String string, int n3, boolean bl) {
        o3.k.e(charSequence, "<this>");
        o3.k.e(string, "string");
        if (!bl && charSequence instanceof String) {
            return ((String)charSequence).indexOf(string, n3);
        }
        return l.h(charSequence, string, n3, charSequence.length(), bl, false, 16, null);
    }

    public static final int g(CharSequence charSequence, CharSequence charSequence2, int n3, int n4, boolean bl, boolean bl2) {
        a a4 = !bl2 ? new c(e.a(n3, 0), e.b(n4, charSequence.length())) : e.d(e.b(n3, l.e(charSequence)), e.a(n4, 0));
        if (charSequence instanceof String && charSequence2 instanceof String) {
            n3 = a4.a();
            int n5 = a4.b();
            n4 = a4.c();
            if (n4 > 0 && n3 <= n5 || n4 < 0 && n5 <= n3) {
                while (true) {
                    if (k.b((String)charSequence2, 0, (String)charSequence, n3, charSequence2.length(), bl)) {
                        return n3;
                    }
                    if (n3 != n5) {
                        n3 += n4;
                        continue;
                    }
                    break;
                }
            }
        } else {
            n3 = a4.a();
            int n6 = a4.b();
            n4 = a4.c();
            if (n4 > 0 && n3 <= n6 || n4 < 0 && n6 <= n3) {
                while (true) {
                    if (bl2 = l.l(charSequence2, 0, charSequence, n3, charSequence2.length(), bl)) {
                        return n3;
                    }
                    if (n3 == n6) break;
                    n3 += n4;
                }
            }
        }
        return -1;
    }

    public static /* synthetic */ int h(CharSequence charSequence, CharSequence charSequence2, int n3, int n4, boolean bl, boolean bl2, int n5, Object object) {
        if ((n5 & 0x10) != 0) {
            bl2 = false;
        }
        return l.g(charSequence, charSequence2, n3, n4, bl, bl2);
    }

    public static final int i(CharSequence charSequence, char c3, int n3, boolean bl) {
        o3.k.e(charSequence, "<this>");
        if (!bl && charSequence instanceof String) {
            return ((String)charSequence).lastIndexOf(c3, n3);
        }
        return l.k(charSequence, new char[]{c3}, n3, bl);
    }

    public static /* synthetic */ int j(CharSequence charSequence, char c3, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n3 = l.e(charSequence);
        }
        if ((n4 & 4) != 0) {
            bl = false;
        }
        return l.i(charSequence, c3, n3, bl);
    }

    public static final int k(CharSequence charSequence, char[] cArray, int n3, boolean bl) {
        o3.k.e(charSequence, "<this>");
        o3.k.e(cArray, "chars");
        if (!bl && cArray.length == 1 && charSequence instanceof String) {
            char c3 = i.t(cArray);
            return ((String)charSequence).lastIndexOf(c3, n3);
        }
        for (n3 = e.b(n3, l.e(charSequence)); -1 < n3; --n3) {
            char c4 = charSequence.charAt(n3);
            int n4 = cArray.length;
            for (int i3 = 0; i3 < n4; ++i3) {
                if (!b.a(cArray[i3], c4, bl)) continue;
                return n3;
            }
        }
        return -1;
    }

    public static final boolean l(CharSequence charSequence, int n3, CharSequence charSequence2, int n4, int n5, boolean bl) {
        o3.k.e(charSequence, "<this>");
        o3.k.e(charSequence2, "other");
        if (n4 >= 0 && n3 >= 0 && n3 <= charSequence.length() - n5 && n4 <= charSequence2.length() - n5) {
            for (int i3 = 0; i3 < n5; ++i3) {
                if (b.a(charSequence.charAt(n3 + i3), charSequence2.charAt(n4 + i3), bl)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static final String m(String string, char c3, String string2) {
        o3.k.e(string, "<this>");
        o3.k.e(string2, "missingDelimiterValue");
        int n3 = l.j(string, c3, 0, false, 6, null);
        if (n3 == -1) {
            return string2;
        }
        string = string.substring(n3 + 1, string.length());
        o3.k.d(string, "this as java.lang.String…ing(startIndex, endIndex)");
        return string;
    }

    public static /* synthetic */ String n(String string, char c3, String string2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            string2 = string;
        }
        return l.m(string, c3, string2);
    }
}

