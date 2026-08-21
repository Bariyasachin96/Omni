/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 */
package u1;

import android.os.IBinder;
import android.os.Parcel;
import u1.k;
import x1.a;

public final class i
extends y1.a
implements k {
    public i(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICertData");
    }

    @Override
    public final a a() {
        Parcel parcel = this.h(1, this.i());
        a a4 = a.a.i(parcel.readStrongBinder());
        parcel.recycle();
        return a4;
    }

    @Override
    public final int b() {
        Parcel parcel = this.h(2, this.i());
        int n3 = parcel.readInt();
        parcel.recycle();
        return n3;
    }
}

