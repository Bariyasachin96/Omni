/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 */
package e2;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.card.MaterialCardView;
import e2.a;
import m.c;
import m.d;
import p2.k;
import v2.e;
import v2.f;
import v2.i;
import v2.n;
import v2.o;
import z1.g;
import z1.m;

public class b {
    public static final double y = Math.cos(Math.toRadians(45.0));
    public static final Drawable z;
    public final MaterialCardView a;
    public final Rect b = new Rect();
    public final i c;
    public final i d;
    public int e;
    public int f;
    public int g;
    public int h;
    public Drawable i;
    public Drawable j;
    public ColorStateList k;
    public ColorStateList l;
    public o m;
    public ColorStateList n;
    public Drawable o;
    public LayerDrawable p;
    public i q;
    public boolean r = false;
    public boolean s;
    public ValueAnimator t;
    public final TimeInterpolator u;
    public final int v;
    public final int w;
    public float x = 0.0f;

    static {
        ColorDrawable colorDrawable = Build.VERSION.SDK_INT <= 28 ? new ColorDrawable() : null;
        z = colorDrawable;
    }

    public b(MaterialCardView materialCardView, AttributeSet attributeSet, int n3, int n4) {
        this.a = materialCardView;
        Object object = new i(materialCardView.getContext(), attributeSet, n3, n4);
        this.c = object;
        ((i)object).W(materialCardView.getContext());
        ((i)object).p0(-12303292);
        object = ((i)object).K().w();
        attributeSet = materialCardView.getContext().obtainStyledAttributes(attributeSet, m.d.CardView, n3, m.c.CardView);
        n3 = m.d.CardView_cardCornerRadius;
        if (attributeSet.hasValue(n3)) {
            ((o.b)object).o(attributeSet.getDimension(n3, 0.0f));
        }
        this.d = new i();
        this.W(((o.b)object).m());
        this.u = p2.k.g(materialCardView.getContext(), z1.c.motionEasingLinearInterpolator, a2.a.a);
        this.v = p2.k.f(materialCardView.getContext(), z1.c.motionDurationShort2, 300);
        this.w = p2.k.f(materialCardView.getContext(), z1.c.motionDurationShort1, 300);
        attributeSet.recycle();
    }

    public static /* synthetic */ void a(b b3, ValueAnimator valueAnimator) {
        b3.getClass();
        float f3 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        int n3 = (int)(255.0f * f3);
        b3.j.setAlpha(n3);
        b3.x = f3;
    }

    public Rect A() {
        return this.b;
    }

    public final Drawable B(Drawable drawable) {
        int n3;
        int n4;
        if (this.a.getUseCompatPadding()) {
            n4 = (int)Math.ceil(this.f());
            n3 = (int)Math.ceil(this.e());
        } else {
            n3 = 0;
            n4 = 0;
        }
        return new InsetDrawable(this, drawable, n3, n4, n3, n4){
            public final b c;
            {
                this.c = b3;
                super(drawable, n3, n4, n5, n6);
            }

            public int getMinimumHeight() {
                return -1;
            }

            public int getMinimumWidth() {
                return -1;
            }

            public boolean getPadding(Rect rect) {
                return false;
            }
        };
    }

    public boolean C() {
        return this.r;
    }

    public boolean D() {
        return this.s;
    }

    public final boolean E() {
        return (this.g & 0x50) == 80;
    }

    public final boolean F() {
        return (this.g & 0x800005) == 0x800005;
    }

    public void G(TypedArray object) {
        boolean bl;
        ColorStateList colorStateList;
        this.n = colorStateList = s2.c.a(this.a.getContext(), object, z1.m.MaterialCardView_strokeColor);
        if (colorStateList == null) {
            this.n = ColorStateList.valueOf((int)-1);
        }
        this.h = object.getDimensionPixelSize(z1.m.MaterialCardView_strokeWidth, 0);
        this.s = bl = object.getBoolean(z1.m.MaterialCardView_android_checkable, false);
        this.a.setLongClickable(bl);
        this.l = s2.c.a(this.a.getContext(), object, z1.m.MaterialCardView_checkedIconTint);
        this.O(s2.c.e(this.a.getContext(), object, z1.m.MaterialCardView_checkedIcon));
        this.R(object.getDimensionPixelSize(z1.m.MaterialCardView_checkedIconSize, 0));
        this.Q(object.getDimensionPixelSize(z1.m.MaterialCardView_checkedIconMargin, 0));
        this.g = object.getInteger(z1.m.MaterialCardView_checkedIconGravity, 8388661);
        this.k = colorStateList = s2.c.a(this.a.getContext(), object, z1.m.MaterialCardView_rippleColor);
        if (colorStateList == null) {
            this.k = ColorStateList.valueOf((int)h2.a.d((View)this.a, c.a.colorControlHighlight));
        }
        this.K(s2.c.a(this.a.getContext(), object, z1.m.MaterialCardView_cardForegroundColor));
        this.i0();
        this.f0();
        this.j0();
        this.a.setBackgroundInternal(this.B(this.c));
        object = this.c0() ? this.r() : this.d;
        this.i = object;
        this.a.setForeground(this.B((Drawable)object));
    }

