/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package android.support.v4.os;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

public interface a
extends IInterface {
    public static final String a = "android$support$v4$os$IResultReceiver".replace('$', '.');

    public void g(int var1, Bundle var2);

    public static abstract class android.support.v4.os.a$a
    extends Binder
    implements android.support.v4.os.a {
        public cfr_renamed_0() {
            this.attachInterface(this, a);
        }

        public static android.support.v4.os.a h(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(a);
            if (iInterface != null && iInterface instanceof android.support.v4.os.a) {
                return (android.support.v4.os.a)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n3, Parcel parcel, Parcel parcel2, int n4) {
            String string = a;
            if (n3 >= 1 && n3 <= 0xFFFFFF) {
                parcel.enforceInterface(string);
            }
            if (n3 == 1598968902) {
                parcel2.writeString(string);
                return true;
            }
            if (n3 != 1) {
                return super.onTransact(n3, parcel, parcel2, n4);
            }
            this.g(parcel.readInt(), (Bundle)b.b(parcel, Bundle.CREATOR));
            return true;
        }

        public static class a
        implements android.support.v4.os.a {
            public IBinder d;

            public a(IBinder iBinder) {
                this.d = iBinder;
            }

            public IBinder asBinder() {
                return this.d;
            }
        }
    }

    public static abstract class b {
        public static Object b(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }
}

