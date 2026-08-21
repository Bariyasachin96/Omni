/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.view.View
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.google.android.material.internal;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class a0 {
    public static final Comparator a = new Comparator(){

        public int a(View view, View view2) {
            return view.getTop() - view2.getTop();
        }
    };

    public static ActionMenuView a(Toolbar toolbar) {
        for (int i3 = 0; i3 < toolbar.getChildCount(); ++i3) {
            View view = toolbar.getChildAt(i3);
            if (!(view instanceof ActionMenuView)) continue;
            return (ActionMenuView)view;
        }
        return null;
    }

    public static ImageView b(Toolbar toolbar, Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        for (int i3 = 0; i3 < toolbar.getChildCount(); ++i3) {
            ImageView imageView;
            View view = toolbar.getChildAt(i3);
            if (!(view instanceof ImageView) || (view = (imageView = (ImageView)view).getDrawable()) == null || view.getConstantState() == null || !view.getConstantState().equals(drawable.getConstantState())) continue;
            return imageView;
        }
        return null;
    }

    public static ImageView c(Toolbar toolbar) {
        return a0.b(toolbar, toolbar.getLogo());
    }

    public static ImageButton d(Toolbar toolbar) {
        Drawable drawable = toolbar.getNavigationIcon();
        if (drawable == null) {
            return null;
        }
        for (int i3 = 0; i3 < toolbar.getChildCount(); ++i3) {
            View view = toolbar.getChildAt(i3);
            if (!(view instanceof ImageButton) || (view = (ImageButton)view).getDrawable() != drawable) continue;
            return view;
        }
        return null;
    }

    public static TextView e(Toolbar object) {
        if ((object = a0.f((Toolbar)object, ((Toolbar)object).getSubtitle())).isEmpty()) {
            return null;
        }
        return (TextView)Collections.max(object, a);
    }

    public static List f(Toolbar toolbar, CharSequence charSequence) {
        ArrayList<View> arrayList = new ArrayList<View>();
        for (int i3 = 0; i3 < toolbar.getChildCount(); ++i3) {
            View view = toolbar.getChildAt(i3);
            if (!(view instanceof TextView) || !TextUtils.equals((CharSequence)(view = (TextView)view).getText(), (CharSequence)charSequence)) continue;
            arrayList.add(view);
        }
        return arrayList;
    }

    public static TextView g(Toolbar object) {
        if ((object = a0.f((Toolbar)object, ((Toolbar)object).getTitle())).isEmpty()) {
            return null;
        }
        return (TextView)Collections.min(object, a);
    }
}

