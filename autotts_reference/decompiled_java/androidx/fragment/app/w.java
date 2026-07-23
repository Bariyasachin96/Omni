/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.res.Resources$NotFoundException
 *  android.os.Bundle
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentState;
import androidx.fragment.app.e0;
import androidx.fragment.app.k;
import androidx.fragment.app.l;
import androidx.fragment.app.n;
import androidx.fragment.app.x;
import androidx.lifecycle.c0;
import b1.c;
import java.util.Objects;
import o0.x0;

public class w {
    public final n a;
    public final x b;
    public final Fragment c;
    public boolean d = false;
    public int e = -1;

    public w(n n3, x x3, Fragment fragment) {
        this.a = n3;
        this.b = x3;
        this.c = fragment;
    }

    public w(n object, x x3, Fragment fragment, FragmentState fragmentState) {
        this.a = object;
        this.b = x3;
        this.c = fragment;
        fragment.e = null;
        fragment.f = null;
        fragment.u = 0;
        fragment.r = false;
        fragment.n = false;
        object = fragment.j;
        object = object != null ? ((Fragment)object).h : null;
        fragment.k = object;
        fragment.j = null;
        object = fragmentState.o;
        if (object != null) {
            fragment.d = object;
            return;
        }
        fragment.d = new Bundle();
    }

    public w(n object, x x3, ClassLoader classLoader, k k3, FragmentState fragmentState) {
        this.a = object;
        this.b = x3;
        this.c = object = fragmentState.o(k3, classLoader);
        if (FragmentManager.I0(2)) {
            Objects.toString(object);
        }
    }

