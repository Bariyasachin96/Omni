/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup$MarginLayoutParams
 */
package com.google.android.material.search;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.search.SearchView;
import o0.f0;
import o0.z1;

public final class i
implements f0 {
    public final ViewGroup.MarginLayoutParams a;
    public final int b;
    public final int c;

    public /* synthetic */ i(ViewGroup.MarginLayoutParams marginLayoutParams, int n3, int n4) {
        this.a = marginLayoutParams;
        this.b = n3;
        this.c = n4;
    }

    @Override
    public final z1 a(View view, z1 z12) {
        return SearchView.f(this.a, this.b, this.c, view, z12);
    }
}

