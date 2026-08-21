/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 */
package x;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import s.o;
import w.f;
import x.d;

public class j
extends d {
    public String g;
    public int h = -1;
    public float i = Float.NaN;
    public float j = Float.NaN;
    public float k = Float.NaN;
    public float l = Float.NaN;
    public float m = Float.NaN;
    public float n = Float.NaN;
    public float o = Float.NaN;
    public float p = Float.NaN;
    public float q = Float.NaN;
    public float r = Float.NaN;
    public float s = Float.NaN;
    public float t = Float.NaN;
    public int u = 0;
    public String v = null;
    public float w = Float.NaN;
    public float x = 0.0f;

    public j() {
        this.d = 3;
        this.e = new HashMap();
    }

    public static /* synthetic */ float A(j j3, float f3) {
        j3.r = f3;
        return f3;
    }

    public static /* synthetic */ float C(j j3, float f3) {
        j3.s = f3;
        return f3;
    }

    public static /* synthetic */ float E(j j3, float f3) {
        j3.t = f3;
        return f3;
    }

    public static /* synthetic */ float G(j j3, float f3) {
        j3.k = f3;
        return f3;
    }

    public static /* synthetic */ int I(j j3, int n3) {
        j3.h = n3;
        return n3;
    }

    public static /* synthetic */ String J(j j3, String string) {
        j3.v = string;
        return string;
    }

    public static /* synthetic */ int L(j j3, int n3) {
        j3.u = n3;
        return n3;
    }

    public static /* synthetic */ float N(j j3, float f3) {
        j3.w = f3;
        return f3;
    }

    public static /* synthetic */ float P(j j3, float f3) {
        j3.x = f3;
        return f3;
    }

    public static /* synthetic */ float R(j j3, float f3) {
        j3.o = f3;
        return f3;
    }

    public static /* synthetic */ float T(j j3, float f3) {
        j3.l = f3;
        return f3;
    }

    public static /* synthetic */ float n(j j3, float f3) {
        j3.i = f3;
        return f3;
    }

    public static /* synthetic */ float q(j j3, float f3) {
        j3.m = f3;
        return f3;
    }

    public static /* synthetic */ float r(j j3, float f3) {
        j3.j = f3;
        return f3;
    }

    public static /* synthetic */ String s(j j3, String string) {
        j3.g = string;
        return string;
    }

    public static /* synthetic */ float u(j j3, float f3) {
        j3.p = f3;
        return f3;
    }

    public static /* synthetic */ float w(j j3, float f3) {
        j3.n = f3;
        return f3;
    }

    public static /* synthetic */ float y(j j3, float f3) {
        j3.q = f3;
        return f3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void U(HashMap hashMap) {
        Iterator iterator = hashMap.keySet().iterator();
        block42: while (iterator.hasNext()) {
            Object object = (String)iterator.next();
            Object object2 = (f)hashMap.get(object);
            if (object2 == null) continue;
            boolean bl = ((String)object).startsWith("CUSTOM");
            int n3 = 7;
            if (bl) {
                object = ((String)object).substring(7);
                if ((object = (androidx.constraintlayout.widget.a)this.e.get(object)) == null) continue;
                ((f.b)object2).j(this.a, (androidx.constraintlayout.widget.a)object, this.w, this.u, this.x);
                continue;
            }
            int n4 = -1;
            switch (((String)object).hashCode()) {
                case 92909918: {
                    if (!((String)object).equals("alpha")) break;
                    n4 = 1;
                    break;
                }
                case 37232917: {
                    if (!((String)object).equals("transitionPathRotate")) break;
                    n4 = 2;
                    break;
                }
                case -4379043: {
                    if (!((String)object).equals("elevation")) break;
                    n4 = 3;
                    break;
                }
                case -40300674: {
                    if (!((String)object).equals("rotation")) break;
                    n4 = 4;
                    break;
                }
                case -908189617: {
                    if (!((String)object).equals("scaleY")) break;
                    n4 = 5;
                    break;
                }
                case -908189618: {
                    if (!((String)object).equals("scaleX")) break;
                    n4 = 6;
                    break;
                }
                case -1001078227: {
                    if (!((String)object).equals("progress")) break;
                    n4 = 7;
                    break;
                }
                case -1225497655: {
                    if (!((String)object).equals("translationZ")) break;
                    n4 = 8;
                    break;
                }
                case -1225497656: {
                    if (!((String)object).equals("translationY")) break;
                    n4 = 9;
                    break;
                }
                case -1225497657: {
                    if (!((String)object).equals("translationX")) break;
                    n4 = 10;
                    break;
                }
                case -1249320805: {
                    if (!((String)object).equals("rotationY")) break;
                    n4 = 11;
                    break;
                }
                case -1249320806: {
                    if (!((String)object).equals("rotationX")) break;
                    n4 = 12;
                    break;
                }
            }
            switch (n4) {
                default: {
                    n3 = -1;
                    break;
                }
                case 1: {
                    n3 = 11;
                    break;
                }
                case 2: {
                    n3 = 10;
                    break;
                }
                case 3: {
                    n3 = 9;
                    break;
                }
                case 4: {
                    n3 = 8;
                    break;
                }
                case 6: {
                    n3 = 6;
                    break;
                }
                case 7: {
                    n3 = 5;
                    break;
                }
                case 8: {
                    n3 = 4;
                    break;
                }
                case 9: {
                    n3 = 3;
                    break;
                }
                case 10: {
                    n3 = 2;
                    break;
                }
                case 11: {
                    n3 = 1;
                    break;
                }
                case 12: {
                    n3 = 0;
                    break;
                }
                case 5: 
            }
            switch (n3) {
                default: {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("UNKNOWN addValues \"");
                    ((StringBuilder)object2).append((String)object);
                    ((StringBuilder)object2).append("\"");
                    Log.e((String)"KeyTimeCycles", (String)((StringBuilder)object2).toString());
                    continue block42;
                }
                case 11: {
                    if (Float.isNaN(this.i)) continue block42;
                    ((o)object2).b(this.a, this.i, this.w, this.u, this.x);
                    continue block42;
                }
                case 10: {
                    if (Float.isNaN(this.n)) continue block42;
                    ((o)object2).b(this.a, this.n, this.w, this.u, this.x);
                    continue block42;
                }
                case 9: {
                    if (Float.isNaN(this.j)) continue block42;
                    ((o)object2).b(this.a, this.j, this.w, this.u, this.x);
                    continue block42;
                }
                case 8: {
                    if (Float.isNaN(this.k)) continue block42;
                    ((o)object2).b(this.a, this.k, this.w, this.u, this.x);
                    continue block42;
                }
                case 7: {
                    if (Float.isNaN(this.p)) continue block42;
                    ((o)object2).b(this.a, this.p, this.w, this.u, this.x);
                    continue block42;
                }
                case 6: {
                    if (Float.isNaN(this.o)) continue block42;
                    ((o)object2).b(this.a, this.o, this.w, this.u, this.x);
                    continue block42;
                }
                case 5: {
                    if (Float.isNaN(this.t)) continue block42;
                    ((o)object2).b(this.a, this.t, this.w, this.u, this.x);
                    continue block42;
                }
                case 4: {
                    if (Float.isNaN(this.s)) continue block42;
                    ((o)object2).b(this.a, this.s, this.w, this.u, this.x);
                    continue block42;
                }
                case 3: {
                    if (Float.isNaN(this.r)) continue block42;
                    ((o)object2).b(this.a, this.r, this.w, this.u, this.x);
                    continue block42;
                }
                case 2: {
                    if (Float.isNaN(this.q)) continue block42;
                    ((o)object2).b(this.a, this.q, this.w, this.u, this.x);
                    continue block42;
                }
                case 1: {
                    if (Float.isNaN(this.m)) continue block42;
                    ((o)object2).b(this.a, this.m, this.w, this.u, this.x);
                    continue block42;
                }
                case 0: 
            }
            if (Float.isNaN(this.l)) continue;
            ((o)object2).b(this.a, this.l, this.w, this.u, this.x);
        }
        return;
    }

    @Override
    public void a(HashMap hashMap) {
        throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
    }

    @Override
    public d b() {
        return new j().c(this);
    }

    @Override
    public d c(d d3) {
        super.c(d3);
        d3 = (j)d3;
        this.g = ((j)d3).g;
        this.h = ((j)d3).h;
        this.u = ((j)d3).u;
        this.w = ((j)d3).w;
        this.x = ((j)d3).x;
        this.t = ((j)d3).t;
        this.i = ((j)d3).i;
        this.j = ((j)d3).j;
        this.k = ((j)d3).k;
        this.n = ((j)d3).n;
        this.l = ((j)d3).l;
        this.m = ((j)d3).m;
        this.o = ((j)d3).o;
        this.p = ((j)d3).p;
        this.q = ((j)d3).q;
        this.r = ((j)d3).r;
        this.s = ((j)d3).s;
        this.v = ((j)d3).v;
        return this;
    }

    @Override
    public void d(HashSet hashSet) {
        if (!Float.isNaN(this.i)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.j)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.k)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.l)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.m)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.q)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.r)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.s)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.n)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.o)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.p)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.t)) {
            hashSet.add("progress");
        }
        if (this.e.size() > 0) {
            for (String string : this.e.keySet()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("CUSTOM,");
                stringBuilder.append(string);
                hashSet.add(stringBuilder.toString());
            }
        }
    }

    @Override
    public void e(Context context, AttributeSet attributeSet) {
        x.j$a.a(this, context.obtainStyledAttributes(attributeSet, y.d.KeyTimeCycle));
    }

    @Override
    public void h(HashMap hashMap) {
        if (this.h != -1) {
            if (!Float.isNaN(this.i)) {
                hashMap.put("alpha", this.h);
            }
            if (!Float.isNaN(this.j)) {
                hashMap.put("elevation", this.h);
            }
            if (!Float.isNaN(this.k)) {
                hashMap.put("rotation", this.h);
            }
            if (!Float.isNaN(this.l)) {
                hashMap.put("rotationX", this.h);
            }
            if (!Float.isNaN(this.m)) {
                hashMap.put("rotationY", this.h);
            }
            if (!Float.isNaN(this.q)) {
                hashMap.put("translationX", this.h);
            }
            if (!Float.isNaN(this.r)) {
                hashMap.put("translationY", this.h);
            }
            if (!Float.isNaN(this.s)) {
                hashMap.put("translationZ", this.h);
            }
            if (!Float.isNaN(this.n)) {
                hashMap.put("transitionPathRotate", this.h);
            }
            if (!Float.isNaN(this.o)) {
                hashMap.put("scaleX", this.h);
            }
            if (!Float.isNaN(this.o)) {
                hashMap.put("scaleY", this.h);
            }
            if (!Float.isNaN(this.t)) {
                hashMap.put("progress", this.h);
            }
            if (this.e.size() > 0) {
                for (String string : this.e.keySet()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("CUSTOM,");
                    stringBuilder.append(string);
                    hashMap.put(stringBuilder.toString(), this.h);
                }
            }
        }
    }

    public static abstract class a {
        public static SparseIntArray a;

        static {
            SparseIntArray sparseIntArray;
            a = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.KeyTimeCycle_android_alpha, 1);
            a.append(y.d.KeyTimeCycle_android_elevation, 2);
            a.append(y.d.KeyTimeCycle_android_rotation, 4);
            a.append(y.d.KeyTimeCycle_android_rotationX, 5);
            a.append(y.d.KeyTimeCycle_android_rotationY, 6);
            a.append(y.d.KeyTimeCycle_android_scaleX, 7);
            a.append(y.d.KeyTimeCycle_transitionPathRotate, 8);
            a.append(y.d.KeyTimeCycle_transitionEasing, 9);
            a.append(y.d.KeyTimeCycle_motionTarget, 10);
            a.append(y.d.KeyTimeCycle_framePosition, 12);
            a.append(y.d.KeyTimeCycle_curveFit, 13);
            a.append(y.d.KeyTimeCycle_android_scaleY, 14);
            a.append(y.d.KeyTimeCycle_android_translationX, 15);
            a.append(y.d.KeyTimeCycle_android_translationY, 16);
            a.append(y.d.KeyTimeCycle_android_translationZ, 17);
            a.append(y.d.KeyTimeCycle_motionProgress, 18);
            a.append(y.d.KeyTimeCycle_wavePeriod, 20);
            a.append(y.d.KeyTimeCycle_waveOffset, 21);
            a.append(y.d.KeyTimeCycle_waveShape, 19);
        }

        public static void a(j j3, TypedArray typedArray) {
            int n3 = typedArray.getIndexCount();
            block21: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = typedArray.getIndex(i3);
                switch (a.get(n4)) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unused attribute 0x");
                        stringBuilder.append(Integer.toHexString(n4));
                        stringBuilder.append("   ");
                        stringBuilder.append(a.get(n4));
                        Log.e((String)"KeyTimeCycle", (String)stringBuilder.toString());
                        continue block21;
                    }
                    case 21: {
                        if (typedArray.peekValue((int)n4).type == 5) {
                            x.j.P(j3, typedArray.getDimension(n4, j3.x));
                            continue block21;
                        }
                        x.j.P(j3, typedArray.getFloat(n4, j3.x));
                        continue block21;
                    }
                    case 20: {
                        x.j.N(j3, typedArray.getFloat(n4, j3.w));
                        continue block21;
                    }
                    case 19: {
                        if (typedArray.peekValue((int)n4).type == 3) {
                            x.j.J(j3, typedArray.getString(n4));
                            x.j.L(j3, 7);
                            continue block21;
                        }
                        x.j.L(j3, typedArray.getInt(n4, j3.u));
                        continue block21;
                    }
                    case 18: {
                        x.j.E(j3, typedArray.getFloat(n4, j3.t));
                        continue block21;
                    }
                    case 17: {
                        x.j.C(j3, typedArray.getDimension(n4, j3.s));
                        continue block21;
                    }
                    case 16: {
                        x.j.A(j3, typedArray.getDimension(n4, j3.r));
                        continue block21;
                    }
                    case 15: {
                        x.j.y(j3, typedArray.getDimension(n4, j3.q));
                        continue block21;
                    }
                    case 14: {
                        x.j.u(j3, typedArray.getFloat(n4, j3.p));
                        continue block21;
                    }
                    case 13: {
                        x.j.I(j3, typedArray.getInteger(n4, j3.h));
                        continue block21;
                    }
                    case 12: {
                        j3.a = typedArray.getInt(n4, j3.a);
                        continue block21;
                    }
                    case 10: {
                        if (MotionLayout.f1) {
                            int n5;
                            j3.b = n5 = typedArray.getResourceId(n4, j3.b);
                            if (n5 != -1) continue block21;
                            j3.c = typedArray.getString(n4);
                            continue block21;
                        }
                        if (typedArray.peekValue((int)n4).type == 3) {
                            j3.c = typedArray.getString(n4);
                            continue block21;
                        }
                        j3.b = typedArray.getResourceId(n4, j3.b);
                        continue block21;
                    }
                    case 9: {
                        x.j.s(j3, typedArray.getString(n4));
                        continue block21;
                    }
                    case 8: {
                        x.j.w(j3, typedArray.getFloat(n4, j3.n));
                        continue block21;
                    }
                    case 7: {
                        x.j.R(j3, typedArray.getFloat(n4, j3.o));
                        continue block21;
                    }
                    case 6: {
                        x.j.q(j3, typedArray.getFloat(n4, j3.m));
                        continue block21;
                    }
                    case 5: {
                        x.j.T(j3, typedArray.getFloat(n4, j3.l));
                        continue block21;
                    }
                    case 4: {
                        x.j.G(j3, typedArray.getFloat(n4, j3.k));
                        continue block21;
                    }
                    case 2: {
                        x.j.r(j3, typedArray.getDimension(n4, j3.j));
                        continue block21;
                    }
                    case 1: {
                        x.j.n(j3, typedArray.getFloat(n4, j3.i));
                    }
                }
            }
        }
    }
}

