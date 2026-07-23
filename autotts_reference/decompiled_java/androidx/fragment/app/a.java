/*
 * Decompiled with CFR 0.152.
 */
package androidx.fragment.app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.d0;
import androidx.fragment.app.k;
import androidx.fragment.app.y;
import androidx.lifecycle.f;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public final class a
extends y
implements FragmentManager.l {
    public final FragmentManager t;
    public boolean u;
    public int v;
    public boolean w;

    public a(FragmentManager fragmentManager) {
        k k3 = fragmentManager.t0();
        ClassLoader classLoader = fragmentManager.v0() != null ? fragmentManager.v0().q().getClassLoader() : null;
        super(k3, classLoader);
        this.v = -1;
        this.w = false;
        this.t = fragmentManager;
    }

    /*
     * Unable to fully structure code
     */
    public Fragment A(ArrayList var1_1, Fragment var2_2) {
        block7: for (var3_3 = this.c.size() - 1; var3_3 >= 0; --var3_3) {
            var5_5 = (y.a)this.c.get(var3_3);
            var4_4 = var5_5.a;
            if (var4_4 == 1) ** GOTO lbl-1000
            if (var4_4 == 3) ** GOTO lbl-1000
            switch (var4_4) {
                default: {
                    continue block7;
                }
                case 10: {
                    var5_5.i = var5_5.h;
                    continue block7;
                }
                case 9: {
                    var2_2 = var5_5.b;
                    continue block7;
                }
                case 8: {
                    var2_2 = null;
                    continue block7;
                }
                case 6: lbl-1000:
                // 2 sources

                {
                    var1_1.add(var5_5.b);
                    continue block7;
                }
                case 7: lbl-1000:
                // 2 sources

                {
                    var1_1.remove(var5_5.b);
                }
            }
        }
        return var2_2;
    }

    @Override
    public boolean a(ArrayList arrayList, ArrayList arrayList2) {
        if (FragmentManager.I0(2)) {
            ((Object)this).toString();
        }
        arrayList.add(this);
        arrayList2.add(Boolean.FALSE);
        if (this.i) {
            this.t.i(this);
        }
        return true;
    }

    @Override
    public int f() {
        return this.s(false);
    }

    @Override
    public int g() {
        return this.s(true);
    }

    @Override
    public void h() {
        this.j();
        this.t.b0(this, false);
    }

    @Override
    public void i() {
        this.j();
        this.t.b0(this, true);
    }

    @Override
    public void k(int n3, Fragment fragment, String string, int n4) {
        super.k(n3, fragment, string, n4);
        fragment.v = this.t;
    }

    @Override
    public boolean l() {
        return this.c.isEmpty();
    }

    @Override
    public y m(Fragment fragment) {
        Object object = fragment.v;
        if (object != null && object != this.t) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot remove Fragment attached to a different FragmentManager. Fragment ");
            ((StringBuilder)object).append(fragment.toString());
            ((StringBuilder)object).append(" is already attached to a FragmentManager.");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        return super.m(fragment);
    }

    @Override
    public y p(Fragment object, f.b b3) {
        if (((Fragment)object).v == this.t) {
            if (b3 == f.b.d && ((Fragment)object).c > -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot set maximum Lifecycle to ");
                ((StringBuilder)object).append((Object)b3);
                ((StringBuilder)object).append(" after the Fragment has been created");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (b3 != f.b.c) {
                return super.p((Fragment)object, b3);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot set maximum Lifecycle to ");
            ((StringBuilder)object).append((Object)b3);
            ((StringBuilder)object).append(". Use remove() to remove the fragment from the FragmentManager and trigger its destruction.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot setMaxLifecycle for Fragment not attached to FragmentManager ");
        ((StringBuilder)object).append(this.t);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void r(int n3) {
        if (this.i) {
            if (FragmentManager.I0(2)) {
                ((Object)this).toString();
            }
            int n4 = this.c.size();
            for (int i3 = 0; i3 < n4; ++i3) {
                y.a a4 = (y.a)this.c.get(i3);
                Fragment fragment = a4.b;
                if (fragment == null) continue;
                fragment.u += n3;
                if (!FragmentManager.I0(2)) continue;
                Objects.toString(a4.b);
                int n5 = a4.b.u;
            }
        }
    }

    public int s(boolean bl) {
        if (!this.u) {
            if (FragmentManager.I0(2)) {
                ((Object)this).toString();
                PrintWriter printWriter = new PrintWriter(new d0("FragmentManager"));
                this.t("  ", printWriter);
                printWriter.close();
            }
            this.u = true;
            this.v = this.i ? this.t.l() : -1;
            this.t.Y(this, bl);
            return this.v;
        }
        throw new IllegalStateException("commit already called");
    }

    public void t(String string, PrintWriter printWriter) {
        this.u(string, printWriter, true);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.v >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.v);
        }
        if (this.k != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.k);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void u(String string, PrintWriter printWriter, boolean bl) {
        if (bl) {
            printWriter.print(string);
            printWriter.print("mName=");
            printWriter.print(this.k);
            printWriter.print(" mIndex=");
            printWriter.print(this.v);
            printWriter.print(" mCommitted=");
            printWriter.println(this.u);
            if (this.h != 0) {
                printWriter.print(string);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.h));
            }
            if (this.d != 0 || this.e != 0) {
                printWriter.print(string);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.d));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.e));
            }
            if (this.f != 0 || this.g != 0) {
                printWriter.print(string);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.g));
            }
            if (this.l != 0 || this.m != null) {
                printWriter.print(string);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.l));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.m);
            }
            if (this.n != 0 || this.o != null) {
                printWriter.print(string);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.n));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.o);
            }
        }
        if (!this.c.isEmpty()) {
            printWriter.print(string);
            printWriter.println("Operations:");
            int n3 = this.c.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                CharSequence charSequence;
                y.a a4 = (y.a)this.c.get(i3);
                switch (a4.a) {
                    default: {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("cmd=");
                        ((StringBuilder)charSequence).append(a4.a);
                        charSequence = ((StringBuilder)charSequence).toString();
                        break;
                    }
                    case 10: {
                        charSequence = "OP_SET_MAX_LIFECYCLE";
                        break;
                    }
                    case 9: {
                        charSequence = "UNSET_PRIMARY_NAV";
                        break;
                    }
                    case 8: {
                        charSequence = "SET_PRIMARY_NAV";
                        break;
                    }
                    case 7: {
                        charSequence = "ATTACH";
                        break;
                    }
                    case 6: {
                        charSequence = "DETACH";
                        break;
                    }
                    case 5: {
                        charSequence = "SHOW";
                        break;
                    }
                    case 4: {
                        charSequence = "HIDE";
                        break;
                    }
                    case 3: {
                        charSequence = "REMOVE";
                        break;
                    }
                    case 2: {
                        charSequence = "REPLACE";
                        break;
                    }
                    case 1: {
                        charSequence = "ADD";
                        break;
                    }
                    case 0: {
                        charSequence = "NULL";
                    }
                }
                printWriter.print(string);
                printWriter.print("  Op #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.print((String)charSequence);
                printWriter.print(" ");
                printWriter.println(a4.b);
                if (!bl) continue;
                if (a4.d != 0 || a4.e != 0) {
                    printWriter.print(string);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(a4.d));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(a4.e));
                }
                if (a4.f == 0 && a4.g == 0) continue;
                printWriter.print(string);
                printWriter.print("popEnterAnim=#");
                printWriter.print(Integer.toHexString(a4.f));
                printWriter.print(" popExitAnim=#");
                printWriter.println(Integer.toHexString(a4.g));
            }
        }
    }

    public void v() {
        int n3 = this.c.size();
        block11: for (int i3 = 0; i3 < n3; ++i3) {
            y.a a4 = (y.a)this.c.get(i3);
            Object object = a4.b;
            if (object != null) {
                ((Fragment)object).p = this.w;
                ((Fragment)object).y1(false);
                ((Fragment)object).x1(this.h);
                ((Fragment)object).A1(this.p, this.q);
            }
            switch (a4.a) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown cmd: ");
                    ((StringBuilder)object).append(a4.a);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 10: {
                    this.t.k1((Fragment)object, a4.i);
                    continue block11;
                }
                case 9: {
                    this.t.l1(null);
                    continue block11;
                }
                case 8: {
                    this.t.l1((Fragment)object);
                    continue block11;
                }
                case 7: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j1((Fragment)object, false);
                    this.t.n((Fragment)object);
                    continue block11;
                }
                case 6: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.w((Fragment)object);
                    continue block11;
                }
                case 5: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j1((Fragment)object, false);
                    this.t.n1((Fragment)object);
                    continue block11;
                }
                case 4: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.F0((Fragment)object);
                    continue block11;
                }
                case 3: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.b1((Fragment)object);
                    continue block11;
                }
                case 1: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j1((Fragment)object, false);
                    this.t.j((Fragment)object);
                }
            }
        }
    }

    public void w() {
        block11: for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
            y.a a4 = (y.a)this.c.get(i3);
            Object object = a4.b;
            if (object != null) {
                ((Fragment)object).p = this.w;
                ((Fragment)object).y1(true);
                ((Fragment)object).x1(FragmentManager.f1(this.h));
                ((Fragment)object).A1(this.q, this.p);
            }
            switch (a4.a) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown cmd: ");
                    ((StringBuilder)object).append(a4.a);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 10: {
                    this.t.k1((Fragment)object, a4.h);
                    continue block11;
                }
                case 9: {
                    this.t.l1((Fragment)object);
                    continue block11;
                }
                case 8: {
                    this.t.l1(null);
                    continue block11;
                }
                case 7: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j1((Fragment)object, true);
                    this.t.w((Fragment)object);
                    continue block11;
                }
                case 6: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.n((Fragment)object);
                    continue block11;
                }
                case 5: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j1((Fragment)object, true);
                    this.t.F0((Fragment)object);
                    continue block11;
                }
                case 4: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.n1((Fragment)object);
                    continue block11;
                }
                case 3: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j((Fragment)object);
                    continue block11;
                }
                case 1: {
                    ((Fragment)object).s1(a4.d, a4.e, a4.f, a4.g);
                    this.t.j1((Fragment)object, true);
                    this.t.b1((Fragment)object);
                }
            }
        }
    }

    public Fragment x(ArrayList arrayList, Fragment object) {
        int n3 = 0;
        Fragment fragment = object;
        while (n3 < this.c.size()) {
            int n4;
            block14: {
                y.a a4;
                block11: {
                    Fragment fragment2;
                    block12: {
                        block13: {
                            a4 = (y.a)this.c.get(n3);
                            n4 = a4.a;
                            if (n4 == 1) break block11;
                            if (n4 == 2) break block12;
                            if (n4 == 3 || n4 == 6) break block13;
                            if (n4 == 7) break block11;
                            if (n4 != 8) {
                                object = fragment;
                                n4 = n3;
                            } else {
                                this.c.add(n3, new y.a(9, fragment, true));
                                a4.c = true;
                                n4 = n3 + 1;
                                object = a4.b;
                            }
                            break block14;
                        }
                        arrayList.remove(a4.b);
                        fragment2 = a4.b;
                        object = fragment;
                        n4 = n3;
                        if (fragment2 == fragment) {
                            this.c.add(n3, new y.a(9, fragment2));
                            n4 = n3 + 1;
                            object = null;
                        }
                        break block14;
                    }
                    fragment2 = a4.b;
                    int n5 = fragment2.A;
                    int n6 = 0;
                    n4 = n3;
                    object = fragment;
                    for (int i3 = arrayList.size() - 1; i3 >= 0; --i3) {
                        Fragment fragment3 = (Fragment)arrayList.get(i3);
                        fragment = object;
                        int n7 = n4;
                        n3 = n6;
                        if (fragment3.A == n5) {
                            if (fragment3 == fragment2) {
                                n3 = 1;
                                fragment = object;
                                n7 = n4;
                            } else {
                                fragment = object;
                                n3 = n4;
                                if (fragment3 == object) {
                                    this.c.add(n4, new y.a(9, fragment3, true));
                                    n3 = n4 + 1;
                                    fragment = null;
                                }
                                object = new y.a(3, fragment3, true);
                                ((y.a)object).d = a4.d;
                                ((y.a)object).f = a4.f;
                                ((y.a)object).e = a4.e;
                                ((y.a)object).g = a4.g;
                                this.c.add(n3, object);
                                arrayList.remove(fragment3);
                                n7 = n3 + 1;
                                n3 = n6;
                            }
                        }
                        object = fragment;
                        n4 = n7;
                        n6 = n3;
                    }
                    if (n6 != 0) {
                        this.c.remove(n4);
                        --n4;
                    } else {
                        a4.a = 1;
                        a4.c = true;
                        arrayList.add(fragment2);
                    }
                    break block14;
                }
                arrayList.add(a4.b);
                n4 = n3;
                object = fragment;
            }
            n3 = n4 + 1;
            fragment = object;
        }
        return fragment;
    }

    public String y() {
        return this.k;
    }

    public void z() {
        if (this.s != null) {
            for (int i3 = 0; i3 < this.s.size(); ++i3) {
                ((Runnable)this.s.get(i3)).run();
            }
            this.s = null;
        }
    }
}

