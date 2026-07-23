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
import c3.f0;
import c3.g0;
import c3.m;
import c3.o;
import c3.v;
import c3.y;
import c3.z;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.vnspeak.autotts.clsCLD2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoTtsService
extends TextToSpeechService {
    public static volatile String F;
    public static volatile String G;
    public static volatile int H;
    public static volatile int I;
    public static volatile int J;
    public static volatile String K;
    public static volatile String L;
    public static final ArrayList M;
    public static int N;
    public static volatile int O;
    public static volatile ArrayList P;
    public static boolean Q;
    public static boolean R;
    public static boolean S;
    public static boolean T;
    public static boolean U;
    public static boolean V;
    public static boolean W;
    public static boolean X;
    public static volatile ArrayList Y;
    public static TextToSpeech Z;
    public static int a0;
    public static int b0;
    public static volatile String c0;
    public static final byte[] d0;
    public static String e0;
    public static final byte[] f0;
    public static int g0;
    public final int A;
    public final int B;
    public final int C;
    public final int D;
    public int E = 2;
    public volatile String c = "";
    public volatile int d = -1;
    public volatile int e = -1;
    public final ArrayList f = new ArrayList();
    public final ArrayList g = new ArrayList();
    public Context h;
    public int i;
    public volatile Bundle j;
    public float k;
    public int l;
    public int m;
    public boolean n = false;
    public final Object o = new Object();
    public final AtomicBoolean p = new AtomicBoolean();
    public final AtomicBoolean q = new AtomicBoolean();
    public AudioManager r;
    public AudioFocusRequest s;
    public final Handler t = new Handler(Looper.getMainLooper());
    public int u = -1;
    public long v = 0L;
    public long w = 0L;
    public LicenseCheckerCallback x;
    public LicenseChecker y = null;
    public final int z;

    static {
        M = new ArrayList();
        N = 0;
        P = null;
        Q = false;
        R = false;
        S = false;
        T = false;
        U = false;
        V = false;
        W = true;
        X = false;
        Y = new ArrayList();
        a0 = 0;
        b0 = -1;
        c0 = "";
        d0 = new byte[32];
        e0 = "";
        f0 = new byte[]{-45, 64, 37, -10, -72, -47, 64, -63, 102, 86, -35, -85, 78, -15, -26, -113, -54, 36, -74, 35};
        g0 = -1;
    }

    public AutoTtsService() {
        this.z = 0;
        this.A = 1;
        this.B = 2;
        this.C = 3;
        this.D = -1;
    }

    public static /* synthetic */ TextToSpeech B(TextToSpeech textToSpeech) {
        Z = textToSpeech;
        return textToSpeech;
    }

    public static /* synthetic */ int C(AutoTtsService autoTtsService, int n3) {
        autoTtsService.e = n3;
        return n3;
    }

    public static /* synthetic */ int G(AutoTtsService autoTtsService, int n3) {
        autoTtsService.u = n3;
        return n3;
    }

    public static /* synthetic */ void a(int n3) {
        if (n3 != -2) {
            if (n3 != -1) {
                if (n3 != 1) {
                    return;
                }
                c3.m.a.c("TTS", "Audio focus gained");
                return;
            }
            c3.m.a.c("TTS", "Audio focus lost");
            return;
        }
        c3.m.a.c("TTS", "Audio focus lost temporarily");
    }

    public static /* synthetic */ int d(int n3) {
        N = n3;
        return n3;
    }

    public static /* synthetic */ int e() {
        int n3 = N;
        N = n3 + 1;
        return n3;
    }

    public static /* synthetic */ int f(AutoTtsService autoTtsService) {
        int n3 = autoTtsService.i;
        autoTtsService.i = n3 + 1;
        return n3;
    }

    public static /* synthetic */ int l(AutoTtsService autoTtsService, String string, String string2, String string3) {
        return autoTtsService.Z(string, string2, string3);
    }

    public static /* synthetic */ int o(int n3) {
        g0 = n3;
        return n3;
    }

    public static /* synthetic */ int q() {
        return b0;
    }

    public static /* synthetic */ String r(AutoTtsService autoTtsService, String string) {
        return autoTtsService.M(string);
    }

    public static /* synthetic */ int s(AutoTtsService autoTtsService, String string) {
        return autoTtsService.O(string);
    }

    public static /* synthetic */ int t(AutoTtsService autoTtsService, String string) {
        return autoTtsService.R(string);
    }

    public static /* synthetic */ int u(AutoTtsService autoTtsService, String string) {
        return autoTtsService.N(string);
    }

    public static /* synthetic */ int v(AutoTtsService autoTtsService) {
        return autoTtsService.l;
    }

    public static /* synthetic */ int w(AutoTtsService autoTtsService) {
        return autoTtsService.m;
    }

    public static /* synthetic */ Bundle x(AutoTtsService autoTtsService) {
        return autoTtsService.j;
    }

    public static /* synthetic */ float y(AutoTtsService autoTtsService) {
        return autoTtsService.k;
    }

    public final void H() {
        LicenseChecker licenseChecker = this.y;
        if (licenseChecker != null) {
            licenseChecker.f(this.x);
        }
    }

    public final Notification I() {
        return new k((Context)this, "tts_channel").d("Auto TTS active").g(17301540).f(true).a();
    }

    public final void J() {
        NotificationChannel notificationChannel = new NotificationChannel("tts_channel", (CharSequence)"TTS Engine", 2);
        ((NotificationManager)this.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
    }

    public final void K(SynthesisCallback synthesisCallback, int n3) {
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
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void L(SynthesisCallback synthesisCallback, int n3) {
        o o3 = c3.m.a;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("endSynthesis #");
        ((StringBuilder)object).append(n3);
        o3.c("AutoTTS", ((StringBuilder)object).toString());
        object = this.o;
        // MONITORENTER : object
        this.p.set(true);
        this.o.notifyAll();
        // MONITOREXIT : object
        if (!synthesisCallback.hasStarted()) return;
        if (synthesisCallback.hasFinished()) return;
        synthesisCallback.done();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final String M(String object) {
        Object object2 = c3.m.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("getEngine4Language ");
        ((StringBuilder)object3).append((String)object);
        ((o)object2).c("AutoTTS", ((StringBuilder)object3).toString());
        if (O == 3) {
            return "com.google.android.tts";
        }
        object2 = c3.m.c;
        // MONITORENTER : object2
        int n3 = 0;
        while (true) {
            Object object4;
            object3 = c3.m.c;
            if (n3 >= object3.size()) {
                c3.m.a.c("AutoTTS", " res ''");
                return "";
            }
            if (!((c3.e)object3.get((int)n3)).f.isEmpty() && !((c3.e)object3.get((int)n3)).f.equalsIgnoreCase("disable")) {
                object4 = c3.m.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("- ");
                stringBuilder.append(((c3.e)object3.get((int)n3)).b);
                stringBuilder.append(" ");
                stringBuilder.append(((c3.e)object3.get((int)n3)).f);
                ((o)object4).c("AutoTTS", stringBuilder.toString());
            }
            if (((String)object).equals(((c3.e)object3.get((int)n3)).b)) {
                if (((c3.e)object3.get((int)n3)).i) {
                    c3.m.a.c("AutoTTS", " res1 Disable");
                    // MONITOREXIT : object2
                    return "Disable";
                }
                object = c3.m.a;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append(" res ");
                ((StringBuilder)object4).append(((c3.e)object3.get((int)n3)).f);
                ((o)object).c("AutoTTS", ((StringBuilder)object4).toString());
                object = ((c3.e)object3.get((int)n3)).f;
                // MONITOREXIT : object2
                return object;
            }
            ++n3;
        }
    }

    public final int N(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c3.m.c).size(); ++i3) {
            if (!((c3.e)list.get((int)i3)).b.equals(string)) continue;
            return ((c3.e)list.get((int)i3)).e;
        }
        return 100;
    }

    public final int O(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c3.m.c).size(); ++i3) {
            if (!((c3.e)list.get((int)i3)).b.equals(string)) continue;
            return ((c3.e)list.get((int)i3)).c;
        }
        return 100;
    }

    public final String P(String charSequence) {
        o o3 = c3.m.a;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("getVariant4Language ");
        ((StringBuilder)object).append((String)charSequence);
        o3.c("TAG", ((StringBuilder)object).toString());
        if (O == 3) {
            return charSequence;
        }
        for (int i3 = 0; i3 < (object = c3.m.c).size(); ++i3) {
            if (!((c3.e)object.get((int)i3)).g.isEmpty()) {
                o3 = c3.m.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" -");
                stringBuilder.append(((c3.e)object.get((int)i3)).b);
                stringBuilder.append(" -> ");
                stringBuilder.append(((c3.e)object.get((int)i3)).h);
                o3.c("AutoTTS", stringBuilder.toString());
            }
            if (!((String)charSequence).equals(((c3.e)object.get((int)i3)).b)) continue;
            o3 = c3.m.a;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" Found ");
            ((StringBuilder)charSequence).append(((c3.e)object.get((int)i3)).h);
            o3.c("AutoTTS", ((StringBuilder)charSequence).toString());
            return ((c3.e)object.get((int)i3)).h;
        }
        return "";
    }

    public final String Q(String object) {
        Object object2 = c3.m.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getVoice4Language ");
        stringBuilder.append((String)object);
        ((o)object2).c("TAG", stringBuilder.toString());
        if (O == 3) {
            return object;
        }
        for (int i3 = 0; i3 < (object2 = c3.m.c).size(); ++i3) {
            if (!((c3.e)object2.get((int)i3)).g.isEmpty()) {
                o o3 = c3.m.a;
                stringBuilder = new StringBuilder();
                stringBuilder.append(" -");
                stringBuilder.append(((c3.e)object2.get((int)i3)).b);
                stringBuilder.append(" -> ");
                stringBuilder.append(((c3.e)object2.get((int)i3)).g);
                o3.c("AutoTTS", stringBuilder.toString());
            }
            if (!((String)object).equals(((c3.e)object2.get((int)i3)).b)) continue;
            object = c3.m.a;
            stringBuilder = new StringBuilder();
            stringBuilder.append(" Found ");
            stringBuilder.append(((c3.e)object2.get((int)i3)).g);
            ((o)object).c("AutoTTS", stringBuilder.toString());
            return ((c3.e)object2.get((int)i3)).g;
        }
        return "";
    }

    public final int R(String string) {
        List list;
        for (int i3 = 0; i3 < (list = c3.m.c).size(); ++i3) {
            if (!((c3.e)list.get((int)i3)).b.equals(string)) continue;
            return ((c3.e)list.get((int)i3)).d;
        }
        return 100;
    }

    public final boolean S() {
        if (Build.VERSION.SDK_INT >= 33) {
            return this.checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0;
        }
        return true;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void T() {
        synchronized (this) {
            block9: {
                try {
                    c3.m.a.c("AutoTTS", "initAllTTS");
                    var1_1 = 0;
lbl5:
                    // 2 sources

                    while (true) {
                        var2_2 = this.f.size();
                        if (var1_1 < var2_2) {
                            try {
                                ((f0)this.f.get(var1_1)).m();
                                ((f0)this.f.get(var1_1)).l();
                            }
                            catch (Exception var3_3) {}
                        }
                        break;
                    }
                }
                catch (Throwable var3_4) {
                    break block9;
                }
                {
                    this.f.clear();
                    this.i = 0;
                    if (!AutoTtsService.P.isEmpty()) {
                        var4_6 = this.f;
                        var3_5 = new f0((String)AutoTtsService.P.get(this.i));
                        var4_6.add(var3_5);
                        var3_5 = new c3.d(this.h, this);
                        var3_5.c((String)AutoTtsService.P.get(this.i));
                        this.g.add(var3_5);
                        var5_7 = this.getApplicationContext();
                        var3_5 = new c(this, null);
                        var4_6 = new TextToSpeech(var5_7, (TextToSpeech.OnInitListener)var3_5, (String)AutoTtsService.P.get(this.i));
                        AutoTtsService.Z = var4_6;
                    }
                    return;
                }
            }
            throw var3_4;
            ++var1_1;
            ** continue;
        }
    }

    public final void U() {
        String string = Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id");
        this.x = new b(this, null);
        this.y = new LicenseChecker((Context)this, new ServerManagedPolicy((Context)this, new AESObfuscator(f0, this.getPackageName(), string)), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzCqD0VjR3RQYVN1f5hIVDWBBoomRgzjbHqW3g5v59YfVwTkmM4hWXvyHEXBHcE7Wcbl8Tlic9LIH0HStl7KN+Erx4mUlk8jqsPGeDC9r2f2VLKYGKm6lb5Lvjw8aNfS6auzJlFN12/NBMEBPb1wstV2B1gUaDNT/63Zz0arO6XbjFM9WAHpo54BFQoWk/vRK95G88xlWoUX3QGum0AouPMj8vKiYaBGzFjnXMTdRH70bYPY5pPmF710ox3vv/SSiM78BT/Ez1V7rshx3fL9ZjrxbmrO8YYbqtzvGu91+y0viRkLvJozU5dy5zHp147UEaX3rDnyxFBhGngO1ng3hQIDAQAB");
        this.H();
    }

    public final boolean V(Locale object, Locale object2) {
        if (object2 != null && object != null) {
            String string = ((Locale)object).getISO3Language();
            String string2 = ((Locale)object).getISO3Country();
            String string3 = ((Locale)object).getVariant();
            object = ((Locale)object2).getISO3Language();
            String string4 = ((Locale)object2).getISO3Country();
            object2 = ((Locale)object2).getVariant();
            if (string.equals(object)) {
                if (string2.isEmpty()) {
                    return true;
                }
                if (string2.equals(string4)) {
                    if (string3.isEmpty()) {
                        return true;
                    }
                    return string3.equals(object2);
                }
            }
        }
        return false;
    }

    public final boolean W() {
        StatusBarNotification[] statusBarNotificationArray = ((NotificationManager)this.getSystemService(NotificationManager.class)).getActiveNotifications();
        int n3 = statusBarNotificationArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (statusBarNotificationArray[i3].getId() != 136549) continue;
            return true;
        }
        return false;
    }

    public final void X() {
        if (F != null) {
            return;
        }
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
        F = sharedPreferences.getString("auto_mode_language", "");
        if (F.isEmpty()) {
            F = c3.m.f(Locale.getDefault());
        }
        if ((K = sharedPreferences.getString("mixed_mode_latin_language", "")).isEmpty()) {
            K = c3.m.f(Locale.getDefault());
        }
        if ((L = sharedPreferences.getString("mixed_mode_non_latin_language", "")).isEmpty()) {
            L = c3.m.f(Locale.getDefault());
        }
        if ((G = sharedPreferences.getString("dual_mode_language", "")).isEmpty()) {
            G = c3.m.f(Locale.getDefault());
        }
        H = sharedPreferences.getInt("number_mode_language", 0);
        I = sharedPreferences.getInt("punc_mode_language", 0);
        J = sharedPreferences.getInt("emoji_mode_language", 0);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void Y() {
        synchronized (this) {
            block9: {
                try {
                    AutoTtsService.P = var2_1 = new ArrayList();
                    var3_3 = this.M(c3.m.f(Locale.getDefault()));
                    var2_1 = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                    var1_4 = 0;
lbl7:
                    // 2 sources

                    while (true) {
                        var4_5 = new StringBuilder();
                        var4_5.append("engine_");
                        var4_5.append(var1_4);
                        var4_5 = var2_1.getString(var4_5.toString(), "");
                        if (!var4_5.isEmpty() && !var4_5.equals("end")) {
                            if (var4_5.equals(var3_3)) break;
                            AutoTtsService.P.add(var4_5);
                        }
                        ** GOTO lbl-1000
                        break;
                    }
                }
                catch (Throwable var2_2) {
                    break block9;
                }
                ++var1_4;
                ** continue;
lbl-1000:
                // 1 sources

                {
                    if (!var3_3.isEmpty() && !var3_3.equals("Disable")) {
                        AutoTtsService.P.add(0, var3_3);
                    }
                    if (AutoTtsService.P.isEmpty() && c3.v.a(this.h)) {
                        AutoTtsService.P.add("com.google.android.tts");
                    }
                    return;
                }
            }
            throw var2_2;
        }
    }

    public final int Z(String object, String object2, String object3) {
        Object object4;
        CharSequence charSequence;
        Object object5 = c3.m.a;
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("loadLanguage ");
        ((StringBuilder)charSequence2).append((String)object);
        ((StringBuilder)charSequence2).append(" ");
        ((StringBuilder)charSequence2).append((String)object2);
        ((StringBuilder)charSequence2).append(" ");
        ((StringBuilder)charSequence2).append((String)object3);
        ((o)object5).c("AutoTTS", ((StringBuilder)charSequence2).toString());
        int n3 = this.onIsLanguageAvailable((String)object, (String)object2, (String)object3);
        object5 = c3.m.a;
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(" isLanguageAvailable = ");
        ((StringBuilder)charSequence2).append(n3);
        ((o)object5).c("AutoTTS", ((StringBuilder)charSequence2).toString());
        if (((String)object3).contains("autotts.") && n3 == 2) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)object);
            ((StringBuilder)charSequence2).append("_");
            ((StringBuilder)charSequence2).append((String)object2);
            charSequence2 = ((StringBuilder)charSequence2).toString();
            object3 = ((String)object3).substring(8);
            object5 = "";
        } else {
            charSequence = this.Q((String)object);
            object4 = this.M((String)object);
            charSequence2 = object3;
            if (((String)object3).isEmpty()) {
                charSequence2 = this.P((String)object);
            }
            object5 = charSequence2;
            object3 = object4;
            charSequence2 = charSequence;
        }
        charSequence = charSequence2;
        charSequence2 = object3;
        if (((String)object3).isEmpty()) {
            charSequence = this.Q(F);
            charSequence2 = this.M(F);
        }
        object4 = c3.m.a;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("engine: ");
        ((StringBuilder)object3).append((String)charSequence2);
        ((StringBuilder)object3).append(" voice ");
        ((StringBuilder)object3).append((String)charSequence);
        ((StringBuilder)object3).append(" variant ");
        ((StringBuilder)object3).append((String)object5);
        ((o)object4).c("AutoTTS", ((StringBuilder)object3).toString());
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    return n3;
                }
                object3 = this.f0((String)charSequence);
                if (object3 == null) {
                    return -2;
                }
                if (this.c.equals(charSequence2) && c3.m.f((Locale)object3).equals(object) && ((String)object2).equals(c3.m.e((Locale)object3)) && ((Locale)object3).getVariant().equals(object5)) {
                    this.b0((String)charSequence2, (Locale)object3, (String)object5, R);
                    return n3;
                }
                if (!this.c.equals(charSequence2) && c3.m.f((Locale)object3).equals(object) && ((String)object2).equals(c3.m.e((Locale)object3)) && ((Locale)object3).getVariant().equals(object5)) {
                    this.b0((String)charSequence2, (Locale)object3, (String)object5, R);
                    return n3;
                }
                object = new Locale((String)object, (String)object2, (String)object5);
                this.b0(this.k0((Locale)object), (Locale)object, (String)object5, R);
                return n3;
            }
            object3 = this.f0((String)charSequence);
            if (object3 == null) {
                return -2;
            }
            if (this.c.equals(charSequence2) && c3.m.f((Locale)object3).equals(object) && ((String)object2).equals(c3.m.e((Locale)object3))) {
                this.b0("", (Locale)object3, (String)object5, R);
                return n3;
            }
            if (!this.c.equals(charSequence2) && c3.m.f((Locale)object3).equals(object) && ((String)object2).equals(c3.m.e((Locale)object3))) {
                this.b0((String)charSequence2, (Locale)object3, (String)object5, R);
                return n3;
            }
            object = new Locale((String)object, (String)object2, "");
            this.b0(this.k0((Locale)object), (Locale)object, (String)object5, R);
            return n3;
        }
        object2 = this.f0((String)charSequence);
        if (object2 == null) {
            return -2;
        }
        if (this.c.equals(charSequence2) && c3.m.f((Locale)object2).equals(object)) {
            this.b0((String)charSequence2, (Locale)object2, (String)object5, R);
            return n3;
        }
        if (!this.c.equals(charSequence2) && c3.m.f((Locale)object2).equals(object)) {
            this.b0((String)charSequence2, (Locale)object2, (String)object5, R);
            this.c = charSequence2;
            return n3;
        }
        object = new Locale((String)object, "", "");
        this.b0(this.k0((Locale)object), (Locale)object, (String)object5, R);
        return n3;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void a0() {
        synchronized (this) {
            try {
                c3.m.a.c("AutoTTS", "loadLanguages");
                c3.m.c.clear();
                c3.m.f.clear();
                SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    Object object = new StringBuilder();
                    ((StringBuilder)object).append("language_");
                    ((StringBuilder)object).append(n3);
                    String string = sharedPreferences.getString(((StringBuilder)object).toString(), "");
                    object = c3.m.a;
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" - ");
                    ((StringBuilder)object2).append(string);
                    ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (string.isEmpty()) {
                        object = c3.m.a;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Enabled languages 2 letters: ");
                        ((StringBuilder)object2).append(c3.m.f.toString());
                        ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("_speed");
                    int n4 = sharedPreferences.getInt(((StringBuilder)object).toString(), 100);
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("_pitch");
                    int n5 = sharedPreferences.getInt(((StringBuilder)object).toString(), 100);
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("_volume");
                    int n6 = sharedPreferences.getInt(((StringBuilder)object).toString(), 100);
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("_variant");
                    String string2 = sharedPreferences.getString(((StringBuilder)object).toString(), "*Default");
                    String[] stringArray = sharedPreferences.getString(string, "");
                    Object object3 = "";
                    String string3 = "";
                    object2 = object3;
                    object = string3;
                    if (!stringArray.isEmpty()) {
                        stringArray = stringArray.split("#");
                        object2 = object3;
                        object = string3;
                        if (stringArray.length >= 2) {
                            object2 = stringArray[0];
                            object = stringArray[1];
                        }
                    }
                    object3 = new c3.e("", string, n4, n6, n5, (String)object2, (String)object, string2);
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((c3.e)object3).b);
                    ((StringBuilder)object).append("_disabled");
                    ((c3.e)object3).i = sharedPreferences.getBoolean(((StringBuilder)object).toString(), false);
                    c3.m.c.add(object3);
                    if (!((c3.e)object3).i) {
                        object3 = (String)c3.m.i.get(string);
                        object = c3.m.a;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(" -not disabled: ");
                        ((StringBuilder)object2).append((String)object3);
                        ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                        if (object3 != null) {
                            c3.m.f.add(object3);
                        }
                    }
                    ++n3;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final void b0(String object, Locale locale, String string, boolean bl) {
        int n3;
        String string2;
        Object object2;
        Object object32;
        Object object4;
        block24: {
            object4 = object;
            object32 = c3.m.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("LoadVoice ");
            ((StringBuilder)object2).append((String)object4);
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(locale.toString());
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(string);
            ((o)object32).c("AutoTTS", ((StringBuilder)object2).toString());
            if (bl && O != 3) {
                this.d0((String)object, locale, string, bl);
                return;
            }
            if (((String)object4).isEmpty()) {
                object4 = this.c;
            } else {
                this.c = object4;
            }
            object2 = "";
            string2 = ((String)object4).replace("-", "").replace("_", "");
            object4 = c3.m.a;
            object = new StringBuilder();
            ((StringBuilder)object).append(" current engine: ");
            ((StringBuilder)object).append(string2);
            ((o)object4).c("AutoTTS", ((StringBuilder)object).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((f0)this.f.get(n3)).e().equals(string2) || ((f0)this.f.get(n3)).f() != 2) continue;
                c3.m.a.c("AutoTTS", " found!");
                break block24;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4;
            Object object5;
            this.d = n3;
            if (string.isEmpty() && !((f0)this.f.get((int)n3)).e.isEmpty()) {
                c3.m.a.c("AutoTTS", "Load voice original");
                this.c0(string2, locale);
                return;
            }
            object32 = new Locale("zxx");
            object4 = object2;
            object = object32;
            if (!((f0)this.f.get((int)n3)).e.isEmpty()) {
                object5 = ((f0)this.f.get(n3)).g().getVoice();
                object4 = object2;
                object = object32;
                if (object5 != null) {
                    object = object5.getLocale();
                    object4 = object5.getName();
                    object32 = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" last ");
                    ((StringBuilder)object2).append(object);
                    ((StringBuilder)object2).append(" ");
                    ((StringBuilder)object2).append((String)object4);
                    ((o)object32).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (c3.m.f((Locale)object).equals(c3.m.f(locale)) && (c3.m.e((Locale)object).equals(c3.m.e(locale)) || c3.m.e(locale).equals("")) && ((String)object4).equals(string)) {
                        c3.m.a.c("AutoTTS", " Do nothing!");
                        return;
                    }
                }
            }
            object32 = Y;
            int n5 = ((ArrayList)object32).size();
            for (n4 = 0; n4 < n5; ++n4) {
                object2 = ((ArrayList)object32).get(n4);
                object5 = ((String)(object2 = (String)object2)).split("#");
                if (((String[])object5).length < 2 || !object5[0].equals(string2) || !object5[1].equals(locale.toString())) continue;
                object5 = c3.m.a;
                object32 = new StringBuilder();
                ((StringBuilder)object32).append("voice: ");
                ((StringBuilder)object32).append((String)object2);
                ((o)object5).c("AutoTTS", ((StringBuilder)object32).toString());
                for (Object object32 : c3.m.c) {
                    if (!((c3.e)object32).f.equals(string2) || !((c3.e)object32).g.equals(locale.toString())) continue;
                    string = ((c3.e)object32).h;
                    break;
                }
                break;
            }
            object2 = c3.m.a;
            object32 = new StringBuilder();
            ((StringBuilder)object32).append(" variant ");
            ((StringBuilder)object32).append(string);
            ((o)object2).c("AutoTTS", ((StringBuilder)object32).toString());
            if (string.equals("*Default") && !this.V(locale, (Locale)object)) {
                object4 = c3.m.a;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(locale.toString());
                ((StringBuilder)object2).append(" vs ");
                ((StringBuilder)object2).append(((Locale)object).toString());
                ((o)object4).c("AutoTTS", ((StringBuilder)object2).toString());
                if (((f0)this.f.get(this.d)).g().setLanguage(locale) >= 0) {
                    ((f0)this.f.get((int)this.d)).f = true;
                    c3.m.a.c("AutoTTS", "Set voice 1");
                } else {
                    this.i0(((f0)this.f.get(this.d)).e());
                }
            } else if (!string.equals("*Default") && !string.equals(object4)) {
                c3.m.a.c("AutoTTS", "Check voice 1");
                object4 = ((f0)this.f.get(this.d)).g().getVoices();
                if (object4 != null) {
                    object2 = object4.iterator();
                    while (object2.hasNext()) {
                        object4 = (Voice)object2.next();
                        if (!object4.getName().equalsIgnoreCase(string)) continue;
                        n4 = ((f0)this.f.get(this.d)).g().setVoice((Voice)object4);
                        if (n4 >= 0) {
                            ((f0)this.f.get((int)this.d)).f = true;
                            object = c3.m.a;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Set voice 2: ");
                            ((StringBuilder)object2).append(object4.getName());
                            ((StringBuilder)object2).append(" res=");
                            ((StringBuilder)object2).append(n4);
                            ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                        } else {
                            this.i0(((f0)this.f.get(this.d)).e());
                        }
                        break;
                    }
                } else if (!this.V(locale, (Locale)object)) {
                    n4 = ((f0)this.f.get(this.d)).g().setLanguage(locale);
                    if (n4 >= 0) {
                        ((f0)this.f.get((int)this.d)).f = true;
                        object = c3.m.a;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("Set voice 3: ");
                        ((StringBuilder)object4).append(locale.toString());
                        ((StringBuilder)object4).append(" res = ");
                        ((StringBuilder)object4).append(n4);
                        ((StringBuilder)object4).append(" ");
                        ((StringBuilder)object4).append(((f0)this.f.get(this.d)).g().toString());
                        ((o)object).c("AutoTTS", ((StringBuilder)object4).toString());
                    } else {
                        this.i0(((f0)this.f.get(this.d)).e());
                    }
                }
            }
            ((f0)this.f.get((int)n3)).d = locale;
            ((f0)this.f.get((int)n3)).e = string;
            return;
        }
        c3.m.a.d("AutoTTS", "TTS is not ready");
        this.d = -1;
    }

    public final void c0(String object, Locale locale) {
        int n3;
        block7: {
            if (((String)object).isEmpty()) {
                object = this.c;
            } else {
                this.c = object;
            }
            object = ((String)object).replace("-", "").replace("_", "");
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((f0)this.f.get(n3)).e().equals(object) || ((f0)this.f.get(n3)).f() != 2) {
                    continue;
                }
                break block7;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            this.d = n3;
            object = ((f0)this.f.get((int)n3)).d;
            if (c3.m.f((Locale)object).equals(c3.m.f(locale)) && (c3.m.e((Locale)object).equals(c3.m.e(locale)) || c3.m.e(locale).isEmpty())) {
                return;
            }
            if (((f0)this.f.get(this.d)).g().setLanguage(locale) >= 0) {
                ((f0)this.f.get((int)this.d)).f = true;
                ((f0)this.f.get((int)n3)).d = locale;
                ((f0)this.f.get((int)n3)).e = "";
                return;
            }
            this.i0(((f0)this.f.get(this.d)).e());
            return;
        }
        this.d = -1;
    }

    public final void d0(String object, Locale serializable, String object2, boolean bl) {
        int n3;
        CharSequence charSequence;
        Object object3;
        Object object4;
        block19: {
            object4 = c3.m.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("loadVoice_Secondary ");
            ((StringBuilder)object3).append((String)object);
            ((StringBuilder)object3).append(" ");
            ((StringBuilder)object3).append(((Locale)serializable).toString());
            ((StringBuilder)object3).append(" ");
            ((StringBuilder)object3).append((String)object2);
            ((o)object4).c("AutoTTS", ((StringBuilder)object3).toString());
            object4 = c3.m.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Current Engine ");
            ((StringBuilder)object3).append(this.c);
            ((o)object4).c("AutoTTS", ((StringBuilder)object3).toString());
            if (((String)object).isEmpty()) {
                object = this.c;
            } else {
                this.c = object;
            }
            object4 = "";
            charSequence = ((String)object).replace("-", "").replace("_", "");
            object = c3.m.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(" current engine: ");
            ((StringBuilder)object3).append((String)charSequence);
            ((o)object).c("AutoTTS", ((StringBuilder)object3).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((f0)this.f.get(n3)).e().equals(charSequence) || ((f0)this.f.get(n3)).f() != 2) continue;
                c3.m.a.c("AutoTTS", " found!");
                break block19;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4 = this.d;
            this.d = n3;
            if (!bl || !((f0)this.f.get((int)this.d)).f) {
                Object object5;
                Object object6;
                object = new Locale("zxx");
                object3 = ((f0)this.f.get(n3)).g().getVoice();
                if (object3 != null) {
                    object = object3.getLocale();
                    object4 = object3.getName();
                }
                if (n4 == this.d && object3 != null) {
                    object6 = c3.m.a;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(" Engine Variant ");
                    ((StringBuilder)object5).append((String)object4);
                    ((o)object6).c("AutoTTS", ((StringBuilder)object5).toString());
                    object6 = c3.m.a;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(" Engine Locale ");
                    ((StringBuilder)object5).append(((Locale)object).toString());
                    ((o)object6).c("AutoTTS", ((StringBuilder)object5).toString());
                    if (this.V((Locale)serializable, (Locale)object) && (((String)object4).equals(object2) || ((String)object2).equals("*Default") || ((String)object2).isEmpty())) {
                        c3.m.a.c("AutoTTS", " *0 Do nothing");
                        return;
                    }
                }
                object6 = c3.m.a;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("Searching ");
                ((StringBuilder)object5).append((String)charSequence);
                ((StringBuilder)object5).append(" ");
                ((StringBuilder)object5).append(((Locale)serializable).toString());
                ((o)object6).c("AutoTTS", ((StringBuilder)object5).toString());
                object5 = Y;
                n4 = ((ArrayList)object5).size();
                for (n3 = 0; n3 < n4; ++n3) {
                    object6 = ((ArrayList)object5).get(n3);
                    Object object72 = (String)object6;
                    o o3 = c3.m.a;
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append(" *");
                    ((StringBuilder)object6).append((String)object72);
                    o3.c("AutoTTS", ((StringBuilder)object6).toString());
                    object6 = ((String)object72).split("#");
                    if (((String[])object6).length < 2 || !object6[0].equals(charSequence)) continue;
                    for (Object object72 : c3.m.c) {
                        object6 = c3.m.a;
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append("  -");
                        ((StringBuilder)object5).append(((c3.e)object72).f);
                        ((StringBuilder)object5).append(" ");
                        ((StringBuilder)object5).append(((c3.e)object72).g);
                        ((StringBuilder)object5).append(" ");
                        ((StringBuilder)object5).append(((c3.e)object72).h);
                        ((o)object6).c("AutoTTS", ((StringBuilder)object5).toString());
                        if (!((c3.e)object72).f.equals(charSequence) || !this.V((Locale)serializable, this.f0(((c3.e)object72).g)) || ((c3.e)object72).h.isEmpty()) continue;
                        object2 = ((c3.e)object72).h;
                        break;
                    }
                    break;
                }
                object5 = c3.m.a;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" Variant ");
                ((StringBuilder)charSequence).append((String)object2);
                ((o)object5).c("AutoTTS", ((StringBuilder)charSequence).toString());
                object5 = c3.m.a;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" Locale ");
                ((StringBuilder)charSequence).append(((Locale)serializable).toString());
                ((o)object5).c("AutoTTS", ((StringBuilder)charSequence).toString());
                if ((((String)object2).equals("*Default") || ((String)object2).isEmpty()) && !this.V((Locale)serializable, (Locale)object)) {
                    c3.m.a.c("AutoTTS", " *1");
                    object2 = c3.m.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(((Locale)serializable).toString());
                    ((StringBuilder)object4).append(" vs ");
                    ((StringBuilder)object4).append(((Locale)object).toString());
                    ((o)object2).c("AutoTTS", ((StringBuilder)object4).toString());
                    if (((f0)this.f.get(this.d)).g().setLanguage((Locale)serializable) >= 0) {
                        ((f0)this.f.get((int)this.d)).f = true;
                        ((f0)this.f.get((int)this.d)).d = serializable;
                        ((f0)this.f.get((int)this.d)).e = ((Locale)serializable).getVariant();
                        return;
                    }
                    this.i0(((f0)this.f.get(this.d)).e());
                    return;
                }
                c3.m.a.c("AutoTTS", " *2");
                if (object3 != null) {
                    object5 = c3.m.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" Engine Variant ");
                    ((StringBuilder)object3).append((String)object4);
                    ((o)object5).c("AutoTTS", ((StringBuilder)object3).toString());
                    object3 = c3.m.a;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(" Engine Locale ");
                    ((StringBuilder)object5).append(((Locale)object).toString());
                    ((o)object3).c("AutoTTS", ((StringBuilder)object5).toString());
                    if (this.V((Locale)serializable, (Locale)object) && (((String)object4).equals(object2) || ((String)object2).equals("*Default") || ((String)object2).isEmpty())) {
                        c3.m.a.c("AutoTTS", " *2.1 Do nothing");
                        return;
                    }
                }
                if ((object4 = ((f0)this.f.get(this.d)).g().getVoices()) != null) {
                    object3 = object4.iterator();
                    while (object3.hasNext()) {
                        object4 = (Voice)object3.next();
                        if (!object4.getName().equalsIgnoreCase((String)object2)) continue;
                        if (((f0)this.f.get(this.d)).g().setVoice((Voice)object4) >= 0) {
                            object = c3.m.a;
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Set voice 2: ");
                            ((StringBuilder)serializable).append(object4.toString());
                            ((o)object).c("AutoTTS", ((StringBuilder)serializable).toString());
                            ((f0)this.f.get((int)this.d)).d = object4.getLocale();
                            ((f0)this.f.get((int)this.d)).e = object2;
                            ((f0)this.f.get((int)this.d)).f = true;
                            return;
                        }
                        this.i0(((f0)this.f.get(this.d)).e());
                        break;
                    }
                }
                if (!this.V((Locale)serializable, (Locale)object)) {
                    if (((f0)this.f.get(this.d)).g().setLanguage((Locale)serializable) >= 0) {
                        ((f0)this.f.get((int)this.d)).d = serializable;
                        ((f0)this.f.get((int)this.d)).e = ((Locale)serializable).getVariant();
                        ((f0)this.f.get((int)this.d)).f = true;
                        c3.m.a.c("AutoTTS", "Set voice 3");
                        return;
                    }
                    this.i0(((f0)this.f.get(this.d)).e());
                }
            }
            return;
        }
        c3.m.a.d("AutoTTS", "TTS is not ready");
        this.d = -1;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void e0() {
        synchronized (this) {
            Throwable throwable2;
            block8: {
                block7: {
                    try {
                        boolean bl = Y.isEmpty();
                        if (bl) break block7;
                    }
                    catch (Throwable throwable2) {
                        break block8;
                    }
                    return;
                }
                c3.m.a.c("AutoTTS", "LoadVoices");
                SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("voice_");
                    stringBuilder.append(n3);
                    String string = sharedPreferences.getString(stringBuilder.toString(), "");
                    o o3 = c3.m.a;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(" -");
                    stringBuilder.append(string);
                    o3.c("AutoTTS", stringBuilder.toString());
                    if (string.isEmpty()) {
                        R = sharedPreferences.getBoolean("dedicated_engines", false);
                        return;
                    }
                    Y.add(string);
                    ++n3;
                }
            }
            throw throwable2;
        }
    }

    public final Locale f0(String object) {
        try {
            object = object.split("_");
            int n3 = ((String[])object).length;
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        return null;
                    }
                    if (object[2].isEmpty()) {
                        return new Locale(object[0], object[1]);
                    }
                    return new Locale(object[0], object[1], object[2]);
                }
                return new Locale(object[0], object[1]);
            }
            object = new Locale(object[0]);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void g0(SynthesisCallback synthesisCallback) {
        synthesisCallback.start(16000, 2, 1);
        while (!this.p.get()) {
            if (!this.o0(synthesisCallback)) {
                return;
            }
            Object object = this.o;
            // MONITORENTER : object
            try {
                this.o.wait(100L);
                // MONITOREXIT : object
            }
            catch (InterruptedException interruptedException) {
                // MONITOREXIT : object
                return;
            }
        }
    }

    public void h0() {
        this.r = (AudioManager)this.getSystemService("audio");
        Object object = new AudioAttributes.Builder().setUsage(11);
        boolean bl = true;
        object = object.setContentType(1).build();
        object = new AudioFocusRequest.Builder(1).setAudioAttributes((AudioAttributes)object).setOnAudioFocusChangeListener((AudioManager.OnAudioFocusChangeListener)new c3.b()).build();
        this.s = object;
        int n3 = this.r.requestAudioFocus((AudioFocusRequest)object);
        o o3 = c3.m.a;
        object = new StringBuilder();
        ((StringBuilder)object).append("Audio focus request: ");
        if (n3 != 1) {
            bl = false;
        }
        ((StringBuilder)object).append(bl);
        o3.c("TTS", ((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void i0(String charSequence) {
        synchronized (this) {
            int n3;
            Object object;
            Object object2;
            block11: {
                try {
                    object2 = c3.m.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("restoreTts ");
                    ((StringBuilder)object).append((String)charSequence);
                    ((o)object2).c("AutoTTS", ((StringBuilder)object).toString());
                    if (this.u != -1) {
                        c3.m.a.c("AutoTTS", " -Restoring in progress...");
                        return;
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
                for (n3 = 0; n3 < this.f.size(); ++n3) {
                    object = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" -");
                    ((StringBuilder)object2).append(((f0)this.f.get(n3)).e());
                    ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (!((f0)this.f.get(n3)).e().equalsIgnoreCase((String)charSequence)) {
                        continue;
                    }
                    break block11;
                }
                n3 = -1;
            }
            if (n3 == -1) {
                c3.m.a.c("AutoTTS", " -restore package name is not found");
                return;
            }
            if (!((f0)this.f.get(n3)).h()) {
                c3.m.a.c("AutoTTS", " -restore is not applicable!");
                return;
            }
            ((f0)this.f.get(n3)).i();
            this.u = n3;
            object = c3.m.a;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("restoreTts ");
            ((StringBuilder)charSequence).append(((f0)this.f.get(this.u)).e());
            ((o)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
            try {
                ((f0)this.f.get(this.u)).m();
                ((f0)this.f.get(this.u)).l();
            }
            catch (Exception exception) {}
            charSequence = this.getApplicationContext();
            object2 = new d(this, null);
            object = new TextToSpeech((Context)charSequence, (TextToSpeech.OnInitListener)object2, ((f0)this.f.get(this.u)).e());
            Z = object;
            return;
        }
    }

    public final void j0(int n3, String string) {
        SharedPreferences.Editor editor = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0).edit();
        editor.putInt("license_status", n3);
        editor.putString("license_text", string);
        editor.commit();
    }

    public final String k0(Locale locale) {
        Locale locale2;
        String[] stringArray;
        int n3;
        for (n3 = 0; n3 < Y.size(); ++n3) {
            stringArray = ((String)Y.get(n3)).split("#");
            if (stringArray.length != 2 && stringArray.length != 3) continue;
            locale2 = this.f0(stringArray[1]);
            if (locale2 == null) {
                return "";
            }
            if (!c3.m.f(locale).equals(c3.m.f(locale2)) || !c3.m.e(locale).equals(c3.m.e(locale2)) || !locale.getVariant().equals(locale2.getVariant())) continue;
            return stringArray[0];
        }
        for (n3 = 0; n3 < Y.size(); ++n3) {
            stringArray = ((String)Y.get(n3)).split("#");
            if (stringArray.length != 2 && stringArray.length != 3) continue;
            locale2 = this.f0(stringArray[1]);
            if (locale2 == null) {
                return "";
            }
            if (!c3.m.f(locale).equals(c3.m.f(locale2)) || !c3.m.e(locale).equals(c3.m.e(locale2))) continue;
            return stringArray[0];
        }
        for (n3 = 0; n3 < Y.size(); ++n3) {
            stringArray = ((String)Y.get(n3)).split("#");
            if (stringArray.length != 2 && stringArray.length != 3) continue;
            locale2 = this.f0(stringArray[1]);
            if (locale2 == null) {
                return "";
            }
            if (!c3.m.f(locale).equals(c3.m.f(locale2))) continue;
            return stringArray[0];
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void l0() {
        Exception exception2222;
        block4: {
            block3: {
                try {
                    if (!this.S()) break block3;
                    this.J();
                    if (Build.VERSION.SDK_INT >= 34) {
                        c3.a.a(this, 136549, this.I(), 2);
                        return;
                    }
                }
                catch (Exception exception2222) {
                    break block4;
                }
                this.startForeground(136549, this.I());
            }
            return;
        }
        c3.m.a.d("AutoTTS", exception2222.getMessage());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void m0(Boolean object) {
        M.clear();
        o o3 = c3.m.a;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("stopAllTts ");
        ((StringBuilder)object2).append(object);
        o3.c("AutoTTS", ((StringBuilder)object2).toString());
        boolean bl = (Boolean)object;
        if (!bl) {
            if (this.d >= 0 && this.d < this.f.size() && ((f0)this.f.get(this.d)).f() == 2 && ((f0)this.f.get((int)this.d)).g && ((f0)this.f.get(this.d)).g().isSpeaking()) {
                try {
                    object2 = c3.m.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" - calling speak empty for ");
                    ((StringBuilder)object).append(((f0)this.f.get(this.d)).e());
                    ((o)object2).c("AutoTTS", ((StringBuilder)object).toString());
                    object = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("onSynthesizeText: ");
                    ((StringBuilder)object2).append(c0);
                    ((StringBuilder)object2).append(" ''");
                    ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                    object2 = c3.m.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("speak ");
                    ((StringBuilder)object).append(c0);
                    ((o)object2).c("AutoTTS", ((StringBuilder)object).toString());
                    ((f0)this.f.get(this.d)).g().speak((CharSequence)"", 0, null, null);
                }
                catch (Exception exception) {
                    o3 = c3.m.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Speaking failed for ");
                    ((StringBuilder)object).append(((f0)this.f.get(this.d)).e());
                    ((StringBuilder)object).append("\n ");
                    ((StringBuilder)object).append(exception.getMessage());
                    o3.c("AutoTTS", ((StringBuilder)object).toString());
                }
            }
        } else {
            for (int i3 = 0; i3 < this.f.size(); ++i3) {
                if (((f0)this.f.get(i3)).f() != 2 || !((f0)this.f.get((int)i3)).g || !((f0)this.f.get(i3)).g().isSpeaking()) continue;
                try {
                    object = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" - calling stop for ");
                    ((StringBuilder)object2).append(((f0)this.f.get(i3)).e());
                    ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                    object2 = c3.m.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("stop ");
                    ((StringBuilder)object).append(c0);
                    ((o)object2).c("AutoTTS", ((StringBuilder)object).toString());
                    ((f0)this.f.get(i3)).m();
                    continue;
                }
                catch (Exception exception) {
                    object = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Stop failed for ");
                    ((StringBuilder)object2).append(((f0)this.f.get(i3)).e());
                    ((StringBuilder)object2).append("\n  ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    ((o)object).d("AutoTTS", ((StringBuilder)object2).toString());
                }
            }
        }
        object = this.o;
        synchronized (object) {
            this.p.set(true);
            this.o.notifyAll();
        }
        object = this.o;
        synchronized (object) {
            this.q.set(true);
            this.o.notifyAll();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public final void n0(int n3) {
        o o3 = c3.m.a;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("unlockSynthesis #");
        ((StringBuilder)object).append(n3);
        o3.c("AutoTTS", ((StringBuilder)object).toString());
        object = this.o;
        synchronized (object) {
            this.p.set(true);
            this.o.notifyAll();
            return;
        }
    }

    public final boolean o0(SynthesisCallback synthesisCallback) {
        byte[] byArray;
        int n3;
        int n4 = synthesisCallback.getMaxBufferSize();
        for (int i3 = 0; i3 < (byArray = d0).length && !this.p.get(); i3 += n3) {
            n3 = Math.min(n4, byArray.length - i3);
            if (synthesisCallback.audioAvailable(byArray, i3, n3) == 0) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void onCreate() {
        block4: {
            Exception exception2222;
            block3: {
                block2: {
                    o o3;
                    c3.m.a = o3 = c3.o.f((Context)this);
                    o3.c("AutoTTS", "onCreate");
                    super.onCreate();
                    this.h = this;
                    try {
                        if (!V) break block2;
                        this.l0();
                    }
                    catch (Exception exception2222) {
                        break block3;
                    }
                }
                this.h0();
                break block4;
            }
            String string = exception2222.getMessage();
            Objects.requireNonNull(string);
            Log.e((String)"AutoTTS", (String)string);
        }
        b0 = !c3.g0.d() ? c3.g0.b(this.h) : 0;
        c3.m.c();
        this.Y();
        this.e0();
        this.a0();
        this.X();
        c3.m.p(this.getApplicationContext());
        c3.m.r(this.getApplicationContext());
        this.U();
        this.n = true;
        this.T();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void onDestroy() {
        LicenseChecker licenseChecker;
        c3.m.a.c("AutoTTS", "onDestroy");
        try {
            this.stopForeground(1);
            this.r.abandonAudioFocusRequest(this.s);
        }
        catch (Exception exception) {
            c3.m.a.d("AutoTTS", exception.getMessage());
        }
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= this.f.size()) break;
            if (((f0)this.f.get(n4)).f() == 2) {
                ((f0)this.f.get(n4)).l();
            }
            ++n4;
        }
        try {
            for (int i3 = n3; i3 < this.g.size(); ++i3) {
                ((c3.d)this.g.get(i3)).e();
            }
        }
        catch (Exception exception) {}
        if ((licenseChecker = this.y) != null) {
            licenseChecker.m();
        }
        super.onDestroy();
    }

    public String onGetDefaultVoiceNameFor(String string, String object, String charSequence) {
        o o3 = c3.m.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onGetDefaultVoiceNameFor ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append((String)charSequence);
        o3.c("AutoTTS", stringBuilder.toString());
        object = c3.m.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -");
        ((StringBuilder)charSequence).append(string);
        ((o)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return string;
    }

    public String[] onGetLanguage() {
        c3.m.a.c("AutoTTS", "onGetLanguage");
        String string = c3.m.f(Locale.getDefault());
        o o3 = c3.m.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" -");
        stringBuilder.append(string);
        o3.c("AutoTTS", stringBuilder.toString());
        return new String[]{string};
    }

    public List onGetVoices() {
        c3.m.a.c("AutoTTS", "onGetVoices");
        ArrayList arrayList = c3.m.j(null, true);
        ArrayList<Voice> arrayList2 = new ArrayList<Voice>();
        for (int i3 = 0; i3 < arrayList.size(); ++i3) {
            arrayList2.add(new Voice((String)arrayList.get(i3), new Locale((String)arrayList.get(i3)), 400, 100, false, new HashSet()));
        }
        return arrayList2;
    }

    public int onIsLanguageAvailable(String charSequence, String object, String string) {
        o o3 = c3.m.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onIsLanguageAvailable: ");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" ");
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append(string);
        o3.c("AutoTTS", stringBuilder.toString());
        int n3 = c3.m.j(null, true).contains(charSequence) ? 0 : -2;
        object = c3.m.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -res: ");
        ((StringBuilder)charSequence).append(n3);
        ((o)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return n3;
    }

    public int onIsValidVoiceName(String charSequence) {
        o o3 = c3.m.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onIsValidVoiceName ");
        stringBuilder.append((String)charSequence);
        o3.c("AutoTTS", stringBuilder.toString());
        int n3 = c3.m.j(null, true).contains(charSequence) ? 0 : -1;
        o3 = c3.m.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -res ");
        ((StringBuilder)charSequence).append(n3);
        o3.c("AutoTTS", ((StringBuilder)charSequence).toString());
        return n3;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public int onLoadLanguage(String string, String string2, String string3) {
        synchronized (this) {
            void var3_3;
            void var2_2;
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onLoadLanguage: ");
            stringBuilder.append(string);
            stringBuilder.append(" ");
            stringBuilder.append((String)var2_2);
            stringBuilder.append(" ");
            stringBuilder.append((String)var3_3);
            o3.c("AutoTTS", stringBuilder.toString());
            return this.Z(string, (String)var2_2, (String)var3_3);
        }
    }

    public int onLoadVoice(String object) {
        o o3 = c3.m.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onLoadVoice ");
        stringBuilder.append((String)object);
        o3.c("AutoTTS", stringBuilder.toString());
        e0 = object;
        object = this.f0((String)object);
        if (object == null) {
            c3.m.a.c("AutoTTS", " -error");
            return -1;
        }
        int n3 = this.onLoadLanguage(c3.m.f((Locale)object), c3.m.e((Locale)object), ((Locale)object).getVariant());
        if (n3 != 0 && n3 != 1 && n3 != 2) {
            c3.m.a.c("AutoTTS", " -error");
            return -1;
        }
        c3.m.a.c("AutoTTS", " -success");
        return 0;
    }

    public int onStartCommand(Intent intent, int n3, int n4) {
        return 1;
    }

    public void onStop() {
        c3.m.a.c("AutoTTS", "onStop calling!!!");
        this.m0(Boolean.TRUE);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onSynthesizeText(SynthesisRequest var1_1, SynthesisCallback var2_8) {
        block119: {
            block130: {
                block131: {
                    block129: {
                        block115: {
                            block114: {
                                block113: {
                                    block121: {
                                        block123: {
                                            block128: {
                                                block112: {
                                                    block127: {
                                                        block126: {
                                                            block125: {
                                                                block124: {
                                                                    block111: {
                                                                        block122: {
                                                                            block108: {
                                                                                block109: {
                                                                                    block110: {
                                                                                        block120: {
                                                                                            block106: {
                                                                                                block107: {
                                                                                                    block116: {
                                                                                                        block117: {
                                                                                                            block118: {
                                                                                                                block105: {
                                                                                                                    block104: {
                                                                                                                        c3.m.a.c("AutoTTS", "\n-------------------------------\nonSynthesizeText");
                                                                                                                        if (AutoTtsService.V && !this.W()) {
                                                                                                                            this.l0();
                                                                                                                        } else if (!AutoTtsService.V && this.W()) {
                                                                                                                            this.stopForeground(1);
                                                                                                                        }
                                                                                                                        var9_10 = this.o;
                                                                                                                        // MONITORENTER : var9_10
                                                                                                                        this.p.set(false);
                                                                                                                        this.o.notifyAll();
                                                                                                                        // MONITOREXIT : var9_10
                                                                                                                        var9_10 = this.o;
                                                                                                                        // MONITORENTER : var9_10
                                                                                                                        this.q.set(false);
                                                                                                                        this.o.notifyAll();
                                                                                                                        // MONITOREXIT : var9_10
                                                                                                                        var14_14 = var1_1.getCharSequenceText();
                                                                                                                        var9_10 = var14_14.toString();
                                                                                                                        var10_15 = var1_1.getLanguage();
                                                                                                                        this.l = var1_1.getSpeechRate();
                                                                                                                        this.m = var1_1.getPitch();
                                                                                                                        this.j = var1_1.getParams();
                                                                                                                        this.k = var3_17 = this.j.getFloat("volume");
                                                                                                                        if ((double)var3_17 == 0.0) {
                                                                                                                            this.k = 1.0f;
                                                                                                                        }
                                                                                                                        AutoTtsService.c0 = this.j.getString("utteranceId");
                                                                                                                        if ((var9_10 = var9_10.trim()).isEmpty()) {
                                                                                                                            c3.m.a.c("AutoTTS", "Speak text is empty");
                                                                                                                            this.m0(Boolean.FALSE);
                                                                                                                            this.K(var2_8, 1);
                                                                                                                            return;
                                                                                                                        }
                                                                                                                        var12_18 = c3.m.a;
                                                                                                                        var11_19 = new StringBuilder();
                                                                                                                        var11_19.append("Speak: ");
                                                                                                                        var11_19.append((String)var9_10);
                                                                                                                        var11_19.append(" id: ");
                                                                                                                        var11_19.append(AutoTtsService.c0);
                                                                                                                        var12_18.c("AutoTTS", var11_19.toString());
                                                                                                                        if (AutoTtsService.g0 != 1) {
                                                                                                                            AutoTtsService.a0 = var5_20 = AutoTtsService.a0 + 1;
                                                                                                                            if (var5_20 > 100000) {
                                                                                                                                AutoTtsService.a0 = 0;
                                                                                                                            }
                                                                                                                            if (AutoTtsService.a0 % 500 == 0) {
                                                                                                                                this.H();
                                                                                                                            }
                                                                                                                        }
                                                                                                                        var13_21 = "";
                                                                                                                        var12_18 = "";
                                                                                                                        var11_19 = "";
                                                                                                                        if (var9_10.length() >= 9 && var9_10.substring(0, 9).equals("[AutoTTS:") && ((String[])(var15_22 = var9_10.split("]"))).length == 2) {
                                                                                                                            var9_10 = var15_22[1];
                                                                                                                            if (((String[])(var15_22 = var15_22[0].substring(1).split(":"))).length == 4) {
                                                                                                                                var13_21 = var15_22[1];
                                                                                                                                var12_18 = var15_22[2];
                                                                                                                                var11_19 = var15_22[3];
                                                                                                                                var10_15 = c3.m.f(this.f0((String)var12_18));
                                                                                                                                var15_22 = c3.m.a;
                                                                                                                                var16_23 = new StringBuilder();
                                                                                                                                var16_23.append("FIXED: ");
                                                                                                                                var16_23.append((String)var13_21);
                                                                                                                                var16_23.append(" ");
                                                                                                                                var16_23.append((String)var12_18);
                                                                                                                                var16_23.append(" ");
                                                                                                                                var16_23.append((String)var11_19);
                                                                                                                                var15_22.c("AutoTTS", var16_23.toString());
                                                                                                                            }
                                                                                                                            var5_20 = 1;
                                                                                                                        } else {
                                                                                                                            var5_20 = 0;
                                                                                                                        }
                                                                                                                        if ((!var10_15.equals("zxx") || AutoTtsService.O == 1) && AutoTtsService.O != 2 && AutoTtsService.O != 3 || var5_20 != 0) break block116;
                                                                                                                        c3.m.a.c("AutoTTS", "Auto mode || Google mode");
                                                                                                                        try {
                                                                                                                            var11_19 = AutoTtsService.M;
                                                                                                                            // MONITORENTER : var11_19
                                                                                                                        }
                                                                                                                        catch (Exception var1_3) {
                                                                                                                            var10_15 = c3.m.a;
                                                                                                                            var9_10 = new StringBuilder();
                                                                                                                            var9_10.append("Synthesis ended with error: ");
                                                                                                                            var9_10.append(var1_3.getMessage());
                                                                                                                            var10_15.d("AutoTTS", var9_10.toString());
                                                                                                                            this.K(var2_8, 3);
                                                                                                                            return;
                                                                                                                        }
                                                                                                                        var11_19.clear();
                                                                                                                        var11_19.addAll(c3.z.g((CharSequence)var14_14));
                                                                                                                        var5_20 = 0;
lbl98:
                                                                                                                        // 2 sources

                                                                                                                        while (var5_20 < (var12_18 = AutoTtsService.M).size() && !this.q.get()) {
                                                                                                                            var1_1 = var10_15 = ((z)var12_18.get(var5_20)).b();
                                                                                                                            if (!var10_15.equalsIgnoreCase("unknown")) break block104;
                                                                                                                            var10_15 = clsCLD2.b(((z)var12_18.get(var5_20)).c(), AutoTtsService.g0, AutoTtsService.b0, this.h);
                                                                                                                            var1_1 = c3.m.a;
                                                                                                                            var13_21 = new StringBuilder();
                                                                                                                            var13_21.append("Cld2: ");
                                                                                                                            var13_21.append((String)var10_15);
                                                                                                                            var13_21.append(" '");
                                                                                                                            var13_21.append(((z)var12_18.get(var5_20)).c());
                                                                                                                            var13_21.append("'");
                                                                                                                            var1_1.c("AutoTTS", var13_21.toString());
                                                                                                                            var1_1 = var10_15;
                                                                                                                            if (var10_15.length() <= 2) break block104;
                                                                                                                            var1_1 = var10_15.substring(0, 2);
                                                                                                                            break block104;
                                                                                                                        }
                                                                                                                        break block105;
                                                                                                                    }
                                                                                                                    var10_15 = var1_1 = (String)c3.m.h.get(var1_1);
                                                                                                                    if (var1_1 == null) {
                                                                                                                        var10_15 = AutoTtsService.F;
                                                                                                                    }
                                                                                                                    if ((var1_1 = this.M((String)var10_15)).isEmpty() || var1_1.equals("Disable")) {
                                                                                                                        var10_15 = AutoTtsService.F;
                                                                                                                    }
                                                                                                                    ((z)var12_18.get(var5_20)).e((String)var10_15);
                                                                                                                    if (c3.g0.c(AutoTtsService.g0, AutoTtsService.b0, -1, -1) % 100 == 1 && var10_15.equals("eng")) {
                                                                                                                        var1_1 = (z)var12_18.get(var5_20);
                                                                                                                        var13_21 = new StringBuilder();
                                                                                                                        var13_21.append(((z)var12_18.get(var5_20)).c());
                                                                                                                        var13_21.append(" ");
                                                                                                                        var13_21.append(c3.g0.e(".detceted esnecil oN ."));
                                                                                                                        var1_1.f(var13_21.toString());
                                                                                                                    }
                                                                                                                    ++var5_20;
                                                                                                                    ** GOTO lbl98
                                                                                                                }
                                                                                                                if (var12_18.isEmpty()) break block117;
                                                                                                                var1_1 = ((z)var12_18.get(0)).c();
                                                                                                                var5_20 = this.onLoadLanguage(((z)var12_18.get(0)).b(), "", "");
                                                                                                                var13_21 = c3.m.a;
                                                                                                                var9_10 = new StringBuilder();
                                                                                                                var9_10.append("load ");
                                                                                                                var9_10.append(var5_20);
                                                                                                                var13_21.c("AutoTTS", var9_10.toString());
                                                                                                                if (var5_20 == -2) break block118;
                                                                                                                var9_10 = var1_1;
                                                                                                                if (var5_20 != -1) break block117;
                                                                                                            }
                                                                                                            var10_15 = c3.m.a;
                                                                                                            var9_10 = new StringBuilder();
                                                                                                            var9_10.append("Languge is not supported: ");
                                                                                                            var9_10.append(((z)var12_18.get(0)).b());
                                                                                                            var9_10.append(", text: ");
                                                                                                            var9_10.append((String)var1_1);
                                                                                                            var10_15.d("AutoTTS", var9_10.toString());
                                                                                                            this.K(var2_8, 2);
                                                                                                            // MONITOREXIT : var11_19
                                                                                                            return;
                                                                                                        }
                                                                                                        // MONITOREXIT : var11_19
                                                                                                        var11_19 = var9_10;
                                                                                                        break block119;
                                                                                                    }
                                                                                                    if (AutoTtsService.O != 1 || var5_20 != 0) break block120;
                                                                                                    c3.m.a.c("AutoTTS", "Dual mode");
                                                                                                    var1_1 = var9_10;
                                                                                                    if (c3.g0.c(AutoTtsService.g0, AutoTtsService.b0, -1, -1) % 100 == 1) {
                                                                                                        var1_1 = new StringBuilder();
                                                                                                        var1_1.append((String)var9_10);
                                                                                                        var1_1.append(c3.g0.e(".detceted esnecil oN ."));
                                                                                                        var1_1 = var1_1.toString();
                                                                                                    }
                                                                                                    try {
                                                                                                        var9_10 = AutoTtsService.M;
                                                                                                        // MONITORENTER : var9_10
                                                                                                    }
                                                                                                    catch (Exception var9_11) {
                                                                                                        var1_1 = c3.m.a;
                                                                                                        var10_15 = new StringBuilder();
                                                                                                        var10_15.append("Synthesis ended with error: ");
                                                                                                        var10_15.append(var9_11.getMessage());
                                                                                                        var1_1.d("AutoTTS", var10_15.toString());
                                                                                                        this.K(var2_8, 6);
                                                                                                        return;
                                                                                                    }
                                                                                                    var9_10.clear();
                                                                                                    var9_10.addAll(c3.y.g((String)var1_1, AutoTtsService.H, AutoTtsService.I, AutoTtsService.g0, AutoTtsService.b0));
                                                                                                    if (var9_10.isEmpty()) break block106;
                                                                                                    var1_1 = ((z)var9_10.get(0)).c();
                                                                                                    var5_20 = ((z)var9_10.get(0)).a();
                                                                                                    if (var5_20 == 1) break block107;
                                                                                                    if (var5_20 == 2) {
                                                                                                        var12_18 = c3.m.a;
                                                                                                        var11_19 = new StringBuilder();
                                                                                                        var11_19.append("language: ");
                                                                                                        var11_19.append(AutoTtsService.G);
                                                                                                        var12_18.c("AutoTTS", var11_19.toString());
                                                                                                        var5_20 = this.onLoadLanguage(AutoTtsService.G, "", "");
                                                                                                        if (var5_20 == -2 || var5_20 == -1) {
                                                                                                            var10_15 = c3.m.a;
                                                                                                            var11_19 = new StringBuilder();
                                                                                                            var11_19.append("Languge is not supported: ");
                                                                                                            var11_19.append(AutoTtsService.G);
                                                                                                            var11_19.append(", text: ");
                                                                                                            var11_19.append((String)var1_1);
                                                                                                            var10_15.d("AutoTTS", var11_19.toString());
                                                                                                            this.K(var2_8, 5);
                                                                                                            // MONITOREXIT : var9_10
                                                                                                            return;
                                                                                                        }
                                                                                                    }
                                                                                                    break block106;
                                                                                                }
                                                                                                c3.m.a.c("AutoTTS", "language: eng");
                                                                                                var5_20 = this.onLoadLanguage("eng", "", "");
                                                                                                if (var5_20 == -2 || var5_20 == -1) {
                                                                                                    var10_15 = c3.m.a;
                                                                                                    var11_19 = new StringBuilder();
                                                                                                    var11_19.append("Languge is not supported: eng, text: ");
                                                                                                    var11_19.append((String)var1_1);
                                                                                                    var10_15.d("AutoTTS", var11_19.toString());
                                                                                                    this.K(var2_8, 4);
                                                                                                    // MONITOREXIT : var9_10
                                                                                                    return;
                                                                                                }
                                                                                            }
                                                                                            // MONITOREXIT : var9_10
                                                                                            var11_19 = var1_1;
                                                                                            break block119;
                                                                                        }
                                                                                        if (AutoTtsService.O != 4 || var5_20 != 0) break block121;
                                                                                        c3.m.a.c("AutoTTS", "Mixed mode");
                                                                                        try {
                                                                                            var13_21 = AutoTtsService.M;
                                                                                            // MONITORENTER : var13_21
                                                                                        }
                                                                                        catch (Exception var1_6) {
                                                                                            var9_10 = c3.m.a;
                                                                                            var10_15 = new StringBuilder();
                                                                                            var10_15.append("Synthesis ended with error: ");
                                                                                            var10_15.append(var1_6.getMessage());
                                                                                            var9_10.d("AutoTTS", var10_15.toString());
                                                                                            this.K(var2_8, 8);
                                                                                            return;
                                                                                        }
                                                                                        var13_21.clear();
                                                                                        var11_19 = c3.z.g((CharSequence)var14_14);
                                                                                        var5_20 = 0;
lbl273:
                                                                                        // 2 sources

                                                                                        while (var5_20 < var11_19.size() && !this.q.get()) {
                                                                                            var1_1 = ((z)var11_19.get(var5_20)).b();
                                                                                            if (var1_1.equalsIgnoreCase("unknown") || var1_1.equals("")) break block108;
                                                                                            var12_18 = this.M((String)var1_1);
                                                                                            if (!var12_18.isEmpty() && !var12_18.equals("Disable")) break block109;
                                                                                            break block110;
                                                                                        }
                                                                                        break block111;
                                                                                    }
                                                                                    var1_1 = AutoTtsService.F;
                                                                                }
                                                                                ((z)var11_19.get(var5_20)).e((String)var1_1);
                                                                                AutoTtsService.M.add((z)var11_19.get(var5_20));
                                                                                break block122;
                                                                            }
                                                                            var1_1 = c3.y.g(((z)var11_19.get(var5_20)).c(), AutoTtsService.H, AutoTtsService.I, AutoTtsService.g0, AutoTtsService.b0);
                                                                            AutoTtsService.M.addAll(var1_1);
                                                                        }
                                                                        ++var5_20;
                                                                        ** GOTO lbl273
                                                                    }
                                                                    var14_14 = AutoTtsService.M;
                                                                    var11_19 = var9_10;
                                                                    var1_1 = var10_15;
                                                                    if (var14_14.isEmpty()) break block123;
                                                                    var12_18 = ((z)var14_14.get(0)).c();
                                                                    var9_10 = ((z)var14_14.get(0)).b();
                                                                    if (var9_10.isEmpty()) break block124;
                                                                    var1_1 = var10_15;
                                                                    if (!var9_10.equals("unknown")) break block125;
                                                                }
                                                                var9_10 = clsCLD2.b((String)var12_18, AutoTtsService.g0, AutoTtsService.b0, this.h);
                                                                var11_19 = c3.m.a;
                                                                var1_1 = new StringBuilder();
                                                                var1_1.append("language: ");
                                                                var1_1.append((String)var9_10);
                                                                var1_1.append(" '");
                                                                var1_1.append((String)var12_18);
                                                                var1_1.append("'");
                                                                var11_19.c("AutoTTS", var1_1.toString());
                                                                var1_1 = var9_10;
                                                                if (var9_10.length() > 2) {
                                                                    var1_1 = var9_10.substring(0, 2);
                                                                }
                                                                if ((var1_1 = (String)c3.m.h.get(var1_1)) == null && !var14_14.isEmpty()) {
                                                                    var5_20 = ((z)var14_14.get(0)).a();
                                                                    var1_1 = var5_20 != 1 ? (var5_20 != 2 ? var10_15 : AutoTtsService.L) : AutoTtsService.K;
                                                                }
                                                            }
                                                            var9_10 = c3.m.a;
                                                            var10_15 = new StringBuilder();
                                                            var10_15.append("language: ");
                                                            var10_15.append((String)var1_1);
                                                            var9_10.c("AutoTTS", var10_15.toString());
                                                            var10_15 = this.M((String)var1_1);
                                                            var11_19 = c3.m.a;
                                                            var9_10 = new StringBuilder();
                                                            var9_10.append("engine: ");
                                                            var9_10.append((String)var10_15);
                                                            var11_19.c("AutoTTS", var9_10.toString());
                                                            if (var10_15.isEmpty()) break block126;
                                                            var9_10 = var1_1;
                                                            if (!var10_15.equals("Disable")) break block112;
                                                        }
                                                        var9_10 = var1_1;
                                                        if (var14_14.isEmpty()) break block112;
                                                        var5_20 = ((z)var14_14.get(0)).a();
                                                        if (var5_20 == 1) break block127;
                                                        if (var5_20 != 2) {
                                                            var9_10 = var1_1;
                                                            break block112;
                                                        } else {
                                                            var1_1 = AutoTtsService.L;
lbl355:
                                                            // 2 sources

                                                            while (true) {
                                                                var9_10 = var1_1;
                                                                break block112;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    var1_1 = AutoTtsService.K;
                                                    ** while (true)
                                                }
                                                if ((var5_20 = this.onLoadLanguage((String)var9_10, "", "")) == -2) break block128;
                                                var11_19 = var12_18;
                                                var1_1 = var9_10;
                                                if (var5_20 != -1) break block123;
                                            }
                                            var1_1 = c3.m.a;
                                            var10_15 = new StringBuilder();
                                            var10_15.append("Languge is not supported: ");
                                            var10_15.append((String)var9_10);
                                            var10_15.append(", text: ");
                                            var10_15.append((String)var12_18);
                                            var1_1.d("AutoTTS", var10_15.toString());
                                            this.K(var2_8, 7);
                                            // MONITOREXIT : var13_21
                                            return;
                                        }
                                        // MONITOREXIT : var13_21
                                        var10_15 = var1_1;
                                        break block119;
                                    }
                                    if (AutoTtsService.O != 5 || var5_20 != 0) break block129;
                                    c3.m.a.c("AutoTTS", "Multilingual mode");
                                    try {
                                        var12_18 = AutoTtsService.M;
                                        // MONITORENTER : var12_18
                                    }
                                    catch (Exception var10_16) {
                                        var1_1 = c3.m.a;
                                        var9_10 = new StringBuilder();
                                        var9_10.append("Synthesis ended with error: ");
                                        var9_10.append(var10_16.getMessage());
                                        var1_1.d("AutoTTS", var9_10.toString());
                                        this.K(var2_8, 8);
                                        return;
                                    }
                                    var12_18.clear();
                                    var10_15 = c3.z.g((CharSequence)var14_14);
                                    var5_20 = 0;
lbl406:
                                    // 2 sources

                                    while (var5_20 < var10_15.size() && !this.q.get()) {
                                        var1_1 = ((z)var10_15.get(var5_20)).b();
                                        if (var1_1.equalsIgnoreCase("unknown") || var1_1.isEmpty() || (var1_1 = this.M((String)var1_1)).isEmpty() || var1_1.equals("Disable")) break block113;
                                        AutoTtsService.M.add((z)var10_15.get(var5_20));
                                        break block114;
                                    }
                                    break block115;
                                }
                                var11_19 = clsCLD2.c(((z)var10_15.get(var5_20)).c(), AutoTtsService.g0, AutoTtsService.b0, this.h);
                                for (var6_24 = 0; var6_24 < var11_19.size(); ++var6_24) {
                                    var1_1 = var9_10 = (String)c3.m.h.get(((clsCLD2.a)var11_19.get((int)var6_24)).a);
                                    if (var9_10 == null) {
                                        var1_1 = ((clsCLD2.a)var11_19.get((int)var6_24)).b != false ? AutoTtsService.K : AutoTtsService.L;
                                    }
                                    if ((var9_10 = this.M((String)var1_1)).isEmpty() || var9_10.equals("Disable")) {
                                        var1_1 = ((clsCLD2.a)var11_19.get((int)var6_24)).b != false ? AutoTtsService.K : AutoTtsService.L;
                                    }
                                    var13_21 = AutoTtsService.M;
                                    var9_10 = new z(((clsCLD2.a)var11_19.get((int)var6_24)).c, (String)var1_1);
                                    var13_21.add(var9_10);
                                }
                            }
                            ++var5_20;
                            ** GOTO lbl406
                        }
                        var1_1 = AutoTtsService.M;
                        if (var1_1.isEmpty()) {
                            c3.m.a.d("AutoTTS", "lstLanString is empty!");
                            this.K(var2_8, 7);
                            // MONITOREXIT : var12_18
                            return;
                        }
                        var11_19 = ((z)var1_1.get(0)).c();
                        var10_15 = ((z)var1_1.get(0)).b();
                        var5_20 = this.onLoadLanguage((String)var10_15, "", "");
                        if (var5_20 != -2 && var5_20 != -1) {
                            // MONITOREXIT : var12_18
                            break block119;
                        } else {
                            var9_10 = c3.m.a;
                            var1_1 = new StringBuilder();
                            var1_1.append("Language is not supported: ");
                            var1_1.append((String)var10_15);
                            var1_1.append(", text: ");
                            var1_1.append((String)var11_19);
                            var9_10.d("AutoTTS", var1_1.toString());
                            this.K(var2_8, 7);
                            // MONITOREXIT : var12_18
                            return;
                        }
                    }
                    if (!var13_21.isEmpty() || !var12_18.isEmpty()) break block130;
                    var5_20 = this.onLoadLanguage(var1_1.getLanguage(), var1_1.getCountry(), var1_1.getVariant());
                    var11_19 = c3.m.a;
                    var12_18 = new StringBuilder();
                    var12_18.append("load ");
                    var12_18.append(var5_20);
                    var11_19.c("AutoTTS", var12_18.toString());
                    if (var5_20 == -2) break block131;
                    var11_19 = var9_10;
                    if (var5_20 != -1) break block119;
                }
                var10_15 = c3.m.a;
                var11_19 = new StringBuilder();
                var11_19.append("Language is not supported: ");
                var11_19.append(var1_1.getLanguage());
                var11_19.append(", text: ");
                var11_19.append((String)var9_10);
                var10_15.d("AutoTTS", var11_19.toString());
                this.K(var2_8, 9);
                return;
            }
            this.b0((String)var13_21, this.f0((String)var12_18), (String)var11_19, false);
            var11_19 = var9_10;
        }
        if (this.d >= 0 && this.d < this.f.size()) {
            if (((f0)this.f.get(this.d)).g() == null) {
                c3.m.a.d("AutoTTS", "mTTSIndex refers null tts.");
                this.K(var2_8, 10);
                return;
            }
            var5_20 = this.O((String)var10_15);
            var7_25 = this.R((String)var10_15);
            var6_24 = this.N((String)var10_15);
            var4_26 = (float)this.l / 100.0f * (float)var5_20 / 100.0f;
            var3_17 = (float)this.m / 100.0f * (float)var6_24 / 100.0f;
            ((f0)this.f.get(this.d)).g().setSpeechRate(var4_26);
            ((f0)this.f.get(this.d)).g().setPitch(var3_17);
            var1_1 = new Bundle(this.j);
            var1_1.remove("language");
            var1_1.remove("country");
            var1_1.remove("voiceName");
            var1_1.remove("variant");
            var1_1.remove("pitch");
            var1_1.remove("rate");
            var1_1.remove("utteranceId");
            if (AutoTtsService.S) {
                var1_1.remove("streamType");
                var1_1.remove("audioAttributes");
            }
            if ((double)(var3_17 = this.k * (float)var7_25 / 100.0f) != 0.0) {
                var1_1.putFloat("volume", var3_17);
            }
            ((f0)this.f.get(this.d)).g().setOnUtteranceProgressListener((UtteranceProgressListener)new e(this, var2_8, null));
            if (!this.p.get() && !this.q.get()) {
                ((f0)this.f.get((int)this.d)).g = true;
                if (AutoTtsService.T && !((f0)this.f.get((int)this.d)).h) {
                    try {
                        var9_10 = new AudioAttributes.Builder();
                        var9_10 = var9_10.setUsage(11).setContentType(1).build();
                        ((f0)this.f.get(this.d)).g().setAudioAttributes((AudioAttributes)var9_10);
                        ((f0)this.f.get((int)this.d)).h = true;
                    }
                    catch (Exception var9_12) {
                        c3.m.a.d("AutoTTS", var9_12.toString());
                    }
                }
                this.t.postDelayed(new Runnable(this, (String)var11_19, (Bundle)var1_1, var2_8){
                    public final String c;
                    public final Bundle d;
                    public final SynthesisCallback e;
                    public final AutoTtsService f;
                    {
                        this.f = autoTtsService;
                        this.c = string;
                        this.d = bundle;
                        this.e = synthesisCallback;
                    }

                    @Override
                    public void run() {
                        Exception exception2;
                        block3: {
                            try {
                                AutoTtsService.d(1);
                                Object object = c3.m.a;
                                Object object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Current engine: ");
                                ((StringBuilder)object2).append(((f0)this.f.f.get(this.f.d)).e());
                                ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                                object2 = c3.m.a;
                                object = new StringBuilder();
                                ((StringBuilder)object).append(c0);
                                ((StringBuilder)object).append("_");
                                ((StringBuilder)object).append(N);
                                ((o)object2).c("AutoTTS", ((StringBuilder)object).toString());
                                object2 = c3.m.a;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("speak 1: ");
                                ((StringBuilder)object).append(this.c);
                                ((o)object2).c("AutoTTS", ((StringBuilder)object).toString());
                                TextToSpeech textToSpeech = ((f0)this.f.f.get(this.f.d)).g();
                                String string = this.c;
                                object2 = this.d;
                                object = new StringBuilder();
                                ((StringBuilder)object).append(c0);
                                ((StringBuilder)object).append("_");
                                ((StringBuilder)object).append(N);
                                if (textToSpeech.speak((CharSequence)string, 0, (Bundle)object2, ((StringBuilder)object).toString()) != 0) {
                                    c3.m.a.d("AutoTTS", "Speaking failed!!!");
                                    this.f.K(this.e, 12);
                                    this.f.n0(12);
                                    return;
                                }
                            }
                            catch (Exception exception2) {
                                break block3;
                            }
                            return;
                        }
                        o o3 = c3.m.a;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onSynthesis Error: ");
                        stringBuilder.append(exception2.getMessage());
                        o3.d("AutoTTS", stringBuilder.toString());
                        this.f.K(this.e, 14);
                        this.f.n0(14);
                    }
                }, 50L);
            }
            if (!AutoTtsService.U) {
                var1_1 = this.o;
                // MONITORENTER : var1_1
                while (!this.p.get() && !(var8_27 = this.q.get())) {
                    this.o.wait();
                }
lbl541:
                // 2 sources

                while (true) {
                    // MONITOREXIT : var1_1
lbl543:
                    // 2 sources

                    while (true) {
                        c3.m.a.c("AutoTTS", "onSynthesizeText ended");
                        this.K(var2_8, 13);
                        return;
                    }
                    break;
                }
                catch (InterruptedException var9_13) {
                    ** continue;
                }
            }
            this.g0(var2_8);
            ** continue;
        }
        c3.m.a.d("AutoTTS", "mTTSIndex out of range.");
        this.K(var2_8, 10);
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
            AutoTtsService.o(1);
            this.a.j0(g0, "Valid license");
        }

        @Override
        public void b(int n3) {
            AutoTtsService.o(1);
            this.a.j0(g0, "License check error");
        }

        @Override
        public void c(int n3) {
            AutoTtsService.o(1);
            this.a.j0(g0, "Invalid license");
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
            if (this.a.i >= P.size()) {
                c3.m.a.c("AutoTTS", "All tts engines have been initialized. (1)");
                return;
            }
            Object object = c3.m.a;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Init ");
            ((StringBuilder)object2).append(((f0)this.a.f.get(this.a.i)).e());
            ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
            object = c3.m.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("res ");
            ((StringBuilder)object2).append(n3);
            ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
            if (n3 == 0) {
                if (T) {
                    try {
                        object = new AudioAttributes.Builder();
                        object = object.setUsage(11).setContentType(1).build();
                        Z.setAudioAttributes((AudioAttributes)object);
                        ((f0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).i)).h = true;
                    }
                    catch (Exception exception) {
                        c3.m.a.d("AutoTTS", ((Object)exception).toString());
                    }
                }
                ((f0)this.a.f.get(this.a.i)).k(Z);
                ((f0)this.a.f.get(this.a.i)).j(2);
                if (((String)P.get(this.a.i)).equals("com.google.android.tts")) {
                    object = this.a;
                    AutoTtsService.C((AutoTtsService)((Object)object), ((AutoTtsService)((Object)object)).i);
                }
            } else {
                ((f0)this.a.f.get(this.a.i)).k(Z);
                ((f0)this.a.f.get(this.a.i)).j(-1);
            }
            n3 = 0;
            while (n3 == 0) {
                block11: {
                    AutoTtsService.f(this.a);
                    if (this.a.i < P.size()) {
                        this.a.f.add(new f0((String)P.get(this.a.i)));
                        object = new c3.d(this.a.h, this.a);
                        ((c3.d)object).c((String)P.get(this.a.i));
                        this.a.g.add(object);
                        try {
                            object = this.a.h;
                            object2 = new c(this.a);
                            TextToSpeech textToSpeech = new TextToSpeech((Context)object, (TextToSpeech.OnInitListener)object2, (String)P.get(this.a.i));
                            AutoTtsService.B(textToSpeech);
                            break block11;
                        }
                        catch (Exception exception) {
                            object = c3.m.a;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Error when initializing ");
                            ((StringBuilder)object2).append((String)P.get(this.a.i));
                            ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                            continue;
                        }
                    }
                    c3.m.a.c("AutoTTS", "All tts engines have been initialized. (2)");
                }
                n3 = 1;
            }
        }
    }

    public class d
    implements TextToSpeech.OnInitListener {
        public final AutoTtsService a;

        public d(AutoTtsService autoTtsService) {
            this.a = autoTtsService;
        }

        public /* synthetic */ d(AutoTtsService autoTtsService, a a4) {
            this(autoTtsService);
        }

        public void onInit(int n3) {
            if (this.a.u >= 0 && this.a.u < this.a.f.size()) {
                o o3 = c3.m.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Restore ");
                stringBuilder.append(((f0)this.a.f.get(this.a.u)).e());
                o3.c("AutoTTS", stringBuilder.toString());
                o3 = c3.m.a;
                stringBuilder = new StringBuilder();
                stringBuilder.append("res ");
                stringBuilder.append(n3);
                o3.c("AutoTTS", stringBuilder.toString());
                if (n3 == 0) {
                    if (T) {
                        try {
                            stringBuilder = new AudioAttributes.Builder();
                            stringBuilder = stringBuilder.setUsage(11).setContentType(1).build();
                            Z.setAudioAttributes((AudioAttributes)stringBuilder);
                            ((f0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).u)).h = true;
                        }
                        catch (Exception exception) {
                            c3.m.a.d("AutoTTS", ((Object)exception).toString());
                        }
                    }
                    ((f0)this.a.f.get(this.a.u)).k(Z);
                    ((f0)this.a.f.get(this.a.u)).j(2);
                    ((f0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).u)).e = "";
                    ((f0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).u)).d = null;
                } else {
                    ((f0)this.a.f.get(this.a.u)).k(Z);
                    ((f0)this.a.f.get(this.a.u)).j(-1);
                }
                AutoTtsService.G(this.a, -1);
                return;
            }
            c3.m.a.c("AutoTTS", "ttsInitListener_restore invalid index");
            AutoTtsService.G(this.a, -1);
        }
    }

    public class e
    extends UtteranceProgressListener {
        public final SynthesisCallback a;
        public final Handler b;
        public final AutoTtsService c;

        public e(AutoTtsService autoTtsService, SynthesisCallback synthesisCallback) {
            this.c = autoTtsService;
            this.b = new Handler(Looper.getMainLooper());
            this.a = synthesisCallback;
        }

        public /* synthetic */ e(AutoTtsService autoTtsService, SynthesisCallback synthesisCallback, a a4) {
            this(autoTtsService, synthesisCallback);
        }

        public static /* synthetic */ SynthesisCallback a(e e3) {
            return e3.a;
        }

        public void onDone(String string) {
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDone ");
            stringBuilder.append(string);
            o3.c("AutoTTS", stringBuilder.toString());
            if (M.size() > 1) {
                this.b.postDelayed(new Runnable(this){
                    public final e c;
                    {
                        this.c = e3;
                    }

                    /*
                     * Exception decompiling
                     */
                    @Override
                    public void run() {
                        /*
                         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                         * 
                         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [23 : 1007->1148)] java.lang.Exception
                         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
                         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
                         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
                         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
                         *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                         *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
                         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
                         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
                         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                         *     at org.benf.cfr.reader.Main.main(Main.java:54)
                         */
                        throw new IllegalStateException("Decompilation failed");
                    }
                }, 50L);
                return;
            }
            c3.m.a.c("AutoTTS", "No more text to read.");
            this.c.L(this.a, 7);
        }

        public void onError(String string) {
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            o3.d("AutoTTS", stringBuilder.toString());
            this.c.L(this.a, 8);
        }

        public void onError(String string, int n3) {
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            stringBuilder.append(" code ");
            stringBuilder.append(n3);
            o3.d("AutoTTS", stringBuilder.toString());
            this.c.L(this.a, 9);
        }

        public void onStart(String string) {
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStart ");
            stringBuilder.append(string);
            o3.c("AutoTTS", stringBuilder.toString());
            if (!this.a.hasStarted()) {
                this.a.start(16000, 2, 1);
            }
        }

        public void onStop(String string, boolean bl) {
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStop ");
            stringBuilder.append(string);
            o3.c("AutoTTS", stringBuilder.toString());
        }
    }
}

