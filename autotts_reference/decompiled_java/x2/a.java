/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package x2;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.google.android.material.tabs.TabLayout;

public class a
extends com.google.android.material.tabs.a {
    public static float e(float f3) {
        return (float)(1.0 - Math.cos((double)f3 * Math.PI / 2.0));
    }

    public static float f(float f3) {
        return (float)Math.sin((double)f3 * Math.PI / 2.0);
    }

    @Override
    public void d(TabLayout tabLayout, View view, View view2, float f3, Drawable drawable) {
        float f4;
        view = com.google.android.material.tabs.a.a(tabLayout, view);
        tabLayout = com.google.android.material.tabs.a.a(tabLayout, view2);
        if (view.left < ((RectF)tabLayout).left) {
            f4 = a.e(f3);
            f3 = a.f(f3);
        } else {
            f4 = a.f(f3);
            f3 = a.e(f3);
        }
        drawable.setBounds(a2.a.c((int)view.left, (int)((RectF)tabLayout).left, f4), drawable.getBounds().top, a2.a.c((int)view.right, (int)((RectF)tabLayout).right, f3), drawable.getBounds().bottom);
    }
}

