/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package com.google.android.material.slider;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.slider.BaseSlider;
import z1.c;

public class Slider
extends BaseSlider<Slider, Object, Object> {
    public Slider(Context context) {
        this(context, null);
    }

    public Slider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.sliderStyle);
    }

    public Slider(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        context = context.obtainStyledAttributes(attributeSet, new int[]{16842788});
        if (context.hasValue(0)) {
            this.setValue(context.getFloat(0, 0.0f));
        }
        context.recycle();
    }

    @Override
    public boolean F0() {
        if (this.getActiveThumbIndex() != -1) {
            return true;
        }
        this.setActiveThumbIndex(0);
        return true;
    }

    public float getValue() {
        return this.getValues().get(0).floatValue();
    }

    @Override
    public void setCustomThumbDrawable(int n3) {
        super.setCustomThumbDrawable(n3);
    }

    @Override
    public void setCustomThumbDrawable(Drawable drawable) {
        super.setCustomThumbDrawable(drawable);
    }

    public void setValue(float f3) {
        this.setValues(Float.valueOf(f3));
    }
}

