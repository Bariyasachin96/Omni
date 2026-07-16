/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package t0;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import t0.a;

public abstract class c
extends a {
    public int k;
    public int l;
    public LayoutInflater m;

    public c(Context context, int n3, Cursor cursor, boolean bl) {
        super(context, cursor, bl);
        this.l = n3;
        this.k = n3;
        this.m = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    @Override
    public View f(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.m.inflate(this.l, viewGroup, false);
    }

    @Override
    public View g(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.m.inflate(this.k, viewGroup, false);
    }
}

