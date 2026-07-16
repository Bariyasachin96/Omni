/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import androidx.lifecycle.p;
import java.util.Map;
import k.b;

public abstract class LiveData {
    public static final Object k = new Object();
    public final Object a = new Object();
    public k.b b = new k.b();
    public int c = 0;
    public boolean d;
    public volatile Object e;
    public volatile Object f;
    public int g;
    public boolean h;
    public boolean i;
    public final Runnable j;

    public LiveData() {
        Object object;
        this.f = object = k;
        this.j = new Runnable(this){
            public final LiveData c;
            {
                this.c = liveData;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object;
                Object object2 = this.c.a;
                synchronized (object2) {
                    object = this.c.f;
                    this.c.f = k;
                }
                this.c.i(object);
            }
        };
        this.e = object;
        this.g = -1;
    }

    public static void a(String string) {
        if (j.c.f().b()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot invoke ");
        stringBuilder.append(string);
        stringBuilder.append(" on a background thread");
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(int n3) {
        Throwable throwable2;
        int n4 = this.c;
        this.c = n3 + n4;
        if (this.d) {
            return;
        }
        this.d = true;
        while (true) {
            int n5;
            block7: {
                block6: {
                    try {
                        n5 = this.c;
                        if (n4 == n5) {
                            this.d = false;
                            return;
                        }
                        n3 = n4 == 0 && n5 > 0 ? 1 : 0;
                        n4 = n4 > 0 && n5 == 0 ? 1 : 0;
                        if (n3 == 0) break block6;
                        this.f();
                        break block7;
                    }
                    catch (Throwable throwable2) {
                        break;
                    }
                }
                if (n4 != 0) {
                    this.g();
                }
            }
            n4 = n5;
        }
        this.d = false;
        throw throwable2;
    }

    public final void c(c c3) {
        int n3;
        block5: {
            block4: {
                if (!c3.b) break block4;
                if (!c3.j()) {
                    c3.h(false);
                    return;
                }
                int n4 = c3.c;
                n3 = this.g;
                if (n4 < n3) break block5;
            }
            return;
        }
        c3.c = n3;
        c3.a.a(this.e);
    }

    public void d(c c3) {
        if (this.h) {
            this.i = true;
            return;
        }
        this.h = true;
        c c4 = c3;
        do {
            block5: {
                this.i = false;
                if (c4 != null) {
                    this.c(c4);
                    c3 = null;
                } else {
                    b.d d3 = this.b.c();
                    do {
                        c3 = c4;
                        if (!d3.hasNext()) break block5;
                        this.c((c)((Map.Entry)d3.next()).getValue());
                    } while (!this.i);
                    c3 = c4;
                }
            }
            c4 = c3;
        } while (this.i);
        this.h = false;
    }

    public void e(p object) {
        LiveData.a("observeForever");
        b b3 = new b(this, (p)object);
        object = (c)this.b.f(object, b3);
        if (!(object instanceof LifecycleBoundObserver)) {
            if (object != null) {
                return;
            }
            b3.h(true);
            return;
        }
        throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
    }

    public void f() {
    }

    public void g() {
    }

    public void h(p object) {
        LiveData.a("removeObserver");
        object = (c)this.b.g(object);
        if (object == null) {
            return;
        }
        ((c)object).i();
        ((c)object).h(false);
    }

    public void i(Object object) {
        LiveData.a("setValue");
        ++this.g;
        this.e = object;
        this.d(null);
    }

    /*
     * Signature claims super is androidx.lifecycle.LiveData.c, not androidx.lifecycle.LiveData$c - discarding signature.
     */
    public class LifecycleBoundObserver
    extends c
    implements i {
        public final k e;
        public final LiveData f;

        @Override
        public void d(k object, f.a object2) {
            object = this.e.t().b();
            if (object == f.b.c) {
                this.f.h(this.a);
                return;
            }
            object2 = null;
            while (object2 != object) {
                this.h(this.j());
                f.b b3 = this.e.t().b();
                object2 = object;
                object = b3;
            }
        }

        @Override
        public void i() {
            this.e.t().c(this);
        }

        @Override
        public boolean j() {
            return this.e.t().b().b(f.b.f);
        }
    }

    public class b
    extends c {
        public final LiveData e;

        public b(LiveData liveData, p p3) {
            this.e = liveData;
            super(liveData, p3);
        }

        @Override
        public boolean j() {
            return true;
        }
    }

    public abstract class c {
        public final p a;
        public boolean b;
        public int c;
        public final LiveData d;

        public c(LiveData liveData, p p3) {
            this.d = liveData;
            this.c = -1;
            this.a = p3;
        }

        public void h(boolean bl) {
            if (bl != this.b) {
                this.b = bl;
                LiveData liveData = this.d;
                int n3 = bl ? 1 : -1;
                liveData.b(n3);
                if (this.b) {
                    this.d.d(this);
                }
            }
        }

        public void i() {
        }

        public abstract boolean j();
    }
}

