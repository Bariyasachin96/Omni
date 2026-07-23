/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseBooleanArray
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.c0;
import androidx.appcompat.widget.r0;
import java.util.ArrayList;
import o0.b;

public class ActionMenuPresenter
extends androidx.appcompat.view.menu.a
implements b.a {
    public e A;
    public a B;
    public c C;
    public b D;
    public final f E;
    public int F;
    public d m;
    public Drawable n;
    public boolean o;
    public boolean p;
    public boolean q;
    public int r;
    public int s;
    public int t;
    public boolean u;
    public boolean v;
    public boolean w;
    public boolean x;
    public int y;
    public final SparseBooleanArray z = new SparseBooleanArray();

    public ActionMenuPresenter(Context context) {
        super(context, c.g.abc_action_menu_layout, c.g.abc_action_menu_item_layout);
        this.E = new f(this);
    }

    public boolean B() {
        return this.E() | this.F();
    }

    public final View C(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup)this.k;
        if (viewGroup == null) {
            return null;
        }
        int n3 = viewGroup.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = viewGroup.getChildAt(i3);
            if (!(view instanceof j.a) || ((j.a)view).getItemData() != menuItem) continue;
            return view;
        }
        return null;
    }

    public Drawable D() {
        d d3 = this.m;
        if (d3 != null) {
            return d3.getDrawable();
        }
        if (this.o) {
            return this.n;
        }
        return null;
    }

    public boolean E() {
        j j3;
        Object object = this.C;
        if (object != null && (j3 = this.k) != null) {
            ((View)j3).removeCallbacks((Runnable)object);
            this.C = null;
            return true;
        }
        object = this.A;
        if (object != null) {
            ((h)object).b();
            return true;
        }
        return false;
    }

    public boolean F() {
        a a4 = this.B;
        if (a4 != null) {
            a4.b();
            return true;
        }
        return false;
    }

    public boolean G() {
        return this.C != null || this.H();
        {
        }
    }

    public boolean H() {
        e e3 = this.A;
        return e3 != null && e3.d();
    }

    public void I(Configuration object) {
        if (!this.u) {
            this.t = h.a.b(this.d).d();
        }
        if ((object = this.e) != null) {
            ((androidx.appcompat.view.menu.e)object).N(true);
        }
    }

    public void J(boolean bl) {
        this.x = bl;
    }

    public void K(ActionMenuView actionMenuView) {
        this.k = actionMenuView;
        actionMenuView.b(this.e);
    }

    public void L(Drawable drawable) {
        d d3 = this.m;
        if (d3 != null) {
            d3.setImageDrawable(drawable);
            return;
        }
        this.o = true;
        this.n = drawable;
    }

    public void M(boolean bl) {
        this.p = bl;
        this.q = true;
    }

    public boolean N() {
        Object object;
        if (this.p && !this.H() && (object = this.e) != null && this.k != null && this.C == null && !((androidx.appcompat.view.menu.e)object).B().isEmpty()) {
            this.C = object = new c(this, new e(this, this.d, this.e, (View)this.m, true));
            ((View)this.k).post((Runnable)object);
            return true;
        }
        return false;
    }

    @Override
    public void a(androidx.appcompat.view.menu.e e3, boolean bl) {
        this.B();
        super.a(e3, bl);
    }

    @Override
    public void b(Context object, androidx.appcompat.view.menu.e e3) {
        super.b((Context)object, e3);
        e3 = object.getResources();
        object = h.a.b((Context)object);
        if (!this.q) {
            this.p = ((h.a)object).h();
        }
        if (!this.w) {
            this.r = ((h.a)object).c();
        }
        if (!this.u) {
            this.t = ((h.a)object).d();
        }
        int n3 = this.r;
        if (this.p) {
            if (this.m == null) {
                this.m = object = new d(this, this.c);
                if (this.o) {
                    ((AppCompatImageView)((Object)object)).setImageDrawable(this.n);
                    this.n = null;
                    this.o = false;
                }
                int n4 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                this.m.measure(n4, n4);
            }
            n3 -= this.m.getMeasuredWidth();
        } else {
            this.m = null;
        }
        this.s = n3;
        this.y = (int)(e3.getDisplayMetrics().density * 56.0f);
    }

    @Override
    public void c(boolean bl) {
        if (bl) {
            super.f(null);
            return;
        }
        androidx.appcompat.view.menu.e e3 = this.e;
        if (e3 != null) {
            e3.e(false);
        }
    }

    @Override
    public void d(Parcelable parcelable) {
        int n3;
        if (parcelable instanceof SavedState && (n3 = ((SavedState)parcelable).c) > 0 && (parcelable = this.e.findItem(n3)) != null) {
            this.f((l)parcelable.getSubMenu());
        }
    }

    @Override
    public boolean f(l l3) {
        boolean bl = l3.hasVisibleItems();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        Object object = l3;
        while (((l)object).j0() != this.e) {
            object = (l)((l)object).j0();
        }
        if ((object = this.C(((l)object).getItem())) == null) {
            return false;
        }
        this.F = l3.getItem().getItemId();
        int n3 = l3.size();
        int n4 = 0;
        while (true) {
            bl = bl2;
            if (n4 >= n3) break;
            MenuItem menuItem = l3.getItem(n4);
            if (menuItem.isVisible() && menuItem.getIcon() != null) {
                bl = true;
                break;
            }
            ++n4;
        }
        this.B = object = new a(this, this.d, l3, (View)object);
        ((h)object).g(bl);
        this.B.k();
        super.f(l3);
        return true;
    }

    @Override
    public void g(boolean bl) {
        o0.b b3;
        int n3;
        int n4;
        super.g(bl);
        ((View)this.k).requestLayout();
        Object object = this.e;
        int n5 = 0;
        if (object != null) {
            object = ((androidx.appcompat.view.menu.e)object).u();
            n4 = ((ArrayList)object).size();
            for (n3 = 0; n3 < n4; ++n3) {
                b3 = ((g)((ArrayList)object).get(n3)).a();
                if (b3 == null) continue;
                b3.h(this);
            }
        }
        object = (object = this.e) != null ? ((androidx.appcompat.view.menu.e)object).B() : null;
        n3 = n5;
        if (this.p) {
            n3 = n5;
            if (object != null) {
                n4 = ((ArrayList)object).size();
                if (n4 == 1) {
                    n3 = ((g)((ArrayList)object).get(0)).isActionViewExpanded() ^ 1;
                } else {
                    n3 = n5;
                    if (n4 > 0) {
                        n3 = 1;
                    }
                }
            }
        }
        if (n3 != 0) {
            if (this.m == null) {
                this.m = new d(this, this.c);
            }
            if ((object = (ViewGroup)this.m.getParent()) != this.k) {
                if (object != null) {
                    object.removeView((View)this.m);
                }
                object = (ActionMenuView)this.k;
                object.addView((View)this.m, (ViewGroup.LayoutParams)((ActionMenuView)object).D());
            }
        } else {
            object = this.m;
            if (object != null && (b3 = object.getParent()) == (object = this.k)) {
                ((ViewGroup)object).removeView((View)this.m);
            }
        }
        ((ActionMenuView)this.k).setOverflowReserved(this.p);
    }

    @Override
    public void h(g object, j.a a4) {
        a4.d((g)object, 0);
        object = (ActionMenuView)this.k;
        a4 = (ActionMenuItemView)a4;
        ((ActionMenuItemView)a4).setItemInvoker((e.b)object);
        if (this.D == null) {
            this.D = new b(this);
        }
        ((ActionMenuItemView)a4).setPopupCallback(this.D);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean i() {
        int n3;
        g g3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        ViewGroup viewGroup;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        Object object;
        block38: {
            block39: {
                object = this.e;
                n13 = 0;
                if (object != null) {
                    object = ((androidx.appcompat.view.menu.e)object).G();
                    n12 = ((ArrayList)object).size();
                } else {
                    object = null;
                    n12 = 0;
                }
                n11 = this.t;
                n10 = this.s;
                n9 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                viewGroup = (ViewGroup)this.k;
                n6 = n8 = (n7 = 0);
                n5 = n8;
                n8 = n11;
                for (n4 = 0; n4 < n12; ++n4) {
                    g3 = (g)((ArrayList)object).get(n4);
                    if (g3.o()) {
                        ++n5;
                    } else if (g3.n()) {
                        ++n6;
                    } else {
                        n7 = 1;
                    }
                    n11 = n8;
                    if (this.x) {
                        n11 = n8;
                        if (g3.isActionViewExpanded()) {
                            n11 = 0;
                        }
                    }
                    n8 = n11;
                }
                n4 = n8;
                if (!this.p) break block38;
                if (n7 != 0) break block39;
                n4 = n8;
                if (n6 + n5 <= n8) break block38;
            }
            n4 = n8 - 1;
        }
        n6 = n4 - n5;
        SparseBooleanArray sparseBooleanArray = this.z;
        sparseBooleanArray.clear();
        if (this.v) {
            n8 = this.y;
            n5 = n10 / n8;
            n3 = n8 + n10 % n8 / n5;
        } else {
            n3 = 0;
            n5 = 0;
        }
        int n14 = 0;
        n8 = 0;
        n7 = n10;
        n10 = n12;
        n12 = n13;
        while (n14 < n10) {
            Object object2;
            g3 = (g)((ArrayList)object).get(n14);
            if (g3.o()) {
                object2 = this.q(g3, null, viewGroup);
                if (this.v) {
                    n5 -= ActionMenuView.J((View)object2, n3, n5, n9, n12);
                } else {
                    object2.measure(n9, n9);
                }
                n11 = object2.getMeasuredWidth();
                n7 -= n11;
                n4 = n8;
                if (n8 == 0) {
                    n4 = n11;
                }
                if ((n8 = g3.getGroupId()) != 0) {
                    sparseBooleanArray.put(n8, true);
                }
                g3.u(true);
                n8 = n12;
            } else if (g3.n()) {
                n13 = g3.getGroupId();
                boolean bl = sparseBooleanArray.get(n13);
                int n15 = !(n6 <= 0 && !bl || n7 <= 0 || this.v && n5 <= 0) ? 1 : 0;
                int n16 = n15;
                int n17 = n15;
                n11 = n7;
                n12 = n5;
                n4 = n8;
                if (n15 != 0) {
                    object2 = this.q(g3, null, viewGroup);
                    if (this.v) {
                        n4 = ActionMenuView.J((View)object2, n3, n5, n9, 0);
                        n5 = n12 = n5 - n4;
                        if (n4 == 0) {
                            n16 = 0;
                            n5 = n12;
                        }
                    } else {
                        object2.measure(n9, n9);
                    }
                    n12 = object2.getMeasuredWidth();
                    n11 = n7 - n12;
                    n4 = n8;
                    if (n8 == 0) {
                        n4 = n12;
                    }
                    n8 = (this.v ? n11 >= 0 : n11 + n4 > 0) ? 1 : 0;
                    n17 = n16 & n8;
                    n12 = n5;
                }
                if (n17 != 0 && n13 != 0) {
                    sparseBooleanArray.put(n13, true);
                    n8 = n6;
                } else {
                    n8 = n6;
                    if (bl) {
                        sparseBooleanArray.put(n13, false);
                        n5 = 0;
                        while (true) {
                            n8 = n6;
                            if (n5 >= n14) break;
                            object2 = (g)((ArrayList)object).get(n5);
                            n8 = n6;
                            if (((g)object2).getGroupId() == n13) {
                                n8 = n6;
                                if (((g)object2).l()) {
                                    n8 = n6 + 1;
                                }
                                ((g)object2).u(false);
                            }
                            ++n5;
                            n6 = n8;
                        }
                    }
                }
                n5 = n8;
                if (n17 != 0) {
                    n5 = n8 - 1;
                }
                g3.u(n17 != 0);
                n8 = 0;
                n6 = n5;
                n7 = n11;
                n5 = n12;
            } else {
                g3.u(n12 != 0);
                n4 = n8;
                n8 = n12;
            }
            ++n14;
            n12 = n8;
            n8 = n4;
        }
        return true;
    }

    @Override
    public Parcelable j() {
        SavedState savedState = new SavedState();
        savedState.c = this.F;
        return savedState;
    }

    @Override
    public boolean o(ViewGroup viewGroup, int n3) {
        if (viewGroup.getChildAt(n3) == this.m) {
            return false;
        }
        return super.o(viewGroup, n3);
    }

    @Override
    public View q(g object, View view, ViewGroup viewGroup) {
        View view2 = ((g)object).getActionView();
        if (view2 == null || ((g)object).j()) {
            view2 = super.q((g)object, view, viewGroup);
        }
        int n3 = ((g)object).isActionViewExpanded() ? 8 : 0;
        view2.setVisibility(n3);
        object = (ActionMenuView)viewGroup;
        view = view2.getLayoutParams();
        if (!((ActionMenuView)object).checkLayoutParams((ViewGroup.LayoutParams)view)) {
            view2.setLayoutParams((ViewGroup.LayoutParams)((ActionMenuView)object).C((ViewGroup.LayoutParams)view));
        }
        return view2;
    }

    @Override
    public j r(ViewGroup object) {
        j j3 = this.k;
        if (j3 != (object = super.r((ViewGroup)object))) {
            ((ActionMenuView)object).setPresenter(this);
        }
        return object;
    }

    @Override
    public boolean t(int n3, g g3) {
        return g3.l();
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;

        public SavedState() {
        }

        public SavedState(Parcel parcel) {
            this.c = parcel.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeInt(this.c);
        }
    }

    public class a
    extends h {
        public final ActionMenuPresenter m;

        public a(ActionMenuPresenter actionMenuPresenter, Context object, l object2, View view) {
            this.m = actionMenuPresenter;
            super((Context)object, (androidx.appcompat.view.menu.e)object2, view, false, c.a.actionOverflowMenuStyle);
            if (!((g)((l)object2).getItem()).l()) {
                object2 = actionMenuPresenter.m;
                object = object2;
                if (object2 == null) {
                    object = (View)actionMenuPresenter.k;
                }
                this.f((View)object);
            }
            this.j(actionMenuPresenter.E);
        }

        @Override
        public void e() {
            ActionMenuPresenter actionMenuPresenter = this.m;
            actionMenuPresenter.B = null;
            actionMenuPresenter.F = 0;
            super.e();
        }
    }

    public class b
    extends ActionMenuItemView.b {
        public final ActionMenuPresenter a;

        public b(ActionMenuPresenter actionMenuPresenter) {
            this.a = actionMenuPresenter;
        }

        @Override
        public i.f a() {
            a a4 = this.a.B;
            if (a4 != null) {
                return a4.c();
            }
            return null;
        }
    }

    public class c
    implements Runnable {
        public e c;
        public final ActionMenuPresenter d;

        public c(ActionMenuPresenter actionMenuPresenter, e e3) {
            this.d = actionMenuPresenter;
            this.c = e3;
        }

        @Override
        public void run() {
            View view;
            if (this.d.e != null) {
                this.d.e.d();
            }
            if ((view = (View)this.d.k) != null && view.getWindowToken() != null && this.c.m()) {
                this.d.A = this.c;
            }
            this.d.C = null;
        }
    }

    public class d
    extends AppCompatImageView
    implements ActionMenuView.a {
        public final ActionMenuPresenter f;

        public d(ActionMenuPresenter actionMenuPresenter, Context context) {
            this.f = actionMenuPresenter;
            super(context, null, c.a.actionOverflowButtonStyle);
            this.setClickable(true);
            this.setFocusable(true);
            this.setVisibility(0);
            this.setEnabled(true);
            r0.a((View)this, this.getContentDescription());
            this.setOnTouchListener(new c0(this, (View)this, actionMenuPresenter){
                public final ActionMenuPresenter l;
                public final d m;
                {
                    this.m = d3;
                    this.l = actionMenuPresenter;
                    super(view);
                }

                @Override
                public i.f b() {
                    e e3 = this.m.f.A;
                    if (e3 == null) {
                        return null;
                    }
                    return e3.c();
                }

                @Override
                public boolean c() {
                    this.m.f.N();
                    return true;
                }

                @Override
                public boolean d() {
                    ActionMenuPresenter actionMenuPresenter = this.m.f;
                    if (actionMenuPresenter.C != null) {
                        return false;
                    }
                    actionMenuPresenter.E();
                    return true;
                }
            });
        }

        @Override
        public boolean a() {
            return false;
        }

        @Override
        public boolean b() {
            return false;
        }

        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            this.playSoundEffect(0);
            this.f.N();
            return true;
        }

        public boolean setFrame(int n3, int n4, int n5, int n6) {
            boolean bl = super.setFrame(n3, n4, n5, n6);
            Drawable drawable = this.getDrawable();
            Drawable drawable2 = this.getBackground();
            if (drawable != null && drawable2 != null) {
                int n7 = this.getWidth();
                n5 = this.getHeight();
                n3 = Math.max(n7, n5) / 2;
                int n8 = this.getPaddingLeft();
                int n9 = this.getPaddingRight();
                n4 = this.getPaddingTop();
                n6 = this.getPaddingBottom();
                n7 = (n7 + (n8 - n9)) / 2;
                n4 = (n5 + (n4 - n6)) / 2;
                h0.a.l(drawable2, n7 - n3, n4 - n3, n7 + n3, n4 + n3);
            }
            return bl;
        }
    }

    public class e
    extends h {
        public final ActionMenuPresenter m;

        public e(ActionMenuPresenter actionMenuPresenter, Context context, androidx.appcompat.view.menu.e e3, View view, boolean bl) {
            this.m = actionMenuPresenter;
            super(context, e3, view, bl, c.a.actionOverflowMenuStyle);
            this.h(0x800005);
            this.j(actionMenuPresenter.E);
        }

        @Override
        public void e() {
            if (this.m.e != null) {
                this.m.e.close();
            }
            this.m.A = null;
            super.e();
        }
    }

    public class f
    implements i.a {
        public final ActionMenuPresenter c;

        public f(ActionMenuPresenter actionMenuPresenter) {
            this.c = actionMenuPresenter;
        }

        @Override
        public void a(androidx.appcompat.view.menu.e e3, boolean bl) {
            i.a a4;
            if (e3 instanceof l) {
                e3.F().e(false);
            }
            if ((a4 = this.c.p()) != null) {
                a4.a(e3, bl);
            }
        }

        @Override
        public boolean b(androidx.appcompat.view.menu.e e3) {
            if (e3 == this.c.e) {
                return false;
            }
            this.c.F = ((l)e3).getItem().getItemId();
            i.a a4 = this.c.p();
            if (a4 != null) {
                return a4.b(e3);
            }
            return false;
        }
    }
}

