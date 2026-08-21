/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.os.SystemClock
 *  android.text.Editable
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnTouchListener
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.AutoCompleteTextView$OnDismissListener
 *  android.widget.EditText
 *  android.widget.Spinner
 */
package com.google.android.material.textfield;

import a2.a;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.material.textfield.i;
import com.google.android.material.textfield.j;
import com.google.android.material.textfield.k;
import com.google.android.material.textfield.l;
import com.google.android.material.textfield.m;
import com.google.android.material.textfield.n;
import com.google.android.material.textfield.o;
import com.google.android.material.textfield.q;
import com.google.android.material.textfield.r;
import com.google.android.material.textfield.s;
import z1.c;
import z1.f;

public class p
extends s {
    public final int e;
    public final int f;
    public final TimeInterpolator g;
    public AutoCompleteTextView h;
    public final View.OnClickListener i = new l(this);
    public final View.OnFocusChangeListener j = new m(this);
    public final AccessibilityManager.TouchExplorationStateChangeListener k = new n(this);
    public boolean l;
    public boolean m;
    public boolean n;
    public long o = Long.MAX_VALUE;
    public AccessibilityManager p;
    public ValueAnimator q;
    public ValueAnimator r;

    public p(r r3) {
        super(r3);
        Context context = r3.getContext();
        int n3 = z1.c.motionDurationShort3;
        this.f = p2.k.f(context, n3, 67);
        this.e = p2.k.f(r3.getContext(), n3, 50);
        this.g = p2.k.g(r3.getContext(), z1.c.motionEasingLinearInterpolator, a2.a.a);
    }

    public static /* synthetic */ void A(p p3) {
        p3.K();
        p3.H(false);
    }

    public static /* synthetic */ void B(p p3, View view) {
        p3.J();
    }

    public static AutoCompleteTextView D(EditText editText) {
        if (editText instanceof AutoCompleteTextView) {
            return (AutoCompleteTextView)editText;
        }
        throw new RuntimeException("EditText needs to be an AutoCompleteTextView if an Exposed Dropdown Menu is being used.");
    }

    private void F() {
        ValueAnimator valueAnimator;
        this.r = this.E(this.f, 0.0f, 1.0f);
        this.q = valueAnimator = this.E(this.e, 1.0f, 0.0f);
        valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final p a;
            {
                this.a = p3;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.r();
                this.a.r.start();
            }
        });
    }

    public static /* synthetic */ void v(p p3) {
        boolean bl = p3.h.isPopupShowing();
        p3.H(bl);
        p3.m = bl;
    }

    public static /* synthetic */ void w(p object, boolean bl) {
        AutoCompleteTextView autoCompleteTextView = object.h;
        if (autoCompleteTextView != null && !com.google.android.material.textfield.q.a((EditText)autoCompleteTextView)) {
            object = object.d;
            int n3 = bl ? 2 : 1;
            object.setImportantForAccessibility(n3);
        }
    }

    public static /* synthetic */ void x(p p3, ValueAnimator valueAnimator) {
        p3.getClass();
        float f3 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        p3.d.setAlpha(f3);
    }

    public static /* synthetic */ void y(p p3, View view, boolean bl) {
        p3.l = bl;
        p3.r();
        if (!bl) {
            p3.H(false);
            p3.m = false;
        }
    }

    public static /* synthetic */ boolean z(p p3, View view, MotionEvent motionEvent) {
        p3.getClass();
        if (motionEvent.getAction() == 1) {
            if (p3.G()) {
                p3.m = false;
            }
            p3.J();
            p3.K();
        }
        return false;
    }

    public final ValueAnimator E(int n3, float ... object) {
        object = ValueAnimator.ofFloat((float[])object);
        object.setInterpolator(this.g);
        object.setDuration((long)n3);
        object.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new i(this));
        return object;
    }

    public final boolean G() {
        long l3 = SystemClock.uptimeMillis() - this.o;
        return l3 < 0L || l3 > 300L;
        {
        }
    }

    public final void H(boolean bl) {
        if (this.n != bl) {
            this.n = bl;
            this.r.cancel();
            this.q.start();
        }
    }

    public final void I() {
        this.h.setOnTouchListener((View.OnTouchListener)new j(this));
        this.h.setOnDismissListener((AutoCompleteTextView.OnDismissListener)new k(this));
        this.h.setThreshold(0);
    }

    public final void J() {
        if (this.h == null) {
            return;
        }
        if (this.G()) {
            this.m = false;
        }
        if (!this.m) {
            this.H(this.n ^ true);
            if (this.n) {
                this.h.requestFocus();
                this.h.showDropDown();
                return;
            }
            this.h.dismissDropDown();
            return;
        }
        this.m = false;
    }

    public final void K() {
        this.m = true;
        this.o = SystemClock.uptimeMillis();
    }

    @Override
    public void a(Editable editable) {
        if (this.p.isTouchExplorationEnabled() && com.google.android.material.textfield.q.a((EditText)this.h) && !this.d.hasFocus()) {
            this.h.dismissDropDown();
        }
        this.h.post((Runnable)new o(this));
    }

    @Override
    public int c() {
        return z1.k.exposed_dropdown_menu_content_description;
    }

    @Override
    public int d() {
        return z1.f.mtrl_dropdown_arrow;
    }

    @Override
    public View.OnFocusChangeListener e() {
        return this.j;
    }

    @Override
    public View.OnClickListener f() {
        return this.i;
    }

    @Override
    public AccessibilityManager.TouchExplorationStateChangeListener h() {
        return this.k;
    }

    @Override
    public boolean i(int n3) {
        return n3 != 0;
    }

    @Override
    public boolean j() {
        return true;
    }

    @Override
    public boolean k() {
        return this.l;
    }

    @Override
    public boolean l() {
        return true;
    }

    @Override
    public boolean m() {
        return this.n;
    }

    @Override
    public void n(EditText editText) {
        this.h = com.google.android.material.textfield.p.D(editText);
        this.I();
        this.a.setErrorIconDrawable(null);
        if (!com.google.android.material.textfield.q.a(editText) && this.p.isTouchExplorationEnabled()) {
            this.d.setImportantForAccessibility(2);
        }
        this.a.setEndIconVisible(true);
    }

    @Override
    public void o(View view, p0.s s3) {
        if (!com.google.android.material.textfield.q.a((EditText)this.h)) {
            s3.h0(Spinner.class.getName());
        }
        if (s3.R()) {
            s3.r0(null);
        }
    }

    @Override
    public void p(View view, AccessibilityEvent accessibilityEvent) {
        if (this.p.isEnabled() && !com.google.android.material.textfield.q.a((EditText)this.h)) {
            boolean bl = (accessibilityEvent.getEventType() == 32768 || accessibilityEvent.getEventType() == 8) && this.n && !this.h.isPopupShowing();
            if (accessibilityEvent.getEventType() == 1 || bl) {
                this.J();
                this.K();
            }
        }
    }

    @Override
    public void s() {
        this.F();
        this.p = (AccessibilityManager)this.c.getSystemService("accessibility");
    }

    @Override
    public boolean t() {
        return true;
    }

    @Override
    public void u() {
        AutoCompleteTextView autoCompleteTextView = this.h;
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setOnTouchListener(null);
            this.h.setOnDismissListener(null);
        }
    }
}

