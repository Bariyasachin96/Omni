/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Typeface
 *  android.text.TextUtils
 *  android.util.Property
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.textfield;

import a2.a;
import a2.b;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.j;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;
import p2.k;
import s2.c;
import z1.e;
import z1.g;

public final class u {
    public ColorStateList A;
    public Typeface B;
    public final int a;
    public final int b;
    public final int c;
    public final TimeInterpolator d;
    public final TimeInterpolator e;
    public final TimeInterpolator f;
    public final Context g;
    public final TextInputLayout h;
    public LinearLayout i;
    public int j;
    public FrameLayout k;
    public Animator l;
    public final float m;
    public int n;
    public int o;
    public CharSequence p;
    public boolean q;
    public TextView r;
    public CharSequence s;
    public int t;
    public int u;
    public ColorStateList v;
    public CharSequence w;
    public boolean x;
    public TextView y;
    public int z;

    public u(TextInputLayout textInputLayout) {
        Context context;
        this.g = context = textInputLayout.getContext();
        this.h = textInputLayout;
        this.m = context.getResources().getDimensionPixelSize(z1.e.design_textinput_caption_translate_y);
        int n3 = z1.c.motionDurationShort4;
        this.a = p2.k.f(context, n3, 217);
        this.b = p2.k.f(context, z1.c.motionDurationMedium4, 167);
        this.c = p2.k.f(context, n3, 167);
        n3 = z1.c.motionEasingEmphasizedDecelerateInterpolator;
        this.d = p2.k.g(context, n3, a2.a.d);
        textInputLayout = a2.a.a;
        this.e = p2.k.g(context, n3, (TimeInterpolator)textInputLayout);
        this.f = p2.k.g(context, z1.c.motionEasingLinearInterpolator, (TimeInterpolator)textInputLayout);
    }

    public static /* synthetic */ int a(u u3, int n3) {
        u3.n = n3;
        return n3;
    }

    public static /* synthetic */ Animator b(u u3, Animator animator) {
        u3.l = animator;
        return animator;
    }

    public boolean A() {
        return this.q;
    }

    public boolean B() {
        return this.x;
    }

    public void C(TextView textView, int n3) {
        FrameLayout frameLayout;
        if (this.i == null) {
            return;
        }
        if (this.z(n3) && (frameLayout = this.k) != null) {
            frameLayout.removeView((View)textView);
        } else {
            this.i.removeView((View)textView);
        }
        this.j = n3 = this.j - 1;
        this.O((ViewGroup)this.i, n3);
    }

    public final void D(int n3, int n4) {
        TextView textView;
        if (n3 == n4) {
            return;
        }
        if (n4 != 0 && (textView = this.m(n4)) != null) {
            textView.setVisibility(0);
            textView.setAlpha(1.0f);
        }
        if (n3 != 0 && (textView = this.m(n3)) != null) {
            textView.setVisibility(4);
            if (n3 == 1) {
                textView.setText(null);
            }
        }
        this.n = n4;
    }

    public void E(int n3) {
        this.t = n3;
        TextView textView = this.r;
        if (textView != null) {
            textView.setAccessibilityLiveRegion(n3);
        }
    }

    public void F(CharSequence charSequence) {
        this.s = charSequence;
        TextView textView = this.r;
        if (textView != null) {
            textView.setContentDescription(charSequence);
        }
    }

