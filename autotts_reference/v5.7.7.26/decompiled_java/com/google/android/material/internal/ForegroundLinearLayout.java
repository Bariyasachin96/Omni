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
 *  android.view.Gravity
 */
package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.google.android.material.internal.z;
import z1.m;

public class ForegroundLinearLayout
extends LinearLayoutCompat {
    public Drawable r;
    public final Rect s = new Rect();
    public final Rect t = new Rect();
    public int u = 119;
    public boolean v = true;
    public boolean w = false;

    public ForegroundLinearLayout(Context context) {
        this(context, null);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        context = z.i(context, attributeSet, z1.m.ForegroundLinearLayout, n3, 0, new int[0]);
        this.u = context.getInt(z1.m.ForegroundLinearLayout_android_foregroundGravity, this.u);
        attributeSet = context.getDrawable(z1.m.ForegroundLinearLayout_android_foreground);
        if (attributeSet != null) {
            this.setForeground((Drawable)attributeSet);
        }
        this.v = context.getBoolean(z1.m.ForegroundLinearLayout_foregroundInsidePadding, true);
        context.recycle();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.r;
        if (drawable != null) {
            if (this.w) {
                this.w = false;
                Rect rect = this.s;
                Rect rect2 = this.t;
                int n3 = this.getRight() - this.getLeft();
                int n4 = this.getBottom() - this.getTop();
                if (this.v) {
                    rect.set(0, 0, n3, n4);
                } else {
                    rect.set(this.getPaddingLeft(), this.getPaddingTop(), n3 - this.getPaddingRight(), n4 - this.getPaddingBottom());
                }
                Gravity.apply((int)this.u, (int)drawable.getIntrinsicWidth(), (int)drawable.getIntrinsicHeight(), (Rect)rect, (Rect)rect2);
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    public void drawableHotspotChanged(float f3, float f4) {
        super.drawableHotspotChanged(f3, f4);
        Drawable drawable = this.r;
        if (drawable != null) {
            drawable.setHotspot(f3, f4);
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.r;
        if (drawable != null && drawable.isStateful()) {
            this.r.setState(this.getDrawableState());
        }
    }

    public Drawable getForeground() {
        return this.r;
    }

    public int getForegroundGravity() {
        return this.u;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.r;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        this.w = bl | this.w;
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        this.w = true;
    }

    public void setForeground(Drawable drawable) {
        Drawable drawable2 = this.r;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                this.unscheduleDrawable(this.r);
            }
            this.r = drawable;
            this.w = true;
            if (drawable != null) {
                this.setWillNotDraw(false);
                drawable.setCallback((Drawable.Callback)this);
                if (drawable.isStateful()) {
                    drawable.setState(this.getDrawableState());
                }
                if (this.u == 119) {
                    drawable.getPadding(new Rect());
                }
            } else {
                this.setWillNotDraw(true);
            }
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setForegroundGravity(int n3) {
        if (this.u != n3) {
            int n4 = n3;
            if ((0x800007 & n3) == 0) {
                n4 = n3 | 0x800003;
            }
            n3 = n4;
            if ((n4 & 0x70) == 0) {
                n3 = n4 | 0x30;
            }
            this.u = n3;
            if (n3 == 119 && this.r != null) {
                Rect rect = new Rect();
                this.r.getPadding(rect);
            }
            this.requestLayout();
        }
    }

    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.r;
        {
        }
    }
}

