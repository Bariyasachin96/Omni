/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.Menu
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.Window$Callback
 *  android.view.WindowInsets
 *  android.widget.OverScroller
 */
package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.t;
import androidx.appcompat.widget.u;
import c.a;
import c.f;
import g0.b;
import o0.c0;
import o0.d0;
import o0.e0;
import o0.x0;
import o0.z1;

public class ActionBarOverlayLayout
extends ViewGroup
implements t,
c0,
d0 {
    public static final int[] I = new int[]{a.actionBarSize, 16842841};
    public static final z1 J = new z1.b().d(b.b(0, 1, 0, 1)).a();
    public static final Rect K = new Rect();
    public d A;
    public OverScroller B;
    public ViewPropertyAnimator C;
    public final AnimatorListenerAdapter D;
    public final Runnable E;
    public final Runnable F;
    public final e0 G;
    public final e H;
    public int c;
    public int d = 0;
    public ContentFrameLayout e;
    public ActionBarContainer f;
    public u g;
    public Drawable h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public int m;
    public int n;
    public final Rect o = new Rect();
    public final Rect p = new Rect();
    public final Rect q = new Rect();
    public final Rect r = new Rect();
    public final Rect s = new Rect();
    public final Rect t = new Rect();
    public final Rect u = new Rect();
    public final Rect v = new Rect();
    public z1 w;
    public z1 x;
    public z1 y;
    public z1 z;

    public ActionBarOverlayLayout(Context context) {
        this(context, null);
    }

    public ActionBarOverlayLayout(Context object, AttributeSet object2) {
        super(object, object2);
        object2 = z1.b;
        this.w = object2;
        this.x = object2;
        this.y = object2;
        this.z = object2;
        this.D = new AnimatorListenerAdapter(this){
            public final ActionBarOverlayLayout a;
            {
                this.a = actionBarOverlayLayout;
            }

            public void onAnimationCancel(Animator object) {
                object = this.a;
                object.C = null;
                object.l = false;
            }

            public void onAnimationEnd(Animator object) {
                object = this.a;
                object.C = null;
                object.l = false;
            }
        };
        this.E = new Runnable(this){
            public final ActionBarOverlayLayout c;
            {
                this.c = actionBarOverlayLayout;
            }

            @Override
            public void run() {
                this.c.t();
                ActionBarOverlayLayout actionBarOverlayLayout = this.c;
                actionBarOverlayLayout.C = actionBarOverlayLayout.f.animate().translationY(0.0f).setListener((Animator.AnimatorListener)this.c.D);
            }
        };
        this.F = new Runnable(this){
            public final ActionBarOverlayLayout c;
            {
                this.c = actionBarOverlayLayout;
            }

            @Override
            public void run() {
                this.c.t();
                ActionBarOverlayLayout actionBarOverlayLayout = this.c;
                actionBarOverlayLayout.C = actionBarOverlayLayout.f.animate().translationY((float)(-this.c.f.getHeight())).setListener((Animator.AnimatorListener)this.c.D);
            }
        };
        this.u((Context)object);
        this.G = new e0(this);
        object = new e((Context)object);
        this.H = object;
        this.addView((View)object);
    }

    public final boolean A(float f3) {
        this.B.fling(0, 0, 0, (int)f3, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.B.getFinalY() > this.f.getHeight();
    }

    @Override
    public boolean a() {
        this.y();
        return this.g.a();
    }

    @Override
    public void b(View view, View view2, int n3, int n4) {
        if (n4 == 0) {
            this.onNestedScrollAccepted(view, view2, n3);
        }
    }

    @Override
    public boolean c() {
        this.y();
        return this.g.c();
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    public boolean d() {
        this.y();
        return this.g.d();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.h != null) {
            int n3 = this.f.getVisibility() == 0 ? (int)((float)this.f.getBottom() + this.f.getTranslationY() + 0.5f) : 0;
            this.h.setBounds(0, n3, this.getWidth(), this.h.getIntrinsicHeight() + n3);
            this.h.draw(canvas);
        }
    }

    @Override
    public boolean e() {
        this.y();
        return this.g.e();
    }

    @Override
    public boolean f() {
        this.y();
        return this.g.f();
    }

    public boolean fitSystemWindows(Rect rect) {
        return super.fitSystemWindows(rect);
    }

    @Override
    public void g(View view, int n3) {
        if (n3 == 0) {
            this.onStopNestedScroll(view);
        }
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.f;
        if (actionBarContainer != null) {
            return -((int)actionBarContainer.getTranslationY());
        }
        return 0;
    }

    public int getNestedScrollAxes() {
        return this.G.a();
    }

    public CharSequence getTitle() {
        this.y();
        return this.g.getTitle();
    }

    @Override
    public void h(View view, int n3, int n4, int[] nArray, int n5) {
        if (n5 == 0) {
            this.onNestedPreScroll(view, n3, n4, nArray);
        }
    }

    @Override
    public void i(int n3) {
        this.y();
        if (n3 != 2) {
            if (n3 != 5) {
                if (n3 != 109) {
                    return;
                }
                this.setOverlayMode(true);
                return;
            }
            this.g.q();
            return;
        }
        this.g.p();
    }

    @Override
    public void j() {
        this.y();
        this.g.g();
    }

    @Override
    public void k(View view, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        this.l(view, n3, n4, n5, n6, n7);
    }

    @Override
    public void l(View view, int n3, int n4, int n5, int n6, int n7) {
        if (n7 == 0) {
            this.onNestedScroll(view, n3, n4, n5, n6);
        }
    }

    @Override
    public boolean m(View view, View view2, int n3, int n4) {
        return n4 == 0 && this.onStartNestedScroll(view, view2, n3);
    }

    public final void n() {
        this.t();
        this.F.run();
    }

    public final boolean o(View object, Rect rect, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        boolean bl5;
        int n3;
        int n4;
        object = (LayoutParams)object.getLayoutParams();
        if (bl && (n4 = object.leftMargin) != (n3 = rect.left)) {
            object.leftMargin = n3;
            bl5 = true;
        } else {
            bl5 = false;
        }
        bl = bl5;
        if (bl2) {
            n3 = object.topMargin;
            n4 = rect.top;
            bl = bl5;
            if (n3 != n4) {
                object.topMargin = n4;
                bl = true;
            }
        }
        bl2 = bl;
        if (bl4) {
            n3 = object.rightMargin;
            n4 = rect.right;
            bl2 = bl;
            if (n3 != n4) {
                object.rightMargin = n4;
                bl2 = true;
            }
        }
        if (bl3 && (n4 = object.bottomMargin) != (n3 = rect.bottom)) {
            object.bottomMargin = n3;
            return true;
        }
        return bl2;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets object) {
        this.y();
        object = z1.x((WindowInsets)object, (View)this);
        Object object2 = new Rect(((z1)object).j(), ((z1)object).l(), ((z1)object).k(), ((z1)object).i());
        boolean bl = this.o((View)this.f, (Rect)object2, true, true, false, true);
        x0.f((View)this, (z1)object, this.o);
        object2 = this.o;
        object2 = ((z1)object).n(object2.left, object2.top, object2.right, object2.bottom);
        this.w = object2;
        boolean bl2 = this.x.equals(object2);
        boolean bl3 = true;
        if (!bl2) {
            this.x = this.w;
            bl = true;
        }
        if (!this.p.equals((Object)this.o)) {
            this.p.set(this.o);
            bl = bl3;
        }
        if (bl) {
            this.requestLayout();
        }
        return ((z1)object).a().c().b().v();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.u(this.getContext());
        x0.e0((View)this);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.t();
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        n6 = this.getChildCount();
        n5 = this.getPaddingLeft();
        n4 = this.getPaddingTop();
        for (n3 = 0; n3 < n6; ++n3) {
            View view = this.getChildAt(n3);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n7 = view.getMeasuredWidth();
            int n8 = view.getMeasuredHeight();
            int n9 = layoutParams.leftMargin + n5;
            int n10 = layoutParams.topMargin + n4;
            view.layout(n9, n10, n7 + n9, n8 + n10);
        }
    }

    public void onMeasure(int n3, int n4) {
        int n5;
        int n6;
        this.y();
        this.measureChildWithMargins((View)this.f, n3, 0, n4, 0);
        Object object = (LayoutParams)this.f.getLayoutParams();
        int n7 = Math.max(0, this.f.getMeasuredWidth() + object.leftMargin + object.rightMargin);
        int n8 = Math.max(0, this.f.getMeasuredHeight() + object.topMargin + object.bottomMargin);
        int n9 = View.combineMeasuredStates((int)0, (int)this.f.getMeasuredState());
        int n10 = (x0.I((View)this) & 0x100) != 0 ? 1 : 0;
        if (n10 != 0) {
            n5 = n6 = this.c;
            if (this.j) {
                n5 = n6;
                if (this.f.getTabContainer() != null) {
                    n5 = n6 + this.c;
                }
            }
        } else {
            n5 = this.f.getVisibility() != 8 ? this.f.getMeasuredHeight() : 0;
        }
        this.q.set(this.o);
        this.y = this.w;
        if (!this.i && n10 == 0 && this.p()) {
            object = this.q;
            ((Rect)object).top += n5;
            ((Rect)object).bottom = ((Rect)object).bottom;
            this.y = this.y.n(0, n5, 0, 0);
        } else {
            object = b.b(this.y.j(), this.y.l() + n5, this.y.k(), this.y.i());
            this.y = new z1.b(this.y).d((b)object).a();
        }
        this.o((View)this.e, this.q, true, true, true, true);
        if (!this.z.equals(this.y)) {
            object = this.y;
            this.z = object;
            x0.g((View)this.e, (z1)object);
        }
        this.measureChildWithMargins((View)this.e, n3, 0, n4, 0);
        object = (LayoutParams)this.e.getLayoutParams();
        n5 = Math.max(n7, this.e.getMeasuredWidth() + object.leftMargin + object.rightMargin);
        n8 = Math.max(n8, this.e.getMeasuredHeight() + object.topMargin + object.bottomMargin);
        n10 = View.combineMeasuredStates((int)n9, (int)this.e.getMeasuredState());
        n9 = this.getPaddingLeft();
        n6 = this.getPaddingRight();
        n8 = Math.max(n8 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n5 + (n9 + n6), this.getSuggestedMinimumWidth()), (int)n3, (int)n10), View.resolveSizeAndState((int)n8, (int)n4, (int)(n10 << 16)));
    }

    public boolean onNestedFling(View view, float f3, float f4, boolean bl) {
        if (this.k && bl) {
            if (this.A(f4)) {
                this.n();
            } else {
                this.z();
            }
            this.l = true;
            return true;
        }
        return false;
    }

    public boolean onNestedPreFling(View view, float f3, float f4) {
        return false;
    }

    public void onNestedPreScroll(View view, int n3, int n4, int[] nArray) {
    }

    public void onNestedScroll(View view, int n3, int n4, int n5, int n6) {
        this.m = n3 = this.m + n4;
        this.setActionBarHideOffset(n3);
    }

    public void onNestedScrollAccepted(View object, View view, int n3) {
        this.G.b((View)object, view, n3);
        this.m = this.getActionBarHideOffset();
        this.t();
        object = this.A;
        if (object != null) {
            object.b();
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int n3) {
        if ((n3 & 2) != 0 && this.f.getVisibility() == 0) {
            return this.k;
        }
        return false;
    }

    public void onStopNestedScroll(View object) {
        if (this.k && !this.l) {
            if (this.m <= this.f.getHeight()) {
                this.x();
            } else {
                this.w();
            }
        }
        if ((object = this.A) != null) {
            object.c();
        }
    }

    public void onWindowSystemUiVisibilityChanged(int n3) {
        d d3;
        super.onWindowSystemUiVisibilityChanged(n3);
        this.y();
        int n4 = this.n;
        this.n = n3;
        boolean bl = false;
        boolean bl2 = (n3 & 4) == 0;
        if ((n3 & 0x100) != 0) {
            bl = true;
        }
        if ((d3 = this.A) != null) {
            d3.d(bl ^ true);
            if (!bl2 && bl) {
                this.A.e();
            } else {
                this.A.a();
            }
        }
        if (((n4 ^ n3) & 0x100) != 0 && this.A != null) {
            x0.e0((View)this);
        }
    }

    public void onWindowVisibilityChanged(int n3) {
        super.onWindowVisibilityChanged(n3);
        this.d = n3;
        d d3 = this.A;
        if (d3 != null) {
            d3.onWindowVisibilityChanged(n3);
        }
    }

    public final boolean p() {
        x0.f(this.H, J, this.r);
        return this.r.equals((Object)K) ^ true;
    }

    public LayoutParams q() {
        return new LayoutParams(-1, -1);
    }

    public LayoutParams r(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public final u s(View view) {
        if (view instanceof u) {
            return (u)view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar)view).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        stringBuilder.append(view.getClass().getSimpleName());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void setActionBarHideOffset(int n3) {
        this.t();
        n3 = Math.max(0, Math.min(n3, this.f.getHeight()));
        this.f.setTranslationY(-n3);
    }

    public void setActionBarVisibilityCallback(d d3) {
        this.A = d3;
        if (this.getWindowToken() != null) {
            this.A.onWindowVisibilityChanged(this.d);
            int n3 = this.n;
            if (n3 != 0) {
                this.onWindowSystemUiVisibilityChanged(n3);
                x0.e0((View)this);
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean bl) {
        this.j = bl;
    }

    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl != this.k) {
            this.k = bl;
            if (!bl) {
                this.t();
                this.setActionBarHideOffset(0);
            }
        }
    }

    public void setIcon(int n3) {
        this.y();
        this.g.setIcon(n3);
    }

    public void setIcon(Drawable drawable) {
        this.y();
        this.g.setIcon(drawable);
    }

    public void setLogo(int n3) {
        this.y();
        this.g.m(n3);
    }

    @Override
    public void setMenu(Menu menu, i.a a4) {
        this.y();
        this.g.setMenu(menu, a4);
    }

    @Override
    public void setMenuPrepared() {
        this.y();
        this.g.setMenuPrepared();
    }

    public void setOverlayMode(boolean bl) {
        this.i = bl;
    }

    public void setShowingForActionMode(boolean bl) {
    }

    public void setUiOptions(int n3) {
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.y();
        this.g.setWindowCallback(callback);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.y();
        this.g.setWindowTitle(charSequence);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void t() {
        this.removeCallbacks(this.E);
        this.removeCallbacks(this.F);
        ViewPropertyAnimator viewPropertyAnimator = this.C;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public final void u(Context context) {
        Drawable drawable;
        TypedArray typedArray = this.getContext().getTheme().obtainStyledAttributes(I);
        boolean bl = false;
        this.c = typedArray.getDimensionPixelSize(0, 0);
        this.h = drawable = typedArray.getDrawable(1);
        if (drawable == null) {
            bl = true;
        }
        this.setWillNotDraw(bl);
        typedArray.recycle();
        this.B = new OverScroller(context);
    }

    public boolean v() {
        return this.i;
    }

    public final void w() {
        this.t();
        this.postDelayed(this.F, 600L);
    }

    public final void x() {
        this.t();
        this.postDelayed(this.E, 600L);
    }

    public void y() {
        if (this.e == null) {
            this.e = (ContentFrameLayout)this.findViewById(c.f.action_bar_activity_content);
            this.f = (ActionBarContainer)this.findViewById(c.f.action_bar_container);
            this.g = this.s(this.findViewById(c.f.action_bar));
        }
    }

    public final void z() {
        this.t();
        this.E.run();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public static interface d {
        public void a();

        public void b();

        public void c();

        public void d(boolean var1);

        public void e();

        public void onWindowVisibilityChanged(int var1);
    }

    public static final class e
    extends View {
        public e(Context context) {
            super(context);
            this.setWillNotDraw(true);
        }

        public int getWindowSystemUiVisibility() {
            return 0;
        }
    }
}

