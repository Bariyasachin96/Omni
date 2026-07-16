/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.v;
import e3.x;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import o3.k;

public final class w
implements Map,
Serializable {
    public static final w c = new w();

    public boolean a(Void void_) {
        k.e(void_, "value");
        return false;
    }

    public Void b(Object object) {
        return null;
    }

    public Set c() {
        return x.c;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean containsKey(Object object) {
        return false;
    }

    public Set d() {
        return x.c;
    }

    public int e() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Map && ((Map)object).isEmpty();
    }

    public Collection f() {
        return v.c;
    }

    public Void g(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    public void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public String toString() {
        return "{}";
    }
}

