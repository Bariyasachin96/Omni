/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.KeyCharacterMap
 *  android.view.KeyEvent
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.s;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.u;
import c.a;
import c.f;
import c.j;
import h.b;
import h.g;
import h.h;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import o0.h1;
import o0.i1;
import o0.j1;
import o0.k1;
import o0.x0;

public class t
extends ActionBar
implements ActionBarOverlayLayout.d {
    public static final Interpolator E = new AccelerateInterpolator();
    public static final Interpolator F = new DecelerateInterpolator();
    public boolean A;
    public final i1 B;
    public final i1 C;
    public final k1 D;
    public Context a;
    public Context b;
    public Activity c;
    public ActionBarOverlayLayout d;
    public ActionBarContainer e;
    public u f;
    public ActionBarContextView g;
    public View h;
    public ScrollingTabContainerView i;
    public ArrayList j = new ArrayList();
    public int k = -1;
    public boolean l;
    public d m;
    public b n;
    public b.a o;
    public boolean p;
    public ArrayList q = new ArrayList();
    public boolean r;
    public int s = 0;
    public boolean t = true;
    public boolean u;
    public boolean v;
    public boolean w;
    public boolean x = true;
    public h y;
    public boolean z;

    public t(Activity activity, boolean bl) {
        this.B = new j1(this){
            public final t a;
            {
                this.a = t3;
            }

            @Override
            public void b(View object) {
                object = this.a;
                if (((t)object).t && (object = ((t)object).h) != null) {
                    object.setTranslationY(0.0f);
                    this.a.e.setTranslationY(0.0f);
                }
                this.a.e.setVisibility(8);
                this.a.e.setTransitioning(false);
                object = this.a;
                ((t)object).y = null;
                ((t)object).w();
                object = this.a.d;
                if (object != null) {
                    x0.e0((View)object);
                }
            }
        };
        this.C = new j1(this){
            public final t a;
            {
                this.a = t3;
            }

            @Override
            public void b(View object) {
                object = this.a;
                object.y = null;
                object.e.requestLayout();
            }
        };
        this.D = new k1(this){
            public final t a;
            {
                this.a = t3;
            }

            @Override
            public void a(View view) {
                ((View)this.a.e.getParent()).invalidate();
            }
        };
        this.c = activity;
        activity = activity.getWindow().getDecorView();
        this.C((View)activity);
        if (!bl) {
            this.h = activity.findViewById(0x1020002);
        }
    }

    public t(Dialog dialog) {
        this.B = new /* invalid duplicate definition of identical inner class */;
        this.C = new /* invalid duplicate definition of identical inner class */;
        this.D = new /* invalid duplicate definition of identical inner class */;
        this.C(dialog.getWindow().getDecorView());
    }

    public static boolean v(boolean bl, boolean bl2, boolean bl3) {
        if (bl3) {
            return true;
        }
        return !bl && !bl2;
        {
        }
    }

    public int A() {
        return this.f.n();
    }

    public final void B() {
        if (this.w) {
            this.w = false;
            ActionBarOverlayLayout actionBarOverlayLayout = this.d;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(false);
            }
            this.L(false);
        }
    }

    public final void C(View object) {
        Object object2 = (ActionBarOverlayLayout)object.findViewById(c.f.decor_content_parent);
        this.d = object2;
        if (object2 != null) {
            ((ActionBarOverlayLayout)object2).setActionBarVisibilityCallback(this);
        }
        this.f = this.z(object.findViewById(c.f.action_bar));
        this.g = (ActionBarContextView)object.findViewById(c.f.action_context_bar);
        object = (ActionBarContainer)object.findViewById(c.f.action_bar_container);
        this.e = object;
        object2 = this.f;
        if (object2 != null && this.g != null && object != null) {
            this.a = object2.b();
            int n3 = (this.f.l() & 4) != 0 ? 1 : 0;
            if (n3 != 0) {
                this.l = true;
            }
            boolean bl = ((h.a)(object = h.a.b(this.a))).a() || n3 != 0;
            this.I(bl);
            this.G(((h.a)object).g());
            object = this.a.obtainStyledAttributes(null, c.j.ActionBar, c.a.actionBarStyle, 0);
            if (object.getBoolean(c.j.ActionBar_hideOnContentScroll, false)) {
                this.H(true);
            }
            if ((n3 = object.getDimensionPixelSize(c.j.ActionBar_elevation, 0)) != 0) {
                this.F(n3);
            }
            object.recycle();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getClass().getSimpleName());
        ((StringBuilder)object).append(" can only be used with a compatible window decor layout");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public void D(boolean bl) {
        int n3 = bl ? 4 : 0;
        this.E(n3, 4);
    }

    public void E(int n3, int n4) {
        int n5 = this.f.l();
        if ((n4 & 4) != 0) {
            this.l = true;
        }
        this.f.k(n3 & n4 | ~n4 & n5);
    }

    public void F(float f3) {
        x0.n0((View)this.e, f3);
    }

    public final void G(boolean bl) {
        this.r = bl;
        if (!bl) {
            this.f.h(null);
            this.e.setTabContainer(this.i);
        } else {
            this.e.setTabContainer(null);
            this.f.h(this.i);
        }
        int n3 = this.A();
        boolean bl2 = true;
        n3 = n3 == 2 ? 1 : 0;
        Object object = this.i;
        if (object != null) {
            if (n3 != 0) {
                object.setVisibility(0);
                object = this.d;
                if (object != null) {
                    x0.e0((View)object);
                }
            } else {
                object.setVisibility(8);
            }
        }
        object = this.f;
        bl = !this.r && n3 != 0;
        object.r(bl);
        object = this.d;
        bl = !this.r && n3 != 0 ? bl2 : false;
        ((ActionBarOverlayLayout)object).setHasNonEmbeddedTabs(bl);
    }

    public void H(boolean bl) {
        if (bl && !this.d.v()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.A = bl;
        this.d.setHideOnContentScrollEnabled(bl);
    }

    public void I(boolean bl) {
        this.f.i(bl);
    }

    public final boolean J() {
        return this.e.isLaidOut();
    }

    public final void K() {
        if (!this.w) {
            this.w = true;
            ActionBarOverlayLayout actionBarOverlayLayout = this.d;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(true);
            }
            this.L(false);
        }
    }

    public final void L(boolean bl) {
        if (androidx.appcompat.app.t.v(this.u, this.v, this.w)) {
            if (!this.x) {
                this.x = true;
                this.y(bl);
                return;
            }
        } else if (this.x) {
            this.x = false;
            this.x(bl);
        }
    }

    @Override
    public void a() {
        if (this.v) {
            this.v = false;
            this.L(true);
        }
    }

    @Override
    public void b() {
        h h3 = this.y;
        if (h3 != null) {
            h3.a();
            this.y = null;
        }
    }

    @Override
    public void c() {
    }

    @Override
    public void d(boolean bl) {
        this.t = bl;
    }

    @Override
    public void e() {
        if (!this.v) {
            this.v = true;
            this.L(true);
        }
    }

    @Override
    public boolean g() {
        u u3 = this.f;
        if (u3 != null && u3.j()) {
            this.f.collapseActionView();
            return true;
        }
        return false;
    }

    @Override
    public void h(boolean bl) {
        block3: {
            block2: {
                if (bl == this.p) break block2;
                this.p = bl;
                if (this.q.size() > 0) break block3;
            }
            return;
        }
        androidx.appcompat.app.s.a(this.q.get(0));
        throw null;
    }

    @Override
    public int i() {
        return this.f.l();
    }

    @Override
    public Context j() {
        if (this.b == null) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(c.a.actionBarWidgetTheme, typedValue, true);
            int n3 = typedValue.resourceId;
            this.b = n3 != 0 ? new ContextThemeWrapper(this.a, n3) : this.a;
        }
        return this.b;
    }

    @Override
    public void l(Configuration configuration) {
        this.G(h.a.b(this.a).g());
    }

    @Override
    public boolean n(int n3, KeyEvent keyEvent) {
        d d3 = this.m;
        if (d3 == null) {
            return false;
        }
        if ((d3 = d3.e()) != null) {
            int n4 = keyEvent != null ? keyEvent.getDeviceId() : -1;
            n4 = KeyCharacterMap.load((int)n4).getKeyboardType();
            boolean bl = true;
            if (n4 == 1) {
                bl = false;
            }
            d3.setQwertyMode(bl);
            return d3.performShortcut(n3, keyEvent, 0);
        }
        return false;
    }

    @Override
    public void onWindowVisibilityChanged(int n3) {
        this.s = n3;
    }

    @Override
    public void q(boolean bl) {
        if (!this.l) {
            this.D(bl);
        }
    }

    @Override
    public void r(boolean bl) {
        h h3;
        this.z = bl;
        if (!bl && (h3 = this.y) != null) {
            h3.a();
        }
    }

    @Override
    public void s(CharSequence charSequence) {
        this.f.setWindowTitle(charSequence);
    }

    @Override
    public b t(b.a object) {
        d d3 = this.m;
        if (d3 != null) {
            d3.c();
        }
        this.d.setHideOnContentScrollEnabled(false);
        this.g.k();
        object = new d(this, this.g.getContext(), (b.a)object);
        if (((d)object).t()) {
            this.m = object;
            ((d)object).k();
            this.g.h((b)object);
            this.u(true);
            return object;
        }
        return null;
    }

    public void u(boolean bl) {
        if (bl) {
            this.K();
        } else {
            this.B();
        }
        if (this.J()) {
            h1 h12;
            h1 h13;
            if (bl) {
                h13 = this.f.o(4, 100L);
                h12 = this.g.f(0, 200L);
            } else {
                h12 = this.f.o(0, 200L);
                h13 = this.g.f(8, 100L);
            }
            h h3 = new h();
            h3.d(h13, h12);
            h3.h();
            return;
        }
        if (bl) {
            this.f.setVisibility(4);
            this.g.setVisibility(0);
            return;
        }
        this.f.setVisibility(0);
        this.g.setVisibility(8);
    }

    public void w() {
        b.a a4 = this.o;
        if (a4 != null) {
            a4.d(this.n);
            this.n = null;
            this.o = null;
        }
    }

    public void x(boolean bl) {
        h h3 = this.y;
        if (h3 != null) {
            h3.a();
        }
        if (this.s == 0 && (this.z || bl)) {
            Object object;
            float f3;
            this.e.setAlpha(1.0f);
            this.e.setTransitioning(true);
            h3 = new h();
            float f4 = f3 = (float)(-this.e.getHeight());
            if (bl) {
                object = new int[]{0, false};
                this.e.getLocationInWindow((int[])object);
                f4 = f3 - (float)object[1];
            }
            object = x0.e((View)this.e).l(f4);
            object.j(this.D);
            h3.c((h1)object);
            if (this.t && (object = this.h) != null) {
                h3.c(x0.e((View)object).l(f4));
            }
            h3.f(E);
            h3.e(250L);
            h3.g(this.B);
            this.y = h3;
            h3.h();
            return;
        }
        this.B.b(null);
    }

    public void y(boolean bl) {
        Object object = this.y;
        if (object != null) {
            ((h)object).a();
        }
        this.e.setVisibility(0);
        if (this.s == 0 && (this.z || bl)) {
            float f3;
            this.e.setTranslationY(0.0f);
            float f4 = f3 = (float)(-this.e.getHeight());
            if (bl) {
                object = new int[2];
                object[0] = false;
                object[1] = false;
                this.e.getLocationInWindow((int[])object);
                f4 = f3 - (float)object[1];
            }
            this.e.setTranslationY(f4);
            object = new h();
            h1 h12 = x0.e((View)this.e).l(0.0f);
            h12.j(this.D);
            ((h)object).c(h12);
            if (this.t && (h12 = this.h) != null) {
                h12.setTranslationY(f4);
                ((h)object).c(x0.e(this.h).l(0.0f));
            }
            ((h)object).f(F);
            ((h)object).e(250L);
            ((h)object).g(this.C);
            this.y = object;
            ((h)object).h();
        } else {
            this.e.setAlpha(1.0f);
            this.e.setTranslationY(0.0f);
            if (this.t && (object = this.h) != null) {
                object.setTranslationY(0.0f);
            }
            this.C.b(null);
        }
        object = this.d;
        if (object != null) {
            x0.e0((View)object);
        }
    }

    public final u z(View object) {
        if (object instanceof u) {
            return (u)object;
        }
        if (object instanceof Toolbar) {
            return ((Toolbar)object).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        object = object != null ? object.getClass().getSimpleName() : "null";
        stringBuilder.append((String)object);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public class d
    extends b
    implements e.a {
        public final Context e;
        public final e f;
        public b.a g;
        public WeakReference h;
        public final t i;

        public d(t object, Context context, b.a a4) {
            this.i = object;
            this.e = context;
            this.g = a4;
            this.f = object = new e(context).X(1);
            ((e)object).W(this);
        }

        @Override
        public boolean a(e object, MenuItem menuItem) {
            object = this.g;
            if (object != null) {
                return object.a(this, menuItem);
            }
            return false;
        }

        @Override
        public void b(e e3) {
            if (this.g == null) {
                return;
            }
            this.k();
            this.i.g.l();
        }

        @Override
        public void c() {
            t t3 = this.i;
            if (t3.m != this) {
                return;
            }
            if (!androidx.appcompat.app.t.v(t3.u, t3.v, false)) {
                t3 = this.i;
                t3.n = this;
                t3.o = this.g;
            } else {
                this.g.d(this);
            }
            this.g = null;
            this.i.u(false);
            this.i.g.g();
            t3 = this.i;
            t3.d.setHideOnContentScrollEnabled(t3.A);
            this.i.m = null;
        }

        @Override
        public View d() {
            WeakReference weakReference = this.h;
            if (weakReference != null) {
                return (View)weakReference.get();
            }
            return null;
        }

        @Override
        public Menu e() {
            return this.f;
        }

        @Override
        public MenuInflater f() {
            return new g(this.e);
        }

        @Override
        public CharSequence g() {
            return this.i.g.getSubtitle();
        }

        @Override
        public CharSequence i() {
            return this.i.g.getTitle();
        }

        @Override
        public void k() {
            if (this.i.m != this) {
                return;
            }
            this.f.i0();
            try {
                this.g.c(this, this.f);
                return;
            }
            finally {
                this.f.h0();
            }
        }

        @Override
        public boolean l() {
            return this.i.g.j();
        }

        @Override
        public void m(View view) {
            this.i.g.setCustomView(view);
            this.h = new WeakReference<View>(view);
        }

        @Override
        public void n(int n3) {
            this.o(this.i.a.getResources().getString(n3));
        }

        @Override
        public void o(CharSequence charSequence) {
            this.i.g.setSubtitle(charSequence);
        }

        @Override
        public void q(int n3) {
            this.r(this.i.a.getResources().getString(n3));
        }

        @Override
        public void r(CharSequence charSequence) {
            this.i.g.setTitle(charSequence);
        }

        @Override
        public void s(boolean bl) {
            super.s(bl);
            this.i.g.setTitleOptional(bl);
        }

        public boolean t() {
            this.f.i0();
            try {
                boolean bl = this.g.b(this, this.f);
                return bl;
            }
            finally {
                this.f.h0();
            }
        }
    }
}

