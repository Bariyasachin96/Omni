/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package c3;

import android.widget.CompoundButton;
import c3.i;
import c3.m;

public final class g
implements CompoundButton.OnCheckedChangeListener {
    public final m a;

    public /* synthetic */ g(m m3) {
        this.a = m3;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        i.F1(this.a, compoundButton, bl);
    }
}

