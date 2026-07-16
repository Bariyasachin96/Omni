/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.LayoutTransition
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.Observable
 *  android.graphics.Canvas
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.FocusFinder
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.OverScroller
 */
package androidx.recyclerview.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.core.widget.f;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.a;
import androidx.recyclerview.widget.b;
import androidx.recyclerview.widget.c;
import androidx.recyclerview.widget.d;
import androidx.recyclerview.widget.e;
import androidx.recyclerview.widget.k;
import androidx.recyclerview.widget.o;
import androidx.recyclerview.widget.p;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import o0.b1;
import o0.x0;
import p0.s;

public class RecyclerView
extends ViewGroup
implements o0.a0 {
    public static boolean D0 = false;
    public static boolean E0 = false;
    public static final int[] F0 = new int[]{16843830};
    public static final float G0 = (float)(Math.log(0.78) / Math.log(0.9));
    public static final boolean H0 = false;
    public static final boolean I0 = true;
    public static final boolean J0 = true;
    public static final boolean K0 = true;
    public static final boolean L0 = false;
    public static final boolean M0 = false;
    public static final Class[] N0;
    public static final Interpolator O0;
    public static final a0 P0;
    public boolean A;
    public int A0;
    public boolean B;
    public int B0;
    public int C;
    public final p.b C0;
    public boolean D;
    public final AccessibilityManager E;
    public List F;
    public boolean G = false;
    public boolean H = false;
    public int I = 0;
    public int J = 0;
    public l K;
    public EdgeEffect L;
    public EdgeEffect M;
    public EdgeEffect N;
    public EdgeEffect O;
    public m P;
    public int Q = 0;
    public int R = -1;
    public VelocityTracker S;
    public int T;
    public int U;
    public int V;
    public int W;
    public int a0;
    public r b0;
    public final float c;
    public final int c0;
    public final x d = new x(this);
    public final int d0;
    public final v e = new v(this);
    public float e0;
    public SavedState f;
    public float f0;
    public a g;
    public boolean g0 = true;
    public b h;
    public final c0 h0;
    public final androidx.recyclerview.widget.p i = new androidx.recyclerview.widget.p();
    public e i0;
    public boolean j;
    public e.b j0;
    public final Runnable k = new Runnable(this){
        public final RecyclerView c;
        {
            this.c = recyclerView;
        }

        @Override
        public void run() {
            RecyclerView recyclerView = this.c;
            if (recyclerView.x && !recyclerView.isLayoutRequested()) {
                recyclerView = this.c;
                if (!recyclerView.u) {
                    recyclerView.requestLayout();
                    return;
                }
                if (recyclerView.A) {
                    recyclerView.z = true;
                    return;
                }
                recyclerView.A();
            }
        }
    };
    public final z k0;
    public final Rect l = new Rect();
    public t l0;
    public final Rect m = new Rect();
    public List m0;
    public final RectF n = new RectF();
    public boolean n0;
    public h o;
    public boolean o0;
    public p p;
    public m.a p0;
    public final List q = new ArrayList();
    public boolean q0;
    public final ArrayList r = new ArrayList();
    public androidx.recyclerview.widget.k r0;
    public final ArrayList s = new ArrayList();
    public final int[] s0;
    public s t;
    public o0.b0 t0;
    public boolean u;
    public final int[] u0;
    public boolean v;
    public final int[] v0;
    public boolean w;
    public final int[] w0;
    public boolean x;
    public final List x0;
    public int y = 0;
    public Runnable y0;
    public boolean z;
    public boolean z0;

    static {
        Class<Integer> clazz = Integer.TYPE;
        N0 = new Class[]{Context.class, AttributeSet.class, clazz, clazz};
        O0 = new Interpolator(){

            public float getInterpolation(float f3) {
                return (f3 -= 1.0f) * f3 * f3 * f3 * f3 + 1.0f;
            }
        };
        P0 = new a0();
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, i1.a.recyclerViewStyle);
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        this.K = P0;
        this.P = new c();
        this.e0 = Float.MIN_VALUE;
        this.f0 = Float.MIN_VALUE;
        this.h0 = new c0(this);
        e.b b3 = K0 ? new e.b() : null;
        this.j0 = b3;
        this.k0 = new z();
        this.n0 = false;
        this.o0 = false;
        this.p0 = new n(this);
        this.q0 = false;
        this.s0 = new int[2];
        this.u0 = new int[2];
        this.v0 = new int[2];
        this.w0 = new int[2];
        this.x0 = new ArrayList();
        this.y0 = new Runnable(this){
            public final RecyclerView c;
            {
                this.c = recyclerView;
            }

            @Override
            public void run() {
                m m3 = this.c.P;
                if (m3 != null) {
                    m3.u();
                }
                this.c.q0 = false;
            }
        };
        this.A0 = 0;
        this.B0 = 0;
        this.C0 = new p.b(this){
            public final RecyclerView a;
            {
                this.a = recyclerView;
            }

            @Override
            public void a(d0 d02) {
                RecyclerView recyclerView = this.a;
                recyclerView.p.q1(d02.a, recyclerView.e);
            }

            @Override
            public void b(d0 d02, m.b b3, m.b b4) {
                this.a.o(d02, b3, b4);
            }

            @Override
            public void c(d0 d02, m.b b3, m.b b4) {
                this.a.e.O(d02);
                this.a.q(d02, b3, b4);
            }

            @Override
            public void d(d0 d02, m.b b3, m.b b4) {
                d02.G(false);
                RecyclerView recyclerView = this.a;
                if (recyclerView.G) {
                    if (recyclerView.P.b(d02, d02, b3, b4)) {
                        this.a.U0();
                        return;
                    }
                } else if (recyclerView.P.d(d02, b3, b4)) {
                    this.a.U0();
                }
            }
        };
        this.setScrollContainer(true);
        this.setFocusableInTouchMode(true);
        b3 = ViewConfiguration.get((Context)context);
        this.a0 = b3.getScaledTouchSlop();
        this.e0 = b1.e((ViewConfiguration)b3, context);
        this.f0 = b1.h((ViewConfiguration)b3, context);
        this.c0 = b3.getScaledMinimumFlingVelocity();
        this.d0 = b3.getScaledMaximumFlingVelocity();
        this.c = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        boolean bl = this.getOverScrollMode() == 2;
        this.setWillNotDraw(bl);
        this.P.v(this.p0);
        this.v0();
        this.x0();
        this.w0();
        if (o0.x0.w((View)this) == 0) {
            o0.x0.o0((View)this, 1);
        }
        this.E = (AccessibilityManager)this.getContext().getSystemService("accessibility");
        this.setAccessibilityDelegateCompat(new androidx.recyclerview.widget.k(this));
        Object object = i1.c.RecyclerView;
        b3 = context.obtainStyledAttributes(attributeSet, object, n3, 0);
        o0.x0.f0((View)this, context, object, attributeSet, (TypedArray)b3, n3, 0);
        object = b3.getString(i1.c.RecyclerView_layoutManager);
        if (b3.getInt(i1.c.RecyclerView_android_descendantFocusability, -1) == -1) {
            this.setDescendantFocusability(262144);
        }
        this.j = b3.getBoolean(i1.c.RecyclerView_android_clipToPadding, true);
        this.w = bl = b3.getBoolean(i1.c.RecyclerView_fastScrollEnabled, false);
        if (bl) {
            this.y0((StateListDrawable)b3.getDrawable(i1.c.RecyclerView_fastScrollVerticalThumbDrawable), b3.getDrawable(i1.c.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)b3.getDrawable(i1.c.RecyclerView_fastScrollHorizontalThumbDrawable), b3.getDrawable(i1.c.RecyclerView_fastScrollHorizontalTrackDrawable));
        }
        b3.recycle();
        this.B(context, (String)object, attributeSet, n3, 0);
        object = F0;
        b3 = context.obtainStyledAttributes(attributeSet, object, n3, 0);
        o0.x0.f0((View)this, context, object, attributeSet, (TypedArray)b3, n3, 0);
        bl = b3.getBoolean(0, true);
        b3.recycle();
        this.setNestedScrollingEnabled(bl);
        u0.a.d((View)this, true);
    }

    private boolean D1(MotionEvent motionEvent) {
        boolean bl;
        EdgeEffect edgeEffect = this.L;
        if (edgeEffect != null && androidx.core.widget.f.b(edgeEffect) != 0.0f && !this.canScrollHorizontally(-1)) {
            androidx.core.widget.f.d(this.L, 0.0f, 1.0f - motionEvent.getY() / (float)this.getHeight());
            bl = true;
        } else {
            bl = false;
        }
        edgeEffect = this.N;
        boolean bl2 = bl;
        if (edgeEffect != null) {
            bl2 = bl;
            if (androidx.core.widget.f.b(edgeEffect) != 0.0f) {
                bl2 = bl;
                if (!this.canScrollHorizontally(1)) {
                    androidx.core.widget.f.d(this.N, 0.0f, motionEvent.getY() / (float)this.getHeight());
                    bl2 = true;
                }
            }
        }
        edgeEffect = this.M;
        bl = bl2;
        if (edgeEffect != null) {
            bl = bl2;
            if (androidx.core.widget.f.b(edgeEffect) != 0.0f) {
                bl = bl2;
                if (!this.canScrollVertically(-1)) {
                    androidx.core.widget.f.d(this.M, 0.0f, motionEvent.getX() / (float)this.getWidth());
                    bl = true;
                }
            }
        }
        if ((edgeEffect = this.O) != null && androidx.core.widget.f.b(edgeEffect) != 0.0f && !this.canScrollVertically(1)) {
            androidx.core.widget.f.d(this.O, 0.0f, 1.0f - motionEvent.getX() / (float)this.getWidth());
            return true;
        }
        return bl;
    }

    public static RecyclerView b0(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView)view;
        }
        view = (ViewGroup)view;
        int n3 = view.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            RecyclerView recyclerView = RecyclerView.b0(view.getChildAt(i3));
            if (recyclerView == null) continue;
            return recyclerView;
        }
        return null;
    }

    private int d1(int n3, float f3) {
        float f4 = f3 / (float)this.getWidth();
        float f5 = (float)n3 / (float)this.getHeight();
        EdgeEffect edgeEffect = this.M;
        float f6 = 0.0f;
        float f7 = 0.0f;
        f3 = 0.0f;
        if (edgeEffect != null && androidx.core.widget.f.b(edgeEffect) != 0.0f) {
            if (this.canScrollVertically(-1)) {
                this.M.onRelease();
            } else {
                f3 = -androidx.core.widget.f.d(this.M, -f5, f4);
                if (androidx.core.widget.f.b(this.M) == 0.0f) {
                    this.M.onRelease();
                }
            }
            this.invalidate();
        } else {
            edgeEffect = this.O;
            f3 = f7;
            if (edgeEffect != null) {
                f3 = f7;
                if (androidx.core.widget.f.b(edgeEffect) != 0.0f) {
                    if (this.canScrollVertically(1)) {
                        this.O.onRelease();
                        f3 = f6;
                    } else {
                        f3 = androidx.core.widget.f.d(this.O, f5, 1.0f - f4);
                        if (androidx.core.widget.f.b(this.O) == 0.0f) {
                            this.O.onRelease();
                        }
                    }
                    this.invalidate();
                }
            }
        }
        return Math.round(f3 * (float)this.getHeight());
    }

    private o0.b0 getScrollingChildHelper() {
        if (this.t0 == null) {
            this.t0 = new o0.b0((View)this);
        }
        return this.t0;
    }

    public static d0 m0(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams)view.getLayoutParams()).a;
    }

    public static void n0(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        Rect rect2 = layoutParams.b;
        rect.set(view.getLeft() - rect2.left - layoutParams.leftMargin, view.getTop() - rect2.top - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    private float r0(int n3) {
        double d3 = Math.log((float)Math.abs(n3) * 0.35f / (this.c * 0.015f));
        float f3 = G0;
        double d4 = f3;
        return (float)((double)(this.c * 0.015f) * Math.exp((double)f3 / (d4 - 1.0) * d3));
    }

    public static void setDebugAssertionsEnabled(boolean bl) {
        D0 = bl;
    }

    public static void setVerboseLoggingEnabled(boolean bl) {
        E0 = bl;
    }

    public static void u(d0 d02) {
        block4: {
            WeakReference weakReference = d02.b;
            if (weakReference != null) {
                weakReference = (View)weakReference.get();
                while (weakReference != null) {
                    if (weakReference != d02.a) {
                        if ((weakReference = weakReference.getParent()) instanceof View) {
                            weakReference = (View)weakReference;
                            continue;
                        }
                        weakReference = null;
                        continue;
                    }
                    break block4;
                }
                d02.b = null;
            }
        }
    }

    public void A() {
        if (this.x && !this.G) {
            if (this.g.p()) {
                if (this.g.o(4) && !this.g.o(11)) {
                    k0.e.a("RV PartialInvalidate");
                    this.B1();
                    this.O0();
                    this.g.t();
                    if (!this.z) {
                        if (this.u0()) {
                            this.H();
                        } else {
                            this.g.i();
                        }
                    }
                    this.E1(true);
                    this.P0();
                    k0.e.b();
                    return;
                }
                if (this.g.p()) {
                    k0.e.a("RV FullInvalidate");
                    this.H();
                    k0.e.b();
                }
            }
            return;
        }
        k0.e.a("RV FullInvalidate");
        this.H();
        k0.e.b();
    }

    public boolean A0() {
        AccessibilityManager accessibilityManager = this.E;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    public void A1(int n3) {
        if (this.A) {
            return;
        }
        p p3 = this.p;
        if (p3 == null) {
            Log.e((String)"RecyclerView", (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        p3.M1(this, this.k0, n3);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void B(Context object, String object2, AttributeSet attributeSet, int n3, int n4) {
        if (object2 == null) return;
        if (((String)(object2 = ((String)object2).trim())).isEmpty()) return;
        String string = this.p0((Context)object, (String)object2);
        try {
            object2 = this.isInEditMode() ? this.getClass().getClassLoader() : object.getClassLoader();
            Class<p> clazz = Class.forName(string, false, (ClassLoader)object2).asSubclass(p.class);
            try {
                Constructor<p> constructor = clazz.getConstructor(N0);
                object2 = new Object[]{object, attributeSet, n3, n4};
                object = constructor;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                object2 = null;
                try {
                    object = clazz.getConstructor(null);
                }
                catch (NoSuchMethodException noSuchMethodException2) {
                    noSuchMethodException2.initCause(noSuchMethodException);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(attributeSet.getPositionDescription());
                    ((StringBuilder)object2).append(": Error creating LayoutManager ");
                    ((StringBuilder)object2).append(string);
                    IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)object2).toString(), noSuchMethodException2);
                    throw illegalStateException;
                }
            }
            ((AccessibleObject)object).setAccessible(true);
            this.setLayoutManager((p)((Constructor)object).newInstance((Object[])object2));
            return;
        }
        catch (ClassCastException classCastException) {}
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(attributeSet.getPositionDescription());
        ((StringBuilder)object2).append(": Class is not a LayoutManager ");
        ((StringBuilder)object2).append(string);
        throw new IllegalStateException(((StringBuilder)object2).toString(), classCastException);
        catch (IllegalAccessException illegalAccessException) {}
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(attributeSet.getPositionDescription());
        ((StringBuilder)object2).append(": Cannot access non-public constructor ");
        ((StringBuilder)object2).append(string);
        throw new IllegalStateException(((StringBuilder)object2).toString(), illegalAccessException);
        catch (InstantiationException instantiationException) {}
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(attributeSet.getPositionDescription());
        ((StringBuilder)object2).append(": Could not instantiate the LayoutManager: ");
        ((StringBuilder)object2).append(string);
        throw new IllegalStateException(((StringBuilder)object2).toString(), instantiationException);
        catch (InvocationTargetException invocationTargetException) {}
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(attributeSet.getPositionDescription());
        ((StringBuilder)object2).append(": Could not instantiate the LayoutManager: ");
        ((StringBuilder)object2).append(string);
        throw new IllegalStateException(((StringBuilder)object2).toString(), invocationTargetException);
        catch (ClassNotFoundException classNotFoundException) {}
        object = new StringBuilder();
        ((StringBuilder)object).append(attributeSet.getPositionDescription());
        ((StringBuilder)object).append(": Unable to find LayoutManager ");
        ((StringBuilder)object).append(string);
        throw new IllegalStateException(((StringBuilder)object).toString(), classNotFoundException);
    }

    public boolean B0() {
        return this.I > 0;
    }

    public void B1() {
        int n3;
        this.y = n3 = this.y + 1;
        if (n3 == 1 && !this.A) {
            this.z = false;
        }
    }

    public void C(int n3, int n4) {
        this.setMeasuredDimension(androidx.recyclerview.widget.RecyclerView$p.s(n3, this.getPaddingLeft() + this.getPaddingRight(), o0.x0.A((View)this)), androidx.recyclerview.widget.RecyclerView$p.s(n4, this.getPaddingTop() + this.getPaddingBottom(), o0.x0.z((View)this)));
    }

    public final boolean C0(View object, View view, int n3) {
        if (view != null && view != this && view != object) {
            int n4;
            if (this.X(view) == null) {
                return false;
            }
            if (object == null) {
                return true;
            }
            if (this.X((View)object) == null) {
                return true;
            }
            this.l.set(0, 0, object.getWidth(), object.getHeight());
            this.m.set(0, 0, view.getWidth(), view.getHeight());
            this.offsetDescendantRectToMyCoords((View)object, this.l);
            this.offsetDescendantRectToMyCoords(view, this.m);
            int n5 = this.p.d0();
            int n6 = -1;
            int n7 = n5 == 1 ? -1 : 1;
            view = this.l;
            int n8 = view.left;
            object = this.m;
            int n9 = ((Rect)object).left;
            n5 = (n8 < n9 || view.right <= n9) && view.right < ((Rect)object).right ? 1 : (((n4 = view.right) > (n5 = ((Rect)object).right) || n8 >= n5) && n8 > n9 ? -1 : 0);
            n4 = view.top;
            int n10 = ((Rect)object).top;
            if ((n4 < n10 || view.bottom <= n10) && view.bottom < ((Rect)object).bottom) {
                n6 = 1;
            } else {
                n8 = view.bottom;
                n9 = ((Rect)object).bottom;
                if (n8 <= n9 && n4 < n9 || n4 <= n10) {
                    n6 = 0;
                }
            }
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 17) {
                        if (n3 != 33) {
                            if (n3 != 66) {
                                if (n3 == 130) {
                                    return n6 > 0;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Invalid direction: ");
                                ((StringBuilder)object).append(n3);
                                ((StringBuilder)object).append(this.V());
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            return n5 > 0;
                        }
                        return n6 < 0;
                    }
                    return n5 < 0;
                }
                return n6 > 0 || n6 == 0 && n5 * n7 > 0;
                {
                }
            }
            return n6 < 0 || n6 == 0 && n5 * n7 < 0;
            {
            }
        }
        return false;
    }

    public boolean C1(int n3, int n4) {
        return this.getScrollingChildHelper().p(n3, n4);
    }

    public final boolean D(int n3, int n4) {
        this.a0(this.s0);
        int[] nArray = this.s0;
        return nArray[0] != n3 || nArray[1] != n4;
        {
        }
    }

    public void D0(int n3) {
        if (this.p == null) {
            return;
        }
        this.setScrollState(2);
        this.p.B1(n3);
        this.awakenScrollBars();
    }

    public void E(View view) {
        Object object = RecyclerView.m0(view);
        this.M0(view);
        h h3 = this.o;
        if (h3 != null && object != null) {
            h3.t((d0)object);
        }
        if ((object = this.F) != null) {
            for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                ((q)this.F.get(i3)).a(view);
            }
        }
    }

    public void E0() {
        int n3 = this.h.j();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((LayoutParams)this.h.i((int)i3).getLayoutParams()).c = true;
        }
        this.e.s();
    }

    public void E1(boolean bl) {
        if (this.y < 1) {
            if (!D0) {
                this.y = 1;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stopInterceptRequestLayout was called more times than startInterceptRequestLayout.");
                stringBuilder.append(this.V());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        if (!bl && !this.A) {
            this.z = false;
        }
        if (this.y == 1) {
            if (bl && this.z && !this.A && this.p != null && this.o != null) {
                this.H();
            }
            if (!this.A) {
                this.z = false;
            }
        }
        --this.y;
    }

    public void F(View view) {
        d0 d02 = RecyclerView.m0(view);
        this.N0(view);
        Object object = this.o;
        if (object != null && d02 != null) {
            ((h)object).u(d02);
        }
        if ((object = this.F) != null) {
            for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                ((q)this.F.get(i3)).b(view);
            }
        }
    }

    public void F0() {
        int n3 = this.h.j();
        for (int i3 = 0; i3 < n3; ++i3) {
            d0 d02 = RecyclerView.m0(this.h.i(i3));
            if (d02 == null || d02.J()) continue;
            d02.b(6);
        }
        this.E0();
        this.e.t();
    }

    public void F1(int n3) {
        this.getScrollingChildHelper().r(n3);
    }

    public final void G() {
        int n3 = this.C;
        this.C = 0;
        if (n3 != 0 && this.A0()) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
            accessibilityEvent.setEventType(2048);
            p0.b.b(accessibilityEvent, n3);
            this.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public final void G0(int n3, int n4, MotionEvent object, int n5) {
        Object object2 = this.p;
        if (object2 == null) {
            Log.e((String)"RecyclerView", (String)"Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.A) {
            return;
        }
        int[] nArray = this.w0;
        int n6 = 0;
        nArray[0] = 0;
        nArray[1] = 0;
        int n7 = ((p)object2).p();
        boolean bl = this.p.q();
        int n8 = bl ? n7 | 2 : n7;
        float f3 = object == null ? (float)this.getHeight() / 2.0f : object.getY();
        float f4 = object == null ? (float)this.getWidth() / 2.0f : object.getX();
        int n9 = n3 - this.c1(n3, f3);
        int n10 = n4 - this.d1(n4, f4);
        this.C1(n8, n5);
        n8 = n7 != 0 ? n9 : 0;
        int n11 = bl ? n10 : 0;
        n4 = n9;
        n3 = n10;
        if (this.L(n8, n11, this.w0, this.u0, n5)) {
            object2 = this.w0;
            n4 = n9 - object2[0];
            n3 = n10 - object2[1];
        }
        n9 = n7 != 0 ? n4 : 0;
        n10 = n6;
        if (bl) {
            n10 = n3;
        }
        this.p1(n9, n10, (MotionEvent)object, n5);
        object = this.i0;
        if (object != null && (n4 != 0 || n3 != 0)) {
            ((e)object).f(this, n4, n3);
        }
        this.F1(n5);
    }

    public void G1() {
        this.setScrollState(0);
        this.H1();
    }

    public void H() {
        if (this.o == null) {
            Log.w((String)"RecyclerView", (String)"No adapter attached; skipping layout");
            return;
        }
        if (this.p == null) {
            Log.e((String)"RecyclerView", (String)"No layout manager attached; skipping layout");
            return;
        }
        this.k0.j = false;
        boolean bl = this.z0 && (this.A0 != this.getWidth() || this.B0 != this.getHeight());
        this.A0 = 0;
        this.B0 = 0;
        this.z0 = false;
        if (this.k0.e == 1) {
            this.I();
            this.p.D1(this);
            this.J();
        } else if (!this.g.q() && !bl && this.p.s0() == this.getWidth() && this.p.b0() == this.getHeight()) {
            this.p.D1(this);
        } else {
            this.p.D1(this);
            this.J();
        }
        this.K();
    }

    public void H0(int n3) {
        int n4 = this.h.g();
        for (int i3 = 0; i3 < n4; ++i3) {
            this.h.f(i3).offsetLeftAndRight(n3);
        }
    }

    public final void H1() {
        this.h0.f();
        p p3 = this.p;
        if (p3 != null) {
            p3.O1();
        }
    }

    public final void I() {
        m.b b3;
        int n3;
        int n4;
        Object object = this.k0;
        boolean bl = true;
        ((z)object).a(1);
        this.W(this.k0);
        this.k0.j = false;
        this.B1();
        this.i.f();
        this.O0();
        this.W0();
        this.n1();
        object = this.k0;
        if (!((z)object).k || !this.o0) {
            bl = false;
        }
        ((z)object).i = bl;
        this.o0 = false;
        this.n0 = false;
        ((z)object).h = ((z)object).l;
        ((z)object).f = this.o.f();
        this.a0(this.s0);
        if (this.k0.k) {
            n4 = this.h.g();
            for (n3 = 0; n3 < n4; ++n3) {
                object = RecyclerView.m0(this.h.f(n3));
                if (((d0)object).J() || ((d0)object).t() && !this.o.j()) continue;
                b3 = this.P.t(this.k0, (d0)object, androidx.recyclerview.widget.RecyclerView$m.e((d0)object), ((d0)object).o());
                this.i.e((d0)object, b3);
                if (!this.k0.i || !((d0)object).y() || ((d0)object).v() || ((d0)object).J() || ((d0)object).t()) continue;
                long l3 = this.i0((d0)object);
                this.i.c(l3, (d0)object);
            }
        }
        if (this.k0.l) {
            this.o1();
            object = this.k0;
            bl = ((z)object).g;
            ((z)object).g = false;
            this.p.b1(this.e, (z)object);
            this.k0.g = bl;
            for (n3 = 0; n3 < this.h.g(); ++n3) {
                object = RecyclerView.m0(this.h.f(n3));
                if (((d0)object).J() || this.i.i((d0)object)) continue;
                int n5 = androidx.recyclerview.widget.RecyclerView$m.e((d0)object);
                bl = ((d0)object).p(8192);
                n4 = n5;
                if (!bl) {
                    n4 = n5 | 0x1000;
                }
                b3 = this.P.t(this.k0, (d0)object, n4, ((d0)object).o());
                if (bl) {
                    this.Z0((d0)object, b3);
                    continue;
                }
                this.i.a((d0)object, b3);
            }
            this.v();
        } else {
            this.v();
        }
        this.P0();
        this.E1(false);
        this.k0.e = 2;
    }

    public void I0(int n3) {
        int n4 = this.h.g();
        for (int i3 = 0; i3 < n4; ++i3) {
            this.h.f(i3).offsetTopAndBottom(n3);
        }
    }

    public void I1(int n3, int n4, Object object) {
        int n5 = this.h.j();
        for (int i3 = 0; i3 < n5; ++i3) {
            int n6;
            View view = this.h.i(i3);
            d0 d02 = RecyclerView.m0(view);
            if (d02 == null || d02.J() || (n6 = d02.c) < n3 || n6 >= n3 + n4) continue;
            d02.b(2);
            d02.a(object);
            ((LayoutParams)view.getLayoutParams()).c = true;
        }
        this.e.R(n3, n4);
    }

    public final void J() {
        z z3;
        this.B1();
        this.O0();
        this.k0.a(6);
        this.g.j();
        this.k0.f = this.o.f();
        this.k0.d = 0;
        if (this.f != null && this.o.d()) {
            z3 = this.f.e;
            if (z3 != null) {
                this.p.g1((Parcelable)z3);
            }
            this.f = null;
        }
        z3 = this.k0;
        z3.h = false;
        this.p.b1(this.e, z3);
        z3 = this.k0;
        z3.g = false;
        boolean bl = z3.k && this.P != null;
        z3.k = bl;
        z3.e = 4;
        this.P0();
        this.E1(false);
    }

    public void J0(int n3, int n4) {
        int n5 = this.h.j();
        for (int i3 = 0; i3 < n5; ++i3) {
            d0 d02 = RecyclerView.m0(this.h.i(i3));
            if (d02 == null || d02.J() || d02.c < n3) continue;
            if (E0) {
                ((Object)d02).toString();
            }
            d02.A(n4, false);
            this.k0.g = true;
        }
        this.e.v(n3, n4);
        this.requestLayout();
    }

    public final void K() {
        this.k0.a(4);
        this.B1();
        this.O0();
        Object object = this.k0;
        ((z)object).e = 1;
        if (((z)object).k) {
            for (int i3 = this.h.g() - 1; i3 >= 0; --i3) {
                d0 d02 = RecyclerView.m0(this.h.f(i3));
                if (d02.J()) continue;
                long l3 = this.i0(d02);
                m.b b3 = this.P.s(this.k0, d02);
                object = this.i.g(l3);
                if (object != null && !((d0)object).J()) {
                    boolean bl = this.i.h((d0)object);
                    boolean bl2 = this.i.h(d02);
                    if (bl && object == d02) {
                        this.i.d(d02, b3);
                        continue;
                    }
                    m.b b4 = this.i.n((d0)object);
                    this.i.d(d02, b3);
                    b3 = this.i.m(d02);
                    if (b4 == null) {
                        this.s0(l3, d02, (d0)object);
                        continue;
                    }
                    this.p((d0)object, d02, b4, b3, bl, bl2);
                    continue;
                }
                this.i.d(d02, b3);
            }
            this.i.o(this.C0);
        }
        this.p.p1(this.e);
        object = this.k0;
        ((z)object).c = ((z)object).f;
        this.G = false;
        this.H = false;
        ((z)object).k = false;
        ((z)object).l = false;
        this.p.h = false;
        object = this.e.b;
        if (object != null) {
            ((ArrayList)object).clear();
        }
        object = this.p;
        if (((p)object).n) {
            ((p)object).m = 0;
            ((p)object).n = false;
            this.e.P();
        }
        this.p.c1(this.k0);
        this.P0();
        this.E1(false);
        this.i.f();
        object = this.s0;
        if (this.D((int)object[0], (int)object[1])) {
            this.O(0, 0);
        }
        this.a1();
        this.l1();
    }

    public void K0(int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8 = this.h.j();
        if (n3 < n4) {
            n7 = -1;
            n6 = n3;
            n5 = n4;
        } else {
            n5 = n3;
            n6 = n4;
            n7 = 1;
        }
        for (int i3 = 0; i3 < n8; ++i3) {
            int n9;
            d0 d02 = RecyclerView.m0(this.h.i(i3));
            if (d02 == null || (n9 = d02.c) < n6 || n9 > n5) continue;
            if (E0) {
                ((Object)d02).toString();
            }
            if (d02.c == n3) {
                d02.A(n4 - n3, false);
            } else {
                d02.A(n7, false);
            }
            this.k0.g = true;
        }
        this.e.w(n3, n4);
        this.requestLayout();
    }

    public boolean L(int n3, int n4, int[] nArray, int[] nArray2, int n5) {
        return this.getScrollingChildHelper().d(n3, n4, nArray, nArray2, n5);
    }

    public void L0(int n3, int n4, boolean bl) {
        int n5 = this.h.j();
        for (int i3 = 0; i3 < n5; ++i3) {
            d0 d02 = RecyclerView.m0(this.h.i(i3));
            if (d02 == null || d02.J()) continue;
            int n6 = d02.c;
            if (n6 >= n3 + n4) {
                if (E0) {
                    ((Object)d02).toString();
                }
                d02.A(-n4, bl);
                this.k0.g = true;
                continue;
            }
            if (n6 < n3) continue;
            if (E0) {
                ((Object)d02).toString();
            }
            d02.i(n3 - 1, -n4, bl);
            this.k0.g = true;
        }
        this.e.x(n3, n4, bl);
        this.requestLayout();
    }

    public final void M(int n3, int n4, int n5, int n6, int[] nArray, int n7, int[] nArray2) {
        this.getScrollingChildHelper().e(n3, n4, n5, n6, nArray, n7, nArray2);
    }

    public void M0(View view) {
    }

    public void N(int n3) {
        Object object = this.p;
        if (object != null) {
            ((p)object).i1(n3);
        }
        this.S0(n3);
        object = this.l0;
        if (object != null) {
            ((t)object).a(this, n3);
        }
        if ((object = this.m0) != null) {
            for (int i3 = object.size() - 1; i3 >= 0; --i3) {
                ((t)this.m0.get(i3)).a(this, n3);
            }
        }
    }

    public void N0(View view) {
    }

    public void O(int n3, int n4) {
        ++this.J;
        int n5 = this.getScrollX();
        int n6 = this.getScrollY();
        this.onScrollChanged(n5, n6, n5 - n3, n6 - n4);
        this.T0(n3, n4);
        Object object = this.l0;
        if (object != null) {
            ((t)object).b(this, n3, n4);
        }
        if ((object = this.m0) != null) {
            for (n6 = object.size() - 1; n6 >= 0; --n6) {
                ((t)this.m0.get(n6)).b(this, n3, n4);
            }
        }
        --this.J;
    }

    public void O0() {
        ++this.I;
    }

    public void P() {
        for (int i3 = this.x0.size() - 1; i3 >= 0; --i3) {
            int n3;
            d0 d02 = (d0)this.x0.get(i3);
            if (d02.a.getParent() != this || d02.J() || (n3 = d02.q) == -1) continue;
            o0.x0.o0(d02.a, n3);
            d02.q = -1;
        }
        this.x0.clear();
    }

    public void P0() {
        this.Q0(true);
    }

    public final boolean Q(MotionEvent motionEvent) {
        s s3 = this.t;
        if (s3 == null) {
            if (motionEvent.getAction() == 0) {
                return false;
            }
            return this.Z(motionEvent);
        }
        s3.b(this, motionEvent);
        int n3 = motionEvent.getAction();
        if (n3 == 3 || n3 == 1) {
            this.t = null;
        }
        return true;
    }

    public void Q0(boolean bl) {
        int n3;
        this.I = n3 = this.I - 1;
        if (n3 < 1) {
            if (D0 && n3 < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("layout or scroll counter cannot go below zero.Some calls are not matching");
                stringBuilder.append(this.V());
                throw new IllegalStateException(stringBuilder.toString());
            }
            this.I = 0;
            if (bl) {
                this.G();
                this.P();
            }
        }
    }

    public void R() {
        EdgeEffect edgeEffect;
        if (this.O != null) {
            return;
        }
        this.O = edgeEffect = this.K.a(this, 3);
        if (this.j) {
            edgeEffect.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        edgeEffect.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public final void R0(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n3) == this.R) {
            int n4;
            n3 = n3 == 0 ? 1 : 0;
            this.R = motionEvent.getPointerId(n3);
            this.V = n4 = (int)(motionEvent.getX(n3) + 0.5f);
            this.T = n4;
            this.W = n3 = (int)(motionEvent.getY(n3) + 0.5f);
            this.U = n3;
        }
    }

    public void S() {
        EdgeEffect edgeEffect;
        if (this.L != null) {
            return;
        }
        this.L = edgeEffect = this.K.a(this, 0);
        if (this.j) {
            edgeEffect.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        edgeEffect.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    public void S0(int n3) {
    }

    public void T() {
        EdgeEffect edgeEffect;
        if (this.N != null) {
            return;
        }
        this.N = edgeEffect = this.K.a(this, 2);
        if (this.j) {
            edgeEffect.setSize(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight());
            return;
        }
        edgeEffect.setSize(this.getMeasuredHeight(), this.getMeasuredWidth());
    }

    public void T0(int n3, int n4) {
    }

    public void U() {
        EdgeEffect edgeEffect;
        if (this.M != null) {
            return;
        }
        this.M = edgeEffect = this.K.a(this, 1);
        if (this.j) {
            edgeEffect.setSize(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom());
            return;
        }
        edgeEffect.setSize(this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public void U0() {
        if (!this.q0 && this.u) {
            o0.x0.Z((View)this, this.y0);
            this.q0 = true;
        }
    }

    public String V() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        stringBuilder.append(super.toString());
        stringBuilder.append(", adapter:");
        stringBuilder.append(this.o);
        stringBuilder.append(", layout:");
        stringBuilder.append(this.p);
        stringBuilder.append(", context:");
        stringBuilder.append(this.getContext());
        return stringBuilder.toString();
    }

    public final boolean V0() {
        return this.P != null && this.p.P1();
    }

    public final void W(z z3) {
        if (this.getScrollState() == 2) {
            OverScroller overScroller = this.h0.e;
            z3.p = overScroller.getFinalX() - overScroller.getCurrX();
            z3.q = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        z3.p = 0;
        z3.q = 0;
    }

    public final void W0() {
        if (this.G) {
            this.g.v();
            if (this.H) {
                this.p.W0(this);
            }
        }
        if (this.V0()) {
            this.g.t();
        } else {
            this.g.j();
        }
        boolean bl = this.n0;
        boolean bl2 = true;
        boolean bl3 = bl || this.o0;
        z z3 = this.k0;
        bl = !(!this.x || this.P == null || !(bl = this.G) && !bl3 && !this.p.h || bl && !this.o.j());
        z3.k = bl;
        z3 = this.k0;
        bl = z3.k && bl3 && !this.G && this.V0() ? bl2 : false;
        z3.l = bl;
    }

    public View X(View view) {
        ViewParent viewParent = view.getParent();
        View view2 = view;
        view = viewParent;
        while (view != null && view != this && view instanceof View) {
            view2 = view;
            view = view2.getParent();
        }
        if (view == this) {
            return view2;
        }
        return null;
    }

    public void X0(boolean bl) {
        this.H = bl | this.H;
        this.G = true;
        this.F0();
    }

    public d0 Y(View view) {
        if ((view = this.X(view)) == null) {
            return null;
        }
        return this.l0(view);
    }

    /*
     * Unable to fully structure code
     */
    public final void Y0(float var1_1, float var2_2, float var3_3, float var4_4) {
        var6_5 = true;
        if (var2_2 < 0.0f) {
            this.S();
            androidx.core.widget.f.d(this.L, -var2_2 / (float)this.getWidth(), 1.0f - var3_3 / (float)this.getHeight());
lbl6:
            // 2 sources

            while (true) {
                var5_6 = true;
                break;
            }
        } else {
            if (var2_2 > 0.0f) {
                this.T();
                androidx.core.widget.f.d(this.N, var2_2 / (float)this.getWidth(), var3_3 / (float)this.getHeight());
                ** continue;
            }
            var5_6 = false;
        }
        if (var4_4 < 0.0f) {
            this.U();
            androidx.core.widget.f.d(this.M, -var4_4 / (float)this.getHeight(), var1_1 / (float)this.getWidth());
            var5_6 = var6_5;
        } else if (var4_4 > 0.0f) {
            this.R();
            androidx.core.widget.f.d(this.O, var4_4 / (float)this.getHeight(), 1.0f - var1_1 / (float)this.getWidth());
            var5_6 = var6_5;
        }
        if (!var5_6 && var2_2 == 0.0f && var4_4 == 0.0f) {
            return;
        }
        o0.x0.Y((View)this);
    }

    public final boolean Z(MotionEvent motionEvent) {
        int n3 = motionEvent.getAction();
        int n4 = this.s.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            s s3 = (s)this.s.get(i3);
            if (!s3.a(this, motionEvent) || n3 == 3) continue;
            this.t = s3;
            return true;
        }
        return false;
    }

    public void Z0(d0 d02, m.b b3) {
        d02.F(0, 8192);
        if (this.k0.i && d02.y() && !d02.v() && !d02.J()) {
            long l3 = this.i0(d02);
            this.i.c(l3, d02);
        }
        this.i.e(d02, b3);
    }

    public void a(int n3, int n4) {
        if (n3 < 0) {
            this.S();
            if (this.L.isFinished()) {
                this.L.onAbsorb(-n3);
            }
        } else if (n3 > 0) {
            this.T();
            if (this.N.isFinished()) {
                this.N.onAbsorb(n3);
            }
        }
        if (n4 < 0) {
            this.U();
            if (this.M.isFinished()) {
                this.M.onAbsorb(-n4);
            }
        } else if (n4 > 0) {
            this.R();
            if (this.O.isFinished()) {
                this.O.onAbsorb(n4);
            }
        }
        if (n3 == 0 && n4 == 0) {
            return;
        }
        o0.x0.Y((View)this);
    }

    public final void a0(int[] nArray) {
        int n3 = this.h.g();
        if (n3 == 0) {
            nArray[0] = -1;
            nArray[1] = -1;
            return;
        }
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < n3; ++i3) {
            int n6;
            d0 d02 = RecyclerView.m0(this.h.f(i3));
            if (d02.J()) {
                n6 = n5;
            } else {
                int n7 = d02.m();
                int n8 = n4;
                if (n7 < n4) {
                    n8 = n7;
                }
                n4 = n8;
                n6 = n5;
                if (n7 > n5) {
                    n6 = n7;
                    n4 = n8;
                }
            }
            n5 = n6;
        }
        nArray[0] = n4;
        nArray[1] = n5;
    }

    public final void a1() {
        block12: {
            Object object;
            block13: {
                block14: {
                    if (!this.g0 || this.o == null || !this.hasFocus() || this.getDescendantFocusability() == 393216 || this.getDescendantFocusability() == 131072 && this.isFocused()) break block12;
                    if (this.isFocused()) break block13;
                    object = this.getFocusedChild();
                    if (!M0 || object.getParent() != null && object.hasFocus()) break block14;
                    if (this.h.g() == 0) {
                        this.requestFocus();
                        return;
                    }
                    break block13;
                }
                if (!this.h.n((View)object)) break block12;
            }
            long l3 = this.k0.n;
            Object object2 = null;
            object = l3 != -1L && this.o.j() ? this.e0(this.k0.n) : null;
            if (object != null && !this.h.n(object.a) && object.a.hasFocusable()) {
                object = object.a;
            } else {
                object = object2;
                if (this.h.g() > 0) {
                    object = this.c0();
                }
            }
            if (object != null) {
                int n3 = this.k0.o;
                object2 = object;
                if ((long)n3 != -1L) {
                    View view = object.findViewById(n3);
                    object2 = object;
                    if (view != null) {
                        object2 = object;
                        if (view.isFocusable()) {
                            object2 = view;
                        }
                    }
                }
                object2.requestFocus();
            }
        }
    }

    public void addFocusables(ArrayList arrayList, int n3, int n4) {
        p p3 = this.p;
        if (p3 != null && p3.J0(this, arrayList, n3, n4)) {
            return;
        }
        super.addFocusables(arrayList, n3, n4);
    }

    public final void b1() {
        boolean bl;
        EdgeEffect edgeEffect = this.L;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl = this.L.isFinished();
        } else {
            bl = false;
        }
        edgeEffect = this.M;
        boolean bl2 = bl;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl2 = bl | this.M.isFinished();
        }
        edgeEffect = this.N;
        bl = bl2;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl = bl2 | this.N.isFinished();
        }
        edgeEffect = this.O;
        bl2 = bl;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            bl2 = bl | this.O.isFinished();
        }
        if (bl2) {
            o0.x0.Y((View)this);
        }
    }

    public final View c0() {
        Object object = this.k0;
        int n3 = ((z)object).m;
        if (n3 == -1) {
            n3 = 0;
        }
        int n4 = ((z)object).b();
        for (int i3 = n3; i3 < n4 && (object = this.d0(i3)) != null; ++i3) {
            if (!((d0)object).a.hasFocusable()) continue;
            return ((d0)object).a;
        }
        for (n3 = Math.min(n4, n3) - 1; n3 >= 0; --n3) {
            object = this.d0(n3);
            if (object == null) {
                return null;
            }
            if (!((d0)object).a.hasFocusable()) continue;
            return ((d0)object).a;
        }
        return null;
    }

    public final int c1(int n3, float f3) {
        float f4 = f3 / (float)this.getHeight();
        float f5 = (float)n3 / (float)this.getWidth();
        EdgeEffect edgeEffect = this.L;
        float f6 = 0.0f;
        float f7 = 0.0f;
        f3 = 0.0f;
        if (edgeEffect != null && androidx.core.widget.f.b(edgeEffect) != 0.0f) {
            if (this.canScrollHorizontally(-1)) {
                this.L.onRelease();
            } else {
                f3 = -androidx.core.widget.f.d(this.L, -f5, 1.0f - f4);
                if (androidx.core.widget.f.b(this.L) == 0.0f) {
                    this.L.onRelease();
                }
            }
            this.invalidate();
        } else {
            edgeEffect = this.N;
            f3 = f7;
            if (edgeEffect != null) {
                f3 = f7;
                if (androidx.core.widget.f.b(edgeEffect) != 0.0f) {
                    if (this.canScrollHorizontally(1)) {
                        this.N.onRelease();
                        f3 = f6;
                    } else {
                        f3 = androidx.core.widget.f.d(this.N, f5, f4);
                        if (androidx.core.widget.f.b(this.N) == 0.0f) {
                            this.N.onRelease();
                        }
                    }
                    this.invalidate();
                }
            }
        }
        return Math.round(f3 * (float)this.getWidth());
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && this.p.r((LayoutParams)layoutParams);
    }

    public int computeHorizontalScrollExtent() {
        p p3 = this.p;
        if (p3 == null) {
            return 0;
        }
        if (p3.p()) {
            return this.p.v(this.k0);
        }
        return 0;
    }

    public int computeHorizontalScrollOffset() {
        p p3 = this.p;
        if (p3 == null) {
            return 0;
        }
        if (p3.p()) {
            return this.p.w(this.k0);
        }
        return 0;
    }

    public int computeHorizontalScrollRange() {
        p p3 = this.p;
        if (p3 == null) {
            return 0;
        }
        if (p3.p()) {
            return this.p.x(this.k0);
        }
        return 0;
    }

    public int computeVerticalScrollExtent() {
        p p3 = this.p;
        if (p3 == null) {
            return 0;
        }
        if (p3.q()) {
            return this.p.y(this.k0);
        }
        return 0;
    }

    public int computeVerticalScrollOffset() {
        p p3 = this.p;
        if (p3 == null) {
            return 0;
        }
        if (p3.q()) {
            return this.p.z(this.k0);
        }
        return 0;
    }

    public int computeVerticalScrollRange() {
        p p3 = this.p;
        if (p3 == null) {
            return 0;
        }
        if (p3.q()) {
            return this.p.A(this.k0);
        }
        return 0;
    }

    public d0 d0(int n3) {
        boolean bl = this.G;
        d0 d02 = null;
        if (bl) {
            return null;
        }
        int n4 = this.h.j();
        for (int i3 = 0; i3 < n4; ++i3) {
            d0 d03 = RecyclerView.m0(this.h.i(i3));
            d0 d04 = d02;
            if (d03 != null) {
                d04 = d02;
                if (!d03.v()) {
                    d04 = d02;
                    if (this.h0(d03) == n3) {
                        if (this.h.n(d03.a)) {
                            d04 = d03;
                        } else {
                            return d03;
                        }
                    }
                }
            }
            d02 = d04;
        }
        return d02;
    }

    public boolean dispatchNestedFling(float f3, float f4, boolean bl) {
        return this.getScrollingChildHelper().a(f3, f4, bl);
    }

    public boolean dispatchNestedPreFling(float f3, float f4) {
        return this.getScrollingChildHelper().b(f3, f4);
    }

    public boolean dispatchNestedPreScroll(int n3, int n4, int[] nArray, int[] nArray2) {
        return this.getScrollingChildHelper().c(n3, n4, nArray, nArray2);
    }

    public boolean dispatchNestedScroll(int n3, int n4, int n5, int n6, int[] nArray) {
        return this.getScrollingChildHelper().f(n3, n4, n5, n6, nArray);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public void dispatchRestoreInstanceState(SparseArray sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    public void dispatchSaveInstanceState(SparseArray sparseArray) {
        this.dispatchFreezeSelfOnly(sparseArray);
    }

    public void draw(Canvas canvas) {
        int n3;
        int n4;
        super.draw(canvas);
        int n5 = this.r.size();
        int n6 = 0;
        for (n4 = 0; n4 < n5; ++n4) {
            ((o)this.r.get(n4)).i(canvas, this, this.k0);
        }
        EdgeEffect edgeEffect = this.L;
        int n7 = 1;
        if (edgeEffect != null && !edgeEffect.isFinished()) {
            n3 = canvas.save();
            n4 = this.j ? this.getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float)(-this.getHeight() + n4), 0.0f);
            edgeEffect = this.L;
            n5 = edgeEffect != null && edgeEffect.draw(canvas) ? 1 : 0;
            canvas.restoreToCount(n3);
        } else {
            n5 = 0;
        }
        edgeEffect = this.M;
        n4 = n5;
        if (edgeEffect != null) {
            n4 = n5;
            if (!edgeEffect.isFinished()) {
                n3 = canvas.save();
                if (this.j) {
                    canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
                }
                n4 = (edgeEffect = this.M) != null && edgeEffect.draw(canvas) ? 1 : 0;
                n4 = n5 | n4;
                canvas.restoreToCount(n3);
            }
        }
        edgeEffect = this.N;
        n5 = n4;
        if (edgeEffect != null) {
            n5 = n4;
            if (!edgeEffect.isFinished()) {
                n3 = canvas.save();
                int n8 = this.getWidth();
                n5 = this.j ? this.getPaddingTop() : 0;
                canvas.rotate(90.0f);
                canvas.translate((float)n5, (float)(-n8));
                edgeEffect = this.N;
                n5 = edgeEffect != null && edgeEffect.draw(canvas) ? 1 : 0;
                n5 = n4 | n5;
                canvas.restoreToCount(n3);
            }
        }
        edgeEffect = this.O;
        n4 = n5;
        if (edgeEffect != null) {
            n4 = n5;
            if (!edgeEffect.isFinished()) {
                n3 = canvas.save();
                canvas.rotate(180.0f);
                if (this.j) {
                    canvas.translate((float)(-this.getWidth() + this.getPaddingRight()), (float)(-this.getHeight() + this.getPaddingBottom()));
                } else {
                    canvas.translate((float)(-this.getWidth()), (float)(-this.getHeight()));
                }
                edgeEffect = this.O;
                n4 = n6;
                if (edgeEffect != null) {
                    n4 = n6;
                    if (edgeEffect.draw(canvas)) {
                        n4 = 1;
                    }
                }
                n4 = n5 | n4;
                canvas.restoreToCount(n3);
            }
        }
        if (n4 == 0 && this.P != null && this.r.size() > 0 && this.P.p()) {
            n4 = n7;
        }
        if (n4 != 0) {
            o0.x0.Y((View)this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long l3) {
        return super.drawChild(canvas, view, l3);
    }

    public d0 e0(long l3) {
        h h3 = this.o;
        d0 d02 = null;
        d0 d03 = null;
        d0 d04 = d02;
        if (h3 != null) {
            if (!h3.j()) {
                d04 = d02;
            } else {
                int n3 = this.h.j();
                int n4 = 0;
                d02 = d03;
                while (true) {
                    d04 = d02;
                    if (n4 >= n3) break;
                    d03 = RecyclerView.m0(this.h.i(n4));
                    d04 = d02;
                    if (d03 != null) {
                        d04 = d02;
                        if (!d03.v()) {
                            d04 = d02;
                            if (d03.k() == l3) {
                                if (this.h.n(d03.a)) {
                                    d04 = d03;
                                } else {
                                    return d03;
                                }
                            }
                        }
                    }
                    ++n4;
                    d02 = d04;
                }
            }
        }
        return d04;
    }

    public void e1() {
        Object object = this.P;
        if (object != null) {
            ((m)object).k();
        }
        if ((object = this.p) != null) {
            ((p)object).o1(this.e);
            this.p.p1(this.e);
        }
        this.e.c();
    }

    /*
     * Unable to fully structure code
     */
    public d0 f0(int var1_1, boolean var2_2) {
        var4_3 = this.h.j();
        var5_4 = null;
        for (var3_5 = 0; var3_5 < var4_3; ++var3_5) {
            block5: {
                block6: {
                    var7_7 = RecyclerView.m0(this.h.i(var3_5));
                    var6_6 = var5_4;
                    if (var7_7 == null) break block5;
                    var6_6 = var5_4;
                    if (var7_7.v()) break block5;
                    if (!var2_2) break block6;
                    if (var7_7.c == var1_1) ** GOTO lbl-1000
                    var6_6 = var5_4;
                    break block5;
                }
                if (var7_7.m() != var1_1) {
                    var6_6 = var5_4;
                } else if (this.h.n(var7_7.a)) {
                    var6_6 = var7_7;
                } else {
                    return var7_7;
                }
            }
            var5_4 = var6_6;
        }
        return var5_4;
    }

    public boolean f1(View view) {
        this.B1();
        boolean bl = this.h.r(view);
        if (bl) {
            d0 d02 = RecyclerView.m0(view);
            this.e.O(d02);
            this.e.H(d02);
            if (E0) {
                Objects.toString(view);
                this.toString();
            }
        }
        this.E1(bl ^ true);
        return bl;
    }

    public View focusSearch(View view, int n3) {
        Object object = this.p.U0(view, n3);
        if (object != null) {
            return object;
        }
        object = this.o;
        int n4 = 1;
        int n5 = object != null && this.p != null && !this.B0() && !this.A ? 1 : 0;
        object = FocusFinder.getInstance();
        if (n5 != 0 && (n3 == 2 || n3 == 1)) {
            int n6;
            int n7;
            if (this.p.q()) {
                n7 = n3 == 2 ? 130 : 33;
                n6 = object.findNextFocus((ViewGroup)this, view, n7) == null ? 1 : 0;
                n5 = n6;
                if (L0) {
                    n3 = n7;
                    n5 = n6;
                }
            } else {
                n5 = 0;
            }
            n6 = n5;
            n7 = n3;
            if (n5 == 0) {
                n6 = n5;
                n7 = n3;
                if (this.p.p()) {
                    n5 = this.p.d0() == 1 ? 1 : 0;
                    n7 = n3 == 2 ? 1 : 0;
                    n5 = (n5 ^ n7) != 0 ? 66 : 17;
                    n7 = object.findNextFocus((ViewGroup)this, view, n5) == null ? n4 : 0;
                    if (L0) {
                        n3 = n5;
                    }
                    n6 = n7;
                    n7 = n3;
                }
            }
            if (n6 != 0) {
                this.A();
                if (this.X(view) == null) {
                    return null;
                }
                this.B1();
                this.p.N0(view, n7, this.e, this.k0);
                this.E1(false);
            }
            object = object.findNextFocus((ViewGroup)this, view, n7);
            n3 = n7;
        } else if ((object = object.findNextFocus((ViewGroup)this, view, n3)) == null && n5 != 0) {
            this.A();
            if (this.X(view) == null) {
                return null;
            }
            this.B1();
            object = this.p.N0(view, n3, this.e, this.k0);
            this.E1(false);
        }
        if (object != null && !object.hasFocusable()) {
            if (this.getFocusedChild() == null) {
                return super.focusSearch(view, n3);
            }
            this.k1((View)object, null);
            return view;
        }
        if (this.C0(view, (View)object, n3)) {
            return object;
        }
        return super.focusSearch(view, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean g0(int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        boolean bl;
        int n9;
        Object object;
        block36: {
            block35: {
                block34: {
                    block30: {
                        block32: {
                            block33: {
                                block31: {
                                    block29: {
                                        block25: {
                                            block27: {
                                                block28: {
                                                    block26: {
                                                        block24: {
                                                            block23: {
                                                                block22: {
                                                                    block21: {
                                                                        object = this.p;
                                                                        if (object == null) {
                                                                            Log.e((String)"RecyclerView", (String)"Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                                                                            return false;
                                                                        }
                                                                        if (this.A) {
                                                                            return false;
                                                                        }
                                                                        n9 = ((p)object).p();
                                                                        bl = this.p.q();
                                                                        if (n9 == 0) break block21;
                                                                        n8 = n3;
                                                                        if (Math.abs(n3) >= this.c0) break block22;
                                                                    }
                                                                    n8 = 0;
                                                                }
                                                                if (!bl) break block23;
                                                                n3 = n4;
                                                                if (Math.abs(n4) >= this.c0) break block24;
                                                            }
                                                            n3 = 0;
                                                        }
                                                        if (n8 == 0 && n3 == 0) {
                                                            return false;
                                                        }
                                                        if (n8 == 0) break block25;
                                                        object = this.L;
                                                        if (object == null || androidx.core.widget.f.b((EdgeEffect)object) == 0.0f) break block26;
                                                        object = this.L;
                                                        n7 = -n8;
                                                        n4 = n8;
                                                        if (!this.u1((EdgeEffect)object, n7, this.getWidth())) break block27;
                                                        this.L.onAbsorb(n7);
                                                        break block28;
                                                    }
                                                    object = this.N;
                                                    if (object == null || androidx.core.widget.f.b((EdgeEffect)object) == 0.0f) break block25;
                                                    n4 = n8;
                                                    if (!this.u1(this.N, n8, this.getWidth())) break block27;
                                                    this.N.onAbsorb(n8);
                                                }
                                                n4 = 0;
                                            }
                                            n7 = 0;
                                            break block29;
                                        }
                                        n4 = 0;
                                        n7 = n8;
                                    }
                                    if (n3 == 0) break block30;
                                    object = this.M;
                                    if (object == null || androidx.core.widget.f.b((EdgeEffect)object) == 0.0f) break block31;
                                    object = this.M;
                                    n6 = -n3;
                                    n8 = n3;
                                    if (!this.u1((EdgeEffect)object, n6, this.getHeight())) break block32;
                                    this.M.onAbsorb(n6);
                                    break block33;
                                }
                                object = this.O;
                                if (object == null || androidx.core.widget.f.b((EdgeEffect)object) == 0.0f) break block30;
                                n8 = n3;
                                if (!this.u1(this.O, n3, this.getHeight())) break block32;
                                this.O.onAbsorb(n3);
                            }
                            n8 = 0;
                        }
                        n6 = 0;
                        n3 = n8;
                        break block34;
                    }
                    n6 = n3;
                    n3 = 0;
                }
                if (n4 != 0) break block35;
                n5 = n4;
                n8 = n3;
                if (n3 == 0) break block36;
            }
            n8 = this.d0;
            n5 = Math.max(-n8, Math.min(n4, n8));
            n4 = this.d0;
            n8 = Math.max(-n4, Math.min(n3, n4));
            this.h0.b(n5, n8);
        }
        if (n7 == 0 && n6 == 0) {
            if (n5 != 0) return true;
            if (n8 == 0) return false;
            return true;
        }
        float f3 = n7;
        float f4 = n6;
        if (this.dispatchNestedPreFling(f3, f4)) return false;
        boolean bl2 = n9 != 0 || bl;
        this.dispatchNestedFling(f3, f4, bl2);
        object = this.b0;
        if (object != null && ((r)object).a(n7, n6)) {
            return true;
        }
        if (!bl2) return false;
        n3 = n9;
        if (bl) {
            n3 = n9 | 2;
        }
        this.C1(n3, 1);
        n3 = this.d0;
        n3 = Math.max(-n3, Math.min(n7, n3));
        n4 = this.d0;
        n4 = Math.max(-n4, Math.min(n6, n4));
        this.h0.b(n3, n4);
        return true;
    }

    public void g1(o o3) {
        p p3 = this.p;
        if (p3 != null) {
            p3.l("Cannot remove item decoration during a scroll  or layout");
        }
        this.r.remove(o3);
        if (this.r.isEmpty()) {
            boolean bl = this.getOverScrollMode() == 2;
            this.setWillNotDraw(bl);
        }
        this.E0();
        this.requestLayout();
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        Object object = this.p;
        if (object != null) {
            return ((p)object).I();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RecyclerView has no LayoutManager");
        ((StringBuilder)object).append(this.V());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet object) {
        p p3 = this.p;
        if (p3 != null) {
            return p3.J(this.getContext(), (AttributeSet)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RecyclerView has no LayoutManager");
        ((StringBuilder)object).append(this.V());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams object) {
        p p3 = this.p;
        if (p3 != null) {
            return p3.K((ViewGroup.LayoutParams)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("RecyclerView has no LayoutManager");
        ((StringBuilder)object).append(this.V());
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    public h getAdapter() {
        return this.o;
    }

    public int getBaseline() {
        p p3 = this.p;
        if (p3 != null) {
            return p3.L();
        }
        return super.getBaseline();
    }

    public int getChildDrawingOrder(int n3, int n4) {
        return super.getChildDrawingOrder(n3, n4);
    }

    public boolean getClipToPadding() {
        return this.j;
    }

    public androidx.recyclerview.widget.k getCompatAccessibilityDelegate() {
        return this.r0;
    }

    public l getEdgeEffectFactory() {
        return this.K;
    }

    public m getItemAnimator() {
        return this.P;
    }

    public int getItemDecorationCount() {
        return this.r.size();
    }

    public p getLayoutManager() {
        return this.p;
    }

    public int getMaxFlingVelocity() {
        return this.d0;
    }

    public int getMinFlingVelocity() {
        return this.c0;
    }

    public long getNanoTime() {
        if (K0) {
            return System.nanoTime();
        }
        return 0L;
    }

    public r getOnFlingListener() {
        return this.b0;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.g0;
    }

    public u getRecycledViewPool() {
        return this.e.i();
    }

    public int getScrollState() {
        return this.Q;
    }

    public int h0(d0 d02) {
        if (!d02.p(524) && d02.s()) {
            return this.g.e(d02.c);
        }
        return -1;
    }

    public void h1(s s3) {
        this.s.remove(s3);
        if (this.t == s3) {
            this.t = null;
        }
    }

    public boolean hasNestedScrollingParent() {
        return this.getScrollingChildHelper().j();
    }

    public final void i(d0 d02) {
        View view = d02.a;
        boolean bl = view.getParent() == this;
        this.e.O(this.l0(view));
        if (d02.x()) {
            this.h.c(view, -1, view.getLayoutParams(), true);
            return;
        }
        if (!bl) {
            this.h.b(view, true);
            return;
        }
        this.h.k(view);
    }

    public long i0(d0 d02) {
        if (this.o.j()) {
            return d02.k();
        }
        return d02.c;
    }

    public void i1(t t3) {
        List list = this.m0;
        if (list != null) {
            list.remove(t3);
        }
    }

    public boolean isAttachedToWindow() {
        return this.u;
    }

    public final boolean isLayoutSuppressed() {
        return this.A;
    }

    public boolean isNestedScrollingEnabled() {
        return this.getScrollingChildHelper().l();
    }

    public void j(o o3) {
        this.k(o3, -1);
    }

    public int j0(View object) {
        if ((object = RecyclerView.m0((View)object)) != null) {
            return ((d0)object).j();
        }
        return -1;
    }

    public void j1() {
        int n3 = this.h.g();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.h.f(i3);
            d0 d02 = this.l0(view);
            if (d02 == null || (d02 = d02.i) == null) continue;
            d02 = d02.a;
            int n4 = view.getLeft();
            int n5 = view.getTop();
            if (n4 == d02.getLeft() && n5 == d02.getTop()) continue;
            d02.layout(n4, n5, d02.getWidth() + n4, d02.getHeight() + n5);
        }
    }

    public void k(o o3, int n3) {
        p p3 = this.p;
        if (p3 != null) {
            p3.l("Cannot add item decoration during a scroll  or layout");
        }
        if (this.r.isEmpty()) {
            this.setWillNotDraw(false);
        }
        if (n3 < 0) {
            this.r.add(o3);
        } else {
            this.r.add(n3, o3);
        }
        this.E0();
        this.requestLayout();
    }

    public int k0(View object) {
        if ((object = RecyclerView.m0((View)object)) != null) {
            return ((d0)object).m();
        }
        return -1;
    }

    public final void k1(View view, View view2) {
        Rect rect;
        Object object = view2 != null ? view2 : view;
        this.l.set(0, 0, object.getWidth(), object.getHeight());
        object = object.getLayoutParams();
        if (object instanceof LayoutParams) {
            object = (LayoutParams)((Object)object);
            if (!((LayoutParams)((Object)object)).c) {
                object = ((LayoutParams)((Object)object)).b;
                rect = this.l;
                rect.left -= ((Rect)object).left;
                rect.right += ((Rect)object).right;
                rect.top -= ((Rect)object).top;
                rect.bottom += ((Rect)object).bottom;
            }
        }
        if (view2 != null) {
            this.offsetDescendantRectToMyCoords(view2, this.l);
            this.offsetRectIntoDescendantCoords(view, this.l);
        }
        object = this.p;
        rect = this.l;
        boolean bl = this.x;
        boolean bl2 = view2 == null;
        ((p)object).w1(this, view, rect, bl ^ true, bl2);
    }

    public void l(q q3) {
        if (this.F == null) {
            this.F = new ArrayList();
        }
        this.F.add(q3);
    }

    public d0 l0(View view) {
        Object object = view.getParent();
        if (object != null && object != this) {
            object = new StringBuilder();
            ((StringBuilder)object).append("View ");
            ((StringBuilder)object).append(view);
            ((StringBuilder)object).append(" is not a direct child of ");
            ((StringBuilder)object).append(this);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return RecyclerView.m0(view);
    }

    public final void l1() {
        z z3 = this.k0;
        z3.n = -1L;
        z3.m = -1;
        z3.o = -1;
    }

    public void m(s s3) {
        this.s.add(s3);
    }

    public final void m1() {
        VelocityTracker velocityTracker = this.S;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        this.F1(0);
        this.b1();
    }

    public void n(t t3) {
        if (this.m0 == null) {
            this.m0 = new ArrayList();
        }
        this.m0.add(t3);
    }

    public final void n1() {
        boolean bl = this.g0;
        Object object = null;
        Object object2 = bl && this.hasFocus() && this.o != null ? this.getFocusedChild() : null;
        if ((object2 = object2 == null ? object : this.Y((View)object2)) == null) {
            this.l1();
            return;
        }
        object = this.k0;
        long l3 = this.o.j() ? ((d0)object2).k() : -1L;
        ((z)object).n = l3;
        object = this.k0;
        int n3 = this.G ? -1 : (((d0)object2).v() ? ((d0)object2).d : ((d0)object2).j());
        ((z)object).m = n3;
        this.k0.o = this.o0(((d0)object2).a);
    }

    public void o(d0 d02, m.b b3, m.b b4) {
        d02.G(false);
        if (this.P.a(d02, b3, b4)) {
            this.U0();
        }
    }

    public final int o0(View view) {
        int n3 = view.getId();
        while (!view.isFocused() && view instanceof ViewGroup && view.hasFocus()) {
            View view2;
            view = view2 = ((ViewGroup)view).getFocusedChild();
            if (view2.getId() == -1) continue;
            n3 = view2.getId();
            view = view2;
        }
        return n3;
    }

    public void o1() {
        int n3 = this.h.j();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = RecyclerView.m0(this.h.i(i3));
            if (D0 && ((d0)object).c == -1 && !((d0)object).v()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("view holder cannot have position -1 unless it is removed");
                ((StringBuilder)object).append(this.V());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            if (((d0)object).J()) continue;
            ((d0)object).E();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.I = 0;
        boolean bl = true;
        this.u = true;
        if (!this.x || this.isLayoutRequested()) {
            bl = false;
        }
        this.x = bl;
        this.e.z();
        Object object = this.p;
        if (object != null) {
            ((p)object).E(this);
        }
        this.q0 = false;
        if (K0) {
            e e3;
            object = androidx.recyclerview.widget.e.g;
            this.i0 = e3 = (e)((ThreadLocal)object).get();
            if (e3 == null) {
                float f3;
                this.i0 = new e();
                e3 = o0.x0.s((View)this);
                if (this.isInEditMode() || e3 == null || !((f3 = e3.getRefreshRate()) >= 30.0f)) {
                    f3 = 60.0f;
                }
                e3 = this.i0;
                e3.e = (long)(1.0E9f / f3);
                ((ThreadLocal)object).set(e3);
            }
            this.i0.a(this);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object object = this.P;
        if (object != null) {
            ((m)object).k();
        }
        this.G1();
        this.u = false;
        object = this.p;
        if (object != null) {
            ((p)object).F(this, this.e);
        }
        this.x0.clear();
        this.removeCallbacks(this.y0);
        this.i.j();
        this.e.A();
        u0.a.b(this);
        if (K0 && (object = this.i0) != null) {
            ((e)object).j(this);
            this.i0 = null;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n3 = this.r.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((o)this.r.get(i3)).g(canvas, this, this.k0);
        }
    }

    /*
     * Unable to fully structure code
     */
    public boolean onGenericMotionEvent(MotionEvent var1_1) {
        block8: {
            block7: {
                block9: {
                    block10: {
                        if (this.p == null) {
                            return false;
                        }
                        if (this.A) {
                            return false;
                        }
                        if (var1_1.getAction() != 8) break block8;
                        if ((var1_1.getSource() & 2) == 0) break block9;
                        var2_2 = this.p.q() != false ? -var1_1.getAxisValue(9) : 0.0f;
                        var3_3 = var2_2;
                        if (!this.p.p()) break block10;
                        var3_3 = var1_1.getAxisValue(10);
                        break block7;
                    }
lbl13:
                    // 2 sources

                    while (true) {
                        var4_4 = 0.0f;
                        var2_2 = var3_3;
                        var3_3 = var4_4;
                        break block7;
                        break;
                    }
                }
                if ((var1_1.getSource() & 0x400000) == 0) ** GOTO lbl-1000
                var3_3 = var1_1.getAxisValue(26);
                if (this.p.q()) {
                    var3_3 = -var3_3;
                    ** continue;
                }
                if (this.p.p()) {
                    var2_2 = 0.0f;
                } else lbl-1000:
                // 2 sources

                {
                    var2_2 = 0.0f;
                    var3_3 = 0.0f;
                }
            }
            if (var2_2 != 0.0f || var3_3 != 0.0f) {
                this.G0((int)(var3_3 * this.e0), (int)(var2_2 * this.f0), var1_1, 1);
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent object) {
        if (this.A) {
            return false;
        }
        this.t = null;
        if (this.Z((MotionEvent)object)) {
            this.t();
            return true;
        }
        p p3 = this.p;
        if (p3 == null) {
            return false;
        }
        int n3 = p3.p();
        boolean bl = this.p.q();
        if (this.S == null) {
            this.S = VelocityTracker.obtain();
        }
        this.S.addMovement(object);
        int n4 = object.getActionMasked();
        int n5 = object.getActionIndex();
        if (n4 != 0) {
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 5) {
                            if (n4 == 6) {
                                this.R0((MotionEvent)object);
                            }
                        } else {
                            this.R = object.getPointerId(n5);
                            this.V = n3 = (int)(object.getX(n5) + 0.5f);
                            this.T = n3;
                            this.W = n5 = (int)(object.getY(n5) + 0.5f);
                            this.U = n5;
                        }
                    } else {
                        this.t();
                    }
                } else {
                    n4 = object.findPointerIndex(this.R);
                    if (n4 < 0) {
                        object = new StringBuilder();
                        object.append("Error processing scroll; pointer index for id ");
                        object.append(this.R);
                        object.append(" not found. Did any MotionEvents get skipped?");
                        Log.e((String)"RecyclerView", (String)object.toString());
                        return false;
                    }
                    n5 = (int)(object.getX(n4) + 0.5f);
                    n4 = (int)(object.getY(n4) + 0.5f);
                    if (this.Q != 1) {
                        int n6 = this.T;
                        int n7 = this.U;
                        if (n3 != 0 && Math.abs(n5 - n6) > this.a0) {
                            this.V = n5;
                            n5 = 1;
                        } else {
                            n5 = 0;
                        }
                        n3 = n5;
                        if (bl) {
                            n3 = n5;
                            if (Math.abs(n4 - n7) > this.a0) {
                                this.W = n4;
                                n3 = 1;
                            }
                        }
                        if (n3 != 0) {
                            this.setScrollState(1);
                        }
                    }
                }
            } else {
                this.S.clear();
                this.F1(0);
            }
        } else {
            if (this.B) {
                this.B = false;
            }
            this.R = object.getPointerId(0);
            this.V = n5 = (int)(object.getX() + 0.5f);
            this.T = n5;
            this.W = n5 = (int)(object.getY() + 0.5f);
            this.U = n5;
            if (this.D1((MotionEvent)object) || this.Q == 2) {
                this.getParent().requestDisallowInterceptTouchEvent(true);
                this.setScrollState(1);
                this.F1(1);
            }
            object = this.v0;
            object[1] = (MotionEvent)false;
            object[0] = (MotionEvent)false;
            n5 = n3;
            if (bl) {
                n5 = n3 | 2;
            }
            this.C1(n5, 0);
        }
        return this.Q == 1;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        k0.e.a("RV OnLayout");
        this.H();
        k0.e.b();
        this.x = true;
    }

    public void onMeasure(int n3, int n4) {
        Object object = this.p;
        if (object == null) {
            this.C(n3, n4);
            return;
        }
        boolean bl = ((p)object).w0();
        boolean bl2 = false;
        if (bl) {
            int n5 = View.MeasureSpec.getMode((int)n3);
            int n6 = View.MeasureSpec.getMode((int)n4);
            this.p.d1(this.e, this.k0, n3, n4);
            bl = bl2;
            if (n5 == 0x40000000) {
                bl = bl2;
                if (n6 == 0x40000000) {
                    bl = true;
                }
            }
            this.z0 = bl;
            if (!bl && this.o != null) {
                if (this.k0.e == 1) {
                    this.I();
                }
                this.p.E1(n3, n4);
                this.k0.j = true;
                this.J();
                this.p.H1(n3, n4);
                if (this.p.K1()) {
                    this.p.E1(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0x40000000));
                    this.k0.j = true;
                    this.J();
                    this.p.H1(n3, n4);
                }
                this.A0 = this.getMeasuredWidth();
                this.B0 = this.getMeasuredHeight();
            }
            return;
        }
        if (this.v) {
            this.p.d1(this.e, this.k0, n3, n4);
            return;
        }
        if (this.D) {
            this.B1();
            this.O0();
            this.W0();
            this.P0();
            object = this.k0;
            if (((z)object).l) {
                ((z)object).h = true;
            } else {
                this.g.j();
                this.k0.h = false;
            }
            this.D = false;
            this.E1(false);
        } else if (this.k0.l) {
            this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredHeight());
            return;
        }
        object = this.o;
        this.k0.f = object != null ? ((h)object).f() : 0;
        this.B1();
        this.p.d1(this.e, this.k0, n3, n4);
        this.E1(false);
        this.k0.h = false;
    }

    public boolean onRequestFocusInDescendants(int n3, Rect rect) {
        if (this.B0()) {
            return false;
        }
        return super.onRequestFocusInDescendants(n3, rect);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        this.f = parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Object object = this.f;
        if (object != null) {
            savedState.p((SavedState)object);
            return savedState;
        }
        object = this.p;
        if (object != null) {
            savedState.e = ((p)object).h1();
            return savedState;
        }
        savedState.e = null;
        return savedState;
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        if (n3 == n5 && n4 == n6) {
            return;
        }
        this.z0();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        block21: {
            block27: {
                block25: {
                    block22: {
                        block23: {
                            block26: {
                                block24: {
                                    if (this.A || this.B) break block21;
                                    if (this.Q((MotionEvent)var1_1)) {
                                        this.t();
                                        return true;
                                    }
                                    var14_2 = this.p;
                                    if (var14_2 == null) {
                                        return false;
                                    }
                                    var10_3 = var14_2.p();
                                    var13_4 = this.p.q();
                                    if (this.S == null) {
                                        this.S = VelocityTracker.obtain();
                                    }
                                    var5_5 = var1_1.getActionMasked();
                                    var4_6 = var1_1.getActionIndex();
                                    if (var5_5 == 0) {
                                        var14_2 = this.v0;
                                        var14_2[1] = false;
                                        var14_2[0] = false;
                                    }
                                    var14_2 = MotionEvent.obtain((MotionEvent)var1_1);
                                    var15_7 = this.v0;
                                    var14_2.offsetLocation((float)var15_7[0], (float)var15_7[1]);
                                    if (var5_5 == 0) break block22;
                                    if (var5_5 == 1) break block23;
                                    if (var5_5 == 2) break block24;
                                    if (var5_5 != 3) {
                                        if (var5_5 != 5) {
                                            if (var5_5 == 6) {
                                                this.R0((MotionEvent)var1_1);
                                            }
                                        } else {
                                            this.R = var1_1.getPointerId(var4_6);
                                            this.V = var5_5 = (int)(var1_1.getX(var4_6) + 0.5f);
                                            this.T = var5_5;
                                            this.W = var4_6 = (int)(var1_1.getY(var4_6) + 0.5f);
                                            this.U = var4_6;
                                        }
                                    } else {
                                        this.t();
                                    }
                                    break block25;
                                }
                                var4_6 = var1_1.findPointerIndex(this.R);
                                if (var4_6 < 0) {
                                    var1_1 = new StringBuilder();
                                    var1_1.append("Error processing scroll; pointer index for id ");
                                    var1_1.append(this.R);
                                    var1_1.append(" not found. Did any MotionEvents get skipped?");
                                    Log.e((String)"RecyclerView", (String)var1_1.toString());
                                    return false;
                                }
                                var11_8 = (int)(var1_1.getX(var4_6) + 0.5f);
                                var12_9 = (int)(var1_1.getY(var4_6) + 0.5f);
                                var4_6 = this.V - var11_8;
                                var7_10 = this.W - var12_9;
                                var9_11 = var4_6;
                                var5_5 = var7_10;
                                if (this.Q == 1) break block26;
                                var6_12 = var4_6;
                                if (var10_3 == 0) ** GOTO lbl-1000
                                var4_6 = var4_6 > 0 ? Math.max(0, var4_6 - this.a0) : Math.min(0, var4_6 + this.a0);
                                var6_12 = var4_6;
                                if (var4_6 != 0) {
                                    var5_5 = 1;
                                } else lbl-1000:
                                // 2 sources

                                {
                                    var5_5 = 0;
                                    var4_6 = var6_12;
                                }
                                var6_12 = var7_10;
                                var8_13 = var5_5;
                                if (var13_4) {
                                    var7_10 = var7_10 > 0 ? Math.max(0, var7_10 - this.a0) : Math.min(0, var7_10 + this.a0);
                                    var6_12 = var7_10;
                                    var8_13 = var5_5;
                                    if (var7_10 != 0) {
                                        var8_13 = 1;
                                        var6_12 = var7_10;
                                    }
                                }
                                var9_11 = var4_6;
                                var5_5 = var6_12;
                                if (var8_13 != 0) {
                                    this.setScrollState(1);
                                    var5_5 = var6_12;
                                    var9_11 = var4_6;
                                }
                            }
                            if (this.Q == 1) {
                                var15_7 = this.w0;
                                var15_7[0] = 0;
                                var15_7[1] = 0;
                                var7_10 = var9_11 - this.c1(var9_11, var1_1.getY());
                                var6_12 = var5_5 - this.d1(var5_5, var1_1.getX());
                                var8_13 = var10_3 != 0 ? var7_10 : 0;
                                var9_11 = var13_4 != false ? var6_12 : 0;
                                var5_5 = var7_10;
                                var4_6 = var6_12;
                                if (this.L(var8_13, var9_11, this.w0, this.u0, 0)) {
                                    var15_7 = this.w0;
                                    var5_5 = var7_10 - var15_7[0];
                                    var4_6 = var6_12 - var15_7[1];
                                    var15_7 = this.v0;
                                    var6_12 = var15_7[0];
                                    var16_14 = this.u0;
                                    var15_7[0] = var6_12 + var16_14[0];
                                    var15_7[1] = var15_7[1] + var16_14[1];
                                    this.getParent().requestDisallowInterceptTouchEvent(true);
                                }
                                var15_7 = this.u0;
                                this.V = var11_8 - var15_7[0];
                                this.W = var12_9 - var15_7[1];
                                var6_12 = var10_3 != 0 ? var5_5 : 0;
                                if (this.p1(var6_12, var7_10 = var13_4 != false ? var4_6 : 0, (MotionEvent)var1_1, 0)) {
                                    this.getParent().requestDisallowInterceptTouchEvent(true);
                                }
                                if ((var1_1 = this.i0) != null && (var5_5 != 0 || var4_6 != 0)) {
                                    var1_1.f(this, var5_5, var4_6);
                                }
                            }
                            break block25;
                        }
                        this.S.addMovement((MotionEvent)var14_2);
                        this.S.computeCurrentVelocity(1000, (float)this.d0);
                        var2_15 = var10_3 != 0 ? -this.S.getXVelocity(this.R) : 0.0f;
                        var3_16 = var13_4 != false ? -this.S.getYVelocity(this.R) : 0.0f;
                        if (var2_15 == 0.0f && var3_16 == 0.0f || !this.g0((int)var2_15, (int)var3_16)) {
                            this.setScrollState(0);
                        }
                        this.m1();
                        break block27;
                    }
                    this.R = var1_1.getPointerId(0);
                    this.V = var4_6 = (int)(var1_1.getX() + 0.5f);
                    this.T = var4_6;
                    this.W = var4_6 = (int)(var1_1.getY() + 0.5f);
                    this.U = var4_6;
                    var4_6 = var10_3;
                    if (var13_4) {
                        var4_6 = var10_3 | 2;
                    }
                    this.C1(var4_6, 0);
                }
                this.S.addMovement((MotionEvent)var14_2);
            }
            var14_2.recycle();
            return true;
        }
        return false;
    }

    public final void p(d0 d02, d0 d03, m.b b3, m.b b4, boolean bl, boolean bl2) {
        d02.G(false);
        if (bl) {
            this.i(d02);
        }
        if (d02 != d03) {
            if (bl2) {
                this.i(d03);
            }
            d02.h = d03;
            this.i(d02);
            this.e.O(d02);
            d03.G(false);
            d03.i = d02;
        }
        if (this.P.b(d02, d03, b3, b4)) {
            this.U0();
        }
    }

    public final String p0(Context object, String string) {
        if (string.charAt(0) == '.') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object.getPackageName());
            stringBuilder.append(string);
            return stringBuilder.toString();
        }
        if (string.contains(".")) {
            return string;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(RecyclerView.class.getPackage().getName());
        ((StringBuilder)object).append('.');
        ((StringBuilder)object).append(string);
        return ((StringBuilder)object).toString();
    }

    public boolean p1(int n3, int n4, MotionEvent motionEvent, int n5) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int[] nArray;
        this.A();
        if (this.o != null) {
            nArray = this.w0;
            nArray[0] = 0;
            nArray[1] = 0;
            this.q1(n3, n4, nArray);
            nArray = this.w0;
            n10 = nArray[0];
            n9 = nArray[1];
            n8 = n3 - n10;
            n7 = n4 - n9;
        } else {
            n6 = 0;
            n7 = n9 = (n10 = 0);
            n8 = n9;
            n9 = n10;
            n10 = n6;
        }
        if (!this.r.isEmpty()) {
            this.invalidate();
        }
        nArray = this.w0;
        nArray[0] = 0;
        nArray[1] = 0;
        this.M(n10, n9, n8, n7, this.u0, n5, nArray);
        nArray = this.w0;
        n6 = nArray[0];
        int n11 = nArray[1];
        n5 = n6 == 0 && n11 == 0 ? 0 : 1;
        int n12 = this.V;
        nArray = this.u0;
        int n13 = nArray[0];
        this.V = n12 - n13;
        int n14 = this.W;
        n12 = nArray[1];
        this.W = n14 - n12;
        nArray = this.v0;
        nArray[0] = nArray[0] + n13;
        nArray[1] = nArray[1] + n12;
        if (this.getOverScrollMode() != 2) {
            if (motionEvent != null && !o0.z.a(motionEvent, 8194)) {
                this.Y0(motionEvent.getX(), n8 - n6, motionEvent.getY(), n7 - n11);
            }
            this.w(n3, n4);
        }
        if (n10 != 0 || n9 != 0) {
            this.O(n10, n9);
        }
        if (!this.awakenScrollBars()) {
            this.invalidate();
        }
        return n5 != 0 || n10 != 0 || n9 != 0;
        {
        }
    }

    public void q(d0 d02, m.b b3, m.b b4) {
        this.i(d02);
        d02.G(false);
        if (this.P.c(d02, b3, b4)) {
            this.U0();
        }
    }

    public Rect q0(View view) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (!layoutParams.c) {
            return layoutParams.b;
        }
        if (this.k0.e() && (layoutParams.b() || layoutParams.d())) {
            return layoutParams.b;
        }
        Rect rect = layoutParams.b;
        rect.set(0, 0, 0, 0);
        int n3 = this.r.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            this.l.set(0, 0, 0, 0);
            ((o)this.r.get(i3)).e(this.l, view, this, this.k0);
            int n4 = rect.left;
            Rect rect2 = this.l;
            rect.left = n4 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        layoutParams.c = false;
        return rect;
    }

    public void q1(int n3, int n4, int[] nArray) {
        this.B1();
        this.O0();
        k0.e.a("RV Scroll");
        this.W(this.k0);
        n3 = n3 != 0 ? this.p.A1(n3, this.e, this.k0) : 0;
        n4 = n4 != 0 ? this.p.C1(n4, this.e, this.k0) : 0;
        k0.e.b();
        this.j1();
        this.P0();
        this.E1(false);
        if (nArray != null) {
            nArray[0] = n3;
            nArray[1] = n4;
        }
    }

    public void r(String charSequence) {
        if (this.B0()) {
            if (charSequence == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Cannot call this method while RecyclerView is computing a layout or scrolling");
                ((StringBuilder)charSequence).append(this.V());
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            throw new IllegalStateException((String)charSequence);
        }
        if (this.J > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append(this.V());
            Log.w((String)"RecyclerView", (String)"Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", (Throwable)new IllegalStateException(((StringBuilder)charSequence).toString()));
        }
    }

    public void r1(int n3) {
        if (this.A) {
            return;
        }
        this.G1();
        p p3 = this.p;
        if (p3 == null) {
            Log.e((String)"RecyclerView", (String)"Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        p3.B1(n3);
        this.awakenScrollBars();
    }

    public void removeDetachedView(View object, boolean bl) {
        Object object2;
        block9: {
            block8: {
                block7: {
                    object2 = RecyclerView.m0((View)object);
                    if (object2 == null) break block7;
                    if (((d0)object2).x()) {
                        ((d0)object2).f();
                    } else if (!((d0)object2).J()) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                        ((StringBuilder)object).append(object2);
                        ((StringBuilder)object).append(this.V());
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    break block8;
                }
                if (D0) break block9;
            }
            object.clearAnimation();
            this.F((View)object);
            super.removeDetachedView((View)object, bl);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("No ViewHolder found for child: ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(this.V());
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.p.f1(this, this.k0, view, view2) && view2 != null) {
            this.k1(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return this.p.v1(this, view, rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        int n3 = this.s.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((s)this.s.get(i3)).c(bl);
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        if (this.y == 0 && !this.A) {
            super.requestLayout();
            return;
        }
        this.z = true;
    }

    public boolean s(d0 d02) {
        m m3 = this.P;
        return m3 == null || m3.g(d02, d02.o());
        {
        }
    }

    public final void s0(long l3, d0 d02, d0 object) {
        Object object2;
        int n3 = this.h.g();
        for (int i3 = 0; i3 < n3; ++i3) {
            object2 = RecyclerView.m0(this.h.f(i3));
            if (object2 == d02 || this.i0((d0)object2) != l3) continue;
            object = this.o;
            if (object != null && ((h)object).j()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(" \n View Holder 2:");
                ((StringBuilder)object).append(d02);
                ((StringBuilder)object).append(this.V());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(" \n View Holder 2:");
            ((StringBuilder)object).append(d02);
            ((StringBuilder)object).append(this.V());
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append(" cannot be found but it is necessary for ");
        ((StringBuilder)object2).append(d02);
        ((StringBuilder)object2).append(this.V());
        Log.e((String)"RecyclerView", (String)((StringBuilder)object2).toString());
    }

    public final void s1(h object, boolean bl, boolean bl2) {
        h h3 = this.o;
        if (h3 != null) {
            h3.y(this.d);
            this.o.r(this);
        }
        if (!bl || bl2) {
            this.e1();
        }
        this.g.v();
        h3 = this.o;
        this.o = object;
        if (object != null) {
            ((h)object).w(this.d);
            ((h)object).n(this);
        }
        if ((object = this.p) != null) {
            ((p)object).I0(h3, this.o);
        }
        this.e.y(h3, this.o, bl);
        this.k0.g = true;
    }

    public void scrollBy(int n3, int n4) {
        boolean bl;
        boolean bl2;
        block7: {
            block6: {
                p p3 = this.p;
                if (p3 == null) {
                    Log.e((String)"RecyclerView", (String)"Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                    return;
                }
                if (this.A) break block6;
                bl2 = p3.p();
                bl = this.p.q();
                if (bl2 || bl) break block7;
            }
            return;
        }
        if (!bl2) {
            n3 = 0;
        }
        if (!bl) {
            n4 = 0;
        }
        this.p1(n3, n4, null, 0);
    }

    public void scrollTo(int n3, int n4) {
        Log.w((String)"RecyclerView", (String)"RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (this.v1(accessibilityEvent)) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(androidx.recyclerview.widget.k k3) {
        this.r0 = k3;
        o0.x0.h0((View)this, k3);
    }

    public void setAdapter(h h3) {
        this.setLayoutFrozen(false);
        this.s1(h3, false, true);
        this.X0(false);
        this.requestLayout();
    }

    public void setChildDrawingOrderCallback(k k3) {
        if (k3 == null) {
            return;
        }
        this.setChildrenDrawingOrderEnabled(false);
    }

    public void setClipToPadding(boolean bl) {
        if (bl != this.j) {
            this.z0();
        }
        this.j = bl;
        super.setClipToPadding(bl);
        if (this.x) {
            this.requestLayout();
        }
    }

    public void setEdgeEffectFactory(l l3) {
        n0.h.g(l3);
        this.K = l3;
        this.z0();
    }

    public void setHasFixedSize(boolean bl) {
        this.v = bl;
    }

    public void setItemAnimator(m m3) {
        m m4 = this.P;
        if (m4 != null) {
            m4.k();
            this.P.v(null);
        }
        this.P = m3;
        if (m3 != null) {
            m3.v(this.p0);
        }
    }

    public void setItemViewCacheSize(int n3) {
        this.e.L(n3);
    }

    @Deprecated
    public void setLayoutFrozen(boolean bl) {
        this.suppressLayout(bl);
    }

    public void setLayoutManager(p p3) {
        Object object;
        if (p3 == this.p) {
            return;
        }
        this.G1();
        if (this.p != null) {
            object = this.P;
            if (object != null) {
                ((m)object).k();
            }
            this.p.o1(this.e);
            this.p.p1(this.e);
            this.e.c();
            if (this.u) {
                this.p.F(this, this.e);
            }
            this.p.I1(null);
            this.p = null;
        } else {
            this.e.c();
        }
        this.h.o();
        this.p = p3;
        if (p3 != null) {
            if (p3.b == null) {
                p3.I1(this);
                if (this.u) {
                    this.p.E(this);
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("LayoutManager ");
                ((StringBuilder)object).append(p3);
                ((StringBuilder)object).append(" is already attached to a RecyclerView:");
                ((StringBuilder)object).append(p3.b.V());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
        this.e.P();
        this.requestLayout();
    }

    @Deprecated
    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (layoutTransition == null) {
            super.setLayoutTransition(null);
            return;
        }
        throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
    }

    public void setNestedScrollingEnabled(boolean bl) {
        this.getScrollingChildHelper().m(bl);
    }

    public void setOnFlingListener(r r3) {
        this.b0 = r3;
    }

    @Deprecated
    public void setOnScrollListener(t t3) {
        this.l0 = t3;
    }

    public void setPreserveFocusAfterLayout(boolean bl) {
        this.g0 = bl;
    }

    public void setRecycledViewPool(u u3) {
        this.e.J(u3);
    }

    @Deprecated
    public void setRecyclerListener(w w3) {
    }

    public void setScrollState(int n3) {
        if (n3 == this.Q) {
            return;
        }
        if (E0) {
            new Exception();
        }
        this.Q = n3;
        if (n3 != 2) {
            this.H1();
        }
        this.N(n3);
    }

    public void setScrollingTouchSlop(int n3) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        if (n3 != 0) {
            if (n3 != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
                stringBuilder.append(n3);
                stringBuilder.append("; using default value");
                Log.w((String)"RecyclerView", (String)stringBuilder.toString());
            } else {
                this.a0 = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
        }
        this.a0 = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(b0 b02) {
        this.e.K(b02);
    }

    public boolean startNestedScroll(int n3) {
        return this.getScrollingChildHelper().o(n3);
    }

    public void stopNestedScroll() {
        this.getScrollingChildHelper().q();
    }

    public final void suppressLayout(boolean bl) {
        if (bl != this.A) {
            this.r("Do not suppressLayout in layout or scroll");
            if (!bl) {
                this.A = false;
                if (this.z && this.p != null && this.o != null) {
                    this.requestLayout();
                }
                this.z = false;
                return;
            }
            long l3 = SystemClock.uptimeMillis();
            this.onTouchEvent(MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0));
            this.A = true;
            this.B = true;
            this.G1();
        }
    }

    public final void t() {
        this.m1();
        this.setScrollState(0);
    }

    public boolean t0() {
        return !this.x || this.G || this.g.p();
        {
        }
    }

    public boolean t1(d0 d02, int n3) {
        if (this.B0()) {
            d02.q = n3;
            this.x0.add(d02);
            return false;
        }
        o0.x0.o0(d02.a, n3);
        return true;
    }

    public final boolean u0() {
        int n3 = this.h.g();
        for (int i3 = 0; i3 < n3; ++i3) {
            d0 d02 = RecyclerView.m0(this.h.f(i3));
            if (d02 == null || d02.J() || !d02.y()) continue;
            return true;
        }
        return false;
    }

    public final boolean u1(EdgeEffect edgeEffect, int n3, int n4) {
        if (n3 > 0) {
            return true;
        }
        float f3 = androidx.core.widget.f.b(edgeEffect);
        float f4 = n4;
        return this.r0(-n3) < f3 * f4;
    }

    public void v() {
        int n3 = this.h.j();
        for (int i3 = 0; i3 < n3; ++i3) {
            d0 d02 = RecyclerView.m0(this.h.i(i3));
            if (d02.J()) continue;
            d02.c();
        }
        this.e.d();
    }

    public void v0() {
        this.g = new a(new a.a(this){
            public final RecyclerView a;
            {
                this.a = recyclerView;
            }

            @Override
            public void a(int n3, int n4) {
                this.a.K0(n3, n4);
                this.a.n0 = true;
            }

            @Override
            public void b(a.b b3) {
                this.i(b3);
            }

            @Override
            public d0 c(int n3) {
                d0 d02 = this.a.f0(n3, true);
                if (d02 == null) {
                    return null;
                }
                if (this.a.h.n(d02.a)) {
                    boolean bl = D0;
                    return null;
                }
                return d02;
            }

            @Override
            public void d(int n3, int n4) {
                this.a.L0(n3, n4, false);
                this.a.n0 = true;
            }

            @Override
            public void e(int n3, int n4) {
                this.a.J0(n3, n4);
                this.a.n0 = true;
            }

            @Override
            public void f(int n3, int n4) {
                this.a.L0(n3, n4, true);
                Object object = this.a;
                ((RecyclerView)object).n0 = true;
                object = ((RecyclerView)object).k0;
                ((z)object).d += n4;
            }

            @Override
            public void g(a.b b3) {
                this.i(b3);
            }

            @Override
            public void h(int n3, int n4, Object object) {
                this.a.I1(n3, n4, object);
                this.a.o0 = true;
            }

            public void i(a.b b3) {
                int n3 = b3.a;
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 4) {
                            if (n3 != 8) {
                                return;
                            }
                            RecyclerView recyclerView = this.a;
                            recyclerView.p.X0(recyclerView, b3.b, b3.d, 1);
                            return;
                        }
                        RecyclerView recyclerView = this.a;
                        recyclerView.p.a1(recyclerView, b3.b, b3.d, b3.c);
                        return;
                    }
                    RecyclerView recyclerView = this.a;
                    recyclerView.p.Y0(recyclerView, b3.b, b3.d);
                    return;
                }
                RecyclerView recyclerView = this.a;
                recyclerView.p.V0(recyclerView, b3.b, b3.d);
            }
        });
    }

    public boolean v1(AccessibilityEvent accessibilityEvent) {
        boolean bl = this.B0();
        int n3 = 0;
        if (bl) {
            int n4 = accessibilityEvent != null ? p0.b.a(accessibilityEvent) : 0;
            if (n4 == 0) {
                n4 = n3;
            }
            this.C |= n4;
            return true;
        }
        return false;
    }

    public void w(int n3, int n4) {
        boolean bl;
        EdgeEffect edgeEffect = this.L;
        if (edgeEffect != null && !edgeEffect.isFinished() && n3 > 0) {
            this.L.onRelease();
            bl = this.L.isFinished();
        } else {
            bl = false;
        }
        edgeEffect = this.N;
        boolean bl2 = bl;
        if (edgeEffect != null) {
            bl2 = bl;
            if (!edgeEffect.isFinished()) {
                bl2 = bl;
                if (n3 < 0) {
                    this.N.onRelease();
                    bl2 = bl | this.N.isFinished();
                }
            }
        }
        edgeEffect = this.M;
        bl = bl2;
        if (edgeEffect != null) {
            bl = bl2;
            if (!edgeEffect.isFinished()) {
                bl = bl2;
                if (n4 > 0) {
                    this.M.onRelease();
                    bl = bl2 | this.M.isFinished();
                }
            }
        }
        edgeEffect = this.O;
        bl2 = bl;
        if (edgeEffect != null) {
            bl2 = bl;
            if (!edgeEffect.isFinished()) {
                bl2 = bl;
                if (n4 < 0) {
                    this.O.onRelease();
                    bl2 = bl | this.O.isFinished();
                }
            }
        }
        if (bl2) {
            o0.x0.Y((View)this);
        }
    }

    public final void w0() {
        if (o0.x0.x((View)this) == 0) {
            o0.x0.q0((View)this, 8);
        }
    }

    public void w1(int n3, int n4) {
        this.x1(n3, n4, null);
    }

    public int x(int n3) {
        return this.y(n3, this.L, this.N, this.getWidth());
    }

    public final void x0() {
        this.h = new b(new b.b(this){
            public final RecyclerView a;
            {
                this.a = recyclerView;
            }

            @Override
            public View a(int n3) {
                return this.a.getChildAt(n3);
            }

            @Override
            public void b(View object) {
                if ((object = RecyclerView.m0((View)object)) != null) {
                    ((d0)object).B(this.a);
                }
            }

            @Override
            public d0 c(View view) {
                return RecyclerView.m0(view);
            }

            @Override
            public void d(int n3) {
                Object object;
                block9: {
                    block8: {
                        block7: {
                            object = this.a(n3);
                            if (object == null) break block7;
                            if ((object = RecyclerView.m0((View)object)) != null) {
                                if (((d0)object).x() && !((d0)object).J()) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("called detach on an already detached child ");
                                    stringBuilder.append(object);
                                    stringBuilder.append(this.a.V());
                                    throw new IllegalArgumentException(stringBuilder.toString());
                                }
                                if (E0) {
                                    object.toString();
                                }
                                ((d0)object).b(256);
                            }
                            break block8;
                        }
                        if (D0) break block9;
                    }
                    this.a.detachViewFromParent(n3);
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("No view at offset ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(this.a.V());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }

            @Override
            public void e(View object) {
                if ((object = RecyclerView.m0((View)object)) != null) {
                    ((d0)object).C(this.a);
                }
            }

            @Override
            public void f(View view, int n3) {
                this.a.addView(view, n3);
                this.a.E(view);
            }

            @Override
            public int g() {
                return this.a.getChildCount();
            }

            @Override
            public void h(int n3) {
                View view = this.a.getChildAt(n3);
                if (view != null) {
                    this.a.F(view);
                    view.clearAnimation();
                }
                this.a.removeViewAt(n3);
            }

            @Override
            public void i() {
                int n3 = this.g();
                for (int i3 = 0; i3 < n3; ++i3) {
                    View view = this.a(i3);
                    this.a.F(view);
                    view.clearAnimation();
                }
                this.a.removeAllViews();
            }

            @Override
            public void j(View object, int n3, ViewGroup.LayoutParams object2) {
                block7: {
                    block6: {
                        block5: {
                            d0 d02 = RecyclerView.m0((View)object);
                            if (d02 == null) break block5;
                            if (!d02.x() && !d02.J()) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Called attach on a child which is not detached: ");
                                ((StringBuilder)object).append(d02);
                                ((StringBuilder)object).append(this.a.V());
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            if (E0) {
                                ((Object)d02).toString();
                            }
                            d02.f();
                            break block6;
                        }
                        if (D0) break block7;
                    }
                    this.a.attachViewToParent((View)object, n3, (ViewGroup.LayoutParams)object2);
                    return;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("No ViewHolder found for child: ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(", index: ");
                ((StringBuilder)object2).append(n3);
                ((StringBuilder)object2).append(this.a.V());
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }

            @Override
            public int k(View view) {
                return this.a.indexOfChild(view);
            }
        });
    }

    public void x1(int n3, int n4, Interpolator interpolator) {
        this.y1(n3, n4, interpolator, Integer.MIN_VALUE);
    }

    public final int y(int n3, EdgeEffect edgeEffect, EdgeEffect edgeEffect2, int n4) {
        if (n3 > 0 && edgeEffect != null && androidx.core.widget.f.b(edgeEffect) != 0.0f) {
            float f3 = (float)(-n3) * 4.0f / (float)n4;
            if ((n4 = Math.round((float)(-n4) / 4.0f * androidx.core.widget.f.d(edgeEffect, f3, 0.5f))) != n3) {
                edgeEffect.finish();
            }
            return n3 - n4;
        }
        int n5 = n3;
        if (n3 < 0) {
            n5 = n3;
            if (edgeEffect2 != null) {
                n5 = n3;
                if (androidx.core.widget.f.b(edgeEffect2) != 0.0f) {
                    float f4 = n3;
                    float f5 = n4;
                    if ((n4 = Math.round(f5 / 4.0f * androidx.core.widget.f.d(edgeEffect2, f4 = f4 * 4.0f / f5, 0.5f))) != n3) {
                        edgeEffect2.finish();
                    }
                    n5 = n3 - n4;
                }
            }
        }
        return n5;
    }

    public void y0(StateListDrawable object, Drawable drawable, StateListDrawable stateListDrawable, Drawable drawable2) {
        if (object != null && drawable != null && stateListDrawable != null && drawable2 != null) {
            Resources resources = this.getContext().getResources();
            new d(this, (StateListDrawable)object, drawable, stateListDrawable, drawable2, resources.getDimensionPixelSize(i1.b.fastscroll_default_thickness), resources.getDimensionPixelSize(i1.b.fastscroll_minimum_range), resources.getDimensionPixelOffset(i1.b.fastscroll_margin));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Trying to set fast scroller without both required drawables.");
        ((StringBuilder)object).append(this.V());
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void y1(int n3, int n4, Interpolator interpolator, int n5) {
        this.z1(n3, n4, interpolator, n5, false);
    }

    public int z(int n3) {
        return this.y(n3, this.M, this.O, this.getHeight());
    }

    public void z0() {
        this.O = null;
        this.M = null;
        this.N = null;
        this.L = null;
    }

    public void z1(int n3, int n4, Interpolator interpolator, int n5, boolean bl) {
        int n6;
        int n7;
        block11: {
            block10: {
                p p3 = this.p;
                if (p3 == null) {
                    Log.e((String)"RecyclerView", (String)"Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
                    return;
                }
                if (this.A) break block10;
                boolean bl2 = p3.p();
                n7 = 0;
                n6 = n3;
                if (!bl2) {
                    n6 = 0;
                }
                if (!this.p.q()) {
                    n4 = 0;
                }
                if (n6 != 0 || n4 != 0) break block11;
            }
            return;
        }
        if (n5 != Integer.MIN_VALUE && n5 <= 0) {
            this.scrollBy(n6, n4);
            return;
        }
        if (bl) {
            n3 = n7;
            if (n6 != 0) {
                n3 = 1;
            }
            n7 = n3;
            if (n4 != 0) {
                n7 = n3 | 2;
            }
            this.C1(n7, 1);
        }
        this.h0.e(n6, n4, n5, interpolator);
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public d0 a;
        public final Rect b = new Rect();
        public boolean c = true;
        public boolean d = false;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
        }

        public int a() {
            return this.a.m();
        }

        public boolean b() {
            return this.a.y();
        }

        public boolean c() {
            return this.a.v();
        }

        public boolean d() {
            return this.a.t();
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
        public Parcelable e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                classLoader = p.class.getClassLoader();
            }
            this.e = parcel.readParcelable(classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void p(SavedState savedState) {
            this.e = savedState.e;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeParcelable(this.e, 0);
        }
    }

    public static class a0
    extends l {
        @Override
        public EdgeEffect a(RecyclerView recyclerView, int n3) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class b0 {
    }

    public class c0
    implements Runnable {
        public int c;
        public int d;
        public OverScroller e;
        public Interpolator f;
        public boolean g;
        public boolean h;
        public final RecyclerView i;

        public c0(RecyclerView recyclerView) {
            Interpolator interpolator;
            this.i = recyclerView;
            this.f = interpolator = O0;
            this.g = false;
            this.h = false;
            this.e = new OverScroller(recyclerView.getContext(), interpolator);
        }

        public final int a(int n3, int n4) {
            int n5;
            int n6 = Math.abs(n3);
            n4 = n6 > (n5 = Math.abs(n4)) ? 1 : 0;
            RecyclerView recyclerView = this.i;
            n3 = n4 != 0 ? recyclerView.getWidth() : recyclerView.getHeight();
            n4 = n4 != 0 ? n6 : n5;
            return Math.min((int)(((float)n4 / (float)n3 + 1.0f) * 300.0f), 2000);
        }

        public void b(int n3, int n4) {
            this.i.setScrollState(2);
            this.d = 0;
            this.c = 0;
            Interpolator interpolator = this.f;
            Interpolator interpolator2 = O0;
            if (interpolator != interpolator2) {
                this.f = interpolator2;
                this.e = new OverScroller(this.i.getContext(), interpolator2);
            }
            this.e.fling(0, 0, n3, n4, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.d();
        }

        public final void c() {
            this.i.removeCallbacks(this);
            o0.x0.Z((View)this.i, this);
        }

        public void d() {
            if (this.g) {
                this.h = true;
                return;
            }
            this.c();
        }

        public void e(int n3, int n4, int n5, Interpolator interpolator) {
            int n6 = n5;
            if (n5 == Integer.MIN_VALUE) {
                n6 = this.a(n3, n4);
            }
            Interpolator interpolator2 = interpolator;
            if (interpolator == null) {
                interpolator2 = O0;
            }
            if (this.f != interpolator2) {
                this.f = interpolator2;
                this.e = new OverScroller(this.i.getContext(), interpolator2);
            }
            this.d = 0;
            this.c = 0;
            this.i.setScrollState(2);
            this.e.startScroll(0, 0, n3, n4, n6);
            this.d();
        }

        public void f() {
            this.i.removeCallbacks(this);
            this.e.abortAnimation();
        }

        @Override
        public void run() {
            Object object = this.i;
            if (((RecyclerView)object).p == null) {
                this.f();
                return;
            }
            this.h = false;
            this.g = true;
            ((RecyclerView)object).A();
            object = this.e;
            if (object.computeScrollOffset()) {
                int n3;
                int n4;
                int n5 = object.getCurrX();
                int n6 = object.getCurrY();
                int n7 = this.c;
                int n8 = this.d;
                this.c = n5;
                this.d = n6;
                n7 = this.i.x(n5 - n7);
                n5 = this.i.z(n6 - n8);
                Object object2 = this.i;
                Object object3 = ((RecyclerView)object2).w0;
                object3[0] = 0;
                object3[1] = 0;
                n8 = n7;
                n6 = n5;
                if (((RecyclerView)object2).L(n7, n5, (int[])object3, null, 1)) {
                    object3 = this.i.w0;
                    n8 = n7 - object3[0];
                    n6 = n5 - object3[1];
                }
                if (this.i.getOverScrollMode() != 2) {
                    this.i.w(n8, n6);
                }
                object2 = this.i;
                if (((RecyclerView)object2).o != null) {
                    object3 = ((RecyclerView)object2).w0;
                    object3[0] = 0;
                    object3[1] = 0;
                    ((RecyclerView)object2).q1(n8, n6, (int[])object3);
                    object2 = this.i;
                    object3 = ((RecyclerView)object2).w0;
                    n4 = object3[0];
                    n3 = object3[1];
                    n5 = n8 - n4;
                    n7 = n6 - n3;
                    object3 = ((RecyclerView)object2).p.g;
                    if (object3 != null && !((y)object3).g() && ((y)object3).h()) {
                        n6 = this.i.k0.b();
                        if (n6 == 0) {
                            ((y)object3).r();
                        } else if (((y)object3).f() >= n6) {
                            ((y)object3).p(n6 - 1);
                            ((y)object3).j(n4, n3);
                        } else {
                            ((y)object3).j(n4, n3);
                        }
                    }
                    n6 = n3;
                    n8 = n4;
                } else {
                    n3 = 0;
                    n4 = 0;
                    n5 = n8;
                    n7 = n6;
                    n8 = n3;
                    n6 = n4;
                }
                if (!this.i.r.isEmpty()) {
                    this.i.invalidate();
                }
                object3 = this.i;
                object2 = ((RecyclerView)object3).w0;
                object2[0] = false;
                object2[1] = false;
                ((RecyclerView)object3).M(n8, n6, n5, n7, null, 1, (int[])object2);
                object3 = this.i;
                object2 = ((RecyclerView)object3).w0;
                n3 = n5 - object2[0];
                n4 = n7 - object2[1];
                if (n8 != 0 || n6 != 0) {
                    ((RecyclerView)object3).O(n8, n6);
                }
                if (!this.i.awakenScrollBars()) {
                    this.i.invalidate();
                }
                n7 = object.getCurrX() == object.getFinalX() ? 1 : 0;
                n5 = object.getCurrY() == object.getFinalY() ? 1 : 0;
                n7 = !object.isFinished() && (n7 == 0 && n3 == 0 || n5 == 0 && n4 == 0) ? 0 : 1;
                object3 = this.i.p.g;
                if (!(object3 != null && ((y)object3).g() || n7 == 0)) {
                    if (this.i.getOverScrollMode() != 2) {
                        n8 = (int)object.getCurrVelocity();
                        n6 = n3 < 0 ? -n8 : (n3 > 0 ? n8 : 0);
                        if (n4 < 0) {
                            n8 = -n8;
                        } else if (n4 <= 0) {
                            n8 = 0;
                        }
                        this.i.a(n6, n8);
                    }
                    if (K0) {
                        this.i.j0.b();
                    }
                } else {
                    this.d();
                    object = this.i;
                    object3 = ((RecyclerView)object).i0;
                    if (object3 != null) {
                        ((e)object3).f((RecyclerView)object, n8, n6);
                    }
                }
            }
            if ((object = this.i.p.g) != null && ((y)object).g()) {
                ((y)object).j(0, 0);
            }
            this.g = false;
            if (this.h) {
                this.c();
                return;
            }
            this.i.setScrollState(0);
            this.i.F1(1);
        }
    }

    public static abstract class d0 {
        public static final List t = Collections.EMPTY_LIST;
        public final View a;
        public WeakReference b;
        public int c = -1;
        public int d = -1;
        public long e = -1L;
        public int f = -1;
        public int g = -1;
        public d0 h = null;
        public d0 i = null;
        public int j;
        public List k = null;
        public List l = null;
        public int m = 0;
        public v n = null;
        public boolean o = false;
        public int p = 0;
        public int q = -1;
        public RecyclerView r;
        public h s;

        public d0(View view) {
            if (view != null) {
                this.a = view;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        public void A(int n3, boolean bl) {
            if (this.d == -1) {
                this.d = this.c;
            }
            if (this.g == -1) {
                this.g = this.c;
            }
            if (bl) {
                this.g += n3;
            }
            this.c += n3;
            if (this.a.getLayoutParams() != null) {
                ((LayoutParams)this.a.getLayoutParams()).c = true;
            }
        }

        public void B(RecyclerView recyclerView) {
            int n3 = this.q;
            this.p = n3 != -1 ? n3 : o0.x0.w(this.a);
            recyclerView.t1(this, 4);
        }

        public void C(RecyclerView recyclerView) {
            recyclerView.t1(this, this.p);
            this.p = 0;
        }

        public void D() {
            if (D0 && this.x()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Attempting to reset temp-detached ViewHolder: ");
                stringBuilder.append(this);
                stringBuilder.append(". ViewHolders should be fully detached before resetting.");
                throw new IllegalStateException(stringBuilder.toString());
            }
            this.j = 0;
            this.c = -1;
            this.d = -1;
            this.e = -1L;
            this.g = -1;
            this.m = 0;
            this.h = null;
            this.i = null;
            this.d();
            this.p = 0;
            this.q = -1;
            RecyclerView.u(this);
        }

        public void E() {
            if (this.d == -1) {
                this.d = this.c;
            }
        }

        public void F(int n3, int n4) {
            this.j = n3 & n4 | this.j & ~n4;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void G(boolean bl) {
            int n3 = this.m;
            n3 = bl ? --n3 : ++n3;
            this.m = n3;
            if (n3 < 0) {
                this.m = 0;
                if (D0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
                    stringBuilder.append(this);
                    throw new RuntimeException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
                stringBuilder.append(this);
                Log.e((String)"View", (String)stringBuilder.toString());
            } else if (!bl && n3 == 1) {
                this.j |= 0x10;
            } else if (bl && n3 == 0) {
                this.j &= 0xFFFFFFEF;
            }
            if (E0) {
                ((Object)this).toString();
            }
        }

        public void H(v v3, boolean bl) {
            this.n = v3;
            this.o = bl;
        }

        public boolean I() {
            return (this.j & 0x10) != 0;
        }

        public boolean J() {
            return (this.j & 0x80) != 0;
        }

        public void K() {
            this.n.O(this);
        }

        public boolean L() {
            return (this.j & 0x20) != 0;
        }

        public void a(Object object) {
            if (object == null) {
                this.b(1024);
                return;
            }
            if ((0x400 & this.j) == 0) {
                this.g();
                this.k.add(object);
            }
        }

        public void b(int n3) {
            this.j = n3 | this.j;
        }

        public void c() {
            this.d = -1;
            this.g = -1;
        }

        public void d() {
            List list = this.k;
            if (list != null) {
                list.clear();
            }
            this.j &= 0xFFFFFBFF;
        }

        public void e() {
            this.j &= 0xFFFFFFDF;
        }

        public void f() {
            this.j &= 0xFFFFFEFF;
        }

        public final void g() {
            if (this.k == null) {
                ArrayList arrayList;
                this.k = arrayList = new ArrayList();
                this.l = Collections.unmodifiableList(arrayList);
            }
        }

        public boolean h() {
            return (this.j & 0x10) == 0 && o0.x0.L(this.a);
        }

        public void i(int n3, int n4, boolean bl) {
            this.b(8);
            this.A(n4, bl);
            this.c = n3;
        }

        public final int j() {
            RecyclerView recyclerView = this.r;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.h0(this);
        }

        public final long k() {
            return this.e;
        }

        public final int l() {
            return this.f;
        }

        public final int m() {
            int n3;
            int n4 = n3 = this.g;
            if (n3 == -1) {
                n4 = this.c;
            }
            return n4;
        }

        public final int n() {
            return this.d;
        }

        public List o() {
            if ((this.j & 0x400) == 0) {
                List list = this.k;
                if (list != null && list.size() != 0) {
                    return this.l;
                }
                return t;
            }
            return t;
        }

        public boolean p(int n3) {
            return (n3 & this.j) != 0;
        }

        public boolean q() {
            return (this.j & 0x200) != 0 || this.t();
            {
            }
        }

        public boolean r() {
            return this.a.getParent() != null && this.a.getParent() != this.r;
        }

        public boolean s() {
            return (this.j & 1) != 0;
        }

        public boolean t() {
            return (this.j & 4) != 0;
        }

        public String toString() {
            CharSequence charSequence = this.getClass().isAnonymousClass() ? "ViewHolder" : this.getClass().getSimpleName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("{");
            stringBuilder.append(Integer.toHexString(this.hashCode()));
            stringBuilder.append(" position=");
            stringBuilder.append(this.c);
            stringBuilder.append(" id=");
            stringBuilder.append(this.e);
            stringBuilder.append(", oldPos=");
            stringBuilder.append(this.d);
            stringBuilder.append(", pLpos:");
            stringBuilder.append(this.g);
            stringBuilder = new StringBuilder(stringBuilder.toString());
            if (this.w()) {
                stringBuilder.append(" scrap ");
                charSequence = this.o ? "[changeScrap]" : "[attachedScrap]";
                stringBuilder.append((String)charSequence);
            }
            if (this.t()) {
                stringBuilder.append(" invalid");
            }
            if (!this.s()) {
                stringBuilder.append(" unbound");
            }
            if (this.z()) {
                stringBuilder.append(" update");
            }
            if (this.v()) {
                stringBuilder.append(" removed");
            }
            if (this.J()) {
                stringBuilder.append(" ignored");
            }
            if (this.x()) {
                stringBuilder.append(" tmpDetached");
            }
            if (!this.u()) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" not recyclable(");
                ((StringBuilder)charSequence).append(this.m);
                ((StringBuilder)charSequence).append(")");
                stringBuilder.append(((StringBuilder)charSequence).toString());
            }
            if (this.q()) {
                stringBuilder.append(" undefined adapter position");
            }
            if (this.a.getParent() == null) {
                stringBuilder.append(" no parent");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public final boolean u() {
            return (this.j & 0x10) == 0 && !o0.x0.L(this.a);
        }

        public boolean v() {
            return (this.j & 8) != 0;
        }

        public boolean w() {
            return this.n != null;
        }

        public boolean x() {
            return (this.j & 0x100) != 0;
        }

        public boolean y() {
            return (this.j & 2) != 0;
        }

        public boolean z() {
            return (this.j & 2) != 0;
        }
    }

    public static abstract class h {
        public final i a = new i();
        public boolean b = false;
        public a c = a.c;

        public final void c(d0 d02, int n3) {
            boolean bl = d02.s == null;
            if (bl) {
                d02.c = n3;
                if (this.j()) {
                    d02.e = this.g(n3);
                }
                d02.F(1, 519);
                k0.e.a("RV OnBindView");
            }
            d02.s = this;
            if (D0) {
                if (d02.a.getParent() == null && o0.x0.N(d02.a) != d02.x()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Temp-detached state out of sync with reality. holder.isTmpDetached(): ");
                    stringBuilder.append(d02.x());
                    stringBuilder.append(", attached to window: ");
                    stringBuilder.append(o0.x0.N(d02.a));
                    stringBuilder.append(", holder: ");
                    stringBuilder.append(d02);
                    throw new IllegalStateException(stringBuilder.toString());
                }
                if (d02.a.getParent() == null && o0.x0.N(d02.a)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Attempting to bind attached holder with no parent (AKA temp detached): ");
                    stringBuilder.append(d02);
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            this.p(d02, n3, d02.o());
            if (bl) {
                d02.d();
                d02 = d02.a.getLayoutParams();
                if (d02 instanceof LayoutParams) {
                    ((LayoutParams)((Object)d02)).c = true;
                }
                k0.e.b();
            }
        }

        public boolean d() {
            int n3 = androidx.recyclerview.widget.RecyclerView$g.a[this.c.ordinal()];
            if (n3 != 1) {
                if (n3 != 2) {
                    return true;
                }
                if (this.f() > 0) {
                    return true;
                }
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final d0 e(ViewGroup object, int n3) {
            Throwable throwable2;
            block3: {
                try {
                    k0.e.a("RV CreateView");
                    object = this.q((ViewGroup)object, n3);
                    if (object.a.getParent() != null) break block3;
                    object.f = n3;
                }
                catch (Throwable throwable2) {}
                k0.e.b();
                return object;
            }
            object = new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            throw object;
            k0.e.b();
            throw throwable2;
        }

        public abstract int f();

        public long g(int n3) {
            return -1L;
        }

        public int h(int n3) {
            return 0;
        }

        public final boolean i() {
            return this.a.a();
        }

        public final boolean j() {
            return this.b;
        }

        public final void k() {
            this.a.b();
        }

        public final void l(int n3) {
            this.a.c(n3, 1);
        }

        public final void m(int n3, int n4) {
            this.a.c(n3, n4);
        }

        public void n(RecyclerView recyclerView) {
        }

        public abstract void o(d0 var1, int var2);

        public void p(d0 d02, int n3, List list) {
            this.o(d02, n3);
        }

        public abstract d0 q(ViewGroup var1, int var2);

        public void r(RecyclerView recyclerView) {
        }

        public boolean s(d0 d02) {
            return false;
        }

        public void t(d0 d02) {
        }

        public void u(d0 d02) {
        }

        public void v(d0 d02) {
        }

        public void w(j j3) {
            this.a.registerObserver(j3);
        }

        public void x(boolean bl) {
            if (!this.i()) {
                this.b = bl;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public void y(j j3) {
            this.a.unregisterObserver(j3);
        }

        public static final class a
        extends Enum {
            public static final /* enum */ a c;
            public static final /* enum */ a d;
            public static final /* enum */ a e;
            public static final a[] f;

            static {
                a a4;
                a a5;
                a a6;
                c = a6 = new a("ALLOW", 0);
                d = a5 = new a("PREVENT_WHEN_EMPTY", 1);
                e = a4 = new a("PREVENT", 2);
                f = new a[]{a6, a5, a4};
            }

            /*
             * WARNING - Possible parameter corruption
             * WARNING - void declaration
             */
            public a() {
                void cfr_renamed_1;
                void cfr_renamed_2;
            }

            public static a valueOf(String string) {
                return Enum.valueOf(a.class, string);
            }

            public static a[] values() {
                return (a[])f.clone();
            }
        }
    }

    public static class i
    extends Observable {
        public boolean a() {
            return this.mObservers.isEmpty() ^ true;
        }

        public void b() {
            for (int i3 = this.mObservers.size() - 1; i3 >= 0; --i3) {
                ((j)this.mObservers.get(i3)).a();
            }
        }

        public void c(int n3, int n4) {
            this.d(n3, n4, null);
        }

        public void d(int n3, int n4, Object object) {
            for (int i3 = this.mObservers.size() - 1; i3 >= 0; --i3) {
                ((j)this.mObservers.get(i3)).b(n3, n4, object);
            }
        }
    }

    public static abstract class j {
        public abstract void a();

        public abstract void b(int var1, int var2, Object var3);
    }

    public static interface k {
    }

    public static abstract class l {
        public abstract EdgeEffect a(RecyclerView var1, int var2);
    }

    public static abstract class m {
        public a a = null;
        public ArrayList b = new ArrayList();
        public long c = 120L;
        public long d = 120L;
        public long e = 250L;
        public long f = 250L;

        public static int e(d0 d02) {
            int n3 = d02.j;
            int n4 = n3 & 0xE;
            if (d02.t()) {
                return 4;
            }
            if ((n3 & 4) == 0) {
                n3 = d02.n();
                int n5 = d02.j();
                if (n3 != -1 && n5 != -1 && n3 != n5) {
                    return n4 | 0x800;
                }
            }
            return n4;
        }

        public abstract boolean a(d0 var1, b var2, b var3);

        public abstract boolean b(d0 var1, d0 var2, b var3, b var4);

        public abstract boolean c(d0 var1, b var2, b var3);

        public abstract boolean d(d0 var1, b var2, b var3);

        public abstract boolean f(d0 var1);

        public boolean g(d0 d02, List list) {
            return this.f(d02);
        }

        public final void h(d0 d02) {
            this.r(d02);
            a a4 = this.a;
            if (a4 != null) {
                a4.a(d02);
            }
        }

        public final void i() {
            if (this.b.size() <= 0) {
                this.b.clear();
                return;
            }
            androidx.appcompat.app.s.a(this.b.get(0));
            throw null;
        }

        public abstract void j(d0 var1);

        public abstract void k();

        public long l() {
            return this.c;
        }

        public long m() {
            return this.f;
        }

        public long n() {
            return this.e;
        }

        public long o() {
            return this.d;
        }

        public abstract boolean p();

        public b q() {
            return new b();
        }

        public void r(d0 d02) {
        }

        public b s(z z3, d0 d02) {
            return this.q().a(d02);
        }

        public b t(z z3, d0 d02, int n3, List list) {
            return this.q().a(d02);
        }

        public abstract void u();

        public void v(a a4) {
            this.a = a4;
        }

        public static interface a {
            public void a(d0 var1);
        }

        public static class b {
            public int a;
            public int b;
            public int c;
            public int d;

            public b a(d0 d02) {
                return this.b(d02, 0);
            }

            public b b(d0 d02, int n3) {
                d02 = d02.a;
                this.a = d02.getLeft();
                this.b = d02.getTop();
                this.c = d02.getRight();
                this.d = d02.getBottom();
                return this;
            }
        }
    }

    public class n
    implements m.a {
        public final RecyclerView a;

        public n(RecyclerView recyclerView) {
            this.a = recyclerView;
        }

        @Override
        public void a(d0 d02) {
            d02.G(true);
            if (d02.h != null && d02.i == null) {
                d02.h = null;
            }
            d02.i = null;
            if (!d02.I() && !this.a.f1(d02.a) && d02.x()) {
                this.a.removeDetachedView(d02.a, false);
            }
        }
    }

    public static abstract class o {
        public void d(Rect rect, int n3, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void e(Rect rect, View view, RecyclerView recyclerView, z z3) {
            this.d(rect, ((LayoutParams)view.getLayoutParams()).a(), recyclerView);
        }

        public void f(Canvas canvas, RecyclerView recyclerView) {
        }

        public void g(Canvas canvas, RecyclerView recyclerView, z z3) {
            this.f(canvas, recyclerView);
        }

        public void h(Canvas canvas, RecyclerView recyclerView) {
        }

        public void i(Canvas canvas, RecyclerView recyclerView, z z3) {
            this.h(canvas, recyclerView);
        }
    }

    public static abstract class p {
        public b a;
        public RecyclerView b;
        public final o.b c;
        public final o.b d;
        public androidx.recyclerview.widget.o e;
        public androidx.recyclerview.widget.o f;
        public y g;
        public boolean h;
        public boolean i;
        public boolean j;
        public boolean k;
        public boolean l;
        public int m;
        public boolean n;
        public int o;
        public int p;
        public int q;
        public int r;

        public p() {
            o.b b3;
            o.b b4;
            this.c = b4 = new o.b(this){
                public final p a;
                {
                    this.a = p3;
                }

                @Override
                public View a(int n3) {
                    return this.a.N(n3);
                }

                @Override
                public int b() {
                    return this.a.s0() - this.a.j0();
                }

                @Override
                public int c(View view) {
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    return this.a.V(view) - layoutParams.leftMargin;
                }

                @Override
                public int d() {
                    return this.a.i0();
                }

                @Override
                public int e(View view) {
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    return this.a.Y(view) + layoutParams.rightMargin;
                }
            };
            this.d = b3 = new o.b(this){
                public final p a;
                {
                    this.a = p3;
                }

                @Override
                public View a(int n3) {
                    return this.a.N(n3);
                }

                @Override
                public int b() {
                    return this.a.b0() - this.a.h0();
                }

                @Override
                public int c(View view) {
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    return this.a.Z(view) - layoutParams.topMargin;
                }

                @Override
                public int d() {
                    return this.a.k0();
                }

                @Override
                public int e(View view) {
                    LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                    return this.a.T(view) + layoutParams.bottomMargin;
                }
            };
            this.e = new androidx.recyclerview.widget.o(b4);
            this.f = new androidx.recyclerview.widget.o(b3);
            this.h = false;
            this.i = false;
            this.j = false;
            this.k = true;
            this.l = true;
        }

        public static boolean A0(int n3, int n4, int n5) {
            int n6 = View.MeasureSpec.getMode((int)n4);
            n4 = View.MeasureSpec.getSize((int)n4);
            if (n5 > 0 && n3 != n5) {
                return false;
            }
            if (n6 != Integer.MIN_VALUE) {
                if (n6 != 0) {
                    if (n6 != 0x40000000) {
                        return false;
                    }
                    return n4 == n3;
                }
                return true;
            }
            return n4 >= n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int P(int n3, int n4, int n5, int n6, boolean bl) {
            block16: {
                block13: {
                    block15: {
                        block12: {
                            block14: {
                                block11: {
                                    block10: {
                                        n3 = Math.max(0, n3 - n5);
                                        if (!bl) break block10;
                                        if (n6 >= 0) break block11;
                                        if (n6 == -1 && (n4 == Integer.MIN_VALUE || n4 != 0 && n4 == 0x40000000)) break block12;
                                        break block13;
                                    }
                                    if (n6 < 0) break block14;
                                }
                                n4 = 0x40000000;
                                return View.MeasureSpec.makeMeasureSpec((int)n6, (int)n4);
                            }
                            if (n6 != -1) break block15;
                        }
                        n6 = n3;
                        return View.MeasureSpec.makeMeasureSpec((int)n6, (int)n4);
                    }
                    if (n6 == -2) break block16;
                }
                n4 = 0;
                n6 = 0;
                return View.MeasureSpec.makeMeasureSpec((int)n6, (int)n4);
            }
            if (n4 != Integer.MIN_VALUE && n4 != 0x40000000) {
                n4 = 0;
                n6 = n3;
                return View.MeasureSpec.makeMeasureSpec((int)n6, (int)n4);
            }
            n4 = Integer.MIN_VALUE;
            n6 = n3;
            return View.MeasureSpec.makeMeasureSpec((int)n6, (int)n4);
        }

        public static d m0(Context context, AttributeSet attributeSet, int n3, int n4) {
            d d3 = new d();
            context = context.obtainStyledAttributes(attributeSet, i1.c.RecyclerView, n3, n4);
            d3.a = context.getInt(i1.c.RecyclerView_android_orientation, 1);
            d3.b = context.getInt(i1.c.RecyclerView_spanCount, 1);
            d3.c = context.getBoolean(i1.c.RecyclerView_reverseLayout, false);
            d3.d = context.getBoolean(i1.c.RecyclerView_stackFromEnd, false);
            context.recycle();
            return d3;
        }

        public static int s(int n3, int n4, int n5) {
            int n6 = View.MeasureSpec.getMode((int)n3);
            n3 = View.MeasureSpec.getSize((int)n3);
            if (n6 != Integer.MIN_VALUE) {
                if (n6 != 0x40000000) {
                    n3 = Math.max(n4, n5);
                }
                return n3;
            }
            return Math.min(n3, Math.max(n4, n5));
        }

        public abstract int A(z var1);

        public abstract int A1(int var1, v var2, z var3);

        public void B(v v3) {
            for (int i3 = this.O() - 1; i3 >= 0; --i3) {
                this.z1(v3, i3, this.N(i3));
            }
        }

        public boolean B0() {
            y y3 = this.g;
            return y3 != null && y3.h();
        }

        public abstract void B1(int var1);

        public void C(int n3) {
            this.D(n3, this.N(n3));
        }

        public boolean C0(View view, boolean bl, boolean bl2) {
            bl2 = this.e.b(view, 24579) && this.f.b(view, 24579);
            if (bl) {
                return bl2;
            }
            return bl2 ^ true;
        }

        public abstract int C1(int var1, v var2, z var3);

        public final void D(int n3, View view) {
            this.a.d(n3);
        }

        public void D0(View view, int n3, int n4, int n5, int n6) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = layoutParams.b;
            view.layout(n3 + rect.left + layoutParams.leftMargin, n4 + rect.top + layoutParams.topMargin, n5 - rect.right - layoutParams.rightMargin, n6 - rect.bottom - layoutParams.bottomMargin);
        }

        public void D1(RecyclerView recyclerView) {
            this.E1(View.MeasureSpec.makeMeasureSpec((int)recyclerView.getWidth(), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)recyclerView.getHeight(), (int)0x40000000));
        }

        public void E(RecyclerView recyclerView) {
            this.i = true;
            this.K0(recyclerView);
        }

        public void E0(View view, int n3, int n4) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            Rect rect = this.b.q0(view);
            int n5 = rect.left;
            int n6 = rect.right;
            int n7 = rect.top;
            int n8 = rect.bottom;
            n3 = androidx.recyclerview.widget.RecyclerView$p.P(this.s0(), this.t0(), this.i0() + this.j0() + layoutParams.leftMargin + layoutParams.rightMargin + (n3 + (n5 + n6)), layoutParams.width, this.p());
            if (this.J1(view, n3, n4 = androidx.recyclerview.widget.RecyclerView$p.P(this.b0(), this.c0(), this.k0() + this.h0() + layoutParams.topMargin + layoutParams.bottomMargin + (n4 + (n7 + n8)), layoutParams.height, this.q()), layoutParams)) {
                view.measure(n3, n4);
            }
        }

        public void E1(int n3, int n4) {
            this.q = View.MeasureSpec.getSize((int)n3);
            this.o = n3 = View.MeasureSpec.getMode((int)n3);
            if (n3 == 0 && !I0) {
                this.q = 0;
            }
            this.r = View.MeasureSpec.getSize((int)n4);
            this.p = n3 = View.MeasureSpec.getMode((int)n4);
            if (n3 == 0 && !I0) {
                this.r = 0;
            }
        }

        public void F(RecyclerView recyclerView, v v3) {
            this.i = false;
            this.M0(recyclerView, v3);
        }

        public void F0(int n3, int n4) {
            Object object = this.N(n3);
            if (object != null) {
                this.C(n3);
                this.m((View)object, n4);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot move a child from non-existing index:");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(this.b.toString());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void F1(int n3, int n4) {
            this.b.setMeasuredDimension(n3, n4);
        }

        public View G(View view) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null) {
                return null;
            }
            if ((view = recyclerView.X(view)) == null) {
                return null;
            }
            if (this.a.n(view)) {
                return null;
            }
            return view;
        }

        public void G0(int n3) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.H0(n3);
            }
        }

        public void G1(Rect rect, int n3, int n4) {
            int n5 = rect.width();
            int n6 = this.i0();
            int n7 = this.j0();
            int n8 = rect.height();
            int n9 = this.k0();
            int n10 = this.h0();
            this.F1(androidx.recyclerview.widget.RecyclerView$p.s(n3, n5 + n6 + n7, this.g0()), androidx.recyclerview.widget.RecyclerView$p.s(n4, n8 + n9 + n10, this.f0()));
        }

        public View H(int n3) {
            int n4 = this.O();
            for (int i3 = 0; i3 < n4; ++i3) {
                View view = this.N(i3);
                d0 d02 = RecyclerView.m0(view);
                if (d02 == null || d02.m() != n3 || d02.J() || !this.b.k0.e() && d02.v()) continue;
                return view;
            }
            return null;
        }

        public void H0(int n3) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.I0(n3);
            }
        }

        public void H1(int n3, int n4) {
            int n5 = this.O();
            if (n5 == 0) {
                this.b.C(n3, n4);
                return;
            }
            int n6 = Integer.MIN_VALUE;
            int n7 = Integer.MAX_VALUE;
            int n8 = Integer.MIN_VALUE;
            int n9 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < n5; ++i3) {
                View view = this.N(i3);
                Rect rect = this.b.l;
                this.U(view, rect);
                int n10 = rect.left;
                int n11 = n9;
                if (n10 < n9) {
                    n11 = n10;
                }
                n10 = rect.right;
                n9 = n6;
                if (n10 > n6) {
                    n9 = n10;
                }
                n6 = rect.top;
                n10 = n7;
                if (n6 < n7) {
                    n10 = n6;
                }
                n6 = rect.bottom;
                n7 = n8;
                if (n6 > n8) {
                    n7 = n6;
                }
                n6 = n9;
                n8 = n7;
                n9 = n11;
                n7 = n10;
            }
            this.b.l.set(n9, n7, n6, n8);
            this.G1(this.b.l, n3, n4);
        }

        public abstract LayoutParams I();

        public void I0(h h3, h h4) {
        }

        public void I1(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.b = null;
                this.a = null;
                this.q = 0;
                this.r = 0;
            } else {
                this.b = recyclerView;
                this.a = recyclerView.h;
                this.q = recyclerView.getWidth();
                this.r = recyclerView.getHeight();
            }
            this.o = 0x40000000;
            this.p = 0x40000000;
        }

        public LayoutParams J(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public boolean J0(RecyclerView recyclerView, ArrayList arrayList, int n3, int n4) {
            return false;
        }

        public boolean J1(View view, int n3, int n4, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.k || !androidx.recyclerview.widget.RecyclerView$p.A0(view.getWidth(), n3, layoutParams.width) || !androidx.recyclerview.widget.RecyclerView$p.A0(view.getHeight(), n4, layoutParams.height);
            {
            }
        }

        public LayoutParams K(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams)layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public void K0(RecyclerView recyclerView) {
        }

        public boolean K1() {
            return false;
        }

        public int L() {
            return -1;
        }

        public void L0(RecyclerView recyclerView) {
        }

        public boolean L1(View view, int n3, int n4, LayoutParams layoutParams) {
            return !this.k || !androidx.recyclerview.widget.RecyclerView$p.A0(view.getMeasuredWidth(), n3, layoutParams.width) || !androidx.recyclerview.widget.RecyclerView$p.A0(view.getMeasuredHeight(), n4, layoutParams.height);
            {
            }
        }

        public int M(View view) {
            return ((LayoutParams)view.getLayoutParams()).b.bottom;
        }

        public void M0(RecyclerView recyclerView, v v3) {
            this.L0(recyclerView);
        }

        public abstract void M1(RecyclerView var1, z var2, int var3);

        public View N(int n3) {
            b b3 = this.a;
            if (b3 != null) {
                return b3.f(n3);
            }
            return null;
        }

        public abstract View N0(View var1, int var2, v var3, z var4);

        public void N1(y y3) {
            y y4 = this.g;
            if (y4 != null && y3 != y4 && y4.h()) {
                this.g.r();
            }
            this.g = y3;
            y3.q(this.b, this);
        }

        public int O() {
            b b3 = this.a;
            if (b3 != null) {
                return b3.g();
            }
            return 0;
        }

        public void O0(AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.b;
            this.P0(recyclerView.e, recyclerView.k0, accessibilityEvent);
        }

        public void O1() {
            y y3 = this.g;
            if (y3 != null) {
                y3.r();
            }
        }

        public void P0(v object, z z3, AccessibilityEvent accessibilityEvent) {
            object = this.b;
            if (object != null && accessibilityEvent != null) {
                boolean bl;
                boolean bl2 = bl = true;
                if (!object.canScrollVertically(1)) {
                    bl2 = bl;
                    if (!this.b.canScrollVertically(-1)) {
                        bl2 = bl;
                        if (!this.b.canScrollHorizontally(-1)) {
                            bl2 = this.b.canScrollHorizontally(1) ? bl : false;
                        }
                    }
                }
                accessibilityEvent.setScrollable(bl2);
                object = this.b.o;
                if (object != null) {
                    accessibilityEvent.setItemCount(((h)object).f());
                }
            }
        }

        public boolean P1() {
            return false;
        }

        public final int[] Q(View view, Rect rect) {
            int n3 = this.i0();
            int n4 = this.k0();
            int n5 = this.s0();
            int n6 = this.j0();
            int n7 = this.b0();
            int n8 = this.h0();
            int n9 = view.getLeft() + rect.left - view.getScrollX();
            int n10 = view.getTop() + rect.top - view.getScrollY();
            int n11 = rect.width();
            int n12 = rect.height();
            int n13 = n9 - n3;
            n3 = Math.min(0, n13);
            int n14 = n10 - n4;
            n4 = Math.min(0, n14);
            n11 = n11 + n9 - (n5 - n6);
            n6 = Math.max(0, n11);
            n10 = Math.max(0, n12 + n10 - (n7 - n8));
            if (this.d0() == 1) {
                n3 = n6 != 0 ? n6 : Math.max(n3, n11);
            } else if (n3 == 0) {
                n3 = Math.min(n13, n6);
            }
            if (n4 == 0) {
                n4 = Math.min(n14, n10);
            }
            return new int[]{n3, n4};
        }

        public void Q0(v v3, z z3, p0.s s3) {
            if (this.b.canScrollVertically(-1) || this.b.canScrollHorizontally(-1)) {
                s3.a(8192);
                s3.B0(true);
            }
            if (this.b.canScrollVertically(1) || this.b.canScrollHorizontally(1)) {
                s3.a(4096);
                s3.B0(true);
            }
            s3.j0(s.e.b(this.o0(v3, z3), this.S(v3, z3), this.z0(v3, z3), this.p0(v3, z3)));
        }

        public boolean R() {
            RecyclerView recyclerView = this.b;
            return recyclerView != null && recyclerView.j;
        }

        public void R0(p0.s s3) {
            RecyclerView recyclerView = this.b;
            this.Q0(recyclerView.e, recyclerView.k0, s3);
        }

        public int S(v v3, z z3) {
            return -1;
        }

        public void S0(View view, p0.s s3) {
            Object object = RecyclerView.m0(view);
            if (object != null && !((d0)object).v() && !this.a.n(((d0)object).a)) {
                object = this.b;
                this.T0(((RecyclerView)object).e, ((RecyclerView)object).k0, view, s3);
            }
        }

        public int T(View view) {
            return view.getBottom() + this.M(view);
        }

        public void T0(v v3, z z3, View view, p0.s s3) {
        }

        public void U(View view, Rect rect) {
            RecyclerView.n0(view, rect);
        }

        public View U0(View view, int n3) {
            return null;
        }

        public int V(View view) {
            return view.getLeft() - this.e0(view);
        }

        public void V0(RecyclerView recyclerView, int n3, int n4) {
        }

        public int W(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).b;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public void W0(RecyclerView recyclerView) {
        }

        public int X(View view) {
            Rect rect = ((LayoutParams)view.getLayoutParams()).b;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public void X0(RecyclerView recyclerView, int n3, int n4, int n5) {
        }

        public int Y(View view) {
            return view.getRight() + this.n0(view);
        }

        public void Y0(RecyclerView recyclerView, int n3, int n4) {
        }

        public int Z(View view) {
            return view.getTop() - this.q0(view);
        }

        public void Z0(RecyclerView recyclerView, int n3, int n4) {
        }

        public View a0() {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null) {
                return null;
            }
            if ((recyclerView = recyclerView.getFocusedChild()) != null && !this.a.n((View)recyclerView)) {
                return recyclerView;
            }
            return null;
        }

        public void a1(RecyclerView recyclerView, int n3, int n4, Object object) {
            this.Z0(recyclerView, n3, n4);
        }

        public int b0() {
            return this.r;
        }

        public abstract void b1(v var1, z var2);

        public int c0() {
            return this.p;
        }

        public void c1(z z3) {
        }

        public int d0() {
            return o0.x0.y((View)this.b);
        }

        public void d1(v v3, z z3, int n3, int n4) {
            this.b.C(n3, n4);
        }

        public int e() {
            Object object = this.b;
            object = object != null ? ((RecyclerView)object).getAdapter() : null;
            if (object != null) {
                return ((h)object).f();
            }
            return 0;
        }

        public int e0(View view) {
            return ((LayoutParams)view.getLayoutParams()).b.left;
        }

        public boolean e1(RecyclerView recyclerView, View view, View view2) {
            return this.B0() || recyclerView.B0();
            {
            }
        }

        public int f0() {
            return o0.x0.z((View)this.b);
        }

        public boolean f1(RecyclerView recyclerView, z z3, View view, View view2) {
            return this.e1(recyclerView, view, view2);
        }

        public void g(View view) {
            this.h(view, -1);
        }

        public int g0() {
            return o0.x0.A((View)this.b);
        }

        public void g1(Parcelable parcelable) {
        }

        public void h(View view, int n3) {
            this.k(view, n3, true);
        }

        public int h0() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public Parcelable h1() {
            return null;
        }

        public void i(View view) {
            this.j(view, -1);
        }

        public int i0() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public void i1(int n3) {
        }

        public void j(View view, int n3) {
            this.k(view, n3, false);
        }

        public int j0() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public void j1(y y3) {
            if (this.g == y3) {
                this.g = null;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void k(View view, int n3, boolean bl) {
            LayoutParams layoutParams;
            Object object;
            block13: {
                block12: {
                    object = RecyclerView.m0(view);
                    if (!bl && !((d0)object).v()) {
                        this.b.i.p((d0)object);
                    } else {
                        this.b.i.b((d0)object);
                    }
                    layoutParams = (LayoutParams)view.getLayoutParams();
                    if (((d0)object).L() || ((d0)object).w()) break block12;
                    if (view.getParent() == this.b) {
                        int n4 = this.a.m(view);
                        int n5 = n3;
                        if (n3 == -1) {
                            n5 = this.a.g();
                        }
                        if (n4 == -1) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                            ((StringBuilder)object).append(this.b.indexOfChild(view));
                            ((StringBuilder)object).append(this.b.V());
                            throw new IllegalStateException(((StringBuilder)object).toString());
                        }
                        if (n4 != n5) {
                            this.b.p.F0(n4, n5);
                        }
                        break block13;
                    } else {
                        this.a.a(view, n3, false);
                        layoutParams.c = true;
                        y y3 = this.g;
                        if (y3 != null && y3.h()) {
                            this.g.k(view);
                        }
                    }
                    break block13;
                }
                if (((d0)object).w()) {
                    ((d0)object).K();
                } else {
                    ((d0)object).e();
                }
                this.a.c(view, n3, view.getLayoutParams(), false);
            }
            if (layoutParams.d) {
                if (E0) {
                    Objects.toString(layoutParams.a);
                }
                ((d0)object).a.invalidate();
                layoutParams.d = false;
            }
        }

        public int k0() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public boolean k1(int n3, Bundle bundle) {
            RecyclerView recyclerView = this.b;
            return this.l1(recyclerView.e, recyclerView.k0, n3, bundle);
        }

        public void l(String string) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.r(string);
            }
        }

        public int l0(View view) {
            return ((LayoutParams)view.getLayoutParams()).a();
        }

        /*
         * Unable to fully structure code
         */
        public boolean l1(v var1_1, z var2_2, int var3_3, Bundle var4_4) {
            block9: {
                if (this.b == null) {
                    return false;
                }
                var7_5 = this.b0();
                var8_6 = this.s0();
                var1_1 = new Rect();
                var5_7 = var7_5;
                var6_8 = var8_6;
                if (this.b.getMatrix().isIdentity()) {
                    var5_7 = var7_5;
                    var6_8 = var8_6;
                    if (this.b.getGlobalVisibleRect((Rect)var1_1)) {
                        var5_7 = var1_1.height();
                        var6_8 = var1_1.width();
                    }
                }
                if (var3_3 == 4096) ** GOTO lbl31
                if (var3_3 != 8192) {
                    var3_3 = 0;
                    var5_7 = 0;
                } else {
                    var3_3 = this.b.canScrollVertically(-1) != false ? -(var5_7 - this.k0() - this.h0()) : 0;
                    var5_7 = var3_3;
                    if (this.b.canScrollHorizontally(-1)) {
                        var5_7 = -(var6_8 - this.i0() - this.j0());
lbl23:
                        // 2 sources

                        while (true) {
                            var6_8 = var3_3;
                            var3_3 = var5_7;
                            var5_7 = var6_8;
                            break block9;
                            break;
                        }
                    }
                    while (true) {
                        var3_3 = 0;
                        break block9;
                        break;
                    }
lbl31:
                    // 1 sources

                    var3_3 = this.b.canScrollVertically(1) != false ? var5_7 - this.k0() - this.h0() : 0;
                    var5_7 = var3_3;
                    if (!this.b.canScrollHorizontally(1)) ** continue;
                    var5_7 = var6_8 - this.i0() - this.j0();
                    ** continue;
                }
            }
            if (var5_7 == 0 && var3_3 == 0) {
                return false;
            }
            this.b.z1(var3_3, var5_7, null, -2147483648, true);
            return true;
        }

        public void m(View view, int n3) {
            this.n(view, n3, (LayoutParams)view.getLayoutParams());
        }

        public boolean m1(View view, int n3, Bundle bundle) {
            RecyclerView recyclerView = this.b;
            return this.n1(recyclerView.e, recyclerView.k0, view, n3, bundle);
        }

        public void n(View view, int n3, LayoutParams layoutParams) {
            d0 d02 = RecyclerView.m0(view);
            if (d02.v()) {
                this.b.i.b(d02);
            } else {
                this.b.i.p(d02);
            }
            this.a.c(view, n3, (ViewGroup.LayoutParams)layoutParams, d02.v());
        }

        public int n0(View view) {
            return ((LayoutParams)view.getLayoutParams()).b.right;
        }

        public boolean n1(v v3, z z3, View view, int n3, Bundle bundle) {
            return false;
        }

        public void o(View view, Rect rect) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
                return;
            }
            rect.set(recyclerView.q0(view));
        }

        public int o0(v v3, z z3) {
            return -1;
        }

        public void o1(v v3) {
            for (int i3 = this.O() - 1; i3 >= 0; --i3) {
                if (RecyclerView.m0(this.N(i3)).J()) continue;
                this.r1(i3, v3);
            }
        }

        public abstract boolean p();

        public int p0(v v3, z z3) {
            return 0;
        }

        public void p1(v v3) {
            int n3 = v3.j();
            for (int i3 = n3 - 1; i3 >= 0; --i3) {
                m m3;
                View view = v3.n(i3);
                d0 d02 = RecyclerView.m0(view);
                if (d02.J()) continue;
                d02.G(false);
                if (d02.x()) {
                    this.b.removeDetachedView(view, false);
                }
                if ((m3 = this.b.P) != null) {
                    m3.j(d02);
                }
                d02.G(true);
                v3.D(view);
            }
            v3.e();
            if (n3 > 0) {
                this.b.invalidate();
            }
        }

        public abstract boolean q();

        public int q0(View view) {
            return ((LayoutParams)view.getLayoutParams()).b.top;
        }

        public void q1(View view, v v3) {
            this.t1(view);
            v3.G(view);
        }

        public boolean r(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void r0(View view, boolean bl, Rect rect) {
            Rect rect2;
            if (bl) {
                rect2 = ((LayoutParams)view.getLayoutParams()).b;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (this.b != null && (rect2 = view.getMatrix()) != null && !rect2.isIdentity()) {
                RectF rectF = this.b.n;
                rectF.set(rect);
                rect2.mapRect(rectF);
                rect.set((int)Math.floor(rectF.left), (int)Math.floor(rectF.top), (int)Math.ceil(rectF.right), (int)Math.ceil(rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public void r1(int n3, v v3) {
            View view = this.N(n3);
            this.u1(n3);
            v3.G(view);
        }

        public int s0() {
            return this.q;
        }

        public boolean s1(Runnable runnable) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void t(int n3, int n4, z z3, c c3) {
        }

        public int t0() {
            return this.o;
        }

        public void t1(View view) {
            this.a.p(view);
        }

        public void u(int n3, c c3) {
        }

        public boolean u0() {
            int n3 = this.O();
            for (int i3 = 0; i3 < n3; ++i3) {
                ViewGroup.LayoutParams layoutParams = this.N(i3).getLayoutParams();
                if (layoutParams.width >= 0 || layoutParams.height >= 0) continue;
                return true;
            }
            return false;
        }

        public void u1(int n3) {
            if (this.N(n3) != null) {
                this.a.q(n3);
            }
        }

        public abstract int v(z var1);

        public boolean v0() {
            return this.i;
        }

        public boolean v1(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
            return this.w1(recyclerView, view, rect, bl, false);
        }

        public abstract int w(z var1);

        public abstract boolean w0();

        public boolean w1(RecyclerView recyclerView, View object, Rect rect, boolean bl, boolean bl2) {
            object = this.Q((View)object, rect);
            View view = object[0];
            View view2 = object[1];
            if (bl2 && !this.x0(recyclerView, (int)view, (int)view2) || view == false && view2 == false) {
                return false;
            }
            if (bl) {
                recyclerView.scrollBy((int)view, (int)view2);
            } else {
                recyclerView.w1((int)view, (int)view2);
            }
            return true;
        }

        public abstract int x(z var1);

        public final boolean x0(RecyclerView recyclerView, int n3, int n4) {
            if ((recyclerView = recyclerView.getFocusedChild()) == null) {
                return false;
            }
            int n5 = this.i0();
            int n6 = this.k0();
            int n7 = this.s0();
            int n8 = this.j0();
            int n9 = this.b0();
            int n10 = this.h0();
            Rect rect = this.b.l;
            this.U((View)recyclerView, rect);
            return rect.left - n3 < n7 - n8 && rect.right - n3 > n5 && rect.top - n4 < n9 - n10 && rect.bottom - n4 > n6;
            {
            }
        }

        public void x1() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public abstract int y(z var1);

        public final boolean y0() {
            return this.l;
        }

        public void y1() {
            this.h = true;
        }

        public abstract int z(z var1);

        public boolean z0(v v3, z z3) {
            return false;
        }

        public final void z1(v v3, int n3, View view) {
            d0 d02 = RecyclerView.m0(view);
            if (d02.J()) {
                if (E0) {
                    ((Object)d02).toString();
                }
                return;
            }
            if (d02.t() && !d02.v() && !this.b.o.j()) {
                this.u1(n3);
                v3.H(d02);
                return;
            }
            this.C(n3);
            v3.I(view);
            this.b.i.k(d02);
        }

        public static interface c {
            public void a(int var1, int var2);
        }

        public static class d {
            public int a;
            public int b;
            public boolean c;
            public boolean d;
        }
    }

    public static interface q {
        public void a(View var1);

        public void b(View var1);
    }

    public static abstract class r {
        public abstract boolean a(int var1, int var2);
    }

    public static interface s {
        public boolean a(RecyclerView var1, MotionEvent var2);

        public void b(RecyclerView var1, MotionEvent var2);

        public void c(boolean var1);
    }

    public static abstract class t {
        public void a(RecyclerView recyclerView, int n3) {
        }

        public abstract void b(RecyclerView var1, int var2, int var3);
    }

    public static class u {
        public SparseArray a = new SparseArray();
        public int b = 0;
        public Set c = Collections.newSetFromMap(new IdentityHashMap());

        public void a() {
            ++this.b;
        }

        public void b(h h3) {
            this.c.add(h3);
        }

        public void c() {
            for (int i3 = 0; i3 < this.a.size(); ++i3) {
                a a4 = (a)this.a.valueAt(i3);
                ArrayList arrayList = a4.a;
                int n3 = arrayList.size();
                for (int i4 = 0; i4 < n3; ++i4) {
                    Object e3 = arrayList.get(i4);
                    u0.a.a(((d0)e3).a);
                }
                a4.a.clear();
            }
        }

        public void d() {
            --this.b;
        }

        public void e(h object, boolean bl) {
            this.c.remove(object);
            if (this.c.size() == 0 && !bl) {
                for (int i3 = 0; i3 < this.a.size(); ++i3) {
                    object = this.a;
                    object = ((a)object.get((int)object.keyAt((int)i3))).a;
                    for (int i4 = 0; i4 < ((ArrayList)object).size(); ++i4) {
                        u0.a.a(((d0)((ArrayList)object).get((int)i4)).a);
                    }
                }
            }
        }

        public void f(int n3, long l3) {
            a a4 = this.i(n3);
            a4.d = this.l(a4.d, l3);
        }

        public void g(int n3, long l3) {
            a a4 = this.i(n3);
            a4.c = this.l(a4.c, l3);
        }

        public d0 h(int n3) {
            Object object = (a)this.a.get(n3);
            if (object != null && !((a)object).a.isEmpty()) {
                object = ((a)object).a;
                for (n3 = ((ArrayList)object).size() - 1; n3 >= 0; --n3) {
                    if (((d0)((ArrayList)object).get(n3)).r()) continue;
                    return (d0)((ArrayList)object).remove(n3);
                }
            }
            return null;
        }

        public final a i(int n3) {
            a a4;
            a a5 = a4 = (a)this.a.get(n3);
            if (a4 == null) {
                a5 = new a();
                this.a.put(n3, (Object)a5);
            }
            return a5;
        }

        public void j(h h3, h h4, boolean bl) {
            if (h3 != null) {
                this.d();
            }
            if (!bl && this.b == 0) {
                this.c();
            }
            if (h4 != null) {
                this.a();
            }
        }

        public void k(d0 d02) {
            int n3 = d02.l();
            ArrayList arrayList = this.i((int)n3).a;
            if (((a)this.a.get((int)n3)).b <= arrayList.size()) {
                u0.a.a(d02.a);
                return;
            }
            if (D0 && arrayList.contains(d02)) {
                throw new IllegalArgumentException("this scrap item already exists");
            }
            d02.D();
            arrayList.add(d02);
        }

        public long l(long l3, long l4) {
            if (l3 == 0L) {
                return l4;
            }
            return l3 / 4L * 3L + l4 / 4L;
        }

        public boolean m(int n3, long l3, long l4) {
            long l5 = this.i((int)n3).d;
            return l5 == 0L || l3 + l5 < l4;
            {
            }
        }

        public boolean n(int n3, long l3, long l4) {
            long l5 = this.i((int)n3).c;
            return l5 == 0L || l3 + l5 < l4;
            {
            }
        }

        public static class a {
            public final ArrayList a = new ArrayList();
            public int b = 5;
            public long c = 0L;
            public long d = 0L;
        }
    }

    public final class v {
        public final ArrayList a;
        public ArrayList b;
        public final ArrayList c;
        public final List d;
        public int e;
        public int f;
        public u g;
        public final RecyclerView h;

        public v(RecyclerView object) {
            this.h = object;
            this.a = object = new ArrayList();
            this.b = null;
            this.c = new ArrayList();
            this.d = Collections.unmodifiableList(object);
            this.e = 2;
            this.f = 2;
        }

        public void A() {
            for (int i3 = 0; i3 < this.c.size(); ++i3) {
                u0.a.a(((d0)this.c.get((int)i3)).a);
            }
            this.B(this.h.o);
        }

        public final void B(h h3) {
            this.C(h3, false);
        }

        public final void C(h h3, boolean bl) {
            u u3 = this.g;
            if (u3 != null) {
                u3.e(h3, bl);
            }
        }

        public void D(View object) {
            object = RecyclerView.m0((View)object);
            ((d0)object).n = null;
            ((d0)object).o = false;
            ((d0)object).e();
            this.H((d0)object);
        }

        public void E() {
            for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
                this.F(i3);
            }
            this.c.clear();
            if (K0) {
                this.h.j0.b();
            }
        }

        public void F(int n3) {
            boolean bl = D0;
            d0 d02 = (d0)this.c.get(n3);
            if (E0) {
                Objects.toString(d02);
            }
            this.a(d02, true);
            this.c.remove(n3);
        }

        public void G(View view) {
            d0 d02 = RecyclerView.m0(view);
            if (d02.x()) {
                this.h.removeDetachedView(view, false);
            }
            if (d02.w()) {
                d02.K();
            } else if (d02.L()) {
                d02.e();
            }
            this.H(d02);
            if (this.h.P != null && !d02.u()) {
                this.h.P.j(d02);
            }
        }

        public void H(d0 object) {
            boolean bl = ((d0)object).w();
            boolean bl2 = false;
            int n3 = 0;
            int n4 = 1;
            if (!bl && ((d0)object).a.getParent() == null) {
                if (!((d0)object).x()) {
                    if (!((d0)object).J()) {
                        bl2 = ((d0)object).h();
                        Object object2 = this.h.o;
                        int n5 = object2 != null && bl2 && ((h)object2).s((d0)object) ? 1 : 0;
                        if (D0 && this.c.contains(object)) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("cached view received recycle internal? ");
                            ((StringBuilder)object2).append(object);
                            ((StringBuilder)object2).append(this.h.V());
                            throw new IllegalArgumentException(((StringBuilder)object2).toString());
                        }
                        if (n5 == 0 && !((d0)object).u()) {
                            if (E0) {
                                this.h.V();
                            }
                            n4 = 0;
                            n5 = n3;
                            n3 = n4;
                        } else {
                            if (this.f > 0 && !((d0)object).p(526)) {
                                n5 = n3 = this.c.size();
                                if (n3 >= this.f) {
                                    n5 = n3;
                                    if (n3 > 0) {
                                        this.F(0);
                                        n5 = n3 - 1;
                                    }
                                }
                                n3 = n5;
                                if (K0) {
                                    n3 = n5;
                                    if (n5 > 0) {
                                        n3 = n5--;
                                        if (!this.h.j0.d(((d0)object).c)) {
                                            while (n5 >= 0 && this.h.j0.d(n3 = ((d0)this.c.get((int)n5)).c)) {
                                                --n5;
                                            }
                                            n3 = n5 + 1;
                                        }
                                    }
                                }
                                this.c.add(n3, object);
                                n5 = 1;
                            } else {
                                n5 = 0;
                            }
                            if (n5 == 0) {
                                this.a((d0)object, true);
                                n3 = n4;
                            } else {
                                n3 = 0;
                            }
                        }
                        this.h.i.q((d0)object);
                        if (n5 == 0 && n3 == 0 && bl2) {
                            u0.a.a(((d0)object).a);
                            ((d0)object).s = null;
                            ((d0)object).r = null;
                        }
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.");
                    ((StringBuilder)object).append(this.h.V());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                stringBuilder.append(object);
                stringBuilder.append(this.h.V());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scrapped or attached views may not be recycled. isScrap:");
            stringBuilder.append(((d0)object).w());
            stringBuilder.append(" isAttached:");
            if (((d0)object).a.getParent() != null) {
                bl2 = true;
            }
            stringBuilder.append(bl2);
            stringBuilder.append(this.h.V());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public void I(View object) {
            if (!((d0)(object = RecyclerView.m0((View)object))).p(12) && ((d0)object).y() && !this.h.s((d0)object)) {
                if (this.b == null) {
                    this.b = new ArrayList();
                }
                ((d0)object).H(this, true);
                this.b.add(object);
                return;
            }
            if (((d0)object).t() && !((d0)object).v() && !this.h.o.j()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
                ((StringBuilder)object).append(this.h.V());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            ((d0)object).H(this, false);
            this.a.add(object);
        }

        public void J(u u3) {
            this.B(this.h.o);
            u u4 = this.g;
            if (u4 != null) {
                u4.d();
            }
            this.g = u3;
            if (u3 != null && this.h.getAdapter() != null) {
                this.g.a();
            }
            this.u();
        }

        public void K(b0 b02) {
        }

        public void L(int n3) {
            this.e = n3;
            this.P();
        }

        public final boolean M(d0 d02, int n3, int n4, long l3) {
            d02.s = null;
            d02.r = this.h;
            int n5 = d02.l();
            long l4 = this.h.getNanoTime();
            boolean bl = false;
            if (l3 != Long.MAX_VALUE && !this.g.m(n5, l4, l3)) {
                return false;
            }
            if (d02.x()) {
                RecyclerView recyclerView = this.h;
                recyclerView.attachViewToParent(d02.a, recyclerView.getChildCount(), d02.a.getLayoutParams());
                bl = true;
            }
            this.h.o.c(d02, n3);
            if (bl) {
                this.h.detachViewFromParent(d02.a);
            }
            l3 = this.h.getNanoTime();
            this.g.f(d02.l(), l3 - l4);
            this.b(d02);
            if (this.h.k0.e()) {
                d02.g = n4;
            }
            return true;
        }

        /*
         * Unable to fully structure code
         */
        public d0 N(int var1_1, boolean var2_2, long var3_3) {
            block28: {
                block32: {
                    block31: {
                        block30: {
                            block29: {
                                if (var1_1 < 0 || var1_1 >= this.h.k0.b()) break block28;
                                var14_4 = this.h.k0.e();
                                var13_5 = true;
                                if (!var14_4) break block29;
                                var15_7 = var16_6 = this.h(var1_1);
                                if (var16_6 == null) break block30;
                                var6_9 = 1;
                                break block31;
                            }
                            var15_7 = null;
                        }
                        var6_9 = 0;
                        var16_6 = var15_7;
                    }
                    var15_7 = var16_6;
                    var5_10 = var6_9;
                    if (var16_6 == null) {
                        var15_7 = var16_6 = this.m(var1_1, var2_2);
                        var5_10 = var6_9;
                        if (var16_6 != null) {
                            if (!this.Q((d0)var16_6)) {
                                if (!var2_2) {
                                    var16_6.b(4);
                                    if (var16_6.w()) {
                                        this.h.removeDetachedView(var16_6.a, false);
                                        var16_6.K();
                                    } else if (var16_6.L()) {
                                        var16_6.e();
                                    }
                                    this.H((d0)var16_6);
                                }
                                var15_7 = null;
                                var5_10 = var6_9;
                            } else {
                                var5_10 = 1;
                                var15_7 = var16_6;
                            }
                        }
                    }
                    var16_6 = var15_7;
                    var7_11 = var5_10;
                    if (var15_7 == null) {
                        var7_11 = this.h.g.m(var1_1);
                        if (var7_11 >= 0 && var7_11 < this.h.o.f()) {
                            var8_12 = this.h.o.h(var7_11);
                            var6_9 = var5_10;
                            if (this.h.o.j()) {
                                var15_7 = var16_6 = this.l(this.h.o.g(var7_11), var8_12, var2_2);
                                var6_9 = var5_10;
                                if (var16_6 != null) {
                                    var16_6.c = var7_11;
                                    var6_9 = 1;
                                    var15_7 = var16_6;
                                }
                            }
                            var17_13 = var15_7;
                            if (var15_7 == null) {
                                var2_2 = RecyclerView.D0;
                                var17_13 = this.i().h(var8_12);
                                if (var17_13 != null) {
                                    var17_13.D();
                                    if (RecyclerView.H0) {
                                        this.r((d0)var17_13);
                                    }
                                }
                            }
                            var16_6 = var17_13;
                            var7_11 = var6_9;
                            if (var17_13 == null) {
                                var9_14 = this.h.getNanoTime();
                                if (var3_3 != 0x7FFFFFFFFFFFFFFFL && !this.g.n(var8_12, var9_14, var3_3)) {
                                    return null;
                                }
                                var15_7 = this.h;
                                var16_6 = var15_7.o.e((ViewGroup)var15_7, var8_12);
                                if (RecyclerView.K0 && (var15_7 = RecyclerView.b0(var16_6.a)) != null) {
                                    var16_6.b = new WeakReference<Object>(var15_7);
                                }
                                var11_15 = this.h.getNanoTime();
                                this.g.g(var8_12, var11_15 - var9_14);
                                var2_2 = RecyclerView.D0;
                                var7_11 = var6_9;
                            }
                        } else {
                            var15_7 = new StringBuilder();
                            var15_7.append("Inconsistency detected. Invalid item position ");
                            var15_7.append(var1_1);
                            var15_7.append("(offset:");
                            var15_7.append(var7_11);
                            var15_7.append(").state:");
                            var15_7.append(this.h.k0.b());
                            var15_7.append(this.h.V());
                            throw new IndexOutOfBoundsException(var15_7.toString());
                        }
                    }
                    if (var7_11 != 0 && !this.h.k0.e() && var16_6.p(8192)) {
                        var16_6.F(0, 8192);
                        if (this.h.k0.k) {
                            var5_10 = androidx.recyclerview.widget.RecyclerView$m.e((d0)var16_6);
                            var15_7 = this.h;
                            var15_7 = var15_7.P.t(var15_7.k0, (d0)var16_6, var5_10 | 4096, var16_6.o());
                            this.h.Z0((d0)var16_6, (m.b)var15_7);
                        }
                    }
                    if (!this.h.k0.e() || !var16_6.s()) break block32;
                    var16_6.g = var1_1;
                    ** GOTO lbl-1000
                }
                if (var16_6.s() && !var16_6.z() && !var16_6.t()) lbl-1000:
                // 2 sources

                {
                    var2_2 = false;
                } else {
                    if (RecyclerView.D0 && var16_6.v()) {
                        var15_7 = new StringBuilder();
                        var15_7.append("Removed holder should be bound and it should come here only in pre-layout. Holder: ");
                        var15_7.append(var16_6);
                        var15_7.append(this.h.V());
                        throw new IllegalStateException(var15_7.toString());
                    }
                    var2_2 = this.M((d0)var16_6, this.h.g.m(var1_1), var1_1, var3_3);
                }
                var15_7 = var16_6.a.getLayoutParams();
                if (var15_7 == null) {
                    var15_7 = (LayoutParams)this.h.generateDefaultLayoutParams();
                    var16_6.a.setLayoutParams((ViewGroup.LayoutParams)var15_7);
                } else if (!this.h.checkLayoutParams((ViewGroup.LayoutParams)var15_7)) {
                    var15_7 = (LayoutParams)this.h.generateLayoutParams((ViewGroup.LayoutParams)var15_7);
                    var16_6.a.setLayoutParams((ViewGroup.LayoutParams)var15_7);
                } else {
                    var15_7 = (LayoutParams)var15_7;
                }
                var15_7.a = var16_6;
                var2_2 = var7_11 != 0 && var2_2 != false ? var13_5 : false;
                var15_7.d = var2_2;
                return var16_6;
            }
            var15_8 = new StringBuilder();
            var15_8.append("Invalid item position ");
            var15_8.append(var1_1);
            var15_8.append("(");
            var15_8.append(var1_1);
            var15_8.append("). Item count:");
            var15_8.append(this.h.k0.b());
            var15_8.append(this.h.V());
            throw new IndexOutOfBoundsException(var15_8.toString());
        }

        public void O(d0 d02) {
            if (d02.o) {
                this.b.remove(d02);
            } else {
                this.a.remove(d02);
            }
            d02.n = null;
            d02.o = false;
            d02.e();
        }

        public void P() {
            p p3 = this.h.p;
            int n3 = p3 != null ? p3.m : 0;
            this.f = this.e + n3;
            for (n3 = this.c.size() - 1; n3 >= 0 && this.c.size() > this.f; --n3) {
                this.F(n3);
            }
        }

        public boolean Q(d0 object) {
            if (((d0)object).v()) {
                if (D0 && !this.h.k0.e()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("should not receive a removed view unless it is pre layout");
                    ((StringBuilder)object).append(this.h.V());
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                return this.h.k0.e();
            }
            int n3 = ((d0)object).c;
            if (n3 >= 0 && n3 < this.h.o.f()) {
                if (!this.h.k0.e() && this.h.o.h(((d0)object).c) != ((d0)object).l()) {
                    return false;
                }
                if (this.h.o.j()) {
                    return ((d0)object).k() == this.h.o.g(((d0)object).c);
                }
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
            stringBuilder.append(object);
            stringBuilder.append(this.h.V());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public void R(int n3, int n4) {
            for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
                int n5;
                d0 d02 = (d0)this.c.get(i3);
                if (d02 == null || (n5 = d02.c) < n3 || n5 >= n4 + n3) continue;
                d02.b(2);
                this.F(i3);
            }
        }

        public void a(d0 d02, boolean bl) {
            RecyclerView.u(d02);
            View view = d02.a;
            o0.a a4 = this.h.r0;
            if (a4 != null) {
                a4 = (a4 = a4.n()) instanceof k.a ? ((k.a)a4).n(view) : null;
                o0.x0.h0(view, a4);
            }
            if (bl) {
                this.g(d02);
            }
            d02.s = null;
            d02.r = null;
            this.i().k(d02);
        }

        public final void b(d0 d02) {
            if (this.h.A0()) {
                o0.a a4;
                d02 = d02.a;
                if (o0.x0.w((View)d02) == 0) {
                    o0.x0.o0((View)d02, 1);
                }
                if ((a4 = this.h.r0) != null) {
                    if ((a4 = a4.n()) instanceof k.a) {
                        ((k.a)a4).o((View)d02);
                    }
                    o0.x0.h0((View)d02, a4);
                }
            }
        }

        public void c() {
            this.a.clear();
            this.E();
        }

        public void d() {
            int n3;
            int n4 = this.c.size();
            int n5 = 0;
            for (n3 = 0; n3 < n4; ++n3) {
                ((d0)this.c.get(n3)).c();
            }
            n4 = this.a.size();
            for (n3 = 0; n3 < n4; ++n3) {
                ((d0)this.a.get(n3)).c();
            }
            ArrayList arrayList = this.b;
            if (arrayList != null) {
                n4 = arrayList.size();
                for (n3 = n5; n3 < n4; ++n3) {
                    ((d0)this.b.get(n3)).c();
                }
            }
        }

        public void e() {
            this.a.clear();
            ArrayList arrayList = this.b;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        public int f(int n3) {
            if (n3 >= 0 && n3 < this.h.k0.b()) {
                if (!this.h.k0.e()) {
                    return n3;
                }
                return this.h.g.m(n3);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid position ");
            stringBuilder.append(n3);
            stringBuilder.append(". State item count is ");
            stringBuilder.append(this.h.k0.b());
            stringBuilder.append(this.h.V());
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public void g(d0 d02) {
            this.h.getClass();
            if (this.h.q.size() <= 0) {
                Object object = this.h.o;
                if (object != null) {
                    ((h)object).v(d02);
                }
                object = this.h;
                if (((RecyclerView)object).k0 != null) {
                    ((RecyclerView)object).i.q(d02);
                }
                if (E0) {
                    Objects.toString(d02);
                }
                return;
            }
            androidx.appcompat.app.s.a(this.h.q.get(0));
            throw null;
        }

        public d0 h(int n3) {
            int n4;
            Object object = this.b;
            if (object != null && (n4 = ((ArrayList)object).size()) != 0) {
                int n5 = 0;
                for (int i3 = 0; i3 < n4; ++i3) {
                    object = (d0)this.b.get(i3);
                    if (((d0)object).L() || ((d0)object).m() != n3) continue;
                    ((d0)object).b(32);
                    return object;
                }
                if (this.h.o.j() && (n3 = this.h.g.m(n3)) > 0 && n3 < this.h.o.f()) {
                    long l3 = this.h.o.g(n3);
                    for (n3 = n5; n3 < n4; ++n3) {
                        object = (d0)this.b.get(n3);
                        if (((d0)object).L() || ((d0)object).k() != l3) continue;
                        ((d0)object).b(32);
                        return object;
                    }
                }
            }
            return null;
        }

        public u i() {
            if (this.g == null) {
                this.g = new u();
                this.u();
            }
            return this.g;
        }

        public int j() {
            return this.a.size();
        }

        public List k() {
            return this.d;
        }

        public d0 l(long l3, int n3, boolean bl) {
            d0 d02;
            int n4;
            for (n4 = this.a.size() - 1; n4 >= 0; --n4) {
                d02 = (d0)this.a.get(n4);
                if (d02.k() != l3 || d02.L()) continue;
                if (n3 == d02.l()) {
                    d02.b(32);
                    if (d02.v() && !this.h.k0.e()) {
                        d02.F(2, 14);
                    }
                    return d02;
                }
                if (bl) continue;
                this.a.remove(n4);
                this.h.removeDetachedView(d02.a, false);
                this.D(d02.a);
            }
            for (n4 = this.c.size() - 1; n4 >= 0; --n4) {
                d02 = (d0)this.c.get(n4);
                if (d02.k() != l3 || d02.r()) continue;
                if (n3 == d02.l()) {
                    if (!bl) {
                        this.c.remove(n4);
                    }
                    return d02;
                }
                if (bl) continue;
                this.F(n4);
                return null;
            }
            return null;
        }

        public d0 m(int n3, boolean bl) {
            Object object;
            d0 d02;
            int n4;
            int n5 = this.a.size();
            int n6 = 0;
            for (n4 = 0; n4 < n5; ++n4) {
                d02 = (d0)this.a.get(n4);
                if (d02.L() || d02.m() != n3 || d02.t() || !this.h.k0.h && d02.v()) continue;
                d02.b(32);
                return d02;
            }
            if (!bl && (object = this.h.h.e(n3)) != null) {
                d02 = RecyclerView.m0((View)object);
                this.h.h.s((View)object);
                n3 = this.h.h.m((View)object);
                if (n3 != -1) {
                    this.h.h.d(n3);
                    this.I((View)object);
                    d02.b(8224);
                    return d02;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("layout index should not be -1 after unhiding a view:");
                ((StringBuilder)object).append(d02);
                ((StringBuilder)object).append(this.h.V());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            n5 = this.c.size();
            for (n4 = n6; n4 < n5; ++n4) {
                d02 = (d0)this.c.get(n4);
                if (d02.t() || d02.m() != n3 || d02.r()) continue;
                if (!bl) {
                    this.c.remove(n4);
                }
                if (E0) {
                    ((Object)d02).toString();
                }
                return d02;
            }
            return null;
        }

        public View n(int n3) {
            return ((d0)this.a.get((int)n3)).a;
        }

        public View o(int n3) {
            return this.p(n3, false);
        }

        public View p(int n3, boolean bl) {
            return this.N((int)n3, (boolean)bl, (long)Long.MAX_VALUE).a;
        }

        public final void q(ViewGroup viewGroup, boolean bl) {
            int n3;
            for (n3 = viewGroup.getChildCount() - 1; n3 >= 0; --n3) {
                View view = viewGroup.getChildAt(n3);
                if (!(view instanceof ViewGroup)) continue;
                this.q((ViewGroup)view, true);
            }
            if (!bl) {
                return;
            }
            if (viewGroup.getVisibility() == 4) {
                viewGroup.setVisibility(0);
                viewGroup.setVisibility(4);
                return;
            }
            n3 = viewGroup.getVisibility();
            viewGroup.setVisibility(4);
            viewGroup.setVisibility(n3);
        }

        public final void r(d0 d02) {
            d02 = d02.a;
            if (d02 instanceof ViewGroup) {
                this.q((ViewGroup)d02, false);
            }
        }

        public void s() {
            int n3 = this.c.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                LayoutParams layoutParams = (LayoutParams)((d0)this.c.get((int)i3)).a.getLayoutParams();
                if (layoutParams == null) continue;
                layoutParams.c = true;
            }
        }

        public void t() {
            Object object;
            int n3 = this.c.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                object = (d0)this.c.get(i3);
                if (object == null) continue;
                ((d0)object).b(6);
                ((d0)object).a(null);
            }
            object = this.h.o;
            if (object != null && ((h)object).j()) {
                return;
            }
            this.E();
        }

        public final void u() {
            if (this.g != null) {
                RecyclerView recyclerView = this.h;
                if (recyclerView.o != null && recyclerView.isAttachedToWindow()) {
                    this.g.b(this.h.o);
                }
            }
        }

        public void v(int n3, int n4) {
            int n5 = this.c.size();
            for (int i3 = 0; i3 < n5; ++i3) {
                d0 d02 = (d0)this.c.get(i3);
                if (d02 == null || d02.c < n3) continue;
                if (E0) {
                    ((Object)d02).toString();
                }
                d02.A(n4, false);
            }
        }

        public void w(int n3, int n4) {
            int n5;
            int n6;
            int n7;
            if (n3 < n4) {
                n7 = -1;
                n6 = n3;
                n5 = n4;
            } else {
                n7 = 1;
                n5 = n3;
                n6 = n4;
            }
            int n8 = this.c.size();
            for (int i3 = 0; i3 < n8; ++i3) {
                int n9;
                d0 d02 = (d0)this.c.get(i3);
                if (d02 == null || (n9 = d02.c) < n6 || n9 > n5) continue;
                if (n9 == n3) {
                    d02.A(n4 - n3, false);
                } else {
                    d02.A(n7, false);
                }
                if (!E0) continue;
                ((Object)d02).toString();
            }
        }

        public void x(int n3, int n4, boolean bl) {
            for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
                d0 d02 = (d0)this.c.get(i3);
                if (d02 == null) continue;
                int n5 = d02.c;
                if (n5 >= n3 + n4) {
                    if (E0) {
                        ((Object)d02).toString();
                    }
                    d02.A(-n4, bl);
                    continue;
                }
                if (n5 < n3) continue;
                d02.b(8);
                this.F(i3);
            }
        }

        public void y(h h3, h h4, boolean bl) {
            this.c();
            this.C(h3, true);
            this.i().j(h3, h4, bl);
            this.u();
        }

        public void z() {
            this.u();
        }
    }

    public static interface w {
    }

    public class x
    extends j {
        public final RecyclerView a;

        public x(RecyclerView recyclerView) {
            this.a = recyclerView;
        }

        @Override
        public void a() {
            this.a.r(null);
            RecyclerView recyclerView = this.a;
            recyclerView.k0.g = true;
            recyclerView.X0(true);
            if (!this.a.g.p()) {
                this.a.requestLayout();
            }
        }

        @Override
        public void b(int n3, int n4, Object object) {
            this.a.r(null);
            if (this.a.g.r(n3, n4, object)) {
                this.c();
            }
        }

        public void c() {
            RecyclerView recyclerView;
            if (J0) {
                recyclerView = this.a;
                if (recyclerView.v && recyclerView.u) {
                    o0.x0.Z((View)recyclerView, recyclerView.k);
                    return;
                }
            }
            recyclerView = this.a;
            recyclerView.D = true;
            recyclerView.requestLayout();
        }
    }

    public static abstract class y {
        public int a = -1;
        public RecyclerView b;
        public p c;
        public boolean d;
        public boolean e;
        public View f;
        public final a g = new a(0, 0);
        public boolean h;

        public PointF a(int n3) {
            Object object = this.e();
            if (object instanceof b) {
                return ((b)object).d(n3);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("You should override computeScrollVectorForPosition when the LayoutManager does not implement ");
            ((StringBuilder)object).append(b.class.getCanonicalName());
            Log.w((String)"RecyclerView", (String)((StringBuilder)object).toString());
            return null;
        }

        public View b(int n3) {
            return this.b.p.H(n3);
        }

        public int c() {
            return this.b.p.O();
        }

        public int d(View view) {
            return this.b.k0(view);
        }

        public p e() {
            return this.c;
        }

        public int f() {
            return this.a;
        }

        public boolean g() {
            return this.d;
        }

        public boolean h() {
            return this.e;
        }

        public void i(PointF pointF) {
            float f3 = pointF.x;
            float f4 = pointF.y;
            f3 = (float)Math.sqrt(f3 * f3 + f4 * f4);
            pointF.x /= f3;
            pointF.y /= f3;
        }

        public void j(int n3, int n4) {
            float f3;
            View view;
            RecyclerView recyclerView = this.b;
            if (this.a == -1 || recyclerView == null) {
                this.r();
            }
            if (this.d && this.f == null && this.c != null && (view = this.a(this.a)) != null && ((f3 = view.x) != 0.0f || view.y != 0.0f)) {
                recyclerView.q1((int)Math.signum(f3), (int)Math.signum(view.y), null);
            }
            this.d = false;
            view = this.f;
            if (view != null) {
                if (this.d(view) == this.a) {
                    this.o(this.f, recyclerView.k0, this.g);
                    this.g.c(recyclerView);
                    this.r();
                } else {
                    Log.e((String)"RecyclerView", (String)"Passed over target position while smooth scrolling.");
                    this.f = null;
                }
            }
            if (this.e) {
                this.l(n3, n4, recyclerView.k0, this.g);
                boolean bl = this.g.a();
                this.g.c(recyclerView);
                if (bl && this.e) {
                    this.d = true;
                    recyclerView.h0.d();
                }
            }
        }

        public void k(View view) {
            if (this.d(view) == this.f()) {
                this.f = view;
                boolean bl = D0;
            }
        }

        public abstract void l(int var1, int var2, z var3, a var4);

        public abstract void m();

        public abstract void n();

        public abstract void o(View var1, z var2, a var3);

        public void p(int n3) {
            this.a = n3;
        }

        public void q(RecyclerView recyclerView, p p3) {
            recyclerView.h0.f();
            if (this.h) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("An instance of ");
                stringBuilder.append(this.getClass().getSimpleName());
                stringBuilder.append(" was started more than once. Each instance of");
                stringBuilder.append(this.getClass().getSimpleName());
                stringBuilder.append(" is intended to only be used once. You should create a new instance for each use.");
                Log.w((String)"RecyclerView", (String)stringBuilder.toString());
            }
            this.b = recyclerView;
            this.c = p3;
            int n3 = this.a;
            if (n3 != -1) {
                recyclerView.k0.a = n3;
                this.e = true;
                this.d = true;
                this.f = this.b(this.f());
                this.m();
                this.b.h0.d();
                this.h = true;
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public final void r() {
            if (!this.e) {
                return;
            }
            this.e = false;
            this.n();
            this.b.k0.a = -1;
            this.f = null;
            this.a = -1;
            this.d = false;
            this.c.j1(this);
            this.c = null;
            this.b = null;
        }

        public static class a {
            public int a;
            public int b;
            public int c;
            public int d = -1;
            public Interpolator e;
            public boolean f = false;
            public int g = 0;

            public a(int n3, int n4) {
                this(n3, n4, Integer.MIN_VALUE, null);
            }

            public a(int n3, int n4, int n5, Interpolator interpolator) {
                this.a = n3;
                this.b = n4;
                this.c = n5;
                this.e = interpolator;
            }

            public boolean a() {
                return this.d >= 0;
            }

            public void b(int n3) {
                this.d = n3;
            }

            public void c(RecyclerView recyclerView) {
                int n3 = this.d;
                if (n3 >= 0) {
                    this.d = -1;
                    recyclerView.D0(n3);
                    this.f = false;
                    return;
                }
                if (this.f) {
                    this.e();
                    recyclerView.h0.e(this.a, this.b, this.c, this.e);
                    this.g = n3 = this.g + 1;
                    if (n3 > 10) {
                        Log.e((String)"RecyclerView", (String)"Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.f = false;
                    return;
                }
                this.g = 0;
            }

            public void d(int n3, int n4, int n5, Interpolator interpolator) {
                this.a = n3;
                this.b = n4;
                this.c = n5;
                this.e = interpolator;
                this.f = true;
            }

            public final void e() {
                if (this.e != null && this.c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                if (this.c >= 1) {
                    return;
                }
                throw new IllegalStateException("Scroll duration must be a positive number");
            }
        }

        public static interface b {
            public PointF d(int var1);
        }
    }

    public static class z {
        public int a = -1;
        public SparseArray b;
        public int c = 0;
        public int d = 0;
        public int e = 1;
        public int f = 0;
        public boolean g = false;
        public boolean h = false;
        public boolean i = false;
        public boolean j = false;
        public boolean k = false;
        public boolean l = false;
        public int m;
        public long n;
        public int o;
        public int p;
        public int q;

        public void a(int n3) {
            if ((this.e & n3) != 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Layout state should be one of ");
            stringBuilder.append(Integer.toBinaryString(n3));
            stringBuilder.append(" but it is ");
            stringBuilder.append(Integer.toBinaryString(this.e));
            throw new IllegalStateException(stringBuilder.toString());
        }

        public int b() {
            if (this.h) {
                return this.c - this.d;
            }
            return this.f;
        }

        public int c() {
            return this.a;
        }

        public boolean d() {
            return this.a != -1;
        }

        public boolean e() {
            return this.h;
        }

        public void f(h h3) {
            this.e = 1;
            this.f = h3.f();
            this.h = false;
            this.i = false;
            this.j = false;
        }

        public boolean g() {
            return this.l;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("State{mTargetPosition=");
            stringBuilder.append(this.a);
            stringBuilder.append(", mData=");
            stringBuilder.append(this.b);
            stringBuilder.append(", mItemCount=");
            stringBuilder.append(this.f);
            stringBuilder.append(", mIsMeasuring=");
            stringBuilder.append(this.j);
            stringBuilder.append(", mPreviousLayoutItemCount=");
            stringBuilder.append(this.c);
            stringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            stringBuilder.append(this.d);
            stringBuilder.append(", mStructureChanged=");
            stringBuilder.append(this.g);
            stringBuilder.append(", mInPreLayout=");
            stringBuilder.append(this.h);
            stringBuilder.append(", mRunSimpleAnimations=");
            stringBuilder.append(this.k);
            stringBuilder.append(", mRunPredictiveAnimations=");
            stringBuilder.append(this.l);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }
}

