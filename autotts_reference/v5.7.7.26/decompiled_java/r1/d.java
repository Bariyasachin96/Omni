/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.util.Log
 */
package r1;

import android.os.RemoteException;
import android.util.Log;
import java.util.Arrays;
import u1.b;
import u1.j;
import u1.k;
import x1.a;

public abstract class d
extends j {
    public final int d;

    public d(byte[] byArray) {
        boolean bl = byArray.length == 25;
        b.a(bl);
        this.d = Arrays.hashCode(byArray);
    }

    @Override
    public final a a() {
        return x1.b.k(this.j());
    }

    @Override
    public final int b() {
        return this.d;
    }

    public final boolean equals(Object object) {
        RemoteException remoteException2;
        block7: {
            block6: {
                block5: {
                    if (!(object instanceof k)) {
                        return false;
                    }
                    try {
                        object = (k)object;
                        if (object.b() == this.d) break block5;
                        return false;
                    }
                    catch (RemoteException remoteException2) {}
                }
                object = object.a();
                if (object == null) break block6;
                object = (byte[])x1.b.j((a)object);
                boolean bl = Arrays.equals(this.j(), (byte[])object);
                return bl;
                break block7;
            }
            return false;
        }
        Log.e((String)"GoogleCertificates", (String)"Failed to get Google certificates from remote", (Throwable)remoteException2);
        return false;
    }

    public final int hashCode() {
        return this.d;
    }

    public abstract byte[] j();
}

