/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.FloatEvaluator
 *  android.animation.ObjectAnimator
 *  android.animation.StateListAnimator
 *  android.animation.TimeInterpolator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Matrix
 *  android.graphics.Matrix$ScaleToFit
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Property;
import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.VisibilityAwareImageButton;
import java.util.ArrayList;
import m2.c;
import m2.d;
import n0.h;
import p2.k;
import u2.b;
import v2.i;
import v2.j;
import v2.o;
import v2.r;

public class a {
    public static final TimeInterpolator C = a2.a.c;
    public static final int D = z1.c.motionDurationLong2;
    public static final int E = z1.c.motionEasingEmphasizedInterpolator;
    public static final int F = z1.c.motionDurationMedium1;
    public static final int G = z1.c.motionEasingEmphasizedAccelerateInterpolator;
    public static final int[] H = new int[]{16842919, 16842910};
    public static final int[] I = new int[]{16843623, 16842908, 16842910};
    public static final int[] J = new int[]{16842908, 16842910};
    public static final int[] K = new int[]{16843623, 16842910};
    public static final int[] L = new int[]{16842910};
    public static final int[] M = new int[0];
    public final Matrix A;
    public ViewTreeObserver.OnPreDrawListener B;
    public o a;
    public i b;
    public Drawable c;
    public c d;
    public Drawable e;
    public boolean f;
    public boolean g = true;
    public float h;
    public float i;
    public float j;
    public int k;
    public StateListAnimator l;
    public Animator m;
    public a2.h n;
    public a2.h o;
    public float p = 1.0f;
    public int q;
    public int r = 0;
    public ArrayList s;
    public ArrayList t;
    public ArrayList u;
    public final FloatingActionButton v;
    public final b w;
    public final Rect x = new Rect();
    public final RectF y = new RectF();
    public final RectF z = new RectF();

    public a(FloatingActionButton floatingActionButton, b b3) {
        this.A = new Matrix();
        this.v = floatingActionButton;
        this.w = b3;
    }

    public static /* synthetic */ void a(a a4, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Matrix matrix, ValueAnimator valueAnimator) {
        a4.getClass();
        float f10 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        a4.v.setAlpha(a2.a.b(f3, f4, 0.0f, 0.2f, f10));
        a4.v.setScaleX(a2.a.a(f5, f6, f10));
        a4.v.setScaleY(a2.a.a(f7, f6, f10));
        a4.p = a2.a.a(f8, f9, f10);
        a4.h(a2.a.a(f8, f9, f10), matrix);
        a4.v.setImageMatrix(matrix);
    }

    public static /* synthetic */ int b(a a4, int n3) {
        a4.r = n3;
        return n3;
    }

    public static /* synthetic */ Animator c(a a4, Animator animator) {
        a4.m = animator;
        return animator;
    }

    public static /* synthetic */ float d(a a4, float f3) {
        a4.p = f3;
        return f3;
    }

    public void A(ColorStateList object, PorterDuff.Mode mode, ColorStateList colorStateList, int n3) {
        i i3;
        this.b = i3 = this.n();
        i3.setTintList((ColorStateList)object);
        if (mode != null) {
            this.b.setTintMode(mode);
        }
        this.b.W(this.v.getContext());
        if (n3 > 0) {
            this.d = this.j(n3, (ColorStateList)object);
            object = new LayerDrawable(new Drawable[]{(Drawable)n0.h.g((Object)this.d), (Drawable)n0.h.g(this.b)});
        } else {
            this.d = null;
            object = this.b;
        }
        object = new RippleDrawable(t2.a.d(colorStateList), (Drawable)object, null);
        this.c = object;
        this.e = object;
    }

    public boolean B() {
        if (this.v.getVisibility() == 0) {
            return this.r == 1;
        }
        return this.r != 2;
    }

    public boolean C() {
        if (this.v.getVisibility() != 0) {
            return this.r == 2;
        }
        return this.r != 1;
    }

