/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 */
package y1;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public abstract class a
implements IInterface {
    public final IBinder d;
    public final String e;

    public a(IBinder iBinder, String string) {
        this.d = iBinder;
        this.e = string;
    }

    public final IBinder asBinder() {
        return this.d;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Parcel h(int n3, Parcel parcel) {
        Throwable throwable2222222;
        Parcel parcel2 = Parcel.obtain();
        this.d.transact(n3, parcel, parcel2, 0);
        parcel2.readException();
        parcel.recycle();
        return parcel2;
        {
            catch (Throwable throwable2222222) {
            }
            catch (RuntimeException runtimeException) {}
            {
                parcel2.recycle();
                throw runtimeException;
            }
        }
        parcel.recycle();
        throw throwable2222222;
    }

    public final Parcel i() {
        Parcel parcel = Parcel.obtain();
        parcel.writeInterfaceToken(this.e);
        return parcel;
    }
}

