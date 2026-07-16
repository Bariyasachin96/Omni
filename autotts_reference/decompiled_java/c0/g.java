/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Configuration
 */
package c0;

import android.content.res.Configuration;
import o3.k;

public final class g {
    public final boolean a;
    public Configuration b;

    public g(boolean bl) {
        this.a = bl;
    }

    public g(boolean bl, Configuration configuration) {
        k.e(configuration, "newConfig");
        this(bl);
        this.b = configuration;
    }

    public final boolean a() {
        return this.a;
    }
}

