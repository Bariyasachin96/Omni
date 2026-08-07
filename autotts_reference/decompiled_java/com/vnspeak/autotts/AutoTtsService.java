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
import c3.a0;
import c3.d0;
import c3.e0;
import c3.f;
import c3.k0;
import c3.l0;
import c3.n;
import c3.p;
import c3.w;
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
    public static volatile String G;
    public static volatile String H;
    public static volatile int I;
    public static volatile String J;
    public static volatile int K;
    public static volatile String L;
    public static volatile int M;
    public static volatile String N;
    public static volatile String O;
    public static volatile String P;
    public static final ArrayList Q;
    public static int R;
    public static volatile int S;
    public static volatile ArrayList T;
    public static boolean U;
    public static boolean V;
    public static boolean W;
    public static boolean X;
    public static boolean Y;
    public static boolean Z;
    public static boolean a0;
    public static boolean b0;
    public static boolean c0;
    public static boolean d0;
    public static volatile ArrayList e0;
    public static TextToSpeech f0;
    public static int g0;
    public static int h0;
    public static volatile String i0;
    public static final byte[] j0;
    public static String k0;
    public static final byte[] l0;
    public static int m0;
    public final int A;
    public final int B;
    public final int C;
    public final int D;
    public final int E;
    public int F = 2;
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
    public volatile boolean v = false;
    public long w = 0L;
    public long x = 0L;
    public LicenseCheckerCallback y;
    public LicenseChecker z = null;

    static {
        Q = new ArrayList();
        R = 0;
        T = null;
        U = false;
        V = false;
        W = false;
        X = false;
        Y = false;
        Z = false;
        a0 = true;
        b0 = false;
        c0 = true;
        d0 = false;
        e0 = new ArrayList();
        g0 = 0;
        h0 = -1;
        i0 = "";
        j0 = new byte[32];
        k0 = "";
        l0 = new byte[]{-45, 64, 37, -10, -72, -47, 64, -63, 102, 86, -35, -85, 78, -15, -26, -113, -54, 36, -74, 35};
        m0 = -1;
    }

    public AutoTtsService() {
        this.A = 0;
        this.B = 1;
        this.C = 2;
        this.D = 3;
        this.E = -1;
    }

    public static /* synthetic */ TextToSpeech B(TextToSpeech textToSpeech) {
        f0 = textToSpeech;
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
                c3.n.a.c("TTS", "Audio focus gained");
                return;
            }
            c3.n.a.c("TTS", "Audio focus lost");
            return;
        }
        c3.n.a.c("TTS", "Audio focus lost temporarily");
    }

    public static /* synthetic */ int d(int n3) {
        R = n3;
        return n3;
    }

    public static /* synthetic */ int e() {
        int n3 = R;
        R = n3 + 1;
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
        m0 = n3;
        return n3;
    }

    public static /* synthetic */ int q() {
        return h0;
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
        LicenseChecker licenseChecker = this.z;
        if (licenseChecker != null) {
            licenseChecker.f(this.y);
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
        Object object = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("endSynthesis #");
        stringBuilder.append(n3);
        ((p)object).c("AutoTTS", stringBuilder.toString());
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
    public final String M(String charSequence) {
        Object object = c3.n.a;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("getEngine4Language ");
        ((StringBuilder)object2).append((String)charSequence);
        ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
        if (S == 3) {
            return "com.google.android.tts";
        }
        object2 = c3.n.c;
        synchronized (object2) {
            try {
                for (int i3 = 0; i3 < (object = c3.n.c).size(); ++i3) {
                    p p3;
                    if (!(((f)object.get((int)i3)).f.isEmpty() || ((f)object.get((int)i3)).f.equalsIgnoreCase("disable") || ((f)object.get((int)i3)).i)) {
                        p3 = c3.n.a;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("- ");
                        stringBuilder.append(((f)object.get((int)i3)).b);
                        stringBuilder.append(" ");
                        stringBuilder.append(((f)object.get((int)i3)).f);
                        p3.c("AutoTTS", stringBuilder.toString());
                    }
                    if (!((String)charSequence).equals(((f)object.get((int)i3)).b)) continue;
                    if (((f)object.get((int)i3)).i) {
                        c3.n.a.c("AutoTTS", " res1 Disable");
                        return "Disable";
                    }
                    p3 = c3.n.a;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(" res ");
                    ((StringBuilder)charSequence).append(((f)object.get((int)i3)).f);
                    p3.c("AutoTTS", ((StringBuilder)charSequence).toString());
                    return ((f)object.get((int)i3)).f;
                }
                // MONITOREXIT @DISABLED, blocks:[0, 3] lbl39 : MonitorExitStatement: MONITOREXIT : var3_4
                c3.n.a.c("AutoTTS", " res ''");
                return "";
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int N(String string) {
        int n3 = 0;
        try {
            while (true) {
                List list = c3.n.c;
                if (n3 >= list.size()) return 100;
                if (((f)list.get((int)n3)).b.equals(string)) {
                    return ((f)list.get((int)n3)).e;
                }
                ++n3;
            }
        }
        catch (Exception exception) {
            return 100;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int O(String string) {
        int n3 = 0;
        try {
            while (true) {
                List list = c3.n.c;
                if (n3 >= list.size()) return 100;
                if (((f)list.get((int)n3)).b.equals(string)) {
                    return ((f)list.get((int)n3)).c;
                }
                ++n3;
            }
        }
        catch (Exception exception) {
            return 100;
        }
    }

    public final String P(String object) {
        Object object2 = c3.n.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("getVariant4Language ");
        ((StringBuilder)object3).append((String)object);
        ((p)object2).c("TAG", ((StringBuilder)object3).toString());
        if (S == 3) {
            return object;
        }
        for (int i3 = 0; i3 < (object3 = c3.n.c).size(); ++i3) {
            if (((f)object3.get((int)i3)).f.isEmpty() || ((f)object3.get((int)i3)).f.equalsIgnoreCase("disable") || ((f)object3.get((int)i3)).i) continue;
            p p3 = c3.n.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" -");
            ((StringBuilder)object2).append(((f)object3.get((int)i3)).b);
            ((StringBuilder)object2).append(" -> ");
            ((StringBuilder)object2).append(((f)object3.get((int)i3)).h);
            p3.c("AutoTTS", ((StringBuilder)object2).toString());
            if (!((String)object).equals(((f)object3.get((int)i3)).b)) continue;
            object = c3.n.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" Found ");
            ((StringBuilder)object2).append(((f)object3.get((int)i3)).h);
            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
            return ((f)object3.get((int)i3)).h;
        }
        return "";
    }

    public final String Q(String charSequence) {
        Object object = c3.n.a;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("getVoice4Language ");
        ((StringBuilder)object2).append((String)charSequence);
        ((p)object).c("TAG", ((StringBuilder)object2).toString());
        if (S == 3) {
            return charSequence;
        }
        for (int i3 = 0; i3 < (object = c3.n.c).size(); ++i3) {
            if (((f)object.get((int)i3)).f.isEmpty() || ((f)object.get((int)i3)).f.equalsIgnoreCase("disable") || ((f)object.get((int)i3)).i) continue;
            p p3 = c3.n.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" -");
            ((StringBuilder)object2).append(((f)object.get((int)i3)).b);
            ((StringBuilder)object2).append(" -> ");
            ((StringBuilder)object2).append(((f)object.get((int)i3)).g);
            p3.c("AutoTTS", ((StringBuilder)object2).toString());
            if (!((String)charSequence).equals(((f)object.get((int)i3)).b)) continue;
            object2 = c3.n.a;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" Found ");
            ((StringBuilder)charSequence).append(((f)object.get((int)i3)).g);
            ((p)object2).c("AutoTTS", ((StringBuilder)charSequence).toString());
            return ((f)object.get((int)i3)).g;
        }
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final int R(String string) {
        int n3 = 0;
        try {
            while (true) {
                List list = c3.n.c;
                if (n3 >= list.size()) return 100;
                if (((f)list.get((int)n3)).b.equals(string)) {
                    return ((f)list.get((int)n3)).d;
                }
                ++n3;
            }
        }
        catch (Exception exception) {
            return 100;
        }
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
                c3.n.a.c("AutoTTS", "initAllTTS");
                int n3 = 0;
                while (true) {
                    int n4;
                    if (n3 < (n4 = this.f.size())) {
                        ((k0)this.f.get(n3)).m();
                        ((k0)this.f.get(n3)).l();
                    }
                    this.f.clear();
                    this.i = 0;
                    if (!T.isEmpty()) {
                        TextToSpeech textToSpeech;
                        Object object = this.f;
                        Object object2 = new k0((String)T.get(this.i));
                        ((ArrayList)object).add(object2);
                        object = new c3.d(this.h, this);
                        ((c3.d)object).c((String)T.get(this.i));
                        this.g.add(object);
                        object = this.getApplicationContext();
                        object2 = new c(this, null);
                        f0 = textToSpeech = new TextToSpeech((Context)object, (TextToSpeech.OnInitListener)object2, (String)T.get(this.i));
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
        this.y = new b(this, null);
        this.z = new LicenseChecker((Context)this, new ServerManagedPolicy((Context)this, new AESObfuscator(l0, this.getPackageName(), string)), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzCqD0VjR3RQYVN1f5hIVDWBBoomRgzjbHqW3g5v59YfVwTkmM4hWXvyHEXBHcE7Wcbl8Tlic9LIH0HStl7KN+Erx4mUlk8jqsPGeDC9r2f2VLKYGKm6lb5Lvjw8aNfS6auzJlFN12/NBMEBPb1wstV2B1gUaDNT/63Zz0arO6XbjFM9WAHpo54BFQoWk/vRK95G88xlWoUX3QGum0AouPMj8vKiYaBGzFjnXMTdRH70bYPY5pPmF710ox3vv/SSiM78BT/Ez1V7rshx3fL9ZjrxbmrO8YYbqtzvGu91+y0viRkLvJozU5dy5zHp147UEaX3rDnyxFBhGngO1ng3hQIDAQAB");
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
        if (G != null) {
            return;
        }
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
        G = sharedPreferences.getString("auto_mode_language", "");
        if (G.isEmpty()) {
            G = c3.n.e(Locale.getDefault());
        }
        if ((O = sharedPreferences.getString("mixed_mode_latin_language", "")).isEmpty()) {
            O = c3.n.e(Locale.getDefault());
        }
        if ((P = sharedPreferences.getString("mixed_mode_non_latin_language", "")).isEmpty()) {
            P = c3.n.e(Locale.getDefault());
        }
        if ((H = sharedPreferences.getString("dual_mode_language", "")).isEmpty()) {
            H = c3.n.e(Locale.getDefault());
        }
        I = sharedPreferences.getInt("number_mode_language", 0);
        K = sharedPreferences.getInt("punc_mode_language", 0);
        M = sharedPreferences.getInt("emoji_mode_language", 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void Y() {
        synchronized (this) {
            try {
                Object object = new ArrayList();
                T = object;
                object = this.M(c3.n.e(Locale.getDefault()));
                SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    CharSequence charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("engine_");
                    ((StringBuilder)charSequence).append(n3);
                    charSequence = sharedPreferences.getString(((StringBuilder)charSequence).toString(), "");
                    if (((String)charSequence).isEmpty() || ((String)charSequence).equals("end")) break;
                    if (!((String)charSequence).equals(object)) {
                        T.add(charSequence);
                    }
                    ++n3;
                }
                if (!((String)object).isEmpty() && !((String)object).equals("Disable")) {
                    T.add(0, object);
                }
                if (T.isEmpty() && c3.w.a(this.h)) {
                    T.add("com.google.android.tts");
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final int Z(String object, String object2, String object3) {
        Object object4;
        Object object5;
        Object object6 = c3.n.a;
        Object object7 = new StringBuilder();
        ((StringBuilder)object7).append("loadLanguage ");
        ((StringBuilder)object7).append((String)object);
        ((StringBuilder)object7).append(" ");
        ((StringBuilder)object7).append((String)object2);
        ((StringBuilder)object7).append(" ");
        ((StringBuilder)object7).append((String)object3);
        ((p)object6).c("AutoTTS", ((StringBuilder)object7).toString());
        int n3 = this.onIsLanguageAvailable((String)object, (String)object2, (String)object3);
        object7 = c3.n.a;
        object6 = new StringBuilder();
        ((StringBuilder)object6).append(" isLanguageAvailable = ");
        ((StringBuilder)object6).append(n3);
        ((p)object7).c("AutoTTS", ((StringBuilder)object6).toString());
        if (((String)object3).contains("autotts.") && n3 == 2) {
            object7 = new StringBuilder();
            ((StringBuilder)object7).append((String)object);
            ((StringBuilder)object7).append("_");
            ((StringBuilder)object7).append((String)object2);
            object7 = ((StringBuilder)object7).toString();
            object3 = ((String)object3).substring(8);
            object6 = "";
        } else {
            object5 = this.Q((String)object);
            object4 = this.M((String)object);
            object7 = object3;
            if (((String)object3).isEmpty()) {
                object7 = this.P((String)object);
            }
            object6 = object7;
            object3 = object4;
            object7 = object5;
        }
        object5 = object7;
        object7 = object3;
        if (((String)object3).isEmpty()) {
            object5 = this.Q(G);
            object7 = this.M(G);
        }
        object4 = c3.n.a;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("engine: ");
        ((StringBuilder)object3).append((String)object7);
        ((StringBuilder)object3).append(" voice ");
        ((StringBuilder)object3).append((String)object5);
        ((StringBuilder)object3).append(" variant ");
        ((StringBuilder)object3).append((String)object6);
        ((p)object4).c("AutoTTS", ((StringBuilder)object3).toString());
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    return n3;
                }
                object3 = this.f0((String)object5);
                if (object3 == null) {
                    return -2;
                }
                if (this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3)) && ((Locale)object3).getVariant().equals(object6)) {
                    this.b0((String)object7, (Locale)object3, (String)object6, V);
                    return n3;
                }
                if (!this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3)) && ((Locale)object3).getVariant().equals(object6)) {
                    this.b0((String)object7, (Locale)object3, (String)object6, V);
                    return n3;
                }
                object = new Locale((String)object, (String)object2, (String)object6);
                this.b0(this.k0((Locale)object), (Locale)object, (String)object6, V);
                return n3;
            }
            object3 = this.f0((String)object5);
            if (object3 == null) {
                return -2;
            }
            if (this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3))) {
                this.b0("", (Locale)object3, (String)object6, V);
                return n3;
            }
            if (!this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3))) {
                this.b0((String)object7, (Locale)object3, (String)object6, V);
                return n3;
            }
            object = new Locale((String)object, (String)object2, "");
            this.b0(this.k0((Locale)object), (Locale)object, (String)object6, V);
            return n3;
        }
        object2 = this.f0((String)object5);
        if (object2 == null) {
            return -2;
        }
        if (this.c.equals(object7) && c3.n.e((Locale)object2).equals(object)) {
            this.b0((String)object7, (Locale)object2, (String)object6, V);
            return n3;
        }
        if (!this.c.equals(object7) && c3.n.e((Locale)object2).equals(object)) {
            this.b0((String)object7, (Locale)object2, (String)object6, V);
            this.c = object7;
            return n3;
        }
        object = new Locale((String)object, "", "");
        this.b0(this.k0((Locale)object), (Locale)object, (String)object6, V);
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a0() {
        synchronized (this) {
            Throwable throwable2;
            block14: {
                Object object;
                String[] stringArray;
                CharSequence charSequence;
                Object object2;
                String string;
                int n3;
                int n4;
                String string2;
                Object object3;
                int n5;
                int n6;
                SharedPreferences sharedPreferences;
                ArrayList<Object> arrayList;
                ArrayList<Object> arrayList2;
                try {
                    c3.n.a.c("AutoTTS", "loadLanguages");
                    arrayList2 = new ArrayList<Object>();
                    arrayList = new ArrayList<Object>();
                    sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                    n6 = sharedPreferences.getAll().size();
                    n5 = 0;
                    for (int i3 = 0; i3 < 64; ++i3) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("language_");
                        ((StringBuilder)object3).append(i3);
                        string2 = sharedPreferences.getString(((StringBuilder)object3).toString(), "");
                        if (string2.isEmpty()) {
                            n5 = n4 = n5 + 1;
                            if (n4 < 8) continue;
                            n5 = n4;
                            if (arrayList2.isEmpty()) continue;
                            break;
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
                        object2 = "";
                        charSequence = "";
                        stringArray = sharedPreferences.getString(string2, "");
                        object = object2;
                        object3 = charSequence;
                        if (stringArray.isEmpty()) break block13;
                        stringArray = stringArray.split("#");
                        object = object2;
                        object3 = charSequence;
                        if (stringArray.length < 2) break block13;
                    }
                }
                catch (Throwable throwable2) {}
                {
                    block13: {
                        object = stringArray[0];
                        object3 = stringArray[1];
                    }
                    object2 = new f("", string2, n5, n3, n4, (String)object, (String)object3, string);
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append("_disabled");
                    ((f)object2).i = sharedPreferences.getBoolean(((StringBuilder)charSequence).toString(), false);
                    arrayList2.add(object2);
                    if (!(((f)object2).i || ((String)object).isEmpty() || ((String)object).equalsIgnoreCase("disable"))) {
                        object2 = c3.e.b(string2);
                        if (object2 != null && !arrayList.contains(object2)) {
                            arrayList.add(object2);
                        } else if (object2 == null) {
                            object2 = c3.n.a;
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("unmapped language code: ");
                            ((StringBuilder)charSequence).append(string2);
                            ((p)object2).c("AutoTTS", ((StringBuilder)charSequence).toString());
                        }
                    }
                    object2 = c3.n.a;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(" - ");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(" ");
                    ((StringBuilder)charSequence).append((String)object);
                    ((StringBuilder)charSequence).append(" ");
                    ((StringBuilder)charSequence).append((String)object3);
                    ((StringBuilder)charSequence).append(" ");
                    ((StringBuilder)charSequence).append(string);
                    ((p)object2).c("AutoTTS", ((StringBuilder)charSequence).toString());
                    n5 = 0;
                    continue;
                }
                if (arrayList2.isEmpty() && n6 > 0) {
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("prefs readable (");
                    ((StringBuilder)object).append(n6);
                    ((StringBuilder)object).append(" keys) but no languages parsed");
                    ((p)object3).d("AutoTTS", ((StringBuilder)object).toString());
                    return;
                }
                break block14;
                if (arrayList2.isEmpty()) {
                    c3.n.a.d("AutoTTS", "no languages loaded - keeping previous state");
                    return;
                }
                object3 = c3.n.c;
                object3.clear();
                object3.addAll(arrayList2);
                c3.n.f.clear();
                c3.n.f.addAll(arrayList);
                this.v = true;
                object3 = c3.n.a;
                object = new StringBuilder();
                ((StringBuilder)object).append("Enabled languages 2 letters: ");
                ((StringBuilder)object).append(c3.n.f);
                ((p)object3).c("AutoTTS", ((StringBuilder)object).toString());
                return;
            }
            throw throwable2;
        }
    }

    public final void b0(String object, Locale locale, String string, boolean bl) {
        int n3;
        String string2;
        Object object22;
        Object object3;
        Object object4;
        block24: {
            object4 = object;
            object3 = c3.n.a;
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("LoadVoice ");
            ((StringBuilder)object22).append((String)object4);
            ((StringBuilder)object22).append(" ");
            ((StringBuilder)object22).append(locale.toString());
            ((StringBuilder)object22).append(" ");
            ((StringBuilder)object22).append(string);
            ((p)object3).c("AutoTTS", ((StringBuilder)object22).toString());
            if (bl && S != 3) {
                this.d0((String)object, locale, string, bl);
                return;
            }
            if (((String)object4).isEmpty()) {
                object4 = this.c;
            } else {
                this.c = object4;
            }
            object3 = "";
            string2 = ((String)object4).replace("-", "").replace("_", "");
            object = c3.n.a;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append(" current engine: ");
            ((StringBuilder)object4).append(string2);
            ((p)object).c("AutoTTS", ((StringBuilder)object4).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((k0)this.f.get(n3)).e().equals(string2) || ((k0)this.f.get(n3)).f() != 2) continue;
                c3.n.a.c("AutoTTS", " found!");
                break block24;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4;
            Object object5;
            this.d = n3;
            if (string.isEmpty() && !((k0)this.f.get((int)n3)).e.isEmpty()) {
                c3.n.a.c("AutoTTS", "Load voice original");
                this.c0(string2, locale);
                return;
            }
            object22 = new Locale("zxx");
            object4 = object3;
            object = object22;
            if (!((k0)this.f.get((int)n3)).e.isEmpty()) {
                object5 = ((k0)this.f.get(n3)).g().getVoice();
                object4 = object3;
                object = object22;
                if (object5 != null) {
                    object = object5.getLocale();
                    object4 = object5.getName();
                    object22 = c3.n.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" last ");
                    ((StringBuilder)object3).append(object);
                    ((StringBuilder)object3).append(" ");
                    ((StringBuilder)object3).append((String)object4);
                    ((p)object22).c("AutoTTS", ((StringBuilder)object3).toString());
                    if (c3.n.e((Locale)object).equals(c3.n.e(locale)) && (c3.n.d((Locale)object).equals(c3.n.d(locale)) || c3.n.d(locale).equals("")) && ((String)object4).equals(string)) {
                        c3.n.a.c("AutoTTS", " Do nothing!");
                        return;
                    }
                }
            }
            object22 = e0;
            int n5 = ((ArrayList)object22).size();
            for (n4 = 0; n4 < n5; ++n4) {
                object3 = ((ArrayList)object22).get(n4);
                object5 = ((String)(object3 = (String)object3)).split("#");
                if (((String[])object5).length < 2 || !object5[0].equals(string2) || !object5[1].equals(locale.toString())) continue;
                object5 = c3.n.a;
                object22 = new StringBuilder();
                ((StringBuilder)object22).append("voice: ");
                ((StringBuilder)object22).append((String)object3);
                ((p)object5).c("AutoTTS", ((StringBuilder)object22).toString());
                for (Object object22 : c3.n.c) {
                    if (!((f)object22).f.equals(string2) || !((f)object22).g.equals(locale.toString())) continue;
                    string = ((f)object22).h;
                    break;
                }
                break;
            }
            object22 = c3.n.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(" variant ");
            ((StringBuilder)object3).append(string);
            ((p)object22).c("AutoTTS", ((StringBuilder)object3).toString());
            if (string.equals("*Default") && !this.V(locale, (Locale)object)) {
                object3 = c3.n.a;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append(locale.toString());
                ((StringBuilder)object4).append(" vs ");
                ((StringBuilder)object4).append(((Locale)object).toString());
                ((p)object3).c("AutoTTS", ((StringBuilder)object4).toString());
                if (((k0)this.f.get(this.d)).g().setLanguage(locale) >= 0) {
                    ((k0)this.f.get((int)this.d)).f = true;
                    c3.n.a.c("AutoTTS", "Set voice 1");
                } else {
                    this.i0(((k0)this.f.get(this.d)).e());
                }
            } else if (!string.equals("*Default") && !string.equals(object4)) {
                c3.n.a.c("AutoTTS", "Check voice 1");
                object4 = ((k0)this.f.get(this.d)).g().getVoices();
                if (object4 != null) {
                    object3 = object4.iterator();
                    while (object3.hasNext()) {
                        object4 = (Voice)object3.next();
                        if (!object4.getName().equalsIgnoreCase(string)) continue;
                        n4 = ((k0)this.f.get(this.d)).g().setVoice((Voice)object4);
                        if (n4 >= 0) {
                            ((k0)this.f.get((int)this.d)).f = true;
                            object = c3.n.a;
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("Set voice 2: ");
                            ((StringBuilder)object3).append(object4.getName());
                            ((StringBuilder)object3).append(" res=");
                            ((StringBuilder)object3).append(n4);
                            ((p)object).c("AutoTTS", ((StringBuilder)object3).toString());
                        } else {
                            this.i0(((k0)this.f.get(this.d)).e());
                        }
                        break;
                    }
                } else if (!this.V(locale, (Locale)object)) {
                    n4 = ((k0)this.f.get(this.d)).g().setLanguage(locale);
                    if (n4 >= 0) {
                        ((k0)this.f.get((int)this.d)).f = true;
                        object4 = c3.n.a;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Set voice 3: ");
                        ((StringBuilder)object).append(locale.toString());
                        ((StringBuilder)object).append(" res = ");
                        ((StringBuilder)object).append(n4);
                        ((StringBuilder)object).append(" ");
                        ((StringBuilder)object).append(((k0)this.f.get(this.d)).g().toString());
                        ((p)object4).c("AutoTTS", ((StringBuilder)object).toString());
                    } else {
                        this.i0(((k0)this.f.get(this.d)).e());
                    }
                }
            }
            ((k0)this.f.get((int)n3)).d = locale;
            ((k0)this.f.get((int)n3)).e = string;
            return;
        }
        c3.n.a.d("AutoTTS", "TTS is not ready");
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
                if (!((k0)this.f.get(n3)).e().equals(object) || ((k0)this.f.get(n3)).f() != 2) {
                    continue;
                }
                break block7;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            this.d = n3;
            object = ((k0)this.f.get((int)n3)).d;
            if (c3.n.e((Locale)object).equals(c3.n.e(locale)) && (c3.n.d((Locale)object).equals(c3.n.d(locale)) || c3.n.d(locale).isEmpty())) {
                return;
            }
            if (((k0)this.f.get(this.d)).g().setLanguage(locale) >= 0) {
                ((k0)this.f.get((int)this.d)).f = true;
                ((k0)this.f.get((int)n3)).d = locale;
                ((k0)this.f.get((int)n3)).e = "";
                return;
            }
            this.i0(((k0)this.f.get(this.d)).e());
            return;
        }
        this.d = -1;
    }

    public final void d0(String object, Locale serializable, String object2, boolean bl) {
        int n3;
        Object object3;
        Object object4;
        Object object5;
        block19: {
            object5 = c3.n.a;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("loadVoice_Secondary ");
            ((StringBuilder)object4).append((String)object);
            ((StringBuilder)object4).append(" ");
            ((StringBuilder)object4).append(((Locale)serializable).toString());
            ((StringBuilder)object4).append(" ");
            ((StringBuilder)object4).append((String)object2);
            ((p)object5).c("AutoTTS", ((StringBuilder)object4).toString());
            object5 = c3.n.a;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Current Engine ");
            ((StringBuilder)object4).append(this.c);
            ((p)object5).c("AutoTTS", ((StringBuilder)object4).toString());
            if (((String)object).isEmpty()) {
                object = this.c;
            } else {
                this.c = object;
            }
            object4 = "";
            object3 = ((String)object).replace("-", "").replace("_", "");
            object = c3.n.a;
            object5 = new StringBuilder();
            ((StringBuilder)object5).append(" current engine: ");
            ((StringBuilder)object5).append((String)object3);
            ((p)object).c("AutoTTS", ((StringBuilder)object5).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((k0)this.f.get(n3)).e().equals(object3) || ((k0)this.f.get(n3)).f() != 2) continue;
                c3.n.a.c("AutoTTS", " found!");
                break block19;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4 = this.d;
            this.d = n3;
            if (!bl || !((k0)this.f.get((int)this.d)).f) {
                Object object6;
                Object object72;
                object = new Locale("zxx");
                object5 = ((k0)this.f.get(n3)).g().getVoice();
                if (object5 != null) {
                    object = object5.getLocale();
                    object4 = object5.getName();
                }
                if (n4 == this.d && object5 != null) {
                    object72 = c3.n.a;
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append(" Engine Variant ");
                    ((StringBuilder)object6).append((String)object4);
                    object72.c("AutoTTS", ((StringBuilder)object6).toString());
                    object72 = c3.n.a;
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append(" Engine Locale ");
                    ((StringBuilder)object6).append(((Locale)object).toString());
                    object72.c("AutoTTS", ((StringBuilder)object6).toString());
                    if (this.V((Locale)serializable, (Locale)object) && (((String)object4).equals(object2) || ((String)object2).equals("*Default") || ((String)object2).isEmpty())) {
                        c3.n.a.c("AutoTTS", " *0 Do nothing");
                        return;
                    }
                }
                object6 = c3.n.a;
                object72 = new StringBuilder();
                object72.append("Searching ");
                object72.append((String)object3);
                object72.append(" ");
                object72.append(((Locale)serializable).toString());
                ((p)object6).c("AutoTTS", object72.toString());
                object6 = e0;
                n4 = ((ArrayList)object6).size();
                for (n3 = 0; n3 < n4; ++n3) {
                    object72 = ((ArrayList)object6).get(n3);
                    Object object8 = (String)object72;
                    Object object9 = c3.n.a;
                    object72 = new StringBuilder();
                    object72.append(" *");
                    object72.append((String)object8);
                    ((p)object9).c("AutoTTS", object72.toString());
                    object72 = ((String)object8).split("#");
                    if (((String[])object72).length < 2 || !object72[0].equals(object3)) continue;
                    for (Object object72 : c3.n.c) {
                        object8 = c3.n.a;
                        object9 = new StringBuilder();
                        ((StringBuilder)object9).append("  -");
                        ((StringBuilder)object9).append(object72.f);
                        ((StringBuilder)object9).append(" ");
                        ((StringBuilder)object9).append(object72.g);
                        ((StringBuilder)object9).append(" ");
                        ((StringBuilder)object9).append(object72.h);
                        ((p)object8).c("AutoTTS", ((StringBuilder)object9).toString());
                        if (!object72.f.equals(object3) || !this.V((Locale)serializable, this.f0(object72.g)) || object72.h.isEmpty()) continue;
                        object2 = object72.h;
                        break;
                    }
                    break;
                }
                object3 = c3.n.a;
                object6 = new StringBuilder();
                ((StringBuilder)object6).append(" Variant ");
                ((StringBuilder)object6).append((String)object2);
                ((p)object3).c("AutoTTS", ((StringBuilder)object6).toString());
                object6 = c3.n.a;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(" Locale ");
                ((StringBuilder)object3).append(((Locale)serializable).toString());
                ((p)object6).c("AutoTTS", ((StringBuilder)object3).toString());
                if ((((String)object2).equals("*Default") || ((String)object2).isEmpty()) && !this.V((Locale)serializable, (Locale)object)) {
                    c3.n.a.c("AutoTTS", " *1");
                    object2 = c3.n.a;
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append(((Locale)serializable).toString());
                    ((StringBuilder)object4).append(" vs ");
                    ((StringBuilder)object4).append(((Locale)object).toString());
                    ((p)object2).c("AutoTTS", ((StringBuilder)object4).toString());
                    if (((k0)this.f.get(this.d)).g().setLanguage((Locale)serializable) >= 0) {
                        ((k0)this.f.get((int)this.d)).f = true;
                        ((k0)this.f.get((int)this.d)).d = serializable;
                        ((k0)this.f.get((int)this.d)).e = ((Locale)serializable).getVariant();
                        return;
                    }
                    this.i0(((k0)this.f.get(this.d)).e());
                    return;
                }
                c3.n.a.c("AutoTTS", " *2");
                if (object5 != null) {
                    object6 = c3.n.a;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(" Engine Variant ");
                    ((StringBuilder)object5).append((String)object4);
                    ((p)object6).c("AutoTTS", ((StringBuilder)object5).toString());
                    object6 = c3.n.a;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(" Engine Locale ");
                    ((StringBuilder)object5).append(((Locale)object).toString());
                    ((p)object6).c("AutoTTS", ((StringBuilder)object5).toString());
                    if (this.V((Locale)serializable, (Locale)object) && (((String)object4).equals(object2) || ((String)object2).equals("*Default") || ((String)object2).isEmpty())) {
                        c3.n.a.c("AutoTTS", " *2.1 Do nothing");
                        return;
                    }
                }
                if ((object4 = ((k0)this.f.get(this.d)).g().getVoices()) != null) {
                    object5 = object4.iterator();
                    while (object5.hasNext()) {
                        object4 = (Voice)object5.next();
                        if (!object4.getName().equalsIgnoreCase((String)object2)) continue;
                        if (((k0)this.f.get(this.d)).g().setVoice((Voice)object4) >= 0) {
                            object = c3.n.a;
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Set voice 2: ");
                            ((StringBuilder)serializable).append(object4.toString());
                            ((p)object).c("AutoTTS", ((StringBuilder)serializable).toString());
                            ((k0)this.f.get((int)this.d)).d = object4.getLocale();
                            ((k0)this.f.get((int)this.d)).e = object2;
                            ((k0)this.f.get((int)this.d)).f = true;
                            return;
                        }
                        this.i0(((k0)this.f.get(this.d)).e());
                        break;
                    }
                }
                if (!this.V((Locale)serializable, (Locale)object)) {
                    if (((k0)this.f.get(this.d)).g().setLanguage((Locale)serializable) >= 0) {
                        ((k0)this.f.get((int)this.d)).d = serializable;
                        ((k0)this.f.get((int)this.d)).e = ((Locale)serializable).getVariant();
                        ((k0)this.f.get((int)this.d)).f = true;
                        c3.n.a.c("AutoTTS", "Set voice 3");
                        return;
                    }
                    this.i0(((k0)this.f.get(this.d)).e());
                }
            }
            return;
        }
        c3.n.a.d("AutoTTS", "TTS is not ready");
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
                boolean bl = e0.isEmpty();
                if (bl) break block7;
                return;
            }
            try {
                c3.n.a.c("AutoTTS", "LoadVoices");
                SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
                int n3 = 0;
                while (true) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("voice_");
                    stringBuilder.append(n3);
                    String string = sharedPreferences.getString(stringBuilder.toString(), "");
                    p p3 = c3.n.a;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(" -");
                    stringBuilder.append(string);
                    p3.c("AutoTTS", stringBuilder.toString());
                    if (string.isEmpty()) {
                        V = sharedPreferences.getBoolean("dedicated_engines", false);
                        return;
                    }
                    e0.add(string);
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
        object = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Audio focus request: ");
        if (n3 != 1) {
            bl = false;
        }
        stringBuilder.append(bl);
        ((p)object).c("TTS", stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void i0(String object) {
        synchronized (this) {
            int n3;
            StringBuilder stringBuilder;
            Object object2;
            block11: {
                try {
                    object2 = c3.n.a;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("restoreTts ");
                    stringBuilder.append((String)object);
                    ((p)object2).c("AutoTTS", stringBuilder.toString());
                    if (this.u != -1) {
                        c3.n.a.c("AutoTTS", " -Restoring in progress...");
                        return;
                    }
                }
                catch (Throwable throwable) {}
                throw throwable;
                for (n3 = 0; n3 < this.f.size(); ++n3) {
                    object2 = c3.n.a;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(" -");
                    stringBuilder.append(((k0)this.f.get(n3)).e());
                    ((p)object2).c("AutoTTS", stringBuilder.toString());
                    if (!((k0)this.f.get(n3)).e().equalsIgnoreCase((String)object)) {
                        continue;
                    }
                    break block11;
                }
                n3 = -1;
            }
            if (n3 == -1) {
                c3.n.a.c("AutoTTS", " -restore package name is not found");
                return;
            }
            if (!((k0)this.f.get(n3)).h()) {
                c3.n.a.c("AutoTTS", " -restore is not applicable!");
                return;
            }
            ((k0)this.f.get(n3)).i();
            this.u = n3;
            object = c3.n.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("restoreTts ");
            ((StringBuilder)object2).append(((k0)this.f.get(this.u)).e());
            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
            try {
                ((k0)this.f.get(this.u)).m();
                ((k0)this.f.get(this.u)).l();
            }
            catch (Exception exception) {}
            object2 = this.getApplicationContext();
            object = new d(this, null);
            stringBuilder = new TextToSpeech((Context)object2, (TextToSpeech.OnInitListener)object, ((k0)this.f.get(this.u)).e());
            f0 = stringBuilder;
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
        for (n3 = 0; n3 < e0.size(); ++n3) {
            stringArray = ((String)e0.get(n3)).split("#");
            if (stringArray.length != 2 && stringArray.length != 3) continue;
            locale2 = this.f0(stringArray[1]);
            if (locale2 == null) {
                return "";
            }
            if (!c3.n.e(locale).equals(c3.n.e(locale2)) || !c3.n.d(locale).equals(c3.n.d(locale2)) || !locale.getVariant().equals(locale2.getVariant())) continue;
            return stringArray[0];
        }
        for (n3 = 0; n3 < e0.size(); ++n3) {
            stringArray = ((String)e0.get(n3)).split("#");
            if (stringArray.length != 2 && stringArray.length != 3) continue;
            locale2 = this.f0(stringArray[1]);
            if (locale2 == null) {
                return "";
            }
            if (!c3.n.e(locale).equals(c3.n.e(locale2)) || !c3.n.d(locale).equals(c3.n.d(locale2))) continue;
            return stringArray[0];
        }
        for (n3 = 0; n3 < e0.size(); ++n3) {
            stringArray = ((String)e0.get(n3)).split("#");
            if (stringArray.length != 2 && stringArray.length != 3) continue;
            locale2 = this.f0(stringArray[1]);
            if (locale2 == null) {
                return "";
            }
            if (!c3.n.e(locale).equals(c3.n.e(locale2))) continue;
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
        c3.n.a.d("AutoTTS", exception2.getMessage());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void m0(Boolean object) {
        Q.clear();
        Object object2 = c3.n.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("stopAllTts ");
        ((StringBuilder)object3).append(object);
        ((p)object2).c("AutoTTS", ((StringBuilder)object3).toString());
        boolean bl = (Boolean)object;
        if (!bl) {
            if (this.d >= 0 && this.d < this.f.size() && ((k0)this.f.get(this.d)).f() == 2 && ((k0)this.f.get((int)this.d)).g && ((k0)this.f.get(this.d)).g().isSpeaking()) {
                try {
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" - calling speak empty for ");
                    ((StringBuilder)object).append(((k0)this.f.get(this.d)).e());
                    ((p)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    object = c3.n.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("onSynthesizeText: ");
                    ((StringBuilder)object3).append(i0);
                    ((StringBuilder)object3).append(" ''");
                    ((p)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("speak ");
                    ((StringBuilder)object).append(i0);
                    ((p)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    ((k0)this.f.get(this.d)).g().speak((CharSequence)"", 0, null, null);
                }
                catch (Exception exception) {
                    object3 = c3.n.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Speaking failed for ");
                    ((StringBuilder)object2).append(((k0)this.f.get(this.d)).e());
                    ((StringBuilder)object2).append("\n ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    ((p)object3).c("AutoTTS", ((StringBuilder)object2).toString());
                }
            }
        } else {
            for (int i3 = 0; i3 < this.f.size(); ++i3) {
                if (((k0)this.f.get(i3)).f() != 2 || !((k0)this.f.get((int)i3)).g || !((k0)this.f.get(i3)).g().isSpeaking()) continue;
                try {
                    object = c3.n.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" - calling stop for ");
                    ((StringBuilder)object3).append(((k0)this.f.get(i3)).e());
                    ((p)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("stop ");
                    ((StringBuilder)object).append(i0);
                    ((p)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    ((k0)this.f.get(i3)).m();
                    continue;
                }
                catch (Exception exception) {
                    object = c3.n.a;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Stop failed for ");
                    ((StringBuilder)object2).append(((k0)this.f.get(i3)).e());
                    ((StringBuilder)object2).append("\n  ");
                    ((StringBuilder)object2).append(exception.getMessage());
                    ((p)object).d("AutoTTS", ((StringBuilder)object2).toString());
                }
            }
        }
        object3 = this.o;
        synchronized (object3) {
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
        p p3 = c3.n.a;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("unlockSynthesis #");
        ((StringBuilder)object).append(n3);
        p3.c("AutoTTS", ((StringBuilder)object).toString());
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
        for (int i3 = 0; i3 < (byArray = j0).length && !this.p.get(); i3 += n3) {
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
                    c3.n.a = c3.p.f((Context)this);
                    String string = c3.a0.c((Context)this);
                    long l3 = c3.a0.b((Context)this);
                    p p3 = c3.n.a;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onCreate Version Name: ");
                    stringBuilder.append(string);
                    stringBuilder.append(" Version Code: ");
                    stringBuilder.append(l3);
                    p3.c("AutoTTS", stringBuilder.toString());
                    super.onCreate();
                    this.h = this;
                    try {
                        if (!Z) break block2;
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
        h0 = !c3.l0.d() ? c3.l0.b(this.h) : 0;
        this.Y();
        this.e0();
        this.a0();
        clsCLD2.f(c3.n.f);
        this.X();
        c3.n.o(this.getApplicationContext());
        c3.n.q(this.getApplicationContext());
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
        c3.n.a.c("AutoTTS", "onDestroy");
        try {
            this.stopForeground(1);
            this.r.abandonAudioFocusRequest(this.s);
        }
        catch (Exception exception) {
            c3.n.a.d("AutoTTS", exception.getMessage());
        }
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= this.f.size()) break;
            if (((k0)this.f.get(n4)).f() == 2) {
                ((k0)this.f.get(n4)).l();
            }
            ++n4;
        }
        try {
            for (int i3 = n3; i3 < this.g.size(); ++i3) {
                ((c3.d)this.g.get(i3)).e();
            }
        }
        catch (Exception exception) {}
        if ((licenseChecker = this.z) != null) {
            licenseChecker.m();
        }
        super.onDestroy();
    }

    public String onGetDefaultVoiceNameFor(String string, String charSequence, String object) {
        p p3 = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onGetDefaultVoiceNameFor ");
        stringBuilder.append(string);
        stringBuilder.append(" ");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" ");
        stringBuilder.append((String)object);
        p3.c("AutoTTS", stringBuilder.toString());
        object = c3.n.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -");
        ((StringBuilder)charSequence).append(string);
        ((p)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return string;
    }

    public String[] onGetLanguage() {
        c3.n.a.c("AutoTTS", "onGetLanguage");
        String string = c3.n.e(Locale.getDefault());
        p p3 = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" -");
        stringBuilder.append(string);
        p3.c("AutoTTS", stringBuilder.toString());
        return new String[]{string};
    }

    public List onGetVoices() {
        c3.n.a.c("AutoTTS", "onGetVoices");
        ArrayList arrayList = c3.n.i(null, true);
        ArrayList<Voice> arrayList2 = new ArrayList<Voice>();
        for (int i3 = 0; i3 < arrayList.size(); ++i3) {
            arrayList2.add(new Voice((String)arrayList.get(i3), new Locale((String)arrayList.get(i3)), 400, 100, false, new HashSet()));
        }
        return arrayList2;
    }

    public int onIsLanguageAvailable(String charSequence, String object, String string) {
        p p3 = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onIsLanguageAvailable: ");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" ");
        stringBuilder.append((String)object);
        stringBuilder.append(" ");
        stringBuilder.append(string);
        p3.c("AutoTTS", stringBuilder.toString());
        int n3 = c3.n.i(null, true).contains(charSequence) ? 0 : -2;
        object = c3.n.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -res: ");
        ((StringBuilder)charSequence).append(n3);
        ((p)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return n3;
    }

    public int onIsValidVoiceName(String charSequence) {
        p p3 = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onIsValidVoiceName ");
        stringBuilder.append((String)charSequence);
        p3.c("AutoTTS", stringBuilder.toString());
        int n3 = c3.n.i(null, true).contains(charSequence) ? 0 : -1;
        p3 = c3.n.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -res ");
        ((StringBuilder)charSequence).append(n3);
        p3.c("AutoTTS", ((StringBuilder)charSequence).toString());
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int onLoadLanguage(String string, String string2, String string3) {
        synchronized (this) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onLoadLanguage: ");
            stringBuilder.append(string);
            stringBuilder.append(" ");
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(string3);
            p3.c("AutoTTS", stringBuilder.toString());
            return this.Z(string, string2, string3);
        }
    }

    public int onLoadVoice(String object) {
        p p3 = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onLoadVoice ");
        stringBuilder.append((String)object);
        p3.c("AutoTTS", stringBuilder.toString());
        k0 = object;
        object = this.f0((String)object);
        if (object == null) {
            c3.n.a.c("AutoTTS", " -error");
            return -1;
        }
        int n3 = this.onLoadLanguage(c3.n.e((Locale)object), c3.n.d((Locale)object), ((Locale)object).getVariant());
        if (n3 != 0 && n3 != 1 && n3 != 2) {
            c3.n.a.c("AutoTTS", " -error");
            return -1;
        }
        c3.n.a.c("AutoTTS", " -success");
        return 0;
    }

    public int onStartCommand(Intent intent, int n3, int n4) {
        return 1;
    }

    public void onStop() {
        c3.n.a.c("AutoTTS", "onStop calling!!!");
        this.m0(Boolean.TRUE);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onSynthesizeText(SynthesisRequest var1_1, SynthesisCallback var2_6) {
        block177: {
            block160: {
                block156: {
                    block145: {
                        block146: {
                            block175: {
                                block176: {
                                    block162: {
                                        block173: {
                                            block174: {
                                                block172: {
                                                    block159: {
                                                        block158: {
                                                            block168: {
                                                                block155: {
                                                                    block147: {
                                                                        block171: {
                                                                            block170: {
                                                                                block148: {
                                                                                    block154: {
                                                                                        block149: {
                                                                                            block150: {
                                                                                                block151: {
                                                                                                    block152: {
                                                                                                        block153: {
                                                                                                            block163: {
                                                                                                                block144: {
                                                                                                                    block139: {
                                                                                                                        block167: {
                                                                                                                            block140: {
                                                                                                                                block166: {
                                                                                                                                    block141: {
                                                                                                                                        block165: {
                                                                                                                                            block142: {
                                                                                                                                                block164: {
                                                                                                                                                    block143: {
                                                                                                                                                        block161: {
                                                                                                                                                            block138: {
                                                                                                                                                                block137: {
                                                                                                                                                                    c3.n.a.c("AutoTTS", "\n-------------------------------\nonSynthesizeText");
                                                                                                                                                                    if (AutoTtsService.Z && !this.W()) {
                                                                                                                                                                        this.l0();
                                                                                                                                                                    } else if (!AutoTtsService.Z && this.W()) {
                                                                                                                                                                        this.stopForeground(1);
                                                                                                                                                                    }
                                                                                                                                                                    var9_8 = this.o;
                                                                                                                                                                    // MONITORENTER : var9_8
                                                                                                                                                                    this.p.set(false);
                                                                                                                                                                    this.o.notifyAll();
                                                                                                                                                                    // MONITOREXIT : var9_8
                                                                                                                                                                    var9_8 = this.o;
                                                                                                                                                                    // MONITORENTER : var9_8
                                                                                                                                                                    this.q.set(false);
                                                                                                                                                                    this.o.notifyAll();
                                                                                                                                                                    // MONITOREXIT : var9_8
                                                                                                                                                                    var14_13 = var1_1.getCharSequenceText();
                                                                                                                                                                    var9_8 = var14_13.toString();
                                                                                                                                                                    var10_14 = var1_1.getLanguage();
                                                                                                                                                                    this.l = var1_1.getSpeechRate();
                                                                                                                                                                    this.m = var1_1.getPitch();
                                                                                                                                                                    this.j = var1_1.getParams();
                                                                                                                                                                    this.k = var3_17 = this.j.getFloat("volume");
                                                                                                                                                                    if ((double)var3_17 == 0.0) {
                                                                                                                                                                        this.k = 1.0f;
                                                                                                                                                                    }
                                                                                                                                                                    AutoTtsService.i0 = this.j.getString("utteranceId");
                                                                                                                                                                    if ((var9_8 = var9_8.trim()).isEmpty()) {
                                                                                                                                                                        c3.n.a.c("AutoTTS", "Speak text is empty");
                                                                                                                                                                        this.m0(Boolean.FALSE);
                                                                                                                                                                        this.K(var2_6, 1);
                                                                                                                                                                        return;
                                                                                                                                                                    }
                                                                                                                                                                    var11_18 = c3.n.a;
                                                                                                                                                                    var12_19 = new StringBuilder();
                                                                                                                                                                    var12_19.append("Speak: ");
                                                                                                                                                                    var12_19.append((String)var9_8);
                                                                                                                                                                    var12_19.append(" id: ");
                                                                                                                                                                    var12_19.append(AutoTtsService.i0);
                                                                                                                                                                    var11_18.c("AutoTTS", var12_19.toString());
                                                                                                                                                                    if (AutoTtsService.m0 != 1) {
                                                                                                                                                                        AutoTtsService.g0 = var5_20 = AutoTtsService.g0 + 1;
                                                                                                                                                                        if (var5_20 > 100000) {
                                                                                                                                                                            AutoTtsService.g0 = 0;
                                                                                                                                                                        }
                                                                                                                                                                        if (AutoTtsService.g0 % 500 == 0) {
                                                                                                                                                                            this.H();
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                    var13_21 = "";
                                                                                                                                                                    var12_19 = "";
                                                                                                                                                                    var11_18 = "";
                                                                                                                                                                    if (var9_8.length() >= 9 && var9_8.substring(0, 9).equals("[AutoTTS:") && ((String[])(var15_22 = var9_8.split("]"))).length == 2) {
                                                                                                                                                                        var9_8 = var15_22[1];
                                                                                                                                                                        if (((String[])(var15_22 = var15_22[0].substring(1).split(":"))).length == 4) {
                                                                                                                                                                            var13_21 = var15_22[1];
                                                                                                                                                                            var12_19 = var15_22[2];
                                                                                                                                                                            var11_18 = var15_22[3];
                                                                                                                                                                            var10_14 = c3.n.e(this.f0((String)var12_19));
                                                                                                                                                                            var16_23 = c3.n.a;
                                                                                                                                                                            var15_22 = new StringBuilder();
                                                                                                                                                                            var15_22.append("FIXED: ");
                                                                                                                                                                            var15_22.append((String)var13_21);
                                                                                                                                                                            var15_22.append(" ");
                                                                                                                                                                            var15_22.append((String)var12_19);
                                                                                                                                                                            var15_22.append(" ");
                                                                                                                                                                            var15_22.append((String)var11_18);
                                                                                                                                                                            var16_23.c("AutoTTS", var15_22.toString());
                                                                                                                                                                        }
                                                                                                                                                                        var5_20 = 1;
                                                                                                                                                                    } else {
                                                                                                                                                                        var5_20 = 0;
                                                                                                                                                                    }
                                                                                                                                                                    if ((!var10_14.equals("zxx") || AutoTtsService.S == 1) && AutoTtsService.S != 2 && AutoTtsService.S != 3 || var5_20 != 0) break block161;
                                                                                                                                                                    c3.n.a.c("AutoTTS", "Auto mode || Google mode");
                                                                                                                                                                    var11_18 = AutoTtsService.Q;
                                                                                                                                                                    // MONITORENTER : var11_18
                                                                                                                                                                    try {
                                                                                                                                                                        var11_18.clear();
                                                                                                                                                                        var11_18.addAll(c3.e0.g((CharSequence)var14_13));
                                                                                                                                                                        for (var5_20 = 0; var5_20 < (var12_19 = AutoTtsService.Q).size() && !this.q.get(); ++var5_20) {
                                                                                                                                                                            var1_1 = var10_14 = ((e0)var12_19.get(var5_20)).b();
                                                                                                                                                                            if (var10_14.equalsIgnoreCase("unknown")) {
                                                                                                                                                                                var10_14 = clsCLD2.b(((e0)var12_19.get(var5_20)).c(), AutoTtsService.m0, AutoTtsService.h0, this.h);
                                                                                                                                                                                var13_21 = c3.n.a;
                                                                                                                                                                                var1_1 = new StringBuilder();
                                                                                                                                                                                var1_1.append("Cld2: ");
                                                                                                                                                                                var1_1.append((String)var10_14);
                                                                                                                                                                                var1_1.append(" '");
                                                                                                                                                                                var1_1.append(((e0)var12_19.get(var5_20)).c());
                                                                                                                                                                                var1_1.append("'");
                                                                                                                                                                                var13_21.c("AutoTTS", var1_1.toString());
                                                                                                                                                                                var1_1 = var10_14;
                                                                                                                                                                                if (var10_14.length() > 2) {
                                                                                                                                                                                    var1_1 = var10_14.substring(0, 2);
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                            var10_14 = var1_1 = c3.e.c((String)var1_1);
                                                                                                                                                                            if (var1_1 == null) {
                                                                                                                                                                                var10_14 = AutoTtsService.G;
                                                                                                                                                                            }
                                                                                                                                                                            if ((var1_1 = this.M((String)var10_14)).isEmpty() || var1_1.equals("Disable")) {
                                                                                                                                                                                var10_14 = AutoTtsService.G;
                                                                                                                                                                            }
                                                                                                                                                                            ((e0)var12_19.get(var5_20)).e((String)var10_14);
                                                                                                                                                                            if (c3.l0.c(AutoTtsService.m0, AutoTtsService.h0, -1, -1) % 100 != 1 || !var10_14.equals("eng")) continue;
                                                                                                                                                                            var1_1 = (e0)var12_19.get(var5_20);
                                                                                                                                                                            var13_21 = new StringBuilder();
                                                                                                                                                                            var13_21.append(((e0)var12_19.get(var5_20)).c());
                                                                                                                                                                            var13_21.append(" ");
                                                                                                                                                                            var13_21.append(c3.l0.e(".detceted esnecil oN ."));
                                                                                                                                                                            var1_1.f(var13_21.toString());
                                                                                                                                                                        }
                                                                                                                                                                        if (var12_19.isEmpty()) break block137;
                                                                                                                                                                        var1_1 = ((e0)var12_19.get(0)).c();
                                                                                                                                                                        var5_20 = this.onLoadLanguage(((e0)var12_19.get(0)).b(), "", "");
                                                                                                                                                                        var9_8 = c3.n.a;
                                                                                                                                                                        var13_21 = new StringBuilder();
                                                                                                                                                                        var13_21.append("load ");
                                                                                                                                                                        var13_21.append(var5_20);
                                                                                                                                                                        var9_8.c("AutoTTS", var13_21.toString());
                                                                                                                                                                        if (var5_20 != -2) {
                                                                                                                                                                            var9_8 = var1_1;
                                                                                                                                                                            if (var5_20 != -1) break block137;
                                                                                                                                                                        }
                                                                                                                                                                        var9_8 = c3.n.a;
                                                                                                                                                                        var10_14 = new StringBuilder();
                                                                                                                                                                        var10_14.append("Languge is not supported: ");
                                                                                                                                                                        var10_14.append(((e0)var12_19.get(0)).b());
                                                                                                                                                                        var10_14.append(", text: ");
                                                                                                                                                                        var10_14.append((String)var1_1);
                                                                                                                                                                        var9_8.d("AutoTTS", var10_14.toString());
                                                                                                                                                                        this.K(var2_6, 2);
                                                                                                                                                                        // MONITOREXIT : var11_18
                                                                                                                                                                        return;
                                                                                                                                                                    }
                                                                                                                                                                    catch (Throwable var1_2) {
                                                                                                                                                                        break block138;
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                                // MONITOREXIT : var11_18
                                                                                                                                                                var11_18 = var9_8;
                                                                                                                                                                break block162;
                                                                                                                                                            }
                                                                                                                                                            try {
                                                                                                                                                                throw var1_2;
                                                                                                                                                            }
                                                                                                                                                            catch (Exception var10_15) {
                                                                                                                                                                var9_8 = c3.n.a;
                                                                                                                                                                var1_1 = new StringBuilder();
                                                                                                                                                                var1_1.append("Synthesis ended with error: ");
                                                                                                                                                                var1_1.append(var10_15.getMessage());
                                                                                                                                                                var9_8.d("AutoTTS", var1_1.toString());
                                                                                                                                                                this.K(var2_6, 3);
                                                                                                                                                                return;
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                        if (AutoTtsService.S != 1 || var5_20 != 0) break block163;
                                                                                                                                                        c3.n.a.c("AutoTTS", "Dual mode");
                                                                                                                                                        var1_1 = var9_8;
                                                                                                                                                        if (c3.l0.c(AutoTtsService.m0, AutoTtsService.h0, -1, -1) % 100 == 1) {
                                                                                                                                                            var1_1 = new StringBuilder();
                                                                                                                                                            var1_1.append((String)var9_8);
                                                                                                                                                            var1_1.append(c3.l0.e(".detceted esnecil oN ."));
                                                                                                                                                            var1_1 = var1_1.toString();
                                                                                                                                                        }
                                                                                                                                                        try {
                                                                                                                                                            var11_18 = AutoTtsService.Q;
                                                                                                                                                            // MONITORENTER : var11_18
                                                                                                                                                        }
                                                                                                                                                        catch (Exception var9_9) {
                                                                                                                                                            var10_14 = c3.n.a;
                                                                                                                                                            var1_1 = new StringBuilder();
                                                                                                                                                            var1_1.append("Synthesis ended with error: ");
                                                                                                                                                            var1_1.append(var9_9.getMessage());
                                                                                                                                                            var10_14.d("AutoTTS", var1_1.toString());
                                                                                                                                                            this.K(var2_6, 6);
                                                                                                                                                            return;
                                                                                                                                                        }
                                                                                                                                                        try {
                                                                                                                                                            var11_18.clear();
                                                                                                                                                            var11_18.addAll(c3.d0.t((String)var1_1, AutoTtsService.I, AutoTtsService.K, AutoTtsService.M, AutoTtsService.m0, AutoTtsService.h0));
                                                                                                                                                            if (var11_18.isEmpty()) break block139;
                                                                                                                                                            var9_8 = ((e0)var11_18.get(0)).c();
                                                                                                                                                            var5_20 = ((e0)var11_18.get(0)).a();
                                                                                                                                                            if (var5_20 == 1) break block140;
                                                                                                                                                            if (var5_20 == 2) break block141;
                                                                                                                                                            if (var5_20 == 3) break block142;
                                                                                                                                                            if (var5_20 == 4) break block143;
                                                                                                                                                            if (var5_20 != 5) {
                                                                                                                                                                var1_1 = var9_8;
                                                                                                                                                                break block139;
                                                                                                                                                            }
                                                                                                                                                            var12_19 = c3.n.a;
                                                                                                                                                            var1_1 = new StringBuilder();
                                                                                                                                                            var1_1.append("language: ");
                                                                                                                                                            var1_1.append(AutoTtsService.N);
                                                                                                                                                            var12_19.c("AutoTTS", var1_1.toString());
                                                                                                                                                            var5_20 = this.onLoadLanguage(AutoTtsService.N, "", "");
                                                                                                                                                            if (var5_20 != -2) {
                                                                                                                                                                var1_1 = var9_8;
                                                                                                                                                                if (var5_20 != -1) break block139;
                                                                                                                                                            }
                                                                                                                                                            var10_14 = c3.n.a;
                                                                                                                                                            var1_1 = new StringBuilder();
                                                                                                                                                            var1_1.append("Languge is not supported: ");
                                                                                                                                                            var1_1.append(AutoTtsService.N);
                                                                                                                                                            var1_1.append(", text: ");
                                                                                                                                                            var1_1.append((String)var9_8);
                                                                                                                                                            var10_14.d("AutoTTS", var1_1.toString());
                                                                                                                                                            this.K(var2_6, 5);
                                                                                                                                                            // MONITOREXIT : var11_18
                                                                                                                                                            return;
                                                                                                                                                        }
                                                                                                                                                        catch (Throwable var1_3) {
                                                                                                                                                            break block144;
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                    var12_19 = c3.n.a;
                                                                                                                                                    var1_1 = new StringBuilder();
                                                                                                                                                    var1_1.append("language: ");
                                                                                                                                                    var1_1.append(AutoTtsService.L);
                                                                                                                                                    var12_19.c("AutoTTS", var1_1.toString());
                                                                                                                                                    var5_20 = this.onLoadLanguage(AutoTtsService.L, "", "");
                                                                                                                                                    if (var5_20 == -2) break block164;
                                                                                                                                                    var1_1 = var9_8;
                                                                                                                                                    if (var5_20 != -1) break block139;
                                                                                                                                                }
                                                                                                                                                var1_1 = c3.n.a;
                                                                                                                                                var10_14 = new StringBuilder();
                                                                                                                                                var10_14.append("Languge is not supported: ");
                                                                                                                                                var10_14.append(AutoTtsService.L);
                                                                                                                                                var10_14.append(", text: ");
                                                                                                                                                var10_14.append((String)var9_8);
                                                                                                                                                var1_1.d("AutoTTS", var10_14.toString());
                                                                                                                                                this.K(var2_6, 5);
                                                                                                                                                // MONITOREXIT : var11_18
                                                                                                                                                return;
                                                                                                                                            }
                                                                                                                                            var1_1 = c3.n.a;
                                                                                                                                            var12_19 = new StringBuilder();
                                                                                                                                            var12_19.append("language: ");
                                                                                                                                            var12_19.append(AutoTtsService.J);
                                                                                                                                            var1_1.c("AutoTTS", var12_19.toString());
                                                                                                                                            var5_20 = this.onLoadLanguage(AutoTtsService.J, "", "");
                                                                                                                                            if (var5_20 == -2) break block165;
                                                                                                                                            var1_1 = var9_8;
                                                                                                                                            if (var5_20 != -1) break block139;
                                                                                                                                        }
                                                                                                                                        var1_1 = c3.n.a;
                                                                                                                                        var10_14 = new StringBuilder();
                                                                                                                                        var10_14.append("Languge is not supported: ");
                                                                                                                                        var10_14.append(AutoTtsService.J);
                                                                                                                                        var10_14.append(", text: ");
                                                                                                                                        var10_14.append((String)var9_8);
                                                                                                                                        var1_1.d("AutoTTS", var10_14.toString());
                                                                                                                                        this.K(var2_6, 5);
                                                                                                                                        // MONITOREXIT : var11_18
                                                                                                                                        return;
                                                                                                                                    }
                                                                                                                                    var12_19 = c3.n.a;
                                                                                                                                    var1_1 = new StringBuilder();
                                                                                                                                    var1_1.append("language: ");
                                                                                                                                    var1_1.append(AutoTtsService.H);
                                                                                                                                    var12_19.c("AutoTTS", var1_1.toString());
                                                                                                                                    var5_20 = this.onLoadLanguage(AutoTtsService.H, "", "");
                                                                                                                                    if (var5_20 == -2) break block166;
                                                                                                                                    var1_1 = var9_8;
                                                                                                                                    if (var5_20 != -1) break block139;
                                                                                                                                }
                                                                                                                                var1_1 = c3.n.a;
                                                                                                                                var10_14 = new StringBuilder();
                                                                                                                                var10_14.append("Languge is not supported: ");
                                                                                                                                var10_14.append(AutoTtsService.H);
                                                                                                                                var10_14.append(", text: ");
                                                                                                                                var10_14.append((String)var9_8);
                                                                                                                                var1_1.d("AutoTTS", var10_14.toString());
                                                                                                                                this.K(var2_6, 5);
                                                                                                                                // MONITOREXIT : var11_18
                                                                                                                                return;
                                                                                                                            }
                                                                                                                            c3.n.a.c("AutoTTS", "language: eng");
                                                                                                                            var5_20 = this.onLoadLanguage("eng", "", "");
                                                                                                                            if (var5_20 == -2) break block167;
                                                                                                                            var1_1 = var9_8;
                                                                                                                            if (var5_20 != -1) break block139;
                                                                                                                        }
                                                                                                                        var1_1 = c3.n.a;
                                                                                                                        var10_14 = new StringBuilder();
                                                                                                                        var10_14.append("Languge is not supported: eng, text: ");
                                                                                                                        var10_14.append((String)var9_8);
                                                                                                                        var1_1.d("AutoTTS", var10_14.toString());
                                                                                                                        this.K(var2_6, 4);
                                                                                                                        // MONITOREXIT : var11_18
                                                                                                                        return;
                                                                                                                    }
                                                                                                                    // MONITOREXIT : var11_18
                                                                                                                    var11_18 = var1_1;
                                                                                                                    break block162;
                                                                                                                }
                                                                                                                // MONITOREXIT : var11_18
                                                                                                                throw var1_3;
                                                                                                            }
                                                                                                            if (AutoTtsService.S != 4 || var5_20 != 0) break block168;
                                                                                                            c3.n.a.c("AutoTTS", "Mixed mode");
                                                                                                            var12_19 = AutoTtsService.Q;
                                                                                                            // MONITORENTER : var12_19
                                                                                                            try {
                                                                                                                var12_19.clear();
                                                                                                                var11_18 = c3.e0.g((CharSequence)var14_13);
                                                                                                                var1_1 = var10_14;
                                                                                                                block41: for (var5_20 = 0; var5_20 < var11_18.size() && !this.q.get(); ++var5_20) {
                                                                                                                    block169: {
                                                                                                                        var10_14 = ((e0)var11_18.get(var5_20)).b();
                                                                                                                        if (var10_14.equalsIgnoreCase("unknown") || var10_14.isEmpty()) break block169;
                                                                                                                        var13_21 = this.M((String)var10_14);
                                                                                                                        if (var13_21.isEmpty() || var13_21.equals("Disable")) {
                                                                                                                            var10_14 = AutoTtsService.G;
                                                                                                                        }
                                                                                                                        ((e0)var11_18.get(var5_20)).e((String)var10_14);
                                                                                                                        AutoTtsService.Q.add((e0)var11_18.get(var5_20));
                                                                                                                        var10_14 = var1_1;
                                                                                                                        ** GOTO lbl432
                                                                                                                    }
                                                                                                                    var13_21 = c3.d0.t(((e0)var11_18.get(var5_20)).c(), AutoTtsService.I, AutoTtsService.K, AutoTtsService.M, AutoTtsService.m0, AutoTtsService.h0);
                                                                                                                    var6_24 = 0;
lbl367:
                                                                                                                    // 2 sources

                                                                                                                    while (true) {
                                                                                                                        var10_14 = var1_1;
                                                                                                                        if (var6_24 < var13_21.size()) {
                                                                                                                            if (c3.d0.n(((e0)var13_21.get(var6_24)).c())) {
                                                                                                                                var7_25 = AutoTtsService.I;
                                                                                                                                if (var7_25 != 0 && var7_25 != 1) {
                                                                                                                                    if (var7_25 != 2) {
                                                                                                                                        if (var7_25 == 3) {
                                                                                                                                            var14_13 = AutoTtsService.Q;
                                                                                                                                            var10_14 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.J);
                                                                                                                                            var14_13.add(var10_14);
                                                                                                                                        }
                                                                                                                                        break block145;
                                                                                                                                    } else {
                                                                                                                                        var10_14 = AutoTtsService.Q;
                                                                                                                                        var14_13 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.P);
                                                                                                                                        var10_14.add(var14_13);
                                                                                                                                    }
                                                                                                                                    break block145;
                                                                                                                                }
                                                                                                                                var14_13 = AutoTtsService.Q;
                                                                                                                                var10_14 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.O);
                                                                                                                                var14_13.add(var10_14);
                                                                                                                                break block145;
                                                                                                                            }
                                                                                                                            if (c3.d0.o(((e0)var13_21.get(var6_24)).c())) {
                                                                                                                                var7_25 = AutoTtsService.K;
                                                                                                                                if (var7_25 != 0 && var7_25 != 1) {
                                                                                                                                    if (var7_25 != 2) {
                                                                                                                                        if (var7_25 == 3) {
                                                                                                                                            var10_14 = AutoTtsService.Q;
                                                                                                                                            var14_13 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.L);
                                                                                                                                            var10_14.add(var14_13);
                                                                                                                                        }
                                                                                                                                        break block145;
                                                                                                                                    } else {
                                                                                                                                        var10_14 = AutoTtsService.Q;
                                                                                                                                        var14_13 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.P);
                                                                                                                                        var10_14.add(var14_13);
                                                                                                                                    }
                                                                                                                                    break block145;
                                                                                                                                }
                                                                                                                                var10_14 = AutoTtsService.Q;
                                                                                                                                var14_13 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.O);
                                                                                                                                var10_14.add(var14_13);
                                                                                                                                break block145;
                                                                                                                            }
                                                                                                                            if (c3.d0.l(((e0)var13_21.get(var6_24)).c())) {
                                                                                                                                var7_25 = AutoTtsService.M;
                                                                                                                                if (var7_25 != 0 && var7_25 != 1) {
                                                                                                                                    if (var7_25 != 2) {
                                                                                                                                        if (var7_25 == 3) {
                                                                                                                                            var10_14 = AutoTtsService.Q;
                                                                                                                                            var14_13 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.N);
                                                                                                                                            var10_14.add(var14_13);
                                                                                                                                        }
                                                                                                                                        break block145;
                                                                                                                                    } else {
                                                                                                                                        var14_13 = AutoTtsService.Q;
                                                                                                                                        var10_14 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.P);
                                                                                                                                        var14_13.add(var10_14);
                                                                                                                                    }
                                                                                                                                    break block145;
                                                                                                                                }
                                                                                                                                var10_14 = AutoTtsService.Q;
                                                                                                                                var14_13 = new e0(((e0)var13_21.get(var6_24)).c(), AutoTtsService.O);
                                                                                                                                var10_14.add(var14_13);
                                                                                                                                break block145;
                                                                                                                            }
                                                                                                                            var14_13 = clsCLD2.c(((e0)var13_21.get(var6_24)).c(), AutoTtsService.m0, AutoTtsService.h0, this.h);
                                                                                                                            break block146;
                                                                                                                        }
lbl432:
                                                                                                                        // 3 sources

                                                                                                                        var1_1 = var10_14;
                                                                                                                        continue block41;
                                                                                                                        break;
                                                                                                                    }
                                                                                                                }
                                                                                                                var13_21 = AutoTtsService.Q;
                                                                                                                var10_14 = var1_1;
                                                                                                                if (var13_21.isEmpty()) break block147;
                                                                                                                var10_14 = ((e0)var13_21.get(0)).c();
                                                                                                                var11_18 = ((e0)var13_21.get(0)).b();
                                                                                                                if (!var11_18.isEmpty()) {
                                                                                                                    var9_8 = var11_18;
                                                                                                                    if (!var11_18.equals("unknown")) break block148;
                                                                                                                }
                                                                                                                var11_18 = clsCLD2.b((String)var10_14, AutoTtsService.m0, AutoTtsService.h0, this.h);
                                                                                                                var14_13 = c3.n.a;
                                                                                                                var9_8 = new StringBuilder();
                                                                                                                var9_8.append("language: ");
                                                                                                                var9_8.append((String)var11_18);
                                                                                                                var9_8.append(" '");
                                                                                                                var9_8.append((String)var10_14);
                                                                                                                var9_8.append("'");
                                                                                                                var14_13.c("AutoTTS", var9_8.toString());
                                                                                                                var9_8 = var11_18;
                                                                                                                if (var11_18.length() > 2) {
                                                                                                                    var9_8 = var11_18.substring(0, 2);
                                                                                                                }
                                                                                                                var9_8 = var11_18 = c3.e.c((String)var9_8);
                                                                                                                if (var11_18 != null) break block148;
                                                                                                                var9_8 = var11_18;
                                                                                                                if (var13_21.isEmpty()) break block148;
                                                                                                                var5_20 = ((e0)var13_21.get(0)).a();
                                                                                                                if (var5_20 == 1) break block149;
                                                                                                                if (var5_20 == 2) break block150;
                                                                                                                if (var5_20 == 3) break block151;
                                                                                                                if (var5_20 == 4) break block152;
                                                                                                                if (var5_20 == 5) break block153;
                                                                                                                break block154;
                                                                                                            }
                                                                                                            catch (Throwable var1_4) {
                                                                                                                break block155;
                                                                                                            }
                                                                                                        }
                                                                                                        var1_1 = AutoTtsService.N;
                                                                                                        break block154;
                                                                                                    }
                                                                                                    var1_1 = AutoTtsService.L;
                                                                                                    break block154;
                                                                                                }
                                                                                                var1_1 = AutoTtsService.J;
                                                                                                break block154;
                                                                                            }
                                                                                            var1_1 = AutoTtsService.P;
                                                                                            break block154;
                                                                                        }
                                                                                        var1_1 = AutoTtsService.O;
                                                                                    }
                                                                                    var9_8 = var1_1;
                                                                                }
                                                                                var1_1 = c3.n.a;
                                                                                var11_18 = new StringBuilder();
                                                                                var11_18.append("language: ");
                                                                                var11_18.append((String)var9_8);
                                                                                var1_1.c("AutoTTS", var11_18.toString());
                                                                                var11_18 = this.M((String)var9_8);
                                                                                var1_1 = c3.n.a;
                                                                                var14_13 = new StringBuilder();
                                                                                var14_13.append("engine: ");
                                                                                var14_13.append((String)var11_18);
                                                                                var1_1.c("AutoTTS", var14_13.toString());
                                                                                if (var11_18.isEmpty()) break block170;
                                                                                var1_1 = var9_8;
                                                                                if (!var11_18.equals("Disable")) break block171;
                                                                            }
                                                                            var1_1 = var9_8;
                                                                            if (!var13_21.isEmpty()) {
                                                                                var5_20 = ((e0)var13_21.get(0)).a();
                                                                                var1_1 = var5_20 != 1 ? (var5_20 != 2 ? (var5_20 != 3 ? (var5_20 != 4 ? (var5_20 != 5 ? var9_8 : AutoTtsService.N) : AutoTtsService.L) : AutoTtsService.J) : AutoTtsService.P) : AutoTtsService.O;
                                                                            }
                                                                        }
                                                                        if ((var5_20 = this.onLoadLanguage((String)var1_1, "", "")) != -2 && var5_20 != -1) {
                                                                            var9_8 = var10_14;
                                                                            var10_14 = var1_1;
                                                                        } else {
                                                                            var11_18 = c3.n.a;
                                                                            var9_8 = new StringBuilder();
                                                                            var9_8.append("Languge is not supported: ");
                                                                            var9_8.append((String)var1_1);
                                                                            var9_8.append(", text: ");
                                                                            var9_8.append((String)var10_14);
                                                                            var11_18.d("AutoTTS", var9_8.toString());
                                                                            this.K(var2_6, 7);
                                                                            // MONITOREXIT : var12_19
                                                                            return;
                                                                        }
                                                                    }
                                                                    // MONITOREXIT : var12_19
                                                                    var11_18 = var9_8;
                                                                    break block162;
                                                                }
                                                                try {
                                                                    throw var1_4;
                                                                }
                                                                catch (Exception var10_16) {
                                                                    var1_1 = c3.n.a;
                                                                    var9_8 = new StringBuilder();
                                                                    var9_8.append("Synthesis ended with error: ");
                                                                    var9_8.append(var10_16.getMessage());
                                                                    var1_1.d("AutoTTS", var9_8.toString());
                                                                    this.K(var2_6, 8);
                                                                    return;
                                                                }
                                                            }
                                                            if (AutoTtsService.S != 5 || var5_20 != 0) break block172;
                                                            c3.n.a.c("AutoTTS", "Multilingual mode");
                                                            var12_19 = AutoTtsService.Q;
                                                            // MONITORENTER : var12_19
                                                            try {
                                                                var12_19.clear();
                                                                var10_14 = c3.e0.g((CharSequence)var14_13);
                                                                var5_20 = 0;
lbl558:
                                                                // 2 sources

                                                                while (true) {
                                                                    if (var5_20 < var10_14.size() && !this.q.get()) {
                                                                        var1_1 = ((e0)var10_14.get(var5_20)).b();
                                                                        if (!(var1_1.equalsIgnoreCase("unknown") || var1_1.isEmpty() || (var1_1 = this.M((String)var1_1)).isEmpty() || var1_1.equals("Disable"))) {
                                                                            AutoTtsService.Q.add((e0)var10_14.get(var5_20));
                                                                            break block156;
                                                                        } else {
                                                                            var11_18 = c3.d0.t(((e0)var10_14.get(var5_20)).c(), AutoTtsService.I, AutoTtsService.K, AutoTtsService.M, AutoTtsService.m0, AutoTtsService.h0);
                                                                            for (var6_24 = 0; var6_24 < var11_18.size(); ++var6_24) {
                                                                                if (c3.d0.n(((e0)var11_18.get(var6_24)).c())) {
                                                                                    var1_1 = System.out;
                                                                                    var9_8 = new StringBuilder();
                                                                                    var9_8.append("- ");
                                                                                    var9_8.append(((e0)var11_18.get(var6_24)).c());
                                                                                    var1_1.print(var9_8.toString());
                                                                                    var7_25 = AutoTtsService.I;
                                                                                    if (var7_25 != 0 && var7_25 != 1) {
                                                                                        if (var7_25 != 2) {
                                                                                            if (var7_25 != 3) continue;
                                                                                            var1_1 = AutoTtsService.Q;
                                                                                            var9_8 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.J);
                                                                                            var1_1.add(var9_8);
                                                                                            continue;
                                                                                        }
                                                                                        var9_8 = AutoTtsService.Q;
                                                                                        var1_1 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.P);
                                                                                        var9_8.add(var1_1);
                                                                                        continue;
                                                                                    }
                                                                                    var1_1 = AutoTtsService.Q;
                                                                                    var9_8 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.O);
                                                                                    var1_1.add(var9_8);
                                                                                    continue;
                                                                                }
                                                                                if (c3.d0.o(((e0)var11_18.get(var6_24)).c())) {
                                                                                    var7_25 = AutoTtsService.K;
                                                                                    if (var7_25 != 0 && var7_25 != 1) {
                                                                                        if (var7_25 != 2) {
                                                                                            if (var7_25 != 3) continue;
                                                                                            var9_8 = AutoTtsService.Q;
                                                                                            var1_1 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.L);
                                                                                            var9_8.add(var1_1);
                                                                                            continue;
                                                                                        }
                                                                                        var9_8 = AutoTtsService.Q;
                                                                                        var1_1 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.P);
                                                                                        var9_8.add(var1_1);
                                                                                        continue;
                                                                                    }
                                                                                    var9_8 = AutoTtsService.Q;
                                                                                    var1_1 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.O);
                                                                                    var9_8.add(var1_1);
                                                                                    continue;
                                                                                }
                                                                                if (c3.d0.l(((e0)var11_18.get(var6_24)).c())) {
                                                                                    var7_25 = AutoTtsService.M;
                                                                                    if (var7_25 != 0 && var7_25 != 1) {
                                                                                        if (var7_25 != 2) {
                                                                                            if (var7_25 != 3) continue;
                                                                                            var9_8 = AutoTtsService.Q;
                                                                                            var1_1 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.N);
                                                                                            var9_8.add(var1_1);
                                                                                            continue;
                                                                                        }
                                                                                        var1_1 = AutoTtsService.Q;
                                                                                        var9_8 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.P);
                                                                                        var1_1.add(var9_8);
                                                                                        continue;
                                                                                    }
                                                                                    var1_1 = AutoTtsService.Q;
                                                                                    var9_8 = new e0(((e0)var11_18.get(var6_24)).c(), AutoTtsService.O);
                                                                                    var1_1.add(var9_8);
                                                                                    continue;
                                                                                }
                                                                                var13_21 = clsCLD2.c(((e0)var11_18.get(var6_24)).c(), AutoTtsService.m0, AutoTtsService.h0, this.h);
                                                                                for (var7_25 = 0; var7_25 < var13_21.size(); ++var7_25) {
                                                                                    var1_1 = var9_8 = c3.e.c(((clsCLD2.a)var13_21.get((int)var7_25)).a);
                                                                                    if (var9_8 == null) {
                                                                                        var1_1 = ((clsCLD2.a)var13_21.get((int)var7_25)).b != false ? AutoTtsService.O : AutoTtsService.P;
                                                                                    }
                                                                                    if ((var9_8 = this.M((String)var1_1)).isEmpty() || var9_8.equals("Disable")) {
                                                                                        var1_1 = ((clsCLD2.a)var13_21.get((int)var7_25)).b != false ? AutoTtsService.O : AutoTtsService.P;
                                                                                    }
                                                                                    var9_8 = AutoTtsService.Q;
                                                                                    var14_13 = new e0(((clsCLD2.a)var13_21.get((int)var7_25)).c, (String)var1_1);
                                                                                    var9_8.add(var14_13);
                                                                                }
                                                                            }
                                                                        }
                                                                        break block156;
                                                                    }
                                                                    var1_1 = AutoTtsService.Q;
                                                                    if (var1_1.isEmpty()) {
                                                                        c3.n.a.d("AutoTTS", "lstLanString is empty!");
                                                                        this.K(var2_6, 7);
                                                                        // MONITOREXIT : var12_19
                                                                        return;
                                                                    }
                                                                    var11_18 = ((e0)var1_1.get(0)).c();
                                                                    var10_14 = ((e0)var1_1.get(0)).b();
                                                                    var5_20 = this.onLoadLanguage((String)var10_14, "", "");
                                                                    if (var5_20 != -2 && var5_20 != -1) break;
                                                                    break block158;
                                                                    break;
                                                                }
                                                            }
                                                            catch (Throwable var1_5) {
                                                                break block159;
                                                            }
                                                            // MONITOREXIT : var12_19
                                                            break block162;
                                                        }
                                                        var1_1 = c3.n.a;
                                                        var9_8 = new StringBuilder();
                                                        var9_8.append("Language is not supported: ");
                                                        var9_8.append((String)var10_14);
                                                        var9_8.append(", text: ");
                                                        var9_8.append((String)var11_18);
                                                        var1_1.d("AutoTTS", var9_8.toString());
                                                        this.K(var2_6, 7);
                                                        // MONITOREXIT : var12_19
                                                        return;
                                                    }
                                                    try {
                                                        throw var1_5;
                                                    }
                                                    catch (Exception var9_10) {
                                                        var10_14 = c3.n.a;
                                                        var1_1 = new StringBuilder();
                                                        var1_1.append("Synthesis ended with error: ");
                                                        var1_1.append(var9_10.getMessage());
                                                        var10_14.d("AutoTTS", var1_1.toString());
                                                        this.K(var2_6, 8);
                                                        return;
                                                    }
                                                }
                                                if (!var13_21.isEmpty() || !var12_19.isEmpty()) break block173;
                                                var5_20 = this.onLoadLanguage(var1_1.getLanguage(), var1_1.getCountry(), var1_1.getVariant());
                                                var12_19 = c3.n.a;
                                                var11_18 = new StringBuilder();
                                                var11_18.append("load ");
                                                var11_18.append(var5_20);
                                                var12_19.c("AutoTTS", var11_18.toString());
                                                if (var5_20 == -2) break block174;
                                                var11_18 = var9_8;
                                                if (var5_20 != -1) break block162;
                                            }
                                            var11_18 = c3.n.a;
                                            var10_14 = new StringBuilder();
                                            var10_14.append("Language is not supported: ");
                                            var10_14.append(var1_1.getLanguage());
                                            var10_14.append(", text: ");
                                            var10_14.append((String)var9_8);
                                            var11_18.d("AutoTTS", var10_14.toString());
                                            this.K(var2_6, 9);
                                            return;
                                        }
                                        this.b0((String)var13_21, this.f0((String)var12_19), (String)var11_18, false);
                                        var11_18 = var9_8;
                                    }
                                    if (this.d < 0 || this.d >= this.f.size()) break block175;
                                    if (((k0)this.f.get(this.d)).g() == null) {
                                        c3.n.a.d("AutoTTS", "mTTSIndex refers null tts.");
                                        this.K(var2_6, 10);
                                        return;
                                    }
                                    var7_25 = this.O((String)var10_14);
                                    var6_24 = this.R((String)var10_14);
                                    var5_20 = this.N((String)var10_14);
                                    var4_26 = (float)this.l / 100.0f * (float)var7_25 / 100.0f;
                                    var3_17 = (float)this.m / 100.0f * (float)var5_20 / 100.0f;
                                    ((k0)this.f.get(this.d)).g().setSpeechRate(var4_26);
                                    ((k0)this.f.get(this.d)).g().setPitch(var3_17);
                                    var1_1 = new Bundle(this.j);
                                    var1_1.remove("language");
                                    var1_1.remove("country");
                                    var1_1.remove("voiceName");
                                    var1_1.remove("variant");
                                    var1_1.remove("pitch");
                                    var1_1.remove("rate");
                                    var1_1.remove("utteranceId");
                                    if (AutoTtsService.W) {
                                        var1_1.remove("streamType");
                                        var1_1.remove("audioAttributes");
                                    }
                                    if ((double)(var3_17 = this.k * (float)var6_24 / 100.0f) != 0.0) {
                                        var1_1.putFloat("volume", var3_17);
                                    }
                                    ((k0)this.f.get(this.d)).g().setOnUtteranceProgressListener((UtteranceProgressListener)new e(this, var2_6, null));
                                    if (!this.p.get() && !this.q.get()) {
                                        ((k0)this.f.get((int)this.d)).g = true;
                                        if (AutoTtsService.X && !((k0)this.f.get((int)this.d)).h) {
                                            try {
                                                var9_8 = new AudioAttributes.Builder();
                                                var9_8 = var9_8.setUsage(11).setContentType(1).build();
                                                ((k0)this.f.get(this.d)).g().setAudioAttributes((AudioAttributes)var9_8);
                                                ((k0)this.f.get((int)this.d)).h = true;
                                            }
                                            catch (Exception var9_11) {
                                                c3.n.a.d("AutoTTS", var9_11.toString());
                                            }
                                        }
                                        this.t.postDelayed(new Runnable(this, (String)var11_18, (Bundle)var1_1, var2_6){
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
                                                        Object object = c3.n.a;
                                                        Object object2 = new StringBuilder();
                                                        ((StringBuilder)object2).append("Current engine: ");
                                                        ((StringBuilder)object2).append(((k0)this.f.f.get(this.f.d)).e());
                                                        ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
                                                        object2 = c3.n.a;
                                                        object = new StringBuilder();
                                                        ((StringBuilder)object).append(i0);
                                                        ((StringBuilder)object).append("_");
                                                        ((StringBuilder)object).append(R);
                                                        ((p)object2).c("AutoTTS", ((StringBuilder)object).toString());
                                                        object = c3.n.a;
                                                        object2 = new StringBuilder();
                                                        ((StringBuilder)object2).append("speak 1: ");
                                                        ((StringBuilder)object2).append(this.c);
                                                        ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
                                                        object = ((k0)this.f.f.get(this.f.d)).g();
                                                        object2 = this.c;
                                                        Bundle bundle = this.d;
                                                        StringBuilder stringBuilder = new StringBuilder();
                                                        stringBuilder.append(i0);
                                                        stringBuilder.append("_");
                                                        stringBuilder.append(R);
                                                        if (object.speak((CharSequence)object2, 0, bundle, stringBuilder.toString()) != 0) {
                                                            c3.n.a.d("AutoTTS", "Speaking failed!!!");
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
                                                p p3 = c3.n.a;
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("onSynthesis Error: ");
                                                stringBuilder.append(exception2.getMessage());
                                                p3.d("AutoTTS", stringBuilder.toString());
                                                this.f.K(this.e, 14);
                                                this.f.n0(14);
                                            }
                                        }, 50L);
                                    }
                                    if (AutoTtsService.Y) break block176;
                                    var1_1 = this.o;
                                    // MONITORENTER : var1_1
                                    ** try [egrp 18[TRYBLOCK] [124 : 5658->5703)] { 
lbl771:
                                    // 1 sources

                                    ** GOTO lbl-1000
                                }
                                this.g0(var2_6);
                                break block177;
                            }
                            c3.n.a.d("AutoTTS", "mTTSIndex out of range.");
                            this.K(var2_6, 10);
                            return;
                        }
                        for (var7_25 = 0; var7_25 < var14_13.size(); ++var7_25) {
                            var1_1 = var10_14 = c3.e.c(((clsCLD2.a)var14_13.get((int)var7_25)).a);
                            if (var10_14 == null) {
                                var1_1 = ((clsCLD2.a)var14_13.get((int)var7_25)).b != false ? AutoTtsService.O : AutoTtsService.P;
                            }
                            if ((var10_14 = this.M((String)var1_1)).isEmpty() || var10_14.equals("Disable")) {
                                var1_1 = ((clsCLD2.a)var14_13.get((int)var7_25)).b != false ? AutoTtsService.O : AutoTtsService.P;
                            }
                            var10_14 = AutoTtsService.Q;
                            var15_22 = new e0(((clsCLD2.a)var14_13.get((int)var7_25)).c, (String)var1_1);
                            var10_14.add(var15_22);
                        }
                    }
                    ++var6_24;
                    ** while (true)
                }
                ++var5_20;
                ** while (true)
lbl-1000:
                // 2 sources

                {
                    while (!this.p.get() && !(var8_27 = this.q.get())) {
                        this.o.wait();
                    }
                    break block160;
                }
lbl802:
                // 1 sources

                catch (Throwable var2_7) {
                    throw var2_7;
                }
                catch (InterruptedException var9_12) {}
            }
            // MONITOREXIT : var1_1
        }
        c3.n.a.c("AutoTTS", "onSynthesizeText ended");
        this.K(var2_6, 13);
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
            this.a.j0(m0, "Valid license");
        }

        @Override
        public void b(int n3) {
            AutoTtsService.o(1);
            this.a.j0(m0, "License check error");
        }

        @Override
        public void c(int n3) {
            AutoTtsService.o(1);
            this.a.j0(m0, "Invalid license");
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
            if (this.a.i >= T.size()) {
                c3.n.a.c("AutoTTS", "All tts engines have been initialized. (1)");
                return;
            }
            Object object = c3.n.a;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Init ");
            ((StringBuilder)object2).append(((k0)this.a.f.get(this.a.i)).e());
            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
            object2 = c3.n.a;
            object = new StringBuilder();
            ((StringBuilder)object).append("res ");
            ((StringBuilder)object).append(n3);
            ((p)object2).c("AutoTTS", ((StringBuilder)object).toString());
            if (n3 == 0) {
                if (X) {
                    try {
                        object = new AudioAttributes.Builder();
                        object = object.setUsage(11).setContentType(1).build();
                        f0.setAudioAttributes((AudioAttributes)object);
                        ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).i)).h = true;
                    }
                    catch (Exception exception) {
                        c3.n.a.d("AutoTTS", ((Object)exception).toString());
                    }
                }
                ((k0)this.a.f.get(this.a.i)).k(f0);
                ((k0)this.a.f.get(this.a.i)).j(2);
                if (((String)T.get(this.a.i)).equals("com.google.android.tts")) {
                    object = this.a;
                    AutoTtsService.C((AutoTtsService)((Object)object), ((AutoTtsService)((Object)object)).i);
                }
            } else {
                ((k0)this.a.f.get(this.a.i)).k(f0);
                ((k0)this.a.f.get(this.a.i)).j(-1);
            }
            n3 = 0;
            while (n3 == 0) {
                block11: {
                    AutoTtsService.f(this.a);
                    if (this.a.i < T.size()) {
                        this.a.f.add(new k0((String)T.get(this.a.i)));
                        object = new c3.d(this.a.h, this.a);
                        ((c3.d)object).c((String)T.get(this.a.i));
                        this.a.g.add(object);
                        try {
                            Context context = this.a.h;
                            object2 = new c(this.a);
                            object = new TextToSpeech(context, (TextToSpeech.OnInitListener)object2, (String)T.get(this.a.i));
                            AutoTtsService.B((TextToSpeech)object);
                            break block11;
                        }
                        catch (Exception exception) {
                            object = c3.n.a;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Error when initializing ");
                            ((StringBuilder)object2).append((String)T.get(this.a.i));
                            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
                            continue;
                        }
                    }
                    c3.n.a.c("AutoTTS", "All tts engines have been initialized. (2)");
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
                p p3 = c3.n.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Restore ");
                stringBuilder.append(((k0)this.a.f.get(this.a.u)).e());
                p3.c("AutoTTS", stringBuilder.toString());
                p3 = c3.n.a;
                stringBuilder = new StringBuilder();
                stringBuilder.append("res ");
                stringBuilder.append(n3);
                p3.c("AutoTTS", stringBuilder.toString());
                if (n3 == 0) {
                    if (X) {
                        try {
                            p3 = new AudioAttributes.Builder();
                            p3 = p3.setUsage(11).setContentType(1).build();
                            f0.setAudioAttributes((AudioAttributes)p3);
                            ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).u)).h = true;
                        }
                        catch (Exception exception) {
                            c3.n.a.d("AutoTTS", ((Object)exception).toString());
                        }
                    }
                    ((k0)this.a.f.get(this.a.u)).k(f0);
                    ((k0)this.a.f.get(this.a.u)).j(2);
                    ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).u)).e = "";
                    ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).u)).d = null;
                } else {
                    ((k0)this.a.f.get(this.a.u)).k(f0);
                    ((k0)this.a.f.get(this.a.u)).j(-1);
                }
                AutoTtsService.G(this.a, -1);
                return;
            }
            c3.n.a.c("AutoTTS", "ttsInitListener_restore invalid index");
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
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDone ");
            stringBuilder.append(string);
            p3.c("AutoTTS", stringBuilder.toString());
            if (Q.size() > 1) {
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
                        block26: {
                            block24: {
                                block44: {
                                    block25: {
                                        block23: {
                                            block39: {
                                                block33: {
                                                    block40: {
                                                        block41: {
                                                            block42: {
                                                                block43: {
                                                                    block34: {
                                                                        block38: {
                                                                            block35: {
                                                                                block36: {
                                                                                    block37: {
                                                                                        block32: {
                                                                                            block30: {
                                                                                                block31: {
                                                                                                    block27: {
                                                                                                        block28: {
                                                                                                            block29: {
                                                                                                                AutoTtsService.e();
                                                                                                                AutoTtsService.k().remove(0);
                                                                                                                var10_1 = ((e0)AutoTtsService.k().get(0)).c();
                                                                                                                var3_2 = AutoTtsService.S;
                                                                                                                if (var3_2 != 1) break block27;
                                                                                                                var3_2 = ((e0)AutoTtsService.k().get(0)).a();
                                                                                                                var7_3 /* !! */  = "eng";
                                                                                                                if (var3_2 == 1) break block28;
                                                                                                                if (var3_2 == 2) break block29;
                                                                                                                if (var3_2 != 3) {
                                                                                                                    if (var3_2 != 4) {
                                                                                                                        if (var3_2 == 5) {
                                                                                                                            var7_3 /* !! */  = AutoTtsService.N;
                                                                                                                            var3_2 = AutoTtsService.l(this.c.c, AutoTtsService.N, "", "");
                                                                                                                            if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                                var8_4 = c3.n.a;
                                                                                                                                var7_3 /* !! */  = new StringBuilder();
                                                                                                                                var7_3 /* !! */ .append("Language ");
                                                                                                                                var7_3 /* !! */ .append(AutoTtsService.N);
                                                                                                                                var7_3 /* !! */ .append(" is not supported.\n Text: ");
                                                                                                                                var7_3 /* !! */ .append(var10_1);
                                                                                                                                var8_4.d("AutoTTS", var7_3 /* !! */ .toString());
                                                                                                                                var7_3 /* !! */  = this.c;
                                                                                                                                AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                                return;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        break block23;
                                                                                                                    } else {
                                                                                                                        var7_3 /* !! */  = AutoTtsService.L;
                                                                                                                        var3_2 = AutoTtsService.l(this.c.c, AutoTtsService.L, "", "");
                                                                                                                        if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                            var7_3 /* !! */  = c3.n.a;
                                                                                                                            var8_6 = new StringBuilder();
                                                                                                                            var8_6.append("Language ");
                                                                                                                            var8_6.append(AutoTtsService.L);
                                                                                                                            var8_6.append(" is not supported.\n Text: ");
                                                                                                                            var8_6.append(var10_1);
                                                                                                                            var7_3 /* !! */ .d("AutoTTS", var8_6.toString());
                                                                                                                            var7_3 /* !! */  = this.c;
                                                                                                                            AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                            return;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    break block23;
                                                                                                                } else {
                                                                                                                    var7_3 /* !! */  = AutoTtsService.J;
                                                                                                                    var3_2 = AutoTtsService.l(this.c.c, AutoTtsService.J, "", "");
                                                                                                                    if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                        var7_3 /* !! */  = c3.n.a;
                                                                                                                        var8_7 = new StringBuilder();
                                                                                                                        var8_7.append("Language ");
                                                                                                                        var8_7.append(AutoTtsService.J);
                                                                                                                        var8_7.append(" is not supported.\n Text: ");
                                                                                                                        var8_7.append(var10_1);
                                                                                                                        var7_3 /* !! */ .d("AutoTTS", var8_7.toString());
                                                                                                                        var7_3 /* !! */  = this.c;
                                                                                                                        AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                        return;
                                                                                                                    }
                                                                                                                }
                                                                                                                break block23;
                                                                                                            }
                                                                                                            var7_3 /* !! */  = AutoTtsService.H;
                                                                                                            var3_2 = AutoTtsService.l(this.c.c, AutoTtsService.H, "", "");
                                                                                                            if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                var8_8 = c3.n.a;
                                                                                                                var7_3 /* !! */  = new StringBuilder();
                                                                                                                var7_3 /* !! */ .append("Language ");
                                                                                                                var7_3 /* !! */ .append(AutoTtsService.H);
                                                                                                                var7_3 /* !! */ .append(" is not supported.\n Text: ");
                                                                                                                var7_3 /* !! */ .append(var10_1);
                                                                                                                var8_8.d("AutoTTS", var7_3 /* !! */ .toString());
                                                                                                                var7_3 /* !! */  = this.c;
                                                                                                                AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                return;
                                                                                                            }
                                                                                                            break block23;
                                                                                                        }
                                                                                                        var3_2 = AutoTtsService.l(this.c.c, "eng", "", "");
                                                                                                        if (var3_2 == -2 || var3_2 == -1) {
                                                                                                            var7_3 /* !! */  = c3.n.a;
                                                                                                            var8_9 = new StringBuilder();
                                                                                                            var8_9.append("Language eng is not supported.\n Text: ");
                                                                                                            var8_9.append(var10_1);
                                                                                                            var7_3 /* !! */ .d("AutoTTS", var8_9.toString());
                                                                                                            var7_3 /* !! */  = this.c;
                                                                                                            AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 2);
                                                                                                            return;
                                                                                                        }
                                                                                                        break block23;
                                                                                                    }
                                                                                                    if (AutoTtsService.S == 4 || AutoTtsService.S == 5) break block30;
                                                                                                    var8_10 = ((e0)AutoTtsService.k().get(0)).b();
                                                                                                    var3_2 = AutoTtsService.l(this.c.c, (String)var8_10, "", "");
                                                                                                    if (var3_2 == -2) break block31;
                                                                                                    var7_3 /* !! */  = var8_10;
                                                                                                    if (var3_2 != -1) break block23;
                                                                                                }
                                                                                                var9_13 = c3.n.a;
                                                                                                var7_3 /* !! */  = new StringBuilder();
                                                                                                var7_3 /* !! */ .append("Language ");
                                                                                                var7_3 /* !! */ .append((String)var8_10);
                                                                                                var7_3 /* !! */ .append(" is not supported.\n Text: ");
                                                                                                var7_3 /* !! */ .append(var10_1);
                                                                                                var9_13.d("AutoTTS", var7_3 /* !! */ .toString());
                                                                                                var7_3 /* !! */  = this.c;
                                                                                                AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 4);
                                                                                                return;
                                                                                            }
                                                                                            var8_10 = ((e0)AutoTtsService.k().get(0)).b();
                                                                                            if (var8_10.isEmpty()) break block32;
                                                                                            var7_3 /* !! */  = var8_10;
                                                                                            if (!var8_10.equals("unknown")) break block33;
                                                                                        }
                                                                                        var8_10 = clsCLD2.b(var10_1, AutoTtsService.n(), AutoTtsService.q(), AutoTtsService.D(this.c.c));
                                                                                        var9_12 = c3.n.a;
                                                                                        var7_3 /* !! */  = new StringBuilder();
                                                                                        var7_3 /* !! */ .append("Cld2: ");
                                                                                        var7_3 /* !! */ .append((String)var8_10);
                                                                                        var7_3 /* !! */ .append(" '");
                                                                                        var7_3 /* !! */ .append(var10_1);
                                                                                        var7_3 /* !! */ .append("'");
                                                                                        var9_12.c("AutoTTS", var7_3 /* !! */ .toString());
                                                                                        var7_3 /* !! */  = var8_10;
                                                                                        if (var8_10.length() > 2) {
                                                                                            var7_3 /* !! */  = var8_10.substring(0, 2);
                                                                                        }
                                                                                        var8_10 = var9_12 = c3.e.c((String)var7_3 /* !! */ );
                                                                                        if (var9_12 != null) break block34;
                                                                                        var3_2 = ((e0)AutoTtsService.k().get(0)).a();
                                                                                        if (var3_2 == 1) break block35;
                                                                                        if (var3_2 == 2) break block36;
                                                                                        if (var3_2 == 3) break block37;
                                                                                        if (var3_2 != 4) {
                                                                                            if (var3_2 == 5) {
                                                                                                var7_3 /* !! */  = AutoTtsService.N;
                                                                                            }
                                                                                            break block38;
                                                                                        } else {
                                                                                            var7_3 /* !! */  = AutoTtsService.L;
                                                                                        }
                                                                                        break block38;
                                                                                    }
                                                                                    var7_3 /* !! */  = AutoTtsService.J;
                                                                                    break block38;
                                                                                }
                                                                                var7_3 /* !! */  = AutoTtsService.P;
                                                                                break block38;
                                                                            }
                                                                            var7_3 /* !! */  = AutoTtsService.O;
                                                                        }
                                                                        var8_10 = var7_3 /* !! */ ;
                                                                    }
                                                                    if (!(var7_3 /* !! */  = AutoTtsService.r(this.c.c, (String)var8_10)).isEmpty() && !var7_3 /* !! */ .equals("Disable")) break block39;
                                                                    var3_2 = ((e0)AutoTtsService.k().get(0)).a();
                                                                    if (var3_2 == 1) break block40;
                                                                    if (var3_2 == 2) break block41;
                                                                    if (var3_2 == 3) break block42;
                                                                    if (var3_2 == 4) break block43;
                                                                    if (var3_2 != 5) break block39;
                                                                    var7_3 /* !! */  = AutoTtsService.N;
                                                                    break block33;
                                                                }
                                                                var7_3 /* !! */  = AutoTtsService.L;
                                                                break block33;
                                                            }
                                                            var7_3 /* !! */  = AutoTtsService.J;
                                                            break block33;
                                                        }
                                                        var7_3 /* !! */  = AutoTtsService.P;
                                                        break block33;
                                                    }
                                                    var7_3 /* !! */  = AutoTtsService.O;
                                                }
                                                var8_10 = var7_3 /* !! */ ;
                                            }
                                            var3_2 = AutoTtsService.l(this.c.c, (String)var8_10, "", "");
                                            if (var3_2 == -2) break block24;
                                            var7_3 /* !! */  = var8_10;
                                            if (var3_2 == -1) break block24;
                                        }
                                        var5_14 = AutoTtsService.s(this.c.c, (String)var7_3 /* !! */ );
                                        var4_15 = AutoTtsService.t(this.c.c, (String)var7_3 /* !! */ );
                                        var3_2 = AutoTtsService.u(this.c.c, (String)var7_3 /* !! */ );
                                        var1_16 = (float)AutoTtsService.v(this.c.c) / 100.0f * (float)var5_14 / 100.0f;
                                        var2_17 = (float)AutoTtsService.w(this.c.c) / 100.0f * (float)var3_2 / 100.0f;
                                        ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g().setSpeechRate(var1_16);
                                        ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g().setPitch(var2_17);
                                        var7_3 /* !! */  = new Bundle(AutoTtsService.x(this.c.c));
                                        var7_3 /* !! */ .remove("language");
                                        var7_3 /* !! */ .remove("country");
                                        var7_3 /* !! */ .remove("voiceName");
                                        var7_3 /* !! */ .remove("variant");
                                        var7_3 /* !! */ .remove("pitch");
                                        var7_3 /* !! */ .remove("rate");
                                        var7_3 /* !! */ .remove("utteranceId");
                                        if (!AutoTtsService.W) break block25;
                                        var7_3 /* !! */ .remove("streamType");
                                        var7_3 /* !! */ .remove("audioAttributes");
                                        {
                                            catch (Exception var8_5) {}
                                        }
                                    }
                                    if ((double)(var1_16 = AutoTtsService.y(this.c.c) * (float)var4_15 / 100.0f) == 0.0) ** GOTO lbl225
                                    var7_3 /* !! */ .putFloat("volume", var1_16);
lbl225:
                                    // 2 sources

                                    var9_12 = ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g();
                                    var8_10 = this.c;
                                    var11_18 = new e(var8_10.c, com.vnspeak.autotts.AutoTtsService$e.a((e)var8_10), null);
                                    var9_12.setOnUtteranceProgressListener((UtteranceProgressListener)var11_18);
                                    ((k0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.g((AutoTtsService)this.c.c))).g = true;
                                    if (!AutoTtsService.X || (var6_19 = ((k0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.g((AutoTtsService)this.c.c))).h)) break block44;
                                    try {
                                        var8_10 = new AudioAttributes.Builder();
                                        var8_10 = var8_10.setUsage(11).setContentType(1).build();
                                        ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g().setAudioAttributes((AudioAttributes)var8_10);
                                        ((k0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.g((AutoTtsService)this.c.c))).h = true;
                                    }
                                    catch (Exception var8_11) {
                                        c3.n.a.d("AutoTTS", var8_11.toString());
                                        break block26;
                                    }
                                }
                                var8_10 = c3.n.a;
                                var9_12 = new StringBuilder();
                                var9_12.append("speak 2: ");
                                var9_12.append(var10_1);
                                var8_10.c("AutoTTS", var9_12.toString());
                                var9_12 = ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.g(this.c.c))).g();
                                var8_10 = new StringBuilder();
                                var8_10.append(AutoTtsService.h());
                                var8_10.append("_");
                                var8_10.append(AutoTtsService.c());
                                if (var9_12.speak((CharSequence)var10_1, 0, (Bundle)var7_3 /* !! */ , var8_10.toString()) == 0) return;
                                c3.n.a.d("AutoTTS", "Speaking failed!!!");
                                var7_3 /* !! */  = this.c;
                                AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 5);
                                return;
                            }
                            var7_3 /* !! */  = c3.n.a;
                            var9_12 = new StringBuilder();
                            var9_12.append("Language ");
                            var9_12.append((String)var8_10);
                            var9_12.append(" is not supported.\n Text: ");
                            var9_12.append(var10_1);
                            var7_3 /* !! */ .d("AutoTTS", var9_12.toString());
                            var7_3 /* !! */  = this.c;
                            AutoTtsService.i(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 14);
                            return;
                        }
                        var9_12 = c3.n.a;
                        var7_3 /* !! */  = new StringBuilder();
                        var7_3 /* !! */ .append("onDone Error: ");
                        var7_3 /* !! */ .append(var8_5.getMessage());
                        var9_12.d("AutoTTS", var7_3 /* !! */ .toString());
                        var7_3 /* !! */  = this.c;
                        AutoTtsService.m(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 6);
                    }
                }, 50L);
                return;
            }
            c3.n.a.c("AutoTTS", "No more text to read.");
            this.c.L(this.a, 7);
        }

        public void onError(String string) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            p3.d("AutoTTS", stringBuilder.toString());
            this.c.L(this.a, 8);
        }

        public void onError(String string, int n3) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            stringBuilder.append(" code ");
            stringBuilder.append(n3);
            p3.d("AutoTTS", stringBuilder.toString());
            this.c.L(this.a, 9);
        }

        public void onStart(String string) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStart ");
            stringBuilder.append(string);
            p3.c("AutoTTS", stringBuilder.toString());
            if (!this.a.hasStarted()) {
                this.a.start(16000, 2, 1);
            }
        }

        public void onStop(String string, boolean bl) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStop ");
            stringBuilder.append(string);
            p3.c("AutoTTS", stringBuilder.toString());
        }
    }
}

