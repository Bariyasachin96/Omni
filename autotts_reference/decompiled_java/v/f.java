/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import v.d;
import v.g;
import v.p;

public class f
implements d {
    public d a = null;
    public boolean b = false;
    public boolean c = false;
    public p d;
    public a e = v.f$a.c;
    public int f;
    public int g;
    public int h = 1;
    public g i = null;
    public boolean j = false;
    public List k = new ArrayList();
    public List l = new ArrayList();

    public f(p p3) {
        this.d = p3;
    }

    @Override
    public void a(d object) {
        block8: {
            block9: {
                block10: {
                    f f3;
                    object = this.l.iterator();
                    while (object.hasNext()) {
                        if (((f)object.next()).j) continue;
                        break block8;
                    }
                    this.c = true;
                    object = this.a;
                    if (object != null) {
                        object.a(this);
                    }
                    if (this.b) {
                        this.d.a(this);
                        return;
                    }
                    Iterator iterator = this.l.iterator();
                    object = null;
                    int n3 = 0;
                    while (iterator.hasNext()) {
                        f3 = (f)iterator.next();
                        if (f3 instanceof g) continue;
                        ++n3;
                        object = f3;
                    }
                    if (object == null || n3 != true || !((f)object).j) break block9;
                    f3 = this.i;
                    if (f3 == null) break block10;
                    if (!f3.j) break block8;
                    this.f = this.h * f3.g;
                }
                this.d(((f)object).g + this.f);
            }
            if ((object = this.a) != null) {
                object.a(this);
            }
        }
    }

    public void b(d d3) {
        this.k.add(d3);
        if (this.j) {
            d3.a(d3);
        }
    }

    public void c() {
        this.l.clear();
        this.k.clear();
        this.j = false;
        this.g = 0;
        this.c = false;
        this.b = false;
    }

    public void d(int n3) {
        if (!this.j) {
            this.j = true;
            this.g = n3;
            for (d d3 : this.k) {
                d3.a(d3);
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.d.b.v());
        stringBuilder.append(":");
        stringBuilder.append((Object)this.e);
        stringBuilder.append("(");
        Object object = this.j ? Integer.valueOf(this.g) : "unresolved";
        stringBuilder.append(object);
        stringBuilder.append(") <t=");
        stringBuilder.append(this.l.size());
        stringBuilder.append(":d=");
        stringBuilder.append(this.k.size());
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public static final class a
    extends Enum {
        public static final /* enum */ a c = new a("UNKNOWN", 0);
        public static final /* enum */ a d = new a("HORIZONTAL_DIMENSION", 1);
        public static final /* enum */ a e = new a("VERTICAL_DIMENSION", 2);
        public static final /* enum */ a f = new a("LEFT", 3);
        public static final /* enum */ a g = new a("RIGHT", 4);
        public static final /* enum */ a h = new a("TOP", 5);
        public static final /* enum */ a i = new a("BOTTOM", 6);
        public static final /* enum */ a j = new a("BASELINE", 7);
        public static final a[] k = v.f$a.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ a[] a() {
            return new a[]{c, d, e, f, g, h, i, j};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])k.clone();
        }
    }
}

