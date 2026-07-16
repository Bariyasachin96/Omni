/*
 * Decompiled with CFR 0.152.
 */
package s;

import s.c;

public class i
extends c {
    public double d;
    public double e;

    public i(String string) {
        this.a = string;
        int n3 = string.indexOf(40);
        int n4 = string.indexOf(44, n3);
        this.d = Double.parseDouble(string.substring(n3 + 1, n4).trim());
        n3 = n4 + 1;
        this.e = Double.parseDouble(string.substring(n3, string.indexOf(44, n3)).trim());
    }

    @Override
    public double a(double d3) {
        return this.e(d3);
    }

    @Override
    public double b(double d3) {
        return this.d(d3);
    }

    public final double d(double d3) {
        double d4 = this.e;
        if (d3 < d4) {
            double d5 = this.d;
            return d5 * d4 * d4 / (((d4 - d3) * d5 + d3) * (d5 * (d4 - d3) + d3));
        }
        double d6 = this.d;
        return (d4 - 1.0) * d6 * (d4 - 1.0) / ((-d6 * (d4 - d3) - d3 + 1.0) * (-d6 * (d4 - d3) - d3 + 1.0));
    }

    public final double e(double d3) {
        double d4 = this.e;
        if (d3 < d4) {
            return d4 * d3 / (d3 + this.d * (d4 - d3));
        }
        return (1.0 - d4) * (d3 - 1.0) / (1.0 - d3 - this.d * (d4 - d3));
    }
}

