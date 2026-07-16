/*
 * Decompiled with CFR 0.152.
 */
package f2;

import com.google.android.material.carousel.CarouselLayoutManager;

public final class d
implements Runnable {
    public final CarouselLayoutManager c;

    public /* synthetic */ d(CarouselLayoutManager carouselLayoutManager) {
        this.c = carouselLayoutManager;
    }

    @Override
    public final void run() {
        CarouselLayoutManager.Q1(this.c);
    }
}

