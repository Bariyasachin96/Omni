/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package com.google.android.material.circularreveal.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.circularreveal.b;
import com.google.android.material.circularreveal.c;

public class CircularRevealCardView
extends MaterialCardView
implements c {
    public final b t = new b(this);

    public CircularRevealCardView(Context context) {
        this(context, null);
    }

    public CircularRevealCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void a() {
        this.t.b();
    }

    @Override
    public void c(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void d() {
        this.t.a();
    }

    public void draw(Canvas canvas) {
        b b3 = this.t;
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
        return this.t.e();
    }

    @Override
    public int getCircularRevealScrimColor() {
        return this.t.f();
    }

    @Override
    public c.e getRevealInfo() {
        return this.t.h();
    }

    public boolean isOpaque() {
        b b3 = this.t;
        if (b3 != null) {
            return b3.j();
        }
        return super.isOpaque();
    }

    @Override
    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.t.k(drawable);
    }

    @Override
    public void setCircularRevealScrimColor(int n3) {
        this.t.l(n3);
    }

    @Override
    public void setRevealInfo(c.e e3) {
        this.t.m(e3);
    }
}

