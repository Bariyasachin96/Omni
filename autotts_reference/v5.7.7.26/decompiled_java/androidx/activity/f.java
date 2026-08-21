/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.activity;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.savedstate.a;

public final class f
implements a.c {
    public final ComponentActivity a;

    public /* synthetic */ f(ComponentActivity componentActivity) {
        this.a = componentActivity;
    }

    @Override
    public final Bundle a() {
        return ComponentActivity.z(this.a);
    }
}

