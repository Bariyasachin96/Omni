/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.d;
import c1.f;
import c1.g;
import c1.s;
import e3.k;
import e3.l;
import e3.t;
import e3.y;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import r3.c;

public abstract class h {
    public static final List a(List list, List list2) {
        o3.k.e(list, "f1");
        o3.k.e(list2, "f2");
        Iterator iterator = l.f(list2).iterator();
        if (iterator.hasNext()) {
            float f3;
            float f4;
            int n3;
            int n4;
            float f5;
            Object object = (y)iterator;
            int n5 = ((y)object).nextInt();
            if (iterator.hasNext()) {
                f5 = h.b(((s)list.get(0)).a(), ((s)list2.get(n5)).a());
                n4 = n5;
                do {
                    n3 = ((y)object).nextInt();
                    f4 = h.b(((s)list.get(0)).a(), ((s)list2.get(n3)).a());
                    n5 = n4;
                    f3 = f5;
                    if (Float.compare(f5, f4) > 0) {
                        n5 = n3;
                        f3 = f4;
                    }
                    n4 = n5;
                    f5 = f3;
                } while (iterator.hasNext());
            }
            int n6 = list.size();
            int n7 = list2.size();
            object = l.i(list2.get(n5));
            n4 = n5;
            for (n3 = 1; n3 < n6; ++n3) {
                int n8 = n5 - (n6 - n3);
                if (n8 <= n4) {
                    n8 += n7;
                }
                iterator = new c(n4 + 1, n8).iterator();
                if (iterator.hasNext()) {
                    y y3 = (y)iterator;
                    n4 = y3.nextInt();
                    if (iterator.hasNext()) {
                        f5 = h.b(((s)list.get(n3)).a(), ((s)list2.get(n4 % n7)).a());
                        n8 = n4;
                        do {
                            int n9 = y3.nextInt();
                            f4 = h.b(((s)list.get(n3)).a(), ((s)list2.get(n9 % n7)).a());
                            n4 = n8;
                            f3 = f5;
                            if (Float.compare(f5, f4) > 0) {
                                n4 = n9;
                                f3 = f4;
                            }
                            n8 = n4;
                            f5 = f3;
                        } while (iterator.hasNext());
                    }
                    object.add(list2.get(n4 % n7));
                    continue;
                }
                throw new NoSuchElementException();
            }
            return object;
        }
        throw new NoSuchElementException();
    }

    public static final float b(g g3, g g4) {
        o3.k.e(g3, "f1");
        o3.k.e(g4, "f2");
        if (g3 instanceof g.a && g4 instanceof g.a && ((g.a)g3).c() != ((g.a)g4).c()) {
            return Float.MAX_VALUE;
        }
        float f3 = (((d)t.p(g3.a())).b() + ((d)t.w(g3.a())).d()) / 2.0f;
        float f4 = (((d)t.p(g3.a())).c() + ((d)t.w(g3.a())).e()) / 2.0f;
        float f5 = (((d)t.p(g4.a())).b() + ((d)t.w(g4.a())).d()) / 2.0f;
        float f6 = (((d)t.p(g4.a())).c() + ((d)t.w(g4.a())).e()) / 2.0f;
        f5 = f3 - f5;
        return f5 * f5 + (f4 -= f6) * f4;
    }

    public static final f c(List object, List list) {
        int n3;
        o3.k.e(object, "features1");
        o3.k.e(list, "features2");
        List list2 = k.c();
        int n4 = object.size();
        for (n3 = 0; n3 < n4; ++n3) {
            if (!(((s)object.get(n3)).a() instanceof g.a)) continue;
            list2.add(object.get(n3));
        }
        object = k.a(list2);
        list2 = k.c();
        n4 = list.size();
        for (n3 = 0; n3 < n4; ++n3) {
            if (!(((s)list.get(n3)).a() instanceof g.a)) continue;
            list2.add(list.get(n3));
        }
        list = k.a(list2);
        object = object.size() > list.size() ? d3.h.a(h.a(list, (List)object), list) : d3.h.a(object, h.a((List)object, list));
        list = (List)object.a();
        list2 = (List)object.b();
        object = k.c();
        n4 = list.size();
        for (n3 = 0; n3 < n4 && n3 != list2.size(); ++n3) {
            object.add(d3.h.a(Float.valueOf(((s)list.get(n3)).b()), Float.valueOf(((s)list2.get(n3)).b())));
        }
        object = k.a((List)object).toArray(new d3.d[0]);
        return new f(Arrays.copyOf(object, ((d3.d[])object).length));
    }
}

