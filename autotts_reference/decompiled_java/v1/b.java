/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package v1;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public abstract class b {
    public static int a(Parcel parcel) {
        return b.p(parcel, 20293);
    }

    public static void b(Parcel parcel, int n3) {
        b.q(parcel, n3);
    }

    public static void c(Parcel parcel, int n3, boolean bl) {
        b.o(parcel, n3, 4);
        parcel.writeInt(bl ? 1 : 0);
    }

    public static void d(Parcel parcel, int n3, Bundle bundle, boolean bl) {
        if (bundle == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        n3 = b.p(parcel, n3);
        parcel.writeBundle(bundle);
        b.q(parcel, n3);
    }

    public static void e(Parcel parcel, int n3, float f3) {
        b.o(parcel, n3, 4);
        parcel.writeFloat(f3);
    }

    public static void f(Parcel parcel, int n3, IBinder iBinder, boolean bl) {
        if (iBinder == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        n3 = b.p(parcel, n3);
        parcel.writeStrongBinder(iBinder);
        b.q(parcel, n3);
    }

    public static void g(Parcel parcel, int n3, int n4) {
        b.o(parcel, n3, 4);
        parcel.writeInt(n4);
    }

    public static void h(Parcel parcel, int n3, int[] nArray, boolean bl) {
        if (nArray == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        n3 = b.p(parcel, n3);
        parcel.writeIntArray(nArray);
        b.q(parcel, n3);
    }

    public static void i(Parcel parcel, int n3, Integer n4, boolean bl) {
        if (n4 == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        b.o(parcel, n3, 4);
        parcel.writeInt(n4.intValue());
    }

    public static void j(Parcel parcel, int n3, long l3) {
        b.o(parcel, n3, 8);
        parcel.writeLong(l3);
    }

    public static void k(Parcel parcel, int n3, Parcelable parcelable, int n4, boolean bl) {
        if (parcelable == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        n3 = b.p(parcel, n3);
        parcelable.writeToParcel(parcel, n4);
        b.q(parcel, n3);
    }

    public static void l(Parcel parcel, int n3, String string, boolean bl) {
        if (string == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        n3 = b.p(parcel, n3);
        parcel.writeString(string);
        b.q(parcel, n3);
    }

    public static void m(Parcel parcel, int n3, List list, boolean bl) {
        if (list == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        n3 = b.p(parcel, n3);
        parcel.writeStringList(list);
        b.q(parcel, n3);
    }

    public static void n(Parcel parcel, int n3, Parcelable[] parcelableArray, int n4, boolean bl) {
        if (parcelableArray == null) {
            if (bl) {
                b.o(parcel, n3, 0);
            }
            return;
        }
        int n5 = b.p(parcel, n3);
        int n6 = parcelableArray.length;
        parcel.writeInt(n6);
        for (n3 = 0; n3 < n6; ++n3) {
            Parcelable parcelable = parcelableArray[n3];
            if (parcelable == null) {
                parcel.writeInt(0);
                continue;
            }
            b.r(parcel, parcelable, n4);
        }
        b.q(parcel, n5);
    }

    public static void o(Parcel parcel, int n3, int n4) {
        parcel.writeInt(n3 | n4 << 16);
    }

    public static int p(Parcel parcel, int n3) {
        parcel.writeInt(n3 | 0xFFFF0000);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    public static void q(Parcel parcel, int n3) {
        int n4 = parcel.dataPosition();
        parcel.setDataPosition(n3 - 4);
        parcel.writeInt(n4 - n3);
        parcel.setDataPosition(n4);
    }

    public static void r(Parcel parcel, Parcelable parcelable, int n3) {
        int n4 = parcel.dataPosition();
        parcel.writeInt(1);
        int n5 = parcel.dataPosition();
        parcelable.writeToParcel(parcel, n3);
        n3 = parcel.dataPosition();
        parcel.setDataPosition(n4);
        parcel.writeInt(n3 - n5);
        parcel.setDataPosition(n3);
    }
}

