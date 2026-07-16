/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.b;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import m1.p;

public abstract class c {
    public static Transition a = new AutoTransition();
    public static ThreadLocal b = new ThreadLocal();
    public static ArrayList c = new ArrayList();

    public static void a(ViewGroup viewGroup, Transition transition) {
        if (!c.contains(viewGroup) && viewGroup.isLaidOut()) {
            c.add(viewGroup);
            Transition transition2 = transition;
            if (transition == null) {
                transition2 = a;
            }
            transition = transition2.n();
            androidx.transition.c.d(viewGroup, transition);
            p.b(viewGroup, null);
            androidx.transition.c.c(viewGroup, transition);
        }
    }

    public static o.a b() {
        Object object = (WeakReference)b.get();
        if (object != null && (object = (o.a)((Reference)object).get()) != null) {
            return object;
        }
        object = new o.a();
        WeakReference<Object> weakReference = new WeakReference<Object>(object);
        b.set(weakReference);
        return object;
    }

    public static void c(ViewGroup viewGroup, Transition object) {
        if (object != null && viewGroup != null) {
            object = new a((Transition)object, viewGroup);
            viewGroup.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
            viewGroup.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
        }
    }

    public static void d(ViewGroup viewGroup, Transition transition) {
        ArrayList arrayList = (ArrayList)androidx.transition.c.b().get(viewGroup);
        if (arrayList != null && arrayList.size() > 0) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object e3 = arrayList.get(i3);
                ((Transition)e3).Y((View)viewGroup);
            }
        }
        if (transition != null) {
            transition.l(viewGroup, true);
        }
        p.a(viewGroup);
    }

    public static class a
    implements ViewTreeObserver.OnPreDrawListener,
    View.OnAttachStateChangeListener {
        public Transition c;
        public ViewGroup d;

        public a(Transition transition, ViewGroup viewGroup) {
            this.c = transition;
            this.d = viewGroup;
        }

        public final void a() {
            this.d.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
            this.d.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }

        public boolean onPreDraw() {
            Object object;
            this.a();
            if (!c.remove(this.d)) {
                return true;
            }
            o.a a4 = androidx.transition.c.b();
            ViewGroup viewGroup = (ViewGroup)a4.get(this.d);
            ArrayList arrayList = null;
            if (viewGroup == null) {
                object = new ArrayList();
                a4.put(this.d, object);
            } else {
                object = viewGroup;
                if (viewGroup.size() > 0) {
                    arrayList = new ArrayList(viewGroup);
                    object = viewGroup;
                }
            }
            object.add(this.c);
            this.c.a(new b(this, a4){
                public final o.a a;
                public final a b;
                {
                    this.b = a4;
                    this.a = a5;
                }

                @Override
                public void g(Transition transition) {
                    ((ArrayList)this.a.get(this.b.d)).remove(transition);
                    transition.a0(this);
                }
            });
            object = this.c;
            viewGroup = this.d;
            object.l(viewGroup, false);
            if (arrayList != null) {
                int n3 = arrayList.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    object = arrayList.get(i3);
                    ((Transition)object).c0((View)this.d);
                }
            }
            this.c.Z(this.d);
            return true;
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View object) {
            this.a();
            c.remove(this.d);
            ArrayList arrayList = (ArrayList)androidx.transition.c.b().get(this.d);
            if (arrayList != null && arrayList.size() > 0) {
                int n3 = arrayList.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    object = arrayList.get(i3);
                    ((Transition)object).c0((View)this.d);
                }
            }
            this.c.m(true);
        }
    }
}

