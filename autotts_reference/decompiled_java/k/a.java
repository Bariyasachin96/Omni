/*
 * Decompiled with CFR 0.152.
 */
package k;

import java.util.HashMap;
import java.util.Map;
import k.b;

public class a
extends b {
    public final HashMap g = new HashMap();

    @Override
    public b.c b(Object object) {
        return (b.c)this.g.get(object);
    }

    public boolean contains(Object object) {
        return this.g.containsKey(object);
    }

    @Override
    public Object f(Object object, Object object2) {
        b.c c3 = this.b(object);
        if (c3 != null) {
            return c3.d;
        }
        this.g.put(object, this.e(object, object2));
        return null;
    }

    @Override
    public Object g(Object object) {
        Object object2 = super.g(object);
        this.g.remove(object);
        return object2;
    }

    public Map.Entry h(Object object) {
        if (this.contains(object)) {
            return ((b.c)this.g.get((Object)object)).f;
        }
        return null;
    }
}

