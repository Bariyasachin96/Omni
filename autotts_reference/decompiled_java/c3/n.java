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
import c3.r;

public final class n
implements View.OnClickListener {
    public final r c;
    public final Button d;
    public final r.d e;

    public /* synthetic */ n(r r3, Button button, r.d d3) {
        this.c = r3;
        this.d = button;
        this.e = d3;
    }

    public final void onClick(View view) {
        r.a(this.c, this.d, this.e, view);
    }
}

