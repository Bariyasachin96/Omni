/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.ContextThemeWrapper
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.r;
import com.google.android.material.datepicker.s;
import java.util.AbstractCollection;
import java.util.Iterator;

public final class o<S>
extends s {
    public int f0;
    public DateSelector g0;
    public CalendarConstraints h0;

    public static o G1(DateSelector dateSelector, int n3, CalendarConstraints calendarConstraints) {
        o o3 = new o();
        Bundle bundle = new Bundle();
        bundle.putInt("THEME_RES_ID_KEY", n3);
        bundle.putParcelable("DATE_SELECTOR_KEY", (Parcelable)dateSelector);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", (Parcelable)calendarConstraints);
        o3.t1(bundle);
        return o3;
    }

    @Override
    public void G0(Bundle bundle) {
        super.G0(bundle);
        bundle.putInt("THEME_RES_ID_KEY", this.f0);
        bundle.putParcelable("DATE_SELECTOR_KEY", (Parcelable)this.g0);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", (Parcelable)this.h0);
    }

    @Override
    public void k0(Bundle bundle) {
        super.k0(bundle);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = this.n();
        }
        this.f0 = bundle2.getInt("THEME_RES_ID_KEY");
        this.g0 = (DateSelector)bundle2.getParcelable("DATE_SELECTOR_KEY");
        this.h0 = (CalendarConstraints)bundle2.getParcelable("CALENDAR_CONSTRAINTS_KEY");
    }

    @Override
    public View o0(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.cloneInContext((Context)new ContextThemeWrapper(this.p(), this.f0));
        return this.g0.n(layoutInflater, viewGroup, bundle, this.h0, new r(this){
            public final o a;
            {
                this.a = o3;
            }

            @Override
            public void a() {
                Iterator iterator = ((AbstractCollection)this.a.e0).iterator();
                while (iterator.hasNext()) {
                    ((r)iterator.next()).a();
                }
            }

            @Override
            public void b(Object object) {
                Iterator iterator = ((AbstractCollection)this.a.e0).iterator();
                while (iterator.hasNext()) {
                    ((r)iterator.next()).b(object);
                }
            }
        });
    }
}

