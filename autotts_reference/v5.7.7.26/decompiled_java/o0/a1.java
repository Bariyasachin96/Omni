/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewConfiguration
 */
package o0;

import android.view.ViewConfiguration;
import n0.i;

public final class a1
implements i {
    public final ViewConfiguration a;

    public /* synthetic */ a1(ViewConfiguration viewConfiguration) {
        this.a = viewConfiguration;
    }

    @Override
    public final Object get() {
        return this.a.getScaledMinimumFlingVelocity();
    }
}

