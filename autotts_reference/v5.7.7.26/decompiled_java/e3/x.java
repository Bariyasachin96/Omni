/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.u;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import o3.f;
import o3.k;

public final class x
implements Set,
Serializable {
    public static final x c = new x();

    public boolean a(Void void_) {
        k.e(void_, "element");
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public int b() {
        return 0;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean containsAll(Collection collection) {
        k.e(collection, "elements");
        return collection.isEmpty();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Set && ((Set)object).isEmpty();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator iterator() {
        return u.c;
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public Object[] toArray() {
        return f.a(this);
    }

    @Override
    public Object[] toArray(Object[] objectArray) {
        k.e(objectArray, "array");
        return f.b(this, objectArray);
    }

    public String toString() {
        return "[]";
    }
}

