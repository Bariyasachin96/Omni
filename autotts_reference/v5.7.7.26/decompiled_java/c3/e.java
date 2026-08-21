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
        String[] stringArray = new String[]{"alb", "sqi"};
        String[] stringArray2 = new String[]{"arm", "hye"};
        String[] stringArray3 = new String[]{"baq", "eus"};
        String[] stringArray4 = new String[]{"bur", "mya"};
        String[] stringArray5 = new String[]{"chi", "zho"};
        Object object = new String[]{"cze", "ces"};
        String[] stringArray6 = new String[]{"dut", "nld"};
        String[] stringArray7 = new String[]{"fre", "fra"};
        String[] stringArray8 = new String[]{"geo", "kat"};
        String[] stringArray9 = new String[]{"ger", "deu"};
        Object object2 = new String[]{"gre", "ell"};
        String[] stringArray10 = new String[]{"ice", "isl"};
        Object object3 = new String[]{"mac", "mkd"};
        String[] stringArray11 = new String[]{"mao", "mri"};
        String[] stringArray12 = new String[]{"may", "msa"};
        String[] stringArray13 = new String[]{"per", "fas"};
        String[] stringArray14 = new String[]{"rum", "ron"};
        String[] stringArray15 = new String[]{"slo", "slk"};
        String[] stringArray16 = new String[]{"wel", "cym"};
        c = new String[][]{stringArray, stringArray2, stringArray3, stringArray4, stringArray5, object, stringArray6, stringArray7, stringArray8, stringArray9, object2, stringArray10, object3, stringArray11, stringArray12, stringArray13, stringArray14, stringArray15, {"tib", "bod"}, stringArray16};
        stringArray8 = new String[]{"eu", "eus"};
        object2 = new String[]{"my", "mya"};
        stringArray9 = new String[]{"cs", "ces"};
        String[] stringArray17 = new String[]{"de", "deu"};
        String[] stringArray18 = new String[]{"el", "ell"};
        object = new String[]{"ro", "ron"};
        object3 = new String[]{"cy", "cym"};
        d = new String[][]{{"sq", "sqi"}, {"hy", "hye"}, stringArray8, object2, {"zh", "zho"}, stringArray9, {"nl", "nld"}, {"fr", "fra"}, {"ka", "kat"}, stringArray17, stringArray18, {"is", "isl"}, {"mk", "mkd"}, {"mi", "mri"}, {"ms", "msa"}, {"fa", "fas"}, object, {"sk", "slk"}, {"bo", "bod"}, object3};
        e = new String[]{"cmn", "lzh", "gan", "hak", "wuu", "hsn", "cjy", "nan", "mnp"};
        f = new String[]{"fil", "ceb", "haw", "hmn", "war", "nso", "syr", "chr", "lus", "sco"};
        object3 = new HashMap(256);
        object = new HashMap(320);
        String[] stringArray19 = Locale.getISOLanguages();
        int n3 = stringArray19.length;
        int n4 = 0;
        int n5 = 0;
        while (true) {
            block11: {
                if (n5 < n3) {
                    String string = stringArray19[n5];
                    object2 = new Locale(string);
                    object2 = ((Locale)object2).getISO3Language();
                    if (object2 == null || ((String)object2).isEmpty()) break block11;
                    object3.put(string, object2);
                    if (!object.containsKey(object2)) {
                        object.put(object2, string);
                    }
                } else {
                    for (String[] stringArray20 : d) {
                        object3.put(stringArray20[0], stringArray20[1]);
                        object.put(stringArray20[1], stringArray20[0]);
                    }
                    for (String[] stringArray21 : c) {
                        object2 = (String)object.get(stringArray21[1]);
                        if (object2 == null) continue;
                        object.put(stringArray21[0], object2);
                    }
                    object3.put("iw", "heb");
                    object3.put("he", "heb");
                    object3.put("in", "ind");
                    object3.put("id", "ind");
                    object3.put("ji", "yid");
                    object3.put("yi", "yid");
                    object.put("heb", "he");
                    object.put("ind", "id");
                    object.put("yid", "yi");
                    String[] stringArray22 = e;
                    n3 = stringArray22.length;
                    for (n5 = 0; n5 < n3; ++n5) {
                        object.put(stringArray22[n5], "zh");
                    }
                    object.put("zho", "zh");
                    object.put("yue", "yue");
                    String[] stringArray23 = f;
                    n3 = stringArray23.length;
                    n5 = n4;
                    while (true) {
                        if (n5 >= n3) {
                            a = Collections.unmodifiableMap(object3);
                            b = Collections.unmodifiableMap(object);
                            return;
                        }
                        String string = stringArray23[n5];
                        object.put(string, string);
                        object3.put(string, string);
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
        if ((string = c3.e.a(string)) == null) {
            return null;
        }
        String string2 = (String)b.get(string);
        if (string2 != null) {
            return string2;
        }
        if (string.length() == 2 && a.containsKey(string)) {
            return string;
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

