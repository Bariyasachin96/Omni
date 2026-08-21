/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.view.View
 */
package f2;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.carousel.CarouselLayoutManager;

public abstract class e {
    public final int a;

    public e(int n3) {
        this.a = n3;
    }

    public /* synthetic */ e(int n3, a a4) {
        this(n3);
    }

    public static e b(CarouselLayoutManager carouselLayoutManager) {
        return new e(0, carouselLayoutManager){
            public final CarouselLayoutManager b;
            {
                this.b = carouselLayoutManager;
                super(n3, null);
            }

            @Override
            public void a(RectF rectF, RectF rectF2, RectF rectF3) {
                float f3 = rectF2.left;
                float f4 = rectF3.left;
                if (f3 < f4 && rectF2.right > f4) {
                    rectF.left += (f4 -= f3);
                    rectF2.left += f4;
                }
                if ((f3 = rectF2.right) > (f4 = rectF3.right) && rectF2.left < f4) {
                    f4 = f3 - f4;
                    rectF.right = Math.max(rectF.right - f4, rectF.left);
                    rectF2.right = Math.max(rectF2.right - f4, rectF2.left);
                }
            }

            @Override
            public RectF e(float f3, float f4, float f5, float f6) {
                return new RectF(f6, 0.0f, f4 - f6, f3);
            }

            @Override
            public int f() {
                return this.b.b0() - this.b.h0();
            }

            @Override
            public int g() {
                return 0;
            }

            @Override
            public int h() {
                return this.b.s0();
            }

            @Override
            public int i() {
                if (this.b.F2()) {
                    return this.h();
                }
                return this.g();
            }

            @Override
            public int j() {
                return this.b.k0();
            }

            @Override
            public void k(View view, int n3, int n4) {
                int n5 = this.j();
                int n6 = this.n(view);
                this.b.D0(view, n3, n5, n4, n5 + n6);
            }

            @Override
            public void l(RectF rectF, RectF rectF2, RectF rectF3) {
                float f3;
                if (rectF2.right <= rectF3.left) {
                    rectF.right = f3 = (float)Math.floor(rectF.right) - 1.0f;
                    rectF.left = Math.min(rectF.left, f3);
                }
                if (rectF2.left >= rectF3.right) {
                    rectF.left = f3 = (float)Math.ceil(rectF.left) + 1.0f;
                    rectF.right = Math.max(f3, rectF.right);
                }
            }

            @Override
            public void m(View view, Rect rect, float f3, float f4) {
                view.offsetLeftAndRight((int)(f4 - ((float)rect.left + f3)));
            }

            public int n(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.b.W(view) + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        };
    }

    public static e c(CarouselLayoutManager carouselLayoutManager, int n3) {
        if (n3 != 0) {
            if (n3 == 1) {
                return e.d(carouselLayoutManager);
            }
            throw new IllegalArgumentException("invalid orientation");
        }
        return e.b(carouselLayoutManager);
    }

    public static e d(CarouselLayoutManager carouselLayoutManager) {
        return new e(1, carouselLayoutManager){
            public final CarouselLayoutManager b;
            {
                this.b = carouselLayoutManager;
                super(n3, null);
            }

            @Override
            public void a(RectF rectF, RectF rectF2, RectF rectF3) {
                float f3 = rectF2.top;
                float f4 = rectF3.top;
                if (f3 < f4 && rectF2.bottom > f4) {
                    rectF.top += (f4 -= f3);
                    rectF3.top += f4;
                }
                if ((f4 = rectF2.bottom) > (f3 = rectF3.bottom) && rectF2.top < f3) {
                    rectF.bottom = Math.max(rectF.bottom - (f4 -= f3), rectF.top);
                    rectF2.bottom = Math.max(rectF2.bottom - f4, rectF2.top);
                }
            }

            @Override
            public RectF e(float f3, float f4, float f5, float f6) {
                return new RectF(0.0f, f5, f4, f3 - f5);
            }

            @Override
            public int f() {
                return this.b.b0();
            }

            @Override
            public int g() {
                return this.b.i0();
            }

            @Override
            public int h() {
                return this.b.s0() - this.b.j0();
            }

            @Override
            public int i() {
                return this.j();
            }

            @Override
            public int j() {
                return 0;
            }

            @Override
            public void k(View view, int n3, int n4) {
                int n5 = this.g();
                int n6 = this.n(view);
                this.b.D0(view, n5, n3, n5 + n6, n4);
            }

            @Override
            public void l(RectF rectF, RectF rectF2, RectF rectF3) {
                float f3;
                if (rectF2.bottom <= rectF3.top) {
                    rectF.bottom = f3 = (float)Math.floor(rectF.bottom) - 1.0f;
                    rectF.top = Math.min(rectF.top, f3);
                }
                if (rectF2.top >= rectF3.bottom) {
                    rectF.top = f3 = (float)Math.ceil(rectF.top) + 1.0f;
                    rectF.bottom = Math.max(f3, rectF.bottom);
                }
            }

            @Override
            public void m(View view, Rect rect, float f3, float f4) {
                view.offsetTopAndBottom((int)(f4 - ((float)rect.top + f3)));
            }

            public int n(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                return this.b.X(view) + layoutParams.leftMargin + layoutParams.rightMargin;
            }
        };
    }

    public abstract void a(RectF var1, RectF var2, RectF var3);

    public abstract RectF e(float var1, float var2, float var3, float var4);

    public abstract int f();

    public abstract int g();

    public abstract int h();

    public abstract int i();

    public abstract int j();

    public abstract void k(View var1, int var2, int var3);

    public abstract void l(RectF var1, RectF var2, RectF var3);

    public abstract void m(View var1, Rect var2, float var3, float var4);
}

