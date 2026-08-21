/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.os.Build$VERSION
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.view.Gravity
 *  android.view.View
 */
package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import com.google.android.material.internal.u;
import com.google.android.material.internal.v;
import m0.m;
import m0.n;
import n0.h;
import s2.a;
import s2.d;
import s2.j;

public final class b {
    public Typeface A;
    public Typeface B;
    public Typeface C;
    public Typeface D;
    public a E;
    public a F;
    public TextUtils.TruncateAt G = TextUtils.TruncateAt.END;
    public CharSequence H;
    public CharSequence I;
    public boolean J;
    public boolean K = true;
    public float L;
    public float M;
    public float N;
    public float O;
    public float P;
    public int Q;
    public int R;
    public int[] S;
    public boolean T;
    public final TextPaint U;
    public final TextPaint V;
    public TimeInterpolator W;
    public TimeInterpolator X;
    public float Y;
    public float Z;
    public final View a;
    public float a0;
    public float b;
    public ColorStateList b0;
    public boolean c;
    public float c0;
    public float d;
    public float d0;
    public float e;
    public float e0;
    public int f;
    public ColorStateList f0;
    public final Rect g;
    public float g0;
    public final Rect h;
    public float h0;
    public Rect i;
    public float i0;
    public final RectF j;
    public StaticLayout j0;
    public int k = 16;
    public float k0;
    public int l = 16;
    public float l0;
    public float m = 15.0f;
    public float m0;
    public float n = 15.0f;
    public CharSequence n0;
    public ColorStateList o;
    public int o0 = 1;
    public ColorStateList p;
    public int p0 = 1;
    public int q;
    public float q0 = 0.0f;
    public float r;
    public float r0 = 1.0f;
    public float s;
    public int s0 = com.google.android.material.internal.u.o;
    public float t;
    public v t0;
    public float u;
    public int u0 = -1;
    public float v;
    public int v0 = -1;
    public float w;
    public boolean w0;
    public Typeface x;
    public Typeface y;
    public Typeface z;

    public b(View view) {
        TextPaint textPaint;
        this.a = view;
        this.U = textPaint = new TextPaint(129);
        this.V = new TextPaint((Paint)textPaint);
        this.h = new Rect();
        this.g = new Rect();
        this.j = new RectF();
        this.e = this.e();
        this.Z(view.getContext().getResources().getConfiguration());
    }

    public static boolean U(float f3, float f4) {
        return Math.abs(f3 - f4) < 1.0E-5f;
    }

    public static float Y(float f3, float f4, float f5, TimeInterpolator timeInterpolator) {
        float f6 = f5;
        if (timeInterpolator != null) {
            f6 = timeInterpolator.getInterpolation(f5);
        }
        return a2.a.a(f3, f4, f6);
    }

    public static int a(int n3, int n4, float f3) {
        float f4 = 1.0f - f3;
        float f5 = Color.alpha((int)n3);
        float f6 = Color.alpha((int)n4);
        float f7 = Color.red((int)n3);
        float f8 = Color.red((int)n4);
        float f9 = Color.green((int)n3);
        float f10 = Color.green((int)n4);
        float f11 = Color.blue((int)n3);
        float f12 = Color.blue((int)n4);
        return Color.argb((int)Math.round(f5 * f4 + f6 * f3), (int)Math.round(f7 * f4 + f8 * f3), (int)Math.round(f9 * f4 + f10 * f3), (int)Math.round(f11 * f4 + f12 * f3));
    }

    public static boolean d0(Rect rect, int n3, int n4, int n5, int n6) {
        return rect.left == n3 && rect.top == n4 && rect.right == n5 && rect.bottom == n6;
    }

    public int A() {
        return this.o0;
    }

    public void A0(float f3) {
        if (this.m != f3) {
            this.m = f3;
            this.b0();
        }
    }

    public float B() {
        this.R(this.V);
        return -this.V.ascent() + this.V.descent();
    }

    public void B0(Typeface typeface) {
        if (this.C0(typeface)) {
            this.b0();
        }
    }

    public int C() {
        return this.k;
    }

    public final boolean C0(Typeface object) {
        a a4 = this.E;
        if (a4 != null) {
            a4.c();
        }
        if (this.C != object) {
            this.C = object;
            a4 = s2.j.b(this.a.getContext().getResources().getConfiguration(), object);
            this.B = a4;
            object = a4;
            if (a4 == null) {
                object = this.C;
            }
            this.A = object;
            return true;
        }
        return false;
    }

