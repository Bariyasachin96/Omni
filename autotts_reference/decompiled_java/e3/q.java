/*
 * Decompiled with CFR 0.152.
 */
package e3;

import e3.l;
import e3.p;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import o3.k;

public abstract class q
extends p {
    public static boolean m(Collection collection, Iterable object) {
        k.e(collection, "<this>");
        k.e(object, "elements");
        if (object instanceof Collection) {
            return collection.addAll((Collection)object);
        }
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            if (!collection.add(object.next())) continue;
            bl = true;
        }
        return bl;
    }

    public static Object n(List list) {
        k.e(list, "<this>");
        if (!list.isEmpty()) {
            return list.remove(l.g(list));
        }
        throw new NoSuchElementException("List is empty.");
    }
}

