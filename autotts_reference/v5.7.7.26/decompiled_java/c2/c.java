/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package c2;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetDragHandleView;
import p0.v;

public final class c
implements v {
    public final BottomSheetDragHandleView a;

    public /* synthetic */ c(BottomSheetDragHandleView bottomSheetDragHandleView) {
        this.a = bottomSheetDragHandleView;
    }

    @Override
    public final boolean a(View view, v.a a4) {
        return BottomSheetDragHandleView.c(this.a, view, a4);
    }
}

