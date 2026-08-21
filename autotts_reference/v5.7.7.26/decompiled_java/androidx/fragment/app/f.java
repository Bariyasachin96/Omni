/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package androidx.fragment.app;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import n0.a;

public final class f
implements a {
    public final FragmentActivity a;

    public /* synthetic */ f(FragmentActivity fragmentActivity) {
        this.a = fragmentActivity;
    }

    @Override
    public final void accept(Object object) {
        FragmentActivity.N(this.a, (Intent)object);
    }
}

