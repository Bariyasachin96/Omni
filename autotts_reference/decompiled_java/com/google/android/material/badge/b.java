/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.SparseArray
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.google.android.material.badge;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.badge.a;
import com.google.android.material.internal.ParcelableSparseArray;

public abstract class b {
    public static void a(a a4, View view) {
        b.b(a4, view, null);
    }

    public static void b(a a4, View view, FrameLayout frameLayout) {
        b.f(a4, view, frameLayout);
        if (a4.k() != null) {
            a4.k().setForeground((Drawable)a4);
            return;
        }
        view.getOverlay().add((Drawable)a4);
    }

    public static SparseArray c(Context context, ParcelableSparseArray parcelableSparseArray) {
        SparseArray sparseArray = new SparseArray(parcelableSparseArray.size());
        for (int i3 = 0; i3 < parcelableSparseArray.size(); ++i3) {
            int n3 = parcelableSparseArray.keyAt(i3);
            Object object = (BadgeState.State)parcelableSparseArray.valueAt(i3);
            object = object != null ? a.f(context, (BadgeState.State)object) : null;
            sparseArray.put(n3, object);
        }
        return sparseArray;
    }

    public static ParcelableSparseArray d(SparseArray sparseArray) {
        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
        for (int i3 = 0; i3 < sparseArray.size(); ++i3) {
            int n3 = sparseArray.keyAt(i3);
            Object object = (a)sparseArray.valueAt(i3);
            object = object != null ? ((a)object).u() : null;
            parcelableSparseArray.put(n3, object);
        }
        return parcelableSparseArray;
    }

    public static void e(a a4, View view) {
        if (a4 == null) {
            return;
        }
        if (a4.k() != null) {
            a4.k().setForeground(null);
            return;
        }
        view.getOverlay().remove((Drawable)a4);
    }

    public static void f(a a4, View view, FrameLayout frameLayout) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        a4.setBounds(rect);
        a4.Q(view, frameLayout);
    }

    public static void g(Rect rect, float f3, float f4, float f5, float f6) {
        rect.set((int)(f3 - f5), (int)(f4 - f6), (int)(f3 + f5), (int)(f4 + f6));
    }
}

