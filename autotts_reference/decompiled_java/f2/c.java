/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 */
package f2;

import android.view.View;
import com.google.android.material.carousel.CarouselLayoutManager;

public final class c
implements View.OnLayoutChangeListener {
    public final CarouselLayoutManager a;

    public /* synthetic */ c(CarouselLayoutManager carouselLayoutManager) {
        this.a = carouselLayoutManager;
    }

    public final void onLayoutChange(View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        CarouselLayoutManager.R1(this.a, view, n3, n4, n5, n6, n7, n8, n9, n10);
    }
}

