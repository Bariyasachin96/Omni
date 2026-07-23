/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.ContextThemeWrapper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.GridView
 *  android.widget.ListAdapter
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.b;
import com.google.android.material.datepicker.i;
import com.google.android.material.datepicker.n;
import com.google.android.material.datepicker.p;
import com.google.android.material.datepicker.q;
import com.google.android.material.datepicker.r;
import com.google.android.material.datepicker.s;
import com.google.android.material.datepicker.t;
import com.google.android.material.datepicker.v;
import com.google.android.material.datepicker.w;
import java.util.AbstractCollection;
import java.util.Calendar;
import java.util.Iterator;
import n0.d;
import o0.a;
import o0.x0;
import p0.s;
import z1.e;
import z1.g;
import z1.h;
import z1.k;

public final class j<S>
extends s {
    public static final Object u0 = "MONTHS_VIEW_GROUP_TAG";
    public static final Object v0 = "NAVIGATION_PREV_TAG";
    public static final Object w0 = "NAVIGATION_NEXT_TAG";
    public static final Object x0 = "SELECTOR_TOGGLE_TAG";
    public int f0;
    public DateSelector g0;
    public CalendarConstraints h0;
    public DayViewDecorator i0;
    public Month j0;
    public l k0;
    public b l0;
    public RecyclerView m0;
    public RecyclerView n0;
    public View o0;
    public View p0;
    public View q0;
    public View r0;
    public MaterialButton s0;
    public AccessibilityManager t0;

    public static /* synthetic */ Month M1(j j3, Month month) {
        j3.j0 = month;
        return month;
    }

    public static int V1(Context context) {
        return context.getResources().getDimensionPixelSize(z1.e.mtrl_calendar_day_height);
    }

    public static int W1(Context context) {
        context = context.getResources();
        int n3 = context.getDimensionPixelSize(z1.e.mtrl_calendar_navigation_height);
        int n4 = context.getDimensionPixelOffset(z1.e.mtrl_calendar_navigation_top_padding);
        int n5 = context.getDimensionPixelOffset(z1.e.mtrl_calendar_navigation_bottom_padding);
        int n6 = context.getDimensionPixelSize(z1.e.mtrl_calendar_days_of_week_height);
        int n7 = com.google.android.material.datepicker.p.i;
        return n3 + n4 + n5 + n6 + (context.getDimensionPixelSize(z1.e.mtrl_calendar_day_height) * n7 + (n7 - 1) * context.getDimensionPixelOffset(z1.e.mtrl_calendar_month_vertical_padding)) + context.getDimensionPixelOffset(z1.e.mtrl_calendar_bottom_padding);
    }

    public static j Y1(DateSelector dateSelector, int n3, CalendarConstraints calendarConstraints, DayViewDecorator dayViewDecorator) {
        j j3 = new j();
        Bundle bundle = new Bundle();
        bundle.putInt("THEME_RES_ID_KEY", n3);
        bundle.putParcelable("GRID_SELECTOR_KEY", (Parcelable)dateSelector);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", (Parcelable)calendarConstraints);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", (Parcelable)dayViewDecorator);
        bundle.putParcelable("CURRENT_MONTH_KEY", (Parcelable)calendarConstraints.y());
        j3.t1(bundle);
        return j3;
    }

    @Override
    public boolean E1(r r3) {
        return super.E1(r3);
    }

    @Override
    public void G0(Bundle bundle) {
        super.G0(bundle);
        bundle.putInt("THEME_RES_ID_KEY", this.f0);
        bundle.putParcelable("GRID_SELECTOR_KEY", (Parcelable)this.g0);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", (Parcelable)this.h0);
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", (Parcelable)this.i0);
        bundle.putParcelable("CURRENT_MONTH_KEY", (Parcelable)this.j0);
    }

    public final void P1(View view, q q3) {
        MaterialButton materialButton;
        this.s0 = materialButton = (MaterialButton)view.findViewById(z1.g.month_navigation_fragment_toggle);
        materialButton.setTag(x0);
        o0.x0.h0((View)this.s0, new a(this){
            public final j d;
            {
                this.d = j3;
            }

            @Override
            public void g(View object, p0.s s3) {
                super.g((View)object, s3);
                object = this.d.r0.getVisibility() == 0 ? this.d.Q(z1.k.mtrl_picker_toggle_to_year_selection) : this.d.Q(z1.k.mtrl_picker_toggle_to_day_selection);
                s3.b(new s.a(16, (CharSequence)object));
            }
        });
        materialButton = view.findViewById(z1.g.month_navigation_previous);
        this.o0 = materialButton;
        materialButton.setTag(v0);
        materialButton = view.findViewById(z1.g.month_navigation_next);
        this.p0 = materialButton;
        materialButton.setTag(w0);
        this.q0 = view.findViewById(z1.g.mtrl_calendar_year_selector_frame);
        this.r0 = view.findViewById(z1.g.mtrl_calendar_day_selector_frame);
        this.c2(com.google.android.material.datepicker.j$l.c);
        this.s0.setText(this.j0.v());
        this.n0.n(new RecyclerView.t(this, q3){
            public final q a;
            public final j b;
            {
                this.b = j3;
                this.a = q3;
            }

            @Override
            public void b(RecyclerView object, int n3, int n4) {
                n3 = n3 < 0 ? this.b.X1().c2() : this.b.X1().e2();
                object = this.a.A(n3);
                com.google.android.material.datepicker.j.M1(this.b, (Month)object);
                this.b.s0.setText(this.a.B(n3));
                n3 = this.a.C((Month)object);
                this.b.f2(n3);
            }
        });
        this.s0.setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View view) {
                this.c.e2();
            }
        });
        this.p0.setOnClickListener(new View.OnClickListener(this, q3){
            public final q c;
            public final j d;
            {
                this.d = j3;
                this.c = q3;
            }

            public void onClick(View view) {
                int n3 = this.d.X1().c2();
                this.d.b2(this.c.A(n3 + 1));
            }
        });
        this.o0.setOnClickListener(new View.OnClickListener(this, q3){
            public final q c;
            public final j d;
            {
                this.d = j3;
                this.c = q3;
            }

            public void onClick(View view) {
                int n3 = this.d.X1().e2();
                this.d.b2(this.c.A(n3 - 1));
            }
        });
        this.f2(q3.C(this.j0));
    }

    public final RecyclerView.o Q1() {
        return new RecyclerView.o(this){
            public final Calendar a;
            public final Calendar b;
            public final j c;
            {
                this.c = j3;
                this.a = com.google.android.material.datepicker.v.m();
                this.b = com.google.android.material.datepicker.v.m();
            }

            @Override
            public void g(Canvas canvas, RecyclerView recyclerView, RecyclerView.z object) {
                if (recyclerView.getAdapter() instanceof w && recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    w w3 = (w)recyclerView.getAdapter();
                    GridLayoutManager gridLayoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
                    for (d d3 : this.c.g0.f()) {
                        Object object2 = d3.a;
                        if (object2 == null || d3.b == null) continue;
                        this.a.setTimeInMillis((Long)object2);
                        this.b.setTimeInMillis((Long)d3.b);
                        int n3 = w3.B(this.a.get(1));
                        int n4 = w3.B(this.b.get(1));
                        d3 = gridLayoutManager.H(n3);
                        object2 = gridLayoutManager.H(n4);
                        int n5 = n3 / gridLayoutManager.X2();
                        int n6 = n4 / gridLayoutManager.X2();
                        for (n4 = n5; n4 <= n6; ++n4) {
                            View view = gridLayoutManager.H(gridLayoutManager.X2() * n4);
                            if (view == null) continue;
                            int n7 = view.getTop();
                            int n8 = ((j)this.c).l0.d.c();
                            int n9 = view.getBottom();
                            int n10 = ((j)this.c).l0.d.b();
                            n3 = n4 == n5 && d3 != null ? d3.getLeft() + d3.getWidth() / 2 : 0;
                            int n11 = n4 == n6 && object2 != null ? object2.getLeft() + object2.getWidth() / 2 : recyclerView.getWidth();
                            canvas.drawRect((float)n3, (float)(n7 + n8), (float)n11, (float)(n9 - n10), ((j)this.c).l0.h);
                        }
                    }
                }
            }
        };
    }

    public CalendarConstraints R1() {
        return this.h0;
    }

    public b S1() {
        return this.l0;
    }

    public Month T1() {
        return this.j0;
    }

    public DateSelector U1() {
        return this.g0;
    }

    public LinearLayoutManager X1() {
        return (LinearLayoutManager)this.n0.getLayoutManager();
    }

    public final void Z1(int n3) {
        this.n0.post(new Runnable(this, n3){
            public final int c;
            public final j d;
            {
                this.d = j3;
                this.c = n3;
            }

            @Override
            public void run() {
                this.d.n0.A1(this.c);
            }
        });
    }

    public void a2() {
        MaterialButton materialButton = this.s0;
        if (materialButton != null) {
            materialButton.sendAccessibilityEvent(8);
        }
    }

    public void b2(Month month) {
        q q3 = (q)this.n0.getAdapter();
        int n3 = q3.C(month);
        AccessibilityManager accessibilityManager = this.t0;
        if (accessibilityManager != null && accessibilityManager.isEnabled()) {
            this.j0 = month;
            this.n0.r1(n3);
        } else {
            int n4 = n3 - q3.C(this.j0);
            int n5 = Math.abs(n4);
            boolean bl = false;
            n5 = n5 > 3 ? 1 : 0;
            if (n4 > 0) {
                bl = true;
            }
            this.j0 = month;
            if (n5 != 0 && bl) {
                this.n0.r1(n3 - 3);
                this.Z1(n3);
            } else if (n5 != 0) {
                this.n0.r1(n3 + 3);
                this.Z1(n3);
            } else {
                this.Z1(n3);
            }
        }
        this.f2(n3);
    }

    public void c2(l l3) {
        this.k0 = l3;
        if (l3 == com.google.android.material.datepicker.j$l.d) {
            this.m0.getLayoutManager().B1(((w)this.m0.getAdapter()).B(this.j0.e));
            this.q0.setVisibility(0);
            this.r0.setVisibility(8);
            this.o0.setVisibility(8);
            this.p0.setVisibility(8);
            return;
        }
        if (l3 == com.google.android.material.datepicker.j$l.c) {
            this.q0.setVisibility(8);
            this.r0.setVisibility(0);
            this.o0.setVisibility(0);
            this.p0.setVisibility(0);
            this.b2(this.j0);
        }
    }

    public final void d2() {
        o0.x0.h0((View)this.n0, new a(this){
            public final j d;
            {
                this.d = j3;
            }

            @Override
            public void g(View view, p0.s s3) {
                super.g(view, s3);
                s3.B0(false);
            }
        });
    }

    public void e2() {
        l l3 = this.k0;
        l l4 = com.google.android.material.datepicker.j$l.d;
        if (l3 == l4) {
            this.c2(com.google.android.material.datepicker.j$l.c);
            this.n0.announceForAccessibility(this.Q(z1.k.mtrl_picker_toggled_to_day_selection));
            return;
        }
        if (l3 == com.google.android.material.datepicker.j$l.c) {
            this.c2(l4);
            this.m0.announceForAccessibility(this.Q(z1.k.mtrl_picker_toggled_to_year_selection));
        }
    }

    public final void f2(int n3) {
        View view = this.p0;
        int n4 = this.n0.getAdapter().f();
        boolean bl = false;
        boolean bl2 = n3 + 1 < n4;
        view.setEnabled(bl2);
        view = this.o0;
        bl2 = bl;
        if (n3 - 1 >= 0) {
            bl2 = true;
        }
        view.setEnabled(bl2);
    }

    @Override
    public void k0(Bundle bundle) {
        super.k0(bundle);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = this.n();
        }
        this.f0 = bundle2.getInt("THEME_RES_ID_KEY");
        this.g0 = (DateSelector)bundle2.getParcelable("GRID_SELECTOR_KEY");
        this.h0 = (CalendarConstraints)bundle2.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.i0 = (DayViewDecorator)bundle2.getParcelable("DAY_VIEW_DECORATOR_KEY");
        this.j0 = (Month)bundle2.getParcelable("CURRENT_MONTH_KEY");
    }

    @Override
    public View o0(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        int n3;
        int n4;
        bundle = new ContextThemeWrapper(this.p(), this.f0);
        this.l0 = new b((Context)bundle);
        layoutInflater = layoutInflater.cloneInContext((Context)bundle);
        this.t0 = (AccessibilityManager)this.n1().getSystemService("accessibility");
        Object object2 = this.h0.z();
        if (com.google.android.material.datepicker.n.e2((Context)bundle)) {
            n4 = z1.i.mtrl_calendar_vertical;
            n3 = 1;
        } else {
            n4 = z1.i.mtrl_calendar_horizontal;
            n3 = 0;
        }
        layoutInflater = layoutInflater.inflate(n4, (ViewGroup)object, false);
        layoutInflater.setMinimumHeight(com.google.android.material.datepicker.j.W1(this.n1()));
        object = (GridView)layoutInflater.findViewById(z1.g.mtrl_calendar_days_of_week);
        o0.x0.h0((View)object, new a(this){
            public final j d;
            {
                this.d = j3;
            }

            @Override
            public void g(View view, p0.s s3) {
                super.g(view, s3);
                s3.j0(null);
            }
        });
        n4 = this.h0.w();
        i i3 = n4 > 0 ? new i(n4) : new i();
        object.setAdapter((ListAdapter)i3);
        object.setNumColumns(((Month)object2).f);
        object.setEnabled(false);
        this.n0 = (RecyclerView)layoutInflater.findViewById(z1.g.mtrl_calendar_months);
        object = new t(this, this.p(), n3, false, n3){
            public final int I;
            public final j J;
            {
                this.J = j3;
                this.I = n4;
                super(context, n3, bl);
            }

            @Override
            public void Q1(RecyclerView.z z3, int[] nArray) {
                if (this.I == 0) {
                    nArray[0] = this.J.n0.getWidth();
                    nArray[1] = this.J.n0.getWidth();
                    return;
                }
                nArray[0] = this.J.n0.getHeight();
                nArray[1] = this.J.n0.getHeight();
            }
        };
        this.n0.setLayoutManager((RecyclerView.p)object);
        this.n0.setTag(u0);
        object = new q((Context)bundle, this.g0, this.h0, this.i0, new m(this){
            public final j a;
            {
                this.a = j3;
            }

            @Override
            public void a(long l3) {
                if (this.a.h0.u().g(l3)) {
                    this.a.g0.k(l3);
                    Iterator iterator = ((AbstractCollection)this.a.e0).iterator();
                    while (iterator.hasNext()) {
                        ((r)iterator.next()).b(this.a.g0.j());
                    }
                    this.a.n0.getAdapter().k();
                    if (this.a.m0 != null) {
                        this.a.m0.getAdapter().k();
                    }
                }
            }
        });
        this.n0.setAdapter((RecyclerView.h)object);
        n3 = bundle.getResources().getInteger(z1.h.mtrl_calendar_year_selector_span);
        this.m0 = object2 = (RecyclerView)layoutInflater.findViewById(z1.g.mtrl_calendar_year_selector_frame);
        if (object2 != null) {
            ((RecyclerView)object2).setHasFixedSize(true);
            this.m0.setLayoutManager(new GridLayoutManager((Context)bundle, n3, 1, false));
            this.m0.setAdapter(new w(this));
            this.m0.j(this.Q1());
        }
        if (layoutInflater.findViewById(z1.g.month_navigation_fragment_toggle) != null) {
            this.P1((View)layoutInflater, (q)object);
        }
        if (!com.google.android.material.datepicker.n.e2((Context)bundle)) {
            new androidx.recyclerview.widget.j().b(this.n0);
        }
        this.n0.r1(((q)object).C(this.j0));
        this.d2();
        return layoutInflater;
    }

    public static final class l
    extends Enum {
        public static final /* enum */ l c = new l("DAY", 0);
        public static final /* enum */ l d = new l("YEAR", 1);
        public static final l[] e = com.google.android.material.datepicker.j$l.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public l() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ l[] a() {
            return new l[]{c, d};
        }

        public static l valueOf(String string) {
            return Enum.valueOf(l.class, string);
        }

        public static l[] values() {
            return (l[])e.clone();
        }
    }

    public static interface m {
        public void a(long var1);
    }
}

