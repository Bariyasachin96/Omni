/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.j;
import androidx.lifecycle.k;
import androidx.lifecycle.m;
import j.c;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import k.b;
import o3.g;

public class l
extends f {
    public static final a j = new a(null);
    public final boolean b;
    public k.a c;
    public f.b d;
    public final WeakReference e;
    public int f;
    public boolean g;
    public boolean h;
    public ArrayList i;

    public l(k k3) {
        o3.k.e(k3, "provider");
        this(k3, true);
    }

    public l(k k3, boolean bl) {
        this.b = bl;
        this.c = new k.a();
        this.d = f.b.d;
        this.i = new ArrayList();
        this.e = new WeakReference<k>(k3);
    }

    @Override
    public void a(j object) {
        k k3;
        o3.k.e(object, "observer");
        this.f("addObserver");
        Object object2 = this.d;
        Enum enum_ = f.b.c;
        if (object2 != enum_) {
            enum_ = f.b.d;
        }
        object2 = new b((j)object, (f.b)enum_);
        if ((b)this.c.f(object, object2) != null || (k3 = (k)this.e.get()) == null) {
            return;
        }
        boolean bl = this.f != 0 || this.g;
        enum_ = this.e((j)object);
        ++this.f;
        while (((b)object2).b().compareTo(enum_) < 0 && this.c.contains(object)) {
            this.l(((b)object2).b());
            enum_ = f.a.Companion.b(((b)object2).b());
            if (enum_ != null) {
                ((b)object2).a(k3, (f.a)enum_);
                this.k();
                enum_ = this.e((j)object);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("no event up from ");
            ((StringBuilder)object).append((Object)((b)object2).b());
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (!bl) {
            this.n();
        }
        --this.f;
    }

    @Override
    public f.b b() {
        return this.d;
    }

    @Override
    public void c(j j3) {
        o3.k.e(j3, "observer");
        this.f("removeObserver");
        this.c.g(j3);
    }

    public final void d(k object) {
        Iterator iterator = this.c.descendingIterator();
        o3.k.d(iterator, "observerMap.descendingIterator()");
        while (iterator.hasNext() && !this.h) {
            Object object2 = (Map.Entry)iterator.next();
            o3.k.d(object2, "next()");
            j j3 = (j)object2.getKey();
            object2 = (b)object2.getValue();
            while (((b)object2).b().compareTo(this.d) > 0 && !this.h && this.c.contains(j3)) {
                f.a a4 = f.a.Companion.a(((b)object2).b());
                if (a4 != null) {
                    this.l(a4.b());
                    ((b)object2).a((k)object, a4);
                    this.k();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("no event down from ");
                ((StringBuilder)object).append((Object)((b)object2).b());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
    }

    public final f.b e(j object) {
        object = this.c.h(object);
        Object object2 = null;
        object = object != null && (object = (b)object.getValue()) != null ? ((b)object).b() : null;
        if (!this.i.isEmpty()) {
            object2 = this.i;
            object2 = (f.b)((Object)object2.get(object2.size() - 1));
        }
        a a4 = j;
        return a4.a(a4.a(this.d, (f.b)((Object)object)), (f.b)((Object)object2));
    }

    public final void f(String string) {
        if (this.b && !j.c.f().b()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Method ");
            stringBuilder.append(string);
            stringBuilder.append(" must be called on the main thread");
            throw new IllegalStateException(stringBuilder.toString().toString());
        }
    }

    public final void g(k object) {
        b.d d3 = this.c.c();
        o3.k.d(d3, "observerMap.iteratorWithAdditions()");
        while (d3.hasNext() && !this.h) {
            Object object2 = (Map.Entry)d3.next();
            j j3 = (j)object2.getKey();
            object2 = (b)object2.getValue();
            while (((b)object2).b().compareTo(this.d) < 0 && !this.h && this.c.contains(j3)) {
                this.l(((b)object2).b());
                f.a a4 = f.a.Companion.b(((b)object2).b());
                if (a4 != null) {
                    ((b)object2).a((k)object, a4);
                    this.k();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("no event up from ");
                ((StringBuilder)object).append((Object)((b)object2).b());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
    }

    public void h(f.a a4) {
        o3.k.e((Object)a4, "event");
        this.f("handleLifecycleEvent");
        this.j(a4.b());
    }

    public final boolean i() {
        if (this.c.size() == 0) {
            return true;
        }
        Object object = this.c.a();
        o3.k.b(object);
        object = ((b)object.getValue()).b();
        Object object2 = this.c.d();
        o3.k.b(object2);
        object2 = ((b)object2.getValue()).b();
        return object == object2 && this.d == object2;
    }

    public final void j(f.b object) {
        block6: {
            block5: {
                f.b b3 = this.d;
                if (b3 == object) break block5;
                if (b3 == f.b.d && object == f.b.c) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("no event down from ");
                    ((StringBuilder)object).append((Object)this.d);
                    ((StringBuilder)object).append(" in component ");
                    ((StringBuilder)object).append(this.e.get());
                    throw new IllegalStateException(((StringBuilder)object).toString().toString());
                }
                this.d = object;
                if (this.g || this.f != 0) break block6;
                this.g = true;
                this.n();
                this.g = false;
                if (this.d == f.b.c) {
                    this.c = new k.a();
                }
            }
            return;
        }
        this.h = true;
    }

    public final void k() {
        ArrayList arrayList = this.i;
        arrayList.remove(arrayList.size() - 1);
    }

    public final void l(f.b b3) {
        this.i.add(b3);
    }

    public void m(f.b b3) {
        o3.k.e((Object)b3, "state");
        this.f("setCurrentState");
        this.j(b3);
    }

    public final void n() {
        k k3 = (k)this.e.get();
        if (k3 != null) {
            while (!this.i()) {
                this.h = false;
                Object object = this.d;
                Map.Entry entry = this.c.a();
                o3.k.b(entry);
                if (((Enum)object).compareTo(((b)entry.getValue()).b()) < 0) {
                    this.d(k3);
                }
                object = this.c.d();
                if (this.h || object == null || this.d.compareTo(((b)object.getValue()).b()) <= 0) continue;
                this.g(k3);
            }
            this.h = false;
            return;
        }
        throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already garbage collected. It is too late to change lifecycle state.");
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final f.b a(f.b b3, f.b b4) {
            o3.k.e((Object)b3, "state1");
            if (b4 != null && b4.compareTo(b3) < 0) {
                return b4;
            }
            return b3;
        }
    }

    public static final class b {
        public f.b a;
        public i b;

        public b(j j3, f.b b3) {
            o3.k.e((Object)b3, "initialState");
            o3.k.b(j3);
            this.b = m.f(j3);
            this.a = b3;
        }

        public final void a(k k3, f.a a4) {
            o3.k.e((Object)a4, "event");
            f.b b3 = a4.b();
            this.a = j.a(this.a, b3);
            i i3 = this.b;
            o3.k.b(k3);
            i3.d(k3, a4);
            this.a = b3;
        }

        public final f.b b() {
            return this.a;
        }
    }
}

