/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 */
package d0;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import d0.a;

public interface b
extends IInterface {
    public static final String c = "androidx$core$app$unusedapprestrictions$IUnusedAppRestrictionsBackportService".replace('$', '.');

    public void c(d0.a var1);

    public static abstract class a
    extends Binder
    implements b {
        public a() {
            this.attachInterface(this, c);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n3, Parcel parcel, Parcel parcel2, int n4) {
            String string = c;
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
            this.c(a.a.h(parcel.readStrongBinder()));
            return true;
        }
    }
}

