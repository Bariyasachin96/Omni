/*
 * Decompiled with CFR 0.152.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import n0.e;
import n0.f;
import o.j;
import o.r;

public class p {
    public final r a = new r();
    public final j b = new j();

    public void a(RecyclerView.d0 d02, RecyclerView.m.b b3) {
        a a4;
        a a5 = a4 = (a)this.a.get(d02);
        if (a4 == null) {
            a5 = androidx.recyclerview.widget.p$a.b();
            this.a.put(d02, a5);
        }
        a5.a |= 2;
        a5.b = b3;
    }

    public void b(RecyclerView.d0 d02) {
        a a4;
        a a5 = a4 = (a)this.a.get(d02);
        if (a4 == null) {
            a5 = androidx.recyclerview.widget.p$a.b();
            this.a.put(d02, a5);
        }
        a5.a |= 1;
    }

    public void c(long l3, RecyclerView.d0 d02) {
        this.b.h(l3, d02);
    }

    public void d(RecyclerView.d0 d02, RecyclerView.m.b b3) {
        a a4;
        a a5 = a4 = (a)this.a.get(d02);
        if (a4 == null) {
            a5 = androidx.recyclerview.widget.p$a.b();
            this.a.put(d02, a5);
        }
        a5.c = b3;
        a5.a |= 8;
    }

    public void e(RecyclerView.d0 d02, RecyclerView.m.b b3) {
        a a4;
        a a5 = a4 = (a)this.a.get(d02);
        if (a4 == null) {
            a5 = androidx.recyclerview.widget.p$a.b();
            this.a.put(d02, a5);
        }
        a5.b = b3;
        a5.a |= 4;
    }

    public void f() {
        this.a.clear();
        this.b.a();
    }

    public RecyclerView.d0 g(long l3) {
        return (RecyclerView.d0)this.b.d(l3);
    }

    public boolean h(RecyclerView.d0 object) {
        return (object = (a)this.a.get(object)) != null && (((a)object).a & 1) != 0;
    }

    public boolean i(RecyclerView.d0 object) {
        return (object = (a)this.a.get(object)) != null && (((a)object).a & 4) != 0;
    }

    public void j() {
        androidx.recyclerview.widget.p$a.a();
    }

    public void k(RecyclerView.d0 d02) {
        this.p(d02);
    }

    public final RecyclerView.m.b l(RecyclerView.d0 object, int n3) {
        block5: {
            block8: {
                int n4;
                a a4;
                int n5;
                block7: {
                    block6: {
                        n5 = this.a.d(object);
                        if (n5 < 0) {
                            return null;
                        }
                        a4 = (a)this.a.j(n5);
                        if (a4 == null || ((n4 = a4.a) & n3) == 0) break block5;
                        a4.a = n4 = ~n3 & n4;
                        if (n3 != 4) break block6;
                        object = a4.b;
                        break block7;
                    }
                    if (n3 != 8) break block8;
                    object = a4.c;
                }
                if ((n4 & 0xC) == 0) {
                    this.a.h(n5);
                    androidx.recyclerview.widget.p$a.c(a4);
                }
                return object;
            }
            throw new IllegalArgumentException("Must provide flag PRE or POST");
        }
        return null;
    }

    public RecyclerView.m.b m(RecyclerView.d0 d02) {
        return this.l(d02, 8);
    }

    public RecyclerView.m.b n(RecyclerView.d0 d02) {
        return this.l(d02, 4);
    }

    public void o(b b3) {
        for (int i3 = this.a.size() - 1; i3 >= 0; --i3) {
            RecyclerView.d0 d02 = (RecyclerView.d0)this.a.f(i3);
            a a4 = (a)this.a.h(i3);
            int n3 = a4.a;
            if ((n3 & 3) == 3) {
                b3.a(d02);
            } else if ((n3 & 1) != 0) {
                RecyclerView.m.b b4 = a4.b;
                if (b4 == null) {
                    b3.a(d02);
                } else {
                    b3.c(d02, b4, a4.c);
                }
            } else if ((n3 & 0xE) == 14) {
                b3.b(d02, a4.b, a4.c);
            } else if ((n3 & 0xC) == 12) {
                b3.d(d02, a4.b, a4.c);
            } else if ((n3 & 4) != 0) {
                b3.c(d02, a4.b, null);
            } else if ((n3 & 8) != 0) {
                b3.b(d02, a4.b, a4.c);
            }
            androidx.recyclerview.widget.p$a.c(a4);
        }
    }

    public void p(RecyclerView.d0 object) {
        if ((object = (a)this.a.get(object)) == null) {
            return;
        }
        ((a)object).a &= 0xFFFFFFFE;
    }

    public void q(RecyclerView.d0 object) {
        for (int i3 = this.b.k() - 1; i3 >= 0; --i3) {
            if (object != this.b.l(i3)) continue;
            this.b.j(i3);
            break;
        }
        if ((object = (a)this.a.remove(object)) != null) {
            androidx.recyclerview.widget.p$a.c((a)object);
        }
    }

    public static class a {
        public static e d = new f(20);
        public int a;
        public RecyclerView.m.b b;
        public RecyclerView.m.b c;

        public static void a() {
            while (d.b() != null) {
            }
        }

        public static a b() {
            a a4;
            a a5 = a4 = (a)d.b();
            if (a4 == null) {
                a5 = new a();
            }
            return a5;
        }

        public static void c(a a4) {
            a4.a = 0;
            a4.b = null;
            a4.c = null;
            d.a(a4);
        }
    }

    public static interface b {
        public void a(RecyclerView.d0 var1);

        public void b(RecyclerView.d0 var1, RecyclerView.m.b var2, RecyclerView.m.b var3);

        public void c(RecyclerView.d0 var1, RecyclerView.m.b var2, RecyclerView.m.b var3);

        public void d(RecyclerView.d0 var1, RecyclerView.m.b var2, RecyclerView.m.b var3);
    }
}

