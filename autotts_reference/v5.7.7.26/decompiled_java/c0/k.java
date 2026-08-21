/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.widget.RemoteViews
 */
package c0;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.IconCompat;
import c0.l;
import java.util.ArrayList;

public class k {
    public boolean A;
    public String B;
    public Bundle C;
    public int D = 0;
    public int E = 0;
    public Notification F;
    public RemoteViews G;
    public RemoteViews H;
    public RemoteViews I;
    public String J;
    public int K = 0;
    public String L;
    public long M;
    public int N = 0;
    public int O = 0;
    public boolean P;
    public Notification Q;
    public boolean R;
    public Object S;
    public ArrayList T;
    public Context a;
    public ArrayList b = new ArrayList();
    public ArrayList c = new ArrayList();
    public ArrayList d = new ArrayList();
    public CharSequence e;
    public CharSequence f;
    public PendingIntent g;
    public PendingIntent h;
    public RemoteViews i;
    public IconCompat j;
    public CharSequence k;
    public int l;
    public int m;
    public boolean n = true;
    public boolean o;
    public CharSequence p;
    public CharSequence q;
    public CharSequence[] r;
    public int s;
    public int t;
    public boolean u;
    public String v;
    public boolean w;
    public String x;
    public boolean y = false;
    public boolean z;

    public k(Context context, String string) {
        Notification notification;
        this.Q = notification = new Notification();
        this.a = context;
        this.J = string;
        notification.when = System.currentTimeMillis();
        this.Q.audioStreamType = -1;
        this.m = 0;
        this.T = new ArrayList();
        this.P = true;
    }

    public static CharSequence c(CharSequence charSequence) {
        if (charSequence == null) {
            return charSequence;
        }
        CharSequence charSequence2 = charSequence;
        if (charSequence.length() > 5120) {
            charSequence2 = charSequence.subSequence(0, 5120);
        }
        return charSequence2;
    }

    public Notification a() {
        return new l(this).b();
    }

    public Bundle b() {
        if (this.C == null) {
            this.C = new Bundle();
        }
        return this.C;
    }

    public k d(CharSequence charSequence) {
        this.e = c0.k.c(charSequence);
        return this;
    }

    public final void e(int n3, boolean bl) {
        if (bl) {
            Notification notification = this.Q;
            notification.flags = n3 | notification.flags;
            return;
        }
        Notification notification = this.Q;
        notification.flags = ~n3 & notification.flags;
    }

    public k f(boolean bl) {
        this.e(2, bl);
        return this;
    }

    public k g(int n3) {
        this.Q.icon = n3;
        return this;
    }
}

