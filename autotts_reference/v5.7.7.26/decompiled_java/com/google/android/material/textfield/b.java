/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 */
package com.google.android.material.textfield;

import android.view.View;
import com.google.android.material.textfield.f;

public final class b
implements View.OnFocusChangeListener {
    public final f c;

    public /* synthetic */ b(f f3) {
        this.c = f3;
    }

    public final void onFocusChange(View view, boolean bl) {
        f.w(this.c, view, bl);
    }
}

