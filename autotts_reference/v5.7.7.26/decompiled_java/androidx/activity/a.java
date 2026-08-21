/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.window.BackEvent
 */
package androidx.activity;

import android.window.BackEvent;
import o3.k;

public final class a {
    public static final a a = new a();

    public final BackEvent a(float f3, float f4, float f5, int n3) {
        return new BackEvent(f3, f4, f5, n3);
    }

    public final float b(BackEvent backEvent) {
        k.e(backEvent, "backEvent");
        return backEvent.getProgress();
    }

    public final int c(BackEvent backEvent) {
        k.e(backEvent, "backEvent");
        return backEvent.getSwipeEdge();
    }

    public final float d(BackEvent backEvent) {
        k.e(backEvent, "backEvent");
        return backEvent.getTouchX();
    }

    public final float e(BackEvent backEvent) {
        k.e(backEvent, "backEvent");
        return backEvent.getTouchY();
    }
}

