/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.appcompat.app.s;
import androidx.lifecycle.CompositeGeneratedAdaptersObserver;
import androidx.lifecycle.DefaultLifecycleObserverAdapter;
import androidx.lifecycle.ReflectiveGenericLifecycleObserver;
import androidx.lifecycle.SingleGeneratedAdapterObserver;
import androidx.lifecycle.a;
import androidx.lifecycle.b;
import androidx.lifecycle.d;
import androidx.lifecycle.i;
import androidx.lifecycle.j;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import u3.k;

public final class m {
    public static final m a = new m();
    public static final Map b = new HashMap();
    public static final Map c = new HashMap();

    public static final String c(String string) {
        o3.k.e(string, "className");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(k.d(string, ".", "_", false, 4, null));
        stringBuilder.append("_LifecycleAdapter");
        return stringBuilder.toString();
    }

    public static final i f(Object object) {
        o3.k.e(object, "object");
        boolean bl = object instanceof i;
        boolean bl2 = object instanceof b;
        if (bl && bl2) {
            return new DefaultLifecycleObserverAdapter((b)object, (i)object);
        }
        if (bl2) {
            return new DefaultLifecycleObserverAdapter((b)object, null);
        }
        if (bl) {
            return (i)object;
        }
        d[] dArray = a;
        Class<?> clazz = object.getClass();
        if (dArray.d(clazz) == 2) {
            clazz = c.get(clazz);
            o3.k.b(clazz);
            clazz = (List)((Object)clazz);
            int n3 = clazz.size();
            if (n3 == 1) {
                dArray.a((Constructor)clazz.get(0), object);
                return new SingleGeneratedAdapterObserver(null);
            }
            n3 = clazz.size();
            dArray = new d[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                a.a((Constructor)clazz.get(i3), object);
                dArray[i3] = null;
            }
            return new CompositeGeneratedAdaptersObserver(dArray);
        }
        return new ReflectiveGenericLifecycleObserver(object);
    }

    public final d a(Constructor constructor, Object object) {
        IllegalAccessException illegalAccessException2;
        block5: {
            InstantiationException instantiationException2;
            block4: {
                try {
                    constructor = constructor.newInstance(object);
                    o3.k.d(constructor, "{\n            constructo…tance(`object`)\n        }");
                    s.a(constructor);
                    return null;
                }
                catch (InvocationTargetException invocationTargetException) {
                }
                catch (InstantiationException instantiationException2) {
                    break block4;
                }
                catch (IllegalAccessException illegalAccessException2) {
                    break block5;
                }
                throw new RuntimeException(invocationTargetException);
            }
            throw new RuntimeException(instantiationException2);
        }
        throw new RuntimeException(illegalAccessException2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Constructor b(Class genericDeclaration) {
        try {
            Object object = ((Class)genericDeclaration).getPackage();
            String string = ((Class)genericDeclaration).getCanonicalName();
            object = object != null ? ((Package)object).getName() : "";
            o3.k.d(object, "fullPackage");
            if (object.length() != 0) {
                o3.k.d(string, "name");
                string = string.substring(((String)object).length() + 1);
                o3.k.d(string, "this as java.lang.String).substring(startIndex)");
            }
            o3.k.d(string, "if (fullPackage.isEmpty(…g(fullPackage.length + 1)");
            string = m.c(string);
            if (object.length() == 0) {
                object = string;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append('.');
                stringBuilder.append(string);
                object = stringBuilder.toString();
            }
            object = Class.forName((String)object);
            o3.k.c(object, "null cannot be cast to non-null type java.lang.Class<out androidx.lifecycle.GeneratedAdapter>");
            genericDeclaration = ((Class)object).getDeclaredConstructor(new Class[]{genericDeclaration});
            if (((AccessibleObject)((Object)genericDeclaration)).isAccessible()) return genericDeclaration;
            ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            return genericDeclaration;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public final int d(Class clazz) {
        Map map = b;
        Integer n3 = (Integer)map.get(clazz);
        if (n3 != null) {
            return n3;
        }
        int n4 = this.g(clazz);
        map.put(clazz, n4);
        return n4;
    }

    public final boolean e(Class clazz) {
        return clazz != null && j.class.isAssignableFrom(clazz);
    }

    public final int g(Class clazz) {
        Object object;
        if (clazz.getCanonicalName() == null) {
            return 1;
        }
        Class clazz2 = this.b(clazz);
        if (clazz2 != null) {
            c.put(clazz, e3.k.d(clazz2));
            return 2;
        }
        if (androidx.lifecycle.a.c.d(clazz)) {
            return 1;
        }
        clazz2 = clazz.getSuperclass();
        if (this.e(clazz2)) {
            o3.k.d(clazz2, "superclass");
            if (this.d(clazz2) == 1) {
                return 1;
            }
            clazz2 = c.get(clazz2);
            o3.k.b(clazz2);
            object = new ArrayList((Collection)((Object)clazz2));
        } else {
            object = null;
        }
        Class<?>[] classArray = clazz.getInterfaces();
        o3.k.d(classArray, "klass.interfaces");
        for (Class<?> clazz3 : classArray) {
            if (!this.e(clazz3)) {
                clazz2 = object;
            } else {
                o3.k.d(clazz3, "intrface");
                if (this.d(clazz3) == 1) {
                    return 1;
                }
                clazz2 = object;
                if (object == null) {
                    clazz2 = new ArrayList();
                }
                object = c.get(clazz3);
                o3.k.b(object);
                clazz2.addAll((Collection)object);
            }
            object = clazz2;
        }
        if (object != null) {
            c.put(clazz, object);
            return 2;
        }
        return 1;
    }
}

