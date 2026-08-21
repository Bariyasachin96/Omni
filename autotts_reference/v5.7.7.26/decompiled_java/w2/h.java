/*
 * Decompiled with CFR 0.152.
 */
package w2;

import com.google.android.material.sidesheet.SideSheetBehavior;

public final class h
implements Runnable {
    public final SideSheetBehavior.c c;

    public /* synthetic */ h(SideSheetBehavior.c c3) {
        this.c = c3;
    }

    @Override
    public final void run() {
        SideSheetBehavior.c.a(this.c);
    }
}

