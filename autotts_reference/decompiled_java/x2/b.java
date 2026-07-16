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
import com.google.android.material.tabs.a;

public class b
extends a {
    @Override
    public void d(TabLayout tabLayout, View view, View view2, float f3, Drawable drawable) {
        float f4 = f3 - 0.5f;
        float f5 = f4 == 0.0f ? 0 : (f4 < 0.0f ? -1 : 1);
        if (f5 >= 0) {
            view = view2;
        }
        tabLayout = a.a(tabLayout, view);
        f3 = f5 < 0 ? a2.a.b(1.0f, 0.0f, 0.0f, 0.5f, f3) : a2.a.b(0.0f, 1.0f, 0.5f, 1.0f, f3);
        drawable.setBounds((int)((RectF)tabLayout).left, drawable.getBounds().top, (int)((RectF)tabLayout).right, drawable.getBounds().bottom);
        drawable.setAlpha((int)(f3 * 255.0f));
    }
}

