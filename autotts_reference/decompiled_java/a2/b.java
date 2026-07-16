/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.ValueAnimator
 */
package a2;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import java.util.Collection;
import java.util.List;

public abstract class b {
    public static void a(AnimatorSet animatorSet, List list) {
        Animator animator;
        int n3 = list.size();
        long l3 = 0L;
        for (int i3 = 0; i3 < n3; ++i3) {
            animator = (Animator)list.get(i3);
            l3 = Math.max(l3, animator.getStartDelay() + animator.getDuration());
        }
        animator = ValueAnimator.ofInt((int[])new int[]{0, 0});
        animator.setDuration(l3);
        list.add(0, animator);
        animatorSet.playTogether((Collection)list);
    }
}

