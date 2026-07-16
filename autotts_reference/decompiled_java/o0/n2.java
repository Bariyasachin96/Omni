/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.view.WindowInsets
 */
package o0;

import android.graphics.Insets;
import android.view.WindowInsets;

public abstract class n2 {
    public static /* bridge */ /* synthetic */ Insets a(WindowInsets windowInsets) {
        return windowInsets.getMandatorySystemGestureInsets();
    }
}

