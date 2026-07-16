/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener
 */
package com.google.android.material.textfield;

import android.view.accessibility.AccessibilityManager;
import com.google.android.material.textfield.p;

public final class n
implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final p a;

    public /* synthetic */ n(p p3) {
        this.a = p3;
    }

    public final void onTouchExplorationStateChanged(boolean bl) {
        p.w(this.a, bl);
    }
}

