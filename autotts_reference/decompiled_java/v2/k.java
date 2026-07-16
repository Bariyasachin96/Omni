/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.RectF
 */
package v2;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import c1.c;
import c1.u;
import c1.v;
import c1.w;
import c1.x;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class k {
    public static final u A;
    public static final u B;
    public static final u C;
    public static final u D;
    public static final u E;
    public static final u F;
    public static final u G;
    public static final u H;
    public static final u I;
    public static final u J;
    public static final u K;
    public static final u L;
    public static final u M;
    public static final u N;
    public static final c a;
    public static final c b;
    public static final c c;
    public static final c d;
    public static final c e;
    public static final u f;
    public static final u g;
    public static final u h;
    public static final u i;
    public static final u j;
    public static final u k;
    public static final u l;
    public static final u m;
    public static final u n;
    public static final u o;
    public static final u p;
    public static final u q;
    public static final u r;
    public static final u s;
    public static final u t;
    public static final u u;
    public static final u v;
    public static final u w;
    public static final u x;
    public static final u y;
    public static final u z;

    static {
        a = new c(0.15f, 0.0f);
        b = new c(0.2f, 0.0f);
        c = new c(0.3f, 0.0f);
        d = new c(0.5f, 0.0f);
        e = new c(1.0f, 0.0f);
        f = v2.k.P(v2.k.i(), true);
        g = v2.k.P(v2.k.K(), true);
        h = v2.k.P(v2.k.H(), true);
        i = v2.k.P(v2.k.d(), true);
        j = v2.k.P(v2.k.s(), true);
        k = v2.k.P(v2.k.e(), true);
        l = v2.k.P(v2.k.G(), true);
        m = v2.k.P(v2.k.z(-45.0f), true);
        n = v2.k.P(v2.k.B(), true);
        o = v2.k.P(v2.k.N(-90.0f), true);
        p = v2.k.P(v2.k.r(), true);
        q = v2.k.P(v2.k.j(), true);
        r = v2.k.P(v2.k.A(), true);
        s = v2.k.P(v2.k.v(-90.0f), true);
        t = v2.k.P(v2.k.L(), true);
        u = v2.k.P(v2.k.O(), true);
        v = v2.k.P(v2.k.n(), true);
        w = v2.k.P(v2.k.o(), true);
        x = v2.k.P(v2.k.p(), true);
        y = v2.k.P(v2.k.q(), true);
        z = v2.k.P(v2.k.m(), true);
        A = v2.k.P(v2.k.w(), true);
        B = v2.k.P(v2.k.k(), true);
        C = v2.k.P(v2.k.l(), true);
        D = v2.k.P(v2.k.h(), true);
        E = v2.k.P(v2.k.J(), true);
        F = v2.k.P(v2.k.f(), true);
        G = v2.k.P(v2.k.I(), true);
        H = v2.k.P(v2.k.t(), true);
        I = v2.k.P(v2.k.E(), true);
        J = v2.k.P(v2.k.F(), true);
        K = v2.k.P(v2.k.C(), true);
        L = v2.k.P(v2.k.D(), true);
        M = v2.k.P(v2.k.g(), true);
        N = v2.k.P(v2.k.x(), true);
    }

    public static u A() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, -0.009f), new c(0.172f, 0.0f), null));
        return v2.k.c(arrayList, 5, 0.5f, 0.5f, false);
    }

    public static u B() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.961f, 0.039f), new c(0.426f, 0.0f), null));
        arrayList.add(new b(new PointF(1.001f, 0.428f), null));
        arrayList.add(new b(new PointF(1.0f, 0.609f), e, null));
        return v2.k.c(arrayList, 2, 0.5f, 0.5f, true);
    }

    public static u C() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 0.0f), null));
        arrayList.add(new b(new PointF(0.704f, 0.0f), null));
        arrayList.add(new b(new PointF(0.704f, 0.065f), null));
        arrayList.add(new b(new PointF(0.843f, 0.065f), null));
        arrayList.add(new b(new PointF(0.843f, 0.148f), null));
        arrayList.add(new b(new PointF(0.926f, 0.148f), null));
        arrayList.add(new b(new PointF(0.926f, 0.296f), null));
        arrayList.add(new b(new PointF(1.0f, 0.296f), null));
        return v2.k.c(arrayList, 2, 0.5f, 0.5f, true);
    }

    public static u D() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.11f, 0.5f), null));
        arrayList.add(new b(new PointF(0.113f, 0.0f), null));
        arrayList.add(new b(new PointF(0.287f, 0.0f), null));
        arrayList.add(new b(new PointF(0.287f, 0.087f), null));
        arrayList.add(new b(new PointF(0.421f, 0.087f), null));
        arrayList.add(new b(new PointF(0.421f, 0.17f), null));
        arrayList.add(new b(new PointF(0.56f, 0.17f), null));
        arrayList.add(new b(new PointF(0.56f, 0.265f), null));
        arrayList.add(new b(new PointF(0.674f, 0.265f), null));
        arrayList.add(new b(new PointF(0.675f, 0.344f), null));
        arrayList.add(new b(new PointF(0.789f, 0.344f), null));
        arrayList.add(new b(new PointF(0.789f, 0.439f), null));
        arrayList.add(new b(new PointF(0.888f, 0.439f), null));
        return v2.k.c(arrayList, 1, 0.5f, 0.5f, true);
    }

    public static u E() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 0.053f), null));
        arrayList.add(new b(new PointF(0.545f, -0.04f), new c(0.405f, 0.0f), null));
        arrayList.add(new b(new PointF(0.67f, -0.035f), new c(0.426f, 0.0f), null));
        arrayList.add(new b(new PointF(0.717f, 0.066f), new c(0.574f, 0.0f), null));
        arrayList.add(new b(new PointF(0.722f, 0.128f), null));
        arrayList.add(new b(new PointF(0.777f, 0.002f), new c(0.36f, 0.0f), null));
        arrayList.add(new b(new PointF(0.914f, 0.149f), new c(0.66f, 0.0f), null));
        arrayList.add(new b(new PointF(0.926f, 0.289f), new c(0.66f, 0.0f), null));
        arrayList.add(new b(new PointF(0.881f, 0.346f), null));
        arrayList.add(new b(new PointF(0.94f, 0.344f), new c(0.126f, 0.0f), null));
        arrayList.add(new b(new PointF(1.003f, 0.437f), new c(0.255f, 0.0f), null));
        return c1.x.c(v2.k.c(arrayList, 2, 0.5f, 0.5f, true), v2.k.b(1.0f, 0.742f));
    }

    public static u F() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.87f, 0.13f), new c(0.146f, 0.0f), null));
        arrayList.add(new b(new PointF(0.818f, 0.357f), null));
        arrayList.add(new b(new PointF(1.0f, 0.332f), new c(0.853f, 0.0f), null));
        return v2.k.c(arrayList, 4, 0.5f, 0.5f, true);
    }

    public static u G() {
        u.a a4 = c1.u.e;
        c c3 = c1.c.d;
        c c4 = b;
        c c5 = e;
        return c1.w.e(a4, 1.6f, 1.0f, c3, Arrays.asList(c4, c4, c5, c5), 0.0f, 0.0f);
    }

    public static u H() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.926f, 0.97f), new c(0.189f, 0.811f), null));
        arrayList.add(new b(new PointF(-0.021f, 0.967f), new c(0.187f, 0.057f), null));
        return v2.k.c(arrayList, 2, 0.5f, 0.5f, false);
    }

    public static u I() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.733f, 0.454f), null));
        arrayList.add(new b(new PointF(0.839f, 0.437f), new c(0.532f, 0.0f), null));
        arrayList.add(new b(new PointF(0.949f, 0.449f), new c(0.439f, 1.0f), null));
        arrayList.add(new b(new PointF(0.998f, 0.478f), new c(0.174f, 0.0f), null));
        return v2.k.c(arrayList, 16, 0.5f, 0.5f, true);
    }

    public static u J() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.193f, 0.277f), new c(0.053f, 0.0f), null));
        arrayList.add(new b(new PointF(0.176f, 0.055f), new c(0.053f, 0.0f), null));
        return v2.k.c(arrayList, 10, 0.5f, 0.5f, false);
    }

    public static u K() {
        return c1.w.e(c1.u.e, 1.0f, 1.0f, c, null, 0.0f, 0.0f);
    }

    public static u L() {
        return c1.w.f(c1.u.e, 8, 1.0f, 0.8f, a);
    }

    public static u M() {
        return c1.v.a(3, 1.0f, 0.0f, 0.0f, b);
    }

    public static u N(float f3) {
        return c1.x.c(v2.k.M(), v2.k.a(f3));
    }

    public static u O() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 1.08f), new c(0.085f, 0.0f), null));
        arrayList.add(new b(new PointF(0.358f, 0.843f), new c(0.085f, 0.0f), null));
        return v2.k.c(arrayList, 8, 0.5f, 0.5f, false);
    }

    public static u P(u u3, boolean bl) {
        return v2.k.Q(u3, bl, new RectF(0.0f, 0.0f, 1.0f, 1.0f));
    }

    public static u Q(u u3, boolean bl, RectF rectF) {
        Object object = new float[4];
        if (bl) {
            u3.d((float[])object);
        } else {
            u3.a((float[])object);
        }
        object = new RectF(object[0], object[1], object[2], object[3]);
        float f3 = Math.min(rectF.width() / object.width(), rectF.height() / object.height());
        Matrix matrix = v2.k.b(f3, f3);
        matrix.preTranslate(-object.centerX(), -object.centerY());
        matrix.postTranslate(rectF.centerX(), rectF.centerY());
        return c1.x.c(u3, matrix);
    }

    public static void R(List list, List list2, int n3, float f3, float f4, boolean bl) {
        int n4;
        list2.clear();
        v2.k.T(list, f3, f4);
        float f5 = (float)(Math.PI * 2 / (double)n3);
        if (bl) {
            float f6 = f5 / 2.0f;
            for (int i3 = 0; i3 < n3 * 2; ++i3) {
                for (n4 = 0; n4 < list.size(); ++n4) {
                    boolean bl2 = i3 % 2 != 0;
                    int n5 = bl2 ? list.size() - 1 - n4 : n4;
                    b b3 = (b)list.get(n5);
                    if (n5 <= 0 && bl2) continue;
                    float f7 = i3;
                    f5 = bl2 ? f6 - ((b)b3).a.x + ((b)((b)list.get((int)0))).a.x * 2.0f : ((b)b3).a.x;
                    list2.add(new b(new PointF(f7 * f6 + f5, ((b)b3).a.y), b3.b, null));
                }
            }
        } else {
            for (n4 = 0; n4 < n3; ++n4) {
                for (b b4 : list) {
                    list2.add(new b(new PointF((float)n4 * f5 + ((b)b4).a.x, ((b)b4).a.y), b4.b, null));
                }
            }
        }
        v2.k.S(list2, f3, f4);
    }

    public static void S(List object, float f3, float f4) {
        object = object.iterator();
        while (object.hasNext()) {
            ((b)object.next()).e(f3, f4);
        }
    }

    public static void T(List object, float f3, float f4) {
        object = object.iterator();
        while (object.hasNext()) {
            ((b)object.next()).f(f3, f4);
        }
    }

    public static List U(List list) {
        ArrayList<c> arrayList = new ArrayList<c>();
        for (int i3 = 0; i3 < list.size(); ++i3) {
            arrayList.add(((b)list.get(i3)).b);
        }
        return arrayList;
    }

    public static float[] V(List list) {
        float[] fArray = new float[list.size() * 2];
        for (int i3 = 0; i3 < list.size(); ++i3) {
            int n3 = i3 * 2;
            fArray[n3] = ((b)((b)list.get((int)i3))).a.x;
            fArray[n3 + 1] = ((b)((b)list.get((int)i3))).a.y;
        }
        return fArray;
    }

    public static Matrix a(float f3) {
        Matrix matrix = new Matrix();
        matrix.setRotate(f3);
        return matrix;
    }

    public static Matrix b(float f3, float f4) {
        Matrix matrix = new Matrix();
        matrix.setScale(f3, f4);
        return matrix;
    }

    public static u c(List object, int n3, float f3, float f4, boolean bl) {
        List list = new ArrayList();
        v2.k.R((List)object, list, n3, f3, f4, bl);
        object = v2.k.V(list);
        list = v2.k.U(list);
        return c1.v.c((float[])object, c1.c.d, list, f3, f4);
    }

    public static u d() {
        c c3 = c1.c.d;
        c c4 = e;
        c c5 = b;
        return c1.x.c(c1.v.b(4, 1.0f, 0.0f, 0.0f, c3, Arrays.asList(c4, c4, c5, c5)), v2.k.a(-135.0f));
    }

    public static u e() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 0.892f), new c(0.313f, 0.0f), null));
        arrayList.add(new b(new PointF(-0.216f, 1.05f), new c(0.207f, 0.0f), null));
        arrayList.add(new b(new PointF(0.499f, -0.16f), new c(0.215f, 1.0f), null));
        arrayList.add(new b(new PointF(1.225f, 1.06f), new c(0.211f, 0.0f), null));
        return v2.k.c(arrayList, 1, 0.5f, 0.5f, false);
    }

    public static u f() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.457f, 0.296f), new c(0.007f, 0.0f), null));
        arrayList.add(new b(new PointF(0.5f, -0.051f), new c(0.007f, 0.0f), null));
        return v2.k.c(arrayList, 15, 0.5f, 0.5f, false);
    }

    public static u g() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.796f, 0.5f), null));
        PointF pointF = new PointF(0.853f, 0.518f);
        c c3 = e;
        arrayList.add(new b(pointF, c3, null));
        arrayList.add(new b(new PointF(0.992f, 0.631f), c3, null));
        arrayList.add(new b(new PointF(0.968f, 1.0f), c3, null));
        return v2.k.c(arrayList, 2, 0.5f, 0.5f, true);
    }

    public static u h() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, -0.006f), new c(0.006f, 0.0f), null));
        arrayList.add(new b(new PointF(0.592f, 0.158f), new c(0.006f, 0.0f), null));
        return v2.k.c(arrayList, 12, 0.5f, 0.5f, false);
    }

    public static u i() {
        return c1.w.b(c1.u.e, 10);
    }

    public static u j() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.171f, 0.841f), new c(0.159f, 0.0f), null));
        arrayList.add(new b(new PointF(-0.02f, 0.5f), new c(0.14f, 0.0f), null));
        arrayList.add(new b(new PointF(0.17f, 0.159f), new c(0.159f, 0.0f), null));
        return v2.k.c(arrayList, 2, 0.5f, 0.5f, false);
    }

    public static u k() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 0.074f), null));
        arrayList.add(new b(new PointF(0.725f, -0.099f), new c(0.476f, 0.0f), null));
        return v2.k.c(arrayList, 4, 0.5f, 0.5f, true);
    }

    public static u l() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 0.036f), null));
        arrayList.add(new b(new PointF(0.758f, -0.101f), new c(0.209f, 0.0f), null));
        return v2.k.c(arrayList, 8, 0.5f, 0.5f, false);
    }

    public static u m() {
        return c1.x.c(c1.w.f(c1.u.e, 12, 1.0f, 0.8f, d), v2.k.a(-90.0f));
    }

    public static u n() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(1.237f, 1.236f), new c(0.258f, 0.0f), null));
        arrayList.add(new b(new PointF(0.5f, 0.918f), new c(0.233f, 0.0f), null));
        return v2.k.c(arrayList, 4, 0.5f, 0.5f, false);
    }

    public static u o() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.723f, 0.884f), new c(0.394f, 0.0f), null));
        arrayList.add(new b(new PointF(0.5f, 1.099f), new c(0.398f, 0.0f), null));
        return v2.k.c(arrayList, 6, 0.5f, 0.5f, false);
    }

    public static u p() {
        return c1.x.c(c1.w.f(c1.u.e, 7, 1.0f, 0.75f, d), v2.k.a(-90.0f));
    }

    public static u q() {
        return c1.x.c(c1.w.f(c1.u.e, 9, 1.0f, 0.8f, d), v2.k.a(-90.0f));
    }

    public static u r() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 1.096f), new c(0.151f, 0.524f), null));
        arrayList.add(new b(new PointF(0.04f, 0.5f), new c(0.159f, 0.0f), null));
        return v2.k.c(arrayList, 2, 0.5f, 0.5f, false);
    }

    public static u s() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(1.0f, 1.0f), new c(0.148f, 0.417f), null));
        arrayList.add(new b(new PointF(0.0f, 1.0f), new c(0.151f, 0.0f), null));
        arrayList.add(new b(new PointF(0.0f, 0.0f), new c(0.148f, 0.0f), null));
        arrayList.add(new b(new PointF(0.978f, 0.02f), new c(0.803f, 0.0f), null));
        return v2.k.c(arrayList, 1, 0.5f, 0.5f, false);
    }

    public static u t() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.37f, 0.187f), null));
        arrayList.add(new b(new PointF(0.416f, 0.049f), new c(0.381f, 0.0f), null));
        arrayList.add(new b(new PointF(0.479f, 0.0f), new c(0.095f, 0.0f), null));
        return v2.k.c(arrayList, 8, 0.5f, 0.5f, true);
    }

    public static u u() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.499f, 1.023f), new c(0.241f, 0.778f), null));
        arrayList.add(new b(new PointF(-0.005f, 0.792f), new c(0.208f, 0.0f), null));
        arrayList.add(new b(new PointF(0.073f, 0.258f), new c(0.228f, 0.0f), null));
        arrayList.add(new b(new PointF(0.433f, 0.0f), new c(0.491f, 0.0f), null));
        return v2.k.c(arrayList, 1, 0.5f, 0.5f, true);
    }

    public static u v(float f3) {
        return c1.x.c(v2.k.u(), v2.k.a(f3));
    }

    public static u w() {
        ArrayList<b> arrayList = new ArrayList<b>();
        PointF pointF = new PointF(0.5f, 0.0f);
        c c3 = e;
        arrayList.add(new b(pointF, c3, null));
        arrayList.add(new b(new PointF(1.0f, 0.0f), c3, null));
        arrayList.add(new b(new PointF(1.0f, 1.14f), new c(0.254f, 0.106f), null));
        arrayList.add(new b(new PointF(0.575f, 0.906f), new c(0.253f, 0.0f), null));
        return v2.k.c(arrayList, 1, 0.5f, 0.5f, true);
    }

    public static u x() {
        ArrayList<b> arrayList = new ArrayList<b>();
        arrayList.add(new b(new PointF(0.5f, 0.268f), new c(0.016f, 0.0f), null));
        arrayList.add(new b(new PointF(0.792f, -0.066f), new c(0.958f, 0.0f), null));
        arrayList.add(new b(new PointF(1.064f, 0.276f), e, null));
        arrayList.add(new b(new PointF(0.501f, 0.946f), new c(0.129f, 0.0f), null));
        return v2.k.c(arrayList, 1, 0.5f, 0.5f, true);
    }

    public static u y() {
        return c1.x.c(c1.w.a(c1.u.e), v2.k.b(1.0f, 0.64f));
    }

    public static u z(float f3) {
        return c1.x.c(v2.k.y(), v2.k.a(f3));
    }

    public static class b {
        public PointF a;
        public c b;

        public b(PointF pointF) {
            this(pointF, c1.c.d);
        }

        public b(PointF pointF, c c3) {
            this.a = pointF;
            this.b = c3;
        }

        public /* synthetic */ b(PointF pointF, c c3, a a4) {
            this(pointF, c3);
        }

        public /* synthetic */ b(PointF pointF, a a4) {
            this(pointF);
        }

        public final void e(float f3, float f4) {
            PointF pointF = this.a;
            f3 = (float)((double)pointF.y * Math.cos(pointF.x) + (double)f3);
            pointF = this.a;
            f4 = (float)((double)pointF.y * Math.sin(pointF.x) + (double)f4);
            pointF = this.a;
            pointF.x = f3;
            pointF.y = f4;
        }

        public final void f(float f3, float f4) {
            this.a.offset(-f3, -f4);
            PointF pointF = this.a;
            f3 = (float)Math.atan2(pointF.y, pointF.x);
            pointF = this.a;
            f4 = (float)Math.hypot(pointF.x, pointF.y);
            pointF = this.a;
            pointF.x = f3;
            pointF.y = f4;
        }
    }
}

