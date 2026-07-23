/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import u.e;
import v.c;
import v.d;
import v.f;
import v.k;
import v.l;
import v.n;
import v.p;

public class m {
    public static int h;
    public int a = 0;
    public boolean b = false;
    public p c = null;
    public p d = null;
    public ArrayList e = new ArrayList();
    public int f;
    public int g;

    public m(p p3, int n3) {
        int n4;
        this.f = n4 = h;
        h = n4 + 1;
        this.c = p3;
        this.d = p3;
        this.g = n3;
    }

    public void a(p p3) {
        this.e.add(p3);
        this.d = p3;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public long b(u.f object, int n3) {
        long l3;
        void var18_7;
        p p3 = this.c;
        boolean bl = p3 instanceof c;
        long l4 = 0L;
        if (bl ? ((c)p3).f != n3 : (n3 == 0 ? !(p3 instanceof l) : !(p3 instanceof n))) {
            return 0L;
        }
        if (n3 == 0) {
            l l5 = ((e)object).e;
        } else {
            n n4 = ((e)object).f;
        }
        f f3 = var18_7.h;
        object = n3 == 0 ? ((e)object).e : ((e)object).f;
        object = ((p)object).i;
        bl = p3.h.l.contains(f3);
        boolean bl2 = this.c.i.l.contains(object);
        long l6 = this.c.j();
        if (bl && bl2) {
            float f4;
            l3 = this.d(this.c.h, 0L);
            long l7 = this.c(this.c.i, 0L);
            long l8 = l3 - l6;
            object = this.c;
            int n5 = ((p)object).i.f;
            l3 = l8;
            if (l8 >= (long)(-n5)) {
                l3 = l8 + (long)n5;
            }
            l8 = -l7;
            n5 = ((p)object).h.f;
            l8 = l7 = l8 - l6 - (long)n5;
            if (l7 >= (long)n5) {
                l8 = l7 - (long)n5;
            }
            if ((f4 = ((p)object).b.s(n3)) > 0.0f) {
                l4 = (long)((float)l8 / f4 + (float)l3 / (1.0f - f4));
            }
            float f5 = l4;
            l3 = (long)(f5 * f4 + 0.5f);
            l8 = (long)(f5 * (1.0f - f4) + 0.5f);
            object = this.c;
            l3 = (long)((p)object).h.f + (l3 + l6 + l8);
            n3 = ((p)object).i.f;
            return l3 - (long)n3;
        }
        if (bl) {
            object = this.c.h;
            return Math.max(this.d((f)object, ((f)object).f), (long)this.c.h.f + l6);
        }
        if (bl2) {
            object = this.c.i;
            long l9 = this.c((f)object, ((f)object).f);
            long l10 = -this.c.i.f;
            return Math.max(-l9, l10 + l6);
        }
        object = this.c;
        l3 = (long)((p)object).h.f + ((p)object).j();
        n3 = this.c.i.f;
        return l3 - (long)n3;
    }

    public final long c(f f3, long l3) {
        long l4;
        p p3 = f3.d;
        if (p3 instanceof k) {
            return l3;
        }
        int n3 = f3.k.size();
        long l5 = l3;
        for (int i3 = 0; i3 < n3; ++i3) {
            d d3 = (d)f3.k.get(i3);
            l4 = l5;
            if (d3 instanceof f) {
                d3 = (f)d3;
                l4 = ((f)d3).d == p3 ? l5 : Math.min(l5, this.c((f)d3, (long)((f)d3).f + l3));
            }
            l5 = l4;
        }
        if (f3 == p3.i) {
            l4 = p3.j();
            f3 = p3.h;
            return Math.min(Math.min(l5, this.c(f3, l3 -= l4)), l3 - (long)p3.h.f);
        }
        return l5;
    }

    public final long d(f f3, long l3) {
        long l4;
        p p3 = f3.d;
        if (p3 instanceof k) {
            return l3;
        }
        int n3 = f3.k.size();
        long l5 = l3;
        for (int i3 = 0; i3 < n3; ++i3) {
            d d3 = (d)f3.k.get(i3);
            l4 = l5;
            if (d3 instanceof f) {
                d3 = (f)d3;
                l4 = ((f)d3).d == p3 ? l5 : Math.max(l5, this.d((f)d3, (long)((f)d3).f + l3));
            }
            l5 = l4;
        }
        if (f3 == p3.h) {
            l4 = p3.j();
            f3 = p3.i;
            return Math.max(Math.max(l5, this.d(f3, l3 += l4)), l3 - (long)p3.i.f);
        }
        return l5;
    }
}

