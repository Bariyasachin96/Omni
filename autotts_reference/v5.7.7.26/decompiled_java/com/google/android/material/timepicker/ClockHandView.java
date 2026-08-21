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
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Pair
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewConfiguration
 */
package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.google.android.material.internal.c0;
import com.google.android.material.timepicker.a;
import java.util.ArrayList;
import java.util.List;
import p2.k;
import z1.c;
import z1.e;
import z1.l;
import z1.m;

class ClockHandView
extends View {
    public final int c;
    public final TimeInterpolator d;
    public final ValueAnimator e = new ValueAnimator();
    public boolean f;
    public float g;
    public float h;
    public boolean i;
    public final int j;
    public boolean k;
    public final List l = new ArrayList();
    public final int m;
    public final float n;
    public final Paint o;
    public final RectF p;
    public final int q;
    public float r;
    public boolean s;
    public double t;
    public int u;
    public int v;

    public ClockHandView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialClockStyle);
    }

    public ClockHandView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        Paint paint;
        this.o = paint = new Paint();
        this.p = new RectF();
        this.v = 1;
        attributeSet = context.obtainStyledAttributes(attributeSet, z1.m.ClockHandView, n3, z1.l.Widget_MaterialComponents_TimePicker_Clock);
        this.c = p2.k.f(context, z1.c.motionDurationLong2, 200);
        this.d = p2.k.g(context, z1.c.motionEasingEmphasizedInterpolator, a2.a.b);
        this.u = attributeSet.getDimensionPixelSize(z1.m.ClockHandView_materialCircleRadius, 0);
        this.m = attributeSet.getDimensionPixelSize(z1.m.ClockHandView_selectorSize, 0);
        Resources resources = this.getResources();
        this.q = resources.getDimensionPixelSize(z1.e.material_clock_hand_stroke_width);
        this.n = resources.getDimensionPixelSize(z1.e.material_clock_hand_center_dot_radius);
        n3 = attributeSet.getColor(z1.m.ClockHandView_clockHandColor, 0);
        paint.setAntiAlias(true);
        paint.setColor(n3);
        this.n(0.0f);
        this.j = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.setImportantForAccessibility(2);
        attributeSet.recycle();
        this.l();
    }

    public static /* synthetic */ void a(ClockHandView clockHandView, ValueAnimator valueAnimator) {
        ((Object)((Object)clockHandView)).getClass();
        clockHandView.p(((Float)valueAnimator.getAnimatedValue()).floatValue(), true);
    }

    public void b(b b3) {
        this.l.add(b3);
    }

    public final void c(float f3, float f4) {
        int n3 = this.getWidth();
        int n4 = 2;
        n3 /= 2;
        int n5 = this.getHeight() / 2;
        f3 = o2.a.b(n3, n5, f3, f4);
        if (!(f3 <= (float)(n3 = this.h(2)) + (f4 = c0.g(this.getContext(), 12)))) {
            n4 = 1;
        }
        this.v = n4;
    }

    public final void d(Canvas canvas) {
        int n3 = this.getHeight() / 2;
        int n4 = this.getWidth() / 2;
        int n5 = this.h(this.v);
        float f3 = n4;
        float f4 = n5;
        float f5 = (float)Math.cos(this.t);
        float f6 = n3;
        float f7 = (float)Math.sin(this.t);
        this.o.setStrokeWidth(0.0f);
        canvas.drawCircle(f5 * f4 + f3, f4 * f7 + f6, (float)this.m, this.o);
        double d3 = Math.sin(this.t);
        double d4 = Math.cos(this.t);
        double d5 = n5 - this.m;
        f7 = n4 + (int)(d4 * d5);
        f5 = n3 + (int)(d5 * d3);
        this.o.setStrokeWidth((float)this.q);
        canvas.drawLine(f3, f6, f7, f5, this.o);
        canvas.drawCircle(f3, f6, this.n, this.o);
    }

    public RectF e() {
        return this.p;
    }

    public final int f(float f3, float f4) {
        int n3 = this.getWidth() / 2;
        int n4 = this.getHeight() / 2;
        double d3 = f3 - (float)n3;
        if ((n3 = (n4 = (int)Math.toDegrees(Math.atan2(f4 - (float)n4, d3))) + 90) < 0) {
            return n4 + 450;
        }
        return n3;
    }

    public float g() {
        return this.r;
    }

    public final int h(int n3) {
        if (n3 == 2) {
            return Math.round((float)this.u * 0.66f);
        }
        return this.u;
    }

    public int i() {
        return this.m;
    }

    public final Pair j(float f3) {
        float f4;
        float f5 = f4 = this.g();
        float f6 = f3;
        if (Math.abs(f4 - f3) > 180.0f) {
            float f7 = f3;
            if (f4 > 180.0f) {
                f7 = f3;
                if (f3 < 180.0f) {
                    f7 = f3 + 360.0f;
                }
            }
            f5 = f4;
            f6 = f7;
            if (f4 < 180.0f) {
                f5 = f4;
                f6 = f7;
                if (f7 > 180.0f) {
                    f5 = f4 + 360.0f;
                    f6 = f7;
                }
            }
        }
        return new Pair((Object)Float.valueOf(f5), (Object)Float.valueOf(f6));
    }

    public final boolean k(float f3, float f4, boolean bl, boolean bl2, boolean bl3) {
        int n3 = this.f(f3, f4);
        f3 = this.g();
        f4 = n3;
        boolean bl4 = false;
        n3 = f3 != f4 ? 1 : 0;
        if (bl2 && n3 != 0) {
            return true;
        }
        if (n3 == 0 && !bl) {
            return false;
        }
        bl = bl4;
        if (bl3) {
            bl = bl4;
            if (this.f) {
                bl = true;
            }
        }
        this.o(f4, bl);
        return true;
    }

    public final void l() {
        this.e.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new a(this));
        this.e.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final ClockHandView a;
            {
                this.a = clockHandView;
            }

            public void onAnimationCancel(Animator animator) {
                animator.end();
            }
        });
    }

    public void m(int n3) {
        this.u = n3;
        this.invalidate();
    }

    public void n(float f3) {
        this.o(f3, false);
    }

    public void o(float f3, boolean bl) {
        this.e.cancel();
        if (!bl) {
            this.p(f3, false);
            return;
        }
        Pair pair = this.j(f3);
        this.e.setFloatValues(new float[]{((Float)pair.first).floatValue(), ((Float)pair.second).floatValue()});
        this.e.setDuration((long)this.c);
        this.e.setInterpolator(this.d);
        this.e.start();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.d(canvas);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        if (!this.e.isRunning()) {
            this.n(this.g());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        int n3 = motionEvent.getActionMasked();
        float f3 = motionEvent.getX();
        float f4 = motionEvent.getY();
        if (n3 != 0) {
            if (n3 != 1 && n3 != 2) {
                bl3 = false;
                bl = bl2 = false;
            } else {
                int n4 = (int)(f3 - this.g);
                int n5 = (int)(f4 - this.h);
                bl3 = n4 * n4 + n5 * n5 > this.j;
                this.i = bl3;
                bl2 = this.s;
                bl3 = n3 == 1;
                if (this.k) {
                    this.c(f3, f4);
                }
                bl = bl3;
                boolean bl4 = false;
                bl3 = bl2;
                bl2 = bl4;
            }
        } else {
            this.g = f3;
            this.h = f4;
            this.i = true;
            this.s = false;
            bl2 = true;
            bl3 = false;
            bl = false;
        }
        this.s |= this.k(f3, f4, bl3, bl2, bl);
        return true;
    }

    public final void p(float f3, boolean bl) {
        this.r = f3 %= 360.0f;
        this.t = Math.toRadians(f3 - 90.0f);
        int n3 = this.getHeight() / 2;
        int n4 = this.getWidth() / 2;
        int n5 = this.h(this.v);
        float f4 = n4;
        float f5 = n5;
        f4 += (float)Math.cos(this.t) * f5;
        f5 = (float)n3 + f5 * (float)Math.sin(this.t);
        Object object = this.p;
        n5 = this.m;
        object.set(f4 - (float)n5, f5 - (float)n5, f4 + (float)n5, f5 + (float)n5);
        object = this.l.iterator();
        while (object.hasNext()) {
            ((b)object.next()).a(f3, bl);
        }
        this.invalidate();
    }

    public void q(boolean bl) {
        if (this.k && !bl) {
            this.v = 1;
        }
        this.k = bl;
        this.invalidate();
    }

    public static interface b {
        public void a(float var1, boolean var2);
    }
}

