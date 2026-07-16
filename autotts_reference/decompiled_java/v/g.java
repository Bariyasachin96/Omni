/*
 * Decompiled with CFR 0.152.
 */
package v;

import v.d;
import v.f;
import v.l;
import v.p;

public class g
extends f {
    public int m;

    public g(p p3) {
        super(p3);
        if (p3 instanceof l) {
            this.e = f.a.d;
            return;
        }
        this.e = f.a.e;
    }

    @Override
    public void d(int n3) {
        if (!this.j) {
            this.j = true;
            this.g = n3;
            for (d d3 : this.k) {
                d3.a(d3);
            }
        }
    }
}

