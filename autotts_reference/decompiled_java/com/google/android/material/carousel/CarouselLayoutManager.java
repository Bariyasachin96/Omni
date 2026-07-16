/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.accessibility.AccessibilityEvent
 */
package com.google.android.material.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.g;
import com.google.android.material.carousel.a;
import com.google.android.material.carousel.c;
import f2.e;
import f2.f;
import f2.i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import n0.h;
import z1.m;

public class CarouselLayoutManager
extends RecyclerView.p
implements f2.b,
RecyclerView.y.b {
    public int A = 0;
    public Map B;
    public e C;
    public final View.OnLayoutChangeListener D;
    public int E;
    public int F = -1;
    public int G = 0;
    public int s;
    public int t;
    public int u;
    public boolean v = false;
    public final c w = new c();
    public a x;
    public com.google.android.material.carousel.d y;
    public com.google.android.material.carousel.c z;

    public CarouselLayoutManager() {
        this(new i());
    }

    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int n3, int n4) {
        this.D = new f2.c(this);
        this.S2(new i());
        this.R2(context, attributeSet);
    }

    public CarouselLayoutManager(a a4) {
        this(a4, 0);
    }

    public CarouselLayoutManager(a a4, int n3) {
        this.D = new f2.c(this);
        this.S2(a4);
        this.T2(n3);
    }

    public static d E2(List list, float f3, boolean bl) {
        int n3;
        int n4;
        int n5;
        float f4 = Float.MAX_VALUE;
        int n6 = -1;
        int n7 = n5 = (n4 = -1);
        float f5 = -3.4028235E38f;
        float f6 = Float.MAX_VALUE;
        float f7 = Float.MAX_VALUE;
        for (n3 = 0; n3 < list.size(); ++n3) {
            c.c c3 = (c.c)list.get(n3);
            float f8 = bl ? c3.b : c3.a;
            float f9 = Math.abs(f8 - f3);
            float f10 = f4;
            int n8 = n6;
            if (f8 <= f3) {
                f10 = f4;
                n8 = n6;
                if (f9 <= f4) {
                    n8 = n3;
                    f10 = f9;
                }
            }
            float f11 = f6;
            int n9 = n5;
            if (f8 > f3) {
                f11 = f6;
                n9 = n5;
                if (f9 <= f6) {
                    n9 = n3;
                    f11 = f9;
                }
            }
            f9 = f7;
            if (f8 <= f7) {
                n4 = n3;
                f9 = f8;
            }
            float f12 = f5;
            if (f8 > f5) {
                n7 = n3;
                f12 = f8;
            }
            f4 = f10;
            f6 = f11;
            f7 = f9;
            f5 = f12;
            n6 = n8;
            n5 = n9;
        }
        n3 = n6;
        if (n6 == -1) {
            n3 = n4;
        }
        n4 = n5;
        if (n5 == -1) {
            n4 = n7;
        }
        return new d((c.c)list.get(n3), (c.c)list.get(n4));
    }

    private int O2(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        int n4 = this.O();
        int n5 = 0;
        if (n4 != 0 && n3 != 0) {
            if (this.y == null) {
                this.L2(v3);
            }
            if (this.e() <= this.q2(this.y).n()) {
                return 0;
            }
            n4 = CarouselLayoutManager.h2(n3, this.s, this.t, this.u);
            this.s += n4;
            this.V2(this.y);
            float f3 = this.z.g() / 2.0f;
            float f4 = this.e2(this.l0(this.N(0)));
            Rect rect = new Rect();
            float f5 = this.F2() ? this.z.i().b : this.z.b().b;
            float f6 = Float.MAX_VALUE;
            for (n3 = n5; n3 < this.O(); ++n3) {
                View view = this.N(n3);
                float f7 = Math.abs(f5 - this.K2(view, f4, f3, rect));
                float f8 = f6;
                if (view != null) {
                    f8 = f6;
                    if (f7 < f6) {
                        this.F = this.l0(view);
                        f8 = f7;
                    }
                }
                f4 = this.Y1(f4, this.z.g());
                f6 = f8;
            }
            this.k2(v3, z3);
            return n4;
        }
        return 0;
    }

    public static /* synthetic */ void Q1(CarouselLayoutManager carouselLayoutManager) {
        carouselLayoutManager.M2();
    }

    public static /* synthetic */ void R1(CarouselLayoutManager carouselLayoutManager, View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        carouselLayoutManager.getClass();
        if (n5 - n3 == n9 - n7 && n6 - n4 == n10 - n8) {
            return;
        }
        view.post((Runnable)new f2.d(carouselLayoutManager));
    }

    public static int h2(int n3, int n4, int n5, int n6) {
        int n7 = n4 + n3;
        if (n7 < n5) {
            return n5 - n4;
        }
        if (n7 > n6) {
            return n6 - n4;
        }
        return n3;
    }

    private int j2(int n3) {
        int n4 = this.v2();
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 17) {
                    if (n3 != 33) {
                        if (n3 != 66) {
                            if (n3 != 130) {
                                return Integer.MIN_VALUE;
                            }
                            if (n4 == 1) {
                                return 1;
                            }
                            return Integer.MIN_VALUE;
                        }
                        if (n4 == 0) {
                            if (this.F2()) {
                                return -1;
                            }
                            return 1;
                        }
                        return Integer.MIN_VALUE;
                    }
                    if (n4 == 1) {
                        return -1;
                    }
                    return Integer.MIN_VALUE;
                }
                if (n4 == 0) {
                    if (this.F2()) {
                        return 1;
                    }
                    return -1;
                }
                return Integer.MIN_VALUE;
            }
            return 1;
        }
        return -1;
    }

    @Override
    public int A(RecyclerView.z z3) {
        return this.u - this.t;
    }

    @Override
    public int A1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        if (this.p()) {
            return this.O2(n3, v3, z3);
        }
        return 0;
    }

    public final int A2() {
        return this.C.j();
    }

    @Override
    public void B1(int n3) {
        this.F = n3;
        if (this.y == null) {
            return;
        }
        this.s = this.C2(n3, this.r2(n3));
        this.A = j0.a.b(n3, 0, Math.max(0, this.e() - 1));
        this.V2(this.y);
        this.x1();
    }

    public final int B2() {
        if (this.R()) {
            return 0;
        }
        if (this.v2() == 1) {
            return this.h0();
        }
        return this.j0();
    }

    @Override
    public int C1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        if (this.q()) {
            return this.O2(n3, v3, z3);
        }
        return 0;
    }

    public final int C2(int n3, com.google.android.material.carousel.c c3) {
        if (this.F2()) {
            return (int)((float)this.n2() - c3.i().a - (float)n3 * c3.g() - c3.g() / 2.0f);
        }
        return (int)((float)n3 * c3.g() - c3.b().a + c3.g() / 2.0f);
    }

    public final int D2(int n3, com.google.android.material.carousel.c c3) {
        Iterator iterator = c3.f().iterator();
        int n4 = Integer.MAX_VALUE;
        while (iterator.hasNext()) {
            c.c c4 = (c.c)iterator.next();
            float f3 = (float)n3 * c3.g() + c3.g() / 2.0f;
            int n5 = this.F2() ? (int)((float)this.n2() - c4.a - f3) : (int)(f3 - c4.a);
            if (Math.abs(n4) <= Math.abs(n5 -= this.s)) continue;
            n4 = n5;
        }
        return n4;
    }

    @Override
    public void E0(View view, int n3, int n4) {
        if (view instanceof f) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            Object object = new Rect();
            this.o(view, (Rect)object);
            int n5 = ((Rect)object).left;
            int n6 = ((Rect)object).right;
            int n7 = ((Rect)object).top;
            int n8 = ((Rect)object).bottom;
            object = this.y;
            float f3 = object != null && this.C.a == 0 ? ((com.google.android.material.carousel.d)object).g().g() : (float)layoutParams.width;
            object = this.y;
            float f4 = object != null && this.C.a == 1 ? ((com.google.android.material.carousel.d)object).g().g() : (float)layoutParams.height;
            view.measure(RecyclerView.p.P(this.s0(), this.t0(), this.i0() + this.j0() + layoutParams.leftMargin + layoutParams.rightMargin + (n3 + (n5 + n6)), (int)f3, this.p()), RecyclerView.p.P(this.b0(), this.c0(), this.k0() + this.h0() + layoutParams.topMargin + layoutParams.bottomMargin + (n4 + (n7 + n8)), (int)f4, this.q()));
            return;
        }
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    public boolean F2() {
        return this.f() && this.d0() == 1;
    }

    public final boolean G2(float f3, d d3) {
        f3 = this.Z1(f3, this.t2(f3, d3) / 2.0f);
        if (this.F2()) {
            return f3 < 0.0f;
        }
        return f3 > (float)this.n2();
    }

    public final boolean H2(float f3, d d3) {
        f3 = this.Y1(f3, this.t2(f3, d3) / 2.0f);
        if (this.F2()) {
            return f3 > (float)this.n2();
        }
        return f3 < 0.0f;
    }

    @Override
    public RecyclerView.LayoutParams I() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public final void I2() {
        if (this.v && Log.isLoggable((String)"CarouselLayoutManager", (int)3)) {
            for (int i3 = 0; i3 < this.O(); ++i3) {
                View view = this.N(i3);
                this.o2(view);
                this.l0(view);
            }
        }
    }

    public final b J2(RecyclerView.v object, float f3, int n3) {
        View view = ((RecyclerView.v)object).o(n3);
        this.E0(view, 0, 0);
        f3 = this.Y1(f3, this.z.g() / 2.0f);
        object = CarouselLayoutManager.E2(this.z.h(), f3, false);
        return new b(view, f3, this.d2(f3, (d)object), (d)object);
    }

    @Override
    public void K0(RecyclerView recyclerView) {
        super.K0(recyclerView);
        this.x.f(recyclerView.getContext());
        this.M2();
        recyclerView.addOnLayoutChangeListener(this.D);
    }

    public final float K2(View view, float f3, float f4, Rect rect) {
        float f5 = this.Y1(f3, f4);
        d d3 = CarouselLayoutManager.E2(this.z.h(), f5, false);
        f3 = this.d2(f5, d3);
        super.U(view, rect);
        this.U2(view, f5, d3);
        this.C.m(view, rect, f4, f3);
        return f3;
    }

    public final void L2(RecyclerView.v object) {
        object = ((RecyclerView.v)object).o(0);
        this.E0((View)object, 0, 0);
        com.google.android.material.carousel.c c3 = this.x.g(this, (View)object);
        object = c3;
        if (this.F2()) {
            object = com.google.android.material.carousel.c.p(c3, this.n2());
        }
        this.y = com.google.android.material.carousel.d.f(this, (com.google.android.material.carousel.c)object, this.p2(), this.s2(), this.B2(), this.x.e());
    }

    @Override
    public void M0(RecyclerView recyclerView, RecyclerView.v v3) {
        super.M0(recyclerView, v3);
        recyclerView.removeOnLayoutChangeListener(this.D);
    }

    @Override
    public void M1(RecyclerView object, RecyclerView.z z3, int n3) {
        object = new g(this, object.getContext()){
            public final CarouselLayoutManager q;
            {
                this.q = carouselLayoutManager;
                super(context);
            }

            @Override
            public PointF a(int n3) {
                return this.q.d(n3);
            }

            @Override
            public int t(View view, int n3) {
                if (this.q.y != null && this.q.f()) {
                    CarouselLayoutManager carouselLayoutManager = this.q;
                    return carouselLayoutManager.g2(carouselLayoutManager.l0(view));
                }
                return 0;
            }

            @Override
            public int u(View view, int n3) {
                if (this.q.y != null && !this.q.f()) {
                    CarouselLayoutManager carouselLayoutManager = this.q;
                    return carouselLayoutManager.g2(carouselLayoutManager.l0(view));
                }
                return 0;
            }
        };
        ((RecyclerView.y)object).p(n3);
        this.N1((RecyclerView.y)object);
    }

    public final void M2() {
        this.y = null;
        this.x1();
    }

    @Override
    public View N0(View view, int n3, RecyclerView.v v3, RecyclerView.z z3) {
        if (this.O() == 0) {
            return null;
        }
        if ((n3 = this.j2(n3)) == Integer.MIN_VALUE) {
            return null;
        }
        if (n3 == -1) {
            if (this.l0(view) == 0) {
                return null;
            }
            this.a2(v3, this.l0(this.N(0)) - 1, 0);
            return this.m2();
        }
        if (this.l0(view) == this.e() - 1) {
            return null;
        }
        this.a2(v3, this.l0(this.N(this.O() - 1)) + 1, -1);
        return this.l2();
    }

    public final void N2(RecyclerView.v v3) {
        View view;
        float f3;
        while (this.O() > 0 && this.H2(f3 = this.o2(view = this.N(0)), CarouselLayoutManager.E2(this.z.h(), f3, true))) {
            this.q1(view, v3);
        }
        while (this.O() - 1 >= 0 && this.G2(f3 = this.o2(view = this.N(this.O() - 1)), CarouselLayoutManager.E2(this.z.h(), f3, true))) {
            this.q1(view, v3);
        }
    }

    @Override
    public void O0(AccessibilityEvent accessibilityEvent) {
        super.O0(accessibilityEvent);
        if (this.O() > 0) {
            accessibilityEvent.setFromIndex(this.l0(this.N(0)));
            accessibilityEvent.setToIndex(this.l0(this.N(this.O() - 1)));
        }
    }

    public final void P2(RecyclerView recyclerView, int n3) {
        if (this.f()) {
            recyclerView.scrollBy(n3, 0);
            return;
        }
        recyclerView.scrollBy(0, n3);
    }

    public void Q2(int n3) {
        this.G = n3;
        this.M2();
    }

    public final void R2(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            context = context.obtainStyledAttributes(attributeSet, z1.m.Carousel);
            this.Q2(context.getInt(z1.m.Carousel_carousel_alignment, 0));
            this.T2(context.getInt(i1.c.RecyclerView_android_orientation, 0));
            context.recycle();
        }
    }

    public void S2(a a4) {
        this.x = a4;
        this.M2();
    }

    public void T2(int n3) {
        if (n3 != 0 && n3 != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid orientation:");
            stringBuilder.append(n3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.l(null);
        e e3 = this.C;
        if (e3 != null && n3 == e3.a) {
            return;
        }
        this.C = f2.e.c(this, n3);
        this.M2();
    }

    @Override
    public void U(View view, Rect rect) {
        super.U(view, rect);
        float f3 = rect.centerY();
        if (this.f()) {
            f3 = rect.centerX();
        }
        float f4 = this.t2(f3, CarouselLayoutManager.E2(this.z.h(), f3, true));
        boolean bl = this.f();
        float f5 = 0.0f;
        f3 = bl ? ((float)rect.width() - f4) / 2.0f : 0.0f;
        if (!this.f()) {
            f5 = ((float)rect.height() - f4) / 2.0f;
        }
        rect.set((int)((float)rect.left + f3), (int)((float)rect.top + f5), (int)((float)rect.right - f3), (int)((float)rect.bottom - f5));
    }

    public final void U2(View view, float f3, d d3) {
        if (!(view instanceof f)) {
            return;
        }
        c.c c3 = d3.a;
        float f4 = c3.c;
        c.c c4 = d3.b;
        float f5 = a2.a.b(f4, c4.c, c3.a, c4.a, f3);
        float f6 = view.getHeight();
        float f7 = view.getWidth();
        f4 = a2.a.b(0.0f, f7 / 2.0f, 0.0f, 1.0f, f5);
        f5 = a2.a.b(0.0f, f6 / 2.0f, 0.0f, 1.0f, f5);
        c3 = this.C.e(f6, f7, f5, f4);
        f6 = this.d2(f3, d3);
        f3 = c3.height() / 2.0f;
        f4 = c3.height() / 2.0f;
        c4 = new RectF(f6 - c3.width() / 2.0f, f6 - f3, f6 + c3.width() / 2.0f, f4 + f6);
        d3 = new RectF((float)this.x2(), (float)this.A2(), (float)this.y2(), (float)this.w2());
        if (this.x.e() == a.a.c) {
            this.C.a((RectF)c3, (RectF)c4, (RectF)d3);
        }
        this.C.l((RectF)c3, (RectF)c4, (RectF)d3);
        ((f)view).setMaskRectF((RectF)c3);
    }

    @Override
    public void V0(RecyclerView recyclerView, int n3, int n4) {
        super.V0(recyclerView, n3, n4);
        this.W2();
    }

    public final void V2(com.google.android.material.carousel.d d3) {
        int n3 = this.u;
        int n4 = this.t;
        this.z = n3 <= n4 ? this.q2(d3) : d3.j(this.s, n4, n3);
        this.w.j(this.z.h());
    }

    @Override
    public void W0(RecyclerView recyclerView) {
        super.W0(recyclerView);
        this.W2();
    }

    public final void W2() {
        int n3;
        int n4 = this.e();
        if (n4 != (n3 = this.E) && this.y != null) {
            if (this.x.h(this, n3)) {
                this.M2();
            }
            this.E = n4;
        }
    }

    public final void X1(View view, int n3, b b3) {
        float f3 = this.z.g() / 2.0f;
        this.j(view, n3);
        this.E0(view, 0, 0);
        float f4 = b3.c;
        n3 = (int)(f4 - f3);
        int n4 = (int)(f4 + f3);
        this.C.k(view, n3, n4);
        this.U2(view, b3.b, b3.d);
    }

    public final void X2() {
        if (this.v && this.O() >= 1) {
            int n3 = 0;
            while (n3 < this.O() - 1) {
                int n4;
                int n5;
                int n6 = this.l0(this.N(n3));
                if (n6 <= (n5 = this.l0(this.N(n4 = n3 + 1)))) {
                    n3 = n4;
                    continue;
                }
                this.I2();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Detected invalid child order. Child at index [");
                stringBuilder.append(n3);
                stringBuilder.append("] had adapter position [");
                stringBuilder.append(n6);
                stringBuilder.append("] and child at index [");
                stringBuilder.append(n4);
                stringBuilder.append("] had adapter position [");
                stringBuilder.append(n5);
                stringBuilder.append("].");
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    @Override
    public void Y0(RecyclerView recyclerView, int n3, int n4) {
        super.Y0(recyclerView, n3, n4);
        this.W2();
    }

    public final float Y1(float f3, float f4) {
        if (this.F2()) {
            return f3 - f4;
        }
        return f3 + f4;
    }

    public final float Z1(float f3, float f4) {
        if (this.F2()) {
            return f3 + f4;
        }
        return f3 - f4;
    }

    @Override
    public int a() {
        return this.s0();
    }

    public final void a2(RecyclerView.v object, int n3, int n4) {
        if (n3 >= 0 && n3 < this.e()) {
            object = this.J2((RecyclerView.v)object, this.e2(n3), n3);
            this.X1(((b)object).a, n4, (b)object);
        }
    }

    @Override
    public int b() {
        return this.G;
    }

    @Override
    public void b1(RecyclerView.v v3, RecyclerView.z z3) {
        if (z3.b() > 0 && !((float)this.n2() <= 0.0f)) {
            boolean bl = this.F2();
            com.google.android.material.carousel.d d3 = this.y;
            boolean bl2 = d3 == null;
            if (bl2 || d3.g().a() != this.n2()) {
                this.L2(v3);
            }
            int n3 = this.i2(this.y);
            int n4 = this.f2(z3, this.y);
            int n5 = bl ? n4 : n3;
            this.t = n5;
            if (bl) {
                n4 = n3;
            }
            this.u = n4;
            if (bl2) {
                this.s = n3;
                this.B = this.y.i(this.e(), this.t, this.u, this.F2());
                n3 = this.F;
                if (n3 != -1) {
                    this.s = this.C2(n3, this.r2(n3));
                }
            }
            n3 = this.s;
            this.s = n3 + CarouselLayoutManager.h2(0, n3, this.t, this.u);
            this.A = j0.a.b(this.A, 0, z3.b());
            this.V2(this.y);
            this.B(v3);
            this.k2(v3, z3);
            this.E = this.e();
            return;
        }
        this.o1(v3);
        this.A = 0;
    }

    public final void b2(RecyclerView.v v3, RecyclerView.z z3, int n3) {
        d d3;
        float f3;
        float f4;
        float f5 = this.e2(n3);
        while (n3 < z3.b() && !this.G2(f4 = this.d2(f3 = this.Y1(f5, this.z.g() / 2.0f), d3 = CarouselLayoutManager.E2(this.z.h(), f3, false)), d3)) {
            f5 = this.Y1(f5, this.z.g());
            if (!this.H2(f4, d3)) {
                View view = v3.o(n3);
                this.X1(view, -1, new b(view, f3, f4, d3));
            }
            ++n3;
        }
    }

    @Override
    public int c() {
        return this.b0();
    }

    @Override
    public void c1(RecyclerView.z z3) {
        super.c1(z3);
        this.A = this.O() == 0 ? 0 : this.l0(this.N(0));
        this.X2();
    }

    public final void c2(RecyclerView.v v3, int n3) {
        d d3;
        float f3;
        float f4;
        float f5 = this.e2(n3);
        while (n3 >= 0 && !this.H2(f4 = this.d2(f3 = this.Y1(f5, this.z.g() / 2.0f), d3 = CarouselLayoutManager.E2(this.z.h(), f3, false)), d3)) {
            f5 = this.Z1(f5, this.z.g());
            if (!this.G2(f4, d3)) {
                View view = v3.o(n3);
                this.X1(view, 0, new b(view, f3, f4, d3));
            }
            --n3;
        }
    }

    @Override
    public PointF d(int n3) {
        if (this.y == null) {
            return null;
        }
        n3 = this.u2(n3, this.r2(n3));
        if (this.f()) {
            return new PointF((float)n3, 0.0f);
        }
        return new PointF(0.0f, (float)n3);
    }

    public final float d2(float f3, d object) {
        c.c c3 = ((d)object).a;
        float f4 = c3.b;
        c.c c4 = ((d)object).b;
        f4 = a2.a.b(f4, c4.b, c3.a, c4.a, f3);
        if (((d)object).b != this.z.d() && ((d)object).a != this.z.k()) {
            return f4;
        }
        object = ((d)object).b;
        return f4 + (f3 - ((c.c)object).a) * (1.0f - ((c.c)object).c);
    }

    public final float e2(int n3) {
        return this.Y1(this.z2() - this.s, this.z.g() * (float)n3);
    }

    @Override
    public boolean f() {
        return this.C.a == 0;
    }

    public final int f2(RecyclerView.z z3, com.google.android.material.carousel.d object) {
        boolean bl = this.F2();
        object = bl ? ((com.google.android.material.carousel.d)object).l() : ((com.google.android.material.carousel.d)object).h();
        c.c c3 = bl ? ((com.google.android.material.carousel.c)object).b() : ((com.google.android.material.carousel.c)object).i();
        int n3 = z3.b();
        int n4 = 1;
        float f3 = n3 - 1;
        float f4 = ((com.google.android.material.carousel.c)object).g();
        float f5 = bl ? -1.0f : 1.0f;
        float f6 = c3.a;
        float f7 = this.z2();
        if (bl) {
            n4 = -1;
        }
        n4 = (int)(f3 * f4 * f5 - (f6 - f7) + (float)n4 * c3.d / 2.0f);
        if (bl) {
            return Math.min(0, n4);
        }
        return Math.max(0, n4);
    }

    public int g2(int n3) {
        float f3 = this.C2(n3, this.r2(n3));
        return (int)((float)this.s - f3);
    }

    public final int i2(com.google.android.material.carousel.d object) {
        boolean bl = this.F2();
        object = bl ? ((com.google.android.material.carousel.d)object).h() : ((com.google.android.material.carousel.d)object).l();
        c.c c3 = bl ? ((com.google.android.material.carousel.c)object).i() : ((com.google.android.material.carousel.c)object).b();
        float f3 = this.Z1(c3.a, ((com.google.android.material.carousel.c)object).g() / 2.0f);
        return (int)((float)this.z2() - f3);
    }

    public final void k2(RecyclerView.v v3, RecyclerView.z z3) {
        this.N2(v3);
        if (this.O() == 0) {
            this.c2(v3, this.A - 1);
            this.b2(v3, z3, this.A);
        } else {
            int n3 = this.l0(this.N(0));
            int n4 = this.l0(this.N(this.O() - 1));
            this.c2(v3, n3 - 1);
            this.b2(v3, z3, n4 + 1);
        }
        this.X2();
    }

    public final View l2() {
        int n3 = this.F2() ? 0 : this.O() - 1;
        return this.N(n3);
    }

    public final View m2() {
        int n3 = this.F2() ? this.O() - 1 : 0;
        return this.N(n3);
    }

    public final int n2() {
        if (this.f()) {
            return this.a();
        }
        return this.c();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final float o2(View view) {
        int n3;
        Rect rect = new Rect();
        super.U(view, rect);
        if (this.f()) {
            n3 = rect.centerX();
            return n3;
        }
        n3 = rect.centerY();
        return n3;
    }

    @Override
    public boolean p() {
        return this.f();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int p2() {
        int n3;
        int n4;
        if (this.O() <= 0) return 0;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)this.N(0).getLayoutParams();
        if (this.C.a == 0) {
            n4 = layoutParams.leftMargin;
            n3 = layoutParams.rightMargin;
            return n4 + n3;
        }
        n4 = layoutParams.topMargin;
        n3 = layoutParams.bottomMargin;
        return n4 + n3;
    }

    @Override
    public boolean q() {
        return this.f() ^ true;
    }

    public final com.google.android.material.carousel.c q2(com.google.android.material.carousel.d d3) {
        if (this.F2()) {
            return d3.h();
        }
        return d3.l();
    }

    public final com.google.android.material.carousel.c r2(int n3) {
        Object object = this.B;
        if (object != null && (object = (com.google.android.material.carousel.c)object.get(j0.a.b(n3, 0, Math.max(0, this.e() - 1)))) != null) {
            return object;
        }
        return this.y.g();
    }

    public final int s2() {
        if (this.R()) {
            return 0;
        }
        if (this.v2() == 1) {
            return this.k0();
        }
        return this.i0();
    }

    public final float t2(float f3, d object) {
        c.c c3 = ((d)object).a;
        float f4 = c3.d;
        object = ((d)object).b;
        return a2.a.b(f4, ((c.c)object).d, c3.b, ((c.c)object).b, f3);
    }

    public int u2(int n3, com.google.android.material.carousel.c c3) {
        return this.C2(n3, c3) - this.s;
    }

    @Override
    public int v(RecyclerView.z z3) {
        if (this.O() != 0 && this.y != null && this.e() > 1) {
            float f3 = this.y.g().g() / (float)this.x(z3);
            return (int)((float)this.s0() * f3);
        }
        return 0;
    }

    public int v2() {
        return this.C.a;
    }

    @Override
    public int w(RecyclerView.z z3) {
        return this.s;
    }

    @Override
    public boolean w0() {
        return true;
    }

    @Override
    public boolean w1(RecyclerView recyclerView, View view, Rect object, boolean bl, boolean bl2) {
        if (this.y == null) {
            return false;
        }
        int n3 = this.D2(this.l0(view), this.r2(this.l0(view)));
        if (n3 == 0) {
            return false;
        }
        n3 = CarouselLayoutManager.h2(n3, this.s, this.t, this.u);
        object = this.y.j(this.s + n3, this.t, this.u);
        this.P2(recyclerView, this.D2(this.l0(view), (com.google.android.material.carousel.c)object));
        return true;
    }

    public final int w2() {
        return this.C.f();
    }

    @Override
    public int x(RecyclerView.z z3) {
        return this.u - this.t;
    }

    public final int x2() {
        return this.C.g();
    }

    @Override
    public int y(RecyclerView.z z3) {
        if (this.O() != 0 && this.y != null && this.e() > 1) {
            float f3 = this.y.g().g() / (float)this.A(z3);
            return (int)((float)this.b0() * f3);
        }
        return 0;
    }

    public final int y2() {
        return this.C.h();
    }

    @Override
    public int z(RecyclerView.z z3) {
        return this.s;
    }

    public final int z2() {
        return this.C.i();
    }

    public static final class b {
        public final View a;
        public final float b;
        public final float c;
        public final d d;

        public b(View view, float f3, float f4, d d3) {
            this.a = view;
            this.b = f3;
            this.c = f4;
            this.d = d3;
        }
    }

    public static class c
    extends RecyclerView.o {
        public final Paint a;
        public List b;

        public c() {
            Paint paint;
            this.a = paint = new Paint();
            this.b = Collections.unmodifiableList(new ArrayList());
            paint.setStrokeWidth(5.0f);
            paint.setColor(-65281);
        }

        @Override
        public void i(Canvas canvas, RecyclerView recyclerView, RecyclerView.z object) {
            super.i(canvas, recyclerView, (RecyclerView.z)object);
            this.a.setStrokeWidth(recyclerView.getResources().getDimension(z1.e.m3_carousel_debug_keyline_width));
            for (c.c c3 : this.b) {
                this.a.setColor(g0.a.c(-65281, -16776961, c3.c));
                if (((CarouselLayoutManager)recyclerView.getLayoutManager()).f()) {
                    canvas.drawLine(c3.b, (float)((CarouselLayoutManager)recyclerView.getLayoutManager()).A2(), c3.b, (float)((CarouselLayoutManager)recyclerView.getLayoutManager()).w2(), this.a);
                    continue;
                }
                canvas.drawLine((float)((CarouselLayoutManager)recyclerView.getLayoutManager()).x2(), c3.b, (float)((CarouselLayoutManager)recyclerView.getLayoutManager()).y2(), c3.b, this.a);
            }
        }

        public void j(List list) {
            this.b = Collections.unmodifiableList(list);
        }
    }

    public static class d {
        public final c.c a;
        public final c.c b;

        public d(c.c c3, c.c c4) {
            boolean bl = c3.a <= c4.a;
            n0.h.a(bl);
            this.a = c3;
            this.b = c4;
        }
    }
}

