/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.internal.c;

public interface b
extends IInterface {
    public Account d();

    public static abstract class a
    extends y1.b
    implements b {
        public static b i(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            if (iInterface instanceof b) {
                return (b)iInterface;
            }
            return new c(iBinder);
        }
    }
}

