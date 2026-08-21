/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 */
package f2;

import android.graphics.Canvas;
import com.google.android.material.carousel.MaskableFrameLayout;
import d2.a;

public final class h
implements a.a {
    public final MaskableFrameLayout a;

    public /* synthetic */ h(MaskableFrameLayout maskableFrameLayout) {
        this.a = maskableFrameLayout;
    }

    @Override
    public final void a(Canvas canvas) {
        MaskableFrameLayout.b(this.a, canvas);
    }
}

