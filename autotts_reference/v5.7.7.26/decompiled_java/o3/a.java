/*
 * Decompiled with CFR 0.152.
 */
package o3;

import java.util.Iterator;
import java.util.NoSuchElementException;
import o3.k;

public final class a
implements Iterator {
    public final Object[] c;
    public int d;

    public a(Object[] objectArray) {
        k.e(objectArray, "array");
        this.c = objectArray;
    }

    @Override
    public boolean hasNext() {
        return this.d < this.c.length;
    }

    public Object next() {
        int n3;
        Object object;
        try {
            object = this.c;
            n3 = this.d;
            this.d = n3 + 1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            --this.d;
            throw new NoSuchElementException(arrayIndexOutOfBoundsException.getMessage());
        }
        object = object[n3];
        return object;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

