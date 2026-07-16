/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 *  android.view.WindowInsets
 *  android.view.WindowInsets$Builder
 */
package o0;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import o0.a2;
import o0.b2;
import o0.c2;
import o0.d2;
import o0.e2;
import o0.f2;
import o0.g2;
import o0.h2;
import o0.i2;
import o0.j2;
import o0.k2;
import o0.l2;
import o0.m2;
import o0.n0;
import o0.n2;
import o0.o2;
import o0.p2;
import o0.q2;
import o0.r;
import o0.r2;
import o0.s2;
import o0.t2;
import o0.u2;
import o0.v2;
import o0.w2;
import o0.x0;
import o0.x2;

public class z1 {
    public static final z1 b = Build.VERSION.SDK_INT >= 30 ? k.q : l.b;
    public final l a;

    public z1(WindowInsets windowInsets) {
        int n3 = Build.VERSION.SDK_INT;
        if (n3 >= 30) {
            this.a = new k(this, windowInsets);
            return;
        }
        if (n3 >= 29) {
            this.a = new j(this, windowInsets);
            return;
        }
        if (n3 >= 28) {
            this.a = new i(this, windowInsets);
            return;
        }
        this.a = new h(this, windowInsets);
    }

    public z1(z1 object) {
        if (object != null) {
            object = ((z1)object).a;
            int n3 = Build.VERSION.SDK_INT;
            this.a = n3 >= 30 && object instanceof k ? new k(this, (k)object) : (n3 >= 29 && object instanceof j ? new j(this, (j)object) : (n3 >= 28 && object instanceof i ? new i(this, (i)object) : (object instanceof h ? new h(this, (h)object) : (object instanceof g ? new g(this, (g)object) : new l(this)))));
            ((l)object).e(this);
            return;
        }
        this.a = new l(this);
    }

    public static g0.b o(g0.b b3, int n3, int n4, int n5, int n6) {
        int n7 = Math.max(0, b3.a - n3);
        int n8 = Math.max(0, b3.b - n4);
        int n9 = Math.max(0, b3.c - n5);
        int n10 = Math.max(0, b3.d - n6);
        if (n7 == n3 && n8 == n4 && n9 == n5 && n10 == n6) {
            return b3;
        }
        return g0.b.b(n7, n8, n9, n10);
    }

    public static z1 w(WindowInsets windowInsets) {
        return z1.x(windowInsets, null);
    }

    public static z1 x(WindowInsets object, View view) {
        object = new z1((WindowInsets)n0.h.g(object));
        if (view != null && view.isAttachedToWindow()) {
            ((z1)object).t(x0.D(view));
            ((z1)object).d(view.getRootView());
        }
        return object;
    }

    public z1 a() {
        return this.a.a();
    }

    public z1 b() {
        return this.a.b();
    }

    public z1 c() {
        return this.a.c();
    }

    public void d(View view) {
        this.a.d(view);
    }

