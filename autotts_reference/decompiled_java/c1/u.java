/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.d;
import c1.e;
import c1.g;
import c1.p;
import c1.q;
import c1.y;
import e3.k;
import e3.l;
import e3.t;
import java.util.List;

public final class u {
    public static final a e = new a(null);
    public final List a;
    public final float b;
    public final float c;
    public final List d;

    public u(List object, float f3, float f4) {
        int n3;
        d d3;
        Object object2;
        int n4;
        List list;
        block20: {
            Object object3;
            o3.k.e(object, "features");
            this.a = object;
            this.b = f3;
            this.c = f4;
            list = k.c();
            n4 = object.size();
            int n5 = 0;
            d d4 = null;
            d d5 = null;
            if (n4 > 0 && ((g)object.get(0)).a().size() == 3) {
                object3 = ((d)((g)object.get(0)).a().get(1)).m(0.5f);
                object2 = (d)((d3.d)object3).a();
                d3 = (d)((d3.d)object3).b();
                object3 = l.i(((g)object.get(0)).a().get(0), object2);
                object2 = l.i(d3, ((g)object.get(0)).a().get(2));
            } else {
                object2 = null;
                object3 = null;
            }
            int n6 = object.size();
            if (n6 >= 0) {
                n4 = 0;
                d4 = null;
                while (true) {
                    if (n4 == 0 && object2 != null) {
                        object = object2;
                    } else if (n4 == this.a.size()) {
                        if (object3 == null) {
                            object = d4;
                            d3 = d5;
                            break block20;
                        }
                        object = object3;
                    } else {
                        object = ((g)this.a.get(n4)).a();
                    }
                    int n7 = object.size();
                    for (n3 = 0; n3 < n7; ++n3) {
                        d d6;
                        d d7 = (d)object.get(n3);
                        if (!d7.p()) {
                            if (d4 != null) {
                                list.add(d4);
                            }
                            if (d5 == null) {
                                d3 = d4 = d7;
                                d6 = d4;
                            } else {
                                d3 = d7;
                                d6 = d5;
                            }
                        } else {
                            d6 = d5;
                            d3 = d4;
                            if (d4 != null) {
                                d4.j()[6] = d7.d();
                                d4.j()[7] = d7.e();
                                d3 = d4;
                                d6 = d5;
                            }
                        }
                        d5 = d6;
                        d4 = d3;
                    }
                    d3 = d5;
                    object = d4;
                    if (n4 != n6) {
                        ++n4;
                        continue;
                    }
                    break block20;
                    break;
                }
            }
            d3 = null;
            object = d4;
        }
        if (object != null && d3 != null) {
            list.add(c1.e.a(((d)object).b(), ((d)object).c(), ((d)object).f(), ((d)object).g(), ((d)object).h(), ((d)object).i(), d3.b(), d3.c()));
        }
        object2 = k.a(list);
        this.d = object2;
        object = object2.get(object2.size() - 1);
        n3 = object2.size();
        for (n4 = n5; n4 < n3; ++n4) {
            object2 = (d)this.d.get(n4);
            f3 = ((d)object2).b();
            if (!(Math.abs(f3 - ((d)(object = (d)object)).d()) > 1.0E-4f) && !(Math.abs(((d)object2).c() - ((d)object).e()) > 1.0E-4f)) {
                object = object2;
                continue;
            }
            throw new IllegalArgumentException("RoundedPolygon must be contiguous, with the anchor points of all curves matching the anchor points of the preceding and succeeding cubics");
        }
    }

    public static /* synthetic */ float[] c(u u3, float[] fArray, boolean bl, int n3, Object object) {
        if ((n3 & 1) != 0) {
            fArray = new float[4];
        }
        if ((n3 & 2) != 0) {
            bl = true;
        }
        return u3.b(fArray, bl);
    }

    public final float[] a(float[] fArray) {
        o3.k.e(fArray, "bounds");
        return u.c(this, fArray, false, 2, null);
    }

    public final float[] b(float[] fArray, boolean bl) {
        o3.k.e(fArray, "bounds");
        if (fArray.length >= 4) {
            int n3 = this.d.size();
            float f3 = Float.MIN_VALUE;
            float f4 = Float.MAX_VALUE;
            float f5 = Float.MAX_VALUE;
            float f6 = Float.MIN_VALUE;
            for (int i3 = 0; i3 < n3; ++i3) {
                ((d)this.d.get(i3)).a(fArray, bl);
                f4 = Math.min(f4, fArray[0]);
                f5 = Math.min(f5, fArray[1]);
                f3 = Math.max(f3, fArray[2]);
                f6 = Math.max(f6, fArray[3]);
            }
            fArray[0] = f4;
            fArray[1] = f5;
            fArray[2] = f3;
            fArray[3] = f6;
            return fArray;
        }
        throw new IllegalArgumentException("Required bounds size of 4");
    }

    public final float[] d(float[] fArray) {
        o3.k.e(fArray, "bounds");
        if (fArray.length >= 4) {
            float f3;
            int n3 = this.d.size();
            float f4 = 0.0f;
            for (int i3 = 0; i3 < n3; ++i3) {
                d d3 = (d)this.d.get(i3);
                f3 = y.e(d3.b() - this.b, d3.c() - this.c);
                long l3 = d3.k(0.5f);
                f4 = Math.max(f4, Math.max(f3, y.e(p.g(l3) - this.b, p.h(l3) - this.c)));
            }
            f3 = (float)Math.sqrt(f4);
            float f5 = this.b;
            fArray[0] = f5 - f3;
            f4 = this.c;
            fArray[1] = f4 - f3;
            fArray[2] = f5 + f3;
            fArray[3] = f4 + f3;
            return fArray;
        }
        throw new IllegalArgumentException("Required bounds size of 4");
    }

    public final float e() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof u)) {
            return false;
        }
        return o3.k.a(this.a, ((u)object).a);
    }

    public final float f() {
        return this.c;
    }

    public final List g() {
        return this.a;
    }

    public final u h(q q3) {
        o3.k.e(q3, "f");
        long l3 = p.m(o.e.b(this.b, this.c), q3);
        List list = k.c();
        int n3 = this.a.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            list.add(((g)this.a.get(i3)).b(q3));
        }
        return new u(k.a(list), p.g(l3), p.h(l3));
    }

    public int hashCode() {
        return ((Object)this.a).hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RoundedPolygon. Cubics = ");
        stringBuilder.append(t.v(this.d, null, null, null, 0, null, null, 63, null));
        stringBuilder.append(" || Features = ");
        stringBuilder.append(t.v(this.a, null, null, null, 0, null, null, 63, null));
        stringBuilder.append(" || Center = (");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append(this.c);
        stringBuilder.append(")]");
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(o3.g g3) {
            this();
        }
    }
}

