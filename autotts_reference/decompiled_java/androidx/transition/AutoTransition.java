/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package androidx.transition;

import android.content.Context;
import android.util.AttributeSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionSet;

public class AutoTransition
extends TransitionSet {
    public AutoTransition() {
        this.B0();
    }

    public AutoTransition(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.B0();
    }

    public final void B0() {
        this.y0(1);
        this.q0(new Fade(2)).q0(new ChangeBounds()).q0(new Fade(1));
    }
}

