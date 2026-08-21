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

public final class n
implements View.OnTouchListener {
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return SearchView.m(view, motionEvent);
    }
}

