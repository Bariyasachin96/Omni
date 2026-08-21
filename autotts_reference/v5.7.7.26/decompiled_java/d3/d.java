/*
 * Decompiled with CFR 0.152.
 */
package d3;

import java.io.Serializable;
import o3.k;

public final class d
implements Serializable {
    public final Object c;
    public final Object d;

    public d(Object object, Object object2) {
        this.c = object;
        this.d = object2;
    }

    public final Object a() {
        return this.c;
    }

    public final Object b() {
        return this.d;
    }

    public final Object c() {
        return this.c;
    }

    public final Object d() {
        return this.d;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof d)) {
            return false;
        }
        object = (d)object;
        if (!k.a(this.c, ((d)object).c)) {
            return false;
        }
        return k.a(this.d, ((d)object).d);
    }

    public int hashCode() {
        Object object = this.c;
        int n3 = 0;
        int n4 = object == null ? 0 : object.hashCode();
        object = this.d;
        if (object != null) {
            n3 = object.hashCode();
        }
        return n4 * 31 + n3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        stringBuilder.append(this.c);
        stringBuilder.append(", ");
        stringBuilder.append(this.d);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

