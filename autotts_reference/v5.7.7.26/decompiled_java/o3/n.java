/*
 * Decompiled with CFR 0.152.
 */
package o3;

import o3.h;
import o3.i;
import o3.l;
import o3.o;
import s3.b;
import s3.c;
import s3.d;

public abstract class n {
    public static final o a;
    public static final b[] b;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        o o3 = null;
        try {
            o o4;
            o3 = o4 = (o)Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        }
        catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException exception) {}
        if (o3 == null) {
            o3 = new o();
        }
        a = o3;
        b = new b[0];
    }

    public static d a(i i3) {
        return a.a(i3);
    }

    public static b b(Class clazz) {
        return a.b(clazz);
    }

    public static c c(Class clazz) {
        return a.c(clazz, "");
    }

    public static String d(h h3) {
        return a.d(h3);
    }

    public static String e(l l3) {
        return a.e(l3);
    }
}

