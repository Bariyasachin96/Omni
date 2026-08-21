/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import androidx.lifecycle.v;

public final class SavedStateHandleAttacher
implements i {
    public final v a;

    public SavedStateHandleAttacher(v v3) {
        o3.k.e(v3, "provider");
        this.a = v3;
    }

    @Override
    public void d(k object, f.a a4) {
        o3.k.e(object, "source");
        o3.k.e((Object)a4, "event");
        if (a4 == f.a.ON_CREATE) {
            object.t().c(this);
            this.a.c();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Next event must be ON_CREATE, it was ");
        ((StringBuilder)object).append((Object)a4);
        throw new IllegalStateException(((StringBuilder)object).toString().toString());
    }
}

