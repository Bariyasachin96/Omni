/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.b;
import c1.d;
import c1.e;
import c1.f;
import c1.h;
import c1.k;
import c1.n;
import c1.u;
import c1.y;
import e3.t;
import java.util.ArrayList;
import java.util.List;
import o3.g;

public final class m {
    public static final a d = new a(null);
    public final u a;
    public final u b;
    public final List c;

    public m(u u3, u u4) {
        o3.k.e(u3, "start");
        o3.k.e(u4, "end");
        this.a = u3;
        this.b = u4;
        this.c = d.a(u3, u4);
    }

    public final List a(float f3) {
        List list = e3.k.c();
        int n3 = this.c.size();
        d d3 = null;
        Object object = null;
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object2 = new float[8];
            for (int i4 = 0; i4 < 8; ++i4) {
                object2[i4] = y.i(((d)((d3.d)this.c.get(i3)).c()).j()[i4], ((d)((d3.d)this.c.get(i3)).d()).j()[i4], f3);
            }
            d d4 = new d((float[])object2);
            object2 = object;
            if (object == null) {
                object2 = d4;
            }
            if (d3 != null) {
                list.add(d3);
            }
            d3 = d4;
            object = object2;
        }
        if (d3 != null && object != null) {
            list.add(e.a(d3.b(), d3.c(), d3.f(), d3.g(), d3.h(), d3.i(), ((d)object).b(), ((d)object).c()));
        }
        return e3.k.a(list);
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final List a(u object, u object2) {
            o3.k.e(object, "p1");
            o3.k.e(object2, "p2");
            Object object3 = k.g;
            k k3 = ((k.a)object3).a(new b(((u)object).e(), ((u)object).f()), (u)object);
            object = ((k.a)object3).a(new b(((u)object2).e(), ((u)object2).f()), (u)object2);
            object3 = h.c(k3.f(), ((k)object).f());
            float f3 = ((f)object3).a(0.0f);
            n.a();
            k k4 = ((k)object).d(f3);
            ArrayList<d3.d> arrayList = new ArrayList<d3.d>();
            object2 = (k.b)t.r(k3, 0);
            int n3 = 1;
            int n4 = 1;
            for (object = (k.b)t.r(k4, 0); object2 != null && object != null; object = (k.b)((d3.d)object).b()) {
                float f4 = n3 == k3.size() ? 1.0f : ((k.b)object2).c();
                float f5 = n4 == k4.size() ? 1.0f : ((f)object3).b(y.j(((k.b)object).c() + f3, 1.0f));
                float f6 = Math.min(f4, f5);
                n.a();
                float f7 = 1.0E-6f + f6;
                if (f4 > f7) {
                    n.a();
                    object2 = ((k.b)object2).a(f6);
                } else {
                    object2 = d3.h.a(object2, t.r(k3, n3));
                    ++n3;
                }
                k.b b3 = (k.b)((d3.d)object2).a();
                object2 = (k.b)((d3.d)object2).b();
                if (f5 > f7) {
                    n.a();
                    object = ((k.b)object).a(y.j(((f)object3).a(f6) - f3, 1.0f));
                } else {
                    object = d3.h.a(object, t.r(k4, n4));
                    ++n4;
                }
                k.b b4 = (k.b)((d3.d)object).a();
                n.a();
                arrayList.add(d3.h.a(b3.b(), b4.b()));
            }
            if (object2 == null && object == null) {
                return arrayList;
            }
            throw new IllegalArgumentException("Expected both Polygon's Cubic to be fully matched");
        }
    }
}

