/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetrics
 *  android.graphics.Rect
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 */
package z2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.internal.w;
import com.google.android.material.internal.z;
import s2.c;
import s2.d;
import v2.g;
import v2.h;
import v2.i;
import z1.e;
import z1.l;
import z1.m;

public class a
extends i
implements w.b {
    public static final int d0 = z1.l.Widget_MaterialComponents_Tooltip;
    public static final int e0 = z1.c.tooltipStyle;
    public CharSequence L;
    public final Context M;
    public final Paint.FontMetrics N = new Paint.FontMetrics();
    public final w O;
    public final View.OnLayoutChangeListener P;
    public final Rect Q;
    public int R;
    public int S;
    public int T;
    public int U;
    public boolean V;
    public int W;
    public int X;
    public float Y;
    public float Z;
    public float a0;
    public float b0;
    public float c0;

    public a(Context context, AttributeSet object, int n3, int n4) {
        super(context, (AttributeSet)object, n3, n4);
        object = new w(this);
        this.O = object;
        this.P = new View.OnLayoutChangeListener(this){
            public final a a;
            {
                this.a = a4;
            }

            public void onLayoutChange(View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
                this.a.R0(view);
            }
        };
        this.Q = new Rect();
        this.Y = 1.0f;
        this.Z = 1.0f;
        this.a0 = 0.5f;
        this.b0 = 0.5f;
        this.c0 = 1.0f;
        this.M = context;
        ((w)object).g().density = context.getResources().getDisplayMetrics().density;
        ((w)object).g().setTextAlign(Paint.Align.CENTER);
    }

    private float E0() {
        this.O.g().getFontMetrics(this.N);
        Paint.FontMetrics fontMetrics = this.N;
        return (fontMetrics.descent + fontMetrics.ascent) / 2.0f;
    }

    public static a G0(Context object, AttributeSet attributeSet, int n3, int n4) {
        object = new a((Context)object, attributeSet, n3, n4);
        super.L0(attributeSet, n3, n4);
        return object;
    }

    private void L0(AttributeSet attributeSet, int n3, int n4) {
        boolean bl;
        attributeSet = com.google.android.material.internal.z.i(this.M, attributeSet, z1.m.Tooltip, n3, n4, new int[0]);
        this.W = this.M.getResources().getDimensionPixelSize(z1.e.mtrl_tooltip_arrowSize);
        this.V = bl = attributeSet.getBoolean(z1.m.Tooltip_showMarker, true);
        if (bl) {
            this.setShapeAppearanceModel(this.K().w().s(this.H0()).m());
        } else {
            this.W = 0;
        }
        this.P0(attributeSet.getText(z1.m.Tooltip_android_text));
        d d3 = s2.c.h(this.M, (TypedArray)attributeSet, z1.m.Tooltip_android_textAppearance);
        if (d3 != null && attributeSet.hasValue(n3 = z1.m.Tooltip_android_textColor)) {
            d3.n(s2.c.a(this.M, (TypedArray)attributeSet, n3));
        }
        this.Q0(d3);
        n3 = h2.a.c(this.M, z1.c.colorOnBackground, a.class.getCanonicalName());
        n3 = h2.a.i(g0.a.k(h2.a.c(this.M, 0x1010031, a.class.getCanonicalName()), 229), g0.a.k(n3, 153));
        this.i0(ColorStateList.valueOf((int)attributeSet.getColor(z1.m.Tooltip_backgroundTint, n3)));
        this.u0(ColorStateList.valueOf((int)h2.a.c(this.M, z1.c.colorSurface, a.class.getCanonicalName())));
        this.R = attributeSet.getDimensionPixelSize(z1.m.Tooltip_android_padding, 0);
        this.S = attributeSet.getDimensionPixelSize(z1.m.Tooltip_android_minWidth, 0);
        this.T = attributeSet.getDimensionPixelSize(z1.m.Tooltip_android_minHeight, 0);
        this.U = attributeSet.getDimensionPixelSize(z1.m.Tooltip_android_layout_margin, 0);
        attributeSet.recycle();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final float D0() {
        int n3;
        if (this.Q.right - this.getBounds().right - this.X - this.U < 0) {
            n3 = this.Q.right - this.getBounds().right - this.X - this.U;
            return n3;
        }
        if (this.Q.left - this.getBounds().left - this.X + this.U <= 0) return 0.0f;
        n3 = this.Q.left - this.getBounds().left - this.X + this.U;
        return n3;
    }

    public final float F0(Rect rect) {
        return (float)rect.centerY() - this.E0();
    }

    public final g H0() {
        float f3 = -this.D0();
        float f4 = (float)(((double)this.getBounds().width() - (double)this.W * Math.sqrt(2.0)) / 2.0);
        f4 = Math.min(Math.max(f3, -f4), f4);
        return new v2.l(new h(this.W), f4);
    }

    public void I0(View view) {
        if (view == null) {
            return;
        }
        view.removeOnLayoutChangeListener(this.P);
    }

    public final void J0(Canvas canvas) {
        if (this.L == null) {
            return;
        }
        Rect rect = this.getBounds();
        int n3 = (int)this.F0(rect);
        if (this.O.e() != null) {
            this.O.g().drawableState = this.getState();
            this.O.n(this.M);
            this.O.g().setAlpha((int)(this.c0 * 255.0f));
        }
        CharSequence charSequence = this.L;
        canvas.drawText(charSequence, 0, charSequence.length(), (float)rect.centerX(), (float)n3, (Paint)this.O.g());
    }

    public final float K0() {
        CharSequence charSequence = this.L;
        if (charSequence == null) {
            return 0.0f;
        }
        return this.O.h(charSequence.toString());
    }

    public void M0(float f3, float f4) {
        this.a0 = f3;
        this.b0 = f4;
        this.invalidateSelf();
    }

    public void N0(View view) {
        if (view == null) {
            return;
        }
        this.R0(view);
        view.addOnLayoutChangeListener(this.P);
    }

    public void O0(float f3) {
        this.Y = f3;
        this.Z = f3;
        this.c0 = a2.a.b(0.0f, 1.0f, 0.19f, 1.0f, f3);
        this.invalidateSelf();
    }

    public void P0(CharSequence charSequence) {
        if (!TextUtils.equals((CharSequence)this.L, (CharSequence)charSequence)) {
            this.L = charSequence;
            this.O.m(true);
            this.invalidateSelf();
        }
    }

    public void Q0(d d3) {
        this.O.k(d3, this.M);
    }

    public final void R0(View view) {
        int[] nArray = new int[2];
        view.getLocationOnScreen(nArray);
        this.X = nArray[0];
        view.getWindowVisibleDisplayFrame(this.Q);
    }

    @Override
    public void a() {
        this.invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        float f3 = this.D0();
        float f4 = (float)(-((double)this.W * Math.sqrt(2.0) - (double)this.W));
        canvas.scale(this.Y, this.Z, (float)this.getBounds().left + (float)this.getBounds().width() * this.a0, (float)this.getBounds().top + (float)this.getBounds().height() * this.b0);
        canvas.translate(f3, f4);
        super.draw(canvas);
        this.J0(canvas);
        canvas.restore();
    }

    public int getIntrinsicHeight() {
        return (int)Math.max(this.O.g().getTextSize(), (float)this.T);
    }

    public int getIntrinsicWidth() {
        return (int)Math.max((float)(this.R * 2) + this.K0(), (float)this.S);
    }

    @Override
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.V) {
            this.setShapeAppearanceModel(this.K().w().s(this.H0()).m());
        }
    }

    @Override
    public boolean onStateChange(int[] nArray) {
        return super.onStateChange(nArray);
    }
}

