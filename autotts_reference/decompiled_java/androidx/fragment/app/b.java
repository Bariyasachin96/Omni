/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.b0;
import androidx.fragment.app.e0;
import androidx.fragment.app.h;
import androidx.fragment.app.z;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import k0.a;
import o0.c1;
import o0.i0;
import o0.x0;

public class b
extends e0 {
    public b(ViewGroup viewGroup) {
        super(viewGroup);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void f(List object, boolean bl) {
        int n3;
        Object object2;
        Object object3;
        Object object4 = object.iterator();
        Object object5 = null;
        Object object6 = null;
        while (object4.hasNext()) {
            object3 = (e0.e)object4.next();
            object2 = e0.e.c.c(((e0.e)object3).f().K);
            n3 = androidx.fragment.app.b$a.a[((e0.e)object3).e().ordinal()];
            if (n3 != 1 && n3 != 2 && n3 != 3) {
                if (n3 != 4 || object2 == e0.e.c.d) continue;
                object6 = object3;
                continue;
            }
            if (object2 != e0.e.c.d || object5 != null) continue;
            object5 = object3;
        }
        if (FragmentManager.I0(2)) {
            Objects.toString(object5);
            Objects.toString(object6);
        }
        object4 = new ArrayList();
        object2 = new ArrayList();
        object3 = new ArrayList(object);
        this.y((List)object);
        object = object.iterator();
        while (true) {
            boolean bl2 = object.hasNext();
            boolean bl3 = false;
            if (!bl2) break;
            e0.e e3 = (e0.e)object.next();
            k0.a a4 = new k0.a();
            e3.j(a4);
            object4.add(new k(e3, a4, bl));
            a4 = new k0.a();
            e3.j(a4);
            if (bl ? e3 == object5 : e3 == object6) {
                bl3 = true;
            }
            object2.add(new m(e3, a4, bl, bl3));
            e3.a(new Runnable(this, (List)object3, e3){
                public final List c;
                public final e0.e d;
                public final b e;
                {
                    this.e = b3;
                    this.c = list;
                    this.d = e3;
                }

                @Override
                public void run() {
                    if (this.c.contains(this.d)) {
                        this.c.remove(this.d);
                        this.e.s(this.d);
                    }
                }
            });
        }
        object = this.x((List)object2, (List)object3, bl, (e0.e)object5, (e0.e)object6);
        this.w((List)object4, (List)object3, object.containsValue(Boolean.TRUE), (Map)object);
        int n4 = ((ArrayList)object3).size();
        for (n3 = 0; n3 < n4; ++n3) {
            object = ((ArrayList)object3).get(n3);
            this.s((e0.e)object);
        }
        object3.clear();
        if (FragmentManager.I0(2)) {
            Objects.toString(object5);
            Objects.toString(object6);
        }
    }

    public void s(e0.e e3) {
        View view = e3.f().K;
        e3.e().a(view);
    }

    public void t(ArrayList arrayList, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            if (c1.a(viewGroup)) {
                if (!arrayList.contains(view)) {
                    arrayList.add(viewGroup);
                    return;
                }
            } else {
                int n3 = viewGroup.getChildCount();
                for (int i3 = 0; i3 < n3; ++i3) {
                    view = viewGroup.getChildAt(i3);
                    if (view.getVisibility() != 0) continue;
                    this.t(arrayList, view);
                }
            }
        } else if (!arrayList.contains(view)) {
            arrayList.add(view);
        }
    }

    public void u(Map map, View view) {
        String string = x0.F(view);
        if (string != null) {
            map.put(string, view);
        }
        if (view instanceof ViewGroup) {
            string = (ViewGroup)view;
            int n3 = string.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                view = string.getChildAt(i3);
                if (view.getVisibility() != 0) continue;
                this.u(map, view);
            }
        }
    }

    public void v(o.a object, Collection collection) {
        object = ((o.a)object).entrySet().iterator();
        while (object.hasNext()) {
            if (collection.contains(x0.F((View)((Map.Entry)object.next()).getValue()))) continue;
            object.remove();
        }
    }

    public final void w(List list, List object, boolean bl, Map object2) {
        Object object3 = this.m();
        Context context = object3.getContext();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Object object4 = list.iterator();
        int n3 = 0;
        boolean bl2 = false;
        list = object3;
        while (object4.hasNext()) {
            object3 = (k)object4.next();
            if (((l)object3).d()) {
                ((l)object3).a();
                continue;
            }
            h.a a4 = ((k)object3).e(context);
            if (a4 == null) {
                ((l)object3).a();
                continue;
            }
            a4 = a4.b;
            if (a4 == null) {
                arrayList.add(object3);
                continue;
            }
            e0.e e3 = ((l)object3).b();
            Fragment fragment = e3.f();
            if (Boolean.TRUE.equals(object2.get(e3))) {
                if (FragmentManager.I0(2)) {
                    Objects.toString(fragment);
                }
                ((l)object3).a();
                continue;
            }
            boolean bl3 = e3.e() == e0.e.c.e;
            if (bl3) {
                object.remove(e3);
            }
            View view = fragment.K;
            list.startViewTransition(view);
            fragment = new AnimatorListenerAdapter(this, (ViewGroup)list, view, bl3, e3, (k)object3){
                public final ViewGroup a;
                public final View b;
                public final boolean c;
                public final e0.e d;
                public final k e;
                public final b f;
                {
                    this.f = b3;
                    this.a = viewGroup;
                    this.b = view;
                    this.c = bl;
                    this.d = e3;
                    this.e = k3;
                }

                public void onAnimationEnd(Animator animator) {
                    this.a.endViewTransition(this.b);
                    if (this.c) {
                        this.d.e().a(this.b);
                    }
                    this.e.a();
                    if (FragmentManager.I0(2)) {
                        Objects.toString(this.d);
                    }
                }
            };
            a4.addListener((Animator.AnimatorListener)fragment);
            a4.setTarget(view);
            a4.start();
            if (FragmentManager.I0(2)) {
                ((Object)e3).toString();
            }
            ((l)object3).c().b(new a.a(this, (Animator)a4, e3){
                public final Animator a;
                public final e0.e b;
                public final b c;
                {
                    this.c = b3;
                    this.a = animator;
                    this.b = e3;
                }

                @Override
                public void onCancel() {
                    this.a.end();
                    if (FragmentManager.I0(2)) {
                        Objects.toString(this.b);
                    }
                }
            });
            bl2 = true;
        }
        int n4 = arrayList.size();
        while (n3 < n4) {
            object = arrayList.get(n3);
            ++n3;
            object2 = (k)object;
            object = ((l)object2).b();
            object3 = ((e0.e)object).f();
            if (bl) {
                if (FragmentManager.I0(2)) {
                    Objects.toString(object3);
                }
                ((l)object2).a();
                continue;
            }
            if (bl2) {
                if (FragmentManager.I0(2)) {
                    Objects.toString(object3);
                }
                ((l)object2).a();
                continue;
            }
            object3 = ((Fragment)object3).K;
            object4 = (Animation)n0.h.g(((h.a)n0.h.g((Object)((k)object2).e((Context)context))).a);
            if (((e0.e)object).e() != e0.e.c.c) {
                object3.startAnimation(object4);
                ((l)object2).a();
            } else {
                list.startViewTransition((View)object3);
                object4 = new h.b((Animation)object4, (ViewGroup)list, (View)object3);
                object4.setAnimationListener(new Animation.AnimationListener(this, (e0.e)object, (ViewGroup)list, (View)object3, (k)object2){
                    public final e0.e a;
                    public final ViewGroup b;
                    public final View c;
                    public final k d;
                    public final b e;
                    {
                        this.e = b3;
                        this.a = e3;
                        this.b = viewGroup;
                        this.c = view;
                        this.d = k3;
                    }

                    public void onAnimationEnd(Animation animation) {
                        this.b.post(new Runnable(this){
                            public final e c;
                            {
                                this.c = e3;
                            }

                            @Override
                            public void run() {
                                e e3 = this.c;
                                e3.b.endViewTransition(e3.c);
                                this.c.d.a();
                            }
                        });
                        if (FragmentManager.I0(2)) {
                            Objects.toString(this.a);
                        }
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                        if (FragmentManager.I0(2)) {
                            Objects.toString(this.a);
                        }
                    }
                });
                object3.startAnimation(object4);
                if (FragmentManager.I0(2)) {
                    object.toString();
                }
            }
            ((l)object2).c().b(new a.a(this, (View)object3, (ViewGroup)list, (k)object2, (e0.e)object){
                public final View a;
                public final ViewGroup b;
                public final k c;
                public final e0.e d;
                public final b e;
                {
                    this.e = b3;
                    this.a = view;
                    this.b = viewGroup;
                    this.c = k3;
                    this.d = e3;
                }

                @Override
                public void onCancel() {
                    this.a.clearAnimation();
                    this.b.endViewTransition(this.a);
                    this.c.a();
                    if (FragmentManager.I0(2)) {
                        Objects.toString(this.d);
                    }
                }
            });
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final Map x(List object, List object2, boolean bl, e0.e e3, e0.e e4) {
        e0.e e5;
        Object object3;
        int n3;
        Object object4;
        Object object5;
        Object object6;
        Object object7;
        Object object8;
        b b3 = this;
        HashMap<e0.e, Object> hashMap = new HashMap<e0.e, Object>();
        Object object9 = object.iterator();
        b0 b02 = null;
        while (object9.hasNext()) {
            object8 = (m)object9.next();
            if (((l)object8).d()) continue;
            object7 = ((m)object8).e();
            if (b02 == null) {
                b02 = object7;
                continue;
            }
            if (object7 == null || b02 == object7) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("Mixing framework transitions and AndroidX transitions is not allowed. Fragment ");
            ((StringBuilder)object).append(((l)object8).b().f());
            ((StringBuilder)object).append(" returned Transition ");
            ((StringBuilder)object).append(((m)object8).h());
            ((StringBuilder)object).append(" which uses a different Transition  type than other Fragments.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (b02 == null) {
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (m)object.next();
                hashMap.put(((l)object2).b(), Boolean.FALSE);
                ((l)object2).a();
            }
            return hashMap;
        }
        Object object10 = new View(b3.m().getContext());
        Rect rect = new Rect();
        Object object11 = new ArrayList();
        object8 = new ArrayList();
        o.a a4 = new o.a();
        Object object12 = object.iterator();
        object9 = null;
        object7 = null;
        int n4 = 0;
        while (object12.hasNext()) {
            int n5;
            object6 = (m)object12.next();
            if (!((m)object6).i() || e3 == null || e4 == null) continue;
            object9 = b02.u(b02.f(((m)object6).g()));
            object6 = e4.f().O();
            object5 = e3.f().O();
            object4 = e3.f().P();
            for (n3 = 0; n3 < ((ArrayList)object4).size(); ++n3) {
                n5 = ((ArrayList)object6).indexOf(((ArrayList)object4).get(n3));
                if (n5 == -1) continue;
                ((ArrayList)object6).set(n5, (String)((ArrayList)object5).get(n3));
            }
            object5 = e4.f().P();
            if (!bl) {
                e3.f().x();
                e4.f().u();
            } else {
                e3.f().u();
                e4.f().x();
            }
            n5 = ((ArrayList)object6).size();
            for (n3 = 0; n3 < n5; ++n3) {
                a4.put((String)((ArrayList)object6).get(n3), (String)((ArrayList)object5).get(n3));
            }
            if (FragmentManager.I0(2)) {
                n5 = ((ArrayList)object5).size();
                for (n3 = 0; n3 < n5; ++n3) {
                    object4 = ((ArrayList)object5).get(n3);
                    object4 = (String)object4;
                }
                n5 = ((ArrayList)object6).size();
                for (n3 = 0; n3 < n5; ++n3) {
                    object4 = ((ArrayList)object6).get(n3);
                    object4 = (String)object4;
                }
            }
            object3 = new o.a();
            b3.u((Map)object3, e3.f().K);
            ((o.a)object3).n((Collection)object6);
            a4.n(((o.a)object3).keySet());
            object4 = new o.a();
            b3.u((Map)object4, e4.f().K);
            ((o.a)object4).n((Collection)object5);
            ((o.a)object4).n(a4.values());
            z.c(a4, (o.a)object4);
            b3.v((o.a)object3, a4.keySet());
            b3.v((o.a)object4, a4.values());
            if (a4.isEmpty()) {
                ((ArrayList)object11).clear();
                ((ArrayList)object8).clear();
                object9 = null;
                continue;
            }
            z.a(e4.f(), e3.f(), bl, (o.a)object3, true);
            e5 = this.m();
            b3 = this;
            i0.a((View)e5, new Runnable(b3, e4, e3, bl, (o.a)object4){
                public final e0.e c;
                public final e0.e d;
                public final boolean e;
                public final o.a f;
                public final b g;
                {
                    this.g = b3;
                    this.c = e3;
                    this.d = e4;
                    this.e = bl;
                    this.f = a4;
                }

                @Override
                public void run() {
                    z.a(this.c.f(), this.d.f(), this.e, this.f, false);
                }
            });
            ((ArrayList)object11).addAll(((o.a)object3).values());
            if (!((ArrayList)object6).isEmpty()) {
                object7 = (View)((o.a)object3).get((String)((ArrayList)object6).get(0));
                b02.p(object9, (View)object7);
            }
            ((ArrayList)object8).addAll(((o.a)object4).values());
            n3 = n4;
            if (!((ArrayList)object5).isEmpty()) {
                object6 = (View)((o.a)object4).get((String)((ArrayList)object5).get(0));
                n3 = n4;
                if (object6 != null) {
                    i0.a((View)b3.m(), new Runnable(b3, b02, (View)object6, rect){
                        public final b0 c;
                        public final View d;
                        public final Rect e;
                        public final b f;
                        {
                            this.f = b3;
                            this.c = b02;
                            this.d = view;
                            this.e = rect;
                        }

                        @Override
                        public void run() {
                            this.c.h(this.d, this.e);
                        }
                    });
                    n3 = 1;
                }
            }
            b02.s(object9, (View)object10, (ArrayList)object11);
            b02.n(object9, null, null, null, null, object9, (ArrayList)object8);
            object6 = Boolean.TRUE;
            hashMap.put(e3, object6);
            hashMap.put(e4, object6);
            n4 = n3;
        }
        object5 = object10;
        object12 = object11;
        object11 = object8;
        object10 = new ArrayList();
        object3 = object.iterator();
        object4 = null;
        object8 = null;
        object6 = object7;
        object7 = object4;
        while (object3.hasNext()) {
            object4 = (m)object3.next();
            if (((l)object4).d()) {
                hashMap.put(((l)object4).b(), Boolean.FALSE);
                ((l)object4).a();
                continue;
            }
            Object object13 = b02.f(((m)object4).h());
            e5 = ((l)object4).b();
            n3 = object9 != null && (e5 == e3 || e5 == e4) ? 1 : 0;
            if (object13 == null) {
                if (n3 != 0) continue;
                hashMap.put(e5, Boolean.FALSE);
                ((l)object4).a();
                continue;
            }
            ArrayList arrayList = new ArrayList();
            b3.t(arrayList, e5.f().K);
            if (n3 != 0) {
                if (e5 == e3) {
                    arrayList.removeAll((Collection<?>)object12);
                } else {
                    arrayList.removeAll((Collection<?>)object11);
                }
            }
            if (arrayList.isEmpty()) {
                b02.a(object13, (View)object5);
            } else {
                b02.b(object13, arrayList);
                b02.n(object13, object13, arrayList, null, null, null, null);
                if (e5.e() == e0.e.c.e) {
                    object2.remove(e5);
                    ArrayList arrayList2 = new ArrayList(arrayList);
                    arrayList2.remove(e5.f().K);
                    b02.m(object13, e5.f().K, arrayList2);
                    i0.a((View)b3.m(), new Runnable(b3, arrayList){
                        public final ArrayList c;
                        public final b d;
                        {
                            this.d = b3;
                            this.c = arrayList;
                        }

                        @Override
                        public void run() {
                            z.d(this.c, 4);
                        }
                    });
                }
            }
            if (e5.e() == e0.e.c.d) {
                ((ArrayList)object10).addAll(arrayList);
                if (n4 != 0) {
                    b02.o(object13, rect);
                }
            } else {
                b02.p(object13, (View)object6);
            }
            hashMap.put(e5, Boolean.TRUE);
            if (((m)object4).j()) {
                object4 = b02.k(object7, object13, null);
                object7 = object8;
                object8 = object4;
            } else {
                object4 = b02.k(object8, object13, null);
                object8 = object7;
                object7 = object4;
            }
            object4 = object7;
            object7 = object8;
            object8 = object4;
        }
        object2 = b02.j(object7, object8, object9);
        if (object2 == null) {
            return hashMap;
        }
        object8 = object.iterator();
        while (object8.hasNext()) {
            object7 = (m)object8.next();
            if (((l)object7).d()) continue;
            object = ((m)object7).h();
            e0.e e6 = ((l)object7).b();
            n4 = object9 != null && (e6 == e3 || e6 == e4) ? 1 : 0;
            if (object == null && n4 == 0) continue;
            if (!x0.O((View)b3.m())) {
                if (FragmentManager.I0(2)) {
                    Objects.toString(b3.m());
                    Objects.toString(e6);
                }
                ((l)object7).a();
                continue;
            }
            b02.q(((l)object7).b().f(), object2, ((l)object7).c(), new Runnable(b3, (m)object7, e6){
                public final m c;
                public final e0.e d;
                public final b e;
                {
                    this.e = b3;
                    this.c = m3;
                    this.d = e3;
                }

                @Override
                public void run() {
                    this.c.a();
                    if (FragmentManager.I0(2)) {
                        Objects.toString(this.d);
                    }
                }
            });
        }
        if (!x0.O((View)b3.m())) {
            return hashMap;
        }
        z.d((ArrayList)object10, 4);
        object = b02.l((ArrayList)object11);
        if (FragmentManager.I0(2)) {
            n3 = ((ArrayList)object12).size();
            for (n4 = 0; n4 < n3; ++n4) {
                e3 = ((ArrayList)object12).get(n4);
                e3 = (View)e3;
                Objects.toString(e3);
                x0.F((View)e3);
            }
            n3 = ((ArrayList)object11).size();
            for (n4 = 0; n4 < n3; ++n4) {
                e3 = ((ArrayList)object11).get(n4);
                e3 = (View)e3;
                Objects.toString(e3);
                x0.F((View)e3);
            }
        }
        b02.c(b3.m(), object2);
        b02.r((View)b3.m(), (ArrayList)object12, (ArrayList)object11, (ArrayList)object, a4);
        z.d((ArrayList)object10, 0);
        b02.t(object9, (ArrayList)object12, (ArrayList)object11);
        return hashMap;
    }

    public final void y(List object) {
        Fragment fragment = ((e0.e)object.get(object.size() - 1)).f();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (e0.e)iterator.next();
            ((e0.e)object).f().N.c = fragment.N.c;
            ((e0.e)object).f().N.d = fragment.N.d;
            ((e0.e)object).f().N.e = fragment.N.e;
            ((e0.e)object).f().N.f = fragment.N.f;
        }
    }

    public static class k
    extends l {
        public boolean c;
        public boolean d = false;
        public h.a e;

        public k(e0.e e3, k0.a a4, boolean bl) {
            super(e3, a4);
            this.c = bl;
        }

        public h.a e(Context object) {
            if (this.d) {
                return this.e;
            }
            Fragment fragment = this.b().f();
            boolean bl = this.b().e() == e0.e.c.d;
            object = h.b(object, fragment, bl, this.c);
            this.e = object;
            this.d = true;
            return object;
        }
    }

    public static abstract class l {
        public final e0.e a;
        public final k0.a b;

        public l(e0.e e3, k0.a a4) {
            this.a = e3;
            this.b = a4;
        }

        public void a() {
            this.a.d(this.b);
        }

        public e0.e b() {
            return this.a;
        }

        public k0.a c() {
            return this.b;
        }

        public boolean d() {
            e0.e.c c3;
            e0.e.c c4;
            e0.e.c c5 = e0.e.c.c(this.a.f().K);
            return c5 == (c4 = this.a.e()) || c5 != (c3 = e0.e.c.d) && c4 != c3;
            {
            }
        }
    }

    public static class m
    extends l {
        public final Object c;
        public final boolean d;
        public final Object e;

        public m(e0.e e3, k0.a object, boolean bl, boolean bl2) {
            super(e3, (k0.a)object);
            if (e3.e() == e0.e.c.d) {
                object = bl ? e3.f().J() : e3.f().s();
                this.c = object;
                boolean bl3 = bl ? e3.f().l() : e3.f().k();
                this.d = bl3;
            } else {
                object = bl ? e3.f().L() : e3.f().w();
                this.c = object;
                this.d = true;
            }
            if (bl2) {
                if (bl) {
                    this.e = e3.f().N();
                    return;
                }
                this.e = e3.f().M();
                return;
            }
            this.e = null;
        }

        public b0 e() {
            b0 b02 = this.f(this.c);
            Object object = this.f(this.e);
            if (b02 != null && object != null && b02 != object) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Mixing framework transitions and AndroidX transitions is not allowed. Fragment ");
                ((StringBuilder)object).append(this.b().f());
                ((StringBuilder)object).append(" returned Transition ");
                ((StringBuilder)object).append(this.c);
                ((StringBuilder)object).append(" which uses a different Transition  type than its shared element transition ");
                ((StringBuilder)object).append(this.e);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (b02 != null) {
                return b02;
            }
            return object;
        }

        public final b0 f(Object object) {
            if (object == null) {
                return null;
            }
            Object object2 = z.a;
            if (object2 != null && ((b0)object2).e(object)) {
                return object2;
            }
            object2 = z.b;
            if (object2 != null && ((b0)object2).e(object)) {
                return object2;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Transition ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" for fragment ");
            ((StringBuilder)object2).append(this.b().f());
            ((StringBuilder)object2).append(" is not a valid framework Transition or AndroidX Transition");
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }

        public Object g() {
            return this.e;
        }

        public Object h() {
            return this.c;
        }

        public boolean i() {
            return this.e != null;
        }

        public boolean j() {
            return this.d;
        }
    }
}

