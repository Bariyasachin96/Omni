/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.Shader
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package f0;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import f0.c;
import f0.f;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class d {
    public final Shader a;
    public final ColorStateList b;
    public int c;

    public d(Shader shader, ColorStateList colorStateList, int n3) {
        this.a = shader;
        this.b = colorStateList;
        this.c = n3;
    }

    public static d a(Resources object, int n3, Resources.Theme theme) {
        XmlResourceParser xmlResourceParser = object.getXml(n3);
        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
        while ((n3 = xmlResourceParser.next()) != 2 && n3 != 1) {
        }
        if (n3 == 2) {
            String string = xmlResourceParser.getName();
            string.getClass();
            if (!string.equals("gradient")) {
                if (string.equals("selector")) {
                    return d.c(f0.c.b((Resources)object, (XmlPullParser)xmlResourceParser, attributeSet, theme));
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(xmlResourceParser.getPositionDescription());
                ((StringBuilder)object).append(": unsupported complex color tag ");
                ((StringBuilder)object).append(string);
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            return d.d(f.b((Resources)object, (XmlPullParser)xmlResourceParser, attributeSet, theme));
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static d b(int n3) {
        return new d(null, null, n3);
    }

    public static d c(ColorStateList colorStateList) {
        return new d(null, colorStateList, colorStateList.getDefaultColor());
    }

    public static d d(Shader shader) {
        return new d(shader, null, 0);
    }

    public static d g(Resources object, int n3, Resources.Theme theme) {
        try {
            object = d.a(object, n3, theme);
            return object;
        }
        catch (Exception exception) {
            Log.e((String)"ComplexColorCompat", (String)"Failed to inflate ComplexColor.", (Throwable)exception);
            return null;
        }
    }

    public int e() {
        return this.c;
    }

    public Shader f() {
        return this.a;
    }

    public boolean h() {
        return this.a != null;
    }

    public boolean i() {
        ColorStateList colorStateList;
        return this.a == null && (colorStateList = this.b) != null && colorStateList.isStateful();
    }

    public boolean j(int[] nArray) {
        ColorStateList colorStateList;
        int n3;
        if (this.i() && (n3 = (colorStateList = this.b).getColorForState(nArray, colorStateList.getDefaultColor())) != this.c) {
            this.c = n3;
            return true;
        }
        return false;
    }

    public void k(int n3) {
        this.c = n3;
    }

    public boolean l() {
        return this.h() || this.c != 0;
        {
        }
    }
}

