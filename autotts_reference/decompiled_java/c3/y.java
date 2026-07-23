/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.c;
import c3.m;
import c3.x;
import c3.z;
import com.vnspeak.autotts.AutoTtsService;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class y {
    public static final String a;
    public static final Pattern b;
    public static final Pattern c;
    public static final Pattern d;
    public static final Pattern e;

    static {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("[×÷°(₠-⃏)( -⁯)(\\p{InBasic_Latin})(\\p{InLATIN_1_SUPPLEMENT})(\\p{InLATIN_EXTENDED_A})(\\p{InLATIN_EXTENDED_ADDITIONAL})(\\p{InLATIN_EXTENDED_B})(\\p{InLATIN_EXTENDED_C})(\\p{InLATIN_EXTENDED_D})(!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)(");
        charSequence.append(c3.c.a());
        charSequence.append(")]+");
        charSequence = charSequence.toString();
        a = charSequence;
        b = Pattern.compile("[×÷°(₠-⃏)( -⁯)[0-9](!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)(\\p{Space})(+)(*)]+", 64);
        c = Pattern.compile("[( -⁯)(!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)(\\p{Space})]+", 64);
        d = Pattern.compile((String)charSequence, 64);
        e = Pattern.compile("[؜‎‏‪‫‬‭‮]", 64);
    }

    public static int a(ArrayList arrayList) {
        for (int i3 = 0; i3 < arrayList.size(); ++i3) {
            int n3 = ((z)arrayList.get(i3)).a();
            if (n3 == 3 || n3 == 4) continue;
            return ((z)arrayList.get(i3)).a();
        }
        return 0;
    }

    public static int b(ArrayList arrayList, int n3) {
        if (n3 != 0) {
            --n3;
            while (n3 >= 0) {
                int n4 = ((z)arrayList.get(n3)).a();
                if (n4 != 3 && n4 != 4) {
                    return ((z)arrayList.get(n3)).a();
                }
                --n3;
            }
        }
        return 0;
    }

    public static int c(String string) {
        if (x.a(string)) {
            return 0;
        }
        if (y.e(string)) {
            return 4;
        }
        if (y.d(string)) {
            return 3;
        }
        return 1;
    }

    public static boolean d(String string) {
        return b.matcher(string).matches();
    }

    public static boolean e(String string) {
        return c.matcher(string).matches();
    }

    public static String f(String string) {
        return e.matcher(string).replaceAll("");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ArrayList g(String serializable, int n3, int n4, int n5, int n6) {
        Object object;
        int n7;
        Object object2;
        block32: {
            int n8;
            block34: {
                block35: {
                    int n9;
                    if (n5 == -1) return new ArrayList();
                    if (n6 == -1) {
                        return new ArrayList();
                    }
                    if (serializable == null) {
                        return new ArrayList();
                    }
                    if (((String)((Object)(serializable = ((String)((Object)serializable)).trim()))).equals("")) {
                        return new ArrayList();
                    }
                    object2 = y.f(((String)((Object)serializable)).replaceAll("\\s+", " "));
                    serializable = m.f(Locale.getDefault());
                    n5 = AutoTtsService.O;
                    n8 = 1;
                    n6 = (n5 != 1 ? n5 == 4 && ((String)((Object)serializable)).equals(AutoTtsService.L) : ((String)((Object)serializable)).equals(AutoTtsService.G)) ? 2 : 1;
                    serializable = new ArrayList<z>();
                    Matcher matcher = d.matcher((CharSequence)object2);
                    n7 = 0;
                    n5 = 0;
                    while (matcher.find()) {
                        n9 = matcher.start();
                        if (n5 != n9) {
                            ((ArrayList)serializable).add(new z(((String)object2).substring(n5, n9), 2));
                        }
                        n5 = matcher.end();
                        object = ((String)object2).substring(n9, n5);
                        ((ArrayList)serializable).add(new z((String)object, y.c((String)object)));
                    }
                    if (n5 < ((String)object2).length()) {
                        ((ArrayList)serializable).add(new z(((String)object2).substring(n5), 2));
                    }
                    object2 = new ArrayList();
                    n5 = 0;
                    while (n5 < ((ArrayList)serializable).size()) {
                        object = new StringBuilder(((z)((ArrayList)serializable).get(n5)).c());
                        n9 = ((z)((ArrayList)serializable).get(n5)).a();
                        while (++n5 < ((ArrayList)serializable).size() && (((z)((ArrayList)serializable).get(n5)).a() == n9 || ((z)((ArrayList)serializable).get(n5)).a() == 0)) {
                            ((StringBuilder)object).append(((z)((ArrayList)serializable).get(n5)).c());
                        }
                        ((ArrayList)object2).add(new z(((StringBuilder)object).toString(), n9));
                    }
                    if (((ArrayList)object2).size() == 0) {
                        return object2;
                    }
                    if (((ArrayList)object2).size() == 1) {
                        n5 = ((z)((ArrayList)object2).get(0)).a();
                        if (n5 != 3) {
                            if (n5 != 4) {
                                return object2;
                            }
                            if (n4 != 0) {
                                ((z)((ArrayList)object2).get(0)).d(n4);
                                return object2;
                            }
                            ((z)((ArrayList)object2).get(0)).d(n6);
                            return object2;
                        }
                        if (n3 != 0) {
                            ((z)((ArrayList)object2).get(0)).d(n3);
                            return object2;
                        }
                        ((z)((ArrayList)object2).get(0)).d(n6);
                        return object2;
                    }
                    n5 = ((z)((ArrayList)object2).get(0)).a();
                    if (n5 == 3) break block34;
                    if (n5 == 4) break block35;
                    n5 = n8;
                    break block32;
                }
                if (n4 == 0) {
                    n5 = y.a((ArrayList)object2);
                    if (n5 != 0) {
                        ((z)((ArrayList)object2).get(0)).d(n5);
                        n5 = n8;
                        break block32;
                    } else {
                        ((z)((ArrayList)object2).get(0)).d(n6);
                        n5 = n8;
                    }
                    break block32;
                } else {
                    ((z)((ArrayList)object2).get(0)).d(n4);
                    n5 = n8;
                }
                break block32;
            }
            if (n3 == 0) {
                n5 = y.a((ArrayList)object2);
                if (n5 != 0) {
                    ((z)((ArrayList)object2).get(0)).d(n5);
                    n5 = n8;
                } else {
                    ((z)((ArrayList)object2).get(0)).d(n6);
                    n5 = n8;
                }
            } else {
                ((z)((ArrayList)object2).get(0)).d(n3);
                n5 = n8;
            }
        }
        while (n5 < ((ArrayList)object2).size()) {
            n6 = ((z)((ArrayList)object2).get(n5)).a();
            if (n6 != 3) {
                if (n6 == 4) {
                    if (n4 == 0) {
                        ((z)((ArrayList)object2).get(n5)).d(y.b((ArrayList)object2, n5));
                    } else {
                        ((z)((ArrayList)object2).get(n5)).d(n4);
                    }
                }
            } else if (n3 == 0) {
                ((z)((ArrayList)object2).get(n5)).d(y.b((ArrayList)object2, n5));
            } else {
                ((z)((ArrayList)object2).get(n5)).d(n3);
            }
            ++n5;
        }
        object = new ArrayList();
        n3 = n7;
        while (n3 < ((ArrayList)object2).size()) {
            serializable = new StringBuilder(((z)((ArrayList)object2).get(n3)).c());
            n4 = ((z)((ArrayList)object2).get(n3)).a();
            while (++n3 < ((ArrayList)object2).size() && ((z)((ArrayList)object2).get(n3)).a() == n4) {
                ((StringBuilder)serializable).append(((z)((ArrayList)object2).get(n3)).c());
            }
            ((ArrayList)object).add(new z(((StringBuilder)serializable).toString(), n4));
        }
        return object;
    }
}

