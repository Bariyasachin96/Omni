/*
 * Decompiled with CFR 0.152.
 */
package j3;

import j3.a;
import o3.k;

public abstract class b {
    public static final a a;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        a a4;
        block24: {
            try {
                ClassCastException classCastException2;
                Object object;
                block25: {
                    object = l3.a.class.newInstance();
                    k.d(object, "forName(\"kotlin.internal…entations\").newInstance()");
                    if (object != null) {
                        try {
                            a4 = (a)object;
                            break block24;
                        }
                        catch (ClassCastException classCastException2) {
                            break block25;
                        }
                    }
                    NullPointerException nullPointerException = new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                    throw nullPointerException;
                }
                ClassLoader classLoader = object.getClass().getClassLoader();
                ClassLoader classLoader2 = a.class.getClassLoader();
                if (!k.a(classLoader, classLoader2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Instance class was loaded from a different classloader: ");
                    stringBuilder.append(classLoader);
                    stringBuilder.append(", base type classloader: ");
                    stringBuilder.append(classLoader2);
                    object = new Object(stringBuilder.toString(), classCastException2);
                    throw object;
                }
                throw classCastException2;
            }
            catch (ClassNotFoundException classNotFoundException) {
                try {
                    ClassCastException classCastException3;
                    Object object;
                    block26: {
                        object = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                        k.d(object, "forName(\"kotlin.internal…entations\").newInstance()");
                        if (object != null) {
                            try {
                                a4 = (a)object;
                            }
                            catch (ClassCastException classCastException3) {
                                break block26;
                            }
                        }
                        NullPointerException nullPointerException = new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                        throw nullPointerException;
                    }
                    ClassLoader classLoader = object.getClass().getClassLoader();
                    object = a.class.getClassLoader();
                    if (!k.a(classLoader, object)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Instance class was loaded from a different classloader: ");
                        stringBuilder.append(classLoader);
                        stringBuilder.append(", base type classloader: ");
                        stringBuilder.append(object);
                        ClassNotFoundException classNotFoundException2 = new ClassNotFoundException(stringBuilder.toString(), classCastException3);
                        throw classNotFoundException2;
                    }
                    throw classCastException3;
                }
                catch (ClassNotFoundException classNotFoundException3) {
                    try {
                        ClassCastException classCastException4;
                        Object object;
                        block27: {
                            object = k3.a.class.newInstance();
                            k.d(object, "forName(\"kotlin.internal…entations\").newInstance()");
                            if (object != null) {
                                try {
                                    a4 = (a)object;
                                    break block24;
                                }
                                catch (ClassCastException classCastException4) {
                                    break block27;
                                }
                            }
                            NullPointerException nullPointerException = new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                            throw nullPointerException;
                        }
                        ClassLoader classLoader = object.getClass().getClassLoader();
                        ClassLoader classLoader3 = a.class.getClassLoader();
                        if (!k.a(classLoader, classLoader3)) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Instance class was loaded from a different classloader: ");
                            stringBuilder.append(classLoader);
                            stringBuilder.append(", base type classloader: ");
                            stringBuilder.append(classLoader3);
                            object = new Object(stringBuilder.toString(), classCastException4);
                            throw object;
                        }
                        throw classCastException4;
                    }
                    catch (ClassNotFoundException classNotFoundException4) {
                        try {
                            ClassCastException classCastException5;
                            Object object;
                            block28: {
                                object = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                                k.d(object, "forName(\"kotlin.internal…entations\").newInstance()");
                                if (object != null) {
                                    try {
                                        a4 = (a)object;
                                        break block24;
                                    }
                                    catch (ClassCastException classCastException5) {
                                        break block28;
                                    }
                                }
                                NullPointerException nullPointerException = new NullPointerException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                                throw nullPointerException;
                            }
                            ClassLoader classLoader = object.getClass().getClassLoader();
                            object = a.class.getClassLoader();
                            if (!k.a(classLoader, object)) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Instance class was loaded from a different classloader: ");
                                stringBuilder.append(classLoader);
                                stringBuilder.append(", base type classloader: ");
                                stringBuilder.append(object);
                                ClassNotFoundException classNotFoundException5 = new ClassNotFoundException(stringBuilder.toString(), classCastException5);
                                throw classNotFoundException5;
                            }
                            throw classCastException5;
                        }
                        catch (ClassNotFoundException classNotFoundException6) {
                            a4 = new a();
                        }
                    }
                }
            }
        }
        a = a4;
    }
}

