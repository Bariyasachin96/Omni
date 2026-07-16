/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.PopupWindow
 */
package androidx.core.widget;

import android.view.View;
import android.widget.PopupWindow;

public abstract class i {
    public static void a(PopupWindow popupWindow, boolean bl) {
        a.c(popupWindow, bl);
    }

    public static void b(PopupWindow popupWindow, int n3) {
        a.d(popupWindow, n3);
    }

    public static void c(PopupWindow popupWindow, View view, int n3, int n4, int n5) {
        popupWindow.showAsDropDown(view, n3, n4, n5);
    }

    public static abstract class a {
        public static boolean a(PopupWindow popupWindow) {
            return popupWindow.getOverlapAnchor();
        }

        public static int b(PopupWindow popupWindow) {
            return popupWindow.getWindowLayoutType();
        }

        public static void c(PopupWindow popupWindow, boolean bl) {
            popupWindow.setOverlapAnchor(bl);
        }

        public static void d(PopupWindow popupWindow, int n3) {
            popupWindow.setWindowLayoutType(n3);
        }
    }
}

