/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.c;
import c3.c0;
import c3.e0;
import c3.n;
import com.vnspeak.autotts.AutoTtsService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class d0 {
    public static final String a;
    public static final Pattern b;
    public static final Pattern c;
    public static final Pattern d;
    public static final Pattern e;
    public static final Pattern f;
    public static final Pattern g;
    public static final String[][] h;
    public static volatile HashSet i;

    static {
        String[] stringArray = new StringBuilder();
        stringArray.append("(?:");
        stringArray.append(c3.c.a());
        stringArray.append("|[‍️⃣])+");
        stringArray = stringArray.toString();
        a = stringArray;
        b = Pattern.compile("[×÷°₠-⃏ -⁯\\p{Punct}\\p{Space}]*[0-9][0-9×÷°₠-⃏ -⁯\\p{Punct}\\p{Space}]*", 64);
        c = Pattern.compile("[( -⁯)(!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)(\\p{Space})]+", 64);
        d = Pattern.compile("[×÷°(₠-⃏)( -⁯)(\\p{InBasic_Latin})(\\p{InLATIN_1_SUPPLEMENT})(\\p{InLATIN_EXTENDED_A})(\\p{InLATIN_EXTENDED_ADDITIONAL})(\\p{InLATIN_EXTENDED_B})(\\p{InLATIN_EXTENDED_C})(\\p{InLATIN_EXTENDED_D})(!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)]+", 64);
        e = Pattern.compile((String)stringArray, 64);
        f = Pattern.compile("(?<![\\p{L}0-9])[+\\-]?[0-9×÷°₠-⃏](?:[0-9×÷°₠-⃏\\p{Space}!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~]*[0-9×÷°₠-⃏])?+(?![\\p{L}0-9])", 64);
        g = Pattern.compile("[؜‎‏‪‫‬‭‮]", 64);
        String[] stringArray2 = new String[]{"*", "otp", "pin", "sms", "imei", "cvv", "tel", "fax", "hotline", "sim", "whatsapp", "zalo", "viber", "telegram"};
        String[] stringArray3 = new String[]{"en", "phone", "mobile", "cell", "call", "dial", "code", "extension", "ext", "passcode", "verification", "account", "contact"};
        String[] stringArray4 = new String[]{"vi", "điện thoại", "sđt", "gọi", "di động", "mã", "liên hệ", "tổng đài", "tài khoản", "cccd", "cmnd"};
        String[] stringArray5 = new String[]{"hi", "फ़ोन", "फोन", "मोबाइल", "कॉल", "कोड", "खाता", "संपर्क", "डायल"};
        String[] stringArray6 = new String[]{"ur", "فون", "موبائل", "کال", "نمبر", "کوڈ", "رابطہ", "اکاؤنٹ"};
        String[] stringArray7 = new String[]{"ar", "هاتف", "جوال", "اتصل", "اتصال", "رقم", "رمز", "كود", "حساب", "موبايل"};
        String[] stringArray8 = new String[]{"ru", "телефон", "моб", "мобильный", "звони", "позвони", "код", "номер", "счёт", "счет", "добавочный"};
        String[] stringArray9 = new String[]{"uk", "телефон", "моб", "дзвони", "зателефонуй", "код", "номер", "рахунок"};
        String[] stringArray10 = new String[]{"fr", "téléphone", "tél", "portable", "appel", "appelez", "code", "poste", "compte", "mobile", "numéro"};
        String[] stringArray11 = new String[]{"es", "teléfono", "móvil", "celular", "llama", "llamar", "código", "cuenta", "extensión", "número"};
        String[] stringArray12 = new String[]{"pt", "telefone", "celular", "telemóvel", "ligar", "ligue", "código", "conta", "ramal", "número"};
        String[] stringArray13 = new String[]{"it", "telefono", "cellulare", "chiama", "chiamare", "codice", "conto", "interno", "numero"};
        String[] stringArray14 = new String[]{"in", "telepon", "ponsel", "hp", "hubungi", "kode", "rekening", "nomor", "seluler"};
        String[] stringArray15 = new String[]{"id", "telepon", "ponsel", "hp", "hubungi", "kode", "rekening", "nomor", "seluler"};
        String[] stringArray16 = new String[]{"ms", "telefon", "bimbit", "hubungi", "kod", "akaun", "nombor", "talian"};
        String[] stringArray17 = new String[]{"jv", "telpon", "telpun", "nomer", "kode", "hubungi"};
        String[] stringArray18 = new String[]{"th", "โทรศัพท์", "โทร", "มือถือ", "รหัส", "เบอร์", "บัญชี", "ติดต่อ", "สายด่วน"};
        String[] stringArray19 = new String[]{"pl", "telefon", "komórka", "zadzwoń", "kod", "numer", "konto", "wewnętrzny"};
        String[] stringArray20 = new String[]{"af", "telefoon", "foon", "selfoon", "skakel", "kode", "rekening", "nommer"};
        String[] stringArray21 = new String[]{"ja", "電話", "携帯", "コード", "番号", "内線", "口座", "連絡", "ダイヤル"};
        String[] stringArray22 = new String[]{"ko", "전화", "휴대폰", "핸드폰", "코드", "번호", "내선", "계좌", "연락", "문자"};
        String[] stringArray23 = new String[]{"zh", "电话", "電話", "手机", "手機", "号码", "號碼", "代码", "代碼", "验证码", "驗證碼", "分机", "分機", "账号", "帳號", "拨打", "撥打", "热线", "熱線"};
        String[] stringArray24 = new String[]{"sv", "telefon", "ring", "mobil", "kod", "nummer", "konto", "anknytning"};
        String[] stringArray25 = new String[]{"da", "telefon", "ring", "mobil", "kode", "nummer", "konto"};
        String[] stringArray26 = new String[]{"no", "telefon", "ring", "mobil", "kode", "nummer", "konto"};
        String[] stringArray27 = new String[]{"fi", "puhelin", "soita", "kännykkä", "koodi", "numero", "tili"};
        String[] stringArray28 = new String[]{"cs", "telefon", "zavolej", "mobil", "kód", "číslo", "účet", "linka"};
        String[] stringArray29 = new String[]{"hu", "telefon", "hívj", "mobil", "kód", "szám", "számla", "mellék"};
        String[] stringArray30 = new String[]{"ro", "telefon", "sună", "mobil", "cod", "număr", "cont", "interior"};
        String[] stringArray31 = new String[]{"el", "τηλέφωνο", "τηλ", "κινητό", "κωδικός", "αριθμός", "λογαριασμός"};
        String[] stringArray32 = new String[]{"he", "טלפון", "נייד", "חייג", "קוד", "מספר", "חשבון", "שלוחה"};
        stringArray = new String[]{"bn", "ফোন", "মোবাইল", "কল", "নম্বর", "কোড", "হিসাব", "যোগাযোগ"};
        String[] stringArray33 = new String[]{"ta", "தொலைபேசி", "கைபேசி", "அழை", "எண்", "குறியீடு", "கணக்கு"};
        String[] stringArray34 = new String[]{"te", "ఫోన్", "మొబైల్", "కాల్", "నంబర్", "కోడ్", "ఖాతా"};
        String[] stringArray35 = new String[]{"kn", "ಫೋನ್", "ಮೊಬೈಲ್", "ಕರೆ", "ಸಂಖ್ಯೆ", "ಕೋಡ್", "ಖಾತೆ"};
        String[] stringArray36 = new String[]{"mr", "फोन", "मोबाईल", "कॉल", "क्रमांक", "कोड", "खाते", "संपर्क"};
        String[] stringArray37 = new String[]{"gu", "ફોન", "મોબાઇલ", "કૉલ", "નંબર", "કોડ", "ખાતું"};
        String[] stringArray38 = new String[]{"pa", "ਫੋਨ", "ਮੋਬਾਈਲ", "ਕਾਲ", "ਨੰਬਰ", "ਕੋਡ", "ਖਾਤਾ"};
        String[] stringArray39 = new String[]{"si", "දුරකථන", "ජංගම", "අමතන්න", "අංකය", "කේතය", "ගිණුම"};
        String[] stringArray40 = new String[]{"lo", "ໂທລະສັບ", "ເບີ", "ລະຫັດ", "ບັນຊີ", "ຕິດຕໍ່"};
        String[] stringArray41 = new String[]{"sw", "simu", "piga", "namba", "nambari", "msimbo", "akaunti"};
        h = new String[][]{stringArray2, stringArray3, stringArray4, stringArray5, stringArray6, {"fa", "تلفن", "موبایل", "تماس", "شماره", "کد", "همراه", "حساب"}, stringArray7, stringArray8, stringArray9, {"de", "telefon", "handy", "anruf", "anrufen", "durchwahl", "code", "konto", "mobil", "rufnummer", "vorwahl"}, stringArray10, stringArray11, stringArray12, stringArray13, {"tr", "telefon", "cep", "ara", "arayın", "kod", "dahili", "hesap", "numara"}, stringArray14, stringArray15, stringArray16, stringArray17, stringArray18, stringArray19, {"ne", "फोन", "मोबाइल", "कल", "नम्बर", "कोड", "खाता", "सम्पर्क"}, stringArray20, stringArray21, stringArray22, stringArray23, {"nl", "telefoon", "bel", "bellen", "code", "mobiel", "nummer", "rekening", "toestel"}, stringArray24, stringArray25, stringArray26, stringArray27, stringArray28, {"sk", "telefón", "zavolaj", "mobil", "kód", "číslo", "účet", "linka"}, stringArray29, stringArray30, {"bg", "телефон", "обади", "мобилен", "код", "номер", "сметка"}, stringArray31, {"iw", "טלפון", "נייד", "חייג", "קוד", "מספר", "חשבון", "שלוחה"}, stringArray32, stringArray, stringArray33, stringArray34, stringArray35, {"ml", "ഫോൺ", "മൊബൈൽ", "വിളിക്കുക", "നമ്പർ", "കോഡ്", "അക്കൗണ്ട്"}, stringArray36, stringArray37, stringArray38, stringArray39, {"km", "ទូរស័ព្ទ", "លេខ", "កូដ", "ទំនាក់ទំនង", "គណនី"}, {"my", "ဖုန်း", "မိုဘိုင်း", "နံပါတ်", "ကုဒ်", "အကောင့်", "ဆက်သွယ်"}, stringArray40, stringArray41, {"tl", "telepono", "tawag", "tumawag", "numero"}};
        i = null;
    }

    public static HashSet a() {
        String[] stringArray2 = i;
        if (stringArray2 != null) {
            return stringArray2;
        }
        HashSet<String> hashSet = new HashSet<String>();
        Set set = n.f;
        for (String[] stringArray2 : h) {
            if (!stringArray2[0].equals("*") && (set == null || !set.contains(stringArray2[0]))) continue;
            for (int i3 = 1; i3 < stringArray2.length; ++i3) {
                hashSet.add(stringArray2[i3]);
            }
        }
        i = hashSet;
        return hashSet;
    }

    public static void b(String string, ArrayList arrayList) {
        Matcher matcher = c.matcher(string);
        int n3 = 0;
        while (matcher.find()) {
            String string2;
            if (matcher.start() != n3) {
                string2 = string.substring(n3, matcher.start());
                arrayList.add(new e0(string2, d0.j(string2)));
            }
            string2 = string.substring(matcher.start(), matcher.end());
            arrayList.add(new e0(string2, d0.j(string2)));
            n3 = matcher.end();
        }
        if (n3 < string.length()) {
            string = string.substring(n3);
            arrayList.add(new e0(string, d0.j(string)));
        }
    }

    public static void c(String string, ArrayList arrayList) {
        Matcher matcher = e.matcher(string);
        int n3 = 0;
        while (matcher.find()) {
            if (matcher.start() != n3) {
                arrayList.add(new e0(string.substring(n3, matcher.start()), 2));
            }
            arrayList.add(new e0(string.substring(matcher.start(), matcher.end()), 5));
            n3 = matcher.end();
        }
        if (n3 < string.length()) {
            arrayList.add(new e0(string.substring(n3), 2));
        }
    }

    public static void d(String string, ArrayList arrayList) {
        Matcher matcher = f.matcher(string);
        int n3 = 0;
        while (matcher.find()) {
            if (matcher.start() != n3) {
                d0.b(string.substring(n3, matcher.start()), arrayList);
            }
            arrayList.add(new e0(string.substring(matcher.start(), matcher.end()), 3));
            n3 = matcher.end();
        }
        if (n3 < string.length()) {
            d0.b(string.substring(n3), arrayList);
        }
    }

    public static boolean e(String string, HashSet object) {
        if (string.isEmpty()) {
            return false;
        }
        Iterator iterator = ((HashSet)object).iterator();
        while (iterator.hasNext()) {
            object = (String)iterator.next();
            int n3 = string.indexOf((String)object);
            while (n3 >= 0) {
                if (!d0.p((String)object)) {
                    return true;
                }
                boolean bl = n3 == 0 || !Character.isLetter(string.charAt(n3 - 1));
                int n4 = ((String)object).length() + n3;
                n4 = n4 < string.length() && Character.isLetter(string.charAt(n4)) ? 0 : 1;
                if (bl && n4 != 0) {
                    return true;
                }
                n3 = string.indexOf((String)object, n3 + 1);
            }
        }
        return false;
    }

    public static String f(ArrayList object, int n3, int n4) {
        CharSequence charSequence = new StringBuilder();
        while (++n3 < ((ArrayList)object).size() && ((StringBuilder)charSequence).length() < n4) {
            ((StringBuilder)charSequence).append(((e0)((ArrayList)object).get(n3)).c());
        }
        charSequence = ((StringBuilder)charSequence).toString();
        object = charSequence;
        if (((String)charSequence).length() > n4) {
            object = ((String)charSequence).substring(0, n4);
        }
        return ((String)object).toLowerCase(Locale.ROOT);
    }

    public static String g(ArrayList object, int n3, int n4) {
        CharSequence charSequence = new StringBuilder();
        --n3;
        while (n3 >= 0 && ((StringBuilder)charSequence).length() < n4) {
            ((StringBuilder)charSequence).insert(0, ((e0)((ArrayList)object).get(n3)).c());
            --n3;
        }
        charSequence = ((StringBuilder)charSequence).toString();
        object = charSequence;
        if (((String)charSequence).length() > n4) {
            object = ((String)charSequence).substring(((String)charSequence).length() - n4);
        }
        return ((String)object).toLowerCase(Locale.ROOT);
    }

    public static int h(int[] nArray) {
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            int n3 = nArray[i3];
            if (n3 == 3 || n3 == 4 || n3 == 5) continue;
            return n3;
        }
        return 0;
    }

    public static int i(int[] nArray, int n3) {
        --n3;
        while (n3 >= 0) {
            int n4 = nArray[n3];
            if (n4 != 3 && n4 != 4 && n4 != 5) {
                return n4;
            }
            --n3;
        }
        return 0;
    }

    public static int j(String string) {
        if (c0.a(string)) {
            return 0;
        }
        if (d0.o(string)) {
            return 4;
        }
        if (d0.n(string)) {
            return 3;
        }
        if (d0.l(string)) {
            return 5;
        }
        return 1;
    }

    public static int k(int[] nArray, int n3, int n4) {
        int n5;
        n3 = n5 = d0.i(nArray, n3);
        if (n5 == 0) {
            n3 = d0.h(nArray);
        }
        if (n3 == 0) {
            return n4;
        }
        return n3;
    }

    public static boolean l(String string) {
        return e.matcher(string).matches();
    }

    public static boolean m(String string) {
        int n3 = 0;
        for (int i3 = 0; i3 < string.length(); ++i3) {
            block5: {
                int n4;
                block4: {
                    char c3;
                    block3: {
                        c3 = string.charAt(i3);
                        if (c3 < '0' || c3 > '9') break block3;
                        n4 = n3 + 1;
                        break block4;
                    }
                    if (c3 == ',' || c3 == '.' || c3 == '%' || c3 == '$') break block5;
                    n4 = n3;
                    if (c3 < '\u20a0') break block4;
                    n4 = n3;
                    if (c3 <= '\u20cf') break block5;
                }
                n3 = n4;
                continue;
            }
            return false;
        }
        return n3 >= 4;
    }

    public static boolean n(String string) {
        return b.matcher(string).matches();
    }

    public static boolean o(String string) {
        return c.matcher(string).matches();
    }

    public static boolean p(String string) {
        char c3 = string.charAt(0);
        if (c3 >= '\u0e00' && c3 <= '\u0eff') {
            return false;
        }
        if (c3 >= '\u1000' && c3 <= '\u109f') {
            return false;
        }
        if (c3 >= '\u1780' && c3 <= '\u17ff') {
            return false;
        }
        if (c3 >= '\u2e80' && c3 <= '\u9fff') {
            return false;
        }
        if (c3 >= '\uac00' && c3 <= '\ud7af') {
            return false;
        }
        return c3 < '\uf900' || c3 > '\ufaff';
    }

    public static String q(String string) {
        return g.matcher(string).replaceAll("");
    }

    public static boolean r(String stringArray) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        String string;
        block30: {
            block29: {
                string = stringArray.trim();
                if (string.isEmpty()) {
                    return false;
                }
                int n9 = 0;
                n3 = n8 = (n7 = (n6 = (n5 = (n4 = 0))));
                int n10 = n6;
                int n11 = n5;
                for (n6 = n9; n6 < string.length(); ++n6) {
                    int n12;
                    int n13;
                    n5 = string.charAt(n6);
                    if (n5 >= 48 && n5 <= 57) {
                        int n14 = n4 + 1;
                        int n15 = n10 + 1;
                        n4 = n14;
                        n10 = n11;
                        n5 = n15;
                        n9 = n7;
                        n13 = n8;
                        n12 = n3;
                        if (n15 > n7) {
                            n9 = n15;
                            n4 = n14;
                            n10 = n11;
                            n5 = n15;
                            n13 = n8;
                            n12 = n3;
                        }
                    } else if (n5 == 44) {
                        n5 = 0;
                        n13 = 1;
                        n10 = n11;
                        n9 = n7;
                        n12 = n3;
                    } else if (n5 == 46) {
                        n5 = 0;
                        n12 = 1;
                        n10 = n11;
                        n9 = n7;
                        n13 = n8;
                    } else if (n5 != 37 && n5 != 36 && (n5 < 8352 || n5 > 8399)) {
                        n5 = 0;
                        n10 = n11;
                        n9 = n7;
                        n13 = n8;
                        n12 = n3;
                    } else {
                        n5 = 0;
                        n10 = 1;
                        n12 = n3;
                        n13 = n8;
                        n9 = n7;
                    }
                    n11 = n10;
                    n10 = n5;
                    n7 = n9;
                    n8 = n13;
                    n3 = n12;
                }
                if (n4 == 0) {
                    return false;
                }
                if (n11 != 0) {
                    return false;
                }
                if ((string.startsWith("+") || string.startsWith("00")) && n4 >= 8) {
                    return true;
                }
                if (string.charAt(0) == '+') break block29;
                stringArray = string;
                if (string.charAt(0) != '-') break block30;
            }
            stringArray = string.substring(1);
        }
        if (stringArray.length() > 0 && stringArray.charAt(0) == '0' && n4 >= 5) {
            return true;
        }
        if (n8 == 0 && n3 == 0) {
            if (n7 >= 7) {
                return true;
            }
            if ((stringArray = stringArray.split("[\\s\\-]+")).length >= 3 && n4 >= 7) {
                block27: {
                    n3 = stringArray.length;
                    n7 = 0;
                    n5 = 1;
                    while (true) {
                        block28: {
                            n6 = n5;
                            if (n7 >= n3) break block27;
                            string = stringArray[n7];
                            if (string.length() < 2 || string.length() > 4) break;
                            n8 = 0;
                            while (true) {
                                n6 = n5;
                                if (n8 >= string.length()) break block28;
                                n6 = string.charAt(n8);
                                if (n6 < 48 || n6 > 57) break;
                                ++n8;
                            }
                            n6 = 0;
                        }
                        if (n6 != 0) {
                            ++n7;
                            n5 = n6;
                            continue;
                        }
                        break block27;
                        break;
                    }
                    n6 = 0;
                }
                if (n6 != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String s(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
        int n3 = 0;
        for (int i3 = 0; i3 < string.length(); ++i3) {
            char c3 = string.charAt(i3);
            if (c3 >= '0' && c3 <= '9' && n3 >= 48 && n3 <= 57) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(c3);
            n3 = c3;
        }
        return stringBuilder.toString();
    }

    public static ArrayList t(String object, int n3, int n4, int n5, int object2, int n6) {
        block49: {
            Object object3;
            int n7;
            ArrayList<e0> arrayList;
            int n8;
            Object object4;
            block51: {
                block53: {
                    block50: {
                        block52: {
                            if (object2 == -1 || n6 == -1) break block49;
                            if (object == null) {
                                return new ArrayList();
                            }
                            if (((String)(object = ((String)object).trim())).isEmpty()) {
                                return new ArrayList();
                            }
                            object = d0.q(((String)object).replaceAll("[   ﻿]", " ").replaceAll("\\s+", " "));
                            object4 = n.e(Locale.getDefault());
                            object2 = AutoTtsService.S;
                            n6 = 2;
                            n8 = 1;
                            if (!(object2 != 1 ? (object2 == 4 || object2 == 5) && ((String)object4).equals(AutoTtsService.P) : ((String)object4).equals(AutoTtsService.H))) {
                                n6 = 1;
                            }
                            object4 = new ArrayList();
                            arrayList = d.matcher((CharSequence)object);
                            n7 = 0;
                            object2 = 0;
                            while (((Matcher)((Object)arrayList)).find()) {
                                object3 = ((Matcher)((Object)arrayList)).start();
                                if (object2 != object3) {
                                    d0.c(((String)object).substring((int)object2, (int)object3), (ArrayList)object4);
                                }
                                object2 = ((Matcher)((Object)arrayList)).end();
                                d0.d(((String)object).substring((int)object3, (int)object2), (ArrayList)object4);
                            }
                            if (object2 < ((String)object).length()) {
                                d0.c(((String)object).substring((int)object2), (ArrayList)object4);
                            }
                            object = new ArrayList();
                            object2 = 0;
                            while (object2 < ((ArrayList)object4).size()) {
                                arrayList = new StringBuilder(((e0)((ArrayList)object4).get((int)object2)).c());
                                object3 = ((e0)((ArrayList)object4).get((int)object2)).a();
                                while (++object2 < ((ArrayList)object4).size() && (((e0)((ArrayList)object4).get((int)object2)).a() == object3 || ((e0)((ArrayList)object4).get((int)object2)).a() == 0)) {
                                    ((StringBuilder)((Object)arrayList)).append(((e0)((ArrayList)object4).get((int)object2)).c());
                                }
                                ((ArrayList)object).add(new e0(((StringBuilder)((Object)arrayList)).toString(), (int)object3));
                            }
                            if (AutoTtsService.d0) {
                                object4 = d0.a();
                                for (object2 = 0; object2 < ((ArrayList)object).size(); ++object2) {
                                    boolean bl;
                                    if (((e0)((ArrayList)object).get((int)object2)).a() != 3) continue;
                                    arrayList = ((e0)((ArrayList)object).get((int)object2)).c();
                                    boolean bl2 = bl = d0.r((String)((Object)arrayList));
                                    if (!bl) {
                                        bl2 = bl;
                                        if (!((HashSet)object4).isEmpty()) {
                                            bl2 = bl;
                                            if (d0.m((String)((Object)arrayList))) {
                                                bl2 = d0.e(d0.g((ArrayList)object, object2, 48), (HashSet)object4) || d0.e(d0.f((ArrayList)object, object2, 24), (HashSet)object4);
                                            }
                                        }
                                    }
                                    if (!bl2) continue;
                                    ((ArrayList)object).set((int)object2, new e0(d0.s((String)((Object)arrayList)), 3));
                                }
                            }
                            if (((ArrayList)object).isEmpty()) break block50;
                            if (((ArrayList)object).size() != 1) break block51;
                            object2 = ((e0)((ArrayList)object).get(0)).a();
                            if (object2 == 3) break block52;
                            if (object2 != 4) {
                                if (object2 == 5 && n5 != 3) {
                                    if (n5 != 0) {
                                        ((e0)((ArrayList)object).get(0)).d(n5);
                                        return object;
                                    }
                                    ((e0)((ArrayList)object).get(0)).d(n6);
                                    return object;
                                }
                            } else if (n4 != 3) {
                                if (n4 != 0) {
                                    ((e0)((ArrayList)object).get(0)).d(n4);
                                    return object;
                                }
                                ((e0)((ArrayList)object).get(0)).d(n6);
                                return object;
                            }
                            break block50;
                        }
                        if (n3 != 3) break block53;
                    }
                    return object;
                }
                if (n3 != 0) {
                    ((e0)((ArrayList)object).get(0)).d(n3);
                    return object;
                }
                ((e0)((ArrayList)object).get(0)).d(n6);
                return object;
            }
            object4 = new int[((ArrayList)object).size()];
            for (object2 = 0; object2 < ((ArrayList)object).size(); ++object2) {
                object4[object2] = ((e0)((ArrayList)object).get((int)object2)).a();
            }
            object2 = object4[0];
            if (object2 != 3) {
                if (object2 != 4) {
                    if (object2 == 5 && n5 != 3) {
                        if (n5 == 0) {
                            ((e0)((ArrayList)object).get(0)).d(d0.k((int[])object4, 0, n6));
                        } else {
                            ((e0)((ArrayList)object).get(0)).d(n5);
                        }
                    }
                } else if (n4 != 3) {
                    if (n4 == 0) {
                        ((e0)((ArrayList)object).get(0)).d(d0.k((int[])object4, 0, n6));
                    } else {
                        ((e0)((ArrayList)object).get(0)).d(n4);
                    }
                }
            } else if (n3 != 3) {
                if (n3 == 0) {
                    ((e0)((ArrayList)object).get(0)).d(d0.k((int[])object4, 0, n6));
                } else {
                    ((e0)((ArrayList)object).get(0)).d(n3);
                }
            }
            for (object2 = 1; object2 < ((ArrayList)object).size(); ++object2) {
                object3 = object4[object2];
                if (object3 != 3) {
                    if (object3 != 4) {
                        if (object3 != 5 || n5 == 3) continue;
                        if (n5 == 0) {
                            ((e0)((ArrayList)object).get((int)object2)).d(d0.k((int[])object4, object2, n6));
                            continue;
                        }
                        ((e0)((ArrayList)object).get((int)object2)).d(n5);
                        continue;
                    }
                    if (n4 == 3) continue;
                    if (n4 == 0) {
                        ((e0)((ArrayList)object).get((int)object2)).d(d0.k((int[])object4, object2, n6));
                        continue;
                    }
                    ((e0)((ArrayList)object).get((int)object2)).d(n4);
                    continue;
                }
                if (n3 == 3) continue;
                if (n3 == 0) {
                    ((e0)((ArrayList)object).get((int)object2)).d(d0.k((int[])object4, object2, n6));
                    continue;
                }
                ((e0)((ArrayList)object).get((int)object2)).d(n3);
            }
            arrayList = new ArrayList<e0>();
            if (n4 != 0 && !AutoTtsService.c0) {
                n4 = n8;
                n3 = n7;
            } else {
                n4 = 0;
                n3 = n7;
            }
            while (n3 < ((ArrayList)object).size()) {
                if (n4 != 0 && object4[n3] == 4) {
                    arrayList.add(new e0(((e0)((ArrayList)object).get(n3)).c(), ((e0)((ArrayList)object).get(n3)).a()));
                    ++n3;
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder(((e0)((ArrayList)object).get(n3)).c());
                n5 = ((e0)((ArrayList)object).get(n3)).a();
                while (++n3 < ((ArrayList)object).size() && ((e0)((ArrayList)object).get(n3)).a() == n5 && (n4 == 0 || object4[n3] != 4)) {
                    stringBuilder.append(((e0)((ArrayList)object).get(n3)).c());
                }
                arrayList.add(new e0(stringBuilder.toString(), n5));
            }
            return arrayList;
        }
        return new ArrayList();
    }
}

