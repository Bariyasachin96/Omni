/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.widget.j;
import com.google.android.material.navigation.g;
import z1.i;

public class NavigationBarSubheaderView
extends FrameLayout
implements g {
    public final TextView c;
    public boolean d;
    public boolean e;
    public androidx.appcompat.view.menu.g f;
    public ColorStateList g;

    public NavigationBarSubheaderView(Context context) {
        super(context);
        LayoutInflater.from((Context)context).inflate(i.m3_navigation_menu_subheader, (ViewGroup)this, true);
        this.c = (TextView)this.findViewById(z1.g.navigation_menu_subheader_label);
    }

    private void a() {
        androidx.appcompat.view.menu.g g3 = this.f;
        if (g3 != null) {
            int n3 = g3.isVisible() && (this.d || !this.e) ? 0 : 8;
            this.setVisibility(n3);
        }
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public void d(androidx.appcompat.view.menu.g g3, int n3) {
        this.f = g3;
        g3.setCheckable(false);
        this.c.setText(g3.getTitle());
        this.a();
    }

    @Override
    public androidx.appcompat.view.menu.g getItemData() {
        return this.f;
    }

    public void setCheckable(boolean bl) {
    }

    public void setChecked(boolean bl) {
    }

    public void setEnabled(boolean bl) {
    }

    @Override
    public void setExpanded(boolean bl) {
        this.d = bl;
        this.a();
    }

    public void setIcon(Drawable drawable) {
    }

    @Override
    public void setOnlyShowWhenExpanded(boolean bl) {
        this.e = bl;
        this.a();
    }

    public void setShortcut(boolean bl, char c3) {
    }

    public void setTextAppearance(int n3) {
        j.m(this.c, n3);
        ColorStateList colorStateList = this.g;
        if (colorStateList != null) {
            this.c.setTextColor(colorStateList);
        }
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.g = colorStateList;
        if (colorStateList != null) {
            this.c.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
    }
}

