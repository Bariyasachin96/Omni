/*
 * Decompiled with CFR 0.152.
 */
package o;

import e3.h;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Map;
import o3.g;
import o3.k;
import p.a;

public class r {
    public int[] c;
    public Object[] d;
    public int e;

    public r() {
        this(0, 1, null);
    }

    public r(int n3) {
        Object[] objectArray = n3 == 0 ? a.a : new int[n3];
        this.c = objectArray;
        objectArray = n3 == 0 ? (Object[])a.c : (Object[])new Object[n3 << 1];
        this.d = objectArray;
    }

    public /* synthetic */ r(int n3, int n4, g g3) {
        if ((n4 & 1) != 0) {
            n3 = 0;
        }
        this(n3);
    }

    public r(r r3) {
        this(0, 1, null);
        if (r3 != null) {
            this.g(r3);
        }
    }

    public final int a(Object object) {
        int n3 = this.e * 2;
        Object[] objectArray = this.d;
        if (object == null) {
            for (int i3 = 1; i3 < n3; i3 += 2) {
                if (objectArray[i3] != null) continue;
                return i3 >> 1;
            }
        } else {
            for (int i4 = 1; i4 < n3; i4 += 2) {
                if (!k.a(object, objectArray[i4])) continue;
                return i4 >> 1;
            }
        }
        return -1;
    }

