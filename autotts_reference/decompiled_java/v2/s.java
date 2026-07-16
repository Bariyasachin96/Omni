/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Path
 *  android.graphics.RectF
 *  android.os.Build$VERSION
 *  android.view.View
 */
package v2;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import d2.a;
import v2.o;
import v2.p;
import v2.t;
import v2.u;

public abstract class s {
    public boolean a = false;
    public boolean b = false;
    public o c;
    public RectF d = new RectF();
    public final Path e = new Path();

    public static s a(View view) {
        if (Build.VERSION.SDK_INT >= 33) {
            return new u(view);
        }
        return new t(view);
    }

    public abstract void b(View var1);

    public boolean c() {
        return this.a;
    }

    public final boolean d() {
        RectF rectF = this.d;
        return rectF.left <= rectF.right && rectF.top <= rectF.bottom;
    }

    public void e(Canvas canvas, a.a a4) {
        if (this.j() && !this.e.isEmpty()) {
            canvas.save();
            canvas.clipPath(this.e);
            a4.a(canvas);
            canvas.restore();
            return;
        }
        a4.a(canvas);
    }

    public void f(View view, RectF rectF) {
        this.d = rectF;
        this.k();
        this.b(view);
    }

    public void g(View view, o o3) {
        this.c = o3;
        this.k();
        this.b(view);
    }

    public void h(View view, boolean bl) {
        if (bl != this.a) {
            this.a = bl;
            this.b(view);
        }
    }

    public void i(View view, boolean bl) {
        this.b = bl;
        this.b(view);
    }

    public abstract boolean j();

    public final void k() {
        if (this.d() && this.c != null) {
            p.l().d(this.c, 1.0f, this.d, this.e);
        }
    }
}

