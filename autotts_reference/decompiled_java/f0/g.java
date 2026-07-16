/*
 * Decompiled with CFR 0.152.
 */
package f0;

import java.lang.reflect.Array;

public abstract class g {
    public static int[] a(int[] nArray, int n3, int n4) {
        int[] nArray2 = nArray;
        if (n3 + 1 > nArray.length) {
            nArray2 = new int[g.c(n3)];
            System.arraycopy(nArray, 0, nArray2, 0, n3);
        }
        nArray2[n3] = n4;
        return nArray2;
    }

    public static Object[] b(Object[] objectArray, int n3, Object object) {
        Object[] objectArray2 = objectArray;
        if (n3 + 1 > objectArray.length) {
            objectArray2 = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), g.c(n3));
            System.arraycopy(objectArray, 0, objectArray2, 0, n3);
        }
        objectArray2[n3] = object;
        return objectArray2;
    }

    public static int c(int n3) {
        if (n3 <= 4) {
            return 8;
        }
        return n3 * 2;
    }
}