    public void b(int n3) {
        int n4 = this.e;
        Object[] objectArray = this.c;
        if (objectArray.length < n3) {
            objectArray = Arrays.copyOf(objectArray, n3);
            k.d(objectArray, "copyOf(this, newSize)");
            this.c = objectArray;
            objectArray = Arrays.copyOf(this.d, n3 * 2);
            k.d(objectArray, "copyOf(this, newSize)");
            this.d = objectArray;
        }
        if (this.e == n4) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    public final int c(Object object, int n3) {
        int n4;
        int n5 = this.e;
        if (n5 == 0) {
            return -1;
        }
        int n6 = a.a(this.c, n5, n3);
        if (n6 < 0 || k.a(object, this.d[n6 << 1])) {
            return n6;
        }
        for (n4 = n6 + 1; n4 < n5 && this.c[n4] == n3; ++n4) {
            if (!k.a(object, this.d[n4 << 1])) continue;
            return n4;
        }
        for (n5 = n6 - 1; n5 >= 0 && this.c[n5] == n3; --n5) {
            if (!k.a(object, this.d[n5 << 1])) continue;
            return n5;
        }
        return ~n4;
    }

    public void clear() {
        if (this.e > 0) {
            this.c = a.a;
            this.d = a.c;
            this.e = 0;
        }
        if (this.e <= 0) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    public boolean containsKey(Object object) {
        return this.d(object) >= 0;
    }

    public boolean containsValue(Object object) {
        return this.a(object) >= 0;
    }

    public int d(Object object) {
        if (object == null) {
            return this.e();
        }
        return this.c(object, object.hashCode());
    }

    public final int e() {
        int n3;
        int n4 = this.e;
        if (n4 == 0) {
            return -1;
        }
        int n5 = a.a(this.c, n4, 0);
        if (n5 < 0 || this.d[n5 << 1] == null) {
            return n5;
        }
        for (n3 = n5 + 1; n3 < n4 && this.c[n3] == 0; ++n3) {
            if (this.d[n3 << 1] != null) continue;
            return n3;
        }
        for (n4 = n5 - 1; n4 >= 0 && this.c[n4] == 0; --n4) {
            if (this.d[n4 << 1] != null) continue;
            return n4;
        }
        return ~n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        block18: {
            int n3;
            block19: {
                block15: {
                    block16: {
                        if (this == object) {
                            return true;
                        }
                        if (!(object instanceof r)) break block15;
                        if (this.size() == ((r)object).size()) break block16;
                        return false;
                    }
                    r r3 = (r)object;
                    int n4 = this.e;
                    for (int i3 = 0; i3 < n4; ++i3) {
                        Object object2;
                        block17: {
                            Object object3 = this.f(i3);
                            object2 = this.j(i3);
                            object = r3.get(object3);
                            if (object2 != null) break block17;
                            if (object != null) return false;
                            if (r3.containsKey(object3)) continue;
                            return false;
                        }
                        if (k.a(object2, object)) continue;
                        return false;
                    }
                    return true;
                }
                if (!(object instanceof Map)) break block18;
                if (this.size() == ((Map)object).size()) break block19;
                return false;
            }
            try {
                n3 = this.e;
            }
            catch (ClassCastException | NullPointerException runtimeException) {
                return false;
            }
            for (int i4 = 0; i4 < n3; ++i4) {
                Object v3;
                Object object4;
                block20: {
                    Object object5 = this.f(i4);
                    object4 = this.j(i4);
                    v3 = ((Map)object).get(object5);
                    if (object4 != null) break block20;
                    if (v3 != null) return false;
                    if (((Map)object).containsKey(object5)) continue;
                    return false;
                }
                boolean bl = k.a(object4, v3);
                if (bl) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public Object f(int n3) {
        if (n3 >= 0 && n3 < this.e) {
            return this.d[n3 << 1];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected index to be within 0..size()-1, but was ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    public void g(r r3) {
        k.e(r3, "map");
        int n3 = r3.e;
        this.b(this.e + n3);
        int n4 = this.e;
        if (n4 == 0) {
            if (n3 > 0) {
                h.e(r3.c, this.c, 0, 0, n3);
                h.g(r3.d, this.d, 0, 0, n3 << 1);
                this.e = n3;
                return;
            }
        } else {
            for (int i3 = 0; i3 < n3; ++i3) {
                this.put(r3.f(i3), r3.j(i3));
            }
        }
    }

    public Object get(Object object) {
        int n3 = this.d(object);
        if (n3 >= 0) {
            return this.d[(n3 << 1) + 1];
        }
        return null;
    }

    public Object getOrDefault(Object object, Object object2) {
        int n3 = this.d(object);
        if (n3 >= 0) {
            return this.d[(n3 << 1) + 1];
        }
        return object2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object h(int n3) {
        int n4;
        if (n3 >= 0 && n3 < (n4 = this.e)) {
            Object[] objectArray = this.d;
            int n5 = n3 << 1;
            Object object = objectArray[n5 + 1];
            if (n4 <= 1) {
                this.clear();
                return object;
            }
            int n6 = n4 - 1;
            int[] nArray = this.c;
            int n7 = nArray.length;
            int n8 = 8;
            if (n7 > 8 && n4 < nArray.length / 3) {
                if (n4 > 8) {
                    n8 = n4 + (n4 >> 1);
                }
                int[] nArray2 = Arrays.copyOf(nArray, n8);
                k.d(nArray2, "copyOf(this, newSize)");
                this.c = nArray2;
                Object[] objectArray2 = Arrays.copyOf(this.d, n8 << 1);
                k.d(objectArray2, "copyOf(this, newSize)");
                this.d = objectArray2;
                if (n4 != this.e) throw new ConcurrentModificationException();
                if (n3 > 0) {
                    h.e(nArray, this.c, 0, 0, n3);
                    h.g(objectArray, this.d, 0, 0, n5);
                }
                if (n3 < n6) {
                    int[] nArray3 = this.c;
                    n8 = n3 + 1;
                    h.e(nArray, nArray3, n3, n8, n4);
                    h.g(objectArray, this.d, n5, n8 << 1, n4 << 1);
                }
            } else {
                if (n3 < n6) {
                    n8 = n3 + 1;
                    h.e(nArray, nArray, n3, n8, n4);
                    objectArray = this.d;
                    h.g(objectArray, objectArray, n5, n8 << 1, n4 << 1);
                }
                objectArray = this.d;
                n3 = n6 << 1;
                objectArray[n3] = null;
                objectArray[n3 + 1] = null;
            }
            if (n4 != this.e) throw new ConcurrentModificationException();
            this.e = n6;
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected index to be within 0..size()-1, but was ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    public int hashCode() {
        int[] nArray = this.c;
        Object[] objectArray = this.d;
        int n3 = this.e;
        int n4 = 1;
        int n5 = 0;
        int n6 = 0;
        while (n5 < n3) {
            Object object = objectArray[n4];
            int n7 = nArray[n5];
            int n8 = object != null ? object.hashCode() : 0;
            n6 += n8 ^ n7;
            ++n5;
            n4 += 2;
        }
        return n6;
    }

    public Object i(int n3, Object object) {
        if (n3 >= 0 && n3 < this.e) {
            n3 = (n3 << 1) + 1;
            Object[] objectArray = this.d;
            Object object2 = objectArray[n3];
            objectArray[n3] = object;
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Expected index to be within 0..size()-1, but was ");
        ((StringBuilder)object).append(n3);
        throw new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public boolean isEmpty() {
        return this.e <= 0;
    }

    public Object j(int n3) {
        if (n3 >= 0 && n3 < this.e) {
            return this.d[(n3 << 1) + 1];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected index to be within 0..size()-1, but was ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    public Object put(Object object, Object object2) {
        int n3 = this.e;
        int n4 = object != null ? object.hashCode() : 0;
        int n5 = object != null ? this.c(object, n4) : this.e();
        if (n5 >= 0) {
            n5 = (n5 << 1) + 1;
            Object[] objectArray = this.d;
            object = objectArray[n5];
            objectArray[n5] = object2;
            return object;
        }
        int n6 = ~n5;
        Object[] objectArray = this.c;
        if (n3 >= objectArray.length) {
            n5 = 8;
            if (n3 >= 8) {
                n5 = (n3 >> 1) + n3;
            } else if (n3 < 4) {
                n5 = 4;
            }
            objectArray = Arrays.copyOf(objectArray, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.c = objectArray;
            objectArray = Arrays.copyOf(this.d, n5 << 1);
            k.d(objectArray, "copyOf(this, newSize)");
            this.d = objectArray;
            if (n3 != this.e) {
                throw new ConcurrentModificationException();
            }
        }
        if (n6 < n3) {
            objectArray = this.c;
            n5 = n6 + 1;
            h.e(objectArray, objectArray, n5, n6, n3);
            objectArray = this.d;
            h.g(objectArray, objectArray, n5 << 1, n6 << 1, this.e << 1);
        }
        if (n3 == (n5 = this.e) && n6 < (objectArray = this.c).length) {
            objectArray[n6] = n4;
            objectArray = this.d;
            n4 = n6 << 1;
            objectArray[n4] = (int)object;
            objectArray[n4 + 1] = (int)object2;
            this.e = n5 + 1;
            return null;
        }
        throw new ConcurrentModificationException();
    }

    public Object putIfAbsent(Object object, Object object2) {
        Object object3 = this.get(object);
        if (object3 == null) {
            return this.put(object, object2);
        }
        return object3;
    }

    public Object remove(Object object) {
        int n3 = this.d(object);
        if (n3 >= 0) {
            return this.h(n3);
        }
        return null;
    }

    public boolean remove(Object object, Object object2) {
        int n3 = this.d(object);
        if (n3 >= 0 && k.a(object2, this.j(n3))) {
            this.h(n3);
            return true;
        }
        return false;
    }

    public Object replace(Object object, Object object2) {
        int n3 = this.d(object);
        if (n3 >= 0) {
            return this.i(n3, object2);
        }
        return null;
    }

    public boolean replace(Object object, Object object2, Object object3) {
        int n3 = this.d(object);
        if (n3 >= 0 && k.a(object2, this.j(n3))) {
            this.i(n3, object3);
            return true;
        }
        return false;
    }

    public int size() {
        return this.e;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        CharSequence charSequence = new StringBuilder(this.e * 28);
        ((StringBuilder)charSequence).append('{');
        int n3 = this.e;
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object;
            if (i3 > 0) {
                ((StringBuilder)charSequence).append(", ");
            }
            if ((object = this.f(i3)) != charSequence) {
                ((StringBuilder)charSequence).append(object);
            } else {
                ((StringBuilder)charSequence).append("(this Map)");
            }
            ((StringBuilder)charSequence).append('=');
            object = this.j(i3);
            if (object != charSequence) {
                ((StringBuilder)charSequence).append(object);
                continue;
            }
            ((StringBuilder)charSequence).append("(this Map)");
        }
        ((StringBuilder)charSequence).append('}');
        charSequence = ((StringBuilder)charSequence).toString();
        k.d(charSequence, "StringBuilder(capacity).…builderAction).toString()");
        return charSequence;
    }
}

