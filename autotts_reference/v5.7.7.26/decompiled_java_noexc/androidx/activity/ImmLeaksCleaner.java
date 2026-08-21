/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.View
 *  android.view.inputmethod.InputMethodManager
 */
package androidx.activity;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

final class ImmLeaksCleaner
implements i {
    public static int b;
    public static Field c;
    public static Field d;
    public static Field e;
    public Activity a;

    public static void h() {
        Field field;
        b = 2;
        d = field = InputMethodManager.class.getDeclaredField("mServedView");
        ((AccessibleObject)field).setAccessible(true);
        e = field = InputMethodManager.class.getDeclaredField("mNextServedView");
        ((AccessibleObject)field).setAccessible(true);
        c = field = InputMethodManager.class.getDeclaredField("mH");
        ((AccessibleObject)field).setAccessible(true);
        b = 1;
    }

    @Override
    public void d(k object, f.a a4) {
        if (a4 == f.a.ON_DESTROY) {
            if (b == 0) {
                ImmLeaksCleaner.h();
            }
            if (b == 1 && (object = c.get((Object)(a4 = (InputMethodManager)this.a.getSystemService("input_method")))) != null) {
                synchronized (object) {
                    View view = (View)d.get((Object)a4);
                    if (view == null) {
                        return;
                    }
                    if (view.isAttachedToWindow()) {
                        return;
                    }
                    e.set((Object)a4, null);
                }
                a4.isActive();
                return;
            }
        }
    }
}

