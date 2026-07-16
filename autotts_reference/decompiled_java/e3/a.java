/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.t;
import java.util.Collection;
import java.util.Iterator;
import n3.l;
import o3.f;
import o3.k;

public abstract class a
implements Collection {
    public abstract int a();

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
        if (this.isEmpty()) {
            return false;
        }
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!k.a(iterator.next(), object)) continue;
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection object) {
        k.e(object, "elements");
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
        return this.size() == 0;
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
    public Object[] toArray() {
        return f.a(this);
    }

    public Object[] toArray(Object[] objectArray) {
        k.e(objectArray, "array");
        return f.b(this, objectArray);
    }

    public String toString() {
        return t.v(this, ", ", "[", "]", 0, null, new l(this){
            public final a d;
            {
                this.d = a4;
                super(1);
            }

            public final CharSequence b(Object object) {
                if (object == this.d) {
                    return "(this Collection)";
                }
                return String.valueOf(object);
            }
        }, 24, null);
    }
}

