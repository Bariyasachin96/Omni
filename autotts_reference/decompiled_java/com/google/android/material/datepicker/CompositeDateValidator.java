/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.datepicker.CalendarConstraints;
import java.util.ArrayList;
import java.util.List;
import n0.h;

public final class CompositeDateValidator
implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<CompositeDateValidator> CREATOR;
    public static final d e;
    public static final d f;
    public final d c;
    public final List d;

    static {
        e = new d(){

            @Override
            public boolean a(List object, long l3) {
                object = object.iterator();
                while (object.hasNext()) {
                    CalendarConstraints.DateValidator dateValidator = (CalendarConstraints.DateValidator)object.next();
                    if (dateValidator == null || !dateValidator.g(l3)) continue;
                    return true;
                }
                return false;
            }

            @Override
            public int getId() {
                return 1;
            }
        };
        f = new d(){

            @Override
            public boolean a(List object, long l3) {
                object = object.iterator();
                while (object.hasNext()) {
                    CalendarConstraints.DateValidator dateValidator = (CalendarConstraints.DateValidator)object.next();
                    if (dateValidator == null || dateValidator.g(l3)) continue;
                    return false;
                }
                return true;
            }

            @Override
            public int getId() {
                return 2;
            }
        };
        CREATOR = new Parcelable.Creator(){

            public CompositeDateValidator a(Parcel object) {
                ArrayList arrayList = object.readArrayList(CalendarConstraints.DateValidator.class.getClassLoader());
                int n3 = object.readInt();
                object = n3 == 2 ? f : (n3 == 1 ? e : f);
                return new CompositeDateValidator((List)h.g(arrayList), (d)object, null);
            }

            public CompositeDateValidator[] b(int n3) {
                return new CompositeDateValidator[n3];
            }
        };
    }

    public CompositeDateValidator(List list, d d3) {
        this.d = list;
        this.c = d3;
    }

    public /* synthetic */ CompositeDateValidator(List list, d d3, a a4) {
        this(list, d3);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CompositeDateValidator)) {
            return false;
        }
        object = (CompositeDateValidator)object;
        return this.d.equals(((CompositeDateValidator)object).d) && this.c.getId() == ((CompositeDateValidator)object).c.getId();
    }

    @Override
    public boolean g(long l3) {
        return this.c.a(this.d, l3);
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeList(this.d);
        parcel.writeInt(this.c.getId());
    }

    public static interface d {
        public boolean a(List var1, long var2);

        public int getId();
    }
}

