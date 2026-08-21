/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 */
package o0;

import android.view.MotionEvent;

public class v0 {
    public final float[] a = new float[20];
    public final long[] b = new long[20];
    public float c = 0.0f;
    public int d = 0;
    public int e = 0;

    public static float f(float f3) {
        float f4 = f3 < 0.0f ? -1.0f : 1.0f;
        return f4 * (float)Math.sqrt(Math.abs(f3) * 2.0f);
    }

    public void a(MotionEvent motionEvent) {
        int n3;
        long l3 = motionEvent.getEventTime();
        if (this.d != 0 && l3 - this.b[this.e] > 40L) {
            this.b();
        }
        this.e = n3 = (this.e + 1) % 20;
        int n4 = this.d;
        if (n4 != 20) {
            this.d = n4 + 1;
        }
        this.a[n3] = motionEvent.getAxisValue(26);
        this.b[this.e] = l3;
    }

    public final void b() {
        this.d = 0;
        this.c = 0.0f;
    }

    public void c(int n3, float f3) {
        float f4;
        this.c = f4 = this.e() * (float)n3;
        if (f4 < -Math.abs(f3)) {
            this.c = -Math.abs(f3);
            return;
        }
        if (this.c > Math.abs(f3)) {
            this.c = Math.abs(f3);
        }
    }

    public float d(int n3) {
        if (n3 != 26) {
            return 0.0f;
        }
        return this.c;
    }

    public final float e() {
        long[] lArray;
        long l3;
        int n3 = this.d;
        if (n3 < 2) {
            return 0.0f;
        }
        int n4 = this.e;
        n3 = (n4 + 20 - (n3 - 1)) % 20;
        long l4 = this.b[n4];
        while (l4 - (l3 = (lArray = this.b)[n3]) > 100L) {
            --this.d;
            n3 = (n3 + 1) % 20;
        }
        n4 = this.d;
        if (n4 < 2) {
            return 0.0f;
        }
        if (n4 == 2) {
            l4 = lArray[n3 = (n3 + 1) % 20];
            if (l3 == l4) {
                return 0.0f;
            }
            return this.a[n3] / (float)(l4 - l3);
        }
        float f3 = 0.0f;
        int n5 = 0;
        for (n4 = 0; n4 < this.d - 1; ++n4) {
            lArray = this.b;
            int n6 = n4 + n3;
            int n7 = (n6 + 1) % 20;
            l3 = lArray[n6 % 20];
            if (lArray[n7] == l3) continue;
            n6 = n5 + 1;
            float f4 = v0.f(f3);
            float f5 = this.a[n7] / (float)(this.b[n7] - l3);
            f4 = f3 + (f5 - f4) * Math.abs(f5);
            n5 = n6;
            f3 = f4;
            if (n6 != 1) continue;
            f3 = f4 * 0.5f;
            n5 = n6;
        }
        return v0.f(f3);
    }
}