    public void H(int n3, int n4) {
        if (this.p != null) {
            int n5;
            int n6;
            if (this.a.getUseCompatPadding()) {
                n6 = (int)Math.ceil(this.f() * 2.0f);
                n5 = (int)Math.ceil(this.e() * 2.0f);
            } else {
                n6 = 0;
                n5 = 0;
            }
            int n7 = this.F() ? n3 - this.e - this.f - n5 : this.e;
            int n8 = this.E() ? this.e : n4 - this.e - this.f - n6;
            n3 = this.F() ? this.e : n3 - this.e - this.f - n5;
            n4 = this.E() ? n4 - this.e - this.f - n6 : this.e;
            if (this.a.getLayoutDirection() != 1) {
                n6 = n3;
                n3 = n7;
                n7 = n6;
            }
            this.p.setLayerInset(2, n3, n4, n7, n8);
        }
    }

    public void I(boolean bl) {
        this.r = bl;
    }

    public void J(ColorStateList colorStateList) {
        this.c.i0(colorStateList);
    }

    public void K(ColorStateList colorStateList) {
        i i3 = this.d;
        ColorStateList colorStateList2 = colorStateList;
        if (colorStateList == null) {
            colorStateList2 = ColorStateList.valueOf((int)0);
        }
        i3.i0(colorStateList2);
    }

    public void L(boolean bl) {
        this.s = bl;
    }

    public void M(boolean bl) {
        this.N(bl, false);
    }

    public void N(boolean bl, boolean bl2) {
        Drawable drawable = this.j;
        if (drawable != null) {
            if (bl2) {
                this.b(bl);
                return;
            }
            int n3 = bl ? 255 : 0;
            drawable.setAlpha(n3);
            float f3 = bl ? 1.0f : 0.0f;
            this.x = f3;
        }
    }

    public void O(Drawable drawable) {
        if (drawable != null) {
            this.j = drawable = h0.a.r(drawable).mutate();
            drawable.setTintList(this.l);
            this.M(this.a.isChecked());
        } else {
            this.j = z;
        }
        drawable = this.p;
        if (drawable != null) {
            drawable.setDrawableByLayerId(z1.g.mtrl_card_checked_layer_id, this.j);
        }
    }

    public void P(int n3) {
        this.g = n3;
        this.H(this.a.getMeasuredWidth(), this.a.getMeasuredHeight());
    }

    public void Q(int n3) {
        this.e = n3;
    }

    public void R(int n3) {
        this.f = n3;
    }

    public void S(ColorStateList colorStateList) {
        this.l = colorStateList;
        Drawable drawable = this.j;
        if (drawable != null) {
            drawable.setTintList(colorStateList);
        }
    }

    public void T(float f3) {
        this.W(this.m.x(f3));
        this.i.invalidateSelf();
        if (this.b0() || this.a0()) {
            this.e0();
        }
        if (this.b0()) {
            this.h0();
        }
    }

    public void U(float f3) {
        this.c.j0(f3);
        i i3 = this.d;
        if (i3 != null) {
            i3.j0(f3);
        }
        if ((i3 = this.q) != null) {
            i3.j0(f3);
        }
    }

    public void V(ColorStateList colorStateList) {
        this.k = colorStateList;
        this.i0();
    }

    public void W(o o3) {
        this.m = o3;
        this.c.setShapeAppearanceModel(o3);
        i i3 = this.c;
        i3.o0(i3.Z() ^ true);
        i3 = this.d;
        if (i3 != null) {
            i3.setShapeAppearanceModel(o3);
        }
        if ((i3 = this.q) != null) {
            i3.setShapeAppearanceModel(o3);
        }
    }

    public void X(ColorStateList colorStateList) {
        if (this.n == colorStateList) {
            return;
        }
        this.n = colorStateList;
        this.j0();
    }

    public void Y(int n3) {
        if (n3 == this.h) {
            return;
        }
        this.h = n3;
        this.j0();
    }

    public void Z(int n3, int n4, int n5, int n6) {
        this.b.set(n3, n4, n5, n6);
        this.e0();
    }

