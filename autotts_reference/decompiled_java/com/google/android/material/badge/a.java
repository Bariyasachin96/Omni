/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.badge.b;
import com.google.android.material.internal.w;
import com.google.android.material.internal.z;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import s2.c;
import s2.d;
import v2.i;
import v2.o;
import z1.k;
import z1.l;

public class a
extends Drawable
implements w.b {
    public static final int p = z1.l.Widget_MaterialComponents_Badge;
    public static final int q = z1.c.badgeStyle;
    public final WeakReference c;
    public final i d;
    public final w e;
    public final Rect f;
    public final BadgeState g;
    public float h;
    public float i;
    public int j;
    public float k;
    public float l;
    public float m;
    public WeakReference n;
    public WeakReference o;

    public a(Context context, int n3, int n4, int n5, BadgeState.State object) {
        w w3;
        this.c = new WeakReference<Context>(context);
        z.c(context);
        this.f = new Rect();
        this.e = w3 = new w(this);
        w3.g().setTextAlign(Paint.Align.CENTER);
        this.g = object = new BadgeState(context, n3, n4, n5, (BadgeState.State)object);
        n3 = this.B() ? ((BadgeState)object).m() : ((BadgeState)object).i();
        n4 = this.B() ? ((BadgeState)object).l() : ((BadgeState)object).h();
        this.d = new i(v2.o.b(context, n3, n4).m());
        this.N();
    }

    public static void P(View view) {
        view = (ViewGroup)view.getParent();
        view.setClipChildren(false);
        view.setClipToPadding(false);
    }

    public static a e(Context context) {
        return new a(context, 0, q, p, null);
    }

    public static a f(Context context, BadgeState.State state) {
        return new a(context, 0, q, p, state);
    }

    public final int A() {
        int n3;
        int n4 = this.g.C();
        if (this.B()) {
            n3 = this.g.B();
            Context context = (Context)this.c.get();
            n4 = n3;
            if (context != null) {
                float f3 = a2.a.b(0.0f, 1.0f, 0.3f, 1.0f, s2.c.f(context) - 1.0f);
                n4 = a2.a.c(n3, n3 - this.g.t(), f3);
            }
        }
        n3 = n4;
        if (this.g.k == 0) {
            n3 = n4 - Math.round(this.m);
        }
        return n3 + this.g.c();
    }

    public final boolean B() {
        return this.D() || this.C();
        {
        }
    }

    public boolean C() {
        return !this.g.E() && this.g.D();
    }

    public boolean D() {
        return this.g.E();
    }

    public final void E() {
        this.e.g().setAlpha(this.getAlpha());
        this.invalidateSelf();
    }

    public final void F() {
        ColorStateList colorStateList = ColorStateList.valueOf((int)this.g.e());
        if (this.d.D() != colorStateList) {
            this.d.i0(colorStateList);
            this.invalidateSelf();
        }
    }

    public final void G() {
        this.e.l(true);
        this.I();
        this.R();
        this.invalidateSelf();
    }

    public final void H() {
        WeakReference weakReference = this.n;
        if (weakReference != null && weakReference.get() != null) {
            View view = (View)this.n.get();
            weakReference = this.o;
            weakReference = weakReference != null ? (FrameLayout)weakReference.get() : null;
            this.Q(view, (FrameLayout)weakReference);
        }
    }

    public final void I() {
        Context context = (Context)this.c.get();
        if (context == null) {
            return;
        }
        i i3 = this.d;
        int n3 = this.B() ? this.g.m() : this.g.i();
        int n4 = this.B() ? this.g.l() : this.g.h();
        i3.setShapeAppearanceModel(v2.o.b(context, n3, n4).m());
        this.invalidateSelf();
    }

    public final void J() {
        d d3;
        Context context;
        block3: {
            block2: {
                context = (Context)this.c.get();
                if (context == null) break block2;
                d3 = new d(context, this.g.A());
                if (this.e.e() != d3) break block3;
            }
            return;
        }
        this.e.k(d3, context);
        this.K();
        this.R();
        this.invalidateSelf();
    }

    public final void K() {
        this.e.g().setColor(this.g.j());
        this.invalidateSelf();
    }

    public final void L() {
        this.S();
        this.e.l(true);
        this.R();
        this.invalidateSelf();
    }

    public final void M() {
        this.setVisible(this.g.G(), false);
    }

    public final void N() {
        this.I();
        this.J();
        this.L();
        this.G();
        this.E();
        this.F();
        this.K();
        this.H();
        this.R();
        this.M();
    }

    public void O(int n3) {
        BadgeState badgeState = this.g;
        if (badgeState.l != n3) {
            badgeState.l = n3;
            this.R();
        }
    }

    public void Q(View view, FrameLayout frameLayout) {
        this.n = new WeakReference<View>(view);
        this.o = new WeakReference<FrameLayout>(frameLayout);
        a.P(view);
        this.R();
        this.invalidateSelf();
    }

    public final void R() {
        Context context = (Context)this.c.get();
        WeakReference weakReference = this.n;
        ViewGroup viewGroup = null;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        if (context != null && weakReference != null) {
            Rect rect = new Rect();
            rect.set(this.f);
            context = new Rect();
            weakReference.getDrawingRect((Rect)context);
            WeakReference weakReference2 = this.o;
            if (weakReference2 != null) {
                viewGroup = (ViewGroup)weakReference2.get();
            }
            if (viewGroup != null) {
                viewGroup.offsetDescendantRectToMyCoords((View)weakReference, (Rect)context);
            }
            this.d((Rect)context, (View)weakReference);
            b.g(this.f, this.h, this.i, this.l, this.m);
            float f3 = this.k;
            if (f3 != -1.0f) {
                this.d.e0(f3);
            }
            if (!rect.equals((Object)this.f)) {
                this.d.setBounds(this.f);
            }
        }
    }

    public final void S() {
        if (this.o() != -2) {
            this.j = (int)Math.pow(10.0, (double)this.o() - 1.0) - 1;
            return;
        }
        this.j = this.p();
    }

    @Override
    public void a() {
        this.invalidateSelf();
    }

    public final void b(View view) {
        FrameLayout frameLayout;
        FrameLayout frameLayout2 = frameLayout = this.k();
        if (frameLayout == null) {
            frameLayout2 = view.getParent();
        }
        if (frameLayout2 instanceof View && frameLayout2.getParent() instanceof View) {
            this.c(view, (View)frameLayout2.getParent());
        }
    }

    public final void c(View view, View view2) {
        boolean bl;
        float f3;
        float f4;
        FrameLayout frameLayout = this.k();
        if (frameLayout == null) {
            f4 = view.getY();
            f3 = view.getX();
            view = view.getParent();
        } else {
            f4 = 0.0f;
            f3 = 0.0f;
            view = frameLayout;
        }
        while ((bl = view instanceof View) && view != view2 && (frameLayout = view.getParent()) instanceof ViewGroup && !((ViewGroup)frameLayout).getClipChildren()) {
            frameLayout = view;
            f4 += frameLayout.getY();
            f3 += frameLayout.getX();
            view = view.getParent();
        }
        if (bl) {
            float f5 = this.y(f4);
            float f6 = this.n(f3);
            f4 = this.i(view.getHeight(), f4);
            f3 = this.t(view.getWidth(), f3);
            if (f5 < 0.0f) {
                this.i += Math.abs(f5);
            }
            if (f6 < 0.0f) {
                this.h += Math.abs(f6);
            }
            if (f4 > 0.0f) {
                this.i -= Math.abs(f4);
            }
            if (f3 > 0.0f) {
                this.h -= Math.abs(f3);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void d(Rect rect, View view) {
        float f3 = this.B() ? this.g.d : this.g.c;
        this.k = f3;
        if (f3 != -1.0f) {
            this.l = f3;
            this.m = f3;
        } else {
            f3 = this.B() ? this.g.g : this.g.e;
            this.l = Math.round(f3 /= 2.0f);
            f3 = this.B() ? this.g.h : this.g.f;
            this.m = Math.round(f3 /= 2.0f);
        }
        if (this.B()) {
            String string = this.h();
            this.l = Math.max(this.l, this.e.h(string) / 2.0f + (float)this.g.g());
            this.m = f3 = Math.max(this.m, this.e.f(string) / 2.0f + (float)this.g.k());
            this.l = Math.max(this.l, f3);
        }
        int n3 = this.A();
        int n4 = this.g.f();
        this.i = n4 != 8388691 && n4 != 0x800055 ? (float)(rect.top + n3) : (float)(rect.bottom - n3);
        n4 = this.z();
        n3 = this.g.f();
        if (n3 != 0x800033 && n3 != 8388691) {
            f3 = this.g.l == 0 ? (view.getLayoutDirection() == 0 ? (float)rect.right + this.l - (float)n4 : (float)rect.left - this.l + (float)n4) : (view.getLayoutDirection() == 0 ? (float)rect.right - this.l + (this.m * 2.0f - (float)n4) : (float)rect.left + this.l - (this.m * 2.0f - (float)n4));
            this.h = f3;
        } else {
            f3 = this.g.l == 0 ? (view.getLayoutDirection() == 0 ? (float)rect.left + this.l - (this.m * 2.0f - (float)n4) : (float)rect.right - this.l + (this.m * 2.0f - (float)n4)) : (view.getLayoutDirection() == 0 ? (float)rect.left - this.l + (float)n4 : (float)rect.right + this.l - (float)n4);
            this.h = f3;
        }
        if (this.g.F()) {
            this.b(view);
            return;
        }
        this.c(view, null);
    }

    public void draw(Canvas canvas) {
        if (!this.getBounds().isEmpty() && this.getAlpha() != 0 && this.isVisible()) {
            this.d.draw(canvas);
            if (this.B()) {
                this.g(canvas);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void g(Canvas canvas) {
        String string = this.h();
        if (string != null) {
            Rect rect = new Rect();
            this.e.g().getTextBounds(string, 0, string.length(), rect);
            float f3 = this.i - rect.exactCenterY();
            float f4 = this.h;
            int n3 = rect.bottom <= 0 ? (int)f3 : Math.round(f3);
            f3 = n3;
            canvas.drawText(string, f4, f3, (Paint)this.e.g());
        }
    }

    public int getAlpha() {
        return this.g.d();
    }

    public int getIntrinsicHeight() {
        return this.f.height();
    }

    public int getIntrinsicWidth() {
        return this.f.width();
    }

    public int getOpacity() {
        return -3;
    }

    public final String h() {
        if (this.D()) {
            return this.w();
        }
        if (this.C()) {
            return this.r();
        }
        return null;
    }

    public final float i(float f3, float f4) {
        return this.i + this.m - f3 + f4;
    }

    public boolean isStateful() {
        return false;
    }

    public CharSequence j() {
        if (!this.isVisible()) {
            return null;
        }
        if (this.D()) {
            return this.x();
        }
        if (this.C()) {
            return this.s();
        }
        return this.l();
    }

    public FrameLayout k() {
        WeakReference weakReference = this.o;
        if (weakReference != null) {
            return (FrameLayout)weakReference.get();
        }
        return null;
    }

    public final CharSequence l() {
        return this.g.p();
    }

    public int m() {
        return this.g.s();
    }

    public final float n(float f3) {
        return this.h - this.l + f3;
    }

    public int o() {
        return this.g.u();
    }

    @Override
    public boolean onStateChange(int[] nArray) {
        return super.onStateChange(nArray);
    }

    public int p() {
        return this.g.v();
    }

    public int q() {
        if (this.g.D()) {
            return this.g.w();
        }
        return 0;
    }

    public final String r() {
        if (this.j != -2 && this.q() > this.j) {
            Context context = (Context)this.c.get();
            if (context == null) {
                return "";
            }
            return String.format(this.g.x(), context.getString(z1.k.mtrl_exceed_max_badge_number_suffix), this.j, "+");
        }
        return NumberFormat.getInstance(this.g.x()).format(this.q());
    }

    public final String s() {
        if (this.g.q() != 0) {
            Context context = (Context)this.c.get();
            if (context == null) {
                return null;
            }
            if (this.j != -2 && this.q() > this.j) {
                return context.getString(this.g.n(), new Object[]{this.j});
            }
            return context.getResources().getQuantityString(this.g.q(), this.q(), new Object[]{this.q()});
        }
        return null;
    }

    public void setAlpha(int n3) {
        this.g.I(n3);
        this.E();
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public final float t(float f3, float f4) {
        return this.h + this.l - f3 + f4;
    }

    public BadgeState.State u() {
        return this.g.y();
    }

    public String v() {
        return this.g.z();
    }

    public final String w() {
        String string;
        String string2 = this.v();
        int n3 = this.o();
        if (n3 == -2) {
            string = string2;
        } else {
            string = string2;
            if (string2 != null) {
                string = string2;
                if (string2.length() > n3) {
                    string = (Context)this.c.get();
                    if (string == null) {
                        return "";
                    }
                    string2 = string2.substring(0, n3 - 1);
                    string = String.format(string.getString(z1.k.m3_exceed_max_badge_text_suffix), string2, "…");
                }
            }
        }
        return string;
    }

    public final CharSequence x() {
        CharSequence charSequence = this.g.o();
        if (charSequence != null) {
            return charSequence;
        }
        return this.v();
    }

    public final float y(float f3) {
        return this.i - this.m + f3;
    }

    public final int z() {
        int n3 = this.B() ? this.g.r() : this.g.s();
        int n4 = n3;
        if (this.g.k == 1) {
            n4 = this.B() ? this.g.j : this.g.i;
            n4 = n3 + n4;
        }
        return n4 + this.g.b();
    }
}

