/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.internal.c0;
import com.google.android.material.tabs.TabLayout;

public class a {
    public static RectF a(TabLayout tabLayout, View view) {
        if (view == null) {
            return new RectF();
        }
        if (!tabLayout.D() && view instanceof TabLayout.TabView) {
            return a.b((TabLayout.TabView)view, 24);
        }
        return new RectF((float)view.getLeft(), (float)view.getTop(), (float)view.getRight(), (float)view.getBottom());
    }

    public static RectF b(TabLayout.TabView tabView, int n3) {
        int n4 = tabView.getContentWidth();
        int n5 = tabView.getContentHeight();
        int n6 = (int)c0.g(tabView.getContext(), n3);
        n3 = n4;
        if (n4 < n6) {
            n3 = n6;
        }
        n4 = (tabView.getLeft() + tabView.getRight()) / 2;
        n6 = (tabView.getTop() + tabView.getBottom()) / 2;
        int n7 = n5 / 2;
        n5 = n4 / 2;
        return new RectF((float)(n4 - (n3 /= 2)), (float)(n6 - n7), (float)(n3 + n4), (float)(n6 + n5));
    }

    public void c(TabLayout tabLayout, View view, Drawable drawable) {
        tabLayout = a.a(tabLayout, view);
        drawable.setBounds((int)((RectF)tabLayout).left, drawable.getBounds().top, (int)((RectF)tabLayout).right, drawable.getBounds().bottom);
    }

    public void d(TabLayout tabLayout, View view, View view2, float f3, Drawable drawable) {
        view = a.a(tabLayout, view);
        tabLayout = a.a(tabLayout, view2);
        drawable.setBounds(a2.a.c((int)view.left, (int)((RectF)tabLayout).left, f3), drawable.getBounds().top, a2.a.c((int)view.right, (int)((RectF)tabLayout).right, f3), drawable.getBounds().bottom);
    }
}

