/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.google.android.material.snackbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.snackbar.BaseTransientBottomBar;

public final class Snackbar$SnackbarLayout
extends BaseTransientBottomBar.SnackbarBaseLayout {
    public Snackbar$SnackbarLayout(Context context) {
        super(context);
    }

    public Snackbar$SnackbarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        int n5 = this.getChildCount();
        n4 = this.getMeasuredWidth();
        int n6 = this.getPaddingLeft();
        int n7 = this.getPaddingRight();
        for (n3 = 0; n3 < n5; ++n3) {
            View view = this.getChildAt(n3);
            if (view.getLayoutParams().width != -1) continue;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n4 - n6 - n7), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)0x40000000));
        }
    }
}

