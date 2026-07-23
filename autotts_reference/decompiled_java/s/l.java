/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.io.PrintStream;
import java.util.Arrays;
import s.c;
import s.g;

public class l
extends c {
    public g d;

    public l(String string) {
        this.a = string;
        double[] dArray = new double[string.length() / 2];
        int n3 = string.indexOf(40) + 1;
        int n4 = string.indexOf(44, n3);
        int n5 = 0;
        while (n4 != -1) {
            dArray[n5] = Double.parseDouble(string.substring(n3, n4).trim());
            n3 = n4 + 1;
            n4 = string.indexOf(44, n3);
            ++n5;
        }
        dArray[n5] = Double.parseDouble(string.substring(n3, string.indexOf(41, n3)).trim());
        this.d = l.d(Arrays.copyOf(dArray, n5 + 1));
    }

    public static g d(double[] object) {
        int n3 = ((double[])object).length * 3 - 2;
        int n4 = ((double[])object).length - 1;
        double d3 = 1.0 / (double)n4;
        Object object2 = new double[n3][1];
        Object object3 = new double[n3];
        for (n3 = 0; n3 < ((double[])object).length; ++n3) {
            double d4 = object[n3];
            int n5 = n3 + n4;
            object2[n5][0] = d4;
            double d5 = (double)n3 * d3;
            object3[n5] = d5;
            if (n3 <= 0) continue;
            n5 = n4 * 2 + n3;
            object2[n5][0] = d4 + 1.0;
            object3[n5] = d5 + 1.0;
            n5 = n3 - 1;
            object2[n5][0] = d4 - 1.0 - d3;
            object3[n5] = d5 - 1.0 - d3;
        }
        object = new g((double[])object3, (double[][])object2);
        object2 = System.out;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append(" 0 ");
        ((StringBuilder)object3).append(((g)object).c(0.0, 0));
        ((PrintStream)object2).println(((StringBuilder)object3).toString());
        object2 = System.out;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append(" 1 ");
        ((StringBuilder)object3).append(((g)object).c(1.0, 0));
        ((PrintStream)object2).println(((StringBuilder)object3).toString());
        return object;
    }

    @Override
    public double a(double d3) {
        return this.d.c(d3, 0);
    }

    @Override
    public double b(double d3) {
        return this.d.f(d3, 0);
    }
}

