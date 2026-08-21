/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.CheckedTextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.z;
import androidx.core.widget.b;
import c.j;
import h0.a;
import o0.x0;

public class e {
    public final CheckedTextView a;
    public ColorStateList b = null;
    public PorterDuff.Mode c = null;
    public boolean d = false;
    public boolean e = false;
    public boolean f;

    public e(CheckedTextView checkedTextView) {
        this.a = checkedTextView;
    }

    public void a() {
        Drawable drawable = androidx.core.widget.b.a(this.a);
        if (drawable != null && (this.d || this.e)) {
            drawable = h0.a.r(drawable).mutate();
            if (this.d) {
                h0.a.o(drawable, this.b);
            }
            if (this.e) {
                h0.a.p(drawable, this.c);
            }
            if (drawable.isStateful()) {
                drawable.setState(this.a.getDrawableState());
            }
            this.a.setCheckMarkDrawable(drawable);
        }
    }

    public ColorStateList b() {
        return this.b;
    }

    public PorterDuff.Mode c() {
        return this.c;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void d(AttributeSet attributeSet, int n3) {
        Object object = this.a.getContext();
        int[] nArray = j.CheckedTextView;
        object = m0.v((Context)object, attributeSet, nArray, n3, 0);
        CheckedTextView checkedTextView = this.a;
        x0.f0((View)checkedTextView, checkedTextView.getContext(), nArray, attributeSet, ((m0)object).r(), n3, 0);
        try {
            block8: {
                n3 = j.CheckedTextView_checkMarkCompat;
                if (((m0)object).s(n3) && (n3 = ((m0)object).n(n3, 0)) != 0) {
                    try {
                        attributeSet = this.a;
                        attributeSet.setCheckMarkDrawable(d.a.b(attributeSet.getContext(), n3));
                        break block8;
                    }
                    catch (Resources.NotFoundException notFoundException) {}
                }
                if (((m0)object).s(n3 = j.CheckedTextView_android_checkMark) && (n3 = ((m0)object).n(n3, 0)) != 0) {
                    attributeSet = this.a;
                    attributeSet.setCheckMarkDrawable(d.a.b(attributeSet.getContext(), n3));
                }
            }
            n3 = j.CheckedTextView_checkMarkTint;
            if (((m0)object).s(n3)) {
                androidx.core.widget.b.b(this.a, ((m0)object).c(n3));
            }
            if (((m0)object).s(n3 = j.CheckedTextView_checkMarkTintMode)) {
                androidx.core.widget.b.c(this.a, z.e(((m0)object).k(n3, -1), null));
            }
            ((m0)object).x();
            return;
        }
        catch (Throwable throwable) {}
        ((m0)object).x();
        throw throwable;
    }

    public void e() {
        if (this.f) {
            this.f = false;
            return;
        }
        this.f = true;
        this.a();
    }

    public void f(ColorStateList colorStateList) {
        this.b = colorStateList;
        this.d = true;
        this.a();
    }

    public void g(PorterDuff.Mode mode) {
        this.c = mode;
        this.e = true;
        this.a();
    }
}

