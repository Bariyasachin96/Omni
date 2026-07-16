/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.StateSet
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package v2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import v2.a;
import v2.d;
import v2.o;
import z1.c;
import z1.m;

public class v {
    public int a;
    public d b;
    public int[][] c = new int[10][];
    public d[] d = new d[10];

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static v b(Context object, TypedArray typedArray, int n3, d d3) {
        Throwable throwable2;
        block12: {
            v v3;
            block13: {
                int n4 = typedArray.getResourceId(n3, 0);
                if (n4 == 0) {
                    return v.c(o.m(typedArray, n3, d3));
                }
                if (!object.getResources().getResourceTypeName(n4).equals("xml")) {
                    return v.c(o.m(typedArray, n3, d3));
                }
                typedArray = object.getResources().getXml(n4);
                try {
                    v3 = new v();
                    AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)typedArray);
                    while ((n3 = typedArray.next()) != 2 && n3 != 1) {
                    }
                    if (n3 != 2) break block12;
                    if (!typedArray.getName().equals("selector")) break block13;
                    v3.i((Context)object, (XmlPullParser)typedArray, attributeSet, object.getTheme());
                }
                catch (Throwable throwable2) {}
            }
            typedArray.close();
            return v3;
        }
        object = new XmlPullParserException("No start tag found");
        throw object;
        if (typedArray == null) throw throwable2;
        try {
            typedArray.close();
            throw throwable2;
        }
        catch (Throwable throwable3) {
            try {
                throwable2.addSuppressed(throwable3);
                throw throwable2;
            }
            catch (Resources.NotFoundException | IOException | XmlPullParserException throwable4) {
                return v.c(d3);
            }
        }
    }

    public static v c(d d3) {
        v v3 = new v();
        v3.a(StateSet.WILD_CARD, d3);
        return v3;
    }

    public final void a(int[] nArray, d d3) {
        int n3 = this.a;
        if (n3 == 0 || nArray.length == 0) {
            this.b = d3;
        }
        if (n3 >= this.c.length) {
            this.f(n3, n3 + 10);
        }
        int[][] nArray2 = this.c;
        n3 = this.a;
        nArray2[n3] = nArray;
        this.d[n3] = d3;
        this.a = n3 + 1;
    }

    public d d(int[] nArray) {
        int n3;
        int n4 = n3 = this.g(nArray);
        if (n3 < 0) {
            n4 = this.g(StateSet.WILD_CARD);
        }
        if (n4 < 0) {
            return this.b;
        }
        return this.d[n4];
    }

    public d e() {
        return this.b;
    }

    public final void f(int n3, int n4) {
        Object object = new int[n4][];
        System.arraycopy(this.c, 0, object, 0, n3);
        this.c = object;
        object = new d[n4];
        System.arraycopy(this.d, 0, object, 0, n3);
        this.d = (d[])object;
    }

    public final int g(int[] nArray) {
        int[][] nArray2 = this.c;
        for (int i3 = 0; i3 < this.a; ++i3) {
            if (!StateSet.stateSetMatches((int[])nArray2[i3], (int[])nArray)) continue;
            return i3;
        }
        return -1;
    }

    public boolean h() {
        return this.a > 1;
    }

    public final void i(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int n3;
        int n4;
        int n5 = xmlPullParser.getDepth() + 1;
        while ((n4 = xmlPullParser.next()) != 1 && ((n3 = xmlPullParser.getDepth()) >= n5 || n4 != 3)) {
            if (n4 != 2 || n3 > n5 || !xmlPullParser.getName().equals("item")) continue;
            Object object = context.getResources();
            object = theme == null ? object.obtainAttributes(attributeSet, m.ShapeAppearance) : theme.obtainStyledAttributes(attributeSet, m.ShapeAppearance, 0, 0);
            d d3 = o.m((TypedArray)object, m.ShapeAppearance_cornerSize, new a(0.0f));
            object.recycle();
            int n6 = attributeSet.getAttributeCount();
            object = new int[n6];
            n4 = 0;
            for (n3 = 0; n3 < n6; ++n3) {
                int n7 = attributeSet.getAttributeNameResource(n3);
                int n8 = n4;
                if (n7 != z1.c.cornerSize) {
                    n8 = attributeSet.getAttributeBooleanValue(n3, false) ? n7 : -n7;
                    object[n4] = (Resources)n8;
                    n8 = n4 + 1;
                }
                n4 = n8;
            }
            this.a(StateSet.trimStateSet((int[])object, (int)n4), d3);
        }
    }
}

