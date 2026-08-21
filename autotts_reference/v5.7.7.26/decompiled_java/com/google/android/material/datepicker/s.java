/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.datepicker;

import androidx.fragment.app.Fragment;
import com.google.android.material.datepicker.r;
import java.util.AbstractCollection;
import java.util.LinkedHashSet;

public abstract class s
extends Fragment {
    public final LinkedHashSet e0 = new LinkedHashSet();

    public boolean E1(r r3) {
        return ((AbstractCollection)this.e0).add(r3);
    }

    public void F1() {
        ((AbstractCollection)this.e0).clear();
    }
}

