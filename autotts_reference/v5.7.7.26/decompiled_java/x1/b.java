/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package x1;

import android.os.IBinder;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import x1.a;

public final class b
extends a.a {
    public final Object d;

    public b(Object object) {
        this.d = object;
    }

    public static Object j(a object) {
        int n3;
        if (object instanceof b) {
            return ((b)object).d;
        }
        IBinder iBinder = object.asBinder();
        Field[] fieldArray = iBinder.getClass().getDeclaredFields();
        int n4 = fieldArray.length;
        object = null;
        int n5 = 0;
        for (int i3 = 0; i3 < n4; ++i3) {
            Field field = fieldArray[i3];
            n3 = n5;
            if (!field.isSynthetic()) {
                n3 = n5 + 1;
                object = field;
            }
            n5 = n3;
        }
        if (n5 == 1) {
            u1.b.c(object);
            if (!((AccessibleObject)object).isAccessible()) {
                ((AccessibleObject)object).setAccessible(true);
                try {
                    object = ((Field)object).get(iBinder);
                    return object;
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", illegalAccessException);
                }
                catch (NullPointerException nullPointerException) {
                    throw new IllegalArgumentException("Binder object is null.", nullPointerException);
                }
            }
            throw new IllegalArgumentException("IObjectWrapper declared field not private!");
        }
        n3 = fieldArray.length;
        object = new StringBuilder(String.valueOf(n3).length() + 53);
        ((StringBuilder)object).append("Unexpected number of IObjectWrapper declared fields: ");
        ((StringBuilder)object).append(n3);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static a k(Object object) {
        return new b(object);
    }
}

