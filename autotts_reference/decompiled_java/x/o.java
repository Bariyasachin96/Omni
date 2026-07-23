/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package x;

import android.view.View;
import androidx.constraintlayout.widget.a;
import androidx.constraintlayout.widget.b;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import x.c;
import x.d;
import x.h;
import x.m;

public class o
implements Comparable {
    public static String[] v = new String[]{"position", "x", "y", "width", "height", "pathRotate"};
    public s.c c;
    public int d = 0;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k = Float.NaN;
    public float l = Float.NaN;
    public int m;
    public int n;
    public float o;
    public m p;
    public LinkedHashMap q;
    public int r;
    public int s;
    public double[] t;
    public double[] u;

    public o() {
        int n3;
        this.m = n3 = x.d.f;
        this.n = n3;
        this.o = Float.NaN;
        this.p = null;
        this.q = new LinkedHashMap();
        this.r = 0;
        this.t = new double[18];
        this.u = new double[18];
    }

    public o(int n3, int n4, h h3, o o3, o o4) {
        int n5;
        this.m = n5 = x.d.f;
        this.n = n5;
        this.o = Float.NaN;
        this.p = null;
        this.q = new LinkedHashMap();
        this.r = 0;
        this.t = new double[18];
        this.u = new double[18];
        if (o3.n != x.d.f) {
            this.o(n3, n4, h3, o3, o4);
            return;
        }
        n5 = h3.q;
        if (n5 != 1) {
            if (n5 != 2) {
                if (n5 != 3) {
                    this.m(h3, o3, o4);
                    return;
                }
                this.l(h3, o3, o4);
                return;
            }
            this.p(n3, n4, h3, o3, o4);
            return;
        }
        this.n(h3, o3, o4);
    }

    public void a(b.a a4) {
        this.c = s.c.c(a4.d.d);
        Object object2 = a4.d;
        this.m = ((b.c)object2).e;
        this.n = ((b.c)object2).b;
        this.k = ((b.c)object2).i;
        this.d = ((b.c)object2).f;
        this.s = ((b.c)object2).c;
        this.l = a4.c.e;
        this.o = a4.e.D;
        for (Object object2 : a4.g.keySet()) {
            a a5 = (a)a4.g.get(object2);
            if (a5 == null || !a5.g()) continue;
            ((AbstractMap)this.q).put(object2, a5);
        }
    }

    public int b(o o3) {
        return Float.compare(this.f, o3.f);
    }

    public final boolean c(float f3, float f4) {
        if (!Float.isNaN(f3) && !Float.isNaN(f4)) {
            return Math.abs(f3 - f4) > 1.0E-6f;
        }
        return Float.isNaN(f3) != Float.isNaN(f4);
    }

    public void d(o o3, boolean[] blArray, String[] stringArray, boolean bl) {
        boolean bl2 = this.c(this.g, o3.g);
        boolean bl3 = this.c(this.h, o3.h);
        blArray[0] = blArray[0] | this.c(this.f, o3.f);
        boolean bl4 = blArray[1];
        boolean bl5 = bl2 | bl3 | bl;
        blArray[1] = bl4 | bl5;
        blArray[2] = bl5 | blArray[2];
        blArray[3] = blArray[3] | this.c(this.i, o3.i);
        bl = blArray[4];
        blArray[4] = this.c(this.j, o3.j) | bl;
    }

    public void e(double[] dArray, int[] nArray) {
        float f3 = this.f;
        float f4 = this.g;
        float f5 = this.h;
        float f6 = this.i;
        float f7 = this.j;
        float f8 = this.k;
        int n3 = 0;
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            int n4 = nArray[i3];
            int n5 = n3;
            if (n4 < 6) {
                dArray[n3] = (new float[]{f3, f4, f5, f6, f7, f8})[n4];
                n5 = n3 + 1;
            }
            n3 = n5;
        }
    }

    public void f(double d3, int[] objectArray, double[] object, float[] fArray, int n3) {
        float f3;
        float f4 = this.g;
        float f5 = this.h;
        float f6 = this.i;
        float f7 = this.j;
        for (int i3 = 0; i3 < objectArray.length; ++i3) {
            f3 = (float)object[i3];
            int n4 = objectArray[i3];
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) continue;
                        f7 = f3;
                        continue;
                    }
                    f6 = f3;
                    continue;
                }
                f5 = f3;
                continue;
            }
            f4 = f3;
        }
        object = this.p;
        float f8 = f4;
        f3 = f5;
        if (object != null) {
            objectArray = new float[2];
            ((m)object).i(d3, (float[])objectArray, new float[2]);
            f8 = objectArray[0];
            f3 = objectArray[1];
            double d4 = f8;
            d3 = f4;
            double d5 = f5;
            f5 = (float)(d4 + Math.sin(d5) * d3 - (double)(f6 / 2.0f));
            f3 = (float)((double)f3 - d3 * Math.cos(d5) - (double)(f7 / 2.0f));
            f8 = f5;
        }
        fArray[n3] = f8 + f6 / 2.0f + 0.0f;
        fArray[n3 + 1] = f3 + f7 / 2.0f + 0.0f;
    }

    public void g(double d3, int[] objectArray, double[] objectArray2, float[] fArray, double[] object, float[] fArray2) {
        float f3;
        float f4;
        float f5 = this.g;
        float f6 = this.h;
        float f7 = this.i;
        float f8 = this.j;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        for (int i3 = 0; i3 < objectArray.length; ++i3) {
            f4 = (float)objectArray2[i3];
            f3 = (float)object[i3];
            int n3 = objectArray[i3];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) continue;
                        f8 = f4;
                        f12 = f3;
                        continue;
                    }
                    f7 = f4;
                    f10 = f3;
                    continue;
                }
                f6 = f4;
                f11 = f3;
                continue;
            }
            f9 = f3;
            f5 = f4;
        }
        f4 = f10 / 2.0f + f9;
        f3 = f12 / 2.0f + f11;
        object = this.p;
        if (object != null) {
            objectArray = new float[2];
            objectArray2 = new float[2];
            ((m)object).i(d3, (float[])objectArray, (float[])objectArray2);
            f10 = objectArray[0];
            f12 = objectArray[1];
            f4 = (float)objectArray2[0];
            f3 = (float)objectArray2[1];
            double d4 = f10;
            double d5 = f5;
            d3 = f6;
            f5 = (float)(d4 + Math.sin(d3) * d5 - (double)(f7 / 2.0f));
            f6 = (float)((double)f12 - Math.cos(d3) * d5 - (double)(f8 / 2.0f));
            d5 = f4;
            double d6 = f9;
            double d7 = Math.sin(d3);
            d4 = Math.cos(d3);
            double d8 = f11;
            f4 = (float)(d5 + d7 * d6 + d4 * d8);
            f3 = (float)((double)f3 - d6 * Math.cos(d3) + Math.sin(d3) * d8);
        }
        fArray[0] = f5 + f7 / 2.0f + 0.0f;
        fArray[1] = f6 + f8 / 2.0f + 0.0f;
        fArray2[0] = f4;
        fArray2[1] = f3;
    }

    public int h(String object, double[] dArray, int n3) {
        object = (a)this.q.get(object);
        int n4 = 0;
        if (object == null) {
            return 0;
        }
        if (((a)object).h() == 1) {
            dArray[n3] = ((a)object).e();
            return 1;
        }
        int n5 = ((a)object).h();
        float[] fArray = new float[n5];
        ((a)object).f(fArray);
        while (n4 < n5) {
            dArray[n3] = fArray[n4];
            ++n4;
            ++n3;
        }
        return n5;
    }

    public int i(String object) {
        if ((object = (a)this.q.get(object)) == null) {
            return 0;
        }
        return ((a)object).h();
    }

    public void j(int[] object, double[] dArray, float[] fArray, int n3) {
        float f3;
        float f4 = this.g;
        float f5 = this.h;
        float f6 = this.i;
        float f7 = this.j;
        for (int i3 = 0; i3 < ((int[])object).length; ++i3) {
            f3 = (float)dArray[i3];
            int n4 = object[i3];
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) continue;
                        f7 = f3;
                        continue;
                    }
                    f6 = f3;
                    continue;
                }
                f5 = f3;
                continue;
            }
            f4 = f3;
        }
        object = this.p;
        float f8 = f4;
        f3 = f5;
        if (object != null) {
            f8 = ((m)object).j();
            f3 = this.p.k();
            double d3 = f8;
            double d4 = f4;
            double d5 = f5;
            f8 = (float)(d3 + Math.sin(d5) * d4 - (double)(f6 / 2.0f));
            f3 = (float)((double)f3 - d4 * Math.cos(d5) - (double)(f7 / 2.0f));
        }
        f5 = f6 + f8;
        f4 = f7 + f3;
        Float.isNaN(Float.NaN);
        Float.isNaN(Float.NaN);
        fArray[n3] = f8 + 0.0f;
        fArray[n3 + 1] = f3 + 0.0f;
        fArray[n3 + 2] = f5 + 0.0f;
        fArray[n3 + 3] = f3 + 0.0f;
        fArray[n3 + 4] = f5 + 0.0f;
        fArray[n3 + 5] = f4 + 0.0f;
        fArray[n3 + 6] = f8 + 0.0f;
        fArray[n3 + 7] = f4 + 0.0f;
    }

    public boolean k(String string) {
        return ((AbstractMap)this.q).containsKey(string);
    }

    public void l(h h3, o o3, o o4) {
        float f3;
        this.e = f3 = (float)h3.a / 100.0f;
        this.d = h3.j;
        float f4 = Float.isNaN(h3.k) ? f3 : h3.k;
        float f5 = Float.isNaN(h3.l) ? f3 : h3.l;
        float f6 = o4.i;
        float f7 = o3.i;
        float f8 = o4.j;
        float f9 = o3.j;
        this.f = this.e;
        float f10 = o3.g;
        float f11 = f7 / 2.0f + f10;
        float f12 = o3.h;
        float f13 = f12 + f9 / 2.0f;
        float f14 = o4.g + f6 / 2.0f;
        float f15 = o4.h + f8 / 2.0f;
        float f16 = f14;
        float f17 = f11;
        if (f11 > f14) {
            f17 = f14;
            f16 = f11;
        }
        if (!(f13 > f15)) {
            f11 = f15;
            f15 = f13;
            f13 = f11;
        }
        f13 -= f15;
        f4 = (f6 - f7) * f4;
        f15 = f4 / 2.0f;
        this.g = (int)(f10 + (f16 -= f17) * f3 - f15);
        f5 = (f8 - f9) * f5;
        f11 = f5 / 2.0f;
        this.h = (int)(f12 + f13 * f3 - f11);
        this.i = (int)(f7 + f4);
        this.j = (int)(f9 + f5);
        f5 = Float.isNaN(h3.m) ? f3 : h3.m;
        boolean bl = Float.isNaN(h3.p);
        f17 = 0.0f;
        f4 = bl ? 0.0f : h3.p;
        if (!Float.isNaN(h3.n)) {
            f3 = h3.n;
        }
        if (!Float.isNaN(h3.o)) {
            f17 = h3.o;
        }
        this.r = 0;
        this.g = (int)(o3.g + f5 * f16 + f17 * f13 - f15);
        this.h = (int)(o3.h + f16 * f4 + f13 * f3 - f11);
        this.c = s.c.c(h3.h);
        this.m = h3.i;
    }

    public void m(h h3, o o3, o o4) {
        float f3;
        this.e = f3 = (float)h3.a / 100.0f;
        this.d = h3.j;
        float f4 = Float.isNaN(h3.k) ? f3 : h3.k;
        float f5 = Float.isNaN(h3.l) ? f3 : h3.l;
        float f6 = o4.i;
        float f7 = o3.i;
        float f8 = o4.j;
        float f9 = o3.j;
        this.f = this.e;
        float f10 = o3.g;
        float f11 = f7 / 2.0f;
        float f12 = o3.h;
        float f13 = f9 / 2.0f;
        float f14 = o4.g;
        float f15 = f6 / 2.0f;
        float f16 = o4.h;
        float f17 = f8 / 2.0f;
        f11 = f14 + f15 - (f11 + f10);
        f13 = f16 + f17 - (f12 + f13);
        f4 = (f6 - f7) * f4;
        f6 = f4 / 2.0f;
        this.g = (int)(f10 + f11 * f3 - f6);
        f5 = (f8 - f9) * f5;
        f10 = f5 / 2.0f;
        this.h = (int)(f12 + f13 * f3 - f10);
        this.i = (int)(f7 + f4);
        this.j = (int)(f9 + f5);
        f4 = Float.isNaN(h3.m) ? f3 : h3.m;
        boolean bl = Float.isNaN(h3.p);
        f7 = 0.0f;
        f5 = bl ? 0.0f : h3.p;
        if (!Float.isNaN(h3.n)) {
            f3 = h3.n;
        }
        if (!Float.isNaN(h3.o)) {
            f7 = h3.o;
        }
        this.r = 0;
        this.g = (int)(o3.g + f4 * f11 + f7 * f13 - f6);
        this.h = (int)(o3.h + f11 * f5 + f13 * f3 - f10);
        this.c = s.c.c(h3.h);
        this.m = h3.i;
    }

    public void n(h h3, o o3, o o4) {
        float f3;
        this.e = f3 = (float)h3.a / 100.0f;
        this.d = h3.j;
        float f4 = Float.isNaN(h3.k) ? f3 : h3.k;
        float f5 = Float.isNaN(h3.l) ? f3 : h3.l;
        float f6 = o4.i;
        float f7 = o3.i;
        float f8 = o4.j;
        float f9 = o3.j;
        this.f = this.e;
        if (!Float.isNaN(h3.m)) {
            f3 = h3.m;
        }
        float f10 = o3.g;
        float f11 = o3.i;
        float f12 = f11 / 2.0f;
        float f13 = o3.h;
        float f14 = o3.j;
        float f15 = f14 / 2.0f;
        float f16 = o4.g;
        float f17 = o4.i / 2.0f;
        float f18 = o4.h;
        float f19 = o4.j / 2.0f;
        f17 = f16 + f17 - (f12 + f10);
        f15 = f18 + f19 - (f15 + f13);
        f18 = f17 * f3;
        f6 = (f6 - f7) * f4;
        f4 = f6 / 2.0f;
        this.g = (int)(f10 + f18 - f4);
        f7 = f15 * f3;
        f3 = (f8 - f9) * f5;
        f5 = f3 / 2.0f;
        this.h = (int)(f13 + f7 - f5);
        this.i = (int)(f11 + f6);
        this.j = (int)(f14 + f3);
        f3 = Float.isNaN(h3.n) ? 0.0f : h3.n;
        f8 = -f15;
        this.r = 1;
        f4 = (int)(o3.g + f18 - f4);
        f5 = (int)(o3.h + f7 - f5);
        this.g = f4 + f8 * f3;
        this.h = f5 + f17 * f3;
        this.n = this.n;
        this.c = s.c.c(h3.h);
        this.m = h3.i;
    }

    public void o(int n3, int n4, h h3, o o3, o o4) {
        float f3;
        this.e = f3 = (float)h3.a / 100.0f;
        this.d = h3.j;
        this.r = h3.q;
        float f4 = Float.isNaN(h3.k) ? f3 : h3.k;
        float f5 = Float.isNaN(h3.l) ? f3 : h3.l;
        float f6 = o4.i;
        float f7 = o3.i;
        float f8 = o4.j;
        float f9 = o3.j;
        this.f = this.e;
        this.i = (int)(f7 + (f6 - f7) * f4);
        this.j = (int)(f9 + (f8 - f9) * f5);
        if (h3.q != 2) {
            f4 = Float.isNaN(h3.m) ? f3 : h3.m;
            f6 = o4.g;
            f5 = o3.g;
            this.g = f4 * (f6 - f5) + f5;
            if (!Float.isNaN(h3.n)) {
                f3 = h3.n;
            }
            f4 = o4.h;
            f5 = o3.h;
            this.h = f3 * (f4 - f5) + f5;
        } else {
            if (Float.isNaN(h3.m)) {
                f5 = o4.g;
                f4 = o3.g;
                f4 = (f5 - f4) * f3 + f4;
            } else {
                f6 = h3.m;
                f4 = Math.min(f5, f4) * f6;
            }
            this.g = f4;
            if (Float.isNaN(h3.n)) {
                f5 = o4.h;
                f4 = o3.h;
                f3 = f3 * (f5 - f4) + f4;
            } else {
                f3 = h3.n;
            }
            this.h = f3;
        }
        this.n = o3.n;
        this.c = s.c.c(h3.h);
        this.m = h3.i;
    }

    public void p(int n3, int n4, h h3, o o3, o o4) {
        float f3;
        this.e = f3 = (float)h3.a / 100.0f;
        this.d = h3.j;
        float f4 = Float.isNaN(h3.k) ? f3 : h3.k;
        float f5 = Float.isNaN(h3.l) ? f3 : h3.l;
        float f6 = o4.i;
        float f7 = o3.i;
        float f8 = o4.j;
        float f9 = o3.j;
        this.f = this.e;
        float f10 = o3.g;
        float f11 = f7 / 2.0f;
        float f12 = o3.h;
        float f13 = f9 / 2.0f;
        float f14 = o4.g;
        float f15 = f6 / 2.0f;
        float f16 = o4.h;
        float f17 = f8 / 2.0f;
        f4 = (f6 - f7) * f4;
        this.g = (int)(f10 + (f14 + f15 - (f11 + f10)) * f3 - f4 / 2.0f);
        f5 = (f8 - f9) * f5;
        this.h = (int)(f12 + (f16 + f17 - (f12 + f13)) * f3 - f5 / 2.0f);
        this.i = (int)(f7 + f4);
        this.j = (int)(f9 + f5);
        this.r = 2;
        if (!Float.isNaN(h3.m)) {
            int n5 = (int)this.i;
            this.g = (int)(h3.m * (float)(n3 - n5));
        }
        if (!Float.isNaN(h3.n)) {
            n3 = (int)this.j;
            this.h = (int)(h3.n * (float)(n4 - n3));
        }
        this.n = this.n;
        this.c = s.c.c(h3.h);
        this.m = h3.i;
    }

    public void q(float f3, float f4, float f5, float f6) {
        this.g = f3;
        this.h = f4;
        this.i = f5;
        this.j = f6;
    }

    public void r(float f3, float f4, float[] fArray, int[] nArray, double[] dArray, double[] dArray2) {
        float f5;
        float f6;
        float f7 = 0.0f;
        float f8 = f6 = (f5 = 0.0f);
        float f9 = f6;
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            f6 = (float)dArray[i3];
            double d3 = dArray2[i3];
            int n3 = nArray[i3];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) continue;
                        f8 = f6;
                        continue;
                    }
                    f5 = f6;
                    continue;
                }
                f9 = f6;
                continue;
            }
            f7 = f6;
        }
        f6 = f7 - 0.0f * f5 / 2.0f;
        fArray[0] = f6 * (1.0f - f3) + (f5 * 1.0f + f6) * f3 + 0.0f;
        fArray[1] = (f9 -= 0.0f * f8 / 2.0f) * (1.0f - f4) + (f8 * 1.0f + f9) * f4 + 0.0f;
    }

    public void s(float f3, View view, int[] object, double[] objectArray, double[] dArray, double[] objectArray2, boolean bl) {
        double d3;
        float f4;
        double d4;
        int n3;
        int n4;
        float f5 = this.g;
        float f6 = this.h;
        float f7 = this.i;
        float f8 = this.j;
        if (((int[])object).length != 0 && this.t.length <= object[((int[])object).length - 1]) {
            n4 = object[((int[])object).length - 1] + 1;
            this.t = new double[n4];
            this.u = new double[n4];
        }
        Arrays.fill(this.t, Double.NaN);
        for (n4 = 0; n4 < ((int[])object).length; ++n4) {
            double[] dArray2 = this.t;
            n3 = object[n4];
            dArray2[n3] = objectArray[n4];
            this.u[n3] = dArray[n4];
        }
        float f9 = Float.NaN;
        float f10 = 0.0f;
        float f11 = 0.0f;
        float f12 = 0.0f;
        float f13 = 0.0f;
        for (n4 = 0; n4 < ((int[])(object = (Object)this.t)).length; ++n4) {
            boolean bl2 = Double.isNaN(object[n4]);
            d4 = 0.0;
            if (bl2 && (objectArray2 == null || objectArray2[n4] == 0.0)) continue;
            if (objectArray2 != null) {
                d4 = objectArray2[n4];
            }
            if (!Double.isNaN(this.t[n4])) {
                d4 = this.t[n4] + d4;
            }
            f4 = (float)d4;
            d3 = (float)this.u[n4];
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 4) {
                            if (n4 != 5) continue;
                            f9 = f4;
                            continue;
                        }
                        f8 = f4;
                        f13 = (float)d3;
                        continue;
                    }
                    f7 = f4;
                    f12 = (float)d3;
                    continue;
                }
                f6 = f4;
                f11 = (float)d3;
                continue;
            }
            f10 = (float)d3;
            f5 = f4;
        }
        object = this.p;
        if (object != null) {
            objectArray2 = new float[2];
            objectArray = new float[2];
            ((m)object).i(f3, (float[])objectArray2, (float[])objectArray);
            d3 = objectArray2[0];
            f13 = (float)objectArray2[1];
            f4 = (float)objectArray[0];
            f3 = (float)objectArray[1];
            double d5 = d3;
            d4 = f5;
            double d6 = f6;
            d3 = (float)(d5 + Math.sin(d6) * d4 - (double)(f7 / 2.0f));
            f6 = (float)((double)f13 - Math.cos(d6) * d4 - (double)(f8 / 2.0f));
            double d7 = f4;
            double d8 = f10;
            d5 = Math.sin(d6);
            double d9 = Math.cos(d6);
            double d10 = f11;
            f5 = (float)(d7 + d5 * d8 + d9 * d4 * d10);
            f11 = (float)((double)f3 - d8 * Math.cos(d6) + Math.sin(d6) * d4 * d10);
            if (dArray.length >= 2) {
                dArray[0] = f5;
                dArray[1] = f11;
            }
            f4 = (float)d3;
            f3 = f6;
            if (!Float.isNaN(f9)) {
                view.setRotation((float)((double)f9 + Math.toDegrees(Math.atan2(f11, f5))));
                f4 = (float)d3;
                f3 = f6;
            }
        } else {
            f4 = f5;
            f3 = f6;
            if (!Float.isNaN(f9)) {
                f3 = f12 / 2.0f;
                view.setRotation(f9 + (float)Math.toDegrees(Math.atan2(f11 + f13 / 2.0f, f10 + f3)) + 0.0f);
                f3 = f6;
                f4 = f5;
            }
        }
        if (view instanceof c) {
            ((c)view).a(f4, f3, f7 + f4, f8 + f3);
            return;
        }
        f9 = f4 + 0.5f;
        n3 = (int)f9;
        int n5 = (int)(f3 += 0.5f);
        n4 = (int)(f9 + f7);
        int n6 = (int)(f3 + f8);
        int n7 = n4 - n3;
        int n8 = n6 - n5;
        if (n7 != view.getMeasuredWidth() || n8 != view.getMeasuredHeight() || bl) {
            view.measure(View.MeasureSpec.makeMeasureSpec((int)n7, (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)n8, (int)0x40000000));
        }
        view.layout(n3, n5, n4, n6);
    }

    public void t(m m3, o o3) {
        double d3 = this.g + this.i / 2.0f - o3.g - o3.i / 2.0f;
        double d4 = this.h + this.j / 2.0f - o3.h - o3.j / 2.0f;
        this.p = m3;
        this.g = (float)Math.hypot(d4, d3);
        if (Float.isNaN(this.o)) {
            this.h = (float)(Math.atan2(d4, d3) + 1.5707963267948966);
            return;
        }
        this.h = (float)Math.toRadians(this.o);
    }
}

