/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.search;

import com.google.android.material.search.SearchView;

public final class g
implements Runnable {
    public final SearchView c;

    public /* synthetic */ g(SearchView searchView) {
        this.c = searchView;
    }

    @Override
    public final void run() {
        SearchView.h(this.c);
    }
}

