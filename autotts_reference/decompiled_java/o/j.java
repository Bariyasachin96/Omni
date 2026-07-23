/*
 * Decompiled with CFR 0.152.
 */
package o;

import e3.h;
import java.util.Arrays;
import o3.g;
import o3.k;
import p.a;

public class j
implements Cloneable {
    public boolean c;
    public long[] d;
    public Object[] e;
    public int f;

    public j() {
        this(0, 1, null);
    }

    public j(int n3) {
        if (n3 == 0) {
            this.d = a.b;
            this.e = a.c;
            return;
        }
        n3 = a.f(n3);
        this.d = new long[n3];
        this.e = new Object[n3];
    }

    public /* synthetic */ j(int n3, int n4, g g3) {
        if ((n4 & 1) != 0) {
            n3 = 10;
        }
        this(n3);
    }

    public void a() {
        int n3 = this.f;
        Object[] objectArray = this.e;
        for (int i3 = 0; i3 < n3; ++i3) {
            objectArray[i3] = null;
        }
        this.f = 0;
        this.c = false;
    }

    public j b() {
        Object object = super.clone();
        k.c(object, "null cannot be cast to non-null type androidx.collection.LongSparseArray<E of androidx.collection.LongSparseArray>");
        object = (j)object;
        ((j)object).d = (long[])this.d.clone();
        ((j)object).e = (Object[])this.e.clone();
        return object;
    }

    public boolean c(long l3) {
        return this.e(l3) >= 0;
    }

    public Object d(long l3) {
        int n3 = a.b(this.d, this.f, l3);
        if (n3 >= 0 && this.e[n3] != o.k.a()) {
            return this.e[n3];
        }
        return null;
    }

    public int e(long l3) {
        if (this.c) {
            int n3 = this.f;
            long[] lArray = this.d;
            Object[] objectArray = this.e;
            int n4 = 0;
            for (int i3 = 0; i3 < n3; ++i3) {
                Object object = objectArray[i3];
                int n5 = n4;
                if (object != o.k.a()) {
                    if (i3 != n4) {
                        lArray[n4] = lArray[i3];
                        objectArray[n4] = object;
                        objectArray[i3] = null;
                    }
                    n5 = n4 + 1;
                }
                n4 = n5;
            }
            this.c = false;
            this.f = n4;
        }
        return a.b(this.d, this.f, l3);
    }

    public boolean f() {
        return this.k() == 0;
    }

    public long g(int n3) {
        int n4;
        if (n3 >= 0 && n3 < (n4 = this.f)) {
            if (this.c) {
                long[] lArray = this.d;
                Object[] objectArray = this.e;
                int n5 = 0;
                for (int i3 = 0; i3 < n4; ++i3) {
                    Object object = objectArray[i3];
                    int n6 = n5;
                    if (object != o.k.a()) {
                        if (i3 != n5) {
                            lArray[n5] = lArray[i3];
                            objectArray[n5] = object;
                            objectArray[i3] = null;
                        }
                        n6 = n5 + 1;
                    }
                    n5 = n6;
                }
                this.c = false;
                this.f = n5;
            }
            return this.d[n3];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected index to be within 0..size()-1, but was ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    public void h(long l3, Object object) {
        int n3;
        Object[] objectArray;
        int n4 = a.b(this.d, this.f, l3);
        if (n4 >= 0) {
            this.e[n4] = object;
            return;
        }
        int n5 = ~n4;
        if (n5 < this.f && this.e[n5] == o.k.a()) {
            this.d[n5] = l3;
            this.e[n5] = object;
            return;
        }
        n4 = n5;
        if (this.c) {
            int n6 = this.f;
            long[] lArray = this.d;
            n4 = n5;
            if (n6 >= lArray.length) {
                objectArray = this.e;
                n5 = 0;
                for (n4 = 0; n4 < n6; ++n4) {
                    Object object2 = objectArray[n4];
                    n3 = n5;
                    if (object2 != o.k.a()) {
                        if (n4 != n5) {
                            lArray[n5] = lArray[n4];
                            objectArray[n5] = object2;
                            objectArray[n4] = null;
                        }
                        n3 = n5 + 1;
                    }
                    n5 = n3;
                }
                this.c = false;
                this.f = n5;
                n4 = ~a.b(this.d, n5, l3);
            }
        }
        if ((n5 = this.f) >= this.d.length) {
            n5 = a.f(n5 + 1);
            objectArray = Arrays.copyOf(this.d, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.d = (long[])objectArray;
            objectArray = Arrays.copyOf(this.e, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.e = objectArray;
        }
        if ((n3 = this.f) - n4 != 0) {
            objectArray = this.d;
            n5 = n4 + 1;
            h.f((long[])objectArray, (long[])objectArray, n5, n4, n3);
            objectArray = this.e;
            h.g(objectArray, objectArray, n5, n4, this.f);
        }
        this.d[n4] = l3;
        this.e[n4] = object;
        ++this.f;
    }

    public void i(long l3) {
        int n3 = a.b(this.d, this.f, l3);
        if (n3 >= 0 && this.e[n3] != o.k.a()) {
            this.e[n3] = o.k.a();
            this.c = true;
        }
    }

    public void j(int n3) {
        if (this.e[n3] != o.k.a()) {
            this.e[n3] = o.k.a();
            this.c = true;
        }
    }

    public int k() {
        if (this.c) {
            int n3 = this.f;
            long[] lArray = this.d;
            Object[] objectArray = this.e;
            int n4 = 0;
            for (int i3 = 0; i3 < n3; ++i3) {
                Object object = objectArray[i3];
                int n5 = n4;
                if (object != o.k.a()) {
                    if (i3 != n4) {
                        lArray[n4] = lArray[i3];
                        objectArray[n4] = object;
                        objectArray[i3] = null;
                    }
                    n5 = n4 + 1;
                }
                n4 = n5;
            }
            this.c = false;
            this.f = n4;
        }
        return this.f;
    }

    public Object l(int n3) {
        int n4;
        if (n3 >= 0 && n3 < (n4 = this.f)) {
            if (this.c) {
                long[] lArray = this.d;
                Object[] objectArray = this.e;
                int n5 = 0;
                for (int i3 = 0; i3 < n4; ++i3) {
                    Object object = objectArray[i3];
                    int n6 = n5;
                    if (object != o.k.a()) {
                        if (i3 != n5) {
                            lArray[n5] = lArray[i3];
                            objectArray[n5] = object;
                            objectArray[i3] = null;
                        }
                        n6 = n5 + 1;
                    }
                    n5 = n6;
                }
                this.c = false;
                this.f = n5;
            }
            return this.e[n3];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected index to be within 0..size()-1, but was ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString().toString());
    }

    public String toString() {
        Object object;
        if (this.k() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.f * 28);
        stringBuilder.append('{');
        int n3 = this.f;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (i3 > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.g(i3));
            stringBuilder.append('=');
            object = this.l(i3);
            if (object != stringBuilder) {
                stringBuilder.append(object);
                continue;
            }
            stringBuilder.append("(this Map)");
        }
        stringBuilder.append('}');
        object = stringBuilder.toString();
        k.d(object, "StringBuilder(capacity).…builderAction).toString()");
        return object;
    }
}

