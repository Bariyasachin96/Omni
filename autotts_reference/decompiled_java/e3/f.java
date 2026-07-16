/*
 * Decompiled with CFR 0.152.
 */
package e3;

import java.lang.reflect.Array;
import o3.k;

public abstract class f {
    public static final Object[] a(Object[] objectArray, int n3) {
        k.e(objectArray, "reference");
        objectArray = Array.newInstance(objectArray.getClass().getComponentType(), n3);
        k.c(objectArray, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.arrayOfNulls>");
        return objectArray;
    }

    public static final void b(int n3, int n4) {
        if (n3 <= n4) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("toIndex (");
        stringBuilder.append(n3);
        stringBuilder.append(") is greater than size (");
        stringBuilder.append(n4);
        stringBuilder.append(").");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }
}

