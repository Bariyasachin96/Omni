/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.h;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import o3.k;

public abstract class i
extends h {
    public static final boolean o(Object[] objectArray, Object object) {
        k.e(objectArray, "<this>");
        return i.s(objectArray, object) >= 0;
    }

    public static List p(Object[] objectArray) {
        k.e(objectArray, "<this>");
        return (List)i.q(objectArray, new ArrayList());
    }

    public static final Collection q(Object[] objectArray, Collection collection) {
        k.e(objectArray, "<this>");
        k.e(collection, "destination");
        for (Object object : objectArray) {
            if (object == null) continue;
            collection.add(object);
        }
        return collection;
    }

    public static final int r(Object[] objectArray) {
        k.e(objectArray, "<this>");
        return objectArray.length - 1;
    }

    public static final int s(Object[] objectArray, Object object) {
        int n3;
        k.e(objectArray, "<this>");
        int n4 = 0;
        if (object == null) {
            int n5 = objectArray.length;
            for (n3 = n4; n3 < n5; ++n3) {
                if (objectArray[n3] != null) continue;
                return n3;
            }
        } else {
            n4 = objectArray.length;
            for (n3 = 0; n3 < n4; ++n3) {
                if (!k.a(object, objectArray[n3])) continue;
                return n3;
            }
        }
        return -1;
    }

    public static char t(char[] cArray) {
        k.e(cArray, "<this>");
        int n3 = cArray.length;
        if (n3 != 0) {
            if (n3 == 1) {
                return cArray[0];
            }
            throw new IllegalArgumentException("Array has more than one element.");
        }
        throw new NoSuchElementException("Array is empty.");
    }
}

