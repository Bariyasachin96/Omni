/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 */
package androidx.fragment.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.q;
import androidx.activity.result.ActivityResultRegistry;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.c0;
import androidx.fragment.app.d;
import androidx.fragment.app.e;
import androidx.fragment.app.f;
import androidx.fragment.app.g;
import androidx.fragment.app.j;
import androidx.fragment.app.l;
import androidx.fragment.app.v;
import androidx.lifecycle.b0;
import androidx.lifecycle.f;
import c0.b;
import c0.o;
import c0.p;
import e0.b;
import e0.c;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import o0.y;

public class FragmentActivity
extends ComponentActivity
implements b.b {
    public boolean A = true;
    public final j w = androidx.fragment.app.j.b(new a(this));
    public final androidx.lifecycle.l x = new androidx.lifecycle.l(this);
    public boolean y;
    public boolean z;

    public FragmentActivity() {
        this.Q();
    }

    public static /* synthetic */ void K(FragmentActivity fragmentActivity, Context context) {
        fragmentActivity.w.a(null);
    }

    public static /* synthetic */ void L(FragmentActivity fragmentActivity, Configuration configuration) {
        fragmentActivity.w.m();
    }

    public static /* synthetic */ Bundle M(FragmentActivity fragmentActivity) {
        fragmentActivity.R();
        fragmentActivity.x.h(f.a.ON_STOP);
        return new Bundle();
    }

    public static /* synthetic */ void N(FragmentActivity fragmentActivity, Intent intent) {
        fragmentActivity.w.m();
    }

    public static boolean S(FragmentManager object, f.b b3) {
        Iterator iterator = ((FragmentManager)object).u0().iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            Fragment fragment = (Fragment)iterator.next();
            if (fragment == null) continue;
            boolean bl2 = bl;
            if (fragment.z() != null) {
                bl2 = bl | FragmentActivity.S(fragment.o(), b3);
            }
            object = fragment.V;
            bl = bl2;
            if (object != null) {
                bl = bl2;
                if (((c0)object).t().b().b(f.b.f)) {
                    fragment.V.h(b3);
                    bl = true;
                }
            }
            if (!fragment.U.b().b(f.b.f)) continue;
            fragment.U.m(b3);
            bl = true;
        }
        return bl;
    }

    public final View O(View view, String string, Context context, AttributeSet attributeSet) {
        return this.w.n(view, string, context, attributeSet);
    }

    public FragmentManager P() {
        return this.w.l();
    }

    public final void Q() {
        this.c().h("android:support:lifecycle", new d(this));
        this.i(new e(this));
        this.E(new f(this));
        this.D(new g(this));
    }

    public void R() {
        while (FragmentActivity.S(this.P(), f.b.e)) {
        }
    }

    public void T(Fragment fragment) {
    }

    public void U() {
        this.x.h(f.a.ON_RESUME);
        this.w.h();
    }

    @Override
    public final void a(int n3) {
    }

    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        super.dump(string, fileDescriptor, printWriter, stringArray);
        if (!this.w(stringArray)) {
            return;
        }
        printWriter.print(string);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append("  ");
        charSequence = charSequence.toString();
        printWriter.print((String)charSequence);
        printWriter.print("mCreated=");
        printWriter.print(this.y);
        printWriter.print(" mResumed=");
        printWriter.print(this.z);
        printWriter.print(" mStopped=");
        printWriter.print(this.A);
        if (this.getApplication() != null) {
            g1.a.b(this).a((String)charSequence, fileDescriptor, printWriter, stringArray);
        }
        this.w.l().W(string, fileDescriptor, printWriter, stringArray);
    }

    @Override
    public void onActivityResult(int n3, int n4, Intent intent) {
        this.w.m();
        super.onActivityResult(n3, n4, intent);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.x.h(f.a.ON_CREATE);
        this.w.e();
    }

    public View onCreateView(View view, String string, Context context, AttributeSet attributeSet) {
        View view2 = this.O(view, string, context, attributeSet);
        if (view2 == null) {
            return super.onCreateView(view, string, context, attributeSet);
        }
        return view2;
    }

    public View onCreateView(String string, Context context, AttributeSet attributeSet) {
        View view = this.O(null, string, context, attributeSet);
        if (view == null) {
            return super.onCreateView(string, context, attributeSet);
        }
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        this.w.f();
        this.x.h(f.a.ON_DESTROY);
    }

    @Override
    public boolean onMenuItemSelected(int n3, MenuItem menuItem) {
        if (super.onMenuItemSelected(n3, menuItem)) {
            return true;
        }
        if (n3 == 6) {
            return this.w.d(menuItem);
        }
        return false;
    }

    public void onPause() {
        super.onPause();
        this.z = false;
        this.w.g();
        this.x.h(f.a.ON_PAUSE);
    }

    public void onPostResume() {
        super.onPostResume();
        this.U();
    }

    @Override
    public void onRequestPermissionsResult(int n3, String[] stringArray, int[] nArray) {
        this.w.m();
        super.onRequestPermissionsResult(n3, stringArray, nArray);
    }

    public void onResume() {
        this.w.m();
        super.onResume();
        this.z = true;
        this.w.k();
    }

    public void onStart() {
        this.w.m();
        super.onStart();
        this.A = false;
        if (!this.y) {
            this.y = true;
            this.w.c();
        }
        this.w.k();
        this.x.h(f.a.ON_START);
        this.w.i();
    }

    public void onStateNotSaved() {
        this.w.m();
    }

    public void onStop() {
        super.onStop();
        this.A = true;
        this.R();
        this.w.j();
        this.x.h(f.a.ON_STOP);
    }

    public class a
    extends l
    implements b,
    c,
    o,
    p,
    androidx.lifecycle.c0,
    q,
    androidx.activity.result.c,
    j1.d,
    v,
    o0.v {
        public final FragmentActivity h;

        public a(FragmentActivity fragmentActivity) {
            this.h = fragmentActivity;
            super(fragmentActivity);
        }

        @Override
        public void A() {
            this.B();
        }

        public void B() {
            this.h.invalidateOptionsMenu();
        }

        public FragmentActivity C() {
            return this.h;
        }

        @Override
        public void a(FragmentManager fragmentManager, Fragment fragment) {
            this.h.T(fragment);
        }

        @Override
        public OnBackPressedDispatcher b() {
            return this.h.b();
        }

        @Override
        public androidx.savedstate.a c() {
            return this.h.c();
        }

        @Override
        public void e(n0.a a4) {
            this.h.e(a4);
        }

        @Override
        public View f(int n3) {
            return this.h.findViewById(n3);
        }

        @Override
        public void g(y y3) {
            this.h.g(y3);
        }

        @Override
        public void h(n0.a a4) {
            this.h.h(a4);
        }

        @Override
        public void i(n0.a a4) {
            this.h.i(a4);
        }

        @Override
        public boolean j() {
            Window window = this.h.getWindow();
            return window != null && window.peekDecorView() != null;
        }

        @Override
        public void k(n0.a a4) {
            this.h.k(a4);
        }

        @Override
        public void l(n0.a a4) {
            this.h.l(a4);
        }

        @Override
        public ActivityResultRegistry m() {
            return this.h.m();
        }

        @Override
        public void n(y y3) {
            this.h.n(y3);
        }

        @Override
        public void o(n0.a a4) {
            this.h.o(a4);
        }

        @Override
        public b0 r() {
            return this.h.r();
        }

        @Override
        public void s(n0.a a4) {
            this.h.s(a4);
        }

        @Override
        public androidx.lifecycle.f t() {
            return this.h.x;
        }

        @Override
        public void u(n0.a a4) {
            this.h.u(a4);
        }

        @Override
        public void w(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
            this.h.dump(string, fileDescriptor, printWriter, stringArray);
        }

        @Override
        public LayoutInflater y() {
            return this.h.getLayoutInflater().cloneInContext((Context)this.h);
        }
    }
}

