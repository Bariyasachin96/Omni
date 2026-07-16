/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityRecord
 *  android.view.animation.AnimationUtils
 *  android.widget.EdgeEffect
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.OverScroller
 *  android.widget.ScrollView
 */
package androidx.core.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.widget.f;
import java.util.ArrayList;
import o0.a0;
import o0.b0;
import o0.d0;
import o0.e0;
import o0.o;
import o0.p;
import o0.x0;
import o0.z;
import p0.s;
import p0.u;

public class NestedScrollView
extends FrameLayout
implements d0,
a0 {
    public static final float F = (float)(Math.log(0.78) / Math.log(0.9));
    public static final a G = new a();
    public static final int[] H = new int[]{16843130};
    public final b0 A;
    public float B;
    public d C;
    public final c D;
    public o E;
    public final float c;
    public long d;
    public final Rect e = new Rect();
    public OverScroller f;
    public EdgeEffect g;
    public EdgeEffect h;
    public int i;
    public boolean j = true;
    public boolean k = false;
    public View l = null;
    public boolean m = false;
    public VelocityTracker n;
    public boolean o;
    public boolean p = true;
    public int q;
    public int r;
    public int s;
    public int t = -1;
    public final int[] u = new int[2];
    public final int[] v = new int[2];
    public int w;
    public int x;
    public SavedState y;
    public final e0 z;

    public NestedScrollView(Context context) {
        this(context, null);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, b0.a.nestedScrollViewStyle);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        c c3;
        this.D = c3 = new c(this);
        this.E = new o(this.getContext(), c3);
        this.g = androidx.core.widget.f.a(context, attributeSet);
        this.h = androidx.core.widget.f.a(context, attributeSet);
        this.c = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        this.B();
        context = context.obtainStyledAttributes(attributeSet, H, n3, 0);
        this.setFillViewport(context.getBoolean(0, false));
        context.recycle();
        this.z = new e0((ViewGroup)this);
        this.A = new b0((View)this);
        this.setNestedScrollingEnabled(true);
        x0.h0((View)this, G);
    }

    public static boolean F(View view, View view2) {
        if (view == view2) {
            return true;
        }
        return (view = view.getParent()) instanceof ViewGroup && NestedScrollView.F(view, view2);
    }

    private static int i(int n3, int n4, int n5) {
        if (n4 < n5 && n3 >= 0) {
            if (n4 + n3 > n5) {
                return n5 - n4;
            }
            return n3;
        }
        return 0;
    }

    public final void A() {
        VelocityTracker velocityTracker = this.n;
        if (velocityTracker == null) {
            this.n = VelocityTracker.obtain();
            return;
        }
        velocityTracker.clear();
    }

    public final void B() {
        this.f = new OverScroller(this.getContext());
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        this.q = viewConfiguration.getScaledTouchSlop();
        this.r = viewConfiguration.getScaledMinimumFlingVelocity();
        this.s = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    public final void C() {
        if (this.n == null) {
            this.n = VelocityTracker.obtain();
        }
    }

    public final void D(int n3, int n4) {
        this.i = n3;
        this.t = n4;
        this.X(2, 0);
    }

    public final boolean E(View view) {
        return this.G(view, 0, this.getHeight()) ^ true;
    }

    public final boolean G(View view, int n3, int n4) {
        view.getDrawingRect(this.e);
        this.offsetDescendantRectToMyCoords(view, this.e);
        return this.e.bottom + n3 >= this.getScrollY() && this.e.top - n3 <= this.getScrollY() + n4;
    }

    public final void H(int n3, int n4, int[] nArray) {
        int n5 = this.getScrollY();
        this.scrollBy(0, n3);
        n5 = this.getScrollY() - n5;
        if (nArray != null) {
            nArray[1] = nArray[1] + n5;
        }
        this.A.e(0, n5, 0, n3 - n5, null, n4, nArray);
    }

    public final void I(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n3) == this.t) {
            n3 = n3 == 0 ? 1 : 0;
            this.i = (int)motionEvent.getY(n3);
            this.t = motionEvent.getPointerId(n3);
            motionEvent = this.n;
            if (motionEvent != null) {
                motionEvent.clear();
            }
        }
    }

    public boolean J(int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, boolean bl) {
        boolean bl2;
        int n11 = this.getOverScrollMode();
        boolean bl3 = this.computeHorizontalScrollRange() > this.computeHorizontalScrollExtent();
        boolean bl4 = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent();
        bl3 = n11 == 0 || n11 == 1 && bl3;
        bl4 = n11 == 0 || n11 == 1 && bl4;
        n5 += n3;
        n3 = !bl3 ? 0 : n9;
        n6 += n4;
        n4 = !bl4 ? 0 : n10;
        n9 = -n3;
        n3 += n7;
        n7 = -n4;
        n4 += n8;
        if (n5 > n3) {
            bl = true;
        } else if (n5 < n9) {
            bl = true;
            n3 = n9;
        } else {
            bl = false;
            n3 = n5;
        }
        if (n6 > n4) {
            bl2 = true;
        } else if (n6 < n7) {
            bl2 = true;
            n4 = n7;
        } else {
            bl2 = false;
            n4 = n6;
        }
        if (bl2 && !this.y(1)) {
            this.f.springBack(n3, n4, 0, 0, 0, this.getScrollRange());
        }
        this.onOverScrolled(n3, n4, bl, bl2);
        return bl || bl2;
        {
        }
    }

    public boolean K(int n3) {
        Rect rect;
        int n4 = n3 == 130 ? 1 : 0;
        int n5 = this.getHeight();
        if (n4 != 0) {
            this.e.top = this.getScrollY() + n5;
            n4 = this.getChildCount();
            if (n4 > 0) {
                View view = this.getChildAt(n4 - 1);
                rect = (FrameLayout.LayoutParams)view.getLayoutParams();
                n4 = view.getBottom() + rect.bottomMargin + this.getPaddingBottom();
                rect = this.e;
                if (rect.top + n5 > n4) {
                    rect.top = n4 - n5;
                }
            }
        } else {
            this.e.top = this.getScrollY() - n5;
            rect = this.e;
            if (rect.top < 0) {
                rect.top = 0;
            }
        }
        rect = this.e;
        n4 = rect.top;
        rect.bottom = n5 += n4;
        return this.O(n3, n4, n5);
    }

    public final void L() {
        VelocityTracker velocityTracker = this.n;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.n = null;
        }
    }

    public final int M(int n3, float f3) {
        float f4 = f3 / (float)this.getWidth();
        float f5 = (float)n3 / (float)this.getHeight();
        float f6 = androidx.core.widget.f.b(this.g);
        f3 = 0.0f;
        if (f6 != 0.0f) {
            f3 = f4 = -androidx.core.widget.f.d(this.g, -f5, f4);
            if (androidx.core.widget.f.b(this.g) == 0.0f) {
                this.g.onRelease();
                f3 = f4;
            }
        } else if (androidx.core.widget.f.b(this.h) != 0.0f) {
            f3 = f4 = androidx.core.widget.f.d(this.h, f5, 1.0f - f4);
            if (androidx.core.widget.f.b(this.h) == 0.0f) {
                this.h.onRelease();
                f3 = f4;
            }
        }
        n3 = Math.round(f3 * (float)this.getHeight());
        if (n3 != 0) {
            this.invalidate();
        }
        return n3;
    }

    public final void N(boolean bl) {
        if (bl) {
            this.X(2, 1);
        } else {
            this.Z(1);
        }
        this.x = this.getScrollY();
        this.postInvalidateOnAnimation();
    }

    public final boolean O(int n3, int n4, int n5) {
        View view;
        int n6 = this.getHeight();
        int n7 = this.getScrollY();
        n6 += n7;
        boolean bl = false;
        boolean bl2 = n3 == 33;
        Object object = view = this.u(bl2, n4, n5);
        if (view == null) {
            object = this;
        }
        if (n4 >= n7 && n5 <= n6) {
            bl2 = bl;
        } else {
            n4 = bl2 ? (n4 -= n7) : n5 - n6;
            this.P(n4, 0, 1, true);
            bl2 = true;
        }
        if (object != this.findFocus()) {
            object.requestFocus(n3);
        }
        return bl2;
    }

    public final int P(int n3, int n4, int n5, boolean bl) {
        int n6;
        int n7;
        if (n5 == 1) {
            this.X(2, n5);
        }
        boolean bl2 = this.o(0, n3, this.v, this.u, n5);
        int n8 = 0;
        if (bl2) {
            n7 = this.v[1];
            n6 = this.u[1];
            n7 = n3 - n7;
        } else {
            n6 = 0;
            n7 = n3;
        }
        int n9 = this.getScrollY();
        int n10 = this.getScrollRange();
        boolean bl3 = this.e() && !bl;
        n3 = this.J(0, n7, 0, n9, 0, n10, 0, 0, true) && !this.y(n5) ? 1 : 0;
        int n11 = this.getScrollY() - n9;
        Object object = this.v;
        object[1] = 0;
        this.p(0, n11, 0, n7 - n11, this.u, n5, (int[])object);
        n11 = this.u[1];
        if ((n9 += (n7 -= this.v[1])) < 0) {
            if (bl3) {
                androidx.core.widget.f.d(this.g, (float)(-n7) / (float)this.getHeight(), (float)n4 / (float)this.getWidth());
                if (!this.h.isFinished()) {
                    this.h.onRelease();
                }
            }
        } else if (n9 > n10 && bl3) {
            androidx.core.widget.f.d(this.h, (float)n7 / (float)this.getHeight(), 1.0f - (float)n4 / (float)this.getWidth());
            if (!this.g.isFinished()) {
                this.g.onRelease();
            }
        }
        if (!this.g.isFinished() || !this.h.isFinished()) {
            this.postInvalidateOnAnimation();
            n3 = n8;
        }
        if (n3 != 0 && n5 == 0 && (object = (Object)this.n) != null) {
            object.clear();
        }
        if (n5 == 1) {
            this.Z(n5);
            this.g.onRelease();
            this.h.onRelease();
        }
        return n6 + n11;
    }

    public final void Q(View view) {
        view.getDrawingRect(this.e);
        this.offsetDescendantRectToMyCoords(view, this.e);
        int n3 = this.j(this.e);
        if (n3 != 0) {
            this.scrollBy(0, n3);
        }
    }

    public final boolean R(Rect rect, boolean bl) {
        int n3 = this.j(rect);
        boolean bl2 = n3 != 0;
        if (bl2) {
            if (bl) {
                this.scrollBy(0, n3);
                return bl2;
            }
            this.T(0, n3);
        }
        return bl2;
    }

    public final boolean S(EdgeEffect edgeEffect, int n3) {
        if (n3 > 0) {
            return true;
        }
        float f3 = androidx.core.widget.f.b(edgeEffect);
        float f4 = this.getHeight();
        return this.x(-n3) < f3 * f4;
    }

    public final void T(int n3, int n4) {
        this.U(n3, n4, 250, false);
    }

    public final void U(int n3, int n4, int n5, boolean bl) {
        if (this.getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.d > 250L) {
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            int n6 = view.getHeight();
            int n7 = layoutParams.topMargin;
            int n8 = layoutParams.bottomMargin;
            int n9 = this.getHeight();
            int n10 = this.getPaddingTop();
            int n11 = this.getPaddingBottom();
            n3 = this.getScrollY();
            n4 = Math.max(0, Math.min(n4 + n3, Math.max(0, n6 + n7 + n8 - (n9 - n10 - n11))));
            this.f.startScroll(this.getScrollX(), n3, 0, n4 - n3, n5);
            this.N(bl);
        } else {
            if (!this.f.isFinished()) {
                this.a();
            }
            this.scrollBy(n3, n4);
        }
        this.d = AnimationUtils.currentAnimationTimeMillis();
    }

    public void V(int n3, int n4, int n5, boolean bl) {
        this.U(n3 - this.getScrollX(), n4 - this.getScrollY(), n5, bl);
    }

    public void W(int n3, int n4, boolean bl) {
        this.V(n3, n4, 250, bl);
    }

    public boolean X(int n3, int n4) {
        return this.A.p(n3, n4);
    }

    public final boolean Y(MotionEvent motionEvent) {
        boolean bl;
        if (androidx.core.widget.f.b(this.g) != 0.0f) {
            androidx.core.widget.f.d(this.g, 0.0f, motionEvent.getX() / (float)this.getWidth());
            bl = true;
        } else {
            bl = false;
        }
        if (androidx.core.widget.f.b(this.h) != 0.0f) {
            androidx.core.widget.f.d(this.h, 0.0f, 1.0f - motionEvent.getX() / (float)this.getWidth());
            return true;
        }
        return bl;
    }

    public void Z(int n3) {
        this.A.r(n3);
    }

    public final void a() {
        this.f.abortAnimation();
        this.Z(1);
    }

    public void addView(View view) {
        if (this.getChildCount() <= 0) {
            super.addView(view);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, int n3) {
        if (this.getChildCount() <= 0) {
            super.addView(view, n3);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() <= 0) {
            super.addView(view, n3, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() <= 0) {
            super.addView(view, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    @Override
    public void b(View view, View view2, int n3, int n4) {
        this.z.c(view, view2, n3, n4);
        this.X(2, n4);
    }

    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    public void computeScroll() {
        int n3;
        if (this.f.isFinished()) {
            return;
        }
        this.f.computeScrollOffset();
        int n4 = this.f.getCurrY();
        int n5 = this.n(n4 - this.x);
        this.x = n4;
        int[] nArray = this.v;
        nArray[1] = 0;
        this.o(0, n5, nArray, null, 1);
        n4 = this.getScrollRange();
        if ((n5 -= this.v[1]) != 0) {
            n3 = this.getScrollY();
            this.J(0, n5, this.getScrollX(), n3, 0, n4, 0, 0, false);
            n3 = this.getScrollY() - n3;
            nArray = this.v;
            nArray[1] = 0;
            this.p(0, n3, 0, n5 -= n3, this.u, 1, nArray);
            n5 -= this.v[1];
        }
        if (n5 != 0) {
            n3 = this.getOverScrollMode();
            if (n3 == 0 || n3 == 1 && n4 > 0) {
                if (n5 < 0) {
                    if (this.g.isFinished()) {
                        this.g.onAbsorb((int)this.f.getCurrVelocity());
                    }
                } else if (this.h.isFinished()) {
                    this.h.onAbsorb((int)this.f.getCurrVelocity());
                }
            }
            this.a();
        }
        if (!this.f.isFinished()) {
            this.postInvalidateOnAnimation();
            return;
        }
        this.Z(1);
    }

    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    public int computeVerticalScrollRange() {
        int n3 = this.getChildCount();
        int n4 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        if (n3 == 0) {
            return n4;
        }
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        n3 = view.getBottom() + layoutParams.bottomMargin;
        int n5 = this.getScrollY();
        int n6 = Math.max(0, n3 - n4);
        if (n5 < 0) {
            return n3 - n5;
        }
        n4 = n3;
        if (n5 > n6) {
            n4 = n3 + (n5 - n6);
        }
        return n4;
    }

    public boolean d(int n3) {
        View view;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, n3);
        int n4 = this.getMaxScrollAmount();
        if (view != null && this.G(view, n4, this.getHeight())) {
            view.getDrawingRect(this.e);
            this.offsetDescendantRectToMyCoords(view, this.e);
            this.P(this.j(this.e), 0, 1, true);
            view.requestFocus(n3);
        } else {
            int n5;
            if (n3 == 33 && this.getScrollY() < n4) {
                n5 = this.getScrollY();
            } else {
                n5 = n4;
                if (n3 == 130) {
                    n5 = n4;
                    if (this.getChildCount() > 0) {
                        view = this.getChildAt(0);
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
                        n5 = Math.min(view.getBottom() + layoutParams.bottomMargin - (this.getScrollY() + this.getHeight() - this.getPaddingBottom()), n4);
                    }
                }
            }
            if (n5 == 0) {
                return false;
            }
            if (n3 != 130) {
                n5 = -n5;
            }
            this.P(n5, 0, 1, true);
        }
        if (view2 != null && view2.isFocused() && this.E(view2)) {
            n3 = this.getDescendantFocusability();
            this.setDescendantFocusability(131072);
            this.requestFocus();
            this.setDescendantFocusability(n3);
        }
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.t(keyEvent);
        {
        }
    }

    public boolean dispatchNestedFling(float f3, float f4, boolean bl) {
        return this.A.a(f3, f4, bl);
    }

    public boolean dispatchNestedPreFling(float f3, float f4) {
        return this.A.b(f3, f4);
    }

    public boolean dispatchNestedPreScroll(int n3, int n4, int[] nArray, int[] nArray2) {
        return this.o(n3, n4, nArray, nArray2, 0);
    }

    public boolean dispatchNestedScroll(int n3, int n4, int n5, int n6, int[] nArray) {
        return this.A.f(n3, n4, n5, n6, nArray);
    }

    public void draw(Canvas canvas) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        super.draw(canvas);
        int n10 = this.getScrollY();
        boolean bl = this.g.isFinished();
        int n11 = 0;
        if (!bl) {
            n9 = canvas.save();
            n8 = this.getWidth();
            n7 = this.getHeight();
            n6 = Math.min(0, n10);
            if (b.a((ViewGroup)this)) {
                n8 -= this.getPaddingLeft() + this.getPaddingRight();
                n5 = this.getPaddingLeft();
            } else {
                n5 = 0;
            }
            n4 = n7;
            n3 = n6;
            if (b.a((ViewGroup)this)) {
                n4 = n7 - (this.getPaddingTop() + this.getPaddingBottom());
                n3 = n6 + this.getPaddingTop();
            }
            canvas.translate((float)n5, (float)n3);
            this.g.setSize(n8, n4);
            if (this.g.draw(canvas)) {
                this.postInvalidateOnAnimation();
            }
            canvas.restoreToCount(n9);
        }
        if (!this.h.isFinished()) {
            n9 = canvas.save();
            n3 = this.getWidth();
            n6 = this.getHeight();
            n7 = Math.max(this.getScrollRange(), n10) + n6;
            n5 = n11;
            n8 = n3;
            if (b.a((ViewGroup)this)) {
                n8 = n3 - (this.getPaddingLeft() + this.getPaddingRight());
                n5 = this.getPaddingLeft();
            }
            n4 = n7;
            n3 = n6;
            if (b.a((ViewGroup)this)) {
                n3 = n6 - (this.getPaddingTop() + this.getPaddingBottom());
                n4 = n7 - this.getPaddingBottom();
            }
            canvas.translate((float)(n5 - n8), (float)n4);
            canvas.rotate(180.0f, (float)n8, 0.0f);
            this.h.setSize(n8, n3);
            if (this.h.draw(canvas)) {
                this.postInvalidateOnAnimation();
            }
            canvas.restoreToCount(n9);
        }
    }

    public final boolean e() {
        int n3 = this.getOverScrollMode();
        return n3 == 0 || n3 == 1 && this.getScrollRange() > 0;
        {
        }
    }

    public final boolean f() {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            if (view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin > this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void g(View view, int n3) {
        this.z.d(view, n3);
        this.Z(n3);
    }

    public float getBottomFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        int n3 = this.getVerticalFadingEdgeLength();
        int n4 = this.getHeight();
        int n5 = this.getPaddingBottom();
        n5 = view.getBottom() + layoutParams.bottomMargin - this.getScrollY() - (n4 - n5);
        if (n5 < n3) {
            return (float)n5 / (float)n3;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int)((float)this.getHeight() * 0.5f);
    }

    public int getNestedScrollAxes() {
        return this.z.a();
    }

    public int getScrollRange() {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            return Math.max(0, view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin - (this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()));
        }
        return 0;
    }

    public float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n3 = this.getVerticalFadingEdgeLength();
        int n4 = this.getScrollY();
        if (n4 < n3) {
            return (float)n4 / (float)n3;
        }
        return 1.0f;
    }

    public float getVerticalScrollFactorCompat() {
        if (this.B == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = this.getContext();
            if (context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                this.B = typedValue.getDimension(context.getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
        }
        return this.B;
    }

    @Override
    public void h(View view, int n3, int n4, int[] nArray, int n5) {
        this.o(n3, n4, nArray, null, n5);
    }

    public boolean hasNestedScrollingParent() {
        return this.y(0);
    }

    public boolean isNestedScrollingEnabled() {
        return this.A.l();
    }

    public int j(Rect rect) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        int n3 = this.getHeight();
        int n4 = this.getScrollY();
        int n5 = n4 + n3;
        int n6 = this.getVerticalFadingEdgeLength();
        int n7 = n4;
        if (rect.top > 0) {
            n7 = n4 + n6;
        }
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        n6 = rect.bottom;
        n4 = rect.bottom < view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin ? n5 - n6 : n5;
        if (n6 > n4 && rect.top > n7) {
            n7 = rect.height() > n3 ? rect.top - n7 : rect.bottom - n4;
            return Math.min(n7, view.getBottom() + layoutParams.bottomMargin - n5);
        }
        if (rect.top < n7 && n6 < n4) {
            n7 = rect.height() > n3 ? 0 - (n4 - rect.bottom) : 0 - (n7 - rect.top);
            return Math.max(n7, -this.getScrollY());
        }
        return 0;
    }

    @Override
    public void k(View view, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        this.H(n6, n7, nArray);
    }

    @Override
    public void l(View view, int n3, int n4, int n5, int n6, int n7) {
        this.H(n6, n7, null);
    }

    @Override
    public boolean m(View view, View view2, int n3, int n4) {
        return (n3 & 2) != 0;
    }

    public void measureChild(View view, int n3, int n4) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec((int)n3, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
    }

    public void measureChildWithMargins(View view, int n3, int n4, int n5, int n6) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec((int)n3, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n4), (int)marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin), (int)0));
    }

    public int n(int n3) {
        int n4 = this.getHeight();
        if (n3 > 0 && androidx.core.widget.f.b(this.g) != 0.0f) {
            float f3 = (float)(-n3) * 4.0f / (float)n4;
            int n5 = Math.round((float)(-n4) / 4.0f * androidx.core.widget.f.d(this.g, f3, 0.5f));
            if (n5 != n3) {
                this.g.finish();
            }
            return n3 - n5;
        }
        int n6 = n3;
        if (n3 < 0) {
            n6 = n3;
            if (androidx.core.widget.f.b(this.h) != 0.0f) {
                float f4 = n3;
                float f5 = n4;
                n6 = Math.round(f5 / 4.0f * androidx.core.widget.f.d(this.h, f4 = f4 * 4.0f / f5, 0.5f));
                if (n6 != n3) {
                    this.h.finish();
                }
                n6 = n3 - n6;
            }
        }
        return n6;
    }

    public boolean o(int n3, int n4, int[] nArray, int[] nArray2, int n5) {
        return this.A.d(n3, n4, nArray, nArray2, n5);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.k = false;
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 8 && !this.m) {
            int n3;
            float f3;
            int n4;
            if (o0.z.a(motionEvent, 2)) {
                n4 = 9;
                f3 = motionEvent.getAxisValue(9);
                n3 = (int)motionEvent.getX();
            } else if (o0.z.a(motionEvent, 0x400000)) {
                f3 = motionEvent.getAxisValue(26);
                n3 = this.getWidth() / 2;
                n4 = 26;
            } else {
                n4 = 0;
                n3 = 0;
                f3 = 0.0f;
            }
            if (f3 != 0.0f) {
                int n5 = (int)(f3 * this.getVerticalScrollFactorCompat());
                boolean bl = o0.z.a(motionEvent, 8194);
                this.P(-n5, n3, 1, bl);
                if (n4 != 0) {
                    this.E.g(motionEvent, n4);
                }
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onInterceptTouchEvent(MotionEvent object) {
        int n3 = object.getAction();
        boolean bl = true;
        boolean bl2 = true;
        if (n3 == 2 && this.m) {
            return true;
        }
        if ((n3 &= 0xFF) != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 6) return this.m;
                        this.I((MotionEvent)object);
                        return this.m;
                    }
                } else {
                    n3 = this.t;
                    if (n3 == -1) return this.m;
                    int n4 = object.findPointerIndex(n3);
                    if (n4 == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid pointerId=");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(" in onInterceptTouchEvent");
                        Log.e((String)"NestedScrollView", (String)((StringBuilder)object).toString());
                        return this.m;
                    } else {
                        n3 = (int)object.getY(n4);
                        if (Math.abs(n3 - this.i) <= this.q || (2 & this.getNestedScrollAxes()) != 0) return this.m;
                        this.m = true;
                        this.i = n3;
                        this.C();
                        this.n.addMovement((MotionEvent)object);
                        this.w = 0;
                        object = this.getParent();
                        if (object == null) return this.m;
                        object.requestDisallowInterceptTouchEvent(true);
                    }
                    return this.m;
                }
            }
            this.m = false;
            this.t = -1;
            this.L();
            if (this.f.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                this.postInvalidateOnAnimation();
            }
            this.Z(0);
            return this.m;
        }
        n3 = (int)object.getY();
        if (!this.z((int)object.getX(), n3)) {
            boolean bl3 = bl2;
            if (!this.Y((MotionEvent)object)) {
                bl3 = !this.f.isFinished() ? bl2 : false;
            }
            this.m = bl3;
            this.L();
            return this.m;
        } else {
            this.i = n3;
            this.t = object.getPointerId(0);
            this.A();
            this.n.addMovement((MotionEvent)object);
            this.f.computeScrollOffset();
            boolean bl4 = bl;
            if (!this.Y((MotionEvent)object)) {
                bl4 = !this.f.isFinished() ? bl : false;
            }
            this.m = bl4;
            this.X(2, 0);
        }
        return this.m;
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        n3 = 0;
        this.j = false;
        View view = this.l;
        if (view != null && NestedScrollView.F(view, (View)this)) {
            this.Q(this.l);
        }
        this.l = null;
        if (!this.k) {
            if (this.y != null) {
                this.scrollTo(this.getScrollX(), this.y.c);
                this.y = null;
            }
            if (this.getChildCount() > 0) {
                view = this.getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
                n3 = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
            int n7 = this.getPaddingTop();
            int n8 = this.getPaddingBottom();
            n5 = this.getScrollY();
            n3 = NestedScrollView.i(n5, n6 - n4 - n7 - n8, n3);
            if (n3 != n5) {
                this.scrollTo(this.getScrollX(), n3);
            }
        }
        this.scrollTo(this.getScrollX(), this.getScrollY());
        this.k = true;
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        if (this.o && View.MeasureSpec.getMode((int)n4) != 0 && this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            int n5 = view.getMeasuredHeight();
            if (n5 < (n4 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom() - layoutParams.topMargin - layoutParams.bottomMargin)) {
                view.measure(ViewGroup.getChildMeasureSpec((int)n3, (int)(this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)n4, (int)0x40000000));
            }
        }
    }

    public boolean onNestedFling(View view, float f3, float f4, boolean bl) {
        if (!bl) {
            this.dispatchNestedFling(0.0f, f4, true);
            this.v((int)f4);
            return true;
        }
        return false;
    }

    public boolean onNestedPreFling(View view, float f3, float f4) {
        return this.dispatchNestedPreFling(f3, f4);
    }

    public void onNestedPreScroll(View view, int n3, int n4, int[] nArray) {
        this.h(view, n3, n4, nArray, 0);
    }

    public void onNestedScroll(View view, int n3, int n4, int n5, int n6) {
        this.H(n6, 0, null);
    }

    public void onNestedScrollAccepted(View view, View view2, int n3) {
        this.b(view, view2, n3, 0);
    }

    public void onOverScrolled(int n3, int n4, boolean bl, boolean bl2) {
        super.scrollTo(n3, n4);
    }

    public boolean onRequestFocusInDescendants(int n3, Rect rect) {
        int n4;
        if (n3 == 2) {
            n4 = 130;
        } else {
            n4 = n3;
            if (n3 == 1) {
                n4 = 33;
            }
        }
        View view = rect == null ? FocusFinder.getInstance().findNextFocus((ViewGroup)this, null, n4) : FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, rect, n4);
        if (view == null) {
            return false;
        }
        if (this.E(view)) {
            return false;
        }
        return view.requestFocus(n4, rect);
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.y = object;
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.c = this.getScrollY();
        return savedState;
    }

    public void onScrollChanged(int n3, int n4, int n5, int n6) {
        super.onScrollChanged(n3, n4, n5, n6);
        d d3 = this.C;
        if (d3 != null) {
            d3.a(this, n3, n4, n5, n6);
        }
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        View view = this.findFocus();
        if (view != null && this != view && this.G(view, 0, n6)) {
            view.getDrawingRect(this.e);
            this.offsetDescendantRectToMyCoords(view, this.e);
            this.q(this.j(this.e));
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int n3) {
        return this.m(view, view2, n3, 0);
    }

    public void onStopNestedScroll(View view) {
        this.g(view, 0);
    }

    public boolean onTouchEvent(MotionEvent object) {
        this.C();
        int n3 = object.getActionMasked();
        if (n3 == 0) {
            this.w = 0;
        }
        MotionEvent motionEvent = MotionEvent.obtain((MotionEvent)object);
        motionEvent.offsetLocation(0.0f, (float)this.w);
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 5) {
                            if (n3 == 6) {
                                this.I((MotionEvent)object);
                                this.i = (int)object.getY(object.findPointerIndex(this.t));
                            }
                        } else {
                            n3 = object.getActionIndex();
                            this.i = (int)object.getY(n3);
                            this.t = object.getPointerId(n3);
                        }
                    } else {
                        if (this.m && this.getChildCount() > 0 && this.f.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                            this.postInvalidateOnAnimation();
                        }
                        this.s();
                    }
                } else {
                    int n4 = object.findPointerIndex(this.t);
                    if (n4 == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid pointerId=");
                        ((StringBuilder)object).append(this.t);
                        ((StringBuilder)object).append(" in onTouchEvent");
                        Log.e((String)"NestedScrollView", (String)((StringBuilder)object).toString());
                    } else {
                        int n5;
                        int n6 = (int)object.getY(n4);
                        n3 = this.i - n6;
                        n3 = n5 = n3 - this.M(n3, object.getX(n4));
                        if (!this.m) {
                            n3 = n5;
                            if (Math.abs(n5) > this.q) {
                                ViewParent viewParent = this.getParent();
                                if (viewParent != null) {
                                    viewParent.requestDisallowInterceptTouchEvent(true);
                                }
                                this.m = true;
                                n3 = n5 > 0 ? n5 - this.q : n5 + this.q;
                            }
                        }
                        if (this.m) {
                            n3 = this.P(n3, (int)object.getX(n4), 0, false);
                            this.i = n6 - n3;
                            this.w += n3;
                        }
                    }
                }
            } else {
                object = this.n;
                object.computeCurrentVelocity(1000, (float)this.s);
                n3 = (int)object.getYVelocity(this.t);
                if (Math.abs(n3) >= this.r) {
                    float f3;
                    if (!this.r(n3) && !this.dispatchNestedPreFling(0.0f, f3 = (float)(n3 = -n3))) {
                        this.dispatchNestedFling(0.0f, f3, true);
                        this.v(n3);
                    }
                } else if (this.f.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    this.postInvalidateOnAnimation();
                }
                this.s();
            }
        } else {
            ViewParent viewParent;
            if (this.getChildCount() == 0) {
                return false;
            }
            if (this.m && (viewParent = this.getParent()) != null) {
                viewParent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.f.isFinished()) {
                this.a();
            }
            this.D((int)object.getY(), object.getPointerId(0));
        }
        object = this.n;
        if (object != null) {
            object.addMovement(motionEvent);
        }
        motionEvent.recycle();
        return true;
    }

    public void p(int n3, int n4, int n5, int n6, int[] nArray, int n7, int[] nArray2) {
        this.A.e(n3, n4, n5, n6, nArray, n7, nArray2);
    }

    public final void q(int n3) {
        if (n3 != 0) {
            if (this.p) {
                this.T(0, n3);
                return;
            }
            this.scrollBy(0, n3);
        }
    }

    public final boolean r(int n3) {
        if (androidx.core.widget.f.b(this.g) != 0.0f) {
            if (this.S(this.g, n3)) {
                this.g.onAbsorb(n3);
                return true;
            }
            this.v(-n3);
            return true;
        }
        if (androidx.core.widget.f.b(this.h) != 0.0f) {
            EdgeEffect edgeEffect = this.h;
            if (this.S(edgeEffect, n3 = -n3)) {
                this.h.onAbsorb(n3);
                return true;
            }
            this.v(n3);
            return true;
        }
        return false;
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.j) {
            this.Q(view2);
        } else {
            this.l = view2;
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.R(rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (bl) {
            this.L();
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        this.j = true;
        super.requestLayout();
    }

    public final void s() {
        this.t = -1;
        this.m = false;
        this.L();
        this.Z(0);
        this.g.onRelease();
        this.h.onRelease();
    }

    public void scrollTo(int n3, int n4) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            int n5 = this.getWidth();
            int n6 = this.getPaddingLeft();
            int n7 = this.getPaddingRight();
            int n8 = view.getWidth();
            int n9 = layoutParams.leftMargin;
            int n10 = layoutParams.rightMargin;
            int n11 = this.getHeight();
            int n12 = this.getPaddingTop();
            int n13 = this.getPaddingBottom();
            int n14 = view.getHeight();
            int n15 = layoutParams.topMargin;
            int n16 = layoutParams.bottomMargin;
            n3 = NestedScrollView.i(n3, n5 - n6 - n7, n8 + n9 + n10);
            n4 = NestedScrollView.i(n4, n11 - n12 - n13, n14 + n15 + n16);
            if (n3 != this.getScrollX() || n4 != this.getScrollY()) {
                super.scrollTo(n3, n4);
            }
        }
    }

    public void setFillViewport(boolean bl) {
        if (bl != this.o) {
            this.o = bl;
            this.requestLayout();
        }
    }

    public void setNestedScrollingEnabled(boolean bl) {
        this.A.m(bl);
    }

    public void setOnScrollChangeListener(d d3) {
        this.C = d3;
    }

    public void setSmoothScrollingEnabled(boolean bl) {
        this.p = bl;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public boolean startNestedScroll(int n3) {
        return this.X(n3, 0);
    }

    public void stopNestedScroll() {
        this.Z(0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean t(KeyEvent keyEvent) {
        this.e.setEmpty();
        boolean bl = this.f();
        int n3 = 130;
        if (!bl) {
            if (!this.isFocused() || keyEvent.getKeyCode() == 4) return false;
            View view = this.findFocus();
            keyEvent = view;
            if (view == this) {
                keyEvent = null;
            }
            if ((keyEvent = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)keyEvent, 130)) == null || keyEvent == this || !keyEvent.requestFocus(130)) return false;
            return true;
        }
        if (keyEvent.getAction() != 0) return false;
        int n4 = keyEvent.getKeyCode();
        if (n4 != 19) {
            if (n4 != 20) {
                if (n4 != 62) {
                    if (n4 == 92) return this.w(33);
                    if (n4 == 93) return this.w(130);
                    if (n4 != 122) {
                        if (n4 != 123) return false;
                        this.K(130);
                        return false;
                    }
                    this.K(33);
                    return false;
                }
                if (keyEvent.isShiftPressed()) {
                    n3 = 33;
                }
                this.K(n3);
                return false;
            }
            if (!keyEvent.isAltPressed()) return this.d(130);
            return this.w(130);
        }
        if (!keyEvent.isAltPressed()) return this.d(33);
        return this.w(33);
    }

    public final View u(boolean bl, int n3, int n4) {
        ArrayList arrayList = this.getFocusables(2);
        int n5 = arrayList.size();
        View view = null;
        boolean bl2 = false;
        for (int i3 = 0; i3 < n5; ++i3) {
            boolean bl3;
            View view2;
            block3: {
                View view3;
                block6: {
                    int n6;
                    block7: {
                        boolean bl4;
                        block5: {
                            int n7;
                            block4: {
                                view3 = (View)arrayList.get(i3);
                                n7 = view3.getTop();
                                n6 = view3.getBottom();
                                view2 = view;
                                bl3 = bl2;
                                if (n3 >= n6) break block3;
                                view2 = view;
                                bl3 = bl2;
                                if (n7 >= n4) break block3;
                                bl4 = n3 < n7 && n6 < n4;
                                if (view != null) break block4;
                                view2 = view3;
                                bl3 = bl4;
                                break block3;
                            }
                            n6 = bl && n7 < view.getTop() || !bl && n6 > view.getBottom() ? 1 : 0;
                            if (!bl2) break block5;
                            view2 = view;
                            bl3 = bl2;
                            if (!bl4) break block3;
                            view2 = view;
                            bl3 = bl2;
                            if (n6 == 0) break block3;
                            break block6;
                        }
                        if (!bl4) break block7;
                        view2 = view3;
                        bl3 = true;
                        break block3;
                    }
                    view2 = view;
                    bl3 = bl2;
                    if (n6 == 0) break block3;
                }
                view2 = view3;
                bl3 = bl2;
            }
            view = view2;
            bl2 = bl3;
        }
        return view;
    }

    public void v(int n3) {
        if (this.getChildCount() > 0) {
            this.f.fling(this.getScrollX(), this.getScrollY(), 0, n3, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.N(true);
        }
    }

    public boolean w(int n3) {
        int n4 = n3 == 130 ? 1 : 0;
        int n5 = this.getHeight();
        Rect rect = this.e;
        rect.top = 0;
        rect.bottom = n5;
        if (n4 != 0 && (n4 = this.getChildCount()) > 0) {
            rect = this.getChildAt(n4 - 1);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)rect.getLayoutParams();
            this.e.bottom = rect.getBottom() + layoutParams.bottomMargin + this.getPaddingBottom();
            rect = this.e;
            rect.top = rect.bottom - n5;
        }
        rect = this.e;
        return this.O(n3, rect.top, rect.bottom);
    }

    public final float x(int n3) {
        double d3 = Math.log((float)Math.abs(n3) * 0.35f / (this.c * 0.015f));
        float f3 = F;
        double d4 = f3;
        return (float)((double)(this.c * 0.015f) * Math.exp((double)f3 / (d4 - 1.0) * d3));
    }

    public boolean y(int n3) {
        return this.A.k(n3);
    }

    public final boolean z(int n3, int n4) {
        if (this.getChildCount() > 0) {
            int n5 = this.getScrollY();
            View view = this.getChildAt(0);
            if (n4 >= view.getTop() - n5 && n4 < view.getBottom() - n5 && n3 >= view.getLeft() && n3 < view.getRight()) {
                return true;
            }
        }
        return false;
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.c = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HorizontalScrollView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
            stringBuilder.append(" scrollPosition=");
            stringBuilder.append(this.c);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.c);
        }
    }

    public static class a
    extends o0.a {
        @Override
        public void f(View object, AccessibilityEvent accessibilityEvent) {
            super.f((View)object, accessibilityEvent);
            object = (NestedScrollView)object;
            accessibilityEvent.setClassName((CharSequence)ScrollView.class.getName());
            boolean bl = ((NestedScrollView)object).getScrollRange() > 0;
            accessibilityEvent.setScrollable(bl);
            accessibilityEvent.setScrollX(object.getScrollX());
            accessibilityEvent.setScrollY(object.getScrollY());
            p0.u.a((AccessibilityRecord)accessibilityEvent, object.getScrollX());
            p0.u.b((AccessibilityRecord)accessibilityEvent, ((NestedScrollView)object).getScrollRange());
        }

        @Override
        public void g(View object, s s3) {
            int n3;
            super.g((View)object, s3);
            object = (NestedScrollView)object;
            s3.h0(ScrollView.class.getName());
            if (object.isEnabled() && (n3 = ((NestedScrollView)object).getScrollRange()) > 0) {
                s3.B0(true);
                if (object.getScrollY() > 0) {
                    s3.b(s.a.r);
                    s3.b(s.a.C);
                }
                if (object.getScrollY() < n3) {
                    s3.b(s.a.q);
                    s3.b(s.a.E);
                }
            }
        }

        @Override
        public boolean j(View view, int n3, Bundle object) {
            if (super.j(view, n3, (Bundle)object)) {
                return true;
            }
            object = (NestedScrollView)view;
            if (!object.isEnabled()) {
                return false;
            }
            int n4 = object.getHeight();
            view = new Rect();
            int n5 = n4;
            if (object.getMatrix().isIdentity()) {
                n5 = n4;
                if (object.getGlobalVisibleRect((Rect)view)) {
                    n5 = view.height();
                }
            }
            if (n3 != 4096) {
                if (n3 != 8192 && n3 != 16908344) {
                    if (n3 != 16908346) {
                        return false;
                    }
                } else {
                    n4 = object.getPaddingBottom();
                    n3 = object.getPaddingTop();
                    n3 = Math.max(object.getScrollY() - (n5 - n4 - n3), 0);
                    if (n3 != object.getScrollY()) {
                        ((NestedScrollView)object).W(0, n3, true);
                        return true;
                    }
                    return false;
                }
            }
            n4 = object.getPaddingBottom();
            n3 = object.getPaddingTop();
            n3 = Math.min(object.getScrollY() + (n5 - n4 - n3), ((NestedScrollView)object).getScrollRange());
            if (n3 != object.getScrollY()) {
                ((NestedScrollView)object).W(0, n3, true);
                return true;
            }
            return false;
        }
    }

    public static abstract class b {
        public static boolean a(ViewGroup viewGroup) {
            return viewGroup.getClipToPadding();
        }
    }

    public class c
    implements p {
        public final NestedScrollView a;

        public c(NestedScrollView nestedScrollView) {
            this.a = nestedScrollView;
        }

        @Override
        public boolean a(float f3) {
            if (f3 == 0.0f) {
                return false;
            }
            this.c();
            this.a.v((int)f3);
            return true;
        }

        @Override
        public float b() {
            return -this.a.getVerticalScrollFactorCompat();
        }

        @Override
        public void c() {
            this.a.f.abortAnimation();
        }
    }

    public static interface d {
        public void a(NestedScrollView var1, int var2, int var3, int var4, int var5);
    }
}

