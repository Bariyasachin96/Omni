/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import s1.e;
import v1.b;

public final class Scope
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<Scope> CREATOR = new e();
    public final int c;
    public final String d;

    public Scope(int n3, String string) {
        u1.b.b(string, "scopeUri must not be null or empty");
        this.c = n3;
        this.d = string;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Scope)) {
            return false;
        }
        return this.d.equals(((Scope)object).d);
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    public String o() {
        return this.d;
    }

    public String toString() {
        return this.d;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        n3 = this.c;
        int n4 = b.a(parcel);
        b.g(parcel, 1, n3);
        b.l(parcel, 2, this.o(), false);
        b.b(parcel, n4);
    }
}

