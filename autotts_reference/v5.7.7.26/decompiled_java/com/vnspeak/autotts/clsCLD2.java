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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class clsCLD2 {
    static {
        System.loadLibrary("cld2");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int a(int n3) {
        if (n3 >= 119808 && n3 <= 120483) {
            if ((n3 = (n3 - 119808) % 52) >= 26) return n3 + 71;
            return n3 + 65;
        }
        if (n3 == 120484) {
            return 105;
        }
        if (n3 == 120485) {
            return 106;
        }
        if (n3 >= 120782 && n3 <= 120831) {
            return (n3 - 120782) % 10 + 48;
        }
        if (n3 >= 65296 && n3 <= 65305) {
            return n3 - 65248;
        }
        if (n3 >= 65313 && n3 <= 65338) {
            return n3 - 65248;
        }
        if (n3 >= 65345 && n3 <= 65370) {
            return n3 - 65248;
        }
        if (n3 == 8450) return 67;
        if (n3 == 8469) return 78;
        if (n3 == 8484 || n3 == 8488) return 90;
        if (n3 == 8492) return 66;
        if (n3 == 8493) return 67;
        if (n3 == 8499) return 77;
        if (n3 == 8500) return 111;
        switch (n3) {
            default: {
                switch (n3) {
                    default: {
                        switch (n3) {
                            default: {
                                switch (n3) {
                                    default: {
                                        if (n3 >= 9398 && n3 <= 9423) {
                                            return n3 - 9333;
                                        }
                                        if (n3 >= 9424 && n3 <= 9449) {
                                            return n3 - 9327;
                                        }
                                        if (n3 == 9450) {
                                            return 48;
                                        }
                                        if (n3 >= 9312 && n3 <= 9320) {
                                            return n3 - 9263;
                                        }
                                        if (n3 >= 9372 && n3 <= 9397) {
                                            return n3 - 9275;
                                        }
                                        if (n3 >= 127312 && n3 <= 127337) {
                                            return n3 - 127247;
                                        }
                                        if (n3 >= 127344 && n3 <= 127369) {
                                            return n3 - 127279;
                                        }
                                        if (n3 >= 127280 && n3 <= 127305) {
                                            return n3 - 127215;
                                        }
                                        switch (n3) {
                                            default: {
                                                if (n3 == 688) return 104;
                                                if (n3 == 7491) return 97;
                                                if (n3 == 7501) return 103;
                                                if (n3 == 7506) return 111;
                                                if (n3 == 7515) return 118;
                                                if (n3 == 7580) return 99;
                                                if (n3 == 7584) return 102;
                                                if (n3 == 7611) return 122;
                                                if (n3 == 8305) return 105;
                                                if (n3 == 8319) return 110;
                                                if (n3 == 690) return 106;
                                                if (n3 == 691) return 114;
                                                if (n3 == 695) return 119;
                                                if (n3 == 696) return 121;
                                                if (n3 == 7503) return 107;
                                                if (n3 == 7504) return 109;
                                                switch (n3) {
                                                    default: {
                                                        switch (n3) {
                                                            default: {
                                                                switch (n3) {
                                                                    default: {
                                                                        if (n3 == 8304) {
                                                                            return 48;
                                                                        }
                                                                        if (n3 == 185) {
                                                                            return 49;
                                                                        }
                                                                        if (n3 == 178) {
                                                                            return 50;
                                                                        }
                                                                        if (n3 == 179) {
                                                                            return 51;
                                                                        }
                                                                        if (n3 >= 8308 && n3 <= 8313) {
                                                                            return n3 - 8256;
                                                                        }
                                                                        if (n3 >= 8320 && n3 <= 8329) {
                                                                            return n3 - 8272;
                                                                        }
                                                                        switch (n3) {
                                                                            default: {
                                                                                return n3;
                                                                            }
                                                                            case 8348: {
                                                                                return 116;
                                                                            }
                                                                            case 8347: {
                                                                                return 115;
                                                                            }
                                                                            case 8346: {
                                                                                return 112;
                                                                            }
                                                                            case 8345: {
                                                                                return 110;
                                                                            }
                                                                            case 8344: {
                                                                                return 109;
                                                                            }
                                                                            case 8343: {
                                                                                return 108;
                                                                            }
                                                                            case 8342: {
                                                                                return 107;
                                                                            }
                                                                            case 8341: {
                                                                                return 104;
                                                                            }
                                                                            case 8339: {
                                                                                return 120;
                                                                            }
                                                                            case 8338: {
                                                                                return 111;
                                                                            }
                                                                            case 8337: {
                                                                                return 101;
                                                                            }
                                                                            case 8336: 
                                                                        }
                                                                        return 97;
                                                                    }
                                                                    case 7512: {
                                                                        return 117;
                                                                    }
                                                                    case 7511: {
                                                                        return 116;
                                                                    }
                                                                    case 7510: 
                                                                }
                                                                return 112;
                                                            }
                                                            case 7497: {
                                                                return 101;
                                                            }
                                                            case 7496: {
                                                                return 100;
                                                            }
                                                            case 7495: 
                                                        }
                                                        return 98;
                                                    }
                                                    case 739: {
                                                        return 120;
                                                    }
                                                    case 738: {
                                                        return 115;
                                                    }
                                                    case 737: 
                                                }
                                                return 108;
                                            }
                                            case 42801: {
                                                return 83;
                                            }
                                            case 42800: {
                                                return 70;
                                            }
                                            case 7458: {
                                                return 90;
                                            }
                                            case 7457: {
                                                return 87;
                                            }
                                            case 7456: {
                                                return 86;
                                            }
                                            case 7452: {
                                                return 85;
                                            }
                                            case 7451: {
                                                return 84;
                                            }
                                            case 7448: {
                                                return 80;
                                            }
                                            case 7439: {
                                                return 79;
                                            }
                                            case 7437: {
                                                return 77;
                                            }
                                            case 7435: {
                                                return 75;
                                            }
                                            case 7434: {
                                                return 74;
                                            }
                                            case 7431: {
                                                return 69;
                                            }
                                            case 7429: {
                                                return 68;
                                            }
                                            case 7428: {
                                                return 67;
                                            }
                                            case 7424: {
                                                return 65;
                                            }
                                            case 671: {
                                                return 76;
                                            }
                                            case 668: {
                                                return 72;
                                            }
                                            case 665: {
                                                return 66;
                                            }
                                            case 655: {
                                                return 89;
                                            }
                                            case 640: {
                                                return 82;
                                            }
                                            case 628: {
                                                return 78;
                                            }
                                            case 618: {
                                                return 73;
                                            }
                                            case 610: 
                                        }
                                        return 71;
                                    }
                                    case 8497: {
                                        return 70;
                                    }
                                    case 8496: {
                                        return 69;
                                    }
                                    case 8495: 
                                }
                                return 101;
                            }
                            case 8475: 
                            case 8476: 
                            case 8477: {
                                return 82;
                            }
                            case 8474: {
                                return 81;
                            }
                            case 8473: 
                        }
                        return 80;
                    }
                    case 8467: {
                        return 108;
                    }
                    case 8466: {
                        return 76;
                    }
                    case 8464: 
                    case 8465: 
                }
                return 73;
            }
            case 8462: {
                return 104;
            }
            case 8459: 
            case 8460: 
            case 8461: {
                return 72;
            }
            case 8458: 
        }
        return 103;
    }

    public static String b(String string) {
        CharSequence charSequence;
        block2: {
            int n3;
            int n4 = 0;
            int n5 = 0;
            while (true) {
                charSequence = string;
                if (n5 >= string.length()) break block2;
                n3 = string.charAt(n5);
                if (n3 == 55349 || n3 == 55356 || n3 >= 8450 && n3 <= 8500 || n3 >= 65296 && n3 <= 65370 || n3 >= 9312 && n3 <= 9450 || n3 >= 7424 && n3 <= 7611 || n3 >= 610 && n3 <= 739 || n3 >= 8304 && n3 <= 8348 || n3 == 185 || n3 == 178 || n3 == 179 || n3 == 42800 || n3 == 42801) break;
                ++n5;
            }
            charSequence = new StringBuilder(string.length());
            n3 = string.length();
            for (n5 = n4; n5 < n3; n5 += Character.charCount(n4)) {
                n4 = string.codePointAt(n5);
                ((StringBuilder)charSequence).appendCodePoint(clsCLD2.a(n4));
            }
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public static int c(String string) {
        if (string != null && !string.isEmpty()) {
            for (int i3 = 0; i3 < string.length(); ++i3) {
                char c3 = string.charAt(i3);
                if (Character.isWhitespace(c3) || c3 >= '0' && c3 <= '9' || clsCLD2.h(c3)) continue;
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
    public static String d(String var0, int var1_2, int var2_3, Context var3_4) {
        if (var0 == null) return "UNKNOWN";
        if (var0.isEmpty()) {
            return "UNKNOWN";
        }
        if (var0.length() == 1 && AutoTtsService.c0) {
            return "UNKNOWN";
        }
        var6_5 = var0.length();
        var4_6 = 0;
        while (true) lbl-1000:
        // 2 sources

        {
            if (var4_6 < var6_5) {
                var5_7 = var4_6 + 64;
                var8_9 = var0.substring(var4_6, Math.min(var5_7, var6_5));
                var9_11 = clsCLD2.nativeGetLanguage((String)var8_9, var1_2, var2_3, (Context)var3_4);
                if (var9_11 == null || var9_11.equals("UNKNOWN")) break block16;
                if (AutoTtsService.b0) {
                    return var9_11;
                }
                if (n.n((String)var9_11).booleanValue()) {
                    return var9_11;
                }
                var4_6 = clsCLD2.c((String)var8_9);
                if (var4_6 != -1 && (var8_9 = com.vnspeak.autotts.a.e(var4_6, n.f)) != null) {
                    var9_11 = var8_9.b();
                    if (var9_11 != null && n.n((String)var9_11).booleanValue()) {
                        return var9_11;
                    }
                    for (Object var8_9 : var8_9.a()) {
                        if (var8_9 == null || !(var7_8 = n.n((String)var8_9).booleanValue())) continue;
                        return var8_9;
                    }
                }
                break block16;
            }
            if (AutoTtsService.b0) {
                return "UNKNOWN";
            }
            var1_2 = clsCLD2.c(var0);
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

    public static List e(String stringArray, int n3, int n4, Object object) {
        ArrayList<a> arrayList = new ArrayList<a>();
        int n5 = 0;
        if (stringArray != null && !stringArray.isEmpty()) {
            stringArray = clsCLD2.b((String)stringArray);
            if (AutoTtsService.c0 && stringArray.length() == 1) {
                arrayList.add(new a("un", clsCLD2.g((String)stringArray), (String)stringArray));
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

    public static a f(String object, int n3, int n4, Object stringArray) {
        if (object != null && !object.isEmpty()) {
            String string = clsCLD2.b((String)object);
            if (AutoTtsService.c0 && string.length() == 1) {
                return new a("un", clsCLD2.g(string), string);
            }
            if ((stringArray = clsCLD2.nativeGetLanguages(string, n3, n4, stringArray)) != null && stringArray.length >= 3) {
                Object object2;
                int n5;
                Object object3;
                int n6;
                if (stringArray.length == 3) {
                    return new a(stringArray[0], "1".equals(stringArray[1]), string);
                }
                object = new HashMap();
                HashMap<Object, boolean[]> hashMap = new HashMap<Object, boolean[]>();
                n3 = 0;
                while ((n6 = n3 + 2) < stringArray.length) {
                    object3 = new StringBuilder();
                    object3.append(stringArray[n3]);
                    object3.append("|");
                    n5 = n3 + 1;
                    object3.append(stringArray[n5]);
                    object2 = object3.toString();
                    object3 = (Integer)object.get(object2);
                    n4 = object3 == null ? 0 : object3.intValue();
                    object.put(object2, n4 + stringArray[n6].length());
                    if (!hashMap.containsKey(object2)) {
                        hashMap.put(object2, new boolean[]{"1".equals(stringArray[n5])});
                    }
                    n3 += 3;
                }
                object2 = object.entrySet().iterator();
                n4 = -1;
                object = null;
                stringArray = null;
                n5 = -1;
                while (object2.hasNext()) {
                    Map.Entry entry = object2.next();
                    n6 = (Integer)entry.getValue();
                    object3 = stringArray;
                    n3 = n4;
                    if (n6 > n4) {
                        object3 = (String)entry.getKey();
                        n3 = n6;
                    }
                    stringArray = object3;
                    n4 = n3;
                    if (((String)entry.getKey()).startsWith("un|")) continue;
                    stringArray = object3;
                    n4 = n3;
                    if (n6 <= n5) continue;
                    object = (String)entry.getKey();
                    n5 = n6;
                    stringArray = object3;
                    n4 = n3;
                }
                if (object == null) {
                    object = stringArray;
                }
                if (object == null) {
                    return new a("un", clsCLD2.g(string), string);
                }
                return new a(object.substring(0, object.indexOf(124)), ((boolean[])hashMap.get(object))[0], string);
            }
            return new a("un", clsCLD2.g(string), string);
        }
        return new a("un", false, "");
    }

    public static boolean g(String object) {
        return (object = Character.UnicodeScript.of(object.codePointAt(0))) == Character.UnicodeScript.LATIN || object == Character.UnicodeScript.COMMON || object == Character.UnicodeScript.INHERITED;
        {
        }
    }

    public static boolean h(char c3) {
        return c3 >= '!' && c3 <= '/' || c3 >= ':' && c3 <= '@' || c3 >= '[' && c3 <= '`' || c3 >= '{' && c3 <= '~';
    }

    public static void i(Set object) {
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

