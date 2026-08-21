/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.text.method.KeyListener
 *  android.util.AttributeSet
 *  android.view.ActionMode$Callback
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.widget.AutoCompleteTextView
 *  android.widget.EditText
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.h;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.p;
import c.a;

public class AppCompatAutoCompleteTextView
extends AutoCompleteTextView {
    public static final int[] f = new int[]{16843126};
    public final d c;
    public final p d;
    public final h e;

    public AppCompatAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public AppCompatAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.autoCompleteTextViewStyle);
    }

    public AppCompatAutoCompleteTextView(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = m0.v(this.getContext(), attributeSet, f, n3, 0);
        if (((m0)object).s(0)) {
            this.setDropDownBackgroundDrawable(((m0)object).g(0));
        }
        ((m0)object).x();
        this.c = object = new d((View)this);
        ((d)object).e(attributeSet, n3);
        this.d = object = new p((TextView)this);
        ((p)object).m(attributeSet, n3);
        ((p)object).b();
        this.e = object = new h((EditText)this);
        ((h)object).c(attributeSet, n3);
        this.a((h)object);
    }

    public void a(h h3) {
        KeyListener keyListener = this.getKeyListener();
        if (h3.b(keyListener)) {
            boolean bl = super.isFocusable();
            boolean bl2 = super.isClickable();
            boolean bl3 = super.isLongClickable();
            int n3 = super.getInputType();
            if ((h3 = h3.a(keyListener)) != keyListener) {
                super.setKeyListener((KeyListener)h3);
                super.setRawInputType(n3);
                super.setFocusable(bl);
                super.setClickable(bl2);
                super.setLongClickable(bl3);
            }
        }
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

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        return androidx.core.widget.j.o(super.getCustomSelectionActionModeCallback());
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

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnection = j.a(super.onCreateInputConnection(editorInfo), editorInfo, (View)this);
        return this.e.d(inputConnection, editorInfo);
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

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(androidx.core.widget.j.p((TextView)this, callback));
    }

    public void setDropDownBackgroundResource(int n3) {
        this.setDropDownBackgroundDrawable(d.a.b(this.getContext(), n3));
    }

    public void setEmojiCompatEnabled(boolean bl) {
        this.e.e(bl);
    }

    public void setKeyListener(KeyListener keyListener) {
        super.setKeyListener(this.e.a(keyListener));
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

    public void setTextAppearance(Context context, int n3) {
        super.setTextAppearance(context, n3);
        p p3 = this.d;
        if (p3 != null) {
            p3.q(context, n3);
        }
    }
}

