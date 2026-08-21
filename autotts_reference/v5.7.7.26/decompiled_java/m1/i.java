/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewGroup
 */
package m1;

import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import m1.e;
import m1.g;
import m1.h;

public abstract class i {
    public static e a(View view, ViewGroup viewGroup, Matrix matrix) {
        if (Build.VERSION.SDK_INT == 28) {
            return g.b(view, viewGroup, matrix);
        }
        return h.b(view, viewGroup, matrix);
    }

    public static void b(View view) {
        if (Build.VERSION.SDK_INT == 28) {
            g.f(view);
            return;
        }
        h.f(view);
    }
}

