/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Region$Op
 *  android.graphics.Xfermode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewOverlay
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.ViewTreeObserver$OnScrollChangedListener
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.SeekBar
 */
package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.appcompat.app.s;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import com.google.android.material.slider.e;
import com.google.android.material.slider.f;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import o0.x0;
import p0.s;
import v2.i;
import v2.o;
import z1.k;
import z1.l;
import z1.m;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L, T>
extends View {
    public static final String X0 = "BaseSlider";
    public static final int Y0 = z1.l.Widget_MaterialComponents_Slider;
    public static final int Z0 = z1.c.motionDurationMedium4;
    public static final int a1 = z1.c.motionDurationShort3;
    public static final int b1 = z1.c.motionEasingEmphasizedInterpolator;
    public static final int c1 = z1.c.motionEasingEmphasizedAccelerateInterpolator;
    public int A;
    public ColorStateList A0;
    public int B;
    public ColorStateList B0;
    public int C;
    public ColorStateList C0;
    public int D;
    public ColorStateList D0;
    public int E;
    public final Path E0;
    public int F;
    public final RectF F0;
    public int G;
    public final RectF G0;
    public int H;
    public final RectF H0;
    public int I;
    public final RectF I0;
    public int J;
    public final Rect J0;
    public int K;
    public final RectF K0;
    public int L = -1;
    public final Rect L0;
    public int M = -1;
    public final Matrix M0;
    public int N;
    public final i N0;
    public int O;
    public Drawable O0;
    public int P;
    public List P0;
    public boolean Q = false;
    public float Q0;
    public Drawable R;
    public int R0;
    public boolean S = false;
    public final int S0;
    public Drawable T;
    public final ViewTreeObserver.OnScrollChangedListener T0;
    public boolean U = false;
    public final ViewTreeObserver.OnGlobalLayoutListener U0;
    public ColorStateList V;
    public final Runnable V0;
    public Drawable W;
    public boolean W0;
    public boolean a0 = false;
    public Drawable b0;
    public final Paint c;
    public boolean c0 = false;
    public final Paint d;
    public ColorStateList d0;
    public final Paint e;
    public int e0;
    public final Paint f;
    public int f0;
    public final Paint g;
    public int g0;
    public final Paint h;
    public float h0;
    public final Paint i;
    public float i0;
    public final c j;
    public MotionEvent j0;
    public final AccessibilityManager k;
    public boolean k0 = false;
    public b l;
    public float l0;
    public int m;
    public float m0;
    public final List n = new ArrayList();
    public ArrayList n0;
    public final List o = new ArrayList();
    public int o0 = -1;
    public final List p = new ArrayList();
    public int p0 = -1;
    public boolean q = false;
    public float q0 = 0.0f;
    public ValueAnimator r;
    public float[] r0;
    public ValueAnimator s;
    public int s0;
    public final int t;
    public int t0;
    public int u;
    public int u0;
    public int v;
    public int v0;
    public int w;
    public boolean w0;
    public int x;
    public boolean x0 = false;
    public int y;
    public boolean y0;
    public int z;
    public ColorStateList z0;

    public BaseSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.sliderStyle);
    }

    public BaseSlider(Context object, AttributeSet attributeSet, int n3) {
        super(y2.a.d(object, attributeSet, n3, Y0), attributeSet, n3);
        Paint paint;
        Paint paint2;
        i i3;
        this.n0 = new ArrayList();
        this.E0 = new Path();
        this.F0 = new RectF();
        this.G0 = new RectF();
        this.H0 = new RectF();
        this.I0 = new RectF();
        this.J0 = new Rect();
        this.K0 = new RectF();
        this.L0 = new Rect();
        this.M0 = new Matrix();
        this.N0 = i3 = new i();
        this.P0 = Collections.EMPTY_LIST;
        this.R0 = 0;
        this.T0 = new com.google.android.material.slider.c(this);
        this.U0 = new com.google.android.material.slider.d(this);
        this.V0 = new e(this);
        Context context = this.getContext();
        this.W0 = this.isShown();
        this.c = new Paint();
        this.d = new Paint();
        this.e = paint2 = new Paint(1);
        object = Paint.Style.FILL;
        paint2.setStyle((Paint.Style)object);
        paint2.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.f = paint2 = new Paint(1);
        paint2.setStyle((Paint.Style)object);
        this.g = paint = new Paint();
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        paint2 = Paint.Cap.ROUND;
        paint.setStrokeCap((Paint.Cap)paint2);
        this.h = paint = new Paint();
        paint.setStyle(style);
        paint.setStrokeCap((Paint.Cap)paint2);
        style = new Paint();
        this.i = style;
        style.setStyle((Paint.Style)object);
        style.setStrokeCap((Paint.Cap)paint2);
        this.t0(context.getResources());
        this.H0(context, attributeSet, n3);
        this.setFocusable(true);
        this.setClickable(true);
        i3.q0(2);
        this.t = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        object = new c(this);
        this.j = object;
        o0.x0.h0(this, (o0.a)object);
        object = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.k = object;
        if (Build.VERSION.SDK_INT >= 29) {
            this.S0 = com.google.android.material.slider.a.a((AccessibilityManager)object, 10000, 6);
            return;
        }
        this.S0 = 120000;
    }

    public static float S(ValueAnimator valueAnimator, float f3) {
        float f4 = f3;
        if (valueAnimator != null) {
            f4 = f3;
            if (valueAnimator.isRunning()) {
                f4 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                valueAnimator.cancel();
            }
        }
        return f4;
    }

    public static /* synthetic */ void a(BaseSlider baseSlider) {
        baseSlider.setActiveThumbIndex(-1);
        baseSlider.invalidate();
    }

    public static /* synthetic */ void b(BaseSlider baseSlider, ValueAnimator object) {
        ((Object)((Object)baseSlider)).getClass();
        float f3 = ((Float)object.getAnimatedValue()).floatValue();
        object = baseSlider.n.iterator();
        while (object.hasNext()) {
            ((z2.a)object.next()).O0(f3);
        }
        baseSlider.postInvalidateOnAnimation();
    }

    public static /* synthetic */ void c(BaseSlider baseSlider) {
        baseSlider.V0();
    }

    public static /* synthetic */ void d(BaseSlider baseSlider) {
        baseSlider.V0();
    }

    public static boolean j0(MotionEvent motionEvent) {
        return motionEvent.getToolType(0) == 3;
    }

    public final void A(z2.a a4) {
        ViewGroup viewGroup = com.google.android.material.internal.c0.i(this);
        if (viewGroup == null) {
            return;
        }
        viewGroup.getOverlay().remove((Drawable)a4);
        a4.I0((View)viewGroup);
    }

    public final boolean A0(int n3) {
        int n4;
        block3: {
            block2: {
                if (this.p0()) break block2;
                n4 = n3;
                if (!this.s0()) break block3;
            }
            n4 = n3 == Integer.MIN_VALUE ? Integer.MAX_VALUE : -n3;
        }
        return this.z0(n4);
    }

    public final float B(float f3) {
        if (f3 == 0.0f) {
            return 0.0f;
        }
        float f4 = (f3 - (float)this.G) / (float)this.v0;
        f3 = this.l0;
        return f4 * (f3 - this.m0) + f3;
    }

    public final float B0(float f3) {
        float f4 = this.l0;
        f3 = (f3 - f4) / (this.m0 - f4);
        if (!this.p0() && !this.s0()) {
            return f3;
        }
        return 1.0f - f3;
    }

    public final void C(int n3) {
        AccessibilityManager accessibilityManager = this.o.iterator();
        if (!accessibilityManager.hasNext()) {
            accessibilityManager = this.k;
            if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                this.I0(n3);
            }
            return;
        }
        androidx.appcompat.app.s.a(accessibilityManager.next());
        ((Float)this.n0.get(n3)).floatValue();
        throw null;
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    public final Boolean C0(int var1_1, KeyEvent var2_2) {
        block8: {
            block9: {
                if (var1_1 == 61) break block8;
                if (var1_1 == 66) break block9;
                if (var1_1 == 81) ** GOTO lbl20
                if (var1_1 == 69) {
                    this.z0(-1);
                    return Boolean.TRUE;
                }
                if (var1_1 == 70) ** GOTO lbl20
                switch (var1_1) {
                    default: {
                        return null;
                    }
                    case 22: {
                        this.A0(1);
                        return Boolean.TRUE;
                    }
                    case 21: {
                        this.A0(-1);
                        return Boolean.TRUE;
                    }
lbl20:
                    // 2 sources

                    this.z0(1);
                    return Boolean.TRUE;
                    case 23: 
                }
            }
            this.o0 = this.p0;
            this.postInvalidate();
            return Boolean.TRUE;
        }
        if (var2_2.hasNoModifiers()) {
            return this.z0(1);
        }
        if (var2_2.isShiftPressed()) {
            return this.z0(-1);
        }
        return Boolean.FALSE;
    }

    public final void D() {
        Iterator iterator = this.o.iterator();
        while (iterator.hasNext()) {
            androidx.appcompat.app.s.a(iterator.next());
            Iterator iterator2 = this.n0.iterator();
            if (!iterator2.hasNext()) continue;
            ((Float)iterator2.next()).floatValue();
            throw null;
        }
    }

    public final void D0() {
        Iterator iterator = this.p.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        androidx.appcompat.app.s.a(iterator.next());
        throw null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void E(Canvas canvas, int n3, int n4) {
        d d3;
        float[] fArray = this.R();
        int n5 = this.G;
        float f3 = n5;
        float f4 = fArray[1];
        float f5 = n3;
        f3 = (float)n5 + fArray[0] * f5;
        if (f3 >= (f4 = f3 + f4 * f5)) {
            this.F0.setEmpty();
            return;
        }
        d d4 = d3 = com.google.android.material.slider.BaseSlider$d.f;
        if (this.n0.size() == 1) {
            d4 = d3;
            if (!this.g0()) {
                d4 = !this.p0() && !this.s0() ? com.google.android.material.slider.BaseSlider$d.d : com.google.android.material.slider.BaseSlider$d.e;
            }
        }
        n5 = 0;
        while (true) {
            int n6;
            float f6;
            block16: {
                block18: {
                    block13: {
                        block14: {
                            block19: {
                                block17: {
                                    block15: {
                                        block11: {
                                            block12: {
                                                if (n5 >= this.n0.size()) {
                                                    return;
                                                }
                                                f5 = f3;
                                                if (this.n0.size() <= 1) break block11;
                                                if (n5 > 0) {
                                                    f3 = this.m1(((Float)this.n0.get(n5 - 1)).floatValue());
                                                }
                                                f6 = this.m1(((Float)this.n0.get(n5)).floatValue());
                                                if (this.p0()) break block12;
                                                f5 = f3;
                                                f4 = f6;
                                                if (!this.s0()) break block11;
                                            }
                                            f5 = f6;
                                            f4 = f3;
                                        }
                                        n6 = this.getTrackCornerSize();
                                        n3 = d4.ordinal();
                                        if (n3 == 1) break block13;
                                        if (n3 == 2) break block14;
                                        if (n3 == 3) break block15;
                                        f3 = f5;
                                        f6 = f4;
                                        break block16;
                                    }
                                    if (this.g0()) break block17;
                                    n3 = this.K;
                                    f5 += (float)n3;
                                    break block18;
                                }
                                if (fArray[1] != 0.5f) break block19;
                                f3 = f5 + (float)this.K;
                                f6 = f4;
                                break block16;
                            }
                            f3 = f5;
                            f6 = f4;
                            if (fArray[0] != 0.5f) break block16;
                            n3 = this.K;
                            break block18;
                        }
                        f3 = f5 + (float)this.K;
                        f6 = f4 + (float)n6;
                        break block16;
                    }
                    f5 -= (float)n6;
                    n3 = this.K;
                }
                f6 = f4 - (float)n3;
                f3 = f5;
            }
            if (f3 >= f6) {
                this.F0.setEmpty();
            } else {
                d3 = this.F0;
                f5 = n4;
                n3 = this.F;
                d3.set(f3, f5 - (float)n3 / 2.0f, f6, f5 + (float)n3 / 2.0f);
                this.a1(canvas, this.d, this.F0, n6, d4);
            }
            ++n5;
            f4 = f6;
        }
    }

    public final void E0() {
        Iterator iterator = this.p.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        androidx.appcompat.app.s.a(iterator.next());
        throw null;
    }

    public final void F(float f3, float f4, float f5, float f6, Canvas canvas, RectF rectF, d d3) {
        if (f4 - f3 > (float)(this.getTrackCornerSize() - this.K)) {
            rectF.set(f3, f5, f4, f6);
        } else {
            rectF.setEmpty();
        }
        this.a1(canvas, this.c, rectF, this.getTrackCornerSize(), d3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean F0() {
        if (this.o0 != -1) {
            return true;
        }
        float f3 = this.a0();
        float f4 = this.m1(f3);
        this.o0 = 0;
        float f5 = Math.abs(((Float)this.n0.get(0)).floatValue() - f3);
        for (int i3 = 1; i3 < this.n0.size(); ++i3) {
            float f6;
            block8: {
                float f7;
                block7: {
                    boolean bl;
                    float f8;
                    block6: {
                        f7 = Math.abs(((Float)this.n0.get(i3)).floatValue() - f3);
                        f8 = this.m1(((Float)this.n0.get(i3)).floatValue());
                        if (Float.compare(f7, f5) > 0) break;
                        bl = !this.p0() && !this.s0() ? f8 - f4 < 0.0f : f8 - f4 > 0.0f;
                        if (Float.compare(f7, f5) >= 0) break block6;
                        this.o0 = i3;
                        break block7;
                    }
                    f6 = f5;
                    if (Float.compare(f7, f5) != 0) break block8;
                    if (Math.abs(f8 - f4) < (float)this.t) {
                        this.o0 = -1;
                        return false;
                    }
                    f6 = f5;
                    if (!bl) break block8;
                    this.o0 = i3;
                }
                f6 = f7;
            }
            f5 = f6;
        }
        return this.o0 != -1;
    }

    public final void G(Canvas canvas, int n3, int n4) {
        float[] fArray = this.R();
        float f3 = n4;
        n4 = this.F;
        float f4 = f3 - (float)n4 / 2.0f;
        float f5 = f3 + (float)n4 / 2.0f;
        f3 = this.G - this.getTrackCornerSize();
        float f6 = this.G;
        float f7 = fArray[0];
        float f8 = n3;
        this.F(f3, f6 + f7 * f8 - (float)this.K, f4, f5, canvas, this.G0, com.google.android.material.slider.BaseSlider$d.d);
        n4 = this.G;
        this.F((float)n4 + fArray[1] * f8 + (float)this.K, n4 + n3 + this.getTrackCornerSize(), f4, f5, canvas, this.H0, com.google.android.material.slider.BaseSlider$d.e);
    }

    public final void G0(z2.a a4, float f3) {
        this.r(a4, f3);
        if (this.s0()) {
            RectF rectF = new RectF(this.J0);
            this.M0.mapRect(rectF);
            rectF.round(this.J0);
        }
        com.google.android.material.internal.d.c(com.google.android.material.internal.c0.i(this), this, this.J0);
        a4.setBounds(this.J0);
    }

    public final void H(Canvas canvas, float f3, float f4) {
        ArrayList arrayList = this.n0;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Object e3 = arrayList.get(n4);
            int n5 = n4 + 1;
            float f5 = this.m1(((Float)e3).floatValue());
            float f6 = (float)this.K + (float)this.H / 2.0f;
            n4 = n5;
            if (!(f3 >= f5 - f6)) continue;
            n4 = n5;
            if (!(f3 <= f5 + f6)) continue;
            return;
        }
        if (this.s0()) {
            canvas.drawPoint(f4, f3, this.i);
            return;
        }
        canvas.drawPoint(f3, f4, this.i);
    }

    public final void H0(Context context, AttributeSet attributeSet, int n3) {
        TypedArray typedArray = com.google.android.material.internal.z.i(context, attributeSet, z1.m.Slider, n3, Y0, new int[0]);
        this.setOrientation(typedArray.getInt(z1.m.Slider_android_orientation, 0));
        this.m = typedArray.getResourceId(z1.m.Slider_labelStyle, z1.l.Widget_MaterialComponents_Tooltip);
        this.l0 = typedArray.getFloat(z1.m.Slider_android_valueFrom, 0.0f);
        this.m0 = typedArray.getFloat(z1.m.Slider_android_valueTo, 1.0f);
        this.setValues(Float.valueOf(this.l0));
        this.setCentered(typedArray.getBoolean(z1.m.Slider_centered, false));
        this.q0 = typedArray.getFloat(z1.m.Slider_android_stepSize, 0.0f);
        float f3 = s2.b.e(context);
        this.A = (int)Math.ceil(typedArray.getDimension(z1.m.Slider_minTouchTargetSize, f3));
        int n4 = z1.m.Slider_trackColor;
        boolean bl = typedArray.hasValue(n4);
        n3 = bl ? n4 : z1.m.Slider_trackColorInactive;
        if (!bl) {
            n4 = z1.m.Slider_trackColorActive;
        }
        attributeSet = s2.c.a(context, typedArray, n3);
        if (attributeSet == null) {
            attributeSet = d.a.a(context, z1.d.material_slider_inactive_track_color);
        }
        this.setTrackInactiveTintList((ColorStateList)attributeSet);
        attributeSet = s2.c.a(context, typedArray, n4);
        if (attributeSet == null) {
            attributeSet = d.a.a(context, z1.d.material_slider_active_track_color);
        }
        this.setTrackActiveTintList((ColorStateList)attributeSet);
        attributeSet = s2.c.a(context, typedArray, z1.m.Slider_thumbColor);
        this.N0.i0((ColorStateList)attributeSet);
        n3 = z1.m.Slider_thumbStrokeColor;
        if (typedArray.hasValue(n3)) {
            this.setThumbStrokeColor(s2.c.a(context, typedArray, n3));
        }
        this.setThumbStrokeWidth(typedArray.getDimension(z1.m.Slider_thumbStrokeWidth, 0.0f));
        attributeSet = s2.c.a(context, typedArray, z1.m.Slider_haloColor);
        if (attributeSet == null) {
            attributeSet = d.a.a(context, z1.d.material_slider_halo_color);
        }
        this.setHaloTintList((ColorStateList)attributeSet);
        n3 = z1.m.Slider_tickVisibilityMode;
        n3 = typedArray.hasValue(n3) ? typedArray.getInt(n3, -1) : this.x(typedArray.getBoolean(z1.m.Slider_tickVisible, true));
        this.s0 = n3;
        n3 = z1.m.Slider_tickColor;
        bl = typedArray.hasValue(n3);
        n4 = bl ? n3 : z1.m.Slider_tickColorInactive;
        if (!bl) {
            n3 = z1.m.Slider_tickColorActive;
        }
        attributeSet = s2.c.a(context, typedArray, n4);
        if (attributeSet == null) {
            attributeSet = d.a.a(context, z1.d.material_slider_inactive_tick_marks_color);
        }
        this.setTickInactiveTintList((ColorStateList)attributeSet);
        attributeSet = s2.c.a(context, typedArray, n3);
        if (attributeSet == null) {
            attributeSet = d.a.a(context, z1.d.material_slider_active_tick_marks_color);
        }
        this.setTickActiveTintList((ColorStateList)attributeSet);
        this.setThumbTrackGapSize(typedArray.getDimensionPixelSize(z1.m.Slider_thumbTrackGapSize, 0));
        this.setTrackStopIndicatorSize(typedArray.getDimensionPixelSize(z1.m.Slider_trackStopIndicatorSize, 0));
        this.setTrackCornerSize(typedArray.getDimensionPixelSize(z1.m.Slider_trackCornerSize, -1));
        this.setTrackInsideCornerSize(typedArray.getDimensionPixelSize(z1.m.Slider_trackInsideCornerSize, 0));
        this.setTrackIconActiveStart(s2.c.e(context, typedArray, z1.m.Slider_trackIconActiveStart));
        this.setTrackIconActiveEnd(s2.c.e(context, typedArray, z1.m.Slider_trackIconActiveEnd));
        this.setTrackIconActiveColor(s2.c.a(context, typedArray, z1.m.Slider_trackIconActiveColor));
        this.setTrackIconInactiveStart(s2.c.e(context, typedArray, z1.m.Slider_trackIconInactiveStart));
        this.setTrackIconInactiveEnd(s2.c.e(context, typedArray, z1.m.Slider_trackIconInactiveEnd));
        this.setTrackIconInactiveColor(s2.c.a(context, typedArray, z1.m.Slider_trackIconInactiveColor));
        this.setTrackIconSize(typedArray.getDimensionPixelSize(z1.m.Slider_trackIconSize, 0));
        n4 = typedArray.getDimensionPixelSize(z1.m.Slider_thumbRadius, 0);
        n3 = z1.m.Slider_thumbWidth;
        n3 = typedArray.getDimensionPixelSize(n3, n4 *= 2);
        n4 = typedArray.getDimensionPixelSize(z1.m.Slider_thumbHeight, n4);
        this.setThumbWidth(n3);
        this.setThumbHeight(n4);
        this.setHaloRadius(typedArray.getDimensionPixelSize(z1.m.Slider_haloRadius, 0));
        this.setThumbElevation(typedArray.getDimension(z1.m.Slider_thumbElevation, 0.0f));
        this.setTrackHeight(typedArray.getDimensionPixelSize(z1.m.Slider_trackHeight, 0));
        this.setTickActiveRadius(typedArray.getDimensionPixelSize(z1.m.Slider_tickRadiusActive, this.N / 2));
        this.setTickInactiveRadius(typedArray.getDimensionPixelSize(z1.m.Slider_tickRadiusInactive, this.N / 2));
        this.setLabelBehavior(typedArray.getInt(z1.m.Slider_labelBehavior, 0));
        if (!typedArray.getBoolean(z1.m.Slider_android_enabled, true)) {
            this.setEnabled(false);
        }
        typedArray.recycle();
    }

    public final void I(Canvas canvas, int n3, int n4, float f3, Drawable drawable) {
        canvas.save();
        if (this.s0()) {
            canvas.concat(this.M0);
        }
        canvas.translate((float)(this.G + (int)(this.B0(f3) * (float)n3)) - (float)drawable.getBounds().width() / 2.0f, (float)n4 - (float)drawable.getBounds().height() / 2.0f);
        drawable.draw(canvas);
        canvas.restore();
    }

    public final void I0(int n3) {
        b b3 = this.l;
        if (b3 == null) {
            this.l = new b(this, null);
        } else {
            this.removeCallbacks(b3);
        }
        this.l.a(n3);
        this.postDelayed(this.l, 200L);
    }

    public final void J(Canvas canvas, int n3, int n4) {
        for (int i3 = 0; i3 < this.n0.size(); ++i3) {
            float f3 = ((Float)this.n0.get(i3)).floatValue();
            Drawable drawable = this.O0;
            if (drawable != null) {
                this.I(canvas, n3, n4, f3, drawable);
                continue;
            }
            if (i3 < this.P0.size()) {
                this.I(canvas, n3, n4, f3, (Drawable)this.P0.get(i3));
                continue;
            }
            if (!this.isEnabled()) {
                canvas.drawCircle((float)this.G + this.B0(f3) * (float)n3, (float)n4, (float)this.getThumbRadius(), this.e);
            }
            this.I(canvas, n3, n4, f3, this.N0);
        }
    }

    public void J0() {
        this.removeCallbacks(this.V0);
        this.postDelayed(this.V0, this.S0);
    }

    public final void K(int n3, int n4, Canvas canvas, Paint paint) {
        while (n3 < n4) {
            float f3 = this.s0() ? this.r0[n3 + 1] : this.r0[n3];
            if (!(this.m0(f3) || this.g0() && this.l0(f3))) {
                float[] fArray = this.r0;
                canvas.drawPoint(fArray[n3], fArray[n3 + 1], paint);
            }
            n3 += 2;
        }
    }

    public final void K0(z2.a a4, float f3) {
        a4.P0(this.Q(f3));
        this.G0(a4, f3);
        ViewOverlay viewOverlay = this.V();
        if (viewOverlay == null) {
            return;
        }
        viewOverlay.add((Drawable)a4);
    }

    public final void L(Canvas canvas, RectF rectF, Drawable drawable) {
        if (this.s0()) {
            this.M0.mapRect(rectF);
        }
        rectF.round(this.L0);
        drawable.setBounds(this.L0);
        drawable.draw(canvas);
    }

    public final void L0(ArrayList arrayList) {
        if (!arrayList.isEmpty()) {
            Collections.sort(arrayList);
            if (this.n0.size() == arrayList.size() && this.n0.equals(arrayList)) {
                return;
            }
            this.n0 = arrayList;
            this.y0 = true;
            this.p0 = 0;
            this.T0();
            this.z();
            this.D();
            this.postInvalidate();
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    public final void M(Canvas canvas, RectF rectF, RectF rectF2) {
        if (!this.d0()) {
            return;
        }
        if (this.n0.size() > 1) {
            Log.w((String)X0, (String)"Track icons can only be used when only 1 thumb is present.");
        }
        this.o(canvas, rectF, this.R, true);
        this.o(canvas, rectF2, this.W, true);
        this.o(canvas, rectF, this.T, false);
        this.o(canvas, rectF2, this.b0, false);
    }

    public final boolean M0() {
        return this.E == 3;
    }

    public final void N() {
        ValueAnimator valueAnimator;
        if (!this.q) {
            this.q = true;
            this.r = valueAnimator = this.y(true);
            this.s = null;
            valueAnimator.start();
        }
        valueAnimator = this.n.iterator();
        for (int i3 = 0; i3 < this.n0.size() && valueAnimator.hasNext(); ++i3) {
            if (i3 == this.p0) continue;
            this.K0((z2.a)valueAnimator.next(), ((Float)this.n0.get(i3)).floatValue());
        }
        if (valueAnimator.hasNext()) {
            this.K0((z2.a)valueAnimator.next(), ((Float)this.n0.get(this.p0)).floatValue());
            return;
        }
        throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", this.n.size(), this.n0.size()));
    }

    public final boolean N0() {
        return this.w0 || !(this.getBackground() instanceof RippleDrawable);
        {
        }
    }

    public final void O() {
        if (this.q) {
            ValueAnimator valueAnimator;
            this.q = false;
            this.s = valueAnimator = this.y(false);
            this.r = null;
            valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final BaseSlider a;
                {
                    this.a = baseSlider;
                }

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    animator = this.a.V();
                    if (animator != null) {
                        Iterator iterator = this.a.n.iterator();
                        while (iterator.hasNext()) {
                            animator.remove((Drawable)((z2.a)iterator.next()));
                        }
                    }
                }
            });
            this.s.start();
        }
    }

    public final boolean O0(float f3) {
        return this.Q0(this.o0, f3);
    }

    public final void P(int n3) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 17) {
                    if (n3 != 66) {
                        return;
                    }
                    this.A0(Integer.MIN_VALUE);
                    return;
                }
                this.A0(Integer.MAX_VALUE);
                return;
            }
            this.z0(Integer.MIN_VALUE);
            return;
        }
        this.z0(Integer.MAX_VALUE);
    }

    public final double P0(float f3) {
        float f4 = this.q0;
        if (f4 > 0.0f) {
            int n3 = (int)((this.m0 - this.l0) / f4);
            return (double)Math.round(f3 * (float)n3) / (double)n3;
        }
        return f3;
    }

    public final String Q(float f3) {
        if (!this.c0()) {
            String string = (float)((int)f3) == f3 ? "%.0f" : "%.2f";
            return String.format(string, Float.valueOf(f3));
        }
        throw null;
    }

    public final boolean Q0(int n3, float f3) {
        this.p0 = n3;
        if ((double)Math.abs(f3 - ((Float)this.n0.get(n3)).floatValue()) < 1.0E-4) {
            return false;
        }
        f3 = this.T(n3, f3);
        this.n0.set(n3, Float.valueOf(f3));
        this.C(n3);
        return true;
    }

    public final float[] R() {
        float f3 = ((Float)this.n0.get(0)).floatValue();
        ArrayList arrayList = this.n0;
        float f4 = ((Float)arrayList.get(arrayList.size() - 1)).floatValue();
        if (this.n0.size() == 1) {
            f3 = this.l0;
        }
        float f5 = this.B0(f3);
        f3 = f4 = this.B0(f4);
        if (this.g0()) {
            f5 = Math.min(0.5f, f4);
            f3 = Math.max(0.5f, f4);
        }
        if (!this.g0() && (this.p0() || this.s0())) {
            return new float[]{f3, f5};
        }
        return new float[]{f5, f3};
    }

    public final boolean R0() {
        return this.O0(this.Z());
    }

    public void S0(int n3, Rect rect) {
        int n4 = this.G + (int)(this.B0(this.getValues().get(n3).floatValue()) * (float)this.v0);
        int n5 = this.v();
        int n6 = Math.max(this.H / 2, this.A / 2);
        n3 = Math.max(this.I / 2, this.A / 2);
        RectF rectF = new RectF((float)(n4 - n6), (float)(n5 - n3), (float)(n4 + n6), (float)(n5 + n3));
        if (this.s0()) {
            this.M0.mapRect(rectF);
        }
        rect.set((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
    }

    public final float T(int n3, float f3) {
        int n4;
        float f4;
        float f5;
        block5: {
            block4: {
                f4 = f5 = this.getMinSeparation();
                if (this.R0 == 0) {
                    f4 = this.B(f5);
                }
                if (this.p0()) break block4;
                f5 = f4;
                if (!this.s0()) break block5;
            }
            f5 = -f4;
        }
        f4 = (n4 = n3 + 1) >= this.n0.size() ? this.m0 : ((Float)this.n0.get(n4)).floatValue() - f5;
        f5 = --n3 < 0 ? this.l0 : ((Float)this.n0.get(n3)).floatValue() + f5;
        return j0.a.a(f3, f5, f4);
    }

    public final void T0() {
        Drawable drawable;
        if (!this.N0() && this.getMeasuredWidth() > 0 && (drawable = this.getBackground()) instanceof RippleDrawable) {
            float f3 = this.B0(((Float)this.n0.get(this.p0)).floatValue()) * (float)this.v0 + (float)this.G;
            int n3 = this.v();
            int n4 = this.J;
            float f4 = n4;
            float f5 = n3 - n4;
            float f6 = n4;
            float f7 = n3 + n4;
            float[] fArray = new float[]{f3 - f4, f5, f3 + f6, f7};
            if (this.s0()) {
                this.M0.mapPoints(fArray);
            }
            drawable.setHotspotBounds((int)fArray[0], (int)fArray[1], (int)fArray[2], (int)fArray[3]);
        }
    }

    public final int U(ColorStateList colorStateList) {
        return colorStateList.getColorForState(this.getDrawableState(), colorStateList.getDefaultColor());
    }

    public final void U0() {
        float f3;
        boolean bl = this.s0();
        boolean bl2 = this.p0();
        float f4 = 0.5f;
        if (bl && bl2) {
            f3 = 0.5f;
            f4 = -0.2f;
        } else {
            f3 = 1.2f;
            if (bl) {
                f4 = 1.2f;
                f3 = 0.5f;
            }
        }
        Iterator iterator = this.n.iterator();
        while (iterator.hasNext()) {
            ((z2.a)iterator.next()).M0(f4, f3);
        }
    }

    public final ViewOverlay V() {
        ViewGroup viewGroup = com.google.android.material.internal.c0.i(this);
        if (viewGroup == null) {
            return null;
        }
        return viewGroup.getOverlay();
    }

    public final void V0() {
        this.U0();
        int n3 = this.E;
        if (n3 != 0 && n3 != 1) {
            if (n3 != 2) {
                if (n3 == 3) {
                    if (this.isEnabled() && this.q0()) {
                        this.N();
                        return;
                    }
                    this.O();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected labelBehavior: ");
                stringBuilder.append(this.E);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.O();
            return;
        }
        if (this.o0 != -1 && this.isEnabled()) {
            this.N();
            return;
        }
        this.O();
    }

    public final float[] W(float f3, float f4) {
        if (this.s0()) {
            return new float[]{f3, f3, f3, f3, f4, f4, f4, f4};
        }
        return new float[]{f3, f3, f4, f4, f4, f4, f3, f3};
    }

    public final void W0() {
        float f3 = this.v();
        this.M0.reset();
        this.M0.setRotate(90.0f, f3, f3);
    }

    public final int X() {
        return (int)((this.m0 - this.l0) / this.q0 + 1.0f);
    }

    public final void X0() {
        if (this.b0()) {
            int n3;
            this.L = n3 = this.H;
            this.M = this.K;
            int n4 = Math.round((float)n3 * 0.5f);
            n3 = this.H;
            this.setThumbWidth(n4);
            this.setThumbTrackGapSize(this.K - (n3 - n4) / 2);
        }
    }

    public final int Y() {
        return this.v0 / this.z + 1;
    }

    public final void Y0() {
        this.h1();
        float f3 = this.q0;
        int n3 = 0;
        if (f3 <= 0.0f) {
            this.Z0(0);
            return;
        }
        int n4 = this.s0;
        if (n4 != 0) {
            if (n4 != 1) {
                if (n4 != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected tickVisibilityMode: ");
                    stringBuilder.append(this.s0);
                    throw new IllegalStateException(stringBuilder.toString());
                }
            } else {
                n4 = this.X();
                if (n4 <= this.Y()) {
                    n3 = n4;
                }
            }
        } else {
            n3 = Math.min(this.X(), this.Y());
        }
        this.Z0(n3);
    }

    public final float Z() {
        double d3;
        block3: {
            double d4;
            block2: {
                d4 = this.P0(this.Q0);
                if (this.p0()) break block2;
                d3 = d4;
                if (!this.s0()) break block3;
            }
            d3 = 1.0 - d4;
        }
        float f3 = this.m0;
        float f4 = this.l0;
        return (float)(d3 * (double)(f3 - f4) + (double)f4);
    }

    public final void Z0(int n3) {
        if (n3 == 0) {
            this.r0 = null;
            return;
        }
        float[] fArray = this.r0;
        if (fArray == null || fArray.length != n3 * 2) {
            this.r0 = new float[n3 * 2];
        }
        float f3 = (float)this.v0 / (float)(n3 - 1);
        float f4 = this.v();
        for (int i3 = 0; i3 < n3 * 2; i3 += 2) {
            fArray = this.r0;
            fArray[i3] = (float)this.G + (float)i3 / 2.0f * f3;
            fArray[i3 + 1] = f4;
        }
        if (this.s0()) {
            this.M0.mapPoints(this.r0);
        }
    }

    public final float a0() {
        float f3;
        float f4;
        block3: {
            block2: {
                f4 = this.Q0;
                if (this.p0()) break block2;
                f3 = f4;
                if (!this.s0()) break block3;
            }
            f3 = 1.0f - f4;
        }
        float f5 = this.m0;
        f4 = this.l0;
        return f3 * (f5 - f4) + f4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a1(Canvas canvas, Paint paint, RectF rectF, float f3, d d3) {
        int n3;
        float f4;
        float f5;
        block13: {
            block14: {
                block10: {
                    block11: {
                        block12: {
                            if (rectF.isEmpty()) {
                                return;
                            }
                            f5 = this.s(f3);
                            f4 = this.p(f3);
                            n3 = d3.ordinal();
                            if (n3 == 1) break block10;
                            if (n3 == 2) break block11;
                            if (n3 == 3) break block12;
                            f3 = f5;
                            f5 = f4;
                            break block13;
                        }
                        n3 = this.P;
                        f3 = n3;
                        break block14;
                    }
                    f3 = this.P;
                    f5 = f4;
                    break block13;
                }
                n3 = this.P;
                f3 = f5;
            }
            f5 = n3;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.BUTT);
        if (this.b0()) {
            paint.setAntiAlias(true);
        }
        RectF rectF2 = new RectF(rectF);
        if (this.s0()) {
            this.M0.mapRect(rectF2);
        }
        this.E0.reset();
        if (rectF.width() >= f3 + f5) {
            this.E0.addRoundRect(rectF2, this.W(f3, f5), Path.Direction.CW);
            canvas.drawPath(this.E0, paint);
            return;
        }
        f4 = Math.min(f3, f5);
        f3 = Math.max(f3, f5);
        canvas.save();
        this.E0.addRoundRect(rectF2, f4, f4, Path.Direction.CW);
        canvas.clipPath(this.E0);
        n3 = d3.ordinal();
        if (n3 != 1) {
            if (n3 != 2) {
                this.I0.set(rectF.centerX() - f3, rectF.top, rectF.centerX() + f3, rectF.bottom);
            } else {
                d3 = this.I0;
                f5 = rectF.right;
                d3.set(f5 - 2.0f * f3, rectF.top, f5, rectF.bottom);
            }
        } else {
            d3 = this.I0;
            f5 = rectF.left;
            d3.set(f5, rectF.top, 2.0f * f3 + f5, rectF.bottom);
        }
        if (this.s0()) {
            this.M0.mapRect(this.I0);
        }
        canvas.drawRoundRect(this.I0, f3, f3, paint);
        canvas.restore();
    }

    public final boolean b0() {
        return this.K > 0;
    }

    public final void b1() {
        Drawable drawable = this.T;
        if (drawable != null) {
            if (!this.U && this.V != null) {
                this.T = h0.a.r(drawable).mutate();
                this.U = true;
            }
            if (this.U) {
                this.T.setTintList(this.V);
            }
        }
    }

    public boolean c0() {
        return false;
    }

    public final void c1() {
        Drawable drawable = this.R;
        if (drawable != null) {
            if (!this.S && this.V != null) {
                this.R = h0.a.r(drawable).mutate();
                this.S = true;
            }
            if (this.S) {
                this.R.setTintList(this.V);
            }
        }
    }

    public final boolean d0() {
        return this.R != null || this.T != null || this.W != null || this.b0 != null;
        {
        }
    }

    public final void d1() {
        Drawable drawable = this.b0;
        if (drawable != null) {
            if (!this.c0 && this.d0 != null) {
                this.b0 = h0.a.r(drawable).mutate();
                this.c0 = true;
            }
            if (this.c0) {
                this.b0.setTintList(this.d0);
            }
        }
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.j.v(motionEvent) || super.dispatchHoverEvent(motionEvent);
        {
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.c.setColor(this.U(this.D0));
        this.d.setColor(this.U(this.C0));
        this.g.setColor(this.U(this.B0));
        this.h.setColor(this.U(this.A0));
        this.i.setColor(this.U(this.B0));
        for (z2.a a4 : this.n) {
            if (!a4.isStateful()) continue;
            a4.setState(this.getDrawableState());
        }
        if (this.N0.isStateful()) {
            this.N0.setState(this.getDrawableState());
        }
        this.f.setColor(this.U(this.z0));
        this.f.setAlpha(63);
    }

    public final Drawable e0(Drawable drawable) {
        drawable = drawable.mutate().getConstantState().newDrawable();
        this.m(drawable);
        return drawable;
    }

    public final void e1() {
        Drawable drawable = this.W;
        if (drawable != null) {
            if (!this.a0 && this.d0 != null) {
                this.W = h0.a.r(drawable).mutate();
                this.a0 = true;
            }
            if (this.a0) {
                this.W.setTintList(this.d0);
            }
        }
    }

    public final void f0() {
        this.c.setStrokeWidth((float)this.F);
        this.d.setStrokeWidth((float)this.F);
    }

    public final void f1(int n3) {
        this.v0 = Math.max(n3 - this.G * 2, 0);
        this.Y0();
    }

    public boolean g0() {
        return this.Q;
    }

    public final void g1(boolean bl) {
        boolean bl2 = this.y0();
        boolean bl3 = this.x0();
        if (this.s0()) {
            this.W0();
        }
        if (!bl2 && !bl) {
            if (bl3) {
                this.postInvalidate();
            }
            return;
        }
        this.requestLayout();
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.j.x();
    }

    public int getActiveThumbIndex() {
        return this.o0;
    }

    public int getFocusedThumbIndex() {
        return this.p0;
    }

    public int getHaloRadius() {
        return this.J;
    }

    public ColorStateList getHaloTintList() {
        return this.z0;
    }

    public int getLabelBehavior() {
        return this.E;
    }

    public float getMinSeparation() {
        return 0.0f;
    }

    public float getStepSize() {
        return this.q0;
    }

    public float getThumbElevation() {
        return this.N0.C();
    }

    public int getThumbHeight() {
        return this.I;
    }

    public int getThumbRadius() {
        return this.H / 2;
    }

    public ColorStateList getThumbStrokeColor() {
        return this.N0.L();
    }

    public float getThumbStrokeWidth() {
        return this.N0.N();
    }

    public ColorStateList getThumbTintList() {
        return this.N0.D();
    }

    public int getThumbTrackGapSize() {
        return this.K;
    }

    public int getThumbWidth() {
        return this.H;
    }

    public int getTickActiveRadius() {
        return this.t0;
    }

    public ColorStateList getTickActiveTintList() {
        return this.A0;
    }

    public int getTickInactiveRadius() {
        return this.u0;
    }

    public ColorStateList getTickInactiveTintList() {
        return this.B0;
    }

    public ColorStateList getTickTintList() {
        if (this.B0.equals(this.A0)) {
            return this.A0;
        }
        throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
    }

    public int getTickVisibilityMode() {
        return this.s0;
    }

    public ColorStateList getTrackActiveTintList() {
        return this.C0;
    }

    public int getTrackCornerSize() {
        int n3;
        int n4 = n3 = this.O;
        if (n3 == -1) {
            n4 = this.F / 2;
        }
        return n4;
    }

    public int getTrackHeight() {
        return this.F;
    }

    public ColorStateList getTrackIconActiveColor() {
        return this.V;
    }

    public Drawable getTrackIconActiveEnd() {
        return this.T;
    }

    public Drawable getTrackIconActiveStart() {
        return this.R;
    }

    public ColorStateList getTrackIconInactiveColor() {
        return this.d0;
    }

    public Drawable getTrackIconInactiveEnd() {
        return this.b0;
    }

    public Drawable getTrackIconInactiveStart() {
        return this.W;
    }

    public int getTrackIconSize() {
        return this.e0;
    }

    public ColorStateList getTrackInactiveTintList() {
        return this.D0;
    }

    public int getTrackInsideCornerSize() {
        return this.P;
    }

    public int getTrackSidePadding() {
        return this.G;
    }

    public int getTrackStopIndicatorSize() {
        return this.N;
    }

    public ColorStateList getTrackTintList() {
        if (this.D0.equals(this.C0)) {
            return this.C0;
        }
        throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
    }

    public int getTrackWidth() {
        return this.v0;
    }

    public float getValueFrom() {
        return this.l0;
    }

    public float getValueTo() {
        return this.m0;
    }

    public List<Float> getValues() {
        return new ArrayList<Float>(this.n0);
    }

    public final boolean h0() {
        ViewParent viewParent = this.getParent();
        while (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)viewParent;
            if ((viewGroup.canScrollHorizontally(1) || viewGroup.canScrollHorizontally(-1)) && viewGroup.shouldDelayChildPressedState()) {
                return true;
            }
            viewParent = viewParent.getParent();
        }
        return false;
    }

    public final void h1() {
        if (this.y0) {
            this.k1();
            this.j1();
            this.i1();
            this.n1();
            this.y0 = false;
        }
    }

    public final boolean i0() {
        ViewParent viewParent = this.getParent();
        while (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)viewParent;
            if ((viewGroup.canScrollVertically(1) || viewGroup.canScrollVertically(-1)) && viewGroup.shouldDelayChildPressedState()) {
                return true;
            }
            viewParent = viewParent.getParent();
        }
        return false;
    }

    public final void i1() {
        float f3 = this.getMinSeparation();
        if (!(f3 < 0.0f)) {
            float f4 = this.q0;
            if (f4 > 0.0f && f3 > 0.0f) {
                if (this.R0 == 1) {
                    if (f3 < f4 || !this.k0(f3)) {
                        throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)", Float.valueOf(f3), Float.valueOf(this.q0), Float.valueOf(this.q0)));
                    }
                } else {
                    throw new IllegalStateException(String.format("minSeparation(%s) cannot be set as a dimension when using stepSize(%s)", Float.valueOf(f3), Float.valueOf(this.q0)));
                }
            }
            return;
        }
        throw new IllegalStateException(String.format("minSeparation(%s) must be greater or equal to 0", Float.valueOf(f3)));
    }

    public final void j1() {
        if (this.q0 > 0.0f && !this.l1(this.m0)) {
            throw new IllegalStateException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.valueOf(this.q0), Float.valueOf(this.l0), Float.valueOf(this.m0)));
        }
    }

    public final boolean k0(double d3) {
        return Math.abs((double)Math.round(d3 = new BigDecimal(Double.toString(d3)).divide(new BigDecimal(Float.toString(this.q0)), MathContext.DECIMAL64).doubleValue()) - d3) < 1.0E-4;
    }

    public final void k1() {
        if (!(this.l0 >= this.m0)) {
            ArrayList arrayList = this.n0;
            int n3 = arrayList.size();
            int n4 = 0;
            while (n4 < n3) {
                Object object = arrayList.get(n4);
                int n5 = n4 + 1;
                if (!(((Float)(object = (Float)object)).floatValue() < this.l0) && !(((Float)object).floatValue() > this.m0)) {
                    n4 = n5;
                    if (!(this.q0 > 0.0f)) continue;
                    if (this.l1(((Float)object).floatValue())) {
                        n4 = n5;
                        continue;
                    }
                    throw new IllegalStateException(String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", object, Float.valueOf(this.l0), Float.valueOf(this.q0), Float.valueOf(this.q0)));
                }
                throw new IllegalStateException(String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", object, Float.valueOf(this.l0), Float.valueOf(this.m0)));
            }
            return;
        }
        throw new IllegalStateException(String.format("valueFrom(%s) must be smaller than valueTo(%s)", Float.valueOf(this.l0), Float.valueOf(this.m0)));
    }

    public final boolean l0(float f3) {
        float f4 = (float)(this.v0 + this.G * 2) / 2.0f;
        float f5 = (float)this.K + (float)this.H / 2.0f;
        return f3 >= f4 - f5 && f3 <= f4 + f5;
    }

    public final boolean l1(float f3) {
        return this.k0(new BigDecimal(Float.toString(f3)).subtract(new BigDecimal(Float.toString(this.l0)), MathContext.DECIMAL64).doubleValue());
    }

    public final void m(Drawable drawable) {
        int n3 = drawable.getIntrinsicWidth();
        int n4 = drawable.getIntrinsicHeight();
        if (n3 == -1 && n4 == -1) {
            drawable.setBounds(0, 0, this.H, this.I);
            return;
        }
        float f3 = (float)Math.max(this.H, this.I) / (float)Math.max(n3, n4);
        drawable.setBounds(0, 0, (int)((float)n3 * f3), (int)((float)n4 * f3));
    }

    public final boolean m0(float f3) {
        float f4;
        float f5 = (float)this.K + (float)this.H / 2.0f;
        Iterator iterator = this.n0.iterator();
        return iterator.hasNext() && f3 >= (f4 = this.m1(((Float)iterator.next()).floatValue())) - f5 && f3 <= f4 + f5;
    }

    public final float m1(float f3) {
        return this.B0(f3) * (float)this.v0 + (float)this.G;
    }

    public final void n(z2.a a4) {
        a4.N0((View)com.google.android.material.internal.c0.i(this));
    }

    public final boolean n0(MotionEvent motionEvent) {
        return !BaseSlider.j0(motionEvent) && this.h0();
    }

    public final void n1() {
        float f3 = this.q0;
        if (f3 != 0.0f) {
            if ((float)((int)f3) != f3) {
                Log.w((String)X0, (String)String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", "stepSize", Float.valueOf(f3)));
            }
            if ((float)((int)(f3 = this.l0)) != f3) {
                Log.w((String)X0, (String)String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", "valueFrom", Float.valueOf(f3)));
            }
            if ((float)((int)(f3 = this.m0)) != f3) {
                Log.w((String)X0, (String)String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.", "valueTo", Float.valueOf(f3)));
            }
        }
    }

    public final void o(Canvas canvas, RectF rectF, Drawable drawable, boolean bl) {
        if (drawable != null) {
            this.w(rectF, this.K0, this.e0, this.f0, bl);
            if (!this.K0.isEmpty()) {
                this.L(canvas, this.K0, drawable);
                return;
            }
        }
    }

    public final boolean o0(MotionEvent motionEvent) {
        return !BaseSlider.j0(motionEvent) && this.i0();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.W0 = this.isShown();
        this.getViewTreeObserver().addOnScrollChangedListener(this.T0);
        this.getViewTreeObserver().addOnGlobalLayoutListener(this.U0);
        Iterator iterator = this.n.iterator();
        while (iterator.hasNext()) {
            this.n((z2.a)iterator.next());
        }
    }

    public void onDetachedFromWindow() {
        Object object = this.l;
        if (object != null) {
            this.removeCallbacks((Runnable)object);
        }
        this.q = false;
        object = this.n.iterator();
        while (object.hasNext()) {
            this.A((z2.a)object.next());
        }
        this.getViewTreeObserver().removeOnScrollChangedListener(this.T0);
        this.getViewTreeObserver().removeOnGlobalLayoutListener(this.U0);
        super.onDetachedFromWindow();
    }

    public void onDraw(Canvas canvas) {
        if (this.y0) {
            this.h1();
            this.Y0();
        }
        super.onDraw(canvas);
        int n3 = this.v();
        this.G(canvas, this.v0, n3);
        this.E(canvas, this.v0, n3);
        if (!this.p0() && !this.s0()) {
            this.M(canvas, this.F0, this.H0);
        } else {
            this.M(canvas, this.F0, this.G0);
        }
        this.w0(canvas);
        this.v0(canvas, n3);
        if ((this.k0 || this.isFocused()) && this.isEnabled()) {
            this.u0(canvas, this.v0, n3);
        }
        this.V0();
        this.J(canvas, this.v0, n3);
    }

    public void onFocusChanged(boolean bl, int n3, Rect rect) {
        super.onFocusChanged(bl, n3, rect);
        if (!bl) {
            this.o0 = -1;
            this.j.o(this.p0);
            return;
        }
        this.P(n3);
        this.j.V(this.p0);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setVisibleToUser(false);
    }

    public boolean onKeyDown(int n3, KeyEvent keyEvent) {
        if (!this.isEnabled()) {
            return super.onKeyDown(n3, keyEvent);
        }
        if (this.n0.size() == 1) {
            this.o0 = 0;
        }
        if (this.o0 == -1) {
            Boolean bl = this.C0(n3, keyEvent);
            if (bl != null) {
                return bl;
            }
            return super.onKeyDown(n3, keyEvent);
        }
        this.x0 |= keyEvent.isLongPress();
        Float f3 = this.q(n3);
        if (f3 != null) {
            if (this.O0(((Float)this.n0.get(this.o0)).floatValue() + f3.floatValue())) {
                this.T0();
                this.postInvalidate();
            }
            return true;
        }
        if (n3 != 23) {
            if (n3 != 61) {
                if (n3 != 66) {
                    return super.onKeyDown(n3, keyEvent);
                }
            } else {
                if (keyEvent.hasNoModifiers()) {
                    return this.z0(1);
                }
                if (keyEvent.isShiftPressed()) {
                    return this.z0(-1);
                }
                return false;
            }
        }
        this.o0 = -1;
        this.postInvalidate();
        return true;
    }

    public boolean onKeyUp(int n3, KeyEvent keyEvent) {
        this.x0 = false;
        return super.onKeyUp(n3, keyEvent);
    }

    public void onMeasure(int n3, int n4) {
        int n5 = this.E;
        int n6 = 0;
        if (n5 == 1 || this.M0()) {
            n6 = ((z2.a)this.n.get(0)).getIntrinsicHeight();
        }
        n6 = View.MeasureSpec.makeMeasureSpec((int)(this.D + n6), (int)0x40000000);
        if (this.s0()) {
            super.onMeasure(n6, n4);
            return;
        }
        super.onMeasure(n3, n6);
    }

    public void onRestoreInstanceState(Parcelable object) {
        object = (SliderState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.l0 = object.c;
        this.m0 = object.d;
        this.L0(object.e);
        this.q0 = object.f;
        if (object.g) {
            this.requestFocus();
        }
    }

    public Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.c = this.l0;
        sliderState.d = this.m0;
        sliderState.e = new ArrayList(this.n0);
        sliderState.f = this.q0;
        sliderState.g = this.hasFocus();
        return sliderState;
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        if (this.s0()) {
            n3 = n4;
        }
        this.f1(n3);
        this.T0();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        block13: {
            float f3;
            float f4;
            block10: {
                int n3;
                block11: {
                    block14: {
                        block12: {
                            float f5;
                            if (!this.isEnabled()) {
                                return false;
                            }
                            f4 = this.s0() ? motionEvent.getY() : motionEvent.getX();
                            f3 = this.s0() ? motionEvent.getX() : motionEvent.getY();
                            this.Q0 = f5 = (f4 - (float)this.G) / (float)this.v0;
                            this.Q0 = f5 = Math.max(0.0f, f5);
                            this.Q0 = Math.min(1.0f, f5);
                            n3 = motionEvent.getActionMasked();
                            if (n3 == 0) break block10;
                            if (n3 == 1) break block11;
                            if (n3 == 2) break block12;
                            if (n3 == 3) break block11;
                            break block13;
                        }
                        if (this.k0) break block14;
                        if (!this.s0() && this.o0(motionEvent) && Math.abs(f4 - this.h0) < (float)this.t) {
                            return false;
                        }
                        if (this.s0() && this.n0(motionEvent) && Math.abs(f3 - this.i0) < (float)this.t * 0.8f) {
                            return false;
                        }
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                        if (!this.F0()) break block13;
                        this.k0 = true;
                        this.X0();
                        this.D0();
                    }
                    this.R0();
                    this.T0();
                    this.invalidate();
                    break block13;
                }
                this.k0 = false;
                MotionEvent motionEvent2 = this.j0;
                if (motionEvent2 != null && motionEvent2.getActionMasked() == 0 && Math.abs(this.j0.getX() - motionEvent.getX()) <= (float)this.t && Math.abs(this.j0.getY() - motionEvent.getY()) <= (float)this.t && this.F0()) {
                    this.D0();
                }
                if (this.o0 != -1) {
                    this.R0();
                    this.T0();
                    if (this.b0() && (n3 = this.L) != -1 && this.M != -1) {
                        this.setThumbWidth(n3);
                        this.setThumbTrackGapSize(this.M);
                    }
                    this.o0 = -1;
                    this.E0();
                }
                this.invalidate();
                break block13;
            }
            this.h0 = f4;
            this.i0 = f3;
            if (!(!this.s0() && this.o0(motionEvent) || this.s0() && this.n0(motionEvent))) {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                if (this.F0()) {
                    this.requestFocus();
                    this.k0 = true;
                    this.X0();
                    this.D0();
                    this.R0();
                    this.T0();
                    this.invalidate();
                }
            }
        }
        this.setPressed(this.k0);
        this.j0 = MotionEvent.obtain((MotionEvent)motionEvent);
        return true;
    }

    public void onVisibilityAggregated(boolean bl) {
        super.onVisibilityAggregated(bl);
        this.W0 = bl;
    }

    public void onVisibilityChanged(View view, int n3) {
        super.onVisibilityChanged(view, n3);
        if (n3 != 0 && (view = this.V()) != null) {
            Iterator iterator = this.n.iterator();
            while (iterator.hasNext()) {
                view.remove((Drawable)((z2.a)iterator.next()));
            }
        }
    }

    public final float p(float f3) {
        float f4 = f3;
        if (!this.n0.isEmpty()) {
            if (!this.b0()) {
                f4 = f3;
            } else {
                int n3 = !this.p0() && !this.s0() ? this.n0.size() - 1 : 0;
                float f5 = this.m1(((Float)this.n0.get(n3)).floatValue()) - (float)this.G;
                n3 = this.v0;
                f4 = f3;
                if (f5 > (float)n3 - f3) {
                    f4 = Math.max((float)n3 - f5, (float)this.P);
                }
            }
        }
        return f4;
    }

    public final boolean p0() {
        return this.getLayoutDirection() == 1;
    }

    public final Float q(int n3) {
        float f3 = this.x0 ? this.u(20) : this.t();
        if (n3 != 69) {
            if (n3 != 70 && n3 != 81) {
                switch (n3) {
                    default: {
                        return null;
                    }
                    case 22: {
                        float f4 = f3;
                        if (this.p0()) {
                            f4 = -f3;
                        }
                        return Float.valueOf(f4);
                    }
                    case 21: {
                        if (!this.p0()) {
                            f3 = -f3;
                        }
                        return Float.valueOf(f3);
                    }
                    case 20: {
                        if (this.s0()) {
                            return Float.valueOf(-f3);
                        }
                        return null;
                    }
                    case 19: 
                }
                if (this.s0()) {
                    return Float.valueOf(f3);
                }
                return null;
            }
            return Float.valueOf(f3);
        }
        return Float.valueOf(-f3);
    }

    public final boolean q0() {
        Rect rect = new Rect();
        com.google.android.material.internal.c0.i(this).getHitRect(rect);
        return this.getLocalVisibleRect(rect) && this.r0();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void r(z2.a a4, float f3) {
        int n3;
        int n4;
        int n5;
        int n6;
        block3: {
            block2: {
                block0: {
                    block1: {
                        if (!this.s0()) break block0;
                        n6 = this.G + (int)(this.B0(f3) * (float)this.v0) - a4.getIntrinsicHeight() / 2;
                        n5 = a4.getIntrinsicHeight() + n6;
                        if (!this.p0()) break block1;
                        n4 = this.v() - (this.g0 + this.I / 2);
                        n3 = a4.getIntrinsicWidth();
                        break block2;
                    }
                    n3 = this.v() + (this.g0 + this.I / 2);
                    n4 = a4.getIntrinsicWidth() + n3;
                    break block3;
                }
                n6 = this.G + (int)(this.B0(f3) * (float)this.v0) - a4.getIntrinsicWidth() / 2;
                n5 = a4.getIntrinsicWidth() + n6;
                n4 = this.v() - (this.g0 + this.I / 2);
                n3 = a4.getIntrinsicHeight();
            }
            n3 = n4 - n3;
        }
        this.J0.set(n6, n3, n5, n4);
    }

    public final boolean r0() {
        return this.W0;
    }

    public final float s(float f3) {
        float f4 = f3;
        if (!this.n0.isEmpty()) {
            if (!this.b0()) {
                f4 = f3;
            } else {
                int n3 = !this.p0() && !this.s0() ? 0 : this.n0.size() - 1;
                float f5 = this.m1(((Float)this.n0.get(n3)).floatValue()) - (float)this.G;
                f4 = f3;
                if (f5 < f3) {
                    f4 = Math.max(f5, (float)this.P);
                }
            }
        }
        return f4;
    }

    public boolean s0() {
        return this.B == 1;
    }

    public void setActiveThumbIndex(int n3) {
        this.o0 = n3;
    }

    public void setCentered(boolean bl) {
        if (this.Q == bl) {
            return;
        }
        this.Q = bl;
        if (bl) {
            this.setValues(Float.valueOf((this.l0 + this.m0) / 2.0f));
        } else {
            this.setValues(Float.valueOf(this.l0));
        }
        this.g1(true);
    }

    public void setCustomThumbDrawable(int n3) {
        this.setCustomThumbDrawable(this.getResources().getDrawable(n3));
    }

    public void setCustomThumbDrawable(Drawable drawable) {
        this.O0 = this.e0(drawable);
        this.P0.clear();
        this.postInvalidate();
    }

    public void setCustomThumbDrawablesForValues(int ... nArray) {
        Drawable[] drawableArray = new Drawable[nArray.length];
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            drawableArray[i3] = this.getResources().getDrawable(nArray[i3]);
        }
        this.setCustomThumbDrawablesForValues(drawableArray);
    }

    public void setCustomThumbDrawablesForValues(Drawable ... drawableArray) {
        this.O0 = null;
        this.P0 = new ArrayList();
        for (Drawable drawable : drawableArray) {
            this.P0.add(this.e0(drawable));
        }
        this.postInvalidate();
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        int n3 = bl ? 0 : 2;
        this.setLayerType(n3, null);
    }

    public void setFocusedThumbIndex(int n3) {
        if (n3 >= 0 && n3 < this.n0.size()) {
            this.p0 = n3;
            this.j.V(n3);
            this.postInvalidate();
            return;
        }
        throw new IllegalArgumentException("index out of range");
    }

    public void setHaloRadius(int n3) {
        if (n3 == this.J) {
            return;
        }
        this.J = n3;
        Drawable drawable = this.getBackground();
        if (!this.N0() && drawable instanceof RippleDrawable) {
            j2.d.m((RippleDrawable)drawable, this.J);
            return;
        }
        this.postInvalidate();
    }

    public void setHaloRadiusResource(int n3) {
        this.setHaloRadius(this.getResources().getDimensionPixelSize(n3));
    }

    public void setHaloTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.z0)) {
            return;
        }
        this.z0 = colorStateList;
        Drawable drawable = this.getBackground();
        if (!this.N0() && drawable instanceof RippleDrawable) {
            ((RippleDrawable)drawable).setColor(colorStateList);
            return;
        }
        this.f.setColor(this.U(colorStateList));
        this.f.setAlpha(63);
        this.invalidate();
    }

    public void setLabelBehavior(int n3) {
        if (this.E != n3) {
            this.E = n3;
            this.g1(true);
        }
    }

    public void setLabelFormatter(f f3) {
    }

    public void setOrientation(int n3) {
        if (this.B == n3) {
            return;
        }
        this.B = n3;
        this.g1(true);
    }

    public void setSeparationUnit(int n3) {
        this.R0 = n3;
        this.y0 = true;
        this.postInvalidate();
    }

    public void setStepSize(float f3) {
        if (!(f3 < 0.0f)) {
            if (this.q0 != f3) {
                this.q0 = f3;
                this.y0 = true;
                this.postInvalidate();
            }
            return;
        }
        throw new IllegalArgumentException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.valueOf(f3), Float.valueOf(this.l0), Float.valueOf(this.m0)));
    }

    public void setThumbElevation(float f3) {
        this.N0.h0(f3);
    }

    public void setThumbElevationResource(int n3) {
        this.setThumbElevation(this.getResources().getDimension(n3));
    }

    public void setThumbHeight(int n3) {
        if (n3 == this.I) {
            return;
        }
        this.I = n3;
        this.N0.setBounds(0, 0, this.H, n3);
        Object object = this.O0;
        if (object != null) {
            this.m((Drawable)object);
        }
        object = this.P0.iterator();
        while (object.hasNext()) {
            this.m((Drawable)object.next());
        }
        this.g1(false);
    }

    public void setThumbHeightResource(int n3) {
        this.setThumbHeight(this.getResources().getDimensionPixelSize(n3));
    }

    public void setThumbRadius(int n3) {
        this.setThumbWidth(n3 *= 2);
        this.setThumbHeight(n3);
    }

    public void setThumbRadiusResource(int n3) {
        this.setThumbRadius(this.getResources().getDimensionPixelSize(n3));
    }

    public void setThumbStrokeColor(ColorStateList colorStateList) {
        this.N0.u0(colorStateList);
        this.postInvalidate();
    }

    public void setThumbStrokeColorResource(int n3) {
        if (n3 != 0) {
            this.setThumbStrokeColor(d.a.a(this.getContext(), n3));
        }
    }

    public void setThumbStrokeWidth(float f3) {
        this.N0.v0(f3);
        this.postInvalidate();
    }

    public void setThumbStrokeWidthResource(int n3) {
        if (n3 != 0) {
            this.setThumbStrokeWidth(this.getResources().getDimension(n3));
        }
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.N0.D())) {
            return;
        }
        this.N0.i0(colorStateList);
        this.invalidate();
    }

    public void setThumbTrackGapSize(int n3) {
        if (this.K == n3) {
            return;
        }
        this.K = n3;
        this.invalidate();
    }

    public void setThumbWidth(int n3) {
        if (n3 == this.H) {
            return;
        }
        this.H = n3;
        this.N0.setShapeAppearanceModel(v2.o.a().q(0, (float)this.H / 2.0f).m());
        this.N0.setBounds(0, 0, this.H, this.I);
        Object object = this.O0;
        if (object != null) {
            this.m((Drawable)object);
        }
        object = this.P0.iterator();
        while (object.hasNext()) {
            this.m((Drawable)object.next());
        }
        this.g1(false);
    }

    public void setThumbWidthResource(int n3) {
        this.setThumbWidth(this.getResources().getDimensionPixelSize(n3));
    }

    public void setTickActiveRadius(int n3) {
        if (this.t0 != n3) {
            this.t0 = n3;
            this.h.setStrokeWidth((float)(n3 * 2));
            this.g1(false);
        }
    }

    public void setTickActiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.A0)) {
            return;
        }
        this.A0 = colorStateList;
        this.h.setColor(this.U(colorStateList));
        this.invalidate();
    }

    public void setTickInactiveRadius(int n3) {
        if (this.u0 != n3) {
            this.u0 = n3;
            this.g.setStrokeWidth((float)(n3 * 2));
            this.g1(false);
        }
    }

    public void setTickInactiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.B0)) {
            return;
        }
        this.B0 = colorStateList;
        this.g.setColor(this.U(colorStateList));
        this.invalidate();
    }

    public void setTickTintList(ColorStateList colorStateList) {
        this.setTickInactiveTintList(colorStateList);
        this.setTickActiveTintList(colorStateList);
    }

    public void setTickVisibilityMode(int n3) {
        if (this.s0 != n3) {
            this.s0 = n3;
            this.postInvalidate();
        }
    }

    public void setTickVisible(boolean bl) {
        this.setTickVisibilityMode(this.x(bl));
    }

    public void setTrackActiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.C0)) {
            return;
        }
        this.C0 = colorStateList;
        this.d.setColor(this.U(colorStateList));
        this.invalidate();
    }

    public void setTrackCornerSize(int n3) {
        if (this.O == n3) {
            return;
        }
        this.O = n3;
        this.invalidate();
    }

    public void setTrackHeight(int n3) {
        if (this.F != n3) {
            this.F = n3;
            this.f0();
            this.g1(false);
        }
    }

    public void setTrackIconActiveColor(ColorStateList colorStateList) {
        if (colorStateList == this.V) {
            return;
        }
        this.V = colorStateList;
        this.c1();
        this.b1();
        this.invalidate();
    }

    public void setTrackIconActiveEnd(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setTrackIconActiveEnd(drawable);
    }

    public void setTrackIconActiveEnd(Drawable drawable) {
        if (drawable == this.T) {
            return;
        }
        this.T = drawable;
        this.U = false;
        this.b1();
        this.invalidate();
    }

    public void setTrackIconActiveStart(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setTrackIconActiveStart(drawable);
    }

    public void setTrackIconActiveStart(Drawable drawable) {
        if (drawable == this.R) {
            return;
        }
        this.R = drawable;
        this.S = false;
        this.c1();
        this.invalidate();
    }

    public void setTrackIconInactiveColor(ColorStateList colorStateList) {
        if (colorStateList == this.d0) {
            return;
        }
        this.d0 = colorStateList;
        this.e1();
        this.d1();
        this.invalidate();
    }

    public void setTrackIconInactiveEnd(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setTrackIconInactiveEnd(drawable);
    }

    public void setTrackIconInactiveEnd(Drawable drawable) {
        if (drawable == this.b0) {
            return;
        }
        this.b0 = drawable;
        this.c0 = false;
        this.d1();
        this.invalidate();
    }

    public void setTrackIconInactiveStart(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.setTrackIconInactiveStart(drawable);
    }

    public void setTrackIconInactiveStart(Drawable drawable) {
        if (drawable == this.W) {
            return;
        }
        this.W = drawable;
        this.a0 = false;
        this.e1();
        this.invalidate();
    }

    public void setTrackIconSize(int n3) {
        if (this.e0 == n3) {
            return;
        }
        this.e0 = n3;
        this.invalidate();
    }

    public void setTrackInactiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.D0)) {
            return;
        }
        this.D0 = colorStateList;
        this.c.setColor(this.U(colorStateList));
        this.invalidate();
    }

    public void setTrackInsideCornerSize(int n3) {
        if (this.P == n3) {
            return;
        }
        this.P = n3;
        this.invalidate();
    }

    public void setTrackStopIndicatorSize(int n3) {
        if (this.N == n3) {
            return;
        }
        this.N = n3;
        this.i.setStrokeWidth((float)n3);
        this.invalidate();
    }

    public void setTrackTintList(ColorStateList colorStateList) {
        this.setTrackInactiveTintList(colorStateList);
        this.setTrackActiveTintList(colorStateList);
    }

    public void setValueFrom(float f3) {
        this.l0 = f3;
        this.y0 = true;
        this.postInvalidate();
    }

    public void setValueTo(float f3) {
        this.m0 = f3;
        this.y0 = true;
        this.postInvalidate();
    }

    public void setValues(List<Float> list) {
        this.L0(new ArrayList<Float>(list));
    }

    public void setValues(Float ... floatArray) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, floatArray);
        this.L0(arrayList);
    }

    public final float t() {
        float f3;
        float f4 = f3 = this.q0;
        if (f3 == 0.0f) {
            f4 = 1.0f;
        }
        return f4;
    }

    public final void t0(Resources resources) {
        int n3;
        this.C = resources.getDimensionPixelSize(z1.e.mtrl_slider_widget_height);
        this.u = n3 = resources.getDimensionPixelOffset(z1.e.mtrl_slider_track_side_padding);
        this.G = n3;
        this.v = resources.getDimensionPixelSize(z1.e.mtrl_slider_thumb_radius);
        this.w = resources.getDimensionPixelSize(z1.e.mtrl_slider_track_height);
        n3 = z1.e.mtrl_slider_tick_radius;
        this.x = resources.getDimensionPixelSize(n3);
        this.y = resources.getDimensionPixelSize(n3);
        this.z = resources.getDimensionPixelSize(z1.e.mtrl_slider_tick_min_spacing);
        this.g0 = resources.getDimensionPixelSize(z1.e.mtrl_slider_label_padding);
        this.f0 = resources.getDimensionPixelOffset(z1.e.m3_slider_track_icon_padding);
    }

    public final float u(int n3) {
        float f3;
        float f4 = this.t();
        float f5 = (this.m0 - this.l0) / f4;
        if (f5 <= (f3 = (float)n3)) {
            return f4;
        }
        return (float)Math.round(f5 / f3) * f4;
    }

    public final void u0(Canvas canvas, int n3, int n4) {
        if (this.N0()) {
            float f3 = this.G;
            float f4 = this.B0(((Float)this.n0.get(this.p0)).floatValue());
            float f5 = n3;
            float f6 = n4;
            float[] fArray = new float[]{f3 + f4 * f5, f6};
            if (this.s0()) {
                this.M0.mapPoints(fArray);
            }
            if (Build.VERSION.SDK_INT < 28) {
                f6 = fArray[0];
                n3 = this.J;
                f5 = n3;
                f3 = fArray[1];
                canvas.clipRect(f6 - f5, f3 - (float)n3, f6 + (float)n3, f3 + (float)n3, Region.Op.UNION);
            }
            canvas.drawCircle(fArray[0], fArray[1], (float)this.J, this.f);
        }
    }

    public final int v() {
        int n3 = this.D / 2;
        int n4 = this.E;
        int n5 = 0;
        if (n4 == 1 || this.M0()) {
            n5 = ((z2.a)this.n.get(0)).getIntrinsicHeight();
        }
        return n3 + n5;
    }

    public final void v0(Canvas canvas, int n3) {
        if (this.N > 0 && !this.n0.isEmpty()) {
            float f3;
            ArrayList arrayList = this.n0;
            float f4 = ((Float)arrayList.get(arrayList.size() - 1)).floatValue();
            if (f4 < (f3 = this.m0)) {
                this.H(canvas, this.m1(f3), n3);
            }
            if (this.g0() || this.n0.size() > 1 && ((Float)this.n0.get(0)).floatValue() > this.l0) {
                this.H(canvas, this.m1(this.l0), n3);
            }
        }
    }

    public final void w(RectF rectF, RectF rectF2, int n3, int n4, boolean bl) {
        if (rectF.right - rectF.left >= (float)(n4 * 2 + n3)) {
            boolean bl2 = this.p0() || this.s0();
            float f3 = bl ^ bl2 ? rectF.left + (float)n4 : rectF.right - (float)n4 - (float)n3;
            float f4 = this.v();
            float f5 = n3;
            rectF2.set(f3, f4 -= f5 / 2.0f, f3 + f5, f5 + f4);
            return;
        }
        rectF2.setEmpty();
    }

    public final void w0(Canvas canvas) {
        float[] fArray = this.r0;
        if (fArray != null && fArray.length != 0) {
            fArray = this.R();
            int n3 = (int)Math.ceil(fArray[0] * ((float)this.r0.length / 2.0f - 1.0f));
            int n4 = (int)Math.floor(fArray[1] * ((float)this.r0.length / 2.0f - 1.0f));
            if (n3 > 0) {
                this.K(0, n3 * 2, canvas, this.g);
            }
            if (n3 <= n4) {
                this.K(n3 * 2, (n4 + 1) * 2, canvas, this.h);
            }
            if ((n4 = (n4 + 1) * 2) < (fArray = this.r0).length) {
                this.K(n4, fArray.length, canvas, this.g);
            }
        }
    }

    public final int x(boolean bl) {
        if (bl) {
            return 0;
        }
        return 2;
    }

    public final boolean x0() {
        int n3 = Math.max(this.H / 2 - this.v, 0);
        int n4 = Math.max((this.F - this.w) / 2, 0);
        int n5 = Math.max(this.t0 - this.x, 0);
        int n6 = Math.max(this.u0 - this.y, 0);
        n5 = this.u + Math.max(Math.max(n3, n4), Math.max(n5, n6));
        if (this.G == n5) {
            return false;
        }
        this.G = n5;
        if (this.isLaidOut()) {
            n5 = this.s0() ? this.getHeight() : this.getWidth();
            this.f1(n5);
        }
        return true;
    }

    public final ValueAnimator y(boolean bl) {
        int n3;
        float f3 = 1.0f;
        float f4 = bl ? 0.0f : 1.0f;
        ValueAnimator valueAnimator = bl ? this.s : this.r;
        float f5 = BaseSlider.S(valueAnimator, f4);
        f4 = bl ? f3 : 0.0f;
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat((float[])new float[]{f5, f4});
        if (bl) {
            n3 = p2.k.f(this.getContext(), Z0, 83);
            valueAnimator = p2.k.g(this.getContext(), b1, a2.a.e);
        } else {
            n3 = p2.k.f(this.getContext(), a1, 117);
            valueAnimator = p2.k.g(this.getContext(), c1, a2.a.c);
        }
        valueAnimator2.setDuration((long)n3);
        valueAnimator2.setInterpolator((TimeInterpolator)valueAnimator);
        valueAnimator2.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new com.google.android.material.slider.b(this));
        return valueAnimator2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean y0() {
        int n3;
        int n4;
        if (this.s0()) {
            n4 = this.getPaddingLeft();
            n3 = this.getPaddingRight();
        } else {
            n4 = this.getPaddingTop();
            n3 = this.getPaddingBottom();
        }
        n4 += n3;
        int n5 = this.F;
        n3 = this.I;
        n3 = Math.max(this.C, Math.max(n5 + n4, n3 + n4));
        if (n3 != this.D) {
            this.D = n3;
            return true;
        }
        return false;
    }

    public final void z() {
        Iterator iterator;
        boolean bl;
        if (this.n.size() > this.n0.size()) {
            List list = this.n.subList(this.n0.size(), this.n.size());
            for (z2.a a4 : list) {
                if (!this.isAttachedToWindow()) continue;
                this.A(a4);
            }
            list.clear();
        }
        while (true) {
            int n3 = this.n.size();
            int n4 = this.n0.size();
            bl = false;
            if (n3 >= n4) break;
            iterator = z2.a.G0(this.getContext(), null, 0, this.m);
            this.n.add(iterator);
            if (!this.isAttachedToWindow()) continue;
            this.n((z2.a)((Object)iterator));
        }
        if (this.n.size() != 1) {
            bl = true;
        }
        iterator = this.n.iterator();
        while (iterator.hasNext()) {
            ((z2.a)iterator.next()).v0((float)bl);
        }
    }

    public final boolean z0(int n3) {
        int n4 = this.p0;
        this.p0 = n3 = (int)j0.a.c((long)n4 + (long)n3, 0L, this.n0.size() - 1);
        if (n3 == n4) {
            return false;
        }
        if (this.o0 != -1) {
            this.o0 = n3;
        }
        this.T0();
        this.postInvalidate();
        return true;
    }

    public static class SliderState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator(){

            public SliderState a(Parcel parcel) {
                return new SliderState(parcel, null);
            }

            public SliderState[] b(int n3) {
                return new SliderState[n3];
            }
        };
        public float c;
        public float d;
        public ArrayList e;
        public float f;
        public boolean g;

        public SliderState(Parcel parcel) {
            super(parcel);
            ArrayList arrayList;
            this.c = parcel.readFloat();
            this.d = parcel.readFloat();
            this.e = arrayList = new ArrayList();
            parcel.readList(arrayList, Float.class.getClassLoader());
            this.f = parcel.readFloat();
            this.g = parcel.createBooleanArray()[0];
        }

        public /* synthetic */ SliderState(Parcel parcel, a a4) {
            this(parcel);
        }

        public SliderState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeFloat(this.c);
            parcel.writeFloat(this.d);
            parcel.writeList((List)this.e);
            parcel.writeFloat(this.f);
            parcel.writeBooleanArray(new boolean[]{this.g});
        }
    }

    public class b
    implements Runnable {
        public int c;
        public final BaseSlider d;

        public b(BaseSlider baseSlider) {
            this.d = baseSlider;
            this.c = -1;
        }

        public /* synthetic */ b(BaseSlider baseSlider, a a4) {
            this(baseSlider);
        }

        public void a(int n3) {
            this.c = n3;
        }

        @Override
        public void run() {
            this.d.j.W(this.c, 4);
        }
    }

    public static class c
    extends v0.a {
        public final BaseSlider q;
        public final Rect r = new Rect();

        public c(BaseSlider baseSlider) {
            super(baseSlider);
            this.q = baseSlider;
        }

        @Override
        public int B(float f3, float f4) {
            for (int i3 = 0; i3 < this.q.getValues().size(); ++i3) {
                this.q.S0(i3, this.r);
                if (!this.r.contains((int)f3, (int)f4)) continue;
                return i3;
            }
            return -1;
        }

        @Override
        public void C(List list) {
            for (int i3 = 0; i3 < this.q.getValues().size(); ++i3) {
                list.add(i3);
            }
        }

        @Override
        public boolean L(int n3, int n4, Bundle bundle) {
            float f3;
            if (!this.q.isEnabled()) {
                return false;
            }
            if (n4 != 4096 && n4 != 8192) {
                float f4;
                if (n4 != 16908349) {
                    return false;
                }
                if (bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE") && this.q.Q0(n3, f4 = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE"))) {
                    this.q.T0();
                    this.q.postInvalidate();
                    this.E(n3);
                    return true;
                }
                return false;
            }
            float f5 = f3 = this.q.u(20);
            if (n4 == 8192) {
                f5 = -f3;
            }
            f3 = f5;
            if (this.q.p0()) {
                f3 = -f5;
            }
            if (this.q.Q0(n3, f5 = j0.a.a(this.q.getValues().get(n3).floatValue() + f3, this.q.getValueFrom(), this.q.getValueTo()))) {
                this.q.setActiveThumbIndex(n3);
                this.q.J0();
                this.q.T0();
                this.q.postInvalidate();
                this.E(n3);
                return true;
            }
            return false;
        }

        @Override
        public void P(int n3, p0.s s3) {
            float f3;
            s3.b(s.a.L);
            Object object = this.q.getValues();
            Object object2 = object.get(n3);
            float f4 = ((Float)object2).floatValue();
            float f5 = this.q.getValueFrom();
            float f6 = this.q.getValueTo();
            if (this.q.isEnabled()) {
                if (f4 > f5) {
                    s3.a(8192);
                }
                if (f4 < f6) {
                    s3.a(4096);
                }
            }
            Object object3 = NumberFormat.getNumberInstance();
            ((NumberFormat)object3).setMaximumFractionDigits(2);
            double d3 = f5;
            float f7 = f6;
            f5 = f3 = ((NumberFormat)object3).parse(((NumberFormat)object3).format(d3)).floatValue();
            f7 = f6;
            f6 = ((NumberFormat)object3).parse(((NumberFormat)object3).format(f6)).floatValue();
            f5 = f3;
            f7 = f6;
            try {
                float f8;
                f4 = f8 = ((NumberFormat)object3).parse(((NumberFormat)object3).format(f4)).floatValue();
                f5 = f3;
                f7 = f6;
            }
            catch (ParseException parseException) {
                Log.w((String)X0, (String)String.format("Error parsing value(%s), valueFrom(%s), and valueTo(%s) into a float.", object2, Float.valueOf(f5), Float.valueOf(f7)));
            }
            s3.y0(s.g.a(1, f5, f7, f4));
            s3.h0(SeekBar.class.getName());
            StringBuilder stringBuilder = new StringBuilder();
            if (this.q.getContentDescription() != null) {
                stringBuilder.append(this.q.getContentDescription());
                stringBuilder.append(",");
            }
            object3 = this.q.Q(f4);
            object2 = this.q.getContext().getString(z1.k.material_slider_value);
            if (object.size() > 1) {
                object2 = this.Y(n3);
            }
            if (!TextUtils.isEmpty((CharSequence)(object = o0.x0.E(this.q)))) {
                s3.G0((CharSequence)object);
            } else {
                stringBuilder.append(String.format(Locale.getDefault(), "%s, %s", object2, object3));
            }
            s3.l0(stringBuilder.toString());
            this.q.S0(n3, this.r);
            s3.c0(this.r);
        }

        public final String Y(int n3) {
            if (n3 == this.q.getValues().size() - 1) {
                return this.q.getContext().getString(z1.k.material_slider_range_end);
            }
            if (n3 == 0) {
                return this.q.getContext().getString(z1.k.material_slider_range_start);
            }
            return "";
        }
    }

    public static final class d
    extends Enum {
        public static final /* enum */ d c = new d("BOTH", 0);
        public static final /* enum */ d d = new d("LEFT", 1);
        public static final /* enum */ d e = new d("RIGHT", 2);
        public static final /* enum */ d f = new d("NONE", 3);
        public static final d[] g = com.google.android.material.slider.BaseSlider$d.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public d() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ d[] a() {
            return new d[]{c, d, e, f};
        }

        public static d valueOf(String string) {
            return Enum.valueOf(d.class, string);
        }

        public static d[] values() {
            return (d[])g.clone();
        }
    }
}

