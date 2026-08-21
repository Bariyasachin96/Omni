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
import c3.u;

public final class q
implements View.OnClickListener {
    public final u c;
    public final Button d;
    public final u.d e;

    public /* synthetic */ q(u u3, Button button, u.d d3) {
        this.c = u3;
        this.d = button;
        this.e = d3;
    }

    public final void onClick(View view) {
        u.a(this.c, this.d, this.e, view);
    }
}

