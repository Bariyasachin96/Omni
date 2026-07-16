/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 */
package m0;

import android.icu.util.ULocale;
import java.util.Locale;

public abstract class b {
    public static String a(Locale locale) {
        return a.c(a.a(a.b(locale)));
    }

    public static abstract class a {
        public static ULocale a(Object object) {
            return ULocale.addLikelySubtags((ULocale)((ULocale)object));
        }

        public static ULocale b(Locale locale) {
            return ULocale.forLocale((Locale)locale);
        }

        public static String c(Object object) {
            return ((ULocale)object).getScript();
        }
    }
}

