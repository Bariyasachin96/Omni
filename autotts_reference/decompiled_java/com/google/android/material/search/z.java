/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.TimeInterpolator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.view.Menu
 *  android.view.View
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.EditText
 *  android.widget.FrameLayout
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.a0;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.f;
import com.google.android.material.internal.g;
import com.google.android.material.internal.o;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.android.material.search.q;
import com.google.android.material.search.r;
import com.google.android.material.search.s;
import com.google.android.material.search.t;
import com.google.android.material.search.u;
import com.google.android.material.search.v;
import com.google.android.material.search.w;
import com.google.android.material.search.x;
import com.google.android.material.search.y;
import e.b;
import h0.a;
import java.util.Objects;
import p2.i;

public class z {
    public final SearchView a;
    public final View b;
    public final ClippableRoundedCornerLayout c;
    public final FrameLayout d;
    public final FrameLayout e;
    public final Toolbar f;
    public final Toolbar g;
    public final LinearLayout h;
    public final TextView i;
    public final EditText j;
    public final ImageButton k;
    public final View l;
    public final TouchObserverFrameLayout m;
    public final i n;
    public AnimatorSet o;
    public SearchBar p;

    public z(SearchView searchView) {
        ClippableRoundedCornerLayout clippableRoundedCornerLayout;
        this.a = searchView;
        this.b = searchView.c;
        this.c = clippableRoundedCornerLayout = searchView.d;
        this.d = searchView.g;
        this.e = searchView.h;
        this.f = searchView.i;
        this.g = searchView.j;
        this.i = searchView.k;
        this.j = searchView.m;
        this.k = searchView.n;
        this.l = searchView.o;
        this.m = searchView.p;
        this.h = searchView.l;
        this.n = new i((View)clippableRoundedCornerLayout);
    }

    public static float[] Z(float f3, float[] fArray, float f4) {
        return new float[]{a2.a.a(f3, fArray[0], f4), a2.a.a(f3, fArray[1], f4), a2.a.a(f3, fArray[2], f4), a2.a.a(f3, fArray[3], f4), a2.a.a(f3, fArray[4], f4), a2.a.a(f3, fArray[5], f4), a2.a.a(f3, fArray[6], f4), a2.a.a(f3, fArray[7], f4)};
    }

