/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.Month;
import com.google.android.material.datepicker.v;
import java.util.Arrays;
import java.util.Objects;
import n0.c;

public final class CalendarConstraints
implements Parcelable {
    public static final Parcelable.Creator<CalendarConstraints> CREATOR = new Parcelable.Creator(){

        public CalendarConstraints a(Parcel parcel) {
            Month month = (Month)parcel.readParcelable(Month.class.getClassLoader());
            Month month2 = (Month)parcel.readParcelable(Month.class.getClassLoader());
            Month month3 = (Month)parcel.readParcelable(Month.class.getClassLoader());
            return new CalendarConstraints(month, month2, (DateValidator)parcel.readParcelable(DateValidator.class.getClassLoader()), month3, parcel.readInt(), null);
        }

        public CalendarConstraints[] b(int n3) {
            return new CalendarConstraints[n3];
        }
    };
    public final Month c;
    public final Month d;
    public final DateValidator e;
    public Month f;
    public final int g;
    public final int h;
    public final int i;

    public CalendarConstraints(Month month, Month month2, DateValidator dateValidator, Month month3, int n3) {
        Objects.requireNonNull(month, "start cannot be null");
        Objects.requireNonNull(month2, "end cannot be null");
        Objects.requireNonNull(dateValidator, "validator cannot be null");
        this.c = month;
        this.d = month2;
        this.f = month3;
        this.g = n3;
        this.e = dateValidator;
        if (month3 != null && month.o(month3) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        }
        if (month3 != null && month3.o(month2) > 0) {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        }
        if (n3 >= 0 && n3 <= v.m().getMaximum(7)) {
            this.i = month.y(month2) + 1;
            this.h = month2.e - month.e + 1;
            return;
        }
        throw new IllegalArgumentException("firstDayOfWeek is not valid");
    }

    public /* synthetic */ CalendarConstraints(Month month, Month month2, DateValidator dateValidator, Month month3, int n3, a a4) {
        this(month, month2, dateValidator, month3, n3);
    }

    public int A() {
        return this.h;
    }

    public boolean B(long l3) {
        Month month;
        return this.c.t(1) <= l3 && l3 <= (month = this.d).t(month.g);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CalendarConstraints)) {
            return false;
        }
        object = (CalendarConstraints)object;
        return this.c.equals(((CalendarConstraints)object).c) && this.d.equals(((CalendarConstraints)object).d) && n0.c.a(this.f, ((CalendarConstraints)object).f) && this.g == ((CalendarConstraints)object).g && this.e.equals(((CalendarConstraints)object).e);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.c, this.d, this.f, this.g, this.e});
    }

    public Month t(Month month) {
        if (month.o(this.c) < 0) {
            return this.c;
        }
        Month month2 = month;
        if (month.o(this.d) > 0) {
            month2 = this.d;
        }
        return month2;
    }

    public DateValidator u() {
        return this.e;
    }

    public Month v() {
        return this.d;
    }

    public int w() {
        return this.g;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeParcelable((Parcelable)this.c, 0);
        parcel.writeParcelable((Parcelable)this.d, 0);
        parcel.writeParcelable((Parcelable)this.f, 0);
        parcel.writeParcelable((Parcelable)this.e, 0);
        parcel.writeInt(this.g);
    }

    public int x() {
        return this.i;
    }

    public Month y() {
        return this.f;
    }

    public Month z() {
        return this.c;
    }

    public static interface DateValidator
    extends Parcelable {
        public boolean g(long var1);
    }

    public static final class b {
        public static final long f = v.a(Month.p((int)1900, (int)0).h);
        public static final long g = v.a(Month.p((int)2100, (int)11).h);
        public long a = f;
        public long b = g;
        public Long c;
        public int d;
        public DateValidator e = DateValidatorPointForward.o(Long.MIN_VALUE);

        public b(CalendarConstraints calendarConstraints) {
            this.a = ((CalendarConstraints)calendarConstraints).c.h;
            this.b = ((CalendarConstraints)calendarConstraints).d.h;
            this.c = ((CalendarConstraints)calendarConstraints).f.h;
            this.d = calendarConstraints.g;
            this.e = calendarConstraints.e;
        }

        public CalendarConstraints a() {
            Object object = new Bundle();
            object.putParcelable("DEEP_COPY_VALIDATOR_KEY", (Parcelable)this.e);
            Month month = Month.q(this.a);
            Month month2 = Month.q(this.b);
            DateValidator dateValidator = (DateValidator)object.getParcelable("DEEP_COPY_VALIDATOR_KEY");
            object = this.c;
            object = object == null ? null : Month.q((Long)object);
            return new CalendarConstraints(month, month2, dateValidator, (Month)object, this.d, null);
        }

        public b b(long l3) {
            this.c = l3;
            return this;
        }
    }
}

