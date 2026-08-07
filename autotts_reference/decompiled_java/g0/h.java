/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package g0;

import android.graphics.Typeface;
import g0.g;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class h
extends g {
    /*
     * WARNING - void declaration
     */
    @Override
    public Typeface h(Object object) {
        void var1_4;
        Integer n3 = -1;
        try {
            Object object2 = Array.newInstance(this.g, 1);
            Array.set(object2, 0, object);
            object = (Typeface)this.m.invoke(null, object2, "sans-serif", n3, n3);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        throw new RuntimeException((Throwable)var1_4);
    }

    @Override
    public Method s(Class genericDeclaration) {
        Class<?> clazz = Array.newInstance(genericDeclaration, 1).getClass();
        genericDeclaration = Integer.TYPE;
        genericDeclaration = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{clazz, String.class, genericDeclaration, genericDeclaration});
        ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
        return genericDeclaration;
    }
}

