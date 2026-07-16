/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.util.Log
 *  android.view.View
 */
package x;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.a;
import androidx.constraintlayout.widget.b;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import s.c;
import s.j;
import w.d;

public class l
implements Comparable {
    public static String[] F = new String[]{"position", "x", "y", "width", "height", "pathRotate"};
    public float A;
    public float B;
    public float C;
    public float D;
    public int E = -1;
    public float c = 0.0f;
    public int d = 0;
    public int e;
    public LinkedHashMap f = new LinkedHashMap();
    public int g = 0;
    public double[] h = new double[18];
    public double[] i = new double[18];
    public float j = 1.0f;
    public boolean k = false;
    public float l = 0.0f;
    public float m = 0.0f;
    public float n = 0.0f;
    public float o = 1.0f;
    public float p = 1.0f;
    public float q = Float.NaN;
    public float r = Float.NaN;
    public float s = 0.0f;
    public float t = 0.0f;
    public float u = 0.0f;
    public c v;
    public int w = 0;
    public float x;
    public float y;
    public float z;

    public l() {
        this.C = Float.NaN;
        this.D = Float.NaN;
    }

    public void a(HashMap hashMap, int n3) {
        block32: for (String string : hashMap.keySet()) {
            Object object = (d)hashMap.get(string);
            if (object == null) continue;
            string.getClass();
            int n4 = string.hashCode();
            int n5 = -1;
            switch (n4) {
                default: {
                    break;
                }
                case 92909918: {
                    if (!string.equals("alpha")) break;
                    n5 = 13;
                    break;
                }
                case 37232917: {
                    if (!string.equals("transitionPathRotate")) break;
                    n5 = 12;
                    break;
                }
                case -4379043: {
                    if (!string.equals("elevation")) break;
                    n5 = 11;
                    break;
                }
                case -40300674: {
                    if (!string.equals("rotation")) break;
                    n5 = 10;
                    break;
                }
                case -760884509: {
                    if (!string.equals("transformPivotY")) break;
                    n5 = 9;
                    break;
                }
                case -760884510: {
                    if (!string.equals("transformPivotX")) break;
                    n5 = 8;
                    break;
                }
                case -908189617: {
                    if (!string.equals("scaleY")) break;
                    n5 = 7;
                    break;
                }
                case -908189618: {
                    if (!string.equals("scaleX")) break;
                    n5 = 6;
                    break;
                }
                case -1001078227: {
                    if (!string.equals("progress")) break;
                    n5 = 5;
                    break;
                }
                case -1225497655: {
                    if (!string.equals("translationZ")) break;
                    n5 = 4;
                    break;
                }
                case -1225497656: {
                    if (!string.equals("translationY")) break;
                    n5 = 3;
                    break;
                }
                case -1225497657: {
                    if (!string.equals("translationX")) break;
                    n5 = 2;
                    break;
                }
                case -1249320805: {
                    if (!string.equals("rotationY")) break;
                    n5 = 1;
                    break;
                }
                case -1249320806: {
                    if (!string.equals("rotationX")) break;
                    n5 = 0;
                }
            }
            float f3 = 1.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            float f9 = 0.0f;
            float f10 = 0.0f;
            float f11 = 0.0f;
            float f12 = 0.0f;
            float f13 = 0.0f;
            float f14 = 0.0f;
            switch (n5) {
                default: {
                    if (string.startsWith("CUSTOM")) {
                        CharSequence charSequence = string.split(",")[1];
                        if (!((AbstractMap)this.f).containsKey(charSequence)) continue block32;
                        a a4 = (a)this.f.get(charSequence);
                        if (object instanceof d.b) {
                            ((d.b)object).i(n3, a4);
                            continue block32;
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(string);
                        ((StringBuilder)charSequence).append(" ViewSpline not a CustomSet frame = ");
                        ((StringBuilder)charSequence).append(n3);
                        ((StringBuilder)charSequence).append(", value");
                        ((StringBuilder)charSequence).append(a4.e());
                        ((StringBuilder)charSequence).append(object);
                        Log.e((String)"MotionPaths", (String)((StringBuilder)charSequence).toString());
                        continue block32;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("UNKNOWN spline ");
                    ((StringBuilder)object).append(string);
                    Log.e((String)"MotionPaths", (String)((StringBuilder)object).toString());
                    continue block32;
                }
                case 13: {
                    if (!Float.isNaN(this.j)) {
                        f3 = this.j;
                    }
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 12: {
                    f3 = Float.isNaN(this.C) ? f14 : this.C;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 11: {
                    f3 = Float.isNaN(this.l) ? f4 : this.l;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 10: {
                    f3 = Float.isNaN(this.m) ? f5 : this.m;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 9: {
                    f3 = Float.isNaN(this.r) ? f6 : this.r;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 8: {
                    f3 = Float.isNaN(this.q) ? f7 : this.q;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 7: {
                    if (!Float.isNaN(this.p)) {
                        f3 = this.p;
                    }
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 6: {
                    if (!Float.isNaN(this.o)) {
                        f3 = this.o;
                    }
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 5: {
                    f3 = Float.isNaN(this.D) ? f8 : this.D;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 4: {
                    f3 = Float.isNaN(this.u) ? f9 : this.u;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 3: {
                    f3 = Float.isNaN(this.t) ? f10 : this.t;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 2: {
                    f3 = Float.isNaN(this.s) ? f11 : this.s;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 1: {
                    f3 = Float.isNaN(this.c) ? f12 : this.c;
                    ((j)object).c(n3, f3);
                    continue block32;
                }
                case 0: 
            }
            f3 = Float.isNaN(this.n) ? f13 : this.n;
            ((j)object).c(n3, f3);
        }
    }

    public void b(View view) {
        this.e = view.getVisibility();
        float f3 = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.j = f3;
        this.k = false;
        this.l = view.getElevation();
        this.m = view.getRotation();
        this.n = view.getRotationX();
        this.c = view.getRotationY();
        this.o = view.getScaleX();
        this.p = view.getScaleY();
        this.q = view.getPivotX();
        this.r = view.getPivotY();
        this.s = view.getTranslationX();
        this.t = view.getTranslationY();
        this.u = view.getTranslationZ();
    }

    public void c(b.a a4) {
        int n3;
        int n4;
        Object object2 = a4.c;
        this.d = n4 = ((b.d)object2).c;
        this.e = n3 = ((b.d)object2).b;
        float f3 = n3 != 0 && n4 == 0 ? 0.0f : ((b.d)object2).d;
        this.j = f3;
        object2 = a4.f;
        this.k = ((b.e)object2).m;
        this.l = ((b.e)object2).n;
        this.m = ((b.e)object2).b;
        this.n = ((b.e)object2).c;
        this.c = ((b.e)object2).d;
        this.o = ((b.e)object2).e;
        this.p = ((b.e)object2).f;
        this.q = ((b.e)object2).g;
        this.r = ((b.e)object2).h;
        this.s = ((b.e)object2).j;
        this.t = ((b.e)object2).k;
        this.u = ((b.e)object2).l;
        this.v = s.c.c(a4.d.d);
        object2 = a4.d;
        this.C = ((b.c)object2).i;
        this.w = ((b.c)object2).f;
        this.E = ((b.c)object2).b;
        this.D = a4.c.e;
        for (Object object2 : a4.g.keySet()) {
            a a5 = (a)a4.g.get(object2);
            if (!a5.g()) continue;
            ((AbstractMap)this.f).put(object2, a5);
        }
    }

    public int d(l l3) {
        return Float.compare(this.x, l3.x);
    }

    public final boolean e(float f3, float f4) {
        if (!Float.isNaN(f3) && !Float.isNaN(f4)) {
            return Math.abs(f3 - f4) > 1.0E-6f;
        }
        return Float.isNaN(f3) != Float.isNaN(f4);
    }

    public void f(l l3, HashSet hashSet) {
        int n3;
        int n4;
        if (this.e(this.j, l3.j)) {
            hashSet.add("alpha");
        }
        if (this.e(this.l, l3.l)) {
            hashSet.add("elevation");
        }
        if ((n4 = this.e) != (n3 = l3.e) && this.d == 0 && (n4 == 0 || n3 == 0)) {
            hashSet.add("alpha");
        }
        if (this.e(this.m, l3.m)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.C) || !Float.isNaN(l3.C)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.D) || !Float.isNaN(l3.D)) {
            hashSet.add("progress");
        }
        if (this.e(this.n, l3.n)) {
            hashSet.add("rotationX");
        }
        if (this.e(this.c, l3.c)) {
            hashSet.add("rotationY");
        }
        if (this.e(this.q, l3.q)) {
            hashSet.add("transformPivotX");
        }
        if (this.e(this.r, l3.r)) {
            hashSet.add("transformPivotY");
        }
        if (this.e(this.o, l3.o)) {
            hashSet.add("scaleX");
        }
        if (this.e(this.p, l3.p)) {
            hashSet.add("scaleY");
        }
        if (this.e(this.s, l3.s)) {
            hashSet.add("translationX");
        }
        if (this.e(this.t, l3.t)) {
            hashSet.add("translationY");
        }
        if (this.e(this.u, l3.u)) {
            hashSet.add("translationZ");
        }
    }

    public void g(float f3, float f4, float f5, float f6) {
        this.y = f3;
        this.z = f4;
        this.A = f5;
        this.B = f6;
    }

    public void h(Rect rect, b b3, int n3, int n4) {
        block4: {
            block6: {
                float f3;
                block5: {
                    this.g(rect.left, rect.top, rect.width(), rect.height());
                    this.c(b3.y(n4));
                    if (n3 == 1) break block4;
                    if (n3 == 2) break block5;
                    if (n3 == 3) break block4;
                    if (n3 != 4) break block6;
                }
                this.m = f3 = this.m + 90.0f;
                if (f3 > 180.0f) {
                    this.m = f3 - 360.0f;
                }
            }
            return;
        }
        this.m -= 90.0f;
    }

    public void i(View view) {
        this.g(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.b(view);
    }
}

