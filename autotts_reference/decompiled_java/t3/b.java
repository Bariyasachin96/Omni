/*
 * Decompiled with CFR 0.152.
 */
package t3;

import d3.e;
import d3.f;
import d3.j;
import g3.a;
import g3.d;
import java.util.Iterator;
import java.util.NoSuchElementException;
import o3.k;
import t3.c;

public final class b
extends c
implements Iterator,
a {
    public int c;
    public Object d;
    public Iterator e;
    public a f;

    @Override
    public Object a(Object object, a a4) {
        this.d = object;
        this.c = 3;
        this.f = a4;
        object = h3.c.b();
        if (object == h3.c.b()) {
            i3.f.c(a4);
        }
        if (object == h3.c.b()) {
            return object;
        }
        return j.a;
    }

    @Override
    public g3.c b() {
        return g3.d.c;
    }

    @Override
    public Object c(Iterator object, a a4) {
        if (!object.hasNext()) {
            return j.a;
        }
        this.e = object;
        this.c = 2;
        this.f = a4;
        object = h3.c.b();
        if (object == h3.c.b()) {
            i3.f.c(a4);
        }
        if (object == h3.c.b()) {
            return object;
        }
        return j.a;
    }

    @Override
    public void d(Object object) {
        d3.f.b(object);
        this.c = 4;
    }

    public final Throwable f() {
        int n3 = this.c;
        if (n3 != 4) {
            if (n3 != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected state of the iterator: ");
                stringBuilder.append(this.c);
                return new IllegalStateException(stringBuilder.toString());
            }
            return new IllegalStateException("Iterator has failed.");
        }
        return new NoSuchElementException();
    }

    public final Object g() {
        if (this.hasNext()) {
            return this.next();
        }
        throw new NoSuchElementException();
    }

    public final void h(a a4) {
        this.f = a4;
    }

    @Override
    public boolean hasNext() {
        while (true) {
            Object object;
            int n3;
            if ((n3 = this.c) != 0) {
                if (n3 != 1) {
                    if (n3 != 2 && n3 != 3) {
                        if (n3 == 4) {
                            return false;
                        }
                        throw this.f();
                    }
                    return true;
                }
                object = this.e;
                k.b(object);
                if (object.hasNext()) {
                    this.c = 2;
                    return true;
                }
                this.e = null;
            }
            this.c = 5;
            a a4 = this.f;
            k.b(a4);
            this.f = null;
            object = d3.e.c;
            a4.d(d3.e.a(j.a));
        }
    }

    public Object next() {
        int n3 = this.c;
        if (n3 != 0 && n3 != 1) {
            if (n3 != 2) {
                if (n3 == 3) {
                    this.c = 0;
                    Object object = this.d;
                    this.d = null;
                    return object;
                }
                throw this.f();
            }
            this.c = 1;
            Iterator iterator = this.e;
            k.b(iterator);
            return iterator.next();
        }
        return this.g();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

