/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.textfield;

import com.google.android.material.textfield.TextInputLayout;

public final class a0
implements Runnable {
    public final TextInputLayout c;

    public /* synthetic */ a0(TextInputLayout textInputLayout) {
        this.c = textInputLayout;
    }

    @Override
    public final void run() {
        TextInputLayout.a(this.c);
    }
}

