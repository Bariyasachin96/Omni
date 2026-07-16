/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package com.google.android.material.textfield;

import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.textfield.r;
import com.google.android.material.textfield.s;
import com.google.android.material.textfield.w;
import z1.f;
import z1.k;

public class x
extends s {
    public int e = z1.f.design_password_eye;
    public EditText f;
    public final View.OnClickListener g = new w(this);

    public x(r r3, int n3) {
        super(r3);
        if (n3 != 0) {
            this.e = n3;
        }
    }

    public static /* synthetic */ void v(x x3, View view) {
        view = x3.f;
        if (view == null) {
            return;
        }
        int n3 = view.getSelectionEnd();
        if (x3.w()) {
            x3.f.setTransformationMethod(null);
        } else {
            x3.f.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
        }
        if (n3 >= 0) {
            x3.f.setSelection(n3);
        }
        x3.r();
    }

    public static boolean x(EditText editText) {
        return editText != null && (editText.getInputType() == 16 || editText.getInputType() == 128 || editText.getInputType() == 144 || editText.getInputType() == 224);
    }

    @Override
    public void b(CharSequence charSequence, int n3, int n4, int n5) {
        this.r();
    }

    @Override
    public int c() {
        return k.password_toggle_content_description;
    }

    @Override
    public int d() {
        return this.e;
    }

    @Override
    public View.OnClickListener f() {
        return this.g;
    }

    @Override
    public boolean l() {
        return true;
    }

    @Override
    public boolean m() {
        return this.w() ^ true;
    }

    @Override
    public void n(EditText editText) {
        this.f = editText;
        this.r();
    }

    @Override
    public void s() {
        if (x.x(this.f)) {
            this.f.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void u() {
        EditText editText = this.f;
        if (editText != null) {
            editText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
        }
    }

    public final boolean w() {
        EditText editText = this.f;
        return editText != null && editText.getTransformationMethod() instanceof PasswordTransformationMethod;
    }
}

