/*
 * Decompiled with CFR 0.152.
 */
package e3;

import f3.a;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class k {
    public static List a(List list) {
        o3.k.e(list, "builder");
        return ((a)list).h();
    }

    public static final Object[] b(Object[] objectArray, boolean bl) {
        o3.k.e(objectArray, "<this>");
        if (bl && o3.k.a(objectArray.getClass(), Object[].class)) {
            return objectArray;
        }
        objectArray = Arrays.copyOf(objectArray, objectArray.length, Object[].class);
        o3.k.d(objectArray, "copyOf(this, this.size, Array<Any?>::class.java)");
        return objectArray;
    }

    public static List c() {
        return new a();
    }

    public static List d(Object list) {
        list = Collections.singletonList(list);
        o3.k.d(list, "singletonList(element)");
        return list;
    }
}

