/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package androidx.fragment.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.k;
import androidx.lifecycle.f;

final class FragmentState
implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator(){

        public FragmentState a(Parcel parcel) {
            return new FragmentState(parcel);
        }

        public FragmentState[] b(int n3) {
            return new FragmentState[n3];
        }
    };
    public final String c;
    public final String d;
    public final boolean e;
    public final int f;
    public final int g;
    public final String h;
    public final boolean i;
    public final boolean j;
    public final boolean k;
    public final Bundle l;
    public final boolean m;
    public final int n;
    public Bundle o;

    public FragmentState(Parcel parcel) {
        this.c = parcel.readString();
        this.d = parcel.readString();
        int n3 = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n3 != 0;
        this.e = bl2;
        this.f = parcel.readInt();
        this.g = parcel.readInt();
        this.h = parcel.readString();
        bl2 = parcel.readInt() != 0;
        this.i = bl2;
        bl2 = parcel.readInt() != 0;
        this.j = bl2;
        bl2 = parcel.readInt() != 0;
        this.k = bl2;
        this.l = parcel.readBundle();
        bl2 = bl;
        if (parcel.readInt() != 0) {
            bl2 = true;
        }
        this.m = bl2;
        this.o = parcel.readBundle();
        this.n = parcel.readInt();
    }

    public FragmentState(Fragment fragment) {
        this.c = fragment.getClass().getName();
        this.d = fragment.h;
        this.e = fragment.q;
        this.f = fragment.z;
        this.g = fragment.A;
        this.h = fragment.B;
        this.i = fragment.E;
        this.j = fragment.o;
        this.k = fragment.D;
        this.l = fragment.i;
        this.m = fragment.C;
        this.n = fragment.T.ordinal();
    }

    public int describeContents() {
        return 0;
    }

    public Fragment o(k object, ClassLoader classLoader) {
        object = ((k)object).a(classLoader, this.c);
        Bundle bundle = this.l;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
        ((Fragment)object).t1(this.l);
        ((Fragment)object).h = this.d;
        ((Fragment)object).q = this.e;
        ((Fragment)object).s = true;
        ((Fragment)object).z = this.f;
        ((Fragment)object).A = this.g;
        ((Fragment)object).B = this.h;
        ((Fragment)object).E = this.i;
        ((Fragment)object).o = this.j;
        ((Fragment)object).D = this.k;
        ((Fragment)object).C = this.m;
        ((Fragment)object).T = f.b.values()[this.n];
        classLoader = this.o;
        if (classLoader != null) {
            ((Fragment)object).d = classLoader;
            return object;
        }
        ((Fragment)object).d = new Bundle();
        return object;
    }

    public String toString() {
        String string;
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentState{");
        stringBuilder.append(this.c);
        stringBuilder.append(" (");
        stringBuilder.append(this.d);
        stringBuilder.append(")}:");
        if (this.e) {
            stringBuilder.append(" fromLayout");
        }
        if (this.g != 0) {
            stringBuilder.append(" id=0x");
            stringBuilder.append(Integer.toHexString(this.g));
        }
        if ((string = this.h) != null && !string.isEmpty()) {
            stringBuilder.append(" tag=");
            stringBuilder.append(this.h);
        }
        if (this.i) {
            stringBuilder.append(" retainInstance");
        }
        if (this.j) {
            stringBuilder.append(" removing");
        }
        if (this.k) {
            stringBuilder.append(" detached");
        }
        if (this.m) {
            stringBuilder.append(" hidden");
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e ? 1 : 0);
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
        parcel.writeString(this.h);
        parcel.writeInt(this.i ? 1 : 0);
        parcel.writeInt(this.j ? 1 : 0);
        parcel.writeInt(this.k ? 1 : 0);
        parcel.writeBundle(this.l);
        parcel.writeInt(this.m ? 1 : 0);
        parcel.writeBundle(this.o);
        parcel.writeInt(this.n);
    }
}

