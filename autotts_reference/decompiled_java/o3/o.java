/*
 * Decompiled with CFR 0.152.
 */
package o3;

import o3.e;
import o3.h;
import o3.i;
import o3.l;
import o3.m;
import s3.b;
import s3.c;
import s3.d;

public class o {
    public d a(i i3) {
        return i3;
    }

    public b b(Class clazz) {
        return new e(clazz);
    }

    public c c(Class clazz, String string) {
        return new m(clazz, string);
    }

    public String d(h object) {
        String string = object.getClass().getGenericInterfaces()[0].toString();
        object = string;
        if (string.startsWith("kotlin.jvm.functions.")) {
            object = string.substring(21);
        }
        return object;
    }

    public String e(l l3) {
        return this.d(l3);
    }
}

