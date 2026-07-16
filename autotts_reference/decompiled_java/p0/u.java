/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.accessibility.AccessibilityRecord
 */
package p0;

import android.view.View;
import android.view.accessibility.AccessibilityRecord;

public abstract class u {
    public static void a(AccessibilityRecord accessibilityRecord, int n3) {
        accessibilityRecord.setMaxScrollX(n3);
    }

    public static void b(AccessibilityRecord accessibilityRecord, int n3) {
        accessibilityRecord.setMaxScrollY(n3);
    }

    public static void c(AccessibilityRecord accessibilityRecord, View view, int n3) {
        accessibilityRecord.setSource(view, n3);
    }
}

