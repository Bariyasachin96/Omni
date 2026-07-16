/*
 * Decompiled with CFR 0.152.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.h;
import java.util.ArrayList;
import java.util.List;
import n0.e;
import n0.f;

public final class a
implements h.a {
    public e a = new f(30);
    public final ArrayList b = new ArrayList();
    public final ArrayList c = new ArrayList();
    public final a d;
    public Runnable e;
    public final boolean f;
    public final h g;
    public int h = 0;

    public a(a a4) {
        this(a4, false);
    }

    public a(a a4, boolean bl) {
        this.d = a4;
        this.f = bl;
        this.g = new h(this);
    }

    @Override
    public void a(b b3) {
        if (!this.f) {
            b3.c = null;
            this.a.a(b3);
        }
    }

    @Override
    public b b(int n3, int n4, int n5, Object object) {
        b b3 = (b)this.a.b();
        if (b3 == null) {
            return new b(n3, n4, n5, object);
        }
        b3.a = n3;
        b3.b = n4;
        b3.d = n5;
        b3.c = object;
        return b3;
    }

    public final void c(b b3) {
        this.s(b3);
    }

    public final void d(b b3) {
        this.s(b3);
    }

    public int e(int n3) {
        int n4 = this.b.size();
        int n5 = n3;
        for (int i3 = 0; i3 < n4; ++i3) {
            b b3 = (b)this.b.get(i3);
            n3 = b3.a;
            if (n3 != 1) {
                int n6;
                if (n3 != 2) {
                    if (n3 != 8) {
                        n3 = n5;
                    } else {
                        n3 = b3.b;
                        if (n3 == n5) {
                            n3 = b3.d;
                        } else {
                            n6 = n5;
                            if (n3 < n5) {
                                n6 = n5 - 1;
                            }
                            n3 = n6;
                            if (b3.d <= n6) {
                                n3 = n6 + 1;
                            }
                        }
                    }
                } else {
                    n6 = b3.b;
                    n3 = n5;
                    if (n6 <= n5) {
                        n3 = b3.d;
                        if (n6 + n3 > n5) {
                            return -1;
                        }
                        n3 = n5 - n3;
                    }
                }
            } else {
                n3 = n5;
                if (b3.b <= n5) {
                    n3 = n5 + b3.d;
                }
            }
            n5 = n3;
        }
        return n5;
    }

    public final void f(b b3) {
        int n3 = b3.b;
        int n4 = b3.d + n3;
        int n5 = -1;
        int n6 = 0;
        for (int i3 = n3; i3 < n4; ++i3) {
            int n7;
            if (this.d.c(i3) == null && !this.h(i3)) {
                if (n5 == 1) {
                    this.s(this.b(2, n3, n6, null));
                    n5 = 1;
                } else {
                    n5 = 0;
                }
                int n8 = 0;
                n7 = n5;
                n5 = n8;
            } else {
                if (n5 == 0) {
                    this.k(this.b(2, n3, n6, null));
                    n7 = 1;
                } else {
                    n7 = 0;
                }
                n5 = 1;
            }
            if (n7 != 0) {
                i3 -= n6;
                n4 -= n6;
                n7 = 1;
            } else {
                n7 = n6 + 1;
            }
            n6 = n7;
        }
        b b4 = b3;
        if (n6 != b3.d) {
            this.a(b3);
            b4 = this.b(2, n3, n6, null);
        }
        if (n5 == 0) {
            this.k(b4);
            return;
        }
        this.s(b4);
    }

    public final void g(b b3) {
        int n3 = b3.b;
        int n4 = b3.d;
        int n5 = 0;
        int n6 = -1;
        int n7 = n3;
        for (int i3 = n3; i3 < n4 + n3; ++i3) {
            int n8;
            int n9;
            if (this.d.c(i3) == null && !this.h(i3)) {
                n9 = n7;
                n8 = n5;
                if (n6 == 1) {
                    this.s(this.b(4, n7, n5, b3.c));
                    n9 = i3;
                    n8 = 0;
                }
                n5 = 0;
                n7 = n9;
                n9 = n8;
            } else {
                n8 = n7;
                n9 = n5;
                if (n6 == 0) {
                    this.k(this.b(4, n7, n5, b3.c));
                    n8 = i3;
                    n9 = 0;
                }
                n5 = 1;
                n7 = n8;
            }
            n8 = n9 + 1;
            n6 = n5;
            n5 = n8;
        }
        Object object = b3;
        if (n5 != b3.d) {
            object = b3.c;
            this.a(b3);
            object = this.b(4, n7, n5, object);
        }
        if (n6 == 0) {
            this.k((b)object);
            return;
        }
        this.s((b)object);
    }

    public final boolean h(int n3) {
        int n4 = this.c.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            b b3 = (b)this.c.get(i3);
            int n5 = b3.a;
            if (n5 == 8) {
                if (this.n(b3.d, i3 + 1) != n3) continue;
                return true;
            }
            if (n5 != 1) continue;
            int n6 = b3.b;
            int n7 = b3.d;
            for (n5 = n6; n5 < n7 + n6; ++n5) {
                if (this.n(n5, i3 + 1) != n3) continue;
                return true;
            }
        }
        return false;
    }

    public void i() {
        int n3 = this.c.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            this.d.b((b)this.c.get(i3));
        }
        this.u(this.c);
        this.h = 0;
    }

    public void j() {
        this.i();
        int n3 = this.b.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = (b)this.b.get(i3);
            int n4 = ((b)object).a;
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 4) {
                        if (n4 == 8) {
                            this.d.b((b)object);
                            this.d.a(((b)object).b, ((b)object).d);
                        }
                    } else {
                        this.d.b((b)object);
                        this.d.h(((b)object).b, ((b)object).d, ((b)object).c);
                    }
                } else {
                    this.d.b((b)object);
                    this.d.f(((b)object).b, ((b)object).d);
                }
            } else {
                this.d.b((b)object);
                this.d.e(((b)object).b, ((b)object).d);
            }
            object = this.e;
            if (object == null) continue;
            object.run();
        }
        this.u(this.b);
        this.h = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void k(b b3) {
        Object object;
        int n3;
        int n4;
        int n5;
        int n6 = b3.a;
        if (n6 != 1 && n6 != 8) {
            n5 = this.w(b3.b, n6);
            n6 = b3.b;
            n4 = b3.a;
            if (n4 != 2) {
                if (n4 != 4) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("op should be remove or update.");
                    stringBuilder.append(b3);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                n3 = 1;
            } else {
                n3 = 0;
            }
            n4 = 1;
        } else {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        for (int i3 = 1; i3 < b3.d; ++i3) {
            int n7 = this.w(b3.b + n3 * i3, b3.a);
            int n8 = b3.a;
            if (n8 != 2 ? n8 == 4 && n7 == n5 + 1 : n7 == n5) {
                ++n4;
                continue;
            }
            object = this.b(n8, n5, n4, b3.c);
            this.l((b)object, n6);
            this.a((b)object);
            n5 = n6;
            if (b3.a == 4) {
                n5 = n6 + n4;
            }
            n4 = 1;
            n6 = n5;
            n5 = n7;
        }
        object = b3.c;
        this.a(b3);
        if (n4 > 0) {
            b3 = this.b(b3.a, n5, n4, object);
            this.l(b3, n6);
            this.a(b3);
        }
    }

    public void l(b b3, int n3) {
        this.d.g(b3);
        int n4 = b3.a;
        if (n4 != 2) {
            if (n4 == 4) {
                this.d.h(n3, b3.d, b3.c);
                return;
            }
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
        this.d.f(n3, b3.d);
    }

    public int m(int n3) {
        return this.n(n3, 0);
    }

    public int n(int n3, int n4) {
        int n5 = this.c.size();
        int n6 = n4;
        n4 = n3;
        while (n6 < n5) {
            int n7;
            b b3 = (b)this.c.get(n6);
            int n8 = b3.a;
            if (n8 == 8) {
                n3 = b3.b;
                if (n3 == n4) {
                    n3 = b3.d;
                } else {
                    n7 = n4;
                    if (n3 < n4) {
                        n7 = n4 - 1;
                    }
                    n3 = n7;
                    if (b3.d <= n7) {
                        n3 = n7 + 1;
                    }
                }
            } else {
                n7 = b3.b;
                n3 = n4;
                if (n7 <= n4) {
                    if (n8 == 2) {
                        n3 = b3.d;
                        if (n4 < n7 + n3) {
                            return -1;
                        }
                        n3 = n4 - n3;
                    } else {
                        n3 = n4;
                        if (n8 == 1) {
                            n3 = n4 + b3.d;
                        }
                    }
                }
            }
            ++n6;
            n4 = n3;
        }
        return n4;
    }

    public boolean o(int n3) {
        return (n3 & this.h) != 0;
    }

    public boolean p() {
        return this.b.size() > 0;
    }

    public boolean q() {
        return !this.c.isEmpty() && !this.b.isEmpty();
    }

    public boolean r(int n3, int n4, Object object) {
        if (n4 < 1) {
            return false;
        }
        this.b.add(this.b(4, n3, n4, object));
        this.h |= 4;
        return this.b.size() == 1;
    }

    public final void s(b b3) {
        this.c.add(b3);
        int n3 = b3.a;
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 4) {
                    if (n3 == 8) {
                        this.d.a(b3.b, b3.d);
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown update op type for ");
                    stringBuilder.append(b3);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.d.h(b3.b, b3.d, b3.c);
                return;
            }
            this.d.d(b3.b, b3.d);
            return;
        }
        this.d.e(b3.b, b3.d);
    }

    public void t() {
        this.g.b(this.b);
        int n3 = this.b.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = (b)this.b.get(i3);
            int n4 = ((b)object).a;
            if (n4 != 1) {
                if (n4 != 2) {
                    if (n4 != 4) {
                        if (n4 == 8) {
                            this.d((b)object);
                        }
                    } else {
                        this.g((b)object);
                    }
                } else {
                    this.f((b)object);
                }
            } else {
                this.c((b)object);
            }
            object = this.e;
            if (object == null) continue;
            object.run();
        }
        this.b.clear();
    }

    public void u(List list) {
        int n3 = list.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            this.a((b)list.get(i3));
        }
        list.clear();
    }

    public void v() {
        this.u(this.b);
        this.u(this.c);
        this.h = 0;
    }

    public final int w(int n3, int n4) {
        b b3;
        int n5 = n3;
        for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
            int n6;
            b3 = (b)this.c.get(i3);
            int n7 = b3.a;
            if (n7 == 8) {
                int n8;
                int n9;
                n6 = b3.b;
                n7 = b3.d;
                if (n6 < n7) {
                    n9 = n6;
                    n8 = n3 = n7;
                } else {
                    n8 = n6;
                    n9 = n3 = n7;
                }
                if (n5 >= n9 && n5 <= n8) {
                    if (n9 == n6) {
                        if (n4 == 1) {
                            b3.d = n7 + 1;
                        } else if (n4 == 2) {
                            b3.d = n7 - 1;
                        }
                        n3 = n5 + 1;
                    } else {
                        if (n4 == 1) {
                            b3.b = n6 + 1;
                        } else if (n4 == 2) {
                            b3.b = n6 - 1;
                        }
                        n3 = n5 - 1;
                    }
                } else {
                    n3 = n5;
                    if (n5 < n6) {
                        if (n4 == 1) {
                            b3.b = n6 + 1;
                            b3.d = n7 + 1;
                            n3 = n5;
                        } else {
                            n3 = n5;
                            if (n4 == 2) {
                                b3.b = n6 - 1;
                                b3.d = n7 - 1;
                                n3 = n5;
                            }
                        }
                    }
                }
            } else {
                n6 = b3.b;
                if (n6 <= n5) {
                    if (n7 == 1) {
                        n3 = n5 - b3.d;
                    } else {
                        n3 = n5;
                        if (n7 == 2) {
                            n3 = n5 + b3.d;
                        }
                    }
                } else if (n4 == 1) {
                    b3.b = n6 + 1;
                    n3 = n5;
                } else {
                    n3 = n5;
                    if (n4 == 2) {
                        b3.b = n6 - 1;
                        n3 = n5;
                    }
                }
            }
            n5 = n3;
        }
        for (n3 = this.c.size() - 1; n3 >= 0; --n3) {
            b3 = (b)this.c.get(n3);
            if (b3.a == 8) {
                n4 = b3.d;
                if (n4 != b3.b && n4 >= 0) continue;
                this.c.remove(n3);
                this.a(b3);
                continue;
            }
            if (b3.d > 0) continue;
            this.c.remove(n3);
            this.a(b3);
        }
        return n5;
    }

    public static interface a {
        public void a(int var1, int var2);

        public void b(b var1);

        public RecyclerView.d0 c(int var1);

        public void d(int var1, int var2);

        public void e(int var1, int var2);

        public void f(int var1, int var2);

        public void g(b var1);

        public void h(int var1, int var2, Object var3);
    }

    public static final class b {
        public int a;
        public int b;
        public Object c;
        public int d;

        public b(int n3, int n4, int n5, Object object) {
            this.a = n3;
            this.b = n4;
            this.d = n5;
            this.c = object;
        }

        public String a() {
            int n3 = this.a;
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 4) {
                        if (n3 != 8) {
                            return "??";
                        }
                        return "mv";
                    }
                    return "up";
                }
                return "rm";
            }
            return "add";
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof b)) {
                return false;
            }
            b b3 = (b)object;
            int n3 = this.a;
            if (n3 != b3.a) {
                return false;
            }
            if (n3 == 8 && Math.abs(this.d - this.b) == 1 && this.d == b3.b && this.b == b3.d) {
                return true;
            }
            if (this.d != b3.d) {
                return false;
            }
            if (this.b != b3.b) {
                return false;
            }
            object = this.c;
            return !(object != null ? !object.equals(b3.c) : b3.c != null);
        }

        public int hashCode() {
            return (this.a * 31 + this.b) * 31 + this.d;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append("[");
            stringBuilder.append(this.a());
            stringBuilder.append(",s:");
            stringBuilder.append(this.b);
            stringBuilder.append("c:");
            stringBuilder.append(this.d);
            stringBuilder.append(",p:");
            stringBuilder.append(this.c);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }
}

