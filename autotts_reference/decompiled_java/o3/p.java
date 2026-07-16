/*
 * Decompiled with CFR 0.152.
 */
package o3;

import n3.a;
import n3.l;
import o3.h;
import o3.k;

public abstract class p {
    public static Object a(Object object, int n3) {
        if (object != null && !p.c(object, n3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("kotlin.jvm.functions.Function");
            stringBuilder.append(n3);
            p.f(object, stringBuilder.toString());
        }
        return object;
    }

    public static int b(Object object) {
        if (object instanceof h) {
            return ((h)object).c();
        }
        if (object instanceof a) {
            return 0;
        }
        if (object instanceof l) {
            return 1;
        }
        if (object instanceof n3.p) {
            return 2;
        }
        return -1;
    }

    public static boolean c(Object object, int n3) {
        return object instanceof d3.a && p.b(object) == n3;
    }

    public static Throwable d(Throwable throwable) {
        return k.h(throwable, p.class.getName());
    }

    public static ClassCastException e(ClassCastException classCastException) {
        throw (ClassCastException)p.d(classCastException);
    }

    public static void f(Object object, String string) {
        object = object == null ? "null" : object.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(" cannot be cast to ");
        stringBuilder.append(string);
        p.g(stringBuilder.toString());
    }

    public static void g(String string) {
        throw p.e(new ClassCastException(string));
    }
}

