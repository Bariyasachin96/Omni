/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.transition.Transition
 *  android.transition.Transition$EpicenterCallback
 *  android.transition.Transition$TransitionListener
 *  android.transition.TransitionManager
 *  android.transition.TransitionSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.b0;
import java.util.ArrayList;
import java.util.List;
import k0.a;

public class a0
extends b0 {
    public static boolean v(Transition transition) {
        return !b0.i(transition.getTargetIds()) || !b0.i(transition.getTargetNames()) || !b0.i(transition.getTargetTypes());
        {
        }
    }

    @Override
    public void a(Object object, View view) {
        if (object != null) {
            ((Transition)object).addTarget(view);
        }
    }

    @Override
    public void b(Object object, ArrayList arrayList) {
        block2: {
            int n3;
            block3: {
                if ((object = (Transition)object) == null) break block2;
                boolean bl = object instanceof TransitionSet;
                int n4 = 0;
                if (!bl) break block3;
                object = (TransitionSet)object;
                n4 = object.getTransitionCount();
                for (n3 = 0; n3 < n4; ++n3) {
                    this.b(object.getTransitionAt(n3), arrayList);
                }
                break block2;
            }
            if (a0.v((Transition)object) || !b0.i(object.getTargets())) break block2;
            int n5 = arrayList.size();
            for (n3 = n4; n3 < n5; ++n3) {
                object.addTarget((View)arrayList.get(n3));
            }
        }
    }

    @Override
    public void c(ViewGroup viewGroup, Object object) {
        TransitionManager.beginDelayedTransition((ViewGroup)viewGroup, (Transition)((Transition)object));
    }

    @Override
    public boolean e(Object object) {
        return object instanceof Transition;
    }

    @Override
    public Object f(Object object) {
        if (object != null) {
            return ((Transition)object).clone();
        }
        return null;
    }

    @Override
    public Object j(Object object, Object object2, Object object3) {
        object = (Transition)object;
        object2 = (Transition)object2;
        object3 = (Transition)object3;
        if (object != null && object2 != null) {
            object = new TransitionSet().addTransition((Transition)object).addTransition((Transition)object2).setOrdering(1);
        } else if (object == null) {
            object = object2 != null ? object2 : null;
        }
        if (object3 != null) {
            object2 = new TransitionSet();
            if (object != null) {
                object2.addTransition((Transition)object);
            }
            object2.addTransition((Transition)object3);
            return object2;
        }
        return object;
    }

    @Override
    public Object k(Object object, Object object2, Object object3) {
        TransitionSet transitionSet = new TransitionSet();
        if (object != null) {
            transitionSet.addTransition((Transition)object);
        }
        if (object2 != null) {
            transitionSet.addTransition((Transition)object2);
        }
        if (object3 != null) {
            transitionSet.addTransition((Transition)object3);
        }
        return transitionSet;
    }

    @Override
    public void m(Object object, View view, ArrayList arrayList) {
        ((Transition)object).addListener(new Transition.TransitionListener(this, view, arrayList){
            public final View a;
            public final ArrayList b;
            public final a0 c;
            {
                this.c = a02;
                this.a = view;
                this.b = arrayList;
            }

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                f.b(transition, this);
                this.a.setVisibility(8);
                int n3 = this.b.size();
                for (int i3 = 0; i3 < n3; ++i3) {
                    ((View)this.b.get(i3)).setVisibility(0);
                }
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
                f.b(transition, this);
                f.a(transition, this);
            }
        });
    }

    @Override
    public void n(Object object, Object object2, ArrayList arrayList, Object object3, ArrayList arrayList2, Object object4, ArrayList arrayList3) {
        ((Transition)object).addListener(new Transition.TransitionListener(this, object2, arrayList, object3, arrayList2, object4, arrayList3){
            public final Object a;
            public final ArrayList b;
            public final Object c;
            public final ArrayList d;
            public final Object e;
            public final ArrayList f;
            public final a0 g;
            {
                this.g = a02;
                this.a = object;
                this.b = arrayList;
                this.c = object2;
                this.d = arrayList2;
                this.e = object3;
                this.f = arrayList3;
            }

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                f.b(transition, this);
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition object) {
                object = this.a;
                if (object != null) {
                    this.g.w(object, this.b, null);
                }
                if ((object = this.c) != null) {
                    this.g.w(object, this.d, null);
                }
                if ((object = this.e) != null) {
                    this.g.w(object, this.f, null);
                }
            }
        });
    }

    @Override
    public void o(Object object, Rect rect) {
        if (object != null) {
            ((Transition)object).setEpicenterCallback(new Transition.EpicenterCallback(this, rect){
                public final Rect a;
                public final a0 b;
                {
                    this.b = a02;
                    this.a = rect;
                }

                public Rect onGetEpicenter(Transition transition) {
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
            object.setEpicenterCallback(new Transition.EpicenterCallback(this, rect){
                public final Rect a;
                public final a0 b;
                {
                    this.b = a02;
                    this.a = rect;
                }

                public Rect onGetEpicenter(Transition transition) {
                    return this.a;
                }
            });
        }
    }

    @Override
    public void q(Fragment fragment, Object object, a a4, Runnable runnable) {
        ((Transition)object).addListener(new Transition.TransitionListener(this, runnable){
            public final Runnable a;
            public final a0 b;
            {
                this.b = a02;
                this.a = runnable;
            }

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                this.a.run();
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
            }
        });
    }

    @Override
    public void s(Object object, View view, ArrayList arrayList) {
        TransitionSet transitionSet = (TransitionSet)object;
        object = transitionSet.getTargets();
        object.clear();
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            b0.d((List)object, (View)arrayList.get(i3));
        }
        object.add(view);
        arrayList.add(view);
        this.b(transitionSet, arrayList);
    }

    @Override
    public void t(Object object, ArrayList arrayList, ArrayList arrayList2) {
        if ((object = (TransitionSet)object) != null) {
            object.getTargets().clear();
            object.getTargets().addAll(arrayList2);
            this.w(object, arrayList, arrayList2);
        }
    }

    @Override
    public Object u(Object object) {
        if (object == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition)object);
        return transitionSet;
    }

    public void w(Object object, ArrayList arrayList, ArrayList arrayList2) {
        block4: {
            List list;
            int n3;
            int n4;
            block3: {
                object = (Transition)object;
                boolean bl = object instanceof TransitionSet;
                if (!bl) break block3;
                object = (TransitionSet)object;
                n4 = object.getTransitionCount();
                for (n3 = 0; n3 < n4; ++n3) {
                    this.w(object.getTransitionAt(n3), arrayList, arrayList2);
                }
                break block4;
            }
            if (a0.v((Transition)object) || (list = object.getTargets()) == null || list.size() != arrayList.size() || !list.containsAll(arrayList)) break block4;
            n3 = arrayList2 == null ? 0 : arrayList2.size();
            for (n4 = 0; n4 < n3; ++n4) {
                object.addTarget((View)arrayList2.get(n4));
            }
            for (n3 = arrayList.size() - 1; n3 >= 0; --n3) {
                object.removeTarget((View)arrayList.get(n3));
            }
        }
    }

    public static abstract class f {
        public static void a(Transition transition, Transition.TransitionListener transitionListener) {
            transition.addListener(transitionListener);
        }

        public static void b(Transition transition, Transition.TransitionListener transitionListener) {
            transition.removeListener(transitionListener);
        }
    }
}

