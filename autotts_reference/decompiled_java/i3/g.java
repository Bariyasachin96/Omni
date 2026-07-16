/*
 * Decompiled with CFR 0.152.
 */
package i3;

import java.lang.reflect.Method;
import o3.k;

public final class g {
    public static final g a = new g();
    public static final a b = new a(null, null, null);
    public static a c;

    public final a a(i3.a object) {
        try {
            a a4;
            Method method = Class.class.getDeclaredMethod("getModule", null);
            Method method2 = object.getClass().getClassLoader().loadClass("java.lang.Module").getDeclaredMethod("getDescriptor", null);
            object = object.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor").getDeclaredMethod("name", null);
            c = a4 = new a(method, method2, (Method)object);
            return a4;
        }
        catch (Exception exception) {
            a a5;
            c = a5 = b;
            return a5;
        }
    }

    public final String b(i3.a object) {
        Object object2;
        k.e(object, "continuation");
        Object object3 = object2 = c;
        if (object2 == null) {
            object3 = this.a((i3.a)object);
        }
        if (object3 == b) {
            return null;
        }
        object2 = ((a)object3).a;
        object = object2 != null ? ((Method)object2).invoke(object.getClass(), null) : null;
        if (object == null) {
            return null;
        }
        object2 = ((a)object3).b;
        object = object2 != null ? ((Method)object2).invoke(object, null) : null;
        if (object == null) {
            return null;
        }
        object3 = ((a)object3).c;
        object = object3 != null ? ((Method)object3).invoke(object, null) : null;
        if (object instanceof String) {
            return (String)object;
        }
        return null;
    }

    public static final class a {
        public final Method a;
        public final Method b;
        public final Method c;

        public a(Method method, Method method2, Method method3) {
            this.a = method;
            this.b = method2;
            this.c = method3;
        }
    }
}

