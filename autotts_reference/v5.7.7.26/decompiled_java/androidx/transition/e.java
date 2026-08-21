/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TimeInterpolator
 *  android.util.Property
 *  android.view.View
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.util.Property;
import android.view.View;
import androidx.transition.Transition;
import m1.n;
import m1.y;

public abstract class e {
    public static Animator a(View object, y y3, int n3, int n4, float f3, float f4, float f5, float f6, TimeInterpolator timeInterpolator, Transition transition) {
        float f7 = object.getTranslationX();
        float f8 = object.getTranslationY();
        Object object2 = (int[])y3.b.getTag(n.transition_position);
        if (object2 != null) {
            f3 = (float)(object2[0] - n3) + f7;
            f4 = (float)(object2[1] - n4) + f8;
        }
        object.setTranslationX(f3);
        object.setTranslationY(f4);
        if (f3 == f5 && f4 == f6) {
            return null;
        }
        object2 = ObjectAnimator.ofPropertyValuesHolder((Object)object, (PropertyValuesHolder[])new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_X, (float[])new float[]{f3, f5}), PropertyValuesHolder.ofFloat((Property)View.TRANSLATION_Y, (float[])new float[]{f4, f6})});
        object = new a((View)object, y3.b, f7, f8);
        transition.a((Transition.g)object);
        object2.addListener((Animator.AnimatorListener)object);
        object2.setInterpolator(timeInterpolator);
        return object2;
    }

    public static class a
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final View a;
        public final View b;
        public int[] c;
        public float d;
        public float e;
        public final float f;
        public final float g;
        public boolean h;

        public a(View object, View view, float f3, float f4) {
            this.b = object;
            this.a = view;
            this.f = f3;
            this.g = f4;
            int n3 = n.transition_position;
            object = (int[])view.getTag(n3);
            this.c = (int[])object;
            if (object != null) {
                view.setTag(n3, null);
            }
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
            this.h = true;
            this.b.setTranslationX(this.f);
            this.b.setTranslationY(this.g);
        }

        @Override
        public void d(Transition transition) {
            this.h();
            this.d = this.b.getTranslationX();
            this.e = this.b.getTranslationY();
            this.b.setTranslationX(this.f);
            this.b.setTranslationY(this.g);
        }

        @Override
        public void e(Transition transition) {
            this.b.setTranslationX(this.d);
            this.b.setTranslationY(this.e);
        }

        @Override
        public void f(Transition transition, boolean bl) {
            if (!this.h) {
                this.a.setTag(n.transition_position, null);
            }
        }

        @Override
        public void g(Transition transition) {
            this.f(transition, false);
        }

        public final void h() {
            if (this.c == null) {
                this.c = new int[2];
            }
            this.b.getLocationOnScreen(this.c);
            this.a.setTag(n.transition_position, (Object)this.c);
        }

        public void onAnimationCancel(Animator animator) {
            this.h = true;
            this.b.setTranslationX(this.f);
            this.b.setTranslationY(this.g);
        }

        public void onAnimationEnd(Animator animator) {
            this.onAnimationEnd(animator, false);
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            if (!bl) {
                this.b.setTranslationX(this.f);
                this.b.setTranslationY(this.g);
            }
        }
    }
}

