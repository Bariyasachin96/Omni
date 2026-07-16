/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.f;
import androidx.lifecycle.g;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import g3.c;
import v3.b;

public final class LifecycleCoroutineScopeImpl
extends g
implements i {
    public final f a;
    public final c b;

    @Override
    public void d(k k3, f.a a4) {
        o3.k.e(k3, "source");
        o3.k.e((Object)a4, "event");
        if (this.i().b().compareTo(f.b.c) <= 0) {
            this.i().c(this);
            v3.b.b(this.h(), null, 1, null);
        }
    }

    public c h() {
        return this.b;
    }

    public f i() {
        return this.a;
    }
}

