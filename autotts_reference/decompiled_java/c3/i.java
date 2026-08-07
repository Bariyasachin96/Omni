/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package c3;

import android.widget.CompoundButton;
import c3.k;
import c3.p;

public final class i
implements CompoundButton.OnCheckedChangeListener {
    public final p a;

    public /* synthetic */ i(p p3) {
        this.a = p3;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        k.F1(this.a, compoundButton, bl);
    }
}

