/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.c;
import c1.p;
import c1.u;
import c1.v;
import c1.y;
import e3.l;
import e3.q;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import o3.k;
import r3.e;

public abstract class w {
    public static final u a(u.a a4) {
        k.e(a4, "<this>");
        return w.d(a4, 0, 0.0f, 0.0f, 0.0f, 15, null);
    }

    public static final u b(u.a a4, int n3) {
        k.e(a4, "<this>");
        return w.d(a4, n3, 0.0f, 0.0f, 0.0f, 14, null);
    }

    public static final u c(u.a a4, int n3, float f3, float f4, float f5) {
        k.e(a4, "<this>");
        if (n3 >= 3) {
            return v.d(n3, f3 / (float)Math.cos(y.g() / (float)n3), f4, f5, new c(f3, 0.0f, 2, null), null, 32, null);
        }
        throw new IllegalArgumentException("Circle must have at least three vertices");
    }

    public static /* synthetic */ u d(u.a a4, int n3, float f3, float f4, float f5, int n4, Object object) {
        if ((n4 & 1) != 0) {
            n3 = 8;
        }
        if ((n4 & 2) != 0) {
            f3 = 1.0f;
        }
        if ((n4 & 4) != 0) {
            f4 = 0.0f;
        }
        if ((n4 & 8) != 0) {
            f5 = 0.0f;
        }
        return w.c(a4, n3, f3, f4, f5);
    }

    public static final u e(u.a a4, float f3, float f4, c c3, List list, float f5, float f6) {
        k.e(a4, "<this>");
        k.e(c3, "rounding");
        float f7 = 2;
        float f8 = f3 / f7;
        f3 = f5 - f8;
        f7 = f4 / f7;
        f4 = f6 - f7;
        return v.c(new float[]{f8 += f5, f7 += f6, f3, f7, f3, f4, f8, f4}, c3, list, f5, f6);
    }

    public static final u f(u.a a4, int n3, float f3, float f4, c c3) {
        k.e(a4, "<this>");
        k.e(c3, "rounding");
        return w.h(a4, n3, f3, f4, c3, null, null, 0.0f, 0.0f, 240, null);
    }

    public static final u g(u.a arrayList, int n3, float f3, float f4, c c3, c c4, List arrayList2, float f5, float f6) {
        k.e(arrayList, "<this>");
        k.e(c3, "rounding");
        if (!(f3 <= 0.0f) && !(f4 <= 0.0f)) {
            if (!(f4 >= f3)) {
                arrayList = arrayList2;
                if (arrayList2 == null) {
                    arrayList = arrayList2;
                    if (c4 != null) {
                        arrayList = e.e(0, n3);
                        arrayList2 = new ArrayList();
                        Iterator iterator = arrayList.iterator();
                        while (true) {
                            arrayList = arrayList2;
                            if (!iterator.hasNext()) break;
                            ((e3.y)iterator).nextInt();
                            q.m(arrayList2, l.h(c3, c4));
                        }
                    }
                }
                return v.c(w.i(n3, f3, f4, f5, f6), c3, arrayList, f5, f6);
            }
            throw new IllegalArgumentException("innerRadius must be less than radius");
        }
        throw new IllegalArgumentException("Star radii must both be greater than 0");
    }

    public static /* synthetic */ u h(u.a a4, int n3, float f3, float f4, c c3, c c4, List object, float f5, float f6, int n4, Object object2) {
        block6: {
            if ((n4 & 2) != 0) {
                f3 = 1.0f;
            }
            if ((n4 & 4) != 0) {
                f4 = 0.5f;
            }
            if ((n4 & 8) != 0) {
                c3 = c.d;
            }
            object2 = null;
            if ((n4 & 0x10) != 0) {
                c4 = null;
            }
            if ((n4 & 0x20) != 0) {
                object = object2;
            }
            if ((n4 & 0x40) != 0) {
                f5 = 0.0f;
            }
            if ((n4 & 0x80) == 0) break block6;
            f6 = 0.0f;
        }
        return w.g(a4, n3, f3, f4, c3, c4, (List)object, f5, f6);
    }

    public static final float[] i(int n3, float f3, float f4, float f5, float f6) {
        float[] fArray = new float[n3 * 4];
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6 = n5;
            if (n4 >= n3) break;
            float f7 = y.g();
            float f8 = n3;
            long l3 = y.l(f3, f7 / f8 * (float)2 * (float)n4, 0L, 4, null);
            fArray[n6] = p.g(l3) + f5;
            fArray[n6 + 1] = p.h(l3) + f6;
            l3 = y.l(f4, y.g() / f8 * (float)(n4 * 2 + 1), 0L, 4, null);
            fArray[n6 + 2] = p.g(l3) + f5;
            n5 = n6 + 4;
            fArray[n6 + 3] = p.h(l3) + f6;
            ++n4;
        }
        return fArray;
    }
}

