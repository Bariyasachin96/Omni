/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.text.Editable
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.widget.EditText
 */
package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.textfield.a;
import com.google.android.material.textfield.b;
import com.google.android.material.textfield.c;
import com.google.android.material.textfield.d;
import com.google.android.material.textfield.e;
import com.google.android.material.textfield.r;
import com.google.android.material.textfield.s;
import z1.k;

public class f
extends s {
    public final int e;
    public final int f;
    public final TimeInterpolator g;
    public final TimeInterpolator h;
    public EditText i;
    public final View.OnClickListener j = new a(this);
    public final View.OnFocusChangeListener k = new b(this);
    public AnimatorSet l;
    public ValueAnimator m;

    public f(r r3) {
        super(r3);
        Context context = r3.getContext();
        int n3 = z1.c.motionDurationShort3;
        this.e = p2.k.f(context, n3, 100);
        this.f = p2.k.f(r3.getContext(), n3, 150);
        this.g = p2.k.g(r3.getContext(), z1.c.motionEasingLinearInterpolator, a2.a.a);
        this.h = p2.k.g(r3.getContext(), z1.c.motionEasingEmphasizedInterpolator, a2.a.d);
    }

    public static /* synthetic */ void v(f f3, View view) {
        view = f3.i;
        if (view == null) {
            return;
        }
        if ((view = view.getText()) != null) {
            view.clear();
        }
        f3.r();
    }

    public static /* synthetic */ void w(f f3, View view, boolean bl) {
        f3.A(f3.E());
    }

    public static /* synthetic */ void x(f f3, ValueAnimator valueAnimator) {
        f3.getClass();
        float f4 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        f3.d.setAlpha(f4);
    }

    public static /* synthetic */ void y(f f3, ValueAnimator valueAnimator) {
        f3.getClass();
        float f4 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        f3.d.setScaleX(f4);
        f3.d.setScaleY(f4);
    }

    public static /* synthetic */ void z(f f3) {
        f3.A(true);
    }

    public final void A(boolean bl) {
        boolean bl2 = this.b.F() == bl;
        if (bl && !this.l.isRunning()) {
            this.m.cancel();
            this.l.start();
            if (bl2) {
                this.l.end();
                return;
            }
        } else if (!bl) {
            this.l.cancel();
            this.m.start();
            if (bl2) {
                this.m.end();
            }
        }
    }

    public final ValueAnimator B(float ... object) {
        object = ValueAnimator.ofFloat((float[])object);
        object.setInterpolator(this.g);
        object.setDuration((long)this.e);
        object.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new c(this));
        return object;
    }

    public final ValueAnimator C() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.8f, 1.0f});
        valueAnimator.setInterpolator(this.h);
        valueAnimator.setDuration((long)this.f);
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new e(this));
        return valueAnimator;
    }

    public final void D() {
        AnimatorSet animatorSet;
        ValueAnimator valueAnimator = this.C();
        ValueAnimator valueAnimator2 = this.B(0.0f, 1.0f);
        this.l = animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{valueAnimator, valueAnimator2});
        this.l.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final f a;
            {
                this.a = f3;
            }

            public void onAnimationStart(Animator animator) {
                this.a.b.a0(true);
            }
        });
        animatorSet = this.B(1.0f, 0.0f);
        this.m = animatorSet;
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final f a;
            {
                this.a = f3;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.b.a0(false);
            }
        });
    }

    public final boolean E() {
        EditText editText = this.i;
        return editText != null && (editText.hasFocus() || this.d.hasFocus()) && this.i.getText().length() > 0;
    }

    @Override
    public void a(Editable editable) {
        if (this.b.w() != null) {
            return;
        }
        this.A(this.E());
    }

    @Override
    public int c() {
        return z1.k.clear_text_end_icon_content_description;
    }

    @Override
    public int d() {
        return z1.f.mtrl_ic_cancel;
    }

    @Override
    public View.OnFocusChangeListener e() {
        return this.k;
    }

    @Override
    public View.OnClickListener f() {
        return this.j;
    }

    @Override
    public View.OnFocusChangeListener g() {
        return this.k;
    }

    @Override
    public void n(EditText editText) {
        this.i = editText;
        this.a.setEndIconVisible(this.E());
    }

    @Override
    public void q(boolean bl) {
        if (this.b.w() == null) {
            return;
        }
        this.A(bl);
    }

    @Override
    public void s() {
        this.D();
    }

    @Override
    public void u() {
        EditText editText = this.i;
        if (editText != null) {
            editText.post((Runnable)new d(this));
        }
    }
}

