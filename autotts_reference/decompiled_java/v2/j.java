/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package v2;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.internal.c0;
import v2.e;
import v2.f;
import v2.g;
import v2.i;
import v2.n;

public abstract class j {
    public static e a(int n3) {
        if (n3 != 0) {
            if (n3 != 1) {
                return j.b();
            }
            return new f();
        }
        return new n();
    }

    public static e b() {
        return new n();
    }

    public static g c() {
        return new g();
    }

    public static void d(View view, float f3) {
        if ((view = view.getBackground()) instanceof i) {
            ((i)view).h0(f3);
        }
    }

    public static void e(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof i) {
            j.f(view, (i)drawable);
        }
    }

    public static void f(View view, i i3) {
        if (i3.Y()) {
            i3.n0(c0.k(view));
        }
    }
}

