/*
 * Decompiled with CFR 0.152.
 */
package e3;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public final class u
implements ListIterator {
    public static final u c = new u();

    public Void a() {
        throw new NoSuchElementException();
    }

    public Void b() {
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public int nextIndex() {
        return 0;
    }

    @Override
    public int previousIndex() {
        return -1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

