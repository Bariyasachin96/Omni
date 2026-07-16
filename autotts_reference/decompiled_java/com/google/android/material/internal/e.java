/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.view.Window
 */
package com.google.android.material.internal;

import android.content.Context;
import android.os.Build;
import android.view.Window;
import g0.a;
import o0.l1;

public abstract class e {
    public static void a(Window window, boolean bl, Integer n3, Integer n4) {
        Integer n5;
        Integer n6;
        int n7;
        int n8;
        block7: {
            block6: {
                n8 = 0;
                n7 = n3 != null && n3 != 0 ? 0 : 1;
                if (n4 == null || n4 == 0) {
                    n8 = 1;
                }
                if (n7 != 0) break block6;
                n6 = n3;
                n5 = n4;
                if (n8 == 0) break block7;
            }
            int n9 = h2.a.b(window.getContext(), 0x1010031, -16777216);
            if (n7 != 0) {
                n3 = n9;
            }
            n6 = n3;
            n5 = n4;
            if (n8 != 0) {
                n5 = n9;
                n6 = n3;
            }
        }
        l1.b(window, bl ^ true);
        n7 = e.c(window.getContext(), bl);
        n8 = e.b(window.getContext(), bl);
        window.setStatusBarColor(n7);
        window.setNavigationBarColor(n8);
        e.f(window, e.d(n7, h2.a.h(n6)));
        e.e(window, e.d(n8, h2.a.h(n5)));
    }

    public static int b(Context context, boolean bl) {
        if (bl && Build.VERSION.SDK_INT < 27) {
            return a.k(h2.a.b(context, 16843858, -16777216), 128);
        }
        if (bl) {
            return 0;
        }
        return h2.a.b(context, 16843858, -16777216);
    }

    public static int c(Context context, boolean bl) {
        if (bl) {
            return 0;
        }
        return h2.a.b(context, 16843857, -16777216);
    }

    public static boolean d(int n3, boolean bl) {
        return h2.a.h(n3) || n3 == 0 && bl;
        {
        }
    }

    public static void e(Window window, boolean bl) {
        l1.a(window, window.getDecorView()).b(bl);
    }

    public static void f(Window window, boolean bl) {
        l1.a(window, window.getDecorView()).c(bl);
    }
}

