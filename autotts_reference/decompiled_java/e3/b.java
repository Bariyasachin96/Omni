/*
 * Decompiled with CFR 0.152.
 */
package e3;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import o3.g;
import o3.k;

public abstract class b
extends e3.a
implements List {
    public static final a c = new a(null);

    public void add(int n3, Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(int n3, Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        return c.d(this, (Collection)object);
    }

    public abstract Object get(int var1);

    @Override
    public int hashCode() {
        return c.e(this);
    }

    @Override
    public int indexOf(Object object) {
        Iterator iterator = this.iterator();
        int n3 = 0;
        while (iterator.hasNext()) {
            if (k.a(iterator.next(), object)) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    @Override
    public Iterator iterator() {
        return new b(this);
    }

    @Override
    public int lastIndexOf(Object object) {
        ListIterator listIterator = this.listIterator(this.size());
        while (listIterator.hasPrevious()) {
            if (!k.a(listIterator.previous(), object)) continue;
            return listIterator.nextIndex();
        }
        return -1;
    }

    public ListIterator listIterator() {
        return new c(this, 0);
    }

    public ListIterator listIterator(int n3) {
        return new c(this, n3);
    }

    public Object remove(int n3) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Object set(int n3, Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public List subList(int n3, int n4) {
        return new d(this, n3, n4);
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final void a(int n3, int n4) {
            if (n3 >= 0 && n3 < n4) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index: ");
            stringBuilder.append(n3);
            stringBuilder.append(", size: ");
            stringBuilder.append(n4);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public final void b(int n3, int n4) {
            if (n3 >= 0 && n3 <= n4) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("index: ");
            stringBuilder.append(n3);
            stringBuilder.append(", size: ");
            stringBuilder.append(n4);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public final void c(int n3, int n4, int n5) {
            if (n3 >= 0 && n4 <= n5) {
                if (n3 <= n4) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("fromIndex: ");
                stringBuilder.append(n3);
                stringBuilder.append(" > toIndex: ");
                stringBuilder.append(n4);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex: ");
            stringBuilder.append(n3);
            stringBuilder.append(", toIndex: ");
            stringBuilder.append(n4);
            stringBuilder.append(", size: ");
            stringBuilder.append(n5);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        public final boolean d(Collection object, Collection object2) {
            k.e(object, "c");
            k.e(object2, "other");
            if (object.size() != object2.size()) {
                return false;
            }
            object2 = object2.iterator();
            object = object.iterator();
            while (object.hasNext()) {
                if (k.a(object.next(), object2.next())) continue;
                return false;
            }
            return true;
        }

        public final int e(Collection collection) {
            k.e(collection, "c");
            Iterator iterator = collection.iterator();
            int n3 = 1;
            while (iterator.hasNext()) {
                collection = iterator.next();
                int n4 = collection != null ? ((Object)collection).hashCode() : 0;
                n3 = n3 * 31 + n4;
            }
            return n3;
        }
    }

    public class b
    implements Iterator {
        public int c;
        public final b d;

        public b(b b3) {
            this.d = b3;
        }

        public final int a() {
            return this.c;
        }

        public final void b(int n3) {
            this.c = n3;
        }

        @Override
        public boolean hasNext() {
            return this.c < this.d.size();
        }

        public Object next() {
            if (this.hasNext()) {
                b b3 = this.d;
                int n3 = this.c;
                this.c = n3 + 1;
                return b3.get(n3);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public class c
    extends b
    implements ListIterator {
        public final b e;

        public c(b b3, int n3) {
            this.e = b3;
            super(b3);
            c.b(n3, b3.size());
            this.b(n3);
        }

        public void add(Object object) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override
        public boolean hasPrevious() {
            return this.a() > 0;
        }

        @Override
        public int nextIndex() {
            return this.a();
        }

        public Object previous() {
            if (this.hasPrevious()) {
                b b3 = this.e;
                this.b(this.a() - 1);
                return b3.get(this.a());
            }
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            return this.a() - 1;
        }

        public void set(Object object) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public static final class d
    extends b
    implements RandomAccess {
        public final b d;
        public final int e;
        public int f;

        public d(b b3, int n3, int n4) {
            k.e(b3, "list");
            this.d = b3;
            this.e = n3;
            c.c(n3, n4, b3.size());
            this.f = n4 - n3;
        }

        @Override
        public int a() {
            return this.f;
        }

        @Override
        public Object get(int n3) {
            c.a(n3, this.f);
            return this.d.get(this.e + n3);
        }
    }
}

