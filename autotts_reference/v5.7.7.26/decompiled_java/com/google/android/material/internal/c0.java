/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 */
package com.google.android.material.internal;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.internal.b0;
import e0.a;
import o0.f0;
import o0.x0;
import o0.y2;
import o0.z1;
import z1.m;

public abstract class c0 {
    public static /* synthetic */ void a(View view, boolean bl) {
        c0.q(view, bl);
    }

    public static Rect b(View view, View view2) {
        int[] nArray = new int[2];
        view2.getLocationOnScreen(nArray);
        int n3 = nArray[0];
        int n4 = nArray[1];
        nArray = new int[2];
        view.getLocationOnScreen(nArray);
        int n5 = nArray[0];
        int n6 = nArray[1];
        return new Rect(n3 -= n5, n4 -= n6, view2.getWidth() + n3, view2.getHeight() + n4);
    }

    public static Rect c(View view) {
        return c0.d(view, 0);
    }

    public static Rect d(View view, int n3) {
        return new Rect(view.getLeft(), view.getTop() + n3, view.getRight(), view.getBottom() + n3);
    }

    public static void e(View view, AttributeSet attributeSet, int n3, int n4, d d3) {
        attributeSet = view.getContext().obtainStyledAttributes(attributeSet, m.Insets, n3, n4);
        boolean bl = attributeSet.getBoolean(m.Insets_paddingBottomSystemWindowInsets, false);
        boolean bl2 = attributeSet.getBoolean(m.Insets_paddingLeftSystemWindowInsets, false);
        boolean bl3 = attributeSet.getBoolean(m.Insets_paddingRightSystemWindowInsets, false);
        attributeSet.recycle();
        c0.f(view, new d(bl, bl2, bl3, d3){
            public final boolean a;
            public final boolean b;
            public final boolean c;
            public final d d;
            {
                this.a = bl;
                this.b = bl2;
                this.c = bl3;
                this.d = d3;
            }

            @Override
            public z1 a(View view, z1 z12, e e3) {
                if (this.a) {
                    e3.d += z12.i();
                }
                boolean bl = c0.m(view);
                if (this.b) {
                    if (bl) {
                        e3.c += z12.j();
                    } else {
                        e3.a += z12.j();
                    }
                }
                if (this.c) {
                    if (bl) {
                        e3.a += z12.k();
                    } else {
                        e3.c += z12.k();
                    }
                }
                e3.a(view);
                d d3 = this.d;
                if (d3 != null) {
                    return d3.a(view, z12, e3);
                }
                return z12;
            }
        });
    }

    public static void f(View view, d d3) {
        x0.r0(view, new f0(d3, new e(view.getPaddingStart(), view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom())){
            public final d a;
            public final e b;
            {
                this.a = d3;
                this.b = e3;
            }

            @Override
            public z1 a(View view, z1 z12) {
                return this.a.a(view, z12, new e(this.b));
            }
        });
        c0.o(view);
    }

    public static float g(Context context, int n3) {
        context = context.getResources();
        return TypedValue.applyDimension((int)1, (float)n3, (DisplayMetrics)context.getDisplayMetrics());
    }

    public static Integer h(View view) {
        if ((view = j2.d.g(view.getBackground())) != null) {
            return view.getDefaultColor();
        }
        return null;
    }

    public static ViewGroup i(View view) {
        if (view == null) {
            return null;
        }
        View view2 = view.getRootView();
        ViewGroup viewGroup = (ViewGroup)view2.findViewById(0x1020002);
        if (viewGroup != null) {
            return viewGroup;
        }
        if (view2 != view && view2 instanceof ViewGroup) {
            return (ViewGroup)view2;
        }
        return null;
    }

    public static InputMethodManager j(View view) {
        return (InputMethodManager)a.g(view.getContext(), InputMethodManager.class);
    }

    public static float k(View view) {
        view = view.getParent();
        float f3 = 0.0f;
        while (view instanceof View) {
            f3 += view.getElevation();
            view = view.getParent();
        }
        return f3;
    }

    public static void l(View view, boolean bl) {
        y2 y22;
        if (bl && (y22 = x0.H(view)) != null) {
            y22.a(z1.m.b());
            return;
        }
        y22 = c0.j(view);
        if (y22 != null) {
            y22.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean m(View view) {
        return view.getLayoutDirection() == 1;
    }

    public static PorterDuff.Mode n(int n3, PorterDuff.Mode mode) {
        if (n3 != 3) {
            if (n3 != 5) {
                if (n3 != 9) {
                    switch (n3) {
                        default: {
                            return mode;
                        }
                        case 16: {
                            return PorterDuff.Mode.ADD;
                        }
                        case 15: {
                            return PorterDuff.Mode.SCREEN;
                        }
                        case 14: 
                    }
                    return PorterDuff.Mode.MULTIPLY;
                }
                return PorterDuff.Mode.SRC_ATOP;
            }
            return PorterDuff.Mode.SRC_IN;
        }
        return PorterDuff.Mode.SRC_OVER;
    }

    public static void o(View view) {
        if (view.isAttachedToWindow()) {
            view.requestApplyInsets();
            return;
        }
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(){

            public void onViewAttachedToWindow(View view) {
                view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
                view.requestApplyInsets();
            }

            public void onViewDetachedFromWindow(View view) {
            }
        });
    }

    public static void p(View view, boolean bl) {
        view.requestFocus();
        view.post((Runnable)new b0(view, bl));
    }

    public static void q(View view, boolean bl) {
        y2 y22;
        if (bl && (y22 = x0.H(view)) != null) {
            y22.d(z1.m.b());
            return;
        }
        c0.j(view).showSoftInput(view, 1);
    }

    public static interface d {
        public z1 a(View var1, z1 var2, e var3);
    }

    public static class e {
        public int a;
        public int b;
        public int c;
        public int d;

        public e(int n3, int n4, int n5, int n6) {
            this.a = n3;
            this.b = n4;
            this.c = n5;
            this.d = n6;
        }

        public e(e e3) {
            this.a = e3.a;
            this.b = e3.b;
            this.c = e3.c;
            this.d = e3.d;
        }

        public void a(View view) {
            view.setPaddingRelative(this.a, this.b, this.c, this.d);
        }
    }
}

