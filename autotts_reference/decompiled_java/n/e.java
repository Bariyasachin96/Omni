/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 */
package n;

import android.graphics.drawable.Drawable;

public abstract class e
extends Drawable {
    public static final double a = Math.cos(Math.toRadians(45.0));

    public static float a(float f3, float f4, boolean bl) {
        float f5 = f3;
        if (bl) {
            f5 = (float)((double)f3 + (1.0 - a) * (double)f4);
        }
        return f5;
    }

    public static float b(float f3, float f4, boolean bl) {
        if (bl) {
            return (float)((double)(f3 * 1.5f) + (1.0 - a) * (double)f4);
        }
        return f3 * 1.5f;
    }
}

