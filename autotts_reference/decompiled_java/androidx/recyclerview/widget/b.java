/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class b {
    public final b a;
    public final a b;
    public final List c;

    public b(b b3) {
        this.a = b3;
        this.b = new a();
        this.c = new ArrayList();
    }

    public void a(View view, int n3, boolean bl) {
        n3 = n3 < 0 ? this.a.g() : this.h(n3);
        this.b.e(n3, bl);
        if (bl) {
            this.l(view);
        }
        this.a.f(view, n3);
    }

    public void b(View view, boolean bl) {
        this.a(view, -1, bl);
    }

    public void c(View view, int n3, ViewGroup.LayoutParams layoutParams, boolean bl) {
        n3 = n3 < 0 ? this.a.g() : this.h(n3);
        this.b.e(n3, bl);
        if (bl) {
            this.l(view);
        }
        this.a.j(view, n3, layoutParams);
    }

    public void d(int n3) {
        n3 = this.h(n3);
        this.b.f(n3);
        this.a.d(n3);
    }

    public View e(int n3) {
        int n4 = this.c.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            View view = (View)this.c.get(i3);
            RecyclerView.d0 d02 = this.a.c(view);
            if (d02.m() != n3 || d02.t() || d02.v()) continue;
            return view;
        }
        return null;
    }

    public View f(int n3) {
        n3 = this.h(n3);
        return this.a.a(n3);
    }

    public int g() {
        return this.a.g() - this.c.size();
    }

    public final int h(int n3) {
        int n4;
        if (n3 < 0) {
            return -1;
        }
        int n5 = this.a.g();
        for (int i3 = n3; i3 < n5; i3 += n4) {
            n4 = n3 - (i3 - this.b.b(i3));
            if (n4 != 0) continue;
            while (this.b.d(i3)) {
                ++i3;
            }
            return i3;
        }
        return -1;
    }

    public View i(int n3) {
        return this.a.a(n3);
    }

    public int j() {
        return this.a.g();
    }

    public void k(View view) {
        int n3 = this.a.k(view);
        if (n3 >= 0) {
            this.b.h(n3);
            this.l(view);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("view is not a child, cannot hide ");
        stringBuilder.append(view);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final void l(View view) {
        this.c.add(view);
        this.a.b(view);
    }

    public int m(View view) {
        int n3 = this.a.k(view);
        if (n3 == -1) {
            return -1;
        }
        if (this.b.d(n3)) {
            return -1;
        }
        return n3 - this.b.b(n3);
    }

    public boolean n(View view) {
        return this.c.contains(view);
    }

    public void o() {
        this.b.g();
        for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
            this.a.e((View)this.c.get(i3));
            this.c.remove(i3);
        }
        this.a.i();
    }

    public void p(View view) {
        int n3 = this.a.k(view);
        if (n3 < 0) {
            return;
        }
        if (this.b.f(n3)) {
            this.t(view);
        }
        this.a.h(n3);
    }

    public void q(int n3) {
        View view = this.a.a(n3 = this.h(n3));
        if (view == null) {
            return;
        }
        if (this.b.f(n3)) {
            this.t(view);
        }
        this.a.h(n3);
    }

    public boolean r(View view) {
        int n3 = this.a.k(view);
        if (n3 == -1) {
            this.t(view);
            return true;
        }
        if (this.b.d(n3)) {
            this.b.f(n3);
            this.t(view);
            this.a.h(n3);
            return true;
        }
        return false;
    }

    public void s(View view) {
        int n3 = this.a.k(view);
        if (n3 >= 0) {
            if (this.b.d(n3)) {
                this.b.a(n3);
                this.t(view);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("trying to unhide a view that was not hidden");
            stringBuilder.append(view);
            throw new RuntimeException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("view is not a child, cannot hide ");
        stringBuilder.append(view);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final boolean t(View view) {
        if (this.c.remove(view)) {
            this.a.e(view);
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.b.toString());
        stringBuilder.append(", hidden list:");
        stringBuilder.append(this.c.size());
        return stringBuilder.toString();
    }

    public static class a {
        public long a = 0L;
        public a b;

        public void a(int n3) {
            if (n3 >= 64) {
                a a4 = this.b;
                if (a4 != null) {
                    a4.a(n3 - 64);
                }
                return;
            }
            this.a &= 1L << n3 ^ 0xFFFFFFFFFFFFFFFFL;
        }

        public int b(int n3) {
            a a4 = this.b;
            if (a4 == null) {
                if (n3 >= 64) {
                    return Long.bitCount(this.a);
                }
                return Long.bitCount(this.a & (1L << n3) - 1L);
            }
            if (n3 < 64) {
                return Long.bitCount(this.a & (1L << n3) - 1L);
            }
            return a4.b(n3 - 64) + Long.bitCount(this.a);
        }

        public final void c() {
            if (this.b == null) {
                this.b = new a();
            }
        }

        public boolean d(int n3) {
            if (n3 >= 64) {
                this.c();
                return this.b.d(n3 - 64);
            }
            return (this.a & 1L << n3) != 0L;
        }

        public void e(int n3, boolean bl) {
            if (n3 >= 64) {
                this.c();
                this.b.e(n3 - 64, bl);
                return;
            }
            long l3 = this.a;
            boolean bl2 = (Long.MIN_VALUE & l3) != 0L;
            long l4 = (1L << n3) - 1L;
            this.a = (l3 & (l4 ^ 0xFFFFFFFFFFFFFFFFL)) << 1 | l3 & l4;
            if (bl) {
                this.h(n3);
            } else {
                this.a(n3);
            }
            if (!bl2 && this.b == null) {
                return;
            }
            this.c();
            this.b.e(0, bl2);
        }

        public boolean f(int n3) {
            if (n3 >= 64) {
                this.c();
                return this.b.f(n3 - 64);
            }
            long l3 = this.a;
            long l4 = 1L << n3;
            boolean bl = (l3 & l4) != 0L;
            this.a = l3 &= l4 ^ 0xFFFFFFFFFFFFFFFFL;
            this.a = l3 & --l4 | Long.rotateRight((l4 ^ 0xFFFFFFFFFFFFFFFFL) & l3, 1);
            a a4 = this.b;
            if (a4 != null) {
                if (a4.d(0)) {
                    this.h(63);
                }
                this.b.f(0);
            }
            return bl;
        }

        public void g() {
            this.a = 0L;
            a a4 = this.b;
            if (a4 != null) {
                a4.g();
            }
        }

        public void h(int n3) {
            if (n3 >= 64) {
                this.c();
                this.b.h(n3 - 64);
                return;
            }
            this.a |= 1L << n3;
        }

        public String toString() {
            if (this.b == null) {
                return Long.toBinaryString(this.a);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.b.toString());
            stringBuilder.append("xx");
            stringBuilder.append(Long.toBinaryString(this.a));
            return stringBuilder.toString();
        }
    }

    public static interface b {
        public View a(int var1);

        public void b(View var1);

        public RecyclerView.d0 c(View var1);

        public void d(int var1);

        public void e(View var1);

        public void f(View var1, int var2);

        public int g();

        public void h(int var1);

        public void i();

        public void j(View var1, int var2, ViewGroup.LayoutParams var3);

        public int k(View var1);
    }
}

