/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 */
package n2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.google.android.material.loadingindicator.LoadingIndicatorSpec;
import n1.g;
import n2.a;
import n2.c;
import z1.f;

public final class b
extends Drawable
implements Drawable.Callback {
    public r2.a c;
    public final Context d;
    public final LoadingIndicatorSpec e;
    public c f;
    public a g;
    public Paint h;
    public int i;
    public Drawable j;

    public b(Context context, LoadingIndicatorSpec loadingIndicatorSpec, c c3, a a4) {
        this.d = context;
        this.e = loadingIndicatorSpec;
        this.f = c3;
        this.g = a4;
        this.c = new r2.a();
        this.h = new Paint();
        a4.j(this);
        this.setAlpha(255);
    }

    public static b a(Context context, LoadingIndicatorSpec object) {
        object = new b(context, (LoadingIndicatorSpec)object, new c((LoadingIndicatorSpec)object), new a((LoadingIndicatorSpec)object));
        ((b)((Object)object)).e(n1.g.b(context.getResources(), z1.f.ic_mtrl_arrow_circle, null));
        return object;
    }

    public a b() {
        return this.g;
    }

    public c c() {
        return this.f;
    }

    public final boolean d() {
        r2.a a4 = this.c;
        return a4 != null && a4.a(this.d.getContentResolver()) == 0.0f;
    }

    public void draw(Canvas canvas) {
        Rect rect = new Rect();
        Rect rect2 = this.getBounds();
        if (!rect2.isEmpty() && this.isVisible() && canvas.getClipBounds(rect)) {
            if (this.d() && (rect = this.j) != null) {
                rect.setBounds(rect2);
                this.j.setTint(this.e.e[0]);
                this.j.draw(canvas);
                return;
            }
            canvas.save();
            this.f.a(canvas, rect2);
            this.f.b(canvas, this.h, this.e.f, this.getAlpha());
            this.f.c(canvas, this.h, this.g.h, this.getAlpha());
            canvas.restore();
        }
    }

    public void e(Drawable drawable) {
        this.j = drawable;
    }

    public boolean f(boolean bl, boolean bl2, boolean bl3) {
        bl2 = super.setVisible(bl, bl2);
        this.g.e();
        if (bl && bl3 && !this.d()) {
            this.g.n();
        }
        return bl2;
    }

    public int getAlpha() {
        return this.i;
    }

    public int getIntrinsicHeight() {
        return this.f.d();
    }

    public int getIntrinsicWidth() {
        return this.f.e();
    }

    public int getOpacity() {
        return -3;
    }

    public void invalidateDrawable(Drawable drawable) {
        drawable = this.getCallback();
        if (drawable != null) {
            drawable.invalidateDrawable((Drawable)this);
        }
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l3) {
        drawable = this.getCallback();
        if (drawable != null) {
            drawable.scheduleDrawable((Drawable)this, runnable, l3);
        }
    }

    public void setAlpha(int n3) {
        if (this.i != n3) {
            this.i = n3;
            this.invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.h.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        return this.f(bl, bl2, bl);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        drawable = this.getCallback();
        if (drawable != null) {
            drawable.unscheduleDrawable((Drawable)this, runnable);
        }
    }
}

