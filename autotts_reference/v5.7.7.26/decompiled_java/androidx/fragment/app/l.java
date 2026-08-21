/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 */
package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.i;
import androidx.fragment.app.t;
import e0.a;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import n0.h;

public abstract class l
extends i {
    public final Activity c;
    public final Context d;
    public final Handler e;
    public final int f;
    public final FragmentManager g = new t();

    public l(Activity activity, Context context, Handler handler, int n3) {
        this.c = activity;
        this.d = (Context)h.h(context, "context == null");
        this.e = (Handler)h.h(handler, "handler == null");
        this.f = n3;
    }

    public l(FragmentActivity fragmentActivity) {
        this(fragmentActivity, (Context)fragmentActivity, new Handler(), 0);
    }

    public abstract void A();

    public Activity p() {
        return this.c;
    }

    public Context q() {
        return this.d;
    }

    public Handler v() {
        return this.e;
    }

    public abstract void w(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    public abstract Object x();

    public abstract LayoutInflater y();

    public void z(Fragment fragment, Intent intent, int n3, Bundle bundle) {
        if (n3 == -1) {
            a.i(this.d, intent, bundle);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }
}

