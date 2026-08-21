/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnHoverListener
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout
 */
package com.google.android.material.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import f2.f;
import f2.g;
import f2.h;
import f2.j;
import j0.a;
import v2.c;
import v2.d;
import v2.o;
import v2.r;
import v2.s;

public class MaskableFrameLayout
extends FrameLayout
implements f,
r {
    public float c = -1.0f;
    public final RectF d = new RectF();
    public final Rect e = new Rect();
    public o f;
    public final s g = s.a((View)this);
    public Boolean h = null;
    public View.OnHoverListener i;
    public boolean j = false;

    public MaskableFrameLayout(Context context) {
        this(context, null);
    }

    public MaskableFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaskableFrameLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.setShapeAppearanceModel(o.f(context, attributeSet, n3, 0, 0).m());
    }

    public static /* synthetic */ d a(d d3) {
        d d4 = d3;
        if (d3 instanceof v2.a) {
            d4 = v2.c.b((v2.a)d3);
        }
        return d4;
    }

    public static /* synthetic */ void b(MaskableFrameLayout maskableFrameLayout, Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    public final void c() {
        this.g.f((View)this, this.d);
    }

    public final void d() {
        if (this.c != -1.0f) {
            float f3 = a2.a.b(0.0f, (float)this.getWidth() / 2.0f, 0.0f, 1.0f, this.c);
            this.setMaskRectF(new RectF(f3, 0.0f, (float)this.getWidth() - f3, (float)this.getHeight()));
        }
    }

    public void dispatchDraw(Canvas canvas) {
        this.g.e(canvas, new h(this));
    }

    public void getFocusedRect(Rect rect) {
        RectF rectF = this.d;
        rect.set((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
    }

    public RectF getMaskRectF() {
        return this.d;
    }

    @Deprecated
    public float getMaskXPercentage() {
        return this.c;
    }

    public o getShapeAppearanceModel() {
        return this.f;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Boolean bl = this.h;
        if (bl != null) {
            this.g.h((View)this, bl);
        }
    }

    public void onDetachedFromWindow() {
        this.h = this.g.c();
        this.g.h((View)this, true);
        super.onDetachedFromWindow();
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        float f3;
        float f4;
        int n3 = motionEvent.getAction();
        if (!(this.d.isEmpty() || n3 != 9 && n3 != 10 && n3 != 7 || this.d.contains(f4 = motionEvent.getX(), f3 = motionEvent.getY()))) {
            if (this.j && this.i != null) {
                motionEvent.setAction(10);
                this.i.onHover((View)this, motionEvent);
            }
            this.j = false;
            return false;
        }
        if (this.i != null) {
            if (!this.j && n3 == 7) {
                motionEvent.setAction(9);
                this.j = true;
            }
            if (n3 == 7 || n3 == 9) {
                this.j = true;
            }
            this.i.onHover((View)this, motionEvent);
        }
        return super.onHoverEvent(motionEvent);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        Rect rect;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.getBoundsInScreen(this.e);
        if (this.getX() > 0.0f) {
            rect = this.e;
            rect.left = (int)((float)rect.left + this.d.left);
        }
        if (this.getY() > 0.0f) {
            rect = this.e;
            rect.top = (int)((float)rect.top + this.d.top);
        }
        rect = this.e;
        rect.right = rect.left + Math.round(this.d.width());
        rect = this.e;
        rect.bottom = rect.top + Math.round(this.d.height());
        accessibilityNodeInfo.setBoundsInScreen(this.e);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f3;
        float f4;
        if (!this.d.isEmpty() && !this.d.contains(f4 = motionEvent.getX(), f3 = motionEvent.getY())) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        if (this.c != -1.0f) {
            this.d();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f3;
        float f4;
        if (!this.d.isEmpty() && motionEvent.getAction() == 0 && !this.d.contains(f4 = motionEvent.getX(), f3 = motionEvent.getY())) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setForceCompatClipping(boolean bl) {
        this.g.h((View)this, bl);
    }

    @Override
    public void setMaskRectF(RectF rectF) {
        this.d.set(rectF);
        this.c();
    }

    @Deprecated
    public void setMaskXPercentage(float f3) {
        if (this.c != (f3 = a.a(f3, 0.0f, 1.0f))) {
            this.c = f3;
            this.d();
        }
    }

    public void setOnHoverListener(View.OnHoverListener onHoverListener) {
        this.i = onHoverListener;
    }

    public void setOnMaskChangedListener(j j3) {
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        this.f = o3 = o3.z(new g());
        this.g.g((View)this, o3);
    }
}

