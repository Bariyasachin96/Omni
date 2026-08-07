/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.internal.z;
import o0.f0;
import o0.x0;
import o0.z1;
import z1.l;
import z1.m;

public class ScrimInsetsFrameLayout
extends FrameLayout {
    public Drawable c;
    public Rect d;
    public Rect e = new Rect();
    public boolean f = true;
    public boolean g = true;
    public boolean h = true;
    public boolean i = true;

    public ScrimInsetsFrameLayout(Context context) {
        this(context, null);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        context = z.i(context, attributeSet, m.ScrimInsetsFrameLayout, n3, l.Widget_Design_ScrimInsetsFrameLayout, new int[0]);
        this.c = context.getDrawable(m.ScrimInsetsFrameLayout_insetForeground);
        context.recycle();
        this.setWillNotDraw(true);
        x0.r0((View)this, new f0(this){
            public final ScrimInsetsFrameLayout a;
            {
                this.a = scrimInsetsFrameLayout;
            }

            @Override
            public z1 a(View object, z1 z12) {
                object = this.a;
                if (object.d == null) {
                    object.d = new Rect();
                }
                this.a.d.set(z12.j(), z12.l(), z12.k(), z12.i());
                this.a.e(z12);
                object = this.a;
                boolean bl = !z12.m() || this.a.c == null;
                object.setWillNotDraw(bl);
                this.a.postInvalidateOnAnimation();
                return z12.c();
            }
        });
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n3 = this.getWidth();
        int n4 = this.getHeight();
        if (this.d != null && this.c != null) {
            Rect rect;
            Rect rect2;
            int n5 = canvas.save();
            canvas.translate((float)this.getScrollX(), (float)this.getScrollY());
            if (this.f) {
                this.e.set(0, 0, n3, this.d.top);
                this.c.setBounds(this.e);
                this.c.draw(canvas);
            }
            if (this.g) {
                this.e.set(0, n4 - this.d.bottom, n3, n4);
                this.c.setBounds(this.e);
                this.c.draw(canvas);
            }
            if (this.h) {
                rect2 = this.e;
                rect = this.d;
                rect2.set(0, rect.top, rect.left, n4 - rect.bottom);
                this.c.setBounds(this.e);
                this.c.draw(canvas);
            }
            if (this.i) {
                rect2 = this.e;
                rect = this.d;
                rect2.set(n3 - rect.right, rect.top, n3, n4 - rect.bottom);
                this.c.setBounds(this.e);
                this.c.draw(canvas);
            }
            canvas.restoreToCount(n5);
        }
    }

    public void e(z1 z12) {
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Drawable drawable = this.c;
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    public void setDrawBottomInsetForeground(boolean bl) {
        this.g = bl;
    }

    public void setDrawLeftInsetForeground(boolean bl) {
        this.h = bl;
    }

    public void setDrawRightInsetForeground(boolean bl) {
        this.i = bl;
    }

    public void setDrawTopInsetForeground(boolean bl) {
        this.f = bl;
    }

    public void setScrimInsetForeground(Drawable drawable) {
        this.c = drawable;
    }
}

