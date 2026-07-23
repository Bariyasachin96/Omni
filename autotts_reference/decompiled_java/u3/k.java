/*
 * Decompiled with CFR 0.152.
 */
package u3;

import r3.e;
import u3.j;
import u3.l;

public abstract class k
extends j {
    public static final boolean b(String string, int n3, String string2, int n4, int n5, boolean bl) {
        o3.k.e(string, "<this>");
        o3.k.e(string2, "other");
        if (!bl) {
            return string.regionMatches(n3, string2, n4, n5);
        }
        return string.regionMatches(bl, n3, string2, n4, n5);
    }

    public static final String c(String string, String string2, String string3, boolean bl) {
        o3.k.e(string, "<this>");
        o3.k.e(string2, "oldValue");
        o3.k.e(string3, "newValue");
        int n3 = 0;
        int n4 = l.f(string, string2, 0, bl);
        if (n4 < 0) {
            return string;
        }
        int n5 = string2.length();
        int n6 = e.a(n5, 1);
        int n7 = string.length() - n5 + string3.length();
        if (n7 >= 0) {
            int n8;
            StringBuilder stringBuilder = new StringBuilder(n7);
            do {
                stringBuilder.append(string, n3, n4);
                stringBuilder.append(string3);
                n7 = n4 + n5;
                if (n4 >= string.length()) break;
                n8 = l.f(string, string2, n4 + n6, bl);
                n3 = n7;
                n4 = n8;
            } while (n8 > 0);
            stringBuilder.append(string, n7, string.length());
            string = stringBuilder.toString();
            o3.k.d(string, "stringBuilder.append(this, i, length).toString()");
            return string;
        }
        throw new OutOfMemoryError();
    }

    public static /* synthetic */ String d(String string, String string2, String string3, boolean bl, int n3, Object object) {
        if ((n3 & 4) != 0) {
            bl = false;
        }
        return k.c(string, string2, string3, bl);
    }
}

