/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.carousel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class c {
    public final float a;
    public int b;
    public final List c;
    public final int d;
    public final int e;
    public final int f;

    public c(float f3, List list, int n3, int n4, int n5) {
        this.a = f3;
        this.c = Collections.unmodifiableList(list);
        this.d = n3;
        this.e = n4;
        while (n3 <= n4) {
            if (((c)list.get((int)n3)).f == 0.0f) {
                ++this.b;
            }
            ++n3;
        }
        this.f = n5;
    }

    public /* synthetic */ c(float f3, List list, int n3, int n4, int n5, a a4) {
        this(f3, list, n3, n4, n5);
    }

    public static c o(c c3, c c4, float f3) {
        if (c3.g() == c4.g()) {
            List list = c3.h();
            List list2 = c4.h();
            if (list.size() == list2.size()) {
                int n3;
                ArrayList<c> arrayList = new ArrayList<c>();
                for (n3 = 0; n3 < c3.h().size(); ++n3) {
                    arrayList.add(com.google.android.material.carousel.c$c.a((c)list.get(n3), (c)list2.get(n3), f3));
                }
                n3 = a2.a.c(c3.c(), c4.c(), f3);
                int n4 = a2.a.c(c3.j(), c4.j(), f3);
                return new c(c3.g(), arrayList, n3, n4, c3.f);
            }
            throw new IllegalArgumentException("Keylines being linearly interpolated must have the same number of keylines.");
        }
        throw new IllegalArgumentException("Keylines being linearly interpolated must have the same item size.");
    }

    public static c p(c c3, int n3) {
        b b3 = new b(c3.g(), n3);
        float f3 = (float)n3 - c3.k().b - c3.k().d / 2.0f;
        for (n3 = c3.h().size() - 1; n3 >= 0; --n3) {
            c c4 = (c)c3.h().get(n3);
            float f4 = c4.d / 2.0f;
            boolean bl = n3 >= c3.c() && n3 <= c3.j();
            b3.d(f4 + f3, c4.c, c4.d, bl, c4.e);
            f3 += c4.d;
        }
        return b3.i();
    }

    public int a() {
        return this.f;
    }

    public c b() {
        return (c)this.c.get(this.d);
    }

    public int c() {
        return this.d;
    }

    public c d() {
        return (c)this.c.get(0);
    }

    public c e() {
        for (int i3 = 0; i3 < this.c.size(); ++i3) {
            c c3 = (c)this.c.get(i3);
            if (c3.e) continue;
            return c3;
        }
        return null;
    }

    public List f() {
        return this.c.subList(this.d, this.e + 1);
    }

    public float g() {
        return this.a;
    }

    public List h() {
        return this.c;
    }

    public c i() {
        return (c)this.c.get(this.e);
    }

    public int j() {
        return this.e;
    }

    public c k() {
        List list = this.c;
        return (c)list.get(list.size() - 1);
    }

    public c l() {
        for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
            c c3 = (c)this.c.get(i3);
            if (c3.e) continue;
            return c3;
        }
        return null;
    }

    public int m() {
        Iterator iterator = this.c.iterator();
        int n3 = 0;
        while (iterator.hasNext()) {
            if (!((c)iterator.next()).e) continue;
            ++n3;
        }
        return this.c.size() - n3;
    }

    public int n() {
        return this.b;
    }

    public static final class b {
        public final float a;
        public final int b;
        public final List c = new ArrayList();
        public c d;
        public c e;
        public int f = -1;
        public int g = -1;
        public float h = 0.0f;
        public int i = -1;

        public b(float f3, int n3) {
            this.a = f3;
            this.b = n3;
        }

        public static float j(float f3, float f4, int n3, int n4) {
            return f3 - (float)n3 * f4 + (float)n4 * f4;
        }

        public b a(float f3, float f4, float f5) {
            return this.d(f3, f4, f5, false, true);
        }

        public b b(float f3, float f4, float f5) {
            return this.c(f3, f4, f5, false);
        }

        public b c(float f3, float f4, float f5, boolean bl) {
            return this.d(f3, f4, f5, bl, false);
        }

        public b d(float f3, float f4, float f5, boolean bl, boolean bl2) {
            float f6;
            block1: {
                float f7;
                block0: {
                    f6 = f5 / 2.0f;
                    f7 = f3 - f6;
                    int n3 = this.b;
                    if (!((f6 += f3) > (float)n3)) break block0;
                    f6 = Math.abs(f6 - Math.max(f6 - f5, (float)n3));
                    break block1;
                }
                f6 = 0.0f;
                if (!(f7 < 0.0f)) break block1;
                f6 = Math.abs(f7 - Math.min(f7 + f5, 0.0f));
            }
            return this.e(f3, f4, f5, bl, bl2, f6);
        }

        public b e(float f3, float f4, float f5, boolean bl, boolean bl2, float f6) {
            return this.f(f3, f4, f5, bl, bl2, f6, 0.0f, 0.0f);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public b f(float f3, float f4, float f5, boolean bl, boolean bl2, float f6, float f7, float f8) {
            if (f5 <= 0.0f) {
                return this;
            }
            if (bl2) {
                if (bl) throw new IllegalArgumentException("Anchor keylines cannot be focal.");
                int n3 = this.i;
                if (n3 != -1 && n3 != 0) {
                    throw new IllegalArgumentException("Anchor keylines must be either the first or last keyline.");
                }
                this.i = this.c.size();
            }
            c c3 = new c(Float.MIN_VALUE, f3, f4, f5, bl2, f6, f7, f8);
            if (bl) {
                if (this.d == null) {
                    this.d = c3;
                    this.f = this.c.size();
                }
                if (this.g != -1 && this.c.size() - this.g > 1) {
                    throw new IllegalArgumentException("Keylines marked as focal must be placed next to each other. There cannot be non-focal keylines between focal keylines.");
                }
                if (f5 != this.d.d) throw new IllegalArgumentException("Keylines that are marked as focal must all have the same masked item size.");
                this.e = c3;
                this.g = this.c.size();
            } else {
                if (this.d == null && c3.d < this.h) {
                    throw new IllegalArgumentException("Keylines before the first focal keyline must be ordered by incrementing masked item size.");
                }
                if (this.e != null && c3.d > this.h) {
                    throw new IllegalArgumentException("Keylines after the last focal keyline must be ordered by decreasing masked item size.");
                }
            }
            this.h = c3.d;
            this.c.add(c3);
            return this;
        }

        public b g(float f3, float f4, float f5, int n3) {
            return this.h(f3, f4, f5, n3, false);
        }

        public b h(float f3, float f4, float f5, int n3, boolean bl) {
            if (n3 > 0 && !(f5 <= 0.0f)) {
                for (int i3 = 0; i3 < n3; ++i3) {
                    this.c((float)i3 * f5 + f3, f4, f5, bl);
                }
            }
            return this;
        }

        public c i() {
            if (this.d != null) {
                ArrayList<c> arrayList = new ArrayList<c>();
                for (int i3 = 0; i3 < this.c.size(); ++i3) {
                    c c3 = (c)this.c.get(i3);
                    arrayList.add(new c(com.google.android.material.carousel.c$b.j(this.d.b, this.a, this.f, i3), c3.b, c3.c, c3.d, c3.e, c3.f, c3.g, c3.h));
                }
                return new c(this.a, arrayList, this.f, this.g, this.b, null);
            }
            throw new IllegalStateException("There must be a keyline marked as focal.");
        }
    }

    public static final class c {
        public final float a;
        public final float b;
        public final float c;
        public final float d;
        public final boolean e;
        public final float f;
        public final float g;
        public final float h;

        public c(float f3, float f4, float f5, float f6) {
            this(f3, f4, f5, f6, false, 0.0f, 0.0f, 0.0f);
        }

        public c(float f3, float f4, float f5, float f6, boolean bl, float f7, float f8, float f9) {
            this.a = f3;
            this.b = f4;
            this.c = f5;
            this.d = f6;
            this.e = bl;
            this.f = f7;
            this.g = f8;
            this.h = f9;
        }

        public static c a(c c3, c c4, float f3) {
            return new c(a2.a.a(c3.a, c4.a, f3), a2.a.a(c3.b, c4.b, f3), a2.a.a(c3.c, c4.c, f3), a2.a.a(c3.d, c4.d, f3));
        }
    }
}

