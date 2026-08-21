/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 */
package com.google.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.vending.licensing.ILicenseResultListener;

public interface ILicensingService
extends IInterface {
    public void f(long var1, String var3, ILicenseResultListener var4);

    public static abstract class Stub
    extends Binder
    implements ILicensingService {
        public Stub() {
            this.attachInterface(this, "com.android.vending.licensing.ILicensingService");
        }

        public static ILicensingService h(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.android.vending.licensing.ILicensingService");
            if (iInterface != null && iInterface instanceof ILicensingService) {
                return (ILicensingService)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n3, Parcel parcel, Parcel parcel2, int n4) {
            if (n3 != 1) {
                if (n3 != 1598968902) {
                    return super.onTransact(n3, parcel, parcel2, n4);
                }
                parcel2.writeString("com.android.vending.licensing.ILicensingService");
                return true;
            }
            parcel.enforceInterface("com.android.vending.licensing.ILicensingService");
            this.f(parcel.readLong(), parcel.readString(), ILicenseResultListener.Stub.h(parcel.readStrongBinder()));
            return true;
        }

        public static class a
        implements ILicensingService {
            public IBinder d;

            public a(IBinder iBinder) {
                this.d = iBinder;
            }

            public IBinder asBinder() {
                return this.d;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void f(long l3, String string, ILicenseResultListener iLicenseResultListener) {
                Throwable throwable2;
                Parcel parcel;
                block2: {
                    parcel = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.android.vending.licensing.ILicensingService");
                        parcel.writeLong(l3);
                        parcel.writeString(string);
                        string = iLicenseResultListener != null ? iLicenseResultListener.asBinder() : null;
                    }
                    catch (Throwable throwable2) {
                        break block2;
                    }
                    parcel.writeStrongBinder((IBinder)string);
                    this.d.transact(1, parcel, null, 1);
                    parcel.recycle();
                    return;
                }
                parcel.recycle();
                throw throwable2;
            }
        }
    }
}

