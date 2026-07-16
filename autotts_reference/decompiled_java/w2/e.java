/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package w2;

import android.view.View;
import com.google.android.material.sidesheet.SideSheetBehavior;
import p0.v;

public final class e
implements v {
    public final SideSheetBehavior a;
    public final int b;

    public /* synthetic */ e(SideSheetBehavior sideSheetBehavior, int n3) {
        this.a = sideSheetBehavior;
        this.b = n3;
    }

    @Override
    public final boolean a(View view, v.a a4) {
        return SideSheetBehavior.I(this.a, this.b, view, a4);
    }
}

