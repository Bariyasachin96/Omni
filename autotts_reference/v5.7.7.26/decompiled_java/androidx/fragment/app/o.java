/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Configuration
 */
package androidx.fragment.app;

import android.content.res.Configuration;
import androidx.fragment.app.FragmentManager;
import n0.a;

public final class o
implements a {
    public final FragmentManager a;

    public /* synthetic */ o(FragmentManager fragmentManager) {
        this.a = fragmentManager;
    }

    @Override
    public final void accept(Object object) {
        FragmentManager.e(this.a, (Configuration)object);
    }
}

