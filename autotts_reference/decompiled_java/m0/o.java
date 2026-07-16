/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package m0;

import android.text.TextUtils;
import java.util.Locale;

public abstract class o {
    public static int a(Locale locale) {
        return TextUtils.getLayoutDirectionFromLocale((Locale)locale);
    }
}

