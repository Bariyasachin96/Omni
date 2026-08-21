/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package com.google.android.material.navigation;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.l;
import com.google.android.material.navigation.h;

public final class e
extends androidx.appcompat.view.menu.e {
    public final Class B;
    public final int C;
    public final boolean D;

    public e(Context context, Class clazz, int n3, boolean bl) {
        super(context);
        this.B = clazz;
        this.C = n3;
        this.D = bl;
    }

    @Override
    public MenuItem a(int n3, int n4, int n5, CharSequence charSequence) {
        if (this.size() + 1 <= this.C) {
            this.i0();
            charSequence = super.a(n3, n4, n5, charSequence);
            this.h0();
            return charSequence;
        }
        String string = this.B.getSimpleName();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Maximum number of items supported by ");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(" is ");
        ((StringBuilder)charSequence).append(this.C);
        ((StringBuilder)charSequence).append(". Limit can be checked with ");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append("#getMaxItemCount()");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public SubMenu addSubMenu(int n3, int n4, int n5, CharSequence object) {
        if (this.D) {
            g g3 = (g)this.a(n3, n4, n5, (CharSequence)object);
            object = new h(this.w(), this, g3);
            g3.x((l)object);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.B.getSimpleName());
        ((StringBuilder)object).append(" does not support submenus");
        throw new UnsupportedOperationException(((StringBuilder)object).toString());
    }
}

