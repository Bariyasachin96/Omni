/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 */
package c3;

import android.view.View;
import android.widget.Button;
import c3.t;

public final class p
implements View.OnClickListener {
    public final t c;
    public final Button d;
    public final t.d e;

    public /* synthetic */ p(t t3, Button button, t.d d3) {
        this.c = t3;
        this.d = button;
        this.e = d3;
    }

    public final void onClick(View view) {
        t.a(this.c, this.d, this.e, view);
    }
}