    public void D() {
        i i3 = this.b;
        if (i3 != null) {
            v2.j.f((View)this.v, i3);
        }
    }

    public void E() {
        this.c0();
    }

    public void F() {
        ViewTreeObserver viewTreeObserver = this.v.getViewTreeObserver();
        ViewTreeObserver.OnPreDrawListener onPreDrawListener = this.B;
        if (onPreDrawListener != null) {
            viewTreeObserver.removeOnPreDrawListener(onPreDrawListener);
            this.B = null;
        }
    }

    public void G(float f3, float f4, float f5) {
        if (this.v.getStateListAnimator() == this.l) {
            StateListAnimator stateListAnimator;
            this.l = stateListAnimator = this.l(f3, f4, f5);
            this.v.setStateListAnimator(stateListAnimator);
        }
        if (this.Y()) {
            this.c0();
        }
    }

    public void H(Rect rect) {
        n0.h.h(this.e, "Didn't initialize content background");
        if (this.Y()) {
            rect = new InsetDrawable(this.e, rect.left, rect.top, rect.right, rect.bottom);
            this.w.b((Drawable)rect);
            return;
        }
        this.w.b(this.e);
    }

    public void I() {
        ArrayList arrayList = this.u;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object e3 = arrayList.get(i3);
                ((f)e3).b();
            }
        }
    }

    public void J() {
        ArrayList arrayList = this.u;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object e3 = arrayList.get(i3);
                ((f)e3).a();
            }
        }
    }

    public void K(ColorStateList colorStateList) {
        Drawable drawable = this.b;
        if (drawable != null) {
            drawable.setTintList(colorStateList);
        }
        if ((drawable = this.d) != null) {
            drawable.c(colorStateList);
        }
    }

    public void L(PorterDuff.Mode mode) {
        i i3 = this.b;
        if (i3 != null) {
            i3.setTintMode(mode);
        }
    }

    public final void M(float f3) {
        if (this.h != f3) {
            this.h = f3;
            this.G(f3, this.i, this.j);
        }
    }

    public void N(boolean bl) {
        this.f = bl;
    }

    public final void O(a2.h h3) {
        this.o = h3;
    }

    public final void P(float f3) {
        if (this.i != f3) {
            this.i = f3;
            this.G(this.h, f3, this.j);
        }
    }

    public final void Q(float f3) {
        this.p = f3;
        Matrix matrix = this.A;
        this.h(f3, matrix);
        this.v.setImageMatrix(matrix);
    }

    public final void R(int n3) {
        if (this.q != n3) {
            this.q = n3;
            this.b0();
        }
    }

    public void S(int n3) {
        this.k = n3;
    }

    public final void T(float f3) {
        if (this.j != f3) {
            this.j = f3;
            this.G(this.h, this.i, f3);
        }
    }

    public void U(ColorStateList colorStateList) {
        Drawable drawable = this.c;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable)drawable).setColor(t2.a.d(colorStateList));
            return;
        }
        if (drawable != null) {
            drawable.setTintList(t2.a.d(colorStateList));
        }
    }

    public void V(boolean bl) {
        this.g = bl;
        this.c0();
    }

    public final void W(o o3) {
        this.a = o3;
        Drawable drawable = this.b;
        if (drawable != null) {
            drawable.setShapeAppearanceModel(o3);
        }
        if ((drawable = this.c) instanceof r) {
            ((r)drawable).setShapeAppearanceModel(o3);
        }
        if ((drawable = this.d) != null) {
            drawable.f(o3);
        }
    }

    public final void X(a2.h h3) {
        this.n = h3;
    }

    public boolean Y() {
        return this.w.c() || this.z();
        {
        }
    }

    public final boolean Z() {
        return this.v.isLaidOut() && !this.v.isInEditMode();
    }

    public void a0(g g3, boolean bl) {
        if (!this.C()) {
            Object object = this.m;
            if (object != null) {
                object.cancel();
            }
            object = this.n;
            int n3 = 0;
            int n4 = object == null ? 1 : 0;
            if (this.Z()) {
                if (this.v.getVisibility() != 0) {
                    object = this.v;
                    float f3 = 0.0f;
                    object.setAlpha(0.0f);
                    object = this.v;
                    float f4 = n4 != 0 ? 0.4f : 0.0f;
                    ((FloatingActionButton)object).setScaleY(f4);
                    object = this.v;
                    f4 = n4 != 0 ? 0.4f : 0.0f;
                    ((FloatingActionButton)object).setScaleX(f4);
                    f4 = f3;
                    if (n4 != 0) {
                        f4 = 0.4f;
                    }
                    this.Q(f4);
                }
                object = (object = this.n) != null ? this.i((a2.h)object, 1.0f, 1.0f, 1.0f) : this.k(1.0f, 1.0f, 1.0f, D, E);
                object.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, bl, g3){
                    public final boolean a;
                    public final g b;
                    public final a c;
                    {
                        this.c = a4;
                        this.a = bl;
                        this.b = g3;
                    }

                    public void onAnimationEnd(Animator object) {
                        com.google.android.material.floatingactionbutton.a.b(this.c, 0);
                        com.google.android.material.floatingactionbutton.a.c(this.c, null);
                        object = this.b;
                        if (object != null) {
                            object.a();
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        this.c.v.b(0, this.a);
                        com.google.android.material.floatingactionbutton.a.b(this.c, 2);
                        com.google.android.material.floatingactionbutton.a.c(this.c, animator);
                    }
                });
                ArrayList arrayList = this.s;
                if (arrayList != null) {
                    int n5 = arrayList.size();
                    for (n4 = n3; n4 < n5; ++n4) {
                        g3 = arrayList.get(n4);
                        object.addListener((Animator.AnimatorListener)g3);
                    }
                }
                object.start();
                return;
            }
            this.v.b(0, bl);
            this.v.setAlpha(1.0f);
            this.v.setScaleY(1.0f);
            this.v.setScaleX(1.0f);
            this.Q(1.0f);
            if (g3 != null) {
                g3.a();
            }
        }
    }

    public final void b0() {
        this.Q(this.p);
    }

    public final void c0() {
        Rect rect = this.x;
        this.t(rect);
        this.H(rect);
        this.w.a(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void d0(float f3) {
        i i3 = this.b;
        if (i3 != null) {
            i3.h0(f3);
        }
    }

    public void e(Animator.AnimatorListener animatorListener) {
        if (this.t == null) {
            this.t = new ArrayList();
        }
        this.t.add(animatorListener);
    }

    public final void e0(ObjectAnimator objectAnimator) {
        if (Build.VERSION.SDK_INT != 26) {
            return;
        }
        objectAnimator.setEvaluator(new TypeEvaluator(this){
            public final FloatEvaluator a;
            public final a b;
            {
                this.b = a4;
                this.a = new FloatEvaluator();
            }

            public Float a(float f3, Float f4, Float f5) {
                float f6;
                f3 = f6 = this.a.evaluate(f3, (Number)f4, (Number)f5).floatValue();
                if (f6 < 0.1f) {
                    f3 = 0.0f;
                }
                return Float.valueOf(f3);
            }
        });
    }

    public void f(Animator.AnimatorListener animatorListener) {
        if (this.s == null) {
            this.s = new ArrayList();
        }
        this.s.add(animatorListener);
    }

    public void g(f f3) {
        if (this.u == null) {
            this.u = new ArrayList();
        }
        this.u.add(f3);
    }

    public final void h(float f3, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.v.getDrawable();
        if (drawable != null && this.q != 0) {
            RectF rectF = this.y;
            RectF rectF2 = this.z;
            rectF.set(0.0f, 0.0f, (float)drawable.getIntrinsicWidth(), (float)drawable.getIntrinsicHeight());
            int n3 = this.q;
            rectF2.set(0.0f, 0.0f, (float)n3, (float)n3);
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
            n3 = this.q;
            matrix.postScale(f3, f3, (float)n3 / 2.0f, (float)n3 / 2.0f);
        }
    }

    public final AnimatorSet i(a2.h h3, float f3, float f4, float f5) {
        ArrayList<ObjectAnimator> arrayList = new ArrayList<ObjectAnimator>();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this.v, (Property)View.ALPHA, (float[])new float[]{f3});
        h3.h("opacity").a((Animator)objectAnimator);
        arrayList.add(objectAnimator);
        objectAnimator = ObjectAnimator.ofFloat((Object)this.v, (Property)View.SCALE_X, (float[])new float[]{f4});
        h3.h("scale").a((Animator)objectAnimator);
        this.e0(objectAnimator);
        arrayList.add(objectAnimator);
        objectAnimator = ObjectAnimator.ofFloat((Object)this.v, (Property)View.SCALE_Y, (float[])new float[]{f4});
        h3.h("scale").a((Animator)objectAnimator);
        this.e0(objectAnimator);
        arrayList.add(objectAnimator);
        this.h(f5, this.A);
        objectAnimator = ObjectAnimator.ofObject((Object)this.v, (Property)new a2.f(), (TypeEvaluator)new a2.g(this){
            public final a d;
            {
                this.d = a4;
            }

            @Override
            public Matrix a(float f3, Matrix matrix, Matrix matrix2) {
                com.google.android.material.floatingactionbutton.a.d(this.d, f3);
                return super.a(f3, matrix, matrix2);
            }
        }, (Object[])new Matrix[]{new Matrix(this.A)});
        h3.h("iconScale").a((Animator)objectAnimator);
        arrayList.add(objectAnimator);
        h3 = new AnimatorSet();
        a2.b.a((AnimatorSet)h3, arrayList);
        return h3;
    }

    public c j(int n3, ColorStateList colorStateList) {
        Context context = this.v.getContext();
        c c3 = new c((o)n0.h.g(this.a));
        c3.e(e0.a.b(context, z1.d.design_fab_stroke_top_outer_color), e0.a.b(context, z1.d.design_fab_stroke_top_inner_color), e0.a.b(context, z1.d.design_fab_stroke_end_inner_color), e0.a.b(context, z1.d.design_fab_stroke_end_outer_color));
        c3.d(n3);
        c3.c(colorStateList);
        return c3;
    }

    public final AnimatorSet k(float f3, float f4, float f5, int n3, int n4) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList<ValueAnimator> arrayList = new ArrayList<ValueAnimator>();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new d(this, this.v.getAlpha(), f3, this.v.getScaleX(), f4, this.v.getScaleY(), this.p, f5, new Matrix(this.A)));
        arrayList.add(valueAnimator);
        a2.b.a(animatorSet, arrayList);
        animatorSet.setDuration((long)p2.k.f(this.v.getContext(), n3, this.v.getContext().getResources().getInteger(z1.h.material_motion_duration_long_1)));
        animatorSet.setInterpolator(p2.k.g(this.v.getContext(), n4, a2.a.b));
        return animatorSet;
    }

    public final StateListAnimator l(float f3, float f4, float f5) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(H, this.m(f3, f5));
        stateListAnimator.addState(I, this.m(f3, f4));
        stateListAnimator.addState(J, this.m(f3, f4));
        stateListAnimator.addState(K, this.m(f3, f4));
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList<ObjectAnimator> arrayList = new ArrayList<ObjectAnimator>();
        arrayList.add(ObjectAnimator.ofFloat((Object)this.v, (String)"elevation", (float[])new float[]{f3}).setDuration(0L));
        arrayList.add(ObjectAnimator.ofFloat((Object)this.v, (Property)View.TRANSLATION_Z, (float[])new float[]{0.0f}).setDuration(100L));
        animatorSet.playSequentially(arrayList.toArray(new Animator[0]));
        animatorSet.setInterpolator(C);
        stateListAnimator.addState(L, (Animator)animatorSet);
        stateListAnimator.addState(M, this.m(0.0f, 0.0f));
        return stateListAnimator;
    }

    public final Animator m(float f3, float f4) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play((Animator)ObjectAnimator.ofFloat((Object)this.v, (String)"elevation", (float[])new float[]{f3}).setDuration(0L)).with((Animator)ObjectAnimator.ofFloat((Object)this.v, (Property)View.TRANSLATION_Z, (float[])new float[]{f4}).setDuration(100L));
        animatorSet.setInterpolator(C);
        return animatorSet;
    }

    public i n() {
        return new e((o)n0.h.g(this.a));
    }

    public final Drawable o() {
        return this.e;
    }

    public float p() {
        return this.v.getElevation();
    }

    public boolean q() {
        return this.f;
    }

    public final a2.h r() {
        return this.o;
    }

    public float s() {
        return this.i;
    }

    public void t(Rect rect) {
        if (this.w.c()) {
            int n3 = this.x();
            float f3 = this.g ? this.p() + this.j : 0.0f;
            int n4 = Math.max(n3, (int)Math.ceil(f3));
            n3 = Math.max(n3, (int)Math.ceil(f3 * 1.5f));
            rect.set(n4, n3, n4, n3);
            return;
        }
        if (this.z()) {
            int n5 = (this.k - this.v.getSizeDimension()) / 2;
            rect.set(n5, n5, n5, n5);
            return;
        }
        rect.set(0, 0, 0, 0);
    }

    public float u() {
        return this.j;
    }

    public final o v() {
        return this.a;
    }

    public final a2.h w() {
        return this.n;
    }

    public int x() {
        if (this.f) {
            return Math.max((this.k - this.v.getSizeDimension()) / 2, 0);
        }
        return 0;
    }

    public void y(g g3, boolean bl) {
        if (!this.B()) {
            Object object = this.m;
            if (object != null) {
                object.cancel();
            }
            if (this.Z()) {
                object = this.o;
                object = object != null ? this.i((a2.h)object, 0.0f, 0.0f, 0.0f) : this.k(0.0f, 0.4f, 0.4f, F, G);
                object.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, bl, g3){
                    public boolean a;
                    public final boolean b;
                    public final g c;
                    public final a d;
                    {
                        this.d = a4;
                        this.b = bl;
                        this.c = g3;
                    }

                    public void onAnimationCancel(Animator animator) {
                        this.a = true;
                    }

                    public void onAnimationEnd(Animator object) {
                        com.google.android.material.floatingactionbutton.a.b(this.d, 0);
                        com.google.android.material.floatingactionbutton.a.c(this.d, null);
                        if (!this.a) {
                            object = this.d.v;
                            boolean bl = this.b;
                            int n3 = bl ? 8 : 4;
                            ((VisibilityAwareImageButton)((Object)object)).b(n3, bl);
                            object = this.c;
                            if (object != null) {
                                object.b();
                            }
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        this.d.v.b(0, this.b);
                        com.google.android.material.floatingactionbutton.a.b(this.d, 1);
                        com.google.android.material.floatingactionbutton.a.c(this.d, animator);
                        this.a = false;
                    }
                });
                ArrayList arrayList = this.t;
                if (arrayList != null) {
                    int n3 = arrayList.size();
                    for (int i3 = 0; i3 < n3; ++i3) {
                        g3 = arrayList.get(i3);
                        object.addListener((Animator.AnimatorListener)g3);
                    }
                }
                object.start();
                return;
            }
            object = this.v;
            int n4 = bl ? 8 : 4;
            ((VisibilityAwareImageButton)((Object)object)).b(n4, bl);
            if (g3 != null) {
                g3.b();
            }
        }
    }

    public final boolean z() {
        return this.f && this.v.getSizeDimension() < this.k;
    }

    public static class e
    extends i {
        public e(o o3) {
            super(o3);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }

    public static interface f {
        public void a();

        public void b();
    }

    public static interface g {
        public void a();

        public void b();
    }
}

