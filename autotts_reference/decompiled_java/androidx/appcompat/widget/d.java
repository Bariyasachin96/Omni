/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.g;
import androidx.appcompat.widget.k0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.z;
import c.j;
import o0.x0;

public class d {
    public final View a;
    public final g b;
    public int c = -1;
    public k0 d;
    public k0 e;
    public k0 f;

    public d(View view) {
        this.a = view;
        this.b = g.b();
    }

    public final boolean a(Drawable drawable) {
        if (this.f == null) {
            this.f = new k0();
        }
        k0 k02 = this.f;
        k02.a();
        ColorStateList colorStateList = x0.q(this.a);
        if (colorStateList != null) {
            k02.d = true;
            k02.a = colorStateList;
        }
        if ((colorStateList = x0.r(this.a)) != null) {
            k02.c = true;
            k02.b = colorStateList;
        }
        if (!k02.d && !k02.c) {
            return false;
        }
        g.i(drawable, k02, this.a.getDrawableState());
        return true;
    }

    public void b() {
        Drawable drawable = this.a.getBackground();
        if (!(drawable == null || this.k() && this.a(drawable))) {
            k0 k02 = this.e;
            if (k02 != null) {
                g.i(drawable, k02, this.a.getDrawableState());
                return;
            }
            k02 = this.d;
            if (k02 != null) {
                g.i(drawable, k02, this.a.getDrawableState());
            }
        }
    }

    public ColorStateList c() {
        k0 k02 = this.e;
        if (k02 != null) {
            return k02.a;
        }
        return null;
    }

    public PorterDuff.Mode d() {
        k0 k02 = this.e;
        if (k02 != null) {
            return k02.b;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void e(AttributeSet attributeSet, int n3) {
        Throwable throwable2;
        Object object;
        block5: {
            block4: {
                object = this.a.getContext();
                int[] nArray = j.ViewBackgroundHelper;
                object = m0.v((Context)object, attributeSet, nArray, n3, 0);
                View view = this.a;
                x0.f0(view, view.getContext(), nArray, attributeSet, ((m0)object).r(), n3, 0);
                try {
                    n3 = j.ViewBackgroundHelper_android_background;
                    if (!((m0)object).s(n3)) break block4;
                    this.c = ((m0)object).n(n3, -1);
                    attributeSet = this.b.f(this.a.getContext(), this.c);
                    if (attributeSet == null) break block4;
                    this.h((ColorStateList)attributeSet);
                }
                catch (Throwable throwable2) {
                    break block5;
                }
            }
            if (((m0)object).s(n3 = j.ViewBackgroundHelper_backgroundTint)) {
                x0.l0(this.a, ((m0)object).c(n3));
            }
            if (((m0)object).s(n3 = j.ViewBackgroundHelper_backgroundTintMode)) {
                x0.m0(this.a, z.e(((m0)object).k(n3, -1), null));
            }
            ((m0)object).x();
            return;
        }
        ((m0)object).x();
        throw throwable2;
    }

    public void f(Drawable drawable) {
        this.c = -1;
        this.h(null);
        this.b();
    }

    public void g(int n3) {
        this.c = n3;
        g g3 = this.b;
        g3 = g3 != null ? g3.f(this.a.getContext(), n3) : null;
        this.h((ColorStateList)g3);
        this.b();
    }

    public void h(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new k0();
            }
            k0 k02 = this.d;
            k02.a = colorStateList;
            k02.d = true;
        } else {
            this.d = null;
        }
        this.b();
    }

    public void i(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new k0();
        }
        k0 k02 = this.e;
        k02.a = colorStateList;
        k02.d = true;
        this.b();
    }

    public void j(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new k0();
        }
        k0 k02 = this.e;
        k02.b = mode;
        k02.c = true;
        this.b();
    }

    public final boolean k() {
        return this.d != null;
    }
}

