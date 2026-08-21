/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 */
package g0;

import android.graphics.Paint;

public abstract class c {
    public static final ThreadLocal a = new ThreadLocal();

    public static boolean a(Paint paint, String string) {
        return g0.c$a.a(paint, string);
    }

    public static abstract class a {
        public static boolean a(Paint paint, String string) {
            return paint.hasGlyph(string);
        }
    }
}

