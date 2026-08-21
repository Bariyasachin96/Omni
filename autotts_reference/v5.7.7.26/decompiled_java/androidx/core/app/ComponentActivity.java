/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.Window$Callback
 */
package androidx.core.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.f;
import androidx.lifecycle.l;
import o.r;
import o0.t;
import o3.k;

public class ComponentActivity
extends Activity
implements androidx.lifecycle.k,
t.a {
    public final r c = new r();
    public final l d = new l(this);

    @Override
    public boolean d(KeyEvent keyEvent) {
        k.e(keyEvent, "event");
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        k.e(keyEvent, "event");
        View view = this.getWindow().getDecorView();
        k.d(view, "window.decorView");
        if (t.d(view, keyEvent)) {
            return true;
        }
        return t.e(this, view, (Window.Callback)this, keyEvent);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        k.e(keyEvent, "event");
        View view = this.getWindow().getDecorView();
        k.d(view, "window.decorView");
        if (t.d(view, keyEvent)) {
            return true;
        }
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.d.c(this);
    }

    public void onSaveInstanceState(Bundle bundle) {
        k.e(bundle, "outState");
        this.d.m(f.b.e);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public f t() {
        return this.d;
    }

    public final boolean w(String[] stringArray) {
        return this.x(stringArray) ^ true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean x(String[] object) {
        if (object == null) return false;
        if (((Object)object).length == 0) {
            return false;
        }
        switch (object[0]) {
            default: {
                return false;
            }
            case "--autofill": {
                return true;
            }
            case "--contentcapture": {
                if (Build.VERSION.SDK_INT < 29) return false;
                return true;
            }
            case "--list-dumpables": 
            case "--dump-dumpable": {
                if (Build.VERSION.SDK_INT < 33) return false;
                return true;
            }
            case "--translation": 
        }
        if (Build.VERSION.SDK_INT < 31) return false;
        return true;
    }
}

