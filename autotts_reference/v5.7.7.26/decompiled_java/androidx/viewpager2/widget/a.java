/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.LayoutTransition
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.viewpager2.widget;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Arrays;
import java.util.Comparator;

public final class a {
    public static final ViewGroup.MarginLayoutParams b;
    public LinearLayoutManager a;

    static {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        b = marginLayoutParams = new ViewGroup.MarginLayoutParams(-1, -1);
        marginLayoutParams.setMargins(0, 0, 0, 0);
    }

    public a(LinearLayoutManager linearLayoutManager) {
        this.a = linearLayoutManager;
    }

    public static boolean c(View view) {
        if (view instanceof ViewGroup) {
            LayoutTransition layoutTransition = (view = (ViewGroup)view).getLayoutTransition();
            if (layoutTransition != null && layoutTransition.isChangingLayout()) {
                return true;
            }
            int n3 = view.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                if (!androidx.viewpager2.widget.a.c(view.getChildAt(i3))) continue;
                return true;
            }
        }
        return false;
    }

    public final boolean a() {
        Object object;
        Object object2;
        int n3 = this.a.O();
        if (n3 == 0) {
            return true;
        }
        Object object3 = this.a.p2() == 0 ? 1 : 0;
        int[][] nArray = new int[n3][2];
        for (object2 = 0; object2 < n3; ++object2) {
            View view = this.a.N((int)object2);
            if (view != null) {
                int n4;
                int n5;
                object = view.getLayoutParams();
                object = object instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams)object : b;
                int[] nArray2 = nArray[object2];
                if (object3 != 0) {
                    n5 = view.getLeft();
                    n4 = object.leftMargin;
                } else {
                    n5 = view.getTop();
                    n4 = object.topMargin;
                }
                nArray2[0] = n5 - n4;
                nArray2 = nArray[object2];
                if (object3 != 0) {
                    n4 = view.getRight();
                    n5 = object.rightMargin;
                } else {
                    n4 = view.getBottom();
                    n5 = object.bottomMargin;
                }
                nArray2[1] = n4 + n5;
                continue;
            }
            throw new IllegalStateException("null view contained in the view hierarchy");
        }
        Arrays.sort(nArray, new Comparator(this){
            public final a c;
            {
                this.c = a4;
            }

            public int a(int[] nArray, int[] nArray2) {
                return nArray[0] - nArray2[0];
            }
        });
        for (object3 = 1; object3 < n3; ++object3) {
            if (nArray[object3 - 1][1] == nArray[object3][0]) continue;
            return false;
        }
        object = nArray[0];
        object2 = object[1];
        object3 = object[0];
        return object3 <= 0 && nArray[n3 - 1][1] >= object2 - object3;
        {
        }
    }

    public final boolean b() {
        int n3 = this.a.O();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!androidx.viewpager2.widget.a.c(this.a.N(i3))) continue;
            return true;
        }
        return false;
    }

    public boolean d() {
        return (!this.a() || this.a.O() <= 1) && this.b();
    }
}

