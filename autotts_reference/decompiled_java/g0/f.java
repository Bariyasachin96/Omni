/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.util.Log
 */
package g0;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import f0.e;
import g0.j;
import g0.k;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class f
extends j {
    public static Class b;
    public static Constructor c;
    public static Method d;
    public static Method e;
    public static boolean f = false;

    public static boolean g(Object object, String string, int n3, boolean bl) {
        g0.f.i();
        try {
            bl = (Boolean)d.invoke(object, string, n3, bl);
            return bl;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    private static Typeface h(Object object) {
        g0.f.i();
        try {
            Object object2 = Array.newInstance(b, 1);
            Array.set(object2, 0, object);
            object = (Typeface)e.invoke(null, object2);
            return object;
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static void i() {
        Method method;
        GenericDeclaration genericDeclaration;
        Class<?> clazz;
        Constructor<?> constructor;
        block4: {
            if (f) {
                return;
            }
            f = true;
            constructor = null;
            try {
                clazz = Class.forName("android.graphics.FontFamily");
                Constructor<?> constructor2 = clazz.getConstructor(null);
                genericDeclaration = clazz.getMethod("addFontWeightStyle", String.class, Integer.TYPE, Boolean.TYPE);
                method = Typeface.class.getMethod("createFromFamiliesWithDefault", Array.newInstance(clazz, 1).getClass());
                constructor = constructor2;
                break block4;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            Log.e((String)"TypefaceCompatApi21Impl", (String)method.getClass().getName(), (Throwable)((Object)method));
            method = null;
            genericDeclaration = clazz = null;
        }
        c = constructor;
        b = clazz;
        d = genericDeclaration;
        e = method;
    }

    private static Object j() {
        g0.f.i();
        try {
            Object t3 = c.newInstance(null);
            return t3;
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    @Override
    public Typeface a(Context context, e.c object, Resources resources, int n3) {
        Object object2 = g0.f.j();
        for (e.d d3 : ((e.c)object).a()) {
            block7: {
                boolean bl;
                block6: {
                    object = k.d(context);
                    if (object == null) {
                        return null;
                    }
                    bl = k.b((File)object, resources, d3.b());
                    if (bl) break block6;
                    ((File)object).delete();
                    return null;
                }
                try {
                    bl = g0.f.g(object2, ((File)object).getPath(), d3.e(), d3.f());
                    if (bl) break block7;
                }
                catch (Throwable throwable) {
                    ((File)object).delete();
                    throw throwable;
                }
                catch (RuntimeException runtimeException) {
                    ((File)object).delete();
                    return null;
                }
                ((File)object).delete();
                return null;
            }
            ((File)object).delete();
        }
        return g0.f.h(object2);
    }
}

