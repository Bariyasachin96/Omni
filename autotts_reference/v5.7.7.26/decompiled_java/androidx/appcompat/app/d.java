/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.LocaleManager
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.LocaleList
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.LocaleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegateImpl;
import androidx.appcompat.app.AppLocalesMetadataHolderService;
import androidx.appcompat.app.e;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Executor;

public abstract class d {
    public static c c = new c(new d());
    public static int d = -100;
    public static k0.b e = null;
    public static k0.b f = null;
    public static Boolean g = null;
    public static boolean h = false;
    public static final o.b i = new o.b();
    public static final Object j = new Object();
    public static final Object k = new Object();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void D(d d3) {
        Object object = j;
        synchronized (object) {
            androidx.appcompat.app.d.E(d3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void E(d d3) {
        Object object = j;
        synchronized (object) {
            Throwable throwable2;
            block5: {
                try {
                    Iterator iterator = i.iterator();
                    while (iterator.hasNext()) {
                        d d4 = (d)((WeakReference)iterator.next()).get();
                        if (d4 != d3 && d4 != null) continue;
                        iterator.remove();
                    }
                }
                catch (Throwable throwable2) {
                    break block5;
                }
                return;
            }
            throw throwable2;
        }
    }

    public static void M(Context context) {
        if (Build.VERSION.SDK_INT >= 33) {
            ComponentName componentName = new ComponentName(context, "androidx.appcompat.app.AppLocalesMetadataHolderService");
            if (context.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
                if (androidx.appcompat.app.d.k().e()) {
                    String string = c0.e.b(context);
                    Object object = context.getSystemService("locale");
                    if (object != null) {
                        b.b(object, a.a(string));
                    }
                }
                context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void N(Context context) {
        if (!androidx.appcompat.app.d.u(context)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            if (h) return;
            c.execute(new androidx.appcompat.app.c(context));
            return;
        }
        Object object = k;
        synchronized (object) {
            Throwable throwable2;
            block8: {
                block9: {
                    k0.b b3;
                    block6: {
                        block7: {
                            try {
                                b3 = e;
                                if (b3 != null) break block6;
                                if (f != null) break block7;
                                f = k0.b.b(c0.e.b(context));
                            }
                            catch (Throwable throwable2) {
                                break block8;
                            }
                        }
                        if (f.e()) {
                            return;
                        }
                        e = f;
                        break block9;
                    }
                    if (b3.equals(f)) return;
                    f = b3 = e;
                    c0.e.a(context, b3.g());
                }
                return;
            }
            throw throwable2;
        }
    }

    public static /* synthetic */ void c(Context context) {
        androidx.appcompat.app.d.M(context);
        h = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void d(d d3) {
        Object object = j;
        synchronized (object) {
            androidx.appcompat.app.d.E(d3);
            o.b b3 = i;
            WeakReference<d> weakReference = new WeakReference<d>(d3);
            b3.add(weakReference);
            return;
        }
    }

    public static d h(Activity activity, androidx.appcompat.app.b b3) {
        return new AppCompatDelegateImpl(activity, b3);
    }

    public static d i(Dialog dialog, androidx.appcompat.app.b b3) {
        return new AppCompatDelegateImpl(dialog, b3);
    }

    public static k0.b k() {
        if (Build.VERSION.SDK_INT >= 33) {
            Object object = androidx.appcompat.app.d.o();
            if (object != null) {
                return k0.b.h(b.a(object));
            }
        } else {
            k0.b b3 = e;
            if (b3 != null) {
                return b3;
            }
        }
        return k0.b.d();
    }

    public static int m() {
        return d;
    }

    public static Object o() {
        Iterator iterator = i.iterator();
        while (iterator.hasNext()) {
            d d3 = (d)((WeakReference)iterator.next()).get();
            if (d3 == null || (d3 = d3.l()) == null) continue;
            return d3.getSystemService("locale");
        }
        return null;
    }

    public static k0.b q() {
        return e;
    }

    public static boolean u(Context context) {
        block4: {
            if (g == null) {
                context = AppLocalesMetadataHolderService.a((Context)context).metaData;
                if (context == null) break block4;
                try {
                    g = context.getBoolean("autoStoreLocales");
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    g = Boolean.FALSE;
                }
            }
        }
        return g;
    }

    public abstract void A(Bundle var1);

    public abstract void B();

    public abstract void C();

    public abstract boolean F(int var1);

    public abstract void G(int var1);

    public abstract void H(View var1);

    public abstract void I(View var1, ViewGroup.LayoutParams var2);

    public void J(OnBackInvokedDispatcher onBackInvokedDispatcher) {
    }

    public abstract void K(int var1);

    public abstract void L(CharSequence var1);

    public abstract void e(View var1, ViewGroup.LayoutParams var2);

    public void f(Context context) {
    }

    public Context g(Context context) {
        this.f(context);
        return context;
    }

    public abstract View j(int var1);

    public abstract Context l();

    public abstract int n();

    public abstract MenuInflater p();

    public abstract ActionBar r();

    public abstract void s();

    public abstract void t();

    public abstract void v(Configuration var1);

    public abstract void w(Bundle var1);

    public abstract void x();

    public abstract void y(Bundle var1);

    public abstract void z();

    public static abstract class a {
        public static LocaleList a(String string) {
            return LocaleList.forLanguageTags((String)string);
        }
    }

    public static abstract class b {
        public static LocaleList a(Object object) {
            return ((LocaleManager)object).getApplicationLocales();
        }

        public static void b(Object object, LocaleList localeList) {
            ((LocaleManager)object).setApplicationLocales(localeList);
        }
    }

    public static class c
    implements Executor {
        public final Object c = new Object();
        public final Queue d = new ArrayDeque();
        public final Executor e;
        public Runnable f;

        public c(Executor executor) {
            this.e = executor;
        }

        public static /* synthetic */ void c(c c3, Runnable runnable) {
            c3.getClass();
            try {
                runnable.run();
                return;
            }
            finally {
                c3.d();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void d() {
            Object object = this.c;
            synchronized (object) {
                Throwable throwable2;
                block4: {
                    block3: {
                        try {
                            Runnable runnable;
                            this.f = runnable = (Runnable)this.d.poll();
                            if (runnable == null) break block3;
                            this.e.execute(runnable);
                        }
                        catch (Throwable throwable2) {
                            break block4;
                        }
                    }
                    return;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void execute(Runnable runnable) {
            Object object = this.c;
            synchronized (object) {
                Throwable throwable2;
                block4: {
                    block3: {
                        try {
                            Queue queue = this.d;
                            e e3 = new e(this, runnable);
                            queue.add(e3);
                            if (this.f != null) break block3;
                            this.d();
                        }
                        catch (Throwable throwable2) {
                            break block4;
                        }
                    }
                    return;
                }
                throw throwable2;
            }
        }
    }

    public static class d
    implements Executor {
        @Override
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }
}

