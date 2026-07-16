/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.util.Log
 *  android.view.View
 *  android.view.animation.PathInterpolator
 */
package p2;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.activity.b;
import p2.k;
import z1.c;

public abstract class a {
    public final TimeInterpolator a = new PathInterpolator(0.1f, 0.1f, 0.0f, 1.0f);
    public final View b;
    public final int c;
    public final int d;
    public final int e;
    public b f;

    public a(View view) {
        this.b = view;
        view = view.getContext();
        this.c = k.f((Context)view, z1.c.motionDurationMedium2, 300);
        this.d = k.f((Context)view, z1.c.motionDurationShort3, 150);
        this.e = k.f((Context)view, z1.c.motionDurationShort2, 100);
    }

    public float a(float f3) {
        return this.a.getInterpolation(f3);
    }

    public b b() {
        if (this.f == null) {
            Log.w((String)"MaterialBackHelper", (String)"Must call startBackProgress() and updateBackProgress() before cancelBackProgress()");
        }
        b b3 = this.f;
        this.f = null;
        return b3;
    }

    public b c() {
        b b3 = this.f;
        this.f = null;
        return b3;
    }

    public void d(b b3) {
        this.f = b3;
    }

    public b e(b b3) {
        if (this.f == null) {
            Log.w((String)"MaterialBackHelper", (String)"Must call startBackProgress() before updateBackProgress()");
        }
        b b4 = this.f;
        this.f = b3;
        return b4;
    }
}

