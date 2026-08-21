/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.Gravity
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.ViewOffsetBehavior;
import j0.a;
import java.util.List;
import o0.z1;

abstract class HeaderScrollingViewBehavior
extends ViewOffsetBehavior<View> {
    public final Rect f = new Rect();
    public final Rect g = new Rect();
    public int h = 0;
    public int i;

    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private static int R(int n3) {
        int n4 = n3;
        if (n3 == 0) {
            n4 = 0x800033;
        }
        return n4;
    }

    @Override
    public void J(CoordinatorLayout coordinatorLayout, View view, int n3) {
        View view2 = this.L(coordinatorLayout.v(view));
        if (view2 != null) {
            CoordinatorLayout.e e3 = (CoordinatorLayout.e)view.getLayoutParams();
            Rect rect = this.f;
            rect.set(coordinatorLayout.getPaddingLeft() + e3.leftMargin, view2.getBottom() + e3.topMargin, coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight() - e3.rightMargin, coordinatorLayout.getHeight() + view2.getBottom() - coordinatorLayout.getPaddingBottom() - e3.bottomMargin);
            z1 z12 = coordinatorLayout.getLastWindowInsets();
            if (z12 != null && coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                rect.left += z12.j();
                rect.right -= z12.k();
            }
            coordinatorLayout = this.g;
            Gravity.apply((int)HeaderScrollingViewBehavior.R(e3.c), (int)view.getMeasuredWidth(), (int)view.getMeasuredHeight(), (Rect)rect, (Rect)coordinatorLayout, (int)n3);
            n3 = this.M(view2);
            view.layout(((Rect)coordinatorLayout).left, ((Rect)coordinatorLayout).top - n3, ((Rect)coordinatorLayout).right, ((Rect)coordinatorLayout).bottom - n3);
            this.h = ((Rect)coordinatorLayout).top - view2.getBottom();
            return;
        }
        super.J(coordinatorLayout, view, n3);
        this.h = 0;
    }

    public abstract View L(List var1);

    public final int M(View view) {
        if (this.i == 0) {
            return 0;
        }
        float f3 = this.N(view);
        int n3 = this.i;
        return a.b((int)(f3 * (float)n3), 0, n3);
    }

    public float N(View view) {
        return 1.0f;
    }

    public final int O() {
        return this.i;
    }

    public int P(View view) {
        return view.getMeasuredHeight();
    }

    public final int Q() {
        return this.h;
    }

    public final void S(int n3) {
        this.i = n3;
    }

    public boolean T() {
        return false;
    }

    @Override
    public boolean q(CoordinatorLayout coordinatorLayout, View view, int n3, int n4, int n5, int n6) {
        View view2;
        int n7 = view.getLayoutParams().height;
        if ((n7 == -1 || n7 == -2) && (view2 = this.L(coordinatorLayout.v(view))) != null) {
            int n8 = View.MeasureSpec.getSize((int)n5);
            if (n8 > 0) {
                n5 = n8;
                if (view2.getFitsSystemWindows()) {
                    z1 z12 = coordinatorLayout.getLastWindowInsets();
                    n5 = n8;
                    if (z12 != null) {
                        n5 = n8 + (z12.l() + z12.i());
                    }
                }
            } else {
                n5 = coordinatorLayout.getHeight();
            }
            n5 += this.P(view2);
            n8 = view2.getMeasuredHeight();
            if (this.T()) {
                view.setTranslationY((float)(-n8));
            } else {
                view.setTranslationY(0.0f);
                n5 -= n8;
            }
            n8 = n7 == -1 ? 0x40000000 : Integer.MIN_VALUE;
            coordinatorLayout.N(view, n3, n4, View.MeasureSpec.makeMeasureSpec((int)n5, (int)n8), n6);
            return true;
        }
        return false;
    }
}

