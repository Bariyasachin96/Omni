/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package com.google.android.material.textfield;

import android.view.View;
import com.google.android.material.textfield.p;

public final class m
implements View.OnFocusChangeListener {
    public final p c;

    public /* synthetic */ m(p p3) {
        this.c = p3;
    }

    public final void onFocusChange(View view, boolean bl) {
        p.y(this.c, view, bl);
    }
}

