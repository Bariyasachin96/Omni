/*
 * Decompiled with CFR 0.152.
 */
package o;

import e3.h;
import e3.t;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import o.c;
import o.d;
import o.i;
import o3.g;
import o3.k;

public final class b
implements Collection,
Set {
    public int[] c;
    public Object[] d;
    public int e;

    public b() {
        this(0, 1, null);
    }

    public b(int n3) {
        this.c = p.a.a;
        this.d = p.a.c;
        if (n3 > 0) {
            o.d.a(this, n3);
        }
    }

    public /* synthetic */ b(int n3, int n4, g g3) {
        if ((n4 & 1) != 0) {
            n3 = 0;
        }
        this(n3);
    }

    public final void a(int n3) {
        int n4 = this.e();
        if (this.c().length < n3) {
            int[] nArray = this.c();
            Object[] objectArray = this.b();
            o.d.a(this, n3);
            if (this.e() > 0) {
                h.i(nArray, this.c(), 0, 0, this.e(), 6, null);
                h.j(objectArray, this.b(), 0, 0, this.e(), 6, null);
            }
        }
        if (this.e() == n4) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public boolean add(Object object) {
        Object[] objectArray;
        int[] nArray;
        int n3;
        int n4;
        int n5 = this.e();
        int n6 = 0;
        if (object == null) {
            n4 = o.d.d(this);
            n3 = 0;
        } else {
            n3 = object.hashCode();
            n4 = o.d.c(this, object, n3);
        }
        if (n4 >= 0) {
            return false;
        }
        int n7 = ~n4;
        if (n5 >= this.c().length) {
            n4 = 8;
            if (n5 >= 8) {
                n4 = (n5 >> 1) + n5;
            } else if (n5 < 4) {
                n4 = 4;
            }
            nArray = this.c();
            objectArray = this.b();
            o.d.a(this, n4);
            if (n5 == this.e()) {
                n4 = n6;
                if (this.c().length == 0) {
                    n4 = 1;
                }
                if (n4 == 0) {
                    h.i(nArray, this.c(), 0, 0, nArray.length, 6, null);
                    h.j(objectArray, this.b(), 0, 0, objectArray.length, 6, null);
                }
            } else {
                throw new ConcurrentModificationException();
            }
        }
        if (n7 < n5) {
            objectArray = this.c();
            nArray = this.c();
            n4 = n7 + 1;
            h.e(objectArray, nArray, n4, n7, n5);
            h.g(this.b(), this.b(), n4, n7, n5);
        }
        if (n5 == this.e() && n7 < this.c().length) {
            this.c()[n7] = n3;
            this.b()[n7] = object;
            this.i(this.e() + 1);
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public boolean addAll(Collection object) {
        k.e(object, "elements");
        this.a(this.e() + object.size());
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            bl |= this.add(object.next());
        }
        return bl;
    }

    public final Object[] b() {
        return this.d;
    }

    public final int[] c() {
        return this.c;
    }

    @Override
    public void clear() {
        if (this.e() != 0) {
            this.h(p.a.a);
            this.g(p.a.c);
            this.i(0);
        }
        if (this.e() == 0) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0;
    }

    @Override
    public boolean containsAll(Collection object) {
        k.e(object, "elements");
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains(object.next())) continue;
            return false;
        }
        return true;
    }

    public int d() {
        return this.e;
    }

    public final int e() {
        return this.e;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Set)) return false;
        if (this.size() != ((Set)object).size()) {
            return false;
        }
        try {
            int n3 = this.e();
            int i3 = 0;
            while (i3 < n3) {
                Object object2 = this.j(i3);
                boolean bl = ((Set)object).contains(object2);
                if (!bl) {
                    return false;
                }
                ++i3;
            }
            return true;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    public final Object f(int n3) {
        int n4 = this.e();
        Object object = this.b()[n3];
        if (n4 <= 1) {
            this.clear();
            return object;
        }
        int n5 = n4 - 1;
        int n6 = this.c().length;
        int n7 = 8;
        if (n6 > 8 && this.e() < this.c().length / 3) {
            if (this.e() > 8) {
                n7 = this.e() + (this.e() >> 1);
            }
            int[] nArray = this.c();
            Object[] objectArray = this.b();
            o.d.a(this, n7);
            if (n3 > 0) {
                h.i(nArray, this.c(), 0, 0, n3, 6, null);
                h.j(objectArray, this.b(), 0, 0, n3, 6, null);
            }
            if (n3 < n5) {
                int[] nArray2 = this.c();
                n7 = n3 + 1;
                h.e(nArray, nArray2, n3, n7, n4);
                h.g(objectArray, this.b(), n3, n7, n4);
            }
        } else {
            if (n3 < n5) {
                int[] nArray = this.c();
                int[] nArray3 = this.c();
                n7 = n3 + 1;
                h.e(nArray, nArray3, n3, n7, n4);
                h.g(this.b(), this.b(), n3, n7, n4);
            }
            this.b()[n5] = null;
        }
        if (n4 == this.e()) {
            this.i(n5);
            return object;
        }
        throw new ConcurrentModificationException();
    }

    public final void g(Object[] objectArray) {
        k.e(objectArray, "<set-?>");
        this.d = objectArray;
    }

    public final void h(int[] nArray) {
        k.e(nArray, "<set-?>");
        this.c = nArray;
    }

    @Override
    public int hashCode() {
        int[] nArray = this.c();
        int n3 = this.e();
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            n4 += nArray[i3];
        }
        return n4;
    }

    public final void i(int n3) {
        this.e = n3;
    }

    public final int indexOf(Object object) {
        if (object == null) {
            return o.d.d(this);
        }
        return o.d.c(this, object, object.hashCode());
    }

    @Override
    public boolean isEmpty() {
        return this.e() <= 0;
    }

    @Override
    public Iterator iterator() {
        return new a(this);
    }

    public final Object j(int n3) {
        return this.b()[n3];
    }

    @Override
    public boolean remove(Object object) {
        int n3 = this.indexOf(object);
        if (n3 >= 0) {
            this.f(n3);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection object) {
        k.e(object, "elements");
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            bl |= this.remove(object.next());
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection collection) {
        k.e(collection, "elements");
        boolean bl = false;
        for (int i3 = this.e() - 1; -1 < i3; --i3) {
            if (t.o(collection, this.b()[i3])) continue;
            this.f(i3);
            bl = true;
        }
        return bl;
    }

    @Override
    public final Object[] toArray() {
        return h.k(this.d, 0, this.e);
    }

    @Override
    public final Object[] toArray(Object[] objectArray) {
        k.e(objectArray, "array");
        objectArray = o.c.a(objectArray, this.e);
        h.g(this.d, objectArray, 0, 0, this.e);
        k.d(objectArray, "result");
        return objectArray;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        CharSequence charSequence = new StringBuilder(this.e() * 14);
        ((StringBuilder)charSequence).append('{');
        int n3 = this.e();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            if (i3 > 0) {
                ((StringBuilder)charSequence).append(", ");
            }
            if ((object = this.j(i3)) != this) {
                ((StringBuilder)charSequence).append(object);
                continue;
            }
            ((StringBuilder)charSequence).append("(this Set)");
        }
        ((StringBuilder)charSequence).append('}');
        charSequence = ((StringBuilder)charSequence).toString();
        k.d(charSequence, "StringBuilder(capacity).…builderAction).toString()");
        return charSequence;
    }

    public final class a
    extends i {
        public final b f;

        public a(b b3) {
            this.f = b3;
            super(b3.e());
        }

        @Override
        public Object a(int n3) {
            return this.f.j(n3);
        }

        @Override
        public void b(int n3) {
            this.f.f(n3);
        }
    }
}

