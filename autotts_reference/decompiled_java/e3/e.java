/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.b;
import e3.c;
import e3.f;
import e3.h;
import e3.i;
import e3.l;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import o3.g;
import o3.k;

public final class e
extends c {
    public static final a f = new a(null);
    public static final Object[] g = new Object[0];
    public int c;
    public Object[] d = g;
    public int e;

    @Override
    public int a() {
        return this.e;
    }

    public void add(int n3, Object object) {
        b.c.b(n3, this.size());
        if (n3 == this.size()) {
            this.addLast(object);
            return;
        }
        if (n3 == 0) {
            this.addFirst(object);
            return;
        }
        this.f(this.size() + 1);
        int n4 = this.i(this.c + n3);
        if (n3 < this.size() + 1 >> 1) {
            n4 = this.e(n4);
            n3 = this.e(this.c);
            int n5 = this.c;
            if (n4 >= n5) {
                Object[] objectArray = this.d;
                objectArray[n3] = objectArray[n5];
                h.g(objectArray, objectArray, n5, n5 + 1, n4 + 1);
            } else {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, n5 - 1, n5, objectArray.length);
                objectArray = this.d;
                objectArray[objectArray.length - 1] = objectArray[0];
                h.g(objectArray, objectArray, 0, 1, n4 + 1);
            }
            this.d[n4] = object;
            this.c = n3;
        } else {
            n3 = this.i(this.c + this.size());
            if (n4 < n3) {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, n4 + 1, n4, n3);
            } else {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, 1, 0, n3);
                objectArray = this.d;
                objectArray[0] = objectArray[objectArray.length - 1];
                h.g(objectArray, objectArray, n4 + 1, n4, objectArray.length - 1);
            }
            this.d[n4] = object;
        }
        this.e = this.size() + 1;
    }

    @Override
    public boolean add(Object object) {
        this.addLast(object);
        return true;
    }

    public boolean addAll(int n3, Collection collection) {
        k.e(collection, "elements");
        b.c.b(n3, this.size());
        if (collection.isEmpty()) {
            return false;
        }
        if (n3 == this.size()) {
            return this.addAll(collection);
        }
        this.f(this.size() + collection.size());
        int n4 = this.i(this.c + this.size());
        int n5 = this.i(this.c + n3);
        int n6 = collection.size();
        if (n3 < this.size() + 1 >> 1) {
            n4 = this.c;
            n3 = n4 - n6;
            if (n5 >= n4) {
                if (n3 >= 0) {
                    Object[] objectArray = this.d;
                    h.g(objectArray, objectArray, n3, n4, n5);
                } else {
                    Object[] objectArray = this.d;
                    int n7 = objectArray.length - (n3 += objectArray.length);
                    if (n7 >= n5 - n4) {
                        h.g(objectArray, objectArray, n3, n4, n5);
                    } else {
                        h.g(objectArray, objectArray, n3, n4, n4 + n7);
                        objectArray = this.d;
                        h.g(objectArray, objectArray, 0, this.c + n7, n5);
                    }
                }
            } else {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, n3, n4, objectArray.length);
                if (n6 >= n5) {
                    objectArray = this.d;
                    h.g(objectArray, objectArray, objectArray.length - n6, 0, n5);
                } else {
                    objectArray = this.d;
                    h.g(objectArray, objectArray, objectArray.length - n6, 0, n6);
                    objectArray = this.d;
                    h.g(objectArray, objectArray, 0, n6, n5);
                }
            }
            this.c = n3;
            this.c(this.h(n5 - n6), collection);
        } else {
            n3 = n5 + n6;
            if (n5 < n4) {
                Object[] objectArray = this.d;
                if ((n6 += n4) <= objectArray.length) {
                    h.g(objectArray, objectArray, n3, n5, n4);
                } else if (n3 >= objectArray.length) {
                    h.g(objectArray, objectArray, n3 - objectArray.length, n5, n4);
                } else {
                    n6 = n4 - (n6 - objectArray.length);
                    h.g(objectArray, objectArray, 0, n6, n4);
                    objectArray = this.d;
                    h.g(objectArray, objectArray, n3, n5, n6);
                }
            } else {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, n6, 0, n4);
                objectArray = this.d;
                if (n3 >= objectArray.length) {
                    h.g(objectArray, objectArray, n3 - objectArray.length, n5, objectArray.length);
                } else {
                    h.g(objectArray, objectArray, 0, objectArray.length - n6, objectArray.length);
                    objectArray = this.d;
                    h.g(objectArray, objectArray, n3, n5, objectArray.length - n6);
                }
            }
            this.c(n5, collection);
        }
        return true;
    }

    @Override
    public boolean addAll(Collection collection) {
        k.e(collection, "elements");
        if (collection.isEmpty()) {
            return false;
        }
        this.f(this.size() + collection.size());
        this.c(this.i(this.c + this.size()), collection);
        return true;
    }

    @Override
    public final void addFirst(Object object) {
        int n3;
        this.f(this.size() + 1);
        this.c = n3 = this.e(this.c);
        this.d[n3] = object;
        this.e = this.size() + 1;
    }

    @Override
    public final void addLast(Object object) {
        this.f(this.size() + 1);
        this.d[this.i((int)(this.c + this.size()))] = object;
        this.e = this.size() + 1;
    }

    @Override
    public Object b(int n3) {
        b.c.a(n3, this.size());
        if (n3 == l.g(this)) {
            return this.removeLast();
        }
        if (n3 == 0) {
            return this.removeFirst();
        }
        int n4 = this.i(this.c + n3);
        Object object = this.d[n4];
        if (n3 < this.size() >> 1) {
            Object[] objectArray;
            n3 = this.c;
            if (n4 >= n3) {
                objectArray = this.d;
                h.g(objectArray, objectArray, n3 + 1, n3, n4);
            } else {
                objectArray = this.d;
                h.g(objectArray, objectArray, 1, 0, n4);
                objectArray = this.d;
                objectArray[0] = objectArray[objectArray.length - 1];
                n3 = this.c;
                h.g(objectArray, objectArray, n3 + 1, n3, objectArray.length - 1);
            }
            objectArray = this.d;
            n3 = this.c;
            objectArray[n3] = null;
            this.c = this.g(n3);
        } else {
            n3 = this.i(this.c + l.g(this));
            if (n4 <= n3) {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, n4, n4 + 1, n3 + 1);
            } else {
                Object[] objectArray = this.d;
                h.g(objectArray, objectArray, n4, n4 + 1, objectArray.length);
                objectArray = this.d;
                objectArray[objectArray.length - 1] = objectArray[0];
                h.g(objectArray, objectArray, 0, 1, n3 + 1);
            }
            this.d[n3] = null;
        }
        this.e = this.size() - 1;
        return object;
    }

    public final void c(int n3, Collection collection) {
        Iterator iterator = collection.iterator();
        int n4 = this.d.length;
        while (n3 < n4 && iterator.hasNext()) {
            this.d[n3] = iterator.next();
            ++n3;
        }
        n4 = this.c;
        for (n3 = 0; n3 < n4 && iterator.hasNext(); ++n3) {
            this.d[n3] = iterator.next();
        }
        this.e = this.size() + collection.size();
    }

    @Override
    public void clear() {
        int n3 = this.c;
        int n4 = this.i(this.c + this.size());
        if (n3 < n4) {
            h.m(this.d, null, n3, n4);
        } else if (!this.isEmpty()) {
            Object[] objectArray = this.d;
            h.m(objectArray, null, this.c, objectArray.length);
            h.m(this.d, null, 0, n4);
        }
        this.c = 0;
        this.e = 0;
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) != -1;
    }

    public final void d(int n3) {
        Object[] objectArray = new Object[n3];
        Object[] objectArray2 = this.d;
        h.g(objectArray2, objectArray, 0, this.c, objectArray2.length);
        objectArray2 = this.d;
        n3 = objectArray2.length;
        int n4 = this.c;
        h.g(objectArray2, objectArray, n3 - n4, 0, n4);
        this.c = 0;
        this.d = objectArray;
    }

    public final int e(int n3) {
        if (n3 == 0) {
            return i.r(this.d);
        }
        return n3 - 1;
    }

    public final void f(int n3) {
        if (n3 >= 0) {
            Object[] objectArray = this.d;
            if (n3 <= objectArray.length) {
                return;
            }
            if (objectArray == g) {
                this.d = new Object[r3.e.a(n3, 10)];
                return;
            }
            this.d(f.a(objectArray.length, n3));
            return;
        }
        throw new IllegalStateException("Deque is too big.");
    }

    public final int g(int n3) {
        if (n3 == i.r(this.d)) {
            return 0;
        }
        return n3 + 1;
    }

    public Object get(int n3) {
        b.c.a(n3, this.size());
        return this.d[this.i(this.c + n3)];
    }

    public final int h(int n3) {
        int n4 = n3;
        if (n3 < 0) {
            n4 = n3 + this.d.length;
        }
        return n4;
    }

    public final int i(int n3) {
        Object[] objectArray = this.d;
        int n4 = n3;
        if (n3 >= objectArray.length) {
            n4 = n3 - objectArray.length;
        }
        return n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int indexOf(Object object) {
        int n3 = this.c;
        int n4 = this.i(this.c + this.size());
        if (n3 < n4) {
            while (n3 < n4) {
                if (k.a(object, this.d[n3])) {
                    n4 = this.c;
                    return n3 - n4;
                }
                ++n3;
            }
            return -1;
        }
        if (n3 < n4) return -1;
        int n5 = this.d.length;
        while (n3 < n5) {
            if (k.a(object, this.d[n3])) {
                n4 = this.c;
                return n3 - n4;
            }
            ++n3;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            if (!k.a(object, this.d[n3])) continue;
            n3 += this.d.length;
            n4 = this.c;
            return n3 - n4;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int lastIndexOf(Object object) {
        int n3 = this.c;
        int n4 = this.i(this.c + this.size());
        if (n3 < n4) {
            if (n3 > --n4) return -1;
            while (true) {
                if (k.a(object, this.d[n4])) {
                    n3 = this.c;
                    return n4 - n3;
                }
                if (n4 == n3) return -1;
                --n4;
            }
        }
        if (n3 <= n4) return -1;
        --n4;
        while (-1 < n4) {
            if (k.a(object, this.d[n4])) {
                n4 += this.d.length;
                n3 = this.c;
                return n4 - n3;
            }
            --n4;
        }
        n3 = this.c;
        n4 = i.r(this.d);
        if (n3 > n4) return -1;
        while (true) {
            if (k.a(object, this.d[n4])) {
                n3 = this.c;
                return n4 - n3;
            }
            if (n4 == n3) return -1;
            --n4;
        }
    }

    @Override
    public boolean remove(Object object) {
        int n3 = this.indexOf(object);
        if (n3 == -1) {
            return false;
        }
        this.remove(n3);
        return true;
    }

    @Override
    public boolean removeAll(Collection collection) {
        k.e(collection, "elements");
        boolean bl = this.isEmpty();
        int n3 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (!bl) {
            if (this.d.length == 0) {
                bl4 = bl2;
            } else {
                int n4 = this.c;
                int n5 = this.i(this.c + this.size());
                if (n4 < n5) {
                    int n6 = n4;
                    bl2 = bl3;
                    while (n4 < n5) {
                        Object object = this.d[n4];
                        if (!collection.contains(object)) {
                            this.d[n6] = object;
                            ++n6;
                        } else {
                            bl2 = true;
                        }
                        ++n4;
                    }
                    h.m(this.d, null, n6, n5);
                    n4 = n6;
                } else {
                    Object[] objectArray;
                    Object object;
                    int n7 = this.d.length;
                    bl2 = false;
                    int n8 = n4;
                    while (n4 < n7) {
                        object = this.d;
                        objectArray = object[n4];
                        object[n4] = null;
                        if (!collection.contains(objectArray)) {
                            this.d[n8] = objectArray;
                            ++n8;
                        } else {
                            bl2 = true;
                        }
                        ++n4;
                    }
                    n4 = this.i(n8);
                    for (n8 = n3; n8 < n5; ++n8) {
                        objectArray = this.d;
                        object = objectArray[n8];
                        objectArray[n8] = null;
                        if (!collection.contains(object)) {
                            this.d[n4] = object;
                            n4 = this.g(n4);
                            continue;
                        }
                        bl2 = true;
                    }
                }
                bl4 = bl2;
                if (bl2) {
                    this.e = this.h(n4 - this.c);
                    bl4 = bl2;
                }
            }
        }
        return bl4;
    }

    @Override
    public final Object removeFirst() {
        if (!this.isEmpty()) {
            Object[] objectArray = this.d;
            int n3 = this.c;
            Object object = objectArray[n3];
            objectArray[n3] = null;
            this.c = this.g(n3);
            this.e = this.size() - 1;
            return object;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Override
    public final Object removeLast() {
        if (!this.isEmpty()) {
            int n3 = this.i(this.c + l.g(this));
            Object[] objectArray = this.d;
            Object object = objectArray[n3];
            objectArray[n3] = null;
            this.e = this.size() - 1;
            return object;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    @Override
    public boolean retainAll(Collection collection) {
        k.e(collection, "elements");
        boolean bl = this.isEmpty();
        int n3 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        if (!bl) {
            if (this.d.length == 0) {
                bl4 = bl2;
            } else {
                int n4 = this.c;
                int n5 = this.i(this.c + this.size());
                if (n4 < n5) {
                    int n6 = n4;
                    while (n4 < n5) {
                        Object object = this.d[n4];
                        if (collection.contains(object)) {
                            this.d[n6] = object;
                            ++n6;
                        } else {
                            bl3 = true;
                        }
                        ++n4;
                    }
                    h.m(this.d, null, n6, n5);
                    n4 = n6;
                } else {
                    Object object;
                    Object[] objectArray;
                    int n7 = this.d.length;
                    bl3 = false;
                    int n8 = n4;
                    while (n4 < n7) {
                        objectArray = this.d;
                        object = objectArray[n4];
                        objectArray[n4] = null;
                        if (collection.contains(object)) {
                            this.d[n8] = object;
                            ++n8;
                        } else {
                            bl3 = true;
                        }
                        ++n4;
                    }
                    n4 = this.i(n8);
                    for (n8 = n3; n8 < n5; ++n8) {
                        objectArray = this.d;
                        object = objectArray[n8];
                        objectArray[n8] = null;
                        if (collection.contains(object)) {
                            this.d[n4] = object;
                            n4 = this.g(n4);
                            continue;
                        }
                        bl3 = true;
                    }
                }
                bl4 = bl3;
                if (bl3) {
                    this.e = this.h(n4 - this.c);
                    bl4 = bl3;
                }
            }
        }
        return bl4;
    }

    public Object set(int n3, Object object) {
        b.c.a(n3, this.size());
        n3 = this.i(this.c + n3);
        Object[] objectArray = this.d;
        Object object2 = objectArray[n3];
        objectArray[n3] = object;
        return object2;
    }

    @Override
    public Object[] toArray() {
        return this.toArray(new Object[this.size()]);
    }

    @Override
    public Object[] toArray(Object[] objectArray) {
        k.e(objectArray, "array");
        if (objectArray.length < this.size()) {
            objectArray = e3.f.a(objectArray, this.size());
        }
        int n3 = this.i(this.c + this.size());
        int n4 = this.c;
        if (n4 < n3) {
            h.j(this.d, objectArray, 0, n4, n3, 2, null);
        } else if (!this.isEmpty()) {
            Object[] objectArray2 = this.d;
            h.g(objectArray2, objectArray, 0, this.c, objectArray2.length);
            objectArray2 = this.d;
            h.g(objectArray2, objectArray, objectArray2.length - this.c, 0, n3);
        }
        if (objectArray.length > this.size()) {
            objectArray[this.size()] = null;
        }
        return objectArray;
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final int a(int n3, int n4) {
            int n5;
            n3 = n5 = n3 + (n3 >> 1);
            if (n5 - n4 < 0) {
                n3 = n4;
            }
            if (n3 - 0x7FFFFFF7 > 0) {
                if (n4 > 0x7FFFFFF7) {
                    return Integer.MAX_VALUE;
                }
                return 0x7FFFFFF7;
            }
            return n3;
        }
    }
}

