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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.datepicker.MaterialCalendarGridView;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.a;
import com.google.android.material.datepicker.b;
import com.google.android.material.datepicker.h;
import com.google.android.material.datepicker.v;
import java.util.Collection;
import java.util.Iterator;
import n0.d;
import z1.i;

public class p
extends BaseAdapter {
    public static final int i = v.m().getMaximum(4);
    public static final int j = v.m().getMaximum(5) + v.m().getMaximum(7) - 1;
    public final Month c;
    public final DateSelector d;
    public Collection e;
    public b f;
    public final CalendarConstraints g;
    public final DayViewDecorator h;

    public p(Month month, DateSelector dateSelector, CalendarConstraints calendarConstraints, DayViewDecorator dayViewDecorator) {
        this.c = month;
        this.d = dateSelector;
        this.g = calendarConstraints;
        this.h = dayViewDecorator;
        this.e = dateSelector.i();
    }

    public int a(int n3) {
        return this.b() + (n3 - 1);
    }

    public int b() {
        return this.c.s(this.g.w());
    }

    public final String c(Context context, long l3) {
        return com.google.android.material.datepicker.h.e(context, l3, this.l(l3), this.k(l3), this.g(l3));
    }

    public Long d(int n3) {
        if (n3 >= this.b() && n3 <= this.m()) {
            return this.c.t(this.n(n3));
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public TextView e(int var1_1, View var2_2, ViewGroup var3_3) {
        this.f(var3_3.getContext());
        var5_4 = (TextView)var2_2;
        if (var2_2 == null) {
            var5_4 = (TextView)LayoutInflater.from((Context)var3_3.getContext()).inflate(z1.i.mtrl_calendar_day, var3_3, false);
        }
        if ((var4_5 = var1_1 - this.b()) < 0) ** GOTO lbl-1000
        var2_2 = this.c;
        if (var4_5 < var2_2.g) {
            var5_4.setTag(var2_2);
            var5_4.setText((CharSequence)String.format(var5_4.getResources().getConfiguration().locale, "%d", new Object[]{++var4_5}));
            var5_4.setVisibility(0);
            var5_4.setEnabled(true);
        } else lbl-1000:
        // 2 sources

        {
            var5_4.setVisibility(8);
            var5_4.setEnabled(false);
            var4_5 = -1;
        }
        var2_2 = this.d(var1_1);
        if (var2_2 == null) {
            return var5_4;
        }
        this.o(var5_4, var2_2.longValue(), var4_5);
        return var5_4;
    }

    public final void f(Context context) {
        if (this.f == null) {
            this.f = new b(context);
        }
    }

    public boolean g(long l3) {
        Iterator iterator = this.d.f().iterator();
        while (iterator.hasNext()) {
            Object object = ((d)iterator.next()).b;
            if (object == null || (Long)object != l3) continue;
            return true;
        }
        return false;
    }

    public int getCount() {
        return j;
    }

    public long getItemId(int n3) {
        return n3 / this.c.f;
    }

    public boolean h(int n3) {
        return n3 % this.c.f == 0;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean i(int n3) {
        return (n3 + 1) % this.c.f == 0;
    }

    public final boolean j(long l3) {
        Iterator iterator = this.d.i().iterator();
        while (iterator.hasNext()) {
            long l4 = (Long)iterator.next();
            if (v.a(l3) != v.a(l4)) continue;
            return true;
        }
        return false;
    }

    public boolean k(long l3) {
        Iterator iterator = this.d.f().iterator();
        while (iterator.hasNext()) {
            Object object = ((d)iterator.next()).a;
            if (object == null || (Long)object != l3) continue;
            return true;
        }
        return false;
    }

    public final boolean l(long l3) {
        return v.k().getTimeInMillis() == l3;
    }

    public int m() {
        return this.b() + this.c.g - 1;
    }

    public int n(int n3) {
        return n3 - this.b() + 1;
    }

    public final void o(TextView textView, long l3, int n3) {
        a a4;
        boolean bl;
        if (textView == null) {
            return;
        }
        Context context = textView.getContext();
        String string = this.c(context, l3);
        textView.setContentDescription((CharSequence)string);
        boolean bl2 = this.g.u().g(l3);
        if (bl2) {
            textView.setEnabled(true);
            bl = this.j(l3);
            textView.setSelected(bl);
            a4 = bl ? this.f.b : (this.l(l3) ? this.f.c : this.f.a);
        } else {
            bl = false;
            textView.setEnabled(false);
            a4 = this.f.g;
        }
        DayViewDecorator dayViewDecorator = this.h;
        if (dayViewDecorator != null && n3 != -1) {
            Month month = this.c;
            int n4 = month.e;
            int n5 = month.d;
            a4.e(textView, dayViewDecorator.o(context, n4, n5, n3, bl2, bl), this.h.u(context, n4, n5, n3, bl2, bl));
            textView.setCompoundDrawables(this.h.q(context, n4, n5, n3, bl2, bl), this.h.s(context, n4, n5, n3, bl2, bl), this.h.r(context, n4, n5, n3, bl2, bl), this.h.p(context, n4, n5, n3, bl2, bl));
            textView.setContentDescription(this.h.t(context, n4, n5, n3, bl2, bl, string));
            return;
        }
        a4.d(textView);
    }

    public final void p(MaterialCalendarGridView materialCalendarGridView, long l3) {
        if (Month.q(l3).equals(this.c)) {
            int n3 = this.c.u(l3);
            this.o((TextView)materialCalendarGridView.getChildAt(materialCalendarGridView.b().a(n3) - materialCalendarGridView.getFirstVisiblePosition()), l3, n3);
        }
    }

    public void q(MaterialCalendarGridView materialCalendarGridView) {
        Object object = this.e.iterator();
        while (object.hasNext()) {
            this.p(materialCalendarGridView, (Long)object.next());
        }
        object = this.d;
        if (object != null) {
            object = object.i().iterator();
            while (object.hasNext()) {
                this.p(materialCalendarGridView, (Long)object.next());
            }
            this.e = this.d.i();
        }
    }

    public boolean r(int n3) {
        return n3 >= this.b() && n3 <= this.m();
    }
}

