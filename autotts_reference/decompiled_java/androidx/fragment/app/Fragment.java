/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.app.Activity
 *  android.app.Application
 *  android.content.ComponentCallbacks
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Looper
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnCreateContextMenuListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 */
package androidx.fragment.app;

import android.animation.Animator;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.result.ActivityResultRegistry;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.c0;
import androidx.fragment.app.e0;
import androidx.fragment.app.g0;
import androidx.fragment.app.t;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.b0;
import androidx.lifecycle.d0;
import androidx.lifecycle.e;
import androidx.lifecycle.f;
import androidx.lifecycle.o;
import androidx.lifecycle.u;
import androidx.lifecycle.z;
import androidx.savedstate.a;
import c0.c;
import c0.r;
import j1.d;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Fragment
implements ComponentCallbacks,
View.OnCreateContextMenuListener,
androidx.lifecycle.k,
androidx.lifecycle.c0,
e,
d {
    public static final Object d0 = new Object();
    public int A;
    public String B;
    public boolean C;
    public boolean D;
    public boolean E;
    public boolean F;
    public boolean G;
    public boolean H = true;
    public boolean I;
    public ViewGroup J;
    public View K;
    public boolean L;
    public boolean M = true;
    public i N;
    public Runnable O;
    public boolean P;
    public LayoutInflater Q;
    public boolean R;
    public String S;
    public f.b T;
    public androidx.lifecycle.l U;
    public c0 V;
    public o W;
    public z.b X;
    public j1.c Y;
    public int Z;
    public final AtomicInteger a0;
    public final ArrayList b0;
    public int c = -1;
    public final l c0;
    public Bundle d;
    public SparseArray e;
    public Bundle f;
    public Boolean g;
    public String h = UUID.randomUUID().toString();
    public Bundle i;
    public Fragment j;
    public String k = null;
    public int l;
    public Boolean m = null;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean s;
    public boolean t;
    public int u;
    public FragmentManager v;
    public androidx.fragment.app.l w;
    public FragmentManager x = new t();
    public Fragment y;
    public int z;

    public Fragment() {
        this.O = new Runnable(this){
            public final Fragment c;
            {
                this.c = fragment;
            }

            @Override
            public void run() {
                this.c.D1();
            }
        };
        this.T = f.b.g;
        this.W = new o();
        this.a0 = new AtomicInteger();
        this.b0 = new ArrayList();
        this.c0 = new l(this){
            public final Fragment a;
            {
                this.a = fragment;
                super(null);
            }

            @Override
            public void a() {
                this.a.Y.c();
                androidx.lifecycle.u.a(this.a);
            }
        };
        this.U();
    }

    public static Fragment W(Context object, String string, Bundle object2) {
        InstantiationException instantiationException2;
        block10: {
            IllegalAccessException illegalAccessException2;
            block9: {
                NoSuchMethodException noSuchMethodException2;
                block8: {
                    InvocationTargetException invocationTargetException2;
                    block7: {
                        block6: {
                            object = (Fragment)androidx.fragment.app.k.d(object.getClassLoader(), string).getConstructor(null).newInstance(null);
                            if (object2 == null) break block6;
                            try {
                                object2.setClassLoader(object.getClass().getClassLoader());
                                ((Fragment)object).t1((Bundle)object2);
                                return object;
                            }
                            catch (InvocationTargetException invocationTargetException2) {
                                break block7;
                            }
                            catch (NoSuchMethodException noSuchMethodException2) {
                                break block8;
                            }
                            catch (IllegalAccessException illegalAccessException2) {
                                break block9;
                            }
                            catch (InstantiationException instantiationException2) {
                                break block10;
                            }
                        }
                        return object;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unable to instantiate fragment ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(": calling Fragment constructor caused an exception");
                    throw new k(((StringBuilder)object).toString(), invocationTargetException2);
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unable to instantiate fragment ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(": could not find Fragment constructor");
                throw new k(((StringBuilder)object2).toString(), noSuchMethodException2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(": make sure class name exists, is public, and has an empty constructor that is public");
            throw new k(((StringBuilder)object).toString(), illegalAccessException2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to instantiate fragment ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(": make sure class name exists, is public, and has an empty constructor that is public");
        throw new k(((StringBuilder)object).toString(), instantiationException2);
    }

    public LayoutInflater A(Bundle object) {
        object = this.w;
        if (object != null) {
            object = ((androidx.fragment.app.l)object).y();
            o0.u.a((LayoutInflater)object, this.x.w0());
            return object;
        }
        throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
    }

    public void A0() {
        this.I = true;
    }

    public void A1(ArrayList arrayList, ArrayList arrayList2) {
        this.f();
        i i3 = this.N;
        i3.h = arrayList;
        i3.i = arrayList2;
    }

    public final int B() {
        f.b b3 = this.T;
        if (b3 != f.b.d && this.y != null) {
            return Math.min(b3.ordinal(), this.y.B());
        }
        return b3.ordinal();
    }

    public void B0(boolean bl) {
    }

    public void B1(Intent intent) {
        this.C1(intent, null);
    }

    public int C() {
        i i3 = this.N;
        if (i3 == null) {
            return 0;
        }
        return i3.g;
    }

    public void C0(Menu menu) {
    }

    public void C1(Intent object, Bundle bundle) {
        androidx.fragment.app.l l3 = this.w;
        if (l3 != null) {
            l3.z(this, (Intent)object, -1, bundle);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to Activity");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final Fragment D() {
        return this.y;
    }

    public void D0(boolean bl) {
    }

    public void D1() {
        if (this.N != null && this.f().t) {
            if (this.w == null) {
                this.f().t = false;
                return;
            }
            if (Looper.myLooper() != this.w.v().getLooper()) {
                this.w.v().postAtFrontOfQueue(new Runnable(this){
                    public final Fragment c;
                    {
                        this.c = fragment;
                    }

                    @Override
                    public void run() {
                        this.c.a(false);
                    }
                });
                return;
            }
            this.a(true);
        }
    }

    public final FragmentManager E() {
        Object object = this.v;
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not associated with a fragment manager.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void E0(int n3, String[] stringArray, int[] nArray) {
    }

    public boolean F() {
        i i3 = this.N;
        if (i3 == null) {
            return false;
        }
        return i3.b;
    }

    public void F0() {
        this.I = true;
    }

    public int G() {
        i i3 = this.N;
        if (i3 == null) {
            return 0;
        }
        return i3.e;
    }

    public void G0(Bundle bundle) {
    }

    public int H() {
        i i3 = this.N;
        if (i3 == null) {
            return 0;
        }
        return i3.f;
    }

    public void H0() {
        this.I = true;
    }

    public float I() {
        i i3 = this.N;
        if (i3 == null) {
            return 1.0f;
        }
        return i3.r;
    }

    public void I0() {
        this.I = true;
    }

    public Object J() {
        Object object;
        Object object2 = this.N;
        if (object2 == null) {
            return null;
        }
        object2 = object = ((i)object2).m;
        if (object == d0) {
            object2 = this.w();
        }
        return object2;
    }

    public void J0(View view, Bundle bundle) {
    }

    public final Resources K() {
        return this.n1().getResources();
    }

    public void K0(Bundle bundle) {
        this.I = true;
    }

    public Object L() {
        Object object;
        Object object2 = this.N;
        if (object2 == null) {
            return null;
        }
        object2 = object = ((i)object2).k;
        if (object == d0) {
            object2 = this.s();
        }
        return object2;
    }

    public void L0(Bundle object) {
        this.x.R0();
        this.c = 3;
        this.I = false;
        this.e0((Bundle)object);
        if (this.I) {
            this.q1();
            this.x.x();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onActivityCreated()");
        throw new g0(((StringBuilder)object).toString());
    }

    public Object M() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        return i3.n;
    }

    public void M0() {
        Object object;
        ArrayList arrayList = this.b0;
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            object = arrayList.get(i3);
            ((l)object).a();
        }
        this.b0.clear();
        this.x.m(this.w, this.d(), this);
        this.c = 0;
        this.I = false;
        this.h0(this.w.q());
        if (this.I) {
            this.v.H(this);
            this.x.y();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onAttach()");
        throw new g0(((StringBuilder)object).toString());
    }

    public Object N() {
        Object object;
        Object object2 = this.N;
        if (object2 == null) {
            return null;
        }
        object2 = object = ((i)object2).o;
        if (object == d0) {
            object2 = this.M();
        }
        return object2;
    }

    public void N0(Configuration configuration) {
        this.onConfigurationChanged(configuration);
    }

    public ArrayList O() {
        Object object = this.N;
        if (object != null && (object = ((i)object).h) != null) {
            return object;
        }
        return new ArrayList();
    }

    public boolean O0(MenuItem menuItem) {
        if (!this.C) {
            if (this.j0(menuItem)) {
                return true;
            }
            return this.x.A(menuItem);
        }
        return false;
    }

    public ArrayList P() {
        Object object = this.N;
        if (object != null && (object = ((i)object).i) != null) {
            return object;
        }
        return new ArrayList();
    }

    public void P0(Bundle object) {
        this.x.R0();
        this.c = 1;
        this.I = false;
        this.U.a(new androidx.lifecycle.i(this){
            public final Fragment a;
            {
                this.a = fragment;
            }

            @Override
            public void d(androidx.lifecycle.k k3, f.a a4) {
                if (a4 == f.a.ON_STOP && (k3 = this.a.K) != null) {
                    androidx.fragment.app.Fragment$j.a((View)k3);
                }
            }
        });
        this.Y.d((Bundle)object);
        this.k0((Bundle)object);
        this.R = true;
        if (this.I) {
            this.U.h(f.a.ON_CREATE);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onCreate()");
        throw new g0(((StringBuilder)object).toString());
    }

    public final String Q(int n3) {
        return this.K().getString(n3);
    }

    public boolean Q0(Menu menu, MenuInflater menuInflater) {
        boolean bl = this.C;
        boolean bl2 = false;
        if (!bl) {
            boolean bl3 = bl2;
            if (this.G) {
                bl3 = bl2;
                if (this.H) {
                    this.n0(menu, menuInflater);
                    bl3 = true;
                }
            }
            return this.x.C(menu, menuInflater) | bl3;
        }
        return false;
    }

    public final Fragment R(boolean bl) {
        Object object;
        if (bl) {
            b1.c.h(this);
        }
        if ((object = this.j) != null) {
            return object;
        }
        FragmentManager fragmentManager = this.v;
        if (fragmentManager != null && (object = this.k) != null) {
            return fragmentManager.f0((String)object);
        }
        return null;
    }

    public void R0(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.x.R0();
        this.t = true;
        this.V = new c0(this, this.r());
        layoutInflater = this.o0(layoutInflater, viewGroup, bundle);
        this.K = layoutInflater;
        if (layoutInflater != null) {
            this.V.d();
            androidx.lifecycle.d0.a(this.K, this.V);
            androidx.lifecycle.e0.a(this.K, this.V);
            j1.e.a(this.K, this.V);
            this.W.i(this.V);
            return;
        }
        if (!this.V.e()) {
            this.V = null;
            return;
        }
        throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
    }

    public View S() {
        return this.K;
    }

    public void S0() {
        this.x.D();
        this.U.h(f.a.ON_DESTROY);
        this.c = 0;
        this.I = false;
        this.R = false;
        this.p0();
        if (this.I) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDestroy()");
        throw new g0(stringBuilder.toString());
    }

    public LiveData T() {
        return this.W;
    }

    public void T0() {
        this.x.E();
        if (this.K != null && this.V.t().b().b(f.b.e)) {
            this.V.a(f.a.ON_DESTROY);
        }
        this.c = 1;
        this.I = false;
        this.r0();
        if (this.I) {
            g1.a.b(this).c();
            this.t = false;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDestroyView()");
        throw new g0(stringBuilder.toString());
    }

    public final void U() {
        this.U = new androidx.lifecycle.l(this);
        this.Y = j1.c.a(this);
        this.X = null;
        if (!this.b0.contains(this.c0)) {
            this.l1(this.c0);
        }
    }

    public void U0() {
        this.c = -1;
        this.I = false;
        this.s0();
        this.Q = null;
        if (this.I) {
            if (!this.x.H0()) {
                this.x.D();
                this.x = new t();
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onDetach()");
        throw new g0(stringBuilder.toString());
    }

    public void V() {
        this.U();
        this.S = this.h;
        this.h = UUID.randomUUID().toString();
        this.n = false;
        this.o = false;
        this.q = false;
        this.r = false;
        this.s = false;
        this.u = 0;
        this.v = null;
        this.x = new t();
        this.w = null;
        this.z = 0;
        this.A = 0;
        this.B = null;
        this.C = false;
        this.D = false;
    }

    public LayoutInflater V0(Bundle bundle) {
        bundle = this.t0(bundle);
        this.Q = bundle;
        return bundle;
    }

    public void W0() {
        this.onLowMemory();
    }

    public final boolean X() {
        return this.w != null && this.n;
    }

    public void X0(boolean bl) {
        this.x0(bl);
    }

    public final boolean Y() {
        FragmentManager fragmentManager;
        return this.C || (fragmentManager = this.v) != null && fragmentManager.L0(this.y);
        {
        }
    }

    public boolean Y0(MenuItem menuItem) {
        if (!this.C) {
            if (this.G && this.H && this.y0(menuItem)) {
                return true;
            }
            return this.x.J(menuItem);
        }
        return false;
    }

    public final boolean Z() {
        return this.u > 0;
    }

    public void Z0(Menu menu) {
        if (!this.C) {
            if (this.G && this.H) {
                this.z0(menu);
            }
            this.x.K(menu);
        }
    }

    public void a(boolean bl) {
        ViewGroup viewGroup;
        Object object = this.N;
        if (object != null) {
            ((i)object).t = false;
        }
        if (this.K != null && (viewGroup = this.J) != null && (object = this.v) != null) {
            object = e0.n(viewGroup, (FragmentManager)object);
            ((e0)object).p();
            if (bl) {
                this.w.v().post(new Runnable(this, (e0)object){
                    public final e0 c;
                    public final Fragment d;
                    {
                        this.d = fragment;
                        this.c = e02;
                    }

                    @Override
                    public void run() {
                        this.c.g();
                    }
                });
                return;
            }
            ((e0)object).g();
        }
    }

    public final boolean a0() {
        FragmentManager fragmentManager;
        return this.H && ((fragmentManager = this.v) == null || fragmentManager.M0(this.y));
    }

    public void a1() {
        this.x.M();
        if (this.K != null) {
            this.V.a(f.a.ON_PAUSE);
        }
        this.U.h(f.a.ON_PAUSE);
        this.c = 6;
        this.I = false;
        this.A0();
        if (this.I) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onPause()");
        throw new g0(stringBuilder.toString());
    }

    public boolean b0() {
        i i3 = this.N;
        if (i3 == null) {
            return false;
        }
        return i3.t;
    }

    public void b1(boolean bl) {
        this.B0(bl);
    }

    @Override
    public final a c() {
        return this.Y.b();
    }

    public final boolean c0() {
        FragmentManager fragmentManager = this.v;
        if (fragmentManager == null) {
            return false;
        }
        return fragmentManager.P0();
    }

    public boolean c1(Menu menu) {
        boolean bl = this.C;
        boolean bl2 = false;
        if (!bl) {
            boolean bl3 = bl2;
            if (this.G) {
                bl3 = bl2;
                if (this.H) {
                    this.C0(menu);
                    bl3 = true;
                }
            }
            return this.x.O(menu) | bl3;
        }
        return false;
    }

    public androidx.fragment.app.i d() {
        return new androidx.fragment.app.i(this){
            public final Fragment c;
            {
                this.c = fragment;
            }

            @Override
            public View f(int n3) {
                Object object = this.c.K;
                if (object != null) {
                    return object.findViewById(n3);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Fragment ");
                ((StringBuilder)object).append(this.c);
                ((StringBuilder)object).append(" does not have a view");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }

            @Override
            public boolean j() {
                return this.c.K != null;
            }
        };
    }

    public void d0() {
        this.x.R0();
    }

    public void d1() {
        boolean bl = this.v.N0(this);
        Boolean bl2 = this.m;
        if (bl2 != null && bl2 == bl) {
            return;
        }
        this.m = bl;
        this.D0(bl);
        this.x.P();
    }

    public void e(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        Object object;
        printWriter.print(string);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.z));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.A));
        printWriter.print(" mTag=");
        printWriter.println(this.B);
        printWriter.print(string);
        printWriter.print("mState=");
        printWriter.print(this.c);
        printWriter.print(" mWho=");
        printWriter.print(this.h);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.u);
        printWriter.print(string);
        printWriter.print("mAdded=");
        printWriter.print(this.n);
        printWriter.print(" mRemoving=");
        printWriter.print(this.o);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.q);
        printWriter.print(" mInLayout=");
        printWriter.println(this.r);
        printWriter.print(string);
        printWriter.print("mHidden=");
        printWriter.print(this.C);
        printWriter.print(" mDetached=");
        printWriter.print(this.D);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.H);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.G);
        printWriter.print(string);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.E);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.M);
        if (this.v != null) {
            printWriter.print(string);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.v);
        }
        if (this.w != null) {
            printWriter.print(string);
            printWriter.print("mHost=");
            printWriter.println(this.w);
        }
        if (this.y != null) {
            printWriter.print(string);
            printWriter.print("mParentFragment=");
            printWriter.println(this.y);
        }
        if (this.i != null) {
            printWriter.print(string);
            printWriter.print("mArguments=");
            printWriter.println(this.i);
        }
        if (this.d != null) {
            printWriter.print(string);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.d);
        }
        if (this.e != null) {
            printWriter.print(string);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.e);
        }
        if (this.f != null) {
            printWriter.print(string);
            printWriter.print("mSavedViewRegistryState=");
            printWriter.println(this.f);
        }
        if ((object = this.R(false)) != null) {
            printWriter.print(string);
            printWriter.print("mTarget=");
            printWriter.print(object);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.l);
        }
        printWriter.print(string);
        printWriter.print("mPopDirection=");
        printWriter.println(this.F());
        if (this.q() != 0) {
            printWriter.print(string);
            printWriter.print("getEnterAnim=");
            printWriter.println(this.q());
        }
        if (this.v() != 0) {
            printWriter.print(string);
            printWriter.print("getExitAnim=");
            printWriter.println(this.v());
        }
        if (this.G() != 0) {
            printWriter.print(string);
            printWriter.print("getPopEnterAnim=");
            printWriter.println(this.G());
        }
        if (this.H() != 0) {
            printWriter.print(string);
            printWriter.print("getPopExitAnim=");
            printWriter.println(this.H());
        }
        if (this.J != null) {
            printWriter.print(string);
            printWriter.print("mContainer=");
            printWriter.println(this.J);
        }
        if (this.K != null) {
            printWriter.print(string);
            printWriter.print("mView=");
            printWriter.println(this.K);
        }
        if (this.m() != null) {
            printWriter.print(string);
            printWriter.print("mAnimatingAway=");
            printWriter.println(this.m());
        }
        if (this.p() != null) {
            g1.a.b(this).a(string, fileDescriptor, printWriter, stringArray);
        }
        printWriter.print(string);
        object = new StringBuilder();
        ((StringBuilder)object).append("Child ");
        ((StringBuilder)object).append(this.x);
        ((StringBuilder)object).append(":");
        printWriter.println(((StringBuilder)object).toString());
        object = this.x;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("  ");
        ((FragmentManager)object).W(stringBuilder.toString(), fileDescriptor, printWriter, stringArray);
    }

    public void e0(Bundle bundle) {
        this.I = true;
    }

    public void e1() {
        this.x.R0();
        this.x.a0(true);
        this.c = 7;
        this.I = false;
        this.F0();
        if (this.I) {
            androidx.lifecycle.l l3 = this.U;
            f.a a4 = f.a.ON_RESUME;
            l3.h(a4);
            if (this.K != null) {
                this.V.a(a4);
            }
            this.x.Q();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onResume()");
        throw new g0(stringBuilder.toString());
    }

    public final boolean equals(Object object) {
        return super.equals(object);
    }

    public final i f() {
        if (this.N == null) {
            this.N = new i();
        }
        return this.N;
    }

    public void f0(int n3, int n4, Intent intent) {
        if (FragmentManager.I0(2)) {
            ((Object)this).toString();
            Objects.toString(intent);
        }
    }

    public void f1(Bundle bundle) {
        this.G0(bundle);
        this.Y.e(bundle);
        Bundle bundle2 = this.x.g1();
        if (bundle2 != null) {
            bundle.putParcelable("android:support:fragments", (Parcelable)bundle2);
        }
    }

    public Fragment g(String string) {
        if (string.equals(this.h)) {
            return this;
        }
        return this.x.j0(string);
    }

    public void g0(Activity activity) {
        this.I = true;
    }

    public void g1() {
        this.x.R0();
        this.x.a0(true);
        this.c = 5;
        this.I = false;
        this.H0();
        if (this.I) {
            androidx.lifecycle.l l3 = this.U;
            f.a a4 = f.a.ON_START;
            l3.h(a4);
            if (this.K != null) {
                this.V.a(a4);
            }
            this.x.R();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onStart()");
        throw new g0(stringBuilder.toString());
    }

    public String h() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fragment_");
        stringBuilder.append(this.h);
        stringBuilder.append("_rq#");
        stringBuilder.append(this.a0.getAndIncrement());
        return stringBuilder.toString();
    }

    public void h0(Context object) {
        this.I = true;
        object = this.w;
        object = object == null ? null : ((androidx.fragment.app.l)object).p();
        if (object != null) {
            this.I = false;
            this.g0((Activity)object);
        }
    }

    public void h1() {
        this.x.T();
        if (this.K != null) {
            this.V.a(f.a.ON_STOP);
        }
        this.U.h(f.a.ON_STOP);
        this.c = 4;
        this.I = false;
        this.I0();
        if (this.I) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" did not call through to super.onStop()");
        throw new g0(stringBuilder.toString());
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final FragmentActivity i() {
        androidx.fragment.app.l l3 = this.w;
        if (l3 == null) {
            return null;
        }
        return (FragmentActivity)l3.p();
    }

    public void i0(Fragment fragment) {
    }

    public void i1() {
        this.J0(this.K, this.d);
        this.x.U();
    }

    @Override
    public f1.a j() {
        Context context;
        block5: {
            context = this.n1().getApplicationContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Application) {
                    context = (Application)context;
                    break block5;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            context = null;
        }
        if (context == null && FragmentManager.I0(3)) {
            Objects.toString(this.n1().getApplicationContext());
        }
        f1.d d3 = new f1.d();
        if (context != null) {
            d3.b(z.a.e, context);
        }
        d3.b(androidx.lifecycle.u.a, this);
        d3.b(androidx.lifecycle.u.b, this);
        if (this.n() != null) {
            d3.b(androidx.lifecycle.u.c, this.n());
        }
        return d3;
    }

    public boolean j0(MenuItem menuItem) {
        return false;
    }

    public final androidx.activity.result.b j1(b.a object, l.a a4, androidx.activity.result.a a5) {
        if (this.c <= 1) {
            AtomicReference atomicReference = new AtomicReference();
            this.l1(new l(this, a4, atomicReference, (b.a)object, a5){
                public final l.a a;
                public final AtomicReference b;
                public final b.a c;
                public final androidx.activity.result.a d;
                public final Fragment e;
                {
                    this.e = fragment;
                    this.a = a4;
                    this.b = atomicReference;
                    this.c = a5;
                    this.d = a6;
                    super(null);
                }

                @Override
                public void a() {
                    String string = this.e.h();
                    ActivityResultRegistry activityResultRegistry = (ActivityResultRegistry)this.a.apply(null);
                    this.b.set(activityResultRegistry.i(string, this.e, this.c, this.d));
                }
            });
            return new androidx.activity.result.b(this, atomicReference, (b.a)object){
                public final AtomicReference a;
                public final b.a b;
                public final Fragment c;
                {
                    this.c = fragment;
                    this.a = atomicReference;
                    this.b = a4;
                }

                @Override
                public void b(Object object, c c3) {
                    androidx.activity.result.b b3 = (androidx.activity.result.b)this.a.get();
                    if (b3 != null) {
                        b3.b(object, c3);
                        return;
                    }
                    throw new IllegalStateException("Operation cannot be started before fragment is in created state");
                }

                @Override
                public void c() {
                    androidx.activity.result.b b3 = this.a.getAndSet(null);
                    if (b3 != null) {
                        b3.c();
                    }
                }
            };
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" is attempting to registerForActivityResult after being created. Fragments must call registerForActivityResult() before they are created (i.e. initialization, onAttach(), or onCreate()).");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public boolean k() {
        Object object = this.N;
        if (object != null && (object = ((i)object).q) != null) {
            return (Boolean)object;
        }
        return true;
    }

    public void k0(Bundle bundle) {
        this.I = true;
        this.p1(bundle);
        if (!this.x.O0(1)) {
            this.x.B();
        }
    }

    public final androidx.activity.result.b k1(b.a a4, androidx.activity.result.a a5) {
        return this.j1(a4, new l.a(this){
            public final Fragment a;
            {
                this.a = fragment;
            }

            public ActivityResultRegistry a(Void object) {
                object = this.a;
                androidx.fragment.app.l l3 = ((Fragment)object).w;
                if (l3 instanceof androidx.activity.result.c) {
                    return ((androidx.activity.result.c)((Object)l3)).m();
                }
                return ((Fragment)object).m1().m();
            }
        }, a5);
    }

    public boolean l() {
        Object object = this.N;
        if (object != null && (object = ((i)object).p) != null) {
            return (Boolean)object;
        }
        return true;
    }

    public Animation l0(int n3, boolean bl, int n4) {
        return null;
    }

    public final void l1(l l3) {
        if (this.c >= 0) {
            l3.a();
            return;
        }
        this.b0.add(l3);
    }

    public View m() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        return i3.a;
    }

    public Animator m0(int n3, boolean bl, int n4) {
        return null;
    }

    public final FragmentActivity m1() {
        Object object = this.i();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to an activity.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final Bundle n() {
        return this.i;
    }

    public void n0(Menu menu, MenuInflater menuInflater) {
    }

    public final Context n1() {
        Object object = this.p();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" not attached to a context.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final FragmentManager o() {
        if (this.w != null) {
            return this.x;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment ");
        stringBuilder.append(this);
        stringBuilder.append(" has not been attached yet.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public View o0(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int n3 = this.Z;
        if (n3 != 0) {
            return layoutInflater.inflate(n3, viewGroup, false);
        }
        return null;
    }

    public final View o1() {
        Object object = this.S();
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not return a View from onCreateView() or this was called before onCreateView().");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.I = true;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.m1().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onLowMemory() {
        this.I = true;
    }

    public Context p() {
        androidx.fragment.app.l l3 = this.w;
        if (l3 == null) {
            return null;
        }
        return l3.q();
    }

    public void p0() {
        this.I = true;
    }

    public void p1(Bundle bundle) {
        if (bundle != null && (bundle = bundle.getParcelable("android:support:fragments")) != null) {
            this.x.e1((Parcelable)bundle);
            this.x.B();
        }
    }

    public int q() {
        i i3 = this.N;
        if (i3 == null) {
            return 0;
        }
        return i3.c;
    }

    public void q0() {
    }

    public final void q1() {
        if (FragmentManager.I0(3)) {
            ((Object)this).toString();
        }
        if (this.K != null) {
            this.r1(this.d);
        }
        this.d = null;
    }

    @Override
    public b0 r() {
        if (this.v != null) {
            if (this.B() != f.b.d.ordinal()) {
                return this.v.D0(this);
            }
            throw new IllegalStateException("Calling getViewModelStore() before a Fragment reaches onCreate() when using setMaxLifecycle(INITIALIZED) is not supported");
        }
        throw new IllegalStateException("Can't access ViewModels from detached fragment");
    }

    public void r0() {
        this.I = true;
    }

    public final void r1(Bundle object) {
        SparseArray sparseArray = this.e;
        if (sparseArray != null) {
            this.K.restoreHierarchyState(sparseArray);
            this.e = null;
        }
        if (this.K != null) {
            this.V.f(this.f);
            this.f = null;
        }
        this.I = false;
        this.K0((Bundle)object);
        if (this.I) {
            if (this.K != null) {
                this.V.a(f.a.ON_CREATE);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fragment ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" did not call through to super.onViewStateRestored()");
        throw new g0(((StringBuilder)object).toString());
    }

    public Object s() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        return i3.j;
    }

    public void s0() {
        this.I = true;
    }

    public void s1(int n3, int n4, int n5, int n6) {
        if (this.N == null && n3 == 0 && n4 == 0 && n5 == 0 && n6 == 0) {
            return;
        }
        this.f().c = n3;
        this.f().d = n4;
        this.f().e = n5;
        this.f().f = n6;
    }

    @Override
    public f t() {
        return this.U;
    }

    public LayoutInflater t0(Bundle bundle) {
        return this.A(bundle);
    }

    public void t1(Bundle bundle) {
        if (this.v != null && this.c0()) {
            throw new IllegalStateException("Fragment already added and state has been saved");
        }
        this.i = bundle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append("}");
        stringBuilder.append(" (");
        stringBuilder.append(this.h);
        if (this.z != 0) {
            stringBuilder.append(" id=0x");
            stringBuilder.append(Integer.toHexString(this.z));
        }
        if (this.B != null) {
            stringBuilder.append(" tag=");
            stringBuilder.append(this.B);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public r u() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        i3.getClass();
        return null;
    }

    public void u0(boolean bl) {
    }

    public void u1(View view) {
        this.f().s = view;
    }

    public int v() {
        i i3 = this.N;
        if (i3 == null) {
            return 0;
        }
        return i3.d;
    }

    public void v0(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.I = true;
    }

    public void v1(SavedState savedState) {
        if (this.v == null) {
            if (savedState == null || (savedState = savedState.c) == null) {
                savedState = null;
            }
            this.d = savedState;
            return;
        }
        throw new IllegalStateException("Fragment already added");
    }

    public Object w() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        return i3.l;
    }

    public void w0(Context object, AttributeSet attributeSet, Bundle bundle) {
        this.I = true;
        object = this.w;
        object = object == null ? null : ((androidx.fragment.app.l)object).p();
        if (object != null) {
            this.I = false;
            this.v0((Activity)object, attributeSet, bundle);
        }
    }

    public void w1(boolean bl) {
        if (this.H != bl) {
            this.H = bl;
            if (this.G && this.X() && !this.Y()) {
                this.w.A();
            }
        }
    }

    public r x() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        i3.getClass();
        return null;
    }

    public void x0(boolean bl) {
    }

    public void x1(int n3) {
        if (this.N == null && n3 == 0) {
            return;
        }
        this.f();
        this.N.g = n3;
    }

    public View y() {
        i i3 = this.N;
        if (i3 == null) {
            return null;
        }
        return i3.s;
    }

    public boolean y0(MenuItem menuItem) {
        return false;
    }

    public void y1(boolean bl) {
        if (this.N == null) {
            return;
        }
        this.f().b = bl;
    }

    public final Object z() {
        androidx.fragment.app.l l3 = this.w;
        if (l3 == null) {
            return null;
        }
        return l3.x();
    }

    public void z0(Menu menu) {
    }

    public void z1(float f3) {
        this.f().r = f3;
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public final Bundle c;

        public SavedState(Bundle bundle) {
            this.c = bundle;
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            parcel = parcel.readBundle();
            this.c = parcel;
            if (classLoader != null && parcel != null) {
                parcel.setClassLoader(classLoader);
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeBundle(this.c);
        }
    }

    public static class i {
        public View a;
        public boolean b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public ArrayList h;
        public ArrayList i;
        public Object j = null;
        public Object k;
        public Object l;
        public Object m;
        public Object n;
        public Object o;
        public Boolean p;
        public Boolean q;
        public float r;
        public View s;
        public boolean t;

        public i() {
            Object object;
            this.k = object = d0;
            this.l = null;
            this.m = object;
            this.n = null;
            this.o = object;
            this.r = 1.0f;
            this.s = null;
        }
    }

    public static abstract class j {
        public static void a(View view) {
            view.cancelPendingInputEvents();
        }
    }

    public static class k
    extends RuntimeException {
        public k(String string, Exception exception) {
            super(string, exception);
        }
    }

    public static abstract class l {
        public l() {
        }

        public /* synthetic */ l(b b3) {
            this();
        }

        public abstract void a();
    }
}

