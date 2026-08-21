/*
 * Decompiled with CFR 0.152.
 */
package androidx.activity;

import androidx.activity.ComponentActivity;

public final class d
implements Runnable {
    public final ComponentActivity c;

    public /* synthetic */ d(ComponentActivity componentActivity) {
        this.c = componentActivity;
    }

    @Override
    public final void run() {
        this.c.I();
    }
}

