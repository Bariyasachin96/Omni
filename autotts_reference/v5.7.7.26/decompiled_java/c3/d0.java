/*
 * Decompiled with CFR 0.152.
 */
package c3;

import c3.c;
import c3.c0;
import c3.e0;
import c3.n;
import com.vnspeak.autotts.AutoTtsService;
import com.vnspeak.autotts.clsCLD2;
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
    public static final Pattern h;
    public static final String[][] i;
    public static volatile HashSet j;

    static {
        String[] stringArray = new StringBuilder();
        stringArray.append("(?:");
        stringArray.append(c3.c.a());
        stringArray.append("|[‍️⃣])+");
        stringArray = stringArray.toString();
        a = stringArray;
        b = Pattern.compile("[0-9]{1,2}:[0-9]{2}(:[0-9]{2})?");
        c = Pattern.compile("[×÷°₠-⃏ -⁯\\p{Punct}\\p{Space}]*[0-9][0-9×÷°₠-⃏ -⁯\\p{Punct}\\p{Space}]*", 64);
        d = Pattern.compile("[( -⁯)(!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)(\\p{Space})]+", 64);
        e = Pattern.compile("[×÷°(₠-⃏)( -⁯)(\\p{InBasic_Latin})(\\p{InLATIN_1_SUPPLEMENT})(\\p{InLATIN_EXTENDED_A})(\\p{InLATIN_EXTENDED_ADDITIONAL})(\\p{InLATIN_EXTENDED_B})(\\p{InLATIN_EXTENDED_C})(\\p{InLATIN_EXTENDED_D})(!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~)]+", 64);
        f = Pattern.compile((String)stringArray, 64);
        g = Pattern.compile("(?<![\\p{L}0-9])[+\\-]?[0-9×÷°₠-⃏](?:[0-9×÷°₠-⃏\\p{Space}!\"#$%&'()*+,\\-_./:;<=>?@\\[\\]^_`{|}~]*[0-9×÷°₠-⃏])?+(?![\\p{L}0-9])", 64);
        h = Pattern.compile("[؜‎‏‪‫‬‭‮]", 64);
        stringArray = new String[]{"ar", "هاتف", "جوال", "اتصل", "اتصال", "رقم", "رمز", "كود", "حساب", "موبايل"};
        String[] stringArray2 = new String[]{"hu", "telefon", "hívj", "mobil", "kód", "szám", "számla", "mellék"};
        String[] stringArray3 = new String[]{"ro", "telefon", "sună", "mobil", "cod", "număr", "cont", "interior"};
        String[] stringArray4 = new String[]{"he", "טלפון", "נייד", "חייג", "קוד", "מספר", "חשבון", "שלוחה"};
        String[] stringArray5 = new String[]{"bn", "ফোন", "মোবাইল", "কল", "নম্বর", "কোড", "হিসাব", "যোগাযোগ"};
        String[] stringArray6 = new String[]{"mr", "फोन", "मोबाईल", "कॉल", "क्रमांक", "कोड", "खाते", "संपर्क"};
        String[] stringArray7 = new String[]{"lo", "ໂທລະສັບ", "ເບີ", "ລະຫັດ", "ບັນຊີ", "ຕິດຕໍ່"};
        i = new String[][]{{"*", "otp", "pin", "sms", "imei", "cvv", "tel", "fax", "hotline", "sim", "whatsapp", "zalo", "viber", "telegram"}, {"en", "phone", "mobile", "cell", "call", "dial", "code", "extension", "ext", "passcode", "verification", "account", "contact"}, {"vi", "điện thoại", "sđt", "gọi", "di động", "mã", "liên hệ", "tổng đài", "tài khoản", "cccd", "cmnd"}, {"hi", "फ़ोन", "फोन", "मोबाइल", "कॉल", "कोड", "खाता", "संपर्क", "डायल"}, {"ur", "فون", "موبائل", "کال", "نمبر", "کوڈ", "رابطہ", "اکاؤنٹ"}, {"fa", "تلفن", "موبایل", "تماس", "شماره", "کد", "همراه", "حساب"}, stringArray, {"ru", "телефон", "моб", "мобильный", "звони", "позвони", "код", "номер", "счёт", "счет", "добавочный"}, {"uk", "телефон", "моб", "дзвони", "зателефонуй", "код", "номер", "рахунок"}, {"de", "telefon", "handy", "anruf", "anrufen", "durchwahl", "code", "konto", "mobil", "rufnummer", "vorwahl"}, {"fr", "téléphone", "tél", "portable", "appel", "appelez", "code", "poste", "compte", "mobile", "numéro"}, {"es", "teléfono", "móvil", "celular", "llama", "llamar", "código", "cuenta", "extensión", "número"}, {"pt", "telefone", "celular", "telemóvel", "ligar", "ligue", "código", "conta", "ramal", "número"}, {"it", "telefono", "cellulare", "chiama", "chiamare", "codice", "conto", "interno", "numero"}, {"tr", "telefon", "cep", "ara", "arayın", "kod", "dahili", "hesap", "numara"}, {"in", "telepon", "ponsel", "hp", "hubungi", "kode", "rekening", "nomor", "seluler"}, {"id", "telepon", "ponsel", "hp", "hubungi", "kode", "rekening", "nomor", "seluler"}, {"ms", "telefon", "bimbit", "hubungi", "kod", "akaun", "nombor", "talian"}, {"jv", "telpon", "telpun", "nomer", "kode", "hubungi"}, {"th", "โทรศัพท์", "โทร", "มือถือ", "รหัส", "เบอร์", "บัญชี", "ติดต่อ", "สายด่วน"}, {"pl", "telefon", "komórka", "zadzwoń", "kod", "numer", "konto", "wewnętrzny"}, {"ne", "फोन", "मोबाइल", "कल", "नम्बर", "कोड", "खाता", "सम्पर्क"}, {"af", "telefoon", "foon", "selfoon", "skakel", "kode", "rekening", "nommer"}, {"ja", "電話", "携帯", "コード", "番号", "内線", "口座", "連絡", "ダイヤル"}, {"ko", "전화", "휴대폰", "핸드폰", "코드", "번호", "내선", "계좌", "연락", "문자"}, {"zh", "电话", "電話", "手机", "手機", "号码", "號碼", "代码", "代碼", "验证码", "驗證碼", "分机", "分機", "账号", "帳號", "拨打", "撥打", "热线", "熱線"}, {"nl", "telefoon", "bel", "bellen", "code", "mobiel", "nummer", "rekening", "toestel"}, {"sv", "telefon", "ring", "mobil", "kod", "nummer", "konto", "anknytning"}, {"da", "telefon", "ring", "mobil", "kode", "nummer", "konto"}, {"no", "telefon", "ring", "mobil", "kode", "nummer", "konto"}, {"fi", "puhelin", "soita", "kännykkä", "koodi", "numero", "tili"}, {"cs", "telefon", "zavolej", "mobil", "kód", "číslo", "účet", "linka"}, {"sk", "telefón", "zavolaj", "mobil", "kód", "číslo", "účet", "linka"}, stringArray2, stringArray3, {"bg", "телефон", "обади", "мобилен", "код", "номер", "сметка"}, {"el", "τηλέφωνο", "τηλ", "κινητό", "κωδικός", "αριθμός", "λογαριασμός"}, {"iw", "טלפון", "נייד", "חייג", "קוד", "מספר", "חשבון", "שלוחה"}, stringArray4, stringArray5, {"ta", "தொலைபேசி", "கைபேசி", "அழை", "எண்", "குறியீடு", "கணக்கு"}, {"te", "ఫోన్", "మొబైల్", "కాల్", "నంబర్", "కోడ్", "ఖాతా"}, {"kn", "ಫೋನ್", "ಮೊಬೈಲ್", "ಕರೆ", "ಸಂಖ್ಯೆ", "ಕೋಡ್", "ಖಾತೆ"}, {"ml", "ഫോൺ", "മൊബൈൽ", "വിളിക്കുക", "നമ്പർ", "കോഡ്", "അക്കൗണ്ട്"}, stringArray6, {"gu", "ફોન", "મોબાઇલ", "કૉલ", "નંબર", "કોડ", "ખાતું"}, {"pa", "ਫੋਨ", "ਮੋਬਾਈਲ", "ਕਾਲ", "ਨੰਬਰ", "ਕੋਡ", "ਖਾਤਾ"}, {"si", "දුරකථන", "ජංගම", "අමතන්න", "අංකය", "කේතය", "ගිණුම"}, {"km", "ទូរស័ព្ទ", "លេខ", "កូដ", "ទំនាក់ទំនង", "គណនី"}, {"my", "ဖုန်း", "မိုဘိုင်း", "နံပါတ်", "ကုဒ်", "အကောင့်", "ဆက်သွယ်"}, stringArray7, {"sw", "simu", "piga", "namba", "nambari", "msimbo", "akaunti"}, {"tl", "telepono", "tawag", "tumawag", "numero"}};
        j = null;
    }

    public static HashSet a() {
        String[] stringArray2 = j;
        if (stringArray2 != null) {
            return stringArray2;
        }
        HashSet<String> hashSet = new HashSet<String>();
        Set set = n.f;
        for (String[] stringArray2 : i) {
            if (!stringArray2[0].equals("*") && (set == null || !set.contains(stringArray2[0]))) continue;
            for (int i3 = 1; i3 < stringArray2.length; ++i3) {
                hashSet.add(stringArray2[i3]);
            }
        }
        j = hashSet;
        return hashSet;
    }

    public static void b(String string, ArrayList arrayList) {
        Matcher matcher = d.matcher(string);
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
        Matcher matcher = f.matcher(string);
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
        Matcher matcher = g.matcher(string);
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
        return f.matcher(string).matches();
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
                    if (c3 == ',' || c3 == '.' || c3 == ':' || c3 == '%' || c3 == '$') break block5;
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
        return c.matcher(string).matches();
    }

    public static boolean o(String string) {
        return d.matcher(string).matches();
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
        return h.matcher(string).replaceAll("");
    }

    public static boolean r(String string) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        String[] stringArray;
        block31: {
            block30: {
                stringArray = string.trim();
                if (stringArray.isEmpty()) {
                    return false;
                }
                if (b.matcher((CharSequence)stringArray).matches()) {
                    return false;
                }
                n3 = n8 = (n7 = (n6 = (n5 = (n4 = 0))));
                int n9 = n6;
                n6 = n5;
                for (int i3 = 0; i3 < stringArray.length(); ++i3) {
                    int n10;
                    int n11;
                    int n12;
                    n5 = stringArray.charAt(i3);
                    if (n5 >= 48 && n5 <= 57) {
                        int n13 = n4 + '\u0001';
                        int n14 = n9 + '\u0001';
                        n4 = n13;
                        n9 = n6;
                        n5 = n14;
                        n12 = n7;
                        n11 = n8;
                        n10 = n3;
                        if (n14 > n7) {
                            n12 = n14;
                            n4 = n13;
                            n9 = n6;
                            n5 = n14;
                            n11 = n8;
                            n10 = n3;
                        }
                    } else if (n5 == 44) {
                        n5 = 0;
                        n11 = 1;
                        n9 = n6;
                        n12 = n7;
                        n10 = n3;
                    } else if (n5 == 46) {
                        n5 = 0;
                        n10 = 1;
                        n9 = n6;
                        n12 = n7;
                        n11 = n8;
                    } else if (n5 != 37 && n5 != 36 && (n5 < 8352 || n5 > 8399)) {
                        n5 = 0;
                        n9 = n6;
                        n12 = n7;
                        n11 = n8;
                        n10 = n3;
                    } else {
                        n5 = 0;
                        n9 = 1;
                        n10 = n3;
                        n11 = n8;
                        n12 = n7;
                    }
                    n6 = n9;
                    n9 = n5;
                    n7 = n12;
                    n8 = n11;
                    n3 = n10;
                }
                if (n4 == 0) {
                    return false;
                }
                if (n6 != 0) {
                    return false;
                }
                if ((stringArray.startsWith("+") || stringArray.startsWith("00")) && n4 >= 8) {
                    return true;
                }
                if (stringArray.charAt(0) == '+') break block30;
                string = stringArray;
                if (stringArray.charAt(0) != '-') break block31;
            }
            string = stringArray.substring(1);
        }
        if (string.length() > 0 && string.charAt(0) == '0' && n4 >= 5) {
            return true;
        }
        if (n8 == 0 && n3 == 0) {
            if (n7 >= 7) {
                return true;
            }
            stringArray = string.split("[\\s\\-]+");
            if (stringArray.length >= 3 && n4 >= 7) {
                block28: {
                    n3 = stringArray.length;
                    n7 = 0;
                    n5 = 1;
                    while (true) {
                        block29: {
                            n6 = n5;
                            if (n7 >= n3) break block28;
                            string = stringArray[n7];
                            if (string.length() < 2 || string.length() > 4) break;
                            n8 = 0;
                            while (true) {
                                n6 = n5;
                                if (n8 >= string.length()) break block29;
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
                        break block28;
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
        int n3 = Math.max(1, AutoTtsService.f0);
        StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
        int n4 = 0;
        for (int i3 = 0; i3 < string.length(); ++i3) {
            char c3 = string.charAt(i3);
            if (c3 >= '0' && c3 <= '9') {
                if (n4 > 0 && n4 % n3 == 0) {
                    stringBuilder.append(' ');
                }
                ++n4;
            } else {
                n4 = 0;
            }
            stringBuilder.append(c3);
        }
        return stringBuilder.toString();
    }

    public static ArrayList t(String object, int n3, int n4, int n5, int n6, int n7) {
        block49: {
            int n8;
            int n9;
            Object object2;
            int n10;
            ArrayList<e0> arrayList;
            block51: {
                block53: {
                    block50: {
                        block52: {
                            if (n6 == -1 || n7 == -1) break block49;
                            if (object == null) {
                                return new ArrayList();
                            }
                            if (((String)(object = ((String)object).trim())).isEmpty()) {
                                return new ArrayList();
                            }
                            object = d0.q(clsCLD2.b(((String)object).replaceAll("[   ﻿]", " ").replaceAll("\\s+", " ")));
                            arrayList = n.e(Locale.getDefault());
                            n6 = AutoTtsService.T;
                            n7 = 2;
                            n10 = 1;
                            if (!(n6 != 1 ? (n6 == 4 || n6 == 5) && ((String)((Object)arrayList)).equals(AutoTtsService.Q) : ((String)((Object)arrayList)).equals(AutoTtsService.I))) {
                                n7 = 1;
                            }
                            arrayList = new ArrayList();
                            object2 = e.matcher((CharSequence)object);
                            n9 = 0;
                            n6 = 0;
                            while (((Matcher)object2).find()) {
                                n8 = ((Matcher)object2).start();
                                if (n6 != n8) {
                                    d0.c(((String)object).substring(n6, n8), arrayList);
                                }
                                n6 = ((Matcher)object2).end();
                                d0.d(((String)object).substring(n8, n6), arrayList);
                            }
                            if (n6 < ((String)object).length()) {
                                d0.c(((String)object).substring(n6), arrayList);
                            }
                            object = new ArrayList();
                            n6 = 0;
                            while (n6 < arrayList.size()) {
                                object2 = new StringBuilder(((e0)arrayList.get(n6)).c());
                                n8 = ((e0)arrayList.get(n6)).a();
                                while (++n6 < arrayList.size() && (((e0)arrayList.get(n6)).a() == n8 || ((e0)arrayList.get(n6)).a() == 0)) {
                                    ((StringBuilder)object2).append(((e0)arrayList.get(n6)).c());
                                }
                                ((ArrayList)object).add(new e0(((StringBuilder)object2).toString(), n8));
                            }
                            if (AutoTtsService.e0) {
                                object2 = d0.a();
                                for (n6 = 0; n6 < ((ArrayList)object).size(); ++n6) {
                                    boolean bl;
                                    if (((e0)((ArrayList)object).get(n6)).a() != 3) continue;
                                    arrayList = ((e0)((ArrayList)object).get(n6)).c();
                                    boolean bl2 = bl = d0.r((String)((Object)arrayList));
                                    if (!bl) {
                                        bl2 = bl;
                                        if (!((HashSet)object2).isEmpty()) {
                                            bl2 = bl;
                                            if (d0.m((String)((Object)arrayList))) {
                                                bl2 = d0.e(d0.g((ArrayList)object, n6, 48), (HashSet)object2) || d0.e(d0.f((ArrayList)object, n6, 24), (HashSet)object2);
                                            }
                                        }
                                    }
                                    if (!bl2) continue;
                                    ((ArrayList)object).set(n6, new e0(d0.s((String)((Object)arrayList)), 3));
                                }
                            }
                            if (((ArrayList)object).isEmpty()) break block50;
                            if (((ArrayList)object).size() != 1) break block51;
                            n6 = ((e0)((ArrayList)object).get(0)).a();
                            if (n6 == 3) break block52;
                            if (n6 != 4) {
                                if (n6 == 5 && n5 != 3) {
                                    if (n5 != 0) {
                                        ((e0)((ArrayList)object).get(0)).d(n5);
                                        return object;
                                    }
                                    ((e0)((ArrayList)object).get(0)).d(n7);
                                    return object;
                                }
                            } else if (n4 != 3) {
                                if (n4 != 0) {
                                    ((e0)((ArrayList)object).get(0)).d(n4);
                                    return object;
                                }
                                ((e0)((ArrayList)object).get(0)).d(n7);
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
                ((e0)((ArrayList)object).get(0)).d(n7);
                return object;
            }
            int[] nArray = new int[((ArrayList)object).size()];
            for (n6 = 0; n6 < ((ArrayList)object).size(); ++n6) {
                nArray[n6] = ((e0)((ArrayList)object).get(n6)).a();
            }
            n6 = nArray[0];
            if (n6 != 3) {
                if (n6 != 4) {
                    if (n6 == 5 && n5 != 3) {
                        if (n5 == 0) {
                            ((e0)((ArrayList)object).get(0)).d(d0.k(nArray, 0, n7));
                        } else {
                            ((e0)((ArrayList)object).get(0)).d(n5);
                        }
                    }
                } else if (n4 != 3) {
                    if (n4 == 0) {
                        ((e0)((ArrayList)object).get(0)).d(d0.k(nArray, 0, n7));
                    } else {
                        ((e0)((ArrayList)object).get(0)).d(n4);
                    }
                }
            } else if (n3 != 3) {
                if (n3 == 0) {
                    ((e0)((ArrayList)object).get(0)).d(d0.k(nArray, 0, n7));
                } else {
                    ((e0)((ArrayList)object).get(0)).d(n3);
                }
            }
            for (n6 = 1; n6 < ((ArrayList)object).size(); ++n6) {
                n8 = nArray[n6];
                if (n8 != 3) {
                    if (n8 != 4) {
                        if (n8 != 5 || n5 == 3) continue;
                        if (n5 == 0) {
                            ((e0)((ArrayList)object).get(n6)).d(d0.k(nArray, n6, n7));
                            continue;
                        }
                        ((e0)((ArrayList)object).get(n6)).d(n5);
                        continue;
                    }
                    if (n4 == 3) continue;
                    if (n4 == 0) {
                        ((e0)((ArrayList)object).get(n6)).d(d0.k(nArray, n6, n7));
                        continue;
                    }
                    ((e0)((ArrayList)object).get(n6)).d(n4);
                    continue;
                }
                if (n3 == 3) continue;
                if (n3 == 0) {
                    ((e0)((ArrayList)object).get(n6)).d(d0.k(nArray, n6, n7));
                    continue;
                }
                ((e0)((ArrayList)object).get(n6)).d(n3);
            }
            arrayList = new ArrayList<e0>();
            if (n4 != 0 && !AutoTtsService.d0) {
                n4 = n10;
                n3 = n9;
            } else {
                n4 = 0;
                n3 = n9;
            }
            while (n3 < ((ArrayList)object).size()) {
                if (n4 != 0 && nArray[n3] == 4) {
                    arrayList.add(new e0(((e0)((ArrayList)object).get(n3)).c(), ((e0)((ArrayList)object).get(n3)).a()));
                    ++n3;
                    continue;
                }
                object2 = new StringBuilder(((e0)((ArrayList)object).get(n3)).c());
                n5 = ((e0)((ArrayList)object).get(n3)).a();
                while (++n3 < ((ArrayList)object).size() && ((e0)((ArrayList)object).get(n3)).a() == n5 && (n4 == 0 || nArray[n3] != 4)) {
                    ((StringBuilder)object2).append(((e0)((ArrayList)object).get(n3)).c());
                }
                arrayList.add(new e0(((StringBuilder)object2).toString(), n5));
            }
            return arrayList;
        }
        return new ArrayList();
    }
}

