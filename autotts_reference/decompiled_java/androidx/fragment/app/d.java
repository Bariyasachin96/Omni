/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.fragment.app;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.savedstate.a;

public final class d
implements a.c {
    public final FragmentActivity a;

    public /* synthetic */ d(FragmentActivity fragmentActivity) {
        this.a = fragmentActivity;
    }

    @Override
    public final Bundle a() {
        return FragmentActivity.M(this.a);
    }
}

