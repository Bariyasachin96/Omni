/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnimationUtils
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.OvershootInterpolator
 */
package x;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import s.c;
import s.p;
import w.c;
import w.d;
import w.f;
import x.a;
import x.d;
import x.e;
import x.f;
import x.h;
import x.i;
import x.j;
import x.k;
import x.l;
import x.o;

public class m {
    public ArrayList A;
    public HashMap B;
    public HashMap C;
    public HashMap D;
    public k[] E;
    public int F;
    public int G;
    public View H;
    public int I;
    public float J;
    public Interpolator K;
    public boolean L;
    public Rect a = new Rect();
    public View b;
    public int c;
    public boolean d = false;
    public String e;
    public int f = -1;
    public o g = new o();
    public o h = new o();
    public l i = new l();
    public l j = new l();
    public s.b[] k;
    public s.b l;
    public float m = Float.NaN;
    public float n = 0.0f;
    public float o = 1.0f;
    public float p;
    public float q;
    public int[] r;
    public double[] s;
    public double[] t;
    public String[] u;
    public int[] v;
    public int w = 4;
    public float[] x = new float[4];
    public ArrayList y = new ArrayList();
    public float[] z = new float[1];

    public m(View view) {
        int n3;
        this.A = new ArrayList();
        this.F = n3 = x.d.f;
        this.G = n3;
        this.H = null;
        this.I = n3;
        this.J = Float.NaN;
        this.K = null;
        this.L = false;
        this.H(view);
    }

