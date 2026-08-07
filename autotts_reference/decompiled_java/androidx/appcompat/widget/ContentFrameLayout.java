/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

public class ContentFrameLayout
extends FrameLayout {
    public TypedValue c;
    public TypedValue d;
    public TypedValue e;
    public TypedValue f;
    public TypedValue g;
    public TypedValue h;
    public final Rect i = new Rect();
    public a j;

    public ContentFrameLayout(Context context) {
        this(context, null);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    public TypedValue getFixedHeightMajor() {
        if (this.g == null) {
            this.g = new TypedValue();
        }
        return this.g;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.h == null) {
            this.h = new TypedValue();
        }
        return this.h;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.e == null) {
            this.e = new TypedValue();
        }
        return this.e;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.f == null) {
            this.f = new TypedValue();
        }
        return this.f;
    }

    public TypedValue getMinWidthMajor() {
        if (this.c == null) {
            this.c = new TypedValue();
        }
        return this.c;
    }

    public TypedValue getMinWidthMinor() {
        if (this.d == null) {
            this.d = new TypedValue();
        }
        return this.d;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a a4 = this.j;
        if (a4 != null) {
            a4.a();
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a a4 = this.j;
        if (a4 != null) {
            a4.onDetachedFromWindow();
        }
    }

    /*
     * Unable to fully structure code
     */
    public void onMeasure(int var1_1, int var2_2) {
        var11_3 = this.getContext().getResources().getDisplayMetrics();
        var4_4 = var11_3.widthPixels;
        var5_5 = var11_3.heightPixels;
        var8_6 = 1;
        var4_4 = var4_4 < var5_5 ? 1 : 0;
        var9_7 = View.MeasureSpec.getMode((int)var1_1);
        var7_8 = View.MeasureSpec.getMode((int)var2_2);
        if (var9_7 != -2147483648 || (var10_9 = var4_4 != 0 ? this.f : this.e) == null || (var5_5 = var10_9.type) == 0) ** GOTO lbl-1000
        if (var5_5 == 5) {
            var3_10 = var10_9.getDimension(var11_3);
lbl11:
            // 2 sources

            while (true) {
                var5_5 = (int)var3_10;
                break;
            }
        } else {
            if (var5_5 == 6) {
                var5_5 = var11_3.widthPixels;
                var3_10 = var10_9.getFraction((float)var5_5, (float)var5_5);
                ** continue;
            }
            var5_5 = 0;
        }
        if (var5_5 > 0) {
            var10_9 = this.i;
            var6_11 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var5_5 - (var10_9.left + var10_9.right), View.MeasureSpec.getSize((int)var1_1)), (int)0x40000000);
            var1_1 = 1;
        } else lbl-1000:
        // 2 sources

        {
            var5_5 = 0;
            var6_11 = var1_1;
            var1_1 = var5_5;
        }
        var5_5 = var2_2;
        if (var7_8 == -2147483648) {
            var10_9 = var4_4 != 0 ? this.g : this.h;
            var5_5 = var2_2;
            if (var10_9 != null) {
                var7_8 = var10_9.type;
                var5_5 = var2_2;
                if (var7_8 != 0) {
                    if (var7_8 == 5) {
                        var3_10 = var10_9.getDimension(var11_3);
lbl37:
                        // 2 sources

                        while (true) {
                            var7_8 = (int)var3_10;
                            break;
                        }
                    } else {
                        if (var7_8 == 6) {
                            var5_5 = var11_3.heightPixels;
                            var3_10 = var10_9.getFraction((float)var5_5, (float)var5_5);
                            ** continue;
                        }
                        var7_8 = 0;
                    }
                    var5_5 = var2_2;
                    if (var7_8 > 0) {
                        var10_9 = this.i;
                        var5_5 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var7_8 - (var10_9.top + var10_9.bottom), View.MeasureSpec.getSize((int)var2_2)), (int)0x40000000);
                    }
                }
            }
        }
        super.onMeasure(var6_11, var5_5);
        var7_8 = this.getMeasuredWidth();
        var6_11 = View.MeasureSpec.makeMeasureSpec((int)var7_8, (int)0x40000000);
        if (var1_1 != 0 || var9_7 != -2147483648 || (var10_9 = var4_4 != 0 ? this.d : this.c) == null || (var1_1 = var10_9.type) == 0) ** GOTO lbl-1000
        if (var1_1 == 5) {
            var3_10 = var10_9.getDimension(var11_3);
lbl55:
            // 2 sources

            while (true) {
                var1_1 = (int)var3_10;
                break;
            }
        } else {
            if (var1_1 == 6) {
                var1_1 = var11_3.widthPixels;
                var3_10 = var10_9.getFraction((float)var1_1, (float)var1_1);
                ** continue;
            }
            var1_1 = 0;
        }
        var2_2 = var1_1;
        if (var1_1 > 0) {
            var10_9 = this.i;
            var2_2 = var1_1 - (var10_9.left + var10_9.right);
        }
        if (var7_8 < var2_2) {
            var1_1 = View.MeasureSpec.makeMeasureSpec((int)var2_2, (int)0x40000000);
            var2_2 = var8_6;
        } else lbl-1000:
        // 2 sources

        {
            var2_2 = 0;
            var1_1 = var6_11;
        }
        if (var2_2 != 0) {
            super.onMeasure(var1_1, var5_5);
        }
    }

    public void setAttachListener(a a4) {
        this.j = a4;
    }

    public void setDecorPadding(int n3, int n4, int n5, int n6) {
        this.i.set(n3, n4, n5, n6);
        if (this.isLaidOut()) {
            this.requestLayout();
        }
    }

    public static interface a {
        public void a();

        public void onDetachedFromWindow();
    }
}

