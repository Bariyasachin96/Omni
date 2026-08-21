/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.AttributeSet
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$AccessibilityStateChangeListener
 */
package com.google.android.material.bottomsheet;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import c2.c;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import o0.a;
import o0.x0;
import p0.s;
import p0.v;
import z1.k;
import z1.l;

public class BottomSheetDragHandleView
extends AppCompatImageView
implements AccessibilityManager.AccessibilityStateChangeListener {
    public static final int p = z1.l.Widget_Material3_BottomSheet_DragHandle;
    public final AccessibilityManager f;
    public BottomSheetBehavior g;
    public final GestureDetector h;
    public boolean i;
    public boolean j = false;
    public boolean k = false;
    public final String l = this.getResources().getString(z1.k.bottomsheet_action_expand);
    public final String m = this.getResources().getString(z1.k.bottomsheet_action_collapse);
    public final BottomSheetBehavior.g n = new BottomSheetBehavior.g(this){
        public final BottomSheetDragHandleView a;
        {
            this.a = bottomSheetDragHandleView;
        }

        @Override
        public void b(View view, float f3) {
        }

        @Override
        public void c(View view, int n3) {
            this.a.k(n3);
        }
    };
    public final GestureDetector.OnGestureListener o;

    public BottomSheetDragHandleView(Context context) {
        this(context, null);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.bottomSheetDragHandleStyle);
    }

    public BottomSheetDragHandleView(Context object, AttributeSet attributeSet, int n3) {
        super(y2.a.d(object, attributeSet, n3, p), attributeSet, n3);
        object = new GestureDetector.SimpleOnGestureListener(this){
            public final BottomSheetDragHandleView a;
            {
                this.a = bottomSheetDragHandleView;
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (this.a.g != null && this.a.g.z0()) {
                    this.a.g.a1(5);
                    return true;
                }
                return super.onDoubleTap(motionEvent);
            }

            public boolean onDown(MotionEvent motionEvent) {
                return this.a.isClickable();
            }

            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return this.a.g();
            }
        };
        this.o = object;
        attributeSet = this.getContext();
        this.h = new GestureDetector((Context)attributeSet, (GestureDetector.OnGestureListener)object, new Handler(Looper.getMainLooper()));
        this.f = (AccessibilityManager)attributeSet.getSystemService("accessibility");
        x0.h0((View)this, new a(this){
            public final BottomSheetDragHandleView d;
            {
                this.d = bottomSheetDragHandleView;
            }

            @Override
            public void h(View view, AccessibilityEvent accessibilityEvent) {
                super.h(view, accessibilityEvent);
                if (accessibilityEvent.getEventType() == 1) {
                    this.d.g();
                }
            }
        });
    }

    public static /* synthetic */ boolean c(BottomSheetDragHandleView bottomSheetDragHandleView, View view, v.a a4) {
        return bottomSheetDragHandleView.g();
    }

    public static View i(View view) {
        if ((view = view.getParent()) instanceof View) {
            return view;
        }
        return null;
    }

    private void setBottomSheetBehavior(BottomSheetBehavior<?> bottomSheetBehavior) {
        BottomSheetBehavior bottomSheetBehavior2 = this.g;
        if (bottomSheetBehavior2 != null) {
            bottomSheetBehavior2.F0(this.n);
            this.g.K0(null);
            this.g.L0(null);
        }
        this.g = bottomSheetBehavior;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.K0((View)this);
            this.g.L0(this);
            this.k(this.g.s0());
            this.g.c0(this.n);
        }
        this.setClickable(this.j());
    }

    public final boolean g() {
        boolean bl = this.j();
        int n3 = 0;
        if (!bl) {
            return false;
        }
        int n4 = n3;
        if (!this.g.x0()) {
            n4 = n3;
            if (!this.g.g1()) {
                n4 = 1;
            }
        }
        int n5 = this.g.s0();
        int n6 = 6;
        n3 = 3;
        if (n5 == 4) {
            if (n4 != 0) {
                n3 = n6;
            }
        } else if (n5 == 3) {
            n3 = n4 != 0 ? n6 : 4;
        } else if (!this.i) {
            n3 = 4;
        }
        this.g.a1(n3);
        return true;
    }

    public final BottomSheetBehavior h() {
        View view;
        BottomSheetDragHandleView bottomSheetDragHandleView = this;
        while ((view = BottomSheetDragHandleView.i((View)bottomSheetDragHandleView)) != null) {
            Object object = view.getLayoutParams();
            bottomSheetDragHandleView = view;
            if (!(object instanceof CoordinatorLayout.e)) continue;
            object = ((CoordinatorLayout.e)((Object)object)).f();
            bottomSheetDragHandleView = view;
            if (!(object instanceof BottomSheetBehavior)) continue;
            return (BottomSheetBehavior)object;
        }
        return null;
    }

    public final boolean j() {
        return this.g != null;
    }

    public final void k(int n3) {
        if (n3 == 4) {
            this.i = true;
        } else if (n3 == 3) {
            this.i = false;
        }
        s.a a4 = s.a.i;
        String string = this.i ? this.l : this.m;
        x0.d0((View)this, a4, string, new c(this));
    }

    public void onAccessibilityStateChanged(boolean bl) {
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setBottomSheetBehavior(this.h());
        AccessibilityManager accessibilityManager = this.f;
        if (accessibilityManager != null) {
            accessibilityManager.addAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener)this);
            this.onAccessibilityStateChanged(this.f.isEnabled());
        }
    }

    public void onDetachedFromWindow() {
        AccessibilityManager accessibilityManager = this.f;
        if (accessibilityManager != null) {
            accessibilityManager.removeAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener)this);
        }
        this.setBottomSheetBehavior(null);
        super.onDetachedFromWindow();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.k && !this.j) {
            return this.h.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        boolean bl = onClickListener != null;
        this.k = bl;
        super.setOnClickListener(onClickListener);
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        boolean bl = onTouchListener != null;
        this.j = bl;
        super.setOnTouchListener(onTouchListener);
    }
}

