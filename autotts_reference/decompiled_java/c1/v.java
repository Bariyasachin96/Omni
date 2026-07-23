/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.c;
import c1.d;
import c1.g;
import c1.p;
import c1.t;
import c1.u;
import c1.y;
import d3.h;
import e3.k;
import java.util.ArrayList;
import java.util.List;
import o.e;
import o.f;
import o.m;

public abstract class v {
    public static final u a(int n3, float f3, float f4, float f5, c c3) {
        o3.k.e(c3, "rounding");
        return v.d(n3, f3, f4, f5, c3, null, 32, null);
    }

    public static final u b(int n3, float f3, float f4, float f5, c c3, List list) {
        o3.k.e(c3, "rounding");
        return v.c(v.f(n3, f3, f4, f5), c3, list, f4, f5);
    }

    public static final u c(float[] fArray, c object, List arrayList, float f3, float f4) {
        Float f5 = Float.valueOf(1.0f);
        o3.k.e(fArray, "vertices");
        o3.k.e(object, "rounding");
        if (fArray.length >= 6) {
            int n3 = fArray.length;
            int n4 = 1;
            if (n3 % 2 != 1) {
                float f6;
                float f7;
                long l3;
                int n5;
                int n6;
                Object object2;
                if (arrayList != null && arrayList.size() * 2 != fArray.length) {
                    throw new IllegalArgumentException("perVertexRounding list should be either null or the same size as the number of vertices (vertices.size / 2)");
                }
                ArrayList<List> arrayList2 = new ArrayList<List>();
                int n7 = fArray.length / 2;
                ArrayList<t> arrayList3 = new ArrayList<t>();
                int n8 = 0;
                n3 = 0;
                while (n3 < n7) {
                    if (arrayList == null || (object2 = (c)arrayList.get(n3)) == null) {
                        object2 = object;
                    }
                    int n9 = (n3 + n7 - n4) % n7 * 2;
                    n6 = n3 + 1;
                    n5 = n6 % n7 * 2;
                    l3 = e.b(fArray[n9], fArray[n9 + n4]);
                    arrayList3.add(new t(l3, e.b(fArray[n3 *= 2], fArray[n3 + n4]), e.b(fArray[n5], fArray[n5 + 1]), (c)object2, null));
                    n3 = n6;
                }
                object = r3.e.e(0, n7);
                arrayList = new ArrayList<Object>(e3.m.l((Iterable)object, 10));
                object2 = object.iterator();
                while (object2.hasNext()) {
                    n6 = ((e3.y)object2).nextInt();
                    f7 = ((t)arrayList3.get(n6)).f();
                    n3 = (n6 + 1) % n7;
                    f7 += ((t)arrayList3.get(n3)).f();
                    f6 = ((t)arrayList3.get(n6)).e() + ((t)arrayList3.get(n3)).e();
                    float f8 = fArray[n6 *= 2];
                    float f9 = fArray[n6 + 1];
                    object = f7 > (f9 = y.d(f8 - fArray[n3 *= 2], f9 - fArray[n3 + 1])) ? h.a(Float.valueOf(f9 / f7), Float.valueOf(0.0f)) : (f6 > f9 ? h.a(f5, Float.valueOf((f9 - f7) / (f6 - f7))) : h.a(f5, f5));
                    arrayList.add(object);
                }
                for (n3 = 0; n3 < n7; ++n3) {
                    object = new m(2);
                    for (n6 = 0; n6 < 2; ++n6) {
                        object2 = (d3.d)arrayList.get((n3 + n7 - 1 + n6) % n7);
                        f6 = ((Number)((d3.d)object2).a()).floatValue();
                        f7 = ((Number)((d3.d)object2).b()).floatValue();
                        ((m)object).h(((t)arrayList3.get(n3)).f() * f6 + (((t)arrayList3.get(n3)).e() - ((t)arrayList3.get(n3)).f()) * f7);
                    }
                    arrayList2.add(((t)arrayList3.get(n3)).d(((f)object).b(0), ((f)object).b(n4)));
                }
                object = new ArrayList();
                n3 = n8;
                while (n3 < n7) {
                    n6 = n3 + 1;
                    n8 = n6 % n7;
                    n5 = n3 * 2;
                    long l4 = e.b(fArray[n5], fArray[n5 + n4]);
                    n5 = (n3 + n7 - n4) % n7 * 2;
                    l3 = e.b(fArray[n5], fArray[n5 + n4]);
                    n5 = n8 * 2;
                    long l5 = e.b(fArray[n5], fArray[n5 + n4]);
                    boolean bl = p.a(p.j(l4, l3), p.j(l5, l4));
                    object.add(new g.a((List)arrayList2.get(n3), l4, ((t)arrayList3.get(n3)).c(), bl, null));
                    object.add(new g.b(k.d(d.b.b(((d)e3.t.w((List)arrayList2.get(n3))).d(), ((d)e3.t.w((List)arrayList2.get(n3))).e(), ((d)e3.t.p((List)arrayList2.get(n8))).b(), ((d)e3.t.p((List)arrayList2.get(n8))).c()))));
                    n3 = n6;
                }
                l3 = f3 == Float.MIN_VALUE || f4 == Float.MIN_VALUE ? v.e(fArray) : e.b(f3, f4);
                return new u((List)object, Float.intBitsToFloat((int)(l3 >> 32)), Float.intBitsToFloat((int)(l3 & 0xFFFFFFFFL)));
            }
            throw new IllegalArgumentException("The vertices array should have even size");
        }
        throw new IllegalArgumentException("Polygons must have at least 3 vertices");
    }

    public static /* synthetic */ u d(int n3, float f3, float f4, float f5, c c3, List list, int n4, Object object) {
        if ((n4 & 2) != 0) {
            f3 = 1.0f;
        }
        if ((n4 & 4) != 0) {
            f4 = 0.0f;
        }
        if ((n4 & 8) != 0) {
            f5 = 0.0f;
        }
        if ((n4 & 0x10) != 0) {
            c3 = c.d;
        }
        if ((n4 & 0x20) != 0) {
            list = null;
        }
        return v.b(n3, f3, f4, f5, c3, list);
    }

    public static final long e(float[] fArray) {
        float f3 = 0.0f;
        int n3 = 0;
        float f4 = 0.0f;
        while (n3 < fArray.length) {
            f3 += fArray[n3];
            int n4 = n3 + 2;
            f4 += fArray[n3 + 1];
            n3 = n4;
        }
        float f5 = f3 / (float)fArray.length;
        f3 = 2;
        return e.b(f5 / f3, f4 / (float)fArray.length / f3);
    }

    public static final float[] f(int n3, float f3, float f4, float f5) {
        float[] fArray = new float[n3 * 2];
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6 = n5;
            if (n4 >= n3) break;
            long l3 = p.k(y.l(f3, y.g() / (float)n3 * (float)2 * (float)n4, 0L, 4, null), e.b(f4, f5));
            fArray[n6] = p.g(l3);
            n5 = n6 + 2;
            fArray[n6 + 1] = p.h(l3);
            ++n4;
        }
        return fArray;
    }
}

