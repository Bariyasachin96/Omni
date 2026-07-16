/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 */
package n1;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public abstract class d {
    public static Interpolator a(Context context, int n3) {
        return AnimationUtils.loadInterpolator((Context)context, (int)n3);
    }
}

