/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.vnspeak.autotts;

import android.content.Context;
import c3.n;
import com.vnspeak.autotts.AutoTtsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class clsCLD2 {
    static {
        System.loadLibrary("cld2");
    }

    public static int a(String string) {
        if (string != null && !string.isEmpty()) {
            for (int i3 = 0; i3 < string.length(); ++i3) {
                char c3 = string.charAt(i3);
                if (Character.isWhitespace(c3) || c3 >= '0' && c3 <= '9' || clsCLD2.e(c3)) continue;
                return string.codePointAt(i3);
            }
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String b(String var0, int var1_2, int var2_3, Context var3_4) {
        if (var0 == null) return "UNKNOWN";
        if (var0.isEmpty()) {
            return "UNKNOWN";
        }
        if (var0.length() == 1 && AutoTtsService.b0) {
            return "UNKNOWN";
        }
        var6_5 = var0.length();
        var4_6 = 0;
        while (true) lbl-1000:
        // 2 sources

        {
            if (var4_6 < var6_5) {
                var5_7 = var4_6 + 64;
                var9_11 = var0.substring(var4_6, Math.min(var5_7, var6_5));
                var8_9 = clsCLD2.nativeGetLanguage(var9_11, var1_2, var2_3, (Context)var3_4);
                if (var8_9 == null || var8_9.equals("UNKNOWN")) break block16;
                if (AutoTtsService.a0) {
                    return var8_9;
                }
                if (n.n((String)var8_9).booleanValue()) {
                    return var8_9;
                }
                var4_6 = clsCLD2.a(var9_11);
                if (var4_6 != -1 && (var8_9 = com.vnspeak.autotts.a.e(var4_6, n.f)) != null) {
                    var9_11 = var8_9.b();
                    if (var9_11 != null && n.n(var9_11).booleanValue()) {
                        return var9_11;
                    }
                    for (String var9_11 : var8_9.a()) {
                        if (var9_11 == null || !(var7_8 = n.n(var9_11).booleanValue())) continue;
                        return var9_11;
                    }
                }
                break block16;
            }
            if (AutoTtsService.a0) {
                return "UNKNOWN";
            }
            var1_2 = clsCLD2.a(var0);
            if (var1_2 == -1) return "UNKNOWN";
            var3_4 = com.vnspeak.autotts.a.e(var1_2, n.f);
            if (var3_4 == null) return "UNKNOWN";
            var0 = var3_4.b();
            if (var0 != null && n.n((String)var0).booleanValue()) {
                return var0;
            }
            var0 = var3_4.a().iterator();
            do {
                if (var0.hasNext() == false) return "UNKNOWN";
            } while ((var3_4 = (String)var0.next()) == null || !(var7_8 = n.n((String)var3_4).booleanValue()));
            return var3_4;
            break;
        }
        catch (Exception var0_1) {
            return "UNKNOWN";
        }
        {
            block16: {
                catch (Exception var8_10) {}
            }
            var4_6 = var5_7;
            ** while (true)
        }
    }

    public static List c(String stringArray, int n3, int n4, Object object) {
        ArrayList<a> arrayList = new ArrayList<a>();
        int n5 = 0;
        if (stringArray != null && !stringArray.isEmpty()) {
            if (AutoTtsService.b0 && stringArray.length() == 1) {
                arrayList.add(new a("un", clsCLD2.d((String)stringArray), (String)stringArray));
                return arrayList;
            }
            stringArray = clsCLD2.nativeGetLanguages((String)stringArray, n3, n4, object);
            n3 = n5;
            if (stringArray != null) {
                while ((n4 = n3 + 2) < stringArray.length) {
                    arrayList.add(new a(stringArray[n3], "1".equals(stringArray[n3 + 1]), stringArray[n4]));
                    n3 += 3;
                }
            }
            return arrayList;
        }
        arrayList.add(new a("un", false, ""));
        return arrayList;
    }

    public static boolean d(String object) {
        return (object = Character.UnicodeScript.of(object.codePointAt(0))) == Character.UnicodeScript.LATIN || object == Character.UnicodeScript.COMMON || object == Character.UnicodeScript.INHERITED;
        {
        }
    }

    public static boolean e(char c3) {
        return c3 >= '!' && c3 <= '/' || c3 >= ':' && c3 <= '@' || c3 >= '[' && c3 <= '`' || c3 >= '{' && c3 <= '~';
    }

    public static void f(Set object) {
        object = object == null ? null : object.toArray(new String[0]);
        clsCLD2.nativeSetLanguageHints(object);
    }

    private static native String nativeGetLanguage(String var0, int var1, int var2, Context var3);

    public static native String[] nativeGetLanguages(String var0, int var1, int var2, Object var3);

    public static native void nativeSetLanguageHints(String[] var0);

    public static class a {
        public final String a;
        public final boolean b;
        public final String c;

        public a(String string, boolean bl, String string2) {
            this.a = string;
            this.b = bl;
            this.c = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(this.a);
            String string = this.b ? "/latin" : "/non-latin";
            stringBuilder.append(string);
            stringBuilder.append("] ");
            stringBuilder.append(this.c);
            return stringBuilder.toString();
        }
    }
}

