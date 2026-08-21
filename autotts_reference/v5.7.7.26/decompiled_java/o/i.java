/*
 * Decompiled with CFR 0.152.
 */
package o;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class i
implements Iterator {
    public int c;
    public int d;
    public boolean e;

    public i(int n3) {
        this.c = n3;
    }

    public abstract Object a(int var1);

    public abstract void b(int var1);

    @Override
    public boolean hasNext() {
        return this.d < this.c;
    }

    public Object next() {
        if (this.hasNext()) {
            Object object = this.a(this.d);
            ++this.d;
            this.e = true;
            return object;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        if (this.e) {
            int n3;
            this.d = n3 = this.d - 1;
            this.b(n3);
            --this.c;
            this.e = false;
            return;
        }
        throw new IllegalStateException("Call next() before removing an element.");
    }
}

