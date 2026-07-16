/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.Base64
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package f0;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Base64;
import android.util.Xml;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class e {
    public static int a(TypedArray typedArray, int n3) {
        return a.a(typedArray, n3);
    }

    public static b b(XmlPullParser xmlPullParser, Resources resources) {
        int n3;
        while ((n3 = xmlPullParser.next()) != 2 && n3 != 1) {
        }
        if (n3 == 2) {
            return e.d(xmlPullParser, resources);
        }
        throw new XmlPullParserException("No start tag found");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List c(Resources object, int n3) {
        Throwable throwable2;
        TypedArray typedArray;
        block9: {
            ArrayList<List> arrayList;
            block8: {
                block7: {
                    block6: {
                        if (n3 == 0) {
                            return Collections.EMPTY_LIST;
                        }
                        typedArray = object.obtainTypedArray(n3);
                        if (typedArray.length() != 0) break block6;
                        object = Collections.EMPTY_LIST;
                        typedArray.recycle();
                        return object;
                    }
                    try {
                        arrayList = new ArrayList<List>();
                        if (e.a(typedArray, 0) != 1) break block7;
                        for (n3 = 0; n3 < typedArray.length(); ++n3) {
                            int n4 = typedArray.getResourceId(n3, 0);
                            if (n4 == 0) continue;
                            arrayList.add(e.h(object.getStringArray(n4)));
                        }
                        break block8;
                    }
                    catch (Throwable throwable2) {}
                }
                arrayList.add(e.h(object.getStringArray(n3)));
                break block9;
            }
            typedArray.recycle();
            return arrayList;
        }
        typedArray.recycle();
        throw throwable2;
    }

    public static b d(XmlPullParser xmlPullParser, Resources resources) {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return e.e(xmlPullParser, resources);
        }
        e.g(xmlPullParser);
        return null;
    }

    public static b e(XmlPullParser xmlPullParser, Resources resources) {
        TypedArray typedArray = resources.obtainAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), b0.c.FontFamily);
        Object object = typedArray.getString(b0.c.FontFamily_fontProviderAuthority);
        String string = typedArray.getString(b0.c.FontFamily_fontProviderPackage);
        String string2 = typedArray.getString(b0.c.FontFamily_fontProviderQuery);
        int n3 = typedArray.getResourceId(b0.c.FontFamily_fontProviderCerts, 0);
        int n4 = typedArray.getInteger(b0.c.FontFamily_fontProviderFetchStrategy, 1);
        int n5 = typedArray.getInteger(b0.c.FontFamily_fontProviderFetchTimeout, 500);
        String string3 = typedArray.getString(b0.c.FontFamily_fontProviderSystemFontFamily);
        typedArray.recycle();
        if (object != null && string != null && string2 != null) {
            while (xmlPullParser.next() != 3) {
                e.g(xmlPullParser);
            }
            return new e(new l0.e((String)object, string, string2, e.c(resources, n3)), n4, n5, string3);
        }
        object = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() != 2) continue;
            if (xmlPullParser.getName().equals("font")) {
                object.add(e.f(xmlPullParser, resources));
                continue;
            }
            e.g(xmlPullParser);
        }
        if (object.isEmpty()) {
            return null;
        }
        return new c(object.toArray(new d[0]));
    }

    public static d f(XmlPullParser xmlPullParser, Resources resources) {
        int n3;
        if (!(resources = resources.obtainAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), b0.c.FontFamilyFont)).hasValue(n3 = b0.c.FontFamilyFont_fontWeight)) {
            n3 = b0.c.FontFamilyFont_android_fontWeight;
        }
        int n4 = resources.getInt(n3, 400);
        n3 = b0.c.FontFamilyFont_fontStyle;
        if (!resources.hasValue(n3)) {
            n3 = b0.c.FontFamilyFont_android_fontStyle;
        }
        boolean bl = 1 == resources.getInt(n3, 0);
        n3 = b0.c.FontFamilyFont_ttcIndex;
        if (!resources.hasValue(n3)) {
            n3 = b0.c.FontFamilyFont_android_ttcIndex;
        }
        int n5 = b0.c.FontFamilyFont_fontVariationSettings;
        if (!resources.hasValue(n5)) {
            n5 = b0.c.FontFamilyFont_android_fontVariationSettings;
        }
        String string = resources.getString(n5);
        n5 = resources.getInt(n3, 0);
        n3 = b0.c.FontFamilyFont_font;
        if (!resources.hasValue(n3)) {
            n3 = b0.c.FontFamilyFont_android_font;
        }
        int n6 = resources.getResourceId(n3, 0);
        String string2 = resources.getString(n3);
        resources.recycle();
        while (xmlPullParser.next() != 3) {
            e.g(xmlPullParser);
        }
        return new d(string2, n4, bl, string, n5, n6);
    }

    public static void g(XmlPullParser xmlPullParser) {
        int n3 = 1;
        while (n3 > 0) {
            int n4 = xmlPullParser.next();
            if (n4 != 2) {
                if (n4 != 3) continue;
                --n3;
                continue;
            }
            ++n3;
        }
    }

    public static List h(String[] stringArray) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        int n3 = stringArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            arrayList.add(Base64.decode((String)stringArray[i3], (int)0));
        }
        return arrayList;
    }

    public static abstract class a {
        public static int a(TypedArray typedArray, int n3) {
            return typedArray.getType(n3);
        }
    }

    public static interface b {
    }

    public static final class c
    implements b {
        public final d[] a;

        public c(d[] dArray) {
            this.a = dArray;
        }

        public d[] a() {
            return this.a;
        }
    }

    public static final class d {
        public final String a;
        public final int b;
        public final boolean c;
        public final String d;
        public final int e;
        public final int f;

        public d(String string, int n3, boolean bl, String string2, int n4, int n5) {
            this.a = string;
            this.b = n3;
            this.c = bl;
            this.d = string2;
            this.e = n4;
            this.f = n5;
        }

        public String a() {
            return this.a;
        }

        public int b() {
            return this.f;
        }

        public int c() {
            return this.e;
        }

        public String d() {
            return this.d;
        }

        public int e() {
            return this.b;
        }

        public boolean f() {
            return this.c;
        }
    }

    public static final class e
    implements b {
        public final l0.e a;
        public final int b;
        public final int c;
        public final String d;

        public e(l0.e e3, int n3, int n4, String string) {
            this.a = e3;
            this.c = n3;
            this.b = n4;
            this.d = string;
        }

        public int a() {
            return this.c;
        }

        public l0.e b() {
            return this.a;
        }

        public String c() {
            return this.d;
        }

        public int d() {
            return this.b;
        }
    }
}

