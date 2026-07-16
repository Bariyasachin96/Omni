/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
 *  android.util.AndroidRuntimeException
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.PathMotion;
import androidx.transition.Transition;
import f0.k;
import java.util.ArrayList;
import m1.r;
import m1.x;
import m1.y;
import m1.z;
import org.xmlpull.v1.XmlPullParser;

public class TransitionSet
extends Transition {
    public ArrayList P = new ArrayList();
    public boolean Q = true;
    public int R;
    public boolean S = false;
    public int T = 0;

    public TransitionSet() {
    }

    public TransitionSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, m1.r.i);
        this.y0(f0.k.g((TypedArray)context, (XmlPullParser)((XmlResourceParser)attributeSet), "transitionOrdering", 0, 0));
        context.recycle();
    }

    public final void A0() {
        b b3 = new b(this);
        ArrayList arrayList = this.P;
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object e3 = arrayList.get(i3);
            ((Transition)e3).a(b3);
        }
        this.R = this.P.size();
    }

    @Override
    public void Y(View view) {
        super.Y(view);
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((Transition)this.P.get(i3)).Y(view);
        }
    }

    @Override
    public void c0(View view) {
        super.c0(view);
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((Transition)this.P.get(i3)).c0(view);
        }
    }

    @Override
    public void e0() {
        int n3;
        if (this.P.isEmpty()) {
            this.m0();
            this.q();
            return;
        }
        this.A0();
        boolean bl = this.Q;
        if (!bl) {
            for (n3 = 1; n3 < this.P.size(); ++n3) {
                ((Transition)this.P.get(n3 - 1)).a(new androidx.transition.b(this, (Transition)this.P.get(n3)){
                    public final Transition a;
                    public final TransitionSet b;
                    {
                        this.b = transitionSet;
                        this.a = transition;
                    }

                    @Override
                    public void g(Transition transition) {
                        this.a.e0();
                        transition.a0(this);
                    }
                });
            }
            Transition transition = (Transition)this.P.get(0);
            if (transition != null) {
                transition.e0();
                return;
            }
        } else {
            ArrayList arrayList = this.P;
            int n4 = arrayList.size();
            for (n3 = 0; n3 < n4; ++n3) {
                Object e3 = arrayList.get(n3);
                ((Transition)e3).e0();
            }
        }
    }

    @Override
    public void g() {
        super.g();
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((Transition)this.P.get(i3)).g();
        }
    }

    @Override
    public void g0(Transition.f f3) {
        super.g0(f3);
        this.T |= 8;
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((Transition)this.P.get(i3)).g0(f3);
        }
    }

    @Override
    public void h(y y3) {
        if (this.O(y3.b)) {
            ArrayList arrayList = this.P;
            int n3 = arrayList.size();
            int n4 = 0;
            while (n4 < n3) {
                Object object = arrayList.get(n4);
                int n5 = n4 + 1;
                object = (Transition)object;
                n4 = n5;
                if (!((Transition)object).O(y3.b)) continue;
                ((Transition)object).h(y3);
                y3.c.add(object);
                n4 = n5;
            }
        }
    }

    @Override
    public void j(y y3) {
        super.j(y3);
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((Transition)this.P.get(i3)).j(y3);
        }
    }

    @Override
    public void j0(PathMotion pathMotion) {
        super.j0(pathMotion);
        this.T |= 4;
        if (this.P != null) {
            for (int i3 = 0; i3 < this.P.size(); ++i3) {
                ((Transition)this.P.get(i3)).j0(pathMotion);
            }
        }
    }

    @Override
    public void k(y y3) {
        if (this.O(y3.b)) {
            ArrayList arrayList = this.P;
            int n3 = arrayList.size();
            int n4 = 0;
            while (n4 < n3) {
                Object object = arrayList.get(n4);
                int n5 = n4 + 1;
                object = (Transition)object;
                n4 = n5;
                if (!((Transition)object).O(y3.b)) continue;
                ((Transition)object).k(y3);
                y3.c.add(object);
                n4 = n5;
            }
        }
    }

    @Override
    public void k0(x x3) {
        super.k0(x3);
        this.T |= 2;
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((Transition)this.P.get(i3)).k0(x3);
        }
    }

    @Override
    public Transition n() {
        TransitionSet transitionSet = (TransitionSet)super.n();
        transitionSet.P = new ArrayList();
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            transitionSet.r0(((Transition)this.P.get(i3)).n());
        }
        return transitionSet;
    }

    @Override
    public String n0(String string) {
        Object object = super.n0(string);
        for (int i3 = 0; i3 < this.P.size(); ++i3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append("\n");
            object = (Transition)this.P.get(i3);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string);
            stringBuilder2.append("  ");
            stringBuilder.append(((Transition)object).n0(stringBuilder2.toString()));
            object = stringBuilder.toString();
        }
        return object;
    }

    public TransitionSet o0(Transition.g g3) {
        return (TransitionSet)super.a(g3);
    }

    @Override
    public void p(ViewGroup viewGroup, z z3, z z4, ArrayList arrayList, ArrayList arrayList2) {
        long l3 = this.F();
        int n3 = this.P.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Transition transition = (Transition)this.P.get(i3);
            if (l3 > 0L && (this.Q || i3 == 0)) {
                long l4 = transition.F();
                if (l4 > 0L) {
                    transition.l0(l4 + l3);
                } else {
                    transition.l0(l3);
                }
            }
            transition.p(viewGroup, z3, z4, arrayList, arrayList2);
        }
    }

    public TransitionSet p0(View view) {
        for (int i3 = 0; i3 < this.P.size(); ++i3) {
            ((Transition)this.P.get(i3)).b(view);
        }
        return (TransitionSet)super.b(view);
    }

    public TransitionSet q0(Transition transition) {
        this.r0(transition);
        long l3 = this.e;
        if (l3 >= 0L) {
            transition.f0(l3);
        }
        if ((this.T & 1) != 0) {
            transition.h0(this.y());
        }
        if ((this.T & 2) != 0) {
            transition.k0(this.C());
        }
        if ((this.T & 4) != 0) {
            transition.j0(this.B());
        }
        if ((this.T & 8) != 0) {
            transition.g0(this.x());
        }
        return this;
    }

    @Override
    public Transition r(View view, boolean bl) {
        for (int i3 = 0; i3 < this.P.size(); ++i3) {
            ((Transition)this.P.get(i3)).r(view, bl);
        }
        return super.r(view, bl);
    }

    public final void r0(Transition transition) {
        this.P.add(transition);
        transition.t = this;
    }

    @Override
    public Transition s(Class clazz, boolean bl) {
        for (int i3 = 0; i3 < this.P.size(); ++i3) {
            ((Transition)this.P.get(i3)).s(clazz, bl);
        }
        return super.s(clazz, bl);
    }

    public Transition s0(int n3) {
        if (n3 >= 0 && n3 < this.P.size()) {
            return (Transition)this.P.get(n3);
        }
        return null;
    }

    public int t0() {
        return this.P.size();
    }

    public TransitionSet u0(Transition.g g3) {
        return (TransitionSet)super.a0(g3);
    }

    public TransitionSet v0(View view) {
        for (int i3 = 0; i3 < this.P.size(); ++i3) {
            ((Transition)this.P.get(i3)).b0(view);
        }
        return (TransitionSet)super.b0(view);
    }

    public TransitionSet w0(long l3) {
        ArrayList arrayList;
        super.f0(l3);
        if (this.e >= 0L && (arrayList = this.P) != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ((Transition)this.P.get(i3)).f0(l3);
            }
        }
        return this;
    }

    public TransitionSet x0(TimeInterpolator timeInterpolator) {
        this.T |= 1;
        ArrayList arrayList = this.P;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ((Transition)this.P.get(i3)).h0(timeInterpolator);
            }
        }
        return (TransitionSet)super.h0(timeInterpolator);
    }

    public TransitionSet y0(int n3) {
        if (n3 != 0) {
            if (n3 == 1) {
                this.Q = false;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid parameter for TransitionSet ordering: ");
            stringBuilder.append(n3);
            throw new AndroidRuntimeException(stringBuilder.toString());
        }
        this.Q = true;
        return this;
    }

    public TransitionSet z0(long l3) {
        return (TransitionSet)super.l0(l3);
    }

    public static class b
    extends androidx.transition.b {
        public TransitionSet a;

        public b(TransitionSet transitionSet) {
            this.a = transitionSet;
        }

        @Override
        public void a(Transition transition) {
            transition = this.a;
            if (!((TransitionSet)transition).S) {
                transition.m0();
                this.a.S = true;
            }
        }

        @Override
        public void g(Transition transition) {
            int n3;
            TransitionSet transitionSet = this.a;
            transitionSet.R = n3 = transitionSet.R - 1;
            if (n3 == 0) {
                transitionSet.S = false;
                transitionSet.q();
            }
            transition.a0(this);
        }
    }
}

