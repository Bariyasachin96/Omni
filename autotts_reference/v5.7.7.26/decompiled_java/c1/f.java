/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.j;
import d3.d;
import d3.h;
import o.m;
import o3.g;
import o3.k;

public final class f {
    public static final a c = new a(null);
    public static final f d;
    public final m a;
    public final m b;

    static {
        Float f3 = Float.valueOf(0.0f);
        d d3 = h.a(f3, f3);
        f3 = Float.valueOf(0.5f);
        d = new f(d3, h.a(f3, f3));
    }

    public f(d ... dArray) {
        k.e(dArray, "mappings");
        this.a = new m(dArray.length);
        this.b = new m(dArray.length);
        int n3 = dArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            this.a.h(((Number)dArray[i3].c()).floatValue());
            this.b.h(((Number)dArray[i3].d()).floatValue());
        }
        j.c(this.a);
        j.c(this.b);
    }

    public final float a(float f3) {
        return j.a(this.a, this.b, f3);
    }

    public final float b(float f3) {
        return j.a(this.b, this.a, f3);
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }
}

