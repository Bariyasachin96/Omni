/*
 * Decompiled with CFR 0.152.
 */
package b1;

import androidx.fragment.app.Fragment;
import b1.f;
import o3.k;

public final class e
extends f {
    public e(Fragment fragment) {
        k.e(fragment, "fragment");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to get target fragment from fragment ");
        stringBuilder.append(fragment);
        super(fragment, stringBuilder.toString());
    }
}

