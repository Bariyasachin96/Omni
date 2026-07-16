/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.search;

import com.google.android.material.search.SearchView;

public final class m
implements Runnable {
    public final SearchView c;

    public /* synthetic */ m(SearchView searchView) {
        this.c = searchView;
    }

    @Override
    public final void run() {
        this.c.L();
    }
}

