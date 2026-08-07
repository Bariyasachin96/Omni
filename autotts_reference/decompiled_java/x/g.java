/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package x;

import android.content.Context;
import android.util.Log;
import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.a;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import x.d;
import x.e;
import x.f;
import x.h;
import x.j;
import x.k;
import x.m;

public class g {
    public static HashMap b;
    public HashMap a;

    static {
        HashMap hashMap;
        b = hashMap = new HashMap();
        try {
            hashMap.put("KeyAttribute", e.class.getConstructor(null));
            b.put("KeyPosition", h.class.getConstructor(null));
            b.put("KeyCycle", f.class.getConstructor(null));
            b.put("KeyTimeCycle", j.class.getConstructor(null));
            b.put("KeyTrigger", k.class.getConstructor(null));
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)"KeyFrames", (String)"unable to load", (Throwable)noSuchMethodException);
        }
    }

    public g() {
        this.a = new HashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public g(Context object, XmlPullParser object2) {
        d d3;
        int n3;
        block23: {
            XmlPullParserException xmlPullParserException2;
            block24: {
                this.a = new HashMap();
                try {
                    n3 = object2.getEventType();
                    d3 = null;
                    break block23;
                }
                catch (IOException iOException) {
                }
                catch (XmlPullParserException xmlPullParserException2) {
                    break block24;
                }
                Log.e((String)"KeyFrames", (String)"Error parsing XML resource", (Throwable)iOException);
                return;
            }
            Log.e((String)"KeyFrames", (String)"Error parsing XML resource", (Throwable)xmlPullParserException2);
            return;
        }
        while (n3 != 1) {
            d d4;
            block25: {
                Object object3;
                block29: {
                    block27: {
                        block28: {
                            block26: {
                                if (n3 == 2) break block26;
                                if (n3 != 3) {
                                    d4 = d3;
                                    break block25;
                                } else {
                                    d4 = d3;
                                    if ("KeyFrameSet".equals(object2.getName())) {
                                        return;
                                    }
                                }
                                break block25;
                            }
                            object3 = object2.getName();
                            if (!b.containsKey(object3)) break block27;
                            switch (((String)object3).hashCode()) {
                                default: {
                                    break block28;
                                }
                                case 1308496505: {
                                    if (((String)object3).equals("KeyTrigger")) {
                                        d3 = new k();
                                        break;
                                    }
                                    break block28;
                                }
                                case 1153397896: {
                                    if (((String)object3).equals("KeyPosition")) {
                                        d3 = new h();
                                        break;
                                    }
                                    break block28;
                                }
                                case 540053991: {
                                    if (((String)object3).equals("KeyCycle")) {
                                        d3 = new f();
                                        break;
                                    }
                                    break block28;
                                }
                                case -298435811: {
                                    if (((String)object3).equals("KeyAttribute")) {
                                        d3 = new e();
                                        break;
                                    }
                                    break block28;
                                }
                                case -300573030: {
                                    if (!((String)object3).equals("KeyTimeCycle")) break block28;
                                    d3 = new j();
                                }
                            }
                            d3.e((Context)object, Xml.asAttributeSet((XmlPullParser)object2));
                            this.c(d3);
                            d4 = d3;
                            break block25;
                        }
                        ((StringBuilder)object).append("Key ");
                        ((StringBuilder)object).append((String)object3);
                        ((StringBuilder)object).append(" not found");
                        super(((StringBuilder)object).toString());
                        throw object2;
                    }
                    if (!((String)object3).equalsIgnoreCase("CustomAttribute")) break block29;
                    d4 = d3;
                    if (d3 != null) {
                        object3 = d3.e;
                        d4 = d3;
                        if (object3 != null) {
                            androidx.constraintlayout.widget.a.i((Context)object, object2, (HashMap)object3);
                            d4 = d3;
                        }
                    }
                    break block25;
                }
                d4 = d3;
                if (((String)object3).equalsIgnoreCase("CustomMethod")) {
                    d4 = d3;
                    if (d3 != null) {
                        object3 = d3.e;
                        d4 = d3;
                        if (object3 != null) {
                            androidx.constraintlayout.widget.a.i((Context)object, object2, (HashMap)object3);
                            d4 = d3;
                        }
                    }
                }
            }
            n3 = object2.next();
            d3 = d4;
        }
    }

    public void a(m m3) {
        ArrayList arrayList = (ArrayList)this.a.get(-1);
        if (arrayList != null) {
            m3.b(arrayList);
        }
    }

    public void b(m m3) {
        ArrayList arrayList = (ArrayList)this.a.get(m3.c);
        if (arrayList != null) {
            m3.b(arrayList);
        }
        if ((arrayList = (ArrayList)this.a.get(-1)) != null) {
            int n3 = arrayList.size();
            int n4 = 0;
            while (n4 < n3) {
                Object object = arrayList.get(n4);
                int n5 = n4 + 1;
                object = (d)object;
                n4 = n5;
                if (!((d)object).f(((ConstraintLayout.LayoutParams)m3.b.getLayoutParams()).c0)) continue;
                m3.a((d)object);
                n4 = n5;
            }
        }
    }

    public void c(d d3) {
        ArrayList arrayList;
        if (!this.a.containsKey(d3.b)) {
            this.a.put(d3.b, new ArrayList());
        }
        if ((arrayList = (ArrayList)this.a.get(d3.b)) != null) {
            arrayList.add(d3);
        }
    }

    public ArrayList d(int n3) {
        return (ArrayList)this.a.get(n3);
    }
}

