/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.b0;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import androidx.transition.b;
import androidx.transition.c;
import java.util.ArrayList;
import java.util.List;
import m1.d;

public class a
extends b0 {
    public static /* synthetic */ void v(Runnable runnable, Transition transition, Runnable runnable2) {
        if (runnable == null) {
            transition.g();
            runnable2.run();
            return;
        }
        runnable.run();
    }

    public static boolean w(Transition transition) {
        return !b0.i(transition.G()) || !b0.i(transition.H()) || !b0.i(transition.I());
        {
        }
    }

    @Override
    public void a(Object object, View view) {
        if (object != null) {
            ((Transition)object).b(view);
        }
    }

    @Override
    public void b(Object object, ArrayList arrayList) {
        block2: {
            int n3;
            int n4;
            block3: {
                if ((object = (Transition)object) == null) break block2;
                boolean bl = object instanceof TransitionSet;
                n4 = 0;
                if (!bl) break block3;
                object = (TransitionSet)object;
                int n5 = ((TransitionSet)object).t0();
                for (n3 = n4; n3 < n5; ++n3) {
                    this.b(((TransitionSet)object).s0(n3), arrayList);
                }
                break block2;
            }
            if (a.w((Transition)object) || !b0.i(((Transition)object).J())) break block2;
            n4 = arrayList.size();
            for (n3 = 0; n3 < n4; ++n3) {
                ((Transition)object).b((View)arrayList.get(n3));
            }
        }
    }

    @Override
    public void c(ViewGroup viewGroup, Object object) {
        c.a(viewGroup, (Transition)object);
    }

    @Override
    public boolean e(Object object) {
        return object instanceof Transition;
    }

    @Override
    public Object f(Object object) {
        if (object != null) {
            return ((Transition)object).n();
        }
        return null;
    }

    @Override
    public Object j(Object object, Object object2, Object object3) {
        object = (Transition)object;
        object2 = (Transition)object2;
        object3 = (Transition)object3;
        if (object != null && object2 != null) {
            object = new TransitionSet().q0((Transition)object).q0((Transition)object2).y0(1);
        } else if (object == null) {
            object = object2 != null ? object2 : null;
        }
        if (object3 != null) {
            object2 = new TransitionSet();
            if (object != null) {
                ((TransitionSet)object2).q0((Transition)object);
            }
            ((TransitionSet)object2).q0((Transition)object3);
            return object2;
        }
        return object;
    }

    @Override
    public Object k(Object object, Object object2, Object object3) {
        TransitionSet transitionSet = new TransitionSet();
        if (object != null) {
            transitionSet.q0((Transition)object);
        }
        if (object2 != null) {
            transitionSet.q0((Transition)object2);
        }
        if (object3 != null) {
            transitionSet.q0((Transition)object3);
        }
        return transitionSet;
    }

    @Override
    public void m(Object object, View view, ArrayList arrayList) {
        ((Transition)object).a(new Transition.g(this, view, arrayList){
            public final View a;
            public final ArrayList b;
            public final a c;
            {
                this.c = a4;
                this.a = view;
                this.b = arrayList;
            }

            @Override
            public void a(Transition transition) {
                transition.a0(this);
                transition.a(this);
            }

            @Override
            public void b(Transition transition) {
            }

            @Override
            public void d(Transition transition) {
            }

            @Override
            public void e(Transition transition) {
            }

            @Override
            public void g(Transition transition) {
                transition.a0(this);
                this.a.setVisibility(8);
                int n3 = this.b.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    ((View)this.b.get(i3)).setVisibility(0);
                }
            }
        });
    }

    @Override
    public void n(Object object, Object object2, ArrayList arrayList, Object object3, ArrayList arrayList2, Object object4, ArrayList arrayList3) {
        ((Transition)object).a(new b(this, object2, arrayList, object3, arrayList2, object4, arrayList3){
            public final Object a;
            public final ArrayList b;
            public final Object c;
            public final ArrayList d;
            public final Object e;
            public final ArrayList f;
            public final a g;
            {
                this.g = a4;
                this.a = object;
                this.b = arrayList;
                this.c = object2;
                this.d = arrayList2;
                this.e = object3;
                this.f = arrayList3;
            }

            @Override
            public void a(Transition object) {
                object = this.a;
                if (object != null) {
                    this.g.x(object, this.b, null);
                }
                if ((object = this.c) != null) {
                    this.g.x(object, this.d, null);
                }
                if ((object = this.e) != null) {
                    this.g.x(object, this.f, null);
                }
            }

            @Override
            public void g(Transition transition) {
                transition.a0(this);
            }
        });
    }

    @Override
    public void o(Object object, Rect rect) {
        if (object != null) {
            ((Transition)object).g0(new Transition.f(this, rect){
                public final Rect a;
                public final a b;
                {
                    this.b = a4;
                    this.a = rect;
                }

                @Override
                public Rect a(Transition transition) {
                    transition = this.a;
                    if (transition != null && !transition.isEmpty()) {
                        return this.a;
                    }
                    return null;
                }
            });
        }
    }

    @Override
    public void p(Object object, View view) {
        if (view != null) {
            object = (Transition)object;
            Rect rect = new Rect();
            this.h(view, rect);
            ((Transition)object).g0(new Transition.f(this, rect){
                public final Rect a;
                public final a b;
                {
                    this.b = a4;
                    this.a = rect;
                }

                @Override
                public Rect a(Transition transition) {
                    return this.a;
                }
            });
        }
    }

    @Override
    public void q(Fragment fragment, Object object, k0.a a4, Runnable runnable) {
        this.y(fragment, object, a4, null, runnable);
    }

    @Override
    public void s(Object object, View view, ArrayList arrayList) {
        object = (TransitionSet)object;
        List list = ((Transition)object).J();
        list.clear();
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            b0.d(list, (View)arrayList.get(i3));
        }
        list.add(view);
        arrayList.add(view);
        this.b(object, arrayList);
    }

    @Override
    public void t(Object object, ArrayList arrayList, ArrayList arrayList2) {
        if ((object = (TransitionSet)object) != null) {
            ((Transition)object).J().clear();
            ((Transition)object).J().addAll(arrayList2);
            this.x(object, arrayList, arrayList2);
        }
    }

    @Override
    public Object u(Object object) {
        if (object == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.q0((Transition)object);
        return transitionSet;
    }

    public void x(Object object, ArrayList arrayList, ArrayList arrayList2) {
        block4: {
            int n3;
            int n4;
            Transition transition;
            block3: {
                transition = (Transition)object;
                boolean bl = transition instanceof TransitionSet;
                if (!bl) break block3;
                object = (TransitionSet)transition;
                n4 = ((TransitionSet)object).t0();
                for (n3 = 0; n3 < n4; ++n3) {
                    this.x(((TransitionSet)object).s0(n3), arrayList, arrayList2);
                }
                break block4;
            }
            if (a.w(transition) || (object = transition.J()).size() != arrayList.size() || !object.containsAll(arrayList)) break block4;
            n3 = arrayList2 == null ? 0 : arrayList2.size();
            for (n4 = 0; n4 < n3; ++n4) {
                transition.b((View)arrayList2.get(n4));
            }
            for (n3 = arrayList.size() - 1; n3 >= 0; --n3) {
                transition.b0((View)arrayList.get(n3));
            }
        }
    }

    public void y(Fragment object, Object object2, k0.a a4, Runnable runnable, Runnable runnable2) {
        object = (Transition)object2;
        a4.b(new d(runnable, (Transition)object, runnable2));
        ((Transition)object).a(new Transition.g(this, runnable2){
            public final Runnable a;
            public final a b;
            {
                this.b = a4;
                this.a = runnable;
            }

            @Override
            public void a(Transition transition) {
            }

            @Override
            public void b(Transition transition) {
            }

            @Override
            public void d(Transition transition) {
            }

            @Override
            public void e(Transition transition) {
            }

            @Override
            public void g(Transition transition) {
                this.a.run();
            }
        });
    }
}

