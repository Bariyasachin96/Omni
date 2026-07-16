/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  org.xmlpull.v1.XmlPullParser
 */
package f0;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import f0.c;
import f0.d;
import org.xmlpull.v1.XmlPullParser;

public abstract class k {
    public static boolean a(TypedArray typedArray, XmlPullParser xmlPullParser, String string, int n3, boolean bl) {
        if (!k.j(xmlPullParser, string)) {
            return bl;
        }
        return typedArray.getBoolean(n3, bl);
    }

    public static int b(TypedArray typedArray, XmlPullParser xmlPullParser, String string, int n3, int n4) {
        if (!k.j(xmlPullParser, string)) {
            return n4;
        }
        return typedArray.getColor(n3, n4);
    }

    public static ColorStateList c(TypedArray object, XmlPullParser xmlPullParser, Resources.Theme theme, String string, int n3) {
        if (k.j(xmlPullParser, string)) {
            xmlPullParser = new TypedValue();
            object.getValue(n3, (TypedValue)xmlPullParser);
            int n4 = xmlPullParser.type;
            if (n4 != 2) {
                if (n4 >= 28 && n4 <= 31) {
                    return k.d((TypedValue)xmlPullParser);
                }
                return c.d(object.getResources(), object.getResourceId(n3, 0), theme);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to resolve attribute at index ");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(xmlPullParser);
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        return null;
    }

    public static ColorStateList d(TypedValue typedValue) {
        return ColorStateList.valueOf((int)typedValue.data);
    }

    public static d e(TypedArray object, XmlPullParser xmlPullParser, Resources.Theme theme, String string, int n3, int n4) {
        if (k.j(xmlPullParser, string)) {
            xmlPullParser = new TypedValue();
            object.getValue(n3, (TypedValue)xmlPullParser);
            int n5 = xmlPullParser.type;
            if (n5 >= 28 && n5 <= 31) {
                return d.b(xmlPullParser.data);
            }
            if ((object = d.g(object.getResources(), object.getResourceId(n3, 0), theme)) != null) {
                return object;
            }
        }
        return d.b(n4);
    }

    public static float f(TypedArray typedArray, XmlPullParser xmlPullParser, String string, int n3, float f3) {
        if (!k.j(xmlPullParser, string)) {
            return f3;
        }
        return typedArray.getFloat(n3, f3);
    }

    public static int g(TypedArray typedArray, XmlPullParser xmlPullParser, String string, int n3, int n4) {
        if (!k.j(xmlPullParser, string)) {
            return n4;
        }
        return typedArray.getInt(n3, n4);
    }

    public static int h(TypedArray typedArray, XmlPullParser xmlPullParser, String string, int n3, int n4) {
        if (!k.j(xmlPullParser, string)) {
            return n4;
        }
        return typedArray.getResourceId(n3, n4);
    }

    public static String i(TypedArray typedArray, XmlPullParser xmlPullParser, String string, int n3) {
        if (!k.j(xmlPullParser, string)) {
            return null;
        }
        return typedArray.getString(n3);
    }

    public static boolean j(XmlPullParser xmlPullParser, String string) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", string) != null;
    }

    public static TypedArray k(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] nArray) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, nArray);
        }
        return theme.obtainStyledAttributes(attributeSet, nArray, 0, 0);
    }
}

