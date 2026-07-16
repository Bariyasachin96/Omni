/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.content.res.XmlResourceParser
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
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import v2.o;
import v2.v;
import z1.c;
import z1.m;

public class w {
    public final int a;
    public final o b;
    public final int[][] c;
    public final o[] d;
    public final v e;
    public final v f;
    public final v g;
    public final v h;

    public w(b b3) {
        this.a = b3.a;
        this.b = b3.b;
        this.c = b3.c;
        this.d = b3.d;
        this.e = b3.e;
        this.f = b3.f;
        this.g = b3.g;
        this.h = b3.h;
    }

    public /* synthetic */ w(b b3, a a4) {
        this(b3);
    }

    public static w b(Context context, TypedArray typedArray, int n3) {
        if ((n3 = typedArray.getResourceId(n3, 0)) == 0) {
            return null;
        }
        if (!Objects.equals(context.getResources().getResourceTypeName(n3), "xml")) {
            return null;
        }
        return new b(context, n3, null).j();
    }

    public static void g(b b3, Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int n3;
        int n4;
        int n5 = xmlPullParser.getDepth() + 1;
        while ((n4 = xmlPullParser.next()) != 1 && ((n3 = xmlPullParser.getDepth()) >= n5 || n4 != 3)) {
            if (n4 != 2 || n3 > n5 || !xmlPullParser.getName().equals("item")) continue;
            Object object = context.getResources();
            object = theme == null ? object.obtainAttributes(attributeSet, m.MaterialShape) : theme.obtainStyledAttributes(attributeSet, m.MaterialShape, 0, 0);
            o o3 = o.b(context, object.getResourceId(m.MaterialShape_shapeAppearance, 0), object.getResourceId(m.MaterialShape_shapeAppearanceOverlay, 0)).m();
            object.recycle();
            int n6 = attributeSet.getAttributeCount();
            object = new int[n6];
            n4 = 0;
            for (n3 = 0; n3 < n6; ++n3) {
                int n7 = attributeSet.getAttributeNameResource(n3);
                int n8 = n4;
                if (n7 != z1.c.shapeAppearance) {
                    n8 = n4;
                    if (n7 != z1.c.shapeAppearanceOverlay) {
                        n8 = attributeSet.getAttributeBooleanValue(n3, false) ? n7 : -n7;
                        object[n4] = (Resources)n8;
                        n8 = n4 + 1;
                    }
                }
                n4 = n8;
            }
            b3.i(StateSet.trimStateSet((int[])object, (int)n4), o3);
        }
    }

    public static int h(int n3) {
        return (n3 & 0xA) >> 1 | (n3 & 5) << 1;
    }

    public o c(boolean bl) {
        if (bl && (this.e != null || this.f != null || this.g != null || this.h != null)) {
            o.b b3 = this.b.w();
            v v3 = this.e;
            if (v3 != null) {
                b3.F(v3.e());
            }
            if ((v3 = this.f) != null) {
                b3.J(v3.e());
            }
            if ((v3 = this.g) != null) {
                b3.w(v3.e());
            }
            if ((v3 = this.h) != null) {
                b3.A(v3.e());
            }
            return b3.m();
        }
        return this.b;
    }

    public o d(int[] nArray) {
        int n3;
        int n4 = n3 = this.e(nArray);
        if (n3 < 0) {
            n4 = this.e(StateSet.WILD_CARD);
        }
        if (this.e == null && this.f == null && this.g == null && this.h == null) {
            return this.d[n4];
        }
        o.b b3 = this.d[n4].w();
        v v3 = this.e;
        if (v3 != null) {
            b3.F(v3.d(nArray));
        }
        if ((v3 = this.f) != null) {
            b3.J(v3.d(nArray));
        }
        if ((v3 = this.g) != null) {
            b3.w(v3.d(nArray));
        }
        if ((v3 = this.h) != null) {
            b3.A(v3.d(nArray));
        }
        return b3.m();
    }

