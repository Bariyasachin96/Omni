/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.Interpolator
 *  android.widget.EdgeEffect
 *  android.widget.Scroller
 */
package androidx.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.customview.view.AbsSavedState;
import e0.a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import o0.f0;
import o0.x0;
import o0.z1;
import p0.s;

public class ViewPager
extends ViewGroup {
    public static final int[] g0 = new int[]{16842931};
    public static final Comparator h0 = new Comparator(){

        public int a(f f3, f f4) {
            return f3.b - f4.b;
        }
    };
    public static final Interpolator i0 = new Interpolator(){

        public float getInterpolation(float f3) {
            return (f3 -= 1.0f) * f3 * f3 * f3 * f3 + 1.0f;
        }
    };
    public static final k j0 = new k();
    public int A;
    public int B;
    public int C;
    public float D;
    public float E;
    public float F;
    public float G;
    public int H = -1;
    public VelocityTracker I;
    public int J;
    public int K;
    public int L;
    public int M;
    public boolean N;
    public EdgeEffect O;
    public EdgeEffect P;
    public boolean Q = true;
    public boolean R = false;
    public boolean S;
    public int T;
    public List U;
    public i V;
    public i W;
    public List a0;
    public int b0;
    public int c;
    public int c0;
    public final ArrayList d = new ArrayList();
    public ArrayList d0;
    public final f e = new f();
    public final Runnable e0;
    public final Rect f = new Rect();
    public int f0 = 0;
    public int g;
    public int h = -1;
    public Parcelable i = null;
    public ClassLoader j = null;
    public Scroller k;
    public boolean l;
    public int m;
    public Drawable n;
    public int o;
    public int p;
    public float q = -3.4028235E38f;
    public float r = Float.MAX_VALUE;
    public int s;
    public int t;
    public boolean u;
    public boolean v;
    public boolean w;
    public int x = 1;
    public boolean y;
    public boolean z;

    public ViewPager(Context context) {
        super(context);
        this.e0 = new Runnable(this){
            public final ViewPager c;
            {
                this.c = viewPager;
            }

            @Override
            public void run() {
                this.c.setScrollState(0);
                this.c.y();
            }
        };
        this.p();
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e0 = new /* invalid duplicate definition of identical inner class */;
        this.p();
    }

    private int getClientWidth() {
        return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    public static boolean q(View view) {
        return view.getClass().getAnnotation(e.class) != null;
    }

    private void setScrollingCacheEnabled(boolean bl) {
        if (this.v != bl) {
            this.v = bl;
        }
    }

    public final void A(int n3, int n4, int n5, int n6) {
        if (n4 > 0 && !this.d.isEmpty()) {
            if (!this.k.isFinished()) {
                this.k.setFinalX(this.getCurrentItem() * this.getClientWidth());
                return;
            }
            int n7 = this.getPaddingLeft();
            int n8 = this.getPaddingRight();
            int n9 = this.getPaddingLeft();
            int n10 = this.getPaddingRight();
            this.scrollTo((int)((float)this.getScrollX() / (float)(n4 - n9 - n10 + n6) * (float)(n3 - n7 - n8 + n5)), this.getScrollY());
            return;
        }
        f f3 = this.o(this.g);
        float f4 = f3 != null ? Math.min(f3.e, this.r) : 0.0f;
        n3 = (int)(f4 * (float)(n3 - this.getPaddingLeft() - this.getPaddingRight()));
        if (n3 != this.getScrollX()) {
            this.e(false);
            this.scrollTo(n3, this.getScrollY());
        }
    }

    public void B(h h3) {
        List list = this.a0;
        if (list != null) {
            list.remove(h3);
        }
    }

    public void C(i i3) {
        List list = this.U;
        if (list != null) {
            list.remove(i3);
        }
    }

    public final void D(boolean bl) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl);
        }
    }

    public final boolean E() {
        this.H = -1;
        this.j();
        this.O.onRelease();
        this.P.onRelease();
        return this.O.isFinished() || this.P.isFinished();
        {
        }
    }

    public final void F(int n3, boolean bl, int n4, boolean bl2) {
        f f3 = this.o(n3);
        int n5 = f3 != null ? (int)((float)this.getClientWidth() * Math.max(this.q, Math.min(f3.e, this.r))) : 0;
        if (bl) {
            this.J(n5, 0, n4);
            if (bl2) {
                this.g(n3);
            }
            return;
        }
        if (bl2) {
            this.g(n3);
        }
        this.e(false);
        this.scrollTo(n5, 0);
        this.w(n5);
    }

    public void G(int n3, boolean bl, boolean bl2) {
        this.H(n3, bl, bl2, 0);
    }

    public void H(int n3, boolean bl, boolean bl2, int n4) {
        this.setScrollingCacheEnabled(false);
    }

    public i I(i i3) {
        i i4 = this.W;
        this.W = i3;
        return i4;
    }

    public void J(int n3, int n4, int n5) {
        int n6;
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        Scroller scroller = this.k;
        if (scroller != null && !scroller.isFinished()) {
            n6 = this.l ? this.k.getCurrX() : this.k.getStartX();
            this.k.abortAnimation();
            this.setScrollingCacheEnabled(false);
        } else {
            n6 = this.getScrollX();
        }
        int n7 = this.getScrollY();
        if ((n3 -= n6) == 0 && (n4 -= n7) == 0) {
            this.e(false);
            this.y();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        int n8 = this.getClientWidth();
        int n9 = n8 / 2;
        float f3 = Math.min(1.0f, (float)Math.abs(n3) * 1.0f / (float)n8);
        float f4 = n9;
        f3 = this.i(f3);
        n5 = Math.abs(n5);
        if (n5 > 0) {
            n5 = Math.min(Math.round(Math.abs((f4 + f3 * f4) / (float)n5) * 1000.0f) * 4, 600);
            this.l = false;
            this.k.startScroll(n6, n7, n3, n4, n5);
            x0.Y((View)this);
            return;
        }
        throw null;
    }

    public final void K() {
        if (this.c0 != 0) {
            ArrayList arrayList = this.d0;
            if (arrayList == null) {
                this.d0 = new ArrayList();
            } else {
                arrayList.clear();
            }
            int n3 = this.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                arrayList = this.getChildAt(i3);
                this.d0.add(arrayList);
            }
            Collections.sort(this.d0, j0);
        }
    }

    public void a(h h3) {
        if (this.a0 == null) {
            this.a0 = new ArrayList();
        }
        this.a0.add(h3);
    }

    public void addFocusables(ArrayList arrayList, int n3, int n4) {
        int n5 = arrayList.size();
        int n6 = this.getDescendantFocusability();
        if (n6 != 393216) {
            for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
                f f3;
                View view = this.getChildAt(i3);
                if (view.getVisibility() != 0 || (f3 = this.m(view)) == null || f3.b != this.g) continue;
                view.addFocusables(arrayList, n3, n4);
            }
        }
        if (n6 == 262144 && n5 != arrayList.size() || !this.isFocusable() || (n4 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode()) {
            return;
        }
        arrayList.add(this);
    }

    public void addTouchables(ArrayList arrayList) {
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            f f3;
            View view = this.getChildAt(i3);
            if (view.getVisibility() != 0 || (f3 = this.m(view)) == null || f3.b != this.g) continue;
            view.addTouchables(arrayList);
        }
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        boolean bl;
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        layoutParams = (LayoutParams)layoutParams2;
        layoutParams.a = bl = layoutParams.a | ViewPager.q(view);
        if (this.u) {
            if (!bl) {
                layoutParams.d = true;
                this.addViewInLayout(view, n3, layoutParams2);
                return;
            }
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
        super.addView(view, n3, layoutParams2);
    }

    public void b(i i3) {
        if (this.U == null) {
            this.U = new ArrayList();
        }
        this.U.add(i3);
    }

    /*
     * Unable to fully structure code
     */
    public boolean c(int var1_1) {
        block13: {
            block14: {
                block11: {
                    block12: {
                        block10: {
                            var6_2 = this.findFocus();
                            if (var6_2 == this) lbl-1000:
                            // 2 sources

                            {
                                while (true) {
                                    var5_3 = null;
                                    break block10;
                                    break;
                                }
                            }
                            var5_3 = var6_2;
                            if (var6_2 != null) {
                                var5_3 = var6_2.getParent();
                                while (var5_3 instanceof ViewGroup) {
                                    if (var5_3 == this) {
                                        var5_3 = var6_2;
                                        break block10;
                                    }
                                    var5_3 = var5_3.getParent();
                                }
                                var7_4 = new StringBuilder();
                                var7_4.append(var6_2.getClass().getSimpleName());
                                var5_3 = var6_2.getParent();
                                while (var5_3 instanceof ViewGroup) {
                                    var7_4.append(" => ");
                                    var7_4.append(var5_3.getClass().getSimpleName());
                                    var5_3 = var5_3.getParent();
                                }
                                var5_3 = new StringBuilder();
                                var5_3.append("arrowScroll tried to find focus based on non-child current focused view ");
                                var5_3.append(var7_4.toString());
                                Log.e((String)"ViewPager", (String)var5_3.toString());
                                ** continue;
                            }
                        }
                        var6_2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)var5_3, var1_1);
                        if (var6_2 == null || var6_2 == var5_3) break block11;
                        if (var1_1 != 17) break block12;
                        var2_5 = this.l((Rect)this.f, (View)var6_2).left;
                        var3_7 = this.l((Rect)this.f, (View)var5_3).left;
                        var4_9 = var5_3 != null && var2_5 >= var3_7 ? this.u() : var6_2.requestFocus();
                        break block13;
                    }
                    if (var1_1 != 66) ** GOTO lbl-1000
                    var2_6 = this.l((Rect)this.f, (View)var6_2).left;
                    var3_8 = this.l((Rect)this.f, (View)var5_3).left;
                    var4_9 = var5_3 != null && var2_6 <= var3_8 ? this.v() : var6_2.requestFocus();
                    break block13;
                }
                if (var1_1 != 17 && var1_1 != 1) {
                    ** if (var1_1 == 66 || var1_1 == 2) goto lbl-1000
                }
                break block14;
lbl-1000:
                // 2 sources

                {
                    var4_9 = false;
                    ** GOTO lbl58
                }
lbl-1000:
                // 1 sources

                {
                    var4_9 = this.v();
                }
                break block13;
            }
            var4_9 = this.u();
        }
        if (var4_9) {
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)var1_1));
        }
        return var4_9;
    }

    public boolean canScrollHorizontally(int n3) {
        return false;
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        this.l = true;
        if (!this.k.isFinished() && this.k.computeScrollOffset()) {
            int n3 = this.getScrollX();
            int n4 = this.getScrollY();
            int n5 = this.k.getCurrX();
            int n6 = this.k.getCurrY();
            if (n3 != n5 || n4 != n6) {
                this.scrollTo(n5, n6);
                if (!this.w(n5)) {
                    this.k.abortAnimation();
                    this.scrollTo(0, n6);
                }
            }
            x0.Y((View)this);
            return;
        }
        this.e(true);
    }

    public boolean d(View view, boolean bl, int n3, int n4, int n5) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n6 = view.getScrollX();
            int n7 = view.getScrollY();
            for (int i3 = viewGroup.getChildCount() - 1; i3 >= 0; --i3) {
                int n8;
                int n9 = n4 + n6;
                View view2 = viewGroup.getChildAt(i3);
                if (n9 < view2.getLeft() || n9 >= view2.getRight() || (n8 = n5 + n7) < view2.getTop() || n8 >= view2.getBottom() || !this.d(view2, true, n3, n9 - view2.getLeft(), n8 - view2.getTop())) continue;
                return true;
            }
        }
        return bl && view.canScrollHorizontally(-n3);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.k(keyEvent);
        {
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            f f3;
            View view = this.getChildAt(i3);
            if (view.getVisibility() != 0 || (f3 = this.m(view)) == null || f3.b != this.g || !view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) continue;
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n3 = this.getOverScrollMode();
        boolean bl = false;
        boolean bl2 = false;
        if (n3 != 0) {
            this.O.finish();
            this.P.finish();
        } else {
            int n4;
            int n5;
            if (!this.O.isFinished()) {
                n3 = canvas.save();
                n5 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                n4 = this.getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float)(-n5 + this.getPaddingTop()), this.q * (float)n4);
                this.O.setSize(n5, n4);
                bl2 = this.O.draw(canvas);
                canvas.restoreToCount(n3);
            }
            bl = bl2;
            if (!this.P.isFinished()) {
                n5 = canvas.save();
                int n6 = this.getWidth();
                n4 = this.getHeight();
                int n7 = this.getPaddingTop();
                n3 = this.getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float)(-this.getPaddingTop()), -(this.r + 1.0f) * (float)n6);
                this.P.setSize(n4 - n7 - n3, n6);
                bl = bl2 | this.P.draw(canvas);
                canvas.restoreToCount(n5);
            }
        }
        if (bl) {
            x0.Y((View)this);
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.n;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(this.getDrawableState());
        }
    }

    public final void e(boolean bl) {
        int n3;
        boolean bl2 = this.f0 == 2;
        if (bl2) {
            this.setScrollingCacheEnabled(false);
            if (!this.k.isFinished()) {
                this.k.abortAnimation();
                int n4 = this.getScrollX();
                n3 = this.getScrollY();
                int n5 = this.k.getCurrX();
                int n6 = this.k.getCurrY();
                if (n4 != n5 || n3 != n6) {
                    this.scrollTo(n5, n6);
                    if (n5 != n4) {
                        this.w(n5);
                    }
                }
            }
        }
        this.w = false;
        for (n3 = 0; n3 < this.d.size(); ++n3) {
            f f3 = (f)this.d.get(n3);
            if (!f3.c) continue;
            f3.c = false;
            bl2 = true;
        }
        if (bl2) {
            if (bl) {
                x0.Z((View)this, this.e0);
                return;
            }
            this.e0.run();
        }
    }

    public final void f(int n3, float f3, int n4) {
        Object object = this.V;
        if (object != null) {
            object.a(n3, f3, n4);
        }
        if ((object = this.U) != null) {
            int n5 = object.size();
            for (int i3 = 0; i3 < n5; ++i3) {
                object = (i)this.U.get(i3);
                if (object == null) continue;
                object.a(n3, f3, n4);
            }
        }
        if ((object = this.W) != null) {
            object.a(n3, f3, n4);
        }
    }

    public final void g(int n3) {
        Object object = this.V;
        if (object != null) {
            object.c(n3);
        }
        if ((object = this.U) != null) {
            int n4 = object.size();
            for (int i3 = 0; i3 < n4; ++i3) {
                object = (i)this.U.get(i3);
                if (object == null) continue;
                object.c(n3);
            }
        }
        if ((object = this.W) != null) {
            object.c(n3);
        }
    }

    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    public p1.a getAdapter() {
        return null;
    }

    public int getChildDrawingOrder(int n3, int n4) {
        int n5 = n4;
        if (this.c0 == 2) {
            n5 = n3 - 1 - n4;
        }
        return ((LayoutParams)((View)this.d0.get((int)n5)).getLayoutParams()).f;
    }

    public int getCurrentItem() {
        return this.g;
    }

    public int getOffscreenPageLimit() {
        return this.x;
    }

    public int getPageMargin() {
        return this.m;
    }

    public final void h(int n3) {
        Object object = this.V;
        if (object != null) {
            object.b(n3);
        }
        if ((object = this.U) != null) {
            int n4 = object.size();
            for (int i3 = 0; i3 < n4; ++i3) {
                object = (i)this.U.get(i3);
                if (object == null) continue;
                object.b(n3);
            }
        }
        if ((object = this.W) != null) {
            object.b(n3);
        }
    }

    public float i(float f3) {
        return (float)Math.sin((f3 - 0.5f) * 0.47123894f);
    }

    public final void j() {
        this.y = false;
        this.z = false;
        VelocityTracker velocityTracker = this.I;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.I = null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean k(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) return false;
        int n3 = keyEvent.getKeyCode();
        if (n3 != 21) {
            if (n3 != 22) {
                if (n3 != 61) return false;
                if (keyEvent.hasNoModifiers()) {
                    return this.c(2);
                }
                if (!keyEvent.hasModifiers(1)) return false;
                return this.c(1);
            }
            if (!keyEvent.hasModifiers(2)) return this.c(66);
            return this.v();
        }
        if (!keyEvent.hasModifiers(2)) return this.c(17);
        return this.u();
    }

    public final Rect l(Rect rect, View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        for (rect = view.getParent(); rect instanceof ViewGroup && rect != this; rect = rect.getParent()) {
            rect = (ViewGroup)rect;
            rect2.left += rect.getLeft();
            rect2.right += rect.getRight();
            rect2.top += rect.getTop();
            rect2.bottom += rect.getBottom();
        }
        return rect2;
    }

    public f m(View object) {
        if (this.d.size() <= 0) {
            return null;
        }
        object = ((f)this.d.get((int)0)).a;
        throw null;
    }

    public final f n() {
        int n3 = this.getClientWidth();
        float f3 = 0.0f;
        float f4 = n3 > 0 ? (float)this.getScrollX() / (float)n3 : 0.0f;
        float f5 = n3 > 0 ? (float)this.m / (float)n3 : 0.0f;
        f f6 = null;
        boolean bl = true;
        int n4 = -1;
        float f7 = 0.0f;
        for (n3 = 0; n3 < this.d.size(); ++n3) {
            int n5;
            f f8 = (f)this.d.get(n3);
            if (!bl && (n5 = f8.b) != ++n4) {
                f6 = this.e;
                f6.e = f3 + f7 + f5;
                f6.b = n4;
                throw null;
            }
            f3 = f8.e;
            f7 = f8.d;
            if (!bl && !(f4 >= f3)) break;
            if (!(f4 < f7 + f3 + f5) && n3 != this.d.size() - 1) {
                n4 = f8.b;
                f7 = f8.d;
                bl = false;
                f6 = f8;
                continue;
            }
            return f8;
        }
        return f6;
    }

    public f o(int n3) {
        for (int i3 = 0; i3 < this.d.size(); ++i3) {
            f f3 = (f)this.d.get(i3);
            if (f3.b != n3) continue;
            return f3;
        }
        return null;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.Q = true;
    }

    public void onDetachedFromWindow() {
        this.removeCallbacks(this.e0);
        Scroller scroller = this.k;
        if (scroller != null && !scroller.isFinished()) {
            this.k.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.m > 0 && this.n != null) {
            this.d.size();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getAction() & 0xFF;
        if (n3 != 3 && n3 != 1) {
            if (n3 != 0) {
                if (this.y) {
                    return true;
                }
                if (this.z) {
                    return false;
                }
            }
            if (n3 != 0) {
                if (n3 != 2) {
                    if (n3 == 6) {
                        this.t(motionEvent);
                    }
                } else {
                    n3 = this.H;
                    if (n3 != -1) {
                        n3 = motionEvent.findPointerIndex(n3);
                        float f3 = motionEvent.getX(n3);
                        float f4 = f3 - this.D;
                        float f5 = Math.abs(f4);
                        float f6 = motionEvent.getY(n3);
                        float f7 = Math.abs(f6 - this.G);
                        float f8 = f4 - 0.0f;
                        float f9 = f8 == 0.0f ? 0 : (f8 > 0.0f ? 1 : -1);
                        if (f9 != false && !this.r(this.D, f4) && this.d((View)this, false, (int)f4, (int)f3, (int)f6)) {
                            this.D = f3;
                            this.E = f6;
                            this.z = true;
                            return false;
                        }
                        n3 = this.C;
                        if (f5 > (float)n3 && f5 * 0.5f > f7) {
                            this.y = true;
                            this.D(true);
                            this.setScrollState(1);
                            f4 = this.F;
                            f5 = this.C;
                            f4 = f9 > 0 ? (f4 += f5) : (f4 -= f5);
                            this.D = f4;
                            this.E = f6;
                            this.setScrollingCacheEnabled(true);
                        } else if (f7 > (float)n3) {
                            this.z = true;
                        }
                        if (this.y && this.x(f3)) {
                            x0.Y((View)this);
                        }
                    }
                }
            } else {
                float f10;
                this.F = f10 = motionEvent.getX();
                this.D = f10;
                this.G = f10 = motionEvent.getY();
                this.E = f10;
                this.H = motionEvent.getPointerId(0);
                this.z = false;
                this.l = true;
                this.k.computeScrollOffset();
                if (this.f0 == 2 && Math.abs(this.k.getFinalX() - this.k.getCurrX()) > this.M) {
                    this.k.abortAnimation();
                    this.w = false;
                    this.y();
                    this.y = true;
                    this.D(true);
                    this.setScrollState(1);
                } else {
                    this.e(false);
                    this.y = false;
                }
            }
            if (this.I == null) {
                this.I = VelocityTracker.obtain();
            }
            this.I.addMovement(motionEvent);
            return this.y;
        }
        this.E();
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        LayoutParams layoutParams;
        int n7;
        int n8;
        int n9 = this.getChildCount();
        int n10 = n5 - n3;
        int n11 = n6 - n4;
        n4 = this.getPaddingLeft();
        n3 = this.getPaddingTop();
        n6 = this.getPaddingRight();
        n5 = this.getPaddingBottom();
        int n12 = this.getScrollX();
        int n13 = 0;
        for (n8 = 0; n8 < n9; ++n8) {
            int n14;
            int n15;
            int n16;
            int n17;
            block9: {
                View view;
                block8: {
                    block15: {
                        block13: {
                            block14: {
                                block10: {
                                    block11: {
                                        block12: {
                                            view = this.getChildAt(n8);
                                            n17 = n4;
                                            n16 = n3;
                                            n15 = n6;
                                            n14 = n5;
                                            n7 = n13;
                                            if (view.getVisibility() == 8) break block9;
                                            layoutParams = (LayoutParams)view.getLayoutParams();
                                            n17 = n4;
                                            n16 = n3;
                                            n15 = n6;
                                            n14 = n5;
                                            n7 = n13;
                                            if (!layoutParams.a) break block9;
                                            n14 = layoutParams.b;
                                            n7 = n14 & 7;
                                            n15 = n14 & 0x70;
                                            if (n7 == 1) break block10;
                                            if (n7 == 3) break block11;
                                            if (n7 == 5) break block12;
                                            n7 = n4;
                                            n14 = n4;
                                            n4 = n7;
                                            break block13;
                                        }
                                        n7 = n10 - n6 - view.getMeasuredWidth();
                                        n6 += view.getMeasuredWidth();
                                        break block14;
                                    }
                                    n7 = view.getMeasuredWidth() + n4;
                                    n14 = n4;
                                    n4 = n7;
                                    break block13;
                                }
                                n7 = Math.max((n10 - view.getMeasuredWidth()) / 2, n4);
                            }
                            n14 = n7;
                        }
                        if (n15 == 16) break block15;
                        if (n15 != 48) {
                            if (n15 != 80) {
                                n15 = n3;
                                n7 = n3;
                                n3 = n15;
                                break block8;
                            } else {
                                n7 = n11 - n5 - view.getMeasuredHeight();
                                n5 += view.getMeasuredHeight();
                            }
                            break block8;
                        } else {
                            n15 = view.getMeasuredHeight() + n3;
                            n7 = n3;
                            n3 = n15;
                        }
                        break block8;
                    }
                    n7 = Math.max((n11 - view.getMeasuredHeight()) / 2, n3);
                }
                view.layout(n14 += n12, n7, view.getMeasuredWidth() + n14, n7 + view.getMeasuredHeight());
                n7 = n13 + 1;
                n14 = n5;
                n15 = n6;
                n16 = n3;
                n17 = n4;
            }
            n4 = n17;
            n3 = n16;
            n6 = n15;
            n5 = n14;
            n13 = n7;
        }
        for (n7 = 0; n7 < n9; ++n7) {
            f f3;
            View view = this.getChildAt(n7);
            if (view.getVisibility() == 8) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.a || (f3 = this.m(view)) == null) continue;
            float f4 = n10 - n4 - n6;
            n8 = (int)(f3.e * f4) + n4;
            if (layoutParams.d) {
                layoutParams.d = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)(f4 * layoutParams.c)), (int)0x40000000), View.MeasureSpec.makeMeasureSpec((int)(n11 - n3 - n5), (int)0x40000000));
            }
            view.layout(n8, n3, view.getMeasuredWidth() + n8, view.getMeasuredHeight() + n3);
        }
        this.o = n3;
        this.p = n11 - n5;
        this.T = n13;
        if (this.Q) {
            this.F(this.g, false, 0, false);
        }
        this.Q = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onMeasure(int n3, int n4) {
        LayoutParams layoutParams;
        int n5;
        View view;
        int n6 = 0;
        this.setMeasuredDimension(View.getDefaultSize((int)0, (int)n3), View.getDefaultSize((int)0, (int)n4));
        n3 = this.getMeasuredWidth();
        this.B = Math.min(n3 / 10, this.A);
        n3 = n3 - this.getPaddingLeft() - this.getPaddingRight();
        n4 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
        int n7 = this.getChildCount();
        int n8 = 0;
        while (true) {
            int n9 = 1;
            int n10 = 0x40000000;
            if (n8 >= n7) break;
            view = this.getChildAt(n8);
            n5 = n3;
            int n11 = n4;
            if (view.getVisibility() != 8) {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n5 = n3;
                n11 = n4;
                if (layoutParams != null) {
                    n5 = n3;
                    n11 = n4;
                    if (layoutParams.a) {
                        int n12;
                        n11 = layoutParams.b;
                        n5 = n11 & 7;
                        boolean bl = (n11 &= 0x70) == 48 || n11 == 80;
                        int n13 = n9;
                        if (n5 != 3) {
                            n13 = n5 == 5 ? n9 : 0;
                        }
                        n11 = Integer.MIN_VALUE;
                        if (bl) {
                            n5 = Integer.MIN_VALUE;
                            n11 = 0x40000000;
                        } else {
                            n5 = n13 != 0 ? 0x40000000 : Integer.MIN_VALUE;
                        }
                        n9 = layoutParams.width;
                        if (n9 != -2) {
                            n11 = n9 != -1 ? n9 : n3;
                            n12 = 0x40000000;
                            n9 = n11;
                        } else {
                            n9 = n3;
                            n12 = n11;
                        }
                        n11 = layoutParams.height;
                        if (n11 != -2) {
                            n5 = n11 != -1 ? n11 : n4;
                        } else {
                            n11 = n4;
                            n10 = n5;
                            n5 = n11;
                        }
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)n9, (int)n12), View.MeasureSpec.makeMeasureSpec((int)n5, (int)n10));
                        if (bl) {
                            n11 = n4 - view.getMeasuredHeight();
                            n5 = n3;
                        } else {
                            n5 = n3;
                            n11 = n4;
                            if (n13 != 0) {
                                n5 = n3 - view.getMeasuredWidth();
                                n11 = n4;
                            }
                        }
                    }
                }
            }
            ++n8;
            n3 = n5;
            n4 = n11;
        }
        this.s = View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000);
        this.t = View.MeasureSpec.makeMeasureSpec((int)n4, (int)0x40000000);
        this.u = true;
        this.y();
        this.u = false;
        n5 = this.getChildCount();
        n4 = n6;
        while (n4 < n5) {
            view = this.getChildAt(n4);
            if (!(view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) != null && layoutParams.a)) {
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)n3 * layoutParams.c)), (int)0x40000000), this.t);
            }
            ++n4;
        }
        return;
    }

    public boolean onRequestFocusInDescendants(int n3, Rect rect) {
        int n4;
        int n5;
        int n6 = this.getChildCount();
        if ((n3 & 2) != 0) {
            n5 = 0;
            n4 = 1;
        } else {
            n5 = n6 - 1;
            n6 = -1;
            n4 = -1;
        }
        while (n5 != n6) {
            f f3;
            View view = this.getChildAt(n5);
            if (view.getVisibility() == 0 && (f3 = this.m(view)) != null && f3.b == this.g && view.requestFocus(n3, rect)) {
                return true;
            }
            n5 += n4;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.h = parcelable.e;
        this.i = parcelable.f;
        this.j = parcelable.g;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.g;
        return savedState;
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        if (n3 != n5) {
            n4 = this.m;
            this.A(n3, n5, n4, n4);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.N) {
            return true;
        }
        if (motionEvent.getAction() == 0) {
            motionEvent.getEdgeFlags();
        }
        return false;
    }

    public void p() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        Context context = this.getContext();
        this.k = new Scroller(context, i0);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        float f3 = context.getResources().getDisplayMetrics().density;
        this.C = viewConfiguration.getScaledPagingTouchSlop();
        this.J = (int)(400.0f * f3);
        this.K = viewConfiguration.getScaledMaximumFlingVelocity();
        this.O = new EdgeEffect(context);
        this.P = new EdgeEffect(context);
        this.L = (int)(25.0f * f3);
        this.M = (int)(2.0f * f3);
        this.A = (int)(f3 * 16.0f);
        x0.h0((View)this, new g(this));
        if (x0.w((View)this) == 0) {
            x0.o0((View)this, 1);
        }
        x0.r0((View)this, new f0(this){
            public final Rect a;
            public final ViewPager b;
            {
                this.b = viewPager;
                this.a = new Rect();
            }

            @Override
            public z1 a(View object, z1 z12) {
                if (((z1)(object = x0.T((View)object, z12))).p()) {
                    return object;
                }
                Rect rect = this.a;
                rect.left = ((z1)object).j();
                rect.top = ((z1)object).l();
                rect.right = ((z1)object).k();
                rect.bottom = ((z1)object).i();
                int n3 = this.b.getChildCount();
                for (int i3 = 0; i3 < n3; ++i3) {
                    z12 = x0.g(this.b.getChildAt(i3), (z1)object);
                    rect.left = Math.min(z12.j(), rect.left);
                    rect.top = Math.min(z12.l(), rect.top);
                    rect.right = Math.min(z12.k(), rect.right);
                    rect.bottom = Math.min(z12.i(), rect.bottom);
                }
                return ((z1)object).q(rect.left, rect.top, rect.right, rect.bottom);
            }
        });
    }

    public final boolean r(float f3, float f4) {
        return f3 < (float)this.B && f4 > 0.0f || f3 > (float)(this.getWidth() - this.B) && f4 < 0.0f;
    }

    public void removeView(View view) {
        if (this.u) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    public void s(int n3, float f3, int n4) {
        if (this.T > 0) {
            int n5 = this.getScrollX();
            int n6 = this.getPaddingLeft();
            int n7 = this.getPaddingRight();
            int n8 = this.getWidth();
            int n9 = this.getChildCount();
            for (int i3 = 0; i3 < n9; ++i3) {
                View view = this.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.a) continue;
                int n10 = layoutParams.b & 7;
                if (n10 != 1) {
                    int n11;
                    if (n10 != 3) {
                        if (n10 != 5) {
                            n11 = n6;
                            n10 = n6;
                            n6 = n11;
                        } else {
                            n10 = n8 - n7 - view.getMeasuredWidth();
                            n7 += view.getMeasuredWidth();
                        }
                    } else {
                        n11 = view.getWidth() + n6;
                        n10 = n6;
                        n6 = n11;
                    }
                } else {
                    n10 = Math.max((n8 - view.getMeasuredWidth()) / 2, n6);
                }
                n10 = n10 + n5 - view.getLeft();
                if (n10 == 0) continue;
                view.offsetLeftAndRight(n10);
            }
        }
        this.f(n3, f3, n4);
        this.S = true;
    }

    public void setAdapter(p1.a a4) {
        this.c = 0;
        List list = this.a0;
        if (list != null && !list.isEmpty()) {
            int n3 = this.a0.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ((h)this.a0.get(i3)).d(this, null, a4);
            }
        }
    }

    public void setCurrentItem(int n3) {
        this.w = false;
        this.G(n3, this.Q ^ true, false);
    }

    public void setCurrentItem(int n3, boolean bl) {
        this.w = false;
        this.G(n3, bl, false);
    }

    public void setOffscreenPageLimit(int n3) {
        int n4 = n3;
        if (n3 < 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Requested offscreen page limit ");
            stringBuilder.append(n3);
            stringBuilder.append(" too small; defaulting to ");
            stringBuilder.append(1);
            Log.w((String)"ViewPager", (String)stringBuilder.toString());
            n4 = 1;
        }
        if (n4 != this.x) {
            this.x = n4;
            this.y();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(i i3) {
        this.V = i3;
    }

    public void setPageMargin(int n3) {
        int n4 = this.m;
        this.m = n3;
        int n5 = this.getWidth();
        this.A(n5, n5, n3, n4);
        this.requestLayout();
    }

    public void setPageMarginDrawable(int n3) {
        this.setPageMarginDrawable(a.d(this.getContext(), n3));
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.n = drawable;
        if (drawable != null) {
            this.refreshDrawableState();
        }
        boolean bl = drawable == null;
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    public void setPageTransformer(boolean bl, j j3) {
        this.setPageTransformer(bl, j3, 2);
    }

    public void setPageTransformer(boolean bl, j j3, int n3) {
        int n4 = 1;
        boolean bl2 = j3 != null;
        this.setChildrenDrawingOrderEnabled(bl2);
        if (bl2) {
            if (bl) {
                n4 = 2;
            }
            this.c0 = n4;
            this.b0 = n3;
        } else {
            this.c0 = 0;
        }
        if (bl2) {
            this.y();
        }
    }

    public void setScrollState(int n3) {
        if (this.f0 == n3) {
            return;
        }
        this.f0 = n3;
        this.h(n3);
    }

    public final void t(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n3) == this.H) {
            n3 = n3 == 0 ? 1 : 0;
            this.D = motionEvent.getX(n3);
            this.H = motionEvent.getPointerId(n3);
            motionEvent = this.I;
            if (motionEvent != null) {
                motionEvent.clear();
            }
        }
    }

    public boolean u() {
        int n3 = this.g;
        if (n3 > 0) {
            this.setCurrentItem(n3 - 1, true);
            return true;
        }
        return false;
    }

    public boolean v() {
        return false;
    }

    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.n;
        {
        }
    }

    public final boolean w(int n3) {
        if (this.d.size() == 0) {
            if (this.Q) {
                return false;
            }
            this.S = false;
            this.s(0, 0.0f, 0);
            if (this.S) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        f f3 = this.n();
        int n4 = this.getClientWidth();
        int n5 = this.m;
        float f4 = n5;
        float f5 = n4;
        f4 /= f5;
        int n6 = f3.b;
        f5 = ((float)n3 / f5 - f3.e) / (f3.d + f4);
        n3 = (int)((float)(n4 + n5) * f5);
        this.S = false;
        this.s(n6, f5, n3);
        if (this.S) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    public final boolean x(float f3) {
        this.D = f3;
        this.getScrollX();
        this.getClientWidth();
        f f4 = (f)this.d.get(0);
        Object object = this.d;
        object = (f)((ArrayList)object).get(((ArrayList)object).size() - 1);
        int n3 = f4.b;
        n3 = ((f)object).b;
        throw null;
    }

    public void y() {
        this.z(this.g);
    }

    public void z(int n3) {
        int n4 = this.g;
        if (n4 != n3) {
            this.o(n4);
            this.g = n3;
        }
        this.K();
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        public boolean a;
        public int b;
        public float c = 0.0f;
        public boolean d;
        public int e;
        public int f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, g0);
            this.b = context.getInteger(0, 48);
            context.recycle();
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
        public int e;
        public Parcelable f;
        public ClassLoader g;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.e = parcel.readInt();
            this.f = parcel.readParcelable(classLoader2);
            this.g = classLoader2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FragmentPager.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" position=");
            stringBuilder.append(this.e);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e);
            parcel.writeParcelable(this.f, n3);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    public static @interface e {
    }

    public static class f {
        public Object a;
        public int b;
        public boolean c;
        public float d;
        public float e;
    }

    public class g
    extends o0.a {
        public final ViewPager d;

        public g(ViewPager viewPager) {
            this.d = viewPager;
        }

        @Override
        public void f(View view, AccessibilityEvent accessibilityEvent) {
            super.f(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ViewPager.class.getName());
            accessibilityEvent.setScrollable(this.n());
            if (accessibilityEvent.getEventType() == 4096) {
                ((Object)((Object)this.d)).getClass();
            }
        }

        @Override
        public void g(View view, s s3) {
            super.g(view, s3);
            s3.h0(ViewPager.class.getName());
            s3.B0(this.n());
            if (this.d.canScrollHorizontally(1)) {
                s3.a(4096);
            }
            if (this.d.canScrollHorizontally(-1)) {
                s3.a(8192);
            }
        }

        @Override
        public boolean j(View object, int n3, Bundle bundle) {
            if (super.j((View)object, n3, bundle)) {
                return true;
            }
            if (n3 != 4096) {
                if (n3 != 8192) {
                    return false;
                }
                if (this.d.canScrollHorizontally(-1)) {
                    object = this.d;
                    ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)((Object)object)).g - 1);
                    return true;
                }
                return false;
            }
            if (this.d.canScrollHorizontally(1)) {
                object = this.d;
                ((ViewPager)((Object)object)).setCurrentItem(((ViewPager)((Object)object)).g + 1);
                return true;
            }
            return false;
        }

        public final boolean n() {
            ((Object)((Object)this.d)).getClass();
            return false;
        }
    }

    public static interface h {
        public void d(ViewPager var1, p1.a var2, p1.a var3);
    }

    public static interface i {
        public void a(int var1, float var2, int var3);

        public void b(int var1);

        public void c(int var1);
    }

    public static interface j {
    }

    public static class k
    implements Comparator {
        public int a(View object, View object2) {
            object = (LayoutParams)object.getLayoutParams();
            object2 = (LayoutParams)object2.getLayoutParams();
            boolean bl = object.a;
            if (bl != object2.a) {
                if (bl) {
                    return 1;
                }
                return -1;
            }
            return object.e - object2.e;
        }
    }
}

