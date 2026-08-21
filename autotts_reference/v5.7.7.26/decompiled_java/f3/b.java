/*
 * Decompiled with CFR 0.152.
 */
package f3;

import java.util.Arrays;
import java.util.List;
import o3.k;

public abstract class b {
    public static final /* synthetic */ boolean a(Object[] objectArray, int n3, int n4, List list) {
        return b.h(objectArray, n3, n4, list);
    }

    public static final /* synthetic */ int b(Object[] objectArray, int n3, int n4) {
        return b.i(objectArray, n3, n4);
    }

    public static final /* synthetic */ String c(Object[] objectArray, int n3, int n4) {
        return b.j(objectArray, n3, n4);
    }

    public static final Object[] d(int n3) {
        if (n3 >= 0) {
            return new Object[n3];
        }
        throw new IllegalArgumentException("capacity must be non-negative.");
    }

    public static final Object[] e(Object[] objectArray, int n3) {
        k.e(objectArray, "<this>");
        objectArray = Arrays.copyOf(objectArray, n3);
        k.d(objectArray, "copyOf(this, newSize)");
        return objectArray;
    }

    public static final void f(Object[] objectArray, int n3) {
        k.e(objectArray, "<this>");
        objectArray[n3] = null;
    }

    public static final void g(Object[] objectArray, int n3, int n4) {
        k.e(objectArray, "<this>");
        while (n3 < n4) {
            b.f(objectArray, n3);
            ++n3;
        }
    }

    public static final boolean h(Object[] objectArray, int n3, int n4, List list) {
        if (n4 != list.size()) {
            return false;
        }
        for (int i3 = 0; i3 < n4; ++i3) {
            if (k.a(objectArray[n3 + i3], list.get(i3))) continue;
            return false;
        }
        return true;
    }

    public static final int i(Object[] objectArray, int n3, int n4) {
        int n5 = 1;
        for (int i3 = 0; i3 < n4; ++i3) {
            Object object = objectArray[n3 + i3];
            int n6 = object != null ? object.hashCode() : 0;
            n5 = n5 * 31 + n6;
        }
        return n5;
    }

    public static final String j(Object[] object, int n3, int n4) {
        StringBuilder stringBuilder = new StringBuilder(n4 * 3 + 2);
        stringBuilder.append("[");
        for (int i3 = 0; i3 < n4; ++i3) {
            if (i3 > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(object[n3 + i3]);
        }
        stringBuilder.append("]");
        object = stringBuilder.toString();
        k.d(object, "sb.toString()");
        return object;
    }
}

