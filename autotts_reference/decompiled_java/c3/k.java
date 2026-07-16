/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.speech.tts.TextToSpeech
 *  android.util.Log
 */
package c3;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import c3.d;
import c3.l;
import c3.m;
import c3.t;
import c3.u;
import com.vnspeak.autotts.AutoTtsService;
import java.text.Collator;
import java.text.Normalizer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class k {
    public static m a;
    public static final List b;
    public static final List c;
    public static List d;
    public static List e;
    public static Set f;
    public static TextToSpeech g;
    public static Map h;
    public static Map i;

    static {
        b = new ArrayList();
        c = new ArrayList();
        d = new ArrayList();
        e = new ArrayList();
        f = new HashSet();
        g = null;
        h = new HashMap();
        i = new HashMap();
    }

    public static void A(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putBoolean("strip_audio_attr", AutoTtsService.P);
        context.putBoolean("force_accessibility_stream", AutoTtsService.Q);
        context.putBoolean("show_notification", AutoTtsService.R);
        context.putBoolean("disable_advanced_detection", AutoTtsService.S);
        context.commit();
    }

    public static void B(Context context, u u3, int n3) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putString(u3.f(), String.valueOf(n3));
        if (n3 == 0) {
            context.putString(k.f(u3.c), u3.f());
        }
        context.commit();
    }

    public static void C(Context context) {
        for (int i3 = 0; i3 < e.size(); ++i3) {
            k.B(context, (u)e.get(i3), i3);
        }
    }

    public static void D(Context context) {
        CharSequence charSequence;
        if (d.isEmpty()) {
            return;
        }
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        AutoTtsService.T = new ArrayList();
        for (int i3 = 0; i3 < d.size(); ++i3) {
            charSequence = ((u)d.get(i3)).f();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("voice_");
            stringBuilder.append(i3);
            context.putString(stringBuilder.toString(), (String)charSequence);
            AutoTtsService.T.add(charSequence);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("voice_");
        ((StringBuilder)charSequence).append(d.size());
        context.putString(((StringBuilder)charSequence).toString(), "");
        context.putBoolean("dedicated_engines", AutoTtsService.O);
        context.commit();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(Context context, Locale locale, l l3) {
        synchronized (k.class) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(l3.b);
            charSequence.append("#");
            charSequence.append(locale.toString());
            charSequence = charSequence.toString();
            u u3 = new u(locale, l3, k.s(context, (String)charSequence));
            d.add(u3);
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean b(Locale object, String string, String string2) {
        synchronized (k.class) {
            try {
                String string3 = k.f((Locale)object);
                String string4 = k.e((Locale)object);
                boolean bl = false;
                int n3 = 0;
                while (true) {
                    boolean bl2 = bl;
                    if (n3 >= d.size()) return bl2;
                    if (((u)k.d.get((int)n3)).d.b.equals(string)) {
                        Object object2 = k.t(((u)k.d.get((int)n3)).c);
                        object = k.f((Locale)object2);
                        object2 = k.e((Locale)object2);
                        if (((String)object).equalsIgnoreCase(string3) && ((String)object2).equalsIgnoreCase(string4)) {
                            if (((u)k.d.get((int)n3)).f.contains(string2)) return true;
                            ((u)d.get(n3)).a(string2);
                            return true;
                        }
                    }
                    ++n3;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static void c() {
        if (h.isEmpty()) {
            for (String string : Locale.getISOLanguages()) {
                String string2 = k.f(new Locale(string));
                try {
                    h.put(string, string2);
                }
                catch (Exception exception) {
                    Log.e((String)"AutoTTS", (String)exception.getMessage());
                }
                try {
                    i.put(string2, string);
                }
                catch (Exception exception) {
                    Log.e((String)"AutoTTS", (String)exception.getMessage());
                }
            }
        }
    }

    public static ArrayList d(Context context) {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        for (int i3 = 0; i3 < d.size(); ++i3) {
            Object object = ((u)d.get(i3)).c();
            String[] stringArray = ((u)d.get(i3)).e();
            if (!stringArray.equalsIgnoreCase("eng") && !stringArray.equalsIgnoreCase(AutoTtsService.D) || arrayList.contains(object)) continue;
            arrayList.add((String)object);
            object = new d((String)object, (String)stringArray);
            SharedPreferences sharedPreferences = context.getSharedPreferences("auto_tts_settings", 0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_volume");
            ((d)object).d = sharedPreferences.getInt(stringBuilder.toString(), 100);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_pitch");
            ((d)object).e = sharedPreferences.getInt(stringBuilder.toString(), 100);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_speed");
            ((d)object).c = sharedPreferences.getInt(stringBuilder.toString(), 100);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_variant");
            ((d)object).h = sharedPreferences.getString(stringBuilder.toString(), "*Default");
            ((d)object).f = "";
            ((d)object).g = "";
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_disabled");
            ((d)object).i = sharedPreferences.getBoolean(stringBuilder.toString(), false);
            stringArray = sharedPreferences.getString((String)stringArray, "");
            if (!stringArray.equals("") && (stringArray = stringArray.split("#")).length >= 2) {
                ((d)object).f = stringArray[0];
                ((d)object).g = stringArray[1];
            }
            arrayList2.add(object);
        }
        Collections.sort(arrayList2, new Comparator(){

            public int a(d object, d object2) {
                object = ((d)object).a;
                Normalizer.Form form = Normalizer.Form.NFD;
                object = Normalizer.normalize((CharSequence)object, form).replaceAll("\\p{M}", "");
                object2 = Normalizer.normalize(((d)object2).a, form).replaceAll("\\p{M}", "");
                return Collator.getInstance().compare((String)object, (String)object2);
            }
        });
        return arrayList2;
    }

    public static String e(Locale object) {
        if (object == null) {
            return "";
        }
        try {
            object = ((Locale)object).getISO3Country();
            return object;
        }
        catch (Exception exception) {
            return "";
        }
    }

    public static String f(Locale object) {
        if (object == null) {
            return "zxx";
        }
        try {
            object = ((Locale)object).getISO3Language();
            if (!(((String)object).equals("cmn") || ((String)object).equals("lzh") || ((String)object).equals("gan") || ((String)object).equals("hak"))) {
                return object;
            }
            return "zho";
        }
        catch (Exception exception) {
            return "zxx";
        }
    }

    public static int g(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (!string.equals(((d)list.get((int)i3)).b)) continue;
            return i3;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ArrayList h(Context var0, boolean var1_2) {
        synchronized (k.class) {
            block16: {
                block17: {
                    try {
                        var9_3 = k.n();
                        var10_4 = new ArrayList<String>();
                        var8_5 = new ArrayList<d>();
                        var4_6 = 0;
                        var2_7 = 0;
lbl8:
                        // 2 sources

                        while (true) {
                            if (var4_6 < k.d.size()) {
                                var14_15 = ((u)k.d.get(var4_6)).c();
                                var11_12 = ((u)k.d.get(var4_6)).e();
                                var13_14 = ((u)k.d.get((int)var4_6)).d.b;
                                if (AutoTtsService.L == 3 && !var13_14.equalsIgnoreCase("com.google.android.tts")) {
                                    var5_9 = var2_7;
                                    break block16;
                                }
                                if (!var10_4.contains(var14_15)) {
                                    var10_4.add(var14_15);
                                    var12_13 /* !! */  = new d(var14_15, (String)var11_12);
                                    var14_15 = var0.getSharedPreferences("auto_tts_settings", 0);
                                    var15_16 = new StringBuilder();
                                    var15_16.append((String)var11_12);
                                    var15_16.append("_volume");
                                    var12_13 /* !! */ .d = var14_15.getInt(var15_16.toString(), 100);
                                    var15_16 = new StringBuilder();
                                    var15_16.append((String)var11_12);
                                    var15_16.append("_pitch");
                                    var12_13 /* !! */ .e = var14_15.getInt(var15_16.toString(), 100);
                                    var15_16 = new StringBuilder();
                                    var15_16.append((String)var11_12);
                                    var15_16.append("_speed");
                                    var12_13 /* !! */ .c = var14_15.getInt(var15_16.toString(), 100);
                                    var15_16 = new StringBuilder();
                                    var15_16.append((String)var11_12);
                                    var15_16.append("_variant");
                                    var12_13 /* !! */ .h = var14_15.getString(var15_16.toString(), "*Default");
                                    var12_13 /* !! */ .f = "";
                                    var12_13 /* !! */ .g = "";
                                    var15_16 = new StringBuilder();
                                    var15_16.append((String)var11_12);
                                    var15_16.append("_disabled");
                                    var12_13 /* !! */ .i = var7_11 = var14_15.getBoolean(var15_16.toString(), false);
                                    var3_8 = var2_7;
                                    if (var7_11) {
                                        var3_8 = var2_7;
                                        if (var9_3.contains(var11_12)) {
                                            var12_13 /* !! */ .i = false;
                                            var3_8 = 1;
                                        }
                                    }
                                    var12_13 /* !! */ .j.add(var13_14);
                                    var11_12 = var14_15.getString((String)var11_12, "");
                                    if (!var11_12.equals("") && (var11_12 = var11_12.split("#")).length >= 2) {
                                        var12_13 /* !! */ .f = var11_12[0];
                                        var12_13 /* !! */ .g = var11_12[1];
                                    }
                                    if (var1_2) {
                                        var5_9 = var3_8;
                                        if (!var12_13 /* !! */ .i) {
                                            var8_5.add(var12_13 /* !! */ );
                                            var5_9 = var3_8;
                                        }
                                        break block16;
                                    }
                                    var8_5.add(var12_13 /* !! */ );
                                    var5_9 = var3_8;
                                    break block16;
                                }
                                var6_10 = var8_5.size();
                                var3_8 = 0;
                                break block17;
                            }
                            var9_3 = new Comparator(){

                                public int a(d object, d object2) {
                                    object = ((d)object).a;
                                    Normalizer.Form form = Normalizer.Form.NFD;
                                    object = Normalizer.normalize((CharSequence)object, form).replaceAll("\\p{M}", "");
                                    object2 = Normalizer.normalize(((d)object2).a, form).replaceAll("\\p{M}", "");
                                    return Collator.getInstance().compare((String)object, (String)object2);
                                }
                            };
                            Collections.sort(var8_5, var9_3);
                            if (var2_7 == 0) ** break block18
                            k.z(var0);
                            break;
                        }
                    }
                    catch (Throwable var0_1) {}
                    {
                        return var8_5;
                    }
                    throw var0_1;
                }
                do {
                    var5_9 = var2_7;
                    if (var3_8 >= var6_10) break block16;
                    var12_13 /* !! */  = var8_5.get(var3_8);
                    ++var3_8;
                } while (!var12_13 /* !! */ .b.equalsIgnoreCase((String)var11_12));
                var12_13 /* !! */ .j.add(var13_14);
                var5_9 = var2_7;
            }
            ++var4_6;
            var2_7 = var5_9;
            ** continue;
        }
    }

    public static ArrayList i() {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (!((d)list.get((int)i3)).b.equalsIgnoreCase("eng") && !((d)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.D)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((d)list.get((int)i3)).a);
            stringBuilder.append(" (");
            stringBuilder.append(((d)list.get((int)i3)).b);
            stringBuilder.append(")");
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    public static ArrayList j(String string, boolean bl) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((d)list.get((int)i3)).j).contains(string) || ((d)list.get((int)i3)).i) continue;
            if (!bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((d)list.get((int)i3)).a);
                stringBuilder.append(" (");
                stringBuilder.append(((d)list.get((int)i3)).b);
                stringBuilder.append(")");
                arrayList.add(stringBuilder.toString());
                continue;
            }
            arrayList.add(((d)list.get((int)i3)).b);
        }
        return arrayList;
    }

    public static ArrayList k(String string) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((d)list.get((int)i3)).j).contains(string)) continue;
            arrayList.add(((d)list.get((int)i3)).b);
        }
        return arrayList;
    }

    public static ArrayList l(String string) {
        List list;
        ArrayList<Boolean> arrayList = new ArrayList<Boolean>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((d)list.get((int)i3)).j).contains(string)) continue;
            int n3 = AutoTtsService.L;
            if (n3 != 1) {
                if (n3 != 2 && n3 != 3) {
                    if (n3 == 4 && (((d)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.H) || ((d)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.I))) {
                        ((d)list.get((int)i3)).i = false;
                    }
                } else if (((d)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.C)) {
                    ((d)list.get((int)i3)).i = false;
                }
            } else if (((d)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.D)) {
                ((d)list.get((int)i3)).i = false;
            }
            arrayList.add(((d)list.get((int)i3)).i ^ true);
        }
        return arrayList;
    }

    public static ArrayList m(String string) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((d)list.get((int)i3)).j).contains(string)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((d)list.get((int)i3)).a);
            stringBuilder.append(" (");
            stringBuilder.append(((d)list.get((int)i3)).b);
            stringBuilder.append(")");
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    public static ArrayList n() {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n3 = AutoTtsService.L;
        if (n3 != 1) {
            if (n3 != 2 && n3 != 3) {
                if (n3 != 4) {
                    return arrayList;
                }
                arrayList.add(AutoTtsService.H);
                arrayList.add(AutoTtsService.I);
                return arrayList;
            }
            arrayList.add(AutoTtsService.C);
            return arrayList;
        }
        arrayList.add(AutoTtsService.D);
        arrayList.add("eng");
        return arrayList;
    }

    public static Boolean o(String object) {
        Object object2 = object;
        if (((String)object).length() != 3) {
            object2 = object = (String)h.get(object);
            if (object == null) {
                return Boolean.FALSE;
            }
        }
        for (int i3 = 0; i3 < (object = c).size(); ++i3) {
            if (((d)object.get((int)i3)).b.compareTo((String)object2) != 0) continue;
            return ((d)object.get((int)i3)).i ^ true;
        }
        return Boolean.FALSE;
    }

    public static void p(Context context) {
        int n3;
        SharedPreferences sharedPreferences = context.getSharedPreferences("auto_tts_settings", 0);
        int n4 = n3 = sharedPreferences.getInt("auto_mode", 3);
        if (n3 == 3) {
            n4 = n3;
            if (!t.a(context)) {
                n4 = 0;
            }
        }
        AutoTtsService.L = n4;
        AutoTtsService.N = sharedPreferences.getBoolean("locale_spans", false);
    }

    public static void q(Context context) {
        AutoTtsService.C = (context = context.getSharedPreferences("auto_tts_settings", 0)).getString("auto_mode_language", "");
        if (AutoTtsService.C.equals("")) {
            AutoTtsService.C = k.f(Locale.getDefault());
        }
        if ((AutoTtsService.H = context.getString("mixed_mode_latin_language", "")).equals("")) {
            AutoTtsService.H = k.f(Locale.getDefault());
        }
        if ((AutoTtsService.I = context.getString("mixed_mode_non_latin_language", "")).equals("")) {
            AutoTtsService.I = k.f(Locale.getDefault());
        }
        if ((AutoTtsService.D = context.getString("dual_mode_language", "")).equals("")) {
            AutoTtsService.D = k.f(Locale.getDefault());
        }
        AutoTtsService.E = context.getInt("number_mode_language", 0);
        AutoTtsService.F = context.getInt("punc_mode_language", 0);
        AutoTtsService.G = context.getInt("emoji_mode_language", 0);
    }

    public static void r(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0);
        AutoTtsService.P = context.getBoolean("strip_audio_attr", false);
        AutoTtsService.Q = context.getBoolean("force_accessibility_stream", false);
        AutoTtsService.R = context.getBoolean("show_notification", false);
        AutoTtsService.S = context.getBoolean("disable_advanced_detection", false);
    }

    public static int s(Context context, String string) {
        return Integer.parseInt(context.getSharedPreferences("auto_tts_settings", 0).getString(string, "1000"));
    }

    public static Locale t(Locale object) {
        if (object == null) {
            return null;
        }
        String string = k.f((Locale)object);
        String string2 = k.e((Locale)object);
        if (!((String)(object = ((Locale)object).getVariant())).isEmpty()) {
            return new Locale(string, string2, (String)object);
        }
        if (!string2.isEmpty()) {
            return new Locale(string, string2);
        }
        return new Locale(string);
    }

    public static void u(Context context) {
        k.x(context);
        k.D(context);
        k.C(context);
        k.w(context);
        k.y(context);
        k.v(context);
        k.A(context);
    }

    public static void v(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putInt("auto_mode", AutoTtsService.L);
        context.putBoolean("locale_spans", AutoTtsService.N);
        context.commit();
    }

    public static void w(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putString("auto_mode_language", AutoTtsService.C);
        context.putString("dual_mode_language", AutoTtsService.D);
        context.putString("mixed_mode_latin_language", AutoTtsService.H);
        context.putString("mixed_mode_non_latin_language", AutoTtsService.I);
        context.putInt("number_mode_language", AutoTtsService.E);
        context.putInt("punc_mode_language", AutoTtsService.F);
        context.putInt("emoji_mode_language", AutoTtsService.G);
        context.commit();
    }

    public static void x(Context context) {
        Object object;
        AutoTtsService.M = new ArrayList();
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        int n3 = 0;
        for (int i3 = 0; i3 != (object = b).size(); ++i3) {
            int n4 = n3;
            if (!((l)object.get((int)i3)).b.equals("com.vnspeak.autotts")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("engine_");
                stringBuilder.append(n3);
                context.putString(stringBuilder.toString(), ((l)object.get((int)i3)).b);
                AutoTtsService.M.add(((l)object.get((int)i3)).b);
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("engine_");
        ((StringBuilder)object).append(n3);
        context.putString(((StringBuilder)object).toString(), "end");
        context.commit();
    }

    public static void y(Context object) {
        StringBuilder stringBuilder;
        SharedPreferences.Editor editor = object.getSharedPreferences("auto_tts_settings", 0).edit();
        for (int i3 = 0; i3 < (object = c).size(); ++i3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("language_");
            stringBuilder.append(i3);
            editor.putString(stringBuilder.toString(), ((d)object.get((int)i3)).b);
            if (((d)object.get((int)i3)).c != 100) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(((d)object.get((int)i3)).b);
                stringBuilder.append("_speed");
                editor.putInt(stringBuilder.toString(), ((d)object.get((int)i3)).c);
            }
            if (((d)object.get((int)i3)).d != 100) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(((d)object.get((int)i3)).b);
                stringBuilder.append("_volume");
                editor.putInt(stringBuilder.toString(), ((d)object.get((int)i3)).d);
            }
            if (((d)object.get((int)i3)).e != 100) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(((d)object.get((int)i3)).b);
                stringBuilder.append("_pitch");
                editor.putInt(stringBuilder.toString(), ((d)object.get((int)i3)).e);
            }
            if (((d)object.get((int)i3)).h.equals("*Default")) continue;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((d)object.get((int)i3)).b);
            stringBuilder.append("_variant");
            editor.putString(stringBuilder.toString(), ((d)object.get((int)i3)).h);
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("language_");
        stringBuilder.append(object.size());
        editor.putString(stringBuilder.toString(), "");
        editor.commit();
    }

    public static void z(Context object) {
        List list;
        SharedPreferences.Editor editor = object.getSharedPreferences("auto_tts_settings", 0).edit();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            object = new StringBuilder();
            ((StringBuilder)object).append(((d)list.get((int)i3)).b);
            ((StringBuilder)object).append("_disabled");
            editor.putBoolean(((StringBuilder)object).toString(), ((d)list.get((int)i3)).i);
        }
        editor.commit();
    }
}

