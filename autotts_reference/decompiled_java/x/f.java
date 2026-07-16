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
import androidx.constraintlayout.widget.a;
import java.util.HashMap;
import java.util.HashSet;
import s.e;
import s.j;
import w.c;
import x.d;

public class f
extends d {
    public String g = null;
    public int h = 0;
    public int i = -1;
    public String j = null;
    public float k = Float.NaN;
    public float l = 0.0f;
    public float m = 0.0f;
    public float n = Float.NaN;
    public int o = -1;
    public float p = Float.NaN;
    public float q = Float.NaN;
    public float r = Float.NaN;
    public float s = Float.NaN;
    public float t = Float.NaN;
    public float u = Float.NaN;
    public float v = Float.NaN;
    public float w = Float.NaN;
    public float x = Float.NaN;
    public float y = Float.NaN;
    public float z = Float.NaN;

    public f() {
        this.d = 4;
        this.e = new HashMap();
    }

    public static /* synthetic */ float A(f f3, float f4) {
        f3.x = f4;
        return f4;
    }

    public static /* synthetic */ float C(f f3, float f4) {
        f3.y = f4;
        return f4;
    }

    public static /* synthetic */ float E(f f3, float f4) {
        f3.z = f4;
        return f4;
    }

    public static /* synthetic */ float G(f f3, float f4) {
        f3.n = f4;
        return f4;
    }

    public static /* synthetic */ float J(f f3, float f4) {
        f3.m = f4;
        return f4;
    }

    public static /* synthetic */ int K(f f3, int n3) {
        f3.h = n3;
        return n3;
    }

    public static /* synthetic */ String L(f f3, String string) {
        f3.j = string;
        return string;
    }

    public static /* synthetic */ int N(f f3, int n3) {
        f3.i = n3;
        return n3;
    }

    public static /* synthetic */ float P(f f3, float f4) {
        f3.k = f4;
        return f4;
    }

    public static /* synthetic */ float R(f f3, float f4) {
        f3.l = f4;
        return f4;
    }

    public static /* synthetic */ int T(f f3, int n3) {
        f3.o = n3;
        return n3;
    }

    public static /* synthetic */ float V(f f3, float f4) {
        f3.p = f4;
        return f4;
    }

    public static /* synthetic */ float X(f f3, float f4) {
        f3.q = f4;
        return f4;
    }

    public static /* synthetic */ float n(f f3, float f4) {
        f3.r = f4;
        return f4;
    }

    public static /* synthetic */ String o(f f3, String string) {
        f3.g = string;
        return string;
    }

    public static /* synthetic */ float q(f f3, float f4) {
        f3.t = f4;
        return f4;
    }

    public static /* synthetic */ float s(f f3, float f4) {
        f3.u = f4;
        return f4;
    }

    public static /* synthetic */ float u(f f3, float f4) {
        f3.s = f4;
        return f4;
    }

    public static /* synthetic */ float w(f f3, float f4) {
        f3.v = f4;
        return f4;
    }

    public static /* synthetic */ float y(f f3, float f4) {
        f3.w = f4;
        return f4;
    }

    public void Y(HashMap hashMap) {
        for (Object object : hashMap.keySet()) {
            if (((String)object).startsWith("CUSTOM")) {
                Object object2 = ((String)object).substring(7);
                if ((object2 = (androidx.constraintlayout.widget.a)this.e.get(object2)) == null || ((androidx.constraintlayout.widget.a)object2).d() != a.a.d || (object = (c)hashMap.get(object)) == null) continue;
                ((e)object).e(this.a, this.i, this.j, this.o, this.k, this.l, this.m, ((androidx.constraintlayout.widget.a)object2).e(), object2);
                continue;
            }
            float f3 = this.Z((String)object);
            if (Float.isNaN(f3) || (object = (c)hashMap.get(object)) == null) continue;
            ((e)object).d(this.a, this.i, this.j, this.o, this.k, this.l, this.m, f3);
        }
    }

    public float Z(String string) {
        string.getClass();
        int n3 = string.hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 1530034690: {
                if (!string.equals("wavePhase")) break;
                n4 = 13;
                break;
            }
            case 156108012: {
                if (!string.equals("waveOffset")) break;
                n4 = 12;
                break;
            }
            case 92909918: {
                if (!string.equals("alpha")) break;
                n4 = 11;
                break;
            }
            case 37232917: {
                if (!string.equals("transitionPathRotate")) break;
                n4 = 10;
                break;
            }
            case -4379043: {
                if (!string.equals("elevation")) break;
                n4 = 9;
                break;
            }
            case -40300674: {
                if (!string.equals("rotation")) break;
                n4 = 8;
                break;
            }
            case -908189617: {
                if (!string.equals("scaleY")) break;
                n4 = 7;
                break;
            }
            case -908189618: {
                if (!string.equals("scaleX")) break;
                n4 = 6;
                break;
            }
            case -1001078227: {
                if (!string.equals("progress")) break;
                n4 = 5;
                break;
            }
            case -1225497655: {
                if (!string.equals("translationZ")) break;
                n4 = 4;
                break;
            }
            case -1225497656: {
                if (!string.equals("translationY")) break;
                n4 = 3;
                break;
            }
            case -1225497657: {
                if (!string.equals("translationX")) break;
                n4 = 2;
                break;
            }
            case -1249320805: {
                if (!string.equals("rotationY")) break;
                n4 = 1;
                break;
            }
            case -1249320806: {
                if (!string.equals("rotationX")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                string.startsWith("CUSTOM");
                return Float.NaN;
            }
            case 13: {
                return this.m;
            }
            case 12: {
                return this.l;
            }
            case 11: {
                return this.p;
            }
            case 10: {
                return this.s;
            }
            case 9: {
                return this.q;
            }
            case 8: {
                return this.r;
            }
            case 7: {
                return this.w;
            }
            case 6: {
                return this.v;
            }
            case 5: {
                return this.n;
            }
            case 4: {
                return this.z;
            }
            case 3: {
                return this.y;
            }
            case 2: {
                return this.x;
            }
            case 1: {
                return this.u;
            }
            case 0: 
        }
        return this.t;
    }

    @Override
    public void a(HashMap hashMap) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("add ");
        ((StringBuilder)object).append(hashMap.size());
        ((StringBuilder)object).append(" values");
        x.a.g("KeyCycle", ((StringBuilder)object).toString(), 2);
        block32: for (String string : hashMap.keySet()) {
            object = (j)hashMap.get(string);
            if (object == null) continue;
            string.getClass();
            int n3 = string.hashCode();
            int n4 = -1;
            switch (n3) {
                default: {
                    break;
                }
                case 1530034690: {
                    if (!string.equals("wavePhase")) break;
                    n4 = 13;
                    break;
                }
                case 156108012: {
                    if (!string.equals("waveOffset")) break;
                    n4 = 12;
                    break;
                }
                case 92909918: {
                    if (!string.equals("alpha")) break;
                    n4 = 11;
                    break;
                }
                case 37232917: {
                    if (!string.equals("transitionPathRotate")) break;
                    n4 = 10;
                    break;
                }
                case -4379043: {
                    if (!string.equals("elevation")) break;
                    n4 = 9;
                    break;
                }
                case -40300674: {
                    if (!string.equals("rotation")) break;
                    n4 = 8;
                    break;
                }
                case -908189617: {
                    if (!string.equals("scaleY")) break;
                    n4 = 7;
                    break;
                }
                case -908189618: {
                    if (!string.equals("scaleX")) break;
                    n4 = 6;
                    break;
                }
                case -1001078227: {
                    if (!string.equals("progress")) break;
                    n4 = 5;
                    break;
                }
                case -1225497655: {
                    if (!string.equals("translationZ")) break;
                    n4 = 4;
                    break;
                }
                case -1225497656: {
                    if (!string.equals("translationY")) break;
                    n4 = 3;
                    break;
                }
                case -1225497657: {
                    if (!string.equals("translationX")) break;
                    n4 = 2;
                    break;
                }
                case -1249320805: {
                    if (!string.equals("rotationY")) break;
                    n4 = 1;
                    break;
                }
                case -1249320806: {
                    if (!string.equals("rotationX")) break;
                    n4 = 0;
                }
            }
            switch (n4) {
                default: {
                    string.startsWith("CUSTOM");
                    continue block32;
                }
                case 13: {
                    ((j)object).c(this.a, this.m);
                    continue block32;
                }
                case 12: {
                    ((j)object).c(this.a, this.l);
                    continue block32;
                }
                case 11: {
                    ((j)object).c(this.a, this.p);
                    continue block32;
                }
                case 10: {
                    ((j)object).c(this.a, this.s);
                    continue block32;
                }
                case 9: {
                    ((j)object).c(this.a, this.q);
                    continue block32;
                }
                case 8: {
                    ((j)object).c(this.a, this.r);
                    continue block32;
                }
                case 7: {
                    ((j)object).c(this.a, this.w);
                    continue block32;
                }
                case 6: {
                    ((j)object).c(this.a, this.v);
                    continue block32;
                }
                case 5: {
                    ((j)object).c(this.a, this.n);
                    continue block32;
                }
                case 4: {
                    ((j)object).c(this.a, this.z);
                    continue block32;
                }
                case 3: {
                    ((j)object).c(this.a, this.y);
                    continue block32;
                }
                case 2: {
                    ((j)object).c(this.a, this.x);
                    continue block32;
                }
                case 1: {
                    ((j)object).c(this.a, this.u);
                    continue block32;
                }
                case 0: 
            }
            ((j)object).c(this.a, this.t);
        }
    }

    @Override
    public d b() {
        return new f().c(this);
    }

    @Override
    public d c(d d3) {
        super.c(d3);
        d3 = (f)d3;
        this.g = ((f)d3).g;
        this.h = ((f)d3).h;
        this.i = ((f)d3).i;
        this.j = ((f)d3).j;
        this.k = ((f)d3).k;
        this.l = ((f)d3).l;
        this.m = ((f)d3).m;
        this.n = ((f)d3).n;
        this.o = ((f)d3).o;
        this.p = ((f)d3).p;
        this.q = ((f)d3).q;
        this.r = ((f)d3).r;
        this.s = ((f)d3).s;
        this.t = ((f)d3).t;
        this.u = ((f)d3).u;
        this.v = ((f)d3).v;
        this.w = ((f)d3).w;
        this.x = ((f)d3).x;
        this.y = ((f)d3).y;
        this.z = ((f)d3).z;
        return this;
    }

    @Override
    public void d(HashSet hashSet) {
        if (!Float.isNaN(this.p)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.q)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.r)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.t)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.u)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.v)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.w)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.s)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.x)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.y)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.z)) {
            hashSet.add("translationZ");
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
        x.f$a.b(this, context.obtainStyledAttributes(attributeSet, y.d.KeyCycle));
    }

    public static abstract class a {
        public static SparseIntArray a;

        static {
            SparseIntArray sparseIntArray;
            a = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.KeyCycle_motionTarget, 1);
            a.append(y.d.KeyCycle_framePosition, 2);
            a.append(y.d.KeyCycle_transitionEasing, 3);
            a.append(y.d.KeyCycle_curveFit, 4);
            a.append(y.d.KeyCycle_waveShape, 5);
            a.append(y.d.KeyCycle_wavePeriod, 6);
            a.append(y.d.KeyCycle_waveOffset, 7);
            a.append(y.d.KeyCycle_waveVariesBy, 8);
            a.append(y.d.KeyCycle_android_alpha, 9);
            a.append(y.d.KeyCycle_android_elevation, 10);
            a.append(y.d.KeyCycle_android_rotation, 11);
            a.append(y.d.KeyCycle_android_rotationX, 12);
            a.append(y.d.KeyCycle_android_rotationY, 13);
            a.append(y.d.KeyCycle_transitionPathRotate, 14);
            a.append(y.d.KeyCycle_android_scaleX, 15);
            a.append(y.d.KeyCycle_android_scaleY, 16);
            a.append(y.d.KeyCycle_android_translationX, 17);
            a.append(y.d.KeyCycle_android_translationY, 18);
            a.append(y.d.KeyCycle_android_translationZ, 19);
            a.append(y.d.KeyCycle_motionProgress, 20);
            a.append(y.d.KeyCycle_wavePhase, 21);
        }

        public static void b(f f3, TypedArray typedArray) {
            int n3 = typedArray.getIndexCount();
            block23: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = typedArray.getIndex(i3);
                switch (a.get(n4)) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unused attribute 0x");
                        stringBuilder.append(Integer.toHexString(n4));
                        stringBuilder.append("   ");
                        stringBuilder.append(a.get(n4));
                        Log.e((String)"KeyCycle", (String)stringBuilder.toString());
                        continue block23;
                    }
                    case 21: {
                        x.f.J(f3, typedArray.getFloat(n4, f3.m) / 360.0f);
                        continue block23;
                    }
                    case 20: {
                        x.f.G(f3, typedArray.getFloat(n4, f3.n));
                        continue block23;
                    }
                    case 19: {
                        x.f.E(f3, typedArray.getDimension(n4, f3.z));
                        continue block23;
                    }
                    case 18: {
                        x.f.C(f3, typedArray.getDimension(n4, f3.y));
                        continue block23;
                    }
                    case 17: {
                        x.f.A(f3, typedArray.getDimension(n4, f3.x));
                        continue block23;
                    }
                    case 16: {
                        x.f.y(f3, typedArray.getFloat(n4, f3.w));
                        continue block23;
                    }
                    case 15: {
                        x.f.w(f3, typedArray.getFloat(n4, f3.v));
                        continue block23;
                    }
                    case 14: {
                        x.f.u(f3, typedArray.getFloat(n4, f3.s));
                        continue block23;
                    }
                    case 13: {
                        x.f.s(f3, typedArray.getFloat(n4, f3.u));
                        continue block23;
                    }
                    case 12: {
                        x.f.q(f3, typedArray.getFloat(n4, f3.t));
                        continue block23;
                    }
                    case 11: {
                        x.f.n(f3, typedArray.getFloat(n4, f3.r));
                        continue block23;
                    }
                    case 10: {
                        x.f.X(f3, typedArray.getDimension(n4, f3.q));
                        continue block23;
                    }
                    case 9: {
                        x.f.V(f3, typedArray.getFloat(n4, f3.p));
                        continue block23;
                    }
                    case 8: {
                        x.f.T(f3, typedArray.getInt(n4, f3.o));
                        continue block23;
                    }
                    case 7: {
                        if (typedArray.peekValue((int)n4).type == 5) {
                            x.f.R(f3, typedArray.getDimension(n4, f3.l));
                            continue block23;
                        }
                        x.f.R(f3, typedArray.getFloat(n4, f3.l));
                        continue block23;
                    }
                    case 6: {
                        x.f.P(f3, typedArray.getFloat(n4, f3.k));
                        continue block23;
                    }
                    case 5: {
                        if (typedArray.peekValue((int)n4).type == 3) {
                            x.f.L(f3, typedArray.getString(n4));
                            x.f.N(f3, 7);
                            continue block23;
                        }
                        x.f.N(f3, typedArray.getInt(n4, f3.i));
                        continue block23;
                    }
                    case 4: {
                        x.f.K(f3, typedArray.getInteger(n4, f3.h));
                        continue block23;
                    }
                    case 3: {
                        x.f.o(f3, typedArray.getString(n4));
                        continue block23;
                    }
                    case 2: {
                        f3.a = typedArray.getInt(n4, f3.a);
                        continue block23;
                    }
                    case 1: {
                        if (MotionLayout.f1) {
                            int n5;
                            f3.b = n5 = typedArray.getResourceId(n4, f3.b);
                            if (n5 != -1) continue block23;
                            f3.c = typedArray.getString(n4);
                            continue block23;
                        }
                        if (typedArray.peekValue((int)n4).type == 3) {
                            f3.c = typedArray.getString(n4);
                            continue block23;
                        }
                        f3.b = typedArray.getResourceId(n4, f3.b);
                    }
                }
            }
        }
    }
}