    public static Interpolator p(Context context, int n3, String string, int n4) {
        if (n3 != -2) {
            if (n3 != -1) {
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 4) {
                                if (n3 != 5) {
                                    return null;
                                }
                                return new OvershootInterpolator();
                            }
                            return new BounceInterpolator();
                        }
                        return new DecelerateInterpolator();
                    }
                    return new AccelerateInterpolator();
                }
                return new AccelerateDecelerateInterpolator();
            }
            return new Interpolator(s.c.c(string)){
                public final c a;
                {
                    this.a = c3;
                }

                public float getInterpolation(float f3) {
                    return (float)this.a.a(f3);
                }
            };
        }
        return AnimationUtils.loadInterpolator((Context)context, (int)n4);
    }

    public void A(Rect rect, Rect rect2, int n3, int n4, int n5) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    if (n3 != 4) {
                        return;
                    }
                    n5 = rect.left;
                    n3 = rect.right;
                    rect2.left = n4 - (rect.bottom + rect.top + rect.width()) / 2;
                    rect2.top = (n5 + n3 - rect.height()) / 2;
                    rect2.right = rect2.left + rect.width();
                    rect2.bottom = rect2.top + rect.height();
                    return;
                }
                n3 = rect.left + rect.right;
                rect2.left = rect.height() / 2 + rect.top - n3 / 2;
                rect2.top = n5 - (n3 + rect.height()) / 2;
                rect2.right = rect2.left + rect.width();
                rect2.bottom = rect2.top + rect.height();
                return;
            }
            n5 = rect.left;
            n3 = rect.right;
            rect2.left = n4 - (rect.top + rect.bottom + rect.width()) / 2;
            rect2.top = (n5 + n3 - rect.height()) / 2;
            rect2.right = rect2.left + rect.width();
            rect2.bottom = rect2.top + rect.height();
            return;
        }
        n3 = rect.left;
        n4 = rect.right;
        rect2.left = (rect.top + rect.bottom - rect.width()) / 2;
        rect2.top = n5 - (n3 + n4 + rect.height()) / 2;
        rect2.right = rect2.left + rect.width();
        rect2.bottom = rect2.top + rect.height();
    }

    public void B(View view) {
        o o3 = this.g;
        o3.e = 0.0f;
        o3.f = 0.0f;
        this.L = true;
        o3.q(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.h.q(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.i.i(view);
        this.j.i(view);
    }

    public void C(Rect rect, b b3, int n3, int n4) {
        Object object;
        int n5 = b3.e;
        if (n5 != 0) {
            object = this.a;
            this.A(rect, (Rect)object, n5, n3, n4);
            rect = this.a;
        }
        object = this.h;
        object.e = 1.0f;
        object.f = 1.0f;
        this.y((o)object);
        this.h.q(rect.left, rect.top, rect.width(), rect.height());
        this.h.a(b3.y(this.c));
        this.j.h(rect, b3, n5, this.c);
    }

    public void D(int n3) {
        this.F = n3;
    }

    public void E(View view) {
        o o3 = this.g;
        o3.e = 0.0f;
        o3.f = 0.0f;
        o3.q(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.i.i(view);
    }

    public void F(Rect object, b object2, int n3, int n4) {
        int n5 = ((b)object2).e;
        if (n5 != 0) {
            this.A((Rect)object, this.a, n5, n3, n4);
        }
        Object object3 = this.g;
        ((o)object3).e = 0.0f;
        ((o)object3).f = 0.0f;
        this.y((o)object3);
        this.g.q(object.left, object.top, object.width(), object.height());
        object3 = ((b)object2).y(this.c);
        this.g.a((b.a)object3);
        this.m = ((b.a)object3).d.g;
        this.i.h((Rect)object, (b)object2, n5, this.c);
        this.G = ((b.a)object3).f.i;
        object = ((b.a)object3).d;
        this.I = object.k;
        this.J = object.j;
        object = this.b.getContext();
        object2 = ((b.a)object3).d;
        this.K = x.m.p((Context)object, ((b.c)object2).m, ((b.c)object2).l, ((b.c)object2).n);
    }

    public void G(w.e object, View view, int n3, int n4, int n5) {
        object = this.g;
        ((o)object).e = 0.0f;
        ((o)object).f = 0.0f;
        object = new Rect();
        if (n3 != 1) {
            if (n3 != 2) {
                this.g.q(((Rect)object).left, ((Rect)object).top, object.width(), object.height());
                throw null;
            }
            throw null;
        }
        throw null;
    }

    public void H(View view) {
        this.b = view;
        this.c = view.getId();
        if ((view = view.getLayoutParams()) instanceof ConstraintLayout.LayoutParams) {
            this.e = ((ConstraintLayout.LayoutParams)view).a();
        }
    }

    public void I(int n3, int n4, float f3, long l3) {
        Object object;
        Object object2;
        int n5;
        Object object3;
        int n6;
        Object object42;
        Object object52;
        int n7;
        new HashSet();
        Object object6 = new HashSet();
        Object object7 = new HashSet();
        Object object8 = new HashSet();
        o[] oArray = new HashMap();
        int n8 = this.F;
        if (n8 != x.d.f) {
            this.g.m = n8;
        }
        this.i.f(this.j, (HashSet)object7);
        Object object9 = this.A;
        if (object9 != null) {
            n7 = ((ArrayList)object9).size();
            n8 = 0;
            object52 = null;
            while (true) {
                object42 = object52;
                if (n8 < n7) {
                    object42 = ((ArrayList)object9).get(n8);
                    n6 = n8 + 1;
                    object3 = (d)object42;
                    if (object3 instanceof h) {
                        object42 = (h)object3;
                        this.w(new o(n3, n4, (h)object42, this.g, this.h));
                        n5 = ((i)object42).g;
                        n8 = n6;
                        if (n5 == x.d.f) continue;
                        this.f = n5;
                        n8 = n6;
                        continue;
                    }
                    if (object3 instanceof f) {
                        ((d)object3).d((HashSet)object8);
                        n8 = n6;
                        continue;
                    }
                    if (object3 instanceof j) {
                        ((d)object3).d((HashSet)object6);
                        n8 = n6;
                        continue;
                    }
                    if (object3 instanceof k) {
                        object42 = object52;
                        if (object52 == null) {
                            object42 = new ArrayList();
                        }
                        ((ArrayList)object42).add((k)((k)object3));
                        object52 = object42;
                        n8 = n6;
                        continue;
                    }
                    ((d)object3).h((HashMap)oArray);
                    ((d)object3).d((HashSet)object7);
                    n8 = n6;
                    continue;
                }
                break;
            }
        } else {
            object42 = null;
        }
        if (object42 != null) {
            this.E = ((ArrayList)object42).toArray(new k[0]);
        }
        boolean bl = ((HashSet)object7).isEmpty();
        n3 = 1;
        if (!bl) {
            this.C = new HashMap();
            object42 = ((HashSet)object7).iterator();
            while (object42.hasNext()) {
                object9 = (String)object42.next();
                if (((String)object9).startsWith("CUSTOM,")) {
                    object52 = new SparseArray();
                    object2 = ((String)object9).split(",")[n3];
                    object3 = this.A;
                    n8 = ((ArrayList)object3).size();
                    for (n4 = 0; n4 < n8; ++n4) {
                        object = ((ArrayList)object3).get(n4);
                        object = (d)object;
                        Object object10 = ((d)object).e;
                        if (object10 == null || (object10 = (androidx.constraintlayout.widget.a)((HashMap)object10).get(object2)) == null) continue;
                        object52.append(((d)object).a, object10);
                    }
                    object52 = w.d.f((String)object9, (SparseArray)object52);
                } else {
                    object52 = w.d.g((String)object9);
                }
                if (object52 == null) continue;
                ((s.j)object52).d((String)object9);
                this.C.put(object9, object52);
            }
            n4 = n3;
            object52 = this.A;
            if (object52 != null) {
                n6 = ((ArrayList)object52).size();
                n3 = 0;
                while (n3 < n6) {
                    object42 = ((ArrayList)object52).get(n3);
                    n8 = n3 + 1;
                    object42 = (d)object42;
                    n3 = n8;
                    if (!(object42 instanceof e)) continue;
                    ((d)object42).a(this.C);
                    n3 = n8;
                }
            }
            this.i.a(this.C, 0);
            this.j.a(this.C, 100);
            object52 = this.C.keySet().iterator();
            while (true) {
                n3 = n4;
                if (object52.hasNext()) {
                    object42 = (String)object52.next();
                    n3 = oArray.containsKey(object42) && (object9 = (Integer)oArray.get(object42)) != null ? (Integer)object9 : 0;
                    if ((object42 = (s.j)this.C.get(object42)) == null) continue;
                    ((s.j)object42).e(n3);
                    continue;
                }
                break;
            }
        } else {
            n3 = 1;
        }
        if (!((HashSet)object6).isEmpty()) {
            if (this.B == null) {
                this.B = new HashMap();
            }
            object42 = ((HashSet)object6).iterator();
            while (object42.hasNext()) {
                object6 = (String)object42.next();
                if (this.B.containsKey(object6)) continue;
                if (((String)object6).startsWith("CUSTOM,")) {
                    object9 = new SparseArray();
                    object3 = ((String)object6).split(",")[n3];
                    object52 = this.A;
                    n6 = ((ArrayList)object52).size();
                    n4 = 0;
                    while (n4 < n6) {
                        object2 = ((ArrayList)object52).get(n4);
                        n8 = n4 + 1;
                        object2 = (d)object2;
                        object = ((d)object2).e;
                        if (object == null) {
                            n4 = n8;
                            continue;
                        }
                        object = (androidx.constraintlayout.widget.a)((HashMap)object).get(object3);
                        n4 = n8;
                        if (object == null) continue;
                        object9.append(((d)object2).a, object);
                        n4 = n8;
                    }
                    object52 = w.f.g(object6, (SparseArray)object9);
                } else {
                    object52 = w.f.h((String)object6, l3);
                }
                if (object52 == null) continue;
                ((s.o)object52).d((String)object6);
                this.B.put(object6, object52);
            }
            object52 = this.A;
            if (object52 != null) {
                n6 = ((ArrayList)object52).size();
                n4 = 0;
                while (n4 < n6) {
                    object42 = ((ArrayList)object52).get(n4);
                    n8 = n4 + 1;
                    object42 = (d)object42;
                    n4 = n8;
                    if (!(object42 instanceof j)) continue;
                    ((j)object42).U(this.B);
                    n4 = n8;
                }
            }
            for (Object object42 : this.B.keySet()) {
                n4 = oArray.containsKey(object42) ? (Integer)oArray.get(object42) : 0;
                ((w.f)this.B.get(object42)).e(n4);
            }
        }
        n4 = this.y.size();
        n7 = n4 + 2;
        oArray = new o[n7];
        oArray[0] = this.g;
        oArray[n4 + 1] = this.h;
        if (this.y.size() > 0 && this.f == -1) {
            this.f = 0;
        }
        object42 = this.y;
        n6 = ((ArrayList)object42).size();
        n4 = n3;
        n8 = 0;
        while (n8 < n6) {
            object52 = ((ArrayList)object42).get(n8);
            ++n8;
            oArray[n4] = (o)object52;
            ++n4;
        }
        object6 = new HashSet<String[]>();
        for (Object object52 : this.h.q.keySet()) {
            if (!((AbstractMap)this.g.q).containsKey(object52)) continue;
            object42 = new StringBuilder();
            ((StringBuilder)object42).append("CUSTOM,");
            ((StringBuilder)object42).append((String)object52);
            if (((HashSet)object7).contains(((StringBuilder)object42).toString())) continue;
            ((HashSet)object6).add(object52);
        }
        object52 = ((HashSet)object6).toArray(new String[0]);
        this.u = object52;
        this.v = new int[((String[])object52).length];
        block11: for (n4 = 0; n4 < ((String[])(object52 = this.u)).length; ++n4) {
            object42 = object52[n4];
            this.v[n4] = 0;
            for (n8 = 0; n8 < n7; ++n8) {
                if (!((AbstractMap)oArray[n8].q).containsKey(object42) || (object52 = (androidx.constraintlayout.widget.a)oArray[n8].q.get(object42)) == null) continue;
                object42 = this.v;
                object42[n4] = object42[n4] + ((androidx.constraintlayout.widget.a)object52).h();
                continue block11;
            }
        }
        n4 = oArray[0].m != x.d.f ? n3 : 0;
        n5 = 18 + ((String[])object52).length;
        object52 = new boolean[n5];
        for (n8 = n3; n8 < n7; ++n8) {
            oArray[n8].d(oArray[n8 - 1], (boolean[])object52, this.u, n4 != 0);
        }
        n8 = 0;
        for (n4 = n3; n4 < n5; ++n4) {
            n6 = n8;
            if (object52[n4] != false) {
                n6 = n8 + 1;
            }
            n8 = n6;
        }
        this.r = new int[n8];
        n4 = Math.max(2, n8);
        this.s = new double[n4];
        this.t = new double[n4];
        n8 = 0;
        for (n4 = n3; n4 < n5; ++n4) {
            n6 = n8;
            if (object52[n4] != false) {
                this.r[n8] = n4;
                n6 = n8 + 1;
            }
            n8 = n6;
        }
        n4 = this.r.length;
        object52 = new int[2];
        object52[n3] = (String)n4;
        object52[0] = (String)n7;
        object7 = Double.TYPE;
        object9 = (double[][])Array.newInstance(object7, (int[])object52);
        object6 = new double[n7];
        for (n4 = 0; n4 < n7; ++n4) {
            oArray[n4].e((double[])object9[n4], this.r);
            object6[n4] = (double)oArray[n4].e;
        }
        for (n4 = 0; n4 < ((String[])(object52 = (Object)this.r)).length; ++n4) {
            if (object52[n4] >= x.o.v.length) continue;
            object52 = new StringBuilder();
            ((StringBuilder)object52).append(x.o.v[this.r[n4]]);
            ((StringBuilder)object52).append(" [");
            object52 = ((StringBuilder)object52).toString();
            for (n8 = 0; n8 < n7; ++n8) {
                object42 = new StringBuilder();
                ((StringBuilder)object42).append((String)object52);
                ((StringBuilder)object42).append((double)object9[n8][n4]);
                object52 = ((StringBuilder)object42).toString();
            }
        }
        this.k = new s.b[this.u.length + 1];
        n4 = 0;
        while (n4 < ((String[])(object52 = this.u)).length) {
            object3 = object52[n4];
            n6 = 0;
            object42 = null;
            object52 = null;
            for (n8 = 0; n8 < n7; ++n8) {
                if (!oArray[n8].k((String)object3)) continue;
                if (object52 == null) {
                    object42 = new double[n7];
                    n5 = oArray[n8].i((String)object3);
                    object52 = new int[2];
                    object52[n3] = n5;
                    object52[0] = n7;
                    object52 = (double[][])Array.newInstance(object7, (int[])object52);
                }
                object2 = oArray[n8];
                object42[n6] = (double)((o)object2).e;
                ((o)object2).h((String)object3, (double[])object52[n6], 0);
                ++n6;
            }
            object42 = Arrays.copyOf((double[])object42, n6);
            object3 = (double[][])Arrays.copyOf(object52, n6);
            object52 = this.k;
            object52[++n4] = s.b.a(this.f, (double[])object42, (double[][])object3);
        }
        this.k[0] = s.b.a(this.f, (double[])object6, (double[][])object9);
        if (oArray[0].m != x.d.f) {
            object52 = new int[n7];
            object42 = new double[n7];
            object6 = new int[2];
            object6[n3] = 2;
            object6[0] = n7;
            object7 = (double[][])Array.newInstance(object7, (int[])object6);
            for (n4 = 0; n4 < n7; ++n4) {
                object6 = oArray[n4];
                object52[n4] = ((o)object6).m;
                object42[n4] = (double)((o)object6).e;
                object9 = object7[n4];
                object9[0] = (double)((o)object6).g;
                object9[n3] = (double)((o)object6).h;
            }
            this.l = s.b.b((int[])object52, (double[])object42, (double[][])object7);
        }
        this.D = new HashMap();
        if (this.A != null) {
            object52 = ((HashSet)object8).iterator();
            float f4 = Float.NaN;
            while (object52.hasNext()) {
                object8 = (String)object52.next();
                object42 = w.c.i((String)object8);
                if (object42 == null) continue;
                f3 = f4;
                if (((s.e)object42).h()) {
                    f3 = f4;
                    if (Float.isNaN(f4)) {
                        f3 = this.s();
                    }
                }
                ((s.e)object42).f((String)object8);
                this.D.put(object8, object42);
                f4 = f3;
            }
            object52 = this.A;
            n8 = ((ArrayList)object52).size();
            n3 = 0;
            while (n3 < n8) {
                object42 = ((ArrayList)object52).get(n3);
                n4 = n3 + 1;
                object42 = (d)object42;
                n3 = n4;
                if (!(object42 instanceof f)) continue;
                ((f)object42).Y(this.D);
                n3 = n4;
            }
            object52 = this.D.values().iterator();
            while (object52.hasNext()) {
                ((w.c)object52.next()).g(f4);
            }
        }
    }

    public void J(m m3) {
        this.g.t(m3, m3.g);
        this.h.t(m3, m3.h);
    }

    public void a(d d3) {
        this.A.add(d3);
    }

    public void b(ArrayList arrayList) {
        this.A.addAll(arrayList);
    }

    public int c(float[] fArray, int[] nArray) {
        if (fArray != null) {
            int n3;
            int n4;
            double[] dArray = this.k[0].h();
            if (nArray != null) {
                ArrayList arrayList = this.y;
                int n5 = arrayList.size();
                n4 = 0;
                n3 = 0;
                while (n3 < n5) {
                    Object e3 = arrayList.get(n3);
                    ++n3;
                    nArray[n4] = ((o)e3).r;
                    ++n4;
                }
            }
            n4 = 0;
            for (n3 = 0; n3 < dArray.length; ++n3) {
                this.k[0].d(dArray[n3], this.s);
                this.g.f(dArray[n3], this.r, this.s, fArray, n4);
                n4 += 2;
            }
            return n4 / 2;
        }
        return 0;
    }

    public void d(float[] fArray, int n3) {
        float f3 = 1.0f / (float)(n3 - 1);
        Object object = this.C;
        w.c c3 = null;
        object = object == null ? null : (s.j)((HashMap)object).get("translationX");
        Object object2 = this.C;
        object2 = object2 == null ? null : (s.j)((HashMap)object2).get("translationY");
        Object object3 = this.D;
        object3 = object3 == null ? null : (w.c)((HashMap)object3).get("translationX");
        Object object4 = this.D;
        if (object4 != null) {
            c3 = (w.c)((HashMap)object4).get("translationY");
        }
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object5;
            Object object6;
            float f4;
            float f5;
            float f6 = (float)i3 * f3;
            float f7 = this.o;
            float f8 = 0.0f;
            float f9 = f6;
            if (f7 != 1.0f) {
                f5 = this.n;
                f4 = f6;
                if (f6 < f5) {
                    f4 = 0.0f;
                }
                f9 = f4;
                if (f4 > f5) {
                    f9 = f4;
                    if ((double)f4 < 1.0) {
                        f9 = Math.min((f4 - f5) * f7, 1.0f);
                    }
                }
            }
            double d3 = f9;
            object4 = this.g.c;
            ArrayList arrayList = this.y;
            int n4 = arrayList.size();
            f4 = Float.NaN;
            int n5 = 0;
            f6 = f8;
            while (n5 < n4) {
                object6 = arrayList.get(n5);
                ++n5;
                o o3 = (o)object6;
                object5 = o3.c;
                f5 = f6;
                object6 = object4;
                f8 = f4;
                if (object5 != null) {
                    f5 = o3.e;
                    if (f5 < f9) {
                        object6 = object5;
                        f8 = f4;
                    } else {
                        f5 = f6;
                        object6 = object4;
                        f8 = f4;
                        if (Float.isNaN(f4)) {
                            f8 = o3.e;
                            object6 = object4;
                            f5 = f6;
                        }
                    }
                }
                f6 = f5;
                object4 = object6;
                f4 = f8;
            }
            if (object4 != null) {
                f8 = f4;
                if (Float.isNaN(f4)) {
                    f8 = 1.0f;
                }
                f4 = f8 - f6;
                d3 = (float)((c)object4).a((f9 - f6) / f4) * f4 + f6;
            }
            this.k[0].d(d3, this.s);
            object6 = this.l;
            if (object6 != null && ((Object)(object4 = (Object)this.s)).length > 0) {
                ((s.b)object6).d(d3, (double[])object4);
            }
            object4 = this.g;
            object5 = this.r;
            object6 = this.s;
            n5 = i3 * 2;
            ((o)object4).f(d3, (int[])object5, (double[])object6, fArray, n5);
            if (object3 != null) {
                fArray[n5] = fArray[n5] + ((s.e)object3).a(f9);
            } else if (object != null) {
                fArray[n5] = fArray[n5] + ((s.j)object).a(f9);
            }
            if (c3 != null) {
                fArray[++n5] = fArray[n5] + c3.a(f9);
                continue;
            }
            if (object2 == null) continue;
            fArray[++n5] = fArray[n5] + ((s.j)object2).a(f9);
        }
    }

    public void e(float f3, float[] fArray, int n3) {
        f3 = this.g(f3, null);
        this.k[0].d(f3, this.s);
        this.g.j(this.r, this.s, fArray, n3);
    }

    public void f(boolean bl) {
        if ("button".equals(x.a.d(this.b)) && this.E != null) {
            Object object;
            for (int i3 = 0; i3 < ((k[])(object = this.E)).length; ++i3) {
                object = object[i3];
                float f3 = bl ? -100.0f : 100.0f;
                ((k)object).y(f3, this.b);
            }
        }
    }

    public final float g(float f3, float[] fArray) {
        float f4;
        float f5;
        float f6 = 0.0f;
        float f7 = 1.0f;
        if (fArray != null) {
            fArray[0] = 1.0f;
            f5 = f3;
        } else {
            float f8 = this.o;
            f5 = f3;
            if ((double)f8 != 1.0) {
                float f9 = this.n;
                f4 = f3;
                if (f3 < f9) {
                    f4 = 0.0f;
                }
                f5 = f4;
                if (f4 > f9) {
                    f5 = f4;
                    if ((double)f4 < 1.0) {
                        f5 = Math.min((f4 - f9) * f8, 1.0f);
                    }
                }
            }
        }
        c c3 = this.g.c;
        ArrayList arrayList = this.y;
        int n3 = arrayList.size();
        f3 = Float.NaN;
        int n4 = 0;
        f4 = f6;
        while (n4 < n3) {
            Object object = arrayList.get(n4);
            int n5 = n4 + 1;
            o o3 = (o)object;
            object = o3.c;
            n4 = n5;
            if (object == null) continue;
            f6 = o3.e;
            if (f6 < f5) {
                c3 = object;
                f4 = f6;
                n4 = n5;
                continue;
            }
            n4 = n5;
            if (!Float.isNaN(f3)) continue;
            f3 = o3.e;
            n4 = n5;
        }
        f6 = f5;
        if (c3 != null) {
            if (Float.isNaN(f3)) {
                f3 = f7;
            }
            double d3 = (f5 - f4) / (f3 -= f4);
            f6 = f3 = (float)c3.a(d3) * f3 + f4;
            if (fArray != null) {
                fArray[0] = (float)c3.b(d3);
                f6 = f3;
            }
        }
        return f6;
    }

    public int h() {
        return this.g.n;
    }

    public void i(double d3, float[] fArray, float[] fArray2) {
        double[] dArray = new double[4];
        double[] dArray2 = new double[4];
        this.k[0].d(d3, dArray);
        this.k[0].g(d3, dArray2);
        Arrays.fill(fArray2, 0.0f);
        this.g.g(d3, this.r, dArray, fArray, dArray2, fArray2);
    }

    public float j() {
        return this.p;
    }

    public float k() {
        return this.q;
    }

    public void l(float f3, float f4, float f5, float[] fArray) {
        f3 = this.g(f3, this.z);
        Object object = this.k;
        if (object != null) {
            double[] dArray;
            object = object[0];
            double d3 = f3;
            ((s.b)object).g(d3, this.t);
            this.k[0].d(d3, this.s);
            f3 = this.z[0];
            for (int i3 = 0; i3 < (dArray = this.t).length; ++i3) {
                dArray[i3] = dArray[i3] * (double)f3;
            }
            object = this.l;
            if (object != null) {
                dArray = this.s;
                if (dArray.length > 0) {
                    ((s.b)object).d(d3, dArray);
                    this.l.g(d3, this.t);
                    this.g.r(f4, f5, fArray, this.r, this.t, this.s);
                }
                return;
            }
            this.g.r(f4, f5, fArray, this.r, dArray, this.s);
            return;
        }
        object = this.h;
        f3 = ((o)object).g;
        o o3 = this.g;
        float f6 = f3 - o3.g;
        float f7 = ((o)object).h - o3.h;
        float f8 = ((o)object).i;
        f3 = o3.i;
        float f9 = ((o)object).j;
        float f10 = o3.j;
        fArray[0] = f6 * (1.0f - f4) + (f8 - f3 + f6) * f4;
        fArray[1] = f7 * (1.0f - f5) + (f9 - f10 + f7) * f5;
    }

    public int m() {
        int n3 = this.g.d;
        ArrayList arrayList = this.y;
        int n4 = arrayList.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            Object e3 = arrayList.get(i3);
            n3 = Math.max(n3, ((o)e3).d);
        }
        return Math.max(n3, this.h.d);
    }

    public float n() {
        return this.h.g;
    }

    public float o() {
        return this.h.h;
    }

    public o q(int n3) {
        return (o)this.y.get(n3);
    }

    public void r(float f3, int n3, int n4, float f4, float f5, float[] fArray) {
        f3 = this.g(f3, this.z);
        Object object = this.C;
        w.c c3 = null;
        object = object == null ? null : (s.j)((HashMap)object).get("translationX");
        Object object2 = this.C;
        object2 = object2 == null ? null : (s.j)((HashMap)object2).get("translationY");
        Object object3 = this.C;
        object3 = object3 == null ? null : (s.j)((HashMap)object3).get("rotation");
        Object object4 = this.C;
        object4 = object4 == null ? null : (s.j)((HashMap)object4).get("scaleX");
        Object object5 = this.C;
        object5 = object5 == null ? null : (s.j)((HashMap)object5).get("scaleY");
        Object object6 = this.D;
        object6 = object6 == null ? null : (w.c)((HashMap)object6).get("translationX");
        Object object7 = this.D;
        object7 = object7 == null ? null : (w.c)((HashMap)object7).get("translationY");
        Object object8 = this.D;
        object8 = object8 == null ? null : (w.c)((HashMap)object8).get("rotation");
        Object object9 = this.D;
        object9 = object9 == null ? null : (w.c)((HashMap)object9).get("scaleX");
        Object object10 = this.D;
        if (object10 != null) {
            c3 = (w.c)((HashMap)object10).get("scaleY");
        }
        object10 = new p();
        ((p)object10).b();
        ((p)object10).d((s.j)object3, f3);
        ((p)object10).h((s.j)object, (s.j)object2, f3);
        ((p)object10).f((s.j)object4, (s.j)object5, f3);
        ((p)object10).c((s.e)object8, f3);
        ((p)object10).g((s.e)object6, (s.e)object7, f3);
        ((p)object10).e((s.e)object9, c3, f3);
        Object object11 = this.l;
        if (object11 != null) {
            object = this.s;
            if (((Object)object).length > 0) {
                double d3 = f3;
                object11.d(d3, (double[])object);
                this.l.g(d3, this.t);
                this.g.r(f4, f5, fArray, this.r, this.t, this.s);
            }
            ((p)object10).a(f4, f5, n3, n4, fArray);
            return;
        }
        object11 = this.k;
        if (object11 != null) {
            f3 = this.g(f3, this.z);
            object = this.k[0];
            double d4 = f3;
            ((s.b)object).g(d4, this.t);
            this.k[0].d(d4, this.s);
            f3 = this.z[0];
            for (int i3 = 0; i3 < ((Object)(object = (Object)this.t)).length; ++i3) {
                object[i3] = object[i3] * (double)f3;
            }
            this.g.r(f4, f5, fArray, this.r, (double[])object, this.s);
            ((p)object10).a(f4, f5, n3, n4, fArray);
            return;
        }
        object11 = this.h;
        float f6 = object11.g;
        o o3 = this.g;
        f6 -= o3.g;
        float f7 = object11.h - o3.h;
        float f8 = object11.i;
        float f9 = o3.i;
        float f10 = object11.j;
        float f11 = o3.j;
        fArray[0] = f6 * (1.0f - f4) + (f8 - f9 + f6) * f4;
        fArray[1] = f7 * (1.0f - f5) + (f7 + (f10 - f11)) * f5;
        ((p)object10).b();
        ((p)object10).d((s.j)object3, f3);
        ((p)object10).h((s.j)object, (s.j)object2, f3);
        ((p)object10).f((s.j)object4, (s.j)object5, f3);
        ((p)object10).c((s.e)object8, f3);
        ((p)object10).g((s.e)object6, (s.e)object7, f3);
        ((p)object10).e((s.e)object9, c3, f3);
        ((p)object10).a(f4, f5, n3, n4, fArray);
    }

    public final float s() {
        float[] fArray = new float[2];
        float f3 = 1.0f / (float)99;
        double d3 = 0.0;
        double d4 = 0.0;
        float f4 = 0.0f;
        for (int i3 = 0; i3 < 100; ++i3) {
            float f5;
            float f6 = (float)i3 * f3;
            double d5 = f6;
            c c3 = this.g.c;
            ArrayList arrayList = this.y;
            int n3 = arrayList.size();
            float f7 = Float.NaN;
            int n4 = 0;
            float f8 = 0.0f;
            while (n4 < n3) {
                Object object = arrayList.get(n4);
                ++n4;
                o o3 = (o)object;
                c c4 = o3.c;
                object = c3;
                f5 = f7;
                float f9 = f8;
                if (c4 != null) {
                    f9 = o3.e;
                    if (f9 < f6) {
                        object = c4;
                        f5 = f7;
                    } else {
                        object = c3;
                        f5 = f7;
                        f9 = f8;
                        if (Float.isNaN(f7)) {
                            f5 = o3.e;
                            f9 = f8;
                            object = c3;
                        }
                    }
                }
                c3 = object;
                f7 = f5;
                f8 = f9;
            }
            if (c3 != null) {
                f5 = f7;
                if (Float.isNaN(f7)) {
                    f5 = 1.0f;
                }
                f7 = f5 - f8;
                d5 = (float)c3.a((f6 - f8) / f7) * f7 + f8;
            }
            this.k[0].d(d5, this.s);
            this.g.f(d5, this.r, this.s, fArray, 0);
            f7 = f4;
            if (i3 > 0) {
                f7 = f4 + (float)Math.hypot(d4 - (double)fArray[1], d3 - (double)fArray[0]);
            }
            d3 = fArray[0];
            d4 = fArray[1];
            f4 = f7;
        }
        return f4;
    }

    public float t() {
        return this.g.g;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" start: x: ");
        stringBuilder.append(this.g.g);
        stringBuilder.append(" y: ");
        stringBuilder.append(this.g.h);
        stringBuilder.append(" end: x: ");
        stringBuilder.append(this.h.g);
        stringBuilder.append(" y: ");
        stringBuilder.append(this.h.h);
        return stringBuilder.toString();
    }

    public float u() {
        return this.g.h;
    }

    public View v() {
        return this.b;
    }

    public final void w(o o3) {
        int n3 = Collections.binarySearch(this.y, o3);
        if (n3 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" KeyPath position \"");
            stringBuilder.append(o3.f);
            stringBuilder.append("\" outside of range");
            Log.e((String)"MotionController", (String)stringBuilder.toString());
        }
        this.y.add(-n3 - 1, o3);
    }

    public boolean x(View view, float f3, long l3, s.d object) {
        boolean bl;
        float f4;
        Object object22;
        boolean bl2;
        Object object3;
        Object object42;
        float f5;
        float f6;
        float f7 = this.g(f3, null);
        int n3 = this.I;
        f3 = f7;
        if (n3 != x.d.f) {
            f6 = 1.0f / (float)n3;
            f5 = (float)Math.floor(f7 / f6);
            f3 = f7 = f7 % f6 / f6;
            if (!Float.isNaN(this.J)) {
                f3 = (f7 + this.J) % 1.0f;
            }
            f3 = (object42 = this.K) != null ? object42.getInterpolation(f3) : ((double)f3 > 0.5 ? 1.0f : 0.0f);
            f3 = f3 * f6 + f5 * f6;
        }
        if ((object42 = this.C) != null) {
            object42 = ((HashMap)object42).values().iterator();
            while (object42.hasNext()) {
                ((w.d)object42.next()).h(view, f3);
            }
        }
        if ((object42 = this.B) != null) {
            object3 = ((HashMap)object42).values().iterator();
            object42 = null;
            bl2 = false;
            while (object3.hasNext()) {
                object22 = (w.f)object3.next();
                if (object22 instanceof f.d) {
                    object42 = (f.d)object22;
                    continue;
                }
                bl2 |= ((w.f)object22).i(view, f3, l3, (s.d)object);
            }
        } else {
            object42 = null;
            bl2 = false;
        }
        if ((object3 = this.k) != null) {
            object3 = object3[0];
            double d3 = f3;
            ((s.b)object3).d(d3, this.s);
            this.k[0].g(d3, this.t);
            object3 = this.l;
            if (object3 != null && ((Object)(object22 = (Object)this.s)).length > 0) {
                ((s.b)object3).d(d3, (double[])object22);
                this.l.g(d3, this.t);
            }
            if (!this.L) {
                this.g.s(f3, view, this.r, this.s, this.t, null, this.d);
                this.d = false;
            }
            if (this.G != x.d.f) {
                if (this.H == null) {
                    this.H = ((View)view.getParent()).findViewById(this.G);
                }
                if ((object3 = this.H) != null) {
                    f7 = (float)(object3.getTop() + this.H.getBottom()) / 2.0f;
                    f5 = (float)(this.H.getLeft() + this.H.getRight()) / 2.0f;
                    if (view.getRight() - view.getLeft() > 0 && view.getBottom() - view.getTop() > 0) {
                        f6 = view.getLeft();
                        f4 = view.getTop();
                        view.setPivotX(f5 - f6);
                        view.setPivotY(f7 - f4);
                    }
                }
            }
            if ((object3 = this.C) != null) {
                for (Object object22 : ((HashMap)object3).values()) {
                    double[] dArray;
                    if (!(object22 instanceof d.d) || (dArray = this.t).length <= 1) continue;
                    ((d.d)object22).i(view, f3, dArray[0], dArray[1]);
                }
            }
            if (object42 != null) {
                object3 = this.t;
                bl = ((f.d)object42).j(view, (s.d)object, f3, l3, (double)object3[0], (double)object3[1]);
                bl2 |= bl;
            }
            for (n3 = 1; n3 < ((s.b[])(object = this.k)).length; ++n3) {
                object[n3].e(d3, this.x);
                w.a.b((androidx.constraintlayout.widget.a)this.g.q.get(this.u[n3 - 1]), view, this.x);
            }
            object = this.i;
            if (((l)object).d == 0) {
                if (f3 <= 0.0f) {
                    view.setVisibility(((l)object).e);
                } else if (f3 >= 1.0f) {
                    view.setVisibility(this.j.e);
                } else if (this.j.e != ((l)object).e) {
                    view.setVisibility(0);
                }
            }
            f7 = f3;
            bl = bl2;
            if (this.E != null) {
                n3 = 0;
                while (true) {
                    object = this.E;
                    f7 = f3;
                    bl = bl2;
                    if (n3 < ((Object)object).length) {
                        ((k)object[n3]).y(f3, view);
                        ++n3;
                        continue;
                    }
                    break;
                }
            }
        } else {
            object42 = this.g;
            float f8 = ((o)object42).g;
            object = this.h;
            float f9 = ((o)object).g;
            float f10 = ((o)object42).h;
            float f11 = ((o)object).h;
            f4 = ((o)object42).i;
            f7 = ((o)object).i;
            f5 = ((o)object42).j;
            f6 = ((o)object).j;
            f8 = f8 + (f9 - f8) * f3 + 0.5f;
            int n4 = (int)f8;
            f10 = f10 + (f11 - f10) * f3 + 0.5f;
            int n5 = (int)f10;
            int n6 = (int)(f8 + ((f7 - f4) * f3 + f4));
            n3 = (int)(f10 + ((f6 - f5) * f3 + f5));
            if (f7 != f4 || f6 != f5 || this.d) {
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n6 - n4), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)(n3 - n5), (int)0x40000000));
                this.d = false;
            }
            view.layout(n4, n5, n6, n3);
            bl = bl2;
            f7 = f3;
        }
        if ((object = this.D) != null) {
            for (Object object42 : ((HashMap)object).values()) {
                if (object42 instanceof c.d) {
                    object3 = (c.d)object42;
                    object42 = this.t;
                    ((c.d)object3).k(view, f7, (double)object42[0], (double)object42[1]);
                    continue;
                }
                ((w.c)object42).j(view, f7);
            }
        }
        return bl;
    }

    public final void y(o o3) {
        o3.q((int)this.b.getX(), (int)this.b.getY(), this.b.getWidth(), this.b.getHeight());
    }

    public void z() {
        this.d = true;
    }
}

