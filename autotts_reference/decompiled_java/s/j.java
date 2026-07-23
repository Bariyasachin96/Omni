/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.text.DecimalFormat;
import java.util.Arrays;
import s.b;

public abstract class j {
    public b a;
    public int[] b = new int[10];
    public float[] c = new float[10];
    public int d;
    public String e;

    public float a(float f3) {
        return (float)this.a.c(f3, 0);
    }

    public float b(float f3) {
        return (float)this.a.f(f3, 0);
    }

    public void c(int n3, float f3) {
        Object[] objectArray = this.b;
        if (objectArray.length < this.d + 1) {
            this.b = Arrays.copyOf(objectArray, objectArray.length * 2);
            objectArray = this.c;
            this.c = Arrays.copyOf((float[])objectArray, objectArray.length * 2);
        }
        objectArray = this.b;
        int n4 = this.d;
        objectArray[n4] = n3;
        this.c[n4] = f3;
        this.d = n4 + 1;
    }

    public void d(String string) {
        this.e = string;
    }

    public void e(int n3) {
        Object[] objectArray;
        int n4 = this.d;
        if (n4 == 0) {
            return;
        }
        s.j$a.a(this.b, this.c, 0, n4 - 1);
        int n5 = 1;
        for (int i3 = 1; i3 < this.d; ++i3) {
            objectArray = this.b;
            n4 = n5;
            if (objectArray[i3 - 1] != objectArray[i3]) {
                n4 = n5 + 1;
            }
            n5 = n4;
        }
        objectArray = new double[n5];
        double[][] dArray = new double[n5][1];
        n5 = 0;
        for (n4 = 0; n4 < this.d; ++n4) {
            int[] nArray;
            if (n4 > 0 && (nArray = this.b)[n4] == nArray[n4 - 1]) continue;
            objectArray[n5] = (int)((double)this.b[n4] * 0.01);
            dArray[n5][0] = this.c[n4];
            ++n5;
        }
        this.a = s.b.a(n3, objectArray, dArray);
    }

    public String toString() {
        String string = this.e;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i3 = 0; i3 < this.d; ++i3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("[");
            stringBuilder.append(this.b[i3]);
            stringBuilder.append(" , ");
            stringBuilder.append(decimalFormat.format(this.c[i3]));
            stringBuilder.append("] ");
            string = stringBuilder.toString();
        }
        return string;
    }

    public static abstract class a {
        public static void a(int[] nArray, float[] fArray, int n3, int n4) {
            int[] nArray2 = new int[nArray.length + 10];
            nArray2[0] = n4;
            nArray2[1] = n3;
            n3 = 2;
            while (n3 > 0) {
                int n5 = nArray2[n3 - 1];
                n4 = n3 - 2;
                int n6 = nArray2[n4];
                if (n5 < n6) {
                    int n7 = s.j$a.b(nArray, fArray, n5, n6);
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

        public static int b(int[] nArray, float[] fArray, int n3, int n4) {
            int n5 = nArray[n4];
            int n6 = n3;
            while (n3 < n4) {
                int n7 = n6;
                if (nArray[n3] <= n5) {
                    s.j$a.c(nArray, fArray, n6, n3);
                    n7 = n6 + 1;
                }
                ++n3;
                n6 = n7;
            }
            s.j$a.c(nArray, fArray, n6, n4);
            return n6;
        }

        public static void c(int[] nArray, float[] fArray, int n3, int n4) {
            int n5 = nArray[n3];
            nArray[n3] = nArray[n4];
            nArray[n4] = n5;
            float f3 = fArray[n3];
            fArray[n3] = fArray[n4];
            fArray[n4] = f3;
        }
    }
}

