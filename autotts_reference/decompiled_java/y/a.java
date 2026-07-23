/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package y;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import y.d;

public class a {
    public final ConstraintLayout a;
    public androidx.constraintlayout.widget.b b;
    public int c = -1;
    public int d = -1;
    public SparseArray e = new SparseArray();
    public SparseArray f = new SparseArray();

    public a(Context context, ConstraintLayout constraintLayout, int n3) {
        this.a = constraintLayout;
        this.a(context, n3);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(Context var1_1, int var2_3) {
        block15: {
            block16: {
                var6_4 = var1_1.getResources().getXml(var2_3);
                try {
                    var3_5 = var6_4.getEventType();
                    var4_6 = null;
                    break block15;
                }
                catch (IOException var1_2) {
                }
                catch (XmlPullParserException var4_7) {
                    break block16;
                }
                var4_8 = new StringBuilder();
                var4_8.append("Error parsing resource: ");
                var4_8.append(var2_3);
                Log.e((String)"ConstraintLayoutStates", (String)var4_8.toString(), (Throwable)var1_2);
                return;
            }
            var1_1 = new StringBuilder();
            var1_1.append("Error parsing resource: ");
            var1_1.append(var2_3);
            Log.e((String)"ConstraintLayoutStates", (String)var1_1.toString(), (Throwable)var4_7);
            return;
        }
        while (var3_5 != 1) {
            block18: {
                block17: {
                    if (var3_5 == 2) break block17;
                    var5_9 = var4_6;
                    break block18;
                }
                var7_10 = var6_4.getName();
                switch (var7_10.hashCode()) {
                    default: {
                        var5_9 = var4_6;
                        break block18;
                    }
                    case 1901439077: {
                        var5_9 = var4_6;
                        if (var7_10.equals("Variant")) {
                            var7_10 = new b((Context)var1_1, (XmlPullParser)var6_4);
                            var5_9 = var4_6;
                            if (var4_6 != null) {
                                var4_6.a((b)var7_10);
                                var5_9 = var4_6;
                            }
                        }
                        break block18;
                    }
                    case 1657696882: {
                        var5_9 = "layoutDescription";
                        ** GOTO lbl53
                    }
                    case 1382829617: {
                        var5_9 = "StateSet";
lbl53:
                        // 2 sources

                        var7_10.equals(var5_9);
                        var5_9 = var4_6;
                        break block18;
                    }
                    case 80204913: {
                        var5_9 = var4_6;
                        if (var7_10.equals("State")) {
                            var5_9 = new a((Context)var1_1, (XmlPullParser)var6_4);
                            this.e.put(var5_9.a, var5_9);
                        }
                        break block18;
                    }
                    case -1349929691: 
                }
                var5_9 = var4_6;
                if (var7_10.equals("ConstraintSet")) {
                    this.b((Context)var1_1, (XmlPullParser)var6_4);
                    var5_9 = var4_6;
                }
            }
            var3_5 = var6_4.next();
            var4_6 = var5_9;
        }
    }

    public final void b(Context context, XmlPullParser xmlPullParser) {
        androidx.constraintlayout.widget.b b3 = new androidx.constraintlayout.widget.b();
        int n3 = xmlPullParser.getAttributeCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            String string = xmlPullParser.getAttributeName(i3);
            String string2 = xmlPullParser.getAttributeValue(i3);
            if (string == null || string2 == null || !"id".equals(string)) continue;
            if (string2.contains("/")) {
                string = string2.substring(string2.indexOf(47) + 1);
                i3 = context.getResources().getIdentifier(string, "id", context.getPackageName());
            } else {
                i3 = -1;
            }
            n3 = i3;
            if (i3 == -1) {
                if (string2.length() > 1) {
                    n3 = Integer.parseInt(string2.substring(1));
                } else {
                    Log.e((String)"ConstraintLayoutStates", (String)"error in parsing id");
                    n3 = i3;
                }
            }
            b3.D(context, xmlPullParser);
            this.f.put(n3, (Object)b3);
            return;
        }
    }

    public void c(y.b b3) {
    }

    public void d(int n3, float f3, float f4) {
        int n4;
        block6: {
            androidx.constraintlayout.widget.b b3;
            block8: {
                block7: {
                    n4 = this.c;
                    if (n4 != n3) break block6;
                    a a4 = n3 == -1 ? (a)this.e.valueAt(0) : (a)this.e.get(n4);
                    n3 = this.d;
                    if (n3 != -1 && ((b)a4.b.get(n3)).a(f3, f4) || this.d == (n4 = a4.b(f3, f4))) break block7;
                    b3 = n4 == -1 ? this.b : ((b)a4.b.get((int)n4)).f;
                    if (n4 != -1) {
                        n3 = ((b)a4.b.get((int)n4)).e;
                    }
                    if (b3 != null) break block8;
                }
                return;
            }
            this.d = n4;
            b3.i(this.a);
            return;
        }
        this.c = n3;
        a a5 = (a)this.e.get(n3);
        n4 = a5.b(f3, f4);
        androidx.constraintlayout.widget.b b4 = n4 == -1 ? a5.d : ((b)a5.b.get((int)n4)).f;
        if (n4 != -1) {
            n3 = ((b)a5.b.get((int)n4)).e;
        }
        if (b4 == null) {
            return;
        }
        this.d = n4;
        b4.i(this.a);
    }

    public static class a {
        public int a;
        public ArrayList b = new ArrayList();
        public int c = -1;
        public androidx.constraintlayout.widget.b d;

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
                Object object = context.getResources().getResourceTypeName(this.c);
                context.getResources().getResourceName(this.c);
                if (!"layout".equals(object)) continue;
                this.d = object = new androidx.constraintlayout.widget.b();
                ((androidx.constraintlayout.widget.b)object).n(context, this.c);
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
        public androidx.constraintlayout.widget.b f;

        public b(Context context, XmlPullParser xmlPullParser) {
            xmlPullParser = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), y.d.Variant);
            int n3 = xmlPullParser.getIndexCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = xmlPullParser.getIndex(i3);
                if (n4 == y.d.Variant_constraints) {
                    this.e = xmlPullParser.getResourceId(n4, this.e);
                    Object object = context.getResources().getResourceTypeName(this.e);
                    context.getResources().getResourceName(this.e);
                    if (!"layout".equals(object)) continue;
                    this.f = object = new androidx.constraintlayout.widget.b();
                    ((androidx.constraintlayout.widget.b)object).n(context, this.e);
                    continue;
                }
                if (n4 == y.d.Variant_region_heightLessThan) {
                    this.d = xmlPullParser.getDimension(n4, this.d);
                    continue;
                }
                if (n4 == y.d.Variant_region_heightMoreThan) {
                    this.b = xmlPullParser.getDimension(n4, this.b);
                    continue;
                }
                if (n4 == y.d.Variant_region_widthLessThan) {
                    this.c = xmlPullParser.getDimension(n4, this.c);
                    continue;
                }
                if (n4 != y.d.Variant_region_widthMoreThan) continue;
                this.a = xmlPullParser.getDimension(n4, this.a);
            }
            xmlPullParser.recycle();
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

