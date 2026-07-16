/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package com.google.android.material.slider;

import android.view.ViewTreeObserver;
import com.google.android.material.slider.BaseSlider;

public final class d
implements ViewTreeObserver.OnGlobalLayoutListener {
    public final BaseSlider c;

    public /* synthetic */ d(BaseSlider baseSlider) {
        this.c = baseSlider;
    }

    public final void onGlobalLayout() {
        BaseSlider.d(this.c);
    }
}

