/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 */
package n;

import android.content.Context;
import android.content.res.ColorStateList;
import n.b;
import n.c;
import n.d;
import n.e;

public class a
implements c {
    @Override
    public ColorStateList a(b b3) {
        return this.p(b3).b();
    }

    @Override
    public void b(b b3) {
        this.d(b3, this.f(b3));
    }

    @Override
    public float c(b b3) {
        return this.j(b3) * 2.0f;
    }

    @Override
    public void d(b b3, float f3) {
        this.p(b3).g(f3, b3.e(), b3.d());
        this.o(b3);
    }

    @Override
    public void e(b b3, ColorStateList colorStateList) {
        this.p(b3).f(colorStateList);
    }

    @Override
    public float f(b b3) {
        return this.p(b3).c();
    }

    @Override
    public void g() {
    }

    @Override
    public void h(b b3, Context context, ColorStateList colorStateList, float f3, float f4, float f5) {
        b3.c(new d(colorStateList, f3));
        context = b3.b();
        context.setClipToOutline(true);
        context.setElevation(f4);
        this.d(b3, f5);
    }

    @Override
    public void i(b b3, float f3) {
        this.p(b3).h(f3);
    }

    @Override
    public float j(b b3) {
        return this.p(b3).d();
    }

    @Override
    public float k(b b3) {
        return this.j(b3) * 2.0f;
    }

    @Override
    public void l(b b3) {
        this.d(b3, this.f(b3));
    }

    @Override
    public float m(b b3) {
        return b3.b().getElevation();
    }

    @Override
    public void n(b b3, float f3) {
        b3.b().setElevation(f3);
    }

    @Override
    public void o(b b3) {
        if (!b3.e()) {
            b3.a(0, 0, 0, 0);
            return;
        }
        float f3 = this.f(b3);
        float f4 = this.j(b3);
        int n3 = (int)Math.ceil(e.a(f3, f4, b3.d()));
        int n4 = (int)Math.ceil(e.b(f3, f4, b3.d()));
        b3.a(n3, n4, n3, n4);
    }

    public final d p(b b3) {
        return (d)b3.f();
    }
}

