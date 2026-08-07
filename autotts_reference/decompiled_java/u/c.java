/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import u.e;

public class c {
    public e a;
    public e b;
    public e c;
    public e d;
    public e e;
    public e f;
    public e g;
    public ArrayList h;
    public int i;
    public int j;
    public float k = 0.0f;
    public int l;
    public int m;
    public int n;
    public boolean o;
    public int p;
    public boolean q;
    public boolean r;
    public boolean s;
    public boolean t;
    public boolean u;
    public boolean v;

    public c(e e3, int n3, boolean bl) {
        this.a = e3;
        this.p = n3;
        this.q = bl;
    }

    public static boolean c(e e3, int n3) {
        return e3.X() != 8 && e3.b0[n3] == e.b.e && ((n3 = e3.y[n3]) == 0 || n3 == 3);
    }

    public void a() {
        if (!this.v) {
            this.b();
        }
        this.v = true;
    }

    public final void b() {
        int n3 = this.p * 2;
        Object object = this.a;
        boolean bl = true;
        this.o = true;
        Object object2 = object;
        boolean bl2 = false;
        while (!bl2) {
            Object object3;
            ++this.i;
            Object object4 = ((e)object).P0;
            int n4 = this.p;
            Object object5 = null;
            object4[n4] = null;
            ((e)object).O0[n4] = null;
            if (((e)object).X() != 8) {
                int n5;
                ++this.l;
                object3 = ((e)object).w(this.p);
                if (object3 != (object4 = e.b.e)) {
                    this.m += ((e)object).G(this.p);
                }
                this.m = n5 = this.m + ((e)object).Y[n3].f();
                object3 = ((e)object).Y;
                n4 = n3 + 1;
                this.m = n5 + object3[n4].f();
                this.n = n5 = this.n + ((e)object).Y[n3].f();
                this.n = n5 + ((e)object).Y[n4].f();
                if (this.b == null) {
                    this.b = object;
                }
                this.d = object;
                object3 = ((e)object).b0;
                n5 = this.p;
                if (object3[n5] == object4) {
                    n4 = ((e)object).y[n5];
                    if (n4 == 0 || n4 == 3 || n4 == 2) {
                        ++this.j;
                        float f3 = ((e)object).N0[n5];
                        if (f3 > 0.0f) {
                            this.k += f3;
                        }
                        if (u.c.c((e)object, n5)) {
                            if (f3 < 0.0f) {
                                this.r = true;
                            } else {
                                this.s = true;
                            }
                            if (this.h == null) {
                                this.h = new ArrayList();
                            }
                            this.h.add(object);
                        }
                        if (this.f == null) {
                            this.f = object;
                        }
                        if ((object4 = this.g) != null) {
                            object4.O0[this.p] = object;
                        }
                        this.g = object;
                    }
                    if (this.p == 0) {
                        if (((e)object).w != 0) {
                            this.o = false;
                        } else if (((e)object).z != 0 || ((e)object).A != 0) {
                            this.o = false;
                        }
                    } else if (((e)object).x != 0) {
                        this.o = false;
                    } else if (((e)object).C != 0 || ((e)object).D != 0) {
                        this.o = false;
                    }
                    if (((e)object).f0 != 0.0f) {
                        this.o = false;
                        this.u = true;
                    }
                }
            }
            if (object2 != object) {
                ((e)object2).P0[this.p] = object;
            }
            object4 = ((e)object).Y[n3 + 1].f;
            object2 = object5;
            if (object4 != null) {
                object4 = object4.d;
                object3 = object4.Y[n3].f;
                object2 = object5;
                if (object3 != null) {
                    object2 = object3.d != object ? object5 : object4;
                }
            }
            if (object2 == null) {
                object2 = object;
                bl2 = true;
            }
            object5 = object;
            object = object2;
            object2 = object5;
        }
        object2 = this.b;
        if (object2 != null) {
            this.m -= ((e)object2).Y[n3].f();
        }
        if ((object2 = this.d) != null) {
            this.m -= ((e)object2).Y[n3 + 1].f();
        }
        this.c = object;
        this.e = this.p == 0 && this.q ? object : this.a;
        if (!this.s || !this.r) {
            bl = false;
        }
        this.t = bl;
    }
}

