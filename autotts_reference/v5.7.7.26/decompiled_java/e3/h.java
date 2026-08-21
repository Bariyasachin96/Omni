/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.f;
import e3.g;
import e3.j;
import java.util.Arrays;
import java.util.List;
import o3.k;

public abstract class h
extends g {
    public static final List c(Object[] object) {
        k.e(object, "<this>");
        object = j.a(object);
        k.d(object, "asList(this)");
        return object;
    }

    public static final float[] d(float[] fArray, float[] fArray2, int n3, int n4, int n5) {
        k.e(fArray, "<this>");
        k.e(fArray2, "destination");
        System.arraycopy(fArray, n4, fArray2, n3, n5 - n4);
        return fArray2;
    }

    public static int[] e(int[] nArray, int[] nArray2, int n3, int n4, int n5) {
        k.e(nArray, "<this>");
        k.e(nArray2, "destination");
        System.arraycopy(nArray, n4, nArray2, n3, n5 - n4);
        return nArray2;
    }

    public static long[] f(long[] lArray, long[] lArray2, int n3, int n4, int n5) {
        k.e(lArray, "<this>");
        k.e(lArray2, "destination");
        System.arraycopy(lArray, n4, lArray2, n3, n5 - n4);
        return lArray2;
    }

    public static Object[] g(Object[] objectArray, Object[] objectArray2, int n3, int n4, int n5) {
        k.e(objectArray, "<this>");
        k.e(objectArray2, "destination");
        System.arraycopy(objectArray, n4, objectArray2, n3, n5 - n4);
        return objectArray2;
    }

    public static /* synthetic */ float[] h(float[] fArray, float[] fArray2, int n3, int n4, int n5, int n6, Object object) {
        if ((n6 & 2) != 0) {
            n3 = 0;
        }
        if ((n6 & 4) != 0) {
            n4 = 0;
        }
        if ((n6 & 8) != 0) {
            n5 = fArray.length;
        }
        return h.d(fArray, fArray2, n3, n4, n5);
    }

    public static /* synthetic */ int[] i(int[] nArray, int[] nArray2, int n3, int n4, int n5, int n6, Object object) {
        if ((n6 & 2) != 0) {
            n3 = 0;
        }
        if ((n6 & 4) != 0) {
            n4 = 0;
        }
        if ((n6 & 8) != 0) {
            n5 = nArray.length;
        }
        return h.e(nArray, nArray2, n3, n4, n5);
    }

    public static /* synthetic */ Object[] j(Object[] objectArray, Object[] objectArray2, int n3, int n4, int n5, int n6, Object object) {
        if ((n6 & 2) != 0) {
            n3 = 0;
        }
        if ((n6 & 4) != 0) {
            n4 = 0;
        }
        if ((n6 & 8) != 0) {
            n5 = objectArray.length;
        }
        return h.g(objectArray, objectArray2, n3, n4, n5);
    }

    public static Object[] k(Object[] objectArray, int n3, int n4) {
        k.e(objectArray, "<this>");
        f.b(n4, objectArray.length);
        objectArray = Arrays.copyOfRange(objectArray, n3, n4);
        k.d(objectArray, "copyOfRange(this, fromIndex, toIndex)");
        return objectArray;
    }

    public static final void l(long[] lArray, long l3, int n3, int n4) {
        k.e(lArray, "<this>");
        Arrays.fill(lArray, n3, n4, l3);
    }

    public static final void m(Object[] objectArray, Object object, int n3, int n4) {
        k.e(objectArray, "<this>");
        Arrays.fill(objectArray, n3, n4, object);
    }

    public static /* synthetic */ void n(long[] lArray, long l3, int n3, int n4, int n5, Object object) {
        if ((n5 & 2) != 0) {
            n3 = 0;
        }
        if ((n5 & 4) != 0) {
            n4 = lArray.length;
        }
        h.l(lArray, l3, n3, n4);
    }
}

