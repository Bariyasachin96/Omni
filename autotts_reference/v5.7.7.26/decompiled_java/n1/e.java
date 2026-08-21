/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.content.Context
 */
package n1;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;

public abstract class e {
    public static Animator a(Context context, int n3) {
        return AnimatorInflater.loadAnimator((Context)context, (int)n3);
    }
}

