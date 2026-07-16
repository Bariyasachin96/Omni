/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.carousel;

import com.google.android.material.carousel.a;
import com.google.android.material.carousel.c;
import f2.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class d {
    public final c a;
    public final List b;
    public final List c;
    public final float[] d;
    public final float[] e;
    public final float f;
    public final float g;

    public d(c c3, List list, List list2) {
        float f3;
        float f4;
        this.a = c3;
        this.b = Collections.unmodifiableList(list);
        this.c = Collections.unmodifiableList(list2);
        this.f = f4 = ((c)list.get((int)(list.size() - 1))).d().a - c3.d().a;
        this.g = f3 = c3.k().a - ((c)list2.get((int)(list2.size() - 1))).k().a;
        this.d = com.google.android.material.carousel.d.m(f4, list, true);
        this.e = com.google.android.material.carousel.d.m(f3, list2, false);
    }

    public static int b(c c3, float f3) {
        for (int i3 = c3.j(); i3 < c3.h().size(); ++i3) {
            if (f3 != ((c.c)c3.h().get((int)i3)).c) continue;
            return i3;
        }
        return c3.h().size() - 1;
    }

    public static int c(c c3) {
        for (int i3 = 0; i3 < c3.h().size(); ++i3) {
            if (((c.c)c3.h().get((int)i3)).e) continue;
            return i3;
        }
        return -1;
    }

    public static int d(c c3, float f3) {
        for (int i3 = c3.c() - 1; i3 >= 0; --i3) {
            if (f3 != ((c.c)c3.h().get((int)i3)).c) continue;
            return i3;
        }
        return 0;
    }

    public static int e(c c3) {
        for (int i3 = c3.h().size() - 1; i3 >= 0; --i3) {
            if (((c.c)c3.h().get((int)i3)).e) continue;
            return i3;
        }
        return -1;
    }

    public static d f(b b3, c c3, float f3, float f4, float f5, a.a a4) {
        return new d(c3, com.google.android.material.carousel.d.p(b3, c3, f3, f4, a4), com.google.android.material.carousel.d.n(b3, c3, f3, f5, a4));
    }

    public static float[] m(float f3, List list, boolean bl) {
        int n3 = list.size();
        float[] fArray = new float[n3];
        for (int i3 = 1; i3 < n3; ++i3) {
            int n4 = i3 - 1;
            c c3 = (c)list.get(n4);
            c c4 = (c)list.get(i3);
            float f4 = bl ? c4.d().a - c3.d().a : c3.k().a - c4.k().a;
            f4 /= f3;
            f4 = i3 == n3 - 1 ? 1.0f : fArray[n4] + f4;
            fArray[i3] = f4;
        }
        return fArray;
    }

    public static List n(b object, c c3, float f3, float f4, a.a a4) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(c3);
        int n3 = com.google.android.material.carousel.d.e(c3);
        int n4 = object.f() ? object.a() : object.c();
        if (!com.google.android.material.carousel.d.r((b)object, c3) && n3 != -1) {
            int n5 = n3 - c3.j();
            float f5 = c3.d().b - c3.d().d / 2.0f;
            if (n5 <= 0 && c3.i().f > 0.0f) {
                arrayList.add(com.google.android.material.carousel.d.x(c3, f5 - c3.i().f - f4, n4));
                return arrayList;
            }
            float f6 = 0.0f;
            for (int i3 = 0; i3 < n5; ++i3) {
                object = (c)arrayList.get(arrayList.size() - 1);
                int n6 = n3 - i3;
                n6 = ++n6 < c3.h().size() ? com.google.android.material.carousel.d.d((c)object, ((c.c)c3.h().get((int)n6)).c) + 1 : 0;
                int n7 = c3.c();
                int n8 = c3.j();
                c c4 = com.google.android.material.carousel.d.t((c)object, n3, n6, f5 - (f6 += ((c.c)c3.h().get((int)n6)).f), n7 + i3 + 1, n8 + i3 + 1, n4);
                object = c4;
                if (i3 == n5 - 1) {
                    object = c4;
                    if (f4 > 0.0f) {
                        object = com.google.android.material.carousel.d.u(c4, f4, n4, false, f3, a4);
                    }
                }
                arrayList.add(object);
            }
        } else if (f4 > 0.0f) {
            arrayList.add(com.google.android.material.carousel.d.u(c3, f4, n4, false, f3, a4));
        }
        return arrayList;
    }

    public static float[] o(List list, float f3, float[] fArray) {
        int n3 = list.size();
        float f4 = fArray[0];
        for (int i3 = 1; i3 < n3; ++i3) {
            float f5 = fArray[i3];
            if (f3 <= f5) {
                return new float[]{a2.a.b(0.0f, 1.0f, f4, f5, f3), i3 - 1, i3};
            }
            f4 = f5;
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    public static List p(b object, c c3, float f3, float f4, a.a a4) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(c3);
        int n3 = com.google.android.material.carousel.d.c(c3);
        int n4 = object.f() ? object.a() : object.c();
        if (!com.google.android.material.carousel.d.q(c3) && n3 != -1) {
            int n5 = c3.c() - n3;
            float f5 = c3.d().b - c3.d().d / 2.0f;
            if (n5 <= 0 && c3.b().f > 0.0f) {
                arrayList.add(com.google.android.material.carousel.d.x(c3, f5 + c3.b().f + f4, n4));
                return arrayList;
            }
            int n6 = 0;
            float f6 = 0.0f;
            int n7 = n4;
            for (n4 = n6; n4 < n5; ++n4) {
                object = (c)arrayList.get(arrayList.size() - 1);
                int n8 = n3 + n4;
                n6 = c3.h().size() - 1;
                f6 += ((c.c)c3.h().get((int)n8)).f;
                if (--n8 >= 0) {
                    n6 = com.google.android.material.carousel.d.b((c)object, ((c.c)c3.h().get((int)n8)).c) - 1;
                }
                c c4 = com.google.android.material.carousel.d.t((c)object, n3, n6, f5 + f6, c3.c() - n4 - 1, c3.j() - n4 - 1, n7);
                object = c4;
                if (n4 == n5 - 1) {
                    object = c4;
                    if (f4 > 0.0f) {
                        object = com.google.android.material.carousel.d.u(c4, f4, n7, true, f3, a4);
                    }
                }
                arrayList.add(object);
            }
        } else if (f4 > 0.0f) {
            arrayList.add(com.google.android.material.carousel.d.u(c3, f4, n4, true, f3, a4));
        }
        return arrayList;
    }

    public static boolean q(c c3) {
        return c3.b().b - c3.b().d / 2.0f >= 0.0f && c3.b() == c3.e();
    }

    public static boolean r(b b3, c c3) {
        int n3 = b3.c();
        if (b3.f()) {
            n3 = b3.a();
        }
        return c3.i().b + c3.i().d / 2.0f <= (float)n3 && c3.i() == c3.l();
    }

    public static c s(List list, float f3, float[] fArray) {
        fArray = com.google.android.material.carousel.d.o(list, f3, fArray);
        return com.google.android.material.carousel.c.o((c)list.get((int)fArray[1]), (c)list.get((int)fArray[2]), fArray[0]);
    }

    public static c t(c object, int n3, int n4, float f3, int n5, int n6, int n7) {
        ArrayList<c.c> arrayList = new ArrayList<c.c>(((c)object).h());
        arrayList.add(n4, (c.c)arrayList.remove(n3));
        object = new c.b(((c)object).g(), n7);
        for (n3 = 0; n3 < arrayList.size(); ++n3) {
            c.c c3 = (c.c)arrayList.get(n3);
            float f4 = c3.d;
            float f5 = f4 / 2.0f;
            boolean bl = n3 >= n5 && n3 <= n6;
            ((c.b)object).e(f3 + f5, c3.c, f4, bl, c3.e, c3.f);
            f3 += c3.d;
        }
        return ((c.b)object).i();
    }

    public static c u(c c3, float f3, int n3, boolean bl, float f4, a.a a4) {
        if (com.google.android.material.carousel.d$a.a[a4.ordinal()] != 1) {
            return com.google.android.material.carousel.d.w(c3, f3, n3, bl);
        }
        return com.google.android.material.carousel.d.v(c3, f3, n3, bl, f4);
    }

    public static c v(c c3, float f3, int n3, boolean bl, float f4) {
        ArrayList arrayList = new ArrayList(c3.h());
        c.b b3 = new c.b(c3.g(), n3);
        float f5 = f3 / (float)c3.m();
        if (!bl) {
            f3 = 0.0f;
        }
        for (n3 = 0; n3 < arrayList.size(); ++n3) {
            c.c c4 = (c.c)arrayList.get(n3);
            if (c4.e) {
                b3.e(c4.b, c4.c, c4.d, false, true, c4.f);
                continue;
            }
            boolean bl2 = n3 >= c3.c() && n3 <= c3.j();
            float f6 = c4.d - f5;
            float f7 = com.google.android.material.carousel.a.b(f6, c3.g(), f4);
            float f8 = f6 / 2.0f + f3;
            float f9 = Math.abs(f8 - c4.b);
            float f10 = c4.f;
            float f11 = bl ? f9 : 0.0f;
            if (bl) {
                f9 = 0.0f;
            }
            b3.f(f8, f7, f6, bl2, false, f10, f11, f9);
            f3 += f6;
        }
        return b3.i();
    }

    public static c w(c c3, float f3, int n3, boolean bl) {
        ArrayList arrayList = new ArrayList(c3.h());
        c.b b3 = new c.b(c3.g(), n3);
        int n4 = bl ? 0 : arrayList.size() - 1;
        for (int i3 = 0; i3 < arrayList.size(); ++i3) {
            c.c c4 = (c.c)arrayList.get(i3);
            if (c4.e && i3 == n4) {
                b3.e(c4.b, c4.c, c4.d, false, true, c4.f);
                continue;
            }
            float f4 = c4.b;
            f4 = bl ? (f4 += f3) : (f4 -= f3);
            float f5 = bl ? f3 : 0.0f;
            float f6 = bl ? 0.0f : f3;
            boolean bl2 = i3 >= c3.c() && i3 <= c3.j();
            float f7 = c4.c;
            float f8 = c4.d;
            boolean bl3 = c4.e;
            float f9 = bl ? Math.max(0.0f, f8 / 2.0f + f4 - (float)n3) : Math.min(0.0f, f4 - f8 / 2.0f);
            b3.f(f4, f7, f8, bl2, bl3, Math.abs(f9), f5, f6);
        }
        return b3.i();
    }

    public static c x(c c3, float f3, int n3) {
        return com.google.android.material.carousel.d.t(c3, 0, 0, f3, c3.c(), c3.j(), n3);
    }

    public final c a(List list, float f3, float[] fArray) {
        if ((fArray = com.google.android.material.carousel.d.o(list, f3, fArray))[0] >= 0.5f) {
            return (c)list.get((int)fArray[2]);
        }
        return (c)list.get((int)fArray[1]);
    }

    public c g() {
        return this.a;
    }

    public c h() {
        List list = this.c;
        return (c)list.get(list.size() - 1);
    }

    public Map i(int n3, int n4, int n5, boolean bl) {
        List list;
        float f3;
        int n6;
        float f4 = this.a.g();
        HashMap<Integer, c> hashMap = new HashMap<Integer, c>();
        int n7 = 0;
        int n8 = 0;
        while (true) {
            int n9;
            block10: {
                block9: {
                    n9 = -1;
                    if (n7 >= n3) break;
                    n6 = bl ? n3 - n7 - 1 : n7;
                    f3 = n6;
                    if (!bl) {
                        n9 = 1;
                    }
                    if (f3 * f4 * (float)n9 > (float)n5 - this.g) break block9;
                    n9 = n8;
                    if (n7 < n3 - this.c.size()) break block10;
                }
                list = this.c;
                hashMap.put(n6, (c)list.get(j0.a.b(n8, 0, list.size() - 1)));
                n9 = n8 + 1;
            }
            ++n7;
            n8 = n9;
        }
        n7 = 0;
        for (n5 = n3 - 1; n5 >= 0; --n5) {
            block12: {
                block11: {
                    n8 = bl ? n3 - n5 - 1 : n5;
                    f3 = n8;
                    if (f3 * f4 * (float)(n6 = bl ? -1 : 1) < (float)n4 + this.f) break block11;
                    n6 = n7;
                    if (n5 >= this.b.size()) break block12;
                }
                list = this.b;
                hashMap.put(n8, (c)list.get(j0.a.b(n7, 0, list.size() - 1)));
                n6 = n7 + 1;
            }
            n7 = n6;
        }
        return hashMap;
    }

    public c j(float f3, float f4, float f5) {
        return this.k(f3, f4, f5, false);
    }

    public c k(float f3, float f4, float f5, boolean bl) {
        block8: {
            float[] fArray;
            List list;
            block7: {
                float f6;
                block6: {
                    f6 = this.f + f4;
                    float f7 = f5 - this.g;
                    float f8 = this.l().b().g;
                    float f9 = this.h().b().h;
                    float f10 = f6;
                    if (this.f == f8) {
                        f10 = f6 + f8;
                    }
                    f6 = f7;
                    if (this.g == f9) {
                        f6 = f7 - f9;
                    }
                    if (!(f3 < f10)) break block6;
                    f3 = a2.a.b(1.0f, 0.0f, f4, f10, f3);
                    list = this.b;
                    fArray = this.d;
                    break block7;
                }
                if (!(f3 > f6)) break block8;
                f3 = a2.a.b(0.0f, 1.0f, f6, f5, f3);
                list = this.c;
                fArray = this.e;
            }
            if (bl) {
                return this.a(list, f3, fArray);
            }
            return com.google.android.material.carousel.d.s(list, f3, fArray);
        }
        return this.a;
    }

    public c l() {
        List list = this.b;
        return (c)list.get(list.size() - 1);
    }
}

