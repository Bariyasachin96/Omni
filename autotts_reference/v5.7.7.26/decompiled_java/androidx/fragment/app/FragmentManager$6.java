/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.fragment.app;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.f;
import androidx.lifecycle.i;
import androidx.lifecycle.k;

class FragmentManager$6
implements i {
    public final String a;
    public final f b;
    public final FragmentManager c;

    @Override
    public void d(k k3, f.a a4) {
        if (a4 == f.a.ON_START && (Bundle)FragmentManager.f(this.c).get(this.a) != null) {
            throw null;
        }
        if (a4 == f.a.ON_DESTROY) {
            this.b.c(this);
            FragmentManager.g(this.c).remove(this.a);
        }
    }
}

