/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ProgressBar
 */
package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import c.a;
import com.google.android.material.internal.z;
import java.util.Arrays;
import n1.b;
import r2.h;
import r2.i;
import r2.j;
import r2.l;
import z1.m;

public abstract class BaseProgressIndicator<S extends r2.b>
extends ProgressBar {
    public static final int r = z1.l.Widget_MaterialComponents_ProgressIndicator;
    public r2.b c;
    public int d;
    public boolean e;
    public boolean f;
    public final int g;
    public final int h;
    public long i = -1L;
    public r2.a j;
    public boolean k = false;
    public int l = 4;
    public boolean m;
    public final Runnable n = new Runnable(this){
        public final BaseProgressIndicator c;
        {
            this.c = baseProgressIndicator;
        }

        @Override
        public void run() {
            this.c.k();
        }
    };
    public final Runnable o = new Runnable(this){
        public final BaseProgressIndicator c;
        {
            this.c = baseProgressIndicator;
        }

        @Override
        public void run() {
            this.c.j();
            BaseProgressIndicator.c(this.c, -1L);
        }
    };
    public final b p = new b(this){
        public final BaseProgressIndicator b;
        {
            this.b = baseProgressIndicator;
        }

        @Override
        public void b(Drawable object) {
            this.b.setIndeterminate(false);
            object = this.b;
            ((BaseProgressIndicator)((Object)object)).setProgressCompat(((BaseProgressIndicator)((Object)object)).d, this.b.e);
        }
    };
    public final b q = new b(this){
        public final BaseProgressIndicator b;
        {
            this.b = baseProgressIndicator;
        }

        @Override
        public void b(Drawable object) {
            super.b((Drawable)object);
            if (!this.b.k) {
                object = this.b;
                object.setVisibility(((BaseProgressIndicator)((Object)object)).l);
            }
        }
    };

    public BaseProgressIndicator(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(y2.a.d(context, attributeSet, n3, r), attributeSet, n3);
        context = this.getContext();
        this.c = this.i(context, attributeSet);
        context = z.i(context, attributeSet, z1.m.BaseProgressIndicator, n3, n4, new int[0]);
        this.g = context.getInt(z1.m.BaseProgressIndicator_showDelay, -1);
        this.h = Math.min(context.getInt(z1.m.BaseProgressIndicator_minHideDelay, -1), 1000);
        context.recycle();
        this.j = new r2.a();
        this.f = true;
    }

    public static /* synthetic */ long c(BaseProgressIndicator baseProgressIndicator, long l3) {
        baseProgressIndicator.i = l3;
        return l3;
    }

    private j getCurrentDrawingDelegate() {
        if (this.isIndeterminate()) {
            if (this.getIndeterminateDrawable() == null) {
                return null;
            }
            return this.getIndeterminateDrawable().y();
        }
        if (this.getProgressDrawable() == null) {
            return null;
        }
        return this.getProgressDrawable().D();
    }

    public Drawable getCurrentDrawable() {
        if (this.isIndeterminate()) {
            return this.getIndeterminateDrawable();
        }
        return this.getProgressDrawable();
    }

    public int getHideAnimationBehavior() {
        return this.c.h;
    }

    public l getIndeterminateDrawable() {
        return (l)super.getIndeterminateDrawable();
    }

    public int[] getIndicatorColor() {
        return this.c.e;
    }

    public int getIndicatorTrackGapSize() {
        return this.c.i;
    }

    public h getProgressDrawable() {
        return (h)super.getProgressDrawable();
    }

    public int getShowAnimationBehavior() {
        return this.c.g;
    }

    public int getTrackColor() {
        return this.c.f;
    }

    public int getTrackCornerRadius() {
        return this.c.b;
    }

    public float getTrackCornerRadiusFraction() {
        return this.c.c;
    }

    public int getTrackThickness() {
        return this.c.a;
    }

    public int getWaveAmplitude() {
        return this.c.l;
    }

    public int getWaveSpeed() {
        return this.c.m;
    }

    public int getWavelengthDeterminate() {
        return this.c.j;
    }

    public int getWavelengthIndeterminate() {
        return this.c.k;
    }

    public void h(boolean bl) {
        if (!this.f) {
            return;
        }
        ((i)this.getCurrentDrawable()).s(this.q(), false, bl);
    }

    public abstract r2.b i(Context var1, AttributeSet var2);

    public void invalidate() {
        super.invalidate();
        if (this.getCurrentDrawable() != null) {
            this.getCurrentDrawable().invalidateSelf();
        }
    }

    public final void j() {
        ((i)this.getCurrentDrawable()).s(false, false, true);
        if (this.m()) {
            this.setVisibility(4);
        }
    }

    public final void k() {
        if (this.h > 0) {
            this.i = SystemClock.uptimeMillis();
        }
        this.setVisibility(0);
    }

    public boolean l() {
        BaseProgressIndicator baseProgressIndicator = this;
        while (baseProgressIndicator.getVisibility() == 0) {
            if ((baseProgressIndicator = baseProgressIndicator.getParent()) == null) {
                return this.getWindowVisibility() == 0;
            }
            if (!(baseProgressIndicator instanceof View)) {
                return true;
            }
            baseProgressIndicator = (View)baseProgressIndicator;
        }
        return false;
    }

    public final boolean m() {
        return !(this.getProgressDrawable() != null && this.getProgressDrawable().isVisible() || this.getIndeterminateDrawable() != null && this.getIndeterminateDrawable().isVisible());
        {
        }
    }

    public final void n() {
        this.o();
        if (this.getProgressDrawable() != null) {
            this.getProgressDrawable().o(this.q);
        }
        if (this.getIndeterminateDrawable() != null) {
            this.getIndeterminateDrawable().o(this.q);
        }
    }

    public void o() {
        if (this.getProgressDrawable() != null && this.getIndeterminateDrawable() != null) {
            this.getIndeterminateDrawable().x().d(this.p);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.n();
        if (this.q()) {
            this.k();
        }
    }

    public void onDetachedFromWindow() {
        this.removeCallbacks(this.o);
        this.removeCallbacks(this.n);
        ((i)this.getCurrentDrawable()).j();
        this.p();
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onDraw(Canvas canvas) {
        synchronized (this) {
            Throwable throwable2;
            block5: {
                int n3;
                block4: {
                    try {
                        n3 = canvas.save();
                        if (this.getPaddingLeft() == 0 && this.getPaddingTop() == 0) break block4;
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                    canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
                }
                if (this.getPaddingRight() != 0 || this.getPaddingBottom() != 0) {
                    canvas.clipRect(0, 0, this.getWidth() - (this.getPaddingLeft() + this.getPaddingRight()), this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom()));
                }
                this.getCurrentDrawable().draw(canvas);
                canvas.restoreToCount(n3);
                return;
            }
            throw throwable2;
        }
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        this.getCurrentDrawingDelegate().g();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onMeasure(int n3, int n4) {
        synchronized (this) {
            Throwable throwable2;
            block5: {
                j j3;
                block4: {
                    try {
                        j3 = this.getCurrentDrawingDelegate();
                        if (j3 != null) break block4;
                    }
                    catch (Throwable throwable2) {}
                    return;
                }
                n3 = j3.f() < 0 ? View.getDefaultSize((int)this.getSuggestedMinimumWidth(), (int)n3) : j3.f() + this.getPaddingLeft() + this.getPaddingRight();
                break block5;
                n4 = j3.e() < 0 ? View.getDefaultSize((int)this.getSuggestedMinimumHeight(), (int)n4) : j3.e() + this.getPaddingTop() + this.getPaddingBottom();
                this.setMeasuredDimension(n3, n4);
                return;
            }
            throw throwable2;
        }
    }

    public void onVisibilityChanged(View view, int n3) {
        super.onVisibilityChanged(view, n3);
        boolean bl = n3 == 0;
        this.h(bl);
    }

    public void onWindowVisibilityChanged(int n3) {
        super.onWindowVisibilityChanged(n3);
        this.h(false);
    }

    public final void p() {
        if (this.getIndeterminateDrawable() != null) {
            this.getIndeterminateDrawable().u(this.q);
            this.getIndeterminateDrawable().x().h();
        }
        if (this.getProgressDrawable() != null) {
            this.getProgressDrawable().u(this.q);
        }
    }

    public boolean q() {
        return this.isAttachedToWindow() && this.getWindowVisibility() == 0 && this.l();
    }

    public void setAnimatorDurationScaleProvider(r2.a a4) {
        this.j = a4;
        if (this.getProgressDrawable() != null) {
            this.getProgressDrawable().e = a4;
        }
        if (this.getIndeterminateDrawable() != null) {
            this.getIndeterminateDrawable().e = a4;
        }
    }

    public void setHideAnimationBehavior(int n3) {
        this.c.h = n3;
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setIndeterminate(boolean bl) {
        synchronized (this) {
            Throwable throwable2;
            block8: {
                i i3;
                block7: {
                    block6: {
                        try {
                            boolean bl2 = this.isIndeterminate();
                            if (bl != bl2) break block6;
                        }
                        catch (Throwable throwable2) {}
                        return;
                    }
                    i3 = (i)this.getCurrentDrawable();
                    if (i3 == null) break block7;
                    i3.j();
                    break block8;
                }
                super.setIndeterminate(bl);
                i3 = (i)this.getCurrentDrawable();
                if (i3 != null) {
                    i3.s(this.q(), false, false);
                }
                if (i3 instanceof l && this.q()) {
                    ((l)i3).x().g();
                }
                this.k = false;
                return;
            }
            throw throwable2;
        }
    }

    public void setIndeterminateAnimatorDurationScale(float f3) {
        r2.b b3 = this.c;
        if (b3.n != f3) {
            b3.n = f3;
            this.getIndeterminateDrawable().x().c();
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        if (drawable instanceof l) {
            ((i)drawable).j();
            super.setIndeterminateDrawable(drawable);
            return;
        }
        if (!this.m) {
            super.setIndeterminateDrawable(drawable);
            return;
        }
        throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
    }

    public void setIndicatorColor(int ... nArray) {
        int[] nArray2 = nArray;
        if (nArray.length == 0) {
            nArray2 = new int[]{h2.a.b(this.getContext(), a.colorPrimary, -1)};
        }
        if (!Arrays.equals(this.getIndicatorColor(), nArray2)) {
            this.c.e = nArray2;
            this.getIndeterminateDrawable().x().c();
            this.invalidate();
        }
    }

    public void setIndicatorTrackGapSize(int n3) {
        r2.b b3 = this.c;
        if (b3.i != n3) {
            b3.i = n3;
            b3.h();
            this.invalidate();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setProgress(int n3) {
        synchronized (this) {
            boolean bl = this.isIndeterminate();
            if (bl) {
                return;
            }
            this.setProgressCompat(n3, false);
            return;
        }
    }

    public void setProgressCompat(int n3, boolean bl) {
        if (this.isIndeterminate()) {
            if (this.getProgressDrawable() != null) {
                this.d = n3;
                this.e = bl;
                this.k = true;
                if (this.getIndeterminateDrawable().isVisible() && this.j.a(this.getContext().getContentResolver()) != 0.0f) {
                    this.getIndeterminateDrawable().x().f();
                    return;
                }
                this.p.b(this.getIndeterminateDrawable());
                return;
            }
        } else {
            super.setProgress(n3);
            if (this.getProgressDrawable() != null && !bl) {
                this.getProgressDrawable().jumpToCurrentState();
            }
        }
    }

    public void setProgressDrawable(Drawable drawable) {
        if (drawable instanceof h) {
            drawable = (h)drawable;
            drawable.j();
            super.setProgressDrawable(drawable);
            drawable.L((float)this.getProgress() / (float)this.getMax());
            return;
        }
        if (!this.m) {
            super.setProgressDrawable(drawable);
            return;
        }
        throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
    }

    public void setShowAnimationBehavior(int n3) {
        this.c.g = n3;
        this.invalidate();
    }

    public void setTrackColor(int n3) {
        r2.b b3 = this.c;
        if (b3.f != n3) {
            b3.f = n3;
            this.invalidate();
        }
    }

    public void setTrackCornerRadius(int n3) {
        r2.b b3 = this.c;
        if (b3.b != n3) {
            b3.b = Math.min(n3, b3.a / 2);
            this.c.d = false;
            this.invalidate();
        }
    }

    public void setTrackCornerRadiusFraction(float f3) {
        r2.b b3 = this.c;
        if (b3.c != f3) {
            b3.c = Math.min(f3, 0.5f);
            this.c.d = true;
            this.invalidate();
        }
    }

    public void setTrackThickness(int n3) {
        r2.b b3 = this.c;
        if (b3.a != n3) {
            b3.a = n3;
            this.requestLayout();
        }
    }

    public void setVisibilityAfterHide(int n3) {
        if (n3 != 0 && n3 != 4 && n3 != 8) {
            throw new IllegalArgumentException("The component's visibility must be one of VISIBLE, INVISIBLE, and GONE defined in View.");
        }
        this.l = n3;
    }

    public void setWaveAmplitude(int n3) {
        r2.b b3 = this.c;
        if (b3.l != n3) {
            b3.l = Math.abs(n3);
            this.requestLayout();
        }
    }

    public void setWaveSpeed(int n3) {
        this.c.m = n3;
        Drawable drawable = this.getProgressDrawable();
        boolean bl = this.c.m != 0;
        drawable.J(bl);
    }

    public void setWavelength(int n3) {
        this.setWavelengthDeterminate(n3);
        this.setWavelengthIndeterminate(n3);
    }

    public void setWavelengthDeterminate(int n3) {
        r2.b b3 = this.c;
        if (b3.j != n3) {
            b3.j = Math.abs(n3);
            if (!this.isIndeterminate()) {
                this.requestLayout();
            }
        }
    }

    public void setWavelengthIndeterminate(int n3) {
        r2.b b3 = this.c;
        if (b3.k != n3) {
            b3.k = Math.abs(n3);
            if (this.isIndeterminate()) {
                this.requestLayout();
            }
        }
    }
}

