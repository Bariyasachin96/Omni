/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.g;
import androidx.appcompat.widget.k0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.z;
import c.j;
import d.a;
import o0.x0;

public class k {
    public final ImageView a;
    public k0 b;
    public k0 c;
    public k0 d;
    public int e = 0;

    public k(ImageView imageView) {
        this.a = imageView;
    }

    public final boolean a(Drawable drawable) {
        if (this.d == null) {
            this.d = new k0();
        }
        k0 k02 = this.d;
        k02.a();
        ColorStateList colorStateList = androidx.core.widget.g.a(this.a);
        if (colorStateList != null) {
            k02.d = true;
            k02.a = colorStateList;
        }
        if ((colorStateList = androidx.core.widget.g.b(this.a)) != null) {
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
        if (this.a.getDrawable() != null) {
            this.a.getDrawable().setLevel(this.e);
        }
    }

    public void c() {
        Drawable drawable = this.a.getDrawable();
        if (drawable != null) {
            z.b(drawable);
        }
        if (!(drawable == null || this.l() && this.a(drawable))) {
            k0 k02 = this.c;
            if (k02 != null) {
                g.i(drawable, k02, this.a.getDrawableState());
                return;
            }
            k02 = this.b;
            if (k02 != null) {
                g.i(drawable, k02, this.a.getDrawableState());
            }
        }
    }

    public ColorStateList d() {
        k0 k02 = this.c;
        if (k02 != null) {
            return k02.a;
        }
        return null;
    }

    public PorterDuff.Mode e() {
        k0 k02 = this.c;
        if (k02 != null) {
            return k02.b;
        }
        return null;
    }

    public boolean f() {
        return !(this.a.getBackground() instanceof RippleDrawable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void g(AttributeSet object, int n3) {
        Throwable throwable2;
        Object object2;
        block6: {
            block5: {
                object2 = this.a.getContext();
                Object object3 = j.AppCompatImageView;
                object2 = m0.v((Context)object2, object, object3, n3, 0);
                ImageView imageView = this.a;
                x0.f0((View)imageView, imageView.getContext(), object3, object, ((m0)object2).r(), n3, 0);
                try {
                    object3 = this.a.getDrawable();
                    object = object3;
                    if (object3 != null) break block5;
                    n3 = ((m0)object2).n(j.AppCompatImageView_srcCompat, -1);
                    object = object3;
                    if (n3 == -1) break block5;
                    object3 = d.a.b(this.a.getContext(), n3);
                    object = object3;
                    if (object3 == null) break block5;
                    this.a.setImageDrawable((Drawable)object3);
                    object = object3;
                }
                catch (Throwable throwable2) {
                    break block6;
                }
            }
            if (object != null) {
                z.b((Drawable)object);
            }
            if (((m0)object2).s(n3 = j.AppCompatImageView_tint)) {
                androidx.core.widget.g.c(this.a, ((m0)object2).c(n3));
            }
            if (((m0)object2).s(n3 = j.AppCompatImageView_tintMode)) {
                androidx.core.widget.g.d(this.a, z.e(((m0)object2).k(n3, -1), null));
            }
            ((m0)object2).x();
            return;
        }
        ((m0)object2).x();
        throw throwable2;
    }

    public void h(Drawable drawable) {
        this.e = drawable.getLevel();
    }

    public void i(int n3) {
        if (n3 != 0) {
            Drawable drawable = d.a.b(this.a.getContext(), n3);
            if (drawable != null) {
                z.b(drawable);
            }
            this.a.setImageDrawable(drawable);
        } else {
            this.a.setImageDrawable(null);
        }
        this.c();
    }

    public void j(ColorStateList colorStateList) {
        if (this.c == null) {
            this.c = new k0();
        }
        k0 k02 = this.c;
        k02.a = colorStateList;
        k02.d = true;
        this.c();
    }

    public void k(PorterDuff.Mode mode) {
        if (this.c == null) {
            this.c = new k0();
        }
        k0 k02 = this.c;
        k02.b = mode;
        k02.c = true;
        this.c();
    }

    public final boolean l() {
        return this.b != null;
    }
}

