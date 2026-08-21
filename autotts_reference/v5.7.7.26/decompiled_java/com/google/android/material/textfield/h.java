/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 */
package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import v2.i;
import v2.o;

public abstract class h
extends i {
    public b L;

    public h(b b3) {
        super(b3);
        this.L = b3;
    }

    public /* synthetic */ h(b b3, a a4) {
        this(b3);
    }

    public static h D0(b b3) {
        return new c(b3);
    }

    public static h E0(o o3) {
        if (o3 == null) {
            o3 = new o();
        }
        return com.google.android.material.textfield.h.D0(new b(o3, new RectF(), null));
    }

    public boolean F0() {
        return this.L.x.isEmpty() ^ true;
    }

    public void G0() {
        this.H0(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void H0(float f3, float f4, float f5, float f6) {
        if (f3 == ((b)this.L).x.left && f4 == ((b)this.L).x.top && f5 == ((b)this.L).x.right && f6 == ((b)this.L).x.bottom) {
            return;
        }
        this.L.x.set(f3, f4, f5, f6);
        this.invalidateSelf();
    }

    public void I0(RectF rectF) {
        this.H0(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    @Override
    public Drawable mutate() {
        this.L = new b(this.L, null);
        return this;
    }

    public static final class b
    extends i.c {
        public final RectF x;

        public b(b b3) {
            super(b3);
            this.x = b3.x;
        }

        public /* synthetic */ b(b b3, a a4) {
            this(b3);
        }

        public b(o o3, RectF rectF) {
            super(o3, null);
            this.x = rectF;
        }

        public /* synthetic */ b(o o3, RectF rectF, a a4) {
            this(o3, rectF);
        }

        @Override
        public Drawable newDrawable() {
            h h3 = com.google.android.material.textfield.h.D0(this);
            h3.invalidateSelf();
            return h3;
        }
    }

    public static class c
    extends h {
        public c(b b3) {
            super(b3, null);
        }

        @Override
        public void w(Canvas canvas) {
            if (this.L.x.isEmpty()) {
                super.w(canvas);
                return;
            }
            canvas.save();
            canvas.clipOutRect(this.L.x);
            super.w(canvas);
            canvas.restore();
        }
    }
}

