/*
 * Decompiled with CFR 0.152.
 */
package k;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public class b
implements Iterable {
    public c c;
    public c d;
    public final WeakHashMap e = new WeakHashMap();
    public int f = 0;

    public Map.Entry a() {
        return this.c;
    }

    public c b(Object object) {
        c c3 = this.c;
        while (c3 != null && !c3.c.equals(object)) {
            c3 = c3.e;
        }
        return c3;
    }

    public d c() {
        d d3 = new d(this);
        this.e.put(d3, Boolean.FALSE);
        return d3;
    }

    public Map.Entry d() {
        return this.d;
    }

    public Iterator descendingIterator() {
        b b3 = new b(this.d, this.c);
        this.e.put(b3, Boolean.FALSE);
        return b3;
    }

    public c e(Object object, Object object2) {
        object = new c(object, object2);
        ++this.f;
        object2 = this.d;
        if (object2 == null) {
            this.c = object;
            this.d = object;
            return object;
        }
        ((c)object2).e = object;
        ((c)object).f = object2;
        this.d = object;
        return object;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof b)) {
            return false;
        }
        Object object2 = (b)object;
        if (this.size() != ((b)object2).size()) {
            return false;
        }
        object = this.iterator();
        object2 = ((b)object2).iterator();
        while (object.hasNext() && object2.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            Object e3 = object2.next();
            if ((entry != null || e3 == null) && (entry == null || entry.equals(e3))) continue;
            return false;
        }
        return !object.hasNext() && !object2.hasNext();
    }

    public Object f(Object object, Object object2) {
        c c3 = this.b(object);
        if (c3 != null) {
            return c3.d;
        }
        this.e(object, object2);
        return null;
    }

    public Object g(Object object) {
        c c3;
        Object object2;
        if ((object = this.b(object)) == null) {
            return null;
        }
        --this.f;
        if (!this.e.isEmpty()) {
            object2 = this.e.keySet().iterator();
            while (object2.hasNext()) {
                ((f)object2.next()).a((c)object);
            }
        }
        if ((c3 = ((c)object).f) != null) {
            c3.e = ((c)object).e;
        } else {
            this.c = ((c)object).e;
        }
        object2 = ((c)object).e;
        if (object2 != null) {
            ((c)object2).f = c3;
        } else {
            this.d = c3;
        }
        ((c)object).e = null;
        ((c)object).f = null;
        return ((c)object).d;
    }

    public int hashCode() {
        Iterator iterator = this.iterator();
        int n3 = 0;
        while (iterator.hasNext()) {
            n3 += ((Map.Entry)iterator.next()).hashCode();
        }
        return n3;
    }

    public Iterator iterator() {
        a a4 = new a(this.c, this.d);
        this.e.put(a4, Boolean.FALSE);
        return a4;
    }

    public int size() {
        return this.f;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(((Map.Entry)iterator.next()).toString());
            if (!iterator.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static class a
    extends e {
        public a(c c3, c c4) {
            super(c3, c4);
        }

        @Override
        public c b(c c3) {
            return c3.f;
        }

        @Override
        public c c(c c3) {
            return c3.e;
        }
    }

    public static class b
    extends e {
        public b(c c3, c c4) {
            super(c3, c4);
        }

        @Override
        public c b(c c3) {
            return c3.e;
        }

        @Override
        public c c(c c3) {
            return c3.f;
        }
    }

    public static class c
    implements Map.Entry {
        public final Object c;
        public final Object d;
        public c e;
        public c f;

        public c(Object object, Object object2) {
            this.c = object;
            this.d = object2;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof c)) {
                return false;
            }
            object = (c)object;
            return this.c.equals(((c)object).c) && this.d.equals(((c)object).d);
        }

        public Object getKey() {
            return this.c;
        }

        public Object getValue() {
            return this.d;
        }

        @Override
        public int hashCode() {
            return this.c.hashCode() ^ this.d.hashCode();
        }

        public Object setValue(Object object) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.c);
            stringBuilder.append("=");
            stringBuilder.append(this.d);
            return stringBuilder.toString();
        }
    }

    public class d
    extends f
    implements Iterator {
        public c c;
        public boolean d;
        public final b e;

        public d(b b3) {
            this.e = b3;
            this.d = true;
        }

        @Override
        public void a(c c3) {
            c c4 = this.c;
            if (c3 == c4) {
                this.c = c3 = c4.f;
                boolean bl = c3 == null;
                this.d = bl;
            }
        }

        public Map.Entry b() {
            if (this.d) {
                this.d = false;
                this.c = this.e.c;
            } else {
                c c3 = this.c;
                c3 = c3 != null ? c3.e : null;
                this.c = c3;
            }
            return this.c;
        }

        @Override
        public boolean hasNext() {
            if (this.d) {
                return this.e.c != null;
            }
            c c3 = this.c;
            return c3 != null && c3.e != null;
        }
    }

    public static abstract class e
    extends f
    implements Iterator {
        public c c;
        public c d;

        public e(c c3, c c4) {
            this.c = c4;
            this.d = c3;
        }

        @Override
        public void a(c c3) {
            c c4;
            if (this.c == c3 && c3 == this.d) {
                this.d = null;
                this.c = null;
            }
            if ((c4 = this.c) == c3) {
                this.c = this.b(c4);
            }
            if (this.d == c3) {
                this.d = this.e();
            }
        }

        public abstract c b(c var1);

        public abstract c c(c var1);

        public Map.Entry d() {
            c c3 = this.d;
            this.d = this.e();
            return c3;
        }

        public final c e() {
            c c3 = this.d;
            c c4 = this.c;
            if (c3 != c4 && c4 != null) {
                return this.c(c3);
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            return this.d != null;
        }
    }

    public static abstract class f {
        public abstract void a(c var1);
    }
}

