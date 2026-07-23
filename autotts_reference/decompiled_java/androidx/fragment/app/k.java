/*
 * Decompiled with CFR 0.152.
 */
package androidx.fragment.app;

import androidx.fragment.app.Fragment;
import o.r;

public abstract class k {
    public static final r a = new r();

    public static boolean b(ClassLoader classLoader, String string) {
        try {
            boolean bl = Fragment.class.isAssignableFrom(k.c(classLoader, string));
            return bl;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public static Class c(ClassLoader object, String string) {
        r r3 = a;
        Object object2 = (r)r3.get(object);
        r r4 = object2;
        if (object2 == null) {
            r4 = new r();
            r3.put(object, r4);
        }
        if ((object2 = (Class)r4.get(string)) == null) {
            object = Class.forName(string, false, (ClassLoader)object);
            r4.put(string, object);
            return object;
        }
        return object2;
    }

    public static Class d(ClassLoader object, String string) {
        try {
            object = k.c((ClassLoader)object, string);
            return object;
        }
        catch (ClassCastException classCastException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to instantiate fragment ");
            stringBuilder.append(string);
            stringBuilder.append(": make sure class is a valid subclass of Fragment");
            throw new Fragment.k(stringBuilder.toString(), classCastException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to instantiate fragment ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(": make sure class name exists");
            throw new Fragment.k(((StringBuilder)object).toString(), classNotFoundException);
        }
    }

    public abstract Fragment a(ClassLoader var1, String var2);
}

