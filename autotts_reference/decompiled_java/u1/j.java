/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 */
package u1;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import u1.i;
import u1.k;
import y1.b;
import y1.c;

public abstract class j
extends b
implements k {
    public j() {
        super("com.google.android.gms.common.internal.ICertData");
    }

    public static k i(IBinder iBinder) {
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
        if (iInterface instanceof k) {
            return (k)iInterface;
        }
        return new i(iBinder);
    }

    @Override
    public final boolean h(int n3, Parcel object, Parcel parcel, int n4) {
        if (n3 != 1) {
            if (n3 != 2) {
                return false;
            }
            n3 = this.b();
            parcel.writeNoException();
            parcel.writeInt(n3);
        } else {
            object = this.a();
            parcel.writeNoException();
            c.b(parcel, (IInterface)object);
        }
        return true;
    }
}

