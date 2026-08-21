/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.Window
 */
package o0;

import android.os.Build;
import android.view.View;
import android.view.Window;
import o0.y2;

public abstract class l1 {
    public static y2 a(Window window, View view) {
        return new y2(window, view);
    }

    public static void b(Window window, boolean bl) {
        if (Build.VERSION.SDK_INT >= 30) {
            b.a(window, bl);
            return;
        }
        a.a(window, bl);
    }

    public static abstract class a {
        public static void a(Window window, boolean bl) {
            window = window.getDecorView();
            int n3 = window.getSystemUiVisibility();
            n3 = bl ? (n3 &= 0xFFFFF8FF) : (n3 |= 0x700);
            window.setSystemUiVisibility(n3);
        }
    }

    public static abstract class b {
        public static void a(Window window, boolean bl) {
            window.setDecorFitsSystemWindows(bl);
        }
    }
}

