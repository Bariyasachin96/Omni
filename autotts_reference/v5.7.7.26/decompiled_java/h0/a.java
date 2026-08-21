/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package h0;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

public abstract class a {
    public static void a(Drawable drawable, Resources.Theme theme) {
        a.a(drawable, theme);
    }

    public static boolean b(Drawable drawable) {
        return a.b(drawable);
    }

    public static void c(Drawable drawable) {
        drawable.clearColorFilter();
    }

    public static int d(Drawable drawable) {
        return drawable.getAlpha();
    }

    public static ColorFilter e(Drawable drawable) {
        return a.c(drawable);
    }

    public static int f(Drawable drawable) {
        return b.a(drawable);
    }

    public static void g(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        a.d(drawable, resources, xmlPullParser, attributeSet, theme);
    }

    public static boolean h(Drawable drawable) {
        return drawable.isAutoMirrored();
    }

    public static void i(Drawable drawable) {
        drawable.jumpToCurrentState();
    }

    public static void j(Drawable drawable, boolean bl) {
        drawable.setAutoMirrored(bl);
    }

    public static void k(Drawable drawable, float f3, float f4) {
        a.e(drawable, f3, f4);
    }

    public static void l(Drawable drawable, int n3, int n4, int n5, int n6) {
        a.f(drawable, n3, n4, n5, n6);
    }

    public static boolean m(Drawable drawable, int n3) {
        return b.b(drawable, n3);
    }

    public static void n(Drawable drawable, int n3) {
        a.g(drawable, n3);
    }

    public static void o(Drawable drawable, ColorStateList colorStateList) {
        a.h(drawable, colorStateList);
    }

    public static void p(Drawable drawable, PorterDuff.Mode mode) {
        a.i(drawable, mode);
    }

    public static Drawable q(Drawable drawable) {
        Drawable drawable2 = drawable;
        if (drawable instanceof h0.b) {
            drawable2 = ((h0.b)drawable).b();
        }
        return drawable2;
    }

    public static Drawable r(Drawable drawable) {
        return drawable;
    }

    public static abstract class a {
        public static void a(Drawable drawable, Resources.Theme theme) {
            drawable.applyTheme(theme);
        }

        public static boolean b(Drawable drawable) {
            return drawable.canApplyTheme();
        }

        public static ColorFilter c(Drawable drawable) {
            return drawable.getColorFilter();
        }

        public static void d(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            drawable.inflate(resources, xmlPullParser, attributeSet, theme);
        }

        public static void e(Drawable drawable, float f3, float f4) {
            drawable.setHotspot(f3, f4);
        }

        public static void f(Drawable drawable, int n3, int n4, int n5, int n6) {
            drawable.setHotspotBounds(n3, n4, n5, n6);
        }

        public static void g(Drawable drawable, int n3) {
            drawable.setTint(n3);
        }

        public static void h(Drawable drawable, ColorStateList colorStateList) {
            drawable.setTintList(colorStateList);
        }

        public static void i(Drawable drawable, PorterDuff.Mode mode) {
            drawable.setTintMode(mode);
        }
    }

    public static abstract class b {
        public static int a(Drawable drawable) {
            return drawable.getLayoutDirection();
        }

        public static boolean b(Drawable drawable, int n3) {
            return drawable.setLayoutDirection(n3);
        }
    }
}

