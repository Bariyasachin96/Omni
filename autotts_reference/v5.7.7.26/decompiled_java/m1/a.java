/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.os.Build$VERSION
 */
package m1;

import android.graphics.Canvas;
import android.os.Build;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class a {
    public static Method a;
    public static Method b;
    public static boolean c;

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void a(Canvas var0, boolean var1_3) {
        var2_4 = Build.VERSION.SDK_INT;
        if (var2_4 >= 29) {
            if (var1_3) {
                m1.a$a.b(var0);
                return;
            }
            m1.a$a.a(var0);
            return;
        }
        if (var2_4 == 28) throw new IllegalStateException("This method doesn't work on Pie!");
        if (m1.a.c) ** GOTO lbl-1000
        try {
            m1.a.a = var3_5 = Canvas.class.getDeclaredMethod("insertReorderBarrier", null);
            var3_5.setAccessible(true);
            m1.a.b = var3_5 = Canvas.class.getDeclaredMethod("insertInorderBarrier", null);
            var3_5.setAccessible(true);
lbl15:
            // 2 sources

            while (true) {
                m1.a.c = true;
                break;
            }
        }
        catch (NoSuchMethodException var3_6) {
            ** continue;
        }
lbl-1000:
        // 2 sources

        {
            block14: {
                block13: {
                    if (var1_3) {
                        var3_5 = m1.a.a;
                        if (var3_5 == null) break block13;
                        var3_5.invoke((Object)var0, null);
                    }
                }
                if (var1_3) break block14;
                var3_5 = m1.a.b;
                if (var3_5 == null) break block14;
                try {
                    var3_5.invoke((Object)var0, null);
                    return;
                }
                catch (InvocationTargetException var0_1) {
                    throw new RuntimeException(var0_1.getCause());
                }
            }
            return;
        }
        catch (IllegalAccessException var0_2) {
            return;
        }
    }

    public static abstract class a {
        public static void a(Canvas canvas) {
            canvas.disableZ();
        }

        public static void b(Canvas canvas) {
            canvas.enableZ();
        }
    }
}

