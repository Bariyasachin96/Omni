/*
 * Decompiled with CFR 0.152.
 */
package androidx.activity;

import androidx.activity.ComponentActivity;

public final class h
implements Runnable {
    public final ComponentActivity.g c;

    public /* synthetic */ h(ComponentActivity.g g3) {
        this.c = g3;
    }

    @Override
    public final void run() {
        ComponentActivity.g.c(this.c);
    }
}

