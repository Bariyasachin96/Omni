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
import s.c;
import x.d;
import x.i;

public class h
extends i {
    public String h = null;
    public int i = x.d.f;
    public int j = 0;
    public float k = Float.NaN;
    public float l = Float.NaN;
    public float m = Float.NaN;
    public float n = Float.NaN;
    public float o = Float.NaN;
    public float p = Float.NaN;
    public int q = 0;
    public float r = Float.NaN;
    public float s = Float.NaN;

    public h() {
        this.d = 2;
    }

    @Override
    public void a(HashMap hashMap) {
    }

    @Override
    public d b() {
        return new h().c(this);
    }

    @Override
    public d c(d d3) {
        super.c(d3);
        d3 = (h)d3;
        this.h = ((h)d3).h;
        this.i = ((h)d3).i;
        this.j = ((h)d3).j;
        this.k = ((h)d3).k;
        this.l = Float.NaN;
        this.m = ((h)d3).m;
        this.n = ((h)d3).n;
        this.o = ((h)d3).o;
        this.p = ((h)d3).p;
        this.r = ((h)d3).r;
        this.s = ((h)d3).s;
        return this;
    }

    @Override
    public void e(Context context, AttributeSet attributeSet) {
        x.h$a.b(this, context.obtainStyledAttributes(attributeSet, y.d.KeyPosition));
    }

    public void m(int n3) {
        this.q = n3;
    }

    public void n(String string, Object object) {
        string.getClass();
        int n3 = string.hashCode();
        int n4 = -1;
        switch (n3) {
            default: {
                break;
            }
            case 428090548: {
                if (!string.equals("percentY")) break;
                n4 = 6;
                break;
            }
            case 428090547: {
                if (!string.equals("percentX")) break;
                n4 = 5;
                break;
            }
            case -200259324: {
                if (!string.equals("sizePercent")) break;
                n4 = 4;
                break;
            }
            case -827014263: {
                if (!string.equals("drawPath")) break;
                n4 = 3;
                break;
            }
            case -1017587252: {
                if (!string.equals("percentHeight")) break;
                n4 = 2;
                break;
            }
            case -1127236479: {
                if (!string.equals("percentWidth")) break;
                n4 = 1;
                break;
            }
            case -1812823328: {
                if (!string.equals("transitionEasing")) break;
                n4 = 0;
            }
        }
        switch (n4) {
            default: {
                return;
            }
            case 6: {
                this.n = this.k(object);
                return;
            }
            case 5: {
                this.m = this.k(object);
                return;
            }
            case 4: {
                float f3;
                this.k = f3 = this.k(object);
                this.l = f3;
                return;
            }
            case 3: {
                this.j = this.l(object);
                return;
            }
            case 2: {
                this.l = this.k(object);
                return;
            }
            case 1: {
                this.k = this.k(object);
                return;
            }
            case 0: 
        }
        this.h = object.toString();
    }

    public static abstract class a {
        public static SparseIntArray a;

        static {
            SparseIntArray sparseIntArray;
            a = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(y.d.KeyPosition_motionTarget, 1);
            a.append(y.d.KeyPosition_framePosition, 2);
            a.append(y.d.KeyPosition_transitionEasing, 3);
            a.append(y.d.KeyPosition_curveFit, 4);
            a.append(y.d.KeyPosition_drawPath, 5);
            a.append(y.d.KeyPosition_percentX, 6);
            a.append(y.d.KeyPosition_percentY, 7);
            a.append(y.d.KeyPosition_keyPositionType, 9);
            a.append(y.d.KeyPosition_sizePercent, 8);
            a.append(y.d.KeyPosition_percentWidth, 11);
            a.append(y.d.KeyPosition_percentHeight, 12);
            a.append(y.d.KeyPosition_pathMotionArc, 10);
        }

        public static void b(h h3, TypedArray typedArray) {
            int n3 = typedArray.getIndexCount();
            block14: for (int i3 = 0; i3 < n3; ++i3) {
                int n4 = typedArray.getIndex(i3);
                switch (a.get(n4)) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unused attribute 0x");
                        stringBuilder.append(Integer.toHexString(n4));
                        stringBuilder.append("   ");
                        stringBuilder.append(a.get(n4));
                        Log.e((String)"KeyPosition", (String)stringBuilder.toString());
                        continue block14;
                    }
                    case 12: {
                        h3.l = typedArray.getFloat(n4, h3.l);
                        continue block14;
                    }
                    case 11: {
                        h3.k = typedArray.getFloat(n4, h3.k);
                        continue block14;
                    }
                    case 10: {
                        h3.i = typedArray.getInt(n4, h3.i);
                        continue block14;
                    }
                    case 9: {
                        h3.q = typedArray.getInt(n4, h3.q);
                        continue block14;
                    }
                    case 8: {
                        float f3;
                        h3.k = f3 = typedArray.getFloat(n4, h3.l);
                        h3.l = f3;
                        continue block14;
                    }
                    case 7: {
                        h3.n = typedArray.getFloat(n4, h3.n);
                        continue block14;
                    }
                    case 6: {
                        h3.m = typedArray.getFloat(n4, h3.m);
                        continue block14;
                    }
                    case 5: {
                        h3.j = typedArray.getInt(n4, h3.j);
                        continue block14;
                    }
                    case 4: {
                        h3.g = typedArray.getInteger(n4, h3.g);
                        continue block14;
                    }
                    case 3: {
                        if (typedArray.peekValue((int)n4).type == 3) {
                            h3.h = typedArray.getString(n4);
                            continue block14;
                        }
                        h3.h = s.c.c[typedArray.getInteger(n4, 0)];
                        continue block14;
                    }
                    case 2: {
                        h3.a = typedArray.getInt(n4, h3.a);
                        continue block14;
                    }
                    case 1: {
                        if (MotionLayout.f1) {
                            int n5;
                            h3.b = n5 = typedArray.getResourceId(n4, h3.b);
                            if (n5 != -1) continue block14;
                            h3.c = typedArray.getString(n4);
                            continue block14;
                        }
                        if (typedArray.peekValue((int)n4).type == 3) {
                            h3.c = typedArray.getString(n4);
                            continue block14;
                        }
                        h3.b = typedArray.getResourceId(n4, h3.b);
                    }
                }
            }
            if (h3.a == -1) {
                Log.e((String)"KeyPosition", (String)"no frame position");
            }
        }
    }
}

