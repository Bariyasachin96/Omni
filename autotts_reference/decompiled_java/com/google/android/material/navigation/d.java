/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 */
package com.google.android.material.navigation;

import android.view.View;
import com.google.android.material.navigation.NavigationBarItemView;

public final class d
implements View.OnLayoutChangeListener {
    public final NavigationBarItemView a;

    public /* synthetic */ d(NavigationBarItemView navigationBarItemView) {
        this.a = navigationBarItemView;
    }

    public final void onLayoutChange(View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        NavigationBarItemView.a(this.a, view, n3, n4, n5, n6, n7, n8, n9, n10);
    }
}

