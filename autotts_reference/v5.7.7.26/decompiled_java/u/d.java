/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import java.util.HashSet;
import r.c;
import r.i;
import u.e;
import u.h;
import v.o;

public class d {
    public HashSet a = null;
    public int b;
    public boolean c;
    public final e d;
    public final a e;
    public d f;
    public int g = 0;
    public int h = Integer.MIN_VALUE;
    public i i;

    public d(e e3, a a4) {
        this.d = e3;
        this.e = a4;
    }

    public boolean a(d d3, int n3) {
        return this.b(d3, n3, Integer.MIN_VALUE, false);
    }

    public boolean b(d object, int n3, int n4, boolean bl) {
        if (object == null) {
            this.q();
            return true;
        }
        if (!bl && !this.p((d)object)) {
            return false;
        }
        this.f = object;
        if (((d)object).a == null) {
            ((d)object).a = new HashSet();
        }
        if ((object = this.f.a) != null) {
            ((HashSet)object).add(this);
        }
        this.g = n3;
        this.h = n4;
        return true;
    }

    public void c(int n3, ArrayList arrayList, o o3) {
        Object object = this.a;
        if (object != null) {
            object = ((HashSet)object).iterator();
            while (object.hasNext()) {
                v.i.a(((d)object.next()).d, n3, arrayList, o3);
            }
        }
    }

    public HashSet d() {
        return this.a;
    }

    public int e() {
        if (!this.c) {
            return 0;
        }
        return this.b;
    }

    public int f() {
        d d3;
        if (this.d.X() == 8) {
            return 0;
        }
        if (this.h != Integer.MIN_VALUE && (d3 = this.f) != null && d3.d.X() == 8) {
            return this.h;
        }
        return this.g;
    }

    public final d g() {
        switch (this.e.ordinal()) {
            default: {
                throw new AssertionError((Object)this.e.name());
            }
            case 4: {
                return this.d.R;
            }
            case 3: {
                return this.d.Q;
            }
            case 2: {
                return this.d.T;
            }
            case 1: {
                return this.d.S;
            }
            case 0: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
        }
        return null;
    }

    public e h() {
        return this.d;
    }

    public i i() {
        return this.i;
    }

    public d j() {
        return this.f;
    }

    public a k() {
        return this.e;
    }

    public boolean l() {
        Object object = this.a;
        if (object == null) {
            return false;
        }
        object = ((HashSet)object).iterator();
        while (object.hasNext()) {
            if (!((d)object.next()).g().o()) continue;
            return true;
        }
        return false;
    }

    public boolean m() {
        HashSet hashSet = this.a;
        if (hashSet == null) {
            return false;
        }
        return hashSet.size() > 0;
    }

    public boolean n() {
        return this.c;
    }

    public boolean o() {
        return this.f != null;
    }

    public boolean p(d d3) {
        a a4;
        if (d3 == null) {
            return false;
        }
        a a5 = d3.k();
        if (a5 == (a4 = this.e)) {
            return a4 != u.d$a.h || d3.h().b0() && this.h().b0();
        }
        switch (a4.ordinal()) {
            default: {
                throw new AssertionError((Object)this.e.name());
            }
            case 6: {
                return a5 != u.d$a.h && a5 != u.d$a.j && a5 != u.d$a.k;
            }
            case 5: {
                return a5 != u.d$a.d && a5 != u.d$a.f;
                {
                }
            }
            case 2: 
            case 4: {
                boolean bl = a5 == u.d$a.e || a5 == u.d$a.g;
                if (d3.h() instanceof h) {
                    return bl || a5 == u.d$a.k;
                    {
                    }
                }
                return bl;
            }
            case 1: 
            case 3: {
                boolean bl = a5 == u.d$a.d || a5 == u.d$a.f;
                if (d3.h() instanceof h) {
                    return bl || a5 == u.d$a.j;
                    {
                    }
                }
                return bl;
            }
            case 0: 
            case 7: 
            case 8: 
        }
        return false;
    }

    public void q() {
        Object object = this.f;
        if (object != null && (object = ((d)object).a) != null) {
            ((HashSet)object).remove(this);
            if (this.f.a.size() == 0) {
                this.f.a = null;
            }
        }
        this.a = null;
        this.f = null;
        this.g = 0;
        this.h = Integer.MIN_VALUE;
        this.c = false;
        this.b = 0;
    }

    public void r() {
        this.c = false;
        this.b = 0;
    }

    public void s(c object) {
        object = this.i;
        if (object == null) {
            this.i = new i(i.a.c, null);
            return;
        }
        ((i)object).e();
    }

    public void t(int n3) {
        this.b = n3;
        this.c = true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.d.v());
        stringBuilder.append(":");
        stringBuilder.append(((Object)((Object)this.e)).toString());
        return stringBuilder.toString();
    }

    public void u(int n3) {
        if (this.o()) {
            this.h = n3;
        }
    }

    public static final class a
    extends Enum {
        public static final /* enum */ a c = new a("NONE", 0);
        public static final /* enum */ a d = new a("LEFT", 1);
        public static final /* enum */ a e = new a("TOP", 2);
        public static final /* enum */ a f = new a("RIGHT", 3);
        public static final /* enum */ a g = new a("BOTTOM", 4);
        public static final /* enum */ a h = new a("BASELINE", 5);
        public static final /* enum */ a i = new a("CENTER", 6);
        public static final /* enum */ a j = new a("CENTER_X", 7);
        public static final /* enum */ a k = new a("CENTER_Y", 8);
        public static final a[] l = u.d$a.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ a[] a() {
            return new a[]{c, d, e, f, g, h, i, j, k};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])l.clone();
        }
    }
}

