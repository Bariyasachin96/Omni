/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Binder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.b;

public abstract class a
extends b.a {
    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Account j(b b3) {
        Throwable throwable2222222;
        if (b3 == null) {
            return null;
        }
        long l3 = Binder.clearCallingIdentity();
        b3 = b3.d();
        Binder.restoreCallingIdentity((long)l3);
        return b3;
        {
            catch (Throwable throwable2222222) {
            }
            catch (RemoteException remoteException) {}
            {
                Log.w((String)"AccountAccessor", (String)"Remote account accessor probably died");
            }
            Binder.restoreCallingIdentity((long)l3);
            return null;
        }
        Binder.restoreCallingIdentity((long)l3);
        throw throwable2222222;
    }
}

