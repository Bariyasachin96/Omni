/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.media.AudioAttributes
 *  android.media.AudioAttributes$Builder
 *  android.media.AudioFocusRequest
 *  android.media.AudioFocusRequest$Builder
 *  android.media.AudioManager
 *  android.media.AudioManager$OnAudioFocusChangeListener
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.provider.Settings$Secure
 *  android.service.notification.StatusBarNotification
 *  android.speech.tts.SynthesisCallback
 *  android.speech.tts.SynthesisRequest
 *  android.speech.tts.TextToSpeech
 *  android.speech.tts.TextToSpeech$OnInitListener
 *  android.speech.tts.TextToSpeechService
 *  android.speech.tts.UtteranceProgressListener
 *  android.speech.tts.Voice
 *  android.util.Log
 */
package com.vnspeak.autotts;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;
import c0.k;
import c3.d0;
import c3.e0;
import c3.m;
import c3.t;
import c3.w;
import c3.x;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.vnspeak.autotts.clsCLD2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AutoTtsService
extends TextToSpeechService {
    public static volatile String C;
    public static volatile String D;
    public static volatile int E;
    public static volatile int F;
    public static volatile int G;
    public static volatile String H;
    public static volatile String I;
    public static final ArrayList J;
    public static int K;
    public static volatile int L;
    public static volatile ArrayList M;
    public static boolean N;
    public static boolean O;
    public static boolean P;
    public static boolean Q;
    public static boolean R;
    public static boolean S;
    public static volatile ArrayList T;
    public static TextToSpeech U;
    public static int V;
    public static int W;
    public static volatile String X;
    public static String Y;
    public static final byte[] Z;
    public static int a0;
    public final int A;
    public int B = 2;
    public volatile String c = "";
    public volatile int d = -1;
    public volatile int e = -1;
    public final ArrayList f = new ArrayList();
    public Context g;
    public int h;
    public volatile Bundle i;
    public float j;
    public int k;
    public int l;
    public boolean m = false;
    public final Object n = new Object();
    public volatile boolean o = false;
    public volatile boolean p = false;
    public AudioManager q;
    public AudioFocusRequest r;
    public long s = 0L;
    public long t = 0L;
    public LicenseCheckerCallback u;
    public LicenseChecker v = null;
    public final int w;
    public final int x;
    public final int y;
    public final int z;

    static {
        J = new ArrayList();
        K = 0;
        M = null;
        N = false;
        O = false;
        P = false;
        Q = false;
        R = false;
        S = false;
        T = new ArrayList();
        V = 0;
        W = -1;
        X = "";
        Y = "";
        Z = new byte[]{-45, 64, 37, -10, -72, -47, 64, -63, 102, 86, -35, -85, 78, -15, -26, -113, -54, 36, -74, 35};
        a0 = -1;
    }

    public AutoTtsService() {
        this.w = 0;
        this.x = 1;
        this.y = 2;
        this.z = 3;
        this.A = -1;
    }

    public static /* synthetic */ int A() {
        int n3 = K;
        K = n3 + 1;
        return n3;
    }

    public static /* synthetic */ int B(AutoTtsService autoTtsService, String string, String string2, String string3) {
        return autoTtsService.T(string, string2, string3);
    }

    public static /* synthetic */ void a(int n3) {
        if (n3 != -2) {
            if (n3 != -1) {
                if (n3 != 1) {
                    return;
                }
                c3.k.a.c("TTS", "Audio focus gained");
                return;
            }
            c3.k.a.c("TTS", "Audio focus lost");
            return;
        }
        c3.k.a.c("TTS", "Audio focus lost temporarily");
    }

    public static /* synthetic */ int c(AutoTtsService autoTtsService) {
        int n3 = autoTtsService.h;
        autoTtsService.h = n3 + 1;
        return n3;
    }

    public static /* synthetic */ int f(int n3) {
        a0 = n3;
        return n3;
    }

    public static /* synthetic */ int g() {
        return W;
    }

    public static /* synthetic */ String h(AutoTtsService autoTtsService, String string) {
        return autoTtsService.H(string);
    }

    public static /* synthetic */ void i(AutoTtsService autoTtsService, SynthesisCallback synthesisCallback, int n3) {
        autoTtsService.F(synthesisCallback, n3);
    }

    public static /* synthetic */ int j(AutoTtsService autoTtsService, String string) {
        return autoTtsService.J(string);
    }

    public static /* synthetic */ int k(AutoTtsService autoTtsService, String string) {
        return autoTtsService.L(string);
    }

    public static /* synthetic */ int l(AutoTtsService autoTtsService, String string) {
        return autoTtsService.I(string);
    }

    public static /* synthetic */ int m(AutoTtsService autoTtsService) {
        return autoTtsService.k;
    }

    public static /* synthetic */ int o(AutoTtsService autoTtsService) {
        return autoTtsService.l;
    }

    public static /* synthetic */ int p(AutoTtsService autoTtsService) {
        return autoTtsService.d;
    }

    public static /* synthetic */ Bundle q(AutoTtsService autoTtsService) {
        return autoTtsService.i;
    }

    public static /* synthetic */ float r(AutoTtsService autoTtsService) {
        return autoTtsService.j;
    }

    public static /* synthetic */ String s() {
        return X;
    }

    public static /* synthetic */ TextToSpeech v(TextToSpeech textToSpeech) {
        U = textToSpeech;
        return textToSpeech;
    }

    public static /* synthetic */ int w(AutoTtsService autoTtsService, int n3) {
        autoTtsService.e = n3;
        return n3;
    }

    public static /* synthetic */ int z() {
        return K;
    }

    public final void C() {
        LicenseChecker licenseChecker = this.v;
        if (licenseChecker != null) {
            licenseChecker.f(this.u);
        }
    }

    public final Notification D() {
        return new k((Context)this, "tts_channel").d("Auto TTS active").g(17301540).f(true).a();
    }

    public final void E() {
        NotificationChannel notificationChannel = new NotificationChannel("tts_channel", (CharSequence)"TTS Engine", 2);
        ((NotificationManager)this.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
    }

    public final void F(SynthesisCallback synthesisCallback, int n3) {
        if (!synthesisCallback.hasStarted()) {
            synthesisCallback.start(16000, 2, 1);
        }
        if (!synthesisCallback.hasFinished()) {
            synthesisCallback.done();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void G(SynthesisCallback synthesisCallback, int n3) {
        Object object = c3.k.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("endSynthesis #");
        stringBuilder.append(n3);
        ((m)object).c("AutoTTS", stringBuilder.toString());
        object = this.n;
        // MONITORENTER : object
        this.o = true;
        this.n.notifyAll();
        // MONITOREXIT : object
        if (!synthesisCallback.hasStarted()) return;
        if (synthesisCallback.hasFinished()) return;
        synthesisCallback.done();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final String H(String charSequence) {
        Object object = c3.k.a;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("getEngine4Language ");
        ((StringBuilder)object2).append((String)charSequence);
        ((m)object).c("AutoTTS", ((StringBuilder)object2).toString());
        if (L == 3) {
            return "com.google.android.tts";
        }
        object = c3.k.c;
        synchronized (object) {
            try {
                for (int i3 = 0; i3 < (object2 = c3.k.c).size(); ++i3) {
                    m m3 = c3.k.a;
                    Object object3 = new StringBuilder();
                    ((StringBuilder)object3).append("- ");
                    ((StringBuilder)object3).append(((c3.d)object2.get((int)i3)).b);
                    ((StringBuilder)object3).append(" ");
                    ((StringBuilder)object3).append(((c3.d)object2.get((int)i3)).f);
                    m3.c("AutoTTS", ((StringBuilder)object3).toString());
                    if (!((String)charSequence).equals(((c3.d)object2.get((int)i3)).b)) continue;
                    if (((c3.d)object2.get((int)i3)).i) {
                        c3.k.a.c("AutoTTS", " res1 Disable");
                        return "Disable";
                    }
                    object3 = c3.k.a;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(" res ");
                    ((StringBuilder)charSequence).append(((c3.d)object2.get((int)i3)).f);
                    ((m)object3).c("AutoTTS", ((StringBuilder)charSequence).toString());
                    return ((c3.d)object2.get((int)i3)).f;
                }
                // MONITOREXIT @DISABLED, blocks:[0, 3] lbl38 : MonitorExitStatement: MONITOREXIT : var3_3
                c3.k.a.c("AutoTTS", " res ''");
                return "";
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final int I(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c3.k.c).size(); ++i3) {
            if (!((c3.d)list.get((int)i3)).b.equals(string)) continue;
            return ((c3.d)list.get((int)i3)).e;
        }
        return 100;
    }

    public final int J(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c3.k.c).size(); ++i3) {
            if (!((c3.d)list.get((int)i3)).b.equals(string)) continue;
            return ((c3.d)list.get((int)i3)).c;
        }
        return 100;
    }

    public final String K(String string) {
        List list;
        if (L == 3) {
            return string;
        }
        for (int i3 = 0; i3 < (list = c3.k.c).size(); ++i3) {
            if (!string.equals(((c3.d)list.get((int)i3)).b)) continue;
            return ((c3.d)list.get((int)i3)).g;
        }
        return "";
    }

    public final int L(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c3.k.c).size(); ++i3) {
            if (!((c3.d)list.get((int)i3)).b.equals(string)) continue;
            return ((c3.d)list.get((int)i3)).d;
        }
        return 100;
    }

    public final boolean M() {
        if (Build.VERSION.SDK_INT >= 33) {
            return this.checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0;
        }
        return true;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void N() {
        synchronized (this) {
            try {
                c3.k.a.c("AutoTTS", "initAllTTS");
                int n3 = 0;
                while (true) {
                    int n4;
                    if (n3 < (n4 = this.f.size())) {
                        ((d0)this.f.get(n3)).k();
                        ((d0)this.f.get(n3)).j();
                    }
                    this.f.clear();
                    this.h = 0;
                    if (!M.isEmpty()) {
                        ArrayList arrayList = this.f;
                        Object object = new d0((String)M.get(this.h));
                        arrayList.add(object);
                        Context context = this.getApplicationContext();
                        object = new c(this, null);
                        arrayList = new TextToSpeech(context, (TextToSpeech.OnInitListener)object, (String)M.get(this.h));
                        U = arrayList;
                    }
                    return;
                    catch (Exception exception) {}
                    ++n3;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final void O() {
        String string = Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id");
        this.u = new b(this, null);
        this.v = new LicenseChecker((Context)this, new ServerManagedPolicy((Context)this, new AESObfuscator(Z, this.getPackageName(), string)), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzCqD0VjR3RQYVN1f5hIVDWBBoomRgzjbHqW3g5v59YfVwTkmM4hWXvyHEXBHcE7Wcbl8Tlic9LIH0HStl7KN+Erx4mUlk8jqsPGeDC9r2f2VLKYGKm6lb5Lvjw8aNfS6auzJlFN12/NBMEBPb1wstV2B1gUaDNT/63Zz0arO6XbjFM9WAHpo54BFQoWk/vRK95G88xlWoUX3QGum0AouPMj8vKiYaBGzFjnXMTdRH70bYPY5pPmF710ox3vv/SSiM78BT/Ez1V7rshx3fL9ZjrxbmrO8YYbqtzvGu91+y0viRkLvJozU5dy5zHp147UEaX3rDnyxFBhGngO1ng3hQIDAQAB");
        this.C();
    }

    public final boolean P(Locale object, Locale object2) {
        if (object2 != null && object != null) {
            String string = ((Locale)object).getISO3Language();
            String string2 = ((Locale)object).getISO3Country();
            String string3 = ((Locale)object).getVariant();
            String string4 = ((Locale)object2).getISO3Language();
            object = ((Locale)object2).getISO3Country();
            object2 = ((Locale)object2).getVariant();
            if (string.equals(string4)) {
                if (string2.isEmpty()) {
                    return true;
                }
                if (string2.equals(object)) {
                    if (string3.isEmpty()) {
                        return true;
                    }
                    return string3.equals(object2);
                }
            }
        }
        return false;
    }

    public final boolean Q() {
        StatusBarNotification[] statusBarNotificationArray = ((NotificationManager)this.getSystemService(NotificationManager.class)).getActiveNotifications();
        int n3 = statusBarNotificationArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (statusBarNotificationArray[i3].getId() != 136549) continue;
            return true;
        }
        return false;
    }

    public final void R() {
        if (C != null) {
            return;
        }
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
        C = sharedPreferences.getString("auto_mode_language", "");
        if (C.isEmpty()) {
            C = c3.k.f(Locale.getDefault());
        }
        if ((H = sharedPreferences.getString("mixed_mode_latin_language", "")).isEmpty()) {
            H = c3.k.f(Locale.getDefault());
        }
        if ((I = sharedPreferences.getString("mixed_mode_non_latin_language", "")).isEmpty()) {
            I = c3.k.f(Locale.getDefault());
        }
        if ((D = sharedPreferences.getString("dual_mode_language", "")).isEmpty()) {
            D = c3.k.f(Locale.getDefault());
        }
        E = sharedPreferences.getInt("number_mode_language", 0);
        F = sharedPreferences.getInt("punc_mode_language", 0);
        G = sharedPreferences.getInt("emoji_mode_language", 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void S() {
        synchronized (this) {
            try {
                SharedPreferences sharedPreferences;
                M = sharedPreferences = new ArrayList();
                String string = this.H(c3.k.f(Locale.getDefault()));
                sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    CharSequence charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("engine_");
                    ((StringBuilder)charSequence).append(n3);
                    charSequence = sharedPreferences.getString(((StringBuilder)charSequence).toString(), "");
                    if (((String)charSequence).isEmpty() || ((String)charSequence).equals("end")) break;
                    if (!((String)charSequence).equals(string)) {
                        M.add(charSequence);
                    }
                    ++n3;
                }
                if (!string.isEmpty() && !string.equals("Disable")) {
                    M.add(0, string);
                }
                if (M.isEmpty() && c3.t.a(this.g)) {
                    M.add("com.google.android.tts");
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final int T(String object, String object2, String object3) {
        Object object4;
        Object object5 = c3.k.a;
        CharSequence charSequence = new StringBuilder();
        charSequence.append("loadLanguage ");
        charSequence.append((String)object);
        charSequence.append(" ");
        charSequence.append((String)object2);
        charSequence.append(" ");
        charSequence.append((String)object3);
        ((m)object5).c("AutoTTS", charSequence.toString());
        int n3 = this.onIsLanguageAvailable((String)object, (String)object2, (String)object3);
        object5 = c3.k.a;
        charSequence = new StringBuilder();
        charSequence.append(" isLanguageAvailable = ");
        charSequence.append(n3);
        ((m)object5).c("AutoTTS", charSequence.toString());
        if (((String)object3).contains("autotts.") && n3 == 2) {
            object5 = new StringBuilder();
            ((StringBuilder)object5).append((String)object);
            ((StringBuilder)object5).append("_");
            ((StringBuilder)object5).append((String)object2);
            object5 = ((StringBuilder)object5).toString();
            object3 = ((String)object3).substring(8);
            charSequence = "";
        } else {
            object5 = this.K((String)object);
            object4 = this.H((String)object);
            charSequence = object3;
            object3 = object4;
        }
        object4 = object5;
        object5 = object3;
        if (((String)object3).isEmpty()) {
            object4 = this.K(C);
            object5 = this.H(C);
        }
        m m3 = c3.k.a;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("engine: ");
        ((StringBuilder)object3).append((String)object5);
        ((StringBuilder)object3).append(" voice ");
        ((StringBuilder)object3).append((String)object4);
        m3.c("AutoTTS", ((StringBuilder)object3).toString());
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    return n3;
                }
                object3 = this.Z((String)object4);
                if (object3 == null) {
                    return -2;
                }
                if (this.c.equals(object5) && c3.k.f((Locale)object3).equals(object) && ((String)object2).equals(c3.k.e((Locale)object3)) && ((Locale)object3).getVariant().equals(charSequence)) {
                    this.V((String)object5, (Locale)object3, (String)charSequence, O);
                    return n3;
                }
                if (!this.c.equals(object5) && c3.k.f((Locale)object3).equals(object) && ((String)object2).equals(c3.k.e((Locale)object3)) && ((Locale)object3).getVariant().equals(charSequence)) {
                    this.V((String)object5, (Locale)object3, (String)charSequence, O);
                    return n3;
                }
                object = new Locale((String)object, (String)object2, (String)charSequence);
                this.V(this.c0((Locale)object), (Locale)object, (String)charSequence, O);
                return n3;
            }
            object3 = this.Z((String)object4);
            if (object3 == null) {
                return -2;
            }
            if (this.c.equals(object5) && c3.k.f((Locale)object3).equals(object) && ((String)object2).equals(c3.k.e((Locale)object3))) {
                this.V("", (Locale)object3, (String)charSequence, O);
                return n3;
            }
            if (!this.c.equals(object5) && c3.k.f((Locale)object3).equals(object) && ((String)object2).equals(c3.k.e((Locale)object3))) {
                this.V((String)object5, (Locale)object3, (String)charSequence, O);
                return n3;
            }
            object = new Locale((String)object, (String)object2, "");
            this.V(this.c0((Locale)object), (Locale)object, (String)charSequence, O);
            return n3;
        }
        object2 = this.Z((String)object4);
        if (object2 == null) {
            return -2;
        }
        if (this.c.equals(object5) && c3.k.f((Locale)object2).equals(object)) {
            this.V((String)object5, (Locale)object2, (String)charSequence, O);
            return n3;
        }
        if (!this.c.equals(object5) && c3.k.f((Locale)object2).equals(object)) {
            this.V((String)object5, (Locale)object2, (String)charSequence, O);
            this.c = object5;
            return n3;
        }
        object = new Locale((String)object, "", "");
        this.V(this.c0((Locale)object), (Locale)object, (String)charSequence, O);
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void U() {
        synchronized (this) {
            Object object;
            String[] stringArray;
            String string;
            int n3;
            int n4;
            int n5;
            Object object2;
            String string2;
            Object object3;
            int n6;
            SharedPreferences sharedPreferences;
            try {
                c3.k.a.c("AutoTTS", "loadLanguages");
                c3.k.c.clear();
                c3.k.f.clear();
                sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                n6 = 0;
                while (true) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("language_");
                    ((StringBuilder)object3).append(n6);
                    string2 = sharedPreferences.getString(((StringBuilder)object3).toString(), "");
                    object3 = c3.k.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" - ");
                    ((StringBuilder)object2).append(string2);
                    ((m)object3).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (string2.isEmpty()) {
                        object2 = c3.k.a;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Enabled languages 2 letters: ");
                        ((StringBuilder)object3).append(c3.k.f.toString());
                        ((m)object2).c("AutoTTS", ((StringBuilder)object3).toString());
                        return;
                    }
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(string2);
                    ((StringBuilder)object3).append("_speed");
                    n5 = sharedPreferences.getInt(((StringBuilder)object3).toString(), 100);
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(string2);
                    ((StringBuilder)object3).append("_pitch");
                    n4 = sharedPreferences.getInt(((StringBuilder)object3).toString(), 100);
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(string2);
                    ((StringBuilder)object3).append("_volume");
                    n3 = sharedPreferences.getInt(((StringBuilder)object3).toString(), 100);
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(string2);
                    ((StringBuilder)object3).append("_variant");
                    string = sharedPreferences.getString(((StringBuilder)object3).toString(), "*Default");
                    stringArray = sharedPreferences.getString(string2, "");
                    object = "";
                    String string3 = "";
                    object2 = object;
                    object3 = string3;
                    if (stringArray.isEmpty()) break block9;
                    stringArray = stringArray.split("#");
                    object2 = object;
                    object3 = string3;
                    if (stringArray.length < 2) break block9;
                    break;
                }
            }
            catch (Throwable throwable) {}
            {
                block9: {
                    object2 = stringArray[0];
                    object3 = stringArray[1];
                }
                object = new c3.d("", string2, n5, n3, n4, (String)object2, (String)object3, string);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(((c3.d)object).b);
                ((StringBuilder)object3).append("_disabled");
                ((c3.d)object).i = sharedPreferences.getBoolean(((StringBuilder)object3).toString(), false);
                c3.k.c.add(object);
                if (!((c3.d)object).i) {
                    object = (String)c3.k.i.get(string2);
                    object2 = c3.k.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" -not disabled: ");
                    ((StringBuilder)object3).append((String)object);
                    ((m)object2).c("AutoTTS", ((StringBuilder)object3).toString());
                    if (object != null) {
                        c3.k.f.add(object);
                    }
                }
                ++n6;
                continue;
            }
            throw throwable;
        }
    }

    public final void V(String object, Locale locale, String string, boolean bl) {
        int n3;
        String string2;
        Object object22;
        Iterator iterator;
        Object object3;
        block18: {
            object3 = object;
            iterator = c3.k.a;
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("LoadVoice ");
            ((StringBuilder)object22).append((String)object3);
            ((StringBuilder)object22).append(" ");
            ((StringBuilder)object22).append(locale.toString());
            ((StringBuilder)object22).append(" ");
            ((StringBuilder)object22).append(string);
            ((m)((Object)iterator)).c("AutoTTS", ((StringBuilder)object22).toString());
            if (bl && L != 3) {
                this.X((String)object, locale, string, bl);
                return;
            }
            if (((String)object3).isEmpty()) {
                object3 = this.c;
            } else {
                this.c = object3;
            }
            object22 = "";
            string2 = ((String)object3).replace("-", "").replace("_", "");
            object = c3.k.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(" current engine: ");
            ((StringBuilder)object3).append(string2);
            ((m)object).c("AutoTTS", ((StringBuilder)object3).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((d0)this.f.get(n3)).e().equals(string2) || ((d0)this.f.get(n3)).f() != 2) continue;
                c3.k.a.c("AutoTTS", " found!");
                break block18;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4;
            Object object4;
            this.d = n3;
            if (string.isEmpty() && !((d0)this.f.get((int)n3)).e.isEmpty()) {
                c3.k.a.c("AutoTTS", "Load voice original");
                this.W(string2, locale);
                return;
            }
            iterator = new Locale("zxx");
            object3 = object22;
            object = iterator;
            if (!((d0)this.f.get((int)n3)).e.isEmpty()) {
                object4 = ((d0)this.f.get(n3)).g().getVoice();
                object3 = object22;
                object = iterator;
                if (object4 != null) {
                    object = object4.getLocale();
                    object3 = object4.getName();
                    iterator = c3.k.a;
                    object22 = new StringBuilder();
                    ((StringBuilder)object22).append(" last ");
                    ((StringBuilder)object22).append(object);
                    ((StringBuilder)object22).append(" ");
                    ((StringBuilder)object22).append((String)object3);
                    ((m)((Object)iterator)).c("AutoTTS", ((StringBuilder)object22).toString());
                    if (c3.k.f((Locale)object).equals(c3.k.f(locale)) && (c3.k.e((Locale)object).equals(c3.k.e(locale)) || c3.k.e(locale).equals("")) && ((String)object3).equals(string)) {
                        c3.k.a.c("AutoTTS", " Do nothing!");
                        return;
                    }
                }
            }
            iterator = T;
            int n5 = ((ArrayList)((Object)iterator)).size();
            for (n4 = 0; n4 < n5; ++n4) {
                object22 = ((ArrayList)((Object)iterator)).get(n4);
                object4 = ((String)(object22 = (String)object22)).split("#");
                if (((String[])object4).length < 2 || !object4[0].equals(string2) || !object4[1].equals(locale.toString())) continue;
                object4 = c3.k.a;
                iterator = new StringBuilder();
                ((StringBuilder)((Object)iterator)).append("voice: ");
                ((StringBuilder)((Object)iterator)).append((String)object22);
                ((m)object4).c("AutoTTS", ((StringBuilder)((Object)iterator)).toString());
                for (Object object22 : c3.k.c) {
                    if (!((c3.d)object22).f.equals(string2) || !((c3.d)object22).g.equals(locale.toString())) continue;
                    string = ((c3.d)object22).h;
                    break;
                }
                break;
            }
            object22 = c3.k.a;
            iterator = new StringBuilder();
            ((StringBuilder)((Object)iterator)).append(" variant ");
            ((StringBuilder)((Object)iterator)).append(string);
            ((m)object22).c("AutoTTS", ((StringBuilder)((Object)iterator)).toString());
            if (string.equals("*Default") && !this.P(locale, (Locale)object)) {
                object22 = c3.k.a;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(locale.toString());
                ((StringBuilder)object3).append(" vs ");
                ((StringBuilder)object3).append(((Locale)object).toString());
                ((m)object22).c("AutoTTS", ((StringBuilder)object3).toString());
                ((d0)this.f.get(this.d)).g().setLanguage(locale);
                ((d0)this.f.get((int)this.d)).f = true;
                c3.k.a.c("AutoTTS", "Set voice 1");
            } else if (!string.equals("*Default") && !string.equals(object3)) {
                c3.k.a.c("AutoTTS", "Check voice 1");
                object3 = ((d0)this.f.get(this.d)).g().getVoices();
                if (object3 != null) {
                    object22 = object3.iterator();
                    while (object22.hasNext()) {
                        object3 = (Voice)object22.next();
                        if (!object3.getName().equalsIgnoreCase(string)) continue;
                        n4 = ((d0)this.f.get(this.d)).g().setVoice((Voice)object3);
                        ((d0)this.f.get((int)this.d)).f = true;
                        object22 = c3.k.a;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Set voice 2: ");
                        ((StringBuilder)object).append(object3.getName());
                        ((StringBuilder)object).append(" res=");
                        ((StringBuilder)object).append(n4);
                        ((m)object22).c("AutoTTS", ((StringBuilder)object).toString());
                        break;
                    }
                } else if (!this.P(locale, (Locale)object)) {
                    ((d0)this.f.get(this.d)).g().setLanguage(locale);
                    ((d0)this.f.get((int)this.d)).f = true;
                    c3.k.a.c("AutoTTS", "Set voice 3");
                }
            }
            ((d0)this.f.get((int)n3)).d = locale;
            ((d0)this.f.get((int)n3)).e = string;
            return;
        }
        c3.k.a.d("AutoTTS", "TTS is not ready");
        this.d = -1;
    }

    public final void W(String object, Locale locale) {
        int n3;
        block6: {
            if (((String)object).isEmpty()) {
                object = this.c;
            } else {
                this.c = object;
            }
            object = ((String)object).replace("-", "").replace("_", "");
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((d0)this.f.get(n3)).e().equals(object) || ((d0)this.f.get(n3)).f() != 2) {
                    continue;
                }
                break block6;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            this.d = n3;
            object = ((d0)this.f.get((int)n3)).d;
            if (c3.k.f((Locale)object).equals(c3.k.f(locale)) && (c3.k.e((Locale)object).equals(c3.k.e(locale)) || c3.k.e(locale).isEmpty())) {
                return;
            }
            ((d0)this.f.get(this.d)).g().setLanguage(locale);
            ((d0)this.f.get((int)this.d)).f = true;
            ((d0)this.f.get((int)n3)).d = locale;
            ((d0)this.f.get((int)n3)).e = "";
            return;
        }
        this.d = -1;
    }

    public final void X(String object, Locale serializable, String charSequence, boolean bl) {
        int n3;
        CharSequence charSequence2;
        Object object2;
        Object object3;
        block16: {
            object3 = c3.k.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("loadVoice_Secondary ");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(((Locale)serializable).toString());
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append((String)charSequence);
            ((m)object3).c("AutoTTS", ((StringBuilder)object2).toString());
            object3 = c3.k.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Current Engine ");
            ((StringBuilder)object2).append(this.c);
            ((m)object3).c("AutoTTS", ((StringBuilder)object2).toString());
            if (((String)object).isEmpty()) {
                object = this.c;
            } else {
                this.c = object;
            }
            object3 = "";
            charSequence2 = ((String)object).replace("-", "").replace("_", "");
            object2 = c3.k.a;
            object = new StringBuilder();
            ((StringBuilder)object).append(" current engine: ");
            ((StringBuilder)object).append((String)charSequence2);
            ((m)object2).c("AutoTTS", ((StringBuilder)object).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((d0)this.f.get(n3)).e().equals(charSequence2) || ((d0)this.f.get(n3)).f() != 2) continue;
                c3.k.a.c("AutoTTS", " found!");
                break block16;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4 = this.d;
            this.d = n3;
            if (!bl || !((d0)this.f.get((int)this.d)).f) {
                Object object4;
                Object object5;
                object = new Locale("zxx");
                object2 = ((d0)this.f.get(n3)).g().getVoice();
                if (object2 != null) {
                    object = object2.getLocale();
                    object3 = object2.getName();
                }
                if (n4 == this.d && object2 != null) {
                    object5 = c3.k.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(" Engine Variant ");
                    ((StringBuilder)object4).append((String)object3);
                    ((m)object5).c("AutoTTS", ((StringBuilder)object4).toString());
                    object5 = c3.k.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(" Engine Locale ");
                    ((StringBuilder)object4).append(((Locale)object).toString());
                    ((m)object5).c("AutoTTS", ((StringBuilder)object4).toString());
                    if (this.P((Locale)serializable, (Locale)object) && (((String)object3).equals(charSequence) || ((String)charSequence).equals("*Default") || ((String)charSequence).isEmpty())) {
                        c3.k.a.c("AutoTTS", " *0 Do nothing");
                        return;
                    }
                }
                object4 = c3.k.a;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("Searching ");
                ((StringBuilder)object5).append((String)charSequence2);
                ((StringBuilder)object5).append(" ");
                ((StringBuilder)object5).append(((Locale)serializable).toString());
                ((m)object4).c("AutoTTS", ((StringBuilder)object5).toString());
                object5 = T;
                n4 = ((ArrayList)object5).size();
                for (n3 = 0; n3 < n4; ++n3) {
                    object4 = ((ArrayList)object5).get(n3);
                    Object object62 = (String)object4;
                    m m3 = c3.k.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(" *");
                    ((StringBuilder)object4).append((String)object62);
                    m3.c("AutoTTS", ((StringBuilder)object4).toString());
                    object4 = ((String)object62).split("#");
                    if (((String[])object4).length < 2 || !object4[0].equals(charSequence2)) continue;
                    for (Object object62 : c3.k.c) {
                        object5 = c3.k.a;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("  -");
                        ((StringBuilder)object4).append(((c3.d)object62).f);
                        ((StringBuilder)object4).append(" ");
                        ((StringBuilder)object4).append(((c3.d)object62).g);
                        ((StringBuilder)object4).append(" ");
                        ((StringBuilder)object4).append(((c3.d)object62).h);
                        ((m)object5).c("AutoTTS", ((StringBuilder)object4).toString());
                        if (!((c3.d)object62).f.equals(charSequence2) || !this.P((Locale)serializable, this.Z(((c3.d)object62).g)) || ((c3.d)object62).h.isEmpty()) continue;
                        charSequence = ((c3.d)object62).h;
                        break;
                    }
                    break;
                }
                object5 = c3.k.a;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(" Variant ");
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((m)object5).c("AutoTTS", ((StringBuilder)charSequence2).toString());
                object5 = c3.k.a;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(" Locale ");
                ((StringBuilder)charSequence2).append(((Locale)serializable).toString());
                ((m)object5).c("AutoTTS", ((StringBuilder)charSequence2).toString());
                if ((((String)charSequence).equals("*Default") || ((String)charSequence).isEmpty()) && !this.P((Locale)serializable, (Locale)object)) {
                    c3.k.a.c("AutoTTS", " *1");
                    object3 = c3.k.a;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(((Locale)serializable).toString());
                    ((StringBuilder)charSequence).append(" vs ");
                    ((StringBuilder)charSequence).append(((Locale)object).toString());
                    ((m)object3).c("AutoTTS", ((StringBuilder)charSequence).toString());
                    ((d0)this.f.get(this.d)).g().setLanguage((Locale)serializable);
                    ((d0)this.f.get((int)this.d)).f = true;
                    ((d0)this.f.get((int)this.d)).d = serializable;
                    ((d0)this.f.get((int)this.d)).e = ((Locale)serializable).getVariant();
                    return;
                }
                c3.k.a.c("AutoTTS", " *2");
                if (object2 != null) {
                    object2 = c3.k.a;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(" Engine Variant ");
                    ((StringBuilder)object5).append((String)object3);
                    ((m)object2).c("AutoTTS", ((StringBuilder)object5).toString());
                    object5 = c3.k.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" Engine Locale ");
                    ((StringBuilder)object2).append(((Locale)object).toString());
                    ((m)object5).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (this.P((Locale)serializable, (Locale)object) && (((String)object3).equals(charSequence) || ((String)charSequence).equals("*Default") || ((String)charSequence).isEmpty())) {
                        c3.k.a.c("AutoTTS", " *2.1 Do nothing");
                        return;
                    }
                }
                if ((object3 = ((d0)this.f.get(this.d)).g().getVoices()) != null) {
                    object2 = object3.iterator();
                    while (object2.hasNext()) {
                        object3 = (Voice)object2.next();
                        if (!object3.getName().equalsIgnoreCase((String)charSequence)) continue;
                        ((d0)this.f.get(this.d)).g().setVoice((Voice)object3);
                        object = c3.k.a;
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Set voice 2: ");
                        ((StringBuilder)serializable).append(object3.toString());
                        ((m)object).c("AutoTTS", ((StringBuilder)serializable).toString());
                        ((d0)this.f.get((int)this.d)).d = object3.getLocale();
                        ((d0)this.f.get((int)this.d)).e = charSequence;
                        ((d0)this.f.get((int)this.d)).f = true;
                        return;
                    }
                }
                if (!this.P((Locale)serializable, (Locale)object)) {
                    ((d0)this.f.get(this.d)).g().setLanguage((Locale)serializable);
                    ((d0)this.f.get((int)this.d)).d = serializable;
                    ((d0)this.f.get((int)this.d)).e = ((Locale)serializable).getVariant();
                    ((d0)this.f.get((int)this.d)).f = true;
                    c3.k.a.c("AutoTTS", "Set voice 3");
                }
            }
            return;
        }
        c3.k.a.d("AutoTTS", "TTS is not ready");
        this.d = -1;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Y() {
        synchronized (this) {
            block7: {
                boolean bl = T.isEmpty();
                if (bl) break block7;
                return;
            }
            try {
                c3.k.a.c("AutoTTS", "LoadVoices");
                SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    CharSequence charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("voice_");
                    ((StringBuilder)charSequence).append(n3);
                    charSequence = sharedPreferences.getString(((StringBuilder)charSequence).toString(), "");
                    m m3 = c3.k.a;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(" -");
                    stringBuilder.append((String)charSequence);
                    m3.c("AutoTTS", stringBuilder.toString());
                    if (((String)charSequence).isEmpty()) {
                        O = sharedPreferences.getBoolean("dedicated_engines", false);
                        return;
                    }
                    T.add(charSequence);
                    ++n3;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Locale Z(String object) {
        String[] stringArray;
        block3: {
            try {
                stringArray = ((String)object).split("_");
                int n3 = stringArray.length;
                if (n3 == 1) return new Locale(stringArray[0]);
                if (n3 == 2) return new Locale(stringArray[0], stringArray[1]);
                if (n3 == 3) break block3;
                return null;
            }
            catch (Exception exception) {
                return null;
            }
        }
        if (!stringArray[2].isEmpty()) return new Locale(stringArray[0], stringArray[1], stringArray[2]);
        return new Locale(stringArray[0], stringArray[1]);
    }

    public void a0() {
        this.q = (AudioManager)this.getSystemService("audio");
        Object object = new AudioAttributes.Builder().setUsage(11);
        boolean bl = true;
        object = object.setContentType(1).build();
        object = new AudioFocusRequest.Builder(1).setAudioAttributes((AudioAttributes)object).setOnAudioFocusChangeListener((AudioManager.OnAudioFocusChangeListener)new c3.b()).build();
        this.r = object;
        int n3 = this.q.requestAudioFocus((AudioFocusRequest)object);
        m m3 = c3.k.a;
        object = new StringBuilder();
        ((StringBuilder)object).append("Audio focus request: ");
        if (n3 != 1) {
            bl = false;
        }
        ((StringBuilder)object).append(bl);
        m3.c("TTS", ((StringBuilder)object).toString());
    }

    public final void b0(int n3, String string) {
        SharedPreferences.Editor editor = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0).edit();
        editor.putInt("license_status", n3);
        editor.putString("license_text", string);
        editor.commit();
    }

    public final String c0(Locale locale) {
        Object object;
        Object object2;
        int n3;
        for (n3 = 0; n3 < T.size(); ++n3) {
            object2 = ((String)T.get(n3)).split("#");
            if (((String[])object2).length != 2 && ((String[])object2).length != 3) continue;
            object = this.Z(object2[1]);
            if (object == null) {
                return "";
            }
            if (!c3.k.f(locale).equals(c3.k.f((Locale)object)) || !c3.k.e(locale).equals(c3.k.e((Locale)object)) || !locale.getVariant().equals(((Locale)object).getVariant())) continue;
            return object2[0];
        }
        for (n3 = 0; n3 < T.size(); ++n3) {
            object = ((String)T.get(n3)).split("#");
            if (((String[])object).length != 2 && ((String[])object).length != 3) continue;
            object2 = this.Z(object[1]);
            if (object2 == null) {
                return "";
            }
            if (!c3.k.f(locale).equals(c3.k.f((Locale)object2)) || !c3.k.e(locale).equals(c3.k.e((Locale)object2))) continue;
            return object[0];
        }
        for (n3 = 0; n3 < T.size(); ++n3) {
            object2 = ((String)T.get(n3)).split("#");
            if (((String[])object2).length != 2 && ((String[])object2).length != 3) continue;
            object = this.Z(object2[1]);
            if (object == null) {
                return "";
            }
            if (!c3.k.f(locale).equals(c3.k.f((Locale)object))) continue;
            return object2[0];
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void d0() {
        Exception exception2;
        block4: {
            block3: {
                try {
                    if (!this.M()) break block3;
                    this.E();
                    if (Build.VERSION.SDK_INT >= 34) {
                        c3.a.a(this, 136549, this.D(), 2);
                        return;
                    }
                }
                catch (Exception exception2) {
                    break block4;
                }
                this.startForeground(136549, this.D());
            }
            return;
        }
        c3.k.a.d("AutoTTS", exception2.getMessage());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void e0(Boolean object) {
        int n3;
        J.clear();
        Object object2 = c3.k.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("stopAllTts ");
        ((StringBuilder)object3).append(object);
        ((m)object2).c("AutoTTS", ((StringBuilder)object3).toString());
        boolean bl = (Boolean)object;
        if (bl) {
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (((d0)this.f.get(n3)).f() != 2 || !((d0)this.f.get((int)n3)).g || !((d0)this.f.get(n3)).g().isSpeaking()) continue;
                try {
                    object = c3.k.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" - calling stop for ");
                    ((StringBuilder)object3).append(((d0)this.f.get(this.d)).e());
                    ((m)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    object = c3.k.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("stop ");
                    ((StringBuilder)object3).append(X);
                    ((m)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    ((d0)this.f.get(n3)).k();
                    continue;
                }
                catch (Exception exception) {
                    object = c3.k.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Stop failed for ");
                    ((StringBuilder)object3).append(((d0)this.f.get(this.d)).e());
                    ((StringBuilder)object3).append("\n  ");
                    ((StringBuilder)object3).append(exception.getMessage());
                    ((m)object).d("AutoTTS", ((StringBuilder)object3).toString());
                }
            }
        } else {
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (((d0)this.f.get(n3)).f() != 2 || !((d0)this.f.get((int)n3)).g || !((d0)this.f.get(n3)).g().isSpeaking()) continue;
                try {
                    object = c3.k.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" - calling speak empty for ");
                    ((StringBuilder)object3).append(((d0)this.f.get(this.d)).e());
                    ((m)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    object3 = c3.k.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onSynthesizeText: ");
                    ((StringBuilder)object).append(X);
                    ((StringBuilder)object).append(" ''");
                    ((m)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    object3 = c3.k.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("speak ");
                    ((StringBuilder)object).append(X);
                    ((m)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    ((d0)this.f.get(this.d)).g().speak((CharSequence)"", 0, null, null);
                    continue;
                }
                catch (Exception exception) {
                    object3 = c3.k.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Speaking failed for ");
                    ((StringBuilder)object2).append(((d0)this.f.get(this.d)).e());
                    ((StringBuilder)object2).append("\n ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    ((m)object3).c("AutoTTS", ((StringBuilder)object2).toString());
                }
            }
        }
        object = this.n;
        synchronized (object) {
            this.o = true;
            this.n.notifyAll();
        }
        object = this.n;
        synchronized (object) {
            this.p = true;
            this.n.notifyAll();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate() {
        block4: {
            Exception exception2;
            block3: {
                block2: {
                    m m3;
                    c3.k.a = m3 = c3.m.f((Context)this);
                    m3.c("AutoTTS", "onCreate");
                    super.onCreate();
                    this.g = this;
                    try {
                        if (!R) break block2;
                        this.d0();
                    }
                    catch (Exception exception2) {
                        break block3;
                    }
                }
                this.a0();
                break block4;
            }
            Log.e((String)"AutoTTS", (String)exception2.getMessage());
        }
        W = !e0.d() ? e0.b(this.g) : 0;
        c3.k.c();
        this.S();
        this.Y();
        this.U();
        this.R();
        c3.k.p(this.getApplicationContext());
        c3.k.r(this.getApplicationContext());
        this.O();
        this.m = true;
        this.N();
    }

    public void onDestroy() {
        c3.k.a.c("AutoTTS", "onDestroy");
        try {
            this.stopForeground(1);
            this.q.abandonAudioFocusRequest(this.r);
        }
        catch (Exception exception) {
            c3.k.a.d("AutoTTS", exception.getMessage());
        }
        for (int i3 = 0; i3 < this.f.size(); ++i3) {
            if (((d0)this.f.get(i3)).f() != 2) continue;
            ((d0)this.f.get(i3)).j();
        }
        LicenseChecker licenseChecker = this.v;
        if (licenseChecker != null) {
            licenseChecker.m();
        }
        super.onDestroy();
    }

    public String onGetDefaultVoiceNameFor(String string, String object, String charSequence) {
        m m3 = c3.k.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onGetDefaultVoiceNameFor ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append((String)charSequence);
        m3.c("AutoTTS", stringBuilder.toString());
        object = c3.k.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -");
        ((StringBuilder)charSequence).append(string);
        ((m)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return string;
    }

    public String[] onGetLanguage() {
        c3.k.a.c("AutoTTS", "onGetLanguage");
        String string = c3.k.f(Locale.getDefault());
        m m3 = c3.k.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" -");
        stringBuilder.append(string);
        m3.c("AutoTTS", stringBuilder.toString());
        return new String[]{string};
    }

    public List onGetVoices() {
        c3.k.a.c("AutoTTS", "onGetVoices");
        ArrayList arrayList = c3.k.j(null, true);
        ArrayList<Voice> arrayList2 = new ArrayList<Voice>();
        for (int i3 = 0; i3 < arrayList.size(); ++i3) {
            arrayList2.add(new Voice((String)arrayList.get(i3), new Locale((String)arrayList.get(i3)), 400, 100, false, new HashSet()));
        }
        return arrayList2;
    }

    public int onIsLanguageAvailable(String object, String charSequence, String string) {
        m m3 = c3.k.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onIsLanguageAvailable: ");
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" ");
        stringBuilder.append(string);
        m3.c("AutoTTS", stringBuilder.toString());
        int n3 = c3.k.j(null, true).contains(object) ? 0 : -2;
        object = c3.k.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -res: ");
        ((StringBuilder)charSequence).append(n3);
        ((m)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return n3;
    }

    public int onIsValidVoiceName(String object) {
        Object object2 = c3.k.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onIsValidVoiceName ");
        stringBuilder.append((String)object);
        ((m)object2).c("AutoTTS", stringBuilder.toString());
        int n3 = c3.k.j(null, true).contains(object) ? 0 : -1;
        object = c3.k.a;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" -res ");
        ((StringBuilder)object2).append(n3);
        ((m)object).c("AutoTTS", ((StringBuilder)object2).toString());
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int onLoadLanguage(String string, String string2, String string3) {
        synchronized (this) {
            m m3 = c3.k.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onLoadLanguage: ");
            stringBuilder.append(string);
            stringBuilder.append(" ");
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(string3);
            m3.c("AutoTTS", stringBuilder.toString());
            return this.T(string, string2, string3);
        }
    }

    public int onLoadVoice(String object) {
        m m3 = c3.k.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onLoadVoice ");
        stringBuilder.append((String)object);
        m3.c("AutoTTS", stringBuilder.toString());
        Y = object;
        object = this.Z((String)object);
        if (object == null) {
            c3.k.a.c("AutoTTS", " -error");
            return -1;
        }
        int n3 = this.onLoadLanguage(c3.k.f((Locale)object), c3.k.e((Locale)object), ((Locale)object).getVariant());
        if (n3 != 0 && n3 != 1 && n3 != 2) {
            c3.k.a.c("AutoTTS", " -error");
            return -1;
        }
        c3.k.a.c("AutoTTS", " -success");
        return 0;
    }

    public int onStartCommand(Intent intent, int n3, int n4) {
        return 1;
    }

    public void onStop() {
        c3.k.a.c("AutoTTS", "onStop calling!!!");
        this.e0(Boolean.TRUE);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onSynthesizeText(SynthesisRequest object, SynthesisCallback synthesisCallback) {
        block105: {
            block104: {
                int n3;
                Object object2;
                float f3;
                Object object3;
                Object object4;
                block93: {
                    Object object5;
                    Object object6;
                    block102: {
                        block103: {
                            block95: {
                                Throwable throwable42;
                                block91: {
                                    block87: {
                                        block101: {
                                            block97: {
                                                block100: {
                                                    block98: {
                                                        block99: {
                                                            Object object7;
                                                            block96: {
                                                                block88: {
                                                                    block89: {
                                                                        block90: {
                                                                            block94: {
                                                                                Throwable throwable222;
                                                                                block86: {
                                                                                    block84: {
                                                                                        block85: {
                                                                                            block92: {
                                                                                                Throwable throwable322;
                                                                                                block83: {
                                                                                                    block82: {
                                                                                                        Object object8;
                                                                                                        c3.k.a.c("AutoTTS", "\n-------------------------------\nonSynthesizeText");
                                                                                                        if (R && !this.Q()) {
                                                                                                            this.d0();
                                                                                                        } else if (!R && this.Q()) {
                                                                                                            this.stopForeground(1);
                                                                                                        }
                                                                                                        object4 = this.n;
                                                                                                        // MONITORENTER : object4
                                                                                                        this.o = false;
                                                                                                        this.n.notifyAll();
                                                                                                        // MONITOREXIT : object4
                                                                                                        object4 = this.n;
                                                                                                        // MONITORENTER : object4
                                                                                                        this.p = false;
                                                                                                        this.n.notifyAll();
                                                                                                        // MONITOREXIT : object4
                                                                                                        object7 = object.getCharSequenceText();
                                                                                                        object4 = object7.toString();
                                                                                                        object3 = object.getLanguage();
                                                                                                        this.k = object.getSpeechRate();
                                                                                                        this.l = object.getPitch();
                                                                                                        this.i = object.getParams();
                                                                                                        this.j = f3 = this.i.getFloat("volume");
                                                                                                        if ((double)f3 == 0.0) {
                                                                                                            this.j = 1.0f;
                                                                                                        }
                                                                                                        X = this.i.getString("utteranceId");
                                                                                                        if (((String)(object4 = ((String)object4).trim())).isEmpty()) {
                                                                                                            c3.k.a.c("AutoTTS", "Speak text is empty");
                                                                                                            this.e0(Boolean.FALSE);
                                                                                                            this.F(synthesisCallback, 1);
                                                                                                            return;
                                                                                                        }
                                                                                                        object6 = c3.k.a;
                                                                                                        object2 = new StringBuilder();
                                                                                                        ((StringBuilder)object2).append("Speak: ");
                                                                                                        ((StringBuilder)object2).append((String)object4);
                                                                                                        ((StringBuilder)object2).append(" id: ");
                                                                                                        ((StringBuilder)object2).append(X);
                                                                                                        ((m)object6).c("AutoTTS", ((StringBuilder)object2).toString());
                                                                                                        if (a0 != 1) {
                                                                                                            V = n3 = V + 1;
                                                                                                            if (n3 > 100000) {
                                                                                                                V = 0;
                                                                                                            }
                                                                                                            if (V % 500 == 0) {
                                                                                                                this.C();
                                                                                                            }
                                                                                                        }
                                                                                                        object5 = "";
                                                                                                        object6 = "";
                                                                                                        object2 = "";
                                                                                                        if (((String)object4).length() >= 9 && ((String)object4).substring(0, 9).equals("[AutoTTS:") && ((String[])(object8 = ((String)object4).split("]"))).length == 2) {
                                                                                                            object4 = object8[1];
                                                                                                            if (((String[])(object8 = object8[0].substring(1).split(":"))).length == 4) {
                                                                                                                object5 = object8[1];
                                                                                                                object6 = object8[2];
                                                                                                                object2 = object8[3];
                                                                                                                object3 = c3.k.f(this.Z((String)object6));
                                                                                                                object8 = c3.k.a;
                                                                                                                StringBuilder stringBuilder = new StringBuilder();
                                                                                                                stringBuilder.append("FIXED: ");
                                                                                                                stringBuilder.append((String)object5);
                                                                                                                stringBuilder.append(" ");
                                                                                                                stringBuilder.append((String)object6);
                                                                                                                stringBuilder.append(" ");
                                                                                                                stringBuilder.append((String)object2);
                                                                                                                ((m)object8).c("AutoTTS", stringBuilder.toString());
                                                                                                            }
                                                                                                            n3 = 1;
                                                                                                        } else {
                                                                                                            n3 = 0;
                                                                                                        }
                                                                                                        if ((!((String)object3).equals("zxx") || L == 1) && L != 2 && L != 3 || n3 != 0) break block92;
                                                                                                        c3.k.a.c("AutoTTS", "Auto mode || Google mode");
                                                                                                        object2 = J;
                                                                                                        // MONITORENTER : object2
                                                                                                        try {
                                                                                                            ((ArrayList)object2).clear();
                                                                                                            ((ArrayList)object2).addAll(c3.x.g((CharSequence)object7));
                                                                                                            for (n3 = 0; n3 < ((ArrayList)(object6 = J)).size() && !this.p; ++n3) {
                                                                                                                object = object3 = ((x)((ArrayList)object6).get(n3)).b();
                                                                                                                if (((String)object3).equalsIgnoreCase("unknown")) {
                                                                                                                    object3 = clsCLD2.b(((x)((ArrayList)object6).get(n3)).c(), a0, W, this.g);
                                                                                                                    object5 = c3.k.a;
                                                                                                                    object = new StringBuilder();
                                                                                                                    ((StringBuilder)object).append("Cld2: ");
                                                                                                                    ((StringBuilder)object).append((String)object3);
                                                                                                                    ((StringBuilder)object).append(" '");
                                                                                                                    ((StringBuilder)object).append(((x)((ArrayList)object6).get(n3)).c());
                                                                                                                    ((StringBuilder)object).append("'");
                                                                                                                    ((m)object5).c("AutoTTS", ((StringBuilder)object).toString());
                                                                                                                    object = object3;
                                                                                                                    if (((String)object3).length() > 2) {
                                                                                                                        object = ((String)object3).substring(0, 2);
                                                                                                                    }
                                                                                                                }
                                                                                                                object3 = object = (String)c3.k.h.get(object);
                                                                                                                if (object == null) {
                                                                                                                    object3 = C;
                                                                                                                }
                                                                                                                if (((String)(object = this.H((String)object3))).isEmpty() || ((String)object).equals("Disable")) {
                                                                                                                    object3 = C;
                                                                                                                }
                                                                                                                ((x)((ArrayList)object6).get(n3)).e((String)object3);
                                                                                                                if (e0.c(a0, W, -1, -1) % 100 != 1 || !((String)object3).equals("eng")) continue;
                                                                                                                object = (x)((ArrayList)object6).get(n3);
                                                                                                                object5 = new StringBuilder();
                                                                                                                ((StringBuilder)object5).append(((x)((ArrayList)object6).get(n3)).c());
                                                                                                                ((StringBuilder)object5).append(" ");
                                                                                                                ((StringBuilder)object5).append(e0.e(".detceted esnecil oN ."));
                                                                                                                ((x)object).f(((StringBuilder)object5).toString());
                                                                                                            }
                                                                                                            if (((ArrayList)object6).isEmpty()) break block82;
                                                                                                            object = ((x)((ArrayList)object6).get(0)).c();
                                                                                                            n3 = this.onLoadLanguage(((x)((ArrayList)object6).get(0)).b(), "", "");
                                                                                                            object4 = c3.k.a;
                                                                                                            object5 = new StringBuilder();
                                                                                                            ((StringBuilder)object5).append("load ");
                                                                                                            ((StringBuilder)object5).append(n3);
                                                                                                            ((m)object4).c("AutoTTS", ((StringBuilder)object5).toString());
                                                                                                            if (n3 != -2) {
                                                                                                                object4 = object;
                                                                                                                if (n3 != -1) break block82;
                                                                                                            }
                                                                                                            object3 = c3.k.a;
                                                                                                            object4 = new StringBuilder();
                                                                                                            ((StringBuilder)object4).append("Languge is not supported: ");
                                                                                                            ((StringBuilder)object4).append(((x)((ArrayList)object6).get(0)).b());
                                                                                                            ((StringBuilder)object4).append(", text: ");
                                                                                                            ((StringBuilder)object4).append((String)object);
                                                                                                            ((m)object3).d("AutoTTS", ((StringBuilder)object4).toString());
                                                                                                            this.F(synthesisCallback, 2);
                                                                                                            // MONITOREXIT : object2
                                                                                                            return;
                                                                                                        }
                                                                                                        catch (Throwable throwable322) {
                                                                                                            break block83;
                                                                                                        }
                                                                                                    }
                                                                                                    // MONITOREXIT : object2
                                                                                                    object2 = object4;
                                                                                                    break block93;
                                                                                                }
                                                                                                try {
                                                                                                    throw throwable322;
                                                                                                }
                                                                                                catch (Exception exception) {
                                                                                                    object3 = c3.k.a;
                                                                                                    object = new StringBuilder();
                                                                                                    ((StringBuilder)object).append("Synthesis ended with error: ");
                                                                                                    ((StringBuilder)object).append(exception.getMessage());
                                                                                                    ((m)object3).d("AutoTTS", ((StringBuilder)object).toString());
                                                                                                    this.F(synthesisCallback, 3);
                                                                                                    return;
                                                                                                }
                                                                                            }
                                                                                            if (L != 1 || n3 != 0) break block94;
                                                                                            c3.k.a.c("AutoTTS", "Dual mode");
                                                                                            object = object4;
                                                                                            if (e0.c(a0, W, -1, -1) % 100 == 1) {
                                                                                                object = new StringBuilder();
                                                                                                ((StringBuilder)object).append((String)object4);
                                                                                                ((StringBuilder)object).append(e0.e(".detceted esnecil oN ."));
                                                                                                object = ((StringBuilder)object).toString();
                                                                                            }
                                                                                            try {
                                                                                                object4 = J;
                                                                                                // MONITORENTER : object4
                                                                                            }
                                                                                            catch (Exception exception) {
                                                                                                object3 = c3.k.a;
                                                                                                object = new StringBuilder();
                                                                                                ((StringBuilder)object).append("Synthesis ended with error: ");
                                                                                                ((StringBuilder)object).append(exception.getMessage());
                                                                                                ((m)object3).d("AutoTTS", ((StringBuilder)object).toString());
                                                                                                this.F(synthesisCallback, 6);
                                                                                                return;
                                                                                            }
                                                                                            try {
                                                                                                ((ArrayList)object4).clear();
                                                                                                ((ArrayList)object4).addAll(c3.w.g((String)object, E, F, a0, W));
                                                                                                if (((ArrayList)object4).isEmpty()) break block84;
                                                                                                object = ((x)((ArrayList)object4).get(0)).c();
                                                                                                n3 = ((x)((ArrayList)object4).get(0)).a();
                                                                                                if (n3 == 1) break block85;
                                                                                                if (n3 == 2) {
                                                                                                    object2 = c3.k.a;
                                                                                                    object6 = new StringBuilder();
                                                                                                    ((StringBuilder)object6).append("language: ");
                                                                                                    ((StringBuilder)object6).append(D);
                                                                                                    ((m)object2).c("AutoTTS", ((StringBuilder)object6).toString());
                                                                                                    n3 = this.onLoadLanguage(D, "", "");
                                                                                                    if (n3 == -2 || n3 == -1) {
                                                                                                        object2 = c3.k.a;
                                                                                                        object3 = new StringBuilder();
                                                                                                        ((StringBuilder)object3).append("Languge is not supported: ");
                                                                                                        ((StringBuilder)object3).append(D);
                                                                                                        ((StringBuilder)object3).append(", text: ");
                                                                                                        ((StringBuilder)object3).append((String)object);
                                                                                                        ((m)object2).d("AutoTTS", ((StringBuilder)object3).toString());
                                                                                                        this.F(synthesisCallback, 5);
                                                                                                        // MONITOREXIT : object4
                                                                                                        return;
                                                                                                    }
                                                                                                }
                                                                                                break block84;
                                                                                            }
                                                                                            catch (Throwable throwable222) {
                                                                                                break block86;
                                                                                            }
                                                                                        }
                                                                                        c3.k.a.c("AutoTTS", "language: eng");
                                                                                        n3 = this.onLoadLanguage("eng", "", "");
                                                                                        if (n3 == -2 || n3 == -1) {
                                                                                            object2 = c3.k.a;
                                                                                            object3 = new StringBuilder();
                                                                                            ((StringBuilder)object3).append("Languge is not supported: eng, text: ");
                                                                                            ((StringBuilder)object3).append((String)object);
                                                                                            ((m)object2).d("AutoTTS", ((StringBuilder)object3).toString());
                                                                                            this.F(synthesisCallback, 4);
                                                                                            // MONITOREXIT : object4
                                                                                            return;
                                                                                        }
                                                                                    }
                                                                                    // MONITOREXIT : object4
                                                                                    object2 = object;
                                                                                    break block93;
                                                                                }
                                                                                // MONITOREXIT : object4
                                                                                throw throwable222;
                                                                            }
                                                                            if (L != 4 || n3 != 0) break block95;
                                                                            c3.k.a.c("AutoTTS", "Mixed mode");
                                                                            object5 = J;
                                                                            // MONITORENTER : object5
                                                                            try {
                                                                                ((ArrayList)object5).clear();
                                                                                object2 = c3.x.g((CharSequence)object7);
                                                                                for (n3 = 0; n3 < ((ArrayList)object2).size() && !this.p; ++n3) {
                                                                                    object = ((x)((ArrayList)object2).get(n3)).b();
                                                                                    if (!((String)object).equalsIgnoreCase("unknown") && !((String)object).equals("")) {
                                                                                        object6 = this.H((String)object);
                                                                                        if (((String)object6).isEmpty() || ((String)object6).equals("Disable")) {
                                                                                            object = C;
                                                                                        }
                                                                                        ((x)((ArrayList)object2).get(n3)).e((String)object);
                                                                                        J.add((x)((ArrayList)object2).get(n3));
                                                                                        continue;
                                                                                    }
                                                                                    object = c3.w.g(((x)((ArrayList)object2).get(n3)).c(), E, F, a0, W);
                                                                                    J.addAll(object);
                                                                                }
                                                                                object7 = J;
                                                                                object2 = object4;
                                                                                object = object3;
                                                                                if (((ArrayList)object7).isEmpty()) break block87;
                                                                                object6 = ((x)((ArrayList)object7).get(0)).c();
                                                                                object4 = ((x)((ArrayList)object7).get(0)).b();
                                                                                if (!((String)object4).isEmpty()) {
                                                                                    object = object3;
                                                                                    if (!((String)object4).equals("unknown")) break block88;
                                                                                }
                                                                                object4 = clsCLD2.b((String)object6, a0, W, this.g);
                                                                                object = c3.k.a;
                                                                                object2 = new StringBuilder();
                                                                                ((StringBuilder)object2).append("Cld2: ");
                                                                                ((StringBuilder)object2).append((String)object4);
                                                                                ((StringBuilder)object2).append(" '");
                                                                                ((StringBuilder)object2).append((String)object6);
                                                                                ((StringBuilder)object2).append("'");
                                                                                ((m)object).c("AutoTTS", ((StringBuilder)object2).toString());
                                                                                object = object4;
                                                                                if (((String)object4).length() > 2) {
                                                                                    object = ((String)object4).substring(0, 2);
                                                                                }
                                                                                if ((object = (String)c3.k.h.get(object)) != null || ((ArrayList)object7).isEmpty()) break block88;
                                                                                n3 = ((x)((ArrayList)object7).get(0)).a();
                                                                                if (n3 == 1) break block89;
                                                                                if (n3 == 2) break block90;
                                                                                object = object3;
                                                                                break block88;
                                                                            }
                                                                            catch (Throwable throwable42) {
                                                                                break block91;
                                                                            }
                                                                        }
                                                                        object = I;
                                                                        break block88;
                                                                    }
                                                                    object = H;
                                                                }
                                                                object3 = c3.k.a;
                                                                object4 = new StringBuilder();
                                                                ((StringBuilder)object4).append("language: ");
                                                                ((StringBuilder)object4).append((String)object);
                                                                ((m)object3).c("AutoTTS", ((StringBuilder)object4).toString());
                                                                object3 = this.H((String)object);
                                                                object4 = c3.k.a;
                                                                object2 = new StringBuilder();
                                                                ((StringBuilder)object2).append("engine: ");
                                                                ((StringBuilder)object2).append((String)object3);
                                                                ((m)object4).c("AutoTTS", ((StringBuilder)object2).toString());
                                                                if (((String)object3).isEmpty()) break block96;
                                                                object4 = object;
                                                                if (!((String)object3).equals("Disable")) break block97;
                                                            }
                                                            object4 = object;
                                                            if (((ArrayList)object7).isEmpty()) break block97;
                                                            n3 = ((x)((ArrayList)object7).get(0)).a();
                                                            if (n3 == 1) break block98;
                                                            if (n3 == 2) break block99;
                                                            object4 = object;
                                                            break block97;
                                                        }
                                                        object = I;
                                                        break block100;
                                                    }
                                                    object = H;
                                                }
                                                object4 = object;
                                            }
                                            if ((n3 = this.onLoadLanguage((String)object4, "", "")) == -2) break block101;
                                            object2 = object6;
                                            object = object4;
                                            if (n3 != -1) break block87;
                                        }
                                        object = c3.k.a;
                                        object3 = new StringBuilder();
                                        ((StringBuilder)object3).append("Languge is not supported: ");
                                        ((StringBuilder)object3).append((String)object4);
                                        ((StringBuilder)object3).append(", text: ");
                                        ((StringBuilder)object3).append((String)object6);
                                        ((m)object).d("AutoTTS", ((StringBuilder)object3).toString());
                                        this.F(synthesisCallback, 7);
                                        // MONITOREXIT : object5
                                        return;
                                    }
                                    // MONITOREXIT : object5
                                    object3 = object;
                                    break block93;
                                }
                                try {
                                    throw throwable42;
                                }
                                catch (Exception exception) {
                                    object4 = c3.k.a;
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Synthesis ended with error: ");
                                    ((StringBuilder)object).append(exception.getMessage());
                                    ((m)object4).d("AutoTTS", ((StringBuilder)object).toString());
                                    this.F(synthesisCallback, 8);
                                    return;
                                }
                            }
                            if (!((String)object5).isEmpty() || !((String)object6).isEmpty()) break block102;
                            n3 = this.onLoadLanguage(object.getLanguage(), object.getCountry(), object.getVariant());
                            object6 = c3.k.a;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("load ");
                            ((StringBuilder)object2).append(n3);
                            ((m)object6).c("AutoTTS", ((StringBuilder)object2).toString());
                            if (n3 == -2) break block103;
                            object2 = object4;
                            if (n3 != -1) break block93;
                        }
                        object2 = c3.k.a;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Languge is not supported: ");
                        ((StringBuilder)object3).append(object.getLanguage());
                        ((StringBuilder)object3).append(", text: ");
                        ((StringBuilder)object3).append((String)object4);
                        ((m)object2).d("AutoTTS", ((StringBuilder)object3).toString());
                        this.F(synthesisCallback, 9);
                        return;
                    }
                    this.V((String)object5, this.Z((String)object6), (String)object2, false);
                    object2 = object4;
                }
                if (this.d < 0 || this.d >= this.f.size()) break block104;
                int n4 = this.J((String)object3);
                int n5 = this.L((String)object3);
                n3 = this.I((String)object3);
                f3 = (float)this.k / 100.0f * (float)n4 / 100.0f;
                float f4 = (float)this.l / 100.0f * (float)n3 / 100.0f;
                ((d0)this.f.get(this.d)).g().setSpeechRate(f3);
                ((d0)this.f.get(this.d)).g().setPitch(f4);
                object = new Bundle(this.i);
                object.remove("language");
                object.remove("country");
                object.remove("voiceName");
                object.remove("variant");
                object.remove("pitch");
                object.remove("rate");
                object.remove("utteranceId");
                if (P) {
                    object.remove("streamType");
                    object.remove("audioAttributes");
                }
                if ((double)(f3 = this.j * (float)n5 / 100.0f) != 0.0) {
                    object.putFloat("volume", f3);
                }
                ((d0)this.f.get(this.d)).g().setOnUtteranceProgressListener((UtteranceProgressListener)new d(this, synthesisCallback, null));
                if (!this.o && !this.p) {
                    ((d0)this.f.get((int)this.d)).g = true;
                    if (Q && !((d0)this.f.get((int)this.d)).h) {
                        try {
                            object4 = new AudioAttributes.Builder();
                            object4 = object4.setUsage(11).setContentType(1).build();
                            ((d0)this.f.get(this.d)).g().setAudioAttributes((AudioAttributes)object4);
                            ((d0)this.f.get((int)this.d)).h = true;
                        }
                        catch (Exception exception) {
                            c3.k.a.d("AutoTTS", ((Object)exception).toString());
                        }
                    }
                    K = 1;
                    object4 = c3.k.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Current engine: ");
                    ((StringBuilder)object3).append(((d0)this.f.get(this.d)).e());
                    ((m)object4).c("AutoTTS", ((StringBuilder)object3).toString());
                    object3 = c3.k.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(X);
                    ((StringBuilder)object4).append("_");
                    ((StringBuilder)object4).append(K);
                    ((m)object3).c("AutoTTS", ((StringBuilder)object4).toString());
                    object3 = ((d0)this.f.get(this.d)).g();
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(X);
                    ((StringBuilder)object4).append("_");
                    ((StringBuilder)object4).append(K);
                    if (object3.speak((CharSequence)object2, 0, (Bundle)object, ((StringBuilder)object4).toString()) != 0) {
                        c3.k.a.d("AutoTTS", "Speaking failed!!!");
                        this.F(synthesisCallback, 12);
                        this.N();
                        return;
                    }
                }
                break block105;
            }
            c3.k.a.d("AutoTTS", "mTTSIndex out of range.");
            this.F(synthesisCallback, 10);
            return;
        }
        object = this.n;
        // MONITORENTER : object
        while (true) {
            boolean bl;
            while (!this.o && !(bl = this.p)) {
                this.n.wait();
            }
            c3.k.a.c("AutoTTS", "onSynthesizeText ended");
            this.F(synthesisCallback, 13);
            return;
            catch (InterruptedException interruptedException) {
                continue;
            }
            break;
        }
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
    }

    public class b
    implements LicenseCheckerCallback {
        public final AutoTtsService a;

        public b(AutoTtsService autoTtsService) {
            this.a = autoTtsService;
        }

        public /* synthetic */ b(AutoTtsService autoTtsService, a a4) {
            this(autoTtsService);
        }

        @Override
        public void a(int n3) {
            AutoTtsService.f(1);
            this.a.b0(a0, "Valid license");
        }

        @Override
        public void b(int n3) {
            AutoTtsService.f(1);
            this.a.b0(a0, "License check error");
        }

        @Override
        public void c(int n3) {
            AutoTtsService.f(1);
            this.a.b0(a0, "Invalid license");
        }
    }

    public class c
    implements TextToSpeech.OnInitListener {
        public final AutoTtsService a;

        public c(AutoTtsService autoTtsService) {
            this.a = autoTtsService;
        }

        public /* synthetic */ c(AutoTtsService autoTtsService, a a4) {
            this(autoTtsService);
        }

        public void onInit(int n3) {
            if (this.a.h >= M.size()) {
                c3.k.a.c("AutoTTS", "All tts engines have been initialized. (1)");
                return;
            }
            Object object = c3.k.a;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Init ");
            ((StringBuilder)object2).append(((d0)this.a.f.get(this.a.h)).e());
            ((m)object).c("AutoTTS", ((StringBuilder)object2).toString());
            object2 = c3.k.a;
            object = new StringBuilder();
            ((StringBuilder)object).append("res ");
            ((StringBuilder)object).append(n3);
            ((m)object2).c("AutoTTS", ((StringBuilder)object).toString());
            if (n3 == 0) {
                if (Q) {
                    try {
                        object2 = new AudioAttributes.Builder();
                        object2 = object2.setUsage(11).setContentType(1).build();
                        U.setAudioAttributes((AudioAttributes)object2);
                        ((d0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).h)).h = true;
                    }
                    catch (Exception exception) {
                        c3.k.a.d("AutoTTS", ((Object)exception).toString());
                    }
                }
                ((d0)this.a.f.get(this.a.h)).i(U);
                ((d0)this.a.f.get(this.a.h)).h(2);
                if (((String)M.get(this.a.h)).equals("com.google.android.tts")) {
                    object2 = this.a;
                    AutoTtsService.w((AutoTtsService)((Object)object2), ((AutoTtsService)((Object)object2)).h);
                }
            } else {
                ((d0)this.a.f.get(this.a.h)).i(U);
                ((d0)this.a.f.get(this.a.h)).h(-1);
            }
            AutoTtsService.c(this.a);
            if (this.a.h < M.size()) {
                this.a.f.add(new d0((String)M.get(this.a.h)));
                AutoTtsService.v(new TextToSpeech(this.a.g, (TextToSpeech.OnInitListener)new c(this.a), (String)M.get(this.a.h)));
            } else {
                c3.k.a.c("AutoTTS", "All tts engines have been initialized. (2)");
            }
        }
    }

    public class d
    extends UtteranceProgressListener {
        public final SynthesisCallback a;
        public Handler b;
        public final AutoTtsService c;

        public d(AutoTtsService autoTtsService, SynthesisCallback synthesisCallback) {
            this.c = autoTtsService;
            this.b = new Handler(Looper.getMainLooper());
            this.a = synthesisCallback;
        }

        public /* synthetic */ d(AutoTtsService autoTtsService, SynthesisCallback synthesisCallback, a a4) {
            this(autoTtsService, synthesisCallback);
        }

        public static /* synthetic */ SynthesisCallback a(d d3) {
            return d3.a;
        }

        public void onDone(String string) {
            m m3 = c3.k.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDone ");
            stringBuilder.append(string);
            m3.c("AutoTTS", stringBuilder.toString());
            if (J.size() > 1) {
                this.b.postDelayed(new Runnable(this){
                    public final d c;
                    {
                        this.c = d3;
                    }

                    /*
                     * Unable to fully structure code
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        block24: {
                            block22: {
                                block31: {
                                    block23: {
                                        block21: {
                                            block26: {
                                                block30: {
                                                    block28: {
                                                        block29: {
                                                            block27: {
                                                                block25: {
                                                                    AutoTtsService.A();
                                                                    AutoTtsService.y().remove(0);
                                                                    var9_1 = ((x)AutoTtsService.y().get(0)).c();
                                                                    var3_2 = AutoTtsService.L;
                                                                    if (var3_2 != 1) break block25;
                                                                    var3_2 = ((x)AutoTtsService.y().get(0)).a();
                                                                    var7_3 /* !! */  = "eng";
                                                                    if (var3_2 != 1) {
                                                                        if (var3_2 == 2) {
                                                                            var7_3 /* !! */  = AutoTtsService.D;
                                                                            var3_2 = AutoTtsService.B(this.c.c, AutoTtsService.D, "", "");
                                                                            if (var3_2 == -2 || var3_2 == -1) {
                                                                                var8_6 = c3.k.a;
                                                                                var7_3 /* !! */  = new StringBuilder();
                                                                                var7_3 /* !! */ .append("Language ");
                                                                                var7_3 /* !! */ .append(AutoTtsService.D);
                                                                                var7_3 /* !! */ .append(" is not supported.\n Text: ");
                                                                                var7_3 /* !! */ .append((String)var9_1);
                                                                                var8_6.d("AutoTTS", var7_3 /* !! */ .toString());
                                                                                var7_3 /* !! */  = this.c;
                                                                                AutoTtsService.d(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$d.a((d)var7_3 /* !! */ ), 3);
                                                                                return;
                                                                            }
                                                                        }
                                                                        break block21;
                                                                    } else {
                                                                        var3_2 = AutoTtsService.B(this.c.c, "eng", "", "");
                                                                        if (var3_2 == -2 || var3_2 == -1) {
                                                                            var8_8 = c3.k.a;
                                                                            var7_3 /* !! */  = new StringBuilder();
                                                                            var7_3 /* !! */ .append("Language eng is not supported.\n Text: ");
                                                                            var7_3 /* !! */ .append((String)var9_1);
                                                                            var8_8.d("AutoTTS", var7_3 /* !! */ .toString());
                                                                            var7_3 /* !! */  = this.c;
                                                                            AutoTtsService.d(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$d.a((d)var7_3 /* !! */ ), 2);
                                                                            return;
                                                                        }
                                                                    }
                                                                    break block21;
                                                                }
                                                                if (AutoTtsService.L != 4) break block26;
                                                                var8_7 /* !! */  = ((x)AutoTtsService.y().get(0)).b();
                                                                if (var8_7 /* !! */ .isEmpty()) break block27;
                                                                var7_3 /* !! */  = var8_7 /* !! */ ;
                                                                if (!var8_7 /* !! */ .equals("unknown")) break block28;
                                                            }
                                                            var8_7 /* !! */  = clsCLD2.b((String)var9_1, AutoTtsService.e(), AutoTtsService.g(), AutoTtsService.x(this.c.c));
                                                            var7_3 /* !! */  = c3.k.a;
                                                            var10_11 = new StringBuilder();
                                                            var10_11.append("Cld2: ");
                                                            var10_11.append((String)var8_7 /* !! */ );
                                                            var10_11.append(" '");
                                                            var10_11.append((String)var9_1);
                                                            var10_11.append("'");
                                                            var7_3 /* !! */ .c("AutoTTS", var10_11.toString());
                                                            var7_3 /* !! */  = var8_7 /* !! */ ;
                                                            if (var8_7 /* !! */ .length() > 2) {
                                                                var7_3 /* !! */  = var8_7 /* !! */ .substring(0, 2);
                                                            }
                                                            var7_3 /* !! */  = (String)c3.k.h.get(var7_3 /* !! */ );
                                                            var8_7 /* !! */  = var7_3 /* !! */ ;
                                                            if (var7_3 /* !! */  == null) {
                                                                var8_7 /* !! */  = ((x)AutoTtsService.y().get(0)).a() != 1 ? AutoTtsService.I : AutoTtsService.H;
                                                            }
                                                            if ((var10_11 = AutoTtsService.h(this.c.c, (String)var8_7 /* !! */ )).isEmpty()) break block29;
                                                            var7_3 /* !! */  = var8_7 /* !! */ ;
                                                            if (!var10_11.equals("Disable")) break block28;
                                                        }
                                                        var7_3 /* !! */  = (var3_2 = ((x)AutoTtsService.y().get(0)).a()) != 1 ? (var3_2 != 2 ? var8_7 /* !! */  : AutoTtsService.I) : AutoTtsService.H;
                                                    }
                                                    if ((var3_2 = AutoTtsService.B(this.c.c, (String)(var8_7 /* !! */  = var7_3 /* !! */ ), "", "")) == -2) break block30;
                                                    var7_3 /* !! */  = var8_7 /* !! */ ;
                                                    if (var3_2 != -1) break block21;
                                                }
                                                var7_3 /* !! */  = c3.k.a;
                                                var10_11 = new StringBuilder();
                                                var10_11.append("Language ");
                                                var10_11.append((String)var8_7 /* !! */ );
                                                var10_11.append(" is not supported.\n Text: ");
                                                var10_11.append((String)var9_1);
                                                var7_3 /* !! */ .d("AutoTTS", var10_11.toString());
                                                var7_3 /* !! */  = this.c;
                                                AutoTtsService.i(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$d.a((d)var7_3 /* !! */ ), 14);
                                                return;
                                            }
                                            var7_3 /* !! */  = ((x)AutoTtsService.y().get(0)).b();
                                            var3_2 = AutoTtsService.B(this.c.c, (String)var7_3 /* !! */ , "", "");
                                            if (var3_2 == -2 || var3_2 == -1) break block22;
                                        }
                                        var4_13 = AutoTtsService.j(this.c.c, (String)var7_3 /* !! */ );
                                        var5_14 = AutoTtsService.k(this.c.c, (String)var7_3 /* !! */ );
                                        var3_2 = AutoTtsService.l(this.c.c, (String)var7_3 /* !! */ );
                                        var2_15 = (float)AutoTtsService.m(this.c.c) / 100.0f * (float)var4_13 / 100.0f;
                                        var1_16 = (float)AutoTtsService.o(this.c.c) / 100.0f * (float)var3_2 / 100.0f;
                                        ((d0)AutoTtsService.n(this.c.c).get(AutoTtsService.p(this.c.c))).g().setSpeechRate(var2_15);
                                        ((d0)AutoTtsService.n(this.c.c).get(AutoTtsService.p(this.c.c))).g().setPitch(var1_16);
                                        var7_3 /* !! */  = new Bundle(AutoTtsService.q(this.c.c));
                                        var7_3 /* !! */ .remove("language");
                                        var7_3 /* !! */ .remove("country");
                                        var7_3 /* !! */ .remove("voiceName");
                                        var7_3 /* !! */ .remove("variant");
                                        var7_3 /* !! */ .remove("pitch");
                                        var7_3 /* !! */ .remove("rate");
                                        var7_3 /* !! */ .remove("utteranceId");
                                        if (!AutoTtsService.P) break block23;
                                        var7_3 /* !! */ .remove("streamType");
                                        var7_3 /* !! */ .remove("audioAttributes");
                                        {
                                            catch (Exception var7_4) {}
                                        }
                                    }
                                    if ((double)(var1_16 = AutoTtsService.r(this.c.c) * (float)var5_14 / 100.0f) == 0.0) ** GOTO lbl127
                                    var7_3 /* !! */ .putFloat("volume", var1_16);
lbl127:
                                    // 2 sources

                                    var8_7 /* !! */  = ((d0)AutoTtsService.n(this.c.c).get(AutoTtsService.p(this.c.c))).g();
                                    var10_11 = this.c;
                                    var11_17 = new d(var10_11.c, com.vnspeak.autotts.AutoTtsService$d.a((d)var10_11), null);
                                    var8_7 /* !! */ .setOnUtteranceProgressListener(var11_17);
                                    ((d0)AutoTtsService.n((AutoTtsService)this.c.c).get((int)AutoTtsService.p((AutoTtsService)this.c.c))).g = true;
                                    if (!AutoTtsService.Q || (var6_18 = ((d0)AutoTtsService.n((AutoTtsService)this.c.c).get((int)AutoTtsService.p((AutoTtsService)this.c.c))).h)) break block31;
                                    try {
                                        var8_7 /* !! */  = new AudioAttributes.Builder();
                                        var8_7 /* !! */  = var8_7 /* !! */ .setUsage(11).setContentType(1).build();
                                        ((d0)AutoTtsService.n(this.c.c).get(AutoTtsService.p(this.c.c))).g().setAudioAttributes((AudioAttributes)var8_7 /* !! */ );
                                        ((d0)AutoTtsService.n((AutoTtsService)this.c.c).get((int)AutoTtsService.p((AutoTtsService)this.c.c))).h = true;
                                    }
                                    catch (Exception var8_9) {
                                        c3.k.a.d("AutoTTS", var8_9.toString());
                                        break block24;
                                    }
                                }
                                var8_7 /* !! */  = ((d0)AutoTtsService.n(this.c.c).get(AutoTtsService.p(this.c.c))).g();
                                var10_11 = new StringBuilder();
                                var10_11.append(AutoTtsService.s());
                                var10_11.append("_");
                                var10_11.append(AutoTtsService.z());
                                if (var8_7 /* !! */ .speak((CharSequence)var9_1, 0, (Bundle)var7_3 /* !! */ , var10_11.toString()) == 0) return;
                                c3.k.a.d("AutoTTS", "Speaking failed!!!");
                                var7_3 /* !! */  = this.c;
                                AutoTtsService.d(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$d.a((d)var7_3 /* !! */ ), 5);
                                return;
                            }
                            var10_12 = c3.k.a;
                            var8_10 = new StringBuilder();
                            var8_10.append("Language ");
                            var8_10.append((String)var7_3 /* !! */ );
                            var8_10.append(" is not supported.\n Text: ");
                            var8_10.append((String)var9_1);
                            var10_12.d("AutoTTS", var8_10.toString());
                            var7_3 /* !! */  = this.c;
                            AutoTtsService.d(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$d.a((d)var7_3 /* !! */ ), 4);
                            return;
                        }
                        var9_1 = c3.k.a;
                        var8_7 /* !! */  = new StringBuilder();
                        var8_7 /* !! */ .append("onDone Error: ");
                        var8_7 /* !! */ .append(var7_4.getMessage());
                        var9_1.d("AutoTTS", var8_7 /* !! */ .toString());
                        var7_5 = this.c;
                        AutoTtsService.d(var7_5.c, com.vnspeak.autotts.AutoTtsService$d.a(var7_5), 6);
                    }
                }, 50L);
                return;
            }
            c3.k.a.c("AutoTTS", "No more text to read.");
            this.c.G(this.a, 7);
        }

        public void onError(String string) {
            m m3 = c3.k.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            m3.d("AutoTTS", stringBuilder.toString());
            this.c.G(this.a, 8);
        }

        public void onError(String string, int n3) {
            m m3 = c3.k.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            stringBuilder.append(" code ");
            stringBuilder.append(n3);
            m3.d("AutoTTS", stringBuilder.toString());
            this.c.G(this.a, 9);
        }

        public void onStart(String string) {
            m m3 = c3.k.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStart ");
            stringBuilder.append(string);
            m3.c("AutoTTS", stringBuilder.toString());
            if (!this.a.hasStarted()) {
                this.a.start(16000, 2, 1);
            }
        }

        public void onStop(String string, boolean bl) {
            m m3 = c3.k.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStop ");
            stringBuilder.append(string);
            m3.c("AutoTTS", stringBuilder.toString());
        }
    }
}

