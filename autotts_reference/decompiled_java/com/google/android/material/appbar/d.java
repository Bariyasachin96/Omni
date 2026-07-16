/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.ObjectAnimator
 *  android.animation.StateListAnimator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewOutlineProvider
 */
package com.google.android.material.appbar;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.google.android.material.internal.z;
import z1.c;
import z1.h;

public abstract class d {
    public static final int[] a = new int[]{16843848};

    public static void a(View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    public static void b(View view, float f3) {
        int n3 = view.getResources().getInteger(h.app_bar_elevation_anim_duration);
        StateListAnimator stateListAnimator = new StateListAnimator();
        int n4 = c.state_liftable;
        int n5 = -c.state_lifted;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)view, (String)"elevation", (float[])new float[]{0.0f});
        long l3 = n3;
        objectAnimator = objectAnimator.setDuration(l3);
        stateListAnimator.addState(new int[]{16842910, n4, n5}, (Animator)objectAnimator);
        objectAnimator = ObjectAnimator.ofFloat((Object)view, (String)"elevation", (float[])new float[]{f3}).setDuration(l3);
        stateListAnimator.addState(new int[]{16842910}, (Animator)objectAnimator);
        objectAnimator = ObjectAnimator.ofFloat((Object)view, (String)"elevation", (float[])new float[]{0.0f}).setDuration(0L);
        stateListAnimator.addState(new int[0], (Animator)objectAnimator);
        view.setStateListAnimator(stateListAnimator);
    }

    public static void c(View view, AttributeSet attributeSet, int n3, int n4) {
        Throwable throwable2;
        block3: {
            block2: {
                Context context = view.getContext();
                attributeSet = z.i(context, attributeSet, a, n3, n4, new int[0]);
                try {
                    if (!attributeSet.hasValue(0)) break block2;
                    view.setStateListAnimator(AnimatorInflater.loadStateListAnimator((Context)context, (int)attributeSet.getResourceId(0, 0)));
                }
                catch (Throwable throwable2) {
                    break block3;
                }
            }
            attributeSet.recycle();
            return;
        }
        attributeSet.recycle();
        throw throwable2;
    }
}

