/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.io.PrintStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import r.d;
import u.b;
import u.e;
import u.f;

public class o {
    public static int g;
    public ArrayList a = new ArrayList();
    public int b;
    public boolean c = false;
    public int d;
    public ArrayList e = null;
    public int f = -1;

    public o(int n3) {
        int n4 = g;
        g = n4 + 1;
        this.b = n4;
        this.d = n3;
    }

    public boolean a(e e3) {
        if (this.a.contains(e3)) {
            return false;
        }
        this.a.add(e3);
        return true;
    }

    public void b(ArrayList arrayList) {
        int n3 = this.a.size();
        if (this.f != -1 && n3 > 0) {
            for (int i3 = 0; i3 < arrayList.size(); ++i3) {
                o o3 = (o)arrayList.get(i3);
                if (this.f != o3.b) continue;
                this.g(this.d, o3);
            }
        }
        if (n3 == 0) {
            arrayList.remove(this);
        }
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.d;
    }

    public final String e() {
        int n3 = this.d;
        if (n3 == 0) {
            return "Horizontal";
        }
        if (n3 == 1) {
            return "Vertical";
        }
        if (n3 == 2) {
            return "Both";
        }
        return "Unknown";
    }

    public int f(d d3, int n3) {
        if (this.a.size() == 0) {
            return 0;
        }
        return this.j(d3, this.a, n3);
    }

    public void g(int n3, o o3) {
        ArrayList arrayList = this.a;
        int n4 = arrayList.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            Object object = arrayList.get(i3);
            object = (e)object;
            o3.a((e)object);
            if (n3 == 0) {
                ((e)object).S0 = o3.c();
                continue;
            }
            ((e)object).T0 = o3.c();
        }
        this.f = o3.b;
    }

    public void h(boolean bl) {
        this.c = bl;
    }

    public void i(int n3) {
        this.d = n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final int j(d d3, ArrayList arrayList, int n3) {
        Object object;
        int n4;
        int n5 = 0;
        f f3 = (f)((e)arrayList.get(0)).M();
        d3.E();
        f3.g(d3, false);
        for (n4 = 0; n4 < arrayList.size(); ++n4) {
            ((e)arrayList.get(n4)).g(d3, false);
        }
        if (n3 == 0 && f3.g1 > 0) {
            u.b.b(f3, d3, arrayList, 0);
        }
        if (n3 == 1 && f3.h1 > 0) {
            u.b.b(f3, d3, arrayList, 1);
        }
        try {
            d3.A();
        }
        catch (Exception exception) {
            PrintStream printStream = System.err;
            object = new StringBuilder();
            ((StringBuilder)object).append(((Object)exception).toString());
            ((StringBuilder)object).append("\n");
            ((StringBuilder)object).append(Arrays.toString(exception.getStackTrace()).replace("[", "   at ").replace(",", "\n   at").replace("]", ""));
            printStream.println(((StringBuilder)object).toString());
        }
        this.e = new ArrayList();
        for (n4 = n5; n4 < arrayList.size(); ++n4) {
            object = new a((e)arrayList.get(n4), d3, n3);
            this.e.add(object);
        }
        if (n3 == 0) {
            n4 = d3.y(f3.Q);
            n3 = d3.y(f3.S);
            d3.E();
            return n3 - n4;
        }
        n4 = d3.y(f3.R);
        n3 = d3.y(f3.T);
        d3.E();
        return n3 - n4;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.e());
        charSequence.append(" [");
        charSequence.append(this.b);
        charSequence.append("] <");
        charSequence = charSequence.toString();
        Serializable serializable = this.a;
        int n3 = ((ArrayList)serializable).size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = ((ArrayList)serializable).get(i3);
            object = (e)object;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" ");
            stringBuilder.append(((e)object).v());
            charSequence = stringBuilder.toString();
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append((String)charSequence);
        ((StringBuilder)serializable).append(" >");
        return ((StringBuilder)serializable).toString();
    }

    public static class a {
        public WeakReference a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;

        public a(e e3, d d3, int n3) {
            this.a = new WeakReference<e>(e3);
            this.b = d3.y(e3.Q);
            this.c = d3.y(e3.R);
            this.d = d3.y(e3.S);
            this.e = d3.y(e3.T);
            this.f = d3.y(e3.U);
            this.g = n3;
        }
    }
}

