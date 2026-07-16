/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ProgressBar
 *  android.widget.SeekBar
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import androidx.appcompat.widget.l;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.z;
import c.j;
import h0.a;
import o0.x0;

public class n
extends l {
    public final SeekBar d;
    public Drawable e;
    public ColorStateList f = null;
    public PorterDuff.Mode g = null;
    public boolean h = false;
    public boolean i = false;

    public n(SeekBar seekBar) {
        super((ProgressBar)seekBar);
        this.d = seekBar;
    }

    @Override
    public void c(AttributeSet attributeSet, int n3) {
        super.c(attributeSet, n3);
        Object object = this.d.getContext();
        int[] nArray = j.AppCompatSeekBar;
        object = m0.v((Context)object, attributeSet, nArray, n3, 0);
        SeekBar seekBar = this.d;
        x0.f0((View)seekBar, seekBar.getContext(), nArray, attributeSet, ((m0)object).r(), n3, 0);
        attributeSet = ((m0)object).h(j.AppCompatSeekBar_android_thumb);
        if (attributeSet != null) {
            this.d.setThumb((Drawable)attributeSet);
        }
        this.j(((m0)object).g(j.AppCompatSeekBar_tickMark));
        n3 = j.AppCompatSeekBar_tickMarkTintMode;
        if (((m0)object).s(n3)) {
            this.g = z.e(((m0)object).k(n3, -1), this.g);
            this.i = true;
        }
        if (((m0)object).s(n3 = j.AppCompatSeekBar_tickMarkTint)) {
            this.f = ((m0)object).c(n3);
            this.h = true;
        }
        ((m0)object).x();
        this.f();
    }

    public final void f() {
        Drawable drawable = this.e;
        if (drawable != null && (this.h || this.i)) {
            this.e = drawable = h0.a.r(drawable.mutate());
            if (this.h) {
                h0.a.o(drawable, this.f);
            }
            if (this.i) {
                h0.a.p(this.e, this.g);
            }
            if (this.e.isStateful()) {
                this.e.setState(this.d.getDrawableState());
            }
        }
    }

    public void g(Canvas canvas) {
        if (this.e != null) {
            int n3 = this.d.getMax();
            int n4 = 1;
            if (n3 > 1) {
                int n5 = this.e.getIntrinsicWidth();
                int n6 = this.e.getIntrinsicHeight();
                n5 = n5 >= 0 ? (n5 /= 2) : 1;
                if (n6 >= 0) {
                    n4 = n6 / 2;
                }
                this.e.setBounds(-n5, -n4, n5, n4);
                float f3 = (float)(this.d.getWidth() - this.d.getPaddingLeft() - this.d.getPaddingRight()) / (float)n3;
                n4 = canvas.save();
                canvas.translate((float)this.d.getPaddingLeft(), (float)(this.d.getHeight() / 2));
                for (n5 = 0; n5 <= n3; ++n5) {
                    this.e.draw(canvas);
                    canvas.translate(f3, 0.0f);
                }
                canvas.restoreToCount(n4);
            }
        }
    }

    public void h() {
        Drawable drawable = this.e;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.d.getDrawableState())) {
            this.d.invalidateDrawable(drawable);
        }
    }

    public void i() {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    public void j(Drawable drawable) {
        Drawable drawable2 = this.e;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.e = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this.d);
            h0.a.m(drawable, this.d.getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(this.d.getDrawableState());
            }
            this.f();
        }
        this.d.invalidate();
    }
}

