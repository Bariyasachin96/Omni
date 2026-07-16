/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 */
package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;

public abstract class i {
    public Fragment d(Context context, String string, Bundle bundle) {
        return Fragment.W(context, string, bundle);
    }

    public abstract View f(int var1);

    public abstract boolean j();
}

