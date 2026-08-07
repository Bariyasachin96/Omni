/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.StateSet
 *  android.util.TypedValue
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package f0;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import f0.a;
import f0.g;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class c {
    public static final ThreadLocal a = new ThreadLocal();

    public static ColorStateList a(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) {
        int n3;
        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
        while ((n3 = xmlPullParser.next()) != 2 && n3 != 1) {
        }
        if (n3 == 2) {
            return c.b(resources, xmlPullParser, attributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static ColorStateList b(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        String string = xmlPullParser.getName();
        if (string.equals("selector")) {
            return c.e((Resources)object, xmlPullParser, attributeSet, theme);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(": invalid color state list tag ");
        ((StringBuilder)object).append(string);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    public static TypedValue c() {
        TypedValue typedValue;
        ThreadLocal threadLocal = a;
        TypedValue typedValue2 = typedValue = (TypedValue)threadLocal.get();
        if (typedValue == null) {
            typedValue2 = new TypedValue();
            threadLocal.set(typedValue2);
        }
        return typedValue2;
    }

    public static ColorStateList d(Resources resources, int n3, Resources.Theme theme) {
        try {
            resources = c.a(resources, (XmlPullParser)resources.getXml(n3), theme);
            return resources;
        }
        catch (Exception exception) {
            Log.e((String)"CSLCompat", (String)"Failed to inflate ColorStateList.", (Throwable)exception);
            return null;
        }
    }

    public static ColorStateList e(Resources object, XmlPullParser object2, AttributeSet attributeSet, Resources.Theme theme) {
        int n3;
        int n4;
        int n5 = object2.getDepth() + 1;
        int[][] nArrayArray = new int[20][];
        int[] nArray = new int[20];
        int n6 = 0;
        while ((n4 = object2.next()) != 1 && ((n3 = object2.getDepth()) >= n5 || n4 != 3)) {
            Object object3 = nArray;
            Object object4 = nArrayArray;
            int n7 = n6;
            if (n4 == 2) {
                object3 = nArray;
                object4 = nArrayArray;
                n7 = n6;
                if (n3 <= n5) {
                    if (!object2.getName().equals("item")) {
                        object3 = nArray;
                        object4 = nArrayArray;
                        n7 = n6;
                    } else {
                        object3 = c.h(object, theme, attributeSet, b0.c.ColorStateListItem);
                        n7 = object3.getResourceId(n3 = b0.c.ColorStateListItem_android_color, -1);
                        if (n7 != -1 && !c.f(object, n7)) {
                            try {
                                n7 = c.a(object, (XmlPullParser)object.getXml(n7), theme).getDefaultColor();
                            }
                            catch (Exception exception) {
                                n7 = object3.getColor(b0.c.ColorStateListItem_android_color, -65281);
                            }
                        } else {
                            n7 = object3.getColor(n3, -65281);
                        }
                        n3 = b0.c.ColorStateListItem_android_alpha;
                        boolean bl = object3.hasValue(n3);
                        float f3 = 1.0f;
                        if (bl) {
                            f3 = object3.getFloat(n3, 1.0f);
                        } else {
                            n3 = b0.c.ColorStateListItem_alpha;
                            if (object3.hasValue(n3)) {
                                f3 = object3.getFloat(n3, 1.0f);
                            }
                        }
                        float f4 = Build.VERSION.SDK_INT >= 31 && object3.hasValue(n3 = b0.c.ColorStateListItem_android_lStar) ? object3.getFloat(n3, -1.0f) : object3.getFloat(b0.c.ColorStateListItem_lStar, -1.0f);
                        object3.recycle();
                        int n8 = attributeSet.getAttributeCount();
                        object4 = new int[n8];
                        n4 = 0;
                        for (n3 = 0; n3 < n8; ++n3) {
                            int n9 = attributeSet.getAttributeNameResource(n3);
                            int n10 = n4;
                            if (n9 != 16843173) {
                                n10 = n4;
                                if (n9 != 16843551) {
                                    n10 = n4;
                                    if (n9 != b0.a.alpha) {
                                        n10 = n4;
                                        if (n9 != b0.a.lStar) {
                                            n10 = attributeSet.getAttributeBooleanValue(n3, false) ? n9 : -n9;
                                            object4[n4] = (int[])n10;
                                            n10 = n4 + 1;
                                        }
                                    }
                                }
                            }
                            n4 = n10;
                        }
                        object4 = StateSet.trimStateSet((int[])object4, (int)n4);
                        object3 = g.a(nArray, n6, c.g(n7, f3, f4));
                        object4 = (int[][])g.b((Object[])nArrayArray, n6, object4);
                        n7 = n6 + 1;
                    }
                }
            }
            nArray = object3;
            nArrayArray = object4;
            n6 = n7;
        }
        object = new int[n6];
        object2 = new int[n6][];
        System.arraycopy(nArray, 0, object, 0, n6);
        System.arraycopy(nArrayArray, 0, object2, 0, n6);
        return new ColorStateList((int[][])object2, (int[])object);
    }

    public static boolean f(Resources resources, int n3) {
        TypedValue typedValue = c.c();
        resources.getValue(n3, typedValue, true);
        n3 = typedValue.type;
        return n3 >= 28 && n3 <= 31;
    }

    public static int g(int n3, float f3, float f4) {
        boolean bl = f4 >= 0.0f && f4 <= 100.0f;
        if (f3 == 1.0f && !bl) {
            return n3;
        }
        int n4 = j0.a.b((int)((float)Color.alpha((int)n3) * f3 + 0.5f), 0, 255);
        int n5 = n3;
        if (bl) {
            a a4 = f0.a.c(n3);
            n5 = f0.a.m(a4.j(), a4.i(), f4);
        }
        return n5 & 0xFFFFFF | n4 << 24;
    }

    public static TypedArray h(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] nArray) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, nArray);
        }
        return theme.obtainStyledAttributes(attributeSet, nArray, 0, 0);
    }
}

