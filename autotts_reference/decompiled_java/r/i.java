/*
 * Decompiled with CFR 0.152.
 */
package r;

import java.util.Arrays;
import java.util.HashSet;
import r.b;
import r.d;

public class i
implements Comparable {
    public static int t = 1;
    public boolean c;
    public String d;
    public int e = -1;
    public int f = -1;
    public int g = 0;
    public float h;
    public boolean i = false;
    public float[] j = new float[9];
    public float[] k = new float[9];
    public a l;
    public b[] m = new b[16];
    public int n = 0;
    public int o = 0;
    public boolean p = false;
    public int q = -1;
    public float r = 0.0f;
    public HashSet s = null;

    public i(a a4, String string) {
        this.l = a4;
    }

    public static void c() {
        ++t;
    }

    public final void a(b b3) {
        int n3;
        int n4;
        for (n4 = 0; n4 < (n3 = this.n); ++n4) {
            if (this.m[n4] != b3) continue;
            return;
        }
        b[] bArray = this.m;
        if (n3 >= bArray.length) {
            this.m = Arrays.copyOf(bArray, bArray.length * 2);
        }
        bArray = this.m;
        n4 = this.n;
        bArray[n4] = b3;
        this.n = n4 + 1;
    }

    public int b(i i3) {
        return this.e - i3.e;
    }

    public final void d(b bArray) {
        int n3 = this.n;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (this.m[i3] != bArray) continue;
            while (i3 < n3 - 1) {
                bArray = this.m;
                int n4 = i3 + 1;
                bArray[i3] = bArray[n4];
                i3 = n4;
            }
            --this.n;
            return;
        }
    }

    public void e() {
        this.d = null;
        this.l = a.g;
        this.g = 0;
        this.e = -1;
        this.f = -1;
        this.h = 0.0f;
        this.i = false;
        this.p = false;
        this.q = -1;
        this.r = 0.0f;
        int n3 = this.n;
        for (int i3 = 0; i3 < n3; ++i3) {
            this.m[i3] = null;
        }
        this.n = 0;
        this.o = 0;
        this.c = false;
        Arrays.fill(this.k, 0.0f);
    }

    public void f(d d3, float f3) {
        this.h = f3;
        this.i = true;
        this.p = false;
        this.q = -1;
        this.r = 0.0f;
        int n3 = this.n;
        this.f = -1;
        for (int i3 = 0; i3 < n3; ++i3) {
            this.m[i3].A(d3, this, false);
        }
        this.n = 0;
    }

    public void g(a a4, String string) {
        this.l = a4;
    }

    public final void h(d d3, b b3) {
        int n3 = this.n;
        for (int i3 = 0; i3 < n3; ++i3) {
            this.m[i3].B(d3, b3, false);
        }
        this.n = 0;
    }

    public String toString() {
        if (this.d != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(this.d);
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.e);
        return stringBuilder.toString();
    }

    public static final class a
    extends Enum {
        public static final /* enum */ a c = new a("UNRESTRICTED", 0);
        public static final /* enum */ a d = new a("CONSTANT", 1);
        public static final /* enum */ a e = new a("SLACK", 2);
        public static final /* enum */ a f = new a("ERROR", 3);
        public static final /* enum */ a g = new a("UNKNOWN", 4);
        public static final a[] h = a.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ a[] a() {
            return new a[]{c, d, e, f, g};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])h.clone();
        }
    }
}

