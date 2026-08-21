/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.button;

import com.google.android.material.button.MaterialButton;

public final class b
implements Runnable {
    public final MaterialButton c;

    public /* synthetic */ b(MaterialButton materialButton) {
        this.c = materialButton;
    }

    @Override
    public final void run() {
        MaterialButton.a(this.c);
    }
}

