/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewGroup
 *  android.view.WindowInsets
 *  android.view.WindowInsetsAnimation
 *  android.view.WindowInsetsAnimation$Bounds
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.PathInterpolator
 */
package o0;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimation$Callback;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import o0.i0;
import o0.n1;
import o0.o1;
import o0.p1;
import o0.q1;
import o0.r1;
import o0.s1;
import o0.t1;
import o0.u1;
import o0.v1;
import o0.w1;
import o0.x0;
import o0.x1;
import o0.y1;
import o0.z1;

public final class m1 {
    public e a;

    public m1(int n3, Interpolator interpolator, long l3) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new d(n3, interpolator, l3);
            return;
        }
        this.a = new c(n3, interpolator, l3);
    }

    public m1(WindowInsetsAnimation windowInsetsAnimation) {
        this(0, null, 0L);
        if (Build.VERSION.SDK_INT >= 30) {
            this.a = new d(windowInsetsAnimation);
        }
    }

    public static void d(View view, b b3) {
        if (Build.VERSION.SDK_INT >= 30) {
            d.h(view, b3);
            return;
        }
        c.p(view, b3);
    }

    public static m1 f(WindowInsetsAnimation windowInsetsAnimation) {
        return new m1(windowInsetsAnimation);
    }

    public long a() {
        return this.a.a();
    }

    public float b() {
        return this.a.b();
    }

    public int c() {
        return this.a.c();
    }

    public void e(float f3) {
        this.a.d(f3);
    }

    public static final class a {
        public final g0.b a;
        public final g0.b b;

        public a(WindowInsetsAnimation.Bounds bounds) {
            this.a = d.g(bounds);
            this.b = d.f(bounds);
        }

        public a(g0.b b3, g0.b b4) {
            this.a = b3;
            this.b = b4;
        }

        public static a d(WindowInsetsAnimation.Bounds bounds) {
            return new a(bounds);
        }

        public g0.b a() {
            return this.a;
        }

        public g0.b b() {
            return this.b;
        }

        public WindowInsetsAnimation.Bounds c() {
            return d.e(this);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bounds{lower=");
            stringBuilder.append(this.a);
            stringBuilder.append(" upper=");
            stringBuilder.append(this.b);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    public static abstract class b {
        public WindowInsets a;
        public final int b;

        public b(int n3) {
            this.b = n3;
        }

        public final int a() {
            return this.b;
        }

        public abstract void b(m1 var1);

        public abstract void c(m1 var1);

        public abstract z1 d(z1 var1, List var2);

        public abstract a e(m1 var1, a var2);
    }

    public static class c
    extends e {
        public static final Interpolator e = new PathInterpolator(0.0f, 1.1f, 0.0f, 1.0f);
        public static final Interpolator f = new d1.a();
        public static final Interpolator g = new DecelerateInterpolator();

        public c(int n3, Interpolator interpolator, long l3) {
            super(n3, interpolator, l3);
        }

        public static int e(z1 z12, z1 z13) {
            int n3 = 0;
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                int n4 = n3;
                if (!z12.f(i3).equals(z13.f(i3))) {
                    n4 = n3 | i3;
                }
                n3 = n4;
            }
            return n3;
        }

        public static o0.m1$a f(z1 object, z1 object2, int n3) {
            object = ((z1)object).f(n3);
            object2 = ((z1)object2).f(n3);
            return new o0.m1$a(g0.b.b(Math.min(((g0.b)object).a, ((g0.b)object2).a), Math.min(((g0.b)object).b, ((g0.b)object2).b), Math.min(((g0.b)object).c, ((g0.b)object2).c), Math.min(((g0.b)object).d, ((g0.b)object2).d)), g0.b.b(Math.max(((g0.b)object).a, ((g0.b)object2).a), Math.max(((g0.b)object).b, ((g0.b)object2).b), Math.max(((g0.b)object).c, ((g0.b)object2).c), Math.max(((g0.b)object).d, ((g0.b)object2).d)));
        }

        public static Interpolator g(int n3, z1 z12, z1 z13) {
            if ((n3 & 8) != 0) {
                if (z12.f((int)z1.m.b()).d > z13.f((int)z1.m.b()).d) {
                    return e;
                }
                return f;
            }
            return g;
        }

        public static View.OnApplyWindowInsetsListener h(View view, b b3) {
            return new a(view, b3);
        }

        public static void i(View view, m1 m12) {
            block6: {
                block5: {
                    b b3 = c.n(view);
                    if (b3 == null) break block5;
                    b3.b(m12);
                    if (b3.a() == 0) break block6;
                }
                if (view instanceof ViewGroup) {
                    view = (ViewGroup)view;
                    for (int i3 = 0; i3 < view.getChildCount(); ++i3) {
                        c.i(view.getChildAt(i3), m12);
                    }
                }
            }
        }

        public static void j(View view, m1 m12, WindowInsets windowInsets, boolean bl) {
            b b3 = c.n(view);
            int n3 = 0;
            boolean bl2 = bl;
            if (b3 != null) {
                b3.a = windowInsets;
                bl2 = bl;
                if (!bl) {
                    b3.c(m12);
                    bl2 = b3.a() == 0;
                }
            }
            if (view instanceof ViewGroup) {
                view = (ViewGroup)view;
                while (n3 < view.getChildCount()) {
                    c.j(view.getChildAt(n3), m12, windowInsets, bl2);
                    ++n3;
                }
            }
        }

        public static void k(View view, z1 z12, List list) {
            block6: {
                z1 z13;
                block5: {
                    b b3 = c.n(view);
                    z13 = z12;
                    if (b3 == null) break block5;
                    z13 = b3.d(z12, list);
                    if (b3.a() == 0) break block6;
                }
                if (view instanceof ViewGroup) {
                    view = (ViewGroup)view;
                    for (int i3 = 0; i3 < view.getChildCount(); ++i3) {
                        c.k(view.getChildAt(i3), z13, list);
                    }
                }
            }
        }

        public static void l(View view, m1 m12, o0.m1$a a4) {
            block6: {
                block5: {
                    b b3 = c.n(view);
                    if (b3 == null) break block5;
                    b3.e(m12, a4);
                    if (b3.a() == 0) break block6;
                }
                if (view instanceof ViewGroup) {
                    view = (ViewGroup)view;
                    for (int i3 = 0; i3 < view.getChildCount(); ++i3) {
                        c.l(view.getChildAt(i3), m12, a4);
                    }
                }
            }
        }

        public static WindowInsets m(View view, WindowInsets windowInsets) {
            if (view.getTag(b0.b.tag_on_apply_window_listener) != null) {
                return windowInsets;
            }
            return view.onApplyWindowInsets(windowInsets);
        }

        public static b n(View object) {
            if ((object = object.getTag(b0.b.tag_window_insets_animation_callback)) instanceof a) {
                return ((a)object).a;
            }
            return null;
        }

        public static z1 o(z1 z12, z1 z13, float f3, int n3) {
            z1.b b3 = new z1.b(z12);
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((n3 & i3) == 0) {
                    b3.b(i3, z12.f(i3));
                    continue;
                }
                g0.b b4 = z12.f(i3);
                g0.b b5 = z13.f(i3);
                float f4 = b4.a - b5.a;
                float f5 = 1.0f - f3;
                b3.b(i3, z1.o(b4, (int)((double)(f4 * f5) + 0.5), (int)((double)((float)(b4.b - b5.b) * f5) + 0.5), (int)((double)((float)(b4.c - b5.c) * f5) + 0.5), (int)((double)((float)(b4.d - b5.d) * f5) + 0.5)));
            }
            return b3.a();
        }

        public static void p(View view, b b3) {
            Object object = view.getTag(b0.b.tag_on_apply_window_listener);
            if (b3 == null) {
                view.setTag(b0.b.tag_window_insets_animation_callback, null);
                if (object == null) {
                    view.setOnApplyWindowInsetsListener(null);
                    return;
                }
            } else {
                b3 = c.h(view, b3);
                view.setTag(b0.b.tag_window_insets_animation_callback, (Object)b3);
                if (object == null) {
                    view.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener)b3);
                }
            }
        }

        public static class a
        implements View.OnApplyWindowInsetsListener {
            public final b a;
            public z1 b;

            public a(View object, b b3) {
                this.a = b3;
                object = x0.D(object);
                object = object != null ? new z1.b((z1)object).a() : null;
                this.b = object;
            }

            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                if (!view.isLaidOut()) {
                    this.b = z1.x(windowInsets, view);
                    return c.m(view, windowInsets);
                }
                z1 z12 = z1.x(windowInsets, view);
                if (this.b == null) {
                    this.b = x0.D(view);
                }
                if (this.b == null) {
                    this.b = z12;
                    return c.m(view, windowInsets);
                }
                Object object = c.n(view);
                if (object != null && Objects.equals(((b)object).a, windowInsets)) {
                    return c.m(view, windowInsets);
                }
                int n3 = c.e(z12, this.b);
                if (n3 == 0) {
                    return c.m(view, windowInsets);
                }
                z1 z13 = this.b;
                object = new m1(n3, c.g(n3, z12, z13), 160L);
                ((m1)object).e(0.0f);
                ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f}).setDuration(((m1)object).a());
                o0.m1$a a4 = c.f(z12, z13, n3);
                c.j(view, (m1)object, windowInsets, false);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this, (m1)object, z12, z13, n3, view){
                    public final m1 a;
                    public final z1 b;
                    public final z1 c;
                    public final int d;
                    public final View e;
                    public final a f;
                    {
                        this.f = a4;
                        this.a = m12;
                        this.b = z12;
                        this.c = z13;
                        this.d = n3;
                        this.e = view;
                    }

                    public void onAnimationUpdate(ValueAnimator object) {
                        this.a.e(object.getAnimatedFraction());
                        object = c.o(this.b, this.c, this.a.b(), this.d);
                        List<m1> list = Collections.singletonList(this.a);
                        c.k(this.e, (z1)object, list);
                    }
                });
                valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, (m1)object, view){
                    public final m1 a;
                    public final View b;
                    public final a c;
                    {
                        this.c = a4;
                        this.a = m12;
                        this.b = view;
                    }

                    public void onAnimationEnd(Animator animator) {
                        this.a.e(1.0f);
                        c.i(this.b, this.a);
                    }
                });
                i0.a(view, new Runnable(this, view, (m1)object, a4, valueAnimator){
                    public final View c;
                    public final m1 d;
                    public final o0.m1$a e;
                    public final ValueAnimator f;
                    public final a g;
                    {
                        this.g = a4;
                        this.c = view;
                        this.d = m12;
                        this.e = a5;
                        this.f = valueAnimator;
                    }

                    @Override
                    public void run() {
                        c.l(this.c, this.d, this.e);
                        this.f.start();
                    }
                });
                this.b = z12;
                return c.m(view, windowInsets);
            }
        }
    }

    public static class d
    extends e {
        public final WindowInsetsAnimation e;

        public d(int n3, Interpolator interpolator, long l3) {
            this(u1.a(n3, interpolator, l3));
        }

        public d(WindowInsetsAnimation windowInsetsAnimation) {
            super(0, null, 0L);
            this.e = windowInsetsAnimation;
        }

        public static WindowInsetsAnimation.Bounds e(o0.m1$a a4) {
            w1.a();
            return v1.a(a4.a().e(), a4.b().e());
        }

        public static g0.b f(WindowInsetsAnimation.Bounds bounds) {
            return g0.b.d(s1.a(bounds));
        }

        public static g0.b g(WindowInsetsAnimation.Bounds bounds) {
            return g0.b.d(t1.a(bounds));
        }

        public static void h(View view, b object) {
            object = object != null ? new a((b)object) : null;
            r1.a(view, (WindowInsetsAnimation$Callback)object);
        }

        @Override
        public long a() {
            return n1.a(this.e);
        }

        @Override
        public float b() {
            return q1.a(this.e);
        }

        @Override
        public int c() {
            return o1.a(this.e);
        }

        @Override
        public void d(float f3) {
            p1.a(this.e, f3);
        }

        public static class a
        extends WindowInsetsAnimation$Callback {
            public final b a;
            public List b;
            public ArrayList c;
            public final HashMap d = new HashMap();

            public a(b b3) {
                super(b3.a());
                this.a = b3;
            }

            public final m1 a(WindowInsetsAnimation windowInsetsAnimation) {
                m1 m12;
                m1 m13 = m12 = (m1)this.d.get(windowInsetsAnimation);
                if (m12 == null) {
                    m13 = m1.f(windowInsetsAnimation);
                    this.d.put(windowInsetsAnimation, m13);
                }
                return m13;
            }

            public void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
                this.a.b(this.a(windowInsetsAnimation));
                this.d.remove(windowInsetsAnimation);
            }

            public void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
                this.a.c(this.a(windowInsetsAnimation));
            }

            public WindowInsets onProgress(WindowInsets windowInsets, List list) {
                ArrayList arrayList = this.c;
                if (arrayList == null) {
                    arrayList = new ArrayList(list.size());
                    this.c = arrayList;
                    this.b = Collections.unmodifiableList(arrayList);
                } else {
                    arrayList.clear();
                }
                for (int i3 = list.size() - 1; i3 >= 0; --i3) {
                    WindowInsetsAnimation windowInsetsAnimation = x1.a(list.get(i3));
                    arrayList = this.a(windowInsetsAnimation);
                    ((m1)((Object)arrayList)).e(y1.a(windowInsetsAnimation));
                    this.c.add(arrayList);
                }
                return this.a.d(z1.w(windowInsets), this.b).v();
            }

            public WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
                return this.a.e(this.a(windowInsetsAnimation), o0.m1$a.d(bounds)).c();
            }
        }
    }

    public static abstract class e {
        public final int a;
        public float b;
        public final Interpolator c;
        public final long d;

        public e(int n3, Interpolator interpolator, long l3) {
            this.a = n3;
            this.c = interpolator;
            this.d = l3;
        }

        public long a() {
            return this.d;
        }

        public float b() {
            Interpolator interpolator = this.c;
            if (interpolator != null) {
                return interpolator.getInterpolation(this.b);
            }
            return this.b;
        }

        public int c() {
            return this.a;
        }

        public void d(float f3) {
            this.b = f3;
        }
    }
}

