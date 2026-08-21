/*
 * Decompiled with CFR 0.152.
 */
package o;

import java.lang.reflect.Array;

public abstract class c {
    public static Object[] a(Object[] objectArray, int n3) {
        if (objectArray.length < n3) {
            return (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n3);
        }
        if (objectArray.length > n3) {
            objectArray[n3] = null;
        }
        return objectArray;
    }
}

