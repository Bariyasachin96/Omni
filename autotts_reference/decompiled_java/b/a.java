/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 */
package b;

import android.content.Context;
import android.content.Intent;
import o3.k;

public abstract class a {
    public abstract Intent a(Context var1, Object var2);

    public a b(Context context, Object object) {
        k.e(context, "context");
        return null;
    }

    public abstract Object c(int var1, Intent var2);

    public static final class a {
        public final Object a;

        public a(Object object) {
            this.a = object;
        }

        public final Object a() {
            return this.a;
        }
    }
}

