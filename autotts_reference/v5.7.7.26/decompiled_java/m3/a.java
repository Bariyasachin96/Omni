/*
 * Decompiled with CFR 0.152.
 */
package m3;

import o3.d;
import o3.k;
import s3.b;

public abstract class a {
    public static final Class a(b object) {
        k.e(object, "<this>");
        object = ((d)object).a();
        k.c(object, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final Class b(b clazz) {
        k.e(clazz, "<this>");
        clazz = ((d)((Object)clazz)).a();
        if (!clazz.isPrimitive()) {
            k.c(clazz, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
            return clazz;
        }
        switch (clazz.getName()) {
            default: {
                break;
            }
            case "short": {
                clazz = Short.class;
                break;
            }
            case "float": {
                clazz = Float.class;
                break;
            }
            case "boolean": {
                clazz = Boolean.class;
                break;
            }
            case "void": {
                clazz = Void.class;
                break;
            }
            case "long": {
                clazz = Long.class;
                break;
            }
            case "char": {
                clazz = Character.class;
                break;
            }
            case "byte": {
                clazz = Byte.class;
                break;
            }
            case "int": {
                clazz = Integer.class;
                break;
            }
            case "double": {
                clazz = Double.class;
            }
        }
        k.c(clazz, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
        return clazz;
    }
}

