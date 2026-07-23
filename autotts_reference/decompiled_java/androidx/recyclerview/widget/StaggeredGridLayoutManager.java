/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.f;
import androidx.recyclerview.widget.g;
import androidx.recyclerview.widget.i;
import androidx.recyclerview.widget.l;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager
extends RecyclerView.p
implements RecyclerView.y.b {
    public boolean A = false;
    public BitSet B;
    public int C = -1;
    public int D = Integer.MIN_VALUE;
    public LazySpanLookup E = new LazySpanLookup();
    public int F = 2;
    public boolean G;
    public boolean H;
    public SavedState I;
    public int J;
    public final Rect K = new Rect();
    public final b L = new b(this);
    public boolean M = false;
    public boolean N = true;
    public int[] O;
    public final Runnable P = new Runnable(this){
        public final StaggeredGridLayoutManager c;
        {
            this.c = staggeredGridLayoutManager;
        }

        @Override
        public void run() {
            this.c.W1();
        }
    };
    public int s = -1;
    public c[] t;
    public i u;
    public i v;
    public int w;
    public int x;
    public final f y;
    public boolean z = false;

    public StaggeredGridLayoutManager(Context object, AttributeSet attributeSet, int n3, int n4) {
        object = RecyclerView.p.m0(object, attributeSet, n3, n4);
        this.L2(object.a);
        this.N2(object.b);
        this.M2(object.c);
        this.y = new f();
        this.e2();
    }

    private void I2() {
        if (this.w != 1 && this.x2()) {
            this.A = this.z ^ true;
            return;
        }
        this.A = this.z;
    }

    private int Y1(RecyclerView.z z3) {
        if (this.O() == 0) {
            return 0;
        }
        return androidx.recyclerview.widget.l.a(z3, this.u, this.i2(this.N ^ true), this.h2(this.N ^ true), this, this.N);
    }

    private int Z1(RecyclerView.z z3) {
        if (this.O() == 0) {
            return 0;
        }
        return androidx.recyclerview.widget.l.b(z3, this.u, this.i2(this.N ^ true), this.h2(this.N ^ true), this, this.N, this.A);
    }

    private int a2(RecyclerView.z z3) {
        if (this.O() == 0) {
            return 0;
        }
        return androidx.recyclerview.widget.l.c(z3, this.u, this.i2(this.N ^ true), this.h2(this.N ^ true), this, this.N);
    }

    private int b2(int n3) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 17) {
                    if (n3 != 33) {
                        if (n3 != 66) {
                            if (n3 != 130) {
                                return Integer.MIN_VALUE;
                            }
                            if (this.w == 1) {
                                return 1;
                            }
                            return Integer.MIN_VALUE;
                        }
                        if (this.w == 0) {
                            return 1;
                        }
                        return Integer.MIN_VALUE;
                    }
                    if (this.w == 1) {
                        return -1;
                    }
                    return Integer.MIN_VALUE;
                }
                if (this.w == 0) {
                    return -1;
                }
                return Integer.MIN_VALUE;
            }
            if (this.w == 1) {
                return 1;
            }
            if (this.x2()) {
                return -1;
            }
            return 1;
        }
        if (this.w == 1) {
            return -1;
        }
        if (this.x2()) {
            return 1;
        }
        return -1;
    }

    private void y2(View view, int n3, int n4, boolean bl) {
        this.o(view, this.K);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n5 = layoutParams.leftMargin;
        Rect rect = this.K;
        n3 = this.V2(n3, n5 + rect.left, layoutParams.rightMargin + rect.right);
        n5 = layoutParams.topMargin;
        rect = this.K;
        n4 = this.V2(n4, n5 + rect.top, layoutParams.bottomMargin + rect.bottom);
        bl = bl ? this.L1(view, n3, n4, layoutParams) : this.J1(view, n3, n4, layoutParams);
        if (bl) {
            view.measure(n3, n4);
        }
    }

    @Override
    public int A(RecyclerView.z z3) {
        return this.a2(z3);
    }

    @Override
    public int A1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        return this.J2(n3, v3, z3);
    }

    /*
     * Unable to fully structure code
     */
    public final void A2(RecyclerView.v var1_1, RecyclerView.z var2_2, boolean var3_3) {
        var8_4 = this.L;
        if ((this.I != null || this.C != -1) && var2_2.b() == 0) {
            this.o1(var1_1);
            var8_4.c();
            return;
        }
        var7_5 = var8_4.e;
        var5_6 = 1;
        var4_7 = var7_5 && this.C == -1 && this.I == null ? 0 : 1;
        if (var4_7 != 0) {
            var8_4.c();
            if (this.I != null) {
                this.R1(var8_4);
            } else {
                this.I2();
                var8_4.c = this.A;
            }
            this.R2(var2_2, var8_4);
            var8_4.e = true;
        }
        if (this.I == null && this.C == -1 && (var8_4.c != this.G || this.x2() != this.H)) {
            this.E.b();
            var8_4.d = true;
        }
        if (this.O() > 0 && ((var9_8 = this.I) == null || var9_8.e < 1)) {
            if (var8_4.d) {
                for (var4_7 = 0; var4_7 < this.s; ++var4_7) {
                    this.t[var4_7].e();
                    var6_9 = var8_4.b;
                    if (var6_9 == -2147483648) continue;
                    this.t[var4_7].v(var6_9);
                }
            } else if (var4_7 == 0 && this.L.f != null) {
                for (var4_7 = 0; var4_7 < this.s; ++var4_7) {
                    var9_8 = this.t[var4_7];
                    var9_8.e();
                    var9_8.v(this.L.f[var4_7]);
                }
            } else {
                for (var4_7 = 0; var4_7 < this.s; ++var4_7) {
                    this.t[var4_7].b(this.A, var8_4.b);
                }
                this.L.d(this.t);
            }
        }
        this.B(var1_1);
        this.y.a = false;
        this.M = false;
        this.T2(this.v.n());
        this.S2(var8_4.a, var2_2);
        if (var8_4.c) {
            this.K2(-1);
            this.f2(var1_1, this.y, var2_2);
            this.K2(1);
            var9_8 = this.y;
            var9_8.c = var8_4.a + var9_8.d;
            this.f2(var1_1, (f)var9_8, var2_2);
        } else {
            this.K2(1);
            this.f2(var1_1, this.y, var2_2);
            this.K2(-1);
            var9_8 = this.y;
            var9_8.c = var8_4.a + var9_8.d;
            this.f2(var1_1, (f)var9_8, var2_2);
        }
        this.H2();
        if (this.O() > 0) {
            if (this.A) {
                this.l2(var1_1, var2_2, true);
                this.m2(var1_1, var2_2, false);
            } else {
                this.m2(var1_1, var2_2, true);
                this.l2(var1_1, var2_2, false);
            }
        }
        if (!var3_3 || var2_2.e() || this.F == 0 || this.O() <= 0 || !this.M && this.v2() == null) ** GOTO lbl-1000
        this.s1(this.P);
        if (this.W1()) {
            var4_7 = var5_6;
        } else lbl-1000:
        // 2 sources

        {
            var4_7 = 0;
        }
        if (var2_2.e()) {
            this.L.c();
        }
        this.G = var8_4.c;
        this.H = this.x2();
        if (var4_7 != 0) {
            this.L.c();
            this.A2(var1_1, var2_2, false);
        }
    }

    @Override
    public void B1(int n3) {
        SavedState savedState = this.I;
        if (savedState != null && savedState.c != n3) {
            savedState.o();
        }
        this.C = n3;
        this.D = Integer.MIN_VALUE;
        this.x1();
    }

    public final boolean B2(int n3) {
        if (this.w == 0) {
            boolean bl = n3 == -1;
            return bl != this.A;
        }
        boolean bl = n3 == -1;
        return (bl = bl == this.A) == this.x2();
    }

    @Override
    public int C1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        return this.J2(n3, v3, z3);
    }

    public void C2(int n3, RecyclerView.z object) {
        int n4;
        int n5;
        if (n3 > 0) {
            n5 = this.o2();
            n4 = 1;
        } else {
            n5 = this.n2();
            n4 = -1;
        }
        this.y.a = true;
        this.S2(n5, (RecyclerView.z)object);
        this.K2(n4);
        object = this.y;
        ((f)object).c = n5 + ((f)object).d;
        ((f)object).b = Math.abs(n3);
    }

    public final void D2(View view) {
        for (int i3 = this.s - 1; i3 >= 0; --i3) {
            this.t[i3].u(view);
        }
    }

    public final void E2(RecyclerView.v v3, f f3) {
        if (f3.a && !f3.i) {
            if (f3.b == 0) {
                if (f3.e == -1) {
                    this.F2(v3, f3.g);
                    return;
                }
                this.G2(v3, f3.f);
                return;
            }
            if (f3.e == -1) {
                int n3 = f3.f;
                n3 = (n3 -= this.q2(n3)) < 0 ? f3.g : f3.g - Math.min(n3, f3.b);
                this.F2(v3, n3);
                return;
            }
            int n4 = this.r2(f3.g) - f3.g;
            if (n4 < 0) {
                n4 = f3.f;
            } else {
                int n5 = f3.f;
                n4 = Math.min(n4, f3.b) + n5;
            }
            this.G2(v3, n4);
        }
    }

    public final void F2(RecyclerView.v v3, int n3) {
        View view;
        block0: for (int i3 = this.O() - 1; i3 >= 0 && this.u.g(view = this.N(i3)) >= n3 && this.u.q(view) >= n3; --i3) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.f) {
                int n4 = 0;
                int n5 = 0;
                while (true) {
                    if (n5 >= this.s) break;
                    if (this.t[n5].a.size() == 1) break block0;
                    ++n5;
                }
                for (int i4 = n4; i4 < this.s; ++i4) {
                    this.t[i4].s();
                }
            } else {
                if (layoutParams.e.a.size() == 1) break;
                layoutParams.e.s();
            }
            this.q1(view, v3);
        }
    }

    @Override
    public void G0(int n3) {
        super.G0(n3);
        for (int i3 = 0; i3 < this.s; ++i3) {
            this.t[i3].r(n3);
        }
    }

    @Override
    public void G1(Rect rect, int n3, int n4) {
        int n5 = this.i0() + this.j0();
        int n6 = this.k0() + this.h0();
        if (this.w == 1) {
            n4 = RecyclerView.p.s(n4, rect.height() + n6, this.f0());
            n6 = RecyclerView.p.s(n3, this.x * this.s + n5, this.g0());
            n3 = n4;
            n4 = n6;
        } else {
            n3 = RecyclerView.p.s(n3, rect.width() + n5, this.g0());
            n6 = RecyclerView.p.s(n4, this.x * this.s + n6, this.f0());
            n4 = n3;
            n3 = n6;
        }
        this.F1(n4, n3);
    }

    public final void G2(RecyclerView.v v3, int n3) {
        block0: while (this.O() > 0) {
            int n4 = 0;
            View view = this.N(0);
            if (this.u.d(view) > n3 || this.u.p(view) > n3) break;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.f) {
                int n5 = 0;
                while (true) {
                    if (n5 >= this.s) break;
                    if (this.t[n5].a.size() == 1) break block0;
                    ++n5;
                }
                for (int i3 = n4; i3 < this.s; ++i3) {
                    this.t[i3].t();
                }
            } else {
                if (layoutParams.e.a.size() == 1) break;
                layoutParams.e.t();
            }
            this.q1(view, v3);
        }
    }

    @Override
    public void H0(int n3) {
        super.H0(n3);
        for (int i3 = 0; i3 < this.s; ++i3) {
            this.t[i3].r(n3);
        }
    }

    public final void H2() {
        if (this.v.k() != 0x40000000) {
            int n3;
            View view;
            int n4;
            int n5 = this.O();
            int n6 = 0;
            float f3 = 0.0f;
            for (n4 = 0; n4 < n5; ++n4) {
                view = this.N(n4);
                float f4 = this.v.e(view);
                if (f4 < f3) continue;
                float f5 = f4;
                if (((LayoutParams)view.getLayoutParams()).e()) {
                    f5 = f4 * 1.0f / (float)this.s;
                }
                f3 = Math.max(f3, f5);
            }
            int n7 = this.x;
            n4 = n3 = Math.round(f3 * (float)this.s);
            if (this.v.k() == Integer.MIN_VALUE) {
                n4 = Math.min(n3, this.v.n());
            }
            this.T2(n4);
            if (this.x != n7) {
                for (n4 = n6; n4 < n5; ++n4) {
                    view = this.N(n4);
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    if (layoutParams.f) continue;
                    if (this.x2() && this.w == 1) {
                        n6 = this.s;
                        n3 = layoutParams.e.e;
                        view.offsetLeftAndRight(-(n6 - 1 - n3) * this.x - -(n6 - 1 - n3) * n7);
                        continue;
                    }
                    n3 = layoutParams.e.e;
                    n6 = this.x * n3;
                    n3 *= n7;
                    if (this.w == 1) {
                        view.offsetLeftAndRight(n6 - n3);
                        continue;
                    }
                    view.offsetTopAndBottom(n6 - n3);
                }
            }
        }
    }

    @Override
    public RecyclerView.LayoutParams I() {
        if (this.w == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override
    public void I0(RecyclerView.h h3, RecyclerView.h h4) {
        this.E.b();
        for (int i3 = 0; i3 < this.s; ++i3) {
            this.t[i3].e();
        }
    }

    @Override
    public RecyclerView.LayoutParams J(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public int J2(int n3, RecyclerView.v v3, RecyclerView.z object) {
        if (this.O() != 0 && n3 != 0) {
            this.C2(n3, (RecyclerView.z)object);
            int n4 = this.f2(v3, this.y, (RecyclerView.z)object);
            if (this.y.b >= n4) {
                n3 = n3 < 0 ? -n4 : n4;
            }
            this.u.r(-n3);
            this.G = this.A;
            object = this.y;
            ((f)object).b = 0;
            this.E2(v3, (f)object);
            return n3;
        }
        return 0;
    }

    @Override
    public RecyclerView.LayoutParams K(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final void K2(int n3) {
        f f3 = this.y;
        f3.e = n3;
        boolean bl = this.A;
        int n4 = 1;
        boolean bl2 = n3 == -1;
        n3 = bl == bl2 ? n4 : -1;
        f3.d = n3;
    }

    public void L2(int n3) {
        if (n3 != 0 && n3 != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        this.l(null);
        if (n3 == this.w) {
            return;
        }
        this.w = n3;
        i i3 = this.u;
        this.u = this.v;
        this.v = i3;
        this.x1();
    }

    @Override
    public void M0(RecyclerView recyclerView, RecyclerView.v v3) {
        super.M0(recyclerView, v3);
        this.s1(this.P);
        for (int i3 = 0; i3 < this.s; ++i3) {
            this.t[i3].e();
        }
        recyclerView.requestLayout();
    }

    @Override
    public void M1(RecyclerView object, RecyclerView.z z3, int n3) {
        object = new g(object.getContext());
        ((RecyclerView.y)object).p(n3);
        this.N1((RecyclerView.y)object);
    }

    public void M2(boolean bl) {
        this.l(null);
        SavedState savedState = this.I;
        if (savedState != null && savedState.j != bl) {
            savedState.j = bl;
        }
        this.z = bl;
        this.x1();
    }

    @Override
    public View N0(View view, int n3, RecyclerView.v v3, RecyclerView.z z3) {
        int n4;
        if (this.O() == 0) {
            return null;
        }
        if ((view = this.G(view)) == null) {
            return null;
        }
        this.I2();
        int n5 = this.b2(n3);
        if (n5 == Integer.MIN_VALUE) {
            return null;
        }
        Object object = (LayoutParams)view.getLayoutParams();
        boolean bl = ((LayoutParams)((Object)object)).f;
        object = ((LayoutParams)((Object)object)).e;
        n3 = n5 == 1 ? this.o2() : this.n2();
        this.S2(n3, z3);
        this.K2(n5);
        f f3 = this.y;
        f3.c = f3.d + n3;
        f3.b = (int)((float)this.u.n() * 0.33333334f);
        f3 = this.y;
        f3.h = true;
        int n6 = 0;
        f3.a = false;
        this.f2(v3, f3, z3);
        this.G = this.A;
        if (!bl && (v3 = ((c)object).m(n3, n5)) != null && v3 != view) {
            return v3;
        }
        if (this.B2(n5)) {
            for (n4 = this.s - 1; n4 >= 0; --n4) {
                v3 = this.t[n4].m(n3, n5);
                if (v3 == null || v3 == view) continue;
                return v3;
            }
        } else {
            for (n4 = 0; n4 < this.s; ++n4) {
                v3 = this.t[n4].m(n3, n5);
                if (v3 == null || v3 == view) continue;
                return v3;
            }
        }
        int n7 = this.z;
        n3 = n5 == -1 ? 1 : 0;
        n3 = (n7 ^ 1) == n3 ? 1 : 0;
        if (!bl && (v3 = this.H(n4 = n3 != 0 ? ((c)object).f() : ((c)object).g())) != null && v3 != view) {
            return v3;
        }
        if (this.B2(n5)) {
            for (n4 = this.s - 1; n4 >= 0; --n4) {
                if (n4 == ((c)object).e || (v3 = this.H(n6 = n3 != 0 ? this.t[n4].f() : this.t[n4].g())) == null || v3 == view) continue;
                return v3;
            }
        } else {
            for (n4 = n6; n4 < this.s; ++n4) {
                n6 = n3 != 0 ? this.t[n4].f() : this.t[n4].g();
                v3 = this.H(n6);
                if (v3 == null || v3 == view) continue;
                return v3;
            }
        }
        return null;
    }

    public void N2(int n3) {
        this.l(null);
        if (n3 != this.s) {
            this.w2();
            this.s = n3;
            this.B = new BitSet(this.s);
            this.t = new c[this.s];
            for (n3 = 0; n3 < this.s; ++n3) {
                this.t[n3] = new c(this, n3);
            }
            this.x1();
        }
    }

    @Override
    public void O0(AccessibilityEvent accessibilityEvent) {
        super.O0(accessibilityEvent);
        if (this.O() > 0) {
            View view = this.i2(false);
            View view2 = this.h2(false);
            if (view != null && view2 != null) {
                int n3;
                int n4 = this.l0(view);
                if (n4 < (n3 = this.l0(view2))) {
                    accessibilityEvent.setFromIndex(n4);
                    accessibilityEvent.setToIndex(n3);
                    return;
                }
                accessibilityEvent.setFromIndex(n3);
                accessibilityEvent.setToIndex(n4);
            }
        }
    }

    public final void O2(int n3, int n4) {
        for (int i3 = 0; i3 < this.s; ++i3) {
            if (this.t[i3].a.isEmpty()) continue;
            this.U2(this.t[i3], n3, n4);
        }
    }

    @Override
    public boolean P1() {
        return this.I == null;
    }

    public final boolean P2(RecyclerView.z z3, b b3) {
        int n3 = this.G ? this.k2(z3.b()) : this.g2(z3.b());
        b3.a = n3;
        b3.b = Integer.MIN_VALUE;
        return true;
    }

    public final void Q1(View view) {
        for (int i3 = this.s - 1; i3 >= 0; --i3) {
            this.t[i3].a(view);
        }
    }

    public boolean Q2(RecyclerView.z object, b b3) {
        int n3;
        boolean bl = ((RecyclerView.z)object).e();
        boolean bl2 = false;
        if (!bl && (n3 = this.C) != -1) {
            if (n3 >= 0 && n3 < ((RecyclerView.z)object).b()) {
                object = this.I;
                if (object != null && ((SavedState)object).c != -1 && ((SavedState)object).e >= 1) {
                    b3.b = Integer.MIN_VALUE;
                    b3.a = this.C;
                } else {
                    object = this.H(this.C);
                    if (object != null) {
                        n3 = this.A ? this.o2() : this.n2();
                        b3.a = n3;
                        if (this.D != Integer.MIN_VALUE) {
                            b3.b = b3.c ? this.u.i() - this.D - this.u.d((View)object) : this.u.m() + this.D - this.u.g((View)object);
                            return true;
                        }
                        if (this.u.e((View)object) > this.u.n()) {
                            n3 = b3.c ? this.u.i() : this.u.m();
                            b3.b = n3;
                            return true;
                        }
                        n3 = this.u.g((View)object) - this.u.m();
                        if (n3 < 0) {
                            b3.b = -n3;
                            return true;
                        }
                        n3 = this.u.i() - this.u.d((View)object);
                        if (n3 < 0) {
                            b3.b = n3;
                            return true;
                        }
                        b3.b = Integer.MIN_VALUE;
                    } else {
                        b3.a = n3 = this.C;
                        int n4 = this.D;
                        if (n4 == Integer.MIN_VALUE) {
                            if (this.V1(n3) == 1) {
                                bl2 = true;
                            }
                            b3.c = bl2;
                            b3.a();
                        } else {
                            b3.b(n4);
                        }
                        b3.d = true;
                    }
                }
                return true;
            }
            this.C = -1;
            this.D = Integer.MIN_VALUE;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void R1(b object) {
        SavedState savedState = this.I;
        int n3 = savedState.e;
        if (n3 > 0) {
            if (n3 != this.s) {
                savedState.p();
                savedState = this.I;
                savedState.c = savedState.d;
            } else {
                for (n3 = 0; n3 < this.s; ++n3) {
                    int n4;
                    this.t[n3].e();
                    savedState = this.I;
                    int n5 = n4 = savedState.f[n3];
                    if (n4 != Integer.MIN_VALUE) {
                        n5 = savedState.k ? this.u.i() : this.u.m();
                        n5 = n4 + n5;
                    }
                    this.t[n3].v(n5);
                }
            }
        }
        savedState = this.I;
        this.H = savedState.l;
        this.M2(savedState.j);
        this.I2();
        savedState = this.I;
        n3 = savedState.c;
        if (n3 != -1) {
            this.C = n3;
            ((b)object).c = savedState.k;
        } else {
            ((b)object).c = this.A;
        }
        if (savedState.g > 1) {
            object = this.E;
            ((LazySpanLookup)object).a = savedState.h;
            ((LazySpanLookup)object).b = savedState.i;
        }
    }

    public void R2(RecyclerView.z z3, b b3) {
        if (this.Q2(z3, b3) || this.P2(z3, b3)) {
            return;
        }
        b3.a();
        b3.a = 0;
    }

    public boolean S1() {
        int n3 = this.t[0].l(Integer.MIN_VALUE);
        for (int i3 = 1; i3 < this.s; ++i3) {
            if (this.t[i3].l(Integer.MIN_VALUE) == n3) continue;
            return false;
        }
        return true;
    }

    public final void S2(int n3, RecyclerView.z object) {
        boolean bl;
        int n4;
        f f3 = this.y;
        boolean bl2 = false;
        f3.b = 0;
        f3.c = n3;
        if (this.B0() && (n4 = ((RecyclerView.z)object).c()) != -1) {
            boolean bl3 = this.A;
            bl = n4 < n3;
            if (bl3 == bl) {
                n3 = this.u.n();
                n4 = 0;
            } else {
                n4 = this.u.n();
                n3 = 0;
            }
        } else {
            n3 = 0;
            n4 = 0;
        }
        if (this.R()) {
            this.y.f = this.u.m() - n4;
            this.y.g = this.u.i() + n3;
        } else {
            this.y.g = this.u.h() + n3;
            this.y.f = -n4;
        }
        object = this.y;
        ((f)object).h = false;
        ((f)object).a = true;
        bl = bl2;
        if (this.u.k() == 0) {
            bl = bl2;
            if (this.u.h() == 0) {
                bl = true;
            }
        }
        ((f)object).i = bl;
    }

    public boolean T1() {
        int n3 = this.t[0].p(Integer.MIN_VALUE);
        for (int i3 = 1; i3 < this.s; ++i3) {
            if (this.t[i3].p(Integer.MIN_VALUE) == n3) continue;
            return false;
        }
        return true;
    }

    public void T2(int n3) {
        this.x = n3 / this.s;
        this.J = View.MeasureSpec.makeMeasureSpec((int)n3, (int)this.v.k());
    }

    public final void U1(View view, LayoutParams layoutParams, f f3) {
        if (f3.e == 1) {
            if (layoutParams.f) {
                this.Q1(view);
                return;
            }
            layoutParams.e.a(view);
            return;
        }
        if (layoutParams.f) {
            this.D2(view);
            return;
        }
        layoutParams.e.u(view);
    }

    public final void U2(c c3, int n3, int n4) {
        int n5 = c3.j();
        if (n3 == -1) {
            if (c3.o() + n5 <= n4) {
                this.B.set(c3.e, false);
                return;
            }
        } else if (c3.k() - n5 >= n4) {
            this.B.set(c3.e, false);
        }
    }

    @Override
    public void V0(RecyclerView recyclerView, int n3, int n4) {
        this.u2(n3, n4, 1);
    }

    public final int V1(int n3) {
        if (this.O() == 0) {
            if (this.A) {
                return 1;
            }
            return -1;
        }
        boolean bl = n3 < this.n2();
        if (bl != this.A) {
            return -1;
        }
        return 1;
    }

    public final int V2(int n3, int n4, int n5) {
        int n6;
        if (n4 == 0 && n5 == 0 || (n6 = View.MeasureSpec.getMode((int)n3)) != Integer.MIN_VALUE && n6 != 0x40000000) {
            return n3;
        }
        return View.MeasureSpec.makeMeasureSpec((int)Math.max(0, View.MeasureSpec.getSize((int)n3) - n4 - n5), (int)n6);
    }

    @Override
    public void W0(RecyclerView recyclerView) {
        this.E.b();
        this.x1();
    }

    public boolean W1() {
        if (this.O() != 0 && this.F != 0 && this.v0()) {
            LazySpanLookup.FullSpanItem fullSpanItem;
            int n3;
            int n4;
            if (this.A) {
                n4 = this.o2();
                n3 = this.n2();
            } else {
                n4 = this.n2();
                n3 = this.o2();
            }
            if (n4 == 0 && this.v2() != null) {
                this.E.b();
                this.y1();
                this.x1();
                return true;
            }
            if (!this.M) {
                return false;
            }
            Object object = this.E;
            int n5 = this.A ? -1 : 1;
            if ((fullSpanItem = ((LazySpanLookup)object).e(n4, ++n3, n5, true)) == null) {
                this.M = false;
                this.E.d(n3);
                return false;
            }
            object = this.E.e(n4, fullSpanItem.c, n5 * -1, true);
            if (object == null) {
                this.E.d(fullSpanItem.c);
            } else {
                this.E.d(((LazySpanLookup.FullSpanItem)object).c + 1);
            }
            this.y1();
            this.x1();
            return true;
        }
        return false;
    }

    @Override
    public void X0(RecyclerView recyclerView, int n3, int n4, int n5) {
        this.u2(n3, n4, 8);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean X1(c c3) {
        boolean bl;
        if (this.A) {
            if (c3.k() >= this.u.i()) return false;
            ArrayList arrayList = c3.a;
            bl = c3.n((View)((View)arrayList.get((int)(arrayList.size() - 1)))).f;
            return bl ^ true;
        }
        if (c3.o() <= this.u.m()) return false;
        bl = c3.n((View)((View)c3.a.get((int)0))).f;
        return bl ^ true;
    }

    @Override
    public void Y0(RecyclerView recyclerView, int n3, int n4) {
        this.u2(n3, n4, 2);
    }

    @Override
    public void a1(RecyclerView recyclerView, int n3, int n4, Object object) {
        this.u2(n3, n4, 4);
    }

    @Override
    public void b1(RecyclerView.v v3, RecyclerView.z z3) {
        this.A2(v3, z3, true);
    }

    @Override
    public void c1(RecyclerView.z z3) {
        super.c1(z3);
        this.C = -1;
        this.D = Integer.MIN_VALUE;
        this.I = null;
        this.L.c();
    }

    public final LazySpanLookup.FullSpanItem c2(int n3) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.e = new int[this.s];
        for (int i3 = 0; i3 < this.s; ++i3) {
            fullSpanItem.e[i3] = n3 - this.t[i3].l(n3);
        }
        return fullSpanItem;
    }

    @Override
    public PointF d(int n3) {
        n3 = this.V1(n3);
        PointF pointF = new PointF();
        if (n3 == 0) {
            return null;
        }
        if (this.w == 0) {
            pointF.x = n3;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = n3;
        return pointF;
    }

    public final LazySpanLookup.FullSpanItem d2(int n3) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.e = new int[this.s];
        for (int i3 = 0; i3 < this.s; ++i3) {
            fullSpanItem.e[i3] = this.t[i3].p(n3) - n3;
        }
        return fullSpanItem;
    }

    public final void e2() {
        this.u = androidx.recyclerview.widget.i.b(this, this.w);
        this.v = androidx.recyclerview.widget.i.b(this, 1 - this.w);
    }

    public final int f2(RecyclerView.v v3, f f3, RecyclerView.z z3) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = this;
        staggeredGridLayoutManager.B.set(0, staggeredGridLayoutManager.s, true);
        int n3 = staggeredGridLayoutManager.y.i ? (f3.e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE) : (f3.e == 1 ? f3.g + f3.b : f3.f - f3.b);
        staggeredGridLayoutManager.O2(f3.e, n3);
        int n4 = staggeredGridLayoutManager.A ? staggeredGridLayoutManager.u.i() : staggeredGridLayoutManager.u.m();
        int n5 = 0;
        while (f3.a(z3) && (staggeredGridLayoutManager.y.i || !staggeredGridLayoutManager.B.isEmpty())) {
            LazySpanLookup.FullSpanItem fullSpanItem;
            int n6;
            int n7;
            int n8;
            c c3;
            View view = f3.b(v3);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n9 = layoutParams.a();
            n5 = staggeredGridLayoutManager.E.g(n9);
            int n10 = n5 == -1 ? 1 : 0;
            if (n10 != 0) {
                c3 = layoutParams.f ? staggeredGridLayoutManager.t[0] : staggeredGridLayoutManager.t2(f3);
                staggeredGridLayoutManager.E.n(n9, c3);
            } else {
                c3 = staggeredGridLayoutManager.t[n5];
            }
            layoutParams.e = c3;
            if (f3.e == 1) {
                staggeredGridLayoutManager.i(view);
            } else {
                staggeredGridLayoutManager.j(view, 0);
            }
            staggeredGridLayoutManager.z2(view, layoutParams, false);
            if (f3.e == 1) {
                n5 = layoutParams.f ? staggeredGridLayoutManager.p2(n4) : c3.l(n4);
                n8 = staggeredGridLayoutManager.u.e(view) + n5;
                n7 = n5;
                n6 = n8;
                if (n10 != 0) {
                    n7 = n5;
                    n6 = n8;
                    if (layoutParams.f) {
                        fullSpanItem = staggeredGridLayoutManager.c2(n5);
                        fullSpanItem.d = -1;
                        fullSpanItem.c = n9;
                        staggeredGridLayoutManager.E.a(fullSpanItem);
                        n7 = n5;
                        n6 = n8;
                    }
                }
            } else {
                n5 = layoutParams.f ? staggeredGridLayoutManager.s2(n4) : c3.p(n4);
                n7 = n8 = n5 - staggeredGridLayoutManager.u.e(view);
                n6 = n5;
                if (n10 != 0) {
                    n7 = n8;
                    n6 = n5;
                    if (layoutParams.f) {
                        fullSpanItem = staggeredGridLayoutManager.d2(n5);
                        fullSpanItem.d = 1;
                        fullSpanItem.c = n9;
                        staggeredGridLayoutManager.E.a(fullSpanItem);
                        n6 = n5;
                        n7 = n8;
                    }
                }
            }
            if (layoutParams.f && f3.d == -1) {
                if (n10 != 0) {
                    staggeredGridLayoutManager.M = true;
                } else {
                    boolean bl = f3.e == 1 ? staggeredGridLayoutManager.S1() : staggeredGridLayoutManager.T1();
                    if (bl ^ true) {
                        fullSpanItem = staggeredGridLayoutManager.E.f(n9);
                        if (fullSpanItem != null) {
                            fullSpanItem.f = true;
                        }
                        staggeredGridLayoutManager.M = true;
                    }
                }
            }
            staggeredGridLayoutManager.U1(view, layoutParams, f3);
            if (staggeredGridLayoutManager.x2() && staggeredGridLayoutManager.w == 1) {
                n5 = layoutParams.f ? staggeredGridLayoutManager.v.i() : staggeredGridLayoutManager.v.i() - (staggeredGridLayoutManager.s - 1 - c3.e) * staggeredGridLayoutManager.x;
                n10 = n5 - staggeredGridLayoutManager.v.e(view);
                n8 = n5;
            } else {
                n5 = layoutParams.f ? staggeredGridLayoutManager.v.m() : c3.e * staggeredGridLayoutManager.x + staggeredGridLayoutManager.v.m();
                n8 = staggeredGridLayoutManager.v.e(view) + n5;
                n10 = n5;
            }
            if (staggeredGridLayoutManager.w == 1) {
                staggeredGridLayoutManager.D0(view, n10, n7, n8, n6);
                staggeredGridLayoutManager = this;
            } else {
                staggeredGridLayoutManager.D0(view, n7, n10, n6, n8);
            }
            if (layoutParams.f) {
                staggeredGridLayoutManager.O2(staggeredGridLayoutManager.y.e, n3);
            } else {
                staggeredGridLayoutManager.U2(c3, staggeredGridLayoutManager.y.e, n3);
            }
            staggeredGridLayoutManager.E2(v3, staggeredGridLayoutManager.y);
            if (staggeredGridLayoutManager.y.h && view.hasFocusable()) {
                if (layoutParams.f) {
                    staggeredGridLayoutManager.B.clear();
                } else {
                    staggeredGridLayoutManager.B.set(c3.e, false);
                }
            }
            n5 = 1;
        }
        if (n5 == 0) {
            staggeredGridLayoutManager.E2(v3, staggeredGridLayoutManager.y);
        }
        if (staggeredGridLayoutManager.y.e == -1) {
            n3 = staggeredGridLayoutManager.s2(staggeredGridLayoutManager.u.m());
            n3 = staggeredGridLayoutManager.u.m() - n3;
        } else {
            n3 = staggeredGridLayoutManager.p2(staggeredGridLayoutManager.u.i()) - staggeredGridLayoutManager.u.i();
        }
        if (n3 > 0) {
            return Math.min(f3.b, n3);
        }
        return 0;
    }

    @Override
    public void g1(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            parcelable = (SavedState)parcelable;
            this.I = parcelable;
            if (this.C != -1) {
                parcelable.o();
                this.I.p();
            }
            this.x1();
        }
    }

    public final int g2(int n3) {
        int n4 = this.O();
        for (int i3 = 0; i3 < n4; ++i3) {
            int n5 = this.l0(this.N(i3));
            if (n5 < 0 || n5 >= n3) continue;
            return n5;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Parcelable h1() {
        int[] nArray;
        if (this.I != null) {
            return new SavedState(this.I);
        }
        SavedState savedState = new SavedState();
        savedState.j = this.z;
        savedState.k = this.G;
        savedState.l = this.H;
        LazySpanLookup lazySpanLookup = this.E;
        int n3 = 0;
        if (lazySpanLookup != null && (nArray = lazySpanLookup.a) != null) {
            savedState.h = nArray;
            savedState.g = nArray.length;
            savedState.i = lazySpanLookup.b;
        } else {
            savedState.g = 0;
        }
        if (this.O() <= 0) {
            savedState.c = -1;
            savedState.d = -1;
            savedState.e = 0;
            return savedState;
        }
        int n4 = this.G ? this.o2() : this.n2();
        savedState.c = n4;
        savedState.d = this.j2();
        savedState.e = n4 = this.s;
        savedState.f = new int[n4];
        while (true) {
            block10: {
                int n5;
                block11: {
                    block9: {
                        if (n3 >= this.s) {
                            return savedState;
                        }
                        if (!this.G) break block9;
                        n4 = n5 = this.t[n3].l(Integer.MIN_VALUE);
                        if (n5 == Integer.MIN_VALUE) break block10;
                        n4 = this.u.i();
                        break block11;
                    }
                    n4 = n5 = this.t[n3].p(Integer.MIN_VALUE);
                    if (n5 == Integer.MIN_VALUE) break block10;
                    n4 = this.u.m();
                }
                n4 = n5 - n4;
            }
            savedState.f[n3] = n4;
            ++n3;
        }
    }

    public View h2(boolean bl) {
        int n3 = this.u.m();
        int n4 = this.u.i();
        View view = null;
        for (int i3 = this.O() - 1; i3 >= 0; --i3) {
            View view2 = this.N(i3);
            int n5 = this.u.g(view2);
            int n6 = this.u.d(view2);
            View view3 = view;
            if (n6 > n3) {
                if (n5 >= n4) {
                    view3 = view;
                } else if (n6 > n4 && bl) {
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                } else {
                    return view2;
                }
            }
            view = view3;
        }
        return view;
    }

    @Override
    public void i1(int n3) {
        if (n3 == 0) {
            this.W1();
        }
    }

    public View i2(boolean bl) {
        int n3 = this.u.m();
        int n4 = this.u.i();
        int n5 = this.O();
        View view = null;
        for (int i3 = 0; i3 < n5; ++i3) {
            View view2 = this.N(i3);
            int n6 = this.u.g(view2);
            View view3 = view;
            if (this.u.d(view2) > n3) {
                if (n6 >= n4) {
                    view3 = view;
                } else if (n6 < n3 && bl) {
                    view3 = view;
                    if (view == null) {
                        view3 = view2;
                    }
                } else {
                    return view2;
                }
            }
            view = view3;
        }
        return view;
    }

    public int j2() {
        View view = this.A ? this.h2(true) : this.i2(true);
        if (view == null) {
            return -1;
        }
        return this.l0(view);
    }

    public final int k2(int n3) {
        for (int i3 = this.O() - 1; i3 >= 0; --i3) {
            int n4 = this.l0(this.N(i3));
            if (n4 < 0 || n4 >= n3) continue;
            return n4;
        }
        return 0;
    }

    @Override
    public void l(String string) {
        if (this.I == null) {
            super.l(string);
        }
    }

    public final void l2(RecyclerView.v v3, RecyclerView.z z3, boolean bl) {
        int n3 = this.p2(Integer.MIN_VALUE);
        if (n3 != Integer.MIN_VALUE && (n3 = this.u.i() - n3) > 0) {
            n3 -= -this.J2(-n3, v3, z3);
            if (bl && n3 > 0) {
                this.u.r(n3);
            }
        }
    }

    public final void m2(RecyclerView.v v3, RecyclerView.z z3, boolean bl) {
        int n3 = this.s2(Integer.MAX_VALUE);
        if (n3 != Integer.MAX_VALUE && (n3 -= this.u.m()) > 0) {
            n3 -= this.J2(n3, v3, z3);
            if (bl && n3 > 0) {
                this.u.r(-n3);
            }
        }
    }

    public int n2() {
        if (this.O() == 0) {
            return 0;
        }
        return this.l0(this.N(0));
    }

    public int o2() {
        int n3 = this.O();
        if (n3 == 0) {
            return 0;
        }
        return this.l0(this.N(n3 - 1));
    }

    @Override
    public boolean p() {
        return this.w == 0;
    }

    public final int p2(int n3) {
        int n4 = this.t[0].l(n3);
        for (int i3 = 1; i3 < this.s; ++i3) {
            int n5 = this.t[i3].l(n3);
            int n6 = n4;
            if (n5 > n4) {
                n6 = n5;
            }
            n4 = n6;
        }
        return n4;
    }

    @Override
    public boolean q() {
        return this.w == 1;
    }

    public final int q2(int n3) {
        int n4 = this.t[0].p(n3);
        for (int i3 = 1; i3 < this.s; ++i3) {
            int n5 = this.t[i3].p(n3);
            int n6 = n4;
            if (n5 > n4) {
                n6 = n5;
            }
            n4 = n6;
        }
        return n4;
    }

    @Override
    public boolean r(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final int r2(int n3) {
        int n4 = this.t[0].l(n3);
        for (int i3 = 1; i3 < this.s; ++i3) {
            int n5 = this.t[i3].l(n3);
            int n6 = n4;
            if (n5 < n4) {
                n6 = n5;
            }
            n4 = n6;
        }
        return n4;
    }

    public final int s2(int n3) {
        int n4 = this.t[0].p(n3);
        for (int i3 = 1; i3 < this.s; ++i3) {
            int n5 = this.t[i3].p(n3);
            int n6 = n4;
            if (n5 < n4) {
                n6 = n5;
            }
            n4 = n6;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void t(int n3, int n4, RecyclerView.z z3, RecyclerView.p.c c3) {
        if (this.w != 0) {
            n3 = n4;
        }
        if (this.O() == 0) return;
        if (n3 == 0) {
            return;
        }
        this.C2(n3, z3);
        int[] nArray = this.O;
        if (nArray == null || nArray.length < this.s) {
            this.O = new int[this.s];
        }
        int n5 = 0;
        n3 = 0;
        for (n4 = 0; n4 < this.s; ++n4) {
            int n6;
            int n7;
            f f3 = this.y;
            if (f3.d == -1) {
                n7 = f3.f;
                n6 = this.t[n4].p(n7);
            } else {
                n7 = this.t[n4].l(f3.g);
                n6 = this.y.g;
            }
            n6 = n7 - n6;
            n7 = n3;
            if (n6 >= 0) {
                this.O[n3] = n6;
                n7 = n3 + 1;
            }
            n3 = n7;
        }
        Arrays.sort(this.O, 0, n3);
        n4 = n5;
        while (n4 < n3) {
            if (!this.y.a(z3)) return;
            c3.a(this.y.c, this.O[n4]);
            f f4 = this.y;
            f4.c += f4.d;
            ++n4;
        }
    }

    public final c t2(f object) {
        int n3;
        int n4;
        int n5;
        if (this.B2(((f)object).e)) {
            n5 = this.s - 1;
            n4 = -1;
            n3 = -1;
        } else {
            n4 = this.s;
            n5 = 0;
            n3 = 1;
        }
        int n6 = ((f)object).e;
        c c3 = null;
        object = null;
        if (n6 == 1) {
            int n7 = this.u.m();
            int n8 = Integer.MAX_VALUE;
            while (n5 != n4) {
                c3 = this.t[n5];
                int n9 = c3.l(n7);
                n6 = n8;
                if (n9 < n8) {
                    object = c3;
                    n6 = n9;
                }
                n5 += n3;
                n8 = n6;
            }
            return object;
        }
        int n10 = this.u.i();
        n6 = Integer.MIN_VALUE;
        object = c3;
        while (n5 != n4) {
            c3 = this.t[n5];
            int n11 = c3.p(n10);
            int n12 = n6;
            if (n11 > n6) {
                object = c3;
                n12 = n11;
            }
            n5 += n3;
            n6 = n12;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void u2(int n3, int n4, int n5) {
        int n6;
        int n7;
        int n8;
        block9: {
            block8: {
                block7: {
                    block6: {
                        block4: {
                            block5: {
                                n8 = this.A ? this.o2() : this.n2();
                                if (n5 != 8) break block4;
                                if (n3 >= n4) break block5;
                                n7 = n4 + 1;
                                break block6;
                            }
                            n6 = n3 + 1;
                            n7 = n4;
                            break block7;
                        }
                        n7 = n3 + n4;
                    }
                    int n9 = n3;
                    n6 = n7;
                    n7 = n9;
                }
                this.E.h(n7);
                if (n5 == 1) break block8;
                if (n5 != 2) {
                    if (n5 == 8) {
                        this.E.k(n3, 1);
                        this.E.j(n4, 1);
                    }
                    break block9;
                } else {
                    this.E.k(n3, n4);
                }
                break block9;
            }
            this.E.j(n3, n4);
        }
        if (n6 <= n8) {
            return;
        }
        n3 = this.A ? this.n2() : this.o2();
        if (n7 > n3) return;
        this.x1();
    }

    @Override
    public int v(RecyclerView.z z3) {
        return this.Y1(z3);
    }

    public View v2() {
        int n3 = this.O();
        int n4 = n3 - 1;
        BitSet bitSet = new BitSet(this.s);
        bitSet.set(0, this.s, true);
        int n5 = this.w;
        int n6 = -1;
        n5 = n5 == 1 && this.x2() ? 1 : -1;
        if (this.A) {
            n3 = -1;
        } else {
            n4 = 0;
        }
        int n7 = n4;
        if (n4 < n3) {
            n6 = 1;
            n7 = n4;
        }
        while (n7 != n3) {
            block9: {
                View view;
                block8: {
                    int n8;
                    Object object;
                    LayoutParams layoutParams;
                    block11: {
                        block10: {
                            block7: {
                                view = this.N(n7);
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (!bitSet.get(layoutParams.e.e)) break block7;
                                if (this.X1(layoutParams.e)) break block8;
                                bitSet.clear(layoutParams.e.e);
                            }
                            if (layoutParams.f || (n4 = n7 + n6) == n3) break block9;
                            object = this.N(n4);
                            if (!this.A) break block10;
                            n8 = this.u.d(view);
                            if (n8 < (n4 = this.u.d((View)object))) break block8;
                            if (n8 != n4) break block9;
                            break block11;
                        }
                        n8 = this.u.g(view);
                        if (n8 > (n4 = this.u.g((View)object))) break block8;
                        if (n8 != n4) break block9;
                    }
                    object = (LayoutParams)object.getLayoutParams();
                    n4 = layoutParams.e.e - object.e.e < 0 ? 1 : 0;
                    n8 = n5 < 0 ? 1 : 0;
                    if (n4 == n8) break block9;
                }
                return view;
            }
            n7 += n6;
        }
        return null;
    }

    @Override
    public int w(RecyclerView.z z3) {
        return this.Z1(z3);
    }

    @Override
    public boolean w0() {
        return this.F != 0;
    }

    public void w2() {
        this.E.b();
        this.x1();
    }

    @Override
    public int x(RecyclerView.z z3) {
        return this.a2(z3);
    }

    public boolean x2() {
        return this.d0() == 1;
    }

    @Override
    public int y(RecyclerView.z z3) {
        return this.Y1(z3);
    }

    @Override
    public int z(RecyclerView.z z3) {
        return this.Z1(z3);
    }

    public final void z2(View view, LayoutParams layoutParams, boolean bl) {
        if (layoutParams.f) {
            if (this.w == 1) {
                this.y2(view, this.J, RecyclerView.p.P(this.b0(), this.c0(), this.k0() + this.h0(), layoutParams.height, true), bl);
                return;
            }
            this.y2(view, RecyclerView.p.P(this.s0(), this.t0(), this.i0() + this.j0(), layoutParams.width, true), this.J, bl);
            return;
        }
        if (this.w == 1) {
            this.y2(view, RecyclerView.p.P(this.x, this.t0(), 0, layoutParams.width, false), RecyclerView.p.P(this.b0(), this.c0(), this.k0() + this.h0(), layoutParams.height, true), bl);
            return;
        }
        this.y2(view, RecyclerView.p.P(this.s0(), this.t0(), this.i0() + this.j0(), layoutParams.width, true), RecyclerView.p.P(this.x, this.c0(), 0, layoutParams.height, false), bl);
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public c e;
        public boolean f;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public boolean e() {
            return this.f;
        }
    }

    public static class LazySpanLookup {
        public int[] a;
        public List b;

        public void a(FullSpanItem fullSpanItem) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int n3 = this.b.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                FullSpanItem fullSpanItem2 = (FullSpanItem)this.b.get(i3);
                if (fullSpanItem2.c == fullSpanItem.c) {
                    this.b.remove(i3);
                }
                if (fullSpanItem2.c < fullSpanItem.c) continue;
                this.b.add(i3, fullSpanItem);
                return;
            }
            this.b.add(fullSpanItem);
        }

        public void b() {
            int[] nArray = this.a;
            if (nArray != null) {
                Arrays.fill(nArray, -1);
            }
            this.b = null;
        }

        public void c(int n3) {
            int[] nArray = this.a;
            if (nArray == null) {
                this.a = nArray = new int[Math.max(n3, 10) + 1];
                Arrays.fill(nArray, -1);
                return;
            }
            if (n3 >= nArray.length) {
                int[] nArray2 = new int[this.o(n3)];
                this.a = nArray2;
                System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
                nArray2 = this.a;
                Arrays.fill(nArray2, nArray.length, nArray2.length, -1);
            }
        }

        public int d(int n3) {
            List list = this.b;
            if (list != null) {
                for (int i3 = list.size() - 1; i3 >= 0; --i3) {
                    if (((FullSpanItem)this.b.get((int)i3)).c < n3) continue;
                    this.b.remove(i3);
                }
            }
            return this.h(n3);
        }

        public FullSpanItem e(int n3, int n4, int n5, boolean bl) {
            Object object = this.b;
            if (object == null) {
                return null;
            }
            int n6 = object.size();
            for (int i3 = 0; i3 < n6; ++i3) {
                object = (FullSpanItem)this.b.get(i3);
                int n7 = ((FullSpanItem)object).c;
                if (n7 >= n4) {
                    return null;
                }
                if (n7 < n3 || n5 != 0 && ((FullSpanItem)object).d != n5 && (!bl || !((FullSpanItem)object).f)) continue;
                return object;
            }
            return null;
        }

        public FullSpanItem f(int n3) {
            Object object = this.b;
            if (object == null) {
                return null;
            }
            for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                object = (FullSpanItem)this.b.get(i3);
                if (((FullSpanItem)object).c != n3) continue;
                return object;
            }
            return null;
        }

        public int g(int n3) {
            int[] nArray = this.a;
            if (nArray != null && n3 < nArray.length) {
                return nArray[n3];
            }
            return -1;
        }

        public int h(int n3) {
            int[] nArray = this.a;
            if (nArray == null) {
                return -1;
            }
            if (n3 >= nArray.length) {
                return -1;
            }
            int n4 = this.i(n3);
            if (n4 == -1) {
                nArray = this.a;
                Arrays.fill(nArray, n3, nArray.length, -1);
                return this.a.length;
            }
            n4 = Math.min(n4 + 1, this.a.length);
            Arrays.fill(this.a, n3, n4, -1);
            return n4;
        }

        public final int i(int n3) {
            int n4;
            FullSpanItem fullSpanItem;
            block5: {
                if (this.b == null) {
                    return -1;
                }
                fullSpanItem = this.f(n3);
                if (fullSpanItem != null) {
                    this.b.remove(fullSpanItem);
                }
                int n5 = this.b.size();
                for (n4 = 0; n4 < n5; ++n4) {
                    if (((FullSpanItem)this.b.get((int)n4)).c < n3) {
                        continue;
                    }
                    break block5;
                }
                n4 = -1;
            }
            if (n4 != -1) {
                fullSpanItem = (FullSpanItem)this.b.get(n4);
                this.b.remove(n4);
                return fullSpanItem.c;
            }
            return -1;
        }

        public void j(int n3, int n4) {
            int[] nArray = this.a;
            if (nArray != null && n3 < nArray.length) {
                int n5 = n3 + n4;
                this.c(n5);
                nArray = this.a;
                System.arraycopy(nArray, n3, nArray, n5, nArray.length - n3 - n4);
                Arrays.fill(this.a, n3, n5, -1);
                this.l(n3, n4);
            }
        }

        public void k(int n3, int n4) {
            int[] nArray = this.a;
            if (nArray != null && n3 < nArray.length) {
                int n5 = n3 + n4;
                this.c(n5);
                nArray = this.a;
                System.arraycopy(nArray, n5, nArray, n3, nArray.length - n3 - n4);
                nArray = this.a;
                Arrays.fill(nArray, nArray.length - n4, nArray.length, -1);
                this.m(n3, n4);
            }
        }

        public final void l(int n3, int n4) {
            Object object = this.b;
            if (object != null) {
                for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                    object = (FullSpanItem)this.b.get(i3);
                    int n5 = ((FullSpanItem)object).c;
                    if (n5 < n3) continue;
                    ((FullSpanItem)object).c = n5 + n4;
                }
            }
        }

        public final void m(int n3, int n4) {
            Object object = this.b;
            if (object != null) {
                for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                    object = (FullSpanItem)this.b.get(i3);
                    int n5 = ((FullSpanItem)object).c;
                    if (n5 < n3) continue;
                    if (n5 < n3 + n4) {
                        this.b.remove(i3);
                        continue;
                    }
                    ((FullSpanItem)object).c = n5 - n4;
                }
            }
        }

        public void n(int n3, c c3) {
            this.c(n3);
            this.a[n3] = c3.e;
        }

        public int o(int n3) {
            int n4;
            for (n4 = this.a.length; n4 <= n3; n4 *= 2) {
            }
            return n4;
        }

        public static class FullSpanItem
        implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator(){

                public FullSpanItem a(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                public FullSpanItem[] b(int n3) {
                    return new FullSpanItem[n3];
                }
            };
            public int c;
            public int d;
            public int[] e;
            public boolean f;

            public FullSpanItem() {
            }

            public FullSpanItem(Parcel parcel) {
                this.c = parcel.readInt();
                this.d = parcel.readInt();
                int n3 = parcel.readInt();
                boolean bl = true;
                if (n3 != 1) {
                    bl = false;
                }
                this.f = bl;
                n3 = parcel.readInt();
                if (n3 > 0) {
                    int[] nArray = new int[n3];
                    this.e = nArray;
                    parcel.readIntArray(nArray);
                }
            }

            public int describeContents() {
                return 0;
            }

            public int o(int n3) {
                int[] nArray = this.e;
                if (nArray == null) {
                    return 0;
                }
                return nArray[n3];
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FullSpanItem{mPosition=");
                stringBuilder.append(this.c);
                stringBuilder.append(", mGapDir=");
                stringBuilder.append(this.d);
                stringBuilder.append(", mHasUnwantedGapAfter=");
                stringBuilder.append(this.f);
                stringBuilder.append(", mGapPerSpan=");
                stringBuilder.append(Arrays.toString(this.e));
                stringBuilder.append('}');
                return stringBuilder.toString();
            }

            public void writeToParcel(Parcel parcel, int n3) {
                parcel.writeInt(this.c);
                parcel.writeInt(this.d);
                parcel.writeInt(this.f ? 1 : 0);
                int[] nArray = this.e;
                if (nArray != null && nArray.length > 0) {
                    parcel.writeInt(nArray.length);
                    parcel.writeIntArray(this.e);
                    return;
                }
                parcel.writeInt(0);
            }
        }
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;
        public int d;
        public int e;
        public int[] f;
        public int g;
        public int[] h;
        public List i;
        public boolean j;
        public boolean k;
        public boolean l;

        public SavedState() {
        }

        public SavedState(Parcel parcel) {
            int[] nArray;
            int n3;
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = n3 = parcel.readInt();
            if (n3 > 0) {
                nArray = new int[n3];
                this.f = nArray;
                parcel.readIntArray(nArray);
            }
            this.g = n3 = parcel.readInt();
            if (n3 > 0) {
                nArray = new int[n3];
                this.h = nArray;
                parcel.readIntArray(nArray);
            }
            n3 = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n3 == 1;
            this.j = bl2;
            bl2 = parcel.readInt() == 1;
            this.k = bl2;
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.l = bl2;
            this.i = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.e = savedState.e;
            this.c = savedState.c;
            this.d = savedState.d;
            this.f = savedState.f;
            this.g = savedState.g;
            this.h = savedState.h;
            this.j = savedState.j;
            this.k = savedState.k;
            this.l = savedState.l;
            this.i = savedState.i;
        }

        public int describeContents() {
            return 0;
        }

        public void o() {
            this.f = null;
            this.e = 0;
            this.c = -1;
            this.d = -1;
        }

        public void p() {
            this.f = null;
            this.e = 0;
            this.g = 0;
            this.h = null;
            this.i = null;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.g);
            if (this.g > 0) {
                parcel.writeIntArray(this.h);
            }
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeInt(this.k ? 1 : 0);
            parcel.writeInt(this.l ? 1 : 0);
            parcel.writeList(this.i);
        }
    }

    public class b {
        public int a;
        public int b;
        public boolean c;
        public boolean d;
        public boolean e;
        public int[] f;
        public final StaggeredGridLayoutManager g;

        public b(StaggeredGridLayoutManager staggeredGridLayoutManager) {
            this.g = staggeredGridLayoutManager;
            this.c();
        }

        public void a() {
            int n3 = this.c ? this.g.u.i() : this.g.u.m();
            this.b = n3;
        }

        public void b(int n3) {
            if (this.c) {
                this.b = this.g.u.i() - n3;
                return;
            }
            this.b = this.g.u.m() + n3;
        }

        public void c() {
            this.a = -1;
            this.b = Integer.MIN_VALUE;
            this.c = false;
            this.d = false;
            this.e = false;
            int[] nArray = this.f;
            if (nArray != null) {
                Arrays.fill(nArray, -1);
            }
        }

        public void d(c[] cArray) {
            int n3 = cArray.length;
            int[] nArray = this.f;
            if (nArray == null || nArray.length < n3) {
                this.f = new int[this.g.t.length];
            }
            for (int i3 = 0; i3 < n3; ++i3) {
                this.f[i3] = cArray[i3].p(Integer.MIN_VALUE);
            }
        }
    }

    public class c {
        public ArrayList a;
        public int b;
        public int c;
        public int d;
        public final int e;
        public final StaggeredGridLayoutManager f;

        public c(StaggeredGridLayoutManager staggeredGridLayoutManager, int n3) {
            this.f = staggeredGridLayoutManager;
            this.a = new ArrayList();
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
            this.d = 0;
            this.e = n3;
        }

        public void a(View view) {
            LayoutParams layoutParams = this.n(view);
            layoutParams.e = this;
            this.a.add(view);
            this.c = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.b = Integer.MIN_VALUE;
            }
            if (!layoutParams.c() && !layoutParams.b()) {
                return;
            }
            this.d += this.f.u.e(view);
        }

        public void b(boolean bl, int n3) {
            int n4 = bl ? this.l(Integer.MIN_VALUE) : this.p(Integer.MIN_VALUE);
            this.e();
            if (n4 == Integer.MIN_VALUE || bl && n4 < this.f.u.i() || !bl && n4 > this.f.u.m()) {
                return;
            }
            int n5 = n4;
            if (n3 != Integer.MIN_VALUE) {
                n5 = n4 + n3;
            }
            this.c = n5;
            this.b = n5;
        }

        public void c() {
            Object object = this.a;
            View view = (View)((ArrayList)object).get(((ArrayList)object).size() - 1);
            object = this.n(view);
            this.c = this.f.u.d(view);
            if (((LayoutParams)((Object)object)).f && (object = this.f.E.f(((RecyclerView.LayoutParams)((Object)object)).a())) != null && ((LazySpanLookup.FullSpanItem)object).d == 1) {
                this.c += ((LazySpanLookup.FullSpanItem)object).o(this.e);
            }
        }

        public void d() {
            Object object = (View)this.a.get(0);
            LayoutParams layoutParams = this.n((View)object);
            this.b = this.f.u.g((View)object);
            if (layoutParams.f && (object = this.f.E.f(layoutParams.a())) != null && ((LazySpanLookup.FullSpanItem)object).d == -1) {
                this.b -= ((LazySpanLookup.FullSpanItem)object).o(this.e);
            }
        }

        public void e() {
            this.a.clear();
            this.q();
            this.d = 0;
        }

        public int f() {
            if (this.f.z) {
                return this.i(this.a.size() - 1, -1, true);
            }
            return this.i(0, this.a.size(), true);
        }

        public int g() {
            if (this.f.z) {
                return this.i(0, this.a.size(), true);
            }
            return this.i(this.a.size() - 1, -1, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        public int h(int n3, int n4, boolean bl, boolean bl2, boolean bl3) {
            int n5 = this.f.u.m();
            int n6 = this.f.u.i();
            int n7 = n4 > n3 ? 1 : -1;
            while (n3 != n4) {
                View view = (View)this.a.get(n3);
                int n8 = this.f.u.g(view);
                int n9 = this.f.u.d(view);
                boolean bl4 = false;
                boolean bl5 = bl3 ? n8 <= n6 : n8 < n6;
                if (bl3 ? n9 >= n5 : n9 > n5) {
                    bl4 = true;
                }
                if (bl5 && bl4) {
                    if (bl && bl2) {
                        if (n8 >= n5 && n9 <= n6) {
                            return this.f.l0(view);
                        }
                    } else {
                        if (bl2) {
                            return this.f.l0(view);
                        }
                        if (n8 < n5 || n9 > n6) {
                            return this.f.l0(view);
                        }
                    }
                }
                n3 += n7;
            }
            return -1;
        }

        public int i(int n3, int n4, boolean bl) {
            return this.h(n3, n4, false, false, bl);
        }

        public int j() {
            return this.d;
        }

        public int k() {
            int n3 = this.c;
            if (n3 != Integer.MIN_VALUE) {
                return n3;
            }
            this.c();
            return this.c;
        }

        public int l(int n3) {
            int n4 = this.c;
            if (n4 != Integer.MIN_VALUE) {
                return n4;
            }
            if (this.a.size() == 0) {
                return n3;
            }
            this.c();
            return this.c;
        }

        public View m(int n3, int n4) {
            View view = null;
            View view2 = null;
            if (n4 == -1) {
                int n5 = this.a.size();
                view = view2;
                for (n4 = 0; n4 < n5; ++n4) {
                    view2 = (View)this.a.get(n4);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = this.f;
                    if (staggeredGridLayoutManager.z && staggeredGridLayoutManager.l0(view2) <= n3) break;
                    staggeredGridLayoutManager = this.f;
                    if (!staggeredGridLayoutManager.z && staggeredGridLayoutManager.l0(view2) >= n3 || !view2.hasFocusable()) break;
                    view = view2;
                }
                return view;
            }
            for (n4 = this.a.size() - 1; n4 >= 0; --n4) {
                view2 = (View)this.a.get(n4);
                StaggeredGridLayoutManager staggeredGridLayoutManager = this.f;
                if (staggeredGridLayoutManager.z && staggeredGridLayoutManager.l0(view2) >= n3) break;
                staggeredGridLayoutManager = this.f;
                if (!staggeredGridLayoutManager.z && staggeredGridLayoutManager.l0(view2) <= n3 || !view2.hasFocusable()) break;
                view = view2;
            }
            return view;
        }

        public LayoutParams n(View view) {
            return (LayoutParams)view.getLayoutParams();
        }

        public int o() {
            int n3 = this.b;
            if (n3 != Integer.MIN_VALUE) {
                return n3;
            }
            this.d();
            return this.b;
        }

        public int p(int n3) {
            int n4 = this.b;
            if (n4 != Integer.MIN_VALUE) {
                return n4;
            }
            if (this.a.size() == 0) {
                return n3;
            }
            this.d();
            return this.b;
        }

        public void q() {
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
        }

        public void r(int n3) {
            int n4 = this.b;
            if (n4 != Integer.MIN_VALUE) {
                this.b = n4 + n3;
            }
            if ((n4 = this.c) != Integer.MIN_VALUE) {
                this.c = n4 + n3;
            }
        }

        public void s() {
            int n3 = this.a.size();
            View view = (View)this.a.remove(n3 - 1);
            LayoutParams layoutParams = this.n(view);
            layoutParams.e = null;
            if (layoutParams.c() || layoutParams.b()) {
                this.d -= this.f.u.e(view);
            }
            if (n3 == 1) {
                this.b = Integer.MIN_VALUE;
            }
            this.c = Integer.MIN_VALUE;
        }

        public void t() {
            View view = (View)this.a.remove(0);
            LayoutParams layoutParams = this.n(view);
            layoutParams.e = null;
            if (this.a.size() == 0) {
                this.c = Integer.MIN_VALUE;
            }
            if (layoutParams.c() || layoutParams.b()) {
                this.d -= this.f.u.e(view);
            }
            this.b = Integer.MIN_VALUE;
        }

        public void u(View view) {
            LayoutParams layoutParams = this.n(view);
            layoutParams.e = this;
            this.a.add(0, view);
            this.b = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.c = Integer.MIN_VALUE;
            }
            if (!layoutParams.c() && !layoutParams.b()) {
                return;
            }
            this.d += this.f.u.e(view);
        }

        public void v(int n3) {
            this.b = n3;
            this.c = n3;
        }
    }
}

