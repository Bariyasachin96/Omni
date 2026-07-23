/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.widget.TextView;
import n0.h;
import s2.c;
import v2.i;
import v2.o;
import z1.m;

public final class a {
    public final Rect a;
    public final ColorStateList b;
    public final ColorStateList c;
    public final ColorStateList d;
    public final int e;
    public final o f;

    public a(ColorStateList colorStateList, ColorStateList colorStateList2, ColorStateList colorStateList3, int n3, o o3, Rect rect) {
        h.d(rect.left);
        h.d(rect.top);
        h.d(rect.right);
        h.d(rect.bottom);
        this.a = rect;
        this.b = colorStateList2;
        this.c = colorStateList;
        this.d = colorStateList3;
        this.e = n3;
        this.f = o3;
    }

    public static a a(Context object, int n3) {
        boolean bl = n3 != 0;
        h.b(bl, "Cannot create a CalendarItemStyle with a styleResId of 0");
        TypedArray typedArray = object.obtainStyledAttributes(n3, m.MaterialCalendarItem);
        Rect rect = new Rect(typedArray.getDimensionPixelOffset(m.MaterialCalendarItem_android_insetLeft, 0), typedArray.getDimensionPixelOffset(m.MaterialCalendarItem_android_insetTop, 0), typedArray.getDimensionPixelOffset(m.MaterialCalendarItem_android_insetRight, 0), typedArray.getDimensionPixelOffset(m.MaterialCalendarItem_android_insetBottom, 0));
        ColorStateList colorStateList = s2.c.a(object, typedArray, m.MaterialCalendarItem_itemFillColor);
        ColorStateList colorStateList2 = s2.c.a(object, typedArray, m.MaterialCalendarItem_itemTextColor);
        ColorStateList colorStateList3 = s2.c.a(object, typedArray, m.MaterialCalendarItem_itemStrokeColor);
        n3 = typedArray.getDimensionPixelSize(m.MaterialCalendarItem_itemStrokeWidth, 0);
        object = o.b(object, typedArray.getResourceId(m.MaterialCalendarItem_itemShapeAppearance, 0), typedArray.getResourceId(m.MaterialCalendarItem_itemShapeAppearanceOverlay, 0)).m();
        typedArray.recycle();
        return new a(colorStateList, colorStateList2, colorStateList3, n3, (o)object, rect);
    }

    public int b() {
        return this.a.bottom;
    }

    public int c() {
        return this.a.top;
    }

    public void d(TextView textView) {
        this.e(textView, null, null);
    }

    public void e(TextView textView, ColorStateList colorStateList, ColorStateList colorStateList2) {
        i i3 = new i();
        i i4 = new i();
        i3.setShapeAppearanceModel(this.f);
        i4.setShapeAppearanceModel(this.f);
        if (colorStateList == null) {
            colorStateList = this.c;
        }
        i3.i0(colorStateList);
        i3.t0(this.e, this.d);
        if (colorStateList2 == null) {
            colorStateList2 = this.b;
        }
        textView.setTextColor(colorStateList2);
        colorStateList2 = new RippleDrawable(this.b.withAlpha(30), (Drawable)i3, (Drawable)i4);
        colorStateList = this.a;
        textView.setBackground((Drawable)new InsetDrawable((Drawable)colorStateList2, colorStateList.left, colorStateList.top, colorStateList.right, colorStateList.bottom));
    }
}

