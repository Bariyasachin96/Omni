/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.RippleDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.OvalShape
 *  android.graphics.drawable.shapes.Shape
 *  android.text.TextPaint
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 */
package com.google.android.material.chip;

import a2.h;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.w;
import com.google.android.material.internal.z;
import j2.d;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import s2.c;
import v2.i;
import z1.m;

public class a
extends i
implements Drawable.Callback,
w.b {
    public static final int[] V0 = new int[]{16842910};
    public static final ShapeDrawable W0 = new ShapeDrawable((Shape)new OvalShape());
    public int A0;
    public int B0;
    public int C0;
    public int D0;
    public int E0;
    public int F0;
    public boolean G0;
    public int H0;
    public int I0 = 255;
    public ColorFilter J0;
    public PorterDuffColorFilter K0;
    public ColorStateList L;
    public ColorStateList L0;
    public ColorStateList M;
    public PorterDuff.Mode M0;
    public float N;
    public int[] N0;
    public float O = -1.0f;
    public boolean O0;
    public ColorStateList P;
    public ColorStateList P0;
    public float Q;
    public WeakReference Q0;
    public ColorStateList R;
    public TextUtils.TruncateAt R0;
    public CharSequence S;
    public boolean S0;
    public boolean T;
    public int T0;
    public Drawable U;
    public boolean U0;
    public ColorStateList V;
    public float W;
    public boolean X;
    public boolean Y;
    public Drawable Z;
    public Drawable a0;
    public ColorStateList b0;
    public float c0;
    public CharSequence d0;
    public boolean e0;
    public boolean f0;
    public Drawable g0;
    public ColorStateList h0;
    public h i0;
    public h j0;
    public float k0;
    public float l0;
    public float m0;
    public float n0;
    public float o0;
    public float p0;
    public float q0;
    public float r0;
    public final Context s0;
    public final Paint t0 = new Paint(1);
    public final Paint u0;
    public final Paint.FontMetrics v0 = new Paint.FontMetrics();
    public final RectF w0 = new RectF();
    public final PointF x0 = new PointF();
    public final Path y0 = new Path();
    public final w z0;

    public a(Context object, AttributeSet object2, int n3, int n4) {
        super((Context)object, (AttributeSet)object2, n3, n4);
        this.M0 = PorterDuff.Mode.SRC_IN;
        this.Q0 = new WeakReference<Object>(null);
        this.W((Context)object);
        this.s0 = object;
        object2 = new w(this);
        this.z0 = object2;
        this.S = "";
        ((w)object2).g().density = object.getResources().getDisplayMetrics().density;
        this.u0 = null;
        object = V0;
        this.setState((int[])object);
        this.E2((int[])object);
        this.S0 = true;
        W0.setTint(-1);
    }

    public static boolean F1(int[] nArray, int n3) {
        if (nArray == null) {
            return false;
        }
        int n4 = nArray.length;
        for (int i3 = 0; i3 < n4; ++i3) {
            if (nArray[i3] != n3) continue;
            return true;
        }
        return false;
    }

    public static boolean J1(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    public static boolean K1(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    public static boolean L1(s2.d d3) {
        return d3 != null && d3.j() != null && d3.j().isStateful();
    }

    public static a N0(Context object, AttributeSet attributeSet, int n3, int n4) {
        object = new a((Context)object, attributeSet, n3, n4);
        ((a)object).M1(attributeSet, n3, n4);
        return object;
    }

    public s2.d A1() {
        return this.z0.e();
    }

    public void A2(float f3) {
        if (this.c0 != f3) {
            this.c0 = f3;
            this.invalidateSelf();
            if (this.i3()) {
                this.N1();
            }
        }
    }

    public float B1() {
        return this.o0;
    }

    public void B2(int n3) {
        this.A2(this.s0.getResources().getDimension(n3));
    }

    public final void C0(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
            h0.a.m(drawable, h0.a.f(this));
            drawable.setLevel(this.getLevel());
            drawable.setVisible(this.isVisible(), false);
            if (drawable == this.Z) {
                if (drawable.isStateful()) {
                    drawable.setState(this.o1());
                }
                drawable.setTintList(this.b0);
                return;
            }
            Drawable drawable2 = this.U;
            if (drawable == drawable2 && this.X) {
                drawable2.setTintList(this.V);
            }
            if (drawable.isStateful()) {
                drawable.setState(this.getState());
            }
        }
    }

    public float C1() {
        return this.n0;
    }

    public void C2(float f3) {
        if (this.p0 != f3) {
            this.p0 = f3;
            this.invalidateSelf();
            if (this.i3()) {
                this.N1();
            }
        }
    }

    public final void D0(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (!this.h3() && !this.g3()) {
            return;
        }
        float f3 = this.k0 + this.l0;
        float f4 = this.s1();
        if (h0.a.f(this) == 0) {
            rectF.left = f3 = (float)rect.left + f3;
            rectF.right = f3 + f4;
        } else {
            rectF.right = f3 = (float)rect.right - f3;
            rectF.left = f3 - f4;
        }
        f4 = this.r1();
        rectF.top = f3 = rect.exactCenterY() - f4 / 2.0f;
        rectF.bottom = f3 + f4;
    }

    public final ColorFilter D1() {
        ColorFilter colorFilter = this.J0;
        if (colorFilter != null) {
            return colorFilter;
        }
        return this.K0;
    }

    public void D2(int n3) {
        this.C2(this.s0.getResources().getDimension(n3));
    }

    public float E0() {
        if (!this.h3() && !this.g3()) {
            return 0.0f;
        }
        return this.l0 + this.s1() + this.m0;
    }

    public boolean E1() {
        return this.O0;
    }

    public boolean E2(int[] nArray) {
        if (!Arrays.equals(this.N0, nArray)) {
            this.N0 = nArray;
            if (this.i3()) {
                return this.O1(this.getState(), nArray);
            }
        }
        return false;
    }

    public final void F0(Rect rect, RectF rectF) {
        rectF.set(rect);
        if (this.i3()) {
            float f3 = this.r0 + this.q0 + this.c0 + this.p0 + this.o0;
            if (h0.a.f(this) == 0) {
                rectF.right = (float)rect.right - f3;
                return;
            }
            rectF.left = (float)rect.left + f3;
        }
    }

    public void F2(ColorStateList colorStateList) {
        if (this.b0 != colorStateList) {
            this.b0 = colorStateList;
            if (this.i3()) {
                this.Z.setTintList(colorStateList);
            }
            this.onStateChange(this.getState());
        }
    }

    public final void G0(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (this.i3()) {
            float f3 = this.r0 + this.q0;
            if (h0.a.f(this) == 0) {
                rectF.right = f3 = (float)rect.right - f3;
                rectF.left = f3 - this.c0;
            } else {
                rectF.left = f3 = (float)rect.left + f3;
                rectF.right = f3 + this.c0;
            }
            float f4 = rect.exactCenterY();
            f3 = this.c0;
            rectF.top = f4 -= f3 / 2.0f;
            rectF.bottom = f4 + f3;
        }
    }

    public boolean G1() {
        return this.e0;
    }

    public void G2(int n3) {
        this.F2(d.a.a(this.s0, n3));
    }

    public final void H0(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (this.i3()) {
            float f3 = this.r0 + this.q0 + this.c0 + this.p0 + this.o0;
            if (h0.a.f(this) == 0) {
                float f4;
                rectF.right = f4 = (float)rect.right;
                rectF.left = f4 - f3;
            } else {
                int n3 = rect.left;
                rectF.left = n3;
                rectF.right = (float)n3 + f3;
            }
            rectF.top = rect.top;
            rectF.bottom = rect.bottom;
        }
    }

    public boolean H1() {
        return a.K1(this.Z);
    }

    public void H2(boolean bl) {
        if (this.Y != bl) {
            boolean bl2 = this.i3();
            this.Y = bl;
            bl = this.i3();
            if (bl2 != bl) {
                if (bl) {
                    this.C0(this.Z);
                } else {
                    this.j3(this.Z);
                }
                this.invalidateSelf();
                this.N1();
            }
        }
    }

    public float I0() {
        if (this.i3()) {
            return this.p0 + this.c0 + this.q0;
        }
        return 0.0f;
    }

    public boolean I1() {
        return this.Y;
    }

    public void I2(a a4) {
        this.Q0 = new WeakReference<a>(a4);
    }

    public final void J0(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (this.S != null) {
            float f3 = this.k0 + this.E0() + this.n0;
            float f4 = this.r0 + this.I0() + this.o0;
            if (h0.a.f(this) == 0) {
                rectF.left = (float)rect.left + f3;
                rectF.right = (float)rect.right - f4;
            } else {
                rectF.left = (float)rect.left + f4;
                rectF.right = (float)rect.right - f3;
            }
            rectF.top = rect.top;
            rectF.bottom = rect.bottom;
        }
    }

    public void J2(TextUtils.TruncateAt truncateAt) {
        this.R0 = truncateAt;
    }

    public final float K0() {
        this.z0.g().getFontMetrics(this.v0);
        Paint.FontMetrics fontMetrics = this.v0;
        return (fontMetrics.descent + fontMetrics.ascent) / 2.0f;
    }

    public void K2(h h3) {
        this.j0 = h3;
    }

    public Paint.Align L0(Rect rect, PointF pointF) {
        Paint.Align align;
        pointF.set(0.0f, 0.0f);
        Paint.Align align2 = align = Paint.Align.LEFT;
        if (this.S != null) {
            float f3 = this.k0 + this.E0() + this.n0;
            if (h0.a.f(this) == 0) {
                pointF.x = (float)rect.left + f3;
                align2 = align;
            } else {
                pointF.x = (float)rect.right - f3;
                align2 = Paint.Align.RIGHT;
            }
            pointF.y = (float)rect.centerY() - this.K0();
        }
        return align2;
    }

    public void L2(int n3) {
        this.K2(a2.h.d(this.s0, n3));
    }

    public final boolean M0() {
        return this.f0 && this.g0 != null && this.e0;
    }

    public final void M1(AttributeSet attributeSet, int n3, int n4) {
        TypedArray typedArray = com.google.android.material.internal.z.i(this.s0, attributeSet, z1.m.Chip, n3, n4, new int[0]);
        this.U0 = typedArray.hasValue(z1.m.Chip_shapeAppearance);
        this.u2(s2.c.a(this.s0, typedArray, z1.m.Chip_chipSurfaceColor));
        this.Y1(s2.c.a(this.s0, typedArray, z1.m.Chip_chipBackgroundColor));
        this.m2(typedArray.getDimension(z1.m.Chip_chipMinHeight, 0.0f));
        n3 = z1.m.Chip_chipCornerRadius;
        if (typedArray.hasValue(n3)) {
            this.a2(typedArray.getDimension(n3, 0.0f));
        }
        this.q2(s2.c.a(this.s0, typedArray, z1.m.Chip_chipStrokeColor));
        this.s2(typedArray.getDimension(z1.m.Chip_chipStrokeWidth, 0.0f));
        this.R2(s2.c.a(this.s0, typedArray, z1.m.Chip_rippleColor));
        this.W2(typedArray.getText(z1.m.Chip_android_text));
        s2.d d3 = s2.c.h(this.s0, typedArray, z1.m.Chip_android_textAppearance);
        d3.o(typedArray.getDimension(z1.m.Chip_android_textSize, d3.k()));
        this.X2(d3);
        n3 = typedArray.getInt(z1.m.Chip_android_ellipsize, 0);
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 == 3) {
                    this.J2(TextUtils.TruncateAt.END);
                }
            } else {
                this.J2(TextUtils.TruncateAt.MIDDLE);
            }
        } else {
            this.J2(TextUtils.TruncateAt.START);
        }
        this.l2(typedArray.getBoolean(z1.m.Chip_chipIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") == null) {
            this.l2(typedArray.getBoolean(z1.m.Chip_chipIconEnabled, false));
        }
        this.e2(s2.c.e(this.s0, typedArray, z1.m.Chip_chipIcon));
        n3 = z1.m.Chip_chipIconTint;
        if (typedArray.hasValue(n3)) {
            this.i2(s2.c.a(this.s0, typedArray, n3));
        }
        this.g2(typedArray.getDimension(z1.m.Chip_chipIconSize, -1.0f));
        this.H2(typedArray.getBoolean(z1.m.Chip_closeIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") == null) {
            this.H2(typedArray.getBoolean(z1.m.Chip_closeIconEnabled, false));
        }
        this.v2(s2.c.e(this.s0, typedArray, z1.m.Chip_closeIcon));
        this.F2(s2.c.a(this.s0, typedArray, z1.m.Chip_closeIconTint));
        this.A2(typedArray.getDimension(z1.m.Chip_closeIconSize, 0.0f));
        this.Q1(typedArray.getBoolean(z1.m.Chip_android_checkable, false));
        this.X1(typedArray.getBoolean(z1.m.Chip_checkedIconVisible, false));
        if (attributeSet != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") != null && attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") == null) {
            this.X1(typedArray.getBoolean(z1.m.Chip_checkedIconEnabled, false));
        }
        this.S1(s2.c.e(this.s0, typedArray, z1.m.Chip_checkedIcon));
        n3 = z1.m.Chip_checkedIconTint;
        if (typedArray.hasValue(n3)) {
            this.U1(s2.c.a(this.s0, typedArray, n3));
        }
        this.U2(a2.h.c(this.s0, typedArray, z1.m.Chip_showMotionSpec));
        this.K2(a2.h.c(this.s0, typedArray, z1.m.Chip_hideMotionSpec));
        this.o2(typedArray.getDimension(z1.m.Chip_chipStartPadding, 0.0f));
        this.O2(typedArray.getDimension(z1.m.Chip_iconStartPadding, 0.0f));
        this.M2(typedArray.getDimension(z1.m.Chip_iconEndPadding, 0.0f));
        this.c3(typedArray.getDimension(z1.m.Chip_textStartPadding, 0.0f));
        this.Z2(typedArray.getDimension(z1.m.Chip_textEndPadding, 0.0f));
        this.C2(typedArray.getDimension(z1.m.Chip_closeIconStartPadding, 0.0f));
        this.x2(typedArray.getDimension(z1.m.Chip_closeIconEndPadding, 0.0f));
        this.c2(typedArray.getDimension(z1.m.Chip_chipEndPadding, 0.0f));
        this.Q2(typedArray.getDimensionPixelSize(z1.m.Chip_android_maxWidth, Integer.MAX_VALUE));
        typedArray.recycle();
    }

    public void M2(float f3) {
        if (this.m0 != f3) {
            float f4 = this.E0();
            this.m0 = f3;
            f3 = this.E0();
            this.invalidateSelf();
            if (f4 != f3) {
                this.N1();
            }
        }
    }

    public void N1() {
        a a4 = (a)this.Q0.get();
        if (a4 != null) {
            a4.a();
        }
    }

    public void N2(int n3) {
        this.M2(this.s0.getResources().getDimension(n3));
    }

    public final void O0(Canvas canvas, Rect rect) {
        if (this.g3()) {
            this.D0(rect, this.w0);
            rect = this.w0;
            float f3 = rect.left;
            float f4 = rect.top;
            canvas.translate(f3, f4);
            this.g0.setBounds(0, 0, (int)this.w0.width(), (int)this.w0.height());
            this.g0.draw(canvas);
            canvas.translate(-f3, -f4);
        }
    }

    public final boolean O1(int[] nArray, int[] nArray2) {
        int n3;
        boolean bl = super.onStateChange(nArray);
        Object object = this.L;
        int n4 = object != null ? object.getColorForState(nArray, this.A0) : 0;
        int n5 = this.q(n4);
        n4 = this.A0;
        boolean bl2 = true;
        if (n4 != n5) {
            this.A0 = n5;
            bl = true;
        }
        n4 = (object = this.M) != null ? object.getColorForState(nArray, this.B0) : 0;
        if (this.B0 != (n4 = this.q(n4))) {
            this.B0 = n4;
            bl = true;
        }
        if (((n4 = this.C0 != (n3 = h2.a.i(n5, n4)) ? 1 : 0) | (n5 = this.D() == null ? 1 : 0)) != 0) {
            this.C0 = n3;
            this.i0(ColorStateList.valueOf((int)n3));
            bl = true;
        }
        n4 = (object = this.P) != null ? object.getColorForState(nArray, this.D0) : 0;
        boolean bl3 = bl;
        if (this.D0 != n4) {
            this.D0 = n4;
            bl3 = true;
        }
        n4 = this.P0 != null && t2.a.e(nArray) ? this.P0.getColorForState(nArray, this.E0) : 0;
        bl = bl3;
        if (this.E0 != n4) {
            this.E0 = n4;
            bl = bl3;
            if (this.O0) {
                bl = true;
            }
        }
        if (this.F0 != (n4 = this.z0.e() != null && this.z0.e().j() != null ? this.z0.e().j().getColorForState(nArray, this.F0) : 0)) {
            this.F0 = n4;
            bl = true;
        }
        if (this.G0 != (bl3 = a.F1(this.getState(), 0x10100A0) && this.e0) && this.g0 != null) {
            float f3 = this.E0();
            this.G0 = bl3;
            if (f3 != this.E0()) {
                bl = true;
                n4 = 1;
            } else {
                n4 = 0;
                bl = true;
            }
        } else {
            n4 = 0;
        }
        object = this.L0;
        n5 = object != null ? object.getColorForState(nArray, this.H0) : 0;
        if (this.H0 != n5) {
            this.H0 = n5;
            this.K0 = j2.d.o(this, this.L0, this.M0);
            bl3 = bl2;
        } else {
            bl3 = bl;
        }
        bl = bl3;
        if (a.K1(this.U)) {
            bl = bl3 | this.U.setState(nArray);
        }
        bl3 = bl;
        if (a.K1(this.g0)) {
            bl3 = bl | this.g0.setState(nArray);
        }
        bl = bl3;
        if (a.K1(this.Z)) {
            object = new int[nArray.length + nArray2.length];
            System.arraycopy(nArray, 0, object, 0, nArray.length);
            System.arraycopy(nArray2, 0, object, nArray.length, nArray2.length);
            bl = bl3 | this.Z.setState((int[])object);
        }
        bl3 = bl;
        if (a.K1(this.a0)) {
            bl3 = bl | this.a0.setState(nArray2);
        }
        if (bl3) {
            this.invalidateSelf();
        }
        if (n4 != 0) {
            this.N1();
        }
        return bl3;
    }

    public void O2(float f3) {
        if (this.l0 != f3) {
            float f4 = this.E0();
            this.l0 = f3;
            f3 = this.E0();
            this.invalidateSelf();
            if (f4 != f3) {
                this.N1();
            }
        }
    }

    public final void P0(Canvas canvas, Rect rect) {
        if (!this.U0) {
            this.t0.setColor(this.B0);
            this.t0.setStyle(Paint.Style.FILL);
            this.t0.setColorFilter(this.D1());
            this.w0.set(rect);
            canvas.drawRoundRect(this.w0, this.a1(), this.a1(), this.t0);
        }
    }

    public boolean P1(boolean bl) {
        if (this.Z != null) {
            int[] nArray = bl ? new int[]{16842919, 16842910} : V0;
            return this.E2(nArray);
        }
        return false;
    }

    public void P2(int n3) {
        this.O2(this.s0.getResources().getDimension(n3));
    }

    public final void Q0(Canvas canvas, Rect rect) {
        if (this.h3()) {
            this.D0(rect, this.w0);
            rect = this.w0;
            float f3 = rect.left;
            float f4 = rect.top;
            canvas.translate(f3, f4);
            this.U.setBounds(0, 0, (int)this.w0.width(), (int)this.w0.height());
            this.U.draw(canvas);
            canvas.translate(-f3, -f4);
        }
    }

    public void Q1(boolean bl) {
        if (this.e0 != bl) {
            this.e0 = bl;
            float f3 = this.E0();
            if (!bl && this.G0) {
                this.G0 = false;
            }
            float f4 = this.E0();
            this.invalidateSelf();
            if (f3 != f4) {
                this.N1();
            }
        }
    }

    public void Q2(int n3) {
        this.T0 = n3;
    }

    public final void R0(Canvas canvas, Rect rect) {
        if (this.Q > 0.0f && !this.U0) {
            this.t0.setColor(this.D0);
            this.t0.setStyle(Paint.Style.STROKE);
            if (!this.U0) {
                this.t0.setColorFilter(this.D1());
            }
            RectF rectF = this.w0;
            float f3 = rect.left;
            float f4 = this.Q;
            rectF.set(f3 + f4 / 2.0f, (float)rect.top + f4 / 2.0f, (float)rect.right - f4 / 2.0f, (float)rect.bottom - f4 / 2.0f);
            f3 = this.O - this.Q / 2.0f;
            canvas.drawRoundRect(this.w0, f3, f3, this.t0);
        }
    }

    public void R1(int n3) {
        this.Q1(this.s0.getResources().getBoolean(n3));
    }

    public void R2(ColorStateList colorStateList) {
        if (this.R != colorStateList) {
            this.R = colorStateList;
            this.k3();
            this.onStateChange(this.getState());
        }
    }

    public final void S0(Canvas canvas, Rect rect) {
        if (!this.U0) {
            this.t0.setColor(this.A0);
            this.t0.setStyle(Paint.Style.FILL);
            this.w0.set(rect);
            canvas.drawRoundRect(this.w0, this.a1(), this.a1(), this.t0);
        }
    }

    public void S1(Drawable drawable) {
        if (this.g0 != drawable) {
            float f3 = this.E0();
            this.g0 = drawable;
            float f4 = this.E0();
            this.j3(this.g0);
            this.C0(this.g0);
            this.invalidateSelf();
            if (f3 != f4) {
                this.N1();
            }
        }
    }

    public void S2(int n3) {
        this.R2(d.a.a(this.s0, n3));
    }

    public final void T0(Canvas canvas, Rect rect) {
        if (this.i3()) {
            this.G0(rect, this.w0);
            rect = this.w0;
            float f3 = rect.left;
            float f4 = rect.top;
            canvas.translate(f3, f4);
            this.Z.setBounds(0, 0, (int)this.w0.width(), (int)this.w0.height());
            this.a0.setBounds(this.Z.getBounds());
            this.a0.jumpToCurrentState();
            this.a0.draw(canvas);
            canvas.translate(-f3, -f4);
        }
    }

    public void T1(int n3) {
        this.S1(d.a.b(this.s0, n3));
    }

    public void T2(boolean bl) {
        this.S0 = bl;
    }

    public final void U0(Canvas canvas, Rect rect) {
        this.t0.setColor(this.E0);
        this.t0.setStyle(Paint.Style.FILL);
        this.w0.set(rect);
        if (!this.U0) {
            canvas.drawRoundRect(this.w0, this.a1(), this.a1(), this.t0);
            return;
        }
        this.l(new RectF(rect), this.y0);
        super.u(canvas, this.t0, this.y0, this.z());
    }

    public void U1(ColorStateList colorStateList) {
        if (this.h0 != colorStateList) {
            this.h0 = colorStateList;
            if (this.M0()) {
                this.g0.setTintList(colorStateList);
            }
            this.onStateChange(this.getState());
        }
    }

    public void U2(h h3) {
        this.i0 = h3;
    }

    public final void V0(Canvas canvas, Rect rect) {
        Paint paint = this.u0;
        if (paint != null) {
            paint.setColor(g0.a.k(-16777216, 127));
            canvas.drawRect(rect, this.u0);
            if (this.h3() || this.g3()) {
                this.D0(rect, this.w0);
                canvas.drawRect(this.w0, this.u0);
            }
            if (this.S != null) {
                canvas.drawLine((float)rect.left, rect.exactCenterY(), (float)rect.right, rect.exactCenterY(), this.u0);
            }
            if (this.i3()) {
                this.G0(rect, this.w0);
                canvas.drawRect(this.w0, this.u0);
            }
            this.u0.setColor(g0.a.k(-65536, 127));
            this.F0(rect, this.w0);
            canvas.drawRect(this.w0, this.u0);
            this.u0.setColor(g0.a.k(-16711936, 127));
            this.H0(rect, this.w0);
            canvas.drawRect(this.w0, this.u0);
        }
    }

    public void V1(int n3) {
        this.U1(d.a.a(this.s0, n3));
    }

    public void V2(int n3) {
        this.U2(a2.h.d(this.s0, n3));
    }

    public final void W0(Canvas canvas, Rect object) {
        if (this.S != null) {
            Object object2 = this.L0((Rect)object, this.x0);
            this.J0((Rect)object, this.w0);
            if (this.z0.e() != null) {
                this.z0.g().drawableState = this.getState();
                this.z0.n(this.s0);
            }
            this.z0.g().setTextAlign(object2);
            int n3 = Math.round(this.z0.h(this.z1().toString()));
            int n4 = Math.round(this.w0.width());
            int n5 = 0;
            n3 = n3 > n4 ? 1 : 0;
            if (n3 != 0) {
                n5 = canvas.save();
                canvas.clipRect(this.w0);
            }
            object2 = this.S;
            object = object2;
            if (n3 != 0) {
                object = object2;
                if (this.R0 != null) {
                    object = TextUtils.ellipsize((CharSequence)object2, (TextPaint)this.z0.g(), (float)this.w0.width(), (TextUtils.TruncateAt)this.R0);
                }
            }
            n4 = object.length();
            object2 = this.x0;
            canvas.drawText((CharSequence)object, 0, n4, object2.x, object2.y, (Paint)this.z0.g());
            if (n3 != 0) {
                canvas.restoreToCount(n5);
            }
        }
    }

    public void W1(int n3) {
        this.X1(this.s0.getResources().getBoolean(n3));
    }

    public void W2(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        if (!TextUtils.equals((CharSequence)this.S, (CharSequence)charSequence2)) {
            this.S = charSequence2;
            this.z0.m(true);
            this.invalidateSelf();
            this.N1();
        }
    }

    public Drawable X0() {
        return this.g0;
    }

    public void X1(boolean bl) {
        if (this.f0 != bl) {
            boolean bl2 = this.g3();
            this.f0 = bl;
            bl = this.g3();
            if (bl2 != bl) {
                if (bl) {
                    this.C0(this.g0);
                } else {
                    this.j3(this.g0);
                }
                this.invalidateSelf();
                this.N1();
            }
        }
    }

    public void X2(s2.d d3) {
        this.z0.k(d3, this.s0);
    }

    public ColorStateList Y0() {
        return this.h0;
    }

    public void Y1(ColorStateList colorStateList) {
        if (this.M != colorStateList) {
            this.M = colorStateList;
            this.onStateChange(this.getState());
        }
    }

    public void Y2(int n3) {
        this.X2(new s2.d(this.s0, n3));
    }

    public ColorStateList Z0() {
        return this.M;
    }

    public void Z1(int n3) {
        this.Y1(d.a.a(this.s0, n3));
    }

    public void Z2(float f3) {
        if (this.o0 != f3) {
            this.o0 = f3;
            this.invalidateSelf();
            this.N1();
        }
    }

    @Override
    public void a() {
        this.N1();
        this.invalidateSelf();
    }

    public float a1() {
        if (this.U0) {
            return this.P();
        }
        return this.O;
    }

    public void a2(float f3) {
        if (this.O != f3) {
            this.O = f3;
            this.setShapeAppearanceModel(this.K().x(f3));
        }
    }

    public void a3(int n3) {
        this.Z2(this.s0.getResources().getDimension(n3));
    }

    public float b1() {
        return this.r0;
    }

    public void b2(int n3) {
        this.a2(this.s0.getResources().getDimension(n3));
    }

    public void b3(float f3) {
        s2.d d3 = this.A1();
        if (d3 != null) {
            d3.o(f3);
            this.z0.g().setTextSize(f3);
            this.a();
        }
    }

    public Drawable c1() {
        Drawable drawable = this.U;
        if (drawable != null) {
            return h0.a.q(drawable);
        }
        return null;
    }

    public void c2(float f3) {
        if (this.r0 != f3) {
            this.r0 = f3;
            this.invalidateSelf();
            this.N1();
        }
    }

    public void c3(float f3) {
        if (this.n0 != f3) {
            this.n0 = f3;
            this.invalidateSelf();
            this.N1();
        }
    }

    public float d1() {
        return this.W;
    }

    public void d2(int n3) {
        this.c2(this.s0.getResources().getDimension(n3));
    }

    public void d3(int n3) {
        this.c3(this.s0.getResources().getDimension(n3));
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = this.getBounds();
        if (!rect.isEmpty() && this.getAlpha() != 0) {
            int n3 = this.I0;
            n3 = n3 < 255 ? d2.a.a(canvas, rect.left, rect.top, rect.right, rect.bottom, n3) : 0;
            this.S0(canvas, rect);
            this.P0(canvas, rect);
            if (this.U0) {
                super.draw(canvas);
            }
            this.R0(canvas, rect);
            this.U0(canvas, rect);
            this.Q0(canvas, rect);
            this.O0(canvas, rect);
            if (this.S0) {
                this.W0(canvas, rect);
            }
            this.T0(canvas, rect);
            this.V0(canvas, rect);
            if (this.I0 < 255) {
                canvas.restoreToCount(n3);
            }
        }
    }

    public ColorStateList e1() {
        return this.V;
    }

    public void e2(Drawable object) {
        Drawable drawable = this.c1();
        if (drawable != object) {
            float f3 = this.E0();
            object = object != null ? h0.a.r(object).mutate() : null;
            this.U = object;
            float f4 = this.E0();
            this.j3(drawable);
            if (this.h3()) {
                this.C0(this.U);
            }
            this.invalidateSelf();
            if (f3 != f4) {
                this.N1();
            }
        }
    }

    public void e3(boolean bl) {
        if (this.O0 != bl) {
            this.O0 = bl;
            this.k3();
            this.onStateChange(this.getState());
        }
    }

    public float f1() {
        return this.N;
    }

    public void f2(int n3) {
        this.e2(d.a.b(this.s0, n3));
    }

    public boolean f3() {
        return this.S0;
    }

    public float g1() {
        return this.k0;
    }

    public void g2(float f3) {
        if (this.W != f3) {
            float f4 = this.E0();
            this.W = f3;
            f3 = this.E0();
            this.invalidateSelf();
            if (f4 != f3) {
                this.N1();
            }
        }
    }

    public final boolean g3() {
        return this.f0 && this.g0 != null && this.G0;
    }

    @Override
    public int getAlpha() {
        return this.I0;
    }

    public ColorFilter getColorFilter() {
        return this.J0;
    }

    public int getIntrinsicHeight() {
        return (int)this.N;
    }

    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.k0 + this.E0() + this.n0 + this.z0.h(this.z1().toString()) + this.o0 + this.I0() + this.r0), this.T0);
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    @Override
    public void getOutline(Outline outline) {
        if (this.U0) {
            super.getOutline(outline);
            return;
        }
        Rect rect = this.getBounds();
        if (!rect.isEmpty()) {
            outline.setRoundRect(rect, this.O);
        } else {
            outline.setRoundRect(0, 0, this.getIntrinsicWidth(), this.getIntrinsicHeight(), this.O);
        }
        outline.setAlpha((float)this.getAlpha() / 255.0f);
    }

    public ColorStateList h1() {
        return this.P;
    }

    public void h2(int n3) {
        this.g2(this.s0.getResources().getDimension(n3));
    }

    public final boolean h3() {
        return this.T && this.U != null;
    }

    public float i1() {
        return this.Q;
    }

    public void i2(ColorStateList colorStateList) {
        this.X = true;
        if (this.V != colorStateList) {
            this.V = colorStateList;
            if (this.h3()) {
                this.U.setTintList(colorStateList);
            }
            this.onStateChange(this.getState());
        }
    }

    public final boolean i3() {
        return this.Y && this.Z != null;
    }

    public void invalidateDrawable(Drawable drawable) {
        drawable = this.getCallback();
        if (drawable != null) {
            drawable.invalidateDrawable((Drawable)this);
        }
    }

    @Override
    public boolean isStateful() {
        return a.J1(this.L) || a.J1(this.M) || a.J1(this.P) || this.O0 && a.J1(this.P0) || a.L1(this.z0.e()) || this.M0() || a.K1(this.U) || a.K1(this.g0) || a.J1(this.L0);
        {
        }
    }

    public Drawable j1() {
        Drawable drawable = this.Z;
        if (drawable != null) {
            return h0.a.q(drawable);
        }
        return null;
    }

    public void j2(int n3) {
        this.i2(d.a.a(this.s0, n3));
    }

    public final void j3(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    public CharSequence k1() {
        return this.d0;
    }

    public void k2(int n3) {
        this.l2(this.s0.getResources().getBoolean(n3));
    }

    public final void k3() {
        ColorStateList colorStateList = this.O0 ? t2.a.d(this.R) : null;
        this.P0 = colorStateList;
    }

    public float l1() {
        return this.q0;
    }

    public void l2(boolean bl) {
        if (this.T != bl) {
            boolean bl2 = this.h3();
            this.T = bl;
            bl = this.h3();
            if (bl2 != bl) {
                if (bl) {
                    this.C0(this.U);
                } else {
                    this.j3(this.U);
                }
                this.invalidateSelf();
                this.N1();
            }
        }
    }

    public final void l3() {
        this.a0 = new RippleDrawable(t2.a.d(this.x1()), this.Z, (Drawable)W0);
    }

    public float m1() {
        return this.c0;
    }

    public void m2(float f3) {
        if (this.N != f3) {
            this.N = f3;
            this.invalidateSelf();
            this.N1();
        }
    }

    public float n1() {
        return this.p0;
    }

    public void n2(int n3) {
        this.m2(this.s0.getResources().getDimension(n3));
    }

    public int[] o1() {
        return this.N0;
    }

    public void o2(float f3) {
        if (this.k0 != f3) {
            this.k0 = f3;
            this.invalidateSelf();
            this.N1();
        }
    }

    public boolean onLayoutDirectionChanged(int n3) {
        boolean bl;
        boolean bl2 = bl = super.onLayoutDirectionChanged(n3);
        if (this.h3()) {
            bl2 = bl | h0.a.m(this.U, n3);
        }
        bl = bl2;
        if (this.g3()) {
            bl = bl2 | h0.a.m(this.g0, n3);
        }
        bl2 = bl;
        if (this.i3()) {
            bl2 = bl | h0.a.m(this.Z, n3);
        }
        if (bl2) {
            this.invalidateSelf();
        }
        return true;
    }

    public boolean onLevelChange(int n3) {
        boolean bl;
        boolean bl2 = bl = super.onLevelChange(n3);
        if (this.h3()) {
            bl2 = bl | this.U.setLevel(n3);
        }
        bl = bl2;
        if (this.g3()) {
            bl = bl2 | this.g0.setLevel(n3);
        }
        bl2 = bl;
        if (this.i3()) {
            bl2 = bl | this.Z.setLevel(n3);
        }
        if (bl2) {
            this.invalidateSelf();
        }
        return bl2;
    }

    @Override
    public boolean onStateChange(int[] nArray) {
        if (this.U0) {
            super.onStateChange(nArray);
        }
        return this.O1(nArray, this.o1());
    }

    public ColorStateList p1() {
        return this.b0;
    }

    public void p2(int n3) {
        this.o2(this.s0.getResources().getDimension(n3));
    }

    public void q1(RectF rectF) {
        this.H0(this.getBounds(), rectF);
    }

    public void q2(ColorStateList colorStateList) {
        if (this.P != colorStateList) {
            this.P = colorStateList;
            if (this.U0) {
                this.u0(colorStateList);
            }
            this.onStateChange(this.getState());
        }
    }

    public final float r1() {
        float f3;
        Drawable drawable = this.G0 ? this.g0 : this.U;
        float f4 = f3 = this.W;
        if (f3 <= 0.0f) {
            f4 = f3;
            if (drawable != null) {
                f4 = f3 = (float)Math.ceil(com.google.android.material.internal.c0.g(this.s0, 24));
                if ((float)drawable.getIntrinsicHeight() <= f3) {
                    return drawable.getIntrinsicHeight();
                }
            }
        }
        return f4;
    }

    public void r2(int n3) {
        this.q2(d.a.a(this.s0, n3));
    }

    public final float s1() {
        Drawable drawable = this.G0 ? this.g0 : this.U;
        float f3 = this.W;
        if (f3 <= 0.0f && drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return f3;
    }

    public void s2(float f3) {
        if (this.Q != f3) {
            this.Q = f3;
            this.t0.setStrokeWidth(f3);
            if (this.U0) {
                super.v0(f3);
            }
            this.invalidateSelf();
        }
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l3) {
        drawable = this.getCallback();
        if (drawable != null) {
            drawable.scheduleDrawable((Drawable)this, runnable, l3);
        }
    }

    @Override
    public void setAlpha(int n3) {
        if (this.I0 != n3) {
            this.I0 = n3;
            this.invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.J0 != colorFilter) {
            this.J0 = colorFilter;
            this.invalidateSelf();
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        if (this.L0 != colorStateList) {
            this.L0 = colorStateList;
            this.onStateChange(this.getState());
        }
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.M0 != mode) {
            this.M0 = mode;
            this.K0 = j2.d.o(this, this.L0, mode);
            this.invalidateSelf();
        }
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3;
        boolean bl4 = bl3 = super.setVisible(bl, bl2);
        if (this.h3()) {
            bl4 = bl3 | this.U.setVisible(bl, bl2);
        }
        bl3 = bl4;
        if (this.g3()) {
            bl3 = bl4 | this.g0.setVisible(bl, bl2);
        }
        bl4 = bl3;
        if (this.i3()) {
            bl4 = bl3 | this.Z.setVisible(bl, bl2);
        }
        if (bl4) {
            this.invalidateSelf();
        }
        return bl4;
    }

    public TextUtils.TruncateAt t1() {
        return this.R0;
    }

    public void t2(int n3) {
        this.s2(this.s0.getResources().getDimension(n3));
    }

    public h u1() {
        return this.j0;
    }

    public final void u2(ColorStateList colorStateList) {
        if (this.L != colorStateList) {
            this.L = colorStateList;
            this.onStateChange(this.getState());
        }
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        drawable = this.getCallback();
        if (drawable != null) {
            drawable.unscheduleDrawable((Drawable)this, runnable);
        }
    }

    public float v1() {
        return this.m0;
    }

    public void v2(Drawable object) {
        Drawable drawable = this.j1();
        if (drawable != object) {
            float f3 = this.I0();
            object = object != null ? h0.a.r(object).mutate() : null;
            this.Z = object;
            this.l3();
            float f4 = this.I0();
            this.j3(drawable);
            if (this.i3()) {
                this.C0(this.Z);
            }
            this.invalidateSelf();
            if (f3 != f4) {
                this.N1();
            }
        }
    }

    public float w1() {
        return this.l0;
    }

    public void w2(CharSequence charSequence) {
        if (this.d0 != charSequence) {
            this.d0 = m0.a.c().h(charSequence);
            this.invalidateSelf();
        }
    }

    public ColorStateList x1() {
        return this.R;
    }

    public void x2(float f3) {
        if (this.q0 != f3) {
            this.q0 = f3;
            this.invalidateSelf();
            if (this.i3()) {
                this.N1();
            }
        }
    }

    public h y1() {
        return this.i0;
    }

    public void y2(int n3) {
        this.x2(this.s0.getResources().getDimension(n3));
    }

    public CharSequence z1() {
        return this.S;
    }

    public void z2(int n3) {
        this.v2(d.a.b(this.s0, n3));
    }

    public static interface a {
        public void a();
    }
}

