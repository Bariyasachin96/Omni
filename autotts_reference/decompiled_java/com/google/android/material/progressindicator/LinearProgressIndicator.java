/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import java.util.Objects;
import r2.b;
import r2.h;
import r2.l;
import r2.m;
import r2.n;
import r2.o;
import z1.c;

public class LinearProgressIndicator
extends BaseProgressIndicator<LinearProgressIndicatorSpec> {
    public static final int s = z1.l.Widget_MaterialComponents_LinearProgressIndicator;

    public LinearProgressIndicator(Context context) {
        this(context, null);
    }

    public LinearProgressIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicator(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3, s);
        this.s();
        this.m = true;
    }

    private void s() {
        m m3 = new m((LinearProgressIndicatorSpec)this.c);
        this.setIndeterminateDrawable(r2.l.w(this.getContext(), (LinearProgressIndicatorSpec)this.c, m3));
        this.setProgressDrawable(r2.h.B(this.getContext(), (LinearProgressIndicatorSpec)this.c, m3));
    }

    public int getIndeterminateAnimationType() {
        return ((LinearProgressIndicatorSpec)this.c).o;
    }

    public int getIndicatorDirection() {
        return ((LinearProgressIndicatorSpec)this.c).p;
    }

    public int getTrackInnerCornerRadius() {
        return ((LinearProgressIndicatorSpec)this.c).t;
    }

    public Integer getTrackStopIndicatorPadding() {
        return ((LinearProgressIndicatorSpec)this.c).s;
    }

    public int getTrackStopIndicatorSize() {
        return ((LinearProgressIndicatorSpec)this.c).r;
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        block2: {
            boolean bl2;
            block3: {
                super.onLayout(bl, n3, n4, n5, n6);
                b b3 = this.c;
                LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec)b3;
                n3 = ((LinearProgressIndicatorSpec)b3).p;
                bl = bl2 = true;
                if (n3 == 1) break block2;
                if (this.getLayoutDirection() != 1) break block3;
                bl = bl2;
                if (((LinearProgressIndicatorSpec)this.c).p == 2) break block2;
            }
            bl = this.getLayoutDirection() == 0 && ((LinearProgressIndicatorSpec)this.c).p == 3 ? bl2 : false;
        }
        linearProgressIndicatorSpec.q = bl;
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        n3 -= this.getPaddingLeft() + this.getPaddingRight();
        n4 -= this.getPaddingTop() + this.getPaddingBottom();
        Drawable drawable = this.getIndeterminateDrawable();
        if (drawable != null) {
            drawable.setBounds(0, 0, n3, n4);
        }
        if ((drawable = this.getProgressDrawable()) != null) {
            drawable.setBounds(0, 0, n3, n4);
        }
    }

    public LinearProgressIndicatorSpec r(Context context, AttributeSet attributeSet) {
        return new LinearProgressIndicatorSpec(context, attributeSet);
    }

    public void setIndeterminateAnimationType(int n3) {
        if (((LinearProgressIndicatorSpec)this.c).o == n3) {
            return;
        }
        if (this.q() && this.isIndeterminate()) {
            throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
        }
        b b3 = this.c;
        ((LinearProgressIndicatorSpec)b3).o = n3;
        ((LinearProgressIndicatorSpec)b3).h();
        if (n3 == 0) {
            this.getIndeterminateDrawable().A(new n((LinearProgressIndicatorSpec)this.c));
        } else {
            this.getIndeterminateDrawable().A(new o(this.getContext(), (LinearProgressIndicatorSpec)this.c));
        }
        this.o();
        this.invalidate();
    }

    @Override
    public void setIndicatorColor(int ... nArray) {
        super.setIndicatorColor(nArray);
        ((LinearProgressIndicatorSpec)this.c).h();
    }

    public void setIndicatorDirection(int n3) {
        boolean bl;
        block2: {
            boolean bl2;
            block3: {
                b b3 = this.c;
                ((LinearProgressIndicatorSpec)b3).p = n3;
                b3 = (LinearProgressIndicatorSpec)b3;
                bl = bl2 = true;
                if (n3 == 1) break block2;
                if (this.getLayoutDirection() != 1) break block3;
                bl = bl2;
                if (((LinearProgressIndicatorSpec)this.c).p == 2) break block2;
            }
            bl = this.getLayoutDirection() == 0 && n3 == 3 ? bl2 : false;
        }
        ((LinearProgressIndicatorSpec)b3).q = bl;
        this.invalidate();
    }

    @Override
    public void setProgressCompat(int n3, boolean bl) {
        b b3 = this.c;
        if (b3 != null && ((LinearProgressIndicatorSpec)b3).o == 0 && this.isIndeterminate()) {
            return;
        }
        super.setProgressCompat(n3, bl);
    }

    @Override
    public void setTrackCornerRadius(int n3) {
        super.setTrackCornerRadius(n3);
        ((LinearProgressIndicatorSpec)this.c).h();
        this.invalidate();
    }

    public void setTrackInnerCornerRadius(int n3) {
        b b3 = this.c;
        if (((LinearProgressIndicatorSpec)b3).t != n3) {
            ((LinearProgressIndicatorSpec)b3).t = Math.round(Math.min((float)n3, (float)((LinearProgressIndicatorSpec)b3).a / 2.0f));
            b3 = this.c;
            ((LinearProgressIndicatorSpec)b3).v = false;
            ((LinearProgressIndicatorSpec)b3).w = true;
            ((LinearProgressIndicatorSpec)b3).h();
            this.invalidate();
        }
    }

    public void setTrackInnerCornerRadiusFraction(float f3) {
        b b3 = this.c;
        if (((LinearProgressIndicatorSpec)b3).u != f3) {
            ((LinearProgressIndicatorSpec)b3).u = Math.min(f3, 0.5f);
            b3 = this.c;
            ((LinearProgressIndicatorSpec)b3).v = true;
            ((LinearProgressIndicatorSpec)b3).w = true;
            ((LinearProgressIndicatorSpec)b3).h();
            this.invalidate();
        }
    }

    public void setTrackStopIndicatorPadding(Integer n3) {
        if (!Objects.equals(((LinearProgressIndicatorSpec)this.c).s, n3)) {
            ((LinearProgressIndicatorSpec)this.c).s = n3;
            this.invalidate();
        }
    }

    public void setTrackStopIndicatorSize(int n3) {
        b b3 = this.c;
        if (((LinearProgressIndicatorSpec)b3).r != n3) {
            ((LinearProgressIndicatorSpec)b3).r = Math.min(n3, ((LinearProgressIndicatorSpec)b3).a);
            ((LinearProgressIndicatorSpec)this.c).h();
            this.invalidate();
        }
    }
}

