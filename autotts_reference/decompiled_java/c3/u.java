/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.k;
import c3.l;
import java.util.ArrayList;
import java.util.Locale;

public class u
implements Comparable {
    public Locale c;
    public l d;
    public int e;
    public ArrayList f;

    public u(Locale cloneable, l l3, int n3) {
        this.c = cloneable;
        this.d = l3;
        this.e = n3;
        cloneable = new ArrayList();
        this.f = cloneable;
        ((ArrayList)cloneable).add("*Default");
    }

    public void a(String string) {
        this.f.add(string);
    }

    public int b(u u3) {
        int n3 = this.e;
        int n4 = u3.e;
        if (n3 != n4) {
            return n3 - n4;
        }
        return this.g().compareToIgnoreCase(u3.g());
    }

    public String c() {
        return this.c.getDisplayLanguage();
    }

    public String d() {
        if (!this.c.getCountry().equals("")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.g());
            stringBuilder.append(", ");
            stringBuilder.append(this.c.getDisplayCountry());
            return stringBuilder.toString();
        }
        return this.g();
    }

    public String e() {
        return k.f(this.c);
    }

    public String f() {
        if (this.d != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.d.b);
            stringBuilder.append("#");
            stringBuilder.append(this.c.toString());
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Disable#");
        stringBuilder.append(this.c.toString());
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final String g() {
        Object object = this.d;
        if (object == null) {
            return "*Disabled";
        }
        String string = ((l)object).a;
        String[] stringArray = string.split(" ");
        if (stringArray.length < 2) return string;
        StringBuilder stringBuilder = new StringBuilder(stringArray[0]);
        int n3 = stringArray.length;
        int n4 = 1;
        StringBuilder stringBuilder2 = new StringBuilder(stringArray[n3 - 1]);
        n3 = stringArray.length - 2;
        object = new StringBuilder();
        ((StringBuilder)object).append((Object)stringBuilder);
        ((StringBuilder)object).append(" ... ");
        ((StringBuilder)object).append((Object)stringBuilder2);
        object = ((StringBuilder)object).toString();
        while (n4 < n3) {
            if (((String)object).length() > 15) return object;
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((Object)stringBuilder);
            ((StringBuilder)charSequence).append(" ... ");
            ((StringBuilder)charSequence).append((Object)stringBuilder2);
            charSequence = ((StringBuilder)charSequence).toString();
            if (((String)charSequence).length() > 15) {
                if (((String)charSequence).length() >= 20) return object;
                return charSequence;
            }
            if (stringArray[n4].length() < stringArray[n3].length()) {
                stringBuilder.append(" ");
                stringBuilder.append(stringArray[n4]);
                ++n4;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(stringArray[n3]);
                ((StringBuilder)object).append(" ");
                stringBuilder2.insert(0, ((StringBuilder)object).toString());
                --n3;
            }
            object = charSequence;
        }
        if (string.length() >= 15) return object;
        return string;
    }

    public void h(int n3) {
        this.e = n3;
    }
}

