/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.google.android.material.transformation;

import a2.b;
import a2.i;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transformation.ExpandableTransformationBehavior;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FabTransformationScrimBehavior
extends ExpandableTransformationBehavior {
    public final i e = new i(75L, 150L);
    public final i f = new i(0L, 150L);

    public FabTransformationScrimBehavior() {
    }

    public FabTransformationScrimBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean H(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        return super.H(coordinatorLayout, view, motionEvent);
    }

    @Override
    public AnimatorSet N(View object, View view, boolean bl, boolean bl2) {
        object = new ArrayList();
        this.O(view, bl, bl2, (List)object, new ArrayList());
        AnimatorSet animatorSet = new AnimatorSet();
        b.a(animatorSet, (List)object);
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, bl, view){
            public final boolean a;
            public final View b;
            public final FabTransformationScrimBehavior c;
            {
                this.c = fabTransformationScrimBehavior;
                this.a = bl;
                this.b = view;
            }

            public void onAnimationEnd(Animator animator) {
                if (!this.a) {
                    this.b.setVisibility(4);
                }
            }

            public void onAnimationStart(Animator animator) {
                if (this.a) {
                    this.b.setVisibility(0);
                }
            }
        });
        return animatorSet;
    }

    public final void O(View view, boolean bl, boolean bl2, List list, List object) {
        object = bl ? this.e : this.f;
        if (bl) {
            if (!bl2) {
                view.setAlpha(0.0f);
            }
            view = ObjectAnimator.ofFloat((Object)view, (Property)View.ALPHA, (float[])new float[]{1.0f});
        } else {
            view = ObjectAnimator.ofFloat((Object)view, (Property)View.ALPHA, (float[])new float[]{0.0f});
        }
        ((i)object).a((Animator)view);
        list.add(view);
    }

    @Override
    public boolean i(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return view2 instanceof FloatingActionButton;
    }
}

