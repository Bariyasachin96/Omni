/*
 * Decompiled with CFR 0.152.
 */


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ScriptFam {
    public static final Map a;
    public static final Set b;
    public static final Set c;
    public static final Set d;
    public static final Set e;
    public static final Set f;
    public static final Set g;
    public static final Set h;
    public static final Set i;
    public static final Set j;
    public static final Set k;
    public static final a l;
    public static final a m;
    public static final a n;
    public static final a o;
    public static final a p;
    public static final a q;
    public static final a r;
    public static final a s;
    public static final a t;
    public static final a u;
    public static final a v;
    public static final Map w;


    // Android's libcore HashSet(Collection) is the classic OpenJDK 8..17 form:
    //     map = new HashMap<>(Math.max((int)(c.size()/.75f) + 1, 16));
    // JDK 19 replaced it with HashMap.newHashMap(c.size()), which gives a
    // SMALLER table for 12 elements (16 instead of 32) and therefore a
    // different iteration order. The harness runs on JDK 21, so the Android
    // form has to be restored explicitly or the comparison is against the
    // wrong runtime.
    static <T> HashSet<T> androidSet(java.util.List<T> c) {
        HashSet<T> s = new HashSet<T>(Math.max((int)(c.size() / .75f) + 1, 16));
        s.addAll(c);
        return s;
    }
    static {
        Set<String> set;
        Set<String> set2;
        Set<String> set3;
        Set<String> set4;
        Set<String> set5;
        Set<String> set6;
        Set<String> set7;
        Set<String> set8;
        a = new ConcurrentHashMap(1024);
        b = set8 = Collections.unmodifiableSet(androidSet(Arrays.asList("en", "es", "fr", "de", "it", "pt", "nl", "sv", "no", "da", "fi", "pl", "cs", "sk", "hu", "ro", "tr", "id", "ms", "vi", "tl", "sw", "hr", "sr", "sl", "et", "lv", "lt", "is", "ga", "cy", "sq", "mt")));
        c = set7 = Collections.unmodifiableSet(androidSet(Arrays.asList("ru", "uk", "be", "bg", "mk", "sr", "bs", "hr", "kk", "ky", "tg", "mn", "uz", "tt", "ba", "cv", "sah", "ce", "av", "ab")));
        d = Collections.unmodifiableSet(androidSet(Arrays.asList("ar", "fa", "ur", "ps", "sd", "ku", "ckb", "az", "ks", "ug", "ha")));
        e = set6 = Collections.unmodifiableSet(androidSet(Arrays.asList("ar")));
        f = set5 = Collections.unmodifiableSet(androidSet(Arrays.asList("fa", "ur", "ps", "ku", "ckb", "sd", "az", "ks", "ug")));
        g = set4 = Collections.unmodifiableSet(androidSet(Arrays.asList("ur", "ps", "sd", "pa")));
        h = set3 = Collections.unmodifiableSet(androidSet(Arrays.asList("dv")));
        i = set2 = Collections.unmodifiableSet(androidSet(Arrays.asList("hi", "mr", "ne", "sa", "kok", "mai", "bh", "new", "awa", "bho", "raj", "hne")));
        j = Collections.unmodifiableSet(androidSet(Arrays.asList("am", "ti", "om", "aa", "so", "sid", "wal", "gez")));
        k = set = Collections.unmodifiableSet(androidSet(Arrays.asList("zh", "ja", "ko", "vi")));
        l = new a("en", set8, "Latin");
        m = new a("ru", set7, "Cyrillic");
        n = new a("ar", set6, "Arabic");
        o = new a("fa", set5, "Arabic");
        p = new a("ur", set4, "Arabic");
        q = new a("dv", set3, "Thaana");
        r = new a("hi", set2, "Devanagari");
        s = new a("zh", set, "CJK");
        t = new a("zz", Collections.singleton("zz"), "Punctuation");
        u = new a("un", Collections.singleton("un"), "Unknown");
        v = new a("en", Collections.unmodifiableSet(androidSet(Arrays.asList("en", "es", "fr", "de", "it", "pt", "ru", "ja", "zh", "ko", "ar", "hi"))), "Digit");
        w = ScriptFam.a();
    }

    public static Map a() {
        HashMap<String, a> hashMap = new HashMap<String, a>();
        hashMap.put("fr", new a("fr", Collections.unmodifiableSet(androidSet(Arrays.asList("fr", "ca", "oc"))), "Latin"));
        hashMap.put("es", new a("es", Collections.unmodifiableSet(androidSet(Arrays.asList("es", "pt", "gl", "ca"))), "Latin"));
        hashMap.put("de", new a("de", Collections.unmodifiableSet(androidSet(Arrays.asList("de", "lb"))), "Latin"));
        hashMap.put("no", new a("no", Collections.unmodifiableSet(androidSet(Arrays.asList("no", "da", "sv", "is", "fo"))), "Latin"));
        hashMap.put("pl", new a("pl", Collections.singleton("pl"), "Latin"));
        hashMap.put("cs", new a("cs", Collections.unmodifiableSet(androidSet(Arrays.asList("cs", "sk"))), "Latin"));
        hashMap.put("vi", new a("vi", Collections.singleton("vi"), "Latin"));
        hashMap.put("uk", new a("uk", Collections.singleton("uk"), "Cyrillic"));
        hashMap.put("sr", new a("sr", Collections.unmodifiableSet(androidSet(Arrays.asList("sr", "bs", "hr"))), "Cyrillic"));
        hashMap.put("bg", new a("bg", Collections.unmodifiableSet(androidSet(Arrays.asList("bg", "mk"))), "Cyrillic"));
        hashMap.put("kk", new a("kk", Collections.unmodifiableSet(androidSet(Arrays.asList("kk", "ky"))), "Cyrillic"));
        hashMap.put("ru_sp", new a("ru", Collections.unmodifiableSet(androidSet(Arrays.asList("ru", "be"))), "Cyrillic"));
        hashMap.put("fa", new a("fa", Collections.unmodifiableSet(androidSet(Arrays.asList("fa", "tg"))), "Arabic"));
        hashMap.put("ur", new a("ur", Collections.unmodifiableSet(androidSet(Arrays.asList("ur", "pa"))), "Arabic"));
        hashMap.put("ps", new a("ps", Collections.singleton("ps"), "Arabic"));
        hashMap.put("sd", new a("sd", Collections.singleton("sd"), "Arabic"));
        hashMap.put("ku", new a("ku", Collections.unmodifiableSet(androidSet(Arrays.asList("ku", "ckb"))), "Arabic"));
        hashMap.put("mr", new a("mr", Collections.unmodifiableSet(androidSet(Arrays.asList("mr", "kok"))), "Devanagari"));
        hashMap.put("el", new a("el", Collections.unmodifiableSet(androidSet(Arrays.asList("el", "grc", "pnt"))), "Greek"));
        hashMap.put("bn", new a("bn", Collections.unmodifiableSet(androidSet(Arrays.asList("bn", "as", "mni"))), "Bengali"));
        hashMap.put("as", new a("as", Collections.singleton("as"), "Bengali"));
        hashMap.put("pa", new a("pa", Collections.singleton("pa"), "Gurmukhi"));
        hashMap.put("gu", new a("gu", Collections.singleton("gu"), "Gujarati"));
        hashMap.put("or", new a("or", Collections.singleton("or"), "Oriya"));
        hashMap.put("ta", new a("ta", Collections.unmodifiableSet(androidSet(Arrays.asList("ta", "kn"))), "Tamil"));
        hashMap.put("te", new a("te", Collections.singleton("te"), "Telugu"));
        hashMap.put("kn", new a("kn", Collections.unmodifiableSet(androidSet(Arrays.asList("kn", "tcy"))), "Kannada"));
        hashMap.put("ml", new a("ml", Collections.singleton("ml"), "Malayalam"));
        hashMap.put("si", new a("si", Collections.singleton("si"), "Sinhala"));
        hashMap.put("th", new a("th", Collections.unmodifiableSet(androidSet(Arrays.asList("th", "nod", "sou", "tts"))), "Thai"));
        hashMap.put("lo", new a("lo", Collections.unmodifiableSet(androidSet(Arrays.asList("lo", "hnx"))), "Lao"));
        hashMap.put("bo", new a("bo", Collections.unmodifiableSet(androidSet(Arrays.asList("bo", "dz"))), "Tibetan"));
        hashMap.put("my", new a("my", Collections.unmodifiableSet(androidSet(Arrays.asList("my", "shn", "kar", "mnw"))), "Myanmar"));
        hashMap.put("km", new a("km", Collections.singleton("km"), "Khmer"));
        hashMap.put("ka", new a("ka", Collections.unmodifiableSet(androidSet(Arrays.asList("ka", "xmf", "lzz"))), "Georgian"));
        hashMap.put("ko", new a("ko", Collections.singleton("ko"), "Hangul"));
        hashMap.put("am", new a("am", j, "Ethiopic"));
        hashMap.put("he", new a("he", Collections.unmodifiableSet(androidSet(Arrays.asList("he", "yi", "lad"))), "Hebrew"));
        hashMap.put("hy", new a("hy", Collections.singleton("hy"), "Armenian"));
        hashMap.put("mn", new a("mn", Collections.unmodifiableSet(androidSet(Arrays.asList("mn", "mnc", "xal"))), "Mongolian"));
        hashMap.put("ja_h", new a("ja", Collections.singleton("ja"), "Hiragana"));
        hashMap.put("ja_k", new a("ja", Collections.unmodifiableSet(androidSet(Arrays.asList("ja", "ain"))), "Katakana"));
        hashMap.put("emoji", new a("zz", Collections.singleton("zz"), "Emoji"));
        hashMap.put("math", new a("zz", Collections.singleton("zz"), "Mathematical"));
        hashMap.put("cjk_p", new a("zh", k, "CJK Punctuation"));
        hashMap.put("grc_l", new a("grc", Collections.singleton("grc"), "Linear B"));
        hashMap.put("la_i", new a("la", Collections.singleton("la"), "Old Italic"));
        hashMap.put("got", new a("got", Collections.singleton("got"), "Gothic"));
        hashMap.put("en_d", new a("en", Collections.singleton("en"), "Deseret"));
        hashMap.put("grc_c", new a("grc", Collections.singleton("grc"), "Cypriot"));
        hashMap.put("sa_k", new a("sa", Collections.singleton("sa"), "Kharoshthi"));
        hashMap.put("egy", new a("egy", Collections.singleton("egy"), "Egyptian Hieroglyphs"));
        hashMap.put("akk", new a("akk", Collections.singleton("akk"), "Cuneiform"));
        return Collections.unmodifiableMap(hashMap);
    }

    public static a b(int n3) {
        if (n3 >= 48 && n3 <= 57) {
            return v;
        }
        if (n3 >= 32 && n3 <= 47 || n3 >= 58 && n3 <= 64 || n3 >= 91 && n3 <= 96 || n3 >= 123 && n3 <= 126 || n3 >= 8192 && n3 <= 8303) {
            return t;
        }
        if (n3 != 192 && n3 != 194 && n3 != 199 && n3 != 200 && n3 != 201 && n3 != 202 && n3 != 203 && n3 != 206 && n3 != 207 && n3 != 212 && n3 != 217 && n3 != 219 && n3 != 224 && n3 != 226 && n3 != 231 && n3 != 232 && n3 != 233 && n3 != 234 && n3 != 235 && n3 != 238 && n3 != 239 && n3 != 244 && n3 != 249 && n3 != 251 && n3 != 338 && n3 != 339) {
            if (n3 != 193 && n3 != 205 && n3 != 209 && n3 != 211 && n3 != 218 && n3 != 225 && n3 != 237 && n3 != 241 && n3 != 243 && n3 != 250) {
                if (n3 != 196 && n3 != 214 && n3 != 220 && n3 != 228 && n3 != 246 && n3 != 252 && n3 != 223) {
                    if (n3 != 197 && n3 != 198 && n3 != 216 && n3 != 229 && n3 != 230 && n3 != 248) {
                        if (n3 != 260 && n3 != 261 && n3 != 262 && n3 != 263 && n3 != 280 && n3 != 281 && n3 != 321 && n3 != 322 && n3 != 323 && n3 != 324 && n3 != 346 && n3 != 347 && n3 != 377 && n3 != 378 && n3 != 379 && n3 != 380) {
                            if (n3 != 268 && n3 != 269 && n3 != 270 && n3 != 271 && n3 != 282 && n3 != 283 && n3 != 327 && n3 != 328 && n3 != 344 && n3 != 345 && n3 != 352 && n3 != 353 && n3 != 356 && n3 != 357 && n3 != 366 && n3 != 367 && n3 != 381 && n3 != 382) {
                                if ((n3 < 7840 || n3 > 7929) && n3 != 272 && n3 != 273 && n3 != 416 && n3 != 417 && n3 != 431 && n3 != 432) {
                                    if (n3 >= 65 && n3 <= 90 || n3 >= 97 && n3 <= 122) {
                                        return l;
                                    }
                                    if (n3 >= 192 && n3 <= 255 || n3 >= 256 && n3 <= 591) {
                                        return l;
                                    }
                                    if (n3 >= 880 && n3 <= 1023 || n3 >= 7936 && n3 <= 8191) {
                                        return (a)w.get("el");
                                    }
                                    if (n3 >= 1024 && n3 <= 1279 || n3 >= 1280 && n3 <= 1327) {
                                        if (n3 != 1028 && n3 != 1108 && n3 != 1030 && n3 != 1110 && n3 != 1031 && n3 != 1111 && n3 != 1168 && n3 != 1169) {
                                            if (n3 != 1026 && n3 != 1106 && n3 != 1032 && n3 != 1112 && n3 != 1033 && n3 != 1113 && n3 != 1034 && n3 != 1114 && n3 != 1035 && n3 != 1115 && n3 != 1039 && n3 != 1119) {
                                                if (n3 != 1066 && n3 != 1098) {
                                                    if (n3 != 1186 && n3 != 1187 && n3 != 1170 && n3 != 1171 && n3 != 1200 && n3 != 1201 && n3 != 1198 && n3 != 1199 && n3 != 1240 && n3 != 1241) {
                                                        if (n3 != 1025 && n3 != 1105 && n3 != 1069 && n3 != 1101 && n3 != 1067 && n3 != 1099) {
                                                            return m;
                                                        }
                                                        return (a)w.get("ru_sp");
                                                    }
                                                    return (a)w.get("kk");
                                                }
                                                return (a)w.get("bg");
                                            }
                                            return (a)w.get("sr");
                                        }
                                        return (a)w.get("uk");
                                    }
                                    if (n3 >= 1328 && n3 <= 1423 || n3 >= 64256 && n3 <= 64279) {
                                        return (a)w.get("hy");
                                    }
                                    if (n3 >= 1424 && n3 <= 1535 || n3 >= 64285 && n3 <= 64335) {
                                        return (a)w.get("he");
                                    }
                                    if (n3 >= 1536 && n3 <= 1791 || n3 >= 1872 && n3 <= 1919 || n3 >= 2208 && n3 <= 2303 || n3 >= 64336 && n3 <= 65023 || n3 >= 65136 && n3 <= 65279) {
                                        if (n3 != 1657 && n3 != 1672 && n3 != 1681 && n3 != 1729 && n3 != 1746) {
                                            if (n3 != 1665 && n3 != 1669 && n3 != 1673 && n3 != 1683 && n3 != 1686 && n3 != 1690 && n3 != 1724 && n3 != 1744) {
                                                if (n3 >= 1658 && n3 <= 1661) {
                                                    return (a)w.get("sd");
                                                }
                                                if (n3 != 1742 && n3 != 1685 && n3 != 1717 && n3 != 1734) {
                                                    if (n3 != 1722 && n3 != 1726) {
                                                        if (n3 != 1662 && n3 != 1670 && n3 != 1688 && n3 != 1711) {
                                                            return n;
                                                        }
                                                        return o;
                                                    }
                                                    return p;
                                                }
                                                return (a)w.get("ku");
                                            }
                                            return (a)w.get("ps");
                                        }
                                        return (a)w.get("ur");
                                    }
                                    if (n3 >= 1920 && n3 <= 1983) {
                                        return q;
                                    }
                                    if (n3 >= 2304 && n3 <= 2431 || n3 >= 43232 && n3 <= 43263) {
                                        if (n3 == 2355) {
                                            return (a)w.get("mr");
                                        }
                                        return r;
                                    }
                                    if (n3 >= 2432 && n3 <= 2559) {
                                        if (n3 != 2544 && n3 != 2545) {
                                            return (a)w.get("bn");
                                        }
                                        return (a)w.get("as");
                                    }
                                    if (n3 >= 2560 && n3 <= 2687) {
                                        return (a)w.get("pa");
                                    }
                                    if (n3 >= 2688 && n3 <= 2815) {
                                        return (a)w.get("gu");
                                    }
                                    if (n3 >= 2816 && n3 <= 2943) {
                                        return (a)w.get("or");
                                    }
                                    if (n3 >= 2944 && n3 <= 3071) {
                                        return (a)w.get("ta");
                                    }
                                    if (n3 >= 3072 && n3 <= 3199) {
                                        return (a)w.get("te");
                                    }
                                    if (n3 >= 3200 && n3 <= 3327) {
                                        return (a)w.get("kn");
                                    }
                                    if (n3 >= 3328 && n3 <= 3455) {
                                        return (a)w.get("ml");
                                    }
                                    if (n3 >= 3456 && n3 <= 3583) {
                                        return (a)w.get("si");
                                    }
                                    if (n3 >= 3584 && n3 <= 3711) {
                                        return (a)w.get("th");
                                    }
                                    if (n3 >= 3712 && n3 <= 3839) {
                                        return (a)w.get("lo");
                                    }
                                    if (n3 >= 3840 && n3 <= 4095) {
                                        return (a)w.get("bo");
                                    }
                                    if (n3 >= 4096 && n3 <= 4255 || n3 >= 43616 && n3 <= 43647) {
                                        return (a)w.get("my");
                                    }
                                    if (n3 >= 4256 && n3 <= 4351 || n3 >= 11520 && n3 <= 11567) {
                                        return (a)w.get("ka");
                                    }
                                    if (n3 >= 4352 && n3 <= 4607 || n3 >= 12592 && n3 <= 12687 || n3 >= 43360 && n3 <= 43391 || n3 >= 44032 && n3 <= 55215 || n3 >= 55216 && n3 <= 55295) {
                                        return (a)w.get("ko");
                                    }
                                    if (n3 >= 4608 && n3 <= 4991 || n3 >= 4992 && n3 <= 5023 || n3 >= 11648 && n3 <= 11743) {
                                        return (a)w.get("am");
                                    }
                                    if (n3 >= 6016 && n3 <= 6143 || n3 >= 6624 && n3 <= 6655) {
                                        return (a)w.get("km");
                                    }
                                    if (n3 >= 6144 && n3 <= 6319 || n3 >= 71264 && n3 <= 71295) {
                                        return (a)w.get("mn");
                                    }
                                    if (n3 >= 12288 && n3 <= 12351) {
                                        return (a)w.get("cjk_p");
                                    }
                                    if (n3 >= 12352 && n3 <= 12447 || n3 >= 110592 && n3 <= 110847 || n3 >= 110848 && n3 <= 110895) {
                                        return (a)w.get("ja_h");
                                    }
                                    if (n3 >= 12448 && n3 <= 12543 || n3 >= 12784 && n3 <= 12799 || n3 >= 110896 && n3 <= 110959) {
                                        return (a)w.get("ja_k");
                                    }
                                    if (n3 >= 19968 && n3 <= 40959 || n3 >= 13312 && n3 <= 19903 || n3 >= 131072 && n3 <= 173791 || n3 >= 173824 && n3 <= 177983 || n3 >= 177984 && n3 <= 178207 || n3 >= 178208 && n3 <= 183983 || n3 >= 183984 && n3 <= 191471 || n3 >= 196608 && n3 <= 201551 || n3 >= 201552 && n3 <= 205743) {
                                        return s;
                                    }
                                    if (n3 >= 126976 && n3 <= 127023 || n3 >= 127744 && n3 <= 129535 || n3 >= 129648 && n3 <= 129791 || n3 >= 9728 && n3 <= 9983 || n3 >= 9984 && n3 <= 10175) {
                                        return (a)w.get("emoji");
                                    }
                                    if (n3 >= 119808 && n3 <= 120831 || n3 >= 8448 && n3 <= 8527 || n3 >= 8704 && n3 <= 8959) {
                                        return (a)w.get("math");
                                    }
                                    if (n3 >= 65536 && n3 <= 65663) {
                                        return (a)w.get("grc_l");
                                    }
                                    if (n3 >= 66304 && n3 <= 66351) {
                                        return (a)w.get("la_i");
                                    }
                                    if (n3 >= 66352 && n3 <= 66383) {
                                        return (a)w.get("got");
                                    }
                                    if (n3 >= 66560 && n3 <= 66639) {
                                        return (a)w.get("en_d");
                                    }
                                    if (n3 >= 67584 && n3 <= 67647) {
                                        return (a)w.get("grc_c");
                                    }
                                    if (n3 >= 68096 && n3 <= 68191) {
                                        return (a)w.get("sa_k");
                                    }
                                    if (n3 >= 77824 && n3 <= 78895 || n3 >= 78896 && n3 <= 78943) {
                                        return (a)w.get("egy");
                                    }
                                    if (n3 >= 73728 && n3 <= 74751 || n3 >= 74752 && n3 <= 74879) {
                                        return (a)w.get("akk");
                                    }
                                    return u;
                                }
                                return (a)w.get("vi");
                            }
                            return (a)w.get("cs");
                        }
                        return (a)w.get("pl");
                    }
                    return (a)w.get("no");
                }
                return (a)w.get("de");
            }
            return (a)w.get("es");
        }
        return (a)w.get("fr");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static a c(int n3, Set set) {
        boolean bl = ScriptFam.g(set, b);
        boolean bl2 = ScriptFam.g(set, c);
        boolean bl3 = ScriptFam.g(set, d);
        boolean bl4 = set.contains("dv");
        boolean bl5 = ScriptFam.g(set, i);
        boolean bl6 = ScriptFam.g(set, k);
        boolean bl7 = set.contains("el") || set.contains("grc") || set.contains("pnt");
        boolean bl8 = set.contains("bn") || set.contains("as") || set.contains("mni");
        boolean bl9 = set.contains("th") || set.contains("nod") || set.contains("sou") || set.contains("tts");
        boolean bl10 = set.contains("ko");
        boolean bl11 = set.contains("ja") || set.contains("ain");
        boolean bl12 = set.contains("he") || set.contains("yi") || set.contains("lad");
        boolean bl13 = set.contains("hy");
        boolean bl14 = set.contains("ka") || set.contains("xmf") || set.contains("lzz");
        boolean bl15 = ScriptFam.g(set, j);
        boolean bl16 = set.contains("pa") || set.contains("gu") || set.contains("or") || set.contains("ta") || set.contains("te") || set.contains("kn") || set.contains("ml") || set.contains("si") || set.contains("tcy");
        boolean bl17 = set.contains("lo") || set.contains("hnx") || set.contains("bo") || set.contains("dz") || set.contains("my") || set.contains("shn") || set.contains("kar") || set.contains("mnw") || set.contains("km");
        boolean bl18 = set.contains("mn") || set.contains("mnc") || set.contains("xal");
        if (n3 >= 48 && n3 <= 57) {
            return ScriptFam.f(v, set);
        }
        if (n3 >= 32 && n3 <= 47 || n3 >= 58 && n3 <= 64 || n3 >= 91 && n3 <= 96 || n3 >= 123 && n3 <= 126 || n3 >= 8192 && n3 <= 8303) {
            return t;
        }
        if (bl) {
            if (n3 == 192 || n3 == 194 || n3 == 199 || n3 == 200 || n3 == 201 || n3 == 202 || n3 == 203 || n3 == 206 || n3 == 207 || n3 == 212 || n3 == 217 || n3 == 219 || n3 == 224 || n3 == 226 || n3 == 231 || n3 == 232 || n3 == 233 || n3 == 234 || n3 == 235 || n3 == 238 || n3 == 239 || n3 == 244 || n3 == 249 || n3 == 251 || n3 == 338 || n3 == 339) return ScriptFam.f((a)w.get("fr"), set);
            if (n3 == 193 || n3 == 205 || n3 == 209 || n3 == 211 || n3 == 218 || n3 == 225 || n3 == 237 || n3 == 241 || n3 == 243 || n3 == 250) return ScriptFam.f((a)w.get("es"), set);
            if (n3 == 196 || n3 == 214 || n3 == 220 || n3 == 228 || n3 == 246 || n3 == 252 || n3 == 223) return ScriptFam.f((a)w.get("de"), set);
            if (n3 == 197 || n3 == 198 || n3 == 216 || n3 == 229 || n3 == 230 || n3 == 248) return ScriptFam.f((a)w.get("no"), set);
            if (n3 == 260 || n3 == 261 || n3 == 262 || n3 == 263 || n3 == 280 || n3 == 281 || n3 == 321 || n3 == 322 || n3 == 323 || n3 == 324 || n3 == 346 || n3 == 347 || n3 == 377 || n3 == 378 || n3 == 379 || n3 == 380) return ScriptFam.f((a)w.get("pl"), set);
            if (n3 == 268 || n3 == 269 || n3 == 270 || n3 == 271 || n3 == 282 || n3 == 283 || n3 == 327 || n3 == 328 || n3 == 344 || n3 == 345 || n3 == 352 || n3 == 353 || n3 == 356 || n3 == 357 || n3 == 366 || n3 == 367 || n3 == 381 || n3 == 382) return ScriptFam.f((a)w.get("cs"), set);
            if (n3 >= 7840 && n3 <= 7929 || n3 == 272 || n3 == 273 || n3 == 416 || n3 == 417 || n3 == 431 || n3 == 432) return ScriptFam.f((a)w.get("vi"), set);
            if (n3 >= 65 && n3 <= 90 || n3 >= 97 && n3 <= 122) {
                return ScriptFam.f(l, set);
            }
            if (n3 >= 192 && n3 <= 255 || n3 >= 256 && n3 <= 591) {
                return ScriptFam.f(l, set);
            }
        }
        if (bl7 && (n3 >= 880 && n3 <= 1023 || n3 >= 7936 && n3 <= 8191)) {
            return ScriptFam.f((a)w.get("el"), set);
        }
        if (bl2 && (n3 >= 1024 && n3 <= 1279 || n3 >= 1280 && n3 <= 1327)) {
            if (n3 == 1028 || n3 == 1108 || n3 == 1030 || n3 == 1110 || n3 == 1031 || n3 == 1111 || n3 == 1168 || n3 == 1169) return ScriptFam.f((a)w.get("uk"), set);
            if (n3 == 1026 || n3 == 1106 || n3 == 1032 || n3 == 1112 || n3 == 1033 || n3 == 1113 || n3 == 1034 || n3 == 1114 || n3 == 1035 || n3 == 1115 || n3 == 1039 || n3 == 1119) return ScriptFam.f((a)w.get("sr"), set);
            if (n3 == 1066 || n3 == 1098) return ScriptFam.f((a)w.get("bg"), set);
            if (n3 == 1186 || n3 == 1187 || n3 == 1170 || n3 == 1171 || n3 == 1200 || n3 == 1201 || n3 == 1198 || n3 == 1199 || n3 == 1240 || n3 == 1241) return ScriptFam.f((a)w.get("kk"), set);
            if (n3 == 1025 || n3 == 1105 || n3 == 1069 || n3 == 1101 || n3 == 1067 || n3 == 1099) return ScriptFam.f((a)w.get("ru_sp"), set);
            return ScriptFam.f(m, set);
        }
        if (bl13 && (n3 >= 1328 && n3 <= 1423 || n3 >= 64256 && n3 <= 64279)) {
            return ScriptFam.f((a)w.get("hy"), set);
        }
        if (bl12 && (n3 >= 1424 && n3 <= 1535 || n3 >= 64285 && n3 <= 64335)) {
            return ScriptFam.f((a)w.get("he"), set);
        }
        if (bl3 && (n3 >= 1536 && n3 <= 1791 || n3 >= 1872 && n3 <= 1919 || n3 >= 2208 && n3 <= 2303 || n3 >= 64336 && n3 <= 65023 || n3 >= 65136 && n3 <= 65279)) {
            if (n3 == 1657 || n3 == 1672 || n3 == 1681 || n3 == 1729 || n3 == 1746) return ScriptFam.f((a)w.get("ur"), set);
            if (n3 == 1665 || n3 == 1669 || n3 == 1673 || n3 == 1683 || n3 == 1686 || n3 == 1690 || n3 == 1724 || n3 == 1744) return ScriptFam.f((a)w.get("ps"), set);
            if (n3 >= 1658 && n3 <= 1661) {
                return ScriptFam.f((a)w.get("sd"), set);
            }
            if (n3 == 1742 || n3 == 1685 || n3 == 1717 || n3 == 1734) return ScriptFam.f((a)w.get("ku"), set);
            if (n3 == 1722 || n3 == 1726) return ScriptFam.f(p, set);
            if (n3 == 1662 || n3 == 1670 || n3 == 1688 || n3 == 1711) return ScriptFam.f(o, set);
            return ScriptFam.f(n, set);
        }
        if (bl4 && n3 >= 1920 && n3 <= 1983) {
            return q;
        }
        if (bl5 && (n3 >= 2304 && n3 <= 2431 || n3 >= 43232 && n3 <= 43263)) {
            if (n3 != 2355) return ScriptFam.f(r, set);
            return ScriptFam.f((a)w.get("mr"), set);
        }
        if (bl8 && n3 >= 2432 && n3 <= 2559) {
            if (n3 == 2544 || n3 == 2545) return ScriptFam.f((a)w.get("as"), set);
            return ScriptFam.f((a)w.get("bn"), set);
        }
        if (bl16) {
            if (n3 >= 2560 && n3 <= 2687) {
                return ScriptFam.f((a)w.get("pa"), set);
            }
            if (n3 >= 2688 && n3 <= 2815) {
                return ScriptFam.f((a)w.get("gu"), set);
            }
            if (n3 >= 2816 && n3 <= 2943) {
                return ScriptFam.f((a)w.get("or"), set);
            }
            if (n3 >= 2944 && n3 <= 3071) {
                return ScriptFam.f((a)w.get("ta"), set);
            }
            if (n3 >= 3072 && n3 <= 3199) {
                return ScriptFam.f((a)w.get("te"), set);
            }
            if (n3 >= 3200 && n3 <= 3327) {
                return ScriptFam.f((a)w.get("kn"), set);
            }
            if (n3 >= 3328 && n3 <= 3455) {
                return ScriptFam.f((a)w.get("ml"), set);
            }
            if (n3 >= 3456 && n3 <= 3583) {
                return ScriptFam.f((a)w.get("si"), set);
            }
        }
        if (bl9 && n3 >= 3584 && n3 <= 3711) {
            return ScriptFam.f((a)w.get("th"), set);
        }
        if (bl17) {
            if (n3 >= 3712 && n3 <= 3839) {
                return ScriptFam.f((a)w.get("lo"), set);
            }
            if (n3 >= 3840 && n3 <= 4095) {
                return ScriptFam.f((a)w.get("bo"), set);
            }
            if (n3 >= 4096 && n3 <= 4255 || n3 >= 43616 && n3 <= 43647) {
                return ScriptFam.f((a)w.get("my"), set);
            }
            if (n3 >= 6016 && n3 <= 6143 || n3 >= 6624 && n3 <= 6655) {
                return ScriptFam.f((a)w.get("km"), set);
            }
        }
        if (bl14 && (n3 >= 4256 && n3 <= 4351 || n3 >= 11520 && n3 <= 11567)) {
            return ScriptFam.f((a)w.get("ka"), set);
        }
        if (bl10 && (n3 >= 4352 && n3 <= 4607 || n3 >= 12592 && n3 <= 12687 || n3 >= 43360 && n3 <= 43391 || n3 >= 44032 && n3 <= 55215 || n3 >= 55216 && n3 <= 55295)) {
            return ScriptFam.f((a)w.get("ko"), set);
        }
        if (bl15 && (n3 >= 4608 && n3 <= 4991 || n3 >= 4992 && n3 <= 5023 || n3 >= 11648 && n3 <= 11743)) {
            return ScriptFam.f((a)w.get("am"), set);
        }
        if (bl18 && (n3 >= 6144 && n3 <= 6319 || n3 >= 71264 && n3 <= 71295)) {
            return ScriptFam.f((a)w.get("mn"), set);
        }
        if (bl6 && n3 >= 12288 && n3 <= 12351) {
            return ScriptFam.f((a)w.get("cjk_p"), set);
        }
        if (bl11) {
            if (n3 >= 12352 && n3 <= 12447 || n3 >= 110592 && n3 <= 110847 || n3 >= 110848 && n3 <= 110895) {
                return ScriptFam.f((a)w.get("ja_h"), set);
            }
            if (n3 >= 12448 && n3 <= 12543 || n3 >= 12784 && n3 <= 12799 || n3 >= 110896 && n3 <= 110959) {
                return ScriptFam.f((a)w.get("ja_k"), set);
            }
        }
        if (bl6 && (n3 >= 19968 && n3 <= 40959 || n3 >= 13312 && n3 <= 19903 || n3 >= 131072 && n3 <= 173791 || n3 >= 173824 && n3 <= 177983 || n3 >= 177984 && n3 <= 178207 || n3 >= 178208 && n3 <= 183983 || n3 >= 183984 && n3 <= 191471 || n3 >= 196608 && n3 <= 201551 || n3 >= 201552 && n3 <= 205743)) {
            return ScriptFam.f(s, set);
        }
        if (n3 >= 126976 && n3 <= 127023 || n3 >= 127744 && n3 <= 129535 || n3 >= 129648 && n3 <= 129791 || n3 >= 9728 && n3 <= 9983 || n3 >= 9984 && n3 <= 10175) {
            return (a)w.get("emoji");
        }
        if (!(n3 >= 119808 && n3 <= 120831 || n3 >= 8448 && n3 <= 8527) && (n3 < 8704 || n3 > 8959)) return u;
        return (a)w.get("math");
    }

    public static a d(int n3) {
        Map map = a;
        a a4 = (a)map.get(n3);
        if (a4 != null) {
            return a4;
        }
        a4 = ScriptFam.b(n3);
        if (map.size() < 5000) {
            map.put(n3, a4);
        }
        return a4;
    }

    public static a e(int n3, Set set) {
        if (set != null && !set.isEmpty()) {
            return ScriptFam.c(n3, set);
        }
        return ScriptFam.d(n3);
    }

    public static a f(a a4, Set object) {
        Set members = a4.a();
        String primary = a4.b();
        boolean bl = object.contains(primary);
        boolean allIn = true;
        Iterator scan = members.iterator();
        while (scan.hasNext()) {
            if (object.contains(scan.next())) continue;
            allIn = false;
            break;
        }
        if (allIn && bl) {
            return a4;
        }
        HashSet kept = new HashSet();
        Iterator iterator = members.iterator();
        while (iterator.hasNext()) {
            Object one = iterator.next();
            if (!object.contains(one)) continue;
            kept.add(one);
        }
        if (kept.isEmpty()) {
            return u;
        }
        String newPrimary = bl ? primary : (String)kept.iterator().next();
        return new a(newPrimary, Collections.unmodifiableSet(kept), a4.c());
    }

    public static boolean g(Set set, Set object) {
        Iterator it = object.iterator();
        while (it.hasNext()) {
            if (!set.contains((String)it.next())) continue;
            return true;
        }
        return false;
    }

    public static final class a {
        public final String a;
        public final Set b;
        public final String c;

        public a(String string, Set set, String string2) {
            this.a = string;
            this.b = set;
            this.c = string2;
        }

        public Set a() {
            return this.b;
        }

        public String b() {
            return this.a;
        }

        public String c() {
            return this.c;
        }

        public String toString() {
            return String.format("Primary: %s, Possible: %s, Script: %s", this.a, this.b, this.c);
        }
    }
}

