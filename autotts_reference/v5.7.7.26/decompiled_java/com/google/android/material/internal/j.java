/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.Checkable
 */
package com.google.android.material.internal;

import android.widget.Checkable;

public interface j
extends Checkable {
    public int getId();

    public void setInternalOnCheckedChangeListener(a var1);

    public static interface a {
        public void a(Object var1, boolean var2);
    }
}

