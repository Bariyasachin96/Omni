/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.Visibility;
import f0.k;
import m1.b0;
import m1.n;
import m1.r;
import m1.y;
import org.xmlpull.v1.XmlPullParser;

public class Fade
extends Visibility {
    public Fade() {
    }

    public Fade(int n3) {
        this.v0(n3);
    }

    public Fade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, m1.r.f);
        this.v0(f0.k.g((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "fadingMode", 0, this.p0()));
        context.recycle();
    }

    public static float x0(y object, float f3) {
        if (object != null && (object = (Float)((y)object).a.get("android:fade:transitionAlpha")) != null) {
            return ((Float)object).floatValue();
        }
        return f3;
    }

    @Override
    public void k(y y3) {
        Float f3;
        super.k(y3);
        Float f4 = f3 = (Float)y3.b.getTag(m1.n.transition_pause_alpha);
        if (f3 == null) {
            f4 = y3.b.getVisibility() == 0 ? Float.valueOf(b0.b(y3.b)) : Float.valueOf(0.0f);
        }
        y3.a.put("android:fade:transitionAlpha", f4);
    }

    @Override
    public Animator r0(ViewGroup viewGroup, View view, y y3, y y4) {
        b0.c(view);
        return this.w0(view, Fade.x0(y3, 0.0f), 1.0f);
    }

    @Override
    public Animator t0(ViewGroup viewGroup, View view, y y3, y y4) {
        b0.c(view);
        viewGroup = this.w0(view, Fade.x0(y3, 1.0f), 0.0f);
        if (viewGroup == null) {
            b0.f(view, Fade.x0(y4, 1.0f));
        }
        return viewGroup;
    }

    public final Animator w0(View object, float f3, float f4) {
        if (f3 == f4) {
            return null;
        }
        b0.f(object, f3);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)object, (Property)b0.b, (float[])new float[]{f4});
        object = new a((View)object);
        objectAnimator.addListener((Animator.AnimatorListener)object);
        this.D().a((Transition.g)object);
        return objectAnimator;
    }

    public static class a
    extends AnimatorListenerAdapter
    implements Transition.g {
        public final View a;
        public boolean b = false;

        public a(View view) {
            this.a = view;
        }

        @Override
        public void a(Transition transition) {
        }

        @Override
        public void b(Transition transition) {
        }

        @Override
        public void c(Transition transition, boolean bl) {
        }

        @Override
        public void d(Transition transition) {
            float f3 = this.a.getVisibility() == 0 ? b0.b(this.a) : 0.0f;
            this.a.setTag(m1.n.transition_pause_alpha, (Object)Float.valueOf(f3));
        }

        @Override
        public void e(Transition transition) {
            this.a.setTag(m1.n.transition_pause_alpha, null);
        }

        @Override
        public void g(Transition transition) {
        }

        public void onAnimationCancel(Animator animator) {
            b0.f(this.a, 1.0f);
        }

        public void onAnimationEnd(Animator animator) {
            this.onAnimationEnd(animator, false);
        }

        public void onAnimationEnd(Animator animator, boolean bl) {
            if (this.b) {
                this.a.setLayerType(0, null);
            }
            if (!bl) {
                b0.f(this.a, 1.0f);
                b0.a(this.a);
            }
        }

        public void onAnimationStart(Animator animator) {
            if (this.a.hasOverlappingRendering() && this.a.getLayerType() == 0) {
                this.b = true;
                this.a.setLayerType(2, null);
            }
        }
    }
}

