/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package u0;

import android.view.View;
import android.view.ViewGroup;
import o0.d1;
import o0.e1;
import o3.k;
import u0.b;
import u0.c;

public abstract class a {
    public static final int a = c.pooling_container_listener_holder_tag;
    public static final int b = c.is_pooling_container_tag;

    public static final void a(View object) {
        k.e(object, "<this>");
        object = e1.a((View)object).iterator();
        while (object.hasNext()) {
            u0.a.c((View)object.next()).a();
        }
    }

    public static final void b(ViewGroup object) {
        k.e(object, "<this>");
        object = d1.a((ViewGroup)object).iterator();
        while (object.hasNext()) {
            u0.a.c((View)object.next()).a();
        }
    }

    public static final b c(View view) {
        b b3;
        int n3 = a;
        b b4 = b3 = (b)view.getTag(n3);
        if (b3 == null) {
            b4 = new b();
            view.setTag(n3, (Object)b4);
        }
        return b4;
    }

    public static final void d(View view, boolean bl) {
        k.e(view, "<this>");
        view.setTag(b, (Object)bl);
    }
}

