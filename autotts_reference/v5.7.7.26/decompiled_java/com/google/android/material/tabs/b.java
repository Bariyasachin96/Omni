/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.tabs;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;

public final class b {
    public final TabLayout a;
    public final ViewPager2 b;
    public final boolean c;
    public final boolean d;
    public final b e;
    public RecyclerView.h f;
    public boolean g;
    public c h;
    public TabLayout.d i;
    public RecyclerView.j j;

    public b(TabLayout tabLayout, ViewPager2 viewPager2, b b3) {
        this(tabLayout, viewPager2, true, b3);
    }

    public b(TabLayout tabLayout, ViewPager2 viewPager2, boolean bl, b b3) {
        this(tabLayout, viewPager2, bl, true, b3);
    }

    public b(TabLayout tabLayout, ViewPager2 viewPager2, boolean bl, boolean bl2, b b3) {
        this.a = tabLayout;
        this.b = viewPager2;
        this.c = bl;
        this.d = bl2;
        this.e = b3;
    }

    public void a() {
        if (!this.g) {
            Object object = this.b.getAdapter();
            this.f = object;
            if (object != null) {
                this.g = true;
                this.h = object = new c(this.a);
                this.b.g((ViewPager2.i)object);
                this.i = object = new d(this.b, this.d);
                this.a.h((TabLayout.d)object);
                if (this.c) {
                    this.j = object = new a(this);
                    this.f.w((RecyclerView.j)object);
                }
                this.b();
                this.a.setScrollPosition(this.b.getCurrentItem(), 0.0f, true);
                return;
            }
            throw new IllegalStateException("TabLayoutMediator attached before ViewPager2 has an adapter");
        }
        throw new IllegalStateException("TabLayoutMediator is already attached");
    }

    public void b() {
        this.a.H();
        Object object = this.f;
        if (object != null) {
            int n3;
            int n4 = ((RecyclerView.h)object).f();
            for (n3 = 0; n3 < n4; ++n3) {
                object = this.a.E();
                this.e.a((TabLayout.f)object, n3);
                this.a.k((TabLayout.f)object, false);
            }
            if (n4 > 0) {
                n3 = this.a.getTabCount();
                n3 = Math.min(this.b.getCurrentItem(), n3 - 1);
                if (n3 != this.a.getSelectedTabPosition()) {
                    object = this.a;
                    ((TabLayout)((Object)object)).K(((TabLayout)((Object)object)).B(n3));
                }
            }
        }
    }

    public class a
    extends RecyclerView.j {
        public final b a;

        public a(b b3) {
            this.a = b3;
        }

        @Override
        public void a() {
            this.a.b();
        }

        @Override
        public void b(int n3, int n4, Object object) {
            this.a.b();
        }
    }

    public static interface b {
        public void a(TabLayout.f var1, int var2);
    }

    public static class c
    extends ViewPager2.i {
        public final WeakReference a;
        public int b;
        public int c;

        public c(TabLayout tabLayout) {
            this.a = new WeakReference<TabLayout>(tabLayout);
            this.d();
        }

        @Override
        public void a(int n3) {
            this.b = this.c;
            this.c = n3;
            TabLayout tabLayout = (TabLayout)((Object)this.a.get());
            if (tabLayout != null) {
                tabLayout.S(this.c);
            }
        }

        @Override
        public void b(int n3, float f3, int n4) {
            TabLayout tabLayout = (TabLayout)((Object)this.a.get());
            if (tabLayout != null) {
                boolean bl;
                n4 = this.c;
                boolean bl2 = true;
                if (n4 == 2 && this.b != 1) {
                    bl2 = false;
                }
                boolean bl3 = bl = true;
                if (n4 == 2) {
                    bl3 = this.b != 0 ? bl : false;
                }
                tabLayout.N(n3, f3, bl2, bl3, false);
            }
        }

        @Override
        public void c(int n3) {
            TabLayout tabLayout = (TabLayout)((Object)this.a.get());
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != n3 && n3 < tabLayout.getTabCount()) {
                int n4 = this.c;
                boolean bl = n4 == 0 || n4 == 2 && this.b == 0;
                tabLayout.L(tabLayout.B(n3), bl);
            }
        }

        public void d() {
            this.c = 0;
            this.b = 0;
        }
    }

    public static class d
    implements TabLayout.d {
        public final ViewPager2 a;
        public final boolean b;

        public d(ViewPager2 viewPager2, boolean bl) {
            this.a = viewPager2;
            this.b = bl;
        }

        @Override
        public void a(TabLayout.f f3) {
        }

        @Override
        public void b(TabLayout.f f3) {
        }

        @Override
        public void c(TabLayout.f f3) {
            this.a.setCurrentItem(f3.g(), this.b);
        }
    }
}

