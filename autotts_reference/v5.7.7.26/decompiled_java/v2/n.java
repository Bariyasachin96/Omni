/*
 * Decompiled with CFR 0.152.
 */
package v2;

import v2.e;
import v2.q;

public class n
extends e {
    public float a = -1.0f;

    @Override
    public void a(q q3, float f3, float f4, float f5) {
        f4 = f5 * f4;
        q3.o(0.0f, f4, 180.0f, 180.0f - f3);
        q3.a(0.0f, 0.0f, f4 *= 2.0f, f4, 180.0f, f3);
    }
}

