/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.ActionMode$Callback
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.widget.CheckedTextView
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.e;
import androidx.appcompat.widget.i;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.p;
import c.a;

public class AppCompatCheckedTextView
extends CheckedTextView {
    public final e c;
    public final d d;
    public final p e;
    public i f;

    public AppCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public AppCompatCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.checkedTextViewStyle);
    }

    public AppCompatCheckedTextView(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new p((TextView)this);
        this.e = object;
        ((p)object).m(attributeSet, n3);
        ((p)object).b();
        this.d = object = new d((View)this);
        ((d)object).e(attributeSet, n3);
        this.c = object = new e(this);
        ((e)object).d(attributeSet, n3);
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
        Object object = this.e;
        if (object != null) {
            ((p)object).b();
        }
        if ((object = this.d) != null) {
            ((d)object).b();
        }
        if ((object = this.c) != null) {
            ((e)object).a();
        }
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return androidx.core.widget.j.o(super.getCustomSelectionActionModeCallback());
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

    public ColorStateList getSupportCheckMarkTintList() {
        e e3 = this.c;
        if (e3 != null) {
            return e3.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportCheckMarkTintMode() {
        e e3 = this.c;
        if (e3 != null) {
            return e3.c();
        }
        return null;
    }

    public ColorStateList getSupportCompoundDrawablesTintList() {
        return this.e.j();
    }

    public PorterDuff.Mode getSupportCompoundDrawablesTintMode() {
        return this.e.k();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return j.a(super.onCreateInputConnection(editorInfo), editorInfo, (View)this);
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

    public void setCheckMarkDrawable(int n3) {
        this.setCheckMarkDrawable(d.a.b(this.getContext(), n3));
    }

    public void setCheckMarkDrawable(Drawable object) {
        super.setCheckMarkDrawable((Drawable)object);
        object = this.c;
        if (object != null) {
            ((e)object).e();
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

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.j.p((TextView)this, callback));
    }

    public void setEmojiCompatEnabled(boolean bl) {
        this.getEmojiTextViewHelper().e(bl);
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

    public void setSupportCheckMarkTintList(ColorStateList colorStateList) {
        e e3 = this.c;
        if (e3 != null) {
            e3.f(colorStateList);
        }
    }

    public void setSupportCheckMarkTintMode(PorterDuff.Mode mode) {
        e e3 = this.c;
        if (e3 != null) {
            e3.g(mode);
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

    public void setTextAppearance(Context context, int n3) {
        super.setTextAppearance(context, n3);
        p p3 = this.e;
        if (p3 != null) {
            p3.q(context, n3);
        }
    }
}

