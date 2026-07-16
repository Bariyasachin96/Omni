/*
 * Decompiled with CFR 0.152.
 */
package r;

import r.f;

public class g
implements f {
    public final Object[] a;
    public int b;

    public g(int n3) {
        if (n3 > 0) {
            this.a = new Object[n3];
            return;
        }
        throw new IllegalArgumentException("The max pool size must be > 0");
    }

    @Override
    public boolean a(Object object) {
        int n3 = this.b;
        Object[] objectArray = this.a;
        if (n3 < objectArray.length) {
            objectArray[n3] = object;
            this.b = n3 + 1;
            return true;
        }
        return false;
    }

    @Override
    public Object b() {
        int n3 = this.b;
        if (n3 > 0) {
            int n4 = n3 - 1;
            Object[] objectArray = this.a;
            Object object = objectArray[n4];
            objectArray[n4] = null;
            this.b = n3 - 1;
            return object;
        }
        return null;
    }

    @Override
    public void c(Object[] objectArray, int n3) {
        int n4 = n3;
        if (n3 > objectArray.length) {
            n4 = objectArray.length;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            Object object = objectArray[n3];
            int n5 = this.b;
            Object[] objectArray2 = this.a;
            if (n5 >= objectArray2.length) continue;
            objectArray2[n5] = object;
            this.b = n5 + 1;
        }
    }
}

