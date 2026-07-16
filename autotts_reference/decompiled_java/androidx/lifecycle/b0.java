/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.y;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import o3.k;

public class b0 {
    public final Map a = new LinkedHashMap();

    public final void a() {
        Iterator iterator = this.a.values().iterator();
        while (iterator.hasNext()) {
            ((y)iterator.next()).a();
        }
        this.a.clear();
    }

    public final y b(String string) {
        k.e(string, "key");
        return (y)this.a.get(string);
    }

    public final Set c() {
        return new HashSet(this.a.keySet());
    }

    public final void d(String object, y y3) {
        k.e(object, "key");
        k.e(y3, "viewModel");
        object = this.a.put(object, y3);
        if (object != null) {
            ((y)object).d();
        }
    }
}

