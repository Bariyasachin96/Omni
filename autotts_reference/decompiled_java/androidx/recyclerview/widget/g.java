/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

public class g
extends RecyclerView.y {
    public final LinearInterpolator i = new LinearInterpolator();
    public final DecelerateInterpolator j = new DecelerateInterpolator();
    public PointF k;
    public final DisplayMetrics l;
    public boolean m = false;
    public float n;
    public int o = 0;
    public int p = 0;

    public g(Context context) {
        this.l = context.getResources().getDisplayMetrics();
    }

    public final float A() {
        if (!this.m) {
            this.n = this.v(this.l);
            this.m = true;
        }
        return this.n;
    }

    public int B() {
        float f3;
        PointF pointF = this.k;
        if (pointF != null && (f3 = pointF.y) != 0.0f) {
            if (f3 > 0.0f) {
                return 1;
            }
            return -1;
        }
        return 0;
    }

    public void C(RecyclerView.y.a a4) {
        PointF pointF = this.a(this.f());
        if (pointF != null && (pointF.x != 0.0f || pointF.y != 0.0f)) {
            this.i(pointF);
            this.k = pointF;
            this.o = (int)(pointF.x * 10000.0f);
            this.p = (int)(pointF.y * 10000.0f);
            int n3 = this.x(10000);
            a4.d((int)((float)this.o * 1.2f), (int)((float)this.p * 1.2f), (int)((float)n3 * 1.2f), (Interpolator)this.i);
            return;
        }
        a4.b(this.f());
        this.r();
    }

    @Override
    public void l(int n3, int n4, RecyclerView.z z3, RecyclerView.y.a a4) {
        if (this.c() == 0) {
            this.r();
            return;
        }
        this.o = this.y(this.o, n3);
        this.p = n3 = this.y(this.p, n4);
        if (this.o == 0 && n3 == 0) {
            this.C(a4);
        }
    }

    @Override
    public void m() {
    }

    @Override
    public void n() {
        this.p = 0;
        this.o = 0;
        this.k = null;
    }

    @Override
    public void o(View view, RecyclerView.z z3, RecyclerView.y.a a4) {
        int n3;
        int n4 = this.t(view, this.z());
        int n5 = this.w((int)Math.sqrt(n4 * n4 + (n3 = this.u(view, this.B())) * n3));
        if (n5 > 0) {
            a4.d(-n4, -n3, n5, (Interpolator)this.j);
        }
    }

    public int s(int n3, int n4, int n5, int n6, int n7) {
        if (n7 != -1) {
            if (n7 != 0) {
                if (n7 == 1) {
                    return n6 - n4;
                }
                throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
            }
            if ((n3 = n5 - n3) > 0) {
                return n3;
            }
            n3 = n6 - n4;
            if (n3 < 0) {
                return n3;
            }
            return 0;
        }
        return n5 - n3;
    }

    public int t(View view, int n3) {
        RecyclerView.p p3 = this.e();
        if (p3 != null && p3.p()) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            return this.s(p3.V(view) - layoutParams.leftMargin, p3.Y(view) + layoutParams.rightMargin, p3.i0(), p3.s0() - p3.j0(), n3);
        }
        return 0;
    }

    public int u(View view, int n3) {
        RecyclerView.p p3 = this.e();
        if (p3 != null && p3.q()) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            return this.s(p3.Z(view) - layoutParams.topMargin, p3.T(view) + layoutParams.bottomMargin, p3.k0(), p3.b0() - p3.h0(), n3);
        }
        return 0;
    }

    public float v(DisplayMetrics displayMetrics) {
        return 25.0f / (float)displayMetrics.densityDpi;
    }

    public int w(int n3) {
        return (int)Math.ceil((double)this.x(n3) / 0.3356);
    }

    public int x(int n3) {
        return (int)Math.ceil((float)Math.abs(n3) * this.A());
    }

    public final int y(int n3, int n4) {
        if (n3 * (n4 = n3 - n4) <= 0) {
            return 0;
        }
        return n4;
    }

    public int z() {
        float f3;
        PointF pointF = this.k;
        if (pointF != null && (f3 = pointF.x) != 0.0f) {
            if (f3 > 0.0f) {
                return 1;
            }
            return -1;
        }
        return 0;
    }
}

