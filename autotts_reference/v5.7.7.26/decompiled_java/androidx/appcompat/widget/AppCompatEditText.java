/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.text.Editable
 *  android.text.method.KeyListener
 *  android.util.AttributeSet
 *  android.view.ActionMode$Callback
 *  android.view.DragEvent
 *  android.view.View
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputMethodManager
 *  android.view.textclassifier.TextClassifier
 *  android.widget.EditText
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassifier;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.d;
import androidx.appcompat.widget.h;
import androidx.appcompat.widget.i0;
import androidx.appcompat.widget.j;
import androidx.appcompat.widget.j0;
import androidx.appcompat.widget.m;
import androidx.appcompat.widget.o;
import androidx.appcompat.widget.p;
import androidx.core.widget.k;
import o0.h0;
import o0.x0;
import s0.c;

public class AppCompatEditText
extends EditText
implements h0 {
    public final d c;
    public final p d;
    public final o e;
    public final k f;
    public final h g;
    public a h;

    public AppCompatEditText(Context context) {
        this(context, null);
    }

    public AppCompatEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, c.a.editTextStyle);
    }

    public AppCompatEditText(Context object, AttributeSet attributeSet, int n3) {
        super(j0.b((Context)object), attributeSet, n3);
        i0.a((View)this, this.getContext());
        object = new d((View)this);
        this.c = object;
        ((d)object).e(attributeSet, n3);
        this.d = object = new p((TextView)this);
        ((p)object).m(attributeSet, n3);
        ((p)object).b();
        this.e = new o((TextView)this);
        this.f = new k();
        this.g = object = new h(this);
        ((h)object).c(attributeSet, n3);
        this.d((h)object);
    }

    private a getSuperCaller() {
        if (this.h == null) {
            this.h = new a(this);
        }
        return this.h;
    }

    @Override
    public o0.d a(o0.d d3) {
        return this.f.a((View)this, d3);
    }

    public void d(h h3) {
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

    public Editable getText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return super.getText();
        }
        return super.getEditableText();
    }

    public TextClassifier getTextClassifier() {
        o o3;
        if (Build.VERSION.SDK_INT < 28 && (o3 = this.e) != null) {
            return o3.a();
        }
        return this.getSuperCaller().a();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnection;
        InputConnection inputConnection2 = super.onCreateInputConnection(editorInfo);
        this.d.r((TextView)this, inputConnection2, editorInfo);
        inputConnection2 = inputConnection = j.a(inputConnection2, editorInfo, (View)this);
        if (inputConnection != null) {
            inputConnection2 = inputConnection;
            if (Build.VERSION.SDK_INT <= 30) {
                String[] stringArray = x0.B((View)this);
                inputConnection2 = inputConnection;
                if (stringArray != null) {
                    s0.a.c(editorInfo, stringArray);
                    inputConnection2 = s0.c.c((View)this, inputConnection, editorInfo);
                }
            }
        }
        return this.g.d(inputConnection2, editorInfo);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 30 && n3 < 33) {
            ((InputMethodManager)this.getContext().getSystemService("input_method")).isActive((View)this);
        }
    }

    public boolean onDragEvent(DragEvent dragEvent) {
        if (m.a((View)this, dragEvent)) {
            return true;
        }
        return super.onDragEvent(dragEvent);
    }

    public boolean onTextContextMenuItem(int n3) {
        if (m.b((TextView)this, n3)) {
            return true;
        }
        return super.onTextContextMenuItem(n3);
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

    public void setEmojiCompatEnabled(boolean bl) {
        this.g.e(bl);
    }

    public void setKeyListener(KeyListener keyListener) {
        super.setKeyListener(this.g.a(keyListener));
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

    public void setTextClassifier(TextClassifier textClassifier) {
        o o3;
        if (Build.VERSION.SDK_INT < 28 && (o3 = this.e) != null) {
            o3.b(textClassifier);
            return;
        }
        this.getSuperCaller().b(textClassifier);
    }

    public class a {
        public final AppCompatEditText a;

        public a(AppCompatEditText appCompatEditText) {
            this.a = appCompatEditText;
        }

        public TextClassifier a() {
            return AppCompatEditText.super.getTextClassifier();
        }

        public void b(TextClassifier textClassifier) {
            AppCompatEditText.super.setTextClassifier(textClassifier);
        }
    }
}

