/*
 * Decompiled with CFR 0.152.
 */
package i3;

import i3.a;
import i3.d;
import i3.g;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import o3.k;

public abstract class e {
    public static final void a(int n3, int n4) {
        if (n4 <= n3) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Debug metadata version mismatch. Expected: ");
        stringBuilder.append(n3);
        stringBuilder.append(", got ");
        stringBuilder.append(n4);
        stringBuilder.append(". Please update the Kotlin standard library.");
        throw new IllegalStateException(stringBuilder.toString().toString());
    }

    public static final d b(a a4) {
        return a4.getClass().getAnnotation(d.class);
    }

    public static final int c(a object) {
        int n3;
        block4: {
            block3: {
                try {
                    Field field = object.getClass().getDeclaredField("label");
                    ((AccessibleObject)field).setAccessible(true);
                    object = field.get(object);
                    object = object instanceof Integer ? (Integer)object : null;
                    if (object == null) break block3;
                }
                catch (Exception exception) {
                    return -1;
                }
                n3 = (Integer)object;
                break block4;
            }
            n3 = 0;
        }
        return n3 - 1;
    }

    public static final StackTraceElement d(a object) {
        k.e(object, "<this>");
        d d3 = e.b((a)object);
        if (d3 == null) {
            return null;
        }
        e.a(1, d3.v());
        int n3 = e.c((a)object);
        n3 = n3 < 0 ? -1 : d3.l()[n3];
        String string = g.a.b((a)object);
        if (string == null) {
            object = d3.c();
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append('/');
            ((StringBuilder)object).append(d3.c());
            object = ((StringBuilder)object).toString();
        }
        return new StackTraceElement((String)object, d3.m(), d3.f(), n3);
    }
}

