/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package v1;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public abstract class a {
    public static Bundle a(Parcel parcel, int n3) {
        n3 = a.p(parcel, n3);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(n4 + n3);
        return bundle;
    }

    public static int[] b(Parcel parcel, int n3) {
        n3 = a.p(parcel, n3);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        int[] nArray = parcel.createIntArray();
        parcel.setDataPosition(n4 + n3);
        return nArray;
    }

    public static Parcelable c(Parcel parcel, int n3, Parcelable.Creator creator) {
        int n4 = a.p(parcel, n3);
        n3 = parcel.dataPosition();
        if (n4 == 0) {
            return null;
        }
        creator = (Parcelable)creator.createFromParcel(parcel);
        parcel.setDataPosition(n3 + n4);
        return creator;
    }

    public static String d(Parcel parcel, int n3) {
        int n4 = a.p(parcel, n3);
        n3 = parcel.dataPosition();
        if (n4 == 0) {
            return null;
        }
        String string = parcel.readString();
        parcel.setDataPosition(n3 + n4);
        return string;
    }

    public static ArrayList e(Parcel parcel, int n3) {
        int n4 = a.p(parcel, n3);
        n3 = parcel.dataPosition();
        if (n4 == 0) {
            return null;
        }
        ArrayList arrayList = parcel.createStringArrayList();
        parcel.setDataPosition(n3 + n4);
        return arrayList;
    }

    public static Object[] f(Parcel parcel, int n3, Parcelable.Creator objectArray) {
        int n4 = a.p(parcel, n3);
        n3 = parcel.dataPosition();
        if (n4 == 0) {
            return null;
        }
        objectArray = parcel.createTypedArray((Parcelable.Creator)objectArray);
        parcel.setDataPosition(n3 + n4);
        return objectArray;
    }

    public static void g(Parcel parcel, int n3) {
        if (parcel.dataPosition() == n3) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(n3).length() + 26);
        stringBuilder.append("Overread allowed size end=");
        stringBuilder.append(n3);
        throw new a(stringBuilder.toString(), parcel);
    }

    public static int h(int n3) {
        return (char)n3;
    }

    public static boolean i(Parcel parcel, int n3) {
        a.s(parcel, n3, 4);
        return parcel.readInt() != 0;
    }

    public static float j(Parcel parcel, int n3) {
        a.s(parcel, n3, 4);
        return parcel.readFloat();
    }

    public static int k(Parcel parcel) {
        return parcel.readInt();
    }

    public static IBinder l(Parcel parcel, int n3) {
        n3 = a.p(parcel, n3);
        int n4 = parcel.dataPosition();
        if (n3 == 0) {
            return null;
        }
        IBinder iBinder = parcel.readStrongBinder();
        parcel.setDataPosition(n4 + n3);
        return iBinder;
    }

    public static int m(Parcel parcel, int n3) {
        a.s(parcel, n3, 4);
        return parcel.readInt();
    }

    public static Integer n(Parcel parcel, int n3) {
        int n4 = a.p(parcel, n3);
        if (n4 == 0) {
            return null;
        }
        a.t(parcel, n3, n4, 4);
        return parcel.readInt();
    }

    public static long o(Parcel parcel, int n3) {
        a.s(parcel, n3, 8);
        return parcel.readLong();
    }

    public static int p(Parcel parcel, int n3) {
        if ((n3 & 0xFFFF0000) != -65536) {
            return (char)(n3 >> 16);
        }
        return parcel.readInt();
    }

    public static void q(Parcel parcel, int n3) {
        n3 = a.p(parcel, n3);
        parcel.setDataPosition(parcel.dataPosition() + n3);
    }

    public static int r(Parcel parcel) {
        int n3 = a.k(parcel);
        int n4 = a.p(parcel, n3);
        int n5 = a.h(n3);
        int n6 = parcel.dataPosition();
        if (n5 == 20293) {
            if ((n4 += n6) >= n6 && n4 <= parcel.dataSize()) {
                return n4;
            }
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(n6).length() + 32 + String.valueOf(n4).length());
            stringBuilder.append("Size read is invalid start=");
            stringBuilder.append(n6);
            stringBuilder.append(" end=");
            stringBuilder.append(n4);
            throw new a(stringBuilder.toString(), parcel);
        }
        throw new a("Expected object header. Got 0x".concat(String.valueOf(Integer.toHexString(n3))), parcel);
    }

    public static void s(Parcel parcel, int n3, int n4) {
        if ((n3 = a.p(parcel, n3)) == n4) {
            return;
        }
        String string = Integer.toHexString(n3);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(n4).length() + 19 + String.valueOf(n3).length() + 4 + String.valueOf(string).length() + 1);
        stringBuilder.append("Expected size ");
        stringBuilder.append(n4);
        stringBuilder.append(" got ");
        stringBuilder.append(n3);
        stringBuilder.append(" (0x");
        stringBuilder.append(string);
        stringBuilder.append(")");
        throw new a(stringBuilder.toString(), parcel);
    }

    public static void t(Parcel parcel, int n3, int n4, int n5) {
        if (n4 == n5) {
            return;
        }
        String string = Integer.toHexString(n4);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(n5).length() + 19 + String.valueOf(n4).length() + 4 + String.valueOf(string).length() + 1);
        stringBuilder.append("Expected size ");
        stringBuilder.append(n5);
        stringBuilder.append(" got ");
        stringBuilder.append(n4);
        stringBuilder.append(" (0x");
        stringBuilder.append(string);
        stringBuilder.append(")");
        throw new a(stringBuilder.toString(), parcel);
    }

    public static class a
    extends RuntimeException {
        public a(String string, Parcel object) {
            int n3 = object.dataPosition();
            int n4 = object.dataSize();
            object = new StringBuilder(String.valueOf(string).length() + 13 + String.valueOf(n3).length() + 6 + String.valueOf(n4).length());
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" Parcel: pos=");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(" size=");
            ((StringBuilder)object).append(n4);
            super(((StringBuilder)object).toString());
        }
    }
}

