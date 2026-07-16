/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.i;
import e3.k;
import java.util.Collection;
import java.util.Iterator;
import o3.b;
import o3.f;

public final class d
implements Collection {
    public final Object[] c;
    public final boolean d;

    public d(Object[] objectArray, boolean bl) {
        o3.k.e(objectArray, "values");
        this.c = objectArray;
        this.d = bl;
    }

    public int a() {
        return this.c.length;
    }

    public boolean add(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean contains(Object object) {
        return i.o(this.c, object);
    }

    public boolean containsAll(Collection object) {
        o3.k.e(object, "elements");
        if (object.isEmpty()) {
            return true;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains(object.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.c.length == 0;
    }

    @Override
    public Iterator iterator() {
        return b.a(this.c);
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public final Object[] toArray() {
        return k.b(this.c, this.d);
    }

    public Object[] toArray(Object[] objectArray) {
        o3.k.e(objectArray, "array");
        return f.b(this, objectArray);
    }
}

