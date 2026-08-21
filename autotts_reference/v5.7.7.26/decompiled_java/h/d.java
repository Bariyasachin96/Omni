/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.view.LayoutInflater
 */
package h;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.LayoutInflater;
import c.i;

public class d
extends ContextWrapper {
    public static Configuration f;
    public int a;
    public Resources.Theme b;
    public LayoutInflater c;
    public Configuration d;
    public Resources e;

    public d(Context context, int n3) {
        super(context);
        this.a = n3;
    }

    public d(Context context, Resources.Theme theme) {
        super(context);
        this.b = theme;
    }

    public static boolean e(Configuration configuration) {
        if (configuration == null) {
            return true;
        }
        if (f == null) {
            Configuration configuration2 = new Configuration();
            configuration2.fontScale = 0.0f;
            f = configuration2;
        }
        return configuration.equals(f);
    }

    public void a(Configuration configuration) {
        if (this.e == null) {
            if (this.d == null) {
                this.d = new Configuration(configuration);
                return;
            }
            throw new IllegalStateException("Override configuration has already been set");
        }
        throw new IllegalStateException("getResources() or getAssets() has already been called");
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public final Resources b() {
        if (this.e == null) {
            Configuration configuration = this.d;
            this.e = configuration != null && !h.d.e(configuration) ? this.createConfigurationContext(this.d).getResources() : super.getResources();
        }
        return this.e;
    }

    public int c() {
        return this.a;
    }

    public final void d() {
        boolean bl = this.b == null;
        if (bl) {
            this.b = this.getResources().newTheme();
            Resources.Theme theme = this.getBaseContext().getTheme();
            if (theme != null) {
                this.b.setTo(theme);
            }
        }
        this.f(this.b, this.a, bl);
    }

    public void f(Resources.Theme theme, int n3, boolean bl) {
        theme.applyStyle(n3, true);
    }

    public AssetManager getAssets() {
        return this.getResources().getAssets();
    }

    public Resources getResources() {
        return this.b();
    }

    public Object getSystemService(String string) {
        if ("layout_inflater".equals(string)) {
            if (this.c == null) {
                this.c = LayoutInflater.from((Context)this.getBaseContext()).cloneInContext((Context)this);
            }
            return this.c;
        }
        return this.getBaseContext().getSystemService(string);
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.b;
        if (theme != null) {
            return theme;
        }
        if (this.a == 0) {
            this.a = i.Theme_AppCompat_Light;
        }
        this.d();
        return this.b;
    }

    public void setTheme(int n3) {
        if (this.a != n3) {
            this.a = n3;
            this.d();
        }
    }
}

