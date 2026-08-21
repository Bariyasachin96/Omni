/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 */
package d2;

import android.graphics.Canvas;

public abstract class a {
    public static int a(Canvas canvas, float f3, float f4, float f5, float f6, int n3) {
        return canvas.saveLayerAlpha(f3, f4, f5, f6, n3);
    }

    public static interface a {
        public void a(Canvas var1);
    }
}

