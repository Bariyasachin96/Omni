/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Build$VERSION
 *  android.view.InputDevice
 *  android.view.ViewConfiguration
 */
package o0;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.InputDevice;
import android.view.ViewConfiguration;
import java.util.Objects;
import n0.i;
import o0.a1;
import o0.z0;

public abstract class b1 {
    public static int a(Resources resources, int n3, i i3, int n4) {
        if (n3 != -1) {
            if (n3 != 0 && (n3 = resources.getDimensionPixelSize(n3)) >= 0) {
                return n3;
            }
            return n4;
        }
        return (Integer)i3.get();
    }

    public static int b(Resources resources, String string, String string2) {
        return resources.getIdentifier(string, string2, "android");
    }

    public static int c(Resources resources, int n3, int n4) {
        if (n3 == 0x400000 && n4 == 26) {
            return b1.b(resources, "config_viewMaxRotaryEncoderFlingVelocity", "dimen");
        }
        return -1;
    }

    public static int d(Resources resources, int n3, int n4) {
        if (n3 == 0x400000 && n4 == 26) {
            return b1.b(resources, "config_viewMinRotaryEncoderFlingVelocity", "dimen");
        }
        return -1;
    }

    public static float e(ViewConfiguration viewConfiguration, Context context) {
        return a.a(viewConfiguration);
    }

    public static int f(Context context, ViewConfiguration viewConfiguration, int n3, int n4, int n5) {
        if (Build.VERSION.SDK_INT >= 34) {
            return c.a(viewConfiguration, n3, n4, n5);
        }
        if (!b1.i(n3, n4, n5)) {
            return Integer.MIN_VALUE;
        }
        context = context.getResources();
        n3 = b1.c((Resources)context, n5, n4);
        Objects.requireNonNull(viewConfiguration);
        return b1.a((Resources)context, n3, new z0(viewConfiguration), Integer.MIN_VALUE);
    }

    public static int g(Context context, ViewConfiguration viewConfiguration, int n3, int n4, int n5) {
        if (Build.VERSION.SDK_INT >= 34) {
            return c.b(viewConfiguration, n3, n4, n5);
        }
        if (!b1.i(n3, n4, n5)) {
            return Integer.MAX_VALUE;
        }
        context = context.getResources();
        n3 = b1.d((Resources)context, n5, n4);
        Objects.requireNonNull(viewConfiguration);
        return b1.a((Resources)context, n3, new a1(viewConfiguration), Integer.MAX_VALUE);
    }

    public static float h(ViewConfiguration viewConfiguration, Context context) {
        return a.b(viewConfiguration);
    }

    public static boolean i(int n3, int n4, int n5) {
        InputDevice inputDevice = InputDevice.getDevice((int)n3);
        return inputDevice != null && inputDevice.getMotionRange(n4, n5) != null;
    }

    public static boolean j(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return b.b(viewConfiguration);
        }
        viewConfiguration = context.getResources();
        int n3 = b1.b((Resources)viewConfiguration, "config_showMenuShortcutsWhenKeyboardPresent", "bool");
        return n3 != 0 && viewConfiguration.getBoolean(n3);
    }

    public static abstract class a {
        public static float a(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledHorizontalScrollFactor();
        }

        public static float b(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledVerticalScrollFactor();
        }
    }

    public static abstract class b {
        public static int a(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledHoverSlop();
        }

        public static boolean b(ViewConfiguration viewConfiguration) {
            return viewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent();
        }
    }

    public static abstract class c {
        public static int a(ViewConfiguration viewConfiguration, int n3, int n4, int n5) {
            return viewConfiguration.getScaledMaximumFlingVelocity(n3, n4, n5);
        }

        public static int b(ViewConfiguration viewConfiguration, int n3, int n4, int n5) {
            return viewConfiguration.getScaledMinimumFlingVelocity(n3, n4, n5);
        }
    }
}

