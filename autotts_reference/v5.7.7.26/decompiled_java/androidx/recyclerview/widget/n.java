/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 */
package androidx.recyclerview.widget;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.RecyclerView;

public abstract class n
extends RecyclerView.r {
    public RecyclerView a;
    public Scroller b;
    public final RecyclerView.t c = new RecyclerView.t(this){
        public boolean a;
        public final n b;
        {
            this.b = n3;
            this.a = false;
        }

        @Override
        public void a(RecyclerView recyclerView, int n3) {
            super.a(recyclerView, n3);
            if (n3 == 0 && this.a) {
                this.a = false;
                this.b.j();
            }
        }

        @Override
        public void b(RecyclerView recyclerView, int n3, int n4) {
            if (n3 == 0 && n4 == 0) {
                return;
            }
            this.a = true;
        }
    };

    @Override
    public boolean a(int n3, int n4) {
        RecyclerView.p p3 = this.a.getLayoutManager();
        if (p3 == null) {
            return false;
        }
        if (this.a.getAdapter() == null) {
            return false;
        }
        int n5 = this.a.getMinFlingVelocity();
        return (Math.abs(n4) > n5 || Math.abs(n3) > n5) && this.i(p3, n3, n4);
    }

    public void b(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.a;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                this.e();
            }
            this.a = recyclerView;
            if (recyclerView != null) {
                this.h();
                this.b = new Scroller(this.a.getContext(), (Interpolator)new DecelerateInterpolator());
                this.j();
            }
        }
    }

    public abstract int[] c(RecyclerView.p var1, View var2);

    public abstract RecyclerView.y d(RecyclerView.p var1);

    public final void e() {
        this.a.i1(this.c);
        this.a.setOnFlingListener(null);
    }

    public abstract View f(RecyclerView.p var1);

    public abstract int g(RecyclerView.p var1, int var2, int var3);

    public final void h() {
        if (this.a.getOnFlingListener() == null) {
            this.a.n(this.c);
            this.a.setOnFlingListener(this);
            return;
        }
        throw new IllegalStateException("An instance of OnFlingListener already set.");
    }

    public final boolean i(RecyclerView.p p3, int n3, int n4) {
        if (!(p3 instanceof RecyclerView.y.b)) {
            return false;
        }
        RecyclerView.y y3 = this.d(p3);
        if (y3 == null) {
            return false;
        }
        if ((n3 = this.g(p3, n3, n4)) == -1) {
            return false;
        }
        y3.p(n3);
        p3.N1(y3);
        return true;
    }

    public void j() {
        Object object;
        View view;
        Object object2 = this.a;
        if (object2 == null || (object2 = ((RecyclerView)object2).getLayoutManager()) == null || (view = this.f((RecyclerView.p)object2)) == null || (object = (object2 = (Object)this.c((RecyclerView.p)object2, view))[0]) == false && object2[1] == false) {
            return;
        }
        this.a.w1((int)object, (int)object2[1]);
    }
}

