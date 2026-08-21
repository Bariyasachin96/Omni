/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.app.s;

public class a {
    public static a c;
    public final Object a = new Object();
    public final Handler b = new Handler(Looper.getMainLooper(), new Handler.Callback(this){
        public final a a;
        {
            this.a = a4;
        }

        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            a a4 = this.a;
            s.a(message.obj);
            a4.c(null);
            return true;
        }
    });

    public static a b() {
        if (c == null) {
            c = new a();
        }
        return c;
    }

    public final boolean a(c c3, int n3) {
        throw null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void c(c c3) {
        Object object = this.a;
        synchronized (object) {
            this.a(c3, 2);
            return;
        }
    }

    public final boolean d(b b3) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void e(b b3) {
        Object object = this.a;
        synchronized (object) {
            try {
                if (this.d(b3)) throw null;
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void f(b b3) {
        Object object = this.a;
        synchronized (object) {
            try {
                if (this.d(b3)) throw null;
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static interface b {
    }

    public static abstract class c {
    }
}

