/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.f;
import androidx.lifecycle.k;
import androidx.lifecycle.q;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class a {
    public static a c = new a();
    public final Map a = new HashMap();
    public final Map b = new HashMap();

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final a a(Class clazz, Method[] object) {
        void var2_4;
        int n3;
        a a4;
        Class clazz2 = clazz.getSuperclass();
        HashMap hashMap = new HashMap();
        if (clazz2 != null && (a4 = this.c(clazz2)) != null) {
            hashMap.putAll(a4.b);
        }
        Class<?>[] classArray = clazz.getInterfaces();
        int n4 = classArray.length;
        for (n3 = 0; n3 < n4; ++n3) {
            for (Map.Entry entry : this.c(classArray[n3]).b.entrySet()) {
                this.e(hashMap, (b)entry.getKey(), (f.a)((Object)entry.getValue()), clazz);
            }
        }
        if (object == null) {
            Method[] methodArray = this.b(clazz);
        }
        int n5 = ((void)var2_4).length;
        boolean bl = false;
        for (n4 = 0; n4 < n5; ++n4) {
            void var8_12 = var2_4[n4];
            q q3 = var8_12.getAnnotation(q.class);
            if (q3 == null) continue;
            Class<?>[] classArray2 = var8_12.getParameterTypes();
            if (classArray2.length > 0) {
                if (!k.class.isAssignableFrom(classArray2[0])) throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                n3 = 1;
            } else {
                n3 = 0;
            }
            f.a a5 = q3.value();
            if (classArray2.length > 1) {
                if (!f.a.class.isAssignableFrom(classArray2[1])) throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                if (a5 != f.a.ON_ANY) throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                n3 = 2;
            }
            if (classArray2.length > 2) throw new IllegalArgumentException("cannot have more than 2 params");
            this.e(hashMap, new b(n3, (Method)var8_12), a5, clazz);
            bl = true;
        }
        a a6 = new a(hashMap);
        this.a.put(clazz, a6);
        this.b.put(clazz, bl);
        return a6;
    }

    public final Method[] b(Class methodArray) {
        try {
            methodArray = methodArray.getDeclaredMethods();
            return methodArray;
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", noClassDefFoundError);
        }
    }

    public a c(Class clazz) {
        a a4 = (a)this.a.get(clazz);
        if (a4 != null) {
            return a4;
        }
        return this.a(clazz, null);
    }

    public boolean d(Class clazz) {
        Method[] methodArray = (Method[])this.b.get(clazz);
        if (methodArray != null) {
            return methodArray.booleanValue();
        }
        methodArray = this.b(clazz);
        int n3 = methodArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            if (methodArray[i3].getAnnotation(q.class) == null) continue;
            this.a(clazz, methodArray);
            return true;
        }
        this.b.put(clazz, Boolean.FALSE);
        return false;
    }

    public final void e(Map object, b object2, f.a a4, Class clazz) {
        f.a a5 = (f.a)((Object)object.get(object2));
        if (a5 != null && a4 != a5) {
            object = ((b)object2).b;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Method ");
            ((StringBuilder)object2).append(((Method)object).getName());
            ((StringBuilder)object2).append(" in ");
            ((StringBuilder)object2).append(clazz.getName());
            ((StringBuilder)object2).append(" already declared with different @OnLifecycleEvent value: previous value ");
            ((StringBuilder)object2).append((Object)a5);
            ((StringBuilder)object2).append(", new value ");
            ((StringBuilder)object2).append((Object)a4);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        if (a5 == null) {
            object.put(object2, a4);
        }
    }

    public static class a {
        public final Map a;
        public final Map b;

        public a(Map arrayList) {
            this.b = arrayList;
            this.a = new HashMap();
            for (Map.Entry entry : arrayList.entrySet()) {
                f.a a4 = (f.a)((Object)entry.getValue());
                List list = (List)this.a.get((Object)a4);
                arrayList = list;
                if (list == null) {
                    arrayList = new ArrayList<b>();
                    this.a.put(a4, arrayList);
                }
                arrayList.add((b)entry.getKey());
            }
        }

        public static void b(List list, k k3, f.a a4, Object object) {
            if (list != null) {
                for (int i3 = list.size() - 1; i3 >= 0; --i3) {
                    ((b)list.get(i3)).a(k3, a4, object);
                }
            }
        }

        public void a(k k3, f.a a4, Object object) {
            androidx.lifecycle.a$a.b((List)this.a.get((Object)a4), k3, a4, object);
            androidx.lifecycle.a$a.b((List)this.a.get((Object)f.a.ON_ANY), k3, a4, object);
        }
    }

    public static final class b {
        public final int a;
        public final Method b;

        public b(int n3, Method method) {
            this.a = n3;
            this.b = method;
            ((AccessibleObject)method).setAccessible(true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void a(k k3, f.a a4, Object object) {
            try {
                int n3 = this.a;
                if (n3 == 0) {
                    this.b.invoke(object, null);
                    return;
                }
                if (n3 == 1) {
                    this.b.invoke(object, k3);
                    return;
                }
                if (n3 != 2) {
                    return;
                }
                this.b.invoke(object, new Object[]{k3, a4});
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException("Failed to call observer method", invocationTargetException.getCause());
            }
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof b)) {
                return false;
            }
            object = (b)object;
            return this.a == ((b)object).a && this.b.getName().equals(((b)object).b.getName());
        }

        public int hashCode() {
            return this.a * 31 + this.b.getName().hashCode();
        }
    }
}

