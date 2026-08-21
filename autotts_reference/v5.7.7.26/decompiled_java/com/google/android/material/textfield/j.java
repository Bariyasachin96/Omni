/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.google.android.material.textfield;

import android.view.MotionEvent;
import android.view.View;
import com.google.android.material.textfield.p;

public final class j
implements View.OnTouchListener {
    public final p c;

    public /* synthetic */ j(p p3) {
        this.c = p3;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return p.z(this.c, view, motionEvent);
    }
}

