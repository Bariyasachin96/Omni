/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

public final class e
extends RecyclerView.t {
    public ViewPager2.i a;
    public final ViewPager2 b;
    public final RecyclerView c;
    public final LinearLayoutManager d;
    public int e;
    public int f;
    public a g;
    public int h;
    public int i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;

    public e(ViewPager2 viewGroup) {
        this.b = viewGroup;
        viewGroup = viewGroup.l;
        this.c = viewGroup;
        this.d = (LinearLayoutManager)viewGroup.getLayoutManager();
        this.g = new a();
        this.n();
    }

    @Override
    public void a(RecyclerView object, int n3) {
        block13: {
            int n4;
            block14: {
                block16: {
                    block15: {
                        block12: {
                            if ((this.e != 1 || this.f != 1) && n3 == 1) {
                                this.p(false);
                                return;
                            }
                            if (!this.k() || n3 != 2) break block12;
                            if (this.k) {
                                this.e(2);
                                this.j = true;
                                return;
                            }
                            break block13;
                        }
                        if (!this.k() || n3 != 0) break block14;
                        this.q();
                        if (this.k) break block15;
                        n4 = this.g.a;
                        if (n4 != -1) {
                            this.c(n4, 0.0f, 0);
                        }
                        break block16;
                    }
                    object = this.g;
                    if (((a)object).c != 0) break block14;
                    int n5 = this.h;
                    n4 = ((a)object).a;
                    if (n5 != n4) {
                        this.d(n4);
                    }
                }
                this.e(0);
                this.n();
            }
            if (this.e == 2 && n3 == 0 && this.l) {
                this.q();
                object = this.g;
                if (((a)object).c == 0) {
                    n3 = this.i;
                    n4 = ((a)object).a;
                    if (n3 != n4) {
                        n3 = n4;
                        if (n4 == -1) {
                            n3 = 0;
                        }
                        this.d(n3);
                    }
                    this.e(0);
                    this.n();
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void b(RecyclerView var1_1, int var2_2, int var3_3) {
        block8: {
            block7: {
                this.k = true;
                this.q();
                if (!this.j) break block7;
                this.j = false;
                if (var3_3 <= 0 && (var3_3 != 0 || (var4_4 = var2_2 < 0) != this.b.d())) ** GOTO lbl-1000
                var1_1 = this.g;
                if (var1_1.c != 0) {
                    var2_2 = var1_1.a + 1;
                } else lbl-1000:
                // 2 sources

                {
                    var2_2 = this.g.a;
                }
                this.i = var2_2;
                if (this.h != var2_2) {
                    this.d(var2_2);
                }
                break block8;
            }
            if (this.e == 0) {
                var2_2 = var3_3 = this.g.a;
                if (var3_3 == -1) {
                    var2_2 = 0;
                }
                this.d(var2_2);
            }
        }
        var1_1 = this.g;
        var2_2 = var3_3 = var1_1.a;
        if (var3_3 == -1) {
            var2_2 = 0;
        }
        this.c(var2_2, var1_1.b, var1_1.c);
        var1_1 = this.g;
        var3_3 = var1_1.a;
        var2_2 = this.i;
        if ((var3_3 == var2_2 || var2_2 == -1) && var1_1.c == 0 && this.f != 1) {
            this.e(0);
            this.n();
        }
    }

    public final void c(int n3, float f3, int n4) {
        ViewPager2.i i3 = this.a;
        if (i3 != null) {
            i3.b(n3, f3, n4);
        }
    }

    public final void d(int n3) {
        ViewPager2.i i3 = this.a;
        if (i3 != null) {
            i3.c(n3);
        }
    }

    public final void e(int n3) {
        if ((this.e != 3 || this.f != 0) && this.f != n3) {
            this.f = n3;
            ViewPager2.i i3 = this.a;
            if (i3 != null) {
                i3.a(n3);
            }
        }
    }

    public final int f() {
        return this.d.c2();
    }

    public double g() {
        this.q();
        a a4 = this.g;
        return (double)a4.a + (double)a4.b;
    }

    public int h() {
        return this.f;
    }

    public boolean i() {
        return this.m;
    }

    public boolean j() {
        return this.f == 0;
    }

    public final boolean k() {
        int n3 = this.e;
        return n3 == 1 || n3 == 4;
        {
        }
    }

    public void l() {
        this.l = true;
    }

    public void m(int n3, boolean bl) {
        int n4 = bl ? 2 : 3;
        this.e = n4;
        n4 = 0;
        this.m = false;
        if (this.i != n3) {
            n4 = 1;
        }
        this.i = n3;
        this.e(2);
        if (n4 != 0) {
            this.d(n3);
        }
    }

    public final void n() {
        this.e = 0;
        this.f = 0;
        this.g.a();
        this.h = -1;
        this.i = -1;
        this.j = false;
        this.k = false;
        this.m = false;
        this.l = false;
    }

    public void o(ViewPager2.i i3) {
        this.a = i3;
    }

    public final void p(boolean bl) {
        this.m = bl;
        int n3 = bl ? 4 : 1;
        this.e = n3;
        n3 = this.i;
        if (n3 != -1) {
            this.h = n3;
            this.i = -1;
        } else if (this.h == -1) {
            this.h = this.f();
        }
        this.e(1);
    }

    public final void q() {
        int n3;
        a a4 = this.g;
        a4.a = n3 = this.d.c2();
        if (n3 == -1) {
            a4.a();
            return;
        }
        View view = this.d.H(n3);
        if (view == null) {
            a4.a();
            return;
        }
        int n4 = this.d.e0(view);
        int n5 = this.d.n0(view);
        int n6 = this.d.q0(view);
        int n7 = this.d.M(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int n8 = n4;
        int n9 = n5;
        n3 = n6;
        int n10 = n7;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            n8 = n4 + layoutParams.leftMargin;
            n9 = n5 + layoutParams.rightMargin;
            n3 = n6 + layoutParams.topMargin;
            n10 = n7 + layoutParams.bottomMargin;
        }
        n10 = view.getHeight() + n3 + n10;
        n7 = view.getWidth();
        if (this.d.p2() == 0) {
            n3 = n10 = view.getLeft() - n8 - this.c.getPaddingLeft();
            if (this.b.d()) {
                n3 = -n10;
            }
            n8 = n7 + n8 + n9;
            n9 = n3;
            n3 = n8;
        } else {
            n9 = view.getTop() - n3 - this.c.getPaddingTop();
            n3 = n10;
        }
        a4.c = n9 = -n9;
        if (n9 < 0) {
            if (new androidx.viewpager2.widget.a(this.d).d()) {
                throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
            }
            throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", a4.c));
        }
        float f3 = n3 == 0 ? 0.0f : (float)n9 / (float)n3;
        a4.b = f3;
    }

    public static final class a {
        public int a;
        public float b;
        public int c;

        public void a() {
            this.a = -1;
            this.b = 0.0f;
            this.c = 0;
        }
    }
}

