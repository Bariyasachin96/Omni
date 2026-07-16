/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener
 */
package b2;

import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.google.android.material.behavior.HideViewOnScrollBehavior;

public final class e
implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final HideViewOnScrollBehavior a;
    public final View b;

    public /* synthetic */ e(HideViewOnScrollBehavior hideViewOnScrollBehavior, View view) {
        this.a = hideViewOnScrollBehavior;
        this.b = view;
    }

    public final void onTouchExplorationStateChanged(boolean bl) {
        HideViewOnScrollBehavior.I(this.a, this.b, bl);
    }
}

