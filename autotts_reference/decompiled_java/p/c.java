/*
 * Decompiled with CFR 0.152.
 */
package p;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Set;
import o3.k;

public final class c {
    public final LinkedHashMap a;

    public c(int n3, float f3) {
        this.a = new LinkedHashMap(n3, f3, true);
    }

    public final Object a(Object object) {
        k.e(object, "key");
        return this.a.get(object);
    }

    public final Set b() {
        Set set = this.a.entrySet();
        k.d(set, "map.entries");
        return set;
    }

    public final boolean c() {
        return ((AbstractMap)this.a).isEmpty();
    }

    public final Object d(Object object, Object object2) {
        k.e(object, "key");
        k.e(object2, "value");
        return ((AbstractMap)this.a).put(object, object2);
    }

    public final Object e(Object object) {
        k.e(object, "key");
        return ((AbstractMap)this.a).remove(object);
    }
}

