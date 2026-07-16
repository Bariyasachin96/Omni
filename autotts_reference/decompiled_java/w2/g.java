/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.ViewGroup$MarginLayoutParams
 */
package w2;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.sidesheet.SideSheetBehavior;

public final class g
implements ValueAnimator.AnimatorUpdateListener {
    public final SideSheetBehavior a;
    public final ViewGroup.MarginLayoutParams b;
    public final int c;
    public final View d;

    public /* synthetic */ g(SideSheetBehavior sideSheetBehavior, ViewGroup.MarginLayoutParams marginLayoutParams, int n3, View view) {
        this.a = sideSheetBehavior;
        this.b = marginLayoutParams;
        this.c = n3;
        this.d = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SideSheetBehavior.K(this.a, this.b, this.c, this.d, valueAnimator);
    }
}

