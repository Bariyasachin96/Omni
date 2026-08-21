/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import c.f;
import c.j;
import o0.x0;

public class ButtonBarLayout
extends LinearLayout {
    public boolean c;
    public boolean d;
    public int e = -1;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int[] nArray = j.ButtonBarLayout;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, nArray);
        x0.f0((View)this, context, nArray, attributeSet, typedArray, 0, 0);
        this.c = typedArray.getBoolean(j.ButtonBarLayout_allowStacking, true);
        typedArray.recycle();
        if (this.getOrientation() == 1) {
            this.setStacked(this.c);
        }
    }

    private void setStacked(boolean n3) {
        if (this.d != n3 && (n3 == 0 || this.c)) {
            this.d = n3;
            this.setOrientation(n3);
            int n4 = n3 != 0 ? 0x800005 : 80;
            this.setGravity(n4);
            View view = this.findViewById(f.spacer);
            if (view != null) {
                n3 = n3 != 0 ? 8 : 4;
                view.setVisibility(n3);
            }
            for (n3 = this.getChildCount() - 2; n3 >= 0; --n3) {
                this.bringChildToFront(this.getChildAt(n3));
            }
        }
    }

    public final int a(int n3) {
        int n4 = this.getChildCount();
        while (n3 < n4) {
            if (this.getChildAt(n3).getVisibility() == 0) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    public final boolean b() {
        return this.d;
    }

    public void onMeasure(int n3, int n4) {
        int n5;
        int n6 = View.MeasureSpec.getSize((int)n3);
        boolean bl = this.c;
        int n7 = 0;
        if (bl) {
            if (n6 > this.e && this.b()) {
                this.setStacked(false);
            }
            this.e = n6;
        }
        if (!this.b() && View.MeasureSpec.getMode((int)n3) == 0x40000000) {
            n5 = View.MeasureSpec.makeMeasureSpec((int)n6, (int)Integer.MIN_VALUE);
            n6 = 1;
        } else {
            n5 = n3;
            n6 = 0;
        }
        super.onMeasure(n5, n4);
        n5 = n6;
        if (this.c) {
            n5 = n6;
            if (!this.b()) {
                n5 = n6;
                if ((this.getMeasuredWidthAndState() & 0xFF000000) == 0x1000000) {
                    this.setStacked(true);
                    n5 = 1;
                }
            }
        }
        if (n5 != 0) {
            super.onMeasure(n3, n4);
        }
        int n8 = this.a(0);
        n6 = n7;
        if (n8 >= 0) {
            View view = this.getChildAt(n8);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            n5 = this.getPaddingTop() + view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            if (this.b()) {
                n7 = this.a(n8 + 1);
                n6 = n5;
                if (n7 >= 0) {
                    n6 = n5 + (this.getChildAt(n7).getPaddingTop() + (int)(this.getResources().getDisplayMetrics().density * 16.0f));
                }
            } else {
                n6 = n5 + this.getPaddingBottom();
            }
        }
        if (x0.z((View)this) != n6) {
            this.setMinimumHeight(n6);
            if (n4 == 0) {
                super.onMeasure(n3, n4);
            }
        }
    }

    public void setAllowStacking(boolean bl) {
        if (this.c != bl) {
            this.c = bl;
            if (!bl && this.b()) {
                this.setStacked(false);
            }
            this.requestLayout();
        }
    }
}

