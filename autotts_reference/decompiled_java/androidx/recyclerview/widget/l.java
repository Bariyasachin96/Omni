/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.i;

public abstract class l {
    public static int a(RecyclerView.z z3, i i3, View view, View view2, RecyclerView.p p3, boolean bl) {
        if (p3.O() != 0 && z3.b() != 0 && view != null && view2 != null) {
            if (!bl) {
                return Math.abs(p3.l0(view) - p3.l0(view2)) + 1;
            }
            int n3 = i3.d(view2);
            int n4 = i3.g(view);
            return Math.min(i3.n(), n3 - n4);
        }
        return 0;
    }

    public static int b(RecyclerView.z z3, i i3, View view, View view2, RecyclerView.p p3, boolean bl, boolean bl2) {
        if (p3.O() != 0 && z3.b() != 0 && view != null && view2 != null) {
            int n3 = Math.min(p3.l0(view), p3.l0(view2));
            int n4 = Math.max(p3.l0(view), p3.l0(view2));
            n4 = bl2 ? Math.max(0, z3.b() - n4 - 1) : Math.max(0, n3);
            if (!bl) {
                return n4;
            }
            n3 = Math.abs(i3.d(view2) - i3.g(view));
            int n5 = Math.abs(p3.l0(view) - p3.l0(view2));
            float f3 = (float)n3 / (float)(n5 + 1);
            return Math.round((float)n4 * f3 + (float)(i3.m() - i3.g(view)));
        }
        return 0;
    }

    public static int c(RecyclerView.z z3, i i3, View view, View view2, RecyclerView.p p3, boolean bl) {
        if (p3.O() != 0 && z3.b() != 0 && view != null && view2 != null) {
            if (!bl) {
                return z3.b();
            }
            int n3 = i3.d(view2);
            int n4 = i3.g(view);
            int n5 = Math.abs(p3.l0(view) - p3.l0(view2));
            return (int)((float)(n3 - n4) / (float)(n5 + 1) * (float)z3.b());
        }
        return 0;
    }
}