    public r e() {
        return this.a.f();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof z1)) {
            return false;
        }
        object = (z1)object;
        return n0.c.a(this.a, ((z1)object).a);
    }

    public g0.b f(int n3) {
        return this.a.g(n3);
    }

    public g0.b g() {
        return this.a.i();
    }

    public g0.b h() {
        return this.a.j();
    }

    public int hashCode() {
        l l3 = this.a;
        if (l3 == null) {
            return 0;
        }
        return l3.hashCode();
    }

    public int i() {
        return this.a.k().d;
    }

    public int j() {
        return this.a.k().a;
    }

    public int k() {
        return this.a.k().c;
    }

    public int l() {
        return this.a.k().b;
    }

    public boolean m() {
        return this.a.k().equals(g0.b.e) ^ true;
    }

    public z1 n(int n3, int n4, int n5, int n6) {
        return this.a.m(n3, n4, n5, n6);
    }

    public boolean p() {
        return this.a.n();
    }

    public z1 q(int n3, int n4, int n5, int n6) {
        return new b(this).d(g0.b.b(n3, n4, n5, n6)).a();
    }

    public void r(g0.b[] bArray) {
        this.a.p(bArray);
    }

    public void s(g0.b b3) {
        this.a.q(b3);
    }

    public void t(z1 z12) {
        this.a.r(z12);
    }

    public void u(g0.b b3) {
        this.a.s(b3);
    }

    public WindowInsets v() {
        l l3 = this.a;
        if (l3 instanceof g) {
            return ((g)l3).c;
        }
        return null;
    }

    public static abstract class a {
        public static Field a;
        public static Field b;
        public static Field c;
        public static boolean d;

        static {
            try {
                Field field;
                a = field = View.class.getDeclaredField("mAttachInfo");
                ((AccessibleObject)field).setAccessible(true);
                Class<?> clazz = Class.forName("android.view.View$AttachInfo");
                b = field = clazz.getDeclaredField("mStableInsets");
                ((AccessibleObject)field).setAccessible(true);
                c = field = clazz.getDeclaredField("mContentInsets");
                ((AccessibleObject)field).setAccessible(true);
                d = true;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to get visible insets from AttachInfo ");
                stringBuilder.append(reflectiveOperationException.getMessage());
                Log.w((String)"WindowInsetsCompat", (String)stringBuilder.toString(), (Throwable)reflectiveOperationException);
            }
        }

        public static z1 a(View view) {
            block5: {
                if (d && view.isAttachedToWindow()) {
                    Object object = view.getRootView();
                    Object object2 = a.get(object);
                    if (object2 == null) break block5;
                    object = (Rect)b.get(object2);
                    Rect rect = (Rect)c.get(object2);
                    if (object == null || rect == null) break block5;
                    try {
                        object2 = new b();
                        object = ((b)object2).c(g0.b.c((Rect)object)).d(g0.b.c(rect)).a();
                        ((z1)object).t((z1)object);
                        ((z1)object).d(view.getRootView());
                        return object;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Failed to get insets from AttachInfo. ");
                        ((StringBuilder)object).append(illegalAccessException.getMessage());
                        Log.w((String)"WindowInsetsCompat", (String)((StringBuilder)object).toString(), (Throwable)illegalAccessException);
                    }
                }
            }
            return null;
        }
    }

    public static final class b {
        public final f a;

        public b() {
            int n3 = Build.VERSION.SDK_INT;
            if (n3 >= 30) {
                this.a = new e();
                return;
            }
            if (n3 >= 29) {
                this.a = new d();
                return;
            }
            this.a = new c();
        }

        public b(z1 z12) {
            int n3 = Build.VERSION.SDK_INT;
            if (n3 >= 30) {
                this.a = new e(z12);
                return;
            }
            if (n3 >= 29) {
                this.a = new d(z12);
                return;
            }
            this.a = new c(z12);
        }

        public z1 a() {
            return this.a.b();
        }

        public b b(int n3, g0.b b3) {
            this.a.c(n3, b3);
            return this;
        }

        public b c(g0.b b3) {
            this.a.e(b3);
            return this;
        }

        public b d(g0.b b3) {
            this.a.g(b3);
            return this;
        }
    }

    public static class c
    extends f {
        public static Field e;
        public static boolean f = false;
        public static Constructor g;
        public static boolean h = false;
        public WindowInsets c;
        public g0.b d;

        public c() {
            this.c = o0.z1$c.i();
        }

        public c(z1 z12) {
            super(z12);
            this.c = z12.v();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private static WindowInsets i() {
            AccessibleObject accessibleObject;
            if (!f) {
                try {
                    e = WindowInsets.class.getDeclaredField("CONSUMED");
                }
                catch (ReflectiveOperationException reflectiveOperationException) {}
                f = true;
            }
            if ((accessibleObject = e) != null) {
                try {
                    accessibleObject = (WindowInsets)((Field)accessibleObject).get(null);
                    if (accessibleObject != null) {
                        return new WindowInsets((WindowInsets)accessibleObject);
                    }
                }
                catch (ReflectiveOperationException reflectiveOperationException) {}
            }
            if (!h) {
                try {
                    g = WindowInsets.class.getConstructor(Rect.class);
                }
                catch (ReflectiveOperationException reflectiveOperationException) {}
                h = true;
            }
            if ((accessibleObject = g) == null) return null;
            try {
                Rect rect = new Rect();
                return (WindowInsets)((Constructor)accessibleObject).newInstance(rect);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                return null;
            }
        }

        @Override
        public z1 b() {
            this.a();
            z1 z12 = z1.w(this.c);
            z12.r(this.b);
            z12.u(this.d);
            return z12;
        }

        @Override
        public void e(g0.b b3) {
            this.d = b3;
        }

        @Override
        public void g(g0.b b3) {
            WindowInsets windowInsets = this.c;
            if (windowInsets != null) {
                this.c = windowInsets.replaceSystemWindowInsets(b3.a, b3.b, b3.c, b3.d);
            }
        }
    }

    public static class d
    extends f {
        public final WindowInsets.Builder c;

        public d() {
            this.c = h2.a();
        }

        public d(z1 z12) {
            super(z12);
            z12 = z12.v();
            z12 = z12 != null ? g2.a((WindowInsets)z12) : h2.a();
            this.c = z12;
        }

        @Override
        public z1 b() {
            this.a();
            z1 z12 = z1.w(d2.a(this.c));
            z12.r(this.b);
            return z12;
        }

        @Override
        public void d(g0.b b3) {
            e2.a(this.c, b3.e());
        }

        @Override
        public void e(g0.b b3) {
            b2.a(this.c, b3.e());
        }

        @Override
        public void f(g0.b b3) {
            c2.a(this.c, b3.e());
        }

        @Override
        public void g(g0.b b3) {
            a2.a(this.c, b3.e());
        }

        @Override
        public void h(g0.b b3) {
            f2.a(this.c, b3.e());
        }
    }

    public static class e
    extends d {
        public e() {
        }

        public e(z1 z12) {
            super(z12);
        }

        @Override
        public void c(int n3, g0.b b3) {
            i2.a(this.c, n.a(n3), b3.e());
        }
    }

    public static abstract class f {
        public final z1 a;
        public g0.b[] b;

        public f() {
            this(new z1(null));
        }

        public f(z1 z12) {
            this.a = z12;
        }

        public final void a() {
            Object object = this.b;
            if (object != null) {
                g0.b b3 = object[m.c(1)];
                g0.b b4 = this.b[m.c(2)];
                object = b4;
                if (b4 == null) {
                    object = this.a.f(2);
                }
                b4 = b3;
                if (b3 == null) {
                    b4 = this.a.f(1);
                }
                this.g(g0.b.a(b4, (g0.b)object));
                object = this.b[m.c(16)];
                if (object != null) {
                    this.f((g0.b)object);
                }
                if ((object = this.b[m.c(32)]) != null) {
                    this.d((g0.b)object);
                }
                if ((object = this.b[m.c(64)]) != null) {
                    this.h((g0.b)object);
                }
            }
        }

        public abstract z1 b();

        public void c(int n3, g0.b b3) {
            if (this.b == null) {
                this.b = new g0.b[9];
            }
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((n3 & i3) == 0) continue;
                this.b[m.c((int)i3)] = b3;
            }
        }

        public void d(g0.b b3) {
        }

        public abstract void e(g0.b var1);

        public void f(g0.b b3) {
        }

        public abstract void g(g0.b var1);

        public void h(g0.b b3) {
        }
    }

    public static class g
    extends l {
        public static boolean h = false;
        public static Method i;
        public static Class j;
        public static Field k;
        public static Field l;
        public final WindowInsets c;
        public g0.b[] d;
        public g0.b e = null;
        public z1 f;
        public g0.b g;

        public g(z1 z12, WindowInsets windowInsets) {
            super(z12);
            this.c = windowInsets;
        }

        public g(z1 z12, g g3) {
            this(z12, new WindowInsets(g3.c));
        }

        private g0.b t(int n3, boolean bl) {
            g0.b b3 = g0.b.e;
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((n3 & i3) == 0) continue;
                b3 = g0.b.a(b3, this.u(i3, bl));
            }
            return b3;
        }

        private g0.b v() {
            z1 z12 = this.f;
            if (z12 != null) {
                return z12.g();
            }
            return g0.b.e;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private g0.b w(View object) {
            ReflectiveOperationException reflectiveOperationException2;
            Object object2;
            block5: {
                if (Build.VERSION.SDK_INT >= 30) throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
                if (!h) {
                    o0.z1$g.x();
                }
                if ((object2 = i) == null) return null;
                if (j == null) return null;
                if (k == null) {
                    return null;
                }
                try {
                    object = ((Method)object2).invoke(object, null);
                    if (object == null) {
                        object = new NullPointerException();
                        Log.w((String)"WindowInsetsCompat", (String)"Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", (Throwable)object);
                        return null;
                    }
                }
                catch (ReflectiveOperationException reflectiveOperationException2) {
                    break block5;
                }
                object = l.get(object);
                if ((object = (Rect)k.get(object)) == null) return null;
                return g0.b.c((Rect)object);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to get visible insets. (Reflection error). ");
            ((StringBuilder)object2).append(reflectiveOperationException2.getMessage());
            Log.e((String)"WindowInsetsCompat", (String)((StringBuilder)object2).toString(), (Throwable)reflectiveOperationException2);
            return null;
        }

        private static void x() {
            try {
                Class<?> clazz;
                i = View.class.getDeclaredMethod("getViewRootImpl", null);
                j = clazz = Class.forName("android.view.View$AttachInfo");
                k = clazz.getDeclaredField("mVisibleInsets");
                l = Class.forName("android.view.ViewRootImpl").getDeclaredField("mAttachInfo");
                ((AccessibleObject)k).setAccessible(true);
                ((AccessibleObject)l).setAccessible(true);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to get visible insets. (Reflection error). ");
                stringBuilder.append(reflectiveOperationException.getMessage());
                Log.e((String)"WindowInsetsCompat", (String)stringBuilder.toString(), (Throwable)reflectiveOperationException);
            }
            h = true;
        }

        @Override
        public void d(View object) {
            g0.b b3 = this.w((View)object);
            object = b3;
            if (b3 == null) {
                object = g0.b.e;
            }
            this.q((g0.b)object);
        }

        @Override
        public void e(z1 z12) {
            z12.t(this.f);
            z12.s(this.g);
        }

        @Override
        public boolean equals(Object object) {
            if (!super.equals(object)) {
                return false;
            }
            object = (g)object;
            return Objects.equals(this.g, ((g)object).g);
        }

        @Override
        public g0.b g(int n3) {
            return this.t(n3, false);
        }

        @Override
        public final g0.b k() {
            if (this.e == null) {
                this.e = g0.b.b(this.c.getSystemWindowInsetLeft(), this.c.getSystemWindowInsetTop(), this.c.getSystemWindowInsetRight(), this.c.getSystemWindowInsetBottom());
            }
            return this.e;
        }

        @Override
        public z1 m(int n3, int n4, int n5, int n6) {
            b b3 = new b(z1.w(this.c));
            b3.d(z1.o(this.k(), n3, n4, n5, n6));
            b3.c(z1.o(this.i(), n3, n4, n5, n6));
            return b3.a();
        }

        @Override
        public boolean o() {
            return this.c.isRound();
        }

        @Override
        public void p(g0.b[] bArray) {
            this.d = bArray;
        }

        @Override
        public void q(g0.b b3) {
            this.g = b3;
        }

        @Override
        public void r(z1 z12) {
            this.f = z12;
        }

        public g0.b u(int n3, boolean bl) {
            if (n3 != 1) {
                int n4;
                Object object = null;
                g0.b b3 = null;
                if (n3 != 2) {
                    if (n3 != 8) {
                        if (n3 != 16) {
                            if (n3 != 32) {
                                if (n3 != 64) {
                                    if (n3 != 128) {
                                        return g0.b.e;
                                    }
                                    object = this.f;
                                    object = object != null ? ((z1)object).e() : this.f();
                                    if (object != null) {
                                        return g0.b.b(((r)object).b(), ((r)object).d(), ((r)object).c(), ((r)object).a());
                                    }
                                    return g0.b.e;
                                }
                                return this.l();
                            }
                            return this.h();
                        }
                        return this.j();
                    }
                    g0.b[] bArray = this.d;
                    object = b3;
                    if (bArray != null) {
                        object = bArray[m.c(8)];
                    }
                    if (object != null) {
                        return object;
                    }
                    b3 = this.k();
                    object = this.v();
                    n3 = b3.d;
                    if (n3 > ((g0.b)object).d) {
                        return g0.b.b(0, 0, 0, n3);
                    }
                    b3 = this.g;
                    if (b3 != null && !b3.equals(g0.b.e) && (n3 = this.g.d) > ((g0.b)object).d) {
                        return g0.b.b(0, 0, 0, n3);
                    }
                    return g0.b.e;
                }
                if (bl) {
                    b3 = this.v();
                    object = this.i();
                    return g0.b.b(Math.max(b3.a, ((g0.b)object).a), 0, Math.max(b3.c, ((g0.b)object).c), Math.max(b3.d, ((g0.b)object).d));
                }
                b3 = this.k();
                z1 z12 = this.f;
                if (z12 != null) {
                    object = z12.g();
                }
                n3 = n4 = b3.d;
                if (object != null) {
                    n3 = Math.min(n4, ((g0.b)object).d);
                }
                return g0.b.b(b3.a, 0, b3.c, n3);
            }
            if (bl) {
                return g0.b.b(0, Math.max(this.v().b, this.k().b), 0, 0);
            }
            return g0.b.b(0, this.k().b, 0, 0);
        }
    }

    public static class h
    extends g {
        public g0.b m = null;

        public h(z1 z12, WindowInsets windowInsets) {
            super(z12, windowInsets);
        }

        public h(z1 z12, h h3) {
            super(z12, h3);
            this.m = h3.m;
        }

        @Override
        public z1 b() {
            return z1.w(this.c.consumeStableInsets());
        }

        @Override
        public z1 c() {
            return z1.w(this.c.consumeSystemWindowInsets());
        }

        @Override
        public final g0.b i() {
            if (this.m == null) {
                this.m = g0.b.b(this.c.getStableInsetLeft(), this.c.getStableInsetTop(), this.c.getStableInsetRight(), this.c.getStableInsetBottom());
            }
            return this.m;
        }

        @Override
        public boolean n() {
            return this.c.isConsumed();
        }

        @Override
        public void s(g0.b b3) {
            this.m = b3;
        }
    }

    public static class i
    extends h {
        public i(z1 z12, WindowInsets windowInsets) {
            super(z12, windowInsets);
        }

        public i(z1 z12, i i3) {
            super(z12, i3);
        }

        @Override
        public z1 a() {
            return z1.w(k2.a(this.c));
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof i)) {
                return false;
            }
            object = (i)object;
            return Objects.equals(this.c, ((g)object).c) && Objects.equals(this.g, ((g)object).g);
        }

        @Override
        public r f() {
            return r.e(j2.a(this.c));
        }

        @Override
        public int hashCode() {
            return this.c.hashCode();
        }
    }

    public static class j
    extends i {
        public g0.b n = null;
        public g0.b o = null;
        public g0.b p = null;

        public j(z1 z12, WindowInsets windowInsets) {
            super(z12, windowInsets);
        }

        public j(z1 z12, j j3) {
            super(z12, j3);
        }

        @Override
        public g0.b h() {
            if (this.o == null) {
                this.o = g0.b.d(n2.a(this.c));
            }
            return this.o;
        }

        @Override
        public g0.b j() {
            if (this.n == null) {
                this.n = g0.b.d(o2.a(this.c));
            }
            return this.n;
        }

        @Override
        public g0.b l() {
            if (this.p == null) {
                this.p = g0.b.d(l2.a(this.c));
            }
            return this.p;
        }

        @Override
        public z1 m(int n3, int n4, int n5, int n6) {
            return z1.w(m2.a(this.c, n3, n4, n5, n6));
        }

        @Override
        public void s(g0.b b3) {
        }
    }

    public static class k
    extends j {
        public static final z1 q = z1.w(q2.a());

        public k(z1 z12, WindowInsets windowInsets) {
            super(z12, windowInsets);
        }

        public k(z1 z12, k k3) {
            super(z12, k3);
        }

        @Override
        public final void d(View view) {
        }

        @Override
        public g0.b g(int n3) {
            return g0.b.d(p2.a(this.c, n.a(n3)));
        }
    }

    public static class l {
        public static final z1 b = new b().a().a().b().c();
        public final z1 a;

        public l(z1 z12) {
            this.a = z12;
        }

        public z1 a() {
            return this.a;
        }

        public z1 b() {
            return this.a;
        }

        public z1 c() {
            return this.a;
        }

        public void d(View view) {
        }

        public void e(z1 z12) {
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof l)) {
                return false;
            }
            object = (l)object;
            return this.o() == ((l)object).o() && this.n() == ((l)object).n() && n0.c.a(this.k(), ((l)object).k()) && n0.c.a(this.i(), ((l)object).i()) && n0.c.a(this.f(), ((l)object).f());
        }

        public r f() {
            return null;
        }

        public g0.b g(int n3) {
            return g0.b.e;
        }

        public g0.b h() {
            return this.k();
        }

        public int hashCode() {
            return n0.c.b(this.o(), this.n(), this.k(), this.i(), this.f());
        }

        public g0.b i() {
            return g0.b.e;
        }

        public g0.b j() {
            return this.k();
        }

        public g0.b k() {
            return g0.b.e;
        }

        public g0.b l() {
            return this.k();
        }

        public z1 m(int n3, int n4, int n5, int n6) {
            return b;
        }

        public boolean n() {
            return false;
        }

        public boolean o() {
            return false;
        }

        public void p(g0.b[] bArray) {
        }

        public void q(g0.b b3) {
        }

        public void r(z1 z12) {
        }

        public void s(g0.b b3) {
        }
    }

    public static final abstract class m {
        public static int a() {
            return 128;
        }

        public static int b() {
            return 8;
        }

        public static int c(int n3) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 4) {
                        if (n3 != 8) {
                            if (n3 != 16) {
                                if (n3 != 32) {
                                    if (n3 != 64) {
                                        if (n3 != 128) {
                                            if (n3 == 256) {
                                                return 8;
                                            }
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("type needs to be >= FIRST and <= LAST, type=");
                                            stringBuilder.append(n3);
                                            throw new IllegalArgumentException(stringBuilder.toString());
                                        }
                                        return 7;
                                    }
                                    return 6;
                                }
                                return 5;
                            }
                            return 4;
                        }
                        return 3;
                    }
                    return 2;
                }
                return 1;
            }
            return 0;
        }

        public static int d() {
            return 32;
        }

        public static int e() {
            return 7;
        }
    }

    public static final abstract class n {
        /*
         * Enabled aggressive block sorting
         */
        public static int a(int n3) {
            int n4 = 0;
            int n5 = 1;
            while (true) {
                int n6;
                block2: {
                    block11: {
                        block3: {
                            block4: {
                                block5: {
                                    block6: {
                                        block7: {
                                            block8: {
                                                block9: {
                                                    block10: {
                                                        if (n5 > 256) {
                                                            return n4;
                                                        }
                                                        n6 = n4;
                                                        if ((n3 & n5) == 0) break block2;
                                                        if (n5 == 1) break block3;
                                                        if (n5 == 2) break block4;
                                                        if (n5 == 4) break block5;
                                                        if (n5 == 8) break block6;
                                                        if (n5 == 16) break block7;
                                                        if (n5 == 32) break block8;
                                                        if (n5 == 64) break block9;
                                                        if (n5 == 128) break block10;
                                                        n6 = n4;
                                                        break block2;
                                                    }
                                                    n6 = x2.a();
                                                    break block11;
                                                }
                                                n6 = w2.a();
                                                break block11;
                                            }
                                            n6 = v2.a();
                                            break block11;
                                        }
                                        n6 = u2.a();
                                        break block11;
                                    }
                                    n6 = n0.a();
                                    break block11;
                                }
                                n6 = t2.a();
                                break block11;
                            }
                            n6 = s2.a();
                            break block11;
                        }
                        n6 = r2.a();
                    }
                    n6 = n4 | n6;
                }
                n5 <<= 1;
                n4 = n6;
            }
        }
    }
}

