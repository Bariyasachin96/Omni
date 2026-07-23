/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.LinearLayout$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.t0;
import c.j;
import o0.s;
import o0.x0;

public class LinearLayoutCompat
extends ViewGroup {
    public boolean c = true;
    public int d = -1;
    public int e = 0;
    public int f;
    public int g = 0x800033;
    public int h;
    public float i;
    public boolean j;
    public int[] k;
    public int[] l;
    public Drawable m;
    public int n;
    public int o;
    public int p;
    public int q;

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        boolean bl;
        int[] nArray = c.j.LinearLayoutCompat;
        m0 m02 = m0.v(context, attributeSet, nArray, n3, 0);
        x0.f0((View)this, context, nArray, attributeSet, m02.r(), n3, 0);
        n3 = m02.k(c.j.LinearLayoutCompat_android_orientation, -1);
        if (n3 >= 0) {
            this.setOrientation(n3);
        }
        if ((n3 = m02.k(c.j.LinearLayoutCompat_android_gravity, -1)) >= 0) {
            this.setGravity(n3);
        }
        if (!(bl = m02.a(c.j.LinearLayoutCompat_android_baselineAligned, true))) {
            this.setBaselineAligned(bl);
        }
        this.i = m02.i(c.j.LinearLayoutCompat_android_weightSum, -1.0f);
        this.d = m02.k(c.j.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.j = m02.a(c.j.LinearLayoutCompat_measureWithLargestChild, false);
        this.setDividerDrawable(m02.g(c.j.LinearLayoutCompat_divider));
        this.p = m02.k(c.j.LinearLayoutCompat_showDividers, 0);
        this.q = m02.f(c.j.LinearLayoutCompat_dividerPadding, 0);
        m02.x();
    }

    private void j(int n3, int n4) {
        int n5 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0x40000000);
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.q(i3);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n6 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n5, 0, n4, 0);
            layoutParams.height = n6;
        }
    }

    private void y(View view, int n3, int n4, int n5, int n6) {
        view.layout(n3, n4, n5 + n3, n6 + n4);
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Unable to fully structure code
     */
    public void e(Canvas var1_1) {
        block6: {
            block5: {
                var4_2 = this.getVirtualChildCount();
                var5_3 = t0.b((View)this);
                for (var2_4 = 0; var2_4 < var4_2; ++var2_4) {
                    var6_6 = this.q(var2_4);
                    if (var6_6 == null || var6_6.getVisibility() == 8 || !this.r(var2_4)) continue;
                    var7_7 = (LayoutParams)var6_6.getLayoutParams();
                    var3_5 = var5_3 != false ? var6_6.getRight() + var7_7.rightMargin : var6_6.getLeft() - var7_7.leftMargin - this.n;
                    this.h(var1_1, var3_5);
                }
                if (!this.r(var4_2)) break block6;
                var6_6 = this.q(var4_2 - 1);
                if (var6_6 != null) ** GOTO lbl21
                if (var5_3) {
                    var2_4 = this.getPaddingLeft();
                } else {
                    var3_5 = this.getWidth() - this.getPaddingRight();
                    var2_4 = this.n;
lbl18:
                    // 2 sources

                    while (true) {
                        var2_4 = var3_5 - var2_4;
                        break block5;
                        break;
                    }
lbl21:
                    // 1 sources

                    var7_7 = (LayoutParams)var6_6.getLayoutParams();
                    if (var5_3) {
                        var3_5 = var6_6.getLeft() - var7_7.leftMargin;
                        var2_4 = this.n;
                        ** continue;
                    }
                    var2_4 = var6_6.getRight() + var7_7.rightMargin;
                }
            }
            this.h(var1_1, var2_4);
        }
    }

    public void f(Canvas canvas) {
        Object object;
        Object object2;
        int n3;
        int n4 = this.getVirtualChildCount();
        for (n3 = 0; n3 < n4; ++n3) {
            object2 = this.q(n3);
            if (object2 == null || object2.getVisibility() == 8 || !this.r(n3)) continue;
            object = (LayoutParams)object2.getLayoutParams();
            this.g(canvas, object2.getTop() - object.topMargin - this.o);
        }
        if (this.r(n4)) {
            object = this.q(n4 - 1);
            if (object == null) {
                n3 = this.getHeight() - this.getPaddingBottom() - this.o;
            } else {
                object2 = (LayoutParams)object.getLayoutParams();
                n3 = object.getBottom() + object2.bottomMargin;
            }
            this.g(canvas, n3);
        }
    }

    public void g(Canvas canvas, int n3) {
        this.m.setBounds(this.getPaddingLeft() + this.q, n3, this.getWidth() - this.getPaddingRight() - this.q, this.o + n3);
        this.m.draw(canvas);
    }

    public int getBaseline() {
        int n3;
        if (this.d < 0) {
            return super.getBaseline();
        }
        int n4 = this.getChildCount();
        if (n4 > (n3 = this.d)) {
            View view = this.getChildAt(n3);
            int n5 = view.getBaseline();
            if (n5 == -1) {
                if (this.d == 0) {
                    return -1;
                }
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
            n3 = n4 = this.e;
            if (this.f == 1) {
                int n6 = this.g & 0x70;
                n3 = n4;
                if (n6 != 48) {
                    n3 = n6 != 16 ? (n6 != 80 ? n4 : this.getBottom() - this.getTop() - this.getPaddingBottom() - this.h) : n4 + (this.getBottom() - this.getTop() - this.getPaddingTop() - this.getPaddingBottom() - this.h) / 2;
                }
            }
            return n3 + ((LayoutParams)view.getLayoutParams()).topMargin + n5;
        }
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
    }

    public int getBaselineAlignedChildIndex() {
        return this.d;
    }

    public Drawable getDividerDrawable() {
        return this.m;
    }

    public int getDividerPadding() {
        return this.q;
    }

    public int getDividerWidth() {
        return this.n;
    }

    public int getGravity() {
        return this.g;
    }

    public int getOrientation() {
        return this.f;
    }

    public int getShowDividers() {
        return this.p;
    }

    public int getVirtualChildCount() {
        return this.getChildCount();
    }

    public float getWeightSum() {
        return this.i;
    }

    public void h(Canvas canvas, int n3) {
        this.m.setBounds(n3, this.getPaddingTop() + this.q, this.n + n3, this.getHeight() - this.getPaddingBottom() - this.q);
        this.m.draw(canvas);
    }

    public final void i(int n3, int n4) {
        int n5 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0x40000000);
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.q(i3);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.height != -1) continue;
            int n6 = layoutParams.width;
            layoutParams.width = view.getMeasuredWidth();
            this.measureChildWithMargins(view, n4, 0, n5, 0);
            layoutParams.width = n6;
        }
    }

    public LayoutParams k() {
        int n3 = this.f;
        if (n3 == 0) {
            return new LayoutParams(-2, -2);
        }
        if (n3 == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    public LayoutParams l(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public LayoutParams m(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)((LayoutParams)layoutParams));
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public int n(View view, int n3) {
        return 0;
    }

    public int o(View view) {
        return 0;
    }

    public void onDraw(Canvas canvas) {
        if (this.m == null) {
            return;
        }
        if (this.f == 1) {
            this.f(canvas);
            return;
        }
        this.e(canvas);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)"androidx.appcompat.widget.LinearLayoutCompat");
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)"androidx.appcompat.widget.LinearLayoutCompat");
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        if (this.f == 1) {
            this.t(n3, n4, n5, n6);
            return;
        }
        this.s(n3, n4, n5, n6);
    }

    public void onMeasure(int n3, int n4) {
        if (this.f == 1) {
            this.x(n3, n4);
            return;
        }
        this.v(n3, n4);
    }

    public int p(View view) {
        return 0;
    }

    public View q(int n3) {
        return this.getChildAt(n3);
    }

    public boolean r(int n3) {
        if (n3 == 0) {
            return (this.p & 1) != 0;
        }
        if (n3 == this.getChildCount()) {
            return (this.p & 4) != 0;
        }
        if ((this.p & 2) != 0) {
            --n3;
            while (n3 >= 0) {
                if (this.getChildAt(n3).getVisibility() != 8) {
                    return true;
                }
                --n3;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void s(int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        boolean bl = t0.b((View)this);
        int n9 = this.getPaddingTop();
        int n10 = n6 - n4;
        int n11 = this.getPaddingBottom();
        int n12 = this.getPaddingBottom();
        int n13 = this.getVirtualChildCount();
        int n14 = this.g;
        boolean bl2 = this.c;
        int[] nArray = this.k;
        int[] nArray2 = this.l;
        n4 = s.b(0x800007 & n14, this.getLayoutDirection());
        int n15 = 2;
        int n16 = 1;
        n3 = n4 != 1 ? (n4 != 5 ? this.getPaddingLeft() : this.getPaddingLeft() + n5 - n3 - this.h) : this.getPaddingLeft() + (n5 - n3 - this.h) / 2;
        if (bl) {
            n8 = n13 - 1;
            n7 = -1;
        } else {
            n8 = 0;
            n7 = 1;
        }
        n4 = 0;
        n5 = n9;
        n6 = n3;
        while (true) {
            block8: {
                block9: {
                    int n17;
                    int n18;
                    LayoutParams layoutParams;
                    int n19;
                    int n20;
                    View view;
                    int n21;
                    block13: {
                        block14: {
                            block10: {
                                block11: {
                                    block12: {
                                        block7: {
                                            if (n4 >= n13) {
                                                return;
                                            }
                                            n21 = n8 + n7 * n4;
                                            view = this.q(n21);
                                            if (view != null) break block7;
                                            n6 += this.w(n21);
                                            n3 = n4;
                                            break block8;
                                        }
                                        if (view.getVisibility() == 8) break block9;
                                        n20 = view.getMeasuredWidth();
                                        n19 = view.getMeasuredHeight();
                                        layoutParams = (LayoutParams)view.getLayoutParams();
                                        n18 = bl2 && layoutParams.height != -1 ? view.getBaseline() : -1;
                                        n3 = n17 = layoutParams.gravity;
                                        if (n17 < 0) {
                                            n3 = n14 & 0x70;
                                        }
                                        if ((n3 &= 0x70) == 16) break block10;
                                        if (n3 == 48) break block11;
                                        if (n3 == 80) break block12;
                                        n3 = n5;
                                        break block13;
                                    }
                                    n3 = n17 = n10 - n11 - n19 - layoutParams.bottomMargin;
                                    if (n18 == -1) break block13;
                                    n3 = view.getMeasuredHeight();
                                    n3 = nArray2[n15] - (n3 - n18);
                                    break block14;
                                }
                                n3 = n17 = n5 + layoutParams.topMargin;
                                if (n18 != -1) {
                                    n3 = n17 + (nArray[n16] - n18);
                                }
                                break block13;
                            }
                            n17 = n5 + (n10 - n9 - n12 - n19) / 2 + layoutParams.topMargin;
                            n3 = layoutParams.bottomMargin;
                        }
                        n3 = n17 - n3;
                    }
                    if (this.r(n21)) {
                        n6 += this.n;
                    }
                    n6 = layoutParams.leftMargin + n6;
                    this.y(view, this.o(view) + n6, n3, n20, n19);
                    n18 = layoutParams.rightMargin;
                    n17 = this.p(view);
                    n3 = this.n(view, n21) + n4;
                    n6 += n18 + n20 + n17;
                    break block8;
                }
                n3 = n4;
            }
            n4 = n3 + 1;
        }
    }

    public void setBaselineAligned(boolean bl) {
        this.c = bl;
    }

    public void setBaselineAlignedChildIndex(int n3) {
        if (n3 >= 0 && n3 < this.getChildCount()) {
            this.d = n3;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("base aligned child index out of range (0, ");
        stringBuilder.append(this.getChildCount());
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.m) {
            return;
        }
        this.m = drawable;
        boolean bl = false;
        if (drawable != null) {
            this.n = drawable.getIntrinsicWidth();
            this.o = drawable.getIntrinsicHeight();
        } else {
            this.n = 0;
            this.o = 0;
        }
        if (drawable == null) {
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.requestLayout();
    }

    public void setDividerPadding(int n3) {
        this.q = n3;
    }

    public void setGravity(int n3) {
        if (this.g != n3) {
            int n4 = n3;
            if ((0x800007 & n3) == 0) {
                n4 = n3 | 0x800003;
            }
            n3 = n4;
            if ((n4 & 0x70) == 0) {
                n3 = n4 | 0x30;
            }
            this.g = n3;
            this.requestLayout();
        }
    }

    public void setHorizontalGravity(int n3) {
        int n4 = this.g;
        if ((0x800007 & n4) != (n3 &= 0x800007)) {
            this.g = n3 | 0xFF7FFFF8 & n4;
            this.requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean bl) {
        this.j = bl;
    }

    public void setOrientation(int n3) {
        if (this.f != n3) {
            this.f = n3;
            this.requestLayout();
        }
    }

    public void setShowDividers(int n3) {
        if (n3 != this.p) {
            this.requestLayout();
        }
        this.p = n3;
    }

    public void setVerticalGravity(int n3) {
        int n4 = this.g;
        if ((n4 & 0x70) != (n3 &= 0x70)) {
            this.g = n3 | n4 & 0xFFFFFF8F;
            this.requestLayout();
        }
    }

    public void setWeightSum(float f3) {
        this.i = Math.max(0.0f, f3);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void t(int n3, int n4, int n5, int n6) {
        int n7 = this.getPaddingLeft();
        int n8 = n5 - n3;
        int n9 = this.getPaddingRight();
        int n10 = this.getPaddingRight();
        int n11 = this.getVirtualChildCount();
        int n12 = this.g;
        n3 = n12 & 0x70;
        n3 = n3 != 16 ? (n3 != 80 ? this.getPaddingTop() : this.getPaddingTop() + n6 - n4 - this.h) : this.getPaddingTop() + (n6 - n4 - this.h) / 2;
        n4 = 0;
        while (true) {
            block9: {
                LayoutParams layoutParams;
                int n13;
                int n14;
                View view;
                block8: {
                    block10: {
                        block6: {
                            block7: {
                                block5: {
                                    block4: {
                                        if (n4 >= n11) {
                                            return;
                                        }
                                        view = this.q(n4);
                                        if (view != null) break block4;
                                        n5 = n3 + this.w(n4);
                                        break block5;
                                    }
                                    n5 = n3;
                                    if (view.getVisibility() == 8) break block5;
                                    n14 = view.getMeasuredWidth();
                                    n13 = view.getMeasuredHeight();
                                    layoutParams = (LayoutParams)view.getLayoutParams();
                                    n5 = n6 = layoutParams.gravity;
                                    if (n6 < 0) {
                                        n5 = n12 & 0x800007;
                                    }
                                    if ((n5 = s.b(n5, this.getLayoutDirection()) & 7) == 1) break block6;
                                    if (n5 == 5) break block7;
                                    n5 = layoutParams.leftMargin + n7;
                                    break block8;
                                }
                                n3 = n5;
                                break block9;
                            }
                            n6 = n8 - n9 - n14;
                            n5 = layoutParams.rightMargin;
                            break block10;
                        }
                        n6 = (n8 - n7 - n10 - n14) / 2 + n7 + layoutParams.leftMargin;
                        n5 = layoutParams.rightMargin;
                    }
                    n5 = n6 - n5;
                }
                n6 = n3;
                if (this.r(n4)) {
                    n6 = n3 + this.o;
                }
                n3 = n6 + layoutParams.topMargin;
                this.y(view, n5, n3 + this.o(view), n14, n13);
                n3 += n13 + layoutParams.bottomMargin + this.p(view);
                n4 += this.n(view, n4);
            }
            ++n4;
        }
    }

    public void u(View view, int n3, int n4, int n5, int n6, int n7) {
        this.measureChildWithMargins(view, n4, n5, n6, n7);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void v(int var1_1, int var2_2) {
        block57: {
            this.h = 0;
            var16_3 = this.getVirtualChildCount();
            var18_4 = View.MeasureSpec.getMode((int)var1_1);
            var22_5 = View.MeasureSpec.getMode((int)var2_2);
            if (this.k == null || this.l == null) {
                this.k = new int[4];
                this.l = new int[4];
            }
            var27_6 /* !! */  = this.k;
            var28_7 = this.l;
            var27_6 /* !! */ [3] = -1;
            var27_6 /* !! */ [2] = -1;
            var27_6 /* !! */ [1] = -1;
            var27_6 /* !! */ [0] = -1;
            var28_7[3] = -1;
            var28_7[2] = -1;
            var28_7[1] = -1;
            var28_7[0] = -1;
            var25_8 = this.c;
            var24_9 = this.j;
            var17_10 = var18_4 == 0x40000000;
            var11_16 = var9_15 = (var10_14 = (var13_13 = (var5_12 = 0)));
            var7_17 = 1;
            var3_18 = 0.0f;
            var12_20 = var8_19 = var11_16;
            block0: for (var6_11 = 0; var6_11 < var16_3; ++var6_11) {
                var29_29 /* !! */  = this.q(var6_11);
                if (var29_29 /* !! */  == null) {
                    this.h += this.w(var6_11);
lbl29:
                    // 2 sources

                    while (true) {
                        var26_28 = var24_9;
                        continue block0;
                        break;
                    }
                }
                if (var29_29 /* !! */ .getVisibility() == 8) {
                    var6_11 += this.n(var29_29 /* !! */ , var6_11);
                    ** continue;
                }
                if (this.r(var6_11)) {
                    this.h += this.n;
                }
                var30_30 = (LayoutParams)var29_29 /* !! */ .getLayoutParams();
                var4_21 = var30_30.weight;
                var3_18 += var4_21;
                if (var18_4 == 0x40000000 && var30_30.width == 0 && var4_21 > 0.0f) {
                    if (var17_10) {
                        this.h += var30_30.leftMargin + var30_30.rightMargin;
                    } else {
                        var14_22 = this.h;
                        this.h = Math.max(var14_22, var30_30.leftMargin + var14_22 + var30_30.rightMargin);
                    }
                    if (var25_8) {
                        var14_22 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                        var29_29 /* !! */ .measure(var14_22, var14_22);
                    } else {
                        var10_14 = 1;
                    }
                    var26_28 = var24_9;
                    var14_22 = var13_13;
                    var15_23 = var10_14;
                } else {
                    if (var30_30.width == 0 && var4_21 > 0.0f) {
                        var30_30.width = -2;
                        var14_22 = 0;
                    } else {
                        var14_22 = -2147483648;
                    }
                    var15_23 = var3_18 == 0.0f ? this.h : 0;
                    var26_28 = var24_9;
                    this.u(var29_29 /* !! */ , var6_11, var1_1, var15_23, var2_2, 0);
                    if (var14_22 != -2147483648) {
                        var30_30.width = var14_22;
                    }
                    var19_24 = var29_29 /* !! */ .getMeasuredWidth();
                    if (var17_10) {
                        this.h += var30_30.leftMargin + var19_24 + var30_30.rightMargin + this.p(var29_29 /* !! */ );
                    } else {
                        var14_22 = this.h;
                        this.h = Math.max(var14_22, var14_22 + var19_24 + var30_30.leftMargin + var30_30.rightMargin + this.p(var29_29 /* !! */ ));
                    }
                    var14_22 = var13_13;
                    var15_23 = var10_14;
                    if (var26_28) {
                        var14_22 = Math.max(var19_24, var13_13);
                        var15_23 = var10_14;
                    }
                }
                var26_28 = var24_9;
                if (var22_5 != 0x40000000 && var30_30.height == -1) {
                    var10_14 = 1;
                    var11_16 = 1;
                } else {
                    var10_14 = 0;
                }
                var13_13 = var30_30.topMargin + var30_30.bottomMargin;
                var19_24 = var29_29 /* !! */ .getMeasuredHeight() + var13_13;
                var20_25 = View.combineMeasuredStates((int)var9_15, (int)var29_29 /* !! */ .getMeasuredState());
                if (var25_8 && (var23_27 = var29_29 /* !! */ .getBaseline()) != -1) {
                    var9_15 = var21_26 = var30_30.gravity;
                    if (var21_26 < 0) {
                        var9_15 = this.g;
                    }
                    var9_15 = ((var9_15 & 112) >> 4 & -2) >> 1;
                    var27_6 /* !! */ [var9_15] = Math.max(var27_6 /* !! */ [var9_15], var23_27);
                    var28_7[var9_15] = Math.max(var28_7[var9_15], var19_24 - var23_27);
                }
                var9_15 = Math.max(var5_12, var19_24);
                var5_12 = var7_17 != 0 && var30_30.height == -1 ? 1 : 0;
                if (var30_30.weight > 0.0f) {
                    if (var10_14 == 0) {
                        var13_13 = var19_24;
                    }
                    var12_20 = Math.max(var12_20, var13_13);
                } else {
                    if (var10_14 == 0) {
                        var13_13 = var19_24;
                    }
                    var8_19 = Math.max(var8_19, var13_13);
                }
                var19_24 = var6_11 + this.n(var29_29 /* !! */ , var6_11);
                var6_11 = var9_15;
                var7_17 = var5_12;
                var9_15 = var20_25;
                var10_14 = var15_23;
                var13_13 = var14_22;
                var5_12 = var6_11;
                var6_11 = var19_24;
            }
            if (this.h > 0 && this.r(var16_3)) {
                this.h += this.n;
            }
            var6_11 = (var6_11 = var27_6 /* !! */ [1]) == -1 && var27_6 /* !! */ [0] == -1 && var27_6 /* !! */ [2] == -1 && var27_6 /* !! */ [3] == -1 ? var5_12 : Math.max(var5_12, Math.max(var27_6 /* !! */ [3], Math.max(var27_6 /* !! */ [0], Math.max(var6_11, var27_6 /* !! */ [2]))) + Math.max(var28_7[3], Math.max(var28_7[0], Math.max(var28_7[1], var28_7[2]))));
            if (!var24_9) break block57;
            if (var18_4 == -2147483648) ** GOTO lbl-1000
            var4_21 = var3_18;
            var14_22 = var6_11;
            if (var18_4 != 0) {
                while (true) {
                    var3_18 = var4_21;
                    var6_11 = var14_22;
                    break;
                }
            } else lbl-1000:
            // 2 sources

            {
                this.h = 0;
                var5_12 = 0;
                while (true) {
                    var4_21 = var3_18;
                    var14_22 = var6_11;
                    if (var5_12 >= var16_3) ** continue;
                    var30_30 = this.q(var5_12);
                    if (var30_30 == null) {
                        this.h += this.w(var5_12);
                    } else if (var30_30.getVisibility() == 8) {
                        var5_12 += this.n((View)var30_30, var5_12);
                    } else {
                        var29_29 /* !! */  = (LayoutParams)var30_30.getLayoutParams();
                        if (var17_10) {
                            this.h += var29_29 /* !! */ .leftMargin + var13_13 + var29_29 /* !! */ .rightMargin + this.p((View)var30_30);
                        } else {
                            var14_22 = this.h;
                            this.h = Math.max(var14_22, var14_22 + var13_13 + var29_29 /* !! */ .leftMargin + var29_29 /* !! */ .rightMargin + this.p((View)var30_30));
                        }
                    }
                    ++var5_12;
                }
            }
        }
        var5_12 = var6_11;
        this.h = var6_11 = this.h + (this.getPaddingLeft() + this.getPaddingRight());
        var6_11 = View.resolveSizeAndState((int)Math.max(var6_11, this.getSuggestedMinimumWidth()), (int)var1_1, (int)0);
        var14_22 = (0xFFFFFF & var6_11) - this.h;
        if (!(var10_14 != 0 || var14_22 != 0 && var3_18 > 0.0f)) {
            var12_20 = Math.max(var8_19, var12_20);
            if (var24_9 && var18_4 != 0x40000000) {
                for (var8_19 = 0; var8_19 < var16_3; ++var8_19) {
                    var27_6 /* !! */  = (int[])this.q(var8_19);
                    if (var27_6 /* !! */  == null || var27_6 /* !! */ .getVisibility() == 8 || !(((LayoutParams)var27_6 /* !! */ .getLayoutParams()).weight > 0.0f)) continue;
                    var27_6 /* !! */ .measure(View.MeasureSpec.makeMeasureSpec((int)var13_13, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)var27_6 /* !! */ .getMeasuredHeight(), (int)0x40000000));
                }
            }
            var10_14 = var6_11;
            var8_19 = var9_15;
            var6_11 = var12_20;
        } else {
            var4_21 = this.i;
            if (var4_21 > 0.0f) {
                var3_18 = var4_21;
            }
            var27_6 /* !! */ [3] = -1;
            var27_6 /* !! */ [2] = -1;
            var27_6 /* !! */ [1] = -1;
            var27_6 /* !! */ [0] = -1;
            var28_7[3] = -1;
            var28_7[2] = -1;
            var28_7[1] = -1;
            var28_7[0] = -1;
            this.h = 0;
            var5_12 = var9_15;
            var9_15 = -1;
            var12_20 = var14_22;
            for (var13_13 = 0; var13_13 < var16_3; ++var13_13) {
                var30_30 = this.q(var13_13);
                if (var30_30 == null || var30_30.getVisibility() == 8) {
                    var10_14 = var5_12;
                } else {
                    var29_29 /* !! */  = (LayoutParams)var30_30.getLayoutParams();
                    var4_21 = var29_29 /* !! */ .weight;
                    if (var4_21 > 0.0f) {
                        var10_14 = (int)((float)var12_20 * var4_21 / var3_18);
                        var3_18 -= var4_21;
                        var12_20 -= var10_14;
                        var15_23 = ViewGroup.getChildMeasureSpec((int)var2_2, (int)(this.getPaddingTop() + this.getPaddingBottom() + var29_29 /* !! */ .topMargin + var29_29 /* !! */ .bottomMargin), (int)var29_29 /* !! */ .height);
                        if (var29_29 /* !! */ .width == 0 && var18_4 == 0x40000000) {
                            if (var10_14 <= 0) {
                                var10_14 = 0;
                            }
                            var30_30.measure(View.MeasureSpec.makeMeasureSpec((int)var10_14, (int)0x40000000), var15_23);
                        } else {
                            var10_14 = var14_22 = var30_30.getMeasuredWidth() + var10_14;
                            if (var14_22 < 0) {
                                var10_14 = 0;
                            }
                            var30_30.measure(View.MeasureSpec.makeMeasureSpec((int)var10_14, (int)0x40000000), var15_23);
                        }
                        var10_14 = View.combineMeasuredStates((int)var5_12, (int)(var30_30.getMeasuredState() & -16777216));
                        var5_12 = var12_20;
                    } else {
                        var10_14 = var5_12;
                        var5_12 = var12_20;
                    }
                    if (var17_10) {
                        this.h += var30_30.getMeasuredWidth() + var29_29 /* !! */ .leftMargin + var29_29 /* !! */ .rightMargin + this.p((View)var30_30);
                    } else {
                        var12_20 = this.h;
                        this.h = Math.max(var12_20, var30_30.getMeasuredWidth() + var12_20 + var29_29 /* !! */ .leftMargin + var29_29 /* !! */ .rightMargin + this.p((View)var30_30));
                    }
                    var12_20 = var22_5 != 0x40000000 && var29_29 /* !! */ .height == -1 ? 1 : 0;
                    var19_24 = var29_29 /* !! */ .topMargin + var29_29 /* !! */ .bottomMargin;
                    var15_23 = var30_30.getMeasuredHeight() + var19_24;
                    var14_22 = Math.max(var9_15, var15_23);
                    var9_15 = var12_20 != 0 ? var19_24 : var15_23;
                    var9_15 = Math.max(var8_19, var9_15);
                    var7_17 = var7_17 != 0 && var29_29 /* !! */ .height == -1 ? 1 : 0;
                    if (var25_8 && (var19_24 = var30_30.getBaseline()) != -1) {
                        var8_19 = var12_20 = var29_29 /* !! */ .gravity;
                        if (var12_20 < 0) {
                            var8_19 = this.g;
                        }
                        var8_19 = ((var8_19 & 112) >> 4 & -2) >> 1;
                        var27_6 /* !! */ [var8_19] = Math.max(var27_6 /* !! */ [var8_19], var19_24);
                        var28_7[var8_19] = Math.max(var28_7[var8_19], var15_23 - var19_24);
                    }
                    var8_19 = var9_15;
                    var9_15 = var14_22;
                    var12_20 = var5_12;
                }
                var5_12 = var10_14;
            }
            var10_14 = var6_11;
            this.h += this.getPaddingLeft() + this.getPaddingRight();
            var6_11 = var27_6 /* !! */ [1];
            var6_11 = var6_11 == -1 && var27_6 /* !! */ [0] == -1 && var27_6 /* !! */ [2] == -1 && var27_6 /* !! */ [3] == -1 ? var9_15 : Math.max(var9_15, Math.max(var27_6 /* !! */ [3], Math.max(var27_6 /* !! */ [0], Math.max(var6_11, var27_6 /* !! */ [2]))) + Math.max(var28_7[3], Math.max(var28_7[0], Math.max(var28_7[1], var28_7[2]))));
            var12_20 = var6_11;
            var9_15 = var5_12;
            var6_11 = var8_19;
            var5_12 = var12_20;
            var8_19 = var9_15;
        }
        if (var7_17 != 0 || var22_5 == 0x40000000) {
            var6_11 = var5_12;
        }
        this.setMeasuredDimension(var10_14 | var8_19 & -16777216, View.resolveSizeAndState((int)Math.max(var6_11 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight()), (int)var2_2, (int)(var8_19 << 16)));
        if (var11_16 != 0) {
            this.i(var16_3, var1_1);
        }
    }

    public int w(int n3) {
        return 0;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void x(int var1_1, int var2_2) {
        block45: {
            block46: {
                this.h = 0;
                var15_3 = this.getVirtualChildCount();
                var21_4 = View.MeasureSpec.getMode((int)var1_1);
                var12_5 = View.MeasureSpec.getMode((int)var2_2);
                var22_6 = this.d;
                var23_7 = this.j;
                var11_15 = var14_14 = (var6_13 = (var5_12 = (var10_11 = (var8_10 = (var9_9 = 0)))));
                var3_16 = 0.0f;
                var7_17 = 1;
                for (var13_8 = 0; var13_8 < var15_3; ++var13_8) {
                    var25_25 /* !! */  = this.q(var13_8);
                    if (var25_25 /* !! */  == null) {
                        this.h += this.w(var13_8);
lbl14:
                        // 2 sources

                        while (true) {
                            var16_19 = var10_11;
                            var10_11 = var8_10;
                            var17_20 = var5_12;
                            var8_10 = var16_19;
                            var5_12 = var9_9;
                            var16_19 = var7_17;
                            break;
                        }
                    } else {
                        if (var25_25 /* !! */ .getVisibility() == 8) {
                            var13_8 += this.n(var25_25 /* !! */ , var13_8);
                            ** continue;
                        }
                        if (this.r(var13_8)) {
                            this.h += this.o;
                        }
                        var24_24 = (LayoutParams)var25_25 /* !! */ .getLayoutParams();
                        var4_18 = var24_24.weight;
                        var3_16 += var4_18;
                        if (var12_5 == 0x40000000 && var24_24.height == 0 && var4_18 > 0.0f) {
                            var14_14 = this.h;
                            this.h = Math.max(var14_14, var24_24.topMargin + var14_14 + var24_24.bottomMargin);
                            var14_14 = 1;
                            var16_19 = var5_12;
                        } else {
                            if (var24_24.height == 0 && var4_18 > 0.0f) {
                                var24_24.height = -2;
                                var16_19 = 0;
                            } else {
                                var16_19 = -2147483648;
                            }
                            var17_20 = var3_16 == 0.0f ? this.h : 0;
                            var18_21 = var5_12;
                            this.u(var25_25 /* !! */ , var13_8, var1_1, 0, var2_2, var17_20);
                            if (var16_19 != -2147483648) {
                                var24_24.height = var16_19;
                            }
                            var17_20 = var25_25 /* !! */ .getMeasuredHeight();
                            var16_19 = this.h;
                            this.h = Math.max(var16_19, var16_19 + var17_20 + var24_24.topMargin + var24_24.bottomMargin + this.p(var25_25 /* !! */ ));
                            if (var23_7) {
                                var8_10 = Math.max(var17_20, var8_10);
                            }
                        }
                        if (var22_6 >= 0 && var22_6 == var13_8 + 1) {
                            this.e = this.h;
                        }
                        if (var13_8 < var22_6 && var24_24.weight > 0.0f) {
                            throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                        }
                        if (var21_4 != 0x40000000 && var24_24.width == -1) {
                            var16_19 = 1;
                            var11_15 = 1;
                        } else {
                            var16_19 = 0;
                        }
                        var18_21 = var24_24.leftMargin + var24_24.rightMargin;
                        var17_20 = var25_25 /* !! */ .getMeasuredWidth() + var18_21;
                        var19_22 = Math.max(var10_11, var17_20);
                        var20_23 = View.combineMeasuredStates((int)var5_12, (int)var25_25 /* !! */ .getMeasuredState());
                        var5_12 = var7_17 != 0 && var24_24.width == -1 ? 1 : 0;
                        if (var24_24.weight > 0.0f) {
                            if (var16_19 == 0) {
                                var18_21 = var17_20;
                            }
                            var7_17 = Math.max(var9_9, var18_21);
                        } else {
                            if (var16_19 != 0) {
                                var17_20 = var18_21;
                            }
                            var6_13 = Math.max(var6_13, var17_20);
                            var7_17 = var9_9;
                        }
                        var13_8 += this.n(var25_25 /* !! */ , var13_8);
                        var10_11 = var8_10;
                        var17_20 = var20_23;
                        var16_19 = var5_12;
                        var5_12 = var7_17;
                        var8_10 = var19_22;
                    }
                    var7_17 = var8_10;
                    var9_9 = var5_12;
                    var8_10 = var10_11;
                    var10_11 = var7_17;
                    var5_12 = var17_20;
                    var7_17 = var16_19;
                }
                if (this.h > 0 && this.r(var15_3)) {
                    this.h += this.o;
                }
                var13_8 = var12_5;
                if (var23_7 && (var13_8 == -2147483648 || var13_8 == 0)) {
                    this.h = 0;
                    for (var12_5 = 0; var12_5 < var15_3; ++var12_5) {
                        var24_24 = this.q(var12_5);
                        if (var24_24 == null) {
                            this.h += this.w(var12_5);
                            continue;
                        }
                        if (var24_24.getVisibility() == 8) {
                            var12_5 += this.n((View)var24_24, var12_5);
                            continue;
                        }
                        var25_25 /* !! */  = (LayoutParams)var24_24.getLayoutParams();
                        var16_19 = this.h;
                        this.h = Math.max(var16_19, var16_19 + var8_10 + var25_25 /* !! */ .topMargin + var25_25 /* !! */ .bottomMargin + this.p((View)var24_24));
                    }
                }
                this.h = var12_5 = this.h + (this.getPaddingTop() + this.getPaddingBottom());
                var17_20 = View.resolveSizeAndState((int)Math.max(var12_5, this.getSuggestedMinimumHeight()), (int)var2_2, (int)0);
                var12_5 = (0xFFFFFF & var17_20) - this.h;
                if (var14_14 != 0 || var12_5 != 0 && var3_16 > 0.0f) break block46;
                var9_9 = var16_19 = Math.max(var6_13, var9_9);
                var14_14 = var5_12;
                var6_13 = var10_11;
                var12_5 = var7_17;
                if (!var23_7) break block45;
                var9_9 = var16_19;
                var14_14 = var5_12;
                var6_13 = var10_11;
                var12_5 = var7_17;
                if (var13_8 != 0x40000000) {
                    var13_8 = 0;
                    while (true) {
                        var9_9 = var16_19;
                        var14_14 = var5_12;
                        var6_13 = var10_11;
                        var12_5 = var7_17;
                        if (var13_8 < var15_3) {
                            var24_24 = this.q(var13_8);
                            if (var24_24 != null && var24_24.getVisibility() != 8 && ((LayoutParams)var24_24.getLayoutParams()).weight > 0.0f) {
                                var24_24.measure(View.MeasureSpec.makeMeasureSpec((int)var24_24.getMeasuredWidth(), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)var8_10, (int)0x40000000));
                            }
                            ++var13_8;
                            continue;
                        } else {
                            ** GOTO lbl-1000
                        }
                        break;
                    }
                }
                break block45;
lbl-1000:
                // 2 sources

                {
                    break block45;
                }
            }
            var4_18 = this.i;
            if (var4_18 > 0.0f) {
                var3_16 = var4_18;
            }
            this.h = 0;
            var9_9 = var12_5;
            var8_10 = var10_11;
            var10_11 = var9_9;
            for (var12_5 = 0; var12_5 < var15_3; ++var12_5) {
                var24_24 = this.q(var12_5);
                if (var24_24.getVisibility() == 8) {
                    var9_9 = var5_12;
                } else {
                    var25_25 /* !! */  = (LayoutParams)var24_24.getLayoutParams();
                    var4_18 = var25_25 /* !! */ .weight;
                    if (var4_18 > 0.0f) {
                        var9_9 = (int)((float)var10_11 * var4_18 / var3_16);
                        var3_16 -= var4_18;
                        var10_11 -= var9_9;
                        var16_19 = ViewGroup.getChildMeasureSpec((int)var1_1, (int)(this.getPaddingLeft() + this.getPaddingRight() + var25_25 /* !! */ .leftMargin + var25_25 /* !! */ .rightMargin), (int)var25_25 /* !! */ .width);
                        if (var25_25 /* !! */ .height == 0 && var13_8 == 0x40000000) {
                            if (var9_9 <= 0) {
                                var9_9 = 0;
                            }
                            var24_24.measure(var16_19, View.MeasureSpec.makeMeasureSpec((int)var9_9, (int)0x40000000));
                        } else {
                            var9_9 = var14_14 = var24_24.getMeasuredHeight() + var9_9;
                            if (var14_14 < 0) {
                                var9_9 = 0;
                            }
                            var24_24.measure(var16_19, View.MeasureSpec.makeMeasureSpec((int)var9_9, (int)0x40000000));
                        }
                        var9_9 = View.combineMeasuredStates((int)var5_12, (int)(var24_24.getMeasuredState() & -256));
                        var5_12 = var10_11;
                    } else {
                        var9_9 = var5_12;
                        var5_12 = var10_11;
                    }
                    var14_14 = var25_25 /* !! */ .leftMargin + var25_25 /* !! */ .rightMargin;
                    var16_19 = var24_24.getMeasuredWidth() + var14_14;
                    var10_11 = Math.max(var8_10, var16_19);
                    if (var21_4 != 0x40000000) {
                        var8_10 = var16_19;
                        if (var25_25 /* !! */ .width == -1) {
                            var8_10 = var14_14;
                        }
                    } else {
                        var8_10 = var16_19;
                    }
                    var8_10 = Math.max(var6_13, var8_10);
                    var6_13 = var7_17 != 0 && var25_25 /* !! */ .width == -1 ? 1 : 0;
                    var7_17 = this.h;
                    this.h = Math.max(var7_17, var7_17 + var24_24.getMeasuredHeight() + var25_25 /* !! */ .topMargin + var25_25 /* !! */ .bottomMargin + this.p((View)var24_24));
                    var7_17 = var6_13;
                    var6_13 = var8_10;
                    var8_10 = var10_11;
                    var10_11 = var5_12;
                }
                var5_12 = var9_9;
            }
            this.h += this.getPaddingTop() + this.getPaddingBottom();
            var9_9 = var6_13;
            var12_5 = var7_17;
            var6_13 = var8_10;
            var14_14 = var5_12;
        }
        var5_12 = var6_13;
        if (var12_5 == 0) {
            var5_12 = var6_13;
            if (var21_4 != 0x40000000) {
                var5_12 = var9_9;
            }
        }
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(var5_12 + (this.getPaddingLeft() + this.getPaddingRight()), this.getSuggestedMinimumWidth()), (int)var1_1, (int)var14_14), var17_20);
        if (var11_15 != 0) {
            this.j(var15_3, var2_2);
        }
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }
}

