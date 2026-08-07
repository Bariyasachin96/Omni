/*
 * Decompiled with CFR 0.152.
 */
package o;

import e3.h;
import java.util.Arrays;
import o.t;
import o3.g;
import o3.k;
import p.a;

public class s
implements Cloneable {
    public boolean c;
    public int[] d;
    public Object[] e;
    public int f;

    public s() {
        this(0, 1, null);
    }

    public s(int n3) {
        if (n3 == 0) {
            this.d = a.a;
            this.e = a.c;
            return;
        }
        n3 = a.e(n3);
        this.d = new int[n3];
        this.e = new Object[n3];
    }

    public /* synthetic */ s(int n3, int n4, g g3) {
        if ((n4 & 1) != 0) {
            n3 = 10;
        }
        this(n3);
    }

    public void a(int n3, Object object) {
        int n4 = this.f;
        if (n4 != 0 && n3 <= this.d[n4 - 1]) {
            this.g(n3, object);
            return;
        }
        if (this.c && n4 >= this.d.length) {
            t.a(this);
        }
        if ((n4 = this.f) >= this.d.length) {
            int n5 = a.e(n4 + 1);
            Object[] objectArray = Arrays.copyOf(this.d, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.d = objectArray;
            objectArray = Arrays.copyOf(this.e, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.e = objectArray;
        }
        this.d[n4] = n3;
        this.e[n4] = object;
        this.f = n4 + 1;
    }

    public void b() {
        int n3 = this.f;
        Object[] objectArray = this.e;
        for (int i3 = 0; i3 < n3; ++i3) {
            objectArray[i3] = null;
        }
        this.f = 0;
        this.c = false;
    }

    public s c() {
        Object object = super.clone();
        k.c(object, "null cannot be cast to non-null type androidx.collection.SparseArrayCompat<E of androidx.collection.SparseArrayCompat>");
        object = (s)object;
        ((s)object).d = (int[])this.d.clone();
        ((s)object).e = (Object[])this.e.clone();
        return object;
    }

    public Object d(int n3) {
        return t.c(this, n3);
    }

    public int e(Object object) {
        if (this.c) {
            t.a(this);
        }
        int n3 = this.f;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (this.e[i3] != object) continue;
            return i3;
        }
        return -1;
    }

    public int f(int n3) {
        if (this.c) {
            t.a(this);
        }
        return this.d[n3];
    }

    public void g(int n3, Object object) {
        Object[] objectArray;
        int n4 = a.a(this.d, this.f, n3);
        if (n4 >= 0) {
            this.e[n4] = object;
            return;
        }
        int n5 = ~n4;
        if (n5 < this.f && this.e[n5] == t.b()) {
            this.d[n5] = n3;
            this.e[n5] = object;
            return;
        }
        n4 = n5;
        if (this.c) {
            n4 = n5;
            if (this.f >= this.d.length) {
                t.a(this);
                n4 = ~a.a(this.d, this.f, n3);
            }
        }
        if ((n5 = this.f) >= this.d.length) {
            n5 = a.e(n5 + 1);
            objectArray = Arrays.copyOf(this.d, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.d = objectArray;
            objectArray = Arrays.copyOf(this.e, n5);
            k.d(objectArray, "copyOf(this, newSize)");
            this.e = objectArray;
        }
        if ((n5 = this.f) - n4 != 0) {
            objectArray = this.d;
            int n6 = n4 + 1;
            h.e(objectArray, objectArray, n6, n4, n5);
            objectArray = this.e;
            h.g(objectArray, objectArray, n6, n4, this.f);
        }
        this.d[n4] = n3;
        this.e[n4] = object;
        ++this.f;
    }

    public int h() {
        if (this.c) {
            t.a(this);
        }
        return this.f;
    }

    public Object i(int n3) {
        if (this.c) {
            t.a(this);
        }
        return this.e[n3];
    }

    public String toString() {
        if (this.h() <= 0) {
            return "{}";
        }
        CharSequence charSequence = new StringBuilder(this.f * 28);
        ((StringBuilder)charSequence).append('{');
        int n3 = this.f;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (i3 > 0) {
                ((StringBuilder)charSequence).append(", ");
            }
            ((StringBuilder)charSequence).append(this.f(i3));
            ((StringBuilder)charSequence).append('=');
            Object object = this.i(i3);
            if (object != this) {
                ((StringBuilder)charSequence).append(object);
                continue;
            }
            ((StringBuilder)charSequence).append("(this Map)");
        }
        ((StringBuilder)charSequence).append('}');
        charSequence = ((StringBuilder)charSequence).toString();
        k.d(charSequence, "buffer.toString()");
        return charSequence;
    }
}

