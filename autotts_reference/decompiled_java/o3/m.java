/*
 * Decompiled with CFR 0.152.
 */
package o3;

import o3.d;
import o3.k;

public final class m
implements d {
    public final Class a;
    public final String b;

    public m(Class clazz, String string) {
        k.e(clazz, "jClass");
        k.e(string, "moduleName");
        this.a = clazz;
        this.b = string;
    }

    @Override
    public Class a() {
        return this.a;
    }

    public boolean equals(Object object) {
        return object instanceof m && k.a(this.a(), ((m)object).a());
    }

    public int hashCode() {
        return this.a().hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.a().toString());
        stringBuilder.append(" (Kotlin reflection is not available)");
        return stringBuilder.toString();
    }
}

