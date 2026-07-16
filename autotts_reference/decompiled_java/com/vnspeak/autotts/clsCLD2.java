/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.vnspeak.autotts;

import android.content.Context;
import c3.k;
import com.vnspeak.autotts.AutoTtsService;
import com.vnspeak.autotts.a;

public abstract class clsCLD2 {
    static {
        System.loadLibrary("cld2");
    }

    public static int a(String string) {
        if (string != null && !string.isEmpty()) {
            for (int i3 = 0; i3 < string.length(); ++i3) {
                char c3 = string.charAt(i3);
                if (Character.isWhitespace(c3) || c3 >= '0' && c3 <= '9' || clsCLD2.c(c3)) continue;
                return string.codePointAt(i3);
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String b(String object, int n3, int n4, Context object2) {
        Exception exception3;
        block15: {
            boolean bl;
            if (object == null) return "UNKNOWN";
            if (((String)object).isEmpty()) {
                return "UNKNOWN";
            }
            int n5 = ((String)object).length();
            int n6 = 0;
            while (n6 < n5) {
                int n7;
                block13: {
                    Exception exception22;
                    block14: {
                        n7 = n6 + 64;
                        Object object3 = ((String)object).substring(n6, Math.min(n7, n5));
                        try {
                            Object object4 = clsCLD2.nativeGetLanguage((String)object3, n3, n4, (Context)object2);
                            if (object4 == null || ((String)object4).equals("UNKNOWN")) break block13;
                            if (AutoTtsService.S) {
                                return object4;
                            }
                            if (k.o((String)object4).booleanValue()) {
                                return object4;
                            }
                            n6 = clsCLD2.a((String)object3);
                            if (n6 == -1 || (object4 = a.e(n6, k.f)) == null) break block13;
                            object3 = ((a.a)object4).b();
                            if (object3 != null && k.o((String)object3).booleanValue()) {
                                return object3;
                            }
                        }
                        catch (Exception exception22) {
                            break block14;
                        }
                        for (Object object4 : ((a.a)object4).a()) {
                            if (object4 == null || !(bl = k.o((String)object4).booleanValue())) continue;
                            return object4;
                        }
                        break block13;
                    }
                    exception22.printStackTrace();
                }
                n6 = n7;
            }
            if (AutoTtsService.S) {
                return "UNKNOWN";
            }
            n3 = clsCLD2.a((String)object);
            if (n3 == -1) return "UNKNOWN";
            try {
                object2 = a.e(n3, k.f);
                if (object2 == null) return "UNKNOWN";
                object = ((a.a)object2).b();
                if (object != null && k.o((String)object).booleanValue()) {
                    return object;
                }
            }
            catch (Exception exception3) {
                break block15;
            }
            object = ((a.a)object2).a().iterator();
            do {
                if (!object.hasNext()) return "UNKNOWN";
            } while ((object2 = (String)object.next()) == null || !(bl = k.o((String)object2).booleanValue()));
            return object2;
        }
        exception3.printStackTrace();
        return "UNKNOWN";
    }

    public static boolean c(char c3) {
        return c3 >= '!' && c3 <= '/' || c3 >= ':' && c3 <= '@' || c3 >= '[' && c3 <= '`' || c3 >= '{' && c3 <= '~';
    }

    private static native String nativeGetLanguage(String var0, int var1, int var2, Context var3);
}

