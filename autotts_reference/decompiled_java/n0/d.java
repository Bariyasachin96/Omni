/*
 * Decompiled with CFR 0.152.
 */
package n0;

import n0.c;

public class d {
    public final Object a;
    public final Object b;

    public d(Object object, Object object2) {
        this.a = object;
        this.b = object2;
    }

    public static d a(Object object, Object object2) {
        return new d(object, object2);
    }

    public boolean equals(Object object) {
        if (!(object instanceof d)) {
            return false;
        }
        object = (d)object;
        return c.a(((d)object).a, this.a) && c.a(((d)object).b, this.b);
    }

    public int hashCode() {
        Object object = this.a;
        int n3 = 0;
        int n4 = object == null ? 0 : object.hashCode();
        object = this.b;
        if (object != null) {
            n3 = object.hashCode();
        }
        return n4 ^ n3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pair{");
        stringBuilder.append(this.a);
        stringBuilder.append(" ");
        stringBuilder.append(this.b);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

