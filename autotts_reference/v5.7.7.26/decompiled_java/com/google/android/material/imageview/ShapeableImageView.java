/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Xfermode
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewOutlineProvider
 */
package com.google.android.material.imageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.widget.AppCompatImageView;
import s2.c;
import v2.i;
import v2.o;
import v2.p;
import v2.r;
import z1.l;
import z1.m;

public class ShapeableImageView
extends AppCompatImageView
implements r {
    public static final int x = z1.l.Widget_MaterialComponents_ShapeableImageView;
    public final p f;
    public final RectF g;
    public final RectF h;
    public final Paint i;
    public final Paint j;
    public final Path k;
    public ColorStateList l;
    public i m;
    public o n;
    public float o;
    public Path p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public boolean w;

    public ShapeableImageView(Context context) {
        this(context, null, 0);
    }

    public ShapeableImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShapeableImageView(Context context, AttributeSet attributeSet, int n3) {
        int n4;
        Paint paint;
        int n5 = x;
        super(y2.a.d(context, attributeSet, n3, n5), attributeSet, n3);
        this.f = v2.p.l();
        this.k = new Path();
        this.w = false;
        context = this.getContext();
        this.j = paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.g = new RectF();
        this.h = new RectF();
        this.p = new Path();
        paint = context.obtainStyledAttributes(attributeSet, z1.m.ShapeableImageView, n3, n5);
        this.setLayerType(2, null);
        this.l = s2.c.a(context, (TypedArray)paint, z1.m.ShapeableImageView_strokeColor);
        this.o = paint.getDimensionPixelSize(z1.m.ShapeableImageView_strokeWidth, 0);
        this.q = n4 = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPadding, 0);
        this.r = n4;
        this.s = n4;
        this.t = n4;
        this.q = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPaddingLeft, n4);
        this.r = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPaddingTop, n4);
        this.s = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPaddingRight, n4);
        this.t = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPaddingBottom, n4);
        this.u = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPaddingStart, Integer.MIN_VALUE);
        this.v = paint.getDimensionPixelSize(z1.m.ShapeableImageView_contentPaddingEnd, Integer.MIN_VALUE);
        paint.recycle();
        this.i = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        this.n = v2.o.e(context, attributeSet, n3, n5).m();
        this.setOutlineProvider(new a(this));
    }

    public static /* synthetic */ i e(ShapeableImageView shapeableImageView, i i3) {
        shapeableImageView.m = i3;
        return i3;
    }

    private boolean i() {
        return this.getLayoutDirection() == 1;
    }

    public final void g(Canvas canvas) {
        if (this.l != null) {
            this.i.setStrokeWidth(this.o);
            int n3 = this.l.getColorForState(this.getDrawableState(), this.l.getDefaultColor());
            if (this.o > 0.0f && n3 != 0) {
                this.i.setColor(n3);
                canvas.drawPath(this.k, this.i);
            }
        }
    }

    public int getContentPaddingBottom() {
        return this.t;
    }

    public final int getContentPaddingEnd() {
        int n3 = this.v;
        if (n3 != Integer.MIN_VALUE) {
            return n3;
        }
        if (this.i()) {
            return this.q;
        }
        return this.s;
    }

    public int getContentPaddingLeft() {
        if (this.h()) {
            int n3;
            if (this.i() && (n3 = this.v) != Integer.MIN_VALUE) {
                return n3;
            }
            if (!this.i() && (n3 = this.u) != Integer.MIN_VALUE) {
                return n3;
            }
        }
        return this.q;
    }

    public int getContentPaddingRight() {
        if (this.h()) {
            int n3;
            if (this.i() && (n3 = this.u) != Integer.MIN_VALUE) {
                return n3;
            }
            if (!this.i() && (n3 = this.v) != Integer.MIN_VALUE) {
                return n3;
            }
        }
        return this.s;
    }

    public final int getContentPaddingStart() {
        int n3 = this.u;
        if (n3 != Integer.MIN_VALUE) {
            return n3;
        }
        if (this.i()) {
            return this.s;
        }
        return this.q;
    }

    public int getContentPaddingTop() {
        return this.r;
    }

    public int getPaddingBottom() {
        return super.getPaddingBottom() - this.getContentPaddingBottom();
    }

    public int getPaddingEnd() {
        return super.getPaddingEnd() - this.getContentPaddingEnd();
    }

    public int getPaddingLeft() {
        return super.getPaddingLeft() - this.getContentPaddingLeft();
    }

    public int getPaddingRight() {
        return super.getPaddingRight() - this.getContentPaddingRight();
    }

    public int getPaddingStart() {
        return super.getPaddingStart() - this.getContentPaddingStart();
    }

    public int getPaddingTop() {
        return super.getPaddingTop() - this.getContentPaddingTop();
    }

    public o getShapeAppearanceModel() {
        return this.n;
    }

    public ColorStateList getStrokeColor() {
        return this.l;
    }

    public float getStrokeWidth() {
        return this.o;
    }

    public final boolean h() {
        return this.u != Integer.MIN_VALUE || this.v != Integer.MIN_VALUE;
        {
        }
    }

    public final void j(int n3, int n4) {
        this.g.set((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(n3 - this.getPaddingRight()), (float)(n4 - this.getPaddingBottom()));
        this.f.d(this.n, 1.0f, this.g, this.k);
        this.p.rewind();
        this.p.addPath(this.k);
        this.h.set(0.0f, 0.0f, (float)n3, (float)n4);
        this.p.addRect(this.h, Path.Direction.CCW);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.p, this.j);
        this.g(canvas);
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (this.w || !this.isLayoutDirectionResolved()) {
            return;
        }
        this.w = true;
        if (!this.isPaddingRelative() && !this.h()) {
            this.setPadding(super.getPaddingLeft(), super.getPaddingTop(), super.getPaddingRight(), super.getPaddingBottom());
            return;
        }
        this.setPaddingRelative(super.getPaddingStart(), super.getPaddingTop(), super.getPaddingEnd(), super.getPaddingBottom());
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        this.j(n3, n4);
    }

    public void setContentPadding(int n3, int n4, int n5, int n6) {
        this.u = Integer.MIN_VALUE;
        this.v = Integer.MIN_VALUE;
        super.setPadding(super.getPaddingLeft() - this.q + n3, super.getPaddingTop() - this.r + n4, super.getPaddingRight() - this.s + n5, super.getPaddingBottom() - this.t + n6);
        this.q = n3;
        this.r = n4;
        this.s = n5;
        this.t = n6;
    }

    public void setContentPaddingRelative(int n3, int n4, int n5, int n6) {
        super.setPaddingRelative(super.getPaddingStart() - this.getContentPaddingStart() + n3, super.getPaddingTop() - this.r + n4, super.getPaddingEnd() - this.getContentPaddingEnd() + n5, super.getPaddingBottom() - this.t + n6);
        int n7 = this.i() ? n5 : n3;
        this.q = n7;
        this.r = n4;
        if (!this.i()) {
            n3 = n5;
        }
        this.s = n3;
        this.t = n6;
    }

    public void setPadding(int n3, int n4, int n5, int n6) {
        super.setPadding(n3 + this.getContentPaddingLeft(), n4 + this.getContentPaddingTop(), n5 + this.getContentPaddingRight(), n6 + this.getContentPaddingBottom());
    }

    public void setPaddingRelative(int n3, int n4, int n5, int n6) {
        super.setPaddingRelative(n3 + this.getContentPaddingStart(), n4 + this.getContentPaddingTop(), n5 + this.getContentPaddingEnd(), n6 + this.getContentPaddingBottom());
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        this.n = o3;
        i i3 = this.m;
        if (i3 != null) {
            i3.setShapeAppearanceModel(o3);
        }
        this.j(this.getWidth(), this.getHeight());
        this.invalidate();
        this.invalidateOutline();
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        this.l = colorStateList;
        this.invalidate();
    }

    public void setStrokeColorResource(int n3) {
        this.setStrokeColor(d.a.a(this.getContext(), n3));
    }

    public void setStrokeWidth(float f3) {
        if (this.o != f3) {
            this.o = f3;
            this.invalidate();
        }
    }

    public void setStrokeWidthResource(int n3) {
        this.setStrokeWidth(this.getResources().getDimensionPixelSize(n3));
    }

    public class a
    extends ViewOutlineProvider {
        public final Rect a;
        public final ShapeableImageView b;

        public a(ShapeableImageView shapeableImageView) {
            this.b = shapeableImageView;
            this.a = new Rect();
        }

        public void getOutline(View view, Outline outline) {
            if (this.b.n == null) {
                return;
            }
            if (this.b.m == null) {
                ShapeableImageView.e(this.b, new i(this.b.n));
            }
            this.b.g.round(this.a);
            this.b.m.setBounds(this.a);
            this.b.m.getOutline(outline);
        }
    }
}

