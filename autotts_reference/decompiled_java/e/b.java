/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Join
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 */
package e;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import c.i;
import c.j;
import h0.a;

public class b
extends Drawable {
    public static final float m = (float)Math.toRadians(45.0);
    public final Paint a;
    public float b;
    public float c;
    public float d;
    public float e;
    public boolean f;
    public final Path g;
    public final int h;
    public boolean i;
    public float j;
    public float k;
    public int l;

    public b(Context context) {
        Paint paint;
        this.a = paint = new Paint();
        this.g = new Path();
        this.i = false;
        this.l = 2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        context = context.getTheme().obtainStyledAttributes(null, c.j.DrawerArrowToggle, c.a.drawerArrowStyle, c.i.Base_Widget_AppCompat_DrawerArrowToggle);
        this.c(context.getColor(c.j.DrawerArrowToggle_color, 0));
        this.b(context.getDimension(c.j.DrawerArrowToggle_thickness, 0.0f));
        this.f(context.getBoolean(c.j.DrawerArrowToggle_spinBars, true));
        this.d(Math.round(context.getDimension(c.j.DrawerArrowToggle_gapBetweenBars, 0.0f)));
        this.h = context.getDimensionPixelSize(c.j.DrawerArrowToggle_drawableSize, 0);
        this.c = Math.round(context.getDimension(c.j.DrawerArrowToggle_barLength, 0.0f));
        this.b = Math.round(context.getDimension(c.j.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.d = context.getDimension(c.j.DrawerArrowToggle_arrowShaftLength, 0.0f);
        context.recycle();
    }

    public static float a(float f3, float f4, float f5) {
        return f3 + (f4 - f3) * f5;
    }

    public void b(float f3) {
        if (this.a.getStrokeWidth() != f3) {
            this.a.setStrokeWidth(f3);
            this.k = (float)((double)(f3 / 2.0f) * Math.cos(m));
            this.invalidateSelf();
        }
    }

    public void c(int n3) {
        if (n3 != this.a.getColor()) {
            this.a.setColor(n3);
            this.invalidateSelf();
        }
    }

    public void d(float f3) {
        if (f3 != this.e) {
            this.e = f3;
            this.invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        int n3;
        Rect rect;
        block6: {
            block7: {
                int n4;
                block8: {
                    rect = this.getBounds();
                    int n5 = this.l;
                    n3 = n4 = 0;
                    if (n5 == 0) break block6;
                    if (n5 == 1) break block7;
                    if (n5 == 3) break block8;
                    n3 = n4;
                    if (h0.a.f(this) != 1) break block6;
                    break block7;
                }
                n3 = n4;
                if (h0.a.f(this) != 0) break block6;
            }
            n3 = 1;
        }
        float f3 = this.b;
        f3 = (float)Math.sqrt(f3 * f3 * 2.0f);
        float f4 = e.b.a(this.c, f3, this.j);
        float f5 = e.b.a(this.c, this.d, this.j);
        float f6 = Math.round(e.b.a(0.0f, this.k, this.j));
        float f7 = e.b.a(0.0f, m, this.j);
        f3 = n3 != 0 ? 0.0f : -180.0f;
        float f8 = n3 != 0 ? 180.0f : 0.0f;
        f3 = e.b.a(f3, f8, this.j);
        double d3 = f4;
        double d4 = f7;
        f4 = Math.round(Math.cos(d4) * d3);
        float f9 = Math.round(d3 * Math.sin(d4));
        this.g.rewind();
        f8 = e.b.a(this.e + this.a.getStrokeWidth(), -this.k, this.j);
        f7 = -f5 / 2.0f;
        this.g.moveTo(f7 + f6, 0.0f);
        this.g.rLineTo(f5 - f6 * 2.0f, 0.0f);
        this.g.moveTo(f7, f8);
        this.g.rLineTo(f4, f9);
        this.g.moveTo(f7, -f8);
        this.g.rLineTo(f4, -f9);
        this.g.close();
        canvas.save();
        f8 = this.a.getStrokeWidth();
        f5 = rect.height();
        f6 = this.e;
        f5 = (int)(f5 - 3.0f * f8 - f6 * 2.0f) / 4 * 2;
        canvas.translate((float)rect.centerX(), f5 + (f8 * 1.5f + f6));
        if (this.f) {
            n3 = this.i ^ n3 ? -1 : 1;
            canvas.rotate(f3 * (float)n3);
        } else if (n3 != 0) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(this.g, this.a);
        canvas.restore();
    }

    public void e(float f3) {
        if (this.j != f3) {
            this.j = f3;
            this.invalidateSelf();
        }
    }

    public void f(boolean bl) {
        if (this.f != bl) {
            this.f = bl;
            this.invalidateSelf();
        }
    }

    public int getIntrinsicHeight() {
        return this.h;
    }

    public int getIntrinsicWidth() {
        return this.h;
    }

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int n3) {
        if (n3 != this.a.getAlpha()) {
            this.a.setAlpha(n3);
            this.invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        this.invalidateSelf();
    }
}

