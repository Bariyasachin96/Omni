/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.io.PrintStream;
import java.text.DecimalFormat;
import s.b;

public abstract class o {
    public static float k = (float)Math.PI * 2;
    public b a;
    public int b = 0;
    public int[] c = new int[10];
    public float[][] d = new float[10][3];
    public int e;
    public String f;
    public float[] g = new float[3];
    public boolean h = false;
    public long i;
    public float j = Float.NaN;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public float a(float f3) {
        switch (this.b) {
            default: {
                return (float)Math.sin(f3 * k);
            }
            case 6: {
                f3 = 1.0f - Math.abs(f3 * 4.0f % 4.0f - 2.0f);
                f3 *= f3;
                return 1.0f - f3;
            }
            case 5: {
                return (float)Math.cos(f3 * k);
            }
            case 4: {
                f3 = (f3 * 2.0f + 1.0f) % 2.0f;
                return 1.0f - f3;
            }
            case 3: {
                return (f3 * 2.0f + 1.0f) % 2.0f - 1.0f;
            }
            case 2: {
                f3 = Math.abs(f3);
                return 1.0f - f3;
            }
            case 1: 
        }
        return Math.signum(f3 * k);
    }

    public void b(int n3, float f3, float f4, int n4, float f5) {
        Object[] objectArray = this.c;
        int n5 = this.e++;
        objectArray[n5] = n3;
        objectArray = this.d[n5];
        objectArray[0] = (int)f3;
        objectArray[1] = (int)f4;
        objectArray[2] = (int)f5;
        this.b = Math.max(this.b, n4);
    }

    public void c(long l3) {
        this.i = l3;
    }

    public void d(String string) {
        this.f = string;
    }

    public void e(int n3) {
        int n4;
        Object[] objectArray;
        int n5 = this.e;
        if (n5 == 0) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error no points added to ");
            stringBuilder.append(this.f);
            printStream.println(stringBuilder.toString());
            return;
        }
        s.o$a.a(this.c, this.d, 0, n5 - 1);
        n5 = 0;
        for (int i3 = 1; i3 < (objectArray = this.c).length; ++i3) {
            n4 = n5;
            if (objectArray[i3] != objectArray[i3 - 1]) {
                n4 = n5 + 1;
            }
            n5 = n4;
        }
        n4 = n5;
        if (n5 == 0) {
            n4 = 1;
        }
        objectArray = new double[n4];
        double[][] dArray = new double[n4][3];
        n4 = 0;
        for (n5 = 0; n5 < this.e; ++n5) {
            Object[] objectArray2;
            if (n5 > 0 && (objectArray2 = this.c)[n5] == objectArray2[n5 - 1]) continue;
            objectArray[n4] = (int)((double)this.c[n5] * 0.01);
            objectArray2 = dArray[n4];
            float[] fArray = this.d[n5];
            objectArray2[0] = (int)fArray[0];
            objectArray2[1] = (int)fArray[1];
            objectArray2[2] = (int)fArray[2];
            ++n4;
        }
        this.a = s.b.a(n3, objectArray, dArray);
    }

    public String toString() {
        String string = this.f;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i3 = 0; i3 < this.e; ++i3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("[");
            stringBuilder.append(this.c[i3]);
            stringBuilder.append(" , ");
            stringBuilder.append(decimalFormat.format(this.d[i3]));
            stringBuilder.append("] ");
            string = stringBuilder.toString();
        }
        return string;
    }

    public static abstract class a {
        public static void a(int[] nArray, float[][] fArray, int n3, int n4) {
            int[] nArray2 = new int[nArray.length + 10];
            nArray2[0] = n4;
            nArray2[1] = n3;
            n3 = 2;
            while (n3 > 0) {
                int n5 = nArray2[n3 - 1];
                n4 = n3 - 2;
                int n6 = nArray2[n4];
                if (n5 < n6) {
                    int n7 = s.o$a.b(nArray, fArray, n5, n6);
                    nArray2[n4] = n7 - 1;
                    nArray2[n3 - 1] = n5;
                    nArray2[n3] = n6;
                    n4 = n3 + 2;
                    nArray2[n3 + 1] = n7 + 1;
                    n3 = n4;
                    continue;
                }
                n3 = n4;
            }
        }

        public static int b(int[] nArray, float[][] fArray, int n3, int n4) {
            int n5 = nArray[n4];
            int n6 = n3;
            while (n3 < n4) {
                int n7 = n6;
                if (nArray[n3] <= n5) {
                    s.o$a.c(nArray, fArray, n6, n3);
                    n7 = n6 + 1;
                }
                ++n3;
                n6 = n7;
            }
            s.o$a.c(nArray, fArray, n6, n4);
            return n6;
        }

        public static void c(int[] objectArray, float[][] fArray, int n3, int n4) {
            int n5 = objectArray[n3];
            objectArray[n3] = objectArray[n4];
            objectArray[n4] = n5;
            objectArray = fArray[n3];
            fArray[n3] = fArray[n4];
            fArray[n4] = objectArray;
        }
    }
}

