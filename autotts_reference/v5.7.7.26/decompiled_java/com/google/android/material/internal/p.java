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
import com.google.android.material.internal.r;

public class p
extends e {
    public p(Context context) {
        super(context);
    }

    @Override
    public SubMenu addSubMenu(int n3, int n4, int n5, CharSequence object) {
        object = (g)this.a(n3, n4, n5, (CharSequence)object);
        r r3 = new r(this.w(), this, (g)object);
        ((g)object).x(r3);
        return r3;
    }
}

