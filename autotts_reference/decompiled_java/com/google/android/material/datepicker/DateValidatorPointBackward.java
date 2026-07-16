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
import java.util.Arrays;

public class DateValidatorPointBackward
implements CalendarConstraints.DateValidator {
    public static final Parcelable.Creator<DateValidatorPointBackward> CREATOR = new Parcelable.Creator(){

        public DateValidatorPointBackward a(Parcel parcel) {
            return new DateValidatorPointBackward(parcel.readLong(), null);
        }

        public DateValidatorPointBackward[] b(int n3) {
            return new DateValidatorPointBackward[n3];
        }
    };
    public final long c;

    public DateValidatorPointBackward(long l3) {
        this.c = l3;
    }

    public /* synthetic */ DateValidatorPointBackward(long l3, a a4) {
        this(l3);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DateValidatorPointBackward)) {
            return false;
        }
        object = (DateValidatorPointBackward)object;
        return this.c == ((DateValidatorPointBackward)object).c;
    }

    @Override
    public boolean g(long l3) {
        return l3 <= this.c;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.c});
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeLong(this.c);
    }
}

