/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.d;
import c1.g;
import c1.l;
import c1.r;
import c1.s;
import c1.u;
import c1.y;
import d3.h;
import d3.j;
import e3.t;
import java.util.ArrayList;
import java.util.List;
import o.f;
import o.m;
import r3.e;

public final class k
extends e3.b {
    public static final a g = new a(null);
    public final l d;
    public final List e;
    public final List f;

    public k(l object, List list, List list2, f f3) {
        if (f3.c() == list2.size() + 1) {
            if (f3.a() == 0.0f) {
                if (f3.g() == 1.0f) {
                    this.d = object;
                    this.f = list;
                    object = new ArrayList();
                    int n3 = list2.size();
                    int n4 = 0;
                    float f4 = 0.0f;
                    while (n4 < n3) {
                        int n5 = n4 + 1;
                        float f5 = f4;
                        if (f3.b(n5) - f3.b(n4) > 1.0E-4f) {
                            object.add(new b(this, (d)list2.get(n4), f4, f3.b(n5)));
                            f5 = f3.b(n5);
                        }
                        n4 = n5;
                        f4 = f5;
                    }
                    b.f((b)object.get(e3.l.g((List)object)), 0.0f, 1.0f, 1, null);
                    this.e = object;
                    return;
                }
                throw new IllegalArgumentException("Last outline progress value is expected to be one");
            }
            throw new IllegalArgumentException("First outline progress value is expected to be zero");
        }
        throw new IllegalArgumentException("Outline progress size is expected to be the cubics size + 1");
    }

    public /* synthetic */ k(l l3, List list, List list2, f f3, o3.g g3) {
        this(l3, list, list2, f3);
    }

    @Override
    public int a() {
        return this.e.size();
    }

    public final k d(float f3) {
        if (0.0f <= f3 && f3 <= 1.0f) {
            List list;
            int n3;
            float f4;
            Object object;
            int n4;
            Object object2;
            block11: {
                if (f3 < 1.0E-4f) {
                    return this;
                }
                object2 = this.e.iterator();
                int n5 = 0;
                n4 = 0;
                while (object2.hasNext()) {
                    object = (b)object2.next();
                    f4 = ((b)object).d();
                    if (!(f3 <= ((b)object).c()) || !(f4 <= f3)) {
                        ++n4;
                        continue;
                    }
                    break block11;
                }
                n4 = -1;
            }
            object2 = ((b)this.e.get(n4)).a(f3);
            object = (b)((d3.d)object2).a();
            object2 = (b)((d3.d)object2).b();
            r.a();
            object2 = e3.l.i(((b)object2).b());
            int n6 = this.e.size();
            for (n3 = 1; n3 < n6; ++n3) {
                list = this.e;
                object2.add(((b)list.get((n3 + n4) % list.size())).b());
            }
            object2.add(((b)object).b());
            object = new m(this.e.size() + 2);
            n6 = this.e.size();
            for (n3 = 0; n3 < n6 + 2; ++n3) {
                if (n3 == 0) {
                    f4 = 0.0f;
                } else if (n3 == this.e.size() + 1) {
                    f4 = 1.0f;
                } else {
                    int n7 = this.e.size();
                    f4 = y.j(((b)this.e.get((n4 + n3 - 1) % n7)).c() - f3, 1.0f);
                }
                ((m)object).h(f4);
            }
            list = e3.k.c();
            n3 = this.f.size();
            for (n4 = n5; n4 < n3; ++n4) {
                list.add(new s(y.j(((s)this.f.get(n4)).b() - f3, 1.0f), ((s)this.f.get(n4)).a()));
            }
            list = e3.k.a(list);
            return new k(this.d, list, (List)object2, (f)object);
        }
        throw new IllegalArgumentException("Cutting point is expected to be between 0 and 1");
    }

    public b e(int n3) {
        return (b)this.e.get(n3);
    }

    public final List f() {
        return this.f;
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(o3.g g3) {
            this();
        }

        public final k a(l l3, u object) {
            float f3;
            int n3;
            Object object2;
            int n4;
            o3.k.e(l3, "measurer");
            o3.k.e(object, "polygon");
            ArrayList arrayList = new ArrayList();
            ArrayList<d3.d> arrayList2 = new ArrayList<d3.d>();
            int n5 = ((u)object).g().size();
            int n6 = 0;
            for (n4 = 0; n4 < n5; ++n4) {
                object2 = (g)((u)object).g().get(n4);
                int n7 = ((g)object2).a().size();
                for (n3 = 0; n3 < n7; ++n3) {
                    if (object2 instanceof g.a && n3 == ((g)object2).a().size() / 2) {
                        arrayList2.add(h.a(object2, arrayList.size()));
                    }
                    arrayList.add(((g)object2).a().get(n3));
                }
            }
            object = Float.valueOf(0.0f);
            n4 = e3.m.l(arrayList, 9);
            if (n4 == 0) {
                object = e3.k.d(object);
            } else {
                object2 = new ArrayList(n4 + 1);
                ((ArrayList)object2).add(object);
                n3 = arrayList.size();
                for (n4 = 0; n4 < n3; ++n4) {
                    Object object3 = arrayList.get(n4);
                    object3 = (d)object3;
                    f3 = ((Number)object).floatValue();
                    float f4 = l3.a((d)object3);
                    if (f4 >= 0.0f) {
                        object = j.a;
                        object = Float.valueOf(f3 + f4);
                        ((ArrayList)object2).add(object);
                        continue;
                    }
                    throw new IllegalArgumentException("Measured cubic is expected to be greater or equal to zero");
                }
                object = object2;
            }
            f3 = ((Number)t.w((List)object)).floatValue();
            object2 = new m(object.size());
            n3 = object.size();
            for (n4 = 0; n4 < n3; ++n4) {
                ((m)object2).h(((Number)object.get(n4)).floatValue() / f3);
            }
            r.a();
            object = e3.k.c();
            n3 = arrayList2.size();
            for (n4 = n6; n4 < n3; ++n4) {
                n6 = ((Number)((d3.d)arrayList2.get(n4)).d()).intValue();
                object.add(new s((((f)object2).b(n6) + ((f)object2).b(n6 + 1)) / (float)2, (g)((d3.d)arrayList2.get(n4)).c()));
            }
            return new k(l3, e3.k.a((List)object), arrayList, (f)object2, null);
        }
    }

    public final class b {
        public final d a;
        public final float b;
        public float c;
        public float d;
        public final k e;

        public b(k k3, d d3, float f3, float f4) {
            o3.k.e(d3, "cubic");
            this.e = k3;
            this.a = d3;
            if (f4 >= f3) {
                this.b = k3.d.a(d3);
                this.c = f3;
                this.d = f4;
                return;
            }
            throw new IllegalArgumentException("endOutlineProgress is expected to be equal or greater than startOutlineProgress");
        }

        public static /* synthetic */ void f(b b3, float f3, float f4, int n3, Object object) {
            if ((n3 & 1) != 0) {
                f3 = b3.c;
            }
            if ((n3 & 2) != 0) {
                f4 = b3.d;
            }
            b3.e(f3, f4);
        }

        public final d3.d a(float f3) {
            f3 = r3.e.c(f3, this.c, this.d);
            float f4 = this.d;
            float f5 = this.c;
            f5 = (f3 - f5) / (f4 - f5);
            f5 = this.e.d.b(this.a, f5 * this.b);
            if (0.0f <= f5 && f5 <= 1.0f) {
                r.a();
                Object object = this.a.m(f5);
                d d3 = (d)((d3.d)object).a();
                object = (d)((d3.d)object).b();
                return h.a(new b(this.e, d3, this.c, f3), new b(this.e, (d)object, f3, this.d));
            }
            throw new IllegalArgumentException("Cubic cut point is expected to be between 0 and 1");
        }

        public final d b() {
            return this.a;
        }

        public final float c() {
            return this.d;
        }

        public final float d() {
            return this.c;
        }

        public final void e(float f3, float f4) {
            if (f4 >= f3) {
                this.c = f3;
                this.d = f4;
                return;
            }
            throw new IllegalArgumentException("endOutlineProgress is expected to be equal or greater than startOutlineProgress");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MeasuredCubic(outlineProgress=[");
            stringBuilder.append(this.c);
            stringBuilder.append(" .. ");
            stringBuilder.append(this.d);
            stringBuilder.append("], size=");
            stringBuilder.append(this.b);
            stringBuilder.append(", cubic=");
            stringBuilder.append(this.a);
            stringBuilder.append(')');
            return stringBuilder.toString();
        }
    }
}

