/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.l;
import java.util.Collection;
import o3.k;

public abstract class m
extends l {
    public static int l(Iterable iterable, int n3) {
        k.e(iterable, "<this>");
        if (iterable instanceof Collection) {
            return ((Collection)iterable).size();
        }
        return n3;
    }
}

