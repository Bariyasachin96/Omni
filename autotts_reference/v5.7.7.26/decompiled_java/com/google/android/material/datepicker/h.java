/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.material.datepicker;

import android.content.Context;
import com.google.android.material.datepicker.v;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import n0.d;
import z1.k;

public abstract class h {
    public static d a(Long l3, Long l4) {
        return h.b(l3, l4, null);
    }

    public static d b(Long comparable, Long comparable2, SimpleDateFormat simpleDateFormat) {
        if (comparable == null && comparable2 == null) {
            return d.a(null, null);
        }
        if (comparable == null) {
            return d.a(null, h.d((Long)comparable2, simpleDateFormat));
        }
        if (comparable2 == null) {
            return d.a(h.d((Long)comparable, simpleDateFormat), null);
        }
        Calendar calendar = v.k();
        Calendar calendar2 = v.m();
        calendar2.setTimeInMillis((Long)comparable);
        Calendar calendar3 = v.m();
        calendar3.setTimeInMillis((Long)comparable2);
        if (simpleDateFormat != null) {
            comparable = new Date((Long)comparable);
            comparable2 = new Date((Long)comparable2);
            return d.a(simpleDateFormat.format((Date)comparable), simpleDateFormat.format((Date)comparable2));
        }
        if (calendar2.get(1) == calendar3.get(1)) {
            if (calendar2.get(1) == calendar.get(1)) {
                return d.a(h.g((Long)comparable, Locale.getDefault()), h.g((Long)comparable2, Locale.getDefault()));
            }
            return d.a(h.g((Long)comparable, Locale.getDefault()), h.n((Long)comparable2, Locale.getDefault()));
        }
        return d.a(h.n((Long)comparable, Locale.getDefault()), h.n((Long)comparable2, Locale.getDefault()));
    }

    public static String c(long l3) {
        return h.d(l3, null);
    }

    public static String d(long l3, SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat != null) {
            return simpleDateFormat.format(new Date(l3));
        }
        if (h.q(l3)) {
            return h.f(l3);
        }
        return h.m(l3);
    }

    public static String e(Context context, long l3, boolean bl, boolean bl2, boolean bl3) {
        String string;
        String string2 = string = h.j(l3);
        if (bl) {
            string2 = String.format(context.getString(k.mtrl_picker_today_description), string);
        }
        if (bl2) {
            return String.format(context.getString(k.mtrl_picker_start_date_description), string2);
        }
        if (bl3) {
            return String.format(context.getString(k.mtrl_picker_end_date_description), string2);
        }
        return string2;
    }

    public static String f(long l3) {
        return h.g(l3, Locale.getDefault());
    }

    public static String g(long l3, Locale locale) {
        return v.b(locale).format(new Date(l3));
    }

    public static String h(long l3) {
        return h.i(l3, Locale.getDefault());
    }

    public static String i(long l3, Locale locale) {
        return v.h(locale).format(new Date(l3));
    }

    public static String j(long l3) {
        if (h.q(l3)) {
            return h.h(l3);
        }
        return h.o(l3);
    }

    public static String k(Context context, int n3) {
        if (v.k().get(1) == n3) {
            return String.format(context.getString(k.mtrl_picker_navigate_to_current_year_description), n3);
        }
        return String.format(context.getString(k.mtrl_picker_navigate_to_year_description), n3);
    }

    public static String l(long l3) {
        return v.p(Locale.getDefault()).format(new Date(l3));
    }

    public static String m(long l3) {
        return h.n(l3, Locale.getDefault());
    }

    public static String n(long l3, Locale locale) {
        return v.o(locale).format(new Date(l3));
    }

    public static String o(long l3) {
        return h.p(l3, Locale.getDefault());
    }

    public static String p(long l3, Locale locale) {
        return v.q(locale).format(new Date(l3));
    }

    public static boolean q(long l3) {
        Calendar calendar = v.k();
        Calendar calendar2 = v.m();
        calendar2.setTimeInMillis(l3);
        return calendar.get(1) == calendar2.get(1);
    }
}

