/*
 * Decompiled with CFR 0.152.
 */
package c1;

import o3.g;

public final class c {
    public static final a c = new a(null);
    public static final c d = new c(0.0f, 0.0f, 3, null);
    public final float a;
    public final float b;

    public c(float f3, float f4) {
        this.a = f3;
        this.b = f4;
    }

    public /* synthetic */ c(float f3, float f4, int n3, g g3) {
        if ((n3 & 1) != 0) {
            f3 = 0.0f;
        }
        if ((n3 & 2) != 0) {
            f4 = 0.0f;
        }
        this(f3, f4);
    }

    public final float a() {
        return this.a;
    }

    public final float b() {
        return this.b;
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }
}

