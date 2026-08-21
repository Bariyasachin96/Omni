/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 */
package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.m;
import java.util.ArrayList;
import java.util.List;
import o0.x0;

public class c
extends m {
    public static TimeInterpolator s;
    public ArrayList h = new ArrayList();
    public ArrayList i = new ArrayList();
    public ArrayList j = new ArrayList();
    public ArrayList k = new ArrayList();
    public ArrayList l = new ArrayList();
    public ArrayList m = new ArrayList();
    public ArrayList n = new ArrayList();
    public ArrayList o = new ArrayList();
    public ArrayList p = new ArrayList();
    public ArrayList q = new ArrayList();
    public ArrayList r = new ArrayList();

    public void Q(RecyclerView.d0 d02) {
        View view = d02.a;
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.o.add(d02);
        viewPropertyAnimator.alpha(1.0f).setDuration(this.l()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, d02, view, viewPropertyAnimator){
            public final RecyclerView.d0 a;
            public final View b;
            public final ViewPropertyAnimator c;
            public final c d;
            {
                this.d = c3;
                this.a = d02;
                this.b = view;
                this.c = viewPropertyAnimator;
            }

            public void onAnimationCancel(Animator animator) {
                this.b.setAlpha(1.0f);
            }

            public void onAnimationEnd(Animator animator) {
                this.c.setListener(null);
                this.d.A(this.a);
                this.d.o.remove(this.a);
                this.d.V();
            }

            public void onAnimationStart(Animator animator) {
                this.d.B(this.a);
            }
        }).start();
    }

    public void R(i i3) {
        RecyclerView.d0 d02 = i3.a;
        View view = null;
        d02 = d02 == null ? null : d02.a;
        RecyclerView.d0 d03 = i3.b;
        if (d03 != null) {
            view = d03.a;
        }
        if (d02 != null) {
            d03 = d02.animate().setDuration(this.m());
            this.r.add(i3.a);
            d03.translationX(i3.e - i3.c);
            d03.translationY(i3.f - i3.d);
            d03.alpha(0.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, i3, (ViewPropertyAnimator)d03, (View)d02){
                public final i a;
                public final ViewPropertyAnimator b;
                public final View c;
                public final c d;
                {
                    this.d = c3;
                    this.a = i3;
                    this.b = viewPropertyAnimator;
                    this.c = view;
                }

                public void onAnimationEnd(Animator animator) {
                    this.b.setListener(null);
                    this.c.setAlpha(1.0f);
                    this.c.setTranslationX(0.0f);
                    this.c.setTranslationY(0.0f);
                    this.d.C(this.a.a, true);
                    this.d.r.remove(this.a.a);
                    this.d.V();
                }

                public void onAnimationStart(Animator animator) {
                    this.d.D(this.a.a, true);
                }
            }).start();
        }
        if (view != null) {
            d02 = view.animate();
            this.r.add(i3.b);
            d02.translationX(0.0f).translationY(0.0f).setDuration(this.m()).alpha(1.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, i3, (ViewPropertyAnimator)d02, view){
                public final i a;
                public final ViewPropertyAnimator b;
                public final View c;
                public final c d;
                {
                    this.d = c3;
                    this.a = i3;
                    this.b = viewPropertyAnimator;
                    this.c = view;
                }

                public void onAnimationEnd(Animator animator) {
                    this.b.setListener(null);
                    this.c.setAlpha(1.0f);
                    this.c.setTranslationX(0.0f);
                    this.c.setTranslationY(0.0f);
                    this.d.C(this.a.b, false);
                    this.d.r.remove(this.a.b);
                    this.d.V();
                }

                public void onAnimationStart(Animator animator) {
                    this.d.D(this.a.b, false);
                }
            }).start();
        }
    }

    public void S(RecyclerView.d0 d02, int n3, int n4, int n5, int n6) {
        View view = d02.a;
        n3 = n5 - n3;
        n4 = n6 - n4;
        if (n3 != 0) {
            view.animate().translationX(0.0f);
        }
        if (n4 != 0) {
            view.animate().translationY(0.0f);
        }
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.p.add(d02);
        viewPropertyAnimator.setDuration(this.n()).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, d02, n3, view, n4, viewPropertyAnimator){
            public final RecyclerView.d0 a;
            public final int b;
            public final View c;
            public final int d;
            public final ViewPropertyAnimator e;
            public final c f;
            {
                this.f = c3;
                this.a = d02;
                this.b = n3;
                this.c = view;
                this.d = n4;
                this.e = viewPropertyAnimator;
            }

            public void onAnimationCancel(Animator animator) {
                if (this.b != 0) {
                    this.c.setTranslationX(0.0f);
                }
                if (this.d != 0) {
                    this.c.setTranslationY(0.0f);
                }
            }

            public void onAnimationEnd(Animator animator) {
                this.e.setListener(null);
                this.f.E(this.a);
                this.f.p.remove(this.a);
                this.f.V();
            }

            public void onAnimationStart(Animator animator) {
                this.f.F(this.a);
            }
        }).start();
    }

    public final void T(RecyclerView.d0 d02) {
        View view = d02.a;
        ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.q.add(d02);
        viewPropertyAnimator.setDuration(this.o()).alpha(0.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, d02, viewPropertyAnimator, view){
            public final RecyclerView.d0 a;
            public final ViewPropertyAnimator b;
            public final View c;
            public final c d;
            {
                this.d = c3;
                this.a = d02;
                this.b = viewPropertyAnimator;
                this.c = view;
            }

            public void onAnimationEnd(Animator animator) {
                this.b.setListener(null);
                this.c.setAlpha(1.0f);
                this.d.G(this.a);
                this.d.q.remove(this.a);
                this.d.V();
            }

            public void onAnimationStart(Animator animator) {
                this.d.H(this.a);
            }
        }).start();
    }

    public void U(List list) {
        for (int i3 = list.size() - 1; i3 >= 0; --i3) {
            ((RecyclerView.d0)list.get((int)i3)).a.animate().cancel();
        }
    }

    public void V() {
        if (!this.p()) {
            this.i();
        }
    }

    public final void W(List list, RecyclerView.d0 d02) {
        for (int i3 = list.size() - 1; i3 >= 0; --i3) {
            i i4 = (i)list.get(i3);
            if (!this.Y(i4, d02) || i4.a != null || i4.b != null) continue;
            list.remove(i4);
        }
    }

    public final void X(i i3) {
        RecyclerView.d0 d02 = i3.a;
        if (d02 != null) {
            this.Y(i3, d02);
        }
        if ((d02 = i3.b) != null) {
            this.Y(i3, d02);
        }
    }

    public final boolean Y(i i3, RecyclerView.d0 d02) {
        block4: {
            boolean bl;
            block3: {
                block2: {
                    RecyclerView.d0 d03 = i3.b;
                    bl = false;
                    if (d03 != d02) break block2;
                    i3.b = null;
                    break block3;
                }
                if (i3.a != d02) break block4;
                i3.a = null;
                bl = true;
            }
            d02.a.setAlpha(1.0f);
            d02.a.setTranslationX(0.0f);
            d02.a.setTranslationY(0.0f);
            this.C(d02, bl);
            return true;
        }
        return false;
    }

    public final void Z(RecyclerView.d0 d02) {
        if (s == null) {
            s = new ValueAnimator().getInterpolator();
        }
        d02.a.animate().setInterpolator(s);
        this.j(d02);
    }

    @Override
    public boolean g(RecyclerView.d0 d02, List list) {
        return !list.isEmpty() || super.g(d02, list);
        {
        }
    }

    @Override
    public void j(RecyclerView.d0 d02) {
        ArrayList arrayList;
        int n3;
        View view = d02.a;
        view.animate().cancel();
        for (n3 = this.j.size() - 1; n3 >= 0; --n3) {
            if (((j)this.j.get((int)n3)).a != d02) continue;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            this.E(d02);
            this.j.remove(n3);
        }
        this.W(this.k, d02);
        if (this.h.remove(d02)) {
            view.setAlpha(1.0f);
            this.G(d02);
        }
        if (this.i.remove(d02)) {
            view.setAlpha(1.0f);
            this.A(d02);
        }
        for (n3 = this.n.size() - 1; n3 >= 0; --n3) {
            arrayList = (ArrayList)this.n.get(n3);
            this.W(arrayList, d02);
            if (!arrayList.isEmpty()) continue;
            this.n.remove(n3);
        }
        block2: for (n3 = this.m.size() - 1; n3 >= 0; --n3) {
            arrayList = (ArrayList)this.m.get(n3);
            for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
                if (((j)arrayList.get((int)i3)).a != d02) continue;
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                this.E(d02);
                arrayList.remove(i3);
                if (!arrayList.isEmpty()) continue block2;
                this.m.remove(n3);
                continue block2;
            }
        }
        for (n3 = this.l.size() - 1; n3 >= 0; --n3) {
            arrayList = (ArrayList)this.l.get(n3);
            if (!arrayList.remove(d02)) continue;
            view.setAlpha(1.0f);
            this.A(d02);
            if (!arrayList.isEmpty()) continue;
            this.l.remove(n3);
        }
        this.q.remove(d02);
        this.o.remove(d02);
        this.r.remove(d02);
        this.p.remove(d02);
        this.V();
    }

    @Override
    public void k() {
        int n3;
        Object object;
        Object object2;
        int n4;
        for (n4 = this.j.size() - 1; n4 >= 0; --n4) {
            object2 = (j)this.j.get(n4);
            object = ((j)object2).a.a;
            object.setTranslationY(0.0f);
            object.setTranslationX(0.0f);
            this.E(((j)object2).a);
            this.j.remove(n4);
        }
        for (n4 = this.h.size() - 1; n4 >= 0; --n4) {
            this.G((RecyclerView.d0)this.h.get(n4));
            this.h.remove(n4);
        }
        for (n4 = this.i.size() - 1; n4 >= 0; --n4) {
            object = (RecyclerView.d0)this.i.get(n4);
            ((RecyclerView.d0)object).a.setAlpha(1.0f);
            this.A((RecyclerView.d0)object);
            this.i.remove(n4);
        }
        for (n4 = this.k.size() - 1; n4 >= 0; --n4) {
            this.X((i)this.k.get(n4));
        }
        this.k.clear();
        if (!this.p()) {
            return;
        }
        for (n4 = this.m.size() - 1; n4 >= 0; --n4) {
            ArrayList arrayList = (ArrayList)this.m.get(n4);
            for (n3 = arrayList.size() - 1; n3 >= 0; --n3) {
                object = (j)arrayList.get(n3);
                object2 = ((j)object).a.a;
                object2.setTranslationY(0.0f);
                object2.setTranslationX(0.0f);
                this.E(((j)object).a);
                arrayList.remove(n3);
                if (!arrayList.isEmpty()) continue;
                this.m.remove(arrayList);
            }
        }
        for (n4 = this.l.size() - 1; n4 >= 0; --n4) {
            object = (ArrayList)this.l.get(n4);
            for (n3 = ((ArrayList)object).size() - 1; n3 >= 0; --n3) {
                object2 = (RecyclerView.d0)((ArrayList)object).get(n3);
                ((RecyclerView.d0)object2).a.setAlpha(1.0f);
                this.A((RecyclerView.d0)object2);
                ((ArrayList)object).remove(n3);
                if (!((ArrayList)object).isEmpty()) continue;
                this.l.remove(object);
            }
        }
        for (n4 = this.n.size() - 1; n4 >= 0; --n4) {
            object = (ArrayList)this.n.get(n4);
            for (n3 = ((ArrayList)object).size() - 1; n3 >= 0; --n3) {
                this.X((i)((ArrayList)object).get(n3));
                if (!((ArrayList)object).isEmpty()) continue;
                this.n.remove(object);
            }
        }
        this.U(this.q);
        this.U(this.p);
        this.U(this.o);
        this.U(this.r);
        this.i();
    }

    @Override
    public boolean p() {
        return !this.i.isEmpty() || !this.k.isEmpty() || !this.j.isEmpty() || !this.h.isEmpty() || !this.p.isEmpty() || !this.q.isEmpty() || !this.o.isEmpty() || !this.r.isEmpty() || !this.m.isEmpty() || !this.l.isEmpty() || !this.n.isEmpty();
        {
        }
    }

    @Override
    public void u() {
        boolean bl = this.h.isEmpty();
        boolean bl2 = this.j.isEmpty();
        boolean bl3 = this.k.isEmpty();
        boolean bl4 = this.i.isEmpty();
        if (!(bl && bl2 && bl4 && bl3)) {
            Object object;
            Object object2 = this.h;
            int n3 = ((ArrayList)object2).size();
            for (int i3 = 0; i3 < n3; ++i3) {
                object = ((ArrayList)object2).get(i3);
                this.T((RecyclerView.d0)object);
            }
            this.h.clear();
            if (!bl2) {
                object = new ArrayList();
                ((ArrayList)object).addAll(this.j);
                this.m.add(object);
                this.j.clear();
                object2 = new Runnable(this, (ArrayList)object){
                    public final ArrayList c;
                    public final c d;
                    {
                        this.d = c3;
                        this.c = arrayList;
                    }

                    @Override
                    public void run() {
                        ArrayList arrayList = this.c;
                        int n3 = arrayList.size();
                        for (int i3 = 0; i3 < n3; ++i3) {
                            Object object = arrayList.get(i3);
                            object = (j)object;
                            this.d.S(((j)object).a, ((j)object).b, ((j)object).c, ((j)object).d, ((j)object).e);
                        }
                        this.c.clear();
                        this.d.m.remove(this.c);
                    }
                };
                if (!bl) {
                    x0.a0(((j)((ArrayList)object).get((int)0)).a.a, (Runnable)object2, this.o());
                } else {
                    object2.run();
                }
            }
            if (!bl3) {
                object2 = new ArrayList();
                ((ArrayList)object2).addAll(this.k);
                this.n.add(object2);
                this.k.clear();
                object = new Runnable(this, (ArrayList)object2){
                    public final ArrayList c;
                    public final c d;
                    {
                        this.d = c3;
                        this.c = arrayList;
                    }

                    @Override
                    public void run() {
                        ArrayList arrayList = this.c;
                        int n3 = arrayList.size();
                        for (int i3 = 0; i3 < n3; ++i3) {
                            Object object = arrayList.get(i3);
                            object = (i)object;
                            this.d.R((i)object);
                        }
                        this.c.clear();
                        this.d.n.remove(this.c);
                    }
                };
                if (!bl) {
                    x0.a0(((i)((ArrayList)object2).get((int)0)).a.a, (Runnable)object, this.o());
                } else {
                    object.run();
                }
            }
            if (!bl4) {
                object2 = new ArrayList();
                ((ArrayList)object2).addAll(this.i);
                this.l.add(object2);
                this.i.clear();
                object = new Runnable(this, (ArrayList)object2){
                    public final ArrayList c;
                    public final c d;
                    {
                        this.d = c3;
                        this.c = arrayList;
                    }

                    @Override
                    public void run() {
                        ArrayList arrayList = this.c;
                        int n3 = arrayList.size();
                        for (int i3 = 0; i3 < n3; ++i3) {
                            Object object = arrayList.get(i3);
                            object = (RecyclerView.d0)object;
                            this.d.Q((RecyclerView.d0)object);
                        }
                        this.c.clear();
                        this.d.l.remove(this.c);
                    }
                };
                if (bl && bl2 && bl3) {
                    object.run();
                    return;
                }
                long l3 = 0L;
                long l4 = !bl ? this.o() : 0L;
                long l5 = !bl2 ? this.n() : 0L;
                if (!bl3) {
                    l3 = this.m();
                }
                l5 = Math.max(l5, l3);
                x0.a0(((RecyclerView.d0)((ArrayList)object2).get((int)0)).a, (Runnable)object, l4 + l5);
            }
        }
    }

    @Override
    public boolean w(RecyclerView.d0 d02) {
        this.Z(d02);
        d02.a.setAlpha(0.0f);
        this.i.add(d02);
        return true;
    }

    @Override
    public boolean x(RecyclerView.d0 d02, RecyclerView.d0 d03, int n3, int n4, int n5, int n6) {
        if (d02 == d03) {
            return this.y(d02, n3, n4, n5, n6);
        }
        float f3 = d02.a.getTranslationX();
        float f4 = d02.a.getTranslationY();
        float f5 = d02.a.getAlpha();
        this.Z(d02);
        int n7 = (int)((float)(n5 - n3) - f3);
        int n8 = (int)((float)(n6 - n4) - f4);
        d02.a.setTranslationX(f3);
        d02.a.setTranslationY(f4);
        d02.a.setAlpha(f5);
        if (d03 != null) {
            this.Z(d03);
            d03.a.setTranslationX((float)(-n7));
            d03.a.setTranslationY((float)(-n8));
            d03.a.setAlpha(0.0f);
        }
        this.k.add(new i(d02, d03, n3, n4, n5, n6));
        return true;
    }

    @Override
    public boolean y(RecyclerView.d0 d02, int n3, int n4, int n5, int n6) {
        View view = d02.a;
        this.Z(d02);
        int n7 = n5 - (n3 += (int)view.getTranslationX());
        int n8 = n6 - (n4 += (int)d02.a.getTranslationY());
        if (n7 == 0 && n8 == 0) {
            this.E(d02);
            return false;
        }
        if (n7 != 0) {
            view.setTranslationX((float)(-n7));
        }
        if (n8 != 0) {
            view.setTranslationY((float)(-n8));
        }
        this.j.add(new j(d02, n3, n4, n5, n6));
        return true;
    }

    @Override
    public boolean z(RecyclerView.d0 d02) {
        this.Z(d02);
        this.h.add(d02);
        return true;
    }

    public static class i {
        public RecyclerView.d0 a;
        public RecyclerView.d0 b;
        public int c;
        public int d;
        public int e;
        public int f;

        public i(RecyclerView.d0 d02, RecyclerView.d0 d03) {
            this.a = d02;
            this.b = d03;
        }

        public i(RecyclerView.d0 d02, RecyclerView.d0 d03, int n3, int n4, int n5, int n6) {
            this(d02, d03);
            this.c = n3;
            this.d = n4;
            this.e = n5;
            this.f = n6;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ChangeInfo{oldHolder=");
            stringBuilder.append(this.a);
            stringBuilder.append(", newHolder=");
            stringBuilder.append(this.b);
            stringBuilder.append(", fromX=");
            stringBuilder.append(this.c);
            stringBuilder.append(", fromY=");
            stringBuilder.append(this.d);
            stringBuilder.append(", toX=");
            stringBuilder.append(this.e);
            stringBuilder.append(", toY=");
            stringBuilder.append(this.f);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static class j {
        public RecyclerView.d0 a;
        public int b;
        public int c;
        public int d;
        public int e;

        public j(RecyclerView.d0 d02, int n3, int n4, int n5, int n6) {
            this.a = d02;
            this.b = n3;
            this.c = n4;
            this.d = n5;
            this.e = n6;
        }
    }
}