    public void G(boolean bl) {
        if (this.q == bl) {
            return;
        }
        this.h();
        if (bl) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.g);
            this.r = appCompatTextView;
            appCompatTextView.setId(z1.g.textinput_error);
            this.r.setTextAlignment(5);
            appCompatTextView = this.B;
            if (appCompatTextView != null) {
                this.r.setTypeface((Typeface)appCompatTextView);
            }
            this.H(this.u);
            this.I(this.v);
            this.F(this.s);
            this.E(this.t);
            this.r.setVisibility(4);
            this.e(this.r, 0);
        } else {
            this.w();
            this.C(this.r, 0);
            this.r = null;
            this.h.q0();
            this.h.C0();
        }
        this.q = bl;
    }

    public void H(int n3) {
        this.u = n3;
        TextView textView = this.r;
        if (textView != null) {
            this.h.c0(textView, n3);
        }
    }

    public void I(ColorStateList colorStateList) {
        this.v = colorStateList;
        TextView textView = this.r;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void J(int n3) {
        this.z = n3;
        TextView textView = this.y;
        if (textView != null) {
            androidx.core.widget.j.m(textView, n3);
        }
    }

    public void K(boolean bl) {
        if (this.x == bl) {
            return;
        }
        this.h();
        if (bl) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(this.g);
            this.y = appCompatTextView;
            appCompatTextView.setId(z1.g.textinput_helper_text);
            this.y.setTextAlignment(5);
            appCompatTextView = this.B;
            if (appCompatTextView != null) {
                this.y.setTypeface((Typeface)appCompatTextView);
            }
            this.y.setVisibility(4);
            this.y.setAccessibilityLiveRegion(1);
            this.J(this.z);
            this.L(this.A);
            this.e(this.y, 1);
            this.y.setAccessibilityDelegate(new View.AccessibilityDelegate(this){
                public final u a;
                {
                    this.a = u3;
                }

                public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    view = this.a.h.getEditText();
                    if (view != null) {
                        accessibilityNodeInfo.setLabeledBy(view);
                    }
                }
            });
        } else {
            this.x();
            this.C(this.y, 1);
            this.y = null;
            this.h.q0();
            this.h.C0();
        }
        this.x = bl;
    }

    public void L(ColorStateList colorStateList) {
        this.A = colorStateList;
        TextView textView = this.y;
        if (textView != null && colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public final void M(TextView textView, Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }

    public void N(Typeface typeface) {
        if (typeface != this.B) {
            this.B = typeface;
            this.M(this.r, typeface);
            this.M(this.y, typeface);
        }
    }

    public final void O(ViewGroup viewGroup, int n3) {
        if (n3 == 0) {
            viewGroup.setVisibility(8);
        }
    }

    public final boolean P(TextView textView, CharSequence charSequence) {
        return this.h.isLaidOut() && this.h.isEnabled() && (this.o != this.n || textView == null || !TextUtils.equals((CharSequence)textView.getText(), (CharSequence)charSequence));
    }

    public void Q(CharSequence charSequence) {
        this.h();
        this.p = charSequence;
        this.r.setText(charSequence);
        int n3 = this.n;
        if (n3 != 1) {
            this.o = 1;
        }
        this.S(n3, this.o, this.P(this.r, charSequence));
    }

    public void R(CharSequence charSequence) {
        this.h();
        this.w = charSequence;
        this.y.setText(charSequence);
        int n3 = this.n;
        if (n3 != 2) {
            this.o = 2;
        }
        this.S(n3, this.o, this.P(this.y, charSequence));
    }

    public final void S(int n3, int n4, boolean bl) {
        if (n3 == n4) {
            return;
        }
        if (bl) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.l = animatorSet;
            ArrayList arrayList = new ArrayList();
            boolean bl2 = this.x;
            TextView textView = this.y;
            this.i(arrayList, bl2, textView, 2, n3, n4);
            this.i(arrayList, this.q, this.r, 1, n3, n4);
            a2.b.a(animatorSet, arrayList);
            animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, n4, this.m(n3), n3, this.m(n4)){
                public final int a;
                public final TextView b;
                public final int c;
                public final TextView d;
                public final u e;
                {
                    this.e = u3;
                    this.a = n3;
                    this.b = textView;
                    this.c = n4;
                    this.d = textView2;
                }

                public void onAnimationEnd(Animator animator) {
                    com.google.android.material.textfield.u.a(this.e, this.a);
                    com.google.android.material.textfield.u.b(this.e, null);
                    animator = this.b;
                    if (animator != null) {
                        animator.setVisibility(4);
                        if (this.c == 1 && this.e.r != null) {
                            this.e.r.setText(null);
                        }
                    }
                    if ((animator = this.d) != null) {
                        animator.setTranslationY(0.0f);
                        this.d.setAlpha(1.0f);
                    }
                }

                public void onAnimationStart(Animator animator) {
                    animator = this.d;
                    if (animator != null) {
                        animator.setVisibility(0);
                        this.d.setAlpha(0.0f);
                    }
                }
            });
            animatorSet.start();
        } else {
            this.D(n3, n4);
        }
        this.h.q0();
        this.h.w0(bl);
        this.h.C0();
    }

    public void e(TextView textView, int n3) {
        LinearLayout linearLayout;
        if (this.i == null && this.k == null) {
            this.i = linearLayout = new LinearLayout(this.g);
            linearLayout.setOrientation(0);
            this.h.addView((View)this.i, -1, -2);
            this.k = new FrameLayout(this.g);
            linearLayout = new LinearLayout.LayoutParams(0, -2, 1.0f);
            this.i.addView((View)this.k, (ViewGroup.LayoutParams)linearLayout);
            if (this.h.getEditText() != null) {
                this.f();
            }
        }
        if (this.z(n3)) {
            this.k.setVisibility(0);
            this.k.addView((View)textView);
        } else {
            linearLayout = new LinearLayout.LayoutParams(-2, -2);
            this.i.addView((View)textView, (ViewGroup.LayoutParams)linearLayout);
        }
        this.i.setVisibility(0);
        ++this.j;
    }

    public void f() {
        if (this.g()) {
            EditText editText = this.h.getEditText();
            boolean bl = s2.c.k(this.g);
            LinearLayout linearLayout = this.i;
            int n3 = z1.e.material_helper_text_font_1_3_padding_horizontal;
            linearLayout.setPaddingRelative(this.v(bl, n3, editText.getPaddingStart()), this.v(bl, z1.e.material_helper_text_font_1_3_padding_top, this.g.getResources().getDimensionPixelSize(z1.e.material_helper_text_default_padding_top)), this.v(bl, n3, editText.getPaddingEnd()), 0);
        }
    }

    public final boolean g() {
        return this.i != null && this.h.getEditText() != null;
    }

    public void h() {
        Animator animator = this.l;
        if (animator != null) {
            animator.cancel();
        }
    }

    public final void i(List list, boolean bl, TextView textView, int n3, int n4, int n5) {
        if (textView != null && bl) {
            if (n3 != n5 && n3 != n4) {
                return;
            }
            bl = n5 == n3;
            ObjectAnimator objectAnimator = this.j(textView, bl);
            if (n3 == n5 && n4 != 0) {
                objectAnimator.setStartDelay((long)this.c);
            }
            list.add(objectAnimator);
            if (n5 == n3 && n4 != 0) {
                textView = this.k(textView);
                textView.setStartDelay((long)this.c);
                list.add(textView);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final ObjectAnimator j(TextView textView, boolean bl) {
        float f3 = bl ? 1.0f : 0.0f;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)textView, (Property)View.ALPHA, (float[])new float[]{f3});
        int n3 = bl ? this.b : this.c;
        long l3 = n3;
        objectAnimator.setDuration(l3);
        textView = bl ? this.e : this.f;
        objectAnimator.setInterpolator((TimeInterpolator)textView);
        return objectAnimator;
    }

    public final ObjectAnimator k(TextView textView) {
        textView = ObjectAnimator.ofFloat((Object)textView, (Property)View.TRANSLATION_Y, (float[])new float[]{-this.m, 0.0f});
        textView.setDuration((long)this.a);
        textView.setInterpolator(this.d);
        return textView;
    }

    public boolean l() {
        return this.y(this.o);
    }

    public final TextView m(int n3) {
        if (n3 != 1) {
            if (n3 != 2) {
                return null;
            }
            return this.y;
        }
        return this.r;
    }

    public int n() {
        return this.t;
    }

    public CharSequence o() {
        return this.s;
    }

    public CharSequence p() {
        return this.p;
    }

    public int q() {
        TextView textView = this.r;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    public ColorStateList r() {
        TextView textView = this.r;
        if (textView != null) {
            return textView.getTextColors();
        }
        return null;
    }

    public CharSequence s() {
        return this.w;
    }

    public View t() {
        return this.y;
    }

    public int u() {
        TextView textView = this.y;
        if (textView != null) {
            return textView.getCurrentTextColor();
        }
        return -1;
    }

    public final int v(boolean bl, int n3, int n4) {
        if (bl) {
            return this.g.getResources().getDimensionPixelSize(n3);
        }
        return n4;
    }

    public void w() {
        this.p = null;
        this.h();
        if (this.n == 1) {
            this.o = this.x && !TextUtils.isEmpty((CharSequence)this.w) ? 2 : 0;
        }
        this.S(this.n, this.o, this.P(this.r, ""));
    }

    public void x() {
        this.h();
        int n3 = this.n;
        if (n3 == 2) {
            this.o = 0;
        }
        this.S(n3, this.o, this.P(this.y, ""));
    }

    public final boolean y(int n3) {
        return n3 == 1 && this.r != null && !TextUtils.isEmpty((CharSequence)this.p);
    }

    public boolean z(int n3) {
        return n3 == 0 || n3 == 1;
        {
        }
    }
}

