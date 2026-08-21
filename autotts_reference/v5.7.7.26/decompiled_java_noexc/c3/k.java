/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
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
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package c3;

import android.content.Context;
import android.content.Intent;
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
import c3.b0;
import c3.f;
import c3.f0;
import c3.g;
import c3.g0;
import c3.h;
import c3.i;
import c3.j;
import c3.l;
import c3.n;
import c3.p;
import c3.u;
import c3.v;
import c3.w;
import com.vnspeak.autotts.AutoTtsService;
import e0.a;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class k
extends Fragment {
    public static int b1;
    public Spinner A0;
    public Spinner B0;
    public Spinner C0;
    public Spinner D0;
    public CheckBox E0;
    public SeekBar F0;
    public SeekBar G0;
    public SeekBar H0;
    public LinearLayout I0;
    public LinearLayout J0;
    public LinearLayout K0;
    public LinearLayout L0;
    public LinearLayout M0;
    public LinearLayout N0;
    public Spinner O0;
    public Spinner P0;
    public Button Q0;
    public RadioButton R0;
    public RadioButton S0;
    public RadioButton T0;
    public RadioButton U0;
    public RadioButton V0;
    public RadioButton W0;
    public b X0;
    public b Y0;
    public u.d Z0;
    public u.a a1;
    public int e0 = 0;
    public View f0;
    public View g0;
    public View h0;
    public View i0;
    public View j0;
    public ListView k0;
    public SearchView l0;
    public d1 m0;
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
            c3.n.a.c("TTS", "Notification permission granted");
            return;
        }
        c3.n.a.c("TTS", "Notification permission denied");
    }

    public static /* synthetic */ void F1(p p3, CompoundButton compoundButton, boolean bl) {
        p3.j(bl);
    }

    public static /* synthetic */ void G1(k k3, u.d d3, u.a a4) {
        k3.Z0 = d3;
        k3.a1 = a4;
        k3.O2(d3.b());
    }

    public static /* synthetic */ void H1(k k3, Uri uri) {
        if (uri != null) {
            k3.G2(k3.Q2(uri));
            return;
        }
        k3.getClass();
    }

    public static boolean H2(Context context, String string) {
        context = context.getSharedPreferences("auto_tts_settings", 0).edit();
        context.clear();
        XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        Object object = new StringReader(string);
        xmlPullParser.setInput((Reader)object);
        int n3 = xmlPullParser.getEventType();
        while (n3 != 1) {
            if (n3 == 2) {
                object = xmlPullParser.getName();
                string = xmlPullParser.getAttributeValue(null, "name");
                if (string != null) {
                    n3 = ((String)object).hashCode();
                    switch (n3) {
                        default: {
                            break;
                        }
                        case 97526364: {
                            if (!((String)object).equals("float")) break;
                            context.putFloat(string, Float.parseFloat(xmlPullParser.getAttributeValue(null, "value")));
                            break;
                        }
                        case 64711720: {
                            if (!((String)object).equals("boolean")) break;
                            context.putBoolean(string, "true".equals(xmlPullParser.getAttributeValue(null, "value")));
                            break;
                        }
                        case 3327612: {
                            if (!((String)object).equals("long")) break;
                            context.putLong(string, Long.parseLong(xmlPullParser.getAttributeValue(null, "value")));
                            break;
                        }
                        case 104431: {
                            if (!((String)object).equals("int")) break;
                            context.putInt(string, Integer.parseInt(xmlPullParser.getAttributeValue(null, "value")));
                            break;
                        }
                        case -891985903: {
                            if (!((String)object).equals("string")) break;
                            context.putString(string, xmlPullParser.nextText());
                        }
                    }
                }
            }
            n3 = xmlPullParser.next();
        }
        context.commit();
        return true;
    }

    public static k M2(int n3) {
        k k3 = new k();
        Bundle bundle = new Bundle();
        bundle.putInt("section_number", n3);
        k3.t1(bundle);
        return k3;
    }

    private void onRadioButtonClicked(View object) {
        boolean bl = ((RadioButton)object).isChecked();
        switch (object.getId()) {
            default: {
                break;
            }
            case 2131230831: {
                if (!bl) break;
                AutoTtsService.T = 0;
                this.I0.setVisibility(8);
                this.J0.setVisibility(8);
                this.K0.setVisibility(8);
                this.L0.setVisibility(8);
                this.M0.setVisibility(8);
                this.N0.setVisibility(8);
                return;
            }
            case 2131230830: {
                if (!bl) break;
                AutoTtsService.T = 5;
                this.I0.setVisibility(8);
                this.J0.setVisibility(8);
                this.K0.setVisibility(8);
                this.L0.setVisibility(0);
                this.M0.setVisibility(0);
                this.N0.setVisibility(0);
                object = c3.n.c;
                synchronized (object) {
                    c3.n.x(this.n1());
                    object.clear();
                    object.addAll(c3.n.g(this.p(), false));
                    AutoTtsService.s0();
                }
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.l(null));
                object.setDropDownViewResource(0x1090009);
                this.w0.setAdapter((SpinnerAdapter)object);
                this.y0.setAdapter((SpinnerAdapter)object);
                int n3 = c3.n.f(AutoTtsService.P);
                if (n3 != -1) {
                    this.w0.setSelection(n3);
                    this.y0.setSelection(n3);
                }
                this.x0.setAdapter((SpinnerAdapter)object);
                this.z0.setAdapter((SpinnerAdapter)object);
                n3 = c3.n.f(AutoTtsService.Q);
                if (n3 != -1) {
                    this.x0.setSelection(n3);
                    this.z0.setSelection(n3);
                }
                this.A0.setAdapter((SpinnerAdapter)object);
                n3 = c3.n.f(AutoTtsService.K);
                if (n3 != -1) {
                    this.A0.setSelection(n3);
                }
                this.B0.setAdapter((SpinnerAdapter)object);
                n3 = c3.n.f(AutoTtsService.M);
                if (n3 != -1) {
                    this.B0.setSelection(n3);
                }
                this.C0.setAdapter((SpinnerAdapter)object);
                n3 = c3.n.f(AutoTtsService.O);
                if (n3 == -1) break;
                this.C0.setSelection(n3);
                return;
            }
            case 2131230829: {
                if (!bl) break;
                AutoTtsService.T = 4;
                this.I0.setVisibility(8);
                this.J0.setVisibility(8);
                this.K0.setVisibility(0);
                this.L0.setVisibility(8);
                this.M0.setVisibility(0);
                this.N0.setVisibility(0);
                object = c3.n.c;
                synchronized (object) {
                    c3.n.x(this.n1());
                    object.clear();
                    object.addAll(c3.n.g(this.p(), false));
                    AutoTtsService.s0();
                }
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.l(null));
                object.setDropDownViewResource(0x1090009);
                this.w0.setAdapter((SpinnerAdapter)object);
                this.y0.setAdapter((SpinnerAdapter)object);
                int n4 = c3.n.f(AutoTtsService.P);
                if (n4 != -1) {
                    this.w0.setSelection(n4);
                    this.y0.setSelection(n4);
                }
                this.x0.setAdapter((SpinnerAdapter)object);
                this.z0.setAdapter((SpinnerAdapter)object);
                n4 = c3.n.f(AutoTtsService.Q);
                if (n4 != -1) {
                    this.x0.setSelection(n4);
                    this.z0.setSelection(n4);
                }
                this.A0.setAdapter((SpinnerAdapter)object);
                n4 = c3.n.f(AutoTtsService.K);
                if (n4 != -1) {
                    this.A0.setSelection(n4);
                }
                this.B0.setAdapter((SpinnerAdapter)object);
                n4 = c3.n.f(AutoTtsService.M);
                if (n4 != -1) {
                    this.B0.setSelection(n4);
                }
                this.C0.setAdapter((SpinnerAdapter)object);
                n4 = c3.n.f(AutoTtsService.O);
                if (n4 == -1) break;
                this.C0.setSelection(n4);
                return;
            }
            case 2131230826: {
                if (!bl) break;
                AutoTtsService.T = 3;
                this.I0.setVisibility(0);
                this.J0.setVisibility(8);
                this.K0.setVisibility(8);
                this.L0.setVisibility(8);
                object = c3.n.c;
                synchronized (object) {
                    c3.n.x(this.n1());
                    object.clear();
                    object.addAll(c3.n.g(this.p(), false));
                    AutoTtsService.s0();
                }
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.l("com.google.android.tts"));
                object.setDropDownViewResource(0x1090009);
                this.r0.setAdapter((SpinnerAdapter)object);
                int n5 = c3.n.f(AutoTtsService.H);
                if (n5 == -1) break;
                this.r0.setSelection(n5);
                return;
            }
            case 2131230825: {
                if (!bl) break;
                AutoTtsService.T = 1;
                this.I0.setVisibility(8);
                this.J0.setVisibility(0);
                this.K0.setVisibility(8);
                this.L0.setVisibility(8);
                this.M0.setVisibility(0);
                this.N0.setVisibility(0);
                List list = c3.n.c;
                synchronized (list) {
                    c3.n.x(this.n1());
                    list.clear();
                    list.addAll(c3.n.g(this.p(), false));
                    AutoTtsService.s0();
                }
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.l(null));
                object.setDropDownViewResource(0x1090009);
                this.s0.setAdapter((SpinnerAdapter)object);
                int n6 = c3.n.f(AutoTtsService.I);
                if (n6 != -1) {
                    this.s0.setSelection(n6);
                }
                this.A0.setAdapter((SpinnerAdapter)object);
                n6 = c3.n.f(AutoTtsService.K);
                if (n6 != -1) {
                    this.A0.setSelection(n6);
                }
                this.B0.setAdapter((SpinnerAdapter)object);
                n6 = c3.n.f(AutoTtsService.M);
                if (n6 != -1) {
                    this.B0.setSelection(n6);
                }
                this.C0.setAdapter((SpinnerAdapter)object);
                n6 = c3.n.f(AutoTtsService.O);
                if (n6 == -1) break;
                this.C0.setSelection(n6);
                return;
            }
            case 2131230824: {
                if (!bl) break;
                AutoTtsService.T = 2;
                this.I0.setVisibility(0);
                this.J0.setVisibility(8);
                this.K0.setVisibility(8);
                this.L0.setVisibility(8);
                this.M0.setVisibility(8);
                this.N0.setVisibility(0);
                List list = c3.n.c;
                synchronized (list) {
                    c3.n.x(this.n1());
                    list.clear();
                    list.addAll(c3.n.g(this.p(), false));
                    AutoTtsService.s0();
                }
                object = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.l(null));
                object.setDropDownViewResource(0x1090009);
                this.r0.setAdapter((SpinnerAdapter)object);
                int n7 = c3.n.f(AutoTtsService.H);
                if (n7 == -1) break;
                this.r0.setSelection(n7);
                return;
            }
        }
    }

    public static /* synthetic */ boolean q2(k k3, boolean bl) {
        k3.q0 = bl;
        return bl;
    }

    public final void A2() {
        u.d d3 = this.Z0;
        if (d3 != null && this.a1 != null) {
            boolean bl = this.L2(d3.b());
            this.a1.a(bl);
            if (!bl) {
                Toast.makeText((Context)this.n1(), (CharSequence)"Package not installed. Please try again.", (int)0).show();
            }
            this.B2();
        }
    }

    public final void B2() {
        this.Z0 = null;
        this.a1 = null;
    }

    public final int C2() {
        List list;
        int n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            return ((f)list.get((int)c3.k.b1)).e;
        }
        return 100;
    }

    public final int D2() {
        List list;
        int n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            return ((f)list.get((int)c3.k.b1)).c;
        }
        return 100;
    }

    public final int E2() {
        List list;
        int n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            return ((f)list.get((int)c3.k.b1)).d;
        }
        return 100;
    }

    @Override
    public void F0() {
        super.F0();
        int n3 = this.e0;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 == 4) {
                        this.U2();
                    }
                } else {
                    this.Y2();
                }
            } else {
                this.W2();
            }
        } else {
            this.X2();
        }
        if (this.Z0 != null && this.a1 != null) {
            this.A2();
        }
    }

    public final void F2(String object) {
        Object object2;
        List list;
        int n3;
        c3.n.e.clear();
        int n4 = 0;
        for (n3 = 0; n3 < (list = c3.n.d).size(); ++n3) {
            String string = ((b0)list.get(n3)).e();
            object2 = ((b0)list.get((int)n3)).d.b;
            if (!((String)object).equals(string)) continue;
            int n5 = AutoTtsService.T;
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4 && n5 != 5) continue;
                        c3.n.e.add((b0)list.get(n3));
                        continue;
                    }
                    if (!((String)object2).equalsIgnoreCase("com.google.android.tts")) continue;
                    c3.n.e.add((b0)list.get(n3));
                    continue;
                }
                c3.n.e.add((b0)list.get(n3));
                continue;
            }
            c3.n.e.add((b0)list.get(n3));
        }
        if (AutoTtsService.T == 2 && !((String)object).equals(AutoTtsService.H)) {
            object2 = new b0(new Locale((String)object, "", ""), null, c3.n.e.size());
            ((b0)object2).h(c3.n.r(this.n1(), ((b0)object2).f()));
            c3.n.e.add(object2);
        }
        if (!(AutoTtsService.T != 4 && AutoTtsService.T != 5 || ((String)object).equals(AutoTtsService.P) || ((String)object).equals(AutoTtsService.Q))) {
            object = new b0(new Locale((String)object, "", ""), null, c3.n.e.size());
            ((b0)object).h(c3.n.r(this.n1(), ((b0)object).f()));
            c3.n.e.add(object);
        }
        Collections.sort(c3.n.e);
        for (n3 = n4; n3 < c3.n.e.size(); ++n3) {
            ((b0)c3.n.e.get(n3)).h(n3);
        }
    }

    public final Boolean G2(String string) {
        System.out.println("importSettings");
        Object object = c3.g0.b(string);
        if (object.isEmpty()) {
            Toast.makeText((Context)this.n1(), (int)2131624063, (int)1).show();
            return Boolean.TRUE;
        }
        Object object2 = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            String string3 = c3.v.b(string2);
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("- ");
            stringBuilder.append(string2);
            stringBuilder.append(" : ");
            stringBuilder.append(string3);
            printStream.println(stringBuilder.toString());
            object2.add(new u.d(string3, string2, this.J2(this.n1(), string2)));
        }
        object2 = new u(this.n1(), (List)object2, new j(this));
        ((u)((Object)object2)).g(new u.b(this, string){
            public final String a;
            public final k b;
            {
                this.b = k3;
                this.a = string;
            }

            @Override
            public void a() {
                k k3 = this.b;
                k3.I2(k3.n1(), this.a);
            }

            @Override
            public void onCancel() {
            }
        });
        object2.show();
        return Boolean.TRUE;
    }

    public final void I1() {
        int n3;
        int n4 = n3 = this.C2() - 5;
        if (n3 < 10) {
            n4 = 10;
        }
        ((f)c3.n.c.get((int)c3.k.b1)).e = n4;
        this.H0.setProgress(n4);
        Context context = this.p();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n4);
        stringBuilder.append(" of ");
        stringBuilder.append(200);
        Toast.makeText((Context)context, (CharSequence)stringBuilder.toString(), (int)0).show();
    }

    public void I2(Context context, String string) {
        c3.k.H2(context, string);
        this.T2();
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
                            this.V2();
                        }
                    } else {
                        this.U2();
                    }
                } else {
                    this.Y2();
                }
            } else {
                this.W2();
            }
        } else {
            this.X2();
        }
        if (this.Z0 != null && this.a1 != null) {
            this.A2();
        }
    }

    public final void J1() {
        int n3;
        int n4 = n3 = this.C2() + 5;
        if (n3 > this.H0.getMax()) {
            n4 = this.H0.getMax();
        }
        ((f)c3.n.c.get((int)c3.k.b1)).e = n4;
        this.H0.setProgress(n4);
        Context context = this.p();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n4);
        stringBuilder.append(" of ");
        stringBuilder.append(200);
        Toast.makeText((Context)context, (CharSequence)stringBuilder.toString(), (int)0).show();
    }

    public boolean J2(Context context, String string) {
        context = context.getPackageManager();
        context.getPackageInfo(string, 0);
        return true;
    }

    public final void K1() {
        int n3;
        int n4 = n3 = this.D2() - 5;
        if (n3 < 10) {
            n4 = 10;
        }
        ((f)c3.n.c.get((int)c3.k.b1)).c = n4;
        this.F0.setProgress(n4);
        Context context = this.p();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n4);
        stringBuilder.append(" of ");
        stringBuilder.append(500);
        Toast.makeText((Context)context, (CharSequence)stringBuilder.toString(), (int)0).show();
    }

    public final boolean K2(String string) {
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

    public final void L1() {
        Object object;
        int n3;
        int n4 = n3 = this.D2() + 5;
        if (n3 > this.F0.getMax()) {
            n4 = this.F0.getMax();
        }
        this.F0.setProgress(n4);
        n3 = b1;
        if (n3 >= 0 && n3 < (object = c3.n.c).size()) {
            ((f)object.get((int)c3.k.b1)).c = n4;
        }
        Context context = this.p();
        object = new StringBuilder();
        ((StringBuilder)object).append(n4);
        ((StringBuilder)object).append(" of ");
        ((StringBuilder)object).append(500);
        Toast.makeText((Context)context, (CharSequence)((StringBuilder)object).toString(), (int)0).show();
    }

    public final boolean L2(String string) {
        this.n1().getPackageManager().getPackageInfo(string, 1);
        return true;
    }

    public final void M1() {
        List list;
        int n3;
        int n4 = n3 = this.E2() - 5;
        if (n3 < 10) {
            n4 = 10;
        }
        if ((n3 = b1) >= 0 && n3 < (list = c3.n.c).size()) {
            ((f)list.get((int)c3.k.b1)).d = n4;
        }
        this.G0.setProgress(n4);
        list = this.p();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n4);
        stringBuilder.append(" of ");
        stringBuilder.append(100);
        Toast.makeText((Context)list, (CharSequence)stringBuilder.toString(), (int)0).show();
    }

    public final void N1() {
        List list;
        int n3;
        int n4 = n3 = this.E2() + 5;
        if (n3 > this.G0.getMax()) {
            n4 = this.G0.getMax();
        }
        if ((n3 = b1) >= 0 && n3 < (list = c3.n.c).size()) {
            ((f)list.get((int)c3.k.b1)).d = n4;
        }
        this.G0.setProgress(n4);
        list = this.p();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n4);
        stringBuilder.append(" of ");
        stringBuilder.append(100);
        Toast.makeText((Context)list, (CharSequence)stringBuilder.toString(), (int)0).show();
    }

    public void N2() {
        this.Y0.a(new String[]{"text/xml", "application/xml"});
    }

    public final void O1(AdapterView object, View object2, int n3, long l3) {
        object = (Spinner)object;
        int n4 = object.getId();
        boolean bl = true;
        if (n4 == 2131230833) {
            b1 = n3;
            c3.n.B(this.p());
            object2 = c3.n.c;
            object = ((f)object2.get((int)n3)).b;
            this.F0.setProgress(((f)object2.get((int)n3)).c);
            this.H0.setProgress(((f)object2.get((int)n3)).e);
            this.G0.setProgress(((f)object2.get((int)n3)).d);
            this.F2((String)object);
            this.b3();
            if (!c3.n.e.isEmpty()) {
                object = this.Q0;
                if (((b0)c3.n.e.get((int)0)).d == null) {
                    bl = false;
                }
                object.setEnabled(bl);
                return;
            }
            this.Q0.setEnabled(false);
            return;
        }
        if (object.getId() == 2131230828) {
            n3 = this.r0.getSelectedItemPosition();
            AutoTtsService.H = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131231061) {
            n3 = this.w0.getSelectedItemPosition();
            AutoTtsService.P = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131231062) {
            n3 = this.x0.getSelectedItemPosition();
            AutoTtsService.Q = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131231093) {
            n3 = this.y0.getSelectedItemPosition();
            AutoTtsService.P = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131231094) {
            n3 = this.z0.getSelectedItemPosition();
            AutoTtsService.Q = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131230933) {
            n3 = this.s0.getSelectedItemPosition();
            AutoTtsService.I = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131231117) {
            AutoTtsService.J = this.t0.getSelectedItemPosition();
            if (AutoTtsService.J != 3) {
                this.A0.setVisibility(8);
                return;
            }
            this.A0.setVisibility(0);
            return;
        }
        if (object.getId() == 2131231163) {
            AutoTtsService.L = this.u0.getSelectedItemPosition();
            if (AutoTtsService.L != 3) {
                this.B0.setVisibility(8);
                this.E0.setEnabled(true);
                return;
            }
            this.B0.setVisibility(0);
            this.E0.setEnabled(false);
            return;
        }
        if (object.getId() == 2131230943) {
            AutoTtsService.N = this.v0.getSelectedItemPosition();
            if (AutoTtsService.N != 3) {
                this.C0.setVisibility(8);
                return;
            }
            this.C0.setVisibility(0);
            return;
        }
        if (object.getId() == 2131231118) {
            n3 = this.A0.getSelectedItemPosition();
            AutoTtsService.K = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131231164) {
            n3 = this.B0.getSelectedItemPosition();
            AutoTtsService.M = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131230944) {
            n3 = this.C0.getSelectedItemPosition();
            AutoTtsService.O = ((f)c3.n.c.get((int)n3)).b;
            return;
        }
        if (object.getId() == 2131230840) {
            if (n3 != 0) {
                for (n4 = 0; n4 < c3.n.e.size(); ++n4) {
                    if (n4 == n3) {
                        ((b0)c3.n.e.get(n4)).h(0);
                        object = this.Q0;
                        bl = ((b0)c3.n.e.get((int)n4)).d != null;
                        object.setEnabled(bl);
                        if (((b0)c3.n.e.get((int)n4)).d == null) continue;
                        object = c3.n.c;
                        ((f)object.get((int)c3.k.b1)).f = ((b0)c3.n.e.get((int)n4)).d.b;
                        ((f)object.get((int)c3.k.b1)).g = ((b0)c3.n.e.get((int)n4)).c.toString();
                        continue;
                    }
                    if (n4 >= n3) continue;
                    ((b0)c3.n.e.get(n4)).h(n4 + 1);
                }
                Collections.sort(c3.n.e);
                this.b3();
                c3.n.x(this.n1());
                return;
            }
        } else {
            if (object.getId() == 2131230839) {
                ((f)c3.n.c.get((int)c3.k.b1)).h = object.getSelectedItem().toString();
                c3.n.x(this.n1());
                return;
            }
            if (object.getId() == 2131231222) {
                AutoTtsService.f0 = object.getSelectedItemPosition() + 1;
            }
        }
    }

    public final void O2(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("market://details?id=");
        stringBuilder.append(string);
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString()));
        intent.addFlags(0x10000000);
        this.B1(intent);
    }

    public final void P1(SeekBar object, int n3, boolean bl) {
        int n4 = object.getId();
        if (n4 != 2131231156) {
            if (n4 != 2131231230) {
                if (n4 == 2131231328) {
                    n4 = n3;
                    if (n3 < 10) {
                        this.G0.setProgress(10);
                        n4 = 10;
                    }
                    if ((n3 = b1) >= 0 && n3 < (object = c3.n.c).size()) {
                        ((f)object.get((int)c3.k.b1)).d = n4;
                        return;
                    }
                }
            } else {
                n4 = n3;
                if (n3 < 10) {
                    this.F0.setProgress(10);
                    n4 = 10;
                }
                if ((n3 = b1) >= 0 && n3 < (object = c3.n.c).size()) {
                    ((f)object.get((int)c3.k.b1)).c = n4;
                    return;
                }
            }
        } else {
            n4 = n3;
            if (n3 < 10) {
                this.H0.setProgress(10);
                n4 = 10;
            }
            if ((n3 = b1) >= 0 && n3 < (object = c3.n.c).size()) {
                ((f)object.get((int)c3.k.b1)).e = n4;
            }
        }
    }

    public final void P2() {
        Intent intent = new Intent();
        intent.setAction("com.android.settings.TTS_SETTINGS");
        intent.setFlags(0x10000000);
        this.B1(intent);
    }

    public final String Q2(Uri object) {
        StringBuilder stringBuilder;
        block1: {
            stringBuilder = new StringBuilder();
            object = this.n1().getContentResolver().openInputStream((Uri)object);
            Object object2 = new InputStreamReader((InputStream)object);
            BufferedReader bufferedReader = new BufferedReader((Reader)object2);
            while ((object2 = bufferedReader.readLine()) != null) {
                stringBuilder.append((String)object2);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
            if (object == null) break block1;
            ((InputStream)object).close();
        }
        return stringBuilder.toString();
    }

    public final void R2() {
        Context context = this.n1();
        if (!((PowerManager)context.getSystemService("power")).isIgnoringBatteryOptimizations(context.getPackageName())) {
            Intent intent = new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS");
            this.B1(intent);
            Toast.makeText((Context)context, (CharSequence)"Find Auto TTS & all third-party tts engines and select 'Unrestricted'", (int)1).show();
            return;
        }
    }

    public final void S2() {
        if (Build.VERSION.SDK_INT >= 33 && a.a(this.n1(), "android.permission.POST_NOTIFICATIONS") != 0) {
            this.X0.a("android.permission.POST_NOTIFICATIONS");
        }
    }

    public final void T2() {
        Intent intent = this.n1().getPackageManager().getLaunchIntentForPackage(this.n1().getPackageName());
        intent.addFlags(0x14000000);
        this.B1(intent);
        Process.killProcess((int)Process.myPid());
        System.exit(0);
    }

    public final void U2() {
        CheckBox checkBox = (CheckBox)this.i0.findViewById(2131231170);
        checkBox.setChecked(AutoTtsService.X);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.X = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230969);
        checkBox.setChecked(AutoTtsService.Y);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.Y = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131231012);
        checkBox.setChecked(AutoTtsService.Z);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.Z = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131231216);
        checkBox.setChecked(AutoTtsService.a0);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.a0 = bl;
                if (bl) {
                    this.a.S2();
                }
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230920);
        checkBox.setChecked(AutoTtsService.b0);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.b0 = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131231166);
        checkBox.setChecked(AutoTtsService.c0);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.c0 = bl;
            }
        });
        this.E0 = checkBox = (CheckBox)this.i0.findViewById(2131231165);
        boolean bl = AutoTtsService.L != 3;
        checkBox.setEnabled(bl);
        this.E0.setChecked(AutoTtsService.d0);
        this.E0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.d0 = bl;
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131231221);
        checkBox.setChecked(AutoTtsService.e0);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.e0 = bl;
                this.a.D0.setEnabled(AutoTtsService.e0);
            }
        });
        this.D0 = (Spinner)this.i0.findViewById(2131231222);
        checkBox = new ArrayAdapter(this.n1(), 17367048, (Object[])new String[]{"1", "2", "3"});
        this.D0.setAdapter((SpinnerAdapter)checkBox);
        this.D0.setEnabled(AutoTtsService.e0);
        this.D0.setSelection(AutoTtsService.f0 - 1);
        this.D0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        ((Button)this.i0.findViewById(2131230921)).setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onClick(View view) {
                this.c.R2();
            }
        });
        ((Button)this.i0.findViewById(2131230955)).setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onClick(View view) {
                c3.g0.d(this.c.n1());
            }
        });
        ((Button)this.i0.findViewById(2131231000)).setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onClick(View view) {
                this.c.N2();
            }
        });
        ((Button)this.i0.findViewById(2131231310)).setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onClick(View view) {
                this.c.P2();
            }
        });
        checkBox = (CheckBox)this.i0.findViewById(2131230945);
        p p3 = c3.p.f(this.n1());
        checkBox.setChecked(p3.g());
        checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new i(p3));
        ((Button)this.i0.findViewById(2131231209)).setOnClickListener(new View.OnClickListener(this, p3){
            public final p c;
            public final k d;
            {
                this.d = k3;
                this.c = p3;
            }

            public void onClick(View view) {
                this.c.k(this.d.n1());
            }
        });
        ((Button)this.i0.findViewById(2131230875)).setOnClickListener(new View.OnClickListener(this, p3){
            public final p c;
            public final k d;
            {
                this.d = k3;
                this.c = p3;
            }

            public void onClick(View view) {
                this.c.a();
            }
        });
        ((TextView)this.i0.findViewById(2131230812)).setText((CharSequence)String.valueOf(90000254));
        ((TextView)this.i0.findViewById(2131230813)).setText((CharSequence)"5.7.7.26");
    }

    public final void V2() {
        Spanned spanned = Html.fromHtml((String)this.Q(2131623986), (int)0);
        TextView textView = (TextView)this.j0.findViewById(2131231019);
        textView.setText((CharSequence)spanned);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public final void W2() {
        List list;
        LinearLayout linearLayout = (LinearLayout)this.g0.findViewById(2131231026);
        LinearLayout linearLayout2 = (LinearLayout)this.g0.findViewById(2131231027);
        if (AutoTtsService.T != 1 && AutoTtsService.T != 0) {
            c3.n.x(this.n1());
            list = c3.n.c;
            synchronized (list) {
                list.clear();
                list.addAll(c3.n.g(this.p(), false));
                AutoTtsService.s0();
            }
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(8);
            this.k0 = (ListView)this.g0.findViewById(2131231029);
            this.l0 = (SearchView)this.g0.findViewById(2131231197);
            if (AutoTtsService.T != 2 && AutoTtsService.T != 4 && AutoTtsService.T != 5) {
                list = c3.n.l("com.google.android.tts");
                this.n0 = c3.n.j("com.google.android.tts");
                this.o0 = c3.n.k("com.google.android.tts");
            } else {
                list = c3.n.l(null);
                this.n0 = c3.n.j(null);
                this.o0 = c3.n.k(null);
            }
            c3.n.x(this.n1());
            this.m0 = new d1(this.p(), (ArrayList)list, this.o0);
            this.k0.setChoiceMode(2);
            this.k0.setAdapter((ListAdapter)this.m0);
            for (int i3 = 0; i3 < ((ArrayList)list).size(); ++i3) {
                this.k0.setItemChecked(i3, ((Boolean)this.o0.get(i3)).booleanValue());
            }
            this.l0.setOnQueryTextListener(new SearchView.m(this){
                public final k a;
                {
                    this.a = k3;
                }

                @Override
                public boolean a(String string) {
                    this.a.y2();
                    return true;
                }

                @Override
                public boolean b(String string) {
                    return false;
                }
            });
            this.k0.setOnItemClickListener(new AdapterView.OnItemClickListener(this){
                public final k c;
                {
                    this.c = k3;
                }

                public void onItemClick(AdapterView object, View object2, int n3, long l3) {
                    boolean bl = this.c.k0.getCheckedItemPositions().get(n3);
                    int n4 = this.c.m0.c(n3);
                    if (n4 >= 0) {
                        this.c.o0.set(n4, bl);
                        object = c3.n.m();
                        object2 = this.c.z2(n4);
                        int n5 = ((ArrayList)object2).size();
                        n4 = 0;
                        block0: while (n4 < n5) {
                            Object object3 = ((ArrayList)object2).get(n4);
                            int n6 = n4 + 1;
                            if (((ArrayList)object).contains(object3 = (String)object3)) {
                                this.c.k0.setItemChecked(n3, true);
                                n4 = n6;
                                continue;
                            }
                            int n7 = 0;
                            while (true) {
                                List list = c3.n.c;
                                n4 = n6;
                                if (n7 >= list.size()) continue block0;
                                if (((String)object3).equalsIgnoreCase(((f)list.get((int)n7)).b)) {
                                    ((f)list.get((int)n7)).i = bl ^ true;
                                    c3.n.y(this.c.n1());
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
        ((Button)this.g0.findViewById(2131231203)).setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onClick(View object) {
                int n3;
                for (n3 = 0; n3 < this.c.m0.getCount(); ++n3) {
                    this.c.k0.setItemChecked(n3, true);
                    int n4 = this.c.m0.c(n3);
                    if (n4 < 0) continue;
                    this.c.o0.set(n4, Boolean.TRUE);
                }
                for (n3 = 0; n3 < (object = c3.n.c).size(); ++n3) {
                    if (!this.c.K2(((f)object.get((int)n3)).b)) continue;
                    ((f)object.get((int)n3)).i = false;
                }
                c3.n.y(this.c.n1());
            }
        });
        ((Button)this.g0.findViewById(2131230874)).setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
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
                for (n4 = 0; n4 < (object = c3.n.c).size(); ++n4) {
                    if (!this.c.K2(((f)object.get((int)n4)).b)) continue;
                    ((f)object.get((int)n4)).i = true;
                }
                ArrayList arrayList = c3.n.m();
                n3 = 0;
                while (true) {
                    object = c3.n.c;
                    if (n3 >= object.size()) break;
                    if (arrayList.contains(((f)object.get((int)n3)).b)) {
                        ((f)object.get((int)n3)).i = false;
                    }
                    ++n3;
                }
                for (n4 = n5; n4 < this.c.n0.size(); ++n4) {
                    if (!arrayList.contains(this.c.n0.get(n4))) continue;
                    this.c.o0.set(n4, Boolean.TRUE);
                    n3 = this.c.m0.b(n4);
                    if (n3 < 0) continue;
                    this.c.k0.setItemChecked(n3, true);
                }
                c3.n.y(this.c.n1());
                if (this.c.q0) {
                    this.c.y2();
                }
            }
        });
        list = (Button)this.g0.findViewById(2131231294);
        this.p0 = list;
        list.setOnClickListener(new View.OnClickListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onClick(View object) {
                object = this.c;
                c3.k.q2((k)object, ((k)object).q0 ^ true);
                this.c.a3();
                this.c.y2();
            }
        });
        this.a3();
        this.q0 = false;
    }

    public final void X2() {
        List list = c3.n.c;
        synchronized (list) {
            c3.n.x(this.n1());
            list.clear();
            list.addAll(c3.n.g(this.p(), false));
            AutoTtsService.s0();
        }
        this.R0 = (RadioButton)this.f0.findViewById(2131230831);
        this.S0 = (RadioButton)this.f0.findViewById(2131230825);
        this.T0 = (RadioButton)this.f0.findViewById(2131230824);
        this.U0 = (RadioButton)this.f0.findViewById(2131230826);
        this.V0 = (RadioButton)this.f0.findViewById(2131230829);
        this.W0 = (RadioButton)this.f0.findViewById(2131230830);
        this.U0.setEnabled(c3.w.a(this.n1()));
        this.R0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((k)object).onRadioButtonClicked((View)((k)object).R0);
            }
        });
        this.S0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((k)object).onRadioButtonClicked((View)((k)object).S0);
            }
        });
        this.T0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((k)object).onRadioButtonClicked((View)((k)object).T0);
            }
        });
        this.U0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((k)object).onRadioButtonClicked((View)((k)object).U0);
            }
        });
        this.V0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((k)object).onRadioButtonClicked((View)((k)object).V0);
            }
        });
        this.W0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton object, boolean bl) {
                object = this.a;
                ((k)object).onRadioButtonClicked((View)((k)object).W0);
            }
        });
        this.I0 = (LinearLayout)this.f0.findViewById(2131230721);
        this.J0 = (LinearLayout)this.f0.findViewById(2131230726);
        this.K0 = (LinearLayout)this.f0.findViewById(2131230729);
        this.L0 = (LinearLayout)this.f0.findViewById(2131230731);
        this.M0 = (LinearLayout)this.f0.findViewById(2131230733);
        this.N0 = (LinearLayout)this.f0.findViewById(2131230725);
        list = (Spinner)this.f0.findViewById(2131230828);
        this.r0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231061);
        this.w0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231062);
        this.x0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231093);
        this.y0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231094);
        this.z0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131230933);
        this.s0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231117);
        this.t0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231163);
        this.u0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131230943);
        this.v0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = new ArrayAdapter(this.p(), 17367048, new ArrayList<String>(Arrays.asList(this.K().getString(2131624189), this.K().getString(2131624191), this.K().getString(2131624192), this.K().getString(2131624193))));
        list.setDropDownViewResource(0x1090009);
        this.t0.setAdapter((SpinnerAdapter)list);
        this.t0.setSelection(AutoTtsService.J);
        this.u0.setAdapter((SpinnerAdapter)list);
        this.u0.setSelection(AutoTtsService.L);
        this.v0.setAdapter((SpinnerAdapter)list);
        this.v0.setSelection(AutoTtsService.N);
        list = (Spinner)this.f0.findViewById(2131231118);
        this.A0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131231164);
        this.B0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        list = (Spinner)this.f0.findViewById(2131230944);
        this.C0 = list;
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
            public final k c;
            {
                this.c = k3;
            }

            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                this.c.O1(adapterView, view, n3, l3);
            }

            public void onNothingSelected(AdapterView adapterView) {
            }
        });
        int n3 = AutoTtsService.T;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 == 5) {
                                if (this.W0.isChecked()) {
                                    this.onRadioButtonClicked((View)this.W0);
                                } else {
                                    this.W0.setChecked(true);
                                }
                            }
                        } else if (this.V0.isChecked()) {
                            this.onRadioButtonClicked((View)this.V0);
                        } else {
                            this.V0.setChecked(true);
                        }
                    } else if (this.U0.isChecked()) {
                        this.onRadioButtonClicked((View)this.U0);
                    } else {
                        this.U0.setChecked(true);
                    }
                } else if (this.T0.isChecked()) {
                    this.onRadioButtonClicked((View)this.T0);
                } else {
                    this.T0.setChecked(true);
                }
            } else if (this.S0.isChecked()) {
                this.onRadioButtonClicked((View)this.S0);
            } else {
                this.S0.setChecked(true);
            }
        } else if (this.R0.isChecked()) {
            this.onRadioButtonClicked((View)this.R0);
        } else {
            this.R0.setChecked(true);
        }
        list = (CheckBox)this.f0.findViewById(2131231030);
        list.setChecked(AutoTtsService.V);
        list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
            public final k a;
            {
                this.a = k3;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                AutoTtsService.V = bl;
            }
        });
    }

    public final void Y2() {
        ScrollView scrollView;
        LinearLayout linearLayout;
        block7: {
            List list;
            block10: {
                block8: {
                    block9: {
                        linearLayout = (LinearLayout)this.h0.findViewById(2131230743);
                        scrollView = (ScrollView)this.h0.findViewById(2131231190);
                        int n3 = AutoTtsService.T;
                        if (n3 == 0) break block7;
                        if (n3 != 1) {
                            if (n3 == 2 || n3 == 3 || n3 == 4 || n3 == 5) {
                                list = c3.n.c;
                                synchronized (list) {
                                    c3.n.x(this.n1());
                                    list.clear();
                                    list.addAll(c3.n.g(this.n1(), true));
                                    AutoTtsService.s0();
                                }
                            }
                        } else {
                            list = c3.n.c;
                            synchronized (list) {
                                c3.n.x(this.n1());
                                list.clear();
                                list.addAll(c3.n.c(this.n1()));
                                AutoTtsService.s0();
                            }
                        }
                        scrollView.setVisibility(0);
                        linearLayout.setVisibility(8);
                        scrollView = (Spinner)this.h0.findViewById(2131230833);
                        scrollView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        list = (Spinner)this.h0.findViewById(2131230840);
                        this.O0 = list;
                        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        list = (Spinner)this.h0.findViewById(2131230839);
                        this.P0 = list;
                        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onItemSelected(AdapterView adapterView, View view, int n3, long l3) {
                                this.c.O1(adapterView, view, n3, l3);
                            }

                            public void onNothingSelected(AdapterView adapterView) {
                            }
                        });
                        list = (Button)this.h0.findViewById(2131231268);
                        this.Q0 = list;
                        list.setEnabled(false);
                        this.Q0.setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.Z2();
                            }
                        });
                        list = (SeekBar)this.h0.findViewById(2131231230);
                        this.F0 = list;
                        list.setMax(500);
                        this.F0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final k a;
                            {
                                this.a = k3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(this.a.D2());
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(500);
                                    c3.l.a(seekBar, stringBuilder.toString());
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        list = (SeekBar)this.h0.findViewById(2131231328);
                        this.G0 = list;
                        list.setMax(100);
                        this.G0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final k a;
                            {
                                this.a = k3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(this.a.E2());
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(100);
                                    c3.l.a(seekBar, stringBuilder.toString());
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        list = (SeekBar)this.h0.findViewById(2131231156);
                        this.H0 = list;
                        list.setMax(200);
                        this.H0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this){
                            public final k a;
                            {
                                this.a = k3;
                            }

                            public void onProgressChanged(SeekBar seekBar, int n3, boolean bl) {
                                this.a.P1(seekBar, n3, bl);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append(this.a.C2());
                                    stringBuilder.append(" of ");
                                    stringBuilder.append(200);
                                    c3.l.a(seekBar, stringBuilder.toString());
                                }
                            }

                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        ((Button)this.h0.findViewById(2131230834)).setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.I1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230835)).setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.J1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230841)).setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.M1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230842)).setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.N1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230837)).setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.K1();
                            }
                        });
                        ((Button)this.h0.findViewById(2131230838)).setOnClickListener(new View.OnClickListener(this){
                            public final k c;
                            {
                                this.c = k3;
                            }

                            public void onClick(View view) {
                                this.c.L1();
                            }
                        });
                        n3 = AutoTtsService.T;
                        if (n3 == 2) break block8;
                        if (n3 == 3) break block9;
                        if (n3 == 4 || n3 == 5) break block8;
                        list = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.h());
                        break block10;
                    }
                    list = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.i("com.google.android.tts", false));
                    break block10;
                }
                list = new ArrayAdapter(this.n1(), 17367048, (List)c3.n.i(null, false));
            }
            list.setDropDownViewResource(0x1090009);
            scrollView.setAdapter((SpinnerAdapter)list);
            ((Button)this.h0.findViewById(2131230836)).setOnClickListener(new View.OnClickListener(this){
                public final k c;
                {
                    this.c = k3;
                }

                public void onClick(View view) {
                    this.c.c3();
                }
            });
            list = (CheckBox)this.h0.findViewById(2131230904);
            if (AutoTtsService.T != 0 && AutoTtsService.T != 3) {
                list.setEnabled(true);
            } else {
                list.setEnabled(false);
            }
            list.setChecked(AutoTtsService.W);
            list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(this){
                public final k a;
                {
                    this.a = k3;
                }

                public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                    AutoTtsService.W = bl;
                }
            });
            return;
        }
        scrollView.setVisibility(8);
        linearLayout.setVisibility(0);
    }

    public final void Z2() {
        if (c3.n.g == null || c3.n.e.isEmpty() || ((b0)c3.n.e.get((int)0)).d == null) {
            return;
        }
        String string = ((b0)c3.n.e.get((int)0)).d.b;
        Object object = ((b0)c3.n.e.get((int)0)).c;
        CharSequence charSequence = ((f)c3.n.c.get((int)c3.k.b1)).h;
        String string2 = c3.f0.a(c3.n.e((Locale)object));
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
        c3.n.g.speak((CharSequence)object, 0, null, "AutoTTS_Test");
    }

    public final void a3() {
        Button button = this.p0;
        if (button != null) {
            if (this.q0) {
                button.setText(2131624220);
                return;
            }
            button.setText(2131624223);
        }
    }

    public final void b3() {
        int n3;
        Object object = new String[c3.n.e.size()];
        for (n3 = 0; n3 < c3.n.e.size(); ++n3) {
            object[n3] = ((b0)c3.n.e.get(n3)).d();
        }
        object = new ArrayAdapter(this.n1(), 17367048, (Object[])object);
        object.setDropDownViewResource(0x1090009);
        this.O0.setAdapter((SpinnerAdapter)object);
        object = ((f)c3.n.c.get((int)c3.k.b1)).h;
        if (!c3.n.e.isEmpty()) {
            if (!((b0)c3.n.e.get(0)).d().equals("*Disabled")) {
                int n4 = ((b0)c3.n.e.get((int)0)).f.size();
                Object[] objectArray = new String[n4];
                if (!((String)object).isEmpty()) {
                    for (n3 = 0; n3 < ((b0)c3.n.e.get((int)0)).f.size(); ++n3) {
                        if (!((String)((b0)c3.n.e.get((int)0)).f.get(n3)).equals(object)) {
                            continue;
                        }
                        break;
                    }
                } else {
                    ((f)c3.n.c.get((int)c3.k.b1)).h = "";
                    object = "";
                }
                if (!((String)object).isEmpty()) {
                    objectArray[0] = object;
                    int n5 = 1;
                    for (int i3 = 0; i3 < ((b0)c3.n.e.get((int)0)).f.size(); ++i3) {
                        n3 = n5;
                        if (!((String)((b0)c3.n.e.get((int)0)).f.get(i3)).equals(object)) {
                            objectArray[n5] = (String)((b0)c3.n.e.get((int)0)).f.get(i3);
                            n3 = n5 + 1;
                        }
                        n5 = n3;
                    }
                } else {
                    for (n3 = 0; n3 < ((b0)c3.n.e.get((int)0)).f.size(); ++n3) {
                        objectArray[n3] = (String)((b0)c3.n.e.get((int)0)).f.get(n3);
                    }
                }
                if (n4 > 1) {
                    Arrays.sort(objectArray, 1, n4 - 1);
                }
                object = new ArrayAdapter(this.p(), 17367048, objectArray);
                object.setDropDownViewResource(0x1090009);
                this.P0.setAdapter((SpinnerAdapter)object);
                return;
            }
            this.P0.setAdapter(null);
        }
    }

    public final void c3() {
        List list;
        int n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            ((f)list.get((int)c3.k.b1)).d = 100;
        }
        this.G0.setProgress(100);
        n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            ((f)list.get((int)c3.k.b1)).c = 100;
        }
        this.F0.setProgress(100);
        n3 = b1;
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            ((f)list.get((int)c3.k.b1)).e = 100;
        }
        this.H0.setProgress(100);
    }

    @Override
    public void k0(Bundle bundle) {
        super.k0(bundle);
        this.Y0 = this.k1(new b.b(), new g(this));
        this.e0 = 1;
        if (this.n() != null) {
            this.e0 = this.n().getInt("section_number");
        }
        this.X0 = this.k1(new d(), new h());
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

    public final void y2() {
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

    public final ArrayList z2(int n3) {
        List list;
        ArrayList<String> arrayList = new ArrayList<String>();
        if (n3 >= 0 && n3 < (list = c3.n.c).size()) {
            arrayList.add(((f)list.get((int)n3)).b);
        }
        return arrayList;
    }

    public static class d1
    extends ArrayAdapter {
        public final ArrayList c;
        public final ArrayList d;
        public final ArrayList e;

        public d1(Context context, ArrayList arrayList, ArrayList arrayList2) {
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

