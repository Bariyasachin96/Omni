/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.u;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import o3.f;
import o3.k;

public final class v
implements List,
Serializable,
RandomAccess {
    public static final v c = new v();

    public boolean a(Void void_) {
        k.e(void_, "element");
        return false;
    }

    public boolean addAll(int n3, Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Void b(int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Empty list doesn't contain element at index ");
        stringBuilder.append(n3);
        stringBuilder.append('.');
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int c() {
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

    public int d(Void void_) {
        k.e(void_, "element");
        return -1;
    }

    public int e(Void void_) {
        k.e(void_, "element");
        return -1;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof List && ((List)object).isEmpty();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator iterator() {
        return u.c;
    }

    public ListIterator listIterator() {
        return u.c;
    }

    public ListIterator listIterator(int n3) {
        if (n3 == 0) {
            return u.c;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: ");
        stringBuilder.append(n3);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
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

    public List subList(int n3, int n4) {
        if (n3 == 0 && n4 == 0) {
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromIndex: ");
        stringBuilder.append(n3);
        stringBuilder.append(", toIndex: ");
        stringBuilder.append(n4);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
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

