/*
 * Decompiled with CFR 0.152.
 */
package c3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class e {
    public static final Map a;
    public static final Map b;
    public static final String[][] c;
    public static final String[][] d;
    public static final String[] e;
    public static final String[] f;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        String[] stringArray = new String[]{"arm", "hye"};
        String[] stringArray2 = new String[]{"baq", "eus"};
        String[] stringArray3 = new String[]{"chi", "zho"};
        String[] stringArray4 = new String[]{"cze", "ces"};
        Object object = new String[]{"dut", "nld"};
        String[] stringArray5 = new String[]{"fre", "fra"};
        Object object2 = new String[]{"ger", "deu"};
        String[] stringArray6 = new String[]{"gre", "ell"};
        Object object3 = new String[]{"per", "fas"};
        String[] stringArray7 = new String[]{"rum", "ron"};
        String[] stringArray8 = new String[]{"slo", "slk"};
        String[] stringArray9 = new String[]{"tib", "bod"};
        String[] stringArray10 = new String[]{"wel", "cym"};
        c = new String[][]{{"alb", "sqi"}, stringArray, stringArray2, {"bur", "mya"}, stringArray3, stringArray4, object, stringArray5, {"geo", "kat"}, object2, stringArray6, {"ice", "isl"}, {"mac", "mkd"}, {"mao", "mri"}, {"may", "msa"}, object3, stringArray7, stringArray8, stringArray9, stringArray10};
        stringArray9 = new String[]{"cs", "ces"};
        object = new String[]{"ka", "kat"};
        object2 = new String[]{"de", "deu"};
        String[] stringArray11 = new String[]{"mk", "mkd"};
        String[] stringArray12 = new String[]{"ms", "msa"};
        object3 = new String[]{"fa", "fas"};
        stringArray6 = new String[]{"sk", "slk"};
        d = new String[][]{{"sq", "sqi"}, {"hy", "hye"}, {"eu", "eus"}, {"my", "mya"}, {"zh", "zho"}, stringArray9, {"nl", "nld"}, {"fr", "fra"}, object, object2, {"el", "ell"}, {"is", "isl"}, stringArray11, {"mi", "mri"}, stringArray12, object3, {"ro", "ron"}, stringArray6, {"bo", "bod"}, {"cy", "cym"}};
        e = new String[]{"cmn", "lzh", "gan", "hak", "wuu", "hsn", "cjy", "nan", "mnp"};
        f = new String[]{"fil", "ceb", "haw", "hmn", "war", "nso", "syr", "chr", "lus", "sco"};
        object = new HashMap(256);
        object2 = new HashMap(320);
        String[] stringArray13 = Locale.getISOLanguages();
        int n3 = stringArray13.length;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            block11: {
                if (n5 < n3) {
                    String string = stringArray13[n5];
                    object3 = new Locale(string);
                    object3 = ((Locale)object3).getISO3Language();
                    if (object3 == null || ((String)object3).isEmpty()) break block11;
                    object.put(string, object3);
                    if (!object2.containsKey(object3)) {
                        object2.put(object3, string);
                    }
                } else {
                    for (String[] stringArray14 : d) {
                        object.put(stringArray14[0], stringArray14[1]);
                        object2.put(stringArray14[1], stringArray14[0]);
                    }
                    for (String[] stringArray15 : c) {
                        object3 = (String)object2.get(stringArray15[1]);
                        if (object3 == null) continue;
                        object2.put(stringArray15[0], object3);
                    }
                    object.put("iw", "heb");
                    object.put("he", "heb");
                    object.put("in", "ind");
                    object.put("id", "ind");
                    object.put("ji", "yid");
                    object.put("yi", "yid");
                    object2.put("heb", "he");
                    object2.put("ind", "id");
                    object2.put("yid", "yi");
                    String[] stringArray16 = e;
                    n3 = stringArray16.length;
                    for (n5 = 0; n5 < n3; ++n5) {
                        object2.put(stringArray16[n5], "zh");
                    }
                    object2.put("zho", "zh");
                    object2.put("yue", "yue");
                    String[] stringArray17 = f;
                    n3 = stringArray17.length;
                    n5 = n4;
                    while (true) {
                        if (n5 >= n3) {
                            a = Collections.unmodifiableMap(object);
                            b = Collections.unmodifiableMap(object2);
                            return;
                        }
                        String string = stringArray17[n5];
                        object2.put(string, string);
                        object.put(string, string);
                        ++n5;
                    }
                    catch (Exception exception) {}
                }
            }
            ++n5;
        }
    }

    public static String a(String string) {
        int n3;
        block3: {
            if (string == null) {
                return null;
            }
            if ((string = string.trim()).isEmpty()) {
                return null;
            }
            int n4 = string.length();
            int n5 = 0;
            while (true) {
                n3 = n4;
                if (n5 >= string.length()) break block3;
                n3 = string.charAt(n5);
                if (n3 == 45 || n3 == 95) break;
                ++n5;
            }
            n3 = n5;
        }
        return string.substring(0, n3).toLowerCase(Locale.ROOT);
    }

    public static String b(String string) {
        String string2 = c3.e.a(string);
        if (string2 == null) {
            return null;
        }
        string = (String)b.get(string2);
        if (string != null) {
            return string;
        }
        if (string2.length() == 2 && a.containsKey(string2)) {
            return string2;
        }
        return null;
    }

    public static String c(String string) {
        if ((string = c3.e.a(string)) == null) {
            return null;
        }
        Object object = a;
        String string2 = (String)object.get(string);
        if (string2 != null) {
            return string2;
        }
        if (string.length() == 3 && (string2 = (String)b.get(string)) != null) {
            if ((object = (String)object.get(string2)) != null) {
                return object;
            }
            return string;
        }
        return null;
    }
}

