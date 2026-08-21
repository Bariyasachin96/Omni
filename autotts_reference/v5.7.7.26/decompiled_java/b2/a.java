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
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

public final class a
implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final HideBottomViewOnScrollBehavior a;
    public final View b;

    public /* synthetic */ a(HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior, View view) {
        this.a = hideBottomViewOnScrollBehavior;
        this.b = view;
    }

    public final void onTouchExplorationStateChanged(boolean bl) {
        HideBottomViewOnScrollBehavior.I(this.a, this.b, bl);
    }
}

