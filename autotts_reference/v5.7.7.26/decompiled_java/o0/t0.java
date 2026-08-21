/*
 * Decompiled with CFR 0.152.
 */
package o0;

import e3.q;
import e3.t;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import n3.l;

public final class t0
implements Iterator {
    public final l c;
    public final List d;
    public Iterator e;

    public t0(Iterator iterator, l l3) {
        this.c = l3;
        this.d = new ArrayList();
        this.e = iterator;
    }

    public final void a(Object object) {
        if ((object = (Iterator)this.c.f(object)) != null && object.hasNext()) {
            this.d.add(this.e);
            this.e = object;
            return;
        }
        while (!this.e.hasNext() && !this.d.isEmpty()) {
            this.e = (Iterator)t.w(this.d);
            q.n(this.d);
        }
    }

    @Override
    public boolean hasNext() {
        return this.e.hasNext();
    }

    public Object next() {
        Object e3 = this.e.next();
        this.a(e3);
        return e3;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}

