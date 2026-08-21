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
 *  android.os.RemoteException
 */
package com.pairip.licensecheck;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ILicenseV2ResultListener
extends IInterface {
    public static final String DESCRIPTOR = "com.android.vending.licensing.ILicenseV2ResultListener";

    public void verifyLicense(int var1, Bundle var2) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements ILicenseV2ResultListener {
        static final int TRANSACTION_VERIFY_LICENSE = 1;

        public Stub() {
            this.attachInterface(this, ILicenseV2ResultListener.DESCRIPTOR);
        }

        private static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return (T)creator.createFromParcel(parcel);
            }
            return null;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n3, Parcel parcel, Parcel parcel2, int n4) throws RemoteException {
            if (n3 >= 1 && n3 <= 0xFFFFFF) {
                parcel.enforceInterface(ILicenseV2ResultListener.DESCRIPTOR);
            }
            if (n3 != 1) {
                if (n3 != 1598968902) {
                    return super.onTransact(n3, parcel, parcel2, n4);
                }
                parcel2.writeString(ILicenseV2ResultListener.DESCRIPTOR);
            } else {
                this.verifyLicense(parcel.readInt(), (Bundle)Stub.readTypedObject(parcel, Bundle.CREATOR));
            }
            return true;
        }
    }
}

