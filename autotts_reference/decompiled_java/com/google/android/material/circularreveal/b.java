/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package com.google.android.material.circularreveal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.circularreveal.c;

public class b {
    public final a a;
    public final View b;
    public final Path c;
    public final Paint d;
    public final Paint e;
    public c.e f;
    public Drawable g;
    public boolean h;

    public b(a a4) {
        this.a = a4;
        a4 = (View)a4;
        this.b = a4;
        a4.setWillNotDraw(false);
        this.c = new Path();
        this.d = new Paint(7);
        a4 = new Paint(1);
        this.e = a4;
        a4.setColor(0);
    }

    public void a() {
    }

    public void b() {
    }

    public void c(Canvas canvas) {
        if (this.n()) {
            this.a.c(canvas);
            if (this.p()) {
                canvas.drawRect(0.0f, 0.0f, (float)this.b.getWidth(), (float)this.b.getHeight(), this.e);
            }
        } else {
            this.a.c(canvas);
            if (this.p()) {
                canvas.drawRect(0.0f, 0.0f, (float)this.b.getWidth(), (float)this.b.getHeight(), this.e);
            }
        }
        this.d(canvas);
    }

    public final void d(Canvas canvas) {
        if (this.o()) {
            Rect rect = this.g.getBounds();
            float f3 = this.f.a - (float)rect.width() / 2.0f;
            float f4 = this.f.b - (float)rect.height() / 2.0f;
            canvas.translate(f3, f4);
            this.g.draw(canvas);
            canvas.translate(-f3, -f4);
        }
    }

    public Drawable e() {
        return this.g;
    }

    public int f() {
        return this.e.getColor();
    }

    public final float g(c.e e3) {
        return o2.a.c(e3.a, e3.b, 0.0f, 0.0f, this.b.getWidth(), this.b.getHeight());
    }

    public c.e h() {
        c.e e3 = this.f;
        if (e3 == null) {
            return null;
        }
        if ((e3 = new c.e(e3)).a()) {
            e3.c = this.g(e3);
        }
        return e3;
    }

    public final void i() {
        this.b.invalidate();
    }

    public boolean j() {
        return this.a.e() && !this.n();
    }

    public void k(Drawable drawable) {
        this.g = drawable;
        this.b.invalidate();
    }

    public void l(int n3) {
        this.e.setColor(n3);
        this.b.invalidate();
    }

    public void m(c.e e3) {
        if (e3 == null) {
            this.f = null;
        } else {
            c.e e4 = this.f;
            if (e4 == null) {
                this.f = new c.e(e3);
            } else {
                e4.c(e3);
            }
            if (o2.a.e(e3.c, this.g(e3), 1.0E-4f)) {
                this.f.c = Float.MAX_VALUE;
            }
        }
        this.i();
    }

    public final boolean n() {
        c.e e3 = this.f;
        boolean bl = e3 == null || e3.a();
        return bl ^ true;
    }

    public final boolean o() {
        return !this.h && this.g != null && this.f != null;
    }

    public final boolean p() {
        return !this.h && Color.alpha((int)this.e.getColor()) != 0;
    }

    public static interface a {
        public void c(Canvas var1);

        public boolean e();
    }
}

