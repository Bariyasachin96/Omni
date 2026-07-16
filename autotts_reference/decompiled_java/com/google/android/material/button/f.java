/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialSplitButton;

public final class f
implements MaterialButton.b {
    public final MaterialSplitButton a;

    public /* synthetic */ f(MaterialSplitButton materialSplitButton) {
        this.a = materialSplitButton;
    }

    @Override
    public final void a(MaterialButton materialButton, boolean bl) {
        MaterialSplitButton.p(this.a, materialButton, bl);
    }
}

