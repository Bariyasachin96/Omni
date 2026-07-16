/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.io.PrintStream;
import java.util.Arrays;
import s.i;
import s.l;

public class c {
    public static c b = new c();
    public static String[] c = new String[]{"standard", "accelerate", "decelerate", "linear"};
    public String a = "identity";

    public static c c(String charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (((String)charSequence).startsWith("cubic")) {
            return new a((String)charSequence);
        }
        if (((String)charSequence).startsWith("spline")) {
            return new l((String)charSequence);
        }
        if (((String)charSequence).startsWith("Schlick")) {
            return new i((String)charSequence);
        }
        int n3 = ((String)charSequence).hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 1312628413: {
                if (!((String)charSequence).equals("standard")) break;
                n4 = 5;
                break;
            }
            case -749065269: {
                if (!((String)charSequence).equals("overshoot")) break;
                n4 = 4;
                break;
            }
            case -1102672091: {
                if (!((String)charSequence).equals("linear")) break;
                n4 = 3;
                break;
            }
            case -1197605014: {
                if (!((String)charSequence).equals("anticipate")) break;
                n4 = 2;
                break;
            }
            case -1263948740: {
                if (!((String)charSequence).equals("decelerate")) break;
                n4 = 1;
                break;
            }
            case -1354466595: {
                if (!((String)charSequence).equals("accelerate")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                PrintStream printStream = System.err;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or ");
                ((StringBuilder)charSequence).append(Arrays.toString(c));
                printStream.println(((StringBuilder)charSequence).toString());
                return b;
            }
            case 5: {
                return new a("cubic(0.4, 0.0, 0.2, 1)");
            }
            case 4: {
                return new a("cubic(0.34, 1.56, 0.64, 1)");
            }
            case 3: {
                return new a("cubic(1, 1, 0, 0)");
            }
            case 2: {
                return new a("cubic(0.36, 0, 0.66, -0.56)");
            }
            case 1: {
                return new a("cubic(0.0, 0.0, 0.2, 0.95)");
            }
            case 0: 
        }
        return new a("cubic(0.4, 0.05, 0.8, 0.7)");
    }

    public double a(double d3) {
        return d3;
    }

    public double b(double d3) {
        return 1.0;
    }

    public String toString() {
        return this.a;
    }

    public static class a
    extends c {
        public static double h = 0.01;
        public static double i = 1.0E-4;
        public double d;
        public double e;
        public double f;
        public double g;

        public a(String string) {
            this.a = string;
            int n3 = string.indexOf(40);
            int n4 = string.indexOf(44, n3);
            this.d = Double.parseDouble(string.substring(n3 + 1, n4).trim());
            n3 = string.indexOf(44, ++n4);
            this.e = Double.parseDouble(string.substring(n4, n3).trim());
            n4 = string.indexOf(44, ++n3);
            this.f = Double.parseDouble(string.substring(n3, n4).trim());
            n3 = n4 + 1;
            this.g = Double.parseDouble(string.substring(n3, string.indexOf(41, n3)).trim());
        }

        @Override
        public double a(double d3) {
            double d4;
            double d5;
            if (d3 <= 0.0) {
                return 0.0;
            }
            if (d3 >= 1.0) {
                return 1.0;
            }
            double d6 = 0.5;
            for (d5 = 0.5; d5 > h; d5 *= 0.5) {
                d4 = this.d(d6);
                if (d4 < d3) {
                    d6 += d5;
                    continue;
                }
                d6 -= d5;
            }
            double d7 = d6 - d5;
            d4 = this.d(d7);
            d6 += d5;
            d5 = this.d(d6);
            d7 = this.e(d7);
            return (this.e(d6) - d7) * (d3 - d4) / (d5 - d4) + d7;
        }

        @Override
        public double b(double d3) {
            double d4;
            double d5;
            double d6 = 0.5;
            for (d5 = 0.5; d5 > i; d5 *= 0.5) {
                d4 = this.d(d6);
                if (d4 < d3) {
                    d6 += d5;
                    continue;
                }
                d6 -= d5;
            }
            d4 = d6 - d5;
            d3 = this.d(d4);
            d5 = d6 + d5;
            d6 = this.d(d5);
            d4 = this.e(d4);
            return (this.e(d5) - d4) / (d6 - d3);
        }

        public final double d(double d3) {
            double d4 = 1.0 - d3;
            double d5 = 3.0 * d4;
            return this.d * (d4 * d5 * d3) + this.f * (d5 * d3 * d3) + d3 * d3 * d3;
        }

        public final double e(double d3) {
            double d4 = 1.0 - d3;
            double d5 = 3.0 * d4;
            return this.e * (d4 * d5 * d3) + this.g * (d5 * d3 * d3) + d3 * d3 * d3;
        }
    }
}

