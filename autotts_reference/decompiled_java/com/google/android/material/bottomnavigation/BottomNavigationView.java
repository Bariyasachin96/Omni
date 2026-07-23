/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.m0;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
import o0.z1;
import z1.l;
import z1.m;

public class BottomNavigationView
extends NavigationBarView {
    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.bottomNavigationStyle);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, l.Widget_Design_BottomNavigationView);
    }

    public BottomNavigationView(Context object, AttributeSet attributeSet, int n3, int n4) {
        super((Context)object, attributeSet, n3, n4);
        object = z.j(this.getContext(), attributeSet, m.BottomNavigationView, n3, n4, new int[0]);
        this.setItemHorizontalTranslationEnabled(((m0)object).a(m.BottomNavigationView_itemHorizontalTranslationEnabled, true));
        n3 = m.BottomNavigationView_android_minHeight;
        if (((m0)object).s(n3)) {
            this.setMinimumHeight(((m0)object).f(n3, 0));
        }
        ((m0)object).x();
        this.g();
    }

    @Override
    public NavigationBarMenuView c(Context context) {
        return new BottomNavigationMenuView(context);
    }

    public final void g() {
        c0.f((View)this, new c0.d(this){
            public final BottomNavigationView a;
            {
                this.a = bottomNavigationView;
            }

            @Override
            public z1 a(View view, z1 z12, c0.e e3) {
                e3.d += z12.i();
                int n3 = view.getLayoutDirection();
                boolean bl = true;
                if (n3 != 1) {
                    bl = false;
                }
                int n4 = z12.j();
                n3 = z12.k();
                int n5 = e3.a;
                int n6 = bl ? n3 : n4;
                e3.a = n5 + n6;
                n6 = e3.c;
                if (!bl) {
                    n4 = n3;
                }
                e3.c = n6 + n4;
                e3.a(view);
                return z12;
            }
        });
    }

    @Override
    public int getMaxItemCount() {
        return 6;
    }

    public final int h(int n3) {
        int n4 = this.getSuggestedMinimumHeight();
        int n5 = n3;
        if (View.MeasureSpec.getMode((int)n3) != 0x40000000) {
            n5 = n3;
            if (n4 > 0) {
                int n6 = this.getPaddingTop();
                n5 = this.getPaddingBottom();
                n5 = View.MeasureSpec.makeMeasureSpec((int)Math.max(View.MeasureSpec.getSize((int)n3), n4 + (n6 + n5)), (int)Integer.MIN_VALUE);
            }
        }
        return n5;
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, this.h(n4));
        if (View.MeasureSpec.getMode((int)n4) != 0x40000000) {
            this.setMeasuredDimension(this.getMeasuredWidth(), Math.max(this.getMeasuredHeight(), this.getSuggestedMinimumHeight() + this.getPaddingTop() + this.getPaddingBottom()));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setItemHorizontalTranslationEnabled(boolean bl) {
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView)this.getMenuView();
        if (bottomNavigationMenuView.r() != bl) {
            bottomNavigationMenuView.setItemHorizontalTranslationEnabled(bl);
            this.getPresenter().g(false);
        }
    }

    @Deprecated
    public void setOnNavigationItemReselectedListener(b b3) {
        this.setOnItemReselectedListener(b3);
    }

    @Deprecated
    public void setOnNavigationItemSelectedListener(c c3) {
        this.setOnItemSelectedListener(c3);
    }

    public static interface b
    extends NavigationBarView.b {
    }

    public static interface c
    extends NavigationBarView.c {
    }
}

