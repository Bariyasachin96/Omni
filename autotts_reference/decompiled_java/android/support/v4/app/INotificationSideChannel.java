/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.app;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

public interface INotificationSideChannel
extends IInterface {
    public static final String DESCRIPTOR = "android$support$v4$app$INotificationSideChannel".replace('$', '.');

    public void cancel(String var1, int var2, String var3);

    public void cancelAll(String var1);

    public void notify(String var1, int var2, String var3, Notification var4);

    public static class Default
    implements INotificationSideChannel {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancel(String string, int n3, String string2) {
        }

        @Override
        public void cancelAll(String string) {
        }

        @Override
        public void notify(String string, int n3, String string2, Notification notification) {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INotificationSideChannel {
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_cancelAll = 3;
        static final int TRANSACTION_notify = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INotificationSideChannel asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INotificationSideChannel) {
                return (INotificationSideChannel)iInterface;
            }
            return new Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n3, Parcel parcel, Parcel parcel2, int n4) {
            String string = DESCRIPTOR;
            if (n3 >= 1 && n3 <= 0xFFFFFF) {
                parcel.enforceInterface(string);
            }
            if (n3 == 1598968902) {
                parcel2.writeString(string);
                return true;
            }
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        return super.onTransact(n3, parcel, parcel2, n4);
                    }
                    this.cancelAll(parcel.readString());
                } else {
                    this.cancel(parcel.readString(), parcel.readInt(), parcel.readString());
                }
            } else {
                this.notify(parcel.readString(), parcel.readInt(), parcel.readString(), (Notification)_Parcel.readTypedObject(parcel, Notification.CREATOR));
            }
            return true;
        }

        public static class Proxy
        implements INotificationSideChannel {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancel(String string, int n3, String string2) {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(DESCRIPTOR);
                    parcel.writeString(string);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    this.mRemote.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void cancelAll(String string) {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(DESCRIPTOR);
                    parcel.writeString(string);
                    this.mRemote.transact(3, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public void notify(String string, int n3, String string2, Notification notification) {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(DESCRIPTOR);
                    parcel.writeString(string);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    _Parcel.writeTypedObject(parcel, (Parcelable)notification, 0);
                    this.mRemote.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        private static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return (T)creator.createFromParcel(parcel);
            }
            return null;
        }

        private static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t3, int n3) {
            if (t3 != null) {
                parcel.writeInt(1);
                t3.writeToParcel(parcel, n3);
                return;
            }
            parcel.writeInt(0);
        }
    }
}

