/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.text.InputFilter
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.TextView
 *  android.widget.ToggleButton
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.i;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.p;

public class AppCompatToggleButton
extends ToggleButton {
    public final d c;
    public final p d;
    public i e;

    public AppCompatToggleButton(Context context) {
        this(context, null);
    }

    public AppCompatToggleButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842827);
    }

    public AppCompatToggleButton(Context object, AttributeSet attributeSet, int n3) {
        super((Context)object, attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new d((View)this);
        this.c = object;
        ((d)object).e(attributeSet, n3);
        this.d = object = new p((TextView)this);
        ((p)object).m(attributeSet, n3);
        this.getEmojiTextViewHelper().c(attributeSet, n3);
    }

    private i getEmojiTextViewHelper() {
        if (this.e == null) {
            this.e = new i((TextView)this);
        }
        return this.e;
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.c;
        if (object != null) {
            ((d)object).b();
        }
        if ((object = this.d) != null) {
            ((p)object).b();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        d d3 = this.c;
        if (d3 != null) {
            return d3.c();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d d3 = this.c;
        if (d3 != null) {
            return d3.d();
        }
        return null;
    }

    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.d.j();
    }

    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.d.k();
    }

    public void setAllCaps(boolean bl) {
        super.setAllCaps(bl);
        this.getEmojiTextViewHelper().d(bl);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d d3 = this.c;
        if (d3 != null) {
            d3.f(drawable);
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        d d3 = this.c;
        if (d3 != null) {
            d3.g(n3);
        }
    }

    public void setCompoundDrawables(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawables((Drawable)object, drawable, drawable2, drawable3);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesRelative(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawablesRelative((Drawable)object, drawable, drawable2, drawable3);
        object = this.d;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setEmojiCompatEnabled(boolean bl) {
        this.getEmojiTextViewHelper().e(bl);
    }

    public void setFilters(InputFilter[] inputFilterArray) {
        super.setFilters(this.getEmojiTextViewHelper().a(inputFilterArray));
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d d3 = this.c;
        if (d3 != null) {
            d3.i(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d d3 = this.c;
        if (d3 != null) {
            d3.j(mode);
        }
    }

    public void setSupportCompoundDrawablesTintList(ColorStateList colorStateList) {
        this.d.w(colorStateList);
        this.d.b();
    }

    public void setSupportCompoundDrawablesTintMode(PorterDuff.Mode mode) {
        this.d.x(mode);
        this.d.b();
    }
}

