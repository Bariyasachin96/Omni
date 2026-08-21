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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoTtsService
extends TextToSpeechService {
    public static volatile String H;
    public static volatile String I;
    public static volatile int J;
    public static volatile String K;
    public static volatile int L;
    public static volatile String M;
    public static volatile int N;
    public static volatile String O;
    public static volatile String P;
    public static volatile String Q;
    public static final ArrayList R;
    public static int S;
    public static volatile int T;
    public static volatile ArrayList U;
    public static boolean V;
    public static boolean W;
    public static boolean X;
    public static boolean Y;
    public static boolean Z;
    public static boolean a0;
    public static boolean b0;
    public static boolean c0;
    public static boolean d0;
    public static boolean e0;
    public static int f0;
    public static volatile ArrayList g0;
    public static TextToSpeech h0;
    public static int i0;
    public static int j0;
    public static volatile String k0;
    public static final byte[] l0;
    public static String m0;
    public static final byte[] n0;
    public static int o0;
    public LicenseChecker A = null;
    public final int B;
    public final int C;
    public final int D;
    public final int E;
    public final int F;
    public int G = 2;
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
    public Runnable u;
    public int v = -1;
    public volatile boolean w = false;
    public long x = 0L;
    public long y = 0L;
    public LicenseCheckerCallback z;

    static {
        R = new ArrayList();
        S = 0;
        U = null;
        V = false;
        W = false;
        X = false;
        Y = false;
        Z = false;
        a0 = false;
        b0 = true;
        c0 = false;
        d0 = true;
        e0 = false;
        f0 = 1;
        g0 = new ArrayList();
        i0 = 0;
        j0 = -1;
        k0 = "";
        l0 = new byte[32];
        m0 = "";
        n0 = new byte[]{-45, 64, 37, -10, -72, -47, 64, -63, 102, 86, -35, -85, 78, -15, -26, -113, -54, 36, -74, 35};
        o0 = -1;
    }

    public AutoTtsService() {
        this.B = 0;
        this.C = 1;
        this.D = 2;
        this.E = 3;
        this.F = -1;
    }

    public static /* synthetic */ TextToSpeech C(TextToSpeech textToSpeech) {
        h0 = textToSpeech;
        return textToSpeech;
    }

    public static /* synthetic */ int F(AutoTtsService autoTtsService, int n3) {
        autoTtsService.e = n3;
        return n3;
    }

    public static /* synthetic */ int J(AutoTtsService autoTtsService, int n3) {
        autoTtsService.v = n3;
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

    public static /* synthetic */ int d(AutoTtsService autoTtsService) {
        int n3 = autoTtsService.i;
        autoTtsService.i = n3 + 1;
        return n3;
    }

    public static /* synthetic */ int g(int n3) {
        S = n3;
        return n3;
    }

    public static /* synthetic */ int h() {
        int n3 = S;
        S = n3 + 1;
        return n3;
    }

    public static /* synthetic */ Runnable n(AutoTtsService autoTtsService, Runnable runnable) {
        autoTtsService.u = runnable;
        return runnable;
    }

    public static /* synthetic */ int s(int n3) {
        o0 = n3;
        return n3;
    }

    public static void s0() {
        synchronized (AutoTtsService.class) {
            Object object;
            c3.n.a.c("AutoTTS", "updateLanguage2LetterCodes");
            ArrayList<String> arrayList = new ArrayList<String>();
            Object object2 = c3.n.c;
            synchronized (object2) {
                for (int i3 = 0; i3 < (object = c3.n.c).size(); ++i3) {
                    if (((f)object.get((int)i3)).i || ((f)object.get((int)i3)).f.isEmpty() || ((f)object.get((int)i3)).f.equalsIgnoreCase("disable")) continue;
                    CharSequence charSequence = c3.e.b(((f)object.get((int)i3)).b);
                    if (charSequence != null && !arrayList.contains(charSequence)) {
                        arrayList.add((String)charSequence);
                        continue;
                    }
                    if (charSequence != null) continue;
                    p p3 = c3.n.a;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("unmapped language code: ");
                    ((StringBuilder)charSequence).append(((f)object.get((int)i3)).b);
                    p3.c("AutoTTS", ((StringBuilder)charSequence).toString());
                }
                c3.n.f.clear();
                c3.n.f.addAll(arrayList);
            }
            clsCLD2.i(c3.n.f);
            object = c3.n.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(c3.n.f.toString());
            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
            return;
        }
    }

    public final void K() {
        LicenseChecker licenseChecker = this.A;
        if (licenseChecker != null) {
            licenseChecker.f(this.z);
        }
    }

    public final Notification L() {
        return new k((Context)this, "tts_channel").d("Auto TTS active").g(17301540).f(true).a();
    }

    public final void M() {
        NotificationChannel notificationChannel = new NotificationChannel("tts_channel", (CharSequence)"TTS Engine", 2);
        ((NotificationManager)this.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
    }

    public final void N(SynthesisCallback synthesisCallback, int n3) {
        if (!synthesisCallback.hasStarted()) {
            synthesisCallback.start(16000, 2, 1);
        }
        if (!synthesisCallback.hasFinished()) {
            synthesisCallback.done();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void O(SynthesisCallback synthesisCallback, int n3) {
        Object object = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("endSynthesis #");
        stringBuilder.append(n3);
        ((p)object).c("AutoTTS", stringBuilder.toString());
        object = this.o;
        synchronized (object) {
            this.p.set(true);
            this.o.notifyAll();
        }
        if (!synthesisCallback.hasStarted() || synthesisCallback.hasFinished()) return;
        synthesisCallback.done();
    }

    public final void P() {
        List list = c3.n.c;
        synchronized (list) {
            if (!this.w || list.isEmpty()) {
                c3.n.a.c("AutoTTS", "languages missing at point of use - reloading");
                this.e0();
            }
            return;
        }
    }

    public final String Q(String object) {
        Object object2 = c3.n.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("getEngine4Language ");
        ((StringBuilder)object3).append((String)object);
        ((p)object2).c("AutoTTS", ((StringBuilder)object3).toString());
        if (T == 3) {
            return "com.google.android.tts";
        }
        object2 = c3.n.c;
        synchronized (object2) {
            for (int i3 = 0; i3 < (object3 = c3.n.c).size(); ++i3) {
                Object object4;
                if (!(((f)object3.get((int)i3)).f.isEmpty() || ((f)object3.get((int)i3)).f.equalsIgnoreCase("disable") || ((f)object3.get((int)i3)).i)) {
                    object4 = c3.n.a;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("- ");
                    stringBuilder.append(((f)object3.get((int)i3)).b);
                    stringBuilder.append(" ");
                    stringBuilder.append(((f)object3.get((int)i3)).f);
                    ((p)object4).c("AutoTTS", stringBuilder.toString());
                }
                if (!((String)object).equals(((f)object3.get((int)i3)).b)) continue;
                if (((f)object3.get((int)i3)).i) {
                    c3.n.a.c("AutoTTS", " res1 Disable");
                    return "Disable";
                }
                object = c3.n.a;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append(" res ");
                ((StringBuilder)object4).append(((f)object3.get((int)i3)).f);
                ((p)object).c("AutoTTS", ((StringBuilder)object4).toString());
                object = ((f)object3.get((int)i3)).f;
                return object;
            }
        }
        c3.n.a.c("AutoTTS", " res ''");
        return "";
    }

    public final int R(String string) {
        List list = c3.n.c;
        synchronized (list) {
            List list2;
            for (int i3 = 0; i3 < (list2 = c3.n.c).size(); ++i3) {
                if (!((f)list2.get((int)i3)).b.equals(string)) continue;
                i3 = ((f)list2.get((int)i3)).e;
                return i3;
            }
            return 100;
        }
    }

    public final int S(String string) {
        List list = c3.n.c;
        synchronized (list) {
            List list2;
            for (int i3 = 0; i3 < (list2 = c3.n.c).size(); ++i3) {
                if (!((f)list2.get((int)i3)).b.equals(string)) continue;
                i3 = ((f)list2.get((int)i3)).c;
                return i3;
            }
            return 100;
        }
    }

    public final String T(String object) {
        Object object2 = c3.n.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("getVariant4Language ");
        ((StringBuilder)object3).append((String)object);
        ((p)object2).c("AutoTTS", ((StringBuilder)object3).toString());
        if (T == 3) {
            return object;
        }
        object3 = c3.n.c;
        synchronized (object3) {
            for (int i3 = 0; i3 < (object2 = c3.n.c).size(); ++i3) {
                if (((f)object2.get((int)i3)).f.isEmpty() || ((f)object2.get((int)i3)).f.equalsIgnoreCase("disable") || ((f)object2.get((int)i3)).i) continue;
                p p3 = c3.n.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" -");
                stringBuilder.append(((f)object2.get((int)i3)).b);
                stringBuilder.append(" -> ");
                stringBuilder.append(((f)object2.get((int)i3)).h);
                p3.c("AutoTTS", stringBuilder.toString());
                if (!((String)object).equals(((f)object2.get((int)i3)).b)) continue;
                object = c3.n.a;
                stringBuilder = new StringBuilder();
                stringBuilder.append(" Found ");
                stringBuilder.append(((f)object2.get((int)i3)).h);
                ((p)object).c("AutoTTS", stringBuilder.toString());
                object = ((f)object2.get((int)i3)).h;
                return object;
            }
            return "";
        }
    }

    public final String U(String charSequence) {
        Object object = c3.n.a;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("getVoice4Language ");
        ((StringBuilder)object2).append((String)charSequence);
        ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
        if (T == 3) {
            return charSequence;
        }
        object = c3.n.c;
        synchronized (object) {
            for (int i3 = 0; i3 < (object2 = c3.n.c).size(); ++i3) {
                if (((f)object2.get((int)i3)).f.isEmpty() || ((f)object2.get((int)i3)).f.equalsIgnoreCase("disable") || ((f)object2.get((int)i3)).i) continue;
                p p3 = c3.n.a;
                Object object3 = new StringBuilder();
                ((StringBuilder)object3).append(" -");
                ((StringBuilder)object3).append(((f)object2.get((int)i3)).b);
                ((StringBuilder)object3).append(" -> ");
                ((StringBuilder)object3).append(((f)object2.get((int)i3)).g);
                p3.c("AutoTTS", ((StringBuilder)object3).toString());
                if (!((String)charSequence).equals(((f)object2.get((int)i3)).b)) continue;
                object3 = c3.n.a;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" Found ");
                ((StringBuilder)charSequence).append(((f)object2.get((int)i3)).g);
                ((p)object3).c("AutoTTS", ((StringBuilder)charSequence).toString());
                charSequence = ((f)object2.get((int)i3)).g;
                return charSequence;
            }
            return "";
        }
    }

    public final int V(String string) {
        List list = c3.n.c;
        synchronized (list) {
            List list2;
            for (int i3 = 0; i3 < (list2 = c3.n.c).size(); ++i3) {
                if (!((f)list2.get((int)i3)).b.equals(string)) continue;
                i3 = ((f)list2.get((int)i3)).d;
                return i3;
            }
            return 100;
        }
    }

    public final boolean W() {
        if (Build.VERSION.SDK_INT >= 33) {
            return this.checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0;
        }
        return true;
    }

    public final void X() {
        synchronized (this) {
            int n3;
            c3.n.a.c("AutoTTS", "initAllTTS");
            for (int i3 = 0; i3 < (n3 = this.f.size()); ++i3) {
                ((k0)this.f.get(i3)).m();
                ((k0)this.f.get(i3)).l();
            }
            this.f.clear();
            this.i = 0;
            if (!U.isEmpty()) {
                TextToSpeech textToSpeech;
                Object object = this.f;
                Object object2 = new k0((String)U.get(this.i));
                ((ArrayList)object).add(object2);
                object2 = new c3.d(this.h, this);
                ((c3.d)object2).c((String)U.get(this.i));
                this.g.add(object2);
                object2 = this.getApplicationContext();
                object = new c(this, null);
                h0 = textToSpeech = new TextToSpeech((Context)object2, (TextToSpeech.OnInitListener)object, (String)U.get(this.i));
            }
            return;
        }
    }

    public final void Y() {
        String string = Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id");
        this.z = new b(this, null);
        this.A = new LicenseChecker((Context)this, new ServerManagedPolicy((Context)this, new AESObfuscator(n0, this.getPackageName(), string)), "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApzCqD0VjR3RQYVN1f5hIVDWBBoomRgzjbHqW3g5v59YfVwTkmM4hWXvyHEXBHcE7Wcbl8Tlic9LIH0HStl7KN+Erx4mUlk8jqsPGeDC9r2f2VLKYGKm6lb5Lvjw8aNfS6auzJlFN12/NBMEBPb1wstV2B1gUaDNT/63Zz0arO6XbjFM9WAHpo54BFQoWk/vRK95G88xlWoUX3QGum0AouPMj8vKiYaBGzFjnXMTdRH70bYPY5pPmF710ox3vv/SSiM78BT/Ez1V7rshx3fL9ZjrxbmrO8YYbqtzvGu91+y0viRkLvJozU5dy5zHp147UEaX3rDnyxFBhGngO1ng3hQIDAQAB");
        this.K();
    }

    public final boolean Z(Locale object, Locale object2) {
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

    public final boolean a0() {
        StatusBarNotification[] statusBarNotificationArray = ((NotificationManager)this.getSystemService(NotificationManager.class)).getActiveNotifications();
        int n3 = statusBarNotificationArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (statusBarNotificationArray[i3].getId() != 136549) continue;
            return true;
        }
        return false;
    }

    public final void b0() {
        if (H != null) {
            return;
        }
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
        H = sharedPreferences.getString("auto_mode_language", "");
        if (H.isEmpty()) {
            H = c3.n.e(Locale.getDefault());
        }
        if ((P = sharedPreferences.getString("mixed_mode_latin_language", "")).isEmpty()) {
            P = c3.n.e(Locale.getDefault());
        }
        if ((Q = sharedPreferences.getString("mixed_mode_non_latin_language", "")).isEmpty()) {
            Q = c3.n.e(Locale.getDefault());
        }
        if ((I = sharedPreferences.getString("dual_mode_language", "")).isEmpty()) {
            I = c3.n.e(Locale.getDefault());
        }
        J = sharedPreferences.getInt("number_mode_language", 0);
        L = sharedPreferences.getInt("punc_mode_language", 0);
        N = sharedPreferences.getInt("emoji_mode_language", 0);
    }

    public final void c0() {
        synchronized (this) {
            SharedPreferences sharedPreferences;
            U = sharedPreferences = new ArrayList();
            String string = this.Q(c3.n.e(Locale.getDefault()));
            sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
            int n3 = 0;
            while (true) {
                CharSequence charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("engine_");
                ((StringBuilder)charSequence).append(n3);
                charSequence = sharedPreferences.getString(((StringBuilder)charSequence).toString(), "");
                if (((String)charSequence).isEmpty() || ((String)charSequence).equals("end")) break;
                if (!((String)charSequence).equals(string)) {
                    U.add(charSequence);
                }
                ++n3;
            }
            if (!string.isEmpty() && !string.equals("Disable")) {
                U.add(0, string);
            }
            if (U.isEmpty() && c3.w.a(this.h)) {
                U.add("com.google.android.tts");
            }
            return;
        }
    }

    public final int d0(String object, String object2, String object3) {
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
            object5 = this.U((String)object);
            object4 = this.Q((String)object);
            object7 = object3;
            if (((String)object3).isEmpty()) {
                object7 = this.T((String)object);
            }
            object6 = object7;
            object3 = object4;
            object7 = object5;
        }
        object5 = object7;
        object7 = object3;
        if (((String)object3).isEmpty()) {
            object5 = this.U(H);
            object7 = this.Q(H);
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
                object3 = this.j0((String)object5);
                if (object3 == null) {
                    return -2;
                }
                if (this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3)) && ((Locale)object3).getVariant().equals(object6)) {
                    this.f0((String)object7, (Locale)object3, (String)object6, W);
                    return n3;
                }
                if (!this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3)) && ((Locale)object3).getVariant().equals(object6)) {
                    this.f0((String)object7, (Locale)object3, (String)object6, W);
                    return n3;
                }
                object = new Locale((String)object, (String)object2, (String)object6);
                this.f0(this.o0((Locale)object), (Locale)object, (String)object6, W);
                return n3;
            }
            object3 = this.j0((String)object5);
            if (object3 == null) {
                return -2;
            }
            if (this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3))) {
                this.f0("", (Locale)object3, (String)object6, W);
                return n3;
            }
            if (!this.c.equals(object7) && c3.n.e((Locale)object3).equals(object) && ((String)object2).equals(c3.n.d((Locale)object3))) {
                this.f0((String)object7, (Locale)object3, (String)object6, W);
                return n3;
            }
            object = new Locale((String)object, (String)object2, "");
            this.f0(this.o0((Locale)object), (Locale)object, (String)object6, W);
            return n3;
        }
        object2 = this.j0((String)object5);
        if (object2 == null) {
            return -2;
        }
        if (this.c.equals(object7) && c3.n.e((Locale)object2).equals(object)) {
            this.f0((String)object7, (Locale)object2, (String)object6, W);
            return n3;
        }
        if (!this.c.equals(object7) && c3.n.e((Locale)object2).equals(object)) {
            this.f0((String)object7, (Locale)object2, (String)object6, W);
            this.c = object7;
            return n3;
        }
        object = new Locale((String)object, "", "");
        this.f0(this.o0((Locale)object), (Locale)object, (String)object6, W);
        return n3;
    }

    public final void e0() {
        synchronized (this) {
            c3.n.a.c("AutoTTS", "loadLanguages");
            ArrayList<Object> arrayList = new ArrayList<Object>();
            SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
            int n3 = 0;
            while (true) {
                Object object = new StringBuilder();
                ((StringBuilder)object).append("language_");
                ((StringBuilder)object).append(n3);
                String string = sharedPreferences.getString(((StringBuilder)object).toString(), "");
                if (string.isEmpty()) {
                    object = c3.n.c;
                    synchronized (object) {
                        object.clear();
                        object.addAll(arrayList);
                        AutoTtsService.s0();
                    }
                    this.w = true;
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
                Object object2 = "";
                Object object3 = "";
                String[] stringArray = sharedPreferences.getString(string, "");
                Object object4 = object2;
                object = object3;
                if (!stringArray.isEmpty()) {
                    stringArray = stringArray.split("#");
                    object4 = object2;
                    object = object3;
                    if (stringArray.length >= 2) {
                        object4 = stringArray[0];
                        object = stringArray[1];
                    }
                }
                object2 = new f("", string, n4, n6, n5, (String)object4, (String)object, string2);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(string);
                ((StringBuilder)object3).append("_disabled");
                ((f)object2).i = sharedPreferences.getBoolean(((StringBuilder)object3).toString(), false);
                arrayList.add(object2);
                object3 = c3.n.a;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" - ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append((String)object4);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" ");
                ((StringBuilder)object2).append(string2);
                ((p)object3).c("AutoTTS", ((StringBuilder)object2).toString());
                ++n3;
            }
        }
    }

    public final void f0(String object, Locale locale, String object2, boolean bl) {
        block27: {
            int n3;
            Object object3;
            Object object4;
            Object object5;
            block26: {
                Object object6;
                String string;
                block28: {
                    block29: {
                        int n4;
                        block25: {
                            object5 = c3.n.a;
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append("LoadVoice ");
                            ((StringBuilder)object4).append((String)object);
                            ((StringBuilder)object4).append(" ");
                            ((StringBuilder)object4).append(locale.toString());
                            ((StringBuilder)object4).append(" ");
                            ((StringBuilder)object4).append((String)object2);
                            ((p)object5).c("AutoTTS", ((StringBuilder)object4).toString());
                            if (bl && T != 3) {
                                this.h0((String)object, locale, (String)object2, bl);
                                return;
                            }
                            if (((String)object).isEmpty()) {
                                object = this.c;
                            } else {
                                this.c = object;
                            }
                            string = ((String)object).replace("-", "").replace("_", "");
                            object4 = c3.n.a;
                            object = new StringBuilder();
                            ((StringBuilder)object).append(" current engine: ");
                            ((StringBuilder)object).append(string);
                            ((p)object4).c("AutoTTS", ((StringBuilder)object).toString());
                            for (n4 = 0; n4 < this.f.size(); ++n4) {
                                if (!((k0)this.f.get(n4)).e().equals(string) || ((k0)this.f.get(n4)).f() != 2) continue;
                                c3.n.a.c("AutoTTS", " found!");
                                break block25;
                            }
                            n4 = -1;
                        }
                        if (n4 == -1) break block27;
                        this.d = n4;
                        if (((String)object2).isEmpty() && !((k0)this.f.get((int)n4)).e.isEmpty()) {
                            c3.n.a.c("AutoTTS", "Load voice original");
                            this.g0(string, locale);
                            return;
                        }
                        object = new Locale("zxx");
                        object3 = "";
                        object4 = object;
                        object5 = object3;
                        if (((k0)this.f.get((int)n4)).e.isEmpty()) break block28;
                        object6 = ((k0)this.f.get(n4)).g().getVoice();
                        object4 = object;
                        object5 = object3;
                        if (object6 == null) break block28;
                        object = object6.getLocale();
                        object3 = object6.getName();
                        object4 = c3.n.a;
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append(" last ");
                        ((StringBuilder)object5).append(object);
                        ((StringBuilder)object5).append(" ");
                        ((StringBuilder)object5).append((String)object3);
                        ((p)object4).c("AutoTTS", ((StringBuilder)object5).toString());
                        object4 = object;
                        object5 = object3;
                        if (!c3.n.e((Locale)object).equals(c3.n.e(locale))) break block28;
                        if (c3.n.d((Locale)object).equals(c3.n.d(locale))) break block29;
                        object4 = object;
                        object5 = object3;
                        if (!c3.n.d(locale).equals("")) break block28;
                    }
                    object4 = object;
                    object5 = object3;
                    if (((String)object3).equals(object2)) {
                        c3.n.a.c("AutoTTS", " Do nothing!");
                        return;
                    }
                }
                object3 = g0;
                int n5 = ((ArrayList)object3).size();
                n3 = 0;
                do {
                    object = object2;
                    if (n3 >= n5) break block26;
                    object = ((ArrayList)object3).get(n3);
                    ++n3;
                } while (((String[])(object6 = ((String)(object = (String)object)).split("#"))).length < 2 || !object6[0].equals(string) || !object6[1].equals(locale.toString()));
                object6 = c3.n.a;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("voice: ");
                ((StringBuilder)object3).append((String)object);
                ((p)object6).c("AutoTTS", ((StringBuilder)object3).toString());
                object3 = c3.n.c;
                synchronized (object3) {
                    object6 = object3.iterator();
                    do {
                        object = object2;
                        if (!object6.hasNext()) break block26;
                        object = (f)object6.next();
                    } while (!((f)object).f.equals(string) || !((f)object).g.equals(locale.toString()));
                    object = ((f)object).h;
                }
            }
            object2 = c3.n.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(" variant ");
            ((StringBuilder)object3).append((String)object);
            ((p)object2).c("AutoTTS", ((StringBuilder)object3).toString());
            if (((String)object).equals("*Default") && !this.Z(locale, (Locale)object4)) {
                object5 = c3.n.a;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(locale.toString());
                ((StringBuilder)object2).append(" vs ");
                ((StringBuilder)object2).append(((Locale)object4).toString());
                ((p)object5).c("AutoTTS", ((StringBuilder)object2).toString());
                if (((k0)this.f.get(this.d)).g().setLanguage(locale) >= 0) {
                    ((k0)this.f.get((int)this.d)).f = true;
                    c3.n.a.c("AutoTTS", "Set voice 1");
                } else {
                    this.m0(((k0)this.f.get(this.d)).e());
                }
            } else if (!((String)object).equals("*Default") && !((String)object).equals(object5)) {
                c3.n.a.c("AutoTTS", "Check voice 1");
                object2 = ((k0)this.f.get(this.d)).g().getVoices();
                if (object2 != null) {
                    object5 = object2.iterator();
                    while (object5.hasNext()) {
                        object2 = (Voice)object5.next();
                        if (!object2.getName().equalsIgnoreCase((String)object)) continue;
                        n3 = ((k0)this.f.get(this.d)).g().setVoice((Voice)object2);
                        if (n3 >= 0) {
                            ((k0)this.f.get((int)this.d)).f = true;
                            object5 = c3.n.a;
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append("Set voice 2: ");
                            ((StringBuilder)object4).append(object2.getName());
                            ((StringBuilder)object4).append(" res=");
                            ((StringBuilder)object4).append(n3);
                            ((p)object5).c("AutoTTS", ((StringBuilder)object4).toString());
                        } else {
                            this.m0(((k0)this.f.get(this.d)).e());
                        }
                        break;
                    }
                } else if (!this.Z(locale, (Locale)object4)) {
                    n3 = ((k0)this.f.get(this.d)).g().setLanguage(locale);
                    if (n3 >= 0) {
                        ((k0)this.f.get((int)this.d)).f = true;
                        object2 = c3.n.a;
                        object4 = new StringBuilder();
                        ((StringBuilder)object4).append("Set voice 3: ");
                        ((StringBuilder)object4).append(locale.toString());
                        ((StringBuilder)object4).append(" res = ");
                        ((StringBuilder)object4).append(n3);
                        ((StringBuilder)object4).append(" ");
                        ((StringBuilder)object4).append(((k0)this.f.get(this.d)).g().toString());
                        ((p)object2).c("AutoTTS", ((StringBuilder)object4).toString());
                    } else {
                        this.m0(((k0)this.f.get(this.d)).e());
                    }
                }
            }
            ((k0)this.f.get((int)n4)).d = locale;
            ((k0)this.f.get((int)n4)).e = object;
            return;
        }
        c3.n.a.d("AutoTTS", "TTS is not ready");
        this.d = -1;
    }

    public final void g0(String object, Locale locale) {
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
            this.m0(((k0)this.f.get(this.d)).e());
            return;
        }
        this.d = -1;
    }

    public final void h0(String object, Locale object2, String object3, boolean bl) {
        int n3;
        Iterator iterator;
        Object object4;
        block20: {
            object4 = c3.n.a;
            iterator = new StringBuilder();
            ((StringBuilder)((Object)iterator)).append("loadVoice_Secondary ");
            ((StringBuilder)((Object)iterator)).append((String)object);
            ((StringBuilder)((Object)iterator)).append(" ");
            ((StringBuilder)((Object)iterator)).append(((Locale)object2).toString());
            ((StringBuilder)((Object)iterator)).append(" ");
            ((StringBuilder)((Object)iterator)).append((String)object3);
            ((p)object4).c("AutoTTS", ((StringBuilder)((Object)iterator)).toString());
            iterator = c3.n.a;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Current Engine ");
            ((StringBuilder)object4).append(this.c);
            ((p)((Object)iterator)).c("AutoTTS", ((StringBuilder)object4).toString());
            if (((String)object).isEmpty()) {
                object = this.c;
            } else {
                this.c = object;
            }
            object = ((String)object).replace("-", "").replace("_", "");
            iterator = c3.n.a;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append(" current engine: ");
            ((StringBuilder)object4).append((String)object);
            ((p)((Object)iterator)).c("AutoTTS", ((StringBuilder)object4).toString());
            for (n3 = 0; n3 < this.f.size(); ++n3) {
                if (!((k0)this.f.get(n3)).e().equals(object) || ((k0)this.f.get(n3)).f() != 2) continue;
                c3.n.a.c("AutoTTS", " found!");
                break block20;
            }
            n3 = -1;
        }
        if (n3 != -1) {
            int n4 = this.d;
            this.d = n3;
            if (!bl || !((k0)this.f.get((int)this.d)).f) {
                Object object5;
                Object object6;
                block22: {
                    Object object7;
                    object4 = new Locale("zxx");
                    iterator = "";
                    object6 = ((k0)this.f.get(n3)).g().getVoice();
                    if (object6 != null) {
                        object4 = object6.getLocale();
                        iterator = object6.getName();
                    }
                    if (n4 == this.d && object6 != null) {
                        object7 = c3.n.a;
                        object5 = new StringBuilder();
                        ((StringBuilder)object5).append(" Engine Variant ");
                        ((StringBuilder)object5).append((String)((Object)iterator));
                        ((p)object7).c("AutoTTS", ((StringBuilder)object5).toString());
                        object5 = c3.n.a;
                        object7 = new StringBuilder();
                        ((StringBuilder)object7).append(" Engine Locale ");
                        ((StringBuilder)object7).append(((Locale)object4).toString());
                        ((p)object5).c("AutoTTS", ((StringBuilder)object7).toString());
                        if (this.Z((Locale)object2, (Locale)object4) && (((String)((Object)iterator)).equals(object3) || ((String)object3).equals("*Default") || ((String)object3).isEmpty())) {
                            c3.n.a.c("AutoTTS", " *0 Do nothing");
                            return;
                        }
                    }
                    object5 = c3.n.a;
                    object7 = new StringBuilder();
                    ((StringBuilder)object7).append("Searching ");
                    ((StringBuilder)object7).append((String)object);
                    ((StringBuilder)object7).append(" ");
                    ((StringBuilder)object7).append(((Locale)object2).toString());
                    ((p)object5).c("AutoTTS", ((StringBuilder)object7).toString());
                    object5 = g0;
                    n4 = ((ArrayList)object5).size();
                    for (n3 = 0; n3 < n4; ++n3) {
                        object7 = ((ArrayList)object5).get(n3);
                        Object object8 = (String)object7;
                        p p3 = c3.n.a;
                        object7 = new StringBuilder();
                        ((StringBuilder)object7).append(" *");
                        ((StringBuilder)object7).append((String)object8);
                        p3.c("AutoTTS", ((StringBuilder)object7).toString());
                        object7 = ((String)object8).split("#");
                        if (((String[])object7).length < 2 || !object7[0].equals(object)) continue;
                        object5 = c3.n.c;
                        synchronized (object5) {
                            block21: {
                                object8 = object5.iterator();
                                while (object8.hasNext()) {
                                    f f3 = (f)object8.next();
                                    p3 = c3.n.a;
                                    object7 = new StringBuilder();
                                    ((StringBuilder)object7).append("  -");
                                    ((StringBuilder)object7).append(f3.f);
                                    ((StringBuilder)object7).append(" ");
                                    ((StringBuilder)object7).append(f3.g);
                                    ((StringBuilder)object7).append(" ");
                                    ((StringBuilder)object7).append(f3.h);
                                    p3.c("AutoTTS", ((StringBuilder)object7).toString());
                                    if (!f3.f.equals(object) || !this.Z((Locale)object2, this.j0(f3.g)) || f3.h.isEmpty()) continue;
                                    object = f3.h;
                                    break block21;
                                }
                                object = object3;
                            }
                            break block22;
                        }
                    }
                    object = object3;
                }
                object3 = c3.n.a;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append(" Variant ");
                ((StringBuilder)object5).append((String)object);
                ((p)object3).c("AutoTTS", ((StringBuilder)object5).toString());
                object5 = c3.n.a;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(" Locale ");
                ((StringBuilder)object3).append(((Locale)object2).toString());
                ((p)object5).c("AutoTTS", ((StringBuilder)object3).toString());
                if ((((String)object).equals("*Default") || ((String)object).isEmpty()) && !this.Z((Locale)object2, (Locale)object4)) {
                    c3.n.a.c("AutoTTS", " *1");
                    object = c3.n.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(((Locale)object2).toString());
                    ((StringBuilder)object3).append(" vs ");
                    ((StringBuilder)object3).append(((Locale)object4).toString());
                    ((p)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    if (((k0)this.f.get(this.d)).g().setLanguage((Locale)object2) >= 0) {
                        ((k0)this.f.get((int)this.d)).f = true;
                        ((k0)this.f.get((int)this.d)).d = object2;
                        ((k0)this.f.get((int)this.d)).e = ((Locale)object2).getVariant();
                        return;
                    }
                    this.m0(((k0)this.f.get(this.d)).e());
                    return;
                }
                c3.n.a.c("AutoTTS", " *2");
                if (object6 != null) {
                    object3 = c3.n.a;
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append(" Engine Variant ");
                    ((StringBuilder)object6).append((String)((Object)iterator));
                    ((p)object3).c("AutoTTS", ((StringBuilder)object6).toString());
                    object6 = c3.n.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append(" Engine Locale ");
                    ((StringBuilder)object3).append(((Locale)object4).toString());
                    ((p)object6).c("AutoTTS", ((StringBuilder)object3).toString());
                    if (this.Z((Locale)object2, (Locale)object4) && (((String)((Object)iterator)).equals(object) || ((String)object).equals("*Default") || ((String)object).isEmpty())) {
                        c3.n.a.c("AutoTTS", " *2.1 Do nothing");
                        return;
                    }
                }
                if ((object3 = ((k0)this.f.get(this.d)).g().getVoices()) != null) {
                    iterator = object3.iterator();
                    while (iterator.hasNext()) {
                        object3 = (Voice)iterator.next();
                        if (!object3.getName().equalsIgnoreCase((String)object)) continue;
                        if (((k0)this.f.get(this.d)).g().setVoice((Voice)object3) >= 0) {
                            object2 = c3.n.a;
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append("Set voice 2: ");
                            ((StringBuilder)object4).append(object3.toString());
                            ((p)object2).c("AutoTTS", ((StringBuilder)object4).toString());
                            ((k0)this.f.get((int)this.d)).d = object3.getLocale();
                            ((k0)this.f.get((int)this.d)).e = object;
                            ((k0)this.f.get((int)this.d)).f = true;
                            return;
                        }
                        this.m0(((k0)this.f.get(this.d)).e());
                        break;
                    }
                }
                if (!this.Z((Locale)object2, (Locale)object4)) {
                    if (((k0)this.f.get(this.d)).g().setLanguage((Locale)object2) >= 0) {
                        ((k0)this.f.get((int)this.d)).d = object2;
                        ((k0)this.f.get((int)this.d)).e = ((Locale)object2).getVariant();
                        ((k0)this.f.get((int)this.d)).f = true;
                        c3.n.a.c("AutoTTS", "Set voice 3");
                        return;
                    }
                    this.m0(((k0)this.f.get(this.d)).e());
                }
            }
            return;
        }
        c3.n.a.d("AutoTTS", "TTS is not ready");
        this.d = -1;
    }

    public final void i0() {
        synchronized (this) {
            boolean bl = g0.isEmpty();
            if (!bl) {
                return;
            }
            c3.n.a.c("AutoTTS", "LoadVoices");
            SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
            int n3 = 0;
            while (true) {
                Object object = new StringBuilder();
                ((StringBuilder)object).append("voice_");
                ((StringBuilder)object).append(n3);
                String string = sharedPreferences.getString(((StringBuilder)object).toString(), "");
                object = c3.n.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" -");
                stringBuilder.append(string);
                ((p)object).c("AutoTTS", stringBuilder.toString());
                if (string.isEmpty()) {
                    W = sharedPreferences.getBoolean("dedicated_engines", false);
                    return;
                }
                g0.add(string);
                ++n3;
            }
        }
    }

    public final Locale j0(String object) {
        int n3 = ((String[])(object = object.split("_"))).length;
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

    public final void k0(SynthesisCallback synthesisCallback) {
        synthesisCallback.start(16000, 2, 1);
        while (!this.p.get() && this.t0(synthesisCallback)) {
            Object object = this.o;
            synchronized (object) {
                this.o.wait(100L);
            }
        }
    }

    public void l0() {
        this.r = (AudioManager)this.getSystemService("audio");
        Object object = new AudioAttributes.Builder().setUsage(11);
        boolean bl = true;
        object = object.setContentType(1).build();
        object = new AudioFocusRequest.Builder(1).setAudioAttributes((AudioAttributes)object).setOnAudioFocusChangeListener((AudioManager.OnAudioFocusChangeListener)new c3.b()).build();
        this.s = object;
        int n3 = this.r.requestAudioFocus((AudioFocusRequest)object);
        p p3 = c3.n.a;
        object = new StringBuilder();
        ((StringBuilder)object).append("Audio focus request: ");
        if (n3 != 1) {
            bl = false;
        }
        ((StringBuilder)object).append(bl);
        p3.c("TTS", ((StringBuilder)object).toString());
    }

    public void m0(String object) {
        synchronized (this) {
            int n3;
            StringBuilder stringBuilder;
            Object object2;
            block6: {
                object2 = c3.n.a;
                stringBuilder = new StringBuilder();
                stringBuilder.append("restoreTts ");
                stringBuilder.append((String)object);
                ((p)object2).c("AutoTTS", stringBuilder.toString());
                if (this.v != -1) {
                    c3.n.a.c("AutoTTS", " -Restoring in progress...");
                    return;
                }
                for (n3 = 0; n3 < this.f.size(); ++n3) {
                    object2 = c3.n.a;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(" -");
                    stringBuilder.append(((k0)this.f.get(n3)).e());
                    ((p)object2).c("AutoTTS", stringBuilder.toString());
                    if (!((k0)this.f.get(n3)).e().equalsIgnoreCase((String)object)) {
                        continue;
                    }
                    break block6;
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
            this.v = n3;
            object = c3.n.a;
            stringBuilder = new StringBuilder();
            stringBuilder.append("restoreTts ");
            stringBuilder.append(((k0)this.f.get(this.v)).e());
            ((p)object).c("AutoTTS", stringBuilder.toString());
            ((k0)this.f.get(this.v)).m();
            ((k0)this.f.get(this.v)).l();
            stringBuilder = this.getApplicationContext();
            object2 = new d(this, null);
            object = new TextToSpeech((Context)stringBuilder, (TextToSpeech.OnInitListener)object2, ((k0)this.f.get(this.v)).e());
            h0 = object;
            return;
        }
    }

    public final void n0(int n3, String string) {
        SharedPreferences.Editor editor = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0).edit();
        editor.putInt("license_status", n3);
        editor.putString("license_text", string);
        editor.commit();
    }

    public final String o0(Locale locale) {
        Object object;
        Object object2;
        int n3;
        for (n3 = 0; n3 < g0.size(); ++n3) {
            object2 = ((String)g0.get(n3)).split("#");
            if (((String[])object2).length != 2 && ((String[])object2).length != 3) continue;
            object = this.j0(object2[1]);
            if (object == null) {
                return "";
            }
            if (!c3.n.e(locale).equals(c3.n.e((Locale)object)) || !c3.n.d(locale).equals(c3.n.d((Locale)object)) || !locale.getVariant().equals(((Locale)object).getVariant())) continue;
            return object2[0];
        }
        for (n3 = 0; n3 < g0.size(); ++n3) {
            object2 = ((String)g0.get(n3)).split("#");
            if (((String[])object2).length != 2 && ((String[])object2).length != 3) continue;
            object = this.j0(object2[1]);
            if (object == null) {
                return "";
            }
            if (!c3.n.e(locale).equals(c3.n.e((Locale)object)) || !c3.n.d(locale).equals(c3.n.d((Locale)object))) continue;
            return object2[0];
        }
        for (n3 = 0; n3 < g0.size(); ++n3) {
            object = ((String)g0.get(n3)).split("#");
            if (((String[])object).length != 2 && ((String[])object).length != 3) continue;
            object2 = this.j0(object[1]);
            if (object2 == null) {
                return "";
            }
            if (!c3.n.e(locale).equals(c3.n.e((Locale)object2))) continue;
            return object[0];
        }
        return "";
    }

    public void onCreate() {
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
        if (a0) {
            this.p0();
        }
        this.l0();
        j0 = !c3.l0.d() ? c3.l0.b(this.h) : 0;
        this.c0();
        this.i0();
        this.e0();
        clsCLD2.i(c3.n.f);
        this.b0();
        c3.n.o(this.getApplicationContext());
        c3.n.q(this.getApplicationContext());
        this.Y();
        this.n = true;
        this.X();
    }

    public void onDestroy() {
        c3.n.a.c("AutoTTS", "onDestroy");
        this.stopForeground(1);
        this.r.abandonAudioFocusRequest(this.s);
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= this.f.size()) break;
            if (((k0)this.f.get(n4)).f() == 2) {
                ((k0)this.f.get(n4)).l();
            }
            ++n4;
        }
        for (int i3 = n3; i3 < this.g.size(); ++i3) {
            ((c3.d)this.g.get(i3)).e();
        }
        LicenseChecker licenseChecker = this.A;
        if (licenseChecker != null) {
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
        Object object = new StringBuilder();
        ((StringBuilder)object).append("onIsValidVoiceName ");
        ((StringBuilder)object).append((String)charSequence);
        p3.c("AutoTTS", ((StringBuilder)object).toString());
        int n3 = c3.n.i(null, true).contains(charSequence) ? 0 : -1;
        object = c3.n.a;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" -res ");
        ((StringBuilder)charSequence).append(n3);
        ((p)object).c("AutoTTS", ((StringBuilder)charSequence).toString());
        return n3;
    }

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
            int n3 = this.d0(string, string2, string3);
            return n3;
        }
    }

    public int onLoadVoice(String object) {
        p p3 = c3.n.a;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onLoadVoice ");
        stringBuilder.append((String)object);
        p3.c("AutoTTS", stringBuilder.toString());
        m0 = object;
        object = this.j0((String)object);
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
        this.q0(Boolean.TRUE);
    }

    public void onSynthesizeText(SynthesisRequest object, SynthesisCallback synthesisCallback) {
        int n3;
        int n4;
        int n5;
        Object object2;
        float f3;
        Object object3;
        Object object4;
        block109: {
            Object object5;
            Object object6;
            block131: {
                block132: {
                    block128: {
                        Object object7;
                        block122: {
                            block123: {
                                Object object8;
                                block110: {
                                    block111: {
                                        block106: {
                                            block107: {
                                                c3.n.a.c("AutoTTS", "\n-------------------------------\nonSynthesizeText");
                                                this.P();
                                                if (a0 && !this.a0()) {
                                                    this.p0();
                                                } else if (!a0 && this.a0()) {
                                                    this.stopForeground(1);
                                                }
                                                object4 = this.o;
                                                synchronized (object4) {
                                                    this.p.set(false);
                                                    this.o.notifyAll();
                                                }
                                                object4 = this.o;
                                                synchronized (object4) {
                                                    this.q.set(false);
                                                    this.o.notifyAll();
                                                }
                                                object7 = object.getCharSequenceText();
                                                object4 = object7.toString();
                                                object3 = object.getLanguage();
                                                this.l = object.getSpeechRate();
                                                this.m = object.getPitch();
                                                this.j = object.getParams();
                                                this.k = f3 = this.j.getFloat("volume");
                                                if ((double)f3 == 0.0) {
                                                    this.k = 1.0f;
                                                }
                                                k0 = this.j.getString("utteranceId");
                                                if (((String)(object4 = ((String)object4).trim())).isEmpty()) {
                                                    c3.n.a.c("AutoTTS", "Speak text is empty");
                                                    this.q0(Boolean.FALSE);
                                                    this.N(synthesisCallback, 1);
                                                    return;
                                                }
                                                object6 = c3.n.a;
                                                object2 = new StringBuilder();
                                                ((StringBuilder)object2).append("Speak: ");
                                                ((StringBuilder)object2).append((String)object4);
                                                ((StringBuilder)object2).append(" id: ");
                                                ((StringBuilder)object2).append(k0);
                                                ((p)object6).c("AutoTTS", ((StringBuilder)object2).toString());
                                                if (o0 != 1) {
                                                    i0 = n5 = i0 + 1;
                                                    if (n5 > 100000) {
                                                        i0 = 0;
                                                    }
                                                    if (i0 % 500 == 0) {
                                                        this.K();
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
                                                        object3 = c3.n.e(this.j0((String)object6));
                                                        object8 = c3.n.a;
                                                        StringBuilder stringBuilder = new StringBuilder();
                                                        stringBuilder.append("FIXED: ");
                                                        stringBuilder.append((String)object5);
                                                        stringBuilder.append(" ");
                                                        stringBuilder.append((String)object6);
                                                        stringBuilder.append(" ");
                                                        stringBuilder.append((String)object2);
                                                        ((p)object8).c("AutoTTS", stringBuilder.toString());
                                                    }
                                                    n5 = 1;
                                                } else {
                                                    n5 = 0;
                                                }
                                                if ((!((String)object3).equals("zxx") || T == 1) && T != 2 && T != 3 || n5 != 0) break block106;
                                                c3.n.a.c("AutoTTS", "Auto mode || Google mode");
                                                object2 = R;
                                                synchronized (object2) {
                                                    block108: {
                                                        ((ArrayList)object2).clear();
                                                        ((ArrayList)object2).addAll(c3.e0.g((CharSequence)object7));
                                                        for (n5 = 0; n5 < ((ArrayList)(object6 = R)).size() && !this.q.get(); ++n5) {
                                                            object3 = object = ((e0)((ArrayList)object6).get(n5)).b();
                                                            if (((String)object).equalsIgnoreCase("unknown")) {
                                                                object = object3 = clsCLD2.d(((e0)((ArrayList)object6).get(n5)).c(), o0, j0, this.h);
                                                                if (((String)object3).equalsIgnoreCase("unknown")) {
                                                                    object = clsCLD2.f((String)((e0)((ArrayList)object6).get((int)n5)).c(), (int)AutoTtsService.o0, (int)AutoTtsService.j0, (Object)this.h).a;
                                                                }
                                                                object5 = c3.n.a;
                                                                object3 = new StringBuilder();
                                                                ((StringBuilder)object3).append("Cld2: ");
                                                                ((StringBuilder)object3).append((String)object);
                                                                ((StringBuilder)object3).append(" '");
                                                                ((StringBuilder)object3).append(((e0)((ArrayList)object6).get(n5)).c());
                                                                ((StringBuilder)object3).append("'");
                                                                ((p)object5).c("AutoTTS", ((StringBuilder)object3).toString());
                                                                object3 = object;
                                                                if (((String)object).length() > 2) {
                                                                    object3 = ((String)object).substring(0, 2);
                                                                }
                                                            }
                                                            object3 = object = c3.e.c((String)object3);
                                                            if (object == null) {
                                                                object3 = H;
                                                            }
                                                            if (((String)(object = this.Q((String)object3))).isEmpty() || ((String)object).equals("Disable")) {
                                                                object3 = H;
                                                            }
                                                            ((e0)((ArrayList)object6).get(n5)).e((String)object3);
                                                            if (c3.l0.c(o0, j0, -1, -1) % 100 != 1 || !((String)object3).equals("eng")) continue;
                                                            object = (e0)((ArrayList)object6).get(n5);
                                                            object5 = new StringBuilder();
                                                            ((StringBuilder)object5).append(((e0)((ArrayList)object6).get(n5)).c());
                                                            ((StringBuilder)object5).append(" ");
                                                            ((StringBuilder)object5).append(c3.l0.e(".detceted esnecil oN ."));
                                                            ((e0)object).f(((StringBuilder)object5).toString());
                                                        }
                                                        if (((ArrayList)object6).isEmpty()) break block107;
                                                        object = ((e0)((ArrayList)object6).get(0)).c();
                                                        n5 = this.onLoadLanguage(((e0)((ArrayList)object6).get(0)).b(), "", "");
                                                        object5 = c3.n.a;
                                                        object4 = new StringBuilder();
                                                        ((StringBuilder)object4).append("load ");
                                                        ((StringBuilder)object4).append(n5);
                                                        ((p)object5).c("AutoTTS", ((StringBuilder)object4).toString());
                                                        if (n5 == -2) break block108;
                                                        object4 = object;
                                                        if (n5 != -1) break block107;
                                                    }
                                                    object3 = c3.n.a;
                                                    object4 = new StringBuilder();
                                                    ((StringBuilder)object4).append("Languge is not supported: ");
                                                    ((StringBuilder)object4).append(((e0)((ArrayList)object6).get(0)).b());
                                                    ((StringBuilder)object4).append(", text: ");
                                                    ((StringBuilder)object4).append((String)object);
                                                    ((p)object3).d("AutoTTS", ((StringBuilder)object4).toString());
                                                    this.N(synthesisCallback, 2);
                                                    return;
                                                }
                                            }
                                            object2 = object4;
                                            break block109;
                                        }
                                        if (T != 1 || n5 != 0) break block110;
                                        c3.n.a.c("AutoTTS", "Dual mode");
                                        object = object4;
                                        if (c3.l0.c(o0, j0, -1, -1) % 100 == 1) {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append((String)object4);
                                            ((StringBuilder)object).append(c3.l0.e(".detceted esnecil oN ."));
                                            object = ((StringBuilder)object).toString();
                                        }
                                        object2 = R;
                                        synchronized (object2) {
                                            block121: {
                                                block112: {
                                                    block120: {
                                                        block113: {
                                                            block119: {
                                                                block114: {
                                                                    block118: {
                                                                        block115: {
                                                                            block117: {
                                                                                block116: {
                                                                                    ((ArrayList)object2).clear();
                                                                                    ((ArrayList)object2).addAll(c3.d0.t((String)object, J, L, N, o0, j0));
                                                                                    if (((ArrayList)object2).isEmpty()) break block111;
                                                                                    object4 = ((e0)((ArrayList)object2).get(0)).c();
                                                                                    n5 = ((e0)((ArrayList)object2).get(0)).a();
                                                                                    if (n5 == 1) break block112;
                                                                                    if (n5 == 2) break block113;
                                                                                    if (n5 == 3) break block114;
                                                                                    if (n5 == 4) break block115;
                                                                                    if (n5 == 5) break block116;
                                                                                    object = object4;
                                                                                    break block111;
                                                                                }
                                                                                object6 = c3.n.a;
                                                                                object = new StringBuilder();
                                                                                ((StringBuilder)object).append("language: ");
                                                                                ((StringBuilder)object).append(O);
                                                                                ((p)object6).c("AutoTTS", ((StringBuilder)object).toString());
                                                                                n5 = this.onLoadLanguage(O, "", "");
                                                                                if (n5 == -2) break block117;
                                                                                object = object4;
                                                                                if (n5 != -1) break block111;
                                                                            }
                                                                            object3 = c3.n.a;
                                                                            object = new StringBuilder();
                                                                            ((StringBuilder)object).append("Language is not supported: ");
                                                                            ((StringBuilder)object).append(O);
                                                                            ((StringBuilder)object).append(", text: ");
                                                                            ((StringBuilder)object).append((String)object4);
                                                                            ((p)object3).d("AutoTTS", ((StringBuilder)object).toString());
                                                                            this.N(synthesisCallback, 5);
                                                                            return;
                                                                        }
                                                                        object = c3.n.a;
                                                                        object6 = new StringBuilder();
                                                                        ((StringBuilder)object6).append("language: ");
                                                                        ((StringBuilder)object6).append(M);
                                                                        ((p)object).c("AutoTTS", ((StringBuilder)object6).toString());
                                                                        n5 = this.onLoadLanguage(M, "", "");
                                                                        if (n5 == -2) break block118;
                                                                        object = object4;
                                                                        if (n5 != -1) break block111;
                                                                    }
                                                                    object3 = c3.n.a;
                                                                    object = new StringBuilder();
                                                                    ((StringBuilder)object).append("Language is not supported: ");
                                                                    ((StringBuilder)object).append(M);
                                                                    ((StringBuilder)object).append(", text: ");
                                                                    ((StringBuilder)object).append((String)object4);
                                                                    ((p)object3).d("AutoTTS", ((StringBuilder)object).toString());
                                                                    this.N(synthesisCallback, 5);
                                                                    return;
                                                                }
                                                                object6 = c3.n.a;
                                                                object = new StringBuilder();
                                                                ((StringBuilder)object).append("language: ");
                                                                ((StringBuilder)object).append(K);
                                                                ((p)object6).c("AutoTTS", ((StringBuilder)object).toString());
                                                                n5 = this.onLoadLanguage(K, "", "");
                                                                if (n5 == -2) break block119;
                                                                object = object4;
                                                                if (n5 != -1) break block111;
                                                            }
                                                            object = c3.n.a;
                                                            object3 = new StringBuilder();
                                                            ((StringBuilder)object3).append("Language is not supported: ");
                                                            ((StringBuilder)object3).append(K);
                                                            ((StringBuilder)object3).append(", text: ");
                                                            ((StringBuilder)object3).append((String)object4);
                                                            ((p)object).d("AutoTTS", ((StringBuilder)object3).toString());
                                                            this.N(synthesisCallback, 5);
                                                            return;
                                                        }
                                                        object6 = c3.n.a;
                                                        object = new StringBuilder();
                                                        ((StringBuilder)object).append("language: ");
                                                        ((StringBuilder)object).append(I);
                                                        ((p)object6).c("AutoTTS", ((StringBuilder)object).toString());
                                                        n5 = this.onLoadLanguage(I, "", "");
                                                        if (n5 == -2) break block120;
                                                        object = object4;
                                                        if (n5 != -1) break block111;
                                                    }
                                                    object = c3.n.a;
                                                    object3 = new StringBuilder();
                                                    ((StringBuilder)object3).append("Language is not supported: ");
                                                    ((StringBuilder)object3).append(I);
                                                    ((StringBuilder)object3).append(", text: ");
                                                    ((StringBuilder)object3).append((String)object4);
                                                    ((p)object).d("AutoTTS", ((StringBuilder)object3).toString());
                                                    this.N(synthesisCallback, 5);
                                                    return;
                                                }
                                                c3.n.a.c("AutoTTS", "language: eng");
                                                n5 = this.onLoadLanguage("eng", "", "");
                                                if (n5 == -2) break block121;
                                                object = object4;
                                                if (n5 != -1) break block111;
                                            }
                                            object3 = c3.n.a;
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("Language is not supported: eng, text: ");
                                            ((StringBuilder)object).append((String)object4);
                                            ((p)object3).d("AutoTTS", ((StringBuilder)object).toString());
                                            this.N(synthesisCallback, 4);
                                            return;
                                        }
                                    }
                                    object2 = object;
                                    break block109;
                                }
                                if (T != 4 || n5 != 0) break block122;
                                c3.n.a.c("AutoTTS", "Mixed mode");
                                object6 = R;
                                synchronized (object6) {
                                    block127: {
                                        block126: {
                                            block125: {
                                                block124: {
                                                    ((ArrayList)object6).clear();
                                                    object2 = c3.e0.g((CharSequence)object7);
                                                    object = object3;
                                                    for (n5 = 0; n5 < ((ArrayList)object2).size() && !this.q.get(); ++n5) {
                                                        object3 = ((e0)((ArrayList)object2).get(n5)).b();
                                                        if (!((String)object3).equalsIgnoreCase("unknown") && !((String)object3).isEmpty()) {
                                                            object5 = this.Q((String)object3);
                                                            if (((String)object5).isEmpty() || ((String)object5).equals("Disable")) {
                                                                object3 = H;
                                                            }
                                                            ((e0)((ArrayList)object2).get(n5)).e((String)object3);
                                                            R.add((e0)((ArrayList)object2).get(n5));
                                                            object3 = object;
                                                        } else {
                                                            object5 = c3.d0.t(((e0)((ArrayList)object2).get(n5)).c(), J, L, N, o0, j0);
                                                            n4 = 0;
                                                            while (true) {
                                                                object3 = object;
                                                                if (n4 >= ((ArrayList)object5).size()) break;
                                                                n3 = ((e0)((ArrayList)object5).get(n4)).a();
                                                                if (n3 != 3) {
                                                                    if (n3 != 4) {
                                                                        if (n3 != 5) {
                                                                            object7 = clsCLD2.e(((e0)((ArrayList)object5).get(n4)).c(), o0, j0, this.h);
                                                                            for (n3 = 0; n3 < object7.size(); ++n3) {
                                                                                object = object3 = c3.e.c(((clsCLD2.a)object7.get((int)n3)).a);
                                                                                if (object3 == null) {
                                                                                    object = ((clsCLD2.a)object7.get((int)n3)).b ? P : Q;
                                                                                }
                                                                                if (((String)(object3 = this.Q((String)object))).isEmpty() || ((String)object3).equals("Disable")) {
                                                                                    object = ((clsCLD2.a)object7.get((int)n3)).b ? P : Q;
                                                                                }
                                                                                object8 = R;
                                                                                object3 = new e0(((clsCLD2.a)object7.get((int)n3)).c, (String)object);
                                                                                ((ArrayList)object8).add(object3);
                                                                            }
                                                                        } else {
                                                                            object7 = R;
                                                                            object3 = new e0(((e0)((ArrayList)object5).get(n4)).c(), O);
                                                                            ((ArrayList)object7).add(object3);
                                                                        }
                                                                    } else {
                                                                        object3 = R;
                                                                        object7 = new e0(((e0)((ArrayList)object5).get(n4)).c(), M);
                                                                        ((ArrayList)object3).add(object7);
                                                                    }
                                                                } else {
                                                                    object7 = R;
                                                                    object3 = new e0(((e0)((ArrayList)object5).get(n4)).c(), K);
                                                                    ((ArrayList)object7).add(object3);
                                                                }
                                                                ++n4;
                                                            }
                                                        }
                                                        object = object3;
                                                    }
                                                    object5 = R;
                                                    object3 = object;
                                                    if (((ArrayList)object5).isEmpty()) break block123;
                                                    object3 = ((e0)((ArrayList)object5).get(0)).c();
                                                    object2 = ((e0)((ArrayList)object5).get(0)).b();
                                                    if (((String)object2).isEmpty()) break block124;
                                                    object4 = object2;
                                                    if (!((String)object2).equals("unknown")) break block125;
                                                }
                                                object2 = clsCLD2.d((String)object3, o0, j0, this.h);
                                                object7 = c3.n.a;
                                                object4 = new StringBuilder();
                                                ((StringBuilder)object4).append("language: ");
                                                ((StringBuilder)object4).append((String)object2);
                                                ((StringBuilder)object4).append(" '");
                                                ((StringBuilder)object4).append((String)object3);
                                                ((StringBuilder)object4).append("'");
                                                ((p)object7).c("AutoTTS", ((StringBuilder)object4).toString());
                                                object4 = object2;
                                                if (((String)object2).length() > 2) {
                                                    object4 = ((String)object2).substring(0, 2);
                                                }
                                                object4 = object2 = c3.e.c((String)object4);
                                                if (object2 == null) {
                                                    object4 = object2;
                                                    if (!((ArrayList)object5).isEmpty()) {
                                                        n5 = ((e0)((ArrayList)object5).get(0)).a();
                                                        if (n5 != 1) {
                                                            if (n5 != 2) {
                                                                if (n5 != 3) {
                                                                    if (n5 != 4) {
                                                                        if (n5 == 5) {
                                                                            object = O;
                                                                        }
                                                                    } else {
                                                                        object = M;
                                                                    }
                                                                } else {
                                                                    object = K;
                                                                }
                                                            } else {
                                                                object = Q;
                                                            }
                                                        } else {
                                                            object = P;
                                                        }
                                                        object4 = object;
                                                    }
                                                }
                                            }
                                            object = c3.n.a;
                                            object2 = new StringBuilder();
                                            ((StringBuilder)object2).append("language: ");
                                            ((StringBuilder)object2).append((String)object4);
                                            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
                                            object2 = this.Q((String)object4);
                                            object = c3.n.a;
                                            object7 = new StringBuilder();
                                            ((StringBuilder)object7).append("engine: ");
                                            ((StringBuilder)object7).append((String)object2);
                                            ((p)object).c("AutoTTS", ((StringBuilder)object7).toString());
                                            if (((String)object2).isEmpty()) break block126;
                                            object = object4;
                                            if (!((String)object2).equals("Disable")) break block127;
                                        }
                                        object = object4;
                                        if (!((ArrayList)object5).isEmpty()) {
                                            n5 = ((e0)((ArrayList)object5).get(0)).a();
                                            object = n5 != 1 ? (n5 != 2 ? (n5 != 3 ? (n5 != 4 ? (n5 != 5 ? object4 : O) : M) : K) : Q) : P;
                                        }
                                    }
                                    if ((n5 = this.onLoadLanguage((String)object, "", "")) != -2 && n5 != -1) {
                                        object4 = object3;
                                        object3 = object;
                                    } else {
                                        object4 = c3.n.a;
                                        object2 = new StringBuilder();
                                        ((StringBuilder)object2).append("Languge is not supported: ");
                                        ((StringBuilder)object2).append((String)object);
                                        ((StringBuilder)object2).append(", text: ");
                                        ((StringBuilder)object2).append((String)object3);
                                        ((p)object4).d("AutoTTS", ((StringBuilder)object2).toString());
                                        this.N(synthesisCallback, 7);
                                        return;
                                    }
                                }
                            }
                            object2 = object4;
                            break block109;
                        }
                        if (T != 5 || n5 != 0) break block128;
                        c3.n.a.c("AutoTTS", "Multilingual mode");
                        object6 = R;
                        synchronized (object6) {
                            block129: {
                                block130: {
                                    ((ArrayList)object6).clear();
                                    object3 = c3.e0.g((CharSequence)object7);
                                    for (n5 = 0; n5 < ((ArrayList)object3).size() && !this.q.get(); ++n5) {
                                        object = ((e0)((ArrayList)object3).get(n5)).b();
                                        if (!(((String)object).equalsIgnoreCase("unknown") || ((String)object).isEmpty() || ((String)(object = this.Q((String)object))).isEmpty() || ((String)object).equals("Disable"))) {
                                            R.add((e0)((ArrayList)object3).get(n5));
                                            continue;
                                        }
                                        object2 = c3.d0.t(((e0)((ArrayList)object3).get(n5)).c(), J, L, N, o0, j0);
                                        for (n4 = 0; n4 < ((ArrayList)object2).size(); ++n4) {
                                            n3 = ((e0)((ArrayList)object2).get(n4)).a();
                                            if (n3 != 3) {
                                                if (n3 != 4) {
                                                    if (n3 != 5) {
                                                        object5 = clsCLD2.e(((e0)((ArrayList)object2).get(n4)).c(), o0, j0, this.h);
                                                        for (n3 = 0; n3 < object5.size(); ++n3) {
                                                            object = object4 = c3.e.c(((clsCLD2.a)object5.get((int)n3)).a);
                                                            if (object4 == null) {
                                                                object = ((clsCLD2.a)object5.get((int)n3)).b ? P : Q;
                                                            }
                                                            if (((String)(object4 = this.Q((String)object))).isEmpty() || ((String)object4).equals("Disable")) {
                                                                object = ((clsCLD2.a)object5.get((int)n3)).b ? P : Q;
                                                            }
                                                            object7 = R;
                                                            object4 = new e0(((clsCLD2.a)object5.get((int)n3)).c, (String)object);
                                                            ((ArrayList)object7).add(object4);
                                                        }
                                                        continue;
                                                    }
                                                    object4 = R;
                                                    object = new e0(((e0)((ArrayList)object2).get(n4)).c(), O);
                                                    ((ArrayList)object4).add(object);
                                                    continue;
                                                }
                                                object = R;
                                                object4 = new e0(((e0)((ArrayList)object2).get(n4)).c(), M);
                                                ((ArrayList)object).add(object4);
                                                continue;
                                            }
                                            object = R;
                                            object4 = new e0(((e0)((ArrayList)object2).get(n4)).c(), K);
                                            ((ArrayList)object).add(object4);
                                        }
                                    }
                                    object = R;
                                    if (((ArrayList)object).isEmpty()) break block129;
                                    object2 = ((e0)((ArrayList)object).get(0)).c();
                                    object3 = ((e0)((ArrayList)object).get(0)).b();
                                    n5 = this.onLoadLanguage((String)object3, "", "");
                                    if (n5 == -2 || n5 == -1) break block130;
                                    break block109;
                                }
                                object4 = c3.n.a;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Language is not supported: ");
                                ((StringBuilder)object).append((String)object3);
                                ((StringBuilder)object).append(", text: ");
                                ((StringBuilder)object).append((String)object2);
                                ((p)object4).d("AutoTTS", ((StringBuilder)object).toString());
                                this.N(synthesisCallback, 7);
                                return;
                            }
                            c3.n.a.d("AutoTTS", "lstLanString is empty!");
                            this.N(synthesisCallback, 7);
                            return;
                        }
                    }
                    if (!((String)object5).isEmpty() || !((String)object6).isEmpty()) break block131;
                    n5 = this.onLoadLanguage(object.getLanguage(), object.getCountry(), object.getVariant());
                    object2 = c3.n.a;
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("load ");
                    ((StringBuilder)object6).append(n5);
                    ((p)object2).c("AutoTTS", ((StringBuilder)object6).toString());
                    if (n5 == -2) break block132;
                    object2 = object4;
                    if (n5 != -1) break block109;
                }
                object2 = c3.n.a;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Language is not supported: ");
                ((StringBuilder)object3).append(object.getLanguage());
                ((StringBuilder)object3).append(", text: ");
                ((StringBuilder)object3).append((String)object4);
                ((p)object2).d("AutoTTS", ((StringBuilder)object3).toString());
                this.N(synthesisCallback, 9);
                return;
            }
            this.f0((String)object5, this.j0((String)object6), (String)object2, false);
            object2 = object4;
        }
        if (this.d >= 0 && this.d < this.f.size()) {
            if (((k0)this.f.get(this.d)).g() == null) {
                c3.n.a.d("AutoTTS", "mTTSIndex refers null tts.");
                this.N(synthesisCallback, 10);
                return;
            }
            n3 = this.S((String)object3);
            n4 = this.V((String)object3);
            n5 = this.R((String)object3);
            f3 = (float)this.l / 100.0f * (float)n3 / 100.0f;
            float f4 = (float)this.m / 100.0f * (float)n5 / 100.0f;
            ((k0)this.f.get(this.d)).g().setSpeechRate(f3);
            ((k0)this.f.get(this.d)).g().setPitch(f4);
            object = new Bundle(this.j);
            object.remove("language");
            object.remove("country");
            object.remove("voiceName");
            object.remove("variant");
            object.remove("pitch");
            object.remove("rate");
            object.remove("utteranceId");
            if (X) {
                object.remove("streamType");
                object.remove("audioAttributes");
            }
            if ((double)(f3 = this.k * (float)n4 / 100.0f) != 0.0) {
                object.putFloat("volume", f3);
            }
            ((k0)this.f.get(this.d)).g().setOnUtteranceProgressListener((UtteranceProgressListener)new e(this, synthesisCallback, null));
            if (!this.p.get() && !this.q.get()) {
                ((k0)this.f.get((int)this.d)).g = true;
                if (Y && !((k0)this.f.get((int)this.d)).h) {
                    object4 = new AudioAttributes.Builder();
                    object4 = object4.setUsage(11).setContentType(1).build();
                    ((k0)this.f.get(this.d)).g().setAudioAttributes((AudioAttributes)object4);
                    ((k0)this.f.get((int)this.d)).h = true;
                }
                object4 = this.t;
                this.u = object = new Runnable(this, (String)object2, (Bundle)object, synthesisCallback){
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
                        if (!this.f.p.get() && !this.f.q.get()) {
                            AutoTtsService.g(1);
                            p p3 = c3.n.a;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Current engine: ");
                            stringBuilder.append(((k0)this.f.f.get(this.f.d)).e());
                            p3.c("AutoTTS", stringBuilder.toString());
                            p3 = c3.n.a;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(k0);
                            stringBuilder.append("_");
                            stringBuilder.append(S);
                            p3.c("AutoTTS", stringBuilder.toString());
                            p3 = c3.n.a;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("speak 1: ");
                            stringBuilder.append(this.c);
                            p3.c("AutoTTS", stringBuilder.toString());
                            p3 = ((k0)this.f.f.get(this.f.d)).g();
                            String string = this.c;
                            Bundle bundle = this.d;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(k0);
                            stringBuilder.append("_");
                            stringBuilder.append(S);
                            if (p3.speak(string, 0, bundle, stringBuilder.toString()) != 0) {
                                c3.n.a.d("AutoTTS", "Speaking failed!!!");
                                this.f.N(this.e, 12);
                                this.f.r0(12);
                                return;
                            }
                        }
                    }
                };
                object4.postDelayed((Runnable)object, 50L);
            }
            if (!Z) {
                object = this.o;
                synchronized (object) {
                    boolean bl;
                    while (!this.p.get() && !(bl = this.q.get())) {
                        this.o.wait();
                    }
                }
            } else {
                c3.n.a.c("AutoTTS", "Keep-live activated");
                this.k0(synthesisCallback);
            }
            c3.n.a.c("AutoTTS", "onSynthesizeText ended");
            this.N(synthesisCallback, 13);
            return;
        }
        c3.n.a.d("AutoTTS", "mTTSIndex out of range.");
        this.N(synthesisCallback, 10);
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
    }

    public final void p0() {
        if (this.W()) {
            this.M();
            if (Build.VERSION.SDK_INT >= 34) {
                c3.a.a(this, 136549, this.L(), 2);
                return;
            }
            this.startForeground(136549, this.L());
        }
    }

    public final void q0(Boolean object) {
        p p3 = c3.n.a;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("stopAllTts ");
        ((StringBuilder)object2).append(object);
        p3.c("AutoTTS", ((StringBuilder)object2).toString());
        this.t.removeCallbacks(this.u);
        R.clear();
        boolean bl = (Boolean)object;
        if (bl) {
            for (int i3 = 0; i3 < this.f.size(); ++i3) {
                if (((k0)this.f.get(i3)).f() != 2 || !((k0)this.f.get((int)i3)).g || !((k0)this.f.get(i3)).g().isSpeaking()) continue;
                object = c3.n.a;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" - calling stop for ");
                ((StringBuilder)object2).append(((k0)this.f.get(i3)).e());
                ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
                object = c3.n.a;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("stop ");
                ((StringBuilder)object2).append(k0);
                ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
                ((k0)this.f.get(i3)).m();
            }
        } else if (this.d >= 0 && this.d < this.f.size() && ((k0)this.f.get(this.d)).f() == 2 && ((k0)this.f.get((int)this.d)).g && ((k0)this.f.get(this.d)).g().isSpeaking()) {
            object2 = c3.n.a;
            object = new StringBuilder();
            ((StringBuilder)object).append(" - calling speak empty for ");
            ((StringBuilder)object).append(((k0)this.f.get(this.d)).e());
            ((p)object2).c("AutoTTS", ((StringBuilder)object).toString());
            object2 = c3.n.a;
            object = new StringBuilder();
            ((StringBuilder)object).append("onSynthesizeText: ");
            ((StringBuilder)object).append(k0);
            ((StringBuilder)object).append(" ''");
            ((p)object2).c("AutoTTS", ((StringBuilder)object).toString());
            object2 = c3.n.a;
            object = new StringBuilder();
            ((StringBuilder)object).append("speak ");
            ((StringBuilder)object).append(k0);
            ((p)object2).c("AutoTTS", ((StringBuilder)object).toString());
            ((k0)this.f.get(this.d)).g().speak((CharSequence)"", 0, null, null);
        }
        object = this.o;
        synchronized (object) {
            this.p.set(true);
            this.o.notifyAll();
        }
        object2 = this.o;
        synchronized (object2) {
            this.q.set(true);
            this.o.notifyAll();
            return;
        }
    }

    public final void r0(int n3) {
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

    public final boolean t0(SynthesisCallback synthesisCallback) {
        byte[] byArray;
        int n3;
        int n4 = synthesisCallback.getMaxBufferSize();
        for (int i3 = 0; i3 < (byArray = l0).length && !this.p.get(); i3 += n3) {
            n3 = Math.min(n4, byArray.length - i3);
            if (synthesisCallback.audioAvailable(byArray, i3, n3) == 0) continue;
            return false;
        }
        return true;
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
            AutoTtsService.s(1);
            this.a.n0(o0, "Valid license");
        }

        @Override
        public void b(int n3) {
            AutoTtsService.s(1);
            this.a.n0(o0, "License check error");
        }

        @Override
        public void c(int n3) {
            AutoTtsService.s(1);
            this.a.n0(o0, "Invalid license");
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
            if (this.a.i >= U.size()) {
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
                if (Y) {
                    object = new AudioAttributes.Builder();
                    object = object.setUsage(11).setContentType(1).build();
                    h0.setAudioAttributes((AudioAttributes)object);
                    ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).i)).h = true;
                }
                ((k0)this.a.f.get(this.a.i)).k(h0);
                ((k0)this.a.f.get(this.a.i)).j(2);
                if (((String)U.get(this.a.i)).equals("com.google.android.tts")) {
                    object = this.a;
                    AutoTtsService.F((AutoTtsService)((Object)object), ((AutoTtsService)((Object)object)).i);
                }
            } else {
                ((k0)this.a.f.get(this.a.i)).k(h0);
                ((k0)this.a.f.get(this.a.i)).j(-1);
            }
            n3 = 0;
            while (n3 == 0) {
                AutoTtsService.d(this.a);
                if (this.a.i < U.size()) {
                    this.a.f.add(new k0((String)U.get(this.a.i)));
                    object = new c3.d(this.a.h, this.a);
                    ((c3.d)object).c((String)U.get(this.a.i));
                    this.a.g.add(object);
                    object = this.a.h;
                    object2 = new c(this.a);
                    TextToSpeech textToSpeech = new TextToSpeech((Context)object, (TextToSpeech.OnInitListener)object2, (String)U.get(this.a.i));
                    AutoTtsService.C(textToSpeech);
                } else {
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
            if (this.a.v >= 0 && this.a.v < this.a.f.size()) {
                p p3 = c3.n.a;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Restore ");
                stringBuilder.append(((k0)this.a.f.get(this.a.v)).e());
                p3.c("AutoTTS", stringBuilder.toString());
                p3 = c3.n.a;
                stringBuilder = new StringBuilder();
                stringBuilder.append("res ");
                stringBuilder.append(n3);
                p3.c("AutoTTS", stringBuilder.toString());
                if (n3 == 0) {
                    if (Y) {
                        stringBuilder = new AudioAttributes.Builder();
                        stringBuilder = stringBuilder.setUsage(11).setContentType(1).build();
                        h0.setAudioAttributes((AudioAttributes)stringBuilder);
                        ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).v)).h = true;
                    }
                    ((k0)this.a.f.get(this.a.v)).k(h0);
                    ((k0)this.a.f.get(this.a.v)).j(2);
                    ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).v)).e = "";
                    ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).v)).d = null;
                } else {
                    ((k0)this.a.f.get(this.a.v)).k(h0);
                    ((k0)this.a.f.get(this.a.v)).j(-1);
                }
                AutoTtsService.J(this.a, -1);
                return;
            }
            c3.n.a.c("AutoTTS", "ttsInitListener_restore invalid index");
            AutoTtsService.J(this.a, -1);
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

        public void onDone(String string) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDone ");
            stringBuilder.append(string);
            p3.c("AutoTTS", stringBuilder.toString());
            if (R.size() > 1) {
                this.b.postDelayed(AutoTtsService.n(this.c, new Runnable(this){
                    public final e c;
                    {
                        this.c = e3;
                    }

                    /*
                     * WARNING - void declaration
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        Object object;
                        Object object2;
                        String string;
                        block44: {
                            boolean bl;
                            void var7_41;
                            int n3;
                            block26: {
                                block39: {
                                    void var7_22;
                                    block33: {
                                        block40: {
                                            block41: {
                                                block42: {
                                                    block43: {
                                                        String string2;
                                                        block34: {
                                                            void var7_32;
                                                            block38: {
                                                                block35: {
                                                                    block36: {
                                                                        block37: {
                                                                            void var7_26;
                                                                            block32: {
                                                                                block30: {
                                                                                    block31: {
                                                                                        block27: {
                                                                                            block28: {
                                                                                                block29: {
                                                                                                    if (this.c.c.p.get()) return;
                                                                                                    if (this.c.c.q.get()) {
                                                                                                        return;
                                                                                                    }
                                                                                                    AutoTtsService.h();
                                                                                                    R.remove(0);
                                                                                                    string = ((e0)R.get(0)).c();
                                                                                                    n3 = T;
                                                                                                    if (n3 != 1) break block27;
                                                                                                    n3 = ((e0)R.get(0)).a();
                                                                                                    String string3 = "eng";
                                                                                                    if (n3 == 1) break block28;
                                                                                                    if (n3 == 2) break block29;
                                                                                                    if (n3 != 3) {
                                                                                                        if (n3 != 4) {
                                                                                                            if (n3 == 5) {
                                                                                                                String string4 = O;
                                                                                                                n3 = this.c.c.d0(O, "", "");
                                                                                                                if (n3 == -2 || n3 == -1) {
                                                                                                                    p p3 = c3.n.a;
                                                                                                                    StringBuilder stringBuilder = new StringBuilder();
                                                                                                                    stringBuilder.append("Language ");
                                                                                                                    stringBuilder.append(O);
                                                                                                                    stringBuilder.append(" is not supported.\n Text: ");
                                                                                                                    stringBuilder.append(string);
                                                                                                                    p3.d("AutoTTS", stringBuilder.toString());
                                                                                                                    e e3 = this.c;
                                                                                                                    e3.c.O(e3.a, 3);
                                                                                                                    return;
                                                                                                                }
                                                                                                            }
                                                                                                            break block26;
                                                                                                        } else {
                                                                                                            String string5 = M;
                                                                                                            n3 = this.c.c.d0(M, "", "");
                                                                                                            if (n3 == -2 || n3 == -1) {
                                                                                                                p p4 = c3.n.a;
                                                                                                                StringBuilder stringBuilder = new StringBuilder();
                                                                                                                stringBuilder.append("Language ");
                                                                                                                stringBuilder.append(M);
                                                                                                                stringBuilder.append(" is not supported.\n Text: ");
                                                                                                                stringBuilder.append(string);
                                                                                                                p4.d("AutoTTS", stringBuilder.toString());
                                                                                                                e e4 = this.c;
                                                                                                                e4.c.O(e4.a, 3);
                                                                                                                return;
                                                                                                            }
                                                                                                        }
                                                                                                        break block26;
                                                                                                    } else {
                                                                                                        String string6 = K;
                                                                                                        n3 = this.c.c.d0(K, "", "");
                                                                                                        if (n3 == -2 || n3 == -1) {
                                                                                                            p p5 = c3.n.a;
                                                                                                            StringBuilder stringBuilder = new StringBuilder();
                                                                                                            stringBuilder.append("Language ");
                                                                                                            stringBuilder.append(K);
                                                                                                            stringBuilder.append(" is not supported.\n Text: ");
                                                                                                            stringBuilder.append(string);
                                                                                                            p5.d("AutoTTS", stringBuilder.toString());
                                                                                                            e e5 = this.c;
                                                                                                            e5.c.O(e5.a, 3);
                                                                                                            return;
                                                                                                        }
                                                                                                    }
                                                                                                    break block26;
                                                                                                }
                                                                                                String string7 = I;
                                                                                                n3 = this.c.c.d0(I, "", "");
                                                                                                if (n3 == -2 || n3 == -1) {
                                                                                                    p p6 = c3.n.a;
                                                                                                    StringBuilder stringBuilder = new StringBuilder();
                                                                                                    stringBuilder.append("Language ");
                                                                                                    stringBuilder.append(I);
                                                                                                    stringBuilder.append(" is not supported.\n Text: ");
                                                                                                    stringBuilder.append(string);
                                                                                                    p6.d("AutoTTS", stringBuilder.toString());
                                                                                                    e e6 = this.c;
                                                                                                    e6.c.O(e6.a, 3);
                                                                                                    return;
                                                                                                }
                                                                                                break block26;
                                                                                            }
                                                                                            n3 = this.c.c.d0("eng", "", "");
                                                                                            if (n3 == -2 || n3 == -1) {
                                                                                                p p7 = c3.n.a;
                                                                                                StringBuilder stringBuilder = new StringBuilder();
                                                                                                stringBuilder.append("Language eng is not supported.\n Text: ");
                                                                                                stringBuilder.append(string);
                                                                                                p7.d("AutoTTS", stringBuilder.toString());
                                                                                                e e7 = this.c;
                                                                                                e7.c.O(e7.a, 2);
                                                                                                return;
                                                                                            }
                                                                                            break block26;
                                                                                        }
                                                                                        if (T == 4 || T == 5) break block30;
                                                                                        object2 = ((e0)R.get(0)).b();
                                                                                        n3 = this.c.c.d0((String)object2, "", "");
                                                                                        if (n3 == -2) break block31;
                                                                                        String string8 = object2;
                                                                                        if (n3 != -1) break block26;
                                                                                    }
                                                                                    p p8 = c3.n.a;
                                                                                    StringBuilder stringBuilder = new StringBuilder();
                                                                                    stringBuilder.append("Language ");
                                                                                    stringBuilder.append((String)object2);
                                                                                    stringBuilder.append(" is not supported.\n Text: ");
                                                                                    stringBuilder.append(string);
                                                                                    p8.d("AutoTTS", stringBuilder.toString());
                                                                                    e e8 = this.c;
                                                                                    e8.c.O(e8.a, 4);
                                                                                    return;
                                                                                }
                                                                                object2 = ((e0)R.get(0)).b();
                                                                                if (((String)object2).isEmpty()) break block32;
                                                                                String string9 = object2;
                                                                                if (!((String)object2).equals("unknown")) break block33;
                                                                            }
                                                                            object2 = clsCLD2.d(string, o0, j0, this.c.c.h);
                                                                            object = c3.n.a;
                                                                            StringBuilder stringBuilder = new StringBuilder();
                                                                            stringBuilder.append("Cld2: ");
                                                                            stringBuilder.append((String)object2);
                                                                            stringBuilder.append(" '");
                                                                            stringBuilder.append(string);
                                                                            stringBuilder.append("'");
                                                                            ((p)object).c("AutoTTS", stringBuilder.toString());
                                                                            String string10 = object2;
                                                                            if (((String)object2).length() > 2) {
                                                                                String string11 = ((String)object2).substring(0, 2);
                                                                            }
                                                                            object2 = object = c3.e.c((String)var7_26);
                                                                            if (object != null) break block34;
                                                                            n3 = ((e0)R.get(0)).a();
                                                                            if (n3 == 1) break block35;
                                                                            if (n3 == 2) break block36;
                                                                            if (n3 == 3) break block37;
                                                                            if (n3 != 4) {
                                                                                if (n3 == 5) {
                                                                                    String string12 = O;
                                                                                }
                                                                                break block38;
                                                                            } else {
                                                                                String string13 = M;
                                                                            }
                                                                            break block38;
                                                                        }
                                                                        String string14 = K;
                                                                        break block38;
                                                                    }
                                                                    String string15 = Q;
                                                                    break block38;
                                                                }
                                                                String string16 = P;
                                                            }
                                                            object2 = var7_32;
                                                        }
                                                        if (!(string2 = this.c.c.Q((String)object2)).isEmpty() && !string2.equals("Disable")) break block39;
                                                        n3 = ((e0)R.get(0)).a();
                                                        if (n3 == 1) break block40;
                                                        if (n3 == 2) break block41;
                                                        if (n3 == 3) break block42;
                                                        if (n3 == 4) break block43;
                                                        if (n3 != 5) break block39;
                                                        String string17 = O;
                                                        break block33;
                                                    }
                                                    String string18 = M;
                                                    break block33;
                                                }
                                                String string19 = K;
                                                break block33;
                                            }
                                            String string20 = Q;
                                            break block33;
                                        }
                                        String string21 = P;
                                    }
                                    object2 = var7_22;
                                }
                                n3 = this.c.c.d0((String)object2, "", "");
                                if (n3 == -2) break block44;
                                String string22 = object2;
                                if (n3 == -1) break block44;
                            }
                            int n4 = this.c.c.S((String)var7_41);
                            n3 = this.c.c.V((String)var7_41);
                            int n5 = this.c.c.R((String)var7_41);
                            float f3 = (float)this.c.c.l / 100.0f * (float)n4 / 100.0f;
                            float f4 = (float)this.c.c.m / 100.0f * (float)n5 / 100.0f;
                            ((k0)this.c.c.f.get(this.c.c.d)).g().setSpeechRate(f3);
                            ((k0)this.c.c.f.get(this.c.c.d)).g().setPitch(f4);
                            Bundle bundle = new Bundle(this.c.c.j);
                            bundle.remove("language");
                            bundle.remove("country");
                            bundle.remove("voiceName");
                            bundle.remove("variant");
                            bundle.remove("pitch");
                            bundle.remove("rate");
                            bundle.remove("utteranceId");
                            if (X) {
                                bundle.remove("streamType");
                                bundle.remove("audioAttributes");
                            }
                            if ((double)(f3 = this.c.c.k * (float)n3 / 100.0f) != 0.0) {
                                bundle.putFloat("volume", f3);
                            }
                            object2 = ((k0)this.c.c.f.get(this.c.c.d)).g();
                            e e9 = this.c;
                            object = new e(e9.c, e9.a, null);
                            object2.setOnUtteranceProgressListener((UtteranceProgressListener)object);
                            ((k0)((AutoTtsService)this.c.c).f.get((int)((AutoTtsService)this.c.c).d)).g = true;
                            if (Y && !(bl = ((k0)((AutoTtsService)this.c.c).f.get((int)((AutoTtsService)this.c.c).d)).h)) {
                                object2 = new AudioAttributes.Builder();
                                object2 = object2.setUsage(11).setContentType(1).build();
                                ((k0)this.c.c.f.get(this.c.c.d)).g().setAudioAttributes((AudioAttributes)object2);
                                ((k0)((AutoTtsService)this.c.c).f.get((int)((AutoTtsService)this.c.c).d)).h = true;
                            }
                            if (this.c.c.p.get()) return;
                            if (this.c.c.q.get()) return;
                            object2 = c3.n.a;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("speak 2: ");
                            ((StringBuilder)object).append(string);
                            ((p)object2).c("AutoTTS", ((StringBuilder)object).toString());
                            object = ((k0)this.c.c.f.get(this.c.c.d)).g();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append(k0);
                            ((StringBuilder)object2).append("_");
                            ((StringBuilder)object2).append(S);
                            if (object.speak((CharSequence)string, 0, bundle, ((StringBuilder)object2).toString()) == 0) return;
                            c3.n.a.d("AutoTTS", "Speaking failed!!!");
                            e e10 = this.c;
                            e10.c.O(e10.a, 5);
                            return;
                        }
                        object = c3.n.a;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Language ");
                        stringBuilder.append((String)object2);
                        stringBuilder.append(" is not supported.\n Text: ");
                        stringBuilder.append(string);
                        ((p)object).d("AutoTTS", stringBuilder.toString());
                        e e11 = this.c;
                        e11.c.N(e11.a, 14);
                    }
                }), 50L);
                return;
            }
            c3.n.a.c("AutoTTS", "No more text to read.");
            this.c.O(this.a, 7);
        }

        public void onError(String string) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            p3.d("AutoTTS", stringBuilder.toString());
            this.c.O(this.a, 8);
        }

        public void onError(String string, int n3) {
            p p3 = c3.n.a;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError ");
            stringBuilder.append(string);
            stringBuilder.append(" code ");
            stringBuilder.append(n3);
            p3.d("AutoTTS", stringBuilder.toString());
            this.c.O(this.a, 9);
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

