/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 *  android.graphics.Rect
 */
package com.google.android.material.internal;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

public class s
implements TypeEvaluator {
    public final Rect a;

    public s(Rect rect) {
        this.a = rect;
    }

    public Rect a(float f3, Rect rect, Rect rect2) {
        int n3 = rect.left;
        int n4 = (int)((float)(rect2.left - n3) * f3);
        int n5 = rect.top;
        int n6 = (int)((float)(rect2.top - n5) * f3);
        int n7 = rect.right;
        int n8 = (int)((float)(rect2.right - n7) * f3);
        int n9 = rect.bottom;
        int n10 = (int)((float)(rect2.bottom - n9) * f3);
        this.a.set(n3 + n4, n5 + n6, n7 + n8, n9 + n10);
        return this.a;
    }
}

