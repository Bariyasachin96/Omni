/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.datepicker.h;
import com.google.android.material.datepicker.v;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

final class Month
implements Comparable<Month>,
Parcelable {
    public static final Parcelable.Creator<Month> CREATOR = new Parcelable.Creator(){

        public Month a(Parcel parcel) {
            return Month.p(parcel.readInt(), parcel.readInt());
        }

        public Month[] b(int n3) {
            return new Month[n3];
        }
    };
    public final Calendar c;
    public final int d;
    public final int e;
    public final int f;
    public final int g;
    public final long h;
    public String i;

    public Month(Calendar calendar) {
        calendar.set(5, 1);
        this.c = calendar = v.e(calendar);
        this.d = calendar.get(2);
        this.e = calendar.get(1);
        this.f = calendar.getMaximum(7);
        this.g = calendar.getActualMaximum(5);
        this.h = calendar.getTimeInMillis();
    }

    public static Month p(int n3, int n4) {
        Calendar calendar = v.m();
        calendar.set(1, n3);
        calendar.set(2, n4);
        return new Month(calendar);
    }

    public static Month q(long l3) {
        Calendar calendar = v.m();
        calendar.setTimeInMillis(l3);
        return new Month(calendar);
    }

    public static Month r() {
        return new Month(v.k());
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Month)) {
            return false;
        }
        object = (Month)object;
        return this.d == ((Month)object).d && this.e == ((Month)object).e;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.d, this.e});
    }

    public int o(Month month) {
        return this.c.compareTo(month.c);
    }

    public int s(int n3) {
        int n4 = this.c.get(7);
        if (n3 <= 0) {
            n3 = this.c.getFirstDayOfWeek();
        }
        n4 -= n3;
        n3 = n4;
        if (n4 < 0) {
            n3 = n4 + this.f;
        }
        return n3;
    }

    public long t(int n3) {
        Calendar calendar = v.e(this.c);
        calendar.set(5, n3);
        return calendar.getTimeInMillis();
    }

    public int u(long l3) {
        Calendar calendar = v.e(this.c);
        calendar.setTimeInMillis(l3);
        return calendar.get(5);
    }

    public String v() {
        if (this.i == null) {
            this.i = com.google.android.material.datepicker.h.l(this.c.getTimeInMillis());
        }
        return this.i;
    }

    public long w() {
        return this.c.getTimeInMillis();
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeInt(this.e);
        parcel.writeInt(this.d);
    }

    public Month x(int n3) {
        Calendar calendar = v.e(this.c);
        calendar.add(2, n3);
        return new Month(calendar);
    }

    public int y(Month month) {
        if (this.c instanceof GregorianCalendar) {
            return (month.e - this.e) * 12 + (month.d - this.d);
        }
        throw new IllegalArgumentException("Only Gregorian calendars are supported.");
    }
}

