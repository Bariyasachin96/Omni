/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.google.android.material.search;

import android.view.MotionEvent;
import android.view.View;
import com.google.android.material.search.SearchView;

public final class o
implements View.OnTouchListener {
    public final SearchView c;

    public /* synthetic */ o(SearchView searchView) {
        this.c = searchView;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return SearchView.g(this.c, view, motionEvent);
    }
}

