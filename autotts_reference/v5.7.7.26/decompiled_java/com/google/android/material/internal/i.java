/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build
 */
package com.google.android.material.internal;

import android.os.Build;
import java.util.Locale;

public abstract class i {
    public static String a() {
        String string = Build.MANUFACTURER;
        if (string != null) {
            return string.toLowerCase(Locale.ENGLISH);
        }
        return "";
    }

    public static boolean b() {
        return i.c() || i.e();
        {
        }
    }

    public static boolean c() {
        return i.a().equals("lge");
    }

    public static boolean d() {
        return i.a().equals("meizu");
    }

    public static boolean e() {
        return i.a().equals("samsung");
    }
}

