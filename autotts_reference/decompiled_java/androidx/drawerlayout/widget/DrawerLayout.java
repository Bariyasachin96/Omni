/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnApplyWindowInsetsListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.WindowInsets
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.drawerlayout.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.customview.view.AbsSavedState;
import e0.a;
import java.util.ArrayList;
import java.util.List;
import o0.s;
import o0.x0;
import o0.z1;
import p0.s;
import p0.v;
import v0.c;
import w0.b;

public class DrawerLayout
extends ViewGroup {
    public static final int[] N = new int[]{16843828};
    public static final int[] O = new int[]{16842931};
    public static final boolean P;
    public static final boolean Q;
    public static boolean R;
    public Drawable A;
    public CharSequence B;
    public CharSequence C;
    public Object D;
    public boolean E;
    public Drawable F;
    public Drawable G;
    public Drawable H;
    public Drawable I;
    public final ArrayList J;
    public Rect K;
    public Matrix L;
    public final v M;
    public final d c;
    public float d;
    public int e;
    public int f;
    public float g;
    public Paint h;
    public final v0.c i;
    public final v0.c j;
    public final g k;
    public final g l;
    public int m;
    public boolean n;
    public boolean o;
    public int p;
    public int q;
    public int r;
    public int s;
    public boolean t;
    public e u;
    public List v;
    public float w;
    public float x;
    public Drawable y;
    public Drawable z;

    static {
        int n3 = Build.VERSION.SDK_INT;
        boolean bl = true;
        P = true;
        Q = true;
        if (n3 < 29) {
            bl = false;
        }
        R = bl;
    }

    public DrawerLayout(Context context) {
        this(context, null);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, w0.a.drawerLayoutStyle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DrawerLayout(Context context, AttributeSet attributeSet, int n3) {
        Throwable throwable2;
        block7: {
            v0.c c3;
            g g3;
            g g4;
            super(context, attributeSet, n3);
            this.c = new d();
            this.f = -1728053248;
            this.h = new Paint();
            this.o = true;
            this.p = 3;
            this.q = 3;
            this.r = 3;
            this.s = 3;
            this.F = null;
            this.G = null;
            this.H = null;
            this.I = null;
            this.M = new v(this){
                public final DrawerLayout a;
                {
                    this.a = drawerLayout;
                }

                @Override
                public boolean a(View view, v.a a4) {
                    if (this.a.A(view) && this.a.p(view) != 2) {
                        this.a.d(view);
                        return true;
                    }
                    return false;
                }
            };
            this.setDescendantFocusability(262144);
            float f3 = this.getResources().getDisplayMetrics().density;
            this.e = (int)(64.0f * f3 + 0.5f);
            this.k = g4 = new g(this, 3);
            this.l = g3 = new g(this, 5);
            this.i = c3 = v0.c.n(this, 1.0f, g4);
            c3.M(1);
            c3.N(f3 *= 400.0f);
            g4.q(c3);
            this.j = c3 = v0.c.n(this, 1.0f, g3);
            c3.M(2);
            c3.N(f3);
            g3.q(c3);
            this.setFocusableInTouchMode(true);
            x0.o0((View)this, 1);
            x0.h0((View)this, new c(this));
            this.setMotionEventSplittingEnabled(false);
            if (x0.v((View)this)) {
                this.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(this){
                    public final DrawerLayout a;
                    {
                        this.a = drawerLayout;
                    }

                    public WindowInsets onApplyWindowInsets(View object, WindowInsets windowInsets) {
                        object = (DrawerLayout)((Object)object);
                        boolean bl = windowInsets.getSystemWindowInsetTop() > 0;
                        ((DrawerLayout)((Object)object)).setChildInsets(windowInsets, bl);
                        return windowInsets.consumeSystemWindowInsets();
                    }
                });
                this.setSystemUiVisibility(1280);
                g3 = context.obtainStyledAttributes(N);
                try {
                    this.y = g3.getDrawable(0);
                }
                finally {
                    g3.recycle();
                }
            }
            attributeSet = context.obtainStyledAttributes(attributeSet, w0.c.DrawerLayout, n3, 0);
            try {
                n3 = w0.c.DrawerLayout_elevation;
                this.d = attributeSet.hasValue(n3) ? attributeSet.getDimension(n3, 0.0f) : this.getResources().getDimension(b.def_drawer_elevation);
            }
            catch (Throwable throwable2) {
                break block7;
            }
            attributeSet.recycle();
            this.J = new ArrayList();
            return;
        }
        attributeSet.recycle();
        throw throwable2;
    }

    public static String u(int n3) {
        if ((n3 & 3) == 3) {
            return "LEFT";
        }
        if ((n3 & 5) == 5) {
            return "RIGHT";
        }
        return Integer.toHexString(n3);
    }

    public static boolean v(View view) {
        return (view = view.getBackground()) != null && view.getOpacity() == -1;
    }

    public static boolean y(View view) {
        return x0.w(view) != 4 && x0.w(view) != 2;
    }

    public boolean A(View view) {
        if (this.B(view)) {
            return (((LayoutParams)view.getLayoutParams()).d & 1) == 1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(view);
        stringBuilder.append(" is not a drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean B(View view) {
        int n3 = o0.s.b(((LayoutParams)view.getLayoutParams()).a, x0.y(view));
        if ((n3 & 3) != 0) {
            return true;
        }
        return (n3 & 5) != 0;
    }

    public boolean C(View view) {
        if (this.B(view)) {
            return ((LayoutParams)view.getLayoutParams()).b > 0.0f;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(view);
        stringBuilder.append(" is not a drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public final boolean D(float f3, float f4, View view) {
        if (this.K == null) {
            this.K = new Rect();
        }
        view.getHitRect(this.K);
        return this.K.contains((int)f3, (int)f4);
    }

    public final void E(Drawable drawable, int n3) {
        if (drawable != null && h0.a.h(drawable)) {
            h0.a.m(drawable, n3);
        }
    }

    public void F(View view, float f3) {
        float f4 = this.s(view);
        float f5 = view.getWidth();
        int n3 = (int)(f4 * f5);
        n3 = (int)(f5 * f3) - n3;
        if (!this.c(view, 3)) {
            n3 = -n3;
        }
        view.offsetLeftAndRight(n3);
        this.M(view, f3);
    }

    public void G(View view) {
        this.H(view, true);
    }

    public void H(View view, boolean bl) {
        if (this.B(view)) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (this.o) {
                layoutParams.b = 1.0f;
                layoutParams.d = 1;
                this.O(view, true);
                this.N(view);
            } else if (bl) {
                layoutParams.d |= 2;
                if (this.c(view, 3)) {
                    this.i.Q(view, 0, view.getTop());
                } else {
                    this.j.Q(view, this.getWidth() - view.getWidth(), view.getTop());
                }
            } else {
                this.F(view, 1.0f);
                this.P(0, view);
                view.setVisibility(0);
            }
            this.invalidate();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(view);
        stringBuilder.append(" is not a sliding drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void I(e e3) {
        List list;
        if (e3 == null || (list = this.v) == null) {
            return;
        }
        list.remove(e3);
    }

    public final Drawable J() {
        int n3 = x0.y((View)this);
        if (n3 == 0) {
            Drawable drawable = this.F;
            if (drawable != null) {
                this.E(drawable, n3);
                return this.F;
            }
        } else {
            Drawable drawable = this.G;
            if (drawable != null) {
                this.E(drawable, n3);
                return this.G;
            }
        }
        return this.H;
    }

    public final Drawable K() {
        int n3 = x0.y((View)this);
        if (n3 == 0) {
            Drawable drawable = this.G;
            if (drawable != null) {
                this.E(drawable, n3);
                return this.G;
            }
        } else {
            Drawable drawable = this.F;
            if (drawable != null) {
                this.E(drawable, n3);
                return this.F;
            }
        }
        return this.I;
    }

    public final void L() {
        if (Q) {
            return;
        }
        this.z = this.J();
        this.A = this.K();
    }

    public void M(View view, float f3) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (f3 == layoutParams.b) {
            return;
        }
        layoutParams.b = f3;
        this.j(view, f3);
    }

    public final void N(View view) {
        s.a a4 = s.a.y;
        x0.b0(view, a4.b());
        if (this.A(view) && this.p(view) != 2) {
            x0.d0(view, a4, null, this.M);
        }
    }

    public final void O(View view, boolean bl) {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view2 = this.getChildAt(i3);
            if (!bl && !this.B(view2) || bl && view2 == view) {
                x0.o0(view2, 1);
                continue;
            }
            x0.o0(view2, 4);
        }
    }

    public void P(int n3, View object) {
        int n4;
        int n5 = this.i.A();
        int n6 = this.j.A();
        if (n5 != 1 && n6 != 1) {
            int n7;
            n4 = n7 = 2;
            if (n5 != 2) {
                n4 = n6 == 2 ? n7 : 0;
            }
        } else {
            n4 = 1;
        }
        if (object != null && n3 == 0) {
            float f3 = ((LayoutParams)object.getLayoutParams()).b;
            if (f3 == 0.0f) {
                this.h((View)object);
            } else if (f3 == 1.0f) {
                this.i((View)object);
            }
        }
        if (n4 != this.m) {
            this.m = n4;
            object = this.v;
            if (object != null) {
                for (n3 = object.size() - 1; n3 >= 0; --n3) {
                    ((e)this.v.get(n3)).a(n4);
                }
            }
        }
    }

    public void a(e e3) {
        if (e3 == null) {
            return;
        }
        if (this.v == null) {
            this.v = new ArrayList();
        }
        this.v.add(e3);
    }

    public void addFocusables(ArrayList arrayList, int n3, int n4) {
        View view;
        int n5;
        if (this.getDescendantFocusability() == 393216) {
            return;
        }
        int n6 = this.getChildCount();
        int n7 = 0;
        int n8 = 0;
        for (n5 = 0; n5 < n6; ++n5) {
            view = this.getChildAt(n5);
            if (this.B(view)) {
                if (!this.A(view)) continue;
                view.addFocusables(arrayList, n3, n4);
                n8 = 1;
                continue;
            }
            this.J.add(view);
        }
        if (n8 == 0) {
            n8 = this.J.size();
            for (n5 = n7; n5 < n8; ++n5) {
                view = (View)this.J.get(n5);
                if (view.getVisibility() != 0) continue;
                view.addFocusables(arrayList, n3, n4);
            }
        }
        this.J.clear();
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n3, layoutParams);
        if (this.m() == null && !this.B(view)) {
            x0.o0(view, 1);
        } else {
            x0.o0(view, 4);
        }
        if (!P) {
            x0.h0(view, this.c);
        }
    }

    public void b() {
        if (!this.t) {
            long l3 = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0);
            int n3 = this.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                this.getChildAt(i3).dispatchTouchEvent(motionEvent);
            }
            motionEvent.recycle();
            this.t = true;
        }
    }

    public boolean c(View view, int n3) {
        return (this.r(view) & n3) == n3;
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        int n3 = this.getChildCount();
        float f3 = 0.0f;
        for (int i3 = 0; i3 < n3; ++i3) {
            f3 = Math.max(f3, ((LayoutParams)this.getChildAt((int)i3).getLayoutParams()).b);
        }
        this.g = f3;
        boolean bl = this.i.m(true);
        boolean bl2 = this.j.m(true);
        if (!bl && !bl2) {
            return;
        }
        x0.Y((View)this);
    }

    public void d(View view) {
        this.e(view, true);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() != 10 && !(this.g <= 0.0f)) {
            int n3 = this.getChildCount();
            if (n3 != 0) {
                float f3 = motionEvent.getX();
                float f4 = motionEvent.getY();
                --n3;
                while (n3 >= 0) {
                    View view = this.getChildAt(n3);
                    if (this.D(f3, f4, view) && !this.z(view) && this.k(motionEvent, view)) {
                        return true;
                    }
                    --n3;
                }
            }
            return false;
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean drawChild(Canvas canvas, View view, long l3) {
        int n3;
        int n4 = this.getHeight();
        boolean bl = this.z(view);
        int n5 = this.getWidth();
        int n6 = canvas.save();
        int n7 = 0;
        int n8 = n5;
        if (bl) {
            int n9 = this.getChildCount();
            n7 = 0;
            for (n8 = 0; n8 < n9; ++n8) {
                View view2 = this.getChildAt(n8);
                n3 = n5;
                int n10 = n7;
                if (view2 != view) {
                    n3 = n5;
                    n10 = n7;
                    if (view2.getVisibility() == 0) {
                        n3 = n5;
                        n10 = n7;
                        if (DrawerLayout.v(view2)) {
                            n3 = n5;
                            n10 = n7;
                            if (this.B(view2)) {
                                int n11;
                                if (view2.getHeight() < n4) {
                                    n3 = n5;
                                    n10 = n7;
                                } else if (this.c(view2, 3)) {
                                    n11 = view2.getRight();
                                    n3 = n5;
                                    n10 = n7;
                                    if (n11 > n7) {
                                        n10 = n11;
                                        n3 = n5;
                                    }
                                } else {
                                    n11 = view2.getLeft();
                                    n3 = n5;
                                    n10 = n7;
                                    if (n11 < n5) {
                                        n3 = n11;
                                        n10 = n7;
                                    }
                                }
                            }
                        }
                    }
                }
                n5 = n3;
                n7 = n10;
            }
            canvas.clipRect(n7, 0, n5, this.getHeight());
            n8 = n5;
        }
        boolean bl2 = super.drawChild(canvas, view, l3);
        canvas.restoreToCount(n6);
        float f3 = this.g;
        if (f3 > 0.0f && bl) {
            n3 = this.f;
            n5 = (int)((float)((0xFF000000 & n3) >>> 24) * f3);
            this.h.setColor(n3 & 0xFFFFFF | n5 << 24);
            canvas.drawRect((float)n7, 0.0f, (float)n8, (float)this.getHeight(), this.h);
            return bl2;
        }
        if (this.z != null && this.c(view, 3)) {
            n8 = this.z.getIntrinsicWidth();
            n7 = view.getRight();
            n5 = this.i.x();
            f3 = Math.max(0.0f, Math.min((float)n7 / (float)n5, 1.0f));
            this.z.setBounds(n7, view.getTop(), n8 + n7, view.getBottom());
            this.z.setAlpha((int)(f3 * 255.0f));
            this.z.draw(canvas);
            return bl2;
        }
        if (this.A != null && this.c(view, 5)) {
            n3 = this.A.getIntrinsicWidth();
            n5 = view.getLeft();
            n7 = this.getWidth();
            n8 = this.j.x();
            f3 = Math.max(0.0f, Math.min((float)(n7 - n5) / (float)n8, 1.0f));
            this.A.setBounds(n5 - n3, view.getTop(), n5, view.getBottom());
            this.A.setAlpha((int)(f3 * 255.0f));
            this.A.draw(canvas);
        }
        return bl2;
    }

    public void e(View view, boolean bl) {
        if (this.B(view)) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (this.o) {
                layoutParams.b = 0.0f;
                layoutParams.d = 0;
            } else if (bl) {
                layoutParams.d |= 4;
                if (this.c(view, 3)) {
                    this.i.Q(view, -view.getWidth(), view.getTop());
                } else {
                    this.j.Q(view, this.getWidth(), view.getTop());
                }
            } else {
                this.F(view, 0.0f);
                this.P(0, view);
                view.setVisibility(4);
            }
            this.invalidate();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(view);
        stringBuilder.append(" is not a sliding drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void f() {
        this.g(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void g(boolean bl) {
        int n3 = this.getChildCount();
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n5 = n4;
            if (this.B(view)) {
                if (bl && !layoutParams.c) {
                    n5 = n4;
                } else {
                    n5 = view.getWidth();
                    int n6 = (this.c(view, 3) ? this.i.Q(view, -n5, view.getTop()) : this.j.Q(view, this.getWidth(), view.getTop())) ? 1 : 0;
                    n5 = n4 | n6;
                    layoutParams.c = false;
                }
            }
            n4 = n5;
        }
        this.k.p();
        this.l.p();
        if (n4 != 0) {
            this.invalidate();
        }
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public float getDrawerElevation() {
        if (Q) {
            return this.d;
        }
        return 0.0f;
    }

    public Drawable getStatusBarBackgroundDrawable() {
        return this.y;
    }

    public void h(View view) {
        Object object = (LayoutParams)view.getLayoutParams();
        if ((((LayoutParams)((Object)object)).d & 1) == 1) {
            ((LayoutParams)((Object)object)).d = 0;
            object = this.v;
            if (object != null) {
                for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                    ((e)this.v.get(i3)).d(view);
                }
            }
            this.O(view, false);
            this.N(view);
            if (this.hasWindowFocus() && (view = this.getRootView()) != null) {
                view.sendAccessibilityEvent(32);
            }
        }
    }

    public void i(View view) {
        Object object = (LayoutParams)view.getLayoutParams();
        if ((((LayoutParams)((Object)object)).d & 1) == 0) {
            ((LayoutParams)((Object)object)).d = 1;
            object = this.v;
            if (object != null) {
                for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                    ((e)this.v.get(i3)).c(view);
                }
            }
            this.O(view, true);
            this.N(view);
            if (this.hasWindowFocus()) {
                this.sendAccessibilityEvent(32);
            }
        }
    }

    public void j(View view, float f3) {
        List list = this.v;
        if (list != null) {
            for (int i3 = list.size() - 1; i3 >= 0; --i3) {
                ((e)this.v.get(i3)).b(view, f3);
            }
        }
    }

    public final boolean k(MotionEvent motionEvent, View view) {
        if (!view.getMatrix().isIdentity()) {
            motionEvent = this.t(motionEvent, view);
            boolean bl = view.dispatchGenericMotionEvent(motionEvent);
            motionEvent.recycle();
            return bl;
        }
        float f3 = this.getScrollX() - view.getLeft();
        float f4 = this.getScrollY() - view.getTop();
        motionEvent.offsetLocation(f3, f4);
        boolean bl = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-f3, -f4);
        return bl;
    }

    public View l(int n3) {
        int n4 = o0.s.b(n3, x0.y((View)this));
        int n5 = this.getChildCount();
        for (n3 = 0; n3 < n5; ++n3) {
            View view = this.getChildAt(n3);
            if ((this.r(view) & 7) != (n4 & 7)) continue;
            return view;
        }
        return null;
    }

    public View m() {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            if ((((LayoutParams)view.getLayoutParams()).d & 1) != 1) continue;
            return view;
        }
        return null;
    }

    public View n() {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            if (!this.B(view) || !this.C(view)) continue;
            return view;
        }
        return null;
    }

    public int o(int n3) {
        int n4 = x0.y((View)this);
        if (n3 != 3) {
            if (n3 != 5) {
                if (n3 != 0x800003) {
                    if (n3 == 0x800005) {
                        n3 = this.s;
                        if (n3 != 3) {
                            return n3;
                        }
                        n3 = n4 == 0 ? this.q : this.p;
                        if (n3 != 3) {
                            return n3;
                        }
                    }
                } else {
                    n3 = this.r;
                    if (n3 != 3) {
                        return n3;
                    }
                    n3 = n4 == 0 ? this.p : this.q;
                    if (n3 != 3) {
                        return n3;
                    }
                }
            } else {
                n3 = this.q;
                if (n3 != 3) {
                    return n3;
                }
                n3 = n4 == 0 ? this.s : this.r;
                if (n3 != 3) {
                    return n3;
                }
            }
        } else {
            n3 = this.p;
            if (n3 != 3) {
                return n3;
            }
            n3 = n4 == 0 ? this.r : this.s;
            if (n3 != 3) {
                return n3;
            }
        }
        return 0;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.o = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.o = true;
    }

    public void onDraw(Canvas canvas) {
        Object object;
        int n3;
        super.onDraw(canvas);
        if (this.E && this.y != null && (n3 = (object = this.D) != null ? ((WindowInsets)object).getSystemWindowInsetTop() : 0) > 0) {
            this.y.setBounds(0, 0, this.getWidth(), n3);
            this.y.draw(canvas);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        int n3;
        block6: {
            block2: {
                block5: {
                    block3: {
                        block4: {
                            n3 = motionEvent.getActionMasked();
                            bl2 = this.i.P(motionEvent);
                            bl = this.j.P(motionEvent);
                            if (n3 == 0) break block2;
                            if (n3 == 1) break block3;
                            if (n3 == 2) break block4;
                            if (n3 == 3) break block3;
                            break block5;
                        }
                        if (this.i.d(3)) {
                            this.k.p();
                            this.l.p();
                        }
                        break block5;
                    }
                    this.g(true);
                    this.t = false;
                }
                n3 = 0;
                break block6;
            }
            float f3 = motionEvent.getX();
            float f4 = motionEvent.getY();
            this.w = f3;
            this.x = f4;
            n3 = this.g > 0.0f && (motionEvent = this.i.t((int)f3, (int)f4)) != null && this.z((View)motionEvent) ? 1 : 0;
            this.t = false;
        }
        return bl2 | bl || n3 != 0 || this.w() || this.t;
        {
        }
    }

    public boolean onKeyDown(int n3, KeyEvent keyEvent) {
        if (n3 == 4 && this.x()) {
            keyEvent.startTracking();
            return true;
        }
        return super.onKeyDown(n3, keyEvent);
    }

    public boolean onKeyUp(int n3, KeyEvent keyEvent) {
        if (n3 == 4) {
            keyEvent = this.n();
            if (keyEvent != null && this.p((View)keyEvent) == 0) {
                this.f();
            }
            return keyEvent != null;
        }
        return super.onKeyUp(n3, keyEvent);
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        Object object;
        Object object2;
        this.n = true;
        int n7 = n5 - n3;
        int n8 = this.getChildCount();
        for (n5 = 0; n5 < n8; ++n5) {
            int n9;
            float f3;
            object2 = this.getChildAt(n5);
            if (object2.getVisibility() == 8) continue;
            object = (LayoutParams)object2.getLayoutParams();
            if (this.z((View)object2)) {
                n3 = object.leftMargin;
                object2.layout(n3, object.topMargin, object2.getMeasuredWidth() + n3, object.topMargin + object2.getMeasuredHeight());
                continue;
            }
            int n10 = object2.getMeasuredWidth();
            int n11 = object2.getMeasuredHeight();
            if (this.c((View)object2, 3)) {
                n3 = -n10;
                f3 = n10;
                n9 = n3 + (int)(object.b * f3);
                f3 = (float)(n10 + n9) / f3;
            } else {
                f3 = n10;
                n9 = n7 - (int)(object.b * f3);
                f3 = (float)(n7 - n9) / f3;
            }
            boolean bl2 = f3 != object.b;
            n3 = object.a & 0x70;
            if (n3 != 16) {
                if (n3 != 80) {
                    n3 = object.topMargin;
                    object2.layout(n9, n3, n10 + n9, n11 + n3);
                } else {
                    n3 = n6 - n4;
                    object2.layout(n9, n3 - object.bottomMargin - object2.getMeasuredHeight(), n10 + n9, n3 - object.bottomMargin);
                }
            } else {
                int n12 = n6 - n4;
                int n13 = (n12 - n11) / 2;
                n3 = object.topMargin;
                if (n13 >= n3) {
                    int n14 = object.bottomMargin;
                    n3 = n13;
                    if (n13 + n11 > n12 - n14) {
                        n3 = n12 - n14 - n11;
                    }
                }
                object2.layout(n9, n3, n10 + n9, n11 + n3);
            }
            if (bl2) {
                this.M((View)object2, f3);
            }
            n3 = object.b > 0.0f ? 0 : 4;
            if (object2.getVisibility() == n3) continue;
            object2.setVisibility(n3);
        }
        if (R && (object = this.getRootWindowInsets()) != null) {
            object = z1.w(object).h();
            object2 = this.i;
            ((v0.c)object2).L(Math.max(((v0.c)object2).w(), object.a));
            object2 = this.j;
            ((v0.c)object2).L(Math.max(((v0.c)object2).w(), object.c));
        }
        this.n = false;
        this.o = false;
    }

    public void onMeasure(int n3, int n4) {
        block23: {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10;
            block22: {
                block21: {
                    n10 = View.MeasureSpec.getMode((int)n3);
                    n9 = View.MeasureSpec.getMode((int)n4);
                    n8 = View.MeasureSpec.getSize((int)n3);
                    n7 = View.MeasureSpec.getSize((int)n4);
                    if (n10 != 0x40000000) break block21;
                    n6 = n8;
                    n5 = n7;
                    if (n9 == 0x40000000) break block22;
                }
                if (!this.isInEditMode()) break block23;
                if (n10 == 0) {
                    n8 = 300;
                }
                n6 = n8;
                n5 = n7;
                if (n9 == 0) {
                    n5 = 300;
                    n6 = n8;
                }
            }
            this.setMeasuredDimension(n6, n5);
            n9 = this.D != null && x0.v((View)this) ? 1 : 0;
            int n11 = x0.y((View)this);
            int n12 = this.getChildCount();
            n8 = n7 = 0;
            for (n10 = 0; n10 < n12; ++n10) {
                StringBuilder stringBuilder;
                int n13;
                View view = this.getChildAt(n10);
                if (view.getVisibility() == 8) continue;
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (n9 != 0) {
                    WindowInsets windowInsets;
                    n13 = o0.s.b(layoutParams.a, n11);
                    if (x0.v(view)) {
                        windowInsets = (WindowInsets)this.D;
                        if (n13 == 3) {
                            stringBuilder = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
                        } else {
                            stringBuilder = windowInsets;
                            if (n13 == 5) {
                                stringBuilder = windowInsets.replaceSystemWindowInsets(0, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                            }
                        }
                        view.dispatchApplyWindowInsets((WindowInsets)stringBuilder);
                    } else {
                        windowInsets = (WindowInsets)this.D;
                        if (n13 == 3) {
                            stringBuilder = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
                        } else {
                            stringBuilder = windowInsets;
                            if (n13 == 5) {
                                stringBuilder = windowInsets.replaceSystemWindowInsets(0, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                            }
                        }
                        layoutParams.leftMargin = stringBuilder.getSystemWindowInsetLeft();
                        layoutParams.topMargin = stringBuilder.getSystemWindowInsetTop();
                        layoutParams.rightMargin = stringBuilder.getSystemWindowInsetRight();
                        layoutParams.bottomMargin = stringBuilder.getSystemWindowInsetBottom();
                    }
                }
                if (this.z(view)) {
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)(n6 - layoutParams.leftMargin - layoutParams.rightMargin), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)(n5 - layoutParams.topMargin - layoutParams.bottomMargin), (int)0x40000000));
                    continue;
                }
                if (this.B(view)) {
                    int n14;
                    float f3;
                    float f4;
                    if (Q && (f4 = x0.t(view)) != (f3 = this.d)) {
                        x0.n0(view, f3);
                    }
                    if ((n13 = (n14 = this.r(view) & 7) == 3 ? 1 : 0) != 0 && n7 != 0 || n13 == 0 && n8 != 0) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Child drawer has absolute gravity ");
                        stringBuilder.append(DrawerLayout.u(n14));
                        stringBuilder.append(" but this ");
                        stringBuilder.append("DrawerLayout");
                        stringBuilder.append(" already has a drawer view along that edge");
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                    if (n13 != 0) {
                        n7 = 1;
                    } else {
                        n8 = 1;
                    }
                    view.measure(ViewGroup.getChildMeasureSpec((int)n3, (int)(this.e + layoutParams.leftMargin + layoutParams.rightMargin), (int)layoutParams.width), ViewGroup.getChildMeasureSpec((int)n4, (int)(layoutParams.topMargin + layoutParams.bottomMargin), (int)layoutParams.height));
                    continue;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Child ");
                stringBuilder.append(view);
                stringBuilder.append(" at index ");
                stringBuilder.append(n10);
                stringBuilder.append(" does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                throw new IllegalStateException(stringBuilder.toString());
            }
            return;
        }
        throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        View view;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        int n3 = parcelable.e;
        if (n3 != 0 && (view = this.l(n3)) != null) {
            this.G(view);
        }
        if ((n3 = parcelable.f) != 3) {
            this.setDrawerLockMode(n3, 3);
        }
        if ((n3 = parcelable.g) != 3) {
            this.setDrawerLockMode(n3, 5);
        }
        if ((n3 = parcelable.h) != 3) {
            this.setDrawerLockMode(n3, 0x800003);
        }
        if ((n3 = parcelable.i) != 3) {
            this.setDrawerLockMode(n3, 0x800005);
        }
    }

    public void onRtlPropertiesChanged(int n3) {
        this.L();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            LayoutParams layoutParams = (LayoutParams)this.getChildAt(i3).getLayoutParams();
            int n4 = layoutParams.d;
            boolean bl = true;
            boolean bl2 = n4 == 1;
            if (n4 != 2) {
                bl = false;
            }
            if (!bl2 && !bl) {
                continue;
            }
            savedState.e = layoutParams.a;
            break;
        }
        savedState.f = this.p;
        savedState.g = this.q;
        savedState.h = this.r;
        savedState.i = this.s;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.i.F(motionEvent);
        this.j.F(motionEvent);
        int n3 = motionEvent.getAction() & 0xFF;
        boolean bl = false;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 3) {
                    this.g(true);
                    this.t = false;
                }
            } else {
                float f3;
                float f4 = motionEvent.getX();
                if ((motionEvent = this.i.t((int)f4, (int)(f3 = motionEvent.getY()))) == null || !this.z((View)motionEvent) || !((f4 -= this.w) * f4 + (f3 -= this.x) * f3 < (float)((n3 = this.i.z()) * n3)) || (motionEvent = this.m()) == null || this.p((View)motionEvent) == 2) {
                    bl = true;
                }
                this.g(bl);
            }
        } else {
            float f5 = motionEvent.getX();
            float f6 = motionEvent.getY();
            this.w = f5;
            this.x = f6;
            this.t = false;
        }
        return true;
    }

    public int p(View view) {
        if (this.B(view)) {
            return this.o(((LayoutParams)view.getLayoutParams()).a);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(view);
        stringBuilder.append(" is not a drawer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public CharSequence q(int n3) {
        if ((n3 = o0.s.b(n3, x0.y((View)this))) == 3) {
            return this.B;
        }
        if (n3 == 5) {
            return this.C;
        }
        return null;
    }

    public int r(View view) {
        return o0.s.b(((LayoutParams)view.getLayoutParams()).a, x0.y((View)this));
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        super.requestDisallowInterceptTouchEvent(bl);
        if (bl) {
            this.g(true);
        }
    }

    public void requestLayout() {
        if (!this.n) {
            super.requestLayout();
        }
    }

    public float s(View view) {
        return ((LayoutParams)view.getLayoutParams()).b;
    }

    public void setChildInsets(Object object, boolean bl) {
        this.D = object;
        this.E = bl;
        bl = !bl && this.getBackground() == null;
        this.setWillNotDraw(bl);
        this.requestLayout();
    }

    public void setDrawerElevation(float f3) {
        this.d = f3;
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            View view = this.getChildAt(i3);
            if (!this.B(view)) continue;
            x0.n0(view, this.d);
        }
    }

    @Deprecated
    public void setDrawerListener(e e3) {
        e e4 = this.u;
        if (e4 != null) {
            this.I(e4);
        }
        if (e3 != null) {
            this.a(e3);
        }
        this.u = e3;
    }

    public void setDrawerLockMode(int n3) {
        this.setDrawerLockMode(n3, 3);
        this.setDrawerLockMode(n3, 5);
    }

    public void setDrawerLockMode(int n3, int n4) {
        v0.c c3;
        int n5 = o0.s.b(n4, x0.y((View)this));
        if (n4 != 3) {
            if (n4 != 5) {
                if (n4 != 0x800003) {
                    if (n4 == 0x800005) {
                        this.s = n3;
                    }
                } else {
                    this.r = n3;
                }
            } else {
                this.q = n3;
            }
        } else {
            this.p = n3;
        }
        if (n3 != 0) {
            c3 = n5 == 3 ? this.i : this.j;
            c3.a();
        }
        if (n3 != 1) {
            if (n3 == 2 && (c3 = this.l(n5)) != null) {
                this.G((View)c3);
                return;
            }
        } else {
            c3 = this.l(n5);
            if (c3 != null) {
                this.d((View)c3);
            }
        }
    }

    public void setDrawerLockMode(int n3, View view) {
        if (this.B(view)) {
            this.setDrawerLockMode(n3, ((LayoutParams)view.getLayoutParams()).a);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(view);
        stringBuilder.append(" is not a drawer with appropriate layout_gravity");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setDrawerShadow(int n3, int n4) {
        this.setDrawerShadow(a.d(this.getContext(), n3), n4);
    }

    public void setDrawerShadow(Drawable drawable, int n3) {
        block2: {
            block4: {
                block6: {
                    block5: {
                        block3: {
                            if (Q) break block2;
                            if ((n3 & 0x800003) != 0x800003) break block3;
                            this.F = drawable;
                            break block4;
                        }
                        if ((n3 & 0x800005) != 0x800005) break block5;
                        this.G = drawable;
                        break block4;
                    }
                    if ((n3 & 3) != 3) break block6;
                    this.H = drawable;
                    break block4;
                }
                if ((n3 & 5) != 5) break block2;
                this.I = drawable;
            }
            this.L();
            this.invalidate();
        }
    }

    public void setDrawerTitle(int n3, CharSequence charSequence) {
        if ((n3 = o0.s.b(n3, x0.y((View)this))) == 3) {
            this.B = charSequence;
            return;
        }
        if (n3 == 5) {
            this.C = charSequence;
        }
    }

    public void setScrimColor(int n3) {
        this.f = n3;
        this.invalidate();
    }

    public void setStatusBarBackground(int n3) {
        Drawable drawable = n3 != 0 ? a.d(this.getContext(), n3) : null;
        this.y = drawable;
        this.invalidate();
    }

    public void setStatusBarBackground(Drawable drawable) {
        this.y = drawable;
        this.invalidate();
    }

    public void setStatusBarBackgroundColor(int n3) {
        this.y = new ColorDrawable(n3);
        this.invalidate();
    }

    public final MotionEvent t(MotionEvent motionEvent, View view) {
        float f3 = this.getScrollX() - view.getLeft();
        float f4 = this.getScrollY() - view.getTop();
        motionEvent = MotionEvent.obtain((MotionEvent)motionEvent);
        motionEvent.offsetLocation(f3, f4);
        view = view.getMatrix();
        if (!view.isIdentity()) {
            if (this.L == null) {
                this.L = new Matrix();
            }
            view.invert(this.L);
            motionEvent.transform(this.L);
        }
        return motionEvent;
    }

    public final boolean w() {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!((LayoutParams)this.getChildAt((int)i3).getLayoutParams()).c) continue;
            return true;
        }
        return false;
    }

    public final boolean x() {
        return this.n() != null;
    }

    public boolean z(View view) {
        return ((LayoutParams)view.getLayoutParams()).a == 0;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int a = 0;
        public float b;
        public boolean c;
        public int d;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, O);
            this.a = context.getInt(0, 0);
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.a = layoutParams.a;
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public int e = 0;
        public int f;
        public int g;
        public int h;
        public int i;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readInt();
            this.f = parcel.readInt();
            this.g = parcel.readInt();
            this.h = parcel.readInt();
            this.i = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f);
            parcel.writeInt(this.g);
            parcel.writeInt(this.h);
            parcel.writeInt(this.i);
        }
    }

    public class c
    extends o0.a {
        public final Rect d;
        public final DrawerLayout e;

        public c(DrawerLayout drawerLayout) {
            this.e = drawerLayout;
            this.d = new Rect();
        }

        @Override
        public boolean a(View object, AccessibilityEvent object2) {
            if (object2.getEventType() == 32) {
                int n3;
                object = object2.getText();
                object2 = this.e.n();
                if (object2 != null && (object2 = this.e.q(n3 = this.e.r((View)object2))) != null) {
                    object.add(object2);
                }
                return true;
            }
            return super.a((View)object, (AccessibilityEvent)object2);
        }

        @Override
        public void f(View view, AccessibilityEvent accessibilityEvent) {
            super.f(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)"androidx.drawerlayout.widget.DrawerLayout");
        }

        @Override
        public void g(View view, p0.s s3) {
            if (P) {
                super.g(view, s3);
            } else {
                p0.s s4 = p0.s.W(s3);
                super.g(view, s4);
                s3.E0(view);
                ViewParent viewParent = x0.C(view);
                if (viewParent instanceof View) {
                    s3.w0((View)viewParent);
                }
                this.o(s3, s4);
                s4.Y();
                this.n(s3, (ViewGroup)view);
            }
            s3.h0("androidx.drawerlayout.widget.DrawerLayout");
            s3.o0(false);
            s3.p0(false);
            s3.Z(s.a.e);
            s3.Z(s.a.f);
        }

        @Override
        public boolean i(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!P && !DrawerLayout.y(view)) {
                return false;
            }
            return super.i(viewGroup, view, accessibilityEvent);
        }

        public final void n(p0.s s3, ViewGroup viewGroup) {
            int n3 = viewGroup.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                View view = viewGroup.getChildAt(i3);
                if (!DrawerLayout.y(view)) continue;
                s3.c(view);
            }
        }

        public final void o(p0.s s3, p0.s s4) {
            Rect rect = this.d;
            s4.l(rect);
            s3.d0(rect);
            s3.J0(s4.T());
            s3.u0(s4.v());
            s3.h0(s4.o());
            s3.l0(s4.r());
            s3.m0(s4.I());
            s3.p0(s4.K());
            s3.a0(s4.D());
            s3.C0(s4.Q());
            s3.a(s4.i());
        }
    }

    public static final class d
    extends o0.a {
        @Override
        public void g(View view, p0.s s3) {
            super.g(view, s3);
            if (!DrawerLayout.y(view)) {
                s3.w0(null);
            }
        }
    }

    public static interface e {
        public void a(int var1);

        public void b(View var1, float var2);

        public void c(View var1);

        public void d(View var1);
    }

    public static abstract class f
    implements e {
        @Override
        public void a(int n3) {
        }

        @Override
        public void b(View view, float f3) {
        }
    }

    public class g
    extends c.c {
        public final int a;
        public v0.c b;
        public final Runnable c;
        public final DrawerLayout d;

        public g(DrawerLayout drawerLayout, int n3) {
            this.d = drawerLayout;
            this.c = new Runnable(this){
                public final g c;
                {
                    this.c = g3;
                }

                @Override
                public void run() {
                    this.c.o();
                }
            };
            this.a = n3;
        }

        @Override
        public int a(View view, int n3, int n4) {
            if (this.d.c(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(n3, 0));
            }
            n4 = this.d.getWidth();
            return Math.max(n4 - view.getWidth(), Math.min(n3, n4));
        }

        @Override
        public int b(View view, int n3, int n4) {
            return view.getTop();
        }

        @Override
        public int d(View view) {
            if (this.d.B(view)) {
                return view.getWidth();
            }
            return 0;
        }

        @Override
        public void f(int n3, int n4) {
            View view = (n3 & 1) == 1 ? this.d.l(3) : this.d.l(5);
            if (view != null && this.d.p(view) == 0) {
                this.b.b(view, n4);
            }
        }

        @Override
        public boolean g(int n3) {
            return false;
        }

        @Override
        public void h(int n3, int n4) {
            this.d.postDelayed(this.c, 160L);
        }

        @Override
        public void i(View view, int n3) {
            ((LayoutParams)view.getLayoutParams()).c = false;
            this.n();
        }

        @Override
        public void j(int n3) {
            this.d.P(n3, this.b.v());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void k(View view, int n3, int n4, int n5, int n6) {
            n4 = view.getWidth();
            float f3 = this.d.c(view, 3) ? (float)(n3 + n4) : (float)(this.d.getWidth() - n3);
            this.d.M(view, f3 /= (float)n4);
            n3 = f3 == 0.0f ? 4 : 0;
            view.setVisibility(n3);
            this.d.invalidate();
        }

        @Override
        public void l(View view, float f3, float f4) {
            float f5;
            block4: {
                int n3;
                int n4;
                block5: {
                    block3: {
                        f4 = this.d.s(view);
                        n4 = view.getWidth();
                        if (!this.d.c(view, 3)) break block3;
                        float f6 = f3 - 0.0f;
                        f5 = f6 == 0.0f ? 0 : (f6 > 0.0f ? 1 : -1);
                        f5 = !(f5 > 0 || f5 == false && f4 > 0.5f) ? (float)(-n4) : 0.0f;
                        break block4;
                    }
                    n3 = this.d.getWidth();
                    if (f3 < 0.0f) break block5;
                    f5 = n3;
                    if (f3 != 0.0f) break block4;
                    f5 = n3;
                    if (!(f4 > 0.5f)) break block4;
                }
                f5 = n3 - n4;
            }
            this.b.O((int)f5, view.getTop());
            this.d.invalidate();
        }

        @Override
        public boolean m(View view, int n3) {
            return this.d.B(view) && this.d.c(view, this.a) && this.d.p(view) == 0;
        }

        public final void n() {
            View view;
            int n3 = this.a;
            int n4 = 3;
            if (n3 == 3) {
                n4 = 5;
            }
            if ((view = this.d.l(n4)) != null) {
                this.d.d(view);
            }
        }

        public void o() {
            View view;
            int n3 = this.b.x();
            int n4 = this.a;
            int n5 = 0;
            if ((n4 = n4 == 3 ? 1 : 0) != 0) {
                view = this.d.l(3);
                if (view != null) {
                    n5 = -view.getWidth();
                }
                n5 += n3;
            } else {
                view = this.d.l(5);
                n5 = this.d.getWidth() - n3;
            }
            if (view != null && (n4 != 0 && view.getLeft() < n5 || n4 == 0 && view.getLeft() > n5) && this.d.p(view) == 0) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                this.b.Q(view, n5, view.getTop());
                layoutParams.c = true;
                this.d.invalidate();
                this.n();
                this.d.b();
            }
        }

        public void p() {
            this.d.removeCallbacks(this.c);
        }

        public void q(v0.c c3) {
            this.b = c3;
        }
    }
}

