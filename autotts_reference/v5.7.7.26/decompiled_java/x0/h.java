/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.AndroidRuntimeException
 *  android.view.View
 */
package x0;

import android.util.AndroidRuntimeException;
import android.view.View;
import androidx.appcompat.app.s;
import java.util.ArrayList;
import o0.x0;
import x0.c;
import x0.i;

public abstract class h
implements c.c {
    public static final p A;
    public static final p n;
    public static final p o;
    public static final p p;
    public static final p q;
    public static final p r;
    public static final p s;
    public static final p t;
    public static final p u;
    public static final p v;
    public static final p w;
    public static final p x;
    public static final p y;
    public static final p z;
    public float a = 0.0f;
    public float b = Float.MAX_VALUE;
    public boolean c = false;
    public final Object d;
    public final i e;
    public boolean f = false;
    public float g = Float.MAX_VALUE;
    public float h = -Float.MAX_VALUE;
    public long i = 0L;
    public float j;
    public final ArrayList k = new ArrayList();
    public final ArrayList l = new ArrayList();
    public c m;

    static {
        n = new p("translationX"){

            public float c(View view) {
                return view.getTranslationX();
            }

            public void d(View view, float f3) {
                view.setTranslationX(f3);
            }
        };
        o = new p("translationY"){

            public float c(View view) {
                return view.getTranslationY();
            }

            public void d(View view, float f3) {
                view.setTranslationY(f3);
            }
        };
        p = new p("translationZ"){

            public float c(View view) {
                return x0.G(view);
            }

            public void d(View view, float f3) {
                x0.x0(view, f3);
            }
        };
        q = new p("scaleX"){

            public float c(View view) {
                return view.getScaleX();
            }

            public void d(View view, float f3) {
                view.setScaleX(f3);
            }
        };
        r = new p("scaleY"){

            public float c(View view) {
                return view.getScaleY();
            }

            public void d(View view, float f3) {
                view.setScaleY(f3);
            }
        };
        s = new p("rotation"){

            public float c(View view) {
                return view.getRotation();
            }

            public void d(View view, float f3) {
                view.setRotation(f3);
            }
        };
        t = new p("rotationX"){

            public float c(View view) {
                return view.getRotationX();
            }

            public void d(View view, float f3) {
                view.setRotationX(f3);
            }
        };
        u = new p("rotationY"){

            public float c(View view) {
                return view.getRotationY();
            }

            public void d(View view, float f3) {
                view.setRotationY(f3);
            }
        };
        v = new p("x"){

            public float c(View view) {
                return view.getX();
            }

            public void d(View view, float f3) {
                view.setX(f3);
            }
        };
        w = new p("y"){

            public float c(View view) {
                return view.getY();
            }

            public void d(View view, float f3) {
                view.setY(f3);
            }
        };
        x = new p("z"){

            public float c(View view) {
                return x0.J(view);
            }

            public void d(View view, float f3) {
                x0.z0(view, f3);
            }
        };
        y = new p("alpha"){

            public float c(View view) {
                return view.getAlpha();
            }

            public void d(View view, float f3) {
                view.setAlpha(f3);
            }
        };
        z = new p("scrollX"){

            public float c(View view) {
                return view.getScrollX();
            }

            public void d(View view, float f3) {
                view.setScrollX((int)f3);
            }
        };
        A = new p("scrollY"){

            public float c(View view) {
                return view.getScrollY();
            }

            public void d(View view, float f3) {
                view.setScrollY((int)f3);
            }
        };
    }

    public h(Object object, i i3) {
        this.d = object;
        this.e = i3;
        if (i3 != s && i3 != t && i3 != u) {
            if (i3 == y) {
                this.j = 0.00390625f;
                return;
            }
            if (i3 != q && i3 != r) {
                this.j = 1.0f;
                return;
            }
            this.j = 0.002f;
            return;
        }
        this.j = 0.1f;
    }

    public static void g(ArrayList arrayList) {
        for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
            if (arrayList.get(i3) != null) continue;
            arrayList.remove(i3);
        }
    }

    @Override
    public boolean a(long l3) {
        long l4 = this.i;
        if (l4 == 0L) {
            this.i = l3;
            this.i(this.b);
            return false;
        }
        this.i = l3;
        float f3 = this.c().g();
        l3 = f3 == 0.0f ? Integer.MAX_VALUE : (long)((float)(l3 - l4) / f3);
        boolean bl = this.n(l3);
        this.b = f3 = Math.min(this.b, this.g);
        this.b = f3 = Math.max(f3, this.h);
        this.i(f3);
        if (bl) {
            this.b(false);
        }
        return bl;
    }

    public final void b(boolean bl) {
        this.f = false;
        this.c().k(this);
        this.i = 0L;
        this.c = false;
        for (int i3 = 0; i3 < this.k.size(); ++i3) {
            if (this.k.get(i3) == null) {
                continue;
            }
            androidx.appcompat.app.s.a(this.k.get(i3));
            throw null;
        }
        x0.h.g(this.k);
    }

    public c c() {
        c c3 = this.m;
        if (c3 != null) {
            return c3;
        }
        return x0.c.h();
    }

    public final float d() {
        return this.e.a(this.d);
    }

    public float e() {
        return this.j * 0.75f;
    }

    public boolean f() {
        return this.f;
    }

    public h h(float f3) {
        if (!(f3 <= 0.0f)) {
            this.j = f3;
            this.k(f3 * 0.75f);
            return this;
        }
        throw new IllegalArgumentException("Minimum visible change must be positive.");
    }

    public void i(float f3) {
        this.e.b(this.d, f3);
        for (int i3 = 0; i3 < this.l.size(); ++i3) {
            if (this.l.get(i3) == null) {
                continue;
            }
            androidx.appcompat.app.s.a(this.l.get(i3));
            throw null;
        }
        x0.h.g(this.l);
    }

    public h j(float f3) {
        this.b = f3;
        this.c = true;
        return this;
    }

    public abstract void k(float var1);

    public void l() {
        if (this.c().j()) {
            if (!this.f) {
                this.m();
            }
            return;
        }
        throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
    }

    public final void m() {
        if (!this.f) {
            float f3;
            this.f = true;
            if (!this.c) {
                this.b = this.d();
            }
            if (!((f3 = this.b) > this.g) && !(f3 < this.h)) {
                this.c().d(this, 0L);
                return;
            }
            throw new IllegalArgumentException("Starting value need to be in between min value and max value");
        }
    }

    public abstract boolean n(long var1);

    public static class o {
        public float a;
        public float b;
    }

    public static abstract class p
    extends i {
        public p(String string) {
            super(string);
        }

        public /* synthetic */ p(String string, f f3) {
            this(string);
        }
    }
}

