/*
 * Decompiled with CFR 0.152.
 */
package n0;

import n0.e;
import o3.k;

public class f
implements e {
    public final Object[] a;
    public int b;

    public f(int n3) {
        if (n3 > 0) {
            this.a = new Object[n3];
            return;
        }
        throw new IllegalArgumentException("The max pool size must be > 0");
    }

    @Override
    public boolean a(Object object) {
        k.e(object, "instance");
        if (!this.c(object)) {
            int n3 = this.b;
            Object[] objectArray = this.a;
            if (n3 < objectArray.length) {
                objectArray[n3] = object;
                this.b = n3 + 1;
                return true;
            }
            return false;
        }
        throw new IllegalStateException("Already in the pool!");
    }

    @Override
    public Object b() {
        int n3 = this.b;
        if (n3 > 0) {
            Object object = this.a[--n3];
            k.c(object, "null cannot be cast to non-null type T of androidx.core.util.Pools.SimplePool");
            this.a[n3] = null;
            --this.b;
            return object;
        }
        return null;
    }

    public final boolean c(Object object) {
        int n3 = this.b;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (this.a[i3] != object) continue;
            return true;
        }
        return false;
    }
}

