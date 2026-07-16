/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.text.TextUtils
 *  android.util.SparseIntArray
 */
package o1;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import o.a;

public class b
extends o1.a {
    public final SparseIntArray d = new SparseIntArray();
    public final Parcel e;
    public final int f;
    public final int g;
    public final String h;
    public int i = -1;
    public int j;
    public int k = -1;

    public b(Parcel parcel) {
        this(parcel, parcel.dataPosition(), parcel.dataSize(), "", new a(), new a(), new a());
    }

    public b(Parcel parcel, int n3, int n4, String string, a a4, a a5, a a6) {
        super(a4, a5, a6);
        this.e = parcel;
        this.f = n3;
        this.g = n4;
        this.j = n3;
        this.h = string;
    }

    @Override
    public void A(byte[] byArray) {
        if (byArray != null) {
            this.e.writeInt(byArray.length);
            this.e.writeByteArray(byArray);
            return;
        }
        this.e.writeInt(-1);
    }

    @Override
    public void C(CharSequence charSequence) {
        TextUtils.writeToParcel((CharSequence)charSequence, (Parcel)this.e, (int)0);
    }

    @Override
    public void E(int n3) {
        this.e.writeInt(n3);
    }

    @Override
    public void G(Parcelable parcelable) {
        this.e.writeParcelable(parcelable, 0);
    }

    @Override
    public void I(String string) {
        this.e.writeString(string);
    }

    @Override
    public void a() {
        int n3 = this.i;
        if (n3 >= 0) {
            int n4 = this.d.get(n3);
            n3 = this.e.dataPosition();
            this.e.setDataPosition(n4);
            this.e.writeInt(n3 - n4);
            this.e.setDataPosition(n3);
        }
    }

    @Override
    public o1.a b() {
        int n3;
        Parcel parcel = this.e;
        int n4 = parcel.dataPosition();
        int n5 = n3 = this.j;
        if (n3 == this.f) {
            n5 = this.g;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.h);
        stringBuilder.append("  ");
        return new b(parcel, n4, n5, stringBuilder.toString(), this.a, this.b, this.c);
    }

    @Override
    public boolean g() {
        return this.e.readInt() != 0;
    }

    @Override
    public byte[] i() {
        int n3 = this.e.readInt();
        if (n3 < 0) {
            return null;
        }
        byte[] byArray = new byte[n3];
        this.e.readByteArray(byArray);
        return byArray;
    }

    @Override
    public CharSequence k() {
        return (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this.e);
    }

    @Override
    public boolean m(int n3) {
        while (this.j < this.g) {
            int n4 = this.k;
            if (n4 == n3) {
                return true;
            }
            if (String.valueOf(n4).compareTo(String.valueOf(n3)) > 0) {
                return false;
            }
            this.e.setDataPosition(this.j);
            n4 = this.e.readInt();
            this.k = this.e.readInt();
            this.j += n4;
        }
        return this.k == n3;
    }

    @Override
    public int o() {
        return this.e.readInt();
    }

    @Override
    public Parcelable q() {
        return this.e.readParcelable(this.getClass().getClassLoader());
    }

    @Override
    public String s() {
        return this.e.readString();
    }

    @Override
    public void w(int n3) {
        this.a();
        this.i = n3;
        this.d.put(n3, this.e.dataPosition());
        this.E(0);
        this.E(n3);
    }

    @Override
    public void y(boolean bl) {
        this.e.writeInt(bl ? 1 : 0);
    }
}

