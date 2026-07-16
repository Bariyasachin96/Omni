/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.Gravity
 */
package o0;

import android.graphics.Rect;
import android.view.Gravity;

public abstract class s {
    public static void a(int n3, int n4, int n5, Rect rect, Rect rect2, int n6) {
        Gravity.apply((int)n3, (int)n4, (int)n5, (Rect)rect, (Rect)rect2, (int)n6);
    }

    public static int b(int n3, int n4) {
        return Gravity.getAbsoluteGravity((int)n3, (int)n4);
    }
}

