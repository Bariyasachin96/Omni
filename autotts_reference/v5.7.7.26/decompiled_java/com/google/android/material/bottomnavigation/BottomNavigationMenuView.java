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
package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import java.util.ArrayList;
import java.util.List;
import z1.e;

public class BottomNavigationMenuView
extends NavigationBarMenuView {
    public final int d0;
    public final int e0;
    public final int f0;
    public final int g0;
    public boolean h0;
    public final List i0 = new ArrayList();

    public BottomNavigationMenuView(Context context) {
        super(context);
        context = new FrameLayout.LayoutParams(-2, -2);
        context.gravity = 17;
        this.setLayoutParams((ViewGroup.LayoutParams)context);
        context = this.getResources();
        this.d0 = context.getDimensionPixelSize(z1.e.design_bottom_navigation_item_max_width);
        this.e0 = context.getDimensionPixelSize(z1.e.design_bottom_navigation_item_min_width);
        this.f0 = context.getDimensionPixelSize(z1.e.design_bottom_navigation_active_item_max_width);
        this.g0 = context.getDimensionPixelSize(z1.e.design_bottom_navigation_active_item_min_width);
    }

    @Override
    public NavigationBarItemView h(Context context) {
        return new BottomNavigationItemView(context);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7 = this.getChildCount();
        int n8 = n6 - n4;
        n6 = 0;
        for (n4 = 0; n4 < n7; ++n4) {
            View view = this.getChildAt(n4);
            if (view.getVisibility() == 8) continue;
            if (this.getLayoutDirection() == 1) {
                int n9 = n5 - n3 - n6;
                view.layout(n9 - view.getMeasuredWidth(), 0, n9, n8);
            } else {
                view.layout(n6, 0, view.getMeasuredWidth() + n6, n8);
            }
            n6 += view.getMeasuredWidth();
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5;
        int n6;
        block21: {
            n6 = View.MeasureSpec.getSize((int)n3);
            n3 = this.getCurrentVisibleContentItemCount();
            int n7 = this.getChildCount();
            this.i0.clear();
            int n8 = View.MeasureSpec.makeMeasureSpec((int)View.MeasureSpec.getSize((int)n4), (int)Integer.MIN_VALUE);
            int n9 = this.getItemIconGravity();
            n4 = 1;
            n5 = 1;
            int n10 = 0;
            int n11 = 0;
            if (n9 == 0) {
                View view;
                if (this.j(this.getLabelVisibilityMode(), n3) && this.r()) {
                    view = this.getChildAt(this.getSelectedItemPosition());
                    n4 = n10 = this.g0;
                    if (view.getVisibility() != 8) {
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)this.f0, (int)Integer.MIN_VALUE), n8);
                        n4 = Math.max(n10, view.getMeasuredWidth());
                    }
                    n10 = view.getVisibility() != 8 ? 1 : 0;
                    n10 = n3 - n10;
                    n9 = Math.min(n6 - this.e0 * n10, Math.min(n4, this.f0));
                    n4 = n6 - n9;
                    n3 = n10 == 0 ? n5 : n10;
                    int n12 = Math.min(n4 / n3, this.d0);
                    n5 = n4 - n10 * n12;
                    for (n10 = 0; n10 < n7; ++n10) {
                        if (this.getChildAt(n10).getVisibility() != 8) {
                            n4 = n10 == this.getSelectedItemPosition() ? n9 : n12;
                            n3 = n4;
                            n6 = n5;
                            if (n5 > 0) {
                                n3 = n4 + 1;
                                n6 = n5 - 1;
                            }
                        } else {
                            n3 = 0;
                            n6 = n5;
                        }
                        this.i0.add(n3);
                        n5 = n6;
                    }
                } else {
                    if (n3 != 0) {
                        n4 = n3;
                    }
                    n5 = Math.min(n6 / n4, this.f0);
                    n10 = n6 - n3 * n5;
                    for (n4 = 0; n4 < n7; ++n4) {
                        if (this.getChildAt(n4).getVisibility() != 8) {
                            if (n10 > 0) {
                                n3 = n5 + 1;
                                --n10;
                            } else {
                                n3 = n5;
                            }
                        } else {
                            n3 = 0;
                        }
                        this.i0.add(n3);
                    }
                }
                n3 = 0;
                n4 = 0;
                n10 = n11;
                while (true) {
                    n6 = n4;
                    n5 = n3;
                    if (n10 < n7) {
                        view = this.getChildAt(n10);
                        if (view.getVisibility() != 8) {
                            view.measure(View.MeasureSpec.makeMeasureSpec((int)((Integer)this.i0.get(n10)), (int)0x40000000), n8);
                            view.getLayoutParams().width = view.getMeasuredWidth();
                            n3 += view.getMeasuredWidth();
                            n4 = Math.max(n4, view.getMeasuredHeight());
                        }
                        ++n10;
                        continue;
                    }
                    break block21;
                    break;
                }
            }
            n4 = n3;
            if (n3 == 0) {
                n4 = 1;
            }
            float f3 = Math.min((float)(n4 + 3) / 10.0f, 0.9f);
            float f4 = n6;
            float f5 = n4;
            n11 = Math.round(f3 * f4 / f5);
            n9 = Math.round(f4 / f5);
            n4 = 0;
            n3 = 0;
            while (n10 < n7) {
                View view = this.getChildAt(n10);
                n6 = n4;
                n5 = n3;
                if (view.getVisibility() != 8) {
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)n9, (int)Integer.MIN_VALUE), n8);
                    if (view.getMeasuredWidth() < n11) {
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)0x40000000), n8);
                    }
                    n6 = n4 + view.getMeasuredWidth();
                    n5 = Math.max(n3, view.getMeasuredHeight());
                }
                ++n10;
                n4 = n6;
                n3 = n5;
            }
            n5 = n4;
            n6 = n3;
        }
        this.setMeasuredDimension(n5, Math.max(n6, this.getSuggestedMinimumHeight()));
    }

    public boolean r() {
        return this.h0;
    }

    public void setItemHorizontalTranslationEnabled(boolean bl) {
        this.h0 = bl;
    }
}

