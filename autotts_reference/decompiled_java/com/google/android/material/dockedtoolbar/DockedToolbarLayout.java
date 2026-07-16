/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package com.google.android.material.dockedtoolbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.m0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import g0.b;
import o0.z1;
import v2.i;
import v2.o;
import y2.a;
import z1.c;
import z1.l;
import z1.m;

public class DockedToolbarLayout
extends FrameLayout {
    public static final int e = l.Widget_Material3_DockedToolbar;
    public Boolean c;
    public Boolean d;

    public DockedToolbarLayout(Context context) {
        this(context, null);
    }

    public DockedToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.dockedToolbarStyle);
    }

    public DockedToolbarLayout(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, e);
    }

    public DockedToolbarLayout(Context object, AttributeSet object2, int n3, int n4) {
        super(a.d((Context)object, (AttributeSet)object2, n3, n4), (AttributeSet)object2, n3);
        Context context = this.getContext();
        object = z.j(context, (AttributeSet)object2, m.DockedToolbar, n3, n4, new int[0]);
        int n5 = m.DockedToolbar_backgroundTint;
        if (((m0)object).s(n5)) {
            n5 = ((m0)object).b(n5, 0);
            object2 = new i(o.e(context, (AttributeSet)object2, n3, n4).m());
            ((i)object2).i0(ColorStateList.valueOf((int)n5));
            this.setBackground((Drawable)object2);
        }
        if (((m0)object).s(n3 = m.DockedToolbar_paddingTopSystemWindowInsets)) {
            this.c = ((m0)object).a(n3, true);
        }
        if (((m0)object).s(n3 = m.DockedToolbar_paddingBottomSystemWindowInsets)) {
            this.d = ((m0)object).a(n3, true);
        }
        c0.f((View)this, new c0.d(this){
            public final DockedToolbarLayout a;
            {
                this.a = dockedToolbarLayout;
            }

            @Override
            public z1 a(View view, z1 z12, c0.e e3) {
                if (this.a.c != null && this.a.d != null && !this.a.c.booleanValue() && !this.a.d.booleanValue()) {
                    return z12;
                }
                b b3 = z12.f(z1.m.e() | z1.m.a() | z1.m.b());
                int n3 = b3.d;
                int n4 = b3.b;
                b3 = view.getLayoutParams();
                int n5 = this.a.d((ViewGroup.LayoutParams)b3, 48) && this.a.c == null && this.a.getFitsSystemWindows() ? n4 : 0;
                int n6 = this.a.d((ViewGroup.LayoutParams)b3, 80) && this.a.d == null && this.a.getFitsSystemWindows() ? n3 : 0;
                if (this.a.d != null) {
                    n6 = this.a.d != false ? n3 : 0;
                }
                if (this.a.c != null) {
                    n5 = this.a.c != false ? n4 : 0;
                }
                e3.b += n5;
                e3.d += n6;
                e3.a(view);
                return z12;
            }
        });
        this.setImportantForAccessibility(1);
        ((m0)object).x();
    }

    public final boolean d(ViewGroup.LayoutParams layoutParams, int n3) {
        if (layoutParams instanceof CoordinatorLayout.e) {
            return (((CoordinatorLayout.e)layoutParams).c & n3) == n3;
        }
        return layoutParams instanceof FrameLayout.LayoutParams && (((FrameLayout.LayoutParams)layoutParams).gravity & n3) == n3;
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (View.MeasureSpec.getMode((int)n4) != 0x40000000) {
            int n5 = this.getChildCount();
            int n6 = Math.max(this.getMeasuredHeight(), this.getSuggestedMinimumHeight() + this.getPaddingTop() + this.getPaddingBottom());
            for (n4 = 0; n4 < n5; ++n4) {
                this.measureChild(this.getChildAt(n4), n3, View.MeasureSpec.makeMeasureSpec((int)n6, (int)0x40000000));
            }
            this.setMeasuredDimension(this.getMeasuredWidth(), n6);
        }
    }
}

