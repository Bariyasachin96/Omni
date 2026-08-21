/*
 * Decompiled with CFR 0.152.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;

public abstract class m
extends RecyclerView.m {
    public boolean g = true;

    public final void A(RecyclerView.d0 d02) {
        this.I(d02);
        this.h(d02);
    }

    public final void B(RecyclerView.d0 d02) {
        this.J(d02);
    }

    public final void C(RecyclerView.d0 d02, boolean bl) {
        this.K(d02, bl);
        this.h(d02);
    }

    public final void D(RecyclerView.d0 d02, boolean bl) {
        this.L(d02, bl);
    }

    public final void E(RecyclerView.d0 d02) {
        this.M(d02);
        this.h(d02);
    }

    public final void F(RecyclerView.d0 d02) {
        this.N(d02);
    }

    public final void G(RecyclerView.d0 d02) {
        this.O(d02);
        this.h(d02);
    }

    public final void H(RecyclerView.d0 d02) {
        this.P(d02);
    }

    public void I(RecyclerView.d0 d02) {
    }

    public void J(RecyclerView.d0 d02) {
    }

    public void K(RecyclerView.d0 d02, boolean bl) {
    }

    public void L(RecyclerView.d0 d02, boolean bl) {
    }

    public void M(RecyclerView.d0 d02) {
    }

    public void N(RecyclerView.d0 d02) {
    }

    public void O(RecyclerView.d0 d02) {
    }

    public void P(RecyclerView.d0 d02) {
    }

    @Override
    public boolean a(RecyclerView.d0 d02, RecyclerView.m.b b3, RecyclerView.m.b b4) {
        int n3;
        int n4;
        if (b3 != null && ((n4 = b3.a) != (n3 = b4.a) || b3.b != b4.b)) {
            return this.y(d02, n4, b3.b, n3, b4.b);
        }
        return this.w(d02);
    }

    @Override
    public boolean b(RecyclerView.d0 d02, RecyclerView.d0 d03, RecyclerView.m.b b3, RecyclerView.m.b b4) {
        int n3;
        int n4;
        int n5 = b3.a;
        int n6 = b3.b;
        if (d03.J()) {
            n4 = b3.a;
            n3 = b3.b;
        } else {
            n4 = b4.a;
            n3 = b4.b;
        }
        return this.x(d02, d03, n5, n6, n4, n3);
    }

    @Override
    public boolean c(RecyclerView.d0 d02, RecyclerView.m.b b3, RecyclerView.m.b b4) {
        int n3 = b3.a;
        int n4 = b3.b;
        b3 = d02.a;
        int n5 = b4 == null ? b3.getLeft() : b4.a;
        int n6 = b4 == null ? b3.getTop() : b4.b;
        if (!(d02.v() || n3 == n5 && n4 == n6)) {
            b3.layout(n5, n6, b3.getWidth() + n5, b3.getHeight() + n6);
            return this.y(d02, n3, n4, n5, n6);
        }
        return this.z(d02);
    }

    @Override
    public boolean d(RecyclerView.d0 d02, RecyclerView.m.b b3, RecyclerView.m.b b4) {
        int n3 = b3.a;
        int n4 = b4.a;
        if (n3 == n4 && b3.b == b4.b) {
            this.E(d02);
            return false;
        }
        return this.y(d02, n3, b3.b, n4, b4.b);
    }

    @Override
    public boolean f(RecyclerView.d0 d02) {
        return !this.g || d02.t();
        {
        }
    }

    public abstract boolean w(RecyclerView.d0 var1);

    public abstract boolean x(RecyclerView.d0 var1, RecyclerView.d0 var2, int var3, int var4, int var5, int var6);

    public abstract boolean y(RecyclerView.d0 var1, int var2, int var3, int var4, int var5);

    public abstract boolean z(RecyclerView.d0 var1);
}

