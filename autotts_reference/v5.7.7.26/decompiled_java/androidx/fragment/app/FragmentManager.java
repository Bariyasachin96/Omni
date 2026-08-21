/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 *  android.view.LayoutInflater$Factory2
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.IntentSenderRequest;
import androidx.fragment.app.BackStackRecordState;
import androidx.fragment.app.BackStackState;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManagerState;
import androidx.fragment.app.FragmentState;
import androidx.fragment.app.a;
import androidx.fragment.app.b;
import androidx.fragment.app.d0;
import androidx.fragment.app.e0;
import androidx.fragment.app.f0;
import androidx.fragment.app.i;
import androidx.fragment.app.n;
import androidx.fragment.app.o;
import androidx.fragment.app.p;
import androidx.fragment.app.q;
import androidx.fragment.app.r;
import androidx.fragment.app.s;
import androidx.fragment.app.u;
import androidx.fragment.app.v;
import androidx.fragment.app.w;
import androidx.fragment.app.x;
import androidx.fragment.app.y;
import androidx.lifecycle.b0;
import androidx.lifecycle.c0;
import androidx.lifecycle.f;
import b.e;
import b1.c;
import c0.g;
import j1.d;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FragmentManager {
    public static boolean S = false;
    public androidx.fragment.app.k A;
    public f0 B = null;
    public f0 C;
    public androidx.activity.result.b D;
    public androidx.activity.result.b E;
    public androidx.activity.result.b F;
    public ArrayDeque G;
    public boolean H;
    public boolean I;
    public boolean J;
    public boolean K;
    public boolean L;
    public ArrayList M;
    public ArrayList N;
    public ArrayList O;
    public u P;
    public c.c Q;
    public Runnable R;
    public final ArrayList a = new ArrayList();
    public boolean b;
    public final x c = new x();
    public ArrayList d;
    public ArrayList e;
    public final androidx.fragment.app.m f = new androidx.fragment.app.m(this);
    public OnBackPressedDispatcher g;
    public final androidx.activity.o h = new androidx.activity.o(this, false){
        public final FragmentManager d;
        {
            this.d = fragmentManager;
            super(bl);
        }

        @Override
        public void d() {
            this.d.E0();
        }
    };
    public final AtomicInteger i = new AtomicInteger();
    public final Map j = Collections.synchronizedMap(new HashMap());
    public final Map k = Collections.synchronizedMap(new HashMap());
    public final Map l = Collections.synchronizedMap(new HashMap());
    public ArrayList m;
    public final n n = new n(this);
    public final CopyOnWriteArrayList o = new CopyOnWriteArrayList();
    public final n0.a p = new o(this);
    public final n0.a q = new p(this);
    public final n0.a r = new q(this);
    public final n0.a s = new r(this);
    public final o0.y t = new o0.y(this){
        public final FragmentManager a;
        {
            this.a = fragmentManager;
        }

        @Override
        public boolean a(MenuItem menuItem) {
            return this.a.J(menuItem);
        }

        @Override
        public void b(Menu menu) {
            this.a.K(menu);
        }

        @Override
        public void c(Menu menu, MenuInflater menuInflater) {
            this.a.C(menu, menuInflater);
        }

        @Override
        public void d(Menu menu) {
            this.a.O(menu);
        }
    };
    public int u = -1;
    public androidx.fragment.app.l v;
    public i w;
    public Fragment x;
    public Fragment y;
    public androidx.fragment.app.k z = null;

    public FragmentManager() {
        this.A = new androidx.fragment.app.k(this){
            public final FragmentManager b;
            {
                this.b = fragmentManager;
            }

            @Override
            public Fragment a(ClassLoader classLoader, String string) {
                return this.b.v0().d(this.b.v0().q(), string, null);
            }
        };
        this.C = new f0(this){
            public final FragmentManager a;
            {
                this.a = fragmentManager;
            }

            @Override
            public e0 a(ViewGroup viewGroup) {
                return new b(viewGroup);
            }
        };
        this.G = new ArrayDeque();
        this.R = new Runnable(this){
            public final FragmentManager c;
            {
                this.c = fragmentManager;
            }

            @Override
            public void run() {
                this.c.a0(true);
            }
        };
    }

    public static Fragment C0(View object) {
        if ((object = object.getTag(a1.b.fragment_container_view_tag)) instanceof Fragment) {
            return (Fragment)object;
        }
        return null;
    }

    public static boolean I0(int n3) {
        return S || Log.isLoggable((String)"FragmentManager", (int)n3);
        {
        }
    }

    public static /* synthetic */ void a(FragmentManager fragmentManager, Integer n3) {
        if (fragmentManager.K0() && n3 == 80) {
            fragmentManager.F(false);
        }
    }

    public static /* synthetic */ Bundle b(FragmentManager fragmentManager) {
        return fragmentManager.g1();
    }

    public static /* synthetic */ void c(FragmentManager fragmentManager, c0.q q3) {
        if (fragmentManager.K0()) {
            fragmentManager.N(q3.a(), false);
        }
    }

    public static void c0(ArrayList arrayList, ArrayList arrayList2, int n3, int n4) {
        while (n3 < n4) {
            a a4 = (a)arrayList.get(n3);
            if (((Boolean)arrayList2.get(n3)).booleanValue()) {
                a4.r(-1);
                a4.w();
            } else {
                a4.r(1);
                a4.v();
            }
            ++n3;
        }
    }

    public static /* synthetic */ void d(FragmentManager fragmentManager, g g3) {
        if (fragmentManager.K0()) {
            fragmentManager.G(g3.a(), false);
        }
    }

    public static /* synthetic */ void e(FragmentManager fragmentManager, Configuration configuration) {
        if (fragmentManager.K0()) {
            fragmentManager.z(configuration, false);
        }
    }

    public static /* synthetic */ Map f(FragmentManager fragmentManager) {
        return fragmentManager.k;
    }

    public static int f1(int n3) {
        if (n3 != 4097) {
            if (n3 != 8194) {
                if (n3 != 8197) {
                    if (n3 != 4099) {
                        if (n3 != 4100) {
                            return 0;
                        }
                        return 8197;
                    }
                    return 4099;
                }
                return 4100;
            }
            return 4097;
        }
        return 8194;
    }

    public static /* synthetic */ Map g(FragmentManager fragmentManager) {
        return fragmentManager.l;
    }

    public static FragmentManager k0(View view) {
        Object object;
        block5: {
            Fragment fragment = FragmentManager.l0(view);
            if (fragment != null) {
                if (fragment.X()) {
                    return fragment.o();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The Fragment ");
                stringBuilder.append(fragment);
                stringBuilder.append(" that owns View ");
                stringBuilder.append(view);
                stringBuilder.append(" has already been destroyed. Nested fragments should always use the child FragmentManager.");
                throw new IllegalStateException(stringBuilder.toString());
            }
            object = view.getContext();
            while (object instanceof ContextWrapper) {
                if (object instanceof FragmentActivity) {
                    object = (FragmentActivity)object;
                    break block5;
                }
                object = ((ContextWrapper)object).getBaseContext();
            }
            object = null;
        }
        if (object != null) {
            return ((FragmentActivity)object).P();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("View ");
        ((StringBuilder)object).append(view);
        ((StringBuilder)object).append(" is not within a subclass of FragmentActivity.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public static Fragment l0(View view) {
        while (view != null) {
            Fragment fragment = FragmentManager.C0(view);
            if (fragment != null) {
                return fragment;
            }
            if ((view = view.getParent()) instanceof View) continue;
            view = null;
        }
        return null;
    }

    public boolean A(MenuItem menuItem) {
        if (this.u < 1) {
            return false;
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null || !fragment.O0(menuItem)) continue;
            return true;
        }
        return false;
    }

    public f0 A0() {
        Object object = this.B;
        if (object != null) {
            return object;
        }
        object = this.x;
        if (object != null) {
            return ((Fragment)object).v.A0();
        }
        return this.C;
    }

    public void B() {
        this.I = false;
        this.J = false;
        this.P.p(false);
        this.S(1);
    }

    public c.c B0() {
        return this.Q;
    }

    public boolean C(Menu object, MenuInflater menuInflater) {
        int n3 = this.u;
        int n4 = 0;
        if (n3 < 1) {
            return false;
        }
        Iterator iterator = this.c.o().iterator();
        ArrayList<Fragment> arrayList = null;
        boolean bl = false;
        while (iterator.hasNext()) {
            Fragment fragment = (Fragment)iterator.next();
            if (fragment == null || !this.M0(fragment) || !fragment.Q0((Menu)object, menuInflater)) continue;
            ArrayList<Fragment> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<Fragment>();
            }
            arrayList2.add(fragment);
            bl = true;
            arrayList = arrayList2;
        }
        if (this.e != null) {
            while (n4 < this.e.size()) {
                object = (Fragment)this.e.get(n4);
                if (arrayList == null || !arrayList.contains(object)) {
                    ((Fragment)object).q0();
                }
                ++n4;
            }
        }
        this.e = arrayList;
        return bl;
    }

    public void D() {
        this.K = true;
        this.a0(true);
        this.X();
        this.s();
        this.S(-1);
        Object object = this.v;
        if (object instanceof e0.c) {
            ((e0.c)object).h(this.q);
        }
        if ((object = this.v) instanceof e0.b) {
            ((e0.b)object).k(this.p);
        }
        if ((object = this.v) instanceof c0.o) {
            ((c0.o)object).s(this.r);
        }
        if ((object = this.v) instanceof c0.p) {
            ((c0.p)object).l(this.s);
        }
        if ((object = this.v) instanceof o0.v) {
            ((o0.v)object).n(this.t);
        }
        this.v = null;
        this.w = null;
        this.x = null;
        if (this.g != null) {
            this.h.h();
            this.g = null;
        }
        if ((object = this.D) != null) {
            ((androidx.activity.result.b)object).c();
            this.E.c();
            this.F.c();
        }
    }

    public b0 D0(Fragment fragment) {
        return this.P.m(fragment);
    }

    public void E() {
        this.S(1);
    }

    public void E0() {
        this.a0(true);
        if (this.h.g()) {
            this.V0();
            return;
        }
        this.g.k();
    }

    public void F(boolean bl) {
        if (bl && this.v instanceof e0.c) {
            this.p1(new IllegalStateException("Do not call dispatchLowMemory() on host. Host implements OnTrimMemoryProvider and automatically dispatches low memory callbacks to fragments."));
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null) continue;
            fragment.W0();
            if (!bl) continue;
            fragment.x.F(true);
        }
    }

    public void F0(Fragment fragment) {
        if (FragmentManager.I0(2)) {
            Objects.toString(fragment);
        }
        if (!fragment.C) {
            fragment.C = true;
            fragment.P = true ^ fragment.P;
            this.m1(fragment);
        }
    }

    public void G(boolean bl, boolean bl2) {
        if (bl2 && this.v instanceof c0.o) {
            this.p1(new IllegalStateException("Do not call dispatchMultiWindowModeChanged() on host. Host implements OnMultiWindowModeChangedProvider and automatically dispatches multi-window mode changes to fragments."));
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null) continue;
            fragment.X0(bl);
            if (!bl2) continue;
            fragment.x.G(bl, true);
        }
    }

    public void G0(Fragment fragment) {
        if (fragment.n && this.J0(fragment)) {
            this.H = true;
        }
    }

    public void H(Fragment fragment) {
        Iterator iterator = this.o.iterator();
        while (iterator.hasNext()) {
            ((v)iterator.next()).a(this, fragment);
        }
    }

    public boolean H0() {
        return this.K;
    }

    public void I() {
        for (Fragment fragment : this.c.l()) {
            if (fragment == null) continue;
            fragment.u0(fragment.Y());
            fragment.x.I();
        }
    }

    public boolean J(MenuItem menuItem) {
        if (this.u < 1) {
            return false;
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null || !fragment.Y0(menuItem)) continue;
            return true;
        }
        return false;
    }

    public final boolean J0(Fragment fragment) {
        return fragment.G && fragment.H || fragment.x.p();
    }

    public void K(Menu menu) {
        if (this.u >= 1) {
            for (Fragment fragment : this.c.o()) {
                if (fragment == null) continue;
                fragment.Z0(menu);
            }
        }
    }

    public final boolean K0() {
        Fragment fragment = this.x;
        if (fragment == null) {
            return true;
        }
        return fragment.X() && this.x.E().K0();
    }

    public final void L(Fragment fragment) {
        if (fragment != null && fragment.equals(this.f0(fragment.h))) {
            fragment.d1();
        }
    }

    public boolean L0(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        return fragment.Y();
    }

    public void M() {
        this.S(5);
    }

    public boolean M0(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.a0();
    }

    public void N(boolean bl, boolean bl2) {
        if (bl2 && this.v instanceof c0.p) {
            this.p1(new IllegalStateException("Do not call dispatchPictureInPictureModeChanged() on host. Host implements OnPictureInPictureModeChangedProvider and automatically dispatches picture-in-picture mode changes to fragments."));
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null) continue;
            fragment.b1(bl);
            if (!bl2) continue;
            fragment.x.N(bl, true);
        }
    }

    public boolean N0(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.v;
        return fragment.equals(fragmentManager.z0()) && this.N0(fragmentManager.x);
    }

    public boolean O(Menu menu) {
        int n3 = this.u;
        boolean bl = false;
        if (n3 < 1) {
            return false;
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null || !this.M0(fragment) || !fragment.c1(menu)) continue;
            bl = true;
        }
        return bl;
    }

    public boolean O0(int n3) {
        return this.u >= n3;
    }

    public void P() {
        this.r1();
        this.L(this.y);
    }

    public boolean P0() {
        return this.I || this.J;
        {
        }
    }

    public void Q() {
        this.I = false;
        this.J = false;
        this.P.p(false);
        this.S(7);
    }

    public void Q0(int n3, boolean bl) {
        if (this.v == null && n3 != -1) {
            throw new IllegalStateException("No activity");
        }
        if (bl || n3 != this.u) {
            androidx.fragment.app.l l3;
            this.u = n3;
            this.c.t();
            this.o1();
            if (this.H && (l3 = this.v) != null && this.u == 7) {
                l3.A();
                this.H = false;
            }
        }
    }

    public void R() {
        this.I = false;
        this.J = false;
        this.P.p(false);
        this.S(5);
    }

    public void R0() {
        if (this.v != null) {
            this.I = false;
            this.J = false;
            this.P.p(false);
            for (Fragment fragment : this.c.o()) {
                if (fragment == null) continue;
                fragment.d0();
            }
        }
    }

    public final void S(int n3) {
        Throwable throwable2;
        block3: {
            try {
                this.b = true;
                this.c.d(n3);
                this.Q0(n3, false);
                Iterator iterator = this.t().iterator();
                while (iterator.hasNext()) {
                    ((e0)iterator.next()).j();
                }
            }
            catch (Throwable throwable2) {
                break block3;
            }
            this.b = false;
            this.a0(true);
            return;
        }
        this.b = false;
        throw throwable2;
    }

    public void S0(FragmentContainerView fragmentContainerView) {
        for (w w3 : this.c.k()) {
            View view;
            Fragment fragment = w3.k();
            if (fragment.A != fragmentContainerView.getId() || (view = fragment.K) == null || view.getParent() != null) continue;
            fragment.J = fragmentContainerView;
            w3.b();
        }
    }

    public void T() {
        this.J = true;
        this.P.p(true);
        this.S(4);
    }

    public void T0(w w3) {
        Fragment fragment = w3.k();
        if (fragment.L) {
            if (this.b) {
                this.L = true;
                return;
            }
            fragment.L = false;
            w3.m();
        }
    }

    public void U() {
        this.S(2);
    }

    public void U0(int n3, int n4, boolean bl) {
        if (n3 >= 0) {
            this.Y(new m(this, null, n3, n4), bl);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void V() {
        if (this.L) {
            this.L = false;
            this.o1();
        }
    }

    public boolean V0() {
        return this.X0(null, -1, 0);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void W(String string, FileDescriptor object, PrintWriter printWriter, String[] object2) {
        int n3;
        int n4;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        this.c.e(string, (FileDescriptor)object, printWriter, (String[])object2);
        object = this.e;
        int n5 = 0;
        if (object != null && (n4 = ((ArrayList)object).size()) > 0) {
            printWriter.print(string);
            printWriter.println("Fragments Created Menus:");
            for (n3 = 0; n3 < n4; ++n3) {
                object = (Fragment)this.e.get(n3);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n3);
                printWriter.print(": ");
                printWriter.println(((Fragment)object).toString());
            }
        }
        if ((object = this.d) != null && (n4 = ((ArrayList)object).size()) > 0) {
            printWriter.print(string);
            printWriter.println("Back Stack:");
            for (n3 = 0; n3 < n4; ++n3) {
                object = (a)this.d.get(n3);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(n3);
                printWriter.print(": ");
                printWriter.println(((a)object).toString());
                ((a)object).t((String)charSequence, printWriter);
            }
        }
        printWriter.print(string);
        object = new StringBuilder();
        ((StringBuilder)object).append("Back Stack Index: ");
        ((StringBuilder)object).append(this.i.get());
        printWriter.println(((StringBuilder)object).toString());
        object = this.a;
        synchronized (object) {
            try {
                n4 = this.a.size();
                if (n4 > 0) {
                    printWriter.print(string);
                    printWriter.println("Pending Actions:");
                    for (n3 = n5; n3 < n4; ++n3) {
                        object2 = (l)this.a.get(n3);
                        printWriter.print(string);
                        printWriter.print("  #");
                        printWriter.print(n3);
                        printWriter.print(": ");
                        printWriter.println(object2);
                    }
                }
                // MONITOREXIT @DISABLED, blocks:[0, 3] lbl55 : MonitorExitStatement: MONITOREXIT : var2_3
                printWriter.print(string);
                printWriter.println("FragmentManager misc state:");
                printWriter.print(string);
                printWriter.print("  mHost=");
                printWriter.println(this.v);
                printWriter.print(string);
                printWriter.print("  mContainer=");
                printWriter.println(this.w);
                if (this.x != null) {
                    printWriter.print(string);
                    printWriter.print("  mParent=");
                    printWriter.println(this.x);
                }
                printWriter.print(string);
                printWriter.print("  mCurState=");
                printWriter.print(this.u);
                printWriter.print(" mStateSaved=");
                printWriter.print(this.I);
                printWriter.print(" mStopped=");
                printWriter.print(this.J);
                printWriter.print(" mDestroyed=");
                printWriter.println(this.K);
                if (this.H) {
                    printWriter.print(string);
                    printWriter.print("  mNeedMenuInvalidate=");
                    printWriter.println(this.H);
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public boolean W0(int n3, int n4) {
        if (n3 >= 0) {
            return this.X0(null, n3, n4);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad id: ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void X() {
        Iterator iterator = this.t().iterator();
        while (iterator.hasNext()) {
            ((e0)iterator.next()).j();
        }
    }

    public final boolean X0(String string, int n3, int n4) {
        this.a0(false);
        this.Z(true);
        Fragment fragment = this.y;
        if (fragment != null && n3 < 0 && string == null && fragment.o().V0()) {
            return true;
        }
        boolean bl = this.Y0(this.M, this.N, string, n3, n4);
        if (bl) {
            this.b = true;
            try {
                this.c1(this.M, this.N);
            }
            finally {
                this.r();
            }
        }
        this.r1();
        this.V();
        this.c.b();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void Y(l object, boolean bl) {
        if (!bl) {
            if (this.v == null) {
                if (this.K) {
                    throw new IllegalStateException("FragmentManager has been destroyed");
                }
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            this.q();
        }
        ArrayList arrayList = this.a;
        synchronized (arrayList) {
            Throwable throwable2;
            block8: {
                try {
                    if (this.v != null) {
                        this.a.add(object);
                        this.i1();
                        return;
                    }
                    if (bl) {
                        return;
                    }
                }
                catch (Throwable throwable2) {
                    break block8;
                }
                object = new IllegalStateException("Activity has been destroyed");
                throw object;
            }
            throw throwable2;
        }
    }

    public boolean Y0(ArrayList arrayList, ArrayList arrayList2, String string, int n3, int n4) {
        boolean bl = (n4 & 1) != 0;
        if ((n4 = this.g0(string, n3, bl)) < 0) {
            return false;
        }
        for (n3 = this.d.size() - 1; n3 >= n4; --n3) {
            arrayList.add((a)this.d.remove(n3));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    public final void Z(boolean bl) {
        if (!this.b) {
            if (this.v == null) {
                if (this.K) {
                    throw new IllegalStateException("FragmentManager has been destroyed");
                }
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            if (Looper.myLooper() == this.v.v().getLooper()) {
                if (!bl) {
                    this.q();
                }
                if (this.M == null) {
                    this.M = new ArrayList();
                    this.N = new ArrayList();
                }
                return;
            }
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        throw new IllegalStateException("FragmentManager is already executing transactions");
    }

    public void Z0(Bundle bundle, String string, Fragment fragment) {
        if (fragment.v != this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.p1(new IllegalStateException(stringBuilder.toString()));
        }
        bundle.putString(string, fragment.h);
    }

    public boolean a0(boolean bl) {
        this.Z(bl);
        bl = false;
        while (this.n0(this.M, this.N)) {
            bl = true;
            this.b = true;
            try {
                this.c1(this.M, this.N);
            }
            finally {
                this.r();
            }
        }
        this.r1();
        this.V();
        this.c.b();
        return bl;
    }

    public void a1(k k3, boolean bl) {
        this.n.o(k3, bl);
    }

    public void b0(l l3, boolean bl) {
        if (bl && (this.v == null || this.K)) {
            return;
        }
        this.Z(bl);
        if (l3.a(this.M, this.N)) {
            this.b = true;
            try {
                this.c1(this.M, this.N);
            }
            finally {
                this.r();
            }
        }
        this.r1();
        this.V();
        this.c.b();
    }

    public void b1(Fragment fragment) {
        if (FragmentManager.I0(2)) {
            Objects.toString(fragment);
            int n3 = fragment.u;
        }
        boolean bl = fragment.Z();
        if (fragment.D && bl) {
            return;
        }
        this.c.u(fragment);
        if (this.J0(fragment)) {
            this.H = true;
        }
        fragment.o = true;
        this.m1(fragment);
    }

    public final void c1(ArrayList arrayList, ArrayList arrayList2) {
        block10: {
            block9: {
                if (arrayList.isEmpty()) break block9;
                if (arrayList.size() != arrayList2.size()) break block10;
                int n3 = arrayList.size();
                int n4 = 0;
                int n5 = 0;
                while (n4 < n3) {
                    int n6 = n4;
                    int n7 = n5;
                    if (!((a)arrayList.get((int)n4)).r) {
                        if (n5 != n4) {
                            this.d0(arrayList, arrayList2, n5, n4);
                        }
                        n7 = n5 = n4 + 1;
                        if (((Boolean)arrayList2.get(n4)).booleanValue()) {
                            while (true) {
                                n7 = n5;
                                if (n5 >= n3) break;
                                n7 = n5;
                                if (!((Boolean)arrayList2.get(n5)).booleanValue()) break;
                                n7 = n5;
                                if (((a)arrayList.get((int)n5)).r) break;
                                ++n5;
                            }
                        }
                        this.d0(arrayList, arrayList2, n4, n7);
                        n6 = n7 - 1;
                    }
                    n4 = n6 + 1;
                    n5 = n7;
                }
                if (n5 != n3) {
                    this.d0(arrayList, arrayList2, n5, n3);
                }
            }
            return;
        }
        throw new IllegalStateException("Internal error with the back stack records");
    }

    public final void d0(ArrayList arrayList, ArrayList arrayList2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        Object object;
        int n8;
        boolean bl = ((a)arrayList.get((int)n3)).r;
        Object object2 = this.O;
        if (object2 == null) {
            this.O = new ArrayList();
        } else {
            ((ArrayList)object2).clear();
        }
        this.O.addAll(this.c.o());
        object2 = this.z0();
        boolean bl2 = false;
        for (n8 = n3; n8 < n4; ++n8) {
            object = (a)arrayList.get(n8);
            object2 = (Boolean)arrayList2.get(n8) == false ? ((a)object).x(this.O, (Fragment)object2) : ((a)object).A(this.O, (Fragment)object2);
            if (!bl2 && !((y)object).i) {
                bl2 = false;
                continue;
            }
            bl2 = true;
        }
        this.O.clear();
        if (!bl && this.u >= 1) {
            for (n8 = n3; n8 < n4; ++n8) {
                object2 = ((a)arrayList.get((int)n8)).c;
                n7 = ((ArrayList)object2).size();
                n6 = 0;
                while (n6 < n7) {
                    object = ((ArrayList)object2).get(n6);
                    n5 = n6 + 1;
                    object = ((y.a)object).b;
                    n6 = n5;
                    if (object == null) continue;
                    n6 = n5;
                    if (((Fragment)object).v == null) continue;
                    object = this.v((Fragment)object);
                    this.c.r((w)object);
                    n6 = n5;
                }
            }
        }
        FragmentManager.c0(arrayList, arrayList2, n3, n4);
        bl = (Boolean)arrayList2.get(n4 - 1);
        for (n8 = n3; n8 < n4; ++n8) {
            object = (a)arrayList.get(n8);
            if (bl) {
                for (n6 = ((y)object).c.size() - 1; n6 >= 0; --n6) {
                    object2 = ((y.a)((y)object).c.get((int)n6)).b;
                    if (object2 == null) continue;
                    this.v((Fragment)object2).m();
                }
                continue;
            }
            object2 = ((y)object).c;
            n7 = ((ArrayList)object2).size();
            n6 = 0;
            while (n6 < n7) {
                object = ((ArrayList)object2).get(n6);
                n5 = n6 + 1;
                object = ((y.a)object).b;
                n6 = n5;
                if (object == null) continue;
                this.v((Fragment)object).m();
                n6 = n5;
            }
        }
        this.Q0(this.u, true);
        object2 = this.u(arrayList, n3, n4).iterator();
        while (true) {
            if (!object2.hasNext()) break;
            object = (e0)object2.next();
            ((e0)object).r(bl);
            ((e0)object).p();
            ((e0)object).g();
        }
        for (n8 = n3; n8 < n4; ++n8) {
            object2 = (a)arrayList.get(n8);
            if (((Boolean)arrayList2.get(n8)).booleanValue() && ((a)object2).v >= 0) {
                ((a)object2).v = -1;
            }
            ((a)object2).z();
        }
        if (bl2) {
            this.d1();
        }
    }

    public final void d1() {
        ArrayList arrayList = this.m;
        if (arrayList != null && arrayList.size() > 0) {
            androidx.appcompat.app.s.a(this.m.get(0));
            throw null;
        }
    }

    public boolean e0() {
        boolean bl = this.a0(true);
        this.m0();
        return bl;
    }

    public void e1(Parcelable object5) {
        int n3;
        Object object2;
        Object object3;
        block20: {
            block19: {
                if (object5 == null) break block19;
                object5 = (Bundle)object5;
                for (Object object4 : object5.keySet()) {
                    if (!((String)object4).startsWith("result_") || (object3 = object5.getBundle((String)object4)) == null) continue;
                    object3.setClassLoader(this.v.q().getClassLoader());
                    object4 = ((String)object4).substring(7);
                    this.k.put(object4, object3);
                }
                object2 = new ArrayList();
                for (Object object4 : object5.keySet()) {
                    if (!((String)object4).startsWith("fragment_") || (object4 = object5.getBundle((String)object4)) == null) continue;
                    object4.setClassLoader(this.v.q().getClassLoader());
                    ((ArrayList)object2).add((FragmentState)object4.getParcelable("state"));
                }
                this.c.x((ArrayList)object2);
                object3 = (FragmentManagerState)object5.getParcelable("state");
                if (object3 != null) break block20;
            }
            return;
        }
        this.c.v();
        object2 = object3.c;
        int n4 = ((ArrayList)object2).size();
        int n5 = 0;
        int n6 = 0;
        while (n6 < n4) {
            Object object4;
            object5 = ((ArrayList)object2).get(n6);
            n3 = n6 + 1;
            object5 = (String)object5;
            object5 = this.c.B((String)object5, null);
            n6 = n3;
            if (object5 == null) continue;
            object4 = this.P.i(((FragmentState)object5).d);
            if (object4 != null) {
                if (FragmentManager.I0(2)) {
                    object4.toString();
                }
                object5 = new w(this.n, this.c, (Fragment)object4, (FragmentState)object5);
            } else {
                object5 = new w(this.n, this.c, this.v.q().getClassLoader(), this.t0(), (FragmentState)object5);
            }
            object4 = ((w)object5).k();
            ((Fragment)object4).v = this;
            if (FragmentManager.I0(2)) {
                object4.toString();
            }
            ((w)object5).o(this.v.q().getClassLoader());
            this.c.r((w)object5);
            ((w)object5).u(this.u);
            n6 = n3;
        }
        for (Object object5 : this.P.l()) {
            if (this.c.c(((Fragment)object5).h)) continue;
            if (FragmentManager.I0(2)) {
                object5.toString();
                Objects.toString(object3.c);
            }
            this.P.o((Fragment)object5);
            ((Fragment)object5).v = this;
            object2 = new w(this.n, this.c, (Fragment)object5);
            ((w)object2).u(1);
            ((w)object2).m();
            ((Fragment)object5).o = true;
            ((w)object2).m();
        }
        this.c.w(object3.d);
        if (object3.e != null) {
            this.d = new ArrayList(object3.e.length);
            for (n6 = 0; n6 < ((Object)(object5 = object3.e)).length; ++n6) {
                object5 = ((BackStackRecordState)object5[n6]).p(this);
                if (FragmentManager.I0(2)) {
                    n3 = ((a)object5).v;
                    object5.toString();
                    object2 = new PrintWriter(new d0("FragmentManager"));
                    ((a)object5).u("  ", (PrintWriter)object2, false);
                    ((PrintWriter)object2).close();
                }
                this.d.add(object5);
            }
        } else {
            this.d = null;
        }
        this.i.set(object3.f);
        object5 = object3.g;
        if (object5 != null) {
            this.y = object5 = this.f0((String)object5);
            this.L((Fragment)object5);
        }
        if ((object5 = object3.h) != null) {
            for (n6 = n5; n6 < ((ArrayList)object5).size(); ++n6) {
                this.j.put((String)((ArrayList)object5).get(n6), (BackStackState)object3.i.get(n6));
            }
        }
        this.G = new ArrayDeque(object3.j);
    }

    public Fragment f0(String string) {
        return this.c.f(string);
    }

    public final int g0(String string, int n3, boolean bl) {
        Object object = this.d;
        if (object != null && !((ArrayList)object).isEmpty()) {
            int n4;
            if (string == null && n3 < 0) {
                if (bl) {
                    return 0;
                }
                return this.d.size() - 1;
            }
            for (n4 = this.d.size() - 1; n4 >= 0; --n4) {
                object = (a)this.d.get(n4);
                if (string != null && string.equals(((a)object).y()) || n3 >= 0 && n3 == ((a)object).v) break;
            }
            if (n4 < 0) {
                return n4;
            }
            if (bl) {
                while (n4 > 0) {
                    object = (a)this.d.get(n4 - 1);
                    if ((string == null || !string.equals(((a)object).y())) && (n3 < 0 || n3 != ((a)object).v)) break;
                    --n4;
                }
                return n4;
            }
            if (n4 == this.d.size() - 1) {
                return -1;
            }
            return n4 + 1;
        }
        return -1;
    }

    public Bundle g1() {
        int n3;
        Object object;
        int n4;
        Bundle bundle = new Bundle();
        this.m0();
        this.X();
        this.a0(true);
        this.I = true;
        this.P.p(true);
        Object object22 = this.c.y();
        ArrayList arrayList = this.c.m();
        if (arrayList.isEmpty()) {
            FragmentManager.I0(2);
            return bundle;
        }
        ArrayList arrayList2 = this.c.z();
        Object object3 = this.d;
        int n5 = 0;
        if (object3 != null && (n4 = ((ArrayList)object3).size()) > 0) {
            object = new BackStackRecordState[n4];
            n3 = 0;
            while (true) {
                object3 = object;
                if (n3 < n4) {
                    object[n3] = new BackStackRecordState((a)this.d.get(n3));
                    if (FragmentManager.I0(2)) {
                        Objects.toString(this.d.get(n3));
                    }
                    ++n3;
                    continue;
                }
                break;
            }
        } else {
            object3 = null;
        }
        object = new FragmentManagerState();
        ((FragmentManagerState)object).c = object22;
        ((FragmentManagerState)object).d = arrayList2;
        ((FragmentManagerState)object).e = object3;
        ((FragmentManagerState)object).f = this.i.get();
        object3 = this.y;
        if (object3 != null) {
            ((FragmentManagerState)object).g = ((Fragment)object3).h;
        }
        ((FragmentManagerState)object).h.addAll(this.j.keySet());
        ((FragmentManagerState)object).i.addAll(this.j.values());
        ((FragmentManagerState)object).j = new ArrayList(this.G);
        bundle.putParcelable("state", (Parcelable)object);
        for (Object object22 : this.k.keySet()) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("result_");
            ((StringBuilder)object3).append((String)object22);
            bundle.putBundle(((StringBuilder)object3).toString(), (Bundle)this.k.get(object22));
        }
        n4 = arrayList.size();
        for (n3 = n5; n3 < n4; ++n3) {
            object3 = arrayList.get(n3);
            object = (FragmentState)object3;
            object3 = new Bundle();
            object3.putParcelable("state", (Parcelable)object);
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("fragment_");
            ((StringBuilder)object22).append(((FragmentState)object).d);
            bundle.putBundle(((StringBuilder)object22).toString(), (Bundle)object3);
        }
        return bundle;
    }

    public Fragment h0(int n3) {
        return this.c.g(n3);
    }

    public Fragment.SavedState h1(Fragment fragment) {
        w w3 = this.c.n(fragment.h);
        if (w3 == null || !w3.k().equals(fragment)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not currently in the FragmentManager");
            this.p1(new IllegalStateException(stringBuilder.toString()));
        }
        return w3.r();
    }

    public void i(a a4) {
        if (this.d == null) {
            this.d = new ArrayList();
        }
        this.d.add(a4);
    }

    public Fragment i0(String string) {
        return this.c.h(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void i1() {
        ArrayList arrayList = this.a;
        synchronized (arrayList) {
            Throwable throwable2;
            block4: {
                block3: {
                    try {
                        if (this.a.size() != 1) break block3;
                        this.v.v().removeCallbacks(this.R);
                        this.v.v().post(this.R);
                        this.r1();
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

    public w j(Fragment fragment) {
        Object object = fragment.S;
        if (object != null) {
            b1.c.f(fragment, (String)object);
        }
        if (FragmentManager.I0(2)) {
            ((Object)fragment).toString();
        }
        object = this.v(fragment);
        fragment.v = this;
        this.c.r((w)object);
        if (!fragment.D) {
            this.c.a(fragment);
            fragment.o = false;
            if (fragment.K == null) {
                fragment.P = false;
            }
            if (this.J0(fragment)) {
                this.H = true;
            }
        }
        return object;
    }

    public Fragment j0(String string) {
        return this.c.i(string);
    }

    public void j1(Fragment fragment, boolean bl) {
        if ((fragment = this.s0(fragment)) != null && fragment instanceof FragmentContainerView) {
            ((FragmentContainerView)((Object)fragment)).setDrawDisappearingViewsLast(bl ^ true);
        }
    }

    public void k(v v3) {
        this.o.add(v3);
    }

    public void k1(Fragment fragment, f.b object) {
        if (fragment.equals(this.f0(fragment.h)) && (fragment.w == null || fragment.v == this)) {
            fragment.T = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(fragment);
        ((StringBuilder)object).append(" is not an active fragment of FragmentManager ");
        ((StringBuilder)object).append(this);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public int l() {
        return this.i.getAndIncrement();
    }

    public void l1(Fragment fragment) {
        if (fragment != null && (!fragment.equals(this.f0(fragment.h)) || fragment.w != null && fragment.v != this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" is not an active fragment of FragmentManager ");
            stringBuilder.append(this);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        Fragment fragment2 = this.y;
        this.y = fragment;
        this.L(fragment2);
        this.L(this.y);
    }

    public void m(androidx.fragment.app.l object, i object2, Fragment fragment) {
        if (this.v == null) {
            Object object3;
            this.v = object;
            this.w = object2;
            this.x = fragment;
            if (fragment != null) {
                this.k(new v(this, fragment){
                    public final Fragment c;
                    public final FragmentManager d;
                    {
                        this.d = fragmentManager;
                        this.c = fragment;
                    }

                    @Override
                    public void a(FragmentManager fragmentManager, Fragment fragment) {
                        this.c.i0(fragment);
                    }
                });
            } else if (object instanceof v) {
                this.k((v)object);
            }
            if (this.x != null) {
                this.r1();
            }
            if (object instanceof androidx.activity.q) {
                object2 = (androidx.activity.q)object;
                object3 = object2.b();
                this.g = object3;
                if (fragment != null) {
                    object2 = fragment;
                }
                ((OnBackPressedDispatcher)object3).h((androidx.lifecycle.k)object2, this.h);
            }
            this.P = fragment != null ? fragment.v.p0(fragment) : (object instanceof c0 ? androidx.fragment.app.u.k(((c0)object).r()) : new u(false));
            this.P.p(this.P0());
            this.c.A(this.P);
            object = this.v;
            if (object instanceof d && fragment == null) {
                object = ((d)object).c();
                ((androidx.savedstate.a)object).h("android:support:fragments", new s(this));
                object = ((androidx.savedstate.a)object).b("android:support:fragments");
                if (object != null) {
                    this.e1((Parcelable)object);
                }
            }
            if ((object = this.v) instanceof androidx.activity.result.c) {
                object2 = ((androidx.activity.result.c)object).m();
                if (fragment != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(fragment.h);
                    ((StringBuilder)object).append(":");
                    object = ((StringBuilder)object).toString();
                } else {
                    object = "";
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("FragmentManager:");
                ((StringBuilder)object3).append((String)object);
                object = ((StringBuilder)object3).toString();
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append("StartActivityForResult");
                this.D = ((ActivityResultRegistry)object2).j(((StringBuilder)object3).toString(), new e(), new androidx.activity.result.a(this){
                    public final FragmentManager a;
                    {
                        this.a = fragmentManager;
                    }

                    public void b(ActivityResult object) {
                        Object object2 = (LaunchedFragmentInfo)this.a.G.pollFirst();
                        if (object2 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No Activities were started for result for ");
                            ((StringBuilder)object).append(this);
                            Log.w((String)"FragmentManager", (String)((StringBuilder)object).toString());
                            return;
                        }
                        String string = ((LaunchedFragmentInfo)object2).c;
                        int n3 = ((LaunchedFragmentInfo)object2).d;
                        object2 = this.a.c.i(string);
                        if (object2 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Activity result delivered for unknown Fragment ");
                            ((StringBuilder)object).append(string);
                            Log.w((String)"FragmentManager", (String)((StringBuilder)object).toString());
                            return;
                        }
                        ((Fragment)object2).f0(n3, ((ActivityResult)object).p(), ((ActivityResult)object).o());
                    }
                });
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append("StartIntentSenderForResult");
                this.E = ((ActivityResultRegistry)object2).j(((StringBuilder)object3).toString(), new j(), new androidx.activity.result.a(this){
                    public final FragmentManager a;
                    {
                        this.a = fragmentManager;
                    }

                    public void b(ActivityResult object) {
                        Object object2 = (LaunchedFragmentInfo)this.a.G.pollFirst();
                        if (object2 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No IntentSenders were started for ");
                            ((StringBuilder)object).append(this);
                            Log.w((String)"FragmentManager", (String)((StringBuilder)object).toString());
                            return;
                        }
                        String string = ((LaunchedFragmentInfo)object2).c;
                        int n3 = ((LaunchedFragmentInfo)object2).d;
                        object2 = this.a.c.i(string);
                        if (object2 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Intent Sender result delivered for unknown Fragment ");
                            ((StringBuilder)object).append(string);
                            Log.w((String)"FragmentManager", (String)((StringBuilder)object).toString());
                            return;
                        }
                        ((Fragment)object2).f0(n3, ((ActivityResult)object).p(), ((ActivityResult)object).o());
                    }
                });
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)object);
                ((StringBuilder)object3).append("RequestPermissions");
                this.F = ((ActivityResultRegistry)object2).j(((StringBuilder)object3).toString(), new b.c(), new androidx.activity.result.a(this){
                    public final FragmentManager a;
                    {
                        this.a = fragmentManager;
                    }

                    public void b(Map object) {
                        int n3;
                        Object object2 = object.keySet().toArray(new String[0]);
                        object = new ArrayList(object.values());
                        int[] nArray = new int[((ArrayList)object).size()];
                        for (n3 = 0; n3 < ((ArrayList)object).size(); ++n3) {
                            int n4 = (Boolean)((ArrayList)object).get(n3) != false ? 0 : -1;
                            nArray[n3] = n4;
                        }
                        Object object3 = (LaunchedFragmentInfo)this.a.G.pollFirst();
                        if (object3 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No permissions were requested for ");
                            ((StringBuilder)object).append(this);
                            Log.w((String)"FragmentManager", (String)((StringBuilder)object).toString());
                            return;
                        }
                        object = ((LaunchedFragmentInfo)object3).c;
                        n3 = ((LaunchedFragmentInfo)object3).d;
                        object3 = this.a.c.i((String)object);
                        if (object3 == null) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Permission request result delivered for unknown Fragment ");
                            ((StringBuilder)object2).append((String)object);
                            Log.w((String)"FragmentManager", (String)((StringBuilder)object2).toString());
                            return;
                        }
                        ((Fragment)object3).E0(n3, (String[])object2, nArray);
                    }
                });
            }
            if ((object = this.v) instanceof e0.b) {
                ((e0.b)object).i(this.p);
            }
            if ((object = this.v) instanceof e0.c) {
                ((e0.c)object).e(this.q);
            }
            if ((object = this.v) instanceof c0.o) {
                ((c0.o)object).u(this.r);
            }
            if ((object = this.v) instanceof c0.p) {
                ((c0.p)object).o(this.s);
            }
            if ((object = this.v) instanceof o0.v && fragment == null) {
                ((o0.v)object).g(this.t);
            }
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public final void m0() {
        Iterator iterator = this.t().iterator();
        while (iterator.hasNext()) {
            ((e0)iterator.next()).k();
        }
    }

    public final void m1(Fragment fragment) {
        ViewGroup viewGroup = this.s0(fragment);
        if (viewGroup != null && fragment.q() + fragment.v() + fragment.G() + fragment.H() > 0) {
            int n3 = a1.b.visible_removing_fragment_view_tag;
            if (viewGroup.getTag(n3) == null) {
                viewGroup.setTag(n3, (Object)fragment);
            }
            ((Fragment)viewGroup.getTag(n3)).y1(fragment.F());
        }
    }

    public void n(Fragment fragment) {
        if (FragmentManager.I0(2)) {
            Objects.toString(fragment);
        }
        if (fragment.D) {
            fragment.D = false;
            if (!fragment.n) {
                this.c.a(fragment);
                if (FragmentManager.I0(2)) {
                    ((Object)fragment).toString();
                }
                if (this.J0(fragment)) {
                    this.H = true;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final boolean n0(ArrayList arrayList, ArrayList arrayList2) {
        ArrayList arrayList3 = this.a;
        synchronized (arrayList3) {
            Throwable throwable3;
            block9: {
                Throwable throwable22;
                block8: {
                    boolean bl;
                    block7: {
                        bl = this.a.isEmpty();
                        if (!bl) break block7;
                        return false;
                    }
                    try {
                        boolean bl2;
                        int n3 = this.a.size();
                        bl = false;
                        for (int i3 = 0; i3 < n3; bl |= bl2, ++i3) {
                            bl2 = ((l)this.a.get(i3)).a(arrayList, arrayList2);
                        }
                    }
                    catch (Throwable throwable22) {
                        break block8;
                    }
                    try {
                        this.a.clear();
                        this.v.v().removeCallbacks(this.R);
                        return bl;
                    }
                    catch (Throwable throwable3) {}
                    break block9;
                }
                this.a.clear();
                this.v.v().removeCallbacks(this.R);
                throw throwable22;
            }
            throw throwable3;
        }
    }

    public void n1(Fragment fragment) {
        if (FragmentManager.I0(2)) {
            Objects.toString(fragment);
        }
        if (fragment.C) {
            fragment.C = false;
            fragment.P ^= true;
        }
    }

    public y o() {
        return new a(this);
    }

    public int o0() {
        ArrayList arrayList = this.d;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public final void o1() {
        Iterator iterator = this.c.k().iterator();
        while (iterator.hasNext()) {
            this.T0((w)iterator.next());
        }
    }

    public boolean p() {
        Iterator iterator = this.c.l().iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            Fragment fragment = (Fragment)iterator.next();
            boolean bl2 = bl;
            if (fragment != null) {
                bl2 = this.J0(fragment);
            }
            bl = bl2;
            if (!bl2) continue;
            return true;
        }
        return false;
    }

    public final u p0(Fragment fragment) {
        return this.P.j(fragment);
    }

    public final void p1(RuntimeException runtimeException) {
        Log.e((String)"FragmentManager", (String)runtimeException.getMessage());
        Log.e((String)"FragmentManager", (String)"Activity state:");
        PrintWriter printWriter = new PrintWriter(new d0("FragmentManager"));
        androidx.fragment.app.l l3 = this.v;
        if (l3 != null) {
            try {
                l3.w("  ", null, printWriter, new String[0]);
            }
            catch (Exception exception) {
                Log.e((String)"FragmentManager", (String)"Failed dumping state", (Throwable)exception);
            }
        } else {
            try {
                this.W("  ", null, printWriter, new String[0]);
            }
            catch (Exception exception) {
                Log.e((String)"FragmentManager", (String)"Failed dumping state", (Throwable)exception);
            }
        }
        throw runtimeException;
    }

    public final void q() {
        if (!this.P0()) {
            return;
        }
        throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }

    public i q0() {
        return this.w;
    }

    public void q1(k k3) {
        this.n.p(k3);
    }

    public final void r() {
        this.b = false;
        this.N.clear();
        this.M.clear();
    }

    public Fragment r0(Bundle object, String string) {
        if ((object = object.getString(string)) == null) {
            return null;
        }
        Fragment fragment = this.f0((String)object);
        if (fragment == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Fragment no longer exists for key ");
            stringBuilder.append(string);
            stringBuilder.append(": unique id ");
            stringBuilder.append((String)object);
            this.p1(new IllegalStateException(stringBuilder.toString()));
        }
        return fragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void r1() {
        Object object = this.a;
        // MONITORENTER : object
        boolean bl = this.a.isEmpty();
        boolean bl2 = true;
        if (!bl) {
            this.h.j(true);
            // MONITOREXIT : object
            return;
        }
        object = this.h;
        if (this.o0() <= 0 || !this.N0(this.x)) {
            bl2 = false;
        }
        ((androidx.activity.o)object).j(bl2);
    }

    public final void s() {
        Object object2 = this.v;
        boolean bl = object2 instanceof c0 ? this.c.p().n() : (((androidx.fragment.app.l)object2).q() instanceof Activity ? ((Activity)this.v.q()).isChangingConfigurations() ^ true : true);
        if (bl) {
            Iterator iterator = this.j.values().iterator();
            while (iterator.hasNext()) {
                for (Object object2 : ((BackStackState)iterator.next()).c) {
                    this.c.p().g((String)object2);
                }
            }
        }
    }

    public final ViewGroup s0(Fragment fragment) {
        ViewGroup viewGroup = fragment.J;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.A <= 0) {
            return null;
        }
        if (this.w.j() && (fragment = this.w.f(fragment.A)) instanceof ViewGroup) {
            return (ViewGroup)fragment;
        }
        return null;
    }

    public final Set t() {
        HashSet<e0> hashSet = new HashSet<e0>();
        Iterator iterator = this.c.k().iterator();
        while (iterator.hasNext()) {
            ViewGroup viewGroup = ((w)iterator.next()).k().J;
            if (viewGroup == null) continue;
            hashSet.add(e0.o(viewGroup, this.A0()));
        }
        return hashSet;
    }

    public androidx.fragment.app.k t0() {
        Object object = this.z;
        if (object != null) {
            return object;
        }
        object = this.x;
        if (object != null) {
            return ((Fragment)object).v.t0();
        }
        return this.A;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("FragmentManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        Object object = this.x;
        if (object != null) {
            stringBuilder.append(object.getClass().getSimpleName());
            stringBuilder.append("{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this.x)));
            stringBuilder.append("}");
        } else {
            object = this.v;
            if (object != null) {
                stringBuilder.append(object.getClass().getSimpleName());
                stringBuilder.append("{");
                stringBuilder.append(Integer.toHexString(System.identityHashCode(this.v)));
                stringBuilder.append("}");
            } else {
                stringBuilder.append("null");
            }
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public final Set u(ArrayList arrayList, int n3, int n4) {
        HashSet<e0> hashSet = new HashSet<e0>();
        while (n3 < n4) {
            ArrayList arrayList2 = ((a)arrayList.get((int)n3)).c;
            int n5 = arrayList2.size();
            int n6 = 0;
            while (n6 < n5) {
                Object object = arrayList2.get(n6);
                int n7 = n6 + 1;
                object = ((y.a)object).b;
                n6 = n7;
                if (object == null) continue;
                object = ((Fragment)object).J;
                n6 = n7;
                if (object == null) continue;
                hashSet.add(e0.n(object, this));
                n6 = n7;
            }
            ++n3;
        }
        return hashSet;
    }

    public List u0() {
        return this.c.o();
    }

    public w v(Fragment object) {
        w w3 = this.c.n(((Fragment)object).h);
        if (w3 != null) {
            return w3;
        }
        object = new w(this.n, this.c, (Fragment)object);
        ((w)object).o(this.v.q().getClassLoader());
        ((w)object).u(this.u);
        return object;
    }

    public androidx.fragment.app.l v0() {
        return this.v;
    }

    public void w(Fragment fragment) {
        if (FragmentManager.I0(2)) {
            Objects.toString(fragment);
        }
        if (!fragment.D) {
            fragment.D = true;
            if (fragment.n) {
                if (FragmentManager.I0(2)) {
                    ((Object)fragment).toString();
                }
                this.c.u(fragment);
                if (this.J0(fragment)) {
                    this.H = true;
                }
                this.m1(fragment);
            }
        }
    }

    public LayoutInflater.Factory2 w0() {
        return this.f;
    }

    public void x() {
        this.I = false;
        this.J = false;
        this.P.p(false);
        this.S(4);
    }

    public n x0() {
        return this.n;
    }

    public void y() {
        this.I = false;
        this.J = false;
        this.P.p(false);
        this.S(0);
    }

    public Fragment y0() {
        return this.x;
    }

    public void z(Configuration configuration, boolean bl) {
        if (bl && this.v instanceof e0.b) {
            this.p1(new IllegalStateException("Do not call dispatchConfigurationChanged() on host. Host implements OnConfigurationChangedProvider and automatically dispatches configuration changes to fragments."));
        }
        for (Fragment fragment : this.c.o()) {
            if (fragment == null) continue;
            fragment.N0(configuration);
            if (!bl) continue;
            fragment.x.z(configuration, true);
        }
    }

    public Fragment z0() {
        return this.y;
    }

    public static class LaunchedFragmentInfo
    implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator(){

            public LaunchedFragmentInfo a(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            public LaunchedFragmentInfo[] b(int n3) {
                return new LaunchedFragmentInfo[n3];
            }
        };
        public String c;
        public int d;

        public LaunchedFragmentInfo(Parcel parcel) {
            this.c = parcel.readString();
            this.d = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeString(this.c);
            parcel.writeInt(this.d);
        }
    }

    public static class j
    extends b.a {
        public Intent d(Context object, IntentSenderRequest intentSenderRequest) {
            Intent intent = new Intent("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST");
            Intent intent2 = intentSenderRequest.o();
            object = intentSenderRequest;
            if (intent2 != null) {
                Bundle bundle = intent2.getBundleExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
                object = intentSenderRequest;
                if (bundle != null) {
                    intent.putExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE", bundle);
                    intent2.removeExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
                    object = intentSenderRequest;
                    if (intent2.getBooleanExtra("androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE", false)) {
                        object = new IntentSenderRequest.a(intentSenderRequest.r()).b(null).c(intentSenderRequest.q(), intentSenderRequest.p()).a();
                    }
                }
            }
            intent.putExtra("androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST", (Parcelable)object);
            if (FragmentManager.I0(2)) {
                intent.toString();
            }
            return intent;
        }

        public ActivityResult e(int n3, Intent intent) {
            return new ActivityResult(n3, intent);
        }
    }

    public static abstract class k {
        public void a(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void b(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void c(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void d(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void e(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void f(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void g(FragmentManager fragmentManager, Fragment fragment, Context context) {
        }

        public void h(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void i(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void j(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
        }

        public void k(FragmentManager fragmentManager, Fragment fragment) {
        }

        public void l(FragmentManager fragmentManager, Fragment fragment) {
        }

        public abstract void m(FragmentManager var1, Fragment var2, View var3, Bundle var4);

        public void n(FragmentManager fragmentManager, Fragment fragment) {
        }
    }

    public static interface l {
        public boolean a(ArrayList var1, ArrayList var2);
    }

    public class m
    implements l {
        public final String a;
        public final int b;
        public final int c;
        public final FragmentManager d;

        public m(FragmentManager fragmentManager, String string, int n3, int n4) {
            this.d = fragmentManager;
            this.a = string;
            this.b = n3;
            this.c = n4;
        }

        @Override
        public boolean a(ArrayList arrayList, ArrayList arrayList2) {
            Fragment fragment = this.d.y;
            if (fragment != null && this.b < 0 && this.a == null && fragment.o().V0()) {
                return false;
            }
            return this.d.Y0(arrayList, arrayList2, this.a, this.b, this.c);
        }
    }
}

