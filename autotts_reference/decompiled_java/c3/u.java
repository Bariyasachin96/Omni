/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package c3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import c3.q;
import c3.r;
import c3.s;
import c3.t;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class u
extends Dialog {
    public List c;
    public c d;
    public b e;
    public LinearLayout f;
    public Button g;
    public Button h;

    public u(Context object, List list, c c3) {
        super((Context)object);
        for (int i3 = 0; i3 < list.size(); ++i3) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((d)list.get(i3)).a());
            stringBuilder.append(": ");
            stringBuilder.append(((d)list.get(i3)).b());
            ((PrintStream)object).println(stringBuilder.toString());
        }
        this.c = new ArrayList(list);
        this.d = c3;
    }

    public static /* synthetic */ void a(u u3, Button button, d d3, View object) {
        ((Object)((Object)u3)).getClass();
        button.setEnabled(false);
        button.setText((CharSequence)"Installing...");
        object = u3.d;
        if (object != null) {
            object.a(d3, new t(u3, d3, button));
        }
    }

    public static /* synthetic */ void b(u u3, View object) {
        object = u3.e;
        if (object != null) {
            object.onCancel();
        }
        u3.dismiss();
    }

    public static /* synthetic */ void c(u u3, d d3, Button button, boolean bl) {
        ((Object)((Object)u3)).getClass();
        if (bl) {
            d3.d(true);
            u3.f();
            u3.h();
            return;
        }
        button.setEnabled(true);
        button.setText((CharSequence)"Install");
    }

    public static /* synthetic */ void d(u u3, View object) {
        object = u3.e;
        if (object != null) {
            object.a();
        }
        u3.dismiss();
    }

    public final View e() {
        Context context = this.getContext();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(48, 48, 48, 24);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        TextView textView = new TextView(context);
        textView.setText((CharSequence)"Requried TTS Engines");
        textView.setTextSize(20.0f);
        textView.setPadding(0, 0, 0, 32);
        linearLayout.addView((View)textView);
        textView = new LinearLayout(context);
        this.f = textView;
        textView.setOrientation(1);
        linearLayout.addView((View)this.f);
        textView = new LinearLayout(context);
        textView.setOrientation(0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = 32;
        textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        layoutParams = new Button(context);
        this.g = layoutParams;
        layoutParams.setText((CharSequence)"Apply");
        this.g.setOnClickListener((View.OnClickListener)new r(this));
        layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
        layoutParams.rightMargin = 8;
        this.g.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        textView.addView((View)this.g);
        context = new Button(context);
        this.h = context;
        context.setText((CharSequence)"Cancel");
        this.h.setOnClickListener((View.OnClickListener)new s(this));
        context = new LinearLayout.LayoutParams(0, -2, 1.0f);
        context.leftMargin = 8;
        this.h.setLayoutParams((ViewGroup.LayoutParams)context);
        textView.addView((View)this.h);
        linearLayout.addView((View)textView);
        return linearLayout;
    }

    public final void f() {
        this.f.removeAllViews();
        Context context = this.getContext();
        for (d d3 : this.c) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            linearLayout.setPadding(0, 16, 0, 16);
            linearLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, -2));
            TextView textView = new TextView(context);
            textView.setText((CharSequence)d3.a());
            textView.setTextSize(16.0f);
            textView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(0, -2, 1.0f));
            linearLayout.addView((View)textView);
            if (d3.c()) {
                d3 = new TextView(context);
                d3.setText("Installed");
                d3.setTextColor(-11751600);
                linearLayout.addView((View)d3);
            } else {
                textView = new TextView(context);
                textView.setText((CharSequence)"Not installed");
                textView.setTextColor(-6381922);
                textView.setPadding(0, 0, 16, 0);
                linearLayout.addView((View)textView);
                textView = new Button(context);
                textView.setText((CharSequence)"Install");
                textView.setOnClickListener((View.OnClickListener)new q(this, (Button)textView, d3));
                linearLayout.addView((View)textView);
            }
            this.f.addView((View)linearLayout);
        }
    }

    public void g(b b3) {
        this.e = b3;
    }

    public final void h() {
        boolean bl;
        Button button;
        block1: {
            button = this.c.iterator();
            while (button.hasNext()) {
                if (((d)button.next()).c()) continue;
                bl = false;
                break block1;
            }
            bl = true;
        }
        this.g.setEnabled(bl);
        button = this.g;
        float f3 = bl ? 1.0f : 0.5f;
        button.setAlpha(f3);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.setContentView(this.e());
        this.setCancelable(false);
        this.f();
        this.h();
    }

    public static interface a {
        public void a(boolean var1);
    }

    public static interface b {
        public void a();

        public void onCancel();
    }

    public static interface c {
        public void a(d var1, a var2);
    }

    public static class d {
        public String a;
        public String b;
        public boolean c;

        public d(String string, String string2, boolean bl) {
            this.a = string;
            this.b = string2;
            this.c = bl;
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }

        public boolean c() {
            return this.c;
        }

        public void d(boolean bl) {
            this.c = bl;
        }
    }
}

