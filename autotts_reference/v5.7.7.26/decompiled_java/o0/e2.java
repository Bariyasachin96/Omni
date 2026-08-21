/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.view.WindowInsets$Builder
 */
package o0;

import android.graphics.Insets;
import android.view.WindowInsets;

public abstract class e2 {
    public static /* bridge */ /* synthetic */ WindowInsets.Builder a(WindowInsets.Builder builder, Insets insets) {
        return builder.setMandatorySystemGestureInsets(insets);
    }
}

