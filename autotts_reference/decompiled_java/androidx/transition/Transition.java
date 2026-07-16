/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.view.InflateException
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.WindowId
 *  android.view.animation.AnimationUtils
 *  android.widget.ListView
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.transition.PathMotion;
import androidx.transition.TransitionSet;
import f0.k;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import m1.r;
import m1.s;
import m1.t;
import m1.u;
import m1.v;
import m1.w;
import m1.x;
import m1.y;
import m1.z;
import o.a;
import o.j;
import o0.x0;
import org.xmlpull.v1.XmlPullParser;

public abstract class Transition
implements Cloneable {
    public static final Animator[] L = new Animator[0];
    public static final int[] M = new int[]{2, 1, 3, 4};
    public static final PathMotion N = new PathMotion(){

        @Override
        public Path a(float f3, float f4, float f5, float f6) {
            Path path = new Path();
            path.moveTo(f3, f4);
            path.lineTo(f5, f6);
            return path;
        }
    };
    public static ThreadLocal O = new ThreadLocal();
    public Animator[] A;
    public int B = 0;
    public boolean C = false;
    public boolean D = false;
    public Transition E = null;
    public ArrayList F = null;
    public ArrayList G;
    public x H;
    public f I;
    public a J;
    public PathMotion K;
    public String c = this.getClass().getName();
    public long d = -1L;
    public long e = -1L;
    public TimeInterpolator f = null;
    public ArrayList g = new ArrayList();
    public ArrayList h = new ArrayList();
    public ArrayList i = null;
    public ArrayList j = null;
    public ArrayList k = null;
    public ArrayList l = null;
    public ArrayList m = null;
    public ArrayList n = null;
    public ArrayList o = null;
    public ArrayList p = null;
    public ArrayList q = null;
    public z r = new z();
    public z s = new z();
    public TransitionSet t = null;
    public int[] u = M;
    public ArrayList v;
    public ArrayList w;
    public g[] x;
    public boolean y = false;
    public ArrayList z = new ArrayList();

    public Transition() {
        this.A = L;
        this.G = new ArrayList();
        this.K = N;
    }

    public Transition(Context object, AttributeSet attributeSet) {
        int n3;
        this.A = L;
        this.G = new ArrayList();
        this.K = N;
        TypedArray typedArray = object.obtainStyledAttributes(attributeSet, m1.r.c);
        attributeSet = (XmlResourceParser)attributeSet;
        long l3 = f0.k.g(typedArray, (XmlPullParser)attributeSet, "duration", 1, -1);
        if (l3 >= 0L) {
            this.f0(l3);
        }
        if ((l3 = (long)f0.k.g(typedArray, (XmlPullParser)attributeSet, "startDelay", 2, -1)) > 0L) {
            this.l0(l3);
        }
        if ((n3 = f0.k.h(typedArray, (XmlPullParser)attributeSet, "interpolator", 0, 0)) > 0) {
            this.h0((TimeInterpolator)AnimationUtils.loadInterpolator((Context)object, (int)n3));
        }
        if ((object = f0.k.i(typedArray, (XmlPullParser)attributeSet, "matchOrder", 3)) != null) {
            this.i0(Transition.X((String)object));
        }
        typedArray.recycle();
    }

    public static a E() {
        a a4;
        a a5 = a4 = (a)O.get();
        if (a4 == null) {
            a5 = new a();
            O.set(a5);
        }
        return a5;
    }

    public static boolean N(int n3) {
        return n3 >= 1 && n3 <= 4;
    }

    public static boolean P(y y3, y y4, String string) {
        y3 = y3.a.get(string);
        y4 = y4.a.get(string);
        if (y3 == null && y4 == null) {
            return false;
        }
        if (y3 != null && y4 != null) {
            return ((Object)y3).equals(y4) ^ true;
        }
        return true;
    }

    public static int[] X(String object) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, ",");
        object = new int[stringTokenizer.countTokens()];
        int n3 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            Object object2;
            block8: {
                block4: {
                    block7: {
                        block6: {
                            block5: {
                                block3: {
                                    object2 = stringTokenizer.nextToken().trim();
                                    if (!"id".equalsIgnoreCase((String)object2)) break block3;
                                    object[n3] = 3;
                                    break block4;
                                }
                                if (!"instance".equalsIgnoreCase((String)object2)) break block5;
                                object[n3] = true;
                                break block4;
                            }
                            if (!"name".equalsIgnoreCase((String)object2)) break block6;
                            object[n3] = 2;
                            break block4;
                        }
                        if (!"itemId".equalsIgnoreCase((String)object2)) break block7;
                        object[n3] = 4;
                        break block4;
                    }
                    if (!((String)object2).isEmpty()) break block8;
                    object2 = new int[((Object)object).length - 1];
                    System.arraycopy(object, 0, object2, 0, n3);
                    --n3;
                    object = object2;
                }
                ++n3;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown match type in matchOrder: '");
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("'");
            throw new InflateException(((StringBuilder)object).toString());
        }
        return object;
    }

    public static void d(z z3, View view, y object) {
        z3.a.put(view, object);
        int n3 = view.getId();
        if (n3 >= 0) {
            if (z3.b.indexOfKey(n3) >= 0) {
                z3.b.put(n3, null);
            } else {
                z3.b.put(n3, (Object)view);
            }
        }
        if ((object = x0.F(view)) != null) {
            if (z3.d.containsKey(object)) {
                z3.d.put(object, (Object)null);
            } else {
                z3.d.put(object, view);
            }
        }
        if (view.getParent() instanceof ListView && (object = (ListView)view.getParent()).getAdapter().hasStableIds()) {
            long l3 = object.getItemIdAtPosition(object.getPositionForView(view));
            if (z3.c.e(l3) >= 0) {
                view = (View)z3.c.d(l3);
                if (view != null) {
                    view.setHasTransientState(false);
                    z3.c.h(l3, null);
                    return;
                }
            } else {
                view.setHasTransientState(true);
                z3.c.h(l3, view);
            }
        }
    }

    public static boolean e(int[] nArray, int n3) {
        int n4 = nArray[n3];
        for (int i3 = 0; i3 < n3; ++i3) {
            if (nArray[i3] != n4) continue;
            return true;
        }
        return false;
    }

    public String A() {
        return this.c;
    }

    public PathMotion B() {
        return this.K;
    }

    public x C() {
        return this.H;
    }

    public final Transition D() {
        TransitionSet transitionSet = this.t;
        if (transitionSet != null) {
            return transitionSet.D();
        }
        return this;
    }

    public long F() {
        return this.d;
    }

    public List G() {
        return this.g;
    }

    public List H() {
        return this.i;
    }

    public List I() {
        return this.j;
    }

    public List J() {
        return this.h;
    }

    public String[] K() {
        return null;
    }

    public y L(View view, boolean bl) {
        Object object = this.t;
        if (object != null) {
            return ((Transition)object).L(view, bl);
        }
        object = bl ? this.r : this.s;
        return (y)((z)object).a.get(view);
    }

    public boolean M(y y3, y y4) {
        block4: {
            if (y3 == null || y4 == null) break block4;
            Object object = this.K();
            if (object != null) {
                int n3 = ((String[])object).length;
                for (int i3 = 0; i3 < n3; ++i3) {
                    if (!Transition.P(y3, y4, (String)object[i3])) continue;
                    return true;
                }
            } else {
                object = y3.a.keySet().iterator();
                while (object.hasNext()) {
                    if (!Transition.P(y3, y4, (String)object.next())) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean O(View view) {
        int n3;
        int n4 = view.getId();
        ArrayList arrayList = this.k;
        if (arrayList != null && arrayList.contains(n4)) {
            return false;
        }
        arrayList = this.l;
        if (arrayList != null && arrayList.contains(view)) {
            return false;
        }
        arrayList = this.m;
        if (arrayList != null) {
            int n5 = arrayList.size();
            for (n3 = 0; n3 < n5; ++n3) {
                if (!((Class)this.m.get(n3)).isInstance(view)) continue;
                return false;
            }
        }
        if (this.n != null && x0.F(view) != null && this.n.contains(x0.F(view))) {
            return false;
        }
        if (this.g.size() == 0 && this.h.size() == 0 && ((arrayList = this.j) == null || arrayList.isEmpty()) && ((arrayList = this.i) == null || arrayList.isEmpty())) {
            return true;
        }
        if (!this.g.contains(n4) && !this.h.contains(view)) {
            arrayList = this.i;
            if (arrayList != null && arrayList.contains(x0.F(view))) {
                return true;
            }
            if (this.j != null) {
                for (n3 = 0; n3 < this.j.size(); ++n3) {
                    if (!((Class)this.j.get(n3)).isInstance(view)) continue;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public final void Q(a a4, a a5, SparseArray sparseArray, SparseArray sparseArray2) {
        int n3 = sparseArray.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view;
            View view2 = (View)sparseArray.valueAt(i3);
            if (view2 == null || !this.O(view2) || (view = (View)sparseArray2.get(sparseArray.keyAt(i3))) == null || !this.O(view)) continue;
            y y3 = (y)a4.get(view2);
            y y4 = (y)a5.get(view);
            if (y3 == null || y4 == null) continue;
            this.v.add(y3);
            this.w.add(y4);
            a4.remove(view2);
            a5.remove(view);
        }
    }

    public final void R(a a4, a a5) {
        for (int i3 = a4.size() - 1; i3 >= 0; --i3) {
            Object object = (View)a4.f(i3);
            if (object == null || !this.O((View)object) || (object = (y)a5.remove(object)) == null || !this.O(object.b)) continue;
            y y3 = (y)a4.h(i3);
            this.v.add(y3);
            this.w.add(object);
        }
    }

    public final void S(a a4, a a5, j j3, j j4) {
        int n3 = j3.k();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view;
            View view2 = (View)j3.l(i3);
            if (view2 == null || !this.O(view2) || (view = (View)j4.d(j3.g(i3))) == null || !this.O(view)) continue;
            y y3 = (y)a4.get(view2);
            y y4 = (y)a5.get(view);
            if (y3 == null || y4 == null) continue;
            this.v.add(y3);
            this.w.add(y4);
            a4.remove(view2);
            a5.remove(view);
        }
    }

    public final void T(a a4, a a5, a a6, a a7) {
        int n3 = a6.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view;
            View view2 = (View)a6.j(i3);
            if (view2 == null || !this.O(view2) || (view = (View)a7.get(a6.f(i3))) == null || !this.O(view)) continue;
            y y3 = (y)a4.get(view2);
            y y4 = (y)a5.get(view);
            if (y3 == null || y4 == null) continue;
            this.v.add(y3);
            this.w.add(y4);
            a4.remove(view2);
            a5.remove(view);
        }
    }

    public final void U(z z3, z z4) {
        int[] nArray;
        a a4 = new a(z3.a);
        a a5 = new a(z4.a);
        for (int i3 = 0; i3 < (nArray = this.u).length; ++i3) {
            int n3 = nArray[i3];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) continue;
                        this.S(a4, a5, z3.c, z4.c);
                        continue;
                    }
                    this.Q(a4, a5, z3.b, z4.b);
                    continue;
                }
                this.T(a4, a5, z3.d, z4.d);
                continue;
            }
            this.R(a4, a5);
        }
        this.c(a4, a5);
    }

    public final void V(Transition transition, h h3, boolean bl) {
        Cloneable cloneable = this.E;
        if (cloneable != null) {
            ((Transition)cloneable).V(transition, h3, bl);
        }
        if ((cloneable = this.F) != null && !((ArrayList)cloneable).isEmpty()) {
            int n3 = this.F.size();
            g[] gArray = this.x;
            cloneable = gArray;
            if (gArray == null) {
                cloneable = new g[n3];
            }
            this.x = null;
            cloneable = (g[])this.F.toArray((T[])cloneable);
            for (int i3 = 0; i3 < n3; ++i3) {
                h3.a((g)((Object)cloneable[i3]), transition, bl);
                cloneable[i3] = null;
            }
            this.x = cloneable;
        }
    }

    public void W(h h3, boolean bl) {
        this.V(this, h3, bl);
    }

    public void Y(View animatorArray) {
        if (!this.D) {
            int n3 = this.z.size();
            animatorArray = this.z.toArray(this.A);
            this.A = L;
            --n3;
            while (n3 >= 0) {
                Animator animator = animatorArray[n3];
                animatorArray[n3] = null;
                animator.pause();
                --n3;
            }
            this.A = animatorArray;
            this.W(androidx.transition.Transition$h.d, false);
            this.C = true;
        }
    }

    public void Z(ViewGroup viewGroup) {
        this.v = new ArrayList();
        this.w = new ArrayList();
        this.U(this.r, this.s);
        a a4 = Transition.E();
        int n3 = a4.size();
        WindowId windowId = viewGroup.getWindowId();
        --n3;
        while (n3 >= 0) {
            d d3;
            Animator animator = (Animator)a4.f(n3);
            if (animator != null && (d3 = (d)a4.get(animator)) != null && d3.a != null && windowId.equals((Object)d3.d)) {
                y y3;
                y y4 = d3.c;
                View view = d3.a;
                y y5 = this.L(view, true);
                y y6 = y3 = this.z(view, true);
                if (y5 == null) {
                    y6 = y3;
                    if (y3 == null) {
                        y6 = (y)this.s.a.get(view);
                    }
                }
                if ((y5 != null || y6 != null) && d3.e.M(y4, y6)) {
                    d3.e.D().getClass();
                    if (!animator.isRunning() && !animator.isStarted()) {
                        a4.remove(animator);
                    } else {
                        animator.cancel();
                    }
                }
            }
            --n3;
        }
        this.p(viewGroup, this.r, this.s, this.v, this.w);
        this.e0();
    }

    public Transition a(g g3) {
        if (this.F == null) {
            this.F = new ArrayList();
        }
        this.F.add(g3);
        return this;
    }

    public Transition a0(g g3) {
        Cloneable cloneable = this.F;
        if (cloneable != null) {
            if (!((ArrayList)cloneable).remove(g3) && (cloneable = this.E) != null) {
                ((Transition)cloneable).a0(g3);
            }
            if (this.F.size() == 0) {
                this.F = null;
            }
        }
        return this;
    }

    public Transition b(View view) {
        this.h.add(view);
        return this;
    }

    public Transition b0(View view) {
        this.h.remove(view);
        return this;
    }

    public final void c(a object, a a4) {
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= ((o.r)object).size()) break;
            y y3 = (y)((o.r)object).j(n4);
            if (this.O(y3.b)) {
                this.v.add(y3);
                this.w.add(null);
            }
            ++n4;
        }
        for (int i3 = n3; i3 < a4.size(); ++i3) {
            object = (y)a4.j(i3);
            if (!this.O(((y)object).b)) continue;
            this.w.add(object);
            this.v.add(null);
        }
    }

    public void c0(View animatorArray) {
        if (this.C) {
            if (!this.D) {
                int n3 = this.z.size();
                animatorArray = this.z.toArray(this.A);
                this.A = L;
                --n3;
                while (n3 >= 0) {
                    Animator animator = animatorArray[n3];
                    animatorArray[n3] = null;
                    animator.resume();
                    --n3;
                }
                this.A = animatorArray;
                this.W(androidx.transition.Transition$h.e, false);
            }
            this.C = false;
        }
    }

    public final void d0(Animator animator, a a4) {
        if (animator != null) {
            animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, a4){
                public final a a;
                public final Transition b;
                {
                    this.b = transition;
                    this.a = a4;
                }

                public void onAnimationEnd(Animator animator) {
                    this.a.remove(animator);
                    this.b.z.remove(animator);
                }

                public void onAnimationStart(Animator animator) {
                    this.b.z.add(animator);
                }
            });
            this.f(animator);
        }
    }

    public void e0() {
        this.m0();
        a a4 = Transition.E();
        ArrayList arrayList = this.G;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Object object = arrayList.get(n4);
            int n5 = n4 + 1;
            object = (Animator)object;
            n4 = n5;
            if (!a4.containsKey(object)) continue;
            this.m0();
            this.d0((Animator)object, a4);
            n4 = n5;
        }
        this.G.clear();
        this.q();
    }

    public void f(Animator animator) {
        if (animator == null) {
            this.q();
            return;
        }
        if (this.v() >= 0L) {
            animator.setDuration(this.v());
        }
        if (this.F() >= 0L) {
            animator.setStartDelay(this.F() + animator.getStartDelay());
        }
        if (this.y() != null) {
            animator.setInterpolator(this.y());
        }
        animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final Transition a;
            {
                this.a = transition;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.q();
                animator.removeListener((Animator.AnimatorListener)this);
            }
        });
        animator.start();
    }

    public Transition f0(long l3) {
        this.e = l3;
        return this;
    }

    public void g() {
        int n3 = this.z.size();
        Animator[] animatorArray = this.z.toArray(this.A);
        this.A = L;
        --n3;
        while (n3 >= 0) {
            Animator animator = animatorArray[n3];
            animatorArray[n3] = null;
            animator.cancel();
            --n3;
        }
        this.A = animatorArray;
        this.W(androidx.transition.Transition$h.c, false);
    }

    public void g0(f f3) {
        this.I = f3;
    }

    public abstract void h(y var1);

    public Transition h0(TimeInterpolator timeInterpolator) {
        this.f = timeInterpolator;
        return this;
    }

    public final void i(View view, boolean bl) {
        if (view != null) {
            int n3 = view.getId();
            Object object = this.k;
            if (!(object != null && ((ArrayList)object).contains(n3) || (object = this.l) != null && ((ArrayList)object).contains(view))) {
                int n4;
                int n5;
                object = this.m;
                int n6 = 0;
                if (object != null) {
                    n5 = ((ArrayList)object).size();
                    for (n4 = 0; n4 < n5; ++n4) {
                        if (!((Class)this.m.get(n4)).isInstance(view)) {
                            continue;
                        }
                        break;
                    }
                } else {
                    if (view.getParent() instanceof ViewGroup) {
                        object = new y(view);
                        if (bl) {
                            this.k((y)object);
                        } else {
                            this.h((y)object);
                        }
                        ((y)object).c.add(this);
                        this.j((y)object);
                        if (bl) {
                            Transition.d(this.r, view, (y)object);
                        } else {
                            Transition.d(this.s, view, (y)object);
                        }
                    }
                    if (!(!(view instanceof ViewGroup) || (object = this.o) != null && ((ArrayList)object).contains(n3) || (object = this.p) != null && ((ArrayList)object).contains(view))) {
                        object = this.q;
                        if (object != null) {
                            n5 = ((ArrayList)object).size();
                            for (n4 = 0; n4 < n5; ++n4) {
                                if (!((Class)this.q.get(n4)).isInstance(view)) {
                                    continue;
                                }
                                break;
                            }
                        } else {
                            view = (ViewGroup)view;
                            for (n4 = n6; n4 < view.getChildCount(); ++n4) {
                                this.i(view.getChildAt(n4), bl);
                            }
                        }
                    }
                }
            }
        }
    }

    public void i0(int ... nArray) {
        if (nArray != null && nArray.length != 0) {
            for (int i3 = 0; i3 < nArray.length; ++i3) {
                if (Transition.N(nArray[i3])) {
                    if (!Transition.e(nArray, i3)) {
                        continue;
                    }
                    throw new IllegalArgumentException("matches contains a duplicate value");
                }
                throw new IllegalArgumentException("matches contains invalid value");
            }
            this.u = (int[])nArray.clone();
            return;
        }
        this.u = M;
    }

    public void j(y y3) {
        String[] stringArray;
        if (this.H != null && !y3.a.isEmpty() && (stringArray = this.H.b()) != null) {
            for (int i3 = 0; i3 < stringArray.length; ++i3) {
                if (y3.a.containsKey(stringArray[i3])) continue;
                this.H.a(y3);
                return;
            }
        }
    }

    public void j0(PathMotion pathMotion) {
        if (pathMotion == null) {
            this.K = N;
            return;
        }
        this.K = pathMotion;
    }

    public abstract void k(y var1);

    public void k0(x x3) {
        this.H = x3;
    }

    public void l(ViewGroup object, boolean bl) {
        Object object2;
        Object object3;
        this.m(bl);
        int n3 = this.g.size();
        int n4 = 0;
        if (n3 <= 0 && this.h.size() <= 0 || (object3 = this.i) != null && !((ArrayList)object3).isEmpty() || (object3 = this.j) != null && !((ArrayList)object3).isEmpty()) {
            this.i((View)object, bl);
        } else {
            for (n3 = 0; n3 < this.g.size(); ++n3) {
                object2 = object.findViewById(((Integer)this.g.get(n3)).intValue());
                if (object2 == null) continue;
                object3 = new y((View)object2);
                if (bl) {
                    this.k((y)object3);
                } else {
                    this.h((y)object3);
                }
                ((y)object3).c.add(this);
                this.j((y)object3);
                if (bl) {
                    Transition.d(this.r, object2, (y)object3);
                    continue;
                }
                Transition.d(this.s, object2, (y)object3);
            }
            for (n3 = 0; n3 < this.h.size(); ++n3) {
                object = (View)this.h.get(n3);
                object3 = new y((View)object);
                if (bl) {
                    this.k((y)object3);
                } else {
                    this.h((y)object3);
                }
                ((y)object3).c.add(this);
                this.j((y)object3);
                if (bl) {
                    Transition.d(this.r, (View)object, (y)object3);
                    continue;
                }
                Transition.d(this.s, (View)object, (y)object3);
            }
        }
        if (!bl && (object = this.J) != null) {
            int n5 = ((o.r)object).size();
            object = new ArrayList(n5);
            int n6 = 0;
            while (true) {
                if (n6 >= n5) break;
                object3 = (String)this.J.f(n6);
                ((ArrayList)object).add((View)this.r.d.remove(object3));
                ++n6;
            }
            for (n3 = n4; n3 < n5; ++n3) {
                object3 = (View)((ArrayList)object).get(n3);
                if (object3 == null) continue;
                object2 = (String)this.J.j(n3);
                this.r.d.put(object2, object3);
            }
        }
    }

    public Transition l0(long l3) {
        this.d = l3;
        return this;
    }

    public void m(boolean bl) {
        if (bl) {
            this.r.a.clear();
            this.r.b.clear();
            this.r.c.a();
            return;
        }
        this.s.a.clear();
        this.s.b.clear();
        this.s.c.a();
    }

    public void m0() {
        if (this.B == 0) {
            this.W(androidx.transition.Transition$h.a, false);
            this.D = false;
        }
        ++this.B;
    }

    public Transition n() {
        try {
            Transition transition = (Transition)super.clone();
            Object object = new ArrayList();
            transition.G = object;
            transition.r = object = new z();
            transition.s = object = new z();
            transition.v = null;
            transition.w = null;
            transition.E = this;
            transition.F = null;
            return transition;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

    public String n0(String charSequence) {
        charSequence = new StringBuilder((String)charSequence);
        ((StringBuilder)charSequence).append(this.getClass().getSimpleName());
        ((StringBuilder)charSequence).append("@");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.hashCode()));
        ((StringBuilder)charSequence).append(": ");
        if (this.e != -1L) {
            ((StringBuilder)charSequence).append("dur(");
            ((StringBuilder)charSequence).append(this.e);
            ((StringBuilder)charSequence).append(") ");
        }
        if (this.d != -1L) {
            ((StringBuilder)charSequence).append("dly(");
            ((StringBuilder)charSequence).append(this.d);
            ((StringBuilder)charSequence).append(") ");
        }
        if (this.f != null) {
            ((StringBuilder)charSequence).append("interp(");
            ((StringBuilder)charSequence).append(this.f);
            ((StringBuilder)charSequence).append(") ");
        }
        if (this.g.size() > 0 || this.h.size() > 0) {
            ((StringBuilder)charSequence).append("tgts(");
            int n3 = this.g.size();
            int n4 = 0;
            if (n3 > 0) {
                for (n3 = 0; n3 < this.g.size(); ++n3) {
                    if (n3 > 0) {
                        ((StringBuilder)charSequence).append(", ");
                    }
                    ((StringBuilder)charSequence).append(this.g.get(n3));
                }
            }
            if (this.h.size() > 0) {
                for (n3 = n4; n3 < this.h.size(); ++n3) {
                    if (n3 > 0) {
                        ((StringBuilder)charSequence).append(", ");
                    }
                    ((StringBuilder)charSequence).append(this.h.get(n3));
                }
            }
            ((StringBuilder)charSequence).append(")");
        }
        return ((StringBuilder)charSequence).toString();
    }

    public Animator o(ViewGroup viewGroup, y y3, y y4) {
        return null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void p(ViewGroup object, z object2, z z3, ArrayList arrayList, ArrayList arrayList2) {
        void var4_6;
        a a4 = Transition.E();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int n3 = var4_6.size();
        this.D().getClass();
        long l3 = Long.MAX_VALUE;
        int n4 = 0;
        while (true) {
            long l4;
            block17: {
                Object object3;
                y y3;
                Object object4;
                y y4;
                Object object5;
                block19: {
                    block11: {
                        int n5;
                        int n6;
                        y y5;
                        block18: {
                            block12: {
                                block14: {
                                    block15: {
                                        block16: {
                                            block13: {
                                                void var3_5;
                                                void var5_7;
                                                if (n4 >= n3) break block12;
                                                object5 = var4_6.get(n4);
                                                y4 = (y)var5_7.get(n4);
                                                object4 = object5;
                                                if (object5 != null) {
                                                    object4 = object5;
                                                    if (!((y)object5).c.contains(this)) {
                                                        object4 = null;
                                                    }
                                                }
                                                y3 = y4;
                                                if (y4 != null) {
                                                    y3 = y4;
                                                    if (!y4.c.contains(this)) {
                                                        y3 = null;
                                                    }
                                                }
                                                if (object4 == null && y3 == null || object4 != null && y3 != null && !this.M((y)object4, y3) || (y4 = this.o((ViewGroup)object, (y)object4, y3)) == null) break block13;
                                                if (y3 == null) break block14;
                                                object3 = y3.b;
                                                object5 = this.K();
                                                if (object5 == null || ((String[])object5).length <= 0) break block15;
                                                y5 = new y((View)object3);
                                                y y6 = (y)var3_5.a.get(object3);
                                                if (y6 == null) break block16;
                                                for (n6 = 0; n6 < ((Object)object5).length; ++n6) {
                                                    Map map = y5.a;
                                                    Object object6 = object5[n6];
                                                    map.put(object6, y6.a.get(object6));
                                                }
                                                break block16;
                                            }
                                            l4 = l3;
                                            break block17;
                                        }
                                        n5 = a4.size();
                                        break block18;
                                    }
                                    object5 = null;
                                    break block11;
                                }
                                object5 = ((y)object4).b;
                                Object var19_23 = null;
                                break block19;
                            }
                            if (sparseIntArray.size() != 0) {
                                for (n4 = 0; n4 < sparseIntArray.size(); ++n4) {
                                    n3 = sparseIntArray.keyAt(n4);
                                    d d3 = (d)a4.get((Animator)this.G.get(n3));
                                    l4 = sparseIntArray.valueAt(n4);
                                    long l5 = d3.f.getStartDelay();
                                    d3.f.setStartDelay(l4 - l3 + l5);
                                }
                            }
                            return;
                        }
                        for (n6 = 0; n6 < n5; ++n6) {
                            object5 = (d)a4.get((Animator)a4.f(n6));
                            if (((d)object5).c == null || ((d)object5).a != object3 || !((d)object5).b.equals(this.A()) || !((d)object5).c.equals(y5)) continue;
                            y4 = null;
                            object5 = y5;
                            break block11;
                        }
                        object5 = y5;
                    }
                    Object object7 = object5;
                    object5 = object3;
                }
                l4 = l3;
                if (y4 != null) {
                    void var19_19;
                    object3 = this.H;
                    l4 = l3;
                    if (object3 != null) {
                        l4 = ((x)object3).c((ViewGroup)object, this, (y)object4, y3);
                        sparseIntArray.put(this.G.size(), (int)l4);
                        l4 = Math.min(l4, l3);
                    }
                    a4.put(y4, new d((View)object5, this.A(), this, object.getWindowId(), (y)var19_19, (Animator)y4));
                    this.G.add(y4);
                }
            }
            ++n4;
            l3 = l4;
        }
    }

    public void q() {
        int n3;
        this.B = n3 = this.B - 1;
        if (n3 == 0) {
            View view;
            this.W(androidx.transition.Transition$h.b, false);
            for (n3 = 0; n3 < this.r.c.k(); ++n3) {
                view = (View)this.r.c.l(n3);
                if (view == null) continue;
                view.setHasTransientState(false);
            }
            for (n3 = 0; n3 < this.s.c.k(); ++n3) {
                view = (View)this.s.c.l(n3);
                if (view == null) continue;
                view.setHasTransientState(false);
            }
            this.D = true;
        }
    }

    public Transition r(View view, boolean bl) {
        this.l = this.u(this.l, view, bl);
        return this;
    }

    public Transition s(Class clazz, boolean bl) {
        this.m = this.t(this.m, clazz, bl);
        return this;
    }

    public final ArrayList t(ArrayList arrayList, Class clazz, boolean bl) {
        ArrayList arrayList2 = arrayList;
        if (clazz != null) {
            if (bl) {
                return androidx.transition.Transition$e.a(arrayList, clazz);
            }
            arrayList2 = androidx.transition.Transition$e.b(arrayList, clazz);
        }
        return arrayList2;
    }

    public String toString() {
        return this.n0("");
    }

    public final ArrayList u(ArrayList arrayList, View view, boolean bl) {
        ArrayList arrayList2 = arrayList;
        if (view != null) {
            if (bl) {
                return androidx.transition.Transition$e.a(arrayList, view);
            }
            arrayList2 = androidx.transition.Transition$e.b(arrayList, view);
        }
        return arrayList2;
    }

    public long v() {
        return this.e;
    }

    public Rect w() {
        f f3 = this.I;
        if (f3 == null) {
            return null;
        }
        return f3.a(this);
    }

    public f x() {
        return this.I;
    }

    public TimeInterpolator y() {
        return this.f;
    }

    public y z(View object, boolean bl) {
        int n3;
        block6: {
            Cloneable cloneable = this.t;
            if (cloneable != null) {
                return ((Transition)cloneable).z((View)object, bl);
            }
            cloneable = bl ? this.v : this.w;
            if (cloneable == null) {
                return null;
            }
            int n4 = ((ArrayList)cloneable).size();
            for (n3 = 0; n3 < n4; ++n3) {
                y y3 = (y)((ArrayList)cloneable).get(n3);
                if (y3 == null) {
                    return null;
                }
                if (y3.b != object) {
                    continue;
                }
                break block6;
            }
            n3 = -1;
        }
        if (n3 >= 0) {
            object = bl ? this.w : this.v;
            return (y)((ArrayList)object).get(n3);
        }
        return null;
    }

    public static class d {
        public View a;
        public String b;
        public y c;
        public WindowId d;
        public Transition e;
        public Animator f;

        public d(View view, String string, Transition transition, WindowId windowId, y y3, Animator animator) {
            this.a = view;
            this.b = string;
            this.c = y3;
            this.d = windowId;
            this.e = transition;
            this.f = animator;
        }
    }

    public static abstract class e {
        public static ArrayList a(ArrayList arrayList, Object object) {
            ArrayList<Object> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<Object>();
            }
            if (!arrayList2.contains(object)) {
                arrayList2.add(object);
            }
            return arrayList2;
        }

        public static ArrayList b(ArrayList arrayList, Object object) {
            ArrayList arrayList2 = arrayList;
            if (arrayList != null) {
                arrayList.remove(object);
                arrayList2 = arrayList;
                if (arrayList.isEmpty()) {
                    arrayList2 = null;
                }
            }
            return arrayList2;
        }
    }

    public static abstract class f {
        public abstract Rect a(Transition var1);
    }

    public static interface g {
        public void a(Transition var1);

        public void b(Transition var1);

        default public void c(Transition transition, boolean bl) {
            this.a(transition);
        }

        public void d(Transition var1);

        public void e(Transition var1);

        default public void f(Transition transition, boolean bl) {
            this.g(transition);
        }

        public void g(Transition var1);
    }

    public static interface h {
        public static final h a = new s();
        public static final h b = new t();
        public static final h c = new u();
        public static final h d = new v();
        public static final h e = new w();

        public static /* synthetic */ void b(g g3, Transition transition, boolean bl) {
            g3.b(transition);
        }

        public static /* synthetic */ void c(g g3, Transition transition, boolean bl) {
            g3.e(transition);
        }

        public static /* synthetic */ void d(g g3, Transition transition, boolean bl) {
            g3.d(transition);
        }

        public void a(g var1, Transition var2, boolean var3);
    }
}

