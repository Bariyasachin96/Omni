/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package u1;

import android.text.TextUtils;

public abstract class b {
    public static void a(boolean bl) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public static String b(String string, Object object) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            return string;
        }
        throw new IllegalArgumentException(String.valueOf(object));
    }

    public static Object c(Object object) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException("null reference");
    }
}

