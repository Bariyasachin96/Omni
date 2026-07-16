/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.google.android.material.search;

import android.view.View;
import com.google.android.material.search.SearchView;

public final class l
implements View.OnClickListener {
    public final SearchView c;

    public /* synthetic */ l(SearchView searchView) {
        this.c = searchView;
    }

    public final void onClick(View view) {
        SearchView.e(this.c, view);
    }
}

