/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 */
package com.google.android.material.navigation;

import android.graphics.Canvas;
import com.google.android.material.navigation.NavigationView;
import d2.a;

public final class i
implements a.a {
    public final NavigationView a;

    public /* synthetic */ i(NavigationView navigationView) {
        this.a = navigationView;
    }

    @Override
    public final void a(Canvas canvas) {
        NavigationView.f(this.a, canvas);
    }
}

