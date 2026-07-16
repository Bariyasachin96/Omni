/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.a;

public class ResultReceiver
implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator(){

        public ResultReceiver a(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        public ResultReceiver[] b(int n3) {
            return new ResultReceiver[n3];
        }
    };
    public final boolean c;
    public final Handler d = null;
    public a e;

    public ResultReceiver(Parcel parcel) {
        this.c = false;
        this.e = a.a.h(parcel.readStrongBinder());
    }

    public int describeContents() {
        return 0;
    }

    public void o(int n3, Bundle bundle) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeToParcel(Parcel parcel, int n3) {
        synchronized (this) {
            Throwable throwable2;
            block4: {
                block3: {
                    try {
                        if (this.e != null) break block3;
                        b b3 = new b(this);
                        this.e = b3;
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                }
                parcel.writeStrongBinder(this.e.asBinder());
                return;
            }
            throw throwable2;
        }
    }

    public class b
    extends a.a {
        public final ResultReceiver d;

        public b(ResultReceiver resultReceiver) {
            this.d = resultReceiver;
        }

        @Override
        public void g(int n3, Bundle bundle) {
            ResultReceiver resultReceiver = this.d;
            Handler handler = resultReceiver.d;
            if (handler != null) {
                handler.post((Runnable)new c(resultReceiver, n3, bundle));
                return;
            }
            resultReceiver.o(n3, bundle);
        }
    }

    public class c
    implements Runnable {
        public final int c;
        public final Bundle d;
        public final ResultReceiver e;

        public c(ResultReceiver resultReceiver, int n3, Bundle bundle) {
            this.e = resultReceiver;
            this.c = n3;
            this.d = bundle;
        }

        @Override
        public void run() {
            this.e.o(this.c, this.d);
        }
    }
}

