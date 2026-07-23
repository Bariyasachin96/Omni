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
import b.d;
import c3.a0;
import c3.b0;
import c3.e;
import c3.f;
import c3.g;
import c3.h;
import c3.i;
import c3.k;
import c3.m;
import c3.o;
import c3.t;
import c3.u;
import c3.v;
import c3.w;
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

public class j
extends Fragment {
    public static int b1;
    public Spinner A0;
    public SeekBar B0;
    public SeekBar C0;
    public SeekBar D0;
    public LinearLayout E0;
    public LinearLayout F0;
    public LinearLayout G0;
    public LinearLayout H0;
    public Spinner I0;
    public Spinner J0;
    public Button K0;
    public RadioButton L0;
    public RadioButton M0;
    public RadioButton N0;
    public RadioButton O0;
    public RadioButton P0;
    public RadioButton Q0;
    public CheckBox R0;
    public CheckBox S0;
    public CheckBox T0;
    public int U0;
    public int V0;
    public int W0;
    public b X0;
    public b Y0;
    public t.d Z0;
    public t.a a1;
    public int e0 = 0;
    public View f0;
    public View g0;
    public View h0;
    public View i0;
    public View j0;
    public ListView k0;
    public SearchView l0;
    public z0 m0;
    public ArrayList n0;
    public ArrayList o0;
    public Button p0;
    public boolean q0 = false;
    public Spinner r0;
    public Spinner s0;
    public Spinner t0;
    public Spinner u0;
    public Spinner v0;
    public Spinner w0;
    public Spinner x0;
    public Spinner y0;
    public Spinner z0;

    public static /* synthetic */ void E1(Boolean bl) {
        if (bl.booleanValue()) {
            c3.m.a.c("TTS", "Notification permission granted");
            return;
        }
        c3.m.a.c("TTS", "Notification permission denied");
    }

    public static /* synthetic */ void F1(o o3, CompoundButton compoundButton, boolean bl) {
        o3.j(bl);
    }

    /*
     * Exception decompiling
     */
    public static boolean F2(Context var0, String var1_2) {
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

    public static /* synthetic */ void G1(j j3, t.d d3, t.a a4) {
        j3.Z0 = d3;
        j3.a1 = a4;
        j3.M2(d3.b());
    }

    public static /* synthetic */ void H1(j j3, Uri uri) {
        if (uri != null) {
            j3.E2(j3.N2(uri));
            return;
        }
        j3.getClass();
    }

    public static j K2(int n3) {
        j j3 = new j();
        Bundle bundle = new Bundle();
        bundle.putInt("section_number", n3);
        j3.t1(bundle);
        return j3;
    }

    private void onRadioButtonClicked(View object) {
        boolean bl = ((RadioButton)object).isChecked();
        switch (object.getId()) {
            default: {
                break;
            }
            case 2131230827: {
                if (!bl) break;
                AutoTtsService.O = 0;
                this.E0.setVisibility(8);
                this.F0.setVisibility(8);
                this.G0.setVisibility(8);
                this.H0.setVisibility(8);
                return;
            }
            case 2131230826: {
                if (!bl) break;
                AutoTtsService.O = 5;
                this.E0.setVisibility(8);
                this.F0.setVisibility(8);
                this.G0.setVisibility(8);
                this.H0.setVisibility(0);
                object = c3.m.c;
                object.clear();
                object.addAll(c3.m.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.m(null));
                object.setDropDownViewResource(0x1090009);
                this.x0.setAdapter((SpinnerAdapter)object);
                this.z0.setAdapter((SpinnerAdapter)object);
                int n3 = c3.m.g(AutoTtsService.K);
                if (n3 != -1) {
                    this.x0.setSelection(n3);
                    this.z0.setSelection(n3);
                }
                this.y0.setAdapter((SpinnerAdapter)object);
                this.A0.setAdapter((SpinnerAdapter)object);
                n3 = c3.m.g(AutoTtsService.L);
                if (n3 == -1) break;
                this.y0.setSelection(n3);
                this.A0.setSelection(n3);
                return;
            }
            case 2131230825: {
                if (!bl) break;
                AutoTtsService.O = 4;
                this.E0.setVisibility(8);
                this.F0.setVisibility(8);
                this.G0.setVisibility(0);
                this.H0.setVisibility(8);
                object = c3.m.c;
                object.clear();
                object.addAll(c3.m.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.m(null));
                object.setDropDownViewResource(0x1090009);
                this.x0.setAdapter((SpinnerAdapter)object);
                this.z0.setAdapter((SpinnerAdapter)object);
                int n4 = c3.m.g(AutoTtsService.K);
                if (n4 != -1) {
                    this.x0.setSelection(n4);
                    this.z0.setSelection(n4);
                }
                this.y0.setAdapter((SpinnerAdapter)object);
                this.A0.setAdapter((SpinnerAdapter)object);
                n4 = c3.m.g(AutoTtsService.L);
                if (n4 == -1) break;
                this.y0.setSelection(n4);
                this.A0.setSelection(n4);
                return;
            }
            case 2131230822: {
                if (!bl) break;
                AutoTtsService.O = 3;
                this.E0.setVisibility(0);
                this.F0.setVisibility(8);
                this.G0.setVisibility(8);
                this.H0.setVisibility(8);
                object = c3.m.c;
                object.clear();
                object.addAll(c3.m.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.m("com.google.android.tts"));
                object.setDropDownViewResource(0x1090009);
                this.r0.setAdapter((SpinnerAdapter)object);
                int n5 = c3.m.g(AutoTtsService.F);
                if (n5 == -1) break;
                this.r0.setSelection(n5);
                return;
            }
            case 2131230821: {
                if (!bl) break;
                AutoTtsService.O = 1;
                this.E0.setVisibility(8);
                this.F0.setVisibility(0);
                this.G0.setVisibility(8);
                this.H0.setVisibility(8);
                object = c3.m.c;
                object.clear();
                object.addAll(c3.m.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.m(null));
                object.setDropDownViewResource(0x1090009);
                this.s0.setAdapter((SpinnerAdapter)object);
                int n6 = c3.m.g(AutoTtsService.G);
                if (n6 == -1) break;
                this.s0.setSelection(n6);
                return;
            }
            case 2131230820: {
                if (!bl) break;
                AutoTtsService.O = 2;
                this.E0.setVisibility(0);
                this.F0.setVisibility(8);
                this.G0.setVisibility(8);
                this.H0.setVisibility(8);
                object = c3.m.c;
                object.clear();
                object.addAll(c3.m.h(this.p(), false));
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.m(null));
                object.setDropDownViewResource(0x1090009);
                this.r0.setAdapter((SpinnerAdapter)object);
                int n7 = c3.m.g(AutoTtsService.F);
                if (n7 == -1) break;
                this.r0.setSelection(n7);
            }
        }
    }

    public static /* synthetic */ boolean r2(j j3, boolean bl) {
        j3.q0 = bl;
        return bl;
    }

    public final ArrayList A2(int n3) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        if (n3 >= 0 && n3 < (list = c3.m.c).size()) {
            arrayList.add(((e)list.get((int)n3)).b);
        }
        return arrayList;
    }

    public final void B2() {
        t.d d3 = this.Z0;
        if (d3 != null && this.a1 != null) {
            boolean bl = this.J2(d3.b());
            this.a1.a(bl);
            if (!bl) {
                Toast.makeText((Context)this.n1(), (CharSequence)"Package not installed. Please try again.", (int)0).show();
            }
            this.C2();
        }
    }

    public final void C2() {
        this.Z0 = null;
        this.a1 = null;
    }

    public final void D2(String object) {
        Object object2;
        int n3;
        c3.m.e.clear();
        int n4 = 0;
        for (n3 = 0; n3 < c3.m.d.size(); ++n3) {
            object2 = ((w)c3.m.d.get(n3)).e();
            String string = ((w)c3.m.d.get((int)n3)).d.b;
            if (!((String)object).equals(object2)) continue;
            int n5 = AutoTtsService.O;
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4 && n5 != 5) continue;
                        c3.m.e.add((w)c3.m.d.get(n3));
                        continue;
                    }
                    if (!string.equalsIgnoreCase("com.google.android.tts")) continue;
                    c3.m.e.add((w)c3.m.d.get(n3));
                    continue;
                }
                c3.m.e.add((w)c3.m.d.get(n3));
                continue;
            }
            c3.m.e.add((w)c3.m.d.get(n3));
        }
        if (AutoTtsService.O == 2 && !((String)object).equals(AutoTtsService.F)) {
            object2 = new w(new Locale((String)object, "", ""), null, c3.m.e.size());
            ((w)object2).h(c3.m.s(this.n1(), ((w)object2).f()));
            c3.m.e.add(object2);
        }
        if (!(AutoTtsService.O != 4 && AutoTtsService.O != 5 || ((String)object).equals(AutoTtsService.K) || ((String)object).equals(AutoTtsService.L))) {
            object = new w(new Locale((String)object, "", ""), null, c3.m.e.size());
            ((w)object).h(c3.m.s(this.n1(), ((w)object).f()));
            c3.m.e.add(object);
        }
        Collections.sort(c3.m.e);
        for (n3 = n4; n3 < c3.m.e.size(); ++n3) {
            ((w)c3.m.e.get(n3)).h(n3);
        }
    }

    public final Boolean E2(String string) {
        System.out.println("importSettings");
        Object object = c3.b0.b(string);
        if (object.isEmpty()) {
            Toast.makeText((Context)this.n1(), (int)2131624058, (int)1).show();
            return Boolean.TRUE;
        }
        Object object2 = new ArrayList();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            String string2 = (String)iterator.next();
            String string3 = c3.u.b(string2);
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("- ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" : ");
            ((StringBuilder)object).append(string3);
            printStream.println(((StringBuilder)object).toString());
            object2.add(new t.d(string3, string2, this.H2(this.n1(), string2)));
        }
        object2 = new t(this.n1(), (List)object2, new i(this));
        ((t)((Object)object2)).g(new t.b(this, string){
            public final String a;
            public final j b;
            {
                this.b = j3;
                this.a = string;
            }

            @Override
            public void a() {
                j j3 = this.b;
                j3.G2(j3.n1(), this.a);
            }

            @Override
            public void onCancel() {
            }
        });
        object2.show();
        return Boolean.TRUE;
    }

    @Override
    public void F0() {
        super.F0();
        int n3 = this.e0;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 == 3) {
                    this.V2();
                }
            } else {
                this.T2();
            }
        } else {
            this.U2();
        }
        if (this.Z0 != null && this.a1 != null) {
            this.B2();
        }
    }

    public void G2(Context context, String string) {
        try {
            c3.j.F2(context, string);
            this.Q2();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public boolean H2(Context context, String string) {
        context = context.getPackageManager();
        try {
            context.getPackageInfo(string, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public final void I1() {
        int n3;
        this.W0 = n3 = this.W0 - 5;
        if (n3 < 10) {
            this.W0 = 10;
        }
        e e3 = (e)c3.m.c.get(b1);
        e3.e = n3 = this.W0;
        this.D0.setProgress(n3);
    }

    public final boolean I2(String string) {
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

    @Override
    public void J0(View view, Bundle bundle) {
        super.J0(view, bundle);
        int n3 = this.e0;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        if (n3 == 5) {
                            this.S2();
                        }
                    } else {
                        this.R2();
                    }
                } else {
                    this.V2();
                }
            } else {
                this.T2();
            }
        } else {
            this.U2();
        }
        if (this.Z0 != null && this.a1 != null) {
            this.B2();
        }
    }

    public final void J1() {
        int n3;
        this.W0 = n3 = this.W0 + 5;
        if (n3 > this.D0.getMax()) {
            this.W0 = this.D0.getMax();
        }
        e e3 = (e)c3.m.c.get(b1);
        e3.e = n3 = this.W0;
        this.D0.setProgress(n3);
    }

    public final boolean J2(String string) {
        try {
            this.n1().getPackageManager().getPackageInfo(string, 1);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public final void K1() {
        int n3;
        this.U0 = n3 = this.U0 - 5;
        if (n3 < 10) {
            this.U0 = 10;
        }
        e e3 = (e)c3.m.c.get(b1);
        e3.c = n3 = this.U0;
        this.B0.setProgress(n3);
    }

    public final void L1() {
        List list;
        int n3;
        this.U0 = n3 = this.U0 + 5;
        if (n3 > this.B0.getMax()) {
            this.U0 = this.B0.getMax();
        }
        this.B0.setProgress(this.U0);
        n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.m.c).size()) {
            ((e)list.get((int)c3.j.b1)).c = this.U0;
        }
    }

    public void L2() {
        this.Y0.a(new String[]{"text/xml", "application/xml"});
    }

    public final void M1() {
        List list;
        int n3;
        this.V0 = n3 = this.V0 - 5;
        if (n3 < 10) {
            this.V0 = 10;
        }
        if ((n3 = b1) >= 0 && n3 < (list = c3.m.c).size()) {
            ((e)list.get((int)c3.j.b1)).d = this.V0;
        }
        this.C0.setProgress(this.V0);
    }

    public final void M2(String string) {
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
                    t.a a4 = this.a1;
                    if (a4 == null) break block4;
                    a4.a(false);
                    this.C2();
                }
            }
            return;
        }
    }

    public final void N1() {
        List list;
        int n3;
        this.V0 = n3 = this.V0 + 5;
        if (n3 > this.C0.getMax()) {
            this.V0 = this.C0.getMax();
        }
        if ((n3 = b1) >= 0 && n3 < (list = c3.m.c).size()) {
            ((e)list.get((int)c3.j.b1)).d = this.V0;
        }
        this.C0.setProgress(this.V0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final String N2(Uri object) {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void O1(AdapterView object, View object2, int n3, long l3) {
        boolean bl;
        int n4;
        block20: {
            try {
                object = (Spinner)object;
                n4 = object.getId();
                bl = true;
                if (n4 == 2131230829) {
                    b1 = n3;
                    c3.m.C(this.p());
                    object2 = c3.m.c;
                    object = ((e)object2.get((int)n3)).b;
                    this.B0.setProgress(((e)object2.get((int)n3)).c);
                    this.D0.setProgress(((e)object2.get((int)n3)).e);
                    this.C0.setProgress(((e)object2.get((int)n3)).d);
                    this.D2((String)object);
                    this.Y2();
                    if (c3.m.e.isEmpty()) {
                        this.K0.setEnabled(false);
                        return;
                    }
                    object = this.K0;
                    if (((w)c3.m.e.get((int)0)).d == null) {
                        bl = false;
                    }
                    object.setEnabled(bl);
                    return;
                }
                if (object.getId() == 2131230824) {
                    n3 = this.r0.getSelectedItemPosition();
                    AutoTtsService.F = ((e)c3.m.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231057) {
                    n3 = this.x0.getSelectedItemPosition();
                    AutoTtsService.K = ((e)c3.m.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231058) {
                    n3 = this.y0.getSelectedItemPosition();
                    AutoTtsService.L = ((e)c3.m.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231089) {
                    n3 = this.z0.getSelectedItemPosition();
                    AutoTtsService.K = ((e)c3.m.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231090) {
                    n3 = this.A0.getSelectedItemPosition();
                    AutoTtsService.L = ((e)c3.m.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131230929) {
                    n3 = this.s0.getSelectedItemPosition();
                    AutoTtsService.G = ((e)c3.m.c.get((int)n3)).b;
                    return;
                }
                if (object.getId() == 2131231113) {
                    AutoTtsService.H = this.t0.getSelectedItemPosition();
                    this.v0.setSelection(AutoTtsService.H);
                    return;
                }
                if (object.getId() == 2131231114) {
                    AutoTtsService.H = this.v0.getSelectedItemPosition();
                    this.t0.setSelection(AutoTtsService.H);
                    return;
                }
                if (object.getId() == 2131231159) {
                    AutoTtsService.I = this.u0.getSelectedItemPosition();
                    this.w0.setSelection(AutoTtsService.I);
                    return;
                }
                if (object.getId() == 2131231160) {
                    AutoTtsService.I = this.w0.getSelectedItemPosition();
                    this.u0.setSelection(AutoTtsService.I);
                    return;
                }
                if (object.getId() == 2131230836) {
                    if (n3 == 0) {
                        return;
                    }
                    break block20;
                }
                if (object.getId() != 2131230835) return;
                ((e)c3.m.c.get((int)c3.j.b1)).h = object.getSelectedItem().toString();
                c3.m.y(this.n1());
                return;
            }
            catch (Exception exception) {}
            exception.printStackTrace();
            return;
        }
        for (n4 = 0; n4 < c3.m.e.size(); ++n4) {
            if (n4 == n3) {
                ((w)c3.m.e.get(n4)).h(0);
                object = this.K0;
                bl = ((w)c3.m.e.get((int)n4)).d != null;
                object.setEnabled(bl);
                if (((w)c3.m.e.get((int)n4)).d == null) continue;
                object = c3.m.c;
                ((e)object.get((int)c3.j.b1)).f = ((w)c3.m.e.get((int)n4)).d.b;
                ((e)object.get((int)c3.j.b1)).g = ((w)c3.m.e.get((int)n4)).c.toString();
                continue;
            }
            if (n4 >= n3) continue;
            ((w)c3.m.e.get(n4)).h(n4 + 1);
        }
        Collections.sort(c3.m.e);
        this.Y2();
    }

    public final void O2() {
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

    public final void P1(SeekBar object, int n3, boolean bl) {
        int n4 = object.getId();
        if (n4 != 2131231152) {
            if (n4 != 2131231223) {
                if (n4 == 2131231320) {
                    this.V0 = n3;
                    if (n3 < 10) {
                        this.V0 = 10;
                        this.C0.setProgress(10);
                    }
                    if ((n3 = b1) >= 0 && n3 < (object = c3.m.c).size()) {
                        ((e)object.get((int)c3.j.b1)).d = this.V0;
                        return;
                    }
                }
            } else {
                this.U0 = n3;
                if (n3 < 10) {
                    this.U0 = 10;
                    this.B0.setProgress(10);
                }
                if ((n3 = b1) >= 0 && n3 < (object = c3.m.c).size()) {
                    ((e)object.get((int)c3.j.b1)).c = this.U0;
                    return;
                }
            }
        } else {
            this.W0 = n3;
            if (n3 < 10) {
                this.W0 = 10;
                this.D0.setProgress(10);
            }
            if ((n3 = b1) >= 0 && n3 < (object = c3.m.c).size()) {
                ((e)object.get((int)c3.j.b1)).e = this.W0;
            }
        }
    }

    public final void P2() {
        if (Build.VERSION.SDK_INT >= 33 && a.a(this.n1(), "android.permission.POST_NOTIFICATIONS") != 0) {
            this.X0.a("android.permission.POST_NOTIFICATIONS");
        }
    }

    public final void Q2() {
        Intent intent = this.n1().getPackageManager().getLaunchIntentForPackage(this.n1().getPackageName());
        intent.addFlags(0x14000000);
        this.B1(intent);
        Process.killProcess((int)Process.myPid());
        System.exit(0);
    }

    public final void R2() {
        Object object = (CheckBox)this.i0.findViewById(2131231165);
        object.setChecked(AutoTtsService.S);
        object.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.S = bl;
            }
        });
        object = (CheckBox)this.i0.findViewById(2131230963);
        object.setChecked(AutoTtsService.T);
        object.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.T = bl;
            }
        });
        object = (CheckBox)this.i0.findViewById(2131231006);
        object.setChecked(AutoTtsService.U);
        object.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.U = bl;
            }
        });
        object = (CheckBox)this.i0.findViewById(2131231211);
        object.setChecked(AutoTtsService.V);
        object.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.V = bl;
                if (bl) {
                    this.a.P2();
                }
            }
        });
        object = (CheckBox)this.i0.findViewById(2131230916);
        object.setChecked(AutoTtsService.W);
        object.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.W = bl;
            }
        });
        CheckBox checkBox = (CheckBox)this.i0.findViewById(2131231161);
        object.setChecked(AutoTtsService.X);
        object.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.X = bl;
            }
        });
        ((Button)this.i0.findViewById(2131230917)).setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View view) {
                this.c.O2();
            }
        });
        ((Button)this.i0.findViewById(2131230949)).setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View view) {
                c3.b0.d(this.c.n1());
            }
        });
        ((Button)this.i0.findViewById(2131230994)).setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View view) {
                this.c.L2();
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230939);
        object = c3.o.f(this.n1());
        checkBox.setChecked(((o)object).g());
        checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new h((o)object));
        ((Button)this.i0.findViewById(2131231204)).setOnClickListener(new View.OnClickListener(this, (o)object){
            public final o c;
            public final j d;
            {
                this.d = j3;
                this.c = o3;
            }

            public void onClick(View view) {
                this.c.k(this.d.n1());
            }
        });
        ((Button)this.i0.findViewById(2131230871)).setOnClickListener(new View.OnClickListener(this, (o)object){
            public final o c;
            public final j d;
            {
                this.d = j3;
                this.c = o3;
            }

            public void onClick(View view) {
                this.c.a();
            }
        });
    }

    public final void S2() {
        Spanned spanned = Html.fromHtml((String)this.Q(2131623983), (int)0);
        TextView textView = (TextView)this.j0.findViewById(2131231013);
        textView.setText((CharSequence)spanned);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public final void T2() {
        LinearLayout linearLayout = (LinearLayout)this.g0.findViewById(2131231020);
        Object object = (LinearLayout)this.g0.findViewById(2131231021);
        int n3 = AutoTtsService.O;
        int n4 = 0;
        if (n3 != 1 && AutoTtsService.O != 0) {
            List list = c3.m.c;
            list.clear();
            list.addAll(c3.m.h(this.p(), false));
            linearLayout.setVisibility(0);
            object.setVisibility(8);
            this.k0 = (ListView)this.g0.findViewById(2131231023);
            this.l0 = (SearchView)this.g0.findViewById(2131231192);
            if (AutoTtsService.O != 2 && AutoTtsService.O != 4 && AutoTtsService.O != 5) {
                object = c3.m.m("com.google.android.tts");
                this.n0 = c3.m.k("com.google.android.tts");
                this.o0 = c3.m.l("com.google.android.tts");
            } else {
                object = c3.m.m(null);
                this.n0 = c3.m.k(null);
                this.o0 = c3.m.l(null);
            }
            c3.m.y(this.n1());
            this.m0 = new z0(this.p(), (ArrayList)object, this.o0);
            this.k0.setChoiceMode(2);
            this.k0.setAdapter((ListAdapter)this.m0);
            while (n4 < ((ArrayList)object).size()) {
                this.k0.setItemChecked(n4, ((Boolean)this.o0.get(n4)).booleanValue());
                ++n4;
            }
            this.l0.setOnQueryTextListener(new SearchView.m(this){
                public final j a;
                {
                    this.a = j3;
                }

                @Override
                public boolean a(String string) {
                    this.a.z2();
                    return true;
                }

                @Override
                public boolean b(String string) {
                    return false;
                }
            });
            this.k0.setOnItemClickListener(new AdapterView.OnItemClickListener(this){
                public final j c;
                {
                    this.c = j3;
                }

                public void onItemClick(AdapterView object, View object2, int n3, long l3) {
                    boolean bl = this.c.k0.getCheckedItemPositions().get(n3);
                    int n4 = this.c.m0.c(n3);
                    if (n4 >= 0) {
                        this.c.o0.set(n4, bl);
                        object = c3.m.n();
                        object2 = this.c.A2(n4);
                        int n5 = ((ArrayList)object2).size();
                        int n6 = 0;
                        block0: while (n6 < n5) {
                            Object object3 = ((ArrayList)object2).get(n6);
                            int n7 = n6 + 1;
                            if (((ArrayList)object).contains(object3 = (String)object3)) {
                                this.c.k0.setItemChecked(n3, true);
                                n6 = n7;
                                continue;
                            }
                            n4 = 0;
                            while (true) {
                                List list = c3.m.c;
                                n6 = n7;
                                if (n4 >= list.size()) continue block0;
                                if (((String)object3).equalsIgnoreCase(((e)list.get((int)n4)).b)) {
                                    ((e)list.get((int)n4)).i = bl ^ true;
                                    c3.m.z(this.c.n1());
                                    n6 = n7;
                                    continue block0;
                                }
                                ++n4;
                            }
                        }
                    }
                }
            });
        } else {
            linearLayout.setVisibility(8);
            object.setVisibility(0);
        }
        ((Button)this.g0.findViewById(2131231198)).setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View object) {
                int n3;
                for (n3 = 0; n3 < this.c.m0.getCount(); ++n3) {
                    this.c.k0.setItemChecked(n3, true);
                    int n4 = this.c.m0.c(n3);
                    if (n4 < 0) continue;
                    this.c.o0.set(n4, Boolean.TRUE);
                }
                for (n3 = 0; n3 < (object = c3.m.c).size(); ++n3) {
                    if (!this.c.I2(((e)object.get((int)n3)).b)) continue;
                    ((e)object.get((int)n3)).i = false;
                }
                c3.m.z(this.c.n1());
            }
        });
        ((Button)this.g0.findViewById(2131230870)).setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View object) {
                int n3;
                int n4;
                int n5 = 0;
                for (n4 = 0; n4 < this.c.m0.getCount(); ++n4) {
                    this.c.k0.setItemChecked(n4, false);
                    n3 = this.c.m0.c(n4);
                    if (n3 < 0) continue;
                    this.c.o0.set(n3, Boolean.FALSE);
                }
                for (n4 = 0; n4 < (object = c3.m.c).size(); ++n4) {
                    if (!this.c.I2(((e)object.get((int)n4)).b)) continue;
                    ((e)object.get((int)n4)).i = true;
                }
                object = c3.m.n();
                n3 = 0;
                while (true) {
                    List list = c3.m.c;
                    if (n3 >= list.size()) break;
                    if (((ArrayList)object).contains(((e)list.get((int)n3)).b)) {
                        ((e)list.get((int)n3)).i = false;
                    }
                    ++n3;
                }
                for (n4 = n5; n4 < this.c.n0.size(); ++n4) {
                    if (!((ArrayList)object).contains(this.c.n0.get(n4))) continue;
                    this.c.o0.set(n4, Boolean.TRUE);
                    n3 = this.c.m0.b(n4);
                    if (n3 < 0) continue;
                    this.c.k0.setItemChecked(n3, true);
                }
                c3.m.z(this.c.n1());
                if (this.c.q0) {
                    this.c.z2();
                }
            }
        });
        object = (Button)this.g0.findViewById(2131231287);
        this.p0 = object;
        object.setOnClickListener(new View.OnClickListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onClick(View object) {
                object = this.c;
                c3.j.r2((j)object, ((j)object).q0 ^ true);
                this.c.X2();
                this.c.z2();
            }
        });
        this.X2();
    }

    public final void U2() {
        List list = c3.m.c;
        list.clear();
        list.addAll(c3.m.h(this.p(), false));
        this.L0 = (RadioButton)this.f0.findViewById(2131230827);
        this.M0 = (RadioButton)this.f0.findViewById(2131230821);
        this.N0 = (RadioButton)this.f0.findViewById(2131230820);
        this.O0 = (RadioButton)this.f0.findViewById(2131230822);
        this.P0 = (RadioButton)this.f0.findViewById(2131230825);
        this.Q0 = (RadioButton)this.f0.findViewById(2131230826);
        this.O0.setEnabled(c3.v.a(this.n1()));
        this.L0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((j)object).onRadioButtonClicked((View)((j)object).L0);
            }
        });
        this.M0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((j)object).onRadioButtonClicked((View)((j)object).M0);
            }
        });
        this.N0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((j)object).onRadioButtonClicked((View)((j)object).N0);
            }
        });
        this.O0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((j)object).onRadioButtonClicked((View)((j)object).O0);
            }
        });
        this.P0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((j)object).onRadioButtonClicked((View)((j)object).P0);
            }
        });
        this.Q0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((j)object).onRadioButtonClicked((View)((j)object).Q0);
            }
        });
        this.E0 = (LinearLayout)this.f0.findViewById(2131230721);
        this.F0 = (LinearLayout)this.f0.findViewById(2131230725);
        this.G0 = (LinearLayout)this.f0.findViewById(2131230728);
        this.H0 = (LinearLayout)this.f0.findViewById(2131230730);
        list = (Spinner)this.f0.findViewById(2131230824);
        this.r0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231057);
        this.x0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231058);
        this.y0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231089);
        this.z0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231090);
        this.A0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131230929);
        this.s0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231113);
        this.t0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231159);
        this.u0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231114);
        this.v0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231160);
        this.w0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final j c;
            {
                this.c = j3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = new ArrayAdapter(this.p(), 17367048, new ArrayList<String>(Arrays.asList(this.K().getString(2131624184), this.K().getString(2131624186), this.K().getString(2131624187))));
        list.setDropDownViewResource(0x1090009);
        this.t0.setAdapter((SpinnerAdapter)list);
        this.t0.setSelection(AutoTtsService.H);
        this.u0.setAdapter((SpinnerAdapter)list);
        this.u0.setSelection(AutoTtsService.I);
        this.v0.setAdapter((SpinnerAdapter)list);
        this.v0.setSelection(AutoTtsService.H);
        this.w0.setAdapter((SpinnerAdapter)list);
        this.w0.setSelection(AutoTtsService.I);
        int n3 = AutoTtsService.O;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 == 5) {
                                if (this.Q0.isChecked()) {
                                    this.onRadioButtonClicked((View)this.Q0);
                                } else {
                                    this.Q0.setChecked(true);
                                }
                            }
                        } else if (this.P0.isChecked()) {
                            this.onRadioButtonClicked((View)this.P0);
                        } else {
                            this.P0.setChecked(true);
                        }
                    } else if (this.O0.isChecked()) {
                        this.onRadioButtonClicked((View)this.O0);
                    } else {
                        this.O0.setChecked(true);
                    }
                } else if (this.N0.isChecked()) {
                    this.onRadioButtonClicked((View)this.N0);
                } else {
                    this.N0.setChecked(true);
                }
            } else if (this.M0.isChecked()) {
                this.onRadioButtonClicked((View)this.M0);
            } else {
                this.M0.setChecked(true);
            }
        } else if (this.L0.isChecked()) {
            this.onRadioButtonClicked((View)this.L0);
        } else {
            this.L0.setChecked(true);
        }
        list = (CheckBox)this.f0.findViewById(2131231024);
        this.R0 = list;
        list.setChecked(AutoTtsService.Q);
        list = (CheckBox)this.f0.findViewById(2131231025);
        this.S0 = list;
        list.setChecked(AutoTtsService.Q);
        list = (CheckBox)this.f0.findViewById(2131231026);
        this.T0 = list;
        list.setChecked(AutoTtsService.Q);
        this.R0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.Q = bl;
                this.a.S0.setChecked(AutoTtsService.Q);
                this.a.T0.setChecked(AutoTtsService.Q);
            }
        });
        this.S0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.Q = bl;
                this.a.R0.setChecked(AutoTtsService.Q);
                this.a.T0.setChecked(AutoTtsService.Q);
            }
        });
        this.T0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final j a;
            {
                this.a = j3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.Q = bl;
                this.a.R0.setChecked(AutoTtsService.Q);
                this.a.S0.setChecked(AutoTtsService.Q);
            }
        });
    }

    public final void V2() {
        ScrollView scrollView;
        LinearLayout linearLayout;
        block5: {
            block8: {
                block6: {
                    block7: {
                        linearLayout = (LinearLayout)this.h0.findViewById(2131230741);
                        scrollView = (ScrollView)this.h0.findViewById(2131231185);
                        int n3 = AutoTtsService.O;
                        if (n3 == 0) break block5;
                        if (n3 != 1) {
                            if (n3 == 2 || n3 == 3 || n3 == 4 || n3 == 5) {
                                List list = c3.m.c;
                                list.clear();
                                list.addAll(c3.m.h(this.n1(), true));
                            }
                        } else {
                            List list = c3.m.c;
                            list.clear();
                            list.addAll(c3.m.d(this.n1()));
                        }
                        scrollView.setVisibility(0);
                        linearLayout.setVisibility(8);
                        linearLayout = (Spinner)this.h0.findViewById(2131230829);
                        linearLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        scrollView = (Spinner)this.h0.findViewById(2131230836);
                        this.I0 = scrollView;
                        scrollView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        scrollView = (Spinner)this.h0.findViewById(2131230835);
                        this.J0 = scrollView;
                        scrollView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        scrollView = (Button)this.h0.findViewById(2131231261);
                        this.K0 = scrollView;
                        scrollView.setEnabled(false);
                        this.K0.setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.W2();
                            }
                        });
                        scrollView = (SeekBar)this.h0.findViewById(2131231223);
                        this.B0 = scrollView;
                        scrollView.setMax(500);
                        this.B0.setAccessibilityLiveRegion(1);
                        this.B0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final j a;
                            {
                                this.a = j3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(this.a.U0);
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(500);
                                    c3.k.a(seekBar, stringBuilder.toString());
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        scrollView = (SeekBar)this.h0.findViewById(2131231320);
                        this.C0 = scrollView;
                        scrollView.setMax(100);
                        this.C0.setAccessibilityLiveRegion(1);
                        this.C0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final j a;
                            {
                                this.a = j3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(this.a.V0);
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(100);
                                    c3.k.a(seekBar, stringBuilder.toString());
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        scrollView = (SeekBar)this.h0.findViewById(2131231152);
                        this.D0 = scrollView;
                        scrollView.setMax(200);
                        this.D0.setAccessibilityLiveRegion(1);
                        this.D0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final j a;
                            {
                                this.a = j3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(this.a.W0);
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(200);
                                    c3.k.a(seekBar, stringBuilder.toString());
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        ((Button)this.h0.findViewById(2131230830)).setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.I1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230831)).setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.J1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230837)).setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.M1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230838)).setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.N1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230833)).setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.K1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230834)).setOnClickListener(new View.OnClickListener(this){
                            public final j c;
                            {
                                this.c = j3;
                            }

                            public void onClick(View view) {
                                this.c.L1();
                            }
                        });
                        n3 = AutoTtsService.O;
                        if (n3 == 2) break block6;
                        if (n3 == 3) break block7;
                        if (n3 == 4 || n3 == 5) break block6;
                        scrollView = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.i());
                        break block8;
                    }
                    scrollView = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.j("com.google.android.tts", false));
                    break block8;
                }
                scrollView = new ArrayAdapter(this.n1(), 17367048, (List)c3.m.j(null, false));
            }
            scrollView.setDropDownViewResource(0x1090009);
            linearLayout.setAdapter((SpinnerAdapter)scrollView);
            ((Button)this.h0.findViewById(2131230832)).setOnClickListener(new View.OnClickListener(this){
                public final j c;
                {
                    this.c = j3;
                }

                public void onClick(View view) {
                    this.c.Z2();
                }
            });
            scrollView = (CheckBox)this.h0.findViewById(2131230900);
            if (AutoTtsService.O != 0 && AutoTtsService.O != 3) {
                scrollView.setEnabled(true);
            } else {
                scrollView.setEnabled(false);
            }
            scrollView.setChecked(AutoTtsService.R);
            scrollView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
                public final j a;
                {
                    this.a = j3;
                }

                public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                    AutoTtsService.R = bl;
                }
            });
            return;
        }
        scrollView.setVisibility(8);
        linearLayout.setVisibility(0);
    }

    public final void W2() {
        if (c3.m.g == null || c3.m.e.isEmpty() || ((w)c3.m.e.get((int)0)).d == null) {
            return;
        }
        String string = ((w)c3.m.e.get((int)0)).d.b;
        Object object = ((w)c3.m.e.get((int)0)).c;
        CharSequence charSequence = ((e)c3.m.c.get((int)c3.j.b1)).h;
        String string2 = c3.a0.a(c3.m.f((Locale)object));
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
        c3.m.g.speak((CharSequence)object, 0, null, "AutoTTS_Test");
    }

    public final void X2() {
        Button button = this.p0;
        if (button != null) {
            if (this.q0) {
                button.setText(2131624209);
                return;
            }
            button.setText(2131624212);
        }
    }

    public final void Y2() {
        int n3;
        Object object = new String[c3.m.e.size()];
        for (n3 = 0; n3 < c3.m.e.size(); ++n3) {
            object[n3] = ((w)c3.m.e.get(n3)).d();
        }
        object = new ArrayAdapter(this.n1(), 17367048, (Object[])object);
        object.setDropDownViewResource(0x1090009);
        this.I0.setAdapter((SpinnerAdapter)object);
        object = ((e)c3.m.c.get((int)c3.j.b1)).h;
        if (!c3.m.e.isEmpty()) {
            if (!((w)c3.m.e.get(0)).d().equals("*Disabled")) {
                int n4 = ((w)c3.m.e.get((int)0)).f.size();
                Object[] objectArray = new String[n4];
                if (!((String)object).isEmpty()) {
                    for (n3 = 0; n3 < ((w)c3.m.e.get((int)0)).f.size(); ++n3) {
                        if (!((String)((w)c3.m.e.get((int)0)).f.get(n3)).equals(object)) {
                            continue;
                        }
                        break;
                    }
                } else {
                    ((e)c3.m.c.get((int)c3.j.b1)).h = "";
                    object = "";
                }
                if (!((String)object).isEmpty()) {
                    objectArray[0] = object;
                    int n5 = 1;
                    for (int i3 = 0; i3 < ((w)c3.m.e.get((int)0)).f.size(); ++i3) {
                        n3 = n5;
                        if (!((String)((w)c3.m.e.get((int)0)).f.get(i3)).equals(object)) {
                            objectArray[n5] = (String)((w)c3.m.e.get((int)0)).f.get(i3);
                            n3 = n5 + 1;
                        }
                        n5 = n3;
                    }
                } else {
                    for (n3 = 0; n3 < ((w)c3.m.e.get((int)0)).f.size(); ++n3) {
                        objectArray[n3] = (String)((w)c3.m.e.get((int)0)).f.get(n3);
                    }
                }
                if (n4 > 1) {
                    Arrays.sort(objectArray, 1, n4 - 1);
                }
                object = new ArrayAdapter(this.p(), 17367048, objectArray);
                object.setDropDownViewResource(0x1090009);
                this.J0.setAdapter((SpinnerAdapter)object);
                return;
            }
            this.J0.setAdapter(null);
        }
    }

    public final void Z2() {
        List list;
        this.W0 = 100;
        this.U0 = 100;
        this.V0 = 100;
        int n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.m.c).size()) {
            ((e)list.get((int)c3.j.b1)).d = this.V0;
        }
        ((SeekBar)this.h0.findViewById(2131231320)).setProgress(this.V0);
        n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.m.c).size()) {
            ((e)list.get((int)c3.j.b1)).c = this.U0;
        }
        ((SeekBar)this.h0.findViewById(2131231223)).setProgress(this.U0);
        n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.m.c).size()) {
            ((e)list.get((int)c3.j.b1)).e = this.W0;
        }
        ((SeekBar)this.h0.findViewById(2131231152)).setProgress(this.W0);
    }

    @Override
    public void k0(Bundle bundle) {
        super.k0(bundle);
        this.Y0 = this.k1(new b.b(), new f(this));
        this.e0 = 1;
        if (this.n() != null) {
            this.e0 = this.n().getInt("section_number");
        }
        this.X0 = this.k1(new d(), new g());
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

    public final void z2() {
        if (this.m0 != null) {
            Object object = this.l0;
            object = object != null && ((SearchView)object).getQuery() != null ? this.l0.getQuery().toString() : "";
            this.m0.a((String)object, this.q0, this.o0);
            for (int i3 = 0; i3 < this.m0.getCount(); ++i3) {
                int n3 = this.m0.c(i3);
                if (n3 < 0 || n3 >= this.o0.size()) continue;
                this.k0.setItemChecked(i3, ((Boolean)this.o0.get(n3)).booleanValue());
            }
        }
    }

    public static class z0
    extends ArrayAdapter {
        public final ArrayList c;
        public final ArrayList d;
        public final ArrayList e;

        public z0(Context context, ArrayList arrayList, ArrayList arrayList2) {
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

