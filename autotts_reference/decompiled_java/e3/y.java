/*
 * Decompiled with CFR 0.152.
 */
package e3;

import java.util.Iterator;

public abstract class y
implements Iterator {
    public abstract int nextInt();

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

