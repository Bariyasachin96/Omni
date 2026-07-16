/*
 * Decompiled with CFR 0.152.
 */
package v;

import u.e;
import u.h;
import v.d;
import v.f;
import v.p;

public class j
extends p {
    public j(e e3) {
        super(e3);
        e3.e.f();
        e3.f.f();
        this.f = ((h)e3).x1();
    }

    @Override
    public void a(d object) {
        object = this.h;
        if (!((f)object).c || ((f)object).j) {
            return;
        }
        f f3 = (f)((f)object).l.get(0);
        object = (h)this.b;
        int n3 = (int)((float)f3.g * ((h)object).A1() + 0.5f);
        this.h.d(n3);
    }

    @Override
    public void d() {
        Object object = (h)this.b;
        int n3 = ((h)object).y1();
        int n4 = ((h)object).z1();
        ((h)object).A1();
        if (((h)object).x1() == 1) {
            if (n3 != -1) {
                this.h.l.add(this.b.c0.e.h);
                this.b.c0.e.h.k.add(this.h);
                this.h.f = n3;
            } else if (n4 != -1) {
                this.h.l.add(this.b.c0.e.i);
                this.b.c0.e.i.k.add(this.h);
                this.h.f = -n4;
            } else {
                object = this.h;
                ((f)object).b = true;
                ((f)object).l.add(this.b.c0.e.i);
                this.b.c0.e.i.k.add(this.h);
            }
            this.q(this.b.e.h);
            this.q(this.b.e.i);
            return;
        }
        if (n3 != -1) {
            this.h.l.add(this.b.c0.f.h);
            this.b.c0.f.h.k.add(this.h);
            this.h.f = n3;
        } else if (n4 != -1) {
            this.h.l.add(this.b.c0.f.i);
            this.b.c0.f.i.k.add(this.h);
            this.h.f = -n4;
        } else {
            object = this.h;
            ((f)object).b = true;
            ((f)object).l.add(this.b.c0.f.i);
            this.b.c0.f.i.k.add(this.h);
        }
        this.q(this.b.f.h);
        this.q(this.b.f.i);
    }

    @Override
    public void e() {
        if (((h)this.b).x1() == 1) {
            this.b.r1(this.h.g);
            return;
        }
        this.b.s1(this.h.g);
    }

    @Override
    public void f() {
        this.h.c();
    }

    @Override
    public boolean m() {
        return false;
    }

    public final void q(f f3) {
        this.h.k.add(f3);
        f3.l.add(this.h);
    }
}