    public float D() {
        int n3 = this.v0;
        if (n3 != -1) {
            return n3;
        }
        return this.E();
    }

    public void D0(float f3) {
        if ((f3 = j0.a.a(f3, 0.0f, 1.0f)) != this.b) {
            this.b = f3;
            this.c();
        }
    }

    public float E() {
        this.R(this.V);
        return -this.V.ascent();
    }

    public void E0(boolean bl) {
        this.c = bl;
    }

    public float F() {
        return this.m;
    }

    public void F0(float f3) {
        this.d = f3;
        this.e = this.e();
    }

    public Typeface G() {
        Typeface typeface = this.A;
        if (typeface != null) {
            return typeface;
        }
        return Typeface.DEFAULT;
    }

    public void G0(int n3) {
        this.s0 = n3;
    }

    public float H() {
        return this.b;
    }

    public final void H0(float f3) {
        this.h(f3);
        this.a.postInvalidateOnAnimation();
    }

    public float I() {
        return this.e;
    }

    public void I0(float f3) {
        this.q0 = f3;
    }

    public int J() {
        return this.s0;
    }

    public void J0(float f3) {
        this.r0 = f3;
    }

    public int K() {
        StaticLayout staticLayout = this.j0;
        if (staticLayout != null) {
            return staticLayout.getLineCount();
        }
        return 0;
    }

    public void K0(TimeInterpolator timeInterpolator) {
        this.W = timeInterpolator;
        this.b0();
    }

    public float L() {
        return this.j0.getSpacingAdd();
    }

    public void L0(boolean bl) {
        this.K = bl;
    }

    public float M() {
        return this.j0.getSpacingMultiplier();
    }

    public final boolean M0(int[] nArray) {
        this.S = nArray;
        if (this.W()) {
            this.b0();
            return true;
        }
        return false;
    }

    public final Layout.Alignment N() {
        int n3 = Gravity.getAbsoluteGravity((int)this.k, (int)(this.J ? 1 : 0)) & 7;
        if (n3 != 1) {
            if (n3 != 5) {
                if (this.J) {
                    return Layout.Alignment.ALIGN_OPPOSITE;
                }
                return Layout.Alignment.ALIGN_NORMAL;
            }
            if (this.J) {
                return Layout.Alignment.ALIGN_NORMAL;
            }
            return Layout.Alignment.ALIGN_OPPOSITE;
        }
        return Layout.Alignment.ALIGN_CENTER;
    }

    public void N0(v v3) {
        if (this.t0 != v3) {
            this.t0 = v3;
            this.c0(true);
        }
    }

    public TimeInterpolator O() {
        return this.W;
    }

    public void O0(CharSequence charSequence) {
        if (charSequence != null && TextUtils.equals((CharSequence)this.H, (CharSequence)charSequence)) {
            return;
        }
        this.H = charSequence;
        this.I = null;
        this.b0();
    }

    public CharSequence P() {
        return this.H;
    }

    public void P0(TimeInterpolator timeInterpolator) {
        this.X = timeInterpolator;
        this.b0();
    }

    public final void Q(TextPaint textPaint) {
        textPaint.setTextSize(this.n);
        textPaint.setTypeface(this.x);
        textPaint.setLetterSpacing(this.g0);
    }

    public void Q0(TextUtils.TruncateAt truncateAt) {
        this.G = truncateAt;
        this.b0();
    }

    public final void R(TextPaint textPaint) {
        textPaint.setTextSize(this.m);
        textPaint.setTypeface(this.A);
        textPaint.setLetterSpacing(this.h0);
    }

    public void R0(Typeface typeface) {
        boolean bl = this.p0(typeface);
        boolean bl2 = this.C0(typeface);
        if (!bl && !bl2) {
            return;
        }
        this.b0();
    }

    public TextUtils.TruncateAt S() {
        return this.G;
    }

    public final boolean S0() {
        return !(this.o0 <= 1 && this.p0 <= 1 || this.J && !this.c);
        {
        }
    }

