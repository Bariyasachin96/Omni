/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.j;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationMenuView
extends RecyclerView
implements j {
    public NavigationMenuView(Context context) {
        this(context, null);
    }

    public NavigationMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.setLayoutManager(new LinearLayoutManager(context, 1, false));
    }

    @Override
    public void b(e e3) {
    }

    public int getWindowAnimations() {
        return 0;
    }
}

