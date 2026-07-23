/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package m1;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import m1.o0;
import m1.y;

public class q
extends o0 {
    public float b = 3.0f;
    public int c = 80;

    @Override
    public long c(ViewGroup viewGroup, Transition transition, y object, y y3) {
        long l3;
        Object object2;
        Object object3;
        int n3;
        if (object == null && y3 == null) {
            return 0L;
        }
        Rect rect = transition.w();
        if (y3 != null && this.e((y)object) != 0) {
            n3 = 1;
            object = y3;
        } else {
            n3 = -1;
        }
        int n4 = this.f((y)object);
        int n5 = this.g((y)object);
        object = new int[2];
        viewGroup.getLocationOnScreen((int[])object);
        reference var14_9 = object[0] + Math.round(viewGroup.getTranslationX());
        reference var12_10 = object[1] + Math.round(viewGroup.getTranslationY());
        int n6 = viewGroup.getWidth() + var14_9;
        int n7 = viewGroup.getHeight() + var12_10;
        if (rect != null) {
            object3 = rect.centerX();
            object2 = rect.centerY();
        } else {
            object3 = (var14_9 + n6) / 2;
            object2 = (var12_10 + n7) / 2;
        }
        float f3 = (float)this.h((View)viewGroup, n4, n5, (int)object3, (int)object2, (int)var14_9, (int)var12_10, n6, n7) / (float)this.i(viewGroup);
        long l4 = l3 = transition.v();
        if (l3 < 0L) {
            l4 = 300L;
        }
        return Math.round((float)(l4 * (long)n3) / this.b * f3);
    }

    /*
     * Unable to fully structure code
     */
    public final int h(View var1_1, int var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, int var9_9) {
        block9: {
            var11_10 = this.c;
            if (var11_10 == 0x800003) {
                if (var1_1.getLayoutDirection() == 1) {
                    while (true) {
                        var10_11 = 5;
                        break block9;
                        break;
                    }
                }
lbl7:
                // 3 sources

                while (true) {
                    var10_11 = 3;
                    break block9;
                    break;
                }
            }
            var10_11 = var11_10;
            if (var11_10 == 0x800005) {
                if (var1_1.getLayoutDirection() != 1) ** continue;
                ** continue;
            }
        }
        if (var10_11 != 3) {
            if (var10_11 != 5) {
                if (var10_11 != 48) {
                    if (var10_11 != 80) {
                        return 0;
                    }
                    return var3_3 - var7_7 + Math.abs(var4_4 - var2_2);
                }
                return var9_9 - var3_3 + Math.abs(var4_4 - var2_2);
            }
            return var2_2 - var6_6 + Math.abs(var5_5 - var3_3);
        }
        return var8_8 - var2_2 + Math.abs(var5_5 - var3_3);
    }

    public final int i(ViewGroup viewGroup) {
        int n3 = this.c;
        if (n3 != 3 && n3 != 5 && n3 != 0x800003 && n3 != 0x800005) {
            return viewGroup.getHeight();
        }
        return viewGroup.getWidth();
    }

    public void j(int n3) {
        this.c = n3;
    }
}

