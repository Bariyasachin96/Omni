/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.speech.tts.TextToSpeech
 *  android.speech.tts.TextToSpeech$EngineInfo
 *  android.speech.tts.TextToSpeech$OnInitListener
 *  android.speech.tts.Voice
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.vnspeak.autotts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import c3.k;
import c3.l;
import c3.m;
import c3.u;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.b;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class NewSettingsActivity
extends AppCompatActivity {
    public LinearLayout D;
    public TextView E;
    public TextToSpeech F = null;
    public ViewPager2 G;
    public c3.j H;
    public Context I;
    public Map J = new HashMap();
    public Handler K;
    public int L = 0;
    public boolean M = false;
    public Handler N = new Handler(Looper.getMainLooper());
    public final List O = new ArrayList();

    public static /* synthetic */ boolean k0(NewSettingsActivity newSettingsActivity, boolean bl) {
        newSettingsActivity.M = bl;
        return bl;
    }

    public final void A0(l l3, Set object) {
        if (object != null && !object.isEmpty()) {
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                Voice voice = (Voice)iterator.next();
                object = voice.getName();
                if (((String)object).isEmpty() || c3.k.b(voice.getLocale(), l3.b, (String)object)) continue;
                c3.k.a((Context)this, voice.getLocale(), l3);
                c3.k.b(voice.getLocale(), l3.b, (String)object);
            }
        }
    }

    public final void B0() {
        Exception exception2;
        c3.k.b.clear();
        c3.k.d.clear();
        Object object = new Intent("android.intent.action.TTS_SERVICE");
        PackageManager packageManager = this.I.getPackageManager();
        ArrayList<List> arrayList = new ArrayList<List>();
        arrayList.add(packageManager.queryIntentServices((Intent)object, 131072));
        arrayList.add(packageManager.queryIntentServices((Intent)object, 128));
        int n3 = 0;
        arrayList.add(packageManager.queryIntentServices((Intent)object, 0));
        int n4 = arrayList.size();
        block7: while (n3 < n4) {
            Object object2;
            Object object3;
            object = arrayList.get(n3);
            int n5 = n3 + 1;
            try {
                object = ((List)object).iterator();
            }
            catch (Exception exception2) {
            }
            while (true) {
                n3 = n5;
                if (!object.hasNext()) continue block7;
                object3 = (ResolveInfo)object.next();
                object2 = object3.serviceInfo;
                if (object2 == null) continue;
                break;
            }
            {
                object2 = ((ServiceInfo)object2).packageName;
                if (((String)object2).contains("autotts") || this.J.containsKey(object2)) continue;
                object3 = object3.loadLabel(packageManager).toString();
                l l3 = new l((String)object3, (String)object2);
                this.J.put(object2, l3);
                c3.k.b.add(l3);
                continue;
            }
        }
        return;
        c3.k.a.e("AutoTTS", "Error in service discovery", exception2);
    }

    public final String C0() {
        return this.getSharedPreferences("auto_tts_settings", 0).getString("license_text", "");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void D0() {
        block17: {
            Object object;
            int n3 = this.L;
            int n4 = 1;
            this.L = n3 + 1;
            List list = c3.k.b;
            synchronized (list) {
                if (this.L < list.size()) {
                    while ((n3 = ++this.L) < (object = c3.k.b).size() && ((l)object.get(this.L)).a()) {
                    }
                    if (this.L < object.size()) {
                        n4 = 0;
                    }
                }
                // MONITOREXIT @DISABLED, blocks:[0, 6] lbl12 : MonitorExitStatement: MONITOREXIT : var3_3
                if (n4 != 0) break block17;
                n3 = this.L;
                list = c3.k.b;
            }
            synchronized (list) {
                if (n3 >= list.size()) {
                    this.K.removeCallbacksAndMessages(null);
                    this.z0();
                    return;
                }
                // MONITOREXIT @DISABLED, blocks:[1, 7] lbl23 : MonitorExitStatement: MONITOREXIT : var3_3
                object = ((l)list.get((int)n3)).b;
                this.M = false;
            }
            this.N.postDelayed(new Runnable(this, n3){
                public final int c;
                public final NewSettingsActivity d;
                {
                    this.d = newSettingsActivity;
                    this.c = n3;
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (!this.d.M) {
                        List list = this.d.O;
                        synchronized (list) {
                            this.d.O.add(this.c);
                        }
                    }
                    this.d.D0();
                }
            }, 30000L);
            this.runOnUiThread(new Runnable(this, n3){
                public final int c;
                public final NewSettingsActivity d;
                {
                    this.d = newSettingsActivity;
                    this.c = n3;
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    List list = c3.k.b;
                    synchronized (list) {
                        Throwable throwable2;
                        block4: {
                            block3: {
                                try {
                                    if (this.c >= list.size()) break block3;
                                    String string = this.d.getResources().getString(2131623989);
                                    this.d.E.setText((CharSequence)String.format("%s %s... (2)", string, ((l)list.get((int)this.c)).b));
                                }
                                catch (Throwable throwable2) {
                                    break block4;
                                }
                            }
                            return;
                        }
                        throw throwable2;
                    }
                }
            });
            synchronized (list) {
                Throwable throwable22;
                block19: {
                    block18: {
                        try {
                            TextToSpeech textToSpeech;
                            n4 = list.size();
                            if (n3 >= n4) break block18;
                            Context context = this.getApplicationContext();
                            object = new j(this, null);
                            this.F = textToSpeech = new TextToSpeech(context, (TextToSpeech.OnInitListener)object, ((l)list.get((int)n3)).b);
                            break block18;
                        }
                        catch (Throwable throwable22) {
                            break block19;
                        }
                        catch (Exception exception) {
                            object = c3.k.a;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Error when initialize ");
                            stringBuilder.append(((l)c3.k.b.get((int)n3)).b);
                            stringBuilder.append("\n");
                            stringBuilder.append(exception.getMessage());
                            ((m)object).d("AutoTTS", stringBuilder.toString());
                            this.D0();
                        }
                    }
                    return;
                }
                throw throwable22;
            }
        }
        this.K.removeCallbacksAndMessages(null);
        this.z0();
        return;
        {
            catch (Throwable throwable) {}
            throw throwable;
        }
        {
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final void E0(c3.j j3, TabLayout tabLayout, int n3) {
        n3 = 0;
        while (n3 < j3.f()) {
            TabLayout.f f3 = tabLayout.B(n3);
            CharSequence charSequence = j3.T(n3);
            int n4 = n3 + 1;
            charSequence = this.getString(2131624019, new Object[]{charSequence, n4, j3.f()});
            n3 = n4;
            if (f3 == null) continue;
            f3.m(charSequence);
            n3 = n4;
        }
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.I = this;
        this.setContentView(2131427435);
        this.H = new c3.j(this);
        object = (ViewPager2)this.findViewById(2131231300);
        this.G = object;
        ((ViewPager2)((Object)object)).setOffscreenPageLimit(4);
        this.G.setAdapter(this.H);
        object = (TabLayout)this.findViewById(2131231236);
        new b((TabLayout)((Object)object), this.G, new b.b(this){
            public final NewSettingsActivity a;
            {
                this.a = newSettingsActivity;
            }

            @Override
            public void a(TabLayout.f f3, int n3) {
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 3) {
                                if (n3 != 4) {
                                    return;
                                }
                                f3.p(2131165341);
                                f3.s(2131624216);
                                f3.m(this.a.getString(2131624216));
                                return;
                            }
                            f3.p(2131165343);
                            f3.s(2131624215);
                            f3.m(this.a.getString(2131624215));
                            return;
                        }
                        f3.p(2131165344);
                        f3.s(2131624214);
                        f3.m(this.a.getString(2131624214));
                        return;
                    }
                    f3.p(2131165340);
                    f3.s(2131624213);
                    f3.m(this.a.getString(2131624213));
                    return;
                }
                f3.p(2131165342);
                f3.s(2131624212);
                f3.m(this.a.getString(2131624212));
            }
        }).a();
        this.E0(this.H, (TabLayout)((Object)object), 0);
        this.G.g(new ViewPager2.i(this, (TabLayout)((Object)object)){
            public final TabLayout a;
            public final NewSettingsActivity b;
            {
                this.b = newSettingsActivity;
                this.a = tabLayout;
            }

            @Override
            public void c(int n3) {
                Exception exception2;
                block3: {
                    super.c(n3);
                    Object object = this.b;
                    ((NewSettingsActivity)object).E0(((NewSettingsActivity)object).H, this.a, n3);
                    try {
                        FragmentManager fragmentManager = this.b.P();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("f");
                        ((StringBuilder)object).append(n3);
                        object = fragmentManager.i0(((StringBuilder)object).toString());
                        if (object instanceof c3.i) {
                            ((c3.i)object).F0();
                            return;
                        }
                    }
                    catch (Exception exception2) {
                        break block3;
                    }
                    return;
                }
                exception2.printStackTrace();
            }
        });
        c3.k.q((Context)this);
        c3.k.p((Context)this);
        c3.k.r((Context)this);
        this.D = (LinearLayout)this.findViewById(2131231015);
        object = this.C0();
        if (((String)object).compareTo("Valid license") != 0 && !((String)object).isEmpty()) {
            ((TextView)this.findViewById(2131231272)).setText((CharSequence)String.format("Auto TTS (%s)", object));
        }
        this.E = (TextView)this.findViewById(2131230890);
        object = new Handler(Looper.getMainLooper());
        this.K = object;
        object.postDelayed(new Runnable(this){
            public final NewSettingsActivity c;
            {
                this.c = newSettingsActivity;
            }

            @Override
            public void run() {
                this.c.z0();
            }
        }, 180000L);
        this.B0();
        this.F = new TextToSpeech((Context)this, (TextToSpeech.OnInitListener)new i(this, null), "com.vnspeak.autotts");
    }

    @Override
    public void onDestroy() {
        this.K.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        c3.k.u((Context)this);
        super.onPause();
    }

    public final void y0() {
        this.L = 0;
        this.M = false;
        this.N.postDelayed(new Runnable(this){
            public final NewSettingsActivity c;
            {
                this.c = newSettingsActivity;
            }

            @Override
            public void run() {
                if (!this.c.M) {
                    this.c.O.add(this.c.L);
                }
                this.c.D0();
            }
        }, 30000L);
        this.runOnUiThread(new Runnable(this){
            public final NewSettingsActivity c;
            {
                this.c = newSettingsActivity;
            }

            @Override
            public void run() {
                String string = this.c.getResources().getString(2131623989);
                this.c.E.setText((CharSequence)String.format("%s %s... (3)", string, ((l)c3.k.b.get((int)((NewSettingsActivity)this.c).L)).b));
            }
        });
        this.F = new TextToSpeech((Context)this, (TextToSpeech.OnInitListener)new j(this, null), ((l)c3.k.b.get((int)this.L)).b);
    }

    public final void z0() {
        List<Integer> list;
        int n3;
        this.K.removeCallbacksAndMessages(null);
        List list2 = this.O.stream().distinct().collect(Collectors.toList());
        this.O.clear();
        this.O.addAll(list2);
        this.O.sort(Comparator.reverseOrder());
        list2 = new ArrayList();
        for (n3 = 0; n3 < this.O.size(); ++n3) {
            int n4 = (Integer)this.O.get(n3);
            if (n4 >= (list = c3.k.b).size()) continue;
            list2.add(((l)list.get((int)((Integer)this.O.get((int)n3)).intValue())).b);
            list.remove((Integer)this.O.get(n3));
        }
        list = new ArrayList();
        for (n3 = 0; n3 < c3.k.d.size(); ++n3) {
            if (!list2.contains(((u)c3.k.d.get((int)n3)).d.b)) continue;
            list.add(n3);
        }
        list.sort(Comparator.reverseOrder());
        for (n3 = 0; n3 < list.size(); ++n3) {
            c3.k.d.remove((Integer)list.get(n3));
        }
        list2 = c3.k.c;
        list2.clear();
        list2.addAll(c3.k.h((Context)this, false));
        c3.k.u((Context)this);
        c3.k.g = new TextToSpeech((Context)this, null, "com.vnspeak.autotts");
        this.runOnUiThread(new Runnable(this){
            public final NewSettingsActivity c;
            {
                this.c = newSettingsActivity;
            }

            @Override
            public void run() {
                this.c.D.setVisibility(8);
                this.c.G.setVisibility(0);
            }
        });
    }

    public class i
    implements TextToSpeech.OnInitListener {
        public final NewSettingsActivity a;

        public i(NewSettingsActivity newSettingsActivity) {
            this.a = newSettingsActivity;
        }

        public /* synthetic */ i(NewSettingsActivity newSettingsActivity, a a4) {
            this(newSettingsActivity);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onInit(int n3) {
            List list;
            if (n3 == 0 && this.a.F != null) {
                list = this.a.F.getEngines();
                List list2 = c3.k.b;
                synchronized (list2) {
                }
            } else {
                Toast.makeText((Context)this.a.I, (CharSequence)this.a.getResources().getString(2131623978), (int)1).show();
                this.a.z0();
                return;
            }
            {
                try {
                    for (n3 = 0; n3 < list.size(); ++n3) {
                        String string = ((TextToSpeech.EngineInfo)list.get((int)n3)).name;
                        if (this.a.J.containsKey(string) || string.contains("autotts")) continue;
                        List list3 = c3.k.b;
                        l l3 = new l(((TextToSpeech.EngineInfo)list.get((int)n3)).label, string);
                        list3.add(l3);
                    }
                    this.a.F.shutdown();
                    this.a.y0();
                    return;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }
    }

    public class j
    implements TextToSpeech.OnInitListener {
        public final NewSettingsActivity a;

        public j(NewSettingsActivity newSettingsActivity) {
            this.a = newSettingsActivity;
        }

        public /* synthetic */ j(NewSettingsActivity newSettingsActivity, a a4) {
            this(newSettingsActivity);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onInit(int n3) {
            block15: {
                block14: {
                    Object object;
                    block11: {
                        Exception exception2;
                        block12: {
                            block10: {
                                NewSettingsActivity.k0(this.a, true);
                                this.a.N.removeCallbacksAndMessages(null);
                                if (n3 != 0) break block14;
                                try {
                                    object = this.a.F.getClass().getDeclaredField("mCurrentEngine");
                                    ((AccessibleObject)object).setAccessible(true);
                                    if (((Field)object).get(this.a.F) == null) break block10;
                                    object = ((Field)object).get(this.a.F);
                                    Objects.requireNonNull(object);
                                    object = object.toString();
                                    break block11;
                                }
                                catch (Exception exception2) {
                                    break block12;
                                }
                            }
                            object = ((l)c3.k.b.get((int)((NewSettingsActivity)this.a).L)).b;
                            break block11;
                        }
                        c3.k.a.e("AutoTTS", "Reflection failed", exception2);
                        n3 = this.a.L;
                        object = c3.k.b;
                        object = n3 < object.size() ? ((l)object.get((int)((NewSettingsActivity)this.a).L)).b : "";
                    }
                    n3 = this.a.L;
                    List list = c3.k.b;
                    if (n3 < list.size()) {
                        if (((String)object).equals(((l)list.get((int)((NewSettingsActivity)this.a).L)).b)) {
                            try {
                                object = this.a.F.getVoices();
                                if (object != null) {
                                    NewSettingsActivity newSettingsActivity = this.a;
                                    newSettingsActivity.A0((l)list.get(newSettingsActivity.L), (Set)object);
                                }
                            }
                            catch (Exception exception) {
                                c3.k.a.d("AutoTTS", exception.getMessage());
                            }
                        } else {
                            this.a.O.add(this.a.L);
                        }
                    }
                    try {
                        this.a.F.shutdown();
                    }
                    catch (Exception exception) {
                        c3.k.a.d("AutoTTS", exception.getMessage());
                    }
                    break block15;
                }
                this.a.O.add(this.a.L);
            }
            this.a.D0();
        }
    }
}

