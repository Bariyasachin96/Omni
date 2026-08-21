/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import r2.b;
import r2.c;
import r2.d;
import r2.e;
import r2.h;
import r2.k;
import r2.l;

public class CircularProgressIndicator
extends BaseProgressIndicator<CircularProgressIndicatorSpec> {
    public static final int s = z1.l.Widget_MaterialComponents_CircularProgressIndicator;

    public CircularProgressIndicator(Context context) {
        this(context, null);
    }

    public CircularProgressIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicator(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3, s);
        this.s();
        this.m = true;
    }

    public int getIndeterminateAnimationType() {
        return ((CircularProgressIndicatorSpec)this.c).o;
    }

    public int getIndicatorDirection() {
        return ((CircularProgressIndicatorSpec)this.c).r;
    }

    public int getIndicatorInset() {
        return ((CircularProgressIndicatorSpec)this.c).q;
    }

    public int getIndicatorSize() {
        return ((CircularProgressIndicatorSpec)this.c).p;
    }

    public CircularProgressIndicatorSpec r(Context context, AttributeSet attributeSet) {
        return new CircularProgressIndicatorSpec(context, attributeSet);
    }

    public final void s() {
        c c3 = new c((CircularProgressIndicatorSpec)this.c);
        this.setIndeterminateDrawable(r2.l.v(this.getContext(), (CircularProgressIndicatorSpec)this.c, c3));
        this.setProgressDrawable(r2.h.A(this.getContext(), (CircularProgressIndicatorSpec)this.c, c3));
    }

    public void setIndeterminateAnimationType(int n3) {
        if (((CircularProgressIndicatorSpec)this.c).o == n3) {
            return;
        }
        if (this.q() && this.isIndeterminate()) {
            throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
        }
        Object object = this.c;
        ((CircularProgressIndicatorSpec)object).o = n3;
        ((CircularProgressIndicatorSpec)object).h();
        object = n3 == 1 ? new e(this.getContext(), (CircularProgressIndicatorSpec)this.c) : new d((CircularProgressIndicatorSpec)this.c);
        this.getIndeterminateDrawable().A((k)object);
        this.o();
        this.invalidate();
    }

    public void setIndicatorDirection(int n3) {
        ((CircularProgressIndicatorSpec)this.c).r = n3;
        this.invalidate();
    }

    public void setIndicatorInset(int n3) {
        b b3 = this.c;
        if (((CircularProgressIndicatorSpec)b3).q != n3) {
            ((CircularProgressIndicatorSpec)b3).q = n3;
            this.invalidate();
        }
    }

    public void setIndicatorSize(int n3) {
        n3 = Math.max(n3, this.getTrackThickness() * 2);
        b b3 = this.c;
        if (((CircularProgressIndicatorSpec)b3).p != n3) {
            ((CircularProgressIndicatorSpec)b3).p = n3;
            ((CircularProgressIndicatorSpec)b3).h();
            this.requestLayout();
            this.invalidate();
        }
    }

    @Override
    public void setTrackThickness(int n3) {
        super.setTrackThickness(n3);
        ((CircularProgressIndicatorSpec)this.c).h();
    }
}

