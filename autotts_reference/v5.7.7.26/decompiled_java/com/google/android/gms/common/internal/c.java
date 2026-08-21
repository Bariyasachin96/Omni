/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.IBinder
 *  android.os.Parcel
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.b;
import y1.a;

public final class c
extends a
implements b {
    public c(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
    }

    @Override
    public final Account d() {
        Parcel parcel = this.h(2, this.i());
        Account account = (Account)y1.c.a(parcel, Account.CREATOR);
        parcel.recycle();
        return account;
    }
}

