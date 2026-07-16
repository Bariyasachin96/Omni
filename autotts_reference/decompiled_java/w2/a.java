/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup$MarginLayoutParams
 */
package w2;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.sidesheet.SideSheetBehavior;
import w2.c;
import w2.d;

public final class a
extends c {
    public final SideSheetBehavior a;

    public a(SideSheetBehavior sideSheetBehavior) {
        this.a = sideSheetBehavior;
    }

    @Override
    public int a(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.leftMargin;
    }

    @Override
    public float b(int n3) {
        float f3 = this.e();
        float f4 = this.d();
        return ((float)n3 - f3) / (f4 - f3);
    }

    @Override
    public int c(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.leftMargin;
    }

    @Override
    public int d() {
        return Math.max(0, this.a.m0() + this.a.k0());
    }

    @Override
    public int e() {
        return -this.a.d0() - this.a.k0();
    }

    @Override
    public int f() {
        return this.a.k0();
    }

    @Override
    public int g() {
        return -this.a.d0();
    }

    @Override
    public int h(View view) {
        return view.getRight() + this.a.k0();
    }

    @Override
    public int i(CoordinatorLayout coordinatorLayout) {
        return coordinatorLayout.getLeft();
    }

    @Override
    public int j() {
        return 1;
    }

    @Override
    public boolean k(float f3) {
        return f3 > 0.0f;
    }

    @Override
    public boolean l(View view) {
        return view.getRight() < (this.d() - this.e()) / 2;
    }

    @Override
    public boolean m(float f3, float f4) {
        return d.a(f3, f4) && Math.abs(f3) > (float)this.a.o0();
    }

    @Override
    public boolean n(View view, float f3) {
        return Math.abs((float)view.getLeft() + f3 * this.a.i0()) > this.a.j0();
    }

    @Override
    public void o(ViewGroup.MarginLayoutParams marginLayoutParams, int n3) {
        marginLayoutParams.leftMargin = n3;
    }

    @Override
    public void p(ViewGroup.MarginLayoutParams marginLayoutParams, int n3, int n4) {
        if (n3 <= this.a.n0()) {
            marginLayoutParams.leftMargin = n4;
        }
    }
}

