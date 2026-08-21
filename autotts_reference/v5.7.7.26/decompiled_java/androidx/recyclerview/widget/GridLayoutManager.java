/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.GridView
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import p0.s;

public class GridLayoutManager
extends LinearLayoutManager {
    public boolean I = false;
    public int J = -1;
    public int[] K;
    public View[] L;
    public final SparseIntArray M = new SparseIntArray();
    public final SparseIntArray N = new SparseIntArray();
    public b O = new a();
    public final Rect P = new Rect();
    public boolean Q;

    public GridLayoutManager(Context context, int n3, int n4, boolean bl) {
        super(context, n4, bl);
        this.e3(n3);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(context, attributeSet, n3, n4);
        this.e3(RecyclerView.p.m0((Context)context, (AttributeSet)attributeSet, (int)n3, (int)n4).b);
    }

    public static int[] Q2(int[] nArray, int n3, int n4) {
        int[] nArray2;
        int n5;
        block7: {
            block6: {
                n5 = 1;
                if (nArray == null || nArray.length != n3 + 1) break block6;
                nArray2 = nArray;
                if (nArray[nArray.length - 1] == n4) break block7;
            }
            nArray2 = new int[n3 + 1];
        }
        int n6 = 0;
        nArray2[0] = 0;
        int n7 = n4 / n3;
        int n8 = n4 % n3;
        int n9 = 0;
        n4 = n6;
        while (n5 <= n3) {
            if ((n4 += n8) > 0 && n3 - n4 < n8) {
                n6 = n7 + 1;
                n4 -= n3;
            } else {
                n6 = n7;
            }
            nArray2[n5] = n9 += n6;
            ++n5;
        }
        return nArray2;
    }

    @Override
    public int A(RecyclerView.z z3) {
        if (this.Q) {
            return this.T2(z3);
        }
        return super.A(z3);
    }

    @Override
    public int A1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        this.f3();
        this.V2();
        return super.A1(n3, v3, z3);
    }

    @Override
    public int C1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        this.f3();
        this.V2();
        return super.C1(n3, v3, z3);
    }

    @Override
    public void E2(boolean bl) {
        if (!bl) {
            super.E2(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    @Override
    public void G1(Rect object, int n3, int n4) {
        if (this.K == null) {
            super.G1((Rect)object, n3, n4);
        }
        int n5 = this.i0() + this.j0();
        int n6 = this.k0() + this.h0();
        if (this.s == 1) {
            n4 = RecyclerView.p.s(n4, object.height() + n6, this.f0());
            object = this.K;
            n5 = RecyclerView.p.s(n3, (int)(object[((Rect)object).length - 1] + n5), this.g0());
            n3 = n4;
            n4 = n5;
        } else {
            n3 = RecyclerView.p.s(n3, object.width() + n5, this.g0());
            object = this.K;
            n5 = RecyclerView.p.s(n4, (int)(object[((Rect)object).length - 1] + n6), this.f0());
            n4 = n3;
            n3 = n5;
        }
        this.F1(n4, n3);
    }

    @Override
    public RecyclerView.LayoutParams I() {
        if (this.s == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override
    public RecyclerView.LayoutParams J(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override
    public RecyclerView.LayoutParams K(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public View N0(View object, int n3, RecyclerView.v v3, RecyclerView.z z3) {
        int n4;
        int n5;
        View view = this.G((View)object);
        LayoutParams layoutParams = null;
        if (view == null) {
            return null;
        }
        LayoutParams layoutParams2 = (LayoutParams)view.getLayoutParams();
        int n6 = layoutParams2.e;
        int n7 = layoutParams2.f + n6;
        if (super.N0((View)object, n3, v3, z3) == null) {
            return null;
        }
        boolean bl = this.V1(n3) == 1;
        if (bl != this.x) {
            n3 = this.O() - 1;
            n5 = -1;
            n4 = -1;
        } else {
            n5 = this.O();
            n4 = 1;
            n3 = 0;
        }
        int n8 = this.s == 1 && this.q2() ? 1 : 0;
        int n9 = this.Y2(v3, z3, n3);
        int n10 = -1;
        int n11 = -1;
        int n12 = 0;
        int n13 = 0;
        int n14 = n3;
        object = null;
        n3 = n13;
        while (n14 != n5) {
            block16: {
                int n15;
                int n16;
                LayoutParams layoutParams3;
                block17: {
                    int n17;
                    block18: {
                        block15: {
                            n13 = this.Y2(v3, z3, n14);
                            layoutParams2 = this.N(n14);
                            if (layoutParams2 == view) break;
                            if (!layoutParams2.hasFocusable() || n13 == n9) break block15;
                            if (layoutParams != null) {
                                break;
                            }
                            break block16;
                        }
                        layoutParams3 = (LayoutParams)layoutParams2.getLayoutParams();
                        n16 = layoutParams3.e;
                        n15 = layoutParams3.f + n16;
                        if (layoutParams2.hasFocusable() && n16 == n6 && n15 == n7) {
                            return layoutParams2;
                        }
                        if (layoutParams2.hasFocusable() && layoutParams == null || !layoutParams2.hasFocusable() && object == null) break block17;
                        n13 = Math.max(n16, n6);
                        n17 = Math.min(n15, n7) - n13;
                        if (!layoutParams2.hasFocusable()) break block18;
                        if (n17 <= n12 && (n17 != n12 || n8 != (n13 = n16 > n10 ? 1 : 0))) break block16;
                        break block17;
                    }
                    if (layoutParams != null) break block16;
                    n13 = 1;
                    if (!this.C0((View)layoutParams2, false, true)) break block16;
                    if (n17 > n3) break block17;
                    if (n17 != n3) break block16;
                    if (n16 <= n11) {
                        n13 = 0;
                    }
                    if (n8 != n13) break block16;
                }
                if (layoutParams2.hasFocusable()) {
                    n10 = layoutParams3.e;
                    n12 = Math.min(n15, n7);
                    n13 = Math.max(n16, n6);
                    layoutParams = layoutParams2;
                    n12 -= n13;
                } else {
                    n11 = layoutParams3.e;
                    n3 = Math.min(n15, n7) - Math.max(n16, n6);
                    object = layoutParams2;
                }
            }
            n14 += n4;
        }
        if (layoutParams != null) {
            return layoutParams;
        }
        return object;
    }

    public final void N2(RecyclerView.v v3, RecyclerView.z z3, int n3, boolean bl) {
        int n4;
        int n5;
        int n6 = 0;
        if (bl) {
            n5 = 1;
            n4 = n3;
            n3 = 0;
        } else {
            --n3;
            n4 = -1;
            n5 = -1;
        }
        while (n3 != n4) {
            int n7;
            View view = this.L[n3];
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            layoutParams.f = n7 = this.a3(v3, z3, this.l0(view));
            layoutParams.e = n6;
            n6 += n7;
            n3 += n5;
        }
    }

    public final void O2() {
        int n3 = this.O();
        for (int i3 = 0; i3 < n3; ++i3) {
            LayoutParams layoutParams = (LayoutParams)this.N(i3).getLayoutParams();
            int n4 = layoutParams.a();
            this.M.put(n4, layoutParams.f());
            this.N.put(n4, layoutParams.e());
        }
    }

    @Override
    public boolean P1() {
        return this.D == null && !this.I;
    }

    public final void P2(int n3) {
        this.K = GridLayoutManager.Q2(this.K, this.J, n3);
    }

    @Override
    public void Q0(RecyclerView.v v3, RecyclerView.z z3, s s3) {
        super.Q0(v3, z3, s3);
        s3.h0(GridView.class.getName());
    }

    @Override
    public void R1(RecyclerView.z z3, LinearLayoutManager.c c3, RecyclerView.p.c c4) {
        int n3 = this.J;
        for (int i3 = 0; i3 < this.J && c3.c(z3) && n3 > 0; ++i3) {
            int n4 = c3.d;
            c4.a(n4, Math.max(0, c3.g));
            n3 -= this.O.f(n4);
            c3.d += c3.e;
        }
    }

    public final void R2() {
        this.M.clear();
        this.N.clear();
    }

    @Override
    public int S(RecyclerView.v v3, RecyclerView.z z3) {
        if (this.s == 1) {
            return this.J;
        }
        if (z3.b() < 1) {
            return 0;
        }
        return this.Y2(v3, z3, z3.b() - 1) + 1;
    }

    public final int S2(RecyclerView.z z3) {
        if (this.O() != 0 && z3.b() != 0) {
            this.X1();
            boolean bl = this.r2();
            View view = this.b2(bl ^ true, true);
            View view2 = this.a2(bl ^ true, true);
            if (view != null && view2 != null) {
                int n3 = this.O.b(this.l0(view), this.J);
                int n4 = this.O.b(this.l0(view2), this.J);
                int n5 = Math.min(n3, n4);
                n3 = Math.max(n3, n4);
                n4 = this.O.b(z3.b() - 1, this.J);
                n5 = this.x ? Math.max(0, n4 + 1 - n3 - 1) : Math.max(0, n5);
                if (!bl) {
                    return n5;
                }
                n3 = Math.abs(this.u.d(view2) - this.u.g(view));
                int n6 = this.O.b(this.l0(view), this.J);
                n4 = this.O.b(this.l0(view2), this.J);
                float f3 = (float)n3 / (float)(n4 - n6 + 1);
                return Math.round((float)n5 * f3 + (float)(this.u.m() - this.u.g(view)));
            }
        }
        return 0;
    }

    @Override
    public void T0(RecyclerView.v v3, RecyclerView.z z3, View object, s s3) {
        ViewGroup.LayoutParams layoutParams = object.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.S0((View)object, s3);
            return;
        }
        object = (LayoutParams)layoutParams;
        int n3 = this.Y2(v3, z3, ((RecyclerView.LayoutParams)((Object)object)).a());
        if (this.s == 0) {
            s3.k0(s.f.a(((LayoutParams)((Object)object)).e(), ((LayoutParams)((Object)object)).f(), n3, 1, false, false));
            return;
        }
        s3.k0(s.f.a(n3, 1, ((LayoutParams)((Object)object)).e(), ((LayoutParams)((Object)object)).f(), false, false));
    }

    public final int T2(RecyclerView.z z3) {
        if (this.O() != 0 && z3.b() != 0) {
            this.X1();
            View view = this.b2(this.r2() ^ true, true);
            View view2 = this.a2(this.r2() ^ true, true);
            if (view != null && view2 != null) {
                if (!this.r2()) {
                    return this.O.b(z3.b() - 1, this.J) + 1;
                }
                int n3 = this.u.d(view2);
                int n4 = this.u.g(view);
                int n5 = this.O.b(this.l0(view), this.J);
                int n6 = this.O.b(this.l0(view2), this.J);
                int n7 = this.O.b(z3.b() - 1, this.J);
                return (int)((float)(n3 - n4) / (float)(n6 - n5 + 1) * (float)(n7 + 1));
            }
        }
        return 0;
    }

    public final void U2(RecyclerView.v v3, RecyclerView.z z3, LinearLayoutManager.a a4, int n3) {
        int n4;
        int n5;
        n3 = n3 == 1 ? 1 : 0;
        int n6 = this.Z2(v3, z3, a4.b);
        if (n3 != 0) {
            while (n6 > 0 && (n3 = a4.b) > 0) {
                a4.b = --n3;
                n6 = this.Z2(v3, z3, n3);
            }
            return;
        }
        int n7 = z3.b();
        n3 = a4.b;
        while (n3 < n7 - 1 && (n5 = this.Z2(v3, z3, n4 = n3 + 1)) > n6) {
            n3 = n4;
            n6 = n5;
        }
        a4.b = n3;
    }

    @Override
    public void V0(RecyclerView recyclerView, int n3, int n4) {
        this.O.h();
        this.O.g();
    }

    public final void V2() {
        View[] viewArray = this.L;
        if (viewArray != null && viewArray.length == this.J) {
            return;
        }
        this.L = new View[this.J];
    }

    @Override
    public void W0(RecyclerView recyclerView) {
        this.O.h();
        this.O.g();
    }

    public int W2(int n3, int n4) {
        if (this.s == 1 && this.q2()) {
            int[] nArray = this.K;
            int n5 = this.J;
            return nArray[n5 - n3] - nArray[n5 - n3 - n4];
        }
        int[] nArray = this.K;
        return nArray[n4 + n3] - nArray[n3];
    }

    @Override
    public void X0(RecyclerView recyclerView, int n3, int n4, int n5) {
        this.O.h();
        this.O.g();
    }

    public int X2() {
        return this.J;
    }

    @Override
    public void Y0(RecyclerView recyclerView, int n3, int n4) {
        this.O.h();
        this.O.g();
    }

    public final int Y2(RecyclerView.v object, RecyclerView.z z3, int n3) {
        if (!z3.e()) {
            return this.O.b(n3, this.J);
        }
        int n4 = ((RecyclerView.v)object).f(n3);
        if (n4 == -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot find span size for pre layout position. ");
            ((StringBuilder)object).append(n3);
            Log.w((String)"GridLayoutManager", (String)((StringBuilder)object).toString());
            return 0;
        }
        return this.O.b(n4, this.J);
    }

    public final int Z2(RecyclerView.v object, RecyclerView.z z3, int n3) {
        if (!z3.e()) {
            return this.O.c(n3, this.J);
        }
        int n4 = this.N.get(n3, -1);
        if (n4 != -1) {
            return n4;
        }
        n4 = ((RecyclerView.v)object).f(n3);
        if (n4 == -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
            ((StringBuilder)object).append(n3);
            Log.w((String)"GridLayoutManager", (String)((StringBuilder)object).toString());
            return 0;
        }
        return this.O.c(n4, this.J);
    }

    @Override
    public void a1(RecyclerView recyclerView, int n3, int n4, Object object) {
        this.O.h();
        this.O.g();
    }

    public final int a3(RecyclerView.v object, RecyclerView.z z3, int n3) {
        if (!z3.e()) {
            return this.O.f(n3);
        }
        int n4 = this.M.get(n3, -1);
        if (n4 != -1) {
            return n4;
        }
        n4 = ((RecyclerView.v)object).f(n3);
        if (n4 == -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
            ((StringBuilder)object).append(n3);
            Log.w((String)"GridLayoutManager", (String)((StringBuilder)object).toString());
            return 1;
        }
        return this.O.f(n4);
    }

    @Override
    public void b1(RecyclerView.v v3, RecyclerView.z z3) {
        if (z3.e()) {
            this.O2();
        }
        super.b1(v3, z3);
        this.R2();
    }

    public final void b3(float f3, int n3) {
        this.P2(Math.max(Math.round(f3 * (float)this.J), n3));
    }

    @Override
    public void c1(RecyclerView.z z3) {
        super.c1(z3);
        this.I = false;
    }

    public final void c3(View view, int n3, boolean bl) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect = layoutParams.b;
        int n4 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int n5 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int n6 = this.W2(layoutParams.e, layoutParams.f);
        if (this.s == 1) {
            n5 = RecyclerView.p.P(n6, n3, n5, layoutParams.width, false);
            n3 = RecyclerView.p.P(this.u.n(), this.c0(), n4, layoutParams.height, true);
        } else {
            n3 = RecyclerView.p.P(n6, n3, n4, layoutParams.height, false);
            n5 = RecyclerView.p.P(this.u.n(), this.t0(), n5, layoutParams.width, true);
        }
        this.d3(view, n5, n3, bl);
    }

    public final void d3(View view, int n3, int n4, boolean bl) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        if (bl = bl ? this.L1(view, n3, n4, layoutParams) : this.J1(view, n3, n4, layoutParams)) {
            view.measure(n3, n4);
        }
    }

    public void e3(int n3) {
        if (n3 == this.J) {
            return;
        }
        this.I = true;
        if (n3 >= 1) {
            this.J = n3;
            this.O.h();
            this.x1();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Span count should be at least 1. Provided ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void f3() {
        int n3;
        int n4;
        if (this.p2() == 1) {
            n4 = this.s0() - this.j0();
            n3 = this.i0();
        } else {
            n4 = this.b0() - this.h0();
            n3 = this.k0();
        }
        this.P2(n4 - n3);
    }

    @Override
    public View j2(RecyclerView.v v3, RecyclerView.z z3, boolean bl, boolean bl2) {
        int n3;
        int n4 = this.O();
        int n5 = 1;
        if (bl2) {
            n3 = this.O() - 1;
            n4 = -1;
            n5 = -1;
        } else {
            n3 = 0;
        }
        int n6 = z3.b();
        this.X1();
        int n7 = this.u.m();
        int n8 = this.u.i();
        View view = null;
        View view2 = null;
        while (n3 != n4) {
            View view3 = this.N(n3);
            int n9 = this.l0(view3);
            View view4 = view;
            View view5 = view2;
            if (n9 >= 0) {
                view4 = view;
                view5 = view2;
                if (n9 < n6) {
                    if (this.Z2(v3, z3, n9) != 0) {
                        view4 = view;
                        view5 = view2;
                    } else if (((RecyclerView.LayoutParams)view3.getLayoutParams()).c()) {
                        view4 = view;
                        view5 = view2;
                        if (view2 == null) {
                            view5 = view3;
                            view4 = view;
                        }
                    } else {
                        if (this.u.g(view3) < n8 && this.u.d(view3) >= n7) {
                            return view3;
                        }
                        view4 = view;
                        view5 = view2;
                        if (view == null) {
                            view4 = view3;
                            view5 = view2;
                        }
                    }
                }
            }
            n3 += n5;
            view = view4;
            view2 = view5;
        }
        if (view != null) {
            return view;
        }
        return view2;
    }

    @Override
    public int o0(RecyclerView.v v3, RecyclerView.z z3) {
        if (this.s == 0) {
            return this.J;
        }
        if (z3.b() < 1) {
            return 0;
        }
        return this.Y2(v3, z3, z3.b() - 1) + 1;
    }

    @Override
    public boolean r(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void s2(RecyclerView.v object, RecyclerView.z object2, LinearLayoutManager.c c3, LinearLayoutManager.b b3) {
        int n3;
        int n4;
        int n5;
        int n6 = this.u.l();
        int n7 = n6 != 0x40000000 ? 1 : 0;
        int n8 = this.O() > 0 ? this.K[this.J] : 0;
        if (n7 != 0) {
            this.f3();
        }
        boolean bl = c3.e == 1;
        int n9 = this.J;
        if (!bl) {
            n9 = this.Z2((RecyclerView.v)object, (RecyclerView.z)object2, c3.d) + this.a3((RecyclerView.v)object, (RecyclerView.z)object2, c3.d);
        }
        for (n5 = 0; n5 < this.J && c3.c((RecyclerView.z)object2) && n9 > 0; ++n5) {
            View view;
            n4 = c3.d;
            n3 = this.a3((RecyclerView.v)object, (RecyclerView.z)object2, n4);
            if (n3 > this.J) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Item at position ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append(" requires ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" spans but GridLayoutManager has only ");
                ((StringBuilder)object).append(this.J);
                ((StringBuilder)object).append(" spans.");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if ((n9 -= n3) < 0 || (view = c3.d((RecyclerView.v)object)) == null) break;
            this.L[n5] = view;
        }
        if (n5 == 0) {
            b3.b = true;
            return;
        }
        this.N2((RecyclerView.v)object, (RecyclerView.z)object2, n5, bl);
        float f3 = 0.0f;
        n9 = 0;
        for (n3 = 0; n3 < n5; ++n3) {
            object = this.L[n3];
            if (c3.l == null) {
                if (bl) {
                    this.i((View)object);
                } else {
                    this.j((View)object, 0);
                }
            } else if (bl) {
                this.g((View)object);
            } else {
                this.h((View)object, 0);
            }
            this.o((View)object, this.P);
            this.c3((View)object, n6, false);
            int n10 = this.u.e((View)object);
            n4 = n9;
            if (n10 > n9) {
                n4 = n10;
            }
            object2 = (LayoutParams)object.getLayoutParams();
            float f4 = (float)this.u.f((View)object) * 1.0f / (float)((LayoutParams)((Object)object2)).f;
            float f5 = f3;
            if (f4 > f3) {
                f5 = f4;
            }
            f3 = f5;
            n9 = n4;
        }
        n3 = n9;
        if (n7 != 0) {
            this.b3(f3, n8);
            n7 = 0;
            n9 = 0;
            while (true) {
                n3 = n9;
                if (n7 >= n5) break;
                object = this.L[n7];
                this.c3((View)object, 0x40000000, true);
                n8 = this.u.e((View)object);
                n3 = n9;
                if (n8 > n9) {
                    n3 = n8;
                }
                ++n7;
                n9 = n3;
            }
        }
        for (n9 = 0; n9 < n5; ++n9) {
            object2 = this.L[n9];
            if (this.u.e((View)object2) == n3) continue;
            LayoutParams layoutParams = (LayoutParams)object2.getLayoutParams();
            object = layoutParams.b;
            n8 = ((Rect)object).top + ((Rect)object).bottom + layoutParams.topMargin + layoutParams.bottomMargin;
            n7 = ((Rect)object).left + ((Rect)object).right + layoutParams.leftMargin + layoutParams.rightMargin;
            n4 = this.W2(layoutParams.e, layoutParams.f);
            if (this.s == 1) {
                n7 = RecyclerView.p.P(n4, 0x40000000, n7, layoutParams.width, false);
                n8 = View.MeasureSpec.makeMeasureSpec((int)(n3 - n8), (int)0x40000000);
            } else {
                n7 = View.MeasureSpec.makeMeasureSpec((int)(n3 - n7), (int)0x40000000);
                n8 = RecyclerView.p.P(n4, 0x40000000, n8, layoutParams.height, false);
            }
            this.d3((View)object2, n7, n8, true);
        }
        b3.a = n3;
        if (this.s == 1) {
            if (c3.f == -1) {
                n8 = c3.b;
                n9 = n8 - n3;
            } else {
                n9 = c3.b;
                n8 = n9 + n3;
            }
            n3 = n9;
            n9 = 0;
            n7 = 0;
        } else {
            if (c3.f == -1) {
                n9 = c3.b;
                n7 = n9 - n3;
            } else {
                n7 = c3.b;
                n9 = n7 + n3;
            }
            n3 = 0;
            n8 = 0;
        }
        n4 = 0;
        while (true) {
            if (n4 >= n5) {
                Arrays.fill(this.L, null);
                return;
            }
            object = this.L[n4];
            object2 = (LayoutParams)object.getLayoutParams();
            if (this.s == 1) {
                if (this.q2()) {
                    n9 = this.i0() + this.K[this.J - ((LayoutParams)((Object)object2)).e];
                    n7 = n9 - this.u.f((View)object);
                } else {
                    n9 = this.i0();
                    n7 = this.K[((LayoutParams)((Object)object2)).e] + n9;
                    n9 = this.u.f((View)object) + n7;
                }
            } else {
                n3 = this.k0();
                n3 = this.K[((LayoutParams)((Object)object2)).e] + n3;
                n8 = this.u.f((View)object) + n3;
            }
            this.D0((View)object, n7, n3, n9, n8);
            if (((RecyclerView.LayoutParams)((Object)object2)).c() || ((RecyclerView.LayoutParams)((Object)object2)).b()) {
                b3.c = true;
            }
            bl = b3.d;
            b3.d = object.hasFocusable() | bl;
            ++n4;
        }
    }

    @Override
    public void u2(RecyclerView.v v3, RecyclerView.z z3, LinearLayoutManager.a a4, int n3) {
        super.u2(v3, z3, a4, n3);
        this.f3();
        if (z3.b() > 0 && !z3.e()) {
            this.U2(v3, z3, a4, n3);
        }
        this.V2();
    }

    @Override
    public int w(RecyclerView.z z3) {
        if (this.Q) {
            return this.S2(z3);
        }
        return super.w(z3);
    }

    @Override
    public int x(RecyclerView.z z3) {
        if (this.Q) {
            return this.T2(z3);
        }
        return super.x(z3);
    }

    @Override
    public int z(RecyclerView.z z3) {
        if (this.Q) {
            return this.S2(z3);
        }
        return super.z(z3);
    }

    public static class LayoutParams
    extends RecyclerView.LayoutParams {
        public int e = -1;
        public int f = 0;

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

        public int e() {
            return this.e;
        }

        public int f() {
            return this.f;
        }
    }

    public static final class a
    extends b {
        @Override
        public int e(int n3, int n4) {
            return n3 % n4;
        }

        @Override
        public int f(int n3) {
            return 1;
        }
    }

    public static abstract class b {
        public final SparseIntArray a = new SparseIntArray();
        public final SparseIntArray b = new SparseIntArray();
        public boolean c = false;
        public boolean d = false;

        public static int a(SparseIntArray sparseIntArray, int n3) {
            int n4 = sparseIntArray.size() - 1;
            int n5 = 0;
            while (n5 <= n4) {
                int n6 = n5 + n4 >>> 1;
                if (sparseIntArray.keyAt(n6) < n3) {
                    n5 = n6 + 1;
                    continue;
                }
                n4 = n6 - 1;
            }
            n3 = n5 - 1;
            if (n3 >= 0 && n3 < sparseIntArray.size()) {
                return sparseIntArray.keyAt(n3);
            }
            return -1;
        }

        public int b(int n3, int n4) {
            if (!this.d) {
                return this.d(n3, n4);
            }
            int n5 = this.b.get(n3, -1);
            if (n5 != -1) {
                return n5;
            }
            n4 = this.d(n3, n4);
            this.b.put(n3, n4);
            return n4;
        }

        public int c(int n3, int n4) {
            if (!this.c) {
                return this.e(n3, n4);
            }
            int n5 = this.a.get(n3, -1);
            if (n5 != -1) {
                return n5;
            }
            n4 = this.e(n3, n4);
            this.a.put(n3, n4);
            return n4;
        }

        public int d(int n3, int n4) {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            if (this.d && (n10 = androidx.recyclerview.widget.GridLayoutManager$b.a(this.b, n3)) != -1) {
                n9 = this.b.get(n10);
                n8 = n10 + 1;
                n7 = this.c(n10, n4) + this.f(n10);
                n10 = n9;
                n6 = n8;
                n5 = n7;
                if (n7 == n4) {
                    n10 = n9 + 1;
                    n5 = 0;
                    n6 = n8;
                }
            } else {
                n10 = 0;
                n5 = n6 = 0;
            }
            int n11 = this.f(n3);
            n9 = n5;
            n5 = n10;
            for (n8 = n6; n8 < n3; ++n8) {
                n7 = this.f(n8);
                if ((n9 += n7) == n4) {
                    n6 = n5 + 1;
                    n10 = 0;
                } else {
                    n6 = n5;
                    n10 = n9;
                    if (n9 > n4) {
                        n6 = n5 + 1;
                        n10 = n7;
                    }
                }
                n5 = n6;
                n9 = n10;
            }
            n3 = n5;
            if (n9 + n11 > n4) {
                n3 = n5 + 1;
            }
            return n3;
        }

        public abstract int e(int var1, int var2);

        public abstract int f(int var1);

        public void g() {
            this.b.clear();
        }

        public void h() {
            this.a.clear();
        }
    }
}

