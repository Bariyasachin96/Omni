/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.util.TypedValue
 *  android.view.MotionEvent
 *  android.view.RoundedCorner
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.WindowInsets
 */
package com.google.android.material.bottomsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import c2.b;
import c2.d;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import com.google.android.material.internal.c0;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import o0.x0;
import o0.z1;
import p0.s;
import p0.v;
import p2.f;
import v0.c;
import v2.i;
import v2.o;
import z1.e;
import z1.k;
import z1.l;
import z1.m;

public class BottomSheetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V>
implements p2.b {
    public static final int n0 = z1.l.Widget_Design_BottomSheet_Modal;
    public boolean A;
    public o B;
    public boolean C;
    public final h D = new h(this, null);
    public ValueAnimator E;
    public int F;
    public int G;
    public int H;
    public float I = 0.5f;
    public int J;
    public float K = -1.0f;
    public boolean L;
    public boolean M;
    public boolean N = true;
    public boolean O = true;
    public boolean P;
    public int Q = 4;
    public int R = 4;
    public c S;
    public boolean T;
    public int U;
    public boolean V;
    public float W = 0.1f;
    public int X;
    public int Y;
    public int Z;
    public WeakReference a0;
    public WeakReference b0;
    public int c = 0;
    public WeakReference c0;
    public boolean d = true;
    public WeakReference d0;
    public boolean e = false;
    public final ArrayList e0 = new ArrayList();
    public float f;
    public VelocityTracker f0;
    public int g;
    public f g0;
    public int h;
    public int h0;
    public boolean i;
    public int i0 = -1;
    public int j;
    public boolean j0;
    public int k;
    public Map k0;
    public i l;
    public final SparseIntArray l0 = new SparseIntArray();
    public ColorStateList m;
    public final c.c m0 = new c.c(this){
        public long a;
        public final BottomSheetBehavior b;
        {
            this.b = bottomSheetBehavior;
        }

        @Override
        public int a(View view, int n3, int n4) {
            return view.getLeft();
        }

        @Override
        public int b(View view, int n3, int n4) {
            return j0.a.b(n3, this.b.r0(), this.e(view));
        }

        @Override
        public int e(View view) {
            if (this.b.j0()) {
                return this.b.Z;
            }
            return this.b.J;
        }

        @Override
        public void j(int n3) {
            if (n3 == 1 && this.b.N) {
                this.b.b1(1);
            }
        }

        @Override
        public void k(View view, int n3, int n4, int n5, int n6) {
            this.b.o0(n4);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void l(View view, float f3, float f4) {
            BottomSheetBehavior bottomSheetBehavior;
            int n3;
            block6: {
                block5: {
                    int n4;
                    int n5;
                    block11: {
                        block12: {
                            block3: {
                                block10: {
                                    block9: {
                                        int n6;
                                        block7: {
                                            block8: {
                                                block2: {
                                                    int n7;
                                                    block4: {
                                                        n3 = 6;
                                                        if (!(f4 < 0.0f)) break block2;
                                                        if (this.b.d) break block3;
                                                        n7 = view.getTop();
                                                        long l3 = SystemClock.uptimeMillis();
                                                        long l4 = this.a;
                                                        if (!this.b.g1()) break block4;
                                                        bottomSheetBehavior = this.b;
                                                        f3 = n7;
                                                        if (!bottomSheetBehavior.d1(l3 - l4, f3 * 100.0f / (float)bottomSheetBehavior.Z)) break block5;
                                                        break block3;
                                                    }
                                                    if (n7 <= this.b.H) break block3;
                                                    break block6;
                                                }
                                                bottomSheetBehavior = this.b;
                                                if (!bottomSheetBehavior.L || !bottomSheetBehavior.f1(view, f4)) break block7;
                                                if (!(Math.abs(f3) < Math.abs(f4) && f4 > (float)this.b.g) && !this.n(view)) break block8;
                                                n3 = 5;
                                                break block6;
                                            }
                                            if (!this.b.d && Math.abs(view.getTop() - this.b.r0()) >= Math.abs(view.getTop() - this.b.H)) break block6;
                                            break block3;
                                        }
                                        if (f4 == 0.0f || Math.abs(f3) > Math.abs(f4)) break block9;
                                        if (!this.b.d && Math.abs((n6 = view.getTop()) - this.b.H) < Math.abs(n6 - this.b.J) && !this.b.g1()) break block6;
                                        break block5;
                                    }
                                    n5 = view.getTop();
                                    if (!this.b.d) break block10;
                                    if (Math.abs(n5 - this.b.G) >= Math.abs(n5 - this.b.J)) break block5;
                                    break block3;
                                }
                                bottomSheetBehavior = this.b;
                                n4 = bottomSheetBehavior.H;
                                if (n5 >= n4) break block11;
                                if (n5 >= Math.abs(n5 - bottomSheetBehavior.J)) break block12;
                            }
                            n3 = 3;
                            break block6;
                        }
                        if (!this.b.g1()) break block6;
                        break block5;
                    }
                    if (Math.abs(n5 - n4) < Math.abs(n5 - this.b.J) && !this.b.g1()) break block6;
                }
                n3 = 4;
            }
            bottomSheetBehavior = this.b;
            bottomSheetBehavior.i1(view, n3, bottomSheetBehavior.h1());
        }

        @Override
        public boolean m(View view, int n3) {
            Object object = this.b;
            int n4 = ((BottomSheetBehavior)object).Q;
            if (n4 == 1) {
                return false;
            }
            if (((BottomSheetBehavior)object).j0) {
                return false;
            }
            if (n4 == 3 && ((BottomSheetBehavior)object).h0 == n3 && (object = (object = ((BottomSheetBehavior)object).d0) != null ? (View)((Reference)object).get() : null) != null && object.canScrollVertically(-1)) {
                return false;
            }
            this.a = SystemClock.uptimeMillis();
            object = this.b.a0;
            return object != null && ((Reference)object).get() == view;
        }

        public final boolean n(View object) {
            int n3 = object.getTop();
            return n3 > (((BottomSheetBehavior)object).Z + ((BottomSheetBehavior)(object = this.b)).r0()) / 2;
        }
    };
    public int n = -1;
    public int o = -1;
    public int p;
    public boolean q;
    public boolean r;
    public boolean s;
    public boolean t;
    public boolean u;
    public boolean v;
    public boolean w;
    public boolean x;
    public int y;
    public int z;

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int n3;
        this.k = context.getResources().getDimensionPixelSize(z1.e.mtrl_min_touch_target_size);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, z1.m.BottomSheetBehavior_Layout);
        int n4 = z1.m.BottomSheetBehavior_Layout_backgroundTint;
        if (typedArray.hasValue(n4)) {
            this.m = s2.c.a(context, typedArray, n4);
        }
        if (typedArray.hasValue(z1.m.BottomSheetBehavior_Layout_shapeAppearance)) {
            this.B = v2.o.e(context, attributeSet, z1.c.bottomSheetStyle, n0).m();
        }
        this.m0(context);
        this.n0();
        this.K = typedArray.getDimension(z1.m.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        n4 = z1.m.BottomSheetBehavior_Layout_android_maxWidth;
        if (typedArray.hasValue(n4)) {
            this.U0(typedArray.getDimensionPixelSize(n4, -1));
        }
        if (typedArray.hasValue(n4 = z1.m.BottomSheetBehavior_Layout_android_maxHeight)) {
            this.T0(typedArray.getDimensionPixelSize(n4, -1));
        }
        if ((attributeSet = typedArray.peekValue(n3 = z1.m.BottomSheetBehavior_Layout_behavior_peekHeight)) != null && (n4 = attributeSet.data) == -1) {
            this.V0(n4);
        } else {
            this.V0(typedArray.getDimensionPixelSize(n3, -1));
        }
        this.S0(typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_behavior_hideable, false));
        this.Q0(typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        this.P0(typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        this.Z0(typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        this.M0(typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_behavior_draggable, true));
        this.N0(typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_behavior_draggableOnNestedScroll, true));
        this.X0(typedArray.getInt(z1.m.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        this.R0(typedArray.getFloat(z1.m.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        n4 = z1.m.BottomSheetBehavior_Layout_behavior_expandedOffset;
        attributeSet = typedArray.peekValue(n4);
        if (attributeSet != null && attributeSet.type == 16) {
            this.O0(attributeSet.data);
        } else {
            this.O0(typedArray.getDimensionPixelOffset(n4, 0));
        }
        this.Y0(typedArray.getInt(z1.m.BottomSheetBehavior_Layout_behavior_significantVelocityThreshold, 500));
        this.r = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.s = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.t = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.u = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        this.v = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_marginLeftSystemWindowInsets, false);
        this.w = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_marginRightSystemWindowInsets, false);
        this.x = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_marginTopSystemWindowInsets, false);
        this.A = typedArray.getBoolean(z1.m.BottomSheetBehavior_Layout_shouldRemoveExpandedCorners, true);
        typedArray.recycle();
        this.f = ViewConfiguration.get((Context)context).getScaledMaximumFlingVelocity();
    }

    public static /* synthetic */ int K(BottomSheetBehavior bottomSheetBehavior, int n3) {
        bottomSheetBehavior.p = n3;
        return n3;
    }

    public static /* synthetic */ int T(BottomSheetBehavior bottomSheetBehavior, int n3) {
        bottomSheetBehavior.z = n3;
        return n3;
    }

    public static /* synthetic */ int W(BottomSheetBehavior bottomSheetBehavior, int n3) {
        bottomSheetBehavior.y = n3;
        return n3;
    }

    public boolean A0() {
        return true;
    }

    @Override
    public void B(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.B(coordinatorLayout, view, parcelable.o());
        this.I0((SavedState)parcelable);
        int n3 = parcelable.e;
        if (n3 != 1 && n3 != 2) {
            this.Q = n3;
            this.R = n3;
            return;
        }
        this.Q = 4;
        this.R = 4;
    }

    public final boolean B0(View view) {
        ViewParent viewParent = view.getParent();
        return viewParent != null && viewParent.isLayoutRequested() && view.isAttachedToWindow();
    }

    @Override
    public Parcelable C(CoordinatorLayout coordinatorLayout, View view) {
        return new SavedState(super.C(coordinatorLayout, view), this);
    }

    public boolean C0() {
        return true;
    }

    public final boolean D0(CoordinatorLayout coordinatorLayout, int n3, int n4) {
        WeakReference weakReference = this.c0;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        return weakReference != null && coordinatorLayout.F((View)weakReference, n3, n4);
    }

    @Override
    public boolean E(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3, int n4) {
        boolean bl = false;
        this.U = 0;
        this.V = false;
        if ((n3 & 2) != 0) {
            bl = true;
        }
        return bl;
    }

    public final boolean E0(CoordinatorLayout coordinatorLayout, int n3, int n4) {
        WeakReference weakReference = this.d0;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        return weakReference != null && coordinatorLayout.F((View)weakReference, n3, n4);
    }

    public void F0(g g3) {
        this.e0.remove(g3);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void G(CoordinatorLayout var1_1, View var2_2, View var3_3, int var4_4) {
        block6: {
            block8: {
                block10: {
                    block9: {
                        block7: {
                            block5: {
                                var6_5 = var2_2.getTop();
                                var5_6 = this.r0();
                                var4_4 = 3;
                                if (var6_5 == var5_6) {
                                    this.b1(3);
                                    return;
                                }
                                if (this.C0() && ((var1_1 = this.d0) == null || var3_3 != var1_1.get() || !this.V)) {
                                    return;
                                }
                                if (this.U <= 0) break block5;
                                if (this.d || var2_2.getTop() <= this.H) break block6;
                                ** GOTO lbl37
                            }
                            if (!this.L || !this.f1(var2_2, this.u0())) break block7;
                            var4_4 = 5;
                            break block6;
                        }
                        if (this.U != 0) break block8;
                        var6_5 = var2_2.getTop();
                        if (!this.d) break block9;
                        if (Math.abs(var6_5 - this.G) >= Math.abs(var6_5 - this.J)) ** GOTO lbl-1000
                        break block6;
                    }
                    var5_6 = this.H;
                    if (var6_5 >= var5_6) break block10;
                    if (var6_5 < Math.abs(var6_5 - this.J)) break block6;
                    if (!this.g1()) ** GOTO lbl37
                    ** GOTO lbl-1000
                }
                if (Math.abs(var6_5 - var5_6) >= Math.abs(var6_5 - this.J)) ** GOTO lbl-1000
                ** GOTO lbl37
            }
            if (this.d) lbl-1000:
            // 5 sources

            {
                while (true) {
                    var4_4 = 4;
                    break;
                }
            } else {
                if (Math.abs((var4_4 = var2_2.getTop()) - this.H) >= Math.abs(var4_4 - this.J)) ** continue;
lbl37:
                // 4 sources

                var4_4 = 6;
            }
        }
        this.i1(var2_2, var4_4, false);
        this.V = false;
    }

    public final void G0(View view, s.a a4, int n3) {
        x0.d0(view, a4, null, this.l0(n3));
    }

    @Override
    public boolean H(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int n3 = motionEvent.getActionMasked();
        if (this.Q == 1 && n3 == 0) {
            return true;
        }
        if (this.e1()) {
            this.S.F(motionEvent);
        }
        if (n3 == 0) {
            this.H0();
        }
        if (this.f0 == null) {
            this.f0 = VelocityTracker.obtain();
        }
        this.f0.addMovement(motionEvent);
        if (this.e1() && n3 == 2 && !this.T && Math.abs((float)this.i0 - motionEvent.getY()) > (float)this.S.z()) {
            this.S.b(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return this.T ^ true;
    }

    public final void H0() {
        this.h0 = -1;
        this.i0 = -1;
        VelocityTracker velocityTracker = this.f0;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f0 = null;
        }
    }

    public final void I0(SavedState savedState) {
        block7: {
            block6: {
                int n3 = this.c;
                if (n3 == 0) break block6;
                if (n3 == -1 || (n3 & 1) == 1) {
                    this.h = savedState.f;
                }
                if (n3 == -1 || (n3 & 2) == 2) {
                    this.d = savedState.g;
                }
                if (n3 == -1 || (n3 & 4) == 4) {
                    this.L = savedState.h;
                }
                if (n3 == -1 || (n3 & 8) == 8) break block7;
            }
            return;
        }
        this.M = savedState.i;
    }

    public final void J0(View view, Runnable runnable) {
        if (this.B0(view)) {
            view.post(runnable);
            return;
        }
        runnable.run();
    }

    public void K0(View view) {
        WeakReference weakReference;
        if (view == null && (weakReference = this.b0) != null) {
            this.k0((View)weakReference.get(), 1);
            this.b0 = null;
            return;
        }
        this.b0 = new WeakReference<View>(view);
        this.k1(view, 1);
    }

    public void L0(BottomSheetDragHandleView object) {
        object = object != null ? new WeakReference<BottomSheetDragHandleView>((BottomSheetDragHandleView)((Object)object)) : null;
        this.c0 = object;
    }

    public void M0(boolean bl) {
        this.N = bl;
    }

    public void N0(boolean bl) {
        this.O = bl;
    }

    public void O0(int n3) {
        if (n3 >= 0) {
            this.F = n3;
            this.l1(this.Q, true);
            return;
        }
        throw new IllegalArgumentException("offset must be greater than or equal to 0");
    }

    public void P0(boolean bl) {
        if (this.d == bl) {
            return;
        }
        this.d = bl;
        if (this.a0 != null) {
            this.d0();
        }
        int n3 = this.d && this.Q == 6 ? 3 : this.Q;
        this.b1(n3);
        this.l1(this.Q, true);
        this.j1();
    }

    public void Q0(boolean bl) {
        this.q = bl;
    }

    public void R0(float f3) {
        if (!(f3 <= 0.0f) && !(f3 >= 1.0f)) {
            this.I = f3;
            if (this.a0 != null) {
                this.f0();
            }
            return;
        }
        throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
    }

    public void S0(boolean bl) {
        if (this.L != bl) {
            this.L = bl;
            if (!bl && this.Q == 5) {
                this.a1(4);
            }
            this.j1();
        }
    }

    public void T0(int n3) {
        this.o = n3;
    }

    public void U0(int n3) {
        this.n = n3;
    }

    public void V0(int n3) {
        this.W0(n3, false);
    }

    public final void W0(int n3, boolean bl) {
        block4: {
            block5: {
                block3: {
                    block2: {
                        if (n3 != -1) break block2;
                        if (this.i) break block3;
                        this.i = true;
                        break block4;
                    }
                    if (this.i || this.h != n3) break block5;
                }
                return;
            }
            this.i = false;
            this.h = Math.max(0, n3);
        }
        this.n1(bl);
    }

    public void X0(int n3) {
        this.c = n3;
    }

    public void Y0(int n3) {
        this.g = n3;
    }

    public void Z0(boolean bl) {
        this.M = bl;
    }

    @Override
    public void a() {
        Object object = this.g0;
        if (object == null) {
            return;
        }
        object = ((p2.a)object).c();
        int n3 = 4;
        if (object != null && Build.VERSION.SDK_INT >= 34) {
            if (this.L) {
                this.g0.h((androidx.activity.b)object, (Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                    public final BottomSheetBehavior a;
                    {
                        this.a = bottomSheetBehavior;
                    }

                    public void onAnimationEnd(Animator object) {
                        this.a.b1(5);
                        object = this.a.a0;
                        if (object != null && ((Reference)object).get() != null) {
                            ((View)this.a.a0.get()).requestLayout();
                        }
                    }
                });
                return;
            }
            this.g0.i((androidx.activity.b)object, null);
            this.a1(4);
            return;
        }
        if (this.L) {
            n3 = 5;
        }
        this.a1(n3);
    }

    public void a1(int n3) {
        if (n3 != 1 && n3 != 2) {
            if (!this.L && n3 == 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot set state: ");
                stringBuilder.append(n3);
                Log.w((String)"BottomSheetBehavior", (String)stringBuilder.toString());
                return;
            }
            int n4 = n3 == 6 && this.d && this.t0(n3) <= this.G ? 3 : n3;
            WeakReference weakReference = this.a0;
            if (weakReference != null && weakReference.get() != null) {
                weakReference = (View)this.a0.get();
                this.J0((View)weakReference, new Runnable(this, (View)weakReference, n4){
                    public final View c;
                    public final int d;
                    public final BottomSheetBehavior e;
                    {
                        this.e = bottomSheetBehavior;
                        this.c = view;
                        this.d = n3;
                    }

                    @Override
                    public void run() {
                        this.e.i1(this.c, this.d, false);
                    }
                });
                return;
            }
            this.b1(n3);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("STATE_");
        String string = n3 == 1 ? "DRAGGING" : "SETTLING";
        stringBuilder.append(string);
        stringBuilder.append(" should not be set externally.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void b(androidx.activity.b b3) {
        f f3 = this.g0;
        if (f3 == null) {
            return;
        }
        f3.j(b3);
    }

    public final int b0(View view, int n3, int n4) {
        return x0.c(view, view.getResources().getString(n3), this.l0(n4));
    }

    public void b1(int n3) {
        WeakReference weakReference;
        block9: {
            block8: {
                if (this.Q == n3) break block8;
                this.Q = n3;
                if (n3 == 4 || n3 == 3 || n3 == 6 || this.L && n3 == 5) {
                    this.R = n3;
                }
                if ((weakReference = this.a0) != null && (weakReference = (View)weakReference.get()) != null) break block9;
            }
            return;
        }
        int n4 = 0;
        if (n3 == 3) {
            this.m1(true);
        } else if (n3 == 6 || n3 == 5 || n3 == 4) {
            this.m1(false);
        }
        this.l1(n3, true);
        while (n4 < this.e0.size()) {
            ((g)this.e0.get(n4)).c((View)weakReference, n3);
            ++n4;
        }
        this.j1();
    }

    @Override
    public void c(androidx.activity.b b3) {
        f f3 = this.g0;
        if (f3 == null) {
            return;
        }
        f3.l(b3);
    }

    public void c0(g g3) {
        if (!this.e0.contains(g3)) {
            this.e0.add(g3);
        }
    }

    public final void c1(View view) {
        boolean bl = Build.VERSION.SDK_INT >= 29 && !this.y0() && !this.i;
        if (!(this.r || this.s || this.t || this.v || this.w || this.x || bl)) {
            return;
        }
        com.google.android.material.internal.c0.f(view, new c0.d(this, bl){
            public final boolean a;
            public final BottomSheetBehavior b;
            {
                this.b = bottomSheetBehavior;
                this.a = bl;
            }

            @Override
            public z1 a(View view, z1 z12, c0.e e3) {
                int n3;
                int n4;
                int n5;
                g0.b b3 = z12.f(z1.m.e());
                g0.b b4 = z12.f(z1.m.d());
                BottomSheetBehavior.T(this.b, b3.b);
                boolean bl = com.google.android.material.internal.c0.m(view);
                int n6 = view.getPaddingBottom();
                int n7 = view.getPaddingLeft();
                int n8 = view.getPaddingRight();
                if (this.b.r) {
                    BottomSheetBehavior.W(this.b, z12.i());
                    n6 = e3.d + this.b.y;
                }
                if (this.b.s) {
                    n5 = bl ? e3.c : e3.a;
                    n7 = n5 + b3.a;
                }
                if (this.b.t) {
                    n5 = bl ? e3.a : e3.c;
                    n8 = n5 + b3.c;
                }
                e3 = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
                bl = this.b.v;
                int n9 = 1;
                if (bl && (n4 = ((ViewGroup.MarginLayoutParams)e3).leftMargin) != (n5 = b3.a)) {
                    ((ViewGroup.MarginLayoutParams)e3).leftMargin = n5;
                    n4 = 1;
                } else {
                    n4 = 0;
                }
                n5 = n4;
                if (this.b.w) {
                    int n10 = ((ViewGroup.MarginLayoutParams)e3).rightMargin;
                    n3 = b3.c;
                    n5 = n4;
                    if (n10 != n3) {
                        ((ViewGroup.MarginLayoutParams)e3).rightMargin = n3;
                        n5 = 1;
                    }
                }
                if (this.b.x && (n4 = ((ViewGroup.MarginLayoutParams)e3).topMargin) != (n3 = b3.b)) {
                    ((ViewGroup.MarginLayoutParams)e3).topMargin = n3;
                    n5 = n9;
                }
                if (n5 != 0) {
                    view.setLayoutParams((ViewGroup.LayoutParams)e3);
                }
                view.setPadding(n7, view.getPaddingTop(), n8, n6);
                if (this.a) {
                    BottomSheetBehavior.K(this.b, b4.d);
                }
                if (!this.b.r && !this.a) {
                    return z12;
                }
                this.b.n1(false);
                return z12;
            }
        });
    }

    @Override
    public void d() {
        f f3 = this.g0;
        if (f3 == null) {
            return;
        }
        f3.f();
    }

    public final void d0() {
        int n3 = this.h0();
        if (this.d) {
            this.J = Math.max(this.Z - n3, this.G);
            return;
        }
        this.J = this.Z - n3;
    }

    public boolean d1(long l3, float f3) {
        return false;
    }

    public final float e0(float f3, RoundedCorner roundedCorner) {
        float f4;
        if (roundedCorner != null && (f4 = (float)b.a(roundedCorner)) > 0.0f && f3 > 0.0f) {
            return f4 / f3;
        }
        return 0.0f;
    }

    public final boolean e1() {
        return this.S != null && (this.N || this.Q == 1);
    }

    public final void f0() {
        this.H = (int)((float)this.Z * (1.0f - this.I));
    }

    public boolean f1(View view, float f3) {
        if (this.M) {
            return true;
        }
        if (!this.A0()) {
            return false;
        }
        if (view.getTop() < this.J) {
            return false;
        }
        int n3 = this.h0();
        return Math.abs((float)view.getTop() + f3 * this.W - (float)this.J) / (float)n3 > 0.5f;
    }

    public final float g0() {
        WeakReference weakReference;
        if (this.l != null && (weakReference = this.a0) != null && weakReference.get() != null && Build.VERSION.SDK_INT >= 31) {
            weakReference = (View)this.a0.get();
            if (this.v0() && (weakReference = weakReference.getRootWindowInsets()) != null) {
                return Math.max(this.e0(this.l.P(), c2.a.a((WindowInsets)weakReference, 0)), this.e0(this.l.Q(), c2.a.a((WindowInsets)weakReference, 1)));
            }
        }
        return 0.0f;
    }

    public boolean g1() {
        return false;
    }

    public final int h0() {
        int n3;
        if (this.i) {
            return Math.min(Math.max(this.j, this.Z - this.Y * 9 / 16), this.X) + this.y;
        }
        if (!this.q && !this.r && (n3 = this.p) > 0) {
            return Math.max(this.h, n3 + this.k);
        }
        return this.h + this.y;
    }

    public boolean h1() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final float i0(int n3) {
        float f3;
        float f4;
        int n4 = this.J;
        if (n3 <= n4 && n4 != this.r0()) {
            n4 = this.J;
            f4 = n4 - n3;
            f3 = n4 - this.r0();
            return f4 / f3;
        }
        n4 = this.J;
        f4 = n4 - n3;
        f3 = this.Z - n4;
        return f4 / f3;
    }

    public final void i1(View view, int n3, boolean bl) {
        int n4 = this.t0(n3);
        c c3 = this.S;
        if (c3 != null && (bl ? c3.O(view.getLeft(), n4) : c3.Q(view, view.getLeft(), n4))) {
            this.b1(2);
            this.l1(n3, true);
            this.D.c(n3);
            return;
        }
        this.b1(n3);
    }

    public final boolean j0() {
        return this.z0() && this.A0();
    }

    public final void j1() {
        WeakReference weakReference = this.a0;
        if (weakReference != null) {
            this.k1((View)weakReference.get(), 0);
        }
        if ((weakReference = this.b0) != null) {
            this.k1((View)weakReference.get(), 1);
        }
    }

    @Override
    public void k(CoordinatorLayout.e e3) {
        super.k(e3);
        this.a0 = null;
        this.S = null;
        this.g0 = null;
    }

    public final void k0(View view, int n3) {
        if (view != null) {
            x0.b0(view, 524288);
            x0.b0(view, 262144);
            x0.b0(view, 0x100000);
            int n4 = this.l0.get(n3, -1);
            if (n4 != -1) {
                x0.b0(view, n4);
                this.l0.delete(n3);
            }
        }
    }

    public final void k1(View view, int n3) {
        int n4;
        block8: {
            block9: {
                block10: {
                    block7: {
                        if (view == null) break block7;
                        this.k0(view, n3);
                        boolean bl = this.d;
                        n4 = 6;
                        if (!bl && this.Q != 6) {
                            this.l0.put(n3, this.b0(view, z1.k.bottomsheet_action_expand_halfway, 6));
                        }
                        if (this.L && this.A0() && this.Q != 5) {
                            this.G0(view, s.a.y, 5);
                        }
                        if ((n3 = this.Q) == 3) break block8;
                        if (n3 == 4) break block9;
                        if (n3 == 6) break block10;
                    }
                    return;
                }
                this.G0(view, s.a.x, 4);
                this.G0(view, s.a.w, 3);
                return;
            }
            if (this.d) {
                n4 = 3;
            }
            this.G0(view, s.a.w, n4);
            return;
        }
        if (this.d) {
            n4 = 4;
        }
        this.G0(view, s.a.x, n4);
    }

    public final v l0(int n3) {
        return new v(this, n3){
            public final int a;
            public final BottomSheetBehavior b;
            {
                this.b = bottomSheetBehavior;
                this.a = n3;
            }

            @Override
            public boolean a(View view, v.a a4) {
                this.b.a1(this.a);
                return true;
            }
        };
    }

    public final void l1(int n3, boolean bl) {
        boolean bl2;
        if (n3 != 2 && this.C != (bl2 = this.w0()) && this.l != null) {
            Object object;
            this.C = bl2;
            float f3 = 1.0f;
            if (bl && (object = this.E) != null) {
                if (object.isRunning()) {
                    this.E.reverse();
                    return;
                }
                float f4 = this.l.E();
                if (bl2) {
                    f3 = this.g0();
                }
                this.E.setFloatValues(new float[]{f4, f3});
                this.E.start();
                return;
            }
            object = this.E;
            if (object != null && object.isRunning()) {
                this.E.cancel();
            }
            object = this.l;
            if (this.C) {
                f3 = this.g0();
            }
            ((i)object).j0(f3);
        }
    }

    public final void m0(Context context) {
        i i3;
        if (this.B == null) {
            return;
        }
        this.l = i3 = new i(this.B);
        i3.W(context);
        i3 = this.m;
        if (i3 != null) {
            this.l.i0((ColorStateList)i3);
            return;
        }
        i3 = new TypedValue();
        context.getTheme().resolveAttribute(0x1010031, (TypedValue)i3, true);
        this.l.setTint(((TypedValue)i3).data);
    }

    public final void m1(boolean bl) {
        block7: {
            int n3;
            CoordinatorLayout coordinatorLayout;
            WeakReference weakReference;
            block8: {
                weakReference = this.a0;
                if (weakReference == null || !((weakReference = ((View)weakReference.get()).getParent()) instanceof CoordinatorLayout)) break block7;
                coordinatorLayout = (CoordinatorLayout)((Object)weakReference);
                n3 = coordinatorLayout.getChildCount();
                if (!bl) break block8;
                if (this.k0 != null) break block7;
                this.k0 = new HashMap(n3);
            }
            for (int i3 = 0; i3 < n3; ++i3) {
                Map map;
                weakReference = coordinatorLayout.getChildAt(i3);
                if (weakReference == this.a0.get()) continue;
                if (bl) {
                    this.k0.put(weakReference, weakReference.getImportantForAccessibility());
                    if (!this.e) continue;
                    weakReference.setImportantForAccessibility(4);
                    continue;
                }
                if (!this.e || (map = this.k0) == null || !map.containsKey(weakReference)) continue;
                weakReference.setImportantForAccessibility((Integer)this.k0.get(weakReference));
            }
            if (!bl) {
                this.k0 = null;
                return;
            }
            if (this.e) {
                ((View)this.a0.get()).sendAccessibilityEvent(8);
            }
        }
    }

    @Override
    public void n() {
        super.n();
        this.a0 = null;
        this.S = null;
        this.g0 = null;
    }

    public final void n0() {
        ValueAnimator valueAnimator;
        this.E = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.g0(), 1.0f});
        valueAnimator.setDuration(500L);
        this.E.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this){
            public final BottomSheetBehavior a;
            {
                this.a = bottomSheetBehavior;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f3 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                if (this.a.l != null) {
                    this.a.l.j0(f3);
                }
            }
        });
    }

    public final void n1(boolean bl) {
        if (this.a0 != null) {
            View view;
            this.d0();
            if (this.Q == 4 && (view = (View)this.a0.get()) != null) {
                if (bl) {
                    this.a1(4);
                    return;
                }
                view.requestLayout();
            }
        }
    }

    @Override
    public boolean o(CoordinatorLayout coordinatorLayout, View object, MotionEvent motionEvent) {
        if (object.isShown() && this.N) {
            int n3;
            int n4 = motionEvent.getActionMasked();
            if (n4 == 0) {
                this.H0();
            }
            if (this.f0 == null) {
                this.f0 = VelocityTracker.obtain();
            }
            this.f0.addMovement(motionEvent);
            if (n4 != 0) {
                if (n4 == 1 || n4 == 3) {
                    this.j0 = false;
                    this.h0 = -1;
                    if (this.T) {
                        this.T = false;
                        return false;
                    }
                }
            } else {
                int n5 = (int)motionEvent.getX();
                this.i0 = n3 = (int)motionEvent.getY();
                if (this.Q != 2 && this.E0(coordinatorLayout, n5, n3)) {
                    this.h0 = motionEvent.getPointerId(motionEvent.getActionIndex());
                    if (!this.D0(coordinatorLayout, n5, this.i0)) {
                        this.j0 = true;
                    }
                }
                boolean bl = this.h0 == -1 && !coordinatorLayout.F((View)object, n5, this.i0);
                this.T = bl;
            }
            if (!this.T && (object = this.S) != null && ((c)object).P(motionEvent)) {
                return true;
            }
            object = this.d0;
            object = object != null ? (View)((Reference)object).get() : null;
            return n4 == 2 && object != null && !this.T && this.Q != 1 && !coordinatorLayout.F((View)object, (int)motionEvent.getX(), (int)motionEvent.getY()) && this.S != null && (n3 = this.i0) != -1 && Math.abs((float)n3 - motionEvent.getY()) > (float)this.S.z();
        }
        this.T = true;
        return false;
    }

    public void o0(int n3) {
        View view = (View)this.a0.get();
        if (view != null && !this.e0.isEmpty()) {
            float f3 = this.i0(n3);
            for (n3 = 0; n3 < this.e0.size(); ++n3) {
                ((g)this.e0.get(n3)).b(view, f3);
            }
        }
    }

    @Override
    public boolean p(CoordinatorLayout coordinatorLayout, View view, int n3) {
        int n4;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.a0 == null) {
            this.j = coordinatorLayout.getResources().getDimensionPixelSize(z1.e.design_bottom_sheet_peek_height_min);
            this.c1(view);
            x0.y0(view, new d(view));
            this.a0 = new WeakReference<View>(view);
            this.g0 = new f(view);
            i i3 = this.l;
            if (i3 != null) {
                float f3;
                view.setBackground((Drawable)i3);
                i3 = this.l;
                float f4 = f3 = this.K;
                if (f3 == -1.0f) {
                    f4 = view.getElevation();
                }
                i3.h0(f4);
            } else {
                i3 = this.m;
                if (i3 != null) {
                    x0.l0(view, (ColorStateList)i3);
                }
            }
            this.j1();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
        }
        if (this.S == null) {
            this.S = v0.c.o(coordinatorLayout, this.m0);
        }
        int n5 = view.getTop();
        coordinatorLayout.M(view, n3);
        this.Y = coordinatorLayout.getWidth();
        this.Z = coordinatorLayout.getHeight();
        this.X = n4 = view.getHeight();
        n3 = this.Z;
        int n6 = this.z;
        if (n3 - n4 < n6) {
            if (this.u) {
                n4 = this.o;
                if (n4 != -1) {
                    n3 = Math.min(n3, n4);
                }
                this.X = n3;
            } else {
                n3 -= n6;
                n4 = this.o;
                if (n4 != -1) {
                    n3 = Math.min(n3, n4);
                }
                this.X = n3;
            }
        }
        n6 = this.Z;
        n4 = this.X;
        n3 = 0;
        this.G = Math.max(0, n6 - n4);
        this.f0();
        this.d0();
        n4 = this.Q;
        if (n4 == 3) {
            x0.S(view, this.r0());
        } else if (n4 == 6) {
            x0.S(view, this.H);
        } else if (this.L && n4 == 5) {
            x0.S(view, this.Z);
        } else if (n4 == 4) {
            x0.S(view, this.J);
        } else if (n4 == 1 || n4 == 2) {
            x0.S(view, n5 - view.getTop());
        }
        this.l1(this.Q, false);
        this.d0 = new WeakReference<View>(this.p0(view));
        while (n3 < this.e0.size()) {
            ((g)this.e0.get(n3)).a(view);
            ++n3;
        }
        return true;
    }

    public View p0(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        if (view.isNestedScrollingEnabled()) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n3 = viewGroup.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                view = this.p0(viewGroup.getChildAt(i3));
                if (view == null) continue;
                return view;
            }
        }
        return null;
    }

    @Override
    public boolean q(CoordinatorLayout coordinatorLayout, View view, int n3, int n4, int n5, int n6) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(this.q0(n3, coordinatorLayout.getPaddingLeft() + coordinatorLayout.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n4, this.n, marginLayoutParams.width), this.q0(n5, coordinatorLayout.getPaddingTop() + coordinatorLayout.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n6, this.o, marginLayoutParams.height));
        return true;
    }

    public final int q0(int n3, int n4, int n5, int n6) {
        n4 = ViewGroup.getChildMeasureSpec((int)n3, (int)n4, (int)n6);
        if (n5 == -1) {
            return n4;
        }
        n3 = View.MeasureSpec.getMode((int)n4);
        n4 = View.MeasureSpec.getSize((int)n4);
        if (n3 != 0x40000000) {
            if (n4 != 0) {
                n5 = Math.min(n4, n5);
            }
            return View.MeasureSpec.makeMeasureSpec((int)n5, (int)Integer.MIN_VALUE);
        }
        return View.MeasureSpec.makeMeasureSpec((int)Math.min(n4, n5), (int)0x40000000);
    }

    public int r0() {
        if (this.d) {
            return this.G;
        }
        int n3 = this.F;
        int n4 = this.u ? 0 : this.z;
        return Math.max(n3, n4);
    }

    @Override
    public boolean s(CoordinatorLayout coordinatorLayout, View view, View view2, float f3, float f4) {
        WeakReference weakReference;
        return this.C0() && (weakReference = this.d0) != null && view2 == weakReference.get() && (this.Q != 3 && !this.P || super.s(coordinatorLayout, view, view2, f3, f4));
    }

    public int s0() {
        return this.Q;
    }

    public final int t0(int n3) {
        if (n3 != 3) {
            if (n3 != 4) {
                if (n3 != 5) {
                    if (n3 == 6) {
                        return this.H;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid state to get top offset: ");
                    stringBuilder.append(n3);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                return this.Z;
            }
            return this.J;
        }
        return this.r0();
    }

    @Override
    public void u(CoordinatorLayout object, View view, View view2, int n3, int n4, int[] nArray, int n5) {
        block8: {
            block10: {
                block5: {
                    block9: {
                        block6: {
                            block7: {
                                if (n5 == 1) break block5;
                                object = this.d0;
                                object = object != null ? (View)((Reference)object).get() : null;
                                if (this.C0() && view2 != object) break block5;
                                n5 = view.getTop();
                                n3 = n5 - n4;
                                if (n4 <= 0) break block6;
                                if (!this.V && !this.O && view2 == object && view2.canScrollVertically(1)) {
                                    this.P = true;
                                    return;
                                }
                                if (n3 >= this.r0()) break block7;
                                nArray[1] = n3 = n5 - this.r0();
                                x0.S(view, -n3);
                                this.b1(3);
                                break block8;
                            }
                            if (!this.N) break block5;
                            nArray[1] = n4;
                            x0.S(view, -n4);
                            this.b1(1);
                            break block8;
                        }
                        if (n4 >= 0) break block8;
                        boolean bl = view2.canScrollVertically(-1);
                        if (!this.V && !this.O && view2 == object && bl) {
                            this.P = true;
                            return;
                        }
                        if (bl) break block8;
                        if (n3 <= this.J || this.j0()) break block9;
                        nArray[1] = n3 = n5 - this.J;
                        x0.S(view, -n3);
                        this.b1(4);
                        break block8;
                    }
                    if (this.N) break block10;
                }
                return;
            }
            nArray[1] = n4;
            x0.S(view, -n4);
            this.b1(1);
        }
        this.o0(view.getTop());
        this.U = n4;
        this.V = true;
        this.P = false;
    }

    public final float u0() {
        VelocityTracker velocityTracker = this.f0;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.f);
        return this.f0.getYVelocity(this.h0);
    }

    public final boolean v0() {
        Object object = this.a0;
        if (object != null && ((Reference)object).get() != null) {
            object = new int[2];
            ((View)this.a0.get()).getLocationOnScreen((int[])object);
            if (object[1] == false) {
                return true;
            }
        }
        return false;
    }

    public final boolean w0() {
        return this.Q == 3 && (this.A || this.v0());
    }

    @Override
    public void x(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
    }

    public boolean x0() {
        return this.d;
    }

    public boolean y0() {
        return this.q;
    }

    public boolean z0() {
        return this.L;
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
        public final int e;
        public int f;
        public boolean g;
        public boolean h;
        public boolean i;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readInt();
            this.f = parcel.readInt();
            int n3 = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n3 == 1;
            this.g = bl2;
            bl2 = parcel.readInt() == 1;
            this.h = bl2;
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.i = bl2;
        }

        public SavedState(Parcelable parcelable, BottomSheetBehavior bottomSheetBehavior) {
            super(parcelable);
            this.e = bottomSheetBehavior.Q;
            this.f = bottomSheetBehavior.h;
            this.g = bottomSheetBehavior.d;
            this.h = bottomSheetBehavior.L;
            this.i = bottomSheetBehavior.M;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f);
            parcel.writeInt(this.g ? 1 : 0);
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
        }
    }

    public static abstract class g {
        public void a(View view) {
        }

        public abstract void b(View var1, float var2);

        public abstract void c(View var1, int var2);
    }

    public class h {
        public int a;
        public boolean b;
        public final Runnable c;
        public final BottomSheetBehavior d;

        public h(BottomSheetBehavior bottomSheetBehavior) {
            this.d = bottomSheetBehavior;
            this.c = new Runnable(this){
                public final h c;
                {
                    this.c = h3;
                }

                @Override
                public void run() {
                    com.google.android.material.bottomsheet.BottomSheetBehavior$h.a(this.c, false);
                    Object object = this.c.d.S;
                    if (object != null && ((c)object).m(true)) {
                        object = this.c;
                        ((h)object).c(((h)object).a);
                        return;
                    }
                    h h3 = this.c;
                    object = h3.d;
                    if (((BottomSheetBehavior)object).Q == 2) {
                        ((BottomSheetBehavior)object).b1(h3.a);
                    }
                }
            };
        }

        public /* synthetic */ h(BottomSheetBehavior bottomSheetBehavior, a a4) {
            this(bottomSheetBehavior);
        }

        public static /* synthetic */ boolean a(h h3, boolean bl) {
            h3.b = bl;
            return bl;
        }

        public void c(int n3) {
            WeakReference weakReference = this.d.a0;
            if (weakReference != null && weakReference.get() != null) {
                this.a = n3;
                if (!this.b) {
                    ((View)this.d.a0.get()).postOnAnimation(this.c);
                    this.b = true;
                }
            }
        }
    }
}

