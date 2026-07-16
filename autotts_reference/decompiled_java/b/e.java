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
import androidx.activity.result.ActivityResult;
import o3.g;
import o3.k;

public final class e
extends b.a {
    public static final a a = new a(null);

    public Intent d(Context context, Intent intent) {
        k.e(context, "context");
        k.e(intent, "input");
        return intent;
    }

    public ActivityResult e(int n3, Intent intent) {
        return new ActivityResult(n3, intent);
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }
    }
}

