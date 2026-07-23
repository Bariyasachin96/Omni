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
     * Enabled aggressive exception aggregation
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
     * Enabled aggressive exception aggregation
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
        synchronized (object2) {
            try {
                for (int i3 = 0; i3 < (object3 = c3.m.c).size(); ++i3) {
                    Object object4;
                    if (!((c3.e)object3.get((int)i3)).f.isEmpty() && !((c3.e)object3.get((int)i3)).f.equalsIgnoreCase("disable")) {
                        object4 = c3.m.a;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("- ");
                        stringBuilder.append(((c3.e)object3.get((int)i3)).b);
                        stringBuilder.append(" ");
                        stringBuilder.append(((c3.e)object3.get((int)i3)).f);
                        ((o)object4).c("AutoTTS", stringBuilder.toString());
                    }
                    if (!((String)object).equals(((c3.e)object3.get((int)i3)).b)) continue;
                    if (((c3.e)object3.get((int)i3)).i) {
                        c3.m.a.c("AutoTTS", " res1 Disable");
                        return "Disable";
                    }
                    object = c3.m.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(" res ");
                    ((StringBuilder)object4).append(((c3.e)object3.get((int)i3)).f);
                    ((o)object).c("AutoTTS", ((StringBuilder)object4).toString());
                    return ((c3.e)object3.get((int)i3)).f;
                }
                // MONITOREXIT @DISABLED, blocks:[0, 3] lbl39 : MonitorExitStatement: MONITOREXIT : var3_3
                c3.m.a.c("AutoTTS", " res ''");
                return "";
            }
            catch (Throwable throwable) {}
            throw throwable;
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
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void T() {
        synchronized (this) {
            try {
                c3.m.a.c("AutoTTS", "initAllTTS");
                int n3 = 0;
                while (true) {
                    int n4;
                    if (n3 < (n4 = this.f.size())) {
                        ((f0)this.f.get(n3)).m();
                        ((f0)this.f.get(n3)).l();
                    }
                    this.f.clear();
                    this.i = 0;
                    if (!P.isEmpty()) {
                        ArrayList arrayList = this.f;
                        Object object = new f0((String)P.get(this.i));
                        arrayList.add(object);
                        object = new c3.d(this.h, this);
                        ((c3.d)object).c((String)P.get(this.i));
                        this.g.add(object);
                        Context context = this.getApplicationContext();
                        object = new c(this, null);
                        arrayList = new TextToSpeech(context, (TextToSpeech.OnInitListener)object, (String)P.get(this.i));
                        Z = arrayList;
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Y() {
        synchronized (this) {
            try {
                SharedPreferences sharedPreferences;
                P = sharedPreferences = new ArrayList();
                String string = this.M(c3.m.f(Locale.getDefault()));
                sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    CharSequence charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("engine_");
                    ((StringBuilder)charSequence).append(n3);
                    charSequence = sharedPreferences.getString(((StringBuilder)charSequence).toString(), "");
                    if (((String)charSequence).isEmpty() || ((String)charSequence).equals("end")) break;
                    if (!((String)charSequence).equals(string)) {
                        P.add(charSequence);
                    }
                    ++n3;
                }
                if (!string.isEmpty() && !string.equals("Disable")) {
                    P.add(0, string);
                }
                if (P.isEmpty() && c3.v.a(this.h)) {
                    P.add("com.google.android.tts");
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a0() {
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
                c3.m.a.c("AutoTTS", "loadLanguages");
                c3.m.c.clear();
                c3.m.f.clear();
                sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                n6 = 0;
                while (true) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("language_");
                    ((StringBuilder)object3).append(n6);
                    string2 = sharedPreferences.getString(((StringBuilder)object3).toString(), "");
                    object3 = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" - ");
                    ((StringBuilder)object2).append(string2);
                    ((o)object3).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (string2.isEmpty()) {
                        object3 = c3.m.a;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Enabled languages 2 letters: ");
                        ((StringBuilder)object2).append(c3.m.f.toString());
                        ((o)object3).c("AutoTTS", ((StringBuilder)object2).toString());
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
                object = new c3.e("", string2, n5, n3, n4, (String)object2, (String)object3, string);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(((c3.e)object).b);
                ((StringBuilder)object3).append("_disabled");
                ((c3.e)object).i = sharedPreferences.getBoolean(((StringBuilder)object3).toString(), false);
                c3.m.c.add(object);
                if (!((c3.e)object).i) {
                    object = (String)c3.m.i.get(string2);
                    object3 = c3.m.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(" -not disabled: ");
                    ((StringBuilder)object2).append((String)object);
                    ((o)object3).c("AutoTTS", ((StringBuilder)object2).toString());
                    if (object != null) {
                        c3.m.f.add(object);
                    }
                }
                ++n6;
                continue;
            }
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
     * Enabled aggressive exception aggregation
     */
    public final void e0() {
        synchronized (this) {
            block7: {
                boolean bl = Y.isEmpty();
                if (bl) break block7;
                return;
            }
            try {
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
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Locale f0(String object) {
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

    /*
     * Loose catch block
     * Enabled aggressive exception aggregation
     */
    public final void g0(SynthesisCallback synthesisCallback) {
        synthesisCallback.start(16000, 2, 1);
        while (!this.p.get() && this.o0(synthesisCallback)) {
            Object object = this.o;
            synchronized (object) {
                try {
                    this.o.wait(100L);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    break;
                }
                catch (Throwable throwable) {}
                {
                }
                throw throwable;
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
     * Enabled aggressive exception aggregation
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
     * Enabled aggressive exception aggregation
     */
    public final void l0() {
        Exception exception2;
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
                catch (Exception exception2) {
                    break block4;
                }
                this.startForeground(136549, this.I());
            }
            return;
        }
        c3.m.a.d("AutoTTS", exception2.getMessage());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
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
     * Enabled aggressive exception aggregation
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
     * Enabled aggressive exception aggregation
     */
    public void onCreate() {
        block4: {
            Exception exception2;
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
                    catch (Exception exception2) {
                        break block3;
                    }
                }
                this.h0();
                break block4;
            }
            String string = exception2.getMessage();
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
     * Enabled aggressive exception aggregation
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int onLoadLanguage(String string, String string2, String string3) {
        synchronized (this) {
            o o3 = c3.m.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onLoadLanguage: ");
            stringBuilder.append(string);
            stringBuilder.append(" ");
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(string3);
            o3.c("AutoTTS", stringBuilder.toString());
            return this.Z(string, string2, string3);
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
     * Exception decompiling
     */
    public void onSynthesizeText(SynthesisRequest var1_1, SynthesisCallback var2_8) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 7[TRYBLOCK] [8 : 807->820)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2283)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:415)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
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
                                                                    AutoTtsService.e();
                                                                    AutoTtsService.k().remove(0);
                                                                    var9_1 = ((z)AutoTtsService.k().get(0)).c();
                                                                    var3_3 = AutoTtsService.O;
                                                                    if (var3_3 != 1) break block25;
                                                                    var3_3 = ((z)AutoTtsService.k().get(0)).a();
                                                                    var7_4 /* !! */  = "eng";
                                                                    if (var3_3 != 1) {
                                                                        if (var3_3 == 2) {
                                                                            var7_4 /* !! */  = AutoTtsService.G;
                                                                            var3_3 = AutoTtsService.l(this.c.c, AutoTtsService.G, "", "");
                                                                            if (var3_3 == -2 || var3_3 == -1) {
                                                                                var8_5 = c3.m.a;
                                                                                var7_4 /* !! */  = new StringBuilder();
                                                                                var7_4 /* !! */ .append("Language ");
                                                                                var7_4 /* !! */ .append(AutoTtsService.G);
                                                                                var7_4 /* !! */ .append(" is not supported.\n Text: ");
                                                                                var7_4 /* !! */ .append(var9_1);
                                                                                var8_5.d("AutoTTS", var7_4 /* !! */ .toString());
                                                                                var7_4 /* !! */  = this.c;
                                                                                AutoTtsService.m(var7_4 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_4 /* !! */ ), 3);
                                                                                return;
                                                                            }
                                                                        }
                                                                        break block21;
                                                                    } else {
                                                                        var3_3 = AutoTtsService.l(this.c.c, "eng", "", "");
                                                                        if (var3_3 == -2 || var3_3 == -1) {
                                                                            var8_7 = c3.m.a;
                                                                            var7_4 /* !! */  = new StringBuilder();
                                                                            var7_4 /* !! */ .append("Language eng is not supported.\n Text: ");
                                                                            var7_4 /* !! */ .append(var9_1);
                                                                            var8_7.d("AutoTTS", var7_4 /* !! */ .toString());
                                                                            var7_4 /* !! */  = this.c;
                                                                            AutoTtsService.m(var7_4 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_4 /* !! */ ), 2);
                                                                            return;
                                                                        }
                                                                    }
                                                                    break block21;
                                                                }
                                                                if (AutoTtsService.O != 4) break block26;
                                                                var8_6 = ((z)AutoTtsService.k().get(0)).b();
                                                                if (var8_6.isEmpty()) break block27;
                                                                var7_4 /* !! */  = var8_6;
                                                                if (!var8_6.equals("unknown")) break block28;
                                                            }
                                                            var8_6 = clsCLD2.b(var9_1, AutoTtsService.n(), AutoTtsService.q(), AutoTtsService.D(this.c.c));
                                                            var10_10 = c3.m.a;
                                                            var7_4 /* !! */  = new StringBuilder();
                                                            var7_4 /* !! */ .append("Cld2: ");
                                                            var7_4 /* !! */ .append((String)var8_6);
                                                            var7_4 /* !! */ .append(" '");
                                                            var7_4 /* !! */ .append(var9_1);
                                                            var7_4 /* !! */ .append("'");
                                                            var10_10.c("AutoTTS", var7_4 /* !! */ .toString());
                                                            var7_4 /* !! */  = var8_6;
                                                            if (var8_6.length() > 2) {
                                                                var7_4 /* !! */  = var8_6.substring(0, 2);
                                                            }
                                                            var8_6 = var7_4 /* !! */  = (String)c3.m.h.get(var7_4 /* !! */ );
                                                            if (var7_4 /* !! */  == null) {
                                                                var8_6 = ((z)AutoTtsService.k().get(0)).a() != 1 ? AutoTtsService.L : AutoTtsService.K;
                                                            }
                                                            if ((var10_10 = AutoTtsService.r(this.c.c, (String)var8_6)).isEmpty()) break block29;
                                                            var7_4 /* !! */  = var8_6;
                                                            if (!var10_10.equals("Disable")) break block28;
                                                        }
                                                        var7_4 /* !! */  = (var3_3 = ((z)AutoTtsService.k().get(0)).a()) != 1 ? (var3_3 != 2 ? var8_6 : AutoTtsService.L) : AutoTtsService.K;
                                                    }
                                                    if ((var3_3 = AutoTtsService.l(this.c.c, (String)(var8_6 = var7_4 /* !! */ ), "", "")) == -2) break block30;
                                                    var7_4 /* !! */  = var8_6;
                                                    if (var3_3 != -1) break block21;
                                                }
                                                var10_10 = c3.m.a;
                                                var7_4 /* !! */  = new StringBuilder();
                                                var7_4 /* !! */ .append("Language ");
                                                var7_4 /* !! */ .append((String)var8_6);
                                                var7_4 /* !! */ .append(" is not supported.\n Text: ");
                                                var7_4 /* !! */ .append(var9_1);
                                                var10_10.d("AutoTTS", var7_4 /* !! */ .toString());
                                                var7_4 /* !! */  = this.c;
                                                AutoTtsService.i(var7_4 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_4 /* !! */ ), 14);
                                                return;
                                            }
                                            var7_4 /* !! */  = ((z)AutoTtsService.k().get(0)).b();
                                            var3_3 = AutoTtsService.l(this.c.c, (String)var7_4 /* !! */ , "", "");
                                            if (var3_3 == -2 || var3_3 == -1) break block22;
                                        }
                                        var4_12 = AutoTtsService.s(this.c.c, (String)var7_4 /* !! */ );
                                        var5_13 = AutoTtsService.t(this.c.c, (String)var7_4 /* !! */ );
                                        var3_3 = AutoTtsService.u(this.c.c, (String)var7_4 /* !! */ );
                                        var1_14 = (float)AutoTtsService.v(this.c.c) / 100.0f * (float)var4_12 / 100.0f;
                                        var2_15 = (float)AutoTtsService.w(this.c.c) / 100.0f * (float)var3_3 / 100.0f;
                                        ((f0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g().setSpeechRate(var1_14);
                                        ((f0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g().setPitch(var2_15);
                                        var7_4 /* !! */  = new Bundle(AutoTtsService.x(this.c.c));
                                        var7_4 /* !! */ .remove("language");
                                        var7_4 /* !! */ .remove("country");
                                        var7_4 /* !! */ .remove("voiceName");
                                        var7_4 /* !! */ .remove("variant");
                                        var7_4 /* !! */ .remove("pitch");
                                        var7_4 /* !! */ .remove("rate");
                                        var7_4 /* !! */ .remove("utteranceId");
                                        if (!AutoTtsService.S) break block23;
                                        var7_4 /* !! */ .remove("streamType");
                                        var7_4 /* !! */ .remove("audioAttributes");
                                        {
                                            catch (Exception var9_2) {}
                                        }
                                    }
                                    if ((double)(var1_14 = AutoTtsService.y(this.c.c) * (float)var5_13 / 100.0f) == 0.0) ** GOTO lbl126
                                    var7_4 /* !! */ .putFloat("volume", var1_14);
lbl126:
                                    // 2 sources

                                    var10_10 = ((f0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g();
                                    var8_6 = this.c;
                                    var11_16 = new e(var8_6.c, com.vnspeak.autotts.AutoTtsService$e.a((e)var8_6), null);
                                    var10_10.setOnUtteranceProgressListener((UtteranceProgressListener)var11_16);
                                    ((f0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.g((AutoTtsService)this.c.c))).g = true;
                                    if (!AutoTtsService.T || (var6_17 = ((f0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.g((AutoTtsService)this.c.c))).h)) break block31;
                                    try {
                                        var8_6 = new AudioAttributes.Builder();
                                        var8_6 = var8_6.setUsage(11).setContentType(1).build();
                                        ((f0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g().setAudioAttributes((AudioAttributes)var8_6);
                                        ((f0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.g((AutoTtsService)this.c.c))).h = true;
                                    }
                                    catch (Exception var8_8) {
                                        c3.m.a.d("AutoTTS", var8_8.toString());
                                        break block24;
                                    }
                                }
                                var10_10 = c3.m.a;
                                var8_6 = new StringBuilder();
                                var8_6.append("speak 2: ");
                                var8_6.append(var9_1);
                                var10_10.c("AutoTTS", var8_6.toString());
                                var8_6 = ((f0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g();
                                var10_10 = new StringBuilder();
                                var10_10.append(AutoTtsService.h());
                                var10_10.append("_");
                                var10_10.append(AutoTtsService.c());
                                if (var8_6.speak((CharSequence)var9_1, 0, (Bundle)var7_4 /* !! */ , var10_10.toString()) == 0) return;
                                c3.m.a.d("AutoTTS", "Speaking failed!!!");
                                var7_4 /* !! */  = this.c;
                                AutoTtsService.m(var7_4 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_4 /* !! */ ), 5);
                                return;
                            }
                            var10_11 = c3.m.a;
                            var8_9 = new StringBuilder();
                            var8_9.append("Language ");
                            var8_9.append((String)var7_4 /* !! */ );
                            var8_9.append(" is not supported.\n Text: ");
                            var8_9.append(var9_1);
                            var10_11.d("AutoTTS", var8_9.toString());
                            var7_4 /* !! */  = this.c;
                            AutoTtsService.m(var7_4 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_4 /* !! */ ), 4);
                            return;
                        }
                        var7_4 /* !! */  = c3.m.a;
                        var8_6 = new StringBuilder();
                        var8_6.append("onDone Error: ");
                        var8_6.append(var9_2.getMessage());
                        var7_4 /* !! */ .d("AutoTTS", var8_6.toString());
                        var7_4 /* !! */  = this.c;
                        AutoTtsService.m(var7_4 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_4 /* !! */ ), 6);
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

