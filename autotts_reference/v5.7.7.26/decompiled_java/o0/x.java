/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.util.Log
 *  android.view.MenuItem
 */
package o0;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MenuItem;
import o0.b;

public abstract class x {
    public static MenuItem a(MenuItem menuItem, b b3) {
        if (menuItem instanceof i0.b) {
            return ((i0.b)menuItem).b(b3);
        }
        Log.w((String)"MenuItemCompat", (String)"setActionProvider: item does not implement SupportMenuItem; ignoring");
        return menuItem;
    }

    public static void b(MenuItem menuItem, char c3, int n3) {
        if (menuItem instanceof i0.b) {
            ((i0.b)menuItem).setAlphabeticShortcut(c3, n3);
            return;
        }
        a.g(menuItem, c3, n3);
    }

    public static void c(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof i0.b) {
            ((i0.b)menuItem).setContentDescription(charSequence);
            return;
        }
        a.h(menuItem, charSequence);
    }

    public static void d(MenuItem menuItem, ColorStateList colorStateList) {
        if (menuItem instanceof i0.b) {
            ((i0.b)menuItem).setIconTintList(colorStateList);
            return;
        }
        a.i(menuItem, colorStateList);
    }

    public static void e(MenuItem menuItem, PorterDuff.Mode mode) {
        if (menuItem instanceof i0.b) {
            ((i0.b)menuItem).setIconTintMode(mode);
            return;
        }
        a.j(menuItem, mode);
    }

    public static void f(MenuItem menuItem, char c3, int n3) {
        if (menuItem instanceof i0.b) {
            ((i0.b)menuItem).setNumericShortcut(c3, n3);
            return;
        }
        a.k(menuItem, c3, n3);
    }

    public static void g(MenuItem menuItem, CharSequence charSequence) {
        if (menuItem instanceof i0.b) {
            ((i0.b)menuItem).setTooltipText(charSequence);
            return;
        }
        a.m(menuItem, charSequence);
    }

    public static abstract class a {
        public static int a(MenuItem menuItem) {
            return menuItem.getAlphabeticModifiers();
        }

        public static CharSequence b(MenuItem menuItem) {
            return menuItem.getContentDescription();
        }

        public static ColorStateList c(MenuItem menuItem) {
            return menuItem.getIconTintList();
        }

        public static PorterDuff.Mode d(MenuItem menuItem) {
            return menuItem.getIconTintMode();
        }

        public static int e(MenuItem menuItem) {
            return menuItem.getNumericModifiers();
        }

        public static CharSequence f(MenuItem menuItem) {
            return menuItem.getTooltipText();
        }

        public static MenuItem g(MenuItem menuItem, char c3, int n3) {
            return menuItem.setAlphabeticShortcut(c3, n3);
        }

        public static MenuItem h(MenuItem menuItem, CharSequence charSequence) {
            return menuItem.setContentDescription(charSequence);
        }

        public static MenuItem i(MenuItem menuItem, ColorStateList colorStateList) {
            return menuItem.setIconTintList(colorStateList);
        }

        public static MenuItem j(MenuItem menuItem, PorterDuff.Mode mode) {
            return menuItem.setIconTintMode(mode);
        }

        public static MenuItem k(MenuItem menuItem, char c3, int n3) {
            return menuItem.setNumericShortcut(c3, n3);
        }

        public static MenuItem l(MenuItem menuItem, char c3, char c4, int n3, int n4) {
            return menuItem.setShortcut(c3, c4, n3, n4);
        }

        public static MenuItem m(MenuItem menuItem, CharSequence charSequence) {
            return menuItem.setTooltipText(charSequence);
        }
    }
}

