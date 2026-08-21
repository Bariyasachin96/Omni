/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.Outline
 *  android.graphics.Path
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package j2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import j2.c;
import j2.e;
import java.io.IOException;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class d {
    public static Drawable a(Drawable drawable, Drawable drawable2) {
        return d.b(drawable, drawable2, -1, -1);
    }

    public static Drawable b(Drawable drawable, Drawable drawable2, int n3, int n4) {
        if (drawable == null) {
            return drawable2;
        }
        if (drawable2 == null) {
            return drawable;
        }
        int n5 = n3;
        if (n3 == -1) {
            n5 = d.i(drawable, drawable2);
        }
        n3 = n4;
        if (n4 == -1) {
            n3 = d.h(drawable, drawable2);
        }
        if (n5 > drawable.getIntrinsicWidth() || n3 > drawable.getIntrinsicHeight()) {
            float f3 = (float)n5 / (float)n3;
            if (f3 >= (float)drawable.getIntrinsicWidth() / (float)drawable.getIntrinsicHeight()) {
                n5 = drawable.getIntrinsicWidth();
                n3 = (int)((float)n5 / f3);
            } else {
                n3 = drawable.getIntrinsicHeight();
                n5 = (int)(f3 * (float)n3);
            }
        }
        drawable = new LayerDrawable(new Drawable[]{drawable, drawable2});
        drawable.setLayerSize(1, n5, n3);
        drawable.setLayerGravity(1, 17);
        return drawable;
    }

    public static Drawable c(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        return d.e(drawable, colorStateList, mode, false);
    }

    public static Drawable d(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        return d.e(drawable, colorStateList, mode, false);
    }

    public static Drawable e(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode, boolean bl) {
        if (drawable == null) {
            return null;
        }
        if (colorStateList != null) {
            drawable = h0.a.r(drawable).mutate();
            if (mode != null) {
                drawable.setTintMode(mode);
            }
            return drawable;
        }
        if (bl) {
            drawable.mutate();
        }
        return drawable;
    }

    public static int[] f(int[] nArray) {
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            int n3 = nArray[i3];
            if (n3 == 0x10100A0) {
                return nArray;
            }
            if (n3 != 0) continue;
            nArray = (int[])nArray.clone();
            nArray[i3] = 0x10100A0;
            return nArray;
        }
        int[] nArray2 = Arrays.copyOf(nArray, nArray.length + 1);
        nArray2[nArray.length] = 0x10100A0;
        return nArray2;
    }

    public static ColorStateList g(Drawable drawable) {
        if (drawable instanceof ColorDrawable) {
            return ColorStateList.valueOf((int)((ColorDrawable)drawable).getColor());
        }
        if (Build.VERSION.SDK_INT >= 29 && j2.a.a(drawable)) {
            return c.a(j2.b.a(drawable));
        }
        return null;
    }

    public static int h(Drawable drawable, Drawable drawable2) {
        int n3 = drawable2.getIntrinsicHeight();
        if (n3 != -1) {
            return n3;
        }
        return drawable.getIntrinsicHeight();
    }

    public static int i(Drawable drawable, Drawable drawable2) {
        int n3 = drawable2.getIntrinsicWidth();
        if (n3 != -1) {
            return n3;
        }
        return drawable.getIntrinsicWidth();
    }

    public static int[] j(int[] nArray) {
        int[] nArray2 = new int[nArray.length];
        int n3 = nArray.length;
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            int n5 = nArray[i3];
            int n6 = n4;
            if (n5 != 0x10100A0) {
                nArray2[n4] = n5;
                n6 = n4 + 1;
            }
            n4 = n6;
        }
        return nArray2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AttributeSet k(Context object, int n3, CharSequence object2) {
        void var0_3;
        block6: {
            try {
                int n4;
                object = object.getResources().getXml(n3);
                while ((n4 = object.next()) != 2 && n4 != 1) {
                }
                if (n4 != 2) {
                    object = new XmlPullParserException("No start tag found");
                    throw object;
                }
                if (TextUtils.equals((CharSequence)object.getName(), (CharSequence)object2)) {
                    return Xml.asAttributeSet((XmlPullParser)object);
                }
            }
            catch (IOException iOException) {
                break block6;
            }
            catch (XmlPullParserException xmlPullParserException) {
                break block6;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Must have a <");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append("> start tag");
            XmlPullParserException xmlPullParserException = new XmlPullParserException(((StringBuilder)object).toString());
            throw xmlPullParserException;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Can't load badge resource ID #0x");
        ((StringBuilder)object2).append(Integer.toHexString(n3));
        object2 = new Resources.NotFoundException(((StringBuilder)object2).toString());
        ((Throwable)object2).initCause((Throwable)var0_3);
        throw object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void l(Outline outline, Path path) {
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 30) {
            b.a(outline, path);
            return;
        }
        if (n3 < 29) {
            if (!path.isConvex()) return;
            a.a(outline, path);
            return;
        }
        try {
            a.a(outline, path);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public static void m(RippleDrawable rippleDrawable, int n3) {
        rippleDrawable.setRadius(n3);
    }

    public static void n(Drawable drawable, int n3) {
        boolean bl = n3 != 0;
        if (bl) {
            drawable.setTint(n3);
            return;
        }
        drawable.setTintList(null);
    }

    public static PorterDuffColorFilter o(Drawable drawable, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList != null && mode != null) {
            return new PorterDuffColorFilter(colorStateList.getColorForState(drawable.getState(), 0), mode);
        }
        return null;
    }

    public static abstract class a {
        public static void a(Outline outline, Path path) {
            outline.setConvexPath(path);
        }
    }

    public static abstract class b {
        public static void a(Outline outline, Path path) {
            e.a(outline, path);
        }
    }
}

