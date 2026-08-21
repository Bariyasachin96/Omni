/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 */
package androidx.viewpager2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;

public final class a
extends RecyclerView.d0 {
    public a(FrameLayout frameLayout) {
        super((View)frameLayout);
    }

    public static a M(ViewGroup viewGroup) {
        viewGroup = new FrameLayout(viewGroup.getContext());
        viewGroup.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        viewGroup.setId(View.generateViewId());
        viewGroup.setSaveEnabled(false);
        return new a((FrameLayout)viewGroup);
    }

    public FrameLayout N() {
        return (FrameLayout)this.a;
    }
}

