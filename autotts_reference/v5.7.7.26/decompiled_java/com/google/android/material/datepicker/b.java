/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import com.google.android.material.datepicker.a;
import com.google.android.material.datepicker.j;
import z1.c;
import z1.m;

public final class b {
    public final a a;
    public final a b;
    public final a c;
    public final a d;
    public final a e;
    public final a f;
    public final a g;
    public final Paint h;

    public b(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(s2.b.f(context, z1.c.materialCalendarStyle, j.class.getCanonicalName()), m.MaterialCalendar);
        this.a = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_dayStyle, 0));
        this.g = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_dayInvalidStyle, 0));
        this.b = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_daySelectedStyle, 0));
        this.c = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_dayTodayStyle, 0));
        ColorStateList colorStateList = s2.c.a(context, typedArray, m.MaterialCalendar_rangeFillColor);
        this.d = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_yearStyle, 0));
        this.e = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_yearSelectedStyle, 0));
        this.f = com.google.android.material.datepicker.a.a(context, typedArray.getResourceId(m.MaterialCalendar_yearTodayStyle, 0));
        context = new Paint();
        this.h = context;
        context.setColor(colorStateList.getDefaultColor());
        typedArray.recycle();
    }
}

