/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.i;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.internal.ParcelableSparseArray;
import java.util.ArrayList;
import o0.a;
import o0.x0;
import o0.z1;
import p0.s;

public class q
implements androidx.appcompat.view.menu.i {
    public boolean A = true;
    public int B;
    public int C;
    public int D;
    public int E = -1;
    public final View.OnClickListener F = new View.OnClickListener(this){
        public final q c;
        {
            this.c = q3;
        }

        public void onClick(View object) {
            object = (NavigationMenuItemView)object;
            q q3 = this.c;
            boolean bl = true;
            q3.Y(true);
            object = ((NavigationMenuItemView)object).getItemData();
            q3 = this.c;
            boolean bl2 = q3.f.P((MenuItem)object, q3, 0);
            if (object != null && ((androidx.appcompat.view.menu.g)object).isCheckable() && bl2) {
                this.c.h.O((androidx.appcompat.view.menu.g)object);
            } else {
                bl = false;
            }
            this.c.Y(false);
            if (bl) {
                this.c.g(false);
            }
        }
    };
    public NavigationMenuView c;
    public LinearLayout d;
    public i.a e;
    public androidx.appcompat.view.menu.e f;
    public int g;
    public c h;
    public LayoutInflater i;
    public int j = 0;
    public ColorStateList k;
    public int l = 0;
    public boolean m = true;
    public ColorStateList n;
    public ColorStateList o;
    public Drawable p;
    public RippleDrawable q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public boolean z;

    public int A() {
        return this.x;
    }

    public final boolean B() {
        return this.q() > 0;
    }

    public View C(int n3) {
        View view = this.i.inflate(n3, (ViewGroup)this.d, false);
        this.e(view);
        return view;
    }

    public void D(boolean bl) {
        if (this.A != bl) {
            this.A = bl;
            this.c0();
        }
    }

    public void E(androidx.appcompat.view.menu.g g3) {
        this.h.O(g3);
    }

    public void F(int n3) {
        this.w = n3;
        this.Z();
    }

    public void G(int n3) {
        this.v = n3;
        this.Z();
    }

    public void H(int n3) {
        this.g = n3;
    }

    public void I(Drawable drawable) {
        this.p = drawable;
        this.b0();
    }

    public void J(RippleDrawable rippleDrawable) {
        this.q = rippleDrawable;
        this.b0();
    }

    public void K(int n3) {
        this.r = n3;
        this.b0();
    }

    public void L(int n3) {
        this.t = n3;
        this.b0();
    }

    public void M(int n3) {
        if (this.u != n3) {
            this.u = n3;
            this.z = true;
            this.b0();
        }
    }

    public void N(ColorStateList colorStateList) {
        this.o = colorStateList;
        this.b0();
    }

    public void O(int n3) {
        this.B = n3;
        this.b0();
    }

    public void P(int n3) {
        this.l = n3;
        this.b0();
    }

    public void Q(boolean bl) {
        this.m = bl;
        this.b0();
    }

    public void R(ColorStateList colorStateList) {
        this.n = colorStateList;
        this.b0();
    }

    public void S(int n3) {
        this.s = n3;
        this.b0();
    }

    public void T(int n3) {
        this.E = n3;
        NavigationMenuView navigationMenuView = this.c;
        if (navigationMenuView != null) {
            navigationMenuView.setOverScrollMode(n3);
        }
    }

    public void U(ColorStateList colorStateList) {
        this.k = colorStateList;
        this.a0();
    }

    public void V(int n3) {
        this.y = n3;
        this.a0();
    }

    public void W(int n3) {
        this.x = n3;
        this.a0();
    }

    public void X(int n3) {
        this.j = n3;
        this.a0();
    }

    public void Y(boolean bl) {
        c c3 = this.h;
        if (c3 != null) {
            c3.P(bl);
        }
    }

    public final void Z() {
        c c3 = this.h;
        if (c3 != null) {
            c3.R();
        }
    }

    @Override
    public void a(androidx.appcompat.view.menu.e e3, boolean bl) {
        i.a a4 = this.e;
        if (a4 != null) {
            a4.a(e3, bl);
        }
    }

    public final void a0() {
        c c3 = this.h;
        if (c3 != null) {
            c3.S();
        }
    }

    @Override
    public void b(Context context, androidx.appcompat.view.menu.e e3) {
        this.i = LayoutInflater.from((Context)context);
        this.f = e3;
        this.D = context.getResources().getDimensionPixelOffset(z1.e.design_navigation_separator_vertical_padding);
    }

    public final void b0() {
        c c3 = this.h;
        if (c3 != null) {
            c3.T();
        }
    }

    public final void c0() {
        int n3 = !this.B() && this.A ? this.C : 0;
        NavigationMenuView navigationMenuView = this.c;
        navigationMenuView.setPadding(0, n3, 0, navigationMenuView.getPaddingBottom());
    }

    @Override
    public void d(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            SparseArray sparseArray = (parcelable = (Bundle)parcelable).getSparseParcelableArray("android:menu:list");
            if (sparseArray != null) {
                this.c.restoreHierarchyState(sparseArray);
            }
            if ((sparseArray = parcelable.getBundle("android:menu:adapter")) != null) {
                this.h.M((Bundle)sparseArray);
            }
            if ((parcelable = parcelable.getSparseParcelableArray("android:menu:header")) != null) {
                this.d.restoreHierarchyState((SparseArray)parcelable);
            }
        }
    }

    public void e(View object) {
        this.d.addView(object);
        object = this.c;
        object.setPadding(0, 0, 0, object.getPaddingBottom());
    }

    @Override
    public boolean f(androidx.appcompat.view.menu.l l3) {
        return false;
    }

    @Override
    public void g(boolean bl) {
        c c3 = this.h;
        if (c3 != null) {
            c3.Q();
        }
    }

    @Override
    public int getId() {
        return this.g;
    }

    public void h(z1 z12) {
        int n3 = z12.l();
        if (this.C != n3) {
            this.C = n3;
            this.c0();
        }
        NavigationMenuView navigationMenuView = this.c;
        navigationMenuView.setPadding(0, navigationMenuView.getPaddingTop(), 0, z12.i());
        x0.g((View)this.d, z12);
    }

    @Override
    public boolean i() {
        return false;
    }

    @Override
    public Parcelable j() {
        c c3;
        Bundle bundle = new Bundle();
        if (this.c != null) {
            c3 = new SparseArray();
            this.c.saveHierarchyState((SparseArray)c3);
            bundle.putSparseParcelableArray("android:menu:list", (SparseArray)c3);
        }
        if ((c3 = this.h) != null) {
            bundle.putBundle("android:menu:adapter", c3.F());
        }
        if (this.d != null) {
            c3 = new SparseArray();
            this.d.saveHierarchyState((SparseArray)c3);
            bundle.putSparseParcelableArray("android:menu:header", (SparseArray)c3);
        }
        return bundle;
    }

    @Override
    public boolean k(androidx.appcompat.view.menu.e e3, androidx.appcompat.view.menu.g g3) {
        return false;
    }

    @Override
    public boolean l(androidx.appcompat.view.menu.e e3, androidx.appcompat.view.menu.g g3) {
        return false;
    }

    public androidx.appcompat.view.menu.g n() {
        return this.h.G();
    }

    public int o() {
        return this.w;
    }

    public int p() {
        return this.v;
    }

    public int q() {
        return this.d.getChildCount();
    }

    public Drawable r() {
        return this.p;
    }

    public int s() {
        return this.r;
    }

    public int t() {
        return this.t;
    }

    public int u() {
        return this.B;
    }

    public ColorStateList v() {
        return this.n;
    }

    public ColorStateList w() {
        return this.o;
    }

    public int x() {
        return this.s;
    }

    public androidx.appcompat.view.menu.j y(ViewGroup object) {
        if (this.c == null) {
            int n3;
            object = (NavigationMenuView)this.i.inflate(z1.i.design_navigation_menu, (ViewGroup)object, false);
            this.c = object;
            ((RecyclerView)object).setAccessibilityDelegateCompat(new h(this, this.c));
            if (this.h == null) {
                object = new c(this);
                this.h = object;
                ((RecyclerView.h)object).x(true);
            }
            if ((n3 = this.E) != -1) {
                this.c.setOverScrollMode(n3);
            }
            object = (LinearLayout)this.i.inflate(z1.i.design_navigation_item_header, (ViewGroup)this.c, false);
            this.d = object;
            object.setImportantForAccessibility(2);
            this.c.setAdapter(this.h);
        }
        return this.c;
    }

    public int z() {
        return this.y;
    }

    public static class b
    extends l {
        public b(View view) {
            super(view);
        }
    }

    public class c
    extends RecyclerView.h {
        public final ArrayList d;
        public androidx.appcompat.view.menu.g e;
        public boolean f;
        public final q g;

        public c(q q3) {
            this.g = q3;
            this.d = new ArrayList();
            this.L();
        }

        public final int D(int n3) {
            int n4 = n3;
            for (int i3 = 0; i3 < n3; ++i3) {
                int n5;
                block4: {
                    block3: {
                        if (this.g.h.h(i3) == 2) break block3;
                        n5 = n4;
                        if (this.g.h.h(i3) != 3) break block4;
                    }
                    n5 = n4 - 1;
                }
                n4 = n5;
            }
            return n4;
        }

        public final void E(int n3, int n4) {
            while (n3 < n4) {
                ((g)this.d.get((int)n3)).b = true;
                ++n3;
            }
        }

        public Bundle F() {
            Bundle bundle = new Bundle();
            Object object = this.e;
            if (object != null) {
                bundle.putInt("android:menu:checked", ((androidx.appcompat.view.menu.g)object).getItemId());
            }
            SparseArray sparseArray = new SparseArray();
            int n3 = this.d.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                androidx.appcompat.view.menu.g g3;
                object = (e)this.d.get(i3);
                if (!(object instanceof g) || (object = (g3 = ((g)object).a()) != null ? g3.getActionView() : null) == null) continue;
                ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                object.saveHierarchyState((SparseArray)parcelableSparseArray);
                sparseArray.put(g3.getItemId(), (Object)parcelableSparseArray);
            }
            bundle.putSparseParcelableArray("android:menu:action_views", sparseArray);
            return bundle;
        }

        public androidx.appcompat.view.menu.g G() {
            return this.e;
        }

        public int H() {
            int n3 = 0;
            for (int i3 = 0; i3 < this.g.h.f(); ++i3) {
                int n4;
                block4: {
                    block3: {
                        int n5 = this.g.h.h(i3);
                        if (n5 == 0) break block3;
                        n4 = n3;
                        if (n5 != 1) break block4;
                    }
                    n4 = n3 + 1;
                }
                n3 = n4;
            }
            return n3;
        }

        public void I(l object, int n3) {
            int n4 = this.h(n3);
            if (n4 != 0) {
                if (n4 != 1) {
                    if (n4 != 2) {
                        return;
                    }
                    f f3 = (f)this.d.get(n3);
                    ((RecyclerView.d0)object).a.setPaddingRelative(this.g.v, f3.b(), this.g.w, f3.a());
                    return;
                }
                object = (TextView)((RecyclerView.d0)object).a;
                object.setText(((g)this.d.get(n3)).a().getTitle());
                androidx.core.widget.j.m((TextView)object, this.g.j);
                object.setPaddingRelative(this.g.x, object.getPaddingTop(), this.g.y, object.getPaddingBottom());
                ColorStateList colorStateList = this.g.k;
                if (colorStateList != null) {
                    object.setTextColor(colorStateList);
                }
                this.N((View)object, n3, true);
                return;
            }
            NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)((RecyclerView.d0)object).a;
            navigationMenuItemView.setIconTintList(this.g.o);
            navigationMenuItemView.setTextAppearance(this.g.l);
            object = this.g.n;
            if (object != null) {
                navigationMenuItemView.setTextColor((ColorStateList)object);
            }
            object = (object = this.g.p) != null ? object.getConstantState().newDrawable() : null;
            navigationMenuItemView.setBackground((Drawable)object);
            object = this.g.q;
            if (object != null) {
                navigationMenuItemView.setForeground(object.getConstantState().newDrawable());
            }
            object = (g)this.d.get(n3);
            navigationMenuItemView.setNeedsEmptyIcon(((g)object).b);
            q q3 = this.g;
            n4 = q3.r;
            int n5 = q3.s;
            navigationMenuItemView.setPadding(n4, n5, n4, n5);
            navigationMenuItemView.setIconPadding(this.g.t);
            q3 = this.g;
            if (q3.z) {
                navigationMenuItemView.setIconSize(q3.u);
            }
            navigationMenuItemView.setMaxLines(this.g.B);
            navigationMenuItemView.B(((g)object).a(), this.g.m);
            this.N((View)navigationMenuItemView, n3, false);
        }

        public l J(ViewGroup viewGroup, int n3) {
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 3) {
                            return null;
                        }
                        return new b((View)this.g.d);
                    }
                    return new j(this.g.i, viewGroup);
                }
                return new k(this.g.i, viewGroup);
            }
            q q3 = this.g;
            return new i(q3.i, viewGroup, q3.F);
        }

        public void K(l l3) {
            if (l3 instanceof i) {
                ((NavigationMenuItemView)l3.a).C();
            }
        }

        public final void L() {
            int n3;
            if (this.f) {
                return;
            }
            this.f = true;
            this.d.clear();
            this.d.add(new d());
            int n4 = this.g.f.G().size();
            int n5 = -1;
            int n6 = n3 = 0;
            for (int i3 = 0; i3 < n4; ++i3) {
                int n7;
                int n8;
                int n9;
                int n10;
                Object object;
                Object object2 = (androidx.appcompat.view.menu.g)this.g.f.G().get(i3);
                if (((androidx.appcompat.view.menu.g)object2).isChecked()) {
                    this.O((androidx.appcompat.view.menu.g)object2);
                }
                if (((androidx.appcompat.view.menu.g)object2).isCheckable()) {
                    ((androidx.appcompat.view.menu.g)object2).t(false);
                }
                if (((androidx.appcompat.view.menu.g)object2).hasSubMenu()) {
                    object = ((androidx.appcompat.view.menu.g)object2).getSubMenu();
                    n10 = n5;
                    n9 = n3;
                    n8 = n6;
                    if (object.hasVisibleItems()) {
                        if (i3 != 0) {
                            this.d.add(new f(this.g.D, 0));
                        }
                        this.d.add(new g((androidx.appcompat.view.menu.g)object2));
                        int n11 = this.d.size();
                        n10 = object.size();
                        n7 = 0;
                        for (n9 = 0; n9 < n10; ++n9) {
                            object2 = (androidx.appcompat.view.menu.g)object.getItem(n9);
                            n8 = n7;
                            if (((androidx.appcompat.view.menu.g)object2).isVisible()) {
                                n8 = n7;
                                if (n7 == 0) {
                                    n8 = n7;
                                    if (((androidx.appcompat.view.menu.g)object2).getIcon() != null) {
                                        n8 = 1;
                                    }
                                }
                                if (((androidx.appcompat.view.menu.g)object2).isCheckable()) {
                                    ((androidx.appcompat.view.menu.g)object2).t(false);
                                }
                                if (((androidx.appcompat.view.menu.g)object2).isChecked()) {
                                    this.O((androidx.appcompat.view.menu.g)object2);
                                }
                                this.d.add(new g((androidx.appcompat.view.menu.g)object2));
                            }
                            n7 = n8;
                        }
                        n10 = n5;
                        n9 = n3;
                        n8 = n6;
                        if (n7 != 0) {
                            this.E(n11, this.d.size());
                            n10 = n5;
                            n9 = n3;
                            n8 = n6;
                        }
                    }
                } else {
                    n10 = ((androidx.appcompat.view.menu.g)object2).getGroupId();
                    if (n10 != n5) {
                        n3 = this.d.size();
                        n6 = ((androidx.appcompat.view.menu.g)object2).getIcon() != null ? 1 : 0;
                        n7 = n6;
                        n8 = n3;
                        if (i3 != 0) {
                            n8 = n3 + 1;
                            object = this.d;
                            n7 = this.g.D;
                            ((ArrayList)object).add(new f(n7, n7));
                            n7 = n6;
                        }
                    } else {
                        n7 = n3;
                        n8 = n6;
                        if (n3 == 0) {
                            n7 = n3;
                            n8 = n6;
                            if (((androidx.appcompat.view.menu.g)object2).getIcon() != null) {
                                this.E(n6, this.d.size());
                                n7 = 1;
                                n8 = n6;
                            }
                        }
                    }
                    object2 = new g((androidx.appcompat.view.menu.g)object2);
                    ((g)object2).b = n7;
                    this.d.add(object2);
                    n9 = n7;
                }
                n5 = n10;
                n3 = n9;
                n6 = n8;
            }
            this.f = false;
        }

        public void M(Bundle bundle) {
            Object object;
            int n3;
            int n4 = 0;
            int n5 = bundle.getInt("android:menu:checked", 0);
            if (n5 != 0) {
                this.f = true;
                int n6 = this.d.size();
                for (n3 = 0; n3 < n6; ++n3) {
                    object = (e)this.d.get(n3);
                    if (!(object instanceof g) || (object = ((g)object).a()) == null || ((androidx.appcompat.view.menu.g)object).getItemId() != n5) continue;
                    this.O((androidx.appcompat.view.menu.g)object);
                    break;
                }
                this.f = false;
                this.L();
            }
            if ((bundle = bundle.getSparseParcelableArray("android:menu:action_views")) != null) {
                n5 = this.d.size();
                for (n3 = n4; n3 < n5; ++n3) {
                    Object object2;
                    object = (e)this.d.get(n3);
                    if (!(object instanceof g) || (object2 = ((g)object).a()) == null || (object = object2.getActionView()) == null || (object2 = (ParcelableSparseArray)((Object)bundle.get(object2.getItemId()))) == null) continue;
                    object.restoreHierarchyState((SparseArray)object2);
                }
            }
        }

        public final void N(View view, int n3, boolean bl) {
            x0.h0(view, new a(this, n3, bl){
                public final int d;
                public final boolean e;
                public final c f;
                {
                    this.f = c3;
                    this.d = n3;
                    this.e = bl;
                }

                @Override
                public void g(View view, s s3) {
                    super.g(view, s3);
                    s3.k0(s.f.a(this.f.D(this.d), 1, 1, 1, this.e, view.isSelected()));
                }
            });
        }

        public void O(androidx.appcompat.view.menu.g g3) {
            if (this.e != g3 && g3.isCheckable()) {
                androidx.appcompat.view.menu.g g4 = this.e;
                if (g4 != null) {
                    g4.setChecked(false);
                }
                this.e = g3;
                g3.setChecked(true);
            }
        }

        public void P(boolean bl) {
            this.f = bl;
        }

        public void Q() {
            int n3 = this.d.size();
            this.L();
            this.k();
            if (n3 == this.d.size()) {
                this.m(0, this.d.size());
            }
        }

        public final void R() {
            for (int i3 = 0; i3 < this.d.size(); ++i3) {
                if (!(this.d.get(i3) instanceof f)) continue;
                this.l(i3);
            }
        }

        public final void S() {
            for (int i3 = 0; i3 < this.d.size(); ++i3) {
                if (!(this.d.get(i3) instanceof g) || this.h(i3) != 1) continue;
                this.l(i3);
            }
        }

        public final void T() {
            for (int i3 = 0; i3 < this.d.size(); ++i3) {
                if (!(this.d.get(i3) instanceof g) || this.h(i3) != 0) continue;
                this.l(i3);
            }
        }

        @Override
        public int f() {
            return this.d.size();
        }

        @Override
        public long g(int n3) {
            return n3;
        }

        @Override
        public int h(int n3) {
            e e3 = (e)this.d.get(n3);
            if (e3 instanceof f) {
                return 2;
            }
            if (e3 instanceof d) {
                return 3;
            }
            if (e3 instanceof g) {
                if (((g)e3).a().hasSubMenu()) {
                    return 1;
                }
                return 0;
            }
            throw new RuntimeException("Unknown item type.");
        }
    }

    public static class d
    implements e {
    }

    public static interface e {
    }

    public static class f
    implements e {
        public final int a;
        public final int b;

        public f(int n3, int n4) {
            this.a = n3;
            this.b = n4;
        }

        public int a() {
            return this.b;
        }

        public int b() {
            return this.a;
        }
    }

    public static class g
    implements e {
        public final androidx.appcompat.view.menu.g a;
        public boolean b;

        public g(androidx.appcompat.view.menu.g g3) {
            this.a = g3;
        }

        public androidx.appcompat.view.menu.g a() {
            return this.a;
        }
    }

    public class h
    extends androidx.recyclerview.widget.k {
        public final q f;

        public h(q q3, RecyclerView recyclerView) {
            this.f = q3;
            super(recyclerView);
        }

        @Override
        public void g(View view, s s3) {
            super.g(view, s3);
            s3.j0(s.e.a(this.f.h.H(), 1, false));
        }
    }

    public static class i
    extends l {
        public i(LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(z1.i.design_navigation_item, viewGroup, false));
            this.a.setOnClickListener(onClickListener);
        }
    }

    public static class j
    extends l {
        public j(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(z1.i.design_navigation_item_separator, viewGroup, false));
        }
    }

    public static class k
    extends l {
        public k(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(z1.i.design_navigation_item_subheader, viewGroup, false));
        }
    }

    public static abstract class l
    extends RecyclerView.d0 {
        public l(View view) {
            super(view);
        }
    }
}

