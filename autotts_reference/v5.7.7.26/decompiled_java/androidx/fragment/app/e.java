/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Configuration
 */
package androidx.fragment.app;

import android.content.res.Configuration;
import androidx.fragment.app.FragmentActivity;
import n0.a;

public final class e
implements a {
    public final FragmentActivity a;

    public /* synthetic */ e(FragmentActivity fragmentActivity) {
        this.a = fragmentActivity;
    }

    @Override
    public final void accept(Object object) {
        FragmentActivity.L(this.a, (Configuration)object);
    }
}

