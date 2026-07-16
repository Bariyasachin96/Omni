/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.transformation.ExpandableBehavior;

@Deprecated
public abstract class ExpandableTransformationBehavior
extends ExpandableBehavior {
    public AnimatorSet d;

    public ExpandableTransformationBehavior() {
    }

    public ExpandableTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static /* synthetic */ AnimatorSet M(ExpandableTransformationBehavior expandableTransformationBehavior, AnimatorSet animatorSet) {
        expandableTransformationBehavior.d = animatorSet;
        return animatorSet;
    }

    @Override
    public boolean L(View view, View view2, boolean bl, boolean bl2) {
        AnimatorSet animatorSet = this.d;
        boolean bl3 = animatorSet != null;
        if (bl3) {
            animatorSet.cancel();
        }
        view = this.N(view, view2, bl, bl3);
        this.d = view;
        view.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final ExpandableTransformationBehavior a;
            {
                this.a = expandableTransformationBehavior;
            }

            public void onAnimationEnd(Animator animator) {
                ExpandableTransformationBehavior.M(this.a, null);
            }
        });
        this.d.start();
        if (!bl2) {
            this.d.end();
        }
        return true;
    }

    public abstract AnimatorSet N(View var1, View var2, boolean var3, boolean var4);
}

