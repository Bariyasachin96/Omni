/*
 * Decompiled with CFR 0.152.
 */
package w2;

import com.google.android.material.sidesheet.SideSheetBehavior;

public final class f
implements Runnable {
    public final SideSheetBehavior c;
    public final int d;

    public /* synthetic */ f(SideSheetBehavior sideSheetBehavior, int n3) {
        this.c = sideSheetBehavior;
        this.d = n3;
    }

    @Override
    public final void run() {
        SideSheetBehavior.J(this.c, this.d);
    }
}

