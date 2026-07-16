/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.c;

class ViewOffsetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    public c c;
    public int d = 0;
    public int e = 0;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int I() {
        c c3 = this.c;
        if (c3 != null) {
            return c3.c();
        }
        return 0;
    }

    public void J(CoordinatorLayout coordinatorLayout, View view, int n3) {
        coordinatorLayout.M(view, n3);
    }

    public boolean K(int n3) {
        c c3 = this.c;
        if (c3 != null) {
            return c3.f(n3);
        }
        this.d = n3;
        return false;
    }

    @Override
    public boolean p(CoordinatorLayout coordinatorLayout, View view, int n3) {
        this.J(coordinatorLayout, view, n3);
        if (this.c == null) {
            this.c = new c(view);
        }
        this.c.d();
        this.c.a();
        n3 = this.d;
        if (n3 != 0) {
            this.c.f(n3);
            this.d = 0;
        }
        if ((n3 = this.e) != 0) {
            this.c.e(n3);
            this.e = 0;
        }
        return true;
    }
}

