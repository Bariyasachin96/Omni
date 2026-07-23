/*
 * Decompiled with CFR 0.152.
 */
package o3;

import java.util.Arrays;

public abstract class k {
    public static boolean a(Object object, Object object2) {
        if (object == null) {
            return object2 == null;
        }
        return object.equals(object2);
    }

    public static void b(Object object) {
        if (object == null) {
            k.i();
        }
    }

    public static void c(Object object, String string) {
        if (object == null) {
            k.j(string);
        }
    }

    public static void d(Object object, String string) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" must not be null");
        throw (NullPointerException)k.g(new NullPointerException(((StringBuilder)object).toString()));
    }

    public static void e(Object object, String string) {
        if (object == null) {
            k.k(string);
        }
    }

    public static String f(String string) {
        int n3;
        Object object = Thread.currentThread().getStackTrace();
        Object object2 = k.class.getName();
        int n4 = 0;
        do {
            n3 = ++n4;
        } while (!object[n4].getClassName().equals(object2));
        while (object[n3].getClassName().equals(object2)) {
            ++n3;
        }
        object2 = object[n3];
        object = ((StackTraceElement)object2).getClassName();
        object2 = ((StackTraceElement)object2).getMethodName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Parameter specified as non-null is null: method ");
        stringBuilder.append((String)object);
        stringBuilder.append(".");
        stringBuilder.append((String)object2);
        stringBuilder.append(", parameter ");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public static Throwable g(Throwable throwable) {
        return k.h(throwable, k.class.getName());
    }

    public static Throwable h(Throwable throwable, String string) {
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        int n3 = stackTraceElementArray.length;
        int n4 = -1;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!string.equals(stackTraceElementArray[i3].getClassName())) continue;
            n4 = i3;
        }
        throwable.setStackTrace(Arrays.copyOfRange(stackTraceElementArray, n4 + 1, n3));
        return throwable;
    }

    public static void i() {
        throw (NullPointerException)k.g(new NullPointerException());
    }

    public static void j(String string) {
        throw (NullPointerException)k.g(new NullPointerException(string));
    }

    public static void k(String string) {
        throw (NullPointerException)k.g(new NullPointerException(k.f(string)));
    }
}

