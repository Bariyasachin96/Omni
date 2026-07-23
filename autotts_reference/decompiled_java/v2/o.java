/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 */
package v2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import v2.d;
import v2.e;
import v2.f;
import v2.g;
import v2.j;
import v2.m;
import v2.n;

public class o {
    public static final d m = new m(0.5f);
    public e a;
    public e b;
    public e c;
    public e d;
    public d e;
    public d f;
    public d g;
    public d h;
    public g i;
    public g j;
    public g k;
    public g l;

    public o() {
        this.a = v2.j.b();
        this.b = v2.j.b();
        this.c = v2.j.b();
        this.d = v2.j.b();
        this.e = new v2.a(0.0f);
        this.f = new v2.a(0.0f);
        this.g = new v2.a(0.0f);
        this.h = new v2.a(0.0f);
        this.i = v2.j.c();
        this.j = v2.j.c();
        this.k = v2.j.c();
        this.l = v2.j.c();
    }

    public o(b b3) {
        this.a = b3.a;
        this.b = b3.b;
        this.c = b3.c;
        this.d = b3.d;
        this.e = b3.e;
        this.f = b3.f;
        this.g = b3.g;
        this.h = b3.h;
        this.i = b3.i;
        this.j = b3.j;
        this.k = b3.k;
        this.l = b3.l;
    }

    public /* synthetic */ o(b b3, a a4) {
        this(b3);
    }

    public static b a() {
        return new b();
    }

    public static b b(Context context, int n3, int n4) {
        return o.c(context, n3, n4, 0);
    }

    public static b c(Context context, int n3, int n4, int n5) {
        return o.d(context, n3, n4, new v2.a(n5));
    }

    public static b d(Context context, int n3, int n4, d object) {
        context = new ContextThemeWrapper(context, n3);
        if (n4 != 0) {
            context.getTheme().applyStyle(n4, true);
        }
        context = context.obtainStyledAttributes(z1.m.ShapeAppearance);
        try {
            int n5 = context.getInt(z1.m.ShapeAppearance_cornerFamily, 0);
            n4 = context.getInt(z1.m.ShapeAppearance_cornerFamilyTopLeft, n5);
            int n6 = context.getInt(z1.m.ShapeAppearance_cornerFamilyTopRight, n5);
            n3 = context.getInt(z1.m.ShapeAppearance_cornerFamilyBottomRight, n5);
            n5 = context.getInt(z1.m.ShapeAppearance_cornerFamilyBottomLeft, n5);
            d d3 = o.m((TypedArray)context, z1.m.ShapeAppearance_cornerSize, (d)object);
            d d4 = o.m((TypedArray)context, z1.m.ShapeAppearance_cornerSizeTopLeft, d3);
            d d5 = o.m((TypedArray)context, z1.m.ShapeAppearance_cornerSizeTopRight, d3);
            object = o.m((TypedArray)context, z1.m.ShapeAppearance_cornerSizeBottomRight, d3);
            d3 = o.m((TypedArray)context, z1.m.ShapeAppearance_cornerSizeBottomLeft, d3);
            b b3 = new b();
            object = b3.C(n4, d4).G(n6, d5).x(n3, (d)object).t(n5, d3);
            return object;
        }
        finally {
            context.recycle();
        }
    }

    public static b e(Context context, AttributeSet attributeSet, int n3, int n4) {
        return o.f(context, attributeSet, n3, n4, 0);
    }

    public static b f(Context context, AttributeSet attributeSet, int n3, int n4, int n5) {
        return o.g(context, attributeSet, n3, n4, new v2.a(n5));
    }

    public static b g(Context context, AttributeSet attributeSet, int n3, int n4, d d3) {
        attributeSet = context.obtainStyledAttributes(attributeSet, z1.m.MaterialShape, n3, n4);
        n4 = attributeSet.getResourceId(z1.m.MaterialShape_shapeAppearance, 0);
        n3 = attributeSet.getResourceId(z1.m.MaterialShape_shapeAppearanceOverlay, 0);
        attributeSet.recycle();
        return o.d(context, n4, n3, d3);
    }

