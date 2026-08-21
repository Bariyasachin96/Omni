/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.drawable.Drawable
 */
package com.google.android.material.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import com.google.android.material.internal.h;

public class f
extends Drawable {
    public final Drawable a;
    public final Drawable b;
    public final float[] c;
    public float d;

    public f(Drawable drawable, Drawable drawable2) {
        Object object = drawable != null ? drawable.getConstantState().newDrawable().mutate() : new b(null);
        this.a = object;
        Object object2 = drawable2 != null ? drawable2.getConstantState().newDrawable().mutate() : new b(null);
        this.b = object2;
        int n3 = 3;
        int n4 = drawable != null ? h0.a.f(drawable) : 3;
        if (drawable2 != null) {
            n3 = h0.a.f(drawable2);
        }
        h0.a.m(object, n4);
        h0.a.m(object2, n3);
        object2.setAlpha(0);
        this.c = new float[2];
    }

    public void a(float f3) {
        if (this.d != f3) {
            this.d = f3;
            h.a(f3, this.c);
            this.a.setAlpha((int)(this.c[0] * 255.0f));
            this.b.setAlpha((int)(this.c[1] * 255.0f));
            this.invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        this.a.draw(canvas);
        this.b.draw(canvas);
    }

    public int getIntrinsicHeight() {
        return Math.max(this.a.getIntrinsicHeight(), this.b.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.a.getIntrinsicWidth(), this.b.getIntrinsicWidth());
    }

    public int getMinimumHeight() {
        return Math.max(this.a.getMinimumHeight(), this.b.getMinimumHeight());
    }

    public int getMinimumWidth() {
        return Math.max(this.a.getMinimumWidth(), this.b.getMinimumWidth());
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isStateful() {
        return this.a.isStateful() || this.b.isStateful();
        {
        }
    }

    public void setAlpha(int n3) {
        if (this.d <= 0.5f) {
            this.a.setAlpha(n3);
            this.b.setAlpha(0);
        } else {
            this.a.setAlpha(0);
            this.b.setAlpha(n3);
        }
        this.invalidateSelf();
    }

    public void setBounds(int n3, int n4, int n5, int n6) {
        super.setBounds(n3, n4, n5, n6);
        this.a.setBounds(n3, n4, n5, n6);
        this.b.setBounds(n3, n4, n5, n6);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        this.b.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    public boolean setState(int[] nArray) {
        boolean bl = this.a.setState(nArray);
        boolean bl2 = this.b.setState(nArray);
        return bl || bl2;
        {
        }
    }

    public static class b
    extends Drawable {
        public b() {
        }

        public /* synthetic */ b(a a4) {
            this();
        }

        public void draw(Canvas canvas) {
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int n3) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }
}

