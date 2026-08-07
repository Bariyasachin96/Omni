/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.icu.text.DateFormat
 *  android.icu.text.DisplayContext
 *  android.icu.util.TimeZone
 */
package com.google.android.material.datepicker;

import android.content.res.Resources;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import com.google.android.material.datepicker.u;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import z1.k;

public abstract class v {
    public static AtomicReference a = new AtomicReference();

    public static long a(long l3) {
        Calendar calendar = v.m();
        calendar.setTimeInMillis(l3);
        return v.e(calendar).getTimeInMillis();
    }

    public static DateFormat b(Locale locale) {
        return v.c("MMMd", locale);
    }

    public static DateFormat c(String string, Locale locale) {
        string = DateFormat.getInstanceForSkeleton((String)string, (Locale)locale);
        string.setTimeZone(v.l());
        string.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        return string;
    }

    public static String d(String string) {
        return string.replaceAll("[^dMy/\\-.]", "").replaceAll("d{1,2}", "dd").replaceAll("M{1,2}", "MM").replaceAll("y{1,4}", "yyyy").replaceAll("\\.$", "").replaceAll("My", "M/y");
    }

    public static Calendar e(Calendar calendar) {
        Calendar calendar2 = v.n(calendar);
        calendar = v.m();
        calendar.set(calendar2.get(1), calendar2.get(2), calendar2.get(5));
        return calendar;
    }

    public static SimpleDateFormat f() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(v.d(((SimpleDateFormat)java.text.DateFormat.getDateInstance(3, Locale.getDefault())).toPattern()), Locale.getDefault());
        simpleDateFormat.setTimeZone(v.j());
        simpleDateFormat.setLenient(false);
        return simpleDateFormat;
    }

    public static String g(Resources object, SimpleDateFormat object2) {
        object2 = ((SimpleDateFormat)object2).toPattern();
        String string = object.getString(k.mtrl_picker_text_input_year_abbr);
        String string2 = object.getString(k.mtrl_picker_text_input_month_abbr);
        String string3 = object.getString(k.mtrl_picker_text_input_day_abbr);
        object = object2;
        if (Locale.getDefault().getLanguage().equals(Locale.KOREAN.getLanguage())) {
            object = ((String)object2).replaceAll("d+", "d").replaceAll("M+", "M").replaceAll("y+", "y");
        }
        return ((String)object).replace("d", string3).replace("M", string2).replace("y", string);
    }

    public static DateFormat h(Locale locale) {
        return v.c("MMMMEEEEd", locale);
    }

    public static u i() {
        u u3;
        u u4 = u3 = (u)a.get();
        if (u3 == null) {
            u4 = u.c();
        }
        return u4;
    }

    public static TimeZone j() {
        return TimeZone.getTimeZone("UTC");
    }

    public static Calendar k() {
        Calendar calendar = v.i().a();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.setTimeZone(v.j());
        return calendar;
    }

    public static android.icu.util.TimeZone l() {
        return android.icu.util.TimeZone.getTimeZone((String)"UTC");
    }

    public static Calendar m() {
        return v.n(null);
    }

    public static Calendar n(Calendar calendar) {
        Calendar calendar2 = Calendar.getInstance(v.j());
        if (calendar == null) {
            calendar2.clear();
            return calendar2;
        }
        calendar2.setTimeInMillis(calendar.getTimeInMillis());
        return calendar2;
    }

    public static DateFormat o(Locale locale) {
        return v.c("yMMMd", locale);
    }

    public static DateFormat p(Locale locale) {
        return v.c("yMMMM", locale);
    }

    public static DateFormat q(Locale locale) {
        return v.c("yMMMMEEEEd", locale);
    }
}

