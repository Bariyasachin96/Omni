/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.WeakHashMap;
import t1.a;

public final class zza
extends Fragment {
    public static final WeakHashMap d = new WeakHashMap();
    public final a c = new a();

    public final void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        super.dump(string, fileDescriptor, printWriter, stringArray);
        this.c.h(string, fileDescriptor, printWriter, stringArray);
    }

    public final void onActivityResult(int n3, int n4, Intent intent) {
        super.onActivityResult(n3, n4, intent);
        this.c.d(n3, n4, intent);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.c.a(bundle);
    }

    public final void onDestroy() {
        super.onDestroy();
        this.c.g();
    }

    public final void onResume() {
        super.onResume();
        this.c.c();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.c.e(bundle);
    }

    public final void onStart() {
        super.onStart();
        this.c.b();
    }

    public final void onStop() {
        super.onStop();
        this.c.f();
    }
}

