/*
 * Decompiled with CFR 0.152.
 */
package e3;

import d3.d;
import e3.a0;
import e3.w;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import o3.k;

public abstract class b0
extends a0 {
    public static Map d() {
        w w3 = w.c;
        k.c(w3, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.emptyMap, V of kotlin.collections.MapsKt__MapsKt.emptyMap>");
        return w3;
    }

    public static final Map e(Map map) {
        k.e(map, "<this>");
        int n3 = map.size();
        if (n3 != 0) {
            if (n3 != 1) {
                return map;
            }
            return a0.c(map);
        }
        return b0.d();
    }

    public static final void f(Map map, Iterable object) {
        k.e(map, "<this>");
        k.e(object, "pairs");
        object = object.iterator();
        while (object.hasNext()) {
            d d3 = (d)object.next();
            map.put(d3.a(), d3.b());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Map g(Iterable object) {
        k.e(object, "<this>");
        if (!(object instanceof Collection)) return b0.e(b0.h((Iterable)object, new LinkedHashMap()));
        Collection collection = (Collection)object;
        int n3 = collection.size();
        if (n3 == 0) return b0.d();
        if (n3 != 1) {
            return b0.h((Iterable)object, new LinkedHashMap(a0.a(collection.size())));
        }
        object = object instanceof List ? ((List)object).get(0) : object.iterator().next();
        object = (d)object;
        return a0.b((d)object);
    }

    public static final Map h(Iterable iterable, Map map) {
        k.e(iterable, "<this>");
        k.e(map, "destination");
        b0.f(map, iterable);
        return map;
    }
}

