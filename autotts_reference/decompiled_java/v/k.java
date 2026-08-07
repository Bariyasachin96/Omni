/*
 * Decompiled with CFR 0.152.
 */
package v;

import u.a;
import u.e;
import u.j;
import v.d;
import v.f;
import v.p;

public class k
extends p {
    public k(e e3) {
        super(e3);
    }

    private void q(f f3) {
        this.h.k.add(f3);
        f3.l.add(this.h);
    }

    @Override
    public void a(d object) {
        a a4 = (a)this.b;
        int n3 = a4.A1();
        object = this.h.l.iterator();
        int n4 = 0;
        int n5 = -1;
        while (object.hasNext()) {
            int n6;
            int n7;
            block6: {
                block5: {
                    n7 = ((f)object.next()).g;
                    if (n5 == -1) break block5;
                    n6 = n5;
                    if (n7 >= n5) break block6;
                }
                n6 = n7;
            }
            n5 = n6;
            if (n4 >= n7) continue;
            n4 = n7;
            n5 = n6;
        }
        if (n3 != 0 && n3 != 2) {
            this.h.d(n4 + a4.B1());
            return;
        }
        this.h.d(n5 + a4.B1());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void d() {
        int n3;
        e e3 = this.b;
        if (!(e3 instanceof a)) return;
        this.h.b = true;
        e3 = (a)e3;
        int n4 = ((a)e3).A1();
        boolean bl = ((a)e3).z1();
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        if (n4 != 0) {
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) return;
                    this.h.e = f.a.i;
                    for (n3 = 0; n3 < ((j)e3).W0; ++n3) {
                        Object object = ((j)e3).V0[n3];
                        if (!bl && ((e)object).X() == 8) continue;
                        object = ((e)object).f.i;
                        ((f)object).k.add(this.h);
                        this.h.l.add(object);
                    }
                    this.q(this.b.f.h);
                    this.q(this.b.f.i);
                    return;
                }
                this.h.e = f.a.h;
                for (n3 = n5; n3 < ((j)e3).W0; ++n3) {
                    Object object = ((j)e3).V0[n3];
                    if (!bl && ((e)object).X() == 8) continue;
                    object = ((e)object).f.h;
                    ((f)object).k.add(this.h);
                    this.h.l.add(object);
                }
                this.q(this.b.f.h);
                this.q(this.b.f.i);
                return;
            }
            this.h.e = f.a.g;
            for (n3 = n6; n3 < ((j)e3).W0; ++n3) {
                Object object = ((j)e3).V0[n3];
                if (!bl && ((e)object).X() == 8) continue;
                object = ((e)object).e.i;
                ((f)object).k.add(this.h);
                this.h.l.add(object);
            }
            this.q(this.b.e.h);
            this.q(this.b.e.i);
            return;
        }
        this.h.e = f.a.f;
        for (n3 = n7; n3 < ((j)e3).W0; ++n3) {
            Object object = ((j)e3).V0[n3];
            if (!bl && ((e)object).X() == 8) continue;
            object = ((e)object).e.h;
            ((f)object).k.add(this.h);
            this.h.l.add(object);
        }
        this.q(this.b.e.h);
        this.q(this.b.e.i);
    }

    @Override
    public void e() {
        e e3 = this.b;
        if (e3 instanceof a) {
            int n3 = ((a)e3).A1();
            if (n3 != 0 && n3 != 1) {
                this.b.s1(this.h.g);
                return;
            }
            this.b.r1(this.h.g);
        }
    }

    @Override
    public void f() {
        this.c = null;
        this.h.c();
    }

    @Override
    public boolean m() {
        return false;
    }
}

