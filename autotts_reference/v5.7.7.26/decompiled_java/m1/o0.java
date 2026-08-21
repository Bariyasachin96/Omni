/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package m1;

import android.view.View;
import m1.x;
import m1.y;

public abstract class o0
extends x {
    public static final String[] a = new String[]{"android:visibilityPropagation:visibility", "android:visibilityPropagation:center"};

    public static int d(y object, int n3) {
        if (object == null) {
            return -1;
        }
        object = (int[])((y)object).a.get("android:visibilityPropagation:center");
        if (object == null) {
            return -1;
        }
        return (int)object[n3];
    }

    @Override
    public void a(y y3) {
        reference var2_6;
        reference var2_5;
        View view = y3.b;
        Integer n3 = (Integer)y3.a.get("android:visibility:visibility");
        Object object = n3;
        if (n3 == null) {
            object = view.getVisibility();
        }
        y3.a.put("android:visibilityPropagation:visibility", object);
        object = new int[2];
        view.getLocationOnScreen((int[])object);
        object[0] = var2_5 = object[0] + Math.round(view.getTranslationX());
        object[0] = var2_5 + view.getWidth() / 2;
        object[1] = var2_6 = object[1] + Math.round(view.getTranslationY());
        object[1] = var2_6 + view.getHeight() / 2;
        y3.a.put("android:visibilityPropagation:center", object);
    }

    @Override
    public String[] b() {
        return a;
    }

    public int e(y object) {
        if (object == null) {
            return 8;
        }
        object = (Integer)((y)object).a.get("android:visibilityPropagation:visibility");
        if (object == null) {
            return 8;
        }
        return (Integer)object;
    }

    public int f(y y3) {
        return o0.d(y3, 0);
    }

    public int g(y y3) {
        return o0.d(y3, 1);
    }
}

