/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class NavigationRailFrameLayout
extends FrameLayout {
    public int c = 0;
    public boolean d = false;

    public NavigationRailFrameLayout(Context context) {
        super(context);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        n5 = this.getChildCount();
        n4 = this.c;
        for (n3 = 0; n3 < n5; ++n3) {
            View view = this.getChildAt(n3);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            n4 = Math.max(n4, view.getTop()) + layoutParams.topMargin;
            view.layout(view.getLeft(), n4, view.getRight(), view.getMeasuredHeight() + n4);
            n4 += view.getMeasuredHeight() + layoutParams.bottomMargin;
        }
    }

    public void onMeasure(int n3, int n4) {
        View view;
        super.onMeasure(n3, n4);
        int n5 = this.getChildCount();
        int n6 = 0;
        View view2 = this.getChildAt(0);
        int n7 = View.MeasureSpec.getSize((int)n4);
        int n8 = n4;
        if (n5 > 1) {
            view = this.getChildAt(0);
            this.measureChild(view, n3, n4);
            view2 = (FrameLayout.LayoutParams)view.getLayoutParams();
            n6 = view.getMeasuredHeight();
            n8 = view2.bottomMargin;
            n5 = view2.topMargin + (n6 + n8);
            int n9 = this.c;
            view = this.getChildAt(1);
            n6 = n5;
            view2 = view;
            n8 = n4;
            if (!this.d) {
                n8 = View.MeasureSpec.makeMeasureSpec((int)(n7 - n5 - n9), (int)Integer.MIN_VALUE);
                view2 = view;
                n6 = n5;
            }
        }
        view = (FrameLayout.LayoutParams)view2.getLayoutParams();
        this.measureChild(view2, n3, n8);
        n3 = view2.getMeasuredHeight();
        n8 = view.bottomMargin;
        n4 = view.topMargin;
        n3 = Math.max(n7, this.c + n6 + (n3 + n8 + n4));
        this.setMeasuredDimension(this.getMeasuredWidth(), n3);
    }

    public void setPaddingTop(int n3) {
        this.c = n3;
    }

    public void setScrollingEnabled(boolean bl) {
        this.d = bl;
    }
}

