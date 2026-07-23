/*
 * Decompiled with CFR 0.152.
 */
package f3;

import e3.c;
import e3.e;
import e3.h;
import f3.b;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import o3.k;

public final class a
extends c
implements List,
RandomAccess,
Serializable {
    public Object[] c;
    public int d;
    public int e;
    public boolean f;
    public final a g;
    public final a h;

    public a() {
        this(10);
    }

    public a(int n3) {
        this(b.d(n3), 0, 0, false, null, null);
    }

    public a(Object[] objectArray, int n3, int n4, boolean bl, a a4, a a5) {
        this.c = objectArray;
        this.d = n3;
        this.e = n4;
        this.f = bl;
        this.g = a4;
        this.h = a5;
    }

    private final void k(int n3) {
        if (this.g == null) {
            if (n3 >= 0) {
                Object[] objectArray = this.c;
                if (n3 > objectArray.length) {
                    n3 = e3.e.f.a(objectArray.length, n3);
                    this.c = b.e(this.c, n3);
                }
                return;
            }
            throw new OutOfMemoryError();
        }
        throw new IllegalStateException();
    }

    @Override
    public int a() {
        return this.e;
    }

    public void add(int n3, Object object) {
        this.i();
        e3.b.c.b(n3, this.e);
        this.g(this.d + n3, object);
    }

    @Override
    public boolean add(Object object) {
        this.i();
        this.g(this.d + this.e, object);
        return true;
    }

    public boolean addAll(int n3, Collection collection) {
        k.e(collection, "elements");
        this.i();
        e3.b.c.b(n3, this.e);
        int n4 = collection.size();
        this.f(this.d + n3, collection, n4);
        return n4 > 0;
    }

    @Override
    public boolean addAll(Collection collection) {
        k.e(collection, "elements");
        this.i();
        int n3 = collection.size();
        this.f(this.d + this.e, collection, n3);
        return n3 > 0;
    }

    @Override
    public Object b(int n3) {
        this.i();
        e3.b.c.a(n3, this.e);
        return this.o(this.d + n3);
    }

    @Override
    public void clear() {
        this.i();
        this.p(this.d, this.e);
    }

    @Override
    public boolean equals(Object object) {
        return object == this || object instanceof List && this.j((List)object);
        {
        }
    }

    public final void f(int n3, Collection object, int n4) {
        a a4 = this.g;
        if (a4 != null) {
            a4.f(n3, (Collection)object, n4);
            this.c = this.g.c;
            this.e += n4;
            return;
        }
        this.m(n3, n4);
        object = object.iterator();
        for (int i3 = 0; i3 < n4; ++i3) {
            this.c[n3 + i3] = object.next();
        }
    }

    public final void g(int n3, Object object) {
        a a4 = this.g;
        if (a4 != null) {
            a4.g(n3, object);
            this.c = this.g.c;
            ++this.e;
            return;
        }
        this.m(n3, 1);
        this.c[n3] = object;
    }

    public Object get(int n3) {
        e3.b.c.a(n3, this.e);
        return this.c[this.d + n3];
    }

    public final List h() {
        if (this.g == null) {
            this.i();
            this.f = true;
            return this;
        }
        throw new IllegalStateException();
    }

    @Override
    public int hashCode() {
        return b.b(this.c, this.d, this.e);
    }

    public final void i() {
        if (!this.n()) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object object) {
        for (int i3 = 0; i3 < this.e; ++i3) {
            if (!k.a(this.c[this.d + i3], object)) continue;
            return i3;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.e == 0;
    }

    @Override
    public Iterator iterator() {
        return new a(this, 0);
    }

    public final boolean j(List list) {
        return b.a(this.c, this.d, this.e, list);
    }

    public final void l(int n3) {
        this.k(this.e + n3);
    }

    @Override
    public int lastIndexOf(Object object) {
        for (int i3 = this.e - 1; i3 >= 0; --i3) {
            if (!k.a(this.c[this.d + i3], object)) continue;
            return i3;
        }
        return -1;
    }

    public ListIterator listIterator() {
        return new a(this, 0);
    }

    public ListIterator listIterator(int n3) {
        e3.b.c.b(n3, this.e);
        return new a(this, n3);
    }

    public final void m(int n3, int n4) {
        this.l(n4);
        Object[] objectArray = this.c;
        e3.h.g(objectArray, objectArray, n3 + n4, n3, this.d + this.e);
        this.e += n4;
    }

    public final boolean n() {
        a a4;
        return this.f || (a4 = this.h) != null && a4.f;
        {
        }
    }

    public final Object o(int n3) {
        Object object = this.g;
        if (object != null) {
            object = ((a)object).o(n3);
            --this.e;
            return object;
        }
        Object[] objectArray = this.c;
        object = objectArray[n3];
        e3.h.g(objectArray, objectArray, n3, n3 + 1, this.d + this.e);
        b.f(this.c, this.d + this.e - 1);
        --this.e;
        return object;
    }

    public final void p(int n3, int n4) {
        Object[] objectArray = this.g;
        if (objectArray != null) {
            objectArray.p(n3, n4);
        } else {
            objectArray = this.c;
            e3.h.g(objectArray, objectArray, n3, n3 + n4, this.e);
            objectArray = this.c;
            n3 = this.e;
            b.g(objectArray, n3 - n4, n3);
        }
        this.e -= n4;
    }

    public final int q(int n3, int n4, Collection objectArray, boolean bl) {
        Object[] objectArray2 = this.g;
        if (objectArray2 != null) {
            n3 = objectArray2.q(n3, n4, (Collection)objectArray, bl);
            this.e -= n3;
            return n3;
        }
        int n5 = 0;
        int n6 = 0;
        while (n5 < n4) {
            objectArray2 = this.c;
            int n7 = n3 + n5;
            if (objectArray.contains(objectArray2[n7]) == bl) {
                objectArray2 = this.c;
                ++n5;
                objectArray2[n6 + n3] = objectArray2[n7];
                ++n6;
                continue;
            }
            ++n5;
        }
        n5 = n4 - n6;
        objectArray = this.c;
        e3.h.g(objectArray, objectArray, n3 + n6, n4 + n3, this.e);
        objectArray = this.c;
        n3 = this.e;
        b.g(objectArray, n3 - n5, n3);
        this.e -= n5;
        return n5;
    }

    @Override
    public boolean remove(Object object) {
        this.i();
        int n3 = this.indexOf(object);
        if (n3 >= 0) {
            this.remove(n3);
        }
        return n3 >= 0;
    }

    @Override
    public boolean removeAll(Collection collection) {
        k.e(collection, "elements");
        this.i();
        return this.q(this.d, this.e, collection, false) > 0;
    }

    @Override
    public boolean retainAll(Collection collection) {
        k.e(collection, "elements");
        this.i();
        return this.q(this.d, this.e, collection, true) > 0;
    }

    public Object set(int n3, Object object) {
        this.i();
        e3.b.c.a(n3, this.e);
        Object[] objectArray = this.c;
        int n4 = this.d;
        Object object2 = objectArray[n4 + n3];
        objectArray[n4 + n3] = object;
        return object2;
    }

    public List subList(int n3, int n4) {
        e3.b.c.c(n3, n4, this.e);
        Object[] objectArray = this.c;
        int n5 = this.d;
        boolean bl = this.f;
        a a4 = this.h;
        if (a4 == null) {
            a4 = this;
        }
        return new a(objectArray, n5 + n3, n4 - n3, bl, this, a4);
    }

    @Override
    public Object[] toArray() {
        Object[] objectArray = this.c;
        int n3 = this.d;
        return e3.h.k(objectArray, n3, this.e + n3);
    }

    @Override
    public Object[] toArray(Object[] objectArray) {
        k.e(objectArray, "destination");
        int n3 = objectArray.length;
        int n4 = this.e;
        if (n3 < n4) {
            Object[] objectArray2 = this.c;
            n3 = this.d;
            objectArray = Arrays.copyOfRange(objectArray2, n3, n4 + n3, objectArray.getClass());
            k.d(objectArray, "copyOfRange(array, offse…h, destination.javaClass)");
            return objectArray;
        }
        Object[] objectArray3 = this.c;
        n3 = this.d;
        e3.h.g(objectArray3, objectArray, 0, n3, n4 + n3);
        n3 = objectArray.length;
        n4 = this.e;
        if (n3 > n4) {
            objectArray[n4] = null;
        }
        return objectArray;
    }

    @Override
    public String toString() {
        return b.c(this.c, this.d, this.e);
    }

    public static final class a
    implements ListIterator {
        public final a c;
        public int d;
        public int e;

        public a(a a4, int n3) {
            k.e(a4, "list");
            this.c = a4;
            this.d = n3;
            this.e = -1;
        }

        public void add(Object object) {
            a a4 = this.c;
            int n3 = this.d;
            this.d = n3 + 1;
            a4.add(n3, object);
            this.e = -1;
        }

        @Override
        public boolean hasNext() {
            return this.d < this.c.e;
        }

        @Override
        public boolean hasPrevious() {
            return this.d > 0;
        }

        @Override
        public Object next() {
            if (this.d < this.c.e) {
                int n3 = this.d;
                this.d = n3 + 1;
                this.e = n3;
                return this.c.c[this.c.d + this.e];
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return this.d;
        }

        public Object previous() {
            int n3 = this.d;
            if (n3 > 0) {
                this.d = --n3;
                this.e = n3;
                return this.c.c[this.c.d + this.e];
            }
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            return this.d - 1;
        }

        @Override
        public void remove() {
            int n3 = this.e;
            if (n3 != -1) {
                this.c.remove(n3);
                this.d = this.e;
                this.e = -1;
                return;
            }
            throw new IllegalStateException("Call next() or previous() before removing element from the iterator.");
        }

        public void set(Object object) {
            int n3 = this.e;
            if (n3 != -1) {
                this.c.set(n3, object);
                return;
            }
            throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.");
        }
    }
}

