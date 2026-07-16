/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package f2;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.carousel.c;
import f2.a;
import f2.b;

public final class i
extends com.google.android.material.carousel.a {
    public static final int[] d = new int[]{1};
    public static final int[] e = new int[]{1, 0};
    public int c = 0;

    @Override
    public c g(b b3, View view) {
        int n3 = b3.c();
        if (b3.f()) {
            n3 = b3.a();
        }
        Object object = (RecyclerView.LayoutParams)view.getLayoutParams();
        float f3 = object.topMargin + object.bottomMargin;
        float f4 = view.getMeasuredHeight();
        if (b3.f()) {
            f3 = object.leftMargin + object.rightMargin;
            f4 = view.getMeasuredWidth();
        }
        float f5 = this.d() + f3;
        float f6 = Math.max(this.c() + f3, f5);
        float f7 = n3;
        float f8 = Math.min(f4 + f3, f7);
        float f9 = j0.a.a(f4 / 3.0f + f3, f5 + f3, f6 + f3);
        f4 = (f8 + f9) / 2.0f;
        object = d;
        float f10 = 2.0f * f5;
        if (f7 <= f10) {
            object = new int[1];
            object[0] = (RecyclerView.LayoutParams)false;
        }
        int[] nArray = e;
        Object object2 = object;
        Object object3 = nArray;
        if (b3.b() == 1) {
            object2 = com.google.android.material.carousel.a.a((int[])object);
            object3 = com.google.android.material.carousel.a.a(nArray);
        }
        int n4 = (int)Math.max(1.0, Math.floor((f7 - (float)com.google.android.material.carousel.b.i((int[])object3) * f4 - (float)com.google.android.material.carousel.b.i((int[])object2) * f6) / f8));
        int n5 = (int)Math.ceil(f7 / f8);
        int n6 = n5 - n4 + 1;
        object = new int[n6];
        for (n4 = 0; n4 < n6; ++n4) {
            object[n4] = (RecyclerView.LayoutParams)(n5 - n4);
        }
        object3 = f2.a.c(f7, f9, f5, f6, (int[])object2, f4, (int[])object3, f8, (int[])object);
        this.c = ((a)object3).e();
        boolean bl = this.i((a)object3, b3.e());
        n5 = ((a)object3).d;
        if (n5 == 0 && ((a)object3).c == 0 && f7 > f10) {
            ((a)object3).c = 1;
            bl = true;
        }
        object = object3;
        if (bl) {
            n4 = ((a)object3).c;
            n6 = ((a)object3).g;
            object = f2.a.c(f7, f9, f5, f6, new int[]{n4}, f4, new int[]{n5}, f8, new int[]{n6});
        }
        return com.google.android.material.carousel.b.d(view.getContext(), f3, n3, (a)object, b3.b());
    }

    @Override
    public boolean h(b b3, int n3) {
        return n3 < this.c && b3.e() >= this.c || n3 >= this.c && b3.e() < this.c;
    }

    public boolean i(a a4, int n3) {
        n3 = a4.e() - n3;
        boolean bl = n3 > 0 && (a4.c > 0 || a4.d > 1);
        while (n3 > 0) {
            int n4 = a4.c;
            if (n4 > 0) {
                a4.c = n4 - 1;
            } else {
                n4 = a4.d;
                if (n4 > 1) {
                    a4.d = n4 - 1;
                }
            }
            --n3;
        }
        return bl;
    }
}

