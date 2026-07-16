/*
 * Decompiled with CFR 0.152.
 */
package b1;

import androidx.fragment.app.Fragment;
import b1.g;
import o3.k;

public final class a
extends g {
    public final String d;

    public a(Fragment fragment, String string) {
        k.e(fragment, "fragment");
        k.e(string, "previousFragmentId");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to reuse fragment ");
        stringBuilder.append(fragment);
        stringBuilder.append(" with previous ID ");
        stringBuilder.append(string);
        super(fragment, stringBuilder.toString());
        this.d = string;
    }
}