    public static /* synthetic */ void a(z z3) {
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = z3.c;
        clippableRoundedCornerLayout.setTranslationY(clippableRoundedCornerLayout.getHeight());
        clippableRoundedCornerLayout = z3.R(true);
        clippableRoundedCornerLayout.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(z3){
            public final z a;
            {
                this.a = z3;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.a.a.s()) {
                    this.a.a.z();
                }
                this.a.a.setTransitionState(SearchView.b.f);
            }

            public void onAnimationStart(Animator animator) {
                this.a.c.setVisibility(0);
                this.a.a.setTransitionState(SearchView.b.e);
            }
        });
        clippableRoundedCornerLayout.start();
    }

    public static float[] a0(float[] fArray, float[] fArray2) {
        return new float[]{Math.max(fArray[0], fArray2[0]), Math.max(fArray[1], fArray2[1]), Math.max(fArray[2], fArray2[2]), Math.max(fArray[3], fArray2[3]), Math.max(fArray[4], fArray2[4]), Math.max(fArray[5], fArray2[5]), Math.max(fArray[6], fArray2[6]), Math.max(fArray[7], fArray2[7])};
    }

    public static /* synthetic */ void b(z z3, ValueAnimator valueAnimator) {
        z3.j.setAlpha(((Float)valueAnimator.getAnimatedValue()).floatValue());
        z3.p.getTextView().setAlpha(1.0f - ((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public static /* synthetic */ void c(z z3, Rect rect, ValueAnimator valueAnimator) {
        z3.getClass();
        rect.right = (Integer)valueAnimator.getAnimatedValue();
        z3.j.setClipBounds(rect);
    }

    public static /* synthetic */ void d(z z3) {
        AnimatorSet animatorSet = z3.J(true);
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(z3){
            public final z a;
            {
                this.a = z3;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.a.a.s()) {
                    this.a.a.z();
                }
                this.a.a.setTransitionState(SearchView.b.f);
            }

            public void onAnimationStart(Animator animator) {
                this.a.c.setVisibility(0);
                this.a.p.i0();
            }
        });
        animatorSet.start();
    }

    public static /* synthetic */ void e(f f3, ValueAnimator valueAnimator) {
        f3.a(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public static /* synthetic */ void f(ImageButton imageButton, ValueAnimator valueAnimator) {
        imageButton.setAlpha(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public static /* synthetic */ void g(z z3, float f3, float[] fArray, Rect rect, ValueAnimator valueAnimator) {
        z3.getClass();
        fArray = z.Z(f3, fArray, valueAnimator.getAnimatedFraction());
        z3.c.c(rect, fArray);
    }

    public static /* synthetic */ void h(b b3, ValueAnimator valueAnimator) {
        b3.e(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public final AnimatorSet A(boolean bl) {
        AnimatorSet animatorSet = new AnimatorSet();
        this.q(animatorSet);
        long l3 = bl ? 300L : 250L;
        animatorSet.setDuration(l3);
        animatorSet.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        return animatorSet;
    }

    public final AnimatorSet B(boolean bl) {
        AnimatorSet animatorSet = new AnimatorSet();
        this.r(animatorSet);
        this.o(animatorSet);
        long l3 = bl ? 300L : 250L;
        animatorSet.setDuration(l3);
        animatorSet.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        return animatorSet;
    }

    public final Animator C(boolean bl) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        long l3 = bl ? 50L : 42L;
        valueAnimator.setDuration(l3);
        l3 = bl ? 250L : 0L;
        valueAnimator.setStartDelay(l3);
        valueAnimator.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.a));
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.e(new View[]{this.k}));
        return valueAnimator;
    }

    public final Animator D(boolean bl) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        long l3 = bl ? 150L : 83L;
        valueAnimator.setDuration(l3);
        l3 = bl ? 75L : 0L;
        valueAnimator.setStartDelay(l3);
        valueAnimator.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.a));
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.e(new View[]{this.l, this.m}));
        return valueAnimator;
    }

    public final Animator E(boolean bl) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{this.D(bl), this.G(bl), this.F(bl)});
        return animatorSet;
    }

    public final Animator F(boolean bl) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.95f, 1.0f});
        long l3 = bl ? 300L : 250L;
        valueAnimator.setDuration(l3);
        valueAnimator.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.f(new View[]{this.m}));
        return valueAnimator;
    }

    public final Animator G(boolean bl) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{(float)this.m.getHeight() * 0.050000012f / 2.0f, 0.0f});
        long l3 = bl ? 300L : 250L;
        valueAnimator.setDuration(l3);
        valueAnimator.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.l(this.l));
        return valueAnimator;
    }

    public final Animator H(boolean bl) {
        Toolbar toolbar = this.g;
        return this.S(bl, (View)toolbar, this.K((View)toolbar), this.L());
    }

    public final Animator I(boolean bl) {
        return this.T(bl, (View)this.j);
    }

    public final AnimatorSet J(boolean bl) {
        AnimatorSet animatorSet = new AnimatorSet();
        if (this.o == null) {
            animatorSet.playTogether(new Animator[]{this.A(bl), this.B(bl)});
        }
        animatorSet.playTogether(new Animator[]{this.O(bl), this.N(bl), this.C(bl), this.E(bl), this.M(bl), this.H(bl), this.y(bl), this.I(bl), this.P(bl), this.Q(bl)});
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, bl){
            public final boolean a;
            public final z b;
            {
                this.b = z3;
                this.a = bl;
            }

            public void onAnimationEnd(Animator object) {
                object = this.b;
                float f3 = this.a ? 1.0f : 0.0f;
                ((z)object).d0(f3);
                this.b.j.setAlpha(1.0f);
                if (this.b.p != null) {
                    this.b.p.getTextView().setAlpha(1.0f);
                }
                this.b.j.setClipBounds(null);
                this.b.c.a();
                if (!this.a) {
                    this.b.n.j();
                }
            }

            public void onAnimationStart(Animator object) {
                object = this.b;
                float f3 = this.a ? 0.0f : 1.0f;
                ((z)object).d0(f3);
            }
        });
        return animatorSet;
    }

    public final int K(View view) {
        int n3 = ((ViewGroup.MarginLayoutParams)view.getLayoutParams()).getMarginEnd();
        int n4 = this.W((View)this.p);
        if (c0.m((View)this.p)) {
            return n4 - n3;
        }
        return n4 + this.p.getWidth() + n3 - this.a.getWidth();
    }

    public final int L() {
        int n3 = this.e.getTop();
        int n4 = this.e.getHeight() / 2;
        return this.X((View)this.p) + this.p.getHeight() / 2 - (n3 + n4);
    }

    public final Animator M(boolean bl) {
        FrameLayout frameLayout = this.d;
        return this.S(bl, (View)frameLayout, this.K((View)frameLayout), this.L());
    }

    public final Animator N(boolean bl) {
        Rect rect = this.n.p();
        Rect rect2 = this.n.o();
        if (rect == null) {
            rect = c0.c((View)this.a);
        }
        if (rect2 == null) {
            rect2 = c0.b((View)this.c, (View)this.p);
        }
        Rect rect3 = new Rect(rect2);
        float f3 = this.p.getCornerSize();
        float[] fArray = z.a0(this.c.getCornerRadii(), this.n.n());
        rect = ValueAnimator.ofObject((TypeEvaluator)new com.google.android.material.internal.s(rect3), (Object[])new Object[]{rect2, rect});
        rect.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new r(this, f3, fArray, rect3));
        long l3 = bl ? 300L : 250L;
        rect.setDuration(l3);
        rect.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        return rect;
    }

    public final Animator O(boolean bl) {
        TimeInterpolator timeInterpolator = bl ? a2.a.a : a2.a.b;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        long l3 = bl ? 300L : 250L;
        valueAnimator.setDuration(l3);
        l3 = bl ? 100L : 0L;
        valueAnimator.setStartDelay(l3);
        valueAnimator.setInterpolator(com.google.android.material.internal.t.a(bl, timeInterpolator));
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.e(this.b));
        return valueAnimator;
    }

    public final Animator P(boolean bl) {
        return this.T(bl, (View)this.i);
    }

    public final AnimatorSet Q(boolean bl) {
        AnimatorSet animatorSet = new AnimatorSet();
        this.v(animatorSet);
        this.t(animatorSet);
        long l3 = bl ? 300L : 250L;
        animatorSet.setDuration(l3);
        animatorSet.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.a));
        return animatorSet;
    }

    public final AnimatorSet R(boolean bl) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{this.V()});
        this.q(animatorSet);
        animatorSet.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        long l3 = bl ? 350L : 300L;
        animatorSet.setDuration(l3);
        return animatorSet;
    }

    public final Animator S(boolean bl, View view, int n3, int n4) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{n3, 0.0f});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.k(view));
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat((float[])new float[]{n4, 0.0f});
        valueAnimator2.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.l(view));
        view = new AnimatorSet();
        view.playTogether(new Animator[]{valueAnimator, valueAnimator2});
        long l3 = bl ? 300L : 250L;
        view.setDuration(l3);
        view.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        return view;
    }

    public final Animator T(boolean bl, View view) {
        TextView textView = this.p.getPlaceholderTextView();
        if (TextUtils.isEmpty((CharSequence)textView.getText()) || bl) {
            textView = this.p.getTextView();
        }
        return this.S(bl, view, this.W((View)textView) - (view.getLeft() + this.h.getLeft()), this.L());
    }

    public final int U(View view, View view2) {
        if (view == null) {
            int n3 = ((ViewGroup.MarginLayoutParams)view2.getLayoutParams()).getMarginStart();
            int n4 = this.p.getPaddingStart();
            int n5 = this.W((View)this.p);
            if (c0.m((View)this.p)) {
                return n5 + this.p.getWidth() + n3 - n4 - this.a.getRight();
            }
            return n5 - n3 + n4;
        }
        return this.W(view) - this.W(view2);
    }

    public final Animator V() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.c.getHeight(), 0.0f});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.l(new View[]{this.c}));
        return valueAnimator;
    }

    public final int W(View view) {
        int n3 = view.getLeft();
        for (view = view.getParent(); view instanceof View && view != this.a.getParent(); view = view.getParent()) {
            n3 += view.getLeft();
        }
        return n3;
    }

    public final int X(View view) {
        int n3 = view.getTop();
        for (view = view.getParent(); view instanceof View && view != this.a.getParent(); view = view.getParent()) {
            n3 += view.getTop();
        }
        return n3;
    }

    public AnimatorSet Y() {
        if (this.p != null) {
            return this.k0();
        }
        return this.l0();
    }

    public androidx.activity.b b0() {
        return this.n.c();
    }

    public final void c0(float f3) {
        ActionMenuView actionMenuView;
        if (this.a.v() && (actionMenuView = a0.a(this.f)) != null) {
            actionMenuView.setAlpha(f3);
        }
    }

    public final void d0(float f3) {
        this.k.setAlpha(f3);
        this.l.setAlpha(f3);
        this.m.setAlpha(f3);
        this.c0(f3);
    }

    public final void e0(Drawable drawable) {
        if (drawable instanceof b) {
            ((b)drawable).e(1.0f);
        }
        if (drawable instanceof f) {
            ((f)drawable).a(1.0f);
        }
    }

    public final void f0(Toolbar viewGroup) {
        if ((viewGroup = a0.a(viewGroup)) != null) {
            for (int i3 = 0; i3 < viewGroup.getChildCount(); ++i3) {
                View view = viewGroup.getChildAt(i3);
                view.setClickable(false);
                view.setFocusable(false);
                view.setFocusableInTouchMode(false);
            }
        }
    }

    public void g0(SearchBar searchBar) {
        this.p = searchBar;
    }

    public final void h0() {
        Menu menu = this.g.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (this.p.getMenuResId() != -1 && this.a.v()) {
            this.g.z(this.p.getMenuResId());
            this.f0(this.g);
            this.g.setVisibility(0);
            return;
        }
        this.g.setVisibility(8);
    }

    public void i0() {
        if (this.p != null) {
            this.m0();
            return;
        }
        this.n0();
    }

    public void j0(androidx.activity.b b3) {
        this.n.v(b3, (View)this.p);
    }

    public final AnimatorSet k0() {
        if (this.a.s()) {
            this.a.p();
        }
        AnimatorSet animatorSet = this.J(false);
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final z a;
            {
                this.a = z3;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.c.setVisibility(8);
                if (!this.a.a.s()) {
                    this.a.a.p();
                }
                this.a.a.setTransitionState(SearchView.b.d);
            }

            public void onAnimationStart(Animator animator) {
                this.a.a.setTransitionState(SearchView.b.c);
            }
        });
        animatorSet.start();
        return animatorSet;
    }

    public final AnimatorSet l0() {
        if (this.a.s()) {
            this.a.p();
        }
        AnimatorSet animatorSet = this.R(false);
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final z a;
            {
                this.a = z3;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.c.setVisibility(8);
                if (!this.a.a.s()) {
                    this.a.a.p();
                }
                this.a.a.setTransitionState(SearchView.b.d);
            }

            public void onAnimationStart(Animator animator) {
                this.a.a.setTransitionState(SearchView.b.c);
            }
        });
        animatorSet.start();
        return animatorSet;
    }

    public final void m0() {
        if (this.a.s()) {
            this.a.z();
        }
        this.a.setTransitionState(SearchView.b.e);
        this.h0();
        this.j.setText(this.p.getText());
        EditText editText = this.j;
        editText.setSelection(editText.getText().length());
        this.c.setVisibility(4);
        this.c.post(new t(this));
    }

    public final void n0() {
        if (this.a.s()) {
            SearchView searchView = this.a;
            Objects.requireNonNull(searchView);
            searchView.postDelayed(new w(searchView), 150L);
        }
        this.c.setVisibility(4);
        this.c.post(new x(this));
    }

    public final void o(AnimatorSet animatorSet) {
        ActionMenuView actionMenuView = a0.a(this.f);
        if (actionMenuView == null) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.U((View)a0.a(this.p), (View)actionMenuView), 0.0f});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.k(new View[]{actionMenuView}));
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat((float[])new float[]{this.L(), 0.0f});
        valueAnimator2.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.l(new View[]{actionMenuView}));
        animatorSet.playTogether(new Animator[]{valueAnimator, valueAnimator2});
    }

    public void o0(androidx.activity.b b3) {
        i i3;
        block5: {
            block6: {
                block4: {
                    if (b3.a() <= 0.0f) break block4;
                    i3 = this.n;
                    SearchBar searchBar = this.p;
                    i3.x(b3, (View)searchBar, searchBar.getCornerSize());
                    i3 = this.o;
                    if (i3 != null) break block5;
                    if (this.a.s()) {
                        this.a.p();
                    }
                    if (this.a.t()) break block6;
                }
                return;
            }
            b3 = this.A(false);
            this.o = b3;
            b3.start();
            this.o.pause();
            return;
        }
        i3.setCurrentPlayTime((long)(b3.a() * (float)this.o.getDuration()));
    }

    public final void p(AnimatorSet animatorSet, ImageButton imageButton) {
        SearchBar searchBar = this.p;
        if (searchBar != null && searchBar.getNavigationIcon() == null) {
            searchBar = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
            searchBar.addUpdateListener(new y(imageButton));
            animatorSet.playTogether(new Animator[]{searchBar});
        }
    }

    public final void q(AnimatorSet animatorSet) {
        ImageButton imageButton = a0.d(this.f);
        if (imageButton == null) {
            return;
        }
        Drawable drawable = h0.a.q(imageButton.getDrawable());
        if (this.a.t()) {
            this.s(animatorSet, drawable);
            this.u(animatorSet, drawable);
            this.p(animatorSet, imageButton);
            return;
        }
        this.e0(drawable);
    }

    public final void r(AnimatorSet animatorSet) {
        ImageButton imageButton = a0.d(this.f);
        if (imageButton == null) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.U((View)a0.d(this.p), (View)imageButton), 0.0f});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.k(new View[]{imageButton}));
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat((float[])new float[]{this.L(), 0.0f});
        valueAnimator2.addUpdateListener((ValueAnimator.AnimatorUpdateListener)com.google.android.material.internal.o.l(new View[]{imageButton}));
        animatorSet.playTogether(new Animator[]{valueAnimator, valueAnimator2});
    }

    public final void s(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof b) {
            b b3 = (b)drawable;
            drawable = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
            drawable.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new s(b3));
            animatorSet.playTogether(new Animator[]{drawable});
        }
    }

    public final void t(AnimatorSet animatorSet) {
        if (this.p != null && TextUtils.equals((CharSequence)this.j.getText(), (CharSequence)this.p.getText())) {
            Rect rect = new Rect(0, 0, this.j.getWidth(), this.j.getHeight());
            ValueAnimator valueAnimator = ValueAnimator.ofInt((int[])new int[]{this.p.getTextView().getWidth(), this.j.getWidth()});
            valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new q(this, rect));
            animatorSet.playTogether(new Animator[]{valueAnimator});
        }
    }

    public final void u(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof f) {
            f f3 = (f)drawable;
            drawable = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
            drawable.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new u(f3));
            animatorSet.playTogether(new Animator[]{drawable});
        }
    }

    public final void v(AnimatorSet animatorSet) {
        if (this.p != null && !TextUtils.equals((CharSequence)this.j.getText(), (CharSequence)this.p.getText())) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
            valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new v(this));
            animatorSet.playTogether(new Animator[]{valueAnimator});
        }
    }

    public void w() {
        this.n.i((View)this.p);
        AnimatorSet animatorSet = this.o;
        if (animatorSet != null) {
            animatorSet.reverse();
        }
        this.o = null;
    }

    public void x() {
        long l3 = this.Y().getTotalDuration();
        this.n.m(l3, (View)this.p);
        if (this.o != null) {
            this.B(false).start();
            this.o.resume();
        }
        this.o = null;
    }

    public final Animator y(boolean bl) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        long l3 = bl ? 300L : 250L;
        valueAnimator.setDuration(l3);
        valueAnimator.setInterpolator(com.google.android.material.internal.t.a(bl, a2.a.b));
        if (this.a.v()) {
            valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new g((View)a0.a(this.g), (View)a0.a(this.f)));
        }
        return valueAnimator;
    }

    public i z() {
        return this.n;
    }
}

