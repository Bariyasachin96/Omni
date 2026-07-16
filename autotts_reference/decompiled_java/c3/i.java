/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.PowerManager
 *  android.os.Process
 *  android.text.Html
 *  android.text.Spanned
 *  android.text.method.LinkMovementMethod
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RadioButton
 *  android.widget.ScrollView
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
 */
package c3;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Process;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.b;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import c3.d;
import c3.e;
import c3.f;
import c3.g;
import c3.h;
import c3.k;
import c3.m;
import c3.r;
import c3.s;
import c3.t;
import c3.u;
import c3.y;
import c3.z;
import com.vnspeak.autotts.AutoTtsService;
import e0.a;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class i
extends Fragment {
    public static int T0;
    public SeekBar A0;
    public LinearLayout B0;
    public LinearLayout C0;
    public LinearLayout D0;
    public Spinner E0;
    public Spinner F0;
    public Button G0;
    public RadioButton H0;
    public RadioButton I0;
    public RadioButton J0;
    public RadioButton K0;
    public RadioButton L0;
    public int M0;
    public int N0;
    public int O0;
    public b P0;
    public b Q0;
    public r.d R0;
    public r.a S0;
    public int e0 = 0;
    public View f0;
    public View g0;
    public View h0;
    public View i0;
    public View j0;
    public ListView k0;
    public SearchView l0;
    public q0 m0;
    public ArrayList n0;
    public ArrayList o0;
    public ArrayList p0;
    public Button q0;
    public boolean r0 = false;
    public Spinner s0;
    public Spinner t0;
    public Spinner u0;
    public Spinner v0;
    public Spinner w0;
    public Spinner x0;
    public SeekBar y0;
    public SeekBar z0;

    public static i D2(int n3) {
        i i3 = new i();
        Bundle bundle = new Bundle();
        bundle.putInt("section_number", n3);
        i3.t1(bundle);
        return i3;
    }

    public static /* synthetic */ void E1(Boolean bl) {
        if (bl.booleanValue()) {
            c3.k.a.c("TTS", "Notification permission granted");
            return;
        }
        c3.k.a.c("TTS", "Notification permission denied");
    }

    public static /* synthetic */ void F1(m m3, CompoundButton compoundButton, boolean bl) {
        m3.j(bl);
    }

    public static /* synthetic */ void G1(i i3, r.d d3, r.a a4) {
        i3.R0 = d3;
        i3.S0 = a4;
        i3.F2(d3.b());
    }

    public static /* synthetic */ void H1(i i3, Uri uri) {
        if (uri != null) {
            i3.x2(i3.G2(uri));
            return;
        }
        i3.getClass();
    }

    public static /* synthetic */ boolean j2(i i3, boolean bl) {
        i3.r0 = bl;
        return bl;
    }

    private void onRadioButtonClicked(View object) {
        boolean bl = ((RadioButton)object).isChecked();
        switch (object.getId()) {
            default: {
                break;
            }
            case 2131230825: {
                if (!bl) break;
                AutoTtsService.L = 0;
                this.B0.setVisibility(8);
                this.C0.setVisibility(8);
                this.D0.setVisibility(8);
                return;
            }
            case 2131230824: {
                if (!bl) break;
                AutoTtsService.L = 4;
                this.B0.setVisibility(8);
                this.C0.setVisibility(8);
                this.D0.setVisibility(0);
                object = c3.k.c;
                object.clear();
                object.addAll(c3.k.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.m(null));
                object.setDropDownViewResource(0x1090009);
                this.w0.setAdapter((SpinnerAdapter)object);
                int n3 = c3.k.g(AutoTtsService.H);
                if (n3 != -1) {
                    this.w0.setSelection(n3);
                }
                this.x0.setAdapter((SpinnerAdapter)object);
                n3 = c3.k.g(AutoTtsService.I);
                if (n3 == -1) break;
                this.x0.setSelection(n3);
                return;
            }
            case 2131230821: {
                if (!bl) break;
                AutoTtsService.L = 3;
                this.B0.setVisibility(0);
                this.C0.setVisibility(8);
                this.D0.setVisibility(8);
                object = c3.k.c;
                object.clear();
                object.addAll(c3.k.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.m("com.google.android.tts"));
                object.setDropDownViewResource(0x1090009);
                this.s0.setAdapter((SpinnerAdapter)object);
                int n4 = c3.k.g(AutoTtsService.C);
                if (n4 == -1) break;
                this.s0.setSelection(n4);
                return;
            }
            case 2131230820: {
                if (!bl) break;
                AutoTtsService.L = 1;
                this.B0.setVisibility(8);
                this.C0.setVisibility(0);
                this.D0.setVisibility(8);
                object = c3.k.c;
                object.clear();
                object.addAll(c3.k.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.m(null));
                object.setDropDownViewResource(0x1090009);
                this.t0.setAdapter((SpinnerAdapter)object);
                int n5 = c3.k.g(AutoTtsService.D);
                if (n5 == -1) break;
                this.t0.setSelection(n5);
                return;
            }
            case 2131230819: {
                if (!bl) break;
                AutoTtsService.L = 2;
                this.B0.setVisibility(0);
                this.C0.setVisibility(8);
                this.D0.setVisibility(8);
                object = c3.k.c;
                object.clear();
                object.addAll(c3.k.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.m(null));
                object.setDropDownViewResource(0x1090009);
                this.s0.setAdapter((SpinnerAdapter)object);
                int n6 = c3.k.g(AutoTtsService.C);
                if (n6 == -1) break;
                this.s0.setSelection(n6);
            }
        }
    }

    /*
     * Exception decompiling
     */
    public static boolean y2(Context var0, String var1_2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 4[TRYBLOCK] [4 : 186->336)] java.lang.Exception
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

    public boolean A2(Context context, String string) {
        context = context.getPackageManager();
        try {
            context.getPackageInfo(string, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public final boolean B2(String string) {
        if (this.m0 == null) {
            return true;
        }
        for (int i3 = 0; i3 < this.m0.getCount(); ++i3) {
            String string2 = (String)this.m0.getItem(i3);
            if (string2 == null || !string2.toLowerCase().contains(string.toLowerCase())) continue;
            return true;
        }
        return true;
    }

    public final boolean C2(String string) {
        try {
            this.n1().getPackageManager().getPackageInfo(string, 1);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public void E2() {
        this.Q0.a(new String[]{"text/xml", "application/xml"});
    }

    @Override
    public void F0() {
        super.F0();
        int n3 = this.e0;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 == 3) {
                    this.O2();
                }
            } else {
                this.M2();
            }
        } else {
            this.N2();
        }
        if (this.R0 != null && this.S0 != null) {
            this.u2();
        }
    }

    public final void F2(String string) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("market://details?id=");
            stringBuilder.append(string);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString()));
            intent.addFlags(0x10000000);
            this.B1(intent);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            block4: {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("https://play.google.com/store/apps/details?id=");
                    stringBuilder.append(string);
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString()));
                    intent.addFlags(0x10000000);
                    this.B1(intent);
                }
                catch (ActivityNotFoundException activityNotFoundException2) {
                    Toast.makeText((Context)this.n1(), (CharSequence)"Cannot open Play Store", (int)0).show();
                    r.a a4 = this.S0;
                    if (a4 == null) break block4;
                    a4.a(false);
                    this.v2();
                }
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final String G2(Uri object) {
        Throwable throwable4222222;
        IOException iOException2222222;
        Throwable throwable22222222;
        BufferedReader bufferedReader;
        block15: {
            StringBuilder stringBuilder = new StringBuilder();
            object = this.n1().getContentResolver().openInputStream((Uri)object);
            Object object2 = new InputStreamReader((InputStream)object);
            bufferedReader = new BufferedReader((Reader)object2);
            try {
                while ((object2 = bufferedReader.readLine()) != null) {
                    stringBuilder.append((String)object2);
                    stringBuilder.append("\n");
                }
            }
            catch (Throwable throwable22222222) {
                break block15;
            }
            bufferedReader.close();
            if (object == null) return stringBuilder.toString();
            ((InputStream)object).close();
            return stringBuilder.toString();
            {
                catch (IOException iOException2222222) {}
            }
        }
        try {
            bufferedReader.close();
            throw throwable22222222;
        }
        catch (Throwable throwable3) {
            try {
                throwable22222222.addSuppressed(throwable3);
                throw throwable22222222;
            }
            catch (Throwable throwable4222222) {}
        }
        if (object == null) throw throwable4222222;
        try {
            ((InputStream)object).close();
            throw throwable4222222;
        }
        catch (Throwable throwable5) {
            throwable4222222.addSuppressed(throwable5);
            throw throwable4222222;
        }
        iOException2222222.printStackTrace();
        return null;
    }

    public final void H2() {
        Context context = this.n1();
        if (!((PowerManager)context.getSystemService("power")).isIgnoringBatteryOptimizations(context.getPackageName())) {
            try {
                Intent intent = new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS");
                this.B1(intent);
                Toast.makeText((Context)context, (CharSequence)"Find Auto TTS & all third-party tts engines and select 'Unrestricted'", (int)1).show();
                return;
            }
            catch (Exception exception) {
                Toast.makeText((Context)context, (CharSequence)"Failed to open battery settings", (int)1).show();
            }
        }
    }

    public final void I1() {
        int n3;
        SeekBar seekBar = (SeekBar)this.h0.findViewById(2131231143);
        this.O0 = n3 = this.O0 - 5;
        if (n3 < 10) {
            this.O0 = 10;
        }
        d d3 = (d)c3.k.c.get(T0);
        d3.e = n3 = this.O0;
        seekBar.setProgress(n3);
    }

    public final void I2() {
        if (Build.VERSION.SDK_INT >= 33 && a.a(this.n1(), "android.permission.POST_NOTIFICATIONS") != 0) {
            this.P0.a("android.permission.POST_NOTIFICATIONS");
        }
    }

    @Override
    public void J0(View view, Bundle bundle) {
        super.J0(view, bundle);
        int n3 = this.e0;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        if (n3 == 5) {
                            this.L2();
                        }
                    } else {
                        this.K2();
                    }
                } else {
                    this.O2();
                }
            } else {
                this.M2();
            }
        } else {
            this.N2();
        }
        if (this.R0 != null && this.S0 != null) {
            this.u2();
        }
    }

    public final void J1() {
        int n3;
        SeekBar seekBar = (SeekBar)this.h0.findViewById(2131231143);
        this.O0 = n3 = this.O0 + 5;
        if (n3 > seekBar.getMax()) {
            this.O0 = seekBar.getMax();
        }
        d d3 = (d)c3.k.c.get(T0);
        d3.e = n3 = this.O0;
        seekBar.setProgress(n3);
    }

    public final void J2() {
        Intent intent = this.n1().getPackageManager().getLaunchIntentForPackage(this.n1().getPackageName());
        intent.addFlags(0x14000000);
        this.B1(intent);
        Process.killProcess((int)Process.myPid());
        System.exit(0);
    }

    public final void K1() {
        int n3;
        SeekBar seekBar = (SeekBar)this.h0.findViewById(2131231212);
        this.M0 = n3 = this.M0 - 5;
        if (n3 < 10) {
            this.M0 = 10;
        }
        d d3 = (d)c3.k.c.get(T0);
        d3.c = n3 = this.M0;
        seekBar.setProgress(n3);
    }

    public final void K2() {
        CheckBox checkBox = (CheckBox)this.i0.findViewById(2131231154);
        checkBox.setChecked(AutoTtsService.P);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.P = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230961);
        checkBox.setChecked(AutoTtsService.Q);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.Q = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131231200);
        checkBox.setChecked(AutoTtsService.R);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.R = bl;
                if (bl) {
                    this.a.I2();
                }
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230914);
        checkBox.setChecked(AutoTtsService.S);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.S = bl;
            }
        });
        ((Button)this.i0.findViewById(2131230915)).setOnClickListener(new View.OnClickListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onClick(View view) {
                this.c.H2();
            }
        });
        ((Button)this.i0.findViewById(2131230947)).setOnClickListener(new View.OnClickListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onClick(View view) {
                c3.z.d(this.c.n1());
            }
        });
        ((Button)this.i0.findViewById(2131230992)).setOnClickListener(new View.OnClickListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onClick(View view) {
                this.c.E2();
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230937);
        m m3 = c3.m.f(this.n1());
        checkBox.setChecked(m3.g());
        checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new g(m3));
        ((Button)this.i0.findViewById(2131231193)).setOnClickListener(new View.OnClickListener(this, m3){
            public final m c;
            public final i d;
            {
                this.d = i3;
                this.c = m3;
            }

            public void onClick(View view) {
                this.c.k(this.d.n1());
            }
        });
        ((Button)this.i0.findViewById(2131230869)).setOnClickListener(new View.OnClickListener(this, m3){
            public final m c;
            public final i d;
            {
                this.d = i3;
                this.c = m3;
            }

            public void onClick(View view) {
                this.c.a();
            }
        });
    }

    public final void L1() {
        int n3;
        Object object = (SeekBar)this.h0.findViewById(2131231212);
        this.M0 = n3 = this.M0 + 5;
        if (n3 > object.getMax()) {
            this.M0 = object.getMax();
        }
        object.setProgress(this.M0);
        n3 = T0;
        if (n3 >= 0 && n3 < (object = c3.k.c).size()) {
            ((d)object.get((int)c3.i.T0)).c = this.M0;
        }
    }

    public final void L2() {
        Spanned spanned = Html.fromHtml((String)this.Q(2131623983), (int)0);
        TextView textView = (TextView)this.j0.findViewById(2131231010);
        textView.setText((CharSequence)spanned);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public final void M1() {
        List list;
        int n3;
        SeekBar seekBar = (SeekBar)this.h0.findViewById(2131231309);
        this.N0 = n3 = this.N0 - 5;
        if (n3 < 10) {
            this.N0 = 10;
        }
        if ((n3 = T0) >= 0 && n3 < (list = c3.k.c).size()) {
            ((d)list.get((int)c3.i.T0)).d = this.N0;
        }
        seekBar.setProgress(this.N0);
    }

    public final void M2() {
        LinearLayout linearLayout = (LinearLayout)this.g0.findViewById(2131231017);
        LinearLayout linearLayout2 = (LinearLayout)this.g0.findViewById(2131231018);
        int n3 = AutoTtsService.L;
        int n4 = 0;
        if (n3 != 1 && AutoTtsService.L != 0) {
            List list = c3.k.c;
            list.clear();
            list.addAll(c3.k.h(this.p(), false));
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
            this.k0 = (ListView)this.g0.findViewById(2131231020);
            this.l0 = (SearchView)this.g0.findViewById(2131231181);
            if (AutoTtsService.L != 2 && AutoTtsService.L != 4) {
                this.n0 = c3.k.m("com.google.android.tts");
                this.o0 = c3.k.k("com.google.android.tts");
                this.p0 = c3.k.l("com.google.android.tts");
            } else {
                this.n0 = c3.k.m(null);
                this.o0 = c3.k.k(null);
                this.p0 = c3.k.l(null);
            }
            c3.k.y(this.n1());
            this.m0 = new q0(this.p(), this.n0, this.p0);
            this.k0.setChoiceMode(2);
            this.k0.setAdapter((ListAdapter)this.m0);
            while (n4 < this.n0.size()) {
                this.k0.setItemChecked(n4, ((Boolean)this.p0.get(n4)).booleanValue());
                ++n4;
            }
            this.l0.setOnQueryTextListener(new SearchView.m(this){
                public final i a;
                {
                    this.a = i3;
                }

                @Override
                public boolean a(String string) {
                    this.a.s2();
                    return true;
                }

                @Override
                public boolean b(String string) {
                    return false;
                }
            });
            this.k0.setOnItemClickListener(new AdapterView.OnItemClickListener(this){
                public final i c;
                {
                    this.c = i3;
                }

                public void onItemClick(AdapterView object, View object2, int n3, long l3) {
                    boolean bl = this.c.k0.getCheckedItemPositions().get(n3);
                    int n4 = this.c.m0.c(n3);
                    if (n4 >= 0) {
                        this.c.p0.set(n4, bl);
                        object2 = c3.k.n();
                        object = this.c.t2(n4);
                        int n5 = ((ArrayList)object).size();
                        n4 = 0;
                        block0: while (n4 < n5) {
                            Object object3 = ((ArrayList)object).get(n4);
                            int n6 = n4 + 1;
                            if (((ArrayList)object2).contains(object3 = (String)object3)) {
                                this.c.k0.setItemChecked(n3, true);
                                n4 = n6;
                                continue;
                            }
                            int n7 = 0;
                            while (true) {
                                List list = c3.k.c;
                                n4 = n6;
                                if (n7 >= list.size()) continue block0;
                                if (((String)object3).equalsIgnoreCase(((d)list.get((int)n7)).b)) {
                                    ((d)list.get((int)n7)).i = bl ^ true;
                                    c3.k.z(this.c.n1());
                                    n4 = n6;
                                    continue block0;
                                }
                                ++n7;
                            }
                        }
                    }
                }
            });
        } else {
            linearLayout.setVisibility(8);
            linearLayout2.setVisibility(0);
        }
        ((Button)this.g0.findViewById(2131231187)).setOnClickListener(new View.OnClickListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onClick(View object) {
                int n3;
                for (n3 = 0; n3 < this.c.m0.getCount(); ++n3) {
                    this.c.k0.setItemChecked(n3, true);
                    int n4 = this.c.m0.c(n3);
                    if (n4 < 0) continue;
                    this.c.p0.set(n4, Boolean.TRUE);
                }
                for (n3 = 0; n3 < (object = c3.k.c).size(); ++n3) {
                    if (!this.c.B2(((d)object.get((int)n3)).b)) continue;
                    ((d)object.get((int)n3)).i = false;
                }
                c3.k.z(this.c.n1());
            }
        });
        ((Button)this.g0.findViewById(2131230868)).setOnClickListener(new View.OnClickListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onClick(View object) {
                int n3;
                int n4;
                int n5 = 0;
                for (n4 = 0; n4 < this.c.m0.getCount(); ++n4) {
                    this.c.k0.setItemChecked(n4, false);
                    n3 = this.c.m0.c(n4);
                    if (n3 < 0) continue;
                    this.c.p0.set(n3, Boolean.FALSE);
                }
                for (n4 = 0; n4 < (object = c3.k.c).size(); ++n4) {
                    if (!this.c.B2(((d)object.get((int)n4)).b)) continue;
                    ((d)object.get((int)n4)).i = true;
                }
                object = c3.k.n();
                n3 = 0;
                while (true) {
                    List list = c3.k.c;
                    if (n3 >= list.size()) break;
                    if (((ArrayList)object).contains(((d)list.get((int)n3)).b)) {
                        ((d)list.get((int)n3)).i = false;
                    }
                    ++n3;
                }
                for (n4 = n5; n4 < this.c.o0.size(); ++n4) {
                    if (!((ArrayList)object).contains(this.c.o0.get(n4))) continue;
                    this.c.p0.set(n4, Boolean.TRUE);
                    n3 = this.c.m0.b(n4);
                    if (n3 < 0) continue;
                    this.c.k0.setItemChecked(n3, true);
                }
                c3.k.z(this.c.n1());
                if (this.c.r0) {
                    this.c.s2();
                }
            }
        });
        linearLayout = (Button)this.g0.findViewById(2131231276);
        this.q0 = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onClick(View object) {
                object = this.c;
                c3.i.j2((i)object, ((i)object).r0 ^ true);
                this.c.Q2();
                this.c.s2();
            }
        });
        this.Q2();
    }

    public final void N1() {
        List list;
        int n3;
        SeekBar seekBar = (SeekBar)this.h0.findViewById(2131231309);
        this.N0 = n3 = this.N0 + 5;
        if (n3 > seekBar.getMax()) {
            this.N0 = seekBar.getMax();
        }
        if ((n3 = T0) >= 0 && n3 < (list = c3.k.c).size()) {
            ((d)list.get((int)c3.i.T0)).d = this.N0;
        }
        seekBar.setProgress(this.N0);
    }

    public final void N2() {
        List list = c3.k.c;
        list.clear();
        list.addAll(c3.k.h(this.p(), false));
        this.H0 = (RadioButton)this.f0.findViewById(2131230825);
        this.I0 = (RadioButton)this.f0.findViewById(2131230820);
        this.J0 = (RadioButton)this.f0.findViewById(2131230819);
        this.K0 = (RadioButton)this.f0.findViewById(2131230821);
        this.L0 = (RadioButton)this.f0.findViewById(2131230824);
        this.K0.setEnabled(c3.t.a(this.n1()));
        this.H0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((i)object).onRadioButtonClicked((View)((i)object).H0);
            }
        });
        this.I0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((i)object).onRadioButtonClicked((View)((i)object).I0);
            }
        });
        this.J0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((i)object).onRadioButtonClicked((View)((i)object).J0);
            }
        });
        this.K0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((i)object).onRadioButtonClicked((View)((i)object).K0);
            }
        });
        this.L0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((i)object).onRadioButtonClicked((View)((i)object).L0);
            }
        });
        this.B0 = (LinearLayout)this.f0.findViewById(2131230721);
        this.C0 = (LinearLayout)this.f0.findViewById(2131230725);
        this.D0 = (LinearLayout)this.f0.findViewById(2131230728);
        list = (Spinner)this.f0.findViewById(2131230823);
        this.s0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231052);
        this.w0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231053);
        this.x0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131230927);
        this.t0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231105);
        this.u0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231150);
        this.v0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final i c;
            {
                this.c = i3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = new ArrayAdapter(this.p(), 17367048, new ArrayList<String>(Arrays.asList(this.K().getString(2131624179), this.K().getString(2131624181), this.K().getString(2131624182))));
        list.setDropDownViewResource(0x1090009);
        this.u0.setAdapter((SpinnerAdapter)list);
        this.u0.setSelection(AutoTtsService.E);
        this.v0.setAdapter((SpinnerAdapter)list);
        this.v0.setSelection(AutoTtsService.F);
        int n3 = AutoTtsService.L;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 == 4) {
                            if (this.L0.isChecked()) {
                                this.onRadioButtonClicked((View)this.L0);
                            } else {
                                this.L0.setChecked(true);
                            }
                        }
                    } else if (this.K0.isChecked()) {
                        this.onRadioButtonClicked((View)this.K0);
                    } else {
                        this.K0.setChecked(true);
                    }
                } else if (this.J0.isChecked()) {
                    this.onRadioButtonClicked((View)this.J0);
                } else {
                    this.J0.setChecked(true);
                }
            } else if (this.I0.isChecked()) {
                this.onRadioButtonClicked((View)this.I0);
            } else {
                this.I0.setChecked(true);
            }
        } else if (this.H0.isChecked()) {
            this.onRadioButtonClicked((View)this.H0);
        } else {
            this.H0.setChecked(true);
        }
        list = (CheckBox)this.f0.findViewById(2131231021);
        list.setChecked(AutoTtsService.N);
        list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.N = bl;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void O1(AdapterView object, View object2, int n3, long l3) {
        boolean bl;
        int n4;
        block16: {
            try {
                object = (Spinner)object;
                n4 = object.getId();
                bl = true;
                if (n4 == 2131230827) {
                    T0 = n3;
                    c3.k.C(this.p());
                    object2 = c3.k.c;
                    object = ((d)object2.get((int)n3)).b;
                    this.y0.setProgress(((d)object2.get((int)n3)).c);
                    this.A0.setProgress(((d)object2.get((int)n3)).e);
                    this.z0.setProgress(((d)object2.get((int)n3)).d);
                    this.w2((String)object);
                    this.R2();
                    if (c3.k.e.isEmpty()) {
                        this.G0.setEnabled(false);
                        return;
                    }
                    object = this.G0;
                    if (((u)c3.k.e.get((int)0)).d == null) {
                        bl = false;
                    }
                    object.setEnabled(bl);
                    return;
                }
                if (object.getId() == 2131230823) {
                    n3 = this.s0.getSelectedItemPosition();
                    AutoTtsService.C = ((d)c3.k.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231052) {
                    n3 = this.w0.getSelectedItemPosition();
                    AutoTtsService.H = ((d)c3.k.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231053) {
                    n3 = this.x0.getSelectedItemPosition();
                    AutoTtsService.I = ((d)c3.k.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131230927) {
                    n3 = this.t0.getSelectedItemPosition();
                    AutoTtsService.D = ((d)c3.k.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231105) {
                    AutoTtsService.E = this.u0.getSelectedItemPosition();
                    return;
                }
                if (object.getId() == 2131231150) {
                    AutoTtsService.F = this.v0.getSelectedItemPosition();
                    return;
                }
                if (object.getId() == 2131230834) {
                    if (n3 == 0) {
                        return;
                    }
                    break block16;
                }
                if (object.getId() != 2131230833) return;
                ((d)c3.k.c.get((int)c3.i.T0)).h = object.getSelectedItem().toString();
                c3.k.y(this.n1());
                return;
            }
            catch (Exception exception) {}
            exception.printStackTrace();
            return;
        }
        for (n4 = 0; n4 < c3.k.e.size(); ++n4) {
            if (n4 == n3) {
                ((u)c3.k.e.get(n4)).h(0);
                object = this.G0;
                bl = ((u)c3.k.e.get((int)n4)).d != null;
                object.setEnabled(bl);
                if (((u)c3.k.e.get((int)n4)).d == null) continue;
                object = c3.k.c;
                ((d)object.get((int)c3.i.T0)).f = ((u)c3.k.e.get((int)n4)).d.b;
                ((d)object.get((int)c3.i.T0)).g = ((u)c3.k.e.get((int)n4)).c.toString();
                continue;
            }
            if (n4 >= n3) continue;
            ((u)c3.k.e.get(n4)).h(n4 + 1);
        }
        Collections.sort(c3.k.e);
        this.R2();
    }

    public final void O2() {
        ScrollView scrollView;
        LinearLayout linearLayout;
        block5: {
            block8: {
                block6: {
                    block7: {
                        linearLayout = (LinearLayout)this.h0.findViewById(2131230740);
                        scrollView = (ScrollView)this.h0.findViewById(2131231174);
                        int n3 = AutoTtsService.L;
                        if (n3 == 0) break block5;
                        if (n3 != 1) {
                            if (n3 == 2 || n3 == 3 || n3 == 4) {
                                List list = c3.k.c;
                                list.clear();
                                list.addAll(c3.k.h(this.n1(), true));
                            }
                        } else {
                            List list = c3.k.c;
                            list.clear();
                            list.addAll(c3.k.d(this.n1()));
                        }
                        scrollView.setVisibility(0);
                        linearLayout.setVisibility(8);
                        scrollView = (Spinner)this.h0.findViewById(2131230827);
                        scrollView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        linearLayout = (Spinner)this.h0.findViewById(2131230834);
                        this.E0 = linearLayout;
                        linearLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        linearLayout = (Spinner)this.h0.findViewById(2131230833);
                        this.F0 = linearLayout;
                        linearLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        linearLayout = (Button)this.h0.findViewById(2131231250);
                        this.G0 = linearLayout;
                        linearLayout.setEnabled(false);
                        this.G0.setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.P2();
                            }
                        });
                        linearLayout = (SeekBar)this.h0.findViewById(2131231212);
                        this.y0 = linearLayout;
                        linearLayout.setMax(500);
                        this.y0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final i a;
                            {
                                this.a = i3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        linearLayout = (SeekBar)this.h0.findViewById(2131231309);
                        this.z0 = linearLayout;
                        linearLayout.setMax(100);
                        this.z0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final i a;
                            {
                                this.a = i3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        linearLayout = (SeekBar)this.h0.findViewById(2131231143);
                        this.A0 = linearLayout;
                        linearLayout.setMax(200);
                        this.A0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final i a;
                            {
                                this.a = i3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        ((Button)this.h0.findViewById(2131230828)).setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.I1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230829)).setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.J1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230835)).setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.M1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230836)).setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.N1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230831)).setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.K1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230832)).setOnClickListener(new View.OnClickListener(this){
                            public final i c;
                            {
                                this.c = i3;
                            }

                            public void onClick(View view) {
                                this.c.L1();
                            }
                        });
                        n3 = AutoTtsService.L;
                        if (n3 == 2) break block6;
                        if (n3 == 3) break block7;
                        if (n3 == 4) break block6;
                        linearLayout = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.i());
                        break block8;
                    }
                    linearLayout = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.j("com.google.android.tts", false));
                    break block8;
                }
                linearLayout = new ArrayAdapter(this.n1(), 17367048, (List)c3.k.j(null, false));
            }
            linearLayout.setDropDownViewResource(0x1090009);
            scrollView.setAdapter((SpinnerAdapter)linearLayout);
            ((Button)this.h0.findViewById(2131230830)).setOnClickListener(new View.OnClickListener(this){
                public final i c;
                {
                    this.c = i3;
                }

                public void onClick(View view) {
                    this.c.S2();
                }
            });
            linearLayout = (CheckBox)this.h0.findViewById(2131230898);
            if (AutoTtsService.L != 0 && AutoTtsService.L != 3) {
                linearLayout.setEnabled(true);
            } else {
                linearLayout.setEnabled(false);
            }
            linearLayout.setChecked(AutoTtsService.O);
            linearLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
                public final i a;
                {
                    this.a = i3;
                }

                public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                    AutoTtsService.O = bl;
                }
            });
            return;
        }
        scrollView.setVisibility(8);
        linearLayout.setVisibility(0);
    }

    public final void P1(SeekBar object, int n3, boolean bl) {
        int n4 = object.getId();
        if (n4 != 2131231143) {
            if (n4 != 2131231212) {
                if (n4 == 2131231309) {
                    this.N0 = n3;
                    if (n3 < 10) {
                        this.N0 = 10;
                        this.z0.setProgress(10);
                    }
                    if ((n3 = T0) >= 0 && n3 < (object = c3.k.c).size()) {
                        ((d)object.get((int)c3.i.T0)).d = this.N0;
                        return;
                    }
                }
            } else {
                this.M0 = n3;
                if (n3 < 10) {
                    this.M0 = 10;
                    this.y0.setProgress(10);
                }
                if ((n3 = T0) >= 0 && n3 < (object = c3.k.c).size()) {
                    ((d)object.get((int)c3.i.T0)).c = this.M0;
                    return;
                }
            }
        } else {
            this.O0 = n3;
            if (n3 < 10) {
                this.O0 = 10;
                this.A0.setProgress(10);
            }
            if ((n3 = T0) >= 0 && n3 < (object = c3.k.c).size()) {
                ((d)object.get((int)c3.i.T0)).e = this.O0;
            }
        }
    }

    public final void P2() {
        if (c3.k.g == null || c3.k.e.isEmpty() || ((u)c3.k.e.get((int)0)).d == null) {
            return;
        }
        String string = ((u)c3.k.e.get((int)0)).d.b;
        Object object = ((u)c3.k.e.get((int)0)).c;
        CharSequence charSequence = ((d)c3.k.c.get((int)c3.i.T0)).h;
        String string2 = c3.y.a(c3.k.f((Locale)object));
        if (string2.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Sorry. Sample text for language ");
            ((StringBuilder)charSequence).append(((Locale)object).getDisplayName(new Locale("eng")));
            ((StringBuilder)charSequence).append(" is missing.");
            object = ((StringBuilder)charSequence).toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[AutoTTS:");
            stringBuilder.append(string);
            stringBuilder.append(":");
            stringBuilder.append(((Locale)object).toString());
            stringBuilder.append(":");
            stringBuilder.append((String)charSequence);
            stringBuilder.append("]");
            stringBuilder.append(string2);
            object = stringBuilder.toString();
        }
        c3.k.g.speak((CharSequence)object, 0, null, "AutoTTS_Test");
    }

    public final void Q2() {
        Button button = this.q0;
        if (button != null) {
            if (this.r0) {
                button.setText(2131624202);
                return;
            }
            button.setText(2131624205);
        }
    }

    public final void R2() {
        int n3;
        Object object = new String[c3.k.e.size()];
        for (n3 = 0; n3 < c3.k.e.size(); ++n3) {
            object[n3] = ((u)c3.k.e.get(n3)).d();
        }
        object = new ArrayAdapter(this.n1(), 17367048, (Object[])object);
        object.setDropDownViewResource(0x1090009);
        this.E0.setAdapter((SpinnerAdapter)object);
        object = ((d)c3.k.c.get((int)c3.i.T0)).h;
        if (!((u)c3.k.e.get(0)).d().equals("*Disabled")) {
            int n4 = ((u)c3.k.e.get((int)0)).f.size();
            Object[] objectArray = new String[n4];
            if (!((String)object).isEmpty()) {
                for (n3 = 0; n3 < ((u)c3.k.e.get((int)0)).f.size(); ++n3) {
                    if (!((String)((u)c3.k.e.get((int)0)).f.get(n3)).equals(object)) {
                        continue;
                    }
                    break;
                }
            } else {
                ((d)c3.k.c.get((int)c3.i.T0)).h = "";
                object = "";
            }
            if (!((String)object).isEmpty()) {
                objectArray[0] = object;
                n3 = 1;
                for (int i3 = 0; i3 < ((u)c3.k.e.get((int)0)).f.size(); ++i3) {
                    int n5 = n3;
                    if (!((String)((u)c3.k.e.get((int)0)).f.get(i3)).equals(object)) {
                        objectArray[n3] = (String)((u)c3.k.e.get((int)0)).f.get(i3);
                        n5 = n3 + 1;
                    }
                    n3 = n5;
                }
            } else {
                for (n3 = 0; n3 < ((u)c3.k.e.get((int)0)).f.size(); ++n3) {
                    objectArray[n3] = (String)((u)c3.k.e.get((int)0)).f.get(n3);
                }
            }
            if (n4 > 1) {
                Arrays.sort(objectArray, 1, n4 - 1);
            }
            object = new ArrayAdapter(this.p(), 17367048, objectArray);
            object.setDropDownViewResource(0x1090009);
            this.F0.setAdapter((SpinnerAdapter)object);
            return;
        }
        this.F0.setAdapter(null);
    }

    public final void S2() {
        List list;
        this.O0 = 100;
        this.M0 = 100;
        this.N0 = 100;
        int n3 = T0;
        if (n3 >= 0 && n3 < (list = c3.k.c).size()) {
            ((d)list.get((int)c3.i.T0)).d = this.N0;
        }
        ((SeekBar)this.h0.findViewById(2131231309)).setProgress(this.N0);
        n3 = T0;
        if (n3 >= 0 && n3 < (list = c3.k.c).size()) {
            ((d)list.get((int)c3.i.T0)).c = this.M0;
        }
        ((SeekBar)this.h0.findViewById(2131231212)).setProgress(this.M0);
        n3 = T0;
        if (n3 >= 0 && n3 < (list = c3.k.c).size()) {
            ((d)list.get((int)c3.i.T0)).e = this.O0;
        }
        ((SeekBar)this.h0.findViewById(2131231143)).setProgress(this.O0);
    }

    @Override
    public void k0(Bundle bundle) {
        super.k0(bundle);
        this.Q0 = this.k1(new b.b(), new e(this));
        this.e0 = 1;
        if (this.n() != null) {
            this.e0 = this.n().getInt("section_number");
        }
        this.P0 = this.k1(new b.d(), new f());
    }

    @Override
    public View o0(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int n3 = this.e0;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        layoutInflater = layoutInflater.inflate(2131427376, viewGroup, false);
                        this.j0 = layoutInflater;
                        return layoutInflater;
                    }
                    layoutInflater = layoutInflater.inflate(2131427374, viewGroup, false);
                    this.i0 = layoutInflater;
                    return layoutInflater;
                }
                layoutInflater = layoutInflater.inflate(2131427379, viewGroup, false);
                this.h0 = layoutInflater;
                return layoutInflater;
            }
            layoutInflater = layoutInflater.inflate(2131427375, viewGroup, false);
            this.g0 = layoutInflater;
            return layoutInflater;
        }
        layoutInflater = layoutInflater.inflate(2131427378, viewGroup, false);
        this.f0 = layoutInflater;
        return layoutInflater;
    }

    public final void s2() {
        if (this.m0 != null) {
            Object object = this.l0;
            object = object != null && ((SearchView)object).getQuery() != null ? this.l0.getQuery().toString() : "";
            this.m0.a((String)object, this.r0, this.p0);
            for (int i3 = 0; i3 < this.m0.getCount(); ++i3) {
                int n3 = this.m0.c(i3);
                if (n3 < 0 || n3 >= this.p0.size()) continue;
                this.k0.setItemChecked(i3, ((Boolean)this.p0.get(n3)).booleanValue());
            }
        }
    }

    public final ArrayList t2(int n3) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        if (n3 >= 0 && n3 < (list = c3.k.c).size()) {
            arrayList.add(((d)list.get((int)n3)).b);
        }
        return arrayList;
    }

    public final void u2() {
        r.d d3 = this.R0;
        if (d3 != null && this.S0 != null) {
            boolean bl = this.C2(d3.b());
            this.S0.a(bl);
            if (!bl) {
                Toast.makeText((Context)this.n1(), (CharSequence)"Package not installed. Please try again.", (int)0).show();
            }
            this.v2();
        }
    }

    public final void v2() {
        this.R0 = null;
        this.S0 = null;
    }

    public final void w2(String object) {
        Object object2;
        int n3;
        c3.k.e.clear();
        int n4 = 0;
        for (n3 = 0; n3 < c3.k.d.size(); ++n3) {
            object2 = ((u)c3.k.d.get(n3)).e();
            String string = ((u)c3.k.d.get((int)n3)).d.b;
            if (!((String)object).equals(object2)) continue;
            int n5 = AutoTtsService.L;
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) continue;
                        c3.k.e.add((u)c3.k.d.get(n3));
                        continue;
                    }
                    if (!string.equalsIgnoreCase("com.google.android.tts")) continue;
                    c3.k.e.add((u)c3.k.d.get(n3));
                    continue;
                }
                c3.k.e.add((u)c3.k.d.get(n3));
                continue;
            }
            c3.k.e.add((u)c3.k.d.get(n3));
        }
        if (AutoTtsService.L == 2 && !((String)object).equals(AutoTtsService.C)) {
            object2 = new u(new Locale((String)object, "", ""), null, c3.k.e.size());
            ((u)object2).h(c3.k.s(this.n1(), ((u)object2).f()));
            c3.k.e.add(object2);
        }
        if (AutoTtsService.L == 4 && !((String)object).equals(AutoTtsService.H) && !((String)object).equals(AutoTtsService.I)) {
            object = new u(new Locale((String)object, "", ""), null, c3.k.e.size());
            ((u)object).h(c3.k.s(this.n1(), ((u)object).f()));
            c3.k.e.add(object);
        }
        Collections.sort(c3.k.e);
        for (n3 = n4; n3 < c3.k.e.size(); ++n3) {
            ((u)c3.k.e.get(n3)).h(n3);
        }
    }

    public final Boolean x2(String string) {
        System.out.println("importSettings");
        Object object = c3.z.b(string);
        if (object.isEmpty()) {
            Toast.makeText((Context)this.n1(), (int)2131624058, (int)1).show();
            return Boolean.TRUE;
        }
        Object object2 = new ArrayList();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            object = c3.s.b(string2);
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("- ");
            stringBuilder.append(string2);
            stringBuilder.append(" : ");
            stringBuilder.append((String)object);
            printStream.println(stringBuilder.toString());
            object2.add(new r.d((String)object, string2, this.A2(this.n1(), string2)));
        }
        object2 = new r(this.n1(), (List)object2, new h(this));
        ((r)((Object)object2)).g(new r.b(this, string){
            public final String a;
            public final i b;
            {
                this.b = i3;
                this.a = string;
            }

            @Override
            public void a() {
                i i3 = this.b;
                i3.z2(i3.n1(), this.a);
            }

            @Override
            public void onCancel() {
            }
        });
        object2.show();
        return Boolean.TRUE;
    }

    public void z2(Context context, String string) {
        try {
            c3.i.y2(context, string);
            this.J2();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static class q0
    extends ArrayAdapter {
        public final ArrayList c;
        public final ArrayList d;
        public final ArrayList e;

        public q0(Context context, ArrayList arrayList, ArrayList arrayList2) {
            super(context, 0x1090010);
            this.c = new ArrayList(arrayList);
            this.d = new ArrayList(arrayList);
            this.e = new ArrayList();
            for (int i3 = 0; i3 < arrayList.size(); ++i3) {
                this.e.add(i3);
            }
            this.addAll(this.d);
        }

        public void a(String string, boolean bl, ArrayList arrayList) {
            this.d.clear();
            this.e.clear();
            this.clear();
            string = string == null ? "" : string.toLowerCase();
            for (int i3 = 0; i3 < this.c.size(); ++i3) {
                boolean bl2 = string.isEmpty();
                boolean bl3 = true;
                boolean bl4 = bl2 || ((String)this.c.get(i3)).toLowerCase().contains(string);
                boolean bl5 = bl3;
                if (bl) {
                    bl5 = arrayList != null && i3 < arrayList.size() && (Boolean)arrayList.get(i3) != false ? bl3 : false;
                }
                if (!bl4 || !bl5) continue;
                this.d.add((String)this.c.get(i3));
                this.e.add(i3);
            }
            this.addAll(this.d);
            this.notifyDataSetChanged();
        }

        public int b(int n3) {
            return this.e.indexOf(n3);
        }

        public int c(int n3) {
            if (n3 >= 0 && n3 < this.e.size()) {
                return (Integer)this.e.get(n3);
            }
            return -1;
        }
    }
}

