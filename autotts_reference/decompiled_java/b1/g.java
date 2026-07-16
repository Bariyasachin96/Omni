/*
 * Decompiled with CFR 0.152.
 */
package b1;

import androidx.fragment.app.Fragment;
import o3.k;

public abstract class g
extends RuntimeException {
    public final Fragment c;

    public g(Fragment fragment, String string) {
        k.e(fragment, "fragment");
        super(string);
        this.c = fragment;
    }

    public final Fragment a() {
        return this.c;
    }
}

