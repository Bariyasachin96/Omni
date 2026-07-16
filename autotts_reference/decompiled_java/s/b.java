/*
 * Decompiled with CFR 0.152.
 */
package s;

import s.f;
import s.g;

public abstract class b {
    public static b a(int n3, double[] dArray, double[][] dArray2) {
        if (dArray.length == 1) {
            n3 = 2;
        }
        if (n3 != 0) {
            if (n3 != 2) {
                return new f(dArray, dArray2);
            }
            return new a(dArray[0], dArray2[0]);
        }
        return new g(dArray, dArray2);
    }

    public static b b(int[] nArray, double[] dArray, double[][] dArray2) {
        return new s.a(nArray, dArray, dArray2);
    }

    public abstract double c(double var1, int var3);

    public abstract void d(double var1, double[] var3);

    public abstract void e(double var1, float[] var3);

    public abstract double f(double var1, int var3);

    public abstract void g(double var1, double[] var3);

    public abstract double[] h();

    public static class a
    extends b {
        public double a;
        public double[] b;

        public a(double d3, double[] dArray) {
            this.a = d3;
            this.b = dArray;
        }

        @Override
        public double c(double d3, int n3) {
            return this.b[n3];
        }

        @Override
        public void d(double d3, double[] dArray) {
            double[] dArray2 = this.b;
            System.arraycopy(dArray2, 0, dArray, 0, dArray2.length);
        }

        @Override
        public void e(double d3, float[] fArray) {
            double[] dArray;
            for (int i3 = 0; i3 < (dArray = this.b).length; ++i3) {
                fArray[i3] = (float)dArray[i3];
            }
        }

        @Override
        public double f(double d3, int n3) {
            return 0.0;
        }

        @Override
        public void g(double d3, double[] dArray) {
            for (int i3 = 0; i3 < this.b.length; ++i3) {
                dArray[i3] = 0.0;
            }
        }

        @Override
        public double[] h() {
            return new double[]{this.a};
        }
    }
}

