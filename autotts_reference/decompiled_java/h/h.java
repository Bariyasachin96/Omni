/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package h;

import android.view.View;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import o0.h1;
import o0.i1;
import o0.j1;

public class h {
    public final ArrayList a;
    public long b = -1L;
    public Interpolator c;
    public i1 d;
    public boolean e;
    public final j1 f = new j1(this){
        public boolean a;
        public int b;
        public final h c;
        {
            this.c = h3;
            this.a = false;
            this.b = 0;
        }

        @Override
        public void b(View object) {
            int n3;
            this.b = n3 = this.b + 1;
            if (n3 == this.c.a.size()) {
                object = this.c.d;
                if (object != null) {
                    object.b(null);
                }
                this.d();
            }
        }

        @Override
        public void c(View object) {
            if (!this.a) {
                this.a = true;
                object = this.c.d;
                if (object != null) {
                    object.c(null);
                }
            }
        }

        public void d() {
            this.b = 0;
            this.a = false;
            this.c.b();
        }
    };

    public h() {
        this.a = new ArrayList();
    }

    public void a() {
        if (!this.e) {
            return;
        }
        ArrayList arrayList = this.a;
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object e3 = arrayList.get(i3);
            ((h1)e3).c();
        }
        this.e = false;
    }

    public void b() {
        this.e = false;
    }

    public h c(h1 h12) {
        if (!this.e) {
            this.a.add(h12);
        }
        return this;
    }

    public h d(h1 h12, h1 h13) {
        this.a.add(h12);
        h13.i(h12.d());
        this.a.add(h13);
        return this;
    }

    public h e(long l3) {
        if (!this.e) {
            this.b = l3;
        }
        return this;
    }

    public h f(Interpolator interpolator) {
        if (!this.e) {
            this.c = interpolator;
        }
        return this;
    }

    public h g(i1 i12) {
        if (!this.e) {
            this.d = i12;
        }
        return this;
    }

    public void h() {
        if (this.e) {
            return;
        }
        ArrayList arrayList = this.a;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Interpolator interpolator;
            Object object = arrayList.get(n4);
            ++n4;
            object = (h1)object;
            long l3 = this.b;
            if (l3 >= 0L) {
                ((h1)object).e(l3);
            }
            if ((interpolator = this.c) != null) {
                ((h1)object).f(interpolator);
            }
            if (this.d != null) {
                ((h1)object).g(this.f);
            }
            ((h1)object).k();
        }
        this.e = true;
    }
}

