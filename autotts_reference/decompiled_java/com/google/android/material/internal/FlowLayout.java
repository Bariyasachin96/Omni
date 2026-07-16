/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import z1.g;
import z1.m;

public class FlowLayout
extends ViewGroup {
    public int c;
    public int d;
    public boolean e = false;
    public int f;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.d(context, attributeSet);
    }

    public static int a(int n3, int n4, int n5) {
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 0x40000000) {
                return n5;
            }
            return n3;
        }
        return Math.min(n5, n3);
    }

    public int b(View object) {
        if (!((object = object.getTag(g.row_index_key)) instanceof Integer)) {
            return -1;
        }
        return (Integer)object;
    }

    public boolean c() {
        return this.e;
    }

    public final void d(Context context, AttributeSet attributeSet) {
        context = context.getTheme().obtainStyledAttributes(attributeSet, m.FlowLayout, 0, 0);
        this.c = context.getDimensionPixelSize(m.FlowLayout_lineSpacing, 0);
        this.d = context.getDimensionPixelSize(m.FlowLayout_horizontalItemSpacing, 0);
        context.recycle();
    }

    public int getItemSpacing() {
        return this.d;
    }

    public int getLineSpacing() {
        return this.c;
    }

    public int getRowCount() {
        return this.f;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        if (this.getChildCount() == 0) {
            this.f = 0;
            return;
        }
        bl = true;
        this.f = 1;
        boolean bl2 = this.getLayoutDirection() == 1;
        n4 = bl2 ? this.getPaddingRight() : this.getPaddingLeft();
        int n7 = bl2 ? this.getPaddingLeft() : this.getPaddingRight();
        int n8 = this.getPaddingTop();
        n6 = n4;
        int n9 = n8;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            int n10;
            int n11;
            View view = this.getChildAt(i3);
            if (view.getVisibility() == 8) {
                view.setTag(g.row_index_key, (Object)-1);
                continue;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                n11 = layoutParams.getMarginStart();
                n10 = layoutParams.getMarginEnd();
            } else {
                n10 = 0;
                n11 = 0;
            }
            int n12 = n6 + n11 + view.getMeasuredWidth();
            int n13 = n5 - n3;
            int n14 = n9;
            int n15 = n6;
            int n16 = n12;
            if (!this.e) {
                n14 = n9;
                n15 = n6;
                n16 = n12;
                if (n12 > n13 - n7) {
                    n16 = n4 + n11 + view.getMeasuredWidth();
                    n14 = n8 + this.c;
                    ++this.f;
                    n15 = n4;
                }
            }
            view.setTag(g.row_index_key, (Object)(this.f - 1));
            n8 = view.getMeasuredHeight() + n14;
            if (bl2) {
                view.layout(n13 - n16, n14, n13 - n15 - n11, n8);
            } else {
                view.layout(n15 + n11, n14, n16, n8);
            }
            n6 = n15 + (n11 + n10 + view.getMeasuredWidth() + this.d);
            n9 = n14;
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getSize((int)n3);
        int n6 = View.MeasureSpec.getMode((int)n3);
        int n7 = View.MeasureSpec.getSize((int)n4);
        int n8 = View.MeasureSpec.getMode((int)n4);
        int n9 = n6 != Integer.MIN_VALUE && n6 != 0x40000000 ? Integer.MAX_VALUE : n5;
        int n10 = this.getPaddingLeft();
        int n11 = this.getPaddingTop();
        int n12 = this.getPaddingRight();
        int n13 = n11;
        int n14 = 0;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            int n15;
            int n16;
            View view = this.getChildAt(i3);
            if (view.getVisibility() == 8) continue;
            this.measureChild(view, n3, n4);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                n16 = layoutParams.leftMargin;
                n15 = layoutParams.rightMargin;
            } else {
                n16 = 0;
                n15 = 0;
            }
            if (n10 + n16 + view.getMeasuredWidth() > n9 - n12 && !this.c()) {
                n10 = this.getPaddingLeft();
                n13 = this.c + n11;
                n11 = n10;
            } else {
                n11 = n10;
            }
            int n17 = n11 + n16 + view.getMeasuredWidth();
            int n18 = view.getMeasuredHeight();
            n10 = n14;
            if (n17 > n14) {
                n10 = n17;
            }
            n16 = n11 + (n16 + n15 + view.getMeasuredWidth() + this.d);
            n14 = n10;
            if (i3 == this.getChildCount() - 1) {
                n14 = n10 + n15;
            }
            n11 = n13 + n18;
            n10 = n16;
        }
        n4 = this.getPaddingRight();
        n3 = this.getPaddingBottom();
        this.setMeasuredDimension(FlowLayout.a(n5, n6, n14 + n4), FlowLayout.a(n7, n8, n11 + n3));
    }

    public void setItemSpacing(int n3) {
        this.d = n3;
    }

    public void setLineSpacing(int n3) {
        this.c = n3;
    }

    public void setSingleLine(boolean bl) {
        this.e = bl;
    }
}

