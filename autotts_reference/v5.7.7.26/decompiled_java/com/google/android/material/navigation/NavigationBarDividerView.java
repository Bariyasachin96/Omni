/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.material.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.navigation.g;
import z1.i;

public class NavigationBarDividerView
extends FrameLayout
implements g {
    public boolean c;
    public boolean d;
    public boolean e;

    public NavigationBarDividerView(Context context) {
        super(context);
        LayoutInflater.from((Context)context).inflate(i.m3_navigation_menu_divider, (ViewGroup)this, true);
        this.a();
    }

    public void a() {
        int n3 = this.e && (this.c || !this.d) ? 0 : 8;
        this.setVisibility(n3);
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public void d(androidx.appcompat.view.menu.g g3, int n3) {
        this.a();
    }

    @Override
    public androidx.appcompat.view.menu.g getItemData() {
        return null;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
    }

    public void setCheckable(boolean bl) {
    }

    public void setChecked(boolean bl) {
    }

    public void setDividersEnabled(boolean bl) {
        this.e = bl;
        this.a();
    }

    public void setEnabled(boolean bl) {
    }

    @Override
    public void setExpanded(boolean bl) {
        this.c = bl;
        this.a();
    }

    public void setIcon(Drawable drawable) {
    }

    @Override
    public void setOnlyShowWhenExpanded(boolean bl) {
        this.d = bl;
        this.a();
    }

    public void setShortcut(boolean bl, char c3) {
    }

    public void setTitle(CharSequence charSequence) {
    }
}

