/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import m1.b0;
import m1.n;
import m1.o;
import m1.y;

public class ChangeClipBounds
extends Transition {
    public static final String[] P = new String[]{"android:clipBounds:clip"};
    public static final Rect Q = new Rect();

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public String[] K() {
        return P;
    }

    @Override
    public void h(y y3) {
        this.o0(y3, false);
    }

    @Override
    public void k(y y3) {
        this.o0(y3, true);
    }

    @Override
    public Animator o(ViewGroup viewGroup, y object, y y3) {
        Rect rect = null;
        viewGroup = rect;
        if (object != null) {
            viewGroup = rect;
            if (y3 != null) {
                viewGroup = rect;
                if (((y)object).a.containsKey("android:clipBounds:clip")) {
                    if (!y3.a.containsKey("android:clipBounds:clip")) {
                        viewGroup = rect;
                    } else {
                        rect = (Rect)((y)object).a.get("android:clipBounds:clip");
                        Rect rect2 = (Rect)y3.a.get("android:clipBounds:clip");
                        if (rect == null && rect2 == null) {
                            return null;
                        }
                        viewGroup = rect == null ? (Rect)((y)object).a.get("android:clipBounds:bounds") : rect;
                        if (viewGroup.equals(object = rect2 == null ? (Rect)y3.a.get("android:clipBounds:bounds") : rect2)) {
                            return null;
                        }
                        y3.b.setClipBounds(rect);
                        o o3 = new o(new Rect());
                        viewGroup = ObjectAnimator.ofObject((Object)y3.b, (Property)b0.c, (TypeEvaluator)o3, (Object[])new Rect[]{viewGroup, object});
                        object = new a(y3.b, rect, rect2);
                        viewGroup.addListener((Animator.AnimatorListener)object);
                        this.a((Transition.g)object);
                    }
                }
            }
        }
        return viewGroup;
    }

    public final void o0(y y3, boolean bl) {
        View view = y3.b;
        if (view.getVisibility() != 8) {
            Object var5_4 = null;
            Object object = bl ? (Rect)view.getTag(m1.n.transition_clip) : null;
            Rect rect = object;
            if (object == null) {
                rect = view.getClipBounds();
            }
            object = rect == Q ? var5_4 : rect;
            y3.a.put("android:clipBounds:clip", object);
            if (object == null) {
                object = new Rect(0, 0, view.getWidth(), view.getHeight());
                y3.a.put("android:clipBounds:bounds", object);
            }
        }
    }

    public static class a
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final Rect a;
        public final Rect b;
        public final View c;

        public a(View view, Rect rect, Rect rect2) {
            this.c = view;
            this.a = rect;
            this.b = rect2;
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
        }

        @Override
        public void d(Transition transition) {
            Rect rect = this.c.getClipBounds();
            transition = rect;
            if (rect == null) {
                transition = Q;
            }
            this.c.setTag(m1.n.transition_clip, (Object)transition);
            this.c.setClipBounds(this.b);
        }

        @Override
        public void e(Transition transition) {
            transition = this.c;
            int n3 = m1.n.transition_clip;
            transition = (Rect)transition.getTag(n3);
            this.c.setClipBounds((Rect)transition);
            this.c.setTag(n3, null);
        }

        @Override
        public void g(Transition transition) {
        }

        public void onAnimationEnd(Animator animator) {
            this.onAnimationEnd(animator, false);
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            if (!bl) {
                this.c.setClipBounds(this.b);
                return;
            }
            this.c.setClipBounds(this.a);
        }
    }
}

