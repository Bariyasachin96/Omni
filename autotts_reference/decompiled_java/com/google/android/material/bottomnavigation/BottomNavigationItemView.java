/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.material.bottomnavigation;

import android.content.Context;
import com.google.android.material.navigation.NavigationBarItemView;
import z1.e;
import z1.i;

public class BottomNavigationItemView
extends NavigationBarItemView {
    public BottomNavigationItemView(Context context) {
        super(context);
    }

    @Override
    public int getItemDefaultMarginResId() {
        return z1.e.design_bottom_navigation_margin;
    }

    @Override
    public int getItemLayoutResId() {
        return z1.i.design_bottom_navigation_item;
    }
}

