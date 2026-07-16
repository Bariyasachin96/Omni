/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.view.View
 */
package com.google.android.material.button;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.c0;
import s2.c;
import t2.a;
import v2.i;
import v2.o;
import v2.r;
import v2.w;
import x0.l;
import z1.m;

public class d {
    public final MaterialButton a;
    public o b;
    public w c;
    public l d;
    public i.d e;
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public PorterDuff.Mode l;
    public ColorStateList m;
    public ColorStateList n;
    public ColorStateList o;
    public Drawable p;
    public boolean q = false;
    public boolean r = false;
    public boolean s = false;
    public boolean t;
    public boolean u = true;
    public LayerDrawable v;
    public int w;

    public d(MaterialButton materialButton, o o3) {
        this.a = materialButton;
        this.b = o3;
    }

    public void A(int n3) {
        this.L(this.h, n3);
    }

    public void B(int n3) {
        this.L(n3, this.i);
    }

    public void C(ColorStateList colorStateList) {
        if (this.o != colorStateList) {
            this.o = colorStateList;
            if (this.a.getBackground() instanceof RippleDrawable) {
                ((RippleDrawable)this.a.getBackground()).setColor(t2.a.d(colorStateList));
            }
        }
    }

    public void D(o o3) {
        this.b = o3;
        this.c = null;
        this.N();
    }

    public void E(boolean bl) {
        this.q = bl;
        this.O();
    }

    public void F(w w3) {
        this.c = w3;
        this.N();
    }

    public void G(ColorStateList colorStateList) {
        if (this.n != colorStateList) {
            this.n = colorStateList;
            this.O();
        }
    }

    public void H(int n3) {
        if (this.k != n3) {
            this.k = n3;
            this.O();
        }
    }

    public void I(ColorStateList colorStateList) {
        if (this.m != colorStateList) {
            this.m = colorStateList;
            if (this.g() != null) {
                this.g().setTintList(this.m);
            }
        }
    }

    public void J(PorterDuff.Mode mode) {
        if (this.l != mode) {
            this.l = mode;
            if (this.g() != null && this.l != null) {
                this.g().setTintMode(this.l);
            }
        }
    }

    public void K(boolean bl) {
        this.u = bl;
    }

    public final void L(int n3, int n4) {
        int n5 = this.a.getPaddingStart();
        int n6 = this.a.getPaddingTop();
        int n7 = this.a.getPaddingEnd();
        int n8 = this.a.getPaddingBottom();
        int n9 = this.h;
        int n10 = this.i;
        this.i = n4;
        this.h = n3;
        if (!this.r) {
            this.M();
        }
        this.a.setPaddingRelative(n5, n6 + n3 - n9, n7, n8 + n4 - n10);
    }

    public final void M() {
        this.a.setInternalBackground(this.a());
        i i3 = this.g();
        if (i3 != null) {
            i3.h0(this.w);
            i3.setState(this.a.getDrawableState());
        }
    }

    public final void N() {
        Object object;
        r r3 = this.g();
        if (r3 != null) {
            object = this.c;
            if (object != null) {
                ((i)r3).r0((w)object);
            } else {
                ((i)r3).setShapeAppearanceModel(this.b);
            }
            object = this.d;
            if (object != null) {
                ((i)r3).g0((l)object);
            }
        }
        if ((r3 = this.p()) != null) {
            object = this.c;
            if (object != null) {
                ((i)r3).r0((w)object);
            } else {
                ((i)r3).setShapeAppearanceModel(this.b);
            }
            object = this.d;
            if (object != null) {
                ((i)r3).g0((l)object);
            }
        }
        if ((r3 = this.f()) != null) {
            r3.setShapeAppearanceModel(this.b);
            if (r3 instanceof i) {
                r3 = (i)r3;
                object = this.c;
                if (object != null) {
                    ((i)r3).r0((w)object);
                }
                if ((object = this.d) != null) {
                    ((i)r3).g0((l)object);
                }
            }
        }
    }

    public final void O() {
        i i3 = this.g();
        i i4 = this.p();
        if (i3 != null) {
            i3.t0(this.k, this.n);
            if (i4 != null) {
                float f3 = this.k;
                int n3 = this.q ? h2.a.d((View)this.a, z1.c.colorSurface) : 0;
                i4.s0(f3, n3);
            }
        }
    }

    public final InsetDrawable P(Drawable drawable) {
        return new InsetDrawable(drawable, this.f, this.h, this.g, this.i);
    }

