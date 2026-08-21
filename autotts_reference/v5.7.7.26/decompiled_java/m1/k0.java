/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package m1;

import android.view.View;
import m1.i0;
import m1.j0;

public abstract class k0
extends i0 {
    public static boolean g = true;

    @Override
    public void e(View view, int n3, int n4, int n5, int n6) {
        if (g) {
            try {
                m1.k0$a.a(view, n3, n4, n5, n6);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                g = false;
            }
        }
    }

    public static abstract class a {
        public static void a(View view, int n3, int n4, int n5, int n6) {
            j0.a(view, n3, n4, n5, n6);
        }
    }
}

