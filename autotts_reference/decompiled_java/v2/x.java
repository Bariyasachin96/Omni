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
 *  android.util.DisplayMetrics
 *  android.util.StateSet
 *  android.util.TypedValue
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package v2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import z1.m;

public class x {
    public int a;
    public a b;
    public int[][] c = new int[10][];
    public a[] d = new a[10];

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static x b(Context object, TypedArray typedArray, int n3) {
        Throwable throwable2;
        block12: {
            x x3;
            block13: {
                if ((n3 = typedArray.getResourceId(n3, 0)) == 0) {
                    return null;
                }
                if (!object.getResources().getResourceTypeName(n3).equals("xml")) {
                    return null;
                }
                typedArray = object.getResources().getXml(n3);
                try {
                    x3 = new x();
                    AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)typedArray);
                    while ((n3 = typedArray.next()) != 2 && n3 != 1) {
                    }
                    if (n3 != 2) break block12;
                    if (!typedArray.getName().equals("selector")) break block13;
                    x3.h((Context)object, (XmlPullParser)typedArray, attributeSet, object.getTheme());
                }
                catch (Throwable throwable2) {}
            }
            typedArray.close();
            return x3;
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
                return null;
            }
        }
    }

    public final void a(int[] nArray, a a4) {
        int n3 = this.a;
        if (n3 == 0 || nArray.length == 0) {
            this.b = a4;
        }
        if (n3 >= this.c.length) {
            this.f(n3, n3 + 10);
        }
        int[][] nArray2 = this.c;
        n3 = this.a;
        nArray2[n3] = nArray;
        this.d[n3] = a4;
        this.a = n3 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int c(int n3) {
        int n4 = -n3;
        int n5 = 0;
        while (true) {
            int n6;
            block7: {
                float f3;
                block6: {
                    c c3;
                    b b3;
                    block5: {
                        if (n5 >= this.a) {
                            return n4;
                        }
                        b3 = this.d[n5].a;
                        c3 = b3.a;
                        if (c3 != v2.x$c.d) break block5;
                        f3 = Math.max((float)n4, b3.b);
                        break block6;
                    }
                    n6 = n4;
                    if (c3 != v2.x$c.c) break block7;
                    f3 = Math.max((float)n4, (float)n3 * b3.b);
                }
                n6 = (int)f3;
            }
            ++n5;
            n4 = n6;
        }
    }

    public final b d(TypedArray typedArray, int n3, b b3) {
        TypedValue typedValue = typedArray.peekValue(n3);
        if (typedValue != null) {
            n3 = typedValue.type;
            if (n3 == 5) {
                return new b(v2.x$c.d, TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)typedArray.getResources().getDisplayMetrics()));
            }
            if (n3 == 6) {
                return new b(v2.x$c.c, typedValue.getFraction(1.0f, 1.0f));
            }
        }
        return b3;
    }

    public a e(int[] nArray) {
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

    public final void f(int n3, int n4) {
        Object object = new int[n4][];
        System.arraycopy(this.c, 0, object, 0, n3);
        this.c = object;
        object = new a[n4];
        System.arraycopy(this.d, 0, object, 0, n3);
        this.d = (a[])object;
    }

    public final int g(int[] nArray) {
        int[][] nArray2 = this.c;
        for (int i3 = 0; i3 < this.a; ++i3) {
            if (!StateSet.stateSetMatches((int[])nArray2[i3], (int[])nArray)) continue;
            return i3;
        }
        return -1;
    }

    public final void h(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int n3;
        int n4;
        int n5 = xmlPullParser.getDepth() + 1;
        while ((n4 = xmlPullParser.next()) != 1 && ((n3 = xmlPullParser.getDepth()) >= n5 || n4 != 3)) {
            if (n4 != 2 || n3 > n5 || !xmlPullParser.getName().equals("item")) continue;
            Object object = context.getResources();
            object = theme == null ? object.obtainAttributes(attributeSet, m.StateListSizeChange) : theme.obtainStyledAttributes(attributeSet, m.StateListSizeChange, 0, 0);
            b b3 = this.d((TypedArray)object, m.StateListSizeChange_widthChange, null);
            object.recycle();
            int n6 = attributeSet.getAttributeCount();
            object = new int[n6];
            n3 = 0;
            for (n4 = 0; n4 < n6; ++n4) {
                int n7 = attributeSet.getAttributeNameResource(n4);
                int n8 = n3;
                if (n7 != z1.c.widthChange) {
                    n8 = attributeSet.getAttributeBooleanValue(n4, false) ? n7 : -n7;
                    object[n3] = (Resources)n8;
                    n8 = n3 + 1;
                }
                n3 = n8;
            }
            this.a(StateSet.trimStateSet((int[])object, (int)n3), new a(b3));
        }
    }

    public static class a {
        public b a;

        public a(b b3) {
            this.a = b3;
        }
    }

    public static class b {
        public c a;
        public float b;

        public b(c c3, float f3) {
            this.a = c3;
            this.b = f3;
        }

        public int a(int n3) {
            c c3 = this.a;
            if (c3 == v2.x$c.c) {
                return (int)(this.b * (float)n3);
            }
            if (c3 == v2.x$c.d) {
                return (int)this.b;
            }
            return 0;
        }
    }

    public static final class c
    extends Enum {
        public static final /* enum */ c c = new c("PERCENT", 0);
        public static final /* enum */ c d = new c("PIXELS", 1);
        public static final c[] e = v2.x$c.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public c() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ c[] a() {
            return new c[]{c, d};
        }

        public static c valueOf(String string) {
            return Enum.valueOf(c.class, string);
        }

        public static c[] values() {
            return (c[])e.clone();
        }
    }
}

