/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.a;
import androidx.fragment.app.y;
import androidx.lifecycle.f;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class BackStackRecordState
implements Parcelable {
    public static final Parcelable.Creator<BackStackRecordState> CREATOR = new Parcelable.Creator(){

        public BackStackRecordState a(Parcel parcel) {
            return new BackStackRecordState(parcel);
        }

        public BackStackRecordState[] b(int n3) {
            return new BackStackRecordState[n3];
        }
    };
    public final int[] c;
    public final ArrayList d;
    public final int[] e;
    public final int[] f;
    public final int g;
    public final String h;
    public final int i;
    public final int j;
    public final CharSequence k;
    public final int l;
    public final CharSequence m;
    public final ArrayList n;
    public final ArrayList o;
    public final boolean p;

    public BackStackRecordState(Parcel parcel) {
        this.c = parcel.createIntArray();
        this.d = parcel.createStringArrayList();
        this.e = parcel.createIntArray();
        this.f = parcel.createIntArray();
        this.g = parcel.readInt();
        this.h = parcel.readString();
        this.i = parcel.readInt();
        this.j = parcel.readInt();
        Parcelable.Creator creator = TextUtils.CHAR_SEQUENCE_CREATOR;
        this.k = (CharSequence)creator.createFromParcel(parcel);
        this.l = parcel.readInt();
        this.m = (CharSequence)creator.createFromParcel(parcel);
        this.n = parcel.createStringArrayList();
        this.o = parcel.createStringArrayList();
        boolean bl = parcel.readInt() != 0;
        this.p = bl;
    }

    public BackStackRecordState(a a4) {
        int n3 = a4.c.size();
        this.c = new int[n3 * 6];
        if (a4.i) {
            this.d = new ArrayList(n3);
            this.e = new int[n3];
            this.f = new int[n3];
            int n4 = 0;
            int n5 = 0;
            while (true) {
                int n6 = n5;
                if (n4 >= n3) break;
                y.a a5 = (y.a)a4.c.get(n4);
                this.c[n6] = a5.a;
                ArrayList arrayList = this.d;
                Object object = a5.b;
                object = object != null ? ((Fragment)object).h : null;
                arrayList.add(object);
                object = this.c;
                object[n6 + 1] = a5.c;
                object[n6 + 2] = a5.d;
                object[n6 + 3] = a5.e;
                object[n6 + 4] = a5.f;
                n5 = n6 + 6;
                object[n6 + 5] = a5.g;
                this.e[n4] = a5.h.ordinal();
                this.f[n4] = a5.i.ordinal();
                ++n4;
            }
            this.g = a4.h;
            this.h = a4.k;
            this.i = a4.v;
            this.j = a4.l;
            this.k = a4.m;
            this.l = a4.n;
            this.m = a4.o;
            this.n = a4.p;
            this.o = a4.q;
            this.p = a4.r;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public int describeContents() {
        return 0;
    }

    public final void o(a a4) {
        int n3 = 0;
        int n4 = 0;
        while (true) {
            int n5;
            int n6;
            int n7;
            int n8 = this.c.length;
            boolean bl = true;
            if (n3 >= n8) break;
            y.a a5 = new y.a();
            int[] nArray = this.c;
            n8 = n3 + 1;
            a5.a = nArray[n3];
            if (FragmentManager.I0(2)) {
                Objects.toString(a4);
                n7 = this.c[n8];
            }
            a5.h = f.b.values()[this.e[n4]];
            a5.i = f.b.values()[this.f[n4]];
            nArray = this.c;
            if (nArray[n8] == 0) {
                bl = false;
            }
            a5.c = bl;
            a5.d = n7 = nArray[n3 + 2];
            a5.e = n6 = nArray[n3 + 3];
            a5.f = n5 = nArray[n3 + 4];
            n8 = n3 + 6;
            a5.g = n3 = nArray[n3 + 5];
            a4.d = n7;
            a4.e = n6;
            a4.f = n5;
            a4.g = n3;
            a4.e(a5);
            ++n4;
            n3 = n8;
        }
        a4.h = this.g;
        a4.k = this.h;
        a4.i = true;
        a4.l = this.j;
        a4.m = this.k;
        a4.n = this.l;
        a4.o = this.m;
        a4.p = this.n;
        a4.q = this.o;
        a4.r = this.p;
    }

    public a p(FragmentManager fragmentManager) {
        a a4 = new a(fragmentManager);
        this.o(a4);
        a4.v = this.i;
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            String string = (String)this.d.get(i3);
            if (string == null) continue;
            ((y.a)a4.c.get((int)i3)).b = fragmentManager.f0(string);
        }
        a4.r(1);
        return a4;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeIntArray(this.c);
        parcel.writeStringList((List)this.d);
        parcel.writeIntArray(this.e);
        parcel.writeIntArray(this.f);
        parcel.writeInt(this.g);
        parcel.writeString(this.h);
        parcel.writeInt(this.i);
        parcel.writeInt(this.j);
        TextUtils.writeToParcel((CharSequence)this.k, (Parcel)parcel, (int)0);
        parcel.writeInt(this.l);
        TextUtils.writeToParcel((CharSequence)this.m, (Parcel)parcel, (int)0);
        parcel.writeStringList((List)this.n);
        parcel.writeStringList((List)this.o);
        parcel.writeInt(this.p ? 1 : 0);
    }
}

