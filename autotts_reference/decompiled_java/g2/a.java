/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 */
package g2;

import android.widget.CompoundButton;
import com.google.android.material.chip.Chip;

public final class a
implements CompoundButton.OnCheckedChangeListener {
    public final Chip a;

    public /* synthetic */ a(Chip chip) {
        this.a = chip;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
        Chip.b(this.a, compoundButton, bl);
    }
}

