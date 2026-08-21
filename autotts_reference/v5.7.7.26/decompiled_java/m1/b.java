/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package m1;

import android.view.ViewGroup;
import androidx.transition.Transition;
import m1.o0;
import m1.y;

public class b
extends o0 {
    public float b = 3.0f;

    public static float h(float f3, float f4, float f5, float f6) {
        f3 = f5 - f3;
        f4 = f6 - f4;
        return (float)Math.sqrt(f3 * f3 + f4 * f4);
    }

    @Override
    public long c(ViewGroup viewGroup, Transition transition, y object, y y3) {
        long l3;
        int n3;
        int n4;
        int n5;
        if (object == null && y3 == null) {
            return 0L;
        }
        if (y3 != null && this.e((y)object) != 0) {
            n5 = 1;
            object = y3;
        } else {
            n5 = -1;
        }
        int n6 = this.f((y)object);
        int n7 = this.g((y)object);
        object = transition.w();
        if (object != null) {
            n4 = object.centerX();
            n3 = object.centerY();
        } else {
            object = new int[2];
            viewGroup.getLocationOnScreen((int[])object);
            n4 = Math.round((float)(object[0] + viewGroup.getWidth() / 2) + viewGroup.getTranslationX());
            n3 = Math.round((float)(object[1] + viewGroup.getHeight() / 2) + viewGroup.getTranslationY());
        }
        float f3 = m1.b.h(n6, n7, n4, n3) / m1.b.h(0.0f, 0.0f, viewGroup.getWidth(), viewGroup.getHeight());
        long l4 = l3 = transition.v();
        if (l3 < 0L) {
            l4 = 300L;
        }
        return Math.round((float)(l4 * (long)n5) / this.b * f3);
    }
}

