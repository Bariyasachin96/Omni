/*
 * Decompiled with CFR 0.152.
 */
package n0;

import java.util.Objects;

public abstract class c {
    public static boolean a(Object object, Object object2) {
        return Objects.equals(object, object2);
    }

    public static int b(Object ... objectArray) {
        return Objects.hash(objectArray);
    }

    public static Object c(Object object, String string) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException(string);
    }
}

