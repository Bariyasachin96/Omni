/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.AndroidRuntimeException
 */
package x0;

import android.util.AndroidRuntimeException;
import x0.h;
import x0.i;
import x0.l;

public final class k
extends h {
    public l B = null;
    public float C = Float.MAX_VALUE;
    public boolean D = false;

    public k(Object object, i i3) {
        super(object, i3);
    }

    @Override
    public void k(float f3) {
    }

    @Override
    public void l() {
        this.r();
        this.B.i(this.e());
        super.l();
    }

    @Override
    public boolean n(long l3) {
        float f3;
        if (this.D) {
            float f4 = this.C;
            if (f4 != Float.MAX_VALUE) {
                this.B.g(f4);
                this.C = Float.MAX_VALUE;
            }
            this.b = this.B.b();
            this.a = 0.0f;
            this.D = false;
            return true;
        }
        if (this.C != Float.MAX_VALUE) {
            Object object = this.B;
            double d3 = this.b;
            double d4 = this.a;
            object = ((l)object).j(d3, d4, l3 /= 2L);
            this.B.g(this.C);
            this.C = Float.MAX_VALUE;
            object = this.B.j(((h.o)object).a, ((h.o)object).b, l3);
            this.b = ((h.o)object).a;
            this.a = ((h.o)object).b;
        } else {
            h.o o3 = this.B.j(this.b, this.a, l3);
            this.b = o3.a;
            this.a = o3.b;
        }
        this.b = f3 = Math.max(this.b, this.h);
        this.b = f3 = Math.min(f3, this.g);
        if (this.q(f3, this.a)) {
            this.b = this.B.b();
            this.a = 0.0f;
            return true;
        }
        return false;
    }

    public void o(float f3) {
        if (this.f()) {
            this.C = f3;
            return;
        }
        if (this.B == null) {
            this.B = new l(f3);
        }
        this.B.g(f3);
        this.l();
    }

    public boolean p() {
        return this.B.b > 0.0;
    }

    public boolean q(float f3, float f4) {
        return this.B.e(f3, f4);
    }

    public final void r() {
        l l3 = this.B;
        if (l3 != null) {
            double d3 = l3.b();
            if (!(d3 > (double)this.g)) {
                if (!(d3 < (double)this.h)) {
                    return;
                }
                throw new UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
            }
            throw new UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        throw new UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
    }

    public k s(l l3) {
        this.B = l3;
        return this;
    }

    public void t() {
        if (this.p()) {
            if (this.c().j()) {
                if (this.f) {
                    this.D = true;
                }
                return;
            }
            throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
    }
}

