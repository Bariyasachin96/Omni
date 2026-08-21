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
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.f;
import androidx.appcompat.widget.i;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.p;
import androidx.core.widget.l;
import c.a;

public class AppCompatCheckBox
extends CheckBox
implements l {
    public final f c;
    public final d d;
    public final p e;
    public i f;

    public AppCompatCheckBox(Context context) {
        this(context, null);
    }

    public AppCompatCheckBox(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.checkboxStyle);
    }

    public AppCompatCheckBox(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new f((CompoundButton)this);
        this.c = object;
        ((f)object).d(attributeSet, n3);
        this.d = object = new d((View)this);
        ((d)object).e(attributeSet, n3);
        this.e = object = new p((TextView)this);
        ((p)object).m(attributeSet, n3);
        this.getEmojiTextViewHelper().c(attributeSet, n3);
    }

    private i getEmojiTextViewHelper() {
        if (this.f == null) {
            this.f = new i((TextView)this);
        }
        return this.f;
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.d;
        if (object != null) {
            ((d)object).b();
        }
        if ((object = this.e) != null) {
            ((p)object).b();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        d d3 = this.d;
        if (d3 != null) {
            return d3.c();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d d3 = this.d;
        if (d3 != null) {
            return d3.d();
        }
        return null;
    }

    @Override
    public ColorStateList getSupportButtonTintList() {
        f f3 = this.c;
        if (f3 != null) {
            return f3.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportButtonTintMode() {
        f f3 = this.c;
        if (f3 != null) {
            return f3.c();
        }
        return null;
    }

    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.e.j();
    }

    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.e.k();
    }

    public void setAllCaps(boolean bl) {
        super.setAllCaps(bl);
        this.getEmojiTextViewHelper().d(bl);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d d3 = this.d;
        if (d3 != null) {
            d3.f(drawable);
        }
    }

    public void setBackgroundResource(int n3) {
        super.setBackgroundResource(n3);
        d d3 = this.d;
        if (d3 != null) {
            d3.g(n3);
        }
    }

    public void setButtonDrawable(int n3) {
        this.setButtonDrawable(d.a.b(this.getContext(), n3));
    }

    public void setButtonDrawable(Drawable object) {
        super.setButtonDrawable((Drawable)object);
        object = this.c;
        if (object != null) {
            ((f)object).e();
        }
    }

    public void setCompoundDrawables(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawables((Drawable)object, drawable, drawable2, drawable3);
        object = this.e;
        if (object != null) {
            ((p)object).p();
        }
    }

    public void setCompoundDrawablesRelative(Drawable object, Drawable drawable, Drawable drawable2, Drawable drawable3) {
        super.setCompoundDrawablesRelative((Drawable)object, drawable, drawable2, drawable3);
        object = this.e;
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
        d d3 = this.d;
        if (d3 != null) {
            d3.i(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d d3 = this.d;
        if (d3 != null) {
            d3.j(mode);
        }
    }

    @Override
    public void setSupportButtonTintList(ColorStateList colorStateList) {
        f f3 = this.c;
        if (f3 != null) {
            f3.f(colorStateList);
        }
    }

    @Override
    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        f f3 = this.c;
        if (f3 != null) {
            f3.g(mode);
        }
    }

    public void setSupportCompoundDrawablesTintList(ColorStateList colorStateList) {
        this.e.w(colorStateList);
        this.e.b();
    }

    public void setSupportCompoundDrawablesTintMode(PorterDuff.Mode mode) {
        this.e.x(mode);
        this.e.b();
    }
}