    public final boolean a0() {
        return this.a.getPreventCornerOverlap() && !this.g();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void b(boolean bl) {
        float f3 = bl ? 1.0f : 0.0f;
        float f4 = bl ? 1.0f - this.x : this.x;
        ValueAnimator valueAnimator = this.t;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.t = null;
        }
        this.t = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.x, f3});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new a(this));
        this.t.setInterpolator(this.u);
        valueAnimator = this.t;
        int n3 = bl ? this.v : this.w;
        long l3 = (long)((float)n3 * f4);
        valueAnimator.setDuration(l3);
        this.t.start();
    }

    public final boolean b0() {
        return this.a.getPreventCornerOverlap() && this.g() && this.a.getUseCompatPadding();
    }

    public final float c() {
        return Math.max(Math.max(this.d(this.m.q(), this.c.P()), this.d(this.m.s(), this.c.Q())), Math.max(this.d(this.m.k(), this.c.y()), this.d(this.m.i(), this.c.x())));
    }

    public final boolean c0() {
        if (this.a.isClickable()) {
            return true;
        }
        MaterialCardView materialCardView = this.a;
        while (materialCardView.isDuplicateParentStateEnabled() && materialCardView.getParent() instanceof View) {
            materialCardView = (View)materialCardView.getParent();
        }
        return materialCardView.isClickable();
    }

    public final float d(e e3, float f3) {
        if (e3 instanceof n) {
            return (float)((1.0 - y) * (double)f3);
        }
        if (e3 instanceof f) {
            return f3 / 2.0f;
        }
        return 0.0f;
    }

    public void d0() {
        Drawable drawable = this.i;
        Object object = this.c0() ? this.r() : this.d;
        this.i = object;
        if (drawable != object) {
            this.g0((Drawable)object);
        }
    }

    public final float e() {
        float f3 = this.a.getMaxCardElevation();
        float f4 = this.b0() ? this.c() : 0.0f;
        return f3 + f4;
    }

    public void e0() {
        float f3 = !this.a0() && !this.b0() ? 0.0f : this.c();
        int n3 = (int)(f3 - this.t());
        MaterialCardView materialCardView = this.a;
        Rect rect = this.b;
        materialCardView.j(rect.left + n3, rect.top + n3, rect.right + n3, rect.bottom + n3);
    }

    public final float f() {
        float f3 = this.a.getMaxCardElevation();
        float f4 = this.b0() ? this.c() : 0.0f;
        return f3 * 1.5f + f4;
    }

    public void f0() {
        this.c.h0(this.a.getCardElevation());
    }

    public final boolean g() {
        return this.c.Z();
    }

    public final void g0(Drawable drawable) {
        if (this.a.getForeground() instanceof InsetDrawable) {
            ((InsetDrawable)this.a.getForeground()).setDrawable(drawable);
            return;
        }
        this.a.setForeground(this.B(drawable));
    }

    public final Drawable h() {
        this.q = new i(this.m);
        return new RippleDrawable(this.k, null, (Drawable)this.q);
    }

    public void h0() {
        if (!this.C()) {
            this.a.setBackgroundInternal(this.B(this.c));
        }
        this.a.setForeground(this.B(this.i));
    }

    public void i() {
        Drawable drawable = this.o;
        if (drawable != null) {
            drawable = drawable.getBounds();
            int n3 = drawable.bottom;
            this.o.setBounds(drawable.left, drawable.top, drawable.right, n3 - 1);
            this.o.setBounds(drawable.left, drawable.top, drawable.right, n3);
        }
    }

    public final void i0() {
        Drawable drawable = this.o;
        if (drawable != null) {
            ((RippleDrawable)drawable).setColor(this.k);
        }
    }

    public i j() {
        return this.c;
    }

    public void j0() {
        this.d.t0(this.h, this.n);
    }

    public ColorStateList k() {
        return this.c.D();
    }

    public ColorStateList l() {
        return this.d.D();
    }

    public Drawable m() {
        return this.j;
    }

    public int n() {
        return this.g;
    }

    public int o() {
        return this.e;
    }

    public int p() {
        return this.f;
    }

    public ColorStateList q() {
        return this.l;
    }

    public final Drawable r() {
        if (this.o == null) {
            this.o = this.h();
        }
        if (this.p == null) {
            LayerDrawable layerDrawable;
            this.p = layerDrawable = new LayerDrawable(new Drawable[]{this.o, this.d, this.j});
            layerDrawable.setId(2, z1.g.mtrl_card_checked_layer_id);
        }
        return this.p;
    }

    public float s() {
        return this.c.P();
    }

    public final float t() {
        if (this.a.getPreventCornerOverlap() && this.a.getUseCompatPadding()) {
            return (float)((1.0 - y) * (double)this.a.getCardViewRadius());
        }
        return 0.0f;
    }

    public float u() {
        return this.c.E();
    }

    public ColorStateList v() {
        return this.k;
    }

    public o w() {
        return this.m;
    }

    public int x() {
        ColorStateList colorStateList = this.n;
        if (colorStateList == null) {
            return -1;
        }
        return colorStateList.getDefaultColor();
    }

    public ColorStateList y() {
        return this.n;
    }

    public int z() {
        return this.h;
    }
}

