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

public interface ILicenseResultListener
extends IInterface {
    public void e(int var1, String var2, String var3);

    public static abstract class Stub
    extends Binder
    implements ILicenseResultListener {
        public Stub() {
            this.attachInterface(this, "com.android.vending.licensing.ILicenseResultListener");
        }

        public static ILicenseResultListener h(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.android.vending.licensing.ILicenseResultListener");
            if (iInterface != null && iInterface instanceof ILicenseResultListener) {
                return (ILicenseResultListener)iInterface;
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
                parcel2.writeString("com.android.vending.licensing.ILicenseResultListener");
                return true;
            }
            parcel.enforceInterface("com.android.vending.licensing.ILicenseResultListener");
            this.e(parcel.readInt(), parcel.readString(), parcel.readString());
            return true;
        }

        public static class a
        implements ILicenseResultListener {
            public IBinder d;

            public a(IBinder iBinder) {
                this.d = iBinder;
            }

            public IBinder asBinder() {
                return this.d;
            }
        }
    }
}