    public final void T(float f3) {
        if (this.c) {
            RectF rectF = this.j;
            Rect rect = f3 < this.e ? this.g : this.h;
            rectF.set(rect);
            return;
        }
        this.j.left = com.google.android.material.internal.b.Y(this.g.left, this.h.left, f3, this.W);
        this.j.top = com.google.android.material.internal.b.Y(this.r, this.s, f3, this.W);
        this.j.right = com.google.android.material.internal.b.Y(this.g.right, this.h.right, f3, this.W);
        this.j.bottom = com.google.android.material.internal.b.Y(this.g.bottom, this.h.bottom, f3, this.W);
    }

    public final boolean T0() {
        return this.p0 == 1;
    }

    public void U0(int n3) {
        this.Q(this.V);
        int n4 = this.p0;
        TextPaint textPaint = this.V;
        CharSequence charSequence = this.H;
        float f3 = n3;
        this.u0 = this.j(n4, textPaint, charSequence, f3 * (this.n / this.m), this.J).getHeight();
        this.R(this.V);
        this.v0 = this.j(this.o0, this.V, this.H, f3, this.J).getHeight();
    }

    public final boolean V() {
        return this.a.getLayoutDirection() == 1;
    }

    public final boolean W() {
        ColorStateList colorStateList = this.p;
        return colorStateList != null && colorStateList.isStateful() || (colorStateList = this.o) != null && colorStateList.isStateful();
    }

    public final boolean X(CharSequence charSequence, boolean bl) {
        m m3 = bl ? m0.n.d : m0.n.c;
        return m3.isRtl(charSequence, 0, charSequence.length());
    }

