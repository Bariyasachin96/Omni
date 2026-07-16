/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.view.View
 */
package com.google.android.material.datepicker;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.c;
import com.google.android.material.datepicker.d;
import com.google.android.material.datepicker.h;
import com.google.android.material.datepicker.v;
import com.google.android.material.internal.y;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import z1.k;

public abstract class e
extends y {
    public final TextInputLayout c;
    public final String d;
    public final DateFormat e;
    public final CalendarConstraints f;
    public final String g;
    public final Runnable h;
    public Runnable i;
    public int j = 0;

    public e(String string, DateFormat dateFormat, TextInputLayout textInputLayout, CalendarConstraints calendarConstraints) {
        this.d = string;
        this.e = dateFormat;
        this.c = textInputLayout;
        this.f = calendarConstraints;
        this.g = textInputLayout.getContext().getString(k.mtrl_picker_out_of_range);
        this.h = new c(this, string);
    }

    public static /* synthetic */ void a(e e3, long l3) {
        e3.getClass();
        String string = com.google.android.material.datepicker.h.c(l3);
        e3.c.setError(String.format(e3.g, e3.g(string)));
        e3.d();
    }

    public static /* synthetic */ void b(e e3, String string) {
        TextInputLayout textInputLayout = e3.c;
        Object object = e3.e;
        Object object2 = textInputLayout.getContext();
        String string2 = object2.getString(k.mtrl_picker_invalid_format);
        string = String.format(object2.getString(k.mtrl_picker_invalid_format_use), e3.g(string));
        object = String.format(object2.getString(k.mtrl_picker_invalid_format_example), e3.g(((DateFormat)object).format(new Date(v.k().getTimeInMillis()))));
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append("\n");
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append("\n");
        ((StringBuilder)object2).append((String)object);
        textInputLayout.setError(((StringBuilder)object2).toString());
        e3.d();
    }

    public void afterTextChanged(Editable editable) {
        char c3;
        if (!Locale.getDefault().getLanguage().equals(Locale.KOREAN.getLanguage()) && editable.length() != 0 && editable.length() < this.d.length() && editable.length() >= this.j && !Character.isLetterOrDigit(c3 = this.d.charAt(editable.length()))) {
            editable.append(c3);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int n3, int n4, int n5) {
        this.j = charSequence.length();
    }

    public final Runnable c(long l3) {
        return new d(this, l3);
    }

    public abstract void d();

    public abstract void e(Long var1);

    public void f(View view, Runnable runnable) {
        view.post(runnable);
    }

    public final String g(String string) {
        return string.replace(' ', ' ');
    }

    @Override
    public void onTextChanged(CharSequence object, int n3, int n4, int n5) {
        this.c.removeCallbacks(this.h);
        this.c.removeCallbacks(this.i);
        this.c.setError(null);
        this.e(null);
        if (!TextUtils.isEmpty((CharSequence)object) && object.length() >= this.d.length()) {
            try {
                object = this.e.parse(object.toString());
                this.c.setError(null);
                long l3 = ((Date)object).getTime();
                if (this.f.u().g(l3) && this.f.B(l3)) {
                    this.e(((Date)object).getTime());
                    return;
                }
                this.i = object = this.c(l3);
                this.f((View)this.c, (Runnable)object);
                return;
            }
            catch (ParseException parseException) {
                this.f((View)this.c, this.h);
            }
        }
    }
}

