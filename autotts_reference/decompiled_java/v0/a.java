/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityRecord
 */
package v0;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityRecord;
import java.util.ArrayList;
import java.util.List;
import o.s;
import o0.x0;
import p0.t;
import p0.u;
import v0.b;

public abstract class a
extends o0.a {
    public static final Rect n = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final b.a o = new b.a(){

        public void b(p0.s s3, Rect rect) {
            s3.k(rect);
        }
    };
    public static final b.b p = new b.b(){

        public p0.s c(s s3, int n3) {
            return (p0.s)s3.i(n3);
        }

        public int d(s s3) {
            return s3.h();
        }
    };
    public final Rect d = new Rect();
    public final Rect e = new Rect();
    public final Rect f = new Rect();
    public final int[] g = new int[2];
    public final AccessibilityManager h;
    public final View i;
    public c j;
    public int k = Integer.MIN_VALUE;
    public int l = Integer.MIN_VALUE;
    public int m = Integer.MIN_VALUE;

    public a(View view) {
        if (view != null) {
            this.i = view;
            this.h = (AccessibilityManager)view.getContext().getSystemService("accessibility");
            view.setFocusable(true);
            if (x0.w(view) == 0) {
                x0.o0(view, 1);
            }
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    public static Rect D(View view, int n3, Rect rect) {
        int n4 = view.getWidth();
        int n5 = view.getHeight();
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 == 130) {
                        rect.set(0, -1, n4, -1);
                        return rect;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                rect.set(-1, 0, -1, n5);
                return rect;
            }
            rect.set(0, n5, n4, n5);
            return rect;
        }
        rect.set(n4, 0, n4, n5);
        return rect;
    }

    public static int H(int n3) {
        if (n3 != 19) {
            if (n3 != 21) {
                if (n3 != 22) {
                    return 130;
                }
                return 66;
            }
            return 17;
        }
        return 33;
    }

    public final int A() {
        return this.l;
    }

    public abstract int B(float var1, float var2);

    public abstract void C(List var1);

    public final void E(int n3) {
        this.F(n3, 0);
    }

    public final void F(int n3, int n4) {
        ViewParent viewParent;
        if (n3 != Integer.MIN_VALUE && this.h.isEnabled() && (viewParent = this.i.getParent()) != null) {
            AccessibilityEvent accessibilityEvent = this.q(n3, 2048);
            p0.b.b(accessibilityEvent, n4);
            viewParent.requestSendAccessibilityEvent(this.i, accessibilityEvent);
        }
    }

    public final boolean G(Rect rect) {
        if (rect != null && !rect.isEmpty()) {
            if (this.i.getWindowVisibility() != 0) {
                return false;
            }
            rect = this.i.getParent();
            while (rect instanceof View) {
                if (!((rect = (View)rect).getAlpha() <= 0.0f) && rect.getVisibility() == 0) {
                    rect = rect.getParent();
                    continue;
                }
                return false;
            }
            if (rect != null) {
                return true;
            }
        }
        return false;
    }

    public final boolean I(int n3, Rect object) {
        s s3 = this.y();
        int n4 = this.l;
        int n5 = Integer.MIN_VALUE;
        p0.s s4 = n4 == Integer.MIN_VALUE ? null : (p0.s)s3.d(n4);
        boolean bl = true;
        if (n3 != 1 && n3 != 2) {
            if (n3 != 17 && n3 != 33 && n3 != 66 && n3 != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            Rect rect = new Rect();
            n4 = this.l;
            if (n4 != Integer.MIN_VALUE) {
                this.z(n4, rect);
            } else if (object != null) {
                rect.set(object);
            } else {
                v0.a.D(this.i, n3, rect);
            }
            object = (p0.s)v0.b.c(s3, p, o, s4, rect, n3);
        } else {
            if (x0.y(this.i) != 1) {
                bl = false;
            }
            object = (p0.s)v0.b.d(s3, p, o, s4, n3, bl, false);
        }
        n3 = object == null ? n5 : s3.f(s3.e(object));
        return this.V(n3);
    }

    public p0.s J(int n3) {
        if (n3 == -1) {
            return this.u();
        }
        return this.t(n3);
    }

    public final void K(boolean bl, int n3, Rect rect) {
        int n4 = this.l;
        if (n4 != Integer.MIN_VALUE) {
            this.o(n4);
        }
        if (bl) {
            this.I(n3, rect);
        }
    }

    public abstract boolean L(int var1, int var2, Bundle var3);

    public void M(AccessibilityEvent accessibilityEvent) {
    }

    public void N(int n3, AccessibilityEvent accessibilityEvent) {
    }

    public void O(p0.s s3) {
    }

    public abstract void P(int var1, p0.s var2);

    public void Q(int n3, boolean bl) {
    }

    public boolean R(int n3, int n4, Bundle bundle) {
        if (n3 != -1) {
            return this.S(n3, n4, bundle);
        }
        return this.T(n4, bundle);
    }

    public final boolean S(int n3, int n4, Bundle bundle) {
        if (n4 != 1) {
            if (n4 != 2) {
                if (n4 != 64) {
                    if (n4 != 128) {
                        return this.L(n3, n4, bundle);
                    }
                    return this.n(n3);
                }
                return this.U(n3);
            }
            return this.o(n3);
        }
        return this.V(n3);
    }

    public final boolean T(int n3, Bundle bundle) {
        return x0.W(this.i, n3, bundle);
    }

    public final boolean U(int n3) {
        int n4;
        if (this.h.isEnabled() && this.h.isTouchExplorationEnabled() && (n4 = this.k) != n3) {
            if (n4 != Integer.MIN_VALUE) {
                this.n(n4);
            }
            this.k = n3;
            this.i.invalidate();
            this.W(n3, 32768);
            return true;
        }
        return false;
    }

    public final boolean V(int n3) {
        if (!this.i.isFocused() && !this.i.requestFocus()) {
            return false;
        }
        int n4 = this.l;
        if (n4 == n3) {
            return false;
        }
        if (n4 != Integer.MIN_VALUE) {
            this.o(n4);
        }
        if (n3 == Integer.MIN_VALUE) {
            return false;
        }
        this.l = n3;
        this.Q(n3, true);
        this.W(n3, 8);
        return true;
    }

    public final boolean W(int n3, int n4) {
        if (n3 != Integer.MIN_VALUE && this.h.isEnabled()) {
            ViewParent viewParent = this.i.getParent();
            if (viewParent == null) {
                return false;
            }
            AccessibilityEvent accessibilityEvent = this.q(n3, n4);
            return viewParent.requestSendAccessibilityEvent(this.i, accessibilityEvent);
        }
        return false;
    }

    public final void X(int n3) {
        int n4 = this.m;
        if (n4 == n3) {
            return;
        }
        this.m = n3;
        this.W(n3, 128);
        this.W(n4, 256);
    }

    @Override
    public t b(View view) {
        if (this.j == null) {
            this.j = new c(this);
        }
        return this.j;
    }

    @Override
    public void f(View view, AccessibilityEvent accessibilityEvent) {
        super.f(view, accessibilityEvent);
        this.M(accessibilityEvent);
    }

    @Override
    public void g(View view, p0.s s3) {
        super.g(view, s3);
        this.O(s3);
    }

    public final boolean n(int n3) {
        if (this.k == n3) {
            this.k = Integer.MIN_VALUE;
            this.i.invalidate();
            this.W(n3, 65536);
            return true;
        }
        return false;
    }

    public final boolean o(int n3) {
        if (this.l != n3) {
            return false;
        }
        this.l = Integer.MIN_VALUE;
        this.Q(n3, false);
        this.W(n3, 8);
        return true;
    }

    public final boolean p() {
        int n3 = this.l;
        return n3 != Integer.MIN_VALUE && this.L(n3, 16, null);
    }

    public final AccessibilityEvent q(int n3, int n4) {
        if (n3 != -1) {
            return this.r(n3, n4);
        }
        return this.s(n4);
    }

    public final AccessibilityEvent r(int n3, int n4) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n4);
        p0.s s3 = this.J(n3);
        accessibilityEvent.getText().add(s3.x());
        accessibilityEvent.setContentDescription(s3.r());
        accessibilityEvent.setScrollable(s3.P());
        accessibilityEvent.setPassword(s3.O());
        accessibilityEvent.setEnabled(s3.I());
        accessibilityEvent.setChecked(s3.F());
        this.N(n3, accessibilityEvent);
        if (accessibilityEvent.getText().isEmpty() && accessibilityEvent.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEvent.setClassName(s3.o());
        u.c((AccessibilityRecord)accessibilityEvent, this.i, n3);
        accessibilityEvent.setPackageName((CharSequence)this.i.getContext().getPackageName());
        return accessibilityEvent;
    }

    public final AccessibilityEvent s(int n3) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain((int)n3);
        this.i.onInitializeAccessibilityEvent(accessibilityEvent);
        return accessibilityEvent;
    }

    public final p0.s t(int n3) {
        p0.s s3 = p0.s.U();
        s3.m0(true);
        s3.o0(true);
        s3.h0("android.view.View");
        Rect rect = n;
        s3.c0(rect);
        s3.d0(rect);
        s3.w0(this.i);
        this.P(n3, s3);
        if (s3.x() == null && s3.r() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        s3.k(this.e);
        if (!this.e.equals((Object)rect)) {
            int n4 = s3.i();
            if ((n4 & 0x40) == 0) {
                if ((n4 & 0x80) == 0) {
                    s3.u0(this.i.getContext().getPackageName());
                    s3.F0(this.i, n3);
                    if (this.k == n3) {
                        s3.a0(true);
                        s3.a(128);
                    } else {
                        s3.a0(false);
                        s3.a(64);
                    }
                    boolean bl = this.l == n3;
                    if (bl) {
                        s3.a(2);
                    } else if (s3.J()) {
                        s3.a(1);
                    }
                    s3.p0(bl);
                    this.i.getLocationOnScreen(this.g);
                    s3.l(this.d);
                    if (this.d.equals((Object)rect)) {
                        s3.k(this.d);
                        if (s3.b != -1) {
                            p0.s s4 = p0.s.U();
                            n3 = s3.b;
                            while (n3 != -1) {
                                s4.x0(this.i, -1);
                                s4.c0(n);
                                this.P(n3, s4);
                                s4.k(this.e);
                                rect = this.d;
                                Rect rect2 = this.e;
                                rect.offset(rect2.left, rect2.top);
                                n3 = s4.b;
                            }
                            s4.Y();
                        }
                        this.d.offset(this.g[0] - this.i.getScrollX(), this.g[1] - this.i.getScrollY());
                    }
                    if (this.i.getLocalVisibleRect(this.f)) {
                        this.f.offset(this.g[0] - this.i.getScrollX(), this.g[1] - this.i.getScrollY());
                        if (this.d.intersect(this.f)) {
                            s3.d0(this.d);
                            if (this.G(this.d)) {
                                s3.J0(true);
                            }
                        }
                    }
                    return s3;
                }
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
    }

    public final p0.s u() {
        p0.s s3 = p0.s.V(this.i);
        x0.U(this.i, s3);
        ArrayList arrayList = new ArrayList();
        this.C(arrayList);
        if (s3.n() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            s3.d(this.i, (Integer)arrayList.get(i3));
        }
        return s3;
    }

    public final boolean v(MotionEvent motionEvent) {
        if (this.h.isEnabled() && this.h.isTouchExplorationEnabled()) {
            int n3 = motionEvent.getAction();
            if (n3 != 7 && n3 != 9) {
                if (n3 != 10) {
                    return false;
                }
                if (this.m != Integer.MIN_VALUE) {
                    this.X(Integer.MIN_VALUE);
                    return true;
                }
                return false;
            }
            n3 = this.B(motionEvent.getX(), motionEvent.getY());
            this.X(n3);
            if (n3 != Integer.MIN_VALUE) {
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean w(KeyEvent keyEvent) {
        int n3 = keyEvent.getAction();
        int n4 = 0;
        if (n3 == 1) return false;
        n3 = keyEvent.getKeyCode();
        if (n3 != 61) {
            if (n3 != 66) {
                switch (n3) {
                    default: {
                        return false;
                    }
                    case 19: 
                    case 20: 
                    case 21: 
                    case 22: {
                        if (!keyEvent.hasNoModifiers()) return false;
                        n3 = v0.a.H(n3);
                        int n5 = keyEvent.getRepeatCount();
                        boolean bl = false;
                        while (n4 < n5 + 1) {
                            if (!this.I(n3, null)) return bl;
                            ++n4;
                            bl = true;
                        }
                        return bl;
                    }
                    case 23: 
                }
            }
            if (!keyEvent.hasNoModifiers()) return false;
            if (keyEvent.getRepeatCount() != 0) return false;
            this.p();
            return true;
        }
        if (keyEvent.hasNoModifiers()) {
            return this.I(2, null);
        }
        if (!keyEvent.hasModifiers(1)) return false;
        return this.I(1, null);
    }

    public final int x() {
        return this.k;
    }

    public final s y() {
        ArrayList arrayList = new ArrayList();
        this.C(arrayList);
        s s3 = new s();
        for (int i3 = 0; i3 < arrayList.size(); ++i3) {
            p0.s s4 = this.t((Integer)arrayList.get(i3));
            s3.g((Integer)arrayList.get(i3), s4);
        }
        return s3;
    }

    public final void z(int n3, Rect rect) {
        this.J(n3).k(rect);
    }

    public class c
    extends t {
        public final a b;

        public c(a a4) {
            this.b = a4;
        }

        @Override
        public p0.s b(int n3) {
            return p0.s.W(this.b.J(n3));
        }

        @Override
        public p0.s d(int n3) {
            if ((n3 = n3 == 2 ? this.b.k : this.b.l) == Integer.MIN_VALUE) {
                return null;
            }
            return this.b(n3);
        }

        @Override
        public boolean f(int n3, int n4, Bundle bundle) {
            return this.b.R(n3, n4, bundle);
        }
    }
}

