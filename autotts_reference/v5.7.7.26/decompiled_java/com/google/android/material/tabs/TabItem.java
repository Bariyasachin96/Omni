/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.m0;
import z1.m;

public class TabItem
extends View {
    public final CharSequence c;
    public final Drawable d;
    public final int e;

    public TabItem(Context context) {
        this(context, null);
    }

    public TabItem(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = m0.u((Context)object, attributeSet, m.TabItem);
        this.c = ((m0)object).p(m.TabItem_android_text);
        this.d = ((m0)object).g(m.TabItem_android_icon);
        this.e = ((m0)object).n(m.TabItem_android_layout, 0);
        ((m0)object).x();
    }
}

