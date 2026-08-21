/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package b1;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import b1.g;
import o3.k;

public final class d
extends g {
    public final ViewGroup d;

    public d(Fragment fragment, ViewGroup viewGroup) {
        k.e(fragment, "fragment");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to use <fragment> tag to add fragment ");
        stringBuilder.append(fragment);
        stringBuilder.append(" to container ");
        stringBuilder.append(viewGroup);
        super(fragment, stringBuilder.toString());
        this.d = viewGroup;
    }
}

