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
import s.j;
import w.d;
import x.d;

public class e
extends d {
    public String g;
    public int h = -1;
    public boolean i = false;
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
    public float u = Float.NaN;
    public float v = Float.NaN;
    public float w = Float.NaN;

    public e() {
        this.d = 1;
        this.e = new HashMap();
    }

    public static /* synthetic */ float B(e e3, float f3) {
        e3.w = f3;
        return f3;
    }

    public static /* synthetic */ float D(e e3, float f3) {
        e3.l = f3;
        return f3;
    }

    public static /* synthetic */ int F(e e3, int n3) {
        e3.h = n3;
        return n3;
    }

    public static /* synthetic */ float H(e e3, float f3) {
        e3.r = f3;
        return f3;
    }

    public static /* synthetic */ float J(e e3, float f3) {
        e3.m = f3;
        return f3;
    }

    public static /* synthetic */ float L(e e3, float f3) {
        e3.n = f3;
        return f3;
    }

    public static /* synthetic */ float N(e e3, float f3) {
        e3.o = f3;
        return f3;
    }

    public static /* synthetic */ float P(e e3, float f3) {
        e3.p = f3;
        return f3;
    }

    public static /* synthetic */ String Q(e e3, String string) {
        e3.g = string;
        return string;
    }

    public static /* synthetic */ float n(e e3, float f3) {
        e3.j = f3;
        return f3;
    }

    public static /* synthetic */ float q(e e3, float f3) {
        e3.s = f3;
        return f3;
    }

    public static /* synthetic */ float r(e e3, float f3) {
        e3.k = f3;
        return f3;
    }

    public static /* synthetic */ float t(e e3, float f3) {
        e3.q = f3;
        return f3;
    }

    public static /* synthetic */ float v(e e3, float f3) {
        e3.t = f3;
        return f3;
    }

    public static /* synthetic */ float x(e e3, float f3) {
        e3.u = f3;
        return f3;
    }

    public static /* synthetic */ float z(e e3, float f3) {
        e3.v = f3;
        return f3;
    }

    public void R(String string, Object object) {
        string.getClass();
        int n3 = string.hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 1941332754: {
                if (!string.equals("visibility")) break;
                n4 = 16;
                break;
            }
            case 579057826: {
                if (!string.equals("curveFit")) break;
                n4 = 15;
                break;
            }
            case 92909918: {
                if (!string.equals("alpha")) break;
                n4 = 14;
                break;
            }
            case 37232917: {
                if (!string.equals("transitionPathRotate")) break;
                n4 = 13;
                break;
            }
            case -4379043: {
                if (!string.equals("elevation")) break;
                n4 = 12;
                break;
            }
            case -40300674: {
                if (!string.equals("rotation")) break;
                n4 = 11;
                break;
            }
            case -760884509: {
                if (!string.equals("transformPivotY")) break;
                n4 = 10;
                break;
            }
            case -760884510: {
                if (!string.equals("transformPivotX")) break;
                n4 = 9;
                break;
            }
            case -908189617: {
                if (!string.equals("scaleY")) break;
                n4 = 8;
                break;
            }
            case -908189618: {
                if (!string.equals("scaleX")) break;
                n4 = 7;
                break;
            }
            case -1225497655: {
                if (!string.equals("translationZ")) break;
                n4 = 6;
                break;
            }
            case -1225497656: {
                if (!string.equals("translationY")) break;
                n4 = 5;
                break;
            }
            case -1225497657: {
                if (!string.equals("translationX")) break;
                n4 = 4;
                break;
            }
            case -1249320805: {
                if (!string.equals("rotationY")) break;
                n4 = 3;
                break;
            }
            case -1249320806: {
                if (!string.equals("rotationX")) break;
                n4 = 2;
                break;
            }
            case -1812823328: {
                if (!string.equals("transitionEasing")) break;
                n4 = 1;
                break;
            }
            case -1913008125: {
                if (!string.equals("motionProgress")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                return;
            }
            case 16: {
                this.i = this.j(object);
                return;
            }
            case 15: {
                this.h = this.l(object);
                return;
            }
            case 14: {
                this.j = this.k(object);
                return;
            }
            case 13: {
                this.q = this.k(object);
                return;
            }
            case 12: {
                this.k = this.k(object);
                return;
            }
            case 11: {
                this.l = this.k(object);
                return;
            }
            case 10: {
                this.p = this.k(object);
                return;
            }
            case 9: {
                this.o = this.k(object);
                return;
            }
            case 8: {
                this.s = this.k(object);
                return;
            }
            case 7: {
                this.r = this.k(object);
                return;
            }
            case 6: {
                this.v = this.k(object);
                return;
            }
            case 5: {
                this.u = this.k(object);
                return;
            }
            case 4: {
                this.t = this.k(object);
                return;
            }
            case 3: {
                this.n = this.k(object);
                return;
            }
            case 2: {
                this.m = this.k(object);
                return;
            }
            case 1: {
                this.g = object.toString();
                return;
            }
            case 0: 
        }
        this.w = this.k(object);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(HashMap hashMap) {
        Iterator iterator = hashMap.keySet().iterator();
        block48: while (iterator.hasNext()) {
            Object object = (String)iterator.next();
            j j3 = (j)hashMap.get(object);
            if (j3 == null) continue;
            boolean bl = ((String)object).startsWith("CUSTOM");
            int n3 = 7;
            if (bl) {
                object = ((String)object).substring(7);
                if ((object = (androidx.constraintlayout.widget.a)this.e.get(object)) == null) continue;
                ((d.b)j3).i(this.a, (androidx.constraintlayout.widget.a)object);
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
                case -760884509: {
                    if (!((String)object).equals("transformPivotY")) break;
                    n4 = 5;
                    break;
                }
                case -760884510: {
                    if (!((String)object).equals("transformPivotX")) break;
                    n4 = 6;
                    break;
                }
                case -908189617: {
                    if (!((String)object).equals("scaleY")) break;
                    n4 = 7;
                    break;
                }
                case -908189618: {
                    if (!((String)object).equals("scaleX")) break;
                    n4 = 8;
                    break;
                }
                case -1001078227: {
                    if (!((String)object).equals("progress")) break;
                    n4 = 9;
                    break;
                }
                case -1225497655: {
                    if (!((String)object).equals("translationZ")) break;
                    n4 = 10;
                    break;
                }
                case -1225497656: {
                    if (!((String)object).equals("translationY")) break;
                    n4 = 11;
                    break;
                }
                case -1225497657: {
                    if (!((String)object).equals("translationX")) break;
                    n4 = 12;
                    break;
                }
                case -1249320805: {
                    if (!((String)object).equals("rotationY")) break;
                    n4 = 13;
                    break;
                }
                case -1249320806: {
                    if (!((String)object).equals("rotationX")) break;
                    n4 = 14;
                    break;
                }
            }
            switch (n4) {
                default: {
                    n3 = -1;
                    break;
                }
                case 1: {
                    n3 = 13;
                    break;
                }
                case 2: {
                    n3 = 12;
                    break;
                }
                case 3: {
                    n3 = 11;
                    break;
                }
                case 4: {
                    n3 = 10;
                    break;
                }
                case 5: {
                    n3 = 9;
                    break;
                }
                case 6: {
                    n3 = 8;
                    break;
                }
                case 8: {
                    n3 = 6;
                    break;
                }
                case 9: {
                    n3 = 5;
                    break;
                }
                case 10: {
                    n3 = 4;
                    break;
                }
                case 11: {
                    n3 = 3;
                    break;
                }
                case 12: {
                    n3 = 2;
                    break;
                }
                case 13: {
                    n3 = 1;
                    break;
                }
                case 14: {
                    n3 = 0;
                    break;
                }
                case 7: 
            }
            switch (n3) {
                default: {
                    continue block48;
                }
                case 13: {
                    if (Float.isNaN(this.j)) continue block48;
                    j3.c(this.a, this.j);
                    continue block48;
                }
                case 12: {
                    if (Float.isNaN(this.q)) continue block48;
                    j3.c(this.a, this.q);
                    continue block48;
                }
                case 11: {
                    if (Float.isNaN(this.k)) continue block48;
                    j3.c(this.a, this.k);
                    continue block48;
                }
                case 10: {
                    if (Float.isNaN(this.l)) continue block48;
                    j3.c(this.a, this.l);
                    continue block48;
                }
                case 9: {
                    if (Float.isNaN(this.n)) continue block48;
                    j3.c(this.a, this.p);
                    continue block48;
                }
                case 8: {
                    if (Float.isNaN(this.m)) continue block48;
                    j3.c(this.a, this.o);
                    continue block48;
                }
                case 7: {
                    if (Float.isNaN(this.s)) continue block48;
                    j3.c(this.a, this.s);
                    continue block48;
                }
                case 6: {
                    if (Float.isNaN(this.r)) continue block48;
                    j3.c(this.a, this.r);
                    continue block48;
                }
                case 5: {
                    if (Float.isNaN(this.w)) continue block48;
                    j3.c(this.a, this.w);
                    continue block48;
                }
                case 4: {
                    if (Float.isNaN(this.v)) continue block48;
                    j3.c(this.a, this.v);
                    continue block48;
                }
                case 3: {
                    if (Float.isNaN(this.u)) continue block48;
                    j3.c(this.a, this.u);
                    continue block48;
                }
                case 2: {
                    if (Float.isNaN(this.t)) continue block48;
                    j3.c(this.a, this.t);
                    continue block48;
                }
                case 1: {
                    if (Float.isNaN(this.n)) continue block48;
                    j3.c(this.a, this.n);
                    continue block48;
                }
                case 0: 
            }
            if (Float.isNaN(this.m)) continue;
            j3.c(this.a, this.m);
        }
        return;
    }

    @Override
    public d b() {
        return new e().c(this);
    }

    @Override
    public d c(d d3) {
        super.c(d3);
        d3 = (e)d3;
        this.h = ((e)d3).h;
        this.i = ((e)d3).i;
        this.j = ((e)d3).j;
        this.k = ((e)d3).k;
        this.l = ((e)d3).l;
        this.m = ((e)d3).m;
        this.n = ((e)d3).n;
        this.o = ((e)d3).o;
        this.p = ((e)d3).p;
        this.q = ((e)d3).q;
        this.r = ((e)d3).r;
        this.s = ((e)d3).s;
        this.t = ((e)d3).t;
        this.u = ((e)d3).u;
        this.v = ((e)d3).v;
        this.w = ((e)d3).w;
        this.g = ((e)d3).g;
        return this;
    }

    @Override
    public void d(HashSet hashSet) {
        if (!Float.isNaN(this.j)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.k)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.l)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.m)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.n)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.o)) {
            hashSet.add("transformPivotX");
        }
        if (!Float.isNaN(this.p)) {
            hashSet.add("transformPivotY");
        }
        if (!Float.isNaN(this.t)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.u)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.v)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.q)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.r)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.s)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.w)) {
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
        x.e$a.a(this, context.obtainStyledAttributes(attributeSet, y.d.KeyAttribute));
    }

    @Override
    public void h(HashMap hashMap) {
        if (this.h != -1) {
            if (!Float.isNaN(this.j)) {
                hashMap.put("alpha", this.h);
            }
            if (!Float.isNaN(this.k)) {
                hashMap.put("elevation", this.h);
            }
            if (!Float.isNaN(this.l)) {
                hashMap.put("rotation", this.h);
            }
            if (!Float.isNaN(this.m)) {
                hashMap.put("rotationX", this.h);
            }
            if (!Float.isNaN(this.n)) {
                hashMap.put("rotationY", this.h);
            }
            if (!Float.isNaN(this.o)) {
                hashMap.put("transformPivotX", this.h);
            }
            if (!Float.isNaN(this.p)) {
                hashMap.put("transformPivotY", this.h);
            }
            if (!Float.isNaN(this.t)) {
                hashMap.put("translationX", this.h);
            }
            if (!Float.isNaN(this.u)) {
                hashMap.put("translationY", this.h);
            }
            if (!Float.isNaN(this.v)) {
                hashMap.put("translationZ", this.h);
            }
            if (!Float.isNaN(this.q)) {
                hashMap.put("transitionPathRotate", this.h);
            }
            if (!Float.isNaN(this.r)) {
                hashMap.put("scaleX", this.h);
            }
            if (!Float.isNaN(this.s)) {
                hashMap.put("scaleY", this.h);
            }
            if (!Float.isNaN(this.w)) {
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
            sparseIntArray.append(y.d.KeyAttribute_android_alpha, 1);
            a.append(y.d.KeyAttribute_android_elevation, 2);
            a.append(y.d.KeyAttribute_android_rotation, 4);
            a.append(y.d.KeyAttribute_android_rotationX, 5);
            a.append(y.d.KeyAttribute_android_rotationY, 6);
            a.append(y.d.KeyAttribute_android_transformPivotX, 19);
            a.append(y.d.KeyAttribute_android_transformPivotY, 20);
            a.append(y.d.KeyAttribute_android_scaleX, 7);
            a.append(y.d.KeyAttribute_transitionPathRotate, 8);
            a.append(y.d.KeyAttribute_transitionEasing, 9);
            a.append(y.d.KeyAttribute_motionTarget, 10);
            a.append(y.d.KeyAttribute_framePosition, 12);
            a.append(y.d.KeyAttribute_curveFit, 13);
            a.append(y.d.KeyAttribute_android_scaleY, 14);
            a.append(y.d.KeyAttribute_android_translationX, 15);
            a.append(y.d.KeyAttribute_android_translationY, 16);
            a.append(y.d.KeyAttribute_android_translationZ, 17);
            a.append(y.d.KeyAttribute_motionProgress, 18);
        }

        public static void a(e e3, TypedArray typedArray) {
            int n3 = typedArray.getIndexCount();
            block20: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = typedArray.getIndex(i3);
                switch (a.get(n4)) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unused attribute 0x");
                        stringBuilder.append(Integer.toHexString(n4));
                        stringBuilder.append("   ");
                        stringBuilder.append(a.get(n4));
                        Log.e((String)"KeyAttribute", (String)stringBuilder.toString());
                        continue block20;
                    }
                    case 20: {
                        x.e.P(e3, typedArray.getDimension(n4, e3.p));
                        continue block20;
                    }
                    case 19: {
                        x.e.N(e3, typedArray.getDimension(n4, e3.o));
                        continue block20;
                    }
                    case 18: {
                        x.e.B(e3, typedArray.getFloat(n4, e3.w));
                        continue block20;
                    }
                    case 17: {
                        x.e.z(e3, typedArray.getDimension(n4, e3.v));
                        continue block20;
                    }
                    case 16: {
                        x.e.x(e3, typedArray.getDimension(n4, e3.u));
                        continue block20;
                    }
                    case 15: {
                        x.e.v(e3, typedArray.getDimension(n4, e3.t));
                        continue block20;
                    }
                    case 14: {
                        x.e.q(e3, typedArray.getFloat(n4, e3.s));
                        continue block20;
                    }
                    case 13: {
                        x.e.F(e3, typedArray.getInteger(n4, e3.h));
                        continue block20;
                    }
                    case 12: {
                        e3.a = typedArray.getInt(n4, e3.a);
                        continue block20;
                    }
                    case 10: {
                        if (MotionLayout.f1) {
                            int n5;
                            e3.b = n5 = typedArray.getResourceId(n4, e3.b);
                            if (n5 != -1) continue block20;
                            e3.c = typedArray.getString(n4);
                            continue block20;
                        }
                        if (typedArray.peekValue((int)n4).type == 3) {
                            e3.c = typedArray.getString(n4);
                            continue block20;
                        }
                        e3.b = typedArray.getResourceId(n4, e3.b);
                        continue block20;
                    }
                    case 9: {
                        x.e.Q(e3, typedArray.getString(n4));
                        continue block20;
                    }
                    case 8: {
                        x.e.t(e3, typedArray.getFloat(n4, e3.q));
                        continue block20;
                    }
                    case 7: {
                        x.e.H(e3, typedArray.getFloat(n4, e3.r));
                        continue block20;
                    }
                    case 6: {
                        x.e.L(e3, typedArray.getFloat(n4, e3.n));
                        continue block20;
                    }
                    case 5: {
                        x.e.J(e3, typedArray.getFloat(n4, e3.m));
                        continue block20;
                    }
                    case 4: {
                        x.e.D(e3, typedArray.getFloat(n4, e3.l));
                        continue block20;
                    }
                    case 2: {
                        x.e.r(e3, typedArray.getDimension(n4, e3.k));
                        continue block20;
                    }
                    case 1: {
                        x.e.n(e3, typedArray.getFloat(n4, e3.j));
                    }
                }
            }
        }
    }
}

