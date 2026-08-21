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
import java.util.Objects;
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

    public static /* synthetic */ Bundle B(AutoTtsService autoTtsService) {
        return autoTtsService.j;
    }

    public static /* synthetic */ TextToSpeech C(TextToSpeech textToSpeech) {
        h0 = textToSpeech;
        return textToSpeech;
    }

    public static /* synthetic */ float D(AutoTtsService autoTtsService) {
        return autoTtsService.k;
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

    public static /* synthetic */ int o(AutoTtsService autoTtsService, String string, String string2, String string3) {
        return autoTtsService.d0(string, string2, string3);
    }

    public static /* synthetic */ int s(int n3) {
        o0 = n3;
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static void s0() {
        Throwable throwable2;
        block12: {
            Object object;
            // MONITORENTER : com.vnspeak.autotts.AutoTtsService.class
            c3.n.a.c("AutoTTS", "updateLanguage2LetterCodes");
            ArrayList<String> arrayList = new ArrayList<String>();
            Object object2 = c3.n.c;
            // MONITORENTER : object2
            try {
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
                // MONITOREXIT : object2
            }
            catch (Throwable throwable2) {
                break block12;
            }
            clsCLD2.i(c3.n.f);
            object = c3.n.a;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(c3.n.f.toString());
            ((p)object).c("AutoTTS", ((StringBuilder)object2).toString());
            return;
        }
        throw throwable2;
    }

    public static /* synthetic */ int t() {
        return j0;
    }

    public static /* synthetic */ String u(AutoTtsService autoTtsService, String string) {
        return autoTtsService.Q(string);
    }

    public static /* synthetic */ int v(AutoTtsService autoTtsService, String string) {
        return autoTtsService.S(string);
    }

    public static /* synthetic */ int w(AutoTtsService autoTtsService, String string) {
        return autoTtsService.V(string);
    }

    public static /* synthetic */ int x(AutoTtsService autoTtsService, String string) {
        return autoTtsService.R(string);
    }

    public static /* synthetic */ int y(AutoTtsService autoTtsService) {
        return autoTtsService.l;
    }

    public static /* synthetic */ int z(AutoTtsService autoTtsService) {
        return autoTtsService.m;
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void O(SynthesisCallback synthesisCallback, int n3) {
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
    public final void P() {
        List list = c3.n.c;
        synchronized (list) {
            Throwable throwable2;
            block4: {
                block3: {
                    try {
                        if (this.w && !list.isEmpty()) break block3;
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                    c3.n.a.c("AutoTTS", "languages missing at point of use - reloading");
                    this.e0();
                }
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
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
            try {
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
                    return ((f)object3.get((int)i3)).f;
                }
                // MONITOREXIT @DISABLED, blocks:[0, 3] lbl39 : MonitorExitStatement: MONITOREXIT : var3_3
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
    public final int R(String string) {
        List list = c3.n.c;
        synchronized (list) {
            int n3 = 0;
            try {
                while (true) {
                    List list2 = c3.n.c;
                    if (n3 >= list2.size()) return 100;
                    if (((f)list2.get((int)n3)).b.equals(string)) {
                        return ((f)list2.get((int)n3)).e;
                    }
                    ++n3;
                }
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
    public final int S(String string) {
        List list = c3.n.c;
        synchronized (list) {
            int n3 = 0;
            try {
                while (true) {
                    List list2 = c3.n.c;
                    if (n3 >= list2.size()) return 100;
                    if (((f)list2.get((int)n3)).b.equals(string)) {
                        return ((f)list2.get((int)n3)).c;
                    }
                    ++n3;
                }
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
            int n3 = 0;
            try {
                while (true) {
                    object2 = c3.n.c;
                    if (n3 >= object2.size()) return "";
                    if (!(((f)object2.get((int)n3)).f.isEmpty() || ((f)object2.get((int)n3)).f.equalsIgnoreCase("disable") || ((f)object2.get((int)n3)).i)) {
                        p p3 = c3.n.a;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(" -");
                        stringBuilder.append(((f)object2.get((int)n3)).b);
                        stringBuilder.append(" -> ");
                        stringBuilder.append(((f)object2.get((int)n3)).h);
                        p3.c("AutoTTS", stringBuilder.toString());
                        if (((String)object).equals(((f)object2.get((int)n3)).b)) {
                            object = c3.n.a;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(" Found ");
                            stringBuilder.append(((f)object2.get((int)n3)).h);
                            ((p)object).c("AutoTTS", stringBuilder.toString());
                            return ((f)object2.get((int)n3)).h;
                        }
                    }
                    ++n3;
                }
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
            int n3 = 0;
            try {
                while (true) {
                    object2 = c3.n.c;
                    if (n3 >= object2.size()) return "";
                    if (!(((f)object2.get((int)n3)).f.isEmpty() || ((f)object2.get((int)n3)).f.equalsIgnoreCase("disable") || ((f)object2.get((int)n3)).i)) {
                        p p3 = c3.n.a;
                        Object object3 = new StringBuilder();
                        ((StringBuilder)object3).append(" -");
                        ((StringBuilder)object3).append(((f)object2.get((int)n3)).b);
                        ((StringBuilder)object3).append(" -> ");
                        ((StringBuilder)object3).append(((f)object2.get((int)n3)).g);
                        p3.c("AutoTTS", ((StringBuilder)object3).toString());
                        if (((String)charSequence).equals(((f)object2.get((int)n3)).b)) {
                            object3 = c3.n.a;
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(" Found ");
                            ((StringBuilder)charSequence).append(((f)object2.get((int)n3)).g);
                            ((p)object3).c("AutoTTS", ((StringBuilder)charSequence).toString());
                            return ((f)object2.get((int)n3)).g;
                        }
                    }
                    ++n3;
                }
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
    public final int V(String string) {
        List list = c3.n.c;
        synchronized (list) {
            int n3 = 0;
            try {
                while (true) {
                    List list2 = c3.n.c;
                    if (n3 >= list2.size()) return 100;
                    if (((f)list2.get((int)n3)).b.equals(string)) {
                        return ((f)list2.get((int)n3)).d;
                    }
                    ++n3;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final boolean W() {
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
    public final void X() {
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
                    catch (Exception exception) {}
                    ++n3;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void c0() {
        synchronized (this) {
            try {
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
            catch (Throwable throwable) {}
            throw throwable;
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final void e0() {
        // MONITORENTER : this
        c3.n.a.c("AutoTTS", "loadLanguages");
        var9_1 = new ArrayList<Object>();
        var10_2 = this.getApplicationContext().getSharedPreferences("auto_tts_settings", 0);
        var1_3 = 0;
        while (true) {
            var5_7 = new StringBuilder();
            var5_7.append("language_");
            var5_7.append(var1_3);
            var11_12 = var10_2.getString(var5_7.toString(), "");
            if (!var11_12.isEmpty()) ** GOTO lbl-1000
            var5_7 = c3.n.c;
            // MONITORENTER : var5_7
            var5_7.clear();
            var5_7.addAll(var9_1);
            break;
        }
        {
            block11: {
                AutoTtsService.s0();
                // MONITOREXIT : var5_7
                this.w = true;
                // MONITOREXIT : this
                return;
lbl-1000:
                // 1 sources

                {
                    var5_7 = new StringBuilder();
                    var5_7.append(var11_12);
                    var5_7.append("_speed");
                    var4_6 = var10_2.getInt(var5_7.toString(), 100);
                    var5_7 = new StringBuilder();
                    var5_7.append(var11_12);
                    var5_7.append("_pitch");
                    var3_5 = var10_2.getInt(var5_7.toString(), 100);
                    var5_7 = new StringBuilder();
                    var5_7.append(var11_12);
                    var5_7.append("_volume");
                    var2_4 = var10_2.getInt(var5_7.toString(), 100);
                    var5_7 = new StringBuilder();
                    var5_7.append(var11_12);
                    var5_7.append("_variant");
                    var12_13 = var10_2.getString(var5_7.toString(), "*Default");
                    var8_11 = "";
                    var7_10 = "";
                    var13_14 = var10_2.getString(var11_12, "");
                    var6_9 = var8_11;
                    var5_7 = var7_10;
                    if (var13_14.isEmpty()) break block11;
                    var13_14 = var13_14.split("#");
                    var6_9 = var8_11;
                    var5_7 = var7_10;
                    if (var13_14.length < 2) break block11;
                }
                var6_9 = var13_14[0];
                var5_7 = var13_14[1];
            }
            var8_11 = new f("", var11_12, var4_6, var2_4, var3_5, (String)var6_9, (String)var5_7, var12_13);
            var7_10 = new StringBuilder();
            var7_10.append(var11_12);
            var7_10.append("_disabled");
            var8_11.i = var10_2.getBoolean(var7_10.toString(), false);
            var9_1.add(var8_11);
            var7_10 = c3.n.a;
            var8_11 = new StringBuilder();
            var8_11.append(" - ");
            var8_11.append(var11_12);
            var8_11.append(" ");
            var8_11.append((String)var6_9);
            var8_11.append(" ");
            var8_11.append((String)var5_7);
            var8_11.append(" ");
            var8_11.append(var12_13);
            var7_10.c("AutoTTS", var8_11.toString());
            ++var1_3;
            continue;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void f0(String object, Locale locale, String object2, boolean bl) {
        block32: {
            int n3;
            Object object3;
            Object object4;
            Object object5;
            block29: {
                Object object6;
                String string;
                block33: {
                    block34: {
                        int n4;
                        block28: {
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
                                break block28;
                            }
                            n4 = -1;
                        }
                        if (n4 == -1) {
                            c3.n.a.d("AutoTTS", "TTS is not ready");
                            this.d = -1;
                            return;
                        }
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
                        if (((k0)this.f.get((int)n4)).e.isEmpty()) break block33;
                        object6 = ((k0)this.f.get(n4)).g().getVoice();
                        object4 = object;
                        object5 = object3;
                        if (object6 == null) break block33;
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
                        if (!c3.n.e((Locale)object).equals(c3.n.e(locale))) break block33;
                        if (c3.n.d((Locale)object).equals(c3.n.d(locale))) break block34;
                        object4 = object;
                        object5 = object3;
                        if (!c3.n.d(locale).equals("")) break block33;
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
                    if (n3 >= n5) break block29;
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
                    Throwable throwable2;
                    block31: {
                        block30: {
                            try {
                                object6 = object3.iterator();
                                do {
                                    object = object2;
                                    if (!object6.hasNext()) break block30;
                                    object = (f)object6.next();
                                } while (!((f)object).f.equals(string) || !((f)object).g.equals(locale.toString()));
                                object = ((f)object).h;
                            }
                            catch (Throwable throwable2) {
                                break block31;
                            }
                        }
                        break block29;
                    }
                    throw throwable2;
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
                            break block32;
                        } else {
                            this.m0(((k0)this.f.get(this.d)).e());
                        }
                        break block32;
                    }
                }
                if (!this.Z(locale, (Locale)object4)) {
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
        }
        ((k0)this.f.get((int)n4)).d = locale;
        ((k0)this.f.get((int)n4)).e = object;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void h0(String object, Locale object2, String object3, boolean bl) {
        Object object4;
        Object object5;
        Iterator iterator;
        Object object6;
        block23: {
            Object object7;
            int n3;
            block21: {
                object6 = c3.n.a;
                iterator = new StringBuilder();
                ((StringBuilder)((Object)iterator)).append("loadVoice_Secondary ");
                ((StringBuilder)((Object)iterator)).append((String)object);
                ((StringBuilder)((Object)iterator)).append(" ");
                ((StringBuilder)((Object)iterator)).append(((Locale)object2).toString());
                ((StringBuilder)((Object)iterator)).append(" ");
                ((StringBuilder)((Object)iterator)).append((String)object3);
                ((p)object6).c("AutoTTS", ((StringBuilder)((Object)iterator)).toString());
                iterator = c3.n.a;
                object6 = new StringBuilder();
                ((StringBuilder)object6).append("Current Engine ");
                ((StringBuilder)object6).append(this.c);
                ((p)((Object)iterator)).c("AutoTTS", ((StringBuilder)object6).toString());
                if (((String)object).isEmpty()) {
                    object = this.c;
                } else {
                    this.c = object;
                }
                object = ((String)object).replace("-", "").replace("_", "");
                iterator = c3.n.a;
                object6 = new StringBuilder();
                ((StringBuilder)object6).append(" current engine: ");
                ((StringBuilder)object6).append((String)object);
                ((p)((Object)iterator)).c("AutoTTS", ((StringBuilder)object6).toString());
                for (n3 = 0; n3 < this.f.size(); ++n3) {
                    if (!((k0)this.f.get(n3)).e().equals(object) || ((k0)this.f.get(n3)).f() != 2) continue;
                    c3.n.a.c("AutoTTS", " found!");
                    break block21;
                }
                n3 = -1;
            }
            if (n3 == -1) {
                c3.n.a.d("AutoTTS", "TTS is not ready");
                this.d = -1;
                return;
            }
            int n4 = this.d;
            this.d = n3;
            if (bl && ((k0)this.f.get((int)this.d)).f) {
                return;
            }
            object6 = new Locale("zxx");
            iterator = "";
            object5 = ((k0)this.f.get(n3)).g().getVoice();
            if (object5 != null) {
                object6 = object5.getLocale();
                iterator = object5.getName();
            }
            if (n4 == this.d && object5 != null) {
                object7 = c3.n.a;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append(" Engine Variant ");
                ((StringBuilder)object4).append((String)((Object)iterator));
                ((p)object7).c("AutoTTS", ((StringBuilder)object4).toString());
                object4 = c3.n.a;
                object7 = new StringBuilder();
                ((StringBuilder)object7).append(" Engine Locale ");
                ((StringBuilder)object7).append(((Locale)object6).toString());
                ((p)object4).c("AutoTTS", ((StringBuilder)object7).toString());
                if (this.Z((Locale)object2, (Locale)object6) && (((String)((Object)iterator)).equals(object3) || ((String)object3).equals("*Default") || ((String)object3).isEmpty())) {
                    c3.n.a.c("AutoTTS", " *0 Do nothing");
                    return;
                }
            }
            object4 = c3.n.a;
            object7 = new StringBuilder();
            ((StringBuilder)object7).append("Searching ");
            ((StringBuilder)object7).append((String)object);
            ((StringBuilder)object7).append(" ");
            ((StringBuilder)object7).append(((Locale)object2).toString());
            ((p)object4).c("AutoTTS", ((StringBuilder)object7).toString());
            object4 = g0;
            n4 = ((ArrayList)object4).size();
            for (n3 = 0; n3 < n4; ++n3) {
                object7 = ((ArrayList)object4).get(n3);
                Object object8 = (String)object7;
                p p3 = c3.n.a;
                object7 = new StringBuilder();
                ((StringBuilder)object7).append(" *");
                ((StringBuilder)object7).append((String)object8);
                p3.c("AutoTTS", ((StringBuilder)object7).toString());
                object7 = ((String)object8).split("#");
                if (((String[])object7).length < 2 || !object7[0].equals(object)) continue;
                object4 = c3.n.c;
                synchronized (object4) {
                    try {
                        block22: {
                            object8 = object4.iterator();
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
                                break block22;
                            }
                            object = object3;
                        }
                        break block23;
                    }
                    catch (Throwable throwable) {}
                    throw throwable;
                }
            }
            object = object3;
        }
        object3 = c3.n.a;
        object4 = new StringBuilder();
        ((StringBuilder)object4).append(" Variant ");
        ((StringBuilder)object4).append((String)object);
        ((p)object3).c("AutoTTS", ((StringBuilder)object4).toString());
        object4 = c3.n.a;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append(" Locale ");
        ((StringBuilder)object3).append(((Locale)object2).toString());
        ((p)object4).c("AutoTTS", ((StringBuilder)object3).toString());
        if ((((String)object).equals("*Default") || ((String)object).isEmpty()) && !this.Z((Locale)object2, (Locale)object6)) {
            c3.n.a.c("AutoTTS", " *1");
            object = c3.n.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(((Locale)object2).toString());
            ((StringBuilder)object3).append(" vs ");
            ((StringBuilder)object3).append(((Locale)object6).toString());
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
        if (object5 != null) {
            object3 = c3.n.a;
            object5 = new StringBuilder();
            ((StringBuilder)object5).append(" Engine Variant ");
            ((StringBuilder)object5).append((String)((Object)iterator));
            ((p)object3).c("AutoTTS", ((StringBuilder)object5).toString());
            object5 = c3.n.a;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append(" Engine Locale ");
            ((StringBuilder)object3).append(((Locale)object6).toString());
            ((p)object5).c("AutoTTS", ((StringBuilder)object3).toString());
            if (this.Z((Locale)object2, (Locale)object6) && (((String)((Object)iterator)).equals(object) || ((String)object).equals("*Default") || ((String)object).isEmpty())) {
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
                    object6 = new StringBuilder();
                    ((StringBuilder)object6).append("Set voice 2: ");
                    ((StringBuilder)object6).append(object3.toString());
                    ((p)object2).c("AutoTTS", ((StringBuilder)object6).toString());
                    ((k0)this.f.get((int)this.d)).d = object3.getLocale();
                    ((k0)this.f.get((int)this.d)).e = object;
                    ((k0)this.f.get((int)this.d)).f = true;
                    return;
                }
                this.m0(((k0)this.f.get(this.d)).e());
                break;
            }
        }
        if (this.Z((Locale)object2, (Locale)object6)) return;
        if (((k0)this.f.get(this.d)).g().setLanguage((Locale)object2) >= 0) {
            ((k0)this.f.get((int)this.d)).d = object2;
            ((k0)this.f.get((int)this.d)).e = ((Locale)object2).getVariant();
            ((k0)this.f.get((int)this.d)).f = true;
            c3.n.a.c("AutoTTS", "Set voice 3");
            return;
        }
        this.m0(((k0)this.f.get(this.d)).e());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void i0() {
        synchronized (this) {
            block7: {
                boolean bl = g0.isEmpty();
                if (bl) break block7;
                return;
            }
            try {
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
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final Locale j0(String object) {
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
    public final void k0(SynthesisCallback synthesisCallback) {
        synthesisCallback.start(16000, 2, 1);
        while (!this.p.get() && this.t0(synthesisCallback)) {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void m0(String object) {
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
                    if (this.v != -1) {
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
            this.v = n3;
            object = c3.n.a;
            stringBuilder = new StringBuilder();
            stringBuilder.append("restoreTts ");
            stringBuilder.append(((k0)this.f.get(this.v)).e());
            ((p)object).c("AutoTTS", stringBuilder.toString());
            try {
                ((k0)this.f.get(this.v)).m();
                ((k0)this.f.get(this.v)).l();
            }
            catch (Exception exception) {}
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
                        if (!a0) break block2;
                        this.p0();
                    }
                    catch (Exception exception2) {
                        break block3;
                    }
                }
                this.l0();
                break block4;
            }
            String string = exception2.getMessage();
            Objects.requireNonNull(string);
            Log.e((String)"AutoTTS", (String)string);
        }
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
        if ((licenseChecker = this.A) != null) {
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
            return this.d0(string, string2, string3);
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

    /*
     * Exception decompiling
     */
    public void onSynthesizeText(SynthesisRequest var1_1, SynthesisCallback var2_7) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 7[TRYBLOCK] [8 : 753->834)] java.lang.Throwable
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void p0() {
        Exception exception2;
        block4: {
            block3: {
                try {
                    if (!this.W()) break block3;
                    this.M();
                    if (Build.VERSION.SDK_INT >= 34) {
                        c3.a.a(this, 136549, this.L(), 2);
                        return;
                    }
                }
                catch (Exception exception2) {
                    break block4;
                }
                this.startForeground(136549, this.L());
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
    public final void q0(Boolean object) {
        Object object2 = c3.n.a;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("stopAllTts ");
        ((StringBuilder)object3).append(object);
        ((p)object2).c("AutoTTS", ((StringBuilder)object3).toString());
        this.t.removeCallbacks(this.u);
        R.clear();
        boolean bl = (Boolean)object;
        if (!bl) {
            if (this.d >= 0 && this.d < this.f.size() && ((k0)this.f.get(this.d)).f() == 2 && ((k0)this.f.get((int)this.d)).g && ((k0)this.f.get(this.d)).g().isSpeaking()) {
                try {
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" - calling speak empty for ");
                    ((StringBuilder)object).append(((k0)this.f.get(this.d)).e());
                    ((p)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onSynthesizeText: ");
                    ((StringBuilder)object).append(k0);
                    ((StringBuilder)object).append(" ''");
                    ((p)object3).c("AutoTTS", ((StringBuilder)object).toString());
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("speak ");
                    ((StringBuilder)object).append(k0);
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
                    object = c3.n.a;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("stop ");
                    ((StringBuilder)object3).append(k0);
                    ((p)object).c("AutoTTS", ((StringBuilder)object3).toString());
                    ((k0)this.f.get(i3)).m();
                    continue;
                }
                catch (Exception exception) {
                    object3 = c3.n.a;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Stop failed for ");
                    ((StringBuilder)object).append(((k0)this.f.get(i3)).e());
                    ((StringBuilder)object).append("\n  ");
                    ((StringBuilder)object).append(exception.getMessage());
                    ((p)object3).d("AutoTTS", ((StringBuilder)object).toString());
                }
            }
        }
        object = this.o;
        synchronized (object) {
            this.p.set(true);
            this.o.notifyAll();
        }
        object3 = this.o;
        synchronized (object3) {
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
                    try {
                        object = new AudioAttributes.Builder();
                        object = object.setUsage(11).setContentType(1).build();
                        h0.setAudioAttributes((AudioAttributes)object);
                        ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).i)).h = true;
                    }
                    catch (Exception exception) {
                        c3.n.a.d("AutoTTS", ((Object)exception).toString());
                    }
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
                block11: {
                    AutoTtsService.d(this.a);
                    if (this.a.i < U.size()) {
                        this.a.f.add(new k0((String)U.get(this.a.i)));
                        object = new c3.d(this.a.h, this.a);
                        ((c3.d)object).c((String)U.get(this.a.i));
                        this.a.g.add(object);
                        try {
                            object = this.a.h;
                            object2 = new c(this.a);
                            TextToSpeech textToSpeech = new TextToSpeech((Context)object, (TextToSpeech.OnInitListener)object2, (String)U.get(this.a.i));
                            AutoTtsService.C(textToSpeech);
                            break block11;
                        }
                        catch (Exception exception) {
                            object = c3.n.a;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Error when initializing ");
                            ((StringBuilder)object2).append((String)U.get(this.a.i));
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
                        try {
                            stringBuilder = new AudioAttributes.Builder();
                            stringBuilder = stringBuilder.setUsage(11).setContentType(1).build();
                            h0.setAudioAttributes((AudioAttributes)stringBuilder);
                            ((k0)((AutoTtsService)this.a).f.get((int)((AutoTtsService)this.a).v)).h = true;
                        }
                        catch (Exception exception) {
                            c3.n.a.d("AutoTTS", ((Object)exception).toString());
                        }
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

        public static /* synthetic */ SynthesisCallback a(e e3) {
            return e3.a;
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
                     * Unable to fully structure code
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        block27: {
                            block25: {
                                block45: {
                                    block26: {
                                        block24: {
                                            block40: {
                                                block34: {
                                                    block41: {
                                                        block42: {
                                                            block43: {
                                                                block44: {
                                                                    block35: {
                                                                        block39: {
                                                                            block36: {
                                                                                block37: {
                                                                                    block38: {
                                                                                        block33: {
                                                                                            block31: {
                                                                                                block32: {
                                                                                                    block28: {
                                                                                                        block29: {
                                                                                                            block30: {
                                                                                                                if (AutoTtsService.c(this.c.c).get() != false) return;
                                                                                                                if (AutoTtsService.e(this.c.c).get()) {
                                                                                                                    return;
                                                                                                                }
                                                                                                                AutoTtsService.h();
                                                                                                                AutoTtsService.m().remove(0);
                                                                                                                var10_1 = ((e0)AutoTtsService.m().get(0)).c();
                                                                                                                var3_2 = AutoTtsService.T;
                                                                                                                if (var3_2 != 1) break block28;
                                                                                                                var3_2 = ((e0)AutoTtsService.m().get(0)).a();
                                                                                                                var7_3 /* !! */  = "eng";
                                                                                                                if (var3_2 == 1) break block29;
                                                                                                                if (var3_2 == 2) break block30;
                                                                                                                if (var3_2 != 3) {
                                                                                                                    if (var3_2 != 4) {
                                                                                                                        if (var3_2 == 5) {
                                                                                                                            var7_3 /* !! */  = AutoTtsService.O;
                                                                                                                            var3_2 = AutoTtsService.o(this.c.c, AutoTtsService.O, "", "");
                                                                                                                            if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                                var7_3 /* !! */  = c3.n.a;
                                                                                                                                var8_4 = new StringBuilder();
                                                                                                                                var8_4.append("Language ");
                                                                                                                                var8_4.append(AutoTtsService.O);
                                                                                                                                var8_4.append(" is not supported.\n Text: ");
                                                                                                                                var8_4.append(var10_1);
                                                                                                                                var7_3 /* !! */ .d("AutoTTS", var8_4.toString());
                                                                                                                                var7_3 /* !! */  = this.c;
                                                                                                                                AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                                return;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        break block24;
                                                                                                                    } else {
                                                                                                                        var7_3 /* !! */  = AutoTtsService.M;
                                                                                                                        var3_2 = AutoTtsService.o(this.c.c, AutoTtsService.M, "", "");
                                                                                                                        if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                            var7_3 /* !! */  = c3.n.a;
                                                                                                                            var8_6 = new StringBuilder();
                                                                                                                            var8_6.append("Language ");
                                                                                                                            var8_6.append(AutoTtsService.M);
                                                                                                                            var8_6.append(" is not supported.\n Text: ");
                                                                                                                            var8_6.append(var10_1);
                                                                                                                            var7_3 /* !! */ .d("AutoTTS", var8_6.toString());
                                                                                                                            var7_3 /* !! */  = this.c;
                                                                                                                            AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                            return;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    break block24;
                                                                                                                } else {
                                                                                                                    var7_3 /* !! */  = AutoTtsService.K;
                                                                                                                    var3_2 = AutoTtsService.o(this.c.c, AutoTtsService.K, "", "");
                                                                                                                    if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                        var7_3 /* !! */  = c3.n.a;
                                                                                                                        var8_7 = new StringBuilder();
                                                                                                                        var8_7.append("Language ");
                                                                                                                        var8_7.append(AutoTtsService.K);
                                                                                                                        var8_7.append(" is not supported.\n Text: ");
                                                                                                                        var8_7.append(var10_1);
                                                                                                                        var7_3 /* !! */ .d("AutoTTS", var8_7.toString());
                                                                                                                        var7_3 /* !! */  = this.c;
                                                                                                                        AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                        return;
                                                                                                                    }
                                                                                                                }
                                                                                                                break block24;
                                                                                                            }
                                                                                                            var7_3 /* !! */  = AutoTtsService.I;
                                                                                                            var3_2 = AutoTtsService.o(this.c.c, AutoTtsService.I, "", "");
                                                                                                            if (var3_2 == -2 || var3_2 == -1) {
                                                                                                                var8_8 = c3.n.a;
                                                                                                                var7_3 /* !! */  = new StringBuilder();
                                                                                                                var7_3 /* !! */ .append("Language ");
                                                                                                                var7_3 /* !! */ .append(AutoTtsService.I);
                                                                                                                var7_3 /* !! */ .append(" is not supported.\n Text: ");
                                                                                                                var7_3 /* !! */ .append(var10_1);
                                                                                                                var8_8.d("AutoTTS", var7_3 /* !! */ .toString());
                                                                                                                var7_3 /* !! */  = this.c;
                                                                                                                AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 3);
                                                                                                                return;
                                                                                                            }
                                                                                                            break block24;
                                                                                                        }
                                                                                                        var3_2 = AutoTtsService.o(this.c.c, "eng", "", "");
                                                                                                        if (var3_2 == -2 || var3_2 == -1) {
                                                                                                            var7_3 /* !! */  = c3.n.a;
                                                                                                            var8_9 = new StringBuilder();
                                                                                                            var8_9.append("Language eng is not supported.\n Text: ");
                                                                                                            var8_9.append(var10_1);
                                                                                                            var7_3 /* !! */ .d("AutoTTS", var8_9.toString());
                                                                                                            var7_3 /* !! */  = this.c;
                                                                                                            AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 2);
                                                                                                            return;
                                                                                                        }
                                                                                                        break block24;
                                                                                                    }
                                                                                                    if (AutoTtsService.T == 4 || AutoTtsService.T == 5) break block31;
                                                                                                    var8_10 = ((e0)AutoTtsService.m().get(0)).b();
                                                                                                    var3_2 = AutoTtsService.o(this.c.c, (String)var8_10, "", "");
                                                                                                    if (var3_2 == -2) break block32;
                                                                                                    var7_3 /* !! */  = var8_10;
                                                                                                    if (var3_2 != -1) break block24;
                                                                                                }
                                                                                                var7_3 /* !! */  = c3.n.a;
                                                                                                var9_13 = new StringBuilder();
                                                                                                var9_13.append("Language ");
                                                                                                var9_13.append((String)var8_10);
                                                                                                var9_13.append(" is not supported.\n Text: ");
                                                                                                var9_13.append(var10_1);
                                                                                                var7_3 /* !! */ .d("AutoTTS", var9_13.toString());
                                                                                                var7_3 /* !! */  = this.c;
                                                                                                AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 4);
                                                                                                return;
                                                                                            }
                                                                                            var8_10 = ((e0)AutoTtsService.m().get(0)).b();
                                                                                            if (var8_10.isEmpty()) break block33;
                                                                                            var7_3 /* !! */  = var8_10;
                                                                                            if (!var8_10.equals("unknown")) break block34;
                                                                                        }
                                                                                        var8_10 = clsCLD2.d(var10_1, AutoTtsService.r(), AutoTtsService.t(), AutoTtsService.G(this.c.c));
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
                                                                                        if (var9_12 != null) break block35;
                                                                                        var3_2 = ((e0)AutoTtsService.m().get(0)).a();
                                                                                        if (var3_2 == 1) break block36;
                                                                                        if (var3_2 == 2) break block37;
                                                                                        if (var3_2 == 3) break block38;
                                                                                        if (var3_2 != 4) {
                                                                                            if (var3_2 == 5) {
                                                                                                var7_3 /* !! */  = AutoTtsService.O;
                                                                                            }
                                                                                            break block39;
                                                                                        } else {
                                                                                            var7_3 /* !! */  = AutoTtsService.M;
                                                                                        }
                                                                                        break block39;
                                                                                    }
                                                                                    var7_3 /* !! */  = AutoTtsService.K;
                                                                                    break block39;
                                                                                }
                                                                                var7_3 /* !! */  = AutoTtsService.Q;
                                                                                break block39;
                                                                            }
                                                                            var7_3 /* !! */  = AutoTtsService.P;
                                                                        }
                                                                        var8_10 = var7_3 /* !! */ ;
                                                                    }
                                                                    if (!(var7_3 /* !! */  = AutoTtsService.u(this.c.c, (String)var8_10)).isEmpty() && !var7_3 /* !! */ .equals("Disable")) break block40;
                                                                    var3_2 = ((e0)AutoTtsService.m().get(0)).a();
                                                                    if (var3_2 == 1) break block41;
                                                                    if (var3_2 == 2) break block42;
                                                                    if (var3_2 == 3) break block43;
                                                                    if (var3_2 == 4) break block44;
                                                                    if (var3_2 != 5) break block40;
                                                                    var7_3 /* !! */  = AutoTtsService.O;
                                                                    break block34;
                                                                }
                                                                var7_3 /* !! */  = AutoTtsService.M;
                                                                break block34;
                                                            }
                                                            var7_3 /* !! */  = AutoTtsService.K;
                                                            break block34;
                                                        }
                                                        var7_3 /* !! */  = AutoTtsService.Q;
                                                        break block34;
                                                    }
                                                    var7_3 /* !! */  = AutoTtsService.P;
                                                }
                                                var8_10 = var7_3 /* !! */ ;
                                            }
                                            var3_2 = AutoTtsService.o(this.c.c, (String)var8_10, "", "");
                                            if (var3_2 == -2) break block25;
                                            var7_3 /* !! */  = var8_10;
                                            if (var3_2 == -1) break block25;
                                        }
                                        var4_14 = AutoTtsService.v(this.c.c, (String)var7_3 /* !! */ );
                                        var3_2 = AutoTtsService.w(this.c.c, (String)var7_3 /* !! */ );
                                        var5_15 = AutoTtsService.x(this.c.c, (String)var7_3 /* !! */ );
                                        var1_16 = (float)AutoTtsService.y(this.c.c) / 100.0f * (float)var4_14 / 100.0f;
                                        var2_17 = (float)AutoTtsService.z(this.c.c) / 100.0f * (float)var5_15 / 100.0f;
                                        ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.i(this.c.c))).g().setSpeechRate(var1_16);
                                        ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.i(this.c.c))).g().setPitch(var2_17);
                                        var7_3 /* !! */  = new Bundle(AutoTtsService.B(this.c.c));
                                        var7_3 /* !! */ .remove("language");
                                        var7_3 /* !! */ .remove("country");
                                        var7_3 /* !! */ .remove("voiceName");
                                        var7_3 /* !! */ .remove("variant");
                                        var7_3 /* !! */ .remove("pitch");
                                        var7_3 /* !! */ .remove("rate");
                                        var7_3 /* !! */ .remove("utteranceId");
                                        if (!AutoTtsService.X) break block26;
                                        var7_3 /* !! */ .remove("streamType");
                                        var7_3 /* !! */ .remove("audioAttributes");
                                        {
                                            catch (Exception var8_5) {}
                                        }
                                    }
                                    if ((double)(var1_16 = AutoTtsService.D(this.c.c) * (float)var3_2 / 100.0f) == 0.0) ** GOTO lbl228
                                    var7_3 /* !! */ .putFloat("volume", var1_16);
lbl228:
                                    // 2 sources

                                    var8_10 = ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.i(this.c.c))).g();
                                    var11_18 = this.c;
                                    var9_12 = new e(var11_18.c, com.vnspeak.autotts.AutoTtsService$e.a(var11_18), null);
                                    var8_10.setOnUtteranceProgressListener((UtteranceProgressListener)var9_12);
                                    ((k0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.i((AutoTtsService)this.c.c))).g = true;
                                    if (!AutoTtsService.Y || (var6_19 = ((k0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.i((AutoTtsService)this.c.c))).h)) break block45;
                                    try {
                                        var8_10 = new AudioAttributes.Builder();
                                        var8_10 = var8_10.setUsage(11).setContentType(1).build();
                                        ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.i(this.c.c))).g().setAudioAttributes((AudioAttributes)var8_10);
                                        ((k0)AutoTtsService.p((AutoTtsService)this.c.c).get((int)AutoTtsService.i((AutoTtsService)this.c.c))).h = true;
                                    }
                                    catch (Exception var8_11) {
                                        c3.n.a.d("AutoTTS", var8_11.toString());
                                        break block27;
                                    }
                                }
                                if (AutoTtsService.c(this.c.c).get() != false) return;
                                if (AutoTtsService.e(this.c.c).get() != false) return;
                                var8_10 = c3.n.a;
                                var9_12 = new StringBuilder();
                                var9_12.append("speak 2: ");
                                var9_12.append(var10_1);
                                var8_10.c("AutoTTS", var9_12.toString());
                                var9_12 = ((k0)AutoTtsService.p(this.c.c).get(AutoTtsService.i(this.c.c))).g();
                                var8_10 = new StringBuilder();
                                var8_10.append(AutoTtsService.j());
                                var8_10.append("_");
                                var8_10.append(AutoTtsService.f());
                                if (var9_12.speak((CharSequence)var10_1, 0, (Bundle)var7_3 /* !! */ , var8_10.toString()) == 0) return;
                                c3.n.a.d("AutoTTS", "Speaking failed!!!");
                                var7_3 /* !! */  = this.c;
                                AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 5);
                                return;
                            }
                            var9_12 = c3.n.a;
                            var7_3 /* !! */  = new StringBuilder();
                            var7_3 /* !! */ .append("Language ");
                            var7_3 /* !! */ .append((String)var8_10);
                            var7_3 /* !! */ .append(" is not supported.\n Text: ");
                            var7_3 /* !! */ .append(var10_1);
                            var9_12.d("AutoTTS", var7_3 /* !! */ .toString());
                            var7_3 /* !! */  = this.c;
                            AutoTtsService.k(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 14);
                            return;
                        }
                        var9_12 = c3.n.a;
                        var7_3 /* !! */  = new StringBuilder();
                        var7_3 /* !! */ .append("onDone Error: ");
                        var7_3 /* !! */ .append(var8_5.getMessage());
                        var9_12.d("AutoTTS", var7_3 /* !! */ .toString());
                        var7_3 /* !! */  = this.c;
                        AutoTtsService.q(var7_3 /* !! */ .c, com.vnspeak.autotts.AutoTtsService$e.a((e)var7_3 /* !! */ ), 6);
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

