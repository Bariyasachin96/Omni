/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package c3;

import android.widget.CompoundButton;
import c3.j;
import c3.o;

public final class h
implements CompoundButton.OnCheckedChangeListener {
    public final o a;

    public /* synthetic */ h(o o3) {
        this.a = o3;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        j.F1(this.a, compoundButton, bl);
    }
}

