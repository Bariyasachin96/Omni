/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.View
 */
package m1;

import android.os.Build;
import android.view.View;
import m1.k0;
import m1.l0;

public class m0
extends k0 {
    public static boolean h = true;

    @Override
    public void g(View view, int n3) {
        if (Build.VERSION.SDK_INT == 28) {
            super.g(view, n3);
            return;
        }
        if (h) {
            try {
                m1.m0$a.a(view, n3);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                h = false;
            }
        }
    }

    public static abstract class a {
        public static void a(View view, int n3) {
            l0.a(view, n3);
        }
    }
}

