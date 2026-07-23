/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.res.Configuration
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.util.Log
 */
package c0;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public abstract class d {
    public static final Class a;
    public static final Field b;
    public static final Field c;
    public static final Method d;
    public static final Method e;
    public static final Method f;
    public static final Handler g;

    static {
        Class clazz;
        g = new Handler(Looper.getMainLooper());
        a = clazz = c0.d.a();
        b = c0.d.b();
        c = c0.d.f();
        d = c0.d.d(clazz);
        e = c0.d.c(clazz);
        f = c0.d.e(clazz);
    }

    public static Class a() {
        try {
            Class<?> clazz = Class.forName("android.app.ActivityThread");
            return clazz;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static Field b() {
        try {
            Field field = Activity.class.getDeclaredField("mMainThread");
            ((AccessibleObject)field).setAccessible(true);
            return field;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static Method c(Class genericDeclaration) {
        if (genericDeclaration == null) {
            return null;
        }
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE);
            ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            return genericDeclaration;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static Method d(Class genericDeclaration) {
        if (genericDeclaration == null) {
            return null;
        }
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE, String.class);
            ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            return genericDeclaration;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Method e(Class genericDeclaration) {
        if (!c0.d.g()) return null;
        if (genericDeclaration == null) {
            return null;
        }
        try {
            Class<Integer> clazz = Integer.TYPE;
            Class<Boolean> clazz2 = Boolean.TYPE;
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod("requestRelaunchActivity", IBinder.class, List.class, List.class, clazz, clazz2, Configuration.class, Configuration.class, clazz2, clazz2);
            ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            return genericDeclaration;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static Field f() {
        try {
            Field field = Activity.class.getDeclaredField("mToken");
            ((AccessibleObject)field).setAccessible(true);
            return field;
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static boolean g() {
        int n3 = Build.VERSION.SDK_INT;
        return n3 == 26 || n3 == 27;
        {
        }
    }

    public static boolean h(Object runnable, int n3, Activity object) {
        Throwable throwable2;
        block5: {
            block4: {
                Object object2;
                try {
                    object2 = c.get(object);
                    if (object2 != runnable) break block4;
                }
                catch (Throwable throwable2) {
                    break block5;
                }
                if (object.hashCode() == n3) {
                    object = b.get(object);
                    Handler handler = g;
                    runnable = new Runnable(object, object2){
                        public final Object c;
                        public final Object d;
                        {
                            this.c = object;
                            this.d = object2;
                        }

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        @Override
                        public void run() {
                            RuntimeException runtimeException2;
                            block5: {
                                Throwable throwable2;
                                block4: {
                                    try {
                                        Method method = d;
                                        if (method != null) {
                                            method.invoke(this.c, this.d, Boolean.FALSE, "AppCompat recreation");
                                            return;
                                        }
                                    }
                                    catch (Throwable throwable2) {
                                        break block4;
                                    }
                                    catch (RuntimeException runtimeException2) {
                                        break block5;
                                    }
                                    e.invoke(this.c, this.d, Boolean.FALSE);
                                    return;
                                }
                                Log.e((String)"ActivityRecreator", (String)"Exception while invoking performStopActivity", (Throwable)throwable2);
                                return;
                            }
                            if (runtimeException2.getClass() != RuntimeException.class) return;
                            if (runtimeException2.getMessage() == null) return;
                            if (runtimeException2.getMessage().startsWith("Unable to stop")) throw runtimeException2;
                        }
                    };
                    handler.postAtFrontOfQueue(runnable);
                    return true;
                }
            }
            return false;
        }
        Log.e((String)"ActivityRecreator", (String)"Exception while fetching field values", (Throwable)throwable2);
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean i(Activity var0) {
        block11: {
            block10: {
                if (Build.VERSION.SDK_INT >= 28) {
                    var0 /* !! */ .recreate();
                    return true;
                }
                if (c0.d.g() && c0.d.f == null) {
                    return false;
                }
                if (c0.d.e == null && c0.d.d == null) {
                    return false;
                }
                try {
                    var4_2 = c0.d.c.get(var0 /* !! */ );
                    if (var4_2 == null) {
                        return false;
                    }
                    var5_4 = c0.d.b.get(var0 /* !! */ );
                    if (var5_4 == null) {
                        return false;
                    }
                    var2_5 = var0 /* !! */ .getApplication();
                    var1_6 = new d(var0 /* !! */ );
                    var2_5.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)var1_6);
                    var3_7 = c0.d.g;
                    var6_8 = new Runnable(var1_6, var4_2){
                        public final d c;
                        public final Object d;
                        {
                            this.c = d3;
                            this.d = object;
                        }

                        @Override
                        public void run() {
                            this.c.a = this.d;
                        }
                    };
                    var3_7.post(var6_8);
                }
                catch (Throwable var0_1) {
                    return false;
                }
                try {
                    if (!c0.d.g()) break block10;
                    var6_8 = c0.d.f;
                    var0 /* !! */  = Boolean.FALSE;
                    var6_8.invoke(var5_4, new Object[]{var4_2, null, null, 0, var0 /* !! */ , null, null, var0 /* !! */ , var0 /* !! */ });
                    break block11;
                }
                catch (Throwable var4_3) {
                    ** GOTO lbl41
                }
            }
            var0 /* !! */ .recreate();
        }
        var0 /* !! */  = new Runnable(var2_5, var1_6){
            public final Application c;
            public final d d;
            {
                this.c = application;
                this.d = d3;
            }

            @Override
            public void run() {
                this.c.unregisterActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)this.d);
            }
        };
        var3_7.post((Runnable)var0 /* !! */ );
        return true;
lbl41:
        // 1 sources

        var3_7 = c0.d.g;
        var0 /* !! */  = new /* invalid duplicate definition of identical inner class */;
        var3_7.post((Runnable)var0 /* !! */ );
        throw var4_3;
    }

    public static final class d
    implements Application.ActivityLifecycleCallbacks {
        public Object a;
        public Activity b;
        public final int c;
        public boolean d = false;
        public boolean e = false;
        public boolean f = false;

        public d(Activity activity) {
            this.b = activity;
            this.c = activity.hashCode();
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.b == activity) {
                this.b = null;
                this.e = true;
            }
        }

        public void onActivityPaused(Activity activity) {
            if (this.e && !this.f && !this.d && c0.d.h(this.a, this.c, activity)) {
                this.f = true;
                this.a = null;
            }
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            if (this.b == activity) {
                this.d = true;
            }
        }

        public void onActivityStopped(Activity activity) {
        }
    }
}

