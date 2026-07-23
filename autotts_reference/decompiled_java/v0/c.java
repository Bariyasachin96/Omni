/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.animation.Interpolator
 *  android.widget.OverScroller
 */
package v0;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;
import o0.x0;

public class c {
    public static final Interpolator x = new Interpolator(){

        public float getInterpolation(float f3) {
            return (f3 -= 1.0f) * f3 * f3 * f3 * f3 + 1.0f;
        }
    };
    public int a;
    public int b;
    public int c = -1;
    public float[] d;
    public float[] e;
    public float[] f;
    public float[] g;
    public int[] h;
    public int[] i;
    public int[] j;
    public int k;
    public VelocityTracker l;
    public float m;
    public float n;
    public int o;
    public final int p;
    public int q;
    public OverScroller r;
    public final c s;
    public View t;
    public boolean u;
    public final ViewGroup v;
    public final Runnable w = new Runnable(this){
        public final c c;
        {
            this.c = c3;
        }

        @Override
        public void run() {
            this.c.K(0);
        }
    };

    public c(Context context, ViewGroup viewGroup, c c3) {
        if (viewGroup != null) {
            if (c3 != null) {
                int n3;
                this.v = viewGroup;
                this.s = c3;
                viewGroup = ViewConfiguration.get((Context)context);
                this.p = n3 = (int)(context.getResources().getDisplayMetrics().density * 20.0f + 0.5f);
                this.o = n3;
                this.b = viewGroup.getScaledTouchSlop();
                this.m = viewGroup.getScaledMaximumFlingVelocity();
                this.n = viewGroup.getScaledMinimumFlingVelocity();
                this.r = new OverScroller(context, x);
                return;
            }
            throw new IllegalArgumentException("Callback may not be null");
        }
        throw new IllegalArgumentException("Parent view may not be null");
    }

    public static c n(ViewGroup object, float f3, c c3) {
        object = v0.c.o(object, c3);
        object.b = (int)((float)object.b * (1.0f / f3));
        return object;
    }

    public static c o(ViewGroup viewGroup, c c3) {
        return new c(viewGroup.getContext(), viewGroup, c3);
    }

    public int A() {
        return this.a;
    }

    public boolean B(int n3, int n4) {
        return this.E(this.t, n3, n4);
    }

    public boolean C(int n3) {
        return (1 << n3 & this.k) != 0;
    }

