/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.LinearLayoutCompat;
import c.f;
import o0.s;
import o0.x0;

public class AlertDialogLayout
extends LinearLayoutCompat {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static int z(View view) {
        int n3 = x0.z(view);
        if (n3 > 0) {
            return n3;
        }
        if (view instanceof ViewGroup && (view = (ViewGroup)view).getChildCount() == 1) {
            return AlertDialogLayout.z(view.getChildAt(0));
        }
        return 0;
    }

    public final boolean A(int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        View view;
        int n9;
        int n10 = this.getChildCount();
        View view2 = null;
        View view3 = null;
        View view4 = null;
        for (n9 = 0; n9 < n10; ++n9) {
            view = this.getChildAt(n9);
            if (view.getVisibility() == 8) continue;
            n8 = view.getId();
            if (n8 == c.f.topPanel) {
                view2 = view;
                continue;
            }
            if (n8 == c.f.buttonPanel) {
                view3 = view;
                continue;
            }
            if (n8 != c.f.contentPanel && n8 != c.f.customPanel) {
                return false;
            }
            if (view4 != null) {
                return false;
            }
            view4 = view;
        }
        int n11 = View.MeasureSpec.getMode((int)n4);
        int n12 = View.MeasureSpec.getSize((int)n4);
        int n13 = View.MeasureSpec.getMode((int)n3);
        int n14 = this.getPaddingTop() + this.getPaddingBottom();
        if (view2 != null) {
            view2.measure(n3, 0);
            n14 += view2.getMeasuredHeight();
            n8 = View.combineMeasuredStates((int)0, (int)view2.getMeasuredState());
        } else {
            n8 = 0;
        }
        if (view3 != null) {
            view3.measure(n3, 0);
            n9 = AlertDialogLayout.z(view3);
            n7 = view3.getMeasuredHeight() - n9;
            n14 += n9;
            n8 = View.combineMeasuredStates((int)n8, (int)view3.getMeasuredState());
        } else {
            n9 = 0;
            n7 = 0;
        }
        if (view4 != null) {
            n6 = n11 == 0 ? 0 : View.MeasureSpec.makeMeasureSpec((int)Math.max(0, n12 - n14), (int)n11);
            view4.measure(n3, n6);
            n5 = view4.getMeasuredHeight();
            n14 += n5;
            n8 = View.combineMeasuredStates((int)n8, (int)view4.getMeasuredState());
        } else {
            n5 = 0;
        }
        int n15 = n12 - n14;
        n12 = n8;
        int n16 = n15;
        n6 = n14;
        if (view3 != null) {
            n7 = Math.min(n15, n7);
            n12 = n15;
            n6 = n9;
            if (n7 > 0) {
                n12 = n15 - n7;
                n6 = n9 + n7;
            }
            view3.measure(n3, View.MeasureSpec.makeMeasureSpec((int)n6, (int)0x40000000));
            n6 = n14 - n9 + view3.getMeasuredHeight();
            n9 = View.combineMeasuredStates((int)n8, (int)view3.getMeasuredState());
            n16 = n12;
            n12 = n9;
        }
        n8 = n12;
        n9 = n6;
        if (view4 != null) {
            n8 = n12;
            n9 = n6;
            if (n16 > 0) {
                view4.measure(n3, View.MeasureSpec.makeMeasureSpec((int)(n5 + n16), (int)n11));
                n9 = n6 - n5 + view4.getMeasuredHeight();
                n8 = View.combineMeasuredStates((int)n12, (int)view4.getMeasuredState());
            }
        }
        n12 = 0;
        for (n6 = 0; n6 < n10; ++n6) {
            view = this.getChildAt(n6);
            n14 = n12;
            if (view.getVisibility() != 8) {
                n14 = Math.max(n12, view.getMeasuredWidth());
            }
            n12 = n14;
        }
        this.setMeasuredDimension(View.resolveSizeAndState((int)(n12 + (this.getPaddingLeft() + this.getPaddingRight())), (int)n3, (int)n8), View.resolveSizeAndState((int)n9, (int)n4, (int)0));
        if (n13 != 0x40000000) {
            this.j(n10, n4);
        }
        return true;
    }

    public final void j(int n3, int n4) {
        int n5 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0x40000000);
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            if (view.getVisibility() == 8) continue;
            LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n6 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n5, 0, n4, 0);
            layoutParams.height = n6;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7 = this.getPaddingLeft();
        int n8 = n5 - n3;
        int n9 = this.getPaddingRight();
        int n10 = this.getPaddingRight();
        n3 = this.getMeasuredHeight();
        int n11 = this.getChildCount();
        int n12 = this.getGravity();
        n5 = n12 & 0x70;
        n3 = n5 != 16 ? (n5 != 80 ? this.getPaddingTop() : this.getPaddingTop() + n6 - n4 - n3) : this.getPaddingTop() + (n6 - n4 - n3) / 2;
        Drawable drawable = this.getDividerDrawable();
        n5 = 0;
        n4 = drawable == null ? 0 : drawable.getIntrinsicHeight();
        while (true) {
            block4: {
                int n13;
                LinearLayoutCompat.LayoutParams layoutParams;
                int n14;
                int n15;
                block7: {
                    block8: {
                        block5: {
                            block6: {
                                if (n5 >= n11) {
                                    return;
                                }
                                drawable = this.getChildAt(n5);
                                n6 = n3;
                                if (drawable == null) break block4;
                                n6 = n3;
                                if (drawable.getVisibility() == 8) break block4;
                                n15 = drawable.getMeasuredWidth();
                                n14 = drawable.getMeasuredHeight();
                                layoutParams = (LinearLayoutCompat.LayoutParams)drawable.getLayoutParams();
                                n6 = n13 = layoutParams.gravity;
                                if (n13 < 0) {
                                    n6 = n12 & 0x800007;
                                }
                                if ((n6 = s.b(n6, this.getLayoutDirection()) & 7) == 1) break block5;
                                if (n6 == 5) break block6;
                                n6 = layoutParams.leftMargin + n7;
                                break block7;
                            }
                            n6 = n8 - n9 - n15;
                            n13 = layoutParams.rightMargin;
                            break block8;
                        }
                        n6 = (n8 - n7 - n10 - n15) / 2 + n7 + layoutParams.leftMargin;
                        n13 = layoutParams.rightMargin;
                    }
                    n6 -= n13;
                }
                n13 = n3;
                if (this.r(n5)) {
                    n13 = n3 + n4;
                }
                n3 = n13 + layoutParams.topMargin;
                this.y((View)drawable, n6, n3, n15, n14);
                n6 = n3 + (n14 + layoutParams.bottomMargin);
            }
            ++n5;
            n3 = n6;
        }
    }

    @Override
    public void onMeasure(int n3, int n4) {
        if (!this.A(n3, n4)) {
            super.onMeasure(n3, n4);
        }
    }

    public final void y(View view, int n3, int n4, int n5, int n6) {
        view.layout(n3, n4, n5 + n3, n6 + n4);
    }
}

