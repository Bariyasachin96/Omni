/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.d;
import c1.p;
import c1.q;
import e3.k;
import java.util.List;
import o.e;

public abstract class g {
    public final List a;

    public g(List list) {
        o3.k.e(list, "cubics");
        this.a = list;
    }

    public final List a() {
        return this.a;
    }

    public abstract g b(q var1);

    public static final class a
    extends g {
        public final long b;
        public final long c;
        public final boolean d;

        public a(List list, long l3, long l4, boolean bl) {
            o3.k.e(list, "cubics");
            super(list);
            this.b = l3;
            this.c = l4;
            this.d = bl;
        }

        public /* synthetic */ a(List list, long l3, long l4, boolean bl, o3.g g3) {
            this(list, l3, l4, bl);
        }

        @Override
        public g b(q q3) {
            o3.k.e(q3, "f");
            List list = k.c();
            int n3 = this.a().size();
            for (int i3 = 0; i3 < n3; ++i3) {
                list.add(((d)this.a().get(i3)).n(q3));
            }
            return new a(k.a(list), p.m(this.b, q3), p.m(this.c, q3), this.d, null);
        }

        public final boolean c() {
            return this.d;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Corner: vertex=");
            stringBuilder.append((Object)e.f(this.b));
            stringBuilder.append(", center=");
            stringBuilder.append((Object)e.f(this.c));
            stringBuilder.append(", convex=");
            stringBuilder.append(this.d);
            return stringBuilder.toString();
        }
    }

    public static final class b
    extends g {
        public b(List list) {
            o3.k.e(list, "cubics");
            super(list);
        }

        public b c(q q3) {
            o3.k.e(q3, "f");
            List list = k.c();
            int n3 = this.a().size();
            for (int i3 = 0; i3 < n3; ++i3) {
                list.add(((d)this.a().get(i3)).n(q3));
            }
            return new b(k.a(list));
        }

        public String toString() {
            return "Edge";
        }
    }
}

