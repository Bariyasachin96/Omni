/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnKeyListener
 *  android.os.Build$VERSION
 *  android.view.KeyEvent
 *  android.view.KeyEvent$Callback
 *  android.view.KeyEvent$DispatcherState
 *  android.view.View
 *  android.view.Window
 *  android.view.Window$Callback
 */
package o0;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import o0.x0;

public abstract class t {
    public static boolean a = false;
    public static Method b;
    public static boolean c = false;
    public static Field d;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean a(ActionBar object, KeyEvent keyEvent) {
        Method method;
        if (!a) {
            try {
                b = object.getClass().getMethod("onMenuKeyEvent", KeyEvent.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            a = true;
        }
        if ((method = b) == null) return false;
        try {
            object = method.invoke(object, keyEvent);
            if (object != null) return (Boolean)object;
            return false;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return false;
        }
    }

    public static boolean b(Activity activity, KeyEvent keyEvent) {
        Object object;
        activity.onUserInteraction();
        Window window = activity.getWindow();
        if (window.hasFeature(8)) {
            object = activity.getActionBar();
            if (keyEvent.getKeyCode() == 82 && object != null && t.a(object, keyEvent)) {
                return true;
            }
        }
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        object = window.getDecorView();
        if (x0.h((View)object, keyEvent)) {
            return true;
        }
        object = object != null ? object.getKeyDispatcherState() : null;
        return keyEvent.dispatch((KeyEvent.Callback)activity, (KeyEvent.DispatcherState)object, (Object)activity);
    }

    public static boolean c(Dialog dialog, KeyEvent keyEvent) {
        Object object = t.f(dialog);
        if (object != null && object.onKey((DialogInterface)dialog, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        object = dialog.getWindow();
        if (object.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (x0.h((View)(object = object.getDecorView()), keyEvent)) {
            return true;
        }
        object = object != null ? object.getKeyDispatcherState() : null;
        return keyEvent.dispatch((KeyEvent.Callback)dialog, (KeyEvent.DispatcherState)object, (Object)dialog);
    }

    public static boolean d(View view, KeyEvent keyEvent) {
        return x0.i(view, keyEvent);
    }

    public static boolean e(a a4, View view, Window.Callback callback, KeyEvent keyEvent) {
        if (a4 == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return a4.d(keyEvent);
        }
        if (callback instanceof Activity) {
            return t.b((Activity)callback, keyEvent);
        }
        if (callback instanceof Dialog) {
            return t.c((Dialog)callback, keyEvent);
        }
        return view != null && x0.h(view, keyEvent) || a4.d(keyEvent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DialogInterface.OnKeyListener f(Dialog dialog) {
        Field field;
        if (!c) {
            try {
                d = field = Dialog.class.getDeclaredField("mOnKeyListener");
                ((AccessibleObject)field).setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            c = true;
        }
        if ((field = d) == null) return null;
        try {
            return (DialogInterface.OnKeyListener)field.get(dialog);
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    public static interface a {
        public boolean d(KeyEvent var1);
    }
}

