/*
 * Decompiled with CFR 0.152.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public final class e
implements Runnable {
    public static final ThreadLocal g = new ThreadLocal();
    public static Comparator h = new Comparator(){

        public int a(c c3, c c4) {
            int n3;
            RecyclerView recyclerView = c3.d;
            int n4 = recyclerView == null ? 1 : 0;
            if (n4 != (n3 = c4.d == null)) {
                if (recyclerView == null) {
                    return 1;
                }
                return -1;
            }
            boolean bl = c3.a;
            if (bl != c4.a) {
                if (bl) {
                    return -1;
                }
                return 1;
            }
            n4 = c4.b - c3.b;
            if (n4 != 0) {
                return n4;
            }
            n4 = c3.c - c4.c;
            if (n4 != 0) {
                return n4;
            }
            return 0;
        }
    };
    public ArrayList c = new ArrayList();
    public long d;
    public long e;
    public ArrayList f = new ArrayList();

    public static boolean e(RecyclerView recyclerView, int n3) {
        int n4 = recyclerView.h.j();
        for (int i3 = 0; i3 < n4; ++i3) {
            RecyclerView.d0 d02 = RecyclerView.m0(recyclerView.h.i(i3));
            if (d02.c != n3 || d02.t()) continue;
            return true;
        }
        return false;
    }

    public void a(RecyclerView recyclerView) {
        if (RecyclerView.D0 && this.c.contains(recyclerView)) {
            throw new IllegalStateException("RecyclerView already present in worker list!");
        }
        this.c.add(recyclerView);
    }

    public final void b() {
        int n3;
        Object object;
        int n4;
        int n5 = this.c.size();
        int n6 = 0;
        for (n4 = 0; n4 < n5; ++n4) {
            object = (RecyclerView)this.c.get(n4);
            n3 = n6;
            if (object.getWindowVisibility() == 0) {
                ((RecyclerView)object).j0.c((RecyclerView)object, false);
                n3 = n6 + ((RecyclerView)object).j0.d;
            }
            n6 = n3;
        }
        this.f.ensureCapacity(n6);
        n6 = 0;
        for (n4 = 0; n4 < n5; ++n4) {
            int n7;
            RecyclerView recyclerView = (RecyclerView)this.c.get(n4);
            if (recyclerView.getWindowVisibility() != 0) {
                n7 = n6;
            } else {
                b b3 = recyclerView.j0;
                int n8 = Math.abs(b3.a) + Math.abs(b3.b);
                n3 = 0;
                while (true) {
                    n7 = n6;
                    if (n3 >= b3.d * 2) break;
                    if (n6 >= this.f.size()) {
                        object = new c();
                        this.f.add(object);
                    } else {
                        object = (c)this.f.get(n6);
                    }
                    int[] nArray = b3.c;
                    n7 = nArray[n3 + 1];
                    boolean bl = n7 <= n8;
                    ((c)object).a = bl;
                    ((c)object).b = n8;
                    ((c)object).c = n7;
                    ((c)object).d = recyclerView;
                    ((c)object).e = nArray[n3];
                    ++n6;
                    n3 += 2;
                }
            }
            n6 = n7;
        }
        Collections.sort(this.f, h);
    }

    public final void c(c object, long l3) {
        long l4 = ((c)object).a ? Long.MAX_VALUE : l3;
        object = this.i(((c)object).d, ((c)object).e, l4);
        if (object != null && ((RecyclerView.d0)object).b != null && ((RecyclerView.d0)object).s() && !((RecyclerView.d0)object).t()) {
            this.h((RecyclerView)((RecyclerView.d0)object).b.get(), l3);
        }
    }

    public final void d(long l3) {
        for (int i3 = 0; i3 < this.f.size(); ++i3) {
            c c3 = (c)this.f.get(i3);
            if (c3.d == null) break;
            this.c(c3, l3);
            c3.a();
        }
    }

    public void f(RecyclerView recyclerView, int n3, int n4) {
        if (recyclerView.isAttachedToWindow()) {
            if (RecyclerView.D0 && !this.c.contains(recyclerView)) {
                throw new IllegalStateException("attempting to post unregistered view!");
            }
            if (this.d == 0L) {
                this.d = recyclerView.getNanoTime();
                recyclerView.post(this);
            }
        }
        recyclerView.j0.e(n3, n4);
    }

    public void g(long l3) {
        this.b();
        this.d(l3);
    }

    public final void h(RecyclerView recyclerView, long l3) {
        block6: {
            Throwable throwable2;
            block7: {
                int n3;
                if (recyclerView == null) break block6;
                if (recyclerView.G && recyclerView.h.j() != 0) {
                    recyclerView.e1();
                }
                b b3 = recyclerView.j0;
                b3.c(recyclerView, true);
                if (b3.d == 0) break block6;
                try {
                    k0.e.a("RV Nested Prefetch");
                    recyclerView.k0.f(recyclerView.o);
                    n3 = 0;
                }
                catch (Throwable throwable2) {}
                while (true) {
                    if (n3 < b3.d * 2) {
                        this.i(recyclerView, b3.c[n3], l3);
                        n3 += 2;
                        continue;
                    }
                    break;
                }
                break block7;
                k0.e.b();
                return;
            }
            k0.e.b();
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final RecyclerView.d0 i(RecyclerView recyclerView, int n3, long l3) {
        Throwable throwable2;
        block5: {
            RecyclerView.d0 d02;
            block3: {
                RecyclerView.v v3;
                block4: {
                    if (androidx.recyclerview.widget.e.e(recyclerView, n3)) {
                        return null;
                    }
                    v3 = recyclerView.e;
                    try {
                        recyclerView.O0();
                        d02 = v3.N(n3, false, l3);
                        if (d02 == null) break block3;
                        if (!d02.s() || d02.t()) break block4;
                        v3.G(d02.a);
                        break block3;
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                }
                v3.a(d02, false);
            }
            recyclerView.Q0(false);
            return d02;
        }
        recyclerView.Q0(false);
        throw throwable2;
    }

    public void j(RecyclerView recyclerView) {
        boolean bl = this.c.remove(recyclerView);
        if (RecyclerView.D0 && !bl) {
            throw new IllegalStateException("RecyclerView removal failed!");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Throwable throwable2;
        block7: {
            block6: {
                try {
                    k0.e.a("RV Prefetch");
                    boolean bl = this.c.isEmpty();
                    if (bl) break block6;
                    int n3 = this.c.size();
                    long l3 = 0L;
                    for (int i3 = 0; i3 < n3; ++i3) {
                        RecyclerView recyclerView = (RecyclerView)this.c.get(i3);
                        long l4 = l3;
                        if (recyclerView.getWindowVisibility() == 0) {
                            l4 = Math.max(recyclerView.getDrawingTime(), l3);
                        }
                        l3 = l4;
                    }
                    if (l3 == 0L) break block6;
                    this.g(TimeUnit.MILLISECONDS.toNanos(l3) + this.e);
                }
                catch (Throwable throwable2) {
                    break block7;
                }
            }
            this.d = 0L;
            k0.e.b();
            return;
        }
        this.d = 0L;
        k0.e.b();
        throw throwable2;
    }

    public static class b
    implements RecyclerView.p.c {
        public int a;
        public int b;
        public int[] c;
        public int d;

        @Override
        public void a(int n3, int n4) {
            if (n3 >= 0) {
                if (n4 >= 0) {
                    int n5 = this.d;
                    int n6 = n5 * 2;
                    int[] nArray = this.c;
                    if (nArray == null) {
                        this.c = nArray = new int[4];
                        Arrays.fill(nArray, -1);
                    } else if (n6 >= nArray.length) {
                        int[] nArray2 = new int[n5 * 4];
                        this.c = nArray2;
                        System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
                    }
                    nArray = this.c;
                    nArray[n6] = n3;
                    nArray[n6 + 1] = n4;
                    ++this.d;
                    return;
                }
                throw new IllegalArgumentException("Pixel distance must be non-negative");
            }
            throw new IllegalArgumentException("Layout positions must be non-negative");
        }

        public void b() {
            int[] nArray = this.c;
            if (nArray != null) {
                Arrays.fill(nArray, -1);
            }
            this.d = 0;
        }

        public void c(RecyclerView recyclerView, boolean bl) {
            this.d = 0;
            Object object = this.c;
            if (object != null) {
                Arrays.fill(object, -1);
            }
            object = recyclerView.p;
            if (recyclerView.o != null && object != null && object.y0()) {
                int n3;
                if (bl) {
                    if (!recyclerView.g.p()) {
                        object.u(recyclerView.o.f(), this);
                    }
                } else if (!recyclerView.t0()) {
                    object.t(this.a, this.b, recyclerView.k0, this);
                }
                if ((n3 = this.d) > object.m) {
                    object.m = n3;
                    object.n = bl;
                    recyclerView.e.P();
                }
            }
        }

        public boolean d(int n3) {
            if (this.c != null) {
                int n4 = this.d;
                for (int i3 = 0; i3 < n4 * 2; i3 += 2) {
                    if (this.c[i3] != n3) continue;
                    return true;
                }
            }
            return false;
        }

        public void e(int n3, int n4) {
            this.a = n3;
            this.b = n4;
        }
    }

    public static class c {
        public boolean a;
        public int b;
        public int c;
        public RecyclerView d;
        public int e;

        public void a() {
            this.a = false;
            this.b = 0;
            this.c = 0;
            this.d = null;
            this.e = 0;
        }
    }
}

