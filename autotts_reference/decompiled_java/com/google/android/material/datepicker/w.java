/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.a;
import com.google.android.material.datepicker.h;
import com.google.android.material.datepicker.j;
import com.google.android.material.datepicker.v;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import z1.i;

public class w
extends RecyclerView.h {
    public final j d;

    public w(j j3) {
        this.d = j3;
    }

    public final View.OnClickListener A(int n3) {
        return new View.OnClickListener(this, n3){
            public final int c;
            public final w d;
            {
                this.d = w3;
                this.c = n3;
            }

            public void onClick(View object) {
                object = Month.p(this.c, ((w)this.d).d.T1().d);
                object = this.d.d.R1().t((Month)object);
                this.d.d.b2((Month)object);
                this.d.d.c2(j.l.c);
                this.d.d.a2();
            }
        };
    }

    public int B(int n3) {
        return n3 - this.d.R1().z().e;
    }

    public int C(int n3) {
        return this.d.R1().z().e + n3;
    }

    public void D(b b3, int n3) {
        n3 = this.C(n3);
        b3.u.setText((CharSequence)String.format(Locale.getDefault(), "%d", n3));
        Object object = b3.u;
        object.setContentDescription((CharSequence)h.k(object.getContext(), n3));
        com.google.android.material.datepicker.b b4 = this.d.S1();
        Calendar calendar = v.k();
        boolean bl = true;
        object = calendar.get(1) == n3 ? b4.f : b4.d;
        Iterator iterator = this.d.U1().i().iterator();
        while (iterator.hasNext()) {
            calendar.setTimeInMillis((Long)iterator.next());
            if (calendar.get(1) != n3) continue;
            object = b4.e;
        }
        ((a)object).d(b3.u);
        calendar = b3.u;
        if (object != b4.e) {
            bl = false;
        }
        calendar.setSelected(bl);
        b3.u.setOnClickListener(this.A(n3));
    }

    public b E(ViewGroup viewGroup, int n3) {
        return new b((TextView)LayoutInflater.from((Context)viewGroup.getContext()).inflate(i.mtrl_calendar_year, viewGroup, false));
    }

    @Override
    public int f() {
        return this.d.R1().A();
    }

    public static class b
    extends RecyclerView.d0 {
        public final TextView u;

        public b(TextView textView) {
            super((View)textView);
            this.u = textView;
        }
    }
}

