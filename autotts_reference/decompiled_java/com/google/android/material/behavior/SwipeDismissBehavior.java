/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import o0.x0;
import p0.s;
import p0.v;
import v0.c;

public class SwipeDismissBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    public v0.c c;
    public boolean d;
    public boolean e;
    public float f = 0.0f;
    public boolean g;
    public int h = 2;
    public float i = 0.5f;
    public float j = 0.0f;
    public float k = 0.5f;
    public final c.c l = new c.c(this){
        public int a;
        public int b;
        public final SwipeDismissBehavior c;
        {
            this.c = swipeDismissBehavior;
            this.b = -1;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int a(View view, int n3, int n4) {
            n4 = view.getLayoutDirection() == 1 ? 1 : 0;
            int n5 = this.c.h;
            if (n5 == 0) {
                if (n4 != 0) {
                    n5 = this.a - view.getWidth();
                    n4 = this.a;
                    return SwipeDismissBehavior.L(n5, n3, n4);
                }
                n5 = this.a;
                n4 = view.getWidth();
                return SwipeDismissBehavior.L(n5, n3, n4 += n5);
            } else {
                if (n5 != 1) {
                    n5 = this.a - view.getWidth();
                    n4 = this.a;
                    n4 = view.getWidth() + n4;
                    return SwipeDismissBehavior.L(n5, n3, n4);
                }
                if (n4 == 0) {
                    n5 = this.a - view.getWidth();
                    n4 = this.a;
                    return SwipeDismissBehavior.L(n5, n3, n4);
                }
                n5 = this.a;
                n4 = view.getWidth();
            }
            return SwipeDismissBehavior.L(n5, n3, n4 += n5);
        }

        @Override
        public int b(View view, int n3, int n4) {
            return view.getTop();
        }

        @Override
        public int d(View view) {
            return view.getWidth();
        }

        @Override
        public void i(View view, int n3) {
            this.b = n3;
            this.a = view.getLeft();
            if ((view = view.getParent()) != null) {
                SwipeDismissBehavior.I(this.c, true);
                view.requestDisallowInterceptTouchEvent(true);
                SwipeDismissBehavior.I(this.c, false);
            }
        }

        @Override
        public void j(int n3) {
            this.c.getClass();
        }

        @Override
        public void k(View view, int n3, int n4, int n5, int n6) {
            float f3 = (float)view.getWidth() * this.c.j;
            float f4 = (float)view.getWidth() * this.c.k;
            float f5 = Math.abs(n3 - this.a);
            if (f5 <= f3) {
                view.setAlpha(1.0f);
                return;
            }
            if (f5 >= f4) {
                view.setAlpha(0.0f);
                return;
            }
            view.setAlpha(SwipeDismissBehavior.K(0.0f, 1.0f - SwipeDismissBehavior.N(f3, f4, f5), 1.0f));
        }

        @Override
        public void l(View view, float f3, float f4) {
            boolean bl;
            int n3;
            this.b = -1;
            int n4 = view.getWidth();
            if (this.n(view, f3)) {
                int n5;
                n3 = !(f3 < 0.0f) && (n3 = view.getLeft()) >= (n5 = this.a) ? n5 + n4 : this.a - n4;
                bl = true;
            } else {
                n3 = this.a;
                bl = false;
            }
            if (this.c.c.O(n3, view.getTop())) {
                view.postOnAnimation((Runnable)new c(this.c, view, bl));
                return;
            }
            if (bl) {
                this.c.getClass();
            }
        }

        @Override
        public boolean m(View view, int n3) {
            int n4 = this.b;
            return (n4 == -1 || n4 == n3) && this.c.J(view);
        }

        public final boolean n(View view, float f3) {
            float f4 = f3 - 0.0f;
            float f5 = f4 == 0.0f ? 0 : (f4 > 0.0f ? 1 : -1);
            if (f5 != false) {
                boolean bl = view.getLayoutDirection() == 1;
                int n3 = this.c.h;
                if (n3 == 2) {
                    return true;
                }
                if (n3 == 0) {
                    if (bl) {
                        return f3 < 0.0f;
                    }
                    return f5 > 0;
                }
                if (n3 == 1) {
                    if (bl) {
                        return f5 > 0;
                    }
                    if (f3 < 0.0f) {
                        return true;
                    }
                }
                return false;
            }
            int n4 = view.getLeft();
            f5 = this.a;
            int n5 = Math.round((float)view.getWidth() * this.c.i);
            return Math.abs(n4 - f5) >= n5;
        }
    };

    public static /* synthetic */ boolean I(SwipeDismissBehavior swipeDismissBehavior, boolean bl) {
        swipeDismissBehavior.e = bl;
        return bl;
    }

    public static float K(float f3, float f4, float f5) {
        return Math.min(Math.max(f3, f4), f5);
    }

    public static int L(int n3, int n4, int n5) {
        return Math.min(Math.max(n3, n4), n5);
    }

    public static float N(float f3, float f4, float f5) {
        return (f5 - f3) / (f4 - f3);
    }

    @Override
    public boolean H(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (this.c != null) {
            if (!this.e || motionEvent.getActionMasked() != 3) {
                this.c.F(motionEvent);
            }
            return true;
        }
        return false;
    }

    public boolean J(View view) {
        return true;
    }

    public final void M(ViewGroup object) {
        if (this.c == null) {
            object = this.g ? v0.c.n(object, this.f, this.l) : v0.c.o(object, this.l);
            this.c = object;
        }
    }

    public void O(float f3) {
        this.k = SwipeDismissBehavior.K(0.0f, f3, 1.0f);
    }

    public void P(float f3) {
        this.j = SwipeDismissBehavior.K(0.0f, f3, 1.0f);
    }

    public void Q(int n3) {
        this.h = n3;
    }

    public final void R(View view) {
        x0.b0(view, 0x100000);
        if (this.J(view)) {
            x0.d0(view, s.a.y, null, new v(this){
                public final SwipeDismissBehavior a;
                {
                    this.a = swipeDismissBehavior;
                }

                @Override
                public boolean a(View view, v.a a4) {
                    boolean bl = this.a.J(view);
                    int n3 = 0;
                    if (bl) {
                        int n4;
                        if (view.getLayoutDirection() == 1) {
                            n3 = 1;
                        }
                        n3 = (n4 = this.a.h) == 0 && n3 != 0 || n4 == 1 && n3 == 0 ? -view.getWidth() : view.getWidth();
                        x0.R(view, n3);
                        view.setAlpha(0.0f);
                        this.a.getClass();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public boolean o(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean bl = this.d;
        int n3 = motionEvent.getActionMasked();
        if (n3 != 0) {
            if (n3 == 1 || n3 == 3) {
                this.d = false;
            }
        } else {
            this.d = bl = coordinatorLayout.F(view, (int)motionEvent.getX(), (int)motionEvent.getY());
        }
        if (bl) {
            this.M(coordinatorLayout);
            if (!this.e && this.c.P(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean p(CoordinatorLayout coordinatorLayout, View view, int n3) {
        boolean bl = super.p(coordinatorLayout, view, n3);
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
            this.R(view);
        }
        return bl;
    }

    public class c
    implements Runnable {
        public final View c;
        public final boolean d;
        public final SwipeDismissBehavior e;

        public c(SwipeDismissBehavior swipeDismissBehavior, View view, boolean bl) {
            this.e = swipeDismissBehavior;
            this.c = view;
            this.d = bl;
        }

        @Override
        public void run() {
            v0.c c3 = this.e.c;
            if (c3 != null && c3.m(true)) {
                this.c.postOnAnimation((Runnable)this);
                return;
            }
            if (this.d) {
                this.e.getClass();
            }
        }
    }
}

