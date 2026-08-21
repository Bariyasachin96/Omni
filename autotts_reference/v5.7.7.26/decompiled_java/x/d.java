/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package x;

import android.content.Context;
import android.util.AttributeSet;
import java.util.HashMap;
import java.util.HashSet;

public abstract class d {
    public static int f = -1;
    public int a;
    public int b;
    public String c;
    public int d;
    public HashMap e;

    public d() {
        int n3;
        this.a = n3 = f;
        this.b = n3;
        this.c = null;
    }

    public abstract void a(HashMap var1);

    public abstract d b();

    public d c(d d3) {
        this.a = d3.a;
        this.b = d3.b;
        this.c = d3.c;
        this.d = d3.d;
        this.e = d3.e;
        return this;
    }

    public abstract void d(HashSet var1);

    public abstract void e(Context var1, AttributeSet var2);

    public boolean f(String string) {
        String string2 = this.c;
        if (string2 != null && string != null) {
            return string.matches(string2);
        }
        return false;
    }

    public void g(int n3) {
        this.a = n3;
    }

    public void h(HashMap hashMap) {
    }

    public d i(int n3) {
        this.b = n3;
        return this;
    }

    public boolean j(Object object) {
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        return Boolean.parseBoolean(object.toString());
    }

    public float k(Object object) {
        if (object instanceof Float) {
            return ((Float)object).floatValue();
        }
        return Float.parseFloat(object.toString());
    }

    public int l(Object object) {
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return Integer.parseInt(object.toString());
    }
}

