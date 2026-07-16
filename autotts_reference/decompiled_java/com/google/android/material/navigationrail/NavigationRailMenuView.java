/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout$LayoutParams
 */
package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import q2.c;

public class NavigationRailMenuView
extends NavigationBarMenuView {
    public int d0 = -1;
    public int e0 = 0;
    public final FrameLayout.LayoutParams f0;

    public NavigationRailMenuView(Context context) {
        super(context);
        context = new FrameLayout.LayoutParams(-1, -2);
        this.f0 = context;
        context.gravity = 49;
        this.setLayoutParams((ViewGroup.LayoutParams)context);
        this.setItemActiveIndicatorResizeable(true);
    }

    public int getItemMinimumHeight() {
        return this.d0;
    }

    public int getItemSpacing() {
        return this.e0;
    }

    public int getMenuGravity() {
        return this.f0.gravity;
    }

    @Override
    public NavigationBarItemView h(Context context) {
        return new c(context);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        View view;
        int n9 = this.getChildCount();
        int n10 = n6 = 0;
        for (n4 = 0; n4 < n9; ++n4) {
            view = this.getChildAt(n4);
            n8 = n10;
            n7 = n6;
            if (view.getVisibility() != 8) {
                n8 = n10 + view.getMeasuredHeight();
                n7 = n6 + 1;
            }
            n10 = n8;
            n6 = n7;
        }
        n4 = n6 <= 1 ? 0 : Math.max(0, Math.min((this.getMeasuredHeight() - n10) / (n6 - 1), this.e0));
        n7 = 0;
        for (n6 = 0; n6 < n9; ++n6) {
            view = this.getChildAt(n6);
            n8 = n7;
            if (view.getVisibility() != 8) {
                n8 = view.getMeasuredHeight();
                view.layout(0, n7, n5 - n3, n8 + n7);
                n8 = n7 + (n8 + n4);
            }
            n7 = n8;
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getSize((int)n4);
        int n6 = this.getCurrentVisibleContentItemCount();
        n6 = n6 > 1 && this.j(this.getLabelVisibilityMode(), n6) ? this.u(n3, n5, n6) : this.t(n3, n5, n6, null);
        this.setMeasuredDimension(View.MeasureSpec.getSize((int)n3), View.resolveSizeAndState((int)n6, (int)n4, (int)0));
    }

    public final int r(int n3, int n4, int n5) {
        n5 = n4 / Math.max(1, n5);
        n4 = this.d0;
        n3 = n4 != -1 ? n4 : View.MeasureSpec.getSize((int)n3);
        return View.MeasureSpec.makeMeasureSpec((int)Math.min(n3, n5), (int)0);
    }

    public final int s(View view, int n3, int n4) {
        view.measure(n3, n4);
        if (view.getVisibility() != 8) {
            return view.getMeasuredHeight();
        }
        return 0;
    }

    public void setItemMinimumHeight(int n3) {
        if (this.d0 != n3) {
            this.d0 = n3;
            this.requestLayout();
        }
    }

    public void setItemSpacing(int n3) {
        if (this.e0 != n3) {
            this.e0 = n3;
            this.requestLayout();
        }
    }

    public void setMenuGravity(int n3) {
        FrameLayout.LayoutParams layoutParams = this.f0;
        if (layoutParams.gravity != n3) {
            layoutParams.gravity = n3;
            this.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
    }

    public final int t(int n3, int n4, int n5, View view) {
        int n6;
        View view2;
        int n7;
        int n8 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)0);
        int n9 = this.getChildCount();
        int n10 = 0;
        int n11 = n4;
        n4 = n10;
        for (n7 = 0; n7 < n9; ++n7) {
            view2 = this.getChildAt(n7);
            n6 = n4;
            n10 = n11;
            if (!(view2 instanceof NavigationBarItemView)) {
                n6 = this.s(view2, n3, n8);
                n10 = n11 - n6;
                n6 = n4 + n6;
            }
            n4 = n6;
            n11 = n10;
        }
        n7 = Math.max(n11, 0);
        n5 = view == null ? this.r(n3, n7, n5) : View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)0);
        n6 = 0;
        for (n7 = 0; n7 < n9; ++n7) {
            view2 = this.getChildAt(n7);
            n11 = n6;
            if (view2.getVisibility() == 0) {
                n11 = n6 + 1;
            }
            n10 = n4;
            if (view2 instanceof NavigationBarItemView) {
                n10 = n4;
                if (view2 != view) {
                    n10 = n4 + this.s(view2, n3, n5);
                }
            }
            n6 = n11;
            n4 = n10;
        }
        return n4 + Math.max(0, n6 - 1) * this.e0;
    }

    public final int u(int n3, int n4, int n5) {
        int n6;
        View view = this.getChildAt(this.getSelectedItemPosition());
        if (view != null) {
            n6 = this.s(view, n3, this.r(n3, n4, n5));
            int n7 = n4 - n6;
            int n8 = n5 - 1;
            n4 = n6;
            n5 = n7;
            n6 = n8;
        } else {
            int n9 = 0;
            n6 = n5;
            n5 = n4;
            n4 = n9;
        }
        return n4 + this.t(n3, n5, n6, view);
    }
}