    public void Z(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= 31) {
            Typeface typeface = this.z;
            if (typeface != null) {
                this.y = s2.j.b(configuration, typeface);
            }
            if ((typeface = this.C) != null) {
                this.B = s2.j.b(configuration, typeface);
            }
            if ((configuration = this.y) == null) {
                configuration = this.z;
            }
            this.x = configuration;
            configuration = this.B;
            if (configuration == null) {
                configuration = this.C;
            }
            this.A = configuration;
            this.c0(true);
        }
    }

    public final float a0(TextPaint textPaint, CharSequence charSequence) {
        return textPaint.measureText(charSequence, 0, charSequence.length());
    }

    public final void b(boolean bl) {
        float f3;
        CharSequence charSequence;
        this.i(1.0f, bl);
        if (this.I != null && this.j0 != null) {
            charSequence = this.T0() ? TextUtils.ellipsize((CharSequence)this.I, (TextPaint)this.U, (float)this.j0.getWidth(), (TextUtils.TruncateAt)this.G) : this.I;
            this.n0 = charSequence;
        }
        charSequence = this.n0;
        float f4 = 0.0f;
        this.k0 = charSequence != null ? this.a0(this.U, charSequence) : 0.0f;
        int n3 = Gravity.getAbsoluteGravity((int)this.l, (int)(this.J ? 1 : 0));
        charSequence = this.i;
        if (charSequence == null) {
            charSequence = this.h;
        }
        int n4 = n3 & 0x70;
        if (n4 != 48) {
            if (n4 != 80) {
                f3 = (this.U.descent() - this.U.ascent()) / 2.0f;
                this.s = (float)charSequence.centerY() - f3;
            } else {
                this.s = (float)((Rect)charSequence).bottom + this.U.ascent();
            }
        } else {
            this.s = ((Rect)charSequence).top;
        }
        this.u = (n3 &= 0x800007) != 1 ? (n3 != 5 ? (float)((Rect)charSequence).left : (float)((Rect)charSequence).right - this.k0) : (float)charSequence.centerX() - this.k0 / 2.0f;
        if (this.k0 <= (float)this.h.width()) {
            f3 = this.u;
            f3 += Math.max(0.0f, (float)this.h.left - f3);
            this.u = f3;
            this.u = f3 + Math.min(0.0f, (float)this.h.right - (this.k0 + f3));
        }
        if (this.m() <= (float)this.h.height()) {
            f3 = this.s;
            f3 += Math.max(0.0f, (float)this.h.top - f3);
            this.s = f3;
            this.s = f3 + Math.min(0.0f, (float)this.h.bottom - (this.r() + f3));
        }
        this.i(0.0f, bl);
        charSequence = this.j0;
        float f5 = charSequence != null ? (float)charSequence.getHeight() : 0.0f;
        charSequence = this.j0;
        f3 = charSequence != null && this.o0 > 1 ? (float)charSequence.getWidth() : ((charSequence = this.I) != null ? this.a0(this.U, charSequence) : 0.0f);
        charSequence = this.j0;
        n3 = charSequence != null ? charSequence.getLineCount() : 0;
        this.q = n3;
        n3 = Gravity.getAbsoluteGravity((int)this.k, (int)(this.J ? 1 : 0));
        n4 = n3 & 0x70;
        if (n4 != 48) {
            if (n4 != 80) {
                this.r = (float)this.g.centerY() - (f5 /= 2.0f);
            } else {
                float f6 = this.g.bottom;
                if (this.w0) {
                    f4 = this.U.descent();
                }
                this.r = f6 - f5 + f4;
            }
        } else {
            this.r = this.g.top;
        }
        this.t = (n3 &= 0x800007) != 1 ? (n3 != 5 ? (float)this.g.left : (float)this.g.right - f3) : (float)this.g.centerX() - f3 / 2.0f;
        this.H0(this.b);
    }

    public void b0() {
        this.c0(false);
    }

    public final void c() {
        this.g(this.b);
    }

    public void c0(boolean bl) {
        if (this.a.getHeight() > 0 && this.a.getWidth() > 0 || bl) {
            this.b(bl);
            this.c();
        }
    }

    public final float d(float f3) {
        float f4 = this.e;
        if (f3 <= f4) {
            return a2.a.b(1.0f, 0.0f, this.d, f4, f3);
        }
        return a2.a.b(0.0f, 1.0f, f4, 1.0f, f3);
    }

    public final float e() {
        float f3 = this.d;
        return f3 + (1.0f - f3) * 0.5f;
    }

    public void e0(ColorStateList colorStateList) {
        if (this.p == colorStateList && this.o == colorStateList) {
            return;
        }
        this.p = colorStateList;
        this.o = colorStateList;
        this.b0();
    }

    public final boolean f(CharSequence charSequence) {
        boolean bl = this.V();
        if (this.K) {
            return this.X(charSequence, bl);
        }
        return bl;
    }

    public void f0(int n3, int n4, int n5, int n6) {
        if (!com.google.android.material.internal.b.d0(this.h, n3, n4, n5, n6)) {
            this.h.set(n3, n4, n5, n6);
            this.T = true;
        }
    }

    public final void g(float f3) {
        int n3;
        float f4;
        this.T(f3);
        if (this.c) {
            if (f3 < this.e) {
                this.v = this.t;
                this.w = this.r;
                this.H0(0.0f);
                f4 = 0.0f;
            } else {
                this.v = this.u;
                this.w = this.s - (float)Math.max(0, this.f);
                this.H0(1.0f);
                f4 = 1.0f;
            }
        } else {
            this.v = com.google.android.material.internal.b.Y(this.t, this.u, f3, this.W);
            this.w = com.google.android.material.internal.b.Y(this.r, this.s, f3, this.W);
            this.H0(f3);
            f4 = f3;
        }
        TimeInterpolator timeInterpolator = a2.a.b;
        this.k0(1.0f - com.google.android.material.internal.b.Y(0.0f, 1.0f, 1.0f - f3, timeInterpolator));
        this.x0(com.google.android.material.internal.b.Y(1.0f, 0.0f, f3, timeInterpolator));
        if (this.p != this.o) {
            this.U.setColor(com.google.android.material.internal.b.a(this.y(), this.w(), f4));
        } else {
            this.U.setColor(this.w());
        }
        float f5 = this.g0;
        f4 = this.h0;
        if (f5 != f4) {
            this.U.setLetterSpacing(com.google.android.material.internal.b.Y(f4, f5, f3, timeInterpolator));
        } else {
            this.U.setLetterSpacing(f5);
        }
        this.N = com.google.android.material.internal.b.Y(this.c0, this.Y, f3, null);
        this.O = com.google.android.material.internal.b.Y(this.d0, this.Z, f3, null);
        this.P = com.google.android.material.internal.b.Y(this.e0, this.a0, f3, null);
        this.Q = n3 = com.google.android.material.internal.b.a(this.x(this.f0), this.x(this.b0), f3);
        this.U.setShadowLayer(this.N, this.O, this.P, n3);
        if (this.c) {
            n3 = this.U.getAlpha();
            n3 = (int)(this.d(f3) * (float)n3);
            this.U.setAlpha(n3);
            if (Build.VERSION.SDK_INT >= 31) {
                timeInterpolator = this.U;
                timeInterpolator.setShadowLayer(this.N, this.O, this.P, h2.a.a(this.Q, timeInterpolator.getAlpha()));
            }
        }
        this.a.postInvalidateOnAnimation();
    }

    public void g0(Rect rect) {
        this.f0(rect.left, rect.top, rect.right, rect.bottom);
    }

    public final void h(float f3) {
        this.i(f3, false);
    }

    public void h0(int n3, int n4, int n5, int n6) {
        if (this.i == null) {
            this.i = new Rect(n3, n4, n5, n6);
            this.T = true;
        }
        if (!com.google.android.material.internal.b.d0(this.i, n3, n4, n5, n6)) {
            this.i.set(n3, n4, n5, n6);
            this.T = true;
        }
    }

    public final void i(float f3, boolean bl) {
        StaticLayout staticLayout;
        int n3;
        Object object;
        float f4;
        float f5;
        block15: {
            block14: {
                float f6;
                float f7;
                if (this.H == null) break block14;
                float f8 = this.h.width();
                f5 = this.g.width();
                f4 = 1.0f;
                if (com.google.android.material.internal.b.U(f3, 1.0f)) {
                    float f9 = this.T0() ? this.n : this.m;
                    f7 = this.T0() ? this.g0 : this.h0;
                    f6 = this.T0() ? 1.0f : com.google.android.material.internal.b.Y(this.m, this.n, f3, this.X) / this.m;
                    this.L = f6;
                    if (this.T0()) {
                        f5 = f8;
                    }
                    object = this.x;
                    f6 = f9;
                } else {
                    f6 = this.m;
                    f7 = this.h0;
                    object = this.A;
                    this.L = com.google.android.material.internal.b.U(f3, 0.0f) ? 1.0f : com.google.android.material.internal.b.Y(this.m, this.n, f3, this.X) / this.m;
                    float f10 = this.n / this.m;
                    float f11 = f5;
                    if (!bl) {
                        if (this.c) {
                            f11 = f5;
                        } else {
                            f11 = f5;
                            if (f5 * f10 > f8) {
                                f11 = f5;
                                if (this.T0()) {
                                    f11 = Math.min(f8 / f10, f5);
                                }
                            }
                        }
                    }
                    f5 = f11;
                }
                n3 = f3 < 0.5f ? this.o0 : this.p0;
                boolean bl2 = false;
                bl = false;
                if (f5 > 0.0f) {
                    bl2 = this.M != f6;
                    boolean bl3 = this.i0 != f7;
                    boolean bl4 = this.D != object;
                    staticLayout = this.j0;
                    boolean bl5 = staticLayout != null && f5 != (float)staticLayout.getWidth();
                    boolean bl6 = this.R != n3;
                    bl2 = bl2 || bl3 || bl5 || bl4 || bl6 || this.T;
                    this.M = f6;
                    this.i0 = f7;
                    this.D = object;
                    this.T = false;
                    this.R = n3;
                    object = this.U;
                    if (this.L != 1.0f) {
                        bl = true;
                    }
                    object.setLinearText(bl);
                }
                if (this.I == null || bl2) break block15;
            }
            return;
        }
        this.U.setTextSize(this.M);
        this.U.setTypeface(this.D);
        this.U.setLetterSpacing(this.i0);
        this.J = this.f(this.H);
        if (!this.S0()) {
            n3 = 1;
        }
        staticLayout = this.U;
        object = this.H;
        f3 = this.T0() ? f4 : this.L;
        object = this.j(n3, (TextPaint)staticLayout, (CharSequence)object, f5 * f3, this.J);
        this.j0 = object;
        this.I = object.getText();
    }

    public void i0(int n3) {
        if (n3 != this.p0) {
            this.p0 = n3;
            this.b0();
        }
    }

    public final StaticLayout j(int n3, TextPaint textPaint, CharSequence charSequence, float f3, boolean bl) {
        Layout.Alignment alignment = n3 == 1 ? Layout.Alignment.ALIGN_NORMAL : this.N();
        return (StaticLayout)n0.h.g(com.google.android.material.internal.u.b(charSequence, textPaint, (int)f3).d(this.G).g(bl).c(alignment).f(false).i(n3).h(this.q0, this.r0).e(this.s0).j(this.t0).a());
    }

    public void j0(int n3) {
        Object object;
        d d3 = new d(this.a.getContext(), n3);
        if (d3.j() != null) {
            this.p = d3.j();
        }
        if (d3.k() != 0.0f) {
            this.n = d3.k();
        }
        if ((object = d3.c) != null) {
            this.b0 = object;
        }
        this.Z = d3.i;
        this.a0 = d3.j;
        this.Y = d3.k;
        this.g0 = d3.m;
        object = this.F;
        if (object != null) {
            ((a)object).c();
        }
        this.F = new a(new a.a(this){
            public final b a;
            {
                this.a = b3;
            }

            @Override
            public void a(Typeface typeface) {
                this.a.o0(typeface);
            }
        }, d3.e());
        d3.h(this.a.getContext(), this.F);
        this.b0();
    }

    public void k(Canvas canvas) {
        int n3 = canvas.save();
        if (this.I != null && this.j.width() > 0.0f && this.j.height() > 0.0f) {
            this.U.setTextSize(this.M);
            float f3 = this.v;
            float f4 = this.w;
            float f5 = this.L;
            if (f5 != 1.0f && !this.c) {
                canvas.scale(f5, f5, f3, f4);
            }
            if (this.S0() && this.T0() && (!this.c || this.b > this.e)) {
                this.l(canvas, this.v - (float)this.j0.getLineStart(0), f4);
            } else {
                canvas.translate(f3, f4);
                this.j0.draw(canvas);
            }
            canvas.restoreToCount(n3);
        }
    }

    public final void k0(float f3) {
        this.l0 = f3;
        this.a.postInvalidateOnAnimation();
    }

    public final void l(Canvas canvas, float f3, float f4) {
        int n3;
        Object object;
        int n4 = this.U.getAlpha();
        canvas.translate(f3, f4);
        if (!this.c) {
            this.U.setAlpha((int)(this.m0 * (float)n4));
            if (Build.VERSION.SDK_INT >= 31) {
                object = this.U;
                object.setShadowLayer(this.N, this.O, this.P, h2.a.a(this.Q, object.getAlpha()));
            }
            this.j0.draw(canvas);
        }
        if (!this.c) {
            this.U.setAlpha((int)(this.l0 * (float)n4));
        }
        if ((n3 = Build.VERSION.SDK_INT) >= 31) {
            object = this.U;
            object.setShadowLayer(this.N, this.O, this.P, h2.a.a(this.Q, object.getAlpha()));
        }
        int n5 = this.j0.getLineBaseline(0);
        object = this.n0;
        int n6 = object.length();
        f3 = n5;
        canvas.drawText((CharSequence)object, 0, n6, 0.0f, f3, (Paint)this.U);
        if (n3 >= 31) {
            this.U.setShadowLayer(this.N, this.O, this.P, this.Q);
        }
        if (!this.c) {
            String string = this.n0.toString().trim();
            object = string;
            if (string.endsWith("…")) {
                object = string.substring(0, string.length() - 1);
            }
            this.U.setAlpha(n4);
            canvas.drawText((String)object, 0, Math.min(this.j0.getLineEnd(0), ((String)object).length()), 0.0f, f3, (Paint)this.U);
        }
    }

    public void l0(ColorStateList colorStateList) {
        if (this.p != colorStateList) {
            this.p = colorStateList;
            this.b0();
        }
    }

    public float m() {
        this.Q(this.V);
        return -this.V.ascent() + this.V.descent();
    }

    public void m0(int n3) {
        if (this.l != n3) {
            this.l = n3;
            this.b0();
        }
    }

    public float n() {
        this.Q(this.V);
        return -this.V.ascent();
    }

    public void n0(float f3) {
        if (this.n != f3) {
            this.n = f3;
            this.b0();
        }
    }

    public void o(RectF rectF, int n3, int n4) {
        this.J = this.f(this.H);
        rectF.left = Math.max(this.s(n3, n4), (float)this.h.left);
        rectF.top = this.h.top;
        rectF.right = Math.min(this.t(rectF, n3, n4), (float)this.h.right);
        rectF.bottom = (float)this.h.top + this.r();
        if (this.j0 != null && !this.T0()) {
            StaticLayout staticLayout = this.j0;
            float f3 = staticLayout.getLineWidth(staticLayout.getLineCount() - 1) * (this.n / this.m);
            if (this.J) {
                rectF.left = rectF.right - f3;
                return;
            }
            rectF.right = rectF.left + f3;
        }
    }

    public void o0(Typeface typeface) {
        if (this.p0(typeface)) {
            this.b0();
        }
    }

    public ColorStateList p() {
        return this.p;
    }

    public final boolean p0(Typeface object) {
        a a4 = this.F;
        if (a4 != null) {
            a4.c();
        }
        if (this.z != object) {
            this.z = object;
            a4 = s2.j.b(this.a.getContext().getResources().getConfiguration(), object);
            this.y = a4;
            object = a4;
            if (a4 == null) {
                object = this.z;
            }
            this.x = object;
            return true;
        }
        return false;
    }

    public int q() {
        return this.l;
    }

    public void q0(int n3) {
        this.f = n3;
    }

    public float r() {
        int n3 = this.u0;
        if (n3 != -1) {
            return n3;
        }
        return this.n();
    }

    public void r0(int n3, int n4, int n5, int n6) {
        this.s0(n3, n4, n5, n6, true);
    }

    public final float s(int n3, int n4) {
        if (n4 != 17 && (n4 & 7) != 1) {
            if ((n4 & 0x800005) != 0x800005 && (n4 & 5) != 5) {
                if (this.J) {
                    return (float)this.h.right - this.k0;
                }
                return this.h.left;
            }
            if (this.J) {
                return this.h.left;
            }
            return (float)this.h.right - this.k0;
        }
        return (float)n3 / 2.0f - this.k0 / 2.0f;
    }

    public void s0(int n3, int n4, int n5, int n6, boolean bl) {
        if (com.google.android.material.internal.b.d0(this.g, n3, n4, n5, n6) && bl == this.w0) {
            return;
        }
        this.g.set(n3, n4, n5, n6);
        this.T = true;
        this.w0 = bl;
    }

    public final float t(RectF rectF, int n3, int n4) {
        if (n4 != 17 && (n4 & 7) != 1) {
            if ((n4 & 0x800005) != 0x800005 && (n4 & 5) != 5) {
                if (this.J) {
                    return this.h.right;
                }
                return rectF.left + this.k0;
            }
            if (this.J) {
                return rectF.left + this.k0;
            }
            return this.h.right;
        }
        return (float)n3 / 2.0f + this.k0 / 2.0f;
    }

    public void t0(Rect rect) {
        this.r0(rect.left, rect.top, rect.right, rect.bottom);
    }

    public float u() {
        return this.n;
    }

    public void u0(float f3) {
        if (this.h0 != f3) {
            this.h0 = f3;
            this.b0();
        }
    }

    public Typeface v() {
        Typeface typeface = this.x;
        if (typeface != null) {
            return typeface;
        }
        return Typeface.DEFAULT;
    }

    public void v0(int n3) {
        if (n3 != this.o0) {
            this.o0 = n3;
            this.b0();
        }
    }

    public int w() {
        return this.x(this.p);
    }

    public void w0(int n3) {
        Object object;
        d d3 = new d(this.a.getContext(), n3);
        if (d3.j() != null) {
            this.o = d3.j();
        }
        if (d3.k() != 0.0f) {
            this.m = d3.k();
        }
        if ((object = d3.c) != null) {
            this.f0 = object;
        }
        this.d0 = d3.i;
        this.e0 = d3.j;
        this.c0 = d3.k;
        this.h0 = d3.m;
        object = this.E;
        if (object != null) {
            ((a)object).c();
        }
        this.E = new a(new a.a(this){
            public final b a;
            {
                this.a = b3;
            }

            @Override
            public void a(Typeface typeface) {
                this.a.B0(typeface);
            }
        }, d3.e());
        d3.h(this.a.getContext(), this.E);
        this.b0();
    }

    public final int x(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        int[] nArray = this.S;
        if (nArray != null) {
            return colorStateList.getColorForState(nArray, 0);
        }
        return colorStateList.getDefaultColor();
    }

    public final void x0(float f3) {
        this.m0 = f3;
        this.a.postInvalidateOnAnimation();
    }

    public final int y() {
        return this.x(this.o);
    }

    public void y0(ColorStateList colorStateList) {
        if (this.o != colorStateList) {
            this.o = colorStateList;
            this.b0();
        }
    }

    public int z() {
        return this.q;
    }

    public void z0(int n3) {
        if (this.k != n3) {
            this.k = n3;
            this.b0();
        }
    }
}

