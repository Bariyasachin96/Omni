/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.FrameLayout
 */
package com.google.android.material.floatingtoolbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.m0;
import com.google.android.material.internal.z;
import o0.f0;
import o0.x0;
import o0.z1;
import v2.i;
import v2.o;
import y2.a;
import z1.c;
import z1.l;
import z1.m;

public class FloatingToolbarLayout
extends FrameLayout {
    public static final String l = "FloatingToolbarLayout";
    public static final int m = z1.l.Widget_Material3_FloatingToolbar;
    public boolean c;
    public boolean d;
    public boolean e;
    public boolean f;
    public Rect g;
    public int h;
    public int i;
    public int j;
    public int k;

    public FloatingToolbarLayout(Context context) {
        this(context, null);
    }

    public FloatingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.floatingToolbarStyle);
    }

    public FloatingToolbarLayout(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, m);
    }

    public FloatingToolbarLayout(Context object, AttributeSet object2, int n3, int n4) {
        super(a.d((Context)object, (AttributeSet)object2, n3, n4), (AttributeSet)object2, n3);
        Context context = this.getContext();
        object = z.j(context, (AttributeSet)object2, z1.m.FloatingToolbar, n3, n4, new int[0]);
        int n5 = z1.m.FloatingToolbar_backgroundTint;
        if (((m0)object).s(n5)) {
            n5 = ((m0)object).b(n5, 0);
            object2 = new i(o.e(context, (AttributeSet)object2, n3, n4).m());
            ((i)object2).i0(ColorStateList.valueOf((int)n5));
            this.setBackground((Drawable)object2);
        }
        this.c = ((m0)object).a(z1.m.FloatingToolbar_marginLeftSystemWindowInsets, true);
        this.d = ((m0)object).a(z1.m.FloatingToolbar_marginTopSystemWindowInsets, false);
        this.e = ((m0)object).a(z1.m.FloatingToolbar_marginRightSystemWindowInsets, true);
        this.f = ((m0)object).a(z1.m.FloatingToolbar_marginBottomSystemWindowInsets, true);
        x0.r0((View)this, new f0(this){
            public final FloatingToolbarLayout a;
            {
                this.a = floatingToolbarLayout;
            }

            @Override
            public z1 a(View object, z1 z12) {
                if (!(this.a.c || this.a.e || this.a.d || this.a.f)) {
                    return z12;
                }
                object = z12.f(z1.m.e() | z1.m.a() | z1.m.b());
                FloatingToolbarLayout.e(this.a, object.d);
                FloatingToolbarLayout.f(this.a, object.b);
                FloatingToolbarLayout.g(this.a, object.c);
                FloatingToolbarLayout.h(this.a, object.a);
                this.a.j();
                return z12;
            }
        });
        ((m0)object).x();
    }

    public static /* synthetic */ int e(FloatingToolbarLayout floatingToolbarLayout, int n3) {
        floatingToolbarLayout.h = n3;
        return n3;
    }

    public static /* synthetic */ int f(FloatingToolbarLayout floatingToolbarLayout, int n3) {
        floatingToolbarLayout.i = n3;
        return n3;
    }

    public static /* synthetic */ int g(FloatingToolbarLayout floatingToolbarLayout, int n3) {
        floatingToolbarLayout.k = n3;
        return n3;
    }

    public static /* synthetic */ int h(FloatingToolbarLayout floatingToolbarLayout, int n3) {
        floatingToolbarLayout.j = n3;
        return n3;
    }

    public final void j() {
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        Rect rect = this.g;
        if (rect == null) {
            Log.w((String)l, (String)"Unable to update margins because original view margins are not set");
            return;
        }
        int n3 = rect.left;
        boolean bl = this.c;
        int n4 = 0;
        int n5 = bl ? this.j : 0;
        n3 += n5;
        int n6 = rect.right;
        n5 = this.e ? this.k : 0;
        n6 += n5;
        int n7 = rect.top;
        n5 = this.d ? this.i : 0;
        n7 += n5;
        int n8 = rect.bottom;
        n5 = n4;
        if (this.f) {
            n5 = this.h;
        }
        n5 = n8 + n5;
        rect = (ViewGroup.MarginLayoutParams)layoutParams;
        if (rect.bottomMargin == n5 && rect.leftMargin == n3 && rect.rightMargin == n6 && rect.topMargin == n7) {
            return;
        }
        rect.bottomMargin = n5;
        rect.leftMargin = n3;
        rect.rightMargin = n6;
        rect.topMargin = n7;
        this.requestLayout();
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            this.g = new Rect(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
            this.j();
            return;
        }
        this.g = null;
    }
}

