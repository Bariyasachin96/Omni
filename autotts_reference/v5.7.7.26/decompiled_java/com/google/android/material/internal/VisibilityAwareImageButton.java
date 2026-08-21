/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.ImageButton
 */
package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class VisibilityAwareImageButton
extends ImageButton {
    public int c = this.getVisibility();

    public VisibilityAwareImageButton(Context context) {
        this(context, null);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
    }

    public final void b(int n3, boolean bl) {
        super.setVisibility(n3);
        if (bl) {
            this.c = n3;
        }
    }

    public final int getUserSetVisibility() {
        return this.c;
    }

    public void setVisibility(int n3) {
        this.b(n3, true);
    }
}

