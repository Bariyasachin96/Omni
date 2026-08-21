/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.a;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;

@Deprecated
class ReflectiveGenericLifecycleObserver
implements i {
    public final Object a;
    public final a.a b;

    public ReflectiveGenericLifecycleObserver(Object object) {
        this.a = object;
        this.b = androidx.lifecycle.a.c.c(object.getClass());
    }

    @Override
    public void d(k k3, f.a a4) {
        this.b.a(k3, a4, this.a);
    }
}

