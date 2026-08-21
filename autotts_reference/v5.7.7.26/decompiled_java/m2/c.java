/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.LinearGradient
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package m2;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import j2.d;
import v2.o;
import v2.p;

public class c
extends Drawable {
    public final p a = v2.p.l();
    public final Paint b;
    public final Path c = new Path();
    public final Rect d = new Rect();
    public final RectF e = new RectF();
    public final RectF f = new RectF();
    public final b g = new b(this, null);
    public float h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public boolean n = true;
    public o o;
    public ColorStateList p;

    public c(o o3) {
        this.o = o3;
        o3 = new Paint(1);
        this.b = o3;
        o3.setStyle(Paint.Style.STROKE);
    }

    public final Shader a() {
        Rect rect = this.d;
        this.copyBounds(rect);
        float f3 = this.h / (float)rect.height();
        int n3 = g0.a.g(this.i, this.m);
        int n4 = g0.a.g(this.j, this.m);
        int n5 = g0.a.g(g0.a.k(this.j, 0), this.m);
        int n6 = g0.a.g(g0.a.k(this.l, 0), this.m);
        int n7 = g0.a.g(this.l, this.m);
        int n8 = g0.a.g(this.k, this.m);
        float f4 = rect.top;
        float f5 = rect.bottom;
        rect = Shader.TileMode.CLAMP;
        return new LinearGradient(0.0f, f4, 0.0f, f5, new int[]{n3, n4, n5, n6, n7, n8}, new float[]{0.0f, f3, 0.5f, 0.5f, 1.0f - f3, 1.0f}, (Shader.TileMode)rect);
    }

    public RectF b() {
        this.f.set(this.getBounds());
        return this.f;
    }

    public void c(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.m = colorStateList.getColorForState(this.getState(), this.m);
        }
        this.p = colorStateList;
        this.n = true;
        this.invalidateSelf();
    }

    public void d(float f3) {
        if (this.h != f3) {
            this.h = f3;
            this.b.setStrokeWidth(f3 * 1.3333f);
            this.n = true;
            this.invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        if (this.n) {
            this.b.setShader(this.a());
            this.n = false;
        }
        float f3 = this.b.getStrokeWidth() / 2.0f;
        this.copyBounds(this.d);
        this.e.set(this.d);
        float f4 = Math.min(this.o.r().a(this.b()), this.e.width() / 2.0f);
        if (this.o.v(this.b())) {
            this.e.inset(f3, f3);
            canvas.drawRoundRect(this.e, f4, f4, this.b);
        }
    }

    public void e(int n3, int n4, int n5, int n6) {
        this.i = n3;
        this.j = n4;
        this.k = n5;
        this.l = n6;
    }

    public void f(o o3) {
        this.o = o3;
        this.invalidateSelf();
    }

    public Drawable.ConstantState getConstantState() {
        return this.g;
    }

    public int getOpacity() {
        if (this.h > 0.0f) {
            return -3;
        }
        return -2;
    }

    public void getOutline(Outline outline) {
        if (this.o.v(this.b())) {
            float f3 = this.o.r().a(this.b());
            outline.setRoundRect(this.getBounds(), f3);
            return;
        }
        this.copyBounds(this.d);
        this.e.set(this.d);
        this.a.d(this.o, 1.0f, this.e, this.c);
        j2.d.l(outline, this.c);
    }

    public boolean getPadding(Rect rect) {
        if (this.o.v(this.b())) {
            int n3 = Math.round(this.h);
            rect.set(n3, n3, n3, n3);
        }
        return true;
    }

    public boolean isStateful() {
        ColorStateList colorStateList = this.p;
        return colorStateList != null && colorStateList.isStateful() || super.isStateful();
    }

    public void onBoundsChange(Rect rect) {
        this.n = true;
    }

    public boolean onStateChange(int[] nArray) {
        int n3;
        ColorStateList colorStateList = this.p;
        if (colorStateList != null && (n3 = colorStateList.getColorForState(nArray, this.m)) != this.m) {
            this.n = true;
            this.m = n3;
        }
        if (this.n) {
            this.invalidateSelf();
        }
        return this.n;
    }

    public void setAlpha(int n3) {
        this.b.setAlpha(n3);
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.b.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    public class b
    extends Drawable.ConstantState {
        public final c a;

        public b(c c3) {
            this.a = c3;
        }

        public /* synthetic */ b(c c3, a a4) {
            this(c3);
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            return this.a;
        }
    }
}

