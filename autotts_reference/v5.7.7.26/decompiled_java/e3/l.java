/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.d;
import e3.h;
import e3.k;
import e3.v;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import r3.c;

public abstract class l
extends k {
    public static List e() {
        return v.c;
    }

    public static c f(Collection collection) {
        o3.k.e(collection, "<this>");
        return new c(0, collection.size() - 1);
    }

    public static int g(List list) {
        o3.k.e(list, "<this>");
        return list.size() - 1;
    }

    public static List h(Object ... objectArray) {
        o3.k.e(objectArray, "elements");
        if (objectArray.length > 0) {
            return h.c(objectArray);
        }
        return l.e();
    }

    public static List i(Object ... objectArray) {
        o3.k.e(objectArray, "elements");
        if (objectArray.length == 0) {
            return new ArrayList();
        }
        return new ArrayList(new d(objectArray, true));
    }

    public static void j() {
        throw new ArithmeticException("Count overflow has happened.");
    }

    public static void k() {
        throw new ArithmeticException("Index overflow has happened.");
    }
}

