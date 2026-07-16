/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 */
package y1;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public abstract class b
extends Binder
implements IInterface {
    public b(String string) {
        this.attachInterface(this, string);
    }

    public final IBinder asBinder() {
        return this;
    }

    public boolean h(int n3, Parcel parcel, Parcel parcel2, int n4) {
        return false;
    }

    public final boolean onTransact(int n3, Parcel parcel, Parcel parcel2, int n4) {
        if (n3 > 0xFFFFFF) {
            if (super.onTransact(n3, parcel, parcel2, n4)) {
                return true;
            }
        } else {
            parcel.enforceInterface(this.getInterfaceDescriptor());
        }
        return this.h(n3, parcel, parcel2, n4);
    }
}

