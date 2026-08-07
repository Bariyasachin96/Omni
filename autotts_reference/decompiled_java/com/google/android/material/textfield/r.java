/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener
 *  android.widget.EditText
 *  android.widget.FrameLayout
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
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
import com.google.android.material.internal.y;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.f;
import com.google.android.material.textfield.g;
import com.google.android.material.textfield.p;
import com.google.android.material.textfield.s;
import com.google.android.material.textfield.t;
import com.google.android.material.textfield.v;
import com.google.android.material.textfield.x;
import h0.a;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import s2.c;
import z1.e;
import z1.i;
import z1.k;
import z1.m;

public class r
extends LinearLayout {
    public final TextInputLayout c;
    public final FrameLayout d;
    public final CheckableImageButton e;
    public ColorStateList f;
    public PorterDuff.Mode g;
    public View.OnLongClickListener h;
    public final CheckableImageButton i;
    public final d j;
    public int k = 0;
    public final LinkedHashSet l = new LinkedHashSet();
    public ColorStateList m;
    public PorterDuff.Mode n;
    public int o;
    public ImageView.ScaleType p;
    public View.OnLongClickListener q;
    public CharSequence r;
    public final TextView s;
    public boolean t;
    public EditText u;
    public final AccessibilityManager v;
    public AccessibilityManager.TouchExplorationStateChangeListener w;
    public final TextWatcher x = new y(this){
        public final r c;
        {
            this.c = r3;
        }

        public void afterTextChanged(Editable editable) {
            this.c.m().a(editable);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
            this.c.m().b(charSequence, n3, n4, n5);
        }
    };
    public final TextInputLayout.g y;

    public r(TextInputLayout textInputLayout, m0 m02) {
        super(textInputLayout.getContext());
        CheckableImageButton checkableImageButton;
        CheckableImageButton checkableImageButton2;
        FrameLayout frameLayout;
        TextInputLayout.g g3;
        this.y = g3 = new TextInputLayout.g(this){
            public final r a;
            {
                this.a = r3;
            }

            @Override
            public void a(TextInputLayout linearLayout) {
                if (this.a.u == linearLayout.getEditText()) {
                    return;
                }
                if (this.a.u != null) {
                    this.a.u.removeTextChangedListener(this.a.x);
                    if (this.a.u.getOnFocusChangeListener() == this.a.m().e()) {
                        this.a.u.setOnFocusChangeListener(null);
                    }
                }
                com.google.android.material.textfield.r.b(this.a, linearLayout.getEditText());
                if (this.a.u != null) {
                    this.a.u.addTextChangedListener(this.a.x);
                }
                this.a.m().n(this.a.u);
                linearLayout = this.a;
                ((r)linearLayout).h0(linearLayout.m());
            }
        };
        this.v = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.c = textInputLayout;
        this.setVisibility(8);
        this.setOrientation(0);
        this.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1, 0x800005));
        this.d = frameLayout = new FrameLayout(this.getContext());
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -1));
        Object object = LayoutInflater.from((Context)this.getContext());
        this.e = checkableImageButton2 = this.i((ViewGroup)this, (LayoutInflater)object, z1.g.text_input_error_icon);
        this.i = checkableImageButton = this.i((ViewGroup)frameLayout, (LayoutInflater)object, z1.g.text_input_end_icon);
        this.j = new d(this, m02);
        object = new AppCompatTextView(this.getContext());
        this.s = object;
        this.C(m02);
        this.B(m02);
        this.D(m02);
        frameLayout.addView((View)checkableImageButton);
        this.addView((View)object);
        this.addView((View)frameLayout);
        this.addView((View)checkableImageButton2);
        textInputLayout.j(g3);
        this.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this){
            public final r c;
            {
                this.c = r3;
            }

            public void onViewAttachedToWindow(View view) {
                this.c.g();
            }

            public void onViewDetachedFromWindow(View view) {
                this.c.M();
            }
        });
    }

    public static /* synthetic */ EditText b(r r3, EditText editText) {
        r3.u = editText;
        return editText;
    }

    public boolean A() {
        return this.k != 0;
    }

    public final void B(m0 m02) {
        int n3;
        int n4 = z1.m.TextInputLayout_passwordToggleEnabled;
        if (!m02.s(n4)) {
            n3 = z1.m.TextInputLayout_endIconTint;
            if (m02.s(n3)) {
                this.m = s2.c.b(this.getContext(), m02, n3);
            }
            if (m02.s(n3 = z1.m.TextInputLayout_endIconTintMode)) {
                this.n = c0.n(m02.k(n3, -1), null);
            }
        }
        if (m02.s(n3 = z1.m.TextInputLayout_endIconMode)) {
            this.U(m02.k(n3, 0));
            n4 = z1.m.TextInputLayout_endIconContentDescription;
            if (m02.s(n4)) {
                this.Q(m02.p(n4));
            }
            this.O(m02.a(z1.m.TextInputLayout_endIconCheckable, true));
        } else if (m02.s(n4)) {
            n3 = z1.m.TextInputLayout_passwordToggleTint;
            if (m02.s(n3)) {
                this.m = s2.c.b(this.getContext(), m02, n3);
            }
            if (m02.s(n3 = z1.m.TextInputLayout_passwordToggleTintMode)) {
                this.n = c0.n(m02.k(n3, -1), null);
            }
            this.U(m02.a(n4, false) ? 1 : 0);
            this.Q(m02.p(z1.m.TextInputLayout_passwordToggleContentDescription));
        }
        this.T(m02.f(z1.m.TextInputLayout_endIconMinSize, this.getResources().getDimensionPixelSize(z1.e.mtrl_min_touch_target_size)));
        n4 = z1.m.TextInputLayout_endIconScaleType;
        if (m02.s(n4)) {
            this.X(com.google.android.material.textfield.t.b(m02.k(n4, -1)));
        }
    }

    public final void C(m0 m02) {
        int n3 = z1.m.TextInputLayout_errorIconTint;
        if (m02.s(n3)) {
            this.f = s2.c.b(this.getContext(), m02, n3);
        }
        if (m02.s(n3 = z1.m.TextInputLayout_errorIconTintMode)) {
            this.g = c0.n(m02.k(n3, -1), null);
        }
        if (m02.s(n3 = z1.m.TextInputLayout_errorIconDrawable)) {
            this.c0(m02.g(n3));
        }
        this.e.setContentDescription(this.getResources().getText(z1.k.error_icon_content_description));
        this.e.setImportantForAccessibility(2);
        this.e.setClickable(false);
        this.e.setPressable(false);
        this.e.setCheckable(false);
        this.e.setFocusable(false);
    }

    public final void D(m0 m02) {
        this.s.setVisibility(8);
        this.s.setId(z1.g.textinput_suffix_text);
        this.s.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2, 80.0f));
        this.s.setAccessibilityLiveRegion(1);
        this.q0(m02.n(z1.m.TextInputLayout_suffixTextAppearance, 0));
        int n3 = z1.m.TextInputLayout_suffixTextColor;
        if (m02.s(n3)) {
            this.r0(m02.c(n3));
        }
        this.p0(m02.p(z1.m.TextInputLayout_suffixText));
    }

    public boolean E() {
        return this.A() && this.i.isChecked();
    }

    public boolean F() {
        return this.d.getVisibility() == 0 && this.i.getVisibility() == 0;
    }

    public boolean G() {
        return this.e.getVisibility() == 0;
    }

    public void H(boolean bl) {
        this.t = bl;
        this.y0();
    }

    public void I() {
        this.w0();
        this.K();
        this.J();
        if (this.m().t()) {
            this.u0(this.c.d0());
        }
    }

    public void J() {
        com.google.android.material.textfield.t.d(this.c, this.i, this.m);
    }

    public void K() {
        com.google.android.material.textfield.t.d(this.c, this.e, this.f);
    }

    public void L(boolean bl) {
        boolean bl2;
        s s3 = this.m();
        boolean bl3 = s3.l();
        boolean bl4 = true;
        if (bl3 && (bl3 = this.i.isChecked()) != s3.m()) {
            this.i.setChecked(bl3 ^ true);
            bl2 = true;
        } else {
            bl2 = false;
        }
        if (s3.j() && (bl3 = this.i.isActivated()) != s3.k()) {
            this.N(bl3 ^ true);
            bl2 = bl4;
        }
        if (!bl && !bl2) {
            return;
        }
        this.J();
    }

    public final void M() {
        AccessibilityManager accessibilityManager;
        AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = this.w;
        if (touchExplorationStateChangeListener != null && (accessibilityManager = this.v) != null) {
            accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
        }
    }

    public void N(boolean bl) {
        this.i.setActivated(bl);
    }

    public void O(boolean bl) {
        this.i.setCheckable(bl);
    }

    public void P(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getResources().getText(n3) : null;
        this.Q(charSequence);
    }

    public void Q(CharSequence charSequence) {
        if (this.l() != charSequence) {
            this.i.setContentDescription(charSequence);
        }
    }

    public void R(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.S(drawable);
    }

    public void S(Drawable drawable) {
        this.i.setImageDrawable(drawable);
        if (drawable != null) {
            com.google.android.material.textfield.t.a(this.c, this.i, this.m, this.n);
            this.J();
        }
    }

    public void T(int n3) {
        if (n3 >= 0) {
            if (n3 != this.o) {
                this.o = n3;
                com.google.android.material.textfield.t.g(this.i, n3);
                com.google.android.material.textfield.t.g(this.e, n3);
            }
            return;
        }
        throw new IllegalArgumentException("endIconSize cannot be less than 0");
    }

    public void U(int n3) {
        if (this.k == n3) {
            return;
        }
        this.t0(this.m());
        int n4 = this.k;
        this.k = n3;
        this.j(n4);
        boolean bl = n3 != 0;
        this.a0(bl);
        Object object = this.m();
        this.R(this.t((s)object));
        this.P(((s)object).c());
        this.O(((s)object).l());
        if (((s)object).i(this.c.getBoxBackgroundMode())) {
            this.s0((s)object);
            this.V(((s)object).f());
            EditText editText = this.u;
            if (editText != null) {
                ((s)object).n(editText);
                this.h0((s)object);
            }
            com.google.android.material.textfield.t.a(this.c, this.i, this.m, this.n);
            this.L(true);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The current box background mode ");
        ((StringBuilder)object).append(this.c.getBoxBackgroundMode());
        ((StringBuilder)object).append(" is not supported by the end icon mode ");
        ((StringBuilder)object).append(n3);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void V(View.OnClickListener onClickListener) {
        com.google.android.material.textfield.t.h(this.i, onClickListener, this.q);
    }

    public void W(View.OnLongClickListener onLongClickListener) {
        this.q = onLongClickListener;
        com.google.android.material.textfield.t.i(this.i, onLongClickListener);
    }

    public void X(ImageView.ScaleType scaleType) {
        this.p = scaleType;
        com.google.android.material.textfield.t.j(this.i, scaleType);
        com.google.android.material.textfield.t.j(this.e, scaleType);
    }

    public void Y(ColorStateList colorStateList) {
        if (this.m != colorStateList) {
            this.m = colorStateList;
            com.google.android.material.textfield.t.a(this.c, this.i, colorStateList, this.n);
        }
    }

    public void Z(PorterDuff.Mode mode) {
        if (this.n != mode) {
            this.n = mode;
            com.google.android.material.textfield.t.a(this.c, this.i, this.m, mode);
        }
    }

    public void a0(boolean bl) {
        if (this.F() != bl) {
            CheckableImageButton checkableImageButton = this.i;
            int n3 = bl ? 0 : 8;
            checkableImageButton.setVisibility(n3);
            this.v0();
            this.x0();
            this.c.p0();
        }
    }

    public void b0(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.c0(drawable);
        this.K();
    }

    public void c0(Drawable drawable) {
        this.e.setImageDrawable(drawable);
        this.w0();
        com.google.android.material.textfield.t.a(this.c, this.e, this.f, this.g);
    }

    public void d0(View.OnClickListener onClickListener) {
        com.google.android.material.textfield.t.h(this.e, onClickListener, this.h);
    }

    public void e0(View.OnLongClickListener onLongClickListener) {
        this.h = onLongClickListener;
        com.google.android.material.textfield.t.i(this.e, onLongClickListener);
    }

    public void f0(ColorStateList colorStateList) {
        if (this.f != colorStateList) {
            this.f = colorStateList;
            com.google.android.material.textfield.t.a(this.c, this.e, colorStateList, this.g);
        }
    }

    public final void g() {
        if (this.w != null && this.v != null && this.isAttachedToWindow()) {
            this.v.addTouchExplorationStateChangeListener(this.w);
        }
    }

    public void g0(PorterDuff.Mode mode) {
        if (this.g != mode) {
            this.g = mode;
            com.google.android.material.textfield.t.a(this.c, this.e, this.f, mode);
        }
    }

    public void h() {
        this.i.performClick();
        this.i.jumpDrawablesToCurrentState();
    }

    public final void h0(s s3) {
        if (this.u != null) {
            if (s3.e() != null) {
                this.u.setOnFocusChangeListener(s3.e());
            }
            if (s3.g() != null) {
                this.i.setOnFocusChangeListener(s3.g());
            }
        }
    }

    public final CheckableImageButton i(ViewGroup object, LayoutInflater layoutInflater, int n3) {
        object = (CheckableImageButton)layoutInflater.inflate(z1.i.design_text_input_end_icon, object, false);
        object.setId(n3);
        com.google.android.material.textfield.t.e((CheckableImageButton)((Object)object));
        if (s2.c.k(this.getContext())) {
            ((ViewGroup.MarginLayoutParams)object.getLayoutParams()).setMarginStart(0);
        }
        return object;
    }

    public void i0(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getResources().getText(n3) : null;
        this.j0(charSequence);
    }

    public final void j(int n3) {
        Iterator iterator = ((AbstractCollection)this.l).iterator();
        if (!iterator.hasNext()) {
            return;
        }
        androidx.appcompat.app.s.a(iterator.next());
        throw null;
    }

    public void j0(CharSequence charSequence) {
        this.i.setContentDescription(charSequence);
    }

    public CheckableImageButton k() {
        if (this.G()) {
            return this.e;
        }
        if (this.A() && this.F()) {
            return this.i;
        }
        return null;
    }

    public void k0(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.getContext(), n3) : null;
        this.l0(drawable);
    }

    public CharSequence l() {
        return this.i.getContentDescription();
    }

    public void l0(Drawable drawable) {
        this.i.setImageDrawable(drawable);
    }

    public s m() {
        return this.j.c(this.k);
    }

    public void m0(boolean bl) {
        if (bl && this.k != 1) {
            this.U(1);
            return;
        }
        if (!bl) {
            this.U(0);
        }
    }

    public Drawable n() {
        return this.i.getDrawable();
    }

    public void n0(ColorStateList colorStateList) {
        this.m = colorStateList;
        com.google.android.material.textfield.t.a(this.c, this.i, colorStateList, this.n);
    }

    public int o() {
        return this.o;
    }

    public void o0(PorterDuff.Mode mode) {
        this.n = mode;
        com.google.android.material.textfield.t.a(this.c, this.i, this.m, mode);
    }

    public int p() {
        return this.k;
    }

    public void p0(CharSequence charSequence) {
        CharSequence charSequence2 = TextUtils.isEmpty((CharSequence)charSequence) ? null : charSequence;
        this.r = charSequence2;
        this.s.setText(charSequence);
        this.y0();
    }

    public ImageView.ScaleType q() {
        return this.p;
    }

    public void q0(int n3) {
        androidx.core.widget.j.m(this.s, n3);
    }

    public CheckableImageButton r() {
        return this.i;
    }

    public void r0(ColorStateList colorStateList) {
        this.s.setTextColor(colorStateList);
    }

    public Drawable s() {
        return this.e.getDrawable();
    }

    public final void s0(s s3) {
        s3.s();
        this.w = s3.h();
        this.g();
    }

    public final int t(s s3) {
        int n3 = this.j.c;
        if (n3 == 0) {
            return s3.d();
        }
        return n3;
    }

    public final void t0(s s3) {
        this.M();
        this.w = null;
        s3.u();
    }

    public CharSequence u() {
        return this.i.getContentDescription();
    }

    public final void u0(boolean bl) {
        if (bl && this.n() != null) {
            Drawable drawable = a.r(this.n()).mutate();
            drawable.setTint(this.c.getErrorCurrentTextColors());
            this.i.setImageDrawable(drawable);
            return;
        }
        com.google.android.material.textfield.t.a(this.c, this.i, this.m, this.n);
    }

    public Drawable v() {
        return this.i.getDrawable();
    }

    public final void v0() {
        FrameLayout frameLayout = this.d;
        int n3 = this.i.getVisibility();
        int n4 = 8;
        n3 = n3 == 0 && !this.G() ? 0 : 8;
        frameLayout.setVisibility(n3);
        n3 = this.r != null && !this.t ? 0 : 8;
        if (this.F() || this.G() || n3 == 0) {
            n4 = 0;
        }
        this.setVisibility(n4);
    }

    public CharSequence w() {
        return this.r;
    }

    public final void w0() {
        Object object = this.s();
        int n3 = 0;
        int n4 = object != null && this.c.O() && this.c.d0() ? 1 : 0;
        object = this.e;
        n4 = n4 != 0 ? n3 : 8;
        object.setVisibility(n4);
        this.v0();
        this.x0();
        if (!this.A()) {
            this.c.p0();
        }
    }

    public ColorStateList x() {
        return this.s.getTextColors();
    }

    public void x0() {
        if (this.c.g == null) {
            return;
        }
        int n3 = !this.F() && !this.G() ? this.c.g.getPaddingEnd() : 0;
        this.s.setPaddingRelative(this.getContext().getResources().getDimensionPixelSize(z1.e.material_input_text_to_prefix_suffix_padding), this.c.g.getPaddingTop(), n3, this.c.g.getPaddingBottom());
    }

    public int y() {
        int n3 = !this.F() && !this.G() ? 0 : this.i.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams)this.i.getLayoutParams()).getMarginStart();
        return this.getPaddingEnd() + this.s.getPaddingEnd() + n3;
    }

    public final void y0() {
        int n3 = this.s.getVisibility();
        Object object = this.r;
        boolean bl = false;
        int n4 = object != null && !this.t ? 0 : 8;
        if (n3 != n4) {
            object = this.m();
            if (n4 == 0) {
                bl = true;
            }
            ((s)object).q(bl);
        }
        this.v0();
        this.s.setVisibility(n4);
        this.c.p0();
    }

    public TextView z() {
        return this.s;
    }

    public static class d {
        public final SparseArray a = new SparseArray();
        public final r b;
        public final int c;
        public final int d;

        public d(r r3, m0 m02) {
            this.b = r3;
            this.c = m02.n(z1.m.TextInputLayout_endIconDrawable, 0);
            this.d = m02.n(z1.m.TextInputLayout_passwordToggleDrawable, 0);
        }

        public final s b(int n3) {
            if (n3 != -1) {
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 == 3) {
                                return new p(this.b);
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid end icon mode: ");
                            stringBuilder.append(n3);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        return new f(this.b);
                    }
                    return new x(this.b, this.d);
                }
                return new v(this.b);
            }
            return new g(this.b);
        }

        public s c(int n3) {
            s s3;
            s s4 = s3 = (s)this.a.get(n3);
            if (s3 == null) {
                s4 = this.b(n3);
                this.a.append(n3, (Object)s4);
            }
            return s4;
        }
    }
}

