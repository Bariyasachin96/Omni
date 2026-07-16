/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.widget.FrameLayout
 */
package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ClippableRoundedCornerLayout
extends FrameLayout {
    public Path c;
    public float[] d = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};

    public ClippableRoundedCornerLayout(Context context) {
        super(context);
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    public void a() {
        this.c = null;
        this.d = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.invalidate();
    }

    public void b(float f3, float f4, float f5, float f6, float[] fArray) {
        this.d(new RectF(f3, f4, f5, f6), fArray);
    }

    public void c(Rect rect, float[] fArray) {
        this.b(rect.left, rect.top, rect.right, rect.bottom, fArray);
    }

    public void d(RectF rectF, float[] fArray) {
        if (this.c == null) {
            this.c = new Path();
        }
        this.d = fArray;
        this.c.reset();
        this.c.addRoundRect(rectF, fArray, Path.Direction.CW);
        this.c.close();
        this.invalidate();
    }

    public void dispatchDraw(Canvas canvas) {
        if (this.c == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int n3 = canvas.save();
        canvas.clipPath(this.c);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(n3);
    }

    public void e(float[] fArray) {
        this.b(this.getLeft(), this.getTop(), this.getRight(), this.getBottom(), fArray);
    }

    public float[] getCornerRadii() {
        return this.d;
    }
}