    public final boolean D(int n3) {
        if (!this.C(n3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignoring pointerId=");
            stringBuilder.append(n3);
            stringBuilder.append(" because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
            Log.e((String)"ViewDragHelper", (String)stringBuilder.toString());
            return false;
        }
        return true;
    }

    public boolean E(View view, int n3, int n4) {
        if (view == null) {
            return false;
        }
        return n3 >= view.getLeft() && n3 < view.getRight() && n4 >= view.getTop() && n4 < view.getBottom();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void F(MotionEvent motionEvent) {
        int n3;
        int n4 = motionEvent.getActionMasked();
        int n5 = motionEvent.getActionIndex();
        if (n4 == 0) {
            this.a();
        }
        if (this.l == null) {
            this.l = VelocityTracker.obtain();
        }
        this.l.addMovement(motionEvent);
        int n6 = 0;
        if (n4 != 0) {
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 3) {
                        if (n4 != 5) {
                            if (n4 != 6) return;
                            n6 = motionEvent.getPointerId(n5);
                            if (this.a == 1 && n6 == this.c) {
                                block15: {
                                    n5 = motionEvent.getPointerCount();
                                    for (n3 = 0; n3 < n5; ++n3) {
                                        View view;
                                        float f3;
                                        float f4;
                                        View view2;
                                        n4 = motionEvent.getPointerId(n3);
                                        if (n4 == this.c || (view2 = this.t((int)(f4 = motionEvent.getX(n3)), (int)(f3 = motionEvent.getY(n3)))) != (view = this.t) || !this.R(view, n4)) continue;
                                        n3 = this.c;
                                        break block15;
                                    }
                                    n3 = -1;
                                }
                                if (n3 == -1) {
                                    this.G();
                                }
                            }
                            this.j(n6);
                            return;
                        }
                        n3 = motionEvent.getPointerId(n5);
                        float f5 = motionEvent.getX(n5);
                        float f6 = motionEvent.getY(n5);
                        this.I(f5, f6, n3);
                        if (this.a == 0) {
                            this.R(this.t((int)f5, (int)f6), n3);
                            n5 = this.h[n3];
                            n6 = this.q;
                            if ((n5 & n6) == 0) return;
                            this.s.h(n5 & n6, n3);
                            return;
                        }
                        if (!this.B((int)f5, (int)f6)) return;
                        this.R(this.t, n3);
                        return;
                    }
                    if (this.a == 1) {
                        this.p(0.0f, 0.0f);
                    }
                    this.a();
                    return;
                }
                if (this.a == 1) {
                    if (!this.D(this.c)) return;
                    n3 = motionEvent.findPointerIndex(this.c);
                    float f7 = motionEvent.getX(n3);
                    float f8 = motionEvent.getY(n3);
                    float[] fArray = this.f;
                    n6 = this.c;
                    n3 = (int)(f7 - fArray[n6]);
                    n6 = (int)(f8 - this.g[n6]);
                    this.r(this.t.getLeft() + n3, this.t.getTop() + n6, n3, n6);
                    this.J(motionEvent);
                    return;
                }
                n5 = motionEvent.getPointerCount();
                for (n3 = n6; n3 < n5; ++n3) {
                    View view;
                    n6 = motionEvent.getPointerId(n3);
                    if (!this.D(n6)) continue;
                    float f9 = motionEvent.getX(n3);
                    float f10 = motionEvent.getY(n3);
                    float f11 = f9 - this.d[n6];
                    float f12 = f10 - this.e[n6];
                    this.H(f11, f12, n6);
                    if (this.a == 1 || this.f(view = this.t((int)f9, (int)f10), f11, f12) && this.R(view, n6)) break;
                }
                this.J(motionEvent);
                return;
            }
            if (this.a == 1) {
                this.G();
            }
            this.a();
            return;
        }
        float f13 = motionEvent.getX();
        float f14 = motionEvent.getY();
        n6 = motionEvent.getPointerId(0);
        motionEvent = this.t((int)f13, (int)f14);
        this.I(f13, f14, n6);
        this.R((View)motionEvent, n6);
        n3 = this.h[n6];
        n5 = this.q;
        if ((n3 & n5) == 0) return;
        this.s.h(n3 & n5, n6);
    }

    public final void G() {
        this.l.computeCurrentVelocity(1000, this.m);
        this.p(this.g(this.l.getXVelocity(this.c), this.n, this.m), this.g(this.l.getYVelocity(this.c), this.n, this.m));
    }

    public final void H(float f3, float f4, int n3) {
        int n4;
        int n5 = n4 = this.c(f3, f4, n3, 1);
        if (this.c(f4, f3, n3, 4)) {
            n5 = n4 | 4;
        }
        n4 = n5;
        if (this.c(f3, f4, n3, 2)) {
            n4 = n5 | 2;
        }
        n5 = n4;
        if (this.c(f4, f3, n3, 8)) {
            n5 = n4 | 8;
        }
        if (n5 != 0) {
            int[] nArray = this.i;
            nArray[n3] = nArray[n3] | n5;
            this.s.f(n5, n3);
        }
    }

    public final void I(float f3, float f4, int n3) {
        this.s(n3);
        float[] fArray = this.d;
        this.f[n3] = f3;
        fArray[n3] = f3;
        fArray = this.e;
        this.g[n3] = f4;
        fArray[n3] = f4;
        this.h[n3] = this.y((int)f3, (int)f4);
        this.k |= 1 << n3;
    }

