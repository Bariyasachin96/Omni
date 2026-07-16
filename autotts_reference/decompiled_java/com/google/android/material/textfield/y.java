/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.EditText
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView$ScaleType
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.m0;
import androidx.core.widget.j;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.c0;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.t;
import p0.s;
import s2.c;
import z1.e;
import z1.g;
import z1.i;
import z1.m;

public class y
extends LinearLayout {
    public final TextInputLayout c;
    public final TextView d;
    public CharSequence e;
    public final CheckableImageButton f;
    public ColorStateList g;
    public PorterDuff.Mode h;
    public int i;
    public ImageView.ScaleType j;
    public View.OnLongClickListener k;
    public boolean l;

    public y(TextInputLayout object, m0 m02) {
        super(object.getContext());
        this.c = object;
        this.setVisibility(8);
        this.setOrientation(0);
        this.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1, 0x800003));
        object = (CheckableImageButton)LayoutInflater.from((Context)this.getContext()).inflate(z1.i.design_text_input_start_icon, (ViewGroup)this, false);
        this.f = object;
        t.e((CheckableImageButton)((Object)object));
        AppCompatTextView appCompatTextView = new AppCompatTextView(this.getContext());
        this.d = appCompatTextView;
        this.j(m02);
        this.i(m02);
        this.addView((View)object);
        this.addView((View)appCompatTextView);
    }

    public void A(s s3) {
        if (this.d.getVisibility() == 0) {
            s3.s0((View)this.d);
            s3.I0((View)this.d);
            return;
        }
        s3.I0((View)this.f);
    }

    public void B() {
        EditText editText = this.c.g;
        if (editText == null) {
            return;
        }
        int n3 = this.k() ? 0 : editText.getPaddingStart();
        this.d.setPaddingRelative(n3, editText.getCompoundPaddingTop(), this.getContext().getResources().getDimensionPixelSize(z1.e.material_input_text_to_prefix_suffix_padding), editText.getCompoundPaddingBottom());
    }

    public final void C() {
        CharSequence charSequence = this.e;
        int n3 = 8;
        int n4 = charSequence != null && !this.l ? 0 : 8;
        if (this.f.getVisibility() == 0 || n4 == 0) {
            n3 = 0;
        }
        this.setVisibility(n3);
        this.d.setVisibility(n4);
        this.c.p0();
    }

    public CharSequence a() {
        return this.e;
    }

    public ColorStateList b() {
        return this.d.getTextColors();
    }

    public int c() {
        int n3 = this.k() ? this.f.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams)this.f.getLayoutParams()).getMarginEnd() : 0;
        return this.getPaddingStart() + this.d.getPaddingStart() + n3;
    }

    public TextView d() {
        return this.d;
    }

    public CharSequence e() {
        return this.f.getContentDescription();
    }

    public Drawable f() {
        return this.f.getDrawable();
    }

    public int g() {
        return this.i;
    }

    public ImageView.ScaleType h() {
        return this.j;
    }

    public final void i(m0 m02) {
        this.d.setVisibility(8);
        this.d.setId(z1.g.textinput_prefix_text);
        this.d.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
        this.d.setAccessibilityLiveRegion(1);
        this.o(m02.n(m.TextInputLayout_prefixTextAppearance, 0));
        int n3 = m.TextInputLayout_prefixTextColor;
        if (m02.s(n3)) {
            this.p(m02.c(n3));
        }
        this.n(m02.p(m.TextInputLayout_prefixText));
    }

    public final void j(m0 m02) {
        if (s2.c.k(this.getContext())) {
            ((ViewGroup.MarginLayoutParams)this.f.getLayoutParams()).setMarginEnd(0);
        }
        this.u(null);
        this.v(null);
        int n3 = m.TextInputLayout_startIconTint;
        if (m02.s(n3)) {
            this.g = s2.c.b(this.getContext(), m02, n3);
        }
        if (m02.s(n3 = m.TextInputLayout_startIconTintMode)) {
            this.h = c0.n(m02.k(n3, -1), null);
        }
        if (m02.s(n3 = m.TextInputLayout_startIconDrawable)) {
            this.s(m02.g(n3));
            n3 = m.TextInputLayout_startIconContentDescription;
            if (m02.s(n3)) {
                this.r(m02.p(n3));
            }
            this.q(m02.a(m.TextInputLayout_startIconCheckable, true));
        }
        this.t(m02.f(m.TextInputLayout_startIconMinSize, this.getResources().getDimensionPixelSize(z1.e.mtrl_min_touch_target_size)));
        n3 = m.TextInputLayout_startIconScaleType;
        if (m02.s(n3)) {
            this.w(t.b(m02.k(n3, -1)));
        }
    }

    public boolean k() {
        return this.f.getVisibility() == 0;
    }

    public void l(boolean bl) {
        this.l = bl;
        this.C();
    }

    public void m() {
        t.d(this.c, this.f, this.g);
    }

    public void n(CharSequence charSequence) {
        CharSequence charSequence2 = TextUtils.isEmpty((CharSequence)charSequence) ? null : charSequence;
        this.e = charSequence2;
        this.d.setText(charSequence);
        this.C();
    }

    public void o(int n3) {
        androidx.core.widget.j.m(this.d, n3);
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        this.B();
    }

    public void p(ColorStateList colorStateList) {
        this.d.setTextColor(colorStateList);
    }

    public void q(boolean bl) {
        this.f.setCheckable(bl);
    }

    public void r(CharSequence charSequence) {
        if (this.e() != charSequence) {
            this.f.setContentDescription(charSequence);
        }
    }

    public void s(Drawable drawable) {
        this.f.setImageDrawable(drawable);
        if (drawable != null) {
            t.a(this.c, this.f, this.g, this.h);
            this.z(true);
            this.m();
            return;
        }
        this.z(false);
        this.u(null);
        this.v(null);
        this.r(null);
    }

    public void t(int n3) {
        if (n3 >= 0) {
            if (n3 != this.i) {
                this.i = n3;
                t.g(this.f, n3);
            }
            return;
        }
        throw new IllegalArgumentException("startIconSize cannot be less than 0");
    }

    public void u(View.OnClickListener onClickListener) {
        t.h(this.f, onClickListener, this.k);
    }

    public void v(View.OnLongClickListener onLongClickListener) {
        this.k = onLongClickListener;
        t.i(this.f, onLongClickListener);
    }

    public void w(ImageView.ScaleType scaleType) {
        this.j = scaleType;
        t.j(this.f, scaleType);
    }

    public void x(ColorStateList colorStateList) {
        if (this.g != colorStateList) {
            this.g = colorStateList;
            t.a(this.c, this.f, colorStateList, this.h);
        }
    }

    public void y(PorterDuff.Mode mode) {
        if (this.h != mode) {
            this.h = mode;
            t.a(this.c, this.f, this.g, mode);
        }
    }

    public void z(boolean bl) {
        if (this.k() != bl) {
            CheckableImageButton checkableImageButton = this.f;
            int n3 = bl ? 0 : 8;
            checkableImageButton.setVisibility(n3);
            this.B();
            this.C();
        }
    }
}

