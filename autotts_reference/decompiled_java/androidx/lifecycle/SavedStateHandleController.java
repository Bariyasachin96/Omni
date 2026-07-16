/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import androidx.savedstate.a;

public final class SavedStateHandleController
implements i {
    public boolean a;

    @Override
    public void d(k k3, f.a a4) {
        o3.k.e(k3, "source");
        o3.k.e((Object)a4, "event");
        if (a4 == f.a.ON_DESTROY) {
            this.a = false;
            k3.t().c(this);
        }
    }

    public final void h(a a4, f f3) {
        o3.k.e(a4, "registry");
        o3.k.e(f3, "lifecycle");
        if (this.a) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        this.a = true;
        f3.a(this);
        throw null;
    }

    public final boolean i() {
        return this.a;
    }
}

