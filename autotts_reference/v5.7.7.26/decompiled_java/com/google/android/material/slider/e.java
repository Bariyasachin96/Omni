/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.slider;

import com.google.android.material.slider.BaseSlider;

public final class e
implements Runnable {
    public final BaseSlider c;

    public /* synthetic */ e(BaseSlider baseSlider) {
        this.c = baseSlider;
    }

    @Override
    public final void run() {
        BaseSlider.a(this.c);
    }
}

