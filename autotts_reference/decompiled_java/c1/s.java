/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.g;
import o3.k;

public final class s {
    public final float a;
    public final g b;

    public s(float f3, g g3) {
        k.e(g3, "feature");
        this.a = f3;
        this.b = g3;
    }

    public final g a() {
        return this.b;
    }

    public final float b() {
        return this.a;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof s)) {
            return false;
        }
        object = (s)object;
        if (Float.compare(this.a, ((s)object).a) != 0) {
            return false;
        }
        return k.a(this.b, ((s)object).b);
    }

    public int hashCode() {
        return Float.hashCode(this.a) * 31 + this.b.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProgressableFeature(progress=");
        stringBuilder.append(this.a);
        stringBuilder.append(", feature=");
        stringBuilder.append(this.b);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

