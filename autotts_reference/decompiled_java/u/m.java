/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.HashSet;
import u.e;
import u.f;
import u.h;
import u.j;
import v.b;

public abstract class m
extends j {
    public int X0 = 0;
    public int Y0 = 0;
    public int Z0 = 0;
    public int a1 = 0;
    public int b1 = 0;
    public int c1 = 0;
    public int d1 = 0;
    public int e1 = 0;
    public boolean f1 = false;
    public int g1 = 0;
    public int h1 = 0;
    public b.a i1 = new b.a();
    public b.b j1 = null;

    public boolean A1(HashSet hashSet) {
        for (int i3 = 0; i3 < this.W0; ++i3) {
            if (!hashSet.contains(this.V0[i3])) continue;
            return true;
        }
        return false;
    }

    public int B1() {
        return this.h1;
    }

    public int C1() {
        return this.g1;
    }

    public int D1() {
        return this.Y0;
    }

    public int E1() {
        return this.d1;
    }

    public int F1() {
        return this.e1;
    }

    public int G1() {
        return this.X0;
    }

    public abstract void H1(int var1, int var2, int var3, int var4);

    public void I1(e e3, e.b b3, int n3, e.b b4, int n4) {
        while (this.j1 == null && this.M() != null) {
            this.j1 = ((f)this.M()).O1();
        }
        b.a a4 = this.i1;
        a4.a = b3;
        a4.b = b4;
        a4.c = n3;
        a4.d = n4;
        this.j1.b(e3, a4);
        e3.p1(this.i1.e);
        e3.Q0(this.i1.f);
        e3.P0(this.i1.h);
        e3.F0(this.i1.g);
    }

    public boolean J1() {
        Object object = this.c0;
        object = object != null ? ((f)object).O1() : null;
        if (object == null) {
            return false;
        }
        for (int i3 = 0; i3 < this.W0; ++i3) {
            e e3 = this.V0[i3];
            if (e3 == null || e3 instanceof h) continue;
            e.b b3 = e3.w(0);
            Object object2 = e3.w(1);
            e.b b4 = e.b.e;
            if (b3 == b4 && e3.w != 1 && object2 == b4 && e3.x != 1) continue;
            e.b b5 = b3;
            if (b3 == b4) {
                b5 = e.b.d;
            }
            b3 = object2;
            if (object2 == b4) {
                b3 = e.b.d;
            }
            object2 = this.i1;
            ((b.a)object2).a = b5;
            ((b.a)object2).b = b3;
            ((b.a)object2).c = e3.Y();
            this.i1.d = e3.z();
            object.b(e3, this.i1);
            e3.p1(this.i1.e);
            e3.Q0(this.i1.f);
            e3.F0(this.i1.g);
        }
        return true;
    }

    public boolean K1() {
        return this.f1;
    }

    public void L1(boolean bl) {
        this.f1 = bl;
    }

    public void M1(int n3, int n4) {
        this.g1 = n3;
        this.h1 = n4;
    }

    public void N1(int n3) {
        this.Z0 = n3;
        this.X0 = n3;
        this.a1 = n3;
        this.Y0 = n3;
        this.b1 = n3;
        this.c1 = n3;
    }

    public void O1(int n3) {
        this.Y0 = n3;
    }

    public void P1(int n3) {
        this.c1 = n3;
    }

    public void Q1(int n3) {
        this.Z0 = n3;
        this.d1 = n3;
    }

    public void R1(int n3) {
        this.a1 = n3;
        this.e1 = n3;
    }

    public void S1(int n3) {
        this.b1 = n3;
        this.d1 = n3;
        this.e1 = n3;
    }

    public void T1(int n3) {
        this.X0 = n3;
    }

    @Override
    public void b(f f3) {
        this.z1();
    }

    public void y1(boolean bl) {
        int n3 = this.b1;
        if (n3 <= 0 && this.c1 <= 0) {
            return;
        }
        if (bl) {
            this.d1 = this.c1;
            this.e1 = n3;
            return;
        }
        this.d1 = n3;
        this.e1 = this.c1;
    }

    public void z1() {
        for (int i3 = 0; i3 < this.W0; ++i3) {
            e e3 = this.V0[i3];
            if (e3 == null) continue;
            e3.Z0(true);
        }
    }
}

