/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class i {
    public final RecyclerView.p a;
    public int b = Integer.MIN_VALUE;
    public final Rect c = new Rect();

    public i(RecyclerView.p p3) {
        this.a = p3;
    }

    public /* synthetic */ i(RecyclerView.p p3, a a4) {
        this(p3);
    }

    public static i a(RecyclerView.p p3) {
        return new i(p3){

            @Override
            public int d(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.Y(view) + layoutParams.rightMargin;
            }

            @Override
            public int e(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.X(view) + layoutParams.leftMargin + layoutParams.rightMargin;
            }

            @Override
            public int f(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.W(view) + layoutParams.topMargin + layoutParams.bottomMargin;
            }

            @Override
            public int g(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.V(view) - layoutParams.leftMargin;
            }

            @Override
            public int h() {
                return this.a.s0();
            }

            @Override
            public int i() {
                return this.a.s0() - this.a.j0();
            }

            @Override
            public int j() {
                return this.a.j0();
            }

            @Override
            public int k() {
                return this.a.t0();
            }

            @Override
            public int l() {
                return this.a.c0();
            }

            @Override
            public int m() {
                return this.a.i0();
            }

            @Override
            public int n() {
                return this.a.s0() - this.a.i0() - this.a.j0();
            }

            @Override
            public int p(View view) {
                this.a.r0(view, true, this.c);
                return this.c.right;
            }

            @Override
            public int q(View view) {
                this.a.r0(view, true, this.c);
                return this.c.left;
            }

            @Override
            public void r(int n3) {
                this.a.G0(n3);
            }
        };
    }

    public static i b(RecyclerView.p p3, int n3) {
        if (n3 != 0) {
            if (n3 == 1) {
                return i.c(p3);
            }
            throw new IllegalArgumentException("invalid orientation");
        }
        return i.a(p3);
    }

    public static i c(RecyclerView.p p3) {
        return new i(p3){

            @Override
            public int d(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.T(view) + layoutParams.bottomMargin;
            }

            @Override
            public int e(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.W(view) + layoutParams.topMargin + layoutParams.bottomMargin;
            }

            @Override
            public int f(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.X(view) + layoutParams.leftMargin + layoutParams.rightMargin;
            }

            @Override
            public int g(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.a.Z(view) - layoutParams.topMargin;
            }

            @Override
            public int h() {
                return this.a.b0();
            }

            @Override
            public int i() {
                return this.a.b0() - this.a.h0();
            }

            @Override
            public int j() {
                return this.a.h0();
            }

            @Override
            public int k() {
                return this.a.c0();
            }

            @Override
            public int l() {
                return this.a.t0();
            }

            @Override
            public int m() {
                return this.a.k0();
            }

            @Override
            public int n() {
                return this.a.b0() - this.a.k0() - this.a.h0();
            }

            @Override
            public int p(View view) {
                this.a.r0(view, true, this.c);
                return this.c.bottom;
            }

            @Override
            public int q(View view) {
                this.a.r0(view, true, this.c);
                return this.c.top;
            }

            @Override
            public void r(int n3) {
                this.a.H0(n3);
            }
        };
    }

    public abstract int d(View var1);

    public abstract int e(View var1);

    public abstract int f(View var1);

    public abstract int g(View var1);

    public abstract int h();

    public abstract int i();

    public abstract int j();

    public abstract int k();

    public abstract int l();

    public abstract int m();

    public abstract int n();

    public int o() {
        if (Integer.MIN_VALUE == this.b) {
            return 0;
        }
        return this.n() - this.b;
    }

    public abstract int p(View var1);

    public abstract int q(View var1);

    public abstract void r(int var1);

    public void s() {
        this.b = this.n();
    }
}

