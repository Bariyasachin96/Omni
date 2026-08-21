/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.i;
import androidx.activity.j;
import androidx.activity.q;
import androidx.activity.t;
import androidx.lifecycle.d0;
import androidx.lifecycle.f;
import androidx.lifecycle.l;
import androidx.savedstate.a;
import j1.c;
import j1.d;
import j1.e;

public class k
extends Dialog
implements androidx.lifecycle.k,
q,
d {
    public l c;
    public final c d;
    public final OnBackPressedDispatcher e;

    public k(Context context, int n3) {
        o3.k.e(context, "context");
        super(context, n3);
        this.d = j1.c.d.a(this);
        this.e = new OnBackPressedDispatcher(new j(this));
    }

    public static /* synthetic */ void a(k k3) {
        k.g(k3);
    }

    public static final void g(k k3) {
        o3.k.e(k3, "this$0");
        super.onBackPressed();
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        o3.k.e(view, "view");
        this.e();
        super.addContentView(view, layoutParams);
    }

    @Override
    public final OnBackPressedDispatcher b() {
        return this.e;
    }

    @Override
    public a c() {
        return this.d.b();
    }

    public final l d() {
        l l3;
        l l4 = l3 = this.c;
        if (l3 == null) {
            this.c = l4 = new l(this);
        }
        return l4;
    }

    public void e() {
        Window window = this.getWindow();
        o3.k.b(window);
        window = window.getDecorView();
        o3.k.d(window, "window!!.decorView");
        d0.a((View)window, this);
        window = this.getWindow();
        o3.k.b(window);
        window = window.getDecorView();
        o3.k.d(window, "window!!.decorView");
        t.a((View)window, this);
        window = this.getWindow();
        o3.k.b(window);
        window = window.getDecorView();
        o3.k.d(window, "window!!.decorView");
        j1.e.a((View)window, this);
    }

    public void onBackPressed() {
        this.e.k();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackPressedDispatcher onBackPressedDispatcher = this.e;
            OnBackInvokedDispatcher onBackInvokedDispatcher = i.a(this);
            o3.k.d(onBackInvokedDispatcher, "onBackInvokedDispatcher");
            onBackPressedDispatcher.n(onBackInvokedDispatcher);
        }
        this.d.d(bundle);
        this.d().h(f.a.ON_CREATE);
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();
        o3.k.d(bundle, "super.onSaveInstanceState()");
        this.d.e(bundle);
        return bundle;
    }

    public void onStart() {
        super.onStart();
        this.d().h(f.a.ON_RESUME);
    }

    public void onStop() {
        this.d().h(f.a.ON_DESTROY);
        this.c = null;
        super.onStop();
    }

    public void setContentView(int n3) {
        this.e();
        super.setContentView(n3);
    }

    public void setContentView(View view) {
        o3.k.e(view, "view");
        this.e();
        super.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        o3.k.e(view, "view");
        this.e();
        super.setContentView(view, layoutParams);
    }

    @Override
    public f t() {
        return this.d();
    }
}

