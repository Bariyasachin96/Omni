/*
 * Decompiled with CFR 0.152.
 */
package v;

import u.d;
import u.e;
import v.d;
import v.f;
import v.g;
import v.m;

public abstract class p
implements d {
    public int a;
    public e b;
    public m c;
    public e.b d;
    public g e = new g(this);
    public int f = 0;
    public boolean g = false;
    public f h = new f(this);
    public f i = new f(this);
    public b j = v.p$b.c;

    public p(e e3) {
        this.b = e3;
    }

    @Override
    public abstract void a(d var1);

    public final void b(f f3, f f4, int n3) {
        f3.l.add(f4);
        f3.f = n3;
        f4.k.add(f3);
    }

    public final void c(f f3, f f4, int n3, g g3) {
        f3.l.add(f4);
        f3.l.add(this.e);
        f3.h = n3;
        f3.i = g3;
        f4.k.add(f3);
        g3.k.add(f3);
    }

    public abstract void d();

    public abstract void e();

    public abstract void f();

    public final int g(int n3, int n4) {
        if (n4 == 0) {
            e e3 = this.b;
            int n5 = e3.A;
            n4 = Math.max(e3.z, n3);
            if (n5 > 0) {
                n4 = Math.min(n5, n3);
            }
            if (n4 != n3) {
                return n4;
            }
        } else {
            e e4 = this.b;
            int n6 = e4.D;
            n4 = Math.max(e4.C, n3);
            if (n6 > 0) {
                n4 = Math.min(n6, n3);
            }
            if (n4 != n3) {
                return n4;
            }
        }
        return n3;
    }

    public final f h(u.d object) {
        Object object2 = ((u.d)object).f;
        if (object2 == null) {
            return null;
        }
        object = object2.d;
        object2 = object2.e;
        int n3 = v.p$a.a[((Enum)object2).ordinal()];
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        if (n3 != 5) {
                            return null;
                        }
                        return ((e)object).f.i;
                    }
                    return ((e)object).f.k;
                }
                return ((e)object).f.h;
            }
            return ((e)object).e.i;
        }
        return ((e)object).e.h;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final f i(u.d object, int n3) {
        u.d d3 = ((u.d)object).f;
        if (d3 == null) {
            return null;
        }
        object = d3.d;
        object = n3 == 0 ? ((e)object).e : ((e)object).f;
        d.a a4 = d3.e;
        n3 = v.p$a.a[a4.ordinal()];
        if (n3 == 1) return ((p)object).h;
        if (n3 == 2) return ((p)object).i;
        if (n3 == 3) return ((p)object).h;
        if (n3 == 5) return ((p)object).i;
        return null;
    }

    public long j() {
        g g3 = this.e;
        if (g3.j) {
            return g3.g;
        }
        return 0L;
    }

    public boolean k() {
        return this.g;
    }

    public final void l(int n3, int n4) {
        block6: {
            block7: {
                block9: {
                    block8: {
                        p p3;
                        e e3;
                        block10: {
                            int n5 = this.a;
                            if (n5 == 0) break block6;
                            if (n5 == 1) break block7;
                            if (n5 == 2) break block8;
                            if (n5 != 3) break block9;
                            e3 = this.b;
                            p3 = e3.e;
                            Object object = p3.d;
                            e.b b3 = e.b.e;
                            if (object != b3 || p3.a != 3) break block10;
                            object = e3.f;
                            if (((p)object).d == b3 && ((p)object).a == 3) break block9;
                        }
                        if (n3 == 0) {
                            p3 = e3.f;
                        }
                        if (p3.e.j) {
                            float f3 = e3.x();
                            n3 = n3 == 1 ? (int)((float)p3.e.g / f3 + 0.5f) : (int)(f3 * (float)p3.e.g + 0.5f);
                            this.e.d(n3);
                            return;
                        }
                        break block9;
                    }
                    Object object = this.b.M();
                    if (object != null) {
                        object = n3 == 0 ? ((e)object).e : ((e)object).f;
                        object = ((p)object).e;
                        if (((f)object).j) {
                            float f4 = n3 == 0 ? this.b.B : this.b.E;
                            n4 = (int)((float)((f)object).g * f4 + 0.5f);
                            this.e.d(this.g(n4, n3));
                        }
                    }
                }
                return;
            }
            n3 = this.g(this.e.m, n3);
            this.e.d(Math.min(n3, n4));
            return;
        }
        this.e.d(this.g(n4, n3));
    }

    public abstract boolean m();

    public void n(d d3, u.d object, u.d d4, int n3) {
        f f3 = this.h((u.d)object);
        d3 = this.h(d4);
        if (f3.j && ((f)d3).j) {
            int n4 = f3.g + ((u.d)object).f();
            int n5 = ((f)d3).g - d4.f();
            int n6 = n5 - n4;
            if (!this.e.j && this.d == e.b.e) {
                this.l(n3, n6);
            }
            object = this.e;
            if (((f)object).j) {
                if (((f)object).g == n6) {
                    this.h.d(n4);
                    this.i.d(n5);
                    return;
                }
                float f4 = n3 == 0 ? this.b.A() : this.b.T();
                n3 = n4;
                if (f3 == d3) {
                    n3 = f3.g;
                    n5 = ((f)d3).g;
                    f4 = 0.5f;
                }
                n4 = this.e.g;
                this.h.d((int)((float)n3 + 0.5f + (float)(n5 - n3 - n4) * f4));
                this.i.d(this.h.g + this.e.g);
            }
        }
    }

    public void o(d d3) {
    }

    public void p(d d3) {
    }

    public static final class b
    extends Enum {
        public static final /* enum */ b c = new b("NONE", 0);
        public static final /* enum */ b d = new b("START", 1);
        public static final /* enum */ b e = new b("END", 2);
        public static final /* enum */ b f = new b("CENTER", 3);
        public static final b[] g = v.p$b.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public b() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ b[] a() {
            return new b[]{c, d, e, f};
        }

        public static b valueOf(String string) {
            return Enum.valueOf(b.class, string);
        }

        public static b[] values() {
            return (b[])g.clone();
        }
    }
}