    public final Drawable a() {
        i i3 = new i(this.b);
        Object object = this.c;
        if (object != null) {
            i3.r0((w)object);
        }
        if ((object = this.d) != null) {
            i3.g0((l)object);
        }
        if ((object = this.e) != null) {
            i3.k0((i.d)object);
        }
        i3.W(this.a.getContext());
        i3.setTintList(this.m);
        object = this.l;
        if (object != null) {
            i3.setTintMode((PorterDuff.Mode)object);
        }
        i3.t0(this.k, this.n);
        object = new i(this.b);
        Object object2 = this.c;
        if (object2 != null) {
            ((i)object).r0((w)object2);
        }
        if ((object2 = this.d) != null) {
            ((i)object).g0((l)object2);
        }
        ((i)object).setTint(0);
        float f3 = this.k;
        int n3 = this.q ? h2.a.d((View)this.a, z1.c.colorSurface) : 0;
        ((i)object).s0(f3, n3);
        object2 = new i(this.b);
        this.p = object2;
        w w3 = this.c;
        if (w3 != null) {
            ((i)object2).r0(w3);
        }
        if ((object2 = this.d) != null) {
            ((i)this.p).g0((l)object2);
        }
        this.p.setTint(-1);
        i3 = new RippleDrawable(t2.a.d(this.o), (Drawable)this.P((Drawable)new LayerDrawable(new Drawable[]{object, i3})), this.p);
        this.v = i3;
        return i3;
    }

    public int b() {
        return this.j;
    }

    public l c() {
        return this.d;
    }

    public int d() {
        return this.i;
    }

    public int e() {
        return this.h;
    }

    public r f() {
        LayerDrawable layerDrawable = this.v;
        if (layerDrawable != null && layerDrawable.getNumberOfLayers() > 1) {
            if (this.v.getNumberOfLayers() > 2) {
                return (r)this.v.getDrawable(2);
            }
            return (r)this.v.getDrawable(1);
        }
        return null;
    }

    public i g() {
        return this.h(false);
    }

    public final i h(boolean bl) {
        LayerDrawable layerDrawable = this.v;
        if (layerDrawable != null && layerDrawable.getNumberOfLayers() > 0) {
            return (i)((LayerDrawable)((InsetDrawable)this.v.getDrawable(0)).getDrawable()).getDrawable(bl ^ 1);
        }
        return null;
    }

    public ColorStateList i() {
        return this.o;
    }

    public o j() {
        return this.b;
    }

    public w k() {
        return this.c;
    }

    public ColorStateList l() {
        return this.n;
    }

    public int m() {
        return this.k;
    }

    public ColorStateList n() {
        return this.m;
    }

    public PorterDuff.Mode o() {
        return this.l;
    }

    public final i p() {
        return this.h(true);
    }

    public boolean q() {
        return this.r;
    }

    public boolean r() {
        return this.t;
    }

    public boolean s() {
        return this.u;
    }

    public void t(TypedArray typedArray) {
        this.f = typedArray.getDimensionPixelOffset(z1.m.MaterialButton_android_insetLeft, 0);
        this.g = typedArray.getDimensionPixelOffset(z1.m.MaterialButton_android_insetRight, 0);
        this.h = typedArray.getDimensionPixelOffset(z1.m.MaterialButton_android_insetTop, 0);
        this.i = typedArray.getDimensionPixelOffset(z1.m.MaterialButton_android_insetBottom, 0);
        int n3 = z1.m.MaterialButton_cornerRadius;
        if (typedArray.hasValue(n3)) {
            this.j = n3 = typedArray.getDimensionPixelSize(n3, -1);
            this.D(this.b.x(n3));
            this.s = true;
        }
        this.k = typedArray.getDimensionPixelSize(z1.m.MaterialButton_strokeWidth, 0);
        this.l = c0.n(typedArray.getInt(z1.m.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.m = s2.c.a(this.a.getContext(), typedArray, z1.m.MaterialButton_backgroundTint);
        this.n = s2.c.a(this.a.getContext(), typedArray, z1.m.MaterialButton_strokeColor);
        this.o = s2.c.a(this.a.getContext(), typedArray, z1.m.MaterialButton_rippleColor);
        this.t = typedArray.getBoolean(z1.m.MaterialButton_android_checkable, false);
        this.w = typedArray.getDimensionPixelSize(z1.m.MaterialButton_elevation, 0);
        this.u = typedArray.getBoolean(z1.m.MaterialButton_toggleCheckedStateOnClick, true);
        n3 = this.a.getPaddingStart();
        int n4 = this.a.getPaddingTop();
        int n5 = this.a.getPaddingEnd();
        int n6 = this.a.getPaddingBottom();
        if (typedArray.hasValue(z1.m.MaterialButton_android_background)) {
            this.v();
        } else {
            this.M();
        }
        this.a.setPaddingRelative(n3 + this.f, n4 + this.h, n5 + this.g, n6 + this.i);
    }

    public void u(int n3) {
        if (this.g() != null) {
            this.g().setTint(n3);
        }
    }

    public void v() {
        this.r = true;
        this.a.setSupportBackgroundTintList(this.m);
        this.a.setSupportBackgroundTintMode(this.l);
    }

    public void w(boolean bl) {
        this.t = bl;
    }

    public void x(int n3) {
        if (this.s && this.j == n3) {
            return;
        }
        this.j = n3;
        this.s = true;
        this.D(this.b.x(n3));
    }

    public void y(i.d d3) {
        this.e = d3;
        i i3 = this.g();
        if (i3 != null) {
            i3.k0(d3);
        }
    }

    public void z(l l3) {
        this.d = l3;
        if (this.c != null) {
            this.N();
        }
    }
}

