/*
 * Decompiled with CFR 0.152.
 */
package o;

import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import o.i;
import o.r;

public class a
extends r
implements Map {
    public a f;
    public c g;
    public e h;

    public a() {
    }

    public a(int n3) {
        super(n3);
    }

    public a(r r3) {
        super(r3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean l(Set set, Object object) {
        if (set == object) {
            return true;
        }
        if (!(object instanceof Set)) return false;
        object = (Set)object;
        try {
            if (set.size() != object.size()) return false;
            boolean bl = set.containsAll((Collection<?>)object);
            if (!bl) return false;
            return true;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    @Override
    public boolean containsKey(Object object) {
        return super.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return super.containsValue(object);
    }

    public Set entrySet() {
        a a4;
        a a5 = a4 = this.f;
        if (a4 == null) {
            this.f = a5 = new a(this);
        }
        return a5;
    }

    @Override
    public Object get(Object object) {
        return super.get(object);
    }

    public boolean k(Collection object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (this.containsKey(object.next())) continue;
            return false;
        }
        return true;
    }

    public Set keySet() {
        c c3;
        c c4 = c3 = this.g;
        if (c3 == null) {
            this.g = c4 = new c(this);
        }
        return c4;
    }

    public boolean m(Collection object) {
        int n3 = this.size();
        object = object.iterator();
        while (object.hasNext()) {
            this.remove(object.next());
        }
        return n3 != this.size();
    }

    public boolean n(Collection collection) {
        int n3 = this.size();
        for (int i3 = this.size() - 1; i3 >= 0; --i3) {
            if (collection.contains(this.f(i3))) continue;
            this.h(i3);
        }
        return n3 != this.size();
    }

    public void putAll(Map object) {
        this.b(this.size() + object.size());
        for (Map.Entry entry : object.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Object remove(Object object) {
        return super.remove(object);
    }

    public Collection values() {
        e e3;
        e e4 = e3 = this.h;
        if (e3 == null) {
            this.h = e4 = new e(this);
        }
        return e4;
    }

    public final class a
    extends AbstractSet {
        public final a c;

        public a(a a4) {
            this.c = a4;
        }

        @Override
        public Iterator iterator() {
            return new d(this.c);
        }

        @Override
        public int size() {
            return this.c.size();
        }
    }

    public final class b
    extends i {
        public final a f;

        public b(a a4) {
            this.f = a4;
            super(a4.size());
        }

        @Override
        public Object a(int n3) {
            return this.f.f(n3);
        }

        @Override
        public void b(int n3) {
            this.f.h(n3);
        }
    }

    public final class c
    implements Set {
        public final a c;

        public c(a a4) {
            this.c = a4;
        }

        @Override
        public boolean add(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            this.c.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.c.containsKey(object);
        }

        @Override
        public boolean containsAll(Collection collection) {
            return this.c.k(collection);
        }

        @Override
        public boolean equals(Object object) {
            return a.l(this, object);
        }

        @Override
        public int hashCode() {
            int n3 = 0;
            for (int i3 = this.c.size() - 1; i3 >= 0; --i3) {
                Object object = this.c.f(i3);
                int n4 = object == null ? 0 : object.hashCode();
                n3 += n4;
            }
            return n3;
        }

        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        @Override
        public Iterator iterator() {
            return new b(this.c);
        }

        @Override
        public boolean remove(Object object) {
            int n3 = this.c.d(object);
            if (n3 >= 0) {
                this.c.h(n3);
                return true;
            }
            return false;
        }

        @Override
        public boolean removeAll(Collection collection) {
            return this.c.m(collection);
        }

        @Override
        public boolean retainAll(Collection collection) {
            return this.c.n(collection);
        }

        @Override
        public int size() {
            return this.c.size();
        }

        @Override
        public Object[] toArray() {
            int n3 = this.c.size();
            Object[] objectArray = new Object[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                objectArray[i3] = this.c.f(i3);
            }
            return objectArray;
        }

        @Override
        public Object[] toArray(Object[] objectArray) {
            int n3 = this.size();
            Object[] objectArray2 = objectArray;
            if (objectArray.length < n3) {
                objectArray2 = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n3);
            }
            for (int i3 = 0; i3 < n3; ++i3) {
                objectArray2[i3] = this.c.f(i3);
            }
            if (objectArray2.length > n3) {
                objectArray2[n3] = null;
            }
            return objectArray2;
        }
    }

    public final class d
    implements Iterator,
    Map.Entry {
        public int c;
        public int d;
        public boolean e;
        public final a f;

        public d(a a4) {
            this.f = a4;
            this.c = a4.size() - 1;
            this.d = -1;
        }

        public Map.Entry a() {
            if (this.hasNext()) {
                ++this.d;
                this.e = true;
                return this;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean equals(Object object) {
            if (this.e) {
                if (!(object instanceof Map.Entry)) {
                    return false;
                }
                return p.a.c((object = (Map.Entry)object).getKey(), this.f.f(this.d)) && p.a.c(object.getValue(), this.f.j(this.d));
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public Object getKey() {
            if (this.e) {
                return this.f.f(this.d);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public Object getValue() {
            if (this.e) {
                return this.f.j(this.d);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override
        public boolean hasNext() {
            return this.d < this.c;
        }

        @Override
        public int hashCode() {
            if (this.e) {
                Object object = this.f.f(this.d);
                Object object2 = this.f.j(this.d);
                int n3 = 0;
                int n4 = object == null ? 0 : object.hashCode();
                if (object2 != null) {
                    n3 = object2.hashCode();
                }
                return n4 ^ n3;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override
        public void remove() {
            if (this.e) {
                this.f.h(this.d);
                --this.d;
                --this.c;
                this.e = false;
                return;
            }
            throw new IllegalStateException();
        }

        public Object setValue(Object object) {
            if (this.e) {
                return this.f.i(this.d, object);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getKey());
            stringBuilder.append("=");
            stringBuilder.append(this.getValue());
            return stringBuilder.toString();
        }
    }

    public final class e
    implements Collection {
        public final a c;

        public e(a a4) {
            this.c = a4;
        }

        public boolean add(Object object) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            this.c.clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.c.a(object) >= 0;
        }

        public boolean containsAll(Collection object) {
            object = object.iterator();
            while (object.hasNext()) {
                if (this.contains(object.next())) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        @Override
        public Iterator iterator() {
            return new f(this.c);
        }

        @Override
        public boolean remove(Object object) {
            int n3 = this.c.a(object);
            if (n3 >= 0) {
                this.c.h(n3);
                return true;
            }
            return false;
        }

        public boolean removeAll(Collection collection) {
            int n3 = this.c.size();
            int n4 = 0;
            boolean bl = false;
            while (n4 < n3) {
                int n5 = n3;
                int n6 = n4;
                if (collection.contains(this.c.j(n4))) {
                    this.c.h(n4);
                    n6 = n4 - 1;
                    n5 = n3 - 1;
                    bl = true;
                }
                n4 = n6 + 1;
                n3 = n5;
            }
            return bl;
        }

        public boolean retainAll(Collection collection) {
            int n3 = this.c.size();
            int n4 = 0;
            boolean bl = false;
            while (n4 < n3) {
                int n5 = n3;
                int n6 = n4;
                if (!collection.contains(this.c.j(n4))) {
                    this.c.h(n4);
                    n6 = n4 - 1;
                    n5 = n3 - 1;
                    bl = true;
                }
                n4 = n6 + 1;
                n3 = n5;
            }
            return bl;
        }

        @Override
        public int size() {
            return this.c.size();
        }

        @Override
        public Object[] toArray() {
            int n3 = this.c.size();
            Object[] objectArray = new Object[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                objectArray[i3] = this.c.j(i3);
            }
            return objectArray;
        }

        public Object[] toArray(Object[] objectArray) {
            int n3 = this.size();
            Object[] objectArray2 = objectArray;
            if (objectArray.length < n3) {
                objectArray2 = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n3);
            }
            for (int i3 = 0; i3 < n3; ++i3) {
                objectArray2[i3] = this.c.j(i3);
            }
            if (objectArray2.length > n3) {
                objectArray2[n3] = null;
            }
            return objectArray2;
        }
    }

    public final class f
    extends i {
        public final a f;

        public f(a a4) {
            this.f = a4;
            super(a4.size());
        }

        @Override
        public Object a(int n3) {
            return this.f.j(n3);
        }

        @Override
        public void b(int n3) {
            this.f.h(n3);
        }
    }
}

