/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package y;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import y.d;

public class e {
    public int a = -1;
    public int b = -1;
    public int c = -1;
    public SparseArray d = new SparseArray();

    public e(Context context, XmlPullParser xmlPullParser) {
        this.b(context, xmlPullParser);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int a(int n3, int n4, float f3, float f4) {
        a a4 = (a)this.d.get(n4);
        if (a4 == null) {
            return n4;
        }
        int n5 = 0;
        n4 = 0;
        if (f3 != -1.0f && f4 != -1.0f) {
            ArrayList arrayList = a4.b;
            int n6 = arrayList.size();
            Object object = null;
            while (n4 < n6) {
                Object object2 = arrayList.get(n4);
                n5 = n4 + 1;
                object2 = (b)object2;
                n4 = n5;
                if (!((b)object2).a(f3, f4)) continue;
                if (n3 == ((b)object2).e) return n3;
                object = object2;
                n4 = n5;
            }
            if (object == null) return a4.c;
            return ((b)object).e;
        }
        if (a4.c == n3) return n3;
        ArrayList arrayList = a4.b;
        int n7 = arrayList.size();
        for (n4 = n5; n4 < n7; ++n4) {
            Object e3 = arrayList.get(n4);
            if (n3 != ((b)e3).e) continue;
            return n3;
        }
        return a4.c;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void b(Context context, XmlPullParser xmlPullParser) {
        int n3;
        TypedArray typedArray;
        block16: {
            XmlPullParserException xmlPullParserException2;
            block17: {
                typedArray = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), y.d.StateSet);
                int n4 = typedArray.getIndexCount();
                for (n3 = 0; n3 < n4; ++n3) {
                    int n5 = typedArray.getIndex(n3);
                    if (n5 != y.d.StateSet_defaultState) continue;
                    this.a = typedArray.getResourceId(n5, this.a);
                }
                typedArray.recycle();
                try {
                    n3 = xmlPullParser.getEventType();
                    typedArray = null;
                    break block16;
                }
                catch (IOException iOException) {
                }
                catch (XmlPullParserException xmlPullParserException2) {
                    break block17;
                }
                Log.e((String)"ConstraintLayoutStates", (String)"Error parsing XML resource", (Throwable)iOException);
                return;
            }
            Log.e((String)"ConstraintLayoutStates", (String)"Error parsing XML resource", (Throwable)xmlPullParserException2);
            return;
        }
        while (n3 != 1) {
            Object object;
            if (n3 != 2) {
                if (n3 != 3) {
                    object = typedArray;
                } else {
                    object = typedArray;
                    if ("StateSet".equals(xmlPullParser.getName())) {
                        return;
                    }
                }
            } else {
                Object object2 = xmlPullParser.getName();
                switch (((String)object2).hashCode()) {
                    default: {
                        object = typedArray;
                        break;
                    }
                    case 1901439077: {
                        object = typedArray;
                        if (!((String)object2).equals("Variant")) break;
                        object2 = new b(context, xmlPullParser);
                        object = typedArray;
                        if (typedArray == null) break;
                        typedArray.a((b)object2);
                        object = typedArray;
                        break;
                    }
                    case 1382829617: {
                        ((String)object2).equals("StateSet");
                        object = typedArray;
                        break;
                    }
                    case 1301459538: {
                        ((String)object2).equals("LayoutDescription");
                        object = typedArray;
                        break;
                    }
                    case 80204913: {
                        object = typedArray;
                        if (!((String)object2).equals("State")) break;
                        object = new a(context, xmlPullParser);
                        this.d.put(object.a, object);
                    }
                }
            }
            n3 = xmlPullParser.next();
            typedArray = object;
        }
    }

    public int c(int n3, int n4, int n5) {
        return this.d(-1, n3, n4, n5);
    }

    public int d(int n3, int n4, float f3, float f4) {
        if (n3 == n4) {
            a a4 = n4 == -1 ? (a)this.d.valueAt(0) : (a)this.d.get(this.b);
            if (a4 == null) {
                return -1;
            }
            if (this.c != -1 && ((b)a4.b.get(n3)).a(f3, f4) || n3 == (n4 = a4.b(f3, f4))) {
                return n3;
            }
            if (n4 == -1) {
                return a4.c;
            }
            return ((b)a4.b.get((int)n4)).e;
        }
        a a5 = (a)this.d.get(n4);
        if (a5 == null) {
            return -1;
        }
        n3 = a5.b(f3, f4);
        if (n3 == -1) {
            return a5.c;
        }
        return ((b)a5.b.get((int)n3)).e;
    }

    public static class a {
        public int a;
        public ArrayList b = new ArrayList();
        public int c = -1;
        public boolean d = false;

        public a(Context context, XmlPullParser xmlPullParser) {
            xmlPullParser = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), y.d.State);
            int n3 = xmlPullParser.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = xmlPullParser.getIndex(i3);
                if (n4 == y.d.State_android_id) {
                    this.a = xmlPullParser.getResourceId(n4, this.a);
                    continue;
                }
                if (n4 != y.d.State_constraints) continue;
                this.c = xmlPullParser.getResourceId(n4, this.c);
                String string = context.getResources().getResourceTypeName(this.c);
                context.getResources().getResourceName(this.c);
                if (!"layout".equals(string)) continue;
                this.d = true;
            }
            xmlPullParser.recycle();
        }

        public void a(b b3) {
            this.b.add(b3);
        }

        public int b(float f3, float f4) {
            for (int i3 = 0; i3 < this.b.size(); ++i3) {
                if (!((b)this.b.get(i3)).a(f3, f4)) continue;
                return i3;
            }
            return -1;
        }
    }

    public static class b {
        public float a = Float.NaN;
        public float b = Float.NaN;
        public float c = Float.NaN;
        public float d = Float.NaN;
        public int e = -1;
        public boolean f = false;

        public b(Context context, XmlPullParser object) {
            TypedArray typedArray = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)object), y.d.Variant);
            int n3 = typedArray.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = typedArray.getIndex(i3);
                if (n4 == y.d.Variant_constraints) {
                    this.e = typedArray.getResourceId(n4, this.e);
                    object = context.getResources().getResourceTypeName(this.e);
                    context.getResources().getResourceName(this.e);
                    if (!"layout".equals(object)) continue;
                    this.f = true;
                    continue;
                }
                if (n4 == y.d.Variant_region_heightLessThan) {
                    this.d = typedArray.getDimension(n4, this.d);
                    continue;
                }
                if (n4 == y.d.Variant_region_heightMoreThan) {
                    this.b = typedArray.getDimension(n4, this.b);
                    continue;
                }
                if (n4 == y.d.Variant_region_widthLessThan) {
                    this.c = typedArray.getDimension(n4, this.c);
                    continue;
                }
                if (n4 != y.d.Variant_region_widthMoreThan) continue;
                this.a = typedArray.getDimension(n4, this.a);
            }
            typedArray.recycle();
        }

        public boolean a(float f3, float f4) {
            if (!Float.isNaN(this.a) && f3 < this.a) {
                return false;
            }
            if (!Float.isNaN(this.b) && f4 < this.b) {
                return false;
            }
            if (!Float.isNaN(this.c) && f3 > this.c) {
                return false;
            }
            return Float.isNaN(this.d) || !(f4 > this.d);
        }
    }
}

