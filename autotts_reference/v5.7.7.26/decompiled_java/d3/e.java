/*
 * Decompiled with CFR 0.152.
 */
package d3;

import java.io.Serializable;
import o3.g;
import o3.k;

public abstract class e
implements Serializable {
    public static final a c = new a(null);

    public static Object a(Object object) {
        return object;
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }

    public static final class b
    implements Serializable {
        public final Throwable c;

        public b(Throwable throwable) {
            k.e(throwable, "exception");
            this.c = throwable;
        }

        public boolean equals(Object object) {
            return object instanceof b && k.a(this.c, ((b)object).c);
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failure(");
            stringBuilder.append(this.c);
            stringBuilder.append(')');
            return stringBuilder.toString();
        }
    }
}

