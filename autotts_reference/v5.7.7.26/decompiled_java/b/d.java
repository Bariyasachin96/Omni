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
import b.a;
import b.c;
import o3.k;

public final class d
extends a {
    public Intent d(Context context, String string) {
        k.e(context, "context");
        k.e(string, "input");
        return c.a.a(new String[]{string});
    }

    public a.a e(Context context, String string) {
        k.e(context, "context");
        k.e(string, "input");
        if (e0.a.a(context, string) == 0) {
            return new a.a(Boolean.TRUE);
        }
        return null;
    }

    public Boolean f(int n3, Intent object) {
        if (object != null && n3 == -1) {
            boolean bl;
            object = object.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
            boolean bl2 = bl = false;
            if (object != null) {
                int n4 = ((Intent)object).length;
                n3 = 0;
                while (true) {
                    bl2 = bl;
                    if (n3 >= n4) break;
                    if (object[n3] == false) {
                        bl2 = true;
                        break;
                    }
                    ++n3;
                }
            }
            return bl2;
        }
        return Boolean.FALSE;
    }
}

