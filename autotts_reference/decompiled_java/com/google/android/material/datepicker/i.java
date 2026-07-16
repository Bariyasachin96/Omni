/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.material.datepicker.v;
import java.util.Calendar;
import java.util.Locale;
import z1.k;

public class i
extends BaseAdapter {
    public static final int f = 4;
    public final Calendar c;
    public final int d;
    public final int e;

    public i() {
        Calendar calendar;
        this.c = calendar = v.m();
        this.d = calendar.getMaximum(7);
        this.e = calendar.getFirstDayOfWeek();
    }

    public i(int n3) {
        Calendar calendar;
        this.c = calendar = v.m();
        this.d = calendar.getMaximum(7);
        this.e = n3;
    }

    public Integer a(int n3) {
        if (n3 >= this.d) {
            return null;
        }
        return this.b(n3);
    }

    public final int b(int n3) {
        int n4 = n3 + this.e;
        int n5 = this.d;
        n3 = n4;
        if (n4 > n5) {
            n3 = n4 - n5;
        }
        return n3;
    }

    public int getCount() {
        return this.d;
    }

    public long getItemId(int n3) {
        return 0L;
    }

    public View getView(int n3, View object, ViewGroup viewGroup) {
        TextView textView = (TextView)object;
        if (object == null) {
            textView = (TextView)LayoutInflater.from((Context)viewGroup.getContext()).inflate(z1.i.mtrl_calendar_day_of_week, viewGroup, false);
        }
        this.c.set(7, this.b(n3));
        object = textView.getResources().getConfiguration().locale;
        textView.setText((CharSequence)this.c.getDisplayName(7, f, (Locale)object));
        textView.setContentDescription((CharSequence)String.format(viewGroup.getContext().getString(k.mtrl_picker_day_of_week_column_header), this.c.getDisplayName(7, 2, Locale.getDefault())));
        return textView;
    }
}

