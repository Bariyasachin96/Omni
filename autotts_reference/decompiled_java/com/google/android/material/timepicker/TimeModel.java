/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.timepicker;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.material.timepicker.b;
import java.util.Arrays;

class TimeModel
implements Parcelable {
    public static final Parcelable.Creator<TimeModel> CREATOR = new Parcelable.Creator(){

        public TimeModel a(Parcel parcel) {
            return new TimeModel(parcel);
        }

        public TimeModel[] b(int n3) {
            return new TimeModel[n3];
        }
    };
    public final b c;
    public final b d;
    public final int e;
    public int f;
    public int g;
    public int h;
    public int i;

    public TimeModel(int n3, int n4, int n5, int n6) {
        this.f = n3;
        this.g = n4;
        this.h = n5;
        this.e = n6;
        this.i = TimeModel.q(n3);
        this.c = new b(59);
        n3 = n6 == 1 ? 23 : 12;
        this.d = new b(n3);
    }

    public TimeModel(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public static String o(Resources resources, CharSequence charSequence) {
        return TimeModel.p(resources, charSequence, "%02d");
    }

    public static String p(Resources object, CharSequence charSequence, String string) {
        try {
            object = String.format(object.getConfiguration().locale, string, Integer.parseInt(String.valueOf(charSequence)));
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    public static int q(int n3) {
        if (n3 >= 12) {
            return 1;
        }
        return 0;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TimeModel)) {
            return false;
        }
        object = (TimeModel)object;
        return this.f == ((TimeModel)object).f && this.g == ((TimeModel)object).g && this.e == ((TimeModel)object).e && this.h == ((TimeModel)object).h;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.e, this.f, this.g, this.h});
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
        parcel.writeInt(this.e);
    }
}

