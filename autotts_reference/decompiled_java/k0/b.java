/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.LocaleList
 */
package k0;

import android.os.LocaleList;
import java.util.Locale;
import k0.c;
import k0.d;

public final class b {
    public static final b b = k0.b.a(new Locale[0]);
    public final c a;

    public b(c c3) {
        this.a = c3;
    }

    public static b a(Locale ... localeArray) {
        return k0.b.h(k0.b$b.a(localeArray));
    }

    public static b b(String stringArray) {
        if (stringArray != null && !stringArray.isEmpty()) {
            stringArray = stringArray.split(",", -1);
            int n3 = stringArray.length;
            Locale[] localeArray = new Locale[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                localeArray[i3] = k0.b$a.a(stringArray[i3]);
            }
            return k0.b.a(localeArray);
        }
        return k0.b.d();
    }

    public static b d() {
        return b;
    }

    public static b h(LocaleList localeList) {
        return new b(new d(localeList));
    }

    public Locale c(int n3) {
        return this.a.get(n3);
    }

    public boolean e() {
        return this.a.isEmpty();
    }

    public boolean equals(Object object) {
        return object instanceof b && this.a.equals(((b)object).a);
    }

    public int f() {
        return this.a.size();
    }

    public String g() {
        return this.a.a();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a.toString();
    }

    public static abstract class a {
        public static final Locale[] a = new Locale[]{new Locale("en", "XA"), new Locale("ar", "XB")};

        public static Locale a(String string) {
            return Locale.forLanguageTag(string);
        }

        public static boolean b(Locale locale) {
            Locale[] localeArray = a;
            int n3 = localeArray.length;
            for (int i3 = 0; i3 < n3; ++i3) {
                if (!localeArray[i3].equals(locale)) continue;
                return true;
            }
            return false;
        }

        public static boolean c(Locale object, Locale locale) {
            if (((Locale)object).equals(locale)) {
                return true;
            }
            if (!((Locale)object).getLanguage().equals(locale.getLanguage())) {
                return false;
            }
            if (!k0.b$a.b((Locale)object) && !k0.b$a.b(locale)) {
                String string = m0.b.a((Locale)object);
                if (string.isEmpty()) {
                    return ((String)(object = ((Locale)object).getCountry())).isEmpty() || ((String)object).equals(locale.getCountry());
                    {
                    }
                }
                return string.equals(m0.b.a(locale));
            }
            return false;
        }
    }

    public static abstract class b {
        public static LocaleList a(Locale ... localeArray) {
            return new LocaleList(localeArray);
        }

        public static LocaleList b() {
            return LocaleList.getAdjustedDefault();
        }

        public static LocaleList c() {
            return LocaleList.getDefault();
        }
    }
}

