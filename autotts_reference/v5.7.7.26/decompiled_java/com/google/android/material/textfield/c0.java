/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.text.StaticLayout$Builder
 */
package com.google.android.material.textfield;

import android.text.StaticLayout;
import com.google.android.material.internal.v;
import com.google.android.material.textfield.TextInputLayout;

public final class c0
implements v {
    public final TextInputLayout a;

    public /* synthetic */ c0(TextInputLayout textInputLayout) {
        this.a = textInputLayout;
    }

    @Override
    public final void a(StaticLayout.Builder builder) {
        TextInputLayout.b(this.a, builder);
    }
}

