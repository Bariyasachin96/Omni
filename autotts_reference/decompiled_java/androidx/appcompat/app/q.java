/*
 * Decompiled with CFR 0.152.
 */
package androidx.appcompat.app;

public class q {
    public static q d;
    public long a;
    public long b;
    public int c;

    public static q b() {
        if (d == null) {
            d = new q();
        }
        return d;
    }

    public void a(long l3, double d3, double d4) {
        long l4;
        float f3 = (float)(l3 - 946728000000L) / 8.64E7f;
        float f4 = 0.01720197f * f3 + 6.24006f;
        double d5 = f4;
        double d6 = Math.sin(d5) * (double)0.0334196f + d5 + Math.sin(2.0f * f4) * 3.4906598739326E-4 + Math.sin(f4 * 3.0f) * (double)5.236E-6f + 1.796593063 + Math.PI;
        d4 = -d4 / 360.0;
        d4 = (double)((float)Math.round((double)(f3 - 9.0E-4f) - d4) + 9.0E-4f) + d4 + Math.sin(d5) * 0.0053 + Math.sin(2.0 * d6) * -0.0069;
        d6 = Math.asin(Math.sin(d6) * Math.sin(0.4092797040939331));
        d3 = 0.01745329238474369 * d3;
        d3 = (Math.sin(-0.10471975803375244) - Math.sin(d3) * Math.sin(d6)) / (Math.cos(d3) * Math.cos(d6));
        if (d3 >= 1.0) {
            this.c = 1;
            this.a = -1L;
            this.b = -1L;
            return;
        }
        if (d3 <= -1.0) {
            this.c = 0;
            this.a = -1L;
            this.b = -1L;
            return;
        }
        d3 = (float)(Math.acos(d3) / (Math.PI * 2));
        this.a = Math.round((d4 + d3) * 8.64E7) + 946728000000L;
        this.b = l4 = Math.round((d4 - d3) * 8.64E7) + 946728000000L;
        if (l4 < l3 && this.a > l3) {
            this.c = 0;
            return;
        }
        this.c = 1;
    }
}

