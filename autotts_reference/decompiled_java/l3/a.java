/*
 * Decompiled with CFR 0.152.
 */
package l3;

import p3.c;

public class a
extends k3.a {
    @Override
    public c a() {
        if (this.b(34)) {
            return new q3.a();
        }
        return super.a();
    }

    public final boolean b(int n3) {
        Integer n4 = a.b;
        return n4 == null || n4 >= n3;
        {
        }
    }

    public static final class a {
        public static final a a;
        public static final Integer b;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static {
            Object object;
            Object object2;
            block4: {
                a = new a();
                object2 = null;
                try {
                    object = Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
                    if (!(object instanceof Integer)) break block4;
                    object = (Integer)object;
                }
                catch (Throwable throwable) {}
            }
            object = null;
            Object object3 = object2;
            if (object != null) {
                object3 = object2;
                if (((Number)object).intValue() > 0) {
                    object3 = object;
                }
            }
            b = object3;
        }
    }
}

