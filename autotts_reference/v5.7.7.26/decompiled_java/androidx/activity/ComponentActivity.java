/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.res.Configuration
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver$OnDrawListener
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.activity;

import a.b;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.h;
import androidx.activity.m;
import androidx.activity.n;
import androidx.activity.q;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.s;
import androidx.activity.t;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.b0;
import androidx.lifecycle.c0;
import androidx.lifecycle.d0;
import androidx.lifecycle.e0;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import androidx.lifecycle.l;
import androidx.lifecycle.u;
import androidx.lifecycle.z;
import b.a;
import c0.o;
import c0.p;
import d3.j;
import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import o0.v;
import o0.w;
import o0.y;

public class ComponentActivity
extends androidx.core.app.ComponentActivity
implements k,
c0,
androidx.lifecycle.e,
j1.d,
q,
androidx.activity.result.c,
e0.b,
e0.c,
o,
p,
v,
n {
    public final a.a e = new a.a();
    public final w f = new w(new androidx.activity.d(this));
    public final l g = new l(this);
    public final j1.c h;
    public b0 i;
    public OnBackPressedDispatcher j;
    public final f k;
    public final m l;
    public int m;
    public final AtomicInteger n;
    public final ActivityResultRegistry o;
    public final CopyOnWriteArrayList p;
    public final CopyOnWriteArrayList q;
    public final CopyOnWriteArrayList r;
    public final CopyOnWriteArrayList s;
    public final CopyOnWriteArrayList t;
    public boolean u;
    public boolean v;

    public ComponentActivity() {
        f f3;
        j1.c c3;
        this.h = c3 = j1.c.a(this);
        this.j = null;
        this.k = f3 = this.F();
        this.l = new m(f3, new androidx.activity.e(this));
        this.n = new AtomicInteger();
        this.o = new ActivityResultRegistry(this){
            public final ComponentActivity h;
            {
                this.h = componentActivity;
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void f(int n3, b.a stringArray, Object stringArray2, c0.c object) {
                object = this.h;
                Object object2 = stringArray.b((Context)object, stringArray2);
                if (object2 != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable(this, n3, (a.a)object2){
                        public final int c;
                        public final a.a d;
                        public final a e;
                        {
                            this.e = a4;
                            this.c = n3;
                            this.d = a5;
                        }

                        @Override
                        public void run() {
                            this.e.c(this.c, this.d.a());
                        }
                    });
                    return;
                }
                if ((stringArray2 = stringArray.a((Context)object, stringArray2)).getExtras() != null && stringArray2.getExtras().getClassLoader() == null) {
                    stringArray2.setExtrasClassLoader(object.getClassLoader());
                }
                if (stringArray2.hasExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE")) {
                    stringArray = stringArray2.getBundleExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
                    stringArray2.removeExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
                } else {
                    stringArray = null;
                }
                if ("androidx.activity.result.contract.action.REQUEST_PERMISSIONS".equals(stringArray2.getAction())) {
                    stringArray = stringArray2 = stringArray2.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
                    if (stringArray2 == null) {
                        stringArray = new String[]{};
                    }
                    c0.b.m((Activity)object, stringArray, n3);
                    return;
                }
                if ("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST".equals(stringArray2.getAction())) {
                    block11: {
                        void var2_4;
                        int n4;
                        int n5;
                        IntentSender intentSender;
                        object2 = (IntentSenderRequest)stringArray2.getParcelableExtra("androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST");
                        try {
                            intentSender = ((IntentSenderRequest)object2).r();
                            stringArray2 = ((IntentSenderRequest)object2).o();
                            n5 = ((IntentSenderRequest)object2).p();
                            n4 = ((IntentSenderRequest)object2).q();
                        }
                        catch (IntentSender.SendIntentException sendIntentException) {}
                        try {
                            c0.b.o((Activity)object, intentSender, n3, (Intent)stringArray2, n5, n4, 0, (Bundle)stringArray);
                            break block11;
                        }
                        catch (IntentSender.SendIntentException sendIntentException) {}
                        {
                        }
                        new Handler(Looper.getMainLooper()).post(new Runnable(this, n3, (IntentSender.SendIntentException)var2_4){
                            public final int c;
                            public final IntentSender.SendIntentException d;
                            public final a e;
                            {
                                this.e = a4;
                                this.c = n3;
                                this.d = sendIntentException;
                            }

                            @Override
                            public void run() {
                                this.e.b(this.c, 0, new Intent().setAction("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST").putExtra("androidx.activity.result.contract.extra.SEND_INTENT_EXCEPTION", (Serializable)this.d));
                            }
                        });
                    }
                    return;
                }
                c0.b.n((Activity)object, (Intent)stringArray2, n3, (Bundle)stringArray);
            }
        };
        this.p = new CopyOnWriteArrayList();
        this.q = new CopyOnWriteArrayList();
        this.r = new CopyOnWriteArrayList();
        this.s = new CopyOnWriteArrayList();
        this.t = new CopyOnWriteArrayList();
        this.u = false;
        this.v = false;
        if (this.t() != null) {
            this.t().a(new i(this){
                public final ComponentActivity a;
                {
                    this.a = componentActivity;
                }

                @Override
                public void d(k k3, f.a a4) {
                    if (a4 == f.a.ON_STOP && (k3 = (k3 = this.a.getWindow()) != null ? k3.peekDecorView() : null) != null) {
                        androidx.activity.ComponentActivity$c.a((View)k3);
                    }
                }
            });
            this.t().a(new i(this){
                public final ComponentActivity a;
                {
                    this.a = componentActivity;
                }

                @Override
                public void d(k k3, f.a a4) {
                    if (a4 == f.a.ON_DESTROY) {
                        this.a.e.b();
                        if (!this.a.isChangingConfigurations()) {
                            this.a.r().a();
                        }
                        this.a.k.a();
                    }
                }
            });
            this.t().a(new i(this){
                public final ComponentActivity a;
                {
                    this.a = componentActivity;
                }

                @Override
                public void d(k k3, f.a a4) {
                    this.a.G();
                    this.a.t().c(this);
                }
            });
            c3.c();
            androidx.lifecycle.u.a(this);
            this.c().h("android:support:activity-result", new androidx.activity.f(this));
            this.D(new androidx.activity.g(this));
            return;
        }
        throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
    }

    public static /* synthetic */ j A(ComponentActivity componentActivity) {
        componentActivity.reportFullyDrawn();
        return null;
    }

    public static /* synthetic */ void y(ComponentActivity componentActivity, Context context) {
        context = componentActivity.c().b("android:support:activity-result");
        if (context != null) {
            componentActivity.o.g((Bundle)context);
        }
    }

    public static /* synthetic */ Bundle z(ComponentActivity componentActivity) {
        componentActivity.getClass();
        Bundle bundle = new Bundle();
        componentActivity.o.h(bundle);
        return bundle;
    }

    public final void D(b b3) {
        this.e.a(b3);
    }

    public final void E(n0.a a4) {
        this.r.add(a4);
    }

    public final f F() {
        return new g(this);
    }

    public void G() {
        if (this.i == null) {
            e e3 = (e)this.getLastNonConfigurationInstance();
            if (e3 != null) {
                this.i = e3.b;
            }
            if (this.i == null) {
                this.i = new b0();
            }
        }
    }

    public void H() {
        d0.a(this.getWindow().getDecorView(), this);
        e0.a(this.getWindow().getDecorView(), this);
        j1.e.a(this.getWindow().getDecorView(), this);
        androidx.activity.t.a(this.getWindow().getDecorView(), this);
        androidx.activity.s.a(this.getWindow().getDecorView(), this);
    }

    public void I() {
        this.invalidateOptionsMenu();
    }

    public Object J() {
        return null;
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.H();
        this.k.b(this.getWindow().getDecorView());
        super.addContentView(view, layoutParams);
    }

    @Override
    public final OnBackPressedDispatcher b() {
        if (this.j == null) {
            this.j = new OnBackPressedDispatcher(new Runnable(this){
                public final ComponentActivity c;
                {
                    this.c = componentActivity;
                }

                @Override
                public void run() {
                    IllegalStateException illegalStateException2;
                    block10: {
                        block9: {
                            block8: {
                                try {
                                    ComponentActivity.super.onBackPressed();
                                    return;
                                }
                                catch (NullPointerException nullPointerException) {
                                }
                                catch (IllegalStateException illegalStateException2) {
                                    break block8;
                                }
                                if (!TextUtils.equals((CharSequence)((Throwable)nullPointerException).getMessage(), (CharSequence)"Attempt to invoke virtual method 'android.os.Handler android.app.FragmentHostCallback.getHandler()' on a null object reference")) {
                                    throw nullPointerException;
                                }
                                break block9;
                            }
                            if (!TextUtils.equals((CharSequence)illegalStateException2.getMessage(), (CharSequence)"Can not perform this action after onSaveInstanceState")) break block10;
                        }
                        return;
                    }
                    throw illegalStateException2;
                }
            });
            this.t().a(new i(this){
                public final ComponentActivity a;
                {
                    this.a = componentActivity;
                }

                @Override
                public void d(k k3, f.a a4) {
                    if (a4 == f.a.ON_CREATE && Build.VERSION.SDK_INT >= 33) {
                        this.a.j.n(androidx.activity.ComponentActivity$d.a((ComponentActivity)k3));
                    }
                }
            });
        }
        return this.j;
    }

    @Override
    public final androidx.savedstate.a c() {
        return this.h.b();
    }

    @Override
    public final void e(n0.a a4) {
        this.q.add(a4);
    }

    @Override
    public void g(y y3) {
        this.f.a(y3);
    }

    @Override
    public final void h(n0.a a4) {
        this.q.remove(a4);
    }

    @Override
    public final void i(n0.a a4) {
        this.p.add(a4);
    }

    @Override
    public f1.a j() {
        f1.d d3 = new f1.d();
        if (this.getApplication() != null) {
            d3.b(z.a.e, this.getApplication());
        }
        d3.b(androidx.lifecycle.u.a, this);
        d3.b(androidx.lifecycle.u.b, this);
        if (this.getIntent() != null && this.getIntent().getExtras() != null) {
            d3.b(androidx.lifecycle.u.c, this.getIntent().getExtras());
        }
        return d3;
    }

    @Override
    public final void k(n0.a a4) {
        this.p.remove(a4);
    }

    @Override
    public final void l(n0.a a4) {
        this.t.remove(a4);
    }

    @Override
    public final ActivityResultRegistry m() {
        return this.o;
    }

    @Override
    public void n(y y3) {
        this.f.f(y3);
    }

    @Override
    public final void o(n0.a a4) {
        this.t.add(a4);
    }

    public void onActivityResult(int n3, int n4, Intent intent) {
        if (!this.o.b(n3, n4, intent)) {
            super.onActivityResult(n3, n4, intent);
        }
    }

    public void onBackPressed() {
        this.b().k();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Iterator iterator = this.p.iterator();
        while (iterator.hasNext()) {
            ((n0.a)iterator.next()).accept(configuration);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.h.d(bundle);
        this.e.c((Context)this);
        super.onCreate(bundle);
        ReportFragment.e(this);
        int n3 = this.m;
        if (n3 != 0) {
            this.setContentView(n3);
        }
    }

    public boolean onCreatePanelMenu(int n3, Menu menu) {
        if (n3 == 0) {
            super.onCreatePanelMenu(n3, menu);
            this.f.b(menu, this.getMenuInflater());
        }
        return true;
    }

    public boolean onMenuItemSelected(int n3, MenuItem menuItem) {
        if (super.onMenuItemSelected(n3, menuItem)) {
            return true;
        }
        if (n3 == 0) {
            return this.f.d(menuItem);
        }
        return false;
    }

    public void onMultiWindowModeChanged(boolean bl) {
        if (!this.u) {
            Iterator iterator = this.s.iterator();
            while (iterator.hasNext()) {
                ((n0.a)iterator.next()).accept(new c0.g(bl));
            }
        }
    }

    public void onMultiWindowModeChanged(boolean bl, Configuration configuration) {
        this.u = true;
        super.onMultiWindowModeChanged(bl, configuration);
        Iterator iterator = this.s.iterator();
        while (iterator.hasNext()) {
            ((n0.a)iterator.next()).accept(new c0.g(bl, configuration));
        }
        return;
        finally {
            this.u = false;
        }
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Iterator iterator = this.r.iterator();
        while (iterator.hasNext()) {
            ((n0.a)iterator.next()).accept(intent);
        }
    }

    public void onPanelClosed(int n3, Menu menu) {
        this.f.c(menu);
        super.onPanelClosed(n3, menu);
    }

    public void onPictureInPictureModeChanged(boolean bl) {
        if (!this.v) {
            Iterator iterator = this.t.iterator();
            while (iterator.hasNext()) {
                ((n0.a)iterator.next()).accept(new c0.q(bl));
            }
        }
    }

    public void onPictureInPictureModeChanged(boolean bl, Configuration configuration) {
        this.v = true;
        super.onPictureInPictureModeChanged(bl, configuration);
        Iterator iterator = this.t.iterator();
        while (iterator.hasNext()) {
            ((n0.a)iterator.next()).accept(new c0.q(bl, configuration));
        }
        return;
        finally {
            this.v = false;
        }
    }

    public boolean onPreparePanel(int n3, View view, Menu menu) {
        if (n3 == 0) {
            super.onPreparePanel(n3, view, menu);
            this.f.e(menu);
        }
        return true;
    }

    public void onRequestPermissionsResult(int n3, String[] stringArray, int[] nArray) {
        if (!this.o.b(n3, -1, new Intent().putExtra("androidx.activity.result.contract.extra.PERMISSIONS", stringArray).putExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS", nArray))) {
            super.onRequestPermissionsResult(n3, stringArray, nArray);
        }
    }

    public final Object onRetainNonConfigurationInstance() {
        Object object = this.J();
        Object object2 = this.i;
        b0 b02 = object2;
        if (object2 == null) {
            e e3 = (e)this.getLastNonConfigurationInstance();
            b02 = object2;
            if (e3 != null) {
                b02 = e3.b;
            }
        }
        if (b02 == null && object == null) {
            return null;
        }
        object2 = new e();
        ((e)object2).a = object;
        ((e)object2).b = b02;
        return object2;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        androidx.lifecycle.f f3 = this.t();
        if (f3 instanceof l) {
            ((l)f3).m(f.b.e);
        }
        super.onSaveInstanceState(bundle);
        this.h.e(bundle);
    }

    public void onTrimMemory(int n3) {
        super.onTrimMemory(n3);
        Iterator iterator = this.q.iterator();
        while (iterator.hasNext()) {
            ((n0.a)iterator.next()).accept(n3);
        }
    }

    @Override
    public b0 r() {
        if (this.getApplication() != null) {
            this.G();
            return this.i;
        }
        throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reportFullyDrawn() {
        Throwable throwable2;
        block3: {
            block2: {
                try {
                    if (!l1.b.d()) break block2;
                    l1.b.a("reportFullyDrawn() for ComponentActivity");
                }
                catch (Throwable throwable2) {
                    break block3;
                }
            }
            super.reportFullyDrawn();
            this.l.b();
            l1.b.b();
            return;
        }
        l1.b.b();
        throw throwable2;
    }

    @Override
    public final void s(n0.a a4) {
        this.s.remove(a4);
    }

    public void setContentView(int n3) {
        this.H();
        this.k.b(this.getWindow().getDecorView());
        super.setContentView(n3);
    }

    public void setContentView(View view) {
        this.H();
        this.k.b(this.getWindow().getDecorView());
        super.setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.H();
        this.k.b(this.getWindow().getDecorView());
        super.setContentView(view, layoutParams);
    }

    public void startActivityForResult(Intent intent, int n3) {
        super.startActivityForResult(intent, n3);
    }

    public void startActivityForResult(Intent intent, int n3, Bundle bundle) {
        super.startActivityForResult(intent, n3, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n3, Intent intent, int n4, int n5, int n6) {
        super.startIntentSenderForResult(intentSender, n3, intent, n4, n5, n6);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n3, Intent intent, int n4, int n5, int n6, Bundle bundle) {
        super.startIntentSenderForResult(intentSender, n3, intent, n4, n5, n6, bundle);
    }

    @Override
    public androidx.lifecycle.f t() {
        return this.g;
    }

    @Override
    public final void u(n0.a a4) {
        this.s.add(a4);
    }

    public static abstract class c {
        public static void a(View view) {
            view.cancelPendingInputEvents();
        }
    }

    public static abstract class d {
        public static OnBackInvokedDispatcher a(Activity activity) {
            return activity.getOnBackInvokedDispatcher();
        }
    }

    public static final class e {
        public Object a;
        public b0 b;
    }

    public static interface f
    extends Executor {
        public void a();

        public void b(View var1);
    }

    public class g
    implements f,
    ViewTreeObserver.OnDrawListener,
    Runnable {
        public final long c;
        public Runnable d;
        public boolean e;
        public final ComponentActivity f;

        public g(ComponentActivity componentActivity) {
            this.f = componentActivity;
            this.c = SystemClock.uptimeMillis() + 10000L;
            this.e = false;
        }

        public static /* synthetic */ void c(g g3) {
            Runnable runnable = g3.d;
            if (runnable != null) {
                runnable.run();
                g3.d = null;
            }
        }

        @Override
        public void a() {
            this.f.getWindow().getDecorView().removeCallbacks((Runnable)this);
            this.f.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener((ViewTreeObserver.OnDrawListener)this);
        }

        @Override
        public void b(View view) {
            if (!this.e) {
                this.e = true;
                view.getViewTreeObserver().addOnDrawListener((ViewTreeObserver.OnDrawListener)this);
            }
        }

        @Override
        public void execute(Runnable runnable) {
            this.d = runnable;
            runnable = this.f.getWindow().getDecorView();
            if (this.e) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    runnable.invalidate();
                    return;
                }
                runnable.postInvalidate();
                return;
            }
            runnable.postOnAnimation(new h(this));
        }

        public void onDraw() {
            Runnable runnable = this.d;
            if (runnable != null) {
                runnable.run();
                this.d = null;
                if (this.f.l.c()) {
                    this.e = false;
                    this.f.getWindow().getDecorView().post((Runnable)this);
                    return;
                }
            } else if (SystemClock.uptimeMillis() > this.c) {
                this.e = false;
                this.f.getWindow().getDecorView().post((Runnable)this);
            }
        }

        @Override
        public void run() {
            this.f.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener((ViewTreeObserver.OnDrawListener)this);
        }
    }
}

