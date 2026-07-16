/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.g;
import androidx.recyclerview.widget.i;
import androidx.recyclerview.widget.l;
import java.util.List;

public class LinearLayoutManager
extends RecyclerView.p
implements RecyclerView.y.b {
    public int A = -1;
    public int B = Integer.MIN_VALUE;
    public boolean C;
    public SavedState D = null;
    public final a E = new a();
    public final b F = new b();
    public int G = 2;
    public int[] H = new int[2];
    public int s = 1;
    public c t;
    public i u;
    public boolean v;
    public boolean w = false;
    public boolean x = false;
    public boolean y = false;
    public boolean z = true;

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int n3, boolean bl) {
        this.C2(n3);
        this.D2(bl);
    }

    public LinearLayoutManager(Context object, AttributeSet attributeSet, int n3, int n4) {
        object = RecyclerView.p.m0(object, attributeSet, n3, n4);
        this.C2(object.a);
        this.D2(object.c);
        this.E2(object.d);
    }

    private View m2() {
        int n3 = this.x ? 0 : this.O() - 1;
        return this.N(n3);
    }

    private View n2() {
        int n3 = this.x ? this.O() - 1 : 0;
        return this.N(n3);
    }

    @Override
    public int A(RecyclerView.z z3) {
        return this.U1(z3);
    }

    @Override
    public int A1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        if (this.s == 1) {
            return 0;
        }
        return this.B2(n3, v3, z3);
    }

    public final void A2() {
        if (this.s != 1 && this.q2()) {
            this.x = this.w ^ true;
            return;
        }
        this.x = this.w;
    }

    @Override
    public void B1(int n3) {
        this.A = n3;
        this.B = Integer.MIN_VALUE;
        SavedState savedState = this.D;
        if (savedState != null) {
            savedState.p();
        }
        this.x1();
    }

    public int B2(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        if (this.O() != 0 && n3 != 0) {
            this.X1();
            this.t.a = true;
            int n4 = n3 > 0 ? 1 : -1;
            int n5 = Math.abs(n3);
            this.I2(n4, n5, true, z3);
            c c3 = this.t;
            int n6 = c3.g + this.Y1(v3, c3, z3, false);
            if (n6 < 0) {
                return 0;
            }
            if (n5 > n6) {
                n3 = n4 * n6;
            }
            this.u.r(-n3);
            this.t.k = n3;
            return n3;
        }
        return 0;
    }

    @Override
    public int C1(int n3, RecyclerView.v v3, RecyclerView.z z3) {
        if (this.s == 0) {
            return 0;
        }
        return this.B2(n3, v3, z3);
    }

    public void C2(int n3) {
        i i3;
        if (n3 != 0 && n3 != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid orientation:");
            stringBuilder.append(n3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.l(null);
        if (n3 == this.s && this.u != null) {
            return;
        }
        this.u = i3 = androidx.recyclerview.widget.i.b(this, n3);
        this.E.a = i3;
        this.s = n3;
        this.x1();
    }

    public void D2(boolean bl) {
        this.l(null);
        if (bl == this.w) {
            return;
        }
        this.w = bl;
        this.x1();
    }

    public void E2(boolean bl) {
        this.l(null);
        if (this.y == bl) {
            return;
        }
        this.y = bl;
        this.x1();
    }

    public final boolean F2(RecyclerView.v v3, RecyclerView.z z3, a a4) {
        int n3 = this.O();
        boolean bl = false;
        if (n3 == 0) {
            return false;
        }
        View view = this.a0();
        if (view != null && a4.d(view, z3)) {
            a4.c(view, this.l0(view));
            return true;
        }
        boolean bl2 = this.v;
        boolean bl3 = this.y;
        if (bl2 != bl3) {
            return false;
        }
        if ((v3 = this.j2(v3, z3, a4.d, bl3)) != null) {
            a4.b((View)v3, this.l0((View)v3));
            if (!z3.e() && this.P1()) {
                int n4 = this.u.g((View)v3);
                int n5 = this.u.d((View)v3);
                int n6 = this.u.m();
                int n7 = this.u.i();
                n3 = n5 <= n6 && n4 < n6 ? 1 : 0;
                boolean bl4 = bl;
                if (n4 >= n7) {
                    bl4 = bl;
                    if (n5 > n7) {
                        bl4 = true;
                    }
                }
                if (n3 != 0 || bl4) {
                    n3 = n6;
                    if (a4.d) {
                        n3 = n7;
                    }
                    a4.c = n3;
                }
            }
            return true;
        }
        return false;
    }

    public final boolean G2(RecyclerView.z object, a a4) {
        int n3;
        boolean bl = ((RecyclerView.z)object).e();
        boolean bl2 = false;
        if (!bl && (n3 = this.A) != -1) {
            if (n3 >= 0 && n3 < ((RecyclerView.z)object).b()) {
                a4.b = this.A;
                object = this.D;
                if (object != null && ((SavedState)object).o()) {
                    a4.d = bl = this.D.e;
                    a4.c = bl ? this.u.i() - this.D.d : this.u.m() + this.D.d;
                    return true;
                }
                if (this.B == Integer.MIN_VALUE) {
                    object = this.H(this.A);
                    if (object != null) {
                        if (this.u.e((View)object) > this.u.n()) {
                            a4.a();
                            return true;
                        }
                        if (this.u.g((View)object) - this.u.m() < 0) {
                            a4.c = this.u.m();
                            a4.d = false;
                            return true;
                        }
                        if (this.u.i() - this.u.d((View)object) < 0) {
                            a4.c = this.u.i();
                            a4.d = true;
                            return true;
                        }
                        n3 = a4.d ? this.u.d((View)object) + this.u.o() : this.u.g((View)object);
                        a4.c = n3;
                    } else {
                        if (this.O() > 0) {
                            n3 = this.l0(this.N(0));
                            bl = this.A < n3;
                            if (bl == this.x) {
                                bl2 = true;
                            }
                            a4.d = bl2;
                        }
                        a4.a();
                    }
                    return true;
                }
                a4.d = bl = this.x;
                a4.c = bl ? this.u.i() - this.B : this.u.m() + this.B;
                return true;
            }
            this.A = -1;
            this.B = Integer.MIN_VALUE;
        }
        return false;
    }

    @Override
    public View H(int n3) {
        View view;
        int n4 = this.O();
        if (n4 == 0) {
            return null;
        }
        int n5 = n3 - this.l0(this.N(0));
        if (n5 >= 0 && n5 < n4 && this.l0(view = this.N(n5)) == n3) {
            return view;
        }
        return super.H(n3);
    }

    public final void H2(RecyclerView.v v3, RecyclerView.z z3, a a4) {
        if (this.G2(z3, a4) || this.F2(v3, z3, a4)) {
            return;
        }
        a4.a();
        int n3 = this.y ? z3.b() - 1 : 0;
        a4.b = n3;
    }

    @Override
    public RecyclerView.LayoutParams I() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public final void I2(int n3, int n4, boolean bl, RecyclerView.z object) {
        this.t.m = this.z2();
        this.t.f = n3;
        Object object2 = this.H;
        boolean bl2 = false;
        object2[0] = 0;
        int n5 = 1;
        int n6 = 1;
        object2[1] = 0;
        this.Q1((RecyclerView.z)object, (int[])object2);
        int n7 = Math.max(0, this.H[0]);
        int n8 = Math.max(0, this.H[1]);
        if (n3 == 1) {
            bl2 = true;
        }
        object = this.t;
        n3 = bl2 ? n8 : n7;
        ((c)object).h = n3;
        if (bl2) {
            n8 = n7;
        }
        ((c)object).i = n8;
        if (bl2) {
            ((c)object).h = n3 + this.u.j();
            View view = this.m2();
            object2 = this.t;
            n3 = n6;
            if (this.x) {
                n3 = -1;
            }
            object2.e = n3;
            n3 = this.l0(view);
            object = this.t;
            object2.d = n3 + ((c)object).e;
            ((c)object).b = this.u.d(view);
            n3 = this.u.d(view) - this.u.i();
        } else {
            object = this.n2();
            object2 = this.t;
            object2.h += this.u.m();
            object2 = this.t;
            n3 = this.x ? n5 : -1;
            object2.e = n3;
            n3 = this.l0((View)object);
            c c3 = this.t;
            object2.d = n3 + c3.e;
            c3.b = this.u.g((View)object);
            n3 = -this.u.g((View)object) + this.u.m();
        }
        object = this.t;
        ((c)object).c = n4;
        if (bl) {
            ((c)object).c = n4 - n3;
        }
        ((c)object).g = n3;
    }

    public final void J2(int n3, int n4) {
        this.t.c = this.u.i() - n4;
        c c3 = this.t;
        int n5 = this.x ? -1 : 1;
        c3.e = n5;
        c3.d = n3;
        c3.f = 1;
        c3.b = n4;
        c3.g = Integer.MIN_VALUE;
    }

    @Override
    public boolean K1() {
        return this.c0() != 0x40000000 && this.t0() != 0x40000000 && this.u0();
    }

    public final void K2(a a4) {
        this.J2(a4.b, a4.c);
    }

    public final void L2(int n3, int n4) {
        this.t.c = n4 - this.u.m();
        c c3 = this.t;
        c3.d = n3;
        n3 = this.x ? 1 : -1;
        c3.e = n3;
        c3.f = -1;
        c3.b = n4;
        c3.g = Integer.MIN_VALUE;
    }

    @Override
    public void M0(RecyclerView recyclerView, RecyclerView.v v3) {
        super.M0(recyclerView, v3);
        if (this.C) {
            this.o1(v3);
            v3.c();
        }
    }

    @Override
    public void M1(RecyclerView object, RecyclerView.z z3, int n3) {
        object = new g(object.getContext());
        ((RecyclerView.y)object).p(n3);
        this.N1((RecyclerView.y)object);
    }

    public final void M2(a a4) {
        this.L2(a4.b, a4.c);
    }

    @Override
    public View N0(View object, int n3, RecyclerView.v v3, RecyclerView.z z3) {
        this.A2();
        if (this.O() == 0) {
            return null;
        }
        if ((n3 = this.V1(n3)) == Integer.MIN_VALUE) {
            return null;
        }
        this.X1();
        this.I2(n3, (int)((float)this.u.n() * 0.33333334f), false, z3);
        object = this.t;
        object.g = Integer.MIN_VALUE;
        object.a = false;
        this.Y1(v3, (c)object, z3, true);
        object = n3 == -1 ? this.i2() : this.h2();
        v3 = n3 == -1 ? this.n2() : this.m2();
        if (v3.hasFocusable()) {
            if (object == null) {
                return null;
            }
            return v3;
        }
        return object;
    }

    @Override
    public void O0(AccessibilityEvent accessibilityEvent) {
        super.O0(accessibilityEvent);
        if (this.O() > 0) {
            accessibilityEvent.setFromIndex(this.c2());
            accessibilityEvent.setToIndex(this.e2());
        }
    }

    @Override
    public boolean P1() {
        return this.D == null && this.v == this.y;
    }

    public void Q1(RecyclerView.z z3, int[] nArray) {
        int n3;
        int n4;
        int n5 = this.o2(z3);
        if (this.t.f == -1) {
            n4 = 0;
            n3 = n5;
        } else {
            n3 = 0;
            n4 = n5;
        }
        nArray[0] = n3;
        nArray[1] = n4;
    }

    public void R1(RecyclerView.z z3, c c3, RecyclerView.p.c c4) {
        int n3 = c3.d;
        if (n3 >= 0 && n3 < z3.b()) {
            c4.a(n3, Math.max(0, c3.g));
        }
    }

    public final int S1(RecyclerView.z z3) {
        if (this.O() == 0) {
            return 0;
        }
        this.X1();
        return androidx.recyclerview.widget.l.a(z3, this.u, this.b2(this.z ^ true, true), this.a2(this.z ^ true, true), this, this.z);
    }

    public final int T1(RecyclerView.z z3) {
        if (this.O() == 0) {
            return 0;
        }
        this.X1();
        return androidx.recyclerview.widget.l.b(z3, this.u, this.b2(this.z ^ true, true), this.a2(this.z ^ true, true), this, this.z, this.x);
    }

    public final int U1(RecyclerView.z z3) {
        if (this.O() == 0) {
            return 0;
        }
        this.X1();
        return androidx.recyclerview.widget.l.c(z3, this.u, this.b2(this.z ^ true, true), this.a2(this.z ^ true, true), this, this.z);
    }

    public int V1(int n3) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 17) {
                    if (n3 != 33) {
                        if (n3 != 66) {
                            if (n3 != 130) {
                                return Integer.MIN_VALUE;
                            }
                            if (this.s == 1) {
                                return 1;
                            }
                            return Integer.MIN_VALUE;
                        }
                        if (this.s == 0) {
                            return 1;
                        }
                        return Integer.MIN_VALUE;
                    }
                    if (this.s == 1) {
                        return -1;
                    }
                    return Integer.MIN_VALUE;
                }
                if (this.s == 0) {
                    return -1;
                }
                return Integer.MIN_VALUE;
            }
            if (this.s == 1) {
                return 1;
            }
            if (this.q2()) {
                return -1;
            }
            return 1;
        }
        if (this.s == 1) {
            return -1;
        }
        if (this.q2()) {
            return 1;
        }
        return -1;
    }

    public c W1() {
        return new c();
    }

    public void X1() {
        if (this.t == null) {
            this.t = this.W1();
        }
    }

    public int Y1(RecyclerView.v v3, c c3, RecyclerView.z z3, boolean bl) {
        int n3 = c3.c;
        int n4 = c3.g;
        if (n4 != Integer.MIN_VALUE) {
            if (n3 < 0) {
                c3.g = n4 + n3;
            }
            this.v2(v3, c3);
        }
        int n5 = c3.c + c3.h;
        b b3 = this.F;
        while ((c3.m || n5 > 0) && c3.c(z3)) {
            int n6;
            block9: {
                block8: {
                    b3.a();
                    this.s2(v3, z3, c3, b3);
                    if (b3.b) break;
                    c3.b += b3.a * c3.f;
                    if (!b3.c || c3.l != null) break block8;
                    n4 = n5;
                    if (z3.e()) break block9;
                }
                n6 = c3.c;
                n4 = b3.a;
                c3.c = n6 - n4;
                n4 = n5 - n4;
            }
            if ((n5 = c3.g) != Integer.MIN_VALUE) {
                c3.g = n5 += b3.a;
                n6 = c3.c;
                if (n6 < 0) {
                    c3.g = n5 + n6;
                }
                this.v2(v3, c3);
            }
            n5 = n4;
            if (!bl) continue;
            n5 = n4;
            if (!b3.d) continue;
        }
        return n3 - c3.c;
    }

    public final View Z1() {
        return this.f2(0, this.O());
    }

    public View a2(boolean bl, boolean bl2) {
        if (this.x) {
            return this.g2(0, this.O(), bl, bl2);
        }
        return this.g2(this.O() - 1, -1, bl, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void b1(RecyclerView.v v3, RecyclerView.z z3) {
        int n3;
        Object object = this.D;
        int n4 = -1;
        if ((object != null || this.A != -1) && z3.b() == 0) {
            this.o1(v3);
            return;
        }
        object = this.D;
        if (object != null && ((SavedState)object).o()) {
            this.A = this.D.c;
        }
        this.X1();
        this.t.a = false;
        this.A2();
        View view = this.a0();
        object = this.E;
        if (((a)object).e && this.A == -1 && this.D == null) {
            if (view != null && (this.u.g(view) >= this.u.i() || this.u.d(view) <= this.u.m())) {
                this.E.c(view, this.l0(view));
            }
        } else {
            ((a)object).e();
            object = this.E;
            ((a)object).d = this.x ^ this.y;
            this.H2(v3, z3, (a)object);
            this.E.e = true;
        }
        object = this.t;
        int n5 = ((c)object).k >= 0 ? 1 : -1;
        ((c)object).f = n5;
        object = this.H;
        object[0] = false;
        object[1] = false;
        this.Q1(z3, (int[])object);
        int n6 = Math.max(0, this.H[0]) + this.u.m();
        int n7 = Math.max(0, this.H[1]) + this.u.j();
        int n8 = n6;
        n5 = n7;
        if (z3.e()) {
            n3 = this.A;
            n8 = n6;
            n5 = n7;
            if (n3 != -1) {
                n8 = n6;
                n5 = n7;
                if (this.B != Integer.MIN_VALUE) {
                    object = this.H(n3);
                    n8 = n6;
                    n5 = n7;
                    if (object != null) {
                        if (this.x) {
                            n5 = this.u.i() - this.u.d((View)object);
                            n8 = this.B;
                        } else {
                            n8 = this.u.g((View)object) - this.u.m();
                            n5 = this.B;
                        }
                        if ((n5 -= n8) > 0) {
                            n8 = n6 + n5;
                            n5 = n7;
                        } else {
                            n5 = n7 - n5;
                            n8 = n6;
                        }
                    }
                }
            }
        }
        object = this.E;
        if (((a)object).d ? this.x : !this.x) {
            n4 = 1;
        }
        this.u2(v3, z3, (a)object, n4);
        this.B(v3);
        this.t.m = this.z2();
        this.t.j = z3.e();
        this.t.i = 0;
        object = this.E;
        if (((a)object).d) {
            this.M2((a)object);
            object = this.t;
            ((c)object).h = n8;
            this.Y1(v3, (c)object, z3, false);
            object = this.t;
            n4 = ((c)object).b;
            n7 = ((c)object).d;
            n6 = ((c)object).c;
            n8 = n5;
            if (n6 > 0) {
                n8 = n5 + n6;
            }
            this.K2(this.E);
            object = this.t;
            ((c)object).h = n8;
            ((c)object).d += ((c)object).e;
            this.Y1(v3, (c)object, z3, false);
            object = this.t;
            n6 = ((c)object).b;
            n3 = ((c)object).c;
            n8 = n4;
            n5 = n6;
            if (n3 > 0) {
                this.L2(n7, n4);
                object = this.t;
                ((c)object).h = n3;
                this.Y1(v3, (c)object, z3, false);
                n8 = this.t.b;
                n5 = n6;
            }
        } else {
            this.K2((a)object);
            object = this.t;
            ((c)object).h = n5;
            this.Y1(v3, (c)object, z3, false);
            object = this.t;
            n4 = ((c)object).b;
            n7 = ((c)object).d;
            n6 = ((c)object).c;
            n5 = n8;
            if (n6 > 0) {
                n5 = n8 + n6;
            }
            this.M2(this.E);
            object = this.t;
            ((c)object).h = n5;
            ((c)object).d += ((c)object).e;
            this.Y1(v3, (c)object, z3, false);
            object = this.t;
            n6 = ((c)object).b;
            n3 = ((c)object).c;
            n8 = n6;
            n5 = n4;
            if (n3 > 0) {
                this.J2(n7, n4);
                object = this.t;
                ((c)object).h = n3;
                this.Y1(v3, (c)object, z3, false);
                n5 = this.t.b;
                n8 = n6;
            }
        }
        n4 = n8;
        n6 = n5;
        if (this.O() > 0) {
            if (this.x ^ this.y) {
                n6 = this.k2(n5, v3, z3, true);
                n4 = n8 + n6;
                n8 = n5 + n6;
                n5 = this.l2(n4, v3, z3, false);
            } else {
                n6 = this.l2(n8, v3, z3, true);
                n4 = n8 + n6;
                n8 = n5 + n6;
                n5 = this.k2(n8, v3, z3, false);
            }
            n4 += n5;
            n6 = n8 + n5;
        }
        this.t2(v3, z3, n4, n6);
        if (!z3.e()) {
            this.u.s();
        } else {
            this.E.e();
        }
        this.v = this.y;
    }

    public View b2(boolean bl, boolean bl2) {
        if (this.x) {
            return this.g2(this.O() - 1, -1, bl, bl2);
        }
        return this.g2(0, this.O(), bl, bl2);
    }

    @Override
    public void c1(RecyclerView.z z3) {
        super.c1(z3);
        this.D = null;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.E.e();
    }

    public int c2() {
        View view = this.g2(0, this.O(), false, true);
        if (view == null) {
            return -1;
        }
        return this.l0(view);
    }

    @Override
    public PointF d(int n3) {
        if (this.O() == 0) {
            return null;
        }
        boolean bl = false;
        int n4 = this.l0(this.N(0));
        int n5 = 1;
        if (n3 < n4) {
            bl = true;
        }
        n3 = n5;
        if (bl != this.x) {
            n3 = -1;
        }
        if (this.s == 0) {
            return new PointF((float)n3, 0.0f);
        }
        return new PointF(0.0f, (float)n3);
    }

    public final View d2() {
        return this.f2(this.O() - 1, -1);
    }

    public int e2() {
        View view = this.g2(this.O() - 1, -1, false, true);
        if (view == null) {
            return -1;
        }
        return this.l0(view);
    }

    public View f2(int n3, int n4) {
        this.X1();
        if (n4 > n3 || n4 < n3) {
            int n5;
            int n6;
            if (this.u.g(this.N(n3)) < this.u.m()) {
                n6 = 16644;
                n5 = 16388;
            } else {
                n6 = 4161;
                n5 = 4097;
            }
            if (this.s == 0) {
                return this.e.a(n3, n4, n6, n5);
            }
            return this.f.a(n3, n4, n6, n5);
        }
        return this.N(n3);
    }

    @Override
    public void g1(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            parcelable = (SavedState)parcelable;
            this.D = parcelable;
            if (this.A != -1) {
                parcelable.p();
            }
            this.x1();
        }
    }

    public View g2(int n3, int n4, boolean bl, boolean bl2) {
        this.X1();
        int n5 = 320;
        int n6 = bl ? 24579 : 320;
        if (!bl2) {
            n5 = 0;
        }
        if (this.s == 0) {
            return this.e.a(n3, n4, n6, n5);
        }
        return this.f.a(n3, n4, n6, n5);
    }

    @Override
    public Parcelable h1() {
        if (this.D != null) {
            return new SavedState(this.D);
        }
        SavedState savedState = new SavedState();
        if (this.O() > 0) {
            boolean bl;
            this.X1();
            savedState.e = bl = this.v ^ this.x;
            if (bl) {
                View view = this.m2();
                savedState.d = this.u.i() - this.u.d(view);
                savedState.c = this.l0(view);
                return savedState;
            }
            View view = this.n2();
            savedState.c = this.l0(view);
            savedState.d = this.u.g(view) - this.u.m();
            return savedState;
        }
        savedState.p();
        return savedState;
    }

    public final View h2() {
        if (this.x) {
            return this.Z1();
        }
        return this.d2();
    }

    public final View i2() {
        if (this.x) {
            return this.d2();
        }
        return this.Z1();
    }

    public View j2(RecyclerView.v v3, RecyclerView.z z3, boolean bl, boolean bl2) {
        int n3;
        int n4;
        this.X1();
        int n5 = this.O();
        if (bl2) {
            n4 = this.O() - 1;
            n5 = -1;
            n3 = -1;
        } else {
            n4 = 0;
            n3 = 1;
        }
        int n6 = z3.b();
        int n7 = this.u.m();
        int n8 = this.u.i();
        RecyclerView.v v4 = null;
        RecyclerView.v v5 = null;
        z3 = null;
        while (n4 != n5) {
            Object object;
            RecyclerView.v v6;
            RecyclerView.v v7;
            block11: {
                block15: {
                    block16: {
                        block14: {
                            int n9;
                            block13: {
                                int n10;
                                int n11;
                                block12: {
                                    v3 = this.N(n4);
                                    n9 = this.l0((View)v3);
                                    n11 = this.u.g((View)v3);
                                    n10 = this.u.d((View)v3);
                                    v7 = v4;
                                    v6 = v5;
                                    object = z3;
                                    if (n9 < 0) break block11;
                                    v7 = v4;
                                    v6 = v5;
                                    object = z3;
                                    if (n9 >= n6) break block11;
                                    if (!((RecyclerView.LayoutParams)v3.getLayoutParams()).c()) break block12;
                                    v7 = v4;
                                    v6 = v5;
                                    object = z3;
                                    if (z3 == null) {
                                        v7 = v4;
                                        v6 = v5;
                                        object = v3;
                                    }
                                    break block11;
                                }
                                n9 = n10 <= n7 && n11 < n7 ? 1 : 0;
                                n10 = n11 >= n8 && n10 > n8 ? 1 : 0;
                                if (n9 == 0 && n10 == 0) {
                                    return v3;
                                }
                                if (!bl) break block13;
                                if (n10 != 0) break block14;
                                v7 = v4;
                                v6 = v5;
                                object = z3;
                                if (v4 != null) break block11;
                                break block15;
                            }
                            if (n9 == 0) break block16;
                        }
                        v7 = v4;
                        v6 = v3;
                        object = z3;
                        break block11;
                    }
                    v7 = v4;
                    v6 = v5;
                    object = z3;
                    if (v4 != null) break block11;
                }
                object = z3;
                v6 = v5;
                v7 = v3;
            }
            n4 += n3;
            v4 = v7;
            v5 = v6;
            z3 = object;
        }
        if (v4 != null) {
            return v4;
        }
        if (v5 != null) {
            return v5;
        }
        return z3;
    }

    public final int k2(int n3, RecyclerView.v v3, RecyclerView.z z3, boolean bl) {
        int n4 = this.u.i() - n3;
        if (n4 > 0) {
            n4 = -this.B2(-n4, v3, z3);
            if (bl && (n3 = this.u.i() - (n3 + n4)) > 0) {
                this.u.r(n3);
                return n3 + n4;
            }
            return n4;
        }
        return 0;
    }

    @Override
    public void l(String string) {
        if (this.D == null) {
            super.l(string);
        }
    }

    public final int l2(int n3, RecyclerView.v v3, RecyclerView.z z3, boolean bl) {
        int n4 = n3 - this.u.m();
        if (n4 > 0) {
            int n5;
            n4 = n5 = -this.B2(n4, v3, z3);
            if (bl) {
                n3 = n3 + n5 - this.u.m();
                n4 = n5;
                if (n3 > 0) {
                    this.u.r(-n3);
                    n4 = n5 - n3;
                }
            }
            return n4;
        }
        return 0;
    }

    public int o2(RecyclerView.z z3) {
        if (z3.d()) {
            return this.u.n();
        }
        return 0;
    }

    @Override
    public boolean p() {
        return this.s == 0;
    }

    public int p2() {
        return this.s;
    }

    @Override
    public boolean q() {
        return this.s == 1;
    }

    public boolean q2() {
        return this.d0() == 1;
    }

    public boolean r2() {
        return this.z;
    }

    public void s2(RecyclerView.v v3, RecyclerView.z object, c c3, b b3) {
        int n3;
        int n4;
        int n5;
        int n6;
        if ((v3 = c3.d(v3)) == null) {
            b3.b = true;
            return;
        }
        object = (RecyclerView.LayoutParams)v3.getLayoutParams();
        if (c3.l == null) {
            boolean bl = this.x;
            boolean bl2 = c3.f == -1;
            if (bl == bl2) {
                this.i((View)v3);
            } else {
                this.j((View)v3, 0);
            }
        } else {
            boolean bl = this.x;
            boolean bl3 = c3.f == -1;
            if (bl == bl3) {
                this.g((View)v3);
            } else {
                this.h((View)v3, 0);
            }
        }
        this.E0((View)v3, 0, 0);
        b3.a = this.u.e((View)v3);
        if (this.s == 1) {
            if (this.q2()) {
                n6 = this.s0() - this.j0();
                n5 = n6 - this.u.f((View)v3);
            } else {
                n5 = this.i0();
                n6 = this.u.f((View)v3) + n5;
            }
            if (c3.f == -1) {
                n4 = c3.b;
                n3 = n4 - b3.a;
            } else {
                n3 = c3.b;
                n4 = b3.a + n3;
            }
        } else {
            n3 = this.k0();
            n4 = this.u.f((View)v3) + n3;
            if (c3.f == -1) {
                n6 = c3.b;
                n5 = n6 - b3.a;
            } else {
                n5 = c3.b;
                n6 = b3.a + n5;
            }
        }
        this.D0((View)v3, n5, n3, n6, n4);
        if (((RecyclerView.LayoutParams)((Object)object)).c() || ((RecyclerView.LayoutParams)((Object)object)).b()) {
            b3.c = true;
        }
        b3.d = v3.hasFocusable();
    }

    @Override
    public void t(int n3, int n4, RecyclerView.z z3, RecyclerView.p.c c3) {
        if (this.s != 0) {
            n3 = n4;
        }
        if (this.O() != 0 && n3 != 0) {
            this.X1();
            n4 = n3 > 0 ? 1 : -1;
            this.I2(n4, Math.abs(n3), true, z3);
            this.R1(z3, this.t, c3);
        }
    }

    public final void t2(RecyclerView.v v3, RecyclerView.z z3, int n3, int n4) {
        if (z3.g() && this.O() != 0 && !z3.e() && this.P1()) {
            int n5;
            Object object = v3.k();
            int n6 = object.size();
            int n7 = this.l0(this.N(0));
            int n8 = n5 = 0;
            for (int i3 = 0; i3 < n6; ++i3) {
                RecyclerView.d0 d02 = (RecyclerView.d0)object.get(i3);
                if (d02.v()) continue;
                boolean bl = d02.m() < n7;
                if (bl != this.x) {
                    n5 += this.u.e(d02.a);
                    continue;
                }
                n8 += this.u.e(d02.a);
            }
            this.t.l = object;
            if (n5 > 0) {
                this.L2(this.l0(this.n2()), n3);
                object = this.t;
                ((c)object).h = n5;
                ((c)object).c = 0;
                ((c)object).a();
                this.Y1(v3, this.t, z3, false);
            }
            if (n8 > 0) {
                this.J2(this.l0(this.m2()), n4);
                object = this.t;
                ((c)object).h = n8;
                ((c)object).c = 0;
                ((c)object).a();
                this.Y1(v3, this.t, z3, false);
            }
            this.t.l = null;
        }
    }

    @Override
    public void u(int n3, RecyclerView.p.c c3) {
        int n4;
        int n5;
        boolean bl;
        SavedState savedState = this.D;
        int n6 = -1;
        if (savedState != null && savedState.o()) {
            savedState = this.D;
            bl = savedState.e;
            n5 = savedState.c;
        } else {
            this.A2();
            boolean bl2 = this.x;
            n5 = n4 = this.A;
            bl = bl2;
            if (n4 == -1) {
                if (bl2) {
                    n5 = n3 - 1;
                    bl = bl2;
                } else {
                    n5 = 0;
                    bl = bl2;
                }
            }
        }
        if (!bl) {
            n6 = 1;
        }
        for (n4 = 0; n4 < this.G && n5 >= 0 && n5 < n3; n5 += n6, ++n4) {
            c3.a(n5, 0);
        }
    }

    public void u2(RecyclerView.v v3, RecyclerView.z z3, a a4, int n3) {
    }

    @Override
    public int v(RecyclerView.z z3) {
        return this.S1(z3);
    }

    public final void v2(RecyclerView.v v3, c c3) {
        if (c3.a && !c3.m) {
            int n3 = c3.g;
            int n4 = c3.i;
            if (c3.f == -1) {
                this.x2(v3, n3, n4);
                return;
            }
            this.y2(v3, n3, n4);
        }
    }

    @Override
    public int w(RecyclerView.z z3) {
        return this.T1(z3);
    }

    @Override
    public boolean w0() {
        return true;
    }

    public final void w2(RecyclerView.v v3, int n3, int n4) {
        block4: {
            if (n3 == n4) break block4;
            if (n4 > n3) {
                --n4;
                while (n4 >= n3) {
                    this.r1(n4, v3);
                    --n4;
                }
            } else {
                for (int i3 = n3; i3 > n4; --i3) {
                    this.r1(i3, v3);
                }
            }
        }
    }

    @Override
    public int x(RecyclerView.z z3) {
        return this.U1(z3);
    }

    public final void x2(RecyclerView.v v3, int n3, int n4) {
        block6: {
            int n5 = this.O();
            if (n3 < 0) break block6;
            int n6 = this.u.h() - n3 + n4;
            if (this.x) {
                for (n3 = 0; n3 < n5; ++n3) {
                    View view = this.N(n3);
                    if (this.u.g(view) >= n6 && this.u.q(view) >= n6) {
                        continue;
                    }
                    this.w2(v3, 0, n3);
                    return;
                }
            } else {
                for (n3 = n4 = n5 - 1; n3 >= 0; --n3) {
                    View view = this.N(n3);
                    if (this.u.g(view) >= n6 && this.u.q(view) >= n6) {
                        continue;
                    }
                    this.w2(v3, n4, n3);
                    break;
                }
            }
        }
    }

    @Override
    public int y(RecyclerView.z z3) {
        return this.S1(z3);
    }

    public final void y2(RecyclerView.v v3, int n3, int n4) {
        block6: {
            if (n3 < 0) break block6;
            int n5 = n3 - n4;
            n4 = this.O();
            if (this.x) {
                for (n3 = --n4; n3 >= 0; --n3) {
                    View view = this.N(n3);
                    if (this.u.d(view) <= n5 && this.u.p(view) <= n5) {
                        continue;
                    }
                    this.w2(v3, n4, n3);
                    return;
                }
            } else {
                for (n3 = 0; n3 < n4; ++n3) {
                    View view = this.N(n3);
                    if (this.u.d(view) <= n5 && this.u.p(view) <= n5) {
                        continue;
                    }
                    this.w2(v3, 0, n3);
                    break;
                }
            }
        }
    }

    @Override
    public int z(RecyclerView.z z3) {
        return this.T1(z3);
    }

    public boolean z2() {
        return this.u.k() == 0 && this.u.h() == 0;
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] b(int n3) {
                return new SavedState[n3];
            }
        };
        public int c;
        public int d;
        public boolean e;

        public SavedState() {
        }

        public SavedState(Parcel parcel) {
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            int n3 = parcel.readInt();
            boolean bl = true;
            if (n3 != 1) {
                bl = false;
            }
            this.e = bl;
        }

        public SavedState(SavedState savedState) {
            this.c = savedState.c;
            this.d = savedState.d;
            this.e = savedState.e;
        }

        public int describeContents() {
            return 0;
        }

        public boolean o() {
            return this.c >= 0;
        }

        public void p() {
            this.c = -1;
        }

        public void writeToParcel(Parcel parcel, int n3) {
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e ? 1 : 0);
        }
    }

    public static class a {
        public i a;
        public int b;
        public int c;
        public boolean d;
        public boolean e;

        public a() {
            this.e();
        }

        public void a() {
            int n3 = this.d ? this.a.i() : this.a.m();
            this.c = n3;
        }

        public void b(View view, int n3) {
            this.c = this.d ? this.a.d(view) + this.a.o() : this.a.g(view);
            this.b = n3;
        }

        public void c(View view, int n3) {
            int n4 = this.a.o();
            if (n4 >= 0) {
                this.b(view, n3);
                return;
            }
            this.b = n3;
            if (this.d) {
                n3 = this.a.i() - n4 - this.a.d(view);
                this.c = this.a.i() - n3;
                if (n3 > 0) {
                    n4 = this.a.e(view);
                    int n5 = this.c;
                    int n6 = this.a.m();
                    if ((n4 = n5 - n4 - (n6 + Math.min(this.a.g(view) - n6, 0))) < 0) {
                        this.c += Math.min(n3, -n4);
                        return;
                    }
                }
            } else {
                int n7 = this.a.g(view);
                n3 = n7 - this.a.m();
                this.c = n7;
                if (n3 > 0) {
                    int n8 = this.a.e(view);
                    int n9 = this.a.i();
                    int n10 = this.a.d(view);
                    n4 = this.a.i() - Math.min(0, n9 - n4 - n10) - (n7 + n8);
                    if (n4 < 0) {
                        this.c -= Math.min(n3, -n4);
                    }
                }
            }
        }

        public boolean d(View object, RecyclerView.z z3) {
            return !((RecyclerView.LayoutParams)((Object)(object = (RecyclerView.LayoutParams)object.getLayoutParams()))).c() && ((RecyclerView.LayoutParams)((Object)object)).a() >= 0 && ((RecyclerView.LayoutParams)((Object)object)).a() < z3.b();
        }

        public void e() {
            this.b = -1;
            this.c = Integer.MIN_VALUE;
            this.d = false;
            this.e = false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AnchorInfo{mPosition=");
            stringBuilder.append(this.b);
            stringBuilder.append(", mCoordinate=");
            stringBuilder.append(this.c);
            stringBuilder.append(", mLayoutFromEnd=");
            stringBuilder.append(this.d);
            stringBuilder.append(", mValid=");
            stringBuilder.append(this.e);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static class b {
        public int a;
        public boolean b;
        public boolean c;
        public boolean d;

        public void a() {
            this.a = 0;
            this.b = false;
            this.c = false;
            this.d = false;
        }
    }

    public static class c {
        public boolean a = true;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h = 0;
        public int i = 0;
        public boolean j = false;
        public int k;
        public List l = null;
        public boolean m;

        public void a() {
            this.b(null);
        }

        public void b(View view) {
            if ((view = this.f(view)) == null) {
                this.d = -1;
                return;
            }
            this.d = ((RecyclerView.LayoutParams)view.getLayoutParams()).a();
        }

        public boolean c(RecyclerView.z z3) {
            int n3 = this.d;
            return n3 >= 0 && n3 < z3.b();
        }

        public View d(RecyclerView.v v3) {
            if (this.l != null) {
                return this.e();
            }
            v3 = v3.o(this.d);
            this.d += this.e;
            return v3;
        }

        public final View e() {
            int n3 = this.l.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                View view = ((RecyclerView.d0)this.l.get((int)i3)).a;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                if (layoutParams.c() || this.d != layoutParams.a()) continue;
                this.b(view);
                return view;
            }
            return null;
        }

        public View f(View view) {
            int n3 = this.l.size();
            View view2 = null;
            int n4 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < n3; ++i3) {
                View view3 = ((RecyclerView.d0)this.l.get((int)i3)).a;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view3.getLayoutParams();
                View view4 = view2;
                int n5 = n4;
                if (view3 != view) {
                    if (layoutParams.c()) {
                        view4 = view2;
                        n5 = n4;
                    } else {
                        int n6 = (layoutParams.a() - this.d) * this.e;
                        if (n6 < 0) {
                            view4 = view2;
                            n5 = n4;
                        } else {
                            view4 = view2;
                            n5 = n4;
                            if (n6 < n4) {
                                if (n6 == 0) {
                                    return view3;
                                }
                                view4 = view3;
                                n5 = n6;
                            }
                        }
                    }
                }
                view2 = view4;
                n4 = n5;
            }
            return view2;
        }
    }
}

