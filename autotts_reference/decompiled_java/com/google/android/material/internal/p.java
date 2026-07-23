/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.SubMenu
 */
package com.google.android.material.internal;

import android.content.Context;
import android.view.SubMenu;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.l;
import com.google.android.material.internal.r;

public class p
extends e {
    public p(Context context) {
        super(context);
    }

    @Override
    public SubMenu addSubMenu(int n3, int n4, int n5, CharSequence object) {
        g g3 = (g)this.a(n3, n4, n5, (CharSequence)object);
        object = new r(this.w(), this, g3);
        g3.x((l)object);
        return object;
    }
}

