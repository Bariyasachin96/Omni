/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout
extends ViewGroup {
    public int c = -1;
    public boolean d;

    public BaselineLayout(Context context) {
        super(context, null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    public int getBaseline() {
        return this.c;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7 = this.getChildCount();
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingRight();
        int n10 = this.getPaddingTop();
        for (n4 = 0; n4 < n7; ++n4) {
            View view = this.getChildAt(n4);
            if (view.getVisibility() == 8) continue;
            int n11 = view.getMeasuredWidth();
            int n12 = view.getMeasuredHeight();
            int n13 = (n5 - n3 - n9 - n8 - n11) / 2 + n8;
            n6 = this.c != -1 && view.getBaseline() != -1 ? this.c + n10 - view.getBaseline() : n10;
            view.layout(n13, n6, n11 + n13, n12 + n6);
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5;
        int n6;
        int n7 = this.getChildCount();
        int n8 = 0;
        int n9 = n6 = (n5 = 0);
        int n10 = -1;
        int n11 = -1;
        int n12 = n6;
        n6 = n5;
        for (int i3 = 0; i3 < n7; ++i3) {
            View view = this.getChildAt(i3);
            if (view.getVisibility() == 8) continue;
            this.measureChild(view, n3, n4);
            int n13 = Math.max(n8, view.getMeasuredHeight());
            int n14 = view.getBaseline();
            n8 = n10;
            n5 = n11;
            if (n14 != -1) {
                n8 = Math.max(n10, n14);
                n5 = Math.max(n11, view.getMeasuredHeight() - n14);
            }
            n12 = Math.max(n12, view.getMeasuredWidth());
            n6 = Math.max(n6, view.getMeasuredHeight());
            n9 = View.combineMeasuredStates((int)n9, (int)view.getMeasuredState());
            n11 = n5;
            n10 = n8;
            n8 = n13;
        }
        n5 = n6;
        if (n10 != -1) {
            n5 = n6;
            if (this.d) {
                n5 = Math.max(n6, Math.max(n11, this.getPaddingBottom()) + n10);
            }
            this.c = n10;
        }
        if (!this.d) {
            n5 = n8 + this.getPaddingBottom();
        }
        n6 = Math.max(n5, this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n12, this.getSuggestedMinimumWidth()), (int)n3, (int)n9), View.resolveSizeAndState((int)n6, (int)n4, (int)(n9 << 16)));
    }

    public void setMeasurePaddingFromBaseline(boolean bl) {
        this.d = bl;
    }
}

