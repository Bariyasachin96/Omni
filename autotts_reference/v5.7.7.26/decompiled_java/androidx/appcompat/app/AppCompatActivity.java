/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.util.DisplayMetrics
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.b;
import androidx.appcompat.app.d;
import androidx.appcompat.widget.s0;
import androidx.fragment.app.FragmentActivity;
import androidx.savedstate.a;
import c0.h;
import c0.s;
import h.b;

public class AppCompatActivity
extends FragmentActivity
implements b,
s.a {
    public d B;
    public Resources C;

    public AppCompatActivity() {
        this.X();
    }

    public d V() {
        if (this.B == null) {
            this.B = androidx.appcompat.app.d.h(this, this);
        }
        return this.B;
    }

    public ActionBar W() {
        return this.V().r();
    }

    public final void X() {
        this.c().h("androidx:appcompat", new a.c(this){
            public final AppCompatActivity a;
            {
                this.a = appCompatActivity;
            }

            @Override
            public Bundle a() {
                Bundle bundle = new Bundle();
                this.a.V().A(bundle);
                return bundle;
            }
        });
        this.D(new a.b(this){
            public final AppCompatActivity a;
            {
                this.a = appCompatActivity;
            }

            @Override
            public void a(Context object) {
                object = this.a.V();
                ((d)object).s();
                ((d)object).w(this.a.c().b("androidx:appcompat"));
            }
        });
    }

    public void Y(s s3) {
        s3.b(this);
    }

    public void Z(k0.b b3) {
    }

    public void a0(int n3) {
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.H();
        this.V().e(view, layoutParams);
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(this.V().g(context));
    }

    public void b0(s s3) {
    }

    public void c0() {
    }

    public void closeOptionsMenu() {
        ActionBar actionBar = this.W();
        if (this.getWindow().hasFeature(0) && (actionBar == null || !actionBar.f())) {
            super.closeOptionsMenu();
        }
    }

    public boolean d0() {
        Object object = this.p();
        if (object != null) {
            if (this.g0((Intent)object)) {
                object = c0.s.d((Context)this);
                this.Y((s)object);
                this.b0((s)object);
                ((s)object).e();
                try {
                    c0.b.k(this);
                }
                catch (IllegalStateException illegalStateException) {
                    this.finish();
                }
            } else {
                this.f0((Intent)object);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int n3 = keyEvent.getKeyCode();
        ActionBar actionBar = this.W();
        if (n3 == 82 && actionBar != null && actionBar.o(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public final boolean e0(KeyEvent keyEvent) {
        return false;
    }

    @Override
    public h.b f(b.a a4) {
        return null;
    }

    public void f0(Intent intent) {
        c0.h.e(this, intent);
    }

    public View findViewById(int n3) {
        return this.V().j(n3);
    }

    public boolean g0(Intent intent) {
        return c0.h.f(this, intent);
    }

    public MenuInflater getMenuInflater() {
        return this.V().p();
    }

    public Resources getResources() {
        Resources resources;
        if (this.C == null && s0.c()) {
            this.C = new s0((Context)this, super.getResources());
        }
        Resources resources2 = resources = this.C;
        if (resources == null) {
            resources2 = super.getResources();
        }
        return resources2;
    }

    public void invalidateOptionsMenu() {
        this.V().t();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.V().v(configuration);
        if (this.C != null) {
            configuration = super.getResources().getConfiguration();
            DisplayMetrics displayMetrics = super.getResources().getDisplayMetrics();
            this.C.updateConfiguration(configuration, displayMetrics);
        }
    }

    public void onContentChanged() {
        this.c0();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.V().x();
    }

    public boolean onKeyDown(int n3, KeyEvent keyEvent) {
        if (this.e0(keyEvent)) {
            return true;
        }
        return super.onKeyDown(n3, keyEvent);
    }

    @Override
    public final boolean onMenuItemSelected(int n3, MenuItem menuItem) {
        if (super.onMenuItemSelected(n3, menuItem)) {
            return true;
        }
        ActionBar actionBar = this.W();
        if (menuItem.getItemId() == 16908332 && actionBar != null && (actionBar.i() & 4) != 0) {
            return this.d0();
        }
        return false;
    }

    public boolean onMenuOpened(int n3, Menu menu) {
        return super.onMenuOpened(n3, menu);
    }

    @Override
    public void onPanelClosed(int n3, Menu menu) {
        super.onPanelClosed(n3, menu);
    }

    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.V().y(bundle);
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        this.V().z();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.V().B();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.V().C();
    }

    public void onTitleChanged(CharSequence charSequence, int n3) {
        super.onTitleChanged(charSequence, n3);
        this.V().L(charSequence);
    }

    public void openOptionsMenu() {
        ActionBar actionBar = this.W();
        if (this.getWindow().hasFeature(0) && (actionBar == null || !actionBar.p())) {
            super.openOptionsMenu();
        }
    }

    @Override
    public Intent p() {
        return c0.h.a(this);
    }

    @Override
    public void q(h.b b3) {
    }

    @Override
    public void setContentView(int n3) {
        this.H();
        this.V().G(n3);
    }

    @Override
    public void setContentView(View view) {
        this.H();
        this.V().H(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.H();
        this.V().I(view, layoutParams);
    }

    public void setTheme(int n3) {
        super.setTheme(n3);
        this.V().K(n3);
    }

    @Override
    public void v(h.b b3) {
    }
}

