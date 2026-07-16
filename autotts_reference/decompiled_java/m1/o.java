/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 *  android.graphics.Rect
 */
package m1;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

public class o
implements TypeEvaluator {
    public Rect a;

    public o() {
    }

    public o(Rect rect) {
        this.a = rect;
    }

    public Rect a(float f3, Rect rect, Rect rect2) {
        int n3 = rect.left;
        n3 += (int)((float)(rect2.left - n3) * f3);
        int n4 = rect.top;
        n4 += (int)((float)(rect2.top - n4) * f3);
        int n5 = rect.right;
        n5 += (int)((float)(rect2.right - n5) * f3);
        int n6 = rect.bottom;
        n6 += (int)((float)(rect2.bottom - n6) * f3);
        rect = this.a;
        if (rect == null) {
            return new Rect(n3, n4, n5, n6);
        }
        rect.set(n3, n4, n5, n6);
        return this.a;
    }
}