    public void a() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        Fragment fragment = this.c;
        fragment.L0(fragment.d);
        n n3 = this.a;
        fragment = this.c;
        n3.a(fragment, fragment.d, false);
    }

    public void b() {
        int n3 = this.b.j(this.c);
        Fragment fragment = this.c;
        fragment.J.addView(fragment.K, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void c() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        Fragment fragment = this.c;
        Object object = fragment.j;
        Object object2 = null;
        if (object != null) {
            object2 = this.b.n(((Fragment)object).h);
            if (object2 == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Fragment ");
                ((StringBuilder)object2).append(this.c);
                ((StringBuilder)object2).append(" declared target fragment ");
                ((StringBuilder)object2).append(this.c.j);
                ((StringBuilder)object2).append(" that does not belong to this FragmentManager!");
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
            object = this.c;
            ((Fragment)object).k = ((Fragment)object).j.h;
            ((Fragment)object).j = null;
        } else {
            object = fragment.k;
            if (object != null && (object2 = this.b.n((String)object)) == null) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Fragment ");
                ((StringBuilder)object2).append(this.c);
                ((StringBuilder)object2).append(" declared target fragment ");
                ((StringBuilder)object2).append(this.c.k);
                ((StringBuilder)object2).append(" that does not belong to this FragmentManager!");
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
        }
        if (object2 != null) {
            ((w)object2).m();
        }
        object2 = this.c;
        ((Fragment)object2).w = ((Fragment)object2).v.v0();
        object2 = this.c;
        ((Fragment)object2).y = ((Fragment)object2).v.y0();
        this.a.g(this.c, false);
        this.c.M0();
        this.a.b(this.c, false);
    }

    public int d() {
        Object object = this.c;
        if (object.v == null) {
            return object.c;
        }
        int n3 = this.e;
        int n4 = androidx.fragment.app.w$b.a[object.T.ordinal()];
        int n5 = n3;
        if (n4 != 1) {
            n5 = n4 != 2 ? (n4 != 3 ? (n4 != 4 ? Math.min(n3, -1) : Math.min(n3, 0)) : Math.min(n3, 1)) : Math.min(n3, 5);
        }
        object = this.c;
        n3 = n5;
        if (object.q) {
            if (object.r) {
                n5 = Math.max(this.e, 2);
                object = this.c.K;
                n3 = n5;
                if (object != null) {
                    n3 = n5;
                    if (object.getParent() == null) {
                        n3 = Math.min(n5, 2);
                    }
                }
            } else {
                n3 = this.e < 4 ? Math.min(n5, object.c) : Math.min(n5, 1);
            }
        }
        n4 = n3;
        if (!this.c.n) {
            n4 = Math.min(n3, 1);
        }
        Fragment fragment = this.c;
        object = fragment.J;
        object = object != null ? e0.n((ViewGroup)object, fragment.E()).l(this) : null;
        if (object == e0.e.b.d) {
            n5 = Math.min(n4, 6);
        } else if (object == e0.e.b.e) {
            n5 = Math.max(n4, 3);
        } else {
            object = this.c;
            n5 = n4;
            if (object.o) {
                n5 = object.Z() ? Math.min(n4, 1) : Math.min(n4, -1);
            }
        }
        object = this.c;
        n3 = n5;
        if (object.L) {
            n3 = n5;
            if (object.c < 5) {
                n3 = Math.min(n5, 4);
            }
        }
        if (FragmentManager.I0(2)) {
            Objects.toString(this.c);
        }
        return n3;
    }

    public void e() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        Object object = this.c;
        if (!((Fragment)object).R) {
            this.a.h((Fragment)object, ((Fragment)object).d, false);
            object = this.c;
            ((Fragment)object).P0(((Fragment)object).d);
            object = this.a;
            Fragment fragment = this.c;
            ((n)object).c(fragment, fragment.d, false);
            return;
        }
        ((Fragment)object).p1(((Fragment)object).d);
        this.c.c = 1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void f() {
        int n3;
        Object object;
        LayoutInflater layoutInflater;
        Object object2;
        block16: {
            block17: {
                block18: {
                    if (this.c.q) {
                        return;
                    }
                    if (FragmentManager.I0(3)) {
                        Objects.toString(this.c);
                    }
                    object2 = this.c;
                    layoutInflater = ((Fragment)object2).V0(((Fragment)object2).d);
                    object = this.c;
                    object2 = ((Fragment)object).J;
                    if (object2 != null) break block16;
                    n3 = ((Fragment)object).A;
                    if (n3 == 0) break block17;
                    if (n3 == -1) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Cannot create fragment ");
                        ((StringBuilder)object2).append(this.c);
                        ((StringBuilder)object2).append(" for a container view with no id");
                        throw new IllegalArgumentException(((StringBuilder)object2).toString());
                    }
                    object = (ViewGroup)((Fragment)object).v.q0().f(this.c.A);
                    if (object != null) break block18;
                    object2 = this.c;
                    if (((Fragment)object2).s) {
                        object2 = object;
                        break block16;
                    } else {
                        try {
                            object2 = ((Fragment)object2).K().getResourceName(this.c.A);
                        }
                        catch (Resources.NotFoundException notFoundException) {
                            object2 = "unknown";
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("No view found for id 0x");
                        ((StringBuilder)object).append(Integer.toHexString(this.c.A));
                        ((StringBuilder)object).append(" (");
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append(") for fragment ");
                        ((StringBuilder)object).append(this.c);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                }
                object2 = object;
                if (!(object instanceof FragmentContainerView)) {
                    b1.c.i(this.c, (ViewGroup)object);
                    object2 = object;
                }
                break block16;
            }
            object2 = null;
        }
        object = this.c;
        ((Fragment)object).J = object2;
        ((Fragment)object).R0(layoutInflater, (ViewGroup)object2, ((Fragment)object).d);
        object = this.c.K;
        if (object != null) {
            object.setSaveFromParentEnabled(false);
            object = this.c;
            ((Fragment)object).K.setTag(a1.b.fragment_container_view_tag, object);
            if (object2 != null) {
                this.b();
            }
            object2 = this.c;
            if (((Fragment)object2).C) {
                ((Fragment)object2).K.setVisibility(8);
            }
            if (x0.N(this.c.K)) {
                x0.e0(this.c.K);
            } else {
                object2 = this.c.K;
                object2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this, (View)object2){
                    public final View c;
                    public final w d;
                    {
                        this.d = w3;
                        this.c = view;
                    }

                    public void onViewAttachedToWindow(View view) {
                        this.c.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
                        x0.e0(this.c);
                    }

                    public void onViewDetachedFromWindow(View view) {
                    }
                });
            }
            this.c.i1();
            object2 = this.a;
            object = this.c;
            ((n)object2).m((Fragment)object, ((Fragment)object).K, ((Fragment)object).d, false);
            n3 = this.c.K.getVisibility();
            float f3 = this.c.K.getAlpha();
            this.c.z1(f3);
            object2 = this.c;
            if (((Fragment)object2).J != null && n3 == 0) {
                object2 = ((Fragment)object2).K.findFocus();
                if (object2 != null) {
                    this.c.u1((View)object2);
                    if (FragmentManager.I0(2)) {
                        object2.toString();
                        Objects.toString(this.c);
                    }
                }
                this.c.K.setAlpha(0.0f);
            }
        }
        this.c.c = 2;
    }

    public void g() {
        Object object3;
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        Object object2 = this.c;
        boolean bl = ((Fragment)object2).o;
        boolean bl2 = true;
        boolean bl3 = bl && !((Fragment)object2).Z();
        if (bl3) {
            object2 = this.c;
            if (!((Fragment)object2).p) {
                this.b.B(((Fragment)object2).h, null);
            }
        }
        if (!bl3 && !this.b.p().q(this.c)) {
            object2 = this.c.k;
            if (object2 != null && (object2 = this.b.f((String)object2)) != null && ((Fragment)object2).E) {
                this.c.j = object2;
            }
            this.c.c = 0;
            return;
        }
        object2 = this.c.w;
        if (object2 instanceof c0) {
            bl2 = this.b.p().n();
        } else if (((l)object2).q() instanceof Activity) {
            bl2 = true ^ ((Activity)((l)object2).q()).isChangingConfigurations();
        }
        if (bl3 && !this.c.p || bl2) {
            this.b.p().f(this.c);
        }
        this.c.S0();
        this.a.d(this.c, false);
        for (Object object3 : this.b.k()) {
            if (object3 == null) continue;
            object3 = ((w)object3).k();
            if (!this.c.h.equals(((Fragment)object3).k)) continue;
            ((Fragment)object3).j = this.c;
            ((Fragment)object3).k = null;
        }
        object3 = this.c;
        object2 = ((Fragment)object3).k;
        if (object2 != null) {
            ((Fragment)object3).j = this.b.f((String)object2);
        }
        this.b.s(this);
    }

    public void h() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        Fragment fragment = this.c;
        Object object = fragment.J;
        if (object != null && (fragment = fragment.K) != null) {
            object.removeView((View)fragment);
        }
        this.c.T0();
        this.a.n(this.c, false);
        object = this.c;
        object.J = null;
        object.K = null;
        object.V = null;
        object.W.i(null);
        this.c.r = false;
    }

    public void i() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        this.c.U0();
        this.a.e(this.c, false);
        Fragment fragment = this.c;
        fragment.c = -1;
        fragment.w = null;
        fragment.y = null;
        fragment.v = null;
        if (fragment.o && !fragment.Z() || this.b.p().q(this.c)) {
            if (FragmentManager.I0(3)) {
                Objects.toString(this.c);
            }
            this.c.V();
        }
    }

    public void j() {
        Object object = this.c;
        if (((Fragment)object).q && ((Fragment)object).r && !((Fragment)object).t) {
            if (FragmentManager.I0(3)) {
                Objects.toString(this.c);
            }
            object = this.c;
            ((Fragment)object).R0(((Fragment)object).V0(((Fragment)object).d), null, this.c.d);
            object = this.c.K;
            if (object != null) {
                object.setSaveFromParentEnabled(false);
                object = this.c;
                ((Fragment)object).K.setTag(a1.b.fragment_container_view_tag, object);
                object = this.c;
                if (((Fragment)object).C) {
                    ((Fragment)object).K.setVisibility(8);
                }
                this.c.i1();
                object = this.a;
                Fragment fragment = this.c;
                ((n)object).m(fragment, fragment.K, fragment.d, false);
                this.c.c = 2;
            }
        }
    }

    public Fragment k() {
        return this.c;
    }

    public final boolean l(View view) {
        if (view == this.c.K) {
            return true;
        }
        for (view = view.getParent(); view != null; view = view.getParent()) {
            if (view != this.c.K) continue;
            return true;
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    public void m() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 3[TRYBLOCK] [3 : 123->155)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void n() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        this.c.a1();
        this.a.f(this.c, false);
    }

    public void o(ClassLoader object) {
        Object object2 = this.c.d;
        if (object2 != null) {
            object2.setClassLoader((ClassLoader)object);
            object = this.c;
            ((Fragment)object).e = ((Fragment)object).d.getSparseParcelableArray("android:view_state");
            object = this.c;
            ((Fragment)object).f = ((Fragment)object).d.getBundle("android:view_registry_state");
            object = this.c;
            ((Fragment)object).k = ((Fragment)object).d.getString("android:target_state");
            object = this.c;
            if (((Fragment)object).k != null) {
                ((Fragment)object).l = ((Fragment)object).d.getInt("android:target_req_state", 0);
            }
            object = this.c;
            object2 = ((Fragment)object).g;
            if (object2 != null) {
                ((Fragment)object).M = (Boolean)object2;
                this.c.g = null;
            } else {
                ((Fragment)object).M = ((Fragment)object).d.getBoolean("android:user_visible_hint", true);
            }
            object = this.c;
            if (!((Fragment)object).M) {
                ((Fragment)object).L = true;
            }
        }
    }

    public void p() {
        Object object;
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        if ((object = this.c.y()) != null && this.l((View)object)) {
            object.requestFocus();
            if (FragmentManager.I0(2)) {
                object.toString();
                Objects.toString(this.c);
                Objects.toString(this.c.K.findFocus());
            }
        }
        this.c.u1(null);
        this.c.e1();
        this.a.i(this.c, false);
        object = this.c;
        object.d = null;
        object.e = null;
        object.f = null;
    }

    public final Bundle q() {
        Bundle bundle = new Bundle();
        this.c.f1(bundle);
        this.a.j(this.c, bundle, false);
        Bundle bundle2 = bundle;
        if (bundle.isEmpty()) {
            bundle2 = null;
        }
        if (this.c.K != null) {
            this.t();
        }
        bundle = bundle2;
        if (this.c.e != null) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", this.c.e);
        }
        bundle2 = bundle;
        if (this.c.f != null) {
            bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putBundle("android:view_registry_state", this.c.f);
        }
        bundle = bundle2;
        if (!this.c.M) {
            bundle = bundle2;
            if (bundle2 == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", this.c.M);
        }
        return bundle;
    }

    public Fragment.SavedState r() {
        Bundle bundle;
        if (this.c.c > -1 && (bundle = this.q()) != null) {
            return new Fragment.SavedState(bundle);
        }
        return null;
    }

    public void s() {
        FragmentState fragmentState = new FragmentState(this.c);
        Fragment fragment = this.c;
        if (fragment.c > -1 && fragmentState.o == null) {
            fragment = this.q();
            fragmentState.o = fragment;
            if (this.c.k != null) {
                if (fragment == null) {
                    fragmentState.o = new Bundle();
                }
                fragmentState.o.putString("android:target_state", this.c.k);
                int n3 = this.c.l;
                if (n3 != 0) {
                    fragmentState.o.putInt("android:target_req_state", n3);
                }
            }
        } else {
            fragmentState.o = fragment.d;
        }
        this.b.B(this.c.h, fragmentState);
    }

    public void t() {
        if (this.c.K != null) {
            if (FragmentManager.I0(2)) {
                Objects.toString(this.c);
                Objects.toString(this.c.K);
            }
            SparseArray sparseArray = new SparseArray();
            this.c.K.saveHierarchyState(sparseArray);
            if (sparseArray.size() > 0) {
                this.c.e = sparseArray;
            }
            sparseArray = new Bundle();
            this.c.V.g((Bundle)sparseArray);
            if (!sparseArray.isEmpty()) {
                this.c.f = sparseArray;
            }
        }
    }

    public void u(int n3) {
        this.e = n3;
    }

    public void v() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        this.c.g1();
        this.a.k(this.c, false);
    }

    public void w() {
        if (FragmentManager.I0(3)) {
            Objects.toString(this.c);
        }
        this.c.h1();
        this.a.l(this.c, false);
    }
}

