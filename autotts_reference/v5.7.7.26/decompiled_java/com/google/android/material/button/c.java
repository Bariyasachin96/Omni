/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonGroup;
import java.util.Comparator;

public final class c
implements Comparator {
    public final MaterialButtonGroup c;

    public /* synthetic */ c(MaterialButtonGroup materialButtonGroup) {
        this.c = materialButtonGroup;
    }

    public final int compare(Object object, Object object2) {
        return MaterialButtonGroup.a(this.c, (MaterialButton)object, (MaterialButton)object2);
    }
}

