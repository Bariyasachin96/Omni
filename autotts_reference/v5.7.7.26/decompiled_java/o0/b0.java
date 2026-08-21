/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package o0;

import android.view.View;
import android.view.ViewParent;
import o0.f1;
import o0.x0;

public class b0 {
    public ViewParent a;
    public ViewParent b;
    public final View c;
    public boolean d;
    public int[] e;

    public b0(View view) {
        this.c = view;
    }

    public boolean a(float f3, float f4, boolean bl) {
        ViewParent viewParent;
        if (this.l() && (viewParent = this.h(0)) != null) {
            return f1.a(viewParent, this.c, f3, f4, bl);
        }
        return false;
    }

    public boolean b(float f3, float f4) {
        ViewParent viewParent;
        if (this.l() && (viewParent = this.h(0)) != null) {
            return f1.b(viewParent, this.c, f3, f4);
        }
        return false;
    }

    public boolean c(int n3, int n4, int[] nArray, int[] nArray2) {
        return this.d(n3, n4, nArray, nArray2, 0);
    }

    public boolean d(int n3, int n4, int[] nArray, int[] nArray2, int n5) {
        if (this.l()) {
            ViewParent viewParent = this.h(n5);
            if (viewParent == null) {
                return false;
            }
            if (n3 == 0 && n4 == 0) {
                if (nArray2 != null) {
                    nArray2[0] = 0;
                    nArray2[1] = 0;
                }
            } else {
                int n6;
                int n7;
                if (nArray2 != null) {
                    this.c.getLocationInWindow(nArray2);
                    n7 = nArray2[0];
                    n6 = nArray2[1];
                } else {
                    n7 = 0;
                    n6 = 0;
                }
                int[] nArray3 = nArray;
                if (nArray == null) {
                    nArray3 = this.i();
                }
                nArray3[0] = 0;
                nArray3[1] = 0;
                f1.c(viewParent, this.c, n3, n4, nArray3, n5);
                if (nArray2 != null) {
                    this.c.getLocationInWindow(nArray2);
                    nArray2[0] = nArray2[0] - n7;
                    nArray2[1] = nArray2[1] - n6;
                }
                return nArray3[0] != 0 || nArray3[1] != 0;
                {
                }
            }
        }
        return false;
    }

    public void e(int n3, int n4, int n5, int n6, int[] nArray, int n7, int[] nArray2) {
        this.g(n3, n4, n5, n6, nArray, n7, nArray2);
    }

    public boolean f(int n3, int n4, int n5, int n6, int[] nArray) {
        return this.g(n3, n4, n5, n6, nArray, 0, null);
    }

    public final boolean g(int n3, int n4, int n5, int n6, int[] nArray, int n7, int[] nArray2) {
        if (this.l()) {
            ViewParent viewParent = this.h(n7);
            if (viewParent == null) {
                return false;
            }
            if (n3 == 0 && n4 == 0 && n5 == 0 && n6 == 0) {
                if (nArray != null) {
                    nArray[0] = 0;
                    nArray[1] = 0;
                }
            } else {
                int n8;
                int n9;
                if (nArray != null) {
                    this.c.getLocationInWindow(nArray);
                    n9 = nArray[0];
                    n8 = nArray[1];
                } else {
                    n9 = 0;
                    n8 = 0;
                }
                if (nArray2 == null) {
                    nArray2 = this.i();
                    nArray2[0] = 0;
                    nArray2[1] = 0;
                }
                f1.d(viewParent, this.c, n3, n4, n5, n6, n7, nArray2);
                if (nArray != null) {
                    this.c.getLocationInWindow(nArray);
                    nArray[0] = nArray[0] - n9;
                    nArray[1] = nArray[1] - n8;
                }
                return true;
            }
        }
        return false;
    }

    public final ViewParent h(int n3) {
        if (n3 != 0) {
            if (n3 != 1) {
                return null;
            }
            return this.b;
        }
        return this.a;
    }

    public final int[] i() {
        if (this.e == null) {
            this.e = new int[2];
        }
        return this.e;
    }

    public boolean j() {
        return this.k(0);
    }

    public boolean k(int n3) {
        return this.h(n3) != null;
    }

    public boolean l() {
        return this.d;
    }

    public void m(boolean bl) {
        if (this.d) {
            x0.B0(this.c);
        }
        this.d = bl;
    }

    public final void n(int n3, ViewParent viewParent) {
        if (n3 != 0) {
            if (n3 != 1) {
                return;
            }
            this.b = viewParent;
            return;
        }
        this.a = viewParent;
    }

    public boolean o(int n3) {
        return this.p(n3, 0);
    }

    public boolean p(int n3, int n4) {
        if (this.k(n4)) {
            return true;
        }
        if (this.l()) {
            View view = this.c;
            for (ViewParent viewParent = this.c.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
                if (f1.f(viewParent, view, this.c, n3, n4)) {
                    this.n(n4, viewParent);
                    f1.e(viewParent, view, this.c, n3, n4);
                    return true;
                }
                if (!(viewParent instanceof View)) continue;
                view = (View)viewParent;
            }
        }
        return false;
    }

    public void q() {
        this.r(0);
    }

    public void r(int n3) {
        ViewParent viewParent = this.h(n3);
        if (viewParent != null) {
            f1.g(viewParent, this.c, n3);
            this.n(n3, null);
        }
    }
}

