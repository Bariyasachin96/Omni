/*
 * Decompiled with CFR 0.152.
 */
package o3;

import java.io.Serializable;
import o3.n;

public abstract class c
implements s3.a,
Serializable {
    public static final Object i = a.a();
    public transient s3.a c;
    public final Object d;
    public final Class e;
    public final String f;
    public final String g;
    public final boolean h;

    public c(Object object, Class clazz, String string, String string2, boolean bl) {
        this.d = object;
        this.e = clazz;
        this.f = string;
        this.g = string2;
        this.h = bl;
    }

    public s3.a b() {
        s3.a a4;
        s3.a a5 = a4 = this.c;
        if (a4 == null) {
            this.c = a5 = this.d();
        }
        return a5;
    }

    public abstract s3.a d();

    public Object g() {
        return this.d;
    }

    public String h() {
        return this.f;
    }

    public s3.c i() {
        Class clazz = this.e;
        if (clazz == null) {
            return null;
        }
        if (this.h) {
            return n.c(clazz);
        }
        return n.b(clazz);
    }

    public String j() {
        return this.g;
    }

    public static class a
    implements Serializable {
        public static final a c = new a();

        public static /* synthetic */ a a() {
            return c;
        }
    }
}

