/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewTreeObserver$OnPreDrawListener
 */
package com.google.android.material.transformation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.util.List;
import l2.a;

@Deprecated
public abstract class ExpandableBehavior
extends CoordinatorLayout.Behavior<View> {
    public int c = 0;

    public ExpandableBehavior() {
    }

    public ExpandableBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean J(boolean bl) {
        if (bl) {
            int n3 = this.c;
            return n3 == 0 || n3 == 2;
            {
            }
        }
        return this.c == 1;
    }

    public a K(CoordinatorLayout coordinatorLayout, View view) {
        List list = coordinatorLayout.v(view);
        int n3 = list.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view2 = (View)list.get(i3);
            if (!this.i(coordinatorLayout, view, view2)) continue;
            return (a)view2;
        }
        return null;
    }

    public abstract boolean L(View var1, View var2, boolean var3, boolean var4);

    @Override
    public abstract boolean i(CoordinatorLayout var1, View var2, View var3);

    @Override
    public boolean l(CoordinatorLayout object, View view, View view2) {
        object = (a)view2;
        if (this.J(object.a())) {
            int n3 = object.a() ? 1 : 2;
            this.c = n3;
            return this.L((View)object, view, object.a(), true);
        }
        return false;
    }

    @Override
    public boolean p(CoordinatorLayout object, View view, int n3) {
        if (!view.isLaidOut() && (object = this.K((CoordinatorLayout)object, view)) != null && this.J(object.a())) {
            n3 = object.a() ? 1 : 2;
            this.c = n3;
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(this, view, n3, (a)object){
                public final View c;
                public final int d;
                public final a e;
                public final ExpandableBehavior f;
                {
                    this.f = expandableBehavior;
                    this.c = view;
                    this.d = n3;
                    this.e = a4;
                }

                public boolean onPreDraw() {
                    this.c.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)this);
                    if (this.f.c == this.d) {
                        ExpandableBehavior expandableBehavior = this.f;
                        a a4 = this.e;
                        expandableBehavior.L((View)a4, this.c, a4.a(), false);
                    }
                    return false;
                }
            });
        }
        return false;
    }
}

