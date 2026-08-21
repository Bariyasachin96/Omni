/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import androidx.appcompat.widget.l0;
import androidx.appcompat.widget.s0;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class j0
extends ContextWrapper {
    public static final Object c = new Object();
    public static ArrayList d;
    public final Resources a;
    public final Resources.Theme b;

    public j0(Context context) {
        super(context);
        if (s0.c()) {
            s0 s02 = new s0((Context)this, context.getResources());
            this.a = s02;
            s02 = s02.newTheme();
            this.b = s02;
            s02.setTo(context.getTheme());
            return;
        }
        this.a = new l0((Context)this, context.getResources());
        this.b = null;
    }

    public static boolean a(Context context) {
        return !(context instanceof j0) && !(context.getResources() instanceof l0) && !(context.getResources() instanceof s0) && s0.c();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Context b(Context object) {
        if (!j0.a((Context)object)) {
            return object;
        }
        Object object2 = c;
        synchronized (object2) {
            Object object3;
            block9: {
                int n3;
                try {
                    object3 = d;
                    if (object3 == null) {
                        d = object3 = new Object();
                        break block9;
                    }
                    for (n3 = object3.size() - 1; n3 >= 0; --n3) {
                        object3 = (WeakReference)d.get(n3);
                        if (object3 != null && ((Reference)object3).get() != null) continue;
                        d.remove(n3);
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
                for (n3 = d.size() - 1; n3 >= 0; --n3) {
                    object3 = (WeakReference)d.get(n3);
                    object3 = object3 != null ? (j0)((Object)((Reference)object3).get()) : null;
                    if (object3 == null || object3.getBaseContext() != object) continue;
                    return object3;
                }
            }
            object3 = new Object((Context)object);
            object = d;
            WeakReference<Object> weakReference = new WeakReference<Object>(object3);
            ((ArrayList)object).add(weakReference);
            return object3;
        }
    }

    public AssetManager getAssets() {
        return this.a.getAssets();
    }

    public Resources getResources() {
        return this.a;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme;
        Resources.Theme theme2 = theme = this.b;
        if (theme == null) {
            theme2 = super.getTheme();
        }
        return theme2;
    }

    public void setTheme(int n3) {
        Resources.Theme theme = this.b;
        if (theme == null) {
            super.setTheme(n3);
            return;
        }
        theme.applyStyle(n3, true);
    }
}

