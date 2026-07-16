/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package com.google.android.material.circularreveal.coordinatorlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.circularreveal.b;
import com.google.android.material.circularreveal.c;

public class CircularRevealCoordinatorLayout
extends CoordinatorLayout
implements c {
    public final b B = new b(this);

    public CircularRevealCoordinatorLayout(Context context) {
        this(context, null);
    }

    public CircularRevealCoordinatorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void a() {
        this.B.b();
    }

    @Override
    public void c(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void d() {
        this.B.a();
    }

    public void draw(Canvas canvas) {
        b b3 = this.B;
        if (b3 != null) {
            b3.c(canvas);
            return;
        }
        super.draw(canvas);
    }

    @Override
    public boolean e() {
        return super.isOpaque();
    }

    public Drawable getCircularRevealOverlayDrawable() {
        return this.B.e();
    }

    @Override
    public int getCircularRevealScrimColor() {
        return this.B.f();
    }

    @Override
    public c.e getRevealInfo() {
        return this.B.h();
    }

    public boolean isOpaque() {
        b b3 = this.B;
        if (b3 != null) {
            return b3.j();
        }
        return super.isOpaque();
    }

    @Override
    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.B.k(drawable);
    }

    @Override
    public void setCircularRevealScrimColor(int n3) {
        this.B.l(n3);
    }

    @Override
    public void setRevealInfo(c.e e3) {
        this.B.m(e3);
    }
}

