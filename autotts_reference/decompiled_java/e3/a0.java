/*
 * Decompiled with CFR 0.152.
 */
package e3;

import d3.d;
import e3.z;
import java.util.Collections;
import java.util.Map;
import o3.k;

public abstract class a0
extends z {
    public static int a(int n3) {
        if (n3 < 0) {
            return n3;
        }
        if (n3 < 3) {
            return n3 + 1;
        }
        if (n3 < 0x40000000) {
            return (int)((float)n3 / 0.75f + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static final Map b(d object) {
        k.e(object, "pair");
        object = Collections.singletonMap(((d)object).c(), ((d)object).d());
        k.d(object, "singletonMap(pair.first, pair.second)");
        return object;
    }

    public static final Map c(Map map) {
        k.e(map, "<this>");
        map = map.entrySet().iterator().next();
        map = Collections.singletonMap(map.getKey(), map.getValue());
        k.d(map, "with(entries.iterator().…ingletonMap(key, value) }");
        return map;
    }
}

