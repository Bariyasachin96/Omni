/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View$MeasureSpec
 */
package q2;

import android.content.Context;
import android.view.View;
import com.google.android.material.navigation.NavigationBarItemView;
import z1.e;
import z1.i;

public final class c
extends NavigationBarItemView {
    public c(Context context) {
        super(context);
    }

    @Override
    public int getItemDefaultMarginResId() {
        return z1.e.mtrl_navigation_rail_icon_margin;
    }

    @Override
    public int getItemLayoutResId() {
        return z1.i.mtrl_navigation_rail_item;
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (View.MeasureSpec.getMode((int)n4) == 0) {
            n3 = View.MeasureSpec.getSize((int)n4);
            n3 = Math.max(this.getMeasuredHeight(), n3);
            this.setMeasuredDimension(this.getMeasuredWidthAndState(), n3);
        }
    }
}

