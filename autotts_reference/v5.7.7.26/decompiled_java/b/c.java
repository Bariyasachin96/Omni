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
import d3.d;
import d3.h;
import e3.a0;
import e3.b0;
import e3.i;
import e3.t;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import o3.g;
import o3.k;
import r3.e;

public final class c
extends b.a {
    public static final a a = new a(null);

    public Intent d(Context context, String[] stringArray) {
        k.e(context, "context");
        k.e(stringArray, "input");
        return a.a(stringArray);
    }

    public a.a e(Context object, String[] stringArray) {
        int n3;
        k.e(object, "context");
        k.e(stringArray, "input");
        if (stringArray.length == 0) {
            return new a.a(b0.d());
        }
        int n4 = stringArray.length;
        int n5 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            if (e0.a.a((Context)object, stringArray[n3]) == 0) {
                continue;
            }
            return null;
        }
        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>(e.a(a0.a(stringArray.length), 16));
        n4 = stringArray.length;
        for (n3 = n5; n3 < n4; ++n3) {
            object = h.a(stringArray[n3], Boolean.TRUE);
            linkedHashMap.put(((d)object).c(), ((d)object).d());
        }
        return new a.a(linkedHashMap);
    }

    public Map f(int n3, Intent object) {
        if (n3 != -1) {
            return b0.d();
        }
        if (object == null) {
            return b0.d();
        }
        Object[] objectArray = object.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
        int[] nArray = object.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
        if (nArray != null && objectArray != null) {
            object = new ArrayList(nArray.length);
            int n4 = nArray.length;
            for (n3 = 0; n3 < n4; ++n3) {
                boolean bl = nArray[n3] == 0;
                object.add(bl);
            }
            return b0.g(t.x(i.p(objectArray), (Iterable)object));
        }
        return b0.d();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final Intent a(String[] intent) {
            k.e(intent, "input");
            intent = new Intent("androidx.activity.result.contract.action.REQUEST_PERMISSIONS").putExtra("androidx.activity.result.contract.extra.PERMISSIONS", (String[])intent);
            k.d(intent, "Intent(ACTION_REQUEST_PE…EXTRA_PERMISSIONS, input)");
            return intent;
        }
    }
}

