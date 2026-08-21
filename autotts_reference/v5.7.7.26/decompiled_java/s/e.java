/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import s.h;

public abstract class e {
    public s.b a;
    public b b;
    public String c;
    public int d = 0;
    public String e = null;
    public int f = 0;
    public ArrayList g = new ArrayList();

    public float a(float f3) {
        return (float)this.b.b(f3);
    }

    public float b(float f3) {
        return (float)this.b.a(f3);
    }

    public void c(Object object) {
    }

    public void d(int n3, int n4, String string, int n5, float f3, float f4, float f5, float f6) {
        this.g.add(new c(n3, f3, f4, f5, f6));
        if (n5 != -1) {
            this.f = n5;
        }
        this.d = n4;
        this.e = string;
    }

    public void e(int n3, int n4, String string, int n5, float f3, float f4, float f5, float f6, Object object) {
        this.g.add(new c(n3, f3, f4, f5, f6));
        if (n5 != -1) {
            this.f = n5;
        }
        this.d = n4;
        this.c(object);
        this.e = string;
    }

    public void f(String string) {
        this.c = string;
    }

    public void g(float f3) {
        int n3 = this.g.size();
        if (n3 == 0) {
            return;
        }
        Collections.sort(this.g, new Comparator(this){
            public final e c;
            {
                this.c = e3;
            }

            public int a(c c3, c c4) {
                return Integer.compare(c3.a, c4.a);
            }
        });
        double[] dArray = new double[n3];
        int n4 = 2;
        double[][] dArray2 = new double[n3][3];
        this.b = new b(this.d, this.e, this.f, n3);
        ArrayList arrayList = this.g;
        int n5 = arrayList.size();
        n3 = 0;
        for (int i3 = 0; i3 < n5; ++i3) {
            c c3 = (c)arrayList.get(i3);
            float f4 = c3.d;
            dArray[n3] = (double)f4 * 0.01;
            double[] dArray3 = dArray2[n3];
            float f5 = c3.b;
            dArray3[0] = f5;
            float f6 = c3.c;
            dArray3[1] = f6;
            float f7 = c3.e;
            dArray3[n4] = f7;
            this.b.c(n3, c3.a, f4, f6, f7, f5);
            ++n3;
        }
        this.b.d(f3);
        this.a = s.b.a(0, dArray, dArray2);
    }

    public boolean h() {
        return this.f == 1;
    }

    public String toString() {
        String string = this.c;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        ArrayList arrayList = this.g;
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = arrayList.get(i3);
            c c3 = (c)object;
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("[");
            ((StringBuilder)object).append(c3.a);
            ((StringBuilder)object).append(" , ");
            ((StringBuilder)object).append(decimalFormat.format(c3.b));
            ((StringBuilder)object).append("] ");
            string = ((StringBuilder)object).toString();
        }
        return string;
    }

    public static class b {
        public final int a;
        public h b;
        public final int c;
        public final int d;
        public final int e;
        public float[] f;
        public double[] g;
        public float[] h;
        public float[] i;
        public float[] j;
        public float[] k;
        public int l;
        public s.b m;
        public double[] n;
        public double[] o;
        public float p;

        public b(int n3, String string, int n4, int n5) {
            h h3;
            this.b = h3 = new h();
            this.c = 0;
            this.d = 1;
            this.e = 2;
            this.l = n3;
            this.a = n4;
            h3.g(n3, string);
            this.f = new float[n5];
            this.g = new double[n5];
            this.h = new float[n5];
            this.i = new float[n5];
            this.j = new float[n5];
            this.k = new float[n5];
        }

        public double a(float f3) {
            double d3;
            Object object = this.m;
            if (object != null) {
                d3 = f3;
                ((s.b)object).g(d3, this.o);
                this.m.d(d3, this.n);
            } else {
                object = this.o;
                object[0] = 0.0;
                object[1] = 0.0;
                object[2] = 0.0;
            }
            object = this.b;
            double d4 = f3;
            d3 = ((h)object).e(d4, this.n[1]);
            d4 = this.b.d(d4, this.n[1], this.o[1]);
            object = this.o;
            return (double)(object[0] + d3 * object[2] + d4 * this.n[2]);
        }

        public double b(float f3) {
            Object object = this.m;
            if (object != null) {
                ((s.b)object).d(f3, this.n);
            } else {
                object = this.n;
                object[0] = (double)this.i[0];
                object[1] = (double)this.j[0];
                object[2] = (double)this.f[0];
            }
            object = this.n;
            Object object2 = object[0];
            Object object3 = object[1];
            return (double)(object2 + this.b.e(f3, (double)object3) * this.n[2]);
        }

        public void c(int n3, int n4, float f3, float f4, float f5, float f6) {
            this.g[n3] = (double)n4 / 100.0;
            this.h[n3] = f3;
            this.i[n3] = f4;
            this.j[n3] = f5;
            this.f[n3] = f6;
        }

        public void d(float f3) {
            this.p = f3;
            int n3 = this.g.length;
            double[][] dArray = new double[n3][3];
            Object[] objectArray = this.f;
            this.n = new double[objectArray.length + 2];
            this.o = new double[objectArray.length + 2];
            if (this.g[0] > 0.0) {
                this.b.a(0.0, this.h[0]);
            }
            if ((objectArray = (Object[])this.g)[n3 = objectArray.length - 1] < 1.0) {
                this.b.a(1.0, this.h[n3]);
            }
            for (n3 = 0; n3 < dArray.length; ++n3) {
                objectArray = dArray[n3];
                objectArray[0] = (float)((double)this.i[n3]);
                objectArray[1] = (float)((double)this.j[n3]);
                objectArray[2] = (float)((double)this.f[n3]);
                this.b.a(this.g[n3], this.h[n3]);
            }
            this.b.f();
            objectArray = this.g;
            if (objectArray.length > 1) {
                this.m = s.b.a(0, objectArray, dArray);
                return;
            }
            this.m = null;
        }
    }

    public static class c {
        public int a;
        public float b;
        public float c;
        public float d;
        public float e;

        public c(int n3, float f3, float f4, float f5, float f6) {
            this.a = n3;
            this.b = f6;
            this.c = f4;
            this.d = f3;
            this.e = f5;
        }
    }
}

