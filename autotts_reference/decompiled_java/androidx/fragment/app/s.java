/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.fragment.app;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.savedstate.a;

public final class s
implements a.c {
    public final FragmentManager a;

    public /* synthetic */ s(FragmentManager fragmentManager) {
        this.a = fragmentManager;
    }

    @Override
    public final Bundle a() {
        return FragmentManager.b(this.a);
    }
}

