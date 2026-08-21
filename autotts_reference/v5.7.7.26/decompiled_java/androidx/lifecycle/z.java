/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.a0;
import androidx.lifecycle.b0;
import androidx.lifecycle.c0;
import androidx.lifecycle.y;
import f1.a;
import f1.d;
import o3.g;
import o3.k;

public class z {
    public final b0 a;
    public final b b;
    public final f1.a c;

    public z(b0 b02, b b3) {
        k.e(b02, "store");
        k.e(b3, "factory");
        this(b02, b3, null, 4, null);
    }

    public z(b0 b02, b b3, f1.a a4) {
        k.e(b02, "store");
        k.e(b3, "factory");
        k.e(a4, "defaultCreationExtras");
        this.a = b02;
        this.b = b3;
        this.c = a4;
    }

    public /* synthetic */ z(b0 b02, b b3, f1.a a4, int n3, g g3) {
        if ((n3 & 4) != 0) {
            a4 = a.a.b;
        }
        this(b02, b3, a4);
    }

    public z(c0 c02, b b3) {
        k.e(c02, "owner");
        k.e(b3, "factory");
        this(c02.r(), b3, a0.a(c02));
    }

    public y a(Class clazz) {
        k.e(clazz, "modelClass");
        String string = clazz.getCanonicalName();
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("androidx.lifecycle.ViewModelProvider.DefaultKey:");
            stringBuilder.append(string);
            return this.b(stringBuilder.toString(), clazz);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    public y b(String string, Class object) {
        k.e(string, "key");
        k.e(object, "modelClass");
        Object object2 = this.a.b(string);
        if (((Class)object).isInstance(object2)) {
            k.c(object2, "null cannot be cast to non-null type T of androidx.lifecycle.ViewModelProvider.get");
            return object2;
        }
        object2 = new d(this.c);
        ((d)object2).b(androidx.lifecycle.z$c.c, string);
        try {
            object = object2 = this.b.b((Class)object, (f1.a)object2);
        }
        catch (AbstractMethodError abstractMethodError) {
            object = this.b.a((Class)object);
        }
        this.a.d(string, (y)object);
        return object;
    }

    public static abstract class androidx.lifecycle.z$a
    extends c {
        public static final a d = new a(null);
        public static final a.b e = a.a.a;

        public static final class androidx.lifecycle.z$a$a {
            public cfr_renamed_5() {
            }

            public /* synthetic */ cfr_renamed_5(g g3) {
                this();
            }

            public static final class a
            implements a.b {
                public static final a a = new a();
            }
        }
    }

    public static interface b {
        public static final a a = a.a;

        default public y a(Class clazz) {
            k.e(clazz, "modelClass");
            throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
        }

        default public y b(Class clazz, f1.a a4) {
            k.e(clazz, "modelClass");
            k.e(a4, "extras");
            return this.a(clazz);
        }

        public static final class a {
            public static final a a = new a();
        }
    }

    public static abstract class c
    implements b {
        public static final a b = new a(null);
        public static final a.b c = a.a.a;

        public static final class androidx.lifecycle.z$c$a {
            public cfr_renamed_6() {
            }

            public /* synthetic */ cfr_renamed_6(g g3) {
                this();
            }

            public static final class a
            implements a.b {
                public static final a a = new a();
            }
        }
    }
}