    public final int e(int[] nArray) {
        int[][] nArray2 = this.c;
        for (int i3 = 0; i3 < this.a; ++i3) {
            if (!StateSet.stateSetMatches((int[])nArray2[i3], (int[])nArray)) continue;
            return i3;
        }
        return -1;
    }

    public boolean f() {
        v v3;
        return this.a > 1 || (v3 = this.e) != null && v3.h() || (v3 = this.f) != null && v3.h() || (v3 = this.g) != null && v3.h() || (v3 = this.h) != null && v3.h();
        {
        }
    }

    public b i() {
        return new b(this);
    }

    public static final class b {
        public int a;
        public o b;
        public int[][] c;
        public o[] d;
        public v e;
        public v f;
        public v g;
        public v h;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public b(Context object, int n3) {
            Throwable throwable2;
            XmlResourceParser xmlResourceParser;
            block10: {
                block11: {
                    this.m();
                    xmlResourceParser = object.getResources().getXml(n3);
                    try {
                        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
                        while ((n3 = xmlResourceParser.next()) != 2 && n3 != 1) {
                        }
                        if (n3 != 2) break block10;
                        if (!xmlResourceParser.getName().equals("selector")) break block11;
                        w.g(this, object, (XmlPullParser)xmlResourceParser, attributeSet, object.getTheme());
                    }
                    catch (Throwable throwable2) {}
                }
                xmlResourceParser.close();
                return;
            }
            super("No start tag found");
            throw object;
            if (xmlResourceParser == null) throw throwable2;
            try {
                xmlResourceParser.close();
                throw throwable2;
            }
            catch (Throwable throwable3) {
                try {
                    throwable2.addSuppressed(throwable3);
                    throw throwable2;
                }
                catch (Resources.NotFoundException | IOException | XmlPullParserException throwable4) {
                    this.m();
                    return;
                }
            }
        }

        public /* synthetic */ b(Context context, int n3, a a4) {
            this(context, n3);
        }

        public b(o o3) {
            this.m();
            this.i(StateSet.WILD_CARD, o3);
        }

        public b(w w3) {
            int n3;
            this.a = n3 = w3.a;
            this.b = w3.b;
            int[][] nArray = w3.c;
            int[][] nArrayArray = new int[nArray.length][];
            this.c = nArrayArray;
            this.d = new o[w3.d.length];
            System.arraycopy(nArray, 0, nArrayArray, 0, n3);
            System.arraycopy(w3.d, 0, this.d, 0, this.a);
            this.e = w3.e;
            this.f = w3.f;
            this.g = w3.g;
            this.h = w3.h;
        }

        public b i(int[] nArray, o o3) {
            int n3 = this.a;
            if (n3 == 0 || nArray.length == 0) {
                this.b = o3;
            }
            if (n3 >= this.c.length) {
                this.l(n3, n3 + 10);
            }
            int[][] nArray2 = this.c;
            n3 = this.a;
            nArray2[n3] = nArray;
            this.d[n3] = o3;
            this.a = n3 + 1;
            return this;
        }

        public w j() {
            if (this.a == 0) {
                return null;
            }
            return new w(this, null);
        }

        public final boolean k(int n3, int n4) {
            return (n4 | n3) == n3;
        }

        public final void l(int n3, int n4) {
            Object object = new int[n4][];
            System.arraycopy(this.c, 0, object, 0, n3);
            this.c = object;
            object = new o[n4];
            System.arraycopy(this.d, 0, object, 0, n3);
            this.d = (o[])object;
        }

        public final void m() {
            this.b = new o();
            this.c = new int[10][];
            this.d = new o[10];
        }

        public b n(v v3, int n3) {
            if (this.k(n3, 1)) {
                this.e = v3;
            }
            if (this.k(n3, 2)) {
                this.f = v3;
            }
            if (this.k(n3, 4)) {
                this.g = v3;
            }
            if (this.k(n3, 8)) {
                this.h = v3;
            }
            return this;
        }
    }
}

