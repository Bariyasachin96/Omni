/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Pair
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.TextView
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import c.a;
import com.google.android.material.internal.a0;
import com.google.android.material.internal.z;
import j2.d;
import v2.i;
import v2.j;
import z1.l;
import z1.m;

public class MaterialToolbar
extends Toolbar {
    public static final int d0 = z1.l.Widget_MaterialComponents_Toolbar;
    public static final ImageView.ScaleType[] e0 = new ImageView.ScaleType[]{ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    public Integer V;
    public boolean W;
    public boolean a0;
    public ImageView.ScaleType b0;
    public Boolean c0;

    public MaterialToolbar(Context context) {
        this(context, null);
    }

    public MaterialToolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.toolbarStyle);
    }

    public MaterialToolbar(Context context, AttributeSet scaleTypeArray, int n3) {
        int n4 = d0;
        super(y2.a.d(context, (AttributeSet)scaleTypeArray, n3, n4), (AttributeSet)scaleTypeArray, n3);
        context = this.getContext();
        TypedArray typedArray = com.google.android.material.internal.z.i(context, (AttributeSet)scaleTypeArray, z1.m.MaterialToolbar, n3, n4, new int[0]);
        n3 = z1.m.MaterialToolbar_navigationIconTint;
        if (typedArray.hasValue(n3)) {
            this.setNavigationIconTint(typedArray.getColor(n3, -1));
        }
        this.W = typedArray.getBoolean(z1.m.MaterialToolbar_titleCentered, false);
        this.a0 = typedArray.getBoolean(z1.m.MaterialToolbar_subtitleCentered, false);
        n3 = typedArray.getInt(z1.m.MaterialToolbar_logoScaleType, -1);
        if (n3 >= 0 && n3 < (scaleTypeArray = e0).length) {
            this.b0 = scaleTypeArray[n3];
        }
        if (typedArray.hasValue(n3 = z1.m.MaterialToolbar_logoAdjustViewBounds)) {
            this.c0 = typedArray.getBoolean(n3, false);
        }
        typedArray.recycle();
        this.Q(context);
    }

    private Drawable T(Drawable drawable) {
        Drawable drawable2 = drawable;
        if (drawable != null) {
            drawable2 = drawable;
            if (this.V != null) {
                drawable2 = h0.a.r(drawable.mutate());
                drawable2.setTint(this.V.intValue());
            }
        }
        return drawable2;
    }

    public final Pair P(TextView textView, TextView textView2) {
        int n3 = this.getMeasuredWidth();
        int n4 = n3 / 2;
        int n5 = this.getPaddingLeft();
        int n6 = n3 - this.getPaddingRight();
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            View view = this.getChildAt(i3);
            int n7 = n6;
            int n8 = n5;
            if (view.getVisibility() != 8) {
                n7 = n6;
                n8 = n5;
                if (view != textView) {
                    n7 = n6;
                    n8 = n5;
                    if (view != textView2) {
                        n3 = n5;
                        if (view.getRight() < n4) {
                            n3 = n5;
                            if (view.getRight() > n5) {
                                n3 = view.getRight();
                            }
                        }
                        n7 = n6;
                        n8 = n3;
                        if (view.getLeft() > n4) {
                            n7 = n6;
                            n8 = n3;
                            if (view.getLeft() < n6) {
                                n7 = view.getLeft();
                                n8 = n3;
                            }
                        }
                    }
                }
            }
            n6 = n7;
            n5 = n8;
        }
        return new Pair((Object)n5, (Object)n6);
    }

    public final void Q(Context context) {
        Drawable drawable = this.getBackground();
        drawable = drawable == null ? ColorStateList.valueOf((int)0) : j2.d.g(drawable);
        if (drawable != null) {
            i i3 = new i();
            i3.i0((ColorStateList)drawable);
            i3.W(context);
            i3.h0(this.getElevation());
            this.setBackground(i3);
        }
    }

    public final void R(View view, Pair pair) {
        int n3 = this.getMeasuredWidth();
        int n4 = view.getMeasuredWidth();
        int n5 = n3 / 2 - n4 / 2;
        int n6 = n4 + n5;
        int n7 = Math.max(Math.max((Integer)pair.first - n5, 0), Math.max(n6 - (Integer)pair.second, 0));
        n3 = n5;
        n4 = n6;
        if (n7 > 0) {
            n3 = n5 + n7;
            n4 = n6 - n7;
            view.measure(View.MeasureSpec.makeMeasureSpec((int)(n4 - n3), (int)0x40000000), view.getMeasuredHeightAndState());
        }
        view.layout(n3, view.getTop(), n4, view.getBottom());
    }

    public final void S() {
        if (this.W || this.a0) {
            TextView textView = com.google.android.material.internal.a0.g(this);
            TextView textView2 = com.google.android.material.internal.a0.e(this);
            if (textView != null || textView2 != null) {
                Pair pair = this.P(textView, textView2);
                if (this.W && textView != null) {
                    this.R((View)textView, pair);
                }
                if (this.a0 && textView2 != null) {
                    this.R((View)textView2, pair);
                }
            }
        }
    }

    public final void U() {
        ImageView imageView = com.google.android.material.internal.a0.c(this);
        if (imageView != null) {
            Boolean bl = this.c0;
            if (bl != null) {
                imageView.setAdjustViewBounds(bl.booleanValue());
            }
            if ((bl = this.b0) != null) {
                imageView.setScaleType((ImageView.ScaleType)bl);
            }
        }
    }

    public ImageView.ScaleType getLogoScaleType() {
        return this.b0;
    }

    public Integer getNavigationIconTint() {
        return this.V;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.e((View)this);
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        this.S();
        this.U();
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        v2.j.d((View)this, f3);
    }

    public void setLogoAdjustViewBounds(boolean bl) {
        Boolean bl2 = this.c0;
        if (bl2 != null && bl2 == bl) {
            return;
        }
        this.c0 = bl;
        this.requestLayout();
    }

    public void setLogoScaleType(ImageView.ScaleType scaleType) {
        if (this.b0 != scaleType) {
            this.b0 = scaleType;
            this.requestLayout();
        }
    }

    @Override
    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(this.T(drawable));
    }

    public void setNavigationIconTint(int n3) {
        this.V = n3;
        Drawable drawable = this.getNavigationIcon();
        if (drawable != null) {
            this.setNavigationIcon(drawable);
        }
    }

    public void setSubtitleCentered(boolean bl) {
        if (this.a0 != bl) {
            this.a0 = bl;
            this.requestLayout();
        }
    }

    public void setTitleCentered(boolean bl) {
        if (this.W != bl) {
            this.W = bl;
            this.requestLayout();
        }
    }
}

