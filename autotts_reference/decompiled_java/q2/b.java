/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.ViewGroup
 */
package q2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import m1.y;
import q2.a;

public class b
extends Transition {
    public static /* synthetic */ void o0(View view, ValueAnimator valueAnimator) {
        view.setTranslationX((1.0f - valueAnimator.getAnimatedFraction()) * -30.0f);
    }

    @Override
    public void h(y y3) {
        y3.a.put("NavigationRailLabelVisibility", y3.b.getVisibility());
    }

    @Override
    public void k(y y3) {
        y3.a.put("NavigationRailLabelVisibility", y3.b.getVisibility());
    }

    @Override
    public Animator o(ViewGroup viewGroup, y y3, y y4) {
        if (y3 != null && y4 != null && y3.a.get("NavigationRailLabelVisibility") != null && y4.a.get("NavigationRailLabelVisibility") != null) {
            if ((Integer)y3.a.get("NavigationRailLabelVisibility") == 8 && (Integer)y4.a.get("NavigationRailLabelVisibility") == 0) {
                y3 = y4.b;
                viewGroup = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
                viewGroup.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new a((View)y3));
                return viewGroup;
            }
            return super.o(viewGroup, y3, y4);
        }
        return super.o(viewGroup, y3, y4);
    }
}

