/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.RadialGradient
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.os.Bundle
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver$OnPreDrawListener
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.TextView
 */
package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.google.android.material.timepicker.ClockHandView;
import com.google.android.material.timepicker.RadialViewGroup;
import java.util.Arrays;
import o0.a;
import o0.x0;
import p0.s;
import s2.c;
import z1.d;
import z1.e;
import z1.g;
import z1.i;
import z1.l;
import z1.m;

class ClockFaceView
extends RadialViewGroup
implements ClockHandView.b {
    public final ClockHandView E;
    public final Rect F = new Rect();
    public final RectF G = new RectF();
    public final Rect H = new Rect();
    public final SparseArray I = new SparseArray();
    public final a J;
    public final int[] K;
    public final float[] L = new float[]{0.0f, 0.9f, 1.0f};
    public final int M;
    public final int N;
    public final int O;
    public final int P;
    public String[] Q;
    public float R;
    public final ColorStateList S;

    public ClockFaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialClockStyle);
    }

    public ClockFaceView(Context objectArray, AttributeSet attributeSet, int n3) {
        super((Context)objectArray, attributeSet, n3);
        ClockHandView clockHandView;
        ColorStateList colorStateList;
        TypedArray typedArray = objectArray.obtainStyledAttributes(attributeSet, z1.m.ClockFaceView, n3, z1.l.Widget_MaterialComponents_TimePicker_Clock);
        attributeSet = this.getResources();
        this.S = colorStateList = s2.c.a((Context)objectArray, typedArray, z1.m.ClockFaceView_clockNumberTextColor);
        LayoutInflater.from((Context)objectArray).inflate(z1.i.material_clockface_view, (ViewGroup)this, true);
        this.E = clockHandView = (ClockHandView)this.findViewById(z1.g.material_clock_hand);
        this.M = attributeSet.getDimensionPixelSize(z1.e.material_clock_hand_padding);
        n3 = colorStateList.getDefaultColor();
        n3 = colorStateList.getColorForState(new int[]{0x10100A1}, n3);
        this.K = new int[]{n3, n3, colorStateList.getDefaultColor()};
        clockHandView.b(this);
        n3 = d.a.a((Context)objectArray, z1.d.material_timepicker_clockface).getDefaultColor();
        objectArray = s2.c.a((Context)objectArray, typedArray, z1.m.ClockFaceView_clockFaceBackgroundColor);
        if (objectArray != null) {
            n3 = objectArray.getDefaultColor();
        }
        this.setBackgroundColor(n3);
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(this){
            public final ClockFaceView c;
            {
                this.c = clockFaceView;
            }

            public boolean onPreDraw() {
                if (!this.c.isShown()) {
                    return true;
                }
                this.c.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                int n3 = this.c.getHeight() / 2;
                int n4 = this.c.E.i();
                int n5 = this.c.M;
                this.c.G(n3 - n4 - n5);
                return true;
            }
        });
        this.setFocusable(false);
        typedArray.recycle();
        this.J = new a(this){
            public final ClockFaceView d;
            {
                this.d = clockFaceView;
            }

            @Override
            public void g(View view, s s3) {
                super.g(view, s3);
                int n3 = (Integer)view.getTag(z1.g.material_value_index);
                if (n3 > 0) {
                    s3.I0((View)this.d.I.get(n3 - 1));
                }
                s3.k0(s.f.a(0, 1, n3, 1, false, view.isSelected()));
                s3.i0(true);
                s3.b(s.a.i);
            }

            @Override
            public boolean j(View view, int n3, Bundle bundle) {
                if (n3 == 16) {
                    long l3 = SystemClock.uptimeMillis();
                    view.getHitRect(this.d.F);
                    float f3 = this.d.F.centerX();
                    float f4 = this.d.F.centerY();
                    this.d.E.onTouchEvent(MotionEvent.obtain((long)l3, (long)l3, (int)0, (float)f3, (float)f4, (int)0));
                    this.d.E.onTouchEvent(MotionEvent.obtain((long)l3, (long)l3, (int)1, (float)f3, (float)f4, (int)0));
                    return true;
                }
                return super.j(view, n3, bundle);
            }
        };
        objectArray = new String[12];
        Arrays.fill(objectArray, "");
        this.S((String[])objectArray, 0);
        this.N = attributeSet.getDimensionPixelSize(z1.e.material_time_picker_minimum_screen_height);
        this.O = attributeSet.getDimensionPixelSize(z1.e.material_time_picker_minimum_screen_width);
        this.P = attributeSet.getDimensionPixelSize(z1.e.material_clock_size);
    }

    public static float R(float f3, float f4, float f5) {
        return Math.max(Math.max(f3, f4), f5);
    }

    @Override
    public void G(int n3) {
        if (n3 != this.F()) {
            super.G(n3);
            this.E.m(this.F());
        }
    }

    @Override
    public void I() {
        super.I();
        for (int i3 = 0; i3 < this.I.size(); ++i3) {
            ((TextView)this.I.get(i3)).setVisibility(0);
        }
    }

    public final void O() {
        RectF rectF = this.E.e();
        TextView textView = this.Q(rectF);
        for (int i3 = 0; i3 < this.I.size(); ++i3) {
            TextView textView2 = (TextView)this.I.get(i3);
            if (textView2 == null) continue;
            boolean bl = textView2 == textView;
            textView2.setSelected(bl);
            RadialGradient radialGradient = this.P(rectF, textView2);
            textView2.getPaint().setShader((Shader)radialGradient);
            textView2.invalidate();
        }
    }

    public final RadialGradient P(RectF rectF, TextView textView) {
        textView.getHitRect(this.F);
        this.G.set(this.F);
        textView.getLineBounds(0, this.H);
        textView = this.G;
        Rect rect = this.H;
        textView.inset((float)rect.left, (float)rect.top);
        if (!RectF.intersects((RectF)rectF, (RectF)this.G)) {
            return null;
        }
        return new RadialGradient(rectF.centerX() - this.G.left, rectF.centerY() - this.G.top, rectF.width() * 0.5f, this.K, this.L, Shader.TileMode.CLAMP);
    }

    public final TextView Q(RectF rectF) {
        float f3 = Float.MAX_VALUE;
        TextView textView = null;
        for (int i3 = 0; i3 < this.I.size(); ++i3) {
            float f4;
            TextView textView2 = (TextView)this.I.get(i3);
            if (textView2 == null) {
                f4 = f3;
            } else {
                textView2.getHitRect(this.F);
                this.G.set(this.F);
                this.G.union(rectF);
                float f5 = this.G.width() * this.G.height();
                f4 = f3;
                if (f5 < f3) {
                    textView = textView2;
                    f4 = f5;
                }
            }
            f3 = f4;
        }
        return textView;
    }

    public void S(String[] stringArray, int n3) {
        this.Q = stringArray;
        this.T(n3);
    }

    public final void T(int n3) {
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.getContext());
        int n4 = this.I.size();
        boolean bl = false;
        for (int i3 = 0; i3 < Math.max(this.Q.length, n4); ++i3) {
            boolean bl2;
            TextView textView = (TextView)this.I.get(i3);
            if (i3 >= this.Q.length) {
                this.removeView((View)textView);
                this.I.remove(i3);
                bl2 = bl;
            } else {
                TextView textView2 = textView;
                if (textView == null) {
                    textView2 = (TextView)layoutInflater.inflate(z1.i.material_clockface_textview, (ViewGroup)this, false);
                    this.I.put(i3, (Object)textView2);
                    this.addView((View)textView2);
                }
                textView2.setText((CharSequence)this.Q[i3]);
                textView2.setTag(z1.g.material_value_index, (Object)i3);
                int n5 = i3 / 12 + 1;
                textView2.setTag(z1.g.material_clock_level, (Object)n5);
                if (n5 > 1) {
                    bl = true;
                }
                x0.h0((View)textView2, this.J);
                textView2.setTextColor(this.S);
                bl2 = bl;
                if (n3 != 0) {
                    textView2.setContentDescription((CharSequence)this.getResources().getString(n3, new Object[]{this.Q[i3]}));
                    bl2 = bl;
                }
            }
            bl = bl2;
        }
        this.E.q(bl);
    }

    @Override
    public void a(float f3, boolean bl) {
        if (Math.abs(this.R - f3) > 0.001f) {
            this.R = f3;
            this.O();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        p0.s.L0(accessibilityNodeInfo).j0(s.e.b(1, this.Q.length, false, 1));
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        this.O();
    }

    @Override
    public void onMeasure(int n3, int n4) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float f3 = displayMetrics.heightPixels;
        float f4 = displayMetrics.widthPixels;
        n3 = (int)((float)this.P / ClockFaceView.R((float)this.N / f3, (float)this.O / f4, 1.0f));
        n4 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000);
        this.setMeasuredDimension(n3, n3);
        super.onMeasure(n4, n4);
    }
}

