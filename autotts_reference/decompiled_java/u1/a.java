/*
 * Decompiled with CFR 0.152.
 */
package u1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import u1.b;

public abstract class a {
    public static boolean a(Object object, Object object2) {
        if (object != object2) {
            return object != null && object.equals(object2);
        }
        return true;
    }

    public static int b(Object ... objectArray) {
        return Arrays.hashCode(objectArray);
    }

    public static a c(Object object) {
        return new a(object, null);
    }

    public static final class a {
        public final List a;
        public final Object b;

        public /* synthetic */ a(Object object, byte[] byArray) {
            u1.b.c(object);
            this.b = object;
            this.a = new ArrayList();
        }

        public a a(String string, Object object) {
            u1.b.c(string);
            int n3 = string.length();
            object = String.valueOf(object);
            StringBuilder stringBuilder = new StringBuilder(n3 + 1 + ((String)object).length());
            stringBuilder.append(string);
            stringBuilder.append("=");
            stringBuilder.append((String)object);
            string = stringBuilder.toString();
            this.a.add(string);
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(100);
            stringBuilder.append(this.b.getClass().getSimpleName());
            stringBuilder.append('{');
            List list = this.a;
            int n3 = list.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                stringBuilder.append((String)list.get(i3));
                if (i3 >= n3 - 1) continue;
                stringBuilder.append(", ");
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }
}

