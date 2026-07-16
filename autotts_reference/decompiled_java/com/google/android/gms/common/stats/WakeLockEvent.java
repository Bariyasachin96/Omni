/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.stats.StatsEvent;
import java.util.List;
import v1.b;
import w1.a;

@Deprecated
public final class WakeLockEvent
extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new a();
    public final int c;
    public final long d;
    public final int e;
    public final String f;
    public final String g;
    public final String h;
    public final int i;
    public final List j;
    public final String k;
    public final long l;
    public final int m;
    public final String n;
    public final float o;
    public final long p;
    public final boolean q;

    public WakeLockEvent(int n3, long l3, int n4, String string, int n5, List list, String string2, long l4, int n6, String string3, String string4, float f3, long l5, String string5, boolean bl) {
        this.c = n3;
        this.d = l3;
        this.e = n4;
        this.f = string;
        this.g = string3;
        this.h = string5;
        this.i = n5;
        this.j = list;
        this.k = string2;
        this.l = l4;
        this.m = n6;
        this.n = string4;
        this.o = f3;
        this.p = l5;
        this.q = bl;
    }

    @Override
    public final long o() {
        return this.d;
    }

    @Override
    public final int p() {
        return this.e;
    }

    @Override
    public final String q() {
        Object object = this.j;
        String string = "";
        object = object == null ? "" : TextUtils.join((CharSequence)",", (Iterable)object);
        int n3 = this.m;
        String string2 = this.g;
        String string3 = this.n;
        float f3 = this.o;
        CharSequence charSequence = this.h;
        int n4 = this.i;
        String string4 = this.f;
        boolean bl = this.q;
        int n5 = String.valueOf(string4).length();
        int n6 = String.valueOf(n4).length();
        int n7 = String.valueOf(object).length();
        int n8 = String.valueOf(n3).length();
        String string5 = string2;
        if (string2 == null) {
            string5 = "";
        }
        int n9 = string5.length();
        string2 = string3;
        if (string3 == null) {
            string2 = "";
        }
        int n10 = string2.length();
        int n11 = String.valueOf(f3).length();
        if (charSequence != null) {
            string = charSequence;
        }
        charSequence = new StringBuilder(n5 + 2 + n6 + 1 + n7 + 1 + n8 + 1 + n9 + 1 + n10 + 1 + n11 + 1 + string.length() + 1 + String.valueOf(bl).length());
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(string4);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(n4);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(n3);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(string5);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(f3);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append("\t");
        ((StringBuilder)charSequence).append(bl);
        return ((StringBuilder)charSequence).toString();
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        n3 = b.a(parcel);
        b.g(parcel, 1, this.c);
        b.j(parcel, 2, this.d);
        b.l(parcel, 4, this.f, false);
        b.g(parcel, 5, this.i);
        b.m(parcel, 6, this.j, false);
        b.j(parcel, 8, this.l);
        b.l(parcel, 10, this.g, false);
        b.g(parcel, 11, this.e);
        b.l(parcel, 12, this.k, false);
        b.l(parcel, 13, this.n, false);
        b.g(parcel, 14, this.m);
        b.e(parcel, 15, this.o);
        b.j(parcel, 16, this.p);
        b.l(parcel, 17, this.h, false);
        b.c(parcel, 18, this.q);
        b.b(parcel, n3);
    }
}

