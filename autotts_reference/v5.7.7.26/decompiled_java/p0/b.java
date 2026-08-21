/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.AccessibilityEvent
 */
package p0;

import android.view.accessibility.AccessibilityEvent;

public abstract class b {
    public static int a(AccessibilityEvent accessibilityEvent) {
        return accessibilityEvent.getContentChangeTypes();
    }

    public static void b(AccessibilityEvent accessibilityEvent, int n3) {
        accessibilityEvent.setContentChangeTypes(n3);
    }
}

