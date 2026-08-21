/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.view.Display
 *  android.view.WindowManager
 */
package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.material.internal.e0;
import com.google.android.material.internal.f0;

public abstract class d0 {
    public static Rect a(Context context) {
        context = (WindowManager)context.getSystemService("window");
        if (Build.VERSION.SDK_INT >= 30) {
            return b.a((WindowManager)context);
        }
        return a.a((WindowManager)context);
    }

    public static abstract class a {
        public static Rect a(WindowManager windowManager) {
            Display display = windowManager.getDefaultDisplay();
            windowManager = new Point();
            display.getRealSize((Point)windowManager);
            display = new Rect();
            display.right = windowManager.x;
            display.bottom = windowManager.y;
            return display;
        }
    }

    public static abstract class b {
        public static Rect a(WindowManager windowManager) {
            return f0.a(e0.a(windowManager));
        }
    }
}

