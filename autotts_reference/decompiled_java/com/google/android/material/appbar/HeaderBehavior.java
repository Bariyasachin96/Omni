/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.widget.OverScroller
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.ViewOffsetBehavior;

abstract class HeaderBehavior<V extends View>
extends ViewOffsetBehavior<V> {
    public Runnable f;
    public OverScroller g;
    public boolean h;
    public int i = -1;
    public int j;
    public int k = -1;
    public VelocityTracker l;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean H(CoordinatorLayout var1_1, View var2_2, MotionEvent var3_3) {
        block10: {
            block7: {
                block9: {
                    block8: {
                        var5_4 = var3_3.getActionMasked();
                        if (var5_4 == 1) break block7;
                        if (var5_4 == 2) break block8;
                        if (var5_4 == 3) ** GOTO lbl-1000
                        if (var5_4 == 6) {
                            var5_4 = var3_3.getActionIndex() == 0 ? 1 : 0;
                            this.i = var3_3.getPointerId(var5_4);
                            this.j = (int)(var3_3.getY(var5_4) + 0.5f);
                        }
                        break block9;
                    }
                    var5_4 = var3_3.findPointerIndex(this.i);
                    if (var5_4 == -1) {
                        return false;
                    }
                    var5_4 = (int)var3_3.getY(var5_4);
                    var6_5 = this.j;
                    this.j = var5_4;
                    this.S(var1_1, var2_2, var6_5 - var5_4, this.O(var2_2), 0);
                }
                var6_5 = 0;
                break block10;
            }
            var7_6 = this.l;
            if (var7_6 != null) {
                var7_6.addMovement(var3_3);
                this.l.computeCurrentVelocity(1000);
                var4_7 = this.l.getYVelocity(this.i);
                this.N(var1_1, var2_2, -this.P(var2_2), 0, var4_7);
                var5_4 = 1;
            } else lbl-1000:
            // 2 sources

            {
                var5_4 = 0;
            }
            this.h = false;
            this.i = -1;
            var1_1 = this.l;
            var6_5 = var5_4;
            if (var1_1 != null) {
                var1_1.recycle();
                this.l = null;
                var6_5 = var5_4;
            }
        }
        var1_1 = this.l;
        if (var1_1 != null) {
            var1_1.addMovement(var3_3);
        }
        return this.h || var6_5 != 0;
        {
        }
    }

    public boolean L(View view) {
        return false;
    }

    public final void M() {
        if (this.l == null) {
            this.l = VelocityTracker.obtain();
        }
    }

    public final boolean N(CoordinatorLayout object, View view, int n3, int n4, float f3) {
        Runnable runnable = this.f;
        if (runnable != null) {
            view.removeCallbacks(runnable);
            this.f = null;
        }
        if (this.g == null) {
            this.g = new OverScroller(view.getContext());
        }
        this.g.fling(0, this.I(), 0, Math.round(f3), 0, 0, n3, n4);
        if (this.g.computeScrollOffset()) {
            this.f = object = new a(this, (CoordinatorLayout)object, view);
            view.postOnAnimation((Runnable)object);
            return true;
        }
        this.R((CoordinatorLayout)object, view);
        return false;
    }

    public int O(View view) {
        return -view.getHeight();
    }

    public int P(View view) {
        return view.getHeight();
    }

    public int Q() {
        return this.I();
    }

    public void R(CoordinatorLayout coordinatorLayout, View view) {
    }

    public final int S(CoordinatorLayout coordinatorLayout, View view, int n3, int n4, int n5) {
        return this.U(coordinatorLayout, view, this.Q() - n3, n4, n5);
    }

    public int T(CoordinatorLayout coordinatorLayout, View view, int n3) {
        return this.U(coordinatorLayout, view, n3, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int U(CoordinatorLayout coordinatorLayout, View view, int n3, int n4, int n5) {
        int n6 = this.I();
        if (n4 != 0 && n6 >= n4 && n6 <= n5 && n6 != (n3 = j0.a.b(n3, n4, n5))) {
            this.K(n3);
            return n6 - n3;
        }
        return 0;
    }

    @Override
    public boolean o(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int n3;
        if (this.k < 0) {
            this.k = ViewConfiguration.get((Context)coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.h) {
            n3 = this.i;
            if (n3 == -1) {
                return false;
            }
            if ((n3 = motionEvent.findPointerIndex(n3)) == -1) {
                return false;
            }
            if (Math.abs((n3 = (int)motionEvent.getY(n3)) - this.j) > this.k) {
                this.j = n3;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.i = -1;
            int n4 = (int)motionEvent.getX();
            n3 = (int)motionEvent.getY();
            boolean bl = this.L(view) && coordinatorLayout.F(view, n4, n3);
            this.h = bl;
            if (bl) {
                this.j = n3;
                this.i = motionEvent.getPointerId(0);
                this.M();
                coordinatorLayout = this.g;
                if (coordinatorLayout != null && !coordinatorLayout.isFinished()) {
                    this.g.abortAnimation();
                    return true;
                }
            }
        }
        if ((coordinatorLayout = this.l) != null) {
            coordinatorLayout.addMovement(motionEvent);
        }
        return false;
    }

    public class a
    implements Runnable {
        public final CoordinatorLayout c;
        public final View d;
        public final HeaderBehavior e;

        public a(HeaderBehavior headerBehavior, CoordinatorLayout coordinatorLayout, View view) {
            this.e = headerBehavior;
            this.c = coordinatorLayout;
            this.d = view;
        }

        @Override
        public void run() {
            Object object;
            if (this.d != null && (object = this.e.g) != null) {
                if (object.computeScrollOffset()) {
                    object = this.e;
                    ((HeaderBehavior)object).T(this.c, this.d, ((HeaderBehavior)object).g.getCurrY());
                    this.d.postOnAnimation((Runnable)this);
                    return;
                }
                this.e.R(this.c, this.d);
            }
        }
    }
}

