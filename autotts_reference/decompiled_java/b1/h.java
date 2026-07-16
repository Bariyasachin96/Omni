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

public final class h
extends g {
    public final ViewGroup d;

    public h(Fragment fragment, ViewGroup viewGroup) {
        k.e(fragment, "fragment");
        k.e(viewGroup, "container");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to add fragment ");
        stringBuilder.append(fragment);
        stringBuilder.append(" to container ");
        stringBuilder.append(viewGroup);
        stringBuilder.append(" which is not a FragmentContainerView");
        super(fragment, stringBuilder.toString());
        this.d = viewGroup;
    }
}

