/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.speech.tts.TextToSpeech
 */
package c3;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import c3.b0;
import c3.e;
import c3.f;
import c3.o;
import c3.p;
import c3.w;
import com.vnspeak.autotts.AutoTtsService;
import java.text.Collator;
import java.text.Normalizer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class n {
    public static p a;
    public static final List b;
    public static final List c;
    public static final List d;
    public static List e;
    public static Set f;
    public static TextToSpeech g;

    static {
        b = new ArrayList();
        c = new ArrayList();
        d = new ArrayList();
        e = new ArrayList();
        f = new HashSet();
        g = null;
    }

    public static void A(Context context, b0 b02, int n3) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putString(b02.f(), String.valueOf(n3));
        if (n3 == 0) {
            context.putString(n.e(b02.c), b02.f());
        }
        context.commit();
    }

    public static void B(Context context) {
        for (int i3 = 0; i3 < e.size(); ++i3) {
            n.A(context, (b0)e.get(i3), i3);
        }
    }

    public static void C(Context context) {
        StringBuilder stringBuilder;
        Object object;
        if (d.isEmpty()) {
            return;
        }
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        AutoTtsService.e0 = new ArrayList();
        for (int i3 = 0; i3 < (object = d).size(); ++i3) {
            object = ((b0)object.get(i3)).f();
            stringBuilder = new StringBuilder();
            stringBuilder.append("voice_");
            stringBuilder.append(i3);
            context.putString(stringBuilder.toString(), (String)object);
            AutoTtsService.e0.add(object);
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("voice_");
        stringBuilder.append(object.size());
        context.putString(stringBuilder.toString(), "");
        context.putBoolean("dedicated_engines", AutoTtsService.V);
        context.commit();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(Context context, Locale locale, o o3) {
        synchronized (n.class) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(o3.b);
            charSequence.append("#");
            charSequence.append(locale.toString());
            charSequence = charSequence.toString();
            b0 b02 = new b0(locale, o3, n.r(context, (String)charSequence));
            d.add(b02);
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
        synchronized (n.class) {
            try {
                String string3 = n.e((Locale)object);
                String string4 = n.d((Locale)object);
                boolean bl = false;
                int n3 = 0;
                while (true) {
                    object = d;
                    boolean bl2 = bl;
                    if (n3 >= object.size()) return bl2;
                    if (((b0)object.get((int)n3)).d.b.equals(string)) {
                        Object object2 = n.s(((b0)object.get((int)n3)).c);
                        String string5 = n.e((Locale)object2);
                        object2 = n.d((Locale)object2);
                        if (string5.equalsIgnoreCase(string3) && ((String)object2).equalsIgnoreCase(string4)) {
                            if (((b0)object.get((int)n3)).f.contains(string2)) return true;
                            ((b0)object.get(n3)).a(string2);
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

    public static ArrayList c(Context context) {
        String[] stringArray;
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        for (int i3 = 0; i3 < (stringArray = d).size(); ++i3) {
            Object object = ((b0)stringArray.get(i3)).c();
            if (!(stringArray = ((b0)stringArray.get(i3)).e()).equalsIgnoreCase("eng") && !stringArray.equalsIgnoreCase(AutoTtsService.H) || arrayList.contains(object)) continue;
            arrayList.add((String)object);
            object = new f((String)object, (String)stringArray);
            SharedPreferences sharedPreferences = context.getSharedPreferences("auto_tts_settings", 0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_volume");
            ((f)object).d = sharedPreferences.getInt(stringBuilder.toString(), 100);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_pitch");
            ((f)object).e = sharedPreferences.getInt(stringBuilder.toString(), 100);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_speed");
            ((f)object).c = sharedPreferences.getInt(stringBuilder.toString(), 100);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_variant");
            ((f)object).h = sharedPreferences.getString(stringBuilder.toString(), "*Default");
            ((f)object).f = "";
            ((f)object).g = "";
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)stringArray);
            stringBuilder.append("_disabled");
            ((f)object).i = sharedPreferences.getBoolean(stringBuilder.toString(), false);
            stringArray = sharedPreferences.getString((String)stringArray, "");
            if (!stringArray.equals("") && (stringArray = stringArray.split("#")).length >= 2) {
                ((f)object).f = stringArray[0];
                ((f)object).g = stringArray[1];
            }
            arrayList2.add(object);
        }
        Collections.sort(arrayList2, new Comparator(){

            public int a(f object, f f3) {
                String string = object.a;
                object = Normalizer.Form.NFD;
                string = Normalizer.normalize(string, (Normalizer.Form)((Object)object)).replaceAll("\\p{M}", "");
                object = Normalizer.normalize(f3.a, (Normalizer.Form)((Object)object)).replaceAll("\\p{M}", "");
                return Collator.getInstance().compare(string, (String)object);
            }
        });
        return arrayList2;
    }

    public static String d(Locale object) {
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

    public static String e(Locale object) {
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

    public static int f(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (!string.equals(((f)list.get((int)i3)).b)) continue;
            return i3;
        }
        return -1;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static ArrayList g(Context context, boolean bl) {
        // MONITORENTER : c3.n.class
        ArrayList arrayList = n.m();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        ArrayList<f> arrayList3 = new ArrayList<f>();
        Comparator comparator = d;
        // MONITORENTER : comparator
        int n3 = 0;
        int n4 = 0;
        while (true) {
            int n5;
            block21: {
                int n6;
                int n7;
                f f3;
                String[] stringArray;
                Object object;
                block22: {
                    block23: {
                        block20: {
                            object = d;
                            if (n3 >= object.size()) break block20;
                            String string = ((b0)object.get(n3)).c();
                            stringArray = ((b0)object.get(n3)).e();
                            object = ((b0)object.get((int)n3)).d.b;
                            if (AutoTtsService.S == 3 && !((String)object).equalsIgnoreCase("com.google.android.tts")) {
                                n5 = n4;
                                break block21;
                            }
                            if (!arrayList2.contains(string)) {
                                boolean bl2;
                                arrayList2.add(string);
                                f3 = new f(string, (String)stringArray);
                                string = context.getSharedPreferences("auto_tts_settings", 0);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append((String)stringArray);
                                stringBuilder.append("_volume");
                                f3.d = string.getInt(stringBuilder.toString(), 100);
                                stringBuilder = new StringBuilder();
                                stringBuilder.append((String)stringArray);
                                stringBuilder.append("_pitch");
                                f3.e = string.getInt(stringBuilder.toString(), 100);
                                stringBuilder = new StringBuilder();
                                stringBuilder.append((String)stringArray);
                                stringBuilder.append("_speed");
                                f3.c = string.getInt(stringBuilder.toString(), 100);
                                stringBuilder = new StringBuilder();
                                stringBuilder.append((String)stringArray);
                                stringBuilder.append("_variant");
                                f3.h = string.getString(stringBuilder.toString(), "*Default");
                                f3.f = "";
                                f3.g = "";
                                stringBuilder = new StringBuilder();
                                stringBuilder.append((String)stringArray);
                                stringBuilder.append("_disabled");
                                f3.i = bl2 = string.getBoolean(stringBuilder.toString(), false);
                                n7 = n4;
                                if (bl2) {
                                    n7 = n4;
                                    if (arrayList.contains(stringArray)) {
                                        f3.i = false;
                                        n7 = 1;
                                    }
                                }
                                ((AbstractCollection)f3.j).add(object);
                                stringArray = string.getString((String)stringArray, "");
                                if (!stringArray.isEmpty() && (stringArray = stringArray.split("#")).length >= 2) {
                                    f3.f = stringArray[0];
                                    f3.g = stringArray[1];
                                }
                                if (bl) {
                                    n5 = n7;
                                    if (!f3.i) {
                                        arrayList3.add(f3);
                                        n5 = n7;
                                    }
                                    break block21;
                                }
                                arrayList3.add(f3);
                                n5 = n7;
                                break block21;
                            }
                            n6 = arrayList3.size();
                            n7 = 0;
                            break block22;
                        }
                        comparator = new Comparator(){

                            public int a(f object, f object2) {
                                object = ((f)object).a;
                                Normalizer.Form form = Normalizer.Form.NFD;
                                object = Normalizer.normalize((CharSequence)object, form).replaceAll("\\p{M}", "");
                                object2 = Normalizer.normalize(((f)object2).a, form).replaceAll("\\p{M}", "");
                                return Collator.getInstance().compare((String)object, (String)object2);
                            }
                        };
                        Collections.sort(arrayList3, comparator);
                        if (n4 == 0) break block23;
                        n.y(context);
                        return arrayList3;
                    }
                    // MONITOREXIT : c3.n.class
                    return arrayList3;
                    catch (Throwable throwable) {}
                    throw throwable;
                }
                do {
                    n5 = n4;
                    if (n7 >= n6) break block21;
                    f3 = arrayList3.get(n7);
                    ++n7;
                } while (!f3.b.equalsIgnoreCase((String)stringArray));
                ((AbstractCollection)f3.j).add(object);
                n5 = n4;
            }
            ++n3;
            n4 = n5;
        }
    }

    public static ArrayList h() {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (!((f)list.get((int)i3)).b.equalsIgnoreCase("eng") && !((f)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.H)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((f)list.get((int)i3)).a);
            stringBuilder.append(" (");
            stringBuilder.append(((f)list.get((int)i3)).b);
            stringBuilder.append(")");
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    public static ArrayList i(String string, boolean bl) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((f)list.get((int)i3)).j).contains(string) || ((f)list.get((int)i3)).i) continue;
            if (!bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((f)list.get((int)i3)).a);
                stringBuilder.append(" (");
                stringBuilder.append(((f)list.get((int)i3)).b);
                stringBuilder.append(")");
                arrayList.add(stringBuilder.toString());
                continue;
            }
            arrayList.add(((f)list.get((int)i3)).b);
        }
        return arrayList;
    }

    public static ArrayList j(String string) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((f)list.get((int)i3)).j).contains(string)) continue;
            arrayList.add(((f)list.get((int)i3)).b);
        }
        return arrayList;
    }

    public static ArrayList k(String string) {
        List list;
        ArrayList<Boolean> arrayList = new ArrayList<Boolean>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((f)list.get((int)i3)).j).contains(string)) continue;
            int n3 = AutoTtsService.S;
            if (n3 != 1) {
                if (n3 != 2 && n3 != 3) {
                    if ((n3 == 4 || n3 == 5) && (((f)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.O) || ((f)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.P))) {
                        ((f)list.get((int)i3)).i = false;
                    }
                } else if (((f)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.G)) {
                    ((f)list.get((int)i3)).i = false;
                }
            } else if (((f)list.get((int)i3)).b.equalsIgnoreCase(AutoTtsService.H)) {
                ((f)list.get((int)i3)).i = false;
            }
            arrayList.add(((f)list.get((int)i3)).i ^ true);
        }
        return arrayList;
    }

    public static ArrayList l(String string) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i3 = 0; i3 < (list = c).size(); ++i3) {
            if (string != null && !((AbstractCollection)((f)list.get((int)i3)).j).contains(string)) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((f)list.get((int)i3)).a);
            stringBuilder.append(" (");
            stringBuilder.append(((f)list.get((int)i3)).b);
            stringBuilder.append(")");
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ArrayList m() {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n3 = AutoTtsService.S;
        if (n3 != 1) {
            if (n3 != 2 && n3 != 3) {
                if (n3 != 4 && n3 != 5) return arrayList;
                arrayList.add(AutoTtsService.O);
                arrayList.add(AutoTtsService.P);
                if (AutoTtsService.I == 3) {
                    arrayList.add(AutoTtsService.J);
                }
                if (AutoTtsService.K == 3) {
                    arrayList.add(AutoTtsService.L);
                }
                if (AutoTtsService.M != 3) return arrayList;
                arrayList.add(AutoTtsService.N);
                return arrayList;
            }
            arrayList.add(AutoTtsService.G);
            return arrayList;
        }
        arrayList.add(AutoTtsService.H);
        arrayList.add("eng");
        if (AutoTtsService.I == 3) {
            arrayList.add(AutoTtsService.J);
        }
        if (AutoTtsService.K == 3) {
            arrayList.add(AutoTtsService.L);
        }
        if (AutoTtsService.M != 3) return arrayList;
        arrayList.add(AutoTtsService.N);
        return arrayList;
    }

    public static Boolean n(String object) {
        String string = object;
        if (((String)object).length() != 3) {
            object = c3.e.c((String)object);
            string = object;
            if (object == null) {
                return Boolean.FALSE;
            }
        }
        for (int i3 = 0; i3 < (object = c).size(); ++i3) {
            if (((f)object.get((int)i3)).b.compareTo(string) != 0) continue;
            return ((f)object.get((int)i3)).i ^ true;
        }
        return Boolean.FALSE;
    }

    public static void o(Context context) {
        int n3;
        SharedPreferences sharedPreferences = context.getSharedPreferences("auto_tts_settings", 0);
        int n4 = n3 = sharedPreferences.getInt("auto_mode", 3);
        if (n3 == 3) {
            n4 = n3;
            if (!w.a(context)) {
                n4 = 0;
            }
        }
        AutoTtsService.S = n4;
        AutoTtsService.U = sharedPreferences.getBoolean("locale_spans", false);
    }

    public static void p(Context context) {
        AutoTtsService.G = (context = context.getSharedPreferences("auto_tts_settings", 0)).getString("auto_mode_language", "");
        if (AutoTtsService.G.isEmpty()) {
            AutoTtsService.G = n.e(Locale.getDefault());
        }
        if ((AutoTtsService.O = context.getString("mixed_mode_latin_language", "")).isEmpty()) {
            AutoTtsService.O = n.e(Locale.getDefault());
        }
        if ((AutoTtsService.P = context.getString("mixed_mode_non_latin_language", "")).isEmpty()) {
            AutoTtsService.P = n.e(Locale.getDefault());
        }
        if ((AutoTtsService.H = context.getString("dual_mode_language", "")).isEmpty()) {
            AutoTtsService.H = n.e(Locale.getDefault());
        }
        AutoTtsService.I = context.getInt("number_mode_language", 0);
        AutoTtsService.K = context.getInt("punc_mode_language", 0);
        AutoTtsService.M = context.getInt("emoji_mode_language", 0);
        AutoTtsService.J = context.getString("number_specific_language", "");
        if (AutoTtsService.J.isEmpty()) {
            AutoTtsService.J = n.e(Locale.getDefault());
        }
        if ((AutoTtsService.L = context.getString("punc_specific_language", "")).isEmpty()) {
            AutoTtsService.L = n.e(Locale.getDefault());
        }
        if ((AutoTtsService.N = context.getString("emoji_specific_language", "")).isEmpty()) {
            AutoTtsService.N = n.e(Locale.getDefault());
        }
    }

    public static void q(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0);
        AutoTtsService.W = context.getBoolean("strip_audio_attr", false);
        AutoTtsService.X = context.getBoolean("force_accessibility_stream", false);
        AutoTtsService.Y = context.getBoolean("keep_alive_mode", false);
        AutoTtsService.Z = context.getBoolean("show_notification", false);
        AutoTtsService.a0 = context.getBoolean("disable_advanced_detection", true);
        AutoTtsService.b0 = context.getBoolean("quick_character_reading", false);
        AutoTtsService.c0 = context.getBoolean("punctuation_with_sentence", true);
        AutoTtsService.d0 = context.getBoolean("smart_number_reading", false);
    }

    public static int r(Context context, String string) {
        return Integer.parseInt(context.getSharedPreferences("auto_tts_settings", 0).getString(string, "1000"));
    }

    public static Locale s(Locale object) {
        if (object == null) {
            return null;
        }
        String string = n.e((Locale)object);
        String string2 = n.d((Locale)object);
        if (!((String)(object = ((Locale)object).getVariant())).isEmpty()) {
            return new Locale(string, string2, (String)object);
        }
        if (!string2.isEmpty()) {
            return new Locale(string, string2);
        }
        return new Locale(string);
    }

    public static void t(Context context) {
        n.w(context);
        n.C(context);
        n.B(context);
        n.v(context);
        n.x(context);
        n.u(context);
        n.z(context);
    }

    public static void u(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putInt("auto_mode", AutoTtsService.S);
        context.putBoolean("locale_spans", AutoTtsService.U);
        context.commit();
    }

    public static void v(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putString("auto_mode_language", AutoTtsService.G);
        context.putString("dual_mode_language", AutoTtsService.H);
        context.putString("mixed_mode_latin_language", AutoTtsService.O);
        context.putString("mixed_mode_non_latin_language", AutoTtsService.P);
        context.putInt("number_mode_language", AutoTtsService.I);
        context.putInt("punc_mode_language", AutoTtsService.K);
        context.putInt("emoji_mode_language", AutoTtsService.M);
        context.putString("number_specific_language", AutoTtsService.J);
        context.putString("punc_specific_language", AutoTtsService.L);
        context.putString("emoji_specific_language", AutoTtsService.N);
        context.commit();
    }

    public static void w(Context context) {
        Object object;
        AutoTtsService.T = new ArrayList();
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        int n3 = 0;
        for (int i3 = 0; i3 != (object = b).size(); ++i3) {
            int n4 = n3;
            if (!((o)object.get((int)i3)).b.equals("com.vnspeak.autotts")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("engine_");
                stringBuilder.append(n3);
                context.putString(stringBuilder.toString(), ((o)object.get((int)i3)).b);
                AutoTtsService.T.add(((o)object.get((int)i3)).b);
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

    public static void x(Context object) {
        StringBuilder stringBuilder;
        SharedPreferences.Editor editor = object.getSharedPreferences("auto_tts_settings", 0).edit();
        for (int i3 = 0; i3 < (object = c).size(); ++i3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("language_");
            stringBuilder.append(i3);
            editor.putString(stringBuilder.toString(), ((f)object.get((int)i3)).b);
            stringBuilder = new StringBuilder();
            stringBuilder.append(((f)object.get((int)i3)).b);
            stringBuilder.append("_speed");
            editor.putInt(stringBuilder.toString(), ((f)object.get((int)i3)).c);
            stringBuilder = new StringBuilder();
            stringBuilder.append(((f)object.get((int)i3)).b);
            stringBuilder.append("_volume");
            editor.putInt(stringBuilder.toString(), ((f)object.get((int)i3)).d);
            stringBuilder = new StringBuilder();
            stringBuilder.append(((f)object.get((int)i3)).b);
            stringBuilder.append("_pitch");
            editor.putInt(stringBuilder.toString(), ((f)object.get((int)i3)).e);
            stringBuilder = new StringBuilder();
            stringBuilder.append(((f)object.get((int)i3)).b);
            stringBuilder.append("_variant");
            editor.putString(stringBuilder.toString(), ((f)object.get((int)i3)).h);
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("language_");
        stringBuilder.append(object.size());
        editor.putString(stringBuilder.toString(), "");
        editor.commit();
    }

    public static void y(Context object) {
        SharedPreferences.Editor editor = object.getSharedPreferences("auto_tts_settings", 0).edit();
        for (int i3 = 0; i3 < (object = c).size(); ++i3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((f)object.get((int)i3)).b);
            stringBuilder.append("_disabled");
            editor.putBoolean(stringBuilder.toString(), ((f)object.get((int)i3)).i);
        }
        editor.commit();
    }

    public static void z(Context context) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.putBoolean("strip_audio_attr", AutoTtsService.W);
        context.putBoolean("force_accessibility_stream", AutoTtsService.X);
        context.putBoolean("keep_alive_mode", AutoTtsService.Y);
        context.putBoolean("show_notification", AutoTtsService.Z);
        context.putBoolean("disable_advanced_detection", AutoTtsService.a0);
        context.putBoolean("quick_character_reading", AutoTtsService.b0);
        context.putBoolean("punctuation_with_sentence", AutoTtsService.c0);
        context.putBoolean("smart_number_reading", AutoTtsService.d0);
        context.commit();
    }
}

