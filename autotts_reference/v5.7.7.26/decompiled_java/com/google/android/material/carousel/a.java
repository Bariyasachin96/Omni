/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 */
package com.google.android.material.carousel;

import android.content.Context;
import android.view.View;
import com.google.android.material.carousel.b;
import com.google.android.material.carousel.c;

public abstract class a {
    public float a;
    public float b;

    public static int[] a(int[] nArray) {
        int n3 = nArray.length;
        int[] nArray2 = new int[n3];
        for (int i3 = 0; i3 < n3; ++i3) {
            nArray2[i3] = nArray[i3] * 2;
        }
        return nArray2;
    }

    public static float b(float f3, float f4, float f5) {
        return 1.0f - (f3 - f5) / (f4 - f5);
    }

    public float c() {
        return this.b;
    }

    public float d() {
        return this.a;
    }

    public a e() {
        return com.google.android.material.carousel.a$a.c;
    }

    public void f(Context context) {
        float f3 = this.a;
        if (!(f3 > 0.0f)) {
            f3 = com.google.android.material.carousel.b.h(context);
        }
        this.a = f3;
        f3 = this.b;
        if (!(f3 > 0.0f)) {
            f3 = com.google.android.material.carousel.b.g(context);
        }
        this.b = f3;
    }

    public abstract c g(f2.b var1, View var2);

    public abstract boolean h(f2.b var1, int var2);

    public static final class a
    extends Enum {
        public static final /* enum */ a c = new a("CONTAINED", 0);
        public static final /* enum */ a d = new a("UNCONTAINED", 1);
        public static final a[] e = com.google.android.material.carousel.a$a.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public a() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ a[] a() {
            return new a[]{c, d};
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        public static a[] values() {
            return (a[])e.clone();
        }
    }
}

