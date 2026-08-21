/*
 * Decompiled with CFR 0.152.
 */
package n0;

import n0.f;
import o3.k;

public class g
extends f {
    public final Object c = new Object();

    public g(int n3) {
        super(n3);
    }

    @Override
    public boolean a(Object object) {
        k.e(object, "instance");
        Object object2 = this.c;
        synchronized (object2) {
            boolean bl = super.a(object);
            return bl;
        }
    }

    @Override
    public Object b() {
        Object object = this.c;
        synchronized (object) {
            Object object2 = super.b();
            return object2;
        }
    }
}

