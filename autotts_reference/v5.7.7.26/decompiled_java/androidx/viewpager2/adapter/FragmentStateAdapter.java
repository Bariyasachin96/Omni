/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.FrameLayout
 */
package androidx.viewpager2.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.appcompat.app.s;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.y;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.j;
import androidx.lifecycle.k;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.a;
import androidx.viewpager2.adapter.b;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import n0.h;

public abstract class FragmentStateAdapter
extends RecyclerView.h
implements b {
    public final f d;
    public final FragmentManager e;
    public final o.j f = new o.j();
    public final o.j g = new o.j();
    public final o.j h = new o.j();
    public FragmentMaxLifecycleEnforcer i;
    public d j = new d();
    public boolean k = false;
    public boolean l = false;

    public FragmentStateAdapter(FragmentActivity fragmentActivity) {
        this(fragmentActivity.P(), fragmentActivity.t());
    }

    public FragmentStateAdapter(FragmentManager fragmentManager, f f3) {
        this.e = fragmentManager;
        this.d = f3;
        super.x(true);
    }

    public static String C(String string, long l3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(l3);
        return stringBuilder.toString();
    }

    public static boolean G(String string, String string2) {
        return string.startsWith(string2) && string.length() > string2.length();
    }

    public static long N(String string, String string2) {
        return Long.parseLong(string.substring(string2.length()));
    }

    public boolean A(long l3) {
        return l3 >= 0L && l3 < (long)this.f();
    }

    public abstract Fragment B(int var1);

    public final void D(int n3) {
        long l3 = this.g(n3);
        if (!this.f.c(l3)) {
            Fragment fragment = this.B(n3);
            fragment.v1((Fragment.SavedState)this.g.d(l3));
            this.f.h(l3, fragment);
        }
    }

    public void E() {
        if (this.l && !this.S()) {
            long l3;
            int n3;
            Object object = new o.b();
            int n4 = 0;
            for (n3 = 0; n3 < this.f.k(); ++n3) {
                l3 = this.f.g(n3);
                if (this.A(l3)) continue;
                object.add(l3);
                this.h.i(l3);
            }
            if (!this.k) {
                this.l = false;
                for (n3 = n4; n3 < this.f.k(); ++n3) {
                    l3 = this.f.g(n3);
                    if (this.F(l3)) continue;
                    object.add(l3);
                }
            }
            object = object.iterator();
            while (object.hasNext()) {
                this.P((Long)object.next());
            }
        }
    }

    public final boolean F(long l3) {
        if (this.h.c(l3)) {
            return true;
        }
        Fragment fragment = (Fragment)this.f.d(l3);
        if (fragment == null) {
            return false;
        }
        if ((fragment = fragment.S()) == null) {
            return false;
        }
        return fragment.getParent() != null;
    }

    public final Long H(int n3) {
        Long l3 = null;
        for (int i3 = 0; i3 < this.h.k(); ++i3) {
            Long l4 = l3;
            if ((Integer)this.h.l(i3) == n3) {
                if (l3 == null) {
                    l4 = this.h.g(i3);
                } else {
                    throw new IllegalStateException("Design assumption violated: a ViewHolder can only be bound to one item at a time.");
                }
            }
            l3 = l4;
        }
        return l3;
    }

    public final void I(a a4, int n3) {
        long l3 = a4.k();
        int n4 = a4.N().getId();
        Long l4 = this.H(n4);
        if (l4 != null && l4 != l3) {
            this.P(l4);
            this.h.i(l4);
        }
        this.h.h(l3, n4);
        this.D(n3);
        if (a4.N().isAttachedToWindow()) {
            this.O(a4);
        }
        this.E();
    }

    public final a J(ViewGroup viewGroup, int n3) {
        return androidx.viewpager2.adapter.a.M(viewGroup);
    }

    public final boolean K(a a4) {
        return true;
    }

    public final void L(a a4) {
        this.O(a4);
        this.E();
    }

    public final void M(a object) {
        if ((object = this.H(((a)object).N().getId())) != null) {
            this.P((Long)object);
            this.h.i((Long)object);
        }
    }

    public void O(a a4) {
        block12: {
            block15: {
                block14: {
                    Object object;
                    Object object2;
                    Fragment fragment;
                    block13: {
                        fragment = (Fragment)this.f.d(a4.k());
                        if (fragment == null) break block12;
                        object2 = a4.N();
                        object = fragment.S();
                        if (!fragment.X() && object != null) {
                            throw new IllegalStateException("Design assumption violated.");
                        }
                        if (fragment.X() && object == null) {
                            this.R(fragment, (FrameLayout)object2);
                            return;
                        }
                        if (!fragment.X() || object.getParent() == null) break block13;
                        if (object.getParent() != object2) {
                            this.z((View)object, (FrameLayout)object2);
                            return;
                        }
                        break block14;
                    }
                    if (fragment.X()) {
                        this.z((View)object, (FrameLayout)object2);
                        return;
                    }
                    if (!this.S()) {
                        this.R(fragment, (FrameLayout)object2);
                        object = this.j.c(fragment);
                        try {
                            fragment.w1(false);
                            y y3 = this.e.o();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("f");
                            ((StringBuilder)object2).append(a4.k());
                            y3.d(fragment, ((StringBuilder)object2).toString()).p(fragment, f.b.f).h();
                            this.i.d(false);
                            return;
                        }
                        finally {
                            this.j.b((List)object);
                        }
                    }
                    if (!this.e.H0()) break block15;
                }
                return;
            }
            this.d.a(new i(this, a4){
                public final a a;
                public final FragmentStateAdapter b;
                {
                    this.b = fragmentStateAdapter;
                    this.a = a4;
                }

                @Override
                public void d(k k3, f.a a4) {
                    if (!this.b.S()) {
                        k3.t().c(this);
                        if (this.a.N().isAttachedToWindow()) {
                            this.b.O(this.a);
                        }
                    }
                }
            });
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    public final void P(long l3) {
        Object object;
        Fragment fragment = (Fragment)this.f.d(l3);
        if (fragment == null) {
            return;
        }
        if (fragment.S() != null && (object = fragment.S().getParent()) != null) {
            ((FrameLayout)object).removeAllViews();
        }
        if (!this.A(l3)) {
            this.g.i(l3);
        }
        if (!fragment.X()) {
            this.f.i(l3);
            return;
        }
        if (this.S()) {
            this.l = true;
            return;
        }
        if (fragment.X() && this.A(l3)) {
            List list = this.j.e(fragment);
            object = this.e.h1(fragment);
            this.j.b(list);
            this.g.h(l3, object);
        }
        object = this.j.d(fragment);
        try {
            this.e.o().m(fragment).h();
            this.f.i(l3);
            return;
        }
        finally {
            this.j.b((List)object);
        }
    }

    public final void Q() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable(this){
            public final FragmentStateAdapter c;
            {
                this.c = fragmentStateAdapter;
            }

            @Override
            public void run() {
                FragmentStateAdapter fragmentStateAdapter = this.c;
                fragmentStateAdapter.k = false;
                fragmentStateAdapter.E();
            }
        };
        this.d.a(new i(this, handler, runnable){
            public final Handler a;
            public final Runnable b;
            public final FragmentStateAdapter c;
            {
                this.c = fragmentStateAdapter;
                this.a = handler;
                this.b = runnable;
            }

            @Override
            public void d(k k3, f.a a4) {
                if (a4 == f.a.ON_DESTROY) {
                    this.a.removeCallbacks(this.b);
                    k3.t().c(this);
                }
            }
        });
        handler.postDelayed(runnable, 10000L);
    }

    public final void R(Fragment fragment, FrameLayout frameLayout) {
        this.e.a1(new FragmentManager.k(this, fragment, frameLayout){
            public final Fragment a;
            public final FrameLayout b;
            public final FragmentStateAdapter c;
            {
                this.c = fragmentStateAdapter;
                this.a = fragment;
                this.b = frameLayout;
            }

            @Override
            public void m(FragmentManager fragmentManager, Fragment fragment, View view, Bundle bundle) {
                if (fragment == this.a) {
                    fragmentManager.q1(this);
                    this.c.z(view, this.b);
                }
            }
        }, false);
    }

    public boolean S() {
        return this.e.P0();
    }

    @Override
    public final Parcelable a() {
        long l3;
        Bundle bundle = new Bundle(this.f.k() + this.g.k());
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= this.f.k()) break;
            l3 = this.f.g(n4);
            Fragment fragment = (Fragment)this.f.d(l3);
            if (fragment != null && fragment.X()) {
                String string = FragmentStateAdapter.C("f#", l3);
                this.e.Z0(bundle, string, fragment);
            }
            ++n4;
        }
        for (int i3 = n3; i3 < this.g.k(); ++i3) {
            l3 = this.g.g(i3);
            if (!this.A(l3)) continue;
            bundle.putParcelable(FragmentStateAdapter.C("s#", l3), (Parcelable)this.g.d(l3));
        }
        return bundle;
    }

    @Override
    public final void b(Parcelable object) {
        if (this.g.f() && this.f.f()) {
            if ((object = (Bundle)object).getClassLoader() == null) {
                object.setClassLoader(this.getClass().getClassLoader());
            }
            for (Object object2 : object.keySet()) {
                long l3;
                if (FragmentStateAdapter.G((String)object2, "f#")) {
                    l3 = FragmentStateAdapter.N((String)object2, "f#");
                    object2 = this.e.r0((Bundle)object, (String)object2);
                    this.f.h(l3, object2);
                    continue;
                }
                if (FragmentStateAdapter.G((String)object2, "s#")) {
                    l3 = FragmentStateAdapter.N((String)object2, "s#");
                    object2 = (Fragment.SavedState)object.getParcelable((String)object2);
                    if (!this.A(l3)) continue;
                    this.g.h(l3, object2);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected key in savedState: ");
                ((StringBuilder)object).append((String)object2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (!this.f.f()) {
                this.l = true;
                this.k = true;
                this.E();
                this.Q();
            }
            return;
        }
        throw new IllegalStateException("Expected the adapter to be 'fresh' while restoring state.");
    }

    @Override
    public long g(int n3) {
        return n3;
    }

    @Override
    public void n(RecyclerView recyclerView) {
        FragmentMaxLifecycleEnforcer fragmentMaxLifecycleEnforcer;
        boolean bl = this.i == null;
        n0.h.a(bl);
        this.i = fragmentMaxLifecycleEnforcer = new FragmentMaxLifecycleEnforcer(this);
        fragmentMaxLifecycleEnforcer.b(recyclerView);
    }

    @Override
    public void r(RecyclerView recyclerView) {
        this.i.c(recyclerView);
        this.i = null;
    }

    public void z(View view, FrameLayout frameLayout) {
        if (frameLayout.getChildCount() <= 1) {
            if (view.getParent() == frameLayout) {
                return;
            }
            if (frameLayout.getChildCount() > 0) {
                frameLayout.removeAllViews();
            }
            if (view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
            frameLayout.addView(view);
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    public class FragmentMaxLifecycleEnforcer {
        public ViewPager2.i a;
        public RecyclerView.j b;
        public i c;
        public ViewPager2 d;
        public long e;
        public final FragmentStateAdapter f;

        public FragmentMaxLifecycleEnforcer(FragmentStateAdapter fragmentStateAdapter) {
            this.f = fragmentStateAdapter;
            this.e = -1L;
        }

        public final ViewPager2 a(RecyclerView object) {
            ViewParent viewParent = object.getParent();
            if (viewParent instanceof ViewPager2) {
                return (ViewPager2)viewParent;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Expected ViewPager2 instance. Got: ");
            ((StringBuilder)object).append(viewParent);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        public void b(RecyclerView object) {
            this.d = this.a((RecyclerView)object);
            this.a = object = new ViewPager2.i(this){
                public final FragmentMaxLifecycleEnforcer a;
                {
                    this.a = fragmentMaxLifecycleEnforcer;
                }

                @Override
                public void a(int n3) {
                    this.a.d(false);
                }

                @Override
                public void c(int n3) {
                    this.a.d(false);
                }
            };
            this.d.g((ViewPager2.i)object);
            this.b = object = new c(this){
                public final FragmentMaxLifecycleEnforcer a;
                {
                    this.a = fragmentMaxLifecycleEnforcer;
                    super(null);
                }

                @Override
                public void a() {
                    this.a.d(true);
                }
            };
            this.f.w((RecyclerView.j)object);
            this.c = object = new i(this){
                public final FragmentMaxLifecycleEnforcer a;
                {
                    this.a = fragmentMaxLifecycleEnforcer;
                }

                @Override
                public void d(k k3, f.a a4) {
                    this.a.d(false);
                }
            };
            this.f.d.a((j)object);
        }

        public void c(RecyclerView recyclerView) {
            this.a(recyclerView).m(this.a);
            this.f.y(this.b);
            this.f.d.c(this.c);
            this.d = null;
        }

        public void d(boolean bl) {
            Object object;
            long l3;
            int n3;
            if (!this.f.S() && this.d.getScrollState() == 0 && !this.f.f.f() && this.f.f() != 0 && (n3 = this.d.getCurrentItem()) < this.f.f() && ((l3 = this.f.g(n3)) != this.e || bl) && (object = (Fragment)this.f.f.d(l3)) != null && ((Fragment)object).X()) {
                Object object2;
                this.e = l3;
                y y3 = this.f.e.o();
                ArrayList<List> arrayList = new ArrayList<List>();
                int n4 = 0;
                object = null;
                for (n3 = 0; n3 < this.f.f.k(); ++n3) {
                    l3 = this.f.f.g(n3);
                    object2 = (Fragment)this.f.f.l(n3);
                    if (!((Fragment)object2).X()) continue;
                    if (l3 != this.e) {
                        f.b b3 = f.b.f;
                        y3.p((Fragment)object2, b3);
                        arrayList.add(this.f.j.a((Fragment)object2, b3));
                    } else {
                        object = object2;
                    }
                    bl = l3 == this.e;
                    ((Fragment)object2).w1(bl);
                }
                if (object != null) {
                    object2 = f.b.g;
                    y3.p((Fragment)object, (f.b)((Object)object2));
                    arrayList.add(this.f.j.a((Fragment)object, (f.b)((Object)object2)));
                }
                if (!y3.l()) {
                    y3.h();
                    Collections.reverse(arrayList);
                    int n5 = arrayList.size();
                    for (n3 = n4; n3 < n5; ++n3) {
                        object = arrayList.get(n3);
                        object = (List)object;
                        this.f.j.b((List)object);
                    }
                }
            }
        }
    }

    public static abstract class c
    extends RecyclerView.j {
        public c() {
        }

        public /* synthetic */ c(_1 var1_1) {
            this();
        }

        @Override
        public abstract void a();

        @Override
        public final void b(int n3, int n4, Object object) {
            this.a();
        }
    }

    public static class d {
        public List a = new CopyOnWriteArrayList();

        public List a(Fragment iterator, f.b object) {
            object = new ArrayList();
            iterator = this.a.iterator();
            if (!iterator.hasNext()) {
                return object;
            }
            s.a(iterator.next());
            throw null;
        }

        public void b(List object) {
            if (!(object = object.iterator()).hasNext()) {
                return;
            }
            s.a(object.next());
            throw null;
        }

        public List c(Fragment iterator) {
            ArrayList arrayList = new ArrayList();
            iterator = this.a.iterator();
            if (!iterator.hasNext()) {
                return arrayList;
            }
            s.a(iterator.next());
            throw null;
        }

        public List d(Fragment object) {
            object = new ArrayList();
            Iterator iterator = this.a.iterator();
            if (!iterator.hasNext()) {
                return object;
            }
            s.a(iterator.next());
            throw null;
        }

        public List e(Fragment iterator) {
            ArrayList arrayList = new ArrayList();
            iterator = this.a.iterator();
            if (!iterator.hasNext()) {
                return arrayList;
            }
            s.a(iterator.next());
            throw null;
        }
    }
}