    public final void J(MotionEvent motionEvent) {
        int n3 = motionEvent.getPointerCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            int n4 = motionEvent.getPointerId(i3);
            if (!this.D(n4)) continue;
            float f3 = motionEvent.getX(i3);
            float f4 = motionEvent.getY(i3);
            this.f[n4] = f3;
            this.g[n4] = f4;
        }
    }

    public void K(int n3) {
        this.v.removeCallbacks(this.w);
        if (this.a != n3) {
            this.a = n3;
            this.s.j(n3);
            if (this.a == 0) {
                this.t = null;
            }
        }
    }

    public void L(int n3) {
        this.o = n3;
    }

    public void M(int n3) {
        this.q = n3;
    }

    public void N(float f3) {
        this.n = f3;
    }

    public boolean O(int n3, int n4) {
        if (this.u) {
            return this.u(n3, n4, (int)this.l.getXVelocity(this.c), (int)this.l.getYVelocity(this.c));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    public boolean P(MotionEvent motionEvent) {
        block17: {
            int n3;
            int n4;
            int n5;
            block14: {
                block15: {
                    block16: {
                        n5 = motionEvent.getActionMasked();
                        n4 = motionEvent.getActionIndex();
                        if (n5 == 0) {
                            this.a();
                        }
                        if (this.l == null) {
                            this.l = VelocityTracker.obtain();
                        }
                        this.l.addMovement(motionEvent);
                        if (n5 == 0) break block14;
                        if (n5 == 1) break block15;
                        if (n5 == 2) break block16;
                        if (n5 == 3) break block15;
                        if (n5 != 5) {
                            if (n5 == 6) {
                                this.j(motionEvent.getPointerId(n4));
                            }
                        } else {
                            n5 = motionEvent.getPointerId(n4);
                            float f3 = motionEvent.getX(n4);
                            float f4 = motionEvent.getY(n4);
                            this.I(f3, f4, n5);
                            n4 = this.a;
                            if (n4 == 0) {
                                int n6 = this.h[n5];
                                n4 = this.q;
                                if ((n6 & n4) != 0) {
                                    this.s.h(n6 & n4, n5);
                                }
                            } else if (n4 == 2 && (motionEvent = this.t((int)f3, (int)f4)) == this.t) {
                                this.R((View)motionEvent, n5);
                            }
                        }
                        break block17;
                    }
                    if (this.d != null && this.e != null) {
                        int n7 = motionEvent.getPointerCount();
                        for (n5 = 0; n5 < n7; ++n5) {
                            int n8 = motionEvent.getPointerId(n5);
                            if (!this.D(n8)) continue;
                            float f5 = motionEvent.getX(n5);
                            float f6 = motionEvent.getY(n5);
                            float f7 = f5 - this.d[n8];
                            float f8 = f6 - this.e[n8];
                            View view = this.t((int)f5, (int)f6);
                            n4 = view != null && this.f(view, f7, f8) ? 1 : 0;
                            if (n4 != 0) {
                                int n9 = view.getLeft();
                                int n10 = (int)f7;
                                int n11 = this.s.a(view, n9 + n10, n10);
                                n10 = view.getTop();
                                int n12 = (int)f8;
                                int n13 = this.s.b(view, n10 + n12, n12);
                                n12 = this.s.d(view);
                                int n14 = this.s.e(view);
                                if ((n12 == 0 || n12 > 0 && n11 == n9) && (n14 == 0 || n14 > 0 && n13 == n10)) break;
                            }
                            this.H(f7, f8, n8);
                            if (this.a == 1 || n4 != 0 && this.R(view, n8)) break;
                        }
                        this.J(motionEvent);
                    }
                    break block17;
                }
                this.a();
                break block17;
            }
            float f9 = motionEvent.getX();
            float f10 = motionEvent.getY();
            n4 = motionEvent.getPointerId(0);
            this.I(f9, f10, n4);
            motionEvent = this.t((int)f9, (int)f10);
            if (motionEvent == this.t && this.a == 2) {
                this.R((View)motionEvent, n4);
            }
            if (((n5 = this.h[n4]) & (n3 = this.q)) != 0) {
                this.s.h(n5 & n3, n4);
            }
        }
        return this.a == 1;
    }

    public boolean Q(View view, int n3, int n4) {
        this.t = view;
        this.c = -1;
        boolean bl = this.u(n3, n4, 0, 0);
        if (!bl && this.a == 0 && this.t != null) {
            this.t = null;
        }
        return bl;
    }

    public boolean R(View view, int n3) {
        if (view == this.t && this.c == n3) {
            return true;
        }
        if (view != null && this.s.m(view, n3)) {
            this.c = n3;
            this.b(view, n3);
            return true;
        }
        return false;
    }

    public void a() {
        this.c = -1;
        this.i();
        VelocityTracker velocityTracker = this.l;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.l = null;
        }
    }

    public void b(View object, int n3) {
        if (object.getParent() == this.v) {
            this.t = object;
            this.c = n3;
            this.s.i((View)object, n3);
            this.K(1);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
        ((StringBuilder)object).append(this.v);
        ((StringBuilder)object).append(")");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public final boolean c(float f3, float f4, int n3, int n4) {
        int n5;
        f3 = Math.abs(f3);
        f4 = Math.abs(f4);
        if (!((this.h[n3] & n4) != n4 || (this.q & n4) == 0 || (this.j[n3] & n4) == n4 || (this.i[n3] & n4) == n4 || f3 <= (float)(n5 = this.b) && f4 <= (float)n5)) {
            if (f3 < f4 * 0.5f && this.s.g(n4)) {
                int[] nArray = this.j;
                nArray[n3] = nArray[n3] | n4;
                return false;
            }
            if ((this.i[n3] & n4) == 0 && f3 > (float)this.b) {
                return true;
            }
        }
        return false;
    }

    public boolean d(int n3) {
        int n4 = this.d.length;
        for (int i3 = 0; i3 < n4; ++i3) {
            if (!this.e(n3, i3)) continue;
            return true;
        }
        return false;
    }

    public boolean e(int n3, int n4) {
        if (!this.C(n4)) {
            return false;
        }
        boolean bl = (n3 & 1) == 1;
        n3 = (n3 & 2) == 2 ? 1 : 0;
        float f3 = this.f[n4] - this.d[n4];
        float f4 = this.g[n4] - this.e[n4];
        if (bl && n3 != 0) {
            n3 = this.b;
            return f3 * f3 + f4 * f4 > (float)(n3 * n3);
        }
        if (bl) {
            return Math.abs(f3) > (float)this.b;
        }
        return n3 != 0 && Math.abs(f4) > (float)this.b;
    }

    public final boolean f(View view, float f3, float f4) {
        if (view == null) {
            return false;
        }
        int n3 = this.s.d(view) > 0 ? 1 : 0;
        boolean bl = this.s.e(view) > 0;
        if (n3 != 0 && bl) {
            n3 = this.b;
            return f3 * f3 + f4 * f4 > (float)(n3 * n3);
        }
        if (n3 != 0) {
            return Math.abs(f3) > (float)this.b;
        }
        return bl && Math.abs(f4) > (float)this.b;
    }

    public final float g(float f3, float f4, float f5) {
        float f6 = Math.abs(f3);
        if (f6 < f4) {
            return 0.0f;
        }
        f4 = f3;
        if (f6 > f5) {
            if (f3 > 0.0f) {
                return f5;
            }
            f4 = -f5;
        }
        return f4;
    }

    public final int h(int n3, int n4, int n5) {
        int n6 = Math.abs(n3);
        if (n6 < n4) {
            return 0;
        }
        n4 = n3;
        if (n6 > n5) {
            if (n3 > 0) {
                return n5;
            }
            n4 = -n5;
        }
        return n4;
    }

    public final void i() {
        float[] fArray = this.d;
        if (fArray == null) {
            return;
        }
        Arrays.fill(fArray, 0.0f);
        Arrays.fill(this.e, 0.0f);
        Arrays.fill(this.f, 0.0f);
        Arrays.fill(this.g, 0.0f);
        Arrays.fill(this.h, 0);
        Arrays.fill(this.i, 0);
        Arrays.fill(this.j, 0);
        this.k = 0;
    }

    public final void j(int n3) {
        if (this.d != null && this.C(n3)) {
            this.d[n3] = 0.0f;
            this.e[n3] = 0.0f;
            this.f[n3] = 0.0f;
            this.g[n3] = 0.0f;
            this.h[n3] = 0;
            this.i[n3] = 0;
            this.j[n3] = 0;
            this.k = ~(1 << n3) & this.k;
        }
    }

    public final int k(int n3, int n4, int n5) {
        if (n3 == 0) {
            return 0;
        }
        int n6 = this.v.getWidth();
        int n7 = n6 / 2;
        float f3 = Math.min(1.0f, (float)Math.abs(n3) / (float)n6);
        float f4 = n7;
        f3 = this.q(f3);
        n3 = (n4 = Math.abs(n4)) > 0 ? Math.round(Math.abs((f4 + f3 * f4) / (float)n4) * 1000.0f) * 4 : (int)(((float)Math.abs(n3) / (float)n5 + 1.0f) * 256.0f);
        return Math.min(n3, 600);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final int l(View view, int n3, int n4, int n5, int n6) {
        float f3;
        float f4;
        n5 = this.h(n5, (int)this.n, (int)this.m);
        n6 = this.h(n6, (int)this.n, (int)this.m);
        int n7 = Math.abs(n3);
        int n8 = Math.abs(n4);
        int n9 = Math.abs(n5);
        int n10 = Math.abs(n6);
        int n11 = n9 + n10;
        int n12 = n7 + n8;
        if (n5 != 0) {
            f4 = n9;
            f3 = n11;
        } else {
            f4 = n7;
            f3 = n12;
        }
        float f5 = f4 / f3;
        if (n6 != 0) {
            f3 = n10;
            f4 = n11;
        } else {
            f3 = n8;
            f4 = n12;
        }
        f4 = f3 / f4;
        n3 = this.k(n3, n5, this.s.d(view));
        n4 = this.k(n4, n6, this.s.e(view));
        return (int)((float)n3 * f5 + (float)n4 * f4);
    }

    public boolean m(boolean bl) {
        if (this.a == 2) {
            boolean bl2 = this.r.computeScrollOffset();
            int n3 = this.r.getCurrX();
            int n4 = this.r.getCurrY();
            int n5 = n3 - this.t.getLeft();
            int n6 = n4 - this.t.getTop();
            if (n5 != 0) {
                x0.R(this.t, n5);
            }
            if (n6 != 0) {
                x0.S(this.t, n6);
            }
            if (n5 != 0 || n6 != 0) {
                this.s.k(this.t, n3, n4, n5, n6);
            }
            boolean bl3 = bl2;
            if (bl2) {
                bl3 = bl2;
                if (n3 == this.r.getFinalX()) {
                    bl3 = bl2;
                    if (n4 == this.r.getFinalY()) {
                        this.r.abortAnimation();
                        bl3 = false;
                    }
                }
            }
            if (!bl3) {
                if (bl) {
                    this.v.post(this.w);
                } else {
                    this.K(0);
                }
            }
        }
        return this.a == 2;
    }

    public final void p(float f3, float f4) {
        this.u = true;
        this.s.l(this.t, f3, f4);
        this.u = false;
        if (this.a == 1) {
            this.K(0);
        }
    }

    public final float q(float f3) {
        return (float)Math.sin((f3 - 0.5f) * 0.47123894f);
    }

    public final void r(int n3, int n4, int n5, int n6) {
        int n7 = this.t.getLeft();
        int n8 = this.t.getTop();
        int n9 = n3;
        if (n5 != 0) {
            n9 = this.s.a(this.t, n3, n5);
            x0.R(this.t, n9 - n7);
        }
        n3 = n4;
        if (n6 != 0) {
            n3 = this.s.b(this.t, n4, n6);
            x0.S(this.t, n3 - n8);
        }
        if (n5 == 0 && n6 == 0) {
            return;
        }
        this.s.k(this.t, n9, n3, n9 - n7, n3 - n8);
    }

    public final void s(int n3) {
        Object[] objectArray = this.d;
        if (objectArray != null && objectArray.length > n3) {
            return;
        }
        float[] fArray = new float[++n3];
        float[] fArray2 = new float[n3];
        float[] fArray3 = new float[n3];
        float[] fArray4 = new float[n3];
        int[] nArray = new int[n3];
        int[] nArray2 = new int[n3];
        int[] nArray3 = new int[n3];
        if (objectArray != null) {
            System.arraycopy(objectArray, 0, fArray, 0, objectArray.length);
            objectArray = this.e;
            System.arraycopy(objectArray, 0, fArray2, 0, objectArray.length);
            objectArray = this.f;
            System.arraycopy(objectArray, 0, fArray3, 0, objectArray.length);
            objectArray = this.g;
            System.arraycopy(objectArray, 0, fArray4, 0, objectArray.length);
            objectArray = this.h;
            System.arraycopy(objectArray, 0, nArray, 0, objectArray.length);
            objectArray = this.i;
            System.arraycopy(objectArray, 0, nArray2, 0, objectArray.length);
            objectArray = this.j;
            System.arraycopy(objectArray, 0, nArray3, 0, objectArray.length);
        }
        this.d = fArray;
        this.e = fArray2;
        this.f = fArray3;
        this.g = fArray4;
        this.h = nArray;
        this.i = nArray2;
        this.j = nArray3;
    }

    public View t(int n3, int n4) {
        for (int i3 = this.v.getChildCount() - 1; i3 >= 0; --i3) {
            View view = this.v.getChildAt(this.s.c(i3));
            if (n3 < view.getLeft() || n3 >= view.getRight() || n4 < view.getTop() || n4 >= view.getBottom()) continue;
            return view;
        }
        return null;
    }

    public final boolean u(int n3, int n4, int n5, int n6) {
        int n7 = this.t.getLeft();
        int n8 = this.t.getTop();
        if ((n3 -= n7) == 0 && (n4 -= n8) == 0) {
            this.r.abortAnimation();
            this.K(0);
            return false;
        }
        n5 = this.l(this.t, n3, n4, n5, n6);
        this.r.startScroll(n7, n8, n3, n4, n5);
        this.K(2);
        return true;
    }

    public View v() {
        return this.t;
    }

    public int w() {
        return this.p;
    }

    public int x() {
        return this.o;
    }

    public final int y(int n3, int n4) {
        int n5 = n3 < this.v.getLeft() + this.o ? 1 : 0;
        int n6 = n5;
        if (n4 < this.v.getTop() + this.o) {
            n6 = n5 | 4;
        }
        n5 = n6;
        if (n3 > this.v.getRight() - this.o) {
            n5 = n6 | 2;
        }
        if (n4 > this.v.getBottom() - this.o) {
            return n5 | 8;
        }
        return n5;
    }

    public int z() {
        return this.b;
    }

    public static abstract class c {
        public abstract int a(View var1, int var2, int var3);

        public abstract int b(View var1, int var2, int var3);

        public int c(int n3) {
            return n3;
        }

        public int d(View view) {
            return 0;
        }

        public int e(View view) {
            return 0;
        }

        public void f(int n3, int n4) {
        }

        public boolean g(int n3) {
            return false;
        }

        public void h(int n3, int n4) {
        }

        public void i(View view, int n3) {
        }

        public abstract void j(int var1);

        public abstract void k(View var1, int var2, int var3, int var4, int var5);

        public abstract void l(View var1, float var2, float var3);

        public abstract boolean m(View var1, int var2);
    }
}

