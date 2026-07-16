/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package b;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import b.a;
import o3.k;

public class b
extends a {
    public Intent d(Context context, String[] stringArray) {
        k.e(context, "context");
        k.e(stringArray, "input");
        context = new Intent("android.intent.action.OPEN_DOCUMENT").putExtra("android.intent.extra.MIME_TYPES", stringArray).setType("*/*");
        k.d(context, "Intent(Intent.ACTION_OPE…          .setType(\"*/*\")");
        return context;
    }

    public final a.a e(Context context, String[] stringArray) {
        k.e(context, "context");
        k.e(stringArray, "input");
        return null;
    }

    public final Uri f(int n3, Intent intent) {
        if (n3 != -1) {
            intent = null;
        }
        if (intent != null) {
            return intent.getData();
        }
        return null;
    }
}

