/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.LinearGradient
 *  android.graphics.RadialGradient
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.SweepGradient
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package f0;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import b0.c;
import f0.k;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class f {
    public static a a(a a4, int n3, int n4, boolean bl, int n5) {
        if (a4 != null) {
            return a4;
        }
        if (bl) {
            return new a(n3, n5, n4);
        }
        return new a(n3, n4);
    }

    public static Shader b(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        String string = xmlPullParser.getName();
        if (string.equals("gradient")) {
            string = k.k((Resources)object, theme, attributeSet, c.GradientColor);
            float f3 = k.f((TypedArray)string, xmlPullParser, "startX", c.GradientColor_android_startX, 0.0f);
            float f4 = k.f((TypedArray)string, xmlPullParser, "startY", c.GradientColor_android_startY, 0.0f);
            float f5 = k.f((TypedArray)string, xmlPullParser, "endX", c.GradientColor_android_endX, 0.0f);
            float f6 = k.f((TypedArray)string, xmlPullParser, "endY", c.GradientColor_android_endY, 0.0f);
            float f7 = k.f((TypedArray)string, xmlPullParser, "centerX", c.GradientColor_android_centerX, 0.0f);
            float f8 = k.f((TypedArray)string, xmlPullParser, "centerY", c.GradientColor_android_centerY, 0.0f);
            int n3 = k.g((TypedArray)string, xmlPullParser, "type", c.GradientColor_android_type, 0);
            int n4 = k.b((TypedArray)string, xmlPullParser, "startColor", c.GradientColor_android_startColor, 0);
            boolean bl = k.j(xmlPullParser, "centerColor");
            int n5 = k.b((TypedArray)string, xmlPullParser, "centerColor", c.GradientColor_android_centerColor, 0);
            int n6 = k.b((TypedArray)string, xmlPullParser, "endColor", c.GradientColor_android_endColor, 0);
            int n7 = k.g((TypedArray)string, xmlPullParser, "tileMode", c.GradientColor_android_tileMode, 0);
            float f9 = k.f((TypedArray)string, xmlPullParser, "gradientRadius", c.GradientColor_android_gradientRadius, 0.0f);
            string.recycle();
            object = f.a(f.c((Resources)object, xmlPullParser, attributeSet, theme), n4, n6, bl, n5);
            if (n3 != 1) {
                if (n3 != 2) {
                    return new LinearGradient(f3, f4, f5, f6, ((a)object).a, ((a)object).b, f.d(n7));
                }
                return new SweepGradient(f7, f8, ((a)object).a, ((a)object).b);
            }
            if (!(f9 <= 0.0f)) {
                return new RadialGradient(f7, f8, f9, ((a)object).a, ((a)object).b, f.d(n7));
            }
            throw new XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(": invalid gradient color tag ");
        ((StringBuilder)object).append(string);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    public static a c(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int n3;
        int n4;
        int n5 = xmlPullParser.getDepth() + 1;
        ArrayList<Float> arrayList = new ArrayList<Float>(20);
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>(20);
        while ((n4 = xmlPullParser.next()) != 1 && ((n3 = xmlPullParser.getDepth()) >= n5 || n4 != 3)) {
            if (n4 != 2 || n3 > n5 || !xmlPullParser.getName().equals("item")) continue;
            TypedArray typedArray = k.k((Resources)object, theme, attributeSet, c.GradientColorItem);
            n3 = c.GradientColorItem_android_color;
            boolean bl = typedArray.hasValue(n3);
            n4 = c.GradientColorItem_android_offset;
            boolean bl2 = typedArray.hasValue(n4);
            if (bl && bl2) {
                n3 = typedArray.getColor(n3, 0);
                float f3 = typedArray.getFloat(n4, 0.0f);
                typedArray.recycle();
                arrayList2.add(n3);
                arrayList.add(Float.valueOf(f3));
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            ((StringBuilder)object).append(": <item> tag requires a 'color' attribute and a 'offset' attribute!");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        if (arrayList2.size() > 0) {
            return new a(arrayList2, arrayList);
        }
        return null;
    }

    public static Shader.TileMode d(int n3) {
        if (n3 != 1) {
            if (n3 != 2) {
                return Shader.TileMode.CLAMP;
            }
            return Shader.TileMode.MIRROR;
        }
        return Shader.TileMode.REPEAT;
    }

    public static final class a {
        public final int[] a;
        public final float[] b;

        public a(int n3, int n4) {
            this.a = new int[]{n3, n4};
            this.b = new float[]{0.0f, 1.0f};
        }

        public a(int n3, int n4, int n5) {
            this.a = new int[]{n3, n4, n5};
            this.b = new float[]{0.0f, 0.5f, 1.0f};
        }

        public a(List list, List list2) {
            int n3 = list.size();
            this.a = new int[n3];
            this.b = new float[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                this.a[i3] = (Integer)list.get(i3);
                this.b[i3] = ((Float)list2.get(i3)).floatValue();
            }
        }
    }
}

