/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.widget.RelativeLayout
 */
package com.google.android.material.circularreveal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.google.android.material.circularreveal.b;
import com.google.android.material.circularreveal.c;

public class CircularRevealRelativeLayout
extends RelativeLayout
implements c {
    public final b c = new b(this);

    public CircularRevealRelativeLayout(Context context) {
        this(context, null);
    }

    public CircularRevealRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void a() {
        this.c.b();
    }

    @Override
    public void c(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void d() {
        this.c.a();
    }

    public void draw(Canvas canvas) {
        b b3 = this.c;
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
        return this.c.e();
    }

    @Override
    public int getCircularRevealScrimColor() {
        return this.c.f();
    }

    @Override
    public c.e getRevealInfo() {
        return this.c.h();
    }

    public boolean isOpaque() {
        b b3 = this.c;
        if (b3 != null) {
            return b3.j();
        }
        return super.isOpaque();
    }

    @Override
    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.c.k(drawable);
    }

    @Override
    public void setCircularRevealScrimColor(int n3) {
        this.c.l(n3);
    }

    @Override
    public void setRevealInfo(c.e e3) {
        this.c.m(e3);
    }
}

