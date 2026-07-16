/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.d;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;
import androidx.lifecycle.n;

public final class CompositeGeneratedAdaptersObserver
implements i {
    public final d[] a;

    public CompositeGeneratedAdaptersObserver(d[] dArray) {
        o3.k.e(dArray, "generatedAdapters");
        this.a = dArray;
    }

    @Override
    public void d(k object, f.a a4) {
        o3.k.e(object, "source");
        o3.k.e((Object)a4, "event");
        new n();
        object = this.a;
        if (((d[])object).length <= 0) {
            if (((d[])object).length <= 0) {
                return;
            }
            object = object[0];
            throw null;
        }
        object = object[0];
        throw null;
    }
}

