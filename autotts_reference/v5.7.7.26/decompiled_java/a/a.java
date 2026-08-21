/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package a;

import a.b;
import android.content.Context;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import o3.k;

public final class a {
    public final Set a = new CopyOnWriteArraySet();
    public volatile Context b;

    public final void a(b b3) {
        k.e(b3, "listener");
        Context context = this.b;
        if (context != null) {
            b3.a(context);
        }
        this.a.add(b3);
    }

    public final void b() {
        this.b = null;
    }

    public final void c(Context context) {
        k.e(context, "context");
        this.b = context;
        Iterator iterator = this.a.iterator();
        while (iterator.hasNext()) {
            ((b)iterator.next()).a(context);
        }
    }
}

