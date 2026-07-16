/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.datepicker.MaterialCalendarGridView;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.j;
import com.google.android.material.datepicker.n;
import com.google.android.material.datepicker.p;
import o0.x0;
import z1.g;
import z1.i;

public class q
extends RecyclerView.h {
    public final CalendarConstraints d;
    public final DateSelector e;
    public final DayViewDecorator f;
    public final j.m g;
    public final int h;

    public q(Context context, DateSelector dateSelector, CalendarConstraints calendarConstraints, DayViewDecorator dayViewDecorator, j.m m3) {
        Month month = calendarConstraints.z();
        Month month2 = calendarConstraints.v();
        Month month3 = calendarConstraints.y();
        if (month.o(month3) <= 0) {
            if (month3.o(month2) <= 0) {
                int n3 = p.i;
                int n4 = j.V1(context);
                int n5 = n.e2(context) ? j.V1(context) : 0;
                this.h = n3 * n4 + n5;
                this.d = calendarConstraints;
                this.e = dateSelector;
                this.f = dayViewDecorator;
                this.g = m3;
                this.x(true);
                return;
            }
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
        throw new IllegalArgumentException("firstPage cannot be after currentPage");
    }

    public Month A(int n3) {
        return this.d.z().x(n3);
    }

    public CharSequence B(int n3) {
        return this.A(n3).v();
    }

    public int C(Month month) {
        return this.d.z().y(month);
    }

    public void D(b object, int n3) {
        Month month = this.d.z().x(n3);
        ((b)object).u.setText((CharSequence)month.v());
        object = (MaterialCalendarGridView)((b)object).v.findViewById(z1.g.month_grid);
        if (((MaterialCalendarGridView)((Object)object)).b() != null && month.equals(((MaterialCalendarGridView)((Object)object)).b().c)) {
            object.invalidate();
            ((MaterialCalendarGridView)((Object)object)).b().q((MaterialCalendarGridView)((Object)object));
        } else {
            p p3 = new p(month, this.e, this.d, this.f);
            object.setNumColumns(month.f);
            ((MaterialCalendarGridView)((Object)object)).setAdapter((ListAdapter)p3);
        }
        object.setOnItemClickListener(new AdapterView.OnItemClickListener(this, (MaterialCalendarGridView)((Object)object)){
            public final MaterialCalendarGridView c;
            public final q d;
            {
                this.d = q3;
                this.c = materialCalendarGridView;
            }

            public void onItemClick(AdapterView adapterView, View view, int n3, long l3) {
                if (this.c.b().r(n3)) {
                    this.d.g.a(this.c.b().d(n3));
                }
            }
        });
    }

    public b E(ViewGroup viewGroup, int n3) {
        LinearLayout linearLayout = (LinearLayout)LayoutInflater.from((Context)viewGroup.getContext()).inflate(i.mtrl_calendar_month_labeled, viewGroup, false);
        if (n.e2(viewGroup.getContext())) {
            linearLayout.setLayoutParams((ViewGroup.LayoutParams)new RecyclerView.LayoutParams(-1, this.h));
            return new b(linearLayout, true);
        }
        return new b(linearLayout, false);
    }

    @Override
    public int f() {
        return this.d.x();
    }

    @Override
    public long g(int n3) {
        return this.d.z().x(n3).w();
    }

    public static class b
    extends RecyclerView.d0 {
        public final TextView u;
        public final MaterialCalendarGridView v;

        public b(LinearLayout linearLayout, boolean bl) {
            super((View)linearLayout);
            TextView textView;
            this.u = textView = (TextView)linearLayout.findViewById(z1.g.month_title);
            x0.i0((View)textView, true);
            this.v = (MaterialCalendarGridView)linearLayout.findViewById(z1.g.month_grid);
            if (!bl) {
                textView.setVisibility(8);
            }
        }
    }
}

