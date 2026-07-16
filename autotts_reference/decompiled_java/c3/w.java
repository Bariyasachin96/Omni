/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.c;
import c3.k;
import c3.v;
import c3.x;
import com.vnspeak.autotts.AutoTtsService;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class w {
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
            int n3 = ((x)arrayList.get(i3)).a();
            if (n3 == 3 || n3 == 4) continue;
            return ((x)arrayList.get(i3)).a();
        }
        return 0;
    }

    public static int b(ArrayList arrayList, int n3) {
        if (n3 != 0) {
            --n3;
            while (n3 >= 0) {
                int n4 = ((x)arrayList.get(n3)).a();
                if (n4 != 3 && n4 != 4) {
                    return ((x)arrayList.get(n3)).a();
                }
                --n3;
            }
        }
        return 0;
    }

    public static int c(String string) {
        if (v.a(string)) {
            return 0;
        }
        if (w.e(string)) {
            return 4;
        }
        if (w.d(string)) {
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
    public static ArrayList g(String arrayList, int n3, int n4, int n5, int n6) {
        CharSequence charSequence;
        int n7;
        Object object;
        block32: {
            int n8;
            block34: {
                block35: {
                    int n9;
                    if (n5 == -1) return new ArrayList();
                    if (n6 == -1) {
                        return new ArrayList();
                    }
                    if (arrayList == null) {
                        return new ArrayList();
                    }
                    if (((String)((Object)(arrayList = ((String)((Object)arrayList)).trim()))).equals("")) {
                        return new ArrayList();
                    }
                    object = w.f(((String)((Object)arrayList)).replaceAll("\\s+", " "));
                    arrayList = k.f(Locale.getDefault());
                    n5 = AutoTtsService.L;
                    n8 = 1;
                    n6 = (n5 != 1 ? n5 == 4 && ((String)((Object)arrayList)).equals(AutoTtsService.I) : ((String)((Object)arrayList)).equals(AutoTtsService.D)) ? 2 : 1;
                    arrayList = new ArrayList<x>();
                    Matcher matcher = d.matcher((CharSequence)object);
                    n7 = 0;
                    n5 = 0;
                    while (matcher.find()) {
                        n9 = matcher.start();
                        if (n5 != n9) {
                            arrayList.add(new x(((String)object).substring(n5, n9), 2));
                        }
                        n5 = matcher.end();
                        charSequence = ((String)object).substring(n9, n5);
                        arrayList.add(new x((String)charSequence, w.c((String)charSequence)));
                    }
                    if (n5 < ((String)object).length()) {
                        arrayList.add(new x(((String)object).substring(n5), 2));
                    }
                    object = new ArrayList();
                    n5 = 0;
                    while (n5 < arrayList.size()) {
                        charSequence = new StringBuilder(((x)arrayList.get(n5)).c());
                        n9 = ((x)arrayList.get(n5)).a();
                        while (++n5 < arrayList.size() && (((x)arrayList.get(n5)).a() == n9 || ((x)arrayList.get(n5)).a() == 0)) {
                            ((StringBuilder)charSequence).append(((x)arrayList.get(n5)).c());
                        }
                        ((ArrayList)object).add(new x(((StringBuilder)charSequence).toString(), n9));
                    }
                    if (((ArrayList)object).size() == 0) {
                        return object;
                    }
                    if (((ArrayList)object).size() == 1) {
                        n5 = ((x)((ArrayList)object).get(0)).a();
                        if (n5 != 3) {
                            if (n5 != 4) {
                                return object;
                            }
                            if (n4 != 0) {
                                ((x)((ArrayList)object).get(0)).d(n4);
                                return object;
                            }
                            ((x)((ArrayList)object).get(0)).d(n6);
                            return object;
                        }
                        if (n3 != 0) {
                            ((x)((ArrayList)object).get(0)).d(n3);
                            return object;
                        }
                        ((x)((ArrayList)object).get(0)).d(n6);
                        return object;
                    }
                    n5 = ((x)((ArrayList)object).get(0)).a();
                    if (n5 == 3) break block34;
                    if (n5 == 4) break block35;
                    n5 = n8;
                    break block32;
                }
                if (n4 == 0) {
                    n5 = w.a((ArrayList)object);
                    if (n5 != 0) {
                        ((x)((ArrayList)object).get(0)).d(n5);
                        n5 = n8;
                        break block32;
                    } else {
                        ((x)((ArrayList)object).get(0)).d(n6);
                        n5 = n8;
                    }
                    break block32;
                } else {
                    ((x)((ArrayList)object).get(0)).d(n4);
                    n5 = n8;
                }
                break block32;
            }
            if (n3 == 0) {
                n5 = w.a((ArrayList)object);
                if (n5 != 0) {
                    ((x)((ArrayList)object).get(0)).d(n5);
                    n5 = n8;
                } else {
                    ((x)((ArrayList)object).get(0)).d(n6);
                    n5 = n8;
                }
            } else {
                ((x)((ArrayList)object).get(0)).d(n3);
                n5 = n8;
            }
        }
        while (n5 < ((ArrayList)object).size()) {
            n6 = ((x)((ArrayList)object).get(n5)).a();
            if (n6 != 3) {
                if (n6 == 4) {
                    if (n4 == 0) {
                        ((x)((ArrayList)object).get(n5)).d(w.b((ArrayList)object, n5));
                    } else {
                        ((x)((ArrayList)object).get(n5)).d(n4);
                    }
                }
            } else if (n3 == 0) {
                ((x)((ArrayList)object).get(n5)).d(w.b((ArrayList)object, n5));
            } else {
                ((x)((ArrayList)object).get(n5)).d(n3);
            }
            ++n5;
        }
        arrayList = new ArrayList();
        n3 = n7;
        while (n3 < ((ArrayList)object).size()) {
            charSequence = new StringBuilder(((x)((ArrayList)object).get(n3)).c());
            n4 = ((x)((ArrayList)object).get(n3)).a();
            while (++n3 < ((ArrayList)object).size() && ((x)((ArrayList)object).get(n3)).a() == n4) {
                ((StringBuilder)charSequence).append(((x)((ArrayList)object).get(n3)).c());
            }
            arrayList.add(new x(((StringBuilder)charSequence).toString(), n4));
        }
        return arrayList;
    }
}