    public static d m(TypedArray typedArray, int n3, d d3) {
        TypedValue typedValue = typedArray.peekValue(n3);
        if (typedValue != null) {
            n3 = typedValue.type;
            if (n3 == 5) {
                return new v2.a(TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)typedArray.getResources().getDisplayMetrics()));
            }
            if (n3 == 6) {
                return new m(typedValue.getFraction(1.0f, 1.0f));
            }
        }
        return d3;
    }

    public g h() {
        return this.k;
    }

    public e i() {
        return this.d;
    }

    public d j() {
        return this.h;
    }

    public e k() {
        return this.c;
    }

    public d l() {
        return this.g;
    }

    public g n() {
        return this.l;
    }

    public g o() {
        return this.j;
    }

    public g p() {
        return this.i;
    }

    public e q() {
        return this.a;
    }

    public d r() {
        return this.e;
    }

    public e s() {
        return this.b;
    }

    public d t() {
        return this.f;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.r());
        stringBuilder.append(", ");
        stringBuilder.append(this.t());
        stringBuilder.append(", ");
        stringBuilder.append(this.l());
        stringBuilder.append(", ");
        stringBuilder.append(this.j());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public boolean u() {
        return this.b instanceof n && this.a instanceof n && this.c instanceof n && this.d instanceof n;
    }

    public boolean v(RectF rectF) {
        boolean bl = this.l.getClass().equals(g.class) && this.j.getClass().equals(g.class) && this.i.getClass().equals(g.class) && this.k.getClass().equals(g.class);
        float f3 = this.e.a(rectF);
        boolean bl2 = this.f.a(rectF) == f3 && this.h.a(rectF) == f3 && this.g.a(rectF) == f3;
        return bl && bl2 && this.u();
    }

    public b w() {
        return new b(this);
    }

    public o x(float f3) {
        return this.w().o(f3).m();
    }

    public o y(d d3) {
        return this.w().p(d3).m();
    }

    public o z(c c3) {
        return this.w().F(c3.a(this.r())).J(c3.a(this.t())).w(c3.a(this.j())).A(c3.a(this.l())).m();
    }

    public static final class b {
        public e a = v2.j.b();
        public e b = v2.j.b();
        public e c = v2.j.b();
        public e d = v2.j.b();
        public d e = new v2.a(0.0f);
        public d f = new v2.a(0.0f);
        public d g = new v2.a(0.0f);
        public d h = new v2.a(0.0f);
        public g i = v2.j.c();
        public g j = v2.j.c();
        public g k = v2.j.c();
        public g l = v2.j.c();

        public b() {
        }

        public b(o o3) {
            this.a = o3.a;
            this.b = o3.b;
            this.c = o3.c;
            this.d = o3.d;
            this.e = o3.e;
            this.f = o3.f;
            this.g = o3.g;
            this.h = o3.h;
            this.i = o3.i;
            this.j = o3.j;
            this.k = o3.k;
            this.l = o3.l;
        }

        public static float n(e e3) {
            if (e3 instanceof n) {
                return ((n)e3).a;
            }
            if (e3 instanceof f) {
                return ((f)e3).a;
            }
            return -1.0f;
        }

        public b A(d d3) {
            this.g = d3;
            return this;
        }

        public b B(g g3) {
            this.i = g3;
            return this;
        }

        public b C(int n3, d d3) {
            return this.D(v2.j.a(n3)).F(d3);
        }

        public b D(e e3) {
            this.a = e3;
            float f3 = v2.o$b.n(e3);
            if (f3 != -1.0f) {
                this.E(f3);
            }
            return this;
        }

        public b E(float f3) {
            this.e = new v2.a(f3);
            return this;
        }

        public b F(d d3) {
            this.e = d3;
            return this;
        }

        public b G(int n3, d d3) {
            return this.H(v2.j.a(n3)).J(d3);
        }

        public b H(e e3) {
            this.b = e3;
            float f3 = v2.o$b.n(e3);
            if (f3 != -1.0f) {
                this.I(f3);
            }
            return this;
        }

        public b I(float f3) {
            this.f = new v2.a(f3);
            return this;
        }

        public b J(d d3) {
            this.f = d3;
            return this;
        }

        public o m() {
            return new o(this, null);
        }

        public b o(float f3) {
            return this.E(f3).I(f3).z(f3).v(f3);
        }

        public b p(d d3) {
            return this.F(d3).J(d3).A(d3).w(d3);
        }

        public b q(int n3, float f3) {
            return this.r(v2.j.a(n3)).o(f3);
        }

        public b r(e e3) {
            return this.D(e3).H(e3).y(e3).u(e3);
        }

        public b s(g g3) {
            this.k = g3;
            return this;
        }

        public b t(int n3, d d3) {
            return this.u(v2.j.a(n3)).w(d3);
        }

        public b u(e e3) {
            this.d = e3;
            float f3 = v2.o$b.n(e3);
            if (f3 != -1.0f) {
                this.v(f3);
            }
            return this;
        }

        public b v(float f3) {
            this.h = new v2.a(f3);
            return this;
        }

        public b w(d d3) {
            this.h = d3;
            return this;
        }

        public b x(int n3, d d3) {
            return this.y(v2.j.a(n3)).A(d3);
        }

        public b y(e e3) {
            this.c = e3;
            float f3 = v2.o$b.n(e3);
            if (f3 != -1.0f) {
                this.z(f3);
            }
            return this;
        }

        public b z(float f3) {
            this.g = new v2.a(f3);
            return this;
        }
    }

    public static interface c {
        public d a(d var1);
    }
}

